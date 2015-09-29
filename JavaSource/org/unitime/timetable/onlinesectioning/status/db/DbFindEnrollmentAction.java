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
operator|.
name|db
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
name|List
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
name|model
operator|.
name|AcademicAreaClassification
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
name|Assignment
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
name|ClassInstructor
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
name|CourseOffering
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
name|CourseReservation
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
name|CurriculumReservation
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
name|InstructionalOffering
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
name|Location
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
name|PosMajor
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
name|SchedulingSubpart
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
name|StudentAccomodation
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
name|StudentEnrollmentMessage
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
name|StudentGroup
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
name|dao
operator|.
name|CourseOfferingDAO
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
name|status
operator|.
name|FindEnrollmentAction
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
name|status
operator|.
name|db
operator|.
name|DbFindEnrollmentInfoAction
operator|.
name|DbCourseRequestMatcher
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|DbFindEnrollmentAction
extends|extends
name|FindEnrollmentAction
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
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
if|if
condition|(
name|iFilter
operator|==
literal|null
condition|)
return|return
name|super
operator|.
name|execute
argument_list|(
name|server
argument_list|,
name|helper
argument_list|)
return|;
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
name|AcademicSessionInfo
name|session
init|=
name|server
operator|.
name|getAcademicSession
argument_list|()
decl_stmt|;
name|OverExpectedCriterion
name|overExp
init|=
name|server
operator|.
name|getOverExpectedCriterion
argument_list|()
decl_stmt|;
name|CourseOffering
name|course
init|=
name|CourseOfferingDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|courseId
argument_list|()
argument_list|,
name|helper
operator|.
name|getHibSession
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
name|InstructionalOffering
name|offering
init|=
name|course
operator|.
name|getInstructionalOffering
argument_list|()
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
for|for
control|(
name|CourseRequest
name|request
range|:
operator|(
name|List
argument_list|<
name|CourseRequest
argument_list|>
operator|)
name|helper
operator|.
name|getHibSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"from CourseRequest where courseOffering.uniqueId = :courseId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"courseId"
argument_list|,
name|course
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
control|)
block|{
name|DbCourseRequestMatcher
name|crm
init|=
operator|new
name|DbCourseRequestMatcher
argument_list|(
name|session
argument_list|,
name|request
argument_list|,
name|isConsentToDoCourse
argument_list|()
argument_list|,
name|helper
operator|.
name|getStudentNameFormat
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|classId
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|boolean
name|match
init|=
literal|false
decl_stmt|;
for|for
control|(
name|StudentClassEnrollment
name|e
range|:
name|crm
operator|.
name|enrollment
argument_list|()
control|)
block|{
if|if
condition|(
name|e
operator|.
name|getClazz
argument_list|()
operator|.
name|getUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
name|classId
argument_list|()
argument_list|)
condition|)
block|{
name|match
operator|=
literal|true
expr_stmt|;
break|break;
block|}
block|}
if|if
condition|(
operator|!
name|match
condition|)
continue|continue;
block|}
if|if
condition|(
operator|!
name|query
argument_list|()
operator|.
name|match
argument_list|(
name|crm
argument_list|)
condition|)
continue|continue;
if|if
condition|(
name|crm
operator|.
name|enrollment
argument_list|()
operator|.
name|isEmpty
argument_list|()
operator|&&
operator|!
name|crm
operator|.
name|canAssign
argument_list|()
condition|)
continue|continue;
name|Student
name|student
init|=
name|request
operator|.
name|getCourseDemand
argument_list|()
operator|.
name|getStudent
argument_list|()
decl_stmt|;
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
name|getUniqueId
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
name|getExternalUniqueId
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
name|helper
operator|.
name|getStudentNameFormat
argument_list|()
operator|.
name|format
argument_list|(
name|student
argument_list|)
argument_list|)
expr_stmt|;
for|for
control|(
name|AcademicAreaClassification
name|ac
range|:
name|student
operator|.
name|getAcademicAreaClassifications
argument_list|()
control|)
block|{
name|st
operator|.
name|addArea
argument_list|(
name|ac
operator|.
name|getAcademicArea
argument_list|()
operator|.
name|getAcademicAreaAbbreviation
argument_list|()
argument_list|)
expr_stmt|;
name|st
operator|.
name|addClassification
argument_list|(
name|ac
operator|.
name|getAcademicClassification
argument_list|()
operator|.
name|getCode
argument_list|()
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|PosMajor
name|mj
range|:
name|student
operator|.
name|getPosMajors
argument_list|()
control|)
block|{
name|st
operator|.
name|addMajor
argument_list|(
name|mj
operator|.
name|getCode
argument_list|()
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|StudentAccomodation
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
operator|.
name|getAbbreviation
argument_list|()
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|StudentGroup
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
operator|.
name|getGroupAbbreviation
argument_list|()
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
name|getCourseDemand
argument_list|()
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
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|c
operator|.
name|setSubject
argument_list|(
name|course
operator|.
name|getSubjectAreaAbbv
argument_list|()
argument_list|)
expr_stmt|;
name|c
operator|.
name|setCourseNbr
argument_list|(
name|course
operator|.
name|getCourseNbr
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
name|getCourseDemand
argument_list|()
operator|.
name|isWaitlist
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|crm
operator|.
name|enrollment
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
if|if
condition|(
name|request
operator|.
name|getCourseDemand
argument_list|()
operator|.
name|getEnrollmentMessages
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|StudentEnrollmentMessage
name|message
init|=
literal|null
decl_stmt|;
for|for
control|(
name|StudentEnrollmentMessage
name|m
range|:
name|request
operator|.
name|getCourseDemand
argument_list|()
operator|.
name|getEnrollmentMessages
argument_list|()
control|)
block|{
if|if
condition|(
name|message
operator|==
literal|null
operator|||
name|message
operator|.
name|getOrder
argument_list|()
operator|<
name|m
operator|.
name|getOrder
argument_list|()
operator|||
operator|(
name|message
operator|.
name|getOrder
argument_list|()
operator|==
name|m
operator|.
name|getOrder
argument_list|()
operator|&&
name|message
operator|.
name|getTimestamp
argument_list|()
operator|.
name|before
argument_list|(
name|m
operator|.
name|getTimestamp
argument_list|()
argument_list|)
operator|)
condition|)
block|{
name|message
operator|=
name|m
expr_stmt|;
block|}
block|}
if|if
condition|(
name|message
operator|!=
literal|null
condition|)
name|e
operator|.
name|setEnrollmentMessage
argument_list|(
name|message
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
name|CourseRequest
name|alt
init|=
literal|null
decl_stmt|;
for|for
control|(
name|CourseRequest
name|r
range|:
name|request
operator|.
name|getCourseDemand
argument_list|()
operator|.
name|getCourseRequests
argument_list|()
control|)
block|{
if|if
condition|(
name|alt
operator|==
literal|null
operator|||
name|alt
operator|.
name|getOrder
argument_list|()
operator|<
name|r
operator|.
name|getOrder
argument_list|()
condition|)
name|alt
operator|=
name|r
expr_stmt|;
block|}
if|if
condition|(
name|alt
operator|!=
literal|null
operator|&&
name|alt
operator|.
name|getOrder
argument_list|()
operator|<
name|request
operator|.
name|getOrder
argument_list|()
condition|)
name|e
operator|.
name|setAlternative
argument_list|(
name|alt
operator|.
name|getCourseOffering
argument_list|()
operator|.
name|getCourseName
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|request
operator|.
name|getCourseDemand
argument_list|()
operator|.
name|isAlternative
argument_list|()
condition|)
block|{
name|alt
operator|=
literal|null
expr_stmt|;
name|demands
label|:
for|for
control|(
name|CourseDemand
name|demand
range|:
name|student
operator|.
name|getCourseDemands
argument_list|()
control|)
block|{
if|if
condition|(
operator|!
name|demand
operator|.
name|getCourseRequests
argument_list|()
operator|.
name|isEmpty
argument_list|()
operator|&&
operator|!
name|demand
operator|.
name|isAlternative
argument_list|()
operator|&&
operator|!
name|demand
operator|.
name|isWaitlist
argument_list|()
condition|)
block|{
for|for
control|(
name|CourseRequest
name|r
range|:
name|demand
operator|.
name|getCourseRequests
argument_list|()
control|)
block|{
if|if
condition|(
operator|!
name|r
operator|.
name|getClassEnrollments
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
continue|continue
name|demands
continue|;
block|}
for|for
control|(
name|CourseRequest
name|r
range|:
name|demand
operator|.
name|getCourseRequests
argument_list|()
control|)
block|{
if|if
condition|(
name|alt
operator|==
literal|null
operator|||
name|demand
operator|.
name|getPriority
argument_list|()
operator|<
name|alt
operator|.
name|getCourseDemand
argument_list|()
operator|.
name|getPriority
argument_list|()
operator|||
operator|(
name|demand
operator|.
name|getPriority
argument_list|()
operator|==
name|alt
operator|.
name|getCourseDemand
argument_list|()
operator|.
name|getPriority
argument_list|()
operator|&&
name|r
operator|.
name|getOrder
argument_list|()
operator|<
name|alt
operator|.
name|getOrder
argument_list|()
operator|)
condition|)
name|alt
operator|=
name|r
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
name|alt
operator|!=
literal|null
condition|)
name|e
operator|.
name|setAlternative
argument_list|(
name|alt
operator|.
name|getCourseOffering
argument_list|()
operator|.
name|getCourseName
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|request
operator|.
name|getCourseDemand
argument_list|()
operator|.
name|getTimestamp
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
name|getCourseDemand
argument_list|()
operator|.
name|getTimestamp
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|crm
operator|.
name|enrollment
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
if|if
condition|(
name|crm
operator|.
name|reservation
argument_list|()
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|crm
operator|.
name|reservation
argument_list|()
operator|instanceof
name|IndividualReservation
condition|)
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
if|else if
condition|(
name|crm
operator|.
name|reservation
argument_list|()
operator|instanceof
name|StudentGroupReservation
condition|)
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
if|else if
condition|(
name|crm
operator|.
name|reservation
argument_list|()
operator|instanceof
name|CourseReservation
condition|)
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
if|else if
condition|(
name|crm
operator|.
name|reservation
argument_list|()
operator|instanceof
name|CurriculumReservation
condition|)
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
block|}
for|for
control|(
name|StudentClassEnrollment
name|x
range|:
name|crm
operator|.
name|enrollment
argument_list|()
control|)
block|{
if|if
condition|(
name|x
operator|.
name|getTimestamp
argument_list|()
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|e
operator|.
name|getEnrolledDate
argument_list|()
operator|==
literal|null
condition|)
name|e
operator|.
name|setEnrolledDate
argument_list|(
name|x
operator|.
name|getTimestamp
argument_list|()
argument_list|)
expr_stmt|;
if|else if
condition|(
name|x
operator|.
name|getTimestamp
argument_list|()
operator|.
name|after
argument_list|(
name|e
operator|.
name|getEnrolledDate
argument_list|()
argument_list|)
condition|)
name|e
operator|.
name|setEnrolledDate
argument_list|(
name|x
operator|.
name|getTimestamp
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|crm
operator|.
name|approval
argument_list|()
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|StudentClassEnrollment
name|x
range|:
name|crm
operator|.
name|enrollment
argument_list|()
control|)
block|{
if|if
condition|(
name|x
operator|.
name|getApprovedDate
argument_list|()
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|x
operator|.
name|getApprovedDate
argument_list|()
operator|==
literal|null
condition|)
block|{
name|e
operator|.
name|setApprovedDate
argument_list|(
name|x
operator|.
name|getApprovedDate
argument_list|()
argument_list|)
expr_stmt|;
name|e
operator|.
name|setApprovedBy
argument_list|(
name|x
operator|.
name|getApprovedBy
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|x
operator|.
name|getApprovedDate
argument_list|()
operator|.
name|after
argument_list|(
name|e
operator|.
name|getApprovedDate
argument_list|()
argument_list|)
condition|)
block|{
name|e
operator|.
name|setApprovedDate
argument_list|(
name|x
operator|.
name|getApprovedDate
argument_list|()
argument_list|)
expr_stmt|;
name|e
operator|.
name|setApprovedBy
argument_list|(
name|x
operator|.
name|getApprovedBy
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
for|for
control|(
name|StudentClassEnrollment
name|enrollment
range|:
name|crm
operator|.
name|enrollment
argument_list|()
control|)
block|{
name|Class_
name|section
init|=
name|enrollment
operator|.
name|getClazz
argument_list|()
decl_stmt|;
name|SchedulingSubpart
name|subpart
init|=
name|section
operator|.
name|getSchedulingSubpart
argument_list|()
decl_stmt|;
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
name|getCourseDemand
argument_list|()
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
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|a
operator|.
name|setSubpart
argument_list|(
name|subpart
operator|.
name|getItype
argument_list|()
operator|.
name|getAbbv
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|subpart
operator|.
name|getInstrOfferingConfig
argument_list|()
operator|.
name|getInstructionalMethod
argument_list|()
operator|!=
literal|null
condition|)
name|a
operator|.
name|setSubpart
argument_list|(
name|a
operator|.
name|getSubpart
argument_list|()
operator|+
literal|" ("
operator|+
name|subpart
operator|.
name|getInstrOfferingConfig
argument_list|()
operator|.
name|getInstructionalMethod
argument_list|()
operator|.
name|getLabel
argument_list|()
operator|+
literal|")"
argument_list|)
expr_stmt|;
name|a
operator|.
name|setClassNumber
argument_list|(
name|section
operator|.
name|getSectionNumberString
argument_list|()
argument_list|)
expr_stmt|;
name|a
operator|.
name|setSection
argument_list|(
name|section
operator|.
name|getClassSuffix
argument_list|(
name|course
argument_list|)
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
name|section
operator|.
name|getEnrollment
argument_list|()
block|,
name|section
operator|.
name|getSectioningLimit
argument_list|()
block|}
argument_list|)
expr_stmt|;
name|Assignment
name|assignment
init|=
name|section
operator|.
name|getCommittedAssignment
argument_list|()
decl_stmt|;
if|if
condition|(
name|assignment
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
name|assignment
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
name|assignment
operator|.
name|getStartSlot
argument_list|()
argument_list|)
expr_stmt|;
name|a
operator|.
name|setLength
argument_list|(
name|assignment
operator|.
name|getSlotPerMtg
argument_list|()
argument_list|)
expr_stmt|;
name|a
operator|.
name|setBreakTime
argument_list|(
name|assignment
operator|.
name|getBreakTime
argument_list|()
argument_list|)
expr_stmt|;
name|a
operator|.
name|setDatePattern
argument_list|(
name|assignment
operator|.
name|getDatePattern
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|assignment
operator|!=
literal|null
operator|&&
operator|!
name|assignment
operator|.
name|getRooms
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
for|for
control|(
name|Location
name|rm
range|:
name|assignment
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
name|getLabel
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|section
operator|.
name|isDisplayInstructor
argument_list|()
operator|&&
operator|!
name|section
operator|.
name|getClassInstructors
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
for|for
control|(
name|ClassInstructor
name|instructor
range|:
name|section
operator|.
name|getClassInstructors
argument_list|()
control|)
block|{
name|a
operator|.
name|addInstructor
argument_list|(
name|helper
operator|.
name|getInstructorNameFormat
argument_list|()
operator|.
name|format
argument_list|(
name|instructor
operator|.
name|getInstructor
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|a
operator|.
name|addInstructoEmail
argument_list|(
name|instructor
operator|.
name|getInstructor
argument_list|()
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
name|getParentClass
argument_list|()
operator|!=
literal|null
condition|)
name|a
operator|.
name|setParentSection
argument_list|(
name|section
operator|.
name|getParentClass
argument_list|()
operator|.
name|getClassSuffix
argument_list|(
name|course
argument_list|)
argument_list|)
expr_stmt|;
name|a
operator|.
name|setSubpartId
argument_list|(
name|section
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|a
operator|.
name|addNote
argument_list|(
name|course
operator|.
name|getScheduleBookNote
argument_list|()
argument_list|)
expr_stmt|;
name|a
operator|.
name|addNote
argument_list|(
name|section
operator|.
name|getSchedulePrintNote
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|section
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getCredit
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|a
operator|.
name|setCredit
argument_list|(
name|section
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getCredit
argument_list|()
operator|.
name|creditAbbv
argument_list|()
operator|+
literal|"|"
operator|+
name|section
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getCredit
argument_list|()
operator|.
name|creditText
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|section
operator|.
name|getParentClass
argument_list|()
operator|!=
literal|null
operator|&&
name|course
operator|.
name|getCredit
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|a
operator|.
name|setCredit
argument_list|(
name|course
operator|.
name|getCredit
argument_list|()
operator|.
name|creditAbbv
argument_list|()
operator|+
literal|"|"
operator|+
name|course
operator|.
name|getCredit
argument_list|()
operator|.
name|creditText
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|a
operator|.
name|getParentSection
argument_list|()
operator|==
literal|null
condition|)
block|{
name|String
name|consent
init|=
operator|(
name|course
operator|.
name|getConsentType
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
name|course
operator|.
name|getConsentType
argument_list|()
operator|.
name|getLabel
argument_list|()
operator|)
decl_stmt|;
if|if
condition|(
name|consent
operator|!=
literal|null
condition|)
name|a
operator|.
name|setParentSection
argument_list|(
name|consent
argument_list|)
expr_stmt|;
block|}
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
name|getSectioningLimit
argument_list|()
argument_list|,
name|section
operator|.
name|getSectioningInfo
argument_list|()
operator|==
literal|null
condition|?
literal|0.0
else|:
name|section
operator|.
name|getSectioningInfo
argument_list|()
operator|.
name|getNbrExpectedStudents
argument_list|()
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
block|}
end_class

end_unit

