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
name|HasText
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
name|ScrollPanel
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
name|FlowForm
extends|extends
name|P
block|{
specifier|public
name|FlowForm
parameter_list|()
block|{
name|super
argument_list|(
literal|"unitime-FlowForm"
argument_list|)
expr_stmt|;
name|addStyleName
argument_list|(
literal|"unitime-NotPrintableBottomLine"
argument_list|)
expr_stmt|;
block|}
specifier|public
name|int
name|addHeaderRow
parameter_list|(
name|Widget
name|widget
parameter_list|)
block|{
name|P
name|row
init|=
operator|new
name|P
argument_list|(
name|DOM
operator|.
name|createSpan
argument_list|()
argument_list|,
literal|"row-cell"
argument_list|,
literal|"unitime-MainTableHeader"
argument_list|)
decl_stmt|;
name|row
operator|.
name|add
argument_list|(
name|widget
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|row
argument_list|)
expr_stmt|;
return|return
name|getWidgetCount
argument_list|()
operator|-
literal|1
return|;
block|}
specifier|public
name|int
name|addHeaderRow
parameter_list|(
name|String
name|text
parameter_list|)
block|{
return|return
name|addHeaderRow
argument_list|(
operator|new
name|Label
argument_list|(
name|text
argument_list|,
literal|false
argument_list|)
argument_list|)
return|;
block|}
specifier|public
name|int
name|addRow
parameter_list|(
name|Widget
name|widget
parameter_list|)
block|{
name|P
name|row
init|=
operator|new
name|P
argument_list|(
name|DOM
operator|.
name|createSpan
argument_list|()
argument_list|,
literal|"row-cell"
argument_list|)
decl_stmt|;
name|row
operator|.
name|add
argument_list|(
name|widget
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|row
argument_list|)
expr_stmt|;
return|return
name|getWidgetCount
argument_list|()
operator|-
literal|1
return|;
block|}
specifier|protected
name|int
name|addBottomRow
parameter_list|(
name|Widget
name|widget
parameter_list|,
name|boolean
name|printable
parameter_list|)
block|{
name|P
name|row
init|=
operator|new
name|P
argument_list|(
name|DOM
operator|.
name|createSpan
argument_list|()
argument_list|,
literal|"row-cell"
argument_list|,
literal|"unitime-MainTableBottomHeader"
argument_list|,
literal|"unitime-TopLine"
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|printable
condition|)
name|row
operator|.
name|addStyleName
argument_list|(
literal|"unitime-NoPrint"
argument_list|)
expr_stmt|;
name|removeStyleName
argument_list|(
literal|"unitime-NotPrintableBottomLine"
argument_list|)
expr_stmt|;
name|row
operator|.
name|add
argument_list|(
name|widget
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|row
argument_list|)
expr_stmt|;
return|return
name|getWidgetCount
argument_list|()
operator|-
literal|1
return|;
block|}
specifier|public
name|int
name|addBottomRow
parameter_list|(
name|Widget
name|widget
parameter_list|)
block|{
return|return
name|addBottomRow
argument_list|(
name|widget
argument_list|,
literal|true
argument_list|)
return|;
block|}
specifier|public
name|int
name|addNotPrintableBottomRow
parameter_list|(
name|Widget
name|widget
parameter_list|)
block|{
return|return
name|addBottomRow
argument_list|(
name|widget
argument_list|,
literal|false
argument_list|)
return|;
block|}
specifier|public
name|int
name|addRow
parameter_list|(
name|String
name|text
parameter_list|,
name|Widget
modifier|...
name|widgets
parameter_list|)
block|{
return|return
name|addRow
argument_list|(
operator|new
name|Label
argument_list|(
name|text
argument_list|)
argument_list|,
name|widgets
argument_list|)
return|;
block|}
specifier|public
name|int
name|addRow
parameter_list|(
name|Widget
name|header
parameter_list|,
name|Widget
modifier|...
name|widgets
parameter_list|)
block|{
if|if
condition|(
name|header
operator|.
name|getElement
argument_list|()
operator|.
name|getId
argument_list|()
operator|==
literal|null
operator|||
name|header
operator|.
name|getElement
argument_list|()
operator|.
name|getId
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
name|header
operator|.
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
name|P
name|head
init|=
operator|new
name|P
argument_list|(
name|DOM
operator|.
name|createSpan
argument_list|()
argument_list|,
literal|"header-cell"
argument_list|)
decl_stmt|;
name|head
operator|.
name|add
argument_list|(
name|header
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|head
argument_list|)
expr_stmt|;
for|for
control|(
name|Widget
name|widget
range|:
name|widgets
control|)
block|{
name|P
name|body
init|=
operator|new
name|P
argument_list|(
name|DOM
operator|.
name|createSpan
argument_list|()
argument_list|,
literal|"content-cell"
argument_list|)
decl_stmt|;
name|add
argument_list|(
name|body
argument_list|)
expr_stmt|;
if|if
condition|(
name|widget
operator|instanceof
name|UniTimeTable
condition|)
block|{
name|ScrollPanel
name|scroll
init|=
operator|new
name|ScrollPanel
argument_list|(
name|widget
argument_list|)
decl_stmt|;
name|scroll
operator|.
name|addStyleName
argument_list|(
literal|"scroll"
argument_list|)
expr_stmt|;
name|body
operator|.
name|add
argument_list|(
name|scroll
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|body
operator|.
name|add
argument_list|(
name|widget
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|widgets
operator|.
name|length
operator|>
literal|0
condition|)
block|{
if|if
condition|(
name|widgets
index|[
literal|0
index|]
operator|instanceof
name|UniTimeWidget
condition|)
name|Roles
operator|.
name|getTextboxRole
argument_list|()
operator|.
name|setAriaLabelledbyProperty
argument_list|(
operator|(
operator|(
name|UniTimeWidget
operator|)
name|widgets
index|[
literal|0
index|]
operator|)
operator|.
name|getWidget
argument_list|()
operator|.
name|getElement
argument_list|()
argument_list|,
name|Id
operator|.
name|of
argument_list|(
name|header
operator|.
name|getElement
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
else|else
name|Roles
operator|.
name|getTextboxRole
argument_list|()
operator|.
name|setAriaLabelledbyProperty
argument_list|(
name|widgets
index|[
literal|0
index|]
operator|.
name|getElement
argument_list|()
argument_list|,
name|Id
operator|.
name|of
argument_list|(
name|header
operator|.
name|getElement
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|getWidgetCount
argument_list|()
operator|-
name|widgets
operator|.
name|length
return|;
block|}
specifier|public
name|int
name|getCell
parameter_list|(
name|String
name|text
parameter_list|)
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
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
name|getWidget
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
literal|"header-cell"
operator|.
name|equals
argument_list|(
name|w
operator|.
name|getStylePrimaryName
argument_list|()
argument_list|)
operator|&&
name|w
operator|instanceof
name|HasText
operator|&&
name|text
operator|.
name|equals
argument_list|(
operator|(
operator|(
name|HasText
operator|)
name|w
operator|)
operator|.
name|getText
argument_list|()
argument_list|)
condition|)
block|{
return|return
literal|1
operator|+
name|i
return|;
block|}
block|}
return|return
operator|-
literal|1
return|;
block|}
specifier|public
name|int
name|getCellForWidget
parameter_list|(
name|Widget
name|w
parameter_list|)
block|{
for|for
control|(
name|Element
name|e
init|=
name|w
operator|.
name|getElement
argument_list|()
init|;
name|e
operator|!=
literal|null
condition|;
name|e
operator|=
name|DOM
operator|.
name|getParent
argument_list|(
name|e
argument_list|)
control|)
block|{
if|if
condition|(
name|e
operator|.
name|getPropertyString
argument_list|(
literal|"tagName"
argument_list|)
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"span"
argument_list|)
condition|)
block|{
if|if
condition|(
name|DOM
operator|.
name|getParent
argument_list|(
name|e
argument_list|)
operator|==
name|getElement
argument_list|()
condition|)
return|return
name|DOM
operator|.
name|getChildIndex
argument_list|(
name|getElement
argument_list|()
argument_list|,
name|e
argument_list|)
return|;
block|}
if|if
condition|(
name|e
operator|==
name|getElement
argument_list|()
condition|)
block|{
return|return
operator|-
literal|1
return|;
block|}
block|}
return|return
operator|-
literal|1
return|;
block|}
specifier|public
name|void
name|setVisible
parameter_list|(
name|int
name|cell
parameter_list|,
name|boolean
name|visible
parameter_list|)
block|{
if|if
condition|(
name|cell
operator|<
literal|0
operator|||
name|cell
operator|>=
name|getWidgetCount
argument_list|()
condition|)
return|return;
name|getWidget
argument_list|(
name|cell
argument_list|)
operator|.
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
if|if
condition|(
literal|"content-cell"
operator|.
name|equals
argument_list|(
name|getWidget
argument_list|(
name|cell
argument_list|)
operator|.
name|getStylePrimaryName
argument_list|()
argument_list|)
condition|)
name|getWidget
argument_list|(
name|cell
operator|-
literal|1
argument_list|)
operator|.
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

