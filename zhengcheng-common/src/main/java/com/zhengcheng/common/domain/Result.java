package com.zhengcheng.common.domain;

import com.zhengcheng.common.enums.CodeEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * Result
 *
 * @author :    quansheng.zhang
 * @date :    2019/2/28 21:00
 */
public class Result extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public Result setData(Object data) {
        put("data", data);
        return this;
    }

    public Result() {
        put("code", CodeEnum.SUCCESS.getCode());
        put("msg", CodeEnum.SUCCESS.getMessage());
    }

    public static Result error() {
        return error(CodeEnum.INTERNAL_SERVER_ERROR.getCode(), "未知异常，请联系管理员");
    }

    public static Result error(String msg) {
        return error(CodeEnum.ERROR.getCode(), msg);
    }

    public static Result errorMessage(String msg) {
        return error(CodeEnum.ERROR.getCode(), msg);
    }

    public static Result create(int code, String msg) {
        return error(code, msg);
    }

    public static Result error(int code, String msg) {
        Result r = new Result();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static Result ok(String msg) {
        Result r = new Result();
        r.put("msg", msg);
        return r;
    }

    public static Result ok(Object data) {
        Result r = new Result();
        r.put("data", data);
        return r;
    }

    public static Result ok(Map<String, Object> map) {
        Result r = new Result();
        r.putAll(map);
        return r;
    }

    public static Result ok() {
        return new Result();
    }

    public Result put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public Result put(Object value) {
        super.put("data", value);
        return this;
    }

    public Result put(PageResult pageResult) {
        super.put("rows", pageResult.getRows());
        super.put("total", pageResult.getTotal());
        return this;
    }

    public Integer getCode() {
        return (Integer) this.get("code");
    }
}
