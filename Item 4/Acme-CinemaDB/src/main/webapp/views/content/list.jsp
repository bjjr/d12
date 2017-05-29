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
<form:form action="" modelAttribute="content">
	<input type="text" name="title" />
	<input type="submit" name="search"
		value="<spring:message code="misc.search"/>" />
</form:form>

<!-- Listing grid -->
<display:table pagesize="5" class="displaytag" name="contents"
	requestURI="${requestURI}" id="row">

	<!-- Attributes -->

	<acme:column code="content.title" property="${row.title}" />

		<spring:message code="content.genre" var="genreTitle" />
		
		<display:column title="${genreTitle}">
		<jstl:forEach items="${row.genres }" var="genre">
			<jstl:choose>
				<jstl:when test="${genre.kind eq 0 }">
					<spring:message code="genre.action" />
				</jstl:when>
				<jstl:when test="${genre.kind eq 1 }">
					<spring:message code="genre.adventure" />
				</jstl:when>
				<jstl:when test="${genre.kind eq 2 }">
					<spring:message code="genre.comedy" />
				</jstl:when>
				<jstl:when test="${genre.kind eq 3 }">
					<spring:message code="genre.drama" />
				</jstl:when>
				<jstl:when test="${genre.kind eq 4 }">
					<spring:message code="genre.horror" />
				</jstl:when>
				<jstl:when test="${genre.kind eq 5 }">
					<spring:message code="genre.animation" />
				</jstl:when>
				<jstl:when test="${genre.kind eq 6 }">
					<spring:message code="genre.sci-fi" />
				</jstl:when>
			</jstl:choose>
		</jstl:forEach>
	</display:column>

	<acme:column code="content.avgRating" property="${row.avgRating}" />
	
	<display:column>
		<acme:link href="review/list.do?contentId=${row.id}" code="review.show"/>
	</display:column>
	
	<display:column>
		<acme:link href="likeUser/listComments.do?assessableEntityId=${row.id}" code="likeUser.comments.list"/>
	</display:column>
	
	<security:authorize access="hasRole('USER')">
		<display:column>
			<acme:link href="likeUser/create.do?assessableEntityId=${row.id}" code="likeUser.comments.create"/>
		</display:column>
	</security:authorize>

	<display:column><a href="content/display.do?contentId=${row.id}"><spring:message code="misc.view" /></a></display:column>

</display:table>

