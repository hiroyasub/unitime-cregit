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
name|gwt
operator|.
name|client
operator|.
name|departments
package|;
end_package

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
name|com
operator|.
name|google
operator|.
name|gwt
operator|.
name|i18n
operator|.
name|client
operator|.
name|NumberFormat
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
name|client
operator|.
name|admin
operator|.
name|AdminCookie
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
name|client
operator|.
name|widgets
operator|.
name|P
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
name|client
operator|.
name|page
operator|.
name|UniTimePageHeader
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
name|client
operator|.
name|rooms
operator|.
name|RoomsTable
operator|.
name|IntegerCell
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
name|client
operator|.
name|widgets
operator|.
name|UniTimeTable
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
name|client
operator|.
name|widgets
operator|.
name|UniTimeTableHeader
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
name|client
operator|.
name|widgets
operator|.
name|UniTimeTableHeader
operator|.
name|HasColumnName
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
name|client
operator|.
name|widgets
operator|.
name|UniTimeTableHeader
operator|.
name|Operation
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
name|com
operator|.
name|google
operator|.
name|gwt
operator|.
name|core
operator|.
name|client
operator|.
name|GWT
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|gwt
operator|.
name|user
operator|.
name|client
operator|.
name|ui
operator|.
name|CheckBox
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|gwt
operator|.
name|user
operator|.
name|client
operator|.
name|ui
operator|.
name|HTML
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|gwt
operator|.
name|user
operator|.
name|client
operator|.
name|ui
operator|.
name|Label
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|gwt
operator|.
name|user
operator|.
name|client
operator|.
name|ui
operator|.
name|Widget
import|;
end_import

