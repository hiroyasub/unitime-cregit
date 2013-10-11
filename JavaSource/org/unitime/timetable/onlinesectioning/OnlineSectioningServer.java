begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 (University Timetabling Application)  * Copyright (C) 2011, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|XDistribution
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

begin_import
import|import
name|net
operator|.
name|sf
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
name|net
operator|.
name|sf
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
name|void
name|addDistribution
parameter_list|(
name|XDistribution
name|distribution
parameter_list|)
function_decl|;
specifier|public
name|Collection
argument_list|<
name|XDistribution
argument_list|>
name|getDistributions
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
name|boolean
name|excludeLockedOfferings
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
name|boolean
name|excludeLockedOffering
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
name|void
name|unload
parameter_list|(
name|boolean
name|remove
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
block|}
end_interface

end_unit

