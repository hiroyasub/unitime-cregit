begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.3 (University Timetabling Application)  * Copyright (C) 2011, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|widgets
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
name|shared
operator|.
name|EventInterface
operator|.
name|WeekInterface
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
name|SelectionEvent
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
name|SelectionHandler
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
name|regexp
operator|.
name|shared
operator|.
name|MatchResult
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
name|regexp
operator|.
name|shared
operator|.
name|RegExp
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
name|SuggestBox
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
name|SuggestOracle
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
name|SuggestOracle
operator|.
name|Suggestion
import|;
end_import

begin_class
specifier|public
class|class
name|WeekSelector
extends|extends
name|Composite
block|{
name|HorizontalPanel
name|iWeekPanel
decl_stmt|;
specifier|private
name|SuggestBox
name|iWeek
decl_stmt|;
specifier|private
name|Button
name|iPrevious
decl_stmt|,
name|iNext
decl_stmt|;
specifier|private
name|List
argument_list|<
name|WeekChangedHandler
argument_list|>
name|iWeekChangedHandlers
init|=
operator|new
name|ArrayList
argument_list|<
name|WeekSelector
operator|.
name|WeekChangedHandler
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|List
argument_list|<
name|WeekInterface
argument_list|>
name|iWeeks
init|=
operator|new
name|ArrayList
argument_list|<
name|WeekInterface
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|WeekSelection
name|iWeekSelection
init|=
operator|new
name|WeekSelection
argument_list|()
decl_stmt|;
specifier|private
name|RegExp
name|iRegExp
init|=
name|RegExp
operator|.
name|compile
argument_list|(
literal|"[^0-9]*([0-9]+)[/ ]*([0-9]*)[ -]*([0-9]*)[/ ]*([0-9]*)"
argument_list|)
decl_stmt|;
specifier|public
name|WeekSelector
parameter_list|()
block|{
name|iWeekPanel
operator|=
operator|new
name|HorizontalPanel
argument_list|()
expr_stmt|;
name|iWeekPanel
operator|.
name|setSpacing
argument_list|(
literal|2
argument_list|)
expr_stmt|;
name|iPrevious
operator|=
operator|new
name|Button
argument_list|(
literal|"&larr;"
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
name|WeekSelection
name|prev
init|=
name|iWeekSelection
operator|.
name|previous
argument_list|()
decl_stmt|;
if|if
condition|(
name|prev
operator|!=
literal|null
condition|)
block|{
name|iWeekSelection
operator|=
name|prev
expr_stmt|;
name|fireWeekChanged
argument_list|()
expr_stmt|;
block|}
block|}
block|}
argument_list|)
expr_stmt|;
name|iPrevious
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|iPrevious
operator|.
name|setTitle
argument_list|(
literal|"Previous week (Alt+p)"
argument_list|)
expr_stmt|;
name|iPrevious
operator|.
name|setAccessKey
argument_list|(
literal|'p'
argument_list|)
expr_stmt|;
name|iWeekPanel
operator|.
name|add
argument_list|(
name|iPrevious
argument_list|)
expr_stmt|;
name|iWeekPanel
operator|.
name|setCellVerticalAlignment
argument_list|(
name|iPrevious
argument_list|,
name|HasVerticalAlignment
operator|.
name|ALIGN_MIDDLE
argument_list|)
expr_stmt|;
name|iWeek
operator|=
operator|new
name|SuggestBox
argument_list|(
operator|new
name|SuggestOracle
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|requestDefaultSuggestions
parameter_list|(
name|Request
name|request
parameter_list|,
name|Callback
name|callback
parameter_list|)
block|{
name|requestSuggestions
argument_list|(
name|request
argument_list|,
name|callback
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|requestSuggestions
parameter_list|(
specifier|final
name|Request
name|request
parameter_list|,
specifier|final
name|Callback
name|callback
parameter_list|)
block|{
name|String
name|query
init|=
name|request
operator|.
name|getQuery
argument_list|()
decl_stmt|;
name|ArrayList
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
name|WeekSelection
name|selection
init|=
name|parse
argument_list|(
name|query
argument_list|)
decl_stmt|;
name|iWeek
operator|.
name|setAutoSelectEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|selection
operator|.
name|isAllWeeks
argument_list|()
condition|)
block|{
name|ArrayList
argument_list|<
name|Suggestion
argument_list|>
name|extra
init|=
operator|new
name|ArrayList
argument_list|<
name|Suggestion
argument_list|>
argument_list|()
decl_stmt|;
if|if
condition|(
name|iWeek
operator|.
name|getText
argument_list|()
operator|.
name|equals
argument_list|(
name|selection
operator|.
name|getReplacementString
argument_list|()
argument_list|)
condition|)
block|{
name|suggestions
operator|.
name|add
argument_list|(
operator|new
name|WeekSelection
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|index
init|=
literal|0
init|;
name|index
operator|<
name|iWeeks
operator|.
name|size
argument_list|()
condition|;
name|index
operator|++
control|)
block|{
if|if
condition|(
name|iWeeks
operator|.
name|get
argument_list|(
name|index
argument_list|)
operator|.
name|getDayOfYear
argument_list|()
operator|==
name|selection
operator|.
name|getFirstDayOfYear
argument_list|()
condition|)
block|{
for|for
control|(
name|int
name|i
init|=
literal|3
init|;
name|i
operator|>
literal|0
condition|;
name|i
operator|--
control|)
if|if
condition|(
name|index
operator|-
name|i
operator|>=
literal|0
condition|)
name|suggestions
operator|.
name|add
argument_list|(
operator|new
name|WeekSelection
argument_list|(
name|iWeeks
operator|.
name|get
argument_list|(
name|index
operator|-
name|i
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|selection
operator|.
name|isOneWeek
argument_list|()
condition|)
name|suggestions
operator|.
name|add
argument_list|(
operator|new
name|WeekSelection
argument_list|(
name|iWeeks
operator|.
name|get
argument_list|(
name|index
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|iWeeks
operator|.
name|get
argument_list|(
name|index
argument_list|)
operator|.
name|getDayOfYear
argument_list|()
operator|==
operator|(
name|selection
operator|.
name|getLastWeek
argument_list|()
operator|==
literal|null
condition|?
name|selection
operator|.
name|getFirstWeek
argument_list|()
else|:
name|selection
operator|.
name|getLastWeek
argument_list|()
operator|)
operator|.
name|getDayOfYear
argument_list|()
condition|)
block|{
for|for
control|(
name|int
name|i
init|=
literal|1
init|;
name|i
operator|<=
literal|3
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|index
operator|+
name|i
operator|<
name|iWeeks
operator|.
name|size
argument_list|()
condition|)
name|extra
operator|.
name|add
argument_list|(
operator|new
name|WeekSelection
argument_list|(
name|iWeeks
operator|.
name|get
argument_list|(
name|index
operator|+
name|i
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
name|iWeek
operator|.
name|setAutoSelectEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|selection
operator|.
name|isOneWeek
argument_list|()
condition|)
block|{
name|suggestions
operator|.
name|add
argument_list|(
name|selection
argument_list|)
expr_stmt|;
for|for
control|(
name|WeekInterface
name|week
range|:
name|iWeeks
control|)
block|{
if|if
condition|(
name|week
operator|.
name|getDayOfYear
argument_list|()
operator|>
name|selection
operator|.
name|getFirstDayOfYear
argument_list|()
condition|)
block|{
if|if
condition|(
name|selection
operator|.
name|isPerfectMatch
argument_list|()
condition|)
name|suggestions
operator|.
name|add
argument_list|(
operator|new
name|WeekSelection
argument_list|(
name|selection
operator|.
name|getFirstWeek
argument_list|()
argument_list|,
name|week
argument_list|)
argument_list|)
expr_stmt|;
else|else
name|suggestions
operator|.
name|add
argument_list|(
operator|new
name|WeekSelection
argument_list|(
name|week
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|suggestions
operator|.
name|size
argument_list|()
operator|+
name|extra
operator|.
name|size
argument_list|()
operator|>=
name|request
operator|.
name|getLimit
argument_list|()
condition|)
break|break;
block|}
block|}
else|else
block|{
if|if
condition|(
name|selection
operator|.
name|isPerfectMatch
argument_list|()
condition|)
block|{
name|suggestions
operator|.
name|add
argument_list|(
name|selection
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|suggestions
operator|.
name|add
argument_list|(
operator|new
name|WeekSelection
argument_list|(
name|selection
operator|.
name|getFirstWeek
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|suggestions
operator|.
name|add
argument_list|(
name|selection
argument_list|)
expr_stmt|;
for|for
control|(
name|WeekInterface
name|week
range|:
name|iWeeks
control|)
block|{
if|if
condition|(
name|week
operator|.
name|getDayOfYear
argument_list|()
operator|>
name|selection
operator|.
name|getLastDayOfYear
argument_list|()
condition|)
name|suggestions
operator|.
name|add
argument_list|(
operator|new
name|WeekSelection
argument_list|(
name|selection
operator|.
name|getFirstWeek
argument_list|()
argument_list|,
name|week
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|suggestions
operator|.
name|size
argument_list|()
operator|+
name|extra
operator|.
name|size
argument_list|()
operator|>=
name|request
operator|.
name|getLimit
argument_list|()
condition|)
break|break;
block|}
name|iWeek
operator|.
name|setAutoSelectEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
block|}
name|suggestions
operator|.
name|addAll
argument_list|(
name|extra
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|suggestions
operator|.
name|add
argument_list|(
operator|new
name|WeekSelection
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|WeekInterface
name|week
range|:
name|iWeeks
control|)
block|{
name|suggestions
operator|.
name|add
argument_list|(
operator|new
name|WeekSelection
argument_list|(
name|week
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|suggestions
operator|.
name|size
argument_list|()
operator|>=
name|request
operator|.
name|getLimit
argument_list|()
condition|)
break|break;
block|}
block|}
name|callback
operator|.
name|onSuggestionsReady
argument_list|(
name|request
argument_list|,
operator|new
name|Response
argument_list|(
name|suggestions
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isDisplayStringHTML
parameter_list|()
block|{
return|return
literal|true
return|;
block|}
block|}
argument_list|)
expr_stmt|;
name|iWeek
operator|.
name|getTextBox
argument_list|()
operator|.
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
name|iWeek
operator|.
name|showSuggestionList
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|iWeekPanel
operator|.
name|add
argument_list|(
name|iWeek
argument_list|)
expr_stmt|;
name|iWeekPanel
operator|.
name|setCellVerticalAlignment
argument_list|(
name|iWeek
argument_list|,
name|HasVerticalAlignment
operator|.
name|ALIGN_MIDDLE
argument_list|)
expr_stmt|;
name|iNext
operator|=
operator|new
name|Button
argument_list|(
literal|"&rarr;"
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
name|WeekSelection
name|next
init|=
name|iWeekSelection
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|next
operator|!=
literal|null
condition|)
block|{
name|iWeekSelection
operator|=
name|next
expr_stmt|;
name|fireWeekChanged
argument_list|()
expr_stmt|;
block|}
block|}
block|}
argument_list|)
expr_stmt|;
name|iNext
operator|.
name|setTitle
argument_list|(
literal|"Next week (Alt+n)"
argument_list|)
expr_stmt|;
name|iNext
operator|.
name|setAccessKey
argument_list|(
literal|'n'
argument_list|)
expr_stmt|;
name|iWeekPanel
operator|.
name|add
argument_list|(
name|iNext
argument_list|)
expr_stmt|;
name|iWeekPanel
operator|.
name|setCellVerticalAlignment
argument_list|(
name|iNext
argument_list|,
name|HasVerticalAlignment
operator|.
name|ALIGN_MIDDLE
argument_list|)
expr_stmt|;
name|iWeek
operator|.
name|addSelectionHandler
argument_list|(
operator|new
name|SelectionHandler
argument_list|<
name|SuggestOracle
operator|.
name|Suggestion
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onSelection
parameter_list|(
name|SelectionEvent
argument_list|<
name|Suggestion
argument_list|>
name|event
parameter_list|)
block|{
name|iWeekSelection
operator|=
operator|(
name|WeekSelection
operator|)
name|event
operator|.
name|getSelectedItem
argument_list|()
expr_stmt|;
name|fireWeekChanged
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|initWidget
argument_list|(
name|iWeekPanel
argument_list|)
expr_stmt|;
block|}
specifier|public
name|WeekSelection
name|parse
parameter_list|(
name|String
name|query
parameter_list|)
block|{
if|if
condition|(
name|query
operator|==
literal|null
condition|)
return|return
operator|new
name|WeekSelection
argument_list|()
return|;
name|MatchResult
name|match
init|=
name|iRegExp
operator|.
name|exec
argument_list|(
name|query
argument_list|)
decl_stmt|;
if|if
condition|(
name|match
operator|!=
literal|null
condition|)
block|{
name|int
name|m1
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|match
operator|.
name|getGroup
argument_list|(
literal|1
argument_list|)
argument_list|)
decl_stmt|;
name|int
name|d1
init|=
operator|(
name|match
operator|.
name|getGroup
argument_list|(
literal|2
argument_list|)
operator|.
name|isEmpty
argument_list|()
condition|?
literal|1
else|:
name|Integer
operator|.
name|parseInt
argument_list|(
name|match
operator|.
name|getGroup
argument_list|(
literal|2
argument_list|)
argument_list|)
operator|)
decl_stmt|;
name|WeekInterface
name|first
init|=
name|find
argument_list|(
name|m1
argument_list|,
name|d1
argument_list|,
literal|null
argument_list|)
decl_stmt|;
name|int
name|m2
init|=
operator|(
name|match
operator|.
name|getGroup
argument_list|(
literal|3
argument_list|)
operator|.
name|isEmpty
argument_list|()
condition|?
operator|-
literal|1
else|:
name|Integer
operator|.
name|parseInt
argument_list|(
name|match
operator|.
name|getGroup
argument_list|(
literal|3
argument_list|)
argument_list|)
operator|)
decl_stmt|;
name|int
name|d2
init|=
operator|(
name|match
operator|.
name|getGroup
argument_list|(
literal|4
argument_list|)
operator|.
name|isEmpty
argument_list|()
condition|?
literal|1
else|:
name|Integer
operator|.
name|parseInt
argument_list|(
name|match
operator|.
name|getGroup
argument_list|(
literal|4
argument_list|)
argument_list|)
operator|)
decl_stmt|;
if|if
condition|(
name|m2
operator|==
name|m1
operator|&&
name|d2
operator|<
name|d1
condition|)
name|d2
operator|=
name|d1
expr_stmt|;
name|WeekInterface
name|last
init|=
operator|(
name|match
operator|.
name|getGroup
argument_list|(
literal|3
argument_list|)
operator|.
name|isEmpty
argument_list|()
condition|?
literal|null
else|:
name|find
argument_list|(
name|m2
argument_list|,
name|d2
argument_list|,
name|first
argument_list|)
operator|)
decl_stmt|;
return|return
operator|new
name|WeekSelection
argument_list|(
name|first
argument_list|,
name|last
argument_list|,
operator|(
name|match
operator|.
name|getGroup
argument_list|(
literal|3
argument_list|)
operator|.
name|isEmpty
argument_list|()
operator|&&
operator|!
name|match
operator|.
name|getGroup
argument_list|(
literal|2
argument_list|)
operator|.
name|isEmpty
argument_list|()
operator|)
operator|||
operator|(
operator|!
name|match
operator|.
name|getGroup
argument_list|(
literal|3
argument_list|)
operator|.
name|isEmpty
argument_list|()
operator|&&
operator|!
name|match
operator|.
name|getGroup
argument_list|(
literal|4
argument_list|)
operator|.
name|isEmpty
argument_list|()
operator|)
argument_list|)
return|;
block|}
return|return
operator|new
name|WeekSelection
argument_list|()
return|;
block|}
specifier|public
name|WeekInterface
name|find
parameter_list|(
name|int
name|month
parameter_list|,
name|int
name|day
parameter_list|,
name|WeekInterface
name|after
parameter_list|)
block|{
name|WeekInterface
name|first
init|=
literal|null
decl_stmt|;
for|for
control|(
name|WeekInterface
name|w
range|:
name|iWeeks
control|)
block|{
if|if
condition|(
name|after
operator|!=
literal|null
operator|&&
name|w
operator|.
name|getDayOfYear
argument_list|()
operator|<
name|after
operator|.
name|getDayOfYear
argument_list|()
condition|)
continue|continue;
if|if
condition|(
name|first
operator|==
literal|null
condition|)
name|first
operator|=
name|w
expr_stmt|;
for|for
control|(
name|String
name|dayName
range|:
name|w
operator|.
name|getDayNames
argument_list|()
control|)
block|{
name|int
name|m
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|dayName
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|dayName
operator|.
name|indexOf
argument_list|(
literal|'/'
argument_list|)
argument_list|)
argument_list|)
decl_stmt|;
name|int
name|d
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|dayName
operator|.
name|substring
argument_list|(
literal|1
operator|+
name|dayName
operator|.
name|indexOf
argument_list|(
literal|'/'
argument_list|)
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|m
operator|==
name|month
operator|&&
name|d
operator|==
name|day
condition|)
return|return
name|w
return|;
block|}
block|}
name|String
name|firstDay
init|=
name|iWeeks
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|getDayNames
argument_list|()
operator|.
name|get
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|int
name|m
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|firstDay
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|firstDay
operator|.
name|indexOf
argument_list|(
literal|'/'
argument_list|)
argument_list|)
argument_list|)
decl_stmt|;
name|int
name|d
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|firstDay
operator|.
name|substring
argument_list|(
literal|1
operator|+
name|firstDay
operator|.
name|indexOf
argument_list|(
literal|'/'
argument_list|)
argument_list|)
argument_list|)
decl_stmt|;
return|return
operator|(
name|month
operator|<
name|m
operator|||
operator|(
name|m
operator|==
name|month
operator|&&
name|day
operator|<
name|d
operator|)
condition|?
name|first
operator|==
literal|null
condition|?
name|iWeeks
operator|.
name|get
argument_list|(
literal|0
argument_list|)
else|:
name|first
else|:
name|iWeeks
operator|.
name|get
argument_list|(
name|iWeeks
operator|.
name|size
argument_list|()
operator|-
literal|1
argument_list|)
operator|)
return|;
block|}
specifier|public
class|class
name|WeekSelection
implements|implements
name|Suggestion
block|{
specifier|private
name|WeekInterface
name|iFirstWeek
decl_stmt|,
name|iLastWeek
decl_stmt|;
specifier|private
name|boolean
name|iPerfectMatch
decl_stmt|;
specifier|public
name|WeekSelection
parameter_list|()
block|{
name|this
argument_list|(
literal|null
argument_list|,
literal|null
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
specifier|public
name|WeekSelection
parameter_list|(
name|WeekInterface
name|week
parameter_list|)
block|{
name|this
argument_list|(
name|week
argument_list|,
literal|null
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
specifier|public
name|WeekSelection
parameter_list|(
name|WeekInterface
name|firstWeek
parameter_list|,
name|WeekInterface
name|lastWeek
parameter_list|)
block|{
name|this
argument_list|(
name|firstWeek
argument_list|,
name|lastWeek
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
specifier|private
name|WeekSelection
parameter_list|(
name|WeekInterface
name|firstWeek
parameter_list|,
name|WeekInterface
name|lastWeek
parameter_list|,
name|boolean
name|perfectMatch
parameter_list|)
block|{
name|iFirstWeek
operator|=
name|firstWeek
expr_stmt|;
name|iLastWeek
operator|=
name|lastWeek
expr_stmt|;
if|if
condition|(
name|iLastWeek
operator|!=
literal|null
operator|&&
name|iLastWeek
operator|.
name|getDayOfYear
argument_list|()
operator|<=
name|iFirstWeek
operator|.
name|getDayOfYear
argument_list|()
condition|)
name|iLastWeek
operator|=
literal|null
expr_stmt|;
name|iPerfectMatch
operator|=
name|perfectMatch
expr_stmt|;
block|}
specifier|public
name|boolean
name|isPerfectMatch
parameter_list|()
block|{
return|return
name|iPerfectMatch
return|;
block|}
specifier|public
name|WeekInterface
name|getFirstWeek
parameter_list|()
block|{
return|return
name|iFirstWeek
return|;
block|}
specifier|public
name|WeekInterface
name|getLastWeek
parameter_list|()
block|{
return|return
name|iLastWeek
return|;
block|}
specifier|public
name|String
name|getFirstDay
parameter_list|()
block|{
return|return
name|iFirstWeek
operator|==
literal|null
condition|?
literal|""
else|:
name|iFirstWeek
operator|.
name|getDayNames
argument_list|()
operator|.
name|get
argument_list|(
literal|0
argument_list|)
return|;
block|}
specifier|public
name|String
name|getLastDay
parameter_list|()
block|{
return|return
name|iLastWeek
operator|==
literal|null
condition|?
name|iFirstWeek
operator|==
literal|null
condition|?
literal|""
else|:
name|iFirstWeek
operator|.
name|getDayNames
argument_list|()
operator|.
name|get
argument_list|(
name|iFirstWeek
operator|.
name|getDayNames
argument_list|()
operator|.
name|size
argument_list|()
operator|-
literal|1
argument_list|)
else|:
name|iLastWeek
operator|.
name|getDayNames
argument_list|()
operator|.
name|get
argument_list|(
name|iLastWeek
operator|.
name|getDayNames
argument_list|()
operator|.
name|size
argument_list|()
operator|-
literal|1
argument_list|)
return|;
block|}
specifier|public
name|boolean
name|isAllWeeks
parameter_list|()
block|{
return|return
name|iFirstWeek
operator|==
literal|null
return|;
block|}
specifier|public
name|boolean
name|isOneWeek
parameter_list|()
block|{
return|return
name|iFirstWeek
operator|!=
literal|null
operator|&&
name|iLastWeek
operator|==
literal|null
return|;
block|}
specifier|public
name|int
name|getNrWeeks
parameter_list|()
block|{
return|return
operator|(
name|isAllWeeks
argument_list|()
condition|?
name|iWeeks
operator|.
name|size
argument_list|()
else|:
name|isOneWeek
argument_list|()
condition|?
literal|1
else|:
name|iWeeks
operator|.
name|indexOf
argument_list|(
name|iLastWeek
argument_list|)
operator|-
name|iWeeks
operator|.
name|indexOf
argument_list|(
name|iFirstWeek
argument_list|)
operator|+
literal|1
operator|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getDisplayString
parameter_list|()
block|{
if|if
condition|(
name|getFirstWeek
argument_list|()
operator|==
literal|null
condition|)
return|return
literal|"All Weeks"
return|;
if|if
condition|(
name|getLastWeek
argument_list|()
operator|==
literal|null
condition|)
return|return
literal|"Week "
operator|+
name|getFirstWeek
argument_list|()
operator|.
name|getDayNames
argument_list|()
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|+
literal|" - "
operator|+
name|getFirstWeek
argument_list|()
operator|.
name|getDayNames
argument_list|()
operator|.
name|get
argument_list|(
name|getFirstWeek
argument_list|()
operator|.
name|getDayNames
argument_list|()
operator|.
name|size
argument_list|()
operator|-
literal|1
argument_list|)
return|;
return|return
literal|"&nbsp;&nbsp;&nbsp;"
operator|+
name|getFirstWeek
argument_list|()
operator|.
name|getDayNames
argument_list|()
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|+
literal|" - "
operator|+
name|getLastWeek
argument_list|()
operator|.
name|getDayNames
argument_list|()
operator|.
name|get
argument_list|(
name|getLastWeek
argument_list|()
operator|.
name|getDayNames
argument_list|()
operator|.
name|size
argument_list|()
operator|-
literal|1
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getReplacementString
parameter_list|()
block|{
if|if
condition|(
name|getFirstWeek
argument_list|()
operator|==
literal|null
condition|)
return|return
literal|"All Weeks"
return|;
if|if
condition|(
name|getLastWeek
argument_list|()
operator|==
literal|null
condition|)
return|return
literal|"Week "
operator|+
name|getFirstWeek
argument_list|()
operator|.
name|getDayNames
argument_list|()
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|+
literal|" - "
operator|+
name|getFirstWeek
argument_list|()
operator|.
name|getDayNames
argument_list|()
operator|.
name|get
argument_list|(
name|getFirstWeek
argument_list|()
operator|.
name|getDayNames
argument_list|()
operator|.
name|size
argument_list|()
operator|-
literal|1
argument_list|)
return|;
return|return
literal|"Weeks "
operator|+
name|getFirstWeek
argument_list|()
operator|.
name|getDayNames
argument_list|()
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|+
literal|" - "
operator|+
name|getLastWeek
argument_list|()
operator|.
name|getDayNames
argument_list|()
operator|.
name|get
argument_list|(
name|getLastWeek
argument_list|()
operator|.
name|getDayNames
argument_list|()
operator|.
name|size
argument_list|()
operator|-
literal|1
argument_list|)
return|;
block|}
specifier|public
name|WeekSelection
name|previous
parameter_list|()
block|{
if|if
condition|(
operator|!
name|isOneWeek
argument_list|()
condition|)
return|return
literal|null
return|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|iWeeks
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
if|if
condition|(
name|iFirstWeek
operator|.
name|getDayOfYear
argument_list|()
operator|==
name|iWeeks
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|.
name|getDayOfYear
argument_list|()
condition|)
return|return
operator|new
name|WeekSelection
argument_list|(
name|i
operator|==
literal|0
condition|?
literal|null
else|:
name|iWeeks
operator|.
name|get
argument_list|(
name|i
operator|-
literal|1
argument_list|)
argument_list|)
return|;
return|return
literal|null
return|;
block|}
specifier|public
name|WeekSelection
name|next
parameter_list|()
block|{
if|if
condition|(
name|isAllWeeks
argument_list|()
condition|)
return|return
operator|new
name|WeekSelection
argument_list|(
name|iWeeks
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
return|;
if|if
condition|(
operator|!
name|isOneWeek
argument_list|()
condition|)
return|return
literal|null
return|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|iWeeks
operator|.
name|size
argument_list|()
operator|-
literal|1
condition|;
name|i
operator|++
control|)
if|if
condition|(
name|iFirstWeek
operator|.
name|getDayOfYear
argument_list|()
operator|==
name|iWeeks
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|.
name|getDayOfYear
argument_list|()
condition|)
return|return
operator|new
name|WeekSelection
argument_list|(
name|iWeeks
operator|.
name|get
argument_list|(
name|i
operator|+
literal|1
argument_list|)
argument_list|)
return|;
return|return
literal|null
return|;
block|}
specifier|public
name|String
name|getDayNames
parameter_list|(
name|int
name|dayOfWeek
parameter_list|)
block|{
if|if
condition|(
name|iFirstWeek
operator|==
literal|null
condition|)
return|return
literal|""
return|;
if|if
condition|(
name|iLastWeek
operator|==
literal|null
condition|)
return|return
name|iFirstWeek
operator|.
name|getDayNames
argument_list|()
operator|.
name|get
argument_list|(
name|dayOfWeek
argument_list|)
return|;
return|return
name|iFirstWeek
operator|.
name|getDayNames
argument_list|()
operator|.
name|get
argument_list|(
name|dayOfWeek
argument_list|)
operator|+
literal|" - "
operator|+
name|iLastWeek
operator|.
name|getDayNames
argument_list|()
operator|.
name|get
argument_list|(
name|dayOfWeek
argument_list|)
return|;
block|}
specifier|public
name|int
name|getFirstDayOfYear
parameter_list|()
block|{
return|return
operator|(
name|iFirstWeek
operator|==
literal|null
condition|?
literal|0
else|:
name|iFirstWeek
operator|.
name|getDayOfYear
argument_list|()
operator|)
return|;
block|}
specifier|public
name|int
name|getLastDayOfYear
parameter_list|()
block|{
name|WeekInterface
name|last
init|=
operator|(
name|iLastWeek
operator|==
literal|null
condition|?
name|iFirstWeek
else|:
name|iLastWeek
operator|)
decl_stmt|;
return|return
operator|(
name|last
operator|==
literal|null
condition|?
literal|null
else|:
name|last
operator|.
name|getDayOfYear
argument_list|()
operator|+
name|last
operator|.
name|getDayNames
argument_list|()
operator|.
name|size
argument_list|()
operator|-
literal|1
operator|)
return|;
block|}
block|}
specifier|public
name|void
name|addWeek
parameter_list|(
name|WeekInterface
name|week
parameter_list|)
block|{
name|iWeeks
operator|.
name|add
argument_list|(
name|week
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|clearWeeks
parameter_list|()
block|{
name|iWeeks
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
specifier|public
name|void
name|addWeek
parameter_list|(
name|int
name|dayOfYear
parameter_list|,
name|String
modifier|...
name|dayNames
parameter_list|)
block|{
name|WeekInterface
name|week
init|=
operator|new
name|WeekInterface
argument_list|()
decl_stmt|;
name|week
operator|.
name|setDayOfYear
argument_list|(
name|dayOfYear
argument_list|)
expr_stmt|;
for|for
control|(
name|String
name|dayName
range|:
name|dayNames
control|)
name|week
operator|.
name|addDayName
argument_list|(
name|dayName
argument_list|)
expr_stmt|;
name|addWeek
argument_list|(
name|week
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|select
parameter_list|(
name|String
name|query
parameter_list|)
block|{
name|iWeekSelection
operator|=
name|parse
argument_list|(
name|query
argument_list|)
expr_stmt|;
name|fireWeekChanged
argument_list|()
expr_stmt|;
block|}
specifier|public
name|WeekSelection
name|getSelection
parameter_list|()
block|{
return|return
name|iWeekSelection
return|;
block|}
specifier|private
name|void
name|fireWeekChanged
parameter_list|()
block|{
name|iWeek
operator|.
name|setText
argument_list|(
name|iWeekSelection
operator|.
name|getReplacementString
argument_list|()
argument_list|)
expr_stmt|;
name|iPrevious
operator|.
name|setEnabled
argument_list|(
name|iWeekSelection
operator|.
name|previous
argument_list|()
operator|!=
literal|null
argument_list|)
expr_stmt|;
name|iNext
operator|.
name|setEnabled
argument_list|(
name|iWeekSelection
operator|.
name|next
argument_list|()
operator|!=
literal|null
argument_list|)
expr_stmt|;
name|WeekChangedEvent
name|event
init|=
operator|new
name|WeekChangedEvent
argument_list|(
name|iWeekSelection
argument_list|)
decl_stmt|;
for|for
control|(
name|WeekChangedHandler
name|h
range|:
name|iWeekChangedHandlers
control|)
name|h
operator|.
name|onWeekChanged
argument_list|(
name|event
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|addWeekChangedHandler
parameter_list|(
name|WeekChangedHandler
name|h
parameter_list|)
block|{
name|iWeekChangedHandlers
operator|.
name|add
argument_list|(
name|h
argument_list|)
expr_stmt|;
block|}
specifier|public
interface|interface
name|WeekChangedHandler
block|{
specifier|public
name|void
name|onWeekChanged
parameter_list|(
name|WeekChangedEvent
name|event
parameter_list|)
function_decl|;
block|}
specifier|public
specifier|static
class|class
name|WeekChangedEvent
block|{
specifier|private
name|WeekSelection
name|iSelection
decl_stmt|;
specifier|public
name|WeekChangedEvent
parameter_list|(
name|WeekSelection
name|selection
parameter_list|)
block|{
name|iSelection
operator|=
name|selection
expr_stmt|;
block|}
specifier|public
name|WeekSelection
name|getSelection
parameter_list|()
block|{
return|return
name|iSelection
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|MySuggestionDisplay
extends|extends
name|SuggestBox
operator|.
name|DefaultSuggestionDisplay
block|{
specifier|public
name|MySuggestionDisplay
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

