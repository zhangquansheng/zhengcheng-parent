package com.zhengcheng.data.elasticsearch.test;

import com.zhengcheng.common.web.PageCommand;
import com.zhengcheng.common.web.PageInfo;
import com.zhengcheng.data.elasticsearch.repository.ElasticsearchIndex;
import com.zhengcheng.data.elasticsearch.repository.ElasticsearchTemplate;
import com.zhengcheng.data.elasticsearch.test.entity.HouseDocument;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * HouseDocumentTest
 *
 * @author quansheng1.zhang
 * @since 2021/6/16 16:51
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class HouseDocumentTest {

    @Autowired
    private ElasticsearchTemplate<HouseDocument> elasticsearchTemplate;
    @Autowired
    private ElasticsearchIndex<HouseDocument> elasticsearchIndex;

    @Test
    public void deleteIndex() throws Exception {
        elasticsearchIndex.delete(HouseDocument.class);
    }

    @Test
    public void existsIndex() throws Exception {
        elasticsearchIndex.exists(HouseDocument.class);
    }

    @Test
    public void list() throws Exception {
        elasticsearchTemplate.list(HouseDocument.class);
    }

    @Test
    public void delete() throws Exception {
        HouseDocument houseDocument = new HouseDocument();
        houseDocument.setId("19932");
        elasticsearchTemplate.delete(houseDocument);
    }

    @Test
    public void page() throws Exception {
        PageCommand pageCommand = new PageCommand();
        pageCommand.setPageNum(1);
        pageCommand.setPageSize(100);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        // sourceBuilder.query(QueryBuilders.termQuery("lpArea", "新站区"));
        sourceBuilder.query(QueryBuilders.matchPhraseQuery("lpLocation", "金水湾"));
        PageInfo<HouseDocument> pageInfo = elasticsearchTemplate.page(sourceBuilder, pageCommand, HouseDocument.class);
        Assert.assertNotNull(pageInfo);
    }

}
