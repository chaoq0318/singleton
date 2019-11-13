/*
 * Copyright 2019 VMware, Inc.
 * SPDX-License-Identifier: EPL-2.0
 */
package com.vmware.vip.common.utils;

import org.apache.commons.lang3.StringUtils;


public enum CategoriesEnum {
    DATES(0, "dates"),
    NUMBERS(1, "numbers"),
    PLURALS(2, "plurals"),
    MEASUREMENTS(3, "measurements"),
    CURRENCIES(4, "currencies"),
    DATE_FIELDS(5, "dateFields");

    private Integer type;

    private String text;

    CategoriesEnum(Integer type, String text) {
        this.type = type;
        this.text = text;
    }

    public Integer getType() {
        return type;
    }

    public String getText() {
        return text;
    }

    public static CategoriesEnum getCategoriesEnumByText(String text){
        if (!StringUtils.isEmpty(text)){
            for(CategoriesEnum categoriesEnum : CategoriesEnum.values()){
                if(text.toUpperCase().equals(categoriesEnum.text.toUpperCase())){
                    return categoriesEnum;
                }
            }
        }
        return null;
    }

}
