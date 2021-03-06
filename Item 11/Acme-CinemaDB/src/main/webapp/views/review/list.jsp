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

<display:table pagesize="5" class="displaytag" name="reviews"
	requestURI="${requestURI}" id="row">

	<acme:column code="review.title" property="${row.title}" sortable="true" />
	<acme:column code="review.body" property="${row.body}" sortable="true" />
	<acme:column code="review.rating" property="${row.rating}" sortable="true" />
	
	<jstl:if test="${isCriticReviews}">
		<acme:column code="review.isDraft" property="${row.draft}" />
		<display:column>
			<acme:link href="review/critic/edit.do?reviewId=${row.id}" code="misc.edit"/>
		</display:column>
	</jstl:if>
	
</display:table>

<br />

<security:authorize access="hasRole('CRITIC')">
	<jstl:if test="${!isCriticReviews}">
		<acme:link href="review/critic/create.do?contentId=${contentId}" code="review.create"/>
	</jstl:if>
</security:authorize>