begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.5 (University Timetabling Application)  * Copyright (C) 2014, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|mobile
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
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Iterator
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
name|Client
operator|.
name|GwtPageChangedHandler
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
name|UniTimeNotifications
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
name|UniTimeNotifications
operator|.
name|NotificationType
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
name|animation
operator|.
name|client
operator|.
name|Animation
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
name|ResizeEvent
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
name|ResizeHandler
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
name|Window
operator|.
name|ScrollEvent
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
name|RootPanel
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|MobileNotifications
implements|implements
name|UniTimeNotifications
operator|.
name|Display
block|{
specifier|protected
name|List
argument_list|<
name|Notification
argument_list|>
name|iNotifications
init|=
operator|new
name|ArrayList
argument_list|<
name|Notification
argument_list|>
argument_list|()
decl_stmt|;
specifier|protected
name|Timer
name|iMoveTimer
init|=
literal|null
decl_stmt|;
specifier|protected
name|Animation
name|iAnimation
decl_stmt|;
specifier|public
name|MobileNotifications
parameter_list|()
block|{
name|Window
operator|.
name|addResizeHandler
argument_list|(
operator|new
name|ResizeHandler
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onResize
parameter_list|(
name|ResizeEvent
name|event
parameter_list|)
block|{
name|delayedMove
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|Window
operator|.
name|addWindowScrollHandler
argument_list|(
operator|new
name|Window
operator|.
name|ScrollHandler
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onWindowScroll
parameter_list|(
name|ScrollEvent
name|event
parameter_list|)
block|{
name|delayedMove
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|Client
operator|.
name|addGwtPageChangedHandler
argument_list|(
operator|new
name|GwtPageChangedHandler
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onChange
parameter_list|(
name|GwtPageChangeEvent
name|event
parameter_list|)
block|{
name|delayedMove
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|iMoveTimer
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
name|move
argument_list|()
expr_stmt|;
block|}
block|}
expr_stmt|;
name|iAnimation
operator|=
operator|new
name|NotificationAnimation
argument_list|()
expr_stmt|;
block|}
specifier|protected
name|void
name|addNotification
parameter_list|(
specifier|final
name|Notification
name|notification
parameter_list|)
block|{
name|RootPanel
operator|.
name|get
argument_list|()
operator|.
name|add
argument_list|(
name|notification
argument_list|,
name|Window
operator|.
name|getScrollLeft
argument_list|()
argument_list|,
name|Window
operator|.
name|getScrollTop
argument_list|()
operator|+
name|Window
operator|.
name|getClientHeight
argument_list|()
argument_list|)
expr_stmt|;
name|iAnimation
operator|.
name|cancel
argument_list|()
expr_stmt|;
for|for
control|(
name|Iterator
argument_list|<
name|Notification
argument_list|>
name|i
init|=
name|iNotifications
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Notification
name|n
init|=
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|n
operator|.
name|equals
argument_list|(
name|notification
argument_list|)
condition|)
block|{
name|n
operator|.
name|hide
argument_list|()
expr_stmt|;
name|i
operator|.
name|remove
argument_list|()
expr_stmt|;
block|}
block|}
name|move
argument_list|()
expr_stmt|;
name|iNotifications
operator|.
name|add
argument_list|(
literal|0
argument_list|,
name|notification
argument_list|)
expr_stmt|;
name|iAnimation
operator|.
name|run
argument_list|(
literal|1000
argument_list|)
expr_stmt|;
name|Timer
name|timer
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
name|notification
operator|.
name|hide
argument_list|()
expr_stmt|;
name|iNotifications
operator|.
name|remove
argument_list|(
name|notification
argument_list|)
expr_stmt|;
block|}
block|}
decl_stmt|;
name|notification
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
name|notification
operator|.
name|hide
argument_list|()
expr_stmt|;
name|iNotifications
operator|.
name|remove
argument_list|(
name|notification
argument_list|)
expr_stmt|;
name|move
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|timer
operator|.
name|schedule
argument_list|(
literal|10000
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|move
parameter_list|()
block|{
name|int
name|height
init|=
literal|0
decl_stmt|;
name|iAnimation
operator|.
name|cancel
argument_list|()
expr_stmt|;
for|for
control|(
name|HTML
name|notification
range|:
name|iNotifications
control|)
block|{
name|height
operator|+=
name|notification
operator|.
name|getElement
argument_list|()
operator|.
name|getClientHeight
argument_list|()
expr_stmt|;
name|notification
operator|.
name|getElement
argument_list|()
operator|.
name|getStyle
argument_list|()
operator|.
name|setProperty
argument_list|(
literal|"left"
argument_list|,
name|Window
operator|.
name|getScrollLeft
argument_list|()
operator|+
literal|"px"
argument_list|)
expr_stmt|;
name|notification
operator|.
name|getElement
argument_list|()
operator|.
name|getStyle
argument_list|()
operator|.
name|setProperty
argument_list|(
literal|"top"
argument_list|,
operator|(
name|Window
operator|.
name|getScrollTop
argument_list|()
operator|+
name|Window
operator|.
name|getClientHeight
argument_list|()
operator|-
name|height
operator|)
operator|+
literal|"px"
argument_list|)
expr_stmt|;
block|}
block|}
specifier|protected
name|void
name|delayedMove
parameter_list|()
block|{
if|if
condition|(
operator|!
name|iNotifications
operator|.
name|isEmpty
argument_list|()
condition|)
name|iMoveTimer
operator|.
name|schedule
argument_list|(
literal|100
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|addNotification
parameter_list|(
name|String
name|html
parameter_list|,
name|NotificationType
name|type
parameter_list|)
block|{
switch|switch
condition|(
name|type
condition|)
block|{
case|case
name|ERROR
case|:
name|addNotification
argument_list|(
operator|new
name|Notification
argument_list|(
name|html
argument_list|,
literal|"unitime-MobileNotificationError"
argument_list|)
argument_list|)
expr_stmt|;
break|break;
case|case
name|WARN
case|:
name|addNotification
argument_list|(
operator|new
name|Notification
argument_list|(
name|html
argument_list|,
literal|"unitime-MobileNotificationWarning"
argument_list|)
argument_list|)
expr_stmt|;
break|break;
case|case
name|INFO
case|:
name|addNotification
argument_list|(
operator|new
name|Notification
argument_list|(
name|html
argument_list|,
literal|"unitime-MobileNotificationInfo"
argument_list|)
argument_list|)
expr_stmt|;
break|break;
block|}
block|}
specifier|private
class|class
name|NotificationAnimation
extends|extends
name|Animation
block|{
annotation|@
name|Override
specifier|protected
name|void
name|onUpdate
parameter_list|(
name|double
name|progress
parameter_list|)
block|{
if|if
condition|(
name|iNotifications
operator|.
name|isEmpty
argument_list|()
condition|)
return|return;
name|int
name|height
init|=
operator|-
operator|(
name|int
operator|)
name|Math
operator|.
name|round
argument_list|(
operator|(
literal|1.0
operator|-
name|progress
operator|)
operator|*
operator|(
name|iNotifications
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|getElement
argument_list|()
operator|.
name|getClientHeight
argument_list|()
operator|)
argument_list|)
decl_stmt|;
for|for
control|(
name|Notification
name|notification
range|:
name|iNotifications
control|)
block|{
name|height
operator|+=
name|notification
operator|.
name|getElement
argument_list|()
operator|.
name|getClientHeight
argument_list|()
expr_stmt|;
name|notification
operator|.
name|getElement
argument_list|()
operator|.
name|getStyle
argument_list|()
operator|.
name|setProperty
argument_list|(
literal|"left"
argument_list|,
name|Window
operator|.
name|getScrollLeft
argument_list|()
operator|+
literal|"px"
argument_list|)
expr_stmt|;
name|notification
operator|.
name|getElement
argument_list|()
operator|.
name|getStyle
argument_list|()
operator|.
name|setProperty
argument_list|(
literal|"top"
argument_list|,
operator|(
name|Window
operator|.
name|getScrollTop
argument_list|()
operator|+
name|Window
operator|.
name|getClientHeight
argument_list|()
operator|-
name|height
operator|)
operator|+
literal|"px"
argument_list|)
expr_stmt|;
block|}
block|}
block|}
specifier|public
specifier|static
class|class
name|Notification
extends|extends
name|HTML
block|{
name|Notification
parameter_list|(
name|String
name|text
parameter_list|,
name|String
name|style
parameter_list|)
block|{
name|super
argument_list|(
name|text
argument_list|)
expr_stmt|;
name|setStyleName
argument_list|(
literal|"unitime-MobileNotification"
argument_list|)
expr_stmt|;
name|addStyleName
argument_list|(
name|style
argument_list|)
expr_stmt|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|getHTML
argument_list|()
return|;
block|}
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|getHTML
argument_list|()
operator|.
name|hashCode
argument_list|()
return|;
block|}
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
if|if
condition|(
name|o
operator|==
literal|null
operator|||
operator|!
operator|(
name|o
operator|instanceof
name|Notification
operator|)
condition|)
return|return
literal|false
return|;
return|return
operator|(
operator|(
name|Notification
operator|)
name|o
operator|)
operator|.
name|getHTML
argument_list|()
operator|.
name|equals
argument_list|(
name|getHTML
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|void
name|hide
parameter_list|()
block|{
name|RootPanel
operator|.
name|get
argument_list|()
operator|.
name|remove
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|show
parameter_list|()
block|{
name|RootPanel
operator|.
name|get
argument_list|()
operator|.
name|add
argument_list|(
name|this
argument_list|,
name|Window
operator|.
name|getScrollLeft
argument_list|()
argument_list|,
name|Window
operator|.
name|getScrollTop
argument_list|()
operator|+
name|Window
operator|.
name|getClientHeight
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

