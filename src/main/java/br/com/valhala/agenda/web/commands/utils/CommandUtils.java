package br.com.valhala.agenda.web.commands.utils;

import br.com.valhala.agenda.web.commands.Command;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class CommandUtils {

    private static Properties properties;

    static {
        try (InputStream stream = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("commands.properties")) {
            properties = new Properties();
            properties.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean existeComando(String comando) {
        return properties.containsKey(comando);
    }

    public static Command getComando(String comando)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return (Command) Class.forName(properties.getProperty(comando)).newInstance();
    }

}