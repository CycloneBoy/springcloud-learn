package com.cycloneboy.springcloud.apigateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

/**
 * Create by  sl on 2019-08-24 00:21
 */
@Slf4j
public class AccessFilter extends ZuulFilter {

  @Override
  public String filterType() {
    return "pre";
  }

  @Override
  public int filterOrder() {
    return 0;
  }

  @Override
  public boolean shouldFilter() {
    return true;
  }

  @Override
  public Object run() throws ZuulException {
    RequestContext ctx = RequestContext.getCurrentContext();
    HttpServletRequest request = ctx.getRequest();

    log.info("send {} to {}", request.getMethod(), request.getRequestURL().toString());
    Object token = request.getParameter("token");

    if (token == null) {
      log.warn("access token is empty");
      ctx.setSendZuulResponse(false);
      ctx.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
      return null;
    }

    log.info("access token is ok");
    return null;
  }
}
