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
name|custom
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
name|ToolBox
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
name|OnlineSectioningInterface
operator|.
name|EligibilityCheck
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
name|solver
operator|.
name|SectioningRequest
import|;
end_import

begin_interface
specifier|public
interface|interface
name|StudentEnrollmentProvider
block|{
specifier|public
name|void
name|checkEligibility
parameter_list|(
name|OnlineSectioningServer
name|server
parameter_list|,
name|OnlineSectioningHelper
name|helper
parameter_list|,
name|EligibilityCheck
name|check
parameter_list|,
name|XStudent
name|student
parameter_list|)
throws|throws
name|SectioningException
function_decl|;
specifier|public
name|List
argument_list|<
name|EnrollmentFailure
argument_list|>
name|enroll
parameter_list|(
name|OnlineSectioningServer
name|server
parameter_list|,
name|OnlineSectioningHelper
name|helper
parameter_list|,
name|XStudent
name|student
parameter_list|,
name|List
argument_list|<
name|EnrollmentRequest
argument_list|>
name|enrollments
parameter_list|,
name|Set
argument_list|<
name|Long
argument_list|>
name|lockedCourses
parameter_list|)
throws|throws
name|SectioningException
function_decl|;
specifier|public
name|XEnrollment
name|resection
parameter_list|(
name|OnlineSectioningServer
name|server
parameter_list|,
name|OnlineSectioningHelper
name|helper
parameter_list|,
name|SectioningRequest
name|sectioningRequest
parameter_list|,
name|XEnrollment
name|enrollment
parameter_list|)
throws|throws
name|SectioningException
function_decl|;
specifier|public
name|boolean
name|requestUpdate
parameter_list|(
name|OnlineSectioningServer
name|server
parameter_list|,
name|OnlineSectioningHelper
name|helper
parameter_list|,
name|Collection
argument_list|<
name|XStudent
argument_list|>
name|students
parameter_list|)
throws|throws
name|SectioningException
function_decl|;
specifier|public
name|void
name|dispose
parameter_list|()
function_decl|;
specifier|public
name|boolean
name|isAllowWaitListing
parameter_list|()
function_decl|;
specifier|public
name|boolean
name|isCanRequestUpdates
parameter_list|()
function_decl|;
specifier|public
specifier|static
class|class
name|EnrollmentError
implements|implements
name|Serializable
implements|,
name|Comparable
argument_list|<
name|EnrollmentError
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
specifier|private
name|String
name|iCode
decl_stmt|;
specifier|private
name|String
name|iMessage
decl_stmt|;
specifier|public
name|EnrollmentError
parameter_list|(
name|String
name|code
parameter_list|,
name|String
name|message
parameter_list|)
block|{
name|iCode
operator|=
name|code
expr_stmt|;
name|iMessage
operator|=
name|message
expr_stmt|;
block|}
specifier|public
name|String
name|getCode
parameter_list|()
block|{
return|return
name|iCode
return|;
block|}
specifier|public
name|String
name|getMessage
parameter_list|()
block|{
return|return
name|iMessage
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|compareTo
parameter_list|(
name|EnrollmentError
name|ee
parameter_list|)
block|{
name|int
name|cmp
init|=
operator|(
name|getCode
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|getCode
argument_list|()
operator|)
operator|.
name|compareTo
argument_list|(
name|ee
operator|.
name|getCode
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|ee
operator|.
name|getCode
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
name|getMessage
argument_list|()
operator|.
name|compareTo
argument_list|(
name|ee
operator|.
name|getMessage
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|getCode
argument_list|()
operator|+
literal|": "
operator|+
name|getMessage
argument_list|()
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|EnrollmentFailure
implements|implements
name|Serializable
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
name|XCourse
name|iCourse
decl_stmt|;
specifier|private
name|XSection
name|iSection
decl_stmt|;
specifier|private
name|String
name|iMessage
decl_stmt|;
specifier|private
name|boolean
name|iEnrolled
decl_stmt|;
specifier|private
name|List
argument_list|<
name|EnrollmentError
argument_list|>
name|iErrors
decl_stmt|;
specifier|public
name|EnrollmentFailure
parameter_list|(
name|XCourse
name|course
parameter_list|,
name|XSection
name|section
parameter_list|,
name|String
name|message
parameter_list|,
name|boolean
name|enrolled
parameter_list|,
name|Collection
argument_list|<
name|EnrollmentError
argument_list|>
name|errors
parameter_list|)
block|{
name|this
argument_list|(
name|course
argument_list|,
name|section
argument_list|,
name|message
argument_list|,
name|enrolled
argument_list|)
expr_stmt|;
if|if
condition|(
name|errors
operator|!=
literal|null
condition|)
name|iErrors
operator|=
operator|new
name|ArrayList
argument_list|<
name|EnrollmentError
argument_list|>
argument_list|(
name|errors
argument_list|)
expr_stmt|;
block|}
specifier|public
name|EnrollmentFailure
parameter_list|(
name|XCourse
name|course
parameter_list|,
name|XSection
name|section
parameter_list|,
name|String
name|message
parameter_list|,
name|boolean
name|enrolled
parameter_list|,
name|EnrollmentError
modifier|...
name|errors
parameter_list|)
block|{
name|this
argument_list|(
name|course
argument_list|,
name|section
argument_list|,
name|message
argument_list|,
name|enrolled
argument_list|)
expr_stmt|;
if|if
condition|(
name|errors
operator|!=
literal|null
condition|)
block|{
name|iErrors
operator|=
operator|new
name|ArrayList
argument_list|<
name|EnrollmentError
argument_list|>
argument_list|()
expr_stmt|;
for|for
control|(
name|EnrollmentError
name|error
range|:
name|errors
control|)
name|iErrors
operator|.
name|add
argument_list|(
name|error
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|EnrollmentFailure
parameter_list|(
name|XCourse
name|course
parameter_list|,
name|XSection
name|section
parameter_list|,
name|String
name|message
parameter_list|,
name|boolean
name|enrolled
parameter_list|)
block|{
name|iCourse
operator|=
name|course
expr_stmt|;
name|iSection
operator|=
name|section
expr_stmt|;
name|iMessage
operator|=
name|message
expr_stmt|;
name|iEnrolled
operator|=
name|enrolled
expr_stmt|;
block|}
specifier|public
name|XCourse
name|getCourse
parameter_list|()
block|{
return|return
name|iCourse
return|;
block|}
specifier|public
name|XSection
name|getSection
parameter_list|()
block|{
return|return
name|iSection
return|;
block|}
specifier|public
name|String
name|getMessage
parameter_list|()
block|{
return|return
name|iMessage
return|;
block|}
specifier|public
name|boolean
name|isEnrolled
parameter_list|()
block|{
return|return
name|iEnrolled
return|;
block|}
specifier|public
name|boolean
name|hasErrors
parameter_list|()
block|{
return|return
name|iErrors
operator|!=
literal|null
operator|&&
operator|!
name|iErrors
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|void
name|addError
parameter_list|(
name|String
name|code
parameter_list|,
name|String
name|message
parameter_list|)
block|{
name|addError
argument_list|(
operator|new
name|EnrollmentError
argument_list|(
name|code
argument_list|,
name|message
argument_list|)
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|addError
parameter_list|(
name|EnrollmentError
name|error
parameter_list|)
block|{
if|if
condition|(
name|iErrors
operator|==
literal|null
condition|)
name|iErrors
operator|=
operator|new
name|ArrayList
argument_list|<
name|EnrollmentError
argument_list|>
argument_list|()
expr_stmt|;
name|iErrors
operator|.
name|add
argument_list|(
name|error
argument_list|)
expr_stmt|;
block|}
specifier|public
name|List
argument_list|<
name|EnrollmentError
argument_list|>
name|getErrors
parameter_list|()
block|{
return|return
name|iErrors
return|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|getCourse
argument_list|()
operator|.
name|getCourseName
argument_list|()
operator|+
literal|" "
operator|+
name|getSection
argument_list|()
operator|.
name|getSubpartName
argument_list|()
operator|+
literal|" "
operator|+
name|getSection
argument_list|()
operator|.
name|getName
argument_list|(
name|getCourse
argument_list|()
operator|.
name|getCourseId
argument_list|()
argument_list|)
operator|+
literal|": "
operator|+
name|getMessage
argument_list|()
operator|+
operator|(
name|isEnrolled
argument_list|()
condition|?
literal|" (e)"
else|:
literal|""
operator|)
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|EnrollmentRequest
implements|implements
name|Serializable
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
name|XCourse
name|iCourse
decl_stmt|;
specifier|private
name|List
argument_list|<
name|XSection
argument_list|>
name|iSections
decl_stmt|;
specifier|public
name|EnrollmentRequest
parameter_list|(
name|XCourse
name|course
parameter_list|,
name|List
argument_list|<
name|XSection
argument_list|>
name|sections
parameter_list|)
block|{
name|iCourse
operator|=
name|course
expr_stmt|;
name|iSections
operator|=
name|sections
expr_stmt|;
block|}
specifier|public
name|XCourse
name|getCourse
parameter_list|()
block|{
return|return
name|iCourse
return|;
block|}
specifier|public
name|List
argument_list|<
name|XSection
argument_list|>
name|getSections
parameter_list|()
block|{
return|return
name|iSections
return|;
block|}
specifier|public
name|float
name|getCredit
parameter_list|()
block|{
name|Float
name|sectionCredit
init|=
literal|null
decl_stmt|;
for|for
control|(
name|XSection
name|s
range|:
name|getSections
argument_list|()
control|)
block|{
name|Float
name|credit
init|=
name|s
operator|.
name|getCreditOverride
argument_list|(
name|getCourse
argument_list|()
operator|.
name|getCourseId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|credit
operator|!=
literal|null
condition|)
block|{
name|sectionCredit
operator|=
operator|(
name|sectionCredit
operator|==
literal|null
condition|?
literal|0f
else|:
name|sectionCredit
operator|.
name|floatValue
argument_list|()
operator|)
operator|+
name|credit
expr_stmt|;
block|}
block|}
if|if
condition|(
name|sectionCredit
operator|!=
literal|null
condition|)
return|return
name|sectionCredit
return|;
if|if
condition|(
name|getCourse
argument_list|()
operator|.
name|hasCredit
argument_list|()
condition|)
return|return
name|getCourse
argument_list|()
operator|.
name|getMinCredit
argument_list|()
return|;
return|return
literal|0f
return|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|getCourse
argument_list|()
operator|.
name|getCourseName
argument_list|()
operator|+
literal|": "
operator|+
name|ToolBox
operator|.
name|col2string
argument_list|(
name|getSections
argument_list|()
argument_list|,
literal|2
argument_list|)
return|;
block|}
block|}
block|}
end_interface

end_unit

