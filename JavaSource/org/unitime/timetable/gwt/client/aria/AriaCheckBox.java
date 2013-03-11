begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.4 (University Timetabling Application)  * Copyright (C) 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|aria
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
name|ui
operator|.
name|CheckBox
import|;
end_import

begin_class
specifier|public
class|class
name|AriaCheckBox
extends|extends
name|CheckBox
implements|implements
name|HasAriaLabel
block|{
name|Element
name|iAriaLabel
decl_stmt|;
specifier|public
name|AriaCheckBox
parameter_list|()
block|{
name|this
argument_list|(
name|DOM
operator|.
name|createInputCheck
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
name|AriaCheckBox
parameter_list|(
name|Element
name|elem
parameter_list|)
block|{
name|super
argument_list|(
name|elem
argument_list|)
expr_stmt|;
name|iAriaLabel
operator|=
name|DOM
operator|.
name|createLabel
argument_list|()
expr_stmt|;
name|iAriaLabel
operator|.
name|setId
argument_list|(
name|DOM
operator|.
name|createUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|iAriaLabel
operator|.
name|setClassName
argument_list|(
literal|"unitime-AriaLabel"
argument_list|)
expr_stmt|;
name|DOM
operator|.
name|appendChild
argument_list|(
name|getElement
argument_list|()
argument_list|,
name|iAriaLabel
argument_list|)
expr_stmt|;
name|Roles
operator|.
name|getCheckboxRole
argument_list|()
operator|.
name|setAriaLabelledbyProperty
argument_list|(
name|elem
argument_list|,
name|Id
operator|.
name|of
argument_list|(
name|iAriaLabel
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|setAriaLabel
parameter_list|(
name|String
name|text
parameter_list|)
block|{
name|iAriaLabel
operator|.
name|setInnerText
argument_list|(
name|text
operator|==
literal|null
condition|?
literal|""
else|:
name|text
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getAriaLabel
parameter_list|()
block|{
return|return
name|iAriaLabel
operator|.
name|getInnerText
argument_list|()
return|;
block|}
block|}
end_class

end_unit

