begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 (University Timetabling Application)  * Copyright (C) 2011, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|onlinesectioning
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
name|FileWriter
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
name|Hashtable
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
name|Student
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
name|OnlineSectioningLogDAO
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
name|StudentDAO
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|OnlineSectioningLogger
extends|extends
name|Thread
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
name|OnlineSectioningLogger
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|List
argument_list|<
name|OnlineSectioningLog
operator|.
name|Action
argument_list|>
name|iActions
init|=
operator|new
name|Vector
argument_list|<
name|OnlineSectioningLog
operator|.
name|Action
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|boolean
name|iActive
init|=
literal|false
decl_stmt|;
specifier|private
name|boolean
name|iEnabled
init|=
literal|false
decl_stmt|;
specifier|private
name|int
name|iLogLimit
init|=
operator|-
literal|1
decl_stmt|;
specifier|private
name|PrintWriter
name|iOut
init|=
literal|null
decl_stmt|;
specifier|private
specifier|static
name|OnlineSectioningLogger
name|sInstance
init|=
literal|null
decl_stmt|;
specifier|public
specifier|static
name|OnlineSectioningLogger
name|getInstance
parameter_list|()
block|{
if|if
condition|(
name|sInstance
operator|==
literal|null
condition|)
name|startLogger
argument_list|()
expr_stmt|;
return|return
name|sInstance
return|;
block|}
specifier|public
specifier|static
name|void
name|startLogger
parameter_list|()
block|{
if|if
condition|(
name|sInstance
operator|==
literal|null
condition|)
block|{
name|sInstance
operator|=
operator|new
name|OnlineSectioningLogger
argument_list|()
expr_stmt|;
name|sInstance
operator|.
name|start
argument_list|()
expr_stmt|;
block|}
block|}
specifier|public
specifier|static
name|void
name|stopLogger
parameter_list|()
block|{
if|if
condition|(
name|sInstance
operator|!=
literal|null
condition|)
block|{
name|sInstance
operator|.
name|iActive
operator|=
literal|false
expr_stmt|;
name|sInstance
operator|.
name|interrupt
argument_list|()
expr_stmt|;
try|try
block|{
name|sInstance
operator|.
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
name|sInstance
operator|=
literal|null
expr_stmt|;
block|}
block|}
specifier|private
name|OnlineSectioningLogger
parameter_list|()
block|{
name|super
argument_list|(
literal|"OnlineSectioningLogger"
argument_list|)
expr_stmt|;
name|setDaemon
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|iEnabled
operator|=
literal|"true"
operator|.
name|equals
argument_list|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"unitime.sectioning.log"
argument_list|,
literal|"true"
argument_list|)
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
literal|"unitime.sectioning.log.limit"
argument_list|,
literal|"5000"
argument_list|)
argument_list|)
expr_stmt|;
try|try
block|{
if|if
condition|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"unitime.sectioning.log.file"
argument_list|)
operator|!=
literal|null
condition|)
name|iOut
operator|=
operator|new
name|PrintWriter
argument_list|(
operator|new
name|FileWriter
argument_list|(
operator|new
name|File
argument_list|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"unitime.sectioning.log.file"
argument_list|)
argument_list|)
argument_list|,
literal|true
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|sLog
operator|.
name|warn
argument_list|(
literal|"Unable to create sectioning log: "
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
specifier|public
name|boolean
name|isEnabled
parameter_list|()
block|{
return|return
name|iEnabled
return|;
block|}
specifier|public
name|void
name|setEnabled
parameter_list|(
name|boolean
name|enabled
parameter_list|)
block|{
name|iEnabled
operator|=
name|enabled
expr_stmt|;
block|}
specifier|public
name|boolean
name|isActive
parameter_list|()
block|{
return|return
name|iActive
return|;
block|}
specifier|public
name|void
name|record
parameter_list|(
name|OnlineSectioningLog
operator|.
name|Log
name|log
parameter_list|)
block|{
if|if
condition|(
name|log
operator|==
literal|null
operator|||
operator|!
name|isEnabled
argument_list|()
operator|||
operator|!
name|isActive
argument_list|()
condition|)
return|return;
for|for
control|(
name|OnlineSectioningLog
operator|.
name|Action
name|action
range|:
name|log
operator|.
name|getActionList
argument_list|()
control|)
block|{
if|if
condition|(
name|action
operator|.
name|hasStartTime
argument_list|()
operator|&&
name|action
operator|.
name|hasStudent
argument_list|()
operator|&&
name|action
operator|.
name|hasOperation
argument_list|()
operator|&&
name|action
operator|.
name|hasSession
argument_list|()
condition|)
block|{
synchronized|synchronized
init|(
name|iActions
init|)
block|{
if|if
condition|(
name|iLogLimit
operator|<=
literal|0
operator|||
name|iActions
operator|.
name|size
argument_list|()
operator|<
name|iLogLimit
condition|)
name|iActions
operator|.
name|add
argument_list|(
name|action
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|iOut
operator|!=
literal|null
condition|)
block|{
synchronized|synchronized
init|(
name|iOut
init|)
block|{
name|iOut
operator|.
name|print
argument_list|(
name|OnlineSectioningLog
operator|.
name|Log
operator|.
name|newBuilder
argument_list|()
operator|.
name|addAction
argument_list|(
name|action
argument_list|)
operator|.
name|build
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|iOut
operator|.
name|flush
argument_list|()
expr_stmt|;
block|}
block|}
block|}
block|}
block|}
specifier|public
name|void
name|run
parameter_list|()
block|{
name|sLog
operator|.
name|info
argument_list|(
literal|"Online Sectioning Logger is up."
argument_list|)
expr_stmt|;
try|try
block|{
name|iActive
operator|=
literal|true
expr_stmt|;
try|try
block|{
name|iOut
operator|=
operator|new
name|PrintWriter
argument_list|(
operator|new
name|FileWriter
argument_list|(
operator|new
name|File
argument_list|(
name|ApplicationProperties
operator|.
name|getDataFolder
argument_list|()
argument_list|,
literal|"sectioning.log"
argument_list|)
argument_list|,
literal|true
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|sLog
operator|.
name|warn
argument_list|(
literal|"Unable to create sectioning log: "
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
while|while
condition|(
literal|true
condition|)
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
name|OnlineSectioningLog
operator|.
name|Action
argument_list|>
name|actionsToSave
init|=
literal|null
decl_stmt|;
synchronized|synchronized
init|(
name|iActions
init|)
block|{
if|if
condition|(
operator|!
name|iActions
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|actionsToSave
operator|=
operator|new
name|ArrayList
argument_list|<
name|OnlineSectioningLog
operator|.
name|Action
argument_list|>
argument_list|(
name|iActions
argument_list|)
expr_stmt|;
name|iActions
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
block|}
try|try
block|{
if|if
condition|(
name|actionsToSave
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
name|actionsToSave
operator|.
name|size
argument_list|()
operator|+
literal|" actions..."
argument_list|)
expr_stmt|;
if|if
condition|(
name|iLogLimit
operator|>
literal|0
operator|&&
name|actionsToSave
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
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
name|OnlineSectioningLogDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|createNewSession
argument_list|()
decl_stmt|;
try|try
block|{
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|Session
argument_list|>
name|sessions
init|=
operator|new
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|Session
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|OnlineSectioningLog
operator|.
name|Action
name|q
range|:
name|actionsToSave
control|)
block|{
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|OnlineSectioningLog
name|log
init|=
operator|new
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|OnlineSectioningLog
argument_list|()
decl_stmt|;
name|log
operator|.
name|setAction
argument_list|(
name|q
operator|.
name|toByteArray
argument_list|()
argument_list|)
expr_stmt|;
name|log
operator|.
name|setOperation
argument_list|(
name|q
operator|.
name|getOperation
argument_list|()
argument_list|)
expr_stmt|;
name|String
name|studentExternalId
init|=
operator|(
name|q
operator|.
name|getStudent
argument_list|()
operator|.
name|hasExternalId
argument_list|()
condition|?
name|q
operator|.
name|getStudent
argument_list|()
operator|.
name|getExternalId
argument_list|()
else|:
literal|null
operator|)
decl_stmt|;
if|if
condition|(
name|studentExternalId
operator|==
literal|null
operator|||
name|studentExternalId
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|Student
name|student
init|=
name|StudentDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|q
operator|.
name|getStudent
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|hibSession
argument_list|)
decl_stmt|;
if|if
condition|(
name|student
operator|==
literal|null
condition|)
continue|continue;
name|studentExternalId
operator|=
name|student
operator|.
name|getExternalUniqueId
argument_list|()
expr_stmt|;
block|}
name|log
operator|.
name|setStudent
argument_list|(
name|studentExternalId
argument_list|)
expr_stmt|;
name|log
operator|.
name|setTimeStamp
argument_list|(
operator|new
name|Date
argument_list|(
name|q
operator|.
name|getStartTime
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|q
operator|.
name|hasResult
argument_list|()
condition|)
name|log
operator|.
name|setResult
argument_list|(
name|q
operator|.
name|getResult
argument_list|()
operator|.
name|getNumber
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|q
operator|.
name|hasUser
argument_list|()
operator|&&
name|q
operator|.
name|getUser
argument_list|()
operator|.
name|hasExternalId
argument_list|()
condition|)
name|log
operator|.
name|setUser
argument_list|(
name|q
operator|.
name|getUser
argument_list|()
operator|.
name|getExternalId
argument_list|()
argument_list|)
expr_stmt|;
name|Long
name|sessionId
init|=
name|q
operator|.
name|getSession
argument_list|()
operator|.
name|getUniqueId
argument_list|()
decl_stmt|;
name|Session
name|session
init|=
name|sessions
operator|.
name|get
argument_list|(
name|sessionId
argument_list|)
decl_stmt|;
if|if
condition|(
name|session
operator|==
literal|null
condition|)
block|{
name|session
operator|=
name|SessionDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|sessionId
argument_list|,
name|hibSession
argument_list|)
expr_stmt|;
name|sessions
operator|.
name|put
argument_list|(
name|sessionId
argument_list|,
name|session
argument_list|)
expr_stmt|;
block|}
name|log
operator|.
name|setSession
argument_list|(
name|session
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|save
argument_list|(
name|log
argument_list|)
expr_stmt|;
block|}
name|hibSession
operator|.
name|flush
argument_list|()
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
literal|"Failed to save "
operator|+
name|actionsToSave
operator|.
name|size
argument_list|()
operator|+
literal|" log actions: "
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
if|if
condition|(
operator|!
name|iActive
condition|)
break|break;
block|}
block|}
catch|catch
parameter_list|(
name|Throwable
name|t
parameter_list|)
block|{
name|sLog
operator|.
name|error
argument_list|(
literal|"Online Sectioning Logger failed: "
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
finally|finally
block|{
name|iActive
operator|=
literal|false
expr_stmt|;
if|if
condition|(
name|iOut
operator|!=
literal|null
condition|)
name|iOut
operator|.
name|flush
argument_list|()
expr_stmt|;
name|iOut
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
name|sLog
operator|.
name|info
argument_list|(
literal|"Online Sectioning Logger is down."
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

