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
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
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
name|BaseCourseRequest
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|CourseRequest
extends|extends
name|BaseCourseRequest
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
name|CourseRequest
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|CourseRequest
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
name|CourseRequest
operator|)
condition|)
return|return
operator|-
literal|1
return|;
name|CourseRequest
name|cr
init|=
operator|(
name|CourseRequest
operator|)
name|o
decl_stmt|;
name|int
name|cmp
init|=
name|getOrder
argument_list|()
operator|.
name|compareTo
argument_list|(
name|cr
operator|.
name|getOrder
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
return|return
operator|(
name|getUniqueId
argument_list|()
operator|==
literal|null
condition|?
operator|new
name|Long
argument_list|(
operator|-
literal|1
argument_list|)
else|:
name|getUniqueId
argument_list|()
operator|)
operator|.
name|compareTo
argument_list|(
name|cr
operator|.
name|getUniqueId
argument_list|()
operator|==
literal|null
condition|?
operator|-
literal|1
else|:
name|cr
operator|.
name|getUniqueId
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|List
argument_list|<
name|StudentClassEnrollment
argument_list|>
name|getClassEnrollments
parameter_list|()
block|{
name|List
argument_list|<
name|StudentClassEnrollment
argument_list|>
name|ret
init|=
operator|new
name|ArrayList
argument_list|<
name|StudentClassEnrollment
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|StudentClassEnrollment
name|e
range|:
name|getCourseDemand
argument_list|()
operator|.
name|getStudent
argument_list|()
operator|.
name|getClassEnrollments
argument_list|()
control|)
block|{
if|if
condition|(
name|this
operator|.
name|equals
argument_list|(
name|e
operator|.
name|getCourseRequest
argument_list|()
argument_list|)
operator|||
operator|(
name|e
operator|.
name|getCourseRequest
argument_list|()
operator|==
literal|null
operator|&&
name|getCourseOffering
argument_list|()
operator|.
name|equals
argument_list|(
name|e
operator|.
name|getCourseOffering
argument_list|()
argument_list|)
operator|)
condition|)
name|ret
operator|.
name|add
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
return|return
name|ret
return|;
block|}
block|}
end_class

end_unit

