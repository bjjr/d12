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

<acme:display code="sa.saName" property="${sa.saName}"/>
<acme:display code="actor.name" property="${sa.name}"/>
<acme:display code="actor.surname" property="${sa.surname}"/>
<acme:display code="actor.country" property="${sa.country}"/>
<acme:display code="actor.phone" property="${sa.phone}"/>
<acme:display code="sa.address" property="${sa.address}"/>
<acme:display code="sa.city" property="${sa.city}"/>
<acme:display code="sa.province" property="${sa.province}"/>
<acme:display code="sa.state" property="${sa.state}"/>
<acme:display code="sa.zipcode" property="${sa.zipcode}"/>

<acme:link href="shippingAddress/list.do" code="misc.return"/>
