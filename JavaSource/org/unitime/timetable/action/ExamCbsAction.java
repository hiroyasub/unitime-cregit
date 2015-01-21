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
name|form
operator|.
name|ExamCbsForm
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
name|exam
operator|.
name|ExamSolverProxy
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
name|exam
operator|.
name|ui
operator|.
name|ExamConflictStatisticsInfo
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

begin_comment
comment|/**   * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|Service
argument_list|(
literal|"/ecbs"
argument_list|)
specifier|public
class|class
name|ExamCbsAction
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
name|ExamSolverProxy
argument_list|>
name|examinationSolverService
decl_stmt|;
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
name|ExamCbsForm
name|myForm
init|=
operator|(
name|ExamCbsForm
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
name|ExaminationConflictStatistics
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
if|if
condition|(
name|op
operator|==
literal|null
condition|)
name|op
operator|=
literal|"Refresh"
expr_stmt|;
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
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|setProperty
argument_list|(
literal|"Ecbs.limit"
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|myForm
operator|.
name|getLimit
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|setProperty
argument_list|(
literal|"Ecbs.type"
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|myForm
operator|.
name|getTypeInt
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|myForm
operator|.
name|reset
argument_list|(
name|mapping
argument_list|,
name|request
argument_list|)
expr_stmt|;
name|myForm
operator|.
name|setTypeInt
argument_list|(
name|Integer
operator|.
name|parseInt
argument_list|(
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getProperty
argument_list|(
literal|"Ecbs.type"
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|ExamCbsForm
operator|.
name|sDefaultType
argument_list|)
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|myForm
operator|.
name|setLimit
argument_list|(
name|Double
operator|.
name|parseDouble
argument_list|(
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getProperty
argument_list|(
literal|"Ecbs.limit"
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|ExamCbsForm
operator|.
name|sDefaultLimit
argument_list|)
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|ExamConflictStatisticsInfo
name|cbs
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|examinationSolverService
operator|.
name|getSolver
argument_list|()
operator|!=
literal|null
condition|)
name|cbs
operator|=
name|examinationSolverService
operator|.
name|getSolver
argument_list|()
operator|.
name|getCbsInfo
argument_list|()
expr_stmt|;
if|if
condition|(
name|cbs
operator|!=
literal|null
condition|)
block|{
name|request
operator|.
name|setAttribute
argument_list|(
literal|"cbs"
argument_list|,
name|cbs
argument_list|)
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|examinationSolverService
operator|.
name|getSolver
argument_list|()
operator|==
literal|null
condition|)
name|request
operator|.
name|setAttribute
argument_list|(
literal|"warning"
argument_list|,
literal|"No examination data are loaded into the solver, conflict-based statistics is not available."
argument_list|)
expr_stmt|;
else|else
name|request
operator|.
name|setAttribute
argument_list|(
literal|"warning"
argument_list|,
literal|"Conflict-based statistics is not available at the moment."
argument_list|)
expr_stmt|;
block|}
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"show"
argument_list|)
return|;
block|}
block|}
end_class

end_unit

