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
name|TabPanel
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
comment|/**  * {@link TabPanel} wrapper to avoid deprecation warnings.  * To be used only for pages not based on LayoutPanel (and preferably avoided in the future development).  */
end_comment

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|UniTimeTabPanel
extends|extends
name|TabPanel
block|{
specifier|public
name|UniTimeTabPanel
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
specifier|public
name|void
name|add
parameter_list|(
name|Widget
name|w
parameter_list|,
name|String
name|tabText
parameter_list|,
name|boolean
name|asHTML
parameter_list|)
block|{
name|super
operator|.
name|add
argument_list|(
name|w
argument_list|,
name|tabText
argument_list|,
name|asHTML
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|insert
parameter_list|(
name|Widget
name|widget
parameter_list|,
name|String
name|tabText
parameter_list|,
name|boolean
name|asHTML
parameter_list|,
name|int
name|beforeIndex
parameter_list|)
block|{
name|super
operator|.
name|insert
argument_list|(
name|widget
argument_list|,
name|tabText
argument_list|,
name|asHTML
argument_list|,
name|beforeIndex
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|setDeckSize
parameter_list|(
name|String
name|width
parameter_list|,
name|String
name|height
parameter_list|)
block|{
name|getDeckPanel
argument_list|()
operator|.
name|setSize
argument_list|(
name|width
argument_list|,
name|height
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|setDeckStyleName
parameter_list|(
name|String
name|style
parameter_list|)
block|{
name|getDeckPanel
argument_list|()
operator|.
name|setStyleName
argument_list|(
name|style
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|selectTab
parameter_list|(
name|int
name|index
parameter_list|)
block|{
if|if
condition|(
name|index
operator|<
name|getTabBar
argument_list|()
operator|.
name|getTabCount
argument_list|()
condition|)
name|super
operator|.
name|selectTab
argument_list|(
name|index
argument_list|)
expr_stmt|;
block|}
specifier|public
name|HandlerRegistration
name|addSelectionHandler
parameter_list|(
name|SelectionHandler
argument_list|<
name|Integer
argument_list|>
name|handler
parameter_list|)
block|{
return|return
name|super
operator|.
name|addSelectionHandler
argument_list|(
name|handler
argument_list|)
return|;
block|}
specifier|public
name|int
name|getTabCount
parameter_list|()
block|{
return|return
name|getTabBar
argument_list|()
operator|.
name|getTabCount
argument_list|()
return|;
block|}
specifier|public
name|int
name|getSelectedTab
parameter_list|()
block|{
return|return
name|getTabBar
argument_list|()
operator|.
name|getSelectedTab
argument_list|()
return|;
block|}
block|}
end_class

end_unit

