package com.onlineshopping.common.utils;





import java.util.HashMap;


public class Result<T> extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public static Result error(BizCodeEnum bizCodeEnum) {
        Result error = error(bizCodeEnum.getCode(), bizCodeEnum.getMsg());
        return error;
    }

    public static Result error(int code, String msg) {
        Result error = new Result();
        error.put("code", code);
        error.put("msg", msg);
        return error;
    }


    public Result put(String key, Object value) {
        super.put(key, value);
        return this;
    }
    public static Result r(int code, String msg) {
        Result r = new Result();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public Result setData(Object data){
        put("data",data);
        return this;
    }

    public Result() {
        put("code", 0);
        put("msg", "success");
    }

    public static Result ok() {
        return new Result();
    }

    public Integer getCode() {

        return (Integer) this.get("code");
    }

}