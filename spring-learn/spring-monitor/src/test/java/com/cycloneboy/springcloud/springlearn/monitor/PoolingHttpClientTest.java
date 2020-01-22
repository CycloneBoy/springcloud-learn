package com.cycloneboy.springcloud.springlearn.monitor;

import com.cycloneboy.springcloud.springlearn.monitor.opentsdb.builder.MetricBuilder;
import com.cycloneboy.springcloud.springlearn.monitor.opentsdb.http.PoolingHttpClient;
import com.cycloneboy.springcloud.springlearn.monitor.opentsdb.response.SimpleHttpResponse;
import java.io.IOException;
import org.junit.Test;


public class PoolingHttpClientTest {

  @Test
  public void test_postJson_DefaultRetries() throws InterruptedException {
    PoolingHttpClient client = new PoolingHttpClient();

    try {
      for (int i = 0; i < 10; i++) {
        MetricBuilder builder = MetricBuilder.getInstance();

        builder.addMetric("metric" + i).setDataPoint(2, 30L)
            .addTag("tag1", "tab1value")
            .addTag("tag2", "tab2value");

        SimpleHttpResponse response = client.doPost(
            "http://localhost:4399/api/put/?details",
            builder.build());
        System.out.println(response.getStatusCode());
        System.out.println(response.getContent());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}