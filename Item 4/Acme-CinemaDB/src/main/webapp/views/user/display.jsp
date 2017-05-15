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

<!-- Tabs jQuery code -->
<script type="text/javascript">
	$document.ready((function() {
		$("#tabs").tabs();
	}));
</script>

<div id="tabs">

	<!-- Tabs links -->
	
	<ul>
		<li><acme:link href="#info" code="user.personal.info"/></li>
		<li><acme:link href="#contentLikes" code="user.likes.content"/></li>
		<li><acme:link href="#cinematicLikes" code="user.likes.cinEntities"/></li>
		<li><acme:link href="#sIdent" code="user.socialIdentities"/></li>
	</ul>
	
	<!-- Tabs contents -->
	
	<!-- User's info -->
	<div id="info">
		<acme:display code="actor.name" property="${user.name}"/>
		<acme:display code="actor.surname" property="${user.name}"/>
		<acme:display code="actor.country" property="${user.name}"/>
		<acme:display code="security.username" property="${user.userAccount.username}"/>
	</div>
	
	<!-- User's content likes -->
	
	<div id="contentLikes">
		<display:table pagesize="5" class="displaytag" name="contentLikes"
			requestURI="user/display.do?userId=${userId}" id="row">
		
			<acme:column code="content.title" property="${row.assessableEntity.title}"/>
			<acme:column code="content.year" property="${row.assessableEntity.year}"/>
			<acme:column code="content.rating" property="${row.assessableEntity.rating}"/>
			<display:column>
				<acme:link href="content/display.do?contentId=${row.id}" code="misc.view"/>
			</display:column>
		
		</display:table>
	</div>
	
	<!-- User's cinematic entities likes -->
	
	<div id="cinematicLikes">
		<display:table pagesize="5" class="displaytag" name="cEntitiesLikes"
			requestURI="user/display.do?userId=${userId}" id="row">
		
			<acme:column code="cinematicEntity.name" property="${row.assessableEntity.name}"/>
			<acme:column code="cinematicEntity.surname" property="${row.assessableEntity.surname}"/>
			<display:column>
				<acme:link href="cinematicEntity/display.do?cinematicEntityId=${row.id}" code="misc.view"/>
			</display:column>
		
		</display:table>
	</div>
	
	<!-- User's social identities -->
	
	<div id="sIdent">
		<display:table pagesize="5" class="displaytag" name="socialIds"
			requestURI="user/display.do?userId=${userId}" id="row">
		
			<acme:column code="socialIdentity.username" property="${row.name}"/>
			<display:column>
				<acme:link href="${row.path}" code="socialIdentity.path"/>
			</display:column>
		
		</display:table>
	</div>
	
</div>