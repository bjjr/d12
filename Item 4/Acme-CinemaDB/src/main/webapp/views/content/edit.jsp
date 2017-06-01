<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="content/producer/edit.do" modelAttribute="content">

	<form:hidden path="id" />
	<form:hidden path="version" />

	<acme:textbox code="content.title" path="title" />

	<acme:textbox code="content.year" path="year" />

	<acme:textarea code="content.images" path="images" />

	<acme:textarea code="content.videos" path="videos" />

	<spring:message code="content.genre" />
	<form:select path="genres" multiple="true">
		<form:option value="0">
			<spring:message code="genre.action" />
		</form:option>            
		<form:option value="1">   
			<spring:message code="genre.adventure" />
		</form:option>            
		<form:option value="2">   
			<spring:message code="genre.comedy" />
		</form:option>            
		<form:option value="3">   
			<spring:message code="genre.drama" />
		</form:option>            
		<form:option value="4">   
			<spring:message code="genre.horror" />
		</form:option>            
		<form:option value="5">   
			<spring:message code="genre.animation" />
		</form:option>            
		<form:option value="6">   
			<spring:message code="genre.sci-fi" />
		</form:option>
	</form:select>
	<br />

	<!-- Buttons -->

	<acme:submit name="save" code="socialIdentity.save" />

	<acme:delete confirmationCode="socialIdentity.confirm.delete"
		buttonCode="socialIdentity.delete" id="${content.id}" />

	<acme:cancel url="content/list.do" code="socialIdentity.cancel" />

</form:form>