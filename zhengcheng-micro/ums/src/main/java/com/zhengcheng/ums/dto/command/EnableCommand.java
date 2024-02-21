package com.zhengcheng.ums.dto.command;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * EnableCommand
 *
 * @author quansheng1.zhang
 * @since 2022/4/22 11:16
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EnableCommand implements Serializable {
    private static final long serialVersionUID = 4898589400166762941L;

    /**
     * ID
     */
    @NotNull(message = "ID不能为空")
    private Long              id;
    /**
     * 是否开启
     */
    private boolean           enable;
}
