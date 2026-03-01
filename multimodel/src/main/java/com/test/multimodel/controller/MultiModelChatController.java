package com.test.openai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MultiModelChatController  {

    private final ChatClient openAiChatClient;
    private final ChatClient bedrockChatClient;

    public MultiModelChatController(@Qualifier("openAiChatClient") ChatClient openAiChatClient,
                                    @Qualifier("bedrockChatClient") ChatClient bedrockChatClient) {
        this.openAiChatClient = openAiChatClient;
        this.bedrockChatClient = bedrockChatClient;
    }


    @GetMapping("/openai/chat")
    public String openAIChat(@RequestParam("message") String message) {

        return openAiChatClient.prompt(message).call().content();
    }
    @GetMapping("/openai/chat")
    public String bedrockAIChat(@RequestParam("message") String message) {

        return bedrockChatClient.prompt(message).call().content();
    }

}