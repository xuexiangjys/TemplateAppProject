package com.xuexiang.templateproject.utils;


/**
 * SharedPreferences管理工具基类
 *
 * @author xuexiang
 * @since 2018/11/27 下午5:16
 */
public final class SettingUtils {

    private SettingUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    private static final String IS_FIRST_OPEN_KEY = "is_first_open_key";

    private static final String IS_AGREE_PRIVACY_KEY = "is_agree_privacy_key";

    /**
     * 是否是第一次启动
     */
    public static boolean isFirstOpen() {
        return MMKVUtils.getBoolean(IS_FIRST_OPEN_KEY, true);
    }

    /**
     * 设置是否是第一次启动
     */
    public static void setIsFirstOpen(boolean isFirstOpen) {
        MMKVUtils.put(IS_FIRST_OPEN_KEY, isFirstOpen);
    }

    /**
     * 获取是否同意隐私政策
     *
     * @return 是否同意隐私政策
     */
    public static boolean isAgreePrivacy() {
        return MMKVUtils.getBoolean(IS_AGREE_PRIVACY_KEY, false);
    }

    /**
     * 设置是否同意隐私政策
     *
     * @param isAgreePrivacy 是否同意隐私政策
     */
    public static void setIsAgreePrivacy(boolean isAgreePrivacy) {
        MMKVUtils.put(IS_AGREE_PRIVACY_KEY, isAgreePrivacy);
    }


}
