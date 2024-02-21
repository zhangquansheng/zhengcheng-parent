package com.zhengcheng.ums.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * RsaKeyPair
 *
 * https://www.hutool.cn/docs/#/crypto/%E9%9D%9E%E5%AF%B9%E7%A7%B0%E5%8A%A0%E5%AF%86-AsymmetricCrypto?id=%E9%9D%9E%E5%AF%B9%E7%A7%B0%E5%8A%A0%E5%AF%86-asymmetriccrypto
 * 
 * @author quansheng1.zhang
 * @since 2022/5/13 17:31
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RsaKeyPair implements Serializable {

    private static final long serialVersionUID = 6592090863204490808L;

    @ApiModelProperty("私钥自己拥有，不能给别人并使用Base64.encode方法转为Base64，便于存储为文本")
    private String            privateKeyBase64;
    @ApiModelProperty("公钥公开并使用Base64.encode方法转为Base64，便于存储为文本")
    private String            publicKeyBase64;
}
