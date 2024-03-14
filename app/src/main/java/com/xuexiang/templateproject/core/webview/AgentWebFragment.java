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

package com.xuexiang.templateproject.core.webview;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;

import com.just.agentweb.action.PermissionInterceptor;
import com.just.agentweb.core.AgentWeb;
import com.just.agentweb.core.client.MiddlewareWebChromeBase;
import com.just.agentweb.core.client.MiddlewareWebClientBase;
import com.just.agentweb.core.client.WebListenerManager;
import com.just.agentweb.core.web.AbsAgentWebSettings;
import com.just.agentweb.core.web.AgentWebConfig;
import com.just.agentweb.core.web.IAgentWebSettings;
import com.just.agentweb.download.AgentWebDownloader;
import com.just.agentweb.download.DefaultDownloadImpl;
import com.just.agentweb.download.DownloadListenerAdapter;
import com.just.agentweb.download.DownloadingService;
import com.just.agentweb.utils.LogUtils;
import com.just.agentweb.widget.IWebLayout;
import com.xuexiang.templateproject.MyApp;
import com.xuexiang.templateproject.R;
import com.xuexiang.xui.utils.XToastUtils;
import com.xuexiang.xutil.net.JsonUtil;

import java.util.HashMap;

/**
 * 通用WebView页面
 *
 * @author xuexiang
 * @since 2019/1/4 下午11:13
 */
public class AgentWebFragment extends Fragment implements FragmentKeyDown {
    public static final String KEY_URL = "com.xuexiang.xuidemo.base.webview.key_url";

    private ImageView mBackImageView;
    private View mLineView;
    private ImageView mFinishImageView;
    private TextView mTitleTextView;
    private AgentWeb mAgentWeb;
    private ImageView mMoreImageView;
    private PopupMenu mPopupMenu;
    public static final String TAG = AgentWebFragment.class.getSimpleName();
    private DownloadingService mDownloadingService;