begin_class
specifier|public
class|class
name|DepartmentsTable
extends|extends
name|UniTimeTable
argument_list|<
name|DepartmentInterface
argument_list|>
block|{
specifier|protected
specifier|static
specifier|final
name|GwtMessages
name|MESSAGES
init|=
name|GWT
operator|.
name|create
argument_list|(
name|GwtMessages
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|DepartmentsColumn
name|iSortBy
init|=
literal|null
decl_stmt|;
specifier|private
name|boolean
name|iAsc
init|=
literal|true
decl_stmt|;
specifier|private
name|boolean
name|iSelectable
init|=
literal|true
decl_stmt|;
specifier|public
name|DepartmentsTable
parameter_list|()
block|{
name|setHeaderData
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|selectDept
parameter_list|(
name|int
name|row
parameter_list|,
name|boolean
name|value
parameter_list|)
block|{
name|Widget
name|w
init|=
name|getWidget
argument_list|(
name|row
argument_list|,
literal|0
argument_list|)
decl_stmt|;
if|if
condition|(
name|w
operator|!=
literal|null
operator|&&
name|w
operator|instanceof
name|CheckBox
condition|)
block|{
operator|(
operator|(
name|CheckBox
operator|)
name|w
operator|)
operator|.
name|setValue
argument_list|(
name|value
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|String
name|getColumnName
parameter_list|(
name|DepartmentsColumn
name|column
parameter_list|)
block|{
switch|switch
condition|(
name|column
condition|)
block|{
case|case
name|CODE
case|:
return|return
name|MESSAGES
operator|.
name|colCode
argument_list|()
return|;
case|case
name|ABBV
case|:
return|return
name|MESSAGES
operator|.
name|colAbbv
argument_list|()
return|;
case|case
name|NAME
case|:
return|return
name|MESSAGES
operator|.
name|colName
argument_list|()
return|;
case|case
name|EXTERNAL_MANAGER
case|:
return|return
name|MESSAGES
operator|.
name|colExternalManager
argument_list|()
return|;
case|case
name|SUBJECTS
case|:
return|return
name|MESSAGES
operator|.
name|colSubjects
argument_list|()
return|;
case|case
name|ROOMS
case|:
return|return
name|MESSAGES
operator|.
name|colRooms
argument_list|()
return|;
case|case
name|STATUS
case|:
return|return
name|MESSAGES
operator|.
name|colStatus
argument_list|()
return|;
case|case
name|DIST_PREF_PRIORITY
case|:
return|return
name|MESSAGES
operator|.
name|colDistPrefPriority
argument_list|()
return|;
case|case
name|ALLOW_REQUIRED
case|:
return|return
name|MESSAGES
operator|.
name|colAllowRequired
argument_list|()
return|;
case|case
name|INSTRUCTOR_PREF
case|:
return|return
name|MESSAGES
operator|.
name|colInstructorPref
argument_list|()
return|;
case|case
name|EVENTS
case|:
return|return
name|MESSAGES
operator|.
name|colEvents
argument_list|()
return|;
case|case
name|STUDENT_SCHEDULING
case|:
return|return
name|MESSAGES
operator|.
name|colStudentScheduling
argument_list|()
return|;
case|case
name|EXT_FUNDING_DEPT
case|:
return|return
name|MESSAGES
operator|.
name|colExternalFundingDept
argument_list|()
return|;
case|case
name|LAST_CHANGE
case|:
return|return
name|MESSAGES
operator|.
name|colLastChange
argument_list|()
return|;
default|default:
return|return
name|column
operator|.
name|name
argument_list|()
return|;
block|}
block|}
specifier|public
name|String
name|getUser
parameter_list|()
block|{
return|return
name|UniTimePageHeader
operator|.
name|getInstance
argument_list|()
operator|.
name|getMiddle
argument_list|()
operator|.
name|getText
argument_list|()
return|;
block|}
specifier|public
name|Widget
name|getColumnWidget
parameter_list|(
name|DepartmentsColumn
name|column
parameter_list|,
name|DepartmentInterface
name|department
parameter_list|)
block|{
empty_stmt|;
name|NumberFormat
name|df5
init|=
name|NumberFormat
operator|.
name|getFormat
argument_list|(
literal|"####0"
argument_list|)
decl_stmt|;
switch|switch
condition|(
name|column
condition|)
block|{
case|case
name|CODE
case|:
return|return
operator|new
name|Label
argument_list|(
name|department
operator|.
name|getDeptCode
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|department
operator|.
name|getDeptCode
argument_list|()
argument_list|)
return|;
case|case
name|ABBV
case|:
return|return
operator|new
name|Label
argument_list|(
name|department
operator|.
name|getAbbreviation
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|department
operator|.
name|getAbbreviation
argument_list|()
argument_list|)
return|;
case|case
name|NAME
case|:
return|return
operator|new
name|HTML
argument_list|(
name|department
operator|.
name|getName
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|department
operator|.
name|getName
argument_list|()
argument_list|,
literal|false
argument_list|)
return|;
case|case
name|EXTERNAL_MANAGER
case|:
return|return
operator|new
name|Label
argument_list|(
name|department
operator|.
name|isExternalManager
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|?
name|department
operator|.
name|getExternalMgrAbbv
argument_list|()
else|:
literal|""
argument_list|)
return|;
case|case
name|SUBJECTS
case|:
return|return
operator|new
name|IntegerCell
argument_list|(
name|department
operator|.
name|getSubjectAreaCount
argument_list|()
argument_list|)
return|;
comment|//	return new Label( df5.format(department.getSubjectAreaCount()));
case|case
name|ROOMS
case|:
return|return
operator|new
name|IntegerCell
argument_list|(
name|department
operator|.
name|getRoomDeptCount
argument_list|()
argument_list|)
return|;
comment|//return new Label(df5.format(department.getRoomDeptCount()));
case|case
name|STATUS
case|:
name|P
name|widget
init|=
operator|new
name|P
argument_list|(
literal|"departments-status"
argument_list|)
decl_stmt|;
name|P
name|ext
init|=
operator|new
name|P
argument_list|(
literal|"ext-status"
argument_list|)
decl_stmt|;
name|ext
operator|.
name|setText
argument_list|(
name|department
operator|.
name|effectiveStatusType
argument_list|()
argument_list|)
expr_stmt|;
name|ext
operator|.
name|addStyleName
argument_list|(
literal|"department-StatusItalics"
argument_list|)
expr_stmt|;
name|widget
operator|.
name|add
argument_list|(
name|ext
argument_list|)
expr_stmt|;
for|for
control|(
name|String
name|dependentStatus
range|:
name|department
operator|.
name|getDependentStatusesStr
argument_list|()
control|)
block|{
name|P
name|ext1
init|=
operator|new
name|P
argument_list|(
literal|"ext-status"
argument_list|)
decl_stmt|;
name|ext1
operator|.
name|setText
argument_list|(
name|dependentStatus
argument_list|)
expr_stmt|;
name|ext1
operator|.
name|addStyleName
argument_list|(
literal|"department-Status"
argument_list|)
expr_stmt|;
name|widget
operator|.
name|add
argument_list|(
name|ext1
argument_list|)
expr_stmt|;
block|}
return|return
name|widget
return|;
case|case
name|DIST_PREF_PRIORITY
case|:
return|return
operator|new
name|IntegerCell
argument_list|(
name|department
operator|.
name|getDistributionPrefPriority
argument_list|()
operator|==
literal|null
operator|&&
name|department
operator|.
name|getDistributionPrefPriority
argument_list|()
operator|==
literal|0
condition|?
literal|0
else|:
name|department
operator|.
name|getDistributionPrefPriority
argument_list|()
argument_list|)
return|;
case|case
name|ALLOW_REQUIRED
case|:
return|return
operator|new
name|Label
argument_list|(
name|department
operator|.
name|allowReq
argument_list|()
argument_list|)
return|;
case|case
name|INSTRUCTOR_PREF
case|:
name|P
name|instrucPrefWidget
init|=
operator|new
name|P
argument_list|(
literal|"instruc-pref"
argument_list|)
decl_stmt|;
if|if
condition|(
name|department
operator|.
name|isInheritInstructorPreferences
argument_list|()
condition|)
block|{
name|instrucPrefWidget
operator|.
name|addStyleName
argument_list|(
literal|"department-accept"
argument_list|)
expr_stmt|;
block|}
return|return
name|instrucPrefWidget
return|;
case|case
name|EVENTS
case|:
name|P
name|eventWidget
init|=
operator|new
name|P
argument_list|(
literal|"events"
argument_list|)
decl_stmt|;
if|if
condition|(
name|department
operator|.
name|isAllowEvents
argument_list|()
condition|)
block|{
name|eventWidget
operator|.
name|addStyleName
argument_list|(
literal|"department-accept"
argument_list|)
expr_stmt|;
block|}
return|return
name|eventWidget
return|;
case|case
name|STUDENT_SCHEDULING
case|:
name|P
name|allowStudentSchedulingWidget
init|=
operator|new
name|P
argument_list|(
literal|"allowStudentSchedulingWidget"
argument_list|)
decl_stmt|;
if|if
condition|(
name|department
operator|.
name|isAllowStudentScheduling
argument_list|()
condition|)
block|{
name|allowStudentSchedulingWidget
operator|.
name|addStyleName
argument_list|(
literal|"department-accept"
argument_list|)
expr_stmt|;
block|}
return|return
name|allowStudentSchedulingWidget
return|;
case|case
name|EXT_FUNDING_DEPT
case|:
name|P
name|extFundingDeptWidget
init|=
operator|new
name|P
argument_list|(
literal|"extFundingDeptWidget"
argument_list|)
decl_stmt|;
if|if
condition|(
name|department
operator|.
name|isCoursesFundingDepartmentsEnabled
argument_list|()
operator|==
literal|true
condition|)
block|{
if|if
condition|(
name|department
operator|.
name|isExternalFundingDept
argument_list|()
operator|!=
literal|null
operator|&&
name|department
operator|.
name|isExternalFundingDept
argument_list|()
operator|==
literal|true
condition|)
name|extFundingDeptWidget
operator|.
name|addStyleName
argument_list|(
literal|"department-accept"
argument_list|)
expr_stmt|;
return|return
name|extFundingDeptWidget
return|;
block|}
else|else
return|return
literal|null
return|;
case|case
name|LAST_CHANGE
case|:
return|return
operator|new
name|HTML
argument_list|(
name|department
operator|.
name|getLastChangeStr
argument_list|()
argument_list|,
literal|false
argument_list|)
return|;
default|default:
return|return
literal|null
return|;
block|}
block|}
specifier|protected
name|void
name|addRow
parameter_list|(
name|DepartmentInterface
name|department
parameter_list|)
block|{
name|List
argument_list|<
name|Widget
argument_list|>
name|line
init|=
operator|new
name|ArrayList
argument_list|<
name|Widget
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|DepartmentsColumn
name|col
range|:
name|DepartmentsColumn
operator|.
name|values
argument_list|()
control|)
block|{
if|if
condition|(
name|getColumnWidget
argument_list|(
name|col
argument_list|,
name|department
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|line
operator|.
name|add
argument_list|(
name|getColumnWidget
argument_list|(
name|col
argument_list|,
name|department
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
name|addRow
argument_list|(
name|department
argument_list|,
name|line
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|setHeaderData
parameter_list|(
name|boolean
name|fundingDepartmentsEnabled
parameter_list|)
block|{
name|clearTable
argument_list|()
expr_stmt|;
name|List
argument_list|<
name|UniTimeTableHeader
argument_list|>
name|header
init|=
operator|new
name|ArrayList
argument_list|<
name|UniTimeTableHeader
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
specifier|final
name|DepartmentsColumn
name|col
range|:
name|DepartmentsColumn
operator|.
name|values
argument_list|()
control|)
block|{
if|if
condition|(
name|DepartmentComparator
operator|.
name|isApplicable
argument_list|(
name|col
argument_list|)
operator|&&
operator|(
name|col
operator|!=
name|DepartmentsColumn
operator|.
name|EXT_FUNDING_DEPT
operator|||
operator|(
name|col
operator|==
name|DepartmentsColumn
operator|.
name|EXT_FUNDING_DEPT
operator|&&
name|fundingDepartmentsEnabled
operator|==
literal|true
operator|)
operator|)
condition|)
block|{
specifier|final
name|UniTimeTableHeader
name|h
init|=
operator|new
name|UniTimeTableHeader
argument_list|(
name|getColumnName
argument_list|(
name|col
argument_list|)
argument_list|)
decl_stmt|;
name|Operation
name|op
init|=
operator|new
name|SortOperation
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|execute
parameter_list|()
block|{
name|doSort
argument_list|(
name|col
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isApplicable
parameter_list|()
block|{
return|return
name|getRowCount
argument_list|()
operator|>
literal|1
operator|&&
name|h
operator|.
name|isVisible
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|hasSeparator
parameter_list|()
block|{
return|return
literal|false
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|MESSAGES
operator|.
name|opSortBy
argument_list|(
name|getColumnName
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getColumnName
parameter_list|()
block|{
return|return
name|h
operator|.
name|getHTML
argument_list|()
operator|.
name|replace
argument_list|(
literal|"<br>"
argument_list|,
literal|" "
argument_list|)
return|;
block|}
block|}
decl_stmt|;
name|h
operator|.
name|addOperation
argument_list|(
name|op
argument_list|)
expr_stmt|;
name|header
operator|.
name|add
argument_list|(
name|h
argument_list|)
expr_stmt|;
block|}
block|}
name|addRow
argument_list|(
literal|null
argument_list|,
name|header
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|getCellCount
argument_list|(
literal|0
argument_list|)
condition|;
name|i
operator|++
control|)
name|getCellFormatter
argument_list|()
operator|.
name|setStyleName
argument_list|(
literal|0
argument_list|,
name|i
argument_list|,
literal|"unitime-ClickableTableHeader"
argument_list|)
expr_stmt|;
if|if
condition|(
name|iSelectable
condition|)
block|{
name|setAllowSelection
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|addMouseClickListener
argument_list|(
operator|new
name|MouseClickListener
argument_list|<
name|DepartmentInterface
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onMouseClick
parameter_list|(
name|TableEvent
argument_list|<
name|DepartmentInterface
argument_list|>
name|event
parameter_list|)
block|{
name|selectDept
argument_list|(
name|event
operator|.
name|getRow
argument_list|()
argument_list|,
name|isSelected
argument_list|(
name|event
operator|.
name|getRow
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
name|setSortBy
argument_list|(
name|AdminCookie
operator|.
name|getInstance
argument_list|()
operator|.
name|getSortDepartmentsBy
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|setData
parameter_list|(
name|List
argument_list|<
name|DepartmentInterface
argument_list|>
name|departments
parameter_list|,
name|boolean
name|showAlldept
parameter_list|)
block|{
name|clearTable
argument_list|(
literal|1
argument_list|)
expr_stmt|;
if|if
condition|(
name|departments
operator|!=
literal|null
condition|)
for|for
control|(
name|DepartmentInterface
name|department
range|:
name|departments
control|)
if|if
condition|(
name|showAlldept
operator|||
name|department
operator|.
name|getSubjectAreaCount
argument_list|()
operator|!=
literal|0
operator|||
name|department
operator|.
name|getTimetableManagersCount
argument_list|()
operator|!=
literal|0
operator|||
name|department
operator|.
name|isExternalManager
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|)
block|{
name|addRow
argument_list|(
name|department
argument_list|)
expr_stmt|;
block|}
name|sort
argument_list|()
expr_stmt|;
block|}
specifier|protected
name|void
name|doSort
parameter_list|(
name|DepartmentsColumn
name|column
parameter_list|)
block|{
if|if
condition|(
name|column
operator|==
name|iSortBy
condition|)
block|{
name|iAsc
operator|=
operator|!
name|iAsc
expr_stmt|;
block|}
else|else
block|{
name|iSortBy
operator|=
name|column
expr_stmt|;
name|iAsc
operator|=
literal|true
expr_stmt|;
block|}
name|AdminCookie
operator|.
name|getInstance
argument_list|()
operator|.
name|setSortDepartmentsBy
argument_list|(
name|getSortBy
argument_list|()
argument_list|)
expr_stmt|;
name|sort
argument_list|()
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasSortBy
parameter_list|()
block|{
return|return
name|iSortBy
operator|!=
literal|null
return|;
block|}
specifier|public
name|int
name|getSortBy
parameter_list|()
block|{
return|return
name|iSortBy
operator|==
literal|null
condition|?
literal|0
else|:
name|iAsc
condition|?
literal|1
operator|+
name|iSortBy
operator|.
name|ordinal
argument_list|()
else|:
operator|-
literal|1
operator|-
name|iSortBy
operator|.
name|ordinal
argument_list|()
return|;
block|}
specifier|public
name|void
name|setSortBy
parameter_list|(
name|int
name|sortBy
parameter_list|)
block|{
if|if
condition|(
name|sortBy
operator|==
literal|0
condition|)
block|{
name|iSortBy
operator|=
literal|null
expr_stmt|;
name|iAsc
operator|=
literal|true
expr_stmt|;
block|}
if|else if
condition|(
name|sortBy
operator|>
literal|0
condition|)
block|{
name|iSortBy
operator|=
name|DepartmentsColumn
operator|.
name|values
argument_list|()
index|[
name|sortBy
operator|-
literal|1
index|]
expr_stmt|;
name|iAsc
operator|=
literal|true
expr_stmt|;
block|}
else|else
block|{
name|iSortBy
operator|=
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
expr_stmt|;
name|iAsc
operator|=
literal|false
expr_stmt|;
block|}
name|sort
argument_list|()
expr_stmt|;
block|}
specifier|public
name|void
name|sort
parameter_list|()
block|{
if|if
condition|(
name|iSortBy
operator|==
literal|null
condition|)
return|return;
name|UniTimeTableHeader
name|header
init|=
name|getHeader
argument_list|(
name|iSortBy
operator|.
name|ordinal
argument_list|()
argument_list|)
decl_stmt|;
name|sort
argument_list|(
name|header
argument_list|,
operator|new
name|DepartmentComparator
argument_list|(
name|iSortBy
argument_list|,
literal|true
argument_list|)
argument_list|,
name|iAsc
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
interface|interface
name|SortOperation
extends|extends
name|Operation
extends|,
name|HasColumnName
block|{}
specifier|public
specifier|static
class|class
name|DepartmentComparator
implements|implements
name|Comparator
argument_list|<
name|DepartmentInterface
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
name|compareById
parameter_list|(
name|DepartmentInterface
name|r1
parameter_list|,
name|DepartmentInterface
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
name|DepartmentInterface
name|r1
parameter_list|,
name|DepartmentInterface
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
name|DepartmentInterface
name|r1
parameter_list|,
name|DepartmentInterface
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
name|DepartmentInterface
name|r1
parameter_list|,
name|DepartmentInterface
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
name|DepartmentInterface
name|r1
parameter_list|,
name|DepartmentInterface
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
name|compareBySubjectCount
parameter_list|(
name|DepartmentInterface
name|r1
parameter_list|,
name|DepartmentInterface
name|r2
parameter_list|)
block|{
return|return
name|compare
argument_list|(
name|r1
operator|.
name|getSubjectAreaCount
argument_list|()
argument_list|,
name|r2
operator|.
name|getSubjectAreaCount
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|int
name|compareByRoomCount
parameter_list|(
name|DepartmentInterface
name|r1
parameter_list|,
name|DepartmentInterface
name|r2
parameter_list|)
block|{
return|return
name|compare
argument_list|(
name|r1
operator|.
name|getRoomDeptCount
argument_list|()
argument_list|,
name|r2
operator|.
name|getRoomDeptCount
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|int
name|compareByStatus
parameter_list|(
name|DepartmentInterface
name|r1
parameter_list|,
name|DepartmentInterface
name|r2
parameter_list|)
block|{
return|return
name|compare
argument_list|(
name|r1
operator|.
name|getStatusTypeStr
argument_list|()
argument_list|,
name|r2
operator|.
name|getStatusTypeStr
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|int
name|compareByDistPrefPriority
parameter_list|(
name|DepartmentInterface
name|r1
parameter_list|,
name|DepartmentInterface
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
name|DepartmentInterface
name|r1
parameter_list|,
name|DepartmentInterface
name|r2
parameter_list|)
block|{
return|return
name|compare
argument_list|(
name|r1
operator|.
name|allowReq
argument_list|()
argument_list|,
name|r2
operator|.
name|allowReq
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|int
name|compareByInstrucPref
parameter_list|(
name|DepartmentInterface
name|r1
parameter_list|,
name|DepartmentInterface
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
name|DepartmentInterface
name|r1
parameter_list|,
name|DepartmentInterface
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
name|DepartmentInterface
name|r1
parameter_list|,
name|DepartmentInterface
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
name|compareByLastChange
parameter_list|(
name|DepartmentInterface
name|r1
parameter_list|,
name|DepartmentInterface
name|r2
parameter_list|)
block|{
return|return
name|compare
argument_list|(
name|r1
operator|.
name|getLastChangeStr
argument_list|()
argument_list|,
name|r2
operator|.
name|getLastChangeStr
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|int
name|compareByExtFundingDept
parameter_list|(
name|DepartmentInterface
name|r1
parameter_list|,
name|DepartmentInterface
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
specifier|protected
name|int
name|compareByColumn
parameter_list|(
name|DepartmentInterface
name|r1
parameter_list|,
name|DepartmentInterface
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
case|case
name|LAST_CHANGE
case|:
return|return
name|compareByLastChange
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
specifier|public
specifier|static
name|boolean
name|isApplicable
parameter_list|(
name|DepartmentsColumn
name|column
parameter_list|)
block|{
switch|switch
condition|(
name|column
condition|)
block|{
case|case
name|CODE
case|:
case|case
name|ABBV
case|:
case|case
name|NAME
case|:
case|case
name|EXTERNAL_MANAGER
case|:
case|case
name|SUBJECTS
case|:
case|case
name|ROOMS
case|:
case|case
name|STATUS
case|:
case|case
name|DIST_PREF_PRIORITY
case|:
case|case
name|ALLOW_REQUIRED
case|:
case|case
name|INSTRUCTOR_PREF
case|:
case|case
name|EVENTS
case|:
case|case
name|STUDENT_SCHEDULING
case|:
case|case
name|EXT_FUNDING_DEPT
case|:
case|case
name|LAST_CHANGE
case|:
return|return
literal|true
return|;
default|default:
return|return
literal|false
return|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|int
name|compare
parameter_list|(
name|DepartmentInterface
name|r1
parameter_list|,
name|DepartmentInterface
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
operator|(
name|b1
operator|==
literal|null
condition|?
name|b2
operator|==
literal|null
condition|?
literal|0
else|:
operator|-
literal|1
else|:
name|b2
operator|==
literal|null
condition|?
literal|1
else|:
name|Boolean
operator|.
name|compare
argument_list|(
name|b1
operator|.
name|booleanValue
argument_list|()
argument_list|,
name|b2
operator|.
name|booleanValue
argument_list|()
argument_list|)
operator|)
return|;
block|}
block|}
block|}
end_class

end_unit

