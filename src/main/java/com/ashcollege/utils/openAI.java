package com.ashcollege.utils;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

import org.json.JSONArray;
import org.json.JSONObject;



public class openAI {
    private static final String API_KEY = " ";

    public static String encodeImage(String imagePath) throws IOException {
        byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));
        return Base64.getEncoder().encodeToString(imageBytes);
    }

    public static String queryOpenAI(String message, String imagePath) {
        String jsonPayload = null;
        String content = "";

        try {
            String base64Image = encodeImage(imagePath);
            HttpClient client = HttpClient.newHttpClient();

            jsonPayload = String.format("""
                    {
                        "model": "gpt-4o",
                        "messages": [
                          {
                            "role": "user",
                            "content": [
                              {
                                "type": "text",
                                "text": "%s"
                              },
                              {
                                "type": "image_url",
                                "image_url": {
                                  "url": "data:image/jpeg;base64,%s"
                                }
                              }
                            ]
                          }
                        ],
                        "max_tokens": 300
                    }""", message, base64Image);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + API_KEY)
                    .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject jsonObject = new JSONObject(response.body());

            JSONArray jsonArray = jsonObject.getJSONArray("choices");
            JSONObject messageObject = jsonArray.getJSONObject(0).getJSONObject("message");

            // חילוץ הערך של "content"
             content = messageObject.getString("content");

            // הצגת התוצאה
            System.out.println(content);

        } catch (IOException | InterruptedException e) {
            System.err.println("Error during HTTP request:");
            e.printStackTrace();
        }
        return content;
    }




}
