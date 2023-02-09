package com.company.ProjectSpring.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import javax.json.Json;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SmsSender {
    public static void sendMessage(String username, String message){
        String token = getToken();
        sendOneMessage(token, username, message);
    }

    private static String getToken(){
        try {
            String serializedData =  Json.createObjectBuilder()
                    .add("username", "ivanbodrov61") // используйте корректные значения учетной записи
                    .add("password", "a8jfbrae")
                    .build()
                    .toString();

            URL url = new URL("https://online.sigmasms.ru/api/login");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setDoOutput(true);
            OutputStream writer = urlConnection.getOutputStream();
            writer.write(serializedData.getBytes());
            writer.flush();
            writer.close();

            if(urlConnection.getResponseCode() == 200){
                InputStream inputStream = urlConnection.getInputStream();

                byte[] buffer = new byte[inputStream.available()];
                IOUtils.readFully(inputStream, buffer);

                JsonElement jsonElement = new JsonParser().parse(new String(buffer));
                JsonElement token = jsonElement.getAsJsonObject().get("token");
                return token.getAsString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String sendOneMessage(String token, String username, String message) {
        try {
            String serializedData = Json.createObjectBuilder()
                    .add("recipient", username) // используйте корректный номер телефона
                    .add("type", "sms")
                    .add("payload", Json.createObjectBuilder()
                            // убедитесь, что имя отправителя добавлено в ЛК в
                            // разделе Компоненты ( https://online.sigmasms.ru/#/components )
                            .add("sender", "B-Media")
                            .add("text", message)
                    )
                    .build()
                    .toString();

            URL url = new URL("https://online.sigmasms.ru/api/sendings");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Authorization", token);

            urlConnection.setDoOutput(true);
            OutputStream writer = urlConnection.getOutputStream();
            writer.write(serializedData.getBytes());
            writer.flush();
            writer.close();

            if(urlConnection.getResponseCode() == 200) {
                InputStream inputStream = urlConnection.getInputStream();

                byte[] buffer = new byte[inputStream.available()];
                IOUtils.readFully(inputStream, buffer);
                JsonElement jsonElement = new JsonParser().parse(new String(buffer));
                JsonElement messageId = jsonElement.getAsJsonObject().get("id");
                return messageId.getAsString();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
