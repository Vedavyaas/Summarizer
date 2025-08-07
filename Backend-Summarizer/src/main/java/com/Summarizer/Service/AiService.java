package com.Summarizer.Service;


import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AiService {

    /* https://docs.spring.io/spring-ai/reference/api/chat/ollama-chat.html
         The above link for ollama AI documentation
    */
    @Autowired
    private OllamaChatModel chatModel;

    public String getResult(String prompt){
        Prompt requestPrompt = new Prompt(
                List.of(new UserMessage(prompt)),
                OllamaOptions.builder()
                        .model("llama2")
                        .build()
        );

        ChatResponse response = chatModel.call(requestPrompt);
        return response.getResult().getOutput().getText();
    }
}
