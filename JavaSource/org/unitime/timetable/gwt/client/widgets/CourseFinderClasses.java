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
name|widgets
package|;
end_package

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
name|GwtAriaMessages
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
name|StudentSectioningConstants
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
name|StudentSectioningMessages
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
name|ClassAssignmentInterface
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
name|ClassAssignmentInterface
operator|.
name|ClassAssignment
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
name|rpc
operator|.
name|AsyncCallback
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

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|CourseFinderClasses
extends|extends
name|WebTable
implements|implements
name|CourseFinder
operator|.
name|CourseFinderCourseDetails
argument_list|<
name|Collection
argument_list|<
name|ClassAssignmentInterface
operator|.
name|ClassAssignment
argument_list|>
argument_list|>
block|{
specifier|protected
specifier|static
specifier|final
name|StudentSectioningMessages
name|MESSAGES
init|=
name|GWT
operator|.
name|create
argument_list|(
name|StudentSectioningMessages
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|protected
specifier|static
specifier|final
name|StudentSectioningConstants
name|CONSTANTS
init|=
name|GWT
operator|.
name|create
argument_list|(
name|StudentSectioningConstants
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|protected
specifier|static
specifier|final
name|GwtAriaMessages
name|ARIA
init|=
name|GWT
operator|.
name|create
argument_list|(
name|GwtAriaMessages
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|String
name|iValue
init|=
literal|null
decl_stmt|;
specifier|private
name|DataProvider
argument_list|<
name|String
argument_list|,
name|Collection
argument_list|<
name|ClassAssignment
argument_list|>
argument_list|>
name|iDataProvider
init|=
literal|null
decl_stmt|;
specifier|public
name|CourseFinderClasses
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
name|setHeader
argument_list|(
operator|new
name|WebTable
operator|.
name|Row
argument_list|(
operator|new
name|WebTable
operator|.
name|Cell
argument_list|(
name|MESSAGES
operator|.
name|colSubpart
argument_list|()
argument_list|,
literal|1
argument_list|,
literal|"7%"
argument_list|)
argument_list|,
operator|new
name|WebTable
operator|.
name|Cell
argument_list|(
name|MESSAGES
operator|.
name|colClass
argument_list|()
argument_list|,
literal|1
argument_list|,
literal|"10%"
argument_list|)
argument_list|,
operator|new
name|WebTable
operator|.
name|Cell
argument_list|(
name|MESSAGES
operator|.
name|colLimit
argument_list|()
argument_list|,
literal|1
argument_list|,
literal|"7%"
argument_list|)
argument_list|,
operator|new
name|WebTable
operator|.
name|Cell
argument_list|(
name|MESSAGES
operator|.
name|colDays
argument_list|()
argument_list|,
literal|1
argument_list|,
literal|"7%"
argument_list|)
argument_list|,
operator|new
name|WebTable
operator|.
name|Cell
argument_list|(
name|MESSAGES
operator|.
name|colStart
argument_list|()
argument_list|,
literal|1
argument_list|,
literal|"7%"
argument_list|)
argument_list|,
operator|new
name|WebTable
operator|.
name|Cell
argument_list|(
name|MESSAGES
operator|.
name|colEnd
argument_list|()
argument_list|,
literal|1
argument_list|,
literal|"7%"
argument_list|)
argument_list|,
operator|new
name|WebTable
operator|.
name|Cell
argument_list|(
name|MESSAGES
operator|.
name|colDate
argument_list|()
argument_list|,
literal|1
argument_list|,
literal|"12%"
argument_list|)
argument_list|,
operator|new
name|WebTable
operator|.
name|Cell
argument_list|(
name|MESSAGES
operator|.
name|colRoom
argument_list|()
argument_list|,
literal|1
argument_list|,
literal|"12%"
argument_list|)
argument_list|,
operator|new
name|WebTable
operator|.
name|Cell
argument_list|(
name|MESSAGES
operator|.
name|colInstructor
argument_list|()
argument_list|,
literal|1
argument_list|,
literal|"15%"
argument_list|)
argument_list|,
operator|new
name|WebTable
operator|.
name|Cell
argument_list|(
name|MESSAGES
operator|.
name|colParent
argument_list|()
argument_list|,
literal|1
argument_list|,
literal|"10%"
argument_list|)
argument_list|,
operator|new
name|WebTable
operator|.
name|Cell
argument_list|(
name|MESSAGES
operator|.
name|colHighDemand
argument_list|()
argument_list|,
literal|1
argument_list|,
literal|"3%"
argument_list|)
argument_list|,
operator|new
name|WebTable
operator|.
name|Cell
argument_list|(
name|MESSAGES
operator|.
name|colNoteIcon
argument_list|()
argument_list|,
literal|1
argument_list|,
literal|"3%"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|setEmptyMessage
argument_list|(
name|MESSAGES
operator|.
name|courseSelectionNoCourseSelected
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|Widget
name|asWidget
parameter_list|()
block|{
return|return
name|this
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|setValue
parameter_list|(
name|String
name|value
parameter_list|)
block|{
if|if
condition|(
name|value
operator|==
literal|null
operator|||
name|value
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|iValue
operator|=
name|value
expr_stmt|;
name|setEmptyMessage
argument_list|(
name|MESSAGES
operator|.
name|courseSelectionNoCourseSelected
argument_list|()
argument_list|)
expr_stmt|;
name|clearData
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
operator|!
name|value
operator|.
name|equals
argument_list|(
name|iValue
argument_list|)
condition|)
block|{
name|iValue
operator|=
name|value
expr_stmt|;
name|setEmptyMessage
argument_list|(
name|MESSAGES
operator|.
name|courseSelectionLoadingClasses
argument_list|()
argument_list|)
expr_stmt|;
name|clearData
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|iDataProvider
operator|.
name|getData
argument_list|(
name|value
argument_list|,
operator|new
name|AsyncCallback
argument_list|<
name|Collection
argument_list|<
name|ClassAssignmentInterface
operator|.
name|ClassAssignment
argument_list|>
argument_list|>
argument_list|()
block|{
specifier|public
name|void
name|onFailure
parameter_list|(
name|Throwable
name|caught
parameter_list|)
block|{
name|setEmptyMessage
argument_list|(
name|caught
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|onSuccess
parameter_list|(
name|Collection
argument_list|<
name|ClassAssignmentInterface
operator|.
name|ClassAssignment
argument_list|>
name|result
parameter_list|)
block|{
if|if
condition|(
operator|!
name|result
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|WebTable
operator|.
name|Row
index|[]
name|rows
init|=
operator|new
name|WebTable
operator|.
name|Row
index|[
name|result
operator|.
name|size
argument_list|()
index|]
decl_stmt|;
name|int
name|idx
init|=
literal|0
decl_stmt|;
name|Long
name|lastSubpartId
init|=
literal|null
decl_stmt|;
for|for
control|(
name|ClassAssignmentInterface
operator|.
name|ClassAssignment
name|clazz
range|:
name|result
control|)
block|{
name|WebTable
operator|.
name|Row
name|row
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|clazz
operator|.
name|isAssigned
argument_list|()
condition|)
block|{
name|row
operator|=
operator|new
name|WebTable
operator|.
name|Row
argument_list|(
operator|new
name|WebTable
operator|.
name|Cell
argument_list|(
name|clazz
operator|.
name|getSubpart
argument_list|()
argument_list|)
argument_list|,
operator|new
name|WebTable
operator|.
name|Cell
argument_list|(
name|clazz
operator|.
name|getSection
argument_list|()
argument_list|)
argument_list|,
operator|new
name|WebTable
operator|.
name|Cell
argument_list|(
name|clazz
operator|.
name|getLimitString
argument_list|()
argument_list|)
argument_list|,
operator|new
name|WebTable
operator|.
name|Cell
argument_list|(
name|clazz
operator|.
name|getDaysString
argument_list|(
name|CONSTANTS
operator|.
name|shortDays
argument_list|()
argument_list|)
argument_list|)
argument_list|,
operator|new
name|WebTable
operator|.
name|Cell
argument_list|(
name|clazz
operator|.
name|getStartString
argument_list|(
name|CONSTANTS
operator|.
name|useAmPm
argument_list|()
argument_list|)
argument_list|)
argument_list|,
operator|new
name|WebTable
operator|.
name|Cell
argument_list|(
name|clazz
operator|.
name|getEndString
argument_list|(
name|CONSTANTS
operator|.
name|useAmPm
argument_list|()
argument_list|)
argument_list|)
argument_list|,
operator|new
name|WebTable
operator|.
name|Cell
argument_list|(
name|clazz
operator|.
name|getDatePattern
argument_list|()
argument_list|)
argument_list|,
operator|new
name|WebTable
operator|.
name|Cell
argument_list|(
name|clazz
operator|.
name|getRooms
argument_list|(
literal|", "
argument_list|)
argument_list|)
argument_list|,
operator|new
name|WebTable
operator|.
name|Cell
argument_list|(
name|clazz
operator|.
name|getInstructors
argument_list|(
literal|", "
argument_list|)
argument_list|)
argument_list|,
operator|new
name|WebTable
operator|.
name|Cell
argument_list|(
name|clazz
operator|.
name|getParentSection
argument_list|()
argument_list|)
argument_list|,
operator|(
name|clazz
operator|.
name|isSaved
argument_list|()
condition|?
operator|new
name|WebTable
operator|.
name|IconCell
argument_list|(
name|RESOURCES
operator|.
name|saved
argument_list|()
argument_list|,
name|MESSAGES
operator|.
name|saved
argument_list|(
name|clazz
operator|.
name|getSubpart
argument_list|()
operator|+
literal|" "
operator|+
name|clazz
operator|.
name|getSection
argument_list|()
argument_list|)
argument_list|,
literal|null
argument_list|)
else|:
name|clazz
operator|.
name|isCancelled
argument_list|()
condition|?
operator|new
name|WebTable
operator|.
name|IconCell
argument_list|(
name|RESOURCES
operator|.
name|cancelled
argument_list|()
argument_list|,
name|MESSAGES
operator|.
name|classCancelled
argument_list|(
name|clazz
operator|.
name|getSubpart
argument_list|()
operator|+
literal|" "
operator|+
name|clazz
operator|.
name|getSection
argument_list|()
argument_list|)
argument_list|,
literal|null
argument_list|)
else|:
name|clazz
operator|.
name|isOfHighDemand
argument_list|()
condition|?
operator|new
name|WebTable
operator|.
name|IconCell
argument_list|(
name|RESOURCES
operator|.
name|highDemand
argument_list|()
argument_list|,
name|MESSAGES
operator|.
name|highDemand
argument_list|(
name|clazz
operator|.
name|getExpected
argument_list|()
argument_list|,
name|clazz
operator|.
name|getAvailableLimit
argument_list|()
argument_list|)
argument_list|,
literal|null
argument_list|)
else|:
operator|new
name|WebTable
operator|.
name|Cell
argument_list|(
literal|""
argument_list|)
operator|)
argument_list|,
name|clazz
operator|.
name|hasNote
argument_list|()
condition|?
operator|new
name|WebTable
operator|.
name|IconCell
argument_list|(
name|RESOURCES
operator|.
name|note
argument_list|()
argument_list|,
name|clazz
operator|.
name|getNote
argument_list|()
argument_list|,
literal|""
argument_list|)
else|:
operator|new
name|WebTable
operator|.
name|Cell
argument_list|(
literal|""
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|row
operator|=
operator|new
name|WebTable
operator|.
name|Row
argument_list|(
operator|new
name|WebTable
operator|.
name|Cell
argument_list|(
name|clazz
operator|.
name|getSubpart
argument_list|()
argument_list|)
argument_list|,
operator|new
name|WebTable
operator|.
name|Cell
argument_list|(
name|clazz
operator|.
name|getSection
argument_list|()
argument_list|)
argument_list|,
operator|new
name|WebTable
operator|.
name|Cell
argument_list|(
name|clazz
operator|.
name|getLimitString
argument_list|()
argument_list|)
argument_list|,
operator|new
name|WebTable
operator|.
name|Cell
argument_list|(
name|MESSAGES
operator|.
name|arrangeHours
argument_list|()
argument_list|,
literal|3
argument_list|,
literal|null
argument_list|)
argument_list|,
operator|new
name|WebTable
operator|.
name|Cell
argument_list|(
name|clazz
operator|.
name|hasDatePattern
argument_list|()
condition|?
name|clazz
operator|.
name|getDatePattern
argument_list|()
else|:
literal|""
argument_list|)
argument_list|,
operator|new
name|WebTable
operator|.
name|Cell
argument_list|(
name|clazz
operator|.
name|getRooms
argument_list|(
literal|", "
argument_list|)
argument_list|)
argument_list|,
operator|new
name|WebTable
operator|.
name|Cell
argument_list|(
name|clazz
operator|.
name|getInstructors
argument_list|(
literal|", "
argument_list|)
argument_list|)
argument_list|,
operator|new
name|WebTable
operator|.
name|Cell
argument_list|(
name|clazz
operator|.
name|getParentSection
argument_list|()
argument_list|)
argument_list|,
operator|(
name|clazz
operator|.
name|isSaved
argument_list|()
condition|?
operator|new
name|WebTable
operator|.
name|IconCell
argument_list|(
name|RESOURCES
operator|.
name|saved
argument_list|()
argument_list|,
name|MESSAGES
operator|.
name|saved
argument_list|(
name|clazz
operator|.
name|getSubpart
argument_list|()
operator|+
literal|" "
operator|+
name|clazz
operator|.
name|getSection
argument_list|()
argument_list|)
argument_list|,
literal|null
argument_list|)
else|:
name|clazz
operator|.
name|isCancelled
argument_list|()
condition|?
operator|new
name|WebTable
operator|.
name|IconCell
argument_list|(
name|RESOURCES
operator|.
name|cancelled
argument_list|()
argument_list|,
name|MESSAGES
operator|.
name|classCancelled
argument_list|(
name|clazz
operator|.
name|getSubpart
argument_list|()
operator|+
literal|" "
operator|+
name|clazz
operator|.
name|getSection
argument_list|()
argument_list|)
argument_list|,
literal|null
argument_list|)
else|:
name|clazz
operator|.
name|isOfHighDemand
argument_list|()
condition|?
operator|new
name|WebTable
operator|.
name|IconCell
argument_list|(
name|RESOURCES
operator|.
name|highDemand
argument_list|()
argument_list|,
name|MESSAGES
operator|.
name|highDemand
argument_list|(
name|clazz
operator|.
name|getExpected
argument_list|()
argument_list|,
name|clazz
operator|.
name|getAvailableLimit
argument_list|()
argument_list|)
argument_list|,
literal|null
argument_list|)
else|:
operator|new
name|WebTable
operator|.
name|Cell
argument_list|(
literal|""
argument_list|)
operator|)
argument_list|,
name|clazz
operator|.
name|hasNote
argument_list|()
condition|?
operator|new
name|WebTable
operator|.
name|IconCell
argument_list|(
name|RESOURCES
operator|.
name|note
argument_list|()
argument_list|,
name|clazz
operator|.
name|getNote
argument_list|()
argument_list|,
literal|""
argument_list|)
else|:
operator|new
name|WebTable
operator|.
name|Cell
argument_list|(
literal|""
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|row
operator|.
name|setId
argument_list|(
name|clazz
operator|.
name|getClassId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|String
name|styleName
init|=
literal|""
decl_stmt|;
if|if
condition|(
name|lastSubpartId
operator|!=
literal|null
operator|&&
operator|!
name|clazz
operator|.
name|getSubpartId
argument_list|()
operator|.
name|equals
argument_list|(
name|lastSubpartId
argument_list|)
condition|)
name|styleName
operator|+=
literal|"top-border-dashed"
expr_stmt|;
if|if
condition|(
name|clazz
operator|.
name|isCancelled
argument_list|()
operator|||
operator|(
operator|!
name|clazz
operator|.
name|isSaved
argument_list|()
operator|&&
operator|!
name|clazz
operator|.
name|isAvailable
argument_list|()
operator|)
condition|)
name|styleName
operator|+=
literal|" text-gray"
expr_stmt|;
for|for
control|(
name|WebTable
operator|.
name|Cell
name|cell
range|:
name|row
operator|.
name|getCells
argument_list|()
control|)
name|cell
operator|.
name|setStyleName
argument_list|(
name|styleName
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
name|rows
index|[
name|idx
operator|++
index|]
operator|=
name|row
expr_stmt|;
name|lastSubpartId
operator|=
name|clazz
operator|.
name|getSubpartId
argument_list|()
expr_stmt|;
if|if
condition|(
operator|!
name|clazz
operator|.
name|isSaved
argument_list|()
operator|&&
operator|!
name|clazz
operator|.
name|isAvailable
argument_list|()
condition|)
name|row
operator|.
name|setAriaLabel
argument_list|(
name|ARIA
operator|.
name|courseFinderClassNotAvailable
argument_list|(
name|MESSAGES
operator|.
name|clazz
argument_list|(
name|clazz
operator|.
name|getSubject
argument_list|()
argument_list|,
name|clazz
operator|.
name|getCourseNbr
argument_list|()
argument_list|,
name|clazz
operator|.
name|getSubpart
argument_list|()
argument_list|,
name|clazz
operator|.
name|getSection
argument_list|()
argument_list|)
argument_list|,
name|clazz
operator|.
name|isAssigned
argument_list|()
condition|?
name|clazz
operator|.
name|getTimeStringAria
argument_list|(
name|CONSTANTS
operator|.
name|longDays
argument_list|()
argument_list|,
name|CONSTANTS
operator|.
name|useAmPm
argument_list|()
argument_list|,
name|ARIA
operator|.
name|arrangeHours
argument_list|()
argument_list|)
operator|+
literal|" "
operator|+
name|clazz
operator|.
name|getRooms
argument_list|(
literal|","
argument_list|)
else|:
name|ARIA
operator|.
name|arrangeHours
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
else|else
name|row
operator|.
name|setAriaLabel
argument_list|(
name|ARIA
operator|.
name|courseFinderClassAvailable
argument_list|(
name|MESSAGES
operator|.
name|clazz
argument_list|(
name|clazz
operator|.
name|getSubject
argument_list|()
argument_list|,
name|clazz
operator|.
name|getCourseNbr
argument_list|()
argument_list|,
name|clazz
operator|.
name|getSubpart
argument_list|()
argument_list|,
name|clazz
operator|.
name|getSection
argument_list|()
argument_list|)
argument_list|,
name|clazz
operator|.
name|isAssigned
argument_list|()
condition|?
name|clazz
operator|.
name|getTimeStringAria
argument_list|(
name|CONSTANTS
operator|.
name|longDays
argument_list|()
argument_list|,
name|CONSTANTS
operator|.
name|useAmPm
argument_list|()
argument_list|,
name|ARIA
operator|.
name|arrangeHours
argument_list|()
argument_list|)
operator|+
literal|" "
operator|+
name|clazz
operator|.
name|getRooms
argument_list|(
literal|","
argument_list|)
else|:
name|ARIA
operator|.
name|arrangeHours
argument_list|()
argument_list|,
name|clazz
operator|.
name|getLimitString
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|setData
argument_list|(
name|rows
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|setEmptyMessage
argument_list|(
name|MESSAGES
operator|.
name|courseSelectionNoClasses
argument_list|(
name|iValue
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|String
name|getValue
parameter_list|()
block|{
return|return
name|iValue
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|setDataProvider
parameter_list|(
name|DataProvider
argument_list|<
name|String
argument_list|,
name|Collection
argument_list|<
name|ClassAssignment
argument_list|>
argument_list|>
name|provider
parameter_list|)
block|{
name|iDataProvider
operator|=
name|provider
expr_stmt|;
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
name|courseSelectionClasses
argument_list|()
return|;
block|}
block|}
end_class

end_unit

