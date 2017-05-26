<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

	<!-- Search Form -->
	<form:form action="" modelAttribute="cinematicEntity">
		<input type="text" name="name" />
		<input type="submit" name="search"
			value="<spring:message code="misc.search"/>" />
	</form:form>

<!-- Listing grid -->
<display:table pagesize="5" class="displaytag" name="cinematicEntities"
	requestURI="${requestURI}" id="row">

	<!-- Attributes -->

	<acme:column code="cinematicEntity.name" property="${row.name}" />

	<acme:column code="cinematicEntity.surname" property="${row.surname}" />

	<acme:column code="cinematicEntity.birthdate"
		property="${row.birthdate}" />

	<display:column>
		<acme:link
			href="likeUser/listComments.do?assessableEntityId=${row.id}"
			code="likeUser.comments.list" />
	</display:column>

	<security:authorize access="hasRole('USER')">
		<display:column>
			<acme:link href="likeUser/create.do?assessableEntityId=${row.id}"
				code="likeUser.comments.create" />
		</display:column>
	</security:authorize>

		<security:authorize access="hasRole('PRODUCER')">
			<display:column>
				<acme:link
					href="cinematicEntity/producer/edit.do?cinematicEntityId=${row.id}"
					code="misc.edit" />
			</display:column>
		</security:authorize>

	<display:column>
		<a href="cinematicEntity/display.do?cinematicEntityId=${row.id }"><spring:message
				code="misc.view" /></a>
	</display:column>

</display:table>

<acme:link href="cinematicEntity/producer/create.do" code="cinematicEntity.create"/>