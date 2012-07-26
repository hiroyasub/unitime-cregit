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
name|CbsForm
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
name|ConflictStatisticsInfo
import|;
end_import

begin_comment
comment|/**   * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|Service
argument_list|(
literal|"/cbs"
argument_list|)
specifier|public
class|class
name|CbsAction
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
name|CbsForm
name|myForm
init|=
operator|(
name|CbsForm
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
name|ConflictStatistics
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
literal|"Refresh"
operator|.
name|equals
argument_list|(
name|op
argument_list|)
condition|)
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
block|}
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
literal|"Cbs.limit"
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
literal|"Cbs.type"
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
literal|"Cbs.limit"
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|CbsForm
operator|.
name|sDefaultLimit
argument_list|)
argument_list|)
argument_list|)
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
literal|"Cbs.type"
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|CbsForm
operator|.
name|sDefaultType
argument_list|)
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|ConflictStatisticsInfo
name|cbs
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|courseTimetablingSolverService
operator|.
name|getSolver
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|cbs
operator|=
name|courseTimetablingSolverService
operator|.
name|getSolver
argument_list|()
operator|.
name|getCbsInfo
argument_list|()
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
condition|)
block|{
for|for
control|(
name|String
name|solutionId
range|:
name|solutionIdsStr
operator|.
name|split
argument_list|(
literal|","
argument_list|)
control|)
block|{
name|Solution
name|solution
init|=
name|SolutionDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|solutionId
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
name|ConflictStatisticsInfo
name|x
init|=
operator|(
name|ConflictStatisticsInfo
operator|)
name|solution
operator|.
name|getInfo
argument_list|(
literal|"CBSInfo"
argument_list|)
decl_stmt|;
if|if
condition|(
name|x
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|cbs
operator|==
literal|null
condition|)
name|cbs
operator|=
name|x
expr_stmt|;
else|else
name|cbs
operator|.
name|merge
argument_list|(
name|x
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
block|}
if|if
condition|(
name|cbs
operator|!=
literal|null
condition|)
name|request
operator|.
name|setAttribute
argument_list|(
literal|"cbs"
argument_list|,
name|cbs
argument_list|)
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"showCbs"
argument_list|)
return|;
block|}
block|}
end_class

end_unit

