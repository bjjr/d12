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

<h2>
	<spring:message code="administrator.minMoviesProducer" />
</h2>
<jstl:out value="${minMoviesProducer}"></jstl:out>

<h2>
	<spring:message code="administrator.avgMoviesProducer" />
</h2>
<jstl:out value="${avgMoviesProducer}"></jstl:out>

<h2>
	<spring:message code="administrator.maxMoviesProducer" />
</h2>
<jstl:out value="${maxMoviesProducer}"></jstl:out>

<h2>
	<spring:message code="administrator.numberOfMoviesPerType" />
</h2>
<jstl:forEach var="entry" items="${numberOfMoviesPerType}">
	<spring:message code="administrator.numberOfMoviesPerType.genre" />
	<jstl:out value="${entry[1]}" />
	<spring:message
		code="administrator.numberOfMoviesPerType.quantity" />
	<jstl:out value="${entry[0]}" />
	<br />
</jstl:forEach>

<h2>
	<spring:message code="administrator.numberOfTvShowsPerType" />
</h2>
<jstl:forEach var="entry" items="${numberOfTvShowsPerType}">
	<spring:message code="administrator.numberOfTvShowsPerType" />
	<jstl:out value="${entry[1]}" />
	<spring:message
		code="administrator.numberOfTvShowsPerType.quantity" />
	<jstl:out value="${entry[0]}" />
	<br />
</jstl:forEach>

<h2>
	<spring:message code="administrator.minTotalMoneyInvoices" />
</h2>
<jstl:out value="${minTotalMoneyInvoices}"></jstl:out>

<h2>
	<spring:message code="administrator.avgTotalMoneyInvoices" />
</h2>
<jstl:out value="${avgTotalMoneyInvoices}"></jstl:out>

<h2>
	<spring:message code="administrator.maxTotalMoneyInvoices" />
</h2>
<jstl:out value="${maxTotalMoneyInvoices}"></jstl:out>

<h2>
	<spring:message code="administrator.avgTotalMoneyOrdersFinished" />
</h2>
<jstl:out value="${avgTotalMoneyOrdersFinished}"></jstl:out>

<h2>
	<spring:message code="administrator.avgShippingAddressPerUser" />
</h2>
<jstl:out value="${avgShippingAddressPerUser}"></jstl:out>

<h2>
	<spring:message code="administrator.minOrderUserPerUser" />
</h2>
<jstl:out value="${minOrderUserPerUser}"></jstl:out>

<h2>
	<spring:message code="administrator.avgOrderUserPerUser" />
</h2>
<jstl:out value="${avgOrderUserPerUser}"></jstl:out>

<h2>
	<spring:message code="administrator.maxOrderUserPerUser" />
</h2>
<jstl:out value="${maxOrderUserPerUser}"></jstl:out>

<h2>
	<spring:message code="administrator.percentageOrdersInProgress" />
</h2>
<jstl:out value="${percentageOrdersInProgress}"></jstl:out>

<h2>
	<spring:message code="administrator.percentageOrdersSent" />
</h2>
<jstl:out value="${percentageOrdersSent}"></jstl:out>

<h2>
	<spring:message code="administrator.percentageOrdersCancelled" />
</h2>
<jstl:out value="${percentageOrdersCancelled}"></jstl:out>

<h2>
	<spring:message code="administrator.minReviewCritic" />
</h2>
<jstl:out value="${minReviewCritic}"></jstl:out>

<h2>
	<spring:message code="administrator.avgReviewCritic" />
</h2>
<jstl:out value="${avgReviewCritic}"></jstl:out>

<h2>
	<spring:message code="administrator.maxReviewCritic" />
</h2>
<jstl:out value="${maxReviewCritic}"></jstl:out>

<h2>
	<spring:message code="administrator.avgRatingMovies" />
</h2>
<jstl:out value="${avgRatingMovies}"></jstl:out>

<h2>
	<spring:message code="administrator.avgRatingTvShows" />
</h2>
<jstl:out value="${avgRatingTvShows}"></jstl:out>

<h2>
	<spring:message code="administrator.avgChaptersPerSeason" />
</h2>
<jstl:out value="${avgChaptersPerSeason}"></jstl:out>

<h2>
	<spring:message code="administrator.maxChaptersPerSeason" />
</h2>
<jstl:out value="${maxChaptersPerSeason}"></jstl:out>

<h2>
	<spring:message code="administrator.avgSeasonsPerTvShow" />
</h2>
<jstl:out value="${avgSeasonsPerTvShow}"></jstl:out>

<h2>
	<spring:message code="administrator.maxSeasonsPerTvShow" />
</h2>
<jstl:out value="${maxSeasonsPerTvShow}"></jstl:out>