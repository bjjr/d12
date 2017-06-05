<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="${action }" modelAttribute="season">

	<form:hidden path="id" />
	<form:hidden path="tvShow" />

	<acme:textbox code="actor.name" path="name" />
	<br />

	<!-- Buttons -->

	<acme:submit name="save" code="socialIdentity.save" />

	<acme:cancel url="${listURL }" code="socialIdentity.cancel" />

</form:form>