<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<!-- Listing grid -->
<display:table pagesize="5" class="displaytag"
	name="likeUser" requestURI="/likeUser/user/listLikes.do" id="row">
	
	<!-- Attributes -->
	
	<acme:column code="likeUser.assessableEntity.id" property="${row.id}"/>
	
	<acme:column code="likeUser.comment" property="${row.comment}"/>

	
	<display:column>
		<acme:link href="likeUser/user/create.do?assessableEntityId=${row.assessableEntity.id}" code="likeUser.comments.edit"/>
	</display:column>
	
	

	<display:column>
		<acme:link href="likeUser/user/unlike.do?assessableEntityId=${row.assessableEntity.id}" code="likeUser.unlike"/>	
	</display:column>


	
	
	
</display:table>

