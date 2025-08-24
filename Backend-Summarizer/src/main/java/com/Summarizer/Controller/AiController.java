package com.Summarizer.Controller;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import java.util.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/control")
public class AiController {

    private final String prompting = "Summarize the paragraph into one professional sentence. Then list up to 10 single-word bullets for core topic, purpose, and key takeaway. Output only the sentence and bullets. No extra text or comments.\n";

    @GetMapping("/api")
    public String aiResponse(@RequestParam String messages){
        String promptMessage = prompting + messages;
        return getGroqResult(promptMessage);
    }

    public String whatsAppResponse(String whatsAppMessage){
        String promptMessage = prompting + whatsAppMessage;
        return getGroqResult(promptMessage);
    }

    private String getGroqResult(String prompt) {

        Dotenv dotenv = Dotenv.load();
        String apiKey = System.getenv("GROQ_API_KEY");
        String apiUrl = System.getenv("GROQ_API_URL");

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);

        Map<String, Object> body = new HashMap<>();
        body.put("model", "llama3-8b-8192");
        body.put("messages", List.of(Map.of("role", "user", "content", prompt)));

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(apiUrl, entity, Map.class);

        List choices = (List) response.getBody().get("choices");
        Map choice = (Map) choices.get(0);
        Map message = (Map) choice.get("message");
        return (String) message.get("content");
    }
}