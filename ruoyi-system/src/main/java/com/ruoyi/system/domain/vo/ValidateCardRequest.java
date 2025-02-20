package com.ruoyi.system.domain.vo;

import com.ruoyi.common.utils.StringUtils;


public class ValidateCardRequest
{
    private String code;

    private String clientIP;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getClientIP() {
        return clientIP;
    }

    public void setClientIP(String clientIP) {
        this.clientIP = clientIP;
    }
}
