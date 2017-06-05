<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="producer/administrator/create.do" modelAttribute="producerForm">

	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<acme:textbox code="actor.name" path="name" />
	<acme:textbox code="actor.surname" path="surname" />
	<acme:textbox code="actor.email" path="email" />
	<acme:textbox code="actor.phone" path="phone" />
	<acme:textbox code="actor.country" path="country" />
	<acme:textbox code="producer.company" path="company"/>
	
	<br />
	<acme:textbox code="security.username" path="userAccount.username" />
	<acme:password code="security.password" path="userAccount.password" />
	<acme:password code="producer.confirmPassword" path="confirmPassword" />
	
	<br />
	<acme:submit name="save" code="misc.save"/>
	<acme:cancel url="welcome/index.do" code="misc.cancel"/>

</form:form>