begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.4 - 3.5 (University Timetabling Application)  * Copyright (C) 2012 - 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|events
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
name|Set
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
name|gwt
operator|.
name|resources
operator|.
name|GwtMessages
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
name|Event
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
name|EventNote
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
name|Meeting
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
name|EventDAO
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|EventExpirationService
extends|extends
name|Thread
block|{
specifier|protected
specifier|static
name|GwtMessages
name|MESSAGES
init|=
name|Localization
operator|.
name|create
argument_list|(
name|GwtMessages
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|static
name|Logger
name|sLog
init|=
name|Logger
operator|.
name|getLogger
argument_list|(
name|EventExpirationService
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|long
name|iSleepTimeInMinutes
init|=
literal|5
decl_stmt|;
specifier|private
name|boolean
name|iActive
init|=
literal|true
decl_stmt|;
specifier|private
specifier|static
name|EventExpirationService
name|sInstance
decl_stmt|;
specifier|private
name|Long
name|iLastExpirationCheck
init|=
literal|null
decl_stmt|;
specifier|private
name|EventExpirationService
parameter_list|()
block|{
name|setName
argument_list|(
literal|"EventExpirationService"
argument_list|)
expr_stmt|;
name|setDaemon
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|iSleepTimeInMinutes
operator|=
name|Long
operator|.
name|parseLong
argument_list|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"unitime.events.expiration.updateIntervalInMinutes"
argument_list|,
literal|"5"
argument_list|)
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|checkForExpiredEventsIfNeeded
parameter_list|()
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
if|if
condition|(
name|iLastExpirationCheck
operator|!=
literal|null
operator|&&
name|ts
operator|-
name|iLastExpirationCheck
operator|<
literal|3600000
condition|)
return|return;
if|if
condition|(
name|iLastExpirationCheck
operator|==
literal|null
operator|||
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
comment|// first time after midnight or when executed for the first time (TODO: allow change)
try|try
block|{
name|sLog
operator|.
name|info
argument_list|(
literal|"Checking for expired events ..."
argument_list|)
expr_stmt|;
name|checkForExpiredEvents
argument_list|()
expr_stmt|;
name|iLastExpirationCheck
operator|=
name|ts
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
name|error
argument_list|(
literal|"Expired events check failed: "
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
name|void
name|checkForExpiredEvents
parameter_list|()
throws|throws
name|Exception
block|{
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
name|EventDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
decl_stmt|;
try|try
block|{
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
name|Date
name|now
init|=
operator|new
name|Date
argument_list|()
decl_stmt|;
for|for
control|(
name|Event
name|event
range|:
operator|(
name|List
argument_list|<
name|Event
argument_list|>
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"select distinct e from Event e inner join e.meetings m "
operator|+
literal|"where e.expirationDate is not null and m.approvalStatus = 0 and e.expirationDate< "
operator|+
name|HibernateUtil
operator|.
name|date
argument_list|(
operator|new
name|Date
argument_list|()
argument_list|)
argument_list|)
operator|.
name|list
argument_list|()
control|)
block|{
name|Set
argument_list|<
name|Meeting
argument_list|>
name|affectedMeetings
init|=
operator|new
name|HashSet
argument_list|<
name|Meeting
argument_list|>
argument_list|()
decl_stmt|;
name|String
name|affectedMeetingStr
init|=
literal|""
decl_stmt|;
for|for
control|(
name|Meeting
name|meeting
range|:
name|event
operator|.
name|getMeetings
argument_list|()
control|)
block|{
if|if
condition|(
name|meeting
operator|.
name|getStatus
argument_list|()
operator|==
name|Meeting
operator|.
name|Status
operator|.
name|PENDING
condition|)
block|{
name|meeting
operator|.
name|setStatus
argument_list|(
name|Meeting
operator|.
name|Status
operator|.
name|CANCELLED
argument_list|)
expr_stmt|;
name|meeting
operator|.
name|setApprovalDate
argument_list|(
name|now
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|meeting
argument_list|)
expr_stmt|;
name|affectedMeetings
operator|.
name|add
argument_list|(
name|meeting
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|affectedMeetingStr
operator|.
name|isEmpty
argument_list|()
condition|)
name|affectedMeetingStr
operator|+=
literal|"<br>"
expr_stmt|;
name|affectedMeetingStr
operator|+=
name|meeting
operator|.
name|getTimeLabel
argument_list|()
operator|+
operator|(
name|meeting
operator|.
name|getLocation
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
literal|" "
operator|+
name|meeting
operator|.
name|getLocation
argument_list|()
operator|.
name|getLabel
argument_list|()
operator|)
expr_stmt|;
block|}
block|}
name|EventNote
name|note
init|=
operator|new
name|EventNote
argument_list|()
decl_stmt|;
name|note
operator|.
name|setEvent
argument_list|(
name|event
argument_list|)
expr_stmt|;
name|note
operator|.
name|setNoteType
argument_list|(
name|EventNote
operator|.
name|sEventNoteTypeCancel
argument_list|)
expr_stmt|;
name|note
operator|.
name|setTimeStamp
argument_list|(
name|now
argument_list|)
expr_stmt|;
name|note
operator|.
name|setUser
argument_list|(
literal|"SYSTEM"
argument_list|)
expr_stmt|;
name|note
operator|.
name|setAffectedMeetings
argument_list|(
name|affectedMeetings
argument_list|)
expr_stmt|;
name|note
operator|.
name|setMeetings
argument_list|(
name|affectedMeetingStr
argument_list|)
expr_stmt|;
name|note
operator|.
name|setTextNote
argument_list|(
name|MESSAGES
operator|.
name|noteEventExpired
argument_list|()
argument_list|)
expr_stmt|;
name|event
operator|.
name|getNotes
argument_list|()
operator|.
name|add
argument_list|(
name|note
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|note
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|update
argument_list|(
name|event
argument_list|)
expr_stmt|;
try|try
block|{
name|EventEmail
operator|.
name|eventExpired
argument_list|(
name|event
argument_list|,
name|affectedMeetings
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
literal|"Failed to sent notification for "
operator|+
name|event
operator|.
name|getEventName
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
name|tx
operator|.
name|commit
argument_list|()
expr_stmt|;
name|tx
operator|=
literal|null
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
if|if
condition|(
name|tx
operator|!=
literal|null
condition|)
name|tx
operator|.
name|rollback
argument_list|()
expr_stmt|;
name|sLog
operator|.
name|error
argument_list|(
literal|"Failed to expire some events: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
throw|throw
name|e
throw|;
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
specifier|public
specifier|static
name|EventExpirationService
name|getInstance
parameter_list|()
block|{
if|if
condition|(
name|sInstance
operator|==
literal|null
condition|)
name|sInstance
operator|=
operator|new
name|EventExpirationService
argument_list|()
expr_stmt|;
return|return
name|sInstance
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|run
parameter_list|()
block|{
try|try
block|{
name|sLog
operator|.
name|info
argument_list|(
literal|"Event expiration service started."
argument_list|)
expr_stmt|;
while|while
condition|(
name|iActive
condition|)
block|{
name|checkForExpiredEventsIfNeeded
argument_list|()
expr_stmt|;
try|try
block|{
name|sleep
argument_list|(
name|iSleepTimeInMinutes
operator|*
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
block|}
name|sLog
operator|.
name|info
argument_list|(
literal|"Event expiration service stopped."
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
name|info
argument_list|(
literal|"Event expiration service failed, "
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
block|}
end_class

end_unit

