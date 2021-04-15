begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*< * Licensed to The Apereo Foundation under one or more contributor license  * agreements. See the NOTICE file distributed with this work for  * additional information regarding copyright ownership.  *  * The Apereo Foundation licenses this file to you under the Apache License,  * Version 2.0 (the "License"); you may not use this file except in  * compliance with the License. You may obtain a copy of the License at:  *  * http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *  * See the License for the specific language governing permissions and  * limitations under the License.  *  */
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
name|custom
operator|.
name|purdue
package|;
end_package

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
name|joda
operator|.
name|time
operator|.
name|DateTime
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|SpecialRegistrationInterface
block|{
comment|/** Possible values of the status field in the Spec Reg API responses */
comment|// FIXME: Are there any other values that can be returned?
specifier|public
specifier|static
enum|enum
name|ResponseStatus
block|{
name|success
block|,
comment|// call succeeded, data field is filled in
name|error
block|,
comment|// there was an error, see message for details
name|failure
block|,
comment|//TODO: is this status used?
name|Failed
block|,
comment|// present in outJson.status when the validation failed
block|; 	}
comment|/** Possible values for the Spec Reg API mode */
specifier|public
specifier|static
enum|enum
name|ApiMode
block|{
name|PREREG
block|,
comment|// pre-registration (Course Requests page is used)
name|REG
block|,
comment|// registration (Scheduling Assistant page is used)
block|; 	}
comment|/** Possible values for the requestor role */
specifier|public
specifier|static
enum|enum
name|RequestorRole
block|{
name|STUDENT
block|,
comment|// request is done by the student
name|MANAGER
block|,
comment|// request is done by the admin or the advisor on behalf of the student
block|; 	}
comment|/** Completion status of a special registration request */
specifier|public
specifier|static
enum|enum
name|CompletionStatus
block|{
name|inProgress
block|,
comment|// there is at least one change (order) that has not been approved, denied or canceled
name|completed
block|,
comment|// all the changes are either approved or denied
name|cancelled
block|,
comment|// all the changes are either approved, denied, or canceled and there is at least one that is canceled
block|; 	}
comment|/** Possible status values of a single override request ({@link Change}, work order in the Spec Reg terms) */
comment|// FIXME: Could there be any other values returned?
specifier|public
specifier|static
enum|enum
name|ChangeStatus
block|{
name|inProgress
block|,
comment|// override has been posted, no change has been done to it yet
name|approved
block|,
comment|// override has been approved
name|denied
block|,
comment|// override has been denied
name|cancelled
block|,
comment|// override has been cancelled
name|deferred
block|,
comment|// override has been deferred (it is in progress for UniTime)
name|escalated
block|,
comment|// override has been escalated (it is in progress for UniTime)
block|; 	}
comment|/** Basic format of (mostly all) responses from Spec Reg API */
specifier|public
specifier|static
class|class
name|Response
parameter_list|<
name|T
parameter_list|>
block|{
comment|/** Response data, depends on the call */
specifier|public
name|T
name|data
decl_stmt|;
comment|/** Success / error message */
specifier|public
name|String
name|message
decl_stmt|;
comment|/** Response status, success or error */
specifier|public
name|ResponseStatus
name|status
decl_stmt|;
block|}
comment|/** Base representation of a special registration request that is used both during pre-registration and registration */
comment|// submitRegistration request (PREREG, REG)
specifier|public
specifier|static
class|class
name|SpecialRegistration
block|{
comment|/** Special registration request id, only returned */
specifier|public
name|String
name|regRequestId
decl_stmt|;
comment|/** Student PUID including the leading zero */
specifier|public
name|String
name|studentId
decl_stmt|;
comment|/** Banner term (e.g., 201910 for Fall 2018) */
specifier|public
name|String
name|term
decl_stmt|;
comment|/** Banner campus (e.g., PWL) */
specifier|public
name|String
name|campus
decl_stmt|;
comment|/** Special Registration API mode (REG or PREREG) */
specifier|public
name|ApiMode
name|mode
decl_stmt|;
comment|/** List of changes that the student needs overrides for */
specifier|public
name|List
argument_list|<
name|Change
argument_list|>
name|changes
decl_stmt|;
comment|/** Request creation date */
specifier|public
name|DateTime
name|dateCreated
decl_stmt|;
comment|/** Max credit needed (only filled in when max override needs to be increased!) */
specifier|public
name|Float
name|maxCredit
decl_stmt|;
comment|/** Student message provided with the request */
specifier|public
name|String
name|requestorNotes
decl_stmt|;
comment|/** Request completion status (only read, never sent) */
specifier|public
name|CompletionStatus
name|completionStatus
decl_stmt|;
block|}
comment|/** Possible operations for a change (work order) */
specifier|public
specifier|static
enum|enum
name|ChangeOperation
block|{
name|ADD
block|,
comment|// sections (or the course in case of pre-reg) are being added
name|DROP
block|,
comment|// sections are being dropped (only during registration)
name|KEEP
block|,
comment|// sections are being unchanged, but a registration error was reported on them (e.g., co-requisite, only during registration)
name|CHGMODE
block|,
comment|// change grade mode
name|CHGVARCR
block|,
comment|// change variable credit
name|CHGVARTL
block|,
comment|// request variable title course
block|; 	}
comment|/** Class representing one change (in a signle course) */
specifier|public
specifier|static
class|class
name|Change
block|{
comment|/** Subject area (can be null in case of the MAXI error) */
specifier|public
name|String
name|subject
decl_stmt|;
comment|/** Course number (can be null in case of the MAXI error) */
specifier|public
name|String
name|courseNbr
decl_stmt|;
comment|/** Comma separated list of crns (only used during registration) */
specifier|public
name|String
name|crn
decl_stmt|;
comment|/** Change operation */
specifier|public
name|ChangeOperation
name|operation
decl_stmt|;
comment|/** Registration errors */
specifier|public
name|List
argument_list|<
name|ChangeError
argument_list|>
name|errors
decl_stmt|;
comment|/** Status of the change */
specifier|public
name|ChangeStatus
name|status
decl_stmt|;
comment|/** Notes attached to the change (only the last one is needed by UniTime) */
specifier|public
name|List
argument_list|<
name|ChangeNote
argument_list|>
name|notes
decl_stmt|;
comment|/** Credit value associated with the course / change, populated by UniTime (only during registration) */
specifier|public
name|String
name|credit
decl_stmt|;
comment|/** Current grade mode (only used when operation = CHGMODE) */
specifier|public
name|String
name|currentGradeMode
decl_stmt|;
comment|/** New grade mode (only used when operation = CHGMODE or CHGVARTL) */
specifier|public
name|String
name|selectedGradeMode
decl_stmt|;
comment|/** New grade mode description (only used when operation = CHGMODE) */
specifier|public
name|String
name|selectedGradeModeDescription
decl_stmt|;
comment|/** Current credit hours (only used when operation = CHGVARCR) */
specifier|public
name|String
name|currentCreditHour
decl_stmt|;
comment|/** Selected credit hours (only used when operation = CHGVARCR or CHGVARTL) */
specifier|public
name|String
name|selectedCreditHour
decl_stmt|;
comment|/** Selected course title (only used when operation = CHGVARTL) */
specifier|public
name|String
name|selectedTitle
decl_stmt|;
comment|/** Selected instructor (only used when operation = CHGVARTL) */
specifier|public
name|String
name|selectedInstructor
decl_stmt|;
comment|/** Selected instructor name (only used when operation = CHGVARTL) */
specifier|public
name|String
name|selectedInstructorName
decl_stmt|;
comment|/** Selected start date (only used when operation = CHGVARTL, format MM/dd/yy) */
specifier|public
name|String
name|selectedStartDate
decl_stmt|;
comment|/** Selected end date (only used when operation = CHGVARTL, format MM/dd/yy) */
specifier|public
name|String
name|selectedEndDate
decl_stmt|;
block|}
comment|/** Registration error for which there needs to be an override */
specifier|public
specifier|static
class|class
name|ChangeError
block|{
comment|/** Error code */
name|String
name|code
decl_stmt|;
comment|/** error message (shown to the student) */
name|String
name|message
decl_stmt|;
block|}
comment|/** Change note */
specifier|public
specifier|static
class|class
name|ChangeNote
block|{
comment|/** Time stamp of the note */
specifier|public
name|DateTime
name|dateCreated
decl_stmt|;
comment|/** Name of the person that provided the note (not used by UniTime) */
specifier|public
name|String
name|fullName
decl_stmt|;
comment|/** Text note */
specifier|public
name|String
name|notes
decl_stmt|;
comment|/** Purpose of the note (not used by UniTime) */
specifier|public
name|String
name|purpose
decl_stmt|;
block|}
comment|/* - Sumbit Registration -------------------------------------------------- */
comment|// POST /submitRegistration
comment|// Request: SpecialRegistrationRequest
comment|// Returns: SubmitRegistrationResponse
comment|/** 	 * Payload message for the /submitRegistration request 	 */
specifier|public
specifier|static
class|class
name|SpecialRegistrationRequest
extends|extends
name|SpecialRegistration
block|{
comment|/** 		 * PUID (including the leading zero) of the user posting the request (may be a student or an admin/advisor). 		 * Only sent, not needed in the response. 		 */
specifier|public
name|String
name|requestorId
decl_stmt|;
comment|/** 		 * Role of the requestor (STUDENT or MANAGER). 		 * Only sent, not needed in the response. 		 */
specifier|public
name|RequestorRole
name|requestorRole
decl_stmt|;
comment|/** 		 * Course requests at the time when the override request was created (only used when mode = PREREG, only sent, not needed in the response) 		 */
specifier|public
name|List
argument_list|<
name|CourseCredit
argument_list|>
name|courseCreditHrs
decl_stmt|;
comment|/** 		 * Alternate course requests at the time when the override request was created (only used when mode = PREREG, only sent, not needed in the response) 		 */
specifier|public
name|List
argument_list|<
name|CourseCredit
argument_list|>
name|alternateCourseCreditHrs
decl_stmt|;
block|}
comment|/** 	 * Course or alternate requests during pre-registration. 	 */
specifier|public
specifier|static
class|class
name|CourseCredit
block|{
comment|/** Subject area */
specifier|public
name|String
name|subject
decl_stmt|;
comment|/** Course number */
specifier|public
name|String
name|courseNbr
decl_stmt|;
comment|/** Course title */
specifier|public
name|String
name|title
decl_stmt|;
comment|/** Lower bound on the credit */
specifier|public
name|Float
name|creditHrs
decl_stmt|;
comment|/** Alternatives, if provided */
specifier|public
name|List
argument_list|<
name|CourseCredit
argument_list|>
name|alternatives
decl_stmt|;
block|}
comment|/** Class representing a special registration that has been cancelled */
specifier|public
specifier|static
class|class
name|CancelledRequest
block|{
comment|/** Subject area */
specifier|public
name|String
name|subject
decl_stmt|;
comment|/** Course number */
specifier|public
name|String
name|courseNbr
decl_stmt|;
comment|/** Comma separated list of CRNs */
specifier|public
name|String
name|crn
decl_stmt|;
comment|/** Special registration request id */
specifier|public
name|String
name|regRequestId
decl_stmt|;
block|}
comment|/** 	 * Special registrations for the /submitRegistration response 	 * 	 */
specifier|public
specifier|static
class|class
name|SubmitRegistrationResponse
extends|extends
name|SpecialRegistration
block|{
comment|/** 		 * List of special registrations that have been cancelled (to create this request). 		 * (only read, never sent; only used in submitRegistration response during registration) 		 */
specifier|public
name|List
argument_list|<
name|CancelledRequest
argument_list|>
name|cancelledRequests
decl_stmt|;
block|}
comment|/** 	 * Response message for the /submitRegistration call 	 * In pre-registration, a separate special registration request is created for each course (plus one for the max credit increase, if needed). 	 * In registration, a single special registration request  	 */
specifier|public
specifier|static
class|class
name|SpecialRegistrationResponseList
extends|extends
name|Response
argument_list|<
name|List
argument_list|<
name|SubmitRegistrationResponse
argument_list|>
argument_list|>
block|{
comment|/** 		 * List of special registrations that have been cancelled (to create these requests). 		 * (only read, never sent; only used in submitRegistration response during grade mode change) 		 */
specifier|public
name|List
argument_list|<
name|CancelledRequest
argument_list|>
name|cancelledRequests
decl_stmt|;
block|}
comment|/* - Check Special Registration Status ------------------------------------ */
comment|// GET /checkSpecialRegistrationStatus?term=<TERM>&campus=<CAMPUS>&studentId=<PUID>&mode=<REG|PREREG>
comment|// Returns: SpecialRegistrationStatusResponse
comment|/**  	 * Special registration status for the /checkSpecialRegistrationStatus response 	 */
specifier|public
specifier|static
class|class
name|SpecialRegistrationStatus
block|{
comment|/** List of special registrations of the student (of given mode) */
specifier|public
name|List
argument_list|<
name|SpecialRegistration
argument_list|>
name|requests
decl_stmt|;
comment|/** Max credits that the student is allowed at the moment */
specifier|public
name|Float
name|maxCredit
decl_stmt|;
comment|/** Student PUID including the leading zero (needed only in /checkAllSpecialRegistrationStatus) */
specifier|public
name|String
name|studentId
decl_stmt|;
block|}
comment|/** 	 * Response message for the /checkSpecialRegistrationStatus call 	 */
specifier|public
specifier|static
class|class
name|SpecialRegistrationStatusResponse
extends|extends
name|Response
argument_list|<
name|SpecialRegistrationStatus
argument_list|>
block|{ 	}
comment|/* - Check All Special Registration Status -------------------------------- */
comment|// GET /checkAllSpecialRegistrationStatus?term=<TERM>&campus=<CAMPUS>&studentIds=<PUID1,PUID2,...>&mode=PREREG
comment|// Returns: SpecialRegistrationMultipleStatusResponse
comment|/** Data message for the /checkAllSpecialRegistrationStatus call */
specifier|public
specifier|static
class|class
name|SpecialRegistrationMultipleStatus
block|{
specifier|public
name|List
argument_list|<
name|SpecialRegistrationStatus
argument_list|>
name|students
decl_stmt|;
block|}
comment|/** 	 * Response message for the /checkAllSpecialRegistrationStatus call (used only during pre-registration) 	 */
specifier|public
specifier|static
class|class
name|SpecialRegistrationMultipleStatusResponse
extends|extends
name|Response
argument_list|<
name|SpecialRegistrationMultipleStatus
argument_list|>
block|{ 	}
comment|/* - Check Eligibility ---------------------------------------------------- */
comment|// GET /checkEligibility?term=<TERM>&campus=<CAMPUS>&studentId=<PUID>&mode=<REG|PREREG>
comment|// Returns: CheckEligibilityResponse
comment|/** 	 * Student eligibility response for the /checkEligibility response 	 */
specifier|public
specifier|static
class|class
name|SpecialRegistrationEligibility
block|{
comment|/** Student PUID including the leading zero */
specifier|public
name|String
name|studentId
decl_stmt|;
comment|/** Banner term */
specifier|public
name|String
name|term
decl_stmt|;
comment|/** Banner campus */
specifier|public
name|String
name|campus
decl_stmt|;
comment|/** Is student eligible to register (pre-reg). Is student eligible to request overrides (reg). */
specifier|public
name|Boolean
name|eligible
decl_stmt|;
comment|/** Detected eligibility problems (in pre-reg: e.g., student has a HOLD) */
specifier|public
name|List
argument_list|<
name|EligibilityProblem
argument_list|>
name|eligibilityProblems
decl_stmt|;
comment|/** Student PIN (NA when not available) */
specifier|public
name|String
name|PIN
decl_stmt|;
block|}
comment|/** 	 * Detected student eligibility problem 	 */
specifier|public
specifier|static
class|class
name|EligibilityProblem
block|{
comment|/** Problem code (e.g., HOLD) */
name|String
name|code
decl_stmt|;
comment|/** Problem message (description) that can bedisplayed to the student */
name|String
name|message
decl_stmt|;
block|}
comment|/** 	 * Response message for the /checkSpecialRegistrationStatus call 	 */
specifier|public
specifier|static
class|class
name|CheckEligibilityResponse
extends|extends
name|Response
argument_list|<
name|SpecialRegistrationEligibility
argument_list|>
block|{
comment|/** Registration errors for which overrides can be requested (only used during registration) */
specifier|public
name|Set
argument_list|<
name|String
argument_list|>
name|overrides
decl_stmt|;
comment|/** Student max credit (only used during registration) */
specifier|public
name|Float
name|maxCredit
decl_stmt|;
comment|/** Are there any not-cancelled requests for the student (only used during registration. indication that the Requested Overrides table should be shown) */
specifier|public
name|Boolean
name|hasNonCanceledRequest
decl_stmt|;
block|}
comment|/* - Schedule Validation -------------------------------------------------- */
comment|// POST /checkRestrictions
comment|// request: CheckRestrictionsRequest
comment|// returns: CheckRestrictionsResponse
comment|/** Registrationn error */
specifier|public
specifier|static
class|class
name|Problem
block|{
comment|/** Error code */
name|String
name|code
decl_stmt|;
comment|/** Error message */
name|String
name|message
decl_stmt|;
comment|/** Section affected */
name|String
name|crn
decl_stmt|;
block|}
comment|/** Data returned from the schedule validation */
specifier|public
specifier|static
class|class
name|ScheduleRestrictions
block|{
comment|/** List of detected problems */
specifier|public
name|List
argument_list|<
name|Problem
argument_list|>
name|problems
decl_stmt|;
comment|/** Student PUID including the leading zero */
specifier|public
name|String
name|sisId
decl_stmt|;
comment|/** Validation status */
comment|// FIXME: what are the possible values?
specifier|public
name|ResponseStatus
name|status
decl_stmt|;
comment|/** Banner term */
specifier|public
name|String
name|term
decl_stmt|;
comment|/** Computed credit hours */
specifier|public
name|Float
name|maxHoursCalc
decl_stmt|;
comment|/** Error message */
specifier|public
name|String
name|message
decl_stmt|;
block|}
comment|/** Possible values for the includeReg parameter */
specifier|public
enum|enum
name|IncludeReg
block|{
name|Y
block|,
comment|// do include students current schedule in the validation
name|N
block|,
comment|// do NOT include students current schedule in the validation
block|; 	}
comment|/** Possible values for the validation mode */
specifier|public
specifier|static
enum|enum
name|ValidationMode
block|{
name|REG
block|,
comment|// registration changes
name|ALT
block|,
comment|// alternate changes
block|; 	}
comment|/** Possible values for the validation operation */
specifier|public
specifier|static
enum|enum
name|ValidationOperation
block|{
name|ADD
block|,
comment|// add CRNs
name|DROP
block|,
comment|// drop CRNs
block|; 	}
comment|/** Representation of a single CRN */
specifier|public
specifier|static
class|class
name|Crn
block|{
comment|/** CRN */
name|String
name|crn
decl_stmt|;
block|}
comment|/** Data provided to the schedule validation */
specifier|public
specifier|static
class|class
name|RestrictionsCheckRequest
block|{
comment|/** Student PUID including the leading zero */
specifier|public
name|String
name|sisId
decl_stmt|;
comment|/** Banner term */
specifier|public
name|String
name|term
decl_stmt|;
comment|/** Banner campus */
specifier|public
name|String
name|campus
decl_stmt|;
comment|/** Include the current student's schedule in the validation */
specifier|public
name|IncludeReg
name|includeReg
decl_stmt|;
comment|/** Validation mode (REG or ALT) */
specifier|public
name|ValidationMode
name|mode
decl_stmt|;
comment|/** Schedule changes */
specifier|public
name|Map
argument_list|<
name|ValidationOperation
argument_list|,
name|List
argument_list|<
name|Crn
argument_list|>
argument_list|>
name|actions
decl_stmt|;
block|}
comment|/** Overrides that have been denied for the student (matching the validation request) */
specifier|public
specifier|static
class|class
name|DeniedRequest
block|{
comment|/** Subject area */
specifier|public
name|String
name|subject
decl_stmt|;
comment|/** Course number */
specifier|public
name|String
name|courseNbr
decl_stmt|;
comment|/** Comma separated lists of CRNs */
specifier|public
name|String
name|crn
decl_stmt|;
comment|/** Registration error code */
specifier|public
name|String
name|code
decl_stmt|;
comment|/** Registration error message */
specifier|public
name|String
name|errorMessage
decl_stmt|;
comment|/** Special Registration API mode (REG or PREREG) */
specifier|public
name|ApiMode
name|mode
decl_stmt|;
block|}
comment|/** Max credit override that have been denied for the student */
specifier|public
specifier|static
class|class
name|DeniedMaxCredit
block|{
comment|/** Registration error code */
specifier|public
name|String
name|code
decl_stmt|;
comment|/** Registration error message */
specifier|public
name|String
name|errorMessage
decl_stmt|;
comment|/** Max credit denied */
specifier|public
name|Float
name|maxCredit
decl_stmt|;
comment|/** Special Registration API mode (REG or PREREG) */
specifier|public
name|ApiMode
name|mode
decl_stmt|;
block|}
comment|/** Request message for the /checkRestrictions call */
specifier|public
specifier|static
class|class
name|CheckRestrictionsRequest
block|{
comment|/** Student PUID including the leading zero */
specifier|public
name|String
name|studentId
decl_stmt|;
comment|/** Banner term */
specifier|public
name|String
name|term
decl_stmt|;
comment|/** Banner campus */
specifier|public
name|String
name|campus
decl_stmt|;
comment|/** Special Registration API mode (REG or PREREG) */
specifier|public
name|ApiMode
name|mode
decl_stmt|;
comment|/** Schedule changes */
specifier|public
name|RestrictionsCheckRequest
name|changes
decl_stmt|;
comment|/** Alternatives (only used in pre-registration) */
specifier|public
name|RestrictionsCheckRequest
name|alternatives
decl_stmt|;
block|}
comment|/** Response message for the /checkRestrictions call */
specifier|public
specifier|static
class|class
name|CheckRestrictionsResponse
block|{
comment|/** Special registrations that would be cancelled if such a request is submitted (used only during registration) */
specifier|public
name|List
argument_list|<
name|SpecialRegistration
argument_list|>
name|cancelRegistrationRequests
decl_stmt|;
comment|/** Matching special registrations that has been denied already (used only during registration) */
specifier|public
name|List
argument_list|<
name|DeniedRequest
argument_list|>
name|deniedRequests
decl_stmt|;
comment|/** Max credit requests that have been denied (student should request that much credit) */
specifier|public
name|List
argument_list|<
name|DeniedMaxCredit
argument_list|>
name|deniedMaxCreditRequests
decl_stmt|;
comment|/** Student eligibility check (used only during registration) */
specifier|public
name|SpecialRegistrationEligibility
name|eligible
decl_stmt|;
comment|/** Student's current max credit (used only during registration) */
specifier|public
name|Float
name|maxCredit
decl_stmt|;
comment|/** Validation response for the schedule changes */
specifier|public
name|ScheduleRestrictions
name|outJson
decl_stmt|;
comment|/** Validation response for the alternative schedule changes (only used in pre-registration) */
specifier|public
name|ScheduleRestrictions
name|outJsonAlternatives
decl_stmt|;
comment|/** List of registrations errors for which the student is allowed to request overrides (used only during registration) */
specifier|public
name|Set
argument_list|<
name|String
argument_list|>
name|overrides
decl_stmt|;
comment|/** Response status, success or error */
specifier|public
name|ResponseStatus
name|status
decl_stmt|;
comment|/** Error message when the validation request fails */
specifier|public
name|String
name|message
decl_stmt|;
block|}
comment|/* - Cancel Registration Request ------------------------------------------ */
comment|// GET /cancelRegistrationRequestFromUniTime?term=<TERM>&campus=<CAMPUS>&studentId=<PUID>&mode=REG
comment|// Returns: SpecialRegistrationCancelResponse
comment|/** 	 * Response message for the /cancelRegistrationRequestFromUniTime call 	 */
specifier|public
specifier|static
class|class
name|SpecialRegistrationCancelResponse
extends|extends
name|Response
argument_list|<
name|String
argument_list|>
block|{ 	}
comment|/** 	 * Response message for the /checkStudentGradeModes call 	 */
specifier|public
specifier|static
class|class
name|SpecialRegistrationCheckGradeModesResponse
extends|extends
name|Response
argument_list|<
name|SpecialRegistrationCheckGradeModes
argument_list|>
block|{ 	}
comment|/** 	 * Response message for the /updateRequestorNote call 	 */
specifier|public
specifier|static
class|class
name|SpecialRegistrationUpdateResponse
extends|extends
name|Response
argument_list|<
name|String
argument_list|>
block|{ 	}
comment|/** 	 * List of available grade modes 	 */
specifier|public
specifier|static
class|class
name|SpecialRegistrationCheckGradeModes
block|{
comment|/** Student PUID including the leading zero */
specifier|public
name|String
name|studentId
decl_stmt|;
comment|/** Banner term (e.g., 201910 for Fall 2018) */
specifier|public
name|String
name|term
decl_stmt|;
comment|/** Banner campus (e.g., PWL) */
specifier|public
name|String
name|campus
decl_stmt|;
comment|/** List of available grade modes */
name|List
argument_list|<
name|SpecialRegistrationCurrentGradeMode
argument_list|>
name|gradingModes
decl_stmt|;
comment|/** List of available variable credits changes */
name|List
argument_list|<
name|SpecialRegistrationVariableCredit
argument_list|>
name|varCredits
decl_stmt|;
comment|/** Student's current credit */
specifier|public
name|Float
name|currentCredit
decl_stmt|;
comment|/** Student's max credit */
specifier|public
name|Float
name|maxCredit
decl_stmt|;
block|}
comment|/** 	 * Current grade mode of a section with the list of available grade mode changes 	 */
specifier|public
specifier|static
class|class
name|SpecialRegistrationCurrentGradeMode
block|{
comment|/** Section CRN */
specifier|public
name|String
name|crn
decl_stmt|;
comment|/** Current grade mode code */
specifier|public
name|String
name|gradingMode
decl_stmt|;
comment|/** Current grade mode desctiption */
specifier|public
name|String
name|gradingModeDescription
decl_stmt|;
comment|/** List of available grading mode changes */
specifier|public
name|List
argument_list|<
name|SpecialRegistrationAvailableGradeMode
argument_list|>
name|availableGradingModes
decl_stmt|;
block|}
comment|/** 	 * Variable credit change 	 */
specifier|public
specifier|static
class|class
name|SpecialRegistrationVariableCredit
block|{
comment|/** Section CRN */
specifier|public
name|String
name|crn
decl_stmt|;
comment|/** Credit min */
specifier|public
name|String
name|creditHrLow
decl_stmt|;
comment|/** Credit max */
specifier|public
name|String
name|creditHrHigh
decl_stmt|;
comment|/** Credit indicator */
specifier|public
name|String
name|creditHrInd
decl_stmt|;
comment|/** Approvals needed, null or empty when no approvals are not needed */
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|approvals
decl_stmt|;
block|}
specifier|public
specifier|static
class|class
name|SpecialRegistrationAvailableGradeMode
block|{
comment|/** Approvals needed, null or empty when no approvals are not needed */
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|approvals
decl_stmt|;
comment|/** Available grade mode code */
specifier|public
name|String
name|gradingMode
decl_stmt|;
comment|/** Available grade mode desctiption */
specifier|public
name|String
name|gradingModeDescription
decl_stmt|;
block|}
block|}
end_class

end_unit

