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
<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=utf-8" errorPage="/error.jsp"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="tt" uri="http://www.unitime.org/tags-custom" %>

<div id="loading" class="unitime-PageLoading" style="visibility:hidden;display:none">
	<img align="middle" vspace="5" border="0" src="images/loading.gif">
</div>
<s:if test="#request.showNavigation && #request.menu != 'hide'">
	<tt:has-back>
		<span class="unitime-Navigation">
			<tt:back styleClass="btn" name="[&larr;]" title="Return to %%"/>
			<tt:back-tree/>
			<tt:gwt-back/>
		</span>
	</tt:has-back>
</s:if>