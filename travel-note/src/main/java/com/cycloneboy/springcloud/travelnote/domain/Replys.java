package com.cycloneboy.springcloud.travelnote.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIgnoreProperties
public class Replys {

    @JsonProperty("list")
    private JsonNode list;

    @JsonProperty("more")
    private Integer more;
}
