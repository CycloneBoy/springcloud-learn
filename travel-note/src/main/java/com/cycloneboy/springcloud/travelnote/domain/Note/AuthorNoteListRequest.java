package com.cycloneboy.springcloud.travelnote.domain.Note;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class AuthorNoteListRequest {

    private String sAction;

    private Integer iPage;

    private Integer iUid;


}

