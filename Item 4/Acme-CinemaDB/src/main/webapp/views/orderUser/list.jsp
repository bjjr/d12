<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<spring:message code="order.status" var="statusTitle" />
	
<display:table pagesize="5" class="displaytag" name="orders"
	requestURI="order/list.do" id="row">

	<acme:column code="order.moment" property="moment" isTimestamp="true" />
	<acme:column code="order.total" property="${row.total}"/>
	
	<display:column title="${statusTitle}">
		<jstl:choose>
			<jstl:when test="${row.status.kind eq 0}">
				<spring:message code="status.inProcess" />
			</jstl:when>
			<jstl:when test="${row.status.kind eq 1}">
				<spring:message code="status.sent" />
			</jstl:when>
			<jstl:when test="${row.status.kind eq 2}">
				<spring:message code="status.cancelled" />
			</jstl:when>
		</jstl:choose>
	</display:column>
	
	<acme:column code="order.shippingAddress" property="${row.shippingAddress.saName}"/>
	
	<display:column>
		<acme:link href="order/display.do?orderId=${row.id}" code="misc.view"/>
	</display:column>

</display:table>
