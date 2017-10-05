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
name|Iterator
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
name|CBSWidget
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
name|ConflictsWidget
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
name|CurrentAssignment
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
name|SelectedSuggestion
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
name|SuggestionsPageContext
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
name|SuggestionsWidget
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
name|GwtRpcResponseNull
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
name|ClassAssignmentDetailsRequest
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
name|ClassInfo
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
name|MakeAssignmentRequest
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
name|SelectedAssignment
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
name|SelectedAssignmentsRequest
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
name|Suggestion
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
name|SuggestionProperties
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
name|SuggestionPropertiesRequest
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
name|i18n
operator|.
name|client
operator|.
name|NumberFormat
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
name|Command
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

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|SuggestionsPage
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
specifier|public
specifier|static
name|NumberFormat
name|sDF
init|=
name|NumberFormat
operator|.
name|getFormat
argument_list|(
literal|"0.###"
argument_list|)
decl_stmt|;
specifier|private
name|SuggestionsPageContext
name|iContext
init|=
literal|null
decl_stmt|;
specifier|private
name|CurrentAssignment
name|iCurrentAssignment
init|=
literal|null
decl_stmt|;
specifier|private
name|SelectedSuggestion
name|iSelectedSuggestion
init|=
literal|null
decl_stmt|;
specifier|private
name|SuggestionsWidget
name|iSuggestions
init|=
literal|null
decl_stmt|;
specifier|private
name|ConflictsWidget
name|iConflicts
init|=
literal|null
decl_stmt|;
specifier|private
name|CBSWidget
name|iCBS
init|=
literal|null
decl_stmt|;
specifier|private
name|List
argument_list|<
name|SelectedAssignment
argument_list|>
name|iSelectedAssignments
init|=
literal|null
decl_stmt|;
specifier|public
name|SuggestionsPage
parameter_list|(
specifier|final
name|Long
name|classId
parameter_list|)
block|{
name|addStyleName
argument_list|(
literal|"unitime-SuggestionsPage"
argument_list|)
expr_stmt|;
name|removeStyleName
argument_list|(
literal|"unitime-NotPrintableBottomLine"
argument_list|)
expr_stmt|;
name|iContext
operator|=
operator|new
name|SuggestionsPageContext
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|select
parameter_list|(
name|ClassInfo
name|clazz
parameter_list|)
block|{
name|setup
argument_list|(
name|clazz
operator|.
name|getClassId
argument_list|()
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|onSelection
parameter_list|(
name|Command
name|undo
parameter_list|)
block|{
name|selectAssignment
argument_list|(
name|iCurrentAssignment
operator|.
name|getSelectedAssignment
argument_list|()
argument_list|,
name|undo
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|remove
parameter_list|(
name|ClassInfo
name|clazz
parameter_list|)
block|{
name|unselectAssignment
argument_list|(
name|clazz
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|assign
parameter_list|(
name|List
argument_list|<
name|SelectedAssignment
argument_list|>
name|assignment
parameter_list|,
specifier|final
name|UniTimeHeaderPanel
name|panel
parameter_list|)
block|{
name|MakeAssignmentRequest
name|request
init|=
operator|new
name|MakeAssignmentRequest
argument_list|(
name|assignment
argument_list|)
decl_stmt|;
name|panel
operator|.
name|showLoading
argument_list|()
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
name|GwtRpcResponseNull
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
name|t
parameter_list|)
block|{
name|panel
operator|.
name|setErrorMessage
argument_list|(
name|MESSAGES
operator|.
name|failedToAssign
argument_list|(
name|t
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
name|failedToAssign
argument_list|(
name|t
operator|.
name|getMessage
argument_list|()
argument_list|)
argument_list|,
name|t
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|onSuccess
parameter_list|(
name|GwtRpcResponseNull
name|response
parameter_list|)
block|{
name|panel
operator|.
name|clearMessage
argument_list|()
expr_stmt|;
if|if
condition|(
operator|!
name|closeSuggestionsDialog
argument_list|()
condition|)
block|{
if|if
condition|(
name|iSelectedAssignments
operator|!=
literal|null
condition|)
name|iSelectedAssignments
operator|.
name|clear
argument_list|()
expr_stmt|;
name|iSelectedSuggestion
operator|.
name|setValue
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|setup
argument_list|(
name|iCurrentAssignment
operator|.
name|getValue
argument_list|()
operator|.
name|getClazz
argument_list|()
operator|.
name|getClassId
argument_list|()
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
block|}
argument_list|)
expr_stmt|;
block|}
block|}
expr_stmt|;
name|SelectedAssignment
name|assignment
init|=
name|getLocationAssignment
argument_list|()
decl_stmt|;
if|if
condition|(
name|assignment
operator|!=
literal|null
condition|)
block|{
name|iSelectedAssignments
operator|=
operator|new
name|ArrayList
argument_list|<
name|SelectedAssignment
argument_list|>
argument_list|()
expr_stmt|;
name|iSelectedAssignments
operator|.
name|add
argument_list|(
name|assignment
argument_list|)
expr_stmt|;
block|}
name|RPC
operator|.
name|execute
argument_list|(
operator|new
name|SuggestionPropertiesRequest
argument_list|()
argument_list|,
operator|new
name|AsyncCallback
argument_list|<
name|SuggestionProperties
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
name|t
parameter_list|)
block|{
name|UniTimeNotifications
operator|.
name|error
argument_list|(
name|MESSAGES
operator|.
name|failedToInitialize
argument_list|(
name|t
operator|.
name|getMessage
argument_list|()
argument_list|)
argument_list|,
name|t
argument_list|)
expr_stmt|;
name|ToolBox
operator|.
name|checkAccess
argument_list|(
name|t
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|onSuccess
parameter_list|(
name|SuggestionProperties
name|properties
parameter_list|)
block|{
name|iContext
operator|.
name|setSuggestionProperties
argument_list|(
name|properties
argument_list|)
expr_stmt|;
if|if
condition|(
name|properties
operator|.
name|isSolver
argument_list|()
condition|)
block|{
name|iSuggestions
operator|=
operator|new
name|SuggestionsWidget
argument_list|(
name|iContext
argument_list|)
block|{
annotation|@
name|Override
specifier|public
name|void
name|onSelection
parameter_list|(
name|Suggestion
name|suggestion
parameter_list|)
block|{
name|iCurrentAssignment
operator|.
name|clearMessage
argument_list|()
expr_stmt|;
name|iCurrentAssignment
operator|.
name|setShowUnassign
argument_list|(
operator|!
name|suggestion
operator|.
name|hasDifferentAssignments
argument_list|()
argument_list|)
expr_stmt|;
name|iCurrentAssignment
operator|.
name|setSelectedAssignment
argument_list|(
name|suggestion
operator|.
name|getAssignment
argument_list|(
name|iCurrentAssignment
operator|.
name|getValue
argument_list|()
operator|.
name|getClazz
argument_list|()
operator|.
name|getClassId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|iSelectedAssignments
operator|=
name|suggestion
operator|.
name|getAssignment
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|iSelectedSuggestion
operator|.
name|setValue
argument_list|(
name|suggestion
argument_list|)
expr_stmt|;
name|iSelectedSuggestion
operator|.
name|getElement
argument_list|()
operator|.
name|scrollIntoView
argument_list|()
expr_stmt|;
block|}
block|}
expr_stmt|;
name|addRow
argument_list|(
name|iSuggestions
argument_list|)
expr_stmt|;
name|iConflicts
operator|=
operator|new
name|ConflictsWidget
argument_list|(
name|iContext
argument_list|)
block|{
annotation|@
name|Override
specifier|public
name|void
name|onSelection
parameter_list|(
name|ClassAssignmentDetails
name|details
parameter_list|)
block|{
name|iCurrentAssignment
operator|.
name|clearMessage
argument_list|()
expr_stmt|;
name|iCurrentAssignment
operator|.
name|setSelectedTime
argument_list|(
name|details
operator|.
name|getTime
argument_list|()
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|iCurrentAssignment
operator|.
name|getElement
argument_list|()
operator|.
name|scrollIntoView
argument_list|()
expr_stmt|;
block|}
block|}
expr_stmt|;
name|addRow
argument_list|(
name|iConflicts
argument_list|)
expr_stmt|;
name|iCBS
operator|=
operator|new
name|CBSWidget
argument_list|(
name|iContext
argument_list|)
block|{
annotation|@
name|Override
specifier|public
name|void
name|onSelection
parameter_list|(
name|SelectedAssignment
name|assignment
parameter_list|)
block|{
if|if
condition|(
name|assignment
operator|.
name|getClassId
argument_list|()
operator|.
name|equals
argument_list|(
name|iCurrentAssignment
operator|.
name|getValue
argument_list|()
operator|.
name|getClazz
argument_list|()
operator|.
name|getClassId
argument_list|()
argument_list|)
condition|)
block|{
name|iCurrentAssignment
operator|.
name|clearMessage
argument_list|()
expr_stmt|;
specifier|final
name|SelectedAssignment
name|undo
init|=
name|iCurrentAssignment
operator|.
name|getSelectedAssignment
argument_list|()
decl_stmt|;
name|iCurrentAssignment
operator|.
name|setSelectedAssignment
argument_list|(
name|assignment
argument_list|)
expr_stmt|;
name|iCurrentAssignment
operator|.
name|getElement
argument_list|()
operator|.
name|scrollIntoView
argument_list|()
expr_stmt|;
name|iContext
operator|.
name|onSelection
argument_list|(
operator|new
name|Command
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|execute
parameter_list|()
block|{
name|iCurrentAssignment
operator|.
name|setSelectedAssignment
argument_list|(
name|undo
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|iSelectedAssignments
operator|==
literal|null
condition|)
name|iSelectedAssignments
operator|=
operator|new
name|ArrayList
argument_list|<
name|SelectedAssignment
argument_list|>
argument_list|()
expr_stmt|;
else|else
name|iSelectedAssignments
operator|.
name|clear
argument_list|()
expr_stmt|;
name|iSelectedAssignments
operator|.
name|add
argument_list|(
name|assignment
argument_list|)
expr_stmt|;
name|setup
argument_list|(
name|assignment
operator|.
name|getClassId
argument_list|()
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
block|}
expr_stmt|;
name|addRow
argument_list|(
name|iCBS
argument_list|)
expr_stmt|;
block|}
name|setup
argument_list|(
name|classId
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|iCurrentAssignment
operator|=
operator|new
name|CurrentAssignment
argument_list|(
name|iContext
argument_list|)
expr_stmt|;
name|addRow
argument_list|(
name|iCurrentAssignment
argument_list|)
expr_stmt|;
name|iSelectedSuggestion
operator|=
operator|new
name|SelectedSuggestion
argument_list|(
name|iContext
argument_list|)
expr_stmt|;
name|addRow
argument_list|(
name|iSelectedSuggestion
argument_list|)
expr_stmt|;
block|}
specifier|public
name|SelectedAssignment
name|getLocationAssignment
parameter_list|()
block|{
name|SelectedAssignment
name|ret
init|=
operator|new
name|SelectedAssignment
argument_list|()
decl_stmt|;
if|if
condition|(
name|Location
operator|.
name|getParameter
argument_list|(
literal|"id"
argument_list|)
operator|!=
literal|null
condition|)
name|ret
operator|.
name|setClassId
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|Location
operator|.
name|getParameter
argument_list|(
literal|"id"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
else|else
return|return
literal|null
return|;
if|if
condition|(
name|Location
operator|.
name|getParameter
argument_list|(
literal|"days"
argument_list|)
operator|!=
literal|null
condition|)
name|ret
operator|.
name|setDays
argument_list|(
name|Integer
operator|.
name|valueOf
argument_list|(
name|Location
operator|.
name|getParameter
argument_list|(
literal|"days"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
else|else
return|return
literal|null
return|;
if|if
condition|(
name|Location
operator|.
name|getParameter
argument_list|(
literal|"slot"
argument_list|)
operator|!=
literal|null
condition|)
name|ret
operator|.
name|setStartSlot
argument_list|(
name|Integer
operator|.
name|valueOf
argument_list|(
name|Location
operator|.
name|getParameter
argument_list|(
literal|"slot"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
else|else
return|return
literal|null
return|;
if|if
condition|(
name|Location
operator|.
name|getParameter
argument_list|(
literal|"pid"
argument_list|)
operator|!=
literal|null
condition|)
name|ret
operator|.
name|setPatternId
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|Location
operator|.
name|getParameter
argument_list|(
literal|"pid"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
else|else
return|return
literal|null
return|;
if|if
condition|(
name|Location
operator|.
name|getParameter
argument_list|(
literal|"did"
argument_list|)
operator|!=
literal|null
condition|)
name|ret
operator|.
name|setDatePatternId
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|Location
operator|.
name|getParameter
argument_list|(
literal|"did"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
else|else
return|return
literal|null
return|;
if|if
condition|(
name|Location
operator|.
name|getParameter
argument_list|(
literal|"room"
argument_list|)
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|String
name|id
range|:
name|Location
operator|.
name|getParameter
argument_list|(
literal|"room"
argument_list|)
operator|.
name|split
argument_list|(
literal|","
argument_list|)
control|)
name|ret
operator|.
name|addRoomId
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|id
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|ret
return|;
block|}
specifier|public
name|SuggestionsPage
parameter_list|()
block|{
name|this
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|Location
operator|.
name|getParameter
argument_list|(
literal|"id"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|setup
parameter_list|(
specifier|final
name|Long
name|classId
parameter_list|,
specifier|final
name|boolean
name|computeSuggestion
parameter_list|)
block|{
name|LoadingWidget
operator|.
name|showLoading
argument_list|(
name|MESSAGES
operator|.
name|waitLoadClassDetails
argument_list|()
argument_list|)
expr_stmt|;
name|RPC
operator|.
name|execute
argument_list|(
operator|new
name|ClassAssignmentDetailsRequest
argument_list|(
name|classId
argument_list|)
argument_list|,
operator|new
name|AsyncCallback
argument_list|<
name|ClassAssignmentDetails
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
name|t
parameter_list|)
block|{
name|ToolBox
operator|.
name|checkAccess
argument_list|(
name|t
argument_list|)
expr_stmt|;
name|LoadingWidget
operator|.
name|hideLoading
argument_list|()
expr_stmt|;
name|UniTimeNotifications
operator|.
name|error
argument_list|(
name|MESSAGES
operator|.
name|failedToLoadClassDetails
argument_list|(
name|t
operator|.
name|getMessage
argument_list|()
argument_list|)
argument_list|,
name|t
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|onSuccess
parameter_list|(
name|ClassAssignmentDetails
name|details
parameter_list|)
block|{
name|LoadingWidget
operator|.
name|hideLoading
argument_list|()
expr_stmt|;
name|iCurrentAssignment
operator|.
name|setValue
argument_list|(
name|details
argument_list|)
expr_stmt|;
if|if
condition|(
name|iSelectedAssignments
operator|!=
literal|null
operator|&&
operator|!
name|iSelectedAssignments
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
for|for
control|(
name|Iterator
argument_list|<
name|SelectedAssignment
argument_list|>
name|i
init|=
name|iSelectedAssignments
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
name|SelectedAssignment
name|a
init|=
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|a
operator|.
name|getClassId
argument_list|()
operator|.
name|equals
argument_list|(
name|classId
argument_list|)
condition|)
block|{
name|iCurrentAssignment
operator|.
name|setSelectedAssignment
argument_list|(
name|a
argument_list|)
expr_stmt|;
if|if
condition|(
name|iCurrentAssignment
operator|.
name|getSelectedAssignment
argument_list|()
operator|==
literal|null
condition|)
name|i
operator|.
name|remove
argument_list|()
expr_stmt|;
block|}
block|}
name|iCurrentAssignment
operator|.
name|setShowUnassign
argument_list|(
name|iSelectedAssignments
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|iCurrentAssignment
operator|.
name|setShowUnassign
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|iSuggestions
operator|!=
literal|null
condition|)
block|{
name|iSuggestions
operator|.
name|setVisible
argument_list|(
name|details
operator|.
name|isCanUnassign
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|details
operator|.
name|isCanUnassign
argument_list|()
condition|)
name|iSuggestions
operator|.
name|setSelection
argument_list|(
name|iSelectedAssignments
argument_list|,
name|classId
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|iConflicts
operator|!=
literal|null
condition|)
block|{
name|iConflicts
operator|.
name|setVisible
argument_list|(
name|details
operator|.
name|isCanUnassign
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|details
operator|.
name|isCanUnassign
argument_list|()
condition|)
name|iConflicts
operator|.
name|setValue
argument_list|(
name|classId
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|iCBS
operator|!=
literal|null
condition|)
block|{
name|iCBS
operator|.
name|setVisible
argument_list|(
name|details
operator|.
name|isCanUnassign
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|details
operator|.
name|isCanUnassign
argument_list|()
condition|)
name|iCBS
operator|.
name|setValue
argument_list|(
name|classId
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|computeSuggestion
operator|&&
name|details
operator|.
name|isCanUnassign
argument_list|()
operator|&&
name|iSelectedAssignments
operator|!=
literal|null
operator|&&
operator|!
name|iSelectedAssignments
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|SelectedAssignmentsRequest
name|request
init|=
operator|new
name|SelectedAssignmentsRequest
argument_list|(
name|classId
argument_list|)
decl_stmt|;
for|for
control|(
name|SelectedAssignment
name|a
range|:
name|iSelectedAssignments
control|)
name|request
operator|.
name|addAssignment
argument_list|(
name|a
argument_list|)
expr_stmt|;
name|computeSuggestion
argument_list|(
name|request
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
block|}
block|}
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|computeSuggestion
parameter_list|(
specifier|final
name|SelectedAssignmentsRequest
name|request
parameter_list|,
specifier|final
name|Command
name|undo
parameter_list|)
block|{
name|iCurrentAssignment
operator|.
name|showLoading
argument_list|()
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
name|Suggestion
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
name|t
parameter_list|)
block|{
if|if
condition|(
name|undo
operator|!=
literal|null
condition|)
name|undo
operator|.
name|execute
argument_list|()
expr_stmt|;
name|iCurrentAssignment
operator|.
name|setErrorMessage
argument_list|(
name|MESSAGES
operator|.
name|failedToComputeSelectedAssignment
argument_list|(
name|t
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
name|failedToComputeSelectedAssignment
argument_list|(
name|t
operator|.
name|getMessage
argument_list|()
argument_list|)
argument_list|,
name|t
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|onSuccess
parameter_list|(
name|Suggestion
name|suggestion
parameter_list|)
block|{
name|iCurrentAssignment
operator|.
name|clearMessage
argument_list|()
expr_stmt|;
name|iCurrentAssignment
operator|.
name|setShowUnassign
argument_list|(
operator|!
name|suggestion
operator|.
name|hasDifferentAssignments
argument_list|()
argument_list|)
expr_stmt|;
name|iSelectedSuggestion
operator|.
name|setValue
argument_list|(
name|suggestion
argument_list|)
expr_stmt|;
if|if
condition|(
name|iSuggestions
operator|!=
literal|null
condition|)
name|iSuggestions
operator|.
name|setRequest
argument_list|(
name|request
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|selectAssignment
parameter_list|(
name|SelectedAssignment
name|selection
parameter_list|,
name|Command
name|undo
parameter_list|)
block|{
if|if
condition|(
name|selection
operator|==
literal|null
condition|)
return|return;
name|SelectedAssignmentsRequest
name|request
init|=
operator|new
name|SelectedAssignmentsRequest
argument_list|(
name|selection
operator|.
name|getClassId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|iSelectedAssignments
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|Iterator
argument_list|<
name|SelectedAssignment
argument_list|>
name|i
init|=
name|iSelectedAssignments
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
name|SelectedAssignment
name|a
init|=
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|a
operator|.
name|getClassId
argument_list|()
operator|.
name|equals
argument_list|(
name|request
operator|.
name|getClassId
argument_list|()
argument_list|)
condition|)
name|request
operator|.
name|addAssignment
argument_list|(
name|a
argument_list|)
expr_stmt|;
else|else
name|i
operator|.
name|remove
argument_list|()
expr_stmt|;
block|}
name|iSelectedAssignments
operator|.
name|add
argument_list|(
name|selection
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|iSelectedAssignments
operator|=
operator|new
name|ArrayList
argument_list|<
name|SelectedAssignment
argument_list|>
argument_list|()
expr_stmt|;
name|iSelectedAssignments
operator|.
name|add
argument_list|(
name|selection
argument_list|)
expr_stmt|;
block|}
name|request
operator|.
name|addAssignment
argument_list|(
name|selection
argument_list|)
expr_stmt|;
name|computeSuggestion
argument_list|(
name|request
argument_list|,
name|undo
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|unselectAssignment
parameter_list|(
name|ClassInfo
name|clazz
parameter_list|)
block|{
if|if
condition|(
name|iSelectedAssignments
operator|==
literal|null
operator|||
name|clazz
operator|==
literal|null
condition|)
return|return;
name|SelectedAssignmentsRequest
name|request
init|=
operator|new
name|SelectedAssignmentsRequest
argument_list|(
name|iCurrentAssignment
operator|.
name|getValue
argument_list|()
operator|.
name|getClazz
argument_list|()
operator|.
name|getClassId
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|Iterator
argument_list|<
name|SelectedAssignment
argument_list|>
name|i
init|=
name|iSelectedAssignments
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
name|SelectedAssignment
name|a
init|=
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|a
operator|.
name|getClassId
argument_list|()
operator|.
name|equals
argument_list|(
name|clazz
operator|.
name|getClassId
argument_list|()
argument_list|)
condition|)
block|{
name|request
operator|.
name|addAssignment
argument_list|(
name|a
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|i
operator|.
name|remove
argument_list|()
expr_stmt|;
block|}
block|}
if|if
condition|(
name|clazz
operator|.
name|equals
argument_list|(
name|iCurrentAssignment
operator|.
name|getValue
argument_list|()
operator|.
name|getClazz
argument_list|()
argument_list|)
condition|)
block|{
name|iCurrentAssignment
operator|.
name|setSelectedAssignment
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|SelectedAssignment
name|selection
init|=
name|iCurrentAssignment
operator|.
name|getValue
argument_list|()
operator|.
name|getSelection
argument_list|()
decl_stmt|;
if|if
condition|(
name|selection
operator|!=
literal|null
condition|)
name|iCurrentAssignment
operator|.
name|setSelectedAssignment
argument_list|(
name|selection
argument_list|)
expr_stmt|;
block|}
name|computeSuggestion
argument_list|(
name|request
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|native
name|boolean
name|closeSuggestionsDialog
parameter_list|()
comment|/*-{ 		if ($wnd.parent) { 			$wnd.parent.hideGwtDialog(); 			$wnd.parent.refreshPage(); 			return true; 		} else { 			return false; 		} 	}-*/
function_decl|;
block|}
end_class

end_unit

