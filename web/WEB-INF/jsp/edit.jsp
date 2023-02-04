<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page import="com.urise.webapp.model.ListSection" %>
<%@ page import="com.urise.webapp.model.TextSection" %>
<%@ page import="com.urise.webapp.util.DateUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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
        <hr>
        <h3>Контакты:</h3>
        <p>
            <c:forEach var="type" items = "<%=ContactType.values()%>">
                <dl>
                    <dt> <h4>${type.title}</h4></dt>
                    <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
                </dl>
            </c:forEach>
        </p>

        <hr>
        <h3>Секции:</h3>
        <p>
        <c:forEach var="sectionType" items="<%=SectionType.values()%>">
            <h4>${sectionType.title} </h4>
<%--                <c:set var="sectionAll" value="${resume.getSection(sectionType)}"/>--%>
<%--                <jsp:useBean id="sectionAll" type="com.urise.webapp.model.Section" />--%>

            <c:choose>
                <c:when test="${sectionType == 'PERSONAL' || sectionType == 'OBJECTIVE'}">
                    <c:if test="${resume.getSection(sectionType) != null}">
                        <c:set var="sectionText" value="${resume.getSection(sectionType) }"/>
                        <jsp:useBean id="sectionText" type="com.urise.webapp.model.TextSection"/>
                        <textarea name="${sectionType}" rows="5" cols="110"><%=sectionText.getContent()%></textarea>
                    </c:if>
                </c:when>

                <c:when test="${sectionType == 'ACHIEVEMENT' || sectionType == 'QUALIFICATIONS'}">
<%--            ${fn:join( sectionKind.getItems,'\\n' )--%>
                    <c:if test="${resume.getSection(sectionType) != null}">
                        <c:set var="sectionList" value="${resume.getSection(sectionType) }"/>
                        <jsp:useBean id="sectionList" type="com.urise.webapp.model.ListSection"/>
                        <textarea name="${sectionType}" rows="10" cols="110"><%= String.join("\n", sectionList.getItems()) %></textarea>
                    </c:if>
                </c:when>

                <c:when test="${sectionType eq 'EXPERIENCE' || sectionType eq 'EDUCATION'}">
                    <c:if test="${resume.getSection(sectionType) != null}">
                        <c:set var="sectionOrg" value="${resume.getSection(sectionType) }"/>
                        <jsp:useBean id="sectionOrg" type="com.urise.webapp.model.OrganizationSection"/>
                        <c:forEach var="org" items="<%= sectionOrg.getOrganizations() %>" varStatus="idxOrg">
                            <input type="text" name="${sectionType.name().concat("HomePageNameOrg").concat(idxOrg.index)}" value="${org.homePage.name}" size="50">
                            <input type="text" name="${sectionType.name().concat("HomePageURL").concat(idxOrg.index)}" value="${org.homePage.url}" size="50">

                            <c:forEach var="position" items="${org.positions}">
                                <p>
                                <input type="text" name="${sectionType.name().concat("PositionTitle").concat(idxOrg.index)}" value="${position.title}" size="50">
                                <input type="text" name="${sectionType.name().concat("PositionDescription").concat(idxOrg.index)}" value="${position.description}" size="50">

                                 <jsp:useBean id="position" type="com.urise.webapp.model.Organization.Position"/>
                                <input type="text" name="${sectionType.name().concat("PositionStartDate").concat(idxOrg.index)}" value="<%= DateUtil.toHtml(position.getStartDate())%>" size=" 50">
                                <input type="text" name="${sectionType.name().concat("PositionEndDate").concat(idxOrg.index)}" value="<%= DateUtil.toHtml(position.getEndDate())%>" size="50">
                            </c:forEach>
                            <br>
                        </c:forEach>

                    </c:if>

                </c:when>
            </c:choose>
        </c:forEach>
        <hr>
        <button type="submit" id="btnSave" name="submit" >Сохранить</button>
        <button type="submit" name="cancel" onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
