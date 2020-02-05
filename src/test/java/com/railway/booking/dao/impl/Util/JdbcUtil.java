package com.railway.booking.dao.impl.Util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JdbcUtil {
    public static String getSqlQueryStringFromFile(String filePath) {
        Path path = Paths.get(filePath);
        return parseSqlFileToString(path);
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