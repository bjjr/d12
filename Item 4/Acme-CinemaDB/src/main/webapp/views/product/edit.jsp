<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="product/producer/edit.do"
	modelAttribute="product">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="content" />

	<acme:textbox code="product.name" path="name" />
	
	<acme:textbox code="product.price" path="price" />
	
	<acme:textbox code="product.stock" path="stock" />
	
	<acme:textbox code="product.picture" path="picture" />
	

	<!-- Buttons -->

	<acme:submit name="save" code="misc.save" />

	<acme:cancel url="/" code="misc.cancel" />

</form:form>