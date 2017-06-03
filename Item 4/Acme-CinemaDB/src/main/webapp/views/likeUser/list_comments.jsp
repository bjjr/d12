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
	name="likeUser" requestURI="${requestURI}" id="row">
	
	<!-- Attributes -->
	
	<acme:column code="likeUser.comment" property="${row.comment}"/>

	<acme:column code="likeUser.user.name" property="${row.user.name}"/>
	
	<security:authorize access="hasRole('USER')">
		<display:column>
			<jstl:if test="${row.user.id == currentUserId}">
				<acme:link href="likeUser/user/edit.do?assessableEntityId=${row.id}" code="likeUser.comments.edit"/>
			</jstl:if>
		</display:column>
	</security:authorize>
	
</display:table>

