<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="socialIdentity/edit.do" modelAttribute="socialIdentity">

	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<acme:textbox code="socialIdentity.username" path="username" />
	
	<acme:textbox code="socialIdentity.path" path="path" />
	
	<!-- Buttons -->
	
	<acme:submit name="save" code="socialIdentity.save"/>
	
	<acme:delete confirmationCode="socialIdentity.confirm.delete" buttonCode="socialIdentity.delete" id="${socialIdentity.id}"/>
	
	<acme:cancel url="socialIdentity/list.do" code="socialIdentity.cancel"/>

</form:form>