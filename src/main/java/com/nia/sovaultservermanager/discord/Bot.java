package com.nia.sovaultservermanager.discord;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.TextChannel;

import javax.security.auth.login.LoginException;

public class Bot {

    private final String TOKEN = "NzM5NDAxMjIxNzUxOTYzNjU3.XyZ7DA.pQdTPt1RDp42ooN07qsEc1E5ggA";
    private final JDA discordClient;

    public Bot() throws LoginException {
        discordClient = JDABuilder.createDefault(TOKEN).build();
    }

    public void sendMessage(TextChannel channel, String message){
        channel.sendMessage(message).complete();
    }

    public JDA getDiscordClient() {
        return discordClient;
    }

    public String getToken() {
        return TOKEN;
    }
}
