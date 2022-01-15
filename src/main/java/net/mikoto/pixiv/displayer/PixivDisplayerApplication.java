package net.mikoto.pixiv.displayer;

import net.mikoto.log.Logger;
import net.mikoto.pixiv.engine.PixivEngine;
import net.mikoto.pixiv.engine.logger.impl.ConsoleTimeFormatLogger;
import net.mikoto.pixiv.engine.pojo.Config;
import org.apache.commons.io.IOUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Properties;

import static net.mikoto.pixiv.displayer.util.FileUtil.createDir;
import static net.mikoto.pixiv.displayer.util.FileUtil.createFile;

/**
 * @author mikoto
 */
@SpringBootApplication
public class PixivDisplayerApplication {
    /**
     * 常量
     */
    public static final Properties PROPERTIES = new Properties();
    private static final Logger LOGGER = new ConsoleTimeFormatLogger();
    public static PixivEngine PIXIV_ENGINE;

    public static void main(String[] args) {
        try {
            SpringApplication.run(PixivDisplayerApplication.class, args);

            createDir("config");
            createFile(new File("config/config.properties"), IOUtils.toString(Objects.requireNonNull(PixivDisplayerApplication.class.getClassLoader().getResourceAsStream("config.properties")), StandardCharsets.UTF_8));
            PROPERTIES.load(new FileReader("config/config.properties"));

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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
