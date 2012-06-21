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
name|java
operator|.
name|io
operator|.
name|File
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|FileInputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|FileOutputStream
import|;
end_import

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
name|Hashtable
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
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
name|zip
operator|.
name|ZipEntry
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|zip
operator|.
name|ZipOutputStream
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
name|log4j
operator|.
name|Logger
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
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionMessages
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
name|commons
operator|.
name|Email
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
name|web
operator|.
name|Web
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
name|form
operator|.
name|EnrollmentAuditPdfReportForm
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
name|SessionDAO
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
name|reports
operator|.
name|enrollment
operator|.
name|PdfEnrollmentAuditReport
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
name|reports
operator|.
name|exam
operator|.
name|InstructorExamReport
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
name|Constants
import|;
end_import

begin_comment
comment|/**   * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|Service
argument_list|(
literal|"/enrollmentAuditPdfReport"
argument_list|)
specifier|public
class|class
name|EnrollmentAuditPdfReportAction
extends|extends
name|Action
block|{
specifier|protected
specifier|static
name|Logger
name|sLog
init|=
name|Logger
operator|.
name|getLogger
argument_list|(
name|EnrollmentAuditPdfReportAction
operator|.
name|class
argument_list|)
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
name|EnrollmentAuditPdfReportForm
name|myForm
init|=
operator|(
name|EnrollmentAuditPdfReportForm
operator|)
name|form
decl_stmt|;
comment|// Check Access
if|if
condition|(
operator|!
name|Web
operator|.
name|isLoggedIn
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Access Denied."
argument_list|)
throw|;
block|}
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
literal|"Generate"
operator|.
name|equals
argument_list|(
name|op
argument_list|)
condition|)
name|myForm
operator|.
name|save
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|)
expr_stmt|;
name|myForm
operator|.
name|load
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
literal|"Generate"
operator|.
name|equals
argument_list|(
name|op
argument_list|)
condition|)
block|{
name|ActionMessages
name|errors
init|=
name|myForm
operator|.
name|validate
argument_list|(
name|mapping
argument_list|,
name|request
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|errors
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|saveErrors
argument_list|(
name|request
argument_list|,
name|errors
argument_list|)
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"show"
argument_list|)
return|;
block|}
name|Session
name|session
init|=
name|Session
operator|.
name|getCurrentAcadSession
argument_list|(
name|Web
operator|.
name|getUser
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
try|try
block|{
name|myForm
operator|.
name|setReport
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|Hashtable
argument_list|<
name|String
argument_list|,
name|File
argument_list|>
name|output
init|=
operator|new
name|Hashtable
argument_list|()
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
name|myForm
operator|.
name|getReports
argument_list|()
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|myForm
operator|.
name|log
argument_list|(
literal|"Generating "
operator|+
name|myForm
operator|.
name|getReports
argument_list|()
index|[
name|i
index|]
operator|+
literal|"..."
argument_list|)
expr_stmt|;
name|Class
name|reportClass
init|=
name|EnrollmentAuditPdfReportForm
operator|.
name|sRegisteredReports
operator|.
name|get
argument_list|(
name|myForm
operator|.
name|getReports
argument_list|()
index|[
name|i
index|]
argument_list|)
decl_stmt|;
name|String
name|reportName
init|=
literal|null
decl_stmt|;
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|String
argument_list|,
name|Class
argument_list|>
name|entry
range|:
name|PdfEnrollmentAuditReport
operator|.
name|sRegisteredReports
operator|.
name|entrySet
argument_list|()
control|)
if|if
condition|(
name|entry
operator|.
name|getValue
argument_list|()
operator|.
name|equals
argument_list|(
name|reportClass
argument_list|)
condition|)
name|reportName
operator|=
name|entry
operator|.
name|getKey
argument_list|()
expr_stmt|;
if|if
condition|(
name|reportName
operator|==
literal|null
condition|)
name|reportName
operator|=
literal|"r"
operator|+
operator|(
name|i
operator|+
literal|1
operator|)
expr_stmt|;
name|String
name|name
init|=
name|session
operator|.
name|getAcademicTerm
argument_list|()
operator|+
name|session
operator|.
name|getSessionStartYear
argument_list|()
operator|+
literal|"_"
operator|+
name|reportName
decl_stmt|;
if|if
condition|(
name|myForm
operator|.
name|getAll
argument_list|()
condition|)
block|{
name|File
name|file
init|=
name|ApplicationProperties
operator|.
name|getTempFile
argument_list|(
name|name
argument_list|,
operator|(
name|myForm
operator|.
name|getModeIdx
argument_list|()
operator|==
name|PdfEnrollmentAuditReport
operator|.
name|sModeText
condition|?
literal|"txt"
else|:
literal|"pdf"
operator|)
argument_list|)
decl_stmt|;
name|myForm
operator|.
name|log
argument_list|(
literal|"&nbsp;&nbsp;Writing<a href='temp/"
operator|+
name|file
operator|.
name|getName
argument_list|()
operator|+
literal|"'>"
operator|+
name|reportName
operator|+
literal|"."
operator|+
operator|(
name|myForm
operator|.
name|getModeIdx
argument_list|()
operator|==
name|PdfEnrollmentAuditReport
operator|.
name|sModeText
condition|?
literal|"txt"
else|:
literal|"pdf"
operator|)
operator|+
literal|"</a>"
argument_list|)
expr_stmt|;
name|PdfEnrollmentAuditReport
name|report
init|=
operator|(
name|PdfEnrollmentAuditReport
operator|)
name|reportClass
operator|.
name|getConstructor
argument_list|(
name|int
operator|.
name|class
argument_list|,
name|File
operator|.
name|class
argument_list|,
name|Session
operator|.
name|class
argument_list|)
operator|.
name|newInstance
argument_list|(
name|myForm
operator|.
name|getModeIdx
argument_list|()
argument_list|,
name|file
argument_list|,
operator|new
name|SessionDAO
argument_list|()
operator|.
name|get
argument_list|(
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
name|report
operator|.
name|setShowId
argument_list|(
name|myForm
operator|.
name|getExternalId
argument_list|()
argument_list|)
expr_stmt|;
name|report
operator|.
name|setShowName
argument_list|(
name|myForm
operator|.
name|getStudentName
argument_list|()
argument_list|)
expr_stmt|;
name|report
operator|.
name|printReport
argument_list|()
expr_stmt|;
name|report
operator|.
name|close
argument_list|()
expr_stmt|;
name|output
operator|.
name|put
argument_list|(
name|reportName
operator|+
literal|"."
operator|+
operator|(
name|myForm
operator|.
name|getModeIdx
argument_list|()
operator|==
name|PdfEnrollmentAuditReport
operator|.
name|sModeText
condition|?
literal|"txt"
else|:
literal|"pdf"
operator|)
argument_list|,
name|file
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|TreeSet
argument_list|<
name|SubjectArea
argument_list|>
name|subjectAreas
init|=
operator|new
name|TreeSet
argument_list|<
name|SubjectArea
argument_list|>
argument_list|()
decl_stmt|;
name|String
name|subjAbbvs
init|=
literal|""
decl_stmt|;
for|for
control|(
name|int
name|j
init|=
literal|0
init|;
name|j
operator|<
name|myForm
operator|.
name|getSubjects
argument_list|()
operator|.
name|length
condition|;
name|j
operator|++
control|)
block|{
name|SubjectArea
name|subject
init|=
operator|new
name|SubjectAreaDAO
argument_list|()
operator|.
name|get
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|myForm
operator|.
name|getSubjects
argument_list|()
index|[
name|j
index|]
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|subjAbbvs
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
block|{
name|subjAbbvs
operator|=
name|subject
operator|.
name|getSubjectAreaAbbreviation
argument_list|()
expr_stmt|;
block|}
if|else if
condition|(
name|subjAbbvs
operator|.
name|length
argument_list|()
operator|<
literal|40
condition|)
block|{
name|subjAbbvs
operator|+=
literal|"_"
operator|+
name|subject
operator|.
name|getSubjectAreaAbbreviation
argument_list|()
expr_stmt|;
block|}
if|else if
condition|(
operator|!
operator|(
name|subjAbbvs
operator|.
name|charAt
argument_list|(
name|subjAbbvs
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
operator|==
literal|'.'
operator|)
condition|)
block|{
name|subjAbbvs
operator|+=
literal|"_..."
expr_stmt|;
block|}
name|subjectAreas
operator|.
name|add
argument_list|(
name|subject
argument_list|)
expr_stmt|;
block|}
name|File
name|file
init|=
name|ApplicationProperties
operator|.
name|getTempFile
argument_list|(
name|name
operator|+
name|subjAbbvs
argument_list|,
operator|(
name|myForm
operator|.
name|getModeIdx
argument_list|()
operator|==
name|PdfEnrollmentAuditReport
operator|.
name|sModeText
condition|?
literal|"txt"
else|:
literal|"pdf"
operator|)
argument_list|)
decl_stmt|;
name|myForm
operator|.
name|log
argument_list|(
literal|"&nbsp;&nbsp;Writing<a href='temp/"
operator|+
name|file
operator|.
name|getName
argument_list|()
operator|+
literal|"'>"
operator|+
name|subjAbbvs
operator|+
literal|"_"
operator|+
name|reportName
operator|+
literal|"."
operator|+
operator|(
name|myForm
operator|.
name|getModeIdx
argument_list|()
operator|==
name|PdfEnrollmentAuditReport
operator|.
name|sModeText
condition|?
literal|"txt"
else|:
literal|"pdf"
operator|)
operator|+
literal|"</a>"
argument_list|)
expr_stmt|;
name|PdfEnrollmentAuditReport
name|report
init|=
operator|(
name|PdfEnrollmentAuditReport
operator|)
name|reportClass
operator|.
name|getConstructor
argument_list|(
name|int
operator|.
name|class
argument_list|,
name|File
operator|.
name|class
argument_list|,
name|Session
operator|.
name|class
argument_list|,
name|TreeSet
operator|.
name|class
argument_list|,
name|String
operator|.
name|class
argument_list|)
operator|.
name|newInstance
argument_list|(
name|myForm
operator|.
name|getModeIdx
argument_list|()
argument_list|,
name|file
argument_list|,
operator|new
name|SessionDAO
argument_list|()
operator|.
name|get
argument_list|(
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|)
argument_list|,
name|subjectAreas
argument_list|,
name|subjAbbvs
argument_list|)
decl_stmt|;
name|report
operator|.
name|setShowId
argument_list|(
name|myForm
operator|.
name|getExternalId
argument_list|()
argument_list|)
expr_stmt|;
name|report
operator|.
name|setShowName
argument_list|(
name|myForm
operator|.
name|getStudentName
argument_list|()
argument_list|)
expr_stmt|;
name|report
operator|.
name|printReport
argument_list|()
expr_stmt|;
name|report
operator|.
name|close
argument_list|()
expr_stmt|;
name|output
operator|.
name|put
argument_list|(
name|subjAbbvs
operator|+
literal|"_"
operator|+
name|reportName
operator|+
literal|"."
operator|+
operator|(
name|myForm
operator|.
name|getModeIdx
argument_list|()
operator|==
name|PdfEnrollmentAuditReport
operator|.
name|sModeText
condition|?
literal|"txt"
else|:
literal|"pdf"
operator|)
argument_list|,
name|file
argument_list|)
expr_stmt|;
block|}
block|}
name|byte
index|[]
name|buffer
init|=
operator|new
name|byte
index|[
literal|32
operator|*
literal|1024
index|]
decl_stmt|;
name|int
name|len
init|=
literal|0
decl_stmt|;
if|if
condition|(
name|output
operator|.
name|isEmpty
argument_list|()
condition|)
name|myForm
operator|.
name|log
argument_list|(
literal|"<font color='orange'>No report generated.</font>"
argument_list|)
expr_stmt|;
if|else if
condition|(
name|myForm
operator|.
name|getEmail
argument_list|()
condition|)
block|{
name|myForm
operator|.
name|log
argument_list|(
literal|"Sending email(s)..."
argument_list|)
expr_stmt|;
try|try
block|{
name|Email
name|mail
init|=
operator|new
name|Email
argument_list|()
decl_stmt|;
name|mail
operator|.
name|setSubject
argument_list|(
name|myForm
operator|.
name|getSubject
argument_list|()
operator|==
literal|null
condition|?
literal|"Enrollment Audit Report"
else|:
name|myForm
operator|.
name|getSubject
argument_list|()
argument_list|)
expr_stmt|;
name|mail
operator|.
name|setText
argument_list|(
operator|(
name|myForm
operator|.
name|getMessage
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|myForm
operator|.
name|getMessage
argument_list|()
operator|+
literal|"\r\n\r\n"
operator|)
operator|+
literal|"For an up-to-date report, please visit "
operator|+
name|request
operator|.
name|getScheme
argument_list|()
operator|+
literal|"://"
operator|+
name|request
operator|.
name|getServerName
argument_list|()
operator|+
literal|":"
operator|+
name|request
operator|.
name|getServerPort
argument_list|()
operator|+
name|request
operator|.
name|getContextPath
argument_list|()
operator|+
literal|"/\r\n\r\n"
operator|+
literal|"This email was automatically generated by "
operator|+
literal|"UniTime "
operator|+
name|Constants
operator|.
name|getVersion
argument_list|()
operator|+
literal|" (Univesity Timetabling Application, http://www.unitime.org)."
argument_list|)
expr_stmt|;
if|if
condition|(
name|myForm
operator|.
name|getAddress
argument_list|()
operator|!=
literal|null
condition|)
for|for
control|(
name|StringTokenizer
name|s
init|=
operator|new
name|StringTokenizer
argument_list|(
name|myForm
operator|.
name|getAddress
argument_list|()
argument_list|,
literal|";,\n\r "
argument_list|)
init|;
name|s
operator|.
name|hasMoreTokens
argument_list|()
condition|;
control|)
name|mail
operator|.
name|addRecipient
argument_list|(
name|s
operator|.
name|nextToken
argument_list|()
argument_list|,
literal|null
argument_list|)
expr_stmt|;
if|if
condition|(
name|myForm
operator|.
name|getCc
argument_list|()
operator|!=
literal|null
condition|)
for|for
control|(
name|StringTokenizer
name|s
init|=
operator|new
name|StringTokenizer
argument_list|(
name|myForm
operator|.
name|getCc
argument_list|()
argument_list|,
literal|";,\n\r "
argument_list|)
init|;
name|s
operator|.
name|hasMoreTokens
argument_list|()
condition|;
control|)
name|mail
operator|.
name|addRecipientCC
argument_list|(
name|s
operator|.
name|nextToken
argument_list|()
argument_list|,
literal|null
argument_list|)
expr_stmt|;
if|if
condition|(
name|myForm
operator|.
name|getBcc
argument_list|()
operator|!=
literal|null
condition|)
for|for
control|(
name|StringTokenizer
name|s
init|=
operator|new
name|StringTokenizer
argument_list|(
name|myForm
operator|.
name|getBcc
argument_list|()
argument_list|,
literal|";,\n\r "
argument_list|)
init|;
name|s
operator|.
name|hasMoreTokens
argument_list|()
condition|;
control|)
name|mail
operator|.
name|addRecipientBCC
argument_list|(
name|s
operator|.
name|nextToken
argument_list|()
argument_list|,
literal|null
argument_list|)
expr_stmt|;
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|String
argument_list|,
name|File
argument_list|>
name|entry
range|:
name|output
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|mail
operator|.
name|addAttachement
argument_list|(
name|entry
operator|.
name|getValue
argument_list|()
argument_list|,
name|session
operator|.
name|getAcademicTerm
argument_list|()
operator|+
name|session
operator|.
name|getSessionStartYear
argument_list|()
operator|+
literal|"_"
operator|+
name|entry
operator|.
name|getKey
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|mail
operator|.
name|send
argument_list|()
expr_stmt|;
name|myForm
operator|.
name|log
argument_list|(
literal|"Email sent."
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|myForm
operator|.
name|log
argument_list|(
literal|"<font color='red'>Unable to send email: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
operator|+
literal|"</font>"
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|output
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
literal|null
return|;
block|}
if|else if
condition|(
name|output
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
block|{
name|request
operator|.
name|setAttribute
argument_list|(
name|Constants
operator|.
name|REQUEST_OPEN_URL
argument_list|,
literal|"temp/"
operator|+
name|output
operator|.
name|elements
argument_list|()
operator|.
name|nextElement
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|FileInputStream
name|fis
init|=
literal|null
decl_stmt|;
name|ZipOutputStream
name|zip
init|=
literal|null
decl_stmt|;
try|try
block|{
name|File
name|zipFile
init|=
name|ApplicationProperties
operator|.
name|getTempFile
argument_list|(
name|session
operator|.
name|getAcademicTerm
argument_list|()
operator|+
name|session
operator|.
name|getSessionStartYear
argument_list|()
argument_list|,
literal|"zip"
argument_list|)
decl_stmt|;
name|myForm
operator|.
name|log
argument_list|(
literal|"Writing<a href='temp/"
operator|+
name|zipFile
operator|.
name|getName
argument_list|()
operator|+
literal|"'>"
operator|+
name|session
operator|.
name|getAcademicTerm
argument_list|()
operator|+
name|session
operator|.
name|getSessionStartYear
argument_list|()
operator|+
literal|".zip</a>..."
argument_list|)
expr_stmt|;
name|zip
operator|=
operator|new
name|ZipOutputStream
argument_list|(
operator|new
name|FileOutputStream
argument_list|(
name|zipFile
argument_list|)
argument_list|)
expr_stmt|;
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|String
argument_list|,
name|File
argument_list|>
name|entry
range|:
name|output
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|zip
operator|.
name|putNextEntry
argument_list|(
operator|new
name|ZipEntry
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|fis
operator|=
operator|new
name|FileInputStream
argument_list|(
name|entry
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
while|while
condition|(
operator|(
name|len
operator|=
name|fis
operator|.
name|read
argument_list|(
name|buffer
argument_list|)
operator|)
operator|>
literal|0
condition|)
name|zip
operator|.
name|write
argument_list|(
name|buffer
argument_list|,
literal|0
argument_list|,
name|len
argument_list|)
expr_stmt|;
name|fis
operator|.
name|close
argument_list|()
expr_stmt|;
name|fis
operator|=
literal|null
expr_stmt|;
name|zip
operator|.
name|closeEntry
argument_list|()
expr_stmt|;
block|}
name|zip
operator|.
name|flush
argument_list|()
expr_stmt|;
name|zip
operator|.
name|close
argument_list|()
expr_stmt|;
name|request
operator|.
name|setAttribute
argument_list|(
name|Constants
operator|.
name|REQUEST_OPEN_URL
argument_list|,
literal|"temp/"
operator|+
name|zipFile
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
if|if
condition|(
name|fis
operator|!=
literal|null
condition|)
name|fis
operator|.
name|close
argument_list|()
expr_stmt|;
if|if
condition|(
name|zip
operator|!=
literal|null
condition|)
name|zip
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
block|}
name|myForm
operator|.
name|log
argument_list|(
literal|"All done."
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|myForm
operator|.
name|log
argument_list|(
literal|"<font color='red'>Process failed: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
operator|+
literal|" (exception "
operator|+
name|e
operator|.
name|getClass
argument_list|()
operator|.
name|getName
argument_list|()
operator|+
literal|")</font>"
argument_list|)
expr_stmt|;
name|sLog
operator|.
name|error
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
name|errors
operator|.
name|add
argument_list|(
literal|"report"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
literal|"Unable to generate report, reason: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|saveErrors
argument_list|(
name|request
argument_list|,
name|errors
argument_list|)
expr_stmt|;
block|}
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
specifier|public
specifier|static
class|class
name|FileGenerator
implements|implements
name|InstructorExamReport
operator|.
name|FileGenerator
block|{
name|String
name|iName
decl_stmt|;
specifier|public
name|FileGenerator
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|iName
operator|=
name|name
expr_stmt|;
block|}
specifier|public
name|File
name|generate
parameter_list|(
name|String
name|prefix
parameter_list|,
name|String
name|ext
parameter_list|)
block|{
return|return
name|ApplicationProperties
operator|.
name|getTempFile
argument_list|(
name|iName
operator|+
literal|"_"
operator|+
name|prefix
argument_list|,
name|ext
argument_list|)
return|;
block|}
block|}
block|}
end_class

end_unit

