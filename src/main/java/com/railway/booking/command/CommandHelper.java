package com.railway.booking.command;

import com.railway.booking.command.user.LoginCommand;
import com.railway.booking.command.user.LoginFormCommand;
import com.railway.booking.command.user.LogoutCommand;
import com.railway.booking.command.user.ProfileCommand;
import com.railway.booking.command.user.RegistrationCommand;
import com.railway.booking.command.user.RegistrationForm;
import com.railway.booking.context.ApplicationInjector;
import com.railway.booking.service.UserService;

import java.util.HashMap;
import java.util.Map;

public class CommandHelper {
    private final Map<String, Command> commands;

    public CommandHelper() {
        commands = new HashMap<>();

        UserService userService = ApplicationInjector.getUserService();
        commands.put("login", new LoginCommand(userService));
        commands.put("logout", new LogoutCommand(userService));
        commands.put("registration", new RegistrationCommand(userService));

        commands.put("registrationForm", new RegistrationForm());
        commands.put("loginForm", new LoginFormCommand());
        commands.put("profile", new ProfileCommand());

    }

    public Command getCommand(String requestPath) {
        String commandName = getCommandNameFromPath(requestPath);

        if (!commands.containsKey(commandName)) {
            return commands.get("login");
        }
        return commands.get(commandName);
    }


    private String getCommandNameFromPath(String requestPath) {
        int lastSlashIndex = requestPath.lastIndexOf('/');

        return requestPath.substring(lastSlashIndex + 1);
    }
}