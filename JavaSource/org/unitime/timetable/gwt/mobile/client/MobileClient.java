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
name|Client
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
name|RunAsyncCallback
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
name|dom
operator|.
name|client
operator|.
name|NodeList
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
name|ui
operator|.
name|RootPanel
import|;
end_import

begin_import
import|import
name|com
operator|.
name|googlecode
operator|.
name|mgwt
operator|.
name|ui
operator|.
name|client
operator|.
name|MGWT
import|;
end_import

begin_import
import|import
name|com
operator|.
name|googlecode
operator|.
name|mgwt
operator|.
name|ui
operator|.
name|client
operator|.
name|MGWTSettings
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|MobileClient
extends|extends
name|Client
block|{
annotation|@
name|Override
specifier|public
name|void
name|onModuleLoad
parameter_list|()
block|{
name|MGWTSettings
name|settings
init|=
name|MGWTSettings
operator|.
name|getAppSetting
argument_list|()
decl_stmt|;
name|settings
operator|.
name|setPreventScrolling
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|MGWT
operator|.
name|applySettings
argument_list|(
name|settings
argument_list|)
expr_stmt|;
name|super
operator|.
name|onModuleLoad
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|onModuleLoadDeferred
parameter_list|()
block|{
name|super
operator|.
name|onModuleLoadDeferred
argument_list|()
expr_stmt|;
comment|// load components
for|for
control|(
specifier|final
name|MobileComponents
name|c
range|:
name|MobileComponents
operator|.
name|values
argument_list|()
control|)
block|{
specifier|final
name|RootPanel
name|p
init|=
name|RootPanel
operator|.
name|get
argument_list|(
name|c
operator|.
name|id
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|p
operator|!=
literal|null
condition|)
block|{
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
name|initComponentAsync
argument_list|(
name|p
argument_list|,
name|c
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|p
operator|==
literal|null
operator|&&
name|c
operator|.
name|isMultiple
argument_list|()
condition|)
block|{
name|NodeList
argument_list|<
name|Element
argument_list|>
name|x
init|=
name|getElementsByName
argument_list|(
name|c
operator|.
name|id
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|x
operator|!=
literal|null
operator|&&
name|x
operator|.
name|getLength
argument_list|()
operator|>
literal|0
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
name|x
operator|.
name|getLength
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|Element
name|e
init|=
name|x
operator|.
name|getItem
argument_list|(
name|i
argument_list|)
decl_stmt|;
name|e
operator|.
name|setId
argument_list|(
name|DOM
operator|.
name|createUniqueId
argument_list|()
argument_list|)
expr_stmt|;
specifier|final
name|RootPanel
name|q
init|=
name|RootPanel
operator|.
name|get
argument_list|(
name|e
operator|.
name|getId
argument_list|()
argument_list|)
decl_stmt|;
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
name|initComponentAsync
argument_list|(
name|q
argument_list|,
name|c
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
specifier|public
name|void
name|initComponentAsync
parameter_list|(
specifier|final
name|RootPanel
name|panel
parameter_list|,
specifier|final
name|MobileComponents
name|comp
parameter_list|)
block|{
name|GWT
operator|.
name|runAsync
argument_list|(
operator|new
name|RunAsyncCallback
argument_list|()
block|{
specifier|public
name|void
name|onSuccess
parameter_list|()
block|{
name|comp
operator|.
name|insert
argument_list|(
name|panel
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|onFailure
parameter_list|(
name|Throwable
name|reason
parameter_list|)
block|{
block|}
block|}
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

