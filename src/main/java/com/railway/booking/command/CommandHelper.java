package com.railway.booking.command;

public final class CommandHelper {

    public static String getCommandNameFromPath(String requestPath) {
        int lastSlashIndex = requestPath.lastIndexOf('/');

        return requestPath.substring(lastSlashIndex + 1);
    }
}
