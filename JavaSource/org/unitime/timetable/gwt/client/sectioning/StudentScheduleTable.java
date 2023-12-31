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
name|sectioning
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
name|Iterator
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
name|UniTimeNotifications
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
name|LoadingWidget
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
name|SimpleForm
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
name|UniTimeHeaderPanel
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
name|WebTable
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
name|resources
operator|.
name|StudentSectioningResources
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
name|services
operator|.
name|SectioningService
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
name|services
operator|.
name|SectioningServiceAsync
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
name|CourseAssignment
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
name|Student
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
name|dom
operator|.
name|client
operator|.
name|Style
operator|.
name|Unit
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
name|event
operator|.
name|logical
operator|.
name|shared
operator|.
name|ValueChangeEvent
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
name|logical
operator|.
name|shared
operator|.
name|ValueChangeHandler
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
name|Composite
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
name|RootPanel
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|StudentScheduleTable
extends|extends
name|Composite
block|{
specifier|public
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
specifier|public
specifier|static
specifier|final
name|StudentSectioningResources
name|RESOURCES
init|=
name|GWT
operator|.
name|create
argument_list|(
name|StudentSectioningResources
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|public
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
name|SectioningServiceAsync
name|sSectioningService
init|=
name|GWT
operator|.
name|create
argument_list|(
name|SectioningService
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|boolean
name|iOnline
decl_stmt|;
specifier|private
name|SimpleForm
name|iPanel
decl_stmt|;
specifier|private
name|UniTimeHeaderPanel
name|iHeader
decl_stmt|;
specifier|private
name|WebTable
name|iTable
decl_stmt|;
specifier|private
name|ClassAssignmentInterface
operator|.
name|Student
name|iStudent
decl_stmt|;
specifier|private
name|boolean
name|iShowTeachingAssignments
decl_stmt|;
specifier|public
name|StudentScheduleTable
parameter_list|(
specifier|final
name|boolean
name|showHeader
parameter_list|,
name|boolean
name|online
parameter_list|,
name|boolean
name|showTeachingAssignments
parameter_list|)
block|{
name|iOnline
operator|=
name|online
expr_stmt|;
name|iPanel
operator|=
operator|new
name|SimpleForm
argument_list|()
expr_stmt|;
name|iShowTeachingAssignments
operator|=
name|showTeachingAssignments
expr_stmt|;
name|iHeader
operator|=
operator|new
name|UniTimeHeaderPanel
argument_list|(
name|showHeader
condition|?
name|MESSAGES
operator|.
name|enrollmentsTable
argument_list|()
else|:
literal|"&nbsp;"
argument_list|)
expr_stmt|;
name|iHeader
operator|.
name|addCollapsibleHandler
argument_list|(
operator|new
name|ValueChangeHandler
argument_list|<
name|Boolean
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onValueChange
parameter_list|(
name|ValueChangeEvent
argument_list|<
name|Boolean
argument_list|>
name|event
parameter_list|)
block|{
name|SectioningCookie
operator|.
name|getInstance
argument_list|()
operator|.
name|setEnrollmentCoursesDetails
argument_list|(
name|event
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|iTable
operator|.
name|getTable
argument_list|()
operator|.
name|getRowCount
argument_list|()
operator|>
literal|2
condition|)
block|{
for|for
control|(
name|int
name|row
init|=
literal|1
init|;
name|row
operator|<
name|iTable
operator|.
name|getTable
argument_list|()
operator|.
name|getRowCount
argument_list|()
condition|;
name|row
operator|++
control|)
block|{
name|iTable
operator|.
name|getTable
argument_list|()
operator|.
name|getRowFormatter
argument_list|()
operator|.
name|setVisible
argument_list|(
name|row
argument_list|,
name|event
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|iTable
operator|.
name|getTable
argument_list|()
operator|.
name|getRowCount
argument_list|()
operator|==
literal|0
condition|)
name|refresh
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|iHeader
operator|.
name|setCollapsible
argument_list|(
name|showHeader
condition|?
name|SectioningCookie
operator|.
name|getInstance
argument_list|()
operator|.
name|getEnrollmentCoursesDetails
argument_list|()
else|:
literal|null
argument_list|)
expr_stmt|;
name|iHeader
operator|.
name|setTitleStyleName
argument_list|(
literal|"unitime3-HeaderTitle"
argument_list|)
expr_stmt|;
name|iPanel
operator|.
name|removeStyleName
argument_list|(
literal|"unitime-NotPrintableBottomLine"
argument_list|)
expr_stmt|;
if|if
condition|(
name|showHeader
condition|)
block|{
name|iPanel
operator|.
name|addHeaderRow
argument_list|(
name|iHeader
argument_list|)
expr_stmt|;
name|iHeader
operator|.
name|getElement
argument_list|()
operator|.
name|getStyle
argument_list|()
operator|.
name|setMarginTop
argument_list|(
literal|10
argument_list|,
name|Unit
operator|.
name|PX
argument_list|)
expr_stmt|;
block|}
name|iTable
operator|=
operator|new
name|WebTable
argument_list|()
expr_stmt|;
name|iTable
operator|.
name|addStyleName
argument_list|(
literal|"unitime-Enrollments"
argument_list|)
expr_stmt|;
name|iPanel
operator|.
name|addRow
argument_list|(
name|iTable
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|showHeader
condition|)
name|iPanel
operator|.
name|addBottomRow
argument_list|(
name|iHeader
argument_list|)
expr_stmt|;
name|iHeader
operator|.
name|setEnabled
argument_list|(
literal|"approve"
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|iHeader
operator|.
name|setEnabled
argument_list|(
literal|"reject"
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|iHeader
operator|.
name|addButton
argument_list|(
literal|"registration"
argument_list|,
name|MESSAGES
operator|.
name|buttonRegistration
argument_list|()
argument_list|,
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
name|e
parameter_list|)
block|{
name|EnrollmentTable
operator|.
name|showCourseRequests
argument_list|(
name|iStudent
argument_list|,
name|iOnline
argument_list|,
operator|new
name|AsyncCallback
argument_list|<
name|Boolean
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onFailure
parameter_list|(
name|Throwable
name|caught
parameter_list|)
block|{
name|UniTimeNotifications
operator|.
name|error
argument_list|(
name|caught
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|onSuccess
parameter_list|(
name|Boolean
name|result
parameter_list|)
block|{
block|}
block|}
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|iHeader
operator|.
name|addButton
argument_list|(
literal|"assistant"
argument_list|,
name|MESSAGES
operator|.
name|buttonAssistant
argument_list|()
argument_list|,
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
name|e
parameter_list|)
block|{
name|EnrollmentTable
operator|.
name|showStudentAssistant
argument_list|(
name|iStudent
argument_list|,
name|iOnline
argument_list|,
operator|new
name|AsyncCallback
argument_list|<
name|Boolean
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onFailure
parameter_list|(
name|Throwable
name|caught
parameter_list|)
block|{
name|UniTimeNotifications
operator|.
name|error
argument_list|(
name|caught
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|onSuccess
parameter_list|(
name|Boolean
name|result
parameter_list|)
block|{
block|}
block|}
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
if|if
condition|(
name|iOnline
condition|)
block|{
name|iHeader
operator|.
name|addButton
argument_list|(
literal|"log"
argument_list|,
name|MESSAGES
operator|.
name|buttonChangeLog
argument_list|()
argument_list|,
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
name|LoadingWidget
operator|.
name|getInstance
argument_list|()
operator|.
name|show
argument_list|(
name|MESSAGES
operator|.
name|loadingChangeLog
argument_list|(
name|iStudent
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|EnrollmentTable
operator|.
name|showChangeLog
argument_list|(
name|iStudent
argument_list|,
operator|new
name|AsyncCallback
argument_list|<
name|Boolean
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onFailure
parameter_list|(
name|Throwable
name|caught
parameter_list|)
block|{
name|LoadingWidget
operator|.
name|getInstance
argument_list|()
operator|.
name|hide
argument_list|()
expr_stmt|;
name|UniTimeNotifications
operator|.
name|error
argument_list|(
name|caught
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|onSuccess
parameter_list|(
name|Boolean
name|result
parameter_list|)
block|{
name|LoadingWidget
operator|.
name|getInstance
argument_list|()
operator|.
name|hide
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
name|initWidget
argument_list|(
name|iPanel
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|insert
parameter_list|(
specifier|final
name|RootPanel
name|panel
parameter_list|)
block|{
name|String
name|studentId
init|=
name|panel
operator|.
name|getElement
argument_list|()
operator|.
name|getInnerText
argument_list|()
operator|.
name|trim
argument_list|()
decl_stmt|;
name|panel
operator|.
name|getElement
argument_list|()
operator|.
name|setInnerText
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|panel
operator|.
name|add
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|sSectioningService
operator|.
name|lookupStudent
argument_list|(
name|iOnline
argument_list|,
name|studentId
argument_list|,
operator|new
name|AsyncCallback
argument_list|<
name|ClassAssignmentInterface
operator|.
name|Student
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onSuccess
parameter_list|(
name|Student
name|result
parameter_list|)
block|{
if|if
condition|(
name|result
operator|!=
literal|null
condition|)
block|{
name|panel
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|setStudent
argument_list|(
name|result
argument_list|)
expr_stmt|;
if|if
condition|(
name|SectioningCookie
operator|.
name|getInstance
argument_list|()
operator|.
name|getEnrollmentCoursesDetails
argument_list|()
condition|)
block|{
name|refresh
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|clear
argument_list|()
expr_stmt|;
name|iHeader
operator|.
name|clearMessage
argument_list|()
expr_stmt|;
name|iHeader
operator|.
name|setCollapsible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|onFailure
parameter_list|(
name|Throwable
name|caught
parameter_list|)
block|{
block|}
block|}
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|setStudent
parameter_list|(
name|ClassAssignmentInterface
operator|.
name|Student
name|student
parameter_list|)
block|{
name|iStudent
operator|=
name|student
expr_stmt|;
name|iHeader
operator|.
name|setEnabled
argument_list|(
literal|"registration"
argument_list|,
name|iStudent
operator|!=
literal|null
operator|&&
name|iStudent
operator|.
name|isCanRegister
argument_list|()
argument_list|)
expr_stmt|;
name|iHeader
operator|.
name|setEnabled
argument_list|(
literal|"assistant"
argument_list|,
name|iStudent
operator|!=
literal|null
operator|&&
name|iStudent
operator|.
name|isCanUseAssistant
argument_list|()
argument_list|)
expr_stmt|;
name|iHeader
operator|.
name|setEnabled
argument_list|(
literal|"log"
argument_list|,
name|iStudent
operator|!=
literal|null
operator|&&
name|iStudent
operator|.
name|isCanUseAssistant
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|clear
parameter_list|()
block|{
for|for
control|(
name|int
name|row
init|=
name|iTable
operator|.
name|getTable
argument_list|()
operator|.
name|getRowCount
argument_list|()
operator|-
literal|1
init|;
name|row
operator|>=
literal|0
condition|;
name|row
operator|--
control|)
block|{
name|iTable
operator|.
name|getTable
argument_list|()
operator|.
name|removeRow
argument_list|(
name|row
argument_list|)
expr_stmt|;
block|}
name|iTable
operator|.
name|getTable
argument_list|()
operator|.
name|clear
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|refresh
parameter_list|()
block|{
name|sSectioningService
operator|.
name|getEnrollment
argument_list|(
name|iOnline
argument_list|,
name|iStudent
operator|.
name|getId
argument_list|()
argument_list|,
operator|new
name|AsyncCallback
argument_list|<
name|ClassAssignmentInterface
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onFailure
parameter_list|(
name|Throwable
name|caught
parameter_list|)
block|{
name|iHeader
operator|.
name|setErrorMessage
argument_list|(
name|MESSAGES
operator|.
name|failedToLoadEnrollments
argument_list|(
name|caught
operator|.
name|getMessage
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|onSuccess
parameter_list|(
name|ClassAssignmentInterface
name|result
parameter_list|)
block|{
name|populate
argument_list|(
name|result
operator|.
name|getCourseAssignments
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|populate
parameter_list|(
name|Collection
argument_list|<
name|CourseAssignment
argument_list|>
name|data
parameter_list|)
block|{
name|clear
argument_list|()
expr_stmt|;
name|iTable
operator|.
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
name|colSubject
argument_list|()
argument_list|,
literal|1
argument_list|,
literal|"75px"
argument_list|)
argument_list|,
operator|new
name|WebTable
operator|.
name|Cell
argument_list|(
name|MESSAGES
operator|.
name|colCourse
argument_list|()
argument_list|,
literal|1
argument_list|,
literal|"75px"
argument_list|)
argument_list|,
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
literal|"50px"
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
literal|"75px"
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
literal|"60px"
argument_list|)
argument_list|,
operator|new
name|WebTable
operator|.
name|Cell
argument_list|(
name|MESSAGES
operator|.
name|colTime
argument_list|()
argument_list|,
literal|1
argument_list|,
literal|"150px"
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
literal|"75px"
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
literal|"100px"
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
literal|"100px"
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
literal|"75px"
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
literal|"10px"
argument_list|)
argument_list|,
operator|new
name|WebTable
operator|.
name|Cell
argument_list|(
name|MESSAGES
operator|.
name|colCredit
argument_list|()
argument_list|,
literal|1
argument_list|,
literal|"75px"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|iTable
operator|.
name|setEmptyMessage
argument_list|(
name|MESSAGES
operator|.
name|emptySchedule
argument_list|()
argument_list|)
expr_stmt|;
name|iHeader
operator|.
name|clearMessage
argument_list|()
expr_stmt|;
name|ArrayList
argument_list|<
name|WebTable
operator|.
name|Row
argument_list|>
name|rows
init|=
operator|new
name|ArrayList
argument_list|<
name|WebTable
operator|.
name|Row
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|ClassAssignmentInterface
operator|.
name|CourseAssignment
name|course
range|:
name|data
control|)
block|{
if|if
condition|(
name|course
operator|.
name|isAssigned
argument_list|()
condition|)
block|{
name|boolean
name|firstClazz
init|=
literal|true
decl_stmt|;
for|for
control|(
name|ClassAssignmentInterface
operator|.
name|ClassAssignment
name|clazz
range|:
name|course
operator|.
name|getClassAssignments
argument_list|()
control|)
block|{
name|String
name|style
init|=
operator|(
name|firstClazz
operator|&&
operator|!
name|rows
operator|.
name|isEmpty
argument_list|()
condition|?
literal|"top-border-dashed"
else|:
literal|""
operator|)
decl_stmt|;
if|if
condition|(
name|clazz
operator|.
name|isTeachingAssignment
argument_list|()
condition|)
block|{
if|if
condition|(
operator|!
name|iShowTeachingAssignments
condition|)
continue|continue;
name|style
operator|+=
operator|(
name|clazz
operator|.
name|isInstructing
argument_list|()
condition|?
literal|" text-steelblue"
else|:
literal|" text-steelblue-italic"
operator|)
expr_stmt|;
block|}
specifier|final
name|WebTable
operator|.
name|Row
name|row
init|=
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
name|firstClazz
condition|?
name|course
operator|.
name|isFreeTime
argument_list|()
condition|?
name|MESSAGES
operator|.
name|freeTimeSubject
argument_list|()
else|:
name|course
operator|.
name|getSubject
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
name|firstClazz
condition|?
name|course
operator|.
name|isFreeTime
argument_list|()
condition|?
name|MESSAGES
operator|.
name|freeTimeCourse
argument_list|()
else|:
name|course
operator|.
name|getCourseNbr
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
operator|+
literal|" "
operator|+
name|clazz
operator|.
name|getStartString
argument_list|(
name|CONSTANTS
operator|.
name|useAmPm
argument_list|()
argument_list|)
operator|+
literal|" - "
operator|+
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
operator|(
name|clazz
operator|.
name|hasDistanceConflict
argument_list|()
condition|?
operator|new
name|WebTable
operator|.
name|RoomCell
argument_list|(
name|RESOURCES
operator|.
name|distantConflict
argument_list|()
argument_list|,
name|MESSAGES
operator|.
name|backToBackDistance
argument_list|(
name|clazz
operator|.
name|getBackToBackRooms
argument_list|()
argument_list|,
name|clazz
operator|.
name|getBackToBackDistance
argument_list|()
argument_list|)
argument_list|,
name|clazz
operator|.
name|getRooms
argument_list|()
argument_list|,
literal|", "
argument_list|)
else|:
operator|new
name|WebTable
operator|.
name|RoomCell
argument_list|(
name|clazz
operator|.
name|getRooms
argument_list|()
argument_list|,
literal|", "
argument_list|)
operator|)
argument_list|,
operator|new
name|WebTable
operator|.
name|InstructorCell
argument_list|(
name|clazz
operator|.
name|getInstructors
argument_list|()
argument_list|,
name|clazz
operator|.
name|getInstructorEmails
argument_list|()
argument_list|,
literal|", "
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
argument_list|,
operator|new
name|WebTable
operator|.
name|AbbvTextCell
argument_list|(
name|clazz
operator|.
name|getCredit
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
name|rows
operator|.
name|add
argument_list|(
name|row
argument_list|)
expr_stmt|;
if|if
condition|(
name|clazz
operator|.
name|isTeachingAssignment
argument_list|()
condition|)
name|row
operator|.
name|setStyleName
argument_list|(
literal|"teaching-assignment"
argument_list|)
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
name|style
argument_list|)
expr_stmt|;
name|firstClazz
operator|=
literal|false
expr_stmt|;
block|}
block|}
else|else
block|{
name|String
name|style
init|=
literal|"text-red"
operator|+
operator|(
operator|!
name|rows
operator|.
name|isEmpty
argument_list|()
condition|?
literal|" top-border-dashed"
else|:
literal|""
operator|)
decl_stmt|;
name|WebTable
operator|.
name|Row
name|row
init|=
literal|null
decl_stmt|;
name|String
name|unassignedMessage
init|=
name|MESSAGES
operator|.
name|courseNotAssigned
argument_list|()
decl_stmt|;
if|if
condition|(
name|course
operator|.
name|hasEnrollmentMessage
argument_list|()
condition|)
name|unassignedMessage
operator|=
name|course
operator|.
name|getEnrollmentMessage
argument_list|()
expr_stmt|;
if|else if
condition|(
name|course
operator|.
name|isOverMaxCredit
argument_list|()
condition|)
name|unassignedMessage
operator|=
name|MESSAGES
operator|.
name|conflictOverMaxCredit
argument_list|(
name|course
operator|.
name|getOverMaxCredit
argument_list|()
argument_list|)
expr_stmt|;
if|else if
condition|(
name|course
operator|.
name|getOverlaps
argument_list|()
operator|!=
literal|null
operator|&&
operator|!
name|course
operator|.
name|getOverlaps
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|unassignedMessage
operator|=
literal|""
expr_stmt|;
for|for
control|(
name|Iterator
argument_list|<
name|String
argument_list|>
name|i
init|=
name|course
operator|.
name|getOverlaps
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|String
name|x
init|=
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|unassignedMessage
operator|.
name|isEmpty
argument_list|()
condition|)
name|unassignedMessage
operator|+=
name|MESSAGES
operator|.
name|conflictWithFirst
argument_list|(
name|x
argument_list|)
expr_stmt|;
if|else if
condition|(
operator|!
name|i
operator|.
name|hasNext
argument_list|()
condition|)
name|unassignedMessage
operator|+=
name|MESSAGES
operator|.
name|conflictWithLast
argument_list|(
name|x
argument_list|)
expr_stmt|;
else|else
name|unassignedMessage
operator|+=
name|MESSAGES
operator|.
name|conflictWithMiddle
argument_list|(
name|x
argument_list|)
expr_stmt|;
if|if
condition|(
name|i
operator|.
name|hasNext
argument_list|()
condition|)
name|unassignedMessage
operator|+=
literal|", "
expr_stmt|;
block|}
if|if
condition|(
name|course
operator|.
name|getInstead
argument_list|()
operator|!=
literal|null
condition|)
name|unassignedMessage
operator|+=
name|MESSAGES
operator|.
name|conflictAssignedAlternative
argument_list|(
name|course
operator|.
name|getInstead
argument_list|()
argument_list|)
expr_stmt|;
name|unassignedMessage
operator|+=
literal|"."
expr_stmt|;
block|}
if|else if
condition|(
name|course
operator|.
name|isNotAvailable
argument_list|()
condition|)
block|{
if|if
condition|(
name|course
operator|.
name|isFull
argument_list|()
condition|)
name|unassignedMessage
operator|=
name|MESSAGES
operator|.
name|courseIsFull
argument_list|()
expr_stmt|;
if|else if
condition|(
name|course
operator|.
name|hasHasIncompReqs
argument_list|()
condition|)
name|unassignedMessage
operator|=
name|MESSAGES
operator|.
name|classNotAvailableDueToStudentPrefs
argument_list|()
expr_stmt|;
else|else
name|unassignedMessage
operator|=
name|MESSAGES
operator|.
name|classNotAvailable
argument_list|()
expr_stmt|;
block|}
if|else if
condition|(
name|course
operator|.
name|isLocked
argument_list|()
condition|)
block|{
name|unassignedMessage
operator|=
name|MESSAGES
operator|.
name|courseLocked
argument_list|(
name|course
operator|.
name|getSubject
argument_list|()
operator|+
literal|" "
operator|+
name|course
operator|.
name|getCourseNbr
argument_list|()
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|ClassAssignmentInterface
operator|.
name|ClassAssignment
name|clazz
range|:
name|course
operator|.
name|getClassAssignments
argument_list|()
control|)
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
name|course
operator|.
name|isFreeTime
argument_list|()
condition|?
name|MESSAGES
operator|.
name|freeTimeSubject
argument_list|()
else|:
name|course
operator|.
name|getSubject
argument_list|()
argument_list|)
argument_list|,
operator|new
name|WebTable
operator|.
name|Cell
argument_list|(
name|course
operator|.
name|isFreeTime
argument_list|()
condition|?
name|MESSAGES
operator|.
name|freeTimeCourse
argument_list|()
else|:
name|course
operator|.
name|getCourseNbr
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
name|unassignedMessage
argument_list|,
literal|3
argument_list|,
literal|null
argument_list|)
argument_list|,
name|clazz
operator|.
name|getNote
argument_list|()
operator|==
literal|null
condition|?
operator|new
name|WebTable
operator|.
name|Cell
argument_list|(
literal|""
argument_list|)
else|:
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
argument_list|,
operator|new
name|WebTable
operator|.
name|AbbvTextCell
argument_list|(
name|clazz
operator|.
name|getCredit
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
break|break;
block|}
if|if
condition|(
name|row
operator|==
literal|null
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
name|course
operator|.
name|getSubject
argument_list|()
argument_list|)
argument_list|,
operator|new
name|WebTable
operator|.
name|Cell
argument_list|(
name|course
operator|.
name|getCourseNbr
argument_list|()
argument_list|)
argument_list|,
operator|new
name|WebTable
operator|.
name|Cell
argument_list|(
name|unassignedMessage
argument_list|,
literal|12
argument_list|,
literal|null
argument_list|)
argument_list|)
expr_stmt|;
block|}
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
name|style
argument_list|)
expr_stmt|;
name|row
operator|.
name|getCell
argument_list|(
name|row
operator|.
name|getNrCells
argument_list|()
operator|-
literal|1
argument_list|)
operator|.
name|setStyleName
argument_list|(
literal|"text-gray"
operator|+
operator|(
operator|!
name|rows
operator|.
name|isEmpty
argument_list|()
condition|?
literal|" top-border-dashed"
else|:
literal|""
operator|)
argument_list|)
expr_stmt|;
name|rows
operator|.
name|add
argument_list|(
name|row
argument_list|)
expr_stmt|;
block|}
block|}
name|WebTable
operator|.
name|Row
index|[]
name|rowArray
init|=
operator|new
name|WebTable
operator|.
name|Row
index|[
name|rows
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
for|for
control|(
name|WebTable
operator|.
name|Row
name|row
range|:
name|rows
control|)
name|rowArray
index|[
name|idx
operator|++
index|]
operator|=
name|row
expr_stmt|;
name|iTable
operator|.
name|setData
argument_list|(
name|rowArray
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

