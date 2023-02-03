<%@ page import="com.urise.webapp.model.ContactType" %>
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
    <form  name=frmResume method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя: </dt>
            <dd><input type="text" name="fullName" size="50" value="${resume.fullName}">
        </dl>
        <h3>Контакты:</h3>
        <hr>
        <p>
            <c:forEach var="type" items = "<%=ContactType.values()%>">
                <dl>
                    <dt> <h4>${type.title}</h4></dt>
                    <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
                </dl>
            </c:forEach>
        </p>
        <h3>Секции:</h3>
        <hr>
        <p>
        <c:forEach var="sectionType" items="<%=SectionType.values()%>">
            <h4>${sectionType.title} </h4>
            <c:set var="sectionKind" value="${resume.getSection(sectionType)}"/>
            <c:choose>
                <c:when test="${sectionType eq 'PERSONAL' || sectionType eq 'OBJECTIVE'}">
                    <c:set var="textSection" value="${sectionKind}"/>
                    <jsp:useBean id="textSection" class="com.urise.webapp.model.TextSection"/>
                    <p>
                    <br>
                    <input type="text" name="${sectionType}" size=130  value="${textSection.content}">
                    <br>
                </c:when>

                <c:when test="${sectionType eq 'ACHIEVEMENT' || sectionType eq 'QUALIFICATIONS'}">
                    <c:set var="listSection" value="${sectionKind}"/>
                    <jsp:useBean id="listSection" class="com.urise.webapp.model.ListSection"/>
                    <ul>
                        <c:forEach var="item" items="<%=listSection.getItems()%>">
                            <li>
                                <textarea name="${sectionType}" rows="10" cols="110"> ${item} </textarea>
                            </li>
                        </c:forEach>
                    </ul>
                </c:when>
                <c:when test="${sectionType eq 'EXPERIENCE' || sectionType eq 'EDUCATION'}">
                    <c:set var="orgSection" value="${sectionKind}"/>
                    <jsp:useBean id="orgSection" class="com.urise.webapp.model.OrganizationSection"/>

                    <c:forEach var="org" items="<%=orgSection.getOrganizations()%>">
                        <jsp:useBean id="org" class="com.urise.webapp.model.Organization"/>
<%--                        <%=org.toHtml()%> <br>--%>
                    </c:forEach>
                </c:when>
            </c:choose>
        </c:forEach>
        <hr>
        <button type="submit" id="btnSave" name="submit">Сохранить</button>
        <button type="submit" name="cancel" onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
