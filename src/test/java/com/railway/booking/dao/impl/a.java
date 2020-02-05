package com.railway.booking.dao.impl;

import com.zaxxer.hikari.HikariDataSource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.ResourceBundle;

public class a {
    private static final String CONFIG_PATH = "src/test/resources/test_config.properties";
    private static final String H2_PATH = "src/test/resources/db/h2_config.properties";


    public static void main(String[] args) throws IOException, URISyntaxException {
        new a().b();
        FileReader fileReader = new FileReader(H2_PATH);
        BufferedReader reader = new BufferedReader(fileReader);
        System.out.println(reader.readLine());

        File file = new File(H2_PATH);
        URL[] urls = {file.toURI().toURL()};
        ClassLoader loader = new URLClassLoader(urls);

        String dataBaseVendor = ResourceBundle.getBundle("h2_config", Locale.getDefault(), loader).getString("db.url");
        System.out.println(dataBaseVendor);
    }

    public void b() {

        ResourceBundle appResource = ResourceBundle.getBundle("test_config");
        System.out.println(appResource.getString("db.vendor"));


    }
}
