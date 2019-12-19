package com.cycloneboy.springcloud.searchhouse.common;

import lombok.Getter;
import lombok.Setter;

/**
 * Datatables响应结构
 */
@Setter
@Getter
public class ApiDataTableResponse extends ApiResponse {

    private int draw;
    private long recordsTotal;
    private long recordsFiltered;

    public ApiDataTableResponse(ApiResponse.Status status) {
        this(status.getCode(), status.getStandardMessage(), null);
    }

    public ApiDataTableResponse(int code, String message, Object data) {
        super(code, message, data);
    }

}
