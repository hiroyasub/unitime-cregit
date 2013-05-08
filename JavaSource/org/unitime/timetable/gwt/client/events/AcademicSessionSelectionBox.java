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
name|IntervalSelector
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
name|GwtRpcRequest
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
name|FocusEvent
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
name|FocusHandler
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
name|rpc
operator|.
name|IsSerializable
import|;
end_import

begin_class
specifier|public
class|class
name|AcademicSessionSelectionBox
extends|extends
name|IntervalSelector
argument_list|<
name|AcademicSessionSelectionBox
operator|.
name|AcademicSession
argument_list|>
implements|implements
name|AcademicSessionProvider
block|{
specifier|private
specifier|static
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
name|List
argument_list|<
name|AcademicSession
argument_list|>
name|iAllSessions
init|=
literal|null
decl_stmt|;
specifier|private
name|AcademicSessionFilter
name|iFilter
init|=
literal|null
decl_stmt|;
specifier|private
name|List
argument_list|<
name|AcademicSessionChangeHandler
argument_list|>
name|iChangeHandlers
init|=
operator|new
name|ArrayList
argument_list|<
name|AcademicSessionChangeHandler
argument_list|>
argument_list|()
decl_stmt|;
specifier|public
name|AcademicSessionSelectionBox
parameter_list|(
name|String
name|term
parameter_list|)
block|{
name|super
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|setHint
argument_list|(
name|MESSAGES
operator|.
name|waitLoadingSessions
argument_list|()
argument_list|)
expr_stmt|;
name|RPC
operator|.
name|execute
argument_list|(
operator|new
name|ListAcademicSessions
argument_list|(
name|term
argument_list|)
argument_list|,
operator|new
name|AsyncCallback
argument_list|<
name|GwtRpcResponseList
argument_list|<
name|AcademicSession
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
name|onInitializationFailure
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
name|AcademicSession
argument_list|>
name|result
parameter_list|)
block|{
name|clearHint
argument_list|()
expr_stmt|;
name|iAllSessions
operator|=
name|result
expr_stmt|;
name|setValues
argument_list|(
name|result
argument_list|)
expr_stmt|;
name|onInitializationSuccess
argument_list|(
name|result
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|addValueChangeHandler
argument_list|(
operator|new
name|ValueChangeHandler
argument_list|<
name|Interval
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
name|Interval
argument_list|>
name|event
parameter_list|)
block|{
name|fireAcademicSessionChanged
argument_list|()
expr_stmt|;
name|setAriaLabel
argument_list|(
name|ARIA
operator|.
name|academicSession
argument_list|(
name|toAriaString
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|addFocusHandler
argument_list|(
operator|new
name|FocusHandler
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onFocus
parameter_list|(
name|FocusEvent
name|event
parameter_list|)
block|{
name|setAriaLabel
argument_list|(
name|ARIA
operator|.
name|academicSession
argument_list|(
name|toAriaString
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|onInitializationSuccess
parameter_list|(
name|List
argument_list|<
name|AcademicSession
argument_list|>
name|sessions
parameter_list|)
block|{
block|}
specifier|protected
name|void
name|onInitializationFailure
parameter_list|(
name|Throwable
name|caught
parameter_list|)
block|{
block|}
annotation|@
name|Override
specifier|public
name|Long
name|getAcademicSessionId
parameter_list|()
block|{
return|return
operator|(
name|getValue
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
name|getValue
argument_list|()
operator|.
name|getFirst
argument_list|()
operator|.
name|getUniqueId
argument_list|()
operator|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getAcademicSessionName
parameter_list|()
block|{
return|return
operator|(
name|getValue
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
name|getValue
argument_list|()
operator|.
name|getFirst
argument_list|()
operator|.
name|getName
argument_list|()
operator|)
return|;
block|}
specifier|public
name|String
name|getAcademicSessionAbbreviation
parameter_list|()
block|{
return|return
operator|(
name|getValue
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
name|getValue
argument_list|()
operator|.
name|getFirst
argument_list|()
operator|.
name|getAbbv
argument_list|()
operator|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|addAcademicSessionChangeHandler
parameter_list|(
name|AcademicSessionChangeHandler
name|handler
parameter_list|)
block|{
name|iChangeHandlers
operator|.
name|add
argument_list|(
name|handler
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|selectSession
parameter_list|(
name|Long
name|sessionId
parameter_list|,
name|AsyncCallback
argument_list|<
name|Boolean
argument_list|>
name|callback
parameter_list|)
block|{
if|if
condition|(
name|sessionId
operator|!=
literal|null
operator|&&
name|sessionId
operator|.
name|equals
argument_list|(
name|getAcademicSessionId
argument_list|()
argument_list|)
condition|)
block|{
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
literal|true
argument_list|)
expr_stmt|;
return|return;
block|}
if|if
condition|(
name|sessionId
operator|==
literal|null
operator|&&
name|getAcademicSessionId
argument_list|()
operator|==
literal|null
condition|)
block|{
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
literal|true
argument_list|)
expr_stmt|;
return|return;
block|}
if|if
condition|(
name|sessionId
operator|==
literal|null
condition|)
block|{
name|setValue
argument_list|(
literal|null
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
literal|true
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|boolean
name|found
init|=
literal|false
decl_stmt|;
if|if
condition|(
name|getValues
argument_list|()
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|AcademicSession
name|session
range|:
name|getValues
argument_list|()
control|)
block|{
if|if
condition|(
name|session
operator|.
name|getUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
name|sessionId
argument_list|)
condition|)
block|{
name|setValue
argument_list|(
operator|new
name|Interval
argument_list|(
name|session
argument_list|)
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
literal|true
argument_list|)
expr_stmt|;
name|found
operator|=
literal|true
expr_stmt|;
break|break;
block|}
block|}
block|}
if|if
condition|(
operator|!
name|found
condition|)
block|{
name|setValue
argument_list|(
literal|null
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
literal|false
argument_list|)
expr_stmt|;
block|}
block|}
name|fireAcademicSessionChanged
argument_list|()
expr_stmt|;
block|}
specifier|public
name|void
name|selectSession
parameter_list|(
name|String
name|sessionAbbreviation
parameter_list|,
name|AsyncCallback
argument_list|<
name|Boolean
argument_list|>
name|callback
parameter_list|)
block|{
if|if
condition|(
name|sessionAbbreviation
operator|!=
literal|null
operator|&&
name|sessionAbbreviation
operator|.
name|equals
argument_list|(
name|getAcademicSessionAbbreviation
argument_list|()
argument_list|)
condition|)
block|{
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
literal|true
argument_list|)
expr_stmt|;
return|return;
block|}
if|if
condition|(
name|sessionAbbreviation
operator|==
literal|null
operator|&&
name|getAcademicSessionAbbreviation
argument_list|()
operator|==
literal|null
condition|)
block|{
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
literal|true
argument_list|)
expr_stmt|;
return|return;
block|}
if|if
condition|(
name|sessionAbbreviation
operator|==
literal|null
condition|)
block|{
name|setValue
argument_list|(
literal|null
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
literal|true
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|boolean
name|found
init|=
literal|false
decl_stmt|;
if|if
condition|(
name|getValues
argument_list|()
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|AcademicSession
name|session
range|:
name|getValues
argument_list|()
control|)
block|{
if|if
condition|(
name|sessionAbbreviation
operator|.
name|equals
argument_list|(
name|session
operator|.
name|getAbbv
argument_list|()
argument_list|)
condition|)
block|{
name|setValue
argument_list|(
operator|new
name|Interval
argument_list|(
name|session
argument_list|)
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
literal|true
argument_list|)
expr_stmt|;
name|found
operator|=
literal|true
expr_stmt|;
break|break;
block|}
block|}
block|}
if|if
condition|(
operator|!
name|found
condition|)
block|{
name|setValue
argument_list|(
literal|null
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
literal|false
argument_list|)
expr_stmt|;
block|}
block|}
name|fireAcademicSessionChanged
argument_list|()
expr_stmt|;
block|}
specifier|private
name|Long
name|iLastSessionId
init|=
literal|null
decl_stmt|;
specifier|public
name|void
name|fireAcademicSessionChanged
parameter_list|()
block|{
specifier|final
name|Long
name|oldSession
init|=
name|iLastSessionId
decl_stmt|;
name|iLastSessionId
operator|=
name|getAcademicSessionId
argument_list|()
expr_stmt|;
name|AcademicSessionChangeEvent
name|event
init|=
operator|new
name|AcademicSessionChangeEvent
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|boolean
name|isChanged
parameter_list|()
block|{
if|if
condition|(
name|oldSession
operator|==
literal|null
condition|)
return|return
name|getAcademicSessionId
argument_list|()
operator|!=
literal|null
return|;
else|else
return|return
operator|!
name|oldSession
operator|.
name|equals
argument_list|(
name|getAcademicSessionId
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Long
name|getOldAcademicSessionId
parameter_list|()
block|{
return|return
name|oldSession
return|;
block|}
annotation|@
name|Override
specifier|public
name|Long
name|getNewAcademicSessionId
parameter_list|()
block|{
return|return
name|getAcademicSessionId
argument_list|()
return|;
block|}
block|}
decl_stmt|;
for|for
control|(
name|AcademicSessionChangeHandler
name|handler
range|:
name|iChangeHandlers
control|)
name|handler
operator|.
name|onAcademicSessionChange
argument_list|(
name|event
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
class|class
name|AcademicSession
implements|implements
name|IsSerializable
block|{
specifier|private
name|Long
name|iUniqueId
decl_stmt|;
specifier|private
name|String
name|iName
decl_stmt|,
name|iAbbv
decl_stmt|,
name|iHint
decl_stmt|;
specifier|private
name|boolean
name|iSelected
decl_stmt|;
specifier|private
name|Long
name|iPreviousId
decl_stmt|,
name|iNextId
decl_stmt|;
specifier|private
name|int
name|iFlags
init|=
literal|0
decl_stmt|;
specifier|public
specifier|static
enum|enum
name|Flag
block|{
name|HasClasses
block|,
name|HasMidtermExams
block|,
name|HasFinalExams
block|,
name|HasEvents
block|,
name|CanAddEvents
block|;
specifier|public
name|int
name|flag
parameter_list|()
block|{
return|return
literal|1
operator|<<
name|ordinal
argument_list|()
return|;
block|}
specifier|public
name|boolean
name|in
parameter_list|(
name|int
name|flags
parameter_list|)
block|{
return|return
operator|(
name|flags
operator|&
name|flag
argument_list|()
operator|)
operator|!=
literal|0
return|;
block|}
specifier|public
name|int
name|set
parameter_list|(
name|int
name|flags
parameter_list|)
block|{
return|return
operator|(
name|in
argument_list|(
name|flags
argument_list|)
condition|?
name|flags
else|:
name|flags
operator|+
name|flag
argument_list|()
operator|)
return|;
block|}
specifier|public
name|int
name|clear
parameter_list|(
name|int
name|flags
parameter_list|)
block|{
return|return
operator|(
name|in
argument_list|(
name|flags
argument_list|)
condition|?
name|flags
operator|-
name|flag
argument_list|()
else|:
name|flags
operator|)
return|;
block|}
block|}
specifier|public
name|AcademicSession
parameter_list|()
block|{
block|}
specifier|public
name|AcademicSession
parameter_list|(
name|Long
name|uniqueId
parameter_list|,
name|String
name|name
parameter_list|,
name|String
name|abbv
parameter_list|,
name|String
name|hint
parameter_list|,
name|boolean
name|selected
parameter_list|)
block|{
name|iUniqueId
operator|=
name|uniqueId
expr_stmt|;
name|iName
operator|=
name|name
expr_stmt|;
name|iAbbv
operator|=
name|abbv
expr_stmt|;
name|iHint
operator|=
name|hint
expr_stmt|;
name|iSelected
operator|=
name|selected
expr_stmt|;
block|}
specifier|public
name|Long
name|getUniqueId
parameter_list|()
block|{
return|return
name|iUniqueId
return|;
block|}
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|iName
return|;
block|}
specifier|public
name|String
name|getAbbv
parameter_list|()
block|{
return|return
name|iAbbv
return|;
block|}
specifier|public
name|String
name|getHint
parameter_list|()
block|{
return|return
name|iHint
return|;
block|}
specifier|public
name|boolean
name|isSelected
parameter_list|()
block|{
return|return
name|iSelected
return|;
block|}
specifier|public
name|Long
name|getPreviousId
parameter_list|()
block|{
return|return
name|iPreviousId
return|;
block|}
specifier|public
name|void
name|setPreviousId
parameter_list|(
name|Long
name|id
parameter_list|)
block|{
name|iPreviousId
operator|=
name|id
expr_stmt|;
block|}
specifier|public
name|Long
name|getNextId
parameter_list|()
block|{
return|return
name|iNextId
return|;
block|}
specifier|public
name|void
name|setNextId
parameter_list|(
name|Long
name|id
parameter_list|)
block|{
name|iNextId
operator|=
name|id
expr_stmt|;
block|}
specifier|public
name|boolean
name|has
parameter_list|(
name|Flag
name|f
parameter_list|)
block|{
return|return
name|f
operator|.
name|in
argument_list|(
name|iFlags
argument_list|)
return|;
block|}
specifier|public
name|void
name|set
parameter_list|(
name|Flag
name|f
parameter_list|)
block|{
name|iFlags
operator|=
name|f
operator|.
name|set
argument_list|(
name|iFlags
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|clear
parameter_list|(
name|Flag
name|f
parameter_list|)
block|{
name|iFlags
operator|=
name|f
operator|.
name|clear
argument_list|(
name|iFlags
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|getName
argument_list|()
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|ListAcademicSessions
implements|implements
name|GwtRpcRequest
argument_list|<
name|GwtRpcResponseList
argument_list|<
name|AcademicSession
argument_list|>
argument_list|>
block|{
specifier|private
name|String
name|iTerm
init|=
literal|null
decl_stmt|;
specifier|public
name|ListAcademicSessions
parameter_list|()
block|{
block|}
specifier|public
name|ListAcademicSessions
parameter_list|(
name|String
name|term
parameter_list|)
block|{
name|iTerm
operator|=
name|term
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasTerm
parameter_list|()
block|{
return|return
name|iTerm
operator|!=
literal|null
operator|&&
operator|!
name|iTerm
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|String
name|getTerm
parameter_list|()
block|{
return|return
name|iTerm
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
operator|(
name|hasTerm
argument_list|()
condition|?
name|getTerm
argument_list|()
else|:
literal|""
operator|)
return|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|setValue
parameter_list|(
name|Interval
name|value
parameter_list|,
name|boolean
name|fire
parameter_list|)
block|{
if|if
condition|(
name|value
operator|==
literal|null
operator|&&
name|getValues
argument_list|()
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|AcademicSession
name|session
range|:
name|getValues
argument_list|()
control|)
if|if
condition|(
name|session
operator|.
name|isSelected
argument_list|()
condition|)
block|{
name|value
operator|=
operator|new
name|Interval
argument_list|(
name|session
argument_list|)
expr_stmt|;
break|break;
block|}
block|}
if|if
condition|(
name|value
operator|!=
literal|null
operator|&&
name|value
operator|.
name|isOne
argument_list|()
condition|)
name|setHint
argument_list|(
name|value
operator|.
name|getFirst
argument_list|()
operator|.
name|getHint
argument_list|()
argument_list|)
expr_stmt|;
name|super
operator|.
name|setValue
argument_list|(
name|value
argument_list|,
name|fire
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|AcademicSession
name|session
parameter_list|(
name|Long
name|id
parameter_list|)
block|{
if|if
condition|(
name|iAllSessions
operator|!=
literal|null
operator|&&
name|id
operator|!=
literal|null
condition|)
for|for
control|(
name|AcademicSession
name|session
range|:
name|iAllSessions
control|)
if|if
condition|(
name|session
operator|.
name|getUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
name|id
argument_list|)
condition|)
return|return
name|session
return|;
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|protected
name|Interval
name|previous
parameter_list|(
name|Interval
name|interval
parameter_list|)
block|{
if|if
condition|(
name|interval
operator|.
name|isOne
argument_list|()
condition|)
block|{
name|AcademicSession
name|prev
init|=
name|session
argument_list|(
name|interval
operator|.
name|getFirst
argument_list|()
operator|.
name|getPreviousId
argument_list|()
argument_list|)
decl_stmt|;
while|while
condition|(
name|prev
operator|!=
literal|null
operator|&&
name|iFilter
operator|!=
literal|null
operator|&&
operator|!
name|iFilter
operator|.
name|accept
argument_list|(
name|prev
argument_list|)
condition|)
name|prev
operator|=
name|session
argument_list|(
name|prev
operator|.
name|getPreviousId
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|prev
operator|!=
literal|null
condition|)
return|return
operator|new
name|Interval
argument_list|(
name|prev
argument_list|)
return|;
block|}
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|protected
name|Interval
name|next
parameter_list|(
name|Interval
name|interval
parameter_list|)
block|{
if|if
condition|(
name|interval
operator|.
name|isOne
argument_list|()
condition|)
block|{
name|AcademicSession
name|next
init|=
name|session
argument_list|(
name|interval
operator|.
name|getFirst
argument_list|()
operator|.
name|getNextId
argument_list|()
argument_list|)
decl_stmt|;
while|while
condition|(
name|next
operator|!=
literal|null
operator|&&
name|iFilter
operator|!=
literal|null
operator|&&
operator|!
name|iFilter
operator|.
name|accept
argument_list|(
name|next
argument_list|)
condition|)
name|next
operator|=
name|session
argument_list|(
name|next
operator|.
name|getNextId
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|next
operator|!=
literal|null
condition|)
return|return
operator|new
name|Interval
argument_list|(
name|next
argument_list|)
return|;
block|}
return|return
literal|null
return|;
block|}
specifier|public
name|void
name|setFilter
parameter_list|(
name|AcademicSessionFilter
name|filter
parameter_list|)
block|{
name|iFilter
operator|=
name|filter
expr_stmt|;
if|if
condition|(
name|iAllSessions
operator|!=
literal|null
condition|)
block|{
name|setValues
argument_list|(
name|iAllSessions
argument_list|)
expr_stmt|;
name|setValue
argument_list|(
name|getValue
argument_list|()
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|List
argument_list|<
name|AcademicSession
argument_list|>
name|getAllSessions
parameter_list|()
block|{
return|return
name|iAllSessions
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|setValues
parameter_list|(
name|List
argument_list|<
name|AcademicSession
argument_list|>
name|sessions
parameter_list|)
block|{
name|List
argument_list|<
name|AcademicSession
argument_list|>
name|filtered
init|=
operator|new
name|ArrayList
argument_list|<
name|AcademicSession
argument_list|>
argument_list|()
decl_stmt|;
name|AcademicSession
name|selected
init|=
literal|null
decl_stmt|;
for|for
control|(
name|AcademicSession
name|session
range|:
name|sessions
control|)
if|if
condition|(
name|iFilter
operator|==
literal|null
operator|||
name|iFilter
operator|.
name|accept
argument_list|(
name|session
argument_list|)
condition|)
block|{
name|filtered
operator|.
name|add
argument_list|(
name|session
argument_list|)
expr_stmt|;
if|if
condition|(
name|session
operator|.
name|isSelected
argument_list|()
condition|)
name|selected
operator|=
name|session
expr_stmt|;
block|}
if|if
condition|(
name|selected
operator|==
literal|null
condition|)
name|selected
operator|=
operator|(
name|filtered
operator|.
name|isEmpty
argument_list|()
condition|?
literal|null
else|:
name|filtered
operator|.
name|get
argument_list|(
name|filtered
operator|.
name|size
argument_list|()
operator|-
literal|1
argument_list|)
operator|)
expr_stmt|;
name|setDefaultValue
argument_list|(
operator|new
name|Interval
argument_list|(
name|selected
argument_list|)
argument_list|)
expr_stmt|;
name|super
operator|.
name|setValues
argument_list|(
name|filtered
argument_list|)
expr_stmt|;
if|if
condition|(
name|getValue
argument_list|()
operator|==
literal|null
operator|&&
name|getDefaultValue
argument_list|()
operator|!=
literal|null
condition|)
name|setValue
argument_list|(
name|getDefaultValue
argument_list|()
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
interface|interface
name|AcademicSessionFilter
block|{
specifier|public
name|boolean
name|accept
parameter_list|(
name|AcademicSession
name|session
parameter_list|)
function_decl|;
block|}
annotation|@
name|Override
specifier|public
name|AcademicSessionInfo
name|getAcademicSessionInfo
parameter_list|()
block|{
return|return
literal|null
return|;
block|}
block|}
end_class

end_unit

