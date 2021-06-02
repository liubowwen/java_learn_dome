import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.apache.http.HttpHost;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.cluster.snapshots.delete.DeleteSnapshotRequestBuilder;
import org.elasticsearch.action.delete.DeleteAction;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.security.user.User;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.index.reindex.DeleteByQueryRequestBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author LBW
 * @Classname Test1
 * @Description TODO
 * @Date 2021/6/1 17:08
 */
public class Test1 {
    private static final RestHighLevelClient client;

    static {
        client = new RestHighLevelClient(
                RestClient.builder(
                      new HttpHost("10.200.0.102", 9200, "http")
//                        new HttpHost("localhost", 9200, "http")
                ));
    }

    /**
     * 测试存储 插入一条数据
     */
    @Test
    public void testIndexData() throws IOException {

        IndexRequest indexRequest = new IndexRequest("test");
        indexRequest.id("4");
        User user = new User();
        user.setAge(12);
        user.setUserName("刘博文");
        user.setGender("男");
        String jsonString = JSONObject.toJSONString(user);
//        必须指定XContentType
        indexRequest.source(jsonString, XContentType.JSON);
        // 执行
        IndexResponse response = client.index(indexRequest, RequestOptions.DEFAULT);
        // 获取响应数据

    }

    /**
     * 查找一条数据
     * @throws IOException
     */
    @Test
    public void testSearch() throws IOException {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("test");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        searchSourceBuilder.query(QueryBuilders.existsQuery("刘博文"));
        searchRequest.source(searchSourceBuilder);
        SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);
        Arrays.toString(search.getHits().getHits());
        System.out.println(search);
        List<SearchHit> searchHits = Arrays.asList(search.getHits().getHits());
        for (SearchHit searchHit : searchHits) {
            User user = JSON.parseObject(searchHit.getSourceAsString(), User.class);
            System.out.println(user);
        }
    }
    @Test
    public  void delete() throws IOException {
        DeleteByQueryRequest deleteByQueryRequest=new DeleteByQueryRequest("test");
        deleteByQueryRequest.setQuery(QueryBuilders.termQuery("age","121"));
        client.deleteByQuery(deleteByQueryRequest,RequestOptions.DEFAULT);
    }

    @Data
   public static class  User {
        private Long id;
        private String userName;
        private String gender;
        private Integer age;
    }


}
