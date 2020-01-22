package com.cycloneboy.springcloud.springlearn.monitor.opentsdb.http;

import com.cycloneboy.springcloud.springlearn.monitor.opentsdb.builder.MetricBuilder;
import com.cycloneboy.springcloud.springlearn.monitor.opentsdb.request.QueryBuilder;
import com.cycloneboy.springcloud.springlearn.monitor.opentsdb.response.Response;
import com.cycloneboy.springcloud.springlearn.monitor.opentsdb.response.SimpleHttpResponse;
import java.io.IOException;


public interface Client {

  String PUT_POST_API = "/api/put";

  String QUERY_POST_API = "/api/query";

  /**
   * Sends metrics from the builder to the KairosDB server.
   *
   * @param builder metrics builder
   * @return response from the server
   * @throws IOException problem occurred sending to the server
   */
  Response pushMetrics(MetricBuilder builder) throws IOException;

  SimpleHttpResponse pushQueries(QueryBuilder builder) throws IOException;
}