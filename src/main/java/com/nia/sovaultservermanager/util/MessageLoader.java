package com.nia.sovaultservermanager.util;

import com.nia.sovaultservermanager.SovaultServerManager;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class MessageLoader{

    private static final Properties properties = new Properties();

    public MessageLoader(String path){
        try{
            properties.load(Files.newBufferedReader(Paths.get(path), StandardCharsets.UTF_8));
        }catch (IOException e){
            SovaultServerManager.getInstance().getLogger().warning(String.format("プロパティーファイルを読み込めませんでした。場所:%s", path));
        }
    }

    public MessageLoader(Path path){
        try{
            properties.load(Files.newBufferedReader(path, StandardCharsets.UTF_8));
        }catch (IOException e){
            SovaultServerManager.getInstance().getLogger().warning(String.format("プロパティーファイルを読み込めませんでした。場所:%s", path));
        }
    }

    public String getProperty(String key){
        return getProperty(key, "");
    }

    public String getProperty(String key, String defaultValue){
        return properties.getProperty(key, defaultValue);
    }

}

