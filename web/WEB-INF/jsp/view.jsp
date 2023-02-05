<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page import="com.urise.webapp.util.WebUtil" %>
<%@ page import="com.urise.webapp.util.DateUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/colors.css">
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/view-resume-styles.css">
    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>

<div class="scrollable-panel">
    <div class="form-wrapper">
        <div class="full-name">${resume.fullName}
            <a class="no-underline-anchor" href="resume?uuid=${resume.uuid}&amp;action=edit">
                <img src="img/pencil.png" alt="Edit">
            </a>
        </div>

        <div class="contacts">
            <c:set var="isShowContacts" value="true"/>
            <c:forEach var="contactEntry" items="${resume.contacts}">
                <c:if test="${isShowContacts}">
                    <c:set var="isShowContacts" value="false"/>
                </c:if>
                <jsp:useBean id="contactEntry" type="java.util.Map.Entry<com.urise.webapp.model.ContactType, java.lang.String>"/>
                <div><%=contactEntry.getKey().toHtml(contactEntry.getValue()) %></div>
            </c:forEach>
        </div>


        <c:set var="isShowSections" value="true"/>
        <c:forEach var="sectionEntry" items="${resume.sections}">
            <div class="spacer"></div>

            <jsp:useBean id="sectionEntry" type="java.util.Map.Entry<com.urise.webapp.model.SectionType, com.urise.webapp.model.Section>"/>
            <c:set var="sectionType" value="<%=sectionEntry.getKey()%>"/>
            <c:set var="sectionKind" value="${resume.getSection(sectionType)}"/>

            <div class="section">${sectionType.title}</div>

            <c:choose>
                <c:when test="${sectionType == 'OBJECTIVE' || sectionType == 'PERSONAL'}">
                    <c:set var="textSection" value="${sectionKind}"/>
                    <jsp:useBean id="textSection" type="com.urise.webapp.model.TextSection"/>
                    <c:choose>
                        <c:when test="${sectionType == 'OBJECTIVE'}">
                            <div class="position"><%=textSection.getContent()%></div>
                        </c:when>
                        <c:otherwise><div class="qualities"><%=textSection.getContent()%></div></c:otherwise>
                    </c:choose>

                </c:when>

                <c:when test="${sectionType == 'ACHIEVEMENT' || sectionType == 'QUALIFICATIONS'}">
                    <c:set var="listSection" value="${sectionKind}"/>
                    <jsp:useBean id="listSection" type="com.urise.webapp.model.ListSection"/>
                    <ul class="list">
                        <c:forEach var="item" items="<%=listSection.getItems()%>">
                            <li>${item}</li>
                        </c:forEach>
                    </ul>
                </c:when>
                <c:when test="${sectionType == 'EXPERIENCE' || sectionType == 'EDUCATION'}">
                    <c:set var="orgSection" value="${sectionKind}"/>
                    <jsp:useBean id="orgSection" type="com.urise.webapp.model.OrganizationSection"/>
                    <c:if test="${orgSection != null}">

                        <div class="section-wrapper">

                            <c:forEach var="org" items="<%=orgSection.getOrganizations()%>">
                                <%--                                <c:forEach var="org" items="${OrgSection.getOrganizations()}">--%>
                                <jsp:useBean id="org" type="com.urise.webapp.model.Organization"/>

                                <c:choose>
                                    <c:when test="${WebUtil.isEmpty(org.homePage.url)}">
                                        <div class="job-name">${org.homePage.name}</div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="job-name"><a class="contact-link" href="${org.homePage.url}">${org.homePage.name}</a></div>
                                    </c:otherwise>
                                </c:choose>

                                <c:forEach var="position" items="${org.getPositions()}">
                                    <div class="period-position">
                                        <jsp:useBean id="position" type="com.urise.webapp.model.Organization.Position"/>

                                        <div class="period">${WebUtil.PeriodDatesPosition(position)}</div>
                                        <div class="position">${position.title}</div>
                                    </div>
                                    <div class="description">${position.description}</div>
                                </c:forEach>
                            </c:forEach>
                        </div>
                    </c:if>
                </c:when>
            </c:choose>
        </c:forEach>
        <div class="footer-spacer"></div>
    </div>
</div>

<%--<jsp:include page="fragments/footer.jsp"/>--%>
</body>
</html>
