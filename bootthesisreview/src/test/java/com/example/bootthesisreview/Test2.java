package com.example.bootthesisreview;

import com.example.bootthesisreview.mapper.BatchMapper;
import com.example.bootthesisreview.mapper.CommitteeVoteMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Test2 {
    @Test
void contextLoads() {
}

    @Autowired
    private CommitteeVoteMapper mapper;
    @Test
    void Test(){
        mapper.committeevoteListForBatch("1").forEach(System.out::println);
    }
}
