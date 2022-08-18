package com.rokai.crm.settings.exception;

public class UserException extends RuntimeException {

    private String code;
    private String mes;

    public UserException(String code, String mes) {
        this.code = code;
        this.mes = mes;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }
}
