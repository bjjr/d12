<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="campaign/producer/create.do" modelAttribute="campaign" >
	
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	
	<acme:datebox code="campaign.start" path="start" isDate="true"/>
	<acme:datebox code="campaign.end" path="end" isDate="true"/>
	<acme:textarea code="campaign.images" path="images"/>
	<acme:textbox code="campaign.max" path="max"/>
	
	<acme:submit name="save" code="misc.save"/>
	<acme:cancel url="campaign/producer/list.do" code="misc.cancel"/>
		
</form:form>