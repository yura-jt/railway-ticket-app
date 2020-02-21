package com.railway.booking.command.admin;

import com.railway.booking.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PanelCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return "view/admin/adminPanel.jsp";
    }
}