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
operator|.
name|test
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
name|SectioningExceptionType
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
name|dao
operator|.
name|_RootDAO
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
name|OnlineSectioningTestFwk
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
name|FindAssignmentAction
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
name|OnlineSectioningTest
extends|extends
name|OnlineSectioningTestFwk
block|{
specifier|public
name|List
argument_list|<
name|Operation
argument_list|>
name|operations
parameter_list|()
block|{
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
operator|new
name|_RootDAO
argument_list|()
operator|.
name|getSession
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|Operation
argument_list|>
name|operations
init|=
operator|new
name|ArrayList
argument_list|<
name|Operation
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
specifier|final
name|Long
name|studentId
range|:
operator|(
name|List
argument_list|<
name|Long
argument_list|>
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"select s.uniqueId from Student s where s.session.uniqueId = :sessionId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|getServer
argument_list|()
operator|.
name|getAcademicSession
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|list
argument_list|()
control|)
block|{
name|CourseRequestInterface
name|request
init|=
name|getServer
argument_list|()
operator|.
name|getRequest
argument_list|(
name|studentId
argument_list|)
decl_stmt|;
if|if
condition|(
name|request
operator|==
literal|null
operator|||
name|request
operator|.
name|getCourses
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
continue|continue;
name|operations
operator|.
name|add
argument_list|(
operator|new
name|Operation
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|double
name|execute
parameter_list|(
name|OnlineSectioningServer
name|s
parameter_list|)
block|{
name|CourseRequestInterface
name|request
init|=
name|s
operator|.
name|getRequest
argument_list|(
name|studentId
argument_list|)
decl_stmt|;
if|if
condition|(
name|request
operator|!=
literal|null
operator|&&
operator|!
name|request
operator|.
name|getCourses
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|FindAssignmentAction
name|action
init|=
literal|null
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|1
init|;
name|i
operator|<=
literal|5
condition|;
name|i
operator|++
control|)
block|{
try|try
block|{
name|action
operator|=
operator|new
name|FindAssignmentAction
argument_list|(
name|request
argument_list|,
operator|new
name|ArrayList
argument_list|<
name|ClassAssignmentInterface
operator|.
name|ClassAssignment
argument_list|>
argument_list|()
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|ClassAssignmentInterface
argument_list|>
name|assignments
init|=
name|s
operator|.
name|execute
argument_list|(
name|action
argument_list|)
decl_stmt|;
if|if
condition|(
name|assignments
operator|!=
literal|null
operator|&&
operator|!
name|assignments
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|List
argument_list|<
name|ClassAssignmentInterface
operator|.
name|ClassAssignment
argument_list|>
name|assignment
init|=
operator|new
name|ArrayList
argument_list|<
name|ClassAssignmentInterface
operator|.
name|ClassAssignment
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|ClassAssignmentInterface
operator|.
name|CourseAssignment
name|course
range|:
name|assignments
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|getCourseAssignments
argument_list|()
control|)
name|assignment
operator|.
name|addAll
argument_list|(
name|course
operator|.
name|getClassAssignments
argument_list|()
argument_list|)
expr_stmt|;
name|s
operator|.
name|execute
argument_list|(
operator|new
name|EnrollStudent
argument_list|(
name|studentId
argument_list|,
name|request
argument_list|,
name|assignment
argument_list|)
argument_list|)
expr_stmt|;
block|}
break|break;
block|}
catch|catch
parameter_list|(
name|SectioningException
name|e
parameter_list|)
block|{
if|if
condition|(
name|e
operator|.
name|getType
argument_list|()
operator|==
name|SectioningExceptionType
operator|.
name|ENROLL_NOT_AVAILABLE
condition|)
block|{
name|sLog
operator|.
name|warn
argument_list|(
literal|"Enrollment failed: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
operator|+
literal|" become unavailable ("
operator|+
name|i
operator|+
literal|". attempt)"
argument_list|)
expr_stmt|;
continue|continue;
block|}
block|}
block|}
return|return
name|action
operator|.
name|value
argument_list|()
return|;
block|}
else|else
block|{
return|return
literal|1.0
return|;
block|}
block|}
block|}
argument_list|)
expr_stmt|;
block|}
name|hibSession
operator|.
name|close
argument_list|()
expr_stmt|;
return|return
name|operations
return|;
block|}
specifier|public
specifier|static
name|void
name|main
parameter_list|(
name|String
name|args
index|[]
parameter_list|)
block|{
operator|new
name|OnlineSectioningTest
argument_list|()
operator|.
name|test
argument_list|(
operator|-
literal|1
argument_list|,
literal|100
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

