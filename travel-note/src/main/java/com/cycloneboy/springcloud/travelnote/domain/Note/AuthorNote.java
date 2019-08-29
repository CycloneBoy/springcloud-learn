package com.cycloneboy.springcloud.travelnote.domain.Note;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Create by  sl on 2019-08-02 23:51
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIgnoreProperties
public class AuthorNote {

    private Integer id;

    @JsonProperty("img_src")
    private String imgSrc;

    private Double lat;

    private Double lng;

    @JsonProperty("mddname")
    private String destination;

    @JsonProperty("title")
    private String title;

}
