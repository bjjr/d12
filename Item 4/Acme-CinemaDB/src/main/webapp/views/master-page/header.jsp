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
					<li><a href="critic/administrator/register.do"><spring:message code="master.page.critic.create" /></a></li>
					<li><a href="fee/administrator/list.do"><spring:message code="master.page.fee.edit" /></a></li>
					<li><a href="invoice/administrator/list.do"><spring:message code="master.page.invoice.list" /></a></li>
					<li><a href="campaign/administrator/list.do"><spring:message code="master.page.campaign.list" /></a></li>
					<li><a href="messageEntity/listSent.do"><spring:message code="master.page.messageEntity.sentMessages" /></a></li>
					<li><a href="messageEntity/listReceived.do"><spring:message code="master.page.messageEntity.receivedMessages" /></a></li>
				</ul>
			</li>
			<li><a href="producer/administrator/list.do"><spring:message code="master.page.producers" /></a></li>
		</security:authorize>
		
		<security:authorize access="hasRole('PRODUCER')">
			<li><a class="fNiv"><spring:message	code="master.page.producer" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="producer/producer/edit.do"><spring:message code="master.page.producer.edit" /></a></li>
					<li><a href="userAccount/edit.do"><spring:message code="master.page.changePassword" /></a></li>
					<li><a href="invoice/producer/list.do"><spring:message code="master.page.invoice.list" /></a></li>
					<li><a href="campaign/producer/list.do"><spring:message code="master.page.campaign.list" /></a></li>
					<li><a href="messageEntity/sendToAdmin.do"><spring:message code="master.page.messageEntity.sentToAdmin" /></a></li>
					<li><a href="messageEntity/listSent.do"><spring:message code="master.page.messageEntity.sentMessages" /></a></li>
					<li><a href="messageEntity/listReceived.do"><spring:message code="master.page.messageEntity.receivedMessages" /></a></li>
				</ul>
			</li>

		</security:authorize>
		
		<security:authorize access="hasRole('CRITIC')">
			<li><a class="fNiv"><spring:message	code="master.page.critic" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="userAccount/edit.do"><spring:message code="master.page.changePassword" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('USER')">
			<li><a class="fNiv"><spring:message	code="master.page.user" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="user/display.do"><spring:message code="master.page.user.profile" /></a></li>
					<li><a href="order/list.do"><spring:message code="master.page.user.orders" /></a></li>
					<li><a href="shippingAddress/list.do"><spring:message code="master.page.user.sas" /></a></li>
					<li><a href="userAccount/edit.do"><spring:message code="master.page.changePassword" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="user/register.do"><spring:message code="master.page.user.registration" /></a></li>
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
		</security:authorize>
		
		<security:authorize access="permitAll()">
			<li><a class="fNiv" href="content/list.do"><spring:message code="master.page.content" /></a></li>
			<li><a class="fNiv" href="cinematicEntity/list.do"><spring:message code="master.page.cinematicEntity" /></a></li>
		</security:authorize>
		
		<li><a class="fNiv" href="user/list.do"><spring:message code="master.page.user.list" /></a></li>
		
		<security:authorize access="isAuthenticated()">
			<li><a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>
					<li><acme:link href="j_spring_security_logout" code="master.page.logout" /></li>
				</ul>
			</li>
		</security:authorize>
		
	</ul>
</div>

<div>
	<a id="enlink" href="?language=en">en</a> | <a id="eslink" href="?language=es">es</a>
</div>

