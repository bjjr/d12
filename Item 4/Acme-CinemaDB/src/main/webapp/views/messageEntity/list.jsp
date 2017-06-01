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

<jstl:if test="${isAdmin == true}">
	<jstl:if test="${isSent}">
		<h2><spring:message code="messageEntity.listSent"/></h2>
	</jstl:if>

	<jstl:if test="${!isSent}">
		<h2><spring:message code="messageEntity.listReceived"/></h2>
	</jstl:if>
</jstl:if>

<jstl:if test="${isAdmin == false}">
	<jstl:if test="${isSent}">
		<h2><spring:message code="messageEntity.listSentToAdmin"/></h2>
	</jstl:if>

	<jstl:if test="${!isSent}">
		<h2><spring:message code="messageEntity.listReceivedFromAdmin"/></h2>
	</jstl:if>
</jstl:if>

<!-- Listing grid -->
<display:table pagesize="5" class="displaytag"
	name="messages" requestURI="${requestURI}" id="row">
	
	<!-- Attributes -->
	<acme:column code="messageEntity.title" property="${row.title}"/>
	
	<jstl:if test="${isAdmin == true}">
		<jstl:if test="${isSent == true}">
			<acme:column code="messageEntity.recipient" property="${row.recipient.userAccount.username}"/>
		</jstl:if>
	
		<jstl:if test="${isSent == false}">
			<acme:column code="messageEntity.sender" property="${row.sender.userAccount.username}"/>
		</jstl:if>
	</jstl:if>
	
	<display:column>
		<acme:link href="messageEntity/display.do?messageEntityId=${row.id}" code="misc.display"/>
	</display:column>
	
</display:table>