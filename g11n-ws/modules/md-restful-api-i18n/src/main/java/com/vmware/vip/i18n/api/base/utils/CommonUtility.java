/*
 * Copyright 2019 VMware, Inc.
 * SPDX-License-Identifier: EPL-2.0
 */
package com.vmware.vip.i18n.api.base.utils;

import com.vmware.i18n.utils.CommonUtil;
import com.vmware.vip.common.utils.CategoriesEnum;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CommonUtility {
  private CommonUtility() {}
    /**
     * Check param
     * @param categories
     * @return
     */
    public static boolean checkParams(List<String> categories, String... args){
        for (String cat : categories) {
            if (StringUtils.isEmpty(cat)) {
                return false;
            }
        }

        for (String param : args){
            if (CommonUtil.isEmpty(param)){
                return false;
            }
        }

        return true;
    }

    /**
     * Get categories according to CategoriesEnum
     * @param scope
     * @return
     */
    public static List<String> getCategories(String scope){
        String[] categories = scope.split(",");
        List<String> catList = new ArrayList<>();
        for (String cat : categories) {
            CategoriesEnum categoriesEnum = CategoriesEnum.getCategoriesEnumByText(cat);
            if (categoriesEnum == null) {
                catList.add(null);
            } else {
                catList.add(categoriesEnum.getText());
            }
        }
        return catList;
    }

}
