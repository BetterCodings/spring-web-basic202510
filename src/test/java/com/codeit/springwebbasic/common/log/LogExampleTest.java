package com.codeit.springwebbasic.common.log;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LogExampleTest {

    @Autowired
    LogExample logExample;

    @Test
    void logTest() {
        logExample.showLog();
    }
}
