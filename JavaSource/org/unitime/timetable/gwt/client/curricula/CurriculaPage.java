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
name|curricula
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
name|Client
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
name|Client
operator|.
name|GwtPageChangeEvent
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
name|curricula
operator|.
name|CurriculumEdit
operator|.
name|EditFinishedEvent
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
name|HorizontalPanelWithHint
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
name|UniTimeTextBox
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
name|services
operator|.
name|CurriculaService
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
name|CurriculaServiceAsync
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
name|CurriculumInterface
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
name|CurriculumInterface
operator|.
name|AcademicAreaInterface
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
name|CurriculumInterface
operator|.
name|AcademicClassificationInterface
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
name|CurriculumInterface
operator|.
name|DepartmentInterface
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
name|event
operator|.
name|dom
operator|.
name|client
operator|.
name|KeyUpEvent
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
name|KeyUpHandler
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
name|Button
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
name|HasVerticalAlignment
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
name|SimplePanel
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
name|CurriculaPage
extends|extends
name|Composite
block|{
specifier|public
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
specifier|private
name|TextBox
name|iFilter
init|=
literal|null
decl_stmt|;
specifier|private
name|Button
name|iSearch
init|=
literal|null
decl_stmt|;
specifier|private
name|Button
name|iNew
init|=
literal|null
decl_stmt|;
specifier|private
name|Button
name|iPrint
init|=
literal|null
decl_stmt|;
specifier|private
name|CurriculaTable
name|iCurriculaTable
init|=
literal|null
decl_stmt|;
specifier|private
name|VerticalPanel
name|iCurriculaPanel
init|=
literal|null
decl_stmt|;
specifier|private
name|SimplePanel
name|iPanel
init|=
literal|null
decl_stmt|;
specifier|private
name|HorizontalPanel
name|iFilterPanel
init|=
literal|null
decl_stmt|;
specifier|private
specifier|final
name|CurriculaServiceAsync
name|iService
init|=
name|GWT
operator|.
name|create
argument_list|(
name|CurriculaService
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|CurriculumEdit
name|iCurriculumPanel
init|=
literal|null
decl_stmt|;
specifier|private
name|ClassificationsEdit
name|iClassificationsEdit
init|=
literal|null
decl_stmt|;
specifier|public
name|CurriculaPage
parameter_list|()
block|{
name|iPanel
operator|=
operator|new
name|SimplePanel
argument_list|()
expr_stmt|;
name|iCurriculaPanel
operator|=
operator|new
name|VerticalPanel
argument_list|()
expr_stmt|;
name|iFilterPanel
operator|=
operator|new
name|HorizontalPanelWithHint
argument_list|(
operator|new
name|HTML
argument_list|(
literal|"Filter curricula by any word from the name, code, or abbreviation<br>of a curricula, academic area, major, or department."
operator|+
literal|"<br><br>You can also use the following tags:"
operator|+
literal|"<ul>"
operator|+
literal|"<li><i>abbv:</i> curriculum abbreviation"
operator|+
literal|"<li><i>name:</i> curriculum name"
operator|+
literal|"<li><i>area:</i> academic area abbreviation or name"
operator|+
literal|"<li><i>major:</i> major code or name"
operator|+
literal|"<li><i>dept:</i> department code, name, or abbreviation"
operator|+
literal|"</ul>Use<i>or</i>,<i>and</i>,<i>not</i>, and brackets to build a boolean query."
operator|+
literal|"<br><br>Example: area: A and (major: AGFN or major: AGMG)"
argument_list|,
literal|false
argument_list|)
argument_list|)
expr_stmt|;
name|iFilterPanel
operator|.
name|setSpacing
argument_list|(
literal|3
argument_list|)
expr_stmt|;
name|Label
name|filterLabel
init|=
operator|new
name|Label
argument_list|(
literal|"Filter:"
argument_list|)
decl_stmt|;
name|iFilterPanel
operator|.
name|add
argument_list|(
name|filterLabel
argument_list|)
expr_stmt|;
name|iFilterPanel
operator|.
name|setCellVerticalAlignment
argument_list|(
name|filterLabel
argument_list|,
name|HasVerticalAlignment
operator|.
name|ALIGN_MIDDLE
argument_list|)
expr_stmt|;
name|iFilter
operator|=
operator|new
name|UniTimeTextBox
argument_list|()
expr_stmt|;
name|iFilter
operator|.
name|setWidth
argument_list|(
literal|"400px"
argument_list|)
expr_stmt|;
name|iFilter
operator|.
name|setHeight
argument_list|(
literal|"26px"
argument_list|)
expr_stmt|;
name|iFilterPanel
operator|.
name|add
argument_list|(
name|iFilter
argument_list|)
expr_stmt|;
name|iSearch
operator|=
operator|new
name|Button
argument_list|(
literal|"<u>S</u>earch"
argument_list|)
expr_stmt|;
name|iSearch
operator|.
name|setAccessKey
argument_list|(
literal|'s'
argument_list|)
expr_stmt|;
name|iSearch
operator|.
name|addStyleName
argument_list|(
literal|"unitime-NoPrint"
argument_list|)
expr_stmt|;
name|iFilterPanel
operator|.
name|add
argument_list|(
name|iSearch
argument_list|)
expr_stmt|;
name|iPrint
operator|=
operator|new
name|Button
argument_list|(
literal|"<u>P</u>rint"
argument_list|)
expr_stmt|;
name|iPrint
operator|.
name|setAccessKey
argument_list|(
literal|'p'
argument_list|)
expr_stmt|;
name|iPrint
operator|.
name|addStyleName
argument_list|(
literal|"unitime-NoPrint"
argument_list|)
expr_stmt|;
name|iFilterPanel
operator|.
name|add
argument_list|(
name|iPrint
argument_list|)
expr_stmt|;
name|iNew
operator|=
operator|new
name|Button
argument_list|(
literal|"<u>A</u>dd New"
argument_list|)
expr_stmt|;
name|iNew
operator|.
name|setAccessKey
argument_list|(
literal|'a'
argument_list|)
expr_stmt|;
name|iNew
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|iNew
operator|.
name|addStyleName
argument_list|(
literal|"unitime-NoPrint"
argument_list|)
expr_stmt|;
name|iFilterPanel
operator|.
name|add
argument_list|(
name|iNew
argument_list|)
expr_stmt|;
name|iService
operator|.
name|canAddCurriculum
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
name|iNew
operator|.
name|setEnabled
argument_list|(
name|result
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|iCurriculaPanel
operator|.
name|add
argument_list|(
name|iFilterPanel
argument_list|)
expr_stmt|;
name|iCurriculaPanel
operator|.
name|setCellHorizontalAlignment
argument_list|(
name|iFilterPanel
argument_list|,
name|HasHorizontalAlignment
operator|.
name|ALIGN_CENTER
argument_list|)
expr_stmt|;
name|iCurriculaTable
operator|=
operator|new
name|CurriculaTable
argument_list|()
expr_stmt|;
name|iCurriculaTable
operator|.
name|getElement
argument_list|()
operator|.
name|getStyle
argument_list|()
operator|.
name|setMarginTop
argument_list|(
literal|10
argument_list|,
name|Unit
operator|.
name|PX
argument_list|)
expr_stmt|;
name|iFilterPanel
operator|.
name|add
argument_list|(
name|iCurriculaTable
operator|.
name|getOperations
argument_list|()
argument_list|)
expr_stmt|;
name|iCurriculaTable
operator|.
name|getOperations
argument_list|()
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|iCurriculaPanel
operator|.
name|add
argument_list|(
name|iCurriculaTable
argument_list|)
expr_stmt|;
name|iCurriculaPanel
operator|.
name|setWidth
argument_list|(
literal|"100%"
argument_list|)
expr_stmt|;
name|iPanel
operator|.
name|setWidget
argument_list|(
name|iCurriculaPanel
argument_list|)
expr_stmt|;
name|iCurriculumPanel
operator|=
operator|new
name|CurriculumEdit
argument_list|(
operator|new
name|CurriculumEdit
operator|.
name|NavigationProvider
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|CurriculumInterface
name|previous
parameter_list|(
name|CurriculumInterface
name|curriculum
parameter_list|)
block|{
return|return
name|iCurriculaTable
operator|.
name|previous
argument_list|(
name|curriculum
operator|==
literal|null
condition|?
literal|null
else|:
name|curriculum
operator|.
name|getId
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|CurriculumInterface
name|next
parameter_list|(
name|CurriculumInterface
name|curriculum
parameter_list|)
block|{
return|return
name|iCurriculaTable
operator|.
name|next
argument_list|(
name|curriculum
operator|==
literal|null
condition|?
literal|null
else|:
name|curriculum
operator|.
name|getId
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|onChange
parameter_list|(
name|CurriculumInterface
name|curriculum
parameter_list|)
block|{
if|if
condition|(
name|curriculum
operator|.
name|getId
argument_list|()
operator|!=
literal|null
condition|)
name|History
operator|.
name|newItem
argument_list|(
literal|"detail="
operator|+
name|curriculum
operator|.
name|getId
argument_list|()
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|iClassificationsEdit
operator|=
operator|new
name|ClassificationsEdit
argument_list|()
expr_stmt|;
name|initWidget
argument_list|(
name|iPanel
argument_list|)
expr_stmt|;
name|iSearch
operator|.
name|addClickHandler
argument_list|(
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
name|loadCurricula
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|iFilter
operator|.
name|addKeyUpHandler
argument_list|(
operator|new
name|KeyUpHandler
argument_list|()
block|{
specifier|public
name|void
name|onKeyUp
parameter_list|(
name|KeyUpEvent
name|event
parameter_list|)
block|{
if|if
condition|(
name|event
operator|.
name|getNativeKeyCode
argument_list|()
operator|==
name|KeyCodes
operator|.
name|KEY_ENTER
condition|)
name|loadCurricula
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|iPrint
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
name|Window
operator|.
name|print
argument_list|()
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
name|iFilter
operator|.
name|setFocus
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
if|if
condition|(
name|Window
operator|.
name|Location
operator|.
name|getParameter
argument_list|(
literal|"q"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|iFilter
operator|.
name|setText
argument_list|(
name|Window
operator|.
name|Location
operator|.
name|getParameter
argument_list|(
literal|"q"
argument_list|)
argument_list|)
expr_stmt|;
name|loadCurricula
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|showLoading
argument_list|(
literal|"Loading curricula ..."
argument_list|)
expr_stmt|;
name|iService
operator|.
name|lastCurriculaFilter
argument_list|(
operator|new
name|AsyncCallback
argument_list|<
name|String
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onSuccess
parameter_list|(
name|String
name|result
parameter_list|)
block|{
if|if
condition|(
name|iFilter
operator|.
name|getText
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|iFilter
operator|.
name|setText
argument_list|(
name|result
argument_list|)
expr_stmt|;
name|loadCurricula
argument_list|()
expr_stmt|;
block|}
name|hideLoading
argument_list|()
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
name|iCurriculaTable
operator|.
name|setError
argument_list|(
literal|"Unable to retrieve curricula ("
operator|+
name|caught
operator|.
name|getMessage
argument_list|()
operator|+
literal|")."
argument_list|)
expr_stmt|;
name|hideLoading
argument_list|()
expr_stmt|;
name|ToolBox
operator|.
name|checkAccess
argument_list|(
name|caught
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
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
if|if
condition|(
name|event
operator|.
name|getValue
argument_list|()
operator|==
literal|null
operator|||
literal|"0:0"
operator|.
name|equals
argument_list|(
name|event
operator|.
name|getValue
argument_list|()
argument_list|)
condition|)
return|return;
name|String
name|command
init|=
name|event
operator|.
name|getValue
argument_list|()
decl_stmt|;
if|if
condition|(
name|command
operator|.
name|startsWith
argument_list|(
literal|"detail="
argument_list|)
condition|)
block|{
name|showLoading
argument_list|(
literal|"Loading curriculum ..."
argument_list|)
expr_stmt|;
name|iService
operator|.
name|loadCurriculum
argument_list|(
name|Long
operator|.
name|parseLong
argument_list|(
name|command
operator|.
name|substring
argument_list|(
literal|"detail="
operator|.
name|length
argument_list|()
argument_list|)
argument_list|)
argument_list|,
operator|new
name|AsyncCallback
argument_list|<
name|CurriculumInterface
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
name|hideLoading
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|onSuccess
parameter_list|(
name|CurriculumInterface
name|result
parameter_list|)
block|{
name|iCurriculumPanel
operator|.
name|edit
argument_list|(
name|result
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|iPanel
operator|.
name|setWidget
argument_list|(
name|iCurriculumPanel
argument_list|)
expr_stmt|;
name|hideLoading
argument_list|()
expr_stmt|;
name|Client
operator|.
name|fireGwtPageChanged
argument_list|(
operator|new
name|GwtPageChangeEvent
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
literal|"new"
operator|.
name|equals
argument_list|(
name|command
argument_list|)
condition|)
block|{
name|UniTimePageLabel
operator|.
name|getInstance
argument_list|()
operator|.
name|setPageName
argument_list|(
literal|"Add Curriculum"
argument_list|)
expr_stmt|;
name|iCurriculumPanel
operator|.
name|addNew
argument_list|()
expr_stmt|;
name|iPanel
operator|.
name|setWidget
argument_list|(
name|iCurriculumPanel
argument_list|)
expr_stmt|;
name|Client
operator|.
name|fireGwtPageChanged
argument_list|(
operator|new
name|GwtPageChangeEvent
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
operator|!
literal|"requests"
operator|.
name|equals
argument_list|(
name|command
argument_list|)
condition|)
name|iFilter
operator|.
name|setText
argument_list|(
name|command
operator|.
name|replace
argument_list|(
literal|"%20"
argument_list|,
literal|" "
argument_list|)
argument_list|)
expr_stmt|;
name|loadCurricula
argument_list|()
expr_stmt|;
if|if
condition|(
name|iCurriculumPanel
operator|.
name|isVisible
argument_list|()
condition|)
block|{
name|UniTimePageLabel
operator|.
name|getInstance
argument_list|()
operator|.
name|setPageName
argument_list|(
literal|"Curricula"
argument_list|)
expr_stmt|;
name|iPanel
operator|.
name|setWidget
argument_list|(
name|iCurriculaPanel
argument_list|)
expr_stmt|;
name|iCurriculaTable
operator|.
name|scrollIntoView
argument_list|()
expr_stmt|;
name|Client
operator|.
name|fireGwtPageChanged
argument_list|(
operator|new
name|GwtPageChangeEvent
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
argument_list|)
expr_stmt|;
name|iCurriculaTable
operator|.
name|addCurriculumClickHandler
argument_list|(
operator|new
name|CurriculaTable
operator|.
name|CurriculumClickHandler
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onClick
parameter_list|(
name|CurriculaTable
operator|.
name|CurriculumClickedEvent
name|evt
parameter_list|)
block|{
name|showLoading
argument_list|(
literal|"Loading curriculum "
operator|+
name|evt
operator|.
name|getCurriculum
argument_list|()
operator|.
name|getName
argument_list|()
operator|+
literal|" ..."
argument_list|)
expr_stmt|;
name|iService
operator|.
name|loadCurriculum
argument_list|(
name|evt
operator|.
name|getCurriculum
argument_list|()
operator|.
name|getId
argument_list|()
argument_list|,
operator|new
name|AsyncCallback
argument_list|<
name|CurriculumInterface
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
name|hideLoading
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|onSuccess
parameter_list|(
name|CurriculumInterface
name|result
parameter_list|)
block|{
name|History
operator|.
name|newItem
argument_list|(
literal|"detail="
operator|+
name|result
operator|.
name|getId
argument_list|()
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|iCurriculumPanel
operator|.
name|edit
argument_list|(
name|result
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|iPanel
operator|.
name|setWidget
argument_list|(
name|iCurriculumPanel
argument_list|)
expr_stmt|;
name|hideLoading
argument_list|()
expr_stmt|;
name|Client
operator|.
name|fireGwtPageChanged
argument_list|(
operator|new
name|GwtPageChangeEvent
argument_list|()
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
name|iNew
operator|.
name|addClickHandler
argument_list|(
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
name|History
operator|.
name|newItem
argument_list|(
literal|"new"
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|UniTimePageLabel
operator|.
name|getInstance
argument_list|()
operator|.
name|setPageName
argument_list|(
literal|"Add Curriculum"
argument_list|)
expr_stmt|;
name|iCurriculumPanel
operator|.
name|addNew
argument_list|()
expr_stmt|;
name|iPanel
operator|.
name|setWidget
argument_list|(
name|iCurriculumPanel
argument_list|)
expr_stmt|;
name|Client
operator|.
name|fireGwtPageChanged
argument_list|(
operator|new
name|GwtPageChangeEvent
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|iCurriculaTable
operator|.
name|setEditClassificationHandler
argument_list|(
operator|new
name|CurriculaTable
operator|.
name|EditClassificationHandler
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|doEdit
parameter_list|(
name|List
argument_list|<
name|CurriculumInterface
argument_list|>
name|curricula
parameter_list|)
block|{
name|iClassificationsEdit
operator|.
name|setData
argument_list|(
name|curricula
argument_list|)
expr_stmt|;
name|UniTimePageLabel
operator|.
name|getInstance
argument_list|()
operator|.
name|setPageName
argument_list|(
literal|"Curriculum Requested Enrollments"
argument_list|)
expr_stmt|;
name|iPanel
operator|.
name|setWidget
argument_list|(
name|iClassificationsEdit
argument_list|)
expr_stmt|;
name|Client
operator|.
name|fireGwtPageChanged
argument_list|(
operator|new
name|GwtPageChangeEvent
argument_list|()
argument_list|)
expr_stmt|;
name|History
operator|.
name|newItem
argument_list|(
literal|"requests"
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|iCurriculumPanel
operator|.
name|addEditFinishedHandler
argument_list|(
operator|new
name|CurriculumEdit
operator|.
name|EditFinishedHandler
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onSave
parameter_list|(
name|EditFinishedEvent
name|evt
parameter_list|)
block|{
name|UniTimePageLabel
operator|.
name|getInstance
argument_list|()
operator|.
name|setPageName
argument_list|(
literal|"Curricula"
argument_list|)
expr_stmt|;
name|iPanel
operator|.
name|setWidget
argument_list|(
name|iCurriculaPanel
argument_list|)
expr_stmt|;
name|loadCurricula
argument_list|()
expr_stmt|;
name|Client
operator|.
name|fireGwtPageChanged
argument_list|(
operator|new
name|GwtPageChangeEvent
argument_list|()
argument_list|)
expr_stmt|;
name|History
operator|.
name|newItem
argument_list|(
name|iFilter
operator|.
name|getText
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
name|onDelete
parameter_list|(
name|EditFinishedEvent
name|evt
parameter_list|)
block|{
name|UniTimePageLabel
operator|.
name|getInstance
argument_list|()
operator|.
name|setPageName
argument_list|(
literal|"Curricula"
argument_list|)
expr_stmt|;
name|iPanel
operator|.
name|setWidget
argument_list|(
name|iCurriculaPanel
argument_list|)
expr_stmt|;
name|loadCurricula
argument_list|()
expr_stmt|;
name|Client
operator|.
name|fireGwtPageChanged
argument_list|(
operator|new
name|GwtPageChangeEvent
argument_list|()
argument_list|)
expr_stmt|;
name|History
operator|.
name|newItem
argument_list|(
name|iFilter
operator|.
name|getText
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
name|onBack
parameter_list|(
name|EditFinishedEvent
name|evt
parameter_list|)
block|{
name|UniTimePageLabel
operator|.
name|getInstance
argument_list|()
operator|.
name|setPageName
argument_list|(
literal|"Curricula"
argument_list|)
expr_stmt|;
name|iPanel
operator|.
name|setWidget
argument_list|(
name|iCurriculaPanel
argument_list|)
expr_stmt|;
name|iCurriculaTable
operator|.
name|scrollIntoView
argument_list|()
expr_stmt|;
name|Client
operator|.
name|fireGwtPageChanged
argument_list|(
operator|new
name|GwtPageChangeEvent
argument_list|()
argument_list|)
expr_stmt|;
name|History
operator|.
name|newItem
argument_list|(
name|iFilter
operator|.
name|getText
argument_list|()
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|iClassificationsEdit
operator|.
name|addEditFinishedHandler
argument_list|(
operator|new
name|ClassificationsEdit
operator|.
name|EditFinishedHandler
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onSave
parameter_list|(
name|ClassificationsEdit
operator|.
name|EditFinishedEvent
name|evt
parameter_list|)
block|{
name|UniTimePageLabel
operator|.
name|getInstance
argument_list|()
operator|.
name|setPageName
argument_list|(
literal|"Curricula"
argument_list|)
expr_stmt|;
name|iPanel
operator|.
name|setWidget
argument_list|(
name|iCurriculaPanel
argument_list|)
expr_stmt|;
name|loadCurricula
argument_list|()
expr_stmt|;
name|Client
operator|.
name|fireGwtPageChanged
argument_list|(
operator|new
name|GwtPageChangeEvent
argument_list|()
argument_list|)
expr_stmt|;
name|History
operator|.
name|newItem
argument_list|(
name|iFilter
operator|.
name|getText
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
name|onBack
parameter_list|(
name|ClassificationsEdit
operator|.
name|EditFinishedEvent
name|evt
parameter_list|)
block|{
name|UniTimePageLabel
operator|.
name|getInstance
argument_list|()
operator|.
name|setPageName
argument_list|(
literal|"Curricula"
argument_list|)
expr_stmt|;
name|iPanel
operator|.
name|setWidget
argument_list|(
name|iCurriculaPanel
argument_list|)
expr_stmt|;
name|iCurriculaTable
operator|.
name|scrollIntoView
argument_list|()
expr_stmt|;
name|Client
operator|.
name|fireGwtPageChanged
argument_list|(
operator|new
name|GwtPageChangeEvent
argument_list|()
argument_list|)
expr_stmt|;
name|History
operator|.
name|newItem
argument_list|(
name|iFilter
operator|.
name|getText
argument_list|()
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|iService
operator|.
name|loadAcademicAreas
argument_list|(
operator|new
name|AsyncCallback
argument_list|<
name|TreeSet
argument_list|<
name|AcademicAreaInterface
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
block|}
annotation|@
name|Override
specifier|public
name|void
name|onSuccess
parameter_list|(
name|TreeSet
argument_list|<
name|AcademicAreaInterface
argument_list|>
name|result
parameter_list|)
block|{
name|iCurriculumPanel
operator|.
name|setupAreas
argument_list|(
name|result
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|iService
operator|.
name|loadDepartments
argument_list|(
operator|new
name|AsyncCallback
argument_list|<
name|TreeSet
argument_list|<
name|DepartmentInterface
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
block|}
annotation|@
name|Override
specifier|public
name|void
name|onSuccess
parameter_list|(
name|TreeSet
argument_list|<
name|DepartmentInterface
argument_list|>
name|result
parameter_list|)
block|{
name|iCurriculumPanel
operator|.
name|setupDepartments
argument_list|(
name|result
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|iService
operator|.
name|loadAcademicClassifications
argument_list|(
operator|new
name|AsyncCallback
argument_list|<
name|TreeSet
argument_list|<
name|AcademicClassificationInterface
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
block|}
annotation|@
name|Override
specifier|public
name|void
name|onSuccess
parameter_list|(
name|TreeSet
argument_list|<
name|AcademicClassificationInterface
argument_list|>
name|result
parameter_list|)
block|{
name|iCurriculumPanel
operator|.
name|setupClassifications
argument_list|(
name|result
argument_list|)
expr_stmt|;
name|iCurriculaTable
operator|.
name|setup
argument_list|(
operator|new
name|ArrayList
argument_list|<
name|AcademicClassificationInterface
argument_list|>
argument_list|(
name|result
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
specifier|private
name|void
name|loadCurricula
parameter_list|()
block|{
if|if
condition|(
operator|!
name|iSearch
operator|.
name|isEnabled
argument_list|()
condition|)
return|return;
name|iSearch
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|iPrint
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|iCurriculaTable
operator|.
name|getOperations
argument_list|()
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
specifier|final
name|boolean
name|newEnabled
init|=
name|iNew
operator|.
name|isEnabled
argument_list|()
decl_stmt|;
if|if
condition|(
name|newEnabled
condition|)
name|iNew
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|History
operator|.
name|newItem
argument_list|(
name|iFilter
operator|.
name|getText
argument_list|()
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|iCurriculaTable
operator|.
name|query
argument_list|(
name|iFilter
operator|.
name|getText
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
name|iSearch
operator|.
name|setEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|iPrint
operator|.
name|setEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|iCurriculaTable
operator|.
name|getOperations
argument_list|()
operator|.
name|setEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
if|if
condition|(
name|newEnabled
condition|)
name|iNew
operator|.
name|setEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|showLoading
parameter_list|(
name|String
name|message
parameter_list|)
block|{
name|LoadingWidget
operator|.
name|getInstance
argument_list|()
operator|.
name|show
argument_list|(
name|message
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|hideLoading
parameter_list|()
block|{
name|LoadingWidget
operator|.
name|getInstance
argument_list|()
operator|.
name|hide
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

