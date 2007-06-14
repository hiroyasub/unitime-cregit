begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.0 (University Course Timetabling& Student Sectioning Application)  * Copyright (C) 2007, UniTime.org, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program; if not, write to the Free Software Foundation, Inc.,  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|Collection
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
name|StringTokenizer
import|;
end_import

begin_import
import|import
name|org
operator|.
name|hibernate
operator|.
name|HibernateException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|hibernate
operator|.
name|Query
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
name|hibernate
operator|.
name|criterion
operator|.
name|Restrictions
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
name|BaseRoomGroup
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
name|RoomGroupDAO
import|;
end_import

begin_class
specifier|public
class|class
name|RoomGroup
extends|extends
name|BaseRoomGroup
implements|implements
name|Comparable
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
comment|/*[CONSTRUCTOR MARKER BEGIN]*/
specifier|public
name|RoomGroup
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|RoomGroup
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Long
name|uniqueId
parameter_list|)
block|{
name|super
argument_list|(
name|uniqueId
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * Constructor for required fields 	 */
specifier|public
name|RoomGroup
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Long
name|uniqueId
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|String
name|name
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|Boolean
name|global
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|Boolean
name|defaultGroup
parameter_list|)
block|{
name|super
argument_list|(
name|uniqueId
argument_list|,
name|name
argument_list|,
name|global
argument_list|,
name|defaultGroup
argument_list|)
expr_stmt|;
block|}
comment|/*[CONSTRUCTOR MARKER END]*/
comment|/** Request attribute name for available room groups **/
specifier|public
specifier|static
name|String
name|GROUP_LIST_ATTR_NAME
init|=
literal|"roomGroupsList"
decl_stmt|;
specifier|public
specifier|static
name|Collection
name|getAllRoomGroups
parameter_list|()
throws|throws
name|HibernateException
block|{
return|return
operator|(
operator|new
name|RoomGroupDAO
argument_list|()
operator|)
operator|.
name|findAll
argument_list|(
name|Order
operator|.
name|asc
argument_list|(
literal|"name"
argument_list|)
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|Collection
name|getAllGlobalRoomGroups
parameter_list|()
throws|throws
name|HibernateException
block|{
return|return
operator|(
operator|new
name|RoomGroupDAO
argument_list|()
operator|)
operator|.
name|getSession
argument_list|()
operator|.
name|createCriteria
argument_list|(
name|RoomGroup
operator|.
name|class
argument_list|)
operator|.
name|add
argument_list|(
name|Restrictions
operator|.
name|eq
argument_list|(
literal|"global"
argument_list|,
name|Boolean
operator|.
name|TRUE
argument_list|)
argument_list|)
operator|.
name|addOrder
argument_list|(
name|Order
operator|.
name|asc
argument_list|(
literal|"name"
argument_list|)
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
return|;
block|}
comment|/** 	 * @param session 	 * @return Collection of RoomGroups for a session 	 * @throws HibernateException 	 */
specifier|public
specifier|static
name|Collection
name|getAllRoomGroupsForSession
parameter_list|(
name|Session
name|session
parameter_list|)
throws|throws
name|HibernateException
block|{
name|RoomGroupDAO
name|rgDao
init|=
operator|new
name|RoomGroupDAO
argument_list|()
decl_stmt|;
name|String
name|query
init|=
literal|"from RoomGroup rg where rg.session.uniqueId = "
operator|+
name|session
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
decl_stmt|;
name|query
operator|+=
literal|" or rg.global = 1"
expr_stmt|;
return|return
operator|(
name|rgDao
operator|.
name|getQuery
argument_list|(
name|query
argument_list|)
operator|.
name|list
argument_list|()
operator|)
return|;
block|}
comment|/** 	 * Gets the default global room group. Only one exists hence only one 	 * record is returned. If more than one exists then it returns the first  	 * one in the list \	 * @return Room Group if found, null otherwise 	 */
specifier|public
specifier|static
name|RoomGroup
name|getGlobalDefaultRoomGroup
parameter_list|()
block|{
name|String
name|sql
init|=
literal|"select rg "
operator|+
literal|"from RoomGroup as rg "
operator|+
literal|"where rg.global = true and rg.defaultGroup = true"
decl_stmt|;
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
operator|new
name|RoomGroupDAO
argument_list|()
operator|.
name|getSession
argument_list|()
decl_stmt|;
name|Query
name|query
init|=
name|hibSession
operator|.
name|createQuery
argument_list|(
name|sql
argument_list|)
decl_stmt|;
name|List
name|l
init|=
name|query
operator|.
name|list
argument_list|()
decl_stmt|;
if|if
condition|(
name|l
operator|!=
literal|null
operator|&&
name|l
operator|.
name|size
argument_list|()
operator|>
literal|0
condition|)
return|return
operator|(
name|RoomGroup
operator|)
name|l
operator|.
name|get
argument_list|(
literal|0
argument_list|)
return|;
else|else
return|return
literal|null
return|;
block|}
specifier|public
specifier|static
name|Collection
name|getAllDepartmentRoomGroups
parameter_list|(
name|Department
name|dept
parameter_list|)
throws|throws
name|HibernateException
block|{
if|if
condition|(
name|dept
operator|==
literal|null
condition|)
return|return
literal|null
return|;
return|return
operator|(
operator|new
name|RoomGroupDAO
argument_list|()
operator|)
operator|.
name|getSession
argument_list|()
operator|.
name|createCriteria
argument_list|(
name|RoomGroup
operator|.
name|class
argument_list|)
operator|.
name|add
argument_list|(
name|Restrictions
operator|.
name|eq
argument_list|(
literal|"global"
argument_list|,
name|Boolean
operator|.
name|FALSE
argument_list|)
argument_list|)
operator|.
name|add
argument_list|(
name|Restrictions
operator|.
name|eq
argument_list|(
literal|"department.uniqueId"
argument_list|,
name|dept
operator|.
name|getUniqueId
argument_list|()
argument_list|)
argument_list|)
operator|.
name|addOrder
argument_list|(
name|Order
operator|.
name|asc
argument_list|(
literal|"name"
argument_list|)
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
return|;
block|}
specifier|public
name|int
name|compareTo
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
operator|||
operator|!
operator|(
name|o
operator|instanceof
name|RoomGroup
operator|)
condition|)
return|return
operator|-
literal|1
return|;
name|RoomGroup
name|rg
init|=
operator|(
name|RoomGroup
operator|)
name|o
decl_stmt|;
name|int
name|cmp
init|=
operator|(
name|isGlobal
argument_list|()
operator|.
name|booleanValue
argument_list|()
operator|==
name|rg
operator|.
name|isGlobal
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|?
literal|0
else|:
operator|(
name|isGlobal
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|?
operator|-
literal|1
else|:
literal|1
operator|)
operator|)
decl_stmt|;
if|if
condition|(
name|cmp
operator|!=
literal|0
condition|)
return|return
name|cmp
return|;
name|cmp
operator|=
name|getName
argument_list|()
operator|.
name|compareTo
argument_list|(
name|rg
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|cmp
operator|!=
literal|0
condition|)
return|return
name|cmp
return|;
return|return
name|getUniqueId
argument_list|()
operator|.
name|compareTo
argument_list|(
name|rg
operator|.
name|getUniqueId
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|boolean
name|hasLocation
parameter_list|(
name|Location
name|location
parameter_list|)
block|{
return|return
name|getRooms
argument_list|()
operator|.
name|contains
argument_list|(
name|location
argument_list|)
return|;
block|}
specifier|public
name|String
name|htmlLabel
parameter_list|()
block|{
return|return
literal|"<span "
operator|+
operator|(
name|isGlobal
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|?
literal|""
else|:
literal|"style='color:#"
operator|+
name|getDepartment
argument_list|()
operator|.
name|getRoomSharingColor
argument_list|(
literal|null
argument_list|)
operator|+
literal|";font-weight:bold;' "
operator|)
operator|+
literal|"title='"
operator|+
name|getName
argument_list|()
operator|+
literal|" ("
operator|+
operator|(
name|isGlobal
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|?
literal|"global"
else|:
name|getDepartment
argument_list|()
operator|.
name|isExternalManager
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|?
name|getDepartment
argument_list|()
operator|.
name|getExternalMgrLabel
argument_list|()
else|:
name|getDepartment
argument_list|()
operator|.
name|getName
argument_list|()
operator|)
operator|+
literal|")'>"
operator|+
name|getName
argument_list|()
operator|+
literal|"</span>"
return|;
block|}
specifier|public
name|String
name|getNameWithTitle
parameter_list|()
block|{
return|return
name|getName
argument_list|()
operator|+
operator|(
name|isGlobal
argument_list|()
operator|!=
literal|null
operator|&&
name|isGlobal
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|?
literal|""
else|:
literal|" (Department)"
operator|)
return|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|getName
argument_list|()
return|;
block|}
specifier|public
name|Object
name|clone
parameter_list|()
block|{
name|RoomGroup
name|newRoomGroup
init|=
operator|new
name|RoomGroup
argument_list|()
decl_stmt|;
name|newRoomGroup
operator|.
name|setDefaultGroup
argument_list|(
name|isDefaultGroup
argument_list|()
argument_list|)
expr_stmt|;
name|newRoomGroup
operator|.
name|setDepartment
argument_list|(
name|getDepartment
argument_list|()
argument_list|)
expr_stmt|;
name|newRoomGroup
operator|.
name|setDescription
argument_list|(
name|getDescription
argument_list|()
argument_list|)
expr_stmt|;
name|newRoomGroup
operator|.
name|setGlobal
argument_list|(
name|isGlobal
argument_list|()
argument_list|)
expr_stmt|;
name|newRoomGroup
operator|.
name|setName
argument_list|(
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|newRoomGroup
operator|.
name|setSession
argument_list|(
name|getSession
argument_list|()
argument_list|)
expr_stmt|;
return|return
operator|(
name|newRoomGroup
operator|)
return|;
block|}
specifier|public
name|RoomGroup
name|findSameRoomGroupInSession
parameter_list|(
name|Session
name|session
parameter_list|)
block|{
if|if
condition|(
name|session
operator|==
literal|null
condition|)
block|{
return|return
operator|(
literal|null
operator|)
return|;
block|}
name|Department
name|d
init|=
name|getDepartment
argument_list|()
operator|.
name|findSameDepartmentInSession
argument_list|(
name|session
argument_list|)
decl_stmt|;
if|if
condition|(
name|d
operator|!=
literal|null
condition|)
block|{
name|List
name|l
init|=
operator|(
operator|new
name|RoomGroupDAO
argument_list|()
operator|)
operator|.
name|getSession
argument_list|()
operator|.
name|createCriteria
argument_list|(
name|RoomGroup
operator|.
name|class
argument_list|)
operator|.
name|add
argument_list|(
name|Restrictions
operator|.
name|eq
argument_list|(
literal|"global"
argument_list|,
name|Boolean
operator|.
name|FALSE
argument_list|)
argument_list|)
operator|.
name|add
argument_list|(
name|Restrictions
operator|.
name|eq
argument_list|(
literal|"department.uniqueId"
argument_list|,
name|d
operator|.
name|getUniqueId
argument_list|()
argument_list|)
argument_list|)
operator|.
name|add
argument_list|(
name|Restrictions
operator|.
name|eq
argument_list|(
literal|"name"
argument_list|,
name|getName
argument_list|()
argument_list|)
argument_list|)
operator|.
name|addOrder
argument_list|(
name|Order
operator|.
name|asc
argument_list|(
literal|"name"
argument_list|)
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
decl_stmt|;
if|if
condition|(
name|l
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
block|{
return|return
operator|(
operator|(
name|RoomGroup
operator|)
name|l
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|)
return|;
block|}
block|}
return|return
literal|null
return|;
block|}
specifier|public
name|String
name|getAbbv
parameter_list|()
block|{
if|if
condition|(
name|super
operator|.
name|getAbbv
argument_list|()
operator|!=
literal|null
operator|&&
name|super
operator|.
name|getAbbv
argument_list|()
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
return|return
name|super
operator|.
name|getAbbv
argument_list|()
return|;
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
for|for
control|(
name|StringTokenizer
name|stk
init|=
operator|new
name|StringTokenizer
argument_list|(
name|getName
argument_list|()
argument_list|,
literal|" "
argument_list|)
init|;
name|stk
operator|.
name|hasMoreTokens
argument_list|()
condition|;
control|)
block|{
name|String
name|word
init|=
name|stk
operator|.
name|nextToken
argument_list|()
decl_stmt|;
if|if
condition|(
literal|"and"
operator|.
name|equalsIgnoreCase
argument_list|(
name|word
argument_list|)
condition|)
name|sb
operator|.
name|append
argument_list|(
literal|"&amp;"
argument_list|)
expr_stmt|;
if|else if
condition|(
name|word
operator|.
name|replaceAll
argument_list|(
literal|"[a-zA-Z\\.]*"
argument_list|,
literal|""
argument_list|)
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|word
operator|.
name|length
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|i
operator|==
literal|0
condition|)
name|sb
operator|.
name|append
argument_list|(
name|word
operator|.
name|substring
argument_list|(
name|i
argument_list|,
name|i
operator|+
literal|1
argument_list|)
operator|.
name|toUpperCase
argument_list|()
argument_list|)
expr_stmt|;
if|else if
condition|(
operator|(
name|i
operator|==
literal|1
operator|&&
name|word
operator|.
name|length
argument_list|()
operator|>
literal|3
operator|)
operator|||
operator|(
name|word
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
operator|>=
literal|'A'
operator|&&
name|word
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
operator|<=
literal|'Z'
operator|)
condition|)
name|sb
operator|.
name|append
argument_list|(
name|word
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
else|else
name|sb
operator|.
name|append
argument_list|(
name|word
argument_list|)
expr_stmt|;
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
end_class

end_unit

