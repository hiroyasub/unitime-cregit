begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.4 - 3.5 (University Timetabling Application)  * Copyright (C) 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|ui
operator|.
name|Anchor
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|AriaAnchor
extends|extends
name|Anchor
implements|implements
name|HasAriaLabel
block|{
specifier|public
name|AriaAnchor
parameter_list|(
name|String
name|text
parameter_list|)
block|{
name|super
argument_list|(
name|text
argument_list|,
literal|false
argument_list|,
literal|"#"
argument_list|)
expr_stmt|;
block|}
specifier|public
name|AriaAnchor
parameter_list|(
name|String
name|text
parameter_list|,
name|boolean
name|asHtml
parameter_list|)
block|{
name|super
argument_list|(
name|text
argument_list|,
name|asHtml
argument_list|,
literal|"#"
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
name|Roles
operator|.
name|getLinkRole
argument_list|()
operator|.
name|getAriaLabelProperty
argument_list|(
name|getElement
argument_list|()
argument_list|)
return|;
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
if|if
condition|(
name|text
operator|==
literal|null
operator|||
name|text
operator|.
name|isEmpty
argument_list|()
condition|)
name|Roles
operator|.
name|getLinkRole
argument_list|()
operator|.
name|removeAriaLabelledbyProperty
argument_list|(
name|getElement
argument_list|()
argument_list|)
expr_stmt|;
else|else
name|Roles
operator|.
name|getLinkRole
argument_list|()
operator|.
name|setAriaLabelProperty
argument_list|(
name|getElement
argument_list|()
argument_list|,
name|text
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

