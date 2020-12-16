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
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|Serializable
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
name|Set
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
name|DataProperties
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
name|onlinesectioning
operator|.
name|custom
operator|.
name|CourseDetailsProvider
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
name|match
operator|.
name|CourseMatcher
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
name|match
operator|.
name|StudentMatcher
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
name|XCourseId
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
name|XTime
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
name|server
operator|.
name|CheckMaster
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
name|server
operator|.
name|CheckMaster
operator|.
name|Master
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_interface
specifier|public
interface|interface
name|OnlineSectioningServer
block|{
specifier|public
name|boolean
name|isMaster
parameter_list|()
function_decl|;
annotation|@
name|CheckMaster
argument_list|(
name|Master
operator|.
name|REQUIRED
argument_list|)
specifier|public
name|void
name|releaseMasterLockIfHeld
parameter_list|()
function_decl|;
specifier|public
name|String
name|getHost
parameter_list|()
function_decl|;
specifier|public
name|String
name|getUser
parameter_list|()
function_decl|;
specifier|public
name|AcademicSessionInfo
name|getAcademicSession
parameter_list|()
function_decl|;
specifier|public
name|DistanceMetric
name|getDistanceMetric
parameter_list|()
function_decl|;
specifier|public
name|DataProperties
name|getConfig
parameter_list|()
function_decl|;
specifier|public
name|OverExpectedCriterion
name|getOverExpectedCriterion
parameter_list|()
function_decl|;
specifier|public
name|Collection
argument_list|<
name|?
extends|extends
name|XCourseId
argument_list|>
name|findCourses
parameter_list|(
name|String
name|query
parameter_list|,
name|Integer
name|limit
parameter_list|,
name|CourseMatcher
name|matcher
parameter_list|)
function_decl|;
specifier|public
name|Collection
argument_list|<
name|?
extends|extends
name|XCourseId
argument_list|>
name|findCourses
parameter_list|(
name|CourseMatcher
name|matcher
parameter_list|)
function_decl|;
specifier|public
name|Collection
argument_list|<
name|?
extends|extends
name|XStudentId
argument_list|>
name|findStudents
parameter_list|(
name|StudentMatcher
name|matcher
parameter_list|)
function_decl|;
specifier|public
name|XCourse
name|getCourse
parameter_list|(
name|Long
name|courseId
parameter_list|)
function_decl|;
specifier|public
name|XCourseId
name|getCourse
parameter_list|(
name|String
name|course
parameter_list|)
function_decl|;
specifier|public
name|XCourseId
name|getCourse
parameter_list|(
name|Long
name|courseId
parameter_list|,
name|String
name|courseName
parameter_list|)
function_decl|;
specifier|public
name|String
name|getCourseDetails
parameter_list|(
name|Long
name|courseId
parameter_list|,
name|CourseDetailsProvider
name|provider
parameter_list|)
function_decl|;
specifier|public
name|XStudent
name|getStudent
parameter_list|(
name|Long
name|studentId
parameter_list|)
function_decl|;
specifier|public
name|XOffering
name|getOffering
parameter_list|(
name|Long
name|offeringId
parameter_list|)
function_decl|;
specifier|public
name|Collection
argument_list|<
name|XCourseRequest
argument_list|>
name|getRequests
parameter_list|(
name|Long
name|offeringId
parameter_list|)
function_decl|;
specifier|public
name|XEnrollments
name|getEnrollments
parameter_list|(
name|Long
name|offeringId
parameter_list|)
function_decl|;
specifier|public
name|XExpectations
name|getExpectations
parameter_list|(
name|Long
name|offeringId
parameter_list|)
function_decl|;
specifier|public
name|Collection
argument_list|<
name|Long
argument_list|>
name|getInstructedOfferings
parameter_list|(
name|String
name|instructorExternalId
parameter_list|)
function_decl|;
specifier|public
name|Set
argument_list|<
name|Long
argument_list|>
name|getRequestedCourseIds
parameter_list|(
name|Long
name|studentId
parameter_list|)
function_decl|;
annotation|@
name|CheckMaster
argument_list|(
name|Master
operator|.
name|REQUIRED
argument_list|)
specifier|public
name|void
name|update
parameter_list|(
name|XExpectations
name|expectations
parameter_list|)
function_decl|;
specifier|public
parameter_list|<
name|X
extends|extends
name|OnlineSectioningAction
parameter_list|>
name|X
name|createAction
parameter_list|(
name|Class
argument_list|<
name|X
argument_list|>
name|clazz
parameter_list|)
throws|throws
name|SectioningException
function_decl|;
specifier|public
parameter_list|<
name|E
parameter_list|>
name|E
name|execute
parameter_list|(
name|OnlineSectioningAction
argument_list|<
name|E
argument_list|>
name|action
parameter_list|,
name|OnlineSectioningLog
operator|.
name|Entity
name|user
parameter_list|)
throws|throws
name|SectioningException
function_decl|;
specifier|public
parameter_list|<
name|E
parameter_list|>
name|void
name|execute
parameter_list|(
name|OnlineSectioningAction
argument_list|<
name|E
argument_list|>
name|action
parameter_list|,
name|OnlineSectioningLog
operator|.
name|Entity
name|user
parameter_list|,
name|ServerCallback
argument_list|<
name|E
argument_list|>
name|callback
parameter_list|)
throws|throws
name|SectioningException
function_decl|;
annotation|@
name|CheckMaster
argument_list|(
name|Master
operator|.
name|REQUIRED
argument_list|)
specifier|public
name|void
name|remove
parameter_list|(
name|XStudent
name|student
parameter_list|)
function_decl|;
annotation|@
name|CheckMaster
argument_list|(
name|Master
operator|.
name|REQUIRED
argument_list|)
specifier|public
name|void
name|update
parameter_list|(
name|XStudent
name|student
parameter_list|,
name|boolean
name|updateRequests
parameter_list|)
function_decl|;
annotation|@
name|CheckMaster
argument_list|(
name|Master
operator|.
name|REQUIRED
argument_list|)
specifier|public
name|void
name|remove
parameter_list|(
name|XOffering
name|offering
parameter_list|)
function_decl|;
annotation|@
name|CheckMaster
argument_list|(
name|Master
operator|.
name|REQUIRED
argument_list|)
specifier|public
name|void
name|update
parameter_list|(
name|XOffering
name|offering
parameter_list|)
function_decl|;
annotation|@
name|CheckMaster
argument_list|(
name|Master
operator|.
name|REQUIRED
argument_list|)
specifier|public
name|void
name|clearAll
parameter_list|()
function_decl|;
annotation|@
name|CheckMaster
argument_list|(
name|Master
operator|.
name|REQUIRED
argument_list|)
specifier|public
name|void
name|clearAllStudents
parameter_list|()
function_decl|;
annotation|@
name|CheckMaster
argument_list|(
name|Master
operator|.
name|REQUIRED
argument_list|)
specifier|public
name|XCourseRequest
name|assign
parameter_list|(
name|XCourseRequest
name|request
parameter_list|,
name|XEnrollment
name|enrollment
parameter_list|)
function_decl|;
annotation|@
name|CheckMaster
argument_list|(
name|Master
operator|.
name|REQUIRED
argument_list|)
specifier|public
name|XCourseRequest
name|waitlist
parameter_list|(
name|XCourseRequest
name|request
parameter_list|,
name|boolean
name|waitlist
parameter_list|)
function_decl|;
annotation|@
name|CheckMaster
argument_list|(
name|Master
operator|.
name|REQUIRED
argument_list|)
specifier|public
name|Lock
name|readLock
parameter_list|()
function_decl|;
annotation|@
name|CheckMaster
argument_list|(
name|Master
operator|.
name|REQUIRED
argument_list|)
specifier|public
name|Lock
name|writeLock
parameter_list|()
function_decl|;
annotation|@
name|CheckMaster
argument_list|(
name|Master
operator|.
name|REQUIRED
argument_list|)
specifier|public
name|Lock
name|lockAll
parameter_list|()
function_decl|;
annotation|@
name|CheckMaster
argument_list|(
name|Master
operator|.
name|REQUIRED
argument_list|)
specifier|public
name|Lock
name|lockStudent
parameter_list|(
name|Long
name|studentId
parameter_list|,
name|Collection
argument_list|<
name|Long
argument_list|>
name|offeringIds
parameter_list|,
name|String
name|actionName
parameter_list|)
function_decl|;
annotation|@
name|CheckMaster
argument_list|(
name|Master
operator|.
name|REQUIRED
argument_list|)
specifier|public
name|Lock
name|lockOffering
parameter_list|(
name|Long
name|offeringId
parameter_list|,
name|Collection
argument_list|<
name|Long
argument_list|>
name|studentIds
parameter_list|,
name|String
name|actionName
parameter_list|)
function_decl|;
annotation|@
name|CheckMaster
argument_list|(
name|Master
operator|.
name|REQUIRED
argument_list|)
specifier|public
name|Lock
name|lockRequest
parameter_list|(
name|CourseRequestInterface
name|request
parameter_list|,
name|String
name|actionName
parameter_list|)
function_decl|;
specifier|public
name|boolean
name|isOfferingLocked
parameter_list|(
name|Long
name|offeringId
parameter_list|)
function_decl|;
annotation|@
name|CheckMaster
argument_list|(
name|Master
operator|.
name|REQUIRED
argument_list|)
specifier|public
name|void
name|lockOffering
parameter_list|(
name|Long
name|offeringId
parameter_list|)
function_decl|;
annotation|@
name|CheckMaster
argument_list|(
name|Master
operator|.
name|REQUIRED
argument_list|)
specifier|public
name|void
name|unlockOffering
parameter_list|(
name|Long
name|offeringId
parameter_list|)
function_decl|;
specifier|public
name|Collection
argument_list|<
name|Long
argument_list|>
name|getLockedOfferings
parameter_list|()
function_decl|;
annotation|@
name|CheckMaster
argument_list|(
name|Master
operator|.
name|REQUIRED
argument_list|)
specifier|public
name|void
name|releaseAllOfferingLocks
parameter_list|()
function_decl|;
annotation|@
name|CheckMaster
argument_list|(
name|Master
operator|.
name|REQUIRED
argument_list|)
specifier|public
name|void
name|persistExpectedSpaces
parameter_list|(
name|Long
name|offeringId
parameter_list|)
function_decl|;
annotation|@
name|CheckMaster
argument_list|(
name|Master
operator|.
name|REQUIRED
argument_list|)
specifier|public
name|List
argument_list|<
name|Long
argument_list|>
name|getOfferingsToPersistExpectedSpaces
parameter_list|(
name|long
name|minimalAge
parameter_list|)
function_decl|;
annotation|@
name|CheckMaster
argument_list|(
name|Master
operator|.
name|REQUIRED
argument_list|)
specifier|public
name|boolean
name|needPersistExpectedSpaces
parameter_list|(
name|Long
name|offeringId
parameter_list|)
function_decl|;
annotation|@
name|CheckMaster
argument_list|(
name|Master
operator|.
name|REQUIRED
argument_list|)
specifier|public
name|boolean
name|isReady
parameter_list|()
function_decl|;
specifier|public
specifier|static
enum|enum
name|Deadline
block|{
name|NEW
block|,
name|CHANGE
block|,
name|DROP
block|}
empty_stmt|;
specifier|public
name|boolean
name|checkDeadline
parameter_list|(
name|Long
name|courseId
parameter_list|,
name|XTime
name|sectionTime
parameter_list|,
name|Deadline
name|type
parameter_list|)
function_decl|;
specifier|public
name|CourseDeadlines
name|getCourseDeadlines
parameter_list|(
name|Long
name|courseId
parameter_list|)
function_decl|;
specifier|public
name|void
name|unload
parameter_list|()
function_decl|;
specifier|public
name|long
name|getMemUsage
parameter_list|()
function_decl|;
specifier|public
parameter_list|<
name|E
parameter_list|>
name|E
name|getProperty
parameter_list|(
name|String
name|name
parameter_list|,
name|E
name|defaultValue
parameter_list|)
function_decl|;
specifier|public
parameter_list|<
name|E
parameter_list|>
name|void
name|setProperty
parameter_list|(
name|String
name|name
parameter_list|,
name|E
name|value
parameter_list|)
function_decl|;
specifier|public
specifier|static
interface|interface
name|Lock
block|{
name|void
name|release
parameter_list|()
function_decl|;
block|}
specifier|public
specifier|static
interface|interface
name|ServerCallback
parameter_list|<
name|E
parameter_list|>
block|{
specifier|public
name|void
name|onFailure
parameter_list|(
name|Throwable
name|exception
parameter_list|)
function_decl|;
specifier|public
name|void
name|onSuccess
parameter_list|(
name|E
name|result
parameter_list|)
function_decl|;
block|}
specifier|public
specifier|static
interface|interface
name|CourseDeadlines
extends|extends
name|Serializable
block|{
specifier|public
name|boolean
name|isEnabled
parameter_list|()
function_decl|;
specifier|public
name|boolean
name|checkDeadline
parameter_list|(
name|XTime
name|sectionTime
parameter_list|,
name|Deadline
name|type
parameter_list|)
function_decl|;
block|}
block|}
end_interface

end_unit

