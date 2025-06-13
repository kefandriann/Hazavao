package com.hazavao.app.endpoint.rest.controller.health;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.MediaType;
import okhttp3.Response;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ChatGPTController {
    private final Dotenv dotenv = Dotenv.load();

    private final String apiKey = dotenv.get("OPENAI_API_KEY");
    private final String apiUrl = dotenv.get("OPENAI_API_URL");

    public String executePrompt(String prompt) throws IOException {
        OkHttpClient client = new OkHttpClient();
        ObjectMapper mapper = new ObjectMapper();

        Map<String, Object> userMessage = Map.of(
                "role", "user",
                "content", prompt
        );

        Map<String, Object> requestBody = Map.of(
                "model", "gpt-3.5-turbo",
                "messages", List.of(userMessage),
                "temperature", 0.7
        );

        RequestBody body = RequestBody.create(
                mapper.writeValueAsString(requestBody),
                MediaType.get("application/json")
        );

        Request request = new Request.Builder()
                .url(apiUrl)
                .addHeader("Authorization", "Bearer " + apiKey)
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Error API : " + response);
            }

            String responseBody = response.body().string();
            Map<String, Object> json = mapper.readValue(responseBody, Map.class);
            Map<String, Object> firstChoice = (Map<String, Object>) ((List<?>) json.get("choices")).get(0);
            Map<String, Object> message = (Map<String, Object>) firstChoice.get("message");

            return message.get("content").toString().trim();
        }
    }
}
