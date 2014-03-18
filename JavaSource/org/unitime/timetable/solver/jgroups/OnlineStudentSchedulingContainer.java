begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.5 (University Timetabling Application)  * Copyright (C) 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|solver
operator|.
name|jgroups
package|;
end_package

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
name|Iterator
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
name|Set
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
name|concurrent
operator|.
name|locks
operator|.
name|ReentrantReadWriteLock
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
name|DataProperties
import|;
end_import

begin_import
import|import
name|org
operator|.
name|infinispan
operator|.
name|manager
operator|.
name|EmbeddedCacheManager
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jgroups
operator|.
name|blocks
operator|.
name|locking
operator|.
name|LockService
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
name|gwt
operator|.
name|shared
operator|.
name|SectioningException
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
name|StudentSectioningQueue
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
name|onlinesectioning
operator|.
name|OnlineSectioningLogger
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
name|onlinesectioning
operator|.
name|OnlineSectioningServer
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
name|onlinesectioning
operator|.
name|OnlineSectioningServerContext
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
name|onlinesectioning
operator|.
name|server
operator|.
name|ReplicatedServerWithMaster
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|OnlineStudentSchedulingContainer
implements|implements
name|SolverContainer
argument_list|<
name|OnlineSectioningServer
argument_list|>
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
name|OnlineStudentSchedulingContainer
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|protected
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|OnlineSectioningServer
argument_list|>
name|iInstances
init|=
operator|new
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|OnlineSectioningServer
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|OnlineStudentSchedulingUpdater
argument_list|>
name|iUpdaters
init|=
operator|new
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|OnlineStudentSchedulingUpdater
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|ReentrantReadWriteLock
name|iGlobalLock
init|=
operator|new
name|ReentrantReadWriteLock
argument_list|()
decl_stmt|;
annotation|@
name|Override
specifier|public
name|Set
argument_list|<
name|String
argument_list|>
name|getSolvers
parameter_list|()
block|{
name|iGlobalLock
operator|.
name|readLock
argument_list|()
operator|.
name|lock
argument_list|()
expr_stmt|;
try|try
block|{
name|TreeSet
argument_list|<
name|String
argument_list|>
name|ret
init|=
operator|new
name|TreeSet
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|Long
argument_list|,
name|OnlineSectioningServer
argument_list|>
name|entry
range|:
name|iInstances
operator|.
name|entrySet
argument_list|()
control|)
block|{
try|try
block|{
name|ret
operator|.
name|add
argument_list|(
name|entry
operator|.
name|getValue
argument_list|()
operator|.
name|getAcademicSession
argument_list|()
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
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
name|sLog
operator|.
name|error
argument_list|(
literal|"Server "
operator|+
name|entry
operator|.
name|getKey
argument_list|()
operator|+
literal|" appears to be in an inconsistent state: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|ret
return|;
block|}
finally|finally
block|{
name|iGlobalLock
operator|.
name|readLock
argument_list|()
operator|.
name|unlock
argument_list|()
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|OnlineSectioningServer
name|getSolver
parameter_list|(
name|String
name|sessionId
parameter_list|)
block|{
return|return
name|getInstance
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|sessionId
argument_list|)
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|long
name|getMemUsage
parameter_list|(
name|String
name|user
parameter_list|)
block|{
name|OnlineSectioningServer
name|solver
init|=
name|getSolver
argument_list|(
name|user
argument_list|)
decl_stmt|;
return|return
name|solver
operator|==
literal|null
condition|?
literal|0
else|:
name|solver
operator|.
name|getMemUsage
argument_list|()
return|;
block|}
specifier|public
name|OnlineSectioningServer
name|getInstance
parameter_list|(
name|Long
name|sessionId
parameter_list|)
block|{
name|iGlobalLock
operator|.
name|readLock
argument_list|()
operator|.
name|lock
argument_list|()
expr_stmt|;
try|try
block|{
name|OnlineSectioningServer
name|instance
init|=
name|iInstances
operator|.
name|get
argument_list|(
name|sessionId
argument_list|)
decl_stmt|;
try|try
block|{
name|instance
operator|.
name|getAcademicSession
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IllegalStateException
name|e
parameter_list|)
block|{
name|sLog
operator|.
name|error
argument_list|(
literal|"Server "
operator|+
name|sessionId
operator|+
literal|" appears to be in an inconsistent state: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
return|return
name|instance
return|;
block|}
finally|finally
block|{
name|iGlobalLock
operator|.
name|readLock
argument_list|()
operator|.
name|unlock
argument_list|()
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|hasSolver
parameter_list|(
name|String
name|sessionId
parameter_list|)
block|{
name|iGlobalLock
operator|.
name|readLock
argument_list|()
operator|.
name|lock
argument_list|()
expr_stmt|;
try|try
block|{
return|return
name|iInstances
operator|.
name|containsKey
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|sessionId
argument_list|)
argument_list|)
return|;
block|}
finally|finally
block|{
name|iGlobalLock
operator|.
name|readLock
argument_list|()
operator|.
name|unlock
argument_list|()
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|OnlineSectioningServer
name|createSolver
parameter_list|(
name|String
name|sessionId
parameter_list|,
name|DataProperties
name|config
parameter_list|)
block|{
return|return
name|createInstance
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|sessionId
argument_list|)
argument_list|,
name|config
argument_list|)
return|;
block|}
specifier|public
name|OnlineSectioningServer
name|createInstance
parameter_list|(
specifier|final
name|Long
name|academicSessionId
parameter_list|,
name|DataProperties
name|config
parameter_list|)
block|{
name|unload
argument_list|(
name|academicSessionId
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|iGlobalLock
operator|.
name|writeLock
argument_list|()
operator|.
name|lock
argument_list|()
expr_stmt|;
try|try
block|{
name|ApplicationProperties
operator|.
name|setSessionId
argument_list|(
name|academicSessionId
argument_list|)
expr_stmt|;
name|Class
name|serverClass
init|=
name|Class
operator|.
name|forName
argument_list|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"unitime.enrollment.server.class"
argument_list|,
name|ReplicatedServerWithMaster
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
name|OnlineSectioningServer
name|server
init|=
operator|(
name|OnlineSectioningServer
operator|)
name|serverClass
operator|.
name|getConstructor
argument_list|(
name|OnlineSectioningServerContext
operator|.
name|class
argument_list|)
operator|.
name|newInstance
argument_list|(
name|getServerContext
argument_list|(
name|academicSessionId
argument_list|)
argument_list|)
decl_stmt|;
name|iInstances
operator|.
name|put
argument_list|(
name|academicSessionId
argument_list|,
name|server
argument_list|)
expr_stmt|;
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
name|SessionDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|createNewSession
argument_list|()
decl_stmt|;
try|try
block|{
name|OnlineStudentSchedulingUpdater
name|updater
init|=
operator|new
name|OnlineStudentSchedulingUpdater
argument_list|(
name|this
argument_list|,
name|server
operator|.
name|getAcademicSession
argument_list|()
argument_list|,
name|StudentSectioningQueue
operator|.
name|getLastTimeStamp
argument_list|(
name|hibSession
argument_list|,
name|academicSessionId
argument_list|)
argument_list|)
decl_stmt|;
name|iUpdaters
operator|.
name|put
argument_list|(
name|academicSessionId
argument_list|,
name|updater
argument_list|)
expr_stmt|;
name|updater
operator|.
name|start
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
return|return
name|server
return|;
block|}
catch|catch
parameter_list|(
name|SectioningException
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
throw|throw
operator|new
name|SectioningException
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
finally|finally
block|{
name|iGlobalLock
operator|.
name|writeLock
argument_list|()
operator|.
name|unlock
argument_list|()
expr_stmt|;
name|ApplicationProperties
operator|.
name|setSessionId
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|OnlineSectioningServerContext
name|getServerContext
parameter_list|(
specifier|final
name|Long
name|academicSessionId
parameter_list|)
block|{
return|return
operator|new
name|OnlineSectioningServerContext
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|Long
name|getAcademicSessionId
parameter_list|()
block|{
return|return
name|academicSessionId
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isWaitTillStarted
parameter_list|()
block|{
return|return
literal|false
return|;
block|}
annotation|@
name|Override
specifier|public
name|EmbeddedCacheManager
name|getCacheManager
parameter_list|()
block|{
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|LockService
name|getLockService
parameter_list|()
block|{
return|return
literal|null
return|;
block|}
block|}
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|unloadSolver
parameter_list|(
name|String
name|sessionId
parameter_list|)
block|{
name|unload
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|sessionId
argument_list|)
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|unload
parameter_list|(
name|Long
name|academicSessionId
parameter_list|,
name|boolean
name|interrupt
parameter_list|)
block|{
name|iGlobalLock
operator|.
name|writeLock
argument_list|()
operator|.
name|lock
argument_list|()
expr_stmt|;
try|try
block|{
name|OnlineStudentSchedulingUpdater
name|u
init|=
name|iUpdaters
operator|.
name|get
argument_list|(
name|academicSessionId
argument_list|)
decl_stmt|;
if|if
condition|(
name|u
operator|!=
literal|null
condition|)
name|u
operator|.
name|stopUpdating
argument_list|(
name|interrupt
argument_list|)
expr_stmt|;
name|OnlineSectioningServer
name|s
init|=
name|iInstances
operator|.
name|get
argument_list|(
name|academicSessionId
argument_list|)
decl_stmt|;
if|if
condition|(
name|s
operator|!=
literal|null
condition|)
block|{
name|sLog
operator|.
name|info
argument_list|(
literal|"Unloading "
operator|+
name|u
operator|.
name|getAcademicSession
argument_list|()
operator|+
literal|"..."
argument_list|)
expr_stmt|;
name|s
operator|.
name|unload
argument_list|()
expr_stmt|;
block|}
name|iInstances
operator|.
name|remove
argument_list|(
name|academicSessionId
argument_list|)
expr_stmt|;
name|iUpdaters
operator|.
name|remove
argument_list|(
name|academicSessionId
argument_list|)
expr_stmt|;
block|}
finally|finally
block|{
name|iGlobalLock
operator|.
name|writeLock
argument_list|()
operator|.
name|unlock
argument_list|()
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|int
name|getUsage
parameter_list|()
block|{
name|iGlobalLock
operator|.
name|readLock
argument_list|()
operator|.
name|lock
argument_list|()
expr_stmt|;
name|int
name|ret
init|=
literal|0
decl_stmt|;
try|try
block|{
for|for
control|(
name|OnlineSectioningServer
name|s
range|:
name|iInstances
operator|.
name|values
argument_list|()
control|)
if|if
condition|(
name|s
operator|.
name|isMaster
argument_list|()
condition|)
name|ret
operator|+=
literal|200
expr_stmt|;
else|else
name|ret
operator|+=
literal|100
expr_stmt|;
block|}
finally|finally
block|{
name|iGlobalLock
operator|.
name|readLock
argument_list|()
operator|.
name|unlock
argument_list|()
expr_stmt|;
block|}
return|return
name|ret
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|start
parameter_list|()
block|{
name|sLog
operator|.
name|info
argument_list|(
literal|"Student Sectioning Service is starting up ..."
argument_list|)
expr_stmt|;
name|OnlineSectioningLogger
operator|.
name|startLogger
argument_list|()
expr_stmt|;
block|}
specifier|public
name|boolean
name|isEnabled
parameter_list|()
block|{
comment|// if autostart is enabled, just check whether there are some instances already loaded in
if|if
condition|(
literal|"true"
operator|.
name|equals
argument_list|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"unitime.enrollment.autostart"
argument_list|,
literal|"false"
argument_list|)
argument_list|)
condition|)
return|return
operator|!
name|iInstances
operator|.
name|isEmpty
argument_list|()
return|;
comment|// quick check for existing instances
if|if
condition|(
operator|!
name|iInstances
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
literal|true
return|;
comment|// otherwise, look for a session that has sectioning enabled
name|String
name|year
init|=
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"unitime.enrollment.year"
argument_list|)
decl_stmt|;
name|String
name|term
init|=
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"unitime.enrollment.term"
argument_list|)
decl_stmt|;
name|String
name|campus
init|=
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"unitime.enrollment.campus"
argument_list|)
decl_stmt|;
for|for
control|(
name|Iterator
argument_list|<
name|Session
argument_list|>
name|i
init|=
name|SessionDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|findAll
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
specifier|final
name|Session
name|session
init|=
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|year
operator|!=
literal|null
operator|&&
operator|!
name|session
operator|.
name|getAcademicYear
argument_list|()
operator|.
name|matches
argument_list|(
name|year
argument_list|)
condition|)
continue|continue;
if|if
condition|(
name|term
operator|!=
literal|null
operator|&&
operator|!
name|session
operator|.
name|getAcademicTerm
argument_list|()
operator|.
name|matches
argument_list|(
name|term
argument_list|)
condition|)
continue|continue;
if|if
condition|(
name|campus
operator|!=
literal|null
operator|&&
operator|!
name|session
operator|.
name|getAcademicInitiative
argument_list|()
operator|.
name|matches
argument_list|(
name|campus
argument_list|)
condition|)
continue|continue;
if|if
condition|(
name|session
operator|.
name|getStatusType
argument_list|()
operator|.
name|isTestSession
argument_list|()
condition|)
continue|continue;
if|if
condition|(
operator|!
name|session
operator|.
name|getStatusType
argument_list|()
operator|.
name|canSectionAssistStudents
argument_list|()
operator|&&
operator|!
name|session
operator|.
name|getStatusType
argument_list|()
operator|.
name|canOnlineSectionStudents
argument_list|()
condition|)
continue|continue;
return|return
literal|true
return|;
block|}
return|return
literal|false
return|;
block|}
specifier|public
name|boolean
name|isRegistrationEnabled
parameter_list|()
block|{
for|for
control|(
name|Session
name|session
range|:
name|SessionDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|findAll
argument_list|()
control|)
block|{
if|if
condition|(
name|session
operator|.
name|getStatusType
argument_list|()
operator|.
name|isTestSession
argument_list|()
condition|)
continue|continue;
if|if
condition|(
operator|!
name|session
operator|.
name|getStatusType
argument_list|()
operator|.
name|canOnlineSectionStudents
argument_list|()
operator|&&
operator|!
name|session
operator|.
name|getStatusType
argument_list|()
operator|.
name|canSectionAssistStudents
argument_list|()
operator|&&
name|session
operator|.
name|getStatusType
argument_list|()
operator|.
name|canPreRegisterStudents
argument_list|()
condition|)
return|return
literal|true
return|;
block|}
return|return
literal|false
return|;
block|}
specifier|public
name|void
name|unloadAll
parameter_list|()
block|{
name|iGlobalLock
operator|.
name|writeLock
argument_list|()
operator|.
name|lock
argument_list|()
expr_stmt|;
try|try
block|{
for|for
control|(
name|OnlineStudentSchedulingUpdater
name|u
range|:
name|iUpdaters
operator|.
name|values
argument_list|()
control|)
block|{
name|u
operator|.
name|stopUpdating
argument_list|(
literal|true
argument_list|)
expr_stmt|;
if|if
condition|(
name|u
operator|.
name|getAcademicSession
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|OnlineSectioningServer
name|s
init|=
name|iInstances
operator|.
name|get
argument_list|(
name|u
operator|.
name|getAcademicSession
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|s
operator|!=
literal|null
condition|)
name|s
operator|.
name|unload
argument_list|()
expr_stmt|;
block|}
block|}
name|iInstances
operator|.
name|clear
argument_list|()
expr_stmt|;
name|iUpdaters
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
finally|finally
block|{
name|iGlobalLock
operator|.
name|writeLock
argument_list|()
operator|.
name|unlock
argument_list|()
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|stop
parameter_list|()
block|{
name|sLog
operator|.
name|info
argument_list|(
literal|"Student Sectioning Service is going down ..."
argument_list|)
expr_stmt|;
name|unloadAll
argument_list|()
expr_stmt|;
name|OnlineSectioningLogger
operator|.
name|stopLogger
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

