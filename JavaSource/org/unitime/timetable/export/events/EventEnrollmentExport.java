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
name|export
operator|.
name|events
package|;
end_package

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
name|Comparator
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
name|org
operator|.
name|springframework
operator|.
name|stereotype
operator|.
name|Service
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
name|events
operator|.
name|EventEnrollmentsBackend
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
name|events
operator|.
name|EventAction
operator|.
name|EventContext
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
name|export
operator|.
name|CSVPrinter
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
name|export
operator|.
name|ExportHelper
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
name|export
operator|.
name|Exporter
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
name|sectioning
operator|.
name|EnrollmentTable
operator|.
name|EnrollmentComparator
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
name|resources
operator|.
name|StudentSectioningMessages
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
name|ClassAssignmentInterface
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
name|ClassAssignmentInterface
operator|.
name|Conflict
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
name|ClassAssignmentInterface
operator|.
name|Enrollment
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
name|EventEnrollmentsRpcRequest
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
name|OnlineSectioningInterface
operator|.
name|WaitListMode
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
name|ClassEvent
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
name|Class_
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
name|CourseEvent
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
name|ExamEvent
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
name|ClassEventDAO
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
name|Class_DAO
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
name|CourseEventDAO
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
name|ExamEventDAO
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
operator|.
name|Format
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|Service
argument_list|(
literal|"org.unitime.timetable.export.Exporter:event-enrollments.csv"
argument_list|)
specifier|public
class|class
name|EventEnrollmentExport
implements|implements
name|Exporter
block|{
specifier|public
specifier|static
specifier|final
name|StudentSectioningMessages
name|MESSAGES
init|=
name|Localization
operator|.
name|create
argument_list|(
name|StudentSectioningMessages
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|GwtMessages
name|GWT_MSG
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
name|Format
argument_list|<
name|Date
argument_list|>
name|sDF
init|=
name|Formats
operator|.
name|getDateFormat
argument_list|(
name|Formats
operator|.
name|Pattern
operator|.
name|DATE_REQUEST
argument_list|)
decl_stmt|;
specifier|protected
specifier|static
name|Format
argument_list|<
name|Date
argument_list|>
name|sTSF
init|=
name|Formats
operator|.
name|getDateFormat
argument_list|(
name|Formats
operator|.
name|Pattern
operator|.
name|DATE_TIME_STAMP
argument_list|)
decl_stmt|;
annotation|@
name|Override
specifier|public
name|String
name|reference
parameter_list|()
block|{
return|return
literal|"event-enrollments.csv"
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|export
parameter_list|(
name|ExportHelper
name|helper
parameter_list|)
throws|throws
name|IOException
block|{
name|String
name|eventId
init|=
name|helper
operator|.
name|getParameter
argument_list|(
literal|"event"
argument_list|)
decl_stmt|;
if|if
condition|(
name|eventId
operator|==
literal|null
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Event parameter not provided."
argument_list|)
throw|;
name|Class_
name|clazz
init|=
literal|null
decl_stmt|;
name|Event
name|event
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|Long
operator|.
name|valueOf
argument_list|(
name|eventId
argument_list|)
operator|<
literal|0
condition|)
name|clazz
operator|=
name|Class_DAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
operator|-
name|Long
operator|.
name|valueOf
argument_list|(
name|eventId
argument_list|)
argument_list|)
expr_stmt|;
else|else
block|{
name|event
operator|=
name|EventDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|eventId
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|event
operator|!=
literal|null
operator|&&
name|event
operator|instanceof
name|CourseEvent
condition|)
name|event
operator|=
name|CourseEventDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|event
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
if|else if
condition|(
name|event
operator|!=
literal|null
operator|&&
name|event
operator|instanceof
name|ExamEvent
condition|)
name|event
operator|=
name|ExamEventDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|event
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
if|else if
condition|(
name|event
operator|!=
literal|null
operator|&&
name|event
operator|instanceof
name|ClassEvent
condition|)
name|event
operator|=
name|ClassEventDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|event
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|clazz
operator|==
literal|null
operator|&&
name|event
operator|==
literal|null
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Given event does not exist."
argument_list|)
throw|;
name|EventEnrollmentsRpcRequest
name|request
init|=
operator|new
name|EventEnrollmentsRpcRequest
argument_list|()
decl_stmt|;
name|request
operator|.
name|setEventId
argument_list|(
name|event
operator|!=
literal|null
condition|?
name|event
operator|.
name|getUniqueId
argument_list|()
else|:
operator|-
name|clazz
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|Session
name|session
init|=
operator|(
name|event
operator|!=
literal|null
condition|?
name|event
operator|.
name|getSession
argument_list|()
else|:
name|clazz
operator|.
name|getControllingDept
argument_list|()
operator|.
name|getSession
argument_list|()
operator|)
decl_stmt|;
name|EventContext
name|context
init|=
operator|new
name|EventContext
argument_list|(
name|helper
operator|.
name|getSessionContext
argument_list|()
argument_list|,
name|helper
operator|.
name|getSessionContext
argument_list|()
operator|.
name|getUser
argument_list|()
argument_list|,
name|session
operator|!=
literal|null
condition|?
name|session
operator|.
name|getUniqueId
argument_list|()
else|:
literal|null
argument_list|)
decl_stmt|;
name|int
name|sort
init|=
literal|0
decl_stmt|;
if|if
condition|(
name|helper
operator|.
name|getParameter
argument_list|(
literal|"sort"
argument_list|)
operator|!=
literal|null
condition|)
name|sort
operator|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|helper
operator|.
name|getParameter
argument_list|(
literal|"sort"
argument_list|)
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|Enrollment
argument_list|>
name|enrollments
init|=
operator|new
name|EventEnrollmentsBackend
argument_list|()
operator|.
name|execute
argument_list|(
name|request
argument_list|,
name|context
argument_list|)
decl_stmt|;
name|export
argument_list|(
name|enrollments
argument_list|,
name|helper
argument_list|,
literal|"1"
operator|.
name|equalsIgnoreCase
argument_list|(
name|helper
operator|.
name|getParameter
argument_list|(
literal|"suffix"
argument_list|)
argument_list|)
argument_list|,
name|sort
argument_list|,
name|helper
operator|.
name|getParameter
argument_list|(
literal|"subpart"
argument_list|)
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|Printer
name|createPrinter
parameter_list|(
name|ExportHelper
name|helper
parameter_list|)
throws|throws
name|IOException
block|{
name|Printer
name|out
init|=
operator|new
name|CSVPrinter
argument_list|(
name|helper
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|helper
operator|.
name|setup
argument_list|(
name|out
operator|.
name|getContentType
argument_list|()
argument_list|,
name|reference
argument_list|()
argument_list|,
literal|false
argument_list|)
expr_stmt|;
return|return
name|out
return|;
block|}
specifier|protected
name|void
name|export
parameter_list|(
name|List
argument_list|<
name|Enrollment
argument_list|>
name|enrollments
parameter_list|,
name|ExportHelper
name|helper
parameter_list|,
name|boolean
name|suffix
parameter_list|,
name|int
name|sort
parameter_list|,
name|String
name|sortBySubpart
parameter_list|)
throws|throws
name|IOException
block|{
name|Printer
name|out
init|=
name|createPrinter
argument_list|(
name|helper
argument_list|)
decl_stmt|;
if|if
condition|(
name|enrollments
operator|==
literal|null
condition|)
name|enrollments
operator|=
operator|new
name|ArrayList
argument_list|<
name|Enrollment
argument_list|>
argument_list|()
expr_stmt|;
if|if
condition|(
name|sort
operator|!=
literal|0
condition|)
block|{
name|boolean
name|asc
init|=
operator|(
name|sort
operator|>
literal|0
operator|)
decl_stmt|;
name|EnrollmentComparator
operator|.
name|SortBy
name|sortBy
init|=
name|EnrollmentComparator
operator|.
name|SortBy
operator|.
name|values
argument_list|()
index|[
name|Math
operator|.
name|abs
argument_list|(
name|sort
argument_list|)
operator|-
literal|1
index|]
decl_stmt|;
name|Collections
operator|.
name|sort
argument_list|(
name|enrollments
argument_list|,
operator|new
name|EnrollmentComparator
argument_list|(
name|sortBy
argument_list|,
name|helper
operator|.
name|getParameter
argument_list|(
literal|"group"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|asc
condition|)
name|Collections
operator|.
name|reverse
argument_list|(
name|enrollments
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|sortBySubpart
operator|!=
literal|null
operator|&&
operator|!
name|sortBySubpart
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|boolean
name|asc
init|=
operator|!
name|sortBySubpart
operator|.
name|startsWith
argument_list|(
literal|"-"
argument_list|)
decl_stmt|;
name|Collections
operator|.
name|sort
argument_list|(
name|enrollments
argument_list|,
operator|new
name|EnrollmentComparator
argument_list|(
name|asc
condition|?
name|sortBySubpart
else|:
name|sortBySubpart
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
argument_list|,
name|suffix
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|asc
condition|)
name|Collections
operator|.
name|reverse
argument_list|(
name|enrollments
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|Collections
operator|.
name|sort
argument_list|(
name|enrollments
argument_list|,
operator|new
name|Comparator
argument_list|<
name|ClassAssignmentInterface
operator|.
name|Enrollment
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|int
name|compare
parameter_list|(
name|ClassAssignmentInterface
operator|.
name|Enrollment
name|e1
parameter_list|,
name|ClassAssignmentInterface
operator|.
name|Enrollment
name|e2
parameter_list|)
block|{
name|int
name|cmp
init|=
name|e1
operator|.
name|getStudent
argument_list|()
operator|.
name|getName
argument_list|()
operator|.
name|compareTo
argument_list|(
name|e2
operator|.
name|getStudent
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|cmp
operator|!=
literal|0
condition|)
return|return
name|cmp
return|;
return|return
operator|(
name|e1
operator|.
name|getStudent
argument_list|()
operator|.
name|getId
argument_list|()
operator|<
name|e2
operator|.
name|getStudent
argument_list|()
operator|.
name|getId
argument_list|()
condition|?
operator|-
literal|1
else|:
literal|1
operator|)
return|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
name|boolean
name|hasExtId
init|=
literal|false
decl_stmt|;
for|for
control|(
name|ClassAssignmentInterface
operator|.
name|Enrollment
name|e
range|:
name|enrollments
control|)
block|{
if|if
condition|(
name|e
operator|.
name|getStudent
argument_list|()
operator|.
name|isCanShowExternalId
argument_list|()
condition|)
block|{
name|hasExtId
operator|=
literal|true
expr_stmt|;
break|break;
block|}
block|}
name|List
argument_list|<
name|String
argument_list|>
name|header
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
if|if
condition|(
name|hasExtId
condition|)
name|header
operator|.
name|add
argument_list|(
name|MESSAGES
operator|.
name|colStudentExternalId
argument_list|()
argument_list|)
expr_stmt|;
name|header
operator|.
name|add
argument_list|(
name|MESSAGES
operator|.
name|colStudent
argument_list|()
argument_list|)
expr_stmt|;
name|header
operator|.
name|add
argument_list|(
name|GWT_MSG
operator|.
name|colEmail
argument_list|()
argument_list|)
expr_stmt|;
name|boolean
name|crosslist
init|=
literal|false
decl_stmt|;
name|Long
name|courseId
init|=
literal|null
decl_stmt|;
for|for
control|(
name|ClassAssignmentInterface
operator|.
name|Enrollment
name|e
range|:
name|enrollments
control|)
block|{
if|if
condition|(
name|courseId
operator|==
literal|null
condition|)
name|courseId
operator|=
name|e
operator|.
name|getCourseId
argument_list|()
expr_stmt|;
if|else if
condition|(
name|e
operator|.
name|getCourseId
argument_list|()
operator|!=
name|courseId
condition|)
block|{
name|crosslist
operator|=
literal|true
expr_stmt|;
break|break;
block|}
if|if
condition|(
name|e
operator|.
name|getCourse
argument_list|()
operator|!=
literal|null
operator|&&
name|e
operator|.
name|getCourse
argument_list|()
operator|.
name|hasCrossList
argument_list|()
condition|)
block|{
name|crosslist
operator|=
literal|true
expr_stmt|;
break|break;
block|}
block|}
if|if
condition|(
name|crosslist
condition|)
name|header
operator|.
name|add
argument_list|(
name|MESSAGES
operator|.
name|colCourse
argument_list|()
argument_list|)
expr_stmt|;
name|boolean
name|hasPriority
init|=
literal|false
decl_stmt|,
name|hasArea
init|=
literal|false
decl_stmt|,
name|hasMajor
init|=
literal|false
decl_stmt|,
name|hasGroup
init|=
literal|false
decl_stmt|,
name|hasAcmd
init|=
literal|false
decl_stmt|,
name|hasAlternative
init|=
literal|false
decl_stmt|,
name|hasReservation
init|=
literal|false
decl_stmt|,
name|hasRequestedDate
init|=
literal|false
decl_stmt|,
name|hasEnrolledDate
init|=
literal|false
decl_stmt|,
name|hasConflict
init|=
literal|false
decl_stmt|,
name|hasMessage
init|=
literal|false
decl_stmt|,
name|hasAdvisor
init|=
literal|false
decl_stmt|,
name|hasMinor
init|=
literal|false
decl_stmt|,
name|hasConc
init|=
literal|false
decl_stmt|,
name|hasDeg
init|=
literal|false
decl_stmt|,
name|hasWaitlistedDate
init|=
literal|false
decl_stmt|,
name|hasWaitListedPosition
init|=
literal|false
decl_stmt|;
name|Set
argument_list|<
name|String
argument_list|>
name|groupTypes
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
name|ClassAssignmentInterface
operator|.
name|Enrollment
name|e
range|:
name|enrollments
control|)
block|{
if|if
condition|(
name|e
operator|.
name|getPriority
argument_list|()
operator|>
literal|0
condition|)
name|hasPriority
operator|=
literal|true
expr_stmt|;
if|if
condition|(
name|e
operator|.
name|isAlternative
argument_list|()
condition|)
name|hasAlternative
operator|=
literal|true
expr_stmt|;
if|if
condition|(
name|e
operator|.
name|getStudent
argument_list|()
operator|.
name|hasArea
argument_list|()
condition|)
name|hasArea
operator|=
literal|true
expr_stmt|;
if|if
condition|(
name|e
operator|.
name|getStudent
argument_list|()
operator|.
name|hasMajor
argument_list|()
condition|)
name|hasMajor
operator|=
literal|true
expr_stmt|;
if|if
condition|(
name|e
operator|.
name|getStudent
argument_list|()
operator|.
name|hasGroup
argument_list|()
condition|)
name|hasGroup
operator|=
literal|true
expr_stmt|;
if|if
condition|(
name|e
operator|.
name|getStudent
argument_list|()
operator|.
name|hasAccommodation
argument_list|()
condition|)
name|hasAcmd
operator|=
literal|true
expr_stmt|;
if|if
condition|(
name|e
operator|.
name|getReservation
argument_list|()
operator|!=
literal|null
condition|)
name|hasReservation
operator|=
literal|true
expr_stmt|;
if|if
condition|(
name|e
operator|.
name|getRequestedDate
argument_list|()
operator|!=
literal|null
condition|)
name|hasRequestedDate
operator|=
literal|true
expr_stmt|;
if|if
condition|(
name|e
operator|.
name|getEnrolledDate
argument_list|()
operator|!=
literal|null
condition|)
name|hasEnrolledDate
operator|=
literal|true
expr_stmt|;
if|if
condition|(
name|e
operator|.
name|hasConflict
argument_list|()
condition|)
name|hasConflict
operator|=
literal|true
expr_stmt|;
if|if
condition|(
name|e
operator|.
name|hasEnrollmentMessage
argument_list|()
condition|)
name|hasMessage
operator|=
literal|true
expr_stmt|;
if|if
condition|(
name|e
operator|.
name|getStudent
argument_list|()
operator|.
name|hasGroups
argument_list|()
condition|)
name|groupTypes
operator|.
name|addAll
argument_list|(
name|e
operator|.
name|getStudent
argument_list|()
operator|.
name|getGroupTypes
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|e
operator|.
name|getStudent
argument_list|()
operator|.
name|hasAdvisor
argument_list|()
condition|)
name|hasAdvisor
operator|=
literal|true
expr_stmt|;
if|if
condition|(
name|e
operator|.
name|getStudent
argument_list|()
operator|.
name|hasMinor
argument_list|()
condition|)
name|hasMinor
operator|=
literal|true
expr_stmt|;
if|if
condition|(
name|e
operator|.
name|getStudent
argument_list|()
operator|.
name|hasConcentration
argument_list|()
condition|)
name|hasConc
operator|=
literal|true
expr_stmt|;
if|if
condition|(
name|e
operator|.
name|getStudent
argument_list|()
operator|.
name|hasDegree
argument_list|()
condition|)
name|hasDeg
operator|=
literal|true
expr_stmt|;
if|if
condition|(
name|e
operator|.
name|hasWaitListedDate
argument_list|()
condition|)
name|hasWaitlistedDate
operator|=
literal|true
expr_stmt|;
if|if
condition|(
name|e
operator|.
name|hasWaitListedPosition
argument_list|()
condition|)
name|hasWaitListedPosition
operator|=
literal|true
expr_stmt|;
block|}
if|if
condition|(
name|hasPriority
condition|)
name|header
operator|.
name|add
argument_list|(
name|MESSAGES
operator|.
name|colPriority
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|hasAlternative
condition|)
name|header
operator|.
name|add
argument_list|(
name|MESSAGES
operator|.
name|colAlternative
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|hasArea
condition|)
block|{
name|header
operator|.
name|add
argument_list|(
name|MESSAGES
operator|.
name|colArea
argument_list|()
argument_list|)
expr_stmt|;
name|header
operator|.
name|add
argument_list|(
name|MESSAGES
operator|.
name|colClassification
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|hasDeg
condition|)
name|header
operator|.
name|add
argument_list|(
name|MESSAGES
operator|.
name|colDegree
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|hasMajor
condition|)
name|header
operator|.
name|add
argument_list|(
name|MESSAGES
operator|.
name|colMajor
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|hasConc
condition|)
name|header
operator|.
name|add
argument_list|(
name|MESSAGES
operator|.
name|colConcentration
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|hasMinor
condition|)
name|header
operator|.
name|add
argument_list|(
name|MESSAGES
operator|.
name|colMinor
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|hasGroup
condition|)
name|header
operator|.
name|add
argument_list|(
name|MESSAGES
operator|.
name|colGroup
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|String
name|g
range|:
name|groupTypes
control|)
name|header
operator|.
name|add
argument_list|(
name|g
argument_list|)
expr_stmt|;
if|if
condition|(
name|hasAcmd
condition|)
name|header
operator|.
name|add
argument_list|(
name|MESSAGES
operator|.
name|colAccommodation
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|hasReservation
condition|)
name|header
operator|.
name|add
argument_list|(
name|MESSAGES
operator|.
name|colReservation
argument_list|()
argument_list|)
expr_stmt|;
specifier|final
name|TreeSet
argument_list|<
name|String
argument_list|>
name|subparts
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
name|ClassAssignmentInterface
operator|.
name|Enrollment
name|e
range|:
name|enrollments
control|)
block|{
if|if
condition|(
name|e
operator|.
name|hasClasses
argument_list|()
condition|)
for|for
control|(
name|ClassAssignmentInterface
operator|.
name|ClassAssignment
name|c
range|:
name|e
operator|.
name|getClasses
argument_list|()
control|)
name|subparts
operator|.
name|add
argument_list|(
name|c
operator|.
name|getSubpart
argument_list|()
argument_list|)
expr_stmt|;
block|}
for|for
control|(
specifier|final
name|String
name|subpart
range|:
name|subparts
control|)
block|{
name|header
operator|.
name|add
argument_list|(
name|subpart
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|hasRequestedDate
condition|)
name|header
operator|.
name|add
argument_list|(
name|MESSAGES
operator|.
name|colRequestTimeStamp
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|hasEnrolledDate
condition|)
name|header
operator|.
name|add
argument_list|(
name|MESSAGES
operator|.
name|colEnrollmentTimeStamp
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|hasWaitlistedDate
condition|)
name|header
operator|.
name|add
argument_list|(
name|MESSAGES
operator|.
name|colWaitListedTimeStamp
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|hasWaitListedPosition
condition|)
name|header
operator|.
name|add
argument_list|(
name|MESSAGES
operator|.
name|colWaitListPosition
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|hasAdvisor
condition|)
name|header
operator|.
name|add
argument_list|(
name|MESSAGES
operator|.
name|colAdvisor
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|hasMessage
condition|)
name|header
operator|.
name|add
argument_list|(
name|MESSAGES
operator|.
name|colMessage
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|hasConflict
condition|)
block|{
name|header
operator|.
name|add
argument_list|(
name|MESSAGES
operator|.
name|colConflictType
argument_list|()
argument_list|)
expr_stmt|;
name|header
operator|.
name|add
argument_list|(
name|MESSAGES
operator|.
name|colConflictName
argument_list|()
argument_list|)
expr_stmt|;
name|header
operator|.
name|add
argument_list|(
name|MESSAGES
operator|.
name|colConflictDate
argument_list|()
argument_list|)
expr_stmt|;
name|header
operator|.
name|add
argument_list|(
name|MESSAGES
operator|.
name|colConflictTime
argument_list|()
argument_list|)
expr_stmt|;
name|header
operator|.
name|add
argument_list|(
name|MESSAGES
operator|.
name|colConflictRoom
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|out
operator|.
name|printHeader
argument_list|(
name|header
operator|.
name|toArray
argument_list|(
operator|new
name|String
index|[
name|header
operator|.
name|size
argument_list|()
index|]
argument_list|)
argument_list|)
expr_stmt|;
name|out
operator|.
name|flush
argument_list|()
expr_stmt|;
for|for
control|(
name|ClassAssignmentInterface
operator|.
name|Enrollment
name|enrollment
range|:
name|enrollments
control|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|line
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
if|if
condition|(
name|hasExtId
condition|)
name|line
operator|.
name|add
argument_list|(
name|enrollment
operator|.
name|getStudent
argument_list|()
operator|.
name|isCanShowExternalId
argument_list|()
condition|?
name|enrollment
operator|.
name|getStudent
argument_list|()
operator|.
name|getExternalId
argument_list|()
else|:
literal|""
argument_list|)
expr_stmt|;
name|line
operator|.
name|add
argument_list|(
name|enrollment
operator|.
name|getStudent
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|line
operator|.
name|add
argument_list|(
name|enrollment
operator|.
name|getStudent
argument_list|()
operator|.
name|getEmail
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|crosslist
condition|)
name|line
operator|.
name|add
argument_list|(
name|enrollment
operator|.
name|getCourseName
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|hasPriority
condition|)
name|line
operator|.
name|add
argument_list|(
name|enrollment
operator|.
name|getPriority
argument_list|()
operator|<=
literal|0
condition|?
literal|""
else|:
name|MESSAGES
operator|.
name|priority
argument_list|(
name|enrollment
operator|.
name|getPriority
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|hasAlternative
condition|)
name|line
operator|.
name|add
argument_list|(
name|enrollment
operator|.
name|getAlternative
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|hasArea
condition|)
block|{
name|line
operator|.
name|add
argument_list|(
name|enrollment
operator|.
name|getStudent
argument_list|()
operator|.
name|getArea
argument_list|(
literal|"\n"
argument_list|)
argument_list|)
expr_stmt|;
name|line
operator|.
name|add
argument_list|(
name|enrollment
operator|.
name|getStudent
argument_list|()
operator|.
name|getClassification
argument_list|(
literal|"\n"
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|hasDeg
condition|)
name|line
operator|.
name|add
argument_list|(
name|enrollment
operator|.
name|getStudent
argument_list|()
operator|.
name|getDegree
argument_list|(
literal|"\n"
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|hasMajor
condition|)
name|line
operator|.
name|add
argument_list|(
name|enrollment
operator|.
name|getStudent
argument_list|()
operator|.
name|getMajor
argument_list|(
literal|"\n"
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|hasConc
condition|)
name|line
operator|.
name|add
argument_list|(
name|enrollment
operator|.
name|getStudent
argument_list|()
operator|.
name|getConcentration
argument_list|(
literal|"\n"
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|hasMinor
condition|)
name|line
operator|.
name|add
argument_list|(
name|enrollment
operator|.
name|getStudent
argument_list|()
operator|.
name|getMinor
argument_list|(
literal|"\n"
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|hasGroup
condition|)
name|line
operator|.
name|add
argument_list|(
name|enrollment
operator|.
name|getStudent
argument_list|()
operator|.
name|getGroup
argument_list|(
literal|"\n"
argument_list|)
argument_list|)
expr_stmt|;
for|for
control|(
name|String
name|g
range|:
name|groupTypes
control|)
name|line
operator|.
name|add
argument_list|(
name|enrollment
operator|.
name|getStudent
argument_list|()
operator|.
name|getGroup
argument_list|(
name|g
argument_list|,
literal|"\n"
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|hasAcmd
condition|)
name|line
operator|.
name|add
argument_list|(
name|enrollment
operator|.
name|getStudent
argument_list|()
operator|.
name|getAccommodation
argument_list|(
literal|"\n"
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|hasReservation
condition|)
name|line
operator|.
name|add
argument_list|(
name|enrollment
operator|.
name|getReservation
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|enrollment
operator|.
name|getReservation
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|subparts
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
if|if
condition|(
operator|!
name|enrollment
operator|.
name|hasClasses
argument_list|()
condition|)
block|{
name|line
operator|.
name|add
argument_list|(
name|enrollment
operator|.
name|isWaitList
argument_list|()
operator|&&
name|enrollment
operator|.
name|getStudent
argument_list|()
operator|.
name|getWaitListMode
argument_list|()
operator|==
name|WaitListMode
operator|.
name|WaitList
condition|?
name|MESSAGES
operator|.
name|courseWaitListed
argument_list|()
else|:
name|MESSAGES
operator|.
name|courseNotEnrolled
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|1
init|;
name|i
operator|<
name|subparts
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
name|line
operator|.
name|add
argument_list|(
literal|""
argument_list|)
expr_stmt|;
block|}
else|else
for|for
control|(
name|String
name|subpart
range|:
name|subparts
control|)
block|{
name|line
operator|.
name|add
argument_list|(
name|enrollment
operator|.
name|getClasses
argument_list|(
name|subpart
argument_list|,
literal|", "
argument_list|,
name|suffix
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|hasRequestedDate
condition|)
name|line
operator|.
name|add
argument_list|(
name|enrollment
operator|.
name|getRequestedDate
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|sDF
operator|.
name|format
argument_list|(
name|enrollment
operator|.
name|getRequestedDate
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|hasEnrolledDate
condition|)
name|line
operator|.
name|add
argument_list|(
name|enrollment
operator|.
name|getEnrolledDate
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|sDF
operator|.
name|format
argument_list|(
name|enrollment
operator|.
name|getEnrolledDate
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|hasWaitlistedDate
condition|)
name|line
operator|.
name|add
argument_list|(
name|enrollment
operator|.
name|hasWaitListedDate
argument_list|()
condition|?
name|sTSF
operator|.
name|format
argument_list|(
name|enrollment
operator|.
name|getWaitListedDate
argument_list|()
argument_list|)
else|:
literal|""
argument_list|)
expr_stmt|;
if|if
condition|(
name|hasWaitListedPosition
condition|)
name|line
operator|.
name|add
argument_list|(
name|enrollment
operator|.
name|hasWaitListedPosition
argument_list|()
condition|?
name|enrollment
operator|.
name|getWaitListedPosition
argument_list|()
else|:
literal|""
argument_list|)
expr_stmt|;
if|if
condition|(
name|hasAdvisor
condition|)
name|line
operator|.
name|add
argument_list|(
name|enrollment
operator|.
name|getStudent
argument_list|()
operator|.
name|getAdvisor
argument_list|(
literal|"\n"
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|hasMessage
condition|)
name|line
operator|.
name|add
argument_list|(
name|enrollment
operator|.
name|hasEnrollmentMessage
argument_list|()
condition|?
name|enrollment
operator|.
name|getEnrollmentMessage
argument_list|()
else|:
literal|""
argument_list|)
expr_stmt|;
if|if
condition|(
name|hasConflict
condition|)
block|{
if|if
condition|(
name|enrollment
operator|.
name|hasConflict
argument_list|()
condition|)
block|{
name|String
name|name
init|=
literal|""
decl_stmt|,
name|type
init|=
literal|""
decl_stmt|,
name|date
init|=
literal|""
decl_stmt|,
name|time
init|=
literal|""
decl_stmt|,
name|room
init|=
literal|""
decl_stmt|;
for|for
control|(
name|Conflict
name|conflict
range|:
name|enrollment
operator|.
name|getConflicts
argument_list|()
control|)
block|{
if|if
condition|(
operator|!
name|name
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|name
operator|+=
literal|"\n"
expr_stmt|;
name|type
operator|+=
literal|"\n"
expr_stmt|;
name|date
operator|+=
literal|"\n"
expr_stmt|;
name|time
operator|+=
literal|"\n"
expr_stmt|;
name|room
operator|+=
literal|"\n"
expr_stmt|;
block|}
name|name
operator|+=
name|conflict
operator|.
name|getName
argument_list|()
operator|.
name|replace
argument_list|(
literal|"<br>"
argument_list|,
literal|"\n"
argument_list|)
expr_stmt|;
empty_stmt|;
name|type
operator|+=
name|conflict
operator|.
name|getType
argument_list|()
operator|.
name|replace
argument_list|(
literal|"<br>"
argument_list|,
literal|"\n"
argument_list|)
expr_stmt|;
empty_stmt|;
name|date
operator|+=
name|conflict
operator|.
name|getDate
argument_list|()
operator|.
name|replace
argument_list|(
literal|"<br>"
argument_list|,
literal|"\n"
argument_list|)
expr_stmt|;
empty_stmt|;
name|time
operator|+=
name|conflict
operator|.
name|getTime
argument_list|()
operator|.
name|replace
argument_list|(
literal|"<br>"
argument_list|,
literal|"\n"
argument_list|)
expr_stmt|;
empty_stmt|;
name|room
operator|+=
name|conflict
operator|.
name|getRoom
argument_list|()
operator|.
name|replace
argument_list|(
literal|"<br>"
argument_list|,
literal|"\n"
argument_list|)
expr_stmt|;
block|}
name|line
operator|.
name|add
argument_list|(
name|type
argument_list|)
expr_stmt|;
name|line
operator|.
name|add
argument_list|(
name|name
argument_list|)
expr_stmt|;
name|line
operator|.
name|add
argument_list|(
name|date
argument_list|)
expr_stmt|;
name|line
operator|.
name|add
argument_list|(
name|time
argument_list|)
expr_stmt|;
name|line
operator|.
name|add
argument_list|(
name|room
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|line
operator|.
name|add
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|line
operator|.
name|add
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|line
operator|.
name|add
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|line
operator|.
name|add
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|line
operator|.
name|add
argument_list|(
literal|""
argument_list|)
expr_stmt|;
block|}
block|}
name|out
operator|.
name|printLine
argument_list|(
name|line
operator|.
name|toArray
argument_list|(
operator|new
name|String
index|[
name|line
operator|.
name|size
argument_list|()
index|]
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|out
operator|.
name|flush
argument_list|()
expr_stmt|;
name|out
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

