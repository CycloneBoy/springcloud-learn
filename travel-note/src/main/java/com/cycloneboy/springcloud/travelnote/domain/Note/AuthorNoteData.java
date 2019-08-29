package com.cycloneboy.springcloud.travelnote.domain.Note;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Create by  sl on 2019-08-03 00:05
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIgnoreProperties
public class AuthorNoteData {

    @JsonProperty("pointers")
    private Pointers pointers;

}
