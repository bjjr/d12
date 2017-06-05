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
	name="producers" requestURI="${requestURI}" id="row">
	
	<!-- Attributes -->
	<acme:column code="actor.name" property="${row.name}"/>
	
	<acme:column code="actor.surname" property="${row.surname}"/>
	
	<acme:column code="actor.email" property="${row.email}"/>
	
	<acme:column code="actor.phone" property="${row.phone}"/>
	
	<acme:column code="actor.country" property="${row.country}"/>
	
	<acme:column code="producer.company" property="${row.company}"/>
		
	<display:column>
		<a href="messageEntity/create.do?producerId=${row.id}"><spring:message code="messageEntity.sendMessage" /></a>
	</display:column>
	
</display:table>