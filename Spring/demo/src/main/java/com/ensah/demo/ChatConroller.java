package com.ensah.demo;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
// import org.springframework.ai.chat.client.advisor.VectorStoreChatMemoryAdvisor;
import org.springframework.ai.chat.memory.cassandra.CassandraChatMemory;
// import org.springframework.ai.chat.memory.cassandra.CassandraChatMemoryConfig;
// import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
public class ChatConroller {

    private final ChatClient chatClient;
    public ChatConroller(ChatClient.Builder builder, CassandraChatMemory chatMemory) {
        this.chatClient = builder
        .defaultAdvisors(
            new MessageChatMemoryAdvisor(chatMemory)
        )
        .build();
    }

    @GetMapping("/stream")
    public Flux<String> stream(@RequestParam String message) {
        return chatClient.prompt()
            .user(message)
            .stream()
            .content();
    }
}
