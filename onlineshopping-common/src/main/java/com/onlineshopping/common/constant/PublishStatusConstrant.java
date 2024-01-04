package com.onlineshopping.common.constant;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
public enum PublishStatusConstrant {

    NEWbUILE(0,"新建"),
    GROUDING(1,"上架"),
    OFFSHELT(2,"下架");

    private int code;
    private String ms;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMs() {
        return ms;
    }

    public void setMs(String ms) {
        this.ms = ms;
    }
}
