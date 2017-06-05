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

<spring:message code="oq.subtotal" var="subtotalTitle" />

<display:table pagesize="5" class="displaytag" name="orderUser.orderQuantities"
	requestURI="orderUser/display.do" id="row">

	<acme:column code="oq.product" property="${row.product.name}"/>
	<acme:column code="oq.product.price" property="${row.product.price}"/>
	<acme:column code="oq.quantity" property="${row.quantity}"/>
	<display:column title="${subtotalTitle}">
		<jstl:out value="${row.product.price * row.quantity}"></jstl:out>
	</display:column>
	<jstl:if test="${!orderUser.finished}">
		<display:column>
			<acme:link href="orderUser/removeProduct.do?orderQuantityId=${row.id}" code="order.removeProduct"/>
		</display:column>
		<display:column>
			<acme:link href="orderQuantity/edit.do?orderQuantityId=${row.id}" code="order.editQuantity"/>
		</display:column>
	</jstl:if>
</display:table>

<jstl:if test="${!orderUser.finished && orderUser.id != 0}">
	<spring:message code="order.costs"></spring:message>
	<acme:display code="order.total" property="${orderUser.total}"/>
	<acme:link href="orderUser/finish.do" code="order.finish"/>
</jstl:if>