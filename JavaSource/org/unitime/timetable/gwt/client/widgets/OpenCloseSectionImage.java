begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.3 - 3.5 (University Timetabling Application)  * Copyright (C) 2011 - 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|Cursor
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
name|logical
operator|.
name|shared
operator|.
name|HasValueChangeHandlers
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
name|ui
operator|.
name|HasValue
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
name|Image
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|OpenCloseSectionImage
extends|extends
name|Image
implements|implements
name|HasValueChangeHandlers
argument_list|<
name|Boolean
argument_list|>
implements|,
name|HasValue
argument_list|<
name|Boolean
argument_list|>
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
name|boolean
name|iValue
init|=
literal|true
decl_stmt|;
specifier|public
name|OpenCloseSectionImage
parameter_list|(
name|boolean
name|opened
parameter_list|)
block|{
name|super
argument_list|(
name|RESOURCES
operator|.
name|treeOpen
argument_list|()
argument_list|)
expr_stmt|;
name|getElement
argument_list|()
operator|.
name|getStyle
argument_list|()
operator|.
name|setCursor
argument_list|(
name|Cursor
operator|.
name|POINTER
argument_list|)
expr_stmt|;
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
name|setValue
argument_list|(
operator|!
name|getValue
argument_list|()
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|HandlerRegistration
name|addValueChangeHandler
parameter_list|(
specifier|final
name|ValueChangeHandler
argument_list|<
name|Boolean
argument_list|>
name|handler
parameter_list|)
block|{
return|return
name|addHandler
argument_list|(
name|handler
argument_list|,
name|ValueChangeEvent
operator|.
name|getType
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Boolean
name|getValue
parameter_list|()
block|{
return|return
name|iValue
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|setValue
parameter_list|(
name|Boolean
name|value
parameter_list|)
block|{
name|setValue
argument_list|(
name|value
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|setValue
parameter_list|(
name|Boolean
name|value
parameter_list|,
name|boolean
name|fireEvents
parameter_list|)
block|{
if|if
condition|(
name|value
operator|==
literal|null
condition|)
return|return;
name|iValue
operator|=
name|value
expr_stmt|;
name|setResource
argument_list|(
name|iValue
condition|?
name|RESOURCES
operator|.
name|treeOpen
argument_list|()
else|:
name|RESOURCES
operator|.
name|treeClosed
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|fireEvents
condition|)
name|ValueChangeEvent
operator|.
name|fire
argument_list|(
name|this
argument_list|,
name|iValue
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

