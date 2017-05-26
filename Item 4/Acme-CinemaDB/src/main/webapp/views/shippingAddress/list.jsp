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

	
<display:table pagesize="5" class="displaytag" name="shippingAddresses"
	requestURI="shippingAddress/list.do" id="row">

	<acme:column code="sa.saName" property="${row.saName}"/>

	<display:column>
		<acme:link href="shippingAddress/display.do?shippingAddressId=${row.id}" code="misc.view"/>
	</display:column>
	
	<display:column>
		<acme:link href="shippingAddress/delete.do?shippingAddressId=${row.id}" code="misc.delete" isDeletionLink="true"/>
	</display:column>
	
</display:table>
<acme:link href="shippingAddress/create.do" code="sa.create"/>
