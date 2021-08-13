package com.zhengcheng.leaf.service;

import com.sankuai.inf.leaf.IDGen;
import com.sankuai.inf.leaf.common.Result;
import com.sankuai.inf.leaf.common.ZeroIDGen;
import com.sankuai.inf.leaf.snowflake.SnowflakeIDGenImpl;
import com.zhengcheng.leaf.exception.InitException;
import com.zhengcheng.leaf.properties.LeafProperties;
import com.zhengcheng.leaf.properties.LeafSnowflakeProperties;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SnowflakeService {

    private IDGen idGen;

    public SnowflakeService(LeafSnowflakeProperties leafSnowflakeProperties, LeafProperties leafProperties) throws InitException {
        if (leafSnowflakeProperties.isEnable()) {
            idGen = new SnowflakeIDGenImpl(leafSnowflakeProperties.getZkAddress(), leafSnowflakeProperties.getPort(), leafProperties.getName());
            if (idGen.init()) {
                log.info("Snowflake Service Init Successfully");
            } else {
                throw new InitException("Snowflake Service Init Fail");
            }
        } else {
            idGen = new ZeroIDGen();
            log.info("Zero ID Gen Service Init Successfully");
        }
    }

    public Result getId(String key) {
        return idGen.get(key);
    }
}
