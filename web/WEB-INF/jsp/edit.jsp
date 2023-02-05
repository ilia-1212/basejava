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
    <link rel="stylesheet" href="css/colors.css">
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/edit-resume-styles.css">
    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>

    <form  name=frmResume method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <div class="scrollable-panel">
            <div class="form-wrapper">
                <div class="section">ФИО</div>
                <input class="field" type="text" name="fullName" size="55" placeholder="ФИО" value="${resume.fullName}" required>
                <div class="section">Контакты</div>

                <c:forEach var="type" items = "<%=ContactType.values()%>">
                    <input class="field" type="text" name="${type.name()}" size="30" placeholder="${type.title}" value="${resume.getContact(type)}">
                </c:forEach>
                <div class="spacer"></div>
                <div class="section">Секции</div>

                <c:forEach var="sectionType" items="<%=SectionType.values()%>">
                    <div class="field-label">${sectionType.title}</div>
                    <%--                <c:set var="sectionAll" value="${resume.getSection(sectionType)}"/>--%>
                    <%--                <jsp:useBean id="sectionAll" type="com.urise.webapp.model.Section" />--%>

                    <c:choose>
                        <c:when test="${sectionType == 'PERSONAL' || sectionType == 'OBJECTIVE'}">
                            <c:if test="${resume.getSection(sectionType) != null}">
                                <c:set var="sectionText" value="${resume.getSection(sectionType) }"/>
                                <jsp:useBean id="sectionText" type="com.urise.webapp.model.TextSection"/>
                                <textarea class="field" name="${sectionType}"><%=sectionText.getContent()%></textarea>
                            </c:if>
                        </c:when>

                        <c:when test="${sectionType == 'ACHIEVEMENT' || sectionType == 'QUALIFICATIONS'}">
                            <%--            ${fn:join( sectionKind.getItems,'\\n' )--%>
                            <c:if test="${resume.getSection(sectionType) != null}">
                                <c:set var="sectionList" value="${resume.getSection(sectionType) }"/>
                                <jsp:useBean id="sectionList" type="com.urise.webapp.model.ListSection"/>
                                <textarea class="field" name="${sectionType}"><%= String.join("\n", sectionList.getItems()) %></textarea>
                            </c:if>
                        </c:when>

                        <c:when test="${sectionType eq 'EXPERIENCE' || sectionType eq 'EDUCATION'}">
                            <c:if test="${resume.getSection(sectionType) != null}">
                                <c:set var="sectionOrg" value="${resume.getSection(sectionType) }"/>
                                <jsp:useBean id="sectionOrg" type="com.urise.webapp.model.OrganizationSection"/>

                                <div class="spacer"></div>


                                    <c:forEach var="org" items="<%= sectionOrg.getOrganizations() %>" varStatus="idxOrg">

                                        <input type="text" name="${sectionType.name().concat("HomePageNameOrg").concat(idxOrg.index)}" value="${org.homePage.name}" class="field" placeholder="Организация" size="50">
                                        <input type="text" name="${sectionType.name().concat("HomePageURL").concat(idxOrg.index)}" value="${org.homePage.url}" class="field" placeholder="Сайт" size="50">

                                        <c:forEach var="position" items="${org.positions}">
                                            <jsp:useBean id="position" type="com.urise.webapp.model.Organization.Position"/>
                                            <div class="date-section">
                                                <input type="text" class="date field" placeholder="MM/YYYY" name="${sectionType.name().concat("PositionStartDate").concat(idxOrg.index)}" value="<%= DateUtil.toHtml(position.getStartDate())%>" size=" 50">

                                                <input type="text" class="date field" placeholder="MM/YYYY" name="${sectionType.name().concat("PositionEndDate").concat(idxOrg.index)}" value="<%= DateUtil.toHtml(position.getEndDate())%>" size="50">
                                            </div>

                                            <textarea class="field" placeholder="Заголовок" name="${sectionType.name().concat("PositionTitle").concat(idxOrg.index)}" cols="30" rows="2">${position.title}</textarea>

                                            <textarea class="field" placeholder="Описание" name="${sectionType.name().concat("PositionDescription").concat(idxOrg.index)}" cols="30" rows="2">${position.description}</textarea>
                                            <p>
                                        </c:forEach>
                                        <hr>
                                    </c:forEach>
                            </c:if>
                        </c:when>
                    </c:choose>
                </c:forEach>

                <div class="button-section">
                    <button type="submit" id="btnSave" name="submit" class="green-submit-button" >Сохранить</button>
                    <button type="button" class="red-cancel-button" onclick="window.history.back()">Отменить</button>
                </div>
            </div>
        </div>
    </form>
<%--<jsp:include page="fragments/footer.jsp"/>--%>

</div>
</body>
</html>
