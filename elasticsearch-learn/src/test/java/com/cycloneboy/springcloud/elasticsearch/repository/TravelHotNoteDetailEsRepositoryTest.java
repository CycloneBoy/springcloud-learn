package com.cycloneboy.springcloud.elasticsearch.repository;


import com.cycloneboy.springcloud.elasticsearch.entity.TravelHotNoteDetailEs;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Create by  sl on 2020-01-26 17:09
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class TravelHotNoteDetailEsRepositoryTest {

  @Autowired
  private TravelHotNoteDetailEsRepository hotNoteDetailEsRepository;


  @Before
  public void setUp() throws Exception {
  }

  @Test
  public void testQuery() {
    List<TravelHotNoteDetailEs> hotNotes = new ArrayList<>();

    Optional<TravelHotNoteDetailEs> result = hotNoteDetailEsRepository.findById(1315408L);
    System.out.println(result.orElseGet(null));

    hotNotes.forEach(travelHotNote -> System.out.println(travelHotNote.toString()));
  }
}