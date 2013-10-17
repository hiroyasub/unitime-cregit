begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 - 3.5 (University Timetabling Application)  * Copyright (C) 2008 - 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|dao
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|Serializable
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|_RootDAO
parameter_list|<
name|T
parameter_list|,
name|K
extends|extends
name|Serializable
parameter_list|>
extends|extends
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|base
operator|.
name|_BaseRootDAO
argument_list|<
name|T
argument_list|,
name|K
argument_list|>
block|{
specifier|public
name|Class
argument_list|<
name|T
argument_list|>
name|getReferenceClass
parameter_list|()
block|{
return|return
literal|null
return|;
block|}
block|}
end_class

end_unit

