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
name|status
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
name|cpsolver
operator|.
name|ifs
operator|.
name|util
operator|.
name|DistanceMetric
import|;
end_import

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|studentsct
operator|.
name|online
operator|.
name|expectations
operator|.
name|OverExpectedCriterion
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
name|sectioning
operator|.
name|SectioningStatusFilterBox
operator|.
name|SectioningStatusFilterRpcRequest
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
name|server
operator|.
name|Query
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
name|CourseAssignment
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
name|model
operator|.
name|XAreaClassificationMajor
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
name|model
operator|.
name|XCourse
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
name|model
operator|.
name|XCourseRequest
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
name|model
operator|.
name|XEnrollment
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
name|model
operator|.
name|XEnrollments
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
name|model
operator|.
name|XExpectations
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
name|model
operator|.
name|XInstructor
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
name|model
operator|.
name|XOffering
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
name|model
operator|.
name|XRequest
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
name|model
operator|.
name|XRoom
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
name|model
operator|.
name|XSection
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
name|model
operator|.
name|XStudent
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
name|model
operator|.
name|XStudentId
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
name|model
operator|.
name|XSubpart
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|FindEnrollmentAction
implements|implements
name|OnlineSectioningAction
argument_list|<
name|List
argument_list|<
name|ClassAssignmentInterface
operator|.
name|Enrollment
argument_list|>
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
specifier|protected
name|Query
name|iQuery
decl_stmt|;
specifier|protected
name|Long
name|iCourseId
decl_stmt|,
name|iClassId
decl_stmt|;
specifier|protected
name|boolean
name|iConsentToDoCourse
decl_stmt|;
specifier|protected
name|boolean
name|iCanShowExtIds
init|=
literal|false
decl_stmt|,
name|iCanRegister
init|=
literal|false
decl_stmt|,
name|iCanUseAssistant
init|=
literal|false
decl_stmt|;
specifier|protected
name|Set
argument_list|<
name|Long
argument_list|>
name|iMyStudents
decl_stmt|;
specifier|public
name|FindEnrollmentAction
name|withParams
parameter_list|(
name|String
name|query
parameter_list|,
name|Long
name|courseId
parameter_list|,
name|Long
name|classId
parameter_list|,
name|boolean
name|isConsentToDoCourse
parameter_list|,
name|boolean
name|canShowExtIds
parameter_list|,
name|boolean
name|canRegister
parameter_list|,
name|boolean
name|canUseAssistant
parameter_list|,
name|Set
argument_list|<
name|Long
argument_list|>
name|myStudents
parameter_list|)
block|{
name|iQuery
operator|=
operator|new
name|Query
argument_list|(
name|query
argument_list|)
expr_stmt|;
name|iCourseId
operator|=
name|courseId
expr_stmt|;
name|iClassId
operator|=
name|classId
expr_stmt|;
name|iConsentToDoCourse
operator|=
name|isConsentToDoCourse
expr_stmt|;
name|iCanShowExtIds
operator|=
name|canShowExtIds
expr_stmt|;
name|iCanRegister
operator|=
name|canRegister
expr_stmt|;
name|iCanUseAssistant
operator|=
name|canUseAssistant
expr_stmt|;
name|iMyStudents
operator|=
name|myStudents
expr_stmt|;
return|return
name|this
return|;
block|}
specifier|protected
name|SectioningStatusFilterRpcRequest
name|iFilter
init|=
literal|null
decl_stmt|;
specifier|public
name|FindEnrollmentAction
name|withFilter
parameter_list|(
name|SectioningStatusFilterRpcRequest
name|filter
parameter_list|)
block|{
name|iFilter
operator|=
name|filter
expr_stmt|;
return|return
name|this
return|;
block|}
specifier|public
name|Query
name|query
parameter_list|()
block|{
return|return
name|iQuery
return|;
block|}
specifier|public
name|Long
name|courseId
parameter_list|()
block|{
return|return
name|iCourseId
return|;
block|}
specifier|public
name|Long
name|classId
parameter_list|()
block|{
return|return
name|iClassId
return|;
block|}
specifier|public
name|boolean
name|isConsentToDoCourse
parameter_list|()
block|{
return|return
name|iConsentToDoCourse
return|;
block|}
specifier|public
name|boolean
name|isMyStudent
parameter_list|(
name|XStudentId
name|student
parameter_list|)
block|{
return|return
name|iMyStudents
operator|!=
literal|null
operator|&&
name|iMyStudents
operator|.
name|contains
argument_list|(
name|student
operator|.
name|getStudentId
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|List
argument_list|<
name|ClassAssignmentInterface
operator|.
name|Enrollment
argument_list|>
name|execute
parameter_list|(
name|OnlineSectioningServer
name|server
parameter_list|,
name|OnlineSectioningHelper
name|helper
parameter_list|)
block|{
name|List
argument_list|<
name|ClassAssignmentInterface
operator|.
name|Enrollment
argument_list|>
name|ret
init|=
operator|new
name|ArrayList
argument_list|<
name|ClassAssignmentInterface
operator|.
name|Enrollment
argument_list|>
argument_list|()
decl_stmt|;
name|XCourse
name|course
init|=
name|server
operator|.
name|getCourse
argument_list|(
name|courseId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|course
operator|==
literal|null
condition|)
return|return
name|ret
return|;
name|XOffering
name|offering
init|=
name|server
operator|.
name|getOffering
argument_list|(
name|course
operator|.
name|getOfferingId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|offering
operator|==
literal|null
condition|)
return|return
name|ret
return|;
name|XEnrollments
name|enrollments
init|=
name|server
operator|.
name|getEnrollments
argument_list|(
name|course
operator|.
name|getOfferingId
argument_list|()
argument_list|)
decl_stmt|;
name|DistanceMetric
name|m
init|=
name|server
operator|.
name|getDistanceMetric
argument_list|()
decl_stmt|;
name|XExpectations
name|expectations
init|=
name|server
operator|.
name|getExpectations
argument_list|(
name|offering
operator|.
name|getOfferingId
argument_list|()
argument_list|)
decl_stmt|;
name|OverExpectedCriterion
name|overExp
init|=
name|server
operator|.
name|getOverExpectedCriterion
argument_list|()
decl_stmt|;
name|AcademicSessionInfo
name|session
init|=
name|server
operator|.
name|getAcademicSession
argument_list|()
decl_stmt|;
name|Set
argument_list|<
name|Long
argument_list|>
name|studentIds
init|=
operator|(
name|iFilter
operator|==
literal|null
condition|?
literal|null
else|:
name|server
operator|.
name|createAction
argument_list|(
name|SectioningStatusFilterAction
operator|.
name|class
argument_list|)
operator|.
name|forRequest
argument_list|(
name|iFilter
argument_list|)
operator|.
name|getStudentIds
argument_list|(
name|server
argument_list|,
name|helper
argument_list|)
operator|)
decl_stmt|;
for|for
control|(
name|XCourseRequest
name|request
range|:
name|enrollments
operator|.
name|getRequests
argument_list|()
control|)
block|{
if|if
condition|(
name|request
operator|.
name|getEnrollment
argument_list|()
operator|!=
literal|null
operator|&&
operator|!
name|request
operator|.
name|getEnrollment
argument_list|()
operator|.
name|getCourseId
argument_list|()
operator|.
name|equals
argument_list|(
name|courseId
argument_list|()
argument_list|)
condition|)
continue|continue;
if|if
condition|(
name|classId
argument_list|()
operator|!=
literal|null
operator|&&
name|request
operator|.
name|getEnrollment
argument_list|()
operator|!=
literal|null
operator|&&
operator|!
name|request
operator|.
name|getEnrollment
argument_list|()
operator|.
name|getSectionIds
argument_list|()
operator|.
name|contains
argument_list|(
name|classId
argument_list|()
argument_list|)
condition|)
continue|continue;
if|if
condition|(
name|request
operator|.
name|getEnrollment
argument_list|()
operator|==
literal|null
operator|&&
operator|!
name|request
operator|.
name|getCourseIds
argument_list|()
operator|.
name|contains
argument_list|(
name|course
argument_list|)
condition|)
continue|continue;
if|if
condition|(
name|studentIds
operator|!=
literal|null
operator|&&
operator|!
name|studentIds
operator|.
name|contains
argument_list|(
name|request
operator|.
name|getStudentId
argument_list|()
argument_list|)
condition|)
continue|continue;
name|XStudent
name|student
init|=
name|server
operator|.
name|getStudent
argument_list|(
name|request
operator|.
name|getStudentId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|student
operator|==
literal|null
condition|)
continue|continue;
if|if
condition|(
name|request
operator|.
name|getEnrollment
argument_list|()
operator|==
literal|null
operator|&&
operator|!
name|student
operator|.
name|canAssign
argument_list|(
name|request
argument_list|)
condition|)
continue|continue;
if|if
condition|(
operator|!
name|query
argument_list|()
operator|.
name|match
argument_list|(
operator|new
name|StatusPageSuggestionsAction
operator|.
name|CourseRequestMatcher
argument_list|(
name|session
argument_list|,
name|course
argument_list|,
name|student
argument_list|,
name|offering
argument_list|,
name|request
argument_list|,
name|isConsentToDoCourse
argument_list|()
argument_list|,
name|isMyStudent
argument_list|(
name|student
argument_list|)
argument_list|,
name|server
argument_list|)
argument_list|)
condition|)
continue|continue;
name|ClassAssignmentInterface
operator|.
name|Student
name|st
init|=
operator|new
name|ClassAssignmentInterface
operator|.
name|Student
argument_list|()
decl_stmt|;
name|st
operator|.
name|setId
argument_list|(
name|student
operator|.
name|getStudentId
argument_list|()
argument_list|)
expr_stmt|;
name|st
operator|.
name|setSessionId
argument_list|(
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|st
operator|.
name|setExternalId
argument_list|(
name|student
operator|.
name|getExternalId
argument_list|()
argument_list|)
expr_stmt|;
name|st
operator|.
name|setCanShowExternalId
argument_list|(
name|iCanShowExtIds
argument_list|)
expr_stmt|;
name|st
operator|.
name|setCanRegister
argument_list|(
name|iCanRegister
argument_list|)
expr_stmt|;
name|st
operator|.
name|setCanUseAssistant
argument_list|(
name|iCanUseAssistant
argument_list|)
expr_stmt|;
name|st
operator|.
name|setName
argument_list|(
name|student
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|XAreaClassificationMajor
name|acm
range|:
name|student
operator|.
name|getMajors
argument_list|()
control|)
block|{
name|st
operator|.
name|addArea
argument_list|(
name|acm
operator|.
name|getArea
argument_list|()
argument_list|)
expr_stmt|;
name|st
operator|.
name|addClassification
argument_list|(
name|acm
operator|.
name|getClassification
argument_list|()
argument_list|)
expr_stmt|;
name|st
operator|.
name|addMajor
argument_list|(
name|acm
operator|.
name|getMajor
argument_list|()
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|String
name|gr
range|:
name|student
operator|.
name|getGroups
argument_list|()
control|)
block|{
name|st
operator|.
name|addGroup
argument_list|(
name|gr
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|String
name|acc
range|:
name|student
operator|.
name|getAccomodations
argument_list|()
control|)
block|{
name|st
operator|.
name|addAccommodation
argument_list|(
name|acc
argument_list|)
expr_stmt|;
block|}
name|ClassAssignmentInterface
operator|.
name|Enrollment
name|e
init|=
operator|new
name|ClassAssignmentInterface
operator|.
name|Enrollment
argument_list|()
decl_stmt|;
name|e
operator|.
name|setStudent
argument_list|(
name|st
argument_list|)
expr_stmt|;
name|e
operator|.
name|setPriority
argument_list|(
literal|1
operator|+
name|request
operator|.
name|getPriority
argument_list|()
argument_list|)
expr_stmt|;
name|CourseAssignment
name|c
init|=
operator|new
name|CourseAssignment
argument_list|()
decl_stmt|;
name|c
operator|.
name|setCourseId
argument_list|(
name|course
operator|.
name|getCourseId
argument_list|()
argument_list|)
expr_stmt|;
name|c
operator|.
name|setSubject
argument_list|(
name|course
operator|.
name|getSubjectArea
argument_list|()
argument_list|)
expr_stmt|;
name|c
operator|.
name|setCourseNbr
argument_list|(
name|course
operator|.
name|getCourseNumber
argument_list|()
argument_list|)
expr_stmt|;
name|c
operator|.
name|setTitle
argument_list|(
name|course
operator|.
name|getTitle
argument_list|()
argument_list|)
expr_stmt|;
name|c
operator|.
name|setHasCrossList
argument_list|(
name|offering
operator|.
name|hasCrossList
argument_list|()
argument_list|)
expr_stmt|;
name|e
operator|.
name|setCourse
argument_list|(
name|c
argument_list|)
expr_stmt|;
name|e
operator|.
name|setWaitList
argument_list|(
name|request
operator|.
name|isWaitlist
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|request
operator|.
name|getEnrollment
argument_list|()
operator|==
literal|null
condition|)
name|e
operator|.
name|setEnrollmentMessage
argument_list|(
name|request
operator|.
name|getEnrollmentMessage
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|request
operator|.
name|getCourseIds
argument_list|()
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|equals
argument_list|(
name|course
argument_list|)
condition|)
name|e
operator|.
name|setAlternative
argument_list|(
name|request
operator|.
name|getCourseIds
argument_list|()
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|getCourseName
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|request
operator|.
name|isAlternative
argument_list|()
condition|)
block|{
for|for
control|(
name|XRequest
name|r
range|:
name|student
operator|.
name|getRequests
argument_list|()
control|)
block|{
if|if
condition|(
name|r
operator|instanceof
name|XCourseRequest
operator|&&
operator|!
name|r
operator|.
name|isAlternative
argument_list|()
operator|&&
operator|(
operator|(
name|XCourseRequest
operator|)
name|r
operator|)
operator|.
name|getEnrollment
argument_list|()
operator|==
literal|null
condition|)
block|{
name|e
operator|.
name|setAlternative
argument_list|(
operator|(
operator|(
name|XCourseRequest
operator|)
name|r
operator|)
operator|.
name|getCourseIds
argument_list|()
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|getCourseName
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
name|request
operator|.
name|getTimeStamp
argument_list|()
operator|!=
literal|null
condition|)
name|e
operator|.
name|setRequestedDate
argument_list|(
name|request
operator|.
name|getTimeStamp
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|request
operator|.
name|getEnrollment
argument_list|()
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|request
operator|.
name|getEnrollment
argument_list|()
operator|.
name|getReservation
argument_list|()
operator|!=
literal|null
condition|)
block|{
switch|switch
condition|(
name|request
operator|.
name|getEnrollment
argument_list|()
operator|.
name|getReservation
argument_list|()
operator|.
name|getType
argument_list|()
condition|)
block|{
case|case
name|Individual
case|:
name|e
operator|.
name|setReservation
argument_list|(
name|MSG
operator|.
name|reservationIndividual
argument_list|()
argument_list|)
expr_stmt|;
break|break;
case|case
name|Group
case|:
name|e
operator|.
name|setReservation
argument_list|(
name|MSG
operator|.
name|reservationGroup
argument_list|()
argument_list|)
expr_stmt|;
break|break;
case|case
name|Course
case|:
name|e
operator|.
name|setReservation
argument_list|(
name|MSG
operator|.
name|reservationCourse
argument_list|()
argument_list|)
expr_stmt|;
break|break;
case|case
name|Curriculum
case|:
name|e
operator|.
name|setReservation
argument_list|(
name|MSG
operator|.
name|reservationCurriculum
argument_list|()
argument_list|)
expr_stmt|;
break|break;
block|}
block|}
if|if
condition|(
name|request
operator|.
name|getEnrollment
argument_list|()
operator|.
name|getTimeStamp
argument_list|()
operator|!=
literal|null
condition|)
name|e
operator|.
name|setEnrolledDate
argument_list|(
name|request
operator|.
name|getEnrollment
argument_list|()
operator|.
name|getTimeStamp
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|request
operator|.
name|getEnrollment
argument_list|()
operator|.
name|getApproval
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|e
operator|.
name|setApprovedDate
argument_list|(
name|request
operator|.
name|getEnrollment
argument_list|()
operator|.
name|getApproval
argument_list|()
operator|.
name|getTimeStamp
argument_list|()
argument_list|)
expr_stmt|;
name|e
operator|.
name|setApprovedBy
argument_list|(
name|request
operator|.
name|getEnrollment
argument_list|()
operator|.
name|getApproval
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|XSection
name|section
range|:
name|offering
operator|.
name|getSections
argument_list|(
name|request
operator|.
name|getEnrollment
argument_list|()
argument_list|)
control|)
block|{
name|ClassAssignmentInterface
operator|.
name|ClassAssignment
name|a
init|=
name|e
operator|.
name|getCourse
argument_list|()
operator|.
name|addClassAssignment
argument_list|()
decl_stmt|;
name|a
operator|.
name|setAlternative
argument_list|(
name|request
operator|.
name|isAlternative
argument_list|()
argument_list|)
expr_stmt|;
name|a
operator|.
name|setClassId
argument_list|(
name|section
operator|.
name|getSectionId
argument_list|()
argument_list|)
expr_stmt|;
name|a
operator|.
name|setSubpart
argument_list|(
name|section
operator|.
name|getSubpartName
argument_list|()
argument_list|)
expr_stmt|;
name|a
operator|.
name|setSection
argument_list|(
name|section
operator|.
name|getName
argument_list|(
name|course
operator|.
name|getCourseId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|a
operator|.
name|setClassNumber
argument_list|(
name|section
operator|.
name|getName
argument_list|(
operator|-
literal|1l
argument_list|)
argument_list|)
expr_stmt|;
name|a
operator|.
name|setCancelled
argument_list|(
name|section
operator|.
name|isCancelled
argument_list|()
argument_list|)
expr_stmt|;
name|a
operator|.
name|setLimit
argument_list|(
operator|new
name|int
index|[]
block|{
name|enrollments
operator|.
name|countEnrollmentsForSection
argument_list|(
name|section
operator|.
name|getSectionId
argument_list|()
argument_list|)
block|,
name|section
operator|.
name|getLimit
argument_list|()
block|}
argument_list|)
expr_stmt|;
if|if
condition|(
name|section
operator|.
name|getTime
argument_list|()
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|DayCode
name|d
range|:
name|DayCode
operator|.
name|toDayCodes
argument_list|(
name|section
operator|.
name|getTime
argument_list|()
operator|.
name|getDays
argument_list|()
argument_list|)
control|)
name|a
operator|.
name|addDay
argument_list|(
name|d
operator|.
name|getIndex
argument_list|()
argument_list|)
expr_stmt|;
name|a
operator|.
name|setStart
argument_list|(
name|section
operator|.
name|getTime
argument_list|()
operator|.
name|getSlot
argument_list|()
argument_list|)
expr_stmt|;
name|a
operator|.
name|setLength
argument_list|(
name|section
operator|.
name|getTime
argument_list|()
operator|.
name|getLength
argument_list|()
argument_list|)
expr_stmt|;
name|a
operator|.
name|setBreakTime
argument_list|(
name|section
operator|.
name|getTime
argument_list|()
operator|.
name|getBreakTime
argument_list|()
argument_list|)
expr_stmt|;
name|a
operator|.
name|setDatePattern
argument_list|(
name|section
operator|.
name|getTime
argument_list|()
operator|.
name|getDatePatternName
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|section
operator|.
name|getNrRooms
argument_list|()
operator|>
literal|0
condition|)
block|{
for|for
control|(
name|XRoom
name|rm
range|:
name|section
operator|.
name|getRooms
argument_list|()
control|)
block|{
name|a
operator|.
name|addRoom
argument_list|(
name|rm
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|rm
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|section
operator|.
name|getInstructors
argument_list|()
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|XInstructor
name|instructor
range|:
name|section
operator|.
name|getInstructors
argument_list|()
control|)
block|{
name|a
operator|.
name|addInstructor
argument_list|(
name|instructor
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|a
operator|.
name|addInstructoEmail
argument_list|(
name|instructor
operator|.
name|getEmail
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|section
operator|.
name|getParentId
argument_list|()
operator|!=
literal|null
condition|)
name|a
operator|.
name|setParentSection
argument_list|(
name|offering
operator|.
name|getSection
argument_list|(
name|section
operator|.
name|getParentId
argument_list|()
argument_list|)
operator|.
name|getName
argument_list|(
name|course
operator|.
name|getCourseId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|a
operator|.
name|setSubpartId
argument_list|(
name|section
operator|.
name|getSubpartId
argument_list|()
argument_list|)
expr_stmt|;
name|a
operator|.
name|addNote
argument_list|(
name|course
operator|.
name|getNote
argument_list|()
argument_list|)
expr_stmt|;
name|a
operator|.
name|addNote
argument_list|(
name|section
operator|.
name|getNote
argument_list|()
argument_list|)
expr_stmt|;
name|XSubpart
name|subpart
init|=
name|offering
operator|.
name|getSubpart
argument_list|(
name|section
operator|.
name|getSubpartId
argument_list|()
argument_list|)
decl_stmt|;
name|a
operator|.
name|setCredit
argument_list|(
name|subpart
operator|.
name|getCredit
argument_list|(
name|course
operator|.
name|getCourseId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|int
name|dist
init|=
literal|0
decl_stmt|;
name|String
name|from
init|=
literal|null
decl_stmt|;
name|TreeSet
argument_list|<
name|String
argument_list|>
name|overlap
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
name|XRequest
name|q
range|:
name|student
operator|.
name|getRequests
argument_list|()
control|)
block|{
if|if
condition|(
name|q
operator|instanceof
name|XCourseRequest
condition|)
block|{
name|XEnrollment
name|otherEnrollment
init|=
operator|(
operator|(
name|XCourseRequest
operator|)
name|q
operator|)
operator|.
name|getEnrollment
argument_list|()
decl_stmt|;
if|if
condition|(
name|otherEnrollment
operator|==
literal|null
condition|)
continue|continue;
name|XOffering
name|otherOffering
init|=
name|server
operator|.
name|getOffering
argument_list|(
name|otherEnrollment
operator|.
name|getOfferingId
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|XSection
name|otherSection
range|:
name|otherOffering
operator|.
name|getSections
argument_list|(
name|otherEnrollment
argument_list|)
control|)
block|{
if|if
condition|(
name|otherSection
operator|.
name|equals
argument_list|(
name|section
argument_list|)
operator|||
name|otherSection
operator|.
name|getTime
argument_list|()
operator|==
literal|null
condition|)
continue|continue;
name|int
name|d
init|=
name|otherSection
operator|.
name|getDistanceInMinutes
argument_list|(
name|section
argument_list|,
name|m
argument_list|)
decl_stmt|;
if|if
condition|(
name|d
operator|>
name|dist
condition|)
block|{
name|dist
operator|=
name|d
expr_stmt|;
name|from
operator|=
literal|""
expr_stmt|;
for|for
control|(
name|Iterator
argument_list|<
name|XRoom
argument_list|>
name|k
init|=
name|otherSection
operator|.
name|getRooms
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|k
operator|.
name|hasNext
argument_list|()
condition|;
control|)
name|from
operator|+=
name|k
operator|.
name|next
argument_list|()
operator|.
name|getName
argument_list|()
operator|+
operator|(
name|k
operator|.
name|hasNext
argument_list|()
condition|?
literal|", "
else|:
literal|""
operator|)
expr_stmt|;
block|}
if|if
condition|(
name|otherSection
operator|.
name|isDistanceConflict
argument_list|(
name|student
argument_list|,
name|section
argument_list|,
name|m
argument_list|)
condition|)
name|a
operator|.
name|setDistanceConflict
argument_list|(
literal|true
argument_list|)
expr_stmt|;
if|if
condition|(
name|section
operator|.
name|getTime
argument_list|()
operator|!=
literal|null
operator|&&
name|section
operator|.
name|getTime
argument_list|()
operator|.
name|hasIntersection
argument_list|(
name|otherSection
operator|.
name|getTime
argument_list|()
argument_list|)
operator|&&
operator|!
name|section
operator|.
name|isToIgnoreStudentConflictsWith
argument_list|(
name|offering
operator|.
name|getDistributions
argument_list|()
argument_list|,
name|otherSection
operator|.
name|getSectionId
argument_list|()
argument_list|)
condition|)
block|{
name|XCourse
name|otherCourse
init|=
name|otherOffering
operator|.
name|getCourse
argument_list|(
name|otherEnrollment
operator|.
name|getCourseId
argument_list|()
argument_list|)
decl_stmt|;
name|XSubpart
name|otherSubpart
init|=
name|otherOffering
operator|.
name|getSubpart
argument_list|(
name|otherSection
operator|.
name|getSubpartId
argument_list|()
argument_list|)
decl_stmt|;
name|overlap
operator|.
name|add
argument_list|(
name|MSG
operator|.
name|clazz
argument_list|(
name|otherCourse
operator|.
name|getSubjectArea
argument_list|()
argument_list|,
name|otherCourse
operator|.
name|getCourseNumber
argument_list|()
argument_list|,
name|otherSubpart
operator|.
name|getName
argument_list|()
argument_list|,
name|otherSection
operator|.
name|getName
argument_list|(
name|otherCourse
operator|.
name|getCourseId
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
if|if
condition|(
operator|!
name|overlap
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|String
name|note
init|=
literal|null
decl_stmt|;
for|for
control|(
name|Iterator
argument_list|<
name|String
argument_list|>
name|j
init|=
name|overlap
operator|.
name|iterator
argument_list|()
init|;
name|j
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|String
name|n
init|=
name|j
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|note
operator|==
literal|null
condition|)
name|note
operator|=
name|MSG
operator|.
name|noteAllowedOverlapFirst
argument_list|(
name|n
argument_list|)
expr_stmt|;
if|else if
condition|(
name|j
operator|.
name|hasNext
argument_list|()
condition|)
name|note
operator|+=
name|MSG
operator|.
name|noteAllowedOverlapMiddle
argument_list|(
name|n
argument_list|)
expr_stmt|;
else|else
name|note
operator|+=
name|MSG
operator|.
name|noteAllowedOverlapLast
argument_list|(
name|n
argument_list|)
expr_stmt|;
block|}
name|a
operator|.
name|setOverlapNote
argument_list|(
name|note
argument_list|)
expr_stmt|;
block|}
name|a
operator|.
name|setBackToBackDistance
argument_list|(
name|dist
argument_list|)
expr_stmt|;
name|a
operator|.
name|setBackToBackRooms
argument_list|(
name|from
argument_list|)
expr_stmt|;
name|a
operator|.
name|setSaved
argument_list|(
literal|true
argument_list|)
expr_stmt|;
if|if
condition|(
name|a
operator|.
name|getParentSection
argument_list|()
operator|==
literal|null
condition|)
name|a
operator|.
name|setParentSection
argument_list|(
name|course
operator|.
name|getConsentLabel
argument_list|()
argument_list|)
expr_stmt|;
name|a
operator|.
name|setExpected
argument_list|(
name|overExp
operator|.
name|getExpected
argument_list|(
name|section
operator|.
name|getLimit
argument_list|()
argument_list|,
name|expectations
operator|.
name|getExpectedSpace
argument_list|(
name|section
operator|.
name|getSectionId
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
name|ret
operator|.
name|add
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
return|return
name|ret
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
literal|"find-enrollments"
return|;
block|}
block|}
end_class

end_unit

