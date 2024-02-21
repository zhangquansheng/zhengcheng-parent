package com.zhengcheng.ums.dto.command;

import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.AES;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.crypto.spec.SecretKeySpec;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;

/**
 * UserLoginCommand
 *
 * @author quansheng1.zhang
 * @since 2022/7/1 22:02
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserMobileLoginCommand implements Serializable {

    @ApiModelProperty("手机号码")
    @NotBlank(message = "手机号码不能为空")
    private String mobile;
    @ApiModelProperty("验证码")
    @NotBlank(message = "验证码不能为空")
    private String code;

    public String getMobile() {
        String ENCODE_KEY = "1234567812345678";
        AES aes = new AES(Mode.ECB, Padding.PKCS5Padding, new SecretKeySpec(ENCODE_KEY.getBytes(), "AES"));
        byte[] decryptDataBase64 = aes.decrypt(mobile);
        return new String(decryptDataBase64, StandardCharsets.UTF_8);
    }
}
