<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="cinematicEntity/producer/edit.do"
	modelAttribute="cinematicEntity">

	<form:hidden path="id" />
	<form:hidden path="version" />

	<acme:textbox code="cinematicEntity.name" path="name" />

	<acme:textbox code="cinematicEntity.surname" path="surname" />

	<acme:datebox code="cinematicEntity.birthdate" path="birthdate" isDate="true"/>

	<acme:textarea code="cinematicEntity.bio" path="bio" />

	<spring:message code="cinematicEntity.list" />
	<form:select path="director">
		<form:option value="true">
			<spring:message code="cinematicEntity.director" />
		</form:option>
		<form:option value="false">
			<spring:message code="cinematicEntity.actor" />
		</form:option>
	</form:select>
	<br/>

	<!-- Buttons -->

	<acme:submit name="save" code="socialIdentity.save" />

	<acme:delete confirmationCode="socialIdentity.confirm.delete"
		buttonCode="socialIdentity.delete" id="${socialIdentity.id}" />

	<acme:cancel url="socialIdentity/list.do" code="socialIdentity.cancel" />

</form:form>