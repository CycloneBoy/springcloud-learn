package com.cycloneboy.springcloud.travelnote.domain.Note;

import com.cycloneboy.springcloud.common.entity.NoteAuthor;
import com.cycloneboy.springcloud.common.entity.TravelNoteDetail;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Create by  sl on 2019-08-05 23:00
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AuthorAndNoteList {

    private NoteAuthor noteAuthor;

    private List<TravelNoteDetail> travelNoteDetailList;
}
