package com.xuexiang.templateproject.core.http.entity;

import androidx.annotation.Keep;

/**
 * @author xuexiang
 * @since 2019-08-28 15:35
 */
@Keep
public class TipInfo {

    /**
     * title : 小贴士3
     * content : <p style=";font-family:'Microsoft YaHei';font-size:15px">欢迎关注我的微信公众号：我的Android开源之旅。</p><p><br/></p>
     */

    private String title;
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "TipInfo{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
