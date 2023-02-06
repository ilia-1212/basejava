package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.*;
import com.urise.webapp.storage.Storage;
import com.urise.webapp.util.DateUtil;
import com.urise.webapp.util.WebUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        Boolean isSubmit = (request.getParameter("submit") != null);
        if (!isSubmit) {
            response.sendRedirect("resume");
            return;
        }
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume r;
        if (WebUtil.isEmpty(fullName)) {
            response.sendRedirect("resume");
            return;
        } else if (WebUtil.isEmpty(uuid)) {
             r = new Resume(fullName);
             setEmptyResume(r);
        } else {
             r = storage.get(uuid);

            if (!WebUtil.isEmpty(fullName)) {
                r.setFullName(fullName.trim());
            }
        }

        for(ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (!WebUtil.isEmpty(value)) {
                r.addContact(type, value.trim());
            } else {
                r.getContacts().remove(type);
            }
        }

       for(SectionType type : SectionType.values()) {
//          for(SectionType type : new SectionType[]{
//                  SectionType.PERSONAL,
//                  SectionType.OBJECTIVE,
//                  SectionType.ACHIEVEMENT,
//                  SectionType.QUALIFICATIONS }) {
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
                        String[] valuesUrl = request.getParameterValues(type.name() + "HomePageURL");
                        List<Organization> orgList = new ArrayList<>();

                        for (int i = 0; i < values.length; i++) {
                            if (!WebUtil.isEmpty(values[i])) {

                                List<Organization.Position> positionList = new ArrayList<>();
                                String[] valuesPosStartDate = request.getParameterValues(type.name() + "PositionStartDate" + i);
                                String[] valuesPosEndDate = request.getParameterValues(type.name() + "PositionEndDate" + i);
                                String[] valuesPosTitle = request.getParameterValues(type.name() + "PositionTitle" + i);
                                String[] valuesPosDesc = request.getParameterValues(type.name() + "PositionDescription" + i);

                                for (int j = 0; j < valuesPosTitle.length; j++) {
                                    if (!WebUtil.isEmpty(valuesPosTitle[j])) {
                                        positionList.add(new Organization.Position(DateUtil.fromHtml(valuesPosStartDate[j]) , DateUtil.fromHtml(valuesPosEndDate[j]), valuesPosTitle[j], valuesPosDesc[j]));
                                    }
                                }
                                orgList.add(new Organization( new Link(values[i], valuesUrl[i]), positionList));
                            }
                        }
                        r.addSection(type, new OrganizationSection(orgList));
                        break;
                    }
                }
            }
        }
        if ((WebUtil.isEmpty(uuid))) {
            storage.save(r);
        } else {
            storage.update(r);
        }

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
                r = new Resume();
                setEmptyResume(r);
                break;
            case "edit" :
                r = storage.get(uuid);
                setEmptyResume(r);
                break;
            default : throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume" , r);
        request.getRequestDispatcher(
                ("view".equals(action) ? "WEB-INF/jsp/view.jsp" : "WEB-INF/jsp/edit.jsp")
        ).forward(request, response);

    }

    private void setEmptyResume(Resume r) {
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
                List<Organization> emptyOrgSection = new ArrayList<>();
                emptyOrgSection.add(Organization.EMPTY);
                if (orgSection != null) {
                    for (Organization org : orgSection.getOrganizations()) {
                        List<Organization.Position> emptyPosSection = new ArrayList<>();

                        emptyPosSection.add(Organization.Position.EMPTY);
                        emptyPosSection.addAll(org.getPositions());

                        emptyOrgSection.add(new Organization(org.getHomePage(), emptyPosSection));
                    }
                }
                r.addSection(type, new OrganizationSection(emptyOrgSection));
            }
        }
    }

    public Storage initStorage() {
        return Config.get().getStorage();
    }
}
