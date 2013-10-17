begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 - 3.5 (University Timetabling Application)  * Copyright (C) 2011 - 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collections
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
name|List
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
name|apache
operator|.
name|log4j
operator|.
name|PropertyConfigurator
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
name|commons
operator|.
name|hibernate
operator|.
name|util
operator|.
name|HibernateUtil
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
name|StudentClassEnrollment
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
name|server
operator|.
name|InMemoryServer
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
name|updates
operator|.
name|PersistExpectedSpacesAction
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|OnlineSectioningTestFwk
block|{
specifier|protected
specifier|static
name|Logger
name|sLog
init|=
name|Logger
operator|.
name|getLogger
argument_list|(
name|OnlineSectioningTestFwk
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|protected
specifier|static
name|DecimalFormat
name|sDF
init|=
operator|new
name|DecimalFormat
argument_list|(
literal|"0.000"
argument_list|)
decl_stmt|;
specifier|private
name|OnlineSectioningServer
name|iServer
init|=
literal|null
decl_stmt|;
specifier|private
name|Pool
name|iTasks
decl_stmt|;
specifier|private
name|List
argument_list|<
name|Runner
argument_list|>
name|iRunners
decl_stmt|;
specifier|private
name|Counter
name|iFinished
init|=
operator|new
name|Counter
argument_list|()
decl_stmt|,
name|iExec
init|=
operator|new
name|Counter
argument_list|()
decl_stmt|,
name|iQuality
init|=
operator|new
name|Counter
argument_list|()
decl_stmt|;
specifier|private
name|double
name|iT0
init|=
literal|0
decl_stmt|;
specifier|private
name|double
name|iRunTime
init|=
literal|0.0
decl_stmt|;
specifier|protected
name|void
name|configureLogging
parameter_list|()
block|{
name|Properties
name|props
init|=
operator|new
name|Properties
argument_list|()
decl_stmt|;
name|props
operator|.
name|setProperty
argument_list|(
literal|"log4j.rootLogger"
argument_list|,
literal|"DEBUG, A1"
argument_list|)
expr_stmt|;
name|props
operator|.
name|setProperty
argument_list|(
literal|"log4j.appender.A1"
argument_list|,
literal|"org.apache.log4j.ConsoleAppender"
argument_list|)
expr_stmt|;
name|props
operator|.
name|setProperty
argument_list|(
literal|"log4j.appender.A1.layout"
argument_list|,
literal|"org.apache.log4j.PatternLayout"
argument_list|)
expr_stmt|;
name|props
operator|.
name|setProperty
argument_list|(
literal|"log4j.appender.A1.layout.ConversionPattern"
argument_list|,
literal|"%-5p %c{2}: %m%n"
argument_list|)
expr_stmt|;
name|props
operator|.
name|setProperty
argument_list|(
literal|"log4j.logger.org.hibernate"
argument_list|,
literal|"INFO"
argument_list|)
expr_stmt|;
name|props
operator|.
name|setProperty
argument_list|(
literal|"log4j.logger.org.hibernate.cfg"
argument_list|,
literal|"WARN"
argument_list|)
expr_stmt|;
name|props
operator|.
name|setProperty
argument_list|(
literal|"log4j.logger.org.hibernate.cache.EhCacheProvider"
argument_list|,
literal|"ERROR"
argument_list|)
expr_stmt|;
name|props
operator|.
name|setProperty
argument_list|(
literal|"log4j.logger.org.unitime.commons.hibernate"
argument_list|,
literal|"INFO"
argument_list|)
expr_stmt|;
name|props
operator|.
name|setProperty
argument_list|(
literal|"log4j.logger.net"
argument_list|,
literal|"INFO"
argument_list|)
expr_stmt|;
name|props
operator|.
name|setProperty
argument_list|(
literal|"log4j.logger.org.unitime.timetable.onlinesectioning"
argument_list|,
literal|"WARN"
argument_list|)
expr_stmt|;
name|props
operator|.
name|setProperty
argument_list|(
literal|"log4j.logger.org.unitime.timetable.onlinesectioning.test"
argument_list|,
literal|"INFO"
argument_list|)
expr_stmt|;
name|props
operator|.
name|setProperty
argument_list|(
literal|"log4j.logger."
operator|+
name|OnlineSectioningTestFwk
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|,
literal|"INFO"
argument_list|)
expr_stmt|;
name|props
operator|.
name|setProperty
argument_list|(
literal|"log4j.logger.net.sf.cpsolver.ifs.util.JProf"
argument_list|,
literal|"INFO"
argument_list|)
expr_stmt|;
name|PropertyConfigurator
operator|.
name|configure
argument_list|(
name|props
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|startServer
parameter_list|()
block|{
specifier|final
name|Session
name|session
init|=
name|Session
operator|.
name|getSessionUsingInitiativeYearTerm
argument_list|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"initiative"
argument_list|,
literal|"PWL"
argument_list|)
argument_list|,
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"year"
argument_list|,
literal|"2011"
argument_list|)
argument_list|,
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"term"
argument_list|,
literal|"Spring"
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|session
operator|==
literal|null
condition|)
block|{
name|sLog
operator|.
name|error
argument_list|(
literal|"Academic session not found, use properties initiative, year, and term to set academic session."
argument_list|)
expr_stmt|;
name|System
operator|.
name|exit
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|sLog
operator|.
name|info
argument_list|(
literal|"Session: "
operator|+
name|session
argument_list|)
expr_stmt|;
block|}
name|OnlineSectioningLogger
operator|.
name|getInstance
argument_list|()
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|iServer
operator|=
operator|new
name|InMemoryServer
argument_list|(
operator|new
name|OnlineSectioningServerContext
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|boolean
name|isWaitTillStarted
parameter_list|()
block|{
return|return
literal|true
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
name|Long
name|getAcademicSessionId
parameter_list|()
block|{
return|return
name|session
operator|.
name|getUniqueId
argument_list|()
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
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|stopServer
parameter_list|()
block|{
if|if
condition|(
name|iServer
operator|!=
literal|null
condition|)
block|{
name|List
argument_list|<
name|Long
argument_list|>
name|offeringIds
init|=
name|iServer
operator|.
name|getOfferingsToPersistExpectedSpaces
argument_list|(
literal|0
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|offeringIds
operator|.
name|isEmpty
argument_list|()
condition|)
name|iServer
operator|.
name|execute
argument_list|(
operator|new
name|PersistExpectedSpacesAction
argument_list|(
name|offeringIds
argument_list|)
argument_list|,
name|user
argument_list|()
argument_list|)
expr_stmt|;
name|iServer
operator|.
name|unload
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
name|iServer
operator|=
literal|null
expr_stmt|;
block|}
specifier|protected
name|void
name|close
parameter_list|()
block|{
name|OnlineSectioningLogger
operator|.
name|stopLogger
argument_list|()
expr_stmt|;
name|HibernateUtil
operator|.
name|closeHibernate
argument_list|()
expr_stmt|;
block|}
specifier|public
name|OnlineSectioningServer
name|getServer
parameter_list|()
block|{
return|return
name|iServer
return|;
block|}
specifier|public
interface|interface
name|Operation
block|{
specifier|public
name|double
name|execute
parameter_list|(
name|OnlineSectioningServer
name|s
parameter_list|)
function_decl|;
block|}
specifier|public
class|class
name|Runner
implements|implements
name|Runnable
block|{
specifier|public
name|void
name|run
parameter_list|()
block|{
name|Operation
name|op
init|=
literal|null
decl_stmt|;
while|while
condition|(
operator|(
name|op
operator|=
name|iTasks
operator|.
name|next
argument_list|()
operator|)
operator|!=
literal|null
condition|)
block|{
name|long
name|t0
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
try|try
block|{
name|double
name|val
init|=
name|op
operator|.
name|execute
argument_list|(
name|getServer
argument_list|()
argument_list|)
decl_stmt|;
name|iQuality
operator|.
name|inc
argument_list|(
name|val
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
literal|"Task failed: "
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
name|iFinished
operator|.
name|inc
argument_list|(
literal|1
argument_list|)
expr_stmt|;
block|}
name|long
name|t1
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
name|iRunTime
operator|=
operator|(
name|t1
operator|-
name|iT0
operator|)
operator|/
literal|1000.0
expr_stmt|;
name|iExec
operator|.
name|inc
argument_list|(
name|t1
operator|-
name|t0
argument_list|)
expr_stmt|;
block|}
block|}
block|}
specifier|public
specifier|static
class|class
name|Pool
block|{
specifier|private
name|Iterator
argument_list|<
name|Operation
argument_list|>
name|iIterator
decl_stmt|;
specifier|private
name|int
name|iCount
decl_stmt|;
specifier|public
name|Pool
parameter_list|(
name|List
argument_list|<
name|Operation
argument_list|>
name|operations
parameter_list|)
block|{
name|iIterator
operator|=
name|operations
operator|.
name|iterator
argument_list|()
expr_stmt|;
name|iCount
operator|=
literal|0
expr_stmt|;
block|}
specifier|public
specifier|synchronized
name|Operation
name|next
parameter_list|()
block|{
if|if
condition|(
name|iIterator
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|iCount
operator|++
expr_stmt|;
return|return
name|iIterator
operator|.
name|next
argument_list|()
return|;
block|}
return|return
literal|null
return|;
block|}
specifier|public
specifier|synchronized
name|int
name|count
parameter_list|()
block|{
return|return
name|iCount
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|Counter
block|{
specifier|private
name|double
name|iValue
init|=
literal|0
decl_stmt|;
specifier|private
name|int
name|iCount
init|=
literal|0
decl_stmt|;
specifier|public
specifier|synchronized
name|void
name|inc
parameter_list|(
name|double
name|val
parameter_list|)
block|{
name|iValue
operator|+=
name|val
expr_stmt|;
name|iCount
operator|++
expr_stmt|;
block|}
specifier|public
specifier|synchronized
name|int
name|count
parameter_list|()
block|{
return|return
name|iCount
return|;
block|}
specifier|public
specifier|synchronized
name|double
name|value
parameter_list|()
block|{
return|return
name|iValue
return|;
block|}
specifier|public
specifier|synchronized
name|void
name|clear
parameter_list|()
block|{
name|iValue
operator|=
literal|0.0
expr_stmt|;
name|iCount
operator|=
literal|0
expr_stmt|;
block|}
block|}
specifier|public
name|int
name|nrFinished
parameter_list|()
block|{
return|return
name|iFinished
operator|.
name|count
argument_list|()
return|;
block|}
specifier|public
name|double
name|testRunTimeInSeconds
parameter_list|()
block|{
return|return
name|iRunTime
return|;
block|}
specifier|public
name|int
name|nrConcurrent
parameter_list|()
block|{
return|return
name|iRunners
operator|.
name|size
argument_list|()
return|;
block|}
specifier|public
name|double
name|totalExecutionTimeInSeconds
parameter_list|()
block|{
return|return
name|iExec
operator|.
name|value
argument_list|()
operator|/
literal|1000.0
return|;
block|}
specifier|public
name|double
name|averageQuality
parameter_list|()
block|{
return|return
name|iQuality
operator|.
name|value
argument_list|()
operator|/
name|iQuality
operator|.
name|count
argument_list|()
return|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|nrFinished
argument_list|()
operator|+
literal|" tasks finished ("
operator|+
name|nrConcurrent
argument_list|()
operator|+
literal|" in parallel)."
operator|+
literal|" Running took "
operator|+
name|sDF
operator|.
name|format
argument_list|(
name|testRunTimeInSeconds
argument_list|()
argument_list|)
operator|+
literal|" s,"
operator|+
literal|" throughput "
operator|+
name|sDF
operator|.
name|format
argument_list|(
name|nrFinished
argument_list|()
operator|/
name|testRunTimeInSeconds
argument_list|()
argument_list|)
operator|+
literal|" tasks / s,"
operator|+
literal|" wait "
operator|+
name|sDF
operator|.
name|format
argument_list|(
name|totalExecutionTimeInSeconds
argument_list|()
operator|/
name|nrFinished
argument_list|()
argument_list|)
operator|+
literal|" s / task,"
operator|+
literal|" quality "
operator|+
name|sDF
operator|.
name|format
argument_list|(
literal|100.0
operator|*
name|averageQuality
argument_list|()
argument_list|)
operator|+
literal|"% on average"
return|;
block|}
specifier|public
specifier|synchronized
name|void
name|run
parameter_list|(
name|List
argument_list|<
name|Operation
argument_list|>
name|operations
parameter_list|,
name|int
name|nrConcurrent
parameter_list|)
block|{
name|sLog
operator|.
name|info
argument_list|(
literal|"Running "
operator|+
name|operations
operator|.
name|size
argument_list|()
operator|+
literal|" tasks..."
argument_list|)
expr_stmt|;
name|iRunners
operator|=
operator|new
name|ArrayList
argument_list|<
name|Runner
argument_list|>
argument_list|()
expr_stmt|;
name|iTasks
operator|=
operator|new
name|Pool
argument_list|(
name|operations
argument_list|)
expr_stmt|;
name|iFinished
operator|.
name|clear
argument_list|()
expr_stmt|;
name|iExec
operator|.
name|clear
argument_list|()
expr_stmt|;
name|iQuality
operator|.
name|clear
argument_list|()
expr_stmt|;
name|iT0
operator|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|nrConcurrent
condition|;
name|i
operator|++
control|)
block|{
name|Runner
name|r
init|=
operator|new
name|Runner
argument_list|()
decl_stmt|;
name|Thread
name|t
init|=
operator|new
name|Thread
argument_list|(
name|r
argument_list|)
decl_stmt|;
name|t
operator|.
name|setDaemon
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|t
operator|.
name|setName
argument_list|(
literal|"Runner #"
operator|+
operator|(
literal|1
operator|+
name|i
operator|)
argument_list|)
expr_stmt|;
name|t
operator|.
name|start
argument_list|()
expr_stmt|;
name|iRunners
operator|.
name|add
argument_list|(
name|r
argument_list|)
expr_stmt|;
block|}
do|do
block|{
try|try
block|{
name|Thread
operator|.
name|sleep
argument_list|(
literal|10000
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|InterruptedException
name|e
parameter_list|)
block|{
break|break;
block|}
name|sLog
operator|.
name|info
argument_list|(
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
do|while
condition|(
name|nrFinished
argument_list|()
operator|<
name|operations
operator|.
name|size
argument_list|()
condition|)
do|;
name|sLog
operator|.
name|info
argument_list|(
literal|"All "
operator|+
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|abstract
name|List
argument_list|<
name|Operation
argument_list|>
name|operations
parameter_list|()
function_decl|;
specifier|public
name|OnlineSectioningLog
operator|.
name|Entity
name|user
parameter_list|()
block|{
return|return
name|OnlineSectioningLog
operator|.
name|Entity
operator|.
name|newBuilder
argument_list|()
operator|.
name|setExternalId
argument_list|(
name|StudentClassEnrollment
operator|.
name|SystemChange
operator|.
name|TEST
operator|.
name|name
argument_list|()
argument_list|)
operator|.
name|setName
argument_list|(
name|StudentClassEnrollment
operator|.
name|SystemChange
operator|.
name|TEST
operator|.
name|getName
argument_list|()
argument_list|)
operator|.
name|setType
argument_list|(
name|OnlineSectioningLog
operator|.
name|Entity
operator|.
name|EntityType
operator|.
name|OTHER
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
specifier|public
name|void
name|test
parameter_list|(
name|int
name|nrTasks
parameter_list|,
name|int
modifier|...
name|nrConcurrent
parameter_list|)
block|{
try|try
block|{
name|configureLogging
argument_list|()
expr_stmt|;
name|HibernateUtil
operator|.
name|configureHibernate
argument_list|(
name|ApplicationProperties
operator|.
name|getProperties
argument_list|()
argument_list|)
expr_stmt|;
name|startServer
argument_list|()
expr_stmt|;
name|List
argument_list|<
name|Operation
argument_list|>
name|operations
init|=
name|operations
argument_list|()
decl_stmt|;
name|Collections
operator|.
name|shuffle
argument_list|(
name|operations
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|c
range|:
name|nrConcurrent
control|)
block|{
name|run
argument_list|(
name|nrTasks
operator|<=
literal|0
condition|?
name|operations
else|:
name|operations
operator|.
name|subList
argument_list|(
literal|0
argument_list|,
name|nrTasks
argument_list|)
argument_list|,
name|c
argument_list|)
expr_stmt|;
block|}
name|stopServer
argument_list|()
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
name|fatal
argument_list|(
literal|"Test failed: "
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
name|close
argument_list|()
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

