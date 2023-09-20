<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="constants.ForwardConst" %>
<%@ page import="constants.AttributeConst" %>

<c:set var="action" value="${ForwardConst.ACT_WORK.getValue()}" />
<c:set var="commGo" value="${ForwardConst.CMD_CREATE_GO.getValue()}" />
<c:set var="commBack" value="${ForwardConst.CMD_CREATE_BACK.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
            <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>

        <h2>出退勤登録</h2>

        <form method="POST" action="<c:url value='?action=${action}&command=${commGo}' />">
            <input type="hidden" name="${AttributeConst.WOR_ID.getValue()}" value="${workmanagement.id}" />
            <input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
            <button type="submit">出勤</button>
        </form>

        <form method="POST" action="<c:url value='?action=${action}&command=${commBack}' />">
        <input type="hidden" name="${AttributeConst.WOR_ID.getValue()}" value="${workmanagement.id}" />
        <input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
            <button type="submit">退勤</button>
        </form>

    </c:param>
</c:import>
