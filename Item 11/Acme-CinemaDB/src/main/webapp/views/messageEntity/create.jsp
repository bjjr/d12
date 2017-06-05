<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="messageEntity/send.do" modelAttribute="messageEntity" >
	
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	
	<acme:textbox code="messageEntity.title" path="title"/>
	<acme:textarea code="messageEntity.body" path="body"/>
	
	<acme:submit name="send" code="messageEntity.send"/>
	
	<jstl:if test="${isAdmin == true}">
		<acme:cancel url="/producer/administrator/list.do" code="misc.cancel"/>
	</jstl:if>
	<jstl:if test="${isAdmin == false}">
		<acme:cancel url="welcome/index.do" code="misc.cancel"/>
	</jstl:if>
	
</form:form>