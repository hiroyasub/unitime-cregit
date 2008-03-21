begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.0 (University Course Timetabling& Student Sectioning Application)  * Copyright (C) 2007, UniTime.org, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program; if not, write to the Free Software Foundation, Inc.,  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|HttpSession
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
name|ActionMapping
import|;
end_import

begin_comment
comment|/**   * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|ExamAssignmentReportForm
extends|extends
name|ExamReportForm
block|{
specifier|private
name|String
name|iReport
init|=
literal|null
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|sExamAssignmentReport
init|=
literal|"Exam Assignment Report"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|sRoomAssignmentReport
init|=
literal|"Room Assignment Report"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|sPeriodUsage
init|=
literal|"Period Usage"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|sNrExamsADay
init|=
literal|"Number of Exams A Day"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|sRoomSplits
init|=
literal|"Room Splits"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|sViolatedDistributions
init|=
literal|"Violated Distribution Constraints"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|sDirectStudentConflicts
init|=
literal|"Direct Student Conflicts"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|sMore2ADayStudentConflicts
init|=
literal|"More Than 2 Exams A Day Student Conflicts"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|sBackToBackStudentConflicts
init|=
literal|"Back-To-Back Student Conflicts"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|sStudentConflicts
init|=
literal|"Student Conflicts"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|sDirectInstructorConflicts
init|=
literal|"Direct Instructor Conflicts"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|sMore2ADayInstructorConflicts
init|=
literal|"More Than 2 Exams A Day Instructor Conflicts"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|sBackToBackInstructorConflicts
init|=
literal|"Back-To-Back Instructor Conflicts"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|sInstructorConflicts
init|=
literal|"Instructor Conflicts"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
index|[]
name|sReports
init|=
block|{
name|sExamAssignmentReport
block|,
name|sRoomAssignmentReport
block|,
name|sPeriodUsage
block|,
name|sNrExamsADay
block|,
name|sRoomSplits
block|,
comment|// sViolatedDistributions,
comment|// sDirectStudentConflicts, sMore2ADayStudentConflicts, sBackToBackStudentConflicts, sStudentConflicts,
comment|// sDirectInstructorConflicts, sMore2ADayInstructorConflicts, sBackToBackInstructorConflicts, sInstructorConflicts
block|}
decl_stmt|;
specifier|private
name|String
name|iFilter
init|=
literal|null
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
name|super
operator|.
name|validate
argument_list|(
name|mapping
argument_list|,
name|request
argument_list|)
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
name|super
operator|.
name|reset
argument_list|(
name|mapping
argument_list|,
name|request
argument_list|)
expr_stmt|;
name|iReport
operator|=
literal|null
expr_stmt|;
block|}
specifier|public
name|String
name|getReport
parameter_list|()
block|{
return|return
name|iReport
return|;
block|}
specifier|public
name|void
name|setReport
parameter_list|(
name|String
name|report
parameter_list|)
block|{
name|iReport
operator|=
name|report
expr_stmt|;
block|}
specifier|public
name|String
index|[]
name|getReports
parameter_list|()
block|{
return|return
name|sReports
return|;
block|}
specifier|public
name|String
name|getFilter
parameter_list|()
block|{
return|return
name|iFilter
return|;
block|}
specifier|public
name|void
name|setFilter
parameter_list|(
name|String
name|filter
parameter_list|)
block|{
name|iFilter
operator|=
name|filter
expr_stmt|;
block|}
specifier|public
name|void
name|load
parameter_list|(
name|HttpSession
name|session
parameter_list|)
block|{
name|super
operator|.
name|load
argument_list|(
name|session
argument_list|)
expr_stmt|;
name|setFilter
argument_list|(
name|session
operator|.
name|getAttribute
argument_list|(
literal|"ExamReport.Filter"
argument_list|)
operator|==
literal|null
condition|?
literal|""
else|:
operator|(
name|String
operator|)
name|session
operator|.
name|getAttribute
argument_list|(
literal|"ExamReport.Filter"
argument_list|)
argument_list|)
expr_stmt|;
name|setReport
argument_list|(
name|session
operator|.
name|getAttribute
argument_list|(
literal|"ExamReport.Report"
argument_list|)
operator|==
literal|null
condition|?
literal|""
else|:
operator|(
name|String
operator|)
name|session
operator|.
name|getAttribute
argument_list|(
literal|"ExamReport.Report"
argument_list|)
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|save
parameter_list|(
name|HttpSession
name|session
parameter_list|)
block|{
name|super
operator|.
name|save
argument_list|(
name|session
argument_list|)
expr_stmt|;
if|if
condition|(
name|getFilter
argument_list|()
operator|==
literal|null
condition|)
name|session
operator|.
name|removeAttribute
argument_list|(
literal|"ExamReport.Filter"
argument_list|)
expr_stmt|;
else|else
name|session
operator|.
name|setAttribute
argument_list|(
literal|"ExamReport.Filter"
argument_list|,
name|getFilter
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|getReport
argument_list|()
operator|==
literal|null
condition|)
name|session
operator|.
name|removeAttribute
argument_list|(
literal|"ExamReport.Report"
argument_list|)
expr_stmt|;
else|else
name|session
operator|.
name|setAttribute
argument_list|(
literal|"ExamReport.Report"
argument_list|,
name|getReport
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

