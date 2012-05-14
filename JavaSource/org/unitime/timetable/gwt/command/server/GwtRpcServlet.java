begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.4 (University Timetabling Application)  * Copyright (C) 2012, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|command
operator|.
name|server
package|;
end_package

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
name|javax
operator|.
name|servlet
operator|.
name|ServletException
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|cpsolver
operator|.
name|ifs
operator|.
name|util
operator|.
name|JProf
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
name|logging
operator|.
name|Log
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
name|logging
operator|.
name|LogFactory
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
name|timetable
operator|.
name|filter
operator|.
name|QueryLogFilter
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
name|command
operator|.
name|client
operator|.
name|GwtRpcCancelledException
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
name|command
operator|.
name|client
operator|.
name|GwtRpcImplementedBy
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
name|command
operator|.
name|client
operator|.
name|GwtRpcRequest
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
name|command
operator|.
name|client
operator|.
name|GwtRpcException
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
name|command
operator|.
name|client
operator|.
name|GwtRpcResponse
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
name|command
operator|.
name|client
operator|.
name|GwtRpcService
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
name|QueryLog
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|gwt
operator|.
name|user
operator|.
name|client
operator|.
name|rpc
operator|.
name|IsSerializable
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|gwt
operator|.
name|user
operator|.
name|server
operator|.
name|rpc
operator|.
name|RemoteServiceServlet
import|;
end_import

