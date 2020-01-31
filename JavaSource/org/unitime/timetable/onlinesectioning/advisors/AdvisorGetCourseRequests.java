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
name|onlinesectioning
operator|.
name|advisors
package|;
end_package

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
name|resources
operator|.
name|StudentSectioningConstants
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
name|server
operator|.
name|DayCode
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
name|CourseRequestInterface
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
name|CourseRequestInterface
operator|.
name|RequestPriority
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
name|CourseRequestInterface
operator|.
name|RequestedCourse
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
name|CourseRequestInterface
operator|.
name|RequestedCourseStatus
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
name|AdvisorClassPref
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
name|AdvisorCourseRequest
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
name|AdvisorInstrMthPref
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
name|AdvisorSectioningPref
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
name|CourseCreditUnitConfig
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
name|CourseDemand
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
name|CourseRequest
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
name|IndividualReservation
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
name|LearningCommunityReservation
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
name|Reservation
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
name|StudentClassPref
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
name|StudentGroupReservation
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
name|StudentInstrMthPref
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
name|StudentSectioningPref
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
name|OnlineSectioningAction
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
name|OnlineSectioningHelper
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
name|NameFormat
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
specifier|public
class|class
name|AdvisorGetCourseRequests
implements|implements
name|OnlineSectioningAction
argument_list|<
name|CourseRequestInterface
argument_list|>
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
specifier|protected
specifier|static
name|StudentSectioningConstants
name|CONST
init|=
name|Localization
operator|.
name|create
argument_list|(
name|StudentSectioningConstants
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|protected
specifier|static
name|StudentSectioningMessages
name|MSG
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
specifier|private
name|Long
name|iStudentId
decl_stmt|;
specifier|private
name|boolean
name|iCheckExistingDemands
init|=
literal|false
decl_stmt|;
specifier|public
name|AdvisorGetCourseRequests
name|forStudent
parameter_list|(
name|Long
name|id
parameter_list|)
block|{
name|iStudentId
operator|=
name|id
expr_stmt|;
return|return
name|this
return|;
block|}
specifier|public
name|AdvisorGetCourseRequests
name|checkDemands
parameter_list|(
name|boolean
name|check
parameter_list|)
block|{
name|iCheckExistingDemands
operator|=
name|check
expr_stmt|;
return|return
name|this
return|;
block|}
annotation|@
name|Override
specifier|public
name|CourseRequestInterface
name|execute
parameter_list|(
name|OnlineSectioningServer
name|server
parameter_list|,
name|OnlineSectioningHelper
name|helper
parameter_list|)
block|{
name|OnlineSectioningLog
operator|.
name|Action
operator|.
name|Builder
name|action
init|=
name|helper
operator|.
name|getAction
argument_list|()
decl_stmt|;
name|action
operator|.
name|setStudent
argument_list|(
name|OnlineSectioningLog
operator|.
name|Entity
operator|.
name|newBuilder
argument_list|()
operator|.
name|setUniqueId
argument_list|(
name|iStudentId
argument_list|)
argument_list|)
expr_stmt|;
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
name|iStudentId
argument_list|,
name|helper
operator|.
name|getHibSession
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|student
operator|!=
literal|null
condition|)
block|{
name|action
operator|.
name|getStudentBuilder
argument_list|()
operator|.
name|setExternalId
argument_list|(
name|student
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|action
operator|.
name|getStudentBuilder
argument_list|()
operator|.
name|setName
argument_list|(
name|NameFormat
operator|.
name|LAST_FIRST_MIDDLE
operator|.
name|format
argument_list|(
name|student
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|student
operator|.
name|getSectioningStatus
argument_list|()
operator|!=
literal|null
condition|)
name|action
operator|.
name|addOptionBuilder
argument_list|()
operator|.
name|setKey
argument_list|(
literal|"status"
argument_list|)
operator|.
name|setValue
argument_list|(
name|student
operator|.
name|getSectioningStatus
argument_list|()
operator|.
name|getReference
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|CourseRequestInterface
name|request
init|=
operator|new
name|CourseRequestInterface
argument_list|()
decl_stmt|;
name|request
operator|.
name|setStudentId
argument_list|(
name|iStudentId
argument_list|)
expr_stmt|;
name|request
operator|.
name|setAcademicSessionId
argument_list|(
name|server
operator|.
name|getAcademicSession
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|AdvisorCourseRequest
argument_list|>
name|acrs
init|=
name|helper
operator|.
name|getHibSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"from AdvisorCourseRequest where student = :studentId order by priority, alternative"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"studentId"
argument_list|,
name|iStudentId
argument_list|)
operator|.
name|list
argument_list|()
decl_stmt|;
if|if
condition|(
name|student
operator|!=
literal|null
operator|&&
name|iCheckExistingDemands
condition|)
block|{
name|Format
argument_list|<
name|Date
argument_list|>
name|ts
init|=
name|Formats
operator|.
name|getDateFormat
argument_list|(
name|Formats
operator|.
name|Pattern
operator|.
name|DATE_TIME_STAMP_SHORT
argument_list|)
decl_stmt|;
name|TreeSet
argument_list|<
name|CourseDemand
argument_list|>
name|demands
init|=
operator|new
name|TreeSet
argument_list|<
name|CourseDemand
argument_list|>
argument_list|(
operator|new
name|Comparator
argument_list|<
name|CourseDemand
argument_list|>
argument_list|()
block|{
specifier|public
name|int
name|compare
parameter_list|(
name|CourseDemand
name|d1
parameter_list|,
name|CourseDemand
name|d2
parameter_list|)
block|{
if|if
condition|(
name|d1
operator|.
name|isAlternative
argument_list|()
operator|&&
operator|!
name|d2
operator|.
name|isAlternative
argument_list|()
condition|)
return|return
literal|1
return|;
if|if
condition|(
operator|!
name|d1
operator|.
name|isAlternative
argument_list|()
operator|&&
name|d2
operator|.
name|isAlternative
argument_list|()
condition|)
return|return
operator|-
literal|1
return|;
name|int
name|cmp
init|=
name|d1
operator|.
name|getPriority
argument_list|()
operator|.
name|compareTo
argument_list|(
name|d2
operator|.
name|getPriority
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
name|d1
operator|.
name|getUniqueId
argument_list|()
operator|.
name|compareTo
argument_list|(
name|d2
operator|.
name|getUniqueId
argument_list|()
argument_list|)
return|;
block|}
block|}
argument_list|)
decl_stmt|;
name|demands
operator|.
name|addAll
argument_list|(
name|student
operator|.
name|getCourseDemands
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|CourseDemand
name|cd
range|:
name|demands
control|)
block|{
name|CourseRequestInterface
operator|.
name|Request
name|r
init|=
literal|null
decl_stmt|;
if|if
condition|(
operator|!
name|cd
operator|.
name|getCourseRequests
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|r
operator|=
operator|new
name|CourseRequestInterface
operator|.
name|Request
argument_list|()
expr_stmt|;
name|boolean
name|enrolled
init|=
literal|false
decl_stmt|,
name|reserved
init|=
literal|false
decl_stmt|;
for|for
control|(
name|CourseRequest
name|course
range|:
operator|new
name|TreeSet
argument_list|<
name|CourseRequest
argument_list|>
argument_list|(
name|cd
operator|.
name|getCourseRequests
argument_list|()
argument_list|)
control|)
block|{
name|RequestedCourse
name|rc
init|=
operator|new
name|RequestedCourse
argument_list|()
decl_stmt|;
name|rc
operator|.
name|setCourseId
argument_list|(
name|course
operator|.
name|getCourseOffering
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|rc
operator|.
name|setCourseName
argument_list|(
name|course
operator|.
name|getCourseOffering
argument_list|()
operator|.
name|getSubjectAreaAbbv
argument_list|()
operator|+
literal|" "
operator|+
name|course
operator|.
name|getCourseOffering
argument_list|()
operator|.
name|getCourseNbr
argument_list|()
operator|+
operator|(
operator|!
name|CONST
operator|.
name|showCourseTitle
argument_list|()
condition|?
literal|""
else|:
literal|" - "
operator|+
name|course
operator|.
name|getCourseOffering
argument_list|()
operator|.
name|getTitle
argument_list|()
operator|)
argument_list|)
expr_stmt|;
name|rc
operator|.
name|setCourseTitle
argument_list|(
name|course
operator|.
name|getCourseOffering
argument_list|()
operator|.
name|getTitle
argument_list|()
argument_list|)
expr_stmt|;
name|CourseCreditUnitConfig
name|credit
init|=
name|course
operator|.
name|getCourseOffering
argument_list|()
operator|.
name|getCredit
argument_list|()
decl_stmt|;
if|if
condition|(
name|credit
operator|!=
literal|null
condition|)
name|rc
operator|.
name|setCredit
argument_list|(
name|credit
operator|.
name|getMinCredit
argument_list|()
argument_list|,
name|credit
operator|.
name|getMaxCredit
argument_list|()
argument_list|)
expr_stmt|;
name|Date
name|hasEnrollments
init|=
literal|null
decl_stmt|;
for|for
control|(
name|StudentClassEnrollment
name|e
range|:
name|course
operator|.
name|getClassEnrollments
argument_list|()
control|)
if|if
condition|(
name|hasEnrollments
operator|==
literal|null
operator|||
name|hasEnrollments
operator|.
name|before
argument_list|(
name|e
operator|.
name|getTimestamp
argument_list|()
argument_list|)
condition|)
name|hasEnrollments
operator|=
name|e
operator|.
name|getTimestamp
argument_list|()
expr_stmt|;
name|rc
operator|.
name|setReadOnly
argument_list|(
name|hasEnrollments
operator|!=
literal|null
argument_list|)
expr_stmt|;
name|rc
operator|.
name|setCanDelete
argument_list|(
name|hasEnrollments
operator|==
literal|null
argument_list|)
expr_stmt|;
if|if
condition|(
name|hasEnrollments
operator|!=
literal|null
condition|)
block|{
name|rc
operator|.
name|setStatus
argument_list|(
name|RequestedCourseStatus
operator|.
name|ENROLLED
argument_list|)
expr_stmt|;
name|enrolled
operator|=
literal|true
expr_stmt|;
block|}
else|else
name|rc
operator|.
name|setStatus
argument_list|(
name|RequestedCourseStatus
operator|.
name|SAVED
argument_list|)
expr_stmt|;
if|if
condition|(
name|hasEnrollments
operator|!=
literal|null
condition|)
block|{
name|rc
operator|.
name|setCanChangeAlternatives
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|rc
operator|.
name|setCanChangePriority
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|r
operator|.
name|addAdvisorNote
argument_list|(
name|MSG
operator|.
name|noteEnrolled
argument_list|(
name|course
operator|.
name|getCourseOffering
argument_list|()
operator|.
name|getSubjectAreaAbbv
argument_list|()
operator|+
literal|" "
operator|+
name|course
operator|.
name|getCourseOffering
argument_list|()
operator|.
name|getCourseNbr
argument_list|()
argument_list|,
name|ts
operator|.
name|format
argument_list|(
name|hasEnrollments
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|Reservation
name|reservation
range|:
name|course
operator|.
name|getCourseOffering
argument_list|()
operator|.
name|getInstructionalOffering
argument_list|()
operator|.
name|getReservations
argument_list|()
control|)
block|{
if|if
condition|(
name|reservation
operator|instanceof
name|IndividualReservation
operator|||
name|reservation
operator|instanceof
name|StudentGroupReservation
operator|||
name|reservation
operator|instanceof
name|LearningCommunityReservation
condition|)
block|{
if|if
condition|(
name|reservation
operator|.
name|isMustBeUsed
argument_list|()
operator|&&
name|reservation
operator|.
name|isApplicable
argument_list|(
name|student
argument_list|,
name|course
argument_list|)
condition|)
block|{
comment|// !reservation.isExpired()&&
name|rc
operator|.
name|setReadOnly
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|rc
operator|.
name|setCanDelete
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|rc
operator|.
name|setCanChangeAlternatives
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|rc
operator|.
name|setCanChangePriority
argument_list|(
literal|false
argument_list|)
expr_stmt|;
if|if
condition|(
name|reservation
operator|instanceof
name|StudentGroupReservation
condition|)
name|r
operator|.
name|addAdvisorNote
argument_list|(
name|MSG
operator|.
name|noteHasGroupReservation
argument_list|(
operator|(
operator|(
name|StudentGroupReservation
operator|)
name|reservation
operator|)
operator|.
name|getGroup
argument_list|()
operator|.
name|getGroupName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
if|else if
condition|(
name|reservation
operator|instanceof
name|LearningCommunityReservation
condition|)
name|r
operator|.
name|addAdvisorNote
argument_list|(
name|MSG
operator|.
name|noteHasGroupReservation
argument_list|(
operator|(
operator|(
name|LearningCommunityReservation
operator|)
name|reservation
operator|)
operator|.
name|getGroup
argument_list|()
operator|.
name|getGroupName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
else|else
name|r
operator|.
name|addAdvisorNote
argument_list|(
name|MSG
operator|.
name|noteHasIndividualReservation
argument_list|()
argument_list|)
expr_stmt|;
name|reserved
operator|=
literal|true
expr_stmt|;
break|break;
block|}
block|}
block|}
if|if
condition|(
name|course
operator|.
name|getPreferences
argument_list|()
operator|!=
literal|null
condition|)
for|for
control|(
name|StudentSectioningPref
name|ssp
range|:
name|course
operator|.
name|getPreferences
argument_list|()
control|)
block|{
if|if
condition|(
name|ssp
operator|instanceof
name|StudentClassPref
condition|)
block|{
name|StudentClassPref
name|scp
init|=
operator|(
name|StudentClassPref
operator|)
name|ssp
decl_stmt|;
name|rc
operator|.
name|setSelectedClass
argument_list|(
name|scp
operator|.
name|getClazz
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|scp
operator|.
name|getClazz
argument_list|()
operator|.
name|getClassPrefLabel
argument_list|(
name|course
operator|.
name|getCourseOffering
argument_list|()
argument_list|)
argument_list|,
name|scp
operator|.
name|isRequired
argument_list|()
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|ssp
operator|instanceof
name|StudentInstrMthPref
condition|)
block|{
name|StudentInstrMthPref
name|imp
init|=
operator|(
name|StudentInstrMthPref
operator|)
name|ssp
decl_stmt|;
name|rc
operator|.
name|setSelectedIntructionalMethod
argument_list|(
name|imp
operator|.
name|getInstructionalMethod
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|imp
operator|.
name|getInstructionalMethod
argument_list|()
operator|.
name|getLabel
argument_list|()
argument_list|,
name|imp
operator|.
name|isRequired
argument_list|()
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
name|r
operator|.
name|addRequestedCourse
argument_list|(
name|rc
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|r
operator|.
name|hasRequestedCourse
argument_list|()
operator|&&
operator|(
name|enrolled
operator|||
name|reserved
operator|)
operator|&&
name|acrs
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
if|if
condition|(
name|cd
operator|.
name|isAlternative
argument_list|()
condition|)
name|request
operator|.
name|getAlternatives
argument_list|()
operator|.
name|add
argument_list|(
name|r
argument_list|)
expr_stmt|;
else|else
name|request
operator|.
name|getCourses
argument_list|()
operator|.
name|add
argument_list|(
name|r
argument_list|)
expr_stmt|;
block|}
name|r
operator|.
name|setWaitList
argument_list|(
name|cd
operator|.
name|getWaitlist
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|cd
operator|.
name|isCriticalOverride
argument_list|()
operator|!=
literal|null
condition|)
name|r
operator|.
name|setCritical
argument_list|(
name|cd
operator|.
name|isCriticalOverride
argument_list|()
argument_list|)
expr_stmt|;
else|else
name|r
operator|.
name|setCritical
argument_list|(
name|cd
operator|.
name|isCritical
argument_list|()
argument_list|)
expr_stmt|;
name|r
operator|.
name|setTimeStamp
argument_list|(
name|cd
operator|.
name|getTimestamp
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
name|fillCourseRequests
argument_list|(
name|request
argument_list|,
name|acrs
argument_list|)
expr_stmt|;
for|for
control|(
name|OnlineSectioningLog
operator|.
name|Request
name|log
range|:
name|OnlineSectioningHelper
operator|.
name|toProto
argument_list|(
name|request
argument_list|)
control|)
name|action
operator|.
name|addRequest
argument_list|(
name|log
argument_list|)
expr_stmt|;
return|return
name|request
return|;
block|}
specifier|protected
specifier|static
name|void
name|fillCourseRequests
parameter_list|(
name|CourseRequestInterface
name|request
parameter_list|,
name|List
argument_list|<
name|AdvisorCourseRequest
argument_list|>
name|acrs
parameter_list|)
block|{
name|int
name|last
init|=
operator|-
literal|1
decl_stmt|;
name|CourseRequestInterface
operator|.
name|Request
name|r
init|=
literal|null
decl_stmt|;
name|Set
argument_list|<
name|Integer
argument_list|>
name|skip
init|=
operator|new
name|HashSet
argument_list|<
name|Integer
argument_list|>
argument_list|()
decl_stmt|;
name|String
name|note
init|=
literal|null
decl_stmt|;
for|for
control|(
name|AdvisorCourseRequest
name|acr
range|:
name|acrs
control|)
block|{
if|if
condition|(
name|acr
operator|.
name|getAlternative
argument_list|()
operator|==
literal|0
operator|||
name|acr
operator|.
name|getNotes
argument_list|()
operator|!=
literal|null
condition|)
name|note
operator|=
name|acr
operator|.
name|getNotes
argument_list|()
expr_stmt|;
if|if
condition|(
name|acr
operator|.
name|getCourseOffering
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|RequestPriority
name|rp
init|=
name|request
operator|.
name|getRequestPriority
argument_list|(
operator|new
name|RequestedCourse
argument_list|(
name|acr
operator|.
name|getCourseOffering
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|acr
operator|.
name|getCourseOffering
argument_list|()
operator|.
name|getCourseName
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|rp
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|note
operator|!=
literal|null
condition|)
name|rp
operator|.
name|getRequest
argument_list|()
operator|.
name|addAdvisorNote
argument_list|(
name|note
argument_list|)
expr_stmt|;
name|skip
operator|.
name|add
argument_list|(
name|acr
operator|.
name|getPriority
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
for|for
control|(
name|AdvisorCourseRequest
name|acr
range|:
name|acrs
control|)
block|{
if|if
condition|(
name|skip
operator|.
name|contains
argument_list|(
name|acr
operator|.
name|getPriority
argument_list|()
argument_list|)
condition|)
continue|continue;
if|if
condition|(
name|acr
operator|.
name|getPriority
argument_list|()
operator|==
operator|-
literal|1
condition|)
block|{
name|request
operator|.
name|setCreditNote
argument_list|(
name|acr
operator|.
name|getNotes
argument_list|()
argument_list|)
expr_stmt|;
continue|continue;
block|}
if|if
condition|(
name|r
operator|==
literal|null
operator|||
name|last
operator|!=
name|acr
operator|.
name|getPriority
argument_list|()
condition|)
block|{
name|r
operator|=
operator|new
name|CourseRequestInterface
operator|.
name|Request
argument_list|()
expr_stmt|;
if|if
condition|(
name|acr
operator|.
name|isSubstitute
argument_list|()
condition|)
name|request
operator|.
name|getAlternatives
argument_list|()
operator|.
name|add
argument_list|(
name|r
argument_list|)
expr_stmt|;
else|else
name|request
operator|.
name|getCourses
argument_list|()
operator|.
name|add
argument_list|(
name|r
argument_list|)
expr_stmt|;
name|last
operator|=
name|acr
operator|.
name|getPriority
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|acr
operator|.
name|getCourseOffering
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|RequestedCourse
name|rc
init|=
operator|new
name|RequestedCourse
argument_list|()
decl_stmt|;
name|rc
operator|.
name|setCourseId
argument_list|(
name|acr
operator|.
name|getCourseOffering
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|rc
operator|.
name|setCourseName
argument_list|(
name|acr
operator|.
name|getCourseOffering
argument_list|()
operator|.
name|getSubjectAreaAbbv
argument_list|()
operator|+
literal|" "
operator|+
name|acr
operator|.
name|getCourseOffering
argument_list|()
operator|.
name|getCourseNbr
argument_list|()
operator|+
operator|(
operator|!
name|CONST
operator|.
name|showCourseTitle
argument_list|()
condition|?
literal|""
else|:
literal|" - "
operator|+
name|acr
operator|.
name|getCourseOffering
argument_list|()
operator|.
name|getTitle
argument_list|()
operator|)
argument_list|)
expr_stmt|;
name|rc
operator|.
name|setCourseTitle
argument_list|(
name|acr
operator|.
name|getCourseOffering
argument_list|()
operator|.
name|getTitle
argument_list|()
argument_list|)
expr_stmt|;
name|CourseCreditUnitConfig
name|credit
init|=
name|acr
operator|.
name|getCourseOffering
argument_list|()
operator|.
name|getCredit
argument_list|()
decl_stmt|;
if|if
condition|(
name|credit
operator|!=
literal|null
condition|)
name|rc
operator|.
name|setCredit
argument_list|(
name|credit
operator|.
name|getMinCredit
argument_list|()
argument_list|,
name|credit
operator|.
name|getMaxCredit
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|acr
operator|.
name|getPreferences
argument_list|()
operator|!=
literal|null
condition|)
for|for
control|(
name|AdvisorSectioningPref
name|ssp
range|:
name|acr
operator|.
name|getPreferences
argument_list|()
control|)
block|{
if|if
condition|(
name|ssp
operator|instanceof
name|AdvisorClassPref
condition|)
block|{
name|AdvisorClassPref
name|scp
init|=
operator|(
name|AdvisorClassPref
operator|)
name|ssp
decl_stmt|;
name|rc
operator|.
name|setSelectedClass
argument_list|(
name|scp
operator|.
name|getClazz
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|scp
operator|.
name|getClazz
argument_list|()
operator|.
name|getClassPrefLabel
argument_list|(
name|acr
operator|.
name|getCourseOffering
argument_list|()
argument_list|)
argument_list|,
name|scp
operator|.
name|isRequired
argument_list|()
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|ssp
operator|instanceof
name|AdvisorInstrMthPref
condition|)
block|{
name|AdvisorInstrMthPref
name|imp
init|=
operator|(
name|AdvisorInstrMthPref
operator|)
name|ssp
decl_stmt|;
name|rc
operator|.
name|setSelectedIntructionalMethod
argument_list|(
name|imp
operator|.
name|getInstructionalMethod
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|imp
operator|.
name|getInstructionalMethod
argument_list|()
operator|.
name|getLabel
argument_list|()
argument_list|,
name|imp
operator|.
name|isRequired
argument_list|()
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
name|r
operator|.
name|addRequestedCourse
argument_list|(
name|rc
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|acr
operator|.
name|getFreeTime
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|CourseRequestInterface
operator|.
name|FreeTime
name|ft
init|=
operator|new
name|CourseRequestInterface
operator|.
name|FreeTime
argument_list|()
decl_stmt|;
name|ft
operator|.
name|setStart
argument_list|(
name|acr
operator|.
name|getFreeTime
argument_list|()
operator|.
name|getStartSlot
argument_list|()
argument_list|)
expr_stmt|;
name|ft
operator|.
name|setLength
argument_list|(
name|acr
operator|.
name|getFreeTime
argument_list|()
operator|.
name|getLength
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|DayCode
name|day
range|:
name|DayCode
operator|.
name|toDayCodes
argument_list|(
name|acr
operator|.
name|getFreeTime
argument_list|()
operator|.
name|getDayCode
argument_list|()
argument_list|)
control|)
name|ft
operator|.
name|addDay
argument_list|(
name|day
operator|.
name|getIndex
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|r
operator|.
name|hasRequestedCourse
argument_list|()
condition|)
name|r
operator|.
name|addRequestedCourse
argument_list|(
operator|new
name|RequestedCourse
argument_list|()
argument_list|)
expr_stmt|;
name|r
operator|.
name|getRequestedCourse
argument_list|(
literal|0
argument_list|)
operator|.
name|addFreeTime
argument_list|(
name|ft
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|acr
operator|.
name|getCourse
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|RequestedCourse
name|rc
init|=
operator|new
name|RequestedCourse
argument_list|()
decl_stmt|;
name|rc
operator|.
name|setCourseName
argument_list|(
name|acr
operator|.
name|getCourse
argument_list|()
argument_list|)
expr_stmt|;
name|r
operator|.
name|addRequestedCourse
argument_list|(
name|rc
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|acr
operator|.
name|getCredit
argument_list|()
operator|!=
literal|null
condition|)
name|r
operator|.
name|setAdvisorCredit
argument_list|(
name|acr
operator|.
name|getCredit
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|acr
operator|.
name|getNotes
argument_list|()
operator|!=
literal|null
condition|)
name|r
operator|.
name|setAdvisorNote
argument_list|(
name|acr
operator|.
name|getNotes
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
specifier|static
name|CourseRequestInterface
name|getRequest
parameter_list|(
name|Long
name|studentId
parameter_list|,
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|)
block|{
name|CourseRequestInterface
name|request
init|=
operator|new
name|CourseRequestInterface
argument_list|()
decl_stmt|;
name|request
operator|.
name|setStudentId
argument_list|(
name|studentId
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|AdvisorCourseRequest
argument_list|>
name|acrs
init|=
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"from AdvisorCourseRequest where student = :studentId order by priority, alternative"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"studentId"
argument_list|,
name|studentId
argument_list|)
operator|.
name|list
argument_list|()
decl_stmt|;
name|fillCourseRequests
argument_list|(
name|request
argument_list|,
name|acrs
argument_list|)
expr_stmt|;
return|return
name|request
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|name
parameter_list|()
block|{
return|return
literal|"advisor-requests"
return|;
block|}
block|}
end_class

end_unit

