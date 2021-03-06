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

<!-- Listing grid -->
<display:table pagesize="5" class="displaytag" name="campaigns"
	requestURI="${requestURI}" id="row">

	<jstl:set value="${row.end.time - current.time}" var="diffms" />
	<jstl:set value="${row.start.time - current.time}" var="diffms2" />

	<jstl:set value="${diffms < 0}" var="isPast" /> />
	<jstl:set value="${diffms2 < 0 and diffms >= 0}" var="isPresent" /> />

	<jstl:choose>

		<jstl:when test="${(!isPast and !isPresent) or (row.approved eq null and isPresent)or (row.approved eq false and isPresent)}">

			<!-- Attributes -->
			<acme:column code="campaign.start" property="start" isDate="true" />

			<acme:column code="campaign.end" property="end" isDate="true" />

			<acme:column code="campaign.max" property="${row.max}" />

			<acme:column code="campaign.timesDisplayed"
				property="${row.timesDisplayed}" />

			<display:column style="background-color:;">
				<jstl:if test="${row.approved ne null}">
					<jstl:if test="${row.approved eq true}">
						<spring:message code="campaign.approved" />
					</jstl:if>
					<jstl:if test="${row.approved eq false}">
						<spring:message code="campaign.cancelled" />
					</jstl:if>
				</jstl:if>
			</display:column>

			<acme:column code="campaign.fee" property="${row.fee}" />

			<security:authorize access="hasRole('ADMIN')">
				<display:column style="background-color:;">
					<jstl:if test="${row.approved eq null}">
						<acme:link
							href="campaign/administrator/approve.do?campaignId=${row.id}"
							code="campaign.approve" />
						<acme:link
							href="campaign/administrator/cancel.do?campaignId=${row.id}"
							code="campaign.cancel" />
					</jstl:if>
				</display:column>
			</security:authorize>

		</jstl:when>

		<jstl:when test="${isPast}">

			<acme:column code="campaign.start" property="start" isDate="true"
				style="background-color:grey;" />

			<acme:column code="campaign.end" property="end" isDate="true"
				style="background-color:grey;" />

			<acme:column code="campaign.max" property="${row.max}"
				style="background-color:grey;" />

			<acme:column code="campaign.timesDisplayed"
				property="${row.timesDisplayed}" style="background-color:grey;" />

			<display:column style="background-color:grey;">
				<jstl:if test="${row.approved ne null}">
					<jstl:if test="${row.approved eq true}">
						<spring:message code="campaign.approved" />
					</jstl:if>
					<jstl:if test="${row.approved eq false}">
						<spring:message code="campaign.cancelled" />
					</jstl:if>
				</jstl:if>
			</display:column>

			<acme:column code="campaign.fee" property="${row.fee}"
				style="background-color:grey;" />
			
			<display:column style="background-color:grey;">
			</display:column>

		</jstl:when>
		<jstl:when test="${isPresent}">
			<acme:column code="campaign.start" property="start" isDate="true"
				style="background-color:yellow;" />

			<acme:column code="campaign.end" property="end" isDate="true"
				style="background-color:yellow;" />

			<acme:column code="campaign.max" property="${row.max}"
				style="background-color:yellow;" />

			<acme:column code="campaign.timesDisplayed"
				property="${row.timesDisplayed}" style="background-color:yellow;" />

			<display:column style="background-color:yellow;">
				<jstl:if test="${row.approved ne null}">
					<jstl:if test="${row.approved eq true}">
						<spring:message code="campaign.approved" />
					</jstl:if>
					<jstl:if test="${row.approved eq false}">
						<spring:message code="campaign.cancelled" />
					</jstl:if>
				</jstl:if>
			</display:column>

			<acme:column code="campaign.fee" property="${row.fee}"
				style="background-color:yellow;" />
				
				<security:authorize access="hasRole('ADMIN')">
				<display:column style="background-color:yellow;">
					<jstl:if test="${row.approved eq null}">
						<acme:link
							href="campaign/administrator/approve.do?campaignId=${row.id}"
							code="campaign.approve" />
						<acme:link
							href="campaign/administrator/cancel.do?campaignId=${row.id}"
							code="campaign.cancel" />
					</jstl:if>
				</display:column>
			</security:authorize>
		</jstl:when>
	</jstl:choose>

</display:table>

<security:authorize access="hasRole('PRODUCER')">
	<acme:link href="campaign/producer/create.do" code="misc.create" />
</security:authorize>

<br />