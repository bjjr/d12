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

<form:form action="shippingAddress/edit.do" modelAttribute="shippingAddress" >

	<form:hidden path="id"/>
	<form:hidden path="version"/>
		
	<acme:textbox code="sa.saName" path="saName"/>
	<br/>
	<acme:textbox code="actor.name" path="name"/>
	<br/>
	<acme:textbox code="actor.surname" path="surname"/>
	<br/>
	<acme:textbox code="actor.country" path="country"/>
	<br/>
	<acme:textbox code="actor.phone" path="phone"/>
	<br/>
	<acme:textbox code="sa.address" path="address"/>
	<br/>
	<acme:textbox code="sa.city" path="city"/>
	<br/>
	<acme:textbox code="sa.province" path="province"/>
	<br/>
	<acme:textbox code="sa.state" path="state"/>
	<br/>
	<acme:textbox code="sa.zipcode" path="zipcode"/>
	<br/>

	<acme:submit name="save" code="misc.save"/>		
	<acme:cancel url="../list.do" code="misc.cancel"/>
		
</form:form>
