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

package com.xuexiang.templateproject.fragment;

import android.view.KeyEvent;

import com.xuexiang.templateproject.core.BaseContainerFragment;
import com.xuexiang.templateproject.utils.ContinuousClickUtils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xui.widget.actionbar.TitleBar;

/**
 * @author xuexiang
 * @since 2018/11/7 下午1:16
 */
@Page(name = "模版程序", anim = CoreAnim.none)
public class MainFragment extends BaseContainerFragment {

    @Override
    protected Class[] getPagesClasses() {
        return new Class[] {
                //此处填写fragment

        };
    }

    @Override
    protected TitleBar initTitle() {
        return super.initTitle().setLeftClickListener(view -> ContinuousClickUtils.exitBy2Click());
    }


    /**
     * 菜单、返回键响应
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            ContinuousClickUtils.exitBy2Click();
        }
        return true;
    }

}
