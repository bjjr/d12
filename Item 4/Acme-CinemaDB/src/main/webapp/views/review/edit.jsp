<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="${action}" modelAttribute="${modelAttribute}" >

	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<acme:textbox code="review.title" path="title" />
	<acme:textbox code="review.body" path="body" />
	<acme:textbox code="review.rating" path="rating" size="1" />
	<br />

	<acme:submit name="publish" code="review.publish"/>	
	<acme:submit name="save" code="review.draft"/>
	<acme:cancel url="review/list.do?contentId=${contentId}" code="misc.cancel"/>
		
</form:form>