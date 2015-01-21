begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to The Apereo Foundation under one or more contributor license  * agreements. See the NOTICE file distributed with this work for  * additional information regarding copyright ownership.  *  * The Apereo Foundation licenses this file to you under the Apache License,  * Version 2.0 (the "License"); you may not use this file except in  * compliance with the License. You may obtain a copy of the License at:  *  * http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *  * See the License for the specific language governing permissions and  * limitations under the License.  *  */
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
name|RouletteWheelSelection
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
name|VariableSelection
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
name|CurVariableSelection
implements|implements
name|VariableSelection
argument_list|<
name|CurVariable
argument_list|,
name|CurValue
argument_list|>
block|{
name|RouletteWheelSelection
argument_list|<
name|CurVariable
argument_list|>
name|iWheel
init|=
literal|null
decl_stmt|;
specifier|public
name|CurVariableSelection
parameter_list|(
name|DataProperties
name|p
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
name|CurVariable
name|selectVariable
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
if|if
condition|(
name|m
operator|.
name|nrUnassignedVariables
argument_list|(
name|assignment
argument_list|)
operator|>
literal|0
condition|)
block|{
name|List
argument_list|<
name|CurVariable
argument_list|>
name|best
init|=
operator|new
name|ArrayList
argument_list|<
name|CurVariable
argument_list|>
argument_list|()
decl_stmt|;
name|double
name|bestValue
init|=
literal|0.0
decl_stmt|;
for|for
control|(
name|CurVariable
name|course
range|:
name|m
operator|.
name|unassignedVariables
argument_list|(
name|assignment
argument_list|)
control|)
block|{
if|if
condition|(
name|course
operator|.
name|getCourse
argument_list|()
operator|.
name|isComplete
argument_list|(
name|assignment
argument_list|)
condition|)
continue|continue;
name|double
name|value
init|=
name|course
operator|.
name|getCourse
argument_list|()
operator|.
name|getMaxSize
argument_list|()
operator|-
name|course
operator|.
name|getCourse
argument_list|()
operator|.
name|getSize
argument_list|(
name|assignment
argument_list|)
decl_stmt|;
if|if
condition|(
name|best
operator|.
name|isEmpty
argument_list|()
operator|||
name|bestValue
operator|<
name|value
condition|)
block|{
name|best
operator|.
name|clear
argument_list|()
expr_stmt|;
name|best
operator|.
name|add
argument_list|(
name|course
argument_list|)
expr_stmt|;
name|bestValue
operator|=
name|value
expr_stmt|;
block|}
if|else if
condition|(
name|bestValue
operator|==
name|value
condition|)
block|{
name|best
operator|.
name|add
argument_list|(
name|course
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
operator|!
name|best
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
name|ToolBox
operator|.
name|random
argument_list|(
name|best
argument_list|)
return|;
block|}
if|if
condition|(
name|iWheel
operator|==
literal|null
operator|||
operator|!
name|iWheel
operator|.
name|hasMoreElements
argument_list|()
condition|)
block|{
name|iWheel
operator|=
operator|new
name|RouletteWheelSelection
argument_list|<
name|CurVariable
argument_list|>
argument_list|()
expr_stmt|;
for|for
control|(
name|CurVariable
name|course
range|:
name|m
operator|.
name|assignedVariables
argument_list|(
name|assignment
argument_list|)
control|)
block|{
name|double
name|penalty
init|=
name|assignment
operator|.
name|getValue
argument_list|(
name|course
argument_list|)
operator|.
name|toDouble
argument_list|(
name|assignment
argument_list|)
decl_stmt|;
if|if
condition|(
name|course
operator|.
name|getCourse
argument_list|()
operator|.
name|getStudents
argument_list|(
name|assignment
argument_list|)
operator|.
name|size
argument_list|()
operator|==
name|m
operator|.
name|getStudents
argument_list|()
operator|.
name|size
argument_list|()
condition|)
continue|continue;
if|if
condition|(
name|penalty
operator|!=
literal|0
condition|)
name|iWheel
operator|.
name|add
argument_list|(
name|course
argument_list|,
name|penalty
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|iWheel
operator|.
name|nextElement
argument_list|()
return|;
block|}
block|}
end_class

end_unit

