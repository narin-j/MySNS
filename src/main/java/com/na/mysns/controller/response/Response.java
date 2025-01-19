package com.na.mysns.controller.response;

import lombok.AllArgsConstructor;

// 결과 코드값 획일화
// generic 타입
@AllArgsConstructor
public class Response<T> {
    private String resultCode;
    private T result;

    public static<T> Response<T> error(String errorCode){
        return new Response<>(errorCode, null);
    }

    public static<T> Response<T> success(T result){
        return new Response<>("SUCCESS", result);
    }

}
