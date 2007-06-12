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
name|Iterator
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
name|BaseDepartmentStatusType
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
name|DepartmentStatusTypeDAO
import|;
end_import

begin_class
specifier|public
class|class
name|DepartmentStatusType
extends|extends
name|BaseDepartmentStatusType
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
specifier|public
specifier|static
specifier|final
name|int
name|sCanManagerView
init|=
literal|1
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|sCanManagerEdit
init|=
literal|2
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|sCanManagerLimitedEdit
init|=
literal|4
decl_stmt|;
comment|//e.g., assign instructors
specifier|public
specifier|static
specifier|final
name|int
name|sCanOwnerView
init|=
literal|8
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|sCanOwnerEdit
init|=
literal|16
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|sCanOwnerLimitedEdit
init|=
literal|32
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|sCanAudit
init|=
literal|64
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|sCanTimetable
init|=
literal|128
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|sCanCommit
init|=
literal|256
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|sApplySession
init|=
literal|1
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|sApplyDepartment
init|=
literal|2
decl_stmt|;
comment|/*[CONSTRUCTOR MARKER BEGIN]*/
specifier|public
name|DepartmentStatusType
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|DepartmentStatusType
parameter_list|(
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
name|DepartmentStatusType
parameter_list|(
name|Long
name|uniqueId
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|String
name|reference
parameter_list|)
block|{
name|super
argument_list|(
name|uniqueId
argument_list|,
name|reference
argument_list|)
expr_stmt|;
block|}
comment|/*[CONSTRUCTOR MARKER END]*/
comment|/** 	 *  	 */
specifier|public
specifier|static
name|DepartmentStatusType
name|findById
parameter_list|(
name|Integer
name|uid
parameter_list|)
throws|throws
name|Exception
block|{
if|if
condition|(
name|uid
operator|==
literal|null
condition|)
return|return
literal|null
return|;
return|return
operator|(
name|DepartmentStatusType
operator|)
operator|(
operator|new
name|DepartmentStatusTypeDAO
argument_list|()
operator|)
operator|.
name|getSession
argument_list|()
operator|.
name|createCriteria
argument_list|(
name|DepartmentStatusType
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
literal|"uniqueId"
argument_list|,
name|uid
argument_list|)
argument_list|)
operator|.
name|uniqueResult
argument_list|()
return|;
block|}
specifier|public
specifier|static
name|TreeSet
name|findAll
parameter_list|()
block|{
return|return
operator|new
name|TreeSet
argument_list|(
operator|(
operator|new
name|DepartmentStatusTypeDAO
argument_list|()
operator|.
name|findAll
argument_list|()
operator|)
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|TreeSet
name|findAll
parameter_list|(
name|int
name|apply
parameter_list|)
block|{
name|TreeSet
name|ret
init|=
name|findAll
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|ret
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
name|DepartmentStatusType
name|t
init|=
operator|(
name|DepartmentStatusType
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|t
operator|.
name|apply
argument_list|(
name|apply
argument_list|)
condition|)
name|i
operator|.
name|remove
argument_list|()
expr_stmt|;
block|}
return|return
name|ret
return|;
block|}
specifier|public
specifier|static
name|TreeSet
name|findAllForSession
parameter_list|()
block|{
return|return
name|findAll
argument_list|(
name|sApplySession
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|TreeSet
name|findAllForDepartment
parameter_list|()
block|{
return|return
name|findAll
argument_list|(
name|sApplyDepartment
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|DepartmentStatusType
name|findByRef
parameter_list|(
name|String
name|ref
parameter_list|)
block|{
if|if
condition|(
name|ref
operator|==
literal|null
condition|)
return|return
literal|null
return|;
return|return
operator|(
name|DepartmentStatusType
operator|)
operator|(
operator|new
name|DepartmentStatusTypeDAO
argument_list|()
operator|)
operator|.
name|getSession
argument_list|()
operator|.
name|createCriteria
argument_list|(
name|DepartmentStatusType
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
literal|"reference"
argument_list|,
name|ref
argument_list|)
argument_list|)
operator|.
name|uniqueResult
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
name|DepartmentStatusType
operator|)
condition|)
return|return
operator|-
literal|1
return|;
name|DepartmentStatusType
name|t
init|=
operator|(
name|DepartmentStatusType
operator|)
name|o
decl_stmt|;
return|return
name|getOrd
argument_list|()
operator|.
name|compareTo
argument_list|(
name|t
operator|.
name|getOrd
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|boolean
name|can
parameter_list|(
name|int
name|operation
parameter_list|)
block|{
return|return
operator|(
name|getStatus
argument_list|()
operator|.
name|intValue
argument_list|()
operator|&
name|operation
operator|)
operator|==
name|operation
return|;
block|}
specifier|public
name|boolean
name|canManagerEdit
parameter_list|()
block|{
return|return
name|can
argument_list|(
name|sCanManagerEdit
argument_list|)
return|;
block|}
specifier|public
name|boolean
name|canManagerLimitedEdit
parameter_list|()
block|{
return|return
name|can
argument_list|(
name|sCanManagerLimitedEdit
argument_list|)
return|;
block|}
specifier|public
name|boolean
name|canManagerView
parameter_list|()
block|{
return|return
name|can
argument_list|(
name|sCanManagerView
argument_list|)
return|;
block|}
specifier|public
name|boolean
name|canOwnerEdit
parameter_list|()
block|{
return|return
name|can
argument_list|(
name|sCanOwnerEdit
argument_list|)
return|;
block|}
specifier|public
name|boolean
name|canOwnerLimitedEdit
parameter_list|()
block|{
return|return
name|can
argument_list|(
name|sCanOwnerLimitedEdit
argument_list|)
return|;
block|}
specifier|public
name|boolean
name|canOwnerView
parameter_list|()
block|{
return|return
name|can
argument_list|(
name|sCanOwnerView
argument_list|)
return|;
block|}
specifier|public
name|boolean
name|canAudit
parameter_list|()
block|{
return|return
name|can
argument_list|(
name|sCanAudit
argument_list|)
return|;
block|}
specifier|public
name|boolean
name|canTimetable
parameter_list|()
block|{
return|return
name|can
argument_list|(
name|sCanTimetable
argument_list|)
return|;
block|}
specifier|public
name|boolean
name|canCommit
parameter_list|()
block|{
return|return
name|can
argument_list|(
name|sCanCommit
argument_list|)
return|;
block|}
specifier|public
name|boolean
name|apply
parameter_list|(
name|int
name|apply
parameter_list|)
block|{
return|return
operator|(
name|getApply
argument_list|()
operator|.
name|intValue
argument_list|()
operator|&
name|apply
operator|)
operator|==
name|apply
return|;
block|}
specifier|public
name|boolean
name|applySession
parameter_list|()
block|{
return|return
name|apply
argument_list|(
name|sApplySession
argument_list|)
return|;
block|}
specifier|public
name|boolean
name|applyDepartment
parameter_list|()
block|{
return|return
name|apply
argument_list|(
name|sApplyDepartment
argument_list|)
return|;
block|}
block|}
end_class

end_unit

