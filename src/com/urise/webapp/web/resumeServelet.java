package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.Storage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class resumeServelet extends HttpServlet {
    private  Storage storage = Config.get().getStorage();

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your PG JDBC Driver?");
            e.printStackTrace();
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        //response.setHeader("Content-Type","text/html; charset=UTF-8");

        response.setContentType("text/html; charset=UTF-8");
        String uuid = request.getParameter("uuid");
        //response.getWriter().write(name == null ? "Hello from deep inside" : "Hello, " + name);

        String htmlText = makeResumeTable(uuid);
        response.getWriter().write(htmlText);
    }

    private String makeResumeTable(String uuid) {
        String htmlText =
                "<table style=\"width:100%\">" +
                "<tr>" +
                "<td>ID</td>" +
                "<td>Name</td>" +
                "</tr>";

        if (uuid == null) {
            for (Resume r : storage.getAllSorted()) {
                htmlText += "<tr>" + "<td>" + r.getUuid()  + "</td>";
                htmlText += "<td>" + r.getFullName()  + "</td>" + "</tr>";
            }
        } else {
            Resume r = storage.get(uuid);
            htmlText += "<tr>" + "<td>" + r.getUuid()  + "</td>";
            htmlText += "<td>" + r.getFullName()  + "</td>" + "</tr>";
        }

        htmlText +=  "</table>";
        return htmlText;
    }
}
