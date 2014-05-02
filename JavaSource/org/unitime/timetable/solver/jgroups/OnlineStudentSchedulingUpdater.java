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
name|Calendar
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
name|model
operator|.
name|dao
operator|.
name|StudentSectioningQueueDAO
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
name|onlinesectioning
operator|.
name|AcademicSessionInfo
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
name|OnlineSectioningLog
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
name|OnlineSectioningServer
operator|.
name|ServerCallback
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
name|CheckAllOfferingsAction
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
name|ClassAssignmentChanged
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
name|ExpireReservationsAction
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
name|ReloadAllData
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
name|ReloadAllStudents
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
name|ReloadOfferingAction
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
name|ReloadStudent
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|OnlineStudentSchedulingUpdater
extends|extends
name|Thread
block|{
specifier|private
name|Logger
name|iLog
decl_stmt|;
specifier|private
name|long
name|iSleepTimeInSeconds
init|=
literal|5
decl_stmt|;
specifier|private
name|boolean
name|iRun
init|=
literal|true
decl_stmt|;
specifier|private
name|OnlineStudentSchedulingContainer
name|iContainer
init|=
literal|null
decl_stmt|;
specifier|private
name|AcademicSessionInfo
name|iSession
init|=
literal|null
decl_stmt|;
specifier|private
name|Date
name|iLastTimeStamp
init|=
literal|null
decl_stmt|;
specifier|public
name|OnlineStudentSchedulingUpdater
parameter_list|(
name|OnlineStudentSchedulingContainer
name|container
parameter_list|,
name|AcademicSessionInfo
name|session
parameter_list|,
name|Date
name|lastTimeStamp
parameter_list|)
block|{
name|super
argument_list|()
expr_stmt|;
name|iContainer
operator|=
name|container
expr_stmt|;
name|iSession
operator|=
name|session
expr_stmt|;
name|iLastTimeStamp
operator|=
name|lastTimeStamp
expr_stmt|;
name|setDaemon
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|setName
argument_list|(
literal|"Updater["
operator|+
name|getAcademicSession
argument_list|()
operator|.
name|toCompactString
argument_list|()
operator|+
literal|"]"
argument_list|)
expr_stmt|;
name|iSleepTimeInSeconds
operator|=
name|ApplicationProperty
operator|.
name|OnlineSchedulingQueueUpdateInterval
operator|.
name|intValue
argument_list|()
expr_stmt|;
name|iLog
operator|=
name|Logger
operator|.
name|getLogger
argument_list|(
name|OnlineStudentSchedulingUpdater
operator|.
name|class
operator|+
literal|".updater["
operator|+
name|getAcademicSession
argument_list|()
operator|.
name|toCompactString
argument_list|()
operator|+
literal|"]"
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|run
parameter_list|()
block|{
try|try
block|{
name|iLog
operator|.
name|info
argument_list|(
name|getAcademicSession
argument_list|()
operator|+
literal|" updater started."
argument_list|)
expr_stmt|;
if|if
condition|(
name|getAcademicSession
argument_list|()
operator|!=
literal|null
condition|)
name|ApplicationProperties
operator|.
name|setSessionId
argument_list|(
name|getAcademicSession
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
while|while
condition|(
name|iRun
condition|)
block|{
try|try
block|{
name|OnlineSectioningServer
name|server
init|=
name|iContainer
operator|.
name|getInstance
argument_list|(
name|getAcademicSession
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|server
operator|!=
literal|null
operator|&&
name|server
operator|.
name|isMaster
argument_list|()
condition|)
block|{
name|checkForUpdates
argument_list|(
name|server
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|iRun
condition|)
break|break;
name|checkForExpiredReservations
argument_list|(
name|server
argument_list|)
expr_stmt|;
name|persistExpectedSpaces
argument_list|(
name|server
argument_list|)
expr_stmt|;
block|}
block|}
finally|finally
block|{
name|_RootDAO
operator|.
name|closeCurrentThreadSessions
argument_list|()
expr_stmt|;
block|}
try|try
block|{
name|sleep
argument_list|(
name|iSleepTimeInSeconds
operator|*
literal|1000
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
block|}
name|iLog
operator|.
name|info
argument_list|(
operator|(
name|getAcademicSession
argument_list|()
operator|==
literal|null
condition|?
literal|"Generic"
else|:
name|getAcademicSession
argument_list|()
operator|.
name|toString
argument_list|()
operator|)
operator|+
literal|" updater stopped."
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|iLog
operator|.
name|error
argument_list|(
operator|(
name|getAcademicSession
argument_list|()
operator|==
literal|null
condition|?
literal|"Generic"
else|:
name|getAcademicSession
argument_list|()
operator|.
name|toString
argument_list|()
operator|)
operator|+
literal|" updater failed, "
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
name|AcademicSessionInfo
name|getAcademicSession
parameter_list|()
block|{
return|return
name|iSession
return|;
block|}
specifier|public
name|OnlineSectioningServer
name|getServer
parameter_list|()
block|{
if|if
condition|(
name|getAcademicSession
argument_list|()
operator|==
literal|null
condition|)
return|return
literal|null
return|;
return|return
name|iContainer
operator|.
name|getInstance
argument_list|(
name|getAcademicSession
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|void
name|checkForUpdates
parameter_list|(
name|OnlineSectioningServer
name|server
parameter_list|)
block|{
try|try
block|{
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
name|StudentSectioningQueueDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|createNewSession
argument_list|()
decl_stmt|;
try|try
block|{
name|iLastTimeStamp
operator|=
name|server
operator|.
name|getProperty
argument_list|(
literal|"Updater.LastTimeStamp"
argument_list|,
name|iLastTimeStamp
argument_list|)
expr_stmt|;
for|for
control|(
name|StudentSectioningQueue
name|q
range|:
name|StudentSectioningQueue
operator|.
name|getItems
argument_list|(
name|hibSession
argument_list|,
name|getAcademicSession
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|iLastTimeStamp
argument_list|)
control|)
block|{
try|try
block|{
name|processChange
argument_list|(
name|server
argument_list|,
name|q
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|iLog
operator|.
name|error
argument_list|(
literal|"Update failed: "
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
if|if
condition|(
operator|!
name|iRun
condition|)
break|break;
name|iLastTimeStamp
operator|=
name|q
operator|.
name|getTimeStamp
argument_list|()
expr_stmt|;
name|server
operator|.
name|setProperty
argument_list|(
literal|"Updater.LastTimeStamp"
argument_list|,
name|iLastTimeStamp
argument_list|)
expr_stmt|;
block|}
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
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|iLog
operator|.
name|error
argument_list|(
literal|"Unable to check for updates: "
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
name|void
name|checkForExpiredReservations
parameter_list|(
name|OnlineSectioningServer
name|server
parameter_list|)
block|{
name|long
name|ts
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
comment|// current time stamp
comment|// the check was done within the last hour -> no need to repeat
name|Long
name|lastReservationCheck
init|=
name|server
operator|.
name|getProperty
argument_list|(
literal|"Updater.LastReservationCheck"
argument_list|,
literal|null
argument_list|)
decl_stmt|;
if|if
condition|(
name|lastReservationCheck
operator|!=
literal|null
operator|&&
name|ts
operator|-
name|lastReservationCheck
operator|<
literal|3600000
condition|)
return|return;
if|if
condition|(
name|Calendar
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|Calendar
operator|.
name|HOUR_OF_DAY
argument_list|)
operator|==
literal|0
condition|)
block|{
comment|// first time after midnight (TODO: allow change)
name|server
operator|.
name|setProperty
argument_list|(
literal|"Updater.LastReservationCheck"
argument_list|,
name|ts
argument_list|)
expr_stmt|;
try|try
block|{
name|server
operator|.
name|execute
argument_list|(
name|server
operator|.
name|createAction
argument_list|(
name|ExpireReservationsAction
operator|.
name|class
argument_list|)
argument_list|,
name|user
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|iLog
operator|.
name|error
argument_list|(
literal|"Expire reservations failed: "
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
block|}
specifier|protected
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
name|SYSTEM
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
name|SYSTEM
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
name|persistExpectedSpaces
parameter_list|(
name|OnlineSectioningServer
name|server
parameter_list|)
block|{
try|try
block|{
name|List
argument_list|<
name|Long
argument_list|>
name|offeringIds
init|=
name|server
operator|.
name|getOfferingsToPersistExpectedSpaces
argument_list|(
literal|2000
operator|*
name|iSleepTimeInSeconds
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
block|{
name|server
operator|.
name|execute
argument_list|(
name|server
operator|.
name|createAction
argument_list|(
name|PersistExpectedSpacesAction
operator|.
name|class
argument_list|)
operator|.
name|forOfferings
argument_list|(
name|offeringIds
argument_list|)
argument_list|,
name|user
argument_list|()
argument_list|,
operator|new
name|ServerCallback
argument_list|<
name|Boolean
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onSuccess
parameter_list|(
name|Boolean
name|result
parameter_list|)
block|{
block|}
annotation|@
name|Override
specifier|public
name|void
name|onFailure
parameter_list|(
name|Throwable
name|exception
parameter_list|)
block|{
name|iLog
operator|.
name|error
argument_list|(
literal|"Failed to persist expected spaces: "
operator|+
name|exception
operator|.
name|getMessage
argument_list|()
argument_list|,
name|exception
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|iLog
operator|.
name|error
argument_list|(
literal|"Failed to persist expected spaces: "
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
specifier|protected
name|void
name|processChange
parameter_list|(
name|OnlineSectioningServer
name|server
parameter_list|,
name|StudentSectioningQueue
name|q
parameter_list|)
block|{
switch|switch
condition|(
name|StudentSectioningQueue
operator|.
name|Type
operator|.
name|values
argument_list|()
index|[
name|q
operator|.
name|getType
argument_list|()
index|]
condition|)
block|{
case|case
name|SESSION_RELOAD
case|:
name|iLog
operator|.
name|info
argument_list|(
literal|"Reloading "
operator|+
name|server
operator|.
name|getAcademicSession
argument_list|()
argument_list|)
expr_stmt|;
name|server
operator|.
name|execute
argument_list|(
name|server
operator|.
name|createAction
argument_list|(
name|ReloadAllData
operator|.
name|class
argument_list|)
argument_list|,
name|q
operator|.
name|getUser
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|server
operator|.
name|getAcademicSession
argument_list|()
operator|.
name|isSectioningEnabled
argument_list|()
condition|)
name|server
operator|.
name|execute
argument_list|(
name|server
operator|.
name|createAction
argument_list|(
name|CheckAllOfferingsAction
operator|.
name|class
argument_list|)
argument_list|,
name|q
operator|.
name|getUser
argument_list|()
argument_list|)
expr_stmt|;
break|break;
case|case
name|SESSION_STATUS_CHANGE
case|:
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
name|iSession
operator|.
name|getUniqueId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|session
operator|==
literal|null
operator|||
operator|(
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
operator|)
condition|)
block|{
if|if
condition|(
name|iContainer
operator|instanceof
name|OnlineStudentSchedulingContainerRemote
condition|)
block|{
try|try
block|{
operator|(
operator|(
name|OnlineStudentSchedulingContainerRemote
operator|)
name|iContainer
operator|)
operator|.
name|getDispatcher
argument_list|()
operator|.
name|callRemoteMethods
argument_list|(
literal|null
argument_list|,
literal|"unload"
argument_list|,
operator|new
name|Object
index|[]
block|{
name|session
operator|.
name|getUniqueId
argument_list|()
block|,
literal|false
block|}
argument_list|,
operator|new
name|Class
index|[]
block|{
name|Long
operator|.
name|class
block|,
name|boolean
operator|.
name|class
block|}
argument_list|,
name|SolverServerImplementation
operator|.
name|sAllResponses
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|iLog
operator|.
name|error
argument_list|(
literal|"Failed to unload server: "
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
else|else
block|{
name|iContainer
operator|.
name|unload
argument_list|(
name|getAcademicSession
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|iLog
operator|.
name|info
argument_list|(
literal|"Session status changed for "
operator|+
name|session
operator|.
name|getLabel
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|server
operator|.
name|getAcademicSession
argument_list|()
operator|.
name|isSectioningEnabled
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
name|server
operator|.
name|releaseAllOfferingLocks
argument_list|()
expr_stmt|;
name|getAcademicSession
argument_list|()
operator|.
name|update
argument_list|(
name|session
argument_list|)
expr_stmt|;
name|server
operator|.
name|setProperty
argument_list|(
literal|"AcademicSession"
argument_list|,
name|getAcademicSession
argument_list|()
argument_list|)
expr_stmt|;
block|}
break|break;
case|case
name|STUDENT_ENROLLMENT_CHANGE
case|:
name|List
argument_list|<
name|Long
argument_list|>
name|studentIds
init|=
name|q
operator|.
name|getIds
argument_list|()
decl_stmt|;
if|if
condition|(
name|studentIds
operator|==
literal|null
operator|||
name|studentIds
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|iLog
operator|.
name|info
argument_list|(
literal|"All students changed for "
operator|+
name|server
operator|.
name|getAcademicSession
argument_list|()
argument_list|)
expr_stmt|;
name|server
operator|.
name|execute
argument_list|(
name|server
operator|.
name|createAction
argument_list|(
name|ReloadAllStudents
operator|.
name|class
argument_list|)
argument_list|,
name|q
operator|.
name|getUser
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|server
operator|.
name|execute
argument_list|(
name|server
operator|.
name|createAction
argument_list|(
name|ReloadStudent
operator|.
name|class
argument_list|)
operator|.
name|forStudents
argument_list|(
name|studentIds
argument_list|)
argument_list|,
name|q
operator|.
name|getUser
argument_list|()
argument_list|)
expr_stmt|;
block|}
break|break;
case|case
name|CLASS_ASSIGNMENT_CHANGE
case|:
name|server
operator|.
name|execute
argument_list|(
name|server
operator|.
name|createAction
argument_list|(
name|ClassAssignmentChanged
operator|.
name|class
argument_list|)
operator|.
name|forClasses
argument_list|(
name|q
operator|.
name|getIds
argument_list|()
argument_list|)
argument_list|,
name|q
operator|.
name|getUser
argument_list|()
argument_list|)
expr_stmt|;
break|break;
case|case
name|OFFERING_CHANGE
case|:
name|server
operator|.
name|execute
argument_list|(
name|server
operator|.
name|createAction
argument_list|(
name|ReloadOfferingAction
operator|.
name|class
argument_list|)
operator|.
name|forOfferings
argument_list|(
name|q
operator|.
name|getIds
argument_list|()
argument_list|)
argument_list|,
name|q
operator|.
name|getUser
argument_list|()
argument_list|)
expr_stmt|;
break|break;
default|default:
name|iLog
operator|.
name|error
argument_list|(
literal|"Student sectioning queue type "
operator|+
name|StudentSectioningQueue
operator|.
name|Type
operator|.
name|values
argument_list|()
index|[
name|q
operator|.
name|getType
argument_list|()
index|]
operator|+
literal|" not known."
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|void
name|stopUpdating
parameter_list|(
name|boolean
name|interrupt
parameter_list|)
block|{
name|iRun
operator|=
literal|false
expr_stmt|;
if|if
condition|(
name|interrupt
condition|)
block|{
name|interrupt
argument_list|()
expr_stmt|;
try|try
block|{
name|this
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
block|}
block|}
block|}
end_class

end_unit

