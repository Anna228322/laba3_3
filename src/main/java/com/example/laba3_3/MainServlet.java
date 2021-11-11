package com.example.laba3_3;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/")
public class MainServlet extends HttpServlet {
    public final static ServiceLocator SERVICE_LOCATOR = new ServiceLocator();

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (isAuthorized(request))
                processMainPage(request, response);
            else
                redirectToAuth(request, response);
        } catch (Exception e) {
            processError(response, e);
        }
    }

    private void redirectToAuth(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = request.getContextPath() + "/login";
        response.sendRedirect(path);
    }

    private boolean isAuthorized(HttpServletRequest request) {
        return SERVICE_LOCATOR.getAccountService()
                .isUserAuthorized(request.getSession().getId());
    }

    private void processError(HttpServletResponse response, Exception e) {
        try {
            response.getWriter().printf("An error has occurred:\n\t %s\n", e.getLocalizedMessage());
            e.printStackTrace();
        }
        catch (Exception in) {
            System.out.printf("Outer: \n\t%s\nInternal:\n\t%s\n", e.getLocalizedMessage(), in.getLocalizedMessage());
            in.printStackTrace();
        }
    }

    private void processMainPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        setMainPagesAttributes(request);
        request.getRequestDispatcher("main_page.jsp").forward(request, response);
    }

    private void setMainPagesAttributes(HttpServletRequest request) {
        String login = SERVICE_LOCATOR.getAccountService().getUserBySessionId(request.getSession().getId()).getLogin();

        String path = request.getParameter("path");
        boolean isValid = SERVICE_LOCATOR.getValidator().validatePath(path);
        String pathToken = isValid ? "Path = " + path : "Path was not correct";
        List<FileModel> directories = isValid ?
                SERVICE_LOCATOR.getPathReader().getUserFileSystem(path, login)
                : new ArrayList<>();
        if (directories == null) {
            pathToken = "Path was not correct";
            directories = new ArrayList<>();
        }
        String dateToken = new Timestamp(System.currentTimeMillis()).toString();

        request.setAttribute("name", "app name");
        request.setAttribute("date_now", dateToken);
        request.setAttribute("path", path);
        request.setAttribute("pathToken", pathToken);
        request.setAttribute("dirs", directories);
    }
}