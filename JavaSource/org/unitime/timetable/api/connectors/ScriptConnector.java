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
name|api
operator|.
name|connectors
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
name|java
operator|.
name|io
operator|.
name|UnsupportedEncodingException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
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
name|javax
operator|.
name|activation
operator|.
name|DataSource
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|activation
operator|.
name|FileTypeMap
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
name|FileItemHeaders
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
name|springframework
operator|.
name|beans
operator|.
name|factory
operator|.
name|annotation
operator|.
name|Autowired
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
name|timetable
operator|.
name|api
operator|.
name|ApiConnector
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
name|api
operator|.
name|ApiHelper
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
name|api
operator|.
name|BinaryFileApiHelper
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
name|api
operator|.
name|BinaryFileApiHelper
operator|.
name|BinaryFile
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
name|UploadServlet
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
name|shared
operator|.
name|ScriptInterface
operator|.
name|ExecuteScriptRpcRequest
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
name|shared
operator|.
name|ScriptInterface
operator|.
name|QueueItemInterface
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
name|SavedHQL
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
name|Script
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
name|ScriptDAO
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
name|server
operator|.
name|script
operator|.
name|GetQueueTableBackend
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
name|server
operator|.
name|script
operator|.
name|ScriptExecution
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
name|SolverServerService
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
name|queue
operator|.
name|QueueItem
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
name|queue
operator|.
name|QueueMessage
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|Service
argument_list|(
literal|"/api/script"
argument_list|)
specifier|public
class|class
name|ScriptConnector
extends|extends
name|ApiConnector
block|{
annotation|@
name|Autowired
name|SolverServerService
name|solverServerService
decl_stmt|;
annotation|@
name|Override
specifier|public
name|void
name|doGet
parameter_list|(
name|ApiHelper
name|helper
parameter_list|)
throws|throws
name|IOException
block|{
name|helper
operator|.
name|getSessionContext
argument_list|()
operator|.
name|checkPermission
argument_list|(
name|Right
operator|.
name|Scripts
argument_list|)
expr_stmt|;
if|if
condition|(
name|helper
operator|.
name|getParameter
argument_list|(
literal|"output"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|DataSource
name|ds
init|=
name|solverServerService
operator|.
name|getQueueProcessor
argument_list|()
operator|.
name|getFile
argument_list|(
name|helper
operator|.
name|getParameter
argument_list|(
literal|"output"
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|ds
operator|==
literal|null
condition|)
name|helper
operator|.
name|sendError
argument_list|(
name|HttpServletResponse
operator|.
name|SC_NO_CONTENT
argument_list|,
literal|"No output found for task "
operator|+
name|helper
operator|.
name|getParameter
argument_list|(
literal|"output"
argument_list|)
argument_list|)
expr_stmt|;
else|else
name|helper
operator|.
name|setResponse
argument_list|(
operator|new
name|BinaryFileApiHelper
operator|.
name|BinaryFile
argument_list|(
name|IOUtils
operator|.
name|toByteArray
argument_list|(
name|ds
operator|.
name|getInputStream
argument_list|()
argument_list|)
argument_list|,
name|ds
operator|.
name|getContentType
argument_list|()
argument_list|,
name|ds
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|helper
operator|.
name|getParameter
argument_list|(
literal|"log"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|solverServerService
operator|.
name|getQueueProcessor
argument_list|()
operator|.
name|get
argument_list|(
name|helper
operator|.
name|getParameter
argument_list|(
literal|"log"
argument_list|)
argument_list|)
expr_stmt|;
name|QueueItem
name|item
init|=
name|solverServerService
operator|.
name|getQueueProcessor
argument_list|()
operator|.
name|get
argument_list|(
name|helper
operator|.
name|getParameter
argument_list|(
literal|"log"
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|item
operator|==
literal|null
condition|)
name|helper
operator|.
name|sendError
argument_list|(
name|HttpServletResponse
operator|.
name|SC_NO_CONTENT
argument_list|,
literal|"No task found for "
operator|+
name|helper
operator|.
name|getParameter
argument_list|(
literal|"output"
argument_list|)
argument_list|)
expr_stmt|;
else|else
block|{
name|String
name|log
init|=
literal|""
decl_stmt|;
for|for
control|(
name|QueueMessage
name|m
range|:
name|item
operator|.
name|getLog
argument_list|()
control|)
block|{
name|log
operator|+=
name|m
operator|.
name|toString
argument_list|()
operator|+
literal|"\n"
expr_stmt|;
block|}
name|helper
operator|.
name|setResponse
argument_list|(
operator|new
name|BinaryFileApiHelper
operator|.
name|BinaryFile
argument_list|(
name|log
operator|.
name|getBytes
argument_list|()
argument_list|,
literal|"plain/text"
argument_list|,
name|item
operator|.
name|getId
argument_list|()
operator|+
literal|".log"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
if|else if
condition|(
name|helper
operator|.
name|getParameter
argument_list|(
literal|"id"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|solverServerService
operator|.
name|getQueueProcessor
argument_list|()
operator|.
name|get
argument_list|(
name|helper
operator|.
name|getParameter
argument_list|(
literal|"id"
argument_list|)
argument_list|)
expr_stmt|;
name|QueueItem
name|item
init|=
name|solverServerService
operator|.
name|getQueueProcessor
argument_list|()
operator|.
name|get
argument_list|(
name|helper
operator|.
name|getParameter
argument_list|(
literal|"id"
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|item
operator|==
literal|null
condition|)
name|helper
operator|.
name|sendError
argument_list|(
name|HttpServletResponse
operator|.
name|SC_NO_CONTENT
argument_list|,
literal|"No task found for "
operator|+
name|helper
operator|.
name|getParameter
argument_list|(
literal|"output"
argument_list|)
argument_list|)
expr_stmt|;
name|helper
operator|.
name|setResponse
argument_list|(
name|GetQueueTableBackend
operator|.
name|convert
argument_list|(
name|item
argument_list|,
name|helper
operator|.
name|getSessionContext
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|helper
operator|.
name|getParameter
argument_list|(
literal|"finished"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|solverServerService
operator|.
name|getQueueProcessor
argument_list|()
operator|.
name|get
argument_list|(
name|helper
operator|.
name|getParameter
argument_list|(
literal|"finished"
argument_list|)
argument_list|)
expr_stmt|;
name|QueueItem
name|item
init|=
name|solverServerService
operator|.
name|getQueueProcessor
argument_list|()
operator|.
name|get
argument_list|(
name|helper
operator|.
name|getParameter
argument_list|(
literal|"finished"
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|item
operator|==
literal|null
condition|)
name|helper
operator|.
name|sendError
argument_list|(
name|HttpServletResponse
operator|.
name|SC_NO_CONTENT
argument_list|,
literal|"No task found for "
operator|+
name|helper
operator|.
name|getParameter
argument_list|(
literal|"output"
argument_list|)
argument_list|)
expr_stmt|;
else|else
name|helper
operator|.
name|setResponse
argument_list|(
operator|new
name|Boolean
argument_list|(
name|item
operator|.
name|finished
argument_list|()
operator|!=
literal|null
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|helper
operator|.
name|getParameter
argument_list|(
literal|"delete"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|Boolean
name|ret
init|=
name|solverServerService
operator|.
name|getQueueProcessor
argument_list|()
operator|.
name|remove
argument_list|(
name|helper
operator|.
name|getParameter
argument_list|(
literal|"delete"
argument_list|)
argument_list|)
decl_stmt|;
name|helper
operator|.
name|setResponse
argument_list|(
name|ret
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|helper
operator|.
name|getParameter
argument_list|(
literal|"script"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|doPost
argument_list|(
name|helper
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|List
argument_list|<
name|QueueItem
argument_list|>
name|items
init|=
name|solverServerService
operator|.
name|getQueueProcessor
argument_list|()
operator|.
name|getItems
argument_list|(
literal|null
argument_list|,
literal|null
argument_list|,
literal|"Script"
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|QueueItemInterface
argument_list|>
name|converted
init|=
operator|new
name|ArrayList
argument_list|<
name|QueueItemInterface
argument_list|>
argument_list|()
decl_stmt|;
if|if
condition|(
name|items
operator|!=
literal|null
condition|)
for|for
control|(
name|QueueItem
name|item
range|:
name|items
control|)
name|converted
operator|.
name|add
argument_list|(
name|GetQueueTableBackend
operator|.
name|convert
argument_list|(
name|item
argument_list|,
name|helper
operator|.
name|getSessionContext
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|helper
operator|.
name|setResponse
argument_list|(
name|converted
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|doPost
parameter_list|(
name|ApiHelper
name|helper
parameter_list|)
throws|throws
name|IOException
block|{
name|helper
operator|.
name|getSessionContext
argument_list|()
operator|.
name|checkPermissionAnyAuthority
argument_list|(
name|Right
operator|.
name|Scripts
argument_list|)
expr_stmt|;
name|Long
name|sessionId
init|=
name|helper
operator|.
name|getAcademicSessionId
argument_list|()
decl_stmt|;
if|if
condition|(
name|sessionId
operator|==
literal|null
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Academic session not provided, please set the term parameter."
argument_list|)
throw|;
name|Session
name|session
init|=
name|SessionDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|sessionId
argument_list|,
name|helper
operator|.
name|getHibSession
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|session
operator|==
literal|null
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Given academic session no longer exists."
argument_list|)
throw|;
name|String
name|scriptName
init|=
name|helper
operator|.
name|getParameter
argument_list|(
literal|"script"
argument_list|)
decl_stmt|;
if|if
condition|(
name|scriptName
operator|==
literal|null
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"SCRIPT parameter not provided."
argument_list|)
throw|;
name|Script
name|script
init|=
operator|(
name|Script
operator|)
name|ScriptDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"from Script where name = :name"
argument_list|)
operator|.
name|setString
argument_list|(
literal|"name"
argument_list|,
name|scriptName
argument_list|)
operator|.
name|uniqueResult
argument_list|()
decl_stmt|;
if|if
condition|(
name|script
operator|==
literal|null
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Script "
operator|+
name|scriptName
operator|+
literal|" does not exist."
argument_list|)
throw|;
name|ExecuteScriptRpcRequest
name|request
init|=
operator|new
name|ExecuteScriptRpcRequest
argument_list|()
decl_stmt|;
name|request
operator|.
name|setScriptId
argument_list|(
name|script
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|request
operator|.
name|setScriptName
argument_list|(
name|script
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|request
operator|.
name|setEmail
argument_list|(
name|helper
operator|.
name|getParameter
argument_list|(
literal|"email"
argument_list|)
argument_list|)
expr_stmt|;
for|for
control|(
name|ScriptParameter
name|parameter
range|:
name|script
operator|.
name|getParameters
argument_list|()
control|)
block|{
if|if
condition|(
literal|"file"
operator|.
name|equals
argument_list|(
name|parameter
operator|.
name|getType
argument_list|()
argument_list|)
condition|)
block|{
specifier|final
name|BinaryFile
name|file
init|=
name|helper
operator|.
name|getRequest
argument_list|(
name|BinaryFile
operator|.
name|class
argument_list|)
decl_stmt|;
if|if
condition|(
name|file
operator|==
literal|null
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Input file not provided."
argument_list|)
throw|;
name|helper
operator|.
name|getSessionContext
argument_list|()
operator|.
name|setAttribute
argument_list|(
name|UploadServlet
operator|.
name|SESSION_LAST_FILE
argument_list|,
operator|new
name|BinaryFileItem
argument_list|(
name|file
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|String
index|[]
name|values
init|=
name|helper
operator|.
name|getParameterValues
argument_list|(
name|parameter
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|values
operator|==
literal|null
operator|||
name|values
operator|.
name|length
operator|==
literal|0
condition|)
continue|continue;
name|String
name|value
init|=
literal|""
decl_stmt|;
for|for
control|(
name|String
name|v
range|:
name|values
control|)
name|value
operator|+=
operator|(
name|value
operator|.
name|isEmpty
argument_list|()
condition|?
literal|""
else|:
literal|","
operator|)
operator|+
name|convertParameter
argument_list|(
name|v
argument_list|,
name|parameter
operator|.
name|getType
argument_list|()
argument_list|,
name|helper
argument_list|)
expr_stmt|;
if|if
condition|(
name|value
operator|!=
literal|null
condition|)
name|request
operator|.
name|setParameter
argument_list|(
name|parameter
operator|.
name|getName
argument_list|()
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
operator|!
name|helper
operator|.
name|getOptinalParameterBoolean
argument_list|(
literal|"queue"
argument_list|,
literal|true
argument_list|)
condition|)
block|{
name|ScriptExecution
name|item
init|=
operator|new
name|ScriptExecution
argument_list|(
name|request
argument_list|,
name|helper
operator|.
name|getSessionContext
argument_list|()
argument_list|)
decl_stmt|;
name|item
operator|.
name|executeItem
argument_list|()
expr_stmt|;
if|if
condition|(
name|item
operator|.
name|hasOutput
argument_list|()
condition|)
block|{
name|FileInputStream
name|is
init|=
operator|new
name|FileInputStream
argument_list|(
name|item
operator|.
name|output
argument_list|()
argument_list|)
decl_stmt|;
name|helper
operator|.
name|setResponse
argument_list|(
operator|new
name|BinaryFile
argument_list|(
name|IOUtils
operator|.
name|toByteArray
argument_list|(
name|is
argument_list|)
argument_list|,
name|FileTypeMap
operator|.
name|getDefaultFileTypeMap
argument_list|()
operator|.
name|getContentType
argument_list|(
name|item
operator|.
name|output
argument_list|()
argument_list|)
argument_list|,
name|item
operator|.
name|getOutputName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|is
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|String
name|log
init|=
literal|""
decl_stmt|;
for|for
control|(
name|QueueMessage
name|m
range|:
name|item
operator|.
name|getLog
argument_list|()
control|)
block|{
name|log
operator|+=
name|m
operator|.
name|toString
argument_list|()
operator|+
literal|"\n"
expr_stmt|;
block|}
name|helper
operator|.
name|setResponse
argument_list|(
operator|new
name|BinaryFileApiHelper
operator|.
name|BinaryFile
argument_list|(
name|log
operator|.
name|getBytes
argument_list|()
argument_list|,
literal|"plain/text"
argument_list|,
name|item
operator|.
name|getId
argument_list|()
operator|+
literal|".log"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|QueueItem
name|item
init|=
name|solverServerService
operator|.
name|getQueueProcessor
argument_list|()
operator|.
name|add
argument_list|(
operator|new
name|ScriptExecution
argument_list|(
name|request
argument_list|,
name|helper
operator|.
name|getSessionContext
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
name|helper
operator|.
name|setResponse
argument_list|(
name|GetQueueTableBackend
operator|.
name|convert
argument_list|(
name|item
argument_list|,
name|helper
operator|.
name|getSessionContext
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
specifier|protected
name|String
name|convertParameter
parameter_list|(
name|String
name|value
parameter_list|,
name|String
name|type
parameter_list|,
name|ApiHelper
name|helper
parameter_list|)
block|{
for|for
control|(
name|SavedHQL
operator|.
name|Option
name|option
range|:
name|SavedHQL
operator|.
name|Option
operator|.
name|values
argument_list|()
control|)
block|{
if|if
condition|(
name|type
operator|.
name|equalsIgnoreCase
argument_list|(
name|option
operator|.
name|name
argument_list|()
argument_list|)
condition|)
block|{
name|Long
name|id
init|=
name|option
operator|.
name|lookupValue
argument_list|(
name|helper
operator|.
name|getSessionContext
argument_list|()
operator|.
name|getUser
argument_list|()
argument_list|,
name|value
argument_list|)
decl_stmt|;
if|if
condition|(
name|id
operator|==
literal|null
condition|)
block|{
try|try
block|{
name|id
operator|=
name|Long
operator|.
name|valueOf
argument_list|(
name|value
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|e
parameter_list|)
block|{
block|}
block|}
if|if
condition|(
name|id
operator|!=
literal|null
condition|)
return|return
name|id
operator|.
name|toString
argument_list|()
return|;
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
name|option
operator|.
name|text
argument_list|()
operator|+
literal|" "
operator|+
name|value
operator|+
literal|" not found."
argument_list|)
throw|;
block|}
block|}
return|return
name|value
return|;
block|}
annotation|@
name|Override
specifier|protected
name|ApiHelper
name|createHelper
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|HttpServletResponse
name|response
parameter_list|)
block|{
return|return
operator|new
name|BinaryFileApiHelper
argument_list|(
name|request
argument_list|,
name|response
argument_list|,
name|sessionContext
argument_list|,
name|getCacheMode
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|protected
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"script"
return|;
block|}
specifier|public
specifier|static
class|class
name|BinaryFileItem
implements|implements
name|FileItem
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
name|BinaryFile
name|iFile
decl_stmt|;
specifier|public
name|BinaryFileItem
parameter_list|(
name|BinaryFile
name|file
parameter_list|)
block|{
name|iFile
operator|=
name|file
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|setHeaders
parameter_list|(
name|FileItemHeaders
name|headers
parameter_list|)
block|{
block|}
annotation|@
name|Override
specifier|public
name|FileItemHeaders
name|getHeaders
parameter_list|()
block|{
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|write
parameter_list|(
name|File
name|file
parameter_list|)
throws|throws
name|Exception
block|{
block|}
annotation|@
name|Override
specifier|public
name|void
name|setFormField
parameter_list|(
name|boolean
name|state
parameter_list|)
block|{
block|}
annotation|@
name|Override
specifier|public
name|void
name|setFieldName
parameter_list|(
name|String
name|name
parameter_list|)
block|{
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isInMemory
parameter_list|()
block|{
return|return
literal|true
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isFormField
parameter_list|()
block|{
return|return
literal|false
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getString
parameter_list|(
name|String
name|encoding
parameter_list|)
throws|throws
name|UnsupportedEncodingException
block|{
return|return
operator|new
name|String
argument_list|(
name|iFile
operator|.
name|getBytes
argument_list|()
argument_list|,
name|encoding
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getString
parameter_list|()
block|{
return|return
operator|new
name|String
argument_list|(
name|iFile
operator|.
name|getBytes
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|long
name|getSize
parameter_list|()
block|{
return|return
name|iFile
operator|.
name|getBytes
argument_list|()
operator|.
name|length
return|;
block|}
annotation|@
name|Override
specifier|public
name|OutputStream
name|getOutputStream
parameter_list|()
throws|throws
name|IOException
block|{
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|iFile
operator|.
name|getFileName
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|InputStream
name|getInputStream
parameter_list|()
throws|throws
name|IOException
block|{
return|return
operator|new
name|ByteArrayInputStream
argument_list|(
name|iFile
operator|.
name|getBytes
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getFieldName
parameter_list|()
block|{
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getContentType
parameter_list|()
block|{
return|return
name|iFile
operator|.
name|getContentType
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|byte
index|[]
name|get
parameter_list|()
block|{
return|return
name|iFile
operator|.
name|getBytes
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|delete
parameter_list|()
block|{
block|}
block|}
block|}
end_class

end_unit

