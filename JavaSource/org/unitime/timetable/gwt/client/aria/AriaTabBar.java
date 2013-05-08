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
name|aria
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
name|resources
operator|.
name|GwtAriaMessages
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
name|aria
operator|.
name|client
operator|.
name|Id
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
name|aria
operator|.
name|client
operator|.
name|Roles
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
name|aria
operator|.
name|client
operator|.
name|SelectedValue
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
name|shared
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
name|Element
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
name|TabBar
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
name|Widget
import|;
end_import

begin_class
specifier|public
class|class
name|AriaTabBar
extends|extends
name|TabBar
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
name|RegExp
name|sStripAcessKeyRegExp
init|=
name|RegExp
operator|.
name|compile
argument_list|(
literal|"(.*)<u>(\\w)</u>(.*)"
argument_list|,
literal|"i"
argument_list|)
decl_stmt|;
specifier|public
name|AriaTabBar
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
name|getElement
argument_list|()
operator|.
name|setId
argument_list|(
name|DOM
operator|.
name|createUniqueId
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Element
name|getTabElement
parameter_list|(
name|int
name|index
parameter_list|)
block|{
return|return
operator|(
operator|(
name|Widget
operator|)
name|getTab
argument_list|(
name|index
argument_list|)
operator|)
operator|.
name|getElement
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|protected
name|void
name|insertTabWidget
parameter_list|(
name|Widget
name|widget
parameter_list|,
name|int
name|beforeIndex
parameter_list|)
block|{
name|super
operator|.
name|insertTabWidget
argument_list|(
name|widget
argument_list|,
name|beforeIndex
argument_list|)
expr_stmt|;
name|Roles
operator|.
name|getTabRole
argument_list|()
operator|.
name|setAriaSelectedState
argument_list|(
name|getTabElement
argument_list|(
name|beforeIndex
argument_list|)
argument_list|,
name|SelectedValue
operator|.
name|FALSE
argument_list|)
expr_stmt|;
name|getTabElement
argument_list|(
name|beforeIndex
argument_list|)
operator|.
name|setId
argument_list|(
name|DOM
operator|.
name|createUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|Id
name|ids
index|[]
init|=
operator|new
name|Id
index|[
name|getTabCount
argument_list|()
index|]
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
name|getTabCount
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|Roles
operator|.
name|getTabRole
argument_list|()
operator|.
name|setAriaLabelProperty
argument_list|(
name|getTabElement
argument_list|(
name|i
argument_list|)
argument_list|,
name|ARIA
operator|.
name|tabNotSelected
argument_list|(
literal|1
operator|+
name|i
argument_list|,
name|getTabCount
argument_list|()
argument_list|,
name|getTabLabel
argument_list|(
name|i
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|ids
index|[
name|i
index|]
operator|=
name|Id
operator|.
name|of
argument_list|(
name|getTabElement
argument_list|(
name|i
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|Roles
operator|.
name|getTablistRole
argument_list|()
operator|.
name|setAriaOwnsProperty
argument_list|(
name|getElement
argument_list|()
argument_list|,
name|ids
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|removeTab
parameter_list|(
name|int
name|index
parameter_list|)
block|{
name|super
operator|.
name|removeTab
argument_list|(
name|index
argument_list|)
expr_stmt|;
name|Id
name|ids
index|[]
init|=
operator|new
name|Id
index|[
name|getTabCount
argument_list|()
index|]
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
name|getTabCount
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|Roles
operator|.
name|getTabRole
argument_list|()
operator|.
name|setAriaLabelProperty
argument_list|(
name|getTabElement
argument_list|(
name|i
argument_list|)
argument_list|,
name|ARIA
operator|.
name|tabNotSelected
argument_list|(
literal|1
operator|+
name|i
argument_list|,
name|getTabCount
argument_list|()
argument_list|,
name|getTabLabel
argument_list|(
name|i
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|ids
index|[
name|i
index|]
operator|=
name|Id
operator|.
name|of
argument_list|(
name|getTabElement
argument_list|(
name|i
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|Roles
operator|.
name|getTablistRole
argument_list|()
operator|.
name|setAriaOwnsProperty
argument_list|(
name|getElement
argument_list|()
argument_list|,
name|ids
argument_list|)
expr_stmt|;
if|if
condition|(
name|getSelectedTab
argument_list|()
operator|>=
literal|0
condition|)
block|{
name|Roles
operator|.
name|getTabRole
argument_list|()
operator|.
name|setAriaSelectedState
argument_list|(
name|getTabElement
argument_list|(
name|getSelectedTab
argument_list|()
argument_list|)
argument_list|,
name|SelectedValue
operator|.
name|TRUE
argument_list|)
expr_stmt|;
name|Roles
operator|.
name|getTabRole
argument_list|()
operator|.
name|setAriaLabelProperty
argument_list|(
name|getTabElement
argument_list|(
name|getSelectedTab
argument_list|()
argument_list|)
argument_list|,
name|ARIA
operator|.
name|tabSelected
argument_list|(
literal|1
operator|+
name|getSelectedTab
argument_list|()
argument_list|,
name|getTabCount
argument_list|()
argument_list|,
name|getTabLabel
argument_list|(
name|getSelectedTab
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|selectTab
parameter_list|(
name|int
name|index
parameter_list|,
name|boolean
name|fireEvents
parameter_list|)
block|{
if|if
condition|(
name|getSelectedTab
argument_list|()
operator|>=
literal|0
condition|)
block|{
name|Roles
operator|.
name|getTabRole
argument_list|()
operator|.
name|setAriaSelectedState
argument_list|(
name|getTabElement
argument_list|(
name|getSelectedTab
argument_list|()
argument_list|)
argument_list|,
name|SelectedValue
operator|.
name|FALSE
argument_list|)
expr_stmt|;
name|Roles
operator|.
name|getTabRole
argument_list|()
operator|.
name|setAriaLabelProperty
argument_list|(
name|getTabElement
argument_list|(
name|getSelectedTab
argument_list|()
argument_list|)
argument_list|,
name|ARIA
operator|.
name|tabNotSelected
argument_list|(
literal|1
operator|+
name|getSelectedTab
argument_list|()
argument_list|,
name|getTabCount
argument_list|()
argument_list|,
name|getTabLabel
argument_list|(
name|getSelectedTab
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|boolean
name|ret
init|=
name|super
operator|.
name|selectTab
argument_list|(
name|index
argument_list|,
name|fireEvents
argument_list|)
decl_stmt|;
if|if
condition|(
name|getSelectedTab
argument_list|()
operator|>=
literal|0
condition|)
block|{
name|Roles
operator|.
name|getTabRole
argument_list|()
operator|.
name|setAriaSelectedState
argument_list|(
name|getTabElement
argument_list|(
name|getSelectedTab
argument_list|()
argument_list|)
argument_list|,
name|SelectedValue
operator|.
name|TRUE
argument_list|)
expr_stmt|;
name|Roles
operator|.
name|getTabRole
argument_list|()
operator|.
name|setAriaLabelProperty
argument_list|(
name|getTabElement
argument_list|(
name|getSelectedTab
argument_list|()
argument_list|)
argument_list|,
name|ARIA
operator|.
name|tabSelected
argument_list|(
literal|1
operator|+
name|getSelectedTab
argument_list|()
argument_list|,
name|getTabCount
argument_list|()
argument_list|,
name|getTabLabel
argument_list|(
name|getSelectedTab
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|fireEvents
condition|)
name|AriaStatus
operator|.
name|getInstance
argument_list|()
operator|.
name|setHTML
argument_list|(
name|ARIA
operator|.
name|onTabSelected
argument_list|(
name|getTabLabel
argument_list|(
name|getSelectedTab
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|ret
return|;
block|}
specifier|public
name|String
name|getTabLabel
parameter_list|(
name|int
name|index
parameter_list|)
block|{
name|String
name|html
init|=
name|getTabHTML
argument_list|(
name|index
argument_list|)
decl_stmt|;
if|if
condition|(
name|html
operator|==
literal|null
operator|||
name|html
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
literal|""
return|;
name|MatchResult
name|result
init|=
name|sStripAcessKeyRegExp
operator|.
name|exec
argument_list|(
name|html
argument_list|)
decl_stmt|;
return|return
operator|(
name|result
operator|==
literal|null
condition|?
name|html
else|:
name|result
operator|.
name|getGroup
argument_list|(
literal|1
argument_list|)
operator|+
name|result
operator|.
name|getGroup
argument_list|(
literal|2
argument_list|)
operator|+
name|result
operator|.
name|getGroup
argument_list|(
literal|3
argument_list|)
operator|)
return|;
block|}
block|}
end_class

end_unit

