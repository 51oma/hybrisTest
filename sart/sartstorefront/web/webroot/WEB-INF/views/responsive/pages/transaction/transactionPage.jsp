<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<spring:htmlEscape defaultHtmlEscape="true"/>

<div class="span-20 last">
    <div class="span-20 last order_history_information">
        <div class="span-6">
            <div class="item_container_holder ">
                <div class="title_holder">
                    <h2>Transaction Details</h2>
                </div>
            </div>
        </div>
        <div class="span-20 last">
            <table border="1px solid black">
                <thead>
                <tr>
                    <th id="header1"><spring:theme text="Transaction ID"/></th>
                    <th id="header2"><spring:theme text="Transaction Date"/></th>
                    <th id="header3"><spring:theme text="Amount"/></th>
                    <th id="header4"><spring:theme text="Description"/></th>
                    <th id="header5"><spring:theme text="Start Date"/></th>
                    <th id="header6"><spring:theme text="End Date"/></th>
                    <th id="header7"><spring:theme text="Filename"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${transactions}" var="transaction">
                    <tr>
                        <td headers="header1">${transaction.transactionId}</td>
                        <td headers="header2"><fmt:formatDate value="${transaction.transactionDate}"
                                                              pattern="dd-MM-yyyy"/></td>
                        <td headers="header3">${transaction.amount}</td>
                        <td headers="header4">${transaction.description}</td>
                        <td headers="header5"><fmt:formatDate value="${transaction.startDate}"
                                                              pattern="dd-MM-yyyy"/></td>
                        <td headers="header6"><fmt:formatDate value="${transaction.endDate}" pattern="dd-MM-yyyy"/></td>
                        <td headers="header7">${transaction.fileName}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>