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
name|io
operator|.
name|PrintWriter
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|StringWriter
import|;
end_import

begin_import
import|import
name|java
operator|.
name|text
operator|.
name|DecimalFormat
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Date
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Properties
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
name|activation
operator|.
name|DataHandler
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|activation
operator|.
name|FileDataSource
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|mail
operator|.
name|Authenticator
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|mail
operator|.
name|BodyPart
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|mail
operator|.
name|Multipart
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|mail
operator|.
name|PasswordAuthentication
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|mail
operator|.
name|Transport
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|mail
operator|.
name|Message
operator|.
name|RecipientType
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|mail
operator|.
name|internet
operator|.
name|InternetAddress
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|mail
operator|.
name|internet
operator|.
name|MimeBodyPart
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|mail
operator|.
name|internet
operator|.
name|MimeMessage
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|mail
operator|.
name|internet
operator|.
name|MimeMultipart
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
name|dom4j
operator|.
name|Document
import|;
end_import

begin_import
import|import
name|org
operator|.
name|dom4j
operator|.
name|io
operator|.
name|OutputFormat
import|;
end_import

begin_import
import|import
name|org
operator|.
name|dom4j
operator|.
name|io
operator|.
name|SAXReader
import|;
end_import

begin_import
import|import
name|org
operator|.
name|dom4j
operator|.
name|io
operator|.
name|XMLWriter
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
name|commons
operator|.
name|User
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
name|dataexchange
operator|.
name|DataExchangeHelper
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
name|DataImportForm
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
name|TimetableManager
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
comment|/**   * MyEclipse Struts  * Creation date: 01-24-2007  *   * XDoclet definition:  * @struts.action path="/dataImport" name="dataImportForm" input="/form/dataImport.jsp" scope="request" validate="true"  */
end_comment

