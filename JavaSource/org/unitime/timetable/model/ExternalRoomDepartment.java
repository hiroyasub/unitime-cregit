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
name|Iterator
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Set
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
name|BaseExternalRoomDepartment
import|;
end_import

begin_class
specifier|public
class|class
name|ExternalRoomDepartment
extends|extends
name|BaseExternalRoomDepartment
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
specifier|public
specifier|static
name|String
name|ASSIGNMENT_TYPE_ASSIGNED
init|=
literal|"assigned"
decl_stmt|;
specifier|public
specifier|static
name|String
name|ASSIGNMENT_TYPE_SCHEDULING
init|=
literal|"scheduling"
decl_stmt|;
comment|/*[CONSTRUCTOR MARKER BEGIN]*/
specifier|public
name|ExternalRoomDepartment
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|ExternalRoomDepartment
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
comment|/** 	 * Check for assigned department 	 * @return true if assigned department 	 */
specifier|public
name|boolean
name|isAssigned
parameter_list|()
block|{
return|return
name|checkAssignmentType
argument_list|(
name|ASSIGNMENT_TYPE_ASSIGNED
argument_list|)
return|;
block|}
comment|/** 	 * Check for scheduling department 	 * @return true if scheduling department 	 */
specifier|public
name|boolean
name|isScheduling
parameter_list|()
block|{
return|return
name|checkAssignmentType
argument_list|(
name|ASSIGNMENT_TYPE_SCHEDULING
argument_list|)
return|;
block|}
comment|/** 	 * Check assignment type 	 * @param assignType 	 * @return true if match 	 */
specifier|private
name|boolean
name|checkAssignmentType
parameter_list|(
name|String
name|assignmentType
parameter_list|)
block|{
name|boolean
name|result
init|=
literal|false
decl_stmt|;
if|if
condition|(
name|this
operator|.
name|getAssignmentType
argument_list|()
operator|.
name|equals
argument_list|(
name|assignmentType
argument_list|)
condition|)
block|{
name|result
operator|=
literal|true
expr_stmt|;
block|}
return|return
name|result
return|;
block|}
specifier|public
specifier|static
name|boolean
name|isControllingExternalDept
parameter_list|(
name|ExternalRoomDepartment
name|externalRoomDept
parameter_list|,
name|Set
name|deptList
parameter_list|)
block|{
name|String
name|asgn
init|=
literal|"assigned"
decl_stmt|;
name|String
name|sched
init|=
literal|"scheduling"
decl_stmt|;
if|if
condition|(
name|externalRoomDept
operator|==
literal|null
operator|||
name|deptList
operator|==
literal|null
operator|||
name|deptList
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
operator|(
literal|false
operator|)
return|;
block|}
if|if
condition|(
name|deptList
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
block|{
if|if
condition|(
name|deptList
operator|.
name|contains
argument_list|(
name|externalRoomDept
argument_list|)
condition|)
block|{
return|return
operator|(
literal|true
operator|)
return|;
block|}
else|else
block|{
return|return
operator|(
literal|false
operator|)
return|;
block|}
block|}
else|else
block|{
name|boolean
name|isControl
init|=
literal|true
decl_stmt|;
name|ExternalRoomDepartment
name|erd
init|=
literal|null
decl_stmt|;
for|for
control|(
name|Iterator
name|erdIt
init|=
name|deptList
operator|.
name|iterator
argument_list|()
init|;
operator|(
name|erdIt
operator|.
name|hasNext
argument_list|()
operator|&&
name|isControl
operator|)
condition|;
control|)
block|{
name|erd
operator|=
operator|(
name|ExternalRoomDepartment
operator|)
name|erdIt
operator|.
name|next
argument_list|()
expr_stmt|;
if|if
condition|(
name|erd
operator|!=
literal|null
operator|&&
operator|!
name|erd
operator|.
name|equals
argument_list|(
name|externalRoomDept
argument_list|)
condition|)
block|{
if|if
condition|(
operator|!
name|erd
operator|.
name|getDepartmentCode
argument_list|()
operator|.
name|equals
argument_list|(
name|externalRoomDept
operator|.
name|getDepartmentCode
argument_list|()
argument_list|)
condition|)
block|{
if|if
condition|(
name|externalRoomDept
operator|.
name|getAssignmentType
argument_list|()
operator|.
name|equals
argument_list|(
name|asgn
argument_list|)
condition|)
block|{
if|if
condition|(
name|erd
operator|.
name|getAssignmentType
argument_list|()
operator|.
name|equals
argument_list|(
name|asgn
argument_list|)
operator|&&
name|erd
operator|.
name|getPercent
argument_list|()
operator|.
name|compareTo
argument_list|(
name|externalRoomDept
operator|.
name|getPercent
argument_list|()
argument_list|)
operator|>=
literal|0
condition|)
block|{
name|isControl
operator|=
literal|false
expr_stmt|;
block|}
if|else if
condition|(
name|erd
operator|.
name|getAssignmentType
argument_list|()
operator|.
name|equals
argument_list|(
name|sched
argument_list|)
condition|)
block|{
name|isControl
operator|=
literal|false
expr_stmt|;
block|}
block|}
if|else if
condition|(
name|externalRoomDept
operator|.
name|getAssignmentType
argument_list|()
operator|.
name|equals
argument_list|(
name|sched
argument_list|)
condition|)
block|{
if|if
condition|(
name|erd
operator|.
name|getAssignmentType
argument_list|()
operator|.
name|equals
argument_list|(
name|sched
argument_list|)
operator|&&
name|erd
operator|.
name|getPercent
argument_list|()
operator|.
name|compareTo
argument_list|(
name|externalRoomDept
operator|.
name|getPercent
argument_list|()
argument_list|)
operator|>=
literal|0
condition|)
block|{
name|isControl
operator|=
literal|false
expr_stmt|;
block|}
block|}
block|}
block|}
block|}
return|return
operator|(
name|isControl
operator|)
return|;
block|}
block|}
block|}
end_class

end_unit

