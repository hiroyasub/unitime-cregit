begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.1 (University Timetabling Application)  * Copyright (C) 2008, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program; if not, write to the Free Software Foundation, Inc.,  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|util
package|;
end_package

begin_comment
comment|/**  * Customized Message Resources   * @author Heston Fernandes  */
end_comment

begin_class
specifier|public
class|class
name|MessageResourcesFactory
extends|extends
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|util
operator|.
name|MessageResourcesFactory
block|{
annotation|@
name|Override
specifier|public
name|MessageResources
name|createResources
parameter_list|(
name|String
name|config
parameter_list|)
block|{
return|return
operator|new
name|MessageResources
argument_list|(
name|this
argument_list|,
name|config
argument_list|,
name|this
operator|.
name|getReturnNull
argument_list|()
argument_list|)
return|;
block|}
block|}
end_class

end_unit

