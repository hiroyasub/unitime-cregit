begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.5 (University Timetabling Application)  * Copyright (C) 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|updates
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
name|AnyCourseMatcher
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
name|AnyStudentMatcher
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
name|XRequest
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

begin_class
annotation|@
name|CheckMaster
argument_list|(
name|Master
operator|.
name|REQUIRED
argument_list|)
specifier|public
class|class
name|CheckEnrollmentConsistencyAction
implements|implements
name|OnlineSectioningAction
argument_list|<
name|Boolean
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
name|Boolean
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
name|List
argument_list|<
name|String
argument_list|>
name|problems
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
try|try
block|{
for|for
control|(
name|XCourseId
name|courseId
range|:
name|server
operator|.
name|findCourses
argument_list|(
operator|new
name|AnyCourseMatcher
argument_list|()
argument_list|)
control|)
block|{
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
name|courseId
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
for|for
control|(
name|XCourseRequest
name|request
range|:
name|requests
control|)
block|{
name|XStudent
name|student
init|=
name|server
operator|.
name|getStudent
argument_list|(
name|request
operator|.
name|getStudentId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|student
operator|.
name|getRequests
argument_list|()
operator|.
name|contains
argument_list|(
name|request
argument_list|)
condition|)
block|{
name|helper
operator|.
name|error
argument_list|(
literal|"Student "
operator|+
name|student
operator|+
literal|" is missing request "
operator|+
name|request
argument_list|)
expr_stmt|;
name|problems
operator|.
name|add
argument_list|(
literal|"Student "
operator|+
name|student
operator|+
literal|" is missing request "
operator|+
name|request
argument_list|)
expr_stmt|;
block|}
block|}
block|}
for|for
control|(
name|XStudentId
name|studentId
range|:
name|server
operator|.
name|findStudents
argument_list|(
operator|new
name|AnyStudentMatcher
argument_list|()
argument_list|)
control|)
block|{
name|XStudent
name|student
init|=
name|server
operator|.
name|getStudent
argument_list|(
name|studentId
operator|.
name|getStudentId
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|XRequest
name|request
range|:
name|student
operator|.
name|getRequests
argument_list|()
control|)
if|if
condition|(
name|request
operator|instanceof
name|XCourseRequest
condition|)
block|{
name|XCourseRequest
name|cr
init|=
operator|(
name|XCourseRequest
operator|)
name|request
decl_stmt|;
for|for
control|(
name|XCourseId
name|course
range|:
name|cr
operator|.
name|getCourseIds
argument_list|()
control|)
block|{
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
name|course
operator|.
name|getOfferingId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|requests
operator|==
literal|null
operator|||
operator|!
name|requests
operator|.
name|contains
argument_list|(
name|cr
argument_list|)
condition|)
block|{
name|helper
operator|.
name|error
argument_list|(
literal|"Offering "
operator|+
name|course
operator|+
literal|" is missing request "
operator|+
name|request
operator|+
literal|" from "
operator|+
name|student
argument_list|)
expr_stmt|;
name|problems
operator|.
name|add
argument_list|(
literal|"Offering "
operator|+
name|course
operator|+
literal|" is missing request "
operator|+
name|request
operator|+
literal|" from "
operator|+
name|student
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
block|}
finally|finally
block|{
name|lock
operator|.
name|release
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|problems
operator|.
name|isEmpty
argument_list|()
condition|)
throw|throw
operator|new
name|SectioningException
argument_list|(
literal|"Consistency check failed: "
operator|+
name|ToolBox
operator|.
name|col2string
argument_list|(
name|problems
argument_list|,
literal|2
argument_list|)
argument_list|)
throw|;
return|return
literal|true
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|name
parameter_list|()
block|{
return|return
literal|"check-consistency"
return|;
block|}
block|}
end_class

end_unit

