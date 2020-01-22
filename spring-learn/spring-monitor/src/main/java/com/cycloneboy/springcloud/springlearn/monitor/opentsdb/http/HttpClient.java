package com.cycloneboy.springcloud.springlearn.monitor.opentsdb.http;

import com.cycloneboy.springcloud.springlearn.monitor.opentsdb.builder.MetricBuilder;
import com.cycloneboy.springcloud.springlearn.monitor.opentsdb.request.QueryBuilder;
import com.cycloneboy.springcloud.springlearn.monitor.opentsdb.response.Response;
import com.cycloneboy.springcloud.springlearn.monitor.opentsdb.response.SimpleHttpResponse;
import java.io.IOException;


public interface HttpClient extends Client {

  public Response pushMetrics(MetricBuilder builder,
      ExpectResponse exceptResponse) throws IOException;

  public SimpleHttpResponse pushQueries(QueryBuilder builder,
      ExpectResponse exceptResponse) throws IOException;
}