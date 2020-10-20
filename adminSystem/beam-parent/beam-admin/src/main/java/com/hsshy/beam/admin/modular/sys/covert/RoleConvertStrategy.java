package com.hsshy.beam.admin.modular.sys.covert;

import org.apache.commons.lang.time.DateFormatUtils;

import java.util.Date;

public class RoleConvertStrategy {

    public String booleanToString(Boolean value) {

        if (value == null) {
            return "--";
        }
        return value ? "是" : "否";
    }

    public String dateFormat(Date date) {
        if (date == null) {
            return "--";
        }
        return DateFormatUtils.format(date, "yyyyMMddHHmmss");
    }
}
