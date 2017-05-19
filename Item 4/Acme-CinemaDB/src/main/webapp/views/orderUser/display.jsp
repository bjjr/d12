<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

	
<!-- User's info -->

<h1><spring:message code="user.personal.info" /></h1>

<div id="info">
	<acme:display code="actor.name" property="${user.name}"/>
	<acme:display code="actor.surname" property="${user.surname}"/>
	<acme:display code="actor.country" property="${user.country}"/>
	<acme:display code="security.username" property="${user.userAccount.username}"/>
</div>

<!-- User's content likes -->

<h1><spring:message code="user.likes.content" /></h1>

<div id="contentLikes">
	<display:table pagesize="5" class="displaytag" name="contentLikes"
		requestURI="user/display.do?userId=${user.id}" id="row">
	
		<acme:column code="content.title" property="${row.assessableEntity.title}"/>
		<acme:column code="content.year" property="${row.assessableEntity.year}"/>
		<display:column title="content.genre">
			<jstl:forEach items="${row.assessableEntity.genres}" var="genre">
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
		<acme:column code="content.rating" property="${row.assessableEntity.avgRating}"/>
		<display:column>
			<acme:link href="content/display.do?contentId=${row.assessableEntity.id}" code="misc.view"/>
		</display:column>
	
	</display:table>
</div>

<!-- User's cinematic entities likes -->

<h1><spring:message code="user.likes.cinEntities" /></h1>

<div id="cinematicLikes">
	<display:table pagesize="5" class="displaytag" name="cEntitiesLikes"
		requestURI="user/display.do?userId=${user.id}" id="cEntity">
	
		<acme:column code="cinematicEntity.name" property="${cEntity.assessableEntity.name}"/>
		<acme:column code="cinematicEntity.surname" property="${cEntity.assessableEntity.surname}"/>
		<display:column>
			<acme:link href="cinematicEntity/display.do?cinematicEntityId=${cEntity.assessableEntity.id}" code="misc.view"/>
		</display:column>
	
	</display:table>
</div>

<!-- User's social identities -->

<h1><spring:message code="user.socialIdentities" /></h1>

<div id="sIdent">
	<display:table pagesize="5" class="displaytag" name="socialIds"
		requestURI="user/display.do?userId=${user.id}" id="socialId">
	
		<acme:column code="socialIdentity.username" property="${socialId.username}"/>
		<display:column>
			<a href="${socialId.path}"><jstl:out value="${socialId.path}" /></a>
		</display:column>
	
	</display:table>
</div>