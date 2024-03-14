package com.zhengcheng.file.common.exception.file;


import com.zhengcheng.common.exception.BizException;

/**
 * 文件信息异常类
 */
public class FileException extends BizException {
    private static final long serialVersionUID = 1L;

    public FileException(String msg, Object[] args, String defaultMessage) {
        super("file", msg, args, defaultMessage);
    }

}
