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
name|page
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|TreeSet
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
name|services
operator|.
name|MenuService
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
name|MenuServiceAsync
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
name|Timer
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
name|HTML
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
name|HorizontalPanel
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
name|PopupPanel
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
name|VerticalPanel
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|UniTimePageHeader
extends|extends
name|Composite
block|{
specifier|private
specifier|final
name|MenuServiceAsync
name|iService
init|=
name|GWT
operator|.
name|create
argument_list|(
name|MenuService
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|HorizontalPanel
name|iPanel
init|=
operator|new
name|HorizontalPanel
argument_list|()
decl_stmt|;
specifier|private
name|VerticalPanelWithHint
name|iSolverInfo
decl_stmt|,
name|iSessionInfo
decl_stmt|,
name|iUserInfo
decl_stmt|;
specifier|public
name|UniTimePageHeader
parameter_list|()
block|{
name|iSolverInfo
operator|=
operator|new
name|VerticalPanelWithHint
argument_list|(
operator|new
name|Callback
argument_list|()
block|{
specifier|public
name|void
name|execute
parameter_list|(
name|Callback
name|callback
parameter_list|)
block|{
name|reloadSolverInfo
argument_list|(
literal|true
argument_list|,
name|callback
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|iPanel
operator|.
name|add
argument_list|(
name|iSolverInfo
argument_list|)
expr_stmt|;
name|iPanel
operator|.
name|setCellHorizontalAlignment
argument_list|(
name|iSolverInfo
argument_list|,
name|HasHorizontalAlignment
operator|.
name|ALIGN_LEFT
argument_list|)
expr_stmt|;
name|iSolverInfo
operator|.
name|getElement
argument_list|()
operator|.
name|getStyle
argument_list|()
operator|.
name|setPaddingRight
argument_list|(
literal|30
argument_list|,
name|Unit
operator|.
name|PX
argument_list|)
expr_stmt|;
name|iUserInfo
operator|=
operator|new
name|VerticalPanelWithHint
argument_list|(
operator|new
name|Callback
argument_list|()
block|{
specifier|public
name|void
name|execute
parameter_list|(
name|Callback
name|callback
parameter_list|)
block|{
if|if
condition|(
name|callback
operator|!=
literal|null
condition|)
name|callback
operator|.
name|execute
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|iPanel
operator|.
name|add
argument_list|(
name|iUserInfo
argument_list|)
expr_stmt|;
name|iPanel
operator|.
name|setCellHorizontalAlignment
argument_list|(
name|iUserInfo
argument_list|,
name|HasHorizontalAlignment
operator|.
name|ALIGN_CENTER
argument_list|)
expr_stmt|;
name|iUserInfo
operator|.
name|getElement
argument_list|()
operator|.
name|getStyle
argument_list|()
operator|.
name|setPaddingRight
argument_list|(
literal|30
argument_list|,
name|Unit
operator|.
name|PX
argument_list|)
expr_stmt|;
name|iSessionInfo
operator|=
operator|new
name|VerticalPanelWithHint
argument_list|(
operator|new
name|Callback
argument_list|()
block|{
specifier|public
name|void
name|execute
parameter_list|(
name|Callback
name|callback
parameter_list|)
block|{
if|if
condition|(
name|callback
operator|!=
literal|null
condition|)
name|callback
operator|.
name|execute
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|iPanel
operator|.
name|add
argument_list|(
name|iSessionInfo
argument_list|)
expr_stmt|;
name|iPanel
operator|.
name|setCellHorizontalAlignment
argument_list|(
name|iSessionInfo
argument_list|,
name|HasHorizontalAlignment
operator|.
name|ALIGN_RIGHT
argument_list|)
expr_stmt|;
name|initWidget
argument_list|(
name|iPanel
argument_list|)
expr_stmt|;
name|reloadSessionInfo
argument_list|()
expr_stmt|;
name|reloadUserInfo
argument_list|()
expr_stmt|;
name|reloadSolverInfo
argument_list|(
literal|false
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|insert
parameter_list|(
specifier|final
name|RootPanel
name|panel
parameter_list|)
block|{
if|if
condition|(
name|panel
operator|.
name|getWidgetCount
argument_list|()
operator|>
literal|0
condition|)
return|return;
name|panel
operator|.
name|add
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|panel
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|reloadSessionInfo
parameter_list|()
block|{
name|iService
operator|.
name|getSessionInfo
argument_list|(
operator|new
name|AsyncCallback
argument_list|<
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onSuccess
parameter_list|(
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|result
parameter_list|)
block|{
name|iSessionInfo
operator|.
name|clear
argument_list|()
expr_stmt|;
name|iSessionInfo
operator|.
name|setHint
argument_list|(
name|result
argument_list|)
expr_stmt|;
if|if
condition|(
name|result
operator|==
literal|null
condition|)
return|return;
name|HTML
name|sessionLabel
init|=
operator|new
name|HTML
argument_list|(
name|result
operator|.
name|get
argument_list|(
literal|"0Session"
argument_list|)
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|sessionLabel
operator|.
name|setStyleName
argument_list|(
literal|"unitime-SessionSelector"
argument_list|)
expr_stmt|;
name|iSessionInfo
operator|.
name|add
argument_list|(
name|sessionLabel
argument_list|)
expr_stmt|;
name|HTML
name|hint
init|=
operator|new
name|HTML
argument_list|(
literal|"Click here to change the session / role."
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|hint
operator|.
name|setStyleName
argument_list|(
literal|"unitime-Hint"
argument_list|)
expr_stmt|;
name|iSessionInfo
operator|.
name|add
argument_list|(
name|hint
argument_list|)
expr_stmt|;
name|ClickHandler
name|c
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
name|ToolBox
operator|.
name|open
argument_list|(
name|GWT
operator|.
name|getHostPageBaseURL
argument_list|()
operator|+
literal|"selectPrimaryRole.do?list=Y"
argument_list|)
expr_stmt|;
block|}
block|}
decl_stmt|;
name|sessionLabel
operator|.
name|addClickHandler
argument_list|(
name|c
argument_list|)
expr_stmt|;
name|hint
operator|.
name|addClickHandler
argument_list|(
name|c
argument_list|)
expr_stmt|;
block|}
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
name|iSessionInfo
operator|.
name|clear
argument_list|()
expr_stmt|;
name|iSessionInfo
operator|.
name|setHint
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
name|void
name|reloadUserInfo
parameter_list|()
block|{
name|iService
operator|.
name|getUserInfo
argument_list|(
operator|new
name|AsyncCallback
argument_list|<
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onSuccess
parameter_list|(
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|result
parameter_list|)
block|{
name|iUserInfo
operator|.
name|clear
argument_list|()
expr_stmt|;
name|iUserInfo
operator|.
name|setHint
argument_list|(
name|result
argument_list|)
expr_stmt|;
if|if
condition|(
name|result
operator|==
literal|null
condition|)
return|return;
name|HTML
name|userLabel
init|=
operator|new
name|HTML
argument_list|(
name|result
operator|.
name|get
argument_list|(
literal|"0Name"
argument_list|)
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|userLabel
operator|.
name|setStyleName
argument_list|(
literal|"unitime-SessionSelector"
argument_list|)
expr_stmt|;
name|iUserInfo
operator|.
name|add
argument_list|(
name|userLabel
argument_list|)
expr_stmt|;
name|HTML
name|hint
init|=
operator|new
name|HTML
argument_list|(
name|result
operator|.
name|get
argument_list|(
literal|"2Role"
argument_list|)
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|hint
operator|.
name|setStyleName
argument_list|(
literal|"unitime-Hint"
argument_list|)
expr_stmt|;
name|iUserInfo
operator|.
name|add
argument_list|(
name|hint
argument_list|)
expr_stmt|;
if|if
condition|(
name|result
operator|.
name|containsKey
argument_list|(
literal|"Chameleon"
argument_list|)
condition|)
block|{
name|ClickHandler
name|c
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
name|ToolBox
operator|.
name|open
argument_list|(
name|GWT
operator|.
name|getHostPageBaseURL
argument_list|()
operator|+
literal|"chameleon.do"
argument_list|)
expr_stmt|;
block|}
block|}
decl_stmt|;
name|userLabel
operator|.
name|addClickHandler
argument_list|(
name|c
argument_list|)
expr_stmt|;
name|hint
operator|.
name|addClickHandler
argument_list|(
name|c
argument_list|)
expr_stmt|;
block|}
block|}
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
name|iUserInfo
operator|.
name|clear
argument_list|()
expr_stmt|;
name|iUserInfo
operator|.
name|setHint
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
name|void
name|reloadSolverInfo
parameter_list|(
name|boolean
name|includeSolutionInfo
parameter_list|,
specifier|final
name|Callback
name|callback
parameter_list|)
block|{
name|iService
operator|.
name|getSolverInfo
argument_list|(
name|includeSolutionInfo
argument_list|,
operator|new
name|AsyncCallback
argument_list|<
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onSuccess
parameter_list|(
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|result
parameter_list|)
block|{
name|iSolverInfo
operator|.
name|clear
argument_list|()
expr_stmt|;
name|boolean
name|hasSolver
init|=
literal|false
decl_stmt|;
try|try
block|{
name|iSolverInfo
operator|.
name|setHint
argument_list|(
name|result
argument_list|)
expr_stmt|;
if|if
condition|(
name|result
operator|!=
literal|null
condition|)
block|{
name|HTML
name|userLabel
init|=
operator|new
name|HTML
argument_list|(
name|result
operator|.
name|get
argument_list|(
literal|"1Solver"
argument_list|)
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|userLabel
operator|.
name|setStyleName
argument_list|(
literal|"unitime-SessionSelector"
argument_list|)
expr_stmt|;
name|iSolverInfo
operator|.
name|add
argument_list|(
name|userLabel
argument_list|)
expr_stmt|;
name|HTML
name|hint
init|=
operator|new
name|HTML
argument_list|(
name|result
operator|.
name|get
argument_list|(
literal|"0Type"
argument_list|)
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|hint
operator|.
name|setStyleName
argument_list|(
literal|"unitime-Hint"
argument_list|)
expr_stmt|;
name|iSolverInfo
operator|.
name|add
argument_list|(
name|hint
argument_list|)
expr_stmt|;
specifier|final
name|String
name|type
init|=
name|result
operator|.
name|get
argument_list|(
literal|"0Type"
argument_list|)
decl_stmt|;
name|ClickHandler
name|c
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
name|type
operator|.
name|equals
argument_list|(
literal|"Course Timetabling Solver"
argument_list|)
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
literal|"solver.do"
argument_list|)
expr_stmt|;
if|else if
condition|(
name|type
operator|.
name|equals
argument_list|(
literal|"Examinations Solver"
argument_list|)
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
literal|"examSolver.do"
argument_list|)
expr_stmt|;
else|else
name|ToolBox
operator|.
name|open
argument_list|(
name|GWT
operator|.
name|getHostPageBaseURL
argument_list|()
operator|+
literal|"studentSolver.do"
argument_list|)
expr_stmt|;
block|}
block|}
decl_stmt|;
name|userLabel
operator|.
name|addClickHandler
argument_list|(
name|c
argument_list|)
expr_stmt|;
name|hint
operator|.
name|addClickHandler
argument_list|(
name|c
argument_list|)
expr_stmt|;
name|hasSolver
operator|=
literal|true
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
block|}
name|Timer
name|t
init|=
operator|new
name|Timer
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|run
parameter_list|()
block|{
name|reloadSolverInfo
argument_list|(
name|iSolverInfo
operator|.
name|iHintPanel
operator|.
name|isShowing
argument_list|()
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
block|}
decl_stmt|;
name|t
operator|.
name|schedule
argument_list|(
name|hasSolver
condition|?
literal|1000
else|:
literal|60000
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
name|execute
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
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
name|Timer
name|t
init|=
operator|new
name|Timer
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|run
parameter_list|()
block|{
name|reloadSolverInfo
argument_list|(
name|iSolverInfo
operator|.
name|iHintPanel
operator|.
name|isShowing
argument_list|()
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
block|}
decl_stmt|;
name|t
operator|.
name|schedule
argument_list|(
literal|5000
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
name|execute
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
interface|interface
name|Callback
block|{
specifier|public
name|void
name|execute
parameter_list|(
name|Callback
name|callback
parameter_list|)
function_decl|;
block|}
specifier|public
specifier|static
class|class
name|VerticalPanelWithHint
extends|extends
name|VerticalPanel
block|{
specifier|private
name|PopupPanel
name|iHintPanel
init|=
literal|null
decl_stmt|;
specifier|private
name|Timer
name|iShowHint
decl_stmt|,
name|iHideHint
init|=
literal|null
decl_stmt|;
specifier|private
name|HTML
name|iHint
init|=
literal|null
decl_stmt|;
specifier|private
name|int
name|iX
decl_stmt|,
name|iY
decl_stmt|;
specifier|private
name|Callback
name|iUpdateInfo
init|=
literal|null
decl_stmt|;
specifier|public
name|VerticalPanelWithHint
parameter_list|(
name|Callback
name|updateInfo
parameter_list|)
block|{
name|super
argument_list|()
expr_stmt|;
name|iUpdateInfo
operator|=
name|updateInfo
expr_stmt|;
name|iHint
operator|=
operator|new
name|HTML
argument_list|(
literal|""
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|iHintPanel
operator|=
operator|new
name|PopupPanel
argument_list|()
expr_stmt|;
name|iHintPanel
operator|.
name|setWidget
argument_list|(
name|iHint
argument_list|)
expr_stmt|;
name|iHintPanel
operator|.
name|setStyleName
argument_list|(
literal|"unitime-PopupHint"
argument_list|)
expr_stmt|;
name|sinkEvents
argument_list|(
name|Event
operator|.
name|ONMOUSEOVER
argument_list|)
expr_stmt|;
name|sinkEvents
argument_list|(
name|Event
operator|.
name|ONMOUSEOUT
argument_list|)
expr_stmt|;
name|sinkEvents
argument_list|(
name|Event
operator|.
name|ONMOUSEMOVE
argument_list|)
expr_stmt|;
name|iShowHint
operator|=
operator|new
name|Timer
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|run
parameter_list|()
block|{
name|iUpdateInfo
operator|.
name|execute
argument_list|(
operator|new
name|Callback
argument_list|()
block|{
specifier|public
name|void
name|execute
parameter_list|(
name|Callback
name|callback
parameter_list|)
block|{
name|iHintPanel
operator|.
name|setPopupPositionAndShow
argument_list|(
operator|new
name|PopupPanel
operator|.
name|PositionCallback
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|setPosition
parameter_list|(
name|int
name|offsetWidth
parameter_list|,
name|int
name|offsetHeight
parameter_list|)
block|{
name|int
name|maxX
init|=
name|Window
operator|.
name|getScrollLeft
argument_list|()
operator|+
name|Window
operator|.
name|getClientWidth
argument_list|()
operator|-
name|offsetWidth
operator|-
literal|10
decl_stmt|;
name|iHintPanel
operator|.
name|setPopupPosition
argument_list|(
name|Math
operator|.
name|min
argument_list|(
name|iX
argument_list|,
name|maxX
argument_list|)
argument_list|,
name|iY
argument_list|)
expr_stmt|;
block|}
block|}
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
name|execute
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
block|}
expr_stmt|;
name|iHideHint
operator|=
operator|new
name|Timer
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|run
parameter_list|()
block|{
name|iHintPanel
operator|.
name|hide
argument_list|()
expr_stmt|;
block|}
block|}
expr_stmt|;
block|}
specifier|public
name|void
name|setHint
parameter_list|(
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|hint
parameter_list|)
block|{
name|String
name|html
init|=
literal|""
decl_stmt|;
if|if
condition|(
name|hint
operator|!=
literal|null
operator|&&
operator|!
name|hint
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|html
operator|+=
literal|"<table cellspacing=\"0\" cellpadding=\"3\">"
expr_stmt|;
name|TreeSet
argument_list|<
name|String
argument_list|>
name|keys
init|=
operator|new
name|TreeSet
argument_list|<
name|String
argument_list|>
argument_list|(
name|hint
operator|.
name|keySet
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|key
range|:
name|keys
control|)
block|{
name|String
name|val
init|=
name|hint
operator|.
name|get
argument_list|(
name|key
argument_list|)
decl_stmt|;
if|if
condition|(
name|val
operator|.
name|isEmpty
argument_list|()
condition|)
continue|continue;
name|String
name|style
init|=
literal|""
decl_stmt|;
if|if
condition|(
name|key
operator|.
name|startsWith
argument_list|(
literal|"A"
argument_list|)
condition|)
name|style
operator|=
literal|"border-top: 1px dashed #AB8B00;"
expr_stmt|;
name|html
operator|+=
literal|"<tr><td style=\""
operator|+
name|style
operator|+
literal|"\">"
operator|+
name|key
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
operator|+
literal|":</td><td style=\""
operator|+
name|style
operator|+
literal|"\">"
operator|+
name|val
operator|+
literal|"</td></tr>"
expr_stmt|;
block|}
name|html
operator|+=
literal|"</table>"
expr_stmt|;
block|}
name|iHint
operator|.
name|setHTML
argument_list|(
name|html
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|onBrowserEvent
parameter_list|(
name|Event
name|event
parameter_list|)
block|{
if|if
condition|(
name|iHint
operator|.
name|getText
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
return|return;
name|iX
operator|=
literal|10
operator|+
name|event
operator|.
name|getClientX
argument_list|()
operator|+
name|getElement
argument_list|()
operator|.
name|getOwnerDocument
argument_list|()
operator|.
name|getScrollLeft
argument_list|()
expr_stmt|;
name|iY
operator|=
literal|10
operator|+
name|event
operator|.
name|getClientY
argument_list|()
operator|+
name|getElement
argument_list|()
operator|.
name|getOwnerDocument
argument_list|()
operator|.
name|getScrollTop
argument_list|()
expr_stmt|;
switch|switch
condition|(
name|DOM
operator|.
name|eventGetType
argument_list|(
name|event
argument_list|)
condition|)
block|{
case|case
name|Event
operator|.
name|ONMOUSEMOVE
case|:
if|if
condition|(
name|iHintPanel
operator|.
name|isShowing
argument_list|()
condition|)
block|{
name|int
name|maxX
init|=
name|Window
operator|.
name|getScrollLeft
argument_list|()
operator|+
name|Window
operator|.
name|getClientWidth
argument_list|()
operator|-
name|iHintPanel
operator|.
name|getOffsetWidth
argument_list|()
operator|-
literal|10
decl_stmt|;
name|iHintPanel
operator|.
name|setPopupPosition
argument_list|(
name|Math
operator|.
name|min
argument_list|(
name|iX
argument_list|,
name|maxX
argument_list|)
argument_list|,
name|iY
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|iShowHint
operator|.
name|cancel
argument_list|()
expr_stmt|;
name|iShowHint
operator|.
name|schedule
argument_list|(
literal|1000
argument_list|)
expr_stmt|;
block|}
break|break;
case|case
name|Event
operator|.
name|ONMOUSEOUT
case|:
name|iShowHint
operator|.
name|cancel
argument_list|()
expr_stmt|;
if|if
condition|(
name|iHintPanel
operator|.
name|isShowing
argument_list|()
condition|)
name|iHideHint
operator|.
name|schedule
argument_list|(
literal|1000
argument_list|)
expr_stmt|;
break|break;
block|}
block|}
block|}
block|}
end_class

end_unit

