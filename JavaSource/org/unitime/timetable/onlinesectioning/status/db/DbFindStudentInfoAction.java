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
name|TreeSet
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
name|StudentAreaClassificationMajor
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
name|StudentNote
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
name|FindStudentInfoAction
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
name|SectioningStatusFilterAction
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
name|DbFindStudentInfoMatcher
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|DbFindStudentInfoAction
extends|extends
name|FindStudentInfoAction
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
name|StudentInfo
argument_list|>
name|execute
parameter_list|(
specifier|final
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
name|Map
argument_list|<
name|Long
argument_list|,
name|StudentInfo
argument_list|>
name|students
init|=
operator|new
name|HashMap
argument_list|<
name|Long
argument_list|,
name|StudentInfo
argument_list|>
argument_list|()
decl_stmt|;
name|int
name|gEnrl
init|=
literal|0
decl_stmt|,
name|gWait
init|=
literal|0
decl_stmt|,
name|gRes
init|=
literal|0
decl_stmt|,
name|gUnasg
init|=
literal|0
decl_stmt|;
name|int
name|gtEnrl
init|=
literal|0
decl_stmt|,
name|gtWait
init|=
literal|0
decl_stmt|,
name|gtRes
init|=
literal|0
decl_stmt|,
name|gtUnasg
init|=
literal|0
decl_stmt|;
name|int
name|gConNeed
init|=
literal|0
decl_stmt|,
name|gtConNeed
init|=
literal|0
decl_stmt|;
name|Set
argument_list|<
name|Long
argument_list|>
name|unassigned
init|=
operator|new
name|HashSet
argument_list|<
name|Long
argument_list|>
argument_list|()
decl_stmt|;
name|Set
argument_list|<
name|Long
argument_list|>
name|assignedRequests
init|=
operator|new
name|HashSet
argument_list|<
name|Long
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
name|DbFindStudentInfoMatcher
name|sm
init|=
operator|new
name|DbFindStudentInfoMatcher
argument_list|(
name|session
argument_list|,
name|iQuery
argument_list|,
name|helper
operator|.
name|getStudentNameFormat
argument_list|()
argument_list|)
decl_stmt|;
name|Map
argument_list|<
name|CourseOffering
argument_list|,
name|List
argument_list|<
name|CourseRequest
argument_list|>
argument_list|>
name|requests
init|=
operator|new
name|HashMap
argument_list|<
name|CourseOffering
argument_list|,
name|List
argument_list|<
name|CourseRequest
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|CourseRequest
name|cr
range|:
operator|(
name|List
argument_list|<
name|CourseRequest
argument_list|>
operator|)
name|SectioningStatusFilterAction
operator|.
name|getCourseQuery
argument_list|(
name|iFilter
argument_list|,
name|server
argument_list|)
operator|.
name|select
argument_list|(
literal|"distinct cr"
argument_list|)
operator|.
name|query
argument_list|(
name|helper
operator|.
name|getHibSession
argument_list|()
argument_list|)
operator|.
name|list
argument_list|()
control|)
block|{
if|if
condition|(
operator|!
name|hasMatchingSubjectArea
argument_list|(
name|cr
operator|.
name|getCourseOffering
argument_list|()
operator|.
name|getSubjectAreaAbbv
argument_list|()
argument_list|)
condition|)
continue|continue;
if|if
condition|(
operator|!
name|isCourseVisible
argument_list|(
name|cr
operator|.
name|getCourseOffering
argument_list|()
operator|.
name|getUniqueId
argument_list|()
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
name|DbCourseRequestMatcher
argument_list|(
name|session
argument_list|,
name|cr
argument_list|,
name|isConsentToDoCourse
argument_list|(
name|cr
operator|.
name|getCourseOffering
argument_list|()
argument_list|)
argument_list|,
name|helper
operator|.
name|getStudentNameFormat
argument_list|()
argument_list|)
argument_list|)
condition|)
continue|continue;
name|List
argument_list|<
name|CourseRequest
argument_list|>
name|list
init|=
name|requests
operator|.
name|get
argument_list|(
name|cr
operator|.
name|getCourseOffering
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|list
operator|==
literal|null
condition|)
block|{
name|list
operator|=
operator|new
name|ArrayList
argument_list|<
name|CourseRequest
argument_list|>
argument_list|()
expr_stmt|;
name|requests
operator|.
name|put
argument_list|(
name|cr
operator|.
name|getCourseOffering
argument_list|()
argument_list|,
name|list
argument_list|)
expr_stmt|;
block|}
name|list
operator|.
name|add
argument_list|(
name|cr
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|CourseOffering
argument_list|,
name|List
argument_list|<
name|CourseRequest
argument_list|>
argument_list|>
name|entry
range|:
name|requests
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|CourseOffering
name|course
init|=
name|entry
operator|.
name|getKey
argument_list|()
decl_stmt|;
name|boolean
name|isConsentToDoCourse
init|=
name|isConsentToDoCourse
argument_list|(
name|course
argument_list|)
decl_stmt|;
for|for
control|(
name|CourseRequest
name|request
range|:
name|entry
operator|.
name|getValue
argument_list|()
control|)
block|{
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
name|StudentInfo
name|s
init|=
name|students
operator|.
name|get
argument_list|(
name|student
operator|.
name|getUniqueId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|s
operator|==
literal|null
condition|)
block|{
name|s
operator|=
operator|new
name|StudentInfo
argument_list|()
expr_stmt|;
name|students
operator|.
name|put
argument_list|(
name|student
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|s
argument_list|)
expr_stmt|;
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
name|s
operator|.
name|setStudent
argument_list|(
name|st
argument_list|)
expr_stmt|;
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
name|StudentAreaClassificationMajor
name|acm
range|:
operator|new
name|TreeSet
argument_list|<
name|StudentAreaClassificationMajor
argument_list|>
argument_list|(
name|student
operator|.
name|getAreaClasfMajors
argument_list|()
argument_list|)
control|)
block|{
name|st
operator|.
name|addArea
argument_list|(
name|acm
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
name|acm
operator|.
name|getAcademicClassification
argument_list|()
operator|.
name|getCode
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
name|int
name|tEnrl
init|=
literal|0
decl_stmt|,
name|tWait
init|=
literal|0
decl_stmt|,
name|tRes
init|=
literal|0
decl_stmt|,
name|tConNeed
init|=
literal|0
decl_stmt|,
name|tReq
init|=
literal|0
decl_stmt|,
name|tUnasg
init|=
literal|0
decl_stmt|;
name|float
name|tCred
init|=
literal|0f
decl_stmt|;
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
condition|)
block|{
if|if
condition|(
operator|!
name|demand
operator|.
name|isAlternative
argument_list|()
condition|)
name|tReq
operator|++
expr_stmt|;
name|List
argument_list|<
name|StudentClassEnrollment
argument_list|>
name|enrollment
init|=
literal|null
decl_stmt|;
name|CourseRequest
name|assigned
init|=
literal|null
decl_stmt|;
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
name|enrollment
operator|=
name|r
operator|.
name|getClassEnrollments
argument_list|()
expr_stmt|;
if|if
condition|(
operator|!
name|enrollment
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|assigned
operator|=
name|r
expr_stmt|;
break|break;
block|}
block|}
if|if
condition|(
name|enrollment
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|CourseRequest
name|first
init|=
literal|null
decl_stmt|;
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
name|first
operator|==
literal|null
operator|||
name|r
operator|.
name|getOrder
argument_list|()
operator|<
name|first
operator|.
name|getOrder
argument_list|()
condition|)
name|first
operator|=
name|r
expr_stmt|;
block|}
name|DbCourseRequestMatcher
name|crm
init|=
operator|new
name|DbCourseRequestMatcher
argument_list|(
name|session
argument_list|,
name|first
argument_list|,
name|isConsentToDoCourse
argument_list|(
name|first
operator|.
name|getCourseOffering
argument_list|()
argument_list|)
argument_list|,
name|helper
operator|.
name|getStudentNameFormat
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|crm
operator|.
name|canAssign
argument_list|()
condition|)
block|{
name|tUnasg
operator|++
expr_stmt|;
name|gtUnasg
operator|++
expr_stmt|;
if|if
condition|(
name|demand
operator|.
name|isWaitlist
argument_list|()
condition|)
block|{
name|tWait
operator|++
expr_stmt|;
name|gtWait
operator|++
expr_stmt|;
block|}
block|}
block|}
else|else
block|{
name|tEnrl
operator|++
expr_stmt|;
name|gtEnrl
operator|++
expr_stmt|;
name|DbCourseRequestMatcher
name|crm
init|=
operator|new
name|DbCourseRequestMatcher
argument_list|(
name|session
argument_list|,
name|assigned
argument_list|,
name|isConsentToDoCourse
argument_list|(
name|assigned
operator|.
name|getCourseOffering
argument_list|()
argument_list|)
argument_list|,
name|helper
operator|.
name|getStudentNameFormat
argument_list|()
argument_list|)
decl_stmt|;
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
name|tRes
operator|++
expr_stmt|;
name|gtRes
operator|++
expr_stmt|;
block|}
if|if
condition|(
name|assigned
operator|.
name|getCourseOffering
argument_list|()
operator|.
name|getConsentType
argument_list|()
operator|!=
literal|null
operator|&&
name|crm
operator|.
name|approval
argument_list|()
operator|==
literal|null
condition|)
block|{
name|tConNeed
operator|++
expr_stmt|;
name|gtConNeed
operator|++
expr_stmt|;
block|}
if|if
condition|(
name|assigned
operator|.
name|getCourseOffering
argument_list|()
operator|.
name|getCredit
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|tCred
operator|+=
name|guessCredit
argument_list|(
name|assigned
operator|.
name|getCourseOffering
argument_list|()
operator|.
name|getCredit
argument_list|()
operator|.
name|creditAbbv
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
for|for
control|(
name|StudentClassEnrollment
name|e
range|:
name|enrollment
control|)
block|{
if|if
condition|(
name|e
operator|.
name|getClazz
argument_list|()
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getCredit
argument_list|()
operator|!=
literal|null
condition|)
name|tCred
operator|+=
name|guessCredit
argument_list|(
name|e
operator|.
name|getClazz
argument_list|()
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getCredit
argument_list|()
operator|.
name|creditAbbv
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
block|}
name|s
operator|.
name|setTotalEnrollment
argument_list|(
name|tEnrl
argument_list|)
expr_stmt|;
name|s
operator|.
name|setTotalReservation
argument_list|(
name|tRes
argument_list|)
expr_stmt|;
name|s
operator|.
name|setTotalWaitlist
argument_list|(
name|tWait
argument_list|)
expr_stmt|;
name|s
operator|.
name|setTotalUnassigned
argument_list|(
name|tUnasg
argument_list|)
expr_stmt|;
name|s
operator|.
name|setTotalConsentNeeded
argument_list|(
name|tConNeed
argument_list|)
expr_stmt|;
name|s
operator|.
name|setEnrollment
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|s
operator|.
name|setReservation
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|s
operator|.
name|setWaitlist
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|s
operator|.
name|setUnassigned
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|s
operator|.
name|setConsentNeeded
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|s
operator|.
name|setRequested
argument_list|(
name|tReq
argument_list|)
expr_stmt|;
name|s
operator|.
name|setStatus
argument_list|(
name|student
operator|.
name|getSectioningStatus
argument_list|()
operator|==
literal|null
condition|?
name|session
operator|.
name|getDefaultSectioningStatus
argument_list|()
else|:
name|student
operator|.
name|getSectioningStatus
argument_list|()
operator|.
name|getReference
argument_list|()
argument_list|)
expr_stmt|;
name|s
operator|.
name|setEmailDate
argument_list|(
name|student
operator|.
name|getScheduleEmailedDate
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
name|student
operator|.
name|getScheduleEmailedDate
argument_list|()
argument_list|)
expr_stmt|;
name|s
operator|.
name|setCredit
argument_list|(
literal|0f
argument_list|)
expr_stmt|;
name|s
operator|.
name|setTotalCredit
argument_list|(
name|tCred
argument_list|)
expr_stmt|;
name|StudentNote
name|note
init|=
literal|null
decl_stmt|;
for|for
control|(
name|StudentNote
name|n
range|:
name|student
operator|.
name|getNotes
argument_list|()
control|)
if|if
condition|(
name|note
operator|==
literal|null
operator|||
name|note
operator|.
name|compareTo
argument_list|(
name|n
argument_list|)
operator|>
literal|0
condition|)
name|note
operator|=
name|n
expr_stmt|;
if|if
condition|(
name|note
operator|!=
literal|null
condition|)
name|s
operator|.
name|setNote
argument_list|(
name|note
operator|.
name|getTextNote
argument_list|()
argument_list|)
expr_stmt|;
block|}
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
argument_list|,
name|helper
operator|.
name|getStudentNameFormat
argument_list|()
argument_list|)
decl_stmt|;
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
name|assignedRequests
operator|.
name|add
argument_list|(
name|crm
operator|.
name|request
argument_list|()
operator|.
name|getCourseDemand
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
condition|)
block|{
name|s
operator|.
name|setEnrollment
argument_list|(
name|s
operator|.
name|getEnrollment
argument_list|()
operator|+
literal|1
argument_list|)
expr_stmt|;
name|gEnrl
operator|++
expr_stmt|;
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
name|s
operator|.
name|setReservation
argument_list|(
name|s
operator|.
name|getReservation
argument_list|()
operator|+
literal|1
argument_list|)
expr_stmt|;
name|gRes
operator|++
expr_stmt|;
block|}
if|if
condition|(
name|course
operator|.
name|getConsentType
argument_list|()
operator|!=
literal|null
operator|&&
name|crm
operator|.
name|approval
argument_list|()
operator|==
literal|null
condition|)
block|{
name|s
operator|.
name|setConsentNeeded
argument_list|(
name|s
operator|.
name|getConsentNeeded
argument_list|()
operator|+
literal|1
argument_list|)
expr_stmt|;
name|gConNeed
operator|++
expr_stmt|;
block|}
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
name|getTimestamp
argument_list|()
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|s
operator|.
name|getEnrolledDate
argument_list|()
operator|==
literal|null
condition|)
name|s
operator|.
name|setEnrolledDate
argument_list|(
name|e
operator|.
name|getTimestamp
argument_list|()
argument_list|)
expr_stmt|;
if|else if
condition|(
name|e
operator|.
name|getTimestamp
argument_list|()
operator|.
name|after
argument_list|(
name|s
operator|.
name|getEnrolledDate
argument_list|()
argument_list|)
condition|)
name|s
operator|.
name|setEnrolledDate
argument_list|(
name|e
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
name|getApprovedDate
argument_list|()
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|s
operator|.
name|getApprovedDate
argument_list|()
operator|==
literal|null
condition|)
name|s
operator|.
name|setApprovedDate
argument_list|(
name|e
operator|.
name|getApprovedDate
argument_list|()
argument_list|)
expr_stmt|;
if|else if
condition|(
name|e
operator|.
name|getApprovedDate
argument_list|()
operator|.
name|after
argument_list|(
name|s
operator|.
name|getApprovedDate
argument_list|()
argument_list|)
condition|)
name|s
operator|.
name|setApprovedDate
argument_list|(
name|e
operator|.
name|getApprovedDate
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
name|course
operator|.
name|getCredit
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|s
operator|.
name|setCredit
argument_list|(
name|s
operator|.
name|getCredit
argument_list|()
operator|+
name|guessCredit
argument_list|(
name|course
operator|.
name|getCredit
argument_list|()
operator|.
name|creditAbbv
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
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
name|getSchedulingSubpart
argument_list|()
operator|.
name|getCredit
argument_list|()
operator|!=
literal|null
condition|)
name|s
operator|.
name|setCredit
argument_list|(
name|s
operator|.
name|getCredit
argument_list|()
operator|+
name|guessCredit
argument_list|(
name|e
operator|.
name|getClazz
argument_list|()
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getCredit
argument_list|()
operator|.
name|creditAbbv
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
if|else if
condition|(
name|crm
operator|.
name|canAssign
argument_list|()
operator|&&
name|unassigned
operator|.
name|add
argument_list|(
name|crm
operator|.
name|request
argument_list|()
operator|.
name|getCourseDemand
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
condition|)
block|{
if|if
condition|(
name|crm
operator|.
name|request
argument_list|()
operator|.
name|getCourseDemand
argument_list|()
operator|.
name|isWaitlist
argument_list|()
condition|)
block|{
name|s
operator|.
name|setWaitlist
argument_list|(
name|s
operator|.
name|getWaitlist
argument_list|()
operator|+
literal|1
argument_list|)
expr_stmt|;
name|gWait
operator|++
expr_stmt|;
if|if
condition|(
name|s
operator|.
name|getTopWaitingPriority
argument_list|()
operator|==
literal|null
condition|)
name|s
operator|.
name|setTopWaitingPriority
argument_list|(
literal|1
operator|+
name|crm
operator|.
name|request
argument_list|()
operator|.
name|getCourseDemand
argument_list|()
operator|.
name|getPriority
argument_list|()
argument_list|)
expr_stmt|;
else|else
name|s
operator|.
name|setTopWaitingPriority
argument_list|(
name|Math
operator|.
name|min
argument_list|(
literal|1
operator|+
name|crm
operator|.
name|request
argument_list|()
operator|.
name|getCourseDemand
argument_list|()
operator|.
name|getPriority
argument_list|()
argument_list|,
name|s
operator|.
name|getTopWaitingPriority
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|s
operator|.
name|setUnassigned
argument_list|(
name|s
operator|.
name|getUnassigned
argument_list|()
operator|+
literal|1
argument_list|)
expr_stmt|;
name|gUnasg
operator|++
expr_stmt|;
block|}
if|if
condition|(
name|crm
operator|.
name|request
argument_list|()
operator|.
name|getCourseDemand
argument_list|()
operator|.
name|getTimestamp
argument_list|()
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|s
operator|.
name|getRequestedDate
argument_list|()
operator|==
literal|null
condition|)
name|s
operator|.
name|setRequestedDate
argument_list|(
name|crm
operator|.
name|request
argument_list|()
operator|.
name|getCourseDemand
argument_list|()
operator|.
name|getTimestamp
argument_list|()
argument_list|)
expr_stmt|;
if|else if
condition|(
name|crm
operator|.
name|request
argument_list|()
operator|.
name|getCourseDemand
argument_list|()
operator|.
name|getTimestamp
argument_list|()
operator|.
name|after
argument_list|(
name|s
operator|.
name|getRequestedDate
argument_list|()
argument_list|)
condition|)
name|s
operator|.
name|setRequestedDate
argument_list|(
name|crm
operator|.
name|request
argument_list|()
operator|.
name|getCourseDemand
argument_list|()
operator|.
name|getTimestamp
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
name|List
argument_list|<
name|StudentInfo
argument_list|>
name|ret
init|=
operator|new
name|ArrayList
argument_list|<
name|StudentInfo
argument_list|>
argument_list|(
name|students
operator|.
name|values
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|iSubjectAreas
operator|==
literal|null
operator|&&
name|iCoursesIcoordinate
operator|==
literal|null
condition|)
block|{
for|for
control|(
name|Student
name|student
range|:
operator|(
name|List
argument_list|<
name|Student
argument_list|>
operator|)
name|SectioningStatusFilterAction
operator|.
name|getQuery
argument_list|(
name|iFilter
argument_list|,
name|server
argument_list|)
operator|.
name|select
argument_list|(
literal|"distinct s"
argument_list|)
operator|.
name|query
argument_list|(
name|helper
operator|.
name|getHibSession
argument_list|()
argument_list|)
operator|.
name|list
argument_list|()
control|)
block|{
if|if
condition|(
name|students
operator|.
name|containsKey
argument_list|(
name|student
operator|.
name|getUniqueId
argument_list|()
argument_list|)
condition|)
continue|continue;
if|if
condition|(
operator|!
name|sm
operator|.
name|match
argument_list|(
name|student
argument_list|)
condition|)
continue|continue;
name|StudentInfo
name|s
init|=
operator|new
name|StudentInfo
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
name|s
operator|.
name|setStudent
argument_list|(
name|st
argument_list|)
expr_stmt|;
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
name|StudentAreaClassificationMajor
name|acm
range|:
operator|new
name|TreeSet
argument_list|<
name|StudentAreaClassificationMajor
argument_list|>
argument_list|(
name|student
operator|.
name|getAreaClasfMajors
argument_list|()
argument_list|)
control|)
block|{
name|st
operator|.
name|addArea
argument_list|(
name|acm
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
name|acm
operator|.
name|getAcademicClassification
argument_list|()
operator|.
name|getCode
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
name|s
operator|.
name|setStatus
argument_list|(
name|student
operator|.
name|getSectioningStatus
argument_list|()
operator|==
literal|null
condition|?
name|session
operator|.
name|getDefaultSectioningStatus
argument_list|()
else|:
name|student
operator|.
name|getSectioningStatus
argument_list|()
operator|.
name|getReference
argument_list|()
argument_list|)
expr_stmt|;
name|s
operator|.
name|setEmailDate
argument_list|(
name|student
operator|.
name|getScheduleEmailedDate
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
name|student
operator|.
name|getScheduleEmailedDate
argument_list|()
argument_list|)
expr_stmt|;
name|StudentNote
name|note
init|=
literal|null
decl_stmt|;
for|for
control|(
name|StudentNote
name|n
range|:
name|student
operator|.
name|getNotes
argument_list|()
control|)
if|if
condition|(
name|note
operator|==
literal|null
operator|||
name|note
operator|.
name|compareTo
argument_list|(
name|n
argument_list|)
operator|>
literal|0
condition|)
name|note
operator|=
name|n
expr_stmt|;
if|if
condition|(
name|note
operator|!=
literal|null
condition|)
name|s
operator|.
name|setNote
argument_list|(
name|note
operator|.
name|getTextNote
argument_list|()
argument_list|)
expr_stmt|;
name|ret
operator|.
name|add
argument_list|(
name|s
argument_list|)
expr_stmt|;
block|}
block|}
name|Collections
operator|.
name|sort
argument_list|(
name|ret
argument_list|,
operator|new
name|Comparator
argument_list|<
name|StudentInfo
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|int
name|compare
parameter_list|(
name|StudentInfo
name|s1
parameter_list|,
name|StudentInfo
name|s2
parameter_list|)
block|{
name|int
name|cmp
init|=
name|s1
operator|.
name|getStudent
argument_list|()
operator|.
name|getName
argument_list|()
operator|.
name|compareTo
argument_list|(
name|s2
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
operator|new
name|Long
argument_list|(
name|s1
operator|.
name|getStudent
argument_list|()
operator|.
name|getId
argument_list|()
argument_list|)
operator|.
name|compareTo
argument_list|(
name|s2
operator|.
name|getStudent
argument_list|()
operator|.
name|getId
argument_list|()
argument_list|)
return|;
block|}
block|}
argument_list|)
expr_stmt|;
if|if
condition|(
name|limit
argument_list|()
operator|!=
literal|null
operator|&&
name|ret
operator|.
name|size
argument_list|()
operator|>=
name|limit
argument_list|()
condition|)
block|{
name|List
argument_list|<
name|StudentInfo
argument_list|>
name|r
init|=
operator|new
name|ArrayList
argument_list|<
name|StudentInfo
argument_list|>
argument_list|(
name|limit
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|StudentInfo
name|i
range|:
name|ret
control|)
block|{
name|r
operator|.
name|add
argument_list|(
name|i
argument_list|)
expr_stmt|;
if|if
condition|(
name|r
operator|.
name|size
argument_list|()
operator|==
name|limit
argument_list|()
condition|)
break|break;
block|}
name|ret
operator|=
name|r
expr_stmt|;
block|}
comment|// if (students.size()> 0) {
name|StudentInfo
name|t
init|=
operator|new
name|StudentInfo
argument_list|()
decl_stmt|;
name|t
operator|.
name|setEnrollment
argument_list|(
name|gEnrl
argument_list|)
expr_stmt|;
name|t
operator|.
name|setReservation
argument_list|(
name|gRes
argument_list|)
expr_stmt|;
name|t
operator|.
name|setWaitlist
argument_list|(
name|gWait
argument_list|)
expr_stmt|;
name|t
operator|.
name|setUnassigned
argument_list|(
name|gUnasg
argument_list|)
expr_stmt|;
name|t
operator|.
name|setTotalEnrollment
argument_list|(
name|gtEnrl
argument_list|)
expr_stmt|;
name|t
operator|.
name|setTotalReservation
argument_list|(
name|gtRes
argument_list|)
expr_stmt|;
name|t
operator|.
name|setTotalWaitlist
argument_list|(
name|gtWait
argument_list|)
expr_stmt|;
name|t
operator|.
name|setTotalUnassigned
argument_list|(
name|gtUnasg
argument_list|)
expr_stmt|;
name|t
operator|.
name|setConsentNeeded
argument_list|(
name|gConNeed
argument_list|)
expr_stmt|;
name|t
operator|.
name|setTotalConsentNeeded
argument_list|(
name|gtConNeed
argument_list|)
expr_stmt|;
name|ret
operator|.
name|add
argument_list|(
name|t
argument_list|)
expr_stmt|;
return|return
name|ret
return|;
block|}
specifier|public
name|boolean
name|isConsentToDoCourse
parameter_list|(
name|CourseOffering
name|course
parameter_list|)
block|{
return|return
name|iCoursesIcanApprove
operator|!=
literal|null
operator|&&
name|course
operator|.
name|getConsentType
argument_list|()
operator|!=
literal|null
operator|&&
name|iCoursesIcanApprove
operator|.
name|contains
argument_list|(
name|course
operator|.
name|getUniqueId
argument_list|()
argument_list|)
return|;
block|}
block|}
end_class

end_unit

