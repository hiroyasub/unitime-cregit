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
name|model
operator|.
name|GlobalConstraint
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
name|CurStudentLimit
extends|extends
name|GlobalConstraint
argument_list|<
name|CurVariable
argument_list|,
name|CurValue
argument_list|>
block|{
specifier|private
name|int
name|iMinLimit
init|=
literal|0
decl_stmt|,
name|iMaxLimit
init|=
literal|0
decl_stmt|;
specifier|public
name|CurStudentLimit
parameter_list|(
name|int
name|minLimit
parameter_list|,
name|int
name|maxLimit
parameter_list|)
block|{
name|iMinLimit
operator|=
name|minLimit
expr_stmt|;
name|iMaxLimit
operator|=
name|maxLimit
expr_stmt|;
block|}
specifier|public
name|int
name|getMinLimit
parameter_list|()
block|{
return|return
name|iMinLimit
return|;
block|}
specifier|public
name|int
name|getMaxLimit
parameter_list|()
block|{
return|return
name|iMaxLimit
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|computeConflicts
parameter_list|(
name|Assignment
argument_list|<
name|CurVariable
argument_list|,
name|CurValue
argument_list|>
name|assignment
parameter_list|,
name|CurValue
name|value
parameter_list|,
name|Set
argument_list|<
name|CurValue
argument_list|>
name|conflicts
parameter_list|)
block|{
name|Set
argument_list|<
name|CurCourse
argument_list|>
name|courses
init|=
name|value
operator|.
name|getStudent
argument_list|()
operator|.
name|getCourses
argument_list|(
name|assignment
argument_list|)
decl_stmt|;
name|int
name|nrCourses
init|=
name|courses
operator|.
name|size
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|courses
operator|.
name|contains
argument_list|(
name|value
operator|.
name|variable
argument_list|()
operator|.
name|getCourse
argument_list|()
argument_list|)
condition|)
name|nrCourses
operator|++
expr_stmt|;
for|for
control|(
name|CurValue
name|conflict
range|:
name|conflicts
control|)
block|{
if|if
condition|(
name|conflict
operator|.
name|getStudent
argument_list|()
operator|.
name|equals
argument_list|(
name|value
operator|.
name|getStudent
argument_list|()
argument_list|)
operator|&&
name|courses
operator|.
name|contains
argument_list|(
name|conflict
operator|.
name|variable
argument_list|()
operator|.
name|getCourse
argument_list|()
argument_list|)
condition|)
name|nrCourses
operator|--
expr_stmt|;
block|}
if|if
condition|(
name|nrCourses
operator|>
name|iMaxLimit
condition|)
block|{
name|List
argument_list|<
name|CurValue
argument_list|>
name|adepts
init|=
operator|new
name|ArrayList
argument_list|<
name|CurValue
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|CurCourse
name|course
range|:
name|courses
control|)
block|{
if|if
condition|(
name|course
operator|.
name|equals
argument_list|(
name|value
operator|.
name|variable
argument_list|()
operator|.
name|getCourse
argument_list|()
argument_list|)
condition|)
continue|continue;
name|CurValue
name|adept
init|=
name|course
operator|.
name|getValue
argument_list|(
name|assignment
argument_list|,
name|value
operator|.
name|getStudent
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|conflicts
operator|.
name|contains
argument_list|(
name|adept
argument_list|)
condition|)
continue|continue;
name|adepts
operator|.
name|add
argument_list|(
name|adept
argument_list|)
expr_stmt|;
block|}
name|conflicts
operator|.
name|add
argument_list|(
name|ToolBox
operator|.
name|random
argument_list|(
name|adepts
argument_list|)
argument_list|)
expr_stmt|;
name|nrCourses
operator|--
expr_stmt|;
block|}
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
literal|"StudentLimit<"
operator|+
name|getMinLimit
argument_list|()
operator|+
literal|","
operator|+
name|getMaxLimit
argument_list|()
operator|+
literal|">"
return|;
block|}
block|}
end_class

end_unit

