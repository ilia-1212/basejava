package com.urise.webapp.util;

import com.urise.webapp.model.Organization;

public class WebUtil {
    public static boolean isEmpty(String str) {
        if (str == null || str.trim().length() == 0 ) {
            return  true;
        }
        return false;
    }

    public static String PeriodDatesPosition(Organization.Position position) {
            return DateUtil.toHtml(position.getStartDate()) + " --- " + DateUtil.toHtml(position.getEndDate());
    }
}
