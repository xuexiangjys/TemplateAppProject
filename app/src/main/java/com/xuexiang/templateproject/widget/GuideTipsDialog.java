package com.xuexiang.templateproject.widget;

import android.content.Context;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;

import com.xuexiang.constant.TimeConstants;
import com.xuexiang.templateproject.R;
import com.xuexiang.templateproject.core.http.api.ApiService;
import com.xuexiang.templateproject.core.http.callback.NoTipCallBack;
import com.xuexiang.templateproject.core.http.entity.TipInfo;
import com.xuexiang.templateproject.utils.MMKVUtils;
import com.xuexiang.xaop.annotation.SingleClick;
import com.xuexiang.xhttp2.XHttp;
import com.xuexiang.xhttp2.cache.model.CacheMode;
import com.xuexiang.xhttp2.request.CustomRequest;
import com.xuexiang.xui.widget.dialog.BaseDialog;
import com.xuexiang.xutil.app.AppUtils;
import com.zzhoujay.richtext.RichText;

import java.util.List;

/**
 * 小贴士弹窗
 *
 * @author xuexiang
 * @since 2019-08-22 17:02
 */
public class GuideTipsDialog extends BaseDialog implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private static final String KEY_IS_IGNORE_TIPS = "com.xuexiang.templateproject.widget.key_is_ignore_tips_";

    private List<TipInfo> mTips;
    private int mIndex = -1;

    private TextView mTvPrevious;
    private TextView mTvNext;

    private TextView mTvTitle;
    private TextView mTvContent;

    /**
     * 显示提示
     *
     * @param context 上下文
     */
    public static void showTips(final Context context) {
        if (!isIgnoreTips()) {
            showTipsForce(context);
        }
    }

    /**
     * 强制显示提示
     *
     * @param context 上下文
     */
    public static void showTipsForce(Context context) {
        CustomRequest request = XHttp.custom().cacheMode(CacheMode.FIRST_CACHE).cacheTime(TimeConstants.DAY).cacheKey("getTips");
        request.apiCall(request.create(ApiService.IGetService.class).getTips(), new NoTipCallBack<List<TipInfo>>() {
            @Override
            public void onSuccess(List<TipInfo> response) throws Throwable {
                if (response != null && response.size() > 0) {
                    new GuideTipsDialog(context, response).show();
                }
            }
        });
    }

    public GuideTipsDialog(Context context, @NonNull List<TipInfo> tips) {
        super(context, R.layout.dialog_guide_tips);
        initViews();
        updateTips(tips);
    }

    /**
     * 初始化弹窗
     */
    private void initViews() {
        mTvTitle = findViewById(R.id.tv_title);
        mTvContent = findViewById(R.id.tv_content);
        AppCompatCheckBox cbIgnore = findViewById(R.id.cb_ignore);
        ImageView ivClose = findViewById(R.id.iv_close);

        mTvPrevious = findViewById(R.id.tv_previous);
        mTvNext = findViewById(R.id.tv_next);

        if (cbIgnore != null) {
            cbIgnore.setChecked(isIgnoreTips());
            cbIgnore.setOnCheckedChangeListener(this);
        }
        if (ivClose != null) {
            ivClose.setOnClickListener(this);
        }
        mTvPrevious.setOnClickListener(this);
        mTvNext.setOnClickListener(this);
        mTvPrevious.setEnabled(false);
        mTvNext.setEnabled(true);
        setCancelable(false);
        setCanceledOnTouchOutside(true);
    }

    /**
     * 更新提示信息
     *
     * @param tips 提示信息
     */
    private void updateTips(List<TipInfo> tips) {
        mTips = tips;
        if (mTips != null && mTips.size() > 0 && mTvContent != null) {
            mIndex = 0;
            showRichText(mTips.get(mIndex));
        }
    }

    /**
     * 切换提示信息
     *
     * @param index 索引
     */
    private void switchTipInfo(int index) {
        if (mTips != null && mTips.size() > 0 && mTvContent != null) {
            if (index >= 0 && index <= mTips.size() - 1) {
                showRichText(mTips.get(index));
                if (index == 0) {
                    mTvPrevious.setEnabled(false);
                    mTvNext.setEnabled(true);
                } else if (index == mTips.size() - 1) {
                    mTvPrevious.setEnabled(true);
                    mTvNext.setEnabled(false);
                } else {
                    mTvPrevious.setEnabled(true);
                    mTvNext.setEnabled(true);
                }
            }
        }
    }

    /**
     * 显示富文本
     *
     * @param tipInfo 提示信息
     */
    private void showRichText(TipInfo tipInfo) {
        mTvTitle.setText(tipInfo.getTitle());
        RichText.fromHtml(tipInfo.getContent())
                .bind(this)
                .into(mTvContent);
    }


    @SingleClick(300)
    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iv_close) {
            dismiss();
        } else if (id == R.id.tv_previous) {
            if (mIndex > 0) {
                mIndex--;
                switchTipInfo(mIndex);
            }
        } else if (id == R.id.tv_next) {
            if (mIndex < mTips.size() - 1) {
                mIndex++;
                switchTipInfo(mIndex);
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        setIsIgnoreTips(isChecked);
    }

    @Override
    public void onDetachedFromWindow() {
        RichText.clear(this);
        super.onDetachedFromWindow();
    }


    public static boolean setIsIgnoreTips(boolean isIgnore) {
        return MMKVUtils.put(KEY_IS_IGNORE_TIPS + AppUtils.getAppVersionCode(), isIgnore);
    }

    public static boolean isIgnoreTips() {
        return MMKVUtils.getBoolean(KEY_IS_IGNORE_TIPS + AppUtils.getAppVersionCode(), false);
    }

}
