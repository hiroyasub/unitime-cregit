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
name|ComputeConflictTableRequest
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
name|ConflictsWidget
extends|extends
name|SimpleForm
implements|implements
name|TakesValue
argument_list|<
name|Long
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
specifier|private
name|SuggestionsPageContext
name|iContext
decl_stmt|;
specifier|private
name|UniTimeHeaderPanel
name|iHeader
decl_stmt|;
specifier|private
name|ConflictTable
name|iTable
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
name|Long
name|iClassId
init|=
literal|null
decl_stmt|;
specifier|private
name|boolean
name|iConflictsComputed
init|=
literal|false
decl_stmt|;
specifier|public
name|ConflictsWidget
parameter_list|(
name|SuggestionsPageContext
name|context
parameter_list|)
block|{
name|super
argument_list|()
expr_stmt|;
name|iContext
operator|=
name|context
expr_stmt|;
name|iHeader
operator|=
operator|new
name|UniTimeHeaderPanel
argument_list|(
name|MESSAGES
operator|.
name|headerConflicts
argument_list|()
argument_list|)
expr_stmt|;
name|iHeader
operator|.
name|setCollapsible
argument_list|(
name|SolverCookie
operator|.
name|getInstance
argument_list|()
operator|.
name|isShowConflicts
argument_list|()
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
name|SolverCookie
operator|.
name|getInstance
argument_list|()
operator|.
name|setShowConflicts
argument_list|(
name|event
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|event
operator|.
name|getValue
argument_list|()
condition|)
block|{
name|showConflicts
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|hideConflicts
argument_list|()
expr_stmt|;
block|}
block|}
block|}
argument_list|)
expr_stmt|;
name|removeStyleName
argument_list|(
literal|"unitime-NotPrintableBottomLine"
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|onSelection
parameter_list|(
name|ClassAssignmentDetails
name|conflict
parameter_list|)
block|{
block|}
annotation|@
name|Override
specifier|public
name|void
name|setValue
parameter_list|(
name|Long
name|classId
parameter_list|)
block|{
name|iHeader
operator|.
name|clearMessage
argument_list|()
expr_stmt|;
name|clear
argument_list|()
expr_stmt|;
name|iConflictsComputed
operator|=
literal|false
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
name|ConflictTable
argument_list|(
name|iContext
argument_list|)
expr_stmt|;
name|iTable
operator|.
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|iTable
operator|.
name|addMouseClickListener
argument_list|(
operator|new
name|MouseClickListener
argument_list|<
name|ClassAssignmentDetails
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
name|ClassAssignmentDetails
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
condition|)
name|onSelection
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
block|}
else|else
block|{
name|iTable
operator|.
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
name|addHeaderRow
argument_list|(
name|iHeader
argument_list|)
expr_stmt|;
name|addRow
argument_list|(
name|iTable
argument_list|)
expr_stmt|;
name|iClassId
operator|=
name|classId
expr_stmt|;
if|if
condition|(
name|SolverCookie
operator|.
name|getInstance
argument_list|()
operator|.
name|isShowConflicts
argument_list|()
condition|)
block|{
name|showConflicts
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|hideConflicts
argument_list|()
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|Long
name|getValue
parameter_list|()
block|{
return|return
name|iClassId
return|;
block|}
specifier|protected
name|void
name|hideConflicts
parameter_list|()
block|{
name|iTable
operator|.
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|showConflicts
parameter_list|()
block|{
if|if
condition|(
operator|!
name|iConflictsComputed
condition|)
name|computeConflicts
argument_list|()
expr_stmt|;
else|else
name|iTable
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|computeConflicts
parameter_list|()
block|{
name|iConflictsComputed
operator|=
literal|true
expr_stmt|;
name|iTable
operator|.
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|iHeader
operator|.
name|showLoading
argument_list|()
expr_stmt|;
name|RPC
operator|.
name|execute
argument_list|(
operator|new
name|ComputeConflictTableRequest
argument_list|(
name|iClassId
argument_list|)
argument_list|,
operator|new
name|AsyncCallback
argument_list|<
name|GwtRpcResponseList
argument_list|<
name|ClassAssignmentDetails
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
name|iHeader
operator|.
name|setErrorMessage
argument_list|(
name|MESSAGES
operator|.
name|failedToComputeConflicts
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
name|GwtRpcResponseList
argument_list|<
name|ClassAssignmentDetails
argument_list|>
name|result
parameter_list|)
block|{
name|iHeader
operator|.
name|clearMessage
argument_list|()
expr_stmt|;
name|iTable
operator|.
name|setValue
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
block|}
end_class

end_unit

