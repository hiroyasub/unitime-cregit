begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 - 3.5 (University Timetabling Application)  * Copyright (C) 2010 - 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|util
operator|.
name|queue
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
name|util
operator|.
name|Date
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
name|log4j
operator|.
name|Logger
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|localization
operator|.
name|impl
operator|.
name|Localization
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
name|_RootDAO
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
comment|/**  *   * @author Tomas Muller  *  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|QueueItem
implements|implements
name|Log
block|{
specifier|protected
specifier|static
name|Logger
name|iLogger
decl_stmt|;
specifier|private
name|Long
name|iSessionId
decl_stmt|;
specifier|private
name|String
name|iOwnerId
decl_stmt|;
specifier|private
name|String
name|iOwnerName
decl_stmt|;
specifier|private
name|String
name|iOwnerEmail
decl_stmt|;
specifier|private
name|File
name|iOutput
init|=
literal|null
decl_stmt|;
specifier|private
name|String
name|iLog
init|=
literal|""
decl_stmt|;
specifier|private
name|String
name|iStatus
init|=
literal|"Waiting..."
decl_stmt|;
specifier|private
name|Date
name|iCreated
init|=
operator|new
name|Date
argument_list|()
decl_stmt|,
name|iStarted
init|=
literal|null
decl_stmt|,
name|iFinished
init|=
literal|null
decl_stmt|;
specifier|private
name|Throwable
name|iException
init|=
literal|null
decl_stmt|;
specifier|private
name|String
name|iLocale
init|=
literal|null
decl_stmt|;
specifier|private
name|Long
name|iId
init|=
literal|null
decl_stmt|;
specifier|public
name|QueueItem
parameter_list|(
name|Long
name|sessionId
parameter_list|,
name|UserContext
name|owner
parameter_list|)
block|{
name|iLogger
operator|=
name|Logger
operator|.
name|getLogger
argument_list|(
name|getClass
argument_list|()
argument_list|)
expr_stmt|;
name|iSessionId
operator|=
name|sessionId
expr_stmt|;
name|iOwnerId
operator|=
name|owner
operator|.
name|getExternalUserId
argument_list|()
expr_stmt|;
name|iOwnerName
operator|=
name|owner
operator|.
name|getName
argument_list|()
expr_stmt|;
name|iOwnerEmail
operator|=
name|owner
operator|.
name|getEmail
argument_list|()
expr_stmt|;
name|iLocale
operator|=
name|Localization
operator|.
name|getLocale
argument_list|()
expr_stmt|;
block|}
specifier|public
name|QueueItem
parameter_list|(
name|Session
name|session
parameter_list|,
name|UserContext
name|owner
parameter_list|)
block|{
name|this
argument_list|(
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|owner
argument_list|)
expr_stmt|;
block|}
specifier|public
name|QueueItem
parameter_list|(
name|UserContext
name|owner
parameter_list|)
block|{
name|this
argument_list|(
name|owner
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|,
name|owner
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Long
name|getSessionId
parameter_list|()
block|{
return|return
name|iSessionId
return|;
block|}
specifier|public
name|Session
name|getSession
parameter_list|()
block|{
return|return
name|SessionDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|iSessionId
argument_list|)
return|;
block|}
specifier|public
name|String
name|getOwnerId
parameter_list|()
block|{
return|return
name|iOwnerId
return|;
block|}
specifier|public
name|String
name|getOwnerName
parameter_list|()
block|{
return|return
name|iOwnerName
return|;
block|}
specifier|public
name|boolean
name|hasOwnerEmail
parameter_list|()
block|{
return|return
name|iOwnerEmail
operator|!=
literal|null
operator|&&
operator|!
name|iOwnerEmail
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|String
name|getOwnerEmail
parameter_list|()
block|{
return|return
name|iOwnerEmail
return|;
block|}
specifier|public
name|String
name|getLocale
parameter_list|()
block|{
return|return
name|iLocale
return|;
block|}
specifier|public
specifier|abstract
name|String
name|type
parameter_list|()
function_decl|;
specifier|public
specifier|abstract
name|String
name|name
parameter_list|()
function_decl|;
specifier|public
specifier|abstract
name|double
name|progress
parameter_list|()
function_decl|;
specifier|protected
specifier|abstract
name|void
name|execute
parameter_list|()
throws|throws
name|Exception
function_decl|;
specifier|public
name|void
name|executeItem
parameter_list|()
block|{
name|iStarted
operator|=
operator|new
name|Date
argument_list|()
expr_stmt|;
name|ApplicationProperties
operator|.
name|setSessionId
argument_list|(
name|getSessionId
argument_list|()
argument_list|)
expr_stmt|;
name|Localization
operator|.
name|setLocale
argument_list|(
name|getLocale
argument_list|()
argument_list|)
expr_stmt|;
try|try
block|{
name|execute
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|fatal
argument_list|(
literal|"Execution failed."
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
finally|finally
block|{
name|ApplicationProperties
operator|.
name|setSessionId
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|_RootDAO
operator|.
name|closeCurrentThreadSessions
argument_list|()
expr_stmt|;
name|Localization
operator|.
name|removeLocale
argument_list|()
expr_stmt|;
block|}
name|iFinished
operator|=
operator|new
name|Date
argument_list|()
expr_stmt|;
name|iStatus
operator|=
literal|"All done."
expr_stmt|;
if|if
condition|(
name|iException
operator|!=
literal|null
condition|)
name|iStatus
operator|=
literal|"Failed ("
operator|+
name|iException
operator|.
name|getMessage
argument_list|()
operator|+
literal|")"
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasOutput
parameter_list|()
block|{
return|return
name|iOutput
operator|!=
literal|null
operator|&&
name|iOutput
operator|.
name|exists
argument_list|()
operator|&&
name|iOutput
operator|.
name|canRead
argument_list|()
return|;
block|}
specifier|public
name|File
name|output
parameter_list|()
block|{
return|return
name|iOutput
return|;
block|}
specifier|public
name|void
name|setOutput
parameter_list|(
name|File
name|output
parameter_list|)
block|{
name|iOutput
operator|=
name|output
expr_stmt|;
block|}
specifier|protected
name|File
name|createOutput
parameter_list|(
name|String
name|prefix
parameter_list|,
name|String
name|ext
parameter_list|)
block|{
if|if
condition|(
name|iOutput
operator|!=
literal|null
condition|)
throw|throw
operator|new
name|RuntimeException
argument_list|(
literal|"Output already created."
argument_list|)
throw|;
name|iOutput
operator|=
name|ApplicationProperties
operator|.
name|getTempFile
argument_list|(
name|prefix
argument_list|,
name|ext
argument_list|)
expr_stmt|;
return|return
name|iOutput
return|;
block|}
specifier|public
name|String
name|log
parameter_list|()
block|{
return|return
name|iLog
return|;
block|}
specifier|protected
name|void
name|log
parameter_list|(
name|String
name|message
parameter_list|)
block|{
if|if
condition|(
name|iLog
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|iLog
operator|+=
literal|"<br>"
expr_stmt|;
name|iLog
operator|+=
name|message
expr_stmt|;
block|}
specifier|protected
name|void
name|warn
parameter_list|(
name|String
name|message
parameter_list|)
block|{
if|if
condition|(
name|iLog
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|iLog
operator|+=
literal|"<br>"
expr_stmt|;
name|iLog
operator|+=
literal|"<font color='orange'>"
operator|+
name|message
operator|+
literal|"</font>"
expr_stmt|;
block|}
specifier|protected
name|void
name|error
parameter_list|(
name|String
name|message
parameter_list|)
block|{
if|if
condition|(
name|iLog
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|iLog
operator|+=
literal|"<br>"
expr_stmt|;
name|iLog
operator|+=
literal|"<font color='red'>"
operator|+
name|message
operator|+
literal|"</font>"
expr_stmt|;
block|}
specifier|protected
name|void
name|setStatus
parameter_list|(
name|String
name|status
parameter_list|)
block|{
name|iStatus
operator|=
name|status
expr_stmt|;
name|log
argument_list|(
literal|"<i>"
operator|+
name|iStatus
operator|+
literal|"</i>"
argument_list|)
expr_stmt|;
block|}
specifier|public
name|String
name|status
parameter_list|()
block|{
return|return
name|iStatus
return|;
block|}
specifier|public
name|boolean
name|hasError
parameter_list|()
block|{
return|return
name|iException
operator|!=
literal|null
return|;
block|}
specifier|protected
name|void
name|setError
parameter_list|(
name|Throwable
name|exception
parameter_list|)
block|{
name|iException
operator|=
name|exception
expr_stmt|;
block|}
specifier|public
name|Throwable
name|error
parameter_list|()
block|{
return|return
name|iException
return|;
block|}
specifier|public
name|Long
name|getId
parameter_list|()
block|{
return|return
name|iId
return|;
block|}
specifier|public
name|void
name|setId
parameter_list|(
name|Long
name|id
parameter_list|)
block|{
name|iId
operator|=
name|id
expr_stmt|;
block|}
specifier|public
name|Date
name|created
parameter_list|()
block|{
return|return
name|iCreated
return|;
block|}
specifier|public
name|Date
name|started
parameter_list|()
block|{
return|return
name|iStarted
return|;
block|}
specifier|public
name|Date
name|finished
parameter_list|()
block|{
return|return
name|iFinished
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|trace
parameter_list|(
name|Object
name|message
parameter_list|)
block|{
name|iLogger
operator|.
name|trace
argument_list|(
name|message
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|trace
parameter_list|(
name|Object
name|message
parameter_list|,
name|Throwable
name|exception
parameter_list|)
block|{
name|iLogger
operator|.
name|trace
argument_list|(
name|message
argument_list|,
name|exception
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|debug
parameter_list|(
name|Object
name|message
parameter_list|)
block|{
name|debug
argument_list|(
name|message
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|debug
parameter_list|(
name|Object
name|message
parameter_list|,
name|Throwable
name|exception
parameter_list|)
block|{
name|iLogger
operator|.
name|debug
argument_list|(
name|message
argument_list|,
name|exception
argument_list|)
expr_stmt|;
if|if
condition|(
name|exception
operator|==
literal|null
condition|)
block|{
if|if
condition|(
name|message
operator|!=
literal|null
condition|)
name|log
argument_list|(
name|message
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|message
operator|==
literal|null
condition|)
block|{
name|log
argument_list|(
name|exception
operator|.
name|getClass
argument_list|()
operator|.
name|getSimpleName
argument_list|()
operator|+
literal|": "
operator|+
name|exception
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|log
argument_list|(
name|message
operator|+
literal|" ("
operator|+
name|exception
operator|.
name|getClass
argument_list|()
operator|.
name|getSimpleName
argument_list|()
operator|+
literal|": "
operator|+
name|exception
operator|.
name|getMessage
argument_list|()
operator|+
literal|")"
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|warn
parameter_list|(
name|Object
name|message
parameter_list|)
block|{
name|warn
argument_list|(
name|message
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|warn
parameter_list|(
name|Object
name|message
parameter_list|,
name|Throwable
name|exception
parameter_list|)
block|{
name|iLogger
operator|.
name|warn
argument_list|(
name|message
argument_list|,
name|exception
argument_list|)
expr_stmt|;
if|if
condition|(
name|exception
operator|==
literal|null
condition|)
block|{
if|if
condition|(
name|message
operator|!=
literal|null
condition|)
name|warn
argument_list|(
name|message
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|message
operator|==
literal|null
condition|)
block|{
name|warn
argument_list|(
name|exception
operator|.
name|getClass
argument_list|()
operator|.
name|getSimpleName
argument_list|()
operator|+
literal|": "
operator|+
name|exception
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|warn
argument_list|(
name|message
operator|+
literal|" ("
operator|+
name|exception
operator|.
name|getClass
argument_list|()
operator|.
name|getSimpleName
argument_list|()
operator|+
literal|": "
operator|+
name|exception
operator|.
name|getMessage
argument_list|()
operator|+
literal|")"
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|info
parameter_list|(
name|Object
name|message
parameter_list|)
block|{
name|info
argument_list|(
name|message
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|info
parameter_list|(
name|Object
name|message
parameter_list|,
name|Throwable
name|exception
parameter_list|)
block|{
name|iLogger
operator|.
name|info
argument_list|(
name|message
argument_list|,
name|exception
argument_list|)
expr_stmt|;
if|if
condition|(
name|exception
operator|==
literal|null
condition|)
block|{
if|if
condition|(
name|message
operator|!=
literal|null
condition|)
name|log
argument_list|(
name|message
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|message
operator|==
literal|null
condition|)
block|{
name|log
argument_list|(
name|exception
operator|.
name|getClass
argument_list|()
operator|.
name|getSimpleName
argument_list|()
operator|+
literal|": "
operator|+
name|exception
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|log
argument_list|(
name|message
operator|+
literal|" ("
operator|+
name|exception
operator|.
name|getClass
argument_list|()
operator|.
name|getSimpleName
argument_list|()
operator|+
literal|": "
operator|+
name|exception
operator|.
name|getMessage
argument_list|()
operator|+
literal|")"
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|error
parameter_list|(
name|Object
name|message
parameter_list|)
block|{
name|error
argument_list|(
name|message
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|error
parameter_list|(
name|Object
name|message
parameter_list|,
name|Throwable
name|exception
parameter_list|)
block|{
name|iLogger
operator|.
name|error
argument_list|(
name|message
argument_list|,
name|exception
argument_list|)
expr_stmt|;
if|if
condition|(
name|exception
operator|==
literal|null
condition|)
block|{
if|if
condition|(
name|message
operator|!=
literal|null
condition|)
name|error
argument_list|(
name|message
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|message
operator|==
literal|null
condition|)
block|{
name|error
argument_list|(
name|exception
operator|.
name|getClass
argument_list|()
operator|.
name|getSimpleName
argument_list|()
operator|+
literal|": "
operator|+
name|exception
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|error
argument_list|(
name|message
operator|+
literal|" ("
operator|+
name|exception
operator|.
name|getClass
argument_list|()
operator|.
name|getSimpleName
argument_list|()
operator|+
literal|": "
operator|+
name|exception
operator|.
name|getMessage
argument_list|()
operator|+
literal|")"
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|fatal
parameter_list|(
name|Object
name|message
parameter_list|)
block|{
name|fatal
argument_list|(
name|message
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|fatal
parameter_list|(
name|Object
name|message
parameter_list|,
name|Throwable
name|exception
parameter_list|)
block|{
name|iLogger
operator|.
name|fatal
argument_list|(
name|message
argument_list|,
name|exception
argument_list|)
expr_stmt|;
if|if
condition|(
name|exception
operator|==
literal|null
condition|)
block|{
if|if
condition|(
name|message
operator|!=
literal|null
condition|)
name|error
argument_list|(
name|message
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|message
operator|==
literal|null
condition|)
block|{
name|error
argument_list|(
name|exception
operator|.
name|getClass
argument_list|()
operator|.
name|getSimpleName
argument_list|()
operator|+
literal|": "
operator|+
name|exception
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|error
argument_list|(
name|message
operator|+
literal|" ("
operator|+
name|exception
operator|.
name|getClass
argument_list|()
operator|.
name|getSimpleName
argument_list|()
operator|+
literal|": "
operator|+
name|exception
operator|.
name|getMessage
argument_list|()
operator|+
literal|")"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|exception
operator|!=
literal|null
condition|)
name|setError
argument_list|(
name|exception
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isDebugEnabled
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
name|isErrorEnabled
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
name|isFatalEnabled
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
name|isInfoEnabled
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
name|isTraceEnabled
parameter_list|()
block|{
return|return
literal|false
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isWarnEnabled
parameter_list|()
block|{
return|return
literal|true
return|;
block|}
block|}
end_class

end_unit

