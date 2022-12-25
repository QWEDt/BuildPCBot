package org.mytelegrambot.utils;

import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GetAnswerFromAPI {
    public static String get() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://yesno.wtf/api"))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return JsonParser.parseString(response.body())
                .getAsJsonObject().get("answer").toString().replace("\"", "");
    }
}
