package com.cycloneboy.springcloud.travelnote.domain.Note;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Create by  sl on 2019-08-03 00:04
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIgnoreProperties
public class Pointers {

    @JsonProperty("world")
    List<AuthorNote> world;

    @JsonProperty("china")
    List<AuthorNote> china;

}
