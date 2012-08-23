begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 (University Timetabling Application)  * Copyright (C) 2008 - 2010, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Enumeration
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Vector
import|;
end_import

begin_import
import|import
name|org
operator|.
name|hibernate
operator|.
name|criterion
operator|.
name|Order
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
name|base
operator|.
name|BaseRoles
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
name|dao
operator|.
name|RolesDAO
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
name|security
operator|.
name|rights
operator|.
name|HasRights
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
name|security
operator|.
name|rights
operator|.
name|Right
import|;
end_import

begin_class
specifier|public
class|class
name|Roles
extends|extends
name|BaseRoles
implements|implements
name|HasRights
block|{
comment|/** 	 * 	 */
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|3256722879445154100L
decl_stmt|;
comment|/*[CONSTRUCTOR MARKER BEGIN]*/
specifier|public
name|Roles
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|Roles
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Long
name|roleId
parameter_list|)
block|{
name|super
argument_list|(
name|roleId
argument_list|)
expr_stmt|;
block|}
comment|/*[CONSTRUCTOR MARKER END]*/
annotation|@
name|Deprecated
specifier|public
specifier|static
name|String
name|EVENT_MGR_ROLE
init|=
literal|"Event Mgr"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|ROLE_STUDENT
init|=
literal|"Student"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|ROLE_INSTRUCTOR
init|=
literal|"Instructor"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|ROLE_NONE
init|=
literal|"No Role"
decl_stmt|;
specifier|public
specifier|static
name|String
name|USER_ROLES_ATTR_NAME
init|=
literal|"userRoles"
decl_stmt|;
specifier|public
specifier|static
name|String
name|ROLES_ATTR_NAME
init|=
literal|"rolesList"
decl_stmt|;
comment|/** Roles List **/
specifier|private
specifier|static
name|Vector
name|rolesList
init|=
literal|null
decl_stmt|;
comment|/** 	 * Retrieves all roles in the database 	 * ordered by column label 	 * @param refresh true - refreshes the list from database 	 * @return Vector of Roles objects 	 */
specifier|public
specifier|static
specifier|synchronized
name|Vector
name|getRolesList
parameter_list|(
name|boolean
name|refresh
parameter_list|)
block|{
if|if
condition|(
name|rolesList
operator|!=
literal|null
operator|&&
operator|!
name|refresh
condition|)
return|return
name|rolesList
return|;
name|RolesDAO
name|rdao
init|=
operator|new
name|RolesDAO
argument_list|()
decl_stmt|;
name|List
name|l
init|=
name|rdao
operator|.
name|findAll
argument_list|(
name|Order
operator|.
name|asc
argument_list|(
literal|"abbv"
argument_list|)
argument_list|)
decl_stmt|;
name|rolesList
operator|=
operator|new
name|Vector
argument_list|(
name|l
argument_list|)
expr_stmt|;
return|return
name|rolesList
return|;
block|}
specifier|public
specifier|static
name|Roles
name|getRole
parameter_list|(
name|String
name|roleRef
parameter_list|)
block|{
for|for
control|(
name|Enumeration
name|e
init|=
name|getRolesList
argument_list|(
literal|false
argument_list|)
operator|.
name|elements
argument_list|()
init|;
name|e
operator|.
name|hasMoreElements
argument_list|()
condition|;
control|)
block|{
name|Roles
name|role
init|=
operator|(
name|Roles
operator|)
name|e
operator|.
name|nextElement
argument_list|()
decl_stmt|;
if|if
condition|(
name|roleRef
operator|.
name|equals
argument_list|(
name|role
operator|.
name|getReference
argument_list|()
argument_list|)
condition|)
return|return
name|role
return|;
block|}
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|hasRight
parameter_list|(
name|Right
name|right
parameter_list|)
block|{
return|return
name|getRights
argument_list|()
operator|.
name|contains
argument_list|(
name|right
operator|.
name|name
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|Long
name|getUniqueId
parameter_list|()
block|{
return|return
name|getRoleId
argument_list|()
return|;
block|}
specifier|public
name|boolean
name|isUsed
parameter_list|()
block|{
return|return
operator|(
operator|(
name|Number
operator|)
name|RolesDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"count m from ManagerRole m where m.role.roleId = :roleId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"roleId"
argument_list|,
name|getRoleId
argument_list|()
argument_list|)
operator|.
name|uniqueResult
argument_list|()
operator|)
operator|.
name|intValue
argument_list|()
operator|>
literal|0
return|;
block|}
block|}
end_class

end_unit

