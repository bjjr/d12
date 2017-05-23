<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:display code="messageEntity.title" property="${messageEntity.title}"/>
	
<acme:display code="messageEntity.moment" property="${messageEntity.moment}"/>

<jstl:if test="${isAdmin == true}">
	<jstl:if test="${isSent == true}">
		<acme:display code="messageEntity.recipient" property="${messageEntity.recipient.userAccount.username}"/>
	</jstl:if>
	
	<jstl:if test="${isSent == false}">
		<acme:display code="messageEntity.sender" property="${messageEntity.sender.userAccount.username}"/>
	</jstl:if>
</jstl:if>

<acme:display code="messageEntity.body" property="${messageEntity.body}"/>

<jstl:if test="${isSent == true}">
	<acme:cancel url="messageEntity/listSent.do" code="misc.return"/>
</jstl:if>

<jstl:if test="${isSent == false}">
	<acme:cancel url="messageEntity/listReceived.do" code="misc.return"/>
</jstl:if>