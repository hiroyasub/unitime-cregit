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
name|filter
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|ByteArrayOutputStream
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
name|lang
operator|.
name|reflect
operator|.
name|InvocationHandler
import|;
end_import

begin_import
import|import
name|java
operator|.
name|lang
operator|.
name|reflect
operator|.
name|Method
import|;
end_import

begin_import
import|import
name|java
operator|.
name|lang
operator|.
name|reflect
operator|.
name|Proxy
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
name|Date
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
name|HashSet
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
name|Vector
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|Filter
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|FilterChain
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|FilterConfig
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
name|ServletInputStream
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|ServletRequest
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|ServletResponse
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
name|hibernate
operator|.
name|CacheMode
import|;
end_import

begin_import
import|import
name|org
operator|.
name|hibernate
operator|.
name|Session
import|;
end_import

begin_import
import|import
name|org
operator|.
name|hibernate
operator|.
name|Transaction
import|;
end_import

begin_import
import|import
name|org
operator|.
name|springframework
operator|.
name|security
operator|.
name|core
operator|.
name|Authentication
import|;
end_import

begin_import
import|import
name|org
operator|.
name|springframework
operator|.
name|security
operator|.
name|core
operator|.
name|context
operator|.
name|SecurityContextHolder
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
name|model
operator|.
name|QueryLog
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
name|QueryLogDAO
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
name|UserContext
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|QueryLogFilter
implements|implements
name|Filter
block|{
specifier|private
specifier|static
name|Log
name|sLog
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|QueryLogFilter
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|Saver
name|iSaver
decl_stmt|;
specifier|private
name|HashSet
argument_list|<
name|String
argument_list|>
name|iExclude
init|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
specifier|public
name|void
name|init
parameter_list|(
name|FilterConfig
name|cfg
parameter_list|)
throws|throws
name|ServletException
block|{
name|iSaver
operator|=
operator|new
name|Saver
argument_list|()
expr_stmt|;
name|iSaver
operator|.
name|start
argument_list|()
expr_stmt|;
name|String
name|exclude
init|=
name|cfg
operator|.
name|getInitParameter
argument_list|(
literal|"exclude"
argument_list|)
decl_stmt|;
if|if
condition|(
name|exclude
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|String
name|x
range|:
name|exclude
operator|.
name|split
argument_list|(
literal|","
argument_list|)
control|)
name|iExclude
operator|.
name|add
argument_list|(
name|x
argument_list|)
expr_stmt|;
block|}
block|}
specifier|private
name|UserContext
name|getUser
parameter_list|()
block|{
name|Authentication
name|authentication
init|=
name|SecurityContextHolder
operator|.
name|getContext
argument_list|()
operator|.
name|getAuthentication
argument_list|()
decl_stmt|;
if|if
condition|(
name|authentication
operator|!=
literal|null
operator|&&
name|authentication
operator|.
name|isAuthenticated
argument_list|()
operator|&&
name|authentication
operator|.
name|getPrincipal
argument_list|()
operator|instanceof
name|UserContext
condition|)
return|return
operator|(
name|UserContext
operator|)
name|authentication
operator|.
name|getPrincipal
argument_list|()
return|;
return|return
literal|null
return|;
block|}
specifier|public
name|void
name|doFilter
parameter_list|(
name|ServletRequest
name|request
parameter_list|,
name|ServletResponse
name|response
parameter_list|,
name|FilterChain
name|chain
parameter_list|)
throws|throws
name|ServletException
throws|,
name|IOException
block|{
name|String
name|sessionId
init|=
literal|null
decl_stmt|;
name|String
name|userId
init|=
literal|null
decl_stmt|;
try|try
block|{
if|if
condition|(
name|request
operator|instanceof
name|HttpServletRequest
condition|)
block|{
name|HttpServletRequest
name|r
init|=
operator|(
name|HttpServletRequest
operator|)
name|request
decl_stmt|;
name|sessionId
operator|=
name|r
operator|.
name|getSession
argument_list|()
operator|.
name|getId
argument_list|()
expr_stmt|;
name|UserContext
name|user
init|=
name|getUser
argument_list|()
decl_stmt|;
if|if
condition|(
name|user
operator|!=
literal|null
condition|)
name|userId
operator|=
name|user
operator|.
name|getExternalUserId
argument_list|()
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|IllegalStateException
name|e
parameter_list|)
block|{
block|}
name|HttpServletRequestWrapper
name|wrapper
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|request
operator|instanceof
name|HttpServletRequest
condition|)
block|{
name|HttpServletRequest
name|r
init|=
operator|(
name|HttpServletRequest
operator|)
name|request
decl_stmt|;
if|if
condition|(
name|r
operator|.
name|getRequestURI
argument_list|()
operator|.
name|endsWith
argument_list|(
literal|".gwt"
argument_list|)
condition|)
block|{
name|wrapper
operator|=
operator|new
name|HttpServletRequestWrapper
argument_list|(
name|r
argument_list|)
expr_stmt|;
name|request
operator|=
name|wrapper
operator|.
name|createRequest
argument_list|()
expr_stmt|;
block|}
block|}
name|long
name|t0
init|=
name|JProf
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
name|Throwable
name|exception
init|=
literal|null
decl_stmt|;
try|try
block|{
name|chain
operator|.
name|doFilter
argument_list|(
name|request
argument_list|,
name|response
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Throwable
name|t
parameter_list|)
block|{
name|exception
operator|=
name|t
expr_stmt|;
block|}
name|long
name|t1
init|=
name|JProf
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
if|if
condition|(
name|exception
operator|==
literal|null
condition|)
block|{
name|Object
name|ex
init|=
name|request
operator|.
name|getAttribute
argument_list|(
literal|"__exception"
argument_list|)
decl_stmt|;
if|if
condition|(
name|ex
operator|!=
literal|null
operator|&&
name|ex
operator|instanceof
name|Throwable
condition|)
name|exception
operator|=
operator|(
name|Throwable
operator|)
name|ex
expr_stmt|;
block|}
if|if
condition|(
name|request
operator|instanceof
name|HttpServletRequest
condition|)
block|{
name|HttpServletRequest
name|r
init|=
operator|(
name|HttpServletRequest
operator|)
name|request
decl_stmt|;
name|QueryLog
name|q
init|=
operator|new
name|QueryLog
argument_list|()
decl_stmt|;
name|String
name|uri
init|=
name|r
operator|.
name|getRequestURI
argument_list|()
decl_stmt|;
if|if
condition|(
name|uri
operator|.
name|indexOf
argument_list|(
literal|'/'
argument_list|)
operator|>=
literal|0
condition|)
name|uri
operator|=
name|uri
operator|.
name|substring
argument_list|(
name|uri
operator|.
name|lastIndexOf
argument_list|(
literal|'/'
argument_list|)
operator|+
literal|1
argument_list|)
expr_stmt|;
if|if
condition|(
name|uri
operator|.
name|endsWith
argument_list|(
literal|".do"
argument_list|)
condition|)
name|q
operator|.
name|setType
argument_list|(
name|QueryLog
operator|.
name|Type
operator|.
name|STRUCTS
operator|.
name|ordinal
argument_list|()
argument_list|)
expr_stmt|;
if|else if
condition|(
name|uri
operator|.
name|endsWith
argument_list|(
literal|".gwt"
argument_list|)
condition|)
name|q
operator|.
name|setType
argument_list|(
name|QueryLog
operator|.
name|Type
operator|.
name|GWT
operator|.
name|ordinal
argument_list|()
argument_list|)
expr_stmt|;
else|else
name|q
operator|.
name|setType
argument_list|(
name|QueryLog
operator|.
name|Type
operator|.
name|OTHER
operator|.
name|ordinal
argument_list|()
argument_list|)
expr_stmt|;
name|q
operator|.
name|setUri
argument_list|(
name|uri
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
name|t1
operator|-
name|t0
argument_list|)
expr_stmt|;
name|q
operator|.
name|setSessionId
argument_list|(
name|sessionId
argument_list|)
expr_stmt|;
name|q
operator|.
name|setUid
argument_list|(
name|userId
argument_list|)
expr_stmt|;
try|try
block|{
if|if
condition|(
name|sessionId
operator|==
literal|null
condition|)
name|q
operator|.
name|setSessionId
argument_list|(
name|r
operator|.
name|getSession
argument_list|()
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|userId
operator|==
literal|null
condition|)
block|{
name|UserContext
name|user
init|=
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
name|getExternalUserId
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|IllegalStateException
name|e
parameter_list|)
block|{
block|}
if|if
condition|(
name|wrapper
operator|!=
literal|null
operator|&&
name|wrapper
operator|.
name|getBody
argument_list|()
operator|!=
literal|null
condition|)
block|{
try|try
block|{
name|String
name|body
init|=
operator|new
name|String
argument_list|(
name|wrapper
operator|.
name|getBody
argument_list|()
argument_list|)
decl_stmt|;
name|q
operator|.
name|setQuery
argument_list|(
name|body
argument_list|)
expr_stmt|;
name|String
name|args
index|[]
init|=
name|body
operator|.
name|split
argument_list|(
literal|"\\|"
argument_list|)
decl_stmt|;
name|q
operator|.
name|setUri
argument_list|(
name|q
operator|.
name|getUri
argument_list|()
operator|+
literal|": "
operator|+
name|args
index|[
literal|5
index|]
operator|.
name|substring
argument_list|(
name|args
index|[
literal|5
index|]
operator|.
name|lastIndexOf
argument_list|(
literal|'.'
argument_list|)
operator|+
literal|1
argument_list|)
operator|+
literal|"#"
operator|+
name|args
index|[
literal|6
index|]
argument_list|)
expr_stmt|;
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
literal|"Error parsing GWT request body: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|String
name|params
init|=
literal|""
decl_stmt|;
for|for
control|(
name|Enumeration
name|e
init|=
name|r
operator|.
name|getParameterNames
argument_list|()
init|;
name|e
operator|.
name|hasMoreElements
argument_list|()
condition|;
control|)
block|{
name|String
name|n
init|=
operator|(
name|String
operator|)
name|e
operator|.
name|nextElement
argument_list|()
decl_stmt|;
if|if
condition|(
literal|"password"
operator|.
name|equals
argument_list|(
name|n
argument_list|)
operator|||
literal|"noCacheTS"
operator|.
name|equals
argument_list|(
name|n
argument_list|)
condition|)
continue|continue;
if|if
condition|(
operator|!
name|params
operator|.
name|isEmpty
argument_list|()
condition|)
name|params
operator|+=
literal|"&"
expr_stmt|;
name|params
operator|+=
name|n
operator|+
literal|"="
operator|+
name|r
operator|.
name|getParameter
argument_list|(
name|n
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|params
operator|.
name|isEmpty
argument_list|()
condition|)
name|q
operator|.
name|setQuery
argument_list|(
name|params
argument_list|)
expr_stmt|;
block|}
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
if|if
condition|(
operator|!
name|iExclude
operator|.
name|contains
argument_list|(
name|q
operator|.
name|getUri
argument_list|()
argument_list|)
operator|||
name|q
operator|.
name|getException
argument_list|()
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|iSaver
operator|!=
literal|null
condition|)
name|iSaver
operator|.
name|add
argument_list|(
name|q
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|exception
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|exception
operator|instanceof
name|ServletException
condition|)
throw|throw
operator|(
name|ServletException
operator|)
name|exception
throw|;
if|if
condition|(
name|exception
operator|instanceof
name|IOException
condition|)
throw|throw
operator|(
name|IOException
operator|)
name|exception
throw|;
if|if
condition|(
name|exception
operator|instanceof
name|RuntimeException
condition|)
throw|throw
operator|(
name|RuntimeException
operator|)
name|exception
throw|;
throw|throw
operator|new
name|ServletException
argument_list|(
name|exception
argument_list|)
throw|;
block|}
block|}
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
specifier|public
specifier|static
class|class
name|Saver
extends|extends
name|Thread
block|{
specifier|private
name|List
argument_list|<
name|QueryLog
argument_list|>
name|iQueries
init|=
operator|new
name|Vector
argument_list|<
name|QueryLog
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|boolean
name|iActive
init|=
literal|true
decl_stmt|;
specifier|private
name|int
name|iLogLimit
init|=
operator|-
literal|1
decl_stmt|;
specifier|public
name|Saver
parameter_list|()
block|{
name|super
argument_list|(
literal|"QueryLogSaver"
argument_list|)
expr_stmt|;
name|iLogLimit
operator|=
name|ApplicationProperty
operator|.
name|QueryLogLimit
operator|.
name|intValue
argument_list|()
expr_stmt|;
name|setDaemon
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|interrupt
parameter_list|()
block|{
name|iActive
operator|=
literal|false
expr_stmt|;
name|super
operator|.
name|interrupt
argument_list|()
expr_stmt|;
try|try
block|{
name|join
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
block|}
specifier|public
name|void
name|add
parameter_list|(
name|QueryLog
name|q
parameter_list|)
block|{
if|if
condition|(
operator|!
name|iActive
condition|)
return|return;
synchronized|synchronized
init|(
name|iQueries
init|)
block|{
if|if
condition|(
name|iLogLimit
operator|<=
literal|0
operator|||
name|iQueries
operator|.
name|size
argument_list|()
operator|<
name|iLogLimit
condition|)
name|iQueries
operator|.
name|add
argument_list|(
name|q
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|void
name|run
parameter_list|()
block|{
name|sLog
operator|.
name|debug
argument_list|(
literal|"Query Log Saver is up."
argument_list|)
expr_stmt|;
while|while
condition|(
literal|true
condition|)
block|{
try|try
block|{
try|try
block|{
name|sleep
argument_list|(
literal|60000
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|InterruptedException
name|e
parameter_list|)
block|{
block|}
name|List
argument_list|<
name|QueryLog
argument_list|>
name|queriesToSave
init|=
literal|null
decl_stmt|;
synchronized|synchronized
init|(
name|iQueries
init|)
block|{
if|if
condition|(
operator|!
name|iQueries
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|queriesToSave
operator|=
operator|new
name|ArrayList
argument_list|<
name|QueryLog
argument_list|>
argument_list|(
name|iQueries
argument_list|)
expr_stmt|;
name|iQueries
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
block|}
if|if
condition|(
name|queriesToSave
operator|!=
literal|null
condition|)
block|{
name|sLog
operator|.
name|debug
argument_list|(
literal|"Persisting "
operator|+
name|queriesToSave
operator|.
name|size
argument_list|()
operator|+
literal|" log entries..."
argument_list|)
expr_stmt|;
if|if
condition|(
name|iLogLimit
operator|>
literal|0
operator|&&
name|queriesToSave
operator|.
name|size
argument_list|()
operator|>=
name|iLogLimit
condition|)
name|sLog
operator|.
name|warn
argument_list|(
literal|"The limit of "
operator|+
name|iLogLimit
operator|+
literal|" unpersisted log messages was reached, some messages have been dropped."
argument_list|)
expr_stmt|;
name|Session
name|hibSession
init|=
name|QueryLogDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|createNewSession
argument_list|()
decl_stmt|;
name|hibSession
operator|.
name|setCacheMode
argument_list|(
name|CacheMode
operator|.
name|IGNORE
argument_list|)
expr_stmt|;
name|Transaction
name|tx
init|=
name|hibSession
operator|.
name|beginTransaction
argument_list|()
decl_stmt|;
try|try
block|{
for|for
control|(
name|QueryLog
name|q
range|:
name|queriesToSave
control|)
name|hibSession
operator|.
name|save
argument_list|(
name|q
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|flush
argument_list|()
expr_stmt|;
name|tx
operator|.
name|commit
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|tx
operator|.
name|rollback
argument_list|()
expr_stmt|;
name|sLog
operator|.
name|error
argument_list|(
literal|"Failed to persist "
operator|+
name|queriesToSave
operator|.
name|size
argument_list|()
operator|+
literal|" log entries:"
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
finally|finally
block|{
name|hibSession
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
block|}
if|if
condition|(
operator|!
name|iActive
condition|)
break|break;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|sLog
operator|.
name|error
argument_list|(
literal|"Failed to persist log entries:"
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
name|sLog
operator|.
name|debug
argument_list|(
literal|"Query Log Saver is down."
argument_list|)
expr_stmt|;
block|}
block|}
specifier|private
specifier|static
class|class
name|HttpServletRequestWrapper
implements|implements
name|InvocationHandler
block|{
specifier|private
name|HttpServletRequest
name|iRequest
decl_stmt|;
specifier|private
name|ServletInputStream
name|iInputStream
init|=
literal|null
decl_stmt|;
specifier|private
name|ByteArrayOutputStream
name|iBody
init|=
literal|null
decl_stmt|;
specifier|public
name|HttpServletRequestWrapper
parameter_list|(
name|HttpServletRequest
name|r
parameter_list|)
block|{
name|iRequest
operator|=
name|r
expr_stmt|;
block|}
specifier|public
name|HttpServletRequest
name|createRequest
parameter_list|()
block|{
return|return
operator|(
name|HttpServletRequest
operator|)
name|Proxy
operator|.
name|newProxyInstance
argument_list|(
name|QueryLogFilter
operator|.
name|class
operator|.
name|getClassLoader
argument_list|()
argument_list|,
operator|new
name|Class
index|[]
block|{
name|HttpServletRequest
operator|.
name|class
block|}
argument_list|,
name|this
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Object
name|invoke
parameter_list|(
name|Object
name|proxy
parameter_list|,
name|Method
name|method
parameter_list|,
name|Object
index|[]
name|args
parameter_list|)
throws|throws
name|Throwable
block|{
if|if
condition|(
literal|"getInputStream"
operator|.
name|equals
argument_list|(
name|method
operator|.
name|getName
argument_list|()
argument_list|)
operator|&&
name|method
operator|.
name|getParameterTypes
argument_list|()
operator|.
name|length
operator|==
literal|0
condition|)
return|return
name|getInputStream
argument_list|()
return|;
return|return
name|iRequest
operator|.
name|getClass
argument_list|()
operator|.
name|getMethod
argument_list|(
name|method
operator|.
name|getName
argument_list|()
argument_list|,
name|method
operator|.
name|getParameterTypes
argument_list|()
argument_list|)
operator|.
name|invoke
argument_list|(
name|iRequest
argument_list|,
name|args
argument_list|)
return|;
block|}
specifier|public
name|ServletInputStream
name|getInputStream
parameter_list|()
throws|throws
name|IOException
block|{
if|if
condition|(
name|iInputStream
operator|==
literal|null
condition|)
block|{
name|iBody
operator|=
operator|new
name|ByteArrayOutputStream
argument_list|()
expr_stmt|;
name|iInputStream
operator|=
operator|new
name|ServletInputStreamWrapper
argument_list|(
name|iRequest
operator|.
name|getInputStream
argument_list|()
argument_list|,
name|iBody
argument_list|)
expr_stmt|;
block|}
return|return
name|iInputStream
return|;
block|}
specifier|public
name|byte
index|[]
name|getBody
parameter_list|()
block|{
return|return
operator|(
name|iBody
operator|==
literal|null
condition|?
literal|null
else|:
name|iBody
operator|.
name|toByteArray
argument_list|()
operator|)
return|;
block|}
block|}
specifier|private
specifier|static
class|class
name|ServletInputStreamWrapper
extends|extends
name|ServletInputStream
block|{
specifier|private
name|InputStream
name|iInputStream
decl_stmt|;
specifier|private
name|OutputStream
name|iOutputStream
decl_stmt|;
specifier|public
name|ServletInputStreamWrapper
parameter_list|(
name|InputStream
name|in
parameter_list|,
name|OutputStream
name|out
parameter_list|)
block|{
name|iInputStream
operator|=
name|in
expr_stmt|;
name|iOutputStream
operator|=
name|out
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|read
parameter_list|()
throws|throws
name|IOException
block|{
name|int
name|out
init|=
name|iInputStream
operator|.
name|read
argument_list|()
decl_stmt|;
name|iOutputStream
operator|.
name|write
argument_list|(
name|out
argument_list|)
expr_stmt|;
return|return
name|out
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|close
parameter_list|()
throws|throws
name|IOException
block|{
name|iInputStream
operator|.
name|close
argument_list|()
expr_stmt|;
name|iOutputStream
operator|.
name|flush
argument_list|()
expr_stmt|;
name|iOutputStream
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

