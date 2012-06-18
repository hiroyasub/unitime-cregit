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
name|events
package|;
end_package

begin_import
import|import
name|java
operator|.
name|text
operator|.
name|DateFormat
import|;
end_import

begin_import
import|import
name|java
operator|.
name|text
operator|.
name|SimpleDateFormat
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
name|TreeSet
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
name|client
operator|.
name|events
operator|.
name|AcademicSessionSelectionBox
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
name|client
operator|.
name|events
operator|.
name|AcademicSessionSelectionBox
operator|.
name|AcademicSession
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
name|GwtRpcResponseList
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
name|server
operator|.
name|GwtRpcHelper
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
name|server
operator|.
name|GwtRpcImplementation
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
name|GwtConstants
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
name|gwt
operator|.
name|shared
operator|.
name|EventException
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
name|PageAccessException
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
name|EventInterface
operator|.
name|EventType
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
name|Exam
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
name|Solution
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

begin_class
specifier|public
class|class
name|ListAcademicSessions
implements|implements
name|GwtRpcImplementation
argument_list|<
name|AcademicSessionSelectionBox
operator|.
name|ListAcademicSessions
argument_list|,
name|GwtRpcResponseList
argument_list|<
name|AcademicSession
argument_list|>
argument_list|>
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
specifier|protected
specifier|static
name|GwtConstants
name|CONSTANTS
init|=
name|Localization
operator|.
name|create
argument_list|(
name|GwtConstants
operator|.
name|class
argument_list|)
decl_stmt|;
annotation|@
name|Override
specifier|public
name|GwtRpcResponseList
argument_list|<
name|AcademicSession
argument_list|>
name|execute
parameter_list|(
name|AcademicSessionSelectionBox
operator|.
name|ListAcademicSessions
name|command
parameter_list|,
name|GwtRpcHelper
name|helper
parameter_list|)
block|{
comment|// Check authentication if needed
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
literal|"unitime.event_timetable.requires_authentication"
argument_list|,
literal|"true"
argument_list|)
argument_list|)
condition|)
block|{
if|if
condition|(
name|helper
operator|.
name|getUser
argument_list|()
operator|==
literal|null
condition|)
throw|throw
operator|new
name|PageAccessException
argument_list|(
name|helper
operator|.
name|isHttpSessionNew
argument_list|()
condition|?
name|MESSAGES
operator|.
name|authenticationExpired
argument_list|()
else|:
name|MESSAGES
operator|.
name|authenticationRequired
argument_list|()
argument_list|)
throw|;
block|}
name|DateFormat
name|df
init|=
operator|new
name|SimpleDateFormat
argument_list|(
name|CONSTANTS
operator|.
name|eventDateFormat
argument_list|()
argument_list|,
name|Localization
operator|.
name|getJavaLocale
argument_list|()
argument_list|)
decl_stmt|;
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
name|getSession
argument_list|()
decl_stmt|;
name|Session
name|selected
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|command
operator|.
name|hasTerm
argument_list|()
condition|)
block|{
try|try
block|{
name|selected
operator|=
name|findSession
argument_list|(
name|hibSession
argument_list|,
name|command
operator|.
name|getTerm
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|EventException
name|e
parameter_list|)
block|{
block|}
block|}
else|else
block|{
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
operator|!=
literal|null
condition|)
name|selected
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
block|}
if|if
condition|(
name|selected
operator|==
literal|null
condition|)
try|try
block|{
name|selected
operator|=
name|findSession
argument_list|(
name|hibSession
argument_list|,
literal|"current"
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|EventException
name|e
parameter_list|)
block|{
block|}
name|GwtRpcResponseList
argument_list|<
name|AcademicSession
argument_list|>
name|ret
init|=
operator|new
name|GwtRpcResponseList
argument_list|<
name|AcademicSession
argument_list|>
argument_list|()
decl_stmt|;
name|TreeSet
argument_list|<
name|Session
argument_list|>
name|sessions
init|=
operator|new
name|TreeSet
argument_list|<
name|Session
argument_list|>
argument_list|(
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"select s from Session s"
argument_list|)
operator|.
name|list
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|selected
operator|==
literal|null
condition|)
name|selected
operator|=
name|sessions
operator|.
name|last
argument_list|()
expr_stmt|;
for|for
control|(
name|Session
name|session
range|:
name|sessions
control|)
block|{
name|EventRights
name|rights
init|=
operator|new
name|SimpleEventRights
argument_list|(
name|helper
argument_list|,
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|session
operator|.
name|getStatusType
argument_list|()
operator|==
literal|null
operator|||
name|session
operator|.
name|getStatusType
argument_list|()
operator|.
name|isTestSession
argument_list|()
condition|)
continue|continue;
name|AcademicSession
name|acadSession
init|=
operator|new
name|AcademicSession
argument_list|(
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|session
operator|.
name|getLabel
argument_list|()
argument_list|,
name|session
operator|.
name|getAcademicTerm
argument_list|()
operator|+
name|session
operator|.
name|getAcademicYear
argument_list|()
operator|+
name|session
operator|.
name|getAcademicInitiative
argument_list|()
argument_list|,
name|df
operator|.
name|format
argument_list|(
name|session
operator|.
name|getEventBeginDate
argument_list|()
argument_list|)
operator|+
literal|" - "
operator|+
name|df
operator|.
name|format
argument_list|(
name|session
operator|.
name|getEventEndDate
argument_list|()
argument_list|)
argument_list|,
name|session
operator|.
name|equals
argument_list|(
name|selected
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|session
operator|.
name|getStatusType
argument_list|()
operator|.
name|canNoRoleReportClass
argument_list|()
operator|&&
name|Solution
operator|.
name|hasTimetable
argument_list|(
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|)
condition|)
name|acadSession
operator|.
name|set
argument_list|(
name|AcademicSession
operator|.
name|Flag
operator|.
name|HasClasses
argument_list|)
expr_stmt|;
if|if
condition|(
name|session
operator|.
name|getStatusType
argument_list|()
operator|.
name|canNoRoleReportExamFinal
argument_list|()
operator|&&
name|Exam
operator|.
name|hasTimetable
argument_list|(
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|Exam
operator|.
name|sExamTypeFinal
argument_list|)
condition|)
name|acadSession
operator|.
name|set
argument_list|(
name|AcademicSession
operator|.
name|Flag
operator|.
name|HasFinalExams
argument_list|)
expr_stmt|;
if|if
condition|(
name|session
operator|.
name|getStatusType
argument_list|()
operator|.
name|canNoRoleReportExamMidterm
argument_list|()
operator|&&
name|Exam
operator|.
name|hasTimetable
argument_list|(
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|Exam
operator|.
name|sExamTypeMidterm
argument_list|)
condition|)
name|acadSession
operator|.
name|set
argument_list|(
name|AcademicSession
operator|.
name|Flag
operator|.
name|HasMidtermExams
argument_list|)
expr_stmt|;
if|if
condition|(
name|rights
operator|.
name|isEventLocation
argument_list|(
literal|null
argument_list|)
condition|)
block|{
name|acadSession
operator|.
name|set
argument_list|(
name|AcademicSession
operator|.
name|Flag
operator|.
name|HasEvents
argument_list|)
expr_stmt|;
if|if
condition|(
name|rights
operator|.
name|canAddEvent
argument_list|(
name|EventType
operator|.
name|Special
argument_list|,
literal|null
argument_list|)
condition|)
name|acadSession
operator|.
name|set
argument_list|(
name|AcademicSession
operator|.
name|Flag
operator|.
name|CanAddEvents
argument_list|)
expr_stmt|;
block|}
name|Session
name|prev
init|=
literal|null
decl_stmt|,
name|next
init|=
literal|null
decl_stmt|;
for|for
control|(
name|Session
name|s
range|:
name|sessions
control|)
block|{
if|if
condition|(
name|s
operator|.
name|getUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|||
operator|!
name|s
operator|.
name|getAcademicInitiative
argument_list|()
operator|.
name|equals
argument_list|(
name|session
operator|.
name|getAcademicInitiative
argument_list|()
argument_list|)
condition|)
continue|continue;
if|if
condition|(
name|s
operator|.
name|getSessionEndDateTime
argument_list|()
operator|.
name|before
argument_list|(
name|session
operator|.
name|getSessionBeginDateTime
argument_list|()
argument_list|)
condition|)
block|{
comment|// before
if|if
condition|(
name|prev
operator|==
literal|null
operator|||
name|prev
operator|.
name|getSessionBeginDateTime
argument_list|()
operator|.
name|before
argument_list|(
name|s
operator|.
name|getSessionBeginDateTime
argument_list|()
argument_list|)
condition|)
block|{
name|prev
operator|=
name|s
expr_stmt|;
block|}
block|}
if|else if
condition|(
name|s
operator|.
name|getSessionBeginDateTime
argument_list|()
operator|.
name|after
argument_list|(
name|session
operator|.
name|getSessionEndDateTime
argument_list|()
argument_list|)
condition|)
block|{
comment|// after
if|if
condition|(
name|next
operator|==
literal|null
operator|||
name|next
operator|.
name|getSessionBeginDateTime
argument_list|()
operator|.
name|after
argument_list|(
name|s
operator|.
name|getSessionBeginDateTime
argument_list|()
argument_list|)
condition|)
block|{
name|next
operator|=
name|s
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
name|next
operator|!=
literal|null
condition|)
name|acadSession
operator|.
name|setNextId
argument_list|(
name|next
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|prev
operator|!=
literal|null
condition|)
name|acadSession
operator|.
name|setPreviousId
argument_list|(
name|prev
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|ret
operator|.
name|add
argument_list|(
name|acadSession
argument_list|)
expr_stmt|;
block|}
return|return
name|ret
return|;
block|}
specifier|public
specifier|static
name|Session
name|findSession
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|String
name|term
parameter_list|)
throws|throws
name|EventException
throws|,
name|PageAccessException
block|{
try|try
block|{
name|Session
name|ret
init|=
name|SessionDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|Long
operator|.
name|parseLong
argument_list|(
name|term
argument_list|)
argument_list|,
name|hibSession
argument_list|)
decl_stmt|;
if|if
condition|(
name|ret
operator|!=
literal|null
condition|)
return|return
name|ret
return|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|e
parameter_list|)
block|{
block|}
name|List
argument_list|<
name|Session
argument_list|>
name|sessions
init|=
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"select s from Session s, RoomTypeOption o where o.session = s and o.status = 1 and ("
operator|+
literal|"s.academicTerm || s.academicYear = :term or "
operator|+
literal|"s.academicTerm || s.academicYear || s.academicInitiative = :term)"
argument_list|)
operator|.
name|setString
argument_list|(
literal|"term"
argument_list|,
name|term
argument_list|)
operator|.
name|list
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|sessions
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
for|for
control|(
name|Session
name|session
range|:
name|sessions
control|)
block|{
if|if
condition|(
name|session
operator|.
name|getStatusType
argument_list|()
operator|==
literal|null
operator|||
name|session
operator|.
name|getStatusType
argument_list|()
operator|.
name|isTestSession
argument_list|()
condition|)
continue|continue;
return|return
name|session
return|;
block|}
block|}
if|if
condition|(
literal|"current"
operator|.
name|equalsIgnoreCase
argument_list|(
name|term
argument_list|)
condition|)
block|{
name|sessions
operator|=
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"select s from Session s, RoomTypeOption o where o.session = s and o.status = 1 and "
operator|+
literal|"s.eventBeginDate<= :today and s.eventEndDate>= :today"
argument_list|)
operator|.
name|setDate
argument_list|(
literal|"today"
argument_list|,
operator|new
name|Date
argument_list|()
argument_list|)
operator|.
name|list
argument_list|()
expr_stmt|;
if|if
condition|(
operator|!
name|sessions
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
for|for
control|(
name|Session
name|session
range|:
name|sessions
control|)
block|{
if|if
condition|(
name|session
operator|.
name|getStatusType
argument_list|()
operator|==
literal|null
operator|||
name|session
operator|.
name|getStatusType
argument_list|()
operator|.
name|isTestSession
argument_list|()
condition|)
continue|continue;
return|return
name|session
return|;
block|}
block|}
block|}
throw|throw
operator|new
name|EventException
argument_list|(
literal|"Academic session "
operator|+
name|term
operator|+
literal|" not found."
argument_list|)
throw|;
block|}
block|}
end_class

end_unit

