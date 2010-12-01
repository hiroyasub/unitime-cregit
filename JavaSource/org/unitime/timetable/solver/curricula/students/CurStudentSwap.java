begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 (University Timetabling Application)  * Copyright (C) 2010, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
operator|.
name|students
package|;
end_package

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|cpsolver
operator|.
name|ifs
operator|.
name|heuristics
operator|.
name|NeighbourSelection
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|cpsolver
operator|.
name|ifs
operator|.
name|model
operator|.
name|Neighbour
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|cpsolver
operator|.
name|ifs
operator|.
name|solution
operator|.
name|Solution
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|cpsolver
operator|.
name|ifs
operator|.
name|solver
operator|.
name|Solver
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
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
name|net
operator|.
name|sf
operator|.
name|cpsolver
operator|.
name|ifs
operator|.
name|util
operator|.
name|ToolBox
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|CurStudentSwap
implements|implements
name|NeighbourSelection
argument_list|<
name|CurVariable
argument_list|,
name|CurValue
argument_list|>
block|{
specifier|protected
name|CurStudentSwap
parameter_list|(
name|DataProperties
name|config
parameter_list|)
block|{
block|}
annotation|@
name|Override
specifier|public
name|void
name|init
parameter_list|(
name|Solver
argument_list|<
name|CurVariable
argument_list|,
name|CurValue
argument_list|>
name|solver
parameter_list|)
block|{
block|}
annotation|@
name|Override
specifier|public
name|Neighbour
argument_list|<
name|CurVariable
argument_list|,
name|CurValue
argument_list|>
name|selectNeighbour
parameter_list|(
name|Solution
argument_list|<
name|CurVariable
argument_list|,
name|CurValue
argument_list|>
name|solution
parameter_list|)
block|{
name|CurModel
name|model
init|=
operator|(
name|CurModel
operator|)
name|solution
operator|.
name|getModel
argument_list|()
decl_stmt|;
name|CurCourse
name|course
init|=
name|ToolBox
operator|.
name|random
argument_list|(
name|model
operator|.
name|getSwapCourses
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|course
operator|==
literal|null
condition|)
return|return
literal|null
return|;
name|CurStudent
name|student
init|=
literal|null
decl_stmt|;
name|CurValue
name|oldValue
init|=
literal|null
decl_stmt|,
name|newValue
init|=
literal|null
decl_stmt|;
name|int
name|idx
init|=
name|ToolBox
operator|.
name|random
argument_list|(
name|model
operator|.
name|getStudents
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|model
operator|.
name|getStudents
argument_list|()
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|student
operator|=
name|model
operator|.
name|getStudents
argument_list|()
operator|.
name|get
argument_list|(
operator|(
name|i
operator|+
name|idx
operator|)
operator|%
name|model
operator|.
name|getStudents
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|oldValue
operator|=
name|course
operator|.
name|getValue
argument_list|(
name|student
argument_list|)
expr_stmt|;
if|if
condition|(
name|oldValue
operator|==
literal|null
operator|&&
name|student
operator|.
name|getCourses
argument_list|()
operator|.
name|size
argument_list|()
operator|<
name|model
operator|.
name|getStudentLimit
argument_list|()
operator|.
name|getMaxLimit
argument_list|()
condition|)
break|break;
if|if
condition|(
name|oldValue
operator|!=
literal|null
operator|&&
name|student
operator|.
name|getCourses
argument_list|()
operator|.
name|size
argument_list|()
operator|>
name|model
operator|.
name|getStudentLimit
argument_list|()
operator|.
name|getMinLimit
argument_list|()
condition|)
break|break;
block|}
if|if
condition|(
name|oldValue
operator|==
literal|null
operator|&&
name|student
operator|.
name|getCourses
argument_list|()
operator|.
name|size
argument_list|()
operator|>=
name|model
operator|.
name|getStudentLimit
argument_list|()
operator|.
name|getMaxLimit
argument_list|()
condition|)
return|return
literal|null
return|;
if|if
condition|(
name|oldValue
operator|!=
literal|null
operator|&&
name|student
operator|.
name|getCourses
argument_list|()
operator|.
name|size
argument_list|()
operator|<=
name|model
operator|.
name|getStudentLimit
argument_list|()
operator|.
name|getMinLimit
argument_list|()
condition|)
return|return
literal|null
return|;
name|idx
operator|=
name|ToolBox
operator|.
name|random
argument_list|(
name|model
operator|.
name|getStudents
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|model
operator|.
name|getStudents
argument_list|()
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|CurStudent
name|newStudent
init|=
name|model
operator|.
name|getStudents
argument_list|()
operator|.
name|get
argument_list|(
operator|(
name|i
operator|+
name|idx
operator|)
operator|%
name|model
operator|.
name|getStudents
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|oldValue
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|course
operator|.
name|getStudents
argument_list|()
operator|.
name|contains
argument_list|(
name|newStudent
argument_list|)
condition|)
continue|continue;
if|if
condition|(
name|newStudent
operator|.
name|getCourses
argument_list|()
operator|.
name|size
argument_list|()
operator|>=
name|model
operator|.
name|getStudentLimit
argument_list|()
operator|.
name|getMaxLimit
argument_list|()
condition|)
continue|continue;
if|if
condition|(
name|course
operator|.
name|getSize
argument_list|()
operator|+
name|newStudent
operator|.
name|getWeight
argument_list|()
operator|-
name|student
operator|.
name|getWeight
argument_list|()
operator|>
name|course
operator|.
name|getMaxSize
argument_list|()
condition|)
continue|continue;
if|if
condition|(
name|course
operator|.
name|getSize
argument_list|()
operator|+
name|newStudent
operator|.
name|getWeight
argument_list|()
operator|-
name|student
operator|.
name|getWeight
argument_list|()
operator|<
name|course
operator|.
name|getMaxSize
argument_list|()
operator|-
name|model
operator|.
name|getMinStudentWidth
argument_list|()
condition|)
continue|continue;
name|newValue
operator|=
operator|new
name|CurValue
argument_list|(
name|oldValue
operator|.
name|variable
argument_list|()
argument_list|,
name|newStudent
argument_list|)
expr_stmt|;
break|break;
block|}
else|else
block|{
if|if
condition|(
name|newStudent
operator|.
name|getCourses
argument_list|()
operator|.
name|size
argument_list|()
operator|<=
name|model
operator|.
name|getStudentLimit
argument_list|()
operator|.
name|getMinLimit
argument_list|()
condition|)
continue|continue;
if|if
condition|(
name|course
operator|.
name|getSize
argument_list|()
operator|+
name|student
operator|.
name|getWeight
argument_list|()
operator|-
name|newStudent
operator|.
name|getWeight
argument_list|()
operator|>
name|course
operator|.
name|getMaxSize
argument_list|()
condition|)
continue|continue;
if|if
condition|(
name|course
operator|.
name|getSize
argument_list|()
operator|+
name|student
operator|.
name|getWeight
argument_list|()
operator|-
name|newStudent
operator|.
name|getWeight
argument_list|()
operator|<
name|course
operator|.
name|getMaxSize
argument_list|()
operator|-
name|model
operator|.
name|getMinStudentWidth
argument_list|()
condition|)
continue|continue;
name|oldValue
operator|=
name|course
operator|.
name|getValue
argument_list|(
name|newStudent
argument_list|)
expr_stmt|;
if|if
condition|(
name|oldValue
operator|!=
literal|null
condition|)
block|{
name|newValue
operator|=
operator|new
name|CurValue
argument_list|(
name|oldValue
operator|.
name|variable
argument_list|()
argument_list|,
name|student
argument_list|)
expr_stmt|;
break|break;
block|}
block|}
block|}
return|return
operator|(
name|newValue
operator|==
literal|null
condition|?
literal|null
else|:
operator|new
name|CurSimpleAssignment
argument_list|(
name|newValue
argument_list|)
operator|)
return|;
block|}
block|}
end_class

end_unit

