package com.cycloneboy.springcloud.slmall.common.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * Created by Exrick on 2017/8/1.
 */
@Data
public class DataTablesResult implements Serializable {

    private static final long serialVersionUID = -347738928433889748L;

    private Boolean success;

    private int draw;

    private int recordsTotal;

    private int recordsFiltered;

    private String error;

    private List<?> data;


}
