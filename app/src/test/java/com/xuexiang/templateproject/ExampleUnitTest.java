package com.xuexiang.templateproject;

import com.xuexiang.templateproject.core.http.entity.TipInfo;
import com.xuexiang.xhttp2.model.ApiResult;
import com.xuexiang.xutil.net.JsonUtil;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);


        TipInfo info = new TipInfo();
        info.setTitle("微信公众号");
        info.setContent("获取更多资讯，欢迎关注我的微信公众号：【我的Android开源之旅】");
        List<TipInfo> list = new ArrayList<>();
        for (int i = 0; i <5 ; i++) {
            list.add(info);
        }
        ApiResult<List<TipInfo>> result = new ApiResult<>();
        result.setData(list);
        System.out.println(JsonUtil.toJson(result));
    }
}