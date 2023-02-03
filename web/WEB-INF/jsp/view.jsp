<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <h2> ${resume.fullName}&nbsp; <a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
    <p>
        <c:set var="isShowContacts" value="true"/>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <c:choose>
                <c:when test="${isShowContacts}">
                    <h3>Контакты:</h3><hr><p>
                    <c:set var="isShowContacts" value="false"/>
                </c:when>
            </c:choose>
            <jsp:useBean id="contactEntry" type="java.util.Map.Entry<com.urise.webapp.model.ContactType, java.lang.String>"/>
            <%=contactEntry.getKey().toHtml(contactEntry.getValue()) %><br/>
        </c:forEach>
    </p>

        <c:set var="isShowSections" value="true"/>
        <c:forEach var="sectionEntry" items="${resume.sections}">
            <c:choose>
                <c:when test="${isShowSections}">
                    <h3>Секции:</h3><hr><p>
                    <c:set var="isShowSections" value="false"/>
                </c:when>
            </c:choose>
            <jsp:useBean id="sectionEntry" type="java.util.Map.Entry<com.urise.webapp.model.SectionType, com.urise.webapp.model.Section>"/>
            <c:set var="sectionType" value="<%=sectionEntry.getKey()%>"/>
                <h4>${sectionType.title}</h4>
                <c:set var="sectionKind" value="${resume.getSection(sectionType)}"/>
                <c:choose>
                    <c:when test="${sectionType eq 'PERSONAL' || sectionType eq 'OBJECTIVE'}">
                        <c:set var="textSection" value="${sectionKind}"/>
                        <jsp:useBean id="textSection" class="com.urise.webapp.model.TextSection"/>
                        <p>${textSection.content} <br>
                    </c:when>

                    <c:when test="${sectionType eq 'ACHIEVEMENT' || sectionType eq 'QUALIFICATIONS'}">
                        <c:set var="listSection" value="${sectionKind}"/>
                        <jsp:useBean id="listSection" class="com.urise.webapp.model.ListSection"/>
                        <ul>
                            <c:forEach var="item" items="<%=listSection.getItems()%>">
                                <li>${item} </li>
                            </c:forEach>
                        </ul>
                    </c:when>
                    <c:when test="${sectionType eq 'EXPERIENCE' || sectionType eq 'EDUCATION'}">
                        <c:set var="orgSection" value="${sectionKind}"/>
                        <jsp:useBean id="orgSection" class="com.urise.webapp.model.OrganizationSection"/>

                        <c:forEach var="org" items="<%=orgSection.getOrganizations()%>">
                            <jsp:useBean id="org" class="com.urise.webapp.model.Organization"/>
                            <%=org.toHtml()%> <br>
                        </c:forEach>

                    </c:when>
                </c:choose>
        </c:forEach>
    </p>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
