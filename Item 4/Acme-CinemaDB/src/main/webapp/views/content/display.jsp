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

<acme:display code="content.title" property="${ content.title}" />
<br />
<acme:display code="content.year" property="${ content.year}" />
<br />
<acme:display code="content.genre" />
<jstl:forEach items="${content.genres }" var="genre">
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
<br />
<acme:display code="content.avgRating" property="${ content.avgRating}" />
<br />
<jstl:forEach items="${content.images }" var="image">
	<img src="${image }" style="height:" />
</jstl:forEach>
<br />
<acme:display code="content.videos" />
<jstl:forEach items="${content.videos }" var="video">
	<a href="${video }"><jstl:out value="${video }"></jstl:out></a>
</jstl:forEach>

<jstl:if test="${isMovie eq false }">
	<display:table pagesize="5" class="displaytag" name="seasons"
		requestURI="${requestURI}" id="row">

		<!-- Attributes -->

		<acme:column code="cinematicEntity.name" property="${row.name}" />
		
		<display:column><a href="season/display.do?seasonId=${row.id }">a</a></display:column>

	</display:table>
</jstl:if>


