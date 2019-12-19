package com.cycloneboy.springcloud.searchhouse.common;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 通用多结果Service返回结构
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceMultiResult<T> {

    private long total;
    private List<T> result;

    public ServiceMultiResult(List<T> result) {
        this.total = result.size();
        this.result = result;
    }

    public int getResultSize() {
        if (this.result == null) {
            return 0;
        }
        return this.result.size();
    }
}
