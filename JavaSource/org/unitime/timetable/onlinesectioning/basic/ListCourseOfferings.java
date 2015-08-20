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
name|basic
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
name|onlinesectioning
operator|.
name|OnlineSectioningAction
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
name|OnlineSectioningServer
operator|.
name|Lock
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

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|ListCourseOfferings
implements|implements
name|OnlineSectioningAction
argument_list|<
name|Collection
argument_list|<
name|ClassAssignmentInterface
operator|.
name|CourseAssignment
argument_list|>
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
name|iQuery
init|=
literal|null
decl_stmt|;
specifier|private
name|Integer
name|iLimit
init|=
literal|null
decl_stmt|;
specifier|private
name|CourseMatcher
name|iMatcher
init|=
literal|null
decl_stmt|;
specifier|public
name|ListCourseOfferings
name|forQuery
parameter_list|(
name|String
name|query
parameter_list|)
block|{
name|iQuery
operator|=
name|query
expr_stmt|;
return|return
name|this
return|;
block|}
specifier|public
name|ListCourseOfferings
name|withLimit
parameter_list|(
name|Integer
name|limit
parameter_list|)
block|{
name|iLimit
operator|=
name|limit
expr_stmt|;
return|return
name|this
return|;
block|}
specifier|public
name|ListCourseOfferings
name|withMatcher
parameter_list|(
name|CourseMatcher
name|matcher
parameter_list|)
block|{
name|iMatcher
operator|=
name|matcher
expr_stmt|;
return|return
name|this
return|;
block|}
annotation|@
name|Override
specifier|public
name|Collection
argument_list|<
name|CourseAssignment
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
name|Lock
name|lock
init|=
name|server
operator|.
name|readLock
argument_list|()
decl_stmt|;
try|try
block|{
name|ArrayList
argument_list|<
name|ClassAssignmentInterface
operator|.
name|CourseAssignment
argument_list|>
name|ret
init|=
operator|new
name|ArrayList
argument_list|<
name|ClassAssignmentInterface
operator|.
name|CourseAssignment
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|XCourseId
name|id
range|:
name|server
operator|.
name|findCourses
argument_list|(
name|iQuery
argument_list|,
name|iLimit
argument_list|,
name|iMatcher
argument_list|)
control|)
block|{
name|XCourse
name|c
init|=
name|server
operator|.
name|getCourse
argument_list|(
name|id
operator|.
name|getCourseId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|c
operator|==
literal|null
condition|)
continue|continue;
name|CourseAssignment
name|course
init|=
operator|new
name|CourseAssignment
argument_list|()
decl_stmt|;
name|course
operator|.
name|setCourseId
argument_list|(
name|c
operator|.
name|getCourseId
argument_list|()
argument_list|)
expr_stmt|;
name|course
operator|.
name|setSubject
argument_list|(
name|c
operator|.
name|getSubjectArea
argument_list|()
argument_list|)
expr_stmt|;
name|course
operator|.
name|setCourseNbr
argument_list|(
name|c
operator|.
name|getCourseNumber
argument_list|()
argument_list|)
expr_stmt|;
name|course
operator|.
name|setTitle
argument_list|(
name|c
operator|.
name|getTitle
argument_list|()
argument_list|)
expr_stmt|;
name|course
operator|.
name|setNote
argument_list|(
name|c
operator|.
name|getNote
argument_list|()
argument_list|)
expr_stmt|;
name|course
operator|.
name|setCreditAbbv
argument_list|(
name|c
operator|.
name|getCreditAbbv
argument_list|()
argument_list|)
expr_stmt|;
name|course
operator|.
name|setCreditText
argument_list|(
name|c
operator|.
name|getCreditText
argument_list|()
argument_list|)
expr_stmt|;
name|course
operator|.
name|setTitle
argument_list|(
name|c
operator|.
name|getTitle
argument_list|()
argument_list|)
expr_stmt|;
name|course
operator|.
name|setHasUniqueName
argument_list|(
name|c
operator|.
name|hasUniqueName
argument_list|()
argument_list|)
expr_stmt|;
name|course
operator|.
name|setLimit
argument_list|(
name|c
operator|.
name|getLimit
argument_list|()
argument_list|)
expr_stmt|;
name|Collection
argument_list|<
name|XCourseRequest
argument_list|>
name|requests
init|=
name|server
operator|.
name|getRequests
argument_list|(
name|c
operator|.
name|getOfferingId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|requests
operator|!=
literal|null
condition|)
block|{
name|int
name|enrl
init|=
literal|0
decl_stmt|;
for|for
control|(
name|XCourseRequest
name|r
range|:
name|requests
control|)
if|if
condition|(
name|r
operator|.
name|getEnrollment
argument_list|()
operator|!=
literal|null
operator|&&
name|r
operator|.
name|getEnrollment
argument_list|()
operator|.
name|getCourseId
argument_list|()
operator|.
name|equals
argument_list|(
name|course
operator|.
name|getCourseId
argument_list|()
argument_list|)
condition|)
name|enrl
operator|++
expr_stmt|;
name|course
operator|.
name|setEnrollment
argument_list|(
name|enrl
argument_list|)
expr_stmt|;
block|}
name|ret
operator|.
name|add
argument_list|(
name|course
argument_list|)
expr_stmt|;
block|}
return|return
name|ret
return|;
block|}
finally|finally
block|{
name|lock
operator|.
name|release
argument_list|()
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|String
name|name
parameter_list|()
block|{
return|return
literal|"list-courses"
return|;
block|}
block|}
end_class

end_unit
