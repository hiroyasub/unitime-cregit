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
name|solver
operator|.
name|suggestions
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
name|Collection
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
name|solver
operator|.
name|SolverCookie
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
name|GwtConstants
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
name|resources
operator|.
name|GwtResources
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
name|SuggestionsInterface
operator|.
name|ClassAssignmentDetails
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
name|SuggestionsInterface
operator|.
name|TimeInfo
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
name|TableInterface
operator|.
name|NaturalOrderComparator
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
name|event
operator|.
name|dom
operator|.
name|client
operator|.
name|ClickEvent
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
name|event
operator|.
name|dom
operator|.
name|client
operator|.
name|ClickHandler
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
name|TakesValue
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
name|HasHorizontalAlignment
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
name|HasHorizontalAlignment
operator|.
name|HorizontalAlignmentConstant
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|ConflictTable
extends|extends
name|UniTimeTable
argument_list|<
name|ClassAssignmentDetails
argument_list|>
implements|implements
name|TakesValue
argument_list|<
name|Collection
argument_list|<
name|ClassAssignmentDetails
argument_list|>
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
specifier|protected
specifier|static
specifier|final
name|GwtConstants
name|CONSTANTS
init|=
name|GWT
operator|.
name|create
argument_list|(
name|GwtConstants
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|protected
specifier|static
specifier|final
name|GwtResources
name|RESOURCES
init|=
name|GWT
operator|.
name|create
argument_list|(
name|GwtResources
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|SuggestionsPageContext
name|iContext
decl_stmt|;
specifier|private
name|ConflictColum
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
specifier|public
name|ConflictTable
parameter_list|(
name|SuggestionsPageContext
name|context
parameter_list|)
block|{
name|addStyleName
argument_list|(
literal|"unitime-ClassAssignmentTable"
argument_list|)
expr_stmt|;
name|addStyleName
argument_list|(
literal|"unitime-ClassAssignmentTableConflicts"
argument_list|)
expr_stmt|;
name|iContext
operator|=
name|context
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
name|ConflictColum
name|column
range|:
name|ConflictColum
operator|.
name|values
argument_list|()
control|)
block|{
name|int
name|nrCells
init|=
name|getNbrCells
argument_list|(
name|column
argument_list|)
decl_stmt|;
for|for
control|(
name|int
name|idx
init|=
literal|0
init|;
name|idx
operator|<
name|nrCells
condition|;
name|idx
operator|++
control|)
block|{
name|UniTimeTableHeader
name|h
init|=
operator|new
name|UniTimeTableHeader
argument_list|(
name|getColumnName
argument_list|(
name|column
argument_list|,
name|idx
argument_list|)
argument_list|,
name|getColumnAlignment
argument_list|(
name|column
argument_list|,
name|idx
argument_list|)
argument_list|)
decl_stmt|;
name|header
operator|.
name|add
argument_list|(
name|h
argument_list|)
expr_stmt|;
block|}
block|}
for|for
control|(
specifier|final
name|ConflictColum
name|column
range|:
name|ConflictColum
operator|.
name|values
argument_list|()
control|)
block|{
if|if
condition|(
name|ConflictsComparator
operator|.
name|isApplicable
argument_list|(
name|column
argument_list|)
operator|&&
name|getNbrCells
argument_list|(
name|column
argument_list|)
operator|>
literal|0
condition|)
block|{
specifier|final
name|UniTimeTableHeader
name|h
init|=
name|header
operator|.
name|get
argument_list|(
name|getCellIndex
argument_list|(
name|column
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
name|column
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
block|}
if|if
condition|(
name|column
operator|==
name|ConflictColum
operator|.
name|STUDENT_CONFLICTS
operator|||
name|column
operator|==
name|ConflictColum
operator|.
name|DISTRIBUTION_CONFLICTS
condition|)
block|{
specifier|final
name|int
name|index
init|=
name|getCellIndex
argument_list|(
name|column
argument_list|)
decl_stmt|;
specifier|final
name|UniTimeTableHeader
name|h
init|=
name|header
operator|.
name|get
argument_list|(
name|index
argument_list|)
decl_stmt|;
name|h
operator|.
name|addOperation
argument_list|(
operator|new
name|Operation
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|execute
parameter_list|()
block|{
if|if
condition|(
name|column
operator|==
name|ConflictColum
operator|.
name|STUDENT_CONFLICTS
condition|)
name|SolverCookie
operator|.
name|getInstance
argument_list|()
operator|.
name|setShowAllStudentConflicts
argument_list|(
literal|true
argument_list|)
expr_stmt|;
else|else
name|SolverCookie
operator|.
name|getInstance
argument_list|()
operator|.
name|setShowAllDistributionConflicts
argument_list|(
literal|true
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|row
init|=
literal|1
init|;
name|row
operator|<
name|getRowCount
argument_list|()
condition|;
name|row
operator|++
control|)
block|{
name|Widget
name|w
init|=
name|getWidget
argument_list|(
name|row
argument_list|,
name|index
argument_list|)
decl_stmt|;
if|if
condition|(
name|w
operator|instanceof
name|ConflictCell
condition|)
operator|(
operator|(
name|ConflictCell
operator|)
name|w
operator|)
operator|.
name|showList
argument_list|()
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isApplicable
parameter_list|()
block|{
return|return
literal|true
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
name|opShowAllConflicts
argument_list|()
return|;
block|}
block|}
argument_list|)
expr_stmt|;
name|h
operator|.
name|addOperation
argument_list|(
operator|new
name|Operation
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|execute
parameter_list|()
block|{
if|if
condition|(
name|column
operator|==
name|ConflictColum
operator|.
name|STUDENT_CONFLICTS
condition|)
name|SolverCookie
operator|.
name|getInstance
argument_list|()
operator|.
name|setShowAllStudentConflicts
argument_list|(
literal|false
argument_list|)
expr_stmt|;
else|else
name|SolverCookie
operator|.
name|getInstance
argument_list|()
operator|.
name|setShowAllDistributionConflicts
argument_list|(
literal|false
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|row
init|=
literal|1
init|;
name|row
operator|<
name|getRowCount
argument_list|()
condition|;
name|row
operator|++
control|)
block|{
name|Widget
name|w
init|=
name|getWidget
argument_list|(
name|row
argument_list|,
name|index
argument_list|)
decl_stmt|;
if|if
condition|(
name|w
operator|instanceof
name|ConflictCell
condition|)
operator|(
operator|(
name|ConflictCell
operator|)
name|w
operator|)
operator|.
name|showNumber
argument_list|()
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isApplicable
parameter_list|()
block|{
return|return
literal|true
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
name|opHideAllConflicts
argument_list|()
return|;
block|}
block|}
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
name|setSortBy
argument_list|(
name|SolverCookie
operator|.
name|getInstance
argument_list|()
operator|.
name|getConflictsSort
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|doSort
parameter_list|(
name|ConflictColum
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
name|SolverCookie
operator|.
name|getInstance
argument_list|()
operator|.
name|setConflictsSort
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
name|ConflictColum
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
name|ConflictColum
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
if|if
condition|(
name|getNbrCells
argument_list|(
name|iSortBy
argument_list|)
operator|==
literal|0
condition|)
name|iSortBy
operator|=
name|ConflictColum
operator|.
name|TIME
expr_stmt|;
name|UniTimeTableHeader
name|header
init|=
name|getHeader
argument_list|(
name|getCellIndex
argument_list|(
name|iSortBy
argument_list|)
argument_list|)
decl_stmt|;
name|sort
argument_list|(
name|header
argument_list|,
operator|new
name|ConflictsComparator
argument_list|(
name|iContext
operator|.
name|getProperties
argument_list|()
operator|.
name|getFirstDay
argument_list|()
argument_list|,
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
enum|enum
name|ConflictColum
block|{
name|DATE
block|,
name|TIME
block|,
name|STUDENT_CONFLICTS
block|,
name|DISTRIBUTION_CONFLICTS
block|, 		; 	}
specifier|protected
name|int
name|getNbrCells
parameter_list|(
name|ConflictColum
name|column
parameter_list|)
block|{
switch|switch
condition|(
name|column
condition|)
block|{
default|default:
return|return
literal|1
return|;
block|}
block|}
specifier|public
name|String
name|getColumnName
parameter_list|(
name|ConflictColum
name|column
parameter_list|,
name|int
name|idx
parameter_list|)
block|{
switch|switch
condition|(
name|column
condition|)
block|{
case|case
name|DATE
case|:
return|return
name|MESSAGES
operator|.
name|colDate
argument_list|()
return|;
case|case
name|TIME
case|:
return|return
name|MESSAGES
operator|.
name|colTime
argument_list|()
return|;
case|case
name|STUDENT_CONFLICTS
case|:
return|return
name|MESSAGES
operator|.
name|colStudentConflicts
argument_list|()
return|;
case|case
name|DISTRIBUTION_CONFLICTS
case|:
return|return
name|MESSAGES
operator|.
name|colDistributionConflicts
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
specifier|protected
name|HorizontalAlignmentConstant
name|getColumnAlignment
parameter_list|(
name|ConflictColum
name|column
parameter_list|,
name|int
name|idx
parameter_list|)
block|{
switch|switch
condition|(
name|column
condition|)
block|{
default|default:
return|return
name|HasHorizontalAlignment
operator|.
name|ALIGN_LEFT
return|;
block|}
block|}
specifier|protected
name|int
name|getCellIndex
parameter_list|(
name|ConflictColum
name|column
parameter_list|)
block|{
name|int
name|ret
init|=
literal|0
decl_stmt|;
for|for
control|(
name|ConflictColum
name|c
range|:
name|ConflictColum
operator|.
name|values
argument_list|()
control|)
if|if
condition|(
name|c
operator|.
name|ordinal
argument_list|()
operator|<
name|column
operator|.
name|ordinal
argument_list|()
condition|)
name|ret
operator|+=
name|getNbrCells
argument_list|(
name|c
argument_list|)
expr_stmt|;
return|return
name|ret
return|;
block|}
specifier|protected
name|Widget
name|getCell
parameter_list|(
specifier|final
name|ClassAssignmentDetails
name|conflict
parameter_list|,
specifier|final
name|ConflictColum
name|column
parameter_list|,
specifier|final
name|int
name|idx
parameter_list|)
block|{
switch|switch
condition|(
name|column
condition|)
block|{
case|case
name|DATE
case|:
return|return
name|iContext
operator|.
name|createDateLabel
argument_list|(
name|conflict
operator|.
name|getTime
argument_list|()
operator|.
name|getDatePattern
argument_list|()
argument_list|)
return|;
case|case
name|TIME
case|:
return|return
name|iContext
operator|.
name|createTimeLabel
argument_list|(
name|conflict
operator|.
name|getTime
argument_list|()
argument_list|,
name|conflict
operator|.
name|getClazz
argument_list|()
operator|.
name|getClassId
argument_list|()
argument_list|,
literal|true
argument_list|)
return|;
case|case
name|STUDENT_CONFLICTS
case|:
if|if
condition|(
name|conflict
operator|.
name|hasStudentConflicts
argument_list|()
condition|)
block|{
return|return
operator|new
name|ConflictCell
argument_list|(
name|SuggestionsPageContext
operator|.
name|dispNumber
argument_list|(
name|conflict
operator|.
name|countStudentConflicts
argument_list|()
argument_list|)
argument_list|,
name|iContext
operator|.
name|createStudentConflicts
argument_list|(
name|conflict
operator|.
name|getStudentConflicts
argument_list|()
argument_list|)
argument_list|,
name|SolverCookie
operator|.
name|getInstance
argument_list|()
operator|.
name|isShowAllStudentConflicts
argument_list|()
argument_list|)
return|;
block|}
else|else
block|{
return|return
literal|null
return|;
block|}
case|case
name|DISTRIBUTION_CONFLICTS
case|:
if|if
condition|(
name|conflict
operator|.
name|hasDistributionConflicts
argument_list|()
condition|)
block|{
return|return
operator|new
name|ConflictCell
argument_list|(
name|SuggestionsPageContext
operator|.
name|dispNumber
argument_list|(
name|conflict
operator|.
name|countDistributionConflicts
argument_list|()
argument_list|)
argument_list|,
name|iContext
operator|.
name|createViolatedConstraints
argument_list|(
name|conflict
operator|.
name|getDistributionConflicts
argument_list|()
argument_list|,
literal|null
argument_list|)
argument_list|,
name|SolverCookie
operator|.
name|getInstance
argument_list|()
operator|.
name|isShowAllDistributionConflicts
argument_list|()
argument_list|)
return|;
block|}
else|else
block|{
return|return
literal|null
return|;
block|}
default|default:
return|return
literal|null
return|;
block|}
block|}
specifier|public
name|int
name|addRow
parameter_list|(
specifier|final
name|ClassAssignmentDetails
name|suggestion
parameter_list|)
block|{
name|List
argument_list|<
name|Widget
argument_list|>
name|widgets
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
name|ConflictColum
name|column
range|:
name|ConflictColum
operator|.
name|values
argument_list|()
control|)
block|{
name|int
name|nbrCells
init|=
name|getNbrCells
argument_list|(
name|column
argument_list|)
decl_stmt|;
for|for
control|(
name|int
name|idx
init|=
literal|0
init|;
name|idx
operator|<
name|nbrCells
condition|;
name|idx
operator|++
control|)
block|{
name|Widget
name|cell
init|=
name|getCell
argument_list|(
name|suggestion
argument_list|,
name|column
argument_list|,
name|idx
argument_list|)
decl_stmt|;
if|if
condition|(
name|cell
operator|==
literal|null
condition|)
name|cell
operator|=
operator|new
name|P
argument_list|()
expr_stmt|;
name|widgets
operator|.
name|add
argument_list|(
name|cell
argument_list|)
expr_stmt|;
block|}
block|}
name|int
name|row
init|=
name|addRow
argument_list|(
name|suggestion
argument_list|,
name|widgets
argument_list|)
decl_stmt|;
name|getRowFormatter
argument_list|()
operator|.
name|setStyleName
argument_list|(
name|row
argument_list|,
literal|"row"
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|col
init|=
literal|0
init|;
name|col
operator|<
name|getCellCount
argument_list|(
name|row
argument_list|)
condition|;
name|col
operator|++
control|)
name|getCellFormatter
argument_list|()
operator|.
name|setStyleName
argument_list|(
name|row
argument_list|,
name|col
argument_list|,
literal|"cell"
argument_list|)
expr_stmt|;
return|return
name|row
return|;
block|}
specifier|public
specifier|static
class|class
name|ConflictsComparator
implements|implements
name|Comparator
argument_list|<
name|ClassAssignmentDetails
argument_list|>
block|{
specifier|private
name|Integer
name|iFirstDay
decl_stmt|;
specifier|private
name|ConflictColum
name|iColumn
decl_stmt|;
specifier|private
name|boolean
name|iAsc
decl_stmt|;
specifier|public
name|ConflictsComparator
parameter_list|(
name|Integer
name|firstDay
parameter_list|,
name|ConflictColum
name|column
parameter_list|,
name|boolean
name|asc
parameter_list|)
block|{
name|iFirstDay
operator|=
name|firstDay
expr_stmt|;
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
name|compareByDate
parameter_list|(
name|ClassAssignmentDetails
name|s1
parameter_list|,
name|ClassAssignmentDetails
name|s2
parameter_list|)
block|{
name|TimeInfo
name|t1
init|=
name|s1
operator|.
name|getTime
argument_list|()
decl_stmt|;
name|TimeInfo
name|t2
init|=
name|s2
operator|.
name|getTime
argument_list|()
decl_stmt|;
return|return
name|compare
argument_list|(
name|t1
operator|==
literal|null
condition|?
literal|null
else|:
name|t1
operator|.
name|getDatePatternName
argument_list|()
argument_list|,
name|t2
operator|==
literal|null
condition|?
literal|null
else|:
name|t2
operator|.
name|getDatePatternName
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|int
name|compareByTime
parameter_list|(
name|ClassAssignmentDetails
name|s1
parameter_list|,
name|ClassAssignmentDetails
name|s2
parameter_list|)
block|{
name|TimeInfo
name|t1
init|=
name|s1
operator|.
name|getTime
argument_list|()
decl_stmt|;
name|TimeInfo
name|t2
init|=
name|s2
operator|.
name|getTime
argument_list|()
decl_stmt|;
name|int
name|cmp
init|=
name|compare
argument_list|(
name|t1
operator|==
literal|null
condition|?
literal|null
else|:
name|t1
operator|.
name|getDaysName
argument_list|(
name|iFirstDay
argument_list|,
operator|new
name|String
index|[]
block|{
literal|"A"
block|,
literal|"B"
block|,
literal|"C"
block|,
literal|"D"
block|,
literal|"E"
block|,
literal|"F"
block|,
literal|"G"
block|}
argument_list|)
argument_list|,
name|t2
operator|==
literal|null
condition|?
literal|null
else|:
name|t2
operator|.
name|getDaysName
argument_list|(
name|iFirstDay
argument_list|,
operator|new
name|String
index|[]
block|{
literal|"A"
block|,
literal|"B"
block|,
literal|"C"
block|,
literal|"D"
block|,
literal|"E"
block|,
literal|"F"
block|,
literal|"G"
block|}
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|cmp
operator|!=
literal|0
condition|)
return|return
name|cmp
return|;
name|cmp
operator|=
name|compare
argument_list|(
name|t1
operator|==
literal|null
condition|?
literal|null
else|:
name|t1
operator|.
name|getStartSlot
argument_list|()
argument_list|,
name|t2
operator|==
literal|null
condition|?
literal|null
else|:
name|t2
operator|.
name|getStartSlot
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|cmp
operator|!=
literal|0
condition|)
return|return
name|cmp
return|;
return|return
name|compare
argument_list|(
name|t1
operator|==
literal|null
condition|?
literal|null
else|:
name|t1
operator|.
name|getName
argument_list|(
name|iFirstDay
argument_list|,
literal|false
argument_list|,
name|CONSTANTS
argument_list|)
argument_list|,
name|t2
operator|==
literal|null
condition|?
literal|null
else|:
name|t2
operator|.
name|getName
argument_list|(
name|iFirstDay
argument_list|,
literal|false
argument_list|,
name|CONSTANTS
argument_list|)
argument_list|)
return|;
block|}
specifier|public
name|int
name|compareByDateTime
parameter_list|(
name|ClassAssignmentDetails
name|s1
parameter_list|,
name|ClassAssignmentDetails
name|s2
parameter_list|)
block|{
name|TimeInfo
name|t1
init|=
name|s1
operator|.
name|getTime
argument_list|()
decl_stmt|;
name|TimeInfo
name|t2
init|=
name|s2
operator|.
name|getTime
argument_list|()
decl_stmt|;
name|int
name|cmp
init|=
name|compare
argument_list|(
name|t1
operator|==
literal|null
condition|?
literal|null
else|:
name|t1
operator|.
name|getDatePatternName
argument_list|()
argument_list|,
name|t2
operator|==
literal|null
condition|?
literal|null
else|:
name|t2
operator|.
name|getDatePatternName
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|cmp
operator|!=
literal|0
condition|)
return|return
name|cmp
return|;
name|cmp
operator|=
name|compare
argument_list|(
name|t1
operator|==
literal|null
condition|?
literal|null
else|:
name|t1
operator|.
name|getDaysName
argument_list|(
name|iFirstDay
argument_list|,
operator|new
name|String
index|[]
block|{
literal|"A"
block|,
literal|"B"
block|,
literal|"C"
block|,
literal|"D"
block|,
literal|"E"
block|,
literal|"F"
block|,
literal|"G"
block|}
argument_list|)
argument_list|,
name|t2
operator|==
literal|null
condition|?
literal|null
else|:
name|t2
operator|.
name|getDaysName
argument_list|(
name|iFirstDay
argument_list|,
operator|new
name|String
index|[]
block|{
literal|"A"
block|,
literal|"B"
block|,
literal|"C"
block|,
literal|"D"
block|,
literal|"E"
block|,
literal|"F"
block|,
literal|"G"
block|}
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|cmp
operator|!=
literal|0
condition|)
return|return
name|cmp
return|;
name|cmp
operator|=
name|compare
argument_list|(
name|t1
operator|==
literal|null
condition|?
literal|null
else|:
name|t1
operator|.
name|getStartSlot
argument_list|()
argument_list|,
name|t2
operator|==
literal|null
condition|?
literal|null
else|:
name|t2
operator|.
name|getStartSlot
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|cmp
operator|!=
literal|0
condition|)
return|return
name|cmp
return|;
return|return
name|compare
argument_list|(
name|t1
operator|==
literal|null
condition|?
literal|null
else|:
name|t1
operator|.
name|getName
argument_list|(
name|iFirstDay
argument_list|,
literal|false
argument_list|,
name|CONSTANTS
argument_list|)
argument_list|,
name|t2
operator|==
literal|null
condition|?
literal|null
else|:
name|t2
operator|.
name|getName
argument_list|(
name|iFirstDay
argument_list|,
literal|false
argument_list|,
name|CONSTANTS
argument_list|)
argument_list|)
return|;
block|}
specifier|public
name|int
name|compareByStudentConflicts
parameter_list|(
name|ClassAssignmentDetails
name|s1
parameter_list|,
name|ClassAssignmentDetails
name|s2
parameter_list|)
block|{
return|return
name|compare
argument_list|(
name|s1
operator|.
name|countStudentConflicts
argument_list|()
argument_list|,
name|s2
operator|.
name|countStudentConflicts
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|int
name|compareByDistributionConflicts
parameter_list|(
name|ClassAssignmentDetails
name|s1
parameter_list|,
name|ClassAssignmentDetails
name|s2
parameter_list|)
block|{
return|return
name|compare
argument_list|(
name|s1
operator|.
name|countDistributionConflicts
argument_list|()
argument_list|,
name|s2
operator|.
name|countDistributionConflicts
argument_list|()
argument_list|)
return|;
block|}
specifier|protected
name|int
name|compareByColumn
parameter_list|(
name|ClassAssignmentDetails
name|c1
parameter_list|,
name|ClassAssignmentDetails
name|c2
parameter_list|)
block|{
switch|switch
condition|(
name|iColumn
condition|)
block|{
case|case
name|DATE
case|:
return|return
name|compareByDate
argument_list|(
name|c1
argument_list|,
name|c2
argument_list|)
return|;
case|case
name|TIME
case|:
return|return
name|compareByTime
argument_list|(
name|c1
argument_list|,
name|c2
argument_list|)
return|;
case|case
name|STUDENT_CONFLICTS
case|:
return|return
name|compareByStudentConflicts
argument_list|(
name|c1
argument_list|,
name|c2
argument_list|)
return|;
case|case
name|DISTRIBUTION_CONFLICTS
case|:
return|return
name|compareByDistributionConflicts
argument_list|(
name|c1
argument_list|,
name|c2
argument_list|)
return|;
default|default:
return|return
name|compareByTime
argument_list|(
name|c1
argument_list|,
name|c2
argument_list|)
return|;
block|}
block|}
specifier|public
specifier|static
name|boolean
name|isApplicable
parameter_list|(
name|ConflictColum
name|column
parameter_list|)
block|{
switch|switch
condition|(
name|column
condition|)
block|{
case|case
name|TIME
case|:
case|case
name|STUDENT_CONFLICTS
case|:
case|case
name|DISTRIBUTION_CONFLICTS
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
name|ClassAssignmentDetails
name|c1
parameter_list|,
name|ClassAssignmentDetails
name|c2
parameter_list|)
block|{
name|int
name|cmp
init|=
name|compareByColumn
argument_list|(
name|c1
argument_list|,
name|c2
argument_list|)
decl_stmt|;
if|if
condition|(
name|cmp
operator|!=
literal|0
condition|)
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
name|cmp
operator|=
name|compareByDate
argument_list|(
name|c1
argument_list|,
name|c2
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
name|compareByTime
argument_list|(
name|c1
argument_list|,
name|c2
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
name|NaturalOrderComparator
operator|.
name|compare
argument_list|(
name|s1
argument_list|,
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
operator|(
name|b1
operator|.
name|booleanValue
argument_list|()
operator|==
name|b2
operator|.
name|booleanValue
argument_list|()
operator|)
condition|?
literal|0
else|:
operator|(
name|b1
operator|.
name|booleanValue
argument_list|()
condition|?
literal|1
else|:
operator|-
literal|1
operator|)
operator|)
return|;
block|}
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
annotation|@
name|Override
specifier|public
name|Collection
argument_list|<
name|ClassAssignmentDetails
argument_list|>
name|getValue
parameter_list|()
block|{
return|return
name|getData
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|setValue
parameter_list|(
name|Collection
argument_list|<
name|ClassAssignmentDetails
argument_list|>
name|value
parameter_list|)
block|{
name|clearTable
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|boolean
name|hasStudentConflicts
init|=
literal|false
decl_stmt|,
name|hasDistributionConflicts
init|=
literal|false
decl_stmt|;
if|if
condition|(
name|value
operator|!=
literal|null
condition|)
for|for
control|(
name|ClassAssignmentDetails
name|suggestion
range|:
name|value
control|)
block|{
if|if
condition|(
name|suggestion
operator|.
name|hasStudentConflicts
argument_list|()
condition|)
name|hasStudentConflicts
operator|=
literal|true
expr_stmt|;
if|if
condition|(
name|suggestion
operator|.
name|hasDistributionConflicts
argument_list|()
condition|)
name|hasDistributionConflicts
operator|=
literal|true
expr_stmt|;
name|addRow
argument_list|(
name|suggestion
argument_list|)
expr_stmt|;
block|}
name|sort
argument_list|()
expr_stmt|;
name|setColumnVisible
argument_list|(
name|getCellIndex
argument_list|(
name|ConflictColum
operator|.
name|STUDENT_CONFLICTS
argument_list|)
argument_list|,
name|hasStudentConflicts
argument_list|)
expr_stmt|;
name|setColumnVisible
argument_list|(
name|getCellIndex
argument_list|(
name|ConflictColum
operator|.
name|DISTRIBUTION_CONFLICTS
argument_list|)
argument_list|,
name|hasDistributionConflicts
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
class|class
name|ConflictCell
extends|extends
name|P
block|{
name|P
name|iN
decl_stmt|,
name|iD
decl_stmt|,
name|iL
decl_stmt|;
specifier|public
name|ConflictCell
parameter_list|(
name|String
name|number
parameter_list|,
name|P
name|conflicts
parameter_list|,
name|boolean
name|visible
parameter_list|)
block|{
name|super
argument_list|(
literal|"conflicts"
argument_list|)
expr_stmt|;
name|iN
operator|=
operator|new
name|P
argument_list|(
literal|"number"
argument_list|)
expr_stmt|;
name|iN
operator|.
name|setHTML
argument_list|(
name|number
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|iN
argument_list|)
expr_stmt|;
name|iD
operator|=
operator|new
name|P
argument_list|(
literal|"dots"
argument_list|)
expr_stmt|;
name|iD
operator|.
name|setHTML
argument_list|(
name|CONSTANTS
operator|.
name|selectionMore
argument_list|()
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|iD
argument_list|)
expr_stmt|;
name|iL
operator|=
operator|new
name|P
argument_list|(
literal|"list"
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|iL
argument_list|)
expr_stmt|;
name|iL
operator|.
name|add
argument_list|(
name|conflicts
argument_list|)
expr_stmt|;
if|if
condition|(
name|visible
condition|)
name|showList
argument_list|()
expr_stmt|;
else|else
name|showNumber
argument_list|()
expr_stmt|;
name|iD
operator|.
name|addClickHandler
argument_list|(
operator|new
name|ClickHandler
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onClick
parameter_list|(
name|ClickEvent
name|event
parameter_list|)
block|{
name|showList
argument_list|()
expr_stmt|;
name|event
operator|.
name|preventDefault
argument_list|()
expr_stmt|;
name|event
operator|.
name|stopPropagation
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|iN
operator|.
name|addClickHandler
argument_list|(
operator|new
name|ClickHandler
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onClick
parameter_list|(
name|ClickEvent
name|event
parameter_list|)
block|{
if|if
condition|(
name|iL
operator|.
name|isVisible
argument_list|()
condition|)
name|showNumber
argument_list|()
expr_stmt|;
else|else
name|showList
argument_list|()
expr_stmt|;
name|event
operator|.
name|preventDefault
argument_list|()
expr_stmt|;
name|event
operator|.
name|stopPropagation
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|showNumber
parameter_list|()
block|{
name|iD
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|iL
operator|.
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|showList
parameter_list|()
block|{
name|iD
operator|.
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|iL
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

