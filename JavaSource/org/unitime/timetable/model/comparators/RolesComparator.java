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
name|comparators
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Comparator
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
name|model
operator|.
name|ManagerRole
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
name|model
operator|.
name|Roles
import|;
end_import

begin_comment
comment|/**  * Compares ManagerRole or Roles objects and orders by role reference  *   * @author Heston Fernandes  */
end_comment

begin_class
specifier|public
class|class
name|RolesComparator
implements|implements
name|Comparator
block|{
specifier|public
name|int
name|compare
parameter_list|(
name|Object
name|o1
parameter_list|,
name|Object
name|o2
parameter_list|)
block|{
if|if
condition|(
name|o1
operator|==
literal|null
operator|||
name|o2
operator|==
literal|null
condition|)
return|return
literal|0
return|;
comment|// Check if ManagerRole object
if|if
condition|(
operator|(
name|o1
operator|instanceof
name|ManagerRole
operator|)
operator|&&
operator|(
name|o2
operator|instanceof
name|ManagerRole
operator|)
condition|)
block|{
name|ManagerRole
name|r1
init|=
operator|(
name|ManagerRole
operator|)
name|o1
decl_stmt|;
name|ManagerRole
name|r2
init|=
operator|(
name|ManagerRole
operator|)
name|o2
decl_stmt|;
return|return
operator|(
name|r1
operator|.
name|getRole
argument_list|()
operator|.
name|getReference
argument_list|()
operator|.
name|compareTo
argument_list|(
name|r2
operator|.
name|getRole
argument_list|()
operator|.
name|getReference
argument_list|()
argument_list|)
operator|)
return|;
block|}
comment|// Check if Roles object
if|if
condition|(
operator|(
name|o1
operator|instanceof
name|Roles
operator|)
operator|&&
operator|(
name|o2
operator|instanceof
name|Roles
operator|)
condition|)
block|{
name|Roles
name|r1
init|=
operator|(
name|Roles
operator|)
name|o1
decl_stmt|;
name|Roles
name|r2
init|=
operator|(
name|Roles
operator|)
name|o2
decl_stmt|;
return|return
operator|(
name|r1
operator|.
name|getReference
argument_list|()
operator|.
name|compareTo
argument_list|(
name|r2
operator|.
name|getReference
argument_list|()
argument_list|)
operator|)
return|;
block|}
comment|// All other cases
return|return
literal|0
return|;
block|}
block|}
end_class

end_unit

