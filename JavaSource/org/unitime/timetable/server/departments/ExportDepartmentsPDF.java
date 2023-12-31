begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to The Apereo Foundation under one or more contributor license  * agreements. See the NOTICE file distributed with this work for  * additional information regarding copyright ownership.  *  * The Apereo Foundation licenses this file to you under the Apache License,  * Version 2.0 (the "License"); you may not use this file except in  * compliance with the License. You may obtain a copy of the License at:  *  * http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *  * See the License for the specific language governing permissions and  * limitations under the License.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|server
operator|.
name|departments
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|text
operator|.
name|DecimalFormat
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collections
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Comparator
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|TreeSet
import|;
end_import

begin_import
import|import
name|org
operator|.
name|springframework
operator|.
name|stereotype
operator|.
name|Service
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|localization
operator|.
name|impl
operator|.
name|Localization
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|defaults
operator|.
name|ApplicationProperty
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|defaults
operator|.
name|CommonValues
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|defaults
operator|.
name|UserProperty
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|export
operator|.
name|ExportHelper
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|export
operator|.
name|Exporter
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|export
operator|.
name|PDFPrinter
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|gwt
operator|.
name|resources
operator|.
name|GwtMessages
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|gwt
operator|.
name|shared
operator|.
name|DepartmentInterface
operator|.
name|DepartmentsColumn
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|ChangeLog
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Department
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|ExternalDepartmentStatusType
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|security
operator|.
name|rights
operator|.
name|Right
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|security
operator|.
name|SessionContext
import|;
end_import

