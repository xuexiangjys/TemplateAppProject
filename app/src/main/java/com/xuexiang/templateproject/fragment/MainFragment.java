package com.xuexiang.templateproject.fragment;

import android.view.KeyEvent;
import android.view.View;

import com.xuexiang.templateproject.core.BaseContainerFragment;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xutil.common.ClickUtils;

/**
 * @author xuexiang
 * @since 2018/11/7 下午1:16
 */
@Page(name = "模版程序")
public class MainFragment extends BaseContainerFragment {

    @Override
    protected Class[] getPagesClasses() {
        return new Class[] {
                //此处填写fragment

        };
    }

    @Override
    protected TitleBar initTitle() {
        return super.initTitle().setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClickUtils.exitBy2Click();
            }
        });
    }


    /**
     * 菜单、返回键响应
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            ClickUtils.exitBy2Click();
        }
        return true;
    }

}
