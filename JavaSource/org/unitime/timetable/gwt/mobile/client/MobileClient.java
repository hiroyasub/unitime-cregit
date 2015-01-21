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
name|mobile
operator|.
name|client
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|logging
operator|.
name|Logger
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
name|Document
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
name|MetaElement
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
name|dom
operator|.
name|client
operator|.
name|event
operator|.
name|orientation
operator|.
name|OrientationChangeEvent
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
name|dom
operator|.
name|client
operator|.
name|event
operator|.
name|orientation
operator|.
name|OrientationChangeHandler
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
specifier|public
specifier|static
name|Logger
name|sLogger
init|=
name|Logger
operator|.
name|getLogger
argument_list|(
name|MobileClient
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
annotation|@
name|Override
specifier|public
name|void
name|onModuleLoadDeferred
parameter_list|()
block|{
name|MGWTSettings
name|settings
init|=
operator|new
name|MGWTSettings
argument_list|()
decl_stmt|;
name|settings
operator|.
name|setViewPort
argument_list|(
operator|new
name|Viewport
argument_list|()
argument_list|)
expr_stmt|;
name|settings
operator|.
name|setFullscreen
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|settings
operator|.
name|setPreventScrolling
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|settings
operator|.
name|setIconUrl
argument_list|(
literal|"images/unitime-phone.png"
argument_list|)
expr_stmt|;
name|settings
operator|.
name|setFixIOS71BodyBug
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|MGWT
operator|.
name|applySettings
argument_list|(
name|settings
argument_list|)
expr_stmt|;
name|MGWT
operator|.
name|addOrientationChangeHandler
argument_list|(
operator|new
name|OrientationChangeHandler
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onOrientationChanged
parameter_list|(
name|OrientationChangeEvent
name|event
parameter_list|)
block|{
name|NodeList
argument_list|<
name|Element
argument_list|>
name|tags
init|=
name|Document
operator|.
name|get
argument_list|()
operator|.
name|getElementsByTagName
argument_list|(
literal|"meta"
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
name|tags
operator|.
name|getLength
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|MetaElement
name|meta
init|=
operator|(
name|MetaElement
operator|)
name|tags
operator|.
name|getItem
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
name|meta
operator|.
name|getName
argument_list|()
operator|.
name|equals
argument_list|(
literal|"viewport"
argument_list|)
condition|)
block|{
name|meta
operator|.
name|setContent
argument_list|(
operator|new
name|Viewport
argument_list|()
operator|.
name|getContent
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
argument_list|)
expr_stmt|;
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
specifier|public
specifier|native
specifier|static
name|int
name|getScreenWidth
parameter_list|()
comment|/*-{ 		if ($wnd.orientation == 90 || $wnd.orientation == -90) 			return ($wnd.screen.width> $wnd.screen.height ? $wnd.screen.width : $wnd.screen.height); 		else 			return ($wnd.screen.width< $wnd.screen.height ? $wnd.screen.width : $wnd.screen.height); 	}-*/
function_decl|;
specifier|public
specifier|static
class|class
name|Viewport
extends|extends
name|MGWTSettings
operator|.
name|ViewPort
block|{
annotation|@
name|Override
specifier|public
name|String
name|getContent
parameter_list|()
block|{
name|int
name|sw
init|=
name|getScreenWidth
argument_list|()
decl_stmt|;
if|if
condition|(
name|sw
operator|<
literal|800
condition|)
block|{
return|return
literal|"initial-scale="
operator|+
operator|(
name|sw
operator|/
literal|800.0
operator|)
operator|+
literal|",minimum-scale="
operator|+
operator|(
name|sw
operator|/
literal|3200.0
operator|)
operator|+
literal|",maximum-scale="
operator|+
operator|(
name|sw
operator|/
literal|200.0
operator|)
operator|+
literal|",width=800,user-scalable=yes"
return|;
block|}
else|else
block|{
return|return
literal|"initial-scale=1.0,minimum-scale=0.25,maximum-scale=1.0,width=device-width,user-scalable=yes"
return|;
block|}
block|}
block|}
block|}
end_class

end_unit

