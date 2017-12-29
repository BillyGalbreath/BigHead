package net.pl3x.bighead.config;

public interface ConfigBase {
    void init();

    void reload();

    String file();
}
