package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.*;
import com.urise.webapp.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.storage = Config.get().getStorage();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Boolean isSubmit = (request.getParameter("submit") != null ? true : false);
        if (!isSubmit) {
            response.sendRedirect("resume");
            return;
        }
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume r = storage.get(uuid);
        r.setFullName(fullName);
        for(ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                r.addContact(type, value);
            } else {
                r.getContacts().remove(type);
            }
        }

        for(SectionType type : SectionType.values()) {
            Section value = null;
            switch (type) {
                case PERSONAL :
                case OBJECTIVE : {
                    value = new TextSection(request.getParameter(type.name()));
                    break;
                }
                case ACHIEVEMENT:
                case QUALIFICATIONS: {
                    value = new ListSection(request.getParameterValues(type.name()));
                    break;
                }
                case EXPERIENCE:
                case EDUCATION: {

                    break;
                }
            }

            if (value != null ) {
                r.addSection(type, value);
            } else {
                r.getSections().remove(type);
            }
        }
        storage.update(r);
        response.sendRedirect("resume");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");

        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume r;
        switch (action) {
            case "delete" :
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view" :
            case "edit" :
                r = storage.get(uuid);
                break;
            default : throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume" , r);
        request.getRequestDispatcher(
                ("view".equals(action) ? "WEB-INF/jsp/view.jsp" : "WEB-INF/jsp/edit.jsp")
        ).forward(request, response);

    }

    public Storage initStorage() {
        return Config.get().getStorage();
    }
}
