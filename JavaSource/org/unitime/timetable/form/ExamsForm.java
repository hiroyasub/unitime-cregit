begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.1 (University Timetabling Application)  * Copyright (C) 2008, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program; if not, write to the Free Software Foundation, Inc.,  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|Enumeration
import|;
end_import

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
name|TreeSet
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
name|ApplicationProperties
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
name|Exam
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
name|util
operator|.
name|ComboBoxLookup
import|;
end_import

begin_comment
comment|/**   * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|ExamsForm
extends|extends
name|ActionForm
block|{
specifier|private
name|String
name|iOp
init|=
literal|null
decl_stmt|;
specifier|private
name|Long
name|iSession
init|=
literal|null
decl_stmt|;
specifier|private
name|String
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
name|Vector
name|iSessions
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
name|int
name|iExamType
decl_stmt|;
specifier|private
name|String
name|iMessage
decl_stmt|;
specifier|private
name|String
name|iUser
decl_stmt|,
name|iPassword
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
name|Exam
operator|.
name|sExamTypeFinal
expr_stmt|;
name|iSession
operator|=
literal|null
expr_stmt|;
name|iUser
operator|=
literal|null
expr_stmt|;
name|iPassword
operator|=
literal|null
expr_stmt|;
name|iMessage
operator|=
literal|null
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
name|getSubjectArea
parameter_list|()
block|{
return|return
name|iSubjectArea
return|;
block|}
specifier|public
name|void
name|setSubjectArea
parameter_list|(
name|String
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
name|Long
name|getSession
parameter_list|()
block|{
return|return
name|iSession
return|;
block|}
specifier|public
name|void
name|setSession
parameter_list|(
name|Long
name|session
parameter_list|)
block|{
name|iSession
operator|=
name|session
expr_stmt|;
block|}
specifier|public
name|Collection
name|getSessions
parameter_list|()
block|{
return|return
name|iSessions
return|;
block|}
specifier|public
name|void
name|load
parameter_list|(
name|HttpSession
name|session
parameter_list|)
block|{
name|setSubjectArea
argument_list|(
name|session
operator|.
name|getAttribute
argument_list|(
literal|"Exams.subjectArea"
argument_list|)
operator|==
literal|null
condition|?
literal|null
else|:
operator|(
name|String
operator|)
name|session
operator|.
name|getAttribute
argument_list|(
literal|"Exams.subjectArea"
argument_list|)
argument_list|)
expr_stmt|;
name|iSessions
operator|=
operator|new
name|Vector
argument_list|()
expr_stmt|;
name|setSession
argument_list|(
name|session
operator|.
name|getAttribute
argument_list|(
literal|"Exams.session"
argument_list|)
operator|==
literal|null
condition|?
name|iSessions
operator|.
name|isEmpty
argument_list|()
condition|?
literal|null
else|:
name|Long
operator|.
name|valueOf
argument_list|(
operator|(
operator|(
name|ComboBoxLookup
operator|)
name|iSessions
operator|.
name|lastElement
argument_list|()
operator|)
operator|.
name|getValue
argument_list|()
argument_list|)
else|:
operator|(
name|Long
operator|)
name|session
operator|.
name|getAttribute
argument_list|(
literal|"Exams.session"
argument_list|)
argument_list|)
expr_stmt|;
name|boolean
name|hasSession
init|=
literal|false
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|Session
operator|.
name|getAllSessions
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
name|Session
name|s
init|=
operator|(
name|Session
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|s
operator|.
name|getStatusType
argument_list|()
operator|!=
literal|null
operator|&&
operator|(
name|s
operator|.
name|getStatusType
argument_list|()
operator|.
name|canNoRoleReportExamFinal
argument_list|()
operator|||
name|s
operator|.
name|getStatusType
argument_list|()
operator|.
name|canNoRoleReportExamMidterm
argument_list|()
operator|)
operator|&&
name|Exam
operator|.
name|hasTimetable
argument_list|(
name|s
operator|.
name|getUniqueId
argument_list|()
argument_list|)
condition|)
block|{
if|if
condition|(
name|s
operator|.
name|getUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
name|getSession
argument_list|()
argument_list|)
condition|)
name|hasSession
operator|=
literal|true
expr_stmt|;
name|iSessions
operator|.
name|add
argument_list|(
operator|new
name|ComboBoxLookup
argument_list|(
name|s
operator|.
name|getLabel
argument_list|()
argument_list|,
name|s
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
operator|!
name|hasSession
condition|)
block|{
name|setSession
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|setSubjectArea
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|getSession
argument_list|()
operator|==
literal|null
operator|&&
operator|!
name|iSessions
operator|.
name|isEmpty
argument_list|()
condition|)
name|setSession
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
operator|(
operator|(
name|ComboBoxLookup
operator|)
name|iSessions
operator|.
name|lastElement
argument_list|()
operator|)
operator|.
name|getValue
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
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
literal|"select distinct sa.subjectAreaAbbreviation from SubjectArea sa"
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
name|setExamType
argument_list|(
name|session
operator|.
name|getAttribute
argument_list|(
literal|"Exams.examType"
argument_list|)
operator|==
literal|null
condition|?
name|iExamType
else|:
operator|(
name|Integer
operator|)
name|session
operator|.
name|getAttribute
argument_list|(
literal|"Exams.examType"
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
literal|"Exams.subjectArea"
argument_list|)
expr_stmt|;
else|else
name|session
operator|.
name|setAttribute
argument_list|(
literal|"Exams.subjectArea"
argument_list|,
name|getSubjectArea
argument_list|()
argument_list|)
expr_stmt|;
name|session
operator|.
name|setAttribute
argument_list|(
literal|"Exams.examType"
argument_list|,
name|getExamType
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|getSession
argument_list|()
operator|==
literal|null
condition|)
name|session
operator|.
name|removeAttribute
argument_list|(
literal|"Exams.session"
argument_list|)
expr_stmt|;
else|else
name|session
operator|.
name|setAttribute
argument_list|(
literal|"Exams.session"
argument_list|,
name|getSession
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
name|int
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
name|int
name|type
parameter_list|)
block|{
name|iExamType
operator|=
name|type
expr_stmt|;
block|}
specifier|public
name|Collection
name|getExamTypes
parameter_list|()
block|{
name|Vector
name|ret
init|=
operator|new
name|Vector
argument_list|(
name|Exam
operator|.
name|sExamTypes
operator|.
name|length
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
name|Exam
operator|.
name|sExamTypes
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|ret
operator|.
name|add
argument_list|(
operator|new
name|ComboBoxLookup
argument_list|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.exam.name.type."
operator|+
name|Exam
operator|.
name|sExamTypes
index|[
name|i
index|]
argument_list|,
name|Exam
operator|.
name|sExamTypes
index|[
name|i
index|]
argument_list|)
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|i
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|ret
return|;
block|}
specifier|public
name|String
name|getUsername
parameter_list|()
block|{
return|return
name|iUser
return|;
block|}
specifier|public
name|void
name|setUsername
parameter_list|(
name|String
name|user
parameter_list|)
block|{
name|iUser
operator|=
name|user
expr_stmt|;
block|}
specifier|public
name|String
name|getPassword
parameter_list|()
block|{
return|return
name|iPassword
return|;
block|}
specifier|public
name|void
name|setPassword
parameter_list|(
name|String
name|password
parameter_list|)
block|{
name|iPassword
operator|=
name|password
expr_stmt|;
block|}
specifier|public
name|String
name|getMessage
parameter_list|()
block|{
return|return
name|iMessage
return|;
block|}
specifier|public
name|void
name|setMessage
parameter_list|(
name|String
name|message
parameter_list|)
block|{
name|iMessage
operator|=
name|message
expr_stmt|;
block|}
specifier|public
name|String
name|getExamTypeLabel
parameter_list|()
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
name|Exam
operator|.
name|sExamTypes
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|i
operator|==
name|getExamType
argument_list|()
condition|)
return|return
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.exam.name.type."
operator|+
name|Exam
operator|.
name|sExamTypes
index|[
name|i
index|]
argument_list|,
name|Exam
operator|.
name|sExamTypes
index|[
name|i
index|]
argument_list|)
operator|.
name|toLowerCase
argument_list|()
return|;
block|}
return|return
literal|""
return|;
block|}
specifier|public
name|String
name|getSessionLabel
parameter_list|()
block|{
if|if
condition|(
name|iSessions
operator|==
literal|null
condition|)
return|return
literal|""
return|;
for|for
control|(
name|Enumeration
name|e
init|=
name|iSessions
operator|.
name|elements
argument_list|()
init|;
name|e
operator|.
name|hasMoreElements
argument_list|()
condition|;
control|)
block|{
name|ComboBoxLookup
name|s
init|=
operator|(
name|ComboBoxLookup
operator|)
name|e
operator|.
name|nextElement
argument_list|()
decl_stmt|;
if|if
condition|(
name|Long
operator|.
name|valueOf
argument_list|(
name|s
operator|.
name|getValue
argument_list|()
argument_list|)
operator|.
name|equals
argument_list|(
name|getSession
argument_list|()
argument_list|)
condition|)
return|return
name|s
operator|.
name|getLabel
argument_list|()
return|;
block|}
return|return
literal|""
return|;
block|}
block|}
end_class

end_unit

