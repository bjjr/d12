<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="orderUser/finish.do" modelAttribute="orderUser" >
	
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	
	<acme:select items="${addresses}" itemLabel="saName" code="order.shippingAddress" path="shippingAddress"/>
	<br/>

	<acme:submit name="save" code="misc.save"/>
	<acme:cancel url="/orderUser/display.do" code="misc.cancel"/>
		
</form:form>
<br/>