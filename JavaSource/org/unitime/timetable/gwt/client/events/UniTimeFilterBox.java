begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.4 (University Timetabling Application)  * Copyright (C) 2012, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|events
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
name|widgets
operator|.
name|FilterBox
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
name|FilterBox
operator|.
name|Chip
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
name|FilterBox
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
name|client
operator|.
name|widgets
operator|.
name|FilterBox
operator|.
name|SuggestionsProvider
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
name|AcademicSessionProvider
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
name|AcademicSessionProvider
operator|.
name|AcademicSessionChangeEvent
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
name|AcademicSessionProvider
operator|.
name|AcademicSessionChangeHandler
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
name|FilterRpcRequest
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
name|core
operator|.
name|client
operator|.
name|Scheduler
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
name|Scheduler
operator|.
name|ScheduledCommand
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
name|event
operator|.
name|shared
operator|.
name|HandlerRegistration
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
name|HasValue
import|;
end_import

begin_class
specifier|public
specifier|abstract
class|class
name|UniTimeFilterBox
extends|extends
name|Composite
implements|implements
name|HasValue
argument_list|<
name|String
argument_list|>
block|{
specifier|private
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
name|AcademicSessionProvider
name|iAcademicSession
decl_stmt|;
specifier|private
name|UniTimeWidget
argument_list|<
name|FilterBox
argument_list|>
name|iFilter
decl_stmt|;
specifier|private
name|boolean
name|iInitialized
init|=
literal|false
decl_stmt|;
specifier|public
name|UniTimeFilterBox
parameter_list|(
name|AcademicSessionProvider
name|session
parameter_list|)
block|{
name|iFilter
operator|=
operator|new
name|UniTimeWidget
argument_list|<
name|FilterBox
argument_list|>
argument_list|(
operator|new
name|FilterBox
argument_list|()
argument_list|)
expr_stmt|;
name|iFilter
operator|.
name|getWidget
argument_list|()
operator|.
name|setSuggestionsProvider
argument_list|(
operator|new
name|SuggestionsProvider
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|getSuggestions
parameter_list|(
name|List
argument_list|<
name|Chip
argument_list|>
name|chips
parameter_list|,
name|String
name|text
parameter_list|,
specifier|final
name|AsyncCallback
argument_list|<
name|Collection
argument_list|<
name|Suggestion
argument_list|>
argument_list|>
name|callback
parameter_list|)
block|{
name|Long
name|sessionId
init|=
name|iAcademicSession
operator|.
name|getAcademicSessionId
argument_list|()
decl_stmt|;
if|if
condition|(
name|sessionId
operator|==
literal|null
condition|)
block|{
name|callback
operator|.
name|onSuccess
argument_list|(
literal|null
argument_list|)
expr_stmt|;
return|return;
block|}
name|RPC
operator|.
name|execute
argument_list|(
name|createRpcRequest
argument_list|(
name|FilterRpcRequest
operator|.
name|Command
operator|.
name|SUGGESTIONS
argument_list|,
name|iAcademicSession
operator|.
name|getAcademicSessionId
argument_list|()
argument_list|,
name|chips
argument_list|,
name|text
argument_list|)
argument_list|,
operator|new
name|AsyncCallback
argument_list|<
name|FilterRpcResponse
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
name|FilterRpcResponse
name|result
parameter_list|)
block|{
name|List
argument_list|<
name|FilterBox
operator|.
name|Suggestion
argument_list|>
name|suggestions
init|=
operator|new
name|ArrayList
argument_list|<
name|FilterBox
operator|.
name|Suggestion
argument_list|>
argument_list|()
decl_stmt|;
if|if
condition|(
name|result
operator|.
name|hasSuggestions
argument_list|()
condition|)
block|{
for|for
control|(
name|FilterRpcResponse
operator|.
name|Entity
name|s
range|:
name|result
operator|.
name|getSuggestions
argument_list|()
control|)
name|addSuggestion
argument_list|(
name|suggestions
argument_list|,
name|s
argument_list|)
expr_stmt|;
block|}
name|callback
operator|.
name|onSuccess
argument_list|(
name|suggestions
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|initWidget
argument_list|(
name|iFilter
argument_list|)
expr_stmt|;
name|iAcademicSession
operator|=
name|session
expr_stmt|;
name|iAcademicSession
operator|.
name|addAcademicSessionChangeHandler
argument_list|(
operator|new
name|AcademicSessionChangeHandler
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onAcademicSessionChange
parameter_list|(
name|AcademicSessionChangeEvent
name|event
parameter_list|)
block|{
if|if
condition|(
name|event
operator|.
name|isChanged
argument_list|()
condition|)
name|init
argument_list|(
literal|true
argument_list|,
name|event
operator|.
name|getNewAcademicSessionId
argument_list|()
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|Scheduler
operator|.
name|get
argument_list|()
operator|.
name|scheduleDeferred
argument_list|(
operator|new
name|ScheduledCommand
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|execute
parameter_list|()
block|{
name|init
argument_list|(
literal|true
argument_list|,
name|iAcademicSession
operator|.
name|getAcademicSessionId
argument_list|()
argument_list|,
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
name|addSuggestion
parameter_list|(
name|List
argument_list|<
name|FilterBox
operator|.
name|Suggestion
argument_list|>
name|suggestions
parameter_list|,
name|FilterRpcResponse
operator|.
name|Entity
name|entity
parameter_list|)
block|{
name|suggestions
operator|.
name|add
argument_list|(
operator|new
name|FilterBox
operator|.
name|Suggestion
argument_list|(
name|entity
operator|.
name|getName
argument_list|()
argument_list|,
name|entity
operator|.
name|getAbbreviation
argument_list|()
argument_list|,
name|entity
operator|.
name|getProperty
argument_list|(
literal|"hint"
argument_list|,
literal|null
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|initAsync
parameter_list|()
block|{
name|setValue
argument_list|(
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|init
parameter_list|(
specifier|final
name|boolean
name|init
parameter_list|,
name|Long
name|academicSessionId
parameter_list|,
specifier|final
name|Command
name|onSuccess
parameter_list|)
block|{
if|if
condition|(
name|academicSessionId
operator|==
literal|null
condition|)
block|{
name|iFilter
operator|.
name|setHint
argument_list|(
name|MESSAGES
operator|.
name|hintNoSession
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|init
condition|)
block|{
name|iFilter
operator|.
name|setHint
argument_list|(
name|MESSAGES
operator|.
name|waitLoadingData
argument_list|(
name|iAcademicSession
operator|.
name|getAcademicSessionName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|iInitialized
operator|=
literal|false
expr_stmt|;
block|}
specifier|final
name|String
name|value
init|=
name|iFilter
operator|.
name|getWidget
argument_list|()
operator|.
name|getValue
argument_list|()
decl_stmt|;
name|RPC
operator|.
name|execute
argument_list|(
name|createRpcRequest
argument_list|(
name|FilterRpcRequest
operator|.
name|Command
operator|.
name|LOAD
argument_list|,
name|academicSessionId
argument_list|,
name|iFilter
operator|.
name|getWidget
argument_list|()
operator|.
name|getChips
argument_list|(
literal|null
argument_list|)
argument_list|,
name|iFilter
operator|.
name|getWidget
argument_list|()
operator|.
name|getText
argument_list|()
argument_list|)
argument_list|,
operator|new
name|AsyncCallback
argument_list|<
name|FilterRpcResponse
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
name|setErrorHint
argument_list|(
name|caught
operator|.
name|getMessage
argument_list|()
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
name|FilterRpcResponse
name|result
parameter_list|)
block|{
name|iFilter
operator|.
name|clearHint
argument_list|()
expr_stmt|;
if|if
condition|(
operator|!
name|value
operator|.
name|equals
argument_list|(
name|iFilter
operator|.
name|getWidget
argument_list|()
operator|.
name|getValue
argument_list|()
argument_list|)
condition|)
return|return;
for|for
control|(
name|FilterBox
operator|.
name|Filter
name|filter
range|:
name|iFilter
operator|.
name|getWidget
argument_list|()
operator|.
name|getFilters
argument_list|()
control|)
name|populateFilter
argument_list|(
name|filter
argument_list|,
name|result
operator|.
name|getEntities
argument_list|(
name|filter
operator|.
name|getCommand
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|onSuccess
operator|!=
literal|null
condition|)
name|onSuccess
operator|.
name|execute
argument_list|()
expr_stmt|;
if|if
condition|(
name|init
condition|)
block|{
name|iInitialized
operator|=
literal|true
expr_stmt|;
name|initAsync
argument_list|()
expr_stmt|;
block|}
block|}
block|}
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|boolean
name|isInitialized
parameter_list|()
block|{
return|return
name|iInitialized
return|;
block|}
specifier|protected
name|boolean
name|populateFilter
parameter_list|(
name|FilterBox
operator|.
name|Filter
name|filter
parameter_list|,
name|List
argument_list|<
name|FilterRpcResponse
operator|.
name|Entity
argument_list|>
name|entities
parameter_list|)
block|{
if|if
condition|(
name|filter
operator|!=
literal|null
operator|&&
name|filter
operator|instanceof
name|FilterBox
operator|.
name|StaticSimpleFilter
condition|)
block|{
name|FilterBox
operator|.
name|StaticSimpleFilter
name|simple
init|=
operator|(
name|FilterBox
operator|.
name|StaticSimpleFilter
operator|)
name|filter
decl_stmt|;
name|List
argument_list|<
name|FilterBox
operator|.
name|Chip
argument_list|>
name|chips
init|=
operator|new
name|ArrayList
argument_list|<
name|FilterBox
operator|.
name|Chip
argument_list|>
argument_list|()
decl_stmt|;
if|if
condition|(
name|entities
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|FilterRpcResponse
operator|.
name|Entity
name|entity
range|:
name|entities
control|)
name|chips
operator|.
name|add
argument_list|(
operator|new
name|FilterBox
operator|.
name|Chip
argument_list|(
name|filter
operator|.
name|getCommand
argument_list|()
argument_list|,
name|entity
operator|.
name|getName
argument_list|()
argument_list|,
name|entity
operator|.
name|getCount
argument_list|()
operator|<=
literal|0
condition|?
literal|null
else|:
literal|"("
operator|+
name|entity
operator|.
name|getCount
argument_list|()
operator|+
literal|")"
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|simple
operator|.
name|setValues
argument_list|(
name|chips
argument_list|)
expr_stmt|;
return|return
literal|true
return|;
block|}
else|else
block|{
return|return
literal|false
return|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|HandlerRegistration
name|addValueChangeHandler
parameter_list|(
name|ValueChangeHandler
argument_list|<
name|String
argument_list|>
name|handler
parameter_list|)
block|{
return|return
name|iFilter
operator|.
name|getWidget
argument_list|()
operator|.
name|addValueChangeHandler
argument_list|(
name|handler
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getValue
parameter_list|()
block|{
return|return
name|iFilter
operator|.
name|getWidget
argument_list|()
operator|.
name|getValue
argument_list|()
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
name|iFilter
operator|.
name|getWidget
argument_list|()
operator|.
name|setValue
argument_list|(
name|value
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|setValue
parameter_list|(
name|String
name|value
parameter_list|,
name|boolean
name|fireEvents
parameter_list|)
block|{
name|iFilter
operator|.
name|getWidget
argument_list|()
operator|.
name|setValue
argument_list|(
name|value
argument_list|,
name|fireEvents
argument_list|)
expr_stmt|;
if|if
condition|(
name|fireEvents
condition|)
name|init
argument_list|(
literal|false
argument_list|,
name|iAcademicSession
operator|.
name|getAcademicSessionId
argument_list|()
argument_list|,
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
if|if
condition|(
name|iFilter
operator|.
name|getWidget
argument_list|()
operator|.
name|isFilterPopupShowing
argument_list|()
condition|)
name|iFilter
operator|.
name|getWidget
argument_list|()
operator|.
name|showFilterPopup
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|clearHint
parameter_list|()
block|{
name|iFilter
operator|.
name|clearHint
argument_list|()
expr_stmt|;
block|}
specifier|public
name|void
name|setErrorHint
parameter_list|(
name|String
name|error
parameter_list|)
block|{
name|iFilter
operator|.
name|setErrorHint
argument_list|(
name|error
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|setHint
parameter_list|(
name|String
name|hint
parameter_list|)
block|{
name|iFilter
operator|.
name|setHint
argument_list|(
name|hint
argument_list|)
expr_stmt|;
block|}
specifier|protected
specifier|abstract
name|FilterRpcRequest
name|createRpcRequest
parameter_list|()
function_decl|;
specifier|protected
name|FilterRpcRequest
name|createRpcRequest
parameter_list|(
name|FilterRpcRequest
operator|.
name|Command
name|command
parameter_list|,
name|Long
name|sessionId
parameter_list|,
name|List
argument_list|<
name|FilterBox
operator|.
name|Chip
argument_list|>
name|chips
parameter_list|,
name|String
name|text
parameter_list|)
block|{
name|FilterRpcRequest
name|request
init|=
name|createRpcRequest
argument_list|()
decl_stmt|;
name|request
operator|.
name|setCommand
argument_list|(
name|command
argument_list|)
expr_stmt|;
name|request
operator|.
name|setSessionId
argument_list|(
name|sessionId
argument_list|)
expr_stmt|;
if|if
condition|(
name|chips
operator|!=
literal|null
condition|)
for|for
control|(
name|Chip
name|chip
range|:
name|chips
control|)
name|request
operator|.
name|addOption
argument_list|(
name|chip
operator|.
name|getCommand
argument_list|()
argument_list|,
name|chip
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
name|request
operator|.
name|setText
argument_list|(
name|text
argument_list|)
expr_stmt|;
return|return
name|request
return|;
block|}
specifier|public
name|void
name|getElements
parameter_list|(
specifier|final
name|AsyncCallback
argument_list|<
name|List
argument_list|<
name|FilterRpcResponse
operator|.
name|Entity
argument_list|>
argument_list|>
name|callback
parameter_list|)
block|{
name|RPC
operator|.
name|execute
argument_list|(
name|getElementsRequest
argument_list|()
argument_list|,
operator|new
name|AsyncCallback
argument_list|<
name|FilterRpcResponse
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
name|FilterRpcResponse
name|result
parameter_list|)
block|{
name|callback
operator|.
name|onSuccess
argument_list|(
name|result
operator|.
name|getResults
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
specifier|public
name|FilterRpcRequest
name|getElementsRequest
parameter_list|()
block|{
return|return
name|createRpcRequest
argument_list|(
name|FilterRpcRequest
operator|.
name|Command
operator|.
name|ENUMERATE
argument_list|,
name|iAcademicSession
operator|.
name|getAcademicSessionId
argument_list|()
argument_list|,
name|iFilter
operator|.
name|getWidget
argument_list|()
operator|.
name|getChips
argument_list|(
literal|null
argument_list|)
argument_list|,
name|iFilter
operator|.
name|getWidget
argument_list|()
operator|.
name|getText
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|void
name|addFilter
parameter_list|(
name|FilterBox
operator|.
name|Filter
name|filter
parameter_list|)
block|{
name|iFilter
operator|.
name|getWidget
argument_list|()
operator|.
name|addFilter
argument_list|(
name|filter
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Chip
name|getChip
parameter_list|(
name|String
name|command
parameter_list|)
block|{
return|return
name|iFilter
operator|.
name|getWidget
argument_list|()
operator|.
name|getChip
argument_list|(
name|command
argument_list|)
return|;
block|}
specifier|public
name|void
name|addChip
parameter_list|(
name|FilterBox
operator|.
name|Chip
name|chip
parameter_list|,
name|boolean
name|fireEvents
parameter_list|)
block|{
name|iFilter
operator|.
name|getWidget
argument_list|()
operator|.
name|addChip
argument_list|(
name|chip
argument_list|,
name|fireEvents
argument_list|)
expr_stmt|;
block|}
specifier|public
name|boolean
name|removeChip
parameter_list|(
name|FilterBox
operator|.
name|Chip
name|chip
parameter_list|,
name|boolean
name|fireEvents
parameter_list|)
block|{
return|return
name|iFilter
operator|.
name|getWidget
argument_list|()
operator|.
name|removeChip
argument_list|(
name|chip
argument_list|,
name|fireEvents
argument_list|)
return|;
block|}
specifier|public
name|boolean
name|hasChip
parameter_list|(
name|FilterBox
operator|.
name|Chip
name|chip
parameter_list|)
block|{
return|return
name|iFilter
operator|.
name|getWidget
argument_list|()
operator|.
name|hasChip
argument_list|(
name|chip
argument_list|)
return|;
block|}
specifier|protected
name|void
name|fireValueChangeEvent
parameter_list|()
block|{
name|ValueChangeEvent
operator|.
name|fire
argument_list|(
name|iFilter
operator|.
name|getWidget
argument_list|()
argument_list|,
name|iFilter
operator|.
name|getWidget
argument_list|()
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
name|boolean
name|isFilterPopupShowing
parameter_list|()
block|{
return|return
name|iFilter
operator|.
name|getWidget
argument_list|()
operator|.
name|isFilterPopupShowing
argument_list|()
return|;
block|}
specifier|public
name|void
name|showFilterPopup
parameter_list|()
block|{
name|iFilter
operator|.
name|getWidget
argument_list|()
operator|.
name|showFilterPopup
argument_list|()
expr_stmt|;
block|}
specifier|protected
name|Long
name|getAcademicSessionId
parameter_list|()
block|{
return|return
name|iAcademicSession
operator|.
name|getAcademicSessionId
argument_list|()
return|;
block|}
block|}
end_class

end_unit

