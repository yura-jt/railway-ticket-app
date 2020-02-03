package com.railway.booking.dao.impl.Util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JdbcUtil {
    public static void main(String[] args) {
        System.out.println();
    }

    public static String getSqlQueryStringFromFile(String filePath) {
        Path path = getPathFromString(filePath);
        return parseSqlFileToString(path);
    }

    private static Path getPathFromString(String filePath) {
        Path path = null;
        try {
            path = Paths.get(ClassLoader.getSystemResource(filePath).toURI());
        } catch (URISyntaxException e) {
            throw new DaoImplTestingException
                    (String.format("Couldn't parse sql file path: %s to Path object", filePath), e);
        }
        return path;
    }

    private static String parseSqlFileToString(Path path) {
        String query = "";
        try {
            query = String.join("\n", Files.readAllLines(path));
        } catch (IOException e) {
            throw new DaoImplTestingException
                    (String.format("Couldn't read lines from file with path: %s", path), e);

        }
        return query;
    }
}