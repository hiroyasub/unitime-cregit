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
name|service
package|;
end_package

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
name|StringTokenizer
import|;
end_import

begin_import
import|import
name|org
operator|.
name|springframework
operator|.
name|beans
operator|.
name|factory
operator|.
name|annotation
operator|.
name|Autowired
import|;
end_import

begin_import
import|import
name|org
operator|.
name|springframework
operator|.
name|stereotype
operator|.
name|Service
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
name|SessionAttribute
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
name|security
operator|.
name|SessionContext
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
name|solver
operator|.
name|CachedClassAssignmentProxy
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
name|solver
operator|.
name|ClassAssignmentProxy
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
name|solver
operator|.
name|SolutionClassAssignmentProxy
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
name|solver
operator|.
name|SolverProxy
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|Service
argument_list|(
literal|"classAssignmentService"
argument_list|)
specifier|public
class|class
name|ClassAssignmentService
implements|implements
name|AssignmentService
argument_list|<
name|ClassAssignmentProxy
argument_list|>
block|{
annotation|@
name|Autowired
name|SessionContext
name|sessionContext
decl_stmt|;
annotation|@
name|Autowired
name|SolverService
argument_list|<
name|SolverProxy
argument_list|>
name|courseTimetablingSolverService
decl_stmt|;
annotation|@
name|Override
specifier|public
name|ClassAssignmentProxy
name|getAssignment
parameter_list|()
block|{
name|SolverProxy
name|solver
init|=
name|courseTimetablingSolverService
operator|.
name|getSolver
argument_list|()
decl_stmt|;
if|if
condition|(
name|solver
operator|!=
literal|null
condition|)
return|return
operator|new
name|CachedClassAssignmentProxy
argument_list|(
name|solver
argument_list|)
return|;
name|String
name|solutionIdsStr
init|=
operator|(
name|String
operator|)
name|sessionContext
operator|.
name|getAttribute
argument_list|(
name|SessionAttribute
operator|.
name|SelectedSolution
argument_list|)
decl_stmt|;
name|HashSet
argument_list|<
name|Long
argument_list|>
name|solutionIds
init|=
operator|new
name|HashSet
argument_list|<
name|Long
argument_list|>
argument_list|()
decl_stmt|;
if|if
condition|(
name|solutionIdsStr
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|StringTokenizer
name|s
init|=
operator|new
name|StringTokenizer
argument_list|(
name|solutionIdsStr
argument_list|,
literal|","
argument_list|)
init|;
name|s
operator|.
name|hasMoreTokens
argument_list|()
condition|;
control|)
name|solutionIds
operator|.
name|add
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|s
operator|.
name|nextToken
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|ProxyHolder
argument_list|<
name|HashSet
argument_list|<
name|Long
argument_list|>
argument_list|,
name|SolutionClassAssignmentProxy
argument_list|>
name|h
init|=
operator|(
name|ProxyHolder
argument_list|<
name|HashSet
argument_list|<
name|Long
argument_list|>
argument_list|,
name|SolutionClassAssignmentProxy
argument_list|>
operator|)
name|sessionContext
operator|.
name|getAttribute
argument_list|(
name|SessionAttribute
operator|.
name|ClassAssignment
argument_list|)
decl_stmt|;
if|if
condition|(
name|h
operator|!=
literal|null
operator|&&
name|h
operator|.
name|isValid
argument_list|(
name|solutionIds
argument_list|)
condition|)
return|return
name|h
operator|.
name|getProxy
argument_list|()
return|;
name|SolutionClassAssignmentProxy
name|newProxy
init|=
operator|new
name|SolutionClassAssignmentProxy
argument_list|(
name|solutionIds
argument_list|)
decl_stmt|;
name|sessionContext
operator|.
name|setAttribute
argument_list|(
name|SessionAttribute
operator|.
name|ClassAssignment
argument_list|,
operator|new
name|ProxyHolder
argument_list|<
name|HashSet
argument_list|<
name|Long
argument_list|>
argument_list|,
name|SolutionClassAssignmentProxy
argument_list|>
argument_list|(
name|solutionIds
argument_list|,
name|newProxy
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|newProxy
return|;
block|}
block|}
end_class

end_unit

