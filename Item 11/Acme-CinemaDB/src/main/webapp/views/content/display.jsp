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
<acme:display code="content.year" property="${ content.year}" />
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

<h1><spring:message code="content.images" /></h1>

<div id="jssor_1" style="position:relative;margin:0 auto;top:0px;left:0px;width:600px;height:300px;overflow:hidden;visibility:hidden;">

	<!-- Loading Screen -->
	<div data-u="loading" style="position:absolute;top:0px;left:0px;background:url('../../images/loading.gif') no-repeat 50% 50%;background-color:rgba(0, 0, 0, 0.7);"></div>
	<div data-u="slides" style="cursor:default;position:relative;top:0px;left:0px;width:600px;height:300px;overflow:hidden;">
	
	<jstl:forEach items="${content.images }" var="imagesrc">
		<div>
    		<img data-u="image" src="${imagesrc}" />
		</div>
	</jstl:forEach>
	
	</div>
	
	<!-- Bullet Navigator -->
	
	<div data-u="navigator" class="jssorb05" style="bottom:16px;right:16px;" data-autocenter="1">
		<!-- bullet navigator item prototype -->
		<div data-u="prototype" style="width:16px;height:16px;"></div>
	</div>
	
	<!-- Arrow Navigator -->
	
	<span data-u="arrowleft" class="jssora12l" style="top:0px;left:0px;width:30px;height:46px;" data-autocenter="2"></span>
	<span data-u="arrowright" class="jssora12r" style="top:0px;right:0px;width:30px;height:46px;" data-autocenter="2"></span>
</div>

<br />

<h1><spring:message code="content.videos" /></h1>

<jstl:forEach items="${listYtId}" var="video">
	<iframe width="560" height="315" src="https://www.youtube.com/embed/${video}" allowfullscreen style="border: 0;"></iframe>
	<br />
</jstl:forEach>

<h1><spring:message code="cinematicEntity.list" /></h1>

<display:table pagesize="5" class="displaytag"
	name="content.cinematicEntities" requestURI="${requestURI}" id="row">
	
	<!-- Attributes -->
	
	<acme:column code="cinematicEntity.name" property="${row.name}"/>

	<acme:column code="cinematicEntity.surname" property="${row.surname}"/>

	<acme:column code="cinematicEntity.birthdate" property="birthdate" isDate="true"/>
	
	<display:column>
		<acme:link href="likeUser/listComments.do?assessableEntityId=${row.id}" code="likeUser.comments.list"/>
	</display:column>
	
	<display:column><a href="cinematicEntity/display.do?cinematicEntityId=${row.id }"><spring:message code="misc.view" /></a></display:column>
	
</display:table>
<br />

<jstl:if test="${isMovie eq false }">
	<h1><spring:message code="content.seasons" /></h1>
	
	<display:table pagesize="5" class="displaytag" name="seasons"
		requestURI="${requestURI}" id="row">

		<!-- Attributes -->

		<acme:column code="cinematicEntity.name" property="${row.name}" />
		
		<display:column><a href="season/display.do?seasonId=${row.id }"><spring:message code="misc.view" /></a></display:column>

		<jstl:if test="${producerId eq content.producer.id}">
			<display:column>
				<acme:link href="season/producer/edit.do?seasonId=${row.id}" code="misc.edit" />
			</display:column>
		</jstl:if>
	</display:table>
	<br />
	<jstl:if test="${producerId eq content.producer.id}">
		<acme:link href="season/producer/create.do?tvShowId=${content.id}" code="season.create" />
	</jstl:if>
</jstl:if>


<jstl:if test="${producerId eq content.producer.id}">
	<jstl:choose>
		<jstl:when test="${isMovie eq false}">
			<acme:link href="tvShow/producer/addCinematicEntities.do?tvShowId=${content.id}" code="content.add.cinematicEntity" />
		</jstl:when>
		<jstl:otherwise>
			<acme:link href="movie/producer/addCinematicEntities.do?movieId=${content.id}" code="content.add.cinematicEntity" />
		</jstl:otherwise>
	</jstl:choose>
</jstl:if>


