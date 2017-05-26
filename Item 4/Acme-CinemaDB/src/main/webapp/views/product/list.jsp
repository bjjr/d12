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
	<acme:column code="product.name" property="${row.name}"/>
	
	<acme:column code="product.price" property="${row.price}"/>
	
	<acme:column code="product.stock" property="${row.stock}"/>
	
	<acme:column code="product.picture" property="${row.picture}"/>
	
	<acme:column code="product.idcode" property="${row.idcode}"/>
	
	<security:authorize access="hasRole('PRODUCER')">
		<acme:link href="/product/producer/edit.do?productId=${row.id}" code="misc.edit"/>
	</security:authorize>
			
	
	
</display:table>