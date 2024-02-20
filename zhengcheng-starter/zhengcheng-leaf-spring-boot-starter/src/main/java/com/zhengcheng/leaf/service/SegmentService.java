package com.zhengcheng.leaf.service;

import com.sankuai.inf.leaf.IDGen;
import com.sankuai.inf.leaf.common.Result;
import com.sankuai.inf.leaf.common.ZeroIDGen;
import com.sankuai.inf.leaf.segment.SegmentIDGenImpl;
import com.sankuai.inf.leaf.segment.dao.IDAllocDao;
import com.sankuai.inf.leaf.segment.dao.impl.IDAllocDaoImpl;
import com.zaxxer.hikari.HikariDataSource;
import com.zhengcheng.leaf.exception.InitException;
import com.zhengcheng.leaf.properties.LeafSegmentProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.jdbc.DataSourceBuilder;

@Slf4j
public class SegmentService {

    private IDGen idGen;
    private HikariDataSource dataSource;

    public SegmentService(LeafSegmentProperties leafSegmentProperties) throws InitException {
        if (leafSegmentProperties.isEnable()) {
            // Config dataSource

            dataSource = DataSourceBuilder.create()
                    .type(HikariDataSource.class)
                    .url(leafSegmentProperties.getJdbc().getUrl())
                    .username(leafSegmentProperties.getJdbc().getUsername())
                    .password(leafSegmentProperties.getJdbc().getPassword())
                    .build();

            // Config Dao
            IDAllocDao dao = new IDAllocDaoImpl(dataSource);

            // Config ID Gen
            idGen = new SegmentIDGenImpl();
            ((SegmentIDGenImpl) idGen).setDao(dao);
            if (idGen.init()) {
                log.info("Segment Service Init Successfully");
            } else {
                throw new InitException("Segment Service Init Fail");
            }
        } else {
            idGen = new ZeroIDGen();
            log.info("Zero ID Gen Service Init Successfully");
        }
    }

    public Result getId(String key) {
        return idGen.get(key);
    }

    public SegmentIDGenImpl getIdGen() {
        if (idGen instanceof SegmentIDGenImpl) {
            return (SegmentIDGenImpl) idGen;
        }
        return null;
    }
}
