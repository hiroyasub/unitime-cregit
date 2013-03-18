begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.4 (University Timetabling Application)  * Copyright (C) 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|reservations
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
name|Date
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
name|events
operator|.
name|UniTimeFilterBox
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
name|shared
operator|.
name|ReservationInterface
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
name|i18n
operator|.
name|client
operator|.
name|DateTimeFormat
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
name|ListBox
import|;
end_import

begin_class
specifier|public
class|class
name|ReservationFilterBox
extends|extends
name|UniTimeFilterBox
block|{
specifier|private
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
specifier|static
name|DateTimeFormat
name|sDateFormat
init|=
name|DateTimeFormat
operator|.
name|getFormat
argument_list|(
name|CONSTANTS
operator|.
name|eventDateFormat
argument_list|()
argument_list|)
decl_stmt|;
specifier|private
name|ListBox
name|iDepartments
decl_stmt|,
name|iSubjects
decl_stmt|;
specifier|public
name|ReservationFilterBox
parameter_list|()
block|{
name|super
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|setShowSuggestionsOnFocus
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|FilterBox
operator|.
name|StaticSimpleFilter
name|mode
init|=
operator|new
name|FilterBox
operator|.
name|StaticSimpleFilter
argument_list|(
literal|"mode"
argument_list|)
decl_stmt|;
name|mode
operator|.
name|setMultipleSelection
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|addFilter
argument_list|(
name|mode
argument_list|)
expr_stmt|;
name|addFilter
argument_list|(
operator|new
name|FilterBox
operator|.
name|StaticSimpleFilter
argument_list|(
literal|"type"
argument_list|)
argument_list|)
expr_stmt|;
name|iDepartments
operator|=
operator|new
name|ListBox
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|iDepartments
operator|.
name|setWidth
argument_list|(
literal|"100%"
argument_list|)
expr_stmt|;
name|addFilter
argument_list|(
operator|new
name|FilterBox
operator|.
name|CustomFilter
argument_list|(
literal|"department"
argument_list|,
name|iDepartments
argument_list|)
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
if|if
condition|(
name|text
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|callback
operator|.
name|onSuccess
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|Chip
name|oldChip
init|=
name|getChip
argument_list|(
literal|"department"
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|Suggestion
argument_list|>
name|suggestions
init|=
operator|new
name|ArrayList
argument_list|<
name|Suggestion
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|iDepartments
operator|.
name|getItemCount
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|iDepartments
operator|.
name|getItemText
argument_list|(
name|i
argument_list|)
operator|.
name|endsWith
argument_list|(
literal|" (0)"
argument_list|)
condition|)
continue|continue;
name|Chip
name|chip
init|=
operator|new
name|Chip
argument_list|(
literal|"department"
argument_list|,
name|iDepartments
operator|.
name|getValue
argument_list|(
name|i
argument_list|)
argument_list|)
decl_stmt|;
name|String
name|name
init|=
name|iDepartments
operator|.
name|getItemText
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
name|iDepartments
operator|.
name|getValue
argument_list|(
name|i
argument_list|)
operator|.
name|toLowerCase
argument_list|()
operator|.
name|startsWith
argument_list|(
name|text
operator|.
name|toLowerCase
argument_list|()
argument_list|)
condition|)
block|{
name|suggestions
operator|.
name|add
argument_list|(
operator|new
name|Suggestion
argument_list|(
name|name
argument_list|,
name|chip
argument_list|,
name|oldChip
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|text
operator|.
name|length
argument_list|()
operator|>
literal|2
operator|&&
operator|(
name|name
operator|.
name|toLowerCase
argument_list|()
operator|.
name|contains
argument_list|(
literal|" "
operator|+
name|text
operator|.
name|toLowerCase
argument_list|()
argument_list|)
operator|||
name|name
operator|.
name|toLowerCase
argument_list|()
operator|.
name|contains
argument_list|(
literal|" ("
operator|+
name|text
operator|.
name|toLowerCase
argument_list|()
argument_list|)
operator|)
condition|)
block|{
name|suggestions
operator|.
name|add
argument_list|(
operator|new
name|Suggestion
argument_list|(
name|name
argument_list|,
name|chip
argument_list|,
name|oldChip
argument_list|)
argument_list|)
expr_stmt|;
block|}
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
block|}
argument_list|)
expr_stmt|;
name|iDepartments
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
name|Chip
name|subjectChip
init|=
name|getChip
argument_list|(
literal|"subject"
argument_list|)
decl_stmt|;
while|while
condition|(
name|subjectChip
operator|!=
literal|null
operator|&&
name|iDepartments
operator|.
name|getSelectedIndex
argument_list|()
operator|>
literal|1
condition|)
block|{
name|removeChip
argument_list|(
name|subjectChip
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|subjectChip
operator|=
name|getChip
argument_list|(
literal|"subject"
argument_list|)
expr_stmt|;
block|}
name|Chip
name|oldChip
init|=
name|getChip
argument_list|(
literal|"department"
argument_list|)
decl_stmt|;
name|Chip
name|newChip
init|=
operator|(
name|iDepartments
operator|.
name|getSelectedIndex
argument_list|()
operator|<=
literal|0
condition|?
literal|null
else|:
operator|new
name|Chip
argument_list|(
literal|"department"
argument_list|,
name|iDepartments
operator|.
name|getValue
argument_list|(
name|iDepartments
operator|.
name|getSelectedIndex
argument_list|()
argument_list|)
argument_list|)
operator|)
decl_stmt|;
if|if
condition|(
name|oldChip
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|newChip
operator|==
literal|null
condition|)
block|{
name|removeChip
argument_list|(
name|oldChip
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
operator|!
name|oldChip
operator|.
name|getValue
argument_list|()
operator|.
name|equals
argument_list|(
name|newChip
operator|.
name|getValue
argument_list|()
argument_list|)
condition|)
block|{
name|removeChip
argument_list|(
name|oldChip
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|addChip
argument_list|(
name|newChip
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
block|}
else|else
block|{
if|if
condition|(
name|newChip
operator|!=
literal|null
condition|)
name|addChip
argument_list|(
name|newChip
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
block|}
argument_list|)
expr_stmt|;
name|iSubjects
operator|=
operator|new
name|ListBox
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|iSubjects
operator|.
name|setWidth
argument_list|(
literal|"100%"
argument_list|)
expr_stmt|;
name|iSubjects
operator|.
name|setVisibleItemCount
argument_list|(
literal|3
argument_list|)
expr_stmt|;
name|addFilter
argument_list|(
operator|new
name|FilterBox
operator|.
name|CustomFilter
argument_list|(
literal|"subject"
argument_list|,
name|iSubjects
argument_list|)
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
if|if
condition|(
name|text
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|callback
operator|.
name|onSuccess
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|Chip
name|deptChip
init|=
name|getChip
argument_list|(
literal|"department"
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|Suggestion
argument_list|>
name|suggestions
init|=
operator|new
name|ArrayList
argument_list|<
name|Suggestion
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|iSubjects
operator|.
name|getItemCount
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|iSubjects
operator|.
name|getItemText
argument_list|(
name|i
argument_list|)
operator|.
name|endsWith
argument_list|(
literal|" (0)"
argument_list|)
condition|)
continue|continue;
name|Chip
name|chip
init|=
operator|new
name|Chip
argument_list|(
literal|"subject"
argument_list|,
name|iSubjects
operator|.
name|getValue
argument_list|(
name|i
argument_list|)
argument_list|)
decl_stmt|;
name|String
name|name
init|=
name|iSubjects
operator|.
name|getItemText
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
name|iSubjects
operator|.
name|getValue
argument_list|(
name|i
argument_list|)
operator|.
name|toLowerCase
argument_list|()
operator|.
name|startsWith
argument_list|(
name|text
operator|.
name|toLowerCase
argument_list|()
argument_list|)
condition|)
block|{
name|suggestions
operator|.
name|add
argument_list|(
operator|new
name|Suggestion
argument_list|(
name|name
argument_list|,
name|chip
argument_list|,
name|deptChip
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|text
operator|.
name|length
argument_list|()
operator|>
literal|2
operator|&&
operator|(
name|name
operator|.
name|toLowerCase
argument_list|()
operator|.
name|contains
argument_list|(
literal|" "
operator|+
name|text
operator|.
name|toLowerCase
argument_list|()
argument_list|)
operator|||
name|name
operator|.
name|toLowerCase
argument_list|()
operator|.
name|contains
argument_list|(
literal|" ("
operator|+
name|text
operator|.
name|toLowerCase
argument_list|()
argument_list|)
operator|)
condition|)
block|{
name|suggestions
operator|.
name|add
argument_list|(
operator|new
name|Suggestion
argument_list|(
name|name
argument_list|,
name|chip
argument_list|,
name|deptChip
argument_list|)
argument_list|)
expr_stmt|;
block|}
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
block|}
argument_list|)
expr_stmt|;
name|iSubjects
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
name|boolean
name|changed
init|=
literal|false
decl_stmt|;
name|Chip
name|deptChip
init|=
name|getChip
argument_list|(
literal|"department"
argument_list|)
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|iSubjects
operator|.
name|getItemCount
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|Chip
name|chip
init|=
operator|new
name|Chip
argument_list|(
literal|"subject"
argument_list|,
name|iSubjects
operator|.
name|getValue
argument_list|(
name|i
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|iSubjects
operator|.
name|isItemSelected
argument_list|(
name|i
argument_list|)
condition|)
block|{
if|if
condition|(
operator|!
name|hasChip
argument_list|(
name|chip
argument_list|)
condition|)
block|{
name|addChip
argument_list|(
name|chip
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|changed
operator|=
literal|true
expr_stmt|;
block|}
if|if
condition|(
name|deptChip
operator|!=
literal|null
operator|&&
name|iSubjects
operator|.
name|getSelectedIndex
argument_list|()
operator|>
literal|1
condition|)
block|{
name|removeChip
argument_list|(
name|deptChip
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|deptChip
operator|=
literal|null
expr_stmt|;
name|changed
operator|=
literal|true
expr_stmt|;
block|}
block|}
else|else
block|{
if|if
condition|(
name|hasChip
argument_list|(
name|chip
argument_list|)
condition|)
block|{
name|removeChip
argument_list|(
name|chip
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|changed
operator|=
literal|true
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
name|changed
condition|)
name|fireValueChangeEvent
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|addFilter
argument_list|(
operator|new
name|FilterBox
operator|.
name|StaticSimpleFilter
argument_list|(
literal|"area"
argument_list|)
argument_list|)
expr_stmt|;
name|addFilter
argument_list|(
operator|new
name|FilterBox
operator|.
name|StaticSimpleFilter
argument_list|(
literal|"group"
argument_list|)
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
literal|"unitime-DateSelector"
argument_list|)
expr_stmt|;
specifier|final
name|SingleDateSelector
operator|.
name|SingleMonth
name|m1
init|=
operator|new
name|SingleDateSelector
operator|.
name|SingleMonth
argument_list|(
literal|"Before"
argument_list|)
decl_stmt|;
name|m1
operator|.
name|setAllowDeselect
argument_list|(
literal|true
argument_list|)
expr_stmt|;
specifier|final
name|SingleDateSelector
operator|.
name|SingleMonth
name|m2
init|=
operator|new
name|SingleDateSelector
operator|.
name|SingleMonth
argument_list|(
literal|"After"
argument_list|)
decl_stmt|;
name|m2
operator|.
name|setAllowDeselect
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|m
operator|.
name|add
argument_list|(
name|m2
argument_list|)
expr_stmt|;
name|m
operator|.
name|add
argument_list|(
name|m1
argument_list|)
expr_stmt|;
name|addFilter
argument_list|(
operator|new
name|FilterBox
operator|.
name|CustomFilter
argument_list|(
literal|"date"
argument_list|,
name|m
argument_list|)
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
name|Chip
name|chFrom
init|=
literal|null
decl_stmt|,
name|chTo
init|=
literal|null
decl_stmt|;
for|for
control|(
name|Chip
name|c
range|:
name|chips
control|)
block|{
if|if
condition|(
name|c
operator|.
name|getCommand
argument_list|()
operator|.
name|equals
argument_list|(
literal|"before"
argument_list|)
condition|)
name|chFrom
operator|=
name|c
expr_stmt|;
if|if
condition|(
name|c
operator|.
name|getCommand
argument_list|()
operator|.
name|equals
argument_list|(
literal|"after"
argument_list|)
condition|)
name|chTo
operator|=
name|c
expr_stmt|;
block|}
try|try
block|{
name|Date
name|date
init|=
name|DateTimeFormat
operator|.
name|getFormat
argument_list|(
literal|"MM/dd"
argument_list|)
operator|.
name|parse
argument_list|(
name|text
argument_list|)
decl_stmt|;
name|suggestions
operator|.
name|add
argument_list|(
operator|new
name|FilterBox
operator|.
name|Suggestion
argument_list|(
operator|new
name|Chip
argument_list|(
literal|"before"
argument_list|,
name|sDateFormat
operator|.
name|format
argument_list|(
name|date
argument_list|)
argument_list|)
argument_list|,
name|chFrom
argument_list|)
argument_list|)
expr_stmt|;
name|suggestions
operator|.
name|add
argument_list|(
operator|new
name|FilterBox
operator|.
name|Suggestion
argument_list|(
operator|new
name|Chip
argument_list|(
literal|"after"
argument_list|,
name|sDateFormat
operator|.
name|format
argument_list|(
name|date
argument_list|)
argument_list|)
argument_list|,
name|chTo
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
block|}
try|try
block|{
name|Date
name|date
init|=
name|DateTimeFormat
operator|.
name|getFormat
argument_list|(
literal|"dd.MM"
argument_list|)
operator|.
name|parse
argument_list|(
name|text
argument_list|)
decl_stmt|;
name|suggestions
operator|.
name|add
argument_list|(
operator|new
name|FilterBox
operator|.
name|Suggestion
argument_list|(
operator|new
name|Chip
argument_list|(
literal|"before"
argument_list|,
name|sDateFormat
operator|.
name|format
argument_list|(
name|date
argument_list|)
argument_list|)
argument_list|,
name|chFrom
argument_list|)
argument_list|)
expr_stmt|;
name|suggestions
operator|.
name|add
argument_list|(
operator|new
name|FilterBox
operator|.
name|Suggestion
argument_list|(
operator|new
name|Chip
argument_list|(
literal|"after"
argument_list|,
name|sDateFormat
operator|.
name|format
argument_list|(
name|date
argument_list|)
argument_list|)
argument_list|,
name|chTo
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
block|}
try|try
block|{
name|Date
name|date
init|=
name|DateTimeFormat
operator|.
name|getFormat
argument_list|(
literal|"MM/dd/yy"
argument_list|)
operator|.
name|parse
argument_list|(
name|text
argument_list|)
decl_stmt|;
name|suggestions
operator|.
name|add
argument_list|(
operator|new
name|FilterBox
operator|.
name|Suggestion
argument_list|(
operator|new
name|Chip
argument_list|(
literal|"before"
argument_list|,
name|sDateFormat
operator|.
name|format
argument_list|(
name|date
argument_list|)
argument_list|)
argument_list|,
name|chFrom
argument_list|)
argument_list|)
expr_stmt|;
name|suggestions
operator|.
name|add
argument_list|(
operator|new
name|FilterBox
operator|.
name|Suggestion
argument_list|(
operator|new
name|Chip
argument_list|(
literal|"after"
argument_list|,
name|sDateFormat
operator|.
name|format
argument_list|(
name|date
argument_list|)
argument_list|)
argument_list|,
name|chTo
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
block|}
try|try
block|{
name|Date
name|date
init|=
name|DateTimeFormat
operator|.
name|getFormat
argument_list|(
literal|"dd.MM.yy"
argument_list|)
operator|.
name|parse
argument_list|(
name|text
argument_list|)
decl_stmt|;
name|suggestions
operator|.
name|add
argument_list|(
operator|new
name|FilterBox
operator|.
name|Suggestion
argument_list|(
operator|new
name|Chip
argument_list|(
literal|"before"
argument_list|,
name|sDateFormat
operator|.
name|format
argument_list|(
name|date
argument_list|)
argument_list|)
argument_list|,
name|chFrom
argument_list|)
argument_list|)
expr_stmt|;
name|suggestions
operator|.
name|add
argument_list|(
operator|new
name|FilterBox
operator|.
name|Suggestion
argument_list|(
operator|new
name|Chip
argument_list|(
literal|"after"
argument_list|,
name|sDateFormat
operator|.
name|format
argument_list|(
name|date
argument_list|)
argument_list|)
argument_list|,
name|chTo
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
block|}
try|try
block|{
name|Date
name|date
init|=
name|DateTimeFormat
operator|.
name|getFormat
argument_list|(
literal|"MMM dd"
argument_list|)
operator|.
name|parse
argument_list|(
name|text
argument_list|)
decl_stmt|;
name|suggestions
operator|.
name|add
argument_list|(
operator|new
name|FilterBox
operator|.
name|Suggestion
argument_list|(
operator|new
name|Chip
argument_list|(
literal|"before"
argument_list|,
name|sDateFormat
operator|.
name|format
argument_list|(
name|date
argument_list|)
argument_list|)
argument_list|,
name|chFrom
argument_list|)
argument_list|)
expr_stmt|;
name|suggestions
operator|.
name|add
argument_list|(
operator|new
name|FilterBox
operator|.
name|Suggestion
argument_list|(
operator|new
name|Chip
argument_list|(
literal|"after"
argument_list|,
name|sDateFormat
operator|.
name|format
argument_list|(
name|date
argument_list|)
argument_list|)
argument_list|,
name|chTo
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
block|}
try|try
block|{
name|Date
name|date
init|=
name|DateTimeFormat
operator|.
name|getFormat
argument_list|(
literal|"MMM dd yy"
argument_list|)
operator|.
name|parse
argument_list|(
name|text
argument_list|)
decl_stmt|;
name|suggestions
operator|.
name|add
argument_list|(
operator|new
name|FilterBox
operator|.
name|Suggestion
argument_list|(
operator|new
name|Chip
argument_list|(
literal|"before"
argument_list|,
name|sDateFormat
operator|.
name|format
argument_list|(
name|date
argument_list|)
argument_list|)
argument_list|,
name|chFrom
argument_list|)
argument_list|)
expr_stmt|;
name|suggestions
operator|.
name|add
argument_list|(
operator|new
name|FilterBox
operator|.
name|Suggestion
argument_list|(
operator|new
name|Chip
argument_list|(
literal|"after"
argument_list|,
name|sDateFormat
operator|.
name|format
argument_list|(
name|date
argument_list|)
argument_list|)
argument_list|,
name|chTo
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
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
name|addFilter
argument_list|(
operator|new
name|FilterBox
operator|.
name|StaticSimpleFilter
argument_list|(
literal|"before"
argument_list|)
argument_list|)
expr_stmt|;
name|addFilter
argument_list|(
operator|new
name|FilterBox
operator|.
name|StaticSimpleFilter
argument_list|(
literal|"after"
argument_list|)
argument_list|)
expr_stmt|;
name|m1
operator|.
name|addValueChangeHandler
argument_list|(
operator|new
name|ValueChangeHandler
argument_list|<
name|Date
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
name|Date
argument_list|>
name|event
parameter_list|)
block|{
name|Chip
name|ch
init|=
name|getChip
argument_list|(
literal|"before"
argument_list|)
decl_stmt|;
if|if
condition|(
name|event
operator|.
name|getValue
argument_list|()
operator|==
literal|null
condition|)
block|{
if|if
condition|(
name|ch
operator|!=
literal|null
condition|)
name|removeChip
argument_list|(
name|ch
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|String
name|text
init|=
name|m1
operator|.
name|toString
argument_list|()
decl_stmt|;
if|if
condition|(
name|ch
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|ch
operator|.
name|getCommand
argument_list|()
operator|.
name|equals
argument_list|(
name|text
argument_list|)
condition|)
return|return;
name|removeChip
argument_list|(
name|ch
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
name|addChip
argument_list|(
operator|new
name|Chip
argument_list|(
literal|"before"
argument_list|,
name|text
argument_list|)
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
block|}
argument_list|)
expr_stmt|;
name|m2
operator|.
name|addValueChangeHandler
argument_list|(
operator|new
name|ValueChangeHandler
argument_list|<
name|Date
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
name|Date
argument_list|>
name|event
parameter_list|)
block|{
name|Chip
name|ch
init|=
name|getChip
argument_list|(
literal|"after"
argument_list|)
decl_stmt|;
if|if
condition|(
name|event
operator|.
name|getValue
argument_list|()
operator|==
literal|null
condition|)
block|{
if|if
condition|(
name|ch
operator|!=
literal|null
condition|)
name|removeChip
argument_list|(
name|ch
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|String
name|text
init|=
name|m2
operator|.
name|toString
argument_list|()
decl_stmt|;
if|if
condition|(
name|ch
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|ch
operator|.
name|getCommand
argument_list|()
operator|.
name|equals
argument_list|(
name|text
argument_list|)
condition|)
return|return;
name|removeChip
argument_list|(
name|ch
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
name|addChip
argument_list|(
operator|new
name|Chip
argument_list|(
literal|"after"
argument_list|,
name|text
argument_list|)
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
block|}
argument_list|)
expr_stmt|;
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
operator|!
name|isFilterPopupShowing
argument_list|()
condition|)
block|{
name|iDepartments
operator|.
name|setSelectedIndex
argument_list|(
literal|0
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|1
init|;
name|i
operator|<
name|iDepartments
operator|.
name|getItemCount
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|String
name|value
init|=
name|iDepartments
operator|.
name|getValue
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
name|hasChip
argument_list|(
operator|new
name|Chip
argument_list|(
literal|"department"
argument_list|,
name|value
argument_list|)
argument_list|)
condition|)
block|{
name|iDepartments
operator|.
name|setSelectedIndex
argument_list|(
name|i
argument_list|)
expr_stmt|;
break|break;
block|}
block|}
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|iSubjects
operator|.
name|getItemCount
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|String
name|value
init|=
name|iSubjects
operator|.
name|getValue
argument_list|(
name|i
argument_list|)
decl_stmt|;
name|iSubjects
operator|.
name|setItemSelected
argument_list|(
name|i
argument_list|,
name|hasChip
argument_list|(
operator|new
name|Chip
argument_list|(
literal|"subject"
argument_list|,
name|value
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|Chip
name|chFrom
init|=
name|getChip
argument_list|(
literal|"before"
argument_list|)
decl_stmt|;
if|if
condition|(
name|chFrom
operator|!=
literal|null
condition|)
name|m1
operator|.
name|setDate
argument_list|(
name|sDateFormat
operator|.
name|parse
argument_list|(
name|chFrom
operator|.
name|getValue
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
else|else
name|m1
operator|.
name|clearSelection
argument_list|()
expr_stmt|;
name|Chip
name|chTo
init|=
name|getChip
argument_list|(
literal|"after"
argument_list|)
decl_stmt|;
if|if
condition|(
name|chTo
operator|!=
literal|null
condition|)
name|m2
operator|.
name|setDate
argument_list|(
name|sDateFormat
operator|.
name|parse
argument_list|(
name|chTo
operator|.
name|getValue
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
else|else
name|m2
operator|.
name|clearSelection
argument_list|()
expr_stmt|;
block|}
name|init
argument_list|(
literal|false
argument_list|,
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
name|isFilterPopupShowing
argument_list|()
condition|)
name|showFilterPopup
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|addFilter
argument_list|(
operator|new
name|FilterBox
operator|.
name|StaticSimpleFilter
argument_list|(
literal|"student"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
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
literal|"department"
operator|.
name|equals
argument_list|(
name|filter
operator|.
name|getCommand
argument_list|()
argument_list|)
condition|)
block|{
name|iDepartments
operator|.
name|clear
argument_list|()
expr_stmt|;
name|iDepartments
operator|.
name|addItem
argument_list|(
name|MESSAGES
operator|.
name|itemAllDepartments
argument_list|()
argument_list|,
literal|""
argument_list|)
expr_stmt|;
if|if
condition|(
name|entities
operator|!=
literal|null
condition|)
for|for
control|(
name|FilterRpcResponse
operator|.
name|Entity
name|entity
range|:
name|entities
control|)
name|iDepartments
operator|.
name|addItem
argument_list|(
name|entity
operator|.
name|getName
argument_list|()
operator|+
literal|" ("
operator|+
name|entity
operator|.
name|getCount
argument_list|()
operator|+
literal|")"
argument_list|,
name|entity
operator|.
name|getAbbreviation
argument_list|()
argument_list|)
expr_stmt|;
name|iDepartments
operator|.
name|setSelectedIndex
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|Chip
name|dept
init|=
name|getChip
argument_list|(
literal|"department"
argument_list|)
decl_stmt|;
if|if
condition|(
name|dept
operator|!=
literal|null
condition|)
for|for
control|(
name|int
name|i
init|=
literal|1
init|;
name|i
operator|<
name|iDepartments
operator|.
name|getItemCount
argument_list|()
condition|;
name|i
operator|++
control|)
if|if
condition|(
name|dept
operator|.
name|getValue
argument_list|()
operator|.
name|equals
argument_list|(
name|iDepartments
operator|.
name|getValue
argument_list|(
name|i
argument_list|)
argument_list|)
condition|)
block|{
name|iDepartments
operator|.
name|setSelectedIndex
argument_list|(
name|i
argument_list|)
expr_stmt|;
break|break;
block|}
return|return
literal|true
return|;
block|}
if|else if
condition|(
literal|"subject"
operator|.
name|equals
argument_list|(
name|filter
operator|.
name|getCommand
argument_list|()
argument_list|)
condition|)
block|{
name|iSubjects
operator|.
name|clear
argument_list|()
expr_stmt|;
if|if
condition|(
name|entities
operator|!=
literal|null
condition|)
for|for
control|(
name|FilterRpcResponse
operator|.
name|Entity
name|entity
range|:
name|entities
control|)
name|iSubjects
operator|.
name|addItem
argument_list|(
name|entity
operator|.
name|getName
argument_list|()
operator|+
literal|" ("
operator|+
name|entity
operator|.
name|getCount
argument_list|()
operator|+
literal|")"
argument_list|,
name|entity
operator|.
name|getAbbreviation
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|iSubjects
operator|.
name|getItemCount
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|String
name|value
init|=
name|iSubjects
operator|.
name|getValue
argument_list|(
name|i
argument_list|)
decl_stmt|;
name|iSubjects
operator|.
name|setItemSelected
argument_list|(
name|i
argument_list|,
name|hasChip
argument_list|(
operator|new
name|Chip
argument_list|(
literal|"subject"
argument_list|,
name|value
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
literal|true
return|;
block|}
if|else if
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
name|getAbbreviation
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
name|FilterRpcRequest
name|createRpcRequest
parameter_list|()
block|{
return|return
operator|new
name|ReservationInterface
operator|.
name|ReservationFilterRpcRequest
argument_list|()
return|;
block|}
block|}
end_class

end_unit

