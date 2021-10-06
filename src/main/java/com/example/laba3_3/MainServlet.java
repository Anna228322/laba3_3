package com.example.laba3_3;

import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/")
public class MainServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String path = request.getParameter("path");
        File dir = new File(path);
        if (dir.isDirectory()) {
            File[] arrayFiles = dir.listFiles();
            List<File> list = Arrays.asList(arrayFiles);
            response.setContentType("text/html");
            //request.setAttribute("date", new Timestamp(System.currentTimeMillis()));
            request.setAttribute("date", new SimpleDateFormat("MM.dd.yyyy HH:mm:ss")
                    .format(new Timestamp(System.currentTimeMillis())));
            request.setAttribute("path", request.getParameter("path"));
            request.setAttribute("directoryList", list);
            request.getRequestDispatcher("Home_page.jsp").forward(request, response);
        } else {
            String fileName = path.split("/")[path.split("/").length - 1];
            response.setHeader("Content-disposition", "attachment; filename=" + fileName);
            OutputStream out = response.getOutputStream();
            FileInputStream in = new FileInputStream(path);
            byte[] buffer = new byte[4096];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
            in.close();
            out.flush();
        }
    }

}