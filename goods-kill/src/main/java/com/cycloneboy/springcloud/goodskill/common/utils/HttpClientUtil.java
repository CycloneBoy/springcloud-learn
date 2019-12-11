package com.cycloneboy.springcloud.goodskill.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * Create by  sl on 2019-12-10 22:10
 */
@Slf4j
@Service
public class HttpClientUtil {

  public String client(String url, HttpMethod method, MultiValueMap<String, Object> params) {
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders httpHeaders = new HttpHeaders();
    //  请勿轻易改变此提交方式，大部分的情况下，提交方式都是表单提交
    httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    HttpEntity<MultiValueMap<String, Object>> requestHttpEntity = new HttpEntity<>(params,
        httpHeaders);
    ResponseEntity<String> response = restTemplate
        .exchange(url, method, requestHttpEntity, String.class);

    log.info("HttpClientUtil:{} - {} - {}", url, method.toString(), response.getBody());
    return response.getBody();
  }

}
