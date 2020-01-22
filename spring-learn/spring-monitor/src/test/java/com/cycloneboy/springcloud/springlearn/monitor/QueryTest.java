package com.cycloneboy.springcloud.springlearn.monitor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cycloneboy.springcloud.springlearn.monitor.opentsdb.http.ExpectResponse;
import com.cycloneboy.springcloud.springlearn.monitor.opentsdb.http.HttpClientImpl;
import com.cycloneboy.springcloud.springlearn.monitor.opentsdb.request.QueryBuilder;
import com.cycloneboy.springcloud.springlearn.monitor.opentsdb.request.SubQueries;
import com.cycloneboy.springcloud.springlearn.monitor.opentsdb.response.SimpleHttpResponse;
import com.cycloneboy.springcloud.springlearn.monitor.opentsdb.util.Aggregator;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import org.junit.Test;

/**
 * Created by shifeng on 2016/5/19. MyProject
 */
public class QueryTest {

    @Test
    public void queryTest() throws IOException {
        HttpClientImpl client = new HttpClientImpl("http://localhost:4399");

        QueryBuilder builder = QueryBuilder.getInstance();
        SubQueries subQueries = new SubQueries();
        String zimsum = Aggregator.zimsum.toString();
        subQueries.addMetric("metric_fund").addTag("product", "HQD").addAggregator(zimsum)
            .addDownsample("1s-" +
                zimsum);
        long now = new Date().getTime() / 1000;
        builder.getQuery().addStart(126358720).addEnd(now).addSubQuery(subQueries);
        System.out.println(builder.build());

        try {
            SimpleHttpResponse response = client.pushQueries(builder,
                ExpectResponse.STATUS_CODE);
            String content = response.getContent();
            int statusCode = response.getStatusCode();
            if (statusCode == 200) {
                JSONArray jsonArray = JSON.parseArray(content);
                for (Object object : jsonArray) {
                    JSONObject json = (JSONObject) JSON.toJSON(object);
                    String dps = json.getString("dps");
                    Map<String, String> map = JSON.parseObject(dps, Map.class);
                    for (Map.Entry entry : map.entrySet()) {
                        System.out.println("Time:" + entry.getKey() + ",Value:" + entry.getValue());
                        Double.parseDouble(String.valueOf(entry.getValue()));
                    }
                }
            }
//            System.out.println(jsonArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
