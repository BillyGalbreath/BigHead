package net.pl3x.bighead.config;

import net.pl3x.bighead.BigHead;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class BigHeadConfig extends ConfigLoader implements ConfigBase {
    public static final BigHeadConfig INSTANCE = new BigHeadConfig();
    private static final String FILE_NAME = "bighead.json";

    public Data data;

    public void init() {
        reload();
    }

    public String file() {
        return FILE_NAME;
    }

    public void reload() {
        try {
            data = loadConfig(new Data(), Data.class, new File(BigHead.configDir, FILE_NAME));
        } catch (IOException e) {
            data = null;
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            saveConfig(data, Data.class, new File(BigHead.configDir, FILE_NAME));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class Data {
        private List<String> bighead = new ArrayList<>();

        public Set<UUID> getBigHeads() {
            return bighead.stream()
                    .map(UUID::fromString)
                    .collect(Collectors.toSet());
        }

        public void setBighead(Set<UUID> bighead) {
            this.bighead = bighead.stream()
                    .map(UUID::toString)
                    .collect(Collectors.toList());
        }
    }
}
