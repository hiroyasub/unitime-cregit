begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 (University Timetabling Application)  * Copyright (C) 2010, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|setWidth
argument_list|(
literal|"100%"
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
argument_list|,
literal|false
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
name|setWidget
argument_list|(
name|row
argument_list|,
literal|1
argument_list|,
name|widget
argument_list|)
expr_stmt|;
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
block|}
end_class

end_unit

