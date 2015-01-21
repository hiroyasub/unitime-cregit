<%--
 * Licensed to The Apereo Foundation under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for
 * additional information regarding copyright ownership.
 *
 * The Apereo Foundation licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
--%>
<%@ page language="java" autoFlush="true" errorPage="../error.jsp"%>
<%@ page import="org.unitime.timetable.form.InstructorEditForm" %>
<%@ page import="org.unitime.timetable.webutil.JavascriptFunctions" %>
<%@ page import="org.unitime.timetable.model.PositionType" %>
<%@ page import="org.unitime.timetable.util.Constants" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.unitime.org/tags-custom" prefix="tt" %>
<%@ taglib uri="http://www.unitime.org/tags-localization" prefix="loc" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<%
	// Get Form 
	String operation = "update";	
	String frmName = "instructorEditForm";	
	InstructorEditForm frm = (InstructorEditForm) request.getAttribute(frmName);	
%>	

<loc:bundle name="CourseMessages">
<tt:session-context/>
<SCRIPT language="javascript">
	<!--
		<%= JavascriptFunctions.getJsConfirm(sessionContext) %>
		function confirmDelete() {
			if (jsConfirm!=null && !jsConfirm)
				return true;

			return ( confirm("<%=MSG.confirmDeleteInstructor()%>"));
		}
		
	// -->
</SCRIPT>

<html:form action="instructorInfoEdit">
	<html:hidden property="instructorId"/>
	<html:hidden property="nextId"/>
	<html:hidden property="previousId"/>
	<html:hidden property="lookupEnabled"/>
	<bean:define name='<%=frmName%>' property="instructorId" id="instructorId"/>
	
	<TABLE width="100%" border="0" cellspacing="0" cellpadding="3">
		<jsp:include page="instructorLookup.jspf">
			<jsp:param name="frmName" value="instructorEditForm"/>
		</jsp:include>
		<logic:notEqual value="true" property="matchFound" name="instructorEditForm">
			<jsp:include page="instructor.jspf">
				<jsp:param name="operation" value="update"/>
				<jsp:param name="frmName" value="instructorEditForm"/>
				<jsp:param name="instructorId" value="<%=instructorId%>"/>
			</jsp:include>
		</logic:notEqual>
	</TABLE>
</html:form>

</loc:bundle>