<%--
 * header.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<div>
	<img src="images/logo.png" alt="Acme-CinemaDB, Inc." />
</div>

<div>

	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		
		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv"><spring:message	code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="producer/administrator/create.do"><spring:message code="master.page.producer.create" /></a></li>
					<li><a href="fee/administrator/list.do"><spring:message code="master.page.fee.edit" /></a></li>
				</ul>
			</li>
			<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /></a></li>
		</security:authorize>
		
		<security:authorize access="hasRole('PRODUCER')">
			<li><a class="fNiv"><spring:message	code="master.page.producer" /></a>
				<ul>
					<li class="arrow"></li>
				</ul>
			</li>
			<li><a class="fNiv" href="producer/producer/edit.do"><spring:message code="master.page.producer.edit" /></a></li>
			<li><a class="fNiv" href="userAccount/edit.do"><spring:message code="master.page.changePassword" /></a></li>
			<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /></a></li>
		</security:authorize>
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
		</security:authorize>
		
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

