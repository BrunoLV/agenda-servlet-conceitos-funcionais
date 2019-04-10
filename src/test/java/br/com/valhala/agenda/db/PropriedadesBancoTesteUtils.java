package br.com.valhala.agenda.db;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropriedadesBancoTesteUtils {

    private static Properties properties;

    static {
        try (InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties")) {
            properties = new Properties();
            properties.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getUrl() {
        return properties.getProperty("h2.url");
    }

    public static String getUsuario() {
        return properties.getProperty("h2.user");
    }

    public static String getSenha() {
        return properties.getProperty("h2.password");
    }

}
