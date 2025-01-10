package com.example.export.domain;

/**
 * @author Mr.Lan
 * @version 1.0
 * @ClassName Result$
 * @description TODO
 * @date 2024/4/3 12:28
 **/

/**
 * 通用的结果工具类，用于创建成功或失败的结果对象，并提供多个重载的方法。
 */
public class Result<T> {
    private String code;
    private String msg;
    private T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Result() {
    }

    public Result(T data) {
        this.data = data;
    }

    public static Result success() {
        Result result = new Result<>();
        result.setCode("200");
        result.setMsg("成功");
        return result;
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>(data);
        result.setCode("200");
        result.setMsg("成功");
        return result;
    }


    public static <T> Result<T> success(String mesg) {
        Result<T> result = new Result<>();
        result.setCode("200");
        result.setMsg("mesg");
        return result;
    }

    public static <T> Result<T> success(T data,String mesg) {
        Result<T> result = new Result<>(data);
        result.setCode("200");
        result.setMsg(mesg);
        return result;
    }

    public static Result error(String code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    public static Result error( String msg) {
        return error("500",msg);
    }


}

