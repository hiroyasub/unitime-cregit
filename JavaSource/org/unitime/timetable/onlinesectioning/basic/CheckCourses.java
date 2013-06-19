begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.3 (University Timetabling Application)  * Copyright (C) 2011, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|CourseRequest
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
name|onlinesectioning
operator|.
name|CourseInfo
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
name|CourseInfoMatcher
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

begin_class
specifier|public
class|class
name|CheckCourses
implements|implements
name|OnlineSectioningAction
argument_list|<
name|Collection
argument_list|<
name|String
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
name|CourseRequestInterface
name|iRequest
decl_stmt|;
specifier|private
name|CourseInfoMatcher
name|iMatcher
decl_stmt|;
specifier|public
name|CheckCourses
parameter_list|(
name|CourseRequestInterface
name|request
parameter_list|,
name|CourseInfoMatcher
name|matcher
parameter_list|)
block|{
name|iRequest
operator|=
name|request
expr_stmt|;
name|iMatcher
operator|=
name|matcher
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|Collection
argument_list|<
name|String
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
name|ArrayList
argument_list|<
name|String
argument_list|>
name|notFound
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|Lock
name|lock
init|=
operator|(
name|iRequest
operator|.
name|getStudentId
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
name|server
operator|.
name|lockStudent
argument_list|(
name|iRequest
operator|.
name|getStudentId
argument_list|()
argument_list|,
literal|null
argument_list|,
literal|true
argument_list|)
operator|)
decl_stmt|;
try|try
block|{
name|Student
name|student
init|=
operator|(
name|iRequest
operator|.
name|getStudentId
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
name|server
operator|.
name|getStudent
argument_list|(
name|iRequest
operator|.
name|getStudentId
argument_list|()
argument_list|)
operator|)
decl_stmt|;
for|for
control|(
name|CourseRequestInterface
operator|.
name|Request
name|cr
range|:
name|iRequest
operator|.
name|getCourses
argument_list|()
control|)
block|{
if|if
condition|(
operator|!
name|cr
operator|.
name|hasRequestedFreeTime
argument_list|()
operator|&&
name|cr
operator|.
name|hasRequestedCourse
argument_list|()
operator|&&
name|lookup
argument_list|(
name|server
argument_list|,
name|student
argument_list|,
name|cr
operator|.
name|getRequestedCourse
argument_list|()
argument_list|)
operator|==
literal|null
condition|)
name|notFound
operator|.
name|add
argument_list|(
name|cr
operator|.
name|getRequestedCourse
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|cr
operator|.
name|hasFirstAlternative
argument_list|()
operator|&&
name|lookup
argument_list|(
name|server
argument_list|,
name|student
argument_list|,
name|cr
operator|.
name|getFirstAlternative
argument_list|()
argument_list|)
operator|==
literal|null
condition|)
name|notFound
operator|.
name|add
argument_list|(
name|cr
operator|.
name|getFirstAlternative
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|cr
operator|.
name|hasSecondAlternative
argument_list|()
operator|&&
name|lookup
argument_list|(
name|server
argument_list|,
name|student
argument_list|,
name|cr
operator|.
name|getSecondAlternative
argument_list|()
argument_list|)
operator|==
literal|null
condition|)
name|notFound
operator|.
name|add
argument_list|(
name|cr
operator|.
name|getSecondAlternative
argument_list|()
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|CourseRequestInterface
operator|.
name|Request
name|cr
range|:
name|iRequest
operator|.
name|getAlternatives
argument_list|()
control|)
block|{
if|if
condition|(
name|cr
operator|.
name|hasRequestedCourse
argument_list|()
operator|&&
name|lookup
argument_list|(
name|server
argument_list|,
name|student
argument_list|,
name|cr
operator|.
name|getRequestedCourse
argument_list|()
argument_list|)
operator|==
literal|null
condition|)
name|notFound
operator|.
name|add
argument_list|(
name|cr
operator|.
name|getRequestedCourse
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|cr
operator|.
name|hasFirstAlternative
argument_list|()
operator|&&
name|lookup
argument_list|(
name|server
argument_list|,
name|student
argument_list|,
name|cr
operator|.
name|getFirstAlternative
argument_list|()
argument_list|)
operator|==
literal|null
condition|)
name|notFound
operator|.
name|add
argument_list|(
name|cr
operator|.
name|getFirstAlternative
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|cr
operator|.
name|hasSecondAlternative
argument_list|()
operator|&&
name|lookup
argument_list|(
name|server
argument_list|,
name|student
argument_list|,
name|cr
operator|.
name|getSecondAlternative
argument_list|()
argument_list|)
operator|==
literal|null
condition|)
name|notFound
operator|.
name|add
argument_list|(
name|cr
operator|.
name|getSecondAlternative
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|notFound
return|;
block|}
finally|finally
block|{
if|if
condition|(
name|lock
operator|!=
literal|null
condition|)
name|lock
operator|.
name|release
argument_list|()
expr_stmt|;
block|}
block|}
specifier|public
name|CourseInfo
name|lookup
parameter_list|(
name|OnlineSectioningServer
name|server
parameter_list|,
name|Student
name|student
parameter_list|,
name|String
name|course
parameter_list|)
block|{
name|CourseInfo
name|c
init|=
name|server
operator|.
name|getCourseInfo
argument_list|(
name|course
argument_list|)
decl_stmt|;
if|if
condition|(
name|c
operator|!=
literal|null
operator|&&
name|iMatcher
operator|!=
literal|null
operator|&&
operator|!
name|iMatcher
operator|.
name|match
argument_list|(
name|c
argument_list|)
condition|)
block|{
if|if
condition|(
name|student
operator|!=
literal|null
condition|)
block|{
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
name|x
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
name|x
operator|.
name|getId
argument_list|()
operator|==
name|c
operator|.
name|getUniqueId
argument_list|()
condition|)
return|return
name|c
return|;
comment|// already requested
block|}
block|}
block|}
return|return
literal|null
return|;
block|}
return|return
name|c
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
literal|"check-courses"
return|;
block|}
block|}
end_class

end_unit

