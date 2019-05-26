/*
 * Copyright (C) 2019 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.xuexiang.templateproject;

import android.app.Application;
import android.content.Context;

import com.xuexiang.templateproject.core.BaseActivity;
import com.xuexiang.templateproject.core.XHttpUpdateHttpServiceImpl;
import com.xuexiang.xaop.XAOP;
import com.xuexiang.xaop.util.PermissionUtils;
import com.xuexiang.xhttp2.XHttpSDK;
import com.xuexiang.xpage.AppPageConfig;
import com.xuexiang.xpage.PageConfig;
import com.xuexiang.xpage.PageConfiguration;
import com.xuexiang.xpage.model.PageInfo;
import com.xuexiang.xui.XUI;
import com.xuexiang.xupdate.XUpdate;
import com.xuexiang.xupdate.utils.UpdateUtils;
import com.xuexiang.xutil.XUtil;
import com.xuexiang.xutil.common.StringUtils;
import com.xuexiang.xutil.tip.ToastUtils;

import java.util.List;

/**
 * @author xuexiang
 * @since 2018/11/7 下午1:12
 */
public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initLibs();
    }


    /**
     * 初始化基础库
     */
    private void initLibs() {
        initXUtil();

        initXPage();

        initXAOP();

        initXUI();

        initXHttp2();

        initXUpdate();
    }

    /**
     * 初始化XUtil工具类
     */
    private void initXUtil() {
        XUtil.init(this);
        XUtil.debug(BuildConfig.DEBUG);
    }

    /**
     * 初始化XPage
     */
    private void initXPage() {
        PageConfig.getInstance()
                //页面注册
                .setPageConfiguration(new PageConfiguration() {
                    @Override
                    public List<PageInfo> registerPages(Context context) {
                        //自动注册页面,是编译时自动生成的，build一下就出来了
                        return AppPageConfig.getInstance().getPages();
                    }
                })
                .debug(BuildConfig.DEBUG ? "PageLog" : null)
                .enableWatcher(BuildConfig.DEBUG)
                .setContainActivityClazz(BaseActivity.class)
                .init(this);
    }

    /**
     * 初始化XAOP
     */
    private void initXAOP() {
        XAOP.init(this);
        XAOP.debug(BuildConfig.DEBUG);
        //设置动态申请权限切片 申请权限被拒绝的事件响应监听
        XAOP.setOnPermissionDeniedListener(new PermissionUtils.OnPermissionDeniedListener() {
            @Override
            public void onDenied(List<String> permissionsDenied) {
                ToastUtils.toast("权限申请被拒绝:" + StringUtils.listToString(permissionsDenied, ","));
            }

        });
    }

    /**
     * 初始化XUI
     */
    private void initXUI() {
        XUI.init(this);
        XUI.debug(BuildConfig.DEBUG);
    }

    /**
     * 初始化XHttp2
     */
    private void initXHttp2() {
        //初始化网络请求框架，必须首先执行
        XHttpSDK.init(this);
        //需要调试的时候执行
        XHttpSDK.debug();
//        XHttpSDK.debug(new CustomLoggingInterceptor()); //设置自定义的日志打印拦截器
        //设置网络请求的全局基础地址
        XHttpSDK.setBaseUrl("http://127.0.0.1:8080");
//        //设置动态参数添加拦截器
//        XHttpSDK.addInterceptor(new CustomDynamicInterceptor());
//        //请求失效校验拦截器
//        XHttpSDK.addInterceptor(new CustomExpiredInterceptor());
    }

    /**
     * 初始化XUpdate
     */
    private void initXUpdate() {
        XUpdate.get()
                .debug(BuildConfig.DEBUG)
                //默认设置只在wifi下检查版本更新
                .isWifiOnly(false)
                //默认设置使用get请求检查版本
                .isGet(true)
                //默认设置非自动模式，可根据具体使用配置
                .isAutoMode(false)
                //设置默认公共请求参数
                .param("versionCode", UpdateUtils.getVersionCode(this))
                .param("appKey", getPackageName())
                //这个必须设置！实现网络请求功能。
                .setIUpdateHttpService(new XHttpUpdateHttpServiceImpl())
                //这个必须初始化
                .init(this);
    }
}
