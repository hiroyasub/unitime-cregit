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
name|solver
operator|.
name|studentsct
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
name|Collections
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
name|HashMap
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
name|java
operator|.
name|util
operator|.
name|concurrent
operator|.
name|atomic
operator|.
name|AtomicLong
import|;
end_import

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|coursett
operator|.
name|model
operator|.
name|TimeLocation
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
name|assignment
operator|.
name|Assignment
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
name|StudentSectioningModel
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
name|model
operator|.
name|Choice
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
name|model
operator|.
name|Config
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
name|model
operator|.
name|Course
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
name|model
operator|.
name|CourseRequest
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
name|model
operator|.
name|Enrollment
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
name|model
operator|.
name|FreeTimeRequest
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
name|model
operator|.
name|Offering
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
name|model
operator|.
name|Request
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
name|model
operator|.
name|SctAssignment
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
name|model
operator|.
name|Section
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
name|model
operator|.
name|Student
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
name|model
operator|.
name|Subpart
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
name|basic
operator|.
name|GetAssignment
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
name|StudentEnrollmentProvider
operator|.
name|EnrollmentFailure
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
name|updates
operator|.
name|EnrollStudent
import|;
end_import

begin_class
specifier|public
class|class
name|BatchEnrollStudent
extends|extends
name|EnrollStudent
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
specifier|private
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
specifier|static
name|AtomicLong
name|sLastGeneratedId
init|=
operator|new
name|AtomicLong
argument_list|(
operator|-
literal|1l
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|sRequestsChangedStatus
init|=
literal|"Modified"
decl_stmt|;
annotation|@
name|Override
specifier|public
name|ClassAssignmentInterface
name|execute
parameter_list|(
name|OnlineSectioningServer
name|server
parameter_list|,
specifier|final
name|OnlineSectioningHelper
name|helper
parameter_list|)
block|{
if|if
condition|(
name|getRequest
argument_list|()
operator|.
name|getStudentId
argument_list|()
operator|==
literal|null
condition|)
throw|throw
operator|new
name|SectioningException
argument_list|(
name|MSG
operator|.
name|exceptionNoStudent
argument_list|()
argument_list|)
throw|;
name|StudentSolver
name|solver
init|=
operator|(
name|StudentSolver
operator|)
name|server
decl_stmt|;
if|if
condition|(
name|solver
operator|.
name|isPublished
argument_list|()
condition|)
throw|throw
operator|new
name|SectioningException
argument_list|(
name|MSG
operator|.
name|exceptionSolverPublished
argument_list|()
argument_list|)
throw|;
name|StudentSectioningModel
name|model
init|=
operator|(
name|StudentSectioningModel
operator|)
name|solver
operator|.
name|currentSolution
argument_list|()
operator|.
name|getModel
argument_list|()
decl_stmt|;
name|Assignment
argument_list|<
name|Request
argument_list|,
name|Enrollment
argument_list|>
name|assignment
init|=
name|solver
operator|.
name|currentSolution
argument_list|()
operator|.
name|getAssignment
argument_list|()
decl_stmt|;
name|Student
name|student
init|=
literal|null
decl_stmt|;
for|for
control|(
name|Student
name|s
range|:
name|model
operator|.
name|getStudents
argument_list|()
control|)
block|{
if|if
condition|(
name|s
operator|.
name|getId
argument_list|()
operator|==
name|getRequest
argument_list|()
operator|.
name|getStudentId
argument_list|()
condition|)
block|{
name|student
operator|=
name|s
expr_stmt|;
break|break;
block|}
block|}
if|if
condition|(
name|student
operator|==
literal|null
condition|)
throw|throw
operator|new
name|SectioningException
argument_list|(
name|MSG
operator|.
name|exceptionBadStudentId
argument_list|()
argument_list|)
throw|;
name|List
argument_list|<
name|EnrollmentFailure
argument_list|>
name|failures
init|=
operator|new
name|ArrayList
argument_list|<
name|EnrollmentFailure
argument_list|>
argument_list|()
decl_stmt|;
if|if
condition|(
name|solver
operator|.
name|getConfig
argument_list|()
operator|.
name|getPropertyBoolean
argument_list|(
literal|"Interactive.UpdateCourseRequests"
argument_list|,
literal|true
argument_list|)
condition|)
block|{
name|List
argument_list|<
name|Request
argument_list|>
name|remaining
init|=
operator|new
name|ArrayList
argument_list|<
name|Request
argument_list|>
argument_list|(
name|student
operator|.
name|getRequests
argument_list|()
argument_list|)
decl_stmt|;
name|int
name|priority
init|=
literal|0
decl_stmt|;
name|Long
name|ts
init|=
operator|new
name|Date
argument_list|()
operator|.
name|getTime
argument_list|()
decl_stmt|;
name|boolean
name|changed
init|=
literal|false
decl_stmt|;
for|for
control|(
name|CourseRequestInterface
operator|.
name|Request
name|r
range|:
name|getRequest
argument_list|()
operator|.
name|getCourses
argument_list|()
control|)
block|{
name|List
argument_list|<
name|Course
argument_list|>
name|courses
init|=
operator|new
name|ArrayList
argument_list|<
name|Course
argument_list|>
argument_list|()
decl_stmt|;
name|Set
argument_list|<
name|Choice
argument_list|>
name|selChoices
init|=
operator|new
name|HashSet
argument_list|<
name|Choice
argument_list|>
argument_list|()
decl_stmt|;
name|Set
argument_list|<
name|Choice
argument_list|>
name|reqChoices
init|=
operator|new
name|HashSet
argument_list|<
name|Choice
argument_list|>
argument_list|()
decl_stmt|;
if|if
condition|(
name|r
operator|.
name|hasRequestedCourse
argument_list|()
condition|)
block|{
for|for
control|(
name|RequestedCourse
name|rc
range|:
name|r
operator|.
name|getRequestedCourse
argument_list|()
control|)
block|{
if|if
condition|(
name|rc
operator|.
name|isFreeTime
argument_list|()
condition|)
block|{
for|for
control|(
name|CourseRequestInterface
operator|.
name|FreeTime
name|ft
range|:
name|rc
operator|.
name|getFreeTime
argument_list|()
control|)
block|{
name|TimeLocation
name|time
init|=
operator|new
name|TimeLocation
argument_list|(
name|DayCode
operator|.
name|toInt
argument_list|(
name|DayCode
operator|.
name|toDayCodes
argument_list|(
name|ft
operator|.
name|getDays
argument_list|()
argument_list|)
argument_list|)
argument_list|,
name|ft
operator|.
name|getStart
argument_list|()
argument_list|,
name|ft
operator|.
name|getLength
argument_list|()
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|,
operator|-
literal|1l
argument_list|,
literal|""
argument_list|,
name|server
operator|.
name|getAcademicSession
argument_list|()
operator|.
name|getFreeTimePattern
argument_list|()
argument_list|,
literal|0
argument_list|)
decl_stmt|;
name|FreeTimeRequest
name|freeTimeRequest
init|=
literal|null
decl_stmt|;
for|for
control|(
name|Iterator
argument_list|<
name|Request
argument_list|>
name|i
init|=
name|remaining
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Request
name|adept
init|=
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|adept
operator|instanceof
name|FreeTimeRequest
operator|&&
operator|!
name|adept
operator|.
name|isAlternative
argument_list|()
condition|)
block|{
name|FreeTimeRequest
name|f
init|=
operator|(
name|FreeTimeRequest
operator|)
name|adept
decl_stmt|;
if|if
condition|(
name|f
operator|.
name|getTime
argument_list|()
operator|.
name|equals
argument_list|(
name|time
argument_list|)
condition|)
block|{
name|freeTimeRequest
operator|=
name|f
expr_stmt|;
name|i
operator|.
name|remove
argument_list|()
expr_stmt|;
break|break;
block|}
block|}
block|}
if|if
condition|(
name|freeTimeRequest
operator|==
literal|null
condition|)
block|{
name|freeTimeRequest
operator|=
operator|new
name|FreeTimeRequest
argument_list|(
name|sLastGeneratedId
operator|.
name|getAndDecrement
argument_list|()
argument_list|,
name|priority
argument_list|,
literal|false
argument_list|,
name|student
argument_list|,
name|time
argument_list|)
expr_stmt|;
name|model
operator|.
name|addVariable
argument_list|(
name|freeTimeRequest
argument_list|)
expr_stmt|;
name|changed
operator|=
literal|true
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|freeTimeRequest
operator|.
name|getPriority
argument_list|()
operator|!=
name|priority
condition|)
block|{
name|freeTimeRequest
operator|.
name|setPriority
argument_list|(
name|priority
argument_list|)
expr_stmt|;
name|changed
operator|=
literal|true
expr_stmt|;
block|}
block|}
block|}
name|priority
operator|++
expr_stmt|;
block|}
if|else if
condition|(
name|rc
operator|.
name|isCourse
argument_list|()
condition|)
block|{
name|Course
name|c
init|=
name|getCourse
argument_list|(
name|model
argument_list|,
name|rc
operator|.
name|getCourseId
argument_list|()
argument_list|,
name|rc
operator|.
name|getCourseName
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|c
operator|!=
literal|null
condition|)
block|{
name|courses
operator|.
name|add
argument_list|(
name|c
argument_list|)
expr_stmt|;
if|if
condition|(
name|rc
operator|.
name|hasSelectedIntructionalMethods
argument_list|()
condition|)
block|{
for|for
control|(
name|Config
name|config
range|:
name|c
operator|.
name|getOffering
argument_list|()
operator|.
name|getConfigs
argument_list|()
control|)
if|if
condition|(
name|config
operator|.
name|getInstructionalMethodId
argument_list|()
operator|!=
literal|null
operator|&&
name|rc
operator|.
name|isSelectedIntructionalMethod
argument_list|(
name|config
operator|.
name|getInstructionalMethodId
argument_list|()
argument_list|)
condition|)
operator|(
name|rc
operator|.
name|isSelectedIntructionalMethod
argument_list|(
name|config
operator|.
name|getInstructionalMethodId
argument_list|()
argument_list|,
literal|true
argument_list|)
condition|?
name|reqChoices
else|:
name|selChoices
operator|)
operator|.
name|add
argument_list|(
operator|new
name|Choice
argument_list|(
name|config
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|rc
operator|.
name|hasSelectedClasses
argument_list|()
condition|)
block|{
for|for
control|(
name|Config
name|config
range|:
name|c
operator|.
name|getOffering
argument_list|()
operator|.
name|getConfigs
argument_list|()
control|)
for|for
control|(
name|Subpart
name|subpart
range|:
name|config
operator|.
name|getSubparts
argument_list|()
control|)
for|for
control|(
name|Section
name|section
range|:
name|subpart
operator|.
name|getSections
argument_list|()
control|)
if|if
condition|(
name|rc
operator|.
name|isSelectedClass
argument_list|(
name|section
operator|.
name|getId
argument_list|()
argument_list|)
condition|)
block|{
if|if
condition|(
name|rc
operator|.
name|isSelectedClass
argument_list|(
name|section
operator|.
name|getId
argument_list|()
argument_list|,
literal|true
argument_list|)
condition|)
block|{
name|reqChoices
operator|.
name|add
argument_list|(
operator|new
name|Choice
argument_list|(
name|section
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|selChoices
operator|.
name|add
argument_list|(
operator|new
name|Choice
argument_list|(
name|section
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
block|}
block|}
block|}
if|if
condition|(
name|courses
operator|.
name|isEmpty
argument_list|()
condition|)
continue|continue;
name|CourseRequest
name|courseRequest
init|=
literal|null
decl_stmt|;
for|for
control|(
name|Iterator
argument_list|<
name|Request
argument_list|>
name|i
init|=
name|remaining
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Request
name|adept
init|=
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|adept
operator|instanceof
name|CourseRequest
operator|&&
operator|!
name|adept
operator|.
name|isAlternative
argument_list|()
condition|)
block|{
name|CourseRequest
name|cr
init|=
operator|(
name|CourseRequest
operator|)
name|adept
decl_stmt|;
if|if
condition|(
name|cr
operator|.
name|getCourses
argument_list|()
operator|.
name|equals
argument_list|(
name|courses
argument_list|)
condition|)
block|{
name|courseRequest
operator|=
name|cr
expr_stmt|;
name|i
operator|.
name|remove
argument_list|()
expr_stmt|;
break|break;
block|}
block|}
block|}
if|if
condition|(
name|courseRequest
operator|==
literal|null
condition|)
block|{
name|courseRequest
operator|=
operator|new
name|CourseRequest
argument_list|(
name|sLastGeneratedId
operator|.
name|getAndDecrement
argument_list|()
argument_list|,
name|priority
argument_list|,
literal|false
argument_list|,
name|student
argument_list|,
name|courses
argument_list|,
name|r
operator|.
name|isWaitList
argument_list|()
operator|||
name|r
operator|.
name|isNoSub
argument_list|()
argument_list|,
name|ts
argument_list|)
expr_stmt|;
name|model
operator|.
name|addVariable
argument_list|(
name|courseRequest
argument_list|)
expr_stmt|;
name|changed
operator|=
literal|true
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|courseRequest
operator|.
name|getPriority
argument_list|()
operator|!=
name|priority
condition|)
block|{
name|courseRequest
operator|.
name|setPriority
argument_list|(
name|priority
argument_list|)
expr_stmt|;
name|changed
operator|=
literal|true
expr_stmt|;
block|}
if|if
condition|(
name|courseRequest
operator|.
name|isWaitlist
argument_list|()
operator|!=
operator|(
name|r
operator|.
name|isWaitList
argument_list|()
operator|||
name|r
operator|.
name|isNoSub
argument_list|()
operator|)
condition|)
block|{
name|courseRequest
operator|.
name|setWaitlist
argument_list|(
name|r
operator|.
name|isWaitList
argument_list|()
operator|||
name|r
operator|.
name|isNoSub
argument_list|()
argument_list|)
expr_stmt|;
name|changed
operator|=
literal|true
expr_stmt|;
block|}
block|}
if|if
condition|(
operator|!
name|courseRequest
operator|.
name|getSelectedChoices
argument_list|()
operator|.
name|equals
argument_list|(
name|selChoices
argument_list|)
condition|)
block|{
name|courseRequest
operator|.
name|getSelectedChoices
argument_list|()
operator|.
name|clear
argument_list|()
expr_stmt|;
name|courseRequest
operator|.
name|getSelectedChoices
argument_list|()
operator|.
name|addAll
argument_list|(
name|selChoices
argument_list|)
expr_stmt|;
name|changed
operator|=
literal|true
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|courseRequest
operator|.
name|getRequiredChoices
argument_list|()
operator|.
name|equals
argument_list|(
name|reqChoices
argument_list|)
condition|)
block|{
name|courseRequest
operator|.
name|getRequiredChoices
argument_list|()
operator|.
name|clear
argument_list|()
expr_stmt|;
name|courseRequest
operator|.
name|getRequiredChoices
argument_list|()
operator|.
name|addAll
argument_list|(
name|reqChoices
argument_list|)
expr_stmt|;
name|changed
operator|=
literal|true
expr_stmt|;
block|}
name|priority
operator|++
expr_stmt|;
block|}
for|for
control|(
name|CourseRequestInterface
operator|.
name|Request
name|r
range|:
name|getRequest
argument_list|()
operator|.
name|getAlternatives
argument_list|()
control|)
block|{
name|List
argument_list|<
name|Course
argument_list|>
name|courses
init|=
operator|new
name|ArrayList
argument_list|<
name|Course
argument_list|>
argument_list|()
decl_stmt|;
name|Set
argument_list|<
name|Choice
argument_list|>
name|selChoices
init|=
operator|new
name|HashSet
argument_list|<
name|Choice
argument_list|>
argument_list|()
decl_stmt|;
name|Set
argument_list|<
name|Choice
argument_list|>
name|reqChoices
init|=
operator|new
name|HashSet
argument_list|<
name|Choice
argument_list|>
argument_list|()
decl_stmt|;
if|if
condition|(
name|r
operator|.
name|hasRequestedCourse
argument_list|()
condition|)
block|{
for|for
control|(
name|RequestedCourse
name|rc
range|:
name|r
operator|.
name|getRequestedCourse
argument_list|()
control|)
block|{
if|if
condition|(
name|rc
operator|.
name|isFreeTime
argument_list|()
condition|)
block|{
for|for
control|(
name|CourseRequestInterface
operator|.
name|FreeTime
name|ft
range|:
name|rc
operator|.
name|getFreeTime
argument_list|()
control|)
block|{
name|TimeLocation
name|time
init|=
operator|new
name|TimeLocation
argument_list|(
name|DayCode
operator|.
name|toInt
argument_list|(
name|DayCode
operator|.
name|toDayCodes
argument_list|(
name|ft
operator|.
name|getDays
argument_list|()
argument_list|)
argument_list|)
argument_list|,
name|ft
operator|.
name|getStart
argument_list|()
argument_list|,
name|ft
operator|.
name|getLength
argument_list|()
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|,
operator|-
literal|1l
argument_list|,
literal|""
argument_list|,
name|server
operator|.
name|getAcademicSession
argument_list|()
operator|.
name|getFreeTimePattern
argument_list|()
argument_list|,
literal|0
argument_list|)
decl_stmt|;
name|FreeTimeRequest
name|freeTimeRequest
init|=
literal|null
decl_stmt|;
for|for
control|(
name|Iterator
argument_list|<
name|Request
argument_list|>
name|i
init|=
name|remaining
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Request
name|adept
init|=
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|adept
operator|instanceof
name|FreeTimeRequest
operator|&&
name|adept
operator|.
name|isAlternative
argument_list|()
condition|)
block|{
name|FreeTimeRequest
name|f
init|=
operator|(
name|FreeTimeRequest
operator|)
name|adept
decl_stmt|;
if|if
condition|(
name|f
operator|.
name|getTime
argument_list|()
operator|.
name|equals
argument_list|(
name|time
argument_list|)
condition|)
block|{
name|freeTimeRequest
operator|=
name|f
expr_stmt|;
name|i
operator|.
name|remove
argument_list|()
expr_stmt|;
break|break;
block|}
block|}
block|}
if|if
condition|(
name|freeTimeRequest
operator|==
literal|null
condition|)
block|{
name|freeTimeRequest
operator|=
operator|new
name|FreeTimeRequest
argument_list|(
name|sLastGeneratedId
operator|.
name|getAndDecrement
argument_list|()
argument_list|,
name|priority
argument_list|,
literal|true
argument_list|,
name|student
argument_list|,
name|time
argument_list|)
expr_stmt|;
name|model
operator|.
name|addVariable
argument_list|(
name|freeTimeRequest
argument_list|)
expr_stmt|;
name|changed
operator|=
literal|true
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|freeTimeRequest
operator|.
name|getPriority
argument_list|()
operator|!=
name|priority
condition|)
block|{
name|freeTimeRequest
operator|.
name|setPriority
argument_list|(
name|priority
argument_list|)
expr_stmt|;
name|changed
operator|=
literal|true
expr_stmt|;
block|}
block|}
block|}
name|priority
operator|++
expr_stmt|;
block|}
if|else if
condition|(
name|rc
operator|.
name|isCourse
argument_list|()
condition|)
block|{
name|Course
name|c
init|=
name|getCourse
argument_list|(
name|model
argument_list|,
name|rc
operator|.
name|getCourseId
argument_list|()
argument_list|,
name|rc
operator|.
name|getCourseName
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|c
operator|!=
literal|null
condition|)
block|{
name|courses
operator|.
name|add
argument_list|(
name|c
argument_list|)
expr_stmt|;
if|if
condition|(
name|rc
operator|.
name|hasSelectedIntructionalMethods
argument_list|()
condition|)
block|{
for|for
control|(
name|Config
name|config
range|:
name|c
operator|.
name|getOffering
argument_list|()
operator|.
name|getConfigs
argument_list|()
control|)
if|if
condition|(
name|config
operator|.
name|getInstructionalMethodId
argument_list|()
operator|!=
literal|null
operator|&&
name|rc
operator|.
name|isSelectedIntructionalMethod
argument_list|(
name|config
operator|.
name|getInstructionalMethodId
argument_list|()
argument_list|)
condition|)
operator|(
name|rc
operator|.
name|isSelectedIntructionalMethod
argument_list|(
name|config
operator|.
name|getInstructionalMethodId
argument_list|()
argument_list|,
literal|true
argument_list|)
condition|?
name|reqChoices
else|:
name|selChoices
operator|)
operator|.
name|add
argument_list|(
operator|new
name|Choice
argument_list|(
name|config
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|rc
operator|.
name|hasSelectedClasses
argument_list|()
condition|)
block|{
for|for
control|(
name|Config
name|config
range|:
name|c
operator|.
name|getOffering
argument_list|()
operator|.
name|getConfigs
argument_list|()
control|)
for|for
control|(
name|Subpart
name|subpart
range|:
name|config
operator|.
name|getSubparts
argument_list|()
control|)
for|for
control|(
name|Section
name|section
range|:
name|subpart
operator|.
name|getSections
argument_list|()
control|)
if|if
condition|(
name|rc
operator|.
name|isSelectedClass
argument_list|(
name|section
operator|.
name|getId
argument_list|()
argument_list|)
condition|)
block|{
if|if
condition|(
name|rc
operator|.
name|isSelectedClass
argument_list|(
name|section
operator|.
name|getId
argument_list|()
argument_list|,
literal|true
argument_list|)
condition|)
block|{
name|reqChoices
operator|.
name|add
argument_list|(
operator|new
name|Choice
argument_list|(
name|section
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|selChoices
operator|.
name|add
argument_list|(
operator|new
name|Choice
argument_list|(
name|section
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
block|}
block|}
block|}
if|if
condition|(
name|courses
operator|.
name|isEmpty
argument_list|()
condition|)
continue|continue;
name|CourseRequest
name|courseRequest
init|=
literal|null
decl_stmt|;
for|for
control|(
name|Iterator
argument_list|<
name|Request
argument_list|>
name|i
init|=
name|remaining
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Request
name|adept
init|=
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|adept
operator|instanceof
name|CourseRequest
operator|&&
name|adept
operator|.
name|isAlternative
argument_list|()
condition|)
block|{
name|CourseRequest
name|cr
init|=
operator|(
name|CourseRequest
operator|)
name|adept
decl_stmt|;
if|if
condition|(
name|cr
operator|.
name|getCourses
argument_list|()
operator|.
name|equals
argument_list|(
name|courses
argument_list|)
condition|)
block|{
name|courseRequest
operator|=
name|cr
expr_stmt|;
name|i
operator|.
name|remove
argument_list|()
expr_stmt|;
break|break;
block|}
block|}
block|}
if|if
condition|(
name|courseRequest
operator|==
literal|null
condition|)
block|{
name|courseRequest
operator|=
operator|new
name|CourseRequest
argument_list|(
name|sLastGeneratedId
operator|.
name|getAndDecrement
argument_list|()
argument_list|,
name|priority
argument_list|,
literal|true
argument_list|,
name|student
argument_list|,
name|courses
argument_list|,
name|r
operator|.
name|isWaitList
argument_list|()
argument_list|,
name|ts
argument_list|)
expr_stmt|;
name|model
operator|.
name|addVariable
argument_list|(
name|courseRequest
argument_list|)
expr_stmt|;
name|changed
operator|=
literal|true
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|courseRequest
operator|.
name|getPriority
argument_list|()
operator|!=
name|priority
condition|)
block|{
name|courseRequest
operator|.
name|setPriority
argument_list|(
name|priority
argument_list|)
expr_stmt|;
name|changed
operator|=
literal|true
expr_stmt|;
block|}
block|}
if|if
condition|(
operator|!
name|courseRequest
operator|.
name|getSelectedChoices
argument_list|()
operator|.
name|equals
argument_list|(
name|selChoices
argument_list|)
condition|)
block|{
name|courseRequest
operator|.
name|getSelectedChoices
argument_list|()
operator|.
name|clear
argument_list|()
expr_stmt|;
name|courseRequest
operator|.
name|getSelectedChoices
argument_list|()
operator|.
name|addAll
argument_list|(
name|selChoices
argument_list|)
expr_stmt|;
name|changed
operator|=
literal|true
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|courseRequest
operator|.
name|getRequiredChoices
argument_list|()
operator|.
name|equals
argument_list|(
name|reqChoices
argument_list|)
condition|)
block|{
name|courseRequest
operator|.
name|getRequiredChoices
argument_list|()
operator|.
name|clear
argument_list|()
expr_stmt|;
name|courseRequest
operator|.
name|getRequiredChoices
argument_list|()
operator|.
name|addAll
argument_list|(
name|reqChoices
argument_list|)
expr_stmt|;
name|changed
operator|=
literal|true
expr_stmt|;
block|}
name|priority
operator|++
expr_stmt|;
block|}
for|for
control|(
name|Request
name|request
range|:
name|remaining
control|)
block|{
name|Enrollment
name|enrollment
init|=
name|assignment
operator|.
name|getValue
argument_list|(
name|request
argument_list|)
decl_stmt|;
if|if
condition|(
name|enrollment
operator|!=
literal|null
condition|)
name|assignment
operator|.
name|unassign
argument_list|(
literal|0l
argument_list|,
name|request
argument_list|)
expr_stmt|;
name|student
operator|.
name|getRequests
argument_list|()
operator|.
name|remove
argument_list|(
name|request
argument_list|)
expr_stmt|;
name|model
operator|.
name|removeVariable
argument_list|(
name|request
argument_list|)
expr_stmt|;
name|changed
operator|=
literal|true
expr_stmt|;
block|}
if|if
condition|(
name|changed
condition|)
block|{
name|Collections
operator|.
name|sort
argument_list|(
name|student
operator|.
name|getRequests
argument_list|()
argument_list|)
expr_stmt|;
name|student
operator|.
name|setStatus
argument_list|(
name|sRequestsChangedStatus
argument_list|)
expr_stmt|;
block|}
block|}
name|Map
argument_list|<
name|CourseRequest
argument_list|,
name|List
argument_list|<
name|Section
argument_list|>
argument_list|>
name|enrollments
init|=
operator|new
name|HashMap
argument_list|<
name|CourseRequest
argument_list|,
name|List
argument_list|<
name|Section
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
name|assignments
label|:
for|for
control|(
name|ClassAssignmentInterface
operator|.
name|ClassAssignment
name|a
range|:
name|getAssignment
argument_list|()
control|)
block|{
if|if
condition|(
name|a
operator|!=
literal|null
operator|&&
name|a
operator|.
name|getCourseId
argument_list|()
operator|!=
literal|null
operator|&&
name|a
operator|.
name|getClassId
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|CourseRequest
name|request
init|=
literal|null
decl_stmt|;
name|Course
name|course
init|=
literal|null
decl_stmt|;
name|requests
label|:
for|for
control|(
name|Request
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
name|CourseRequest
condition|)
block|{
for|for
control|(
name|Course
name|c
range|:
operator|(
operator|(
name|CourseRequest
operator|)
name|r
operator|)
operator|.
name|getCourses
argument_list|()
control|)
block|{
if|if
condition|(
name|c
operator|.
name|getId
argument_list|()
operator|==
name|a
operator|.
name|getCourseId
argument_list|()
condition|)
block|{
name|course
operator|=
name|c
expr_stmt|;
name|request
operator|=
operator|(
name|CourseRequest
operator|)
name|r
expr_stmt|;
break|break
name|requests
break|;
block|}
block|}
block|}
block|}
if|if
condition|(
name|request
operator|==
literal|null
condition|)
block|{
name|XCourse
name|c
init|=
name|server
operator|.
name|getCourse
argument_list|(
name|a
operator|.
name|getCourseId
argument_list|()
argument_list|)
decl_stmt|;
name|XOffering
name|offering
init|=
name|server
operator|.
name|getOffering
argument_list|(
name|c
operator|.
name|getOfferingId
argument_list|()
argument_list|)
decl_stmt|;
name|failures
operator|.
name|add
argument_list|(
operator|new
name|EnrollmentFailure
argument_list|(
name|c
argument_list|,
name|offering
operator|.
name|getSection
argument_list|(
name|a
operator|.
name|getClassId
argument_list|()
argument_list|)
argument_list|,
literal|"Adding courses is not supported at the moment."
argument_list|,
literal|false
argument_list|)
argument_list|)
expr_stmt|;
continue|continue
name|assignments
continue|;
block|}
name|Section
name|section
init|=
name|course
operator|.
name|getOffering
argument_list|()
operator|.
name|getSection
argument_list|(
name|a
operator|.
name|getClassId
argument_list|()
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|Section
argument_list|>
name|sections
init|=
name|enrollments
operator|.
name|get
argument_list|(
name|request
argument_list|)
decl_stmt|;
if|if
condition|(
name|sections
operator|==
literal|null
condition|)
block|{
name|sections
operator|=
operator|new
name|ArrayList
argument_list|<
name|Section
argument_list|>
argument_list|()
expr_stmt|;
name|enrollments
operator|.
name|put
argument_list|(
name|request
argument_list|,
name|sections
argument_list|)
expr_stmt|;
block|}
name|sections
operator|.
name|add
argument_list|(
name|section
argument_list|)
expr_stmt|;
block|}
block|}
for|for
control|(
name|Request
name|request
range|:
name|student
operator|.
name|getRequests
argument_list|()
control|)
block|{
name|Enrollment
name|enrollment
init|=
name|assignment
operator|.
name|getValue
argument_list|(
name|request
argument_list|)
decl_stmt|;
if|if
condition|(
name|enrollment
operator|!=
literal|null
condition|)
name|assignment
operator|.
name|unassign
argument_list|(
literal|0l
argument_list|,
name|request
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|Request
name|request
range|:
name|student
operator|.
name|getRequests
argument_list|()
control|)
block|{
if|if
condition|(
name|request
operator|instanceof
name|CourseRequest
condition|)
block|{
name|CourseRequest
name|cr
init|=
operator|(
name|CourseRequest
operator|)
name|request
decl_stmt|;
name|List
argument_list|<
name|Section
argument_list|>
name|sections
init|=
name|enrollments
operator|.
name|get
argument_list|(
name|cr
argument_list|)
decl_stmt|;
if|if
condition|(
name|sections
operator|!=
literal|null
condition|)
block|{
name|Section
name|section
init|=
name|sections
operator|.
name|get
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|int
name|pririty
init|=
literal|0
decl_stmt|;
name|Config
name|config
init|=
name|section
operator|.
name|getSubpart
argument_list|()
operator|.
name|getConfig
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|cr
operator|.
name|getCourses
argument_list|()
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|cr
operator|.
name|getCourses
argument_list|()
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|.
name|getOffering
argument_list|()
operator|.
name|equals
argument_list|(
name|config
operator|.
name|getOffering
argument_list|()
argument_list|)
condition|)
block|{
name|pririty
operator|=
name|i
expr_stmt|;
break|break;
block|}
block|}
name|assignment
operator|.
name|assign
argument_list|(
literal|0l
argument_list|,
operator|new
name|Enrollment
argument_list|(
name|cr
argument_list|,
name|pririty
argument_list|,
name|config
argument_list|,
operator|new
name|HashSet
argument_list|<
name|SctAssignment
argument_list|>
argument_list|(
name|sections
argument_list|)
argument_list|,
name|assignment
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|FreeTimeRequest
name|ft
init|=
operator|(
name|FreeTimeRequest
operator|)
name|request
decl_stmt|;
name|Enrollment
name|enrollment
init|=
name|ft
operator|.
name|createEnrollment
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|model
operator|.
name|inConflict
argument_list|(
name|assignment
argument_list|,
name|enrollment
argument_list|)
condition|)
name|assignment
operator|.
name|assign
argument_list|(
literal|0l
argument_list|,
name|enrollment
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|server
operator|.
name|execute
argument_list|(
name|server
operator|.
name|createAction
argument_list|(
name|GetAssignment
operator|.
name|class
argument_list|)
operator|.
name|forStudent
argument_list|(
name|getStudentId
argument_list|()
argument_list|)
operator|.
name|withMessages
argument_list|(
name|failures
argument_list|)
operator|.
name|withWaitListMode
argument_list|(
name|WaitListMode
operator|.
name|WaitList
argument_list|)
argument_list|,
name|helper
operator|.
name|getUser
argument_list|()
argument_list|)
return|;
block|}
specifier|protected
name|Course
name|getCourse
parameter_list|(
name|StudentSectioningModel
name|model
parameter_list|,
name|Long
name|courseId
parameter_list|,
name|String
name|courseName
parameter_list|)
block|{
for|for
control|(
name|Offering
name|offering
range|:
name|model
operator|.
name|getOfferings
argument_list|()
control|)
for|for
control|(
name|Course
name|course
range|:
name|offering
operator|.
name|getCourses
argument_list|()
control|)
block|{
if|if
condition|(
name|courseId
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|courseId
operator|.
name|equals
argument_list|(
name|course
operator|.
name|getId
argument_list|()
argument_list|)
condition|)
return|return
name|course
return|;
block|}
else|else
block|{
if|if
condition|(
name|course
operator|.
name|getName
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
name|courseName
argument_list|)
condition|)
return|return
name|course
return|;
block|}
block|}
return|return
literal|null
return|;
block|}
block|}
end_class

end_unit

