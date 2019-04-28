<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="product" required="true" type="de.hybris.platform.commercefacades.product.data.ProductData" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="product-classifications">
    <c:if test="${not empty product.sartoriusClassifications}">
        <div class="headline"><spring:theme code="text.product.sartorius.classification.header" /></div>
        <table class="table">
            <tbody>
            <c:forEach items="${product.sartoriusClassifications}" var="classification">
                <tr>
                    <td class="attrib">${fn:escapeXml(classification)}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
</div>