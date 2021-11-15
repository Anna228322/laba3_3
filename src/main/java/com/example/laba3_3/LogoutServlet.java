package com.example.laba3_3;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        AccountService accountService = MainServlet.SERVICE_LOCATOR.getAccountService();

        accountService.logOut(request.getSession().getId());

        String path = Utils.ROOT_URL + "login";
        response.sendRedirect(path);
    }
}
