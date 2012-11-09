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
name|awt
operator|.
name|Color
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|Serializable
import|;
end_import

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
name|Iterator
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
name|TreeSet
import|;
end_import

begin_import
import|import
name|org
operator|.
name|hibernate
operator|.
name|FlushMode
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
name|BaseDepartment
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
name|BaseRoomDept
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
name|DepartmentDAO
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
name|Qualifiable
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
name|UserContext
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
name|UserQualifier
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
name|Department
extends|extends
name|BaseDepartment
implements|implements
name|Comparable
implements|,
name|Qualifiable
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
name|Department
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|Department
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
comment|/*[CONSTRUCTOR MARKER END]*/
comment|/** Request attribute name for available departments **/
specifier|public
specifier|static
name|String
name|DEPT_ATTR_NAME
init|=
literal|"deptsList"
decl_stmt|;
specifier|public
specifier|static
name|String
name|EXTERNAL_DEPT_ATTR_NAME
init|=
literal|"externalDepartments"
decl_stmt|;
specifier|public
specifier|static
name|TreeSet
name|findAll
parameter_list|(
name|Long
name|sessionId
parameter_list|)
block|{
return|return
operator|new
name|TreeSet
argument_list|(
operator|(
operator|new
name|DepartmentDAO
argument_list|()
operator|)
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select distinct d from Department as d where d.session.uniqueId=:sessionId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
operator|.
name|longValue
argument_list|()
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|TreeSet
argument_list|<
name|Department
argument_list|>
name|findAllExternal
parameter_list|(
name|Long
name|sessionId
parameter_list|)
block|{
return|return
operator|new
name|TreeSet
argument_list|<
name|Department
argument_list|>
argument_list|(
operator|(
operator|new
name|DepartmentDAO
argument_list|()
operator|)
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select distinct d from Department as d where d.externalManager=1 and d.session.uniqueId=:sessionId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
operator|.
name|longValue
argument_list|()
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|TreeSet
name|findAllNonExternal
parameter_list|(
name|Long
name|sessionId
parameter_list|)
block|{
return|return
operator|new
name|TreeSet
argument_list|(
operator|(
operator|new
name|DepartmentDAO
argument_list|()
operator|)
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select distinct d from Department as d where d.externalManager=0 and d.session.uniqueId=:sessionId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
operator|.
name|longValue
argument_list|()
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
argument_list|)
return|;
block|}
comment|/**      *       * @param deptCode      * @param sessionId      * @return      * @throws Exception      */
specifier|public
specifier|static
name|Department
name|findByDeptCode
parameter_list|(
name|String
name|deptCode
parameter_list|,
name|Long
name|sessionId
parameter_list|)
block|{
return|return
operator|(
name|findByDeptCode
argument_list|(
name|deptCode
argument_list|,
name|sessionId
argument_list|,
operator|(
operator|new
name|DepartmentDAO
argument_list|()
operator|)
operator|.
name|getSession
argument_list|()
argument_list|)
operator|)
return|;
block|}
comment|/**      *       * @param deptCode      * @param sessionId      * @param hibSession      * @return      * @throws Exception      */
specifier|public
specifier|static
name|Department
name|findByDeptCode
parameter_list|(
name|String
name|deptCode
parameter_list|,
name|Long
name|sessionId
parameter_list|,
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|)
block|{
return|return
operator|(
name|Department
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"select distinct d from Department as d where d.deptCode=:deptCode and d.session.uniqueId=:sessionId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
argument_list|)
operator|.
name|setString
argument_list|(
literal|"deptCode"
argument_list|,
name|deptCode
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|setFlushMode
argument_list|(
name|FlushMode
operator|.
name|MANUAL
argument_list|)
operator|.
name|uniqueResult
argument_list|()
return|;
block|}
comment|/* (non-Javadoc) 	 * @see java.lang.Comparable#compareTo(java.lang.Object) 	 */
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
name|Department
operator|)
condition|)
return|return
operator|-
literal|1
return|;
name|Department
name|d
init|=
operator|(
name|Department
operator|)
name|o
decl_stmt|;
name|int
name|cmp
init|=
name|Double
operator|.
name|compare
argument_list|(
name|isExternalManager
argument_list|()
operator|==
literal|null
condition|?
literal|0
else|:
name|isExternalManager
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|?
literal|1
else|:
literal|0
argument_list|,
name|d
operator|.
name|isExternalManager
argument_list|()
operator|==
literal|null
condition|?
literal|0
else|:
name|d
operator|.
name|isExternalManager
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|?
literal|1
else|:
literal|0
argument_list|)
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
if|if
condition|(
name|getDistributionPrefPriority
argument_list|()
operator|!=
literal|null
operator|&&
name|d
operator|.
name|getDistributionPrefPriority
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|cmp
operator|=
name|getDistributionPrefPriority
argument_list|()
operator|.
name|compareTo
argument_list|(
name|d
operator|.
name|getDistributionPrefPriority
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
block|}
if|if
condition|(
name|getDeptCode
argument_list|()
operator|!=
literal|null
operator|&&
operator|!
name|getDeptCode
argument_list|()
operator|.
name|equals
argument_list|(
name|d
operator|.
name|getDeptCode
argument_list|()
argument_list|)
condition|)
return|return
name|getDeptCode
argument_list|()
operator|.
name|compareTo
argument_list|(
name|d
operator|.
name|getDeptCode
argument_list|()
argument_list|)
return|;
return|return
name|getUniqueId
argument_list|()
operator|.
name|compareTo
argument_list|(
name|d
operator|.
name|getUniqueId
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|String
name|htmlLabel
parameter_list|()
block|{
return|return
operator|(
name|this
operator|.
name|getDeptCode
argument_list|()
operator|+
literal|" - "
operator|+
name|this
operator|.
name|getName
argument_list|()
operator|)
return|;
block|}
specifier|public
name|String
name|getHtmlTitle
parameter_list|()
block|{
return|return
name|getDeptCode
argument_list|()
operator|+
literal|" - "
operator|+
name|getName
argument_list|()
operator|+
operator|(
name|isExternalManager
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|?
literal|" ("
operator|+
name|getExternalMgrLabel
argument_list|()
operator|+
literal|")"
else|:
literal|""
operator|)
return|;
block|}
specifier|public
name|String
name|getShortLabel
parameter_list|()
block|{
if|if
condition|(
name|isExternalManager
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|)
return|return
name|getExternalMgrAbbv
argument_list|()
operator|.
name|trim
argument_list|()
return|;
if|if
condition|(
name|getAbbreviation
argument_list|()
operator|!=
literal|null
operator|&&
name|getAbbreviation
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
name|getAbbreviation
argument_list|()
operator|.
name|trim
argument_list|()
return|;
return|return
name|getDeptCode
argument_list|()
return|;
block|}
specifier|public
name|String
name|htmlShortLabel
parameter_list|()
block|{
return|return
literal|"<span "
operator|+
literal|"style='color:#"
operator|+
name|getRoomSharingColor
argument_list|(
literal|null
argument_list|)
operator|+
literal|";font-weight:bold;' "
operator|+
literal|"title='"
operator|+
name|getHtmlTitle
argument_list|()
operator|+
literal|"'>"
operator|+
name|getShortLabel
argument_list|()
operator|+
literal|"</span>"
return|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
operator|(
name|this
operator|.
name|getDeptCode
argument_list|()
operator|+
literal|" - "
operator|+
name|this
operator|.
name|getName
argument_list|()
operator|)
return|;
block|}
specifier|public
name|String
name|getLabel
parameter_list|()
block|{
return|return
operator|(
name|this
operator|.
name|getDeptCode
argument_list|()
operator|+
literal|" - "
operator|+
name|this
operator|.
name|getName
argument_list|()
operator|)
operator|+
operator|(
operator|(
name|this
operator|.
name|isExternalManager
argument_list|()
operator|.
name|booleanValue
argument_list|()
operator|)
condition|?
literal|" ( EXT: "
operator|+
name|this
operator|.
name|getExternalMgrLabel
argument_list|()
operator|+
literal|" )"
else|:
literal|""
operator|)
return|;
block|}
specifier|public
specifier|static
name|String
name|color2hex
parameter_list|(
name|Color
name|color
parameter_list|)
block|{
return|return
operator|(
name|color
operator|.
name|getRed
argument_list|()
operator|<
literal|16
condition|?
literal|"0"
else|:
literal|""
operator|)
operator|+
name|Integer
operator|.
name|toHexString
argument_list|(
name|color
operator|.
name|getRed
argument_list|()
argument_list|)
operator|+
operator|(
name|color
operator|.
name|getGreen
argument_list|()
operator|<
literal|16
condition|?
literal|"0"
else|:
literal|""
operator|)
operator|+
name|Integer
operator|.
name|toHexString
argument_list|(
name|color
operator|.
name|getGreen
argument_list|()
argument_list|)
operator|+
operator|(
name|color
operator|.
name|getBlue
argument_list|()
operator|<
literal|16
condition|?
literal|"0"
else|:
literal|""
operator|)
operator|+
name|Integer
operator|.
name|toHexString
argument_list|(
name|color
operator|.
name|getBlue
argument_list|()
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|Color
name|hex2color
parameter_list|(
name|String
name|hex
parameter_list|)
block|{
if|if
condition|(
name|hex
operator|==
literal|null
operator|||
name|hex
operator|.
name|length
argument_list|()
operator|!=
literal|6
condition|)
return|return
literal|null
return|;
return|return
operator|new
name|Color
argument_list|(
name|Integer
operator|.
name|parseInt
argument_list|(
name|hex
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|2
argument_list|)
argument_list|,
literal|16
argument_list|)
argument_list|,
name|Integer
operator|.
name|parseInt
argument_list|(
name|hex
operator|.
name|substring
argument_list|(
literal|2
argument_list|,
literal|4
argument_list|)
argument_list|,
literal|16
argument_list|)
argument_list|,
name|Integer
operator|.
name|parseInt
argument_list|(
name|hex
operator|.
name|substring
argument_list|(
literal|4
argument_list|,
literal|6
argument_list|)
argument_list|,
literal|16
argument_list|)
argument_list|)
return|;
block|}
specifier|private
specifier|static
name|int
name|distance
parameter_list|(
name|String
name|color1
parameter_list|,
name|String
name|color2
parameter_list|)
block|{
if|if
condition|(
name|color1
operator|.
name|equals
argument_list|(
name|color2
argument_list|)
condition|)
return|return
literal|0
return|;
name|Color
name|c1
init|=
name|hex2color
argument_list|(
name|color1
argument_list|)
decl_stmt|;
name|Color
name|c2
init|=
name|hex2color
argument_list|(
name|color2
argument_list|)
decl_stmt|;
return|return
operator|(
name|int
operator|)
name|Math
operator|.
name|sqrt
argument_list|(
operator|(
operator|(
name|c1
operator|.
name|getRed
argument_list|()
operator|-
name|c2
operator|.
name|getRed
argument_list|()
operator|)
operator|*
operator|(
name|c1
operator|.
name|getRed
argument_list|()
operator|-
name|c2
operator|.
name|getRed
argument_list|()
operator|)
operator|)
operator|+
operator|(
operator|(
name|c1
operator|.
name|getGreen
argument_list|()
operator|-
name|c2
operator|.
name|getGreen
argument_list|()
operator|)
operator|*
operator|(
name|c1
operator|.
name|getGreen
argument_list|()
operator|-
name|c2
operator|.
name|getGreen
argument_list|()
operator|)
operator|)
operator|+
operator|(
operator|(
name|c1
operator|.
name|getBlue
argument_list|()
operator|-
name|c2
operator|.
name|getBlue
argument_list|()
operator|)
operator|*
operator|(
name|c1
operator|.
name|getBlue
argument_list|()
operator|-
name|c2
operator|.
name|getBlue
argument_list|()
operator|)
operator|)
argument_list|)
return|;
block|}
specifier|public
name|boolean
name|isRoomSharingColorConflicting
parameter_list|(
name|String
name|color
parameter_list|)
block|{
for|for
control|(
name|Iterator
name|i
init|=
name|getRoomDepts
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|RoomDept
name|rd
init|=
operator|(
name|RoomDept
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|j
init|=
name|rd
operator|.
name|getRoom
argument_list|()
operator|.
name|getRoomDepts
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|j
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|BaseDepartment
name|d
init|=
operator|(
name|BaseDepartment
operator|)
operator|(
operator|(
name|RoomDept
operator|)
name|j
operator|.
name|next
argument_list|()
operator|)
operator|.
name|getDepartment
argument_list|()
decl_stmt|;
if|if
condition|(
name|d
operator|.
name|equals
argument_list|(
name|this
argument_list|)
condition|)
continue|continue;
if|if
condition|(
name|d
operator|.
name|getRoomSharingColor
argument_list|()
operator|==
literal|null
condition|)
continue|continue;
if|if
condition|(
name|distance
argument_list|(
name|color
argument_list|,
name|d
operator|.
name|getRoomSharingColor
argument_list|()
argument_list|)
operator|<
literal|50
condition|)
return|return
literal|true
return|;
block|}
block|}
return|return
literal|false
return|;
block|}
specifier|public
name|boolean
name|isRoomSharingColorConflicting
parameter_list|(
name|String
name|color
parameter_list|,
name|Collection
name|otherDepartments
parameter_list|)
block|{
if|if
condition|(
name|isRoomSharingColorConflicting
argument_list|(
name|color
argument_list|)
condition|)
return|return
literal|true
return|;
if|if
condition|(
name|otherDepartments
operator|!=
literal|null
operator|&&
operator|!
name|otherDepartments
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
for|for
control|(
name|Iterator
name|i
init|=
name|otherDepartments
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Object
name|o
init|=
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|BaseDepartment
name|d
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|o
operator|instanceof
name|BaseDepartment
condition|)
block|{
name|d
operator|=
operator|(
name|BaseDepartment
operator|)
name|o
expr_stmt|;
block|}
if|else if
condition|(
name|o
operator|instanceof
name|BaseRoomDept
condition|)
block|{
name|d
operator|=
operator|(
operator|(
name|BaseRoomDept
operator|)
name|o
operator|)
operator|.
name|getDepartment
argument_list|()
expr_stmt|;
block|}
if|else if
condition|(
name|o
operator|instanceof
name|Long
condition|)
block|{
name|d
operator|=
operator|(
operator|new
name|DepartmentDAO
argument_list|()
operator|)
operator|.
name|get
argument_list|(
operator|(
name|Long
operator|)
name|o
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|d
operator|==
literal|null
condition|)
continue|continue;
if|if
condition|(
name|d
operator|.
name|equals
argument_list|(
name|this
argument_list|)
condition|)
continue|continue;
if|if
condition|(
name|color
operator|.
name|equals
argument_list|(
name|d
operator|.
name|getRoomSharingColor
argument_list|()
argument_list|)
condition|)
return|return
literal|true
return|;
block|}
block|}
return|return
literal|false
return|;
block|}
specifier|public
name|void
name|fixRoomSharingColor
parameter_list|(
name|Collection
name|otherDepartments
parameter_list|)
block|{
name|String
name|color
init|=
name|getRoomSharingColor
argument_list|()
decl_stmt|;
if|if
condition|(
name|isRoomSharingColorConflicting
argument_list|(
name|color
argument_list|,
name|otherDepartments
argument_list|)
condition|)
block|{
name|int
name|idx
init|=
literal|0
decl_stmt|;
name|color
operator|=
name|color2hex
argument_list|(
name|RoomSharingModel
operator|.
name|sDepartmentColors
index|[
name|idx
index|]
argument_list|)
expr_stmt|;
while|while
condition|(
name|isRoomSharingColorConflicting
argument_list|(
name|color
argument_list|,
name|otherDepartments
argument_list|)
condition|)
block|{
name|idx
operator|++
expr_stmt|;
if|if
condition|(
name|idx
operator|>=
name|RoomSharingModel
operator|.
name|sDepartmentColors
operator|.
name|length
condition|)
block|{
name|color
operator|=
name|color2hex
argument_list|(
operator|new
name|Color
argument_list|(
operator|(
name|int
operator|)
operator|(
literal|256.0
operator|*
name|Math
operator|.
name|random
argument_list|()
operator|)
argument_list|,
operator|(
name|int
operator|)
operator|(
literal|256.0
operator|*
name|Math
operator|.
name|random
argument_list|()
operator|)
argument_list|,
operator|(
name|int
operator|)
operator|(
literal|256.0
operator|*
name|Math
operator|.
name|random
argument_list|()
operator|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|color
operator|=
name|color2hex
argument_list|(
name|RoomSharingModel
operator|.
name|sDepartmentColors
index|[
name|idx
index|]
argument_list|)
expr_stmt|;
block|}
block|}
name|setRoomSharingColor
argument_list|(
name|color
argument_list|)
expr_stmt|;
operator|(
operator|new
name|DepartmentDAO
argument_list|()
operator|)
operator|.
name|saveOrUpdate
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|String
name|getRoomSharingColor
parameter_list|(
name|Collection
name|otherDepartments
parameter_list|)
block|{
if|if
condition|(
name|getRoomSharingColor
argument_list|()
operator|==
literal|null
condition|)
block|{
name|setRoomSharingColor
argument_list|(
name|color2hex
argument_list|(
name|RoomSharingModel
operator|.
name|sDepartmentColors
index|[
literal|0
index|]
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|fixRoomSharingColor
argument_list|(
name|otherDepartments
argument_list|)
expr_stmt|;
return|return
name|getRoomSharingColor
argument_list|()
return|;
block|}
specifier|public
name|String
name|getManagingDeptLabel
parameter_list|()
block|{
if|if
condition|(
name|isExternalManager
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|)
block|{
return|return
operator|(
name|getExternalMgrLabel
argument_list|()
operator|)
return|;
block|}
else|else
block|{
return|return
operator|(
name|getDeptCode
argument_list|()
operator|+
literal|" - "
operator|+
name|getName
argument_list|()
operator|)
return|;
block|}
block|}
specifier|public
name|String
name|getManagingDeptAbbv
parameter_list|()
block|{
return|return
literal|"<span title='"
operator|+
name|getHtmlTitle
argument_list|()
operator|+
literal|"'>"
operator|+
name|getShortLabel
argument_list|()
operator|+
literal|"</span>"
return|;
block|}
specifier|public
name|Collection
name|getClasses
parameter_list|()
block|{
return|return
operator|(
operator|new
name|DepartmentDAO
argument_list|()
operator|)
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select distinct c from Class_ as c where c.managingDept=:departmentId or (c.managingDept is null and c.controllingDept=:departmentId)"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"departmentId"
argument_list|,
name|getUniqueId
argument_list|()
operator|.
name|longValue
argument_list|()
argument_list|)
operator|.
name|list
argument_list|()
return|;
block|}
specifier|public
name|Collection
name|getClassesFetchWithStructure
parameter_list|()
block|{
return|return
operator|(
operator|new
name|DepartmentDAO
argument_list|()
operator|)
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select distinct c from Class_ as c "
operator|+
literal|"left join fetch c.childClasses as cc "
operator|+
literal|"left join fetch c.schedulingSubpart as ss "
operator|+
literal|"left join fetch ss.childSubparts as css "
operator|+
literal|"left join fetch ss.instrOfferingConfig as ioc "
operator|+
literal|"left join fetch ioc.instructionalOffering as io "
operator|+
literal|"left join fetch io.courseOfferings as cox "
operator|+
literal|"where c.managingDept=:departmentId or (c.managingDept is null and c.controllingDept=:departmentId)"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"departmentId"
argument_list|,
name|getUniqueId
argument_list|()
operator|.
name|longValue
argument_list|()
argument_list|)
operator|.
name|list
argument_list|()
return|;
block|}
specifier|public
name|Collection
name|getNotAssignedClasses
parameter_list|(
name|Solution
name|solution
parameter_list|)
block|{
return|return
operator|(
operator|new
name|DepartmentDAO
argument_list|()
operator|)
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select distinct c from Class_ as c where (c.managingDept=:departmentId or (c.managingDept is null and c.controllingDept=:departmentId)) and "
operator|+
literal|"not exists (from c.assignments as a where a.solution=:solutionId)"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"departmentId"
argument_list|,
name|getUniqueId
argument_list|()
operator|.
name|longValue
argument_list|()
argument_list|)
operator|.
name|setInteger
argument_list|(
literal|"solutionId"
argument_list|,
name|solution
operator|.
name|getUniqueId
argument_list|()
operator|.
name|intValue
argument_list|()
argument_list|)
operator|.
name|list
argument_list|()
return|;
block|}
specifier|public
specifier|static
name|TreeSet
argument_list|<
name|Department
argument_list|>
name|findAllBeingUsed
parameter_list|(
name|Long
name|sessionId
parameter_list|)
block|{
name|TreeSet
name|ret
init|=
operator|new
name|TreeSet
argument_list|(
operator|(
operator|new
name|DepartmentDAO
argument_list|()
operator|)
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select distinct d from Department as d inner join d.timetableManagers as m where d.session.uniqueId=:sessionId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
operator|.
name|longValue
argument_list|()
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
argument_list|)
decl_stmt|;
name|ret
operator|.
name|addAll
argument_list|(
operator|(
operator|new
name|DepartmentDAO
argument_list|()
operator|)
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select distinct d from Department as d inner join d.roomDepts as r where d.session.uniqueId=:sessionId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
operator|.
name|longValue
argument_list|()
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
argument_list|)
expr_stmt|;
name|ret
operator|.
name|addAll
argument_list|(
operator|(
operator|new
name|DepartmentDAO
argument_list|()
operator|)
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select distinct d from Department as d inner join d.subjectAreas as r where d.session.uniqueId=:sessionId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
operator|.
name|longValue
argument_list|()
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|ret
return|;
block|}
specifier|public
name|DepartmentStatusType
name|effectiveStatusType
parameter_list|()
block|{
name|DepartmentStatusType
name|t
init|=
name|getStatusType
argument_list|()
decl_stmt|;
if|if
condition|(
name|t
operator|!=
literal|null
condition|)
return|return
name|t
return|;
return|return
name|getSession
argument_list|()
operator|.
name|getStatusType
argument_list|()
return|;
block|}
specifier|public
name|Long
name|getSessionId
parameter_list|()
block|{
if|if
condition|(
name|getSession
argument_list|()
operator|!=
literal|null
condition|)
block|{
return|return
operator|(
name|getSession
argument_list|()
operator|.
name|getUniqueId
argument_list|()
operator|)
return|;
block|}
else|else
block|{
return|return
operator|(
literal|null
operator|)
return|;
block|}
block|}
specifier|public
name|Object
name|clone
parameter_list|()
block|{
name|Department
name|d
init|=
operator|new
name|Department
argument_list|()
decl_stmt|;
name|d
operator|.
name|setSession
argument_list|(
name|getSession
argument_list|()
argument_list|)
expr_stmt|;
name|d
operator|.
name|setAbbreviation
argument_list|(
name|getAbbreviation
argument_list|()
argument_list|)
expr_stmt|;
name|d
operator|.
name|setAllowReqRoom
argument_list|(
name|isAllowReqRoom
argument_list|()
argument_list|)
expr_stmt|;
name|d
operator|.
name|setAllowReqTime
argument_list|(
name|isAllowReqTime
argument_list|()
argument_list|)
expr_stmt|;
name|d
operator|.
name|setAllowReqDistribution
argument_list|(
name|isAllowReqDistribution
argument_list|()
argument_list|)
expr_stmt|;
name|d
operator|.
name|setDeptCode
argument_list|(
name|getDeptCode
argument_list|()
argument_list|)
expr_stmt|;
name|d
operator|.
name|setDistributionPrefPriority
argument_list|(
name|getDistributionPrefPriority
argument_list|()
argument_list|)
expr_stmt|;
name|d
operator|.
name|setExternalManager
argument_list|(
name|isExternalManager
argument_list|()
argument_list|)
expr_stmt|;
name|d
operator|.
name|setExternalMgrAbbv
argument_list|(
name|getExternalMgrAbbv
argument_list|()
argument_list|)
expr_stmt|;
name|d
operator|.
name|setExternalMgrLabel
argument_list|(
name|getExternalMgrLabel
argument_list|()
argument_list|)
expr_stmt|;
name|d
operator|.
name|setExternalUniqueId
argument_list|(
name|getExternalUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|d
operator|.
name|setName
argument_list|(
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|d
operator|.
name|setStatusType
argument_list|(
name|getStatusType
argument_list|()
argument_list|)
expr_stmt|;
name|d
operator|.
name|setAllowEvents
argument_list|(
name|isAllowEvents
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|d
return|;
block|}
specifier|public
name|Department
name|findSameDepartmentInSession
parameter_list|(
name|Long
name|newSessionId
parameter_list|)
block|{
return|return
operator|(
name|findSameDepartmentInSession
argument_list|(
name|newSessionId
argument_list|,
operator|(
operator|new
name|DepartmentDAO
argument_list|()
operator|)
operator|.
name|getSession
argument_list|()
argument_list|)
operator|)
return|;
block|}
specifier|public
name|Department
name|findSameDepartmentInSession
parameter_list|(
name|Long
name|newSessionId
parameter_list|,
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|)
block|{
if|if
condition|(
name|newSessionId
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
name|newDept
init|=
name|Department
operator|.
name|findByDeptCode
argument_list|(
name|this
operator|.
name|getDeptCode
argument_list|()
argument_list|,
name|newSessionId
argument_list|,
name|hibSession
argument_list|)
decl_stmt|;
if|if
condition|(
name|newDept
operator|==
literal|null
operator|&&
name|this
operator|.
name|getExternalUniqueId
argument_list|()
operator|!=
literal|null
condition|)
block|{
comment|// if a department wasn't found and an external uniqueid exists for this
comment|//   department check to see if the new term has a department that matches
comment|//   the external unique id
name|List
name|l
init|=
name|hibSession
operator|.
name|createCriteria
argument_list|(
name|Department
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
literal|"externalUniqueId"
argument_list|,
name|this
operator|.
name|getExternalUniqueId
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
literal|"session.uniqueId"
argument_list|,
name|newSessionId
argument_list|)
argument_list|)
operator|.
name|setFlushMode
argument_list|(
name|FlushMode
operator|.
name|MANUAL
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
name|newDept
operator|=
operator|(
name|Department
operator|)
name|l
operator|.
name|get
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
block|}
return|return
operator|(
name|newDept
operator|)
return|;
block|}
specifier|public
name|Department
name|findSameDepartmentInSession
parameter_list|(
name|Session
name|newSession
parameter_list|)
block|{
if|if
condition|(
name|newSession
operator|!=
literal|null
condition|)
return|return
operator|(
name|findSameDepartmentInSession
argument_list|(
name|newSession
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|)
return|;
else|else
return|return
operator|(
literal|null
operator|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Serializable
name|getQualifierId
parameter_list|()
block|{
return|return
name|getUniqueId
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getQualifierType
parameter_list|()
block|{
return|return
name|getClass
argument_list|()
operator|.
name|getSimpleName
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getQualifierReference
parameter_list|()
block|{
return|return
name|getDeptCode
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getQualifierLabel
parameter_list|()
block|{
return|return
name|getName
argument_list|()
return|;
block|}
specifier|public
specifier|static
name|TreeSet
argument_list|<
name|Department
argument_list|>
name|getUserDepartments
parameter_list|(
name|UserContext
name|user
parameter_list|)
block|{
name|TreeSet
argument_list|<
name|Department
argument_list|>
name|departments
init|=
operator|new
name|TreeSet
argument_list|<
name|Department
argument_list|>
argument_list|()
decl_stmt|;
if|if
condition|(
name|user
operator|==
literal|null
operator|||
name|user
operator|.
name|getCurrentAuthority
argument_list|()
operator|==
literal|null
condition|)
return|return
name|departments
return|;
if|if
condition|(
name|user
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|hasRight
argument_list|(
name|Right
operator|.
name|DepartmentIndependent
argument_list|)
condition|)
name|departments
operator|.
name|addAll
argument_list|(
name|Department
operator|.
name|findAllBeingUsed
argument_list|(
name|user
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
else|else
for|for
control|(
name|UserQualifier
name|q
range|:
name|user
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|getQualifiers
argument_list|(
literal|"Department"
argument_list|)
control|)
name|departments
operator|.
name|add
argument_list|(
name|DepartmentDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
operator|(
name|Long
operator|)
name|q
operator|.
name|getQualifierId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|departments
return|;
block|}
annotation|@
name|Override
specifier|public
name|Department
name|getDepartment
parameter_list|()
block|{
return|return
name|this
return|;
block|}
block|}
end_class

end_unit