begin_class
specifier|public
class|class
name|DataImportAction
extends|extends
name|Action
block|{
comment|// --------------------------------------------------------- Instance Variables
comment|// --------------------------------------------------------- Methods
comment|/**  	 * Method execute 	 * @param mapping 	 * @param form 	 * @param request 	 * @param response 	 * @return ActionForward 	 */
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
block|{
name|DataImportForm
name|myForm
init|=
operator|(
name|DataImportForm
operator|)
name|form
decl_stmt|;
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
name|StringWriter
name|log
init|=
operator|new
name|StringWriter
argument_list|()
decl_stmt|;
name|PrintWriter
name|out
init|=
operator|new
name|PrintWriter
argument_list|(
name|log
argument_list|)
decl_stmt|;
name|String
name|userId
init|=
operator|(
name|String
operator|)
name|request
operator|.
name|getSession
argument_list|()
operator|.
name|getAttribute
argument_list|(
literal|"authUserExtId"
argument_list|)
decl_stmt|;
name|User
name|user
init|=
name|Web
operator|.
name|getUser
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|)
decl_stmt|;
name|TimetableManager
name|manager
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|userId
operator|!=
literal|null
condition|)
block|{
name|manager
operator|=
name|TimetableManager
operator|.
name|findByExternalId
argument_list|(
name|userId
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|manager
operator|==
literal|null
operator|&&
name|user
operator|!=
literal|null
condition|)
block|{
name|manager
operator|=
name|TimetableManager
operator|.
name|getManager
argument_list|(
name|user
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
literal|"Import"
operator|.
name|equals
argument_list|(
name|op
argument_list|)
condition|)
block|{
comment|// Validate input
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
name|errors
operator|.
name|size
argument_list|()
operator|>
literal|0
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
literal|"display"
argument_list|)
return|;
block|}
try|try
block|{
name|out
operator|.
name|println
argument_list|(
literal|"Importing "
operator|+
name|myForm
operator|.
name|getFile
argument_list|()
operator|.
name|getFileName
argument_list|()
operator|+
literal|" ("
operator|+
name|myForm
operator|.
name|getFile
argument_list|()
operator|.
name|getFileSize
argument_list|()
operator|+
literal|" bytes)...<br>"
argument_list|)
expr_stmt|;
name|Long
name|start
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
name|DataExchangeHelper
operator|.
name|importDocument
argument_list|(
operator|(
operator|new
name|SAXReader
argument_list|()
operator|)
operator|.
name|read
argument_list|(
name|myForm
operator|.
name|getFile
argument_list|()
operator|.
name|getInputStream
argument_list|()
argument_list|)
argument_list|,
name|manager
argument_list|,
name|out
argument_list|)
expr_stmt|;
name|Long
name|stop
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"Import finished in "
operator|+
operator|new
name|DecimalFormat
argument_list|(
literal|"0.00"
argument_list|)
operator|.
name|format
argument_list|(
operator|(
name|stop
operator|-
name|start
operator|)
operator|/
literal|1000.0
argument_list|)
operator|+
literal|" seconds.<br>"
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|out
operator|.
name|println
argument_list|(
literal|"<font color='red'><b>Unable to import "
operator|+
name|myForm
operator|.
name|getFile
argument_list|()
operator|.
name|getFileName
argument_list|()
operator|+
literal|": "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
operator|+
literal|"</b></font><br>"
argument_list|)
expr_stmt|;
name|Debug
operator|.
name|error
argument_list|(
name|e
argument_list|)
expr_stmt|;
name|errors
operator|.
name|add
argument_list|(
literal|"document"
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
name|saveErrors
argument_list|(
name|request
argument_list|,
name|errors
argument_list|)
expr_stmt|;
block|}
block|}
name|File
name|xmlFile
init|=
literal|null
decl_stmt|;
name|String
name|xmlName
init|=
literal|null
decl_stmt|;
if|if
condition|(
literal|"Export"
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
name|errors
operator|.
name|size
argument_list|()
operator|>
literal|0
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
literal|"display"
argument_list|)
return|;
block|}
try|try
block|{
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
name|String
name|root
init|=
operator|(
name|myForm
operator|.
name|getExportCourses
argument_list|()
condition|?
literal|"offerings"
else|:
name|myForm
operator|.
name|getExportFinalExams
argument_list|()
operator|||
name|myForm
operator|.
name|getExportMidtermExams
argument_list|()
condition|?
literal|"exams"
else|:
literal|"timetable"
operator|)
decl_stmt|;
name|Properties
name|params
init|=
operator|new
name|Properties
argument_list|()
decl_stmt|;
comment|//TODO: checked OK, tested OK
name|xmlName
operator|=
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
name|root
expr_stmt|;
name|params
operator|.
name|setProperty
argument_list|(
literal|"tmtbl.export.exam"
argument_list|,
operator|(
name|myForm
operator|.
name|getExportCourses
argument_list|()
condition|?
literal|"false"
else|:
literal|"true"
operator|)
argument_list|)
expr_stmt|;
name|params
operator|.
name|setProperty
argument_list|(
literal|"tmtbl.export.timetable"
argument_list|,
operator|(
name|myForm
operator|.
name|getExportTimetable
argument_list|()
condition|?
literal|"true"
else|:
literal|"false"
operator|)
argument_list|)
expr_stmt|;
comment|//exam only
if|if
condition|(
name|myForm
operator|.
name|getExportFinalExams
argument_list|()
condition|)
block|{
if|if
condition|(
name|myForm
operator|.
name|getExportMidtermExams
argument_list|()
condition|)
block|{
name|params
operator|.
name|setProperty
argument_list|(
literal|"tmtbl.export.exam.type"
argument_list|,
literal|"all"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|params
operator|.
name|setProperty
argument_list|(
literal|"tmtbl.export.exam.type"
argument_list|,
literal|"final"
argument_list|)
expr_stmt|;
name|xmlName
operator|+=
literal|"_final"
expr_stmt|;
block|}
block|}
else|else
block|{
if|if
condition|(
name|myForm
operator|.
name|getExportMidtermExams
argument_list|()
condition|)
block|{
name|params
operator|.
name|setProperty
argument_list|(
literal|"tmtbl.export.exam.type"
argument_list|,
literal|"midterm"
argument_list|)
expr_stmt|;
name|xmlName
operator|+=
literal|"_midterm"
expr_stmt|;
block|}
else|else
block|{
name|params
operator|.
name|setProperty
argument_list|(
literal|"tmtbl.export.exam.type"
argument_list|,
literal|"none"
argument_list|)
expr_stmt|;
block|}
block|}
name|out
operator|.
name|println
argument_list|(
literal|"Exporting "
operator|+
name|root
operator|+
literal|"...<br>"
argument_list|)
expr_stmt|;
name|xmlName
operator|+=
literal|".xml"
expr_stmt|;
name|xmlFile
operator|=
name|ApplicationProperties
operator|.
name|getTempFile
argument_list|(
name|root
argument_list|,
literal|"xml"
argument_list|)
expr_stmt|;
name|Long
name|start
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
name|Document
name|document
init|=
name|DataExchangeHelper
operator|.
name|exportDocument
argument_list|(
name|root
argument_list|,
name|session
argument_list|,
name|params
argument_list|,
name|out
argument_list|)
decl_stmt|;
if|if
condition|(
name|document
operator|==
literal|null
condition|)
block|{
name|out
operator|.
name|println
argument_list|(
literal|"<font color='red'><b>XML document not created: unknown reason.</b></font><br>"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|FileOutputStream
name|fos
init|=
literal|null
decl_stmt|;
try|try
block|{
name|fos
operator|=
operator|new
name|FileOutputStream
argument_list|(
name|xmlFile
argument_list|)
expr_stmt|;
operator|(
operator|new
name|XMLWriter
argument_list|(
name|fos
argument_list|,
name|OutputFormat
operator|.
name|createPrettyPrint
argument_list|()
argument_list|)
operator|)
operator|.
name|write
argument_list|(
name|document
argument_list|)
expr_stmt|;
name|fos
operator|.
name|flush
argument_list|()
expr_stmt|;
name|fos
operator|.
name|close
argument_list|()
expr_stmt|;
name|fos
operator|=
literal|null
expr_stmt|;
block|}
finally|finally
block|{
try|try
block|{
if|if
condition|(
name|fos
operator|!=
literal|null
condition|)
name|fos
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|out
operator|.
name|println
argument_list|(
literal|"<font color='red'><b>Unable to create export file: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
operator|+
literal|"</b></font><br>"
argument_list|)
expr_stmt|;
block|}
block|}
block|}
name|Long
name|stop
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
if|if
condition|(
name|xmlFile
operator|.
name|exists
argument_list|()
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
name|xmlFile
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|out
operator|.
name|println
argument_list|(
literal|"Export finished in "
operator|+
operator|new
name|DecimalFormat
argument_list|(
literal|"0.00"
argument_list|)
operator|.
name|format
argument_list|(
operator|(
name|stop
operator|-
name|start
operator|)
operator|/
literal|1000.0
argument_list|)
operator|+
literal|" seconds.<br>"
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|out
operator|.
name|println
argument_list|(
literal|"<font color='red'><b>Export failed: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
operator|+
literal|"</b></font><br>"
argument_list|)
expr_stmt|;
name|Debug
operator|.
name|error
argument_list|(
name|e
argument_list|)
expr_stmt|;
name|errors
operator|.
name|add
argument_list|(
literal|"export"
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
name|saveErrors
argument_list|(
name|request
argument_list|,
name|errors
argument_list|)
expr_stmt|;
block|}
block|}
name|out
operator|.
name|flush
argument_list|()
expr_stmt|;
name|out
operator|.
name|close
argument_list|()
expr_stmt|;
name|myForm
operator|.
name|setLog
argument_list|(
name|log
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|op
operator|!=
literal|null
operator|&&
name|myForm
operator|.
name|getEmail
argument_list|()
operator|&&
name|myForm
operator|.
name|getAddress
argument_list|()
operator|!=
literal|null
operator|&&
name|myForm
operator|.
name|getAddress
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|myForm
operator|.
name|setEmail
argument_list|(
literal|false
argument_list|)
expr_stmt|;
try|try
block|{
name|InternetAddress
name|from
init|=
operator|(
name|manager
operator|.
name|getEmailAddress
argument_list|()
operator|==
literal|null
condition|?
operator|new
name|InternetAddress
argument_list|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.inquiry.sender"
argument_list|,
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.contact.email"
argument_list|)
argument_list|)
argument_list|,
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.inquiry.sender.name"
argument_list|)
argument_list|)
else|:
operator|new
name|InternetAddress
argument_list|(
name|manager
operator|.
name|getEmailAddress
argument_list|()
argument_list|,
name|manager
operator|.
name|getName
argument_list|()
argument_list|)
operator|)
decl_stmt|;
name|Properties
name|p
init|=
name|ApplicationProperties
operator|.
name|getProperties
argument_list|()
decl_stmt|;
if|if
condition|(
name|p
operator|.
name|getProperty
argument_list|(
literal|"mail.smtp.host"
argument_list|)
operator|==
literal|null
operator|&&
name|p
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.smtp.host"
argument_list|)
operator|!=
literal|null
condition|)
name|p
operator|.
name|setProperty
argument_list|(
literal|"mail.smtp.host"
argument_list|,
name|p
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.smtp.host"
argument_list|)
argument_list|)
expr_stmt|;
name|Authenticator
name|a
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.mail.user"
argument_list|)
operator|!=
literal|null
operator|&&
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.mail.pwd"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|a
operator|=
operator|new
name|Authenticator
argument_list|()
block|{
specifier|public
name|PasswordAuthentication
name|getPasswordAuthentication
parameter_list|()
block|{
return|return
operator|new
name|PasswordAuthentication
argument_list|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.mail.user"
argument_list|)
argument_list|,
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.mail.pwd"
argument_list|)
argument_list|)
return|;
block|}
block|}
expr_stmt|;
block|}
name|javax
operator|.
name|mail
operator|.
name|Session
name|mailSession
init|=
name|javax
operator|.
name|mail
operator|.
name|Session
operator|.
name|getDefaultInstance
argument_list|(
name|p
argument_list|,
name|a
argument_list|)
decl_stmt|;
name|MimeMessage
name|mail
init|=
operator|new
name|MimeMessage
argument_list|(
name|mailSession
argument_list|)
decl_stmt|;
name|mail
operator|.
name|setSubject
argument_list|(
literal|"Data exchange finished."
argument_list|)
expr_stmt|;
name|Multipart
name|body
init|=
operator|new
name|MimeMultipart
argument_list|()
decl_stmt|;
name|MimeBodyPart
name|text
init|=
operator|new
name|MimeBodyPart
argument_list|()
decl_stmt|;
name|text
operator|.
name|setContent
argument_list|(
name|log
operator|.
name|toString
argument_list|()
operator|+
literal|"<br><br>"
operator|+
literal|"This email was automatically generated at "
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
literal|", by "
operator|+
literal|"UniTime "
operator|+
name|Constants
operator|.
name|VERSION
operator|+
literal|"."
operator|+
name|Constants
operator|.
name|BLD_NUMBER
operator|.
name|replaceAll
argument_list|(
literal|"@build.number@"
argument_list|,
literal|"?"
argument_list|)
operator|+
literal|" (Univesity Timetabling Application, http://www.unitime.org)."
argument_list|,
literal|"text/html"
argument_list|)
expr_stmt|;
name|body
operator|.
name|addBodyPart
argument_list|(
name|text
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
name|RecipientType
operator|.
name|TO
argument_list|,
operator|new
name|InternetAddress
argument_list|(
name|s
operator|.
name|nextToken
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|from
operator|!=
literal|null
condition|)
name|mail
operator|.
name|setFrom
argument_list|(
name|from
argument_list|)
expr_stmt|;
if|if
condition|(
name|xmlFile
operator|!=
literal|null
condition|)
block|{
name|BodyPart
name|attachement
init|=
operator|new
name|MimeBodyPart
argument_list|()
decl_stmt|;
name|attachement
operator|.
name|setDataHandler
argument_list|(
operator|new
name|DataHandler
argument_list|(
operator|new
name|FileDataSource
argument_list|(
name|xmlFile
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|attachement
operator|.
name|setFileName
argument_list|(
name|xmlName
argument_list|)
expr_stmt|;
name|body
operator|.
name|addBodyPart
argument_list|(
name|attachement
argument_list|)
expr_stmt|;
block|}
name|mail
operator|.
name|setSentDate
argument_list|(
operator|new
name|Date
argument_list|()
argument_list|)
expr_stmt|;
name|mail
operator|.
name|setContent
argument_list|(
name|body
argument_list|)
expr_stmt|;
name|Transport
operator|.
name|send
argument_list|(
name|mail
argument_list|)
expr_stmt|;
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
name|ActionMessages
name|errors
init|=
operator|new
name|ActionErrors
argument_list|()
decl_stmt|;
name|errors
operator|.
name|add
argument_list|(
literal|"email"
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
literal|"display"
argument_list|)
return|;
block|}
block|}
end_class

end_unit

