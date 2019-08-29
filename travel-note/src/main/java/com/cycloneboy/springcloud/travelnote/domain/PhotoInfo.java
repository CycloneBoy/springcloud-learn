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
public class PhotoInfo {

    private String noteNumber;

    private String imageNumber;

    @JsonProperty("replys")
    private JsonNode replys;

    @JsonProperty("vote_num")
    private Integer voteNum;

    @JsonProperty("reply_num")
    private Integer replyNum;

    @JsonProperty("share_num")
    private Integer shareNum;

    @JsonProperty("original_url")
    private String originalUrl;

    @JsonProperty("is_vote")
    private Integer isVote;


}
