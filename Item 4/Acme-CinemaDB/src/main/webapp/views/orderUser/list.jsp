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

<acme:link href="orderUser/list.do?displayCategory=0" code="order.display.inProgress"/>
<acme:link href="orderUser/list.do?displayCategory=1" code="order.display.sent"/>
<acme:link href="orderUser/list.do?displayCategory=2" code="order.display.cancelled"/>
<br/><br/>
	
<display:table pagesize="5" class="displaytag" name="orders"
	requestURI="orderUser/list.do" id="row">

	<acme:column code="order.moment" property="moment" isTimestamp="true" />
	<acme:column code="order.total" property="${row.total}"/>
	
	<display:column title="${statusTitle}">
		<jstl:choose>
			<jstl:when test="${row.status eq 0}">
				<spring:message code="status.inProcess" />
			</jstl:when>
			<jstl:when test="${row.status eq 1}">
				<spring:message code="status.sent" />
			</jstl:when>
			<jstl:when test="${row.status eq 2}">
				<spring:message code="status.cancelled" />
			</jstl:when>
		</jstl:choose>
	</display:column>
	
	<security:authorize access="hasRole('USER')">
		<spring:message code="order.shippingAddress" var="shippingTitle" />
		<display:column title="${shippingTitle}">
			<jstl:if test="${row.shippingAddress != null}">
				<jstl:out value="${row.shippingAddress.saName}" />
			</jstl:if>
			
			<jstl:if test="${row.shippingAddress == null}">
				<spring:message code="sa.deleted" />
			</jstl:if>
		</display:column>
	</security:authorize>
	
	<display:column>
		<acme:link href="orderUser/display.do?orderUserId=${row.id}" code="misc.view"/>
	</display:column>
	
	<security:authorize access="hasRole('ADMIN')">
		<jstl:if test="${row.status == 0}">
			<display:column>
				<acme:link href="orderUser/administrator/send.do?orderUserId=${row.id}" code="order.send"/>
			</display:column>
		
			<display:column>
				<acme:link href="orderUser/administrator/cancel.do?orderUserId=${row.id}" code="order.cancel"/>
			</display:column>
		</jstl:if>
	</security:authorize>

</display:table>
