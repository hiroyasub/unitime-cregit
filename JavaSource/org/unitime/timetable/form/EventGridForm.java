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
name|form
package|;
end_package

begin_class
specifier|public
class|class
name|EventGridForm
extends|extends
name|EventAddForm
block|{
specifier|private
name|String
name|iTable
init|=
literal|null
decl_stmt|;
specifier|public
name|void
name|setTable
parameter_list|(
name|String
name|table
parameter_list|)
block|{
name|iTable
operator|=
name|table
expr_stmt|;
block|}
specifier|public
name|String
name|getTable
parameter_list|()
block|{
return|return
name|iTable
return|;
block|}
block|}
end_class

end_unit

