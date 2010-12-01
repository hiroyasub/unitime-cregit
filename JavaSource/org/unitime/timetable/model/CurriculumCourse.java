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
name|TreeSet
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
name|BaseCurriculumCourse
import|;
end_import

begin_class
specifier|public
class|class
name|CurriculumCourse
extends|extends
name|BaseCurriculumCourse
implements|implements
name|Comparable
argument_list|<
name|CurriculumCourse
argument_list|>
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
name|CurriculumCourse
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|CurriculumCourse
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
name|CurriculumCourse
name|c
parameter_list|)
block|{
if|if
condition|(
name|getOrd
argument_list|()
operator|!=
literal|null
operator|&&
name|c
operator|.
name|getOrd
argument_list|()
operator|!=
literal|null
operator|&&
operator|!
name|getOrd
argument_list|()
operator|.
name|equals
argument_list|(
name|c
operator|.
name|getOrd
argument_list|()
argument_list|)
condition|)
return|return
name|getOrd
argument_list|()
operator|.
name|compareTo
argument_list|(
name|c
operator|.
name|getOrd
argument_list|()
argument_list|)
return|;
if|if
condition|(
name|getCourse
argument_list|()
operator|.
name|equals
argument_list|(
name|c
operator|.
name|getCourse
argument_list|()
argument_list|)
condition|)
return|return
name|getClassification
argument_list|()
operator|.
name|compareTo
argument_list|(
name|c
operator|.
name|getClassification
argument_list|()
argument_list|)
return|;
for|for
control|(
name|CurriculumClassification
name|cc1
range|:
operator|new
name|TreeSet
argument_list|<
name|CurriculumClassification
argument_list|>
argument_list|(
name|getClassification
argument_list|()
operator|.
name|getCurriculum
argument_list|()
operator|.
name|getClassifications
argument_list|()
argument_list|)
control|)
block|{
name|CurriculumClassification
name|cc2
init|=
literal|null
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|c
operator|.
name|getClassification
argument_list|()
operator|.
name|getCurriculum
argument_list|()
operator|.
name|getClassifications
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
name|CurriculumClassification
name|cc
init|=
operator|(
name|CurriculumClassification
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|cc1
operator|.
name|getAcademicClassification
argument_list|()
operator|!=
literal|null
operator|&&
name|cc1
operator|.
name|getAcademicClassification
argument_list|()
operator|.
name|equals
argument_list|(
name|cc
operator|.
name|getAcademicClassification
argument_list|()
argument_list|)
condition|)
block|{
name|cc2
operator|=
name|cc
expr_stmt|;
break|break;
block|}
if|if
condition|(
name|cc1
operator|.
name|getAcademicClassification
argument_list|()
operator|==
literal|null
operator|&&
name|cc1
operator|.
name|getName
argument_list|()
operator|.
name|equals
argument_list|(
name|cc
operator|.
name|getName
argument_list|()
argument_list|)
condition|)
block|{
name|cc2
operator|=
name|cc
expr_stmt|;
break|break;
block|}
block|}
name|float
name|s1
init|=
literal|0f
decl_stmt|,
name|s2
init|=
literal|0f
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|cc1
operator|.
name|getCourses
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
name|CurriculumCourse
name|x
init|=
operator|(
name|CurriculumCourse
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|x
operator|.
name|getCourse
argument_list|()
operator|.
name|equals
argument_list|(
name|getCourse
argument_list|()
argument_list|)
condition|)
block|{
name|s1
operator|=
name|x
operator|.
name|getPercShare
argument_list|()
expr_stmt|;
break|break;
block|}
block|}
if|if
condition|(
name|cc2
operator|!=
literal|null
condition|)
for|for
control|(
name|Iterator
name|i
init|=
name|cc2
operator|.
name|getCourses
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
name|CurriculumCourse
name|x
init|=
operator|(
name|CurriculumCourse
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|x
operator|.
name|getCourse
argument_list|()
operator|.
name|equals
argument_list|(
name|c
operator|.
name|getCourse
argument_list|()
argument_list|)
condition|)
block|{
name|s2
operator|=
name|x
operator|.
name|getPercShare
argument_list|()
expr_stmt|;
break|break;
block|}
block|}
name|int
name|cmp
init|=
operator|-
name|Double
operator|.
name|compare
argument_list|(
name|s1
argument_list|,
name|s2
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
name|getCourse
argument_list|()
operator|.
name|getSubjectArea
argument_list|()
operator|.
name|compareTo
argument_list|(
name|c
operator|.
name|getCourse
argument_list|()
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
name|getCourse
argument_list|()
operator|.
name|getCourseNbr
argument_list|()
operator|.
name|compareToIgnoreCase
argument_list|(
name|c
operator|.
name|getCourse
argument_list|()
operator|.
name|getCourseNbr
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
name|getCourse
argument_list|()
operator|.
name|getUniqueId
argument_list|()
operator|.
name|compareTo
argument_list|(
name|c
operator|.
name|getCourse
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
return|;
block|}
block|}
end_class

end_unit

