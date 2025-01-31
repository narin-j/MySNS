package com.na.mysns.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

// 결과 코드값 획일화
// generic 타입
@Getter
@AllArgsConstructor
public class Response<T> {
    private String resultCode;
    private T result;

    public static Response<Void> error(String errorCode){
        return new Response<>(errorCode, null);
    }

    public static <T> Response<T> success() {
        return new Response<T>("SUCCESS", null);
    }

    public static <T> Response<T> success(T result) {
        return new Response<T>("SUCCESS", result);
    }

    public String toStream(){
        if(result == null){
            return "{"+
                    "\"resultCode\":"+"\""+resultCode+"\","+
                    "\"result\":"+null+"}";
        }
        return "{"+
                "\"resultCode\":"+"\""+resultCode+"\","+
                "\"result\":"+result+"\""+"}";
    }

}
