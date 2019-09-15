package com.zhengcheng.mq.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 消费消息返回值
 *
 * @author :    quansheng.zhang
 * @date :    2019/9/15 13:35
 */
@Data
public class Result implements Serializable {
    private static final long serialVersionUID = -4590037829013882029L;

    public static final int SUCCESS_CODE = 200;
    public static final int FAIL_CODE = 500;
    public static final Result SUCCESS = new Result();
    public static final Result FAIL = new Result(500);
    private int code;
    private String msg;

    public Result() {
        this.code = 200;
    }

    public Result(int code) {
        this.code = code;
    }

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
