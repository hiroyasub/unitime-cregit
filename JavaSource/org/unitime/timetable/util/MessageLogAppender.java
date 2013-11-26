begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.4 - 3.5 (University Timetabling Application)  * Copyright (C) 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
package|;
end_package

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
name|org
operator|.
name|apache
operator|.
name|log4j
operator|.
name|AppenderSkeleton
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
name|Level
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
name|spi
operator|.
name|LoggingEvent
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
name|MessageLog
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
name|MessageLogDAO
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|MessageLogAppender
extends|extends
name|AppenderSkeleton
block|{
specifier|private
name|Saver
name|iSaver
init|=
literal|null
decl_stmt|;
specifier|private
name|Level
name|iMinLevel
init|=
literal|null
decl_stmt|;
annotation|@
name|Override
specifier|public
name|void
name|close
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
name|boolean
name|requiresLayout
parameter_list|()
block|{
return|return
literal|false
return|;
block|}
specifier|private
name|Saver
name|getSaver
parameter_list|()
block|{
if|if
condition|(
name|iSaver
operator|==
literal|null
condition|)
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
block|}
return|return
name|iSaver
return|;
block|}
specifier|public
name|Level
name|getMinLevel
parameter_list|()
block|{
if|if
condition|(
name|iMinLevel
operator|==
literal|null
condition|)
block|{
name|iMinLevel
operator|=
name|Level
operator|.
name|toLevel
argument_list|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"unitime.message.log.level"
argument_list|,
name|Level
operator|.
name|WARN
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|,
name|Level
operator|.
name|WARN
argument_list|)
expr_stmt|;
block|}
return|return
name|iMinLevel
return|;
block|}
annotation|@
name|Override
specifier|protected
name|void
name|append
parameter_list|(
name|LoggingEvent
name|event
parameter_list|)
block|{
if|if
condition|(
operator|!
name|event
operator|.
name|getLevel
argument_list|()
operator|.
name|isGreaterOrEqual
argument_list|(
name|getMinLevel
argument_list|()
argument_list|)
condition|)
return|return;
if|if
condition|(
name|event
operator|.
name|getLogger
argument_list|()
operator|.
name|equals
argument_list|(
name|MessageLogAppender
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|)
condition|)
return|return;
name|MessageLog
name|m
init|=
operator|new
name|MessageLog
argument_list|()
decl_stmt|;
name|m
operator|.
name|setLevel
argument_list|(
name|event
operator|.
name|getLevel
argument_list|()
operator|.
name|toInt
argument_list|()
argument_list|)
expr_stmt|;
name|String
name|logger
init|=
name|event
operator|.
name|getLoggerName
argument_list|()
decl_stmt|;
if|if
condition|(
name|logger
operator|.
name|indexOf
argument_list|(
literal|'.'
argument_list|)
operator|>=
literal|0
condition|)
name|logger
operator|=
name|logger
operator|.
name|substring
argument_list|(
name|logger
operator|.
name|lastIndexOf
argument_list|(
literal|'.'
argument_list|)
operator|+
literal|1
argument_list|)
expr_stmt|;
name|m
operator|.
name|setLogger
argument_list|(
name|logger
operator|.
name|length
argument_list|()
operator|>
literal|255
condition|?
name|logger
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|255
argument_list|)
else|:
name|logger
argument_list|)
expr_stmt|;
name|m
operator|.
name|setMessage
argument_list|(
name|event
operator|.
name|getMessage
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
name|event
operator|.
name|getMessage
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|m
operator|.
name|setTimeStamp
argument_list|(
operator|new
name|Date
argument_list|(
name|event
operator|.
name|getTimeStamp
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|m
operator|.
name|setNdc
argument_list|(
name|event
operator|.
name|getNDC
argument_list|()
argument_list|)
expr_stmt|;
name|String
name|thread
init|=
name|event
operator|.
name|getThreadName
argument_list|()
decl_stmt|;
name|m
operator|.
name|setThread
argument_list|(
name|thread
operator|==
literal|null
condition|?
literal|null
else|:
name|thread
operator|.
name|length
argument_list|()
operator|>
literal|100
condition|?
name|thread
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|100
argument_list|)
else|:
name|thread
argument_list|)
expr_stmt|;
name|Throwable
name|t
init|=
operator|(
name|event
operator|.
name|getThrowableInformation
argument_list|()
operator|!=
literal|null
condition|?
name|event
operator|.
name|getThrowableInformation
argument_list|()
operator|.
name|getThrowable
argument_list|()
else|:
literal|null
operator|)
decl_stmt|;
if|if
condition|(
name|t
operator|!=
literal|null
condition|)
block|{
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
name|m
operator|.
name|setException
argument_list|(
name|ex
argument_list|)
expr_stmt|;
block|}
name|getSaver
argument_list|()
operator|.
name|add
argument_list|(
name|m
argument_list|)
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
name|MessageLog
argument_list|>
name|iMessages
init|=
operator|new
name|Vector
argument_list|<
name|MessageLog
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
literal|5000
decl_stmt|;
specifier|private
name|int
name|iCleanupInterval
init|=
literal|180
decl_stmt|;
specifier|private
name|int
name|iCleanupDays
init|=
literal|14
decl_stmt|;
specifier|private
name|long
name|iCounter
init|=
literal|0
decl_stmt|;
specifier|public
name|Saver
parameter_list|()
block|{
name|super
argument_list|(
literal|"MessageLogSaver"
argument_list|)
expr_stmt|;
name|iLogLimit
operator|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"unitime.message.log.limit"
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|iLogLimit
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|iCleanupInterval
operator|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"unitime.message.log.cleanup.interval"
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|iCleanupInterval
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|iCleanupDays
operator|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"unitime.message.log.cleanup.days"
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|iCleanupDays
argument_list|)
argument_list|)
argument_list|)
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
name|MessageLog
name|m
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
name|iMessages
init|)
block|{
if|if
condition|(
name|iLogLimit
operator|<=
literal|0
operator|||
name|iMessages
operator|.
name|size
argument_list|()
operator|<
name|iLogLimit
condition|)
name|iMessages
operator|.
name|add
argument_list|(
name|m
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|void
name|run
parameter_list|()
block|{
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
name|iCounter
operator|++
expr_stmt|;
if|if
condition|(
operator|(
name|iCounter
operator|%
name|iCleanupInterval
operator|)
operator|==
literal|0
condition|)
name|LogCleaner
operator|.
name|cleanupMessageLog
argument_list|(
name|iCleanupDays
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|MessageLog
argument_list|>
name|messagesToSave
init|=
literal|null
decl_stmt|;
synchronized|synchronized
init|(
name|iMessages
init|)
block|{
if|if
condition|(
operator|!
name|iMessages
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|messagesToSave
operator|=
operator|new
name|ArrayList
argument_list|<
name|MessageLog
argument_list|>
argument_list|(
name|iMessages
argument_list|)
expr_stmt|;
name|iMessages
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
block|}
if|if
condition|(
name|messagesToSave
operator|!=
literal|null
condition|)
block|{
name|Session
name|hibSession
init|=
name|MessageLogDAO
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
name|MessageLog
name|m
range|:
name|messagesToSave
control|)
name|hibSession
operator|.
name|save
argument_list|(
name|m
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
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"Failed to persist "
operator|+
name|messagesToSave
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
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"Failed to persist log entries:"
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
block|}
end_class

end_unit

