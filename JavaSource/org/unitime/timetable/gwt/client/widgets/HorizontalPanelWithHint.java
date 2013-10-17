begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 - 3.5 (University Timetabling Application)  * Copyright (C) 2010 - 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|Widget
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|HorizontalPanelWithHint
extends|extends
name|HorizontalPanel
block|{
specifier|private
name|PopupPanel
name|iHint
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
specifier|public
name|HorizontalPanelWithHint
parameter_list|(
name|Widget
name|hint
parameter_list|)
block|{
name|super
argument_list|()
expr_stmt|;
name|iHint
operator|=
operator|new
name|PopupPanel
argument_list|()
expr_stmt|;
name|iHint
operator|.
name|setWidget
argument_list|(
name|hint
argument_list|)
expr_stmt|;
name|iHint
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
name|iHint
operator|.
name|show
argument_list|()
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
name|iHint
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
name|hideHint
parameter_list|()
block|{
if|if
condition|(
name|iHint
operator|.
name|isShowing
argument_list|()
condition|)
name|iHint
operator|.
name|hide
argument_list|()
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
name|int
name|x
init|=
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
decl_stmt|;
name|int
name|y
init|=
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
decl_stmt|;
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
name|iHint
operator|.
name|isShowing
argument_list|()
condition|)
block|{
name|iHint
operator|.
name|setPopupPosition
argument_list|(
name|x
argument_list|,
name|y
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
name|iHint
operator|.
name|setPopupPosition
argument_list|(
name|x
argument_list|,
name|y
argument_list|)
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
name|iHint
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
end_class

end_unit

