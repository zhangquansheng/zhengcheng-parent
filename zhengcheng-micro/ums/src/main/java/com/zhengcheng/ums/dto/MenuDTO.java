package com.zhengcheng.ums.dto;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 菜单
 *
 * @author quansheng1.zhang
 * @since 2022/4/29 18:42
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuDTO implements Serializable {
    private static final long serialVersionUID = -6710058468393911152L;

    private Long              id;
    private String            title;
    private String            icon;
    private Integer           type;
    private String            openType;
    private String            href;
    private List<MenuDTO>     children;
}
