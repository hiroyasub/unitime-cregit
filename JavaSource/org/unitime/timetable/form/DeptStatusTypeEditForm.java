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
name|Iterator
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Vector
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
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionMessage
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
name|DepartmentStatusType
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
name|Session
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
name|DepartmentStatusTypeDAO
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
name|util
operator|.
name|IdValue
import|;
end_import

begin_comment
comment|/**   * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|DeptStatusTypeEditForm
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
literal|684686223274367430L
decl_stmt|;
specifier|private
name|String
name|iOp
decl_stmt|;
specifier|private
name|Long
name|iUniqueId
decl_stmt|;
specifier|private
name|String
name|iReference
decl_stmt|;
specifier|private
name|String
name|iLabel
decl_stmt|;
specifier|private
name|int
name|iApply
init|=
literal|0
decl_stmt|;
specifier|private
name|int
name|iOrder
init|=
operator|-
literal|1
decl_stmt|;
specifier|private
name|boolean
name|iCanManagerView
init|=
literal|false
decl_stmt|;
specifier|private
name|boolean
name|iCanManagerEdit
init|=
literal|false
decl_stmt|;
specifier|private
name|boolean
name|iCanManagerLimitedEdit
init|=
literal|false
decl_stmt|;
specifier|private
name|boolean
name|iCanOwnerView
init|=
literal|false
decl_stmt|;
specifier|private
name|boolean
name|iCanOwnerEdit
init|=
literal|false
decl_stmt|;
specifier|private
name|boolean
name|iCanOwnerLimitedEdit
init|=
literal|false
decl_stmt|;
specifier|private
name|boolean
name|iCanAudit
init|=
literal|false
decl_stmt|;
specifier|private
name|boolean
name|iCanTimetable
init|=
literal|false
decl_stmt|;
specifier|private
name|boolean
name|iCanCommit
init|=
literal|false
decl_stmt|;
specifier|private
name|boolean
name|iCanExamView
init|=
literal|false
decl_stmt|;
specifier|private
name|boolean
name|iCanExamEdit
init|=
literal|false
decl_stmt|;
specifier|private
name|boolean
name|iCanExamTimetable
init|=
literal|false
decl_stmt|;
specifier|private
name|boolean
name|iCanNoRoleReportExamFin
init|=
literal|false
decl_stmt|;
specifier|private
name|boolean
name|iCanNoRoleReportExamMid
init|=
literal|false
decl_stmt|;
specifier|private
name|boolean
name|iCanNoRoleReportClass
init|=
literal|false
decl_stmt|;
specifier|private
name|boolean
name|iCanSectioningStudents
init|=
literal|false
decl_stmt|;
specifier|private
name|boolean
name|iCanPreRegisterStudents
init|=
literal|false
decl_stmt|;
specifier|private
name|boolean
name|iCanOnlineSectionStudents
init|=
literal|false
decl_stmt|;
specifier|private
name|boolean
name|iTestSession
init|=
literal|false
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
if|if
condition|(
name|iReference
operator|==
literal|null
operator|||
name|iReference
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"reference"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.required"
argument_list|,
literal|""
argument_list|)
argument_list|)
expr_stmt|;
else|else
block|{
try|try
block|{
name|DepartmentStatusType
name|ds
init|=
name|DepartmentStatusType
operator|.
name|findByRef
argument_list|(
name|iReference
argument_list|)
decl_stmt|;
if|if
condition|(
name|ds
operator|!=
literal|null
operator|&&
operator|!
name|ds
operator|.
name|getUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
name|iUniqueId
argument_list|)
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"reference"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.exists"
argument_list|,
name|iReference
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"reference"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|iLabel
operator|==
literal|null
operator|||
name|iLabel
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"label"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.required"
argument_list|,
literal|""
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|iApply
operator|<
literal|0
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"apply"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.required"
argument_list|,
literal|""
argument_list|)
argument_list|)
expr_stmt|;
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
literal|"List"
expr_stmt|;
name|iUniqueId
operator|=
operator|new
name|Long
argument_list|(
operator|-
literal|1
argument_list|)
expr_stmt|;
name|iReference
operator|=
literal|null
expr_stmt|;
name|iLabel
operator|=
literal|null
expr_stmt|;
name|iApply
operator|=
literal|0
expr_stmt|;
name|iOrder
operator|=
name|DepartmentStatusType
operator|.
name|findAll
argument_list|()
operator|.
name|size
argument_list|()
expr_stmt|;
name|iCanManagerView
operator|=
literal|false
expr_stmt|;
name|iCanManagerEdit
operator|=
literal|false
expr_stmt|;
name|iCanManagerLimitedEdit
operator|=
literal|false
expr_stmt|;
name|iCanOwnerView
operator|=
literal|false
expr_stmt|;
name|iCanOwnerEdit
operator|=
literal|false
expr_stmt|;
name|iCanOwnerLimitedEdit
operator|=
literal|false
expr_stmt|;
name|iCanAudit
operator|=
literal|false
expr_stmt|;
name|iCanTimetable
operator|=
literal|false
expr_stmt|;
name|iCanCommit
operator|=
literal|false
expr_stmt|;
name|iCanExamView
operator|=
literal|false
expr_stmt|;
name|iCanExamEdit
operator|=
literal|false
expr_stmt|;
name|iCanExamTimetable
operator|=
literal|false
expr_stmt|;
name|iCanNoRoleReportExamFin
operator|=
literal|false
expr_stmt|;
name|iCanNoRoleReportExamMid
operator|=
literal|false
expr_stmt|;
name|iCanNoRoleReportClass
operator|=
literal|false
expr_stmt|;
name|iCanSectioningStudents
operator|=
literal|false
expr_stmt|;
name|iCanPreRegisterStudents
operator|=
literal|false
expr_stmt|;
name|iCanOnlineSectionStudents
operator|=
literal|false
expr_stmt|;
name|iTestSession
operator|=
literal|false
expr_stmt|;
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
name|setUniqueId
parameter_list|(
name|Long
name|uniqueId
parameter_list|)
block|{
name|iUniqueId
operator|=
name|uniqueId
expr_stmt|;
block|}
specifier|public
name|Long
name|getUniqueId
parameter_list|()
block|{
return|return
name|iUniqueId
return|;
block|}
specifier|public
name|void
name|setReference
parameter_list|(
name|String
name|reference
parameter_list|)
block|{
name|iReference
operator|=
name|reference
expr_stmt|;
block|}
specifier|public
name|String
name|getReference
parameter_list|()
block|{
return|return
name|iReference
return|;
block|}
specifier|public
name|void
name|setLabel
parameter_list|(
name|String
name|label
parameter_list|)
block|{
name|iLabel
operator|=
name|label
expr_stmt|;
block|}
specifier|public
name|String
name|getLabel
parameter_list|()
block|{
return|return
name|iLabel
return|;
block|}
specifier|public
name|void
name|setOrder
parameter_list|(
name|int
name|order
parameter_list|)
block|{
name|iOrder
operator|=
name|order
expr_stmt|;
block|}
specifier|public
name|int
name|getOrder
parameter_list|()
block|{
return|return
name|iOrder
return|;
block|}
specifier|public
name|int
name|getApply
parameter_list|()
block|{
return|return
name|iApply
return|;
block|}
specifier|public
name|void
name|setApply
parameter_list|(
name|int
name|apply
parameter_list|)
block|{
name|iApply
operator|=
name|apply
expr_stmt|;
block|}
specifier|public
name|void
name|setApply
parameter_list|(
name|Long
name|apply
parameter_list|)
block|{
name|iApply
operator|=
operator|(
name|apply
operator|==
literal|null
condition|?
operator|-
literal|1
else|:
operator|(
name|int
operator|)
name|apply
operator|.
name|longValue
argument_list|()
operator|)
expr_stmt|;
block|}
specifier|public
name|Vector
name|getApplyOptions
parameter_list|()
block|{
name|Vector
name|options
init|=
operator|new
name|Vector
argument_list|()
decl_stmt|;
name|options
operator|.
name|add
argument_list|(
operator|new
name|IdValue
argument_list|(
operator|new
name|Long
argument_list|(
name|DepartmentStatusType
operator|.
name|Apply
operator|.
name|Session
operator|.
name|toInt
argument_list|()
argument_list|)
argument_list|,
literal|"Session"
argument_list|)
argument_list|)
expr_stmt|;
name|options
operator|.
name|add
argument_list|(
operator|new
name|IdValue
argument_list|(
operator|new
name|Long
argument_list|(
name|DepartmentStatusType
operator|.
name|Apply
operator|.
name|Department
operator|.
name|toInt
argument_list|()
argument_list|)
argument_list|,
literal|"Department"
argument_list|)
argument_list|)
expr_stmt|;
name|options
operator|.
name|add
argument_list|(
operator|new
name|IdValue
argument_list|(
operator|new
name|Long
argument_list|(
name|DepartmentStatusType
operator|.
name|Apply
operator|.
name|Session
operator|.
name|toInt
argument_list|()
operator||
name|DepartmentStatusType
operator|.
name|Apply
operator|.
name|Department
operator|.
name|toInt
argument_list|()
argument_list|)
argument_list|,
literal|"Both"
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|options
return|;
block|}
specifier|public
name|void
name|setCanManagerView
parameter_list|(
name|boolean
name|canManagerView
parameter_list|)
block|{
name|iCanManagerView
operator|=
name|canManagerView
expr_stmt|;
block|}
specifier|public
name|boolean
name|getCanManagerView
parameter_list|()
block|{
return|return
name|iCanManagerView
return|;
block|}
specifier|public
name|void
name|setCanManagerEdit
parameter_list|(
name|boolean
name|canManagerEdit
parameter_list|)
block|{
name|iCanManagerEdit
operator|=
name|canManagerEdit
expr_stmt|;
block|}
specifier|public
name|boolean
name|getCanManagerEdit
parameter_list|()
block|{
return|return
name|iCanManagerEdit
return|;
block|}
specifier|public
name|void
name|setCanManagerLimitedEdit
parameter_list|(
name|boolean
name|canManagerLimitedEdit
parameter_list|)
block|{
name|iCanManagerLimitedEdit
operator|=
name|canManagerLimitedEdit
expr_stmt|;
block|}
specifier|public
name|boolean
name|getCanManagerLimitedEdit
parameter_list|()
block|{
return|return
name|iCanManagerLimitedEdit
return|;
block|}
specifier|public
name|void
name|setCanOwnerView
parameter_list|(
name|boolean
name|canOwnerView
parameter_list|)
block|{
name|iCanOwnerView
operator|=
name|canOwnerView
expr_stmt|;
block|}
specifier|public
name|boolean
name|getCanOwnerView
parameter_list|()
block|{
return|return
name|iCanOwnerView
return|;
block|}
specifier|public
name|void
name|setCanOwnerEdit
parameter_list|(
name|boolean
name|canOwnerEdit
parameter_list|)
block|{
name|iCanOwnerEdit
operator|=
name|canOwnerEdit
expr_stmt|;
block|}
specifier|public
name|boolean
name|getCanOwnerEdit
parameter_list|()
block|{
return|return
name|iCanOwnerEdit
return|;
block|}
specifier|public
name|void
name|setCanOwnerLimitedEdit
parameter_list|(
name|boolean
name|canOwnerLimitedEdit
parameter_list|)
block|{
name|iCanOwnerLimitedEdit
operator|=
name|canOwnerLimitedEdit
expr_stmt|;
block|}
specifier|public
name|boolean
name|getCanOwnerLimitedEdit
parameter_list|()
block|{
return|return
name|iCanOwnerLimitedEdit
return|;
block|}
specifier|public
name|void
name|setCanAudit
parameter_list|(
name|boolean
name|canAudit
parameter_list|)
block|{
name|iCanAudit
operator|=
name|canAudit
expr_stmt|;
block|}
specifier|public
name|boolean
name|getCanAudit
parameter_list|()
block|{
return|return
name|iCanAudit
return|;
block|}
specifier|public
name|void
name|setCanTimetable
parameter_list|(
name|boolean
name|canTimetable
parameter_list|)
block|{
name|iCanTimetable
operator|=
name|canTimetable
expr_stmt|;
block|}
specifier|public
name|boolean
name|getCanTimetable
parameter_list|()
block|{
return|return
name|iCanTimetable
return|;
block|}
specifier|public
name|void
name|setCanCommit
parameter_list|(
name|boolean
name|canCommit
parameter_list|)
block|{
name|iCanCommit
operator|=
name|canCommit
expr_stmt|;
block|}
specifier|public
name|boolean
name|getCanCommit
parameter_list|()
block|{
return|return
name|iCanCommit
return|;
block|}
specifier|public
name|boolean
name|getCanExamView
parameter_list|()
block|{
return|return
name|iCanExamView
return|;
block|}
specifier|public
name|void
name|setCanExamView
parameter_list|(
name|boolean
name|canExamView
parameter_list|)
block|{
name|iCanExamView
operator|=
name|canExamView
expr_stmt|;
block|}
specifier|public
name|boolean
name|getCanExamEdit
parameter_list|()
block|{
return|return
name|iCanExamEdit
return|;
block|}
specifier|public
name|void
name|setCanExamEdit
parameter_list|(
name|boolean
name|canExamEdit
parameter_list|)
block|{
name|iCanExamEdit
operator|=
name|canExamEdit
expr_stmt|;
block|}
specifier|public
name|boolean
name|getCanExamTimetable
parameter_list|()
block|{
return|return
name|iCanExamTimetable
return|;
block|}
specifier|public
name|void
name|setCanExamTimetable
parameter_list|(
name|boolean
name|canExamTimetable
parameter_list|)
block|{
name|iCanExamTimetable
operator|=
name|canExamTimetable
expr_stmt|;
block|}
specifier|public
name|void
name|setCanNoRoleReportExamFin
parameter_list|(
name|boolean
name|canNoRoleReportExamFin
parameter_list|)
block|{
name|iCanNoRoleReportExamFin
operator|=
name|canNoRoleReportExamFin
expr_stmt|;
block|}
specifier|public
name|boolean
name|getCanNoRoleReportExamFin
parameter_list|()
block|{
return|return
name|iCanNoRoleReportExamFin
return|;
block|}
specifier|public
name|void
name|setCanNoRoleReportExamMid
parameter_list|(
name|boolean
name|canNoRoleReportExamMid
parameter_list|)
block|{
name|iCanNoRoleReportExamMid
operator|=
name|canNoRoleReportExamMid
expr_stmt|;
block|}
specifier|public
name|boolean
name|getCanNoRoleReportExamMid
parameter_list|()
block|{
return|return
name|iCanNoRoleReportExamMid
return|;
block|}
specifier|public
name|void
name|setCanNoRoleReportClass
parameter_list|(
name|boolean
name|canNoRoleReportClass
parameter_list|)
block|{
name|iCanNoRoleReportClass
operator|=
name|canNoRoleReportClass
expr_stmt|;
block|}
specifier|public
name|boolean
name|getCanNoRoleReportClass
parameter_list|()
block|{
return|return
name|iCanNoRoleReportClass
return|;
block|}
specifier|public
name|void
name|setCanSectioningStudents
parameter_list|(
name|boolean
name|canSectioningStudents
parameter_list|)
block|{
name|iCanSectioningStudents
operator|=
name|canSectioningStudents
expr_stmt|;
block|}
specifier|public
name|boolean
name|getCanSectioningStudents
parameter_list|()
block|{
return|return
name|iCanSectioningStudents
return|;
block|}
specifier|public
name|void
name|setCanPreRegisterStudents
parameter_list|(
name|boolean
name|canPreRegisterStudents
parameter_list|)
block|{
name|iCanPreRegisterStudents
operator|=
name|canPreRegisterStudents
expr_stmt|;
block|}
specifier|public
name|boolean
name|getCanPreRegisterStudents
parameter_list|()
block|{
return|return
name|iCanPreRegisterStudents
return|;
block|}
specifier|public
name|void
name|setCanOnlineSectionStudents
parameter_list|(
name|boolean
name|canOnlineSectionStudents
parameter_list|)
block|{
name|iCanOnlineSectionStudents
operator|=
name|canOnlineSectionStudents
expr_stmt|;
block|}
specifier|public
name|boolean
name|getCanOnlineSectionStudents
parameter_list|()
block|{
return|return
name|iCanOnlineSectionStudents
return|;
block|}
specifier|public
name|void
name|setTestSession
parameter_list|(
name|boolean
name|testSession
parameter_list|)
block|{
name|iTestSession
operator|=
name|testSession
expr_stmt|;
block|}
specifier|public
name|boolean
name|getTestSession
parameter_list|()
block|{
return|return
name|iTestSession
return|;
block|}
specifier|public
name|int
name|getRights
parameter_list|()
block|{
name|int
name|rights
init|=
literal|0
decl_stmt|;
if|if
condition|(
name|getCanManagerView
argument_list|()
condition|)
name|rights
operator|+=
name|DepartmentStatusType
operator|.
name|Status
operator|.
name|ManagerView
operator|.
name|toInt
argument_list|()
expr_stmt|;
if|if
condition|(
name|getCanManagerEdit
argument_list|()
condition|)
name|rights
operator|+=
name|DepartmentStatusType
operator|.
name|Status
operator|.
name|ManagerEdit
operator|.
name|toInt
argument_list|()
expr_stmt|;
if|if
condition|(
name|getCanManagerLimitedEdit
argument_list|()
condition|)
name|rights
operator|+=
name|DepartmentStatusType
operator|.
name|Status
operator|.
name|ManagerLimitedEdit
operator|.
name|toInt
argument_list|()
expr_stmt|;
if|if
condition|(
name|getCanOwnerView
argument_list|()
condition|)
name|rights
operator|+=
name|DepartmentStatusType
operator|.
name|Status
operator|.
name|OwnerView
operator|.
name|toInt
argument_list|()
expr_stmt|;
if|if
condition|(
name|getCanOwnerEdit
argument_list|()
condition|)
name|rights
operator|+=
name|DepartmentStatusType
operator|.
name|Status
operator|.
name|OwnerEdit
operator|.
name|toInt
argument_list|()
expr_stmt|;
if|if
condition|(
name|getCanOwnerLimitedEdit
argument_list|()
condition|)
name|rights
operator|+=
name|DepartmentStatusType
operator|.
name|Status
operator|.
name|OwnerLimitedEdit
operator|.
name|toInt
argument_list|()
expr_stmt|;
if|if
condition|(
name|getCanAudit
argument_list|()
condition|)
name|rights
operator|+=
name|DepartmentStatusType
operator|.
name|Status
operator|.
name|Audit
operator|.
name|toInt
argument_list|()
expr_stmt|;
if|if
condition|(
name|getCanTimetable
argument_list|()
condition|)
name|rights
operator|+=
name|DepartmentStatusType
operator|.
name|Status
operator|.
name|Timetable
operator|.
name|toInt
argument_list|()
expr_stmt|;
if|if
condition|(
name|getCanCommit
argument_list|()
condition|)
name|rights
operator|+=
name|DepartmentStatusType
operator|.
name|Status
operator|.
name|Commit
operator|.
name|toInt
argument_list|()
expr_stmt|;
if|if
condition|(
name|getCanExamView
argument_list|()
condition|)
name|rights
operator|+=
name|DepartmentStatusType
operator|.
name|Status
operator|.
name|ExamView
operator|.
name|toInt
argument_list|()
expr_stmt|;
if|if
condition|(
name|getCanExamEdit
argument_list|()
condition|)
name|rights
operator|+=
name|DepartmentStatusType
operator|.
name|Status
operator|.
name|ExamEdit
operator|.
name|toInt
argument_list|()
expr_stmt|;
if|if
condition|(
name|getCanExamTimetable
argument_list|()
condition|)
name|rights
operator|+=
name|DepartmentStatusType
operator|.
name|Status
operator|.
name|ExamTimetable
operator|.
name|toInt
argument_list|()
expr_stmt|;
if|if
condition|(
name|getCanNoRoleReportExamFin
argument_list|()
condition|)
name|rights
operator|+=
name|DepartmentStatusType
operator|.
name|Status
operator|.
name|ReportExamsFinal
operator|.
name|toInt
argument_list|()
expr_stmt|;
if|if
condition|(
name|getCanNoRoleReportExamMid
argument_list|()
condition|)
name|rights
operator|+=
name|DepartmentStatusType
operator|.
name|Status
operator|.
name|ReportExamsMidterm
operator|.
name|toInt
argument_list|()
expr_stmt|;
if|if
condition|(
name|getCanNoRoleReportClass
argument_list|()
condition|)
name|rights
operator|+=
name|DepartmentStatusType
operator|.
name|Status
operator|.
name|ReportClasses
operator|.
name|toInt
argument_list|()
expr_stmt|;
if|if
condition|(
name|getCanSectioningStudents
argument_list|()
condition|)
name|rights
operator|+=
name|DepartmentStatusType
operator|.
name|Status
operator|.
name|StudentsAssistant
operator|.
name|toInt
argument_list|()
expr_stmt|;
if|if
condition|(
name|getCanPreRegisterStudents
argument_list|()
condition|)
name|rights
operator|+=
name|DepartmentStatusType
operator|.
name|Status
operator|.
name|StudentsPreRegister
operator|.
name|toInt
argument_list|()
expr_stmt|;
if|if
condition|(
name|getCanOnlineSectionStudents
argument_list|()
condition|)
name|rights
operator|+=
name|DepartmentStatusType
operator|.
name|Status
operator|.
name|StudentsOnline
operator|.
name|toInt
argument_list|()
expr_stmt|;
if|if
condition|(
name|getTestSession
argument_list|()
condition|)
name|rights
operator|+=
name|DepartmentStatusType
operator|.
name|Status
operator|.
name|TestSession
operator|.
name|toInt
argument_list|()
expr_stmt|;
return|return
name|rights
return|;
block|}
specifier|public
name|void
name|setRights
parameter_list|(
name|int
name|rights
parameter_list|)
block|{
name|setCanManagerView
argument_list|(
name|DepartmentStatusType
operator|.
name|Status
operator|.
name|ManagerView
operator|.
name|has
argument_list|(
name|rights
argument_list|)
argument_list|)
expr_stmt|;
name|setCanManagerEdit
argument_list|(
name|DepartmentStatusType
operator|.
name|Status
operator|.
name|ManagerEdit
operator|.
name|has
argument_list|(
name|rights
argument_list|)
argument_list|)
expr_stmt|;
name|setCanManagerLimitedEdit
argument_list|(
name|DepartmentStatusType
operator|.
name|Status
operator|.
name|ManagerLimitedEdit
operator|.
name|has
argument_list|(
name|rights
argument_list|)
argument_list|)
expr_stmt|;
name|setCanOwnerView
argument_list|(
name|DepartmentStatusType
operator|.
name|Status
operator|.
name|OwnerView
operator|.
name|has
argument_list|(
name|rights
argument_list|)
argument_list|)
expr_stmt|;
name|setCanOwnerEdit
argument_list|(
name|DepartmentStatusType
operator|.
name|Status
operator|.
name|OwnerEdit
operator|.
name|has
argument_list|(
name|rights
argument_list|)
argument_list|)
expr_stmt|;
name|setCanOwnerLimitedEdit
argument_list|(
name|DepartmentStatusType
operator|.
name|Status
operator|.
name|OwnerLimitedEdit
operator|.
name|has
argument_list|(
name|rights
argument_list|)
argument_list|)
expr_stmt|;
name|setCanAudit
argument_list|(
name|DepartmentStatusType
operator|.
name|Status
operator|.
name|Audit
operator|.
name|has
argument_list|(
name|rights
argument_list|)
argument_list|)
expr_stmt|;
name|setCanTimetable
argument_list|(
name|DepartmentStatusType
operator|.
name|Status
operator|.
name|Timetable
operator|.
name|has
argument_list|(
name|rights
argument_list|)
argument_list|)
expr_stmt|;
name|setCanCommit
argument_list|(
name|DepartmentStatusType
operator|.
name|Status
operator|.
name|Commit
operator|.
name|has
argument_list|(
name|rights
argument_list|)
argument_list|)
expr_stmt|;
name|setCanExamView
argument_list|(
name|DepartmentStatusType
operator|.
name|Status
operator|.
name|ExamView
operator|.
name|has
argument_list|(
name|rights
argument_list|)
argument_list|)
expr_stmt|;
name|setCanExamEdit
argument_list|(
name|DepartmentStatusType
operator|.
name|Status
operator|.
name|ExamEdit
operator|.
name|has
argument_list|(
name|rights
argument_list|)
argument_list|)
expr_stmt|;
name|setCanExamTimetable
argument_list|(
name|DepartmentStatusType
operator|.
name|Status
operator|.
name|ExamTimetable
operator|.
name|has
argument_list|(
name|rights
argument_list|)
argument_list|)
expr_stmt|;
name|setCanNoRoleReportExamFin
argument_list|(
name|DepartmentStatusType
operator|.
name|Status
operator|.
name|ReportExamsFinal
operator|.
name|has
argument_list|(
name|rights
argument_list|)
argument_list|)
expr_stmt|;
name|setCanNoRoleReportExamMid
argument_list|(
name|DepartmentStatusType
operator|.
name|Status
operator|.
name|ReportExamsMidterm
operator|.
name|has
argument_list|(
name|rights
argument_list|)
argument_list|)
expr_stmt|;
name|setCanNoRoleReportClass
argument_list|(
name|DepartmentStatusType
operator|.
name|Status
operator|.
name|ReportClasses
operator|.
name|has
argument_list|(
name|rights
argument_list|)
argument_list|)
expr_stmt|;
name|setCanSectioningStudents
argument_list|(
name|DepartmentStatusType
operator|.
name|Status
operator|.
name|StudentsAssistant
operator|.
name|has
argument_list|(
name|rights
argument_list|)
argument_list|)
expr_stmt|;
name|setCanPreRegisterStudents
argument_list|(
name|DepartmentStatusType
operator|.
name|Status
operator|.
name|StudentsPreRegister
operator|.
name|has
argument_list|(
name|rights
argument_list|)
argument_list|)
expr_stmt|;
name|setCanOnlineSectionStudents
argument_list|(
name|DepartmentStatusType
operator|.
name|Status
operator|.
name|StudentsOnline
operator|.
name|has
argument_list|(
name|rights
argument_list|)
argument_list|)
expr_stmt|;
name|setTestSession
argument_list|(
name|DepartmentStatusType
operator|.
name|Status
operator|.
name|TestSession
operator|.
name|has
argument_list|(
name|rights
argument_list|)
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|load
parameter_list|(
name|DepartmentStatusType
name|s
parameter_list|)
block|{
if|if
condition|(
name|s
operator|==
literal|null
condition|)
block|{
name|reset
argument_list|(
literal|null
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|setOp
argument_list|(
literal|"Save"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|setUniqueId
argument_list|(
name|s
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|setReference
argument_list|(
name|s
operator|.
name|getReference
argument_list|()
argument_list|)
expr_stmt|;
name|setLabel
argument_list|(
name|s
operator|.
name|getLabel
argument_list|()
argument_list|)
expr_stmt|;
name|setApply
argument_list|(
name|s
operator|.
name|getApply
argument_list|()
argument_list|)
expr_stmt|;
name|setRights
argument_list|(
name|s
operator|.
name|getStatus
argument_list|()
operator|.
name|intValue
argument_list|()
argument_list|)
expr_stmt|;
name|setOrder
argument_list|(
name|s
operator|.
name|getOrd
argument_list|()
argument_list|)
expr_stmt|;
name|setOp
argument_list|(
literal|"Update"
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|DepartmentStatusType
name|saveOrUpdate
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|)
throws|throws
name|Exception
block|{
name|DepartmentStatusType
name|s
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|getUniqueId
argument_list|()
operator|.
name|intValue
argument_list|()
operator|>=
literal|0
condition|)
name|s
operator|=
operator|(
operator|new
name|DepartmentStatusTypeDAO
argument_list|()
operator|)
operator|.
name|get
argument_list|(
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|s
operator|==
literal|null
condition|)
name|s
operator|=
operator|new
name|DepartmentStatusType
argument_list|()
expr_stmt|;
name|s
operator|.
name|setReference
argument_list|(
name|getReference
argument_list|()
argument_list|)
expr_stmt|;
name|s
operator|.
name|setLabel
argument_list|(
name|getLabel
argument_list|()
argument_list|)
expr_stmt|;
name|s
operator|.
name|setApply
argument_list|(
name|getApply
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|s
operator|.
name|getOrd
argument_list|()
operator|==
literal|null
condition|)
name|s
operator|.
name|setOrd
argument_list|(
name|DepartmentStatusType
operator|.
name|findAll
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|s
operator|.
name|setStatus
argument_list|(
name|getRights
argument_list|()
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|s
argument_list|)
expr_stmt|;
name|setUniqueId
argument_list|(
name|s
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|s
return|;
block|}
specifier|public
name|void
name|delete
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|)
throws|throws
name|Exception
block|{
if|if
condition|(
name|getUniqueId
argument_list|()
operator|.
name|intValue
argument_list|()
operator|<
literal|0
condition|)
return|return;
name|DepartmentStatusType
name|s
init|=
operator|(
operator|new
name|DepartmentStatusTypeDAO
argument_list|()
operator|)
operator|.
name|get
argument_list|(
name|getUniqueId
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"select s from Session s where s.statusType.uniqueId=:id"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"id"
argument_list|,
name|s
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|iterate
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Session
name|session
init|=
operator|(
name|Session
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|DepartmentStatusType
name|other
init|=
literal|null
decl_stmt|;
for|for
control|(
name|Iterator
name|j
init|=
name|DepartmentStatusType
operator|.
name|findAll
argument_list|()
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
name|DepartmentStatusType
name|x
init|=
operator|(
name|DepartmentStatusType
operator|)
name|j
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|x
operator|.
name|getUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
name|s
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|&&
name|x
operator|.
name|applySession
argument_list|()
condition|)
block|{
name|other
operator|=
name|x
expr_stmt|;
break|break;
block|}
block|}
if|if
condition|(
name|other
operator|==
literal|null
condition|)
throw|throw
operator|new
name|RuntimeException
argument_list|(
literal|"Unable to delete session status "
operator|+
name|getReference
argument_list|()
operator|+
literal|", no other session status available."
argument_list|)
throw|;
name|session
operator|.
name|setStatusType
argument_list|(
name|other
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|session
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|Iterator
name|i
init|=
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"select d from Department d where d.statusType.uniqueId=:id"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"id"
argument_list|,
name|s
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|iterate
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Department
name|dept
init|=
operator|(
name|Department
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|dept
operator|.
name|setStatusType
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|dept
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|Iterator
name|i
init|=
name|DepartmentStatusType
operator|.
name|findAll
argument_list|()
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
name|DepartmentStatusType
name|x
init|=
operator|(
name|DepartmentStatusType
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|x
operator|.
name|getOrd
argument_list|()
operator|>
name|s
operator|.
name|getOrd
argument_list|()
condition|)
block|{
name|x
operator|.
name|setOrd
argument_list|(
name|x
operator|.
name|getOrd
argument_list|()
operator|-
literal|1
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|x
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|s
operator|!=
literal|null
condition|)
name|hibSession
operator|.
name|delete
argument_list|(
name|s
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

