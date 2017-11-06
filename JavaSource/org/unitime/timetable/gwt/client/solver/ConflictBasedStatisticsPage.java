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
name|solver
operator|.
name|suggestions
operator|.
name|ConflictBasedStatisticsTree
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
name|CourseTimetablingSolverInterface
operator|.
name|ConflictStatisticsFilterRequest
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
name|ConflictStatisticsFilterResponse
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
name|CBSNode
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
name|ConflictBasedStatisticsRequest
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
name|ConflictBasedStatisticsPage
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
name|GwtRpcResponseList
argument_list|<
name|CBSNode
argument_list|>
name|iLastResponse
decl_stmt|;
specifier|private
name|ConflictStatisticsFilterResponse
name|iFilterResponse
decl_stmt|;
specifier|private
name|ConflictBasedStatisticsTree
name|iTree
decl_stmt|;
specifier|private
name|PreferenceLegend
name|iLegend
decl_stmt|;
specifier|public
name|ConflictBasedStatisticsPage
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
name|isShowCBSFilter
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
if|if
condition|(
name|event
operator|.
name|getValue
argument_list|()
operator|!=
literal|null
condition|)
name|SolverCookie
operator|.
name|getInstance
argument_list|()
operator|.
name|setShowCBSFilter
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
literal|"unitime-ConflictBasedStatisticsPage"
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
name|ConflictStatisticsFilterRequest
argument_list|()
argument_list|,
operator|new
name|AsyncCallback
argument_list|<
name|ConflictStatisticsFilterResponse
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
name|ConflictStatisticsFilterResponse
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
name|getSuggestionProperties
argument_list|()
operator|.
name|getPreferences
argument_list|()
argument_list|)
expr_stmt|;
name|iFilterResponse
operator|=
name|result
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
comment|/*-{ 		$wnd.refreshPage = function() { 		}; 	}-*/
function_decl|;
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
name|ConflictBasedStatisticsRequest
name|request
init|=
operator|new
name|ConflictBasedStatisticsRequest
argument_list|()
decl_stmt|;
specifier|final
name|FilterInterface
name|filter
init|=
name|iFilter
operator|.
name|getValue
argument_list|()
decl_stmt|;
try|try
block|{
name|request
operator|.
name|setLimit
argument_list|(
name|Double
operator|.
name|valueOf
argument_list|(
name|filter
operator|.
name|getParameterValue
argument_list|(
literal|"limit"
argument_list|,
literal|"25.0"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|e
parameter_list|)
block|{
block|}
name|request
operator|.
name|setVariableOriented
argument_list|(
literal|"0"
operator|.
name|equals
argument_list|(
name|filter
operator|.
name|getParameterValue
argument_list|(
literal|"mode"
argument_list|,
literal|"0"
argument_list|)
argument_list|)
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
name|GwtRpcResponseList
argument_list|<
name|CBSNode
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
name|failedToLoadConflictStatistics
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
name|failedToLoadConflictStatistics
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
name|GwtRpcResponseList
argument_list|<
name|CBSNode
argument_list|>
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
name|filter
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
name|result
operator|!=
literal|null
operator|&&
operator|!
name|result
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
name|populate
parameter_list|(
name|FilterInterface
name|filter
parameter_list|,
name|GwtRpcResponseList
argument_list|<
name|CBSNode
argument_list|>
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
operator|&&
name|iFilterResponse
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
name|iFilterResponse
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
name|iFilterResponse
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
operator|==
literal|null
operator|||
name|response
operator|.
name|isEmpty
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
name|MESSAGES
operator|.
name|errorConflictStatisticsNoDataReturned
argument_list|()
argument_list|)
expr_stmt|;
return|return;
block|}
if|if
condition|(
name|iTree
operator|==
literal|null
condition|)
block|{
name|iTree
operator|=
operator|new
name|ConflictBasedStatisticsTree
argument_list|(
name|iFilterResponse
operator|.
name|getSuggestionProperties
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|iTree
operator|.
name|setValue
argument_list|(
name|response
argument_list|)
expr_stmt|;
name|iPanel
operator|.
name|addRow
argument_list|(
name|iTree
argument_list|)
expr_stmt|;
name|iPanel
operator|.
name|addRow
argument_list|(
name|iLegend
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit
