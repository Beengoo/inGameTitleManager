package ua.beengoo.ingametitlemanager.utils.config;

public class ConfigCheckException extends Exception{
    public ConfigCheckException(String s) {
        super(s);
    }

    public ConfigCheckException(String s, Exception e) {
        super(s, e);
    }
}