begin_class
specifier|public
class|class
name|GwtRpcServlet
extends|extends
name|RemoteServiceServlet
implements|implements
name|GwtRpcService
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
name|Log
name|sLog
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|GwtRpcServlet
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|QueryLogFilter
operator|.
name|Saver
name|iSaver
init|=
literal|null
decl_stmt|;
specifier|private
specifier|static
name|IdGenerator
name|sIdGenerator
init|=
operator|new
name|IdGenerator
argument_list|()
decl_stmt|;
specifier|private
specifier|static
name|Map
argument_list|<
name|Long
argument_list|,
name|Execution
argument_list|>
name|sExecutions
init|=
operator|new
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|Execution
argument_list|>
argument_list|()
decl_stmt|;
annotation|@
name|Override
specifier|public
name|void
name|init
parameter_list|()
throws|throws
name|ServletException
block|{
name|iSaver
operator|=
operator|new
name|QueryLogFilter
operator|.
name|Saver
argument_list|()
expr_stmt|;
name|iSaver
operator|.
name|setName
argument_list|(
literal|"GwtRpcLogSaver"
argument_list|)
expr_stmt|;
name|iSaver
operator|.
name|start
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|destroy
parameter_list|()
block|{
if|if
condition|(
name|iSaver
operator|!=
literal|null
condition|)
name|iSaver
operator|.
name|interrupt
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
parameter_list|<
name|T
extends|extends
name|GwtRpcResponse
parameter_list|>
name|T
name|execute
parameter_list|(
name|GwtRpcRequest
argument_list|<
name|T
argument_list|>
name|request
parameter_list|)
throws|throws
name|GwtRpcException
block|{
comment|// start time
name|long
name|t0
init|=
name|JProf
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
comment|// create helper
name|GwtRpcHelper
name|helper
init|=
operator|new
name|GwtRpcHelper
argument_list|(
name|getThreadLocalRequest
argument_list|()
operator|.
name|getSession
argument_list|()
argument_list|)
decl_stmt|;
try|try
block|{
comment|// retrieve implementation from given request
name|GwtRpcImplementedBy
name|annotation
init|=
name|request
operator|.
name|getClass
argument_list|()
operator|.
name|getAnnotation
argument_list|(
name|GwtRpcImplementedBy
operator|.
name|class
argument_list|)
decl_stmt|;
if|if
condition|(
name|annotation
operator|==
literal|null
operator|||
name|annotation
operator|.
name|value
argument_list|()
operator|==
literal|null
operator|||
name|annotation
operator|.
name|value
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|GwtRpcException
argument_list|(
literal|"Request "
operator|+
name|request
operator|.
name|getClass
argument_list|()
operator|.
name|getName
argument_list|()
operator|.
name|substring
argument_list|(
name|request
operator|.
name|getClass
argument_list|()
operator|.
name|getName
argument_list|()
operator|.
name|lastIndexOf
argument_list|(
literal|'.'
argument_list|)
operator|+
literal|1
argument_list|)
operator|+
literal|" does not have the GwtRpcImplementedBy annotation."
argument_list|)
throw|;
block|}
name|GwtRpcImplementation
argument_list|<
name|GwtRpcRequest
argument_list|<
name|T
argument_list|>
argument_list|,
name|T
argument_list|>
name|implementation
init|=
operator|(
name|GwtRpcImplementation
argument_list|<
name|GwtRpcRequest
argument_list|<
name|T
argument_list|>
argument_list|,
name|T
argument_list|>
operator|)
name|Class
operator|.
name|forName
argument_list|(
name|annotation
operator|.
name|value
argument_list|()
argument_list|)
operator|.
name|newInstance
argument_list|()
decl_stmt|;
comment|// execute request
name|T
name|response
init|=
name|implementation
operator|.
name|execute
argument_list|(
name|request
argument_list|,
name|helper
argument_list|)
decl_stmt|;
comment|// log request
name|log
argument_list|(
name|request
argument_list|,
name|response
argument_list|,
literal|null
argument_list|,
name|JProf
operator|.
name|currentTimeMillis
argument_list|()
operator|-
name|t0
argument_list|,
name|helper
argument_list|)
expr_stmt|;
comment|// return response
return|return
name|response
return|;
block|}
catch|catch
parameter_list|(
name|Throwable
name|t
parameter_list|)
block|{
comment|// log exception
name|log
argument_list|(
name|request
argument_list|,
literal|null
argument_list|,
name|t
argument_list|,
name|JProf
operator|.
name|currentTimeMillis
argument_list|()
operator|-
name|t0
argument_list|,
name|helper
argument_list|)
expr_stmt|;
comment|// re-throw exception as GwtRpcException or IsSerializable runtime exception
if|if
condition|(
name|t
operator|instanceof
name|GwtRpcException
condition|)
block|{
name|sLog
operator|.
name|info
argument_list|(
literal|"Seen server exception: "
operator|+
name|t
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
throw|throw
operator|(
name|GwtRpcException
operator|)
name|t
throw|;
block|}
if|if
condition|(
name|t
operator|instanceof
name|IsSerializable
condition|)
block|{
name|sLog
operator|.
name|warn
argument_list|(
literal|"Seen server exception: "
operator|+
name|t
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|GwtRpcException
argument_list|(
name|t
operator|.
name|getMessage
argument_list|()
argument_list|,
name|t
argument_list|)
throw|;
block|}
name|sLog
operator|.
name|error
argument_list|(
literal|"Seen exception: "
operator|+
name|t
operator|.
name|getMessage
argument_list|()
argument_list|,
name|t
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|GwtRpcException
argument_list|(
name|t
operator|.
name|getMessage
argument_list|()
argument_list|)
throw|;
block|}
block|}
specifier|private
parameter_list|<
name|T
extends|extends
name|GwtRpcResponse
parameter_list|>
name|void
name|log
parameter_list|(
name|GwtRpcRequest
argument_list|<
name|T
argument_list|>
name|request
parameter_list|,
name|T
name|response
parameter_list|,
name|Throwable
name|exception
parameter_list|,
name|long
name|time
parameter_list|,
name|GwtRpcHelper
name|helper
parameter_list|)
block|{
try|try
block|{
if|if
condition|(
name|iSaver
operator|==
literal|null
condition|)
return|return;
name|QueryLog
name|q
init|=
operator|new
name|QueryLog
argument_list|()
decl_stmt|;
name|String
name|requestName
init|=
name|request
operator|.
name|getClass
argument_list|()
operator|.
name|getName
argument_list|()
decl_stmt|;
if|if
condition|(
name|requestName
operator|.
name|indexOf
argument_list|(
literal|'.'
argument_list|)
operator|>=
literal|0
condition|)
name|requestName
operator|=
name|requestName
operator|.
name|substring
argument_list|(
name|requestName
operator|.
name|lastIndexOf
argument_list|(
literal|'.'
argument_list|)
operator|+
literal|1
argument_list|)
expr_stmt|;
name|q
operator|.
name|setUri
argument_list|(
literal|"RPC:"
operator|+
name|requestName
argument_list|)
expr_stmt|;
name|q
operator|.
name|setType
argument_list|(
name|QueryLog
operator|.
name|Type
operator|.
name|RPC
operator|.
name|ordinal
argument_list|()
argument_list|)
expr_stmt|;
name|q
operator|.
name|setTimeStamp
argument_list|(
operator|new
name|Date
argument_list|()
argument_list|)
expr_stmt|;
name|q
operator|.
name|setTimeSpent
argument_list|(
name|time
argument_list|)
expr_stmt|;
try|try
block|{
name|q
operator|.
name|setSessionId
argument_list|(
name|helper
operator|.
name|getHttpSessionId
argument_list|()
argument_list|)
expr_stmt|;
name|User
name|user
init|=
name|helper
operator|.
name|getUser
argument_list|()
decl_stmt|;
if|if
condition|(
name|user
operator|!=
literal|null
condition|)
name|q
operator|.
name|setUid
argument_list|(
name|user
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IllegalStateException
name|e
parameter_list|)
block|{
block|}
name|q
operator|.
name|setQuery
argument_list|(
name|request
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|exception
operator|!=
literal|null
condition|)
block|{
name|Throwable
name|t
init|=
name|exception
decl_stmt|;
name|String
name|ex
init|=
literal|""
decl_stmt|;
while|while
condition|(
name|t
operator|!=
literal|null
condition|)
block|{
name|String
name|clazz
init|=
name|t
operator|.
name|getClass
argument_list|()
operator|.
name|getName
argument_list|()
decl_stmt|;
if|if
condition|(
name|clazz
operator|.
name|indexOf
argument_list|(
literal|'.'
argument_list|)
operator|>=
literal|0
condition|)
name|clazz
operator|=
name|clazz
operator|.
name|substring
argument_list|(
literal|1
operator|+
name|clazz
operator|.
name|lastIndexOf
argument_list|(
literal|'.'
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|ex
operator|.
name|isEmpty
argument_list|()
condition|)
name|ex
operator|+=
literal|"\n"
expr_stmt|;
name|ex
operator|+=
name|clazz
operator|+
literal|": "
operator|+
name|t
operator|.
name|getMessage
argument_list|()
expr_stmt|;
if|if
condition|(
name|t
operator|.
name|getStackTrace
argument_list|()
operator|!=
literal|null
operator|&&
name|t
operator|.
name|getStackTrace
argument_list|()
operator|.
name|length
operator|>
literal|0
condition|)
name|ex
operator|+=
literal|" (at "
operator|+
name|t
operator|.
name|getStackTrace
argument_list|()
index|[
literal|0
index|]
operator|.
name|getFileName
argument_list|()
operator|+
literal|":"
operator|+
name|t
operator|.
name|getStackTrace
argument_list|()
index|[
literal|0
index|]
operator|.
name|getLineNumber
argument_list|()
operator|+
literal|")"
expr_stmt|;
name|t
operator|=
name|t
operator|.
name|getCause
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|ex
operator|.
name|isEmpty
argument_list|()
condition|)
name|q
operator|.
name|setException
argument_list|(
name|ex
argument_list|)
expr_stmt|;
block|}
name|iSaver
operator|.
name|add
argument_list|(
name|q
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Throwable
name|t
parameter_list|)
block|{
name|sLog
operator|.
name|warn
argument_list|(
literal|"Failed to log a request: "
operator|+
name|t
operator|.
name|getMessage
argument_list|()
argument_list|,
name|t
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
parameter_list|<
name|T
extends|extends
name|GwtRpcResponse
parameter_list|>
name|Long
name|executeAsync
parameter_list|(
name|GwtRpcRequest
argument_list|<
name|T
argument_list|>
name|request
parameter_list|)
throws|throws
name|GwtRpcException
block|{
try|try
block|{
name|Execution
argument_list|<
name|GwtRpcRequest
argument_list|<
name|T
argument_list|>
argument_list|,
name|T
argument_list|>
name|execution
init|=
operator|new
name|Execution
argument_list|<
name|GwtRpcRequest
argument_list|<
name|T
argument_list|>
argument_list|,
name|T
argument_list|>
argument_list|(
name|request
argument_list|)
decl_stmt|;
synchronized|synchronized
init|(
name|sExecutions
init|)
block|{
name|sExecutions
operator|.
name|put
argument_list|(
name|execution
operator|.
name|getExecutionId
argument_list|()
argument_list|,
name|execution
argument_list|)
expr_stmt|;
block|}
name|execution
operator|.
name|start
argument_list|()
expr_stmt|;
return|return
name|execution
operator|.
name|getExecutionId
argument_list|()
return|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|sLog
operator|.
name|warn
argument_list|(
literal|"Execute async failed: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|GwtRpcException
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
annotation|@
name|Override
specifier|public
parameter_list|<
name|T
extends|extends
name|GwtRpcResponse
parameter_list|>
name|T
name|waitForResults
parameter_list|(
name|Long
name|executionId
parameter_list|)
throws|throws
name|GwtRpcException
block|{
try|try
block|{
name|Execution
argument_list|<
name|GwtRpcRequest
argument_list|<
name|T
argument_list|>
argument_list|,
name|T
argument_list|>
name|execution
init|=
literal|null
decl_stmt|;
synchronized|synchronized
init|(
name|sExecutions
init|)
block|{
name|execution
operator|=
name|sExecutions
operator|.
name|get
argument_list|(
name|executionId
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|execution
operator|==
literal|null
condition|)
throw|throw
operator|new
name|GwtRpcException
argument_list|(
literal|"No execution with given id found."
argument_list|)
throw|;
try|try
block|{
name|execution
operator|.
name|waitToFinish
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|InterruptedException
name|e
parameter_list|)
block|{
block|}
synchronized|synchronized
init|(
name|sExecutions
init|)
block|{
name|sExecutions
operator|.
name|remove
argument_list|(
name|executionId
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|execution
operator|.
name|getException
argument_list|()
operator|!=
literal|null
condition|)
throw|throw
name|execution
operator|.
name|getException
argument_list|()
throw|;
return|return
name|execution
operator|.
name|getResponse
argument_list|()
return|;
block|}
catch|catch
parameter_list|(
name|GwtRpcCancelledException
name|e
parameter_list|)
block|{
throw|throw
name|e
throw|;
block|}
catch|catch
parameter_list|(
name|GwtRpcException
name|e
parameter_list|)
block|{
throw|throw
name|e
throw|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|sLog
operator|.
name|warn
argument_list|(
literal|"Wait for results failed: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|GwtRpcException
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|Boolean
name|cancelExecution
parameter_list|(
name|Long
name|executionId
parameter_list|)
throws|throws
name|GwtRpcException
block|{
try|try
block|{
name|Execution
name|execution
init|=
literal|null
decl_stmt|;
synchronized|synchronized
init|(
name|sExecutions
init|)
block|{
name|execution
operator|=
name|sExecutions
operator|.
name|get
argument_list|(
name|executionId
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|execution
operator|==
literal|null
condition|)
return|return
literal|false
return|;
name|execution
operator|.
name|cancelExecution
argument_list|()
expr_stmt|;
return|return
literal|true
return|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|sLog
operator|.
name|warn
argument_list|(
literal|"Cancel execution failed: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|GwtRpcException
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
specifier|private
specifier|static
class|class
name|IdGenerator
block|{
name|long
name|iNextId
init|=
literal|0
decl_stmt|;
specifier|synchronized
name|Long
name|generatedId
parameter_list|()
block|{
return|return
name|iNextId
operator|++
return|;
block|}
block|}
specifier|private
class|class
name|Execution
parameter_list|<
name|R
extends|extends
name|GwtRpcRequest
parameter_list|<
name|T
parameter_list|>
parameter_list|,
name|T
extends|extends
name|GwtRpcResponse
parameter_list|>
extends|extends
name|Thread
block|{
name|R
name|iRequest
decl_stmt|;
name|T
name|iResponse
init|=
literal|null
decl_stmt|;
name|GwtRpcHelper
name|iHelper
init|=
literal|null
decl_stmt|;
name|GwtRpcException
name|iException
init|=
literal|null
decl_stmt|;
name|Thread
name|iWaitingThread
init|=
literal|null
decl_stmt|;
name|long
name|iExecutionId
decl_stmt|;
name|boolean
name|iRunning
init|=
literal|false
decl_stmt|;
name|Execution
parameter_list|(
name|R
name|request
parameter_list|)
block|{
name|setName
argument_list|(
literal|"RPC:"
operator|+
name|request
argument_list|)
expr_stmt|;
name|setDaemon
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|iRequest
operator|=
name|request
expr_stmt|;
name|iExecutionId
operator|=
name|sIdGenerator
operator|.
name|generatedId
argument_list|()
expr_stmt|;
name|iHelper
operator|=
operator|new
name|GwtRpcHelper
argument_list|(
name|getThreadLocalRequest
argument_list|()
operator|.
name|getSession
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|run
parameter_list|()
block|{
name|iRunning
operator|=
literal|true
expr_stmt|;
comment|// start time
name|long
name|t0
init|=
name|JProf
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
try|try
block|{
comment|// retrieve implementation from given request
name|GwtRpcImplementedBy
name|annotation
init|=
name|iRequest
operator|.
name|getClass
argument_list|()
operator|.
name|getAnnotation
argument_list|(
name|GwtRpcImplementedBy
operator|.
name|class
argument_list|)
decl_stmt|;
if|if
condition|(
name|annotation
operator|==
literal|null
operator|||
name|annotation
operator|.
name|value
argument_list|()
operator|==
literal|null
operator|||
name|annotation
operator|.
name|value
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|GwtRpcException
argument_list|(
literal|"Request "
operator|+
name|iRequest
operator|.
name|getClass
argument_list|()
operator|.
name|getName
argument_list|()
operator|.
name|substring
argument_list|(
name|iRequest
operator|.
name|getClass
argument_list|()
operator|.
name|getName
argument_list|()
operator|.
name|lastIndexOf
argument_list|(
literal|'.'
argument_list|)
operator|+
literal|1
argument_list|)
operator|+
literal|" does not have the GwtRpcImplementedBy annotation."
argument_list|)
throw|;
block|}
name|GwtRpcImplementation
argument_list|<
name|R
argument_list|,
name|T
argument_list|>
name|implementation
init|=
operator|(
name|GwtRpcImplementation
argument_list|<
name|R
argument_list|,
name|T
argument_list|>
operator|)
name|Class
operator|.
name|forName
argument_list|(
name|annotation
operator|.
name|value
argument_list|()
argument_list|)
operator|.
name|newInstance
argument_list|()
decl_stmt|;
comment|// execute request
name|iResponse
operator|=
name|implementation
operator|.
name|execute
argument_list|(
name|iRequest
argument_list|,
name|iHelper
argument_list|)
expr_stmt|;
comment|// log request
name|log
argument_list|(
name|iRequest
argument_list|,
name|iResponse
argument_list|,
literal|null
argument_list|,
name|JProf
operator|.
name|currentTimeMillis
argument_list|()
operator|-
name|t0
argument_list|,
name|iHelper
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Throwable
name|t
parameter_list|)
block|{
comment|// log exception
name|log
argument_list|(
name|iRequest
argument_list|,
literal|null
argument_list|,
name|t
argument_list|,
name|JProf
operator|.
name|currentTimeMillis
argument_list|()
operator|-
name|t0
argument_list|,
name|iHelper
argument_list|)
expr_stmt|;
comment|// re-throw exception as GwtRpcException or IsSerializable runtime exception
if|if
condition|(
name|t
operator|instanceof
name|GwtRpcException
condition|)
block|{
name|sLog
operator|.
name|info
argument_list|(
literal|"Seen server exception: "
operator|+
name|t
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
name|iException
operator|=
operator|(
name|GwtRpcException
operator|)
name|t
expr_stmt|;
block|}
if|else  if
condition|(
name|t
operator|instanceof
name|IsSerializable
condition|)
block|{
name|sLog
operator|.
name|warn
argument_list|(
literal|"Seen server exception: "
operator|+
name|t
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
name|iException
operator|=
operator|new
name|GwtRpcException
argument_list|(
name|t
operator|.
name|getMessage
argument_list|()
argument_list|,
name|t
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|sLog
operator|.
name|error
argument_list|(
literal|"Seen exception: "
operator|+
name|t
operator|.
name|getMessage
argument_list|()
argument_list|,
name|t
argument_list|)
expr_stmt|;
name|iException
operator|=
operator|new
name|GwtRpcException
argument_list|(
name|t
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
synchronized|synchronized
init|(
name|this
init|)
block|{
name|iWaitingThread
operator|=
literal|null
expr_stmt|;
name|iRunning
operator|=
literal|false
expr_stmt|;
name|iHelper
operator|=
literal|null
expr_stmt|;
block|}
block|}
name|void
name|waitToFinish
parameter_list|()
throws|throws
name|InterruptedException
block|{
synchronized|synchronized
init|(
name|this
init|)
block|{
if|if
condition|(
name|iRunning
condition|)
block|{
name|iWaitingThread
operator|=
name|Thread
operator|.
name|currentThread
argument_list|()
expr_stmt|;
name|join
argument_list|()
expr_stmt|;
block|}
block|}
block|}
name|void
name|cancelExecution
parameter_list|()
block|{
synchronized|synchronized
init|(
name|this
init|)
block|{
name|iException
operator|=
operator|new
name|GwtRpcCancelledException
argument_list|(
literal|"Operation cancelled by the user."
argument_list|)
expr_stmt|;
if|if
condition|(
name|iWaitingThread
operator|!=
literal|null
condition|)
name|iWaitingThread
operator|.
name|interrupt
argument_list|()
expr_stmt|;
block|}
name|interrupt
argument_list|()
expr_stmt|;
block|}
name|T
name|getResponse
parameter_list|()
block|{
return|return
name|iResponse
return|;
block|}
name|GwtRpcException
name|getException
parameter_list|()
block|{
return|return
name|iException
return|;
block|}
name|Long
name|getExecutionId
parameter_list|()
block|{
return|return
name|iExecutionId
return|;
block|}
block|}
block|}
end_class

end_unit

