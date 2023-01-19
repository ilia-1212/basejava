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

        String htmlText = makePrettyTable(uuid);
        response.getWriter().write(htmlText);
    }

    private String makePrettyTable(String uuid) {
        String htmlText =
            "<html>\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <link rel=\"stylesheet\" href=\"css/style.css\">\n" +
                    "    <title>Резюме</title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "\n" +
                    "<h2>Список резюме</h2>\n" +
                    "\n" +
                    "<table>\n" +
                    "  <tr>\n" +
                    "    <th> UUID </th>\n" +
                    "    <th> ФИО </th>\n" +
                    "  </tr>\n";

                    if (uuid == null) {
                        for (Resume r : storage.getAllSorted()) {

                            htmlText += "<tr>\n" + "<td>" + r.getUuid()  + "</td>";
                            htmlText += "<td>" + r.getFullName()  + "</td>" + "</tr>";
                        }
                    } else {
                        Resume r = storage.get(uuid);
                        htmlText += "<tr>" + "<td>" + r.getUuid()  + "</td>";
                        htmlText += "<td>" + r.getFullName()  + "</td>" + "</tr>";
                    }

                    htmlText +=
                    "\n" +
                    "</table>\n" +
                    "\n" +
                    "</body>\n" +
                    "</html>";
        return htmlText;
    }
}
