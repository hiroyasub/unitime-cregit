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
name|offerings
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
name|client
operator|.
name|ToolBox
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
name|P
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
name|SimpleForm
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
name|UniTimeHeaderPanel
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
name|RootPanel
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
name|UIObject
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
comment|/**  * @author Stephanie Schluttenhofer  */
end_comment

begin_class
specifier|public
class|class
name|AssignInstructorsButton
extends|extends
name|Composite
block|{
specifier|protected
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
name|Long
name|iConfigId
init|=
literal|null
decl_stmt|;
specifier|private
name|SimpleForm
name|iAssignInstructorsPanel
decl_stmt|;
specifier|private
name|UniTimeHeaderPanel
name|iHeader
decl_stmt|;
specifier|private
name|List
argument_list|<
name|ClickHandler
argument_list|>
name|iAssignInstructorsClickHandlers
init|=
operator|new
name|ArrayList
argument_list|<
name|ClickHandler
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|Button
name|findButtonAndFixMargins
parameter_list|(
name|P
name|panel
parameter_list|)
block|{
name|panel
operator|.
name|getElement
argument_list|()
operator|.
name|getStyle
argument_list|()
operator|.
name|setMargin
argument_list|(
literal|0
argument_list|,
name|Unit
operator|.
name|PX
argument_list|)
expr_stmt|;
name|panel
operator|.
name|getElement
argument_list|()
operator|.
name|getStyle
argument_list|()
operator|.
name|setPadding
argument_list|(
literal|0
argument_list|,
name|Unit
operator|.
name|PX
argument_list|)
expr_stmt|;
name|Button
name|b
init|=
literal|null
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
name|panel
operator|.
name|getWidgetCount
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|Widget
name|w
init|=
operator|(
name|Widget
operator|)
name|panel
operator|.
name|getWidget
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
name|w
operator|instanceof
name|P
condition|)
block|{
name|P
name|np
init|=
operator|(
name|P
operator|)
name|w
decl_stmt|;
name|b
operator|=
name|findButtonAndFixMargins
argument_list|(
name|np
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|w
operator|instanceof
name|Button
condition|)
block|{
name|b
operator|=
operator|(
name|Button
operator|)
name|w
expr_stmt|;
name|b
operator|.
name|getElement
argument_list|()
operator|.
name|getStyle
argument_list|()
operator|.
name|setMargin
argument_list|(
literal|0
argument_list|,
name|Unit
operator|.
name|PX
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|w
operator|instanceof
name|UIObject
condition|)
block|{
name|UIObject
name|o
init|=
operator|(
name|UIObject
operator|)
name|w
decl_stmt|;
name|o
operator|.
name|getElement
argument_list|()
operator|.
name|getStyle
argument_list|()
operator|.
name|setMargin
argument_list|(
literal|0
argument_list|,
name|Unit
operator|.
name|PX
argument_list|)
expr_stmt|;
name|o
operator|.
name|getElement
argument_list|()
operator|.
name|getStyle
argument_list|()
operator|.
name|setPadding
argument_list|(
literal|0
argument_list|,
name|Unit
operator|.
name|PX
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|b
operator|!=
literal|null
condition|)
block|{
break|break;
block|}
block|}
return|return
name|b
return|;
block|}
specifier|public
name|AssignInstructorsButton
parameter_list|(
name|boolean
name|editable
parameter_list|)
block|{
name|iAssignInstructorsPanel
operator|=
operator|new
name|SimpleForm
argument_list|()
expr_stmt|;
name|iAssignInstructorsPanel
operator|.
name|removeStyleName
argument_list|(
literal|"unitime-NotPrintableBottomLine"
argument_list|)
expr_stmt|;
name|iHeader
operator|=
operator|new
name|UniTimeHeaderPanel
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|iHeader
operator|.
name|setTitleStyleName
argument_list|(
literal|"unitime3-HeaderTitle"
argument_list|)
expr_stmt|;
if|if
condition|(
name|editable
condition|)
block|{
name|iHeader
operator|.
name|addButton
argument_list|(
literal|"add"
argument_list|,
name|MESSAGES
operator|.
name|buttonAssignInstructors
argument_list|()
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
name|ToolBox
operator|.
name|open
argument_list|(
name|GWT
operator|.
name|getHostPageBaseURL
argument_list|()
operator|+
literal|"gwt.jsp?page=assignClassInstructors&configId="
operator|+
name|iConfigId
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|findButtonAndFixMargins
argument_list|(
name|iHeader
argument_list|)
expr_stmt|;
block|}
name|iAssignInstructorsPanel
operator|.
name|addRow
argument_list|(
name|iHeader
argument_list|)
expr_stmt|;
name|initWidget
argument_list|(
name|iAssignInstructorsPanel
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|insert
parameter_list|(
specifier|final
name|RootPanel
name|panel
parameter_list|)
block|{
name|iConfigId
operator|=
name|Long
operator|.
name|valueOf
argument_list|(
name|panel
operator|.
name|getElement
argument_list|()
operator|.
name|getInnerText
argument_list|()
argument_list|)
expr_stmt|;
name|panel
operator|.
name|getElement
argument_list|()
operator|.
name|setInnerText
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|panel
operator|.
name|add
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|panel
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|addAssignInstructorsClickHandler
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
name|evt
parameter_list|)
block|{
name|ToolBox
operator|.
name|open
argument_list|(
name|GWT
operator|.
name|getHostPageBaseURL
argument_list|()
operator|+
literal|"gwt.jsp?page=assignClassInstructors&configId="
operator|+
name|iConfigId
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|addAssignInstructorsClickHandler
parameter_list|(
name|ClickHandler
name|h
parameter_list|)
block|{
name|iAssignInstructorsClickHandlers
operator|.
name|add
argument_list|(
name|h
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

