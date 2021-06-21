package com.zhengcheng.common.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 操作人信息
 *
 * @author quansheng1.zhang
 * @since 2020/12/3 15:51
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserCommand implements Serializable {

    private static final long serialVersionUID = -2912920982428261794L;
    /**
     * 更新人ID
     */
    private Long updateUserId;
    /**
     * 更新人姓名
     */
    private String updateUserName;
}
