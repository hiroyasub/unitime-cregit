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
package|;
end_package

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
name|ToolBox
operator|.
name|Page
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
name|CourseTimetablingSolverInterface
operator|.
name|AssignmentHistoryFilterRequest
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
name|CourseTimetablingSolverInterface
operator|.
name|AssignmentHistoryFilterResponse
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
name|CourseTimetablingSolverInterface
operator|.
name|AssignmentHistoryRequest
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
name|CourseTimetablingSolverInterface
operator|.
name|AssignmentHistoryResponse
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
name|FilterInterface
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
name|SolverInterface
operator|.
name|PageMessage
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
name|SolverInterface
operator|.
name|PageMessageType
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
name|DOM
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
name|History
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
name|AssignmentHistoryPage
extends|extends
name|Composite
block|{
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
name|PageFilter
name|iFilter
decl_stmt|;
specifier|private
name|SimplePanel
name|iRootPanel
decl_stmt|;
specifier|private
name|SimpleForm
name|iPanel
decl_stmt|;
specifier|private
name|FilterInterface
name|iLastFilter
decl_stmt|;
specifier|private
name|AssignmentHistoryResponse
name|iLastResponse
decl_stmt|;
specifier|private
name|DataTable
name|iTable
decl_stmt|;
specifier|private
name|PreferenceLegend
name|iLegend
decl_stmt|;
specifier|public
name|AssignmentHistoryPage
parameter_list|()
block|{
name|iFilter
operator|=
operator|new
name|PageFilter
argument_list|()
expr_stmt|;
name|iFilter
operator|.
name|getHeader
argument_list|()
operator|.
name|setCollapsible
argument_list|(
name|SolverCookie
operator|.
name|getInstance
argument_list|()
operator|.
name|isAssignmentHistoryFilter
argument_list|()
argument_list|)
expr_stmt|;
name|iFilter
operator|.
name|getHeader
argument_list|()
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
name|SolverCookie
operator|.
name|getInstance
argument_list|()
operator|.
name|setAssignmentHistoryFilter
argument_list|(
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
name|iPanel
operator|=
operator|new
name|SimpleForm
argument_list|(
literal|2
argument_list|)
expr_stmt|;
name|iPanel
operator|.
name|removeStyleName
argument_list|(
literal|"unitime-NotPrintableBottomLine"
argument_list|)
expr_stmt|;
name|iPanel
operator|.
name|addRow
argument_list|(
name|iFilter
argument_list|)
expr_stmt|;
name|iFilter
operator|.
name|getFooter
argument_list|()
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
name|String
name|token
init|=
name|iFilter
operator|.
name|getQuery
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|History
operator|.
name|getToken
argument_list|()
operator|.
name|equals
argument_list|(
name|token
argument_list|)
condition|)
name|History
operator|.
name|newItem
argument_list|(
name|token
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|search
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|iFilter
operator|.
name|getFooter
argument_list|()
operator|.
name|setEnabled
argument_list|(
literal|"search"
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|iFilter
operator|.
name|getFooter
argument_list|()
operator|.
name|addButton
argument_list|(
literal|"print"
argument_list|,
name|MESSAGES
operator|.
name|buttonPrint
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
name|print
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|iFilter
operator|.
name|getFooter
argument_list|()
operator|.
name|setEnabled
argument_list|(
literal|"print"
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|iFilter
operator|.
name|getFooter
argument_list|()
operator|.
name|addButton
argument_list|(
literal|"exportCSV"
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
name|exportData
argument_list|(
literal|"csv"
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|iFilter
operator|.
name|getFooter
argument_list|()
operator|.
name|setEnabled
argument_list|(
literal|"exportCSV"
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|iFilter
operator|.
name|getFooter
argument_list|()
operator|.
name|addButton
argument_list|(
literal|"exportPDF"
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
name|exportData
argument_list|(
literal|"pdf"
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|iFilter
operator|.
name|getFooter
argument_list|()
operator|.
name|setEnabled
argument_list|(
literal|"exportPDF"
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|iRootPanel
operator|=
operator|new
name|SimplePanel
argument_list|(
name|iPanel
argument_list|)
expr_stmt|;
name|iRootPanel
operator|.
name|addStyleName
argument_list|(
literal|"unitime-AssignmentHistoryPage"
argument_list|)
expr_stmt|;
name|initWidget
argument_list|(
name|iRootPanel
argument_list|)
expr_stmt|;
name|init
argument_list|()
expr_stmt|;
name|History
operator|.
name|addValueChangeHandler
argument_list|(
operator|new
name|ValueChangeHandler
argument_list|<
name|String
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
name|String
argument_list|>
name|event
parameter_list|)
block|{
name|iFilter
operator|.
name|setQuery
argument_list|(
name|event
operator|.
name|getValue
argument_list|()
argument_list|,
literal|true
argument_list|)
expr_stmt|;
if|if
condition|(
name|iPanel
operator|.
name|getRowCount
argument_list|()
operator|>
literal|1
condition|)
name|search
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|init
parameter_list|()
block|{
name|RPC
operator|.
name|execute
argument_list|(
operator|new
name|AssignmentHistoryFilterRequest
argument_list|()
argument_list|,
operator|new
name|AsyncCallback
argument_list|<
name|AssignmentHistoryFilterResponse
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
name|iFilter
operator|.
name|getFooter
argument_list|()
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
name|AssignmentHistoryFilterResponse
name|result
parameter_list|)
block|{
name|iLegend
operator|=
operator|new
name|PreferenceLegend
argument_list|(
name|result
operator|.
name|getPreferences
argument_list|()
argument_list|)
expr_stmt|;
name|iFilter
operator|.
name|getFooter
argument_list|()
operator|.
name|clearMessage
argument_list|()
expr_stmt|;
name|iFilter
operator|.
name|setValue
argument_list|(
name|result
argument_list|)
expr_stmt|;
name|iFilter
operator|.
name|getFooter
argument_list|()
operator|.
name|setEnabled
argument_list|(
literal|"search"
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|createTriggers
argument_list|()
expr_stmt|;
if|if
condition|(
name|iFilter
operator|.
name|getHeader
argument_list|()
operator|.
name|isCollapsible
argument_list|()
operator|!=
literal|null
operator|&&
operator|!
name|iFilter
operator|.
name|getHeader
argument_list|()
operator|.
name|isCollapsible
argument_list|()
condition|)
name|search
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
specifier|native
name|void
name|createTriggers
parameter_list|()
comment|/*-{ 		$wnd.refreshPage = function() { 			@org.unitime.timetable.gwt.client.solver.AssignmentHistoryPage::__search()(); 		}; 	}-*/
function_decl|;
specifier|public
specifier|static
name|void
name|__search
parameter_list|()
block|{
specifier|final
name|int
name|left
init|=
name|Window
operator|.
name|getScrollLeft
argument_list|()
decl_stmt|;
specifier|final
name|int
name|top
init|=
name|Window
operator|.
name|getScrollTop
argument_list|()
decl_stmt|;
name|AssignmentHistoryPage
name|page
init|=
operator|(
name|AssignmentHistoryPage
operator|)
name|RootPanel
operator|.
name|get
argument_list|(
literal|"UniTimeGWT:Body"
argument_list|)
operator|.
name|getWidget
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|page
operator|.
name|search
argument_list|(
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
if|if
condition|(
name|result
condition|)
name|Window
operator|.
name|scrollTo
argument_list|(
name|left
argument_list|,
name|top
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|search
parameter_list|(
specifier|final
name|AsyncCallback
argument_list|<
name|Boolean
argument_list|>
name|callback
parameter_list|)
block|{
specifier|final
name|AssignmentHistoryRequest
name|request
init|=
operator|new
name|AssignmentHistoryRequest
argument_list|()
decl_stmt|;
name|request
operator|.
name|setFilter
argument_list|(
name|iFilter
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
name|iFilter
operator|.
name|getFooter
argument_list|()
operator|.
name|clearMessage
argument_list|()
expr_stmt|;
for|for
control|(
name|int
name|row
init|=
name|iPanel
operator|.
name|getRowCount
argument_list|()
operator|-
literal|1
init|;
name|row
operator|>
literal|0
condition|;
name|row
operator|--
control|)
name|iPanel
operator|.
name|removeRow
argument_list|(
name|row
argument_list|)
expr_stmt|;
name|iFilter
operator|.
name|getFooter
argument_list|()
operator|.
name|showLoading
argument_list|()
expr_stmt|;
name|iFilter
operator|.
name|getFooter
argument_list|()
operator|.
name|setEnabled
argument_list|(
literal|"search"
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|LoadingWidget
operator|.
name|showLoading
argument_list|(
name|MESSAGES
operator|.
name|waitLoadingData
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
name|AssignmentHistoryResponse
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
name|hideLoading
argument_list|()
expr_stmt|;
name|iFilter
operator|.
name|getFooter
argument_list|()
operator|.
name|setErrorMessage
argument_list|(
name|MESSAGES
operator|.
name|failedToLoadAssignmentHistory
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
name|failedToLoadAssignmentHistory
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
name|iFilter
operator|.
name|getFooter
argument_list|()
operator|.
name|setEnabled
argument_list|(
literal|"search"
argument_list|,
literal|true
argument_list|)
expr_stmt|;
if|if
condition|(
name|callback
operator|!=
literal|null
condition|)
name|callback
operator|.
name|onFailure
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
name|AssignmentHistoryResponse
name|result
parameter_list|)
block|{
name|LoadingWidget
operator|.
name|hideLoading
argument_list|()
expr_stmt|;
name|iFilter
operator|.
name|getFooter
argument_list|()
operator|.
name|clearMessage
argument_list|()
expr_stmt|;
name|populate
argument_list|(
name|request
operator|.
name|getFilter
argument_list|()
argument_list|,
name|result
argument_list|)
expr_stmt|;
name|iFilter
operator|.
name|getFooter
argument_list|()
operator|.
name|setEnabled
argument_list|(
literal|"search"
argument_list|,
literal|true
argument_list|)
expr_stmt|;
if|if
condition|(
name|callback
operator|!=
literal|null
condition|)
name|callback
operator|.
name|onSuccess
argument_list|(
operator|!
name|result
operator|.
name|getRows
argument_list|()
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|print
parameter_list|()
block|{
specifier|final
name|DataTable
name|table
init|=
operator|new
name|DataTable
argument_list|(
name|iLastResponse
argument_list|)
decl_stmt|;
name|Element
name|headerRow
init|=
name|table
operator|.
name|getRowFormatter
argument_list|()
operator|.
name|getElement
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|Element
name|tableElement
init|=
name|table
operator|.
name|getElement
argument_list|()
decl_stmt|;
name|Element
name|thead
init|=
name|DOM
operator|.
name|createTHead
argument_list|()
decl_stmt|;
name|tableElement
operator|.
name|insertFirst
argument_list|(
name|thead
argument_list|)
expr_stmt|;
name|headerRow
operator|.
name|getParentElement
argument_list|()
operator|.
name|removeChild
argument_list|(
name|headerRow
argument_list|)
expr_stmt|;
name|thead
operator|.
name|appendChild
argument_list|(
name|headerRow
argument_list|)
expr_stmt|;
name|Page
name|page
init|=
operator|new
name|Page
argument_list|()
block|{
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
name|sectAssignmentHistory
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getUser
parameter_list|()
block|{
return|return
literal|""
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getSession
parameter_list|()
block|{
return|return
literal|""
return|;
block|}
annotation|@
name|Override
specifier|public
name|Element
name|getBody
parameter_list|()
block|{
return|return
name|table
operator|.
name|getElement
argument_list|()
return|;
block|}
block|}
decl_stmt|;
name|ToolBox
operator|.
name|print
argument_list|(
name|page
argument_list|)
expr_stmt|;
block|}
specifier|private
name|void
name|exportData
parameter_list|(
name|String
name|format
parameter_list|)
block|{
name|String
name|query
init|=
literal|"output=assignment-history."
operator|+
name|format
operator|+
name|iFilter
operator|.
name|getQuery
argument_list|()
operator|+
literal|"&sort="
operator|+
name|SolverCookie
operator|.
name|getInstance
argument_list|()
operator|.
name|getSolutionChangesSort
argument_list|()
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
specifier|protected
name|void
name|populate
parameter_list|(
name|FilterInterface
name|filter
parameter_list|,
name|AssignmentHistoryResponse
name|response
parameter_list|)
block|{
name|iLastFilter
operator|=
name|filter
expr_stmt|;
name|iLastResponse
operator|=
name|response
expr_stmt|;
name|iFilter
operator|.
name|getFooter
argument_list|()
operator|.
name|setEnabled
argument_list|(
literal|"print"
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|iFilter
operator|.
name|getFooter
argument_list|()
operator|.
name|setEnabled
argument_list|(
literal|"exportCSV"
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|iFilter
operator|.
name|getFooter
argument_list|()
operator|.
name|setEnabled
argument_list|(
literal|"exportPDF"
argument_list|,
literal|false
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|row
init|=
name|iPanel
operator|.
name|getRowCount
argument_list|()
operator|-
literal|1
init|;
name|row
operator|>
literal|0
condition|;
name|row
operator|--
control|)
name|iPanel
operator|.
name|removeRow
argument_list|(
name|row
argument_list|)
expr_stmt|;
name|RootPanel
name|cpm
init|=
name|RootPanel
operator|.
name|get
argument_list|(
literal|"UniTimeGWT:CustomPageMessages"
argument_list|)
decl_stmt|;
if|if
condition|(
name|cpm
operator|!=
literal|null
condition|)
block|{
name|cpm
operator|.
name|clear
argument_list|()
expr_stmt|;
if|if
condition|(
name|response
operator|.
name|hasPageMessages
argument_list|()
condition|)
block|{
for|for
control|(
specifier|final
name|PageMessage
name|pm
range|:
name|response
operator|.
name|getPageMessages
argument_list|()
control|)
block|{
name|P
name|p
init|=
operator|new
name|P
argument_list|(
name|pm
operator|.
name|getType
argument_list|()
operator|==
name|PageMessageType
operator|.
name|ERROR
condition|?
literal|"unitime-PageError"
else|:
name|pm
operator|.
name|getType
argument_list|()
operator|==
name|PageMessageType
operator|.
name|WARNING
condition|?
literal|"unitime-PageWarn"
else|:
literal|"unitime-PageMessage"
argument_list|)
decl_stmt|;
name|p
operator|.
name|setHTML
argument_list|(
name|pm
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|pm
operator|.
name|hasUrl
argument_list|()
condition|)
block|{
name|p
operator|.
name|addStyleName
argument_list|(
literal|"unitime-ClickablePageMessage"
argument_list|)
expr_stmt|;
name|p
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
name|pm
operator|.
name|hasUrl
argument_list|()
condition|)
name|ToolBox
operator|.
name|open
argument_list|(
name|GWT
operator|.
name|getHostPageBaseURL
argument_list|()
operator|+
name|pm
operator|.
name|getUrl
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
name|cpm
operator|.
name|add
argument_list|(
name|p
argument_list|)
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
name|response
operator|.
name|hasMessage
argument_list|()
condition|)
block|{
name|iFilter
operator|.
name|getFooter
argument_list|()
operator|.
name|setMessage
argument_list|(
name|response
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|response
operator|.
name|getRows
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
if|if
condition|(
operator|!
name|response
operator|.
name|hasMessage
argument_list|()
condition|)
name|iFilter
operator|.
name|getFooter
argument_list|()
operator|.
name|setMessage
argument_list|(
name|MESSAGES
operator|.
name|errorAssignmentHistoryNoDataReturned
argument_list|()
argument_list|)
expr_stmt|;
return|return;
block|}
name|UniTimeHeaderPanel
name|header
init|=
operator|new
name|UniTimeHeaderPanel
argument_list|(
name|MESSAGES
operator|.
name|sectAssignmentHistory
argument_list|()
argument_list|)
decl_stmt|;
name|iPanel
operator|.
name|addHeaderRow
argument_list|(
name|header
argument_list|)
expr_stmt|;
if|if
condition|(
name|iTable
operator|==
literal|null
condition|)
block|{
name|iTable
operator|=
operator|new
name|DataTable
argument_list|(
name|response
argument_list|)
expr_stmt|;
name|iTable
operator|.
name|addValueChangeHandler
argument_list|(
operator|new
name|ValueChangeHandler
argument_list|<
name|Integer
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
name|Integer
argument_list|>
name|event
parameter_list|)
block|{
name|SolverCookie
operator|.
name|getInstance
argument_list|()
operator|.
name|setAssignmentHistorySort
argument_list|(
name|event
operator|.
name|getValue
argument_list|()
operator|==
literal|null
condition|?
literal|0
else|:
name|event
operator|.
name|getValue
argument_list|()
operator|.
name|intValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|iTable
operator|.
name|populate
argument_list|(
name|response
argument_list|)
expr_stmt|;
block|}
name|iTable
operator|.
name|setValue
argument_list|(
name|SolverCookie
operator|.
name|getInstance
argument_list|()
operator|.
name|getAssignmentHistorySort
argument_list|()
argument_list|)
expr_stmt|;
name|iPanel
operator|.
name|addRow
argument_list|(
name|iTable
argument_list|)
expr_stmt|;
name|iPanel
operator|.
name|addRow
argument_list|(
name|iLegend
argument_list|)
expr_stmt|;
name|iFilter
operator|.
name|getFooter
argument_list|()
operator|.
name|setEnabled
argument_list|(
literal|"print"
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|iFilter
operator|.
name|getFooter
argument_list|()
operator|.
name|setEnabled
argument_list|(
literal|"exportCSV"
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|iFilter
operator|.
name|getFooter
argument_list|()
operator|.
name|setEnabled
argument_list|(
literal|"exportPDF"
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

