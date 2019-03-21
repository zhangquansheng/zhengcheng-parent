package com.zhengcheng.mvc.mybatis;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * BaseController
 *
 * @author :    quansheng.zhang
 * @Package :     com.zhengcheng.mvc.controller
 * @Description :
 * @date :    2019/3/21 18:08
 */
@Data
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 2393269568666085258L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