begin_class
annotation|@
name|Service
argument_list|(
literal|"org.unitime.timetable.export.Exporter:departments.pdf"
argument_list|)
specifier|public
class|class
name|ExportDepartmentsPDF
implements|implements
name|Exporter
block|{
specifier|protected
specifier|static
name|GwtMessages
name|MESSAGES
init|=
name|Localization
operator|.
name|create
argument_list|(
name|GwtMessages
operator|.
name|class
argument_list|)
decl_stmt|;
annotation|@
name|Override
specifier|public
name|String
name|reference
parameter_list|()
block|{
return|return
literal|"departments.pdf"
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|export
parameter_list|(
name|ExportHelper
name|helper
parameter_list|)
throws|throws
name|IOException
block|{
name|SessionContext
name|context
init|=
name|helper
operator|.
name|getSessionContext
argument_list|()
decl_stmt|;
name|boolean
name|dispLastChanges
init|=
name|CommonValues
operator|.
name|Yes
operator|.
name|eq
argument_list|(
name|UserProperty
operator|.
name|DisplayLastChanges
operator|.
name|get
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
name|helper
operator|.
name|getSessionContext
argument_list|()
operator|.
name|checkPermission
argument_list|(
name|Right
operator|.
name|Departments
argument_list|)
expr_stmt|;
name|PDFPrinter
name|out
init|=
operator|new
name|PDFPrinter
argument_list|(
name|helper
operator|.
name|getOutputStream
argument_list|()
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|helper
operator|.
name|setup
argument_list|(
name|out
operator|.
name|getContentType
argument_list|()
argument_list|,
name|reference
argument_list|()
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|DecimalFormat
name|df5
init|=
operator|new
name|DecimalFormat
argument_list|(
literal|"####0.######"
argument_list|)
decl_stmt|;
name|out
operator|.
name|printHeader
argument_list|(
name|MESSAGES
operator|.
name|propDepartmentlist
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|getQualifiers
argument_list|(
literal|"Session"
argument_list|)
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|getQualifierLabel
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|Department
argument_list|>
name|departments
init|=
operator|new
name|ArrayList
argument_list|<
name|Department
argument_list|>
argument_list|(
name|Department
operator|.
name|findAll
argument_list|(
name|helper
operator|.
name|getAcademicSessionId
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
name|String
name|sort
init|=
name|helper
operator|.
name|getParameter
argument_list|(
literal|"sort"
argument_list|)
decl_stmt|;
name|String
name|showAllDept
init|=
name|helper
operator|.
name|getParameter
argument_list|(
literal|"showAllDept"
argument_list|)
decl_stmt|;
if|if
condition|(
name|sort
operator|!=
literal|null
operator|&&
operator|!
literal|"0"
operator|.
name|equals
argument_list|(
name|sort
argument_list|)
condition|)
block|{
name|int
name|sortBy
init|=
name|Integer
operator|.
name|valueOf
argument_list|(
name|sort
argument_list|)
decl_stmt|;
name|DepartmentComparator
name|cmp
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|sortBy
operator|==
literal|0
condition|)
block|{
comment|// no sort
block|}
if|else if
condition|(
name|sortBy
operator|>
literal|0
condition|)
block|{
name|cmp
operator|=
operator|new
name|DepartmentComparator
argument_list|(
name|DepartmentsColumn
operator|.
name|values
argument_list|()
index|[
name|sortBy
operator|-
literal|1
index|]
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|cmp
operator|=
operator|new
name|DepartmentComparator
argument_list|(
name|DepartmentsColumn
operator|.
name|values
argument_list|()
index|[
operator|-
literal|1
operator|-
name|sortBy
index|]
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|cmp
operator|!=
literal|null
condition|)
name|Collections
operator|.
name|sort
argument_list|(
name|departments
argument_list|,
name|cmp
argument_list|)
expr_stmt|;
block|}
comment|/* 		 * write to pdf 		 */
name|Boolean
name|printHeaderText
init|=
literal|false
decl_stmt|;
for|for
control|(
name|Department
name|d
range|:
name|departments
control|)
block|{
comment|/*             	 * Header             	 */
if|if
condition|(
operator|!
name|printHeaderText
condition|)
block|{
name|ArrayList
argument_list|<
name|String
argument_list|>
name|headerText
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|headerText
operator|.
name|add
argument_list|(
name|MESSAGES
operator|.
name|colNumber
argument_list|()
argument_list|)
expr_stmt|;
name|headerText
operator|.
name|add
argument_list|(
name|MESSAGES
operator|.
name|colAbbv
argument_list|()
argument_list|)
expr_stmt|;
name|headerText
operator|.
name|add
argument_list|(
name|MESSAGES
operator|.
name|colName
argument_list|()
argument_list|)
expr_stmt|;
name|headerText
operator|.
name|add
argument_list|(
name|MESSAGES
operator|.
name|colExternalManager
argument_list|()
operator|.
name|replace
argument_list|(
literal|"<br>"
argument_list|,
literal|"\n"
argument_list|)
argument_list|)
expr_stmt|;
name|headerText
operator|.
name|add
argument_list|(
name|MESSAGES
operator|.
name|colSubjects
argument_list|()
argument_list|)
expr_stmt|;
name|headerText
operator|.
name|add
argument_list|(
name|MESSAGES
operator|.
name|colRooms
argument_list|()
argument_list|)
expr_stmt|;
name|headerText
operator|.
name|add
argument_list|(
name|MESSAGES
operator|.
name|colStatus
argument_list|()
argument_list|)
expr_stmt|;
name|headerText
operator|.
name|add
argument_list|(
name|MESSAGES
operator|.
name|colDistPrefPriority
argument_list|()
operator|.
name|replace
argument_list|(
literal|"<br>"
argument_list|,
literal|"\n"
argument_list|)
argument_list|)
expr_stmt|;
name|headerText
operator|.
name|add
argument_list|(
name|MESSAGES
operator|.
name|colAllowRequired
argument_list|()
operator|.
name|replace
argument_list|(
literal|"<br>"
argument_list|,
literal|"\n"
argument_list|)
argument_list|)
expr_stmt|;
name|headerText
operator|.
name|add
argument_list|(
name|MESSAGES
operator|.
name|colInstructorPref
argument_list|()
operator|.
name|replace
argument_list|(
literal|"<br>"
argument_list|,
literal|"\n"
argument_list|)
argument_list|)
expr_stmt|;
name|headerText
operator|.
name|add
argument_list|(
name|MESSAGES
operator|.
name|colEvents
argument_list|()
argument_list|)
expr_stmt|;
name|headerText
operator|.
name|add
argument_list|(
name|MESSAGES
operator|.
name|colStudentScheduling
argument_list|()
operator|.
name|replace
argument_list|(
literal|"<br>"
argument_list|,
literal|"\n"
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|ApplicationProperty
operator|.
name|CoursesFundingDepartmentsEnabled
operator|.
name|isTrue
argument_list|()
condition|)
name|headerText
operator|.
name|add
argument_list|(
name|MESSAGES
operator|.
name|colExternalFundingDept
argument_list|()
operator|.
name|replace
argument_list|(
literal|"<br>"
argument_list|,
literal|"\n"
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|dispLastChanges
condition|)
name|headerText
operator|.
name|add
argument_list|(
name|MESSAGES
operator|.
name|colLastChange
argument_list|()
operator|.
name|replace
argument_list|(
literal|"<br>"
argument_list|,
literal|"\n"
argument_list|)
argument_list|)
expr_stmt|;
name|String
index|[]
name|headerTextStr
init|=
operator|new
name|String
index|[
name|headerText
operator|.
name|size
argument_list|()
index|]
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|headerText
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|headerTextStr
index|[
name|i
index|]
operator|=
name|headerText
operator|.
name|get
argument_list|(
name|i
argument_list|)
expr_stmt|;
block|}
name|out
operator|.
name|printHeader
argument_list|(
name|headerTextStr
argument_list|)
expr_stmt|;
name|printHeaderText
operator|=
literal|true
expr_stmt|;
block|}
if|if
condition|(
name|showAllDept
operator|.
name|trim
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"true"
argument_list|)
operator|||
operator|!
name|d
operator|.
name|getSubjectAreas
argument_list|()
operator|.
name|isEmpty
argument_list|()
operator|||
operator|!
name|d
operator|.
name|getTimetableManagers
argument_list|()
operator|.
name|isEmpty
argument_list|()
operator|||
name|d
operator|.
name|isExternalManager
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|)
block|{
name|String
name|lastChangeStr
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|dispLastChanges
condition|)
block|{
name|List
argument_list|<
name|ChangeLog
argument_list|>
name|changes
init|=
name|ChangeLog
operator|.
name|findLastNChanges
argument_list|(
name|d
operator|.
name|getSession
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|,
name|d
operator|.
name|getUniqueId
argument_list|()
argument_list|,
literal|1
argument_list|)
decl_stmt|;
name|ChangeLog
name|lastChange
init|=
operator|(
name|changes
operator|==
literal|null
operator|||
name|changes
operator|.
name|isEmpty
argument_list|()
condition|?
literal|null
else|:
operator|(
name|ChangeLog
operator|)
name|changes
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|)
decl_stmt|;
name|lastChangeStr
operator|=
operator|(
name|lastChange
operator|==
literal|null
condition|?
literal|""
else|:
name|MESSAGES
operator|.
name|lastChange
argument_list|(
name|ChangeLog
operator|.
name|sDFdate
operator|.
name|format
argument_list|(
name|lastChange
operator|.
name|getTimeStamp
argument_list|()
argument_list|)
argument_list|,
name|lastChange
operator|.
name|getManager
argument_list|()
operator|.
name|getShortName
argument_list|()
argument_list|)
operator|)
expr_stmt|;
block|}
name|String
name|allowReq
init|=
literal|""
decl_stmt|;
name|int
name|allowReqOrd
init|=
literal|0
decl_stmt|;
if|if
condition|(
name|d
operator|.
name|isAllowReqRoom
argument_list|()
operator|!=
literal|null
operator|&&
name|d
operator|.
name|isAllowReqRoom
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|)
block|{
if|if
condition|(
operator|!
name|allowReq
operator|.
name|isEmpty
argument_list|()
condition|)
name|allowReq
operator|+=
literal|", "
expr_stmt|;
name|allowReq
operator|+=
name|MESSAGES
operator|.
name|colRooms
argument_list|()
expr_stmt|;
name|allowReqOrd
operator|+=
literal|1
expr_stmt|;
block|}
if|if
condition|(
name|d
operator|.
name|isAllowReqTime
argument_list|()
operator|!=
literal|null
operator|&&
name|d
operator|.
name|isAllowReqTime
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|)
block|{
if|if
condition|(
operator|!
name|allowReq
operator|.
name|isEmpty
argument_list|()
condition|)
name|allowReq
operator|+=
literal|", "
expr_stmt|;
name|allowReq
operator|+=
name|MESSAGES
operator|.
name|colTime
argument_list|()
expr_stmt|;
name|allowReqOrd
operator|+=
literal|2
expr_stmt|;
block|}
if|if
condition|(
name|d
operator|.
name|isAllowReqDistribution
argument_list|()
operator|!=
literal|null
operator|&&
name|d
operator|.
name|isAllowReqDistribution
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|)
block|{
if|if
condition|(
operator|!
name|allowReq
operator|.
name|isEmpty
argument_list|()
condition|)
name|allowReq
operator|+=
literal|", "
expr_stmt|;
name|allowReq
operator|+=
name|MESSAGES
operator|.
name|colDistribution
argument_list|()
expr_stmt|;
name|allowReqOrd
operator|+=
literal|4
expr_stmt|;
block|}
if|if
condition|(
name|allowReqOrd
operator|==
literal|7
condition|)
name|allowReq
operator|=
name|MESSAGES
operator|.
name|colAll
argument_list|()
expr_stmt|;
name|String
name|dependentStatuses
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|d
operator|.
name|isExternalManager
argument_list|()
operator|&&
name|d
operator|.
name|getExternalStatusTypes
argument_list|()
operator|!=
literal|null
operator|&&
operator|!
name|d
operator|.
name|getExternalStatusTypes
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|TreeSet
argument_list|<
name|ExternalDepartmentStatusType
argument_list|>
name|set
init|=
operator|new
name|TreeSet
argument_list|<
name|ExternalDepartmentStatusType
argument_list|>
argument_list|(
operator|new
name|Comparator
argument_list|<
name|ExternalDepartmentStatusType
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|int
name|compare
parameter_list|(
name|ExternalDepartmentStatusType
name|e1
parameter_list|,
name|ExternalDepartmentStatusType
name|e2
parameter_list|)
block|{
return|return
name|e1
operator|.
name|getDepartment
argument_list|()
operator|.
name|compareTo
argument_list|(
name|e2
operator|.
name|getDepartment
argument_list|()
argument_list|)
return|;
block|}
block|}
argument_list|)
decl_stmt|;
name|set
operator|.
name|addAll
argument_list|(
name|d
operator|.
name|getExternalStatusTypes
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|ExternalDepartmentStatusType
name|t
range|:
name|set
control|)
block|{
if|if
condition|(
name|dependentStatuses
operator|==
literal|null
condition|)
name|dependentStatuses
operator|=
literal|"    "
operator|+
name|t
operator|.
name|getDepartment
argument_list|()
operator|.
name|getDeptCode
argument_list|()
operator|+
literal|": "
operator|+
name|t
operator|.
name|getStatusType
argument_list|()
operator|.
name|getLabel
argument_list|()
expr_stmt|;
else|else
name|dependentStatuses
operator|+=
literal|"\n    "
operator|+
name|t
operator|.
name|getDepartment
argument_list|()
operator|.
name|getDeptCode
argument_list|()
operator|+
literal|": "
operator|+
name|t
operator|.
name|getStatusType
argument_list|()
operator|.
name|getLabel
argument_list|()
expr_stmt|;
block|}
block|}
comment|/*                      * Body                      */
name|ArrayList
argument_list|<
name|String
argument_list|>
name|bodyText
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|bodyText
operator|.
name|add
argument_list|(
name|d
operator|.
name|getDeptCode
argument_list|()
argument_list|)
expr_stmt|;
name|bodyText
operator|.
name|add
argument_list|(
name|d
operator|.
name|getAbbreviation
argument_list|()
argument_list|)
expr_stmt|;
name|bodyText
operator|.
name|add
argument_list|(
name|d
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|bodyText
operator|.
name|add
argument_list|(
name|d
operator|.
name|isExternalManager
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|?
name|d
operator|.
name|getExternalMgrAbbv
argument_list|()
else|:
literal|""
argument_list|)
expr_stmt|;
name|bodyText
operator|.
name|add
argument_list|(
name|df5
operator|.
name|format
argument_list|(
name|d
operator|.
name|getSubjectAreas
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|bodyText
operator|.
name|add
argument_list|(
name|df5
operator|.
name|format
argument_list|(
name|d
operator|.
name|getRoomDepts
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|bodyText
operator|.
name|add
argument_list|(
name|d
operator|.
name|effectiveStatusType
argument_list|()
operator|.
name|getLabel
argument_list|()
operator|+
operator|(
name|dependentStatuses
operator|==
literal|null
condition|?
literal|""
else|:
literal|"\n"
operator|+
name|dependentStatuses
operator|)
argument_list|)
expr_stmt|;
name|bodyText
operator|.
name|add
argument_list|(
operator|(
name|d
operator|.
name|getDistributionPrefPriority
argument_list|()
operator|==
literal|null
operator|&&
name|d
operator|.
name|getDistributionPrefPriority
argument_list|()
operator|.
name|intValue
argument_list|()
operator|!=
literal|0
condition|?
literal|""
else|:
name|d
operator|.
name|getDistributionPrefPriority
argument_list|()
operator|.
name|toString
argument_list|()
operator|)
argument_list|)
expr_stmt|;
name|bodyText
operator|.
name|add
argument_list|(
name|allowReq
argument_list|)
expr_stmt|;
name|bodyText
operator|.
name|add
argument_list|(
name|d
operator|.
name|isInheritInstructorPreferences
argument_list|()
condition|?
name|MESSAGES
operator|.
name|exportTrue
argument_list|()
else|:
name|MESSAGES
operator|.
name|exportFalse
argument_list|()
argument_list|)
expr_stmt|;
name|bodyText
operator|.
name|add
argument_list|(
name|d
operator|.
name|isAllowEvents
argument_list|()
condition|?
name|MESSAGES
operator|.
name|exportTrue
argument_list|()
else|:
name|MESSAGES
operator|.
name|exportFalse
argument_list|()
argument_list|)
expr_stmt|;
name|bodyText
operator|.
name|add
argument_list|(
name|d
operator|.
name|isAllowStudentScheduling
argument_list|()
condition|?
name|MESSAGES
operator|.
name|exportTrue
argument_list|()
else|:
name|MESSAGES
operator|.
name|exportFalse
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|ApplicationProperty
operator|.
name|CoursesFundingDepartmentsEnabled
operator|.
name|isTrue
argument_list|()
condition|)
name|bodyText
operator|.
name|add
argument_list|(
name|d
operator|.
name|isExternalFundingDept
argument_list|()
operator|!=
literal|null
condition|?
operator|(
name|d
operator|.
name|isExternalFundingDept
argument_list|()
operator|==
literal|true
condition|?
name|MESSAGES
operator|.
name|exportTrue
argument_list|()
else|:
name|MESSAGES
operator|.
name|exportFalse
argument_list|()
operator|)
else|:
name|MESSAGES
operator|.
name|exportFalse
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|dispLastChanges
condition|)
name|bodyText
operator|.
name|add
argument_list|(
name|lastChangeStr
argument_list|)
expr_stmt|;
name|String
index|[]
name|bodyTextStr
init|=
operator|new
name|String
index|[
name|bodyText
operator|.
name|size
argument_list|()
index|]
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|bodyText
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|bodyTextStr
index|[
name|i
index|]
operator|=
name|bodyText
operator|.
name|get
argument_list|(
name|i
argument_list|)
expr_stmt|;
block|}
name|out
operator|.
name|printLine
argument_list|(
name|bodyTextStr
argument_list|)
expr_stmt|;
block|}
block|}
name|out
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
class|class
name|DepartmentComparator
implements|implements
name|Comparator
argument_list|<
name|Department
argument_list|>
block|{
specifier|private
name|DepartmentsColumn
name|iColumn
decl_stmt|;
specifier|private
name|boolean
name|iAsc
decl_stmt|;
specifier|public
name|DepartmentComparator
parameter_list|(
name|DepartmentsColumn
name|column
parameter_list|,
name|boolean
name|asc
parameter_list|)
block|{
name|iColumn
operator|=
name|column
expr_stmt|;
name|iAsc
operator|=
name|asc
expr_stmt|;
block|}
specifier|public
name|int
name|compareByExternalId
parameter_list|(
name|Department
name|r1
parameter_list|,
name|Department
name|r2
parameter_list|)
block|{
return|return
name|compare
argument_list|(
name|r1
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|,
name|r2
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|int
name|compareById
parameter_list|(
name|Department
name|r1
parameter_list|,
name|Department
name|r2
parameter_list|)
block|{
return|return
name|compare
argument_list|(
name|r1
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|r2
operator|.
name|getUniqueId
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|int
name|compareByDeptCode
parameter_list|(
name|Department
name|r1
parameter_list|,
name|Department
name|r2
parameter_list|)
block|{
return|return
name|compare
argument_list|(
name|r1
operator|.
name|getDeptCode
argument_list|()
argument_list|,
name|r2
operator|.
name|getDeptCode
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|int
name|compareByName
parameter_list|(
name|Department
name|r1
parameter_list|,
name|Department
name|r2
parameter_list|)
block|{
return|return
name|compare
argument_list|(
name|r1
operator|.
name|getName
argument_list|()
argument_list|,
name|r2
operator|.
name|getName
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|int
name|compareByAbbreviation
parameter_list|(
name|Department
name|r1
parameter_list|,
name|Department
name|r2
parameter_list|)
block|{
return|return
name|compare
argument_list|(
name|r1
operator|.
name|getAbbreviation
argument_list|()
argument_list|,
name|r2
operator|.
name|getAbbreviation
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|int
name|compareByExtMgr
parameter_list|(
name|Department
name|r1
parameter_list|,
name|Department
name|r2
parameter_list|)
block|{
return|return
name|compare
argument_list|(
name|r1
operator|.
name|getExternalMgrAbbv
argument_list|()
argument_list|,
name|r2
operator|.
name|getExternalMgrAbbv
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|int
name|compareByStatus
parameter_list|(
name|Department
name|r1
parameter_list|,
name|Department
name|r2
parameter_list|)
block|{
return|return
name|compare
argument_list|(
name|r1
operator|.
name|getStatusType
argument_list|()
operator|.
name|getLabel
argument_list|()
argument_list|,
name|r2
operator|.
name|getStatusType
argument_list|()
operator|.
name|getLabel
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|int
name|compareByDistPrefPriority
parameter_list|(
name|Department
name|r1
parameter_list|,
name|Department
name|r2
parameter_list|)
block|{
return|return
name|compare
argument_list|(
name|r1
operator|.
name|getDistributionPrefPriority
argument_list|()
argument_list|,
name|r2
operator|.
name|getDistributionPrefPriority
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|int
name|compareByAllowReqd
parameter_list|(
name|Department
name|r1
parameter_list|,
name|Department
name|r2
parameter_list|)
block|{
return|return
name|compare
argument_list|(
name|r1
operator|.
name|getAllowReqTime
argument_list|()
argument_list|,
name|r2
operator|.
name|getAllowReqTime
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|int
name|compareByInstrucPref
parameter_list|(
name|Department
name|r1
parameter_list|,
name|Department
name|r2
parameter_list|)
block|{
return|return
name|compare
argument_list|(
name|r1
operator|.
name|isInheritInstructorPreferences
argument_list|()
argument_list|,
name|r2
operator|.
name|isInheritInstructorPreferences
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|int
name|compareByEvent
parameter_list|(
name|Department
name|r1
parameter_list|,
name|Department
name|r2
parameter_list|)
block|{
return|return
name|compare
argument_list|(
name|r1
operator|.
name|isAllowEvents
argument_list|()
argument_list|,
name|r2
operator|.
name|isAllowEvents
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|int
name|compareByStdntSched
parameter_list|(
name|Department
name|r1
parameter_list|,
name|Department
name|r2
parameter_list|)
block|{
return|return
name|compare
argument_list|(
name|r1
operator|.
name|isAllowStudentScheduling
argument_list|()
argument_list|,
name|r2
operator|.
name|isAllowStudentScheduling
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|int
name|compareByExtFundingDept
parameter_list|(
name|Department
name|r1
parameter_list|,
name|Department
name|r2
parameter_list|)
block|{
return|return
name|compare
argument_list|(
name|r1
operator|.
name|isExternalFundingDept
argument_list|()
argument_list|,
name|r2
operator|.
name|isExternalFundingDept
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|int
name|compareBySubjectCount
parameter_list|(
name|Department
name|r1
parameter_list|,
name|Department
name|r2
parameter_list|)
block|{
return|return
name|compare
argument_list|(
name|r1
operator|.
name|getSubjectAreas
argument_list|()
operator|.
name|size
argument_list|()
argument_list|,
name|r2
operator|.
name|getSubjectAreas
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|int
name|compareByRoomCount
parameter_list|(
name|Department
name|r1
parameter_list|,
name|Department
name|r2
parameter_list|)
block|{
return|return
name|compare
argument_list|(
name|r1
operator|.
name|getRoomDepts
argument_list|()
operator|.
name|size
argument_list|()
argument_list|,
name|r2
operator|.
name|getRoomDepts
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
return|;
block|}
specifier|protected
name|int
name|compareByColumn
parameter_list|(
name|Department
name|r1
parameter_list|,
name|Department
name|r2
parameter_list|)
block|{
switch|switch
condition|(
name|iColumn
condition|)
block|{
case|case
name|CODE
case|:
return|return
name|compareByDeptCode
argument_list|(
name|r1
argument_list|,
name|r2
argument_list|)
return|;
case|case
name|ABBV
case|:
return|return
name|compareByAbbreviation
argument_list|(
name|r1
argument_list|,
name|r2
argument_list|)
return|;
case|case
name|NAME
case|:
return|return
name|compareByName
argument_list|(
name|r1
argument_list|,
name|r2
argument_list|)
return|;
case|case
name|EXTERNAL_MANAGER
case|:
return|return
name|compareByExtMgr
argument_list|(
name|r1
argument_list|,
name|r2
argument_list|)
return|;
case|case
name|SUBJECTS
case|:
return|return
name|compareBySubjectCount
argument_list|(
name|r1
argument_list|,
name|r2
argument_list|)
return|;
case|case
name|ROOMS
case|:
return|return
name|compareByRoomCount
argument_list|(
name|r1
argument_list|,
name|r2
argument_list|)
return|;
case|case
name|STATUS
case|:
return|return
name|compareByStatus
argument_list|(
name|r1
argument_list|,
name|r2
argument_list|)
return|;
case|case
name|DIST_PREF_PRIORITY
case|:
return|return
name|compareByDistPrefPriority
argument_list|(
name|r1
argument_list|,
name|r2
argument_list|)
return|;
case|case
name|ALLOW_REQUIRED
case|:
return|return
name|compareByAllowReqd
argument_list|(
name|r1
argument_list|,
name|r2
argument_list|)
return|;
case|case
name|INSTRUCTOR_PREF
case|:
return|return
name|compareByInstrucPref
argument_list|(
name|r1
argument_list|,
name|r2
argument_list|)
return|;
case|case
name|EVENTS
case|:
return|return
name|compareByEvent
argument_list|(
name|r1
argument_list|,
name|r2
argument_list|)
return|;
case|case
name|STUDENT_SCHEDULING
case|:
return|return
name|compareByStdntSched
argument_list|(
name|r1
argument_list|,
name|r2
argument_list|)
return|;
case|case
name|EXT_FUNDING_DEPT
case|:
return|return
name|compareByExtFundingDept
argument_list|(
name|r1
argument_list|,
name|r2
argument_list|)
return|;
default|default:
return|return
name|compareByAbbreviation
argument_list|(
name|r1
argument_list|,
name|r2
argument_list|)
return|;
block|}
block|}
specifier|protected
name|int
name|compare
parameter_list|(
name|Boolean
name|b1
parameter_list|,
name|Boolean
name|b2
parameter_list|)
block|{
return|return
operator|-
name|Boolean
operator|.
name|compare
argument_list|(
name|b1
operator|!=
literal|null
operator|&&
name|b1
operator|.
name|booleanValue
argument_list|()
argument_list|,
name|b2
operator|!=
literal|null
operator|&&
name|b2
operator|.
name|booleanValue
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|int
name|compare
parameter_list|(
name|Department
name|r1
parameter_list|,
name|Department
name|r2
parameter_list|)
block|{
name|int
name|cmp
init|=
name|compareByColumn
argument_list|(
name|r1
argument_list|,
name|r2
argument_list|)
decl_stmt|;
if|if
condition|(
name|cmp
operator|==
literal|0
condition|)
name|cmp
operator|=
name|compareByAbbreviation
argument_list|(
name|r1
argument_list|,
name|r2
argument_list|)
expr_stmt|;
if|if
condition|(
name|cmp
operator|==
literal|0
condition|)
name|cmp
operator|=
name|compareById
argument_list|(
name|r1
argument_list|,
name|r2
argument_list|)
expr_stmt|;
return|return
operator|(
name|iAsc
condition|?
name|cmp
else|:
operator|-
name|cmp
operator|)
return|;
block|}
specifier|protected
name|int
name|compare
parameter_list|(
name|String
name|s1
parameter_list|,
name|String
name|s2
parameter_list|)
block|{
if|if
condition|(
name|s1
operator|==
literal|null
operator|||
name|s1
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
operator|(
name|s2
operator|==
literal|null
operator|||
name|s2
operator|.
name|isEmpty
argument_list|()
condition|?
literal|0
else|:
literal|1
operator|)
return|;
block|}
else|else
block|{
return|return
operator|(
name|s2
operator|==
literal|null
operator|||
name|s2
operator|.
name|isEmpty
argument_list|()
condition|?
operator|-
literal|1
else|:
name|s1
operator|.
name|compareToIgnoreCase
argument_list|(
name|s2
argument_list|)
operator|)
return|;
block|}
block|}
specifier|protected
name|int
name|compare
parameter_list|(
name|Number
name|n1
parameter_list|,
name|Number
name|n2
parameter_list|)
block|{
return|return
operator|(
name|n1
operator|==
literal|null
condition|?
name|n2
operator|==
literal|null
condition|?
literal|0
else|:
operator|-
literal|1
else|:
name|n2
operator|==
literal|null
condition|?
literal|1
else|:
name|Double
operator|.
name|compare
argument_list|(
name|n1
operator|.
name|doubleValue
argument_list|()
argument_list|,
name|n2
operator|.
name|doubleValue
argument_list|()
argument_list|)
operator|)
return|;
block|}
block|}
block|}
end_class

end_unit

