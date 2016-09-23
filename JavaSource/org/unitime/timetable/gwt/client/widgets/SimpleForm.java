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
name|FlexTable
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
name|SimpleForm
extends|extends
name|FlexTable
block|{
specifier|private
name|int
name|iColSpan
decl_stmt|;
specifier|public
name|SimpleForm
parameter_list|(
name|int
name|colSpan
parameter_list|)
block|{
name|super
argument_list|()
expr_stmt|;
name|iColSpan
operator|=
name|colSpan
expr_stmt|;
name|setStylePrimaryName
argument_list|(
literal|"unitime-MainTable"
argument_list|)
expr_stmt|;
name|addStyleName
argument_list|(
literal|"unitime-NotPrintableBottomLine"
argument_list|)
expr_stmt|;
name|setCellPadding
argument_list|(
literal|2
argument_list|)
expr_stmt|;
name|setCellSpacing
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
specifier|public
name|SimpleForm
parameter_list|()
block|{
name|this
argument_list|(
literal|2
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|setColSpan
parameter_list|(
name|int
name|colSpan
parameter_list|)
block|{
name|iColSpan
operator|=
name|colSpan
expr_stmt|;
block|}
specifier|public
name|int
name|getColSpan
parameter_list|()
block|{
return|return
name|iColSpan
return|;
block|}
specifier|public
name|int
name|addHeaderRow
parameter_list|(
name|Widget
name|widget
parameter_list|)
block|{
name|int
name|row
init|=
name|getRowCount
argument_list|()
decl_stmt|;
name|getFlexCellFormatter
argument_list|()
operator|.
name|setColSpan
argument_list|(
name|row
argument_list|,
literal|0
argument_list|,
name|iColSpan
argument_list|)
expr_stmt|;
name|getFlexCellFormatter
argument_list|()
operator|.
name|setStyleName
argument_list|(
name|row
argument_list|,
literal|0
argument_list|,
literal|"unitime-MainTableHeader"
argument_list|)
expr_stmt|;
name|getRowFormatter
argument_list|()
operator|.
name|setStyleName
argument_list|(
name|row
argument_list|,
literal|"unitime-MainTableHeaderRow"
argument_list|)
expr_stmt|;
name|setWidget
argument_list|(
name|row
argument_list|,
literal|0
argument_list|,
name|widget
argument_list|)
expr_stmt|;
return|return
name|row
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
name|int
name|row
init|=
name|getRowCount
argument_list|()
decl_stmt|;
name|getFlexCellFormatter
argument_list|()
operator|.
name|setColSpan
argument_list|(
name|row
argument_list|,
literal|0
argument_list|,
name|iColSpan
argument_list|)
expr_stmt|;
if|if
condition|(
name|widget
operator|instanceof
name|HasMobileScroll
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
literal|"table-row"
argument_list|)
expr_stmt|;
name|setWidget
argument_list|(
name|row
argument_list|,
literal|0
argument_list|,
name|scroll
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|setWidget
argument_list|(
name|row
argument_list|,
literal|0
argument_list|,
name|widget
argument_list|)
expr_stmt|;
block|}
return|return
name|row
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
name|int
name|row
init|=
name|getRowCount
argument_list|()
decl_stmt|;
name|getFlexCellFormatter
argument_list|()
operator|.
name|setColSpan
argument_list|(
name|row
argument_list|,
literal|0
argument_list|,
name|iColSpan
argument_list|)
expr_stmt|;
name|getFlexCellFormatter
argument_list|()
operator|.
name|setStyleName
argument_list|(
name|row
argument_list|,
literal|0
argument_list|,
literal|"unitime-MainTableBottomHeader"
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|printable
condition|)
name|getFlexCellFormatter
argument_list|()
operator|.
name|addStyleName
argument_list|(
name|row
argument_list|,
literal|0
argument_list|,
literal|"unitime-NoPrint"
argument_list|)
expr_stmt|;
name|getFlexCellFormatter
argument_list|()
operator|.
name|addStyleName
argument_list|(
name|row
argument_list|,
literal|0
argument_list|,
literal|"unitime-TopLine"
argument_list|)
expr_stmt|;
name|removeStyleName
argument_list|(
literal|"unitime-NotPrintableBottomLine"
argument_list|)
expr_stmt|;
name|setWidget
argument_list|(
name|row
argument_list|,
literal|0
argument_list|,
name|widget
argument_list|)
expr_stmt|;
return|return
name|row
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
name|widget
parameter_list|)
block|{
return|return
name|addRow
argument_list|(
name|text
argument_list|,
name|widget
argument_list|,
name|iColSpan
operator|-
literal|1
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
name|widget
parameter_list|,
name|int
name|colspan
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
name|widget
argument_list|,
name|colspan
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
name|widget
parameter_list|)
block|{
return|return
name|addRow
argument_list|(
name|header
argument_list|,
name|widget
argument_list|,
name|iColSpan
operator|-
literal|1
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
name|widget
parameter_list|,
name|int
name|colSpan
parameter_list|)
block|{
name|header
operator|.
name|addStyleName
argument_list|(
literal|"label-cell"
argument_list|)
expr_stmt|;
name|int
name|row
init|=
name|getRowCount
argument_list|()
decl_stmt|;
name|setWidget
argument_list|(
name|row
argument_list|,
literal|0
argument_list|,
name|header
argument_list|)
expr_stmt|;
if|if
condition|(
name|widget
operator|instanceof
name|HasMobileScroll
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
literal|"table-cell"
argument_list|)
expr_stmt|;
name|setWidget
argument_list|(
name|row
argument_list|,
literal|1
argument_list|,
name|scroll
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|widget
operator|.
name|addStyleName
argument_list|(
literal|"widget-cell"
argument_list|)
expr_stmt|;
name|setWidget
argument_list|(
name|row
argument_list|,
literal|1
argument_list|,
name|widget
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|colSpan
operator|!=
literal|1
condition|)
name|getFlexCellFormatter
argument_list|()
operator|.
name|setColSpan
argument_list|(
name|row
argument_list|,
literal|1
argument_list|,
name|colSpan
argument_list|)
expr_stmt|;
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
if|if
condition|(
name|widget
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
name|widget
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
name|widget
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
return|return
name|row
return|;
block|}
specifier|public
name|int
name|getRow
parameter_list|(
name|String
name|text
parameter_list|)
block|{
for|for
control|(
name|int
name|row
init|=
literal|0
init|;
name|row
operator|<
name|getRowCount
argument_list|()
condition|;
name|row
operator|++
control|)
block|{
if|if
condition|(
name|getCellCount
argument_list|(
name|row
argument_list|)
operator|>
literal|1
condition|)
block|{
name|Widget
name|w
init|=
name|getWidget
argument_list|(
name|row
argument_list|,
literal|0
argument_list|)
decl_stmt|;
if|if
condition|(
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
return|return
name|row
return|;
block|}
block|}
return|return
operator|-
literal|1
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|clear
parameter_list|()
block|{
for|for
control|(
name|int
name|row
init|=
name|getRowCount
argument_list|()
operator|-
literal|1
init|;
name|row
operator|>=
literal|0
condition|;
name|row
operator|--
control|)
name|removeRow
argument_list|(
name|row
argument_list|)
expr_stmt|;
block|}
specifier|public
name|int
name|getRowForWidget
parameter_list|(
name|Widget
name|w
parameter_list|)
block|{
for|for
control|(
name|Element
name|td
init|=
name|w
operator|.
name|getElement
argument_list|()
init|;
name|td
operator|!=
literal|null
condition|;
name|td
operator|=
name|DOM
operator|.
name|getParent
argument_list|(
name|td
argument_list|)
control|)
block|{
if|if
condition|(
name|td
operator|.
name|getPropertyString
argument_list|(
literal|"tagName"
argument_list|)
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"td"
argument_list|)
condition|)
block|{
name|Element
name|tr
init|=
name|DOM
operator|.
name|getParent
argument_list|(
name|td
argument_list|)
decl_stmt|;
name|Element
name|body
init|=
name|DOM
operator|.
name|getParent
argument_list|(
name|tr
argument_list|)
decl_stmt|;
if|if
condition|(
name|body
operator|==
name|getBodyElement
argument_list|()
condition|)
return|return
name|DOM
operator|.
name|getChildIndex
argument_list|(
name|body
argument_list|,
name|tr
argument_list|)
return|;
block|}
if|if
condition|(
name|td
operator|==
name|getBodyElement
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
specifier|static
interface|interface
name|HasMobileScroll
block|{}
block|}
end_class

end_unit

