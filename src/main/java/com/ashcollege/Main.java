package com.ashcollege;


import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;

@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
@EnableScheduling
public class Main   {


    public static boolean applicationStarted = false;
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static long startTime;


    @Autowired
    public static void main(String[] args) throws IOException {
        //OpenAIService.queryOpenAI("What’s in this image? Tell me only what is the food in it, in a single word", "C:\\Users\\USER\\Pictures\\Screenshots\\Image.png");


        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);
        LOGGER.info("Application started.");
        applicationStarted = true;
        getChatResponse("what is the weather?");
        startTime = System.currentTimeMillis();

    }
    public static void getChatResponse(String message) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\r\n  \"model\": \"gpt-3.5-turbo\",\r\n  \"messages\": [\r\n    {\r\n      \"role\": \"user\",\r\n      \"content\": \"Hello, what the weather?\"\r\n    }\r\n  ]\r\n}\r\n");
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer sk-proj-G8ROJ19zX1ZlbGdJZgxDT3BlbkFJjLBL3eYWknAqu29qRib8")
                .addHeader("Cookie", "__cf_bm=NBBVoRQWcZ2Qxv0EygeGO2yuAgFs1EYe7idRexjkg6E-1721153316-1.0.1.1-IsTU2DNf79tVoj.hhz3XSYVtrNfU_D_pUAwx8cgjE4SAxahGCD_c9AKcP7150cVko5P9YrNx89VN6eQto8Rrcg; _cfuvid=9SB9ERZhWgaZXTLoAh_XEcvLPJk5LBMdOhaR1g5.MV0-1721153316689-0.0.1.1-604800000")
                .build();
        Response response = client.newCall(request).execute();
//        message = "what day is it today?";
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", "Bearer " + apiKey);
//        headers.set("Content-Type", "application/json");
//
//        JSONObject body = new JSONObject();
//        body.put("model", "gpt-3.5-turbo");
//        body.put("messages", new JSONArray().put(new JSONObject().put("role", "user").put("content", message)));
//
//        HttpEntity<String> entity = new HttpEntity<>(body.toString(), headers);
//        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class);
//
//        JSONObject responseObject = new JSONObject(response.getBody());
//        return responseObject.getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content");

    }
    


}
