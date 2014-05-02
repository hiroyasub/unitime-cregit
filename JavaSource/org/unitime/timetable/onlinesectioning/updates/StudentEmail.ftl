<!DOCTYPE html>
<!-- 
 * UniTime 3.5 (University Timetabling Application)
 * Copyright (C) 2014, UniTime LLC
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 -->
 <html>
 	<head>
 		<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>
 		<title><#if subject??>${subject}<#else>${msg.emailDeafultTitle()}</#if></title>
 	</head>
 	<body style="font-family: sans-serif, verdana, arial;">
 		<table style="border: 1px solid #9CB0CE; padding: 5px; margin-top: 10px; min-width: 800px;" align="center">
 			<tr><td><table width="100%">
 				<tr>
 					<td rowspan="2"><img src="http://www.unitime.org/include/unitime.png" border="0" height="80px"/></td>
 					<td colspan="2" style="font-size: x-large; font-weight: bold; color: #333333; text-align: right; padding: 20px 30px 10px 10px;"><#if subject??>${subject}<#else>${msg.emailDeafultTitle()}</#if></td>
 				</tr>
 				<tr>
 					<td style="color: #333333; text-align: right; vertical-align: top; padding: 10px 5px 5px 5px;"><#if name??>${name}<#else>${student.name}</#if></td>
 					<td style="color: #333333; text-align: right; vertical-align: top; padding: 10px 5px 5px 5px;">${server.academicSession}</td>
 				</tr>
 			</table></td></tr>
 			
 			<#if message??>
 				<tr><td style="width: 100%; border-bottom: 1px solid #9CB0CE; padding-top: 5px; font-size: large; font-weight: bold; color: black; text-align: left;">${msg.emailMessage()}</td></tr>
 				<tr><td>${message?replace("\n", "<br>")}</td></tr>
 			</#if>
 			
 			<#if changedCourse??>
 				<tr><td style="width: 100%; border-bottom: 1px solid #9CB0CE; padding-top: 5px; font-size: large; font-weight: bold; color: black; text-align: left;">${msg.emailCourseEnrollment(changedCourse.subjectArea,changedCourse.courseNumber)}</td></tr>
 				<#if changes??>
 					<tr><td><table width="100%">
 						<@classTableHeader/>
 						<#list changes as line>
 							<@classTableLine line/>
 						</#list>
 					</table></tr></tr>
 				<#elseif changeMessage??>
 					<tr><td style="color: red; text-align: center; font-style: italic; font-weight: normal;">${changeMessage}</td></tr>
 				</#if>
 			<#elseif changes??>
 				<tr><td style="width: 100%; border-bottom: 1px solid #9CB0CE; padding-top: 5px; font-size: large; font-weight: bold; color: black; text-align: left;">${msg.emailEnrollmentChanges()}</td></tr>
	 			<tr><td><table width="100%">
 					<@classTableHeader/>
 					<#list changes as line>
 						<@classTableLine line/>
 					</#list>
	 				<#if changes?size == 0>
 						<tr><td colspan="13"><i>${msg.emailNoChange()}</i></td></tr>
 					</#if>
 				</table></tr></tr>
 			</#if>
 			
 			<#if classes??>
 				<tr><td style="width: 100%; border-bottom: 1px solid #9CB0CE; padding-top: 5px; font-size: large; font-weight: bold; color: black; text-align: left;">${msg.emailClassList()}</td></tr>
 				<#if classes?size == 0>
 					<tr><td style="color: red; text-align: center; font-style: italic; font-weight: normal;">${msg.emailNoSchedule()}</td></tr>
 				<#else>
 					<tr><td><table width="100%">
 						<@classTableHeader/>
 						<#list classes as line>
 							<@classTableLine line/>
 						</#list>
 						<#if link??>
 							<tr><td colspan="13" style="font-size: 9pt; font-style: italic; color: #9CB0CE; text-align: right; margin-top: -2px; white-space: nowrap;">${msg.emailLinkToUniTime(link)}</td></tr>
 						</#if>
 					</table></tr></tr
 				</#if>
 			</#if>
 			
 			<#if timetable??>
 				<tr><td style="width: 100%; border-bottom: 1px solid #9CB0CE; padding-top: 5px; font-size: large; font-weight: bold; color: black; text-align: left;">${msg.emailTimetable()}</td></tr>
 				<tr><td>${timetable}</tr></tr>
 			</#if>
 			
 			<#if manager>
 				<#if changed>
 					<tr><td>${msg.emailChangesMadeBy(helper.user.name)}</td></tr>
 				<#else>
 					<tr><td>${msg.emailSentBy(helper.user.name)}</td></tr>
 				</#if>
 			</#if>
		</table>
		<table style="width: 800px; margin-top: -3px;" align="center">
			<tr>
				<td width="33%" align="left" style="font-size: 9pt; vertical-align: top; font-style: italic; color: #9CB0CE; white-space: nowrap;">${version}</td>
				<td width="34%" align="center" style="font-size: 9pt; vertical-align: top; font-style: italic; color: #9CB0CE; white-space: nowrap;">${copyright}</td>
				<td width="33%" align="right" style="font-size: 9pt; vertical-align: top; font-style: italic; color: #9CB0CE; white-space: nowrap;">${ts}</td>
			</tr>
		</table>
	</body>
