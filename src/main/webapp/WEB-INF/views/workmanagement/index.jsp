<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="constants.AttributeConst" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="actWork" value="${ForwardConst.ACT_WORK.getValue()}"/>
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}"/>
<c:set var="commNew" value="${ForwardConst.CMD_NEW.getValue()}"/>

<c:import url="../layout/app.jsp">
    <c:param name = "content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>${work_year}年　${work_month}月の勤務履歴</h2>
        <a href="<c:url value='?action=${actWork}&command=${commNew}' />">出退勤の登録はこちら</a>
        <table id="workmanagement_list">
            <tbody>
                <tr>
                    <th class = "work_day">月日</th>
                    <th class = "work_go">出勤時刻</th>
                    <th class = "work_back">退勤時刻</th>
                </tr>

                <c:forEach var="Workmmanagement" items="${work_day}" varStatus="status">

                    <tr class="row${status.count % 2}">
                        <td class = "work_day"><c:out value="${status.current}"/></td>
                        <td class = "work_go"><c:out value='${go_time[status.index]}'/></td>
                        <td class = "work_back"><c:out value='${back_time[status.index]}'/></td>
                        </tr>
                </c:forEach>

            </tbody>
        </table>
    </c:param>
</c:import>

