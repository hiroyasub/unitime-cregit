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
name|ui
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
name|Collection
import|;
end_import

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
name|defaults
operator|.
name|ApplicationProperty
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
name|ClassInstructor
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
name|Class_
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
name|Solution
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|SolutionUnassignedClassesModel
extends|extends
name|UnassignedClassesModel
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|8222974077941239586L
decl_stmt|;
specifier|public
name|SolutionUnassignedClassesModel
parameter_list|(
name|Collection
argument_list|<
name|Solution
argument_list|>
name|solutions
parameter_list|,
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|String
name|instructorFormat
parameter_list|,
name|String
modifier|...
name|prefix
parameter_list|)
block|{
name|super
argument_list|()
expr_stmt|;
name|boolean
name|checkDisplay
init|=
name|ApplicationProperty
operator|.
name|TimetableGridUseClassInstructorsCheckClassDisplayInstructors
operator|.
name|isTrue
argument_list|()
decl_stmt|;
name|boolean
name|checkLead
init|=
name|ApplicationProperty
operator|.
name|TimetableGridUseClassInstructorsCheckLead
operator|.
name|isTrue
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|solutions
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
name|Solution
name|solution
init|=
operator|(
name|Solution
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|j
init|=
name|solution
operator|.
name|getOwner
argument_list|()
operator|.
name|getNotAssignedClasses
argument_list|(
name|solution
argument_list|)
operator|.
name|iterator
argument_list|()
init|;
name|j
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Class_
name|clazz
init|=
operator|(
name|Class_
operator|)
name|j
operator|.
name|next
argument_list|()
decl_stmt|;
name|String
name|name
init|=
name|clazz
operator|.
name|getClassLabel
argument_list|(
name|ApplicationProperty
operator|.
name|SolverShowClassSufix
operator|.
name|isTrue
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|prefix
operator|!=
literal|null
operator|&&
name|prefix
operator|.
name|length
operator|>
literal|0
condition|)
block|{
name|boolean
name|hasPrefix
init|=
literal|false
decl_stmt|;
for|for
control|(
name|String
name|p
range|:
name|prefix
control|)
if|if
condition|(
name|p
operator|==
literal|null
operator|||
name|name
operator|.
name|startsWith
argument_list|(
name|p
argument_list|)
condition|)
block|{
name|hasPrefix
operator|=
literal|true
expr_stmt|;
break|break;
block|}
if|if
condition|(
operator|!
name|hasPrefix
condition|)
continue|continue;
block|}
name|List
argument_list|<
name|String
argument_list|>
name|instructors
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|checkDisplay
operator|||
name|clazz
operator|.
name|isDisplayInstructor
argument_list|()
condition|)
block|{
for|for
control|(
name|ClassInstructor
name|ci
range|:
name|clazz
operator|.
name|getClassInstructors
argument_list|()
control|)
block|{
if|if
condition|(
name|ci
operator|.
name|isLead
argument_list|()
operator|||
operator|!
name|checkLead
condition|)
name|instructors
operator|.
name|add
argument_list|(
name|ci
operator|.
name|getInstructor
argument_list|()
operator|.
name|getName
argument_list|(
name|instructorFormat
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
name|int
name|nrStudents
init|=
operator|(
operator|(
name|Number
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"select count(s) from StudentEnrollment as s where s.clazz.uniqueId=:classId and s.solution.uniqueId=:solutionId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"classId"
argument_list|,
name|clazz
operator|.
name|getUniqueId
argument_list|()
operator|.
name|longValue
argument_list|()
argument_list|)
operator|.
name|setInteger
argument_list|(
literal|"solutionId"
argument_list|,
name|solution
operator|.
name|getUniqueId
argument_list|()
operator|.
name|intValue
argument_list|()
argument_list|)
operator|.
name|uniqueResult
argument_list|()
operator|)
operator|.
name|intValue
argument_list|()
decl_stmt|;
name|rows
argument_list|()
operator|.
name|add
argument_list|(
operator|new
name|UnassignedClassRow
argument_list|(
name|clazz
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|name
argument_list|,
name|instructors
argument_list|,
name|nrStudents
argument_list|,
literal|null
argument_list|,
name|clazz
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
end_class

end_unit

