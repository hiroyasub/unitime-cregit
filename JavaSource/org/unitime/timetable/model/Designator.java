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
name|org
operator|.
name|unitime
operator|.
name|commons
operator|.
name|User
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
name|BaseDesignator
import|;
end_import

begin_class
specifier|public
class|class
name|Designator
extends|extends
name|BaseDesignator
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
name|Designator
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|Designator
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
name|Designator
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Long
name|uniqueId
parameter_list|,
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|SubjectArea
name|subjectArea
parameter_list|,
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|DepartmentalInstructor
name|instructor
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|String
name|code
parameter_list|)
block|{
name|super
argument_list|(
name|uniqueId
argument_list|,
name|subjectArea
argument_list|,
name|instructor
argument_list|,
name|code
argument_list|)
expr_stmt|;
block|}
comment|/*[CONSTRUCTOR MARKER END]*/
comment|/** 	 * Checks whether current user can edit the designator list 	 */
specifier|public
name|boolean
name|canUserEdit
parameter_list|(
name|User
name|user
parameter_list|)
block|{
if|if
condition|(
name|user
operator|.
name|isAdmin
argument_list|()
condition|)
return|return
literal|true
return|;
name|TimetableManager
name|tm
init|=
name|TimetableManager
operator|.
name|getManager
argument_list|(
name|user
argument_list|)
decl_stmt|;
if|if
condition|(
name|tm
operator|==
literal|null
condition|)
return|return
literal|false
return|;
if|if
condition|(
operator|!
name|tm
operator|.
name|getDepartments
argument_list|()
operator|.
name|contains
argument_list|(
name|this
operator|.
name|getSubjectArea
argument_list|()
operator|.
name|getDepartment
argument_list|()
argument_list|)
condition|)
return|return
literal|false
return|;
name|DepartmentStatusType
name|dst
init|=
name|this
operator|.
name|getSubjectArea
argument_list|()
operator|.
name|getDepartment
argument_list|()
operator|.
name|effectiveStatusType
argument_list|()
decl_stmt|;
return|return
operator|(
name|dst
operator|.
name|canOwnerEdit
argument_list|()
operator|||
name|dst
operator|.
name|canOwnerLimitedEdit
argument_list|()
operator|)
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
name|Designator
operator|)
condition|)
return|return
operator|-
literal|1
return|;
name|Designator
name|d
init|=
operator|(
name|Designator
operator|)
name|o
decl_stmt|;
try|try
block|{
name|int
name|c1
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|getCode
argument_list|()
argument_list|)
decl_stmt|;
name|int
name|c2
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|d
operator|.
name|getCode
argument_list|()
argument_list|)
decl_stmt|;
name|int
name|cmp
init|=
operator|(
name|c1
operator|<
name|c2
condition|?
operator|-
literal|1
else|:
name|c1
operator|>
name|c2
condition|?
literal|1
else|:
literal|0
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
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|e
parameter_list|)
block|{
name|int
name|cmp
init|=
name|getCode
argument_list|()
operator|.
name|compareTo
argument_list|(
name|d
operator|.
name|getCode
argument_list|()
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
block|}
name|int
name|cmp
init|=
name|getSubjectArea
argument_list|()
operator|.
name|compareTo
argument_list|(
name|d
operator|.
name|getSubjectArea
argument_list|()
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
name|cmp
operator|=
name|getInstructor
argument_list|()
operator|.
name|compareTo
argument_list|(
name|d
operator|.
name|getInstructor
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
name|d
operator|.
name|getUniqueId
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|getCode
argument_list|()
operator|+
operator|(
name|getSubjectArea
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
literal|" ("
operator|+
name|getSubjectArea
argument_list|()
operator|.
name|getSubjectAreaAbbreviation
argument_list|()
operator|+
literal|")"
operator|)
return|;
block|}
block|}
end_class

end_unit

