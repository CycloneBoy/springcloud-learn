package com.cycloneboy.springcloud.travelnote.domain.Note;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Create by  sl on 2019-08-03 00:27
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIgnoreProperties
public class TravelNoteInfo {

    private AuthorNoteData data;

}
