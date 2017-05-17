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
	name="invoices" requestURI="${requestURI}" id="row">
	
	<!-- Attributes -->
	<acme:column code="invoice.total" property="${row.total}"/>
	
	<acme:column code="invoice.billingDate" property="billingDate" isDate="true" />
	
	<display:column>
		<jstl:if test="${row.paid eq true}">
			<spring:message code="invoice.paid"/>
		</jstl:if>
		<jstl:if test="${row.paid eq false}">
			<acme:link href="invoice/administrator/setPaid.do?invoiceId=${row.id}" code="invoice.set.paid"/>
		</jstl:if>
	</display:column>
	
</display:table>

<acme:link href="invoice/administrator/create.do" code="misc.create"/>


<br />