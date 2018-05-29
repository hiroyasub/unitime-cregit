begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to The Apereo Foundation under one or more contributor license  * agreements. See the NOTICE file distributed with this work for  * additional information regarding copyright ownership.  *  * The Apereo Foundation licenses this file to you under the Apache License,  * Version 2.0 (the "License"); you may not use this file except in  * compliance with the License. You may obtain a copy of the License at:  *  * http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *  * See the License for the specific language governing permissions and  * limitations under the License.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|server
operator|.
name|script
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|ByteArrayInputStream
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
name|InputStream
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
name|io
operator|.
name|IOUtils
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
name|PeriodicTask
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
name|ScriptParameter
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
name|TaskExecution
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
name|TaskParameter
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
name|PeriodicTaskDAO
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
name|TaskExecutionDAO
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
name|TaskOutputFileServlet
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
name|String
name|e
init|=
name|request
operator|.
name|getParameter
argument_list|(
literal|"e"
argument_list|)
decl_stmt|;
if|if
condition|(
name|e
operator|!=
literal|null
condition|)
block|{
name|TaskExecution
name|exec
init|=
name|TaskExecutionDAO
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
name|e
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|exec
operator|==
literal|null
condition|)
block|{
name|response
operator|.
name|sendError
argument_list|(
name|HttpServletResponse
operator|.
name|SC_BAD_REQUEST
argument_list|,
literal|"Task execution not found."
argument_list|)
expr_stmt|;
return|return;
block|}
if|if
condition|(
name|exec
operator|.
name|getOutputFile
argument_list|()
operator|==
literal|null
condition|)
block|{
name|response
operator|.
name|sendError
argument_list|(
name|HttpServletResponse
operator|.
name|SC_BAD_REQUEST
argument_list|,
literal|"Task execution has no output file."
argument_list|)
expr_stmt|;
return|return;
block|}
name|getSessionContext
argument_list|()
operator|.
name|checkPermission
argument_list|(
name|exec
operator|.
name|getTask
argument_list|()
operator|.
name|getSession
argument_list|()
argument_list|,
name|Right
operator|.
name|TaskDetail
argument_list|)
expr_stmt|;
if|if
condition|(
name|exec
operator|.
name|getTask
argument_list|()
operator|.
name|getScript
argument_list|()
operator|.
name|getPermission
argument_list|()
operator|!=
literal|null
condition|)
try|try
block|{
name|Right
name|right
init|=
name|Right
operator|.
name|valueOf
argument_list|(
name|exec
operator|.
name|getTask
argument_list|()
operator|.
name|getScript
argument_list|()
operator|.
name|getPermission
argument_list|()
operator|.
name|replace
argument_list|(
literal|" "
argument_list|,
literal|""
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|getSessionContext
argument_list|()
operator|.
name|hasPermission
argument_list|(
name|right
argument_list|)
condition|)
block|{
name|response
operator|.
name|sendError
argument_list|(
name|HttpServletResponse
operator|.
name|SC_BAD_REQUEST
argument_list|,
literal|"Missing permission "
operator|+
name|exec
operator|.
name|getTask
argument_list|()
operator|.
name|getScript
argument_list|()
operator|.
name|getPermission
argument_list|()
argument_list|)
expr_stmt|;
return|return;
block|}
block|}
catch|catch
parameter_list|(
name|IllegalArgumentException
name|ex
parameter_list|)
block|{
name|response
operator|.
name|sendError
argument_list|(
name|HttpServletResponse
operator|.
name|SC_BAD_REQUEST
argument_list|,
literal|"Bad permission "
operator|+
name|exec
operator|.
name|getTask
argument_list|()
operator|.
name|getScript
argument_list|()
operator|.
name|getPermission
argument_list|()
argument_list|)
expr_stmt|;
return|return;
block|}
name|response
operator|.
name|setContentType
argument_list|(
name|exec
operator|.
name|getOutputContentType
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
name|exec
operator|.
name|getOutputName
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
name|InputStream
name|in
init|=
operator|new
name|ByteArrayInputStream
argument_list|(
name|exec
operator|.
name|getOutputFile
argument_list|()
argument_list|)
decl_stmt|;
try|try
block|{
name|IOUtils
operator|.
name|copy
argument_list|(
name|in
argument_list|,
name|out
argument_list|)
expr_stmt|;
name|out
operator|.
name|flush
argument_list|()
expr_stmt|;
block|}
finally|finally
block|{
name|in
operator|.
name|close
argument_list|()
expr_stmt|;
name|out
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
return|return;
block|}
name|String
name|t
init|=
name|request
operator|.
name|getParameter
argument_list|(
literal|"t"
argument_list|)
decl_stmt|;
if|if
condition|(
name|t
operator|!=
literal|null
condition|)
block|{
name|PeriodicTask
name|task
init|=
name|PeriodicTaskDAO
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
name|t
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|task
operator|==
literal|null
condition|)
block|{
name|response
operator|.
name|sendError
argument_list|(
name|HttpServletResponse
operator|.
name|SC_BAD_REQUEST
argument_list|,
literal|"Task not found."
argument_list|)
expr_stmt|;
return|return;
block|}
if|if
condition|(
name|task
operator|.
name|getInputFile
argument_list|()
operator|==
literal|null
condition|)
block|{
name|response
operator|.
name|sendError
argument_list|(
name|HttpServletResponse
operator|.
name|SC_BAD_REQUEST
argument_list|,
literal|"Task has no input file."
argument_list|)
expr_stmt|;
return|return;
block|}
name|getSessionContext
argument_list|()
operator|.
name|checkPermission
argument_list|(
name|task
operator|.
name|getSession
argument_list|()
argument_list|,
name|Right
operator|.
name|TaskDetail
argument_list|)
expr_stmt|;
if|if
condition|(
name|task
operator|.
name|getScript
argument_list|()
operator|.
name|getPermission
argument_list|()
operator|!=
literal|null
condition|)
try|try
block|{
name|Right
name|right
init|=
name|Right
operator|.
name|valueOf
argument_list|(
name|task
operator|.
name|getScript
argument_list|()
operator|.
name|getPermission
argument_list|()
operator|.
name|replace
argument_list|(
literal|" "
argument_list|,
literal|""
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|getSessionContext
argument_list|()
operator|.
name|hasPermission
argument_list|(
name|right
argument_list|)
condition|)
block|{
name|response
operator|.
name|sendError
argument_list|(
name|HttpServletResponse
operator|.
name|SC_BAD_REQUEST
argument_list|,
literal|"Missing permission "
operator|+
name|task
operator|.
name|getScript
argument_list|()
operator|.
name|getPermission
argument_list|()
argument_list|)
expr_stmt|;
return|return;
block|}
block|}
catch|catch
parameter_list|(
name|IllegalArgumentException
name|ex
parameter_list|)
block|{
name|response
operator|.
name|sendError
argument_list|(
name|HttpServletResponse
operator|.
name|SC_BAD_REQUEST
argument_list|,
literal|"Bad permission "
operator|+
name|task
operator|.
name|getScript
argument_list|()
operator|.
name|getPermission
argument_list|()
argument_list|)
expr_stmt|;
return|return;
block|}
name|ScriptParameter
name|fp
init|=
literal|null
decl_stmt|;
for|for
control|(
name|ScriptParameter
name|p
range|:
name|task
operator|.
name|getScript
argument_list|()
operator|.
name|getParameters
argument_list|()
control|)
if|if
condition|(
literal|"file"
operator|.
name|equalsIgnoreCase
argument_list|(
name|p
operator|.
name|getType
argument_list|()
argument_list|)
condition|)
block|{
name|fp
operator|=
name|p
expr_stmt|;
break|break;
block|}
name|String
name|name
init|=
literal|"unknown.file"
decl_stmt|;
for|for
control|(
name|TaskParameter
name|p
range|:
name|task
operator|.
name|getParameters
argument_list|()
control|)
if|if
condition|(
name|fp
operator|!=
literal|null
operator|&&
name|fp
operator|.
name|getName
argument_list|()
operator|.
name|equals
argument_list|(
name|p
operator|.
name|getName
argument_list|()
argument_list|)
condition|)
name|name
operator|=
name|p
operator|.
name|getValue
argument_list|()
expr_stmt|;
name|response
operator|.
name|setContentType
argument_list|(
literal|"application/octet-stream"
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
name|name
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
name|InputStream
name|in
init|=
operator|new
name|ByteArrayInputStream
argument_list|(
name|task
operator|.
name|getInputFile
argument_list|()
argument_list|)
decl_stmt|;
try|try
block|{
name|IOUtils
operator|.
name|copy
argument_list|(
name|in
argument_list|,
name|out
argument_list|)
expr_stmt|;
name|out
operator|.
name|flush
argument_list|()
expr_stmt|;
block|}
finally|finally
block|{
name|in
operator|.
name|close
argument_list|()
expr_stmt|;
name|out
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
return|return;
block|}
name|response
operator|.
name|sendError
argument_list|(
name|HttpServletResponse
operator|.
name|SC_BAD_REQUEST
argument_list|,
literal|"Task or execution id not provided."
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit
