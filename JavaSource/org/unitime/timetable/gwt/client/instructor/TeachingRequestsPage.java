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
name|RoomCookie
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
name|AssignmentInfo
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
name|SubjectAreaInterface
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
name|TeachingRequestInfo
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
name|TeachingRequestsPagePropertiesRequest
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
name|TeachingRequestsPagePropertiesResponse
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
name|TeachingRequestsPageRequest
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
name|Window
operator|.
name|Location
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
name|ListBox
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|TeachingRequestsPage
extends|extends
name|SimpleForm
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
specifier|protected
specifier|static
specifier|final
name|StudentSectioningMessages
name|SECTMSG
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
specifier|private
name|boolean
name|iAssigned
init|=
literal|true
decl_stmt|;
specifier|private
name|UniTimeHeaderPanel
name|iFilterPanel
decl_stmt|;
specifier|private
name|ListBox
name|iFilter
decl_stmt|;
specifier|private
name|TeachingRequestsTable
name|iTable
decl_stmt|;
specifier|public
name|TeachingRequestsPage
parameter_list|()
block|{
name|iAssigned
operator|=
literal|"true"
operator|.
name|equalsIgnoreCase
argument_list|(
name|Location
operator|.
name|getParameter
argument_list|(
literal|"assigned"
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|iAssigned
condition|)
name|UniTimePageLabel
operator|.
name|getInstance
argument_list|()
operator|.
name|setPageName
argument_list|(
name|MESSAGES
operator|.
name|pageAssignedTeachingRequests
argument_list|()
argument_list|)
expr_stmt|;
else|else
name|UniTimePageLabel
operator|.
name|getInstance
argument_list|()
operator|.
name|setPageName
argument_list|(
name|MESSAGES
operator|.
name|pageUnassignedTeachingRequests
argument_list|()
argument_list|)
expr_stmt|;
name|iFilterPanel
operator|=
operator|new
name|UniTimeHeaderPanel
argument_list|(
name|MESSAGES
operator|.
name|propSubjectArea
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
name|insertLeft
argument_list|(
name|iFilter
argument_list|,
literal|false
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
name|iFilterPanel
operator|.
name|setEnabled
argument_list|(
literal|"search"
argument_list|,
name|iFilter
operator|.
name|getSelectedIndex
argument_list|()
operator|>
literal|0
argument_list|)
expr_stmt|;
name|iFilterPanel
operator|.
name|setEnabled
argument_list|(
literal|"csv"
argument_list|,
name|iFilter
operator|.
name|getSelectedIndex
argument_list|()
operator|>
literal|0
argument_list|)
expr_stmt|;
name|iFilterPanel
operator|.
name|setEnabled
argument_list|(
literal|"pdf"
argument_list|,
name|iFilter
operator|.
name|getSelectedIndex
argument_list|()
operator|>
literal|0
argument_list|)
expr_stmt|;
block|}
block|}
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
argument_list|()
expr_stmt|;
block|}
block|}
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
name|addButton
argument_list|(
literal|"csv"
argument_list|,
name|MESSAGES
operator|.
name|buttonExportCSV
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
name|export
argument_list|(
literal|"teaching-requests.csv"
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|iFilterPanel
operator|.
name|setEnabled
argument_list|(
literal|"csv"
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|iFilterPanel
operator|.
name|addButton
argument_list|(
literal|"pdf"
argument_list|,
name|MESSAGES
operator|.
name|buttonExportPDF
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
name|export
argument_list|(
literal|"teaching-requests.pdf"
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|iFilterPanel
operator|.
name|setEnabled
argument_list|(
literal|"pdf"
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|addRow
argument_list|(
name|iFilterPanel
argument_list|)
expr_stmt|;
name|iTable
operator|=
operator|new
name|TeachingRequestsTable
argument_list|(
name|iAssigned
argument_list|)
block|{
annotation|@
name|Override
specifier|protected
name|void
name|onAssignmentChanged
parameter_list|(
name|List
argument_list|<
name|AssignmentInfo
argument_list|>
name|assignments
parameter_list|)
block|{
if|if
condition|(
name|iTable
operator|.
name|isVisible
argument_list|()
condition|)
name|search
argument_list|()
expr_stmt|;
block|}
block|}
expr_stmt|;
name|iTable
operator|.
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|addRow
argument_list|(
name|iTable
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
name|waitLoadingPage
argument_list|()
argument_list|)
expr_stmt|;
name|RPC
operator|.
name|execute
argument_list|(
operator|new
name|TeachingRequestsPagePropertiesRequest
argument_list|()
argument_list|,
operator|new
name|AsyncCallback
argument_list|<
name|TeachingRequestsPagePropertiesResponse
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
name|TeachingRequestsPagePropertiesResponse
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
name|iTable
operator|.
name|setProperties
argument_list|(
name|result
argument_list|)
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
literal|""
argument_list|)
expr_stmt|;
name|iFilter
operator|.
name|addItem
argument_list|(
name|MESSAGES
operator|.
name|itemAll
argument_list|()
argument_list|,
literal|"-1"
argument_list|)
expr_stmt|;
name|iFilter
operator|.
name|setSelectedIndex
argument_list|(
name|result
operator|.
name|getLastSubjectAreaId
argument_list|()
operator|!=
literal|null
operator|&&
name|result
operator|.
name|getLastSubjectAreaId
argument_list|()
operator|==
operator|-
literal|1l
condition|?
literal|1
else|:
literal|0
argument_list|)
expr_stmt|;
for|for
control|(
name|SubjectAreaInterface
name|s
range|:
name|result
operator|.
name|getSubjectAreas
argument_list|()
control|)
block|{
name|iFilter
operator|.
name|addItem
argument_list|(
name|s
operator|.
name|getAbbreviation
argument_list|()
argument_list|,
name|s
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
name|s
operator|.
name|getId
argument_list|()
operator|.
name|equals
argument_list|(
name|result
operator|.
name|getLastSubjectAreaId
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
name|iFilterPanel
operator|.
name|setEnabled
argument_list|(
literal|"search"
argument_list|,
name|iFilter
operator|.
name|getSelectedIndex
argument_list|()
operator|>
literal|0
argument_list|)
expr_stmt|;
name|iFilterPanel
operator|.
name|setEnabled
argument_list|(
literal|"csv"
argument_list|,
name|iFilter
operator|.
name|getSelectedIndex
argument_list|()
operator|>
literal|0
argument_list|)
expr_stmt|;
name|iFilterPanel
operator|.
name|setEnabled
argument_list|(
literal|"pdf"
argument_list|,
name|iFilter
operator|.
name|getSelectedIndex
argument_list|()
operator|>
literal|0
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
name|void
name|search
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
name|waitLoadingTeachingRequests
argument_list|()
argument_list|)
expr_stmt|;
name|RPC
operator|.
name|execute
argument_list|(
operator|new
name|TeachingRequestsPageRequest
argument_list|(
name|iFilter
operator|.
name|getSelectedIndex
argument_list|()
operator|<=
literal|1
condition|?
literal|null
else|:
name|Long
operator|.
name|valueOf
argument_list|(
name|iFilter
operator|.
name|getSelectedValue
argument_list|()
argument_list|)
argument_list|,
name|iAssigned
argument_list|)
argument_list|,
operator|new
name|AsyncCallback
argument_list|<
name|GwtRpcResponseList
argument_list|<
name|TeachingRequestInfo
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
name|failedToLoadTeachingRequests
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
name|failedToLoadTeachingRequests
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
name|TeachingRequestInfo
argument_list|>
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
name|iTable
operator|.
name|populate
argument_list|(
name|result
argument_list|)
expr_stmt|;
name|iTable
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
name|void
name|export
parameter_list|(
name|String
name|type
parameter_list|)
block|{
name|RoomCookie
name|cookie
init|=
name|RoomCookie
operator|.
name|getInstance
argument_list|()
decl_stmt|;
name|String
name|query
init|=
literal|"output="
operator|+
name|type
operator|+
literal|"&assigned="
operator|+
name|iAssigned
operator|+
operator|(
name|iFilter
operator|.
name|getSelectedIndex
argument_list|()
operator|<=
literal|1
condition|?
literal|""
else|:
literal|"&subjectId="
operator|+
name|Long
operator|.
name|valueOf
argument_list|(
name|iFilter
operator|.
name|getSelectedValue
argument_list|()
argument_list|)
operator|)
operator|+
literal|"&sort="
operator|+
name|InstructorCookie
operator|.
name|getInstance
argument_list|()
operator|.
name|getSortTeachingRequestsBy
argument_list|(
name|iAssigned
argument_list|)
operator|+
literal|"&columns="
operator|+
name|InstructorCookie
operator|.
name|getInstance
argument_list|()
operator|.
name|getTeachingRequestsColumns
argument_list|(
name|iAssigned
argument_list|)
operator|+
literal|"&grid="
operator|+
operator|(
name|cookie
operator|.
name|isGridAsText
argument_list|()
condition|?
literal|"0"
else|:
literal|"1"
operator|)
operator|+
literal|"&vertical="
operator|+
operator|(
name|cookie
operator|.
name|areRoomsHorizontal
argument_list|()
condition|?
literal|"0"
else|:
literal|"1"
operator|)
operator|+
operator|(
name|cookie
operator|.
name|hasMode
argument_list|()
condition|?
literal|"&mode="
operator|+
name|cookie
operator|.
name|getMode
argument_list|()
else|:
literal|""
operator|)
decl_stmt|;
name|RPC
operator|.
name|execute
argument_list|(
name|EncodeQueryRpcRequest
operator|.
name|encode
argument_list|(
name|query
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
block|}
end_class

end_unit

