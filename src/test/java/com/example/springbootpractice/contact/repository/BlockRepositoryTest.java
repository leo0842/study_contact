package com.example.springbootpractice.contact.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.example.springbootpractice.contact.domain.Block;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BlockRepositoryTest {

  @Autowired
  private BlockRepository blockRepository;

  void crud(){
    Block block = new Block();

    block.setName("Shin");
    block.setReason("drink too much");
    block.setStartDate(LocalDate.now());
    block.setEndDate(LocalDate.now().plusDays(3));

    blockRepository.save(block);

  }
}