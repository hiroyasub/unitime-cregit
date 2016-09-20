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
name|HashMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
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
name|ToolBox
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
name|aria
operator|.
name|AriaButton
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
name|aria
operator|.
name|AriaStatus
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
name|UniTimeDialogBox
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
name|UniTimeTabPanel
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
name|UniTimeTable
operator|.
name|TableEvent
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
name|CourseFinder
operator|.
name|CourseFinderCourseDetails
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
name|CourseRequestInterface
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
name|DegreePlanInterface
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
name|DegreePlanInterface
operator|.
name|DegreeCourseInterface
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
name|DegreePlanInterface
operator|.
name|DegreeGroupInterface
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
name|DegreePlanInterface
operator|.
name|DegreePlaceHolderInterface
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
name|Element
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
name|dom
operator|.
name|client
operator|.
name|KeyCodes
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
name|SelectionEvent
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
name|SelectionHandler
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
name|Cookies
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
name|Event
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
name|Event
operator|.
name|NativePreviewEvent
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
name|Button
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
name|RadioButton
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
name|ScrollPanel
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
name|DegreePlanDialog
extends|extends
name|UniTimeDialogBox
block|{
specifier|protected
specifier|static
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
name|SimpleForm
name|iForm
decl_stmt|;
specifier|private
name|DegreePlanTable
name|iDegreePlanTable
decl_stmt|;
specifier|private
name|ScrollPanel
name|iDegreePlanPanel
decl_stmt|;
specifier|private
name|CourseFinderCourseDetails
index|[]
name|iDetails
init|=
literal|null
decl_stmt|;
specifier|private
name|UniTimeTabPanel
name|iCourseDetailsTabPanel
init|=
literal|null
decl_stmt|;
specifier|private
name|Button
name|iBack
decl_stmt|;
specifier|private
name|UniTimeHeaderPanel
name|iFooter
decl_stmt|;
specifier|private
name|Map
argument_list|<
name|Character
argument_list|,
name|Integer
argument_list|>
name|iTabAccessKeys
init|=
operator|new
name|HashMap
argument_list|<
name|Character
argument_list|,
name|Integer
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|TakesValue
argument_list|<
name|CourseRequestInterface
argument_list|>
name|iRequests
decl_stmt|;
specifier|public
name|DegreePlanDialog
parameter_list|(
name|TakesValue
argument_list|<
name|CourseRequestInterface
argument_list|>
name|requests
parameter_list|,
name|AssignmentProvider
name|assignments
parameter_list|,
name|CourseFinderCourseDetails
modifier|...
name|details
parameter_list|)
block|{
name|super
argument_list|(
literal|true
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|setEscapeToHide
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|addStyleName
argument_list|(
literal|"unitime-DegreePlanDialog"
argument_list|)
expr_stmt|;
name|iRequests
operator|=
name|requests
expr_stmt|;
name|iForm
operator|=
operator|new
name|SimpleForm
argument_list|()
expr_stmt|;
name|iDegreePlanTable
operator|=
operator|new
name|DegreePlanTable
argument_list|(
name|requests
argument_list|,
name|assignments
argument_list|)
expr_stmt|;
name|iDegreePlanPanel
operator|=
operator|new
name|ScrollPanel
argument_list|(
name|iDegreePlanTable
argument_list|)
expr_stmt|;
name|iDegreePlanPanel
operator|.
name|setStyleName
argument_list|(
literal|"unitime-ScrollPanel"
argument_list|)
expr_stmt|;
name|iDegreePlanPanel
operator|.
name|addStyleName
argument_list|(
literal|"plan"
argument_list|)
expr_stmt|;
name|iForm
operator|.
name|addRow
argument_list|(
name|iDegreePlanPanel
argument_list|)
expr_stmt|;
name|iDegreePlanTable
operator|.
name|addMouseClickListener
argument_list|(
operator|new
name|UniTimeTable
operator|.
name|MouseClickListener
argument_list|<
name|Object
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
name|Object
argument_list|>
name|event
parameter_list|)
block|{
name|updateAriaStatus
argument_list|()
expr_stmt|;
name|updateCourseDetails
argument_list|(
name|event
operator|.
name|getData
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|iCourseDetailsTabPanel
operator|=
operator|new
name|UniTimeTabPanel
argument_list|()
expr_stmt|;
name|iCourseDetailsTabPanel
operator|.
name|setDeckStyleName
argument_list|(
literal|"unitime-TabPanel"
argument_list|)
expr_stmt|;
name|iCourseDetailsTabPanel
operator|.
name|addSelectionHandler
argument_list|(
operator|new
name|SelectionHandler
argument_list|<
name|Integer
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onSelection
parameter_list|(
name|SelectionEvent
argument_list|<
name|Integer
argument_list|>
name|event
parameter_list|)
block|{
name|Cookies
operator|.
name|setCookie
argument_list|(
literal|"UniTime:CourseFinderCourses"
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|event
operator|.
name|getSelectedItem
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|iForm
operator|.
name|addRow
argument_list|(
name|iCourseDetailsTabPanel
argument_list|)
expr_stmt|;
name|iDetails
operator|=
name|details
expr_stmt|;
name|int
name|tabIndex
init|=
literal|0
decl_stmt|;
for|for
control|(
name|CourseFinderCourseDetails
name|detail
range|:
name|iDetails
control|)
block|{
name|ScrollPanel
name|panel
init|=
operator|new
name|ScrollPanel
argument_list|(
name|detail
operator|.
name|asWidget
argument_list|()
argument_list|)
decl_stmt|;
name|panel
operator|.
name|setStyleName
argument_list|(
literal|"unitime-ScrollPanel-inner"
argument_list|)
expr_stmt|;
name|iCourseDetailsTabPanel
operator|.
name|add
argument_list|(
name|panel
argument_list|,
name|detail
operator|.
name|getName
argument_list|()
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|Character
name|ch
init|=
name|UniTimeHeaderPanel
operator|.
name|guessAccessKey
argument_list|(
name|detail
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|ch
operator|!=
literal|null
condition|)
name|iTabAccessKeys
operator|.
name|put
argument_list|(
name|ch
argument_list|,
name|tabIndex
argument_list|)
expr_stmt|;
name|tabIndex
operator|++
expr_stmt|;
block|}
name|selectLastTab
argument_list|()
expr_stmt|;
name|iFooter
operator|=
operator|new
name|UniTimeHeaderPanel
argument_list|()
expr_stmt|;
name|iFooter
operator|.
name|addButton
argument_list|(
literal|"apply"
argument_list|,
name|MESSAGES
operator|.
name|buttonDegreePlanApply
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
name|doApply
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|iFooter
operator|.
name|addButton
argument_list|(
literal|"close"
argument_list|,
name|MESSAGES
operator|.
name|buttonDegreePlanClose
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
name|hide
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|iBack
operator|=
operator|new
name|AriaButton
argument_list|(
name|MESSAGES
operator|.
name|buttonDegreePlanBack
argument_list|()
argument_list|)
expr_stmt|;
name|iBack
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
name|doBack
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|Character
name|backAck
init|=
name|UniTimeHeaderPanel
operator|.
name|guessAccessKey
argument_list|(
name|MESSAGES
operator|.
name|buttonDegreePlanBack
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|backAck
operator|!=
literal|null
condition|)
name|iBack
operator|.
name|setAccessKey
argument_list|(
name|backAck
argument_list|)
expr_stmt|;
name|ToolBox
operator|.
name|setWhiteSpace
argument_list|(
name|iBack
operator|.
name|getElement
argument_list|()
operator|.
name|getStyle
argument_list|()
argument_list|,
literal|"nowrap"
argument_list|)
expr_stmt|;
name|iFooter
operator|.
name|getPanel
argument_list|()
operator|.
name|insert
argument_list|(
name|iBack
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|iForm
operator|.
name|addBottomRow
argument_list|(
name|iFooter
argument_list|)
expr_stmt|;
name|setWidget
argument_list|(
name|iForm
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|open
parameter_list|(
name|DegreePlanInterface
name|plan
parameter_list|,
name|boolean
name|hasBack
parameter_list|)
block|{
name|iDegreePlanTable
operator|.
name|setValue
argument_list|(
name|plan
argument_list|)
expr_stmt|;
name|setText
argument_list|(
name|MESSAGES
operator|.
name|dialogDegreePlan
argument_list|(
name|plan
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|updateCourseDetails
argument_list|(
name|iDegreePlanTable
operator|.
name|getData
argument_list|(
name|iDegreePlanTable
operator|.
name|getSelectedRow
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|iBack
operator|.
name|setVisible
argument_list|(
name|hasBack
argument_list|)
expr_stmt|;
name|iBack
operator|.
name|setEnabled
argument_list|(
name|hasBack
argument_list|)
expr_stmt|;
name|center
argument_list|()
expr_stmt|;
block|}
specifier|private
name|void
name|selectLastTab
parameter_list|()
block|{
try|try
block|{
name|int
name|tab
init|=
name|Integer
operator|.
name|valueOf
argument_list|(
name|Cookies
operator|.
name|getCookie
argument_list|(
literal|"UniTime:CourseFinderCourses"
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|tab
operator|>=
literal|0
operator|||
name|tab
operator|<
name|iCourseDetailsTabPanel
operator|.
name|getTabCount
argument_list|()
operator|&&
name|tab
operator|!=
name|iCourseDetailsTabPanel
operator|.
name|getSelectedTab
argument_list|()
condition|)
name|iCourseDetailsTabPanel
operator|.
name|selectTab
argument_list|(
name|tab
argument_list|)
expr_stmt|;
else|else
name|iCourseDetailsTabPanel
operator|.
name|selectTab
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|iCourseDetailsTabPanel
operator|.
name|selectTab
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
block|}
specifier|protected
name|void
name|doBack
parameter_list|()
block|{
name|hide
argument_list|()
expr_stmt|;
block|}
specifier|protected
name|void
name|doApply
parameter_list|()
block|{
name|hide
argument_list|()
expr_stmt|;
name|iRequests
operator|.
name|setValue
argument_list|(
name|iDegreePlanTable
operator|.
name|createRequests
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|protected
name|void
name|onPreviewNativeEvent
parameter_list|(
name|NativePreviewEvent
name|event
parameter_list|)
block|{
name|super
operator|.
name|onPreviewNativeEvent
argument_list|(
name|event
argument_list|)
expr_stmt|;
if|if
condition|(
name|event
operator|.
name|getTypeInt
argument_list|()
operator|==
name|Event
operator|.
name|ONKEYUP
operator|&&
operator|(
name|event
operator|.
name|getNativeEvent
argument_list|()
operator|.
name|getAltKey
argument_list|()
operator|||
name|event
operator|.
name|getNativeEvent
argument_list|()
operator|.
name|getCtrlKey
argument_list|()
operator|)
condition|)
block|{
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|Character
argument_list|,
name|Integer
argument_list|>
name|entry
range|:
name|iTabAccessKeys
operator|.
name|entrySet
argument_list|()
control|)
if|if
condition|(
name|event
operator|.
name|getNativeEvent
argument_list|()
operator|.
name|getKeyCode
argument_list|()
operator|==
name|Character
operator|.
name|toLowerCase
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|)
operator|||
name|event
operator|.
name|getNativeEvent
argument_list|()
operator|.
name|getKeyCode
argument_list|()
operator|==
name|Character
operator|.
name|toUpperCase
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|)
condition|)
block|{
name|iCourseDetailsTabPanel
operator|.
name|selectTab
argument_list|(
name|entry
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|event
operator|.
name|getTypeInt
argument_list|()
operator|==
name|Event
operator|.
name|ONKEYDOWN
condition|)
block|{
if|if
condition|(
name|event
operator|.
name|getNativeEvent
argument_list|()
operator|.
name|getKeyCode
argument_list|()
operator|==
name|KeyCodes
operator|.
name|KEY_UP
condition|)
block|{
name|int
name|row
init|=
name|iDegreePlanTable
operator|.
name|getSelectedRow
argument_list|()
decl_stmt|;
if|if
condition|(
name|row
operator|>=
literal|0
condition|)
name|iDegreePlanTable
operator|.
name|setSelected
argument_list|(
name|row
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|row
operator|--
expr_stmt|;
if|if
condition|(
name|row
operator|<=
literal|0
condition|)
name|row
operator|=
name|iDegreePlanTable
operator|.
name|getRowCount
argument_list|()
operator|-
literal|1
expr_stmt|;
name|iDegreePlanTable
operator|.
name|setSelected
argument_list|(
name|row
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|updateCourseDetails
argument_list|(
name|iDegreePlanTable
operator|.
name|getData
argument_list|(
name|row
argument_list|)
argument_list|)
expr_stmt|;
name|scrollToSelectedRow
argument_list|()
expr_stmt|;
name|updateAriaStatus
argument_list|()
expr_stmt|;
block|}
if|else if
condition|(
name|event
operator|.
name|getNativeEvent
argument_list|()
operator|.
name|getKeyCode
argument_list|()
operator|==
name|KeyCodes
operator|.
name|KEY_DOWN
condition|)
block|{
name|int
name|row
init|=
name|iDegreePlanTable
operator|.
name|getSelectedRow
argument_list|()
decl_stmt|;
if|if
condition|(
name|row
operator|>=
literal|0
condition|)
name|iDegreePlanTable
operator|.
name|setSelected
argument_list|(
name|row
argument_list|,
literal|false
argument_list|)
expr_stmt|;
else|else
name|row
operator|=
literal|0
expr_stmt|;
name|row
operator|++
expr_stmt|;
if|if
condition|(
name|row
operator|>=
name|iDegreePlanTable
operator|.
name|getRowCount
argument_list|()
condition|)
name|row
operator|=
literal|1
expr_stmt|;
name|iDegreePlanTable
operator|.
name|setSelected
argument_list|(
name|row
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|updateCourseDetails
argument_list|(
name|iDegreePlanTable
operator|.
name|getData
argument_list|(
name|row
argument_list|)
argument_list|)
expr_stmt|;
name|scrollToSelectedRow
argument_list|()
expr_stmt|;
name|updateAriaStatus
argument_list|()
expr_stmt|;
block|}
block|}
if|if
condition|(
name|event
operator|.
name|getTypeInt
argument_list|()
operator|==
name|Event
operator|.
name|ONKEYUP
operator|&&
operator|(
name|event
operator|.
name|getNativeEvent
argument_list|()
operator|.
name|getKeyCode
argument_list|()
operator|==
name|KeyCodes
operator|.
name|KEY_SPACE
operator|||
name|event
operator|.
name|getNativeEvent
argument_list|()
operator|.
name|getKeyCode
argument_list|()
operator|==
name|KeyCodes
operator|.
name|KEY_ENTER
operator|)
condition|)
block|{
if|if
condition|(
name|iDegreePlanTable
operator|.
name|canChoose
argument_list|(
name|iDegreePlanTable
operator|.
name|getSelectedRow
argument_list|()
argument_list|)
condition|)
name|iDegreePlanTable
operator|.
name|chooseRow
argument_list|(
name|iDegreePlanTable
operator|.
name|getSelectedRow
argument_list|()
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
specifier|protected
name|void
name|updateCourseDetails
parameter_list|(
name|Object
name|data
parameter_list|)
block|{
if|if
condition|(
name|data
operator|==
literal|null
operator|||
operator|!
operator|(
name|data
operator|instanceof
name|CourseAssignment
operator|)
condition|)
block|{
if|if
condition|(
name|iDetails
operator|!=
literal|null
condition|)
for|for
control|(
name|CourseFinderCourseDetails
name|detail
range|:
name|iDetails
control|)
block|{
name|detail
operator|.
name|setValue
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|CourseAssignment
name|course
init|=
operator|(
name|CourseAssignment
operator|)
name|data
decl_stmt|;
name|String
name|courseName
init|=
name|MESSAGES
operator|.
name|courseName
argument_list|(
name|course
operator|.
name|getSubject
argument_list|()
argument_list|,
name|course
operator|.
name|getCourseNbr
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|CONSTANTS
operator|.
name|showCourseTitle
argument_list|()
operator|&&
name|course
operator|.
name|hasTitle
argument_list|()
condition|)
name|courseName
operator|=
name|MESSAGES
operator|.
name|courseNameWithTitle
argument_list|(
name|course
operator|.
name|getSubject
argument_list|()
argument_list|,
name|course
operator|.
name|getCourseNbr
argument_list|()
argument_list|,
name|course
operator|.
name|getTitle
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|CourseFinderCourseDetails
name|detail
range|:
name|iDetails
control|)
name|detail
operator|.
name|setValue
argument_list|(
name|courseName
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
specifier|static
interface|interface
name|AssignmentProvider
block|{
name|ClassAssignmentInterface
name|getLastAssignment
parameter_list|()
function_decl|;
name|ClassAssignmentInterface
name|getSavedAssignment
parameter_list|()
function_decl|;
block|}
specifier|protected
name|void
name|scrollToSelectedRow
parameter_list|()
block|{
if|if
condition|(
name|iDegreePlanTable
operator|.
name|getSelectedRow
argument_list|()
operator|<
literal|0
condition|)
return|return;
name|Element
name|scroll
init|=
name|iDegreePlanPanel
operator|.
name|getElement
argument_list|()
decl_stmt|;
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
name|Element
name|item
init|=
name|iDegreePlanTable
operator|.
name|getRowFormatter
argument_list|()
operator|.
name|getElement
argument_list|(
name|iDegreePlanTable
operator|.
name|getSelectedRow
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|item
operator|==
literal|null
condition|)
return|return;
name|int
name|realOffset
init|=
literal|0
decl_stmt|;
while|while
condition|(
name|item
operator|!=
literal|null
operator|&&
operator|!
name|item
operator|.
name|equals
argument_list|(
name|scroll
argument_list|)
condition|)
block|{
name|realOffset
operator|+=
name|item
operator|.
name|getOffsetTop
argument_list|()
expr_stmt|;
name|item
operator|=
name|item
operator|.
name|getOffsetParent
argument_list|()
expr_stmt|;
block|}
name|scroll
operator|.
name|setScrollTop
argument_list|(
name|realOffset
operator|-
name|scroll
operator|.
name|getOffsetHeight
argument_list|()
operator|/
literal|2
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|updateAriaStatus
parameter_list|()
block|{
name|int
name|row
init|=
name|iDegreePlanTable
operator|.
name|getSelectedRow
argument_list|()
decl_stmt|;
name|Object
name|data
init|=
name|iDegreePlanTable
operator|.
name|getData
argument_list|(
name|row
argument_list|)
decl_stmt|;
name|String
name|status
init|=
literal|null
decl_stmt|;
name|String
name|name
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|data
operator|instanceof
name|DegreePlaceHolderInterface
condition|)
block|{
name|DegreePlaceHolderInterface
name|ph
init|=
operator|(
name|DegreePlaceHolderInterface
operator|)
name|data
decl_stmt|;
name|status
operator|=
name|ARIA
operator|.
name|degreePlaceholder
argument_list|(
name|ph
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|name
operator|=
name|ph
operator|.
name|getType
argument_list|()
expr_stmt|;
block|}
if|else if
condition|(
name|data
operator|instanceof
name|DegreeGroupInterface
condition|)
block|{
name|DegreeGroupInterface
name|group
init|=
operator|(
name|DegreeGroupInterface
operator|)
name|data
decl_stmt|;
if|if
condition|(
name|group
operator|.
name|isChoice
argument_list|()
condition|)
name|status
operator|=
name|ARIA
operator|.
name|degreeChoiceGroup
argument_list|(
name|group
operator|.
name|toString
argument_list|(
name|MESSAGES
argument_list|)
argument_list|)
expr_stmt|;
else|else
name|status
operator|=
name|ARIA
operator|.
name|degreeUnionGroup
argument_list|(
name|group
operator|.
name|toString
argument_list|(
name|MESSAGES
argument_list|)
argument_list|)
expr_stmt|;
name|name
operator|=
name|group
operator|.
name|toString
argument_list|(
name|MESSAGES
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|data
operator|instanceof
name|DegreeCourseInterface
condition|)
block|{
name|DegreeCourseInterface
name|course
init|=
operator|(
name|DegreeCourseInterface
operator|)
name|data
decl_stmt|;
if|if
condition|(
name|course
operator|.
name|hasCourses
argument_list|()
condition|)
name|status
operator|=
name|ARIA
operator|.
name|degreeCourseWithChoice
argument_list|(
name|course
operator|.
name|getCourseName
argument_list|()
argument_list|,
name|course
operator|.
name|getTitle
argument_list|()
argument_list|,
name|course
operator|.
name|getCourses
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
else|else
name|status
operator|=
name|ARIA
operator|.
name|degreeCourseNotOffered
argument_list|(
name|course
operator|.
name|getCourseName
argument_list|()
argument_list|,
name|course
operator|.
name|getTitle
argument_list|()
argument_list|)
expr_stmt|;
name|name
operator|=
name|course
operator|.
name|getCourseName
argument_list|()
expr_stmt|;
block|}
if|else if
condition|(
name|data
operator|instanceof
name|CourseAssignment
condition|)
block|{
name|CourseAssignment
name|course
init|=
operator|(
name|CourseAssignment
operator|)
name|data
decl_stmt|;
if|if
condition|(
name|course
operator|.
name|getNote
argument_list|()
operator|==
literal|null
operator|||
name|course
operator|.
name|getNote
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
name|status
operator|=
name|ARIA
operator|.
name|degreeCourse
argument_list|(
name|course
operator|.
name|getCourseName
argument_list|()
argument_list|,
name|course
operator|.
name|getTitle
argument_list|()
argument_list|)
expr_stmt|;
else|else
name|status
operator|=
name|ARIA
operator|.
name|degreeCourseWithNote
argument_list|(
name|course
operator|.
name|getCourseName
argument_list|()
argument_list|,
name|course
operator|.
name|getTitle
argument_list|()
argument_list|,
name|course
operator|.
name|getNote
argument_list|()
argument_list|)
expr_stmt|;
name|name
operator|=
name|course
operator|.
name|getCourseName
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|name
operator|!=
literal|null
condition|)
block|{
name|Widget
name|w
init|=
name|iDegreePlanTable
operator|.
name|getWidget
argument_list|(
name|row
argument_list|,
literal|1
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
name|RadioButton
condition|)
block|{
name|RadioButton
name|radio
init|=
operator|(
name|RadioButton
operator|)
name|w
decl_stmt|;
if|if
condition|(
name|radio
operator|.
name|getValue
argument_list|()
condition|)
name|status
operator|+=
literal|" "
operator|+
name|ARIA
operator|.
name|degreeCourseSelected
argument_list|(
name|name
argument_list|)
expr_stmt|;
else|else
name|status
operator|+=
literal|" "
operator|+
name|ARIA
operator|.
name|degreeSpaceToSelectCourse
argument_list|(
name|name
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|status
operator|!=
literal|null
condition|)
name|AriaStatus
operator|.
name|getInstance
argument_list|()
operator|.
name|setText
argument_list|(
name|ARIA
operator|.
name|selectedLine
argument_list|(
name|row
argument_list|,
name|iDegreePlanTable
operator|.
name|getRowCount
argument_list|()
operator|-
literal|1
argument_list|)
operator|+
literal|" "
operator|+
name|status
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

