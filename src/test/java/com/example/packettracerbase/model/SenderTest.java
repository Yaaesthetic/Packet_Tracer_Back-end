package com.example.packettracerbase.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SenderTest {

    @Test
    void testSenderBuilder() {
        Sender sender = Sender.builder()
                .cinSender("123456")
                .build();
        assertNotNull(sender);
        assertEquals("123456", sender.getCinSender());
    }

}