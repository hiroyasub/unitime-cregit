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
name|sectioning
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
name|events
operator|.
name|SingleDateSelector
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
name|UniTimeDialogBox
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
name|StudentSectioningConstants
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
name|resources
operator|.
name|StudentSectioningResources
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
name|StudentSchedulingPreferencesInterface
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
name|ui
operator|.
name|AbsolutePanel
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
name|ListBox
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|StudentSchedulingPreferencesDialog
extends|extends
name|UniTimeDialogBox
implements|implements
name|TakesValue
argument_list|<
name|StudentSchedulingPreferencesInterface
argument_list|>
block|{
specifier|protected
specifier|static
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
specifier|protected
specifier|static
name|StudentSectioningConstants
name|CONSTANTS
init|=
name|GWT
operator|.
name|create
argument_list|(
name|StudentSectioningConstants
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|protected
specifier|static
specifier|final
name|StudentSectioningResources
name|RESOURCES
init|=
name|GWT
operator|.
name|create
argument_list|(
name|StudentSectioningResources
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|protected
specifier|static
specifier|final
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
name|SimpleForm
name|iForm
decl_stmt|;
specifier|private
name|UniTimeHeaderPanel
name|iFooter
decl_stmt|;
specifier|private
name|SingleDateSelector
name|iDateFrom
decl_stmt|,
name|iDateTo
decl_stmt|;
specifier|private
name|int
name|iDatesLine
decl_stmt|;
specifier|private
name|ListBox
name|iModality
decl_stmt|,
name|iBackToBack
decl_stmt|;
specifier|private
name|P
name|iModalityDesc
decl_stmt|,
name|iBackToBackDesc
decl_stmt|;
specifier|private
name|StudentSchedulingPreferencesInterface
name|iPreferences
decl_stmt|;
specifier|private
name|HTML
name|iCustomNote
decl_stmt|;
specifier|public
name|StudentSchedulingPreferencesDialog
parameter_list|(
name|AcademicSessionProvider
name|sessionProvider
parameter_list|)
block|{
name|super
argument_list|(
literal|true
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|setEscapeToHide
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|addStyleName
argument_list|(
literal|"unitime-StudentSchedulingPreferencesDialog"
argument_list|)
expr_stmt|;
name|setHTML
argument_list|(
literal|"<img src='"
operator|+
name|RESOURCES
operator|.
name|preferences
argument_list|()
operator|.
name|getSafeUri
argument_list|()
operator|.
name|asString
argument_list|()
operator|+
literal|"' class='gwt-Image'></img><span class='gwt-Label' style='padding-left: 5px; vertical-align: top;'>"
operator|+
name|MESSAGES
operator|.
name|dialogStudentSchedulingPreferences
argument_list|()
operator|+
literal|"</span>"
argument_list|)
expr_stmt|;
name|iForm
operator|=
operator|new
name|SimpleForm
argument_list|()
expr_stmt|;
name|iModality
operator|=
operator|new
name|ListBox
argument_list|()
expr_stmt|;
name|iModality
operator|.
name|addItem
argument_list|(
name|MESSAGES
operator|.
name|itemSchedulingModalityNoPreference
argument_list|()
argument_list|,
literal|"NoPreference"
argument_list|)
expr_stmt|;
name|iModality
operator|.
name|addItem
argument_list|(
name|MESSAGES
operator|.
name|itemSchedulingModalityPreferFaceToFace
argument_list|()
argument_list|,
literal|"DiscouragedOnline"
argument_list|)
expr_stmt|;
name|iModality
operator|.
name|addItem
argument_list|(
name|MESSAGES
operator|.
name|itemSchedulingModalityPreferOnline
argument_list|()
argument_list|,
literal|"PreferredOnline"
argument_list|)
expr_stmt|;
name|iModality
operator|.
name|addItem
argument_list|(
name|MESSAGES
operator|.
name|itemSchedulingModalityRequireOnline
argument_list|()
argument_list|,
literal|"RequiredOnline"
argument_list|)
expr_stmt|;
name|iModality
operator|.
name|addStyleName
argument_list|(
literal|"selection"
argument_list|)
expr_stmt|;
name|AbsolutePanel
name|p
init|=
operator|new
name|AbsolutePanel
argument_list|()
decl_stmt|;
name|p
operator|.
name|setStyleName
argument_list|(
literal|"modality"
argument_list|)
expr_stmt|;
name|p
operator|.
name|add
argument_list|(
name|iModality
argument_list|)
expr_stmt|;
name|iModalityDesc
operator|=
operator|new
name|P
argument_list|(
literal|"description"
argument_list|)
expr_stmt|;
name|iModalityDesc
operator|.
name|setText
argument_list|(
name|MESSAGES
operator|.
name|descSchedulingModalityPreferFaceToFace
argument_list|()
argument_list|)
expr_stmt|;
name|p
operator|.
name|add
argument_list|(
name|iModalityDesc
argument_list|)
expr_stmt|;
name|iModality
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
name|modalityChanged
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|iForm
operator|.
name|addRow
argument_list|(
name|MESSAGES
operator|.
name|propSchedulingPrefModality
argument_list|()
argument_list|,
name|p
argument_list|)
expr_stmt|;
name|iBackToBack
operator|=
operator|new
name|ListBox
argument_list|()
expr_stmt|;
name|iBackToBack
operator|.
name|addItem
argument_list|(
name|MESSAGES
operator|.
name|itemSchedulingBackToBackNoPreference
argument_list|()
argument_list|,
literal|"NoPreference"
argument_list|)
expr_stmt|;
name|iBackToBack
operator|.
name|addItem
argument_list|(
name|MESSAGES
operator|.
name|itemSchedulingBackToBackPrefer
argument_list|()
argument_list|,
literal|"PreferBackToBack"
argument_list|)
expr_stmt|;
name|iBackToBack
operator|.
name|addItem
argument_list|(
name|MESSAGES
operator|.
name|itemSchedulingBackToBackDiscourage
argument_list|()
argument_list|,
literal|"DiscourageBackToBack"
argument_list|)
expr_stmt|;
name|iBackToBack
operator|.
name|addStyleName
argument_list|(
literal|"selection"
argument_list|)
expr_stmt|;
name|AbsolutePanel
name|q
init|=
operator|new
name|AbsolutePanel
argument_list|()
decl_stmt|;
name|q
operator|.
name|setStyleName
argument_list|(
literal|"back-to-back"
argument_list|)
expr_stmt|;
name|q
operator|.
name|add
argument_list|(
name|iBackToBack
argument_list|)
expr_stmt|;
name|iBackToBackDesc
operator|=
operator|new
name|P
argument_list|(
literal|"description"
argument_list|)
expr_stmt|;
name|iBackToBackDesc
operator|.
name|setText
argument_list|(
name|MESSAGES
operator|.
name|descSchedulingBackToBackNoPreference
argument_list|()
argument_list|)
expr_stmt|;
name|q
operator|.
name|add
argument_list|(
name|iBackToBackDesc
argument_list|)
expr_stmt|;
name|iBackToBack
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
name|backToBackChanged
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|iForm
operator|.
name|addRow
argument_list|(
name|MESSAGES
operator|.
name|propSchedulingPrefBackToBack
argument_list|()
argument_list|,
name|q
argument_list|)
expr_stmt|;
name|AbsolutePanel
name|m
init|=
operator|new
name|AbsolutePanel
argument_list|()
decl_stmt|;
name|m
operator|.
name|setStyleName
argument_list|(
literal|"dates"
argument_list|)
expr_stmt|;
name|P
name|from
init|=
operator|new
name|P
argument_list|(
literal|"from"
argument_list|)
decl_stmt|;
name|from
operator|.
name|setText
argument_list|(
name|MESSAGES
operator|.
name|propSchedulingPrefDatesFrom
argument_list|()
argument_list|)
expr_stmt|;
name|m
operator|.
name|add
argument_list|(
name|from
argument_list|)
expr_stmt|;
name|iDateFrom
operator|=
operator|new
name|SingleDateSelector
argument_list|(
name|sessionProvider
argument_list|)
expr_stmt|;
name|m
operator|.
name|add
argument_list|(
name|iDateFrom
argument_list|)
expr_stmt|;
name|P
name|to
init|=
operator|new
name|P
argument_list|(
literal|"to"
argument_list|)
decl_stmt|;
name|to
operator|.
name|setText
argument_list|(
name|MESSAGES
operator|.
name|propSchedulingPrefDatesTo
argument_list|()
argument_list|)
expr_stmt|;
name|m
operator|.
name|add
argument_list|(
name|to
argument_list|)
expr_stmt|;
name|iDateTo
operator|=
operator|new
name|SingleDateSelector
argument_list|(
name|sessionProvider
argument_list|)
expr_stmt|;
name|m
operator|.
name|add
argument_list|(
name|iDateTo
argument_list|)
expr_stmt|;
name|P
name|desc
init|=
operator|new
name|P
argument_list|(
literal|"description"
argument_list|)
decl_stmt|;
name|desc
operator|.
name|setText
argument_list|(
name|MESSAGES
operator|.
name|propSchedulingPrefDatesDescription
argument_list|()
argument_list|)
expr_stmt|;
name|m
operator|.
name|add
argument_list|(
name|desc
argument_list|)
expr_stmt|;
name|iDatesLine
operator|=
name|iForm
operator|.
name|addRow
argument_list|(
name|MESSAGES
operator|.
name|propSchedulingPrefDates
argument_list|()
argument_list|,
name|m
argument_list|)
expr_stmt|;
name|iCustomNote
operator|=
operator|new
name|HTML
argument_list|()
expr_stmt|;
name|iCustomNote
operator|.
name|addStyleName
argument_list|(
literal|"custom-note"
argument_list|)
expr_stmt|;
name|iCustomNote
operator|.
name|setText
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|iCustomNote
operator|.
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|iForm
operator|.
name|addRow
argument_list|(
name|iCustomNote
argument_list|)
expr_stmt|;
name|iFooter
operator|=
operator|new
name|UniTimeHeaderPanel
argument_list|()
expr_stmt|;
name|iFooter
operator|.
name|addButton
argument_list|(
literal|"apply"
argument_list|,
name|MESSAGES
operator|.
name|buttonSchedulingPrefApply
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
name|doApply
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|iFooter
operator|.
name|addButton
argument_list|(
literal|"close"
argument_list|,
name|MESSAGES
operator|.
name|buttonSchedulingPrefClose
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
name|hide
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|iForm
operator|.
name|addBottomRow
argument_list|(
name|iFooter
argument_list|)
expr_stmt|;
name|setWidget
argument_list|(
name|iForm
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|modalityChanged
parameter_list|()
block|{
if|if
condition|(
literal|"DiscouragedOnline"
operator|.
name|equals
argument_list|(
name|iModality
operator|.
name|getSelectedValue
argument_list|()
argument_list|)
condition|)
block|{
name|iModalityDesc
operator|.
name|setText
argument_list|(
name|MESSAGES
operator|.
name|descSchedulingModalityPreferFaceToFace
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
literal|"PreferredOnline"
operator|.
name|equals
argument_list|(
name|iModality
operator|.
name|getSelectedValue
argument_list|()
argument_list|)
condition|)
block|{
name|iModalityDesc
operator|.
name|setText
argument_list|(
name|MESSAGES
operator|.
name|descSchedulingModalityPreferOnline
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
literal|"RequiredOnline"
operator|.
name|equals
argument_list|(
name|iModality
operator|.
name|getSelectedValue
argument_list|()
argument_list|)
condition|)
block|{
name|iModalityDesc
operator|.
name|setText
argument_list|(
name|MESSAGES
operator|.
name|descSchedulingModalityRequireOnline
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
literal|"NoPreference"
operator|.
name|equals
argument_list|(
name|iModality
operator|.
name|getSelectedValue
argument_list|()
argument_list|)
condition|)
block|{
name|iModalityDesc
operator|.
name|setText
argument_list|(
name|MESSAGES
operator|.
name|descSchedulingModalityNoPreference
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
specifier|protected
name|void
name|backToBackChanged
parameter_list|()
block|{
if|if
condition|(
literal|"PreferBackToBack"
operator|.
name|equals
argument_list|(
name|iBackToBack
operator|.
name|getSelectedValue
argument_list|()
argument_list|)
condition|)
block|{
name|iBackToBackDesc
operator|.
name|setText
argument_list|(
name|MESSAGES
operator|.
name|descSchedulingBackToBackPrefer
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
literal|"DiscourageBackToBack"
operator|.
name|equals
argument_list|(
name|iBackToBack
operator|.
name|getSelectedValue
argument_list|()
argument_list|)
condition|)
block|{
name|iBackToBackDesc
operator|.
name|setText
argument_list|(
name|MESSAGES
operator|.
name|descSchedulingBackToBackDiscourage
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
literal|"NoPreference"
operator|.
name|equals
argument_list|(
name|iBackToBack
operator|.
name|getSelectedValue
argument_list|()
argument_list|)
condition|)
block|{
name|iBackToBackDesc
operator|.
name|setText
argument_list|(
name|MESSAGES
operator|.
name|descSchedulingBackToBackNoPreference
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|center
parameter_list|()
block|{
name|super
operator|.
name|center
argument_list|()
expr_stmt|;
name|iModality
operator|.
name|setFocus
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|doApply
parameter_list|()
block|{
name|hide
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|StudentSchedulingPreferencesInterface
name|getValue
parameter_list|()
block|{
name|iPreferences
operator|.
name|setClassModality
argument_list|(
name|StudentSchedulingPreferencesInterface
operator|.
name|ClassModality
operator|.
name|valueOf
argument_list|(
name|iModality
operator|.
name|getSelectedValue
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|iPreferences
operator|.
name|setScheduleGaps
argument_list|(
name|StudentSchedulingPreferencesInterface
operator|.
name|ScheduleGaps
operator|.
name|valueOf
argument_list|(
name|iBackToBack
operator|.
name|getSelectedValue
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|iPreferences
operator|.
name|setClassDateFrom
argument_list|(
name|iPreferences
operator|.
name|isAllowClassDates
argument_list|()
condition|?
name|iDateFrom
operator|.
name|getValueInServerTimeZone
argument_list|()
else|:
literal|null
argument_list|)
expr_stmt|;
name|iPreferences
operator|.
name|setClassDateTo
argument_list|(
name|iPreferences
operator|.
name|isAllowClassDates
argument_list|()
condition|?
name|iDateTo
operator|.
name|getValueInServerTimeZone
argument_list|()
else|:
literal|null
argument_list|)
expr_stmt|;
return|return
operator|new
name|StudentSchedulingPreferencesInterface
argument_list|(
name|iPreferences
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|setValue
parameter_list|(
name|StudentSchedulingPreferencesInterface
name|value
parameter_list|)
block|{
name|iPreferences
operator|=
name|value
expr_stmt|;
name|iForm
operator|.
name|getRowFormatter
argument_list|()
operator|.
name|setVisible
argument_list|(
name|iDatesLine
argument_list|,
name|iPreferences
operator|.
name|isAllowClassDates
argument_list|()
argument_list|)
expr_stmt|;
name|iModality
operator|.
name|clear
argument_list|()
expr_stmt|;
name|iModality
operator|.
name|addItem
argument_list|(
name|MESSAGES
operator|.
name|itemSchedulingModalityNoPreference
argument_list|()
argument_list|,
literal|"NoPreference"
argument_list|)
expr_stmt|;
name|iModality
operator|.
name|addItem
argument_list|(
name|MESSAGES
operator|.
name|itemSchedulingModalityPreferFaceToFace
argument_list|()
argument_list|,
literal|"DiscouragedOnline"
argument_list|)
expr_stmt|;
name|iModality
operator|.
name|addItem
argument_list|(
name|MESSAGES
operator|.
name|itemSchedulingModalityPreferOnline
argument_list|()
argument_list|,
literal|"PreferredOnline"
argument_list|)
expr_stmt|;
if|if
condition|(
name|iPreferences
operator|.
name|isAllowRequireOnline
argument_list|()
condition|)
name|iModality
operator|.
name|addItem
argument_list|(
name|MESSAGES
operator|.
name|itemSchedulingModalityRequireOnline
argument_list|()
argument_list|,
literal|"RequiredOnline"
argument_list|)
expr_stmt|;
if|if
condition|(
name|iPreferences
operator|.
name|getClassModality
argument_list|()
operator|!=
literal|null
condition|)
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|iModality
operator|.
name|getItemCount
argument_list|()
condition|;
name|i
operator|++
control|)
if|if
condition|(
name|iPreferences
operator|.
name|getClassModality
argument_list|()
operator|.
name|name
argument_list|()
operator|.
name|equals
argument_list|(
name|iModality
operator|.
name|getValue
argument_list|(
name|i
argument_list|)
argument_list|)
condition|)
block|{
name|iModality
operator|.
name|setSelectedIndex
argument_list|(
name|i
argument_list|)
expr_stmt|;
name|modalityChanged
argument_list|()
expr_stmt|;
break|break;
block|}
if|if
condition|(
name|iPreferences
operator|.
name|getScheduleGaps
argument_list|()
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|iBackToBack
operator|.
name|getItemCount
argument_list|()
condition|;
name|i
operator|++
control|)
if|if
condition|(
name|iPreferences
operator|.
name|getScheduleGaps
argument_list|()
operator|.
name|name
argument_list|()
operator|.
name|equals
argument_list|(
name|iBackToBack
operator|.
name|getValue
argument_list|(
name|i
argument_list|)
argument_list|)
condition|)
block|{
name|iBackToBack
operator|.
name|setSelectedIndex
argument_list|(
name|i
argument_list|)
expr_stmt|;
name|backToBackChanged
argument_list|()
expr_stmt|;
break|break;
block|}
block|}
name|iDateFrom
operator|.
name|setValueInServerTimeZone
argument_list|(
name|iPreferences
operator|.
name|isAllowRequireOnline
argument_list|()
condition|?
name|iPreferences
operator|.
name|getClassDateFrom
argument_list|()
else|:
literal|null
argument_list|)
expr_stmt|;
name|iDateTo
operator|.
name|setValueInServerTimeZone
argument_list|(
name|iPreferences
operator|.
name|isAllowRequireOnline
argument_list|()
condition|?
name|iPreferences
operator|.
name|getClassDateTo
argument_list|()
else|:
literal|null
argument_list|)
expr_stmt|;
if|if
condition|(
name|iPreferences
operator|.
name|hasCustomNote
argument_list|()
condition|)
block|{
name|iCustomNote
operator|.
name|setHTML
argument_list|(
name|iPreferences
operator|.
name|getCustomNote
argument_list|()
argument_list|)
expr_stmt|;
name|iCustomNote
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|iCustomNote
operator|.
name|setHTML
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|iCustomNote
operator|.
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

