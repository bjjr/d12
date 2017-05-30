<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:display code="content.title" property="${ season.tvShow.title}" />
<br />
<acme:display code="season.name" property="${ season.name}" />
<br />
<display:table pagesize="5" class="displaytag" name="chapters"
	requestURI="${requestURI}" id="row">
	<acme:column code="cinematicEntity.name" property="${ row.name}" />
	<jstl:if test="${producerId eq season.tvShow.producer.id}">
		<display:column>
			<acme:link href="chapter/producer/edit.do?chapterId=${row.id}"
				code="misc.edit" />

		</display:column>
	</jstl:if>
</display:table>

<jstl:if test="${producerId eq season.tvShow.producer.id}">
	<acme:link href="chapter/producer/create.do?seasonId=${season.id}"
		code="chapter.create" />
</jstl:if>
