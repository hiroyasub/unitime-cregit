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
name|model
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|ObjectInput
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|ObjectOutput
import|;
end_import

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
name|HashMap
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
name|TreeSet
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
name|Enrollment
import|;
end_import

begin_import
import|import
name|org
operator|.
name|infinispan
operator|.
name|commons
operator|.
name|marshall
operator|.
name|Externalizer
import|;
end_import

begin_import
import|import
name|org
operator|.
name|infinispan
operator|.
name|commons
operator|.
name|marshall
operator|.
name|SerializeWith
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
name|ClassWaitList
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
name|CourseRequestOption
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
name|com
operator|.
name|google
operator|.
name|protobuf
operator|.
name|InvalidProtocolBufferException
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|SerializeWith
argument_list|(
name|XCourseRequest
operator|.
name|XCourseRequestSerializer
operator|.
name|class
argument_list|)
specifier|public
class|class
name|XCourseRequest
extends|extends
name|XRequest
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
name|List
argument_list|<
name|XCourseId
argument_list|>
name|iCourseIds
init|=
operator|new
name|ArrayList
argument_list|<
name|XCourseId
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|boolean
name|iWaitlist
init|=
literal|false
decl_stmt|;
specifier|private
name|Date
name|iTimeStamp
init|=
literal|null
decl_stmt|;
specifier|private
name|XEnrollment
name|iEnrollment
init|=
literal|null
decl_stmt|;
specifier|private
name|Map
argument_list|<
name|XCourseId
argument_list|,
name|List
argument_list|<
name|XWaitListedSection
argument_list|>
argument_list|>
name|iSectionWaitlist
init|=
literal|null
decl_stmt|;
specifier|private
name|Map
argument_list|<
name|XCourseId
argument_list|,
name|byte
index|[]
argument_list|>
name|iOptions
init|=
literal|null
decl_stmt|;
specifier|private
name|String
name|iMessage
init|=
literal|null
decl_stmt|;
specifier|public
name|XCourseRequest
parameter_list|()
block|{
block|}
specifier|public
name|XCourseRequest
parameter_list|(
name|ObjectInput
name|in
parameter_list|)
throws|throws
name|IOException
throws|,
name|ClassNotFoundException
block|{
name|readExternal
argument_list|(
name|in
argument_list|)
expr_stmt|;
block|}
specifier|public
name|XCourseRequest
parameter_list|(
name|CourseDemand
name|demand
parameter_list|,
name|OnlineSectioningHelper
name|helper
parameter_list|)
block|{
name|super
argument_list|(
name|demand
argument_list|)
expr_stmt|;
name|TreeSet
argument_list|<
name|CourseRequest
argument_list|>
name|crs
init|=
operator|new
name|TreeSet
argument_list|<
name|CourseRequest
argument_list|>
argument_list|(
operator|new
name|Comparator
argument_list|<
name|CourseRequest
argument_list|>
argument_list|()
block|{
specifier|public
name|int
name|compare
parameter_list|(
name|CourseRequest
name|r1
parameter_list|,
name|CourseRequest
name|r2
parameter_list|)
block|{
return|return
name|r1
operator|.
name|getOrder
argument_list|()
operator|.
name|compareTo
argument_list|(
name|r2
operator|.
name|getOrder
argument_list|()
argument_list|)
return|;
block|}
block|}
argument_list|)
decl_stmt|;
name|crs
operator|.
name|addAll
argument_list|(
name|demand
operator|.
name|getCourseRequests
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|CourseRequest
name|cr
range|:
name|crs
control|)
block|{
name|XCourseId
name|courseId
init|=
operator|new
name|XCourseId
argument_list|(
name|cr
operator|.
name|getCourseOffering
argument_list|()
argument_list|)
decl_stmt|;
name|iCourseIds
operator|.
name|add
argument_list|(
name|courseId
argument_list|)
expr_stmt|;
if|if
condition|(
name|cr
operator|.
name|getClassWaitLists
argument_list|()
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|ClassWaitList
name|cwl
range|:
name|cr
operator|.
name|getClassWaitLists
argument_list|()
control|)
block|{
if|if
condition|(
name|iSectionWaitlist
operator|==
literal|null
condition|)
name|iSectionWaitlist
operator|=
operator|new
name|HashMap
argument_list|<
name|XCourseId
argument_list|,
name|List
argument_list|<
name|XWaitListedSection
argument_list|>
argument_list|>
argument_list|()
expr_stmt|;
name|List
argument_list|<
name|XWaitListedSection
argument_list|>
name|sections
init|=
name|iSectionWaitlist
operator|.
name|get
argument_list|(
name|courseId
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
name|XWaitListedSection
argument_list|>
argument_list|()
expr_stmt|;
name|iSectionWaitlist
operator|.
name|put
argument_list|(
name|courseId
argument_list|,
name|sections
argument_list|)
expr_stmt|;
block|}
name|sections
operator|.
name|add
argument_list|(
operator|new
name|XWaitListedSection
argument_list|(
name|cwl
argument_list|,
name|helper
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
for|for
control|(
name|CourseRequestOption
name|option
range|:
name|cr
operator|.
name|getCourseRequestOptions
argument_list|()
control|)
block|{
if|if
condition|(
name|OnlineSectioningLog
operator|.
name|CourseRequestOption
operator|.
name|OptionType
operator|.
name|ORIGINAL_ENROLLMENT
operator|.
name|getNumber
argument_list|()
operator|==
name|option
operator|.
name|getOptionType
argument_list|()
condition|)
block|{
if|if
condition|(
name|iOptions
operator|==
literal|null
condition|)
name|iOptions
operator|=
operator|new
name|HashMap
argument_list|<
name|XCourseId
argument_list|,
name|byte
index|[]
argument_list|>
argument_list|()
expr_stmt|;
name|iOptions
operator|.
name|put
argument_list|(
name|courseId
argument_list|,
name|option
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
name|helper
operator|.
name|isAlternativeCourseEnabled
argument_list|()
operator|&&
name|crs
operator|.
name|size
argument_list|()
operator|==
literal|1
operator|&&
operator|!
name|demand
operator|.
name|isAlternative
argument_list|()
condition|)
block|{
name|CourseOffering
name|co
init|=
name|crs
operator|.
name|first
argument_list|()
operator|.
name|getCourseOffering
argument_list|()
decl_stmt|;
name|CourseOffering
name|alternative
init|=
name|co
operator|.
name|getAlternativeOffering
argument_list|()
decl_stmt|;
if|if
condition|(
name|alternative
operator|!=
literal|null
condition|)
block|{
comment|// Make sure that the alternative course is not already requested
for|for
control|(
name|CourseDemand
name|d
range|:
name|demand
operator|.
name|getStudent
argument_list|()
operator|.
name|getCourseDemands
argument_list|()
control|)
block|{
for|for
control|(
name|CourseRequest
name|r
range|:
name|d
operator|.
name|getCourseRequests
argument_list|()
control|)
block|{
if|if
condition|(
name|alternative
operator|.
name|equals
argument_list|(
name|r
operator|.
name|getCourseOffering
argument_list|()
argument_list|)
condition|)
block|{
name|alternative
operator|=
literal|null
expr_stmt|;
break|break;
block|}
if|if
condition|(
operator|!
name|d
operator|.
name|isAlternative
argument_list|()
operator|&&
name|d
operator|.
name|getPriority
argument_list|()
operator|<
name|demand
operator|.
name|getPriority
argument_list|()
operator|&&
name|d
operator|.
name|getCourseRequests
argument_list|()
operator|.
name|size
argument_list|()
operator|==
literal|1
operator|&&
name|alternative
operator|.
name|equals
argument_list|(
name|r
operator|.
name|getCourseOffering
argument_list|()
operator|.
name|getAlternativeOffering
argument_list|()
argument_list|)
condition|)
block|{
name|alternative
operator|=
literal|null
expr_stmt|;
break|break;
block|}
block|}
if|if
condition|(
name|alternative
operator|==
literal|null
condition|)
break|break;
block|}
block|}
if|if
condition|(
name|alternative
operator|!=
literal|null
condition|)
block|{
name|iCourseIds
operator|.
name|add
argument_list|(
operator|new
name|XCourseId
argument_list|(
name|alternative
argument_list|)
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|StudentClassEnrollment
argument_list|>
name|enrl
init|=
name|alternative
operator|.
name|getClassEnrollments
argument_list|(
name|demand
operator|.
name|getStudent
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|enrl
operator|.
name|isEmpty
argument_list|()
condition|)
name|iEnrollment
operator|=
operator|new
name|XEnrollment
argument_list|(
name|demand
operator|.
name|getStudent
argument_list|()
argument_list|,
name|alternative
argument_list|,
name|helper
argument_list|,
name|enrl
argument_list|)
expr_stmt|;
block|}
block|}
name|iWaitlist
operator|=
operator|(
name|demand
operator|.
name|isWaitlist
argument_list|()
operator|!=
literal|null
operator|&&
name|demand
operator|.
name|isWaitlist
argument_list|()
operator|)
expr_stmt|;
name|iTimeStamp
operator|=
operator|(
name|demand
operator|.
name|getTimestamp
argument_list|()
operator|==
literal|null
condition|?
operator|new
name|Date
argument_list|()
else|:
name|demand
operator|.
name|getTimestamp
argument_list|()
operator|)
expr_stmt|;
for|for
control|(
name|CourseRequest
name|cr
range|:
name|crs
control|)
block|{
name|List
argument_list|<
name|StudentClassEnrollment
argument_list|>
name|enrl
init|=
name|cr
operator|.
name|getClassEnrollments
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|enrl
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|iEnrollment
operator|=
operator|new
name|XEnrollment
argument_list|(
name|demand
operator|.
name|getStudent
argument_list|()
argument_list|,
name|cr
operator|.
name|getCourseOffering
argument_list|()
argument_list|,
name|helper
argument_list|,
name|enrl
argument_list|)
expr_stmt|;
break|break;
block|}
block|}
if|if
condition|(
name|demand
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
name|demand
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
name|iMessage
operator|=
name|message
operator|.
name|getMessage
argument_list|()
expr_stmt|;
block|}
block|}
specifier|public
name|XCourseRequest
parameter_list|(
name|Student
name|student
parameter_list|,
name|CourseOffering
name|course
parameter_list|,
name|int
name|priority
parameter_list|,
name|OnlineSectioningHelper
name|helper
parameter_list|,
name|Collection
argument_list|<
name|StudentClassEnrollment
argument_list|>
name|classes
parameter_list|)
block|{
name|super
argument_list|()
expr_stmt|;
name|iStudentId
operator|=
name|student
operator|.
name|getUniqueId
argument_list|()
expr_stmt|;
name|iRequestId
operator|=
operator|-
name|course
operator|.
name|getUniqueId
argument_list|()
expr_stmt|;
name|iAlternative
operator|=
literal|false
expr_stmt|;
name|iPriority
operator|=
name|priority
expr_stmt|;
name|iCourseIds
operator|.
name|add
argument_list|(
operator|new
name|XCourseId
argument_list|(
name|course
argument_list|)
argument_list|)
expr_stmt|;
name|iWaitlist
operator|=
literal|false
expr_stmt|;
if|if
condition|(
name|classes
operator|!=
literal|null
operator|&&
operator|!
name|classes
operator|.
name|isEmpty
argument_list|()
condition|)
name|iEnrollment
operator|=
operator|new
name|XEnrollment
argument_list|(
name|student
argument_list|,
name|course
argument_list|,
name|helper
argument_list|,
name|classes
argument_list|)
expr_stmt|;
if|if
condition|(
name|iEnrollment
operator|!=
literal|null
condition|)
name|iTimeStamp
operator|=
name|iEnrollment
operator|.
name|getTimeStamp
argument_list|()
expr_stmt|;
else|else
name|iTimeStamp
operator|=
operator|new
name|Date
argument_list|()
expr_stmt|;
block|}
specifier|public
name|XCourseRequest
parameter_list|(
name|org
operator|.
name|cpsolver
operator|.
name|studentsct
operator|.
name|model
operator|.
name|CourseRequest
name|request
parameter_list|,
name|Enrollment
name|enrollment
parameter_list|)
block|{
name|super
argument_list|(
name|request
argument_list|)
expr_stmt|;
for|for
control|(
name|Course
name|course
range|:
name|request
operator|.
name|getCourses
argument_list|()
control|)
name|iCourseIds
operator|.
name|add
argument_list|(
operator|new
name|XCourseId
argument_list|(
name|course
argument_list|)
argument_list|)
expr_stmt|;
name|iWaitlist
operator|=
name|request
operator|.
name|isWaitlist
argument_list|()
expr_stmt|;
name|iTimeStamp
operator|=
name|request
operator|.
name|getTimeStamp
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
operator|new
name|Date
argument_list|(
name|request
operator|.
name|getTimeStamp
argument_list|()
argument_list|)
expr_stmt|;
name|iEnrollment
operator|=
name|enrollment
operator|==
literal|null
condition|?
literal|null
else|:
operator|new
name|XEnrollment
argument_list|(
name|enrollment
argument_list|)
expr_stmt|;
block|}
comment|/**      * List of requested courses (in the correct order -- first is the requested      * course, second is the first alternative, etc.)      */
specifier|public
name|List
argument_list|<
name|XCourseId
argument_list|>
name|getCourseIds
parameter_list|()
block|{
return|return
name|iCourseIds
return|;
block|}
specifier|public
name|boolean
name|hasCourse
parameter_list|(
name|Long
name|courseId
parameter_list|)
block|{
for|for
control|(
name|XCourseId
name|id
range|:
name|iCourseIds
control|)
if|if
condition|(
name|id
operator|.
name|getCourseId
argument_list|()
operator|.
name|equals
argument_list|(
name|courseId
argument_list|)
condition|)
return|return
literal|true
return|;
return|return
literal|false
return|;
block|}
specifier|public
name|Integer
name|getEnrolledCourseIndex
parameter_list|()
block|{
if|if
condition|(
name|iEnrollment
operator|==
literal|null
condition|)
return|return
literal|null
return|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|iCourseIds
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
if|if
condition|(
name|iCourseIds
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|.
name|getCourseId
argument_list|()
operator|.
name|equals
argument_list|(
name|iEnrollment
operator|.
name|getCourseId
argument_list|()
argument_list|)
condition|)
return|return
name|i
return|;
return|return
operator|-
literal|1
return|;
block|}
specifier|public
name|XCourseId
name|getCourseIdByOfferingId
parameter_list|(
name|Long
name|offeringId
parameter_list|)
block|{
for|for
control|(
name|XCourseId
name|id
range|:
name|iCourseIds
control|)
if|if
condition|(
name|id
operator|.
name|getOfferingId
argument_list|()
operator|.
name|equals
argument_list|(
name|offeringId
argument_list|)
condition|)
return|return
name|id
return|;
return|return
literal|null
return|;
block|}
comment|/**      * True if the student can be put on a wait-list (no alternative course      * request will be given instead)      */
specifier|public
name|boolean
name|isWaitlist
parameter_list|()
block|{
return|return
name|iWaitlist
return|;
block|}
comment|/**      * Time stamp of the request      */
specifier|public
name|Date
name|getTimeStamp
parameter_list|()
block|{
return|return
name|iTimeStamp
return|;
block|}
comment|/** Return enrollment, if enrolled */
specifier|public
name|XEnrollment
name|getEnrollment
parameter_list|()
block|{
return|return
name|iEnrollment
return|;
block|}
specifier|public
name|void
name|setEnrollment
parameter_list|(
name|XEnrollment
name|enrollment
parameter_list|)
block|{
name|iEnrollment
operator|=
name|enrollment
expr_stmt|;
block|}
specifier|public
name|void
name|setWaitlist
parameter_list|(
name|boolean
name|waitlist
parameter_list|)
block|{
name|iWaitlist
operator|=
name|waitlist
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasSectionWaitlist
parameter_list|(
name|XCourseId
name|courseId
parameter_list|)
block|{
name|List
argument_list|<
name|XWaitListedSection
argument_list|>
name|sections
init|=
name|getSectionWaitlist
argument_list|(
name|courseId
argument_list|)
decl_stmt|;
return|return
name|sections
operator|!=
literal|null
operator|&&
operator|!
name|sections
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|List
argument_list|<
name|XWaitListedSection
argument_list|>
name|getSectionWaitlist
parameter_list|(
name|XCourseId
name|courseId
parameter_list|)
block|{
return|return
operator|(
name|iSectionWaitlist
operator|==
literal|null
condition|?
literal|null
else|:
name|iSectionWaitlist
operator|.
name|get
argument_list|(
name|courseId
argument_list|)
operator|)
return|;
block|}
specifier|public
name|OnlineSectioningLog
operator|.
name|CourseRequestOption
name|getOptions
parameter_list|(
name|Long
name|offeringId
parameter_list|)
block|{
if|if
condition|(
name|iOptions
operator|==
literal|null
condition|)
return|return
literal|null
return|;
name|XCourseId
name|courseId
init|=
name|getCourseIdByOfferingId
argument_list|(
name|offeringId
argument_list|)
decl_stmt|;
if|if
condition|(
name|courseId
operator|==
literal|null
condition|)
return|return
literal|null
return|;
name|byte
index|[]
name|option
init|=
name|iOptions
operator|.
name|get
argument_list|(
name|courseId
argument_list|)
decl_stmt|;
if|if
condition|(
name|option
operator|!=
literal|null
condition|)
block|{
try|try
block|{
return|return
name|OnlineSectioningLog
operator|.
name|CourseRequestOption
operator|.
name|parseFrom
argument_list|(
name|option
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|InvalidProtocolBufferException
name|e
parameter_list|)
block|{
block|}
block|}
return|return
literal|null
return|;
block|}
specifier|public
name|void
name|fillChoicesIn
parameter_list|(
name|org
operator|.
name|cpsolver
operator|.
name|studentsct
operator|.
name|model
operator|.
name|CourseRequest
name|request
parameter_list|)
block|{
if|if
condition|(
name|iSectionWaitlist
operator|!=
literal|null
condition|)
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|XCourseId
argument_list|,
name|List
argument_list|<
name|XWaitListedSection
argument_list|>
argument_list|>
name|entry
range|:
name|iSectionWaitlist
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|Course
name|course
init|=
name|request
operator|.
name|getCourse
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
operator|.
name|getCourseId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|course
operator|!=
literal|null
condition|)
for|for
control|(
name|XSection
name|section
range|:
name|entry
operator|.
name|getValue
argument_list|()
control|)
name|request
operator|.
name|getSelectedChoices
argument_list|()
operator|.
name|add
argument_list|(
operator|new
name|Choice
argument_list|(
name|course
operator|.
name|getOffering
argument_list|()
argument_list|,
name|section
operator|.
name|getInstructionalType
argument_list|()
argument_list|,
name|section
operator|.
name|getTime
argument_list|()
operator|==
literal|null
operator|||
name|section
operator|.
name|getTime
argument_list|()
operator|.
name|getDays
argument_list|()
operator|==
literal|0
condition|?
literal|null
else|:
name|section
operator|.
name|getTime
argument_list|()
operator|.
name|toTimeLocation
argument_list|()
argument_list|,
name|section
operator|.
name|getInstructorIds
argument_list|()
argument_list|,
name|section
operator|.
name|getInstructorNames
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|String
name|getEnrollmentMessage
parameter_list|()
block|{
return|return
name|iMessage
return|;
block|}
specifier|public
name|void
name|setEnrollmentMessage
parameter_list|(
name|String
name|message
parameter_list|)
block|{
name|iMessage
operator|=
name|message
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
name|String
name|ret
init|=
name|super
operator|.
name|toString
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
argument_list|<
name|XCourseId
argument_list|>
name|i
init|=
name|iCourseIds
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
name|XCourseId
name|c
init|=
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|ret
operator|+=
literal|" "
operator|+
name|c
operator|.
name|getCourseName
argument_list|()
expr_stmt|;
if|if
condition|(
name|i
operator|.
name|hasNext
argument_list|()
condition|)
name|ret
operator|+=
literal|","
expr_stmt|;
block|}
if|if
condition|(
name|isWaitlist
argument_list|()
condition|)
name|ret
operator|+=
literal|" (w)"
expr_stmt|;
name|ret
operator|+=
literal|" ("
operator|+
name|getRequestId
argument_list|()
operator|+
literal|")"
expr_stmt|;
return|return
name|ret
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|readExternal
parameter_list|(
name|ObjectInput
name|in
parameter_list|)
throws|throws
name|IOException
throws|,
name|ClassNotFoundException
block|{
name|super
operator|.
name|readExternal
argument_list|(
name|in
argument_list|)
expr_stmt|;
name|int
name|nrCourses
init|=
name|in
operator|.
name|readInt
argument_list|()
decl_stmt|;
name|iCourseIds
operator|.
name|clear
argument_list|()
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|nrCourses
condition|;
name|i
operator|++
control|)
name|iCourseIds
operator|.
name|add
argument_list|(
operator|new
name|XCourseId
argument_list|(
name|in
argument_list|)
argument_list|)
expr_stmt|;
name|iWaitlist
operator|=
name|in
operator|.
name|readBoolean
argument_list|()
expr_stmt|;
name|iTimeStamp
operator|=
operator|(
name|in
operator|.
name|readBoolean
argument_list|()
condition|?
operator|new
name|Date
argument_list|(
name|in
operator|.
name|readLong
argument_list|()
argument_list|)
else|:
literal|null
operator|)
expr_stmt|;
name|iEnrollment
operator|=
operator|(
name|in
operator|.
name|readBoolean
argument_list|()
condition|?
operator|new
name|XEnrollment
argument_list|(
name|in
argument_list|)
else|:
literal|null
operator|)
expr_stmt|;
name|int
name|nrWaitlists
init|=
name|in
operator|.
name|readInt
argument_list|()
decl_stmt|;
if|if
condition|(
name|nrWaitlists
operator|==
literal|0
condition|)
name|iSectionWaitlist
operator|=
literal|null
expr_stmt|;
else|else
block|{
name|iSectionWaitlist
operator|=
operator|new
name|HashMap
argument_list|<
name|XCourseId
argument_list|,
name|List
argument_list|<
name|XWaitListedSection
argument_list|>
argument_list|>
argument_list|()
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|nrWaitlists
condition|;
name|i
operator|++
control|)
block|{
name|Long
name|courseId
init|=
name|in
operator|.
name|readLong
argument_list|()
decl_stmt|;
name|int
name|nrSections
init|=
name|in
operator|.
name|readInt
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|XWaitListedSection
argument_list|>
name|sections
init|=
operator|new
name|ArrayList
argument_list|<
name|XWaitListedSection
argument_list|>
argument_list|(
name|nrSections
argument_list|)
decl_stmt|;
for|for
control|(
name|int
name|j
init|=
literal|0
init|;
name|j
operator|<
name|nrSections
condition|;
name|j
operator|++
control|)
name|sections
operator|.
name|add
argument_list|(
operator|new
name|XWaitListedSection
argument_list|(
name|in
argument_list|)
argument_list|)
expr_stmt|;
for|for
control|(
name|XCourseId
name|course
range|:
name|iCourseIds
control|)
if|if
condition|(
name|course
operator|.
name|getCourseId
argument_list|()
operator|.
name|equals
argument_list|(
name|courseId
argument_list|)
condition|)
block|{
name|iSectionWaitlist
operator|.
name|put
argument_list|(
name|course
argument_list|,
name|sections
argument_list|)
expr_stmt|;
break|break;
block|}
block|}
block|}
name|int
name|nrOptions
init|=
name|in
operator|.
name|readInt
argument_list|()
decl_stmt|;
if|if
condition|(
name|nrOptions
operator|==
literal|0
condition|)
name|iOptions
operator|=
literal|null
expr_stmt|;
else|else
block|{
name|iOptions
operator|=
operator|new
name|HashMap
argument_list|<
name|XCourseId
argument_list|,
name|byte
index|[]
argument_list|>
argument_list|()
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|nrOptions
condition|;
name|i
operator|++
control|)
block|{
name|Long
name|courseId
init|=
name|in
operator|.
name|readLong
argument_list|()
decl_stmt|;
name|byte
index|[]
name|data
init|=
operator|new
name|byte
index|[
name|in
operator|.
name|readInt
argument_list|()
index|]
decl_stmt|;
name|in
operator|.
name|read
argument_list|(
name|data
argument_list|)
expr_stmt|;
for|for
control|(
name|XCourseId
name|course
range|:
name|iCourseIds
control|)
if|if
condition|(
name|course
operator|.
name|getCourseId
argument_list|()
operator|.
name|equals
argument_list|(
name|courseId
argument_list|)
condition|)
block|{
name|iOptions
operator|.
name|put
argument_list|(
name|course
argument_list|,
name|data
argument_list|)
expr_stmt|;
break|break;
block|}
block|}
block|}
name|iMessage
operator|=
operator|(
name|String
operator|)
name|in
operator|.
name|readObject
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|writeExternal
parameter_list|(
name|ObjectOutput
name|out
parameter_list|)
throws|throws
name|IOException
block|{
name|super
operator|.
name|writeExternal
argument_list|(
name|out
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeInt
argument_list|(
name|iCourseIds
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|XCourseId
name|course
range|:
name|iCourseIds
control|)
name|course
operator|.
name|writeExternal
argument_list|(
name|out
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeBoolean
argument_list|(
name|iWaitlist
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeBoolean
argument_list|(
name|iTimeStamp
operator|!=
literal|null
argument_list|)
expr_stmt|;
if|if
condition|(
name|iTimeStamp
operator|!=
literal|null
condition|)
name|out
operator|.
name|writeLong
argument_list|(
name|iTimeStamp
operator|.
name|getTime
argument_list|()
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeBoolean
argument_list|(
name|iEnrollment
operator|!=
literal|null
argument_list|)
expr_stmt|;
if|if
condition|(
name|iEnrollment
operator|!=
literal|null
condition|)
name|iEnrollment
operator|.
name|writeExternal
argument_list|(
name|out
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeInt
argument_list|(
name|iSectionWaitlist
operator|==
literal|null
condition|?
literal|0
else|:
name|iSectionWaitlist
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|iSectionWaitlist
operator|!=
literal|null
condition|)
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|XCourseId
argument_list|,
name|List
argument_list|<
name|XWaitListedSection
argument_list|>
argument_list|>
name|entry
range|:
name|iSectionWaitlist
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|out
operator|.
name|writeLong
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
operator|.
name|getCourseId
argument_list|()
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeInt
argument_list|(
name|entry
operator|.
name|getValue
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|XWaitListedSection
name|section
range|:
name|entry
operator|.
name|getValue
argument_list|()
control|)
block|{
name|section
operator|.
name|writeExternal
argument_list|(
name|out
argument_list|)
expr_stmt|;
block|}
block|}
name|out
operator|.
name|writeInt
argument_list|(
name|iOptions
operator|==
literal|null
condition|?
literal|0
else|:
name|iOptions
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|iOptions
operator|!=
literal|null
condition|)
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|XCourseId
argument_list|,
name|byte
index|[]
argument_list|>
name|entry
range|:
name|iOptions
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|out
operator|.
name|writeLong
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
operator|.
name|getCourseId
argument_list|()
argument_list|)
expr_stmt|;
name|byte
index|[]
name|value
init|=
name|entry
operator|.
name|getValue
argument_list|()
decl_stmt|;
name|out
operator|.
name|writeInt
argument_list|(
name|value
operator|.
name|length
argument_list|)
expr_stmt|;
name|out
operator|.
name|write
argument_list|(
name|value
argument_list|)
expr_stmt|;
block|}
name|out
operator|.
name|writeObject
argument_list|(
name|iMessage
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
class|class
name|XCourseRequestSerializer
implements|implements
name|Externalizer
argument_list|<
name|XCourseRequest
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
annotation|@
name|Override
specifier|public
name|void
name|writeObject
parameter_list|(
name|ObjectOutput
name|output
parameter_list|,
name|XCourseRequest
name|object
parameter_list|)
throws|throws
name|IOException
block|{
name|object
operator|.
name|writeExternal
argument_list|(
name|output
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|XCourseRequest
name|readObject
parameter_list|(
name|ObjectInput
name|input
parameter_list|)
throws|throws
name|IOException
throws|,
name|ClassNotFoundException
block|{
return|return
operator|new
name|XCourseRequest
argument_list|(
name|input
argument_list|)
return|;
block|}
block|}
block|}
end_class

end_unit

