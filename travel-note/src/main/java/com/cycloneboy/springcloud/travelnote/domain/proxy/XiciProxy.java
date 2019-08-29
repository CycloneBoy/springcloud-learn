package com.cycloneboy.springcloud.travelnote.domain.proxy;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Create by  sl on 2019-08-03 10:17
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class XiciProxy {

    private String ip;

    private Integer port;

    private String protocol;

    private Float speed;

    private Float time;

    private Integer liveTime;

    private LocalDateTime checkTime;


}
