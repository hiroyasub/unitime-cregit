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
name|net
operator|.
name|URL
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
name|SectioningException
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

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|cpsolver
operator|.
name|studentsct
operator|.
name|model
operator|.
name|Course
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
name|studentsct
operator|.
name|model
operator|.
name|Enrollment
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
name|studentsct
operator|.
name|model
operator|.
name|Offering
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
name|studentsct
operator|.
name|model
operator|.
name|Request
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
name|studentsct
operator|.
name|model
operator|.
name|Section
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
name|studentsct
operator|.
name|model
operator|.
name|Student
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
name|ClassAssignmentInterface
name|getAssignment
parameter_list|(
name|Long
name|studentId
parameter_list|)
function_decl|;
specifier|public
name|CourseRequestInterface
name|getRequest
parameter_list|(
name|Long
name|studentId
parameter_list|)
function_decl|;
specifier|public
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
function_decl|;
specifier|public
name|Collection
argument_list|<
name|CourseInfo
argument_list|>
name|findCourses
parameter_list|(
name|String
name|query
parameter_list|,
name|Integer
name|limit
parameter_list|)
function_decl|;
specifier|public
name|List
argument_list|<
name|Section
argument_list|>
name|getSections
parameter_list|(
name|CourseInfo
name|courseInfo
parameter_list|)
function_decl|;
specifier|public
name|CourseInfo
name|getCourseInfo
parameter_list|(
name|Long
name|courseId
parameter_list|)
function_decl|;
specifier|public
name|CourseInfo
name|getCourseInfo
parameter_list|(
name|String
name|course
parameter_list|)
function_decl|;
specifier|public
name|Student
name|getStudent
parameter_list|(
name|Long
name|studentId
parameter_list|)
function_decl|;
specifier|public
name|Section
name|getSection
parameter_list|(
name|Long
name|classId
parameter_list|)
function_decl|;
specifier|public
name|Course
name|getCourse
parameter_list|(
name|Long
name|courseId
parameter_list|)
function_decl|;
specifier|public
name|Offering
name|getOffering
parameter_list|(
name|Long
name|offeringId
parameter_list|)
function_decl|;
specifier|public
name|URL
name|getSectionUrl
parameter_list|(
name|Long
name|courseId
parameter_list|,
name|Section
name|section
parameter_list|)
function_decl|;
specifier|public
name|Collection
argument_list|<
name|String
argument_list|>
name|checkCourses
parameter_list|(
name|CourseRequestInterface
name|req
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
name|Callback
argument_list|<
name|E
argument_list|>
name|callback
parameter_list|)
throws|throws
name|SectioningException
function_decl|;
specifier|public
name|void
name|remove
parameter_list|(
name|Student
name|student
parameter_list|)
function_decl|;
specifier|public
name|void
name|update
parameter_list|(
name|Student
name|student
parameter_list|)
function_decl|;
specifier|public
name|void
name|remove
parameter_list|(
name|Offering
name|offering
parameter_list|)
function_decl|;
specifier|public
name|void
name|update
parameter_list|(
name|Offering
name|offering
parameter_list|)
function_decl|;
specifier|public
name|void
name|update
parameter_list|(
name|CourseInfo
name|info
parameter_list|)
function_decl|;
specifier|public
name|void
name|clearAll
parameter_list|()
function_decl|;
specifier|public
name|void
name|clearAllStudents
parameter_list|()
function_decl|;
specifier|public
name|void
name|notifyStudentChanged
parameter_list|(
name|Long
name|studentId
parameter_list|,
name|List
argument_list|<
name|Request
argument_list|>
name|oldRequests
parameter_list|,
name|List
argument_list|<
name|Request
argument_list|>
name|newRequests
parameter_list|)
function_decl|;
specifier|public
name|void
name|notifyStudentChanged
parameter_list|(
name|Long
name|studentId
parameter_list|,
name|Request
name|request
parameter_list|,
name|Enrollment
name|oldEnrollment
parameter_list|)
function_decl|;
specifier|public
name|Lock
name|readLock
parameter_list|()
function_decl|;
specifier|public
name|Lock
name|writeLock
parameter_list|()
function_decl|;
specifier|public
name|Lock
name|lockAll
parameter_list|()
function_decl|;
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
specifier|public
name|Lock
name|lockClass
parameter_list|(
name|Long
name|classId
parameter_list|,
name|Collection
argument_list|<
name|Long
argument_list|>
name|studentIds
parameter_list|)
function_decl|;
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
specifier|public
name|void
name|lockOffering
parameter_list|(
name|Long
name|offeringId
parameter_list|)
function_decl|;
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
specifier|public
name|void
name|releaseAllOfferingLocks
parameter_list|()
function_decl|;
specifier|public
name|int
name|distance
parameter_list|(
name|Section
name|s1
parameter_list|,
name|Section
name|s2
parameter_list|)
function_decl|;
specifier|public
name|void
name|unload
parameter_list|()
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
name|Callback
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

