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
name|rooms
package|;
end_package

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
name|UniTimeWidget
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
name|EventInterface
operator|.
name|FilterRpcResponse
operator|.
name|Entity
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
name|RoomInterface
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
name|RoomInterface
operator|.
name|FeatureInterface
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
name|RoomInterface
operator|.
name|FeatureTypeInterface
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
name|RoomInterface
operator|.
name|RoomDetailInterface
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
name|RoomInterface
operator|.
name|RoomPropertiesInterface
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
name|RoomInterface
operator|.
name|RoomsPageMode
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
name|RoomInterface
operator|.
name|UpdateRoomFeatureRequest
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
name|Window
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
name|TextBox
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|RoomFeatureEdit
extends|extends
name|Composite
block|{
specifier|private
specifier|static
specifier|final
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
name|SimpleForm
name|iForm
decl_stmt|;
specifier|private
name|UniTimeHeaderPanel
name|iHeader
decl_stmt|,
name|iFooter
decl_stmt|;
specifier|private
name|RoomPropertiesInterface
name|iProperties
decl_stmt|;
specifier|private
name|FeatureInterface
name|iFeature
decl_stmt|;
specifier|private
name|UniTimeWidget
argument_list|<
name|TextBox
argument_list|>
name|iName
decl_stmt|;
specifier|private
name|UniTimeWidget
argument_list|<
name|TextBox
argument_list|>
name|iAbbreviation
decl_stmt|;
specifier|private
name|ListBox
name|iType
init|=
literal|null
decl_stmt|;
specifier|private
name|UniTimeWidget
argument_list|<
name|ListBox
argument_list|>
name|iDepartment
decl_stmt|;
specifier|private
name|CheckBox
name|iGlobal
decl_stmt|;
specifier|private
name|int
name|iDepartmentRow
init|=
operator|-
literal|1
decl_stmt|;
specifier|private
name|RoomsTable
name|iRooms
init|=
literal|null
decl_stmt|;
specifier|public
name|RoomFeatureEdit
parameter_list|(
name|RoomPropertiesInterface
name|properties
parameter_list|)
block|{
name|iProperties
operator|=
name|properties
expr_stmt|;
name|iForm
operator|=
operator|new
name|SimpleForm
argument_list|()
expr_stmt|;
name|iForm
operator|.
name|addStyleName
argument_list|(
literal|"unitime-RoomDepartmentsEdit"
argument_list|)
expr_stmt|;
name|iHeader
operator|=
operator|new
name|UniTimeHeaderPanel
argument_list|()
expr_stmt|;
name|ClickHandler
name|createOrUpdateFeature
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
if|if
condition|(
name|validate
argument_list|()
condition|)
block|{
name|UpdateRoomFeatureRequest
name|request
init|=
operator|new
name|UpdateRoomFeatureRequest
argument_list|()
decl_stmt|;
name|request
operator|.
name|setFeature
argument_list|(
name|iFeature
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|1
init|;
name|i
operator|<
name|iRooms
operator|.
name|getRowCount
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|RoomDetailInterface
name|room
init|=
name|iRooms
operator|.
name|getData
argument_list|(
name|i
argument_list|)
decl_stmt|;
name|boolean
name|wasSelected
init|=
operator|(
name|iFeature
operator|.
name|getRoom
argument_list|(
name|room
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|!=
literal|null
operator|)
decl_stmt|;
name|boolean
name|selected
init|=
name|iRooms
operator|.
name|isRoomSelected
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
name|selected
operator|!=
name|wasSelected
condition|)
block|{
if|if
condition|(
name|selected
condition|)
name|request
operator|.
name|addLocation
argument_list|(
name|room
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
else|else
name|request
operator|.
name|dropLocation
argument_list|(
name|room
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
name|LoadingWidget
operator|.
name|getInstance
argument_list|()
operator|.
name|show
argument_list|(
name|iFeature
operator|.
name|getId
argument_list|()
operator|==
literal|null
condition|?
name|MESSAGES
operator|.
name|waitSavingRoomFeature
argument_list|()
else|:
name|MESSAGES
operator|.
name|waitUpdatingRoomFeature
argument_list|()
argument_list|)
expr_stmt|;
name|RPC
operator|.
name|execute
argument_list|(
name|request
argument_list|,
operator|new
name|AsyncCallback
argument_list|<
name|FeatureInterface
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
name|String
name|message
init|=
operator|(
name|iFeature
operator|.
name|getId
argument_list|()
operator|==
literal|null
condition|?
name|MESSAGES
operator|.
name|errorFailedToSaveRoomFeature
argument_list|(
name|caught
operator|.
name|getMessage
argument_list|()
argument_list|)
else|:
name|MESSAGES
operator|.
name|errorFailedToUpdateRoomFeature
argument_list|(
name|caught
operator|.
name|getMessage
argument_list|()
argument_list|)
operator|)
decl_stmt|;
name|iHeader
operator|.
name|setErrorMessage
argument_list|(
name|message
argument_list|)
expr_stmt|;
name|UniTimeNotifications
operator|.
name|error
argument_list|(
name|message
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|onSuccess
parameter_list|(
name|FeatureInterface
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
name|hide
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|iHeader
operator|.
name|setErrorMessage
argument_list|(
name|MESSAGES
operator|.
name|failedValidationCheckForm
argument_list|()
argument_list|)
expr_stmt|;
name|UniTimeNotifications
operator|.
name|error
argument_list|(
name|MESSAGES
operator|.
name|failedValidationCheckForm
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
decl_stmt|;
name|iHeader
operator|.
name|addButton
argument_list|(
literal|"create"
argument_list|,
name|MESSAGES
operator|.
name|buttonCreateRoomFeature
argument_list|()
argument_list|,
literal|100
argument_list|,
name|createOrUpdateFeature
argument_list|)
expr_stmt|;
name|iHeader
operator|.
name|addButton
argument_list|(
literal|"update"
argument_list|,
name|MESSAGES
operator|.
name|buttonUpdateRoomFeature
argument_list|()
argument_list|,
literal|100
argument_list|,
name|createOrUpdateFeature
argument_list|)
expr_stmt|;
name|iHeader
operator|.
name|addButton
argument_list|(
literal|"delete"
argument_list|,
name|MESSAGES
operator|.
name|buttonDeleteRoomFeature
argument_list|()
argument_list|,
literal|100
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
if|if
condition|(
name|Window
operator|.
name|confirm
argument_list|(
name|MESSAGES
operator|.
name|confirmDeleteRoomFeature
argument_list|()
argument_list|)
condition|)
block|{
name|UpdateRoomFeatureRequest
name|request
init|=
operator|new
name|UpdateRoomFeatureRequest
argument_list|()
decl_stmt|;
name|request
operator|.
name|setDeleteFeatureId
argument_list|(
name|iFeature
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
name|LoadingWidget
operator|.
name|getInstance
argument_list|()
operator|.
name|show
argument_list|(
name|MESSAGES
operator|.
name|waitDeletingRoomFeature
argument_list|()
argument_list|)
expr_stmt|;
name|RPC
operator|.
name|execute
argument_list|(
name|request
argument_list|,
operator|new
name|AsyncCallback
argument_list|<
name|FeatureInterface
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
name|String
name|message
init|=
name|MESSAGES
operator|.
name|errorFailedToDeleteRoomFeature
argument_list|(
name|caught
operator|.
name|getMessage
argument_list|()
argument_list|)
decl_stmt|;
name|iHeader
operator|.
name|setErrorMessage
argument_list|(
name|message
argument_list|)
expr_stmt|;
name|UniTimeNotifications
operator|.
name|error
argument_list|(
name|message
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|onSuccess
parameter_list|(
name|FeatureInterface
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
name|hide
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
block|}
block|}
argument_list|)
expr_stmt|;
name|iHeader
operator|.
name|addButton
argument_list|(
literal|"back"
argument_list|,
name|MESSAGES
operator|.
name|buttonBack
argument_list|()
argument_list|,
literal|100
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
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|iForm
operator|.
name|addHeaderRow
argument_list|(
name|iHeader
argument_list|)
expr_stmt|;
name|iName
operator|=
operator|new
name|UniTimeWidget
argument_list|<
name|TextBox
argument_list|>
argument_list|(
operator|new
name|TextBox
argument_list|()
argument_list|)
expr_stmt|;
name|iName
operator|.
name|getWidget
argument_list|()
operator|.
name|setStyleName
argument_list|(
literal|"unitime-TextBox"
argument_list|)
expr_stmt|;
name|iName
operator|.
name|getWidget
argument_list|()
operator|.
name|setMaxLength
argument_list|(
literal|60
argument_list|)
expr_stmt|;
name|iName
operator|.
name|getWidget
argument_list|()
operator|.
name|setWidth
argument_list|(
literal|"370px"
argument_list|)
expr_stmt|;
name|iName
operator|.
name|getWidget
argument_list|()
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
name|iName
operator|.
name|clearHint
argument_list|()
expr_stmt|;
name|iHeader
operator|.
name|clearMessage
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|iForm
operator|.
name|addRow
argument_list|(
name|MESSAGES
operator|.
name|propName
argument_list|()
argument_list|,
name|iName
argument_list|)
expr_stmt|;
name|iAbbreviation
operator|=
operator|new
name|UniTimeWidget
argument_list|<
name|TextBox
argument_list|>
argument_list|(
operator|new
name|TextBox
argument_list|()
argument_list|)
expr_stmt|;
name|iAbbreviation
operator|.
name|getWidget
argument_list|()
operator|.
name|setStyleName
argument_list|(
literal|"unitime-TextBox"
argument_list|)
expr_stmt|;
name|iAbbreviation
operator|.
name|getWidget
argument_list|()
operator|.
name|setMaxLength
argument_list|(
literal|60
argument_list|)
expr_stmt|;
name|iAbbreviation
operator|.
name|getWidget
argument_list|()
operator|.
name|setWidth
argument_list|(
literal|"370px"
argument_list|)
expr_stmt|;
name|iAbbreviation
operator|.
name|getWidget
argument_list|()
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
name|iAbbreviation
operator|.
name|clearHint
argument_list|()
expr_stmt|;
name|iHeader
operator|.
name|clearMessage
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|iForm
operator|.
name|addRow
argument_list|(
name|MESSAGES
operator|.
name|propAbbreviation
argument_list|()
argument_list|,
name|iAbbreviation
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|iProperties
operator|.
name|getFeatureTypes
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|iType
operator|=
operator|new
name|ListBox
argument_list|()
expr_stmt|;
name|iType
operator|.
name|addItem
argument_list|(
name|MESSAGES
operator|.
name|itemNoFeatureType
argument_list|()
argument_list|,
literal|"-1"
argument_list|)
expr_stmt|;
for|for
control|(
name|FeatureTypeInterface
name|type
range|:
name|iProperties
operator|.
name|getFeatureTypes
argument_list|()
control|)
name|iType
operator|.
name|addItem
argument_list|(
name|type
operator|.
name|getLabel
argument_list|()
argument_list|,
name|type
operator|.
name|getId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|iForm
operator|.
name|addRow
argument_list|(
name|MESSAGES
operator|.
name|propFeatureType
argument_list|()
argument_list|,
name|iType
argument_list|)
expr_stmt|;
block|}
name|iGlobal
operator|=
operator|new
name|CheckBox
argument_list|()
expr_stmt|;
name|iForm
operator|.
name|addRow
argument_list|(
name|MESSAGES
operator|.
name|propGlobalFeature
argument_list|()
argument_list|,
name|iGlobal
argument_list|)
expr_stmt|;
name|iGlobal
operator|.
name|addValueChangeHandler
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
name|iForm
operator|.
name|getRowFormatter
argument_list|()
operator|.
name|setVisible
argument_list|(
name|iDepartmentRow
argument_list|,
operator|!
name|event
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|iDepartment
operator|=
operator|new
name|UniTimeWidget
argument_list|<
name|ListBox
argument_list|>
argument_list|(
operator|new
name|ListBox
argument_list|()
argument_list|)
expr_stmt|;
name|iDepartment
operator|.
name|getWidget
argument_list|()
operator|.
name|setStyleName
argument_list|(
literal|"unitime-TextBox"
argument_list|)
expr_stmt|;
name|iDepartmentRow
operator|=
name|iForm
operator|.
name|addRow
argument_list|(
name|MESSAGES
operator|.
name|propDepartment
argument_list|()
argument_list|,
name|iDepartment
argument_list|)
expr_stmt|;
name|iForm
operator|.
name|addHeaderRow
argument_list|(
name|MESSAGES
operator|.
name|headerRooms
argument_list|()
argument_list|)
expr_stmt|;
name|iRooms
operator|=
operator|new
name|RoomsTable
argument_list|(
name|RoomsPageMode
operator|.
name|COURSES
argument_list|,
name|iProperties
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|iForm
operator|.
name|addRow
argument_list|(
name|iRooms
argument_list|)
expr_stmt|;
name|iRooms
operator|.
name|addMouseClickListener
argument_list|(
operator|new
name|MouseClickListener
argument_list|<
name|RoomDetailInterface
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
name|RoomDetailInterface
argument_list|>
name|event
parameter_list|)
block|{
name|iHeader
operator|.
name|clearMessage
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|iFooter
operator|=
name|iHeader
operator|.
name|clonePanel
argument_list|()
expr_stmt|;
name|iForm
operator|.
name|addBottomRow
argument_list|(
name|iFooter
argument_list|)
expr_stmt|;
name|initWidget
argument_list|(
name|iForm
argument_list|)
expr_stmt|;
block|}
specifier|private
name|void
name|hide
parameter_list|(
name|boolean
name|refresh
parameter_list|)
block|{
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|onHide
argument_list|(
name|refresh
argument_list|)
expr_stmt|;
name|Window
operator|.
name|scrollTo
argument_list|(
name|iLastScrollLeft
argument_list|,
name|iLastScrollTop
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|onHide
parameter_list|(
name|boolean
name|refresh
parameter_list|)
block|{
block|}
specifier|protected
name|void
name|onShow
parameter_list|()
block|{
block|}
specifier|private
name|int
name|iLastScrollTop
decl_stmt|,
name|iLastScrollLeft
decl_stmt|;
specifier|public
name|void
name|show
parameter_list|()
block|{
name|UniTimePageLabel
operator|.
name|getInstance
argument_list|()
operator|.
name|setPageName
argument_list|(
name|iFeature
operator|.
name|getId
argument_list|()
operator|==
literal|null
condition|?
name|MESSAGES
operator|.
name|pageAddRoomFeature
argument_list|()
else|:
name|MESSAGES
operator|.
name|pageEditRoomFeature
argument_list|()
argument_list|)
expr_stmt|;
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|iLastScrollLeft
operator|=
name|Window
operator|.
name|getScrollLeft
argument_list|()
expr_stmt|;
name|iLastScrollTop
operator|=
name|Window
operator|.
name|getScrollTop
argument_list|()
expr_stmt|;
name|onShow
argument_list|()
expr_stmt|;
name|Window
operator|.
name|scrollTo
argument_list|(
literal|0
argument_list|,
literal|0
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|setFeature
parameter_list|(
name|FeatureInterface
name|feature
parameter_list|,
name|String
name|dept
parameter_list|)
block|{
name|iHeader
operator|.
name|clearMessage
argument_list|()
expr_stmt|;
name|iName
operator|.
name|clearHint
argument_list|()
expr_stmt|;
name|iAbbreviation
operator|.
name|clearHint
argument_list|()
expr_stmt|;
name|iDepartment
operator|.
name|clearHint
argument_list|()
expr_stmt|;
if|if
condition|(
name|feature
operator|==
literal|null
condition|)
block|{
name|iFeature
operator|=
operator|new
name|FeatureInterface
argument_list|()
expr_stmt|;
name|iHeader
operator|.
name|setEnabled
argument_list|(
literal|"create"
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|iHeader
operator|.
name|setEnabled
argument_list|(
literal|"update"
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|iHeader
operator|.
name|setEnabled
argument_list|(
literal|"delete"
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|iName
operator|.
name|getWidget
argument_list|()
operator|.
name|setText
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|iAbbreviation
operator|.
name|getWidget
argument_list|()
operator|.
name|setText
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|iDepartment
operator|.
name|getWidget
argument_list|()
operator|.
name|clear
argument_list|()
expr_stmt|;
name|iGlobal
operator|.
name|setValue
argument_list|(
literal|true
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|iGlobal
operator|.
name|setEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
if|if
condition|(
name|iType
operator|!=
literal|null
condition|)
name|iType
operator|.
name|setSelectedIndex
argument_list|(
literal|0
argument_list|)
expr_stmt|;
if|if
condition|(
name|iProperties
operator|.
name|isCanAddDepartmentalRoomFeature
argument_list|()
condition|)
block|{
name|iDepartment
operator|.
name|getWidget
argument_list|()
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
name|iDepartment
operator|.
name|getWidget
argument_list|()
operator|.
name|setSelectedIndex
argument_list|(
literal|0
argument_list|)
expr_stmt|;
for|for
control|(
name|DepartmentInterface
name|department
range|:
name|iProperties
operator|.
name|getDepartments
argument_list|()
control|)
block|{
name|iDepartment
operator|.
name|getWidget
argument_list|()
operator|.
name|addItem
argument_list|(
name|department
operator|.
name|getExtAbbreviationOrCode
argument_list|()
operator|+
literal|" - "
operator|+
name|department
operator|.
name|getExtLabelWhenExist
argument_list|()
argument_list|,
name|department
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
name|dept
operator|!=
literal|null
operator|&&
name|dept
operator|.
name|equals
argument_list|(
name|department
operator|.
name|getDeptCode
argument_list|()
argument_list|)
condition|)
block|{
name|iDepartment
operator|.
name|getWidget
argument_list|()
operator|.
name|setSelectedIndex
argument_list|(
name|iDepartment
operator|.
name|getWidget
argument_list|()
operator|.
name|getItemCount
argument_list|()
operator|-
literal|1
argument_list|)
expr_stmt|;
name|iGlobal
operator|.
name|setValue
argument_list|(
literal|false
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
operator|!
name|iProperties
operator|.
name|isCanAddGlobalRoomFeature
argument_list|()
condition|)
block|{
name|iGlobal
operator|.
name|setValue
argument_list|(
literal|false
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|iGlobal
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
operator|!
name|iProperties
operator|.
name|isCanAddDepartmentalRoomGroup
argument_list|()
condition|)
block|{
name|iGlobal
operator|.
name|setValue
argument_list|(
literal|true
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|iGlobal
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|iFeature
operator|=
operator|new
name|FeatureInterface
argument_list|(
name|feature
argument_list|)
expr_stmt|;
name|iHeader
operator|.
name|setEnabled
argument_list|(
literal|"create"
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|iHeader
operator|.
name|setEnabled
argument_list|(
literal|"update"
argument_list|,
name|feature
operator|.
name|canEdit
argument_list|()
argument_list|)
expr_stmt|;
name|iHeader
operator|.
name|setEnabled
argument_list|(
literal|"delete"
argument_list|,
name|feature
operator|.
name|canDelete
argument_list|()
argument_list|)
expr_stmt|;
name|iName
operator|.
name|getWidget
argument_list|()
operator|.
name|setText
argument_list|(
name|feature
operator|.
name|getLabel
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|feature
operator|.
name|getLabel
argument_list|()
argument_list|)
expr_stmt|;
name|iAbbreviation
operator|.
name|getWidget
argument_list|()
operator|.
name|setText
argument_list|(
name|feature
operator|.
name|getAbbreviation
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|feature
operator|.
name|getAbbreviation
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|iType
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|feature
operator|.
name|getType
argument_list|()
operator|==
literal|null
condition|)
block|{
name|iType
operator|.
name|setSelectedIndex
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|iType
operator|.
name|setSelectedIndex
argument_list|(
literal|1
operator|+
name|iProperties
operator|.
name|getFeatureTypes
argument_list|()
operator|.
name|indexOf
argument_list|(
name|feature
operator|.
name|getType
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
name|iGlobal
operator|.
name|setValue
argument_list|(
operator|!
name|feature
operator|.
name|isDepartmental
argument_list|()
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|iGlobal
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|iDepartment
operator|.
name|getWidget
argument_list|()
operator|.
name|clear
argument_list|()
expr_stmt|;
if|if
condition|(
name|feature
operator|.
name|isDepartmental
argument_list|()
condition|)
block|{
for|for
control|(
name|DepartmentInterface
name|department
range|:
name|iProperties
operator|.
name|getDepartments
argument_list|()
control|)
name|iDepartment
operator|.
name|getWidget
argument_list|()
operator|.
name|addItem
argument_list|(
name|department
operator|.
name|getExtAbbreviationOrCode
argument_list|()
operator|+
literal|" - "
operator|+
name|department
operator|.
name|getExtLabelWhenExist
argument_list|()
argument_list|,
name|department
operator|.
name|getId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|int
name|index
init|=
name|iProperties
operator|.
name|getDepartments
argument_list|()
operator|.
name|indexOf
argument_list|(
name|feature
operator|.
name|getDepartment
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|index
operator|>=
literal|0
condition|)
block|{
name|iDepartment
operator|.
name|getWidget
argument_list|()
operator|.
name|setSelectedIndex
argument_list|(
literal|1
operator|+
name|index
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|iDepartment
operator|.
name|getWidget
argument_list|()
operator|.
name|addItem
argument_list|(
name|feature
operator|.
name|getDepartment
argument_list|()
operator|.
name|getExtAbbreviationOrCode
argument_list|()
operator|+
literal|" - "
operator|+
name|feature
operator|.
name|getDepartment
argument_list|()
operator|.
name|getExtLabelWhenExist
argument_list|()
argument_list|,
name|feature
operator|.
name|getDepartment
argument_list|()
operator|.
name|getId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|iDepartment
operator|.
name|getWidget
argument_list|()
operator|.
name|setSelectedIndex
argument_list|(
name|iDepartment
operator|.
name|getWidget
argument_list|()
operator|.
name|getItemCount
argument_list|()
operator|-
literal|1
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
specifier|public
name|void
name|setRooms
parameter_list|(
name|List
argument_list|<
name|Entity
argument_list|>
name|rooms
parameter_list|)
block|{
name|iRooms
operator|.
name|clearTable
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|iHeader
operator|.
name|clearMessage
argument_list|()
expr_stmt|;
name|ValueChangeHandler
argument_list|<
name|Boolean
argument_list|>
name|clearErrorMessage
init|=
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
name|iHeader
operator|.
name|clearMessage
argument_list|()
expr_stmt|;
block|}
block|}
decl_stmt|;
for|for
control|(
name|Entity
name|e
range|:
name|rooms
control|)
block|{
name|RoomDetailInterface
name|room
init|=
operator|(
name|RoomDetailInterface
operator|)
name|e
decl_stmt|;
name|int
name|row
init|=
name|iRooms
operator|.
name|addRoom
argument_list|(
name|room
argument_list|)
decl_stmt|;
name|boolean
name|selected
init|=
operator|(
name|iFeature
operator|.
name|getRoom
argument_list|(
name|room
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|!=
literal|null
operator|)
decl_stmt|;
name|iRooms
operator|.
name|selectRoom
argument_list|(
name|row
argument_list|,
name|selected
argument_list|)
expr_stmt|;
name|iRooms
operator|.
name|setSelected
argument_list|(
name|row
argument_list|,
name|selected
argument_list|)
expr_stmt|;
name|iRooms
operator|.
name|getRoomSelection
argument_list|(
name|row
argument_list|)
operator|.
name|addValueChangeHandler
argument_list|(
name|clearErrorMessage
argument_list|)
expr_stmt|;
block|}
name|int
name|sort
init|=
name|RoomCookie
operator|.
name|getInstance
argument_list|()
operator|.
name|getRoomsSortBy
argument_list|()
decl_stmt|;
if|if
condition|(
name|sort
operator|!=
literal|0
condition|)
name|iRooms
operator|.
name|setSortBy
argument_list|(
name|sort
argument_list|)
expr_stmt|;
block|}
specifier|public
name|boolean
name|validate
parameter_list|()
block|{
name|boolean
name|result
init|=
literal|true
decl_stmt|;
name|iFeature
operator|.
name|setLabel
argument_list|(
name|iName
operator|.
name|getWidget
argument_list|()
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|iFeature
operator|.
name|getLabel
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|iName
operator|.
name|setErrorHint
argument_list|(
name|MESSAGES
operator|.
name|errorNameIsEmpty
argument_list|()
argument_list|)
expr_stmt|;
name|result
operator|=
literal|false
expr_stmt|;
block|}
name|iFeature
operator|.
name|setAbbreviation
argument_list|(
name|iAbbreviation
operator|.
name|getWidget
argument_list|()
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|iFeature
operator|.
name|getAbbreviation
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|iAbbreviation
operator|.
name|setErrorHint
argument_list|(
name|MESSAGES
operator|.
name|errorAbbreviationIsEmpty
argument_list|()
argument_list|)
expr_stmt|;
name|result
operator|=
literal|false
expr_stmt|;
block|}
if|if
condition|(
name|iType
operator|!=
literal|null
condition|)
block|{
name|iFeature
operator|.
name|setType
argument_list|(
name|iProperties
operator|.
name|getFeatureType
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|iType
operator|.
name|getValue
argument_list|(
name|iType
operator|.
name|getSelectedIndex
argument_list|()
argument_list|)
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|iFeature
operator|.
name|setType
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|iFeature
operator|.
name|getId
argument_list|()
operator|==
literal|null
condition|)
block|{
if|if
condition|(
operator|!
name|iGlobal
operator|.
name|getValue
argument_list|()
condition|)
block|{
name|iFeature
operator|.
name|setDepartment
argument_list|(
name|iProperties
operator|.
name|getDepartment
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|iDepartment
operator|.
name|getWidget
argument_list|()
operator|.
name|getValue
argument_list|(
name|iDepartment
operator|.
name|getWidget
argument_list|()
operator|.
name|getSelectedIndex
argument_list|()
argument_list|)
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|iFeature
operator|.
name|getDepartment
argument_list|()
operator|==
literal|null
condition|)
block|{
name|iDepartment
operator|.
name|setErrorHint
argument_list|(
name|MESSAGES
operator|.
name|errorNoDepartmentSelected
argument_list|()
argument_list|)
expr_stmt|;
name|result
operator|=
literal|false
expr_stmt|;
block|}
block|}
else|else
block|{
name|iFeature
operator|.
name|setDepartment
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|result
return|;
block|}
block|}
end_class

end_unit

