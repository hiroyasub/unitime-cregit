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
name|events
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
name|command
operator|.
name|server
operator|.
name|GwtRpcImplements
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
name|security
operator|.
name|SessionContext
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
name|context
operator|.
name|UniTimeUserContext
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
name|qualifiers
operator|.
name|SimpleQualifier
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
name|rights
operator|.
name|Right
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
name|util
operator|.
name|Formats
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|GwtRpcImplements
argument_list|(
name|AcademicSessionSelectionBox
operator|.
name|ListAcademicSessions
operator|.
name|class
argument_list|)
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
name|SessionContext
name|context
parameter_list|)
block|{
name|Formats
operator|.
name|Format
argument_list|<
name|Date
argument_list|>
name|df
init|=
name|Formats
operator|.
name|getDateFormat
argument_list|(
name|Formats
operator|.
name|Pattern
operator|.
name|DATE_EVENT
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
name|GwtRpcException
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
operator|(
name|context
operator|.
name|isAuthenticated
argument_list|()
operator|&&
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAuthority
argument_list|()
operator|!=
literal|null
condition|?
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
else|:
literal|null
operator|)
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
name|GwtRpcException
name|e
parameter_list|)
block|{
block|}
name|Right
name|permission
init|=
name|Right
operator|.
name|Events
decl_stmt|;
if|if
condition|(
name|command
operator|.
name|hasSource
argument_list|()
condition|)
name|permission
operator|=
name|Right
operator|.
name|valueOf
argument_list|(
name|command
operator|.
name|getSource
argument_list|()
argument_list|)
expr_stmt|;
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
argument_list|()
decl_stmt|;
for|for
control|(
name|Session
name|session
range|:
operator|(
name|List
argument_list|<
name|Session
argument_list|>
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"select s from Session s"
argument_list|)
operator|.
name|list
argument_list|()
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
if|if
condition|(
operator|!
name|context
operator|.
name|hasPermissionAnyAuthority
argument_list|(
name|permission
argument_list|,
operator|new
name|SimpleQualifier
argument_list|(
literal|"Session"
argument_list|,
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|)
argument_list|)
condition|)
continue|continue;
name|sessions
operator|.
name|add
argument_list|(
name|session
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|sessions
operator|.
name|isEmpty
argument_list|()
condition|)
throw|throw
operator|new
name|GwtRpcException
argument_list|(
name|MESSAGES
operator|.
name|noSessionAvailable
argument_list|()
argument_list|)
throw|;
if|if
condition|(
name|selected
operator|==
literal|null
operator|||
operator|!
name|sessions
operator|.
name|contains
argument_list|(
name|selected
argument_list|)
condition|)
name|selected
operator|=
name|UniTimeUserContext
operator|.
name|defaultSession
argument_list|(
name|sessions
argument_list|,
literal|null
argument_list|)
expr_stmt|;
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
if|if
condition|(
operator|!
name|command
operator|.
name|hasTerm
argument_list|()
operator|&&
operator|!
name|context
operator|.
name|hasPermissionAnyAuthority
argument_list|(
name|selected
argument_list|,
name|Right
operator|.
name|EventAddSpecial
argument_list|)
condition|)
block|{
name|TreeSet
argument_list|<
name|Session
argument_list|>
name|preferred
init|=
operator|new
name|TreeSet
argument_list|<
name|Session
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Session
name|session
range|:
name|sessions
control|)
if|if
condition|(
name|context
operator|.
name|hasPermissionAnyAuthority
argument_list|(
name|session
argument_list|,
name|Right
operator|.
name|EventAddSpecial
argument_list|,
operator|new
name|SimpleQualifier
argument_list|(
literal|"Session"
argument_list|,
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|)
argument_list|)
condition|)
name|preferred
operator|.
name|add
argument_list|(
name|session
argument_list|)
expr_stmt|;
if|else if
condition|(
name|context
operator|.
name|hasPermissionAnyAuthority
argument_list|(
name|session
argument_list|,
name|Right
operator|.
name|EventAddCourseRelated
argument_list|,
operator|new
name|SimpleQualifier
argument_list|(
literal|"Session"
argument_list|,
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|)
argument_list|)
condition|)
name|preferred
operator|.
name|add
argument_list|(
name|session
argument_list|)
expr_stmt|;
if|else if
condition|(
name|context
operator|.
name|hasPermissionAnyAuthority
argument_list|(
name|session
argument_list|,
name|Right
operator|.
name|EventAddUnavailable
argument_list|,
operator|new
name|SimpleQualifier
argument_list|(
literal|"Session"
argument_list|,
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|)
argument_list|)
condition|)
name|preferred
operator|.
name|add
argument_list|(
name|session
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|preferred
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|Session
name|defaultSession
init|=
name|UniTimeUserContext
operator|.
name|defaultSession
argument_list|(
name|preferred
argument_list|,
literal|null
argument_list|)
decl_stmt|;
if|if
condition|(
name|defaultSession
operator|!=
literal|null
condition|)
name|selected
operator|=
name|defaultSession
expr_stmt|;
block|}
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
for|for
control|(
name|Session
name|session
range|:
name|sessions
control|)
block|{
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
name|canNoRoleReportClass
argument_list|()
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
name|canNoRoleReportExamFinal
argument_list|()
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
name|canNoRoleReportExamMidterm
argument_list|()
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
name|context
operator|.
name|hasPermissionAnyAuthority
argument_list|(
name|session
argument_list|,
name|Right
operator|.
name|Events
argument_list|,
operator|new
name|SimpleQualifier
argument_list|(
literal|"Session"
argument_list|,
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|)
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
name|HasEvents
argument_list|)
expr_stmt|;
if|if
condition|(
name|context
operator|.
name|hasPermissionAnyAuthority
argument_list|(
name|session
argument_list|,
name|Right
operator|.
name|EventAddSpecial
argument_list|,
operator|new
name|SimpleQualifier
argument_list|(
literal|"Session"
argument_list|,
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|)
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
if|else if
condition|(
name|context
operator|.
name|hasPermissionAnyAuthority
argument_list|(
name|session
argument_list|,
name|Right
operator|.
name|EventAddCourseRelated
argument_list|,
operator|new
name|SimpleQualifier
argument_list|(
literal|"Session"
argument_list|,
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|)
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
if|else if
condition|(
name|context
operator|.
name|hasPermissionAnyAuthority
argument_list|(
name|session
argument_list|,
name|Right
operator|.
name|EventAddUnavailable
argument_list|,
operator|new
name|SimpleQualifier
argument_list|(
literal|"Session"
argument_list|,
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|)
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
literal|"select s from Session s where "
operator|+
literal|"s.academicTerm || s.academicYear = :term or "
operator|+
literal|"s.academicTerm || s.academicYear || s.academicInitiative = :term"
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
literal|"select s from Session s where "
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
name|GwtRpcException
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

