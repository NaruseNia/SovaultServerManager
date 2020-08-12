package com.nia.sovaultservermanager.discord.webhook;

import com.nia.sovaultservermanager.discord.webhook.content.DiscordEmbed;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class DiscordWebHook{

    private final String USER_AGENT = "Mozilla/5.0 (compatible; Discordbot/2.0; +https://discordapp.com)";
    private final URL url;
    private String content;
    private String username;
    private String avatarUrl;
    private boolean tts;
    private final List<DiscordEmbed> embeds = new ArrayList<>();

    public DiscordWebHook(URL url){
        this.url = url;
    }

    public DiscordWebHook setContent(String content) {
        this.content = content;
        return this;
    }

    public DiscordWebHook setUsername(String username) {
        this.username = username;
        return this;
    }

    public DiscordWebHook setAvatar(String avatarUrl) {
        this.avatarUrl = avatarUrl;
        return this;
    }

    public DiscordWebHook setTTS(boolean tts) {
        this.tts = tts;
        return this;
    }

    public void send() throws IOException {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("content", content);
        jsonObject.put("username", username);
        jsonObject.put("avatar_url", avatarUrl);
        jsonObject.put("tts", this.tts);

        //TODO:Add embeds

        URL webHookUrl = url;
        HttpsURLConnection client = (HttpsURLConnection) url.openConnection();
        client.addRequestProperty("Content-Type", "application/json");
        client.addRequestProperty("User-Agent", USER_AGENT);
        client.setDoOutput(true);
        client.setRequestMethod("POST");

        OutputStream outputStream = client.getOutputStream();
        outputStream.write(jsonObject.toString().getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();

        client.getInputStream().close();
        client.disconnect();

    }
}
