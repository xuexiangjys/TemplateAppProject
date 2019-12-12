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

package com.xuexiang.templateproject.utils;


import android.content.Context;
import android.os.Parcelable;

import com.tencent.mmkv.MMKV;

import java.util.Set;

/**
 * MMKV工具类
 *
 * @author xuexiang
 * @since 2019-07-04 10:20
 */
public final class MMKVUtils {

    private MMKVUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    private static MMKV sMMKV;

    /**
     * 初始化
     *
     * @param context
     */
    public static void init(Context context) {
        MMKV.initialize(context.getApplicationContext());
        sMMKV = MMKV.defaultMMKV();
    }

    private static void checkStatus() {
        if (sMMKV == null) {
            sMMKV = MMKV.defaultMMKV();
        }
    }

    //=======================================键值保存==================================================//

    /**
     * 保存键值
     *
     * @param key
     * @param value
     * @return
     */
    public static boolean put(String key, Object value) {
        checkStatus();

        if (value instanceof Integer) {
            return sMMKV.encode(key, (Integer) value);
        } else if (value instanceof Float) {
            return sMMKV.encode(key, (Float) value);
        } else if (value instanceof String) {
            return sMMKV.encode(key, (String) value);
        } else if (value instanceof Boolean) {
            return sMMKV.encode(key, (Boolean) value);
        } else if (value instanceof Long) {
            return sMMKV.encode(key, (Long) value);
        } else if (value instanceof Double) {
            return sMMKV.encode(key, (Double) value);
        } else if (value instanceof Parcelable) {
            return sMMKV.encode(key, (Parcelable) value);
        } else if (value instanceof byte[]) {
            return sMMKV.encode(key, (byte[]) value);
        } else if (value instanceof Set) {
            return sMMKV.encode(key, (Set<String>) value);
        }
        return false;
    }


    //=======================================键值获取==================================================//

    /**
     * 获取键值
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public static Object get(String key, Object defaultValue) {
        checkStatus();

        if (defaultValue instanceof Integer) {
            return sMMKV.decodeInt(key, (Integer) defaultValue);
        } else if (defaultValue instanceof Float) {
            return sMMKV.decodeFloat(key, (Float) defaultValue);
        } else if (defaultValue instanceof String) {
            return sMMKV.decodeString(key, (String) defaultValue);
        } else if (defaultValue instanceof Boolean) {
            return sMMKV.decodeBool(key, (Boolean) defaultValue);
        } else if (defaultValue instanceof Long) {
            return sMMKV.decodeLong(key, (Long) defaultValue);
        } else if (defaultValue instanceof Double) {
            return sMMKV.decodeDouble(key, (Double) defaultValue);
        } else if (defaultValue instanceof byte[]) {
            return sMMKV.decodeBytes(key);
        } else if (defaultValue instanceof Set) {
            return sMMKV.decodeStringSet(key, (Set<String>) defaultValue);
        }
        return null;
    }


    /**
     * 根据key获取boolean值
     *
     * @param key
     * @param defValue
     * @return
     */
    public boolean getBoolean(String key, boolean defValue) {
        checkStatus();

        try {
            return sMMKV.getBoolean(key, defValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defValue;
    }

    /**
     * 根据key获取long值
     *
     * @param key
     * @param defValue
     * @return
     */
    public long getLong(String key, long defValue) {
        checkStatus();

        try {
            return sMMKV.getLong(key, defValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defValue;
    }

    /**
     * 根据key获取float值
     *
     * @param key
     * @param defValue
     * @return
     */
    public float getFloat(String key, float defValue) {
        checkStatus();

        try {
            return sMMKV.getFloat(key, defValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defValue;
    }

    /**
     * 根据key获取String值
     *
     * @param key
     * @param defValue
     * @return
     */
    public String getString(String key, String defValue) {
        checkStatus();

        try {
            return sMMKV.getString(key, defValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defValue;
    }

    /**
     * 根据key获取int值
     *
     * @param key
     * @param defValue
     * @return
     */
    public int getInt(String key, int defValue) {
        checkStatus();

        try {
            return sMMKV.getInt(key, defValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defValue;
    }


    /**
     * 根据key获取double值
     *
     * @param key
     * @param defValue
     * @return
     */
    public double getDouble(String key, double defValue) {
        checkStatus();

        try {
            return sMMKV.decodeDouble(key, defValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defValue;
    }


    /**
     * 获取对象
     *
     * @param key
     * @param tClass 类型
     * @param <T>
     * @return
     */
    public <T extends Parcelable> T getObject(String key, Class<T> tClass) {
        checkStatus();

        return sMMKV.decodeParcelable(key, tClass);
    }

    /**
     * 获取对象
     *
     * @param key
     * @param tClass 类型
     * @param <T>
     * @return
     */
    public <T extends Parcelable> T getObject(String key, Class<T> tClass, T defValue) {
        checkStatus();

        try {
            return sMMKV.decodeParcelable(key, tClass, defValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defValue;
    }


}
