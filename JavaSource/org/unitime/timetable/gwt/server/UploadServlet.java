begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.4 - 3.5 (University Timetabling Application)  * Copyright (C) 2012 - 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|gwt
operator|.
name|server
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
name|io
operator|.
name|OutputStream
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
name|util
operator|.
name|List
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
name|ServletException
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
name|HttpServlet
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
name|commons
operator|.
name|fileupload
operator|.
name|FileItem
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|fileupload
operator|.
name|FileUploadException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|fileupload
operator|.
name|disk
operator|.
name|DiskFileItemFactory
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|fileupload
operator|.
name|servlet
operator|.
name|ServletFileUpload
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
name|defaults
operator|.
name|ApplicationProperty
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
name|gwt
operator|.
name|server
operator|.
name|CalendarServlet
operator|.
name|HttpParams
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
name|gwt
operator|.
name|server
operator|.
name|CalendarServlet
operator|.
name|Params
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
name|gwt
operator|.
name|server
operator|.
name|CalendarServlet
operator|.
name|QParams
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
name|Event
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
name|EventNote
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
name|EventDAO
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

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|UploadServlet
extends|extends
name|HttpServlet
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|int
name|DEFAULT_MAX_SIZE
init|=
literal|4096
operator|*
literal|1024
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|SESSION_LAST_FILE
init|=
literal|"LAST_FILE"
decl_stmt|;
specifier|protected
name|SessionContext
name|getSessionContext
parameter_list|()
block|{
return|return
name|HttpSessionContext
operator|.
name|getSessionContext
argument_list|(
name|getServletContext
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|void
name|doGet
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|HttpServletResponse
name|response
parameter_list|)
throws|throws
name|ServletException
throws|,
name|IOException
block|{
name|Params
name|params
init|=
literal|null
decl_stmt|;
name|String
name|q
init|=
name|request
operator|.
name|getParameter
argument_list|(
literal|"q"
argument_list|)
decl_stmt|;
if|if
condition|(
name|q
operator|!=
literal|null
condition|)
block|{
name|params
operator|=
operator|new
name|QParams
argument_list|(
name|q
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|params
operator|=
operator|new
name|HttpParams
argument_list|(
name|request
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|params
operator|.
name|getParameter
argument_list|(
literal|"event"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|Long
name|eventId
init|=
name|Long
operator|.
name|parseLong
argument_list|(
name|params
operator|.
name|getParameter
argument_list|(
literal|"event"
argument_list|)
argument_list|)
decl_stmt|;
name|String
name|fileName
init|=
name|params
operator|.
name|getParameter
argument_list|(
literal|"name"
argument_list|)
decl_stmt|;
name|Long
name|noteId
init|=
operator|(
name|params
operator|.
name|getParameter
argument_list|(
literal|"note"
argument_list|)
operator|==
literal|null
condition|?
literal|null
else|:
name|Long
operator|.
name|valueOf
argument_list|(
name|params
operator|.
name|getParameter
argument_list|(
literal|"note"
argument_list|)
argument_list|)
operator|)
decl_stmt|;
if|if
condition|(
name|q
operator|==
literal|null
condition|)
name|getSessionContext
argument_list|()
operator|.
name|checkPermissionAnyAuthority
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|eventId
argument_list|)
argument_list|,
literal|"Event"
argument_list|,
name|Right
operator|.
name|EventDetail
argument_list|)
expr_stmt|;
name|Event
name|event
init|=
name|EventDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|eventId
argument_list|)
decl_stmt|;
name|TreeSet
argument_list|<
name|EventNote
argument_list|>
name|notes
init|=
operator|new
name|TreeSet
argument_list|<
name|EventNote
argument_list|>
argument_list|()
decl_stmt|;
if|if
condition|(
name|event
operator|!=
literal|null
condition|)
for|for
control|(
name|EventNote
name|note
range|:
name|event
operator|.
name|getNotes
argument_list|()
control|)
block|{
if|if
condition|(
name|note
operator|.
name|getAttachedName
argument_list|()
operator|==
literal|null
operator|||
name|note
operator|.
name|getAttachedName
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
continue|continue;
if|if
condition|(
name|fileName
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|fileName
operator|.
name|equals
argument_list|(
name|note
operator|.
name|getAttachedName
argument_list|()
argument_list|)
operator|&&
operator|(
name|noteId
operator|==
literal|null
operator|||
name|noteId
operator|.
name|equals
argument_list|(
name|note
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|)
condition|)
name|notes
operator|.
name|add
argument_list|(
name|note
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|noteId
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|noteId
operator|.
name|equals
argument_list|(
name|note
operator|.
name|getUniqueId
argument_list|()
argument_list|)
condition|)
name|notes
operator|.
name|add
argument_list|(
name|note
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|notes
operator|.
name|add
argument_list|(
name|note
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
operator|!
name|notes
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|EventNote
name|note
init|=
name|notes
operator|.
name|last
argument_list|()
decl_stmt|;
name|response
operator|.
name|setContentType
argument_list|(
name|note
operator|.
name|getAttachedContentType
argument_list|()
argument_list|)
expr_stmt|;
name|response
operator|.
name|setHeader
argument_list|(
literal|"Content-Disposition"
argument_list|,
literal|"attachment; filename=\""
operator|+
name|note
operator|.
name|getAttachedName
argument_list|()
operator|+
literal|"\""
argument_list|)
expr_stmt|;
name|OutputStream
name|out
init|=
name|response
operator|.
name|getOutputStream
argument_list|()
decl_stmt|;
name|out
operator|.
name|write
argument_list|(
name|note
operator|.
name|getAttachedFile
argument_list|()
argument_list|)
expr_stmt|;
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
return|return;
block|}
block|}
throw|throw
operator|new
name|ServletException
argument_list|(
literal|"Nothing to download."
argument_list|)
throw|;
block|}
specifier|public
name|void
name|doPost
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|HttpServletResponse
name|response
parameter_list|)
throws|throws
name|ServletException
throws|,
name|IOException
block|{
try|try
block|{
name|String
name|maxSizeProperty
init|=
name|ApplicationProperty
operator|.
name|MaxUploadSize
operator|.
name|value
argument_list|()
decl_stmt|;
name|int
name|maxSize
init|=
operator|(
name|maxSizeProperty
operator|==
literal|null
condition|?
name|DEFAULT_MAX_SIZE
else|:
name|Integer
operator|.
name|parseInt
argument_list|(
name|maxSizeProperty
argument_list|)
operator|)
decl_stmt|;
name|ServletFileUpload
name|upload
init|=
operator|new
name|ServletFileUpload
argument_list|(
operator|new
name|DiskFileItemFactory
argument_list|(
name|maxSize
argument_list|,
name|ApplicationProperties
operator|.
name|getTempFolder
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
name|upload
operator|.
name|setSizeMax
argument_list|(
name|maxSize
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|FileItem
argument_list|>
name|files
init|=
operator|(
name|List
argument_list|<
name|FileItem
argument_list|>
operator|)
name|upload
operator|.
name|parseRequest
argument_list|(
name|request
argument_list|)
decl_stmt|;
name|String
name|message
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|files
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
block|{
name|FileItem
name|file
init|=
name|files
operator|.
name|get
argument_list|(
literal|0
argument_list|)
decl_stmt|;
if|if
condition|(
name|file
operator|.
name|getSize
argument_list|()
operator|<=
literal|0
condition|)
block|{
name|request
operator|.
name|getSession
argument_list|()
operator|.
name|removeAttribute
argument_list|(
name|SESSION_LAST_FILE
argument_list|)
expr_stmt|;
name|message
operator|=
literal|"No file is selected."
expr_stmt|;
block|}
else|else
block|{
name|request
operator|.
name|getSession
argument_list|()
operator|.
name|setAttribute
argument_list|(
name|SESSION_LAST_FILE
argument_list|,
name|file
argument_list|)
expr_stmt|;
name|message
operator|=
literal|"File "
operator|+
name|file
operator|.
name|getName
argument_list|()
operator|+
literal|" ("
operator|+
name|file
operator|.
name|getSize
argument_list|()
operator|+
literal|" bytes) selected."
expr_stmt|;
block|}
block|}
else|else
block|{
name|request
operator|.
name|getSession
argument_list|()
operator|.
name|removeAttribute
argument_list|(
name|SESSION_LAST_FILE
argument_list|)
expr_stmt|;
name|message
operator|=
literal|"No file is selected."
expr_stmt|;
block|}
name|response
operator|.
name|setContentType
argument_list|(
literal|"text/html; charset=UTF-8"
argument_list|)
expr_stmt|;
name|response
operator|.
name|setCharacterEncoding
argument_list|(
literal|"UTF-8"
argument_list|)
expr_stmt|;
name|PrintWriter
name|out
init|=
name|response
operator|.
name|getWriter
argument_list|()
decl_stmt|;
name|out
operator|.
name|print
argument_list|(
name|message
argument_list|)
expr_stmt|;
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
block|}
catch|catch
parameter_list|(
name|FileUploadException
name|e
parameter_list|)
block|{
name|response
operator|.
name|setContentType
argument_list|(
literal|"text/html; charset=UTF-8"
argument_list|)
expr_stmt|;
name|response
operator|.
name|setCharacterEncoding
argument_list|(
literal|"UTF-8"
argument_list|)
expr_stmt|;
name|PrintWriter
name|out
init|=
name|response
operator|.
name|getWriter
argument_list|()
decl_stmt|;
name|out
operator|.
name|print
argument_list|(
literal|"ERROR:Upload failed: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
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
block|}
block|}
block|}
end_class

end_unit

