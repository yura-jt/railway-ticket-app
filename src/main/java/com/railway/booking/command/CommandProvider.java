package com.railway.booking.command;

import com.railway.booking.context.ApplicationInjector;

import java.util.Map;

public class CommandProvider {
    private final Map<String, Command> commands;

    public CommandProvider() {
        commands = ApplicationInjector.getCommands();
    }

    public Command getCommand(String requestPath) {
        String commandName = CommandHelper.getCommandNameFromPath(requestPath);

        return commands.getOrDefault(commandName, commands.get("login"));
    }
}