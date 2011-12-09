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
name|Comparator
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
name|FreeTimeRequest
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
name|Lock
import|;
end_import

begin_class
specifier|public
class|class
name|GetRequest
implements|implements
name|OnlineSectioningAction
argument_list|<
name|CourseRequestInterface
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
name|Long
name|iStudentId
decl_stmt|;
specifier|public
name|GetRequest
parameter_list|(
name|Long
name|studentId
parameter_list|)
block|{
name|iStudentId
operator|=
name|studentId
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|CourseRequestInterface
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
name|Student
name|student
init|=
name|server
operator|.
name|getStudent
argument_list|(
name|iStudentId
argument_list|)
decl_stmt|;
if|if
condition|(
name|student
operator|==
literal|null
condition|)
return|return
literal|null
return|;
name|CourseRequestInterface
name|request
init|=
operator|new
name|CourseRequestInterface
argument_list|()
decl_stmt|;
name|request
operator|.
name|setStudentId
argument_list|(
name|iStudentId
argument_list|)
expr_stmt|;
name|request
operator|.
name|setSaved
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|request
operator|.
name|setAcademicSessionId
argument_list|(
name|server
operator|.
name|getAcademicSession
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|TreeSet
argument_list|<
name|Request
argument_list|>
name|requests
init|=
operator|new
name|TreeSet
argument_list|<
name|Request
argument_list|>
argument_list|(
operator|new
name|Comparator
argument_list|<
name|Request
argument_list|>
argument_list|()
block|{
specifier|public
name|int
name|compare
parameter_list|(
name|Request
name|d1
parameter_list|,
name|Request
name|d2
parameter_list|)
block|{
if|if
condition|(
name|d1
operator|.
name|isAlternative
argument_list|()
operator|&&
operator|!
name|d2
operator|.
name|isAlternative
argument_list|()
condition|)
return|return
literal|1
return|;
if|if
condition|(
operator|!
name|d1
operator|.
name|isAlternative
argument_list|()
operator|&&
name|d2
operator|.
name|isAlternative
argument_list|()
condition|)
return|return
operator|-
literal|1
return|;
name|int
name|cmp
init|=
operator|new
name|Integer
argument_list|(
name|d1
operator|.
name|getPriority
argument_list|()
argument_list|)
operator|.
name|compareTo
argument_list|(
name|d2
operator|.
name|getPriority
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
name|d1
operator|.
name|getId
argument_list|()
argument_list|)
operator|.
name|compareTo
argument_list|(
name|d2
operator|.
name|getId
argument_list|()
argument_list|)
return|;
block|}
block|}
argument_list|)
decl_stmt|;
name|requests
operator|.
name|addAll
argument_list|(
name|student
operator|.
name|getRequests
argument_list|()
argument_list|)
expr_stmt|;
name|CourseRequestInterface
operator|.
name|Request
name|lastRequest
init|=
literal|null
decl_stmt|;
name|int
name|lastRequestPriority
init|=
operator|-
literal|1
decl_stmt|;
for|for
control|(
name|Request
name|cd
range|:
name|requests
control|)
block|{
name|CourseRequestInterface
operator|.
name|Request
name|r
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|cd
operator|instanceof
name|FreeTimeRequest
condition|)
block|{
name|FreeTimeRequest
name|ftr
init|=
operator|(
name|FreeTimeRequest
operator|)
name|cd
decl_stmt|;
name|CourseRequestInterface
operator|.
name|FreeTime
name|ft
init|=
operator|new
name|CourseRequestInterface
operator|.
name|FreeTime
argument_list|()
decl_stmt|;
name|ft
operator|.
name|setStart
argument_list|(
name|ftr
operator|.
name|getTime
argument_list|()
operator|.
name|getStartSlot
argument_list|()
argument_list|)
expr_stmt|;
name|ft
operator|.
name|setLength
argument_list|(
name|ftr
operator|.
name|getTime
argument_list|()
operator|.
name|getLength
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|DayCode
name|day
range|:
name|DayCode
operator|.
name|toDayCodes
argument_list|(
name|ftr
operator|.
name|getTime
argument_list|()
operator|.
name|getDayCode
argument_list|()
argument_list|)
control|)
name|ft
operator|.
name|addDay
argument_list|(
name|day
operator|.
name|getIndex
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|lastRequest
operator|!=
literal|null
operator|&&
name|lastRequestPriority
operator|==
name|cd
operator|.
name|getPriority
argument_list|()
condition|)
block|{
name|r
operator|=
name|lastRequest
expr_stmt|;
name|lastRequest
operator|.
name|addRequestedFreeTime
argument_list|(
name|ft
argument_list|)
expr_stmt|;
name|lastRequest
operator|.
name|setRequestedCourse
argument_list|(
name|lastRequest
operator|.
name|getRequestedCourse
argument_list|()
operator|+
literal|", "
operator|+
name|ft
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|r
operator|=
operator|new
name|CourseRequestInterface
operator|.
name|Request
argument_list|()
expr_stmt|;
name|r
operator|.
name|addRequestedFreeTime
argument_list|(
name|ft
argument_list|)
expr_stmt|;
name|r
operator|.
name|setRequestedCourse
argument_list|(
name|ft
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|cd
operator|.
name|isAlternative
argument_list|()
condition|)
name|request
operator|.
name|getAlternatives
argument_list|()
operator|.
name|add
argument_list|(
name|r
argument_list|)
expr_stmt|;
else|else
name|request
operator|.
name|getCourses
argument_list|()
operator|.
name|add
argument_list|(
name|r
argument_list|)
expr_stmt|;
block|}
block|}
if|else if
condition|(
name|cd
operator|instanceof
name|CourseRequest
condition|)
block|{
name|r
operator|=
operator|new
name|CourseRequestInterface
operator|.
name|Request
argument_list|()
expr_stmt|;
name|int
name|order
init|=
literal|0
decl_stmt|;
for|for
control|(
name|Course
name|course
range|:
operator|(
operator|(
name|CourseRequest
operator|)
name|cd
operator|)
operator|.
name|getCourses
argument_list|()
control|)
block|{
name|CourseInfo
name|c
init|=
name|server
operator|.
name|getCourseInfo
argument_list|(
name|course
operator|.
name|getId
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
switch|switch
condition|(
name|order
condition|)
block|{
case|case
literal|0
case|:
name|r
operator|.
name|setRequestedCourse
argument_list|(
name|c
operator|.
name|getSubjectArea
argument_list|()
operator|+
literal|" "
operator|+
name|c
operator|.
name|getCourseNbr
argument_list|()
operator|+
operator|(
name|c
operator|.
name|hasUniqueName
argument_list|()
condition|?
literal|""
else|:
literal|" - "
operator|+
name|c
operator|.
name|getTitle
argument_list|()
operator|)
argument_list|)
expr_stmt|;
break|break;
case|case
literal|1
case|:
name|r
operator|.
name|setFirstAlternative
argument_list|(
name|c
operator|.
name|getSubjectArea
argument_list|()
operator|+
literal|" "
operator|+
name|c
operator|.
name|getCourseNbr
argument_list|()
operator|+
operator|(
name|c
operator|.
name|hasUniqueName
argument_list|()
condition|?
literal|""
else|:
literal|" - "
operator|+
name|c
operator|.
name|getTitle
argument_list|()
operator|)
argument_list|)
expr_stmt|;
break|break;
case|case
literal|2
case|:
name|r
operator|.
name|setSecondAlternative
argument_list|(
name|c
operator|.
name|getSubjectArea
argument_list|()
operator|+
literal|" "
operator|+
name|c
operator|.
name|getCourseNbr
argument_list|()
operator|+
operator|(
name|c
operator|.
name|hasUniqueName
argument_list|()
condition|?
literal|""
else|:
literal|" - "
operator|+
name|c
operator|.
name|getTitle
argument_list|()
operator|)
argument_list|)
expr_stmt|;
block|}
name|order
operator|++
expr_stmt|;
block|}
if|if
condition|(
name|r
operator|.
name|hasRequestedCourse
argument_list|()
condition|)
block|{
if|if
condition|(
name|cd
operator|.
name|isAlternative
argument_list|()
condition|)
name|request
operator|.
name|getAlternatives
argument_list|()
operator|.
name|add
argument_list|(
name|r
argument_list|)
expr_stmt|;
else|else
name|request
operator|.
name|getCourses
argument_list|()
operator|.
name|add
argument_list|(
name|r
argument_list|)
expr_stmt|;
block|}
name|lastRequest
operator|=
name|r
expr_stmt|;
name|lastRequestPriority
operator|=
name|cd
operator|.
name|getPriority
argument_list|()
expr_stmt|;
block|}
block|}
return|return
name|request
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
literal|"get-request"
return|;
block|}
block|}
end_class

end_unit

