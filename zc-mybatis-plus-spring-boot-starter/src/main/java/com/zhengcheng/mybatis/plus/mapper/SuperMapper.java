package com.zhengcheng.mybatis.plus.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * mapper 父类，注意这个类不要让 mp 扫描到！！
 *
 * @author :    quansheng.zhang
 * @date :    2019/7/28 21:31
 */
public interface SuperMapper<T> extends BaseMapper<T> {
    // 这里可以放一些公共的方法
}
