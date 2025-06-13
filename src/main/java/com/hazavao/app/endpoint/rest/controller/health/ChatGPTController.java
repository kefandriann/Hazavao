package com.hazavao.app.endpoint.rest.controller.health;

import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.MediaType;
import okhttp3.Response;

public class ChatGPTController {
    private static final Dotenv dotenv = Dotenv.load();

    private static final String apiKey = dotenv.get("OPENAI_API_KEY");
    private static final String apiUrl = dotenv.get("OPENAI_API_URL");

    public static String askChatGPT(String prompt) throws Exception {
        OkHttpClient client = new OkHttpClient();

        String json = """
        {
          "model": "gpt-3.5-turbo",
          "messages": [{"role": "user", "content": "%s"}]
        }
        """.formatted(prompt);

        RequestBody body = RequestBody.create(json, MediaType.get("application/json"));

        Request request = new Request.Builder()
                .url(apiUrl)
                .addHeader("Authorization", "Bearer " + apiKey)
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}
