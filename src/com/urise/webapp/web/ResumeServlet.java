package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.*;
import com.urise.webapp.storage.Storage;
import com.urise.webapp.util.WebUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

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
        Boolean isSubmit = (request.getParameter("green-submit-button") != null ? true : false);
        if (!isSubmit) {
            response.sendRedirect("resume");
            return;
        }
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume r = storage.get(uuid);
        if (!WebUtil.isEmpty(fullName)) {
            r.setFullName(fullName.trim());
        }


        for(ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (!WebUtil.isEmpty(value)) {
                r.addContact(type, value.trim());
            } else {
                r.getContacts().remove(type);
            }
        }

//        for(SectionType type : SectionType.values()) {
          for(SectionType type : new SectionType[]{
                  SectionType.PERSONAL,
                  SectionType.OBJECTIVE,
                  SectionType.ACHIEVEMENT,
                  SectionType.QUALIFICATIONS }) {
            String value = request.getParameter(type.name());
            String[] values = request.getParameterValues(type.name());

            if (WebUtil.isEmpty(value) && values.length < 2) {
                r.getSections().remove(type);
            } else {

                switch (type) {
                    case PERSONAL:
                    case OBJECTIVE: {

                        r.addSection(type, new TextSection(value.trim()));
                        break;
                    }
                    case ACHIEVEMENT:
                    case QUALIFICATIONS: {
                        r.addSection(type, new ListSection(value.trim().split("\n")));
                        break;
                    }
                    case EXPERIENCE:
                    case EDUCATION: {

                        break;
                    }
                }
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
                r = storage.get(uuid);
                break;
            case "add" :
            case "edit" :
                if (uuid == null) {
                 uuid = UUID.randomUUID().toString();
                 r = new Resume(uuid, "");
                 storage.save(r);
                } else {
                    r = storage.get(uuid);
                }
                for (SectionType type : SectionType.values()) {
                    if (type == SectionType.OBJECTIVE || type == SectionType.PERSONAL) {
                        TextSection textSection = (TextSection) r.getSection(type);
                        if (textSection == null) {
                            r.addSection(type, TextSection.EMPTY);
                        }
                    }
                    else if (type == SectionType.ACHIEVEMENT || type == SectionType.QUALIFICATIONS) {
                        ListSection listSection = (ListSection) r.getSection(type);
                        if (listSection == null) {
                            r.addSection(type, ListSection.EMPTY);
                        }
                    }
                    else if (type == SectionType.EXPERIENCE || type == SectionType.EDUCATION) {
                        OrganizationSection orgSection = (OrganizationSection) r.getSection(type);
                       //
                    }

                }
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
