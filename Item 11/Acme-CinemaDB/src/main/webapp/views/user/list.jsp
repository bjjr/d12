<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

	
<display:table pagesize="5" class="displaytag" name="users"
	requestURI="user/list.do" id="row">

	<acme:column code="actor.name" property="${row.name}"/>
	<acme:column code="actor.surname" property="${row.surname}"/>
	<acme:column code="actor.country" property="${row.country}"/>
	<display:column>
		<acme:link href="user/display.do?userId=${row.id}" code="misc.view"/>
	</display:column>

</display:table>
