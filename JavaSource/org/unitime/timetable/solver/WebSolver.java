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
name|solver
package|;
end_package

begin_import
import|import
name|java
operator|.
name|text
operator|.
name|SimpleDateFormat
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
name|HttpSession
import|;
end_import

begin_import
import|import
name|org
operator|.
name|springframework
operator|.
name|web
operator|.
name|context
operator|.
name|WebApplicationContext
import|;
end_import

begin_import
import|import
name|org
operator|.
name|springframework
operator|.
name|web
operator|.
name|context
operator|.
name|support
operator|.
name|WebApplicationContextUtils
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
name|studentsct
operator|.
name|StudentSolverProxy
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|WebSolver
block|{
specifier|public
specifier|static
name|SimpleDateFormat
name|sDF
init|=
operator|new
name|SimpleDateFormat
argument_list|(
literal|"MM/dd/yy hh:mmaa"
argument_list|)
decl_stmt|;
specifier|private
specifier|static
name|SolverService
argument_list|<
name|SolverProxy
argument_list|>
name|getCourseTimetablingSolverService
parameter_list|(
name|HttpSession
name|session
parameter_list|)
block|{
name|WebApplicationContext
name|applicationContext
init|=
name|WebApplicationContextUtils
operator|.
name|getWebApplicationContext
argument_list|(
name|session
operator|.
name|getServletContext
argument_list|()
argument_list|)
decl_stmt|;
return|return
operator|(
name|SolverService
argument_list|<
name|SolverProxy
argument_list|>
operator|)
name|applicationContext
operator|.
name|getBean
argument_list|(
literal|"courseTimetablingSolverService"
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|SolverProxy
name|getSolver
parameter_list|(
name|javax
operator|.
name|servlet
operator|.
name|http
operator|.
name|HttpSession
name|session
parameter_list|)
block|{
return|return
name|getCourseTimetablingSolverService
argument_list|(
name|session
argument_list|)
operator|.
name|getSolver
argument_list|()
return|;
block|}
specifier|private
specifier|static
name|SolverService
argument_list|<
name|ExamSolverProxy
argument_list|>
name|getExaminationSolverService
parameter_list|(
name|HttpSession
name|session
parameter_list|)
block|{
name|WebApplicationContext
name|applicationContext
init|=
name|WebApplicationContextUtils
operator|.
name|getWebApplicationContext
argument_list|(
name|session
operator|.
name|getServletContext
argument_list|()
argument_list|)
decl_stmt|;
return|return
operator|(
name|SolverService
argument_list|<
name|ExamSolverProxy
argument_list|>
operator|)
name|applicationContext
operator|.
name|getBean
argument_list|(
literal|"examinationSolverService"
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|ExamSolverProxy
name|getExamSolver
parameter_list|(
name|javax
operator|.
name|servlet
operator|.
name|http
operator|.
name|HttpSession
name|session
parameter_list|)
block|{
return|return
name|getExaminationSolverService
argument_list|(
name|session
argument_list|)
operator|.
name|getSolver
argument_list|()
return|;
block|}
specifier|private
specifier|static
name|SolverService
argument_list|<
name|StudentSolverProxy
argument_list|>
name|getStudentSectioningSolverService
parameter_list|(
name|HttpSession
name|session
parameter_list|)
block|{
name|WebApplicationContext
name|applicationContext
init|=
name|WebApplicationContextUtils
operator|.
name|getWebApplicationContext
argument_list|(
name|session
operator|.
name|getServletContext
argument_list|()
argument_list|)
decl_stmt|;
return|return
operator|(
name|SolverService
argument_list|<
name|StudentSolverProxy
argument_list|>
operator|)
name|applicationContext
operator|.
name|getBean
argument_list|(
literal|"studentSectioningSolverService"
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|StudentSolverProxy
name|getStudentSolver
parameter_list|(
name|javax
operator|.
name|servlet
operator|.
name|http
operator|.
name|HttpSession
name|session
parameter_list|)
block|{
return|return
name|getStudentSectioningSolverService
argument_list|(
name|session
argument_list|)
operator|.
name|getSolver
argument_list|()
return|;
block|}
specifier|public
specifier|static
name|ClassAssignmentProxy
name|getClassAssignmentProxy
parameter_list|(
name|HttpSession
name|session
parameter_list|)
block|{
name|SolverProxy
name|solver
init|=
name|getSolver
argument_list|(
name|session
argument_list|)
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
name|session
operator|.
name|getAttribute
argument_list|(
literal|"Solver.selectedSolutionId"
argument_list|)
decl_stmt|;
name|Set
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
name|SolutionClassAssignmentProxy
name|cachedProxy
init|=
operator|(
name|SolutionClassAssignmentProxy
operator|)
name|session
operator|.
name|getAttribute
argument_list|(
literal|"LastSolutionClassAssignmentProxy"
argument_list|)
decl_stmt|;
if|if
condition|(
name|cachedProxy
operator|!=
literal|null
operator|&&
name|cachedProxy
operator|.
name|equals
argument_list|(
name|solutionIds
argument_list|)
condition|)
block|{
return|return
name|cachedProxy
return|;
block|}
name|SolutionClassAssignmentProxy
name|newProxy
init|=
operator|new
name|SolutionClassAssignmentProxy
argument_list|(
name|solutionIds
argument_list|)
decl_stmt|;
name|session
operator|.
name|setAttribute
argument_list|(
literal|"LastSolutionClassAssignmentProxy"
argument_list|,
name|newProxy
argument_list|)
expr_stmt|;
return|return
name|newProxy
return|;
block|}
block|}
end_class

end_unit

