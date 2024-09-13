package com.ydlclass.ydlEnum;

/**
 * @author itnanls(微信)
 * 我们的服务： 一路陪跑，顺利就业
 */
public enum DeleteFlagEnum {

    // yes代表逻辑上已经删除，no代表逻辑上没有删除
    YES("1"),NO("0");

    private String value;

    DeleteFlagEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
