<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="invoice/administrator/create.do" modelAttribute="invoice" >
	
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="paid"/>
	<form:hidden path="total"/>
	
	<acme:textbox code="invoice.billingDate" path="billingDate" readonly="true"/>
	<acme:select items="${campaigns}" itemLabel="id" code="invoice.campaigns" path="campaign"/>
	
	<acme:submit name="save" code="misc.save"/>
	<acme:cancel url="invoice/administrator/list.do" code="misc.cancel"/>
		
</form:form>