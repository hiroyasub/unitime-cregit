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
name|TimeSelector
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
name|UniTimeWidget
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
name|rpc
operator|.
name|IsSerializable
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

begin_class
specifier|public
class|class
name|StartEndTimeSelector
extends|extends
name|Composite
implements|implements
name|HasValue
argument_list|<
name|StartEndTimeSelector
operator|.
name|StartEndTime
argument_list|>
block|{
specifier|private
name|UniTimeWidget
argument_list|<
name|HorizontalPanel
argument_list|>
name|iPanel
decl_stmt|;
specifier|private
name|TimeSelector
name|iStart
decl_stmt|,
name|iEnd
decl_stmt|;
specifier|public
name|StartEndTimeSelector
parameter_list|()
block|{
name|HorizontalPanel
name|panel
init|=
operator|new
name|HorizontalPanel
argument_list|()
decl_stmt|;
name|panel
operator|.
name|setSpacing
argument_list|(
literal|2
argument_list|)
expr_stmt|;
name|panel
operator|.
name|setStyleName
argument_list|(
literal|"unitime-TimeSelectorPanel"
argument_list|)
expr_stmt|;
name|Label
name|labelFrom
init|=
operator|new
name|Label
argument_list|(
literal|"From:"
argument_list|)
decl_stmt|;
name|panel
operator|.
name|add
argument_list|(
name|labelFrom
argument_list|)
expr_stmt|;
name|panel
operator|.
name|setCellVerticalAlignment
argument_list|(
name|labelFrom
argument_list|,
name|HasVerticalAlignment
operator|.
name|ALIGN_MIDDLE
argument_list|)
expr_stmt|;
name|iStart
operator|=
operator|new
name|TimeSelector
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|panel
operator|.
name|add
argument_list|(
name|iStart
argument_list|)
expr_stmt|;
name|Label
name|labelTo
init|=
operator|new
name|Label
argument_list|(
literal|"To:"
argument_list|)
decl_stmt|;
name|labelTo
operator|.
name|getElement
argument_list|()
operator|.
name|getStyle
argument_list|()
operator|.
name|setPaddingLeft
argument_list|(
literal|5
argument_list|,
name|Unit
operator|.
name|PX
argument_list|)
expr_stmt|;
name|panel
operator|.
name|add
argument_list|(
name|labelTo
argument_list|)
expr_stmt|;
name|panel
operator|.
name|setCellVerticalAlignment
argument_list|(
name|labelTo
argument_list|,
name|HasVerticalAlignment
operator|.
name|ALIGN_MIDDLE
argument_list|)
expr_stmt|;
name|iEnd
operator|=
operator|new
name|TimeSelector
argument_list|(
name|iStart
argument_list|)
expr_stmt|;
name|panel
operator|.
name|add
argument_list|(
name|iEnd
argument_list|)
expr_stmt|;
name|iPanel
operator|=
operator|new
name|UniTimeWidget
argument_list|<
name|HorizontalPanel
argument_list|>
argument_list|(
name|panel
argument_list|)
expr_stmt|;
name|initWidget
argument_list|(
name|iPanel
argument_list|)
expr_stmt|;
name|iStart
operator|.
name|addValueChangeHandler
argument_list|(
operator|new
name|ValueChangeHandler
argument_list|<
name|Integer
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
name|Integer
argument_list|>
name|event
parameter_list|)
block|{
name|ValueChangeEvent
operator|.
name|fire
argument_list|(
name|StartEndTimeSelector
operator|.
name|this
argument_list|,
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|iEnd
operator|.
name|addValueChangeHandler
argument_list|(
operator|new
name|ValueChangeHandler
argument_list|<
name|Integer
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
name|Integer
argument_list|>
name|event
parameter_list|)
block|{
name|ValueChangeEvent
operator|.
name|fire
argument_list|(
name|StartEndTimeSelector
operator|.
name|this
argument_list|,
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
class|class
name|StartEndTime
implements|implements
name|IsSerializable
block|{
specifier|private
name|Integer
name|iStart
decl_stmt|,
name|iEnd
decl_stmt|;
specifier|public
name|StartEndTime
parameter_list|()
block|{
block|}
specifier|public
name|StartEndTime
parameter_list|(
name|Integer
name|start
parameter_list|,
name|Integer
name|end
parameter_list|)
block|{
name|iStart
operator|=
name|start
expr_stmt|;
name|iEnd
operator|=
name|end
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasStart
parameter_list|()
block|{
return|return
name|iStart
operator|!=
literal|null
return|;
block|}
specifier|public
name|Integer
name|getStart
parameter_list|()
block|{
return|return
name|iStart
return|;
block|}
specifier|public
name|boolean
name|hasEnd
parameter_list|()
block|{
return|return
name|iEnd
operator|!=
literal|null
return|;
block|}
specifier|public
name|Integer
name|getEnd
parameter_list|()
block|{
return|return
name|iEnd
return|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|HandlerRegistration
name|addValueChangeHandler
parameter_list|(
name|ValueChangeHandler
argument_list|<
name|StartEndTime
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
name|StartEndTime
name|getValue
parameter_list|()
block|{
return|return
operator|new
name|StartEndTime
argument_list|(
name|iStart
operator|.
name|getValue
argument_list|()
argument_list|,
name|iEnd
operator|.
name|getValue
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|setValue
parameter_list|(
name|StartEndTime
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
name|StartEndTime
name|value
parameter_list|,
name|boolean
name|fireEvents
parameter_list|)
block|{
name|iStart
operator|.
name|setValue
argument_list|(
name|value
operator|==
literal|null
condition|?
literal|null
else|:
name|value
operator|.
name|getStart
argument_list|()
argument_list|)
expr_stmt|;
name|iEnd
operator|.
name|setValue
argument_list|(
name|value
operator|==
literal|null
condition|?
literal|null
else|:
name|value
operator|.
name|getEnd
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
name|value
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

