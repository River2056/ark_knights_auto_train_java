package com.river.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

public class TestGetPropertiesFile {
    public static void main(String[] args) throws FileNotFoundException {
        InputStream configFile = new FileInputStream("./settings.properties");
        Properties config = new Properties();

        try {
            config.load(configFile);
            System.out.println("config is empty: " + config.isEmpty());

//            System.out.printf("commenceBtn.r:%s%n", config.getProperty("color.commenceBtn.r"));
//            System.out.printf("commenceBtn.g:%s%n", config.getProperty("color.commenceBtn.g"));
//            System.out.printf("commenceBtn.b:%s%n", config.getProperty("color.commenceBtn.b"));
//            System.out.printf("operationStart.r:%s%n", config.getProperty("color.operationStart.r"));
//            System.out.printf("operationStart.g:%s%n", config.getProperty("color.operationStart.g"));
//            System.out.printf("operationStart.b:%s%n", config.getProperty("color.operationStart.b"));
//            System.out.printf("operationEnd.r:%s%n", config.getProperty("color.operationEnd.r"));
//            System.out.printf("operationEnd.g:%s%n", config.getProperty("color.operationEnd.g"));
//            System.out.printf("operationEnd.b:%s%n", config.getProperty("color.operationEnd.b"));
//            System.out.printf("cancelBtn.r:%s%n", config.getProperty("color.cancelBtn.r"));
//            System.out.printf("cancelBtn.g:%s%n", config.getProperty("color.cancelBtn.g"));
//            System.out.printf("cancelBtn.b:%s%n", config.getProperty("color.cancelBtn.b"));

            Set<Map.Entry<Object, Object>> set = config.entrySet();
            System.out.println(set);
            System.out.println(set.size());

            Map<Object, Object> map = set.stream().map(e -> e).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            System.out.println(map);

            System.out.printf(
                    "color commence button: %s, %s, %s%n",
                    map.get("color.commenceBtn.r"),
                    map.get("color.commenceBtn.g"),
                    map.get("color.commenceBtn.b")
            );


        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
