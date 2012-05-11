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
name|RoomFilterRpcRequest
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
name|MouseDownEvent
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
name|MouseDownHandler
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
name|CheckBox
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
name|ListBox
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

begin_class
specifier|public
class|class
name|RoomFilterBox
extends|extends
name|UniTimeFilterBox
block|{
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
name|ListBox
name|iBuildings
decl_stmt|,
name|iDepartments
decl_stmt|;
specifier|public
name|RoomFilterBox
parameter_list|(
name|AcademicSessionProvider
name|session
parameter_list|)
block|{
name|super
argument_list|(
name|session
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
name|addFilter
argument_list|(
operator|new
name|FilterBox
operator|.
name|StaticSimpleFilter
argument_list|(
literal|"type"
argument_list|,
literal|true
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
literal|"feature"
argument_list|,
literal|true
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
argument_list|,
literal|true
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
literal|"size"
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
literal|"flag"
argument_list|)
argument_list|)
expr_stmt|;
name|iBuildings
operator|=
operator|new
name|ListBox
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|iBuildings
operator|.
name|setWidth
argument_list|(
literal|"100%"
argument_list|)
expr_stmt|;
name|iBuildings
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
literal|"building"
argument_list|,
name|iBuildings
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
name|iBuildings
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
literal|"building"
argument_list|,
name|iBuildings
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
name|iBuildings
operator|.
name|getItemText
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
name|iBuildings
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
annotation|@
name|Override
specifier|public
name|boolean
name|isVisible
parameter_list|()
block|{
return|return
name|iBuildings
operator|.
name|getItemCount
argument_list|()
operator|>
literal|0
return|;
block|}
block|}
argument_list|)
expr_stmt|;
name|iBuildings
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
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|iBuildings
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
literal|"building"
argument_list|,
name|iBuildings
operator|.
name|getValue
argument_list|(
name|i
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|iBuildings
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
name|Label
name|l1
init|=
operator|new
name|Label
argument_list|(
name|MESSAGES
operator|.
name|propMin
argument_list|()
argument_list|)
decl_stmt|;
specifier|final
name|TextBox
name|min
init|=
operator|new
name|TextBox
argument_list|()
decl_stmt|;
name|min
operator|.
name|setStyleName
argument_list|(
literal|"gwt-SuggestBox"
argument_list|)
expr_stmt|;
name|min
operator|.
name|setMaxLength
argument_list|(
literal|10
argument_list|)
expr_stmt|;
name|min
operator|.
name|getElement
argument_list|()
operator|.
name|getStyle
argument_list|()
operator|.
name|setWidth
argument_list|(
literal|50
argument_list|,
name|Unit
operator|.
name|PX
argument_list|)
expr_stmt|;
name|Label
name|l2
init|=
operator|new
name|Label
argument_list|(
name|MESSAGES
operator|.
name|propMax
argument_list|()
argument_list|)
decl_stmt|;
name|l2
operator|.
name|getElement
argument_list|()
operator|.
name|getStyle
argument_list|()
operator|.
name|setMarginLeft
argument_list|(
literal|10
argument_list|,
name|Unit
operator|.
name|PX
argument_list|)
expr_stmt|;
specifier|final
name|TextBox
name|max
init|=
operator|new
name|TextBox
argument_list|()
decl_stmt|;
name|max
operator|.
name|setMaxLength
argument_list|(
literal|10
argument_list|)
expr_stmt|;
name|max
operator|.
name|getElement
argument_list|()
operator|.
name|getStyle
argument_list|()
operator|.
name|setWidth
argument_list|(
literal|50
argument_list|,
name|Unit
operator|.
name|PX
argument_list|)
expr_stmt|;
name|max
operator|.
name|setStyleName
argument_list|(
literal|"gwt-SuggestBox"
argument_list|)
expr_stmt|;
specifier|final
name|CheckBox
name|nearby
init|=
operator|new
name|CheckBox
argument_list|(
name|MESSAGES
operator|.
name|checkIncludeNearby
argument_list|()
argument_list|)
decl_stmt|;
name|nearby
operator|.
name|getElement
argument_list|()
operator|.
name|getStyle
argument_list|()
operator|.
name|setMarginLeft
argument_list|(
literal|10
argument_list|,
name|Unit
operator|.
name|PX
argument_list|)
expr_stmt|;
name|addFilter
argument_list|(
operator|new
name|FilterBox
operator|.
name|CustomFilter
argument_list|(
literal|"other"
argument_list|,
name|l1
argument_list|,
name|min
argument_list|,
name|l2
argument_list|,
name|max
argument_list|,
name|nearby
argument_list|)
block|{
annotation|@
name|Override
specifier|public
name|void
name|getSuggestions
parameter_list|(
specifier|final
name|List
argument_list|<
name|Chip
argument_list|>
name|chips
parameter_list|,
specifier|final
name|String
name|text
parameter_list|,
name|AsyncCallback
argument_list|<
name|Collection
argument_list|<
name|FilterBox
operator|.
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
literal|"nearby"
operator|.
name|startsWith
argument_list|(
name|text
operator|.
name|toLowerCase
argument_list|()
argument_list|)
operator|||
name|MESSAGES
operator|.
name|checkIncludeNearby
argument_list|()
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
name|MESSAGES
operator|.
name|checkIncludeNearby
argument_list|()
argument_list|,
operator|new
name|Chip
argument_list|(
literal|"flag"
argument_list|,
literal|"nearby"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|Chip
name|old
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
literal|"size"
argument_list|)
condition|)
block|{
name|old
operator|=
name|c
expr_stmt|;
break|break;
block|}
block|}
try|try
block|{
name|String
name|number
init|=
name|text
decl_stmt|;
name|String
name|prefix
init|=
literal|""
decl_stmt|;
if|if
condition|(
name|text
operator|.
name|startsWith
argument_list|(
literal|"<="
argument_list|)
operator|||
name|text
operator|.
name|startsWith
argument_list|(
literal|">="
argument_list|)
condition|)
block|{
name|number
operator|=
name|number
operator|.
name|substring
argument_list|(
literal|2
argument_list|)
expr_stmt|;
name|prefix
operator|=
name|text
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|2
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|text
operator|.
name|startsWith
argument_list|(
literal|"<"
argument_list|)
operator|||
name|text
operator|.
name|startsWith
argument_list|(
literal|">"
argument_list|)
condition|)
block|{
name|number
operator|=
name|number
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|prefix
operator|=
name|text
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|1
argument_list|)
expr_stmt|;
block|}
name|Integer
operator|.
name|parseInt
argument_list|(
name|number
argument_list|)
expr_stmt|;
name|suggestions
operator|.
name|add
argument_list|(
operator|new
name|Suggestion
argument_list|(
operator|new
name|Chip
argument_list|(
literal|"size"
argument_list|,
name|text
argument_list|)
argument_list|,
name|old
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|prefix
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|suggestions
operator|.
name|add
argument_list|(
operator|new
name|Suggestion
argument_list|(
operator|new
name|Chip
argument_list|(
literal|"size"
argument_list|,
literal|"<="
operator|+
name|text
argument_list|)
argument_list|,
name|old
argument_list|)
argument_list|)
expr_stmt|;
name|suggestions
operator|.
name|add
argument_list|(
operator|new
name|Suggestion
argument_list|(
operator|new
name|Chip
argument_list|(
literal|"size"
argument_list|,
literal|">="
operator|+
name|text
argument_list|)
argument_list|,
name|old
argument_list|)
argument_list|)
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
if|if
condition|(
name|text
operator|.
name|contains
argument_list|(
literal|".."
argument_list|)
condition|)
block|{
try|try
block|{
name|String
name|first
init|=
name|text
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|text
operator|.
name|indexOf
argument_list|(
literal|'.'
argument_list|)
argument_list|)
decl_stmt|;
name|String
name|second
init|=
name|text
operator|.
name|substring
argument_list|(
name|text
operator|.
name|indexOf
argument_list|(
literal|".."
argument_list|)
operator|+
literal|2
argument_list|)
decl_stmt|;
name|Integer
operator|.
name|parseInt
argument_list|(
name|first
argument_list|)
expr_stmt|;
name|Integer
operator|.
name|parseInt
argument_list|(
name|second
argument_list|)
expr_stmt|;
name|suggestions
operator|.
name|add
argument_list|(
operator|new
name|Suggestion
argument_list|(
operator|new
name|Chip
argument_list|(
literal|"size"
argument_list|,
name|text
argument_list|)
argument_list|,
name|old
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
name|ChangeHandler
name|ch
init|=
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
name|removed
init|=
name|removeChip
argument_list|(
operator|new
name|Chip
argument_list|(
literal|"size"
argument_list|,
literal|null
argument_list|)
argument_list|,
literal|false
argument_list|)
decl_stmt|;
if|if
condition|(
name|min
operator|.
name|getText
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
if|if
condition|(
name|max
operator|.
name|getText
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
if|if
condition|(
name|removed
condition|)
name|fireValueChangeEvent
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|addChip
argument_list|(
operator|new
name|Chip
argument_list|(
literal|"size"
argument_list|,
literal|"<="
operator|+
name|max
operator|.
name|getText
argument_list|()
argument_list|)
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
if|if
condition|(
name|max
operator|.
name|getText
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|addChip
argument_list|(
operator|new
name|Chip
argument_list|(
literal|"size"
argument_list|,
literal|">="
operator|+
name|min
operator|.
name|getText
argument_list|()
argument_list|)
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|max
operator|.
name|getText
argument_list|()
operator|.
name|equals
argument_list|(
name|min
operator|.
name|getText
argument_list|()
argument_list|)
condition|)
block|{
name|addChip
argument_list|(
operator|new
name|Chip
argument_list|(
literal|"size"
argument_list|,
name|max
operator|.
name|getText
argument_list|()
argument_list|)
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|addChip
argument_list|(
operator|new
name|Chip
argument_list|(
literal|"size"
argument_list|,
name|min
operator|.
name|getText
argument_list|()
operator|+
literal|".."
operator|+
name|max
operator|.
name|getText
argument_list|()
argument_list|)
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
decl_stmt|;
name|min
operator|.
name|addChangeHandler
argument_list|(
name|ch
argument_list|)
expr_stmt|;
name|max
operator|.
name|addChangeHandler
argument_list|(
name|ch
argument_list|)
expr_stmt|;
name|nearby
operator|.
name|addValueChangeHandler
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
name|Chip
name|chip
init|=
operator|new
name|Chip
argument_list|(
literal|"flag"
argument_list|,
literal|"nearby"
argument_list|)
decl_stmt|;
if|if
condition|(
name|event
operator|.
name|getValue
argument_list|()
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
name|addChip
argument_list|(
name|chip
argument_list|,
literal|true
argument_list|)
expr_stmt|;
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
name|removeChip
argument_list|(
name|chip
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
block|}
argument_list|)
expr_stmt|;
name|nearby
operator|.
name|addMouseDownHandler
argument_list|(
operator|new
name|MouseDownHandler
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onMouseDown
parameter_list|(
name|MouseDownEvent
name|event
parameter_list|)
block|{
name|event
operator|.
name|getNativeEvent
argument_list|()
operator|.
name|stopPropagation
argument_list|()
expr_stmt|;
name|event
operator|.
name|getNativeEvent
argument_list|()
operator|.
name|preventDefault
argument_list|()
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
name|nearby
operator|.
name|setValue
argument_list|(
name|hasChip
argument_list|(
operator|new
name|Chip
argument_list|(
literal|"flag"
argument_list|,
literal|"nearby"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|Chip
name|size
init|=
name|getChip
argument_list|(
literal|"size"
argument_list|)
decl_stmt|;
if|if
condition|(
name|size
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|size
operator|.
name|getValue
argument_list|()
operator|.
name|startsWith
argument_list|(
literal|"<="
argument_list|)
condition|)
block|{
name|min
operator|.
name|setText
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|max
operator|.
name|setText
argument_list|(
name|size
operator|.
name|getValue
argument_list|()
operator|.
name|substring
argument_list|(
literal|2
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|size
operator|.
name|getValue
argument_list|()
operator|.
name|startsWith
argument_list|(
literal|"<"
argument_list|)
condition|)
block|{
try|try
block|{
name|max
operator|.
name|setText
argument_list|(
name|String
operator|.
name|valueOf
argument_list|(
name|Integer
operator|.
name|parseInt
argument_list|(
name|size
operator|.
name|getValue
argument_list|()
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
argument_list|)
operator|-
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|min
operator|.
name|setText
argument_list|(
literal|""
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
block|}
if|else if
condition|(
name|size
operator|.
name|getValue
argument_list|()
operator|.
name|startsWith
argument_list|(
literal|">="
argument_list|)
condition|)
block|{
name|min
operator|.
name|setText
argument_list|(
name|size
operator|.
name|getValue
argument_list|()
operator|.
name|substring
argument_list|(
literal|2
argument_list|)
argument_list|)
expr_stmt|;
name|max
operator|.
name|setText
argument_list|(
literal|""
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|size
operator|.
name|getValue
argument_list|()
operator|.
name|startsWith
argument_list|(
literal|">"
argument_list|)
condition|)
block|{
try|try
block|{
name|min
operator|.
name|setText
argument_list|(
name|String
operator|.
name|valueOf
argument_list|(
name|Integer
operator|.
name|parseInt
argument_list|(
name|size
operator|.
name|getValue
argument_list|()
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
argument_list|)
operator|+
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|max
operator|.
name|setText
argument_list|(
literal|""
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
block|}
if|else if
condition|(
name|size
operator|.
name|getValue
argument_list|()
operator|.
name|contains
argument_list|(
literal|".."
argument_list|)
condition|)
block|{
name|min
operator|.
name|setText
argument_list|(
name|size
operator|.
name|getValue
argument_list|()
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|size
operator|.
name|getValue
argument_list|()
operator|.
name|indexOf
argument_list|(
literal|".."
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|max
operator|.
name|setText
argument_list|(
name|size
operator|.
name|getValue
argument_list|()
operator|.
name|substring
argument_list|(
name|size
operator|.
name|getValue
argument_list|()
operator|.
name|indexOf
argument_list|(
literal|".."
argument_list|)
operator|+
literal|2
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|min
operator|.
name|setText
argument_list|(
name|size
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
name|max
operator|.
name|setText
argument_list|(
name|size
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
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
name|iBuildings
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
name|iBuildings
operator|.
name|getValue
argument_list|(
name|i
argument_list|)
decl_stmt|;
name|iBuildings
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
literal|"building"
argument_list|,
name|value
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
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
block|}
if|if
condition|(
name|getAcademicSessionId
argument_list|()
operator|!=
literal|null
condition|)
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
literal|"building"
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
name|iBuildings
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
name|iBuildings
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
name|iBuildings
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
name|iBuildings
operator|.
name|getValue
argument_list|(
name|i
argument_list|)
decl_stmt|;
name|iBuildings
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
literal|"building"
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
else|else
return|return
name|super
operator|.
name|populateFilter
argument_list|(
name|filter
argument_list|,
name|entities
argument_list|)
return|;
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
name|RoomFilterRpcRequest
argument_list|()
return|;
block|}
block|}
end_class

end_unit

