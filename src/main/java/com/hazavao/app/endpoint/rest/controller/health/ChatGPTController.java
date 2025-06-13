package com.hazavao.app.endpoint.rest.controller.health;

import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.*;
import org.json.JSONObject;

public class ChatGPTController {
    private static final Dotenv dotenv = Dotenv.load();

    private static final String apiUrl = dotenv.get("OPENAI_API_URL");

    public static String askChatGPT(String prompt, String API_KEY) throws Exception {
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
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();
            JSONObject obj = new JSONObject(responseBody);
            return obj
                    .getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content");
        }
    }
}
