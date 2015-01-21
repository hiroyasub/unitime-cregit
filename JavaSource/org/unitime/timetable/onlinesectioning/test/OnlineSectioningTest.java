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
name|Collections
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
name|List
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
name|Constants
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
name|heuristics
operator|.
name|RouletteWheelSelection
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
name|basic
operator|.
name|GetRequest
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
name|ComputeSuggestionsAction
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

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|OnlineSectioningTest
extends|extends
name|OnlineSectioningTestFwk
block|{
specifier|private
specifier|static
name|String
index|[]
name|sDays
init|=
operator|new
name|String
index|[]
block|{
literal|"M"
block|,
literal|"T"
block|,
literal|"W"
block|,
literal|"R"
block|,
literal|"F"
block|,
literal|"S"
block|,
literal|"X"
block|}
decl_stmt|;
specifier|private
specifier|static
name|List
argument_list|<
name|ClassAssignmentInterface
operator|.
name|ClassAssignment
argument_list|>
name|toClassAssignments
parameter_list|(
name|ClassAssignmentInterface
name|assignment
parameter_list|)
block|{
if|if
condition|(
name|assignment
operator|==
literal|null
condition|)
return|return
literal|null
return|;
name|List
argument_list|<
name|ClassAssignmentInterface
operator|.
name|ClassAssignment
argument_list|>
name|ret
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
name|assignment
operator|.
name|getCourseAssignments
argument_list|()
control|)
name|ret
operator|.
name|addAll
argument_list|(
name|course
operator|.
name|getClassAssignments
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|ret
return|;
block|}
specifier|private
name|ClassAssignmentInterface
operator|.
name|CourseAssignment
name|course
parameter_list|(
name|CourseRequestInterface
operator|.
name|Request
name|request
parameter_list|,
name|ClassAssignmentInterface
name|assignment
parameter_list|)
block|{
if|if
condition|(
name|assignment
operator|==
literal|null
condition|)
return|return
literal|null
return|;
for|for
control|(
name|CourseAssignment
name|course
range|:
name|assignment
operator|.
name|getCourseAssignments
argument_list|()
control|)
block|{
if|if
condition|(
name|request
operator|.
name|hasRequestedFreeTime
argument_list|()
operator|&&
name|course
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
name|request
operator|.
name|getRequestedFreeTime
argument_list|()
control|)
block|{
for|for
control|(
name|ClassAssignmentInterface
operator|.
name|ClassAssignment
name|clazz
range|:
name|course
operator|.
name|getClassAssignments
argument_list|()
control|)
block|{
if|if
condition|(
name|ft
operator|.
name|getStart
argument_list|()
operator|==
name|clazz
operator|.
name|getStart
argument_list|()
operator|&&
name|ft
operator|.
name|getLength
argument_list|()
operator|==
name|clazz
operator|.
name|getLength
argument_list|()
operator|&&
name|ft
operator|.
name|getDaysString
argument_list|(
name|sDays
argument_list|,
literal|""
argument_list|)
operator|.
name|equals
argument_list|(
name|clazz
operator|.
name|getDaysString
argument_list|(
name|sDays
argument_list|,
literal|""
argument_list|)
argument_list|)
condition|)
block|{
return|return
name|course
return|;
block|}
block|}
block|}
block|}
if|if
condition|(
name|request
operator|.
name|hasRequestedCourse
argument_list|()
operator|&&
operator|!
name|course
operator|.
name|isFreeTime
argument_list|()
condition|)
block|{
if|if
condition|(
name|course
operator|.
name|getCourseName
argument_list|()
operator|.
name|equals
argument_list|(
name|request
operator|.
name|getRequestedCourse
argument_list|()
argument_list|)
condition|)
block|{
return|return
name|course
return|;
block|}
if|else if
condition|(
name|request
operator|.
name|hasFirstAlternative
argument_list|()
operator|&&
name|course
operator|.
name|getCourseName
argument_list|()
operator|.
name|equals
argument_list|(
name|request
operator|.
name|getFirstAlternative
argument_list|()
argument_list|)
condition|)
block|{
return|return
name|course
return|;
block|}
if|else if
condition|(
name|request
operator|.
name|hasSecondAlternative
argument_list|()
operator|&&
name|course
operator|.
name|getCourseName
argument_list|()
operator|.
name|equals
argument_list|(
name|request
operator|.
name|getSecondAlternative
argument_list|()
argument_list|)
condition|)
block|{
return|return
name|course
return|;
block|}
block|}
block|}
return|return
literal|null
return|;
block|}
specifier|private
name|double
name|penalty
parameter_list|(
name|StudentPreferencePenalties
name|penalty
parameter_list|,
name|CourseRequestInterface
name|request
parameter_list|,
name|ClassAssignmentInterface
name|assignment
parameter_list|)
block|{
name|double
name|ret
init|=
literal|0.0
decl_stmt|;
name|int
name|unassigned
init|=
literal|0
decl_stmt|;
for|for
control|(
name|CourseRequestInterface
operator|.
name|Request
name|r
range|:
name|request
operator|.
name|getCourses
argument_list|()
control|)
block|{
name|CourseAssignment
name|course
init|=
name|course
argument_list|(
name|r
argument_list|,
name|assignment
argument_list|)
decl_stmt|;
if|if
condition|(
name|course
operator|==
literal|null
operator|||
operator|!
name|course
operator|.
name|isAssigned
argument_list|()
condition|)
block|{
name|ret
operator|+=
literal|10.0
expr_stmt|;
if|if
condition|(
name|r
operator|.
name|hasRequestedCourse
argument_list|()
condition|)
name|unassigned
operator|++
expr_stmt|;
block|}
else|else
block|{
name|ret
operator|+=
name|penalty
operator|.
name|getPenalty
argument_list|(
name|course
argument_list|)
expr_stmt|;
block|}
block|}
for|for
control|(
name|CourseRequestInterface
operator|.
name|Request
name|r
range|:
name|request
operator|.
name|getAlternatives
argument_list|()
control|)
block|{
if|if
condition|(
name|unassigned
operator|<=
literal|0
condition|)
break|break;
name|CourseAssignment
name|course
init|=
name|course
argument_list|(
name|r
argument_list|,
name|assignment
argument_list|)
decl_stmt|;
if|if
condition|(
name|course
operator|!=
literal|null
operator|&&
name|course
operator|.
name|isAssigned
argument_list|()
condition|)
block|{
name|unassigned
operator|--
expr_stmt|;
name|ret
operator|+=
name|penalty
operator|.
name|getPenalty
argument_list|(
name|course
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|ret
return|;
block|}
specifier|private
name|boolean
name|isBetter
parameter_list|(
name|StudentPreferencePenalties
name|penalty
parameter_list|,
name|CourseRequestInterface
name|request
parameter_list|,
name|ClassAssignmentInterface
name|assignment
parameter_list|,
name|ClassAssignmentInterface
name|suggestion
parameter_list|)
block|{
return|return
name|penalty
argument_list|(
name|penalty
argument_list|,
name|request
argument_list|,
name|suggestion
argument_list|)
operator|<
name|penalty
argument_list|(
name|penalty
argument_list|,
name|request
argument_list|,
name|assignment
argument_list|)
return|;
block|}
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unused"
argument_list|)
specifier|private
name|String
name|toString
parameter_list|(
name|StudentPreferencePenalties
name|penalty
parameter_list|,
name|CourseRequestInterface
name|request
parameter_list|,
name|ClassAssignmentInterface
name|assignment
parameter_list|)
block|{
name|String
name|ret
init|=
name|sDF
operator|.
name|format
argument_list|(
name|penalty
argument_list|(
name|penalty
argument_list|,
name|request
argument_list|,
name|assignment
argument_list|)
argument_list|)
operator|+
literal|"/{"
decl_stmt|;
name|int
name|priority
init|=
literal|1
decl_stmt|;
name|int
name|unassigned
init|=
literal|0
decl_stmt|;
for|for
control|(
name|CourseRequestInterface
operator|.
name|Request
name|r
range|:
name|request
operator|.
name|getCourses
argument_list|()
control|)
block|{
name|CourseAssignment
name|course
init|=
name|course
argument_list|(
name|r
argument_list|,
name|assignment
argument_list|)
decl_stmt|;
if|if
condition|(
name|course
operator|==
literal|null
operator|||
operator|!
name|course
operator|.
name|isAssigned
argument_list|()
condition|)
block|{
name|ret
operator|+=
literal|"\n    "
operator|+
name|priority
operator|+
literal|". "
operator|+
name|r
operator|+
literal|" NOT ASSIGNED"
expr_stmt|;
if|if
condition|(
name|r
operator|.
name|hasRequestedCourse
argument_list|()
condition|)
name|unassigned
operator|++
expr_stmt|;
block|}
else|else
block|{
name|ret
operator|+=
literal|"\n   "
operator|+
name|priority
operator|+
literal|". "
operator|+
name|r
operator|+
literal|": "
operator|+
name|sDF
operator|.
name|format
argument_list|(
name|penalty
operator|.
name|getPenalty
argument_list|(
name|course
argument_list|)
argument_list|)
operator|+
literal|"/"
operator|+
name|course
operator|.
name|getCourseName
argument_list|()
expr_stmt|;
for|for
control|(
name|ClassAssignmentInterface
operator|.
name|ClassAssignment
name|clazz
range|:
name|course
operator|.
name|getClassAssignments
argument_list|()
control|)
name|ret
operator|+=
literal|"\n       "
operator|+
name|sDF
operator|.
name|format
argument_list|(
name|penalty
operator|.
name|getPenalty
argument_list|(
name|clazz
argument_list|)
argument_list|)
operator|+
literal|"/"
operator|+
name|clazz
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
name|priority
operator|++
expr_stmt|;
block|}
name|priority
operator|=
literal|1
expr_stmt|;
for|for
control|(
name|CourseRequestInterface
operator|.
name|Request
name|r
range|:
name|request
operator|.
name|getAlternatives
argument_list|()
control|)
block|{
if|if
condition|(
name|unassigned
operator|<=
literal|0
condition|)
break|break;
name|CourseAssignment
name|course
init|=
name|course
argument_list|(
name|r
argument_list|,
name|assignment
argument_list|)
decl_stmt|;
if|if
condition|(
name|course
operator|!=
literal|null
operator|&&
name|course
operator|.
name|isAssigned
argument_list|()
condition|)
block|{
name|unassigned
operator|--
expr_stmt|;
name|ret
operator|+=
literal|"\n  A"
operator|+
name|priority
operator|+
literal|". "
operator|+
name|r
operator|+
literal|": "
operator|+
name|sDF
operator|.
name|format
argument_list|(
name|penalty
operator|.
name|getPenalty
argument_list|(
name|course
argument_list|)
argument_list|)
operator|+
literal|"/"
operator|+
name|course
operator|.
name|getCourseName
argument_list|()
expr_stmt|;
for|for
control|(
name|ClassAssignmentInterface
operator|.
name|ClassAssignment
name|clazz
range|:
name|course
operator|.
name|getClassAssignments
argument_list|()
control|)
name|ret
operator|+=
literal|"\n       "
operator|+
name|sDF
operator|.
name|format
argument_list|(
name|penalty
operator|.
name|getPenalty
argument_list|(
name|clazz
argument_list|)
argument_list|)
operator|+
literal|"/"
operator|+
name|clazz
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
name|priority
operator|++
expr_stmt|;
block|}
return|return
name|ret
operator|+
literal|"\n}"
return|;
block|}
specifier|public
name|List
argument_list|<
name|Operation
argument_list|>
name|operations
parameter_list|()
block|{
name|getServer
argument_list|()
operator|.
name|getAcademicSession
argument_list|()
operator|.
name|setSectioningEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
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
specifier|final
name|boolean
name|suggestions
init|=
literal|"true"
operator|.
name|equals
argument_list|(
name|System
operator|.
name|getProperty
argument_list|(
literal|"suggestions"
argument_list|,
literal|"false"
argument_list|)
argument_list|)
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
name|execute
argument_list|(
name|createAction
argument_list|(
name|GetRequest
operator|.
name|class
argument_list|)
operator|.
name|forStudent
argument_list|(
name|studentId
argument_list|)
argument_list|,
name|user
argument_list|()
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
name|StudentPreferencePenalties
name|penalties
init|=
operator|new
name|StudentPreferencePenalties
argument_list|(
name|StudentPreferencePenalties
operator|.
name|DistType
operator|.
name|Preference
argument_list|)
decl_stmt|;
name|CourseRequestInterface
name|request
init|=
name|s
operator|.
name|execute
argument_list|(
name|createAction
argument_list|(
name|GetRequest
operator|.
name|class
argument_list|)
operator|.
name|forStudent
argument_list|(
name|studentId
argument_list|)
argument_list|,
name|user
argument_list|()
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
name|double
name|value
init|=
literal|0.0
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
name|s
operator|.
name|createAction
argument_list|(
name|FindAssignmentAction
operator|.
name|class
argument_list|)
operator|.
name|forRequest
argument_list|(
name|request
argument_list|)
operator|.
name|withAssignment
argument_list|(
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
name|ret
init|=
name|s
operator|.
name|execute
argument_list|(
name|action
argument_list|,
name|user
argument_list|()
argument_list|)
decl_stmt|;
name|ClassAssignmentInterface
name|assignment
init|=
operator|(
name|ret
operator|==
literal|null
operator|||
name|ret
operator|.
name|isEmpty
argument_list|()
condition|?
literal|null
else|:
name|ret
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|)
decl_stmt|;
name|List
argument_list|<
name|ClassAssignmentInterface
operator|.
name|ClassAssignment
argument_list|>
name|classes
init|=
name|toClassAssignments
argument_list|(
name|assignment
argument_list|)
decl_stmt|;
if|if
condition|(
name|assignment
operator|!=
literal|null
condition|)
name|value
operator|=
name|assignment
operator|.
name|getValue
argument_list|()
expr_stmt|;
if|if
condition|(
name|suggestions
condition|)
block|{
for|for
control|(
name|int
name|x
init|=
literal|0
init|;
name|x
operator|<
operator|(
name|classes
operator|==
literal|null
condition|?
literal|0
else|:
name|classes
operator|.
name|size
argument_list|()
operator|)
condition|;
name|x
operator|++
control|)
block|{
name|List
argument_list|<
name|ClassAssignmentInterface
argument_list|>
name|suggestions
init|=
name|s
operator|.
name|execute
argument_list|(
name|s
operator|.
name|createAction
argument_list|(
name|ComputeSuggestionsAction
operator|.
name|class
argument_list|)
operator|.
name|forRequest
argument_list|(
name|request
argument_list|)
operator|.
name|withAssignment
argument_list|(
name|classes
argument_list|)
operator|.
name|withSelection
argument_list|(
name|classes
operator|.
name|get
argument_list|(
name|x
argument_list|)
argument_list|)
argument_list|,
name|user
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|suggestions
operator|!=
literal|null
operator|&&
operator|!
name|suggestions
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
for|for
control|(
name|ClassAssignmentInterface
name|suggestion
range|:
name|suggestions
control|)
block|{
if|if
condition|(
name|isBetter
argument_list|(
name|penalties
argument_list|,
name|request
argument_list|,
name|assignment
argument_list|,
name|suggestion
argument_list|)
condition|)
block|{
name|assignment
operator|=
name|suggestion
expr_stmt|;
name|classes
operator|=
name|toClassAssignments
argument_list|(
name|assignment
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
block|}
if|if
condition|(
name|classes
operator|!=
literal|null
condition|)
name|s
operator|.
name|execute
argument_list|(
name|s
operator|.
name|createAction
argument_list|(
name|EnrollStudent
operator|.
name|class
argument_list|)
operator|.
name|forStudent
argument_list|(
name|studentId
argument_list|)
operator|.
name|withRequest
argument_list|(
name|request
argument_list|)
operator|.
name|withAssignment
argument_list|(
name|classes
argument_list|)
argument_list|,
name|user
argument_list|()
argument_list|)
expr_stmt|;
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
name|getMessage
argument_list|()
operator|.
name|contains
argument_list|(
literal|"the class is no longer available"
argument_list|)
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
name|value
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
name|Collections
operator|.
name|shuffle
argument_list|(
name|operations
argument_list|)
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
name|Integer
operator|.
name|valueOf
argument_list|(
name|System
operator|.
name|getProperty
argument_list|(
literal|"nrTasks"
argument_list|,
literal|"-1"
argument_list|)
argument_list|)
argument_list|,
name|Integer
operator|.
name|valueOf
argument_list|(
name|System
operator|.
name|getProperty
argument_list|(
literal|"nrConcurrent"
argument_list|,
literal|"10"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
class|class
name|StudentPreferencePenalties
block|{
specifier|public
specifier|static
enum|enum
name|DistType
block|{
name|Uniform
block|,
name|Preference
block|,
name|PreferenceQuadratic
block|,
name|PreferenceReverse
block|}
empty_stmt|;
specifier|public
specifier|static
name|int
index|[]
index|[]
name|sStudentRequestDistribution
init|=
operator|new
name|int
index|[]
index|[]
block|{
comment|// morning, 7:30a, 8:30a, 9:30a, 10:30a, 11:30a, 12:30p, 1:30p, 2:30p, 3:30p, 4:30p, evening
block|{
literal|1
block|,
literal|1
block|,
literal|4
block|,
literal|7
block|,
literal|10
block|,
literal|10
block|,
literal|5
block|,
literal|8
block|,
literal|8
block|,
literal|6
block|,
literal|3
block|,
literal|1
block|}
block|,
comment|// Monday
block|{
literal|1
block|,
literal|2
block|,
literal|4
block|,
literal|7
block|,
literal|10
block|,
literal|10
block|,
literal|5
block|,
literal|8
block|,
literal|8
block|,
literal|6
block|,
literal|3
block|,
literal|1
block|}
block|,
comment|// Tuesday
block|{
literal|1
block|,
literal|2
block|,
literal|4
block|,
literal|7
block|,
literal|10
block|,
literal|10
block|,
literal|5
block|,
literal|8
block|,
literal|8
block|,
literal|6
block|,
literal|3
block|,
literal|1
block|}
block|,
comment|// Wednesday
block|{
literal|1
block|,
literal|2
block|,
literal|4
block|,
literal|7
block|,
literal|10
block|,
literal|10
block|,
literal|5
block|,
literal|8
block|,
literal|8
block|,
literal|6
block|,
literal|3
block|,
literal|1
block|}
block|,
comment|// Thursday
block|{
literal|1
block|,
literal|2
block|,
literal|4
block|,
literal|7
block|,
literal|10
block|,
literal|10
block|,
literal|5
block|,
literal|4
block|,
literal|3
block|,
literal|2
block|,
literal|1
block|,
literal|1
block|}
block|,
comment|// Friday
block|{
literal|1
block|,
literal|1
block|,
literal|1
block|,
literal|1
block|,
literal|1
block|,
literal|1
block|,
literal|1
block|,
literal|1
block|,
literal|1
block|,
literal|1
block|,
literal|1
block|,
literal|1
block|}
block|,
comment|// Saturday
block|{
literal|1
block|,
literal|1
block|,
literal|1
block|,
literal|1
block|,
literal|1
block|,
literal|1
block|,
literal|1
block|,
literal|1
block|,
literal|1
block|,
literal|1
block|,
literal|1
block|,
literal|1
block|}
comment|// Sunday
block|}
decl_stmt|;
specifier|private
name|HashMap
argument_list|<
name|String
argument_list|,
name|Double
argument_list|>
name|iWeight
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|Double
argument_list|>
argument_list|()
decl_stmt|;
specifier|public
name|StudentPreferencePenalties
parameter_list|(
name|DistType
name|disributionType
parameter_list|)
block|{
name|RouletteWheelSelection
argument_list|<
name|int
index|[]
argument_list|>
name|roulette
init|=
operator|new
name|RouletteWheelSelection
argument_list|<
name|int
index|[]
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|d
init|=
literal|0
init|;
name|d
operator|<
name|sStudentRequestDistribution
operator|.
name|length
condition|;
name|d
operator|++
control|)
for|for
control|(
name|int
name|t
init|=
literal|0
init|;
name|t
operator|<
name|sStudentRequestDistribution
index|[
name|d
index|]
operator|.
name|length
condition|;
name|t
operator|++
control|)
block|{
switch|switch
condition|(
name|disributionType
condition|)
block|{
case|case
name|Uniform
case|:
name|roulette
operator|.
name|add
argument_list|(
operator|new
name|int
index|[]
block|{
name|d
block|,
name|t
block|}
argument_list|,
literal|1
argument_list|)
expr_stmt|;
break|break;
case|case
name|Preference
case|:
name|roulette
operator|.
name|add
argument_list|(
operator|new
name|int
index|[]
block|{
name|d
block|,
name|t
block|}
argument_list|,
name|sStudentRequestDistribution
index|[
name|d
index|]
index|[
name|t
index|]
argument_list|)
expr_stmt|;
break|break;
case|case
name|PreferenceQuadratic
case|:
name|roulette
operator|.
name|add
argument_list|(
operator|new
name|int
index|[]
block|{
name|d
block|,
name|t
block|}
argument_list|,
name|sStudentRequestDistribution
index|[
name|d
index|]
index|[
name|t
index|]
operator|*
name|sStudentRequestDistribution
index|[
name|d
index|]
index|[
name|t
index|]
argument_list|)
expr_stmt|;
break|break;
case|case
name|PreferenceReverse
case|:
name|roulette
operator|.
name|add
argument_list|(
operator|new
name|int
index|[]
block|{
name|d
block|,
name|t
block|}
argument_list|,
literal|11
operator|-
name|sStudentRequestDistribution
index|[
name|d
index|]
index|[
name|t
index|]
argument_list|)
expr_stmt|;
break|break;
default|default:
name|roulette
operator|.
name|add
argument_list|(
operator|new
name|int
index|[]
block|{
name|d
block|,
name|t
block|}
argument_list|,
literal|1
argument_list|)
expr_stmt|;
break|break;
block|}
block|}
name|int
name|idx
init|=
literal|0
decl_stmt|;
while|while
condition|(
name|roulette
operator|.
name|hasMoreElements
argument_list|()
condition|)
block|{
name|int
index|[]
name|dt
init|=
name|roulette
operator|.
name|nextElement
argument_list|()
decl_stmt|;
name|iWeight
operator|.
name|put
argument_list|(
name|dt
index|[
literal|0
index|]
operator|+
literal|"."
operator|+
name|dt
index|[
literal|1
index|]
argument_list|,
operator|new
name|Double
argument_list|(
operator|(
operator|(
name|double
operator|)
name|idx
operator|)
operator|/
operator|(
name|roulette
operator|.
name|size
argument_list|()
operator|-
literal|1
operator|)
argument_list|)
argument_list|)
expr_stmt|;
name|idx
operator|++
expr_stmt|;
block|}
block|}
specifier|public
name|String
name|toString
parameter_list|(
name|int
name|day
parameter_list|,
name|int
name|time
parameter_list|)
block|{
if|if
condition|(
name|time
operator|==
literal|0
condition|)
return|return
name|Constants
operator|.
name|DAY_NAMES_SHORT
index|[
name|day
index|]
operator|+
literal|" morning"
return|;
if|if
condition|(
name|time
operator|==
literal|11
condition|)
return|return
name|Constants
operator|.
name|DAY_NAMES_SHORT
index|[
name|day
index|]
operator|+
literal|" evening"
return|;
return|return
name|Constants
operator|.
name|DAY_NAMES_SHORT
index|[
name|day
index|]
operator|+
literal|" "
operator|+
operator|(
literal|6
operator|+
name|time
operator|)
operator|+
literal|":30"
return|;
block|}
specifier|public
specifier|static
name|int
name|time
parameter_list|(
name|int
name|slot
parameter_list|)
block|{
name|int
name|s
init|=
name|slot
operator|%
name|Constants
operator|.
name|SLOTS_PER_DAY
decl_stmt|;
name|int
name|min
init|=
operator|(
name|s
operator|*
name|Constants
operator|.
name|SLOT_LENGTH_MIN
operator|+
name|Constants
operator|.
name|FIRST_SLOT_TIME_MIN
operator|)
decl_stmt|;
if|if
condition|(
name|min
operator|<
literal|450
condition|)
return|return
literal|0
return|;
comment|// morning
name|int
name|idx
init|=
literal|1
operator|+
operator|(
name|min
operator|-
literal|450
operator|)
operator|/
literal|60
decl_stmt|;
return|return
operator|(
name|idx
operator|>
literal|11
condition|?
literal|11
else|:
name|idx
operator|)
return|;
comment|// 11+ is evening
block|}
specifier|public
name|double
name|getPenalty
parameter_list|(
name|ClassAssignmentInterface
operator|.
name|ClassAssignment
name|assignment
parameter_list|)
block|{
if|if
condition|(
operator|!
name|assignment
operator|.
name|isAssigned
argument_list|()
condition|)
return|return
literal|1.0
return|;
name|int
name|nrSlots
init|=
literal|0
decl_stmt|;
name|double
name|penalty
init|=
literal|0.0
decl_stmt|;
for|for
control|(
name|int
name|day
range|:
name|assignment
operator|.
name|getDays
argument_list|()
control|)
block|{
for|for
control|(
name|int
name|idx
init|=
literal|0
init|;
name|idx
operator|<
name|assignment
operator|.
name|getLength
argument_list|()
condition|;
name|idx
operator|++
control|)
block|{
name|int
name|slot
init|=
name|assignment
operator|.
name|getStart
argument_list|()
operator|+
name|idx
decl_stmt|;
name|nrSlots
operator|++
expr_stmt|;
name|penalty
operator|+=
operator|(
name|iWeight
operator|.
name|get
argument_list|(
name|day
operator|+
literal|"."
operator|+
name|time
argument_list|(
name|slot
argument_list|)
argument_list|)
operator|)
operator|.
name|doubleValue
argument_list|()
expr_stmt|;
block|}
block|}
return|return
name|penalty
operator|/
name|nrSlots
return|;
block|}
specifier|public
name|double
name|getPenalty
parameter_list|(
name|ClassAssignmentInterface
operator|.
name|CourseAssignment
name|assignment
parameter_list|)
block|{
if|if
condition|(
name|assignment
operator|.
name|getClassAssignments
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
literal|1.0
return|;
name|double
name|penalty
init|=
literal|0.0
decl_stmt|;
for|for
control|(
name|ClassAssignmentInterface
operator|.
name|ClassAssignment
name|clazz
range|:
name|assignment
operator|.
name|getClassAssignments
argument_list|()
control|)
name|penalty
operator|+=
name|getPenalty
argument_list|(
name|clazz
argument_list|)
expr_stmt|;
return|return
name|penalty
operator|/
name|assignment
operator|.
name|getClassAssignments
argument_list|()
operator|.
name|size
argument_list|()
return|;
block|}
block|}
block|}
end_class

end_unit

