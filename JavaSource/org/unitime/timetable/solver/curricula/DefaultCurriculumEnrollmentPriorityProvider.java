begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.4 - 3.5 (University Timetabling Application)  * Copyright (C) 2012 - 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|solver
operator|.
name|curricula
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
name|DataProperties
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
name|CurriculumCourse
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
name|CurriculumCourseGroup
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|DefaultCurriculumEnrollmentPriorityProvider
implements|implements
name|CurriculumEnrollmentPriorityProvider
block|{
specifier|private
name|float
name|iThreshold
init|=
literal|0.95f
decl_stmt|;
specifier|private
name|String
name|iGroupMatch
init|=
literal|null
decl_stmt|;
specifier|public
name|DefaultCurriculumEnrollmentPriorityProvider
parameter_list|(
name|DataProperties
name|config
parameter_list|)
block|{
name|iThreshold
operator|=
name|config
operator|.
name|getPropertyFloat
argument_list|(
literal|"CurriculumEnrollmentPriority.Threshold"
argument_list|,
name|iThreshold
argument_list|)
expr_stmt|;
name|iGroupMatch
operator|=
name|config
operator|.
name|getProperty
argument_list|(
literal|"CurriculumEnrollmentPriority.GroupMatch"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|Double
name|getEnrollmentPriority
parameter_list|(
name|CurriculumCourse
name|course
parameter_list|)
block|{
if|if
condition|(
name|course
operator|.
name|getPercShare
argument_list|()
operator|>=
name|iThreshold
condition|)
return|return
literal|1.0
return|;
comment|// required course -- important
for|for
control|(
name|CurriculumCourseGroup
name|group
range|:
name|course
operator|.
name|getGroups
argument_list|()
control|)
block|{
if|if
condition|(
name|iGroupMatch
operator|!=
literal|null
operator|&&
operator|!
name|iGroupMatch
operator|.
name|isEmpty
argument_list|()
operator|&&
name|group
operator|.
name|getName
argument_list|()
operator|.
name|matches
argument_list|(
name|iGroupMatch
argument_list|)
condition|)
block|{
return|return
literal|1.0
return|;
comment|// matching group name
block|}
if|if
condition|(
name|group
operator|.
name|getType
argument_list|()
operator|==
literal|0
condition|)
block|{
comment|// optional group
name|float
name|totalShare
init|=
literal|0.0f
decl_stmt|;
for|for
control|(
name|CurriculumCourse
name|other
range|:
name|course
operator|.
name|getClassification
argument_list|()
operator|.
name|getCourses
argument_list|()
control|)
if|if
condition|(
name|other
operator|.
name|getGroups
argument_list|()
operator|.
name|contains
argument_list|(
name|group
argument_list|)
condition|)
name|totalShare
operator|+=
name|other
operator|.
name|getPercShare
argument_list|()
expr_stmt|;
if|if
condition|(
name|totalShare
operator|>=
name|iThreshold
condition|)
return|return
literal|1.0
return|;
comment|// it is required to take one of the courses of the group
block|}
if|else if
condition|(
name|group
operator|.
name|getType
argument_list|()
operator|==
literal|1
condition|)
block|{
for|for
control|(
name|CurriculumCourse
name|other
range|:
name|course
operator|.
name|getClassification
argument_list|()
operator|.
name|getCourses
argument_list|()
control|)
if|if
condition|(
name|other
operator|.
name|getGroups
argument_list|()
operator|.
name|contains
argument_list|(
name|group
argument_list|)
operator|&&
name|other
operator|.
name|getPercShare
argument_list|()
operator|>=
name|iThreshold
condition|)
return|return
literal|1.0
return|;
comment|// it is required to take courses from this group
block|}
block|}
return|return
literal|null
return|;
block|}
block|}
end_class

end_unit

