/*
 * Copyright (C) 2020 xuexiangjys(xuexiangjys@163.com)
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

package com.xuexiang.templateproject.utils.update;

import com.xuexiang.xupdate.entity.UpdateEntity;
import com.xuexiang.xupdate.proxy.impl.AbstractUpdateParser;

/**
 * 版本更新信息自定义json解析器
 * 具体使用参见： https://github.com/xuexiangjys/XUpdate/wiki/%E9%AB%98%E9%98%B6%E4%BD%BF%E7%94%A8#%E8%87%AA%E5%AE%9A%E4%B9%89%E7%89%88%E6%9C%AC%E6%9B%B4%E6%96%B0%E8%A7%A3%E6%9E%90%E5%99%A8
 *
 * @author xuexiang
 * @since 2020-02-18 13:01
 */
public class CustomUpdateParser extends AbstractUpdateParser {

    @Override
    public UpdateEntity parseJson(String json) throws Exception {
        // TODO: 2020-02-18 这里填写你需要自定义的json格式，如果使用默认的API就不需要设置
        return null;
    }

}
