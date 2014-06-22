begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 - 3.5 (University Timetabling Application)  * Copyright (C) 2008 - 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|tags
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
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
name|javax
operator|.
name|servlet
operator|.
name|jsp
operator|.
name|JspException
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|jsp
operator|.
name|tagext
operator|.
name|BodyTagSupport
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|commons
operator|.
name|Debug
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
name|model
operator|.
name|Department
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
name|ExamType
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
name|SolverGroup
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
name|SolverParameter
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
name|SubjectArea
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
name|ExamTypeDAO
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
name|model
operator|.
name|dao
operator|.
name|SolverGroupDAO
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
name|context
operator|.
name|HttpSessionContext
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
name|WebSolver
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

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|SolverWarnings
extends|extends
name|BodyTagSupport
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|7947787141769725429L
decl_stmt|;
specifier|public
name|SessionContext
name|getSessionContext
parameter_list|()
block|{
return|return
name|HttpSessionContext
operator|.
name|getSessionContext
argument_list|(
name|pageContext
operator|.
name|getServletContext
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|String
name|getSolverWarningCheckSolution
parameter_list|()
block|{
if|if
condition|(
operator|!
name|getSessionContext
argument_list|()
operator|.
name|hasPermission
argument_list|(
name|Right
operator|.
name|CourseTimetabling
argument_list|)
condition|)
return|return
literal|null
return|;
comment|// no permission
comment|// Return warning of the solver loaded in memory (if there is a running solver)
try|try
block|{
name|SolverProxy
name|proxy
init|=
name|WebSolver
operator|.
name|getSolver
argument_list|(
name|pageContext
operator|.
name|getSession
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|proxy
operator|!=
literal|null
condition|)
return|return
name|proxy
operator|.
name|getProperties
argument_list|()
operator|.
name|getProperty
argument_list|(
literal|"General.SolverWarnings"
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
block|}
comment|// Return warning of the selected solution (warning that was present when the solution was saved, if there is a solution loaded in memory)
name|String
name|id
init|=
operator|(
name|String
operator|)
name|pageContext
operator|.
name|getSession
argument_list|()
operator|.
name|getAttribute
argument_list|(
literal|"Solver.selectedSolutionId"
argument_list|)
decl_stmt|;
if|if
condition|(
name|id
operator|!=
literal|null
operator|&&
operator|!
name|id
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|String
name|warn
init|=
literal|""
decl_stmt|;
for|for
control|(
name|String
name|solutionId
range|:
name|id
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
for|for
control|(
name|SolverParameter
name|p
range|:
name|solution
operator|.
name|getParameters
argument_list|()
control|)
block|{
if|if
condition|(
literal|"General.SolverWarnings"
operator|.
name|equals
argument_list|(
name|p
operator|.
name|getDefinition
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
operator|&&
name|p
operator|.
name|getValue
argument_list|()
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
operator|!
name|warn
operator|.
name|isEmpty
argument_list|()
condition|)
name|warn
operator|+=
literal|"<br>"
expr_stmt|;
name|warn
operator|+=
name|p
operator|.
name|getValue
argument_list|()
expr_stmt|;
block|}
block|}
block|}
return|return
name|warn
operator|.
name|isEmpty
argument_list|()
condition|?
literal|null
else|:
name|warn
return|;
block|}
comment|// Compute warning from solver groups
name|Set
argument_list|<
name|SolverGroup
argument_list|>
name|solverGroups
init|=
name|SolverGroup
operator|.
name|getUserSolverGroups
argument_list|(
name|getSessionContext
argument_list|()
operator|.
name|getUser
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|solverGroups
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
literal|null
return|;
comment|// no solver groups
name|String
name|warn
init|=
literal|""
decl_stmt|;
name|int
name|maxDistPriority
init|=
name|Integer
operator|.
name|MIN_VALUE
decl_stmt|;
name|int
name|nrWarns
init|=
literal|0
decl_stmt|;
for|for
control|(
name|SolverGroup
name|sg
range|:
name|solverGroups
control|)
name|maxDistPriority
operator|=
name|Math
operator|.
name|max
argument_list|(
name|maxDistPriority
argument_list|,
name|sg
operator|.
name|getMaxDistributionPriority
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|SolverGroup
name|sg
range|:
name|SolverGroup
operator|.
name|findBySessionId
argument_list|(
name|getSessionContext
argument_list|()
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|)
control|)
block|{
if|if
condition|(
name|solverGroups
operator|.
name|contains
argument_list|(
name|sg
argument_list|)
condition|)
continue|continue;
if|if
condition|(
name|sg
operator|.
name|getMinDistributionPriority
argument_list|()
operator|<
name|maxDistPriority
operator|&&
name|sg
operator|.
name|getCommittedSolution
argument_list|()
operator|==
literal|null
condition|)
block|{
if|if
condition|(
name|nrWarns
operator|>
literal|0
condition|)
name|warn
operator|+=
literal|"<br>"
expr_stmt|;
name|warn
operator|+=
literal|"There is no "
operator|+
name|sg
operator|.
name|getAbbv
argument_list|()
operator|+
literal|" solution committed"
expr_stmt|;
name|boolean
name|dept
init|=
literal|false
decl_stmt|;
for|for
control|(
name|Department
name|d
range|:
name|sg
operator|.
name|getDepartments
argument_list|()
control|)
block|{
if|if
condition|(
name|d
operator|.
name|isExternalManager
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|)
block|{
name|warn
operator|+=
literal|", "
operator|+
name|d
operator|.
name|getExternalMgrAbbv
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|dept
operator|=
literal|true
expr_stmt|;
for|for
control|(
name|SubjectArea
name|sa
range|:
name|d
operator|.
name|getSubjectAreas
argument_list|()
control|)
name|warn
operator|+=
literal|", "
operator|+
name|sa
operator|.
name|getSubjectAreaAbbreviation
argument_list|()
expr_stmt|;
block|}
block|}
name|warn
operator|+=
operator|(
name|dept
condition|?
literal|", departmental"
else|:
literal|""
operator|)
operator|+
literal|" classes are not considered."
expr_stmt|;
name|nrWarns
operator|++
expr_stmt|;
if|if
condition|(
name|nrWarns
operator|>=
literal|3
condition|)
block|{
name|warn
operator|+=
literal|"<br>..."
expr_stmt|;
break|break;
block|}
block|}
block|}
return|return
name|warn
operator|.
name|isEmpty
argument_list|()
condition|?
literal|null
else|:
name|warn
return|;
block|}
specifier|public
name|String
index|[]
name|getCurrentAssignmentWarning
parameter_list|()
block|{
if|if
condition|(
operator|!
name|getSessionContext
argument_list|()
operator|.
name|hasPermission
argument_list|(
name|Right
operator|.
name|Timetables
argument_list|)
condition|)
return|return
literal|null
return|;
comment|// no permission
comment|// Return warning of the solver loaded in memory (if there is a running solver)
try|try
block|{
name|SolverProxy
name|proxy
init|=
name|WebSolver
operator|.
name|getSolver
argument_list|(
name|pageContext
operator|.
name|getSession
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|proxy
operator|!=
literal|null
condition|)
block|{
name|Long
index|[]
name|solverGroupId
init|=
name|proxy
operator|.
name|getProperties
argument_list|()
operator|.
name|getPropertyLongArry
argument_list|(
literal|"General.SolverGroupId"
argument_list|,
literal|null
argument_list|)
decl_stmt|;
name|String
name|names
init|=
literal|""
decl_stmt|;
name|boolean
name|interactive
init|=
name|proxy
operator|.
name|getProperties
argument_list|()
operator|.
name|getPropertyBoolean
argument_list|(
literal|"General.InteractiveMode"
argument_list|,
literal|false
argument_list|)
decl_stmt|;
if|if
condition|(
name|solverGroupId
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|solverGroupId
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|SolverGroup
name|sg
init|=
name|SolverGroupDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|solverGroupId
index|[
name|i
index|]
argument_list|)
decl_stmt|;
if|if
condition|(
name|i
operator|>
literal|0
operator|&&
name|i
operator|+
literal|1
operator|==
name|solverGroupId
operator|.
name|length
condition|)
name|names
operator|+=
operator|(
name|solverGroupId
operator|.
name|length
operator|==
literal|2
condition|?
literal|" and "
else|:
literal|", and "
operator|)
expr_stmt|;
if|else if
condition|(
name|i
operator|>
literal|0
condition|)
name|names
operator|+=
literal|", "
expr_stmt|;
name|names
operator|+=
operator|(
name|sg
operator|==
literal|null
condition|?
literal|"N/A"
else|:
name|solverGroupId
operator|.
name|length
operator|<=
literal|3
condition|?
name|sg
operator|.
name|getName
argument_list|()
else|:
name|sg
operator|.
name|getAbbv
argument_list|()
operator|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|names
operator|==
literal|null
operator|||
name|names
operator|.
name|isEmpty
argument_list|()
condition|)
name|names
operator|=
literal|"N/A"
expr_stmt|;
return|return
operator|new
name|String
index|[]
block|{
name|interactive
condition|?
literal|"listSolutions.do"
else|:
literal|"solver.do"
block|,
literal|"Showing an in-memory solution for "
operator|+
name|names
operator|+
literal|"."
block|}
return|;
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
block|}
comment|// Return warning of the selected solution (warning that was present when the solution was saved, if there is a solution loaded in memory)
name|String
name|id
init|=
operator|(
name|String
operator|)
name|pageContext
operator|.
name|getSession
argument_list|()
operator|.
name|getAttribute
argument_list|(
literal|"Solver.selectedSolutionId"
argument_list|)
decl_stmt|;
if|if
condition|(
name|id
operator|!=
literal|null
operator|&&
operator|!
name|id
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|String
name|names
init|=
literal|""
decl_stmt|;
name|String
index|[]
name|solutionIds
init|=
name|id
operator|.
name|split
argument_list|(
literal|","
argument_list|)
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
name|solutionIds
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
name|solutionIds
index|[
name|i
index|]
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|i
operator|>
literal|0
operator|&&
name|i
operator|+
literal|1
operator|==
name|solutionIds
operator|.
name|length
condition|)
name|names
operator|+=
operator|(
name|solutionIds
operator|.
name|length
operator|==
literal|2
condition|?
literal|" and "
else|:
literal|", and "
operator|)
expr_stmt|;
if|else if
condition|(
name|i
operator|>
literal|0
condition|)
name|names
operator|+=
literal|", "
expr_stmt|;
name|names
operator|+=
operator|(
name|solutionIds
operator|.
name|length
operator|<=
literal|3
condition|?
name|solution
operator|.
name|getOwner
argument_list|()
operator|.
name|getName
argument_list|()
else|:
name|solution
operator|.
name|getOwner
argument_list|()
operator|.
name|getAbbv
argument_list|()
operator|)
expr_stmt|;
block|}
return|return
operator|new
name|String
index|[]
block|{
literal|"listSolutions.do"
block|,
name|names
operator|.
name|isEmpty
argument_list|()
condition|?
literal|null
else|:
name|solutionIds
operator|.
name|length
operator|==
literal|1
condition|?
literal|"Showing a selected solution for "
operator|+
name|names
operator|+
literal|"."
else|:
literal|"Showing selected solutions for "
operator|+
name|names
operator|+
literal|"."
block|}
return|;
block|}
return|return
literal|null
return|;
block|}
specifier|public
name|String
index|[]
name|getExamAssignmentWarning
parameter_list|(
name|boolean
name|checkExamType
parameter_list|)
block|{
if|if
condition|(
operator|!
name|getSessionContext
argument_list|()
operator|.
name|hasPermission
argument_list|(
name|Right
operator|.
name|ExaminationSolver
argument_list|)
condition|)
return|return
literal|null
return|;
comment|// no permission
comment|// Return warning of the solver loaded in memory (if there is a running solver)
try|try
block|{
name|ExamSolverProxy
name|proxy
init|=
name|WebSolver
operator|.
name|getExamSolver
argument_list|(
name|pageContext
operator|.
name|getSession
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|proxy
operator|!=
literal|null
condition|)
block|{
name|ExamType
name|type
init|=
name|ExamTypeDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|proxy
operator|.
name|getExamTypeId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|type
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|checkExamType
condition|)
block|{
name|Long
name|selectedExamTypeId
init|=
operator|(
name|Long
operator|)
name|getSessionContext
argument_list|()
operator|.
name|getAttribute
argument_list|(
name|SessionAttribute
operator|.
name|ExamType
argument_list|)
decl_stmt|;
if|if
condition|(
name|selectedExamTypeId
operator|!=
literal|null
operator|&&
operator|!
name|selectedExamTypeId
operator|.
name|equals
argument_list|(
name|type
operator|.
name|getUniqueId
argument_list|()
argument_list|)
condition|)
return|return
literal|null
return|;
block|}
return|return
operator|new
name|String
index|[]
block|{
literal|"examSolver.do"
block|,
literal|"Showing an in-memory solution for "
operator|+
name|type
operator|.
name|getLabel
argument_list|()
operator|+
literal|" Examinations."
block|}
return|;
block|}
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
block|}
return|return
literal|null
return|;
block|}
specifier|private
name|void
name|printWarning
parameter_list|(
name|String
name|style
parameter_list|,
name|String
name|message
parameter_list|,
name|String
name|link
parameter_list|)
throws|throws
name|IOException
block|{
if|if
condition|(
name|message
operator|!=
literal|null
operator|&&
operator|!
name|message
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|pageContext
operator|.
name|getOut
argument_list|()
operator|.
name|println
argument_list|(
literal|"<table width='100%' border='0' cellpadding='3' cellspacing='0'><tr>"
argument_list|)
expr_stmt|;
name|pageContext
operator|.
name|getOut
argument_list|()
operator|.
name|println
argument_list|(
literal|"<td class=\""
operator|+
name|style
operator|+
literal|"\" style='padding-left:10px' "
operator|+
operator|(
name|link
operator|==
literal|null
condition|?
literal|""
else|:
literal|"onMouseOver=\"this.style.backgroundColor='#BBCDD0';\" onMouseOut=\"this.style.backgroundColor='#DFE7F2';\""
operator|)
operator|+
literal|">"
argument_list|)
expr_stmt|;
if|if
condition|(
name|link
operator|!=
literal|null
condition|)
name|pageContext
operator|.
name|getOut
argument_list|()
operator|.
name|print
argument_list|(
literal|"<a class='noFancyLinks' href=\""
operator|+
name|link
operator|+
literal|"\">"
argument_list|)
expr_stmt|;
name|pageContext
operator|.
name|getOut
argument_list|()
operator|.
name|println
argument_list|(
name|message
argument_list|)
expr_stmt|;
if|if
condition|(
name|link
operator|!=
literal|null
condition|)
name|pageContext
operator|.
name|getOut
argument_list|()
operator|.
name|print
argument_list|(
literal|"</a>"
argument_list|)
expr_stmt|;
name|pageContext
operator|.
name|getOut
argument_list|()
operator|.
name|println
argument_list|(
literal|"</td></tr></table>"
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|int
name|doStartTag
parameter_list|()
block|{
return|return
name|EVAL_BODY_BUFFERED
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|doEndTag
parameter_list|()
throws|throws
name|JspException
block|{
try|try
block|{
name|String
name|body
init|=
operator|(
name|getBodyContent
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
name|getBodyContent
argument_list|()
operator|.
name|getString
argument_list|()
operator|.
name|trim
argument_list|()
operator|)
decl_stmt|;
if|if
condition|(
name|body
operator|==
literal|null
operator|||
name|body
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
name|EVAL_PAGE
return|;
if|if
condition|(
literal|"solver"
operator|.
name|equals
argument_list|(
name|body
argument_list|)
condition|)
block|{
name|String
name|warn
init|=
name|getSolverWarningCheckSolution
argument_list|()
decl_stmt|;
if|if
condition|(
name|warn
operator|!=
literal|null
condition|)
name|printWarning
argument_list|(
literal|"unitime-MessageYellow"
argument_list|,
name|warn
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|String
index|[]
name|awarn
init|=
name|getCurrentAssignmentWarning
argument_list|()
decl_stmt|;
if|if
condition|(
name|awarn
operator|!=
literal|null
condition|)
name|printWarning
argument_list|(
literal|"unitime-MessageBlue"
argument_list|,
name|awarn
index|[
literal|1
index|]
argument_list|,
name|awarn
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
literal|"assignment"
operator|.
name|equals
argument_list|(
name|body
argument_list|)
condition|)
block|{
name|String
index|[]
name|awarn
init|=
name|getCurrentAssignmentWarning
argument_list|()
decl_stmt|;
if|if
condition|(
name|awarn
operator|!=
literal|null
condition|)
block|{
name|printWarning
argument_list|(
literal|"unitime-MessageBlue"
argument_list|,
name|awarn
index|[
literal|1
index|]
argument_list|,
name|awarn
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
block|}
name|String
index|[]
name|xwarn
init|=
name|getExamAssignmentWarning
argument_list|(
literal|false
argument_list|)
decl_stmt|;
if|if
condition|(
name|xwarn
operator|!=
literal|null
condition|)
name|printWarning
argument_list|(
literal|"unitime-MessageBlue"
argument_list|,
name|xwarn
index|[
literal|1
index|]
argument_list|,
name|xwarn
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
literal|"exams"
operator|.
name|equals
argument_list|(
name|body
argument_list|)
condition|)
block|{
name|String
index|[]
name|awarn
init|=
name|getExamAssignmentWarning
argument_list|(
literal|true
argument_list|)
decl_stmt|;
if|if
condition|(
name|awarn
operator|!=
literal|null
condition|)
name|printWarning
argument_list|(
literal|"unitime-MessageBlue"
argument_list|,
name|awarn
index|[
literal|1
index|]
argument_list|,
name|awarn
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|Debug
operator|.
name|error
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
return|return
name|EVAL_PAGE
return|;
block|}
block|}
end_class

end_unit

