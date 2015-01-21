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
name|io
operator|.
name|Serializable
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashSet
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
name|coursett
operator|.
name|constraint
operator|.
name|JenrlConstraint
import|;
end_import

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|coursett
operator|.
name|model
operator|.
name|Lecture
import|;
end_import

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|coursett
operator|.
name|model
operator|.
name|Placement
import|;
end_import

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|coursett
operator|.
name|model
operator|.
name|TimetableModel
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
name|solver
operator|.
name|Solver
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|StudentConflictsReport
implements|implements
name|Serializable
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
specifier|private
name|HashSet
name|iGroups
init|=
operator|new
name|HashSet
argument_list|()
decl_stmt|;
specifier|public
name|StudentConflictsReport
parameter_list|(
name|Solver
name|solver
parameter_list|)
block|{
name|TimetableModel
name|model
init|=
operator|(
name|TimetableModel
operator|)
name|solver
operator|.
name|currentSolution
argument_list|()
operator|.
name|getModel
argument_list|()
decl_stmt|;
name|Assignment
argument_list|<
name|Lecture
argument_list|,
name|Placement
argument_list|>
name|assignment
init|=
name|solver
operator|.
name|currentSolution
argument_list|()
operator|.
name|getAssignment
argument_list|()
decl_stmt|;
for|for
control|(
name|JenrlConstraint
name|jenrl
range|:
name|model
operator|.
name|getJenrlConstraints
argument_list|()
control|)
block|{
if|if
condition|(
name|jenrl
operator|.
name|isInConflict
argument_list|(
name|assignment
argument_list|)
operator|&&
operator|!
name|jenrl
operator|.
name|isToBeIgnored
argument_list|()
condition|)
name|iGroups
operator|.
name|add
argument_list|(
operator|new
name|JenrlInfo
argument_list|(
name|solver
argument_list|,
name|jenrl
argument_list|)
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|Lecture
name|lecture
range|:
name|assignment
operator|.
name|assignedVariables
argument_list|()
control|)
block|{
name|iGroups
operator|.
name|addAll
argument_list|(
name|JenrlInfo
operator|.
name|getCommitedJenrlInfos
argument_list|(
name|solver
argument_list|,
name|lecture
argument_list|)
operator|.
name|values
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|model
operator|.
name|constantVariables
argument_list|()
operator|!=
literal|null
condition|)
for|for
control|(
name|Lecture
name|lecture
range|:
name|model
operator|.
name|constantVariables
argument_list|()
control|)
block|{
if|if
condition|(
name|assignment
operator|.
name|getValue
argument_list|(
name|lecture
argument_list|)
operator|!=
literal|null
condition|)
name|iGroups
operator|.
name|addAll
argument_list|(
name|JenrlInfo
operator|.
name|getCommitedJenrlInfos
argument_list|(
name|solver
argument_list|,
name|lecture
argument_list|)
operator|.
name|values
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|Set
name|getGroups
parameter_list|()
block|{
return|return
name|iGroups
return|;
block|}
block|}
end_class

end_unit

