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
name|gwt
operator|.
name|services
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
name|Collection
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
name|Map
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
name|shared
operator|.
name|AcademicSessionProvider
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
name|CheckCoursesResponse
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
name|DegreePlanInterface
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
name|ReservationException
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
name|ReservationInterface
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
name|gwt
operator|.
name|shared
operator|.
name|StudentSchedulingPreferencesInterface
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
name|AcademicSessionProvider
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
name|gwt
operator|.
name|shared
operator|.
name|SpecialRegistrationInterface
operator|.
name|SpecialRegistrationEligibilityRequest
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
name|SpecialRegistrationInterface
operator|.
name|SpecialRegistrationEligibilityResponse
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
name|SpecialRegistrationInterface
operator|.
name|CancelSpecialRegistrationRequest
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
name|SpecialRegistrationInterface
operator|.
name|CancelSpecialRegistrationResponse
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
name|SpecialRegistrationInterface
operator|.
name|ChangeGradeModesRequest
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
name|SpecialRegistrationInterface
operator|.
name|ChangeGradeModesResponse
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
name|SpecialRegistrationInterface
operator|.
name|RetrieveAllSpecialRegistrationsRequest
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
name|SpecialRegistrationInterface
operator|.
name|RetrieveAvailableGradeModesRequest
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
name|SpecialRegistrationInterface
operator|.
name|RetrieveAvailableGradeModesResponse
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
name|SpecialRegistrationInterface
operator|.
name|RetrieveSpecialRegistrationResponse
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
name|SpecialRegistrationInterface
operator|.
name|SubmitSpecialRegistrationRequest
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
name|SpecialRegistrationInterface
operator|.
name|SubmitSpecialRegistrationResponse
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
name|SpecialRegistrationInterface
operator|.
name|UpdateSpecialRegistrationRequest
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
name|SpecialRegistrationInterface
operator|.
name|UpdateSpecialRegistrationResponse
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
name|SpecialRegistrationInterface
operator|.
name|VariableTitleCourseInfo
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
name|SpecialRegistrationInterface
operator|.
name|VariableTitleCourseRequest
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
name|SpecialRegistrationInterface
operator|.
name|VariableTitleCourseResponse
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
name|AdvisingStudentDetails
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
name|AdvisorCourseRequestSubmission
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
name|AdvisorNote
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
name|SectioningProperties
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
name|StudentInfo
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
name|StudentSectioningContext
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
name|StudentStatusInfo
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|gwt
operator|.
name|user
operator|.
name|client
operator|.
name|rpc
operator|.
name|RemoteService
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|gwt
operator|.
name|user
operator|.
name|client
operator|.
name|rpc
operator|.
name|RemoteServiceRelativePath
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_interface
annotation|@
name|RemoteServiceRelativePath
argument_list|(
literal|"sectioning.gwt"
argument_list|)
specifier|public
interface|interface
name|SectioningService
extends|extends
name|RemoteService
block|{
name|Collection
argument_list|<
name|ClassAssignmentInterface
operator|.
name|CourseAssignment
argument_list|>
name|listCourseOfferings
parameter_list|(
name|StudentSectioningContext
name|cx
parameter_list|,
name|String
name|query
parameter_list|,
name|Integer
name|limit
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|Collection
argument_list|<
name|AcademicSessionProvider
operator|.
name|AcademicSessionInfo
argument_list|>
name|listAcademicSessions
parameter_list|(
name|boolean
name|sectioning
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|String
name|retrieveCourseDetails
parameter_list|(
name|StudentSectioningContext
name|cx
parameter_list|,
name|String
name|course
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|Collection
argument_list|<
name|ClassAssignmentInterface
operator|.
name|ClassAssignment
argument_list|>
name|listClasses
parameter_list|(
name|StudentSectioningContext
name|cx
parameter_list|,
name|String
name|course
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|Long
name|retrieveCourseOfferingId
parameter_list|(
name|Long
name|sessionId
parameter_list|,
name|String
name|course
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|CheckCoursesResponse
name|checkCourses
parameter_list|(
name|CourseRequestInterface
name|request
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|ClassAssignmentInterface
name|section
parameter_list|(
name|CourseRequestInterface
name|request
parameter_list|,
name|ArrayList
argument_list|<
name|ClassAssignmentInterface
operator|.
name|ClassAssignment
argument_list|>
name|currentAssignment
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|Collection
argument_list|<
name|ClassAssignmentInterface
argument_list|>
name|computeSuggestions
parameter_list|(
name|CourseRequestInterface
name|request
parameter_list|,
name|Collection
argument_list|<
name|ClassAssignmentInterface
operator|.
name|ClassAssignment
argument_list|>
name|currentAssignment
parameter_list|,
name|int
name|selectedAssignment
parameter_list|,
name|String
name|filter
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|String
name|logIn
parameter_list|(
name|String
name|userName
parameter_list|,
name|String
name|password
parameter_list|,
name|String
name|pin
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|Boolean
name|logOut
parameter_list|()
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|String
name|whoAmI
parameter_list|()
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|OnlineSectioningInterface
operator|.
name|EligibilityCheck
name|checkEligibility
parameter_list|(
name|StudentSectioningContext
name|cx
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|AcademicSessionProvider
operator|.
name|AcademicSessionInfo
name|lastAcademicSession
parameter_list|(
name|boolean
name|sectioning
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|CourseRequestInterface
name|saveRequest
parameter_list|(
name|CourseRequestInterface
name|request
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|ClassAssignmentInterface
name|enroll
parameter_list|(
name|CourseRequestInterface
name|request
parameter_list|,
name|ArrayList
argument_list|<
name|ClassAssignmentInterface
operator|.
name|ClassAssignment
argument_list|>
name|currentAssignment
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|SectioningProperties
name|getProperties
parameter_list|(
name|Long
name|sessionId
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|List
argument_list|<
name|ClassAssignmentInterface
operator|.
name|Enrollment
argument_list|>
name|listEnrollments
parameter_list|(
name|Long
name|offeringId
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|ClassAssignmentInterface
name|getEnrollment
parameter_list|(
name|boolean
name|online
parameter_list|,
name|Long
name|studentId
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|List
argument_list|<
name|Long
argument_list|>
name|canApprove
parameter_list|(
name|Long
name|classOrOfferingId
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|String
name|approveEnrollments
parameter_list|(
name|Long
name|classOrOfferingId
parameter_list|,
name|List
argument_list|<
name|Long
argument_list|>
name|studentIds
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|Boolean
name|rejectEnrollments
parameter_list|(
name|Long
name|classOrOfferingId
parameter_list|,
name|List
argument_list|<
name|Long
argument_list|>
name|studentIds
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|List
argument_list|<
name|ClassAssignmentInterface
operator|.
name|EnrollmentInfo
argument_list|>
name|findEnrollmentInfos
parameter_list|(
name|boolean
name|online
parameter_list|,
name|String
name|query
parameter_list|,
name|SectioningStatusFilterRpcRequest
name|filter
parameter_list|,
name|Long
name|courseId
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|List
argument_list|<
name|ClassAssignmentInterface
operator|.
name|StudentInfo
argument_list|>
name|findStudentInfos
parameter_list|(
name|boolean
name|online
parameter_list|,
name|String
name|query
parameter_list|,
name|SectioningStatusFilterRpcRequest
name|filter
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|List
argument_list|<
name|ClassAssignmentInterface
operator|.
name|Enrollment
argument_list|>
name|findEnrollments
parameter_list|(
name|boolean
name|online
parameter_list|,
name|String
name|query
parameter_list|,
name|SectioningStatusFilterRpcRequest
name|filter
parameter_list|,
name|Long
name|courseId
parameter_list|,
name|Long
name|classId
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|List
argument_list|<
name|String
index|[]
argument_list|>
name|querySuggestions
parameter_list|(
name|boolean
name|online
parameter_list|,
name|String
name|query
parameter_list|,
name|int
name|limit
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|Long
name|canEnroll
parameter_list|(
name|StudentSectioningContext
name|cx
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|CourseRequestInterface
name|savedRequest
parameter_list|(
name|StudentSectioningContext
name|cx
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|ClassAssignmentInterface
name|savedResult
parameter_list|(
name|StudentSectioningContext
name|cx
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|Boolean
name|selectSession
parameter_list|(
name|Long
name|sessionId
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|List
argument_list|<
name|StudentStatusInfo
argument_list|>
name|lookupStudentSectioningStates
parameter_list|()
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|Boolean
name|sendEmail
parameter_list|(
name|Long
name|sessionId
parameter_list|,
name|Long
name|studentId
parameter_list|,
name|String
name|subject
parameter_list|,
name|String
name|message
parameter_list|,
name|String
name|cc
parameter_list|,
name|Boolean
name|courseRequests
parameter_list|,
name|Boolean
name|classSchedule
parameter_list|,
name|Boolean
name|advisorRequests
parameter_list|,
name|Boolean
name|optional
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|Boolean
name|changeStatus
parameter_list|(
name|List
argument_list|<
name|Long
argument_list|>
name|studentIds
parameter_list|,
name|String
name|note
parameter_list|,
name|String
name|status
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|Boolean
name|changeStudentGroup
parameter_list|(
name|List
argument_list|<
name|Long
argument_list|>
name|studentIds
parameter_list|,
name|Long
name|groupId
parameter_list|,
name|boolean
name|remove
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|List
argument_list|<
name|ClassAssignmentInterface
operator|.
name|SectioningAction
argument_list|>
name|changeLog
parameter_list|(
name|String
name|query
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|Boolean
name|massCancel
parameter_list|(
name|List
argument_list|<
name|Long
argument_list|>
name|studentIds
parameter_list|,
name|String
name|status
parameter_list|,
name|String
name|subject
parameter_list|,
name|String
name|message
parameter_list|,
name|String
name|cc
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|Boolean
name|requestStudentUpdate
parameter_list|(
name|List
argument_list|<
name|Long
argument_list|>
name|studentIds
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|Boolean
name|reloadStudent
parameter_list|(
name|List
argument_list|<
name|Long
argument_list|>
name|studentIds
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|List
argument_list|<
name|DegreePlanInterface
argument_list|>
name|listDegreePlans
parameter_list|(
name|StudentSectioningContext
name|cx
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|ClassAssignmentInterface
operator|.
name|Student
name|lookupStudent
parameter_list|(
name|boolean
name|online
parameter_list|,
name|String
name|studentId
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|ClassAssignmentInterface
operator|.
name|Student
name|lookupStudent
parameter_list|(
name|boolean
name|online
parameter_list|,
name|Long
name|studentId
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|Boolean
name|checkStudentOverrides
parameter_list|(
name|List
argument_list|<
name|Long
argument_list|>
name|studentIds
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|Boolean
name|validateStudentOverrides
parameter_list|(
name|List
argument_list|<
name|Long
argument_list|>
name|studentIds
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|Boolean
name|recheckCriticalCourses
parameter_list|(
name|List
argument_list|<
name|Long
argument_list|>
name|studentIds
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|SubmitSpecialRegistrationResponse
name|submitSpecialRequest
parameter_list|(
name|SubmitSpecialRegistrationRequest
name|request
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|SpecialRegistrationEligibilityResponse
name|checkSpecialRequestEligibility
parameter_list|(
name|SpecialRegistrationEligibilityRequest
name|request
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|List
argument_list|<
name|RetrieveSpecialRegistrationResponse
argument_list|>
name|retrieveAllSpecialRequests
parameter_list|(
name|RetrieveAllSpecialRegistrationsRequest
name|request
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|ClassAssignmentInterface
name|section
parameter_list|(
name|CourseRequestInterface
name|request
parameter_list|,
name|List
argument_list|<
name|ClassAssignmentInterface
operator|.
name|ClassAssignment
argument_list|>
name|currentAssignment
parameter_list|,
name|List
argument_list|<
name|ClassAssignmentInterface
operator|.
name|ClassAssignment
argument_list|>
name|specialRegistration
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|CancelSpecialRegistrationResponse
name|cancelSpecialRequest
parameter_list|(
name|CancelSpecialRegistrationRequest
name|request
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|RetrieveAvailableGradeModesResponse
name|retrieveGradeModes
parameter_list|(
name|RetrieveAvailableGradeModesRequest
name|request
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|ChangeGradeModesResponse
name|changeGradeModes
parameter_list|(
name|ChangeGradeModesRequest
name|request
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|Integer
name|changeCriticalOverride
parameter_list|(
name|Long
name|studentId
parameter_list|,
name|Long
name|courseId
parameter_list|,
name|Integer
name|critical
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|UpdateSpecialRegistrationResponse
name|updateSpecialRequest
parameter_list|(
name|UpdateSpecialRegistrationRequest
name|request
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|CheckCoursesResponse
name|waitListCheckValidation
parameter_list|(
name|CourseRequestInterface
name|request
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|CourseRequestInterface
name|waitListSubmitOverrides
parameter_list|(
name|CourseRequestInterface
name|request
parameter_list|,
name|Float
name|neededCredit
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|Collection
argument_list|<
name|AcademicSessionInfo
argument_list|>
name|getStudentSessions
parameter_list|(
name|String
name|studentExternalId
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|AdvisingStudentDetails
name|getStudentAdvisingDetails
parameter_list|(
name|Long
name|sessionId
parameter_list|,
name|String
name|studentExternalId
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|StudentInfo
name|getStudentInfo
parameter_list|(
name|Long
name|studentId
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|CheckCoursesResponse
name|checkAdvisingDetails
parameter_list|(
name|AdvisingStudentDetails
name|details
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|AdvisorCourseRequestSubmission
name|submitAdvisingDetails
parameter_list|(
name|AdvisingStudentDetails
name|details
parameter_list|,
name|boolean
name|emailStudent
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|CourseRequestInterface
name|getAdvisorRequests
parameter_list|(
name|StudentSectioningContext
name|cx
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|List
argument_list|<
name|ReservationInterface
argument_list|>
name|getReservations
parameter_list|(
name|boolean
name|online
parameter_list|,
name|Long
name|offeringId
parameter_list|)
throws|throws
name|ReservationException
throws|,
name|PageAccessException
function_decl|;
name|List
argument_list|<
name|AdvisorNote
argument_list|>
name|lastAdvisorNotes
parameter_list|(
name|StudentSectioningContext
name|cx
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|Map
argument_list|<
name|Long
argument_list|,
name|String
argument_list|>
name|getChangeLogTexts
parameter_list|(
name|Collection
argument_list|<
name|Long
argument_list|>
name|logIds
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|String
name|getChangeLogMessage
parameter_list|(
name|Long
name|logId
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|Collection
argument_list|<
name|VariableTitleCourseInfo
argument_list|>
name|listVariableTitleCourses
parameter_list|(
name|StudentSectioningContext
name|cx
parameter_list|,
name|String
name|query
parameter_list|,
name|int
name|limit
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|VariableTitleCourseInfo
name|getVariableTitleCourse
parameter_list|(
name|StudentSectioningContext
name|cx
parameter_list|,
name|String
name|course
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|VariableTitleCourseResponse
name|requestVariableTitleCourse
parameter_list|(
name|VariableTitleCourseRequest
name|request
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|Collection
argument_list|<
name|ClassAssignmentInterface
operator|.
name|CourseAssignment
argument_list|>
name|getCoursesFromRequest
parameter_list|(
name|StudentSectioningContext
name|cx
parameter_list|,
name|CourseRequestInterface
operator|.
name|Request
name|query
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|StudentSchedulingPreferencesInterface
name|getStudentSchedulingPreferences
parameter_list|(
name|StudentSectioningContext
name|cx
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|Boolean
name|setStudentSchedulingPreferences
parameter_list|(
name|StudentSectioningContext
name|cx
parameter_list|,
name|StudentSchedulingPreferencesInterface
name|preferences
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
block|}
end_interface

end_unit

