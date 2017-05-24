<%--
 * index.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p>
	<spring:message code="welcome.greeting.prefix" />
	<jstl:choose>
		<jstl:when test="${name != ''}">${name}</jstl:when>
		<jstl:otherwise>
			<spring:message code="welcome.greeting.guest" />
		</jstl:otherwise>
	</jstl:choose>
	<spring:message code="welcome.greeting.suffix" />
</p>

<jstl:if test="${banner != ''}">
	<img src="${banner}" alt="Banner" style="width: 25vw" />
</jstl:if>

<p>
	<spring:message code="welcome.greeting.current.time" />
	${moment}
</p>

<jstl:if test="${conf != null}">
	<p style="color: green;">
		<spring:message code="${conf}" />
	</p>
</jstl:if>

<div id="legal" style="background-color: #bababa;">
	<spring:message code="welcome.legal" />
	<button id="hide">OK</button>
</div>