package com.ensah.financial_advisor;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;


@RestController
public class ChatController {

    private final ChatClient chatClient;


    public ChatController(ChatClient.Builder builder, VectorStore vectorstore) {
        this.chatClient = builder
                            .defaultAdvisors(new QuestionAnswerAdvisor(vectorstore))
                            .build();
    }

    @GetMapping("/")
    public Flux<String> chat() {
        return chatClient.prompt()
        .user("How did the federal resserve's recent interest rate cut impact various classes according to the analysis?")
        .stream()
        .content();
    }

}
