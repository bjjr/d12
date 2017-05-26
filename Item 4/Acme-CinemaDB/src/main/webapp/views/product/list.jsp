<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<!-- Listing grid -->
<display:table pagesize="5" class="displaytag"
	name="products" requestURI="${requestURI}" id="row">
	
	<!-- Attributes -->
	
	<spring:message code="product.picture" var="pic" />
	<display:column title="${pic}">
		<img src="${row.picture}" style="max-width: 150px; max-height: 150px;" />
	</display:column>
	
	<acme:column code="product.name" property="${row.name}"/>
	
	<acme:column code="product.price" property="${row.price}"/>
	
	<acme:column code="product.stock" property="${row.stock}"/>
	
	<acme:column code="product.idcode" property="${row.idcode}"/>
	
	<security:authorize access="hasRole('PRODUCER')">
		<display:column>
			<acme:link href="product/producer/edit.do?productId=${row.id}" code="misc.edit"/>
		</display:column>
	</security:authorize>

	<security:authorize access="hasRole('USER')">
		<jstl:if test="${row.stock > 0}">
			<display:column>
				<acme:link href="orderUser/addProduct.do?productId=${row.id}" code="order.addProduct"/>
			</display:column>
		</jstl:if>
	</security:authorize>
	
</display:table>