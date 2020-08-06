package com.nia.sovaultservermanager.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class PropertyLoader {

    private String path;
    private InputStream inputStream;
    private InputStreamReader inputStreamReader;
    private Properties properties;

    public PropertyLoader(String path) {
        this.path = path;
        inputStream = getClass().getClassLoader().getResourceAsStream(this.path);
        inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        properties = new Properties();
    }

    public PropertyLoader setEncode(Charset charset) {
        inputStreamReader = new InputStreamReader(inputStream, charset);
        return this;
    }

    public Properties getProperties() throws IOException {
        properties.load(inputStreamReader);
        return properties;
    }

}
