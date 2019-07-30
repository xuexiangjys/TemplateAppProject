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

import android.os.SystemClock;

import com.xuexiang.xutil.XUtil;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author xuexiang
 * @since 2019-06-06 15:18
 */
public final class ContinuousClickUtils {

    private final static int COUNTS = 5;// 点击次数
    private final static long DURATION = 1000;// 规定有效时间
    private static long[] mHits = new long[COUNTS];

    private ContinuousClickUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 连续点击
     *
     * @param listener
     */
    public static void doClick(OnContinuousClickListener listener) {
        //每次点击时，数组向前移动一位
        System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
        //为数组最后一位赋值
        mHits[mHits.length - 1] = SystemClock.uptimeMillis();
        if (mHits[0] >= (SystemClock.uptimeMillis() - DURATION)) {
            mHits = new long[COUNTS];//重新初始化数组
            if (listener != null) {
                listener.onContinuousClick();
            }
        }
    }

    public interface OnContinuousClickListener {
        void onContinuousClick();
    }

    /**
     * 双击退出函数
     */
    private static boolean sIsExit = false;

    /**
     * 双击返回退出程序
     */
    public static void exitBy2Click() {
        if (!sIsExit) {
            sIsExit = true;
            XToastUtils.info("再按一次退出程序");
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    sIsExit = false;
                }
            }, 2000);
        } else {
            XUtil.get().exitApp();
        }
    }

}
