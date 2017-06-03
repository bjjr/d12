<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<!-- Listing grid -->

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="socialIdentities" requestURI="${requestURI}" id="row">

	<!-- Attributes -->

	<acme:column code="socialIdentity.username" property="${row.username }" />

	<spring:message code="socialIdentity.path" var="urlSN" />
	<display:column title="${urlSN }">
		<a href="${row.path }">${row.path }</a>
	</display:column>

	<security:authorize access="isAuthenticated()">
		<display:column>
			<acme:link href="socialIdentity/edit.do?socialIdentityId=${row.id}"
				code="socialIdentity.edit" />
		</display:column>
	</security:authorize>
</display:table>

<security:authorize access="isAuthenticated()">
	<br />
	<input type="button" name="createSocialIdentity"
		value="<spring:message code="socialIdentity.create" />"
		onclick="window.location='socialIdentity/create.do'" />
</security:authorize>