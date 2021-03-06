package com.example.laba3_3;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        AccountService accountService = MainServlet.SERVICE_LOCATOR.getAccountService();

        User user = extractUser(request);
        if (accountService.isUserAuthorized(request.getSession().getId()))
            accountService.logOut(request.getSession().getId());
        LoginResult result = accountService.logInUser(user, request.getSession());

        if (result == LoginResult.OK)
            redirectToMainPage(request, response);
        else
            backWithError(request, response, getErrorMessage(result));
    }

    private void backWithError(HttpServletRequest request, HttpServletResponse response, String error) throws ServletException, IOException {
        response.setContentType("text/html");
        request.setAttribute("error", error);
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    private String getErrorMessage(LoginResult res) {
        switch (res) {
            case PROFILE_NOT_FOUND: return "No user found with specified username";
            case INCORRECT_PASSWORD: return "Invalid password";
            default: return null;
        }
    }

    private User extractUser(HttpServletRequest request) {
        String login = null;
        String email = null;
        String password = request.getParameter("password");
        String username = request.getParameter("username");
        if (username.contains("@"))
            email = username;
        else
            login = username;

        User result = new User(login, password, email);
        result.setId(login);
        return result;
    }

    private void redirectToMainPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = request.getContextPath().split("/login")[0] + "?path=/";
        response.sendRedirect(path);
    }
}
