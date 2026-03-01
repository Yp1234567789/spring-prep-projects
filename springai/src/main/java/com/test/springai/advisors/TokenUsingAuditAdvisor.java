package com.test.springai.advisors;

import org.slf4j.Logger;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.ai.chat.model.ChatResponse;

public class TokenUsingAuditAdvisor implements CallAdvisor {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(TokenUsingAuditAdvisor.class);

    @Override
    public ChatClientResponse adviseCall(ChatClientRequest chatClientRequest, CallAdvisorChain callAdvisorChain) {
        ChatClientResponse chatClientResponse = callAdvisorChain.nextCall(chatClientRequest);
        ChatResponse chatResponse = chatClientResponse.chatResponse();
        if(chatResponse.getMetadata() != null ){
            Usage usage = chatResponse.getMetadata().getUsage();
                if(usage != null){
                    logger.info("token usage details:{}",usage.toString());
                }
        }
        return chatClientResponse;
    }

    @Override
    public String getName() {
        return "TokenUsingAuditAdvisor";
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
