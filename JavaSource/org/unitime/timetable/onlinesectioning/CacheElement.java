begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.3 - 3.5 (University Timetabling Application)  * Copyright (C) 2011 - 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|onlinesectioning
package|;
end_package

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|ifs
operator|.
name|util
operator|.
name|JProf
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|CacheElement
parameter_list|<
name|T
parameter_list|>
block|{
specifier|private
name|T
name|iElement
decl_stmt|;
specifier|private
name|long
name|iCreated
decl_stmt|;
specifier|public
name|CacheElement
parameter_list|(
name|T
name|element
parameter_list|)
block|{
name|iElement
operator|=
name|element
expr_stmt|;
name|iCreated
operator|=
name|JProf
operator|.
name|currentTimeMillis
argument_list|()
expr_stmt|;
block|}
specifier|public
name|T
name|element
parameter_list|()
block|{
return|return
name|iElement
return|;
block|}
specifier|public
name|long
name|created
parameter_list|()
block|{
return|return
name|iCreated
return|;
block|}
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
if|if
condition|(
name|o
operator|==
literal|null
condition|)
return|return
literal|false
return|;
if|if
condition|(
name|o
operator|instanceof
name|CacheElement
operator|&&
operator|(
operator|(
name|CacheElement
argument_list|<
name|T
argument_list|>
operator|)
name|o
operator|)
operator|.
name|element
argument_list|()
operator|.
name|equals
argument_list|(
name|element
argument_list|()
argument_list|)
condition|)
return|return
literal|true
return|;
return|return
name|o
operator|.
name|equals
argument_list|(
name|element
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|element
argument_list|()
operator|.
name|hashCode
argument_list|()
return|;
block|}
block|}
end_class

end_unit