</html>

<#macro classTableHeader>
 	<tr>
 		<#assign style="white-space: nowrap; font-weight: bold; padding-top: 5px;">
 		<td style="${style}">${msg.colSubject()}</td>
 		<td style="${style}">${msg.colCourse()}</td>
 		<td style="${style}">${msg.colSubpart()}</td>
 		<td style="${style}">${msg.colClass()}</td>
 		<td style="${style}">${msg.colDays()}</td>
 		<td style="${style}">${msg.colStart()}</td>
 		<td style="${style}">${msg.colEnd()}</td>
 		<td style="${style}">${msg.colDate()}</td>
 		<td style="${style}">${msg.colRoom()}</td>
 		<td style="${style}">${msg.colInstructor()}</td>
 		<td style="${style}">${msg.colParent()}</td>
 		<td style="${style}">${msg.colNote()}</td>
 		<td style="${style}">${msg.colCredit()}</td>
 	</tr>
</#macro>

<#macro classTableLine line>
	<#assign style="white-space: nowrap;">
	<#assign stylebr="">
	<#if line.first>
		<#assign style="white-space: nowrap; border-top: 1px dashed #9CB0CE;">
		<#assign stylebr="border-top: 1px dashed #9CB0CE;">
	</#if>
	<#if line.class.simpleName == "TableSectionDeletedLine">
		<#assign style = style + " text-decoration: line-through;">
		<#assign stylebr = stylebr + " text-decoration: line-through;">
	</#if>
	<#if line.freeTime>
		<tr>
			<td style="${style}">${msg.freeTimeSubject()}</td>
			<td style="${style}">{$msg.freeTimeCourse()}</td>
			<td style="${style}"></td>
			<td style="${style}"></td>
			<td style="${style}">${helper.getDays(line.time)}</td>
			<td style="${style}">${helper.getStartTime(line.time)}</td>
			<td style="${style}">${helper.getEndTime(line.time)}</td>
			<td style="${stylebr}" colspan="6"></td>
		</tr>
	<#elseif line.assigned>
		<tr>
			<td style="${style}">${line.subject}</td>
	 		<td style="${style}">${line.courseNumber}</td>
	 		<td style="${style}">${line.type}</td>
	 		<td style="${style}">${line.name}</td>
	 		<#if line.time??>
	 			<td style="${style}">${line.days}</td>
				<td style="${style}">${line.start}</td>
				<td style="${style}">${line.end}</td>
				<td style="${style}">${line.date}</td>
		 	<#else>
		 		<td style="${style}" colspan="4">${line.arrangeHours}</td>
	 		</#if>
	 		<td style="${style}">${line.rooms}</td>
	 		<td style="${style}">${line.instructors}</td>
	 		<#if line.requires??><td style="${stylebr}">${line.requires}</td><#else><td style="${style}"></td></#if>
	 		<#if line.note??><td style="${stylebr}">${line.note?replace("\n", "<br>")}</td><#else><td style="${style}"></td></#if>
	 		<#if line.credit??><td style="${style}">${line.credit}</td><#else><td style="${style}"></td></#if>
		</tr>
	<#else>
		<#assign style="white-space: nowrap; color: red; border-top: 1px dashed #9CB0CE;">
		<#assign stylebr="color: red; border-top: 1px dashed #9CB0CE;">
	 	<tr>
	 		<td style="${style}">${line.subject}</td>
	 		<td style="${style}">${line.courseNumber}</td>
	 		<td style="${style}"></td>
	 		<td style="${style}"></td>
	 		<td style="${stylebr}" colspan="9" align="center">${line.note}</td>
	 	</tr>	
	</#if>
</#macro>