package com.xuexiang.templateproject.activity;

import android.os.Bundle;

import com.xuexiang.templateproject.core.BaseActivity;
import com.xuexiang.templateproject.fragment.MainFragment;
import com.xuexiang.xpage.base.XPageActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        openPage(MainFragment.class);
    }
}
