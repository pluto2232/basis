package com.ssm.client;


/**
 * @author cheny
 * @date 2023年03月14日 22:53
 */
public enum ContentType {

    FORM("application/x-www-from-urlencoded"),
    MULTIPART("multipart/form-data"),
    JSON("application/json");

    private final String value;
    private final static String ENCODING = "utf-8";
    ContentType(String value){
        this.value = value + ";charset=" +ENCODING;
    }
    @Override
    public String toString(){
        return value;
    }
}