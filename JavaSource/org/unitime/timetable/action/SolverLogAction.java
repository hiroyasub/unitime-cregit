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
name|action
package|;
end_package

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
name|javax
operator|.
name|servlet
operator|.
name|http
operator|.
name|HttpServletRequest
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|http
operator|.
name|HttpServletResponse
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|Action
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionForm
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionForward
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionMapping
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
name|form
operator|.
name|SolverLogForm
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
name|dao
operator|.
name|SolutionDAO
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
name|security
operator|.
name|rights
operator|.
name|Right
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
name|service
operator|.
name|SolverService
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
name|ui
operator|.
name|LogInfo
import|;
end_import

begin_comment
comment|/**   * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|Service
argument_list|(
literal|"/solverLog"
argument_list|)
specifier|public
class|class
name|SolverLogAction
extends|extends
name|Action
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
comment|// --------------------------------------------------------- Instance Variables
comment|// --------------------------------------------------------- Methods
specifier|public
name|ActionForward
name|execute
parameter_list|(
name|ActionMapping
name|mapping
parameter_list|,
name|ActionForm
name|form
parameter_list|,
name|HttpServletRequest
name|request
parameter_list|,
name|HttpServletResponse
name|response
parameter_list|)
throws|throws
name|Exception
block|{
name|SolverLogForm
name|myForm
init|=
operator|(
name|SolverLogForm
operator|)
name|form
decl_stmt|;
comment|// Check Access
name|sessionContext
operator|.
name|checkPermission
argument_list|(
name|Right
operator|.
name|SolverLog
argument_list|)
expr_stmt|;
comment|// Read operation to be performed
name|String
name|op
init|=
operator|(
name|myForm
operator|.
name|getOp
argument_list|()
operator|!=
literal|null
condition|?
name|myForm
operator|.
name|getOp
argument_list|()
else|:
name|request
operator|.
name|getParameter
argument_list|(
literal|"op"
argument_list|)
operator|)
decl_stmt|;
comment|// Change log level
if|if
condition|(
literal|"Change"
operator|.
name|equals
argument_list|(
name|op
argument_list|)
condition|)
block|{
if|if
condition|(
name|myForm
operator|.
name|getLevelNoDefault
argument_list|()
operator|!=
literal|null
condition|)
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|setProperty
argument_list|(
literal|"SolverLog.level"
argument_list|,
name|myForm
operator|.
name|getLevelNoDefault
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|myForm
operator|.
name|setLevel
argument_list|(
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getProperty
argument_list|(
literal|"SolverLog.level"
argument_list|)
argument_list|)
expr_stmt|;
block|}
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
block|{
name|solver
operator|.
name|setDebugLevel
argument_list|(
name|myForm
operator|.
name|getLevelInt
argument_list|()
argument_list|)
expr_stmt|;
name|request
operator|.
name|setAttribute
argument_list|(
literal|"log"
argument_list|,
name|solver
operator|.
name|getLog
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
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
if|if
condition|(
name|solutionIdsStr
operator|!=
literal|null
operator|&&
operator|!
name|solutionIdsStr
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
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
decl_stmt|;
name|LogInfo
index|[]
name|logInfo
init|=
operator|new
name|LogInfo
index|[
name|s
operator|.
name|countTokens
argument_list|()
index|]
decl_stmt|;
name|String
index|[]
name|ownerName
init|=
operator|new
name|String
index|[
name|s
operator|.
name|countTokens
argument_list|()
index|]
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
name|logInfo
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|Solution
name|solution
init|=
operator|(
operator|new
name|SolutionDAO
argument_list|()
operator|)
operator|.
name|get
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
decl_stmt|;
if|if
condition|(
name|solution
operator|!=
literal|null
condition|)
block|{
name|logInfo
index|[
name|i
index|]
operator|=
operator|(
name|LogInfo
operator|)
name|solution
operator|.
name|getInfo
argument_list|(
literal|"LogInfo"
argument_list|)
expr_stmt|;
name|ownerName
index|[
name|i
index|]
operator|=
name|solution
operator|.
name|getOwner
argument_list|()
operator|.
name|getName
argument_list|()
expr_stmt|;
block|}
block|}
name|myForm
operator|.
name|setLogs
argument_list|(
name|logInfo
argument_list|)
expr_stmt|;
name|myForm
operator|.
name|setOwnerNames
argument_list|(
name|ownerName
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"showLog"
argument_list|)
return|;
block|}
block|}
end_class

end_unit

