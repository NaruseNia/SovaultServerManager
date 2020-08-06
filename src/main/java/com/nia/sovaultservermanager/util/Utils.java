package com.nia.sovaultservermanager.util;

import com.nia.sovaultservermanager.SovaultServerManager;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Time;
import java.util.Properties;

public class Utils {

    public static Time ConvertTickToTime(int tickIn){
        int hour, min, sec;
        int secIn = tickIn / 20;

        hour = secIn / 3600;
        min  = (secIn % 3600) / 60;
        sec  = secIn % 60;

        return Time.valueOf( hour + ":" + min + ":" + sec );
    }
}
