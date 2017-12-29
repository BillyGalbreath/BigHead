package net.pl3x.bighead.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;

abstract class ConfigLoader {
    private static final JsonParser parser = new JsonParser();
    private static final Gson gson = new GsonBuilder()
            .disableHtmlEscaping() // stop converting <> tags to \u003c\u003e etc
            .setPrettyPrinting() // make human readable for easier editing
            .create();

    static <T> T loadConfig(T config, Class<T> clazz, File file) throws IOException {
        if (file.exists()) {
            config = gson.fromJson(new String(Files.readAllBytes(file.toPath())), clazz);
        }
        return saveConfig(config, clazz, file);
    }

    static <T> T saveConfig(T config, Class<T> clazz, File file) throws IOException {
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        String json = gson.toJson(parser.parse(gson.toJson(config, clazz)));
        try (PrintWriter out = new PrintWriter(file)) {
            out.println(json);
        }
        return config;
    }
}
