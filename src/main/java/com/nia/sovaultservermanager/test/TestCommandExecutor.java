package com.nia.sovaultservermanager.test;

import com.nia.sovaultservermanager.SovaultServerManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Properties;

public class TestCommandExecutor implements CommandExecutor {

    private Properties message = SovaultServerManager.getProperties();
    private Properties statMessage = SovaultServerManager.getStatLocalize();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage(message.getProperty("channel.main.system"));
        sender.sendMessage(statMessage.getProperty("stat.DAMAGE_DEALT"));
        return true;
    }
}
