package net.mikoto.pixiv.displayer;

import net.mikoto.log.Logger;
import net.mikoto.pixiv.engine.PixivEngine;
import net.mikoto.pixiv.engine.logger.impl.ConsoleTimeFormatLogger;
import net.mikoto.pixiv.engine.pojo.Config;
import org.apache.commons.io.IOUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Properties;

/**
 * @author mikoto
 */
@SpringBootApplication
public class PixivDisplayerApplication {
    /**
     * 常量
     */
    public static final String VERSION = "1.0.0";
    public static final String DESCRIPTION = "Pixiv main project.";
    public static final String AUTHOR = "mikoto";
    public static final String PACKAGE = "net.mikoto.pixiv";
    public static final Integer HTTP_API_PORT = 2465;
    public static final Properties PROPERTIES = new Properties();
    private static final Logger LOGGER = new ConsoleTimeFormatLogger();
    public static PixivEngine PIXIV_ENGINE;

    public static void main(String[] args) {
        SpringApplication.run(PixivDisplayerApplication.class, args);

        // 配置config
        InputStream in = null;
        try {
            in = new FileInputStream("config.properties");
        } catch (FileNotFoundException e) {
            File file = new File("config.properties");
            try {
                file.createNewFile();
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(IOUtils.toString(Objects.requireNonNull(PixivDisplayerApplication.class.getClassLoader().getResourceAsStream("config.properties"))));
                fileWriter.close();
                in = new FileInputStream("config.properties");
            } catch (IOException ignored) {
            }
        }
        try {
            PROPERTIES.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 配置config
        Config config = new Config();
        config.setLogger(LOGGER);
        config.setKey(PROPERTIES.getProperty("KEY"));
        config.setUserPassword(PROPERTIES.getProperty("PASSWORD"));
        config.setUserName(PROPERTIES.getProperty("USERNAME"));
        config.setJpbcUrl(PROPERTIES.getProperty("URL"));
        config.setPixivDataForwardServer(new ArrayList<>(Arrays.asList(PROPERTIES.getProperty("DATA_FORWARD_SERVER").split(";"))));

        // 配置Dao
        PIXIV_ENGINE = new PixivEngine(config);
    }

}
