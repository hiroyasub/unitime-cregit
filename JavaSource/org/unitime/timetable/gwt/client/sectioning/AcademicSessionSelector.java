begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 (University Timetabling Application)  * Copyright (C) 2010, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|Collection
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Vector
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
name|WebTable
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
name|services
operator|.
name|SectioningService
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
name|services
operator|.
name|SectioningServiceAsync
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
name|DialogBox
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
name|Hidden
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
name|Label
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
name|VerticalPanel
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|AcademicSessionSelector
extends|extends
name|Composite
implements|implements
name|AcademicSessionProvider
block|{
specifier|public
specifier|static
specifier|final
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
specifier|private
name|Label
name|iSessionLabel
decl_stmt|;
specifier|private
name|Hidden
name|iSessionId
decl_stmt|;
specifier|private
name|DialogBox
name|iDialog
decl_stmt|;
specifier|private
name|WebTable
name|iSessions
decl_stmt|;
specifier|private
name|String
name|iName
init|=
literal|null
decl_stmt|;
specifier|private
specifier|final
name|SectioningServiceAsync
name|iSectioningService
init|=
name|GWT
operator|.
name|create
argument_list|(
name|SectioningService
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|Vector
argument_list|<
name|AcademicSessionChangeHandler
argument_list|>
name|iAcademicSessionChangeHandlers
init|=
operator|new
name|Vector
argument_list|<
name|AcademicSessionChangeHandler
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|StudentSectioningPage
operator|.
name|Mode
name|iMode
decl_stmt|;
specifier|public
name|AcademicSessionSelector
parameter_list|(
name|StudentSectioningPage
operator|.
name|Mode
name|mode
parameter_list|)
block|{
name|iMode
operator|=
name|mode
expr_stmt|;
name|iSessionLabel
operator|=
operator|new
name|Label
argument_list|(
name|MESSAGES
operator|.
name|sessionSelectorNoSession
argument_list|()
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|iSessionLabel
operator|.
name|setStyleName
argument_list|(
literal|"unitime-SessionSelector"
argument_list|)
expr_stmt|;
name|iSessionId
operator|=
operator|new
name|Hidden
argument_list|(
literal|"sessionId"
argument_list|)
expr_stmt|;
name|VerticalPanel
name|vertical
init|=
operator|new
name|VerticalPanel
argument_list|()
decl_stmt|;
name|vertical
operator|.
name|add
argument_list|(
name|iSessionLabel
argument_list|)
expr_stmt|;
name|vertical
operator|.
name|add
argument_list|(
name|iSessionId
argument_list|)
expr_stmt|;
name|Label
name|hint
init|=
operator|new
name|Label
argument_list|(
name|MESSAGES
operator|.
name|sessionSelectorHint
argument_list|()
argument_list|)
decl_stmt|;
name|hint
operator|.
name|setStyleName
argument_list|(
literal|"unitime-Hint"
argument_list|)
expr_stmt|;
name|vertical
operator|.
name|add
argument_list|(
name|hint
argument_list|)
expr_stmt|;
name|iDialog
operator|=
operator|new
name|MyDialogBox
argument_list|()
expr_stmt|;
name|iDialog
operator|.
name|setText
argument_list|(
name|MESSAGES
operator|.
name|sessionSelectorSelect
argument_list|()
argument_list|)
expr_stmt|;
name|iDialog
operator|.
name|setAnimationEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|iDialog
operator|.
name|setAutoHideEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|iDialog
operator|.
name|setGlassEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|iDialog
operator|.
name|setModal
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|iSessions
operator|=
operator|new
name|WebTable
argument_list|()
expr_stmt|;
name|iSessions
operator|.
name|setHeader
argument_list|(
operator|new
name|WebTable
operator|.
name|Row
argument_list|(
operator|new
name|WebTable
operator|.
name|Cell
argument_list|(
name|MESSAGES
operator|.
name|colYear
argument_list|()
argument_list|,
literal|1
argument_list|,
literal|"80"
argument_list|)
argument_list|,
operator|new
name|WebTable
operator|.
name|Cell
argument_list|(
name|MESSAGES
operator|.
name|colTerm
argument_list|()
argument_list|,
literal|1
argument_list|,
literal|"80"
argument_list|)
argument_list|,
operator|new
name|WebTable
operator|.
name|Cell
argument_list|(
name|MESSAGES
operator|.
name|colCampus
argument_list|()
argument_list|,
literal|1
argument_list|,
literal|"100"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|iSessions
operator|.
name|setEmptyMessage
argument_list|(
name|MESSAGES
operator|.
name|sessionSelectorLoading
argument_list|()
argument_list|)
expr_stmt|;
name|iDialog
operator|.
name|add
argument_list|(
name|iSessions
argument_list|)
expr_stmt|;
name|ClickHandler
name|ch
init|=
operator|new
name|ClickHandler
argument_list|()
block|{
specifier|public
name|void
name|onClick
parameter_list|(
name|ClickEvent
name|event
parameter_list|)
block|{
name|selectSession
argument_list|()
expr_stmt|;
block|}
block|}
decl_stmt|;
name|iSessionLabel
operator|.
name|addClickHandler
argument_list|(
name|ch
argument_list|)
expr_stmt|;
name|hint
operator|.
name|addClickHandler
argument_list|(
name|ch
argument_list|)
expr_stmt|;
name|iSessions
operator|.
name|addRowClickHandler
argument_list|(
operator|new
name|WebTable
operator|.
name|RowClickHandler
argument_list|()
block|{
specifier|public
name|void
name|onRowClick
parameter_list|(
name|WebTable
operator|.
name|RowClickEvent
name|event
parameter_list|)
block|{
name|rowSelected
argument_list|(
name|event
operator|.
name|getRow
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|initWidget
argument_list|(
name|vertical
argument_list|)
expr_stmt|;
block|}
specifier|private
name|void
name|rowSelected
parameter_list|(
name|WebTable
operator|.
name|Row
name|row
parameter_list|)
block|{
name|iDialog
operator|.
name|hide
argument_list|()
expr_stmt|;
name|iSessionLabel
operator|.
name|setText
argument_list|(
name|MESSAGES
operator|.
name|sessionSelectorLabel
argument_list|(
name|row
operator|.
name|getCell
argument_list|(
literal|0
argument_list|)
operator|.
name|getValue
argument_list|()
argument_list|,
name|row
operator|.
name|getCell
argument_list|(
literal|1
argument_list|)
operator|.
name|getValue
argument_list|()
argument_list|,
name|row
operator|.
name|getCell
argument_list|(
literal|2
argument_list|)
operator|.
name|getValue
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|iName
operator|=
name|MESSAGES
operator|.
name|sessionName
argument_list|(
name|row
operator|.
name|getCell
argument_list|(
literal|0
argument_list|)
operator|.
name|getValue
argument_list|()
argument_list|,
name|row
operator|.
name|getCell
argument_list|(
literal|1
argument_list|)
operator|.
name|getValue
argument_list|()
argument_list|,
name|row
operator|.
name|getCell
argument_list|(
literal|2
argument_list|)
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
name|Long
name|oldValue
init|=
operator|(
name|iSessionId
operator|.
name|getValue
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|?
literal|null
else|:
name|Long
operator|.
name|valueOf
argument_list|(
name|iSessionId
operator|.
name|getValue
argument_list|()
argument_list|)
operator|)
decl_stmt|;
name|iSessionId
operator|.
name|setValue
argument_list|(
name|row
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
name|AcademicSessionChangeEvent
name|changeEvent
init|=
operator|new
name|AcademicSessionChangeEvent
argument_list|(
name|oldValue
argument_list|,
name|Long
operator|.
name|valueOf
argument_list|(
name|row
operator|.
name|getId
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
for|for
control|(
name|AcademicSessionChangeHandler
name|handler
range|:
name|iAcademicSessionChangeHandlers
control|)
name|handler
operator|.
name|onAcademicSessionChange
argument_list|(
name|changeEvent
argument_list|)
expr_stmt|;
name|iSessions
operator|.
name|setSelectedRow
argument_list|(
name|row
operator|.
name|getRowIdx
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|selectSession
parameter_list|()
block|{
name|iDialog
operator|.
name|setAutoHideEnabled
argument_list|(
name|getAcademicSessionId
argument_list|()
operator|!=
literal|null
argument_list|)
expr_stmt|;
name|iSectioningService
operator|.
name|listAcademicSessions
argument_list|(
name|iMode
operator|.
name|isSectioning
argument_list|()
argument_list|,
operator|new
name|AsyncCallback
argument_list|<
name|Collection
argument_list|<
name|String
index|[]
argument_list|>
argument_list|>
argument_list|()
block|{
specifier|public
name|void
name|onSuccess
parameter_list|(
name|Collection
argument_list|<
name|String
index|[]
argument_list|>
name|result
parameter_list|)
block|{
name|WebTable
operator|.
name|Row
index|[]
name|records
init|=
operator|new
name|WebTable
operator|.
name|Row
index|[
name|result
operator|.
name|size
argument_list|()
index|]
decl_stmt|;
name|int
name|idx
init|=
literal|0
decl_stmt|;
name|int
name|lastSession
init|=
operator|-
literal|1
decl_stmt|;
for|for
control|(
name|String
index|[]
name|record
range|:
name|result
control|)
block|{
name|WebTable
operator|.
name|Row
name|row
init|=
operator|new
name|WebTable
operator|.
name|Row
argument_list|(
name|record
index|[
literal|1
index|]
argument_list|,
name|record
index|[
literal|2
index|]
argument_list|,
name|record
index|[
literal|3
index|]
argument_list|)
decl_stmt|;
name|row
operator|.
name|setId
argument_list|(
name|record
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
if|if
condition|(
name|row
operator|.
name|getId
argument_list|()
operator|.
name|equals
argument_list|(
name|iSessionId
operator|.
name|getValue
argument_list|()
argument_list|)
condition|)
name|lastSession
operator|=
name|idx
expr_stmt|;
name|records
index|[
name|idx
operator|++
index|]
operator|=
name|row
expr_stmt|;
block|}
name|iSessions
operator|.
name|setData
argument_list|(
name|records
argument_list|)
expr_stmt|;
if|if
condition|(
name|records
operator|.
name|length
operator|==
literal|1
condition|)
name|iSessions
operator|.
name|setSelectedRow
argument_list|(
literal|0
argument_list|)
expr_stmt|;
if|if
condition|(
name|lastSession
operator|>=
literal|0
condition|)
name|iSessions
operator|.
name|setSelectedRow
argument_list|(
name|lastSession
argument_list|)
expr_stmt|;
if|if
condition|(
name|records
operator|.
name|length
operator|==
literal|1
condition|)
name|rowSelected
argument_list|(
name|iSessions
operator|.
name|getRows
argument_list|()
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
else|else
name|iDialog
operator|.
name|center
argument_list|()
expr_stmt|;
block|}
specifier|public
name|void
name|onFailure
parameter_list|(
name|Throwable
name|caught
parameter_list|)
block|{
name|iSessions
operator|.
name|clearData
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|iSessions
operator|.
name|setEmptyMessage
argument_list|(
name|caught
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
name|iDialog
operator|.
name|center
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|selectSession
parameter_list|(
specifier|final
name|Long
name|sessionId
parameter_list|,
specifier|final
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
operator|==
literal|null
condition|)
block|{
name|selectSession
argument_list|()
expr_stmt|;
name|callback
operator|.
name|onSuccess
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|sessionId
operator|.
name|equals
argument_list|(
name|getAcademicSessionId
argument_list|()
argument_list|)
condition|)
block|{
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
name|iSectioningService
operator|.
name|listAcademicSessions
argument_list|(
name|iMode
operator|.
name|isSectioning
argument_list|()
argument_list|,
operator|new
name|AsyncCallback
argument_list|<
name|Collection
argument_list|<
name|String
index|[]
argument_list|>
argument_list|>
argument_list|()
block|{
specifier|public
name|void
name|onSuccess
parameter_list|(
name|Collection
argument_list|<
name|String
index|[]
argument_list|>
name|result
parameter_list|)
block|{
for|for
control|(
name|String
index|[]
name|record
range|:
name|result
control|)
block|{
if|if
condition|(
name|sessionId
operator|.
name|toString
argument_list|()
operator|.
name|equals
argument_list|(
name|record
index|[
literal|0
index|]
argument_list|)
condition|)
block|{
name|selectSession
argument_list|(
name|record
argument_list|)
expr_stmt|;
name|callback
operator|.
name|onSuccess
argument_list|(
literal|true
argument_list|)
expr_stmt|;
return|return;
block|}
block|}
name|selectSession
argument_list|()
expr_stmt|;
name|callback
operator|.
name|onSuccess
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|onFailure
parameter_list|(
name|Throwable
name|caught
parameter_list|)
block|{
name|iSessions
operator|.
name|clearData
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|iSessions
operator|.
name|setEmptyMessage
argument_list|(
name|caught
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
name|iDialog
operator|.
name|center
argument_list|()
expr_stmt|;
name|callback
operator|.
name|onSuccess
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|void
name|selectSession
parameter_list|(
name|String
index|[]
name|session
parameter_list|)
block|{
if|if
condition|(
name|session
operator|==
literal|null
condition|)
block|{
name|iSessionLabel
operator|.
name|setText
argument_list|(
name|MESSAGES
operator|.
name|sessionSelectorNoSession
argument_list|()
argument_list|)
expr_stmt|;
name|iSessionId
operator|.
name|setValue
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|iName
operator|=
literal|null
expr_stmt|;
return|return;
block|}
name|iSessionLabel
operator|.
name|setText
argument_list|(
name|MESSAGES
operator|.
name|sessionSelectorLabel
argument_list|(
name|session
index|[
literal|1
index|]
argument_list|,
name|session
index|[
literal|2
index|]
argument_list|,
name|session
index|[
literal|3
index|]
argument_list|)
argument_list|)
expr_stmt|;
name|iName
operator|=
name|MESSAGES
operator|.
name|sessionName
argument_list|(
name|session
index|[
literal|1
index|]
argument_list|,
name|session
index|[
literal|2
index|]
argument_list|,
name|session
index|[
literal|3
index|]
argument_list|)
expr_stmt|;
name|iSessionId
operator|.
name|setValue
argument_list|(
name|session
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Long
name|getAcademicSessionId
parameter_list|()
block|{
try|try
block|{
return|return
name|Long
operator|.
name|valueOf
argument_list|(
name|iSessionId
operator|.
name|getValue
argument_list|()
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
return|return
literal|null
return|;
block|}
block|}
specifier|public
name|String
name|getAcademicSessionName
parameter_list|()
block|{
return|return
name|iName
return|;
block|}
specifier|public
specifier|static
class|class
name|AcademicSessionChangeEvent
implements|implements
name|AcademicSessionProvider
operator|.
name|AcademicSessionChangeEvent
block|{
specifier|private
name|Long
name|iSessionId
decl_stmt|;
specifier|private
name|Long
name|iOldSessionId
decl_stmt|;
specifier|private
name|AcademicSessionChangeEvent
parameter_list|(
name|Long
name|oldSessionId
parameter_list|,
name|Long
name|sessionId
parameter_list|)
block|{
name|iOldSessionId
operator|=
name|oldSessionId
expr_stmt|;
name|iSessionId
operator|=
name|sessionId
expr_stmt|;
block|}
specifier|public
name|Long
name|getOldAcademicSessionId
parameter_list|()
block|{
return|return
name|iOldSessionId
return|;
block|}
specifier|public
name|Long
name|getNewAcademicSessionId
parameter_list|()
block|{
return|return
name|iSessionId
return|;
block|}
specifier|public
name|boolean
name|isChanged
parameter_list|()
block|{
return|return
operator|(
name|iSessionId
operator|==
literal|null
condition|?
name|iOldSessionId
operator|!=
literal|null
else|:
operator|!
name|iSessionId
operator|.
name|equals
argument_list|(
name|iOldSessionId
argument_list|)
operator|)
return|;
block|}
block|}
specifier|public
name|void
name|addAcademicSessionChangeHandler
parameter_list|(
name|AcademicSessionChangeHandler
name|handler
parameter_list|)
block|{
name|iAcademicSessionChangeHandlers
operator|.
name|add
argument_list|(
name|handler
argument_list|)
expr_stmt|;
block|}
specifier|private
class|class
name|MyDialogBox
extends|extends
name|DialogBox
block|{
specifier|private
name|MyDialogBox
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
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
name|DOM
operator|.
name|eventGetType
argument_list|(
operator|(
name|Event
operator|)
name|event
operator|.
name|getNativeEvent
argument_list|()
argument_list|)
operator|==
name|Event
operator|.
name|ONKEYUP
condition|)
block|{
if|if
condition|(
name|DOM
operator|.
name|eventGetKeyCode
argument_list|(
operator|(
name|Event
operator|)
name|event
operator|.
name|getNativeEvent
argument_list|()
argument_list|)
operator|==
name|KeyCodes
operator|.
name|KEY_DOWN
condition|)
block|{
name|iSessions
operator|.
name|setSelectedRow
argument_list|(
name|iSessions
operator|.
name|getSelectedRow
argument_list|()
operator|+
literal|1
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|DOM
operator|.
name|eventGetKeyCode
argument_list|(
operator|(
name|Event
operator|)
name|event
operator|.
name|getNativeEvent
argument_list|()
argument_list|)
operator|==
name|KeyCodes
operator|.
name|KEY_UP
condition|)
block|{
name|iSessions
operator|.
name|setSelectedRow
argument_list|(
name|iSessions
operator|.
name|getSelectedRow
argument_list|()
operator|==
literal|0
condition|?
name|iSessions
operator|.
name|getRowsCount
argument_list|()
operator|-
literal|1
else|:
name|iSessions
operator|.
name|getSelectedRow
argument_list|()
operator|-
literal|1
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|DOM
operator|.
name|eventGetKeyCode
argument_list|(
operator|(
name|Event
operator|)
name|event
operator|.
name|getNativeEvent
argument_list|()
argument_list|)
operator|==
name|KeyCodes
operator|.
name|KEY_ENTER
operator|&&
name|iSessions
operator|.
name|getSelectedRow
argument_list|()
operator|>=
literal|0
condition|)
block|{
name|rowSelected
argument_list|(
name|iSessions
operator|.
name|getRows
argument_list|()
index|[
name|iSessions
operator|.
name|getSelectedRow
argument_list|()
index|]
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|DOM
operator|.
name|eventGetKeyCode
argument_list|(
operator|(
name|Event
operator|)
name|event
operator|.
name|getNativeEvent
argument_list|()
argument_list|)
operator|==
name|KeyCodes
operator|.
name|KEY_ESCAPE
operator|&&
name|getAcademicSessionId
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|iDialog
operator|.
name|hide
argument_list|()
expr_stmt|;
block|}
block|}
block|}
block|}
block|}
end_class

end_unit

