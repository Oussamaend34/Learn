package com.ensah.demo;

import java.time.Duration;

import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.cassandra.CassandraChatMemory;
import org.springframework.ai.chat.memory.cassandra.CassandraChatMemoryConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    
    @Bean
    public ChatMemory appChatMemory() {
        return CassandraChatMemory.create(CassandraChatMemoryConfig.builder().withTimeToLive(Duration.ofDays(1)).build());
    }
}
