<%--
 * textarea.tag
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@ tag language="java" body-content="empty" %>

<%-- Taglibs --%>

<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>

<%-- Attributes --%> 

<%@ attribute name="href" required="true" %>
<%@ attribute name="code" required="true" %>
<%@ attribute name="isDeletionLink" required="false" %>

<jstl:if test="${isDeletionLink == null}">
	<jstl:set var="isDeletionLink" value="false" />
</jstl:if>

<%-- Definition --%>

<jstl:if test="${!isDeletionLink}">
	<a href="${href}"><spring:message code="${code}" /></a>
</jstl:if>

<jstl:if test="${isDeletionLink}">
	<a href="${href}" onclick="return confirm('<spring:message code="misc.confirm.delete" />')"><spring:message code="${code}" /></a>
</jstl:if>