    public static AgentWebFragment getInstance(String url) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_URL, url);
        return getInstance(bundle);
    }

    public static AgentWebFragment getInstance(Bundle bundle) {
        AgentWebFragment fragment = new AgentWebFragment();
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_agentweb, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAgentWeb = AgentWeb.with(this)
                //传入AgentWeb的父控件。
                .setAgentWebParent((LinearLayout) view, -1, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
                //设置进度条颜色与高度，-1为默认值，高度为2，单位为dp。
                .useDefaultIndicator(-1, 3)
                //设置 IAgentWebSettings。
                .setAgentWebWebSettings(getSettings())
                //WebViewClient ， 与 WebView 使用一致 ，但是请勿获取WebView调用setWebViewClient(xx)方法了,会覆盖AgentWeb DefaultWebClient,同时相应的中间件也会失效。
                .setWebViewClient(mWebViewClient)
                //WebChromeClient
                .setWebChromeClient(mWebChromeClient)
                //设置WebChromeClient中间件，支持多个WebChromeClient，AgentWeb 3.0.0 加入。
                .useMiddlewareWebChrome(getMiddlewareWebChrome())
                //设置WebViewClient中间件，支持多个WebViewClient， AgentWeb 3.0.0 加入。
                .useMiddlewareWebClient(getMiddlewareWebClient())
                //权限拦截 2.0.0 加入。
                .setPermissionInterceptor(mPermissionInterceptor)
                //严格模式 Android 4.2.2 以下会放弃注入对象 ，使用AgentWebView没影响。
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                //自定义UI  AgentWeb3.0.0 加入。
                .setAgentWebUIController(new UIController(getActivity()))
                //参数1是错误显示的布局，参数2点击刷新控件ID -1表示点击整个布局都刷新， AgentWeb 3.0.0 加入。
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
                .setWebLayout(getWebLayout())
                .interceptUnkownUrl()
                //创建AgentWeb。
                .createAgentWeb()
                .ready()//设置 WebSettings。
                //WebView载入该url地址的页面并显示。
                .go(getUrl());

        if (MyApp.isDebug()) {
            AgentWebConfig.debug();
        }

        // 得到 AgentWeb 最底层的控件
        addBackgroundChild(mAgentWeb.getWebCreator().getWebParentLayout());

        initView(view);

        // AgentWeb 没有把WebView的功能全面覆盖 ，所以某些设置 AgentWeb 没有提供，请从WebView方面入手设置。
        mAgentWeb.getWebCreator().getWebView().setOverScrollMode(WebView.OVER_SCROLL_NEVER);
    }

    protected IWebLayout getWebLayout() {
        return new WebLayout(getActivity());
    }

    protected void initView(View view) {
        mBackImageView = view.findViewById(R.id.iv_back);
        mLineView = view.findViewById(R.id.view_line);
        mFinishImageView = view.findViewById(R.id.iv_finish);
        mTitleTextView = view.findViewById(R.id.toolbar_title);
        mBackImageView.setOnClickListener(mOnClickListener);
        mFinishImageView.setOnClickListener(mOnClickListener);
        mMoreImageView = view.findViewById(R.id.iv_more);
        mMoreImageView.setOnClickListener(mOnClickListener);
        pageNavigator(View.GONE);
    }

    protected void addBackgroundChild(FrameLayout frameLayout) {
        TextView textView = new TextView(frameLayout.getContext());
        textView.setText("技术由 AgentWeb 提供");
        textView.setTextSize(16);
        textView.setTextColor(Color.parseColor("#727779"));
        frameLayout.setBackgroundColor(Color.parseColor("#272b2d"));
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(-2, -2);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        final float scale = frameLayout.getContext().getResources().getDisplayMetrics().density;
        params.topMargin = (int) (15 * scale + 0.5f);
        frameLayout.addView(textView, 0, params);
    }


    private void pageNavigator(int tag) {
        mBackImageView.setVisibility(tag);
        mLineView.setVisibility(tag);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_back:
                    // true表示AgentWeb处理了该事件
                    if (!mAgentWeb.back()) {
                        AgentWebFragment.this.getActivity().finish();
                    }
                    break;
                case R.id.iv_finish:
                    AgentWebFragment.this.getActivity().finish();
                    break;
                case R.id.iv_more:
                    showPoPup(v);
                    break;
                default:
                    break;

            }
        }

    };

    //========================================//

    /**
     * 权限申请拦截器
     */
    protected PermissionInterceptor mPermissionInterceptor = new PermissionInterceptor() {
        /**
         * PermissionInterceptor 能达到 url1 允许授权， url2 拒绝授权的效果。
         * @param url
         * @param permissions
         * @param action
         * @return true 该Url对应页面请求权限进行拦截 ，false 表示不拦截。
         */
        @Override
        public boolean intercept(String url, String[] permissions, String action) {
            Log.i(TAG, "mUrl:" + url + "  permission:" + JsonUtil.toJson(permissions) + " action:" + action);
            return false;
        }
    };

    //=====================下载============================//

    /**
     * 更新于 AgentWeb 4.0.0，下载监听
     */
    protected DownloadListenerAdapter mDownloadListenerAdapter = new DownloadListenerAdapter() {
        /**
         *
         * @param url                下载链接
         * @param userAgent          UserAgent
         * @param contentDisposition ContentDisposition
         * @param mimetype           资源的媒体类型
         * @param contentLength      文件长度
         * @param extra              下载配置 ， 用户可以通过 Extra 修改下载icon ， 关闭进度条 ， 是否强制下载。
         * @return true 表示用户处理了该下载事件 ， false 交给 AgentWeb 下载
         */
        @Override
        public boolean onStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength, AgentWebDownloader.Extra extra) {
            LogUtils.i(TAG, "onStart:" + url);
            // 是否开启断点续传
            extra.setOpenBreakPointDownload(true)
                    //下载通知的icon
                    .setIcon(R.drawable.ic_file_download_black_24dp)
                    // 连接的超时时间
                    .setConnectTimeOut(6000)
                    // 以8KB位单位，默认60s ，如果60s内无法从网络流中读满8KB数据，则抛出异常
                    .setBlockMaxTime(10 * 60 * 1000)
                    // 下载的超时时间
                    .setDownloadTimeOut(Long.MAX_VALUE)
                    // 串行下载更节省资源哦
                    .setParallelDownload(false)
                    // false 关闭进度通知
                    .setEnableIndicator(true)
                    // 自定义请求头
                    .addHeader("Cookie", "xx")
                    // 下载完成自动打开
                    .setAutoOpen(true)
                    // 强制下载，不管网络网络类型
                    .setForceDownload(true);
            return false;
        }

        /**
         *
         * 不需要暂停或者停止下载该方法可以不必实现
         * @param url
         * @param downloadingService  用户可以通过 DownloadingService#shutdownNow 终止下载
         */
        @Override
        public void onBindService(String url, DownloadingService downloadingService) {
            super.onBindService(url, downloadingService);
            mDownloadingService = downloadingService;
            LogUtils.i(TAG, "onBindService:" + url + "  DownloadingService:" + downloadingService);
        }

        /**
         * 回调onUnbindService方法，让用户释放掉 DownloadingService。
         * @param url
         * @param downloadingService
         */
        @Override
        public void onUnbindService(String url, DownloadingService downloadingService) {
            super.onUnbindService(url, downloadingService);
            mDownloadingService = null;
            LogUtils.i(TAG, "onUnbindService:" + url);
        }

        /**
         *
         * @param url  下载链接
         * @param loaded  已经下载的长度
         * @param length    文件的总大小
         * @param usedTime   耗时 ，单位ms
         * 注意该方法回调在子线程 ，线程名 AsyncTask #XX 或者 AgentWeb # XX
         */
        @Override
        public void onProgress(String url, long loaded, long length, long usedTime) {
            int mProgress = (int) ((loaded) / Float.valueOf(length) * 100);
            LogUtils.i(TAG, "onProgress:" + mProgress);
            super.onProgress(url, loaded, length, usedTime);
        }

        /**
         *
         * @param path 文件的绝对路径
         * @param url  下载地址
         * @param throwable    如果异常，返回给用户异常
         * @return true 表示用户处理了下载完成后续的事件 ，false 默认交给AgentWeb 处理
         */
        @Override
        public boolean onResult(String path, String url, Throwable throwable) {
            //下载成功
            if (null == throwable) {
                //do you work
            } else {//下载失败

            }
            // true  不会发出下载完成的通知 , 或者打开文件
            return false;
        }
    };

    /**
     * @return IAgentWebSettings
     */
    public IAgentWebSettings getSettings() {
        return new AbsAgentWebSettings() {
            private AgentWeb mAgentWeb;

            @Override
            protected void bindAgentWebSupport(AgentWeb agentWeb) {
                this.mAgentWeb = agentWeb;
            }

            /**
             * AgentWeb 4.0.0 内部删除了 DownloadListener 监听 ，以及相关API ，将 Download 部分完全抽离出来独立一个库，
             * 如果你需要使用 AgentWeb Download 部分 ， 请依赖上 compile 'com.just.agentweb:download:4.0.0 ，
             * 如果你需要监听下载结果，请自定义 AgentWebSetting ， New 出 DefaultDownloadImpl，传入DownloadListenerAdapter
             * 实现进度或者结果监听，例如下面这个例子，如果你不需要监听进度，或者下载结果，下面 setDownloader 的例子可以忽略。
             * @param webView
             * @param downloadListener
             * @return WebListenerManager
             */
            @Override
            public WebListenerManager setDownloader(WebView webView, android.webkit.DownloadListener downloadListener) {
                return super.setDownloader(webView,
                        DefaultDownloadImpl
                                .create(getActivity(),
                                        webView,
                                        mDownloadListenerAdapter,
                                        mDownloadListenerAdapter,
                                        this.mAgentWeb.getPermissionInterceptor()));
            }
        };
    }

    //===================WebChromeClient 和 WebViewClient===========================//
    /**
     * 页面空白，请检查scheme是否加上， scheme://host:port/path?query&query 。
     *
     * @return mUrl
     */
    public String getUrl() {
        String target = "";
        Bundle bundle = getArguments();
        if (bundle != null) {
            target = bundle.getString(KEY_URL);
        }

        if (TextUtils.isEmpty(target)) {
            target = "https://github.com/xuexiangjys";
        }
        return target;
    }

    protected WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            Log.i(TAG, "onProgressChanged:" + newProgress + "  view:" + view);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (mTitleTextView != null && !TextUtils.isEmpty(title)) {
                if (title.length() > 10) {
                    title = title.substring(0, 10).concat("...");
                }
                mTitleTextView.setText(title);
            }
        }
    };

    protected WebViewClient mWebViewClient = new WebViewClient() {

        private HashMap<String, Long> timer = new HashMap<>();

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return shouldOverrideUrlLoading(view, request.getUrl() + "");
        }

        @Nullable
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            return super.shouldInterceptRequest(view, request);
        }

        //
        @Override
        public boolean shouldOverrideUrlLoading(final WebView view, String url) {
            //intent:// scheme的处理 如果返回false ， 则交给 DefaultWebClient 处理 ， 默认会打开该Activity  ， 如果Activity不存在则跳到应用市场上去.  true 表示拦截
            //例如优酷视频播放 ，intent://play?...package=com.youku.phone;end;
            //优酷想唤起自己应用播放该视频 ， 下面拦截地址返回 true  则会在应用内 H5 播放 ，禁止优酷唤起播放该视频， 如果返回 false ， DefaultWebClient  会根据intent 协议处理 该地址 ， 首先匹配该应用存不存在 ，如果存在 ， 唤起该应用播放 ， 如果不存在 ， 则跳到应用市场下载该应用 .
            if (url.startsWith("intent://") && url.contains("com.youku.phone")) {
                return true;
            }

            return false;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Log.i(TAG, "mUrl:" + url + " onPageStarted  target:" + getUrl());
            timer.put(url, System.currentTimeMillis());
            if (url.equals(getUrl())) {
                pageNavigator(View.GONE);
            } else {
                pageNavigator(View.VISIBLE);
            }

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            if (timer.get(url) != null) {
                long overTime = System.currentTimeMillis();
                Long startTime = timer.get(url);
                Log.i(TAG, "  page mUrl:" + url + "  used time:" + (overTime - startTime));
            }

        }

        @Override
        public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
            super.onReceivedHttpError(view, request, errorResponse);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
        }
    };


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    //========================菜单功能================================//

    /**
     * 打开浏览器
     *
     * @param targetUrl 外部浏览器打开的地址
     */
    private void openBrowser(String targetUrl) {
        if (TextUtils.isEmpty(targetUrl) || targetUrl.startsWith("file://")) {
            XToastUtils.toast(targetUrl + " 该链接无法使用浏览器打开。");
            return;
        }
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri uri = Uri.parse(targetUrl);
        intent.setData(uri);
        startActivity(intent);
    }


    /**
     * 显示更多菜单
     *
     * @param view 菜单依附在该View下面
     */
    private void showPoPup(View view) {
        if (mPopupMenu == null) {
            mPopupMenu = new PopupMenu(getContext(), view);
            mPopupMenu.inflate(R.menu.menu_toolbar_web);
            mPopupMenu.setOnMenuItemClickListener(mOnMenuItemClickListener);
        }
        mPopupMenu.show();
    }

    /**
     * 菜单事件
     */
    private PopupMenu.OnMenuItemClickListener mOnMenuItemClickListener = new PopupMenu.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.refresh:
                    if (mAgentWeb != null) {
                        mAgentWeb.getUrlLoader().reload(); // 刷新
                    }
                    return true;

                case R.id.copy:
                    if (mAgentWeb != null) {
                        toCopy(getContext(), mAgentWeb.getWebCreator().getWebView().getUrl());
                    }
                    return true;
                case R.id.default_browser:
                    if (mAgentWeb != null) {
                        openBrowser(mAgentWeb.getWebCreator().getWebView().getUrl());
                    }
                    return true;
                case R.id.share:
                    if (mAgentWeb != null) {
                        shareWebUrl(mAgentWeb.getWebCreator().getWebView().getUrl());
                    }
                    return true;
                default:
                    return false;
            }

        }
    };

    /**
     * 分享网页链接
     *
     * @param url 网页链接
     */
    private void shareWebUrl(String url) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, url);
        shareIntent.setType("text/plain");
        //设置分享列表的标题，并且每次都显示分享列表
        startActivity(Intent.createChooser(shareIntent, "分享到"));
    }


    /**
     * 复制字符串
     *
     * @param context
     * @param text
     */
    private void toCopy(Context context, String text) {
        ClipboardManager manager = (ClipboardManager) context.getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
        if (manager == null) {
            return;
        }
        manager.setPrimaryClip(ClipData.newPlainText(null, text));
    }

    //===================生命周期管理===========================//

    @Override
    public void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();//恢复
        super.onResume();
    }

    @Override
    public void onPause() {
        mAgentWeb.getWebLifeCycle().onPause(); //暂停应用内所有WebView ， 调用mWebView.resumeTimers();/mAgentWeb.getWebLifeCycle().onResume(); 恢复。
        super.onPause();
    }

    @Override
    public boolean onFragmentKeyDown(int keyCode, KeyEvent event) {
        return mAgentWeb.handleKeyEvent(keyCode, event);
    }

    @Override
    public void onDestroyView() {
        mAgentWeb.getWebLifeCycle().onDestroy();
        super.onDestroyView();
    }

    //===================中间键===========================//


    /**
     * MiddlewareWebClientBase 是 AgentWeb 3.0.0 提供一个强大的功能，
     * 如果用户需要使用 AgentWeb 提供的功能， 不想重写 WebClientView方
     * 法覆盖AgentWeb提供的功能，那么 MiddlewareWebClientBase 是一个
     * 不错的选择 。
     *
     * @return
     */
    protected MiddlewareWebClientBase getMiddlewareWebClient() {
        return new MiddlewareWebViewClient() {
            /**
             *
             * @param view
             * @param url
             * @return
             */
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 拦截 url，不执行 DefaultWebClient#shouldOverrideUrlLoading
                if (url.startsWith("agentweb")) {
                    Log.i(TAG, "agentweb scheme ~");
                    return true;
                }
                // 执行 DefaultWebClient#shouldOverrideUrlLoading
                if (super.shouldOverrideUrlLoading(view, url)) {
                    return true;
                }
                // do you work
                return false;
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }
        };
    }

    protected MiddlewareWebChromeBase getMiddlewareWebChrome() {
        return new MiddlewareChromeClient() {
        };
    }
}
