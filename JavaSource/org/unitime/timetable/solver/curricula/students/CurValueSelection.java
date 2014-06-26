begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 - 3.5 (University Timetabling Application)  * Copyright (C) 2010 - 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|cpsolver
operator|.
name|ifs
operator|.
name|assignment
operator|.
name|Assignment
import|;
end_import

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|ifs
operator|.
name|heuristics
operator|.
name|ValueSelection
import|;
end_import

begin_import
import|import
name|org
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
name|org
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
name|CurValueSelection
implements|implements
name|ValueSelection
argument_list|<
name|CurVariable
argument_list|,
name|CurValue
argument_list|>
block|{
specifier|public
name|CurValueSelection
parameter_list|(
name|DataProperties
name|cfg
parameter_list|)
block|{
block|}
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
specifier|public
name|CurValue
name|selectValue
parameter_list|(
name|Solution
argument_list|<
name|CurVariable
argument_list|,
name|CurValue
argument_list|>
name|solution
parameter_list|,
name|CurVariable
name|selectedVariable
parameter_list|)
block|{
comment|/* 		if (ToolBox.random()< 0.2) 			return selectValueSlow(solution, selectedVariable); 		return selectValueFast(solution, selectedVariable); 		*/
return|return
name|selectValueSlow
argument_list|(
name|solution
argument_list|,
name|selectedVariable
argument_list|)
return|;
block|}
specifier|public
name|CurValue
name|selectValueFast
parameter_list|(
name|Solution
argument_list|<
name|CurVariable
argument_list|,
name|CurValue
argument_list|>
name|solution
parameter_list|,
name|CurVariable
name|selectedVariable
parameter_list|)
block|{
name|CurModel
name|m
init|=
operator|(
name|CurModel
operator|)
name|solution
operator|.
name|getModel
argument_list|()
decl_stmt|;
name|Assignment
argument_list|<
name|CurVariable
argument_list|,
name|CurValue
argument_list|>
name|assignment
init|=
name|solution
operator|.
name|getAssignment
argument_list|()
decl_stmt|;
name|CurValue
name|currentValue
init|=
name|assignment
operator|.
name|getValue
argument_list|(
name|selectedVariable
argument_list|)
decl_stmt|;
if|if
condition|(
name|currentValue
operator|!=
literal|null
operator|&&
name|currentValue
operator|.
name|getStudent
argument_list|()
operator|.
name|getCourses
argument_list|(
name|assignment
argument_list|)
operator|.
name|size
argument_list|()
operator|<=
name|m
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
name|int
name|size
init|=
name|selectedVariable
operator|.
name|values
argument_list|(
name|solution
operator|.
name|getAssignment
argument_list|()
argument_list|)
operator|.
name|size
argument_list|()
decl_stmt|;
name|int
name|i
init|=
name|ToolBox
operator|.
name|random
argument_list|(
name|size
argument_list|)
decl_stmt|;
for|for
control|(
name|int
name|j
init|=
literal|0
init|;
name|j
operator|<
name|size
condition|;
name|j
operator|++
control|)
block|{
name|CurValue
name|student
init|=
name|selectedVariable
operator|.
name|values
argument_list|(
name|solution
operator|.
name|getAssignment
argument_list|()
argument_list|)
operator|.
name|get
argument_list|(
operator|(
name|i
operator|+
name|j
operator|)
operator|%
name|size
argument_list|)
decl_stmt|;
if|if
condition|(
name|student
operator|.
name|equals
argument_list|(
name|currentValue
argument_list|)
condition|)
continue|continue;
if|if
condition|(
name|selectedVariable
operator|.
name|getCourse
argument_list|()
operator|.
name|getStudents
argument_list|(
name|assignment
argument_list|)
operator|.
name|contains
argument_list|(
name|student
operator|.
name|getStudent
argument_list|()
argument_list|)
condition|)
continue|continue;
if|if
condition|(
name|student
operator|.
name|getStudent
argument_list|()
operator|.
name|getCourses
argument_list|(
name|assignment
argument_list|)
operator|.
name|size
argument_list|()
operator|>=
name|m
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
name|selectedVariable
operator|.
name|getCourse
argument_list|()
operator|.
name|getSize
argument_list|(
name|assignment
argument_list|)
operator|+
name|student
operator|.
name|getStudent
argument_list|()
operator|.
name|getWeight
argument_list|()
operator|-
operator|(
name|currentValue
operator|==
literal|null
condition|?
literal|0.0
else|:
name|currentValue
operator|.
name|getStudent
argument_list|()
operator|.
name|getWeight
argument_list|()
operator|)
operator|>
name|selectedVariable
operator|.
name|getCourse
argument_list|()
operator|.
name|getMaxSize
argument_list|()
condition|)
continue|continue;
if|if
condition|(
name|currentValue
operator|!=
literal|null
operator|&&
name|selectedVariable
operator|.
name|getCourse
argument_list|()
operator|.
name|getSize
argument_list|(
name|assignment
argument_list|)
operator|+
name|student
operator|.
name|getStudent
argument_list|()
operator|.
name|getWeight
argument_list|()
operator|-
name|currentValue
operator|.
name|getStudent
argument_list|()
operator|.
name|getWeight
argument_list|()
operator|<
name|selectedVariable
operator|.
name|getCourse
argument_list|()
operator|.
name|getMaxSize
argument_list|()
operator|-
name|m
operator|.
name|getMinStudentWidth
argument_list|()
condition|)
continue|continue;
return|return
name|student
return|;
block|}
return|return
literal|null
return|;
block|}
specifier|public
name|CurValue
name|selectValueSlow
parameter_list|(
name|Solution
argument_list|<
name|CurVariable
argument_list|,
name|CurValue
argument_list|>
name|solution
parameter_list|,
name|CurVariable
name|selectedVariable
parameter_list|)
block|{
name|CurModel
name|m
init|=
operator|(
name|CurModel
operator|)
name|solution
operator|.
name|getModel
argument_list|()
decl_stmt|;
name|Assignment
argument_list|<
name|CurVariable
argument_list|,
name|CurValue
argument_list|>
name|assignment
init|=
name|solution
operator|.
name|getAssignment
argument_list|()
decl_stmt|;
name|CurValue
name|currentValue
init|=
name|assignment
operator|.
name|getValue
argument_list|(
name|selectedVariable
argument_list|)
decl_stmt|;
if|if
condition|(
name|currentValue
operator|!=
literal|null
operator|&&
name|currentValue
operator|.
name|getStudent
argument_list|()
operator|.
name|getCourses
argument_list|(
name|assignment
argument_list|)
operator|.
name|size
argument_list|()
operator|<=
name|m
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
name|List
argument_list|<
name|CurValue
argument_list|>
name|bestStudents
init|=
operator|new
name|ArrayList
argument_list|<
name|CurValue
argument_list|>
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|CurValue
argument_list|>
name|allImprovingStudents
init|=
operator|new
name|ArrayList
argument_list|<
name|CurValue
argument_list|>
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|CurValue
argument_list|>
name|allStudents
init|=
operator|new
name|ArrayList
argument_list|<
name|CurValue
argument_list|>
argument_list|()
decl_stmt|;
name|double
name|bestValue
init|=
literal|0
decl_stmt|;
for|for
control|(
name|CurValue
name|student
range|:
name|selectedVariable
operator|.
name|values
argument_list|(
name|solution
operator|.
name|getAssignment
argument_list|()
argument_list|)
control|)
block|{
if|if
condition|(
name|student
operator|.
name|equals
argument_list|(
name|currentValue
argument_list|)
condition|)
continue|continue;
if|if
condition|(
name|selectedVariable
operator|.
name|getCourse
argument_list|()
operator|.
name|getStudents
argument_list|(
name|assignment
argument_list|)
operator|.
name|contains
argument_list|(
name|student
operator|.
name|getStudent
argument_list|()
argument_list|)
condition|)
continue|continue;
if|if
condition|(
name|student
operator|.
name|getStudent
argument_list|()
operator|.
name|getCourses
argument_list|(
name|assignment
argument_list|)
operator|.
name|size
argument_list|()
operator|>=
name|m
operator|.
name|getStudentLimit
argument_list|()
operator|.
name|getMaxLimit
argument_list|()
condition|)
continue|continue;
comment|/* 			if (selectedVariable.getCourse().getSize() + student.getStudent().getWeight() -  					(currentValue == null ? 0.0 : currentValue.getStudent().getWeight())> 					selectedVariable.getCourse().getMaxSize()) continue; 			if (currentValue != null&&  					selectedVariable.getCourse().getSize() + student.getStudent().getWeight() -  					currentValue.getStudent().getWeight()< 					selectedVariable.getCourse().getMaxSize() - m.getMinStudentWidth()) continue; */
name|double
name|value
init|=
name|student
operator|.
name|toDouble
argument_list|(
name|assignment
argument_list|)
decl_stmt|;
name|Set
argument_list|<
name|CurValue
argument_list|>
name|conflicts
init|=
name|m
operator|.
name|conflictValues
argument_list|(
name|assignment
argument_list|,
name|student
argument_list|)
decl_stmt|;
for|for
control|(
name|CurValue
name|conf
range|:
name|conflicts
control|)
block|{
name|value
operator|-=
name|conf
operator|.
name|toDouble
argument_list|(
name|assignment
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|bestStudents
operator|.
name|isEmpty
argument_list|()
operator|||
name|bestValue
operator|>
name|value
condition|)
block|{
name|bestValue
operator|=
name|value
expr_stmt|;
name|bestStudents
operator|.
name|clear
argument_list|()
expr_stmt|;
name|bestStudents
operator|.
name|add
argument_list|(
name|student
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|bestValue
operator|==
name|value
condition|)
block|{
name|bestStudents
operator|.
name|add
argument_list|(
name|student
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|value
operator|!=
literal|0
condition|)
name|allImprovingStudents
operator|.
name|add
argument_list|(
name|student
argument_list|)
expr_stmt|;
name|allStudents
operator|.
name|add
argument_list|(
name|student
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|currentValue
operator|!=
literal|null
condition|)
block|{
name|double
name|rnd
init|=
name|ToolBox
operator|.
name|random
argument_list|()
decl_stmt|;
if|if
condition|(
name|rnd
operator|<
literal|0.01
condition|)
return|return
name|ToolBox
operator|.
name|random
argument_list|(
name|allStudents
argument_list|)
return|;
if|if
condition|(
name|rnd
operator|<
literal|0.10
condition|)
return|return
name|ToolBox
operator|.
name|random
argument_list|(
name|allImprovingStudents
argument_list|)
return|;
block|}
return|return
name|ToolBox
operator|.
name|random
argument_list|(
name|bestStudents
argument_list|)
return|;
block|}
block|}
end_class

end_unit

