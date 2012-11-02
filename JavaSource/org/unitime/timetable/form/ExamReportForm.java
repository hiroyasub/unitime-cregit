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
name|form
package|;
end_package

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
name|TreeSet
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
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionErrors
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
name|ActionMapping
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
name|SubjectAreaDAO
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
comment|/**   * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|ExamReportForm
extends|extends
name|ActionForm
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
operator|-
literal|8009733200124355056L
decl_stmt|;
specifier|private
name|String
name|iOp
init|=
literal|null
decl_stmt|;
specifier|private
name|boolean
name|iShowSections
init|=
literal|false
decl_stmt|;
specifier|private
name|Long
name|iSubjectArea
init|=
literal|null
decl_stmt|;
specifier|private
name|Collection
name|iSubjectAreas
init|=
literal|null
decl_stmt|;
specifier|private
name|String
name|iTable
init|=
literal|null
decl_stmt|;
specifier|private
name|int
name|iNrColumns
decl_stmt|;
specifier|private
name|int
name|iNrRows
decl_stmt|;
specifier|private
name|Long
name|iExamType
decl_stmt|;
specifier|public
name|ActionErrors
name|validate
parameter_list|(
name|ActionMapping
name|mapping
parameter_list|,
name|HttpServletRequest
name|request
parameter_list|)
block|{
name|ActionErrors
name|errors
init|=
operator|new
name|ActionErrors
argument_list|()
decl_stmt|;
return|return
name|errors
return|;
block|}
specifier|public
name|void
name|reset
parameter_list|(
name|ActionMapping
name|mapping
parameter_list|,
name|HttpServletRequest
name|request
parameter_list|)
block|{
name|iOp
operator|=
literal|null
expr_stmt|;
name|iShowSections
operator|=
literal|false
expr_stmt|;
name|iTable
operator|=
literal|null
expr_stmt|;
name|iNrRows
operator|=
name|iNrColumns
operator|=
literal|0
expr_stmt|;
name|iExamType
operator|=
literal|null
expr_stmt|;
name|ExamSolverProxy
name|solver
init|=
name|WebSolver
operator|.
name|getExamSolver
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|)
decl_stmt|;
try|try
block|{
if|if
condition|(
name|solver
operator|!=
literal|null
condition|)
name|iExamType
operator|=
name|solver
operator|.
name|getProperties
argument_list|()
operator|.
name|getPropertyLong
argument_list|(
literal|"Exam.Type"
argument_list|,
name|iExamType
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
block|}
block|}
specifier|public
name|String
name|getOp
parameter_list|()
block|{
return|return
name|iOp
return|;
block|}
specifier|public
name|void
name|setOp
parameter_list|(
name|String
name|op
parameter_list|)
block|{
name|iOp
operator|=
name|op
expr_stmt|;
block|}
specifier|public
name|boolean
name|getShowSections
parameter_list|()
block|{
return|return
name|iShowSections
return|;
block|}
specifier|public
name|void
name|setShowSections
parameter_list|(
name|boolean
name|showSections
parameter_list|)
block|{
name|iShowSections
operator|=
name|showSections
expr_stmt|;
block|}
specifier|public
name|Long
name|getSubjectArea
parameter_list|()
block|{
return|return
name|iSubjectArea
return|;
block|}
specifier|public
name|String
name|getSubjectAreaAbbv
parameter_list|()
block|{
return|return
operator|new
name|SubjectAreaDAO
argument_list|()
operator|.
name|get
argument_list|(
name|iSubjectArea
argument_list|)
operator|.
name|getSubjectAreaAbbreviation
argument_list|()
return|;
block|}
specifier|public
name|void
name|setSubjectArea
parameter_list|(
name|Long
name|subjectArea
parameter_list|)
block|{
name|iSubjectArea
operator|=
name|subjectArea
expr_stmt|;
block|}
specifier|public
name|Collection
name|getSubjectAreas
parameter_list|()
block|{
return|return
name|iSubjectAreas
return|;
block|}
specifier|public
name|void
name|setSubjectAreas
parameter_list|(
name|Collection
name|subjectAreas
parameter_list|)
block|{
name|iSubjectAreas
operator|=
name|subjectAreas
expr_stmt|;
block|}
specifier|public
name|void
name|load
parameter_list|(
name|SessionContext
name|session
parameter_list|)
block|{
name|setShowSections
argument_list|(
literal|"1"
operator|.
name|equals
argument_list|(
name|session
operator|.
name|getUser
argument_list|()
operator|.
name|getProperty
argument_list|(
literal|"ExamReport.showSections"
argument_list|,
literal|"1"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|setSubjectArea
argument_list|(
name|session
operator|.
name|getAttribute
argument_list|(
literal|"ExamReport.subjectArea"
argument_list|)
operator|==
literal|null
condition|?
literal|null
else|:
operator|(
name|Long
operator|)
name|session
operator|.
name|getAttribute
argument_list|(
literal|"ExamReport.subjectArea"
argument_list|)
argument_list|)
expr_stmt|;
try|try
block|{
name|iSubjectAreas
operator|=
operator|new
name|TreeSet
argument_list|(
operator|new
name|SubjectAreaDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select distinct o.course.subjectArea from Exam x inner join x.owners o where "
operator|+
literal|"x.session.uniqueId=:sessionId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|session
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
block|}
name|setExamType
argument_list|(
name|session
operator|.
name|getAttribute
argument_list|(
literal|"Exam.Type"
argument_list|)
operator|==
literal|null
condition|?
name|iExamType
else|:
operator|(
name|Long
operator|)
name|session
operator|.
name|getAttribute
argument_list|(
literal|"Exam.Type"
argument_list|)
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|save
parameter_list|(
name|SessionContext
name|session
parameter_list|)
block|{
name|session
operator|.
name|getUser
argument_list|()
operator|.
name|setProperty
argument_list|(
literal|"ExamReport.showSections"
argument_list|,
name|getShowSections
argument_list|()
condition|?
literal|"1"
else|:
literal|"0"
argument_list|)
expr_stmt|;
if|if
condition|(
name|getSubjectArea
argument_list|()
operator|==
literal|null
condition|)
name|session
operator|.
name|removeAttribute
argument_list|(
literal|"ExamReport.subjectArea"
argument_list|)
expr_stmt|;
else|else
name|session
operator|.
name|setAttribute
argument_list|(
literal|"ExamReport.subjectArea"
argument_list|,
name|getSubjectArea
argument_list|()
argument_list|)
expr_stmt|;
name|session
operator|.
name|setAttribute
argument_list|(
literal|"Exam.Type"
argument_list|,
name|getExamType
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|setTable
parameter_list|(
name|String
name|table
parameter_list|,
name|int
name|cols
parameter_list|,
name|int
name|rows
parameter_list|)
block|{
name|iTable
operator|=
name|table
expr_stmt|;
name|iNrColumns
operator|=
name|cols
expr_stmt|;
name|iNrRows
operator|=
name|rows
expr_stmt|;
block|}
specifier|public
name|String
name|getTable
parameter_list|()
block|{
return|return
name|iTable
return|;
block|}
specifier|public
name|int
name|getNrRows
parameter_list|()
block|{
return|return
name|iNrRows
return|;
block|}
specifier|public
name|int
name|getNrColumns
parameter_list|()
block|{
return|return
name|iNrColumns
return|;
block|}
specifier|public
name|Long
name|getExamType
parameter_list|()
block|{
return|return
name|iExamType
return|;
block|}
specifier|public
name|void
name|setExamType
parameter_list|(
name|Long
name|type
parameter_list|)
block|{
name|iExamType
operator|=
name|type
expr_stmt|;
block|}
block|}
end_class

end_unit

