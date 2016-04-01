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
name|instructor
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
name|Client
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
name|page
operator|.
name|UniTimePageLabel
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
name|RoomHint
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
name|UniTimeTable
operator|.
name|MouseClickListener
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
name|command
operator|.
name|client
operator|.
name|GwtRpcResponseList
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
name|command
operator|.
name|client
operator|.
name|GwtRpcService
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
name|command
operator|.
name|client
operator|.
name|GwtRpcServiceAsync
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
name|InstructorInterface
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
name|EventInterface
operator|.
name|EncodeQueryRpcRequest
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
name|EventInterface
operator|.
name|EncodeQueryRpcResponse
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
name|InstructorInterface
operator|.
name|AttributeInterface
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
name|InstructorInterface
operator|.
name|AttributesColumn
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
name|InstructorInterface
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
name|InstructorInterface
operator|.
name|GetInstructorAttributesRequest
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
name|InstructorInterface
operator|.
name|GetInstructorsRequest
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
name|InstructorInterface
operator|.
name|InstructorAttributePropertiesInterface
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
name|InstructorInterface
operator|.
name|InstructorAttributePropertiesRequest
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
name|ChangeEvent
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
name|ChangeHandler
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
name|HasVerticalAlignment
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
name|ListBox
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
name|SimplePanel
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|InstructorAttributesPage
extends|extends
name|Composite
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
name|GwtRpcServiceAsync
name|RPC
init|=
name|GWT
operator|.
name|create
argument_list|(
name|GwtRpcService
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|ListBox
name|iFilter
init|=
literal|null
decl_stmt|;
specifier|private
name|SimpleForm
name|iAttributesPanel
init|=
literal|null
decl_stmt|;
specifier|private
name|SimplePanel
name|iRootPanel
decl_stmt|;
specifier|private
name|SimplePanel
name|iPanel
init|=
literal|null
decl_stmt|;
specifier|private
name|UniTimeHeaderPanel
name|iFilterPanel
init|=
literal|null
decl_stmt|;
specifier|private
name|InstructorAttributePropertiesInterface
name|iProperties
init|=
literal|null
decl_stmt|;
specifier|private
name|UniTimeHeaderPanel
name|iGlobalAttributesHeader
init|=
literal|null
decl_stmt|;
specifier|private
name|InstructorAttributesTable
name|iGlobalAttributesTable
init|=
literal|null
decl_stmt|;
specifier|private
name|int
name|iGlobalAttributesRow
init|=
operator|-
literal|1
decl_stmt|;
specifier|private
name|UniTimeHeaderPanel
name|iDepartmentalAttributesHeader
init|=
literal|null
decl_stmt|;
specifier|private
name|InstructorAttributesTable
name|iDepartmentalAttributesTable
init|=
literal|null
decl_stmt|;
specifier|private
name|int
name|iDepartmentalAttributesRow
init|=
operator|-
literal|1
decl_stmt|;
specifier|private
name|InstructorAttributeEdit
name|iInstructorAttributeEdit
init|=
literal|null
decl_stmt|;
specifier|public
name|InstructorAttributesPage
parameter_list|()
block|{
name|iPanel
operator|=
operator|new
name|SimplePanel
argument_list|()
expr_stmt|;
name|iAttributesPanel
operator|=
operator|new
name|SimpleForm
argument_list|()
expr_stmt|;
name|iAttributesPanel
operator|.
name|setWidth
argument_list|(
literal|"100%"
argument_list|)
expr_stmt|;
name|iAttributesPanel
operator|.
name|removeStyleName
argument_list|(
literal|"unitime-NotPrintableBottomLine"
argument_list|)
expr_stmt|;
name|ClickHandler
name|clickSearch
init|=
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
name|search
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
block|}
decl_stmt|;
name|ClickHandler
name|clickNew
init|=
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
name|edit
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
block|}
decl_stmt|;
name|iInstructorAttributeEdit
operator|=
literal|null
expr_stmt|;
name|iFilterPanel
operator|=
operator|new
name|UniTimeHeaderPanel
argument_list|(
name|MESSAGES
operator|.
name|propDepartment
argument_list|()
argument_list|)
expr_stmt|;
name|iFilter
operator|=
operator|new
name|ListBox
argument_list|()
expr_stmt|;
name|iFilter
operator|.
name|setStyleName
argument_list|(
literal|"unitime-TextBox"
argument_list|)
expr_stmt|;
name|iFilterPanel
operator|.
name|getPanel
argument_list|()
operator|.
name|insert
argument_list|(
name|iFilter
argument_list|,
literal|2
argument_list|)
expr_stmt|;
name|iFilter
operator|.
name|addChangeHandler
argument_list|(
operator|new
name|ChangeHandler
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onChange
parameter_list|(
name|ChangeEvent
name|event
parameter_list|)
block|{
name|DepartmentInterface
name|dept
init|=
name|getDepartment
argument_list|()
decl_stmt|;
name|iFilterPanel
operator|.
name|setEnabled
argument_list|(
literal|"search"
argument_list|,
name|dept
operator|!=
literal|null
operator|&&
name|dept
operator|.
name|isCanSeeAttributes
argument_list|()
argument_list|)
expr_stmt|;
name|iFilterPanel
operator|.
name|setEnabled
argument_list|(
literal|"new"
argument_list|,
name|iProperties
operator|!=
literal|null
operator|&&
operator|(
name|iProperties
operator|.
name|isCanAddGlobalAttribute
argument_list|()
operator|||
operator|(
name|dept
operator|!=
literal|null
operator|&&
name|dept
operator|.
name|isCanAddAttribute
argument_list|()
operator|)
operator|)
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|iFilterPanel
operator|.
name|getPanel
argument_list|()
operator|.
name|setCellVerticalAlignment
argument_list|(
name|iFilter
argument_list|,
name|HasVerticalAlignment
operator|.
name|ALIGN_MIDDLE
argument_list|)
expr_stmt|;
name|iFilter
operator|.
name|getElement
argument_list|()
operator|.
name|getStyle
argument_list|()
operator|.
name|setMarginLeft
argument_list|(
literal|5
argument_list|,
name|Unit
operator|.
name|PX
argument_list|)
expr_stmt|;
name|iFilterPanel
operator|.
name|addButton
argument_list|(
literal|"search"
argument_list|,
name|MESSAGES
operator|.
name|buttonSearch
argument_list|()
argument_list|,
name|clickSearch
argument_list|)
expr_stmt|;
name|iFilterPanel
operator|.
name|addButton
argument_list|(
literal|"new"
argument_list|,
name|MESSAGES
operator|.
name|buttonAddNewInstructorAttribute
argument_list|()
argument_list|,
name|clickNew
argument_list|)
expr_stmt|;
name|iFilterPanel
operator|.
name|setEnabled
argument_list|(
literal|"search"
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|iFilterPanel
operator|.
name|setEnabled
argument_list|(
literal|"new"
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|int
name|filterRow
init|=
name|iAttributesPanel
operator|.
name|addHeaderRow
argument_list|(
name|iFilterPanel
argument_list|)
decl_stmt|;
name|iAttributesPanel
operator|.
name|getCellFormatter
argument_list|()
operator|.
name|setHorizontalAlignment
argument_list|(
name|filterRow
argument_list|,
literal|0
argument_list|,
name|HasHorizontalAlignment
operator|.
name|ALIGN_CENTER
argument_list|)
expr_stmt|;
name|setup
argument_list|()
expr_stmt|;
name|iGlobalAttributesHeader
operator|=
operator|new
name|UniTimeHeaderPanel
argument_list|(
name|MESSAGES
operator|.
name|headerGlobalInstructorAttributes
argument_list|()
argument_list|)
expr_stmt|;
name|iGlobalAttributesRow
operator|=
name|iAttributesPanel
operator|.
name|addHeaderRow
argument_list|(
name|iGlobalAttributesHeader
argument_list|)
expr_stmt|;
name|iGlobalAttributesTable
operator|=
operator|new
name|InstructorAttributesTable
argument_list|()
block|{
specifier|protected
name|void
name|doSort
parameter_list|(
name|AttributesColumn
name|column
parameter_list|)
block|{
name|super
operator|.
name|doSort
argument_list|(
name|column
argument_list|)
expr_stmt|;
name|iDepartmentalAttributesTable
operator|.
name|setSortBy
argument_list|(
name|InstructorCookie
operator|.
name|getInstance
argument_list|()
operator|.
name|getSortAttributesBy
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
expr_stmt|;
name|iAttributesPanel
operator|.
name|addRow
argument_list|(
name|iGlobalAttributesTable
argument_list|)
expr_stmt|;
name|iAttributesPanel
operator|.
name|getRowFormatter
argument_list|()
operator|.
name|setVisible
argument_list|(
name|iGlobalAttributesRow
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|iAttributesPanel
operator|.
name|getRowFormatter
argument_list|()
operator|.
name|setVisible
argument_list|(
name|iGlobalAttributesRow
operator|+
literal|1
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|iDepartmentalAttributesHeader
operator|=
operator|new
name|UniTimeHeaderPanel
argument_list|(
name|MESSAGES
operator|.
name|headerDepartmentalInstructorAttributes
argument_list|()
argument_list|)
expr_stmt|;
name|iDepartmentalAttributesRow
operator|=
name|iAttributesPanel
operator|.
name|addHeaderRow
argument_list|(
name|iDepartmentalAttributesHeader
argument_list|)
expr_stmt|;
name|iDepartmentalAttributesTable
operator|=
operator|new
name|InstructorAttributesTable
argument_list|()
block|{
specifier|protected
name|void
name|doSort
parameter_list|(
name|AttributesColumn
name|column
parameter_list|)
block|{
name|super
operator|.
name|doSort
argument_list|(
name|column
argument_list|)
expr_stmt|;
name|iGlobalAttributesTable
operator|.
name|setSortBy
argument_list|(
name|InstructorCookie
operator|.
name|getInstance
argument_list|()
operator|.
name|getSortAttributesBy
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
expr_stmt|;
name|iAttributesPanel
operator|.
name|addRow
argument_list|(
name|iDepartmentalAttributesTable
argument_list|)
expr_stmt|;
name|iAttributesPanel
operator|.
name|getRowFormatter
argument_list|()
operator|.
name|setVisible
argument_list|(
name|iDepartmentalAttributesRow
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|iAttributesPanel
operator|.
name|getRowFormatter
argument_list|()
operator|.
name|setVisible
argument_list|(
name|iDepartmentalAttributesRow
operator|+
literal|1
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|iRootPanel
operator|=
operator|new
name|SimplePanel
argument_list|(
name|iAttributesPanel
argument_list|)
expr_stmt|;
name|iPanel
operator|.
name|setWidget
argument_list|(
name|iRootPanel
argument_list|)
expr_stmt|;
name|iGlobalAttributesTable
operator|.
name|addMouseClickListener
argument_list|(
operator|new
name|MouseClickListener
argument_list|<
name|AttributeInterface
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onMouseClick
parameter_list|(
specifier|final
name|TableEvent
argument_list|<
name|AttributeInterface
argument_list|>
name|event
parameter_list|)
block|{
if|if
condition|(
name|event
operator|.
name|getData
argument_list|()
operator|!=
literal|null
operator|&&
operator|(
name|event
operator|.
name|getData
argument_list|()
operator|.
name|canAssign
argument_list|()
operator|||
name|event
operator|.
name|getData
argument_list|()
operator|.
name|canDelete
argument_list|()
operator|)
condition|)
name|edit
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
name|iDepartmentalAttributesTable
operator|.
name|addMouseClickListener
argument_list|(
operator|new
name|MouseClickListener
argument_list|<
name|AttributeInterface
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onMouseClick
parameter_list|(
specifier|final
name|TableEvent
argument_list|<
name|AttributeInterface
argument_list|>
name|event
parameter_list|)
block|{
if|if
condition|(
name|event
operator|.
name|getData
argument_list|()
operator|!=
literal|null
operator|&&
operator|(
name|event
operator|.
name|getData
argument_list|()
operator|.
name|canAssign
argument_list|()
operator|||
name|event
operator|.
name|getData
argument_list|()
operator|.
name|canDelete
argument_list|()
operator|)
condition|)
name|edit
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
name|initWidget
argument_list|(
name|iPanel
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|DepartmentInterface
name|getDepartment
parameter_list|()
block|{
if|if
condition|(
name|iFilter
operator|.
name|getSelectedIndex
argument_list|()
operator|>=
literal|0
condition|)
return|return
name|iProperties
operator|.
name|getDepartment
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|iFilter
operator|.
name|getValue
argument_list|(
name|iFilter
operator|.
name|getSelectedIndex
argument_list|()
argument_list|)
argument_list|)
argument_list|)
return|;
return|return
literal|null
return|;
block|}
specifier|private
name|void
name|edit
parameter_list|(
name|AttributeInterface
name|attribute
parameter_list|)
block|{
if|if
condition|(
name|iInstructorAttributeEdit
operator|==
literal|null
condition|)
return|return;
name|DepartmentInterface
name|dept
init|=
name|getDepartment
argument_list|()
decl_stmt|;
name|iInstructorAttributeEdit
operator|.
name|setAttribute
argument_list|(
name|attribute
argument_list|,
name|dept
argument_list|)
expr_stmt|;
if|if
condition|(
name|dept
operator|==
literal|null
condition|)
block|{
name|iFilterPanel
operator|.
name|clearMessage
argument_list|()
expr_stmt|;
name|iInstructorAttributeEdit
operator|.
name|setInstructors
argument_list|(
operator|new
name|ArrayList
argument_list|<
name|InstructorInterface
argument_list|>
argument_list|()
argument_list|)
expr_stmt|;
name|iInstructorAttributeEdit
operator|.
name|show
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|GetInstructorsRequest
name|request
init|=
operator|new
name|GetInstructorsRequest
argument_list|()
decl_stmt|;
name|request
operator|.
name|setDepartmentId
argument_list|(
name|dept
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
name|LoadingWidget
operator|.
name|execute
argument_list|(
name|request
argument_list|,
operator|new
name|AsyncCallback
argument_list|<
name|GwtRpcResponseList
argument_list|<
name|InstructorInterface
argument_list|>
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
name|iFilterPanel
operator|.
name|setErrorMessage
argument_list|(
name|MESSAGES
operator|.
name|failedToLoadRooms
argument_list|(
name|caught
operator|.
name|getMessage
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|UniTimeNotifications
operator|.
name|error
argument_list|(
name|MESSAGES
operator|.
name|failedToLoadRooms
argument_list|(
name|caught
operator|.
name|getMessage
argument_list|()
argument_list|)
argument_list|,
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
name|GwtRpcResponseList
argument_list|<
name|InstructorInterface
argument_list|>
name|result
parameter_list|)
block|{
name|iFilterPanel
operator|.
name|clearMessage
argument_list|()
expr_stmt|;
name|iInstructorAttributeEdit
operator|.
name|setInstructors
argument_list|(
name|result
argument_list|)
expr_stmt|;
name|iInstructorAttributeEdit
operator|.
name|show
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|,
name|MESSAGES
operator|.
name|waitLoadingInstructors
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
specifier|protected
name|void
name|setup
parameter_list|()
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
name|waitLoadingPage
argument_list|()
argument_list|)
expr_stmt|;
name|RPC
operator|.
name|execute
argument_list|(
operator|new
name|InstructorAttributePropertiesRequest
argument_list|()
argument_list|,
operator|new
name|AsyncCallback
argument_list|<
name|InstructorAttributePropertiesInterface
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
name|iFilterPanel
operator|.
name|setErrorMessage
argument_list|(
name|MESSAGES
operator|.
name|failedToInitialize
argument_list|(
name|caught
operator|.
name|getMessage
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|UniTimeNotifications
operator|.
name|error
argument_list|(
name|MESSAGES
operator|.
name|failedToInitialize
argument_list|(
name|caught
operator|.
name|getMessage
argument_list|()
argument_list|)
argument_list|,
name|caught
argument_list|)
expr_stmt|;
name|ToolBox
operator|.
name|checkAccess
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
name|InstructorAttributePropertiesInterface
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
name|iProperties
operator|=
name|result
expr_stmt|;
name|iInstructorAttributeEdit
operator|=
operator|new
name|InstructorAttributeEdit
argument_list|(
name|iProperties
argument_list|)
block|{
annotation|@
name|Override
specifier|protected
name|void
name|onShow
parameter_list|()
block|{
name|RoomHint
operator|.
name|hideHint
argument_list|()
expr_stmt|;
name|iRootPanel
operator|.
name|setWidget
argument_list|(
name|iInstructorAttributeEdit
argument_list|)
expr_stmt|;
name|Client
operator|.
name|fireGwtPageChanged
argument_list|(
operator|new
name|Client
operator|.
name|GwtPageChangeEvent
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|protected
name|void
name|onHide
parameter_list|(
name|boolean
name|refresh
parameter_list|,
name|AttributeInterface
name|attribute
parameter_list|)
block|{
name|iRootPanel
operator|.
name|setWidget
argument_list|(
name|iAttributesPanel
argument_list|)
expr_stmt|;
name|UniTimePageLabel
operator|.
name|getInstance
argument_list|()
operator|.
name|setPageName
argument_list|(
name|MESSAGES
operator|.
name|pageInstructorAttributes
argument_list|()
argument_list|)
expr_stmt|;
name|Client
operator|.
name|fireGwtPageChanged
argument_list|(
operator|new
name|Client
operator|.
name|GwtPageChangeEvent
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|refresh
operator|&&
operator|(
name|iAttributesPanel
operator|.
name|getRowFormatter
argument_list|()
operator|.
name|isVisible
argument_list|(
name|iGlobalAttributesRow
argument_list|)
operator|||
name|iAttributesPanel
operator|.
name|getRowFormatter
argument_list|()
operator|.
name|isVisible
argument_list|(
name|iDepartmentalAttributesRow
argument_list|)
operator|)
condition|)
name|search
argument_list|(
name|attribute
operator|==
literal|null
condition|?
literal|null
else|:
name|attribute
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
expr_stmt|;
name|iFilter
operator|.
name|clear
argument_list|()
expr_stmt|;
name|iFilter
operator|.
name|addItem
argument_list|(
name|MESSAGES
operator|.
name|itemSelect
argument_list|()
argument_list|,
literal|"-1"
argument_list|)
expr_stmt|;
name|iFilter
operator|.
name|setSelectedIndex
argument_list|(
literal|0
argument_list|)
expr_stmt|;
for|for
control|(
name|DepartmentInterface
name|d
range|:
name|iProperties
operator|.
name|getDepartments
argument_list|()
control|)
block|{
if|if
condition|(
name|d
operator|.
name|isCanSeeAttributes
argument_list|()
condition|)
block|{
name|iFilter
operator|.
name|addItem
argument_list|(
name|d
operator|.
name|getDeptCode
argument_list|()
operator|+
literal|" - "
operator|+
name|d
operator|.
name|getLabel
argument_list|()
argument_list|,
name|d
operator|.
name|getId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|d
operator|.
name|getId
argument_list|()
operator|.
name|equals
argument_list|(
name|iProperties
operator|.
name|getLastDepartmentId
argument_list|()
argument_list|)
condition|)
name|iFilter
operator|.
name|setSelectedIndex
argument_list|(
name|iFilter
operator|.
name|getItemCount
argument_list|()
operator|-
literal|1
argument_list|)
expr_stmt|;
block|}
block|}
name|DepartmentInterface
name|dept
init|=
name|getDepartment
argument_list|()
decl_stmt|;
name|iFilterPanel
operator|.
name|setEnabled
argument_list|(
literal|"search"
argument_list|,
name|dept
operator|!=
literal|null
operator|&&
name|dept
operator|.
name|isCanSeeAttributes
argument_list|()
argument_list|)
expr_stmt|;
name|iFilterPanel
operator|.
name|setEnabled
argument_list|(
literal|"new"
argument_list|,
name|iProperties
operator|!=
literal|null
operator|&&
operator|(
name|iProperties
operator|.
name|isCanAddGlobalAttribute
argument_list|()
operator|||
operator|(
name|dept
operator|!=
literal|null
operator|&&
name|dept
operator|.
name|isCanAddAttribute
argument_list|()
operator|)
operator|)
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|hideResults
parameter_list|()
block|{
name|iGlobalAttributesTable
operator|.
name|clearTable
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|iDepartmentalAttributesTable
operator|.
name|clearTable
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|iAttributesPanel
operator|.
name|getRowFormatter
argument_list|()
operator|.
name|setVisible
argument_list|(
name|iGlobalAttributesRow
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|iAttributesPanel
operator|.
name|getRowFormatter
argument_list|()
operator|.
name|setVisible
argument_list|(
name|iGlobalAttributesRow
operator|+
literal|1
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|iAttributesPanel
operator|.
name|getRowFormatter
argument_list|()
operator|.
name|setVisible
argument_list|(
name|iDepartmentalAttributesRow
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|iAttributesPanel
operator|.
name|getRowFormatter
argument_list|()
operator|.
name|setVisible
argument_list|(
name|iDepartmentalAttributesRow
operator|+
literal|1
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|search
parameter_list|(
specifier|final
name|Long
name|attributeId
parameter_list|)
block|{
name|hideResults
argument_list|()
expr_stmt|;
specifier|final
name|DepartmentInterface
name|dept
init|=
name|getDepartment
argument_list|()
decl_stmt|;
name|GetInstructorAttributesRequest
name|request
init|=
operator|new
name|GetInstructorAttributesRequest
argument_list|()
decl_stmt|;
if|if
condition|(
name|dept
operator|!=
literal|null
condition|)
name|request
operator|.
name|setDepartmentId
argument_list|(
name|dept
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
name|LoadingWidget
operator|.
name|execute
argument_list|(
name|request
argument_list|,
operator|new
name|AsyncCallback
argument_list|<
name|GwtRpcResponseList
argument_list|<
name|AttributeInterface
argument_list|>
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
name|iFilterPanel
operator|.
name|setErrorMessage
argument_list|(
name|MESSAGES
operator|.
name|failedToLoadInstructorAttributes
argument_list|(
name|caught
operator|.
name|getMessage
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|UniTimeNotifications
operator|.
name|error
argument_list|(
name|MESSAGES
operator|.
name|failedToLoadInstructorAttributes
argument_list|(
name|caught
operator|.
name|getMessage
argument_list|()
argument_list|)
argument_list|,
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
name|GwtRpcResponseList
argument_list|<
name|AttributeInterface
argument_list|>
name|result
parameter_list|)
block|{
name|iFilterPanel
operator|.
name|clearMessage
argument_list|()
expr_stmt|;
if|if
condition|(
name|result
operator|==
literal|null
operator|||
name|result
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|iFilterPanel
operator|.
name|setErrorMessage
argument_list|(
name|MESSAGES
operator|.
name|errorNoInstructorAttributes
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
for|for
control|(
name|AttributeInterface
name|attribute
range|:
name|result
control|)
block|{
if|if
condition|(
name|attribute
operator|.
name|isDepartmental
argument_list|()
condition|)
block|{
if|if
condition|(
name|dept
operator|!=
literal|null
operator|&&
operator|!
name|dept
operator|.
name|equals
argument_list|(
name|attribute
operator|.
name|getDepartment
argument_list|()
argument_list|)
condition|)
continue|continue;
name|iDepartmentalAttributesTable
operator|.
name|addAttribute
argument_list|(
name|attribute
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|iGlobalAttributesTable
operator|.
name|addAttribute
argument_list|(
name|attribute
argument_list|)
expr_stmt|;
block|}
block|}
name|iDepartmentalAttributesTable
operator|.
name|sort
argument_list|()
expr_stmt|;
name|iGlobalAttributesTable
operator|.
name|sort
argument_list|()
expr_stmt|;
block|}
name|iAttributesPanel
operator|.
name|getRowFormatter
argument_list|()
operator|.
name|setVisible
argument_list|(
name|iGlobalAttributesRow
argument_list|,
name|iGlobalAttributesTable
operator|.
name|getRowCount
argument_list|()
operator|>
literal|1
argument_list|)
expr_stmt|;
name|iAttributesPanel
operator|.
name|getRowFormatter
argument_list|()
operator|.
name|setVisible
argument_list|(
name|iGlobalAttributesRow
operator|+
literal|1
argument_list|,
name|iGlobalAttributesTable
operator|.
name|getRowCount
argument_list|()
operator|>
literal|1
argument_list|)
expr_stmt|;
name|iAttributesPanel
operator|.
name|getRowFormatter
argument_list|()
operator|.
name|setVisible
argument_list|(
name|iDepartmentalAttributesRow
argument_list|,
name|iDepartmentalAttributesTable
operator|.
name|getRowCount
argument_list|()
operator|>
literal|1
argument_list|)
expr_stmt|;
name|iAttributesPanel
operator|.
name|getRowFormatter
argument_list|()
operator|.
name|setVisible
argument_list|(
name|iDepartmentalAttributesRow
operator|+
literal|1
argument_list|,
name|iDepartmentalAttributesTable
operator|.
name|getRowCount
argument_list|()
operator|>
literal|1
argument_list|)
expr_stmt|;
name|iDepartmentalAttributesTable
operator|.
name|scrollTo
argument_list|(
name|attributeId
argument_list|)
expr_stmt|;
name|iGlobalAttributesTable
operator|.
name|scrollTo
argument_list|(
name|attributeId
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|,
name|MESSAGES
operator|.
name|waitLoadingInstructorAttributes
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|export
parameter_list|(
name|String
name|format
parameter_list|)
block|{
name|RPC
operator|.
name|execute
argument_list|(
name|EncodeQueryRpcRequest
operator|.
name|encode
argument_list|(
name|query
argument_list|(
name|format
argument_list|)
argument_list|)
argument_list|,
operator|new
name|AsyncCallback
argument_list|<
name|EncodeQueryRpcResponse
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
block|}
annotation|@
name|Override
specifier|public
name|void
name|onSuccess
parameter_list|(
name|EncodeQueryRpcResponse
name|result
parameter_list|)
block|{
name|ToolBox
operator|.
name|open
argument_list|(
name|GWT
operator|.
name|getHostPageBaseURL
argument_list|()
operator|+
literal|"export?q="
operator|+
name|result
operator|.
name|getQuery
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|String
name|query
parameter_list|(
name|String
name|format
parameter_list|)
block|{
name|InstructorCookie
name|cookie
init|=
name|InstructorCookie
operator|.
name|getInstance
argument_list|()
decl_stmt|;
name|String
name|query
init|=
literal|"output="
operator|+
name|format
operator|+
literal|"&sort="
operator|+
name|cookie
operator|.
name|getSortAttributesBy
argument_list|()
decl_stmt|;
name|DepartmentInterface
name|dept
init|=
name|getDepartment
argument_list|()
decl_stmt|;
if|if
condition|(
name|dept
operator|!=
literal|null
condition|)
name|query
operator|+=
literal|"&department="
operator|+
name|dept
operator|.
name|getId
argument_list|()
expr_stmt|;
return|return
name|query
return|;
block|}
block|}
end_class

end_unit

