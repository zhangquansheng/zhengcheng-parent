package com.zhengcheng.ums.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * TokenInfoDTO
 *
 * @author quansheng1.zhang
 * @since 2022/3/10 10:35
 */
@Data
public class TokenInfoDTO implements Serializable {

    private static final long serialVersionUID = -2397829211513948729L;

    public String             tokenName;
    public String             tokenValue;
    public Boolean            isLogin;
    public Object             loginId;
    public String             loginType;
    public long               tokenTimeout;
    public long               sessionTimeout;
    public long               tokenSessionTimeout;
    public long               tokenActivityTimeout;
    public String             loginDevice;
    public String             tag;
}
