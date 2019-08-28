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
name|dao
operator|.
name|StudentDAO
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
name|OnlineSectioningLog
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
name|custom
operator|.
name|CriticalCoursesProvider
operator|.
name|CriticalCourses
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
name|DatabaseServer
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
name|ReloadStudent
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|CustomCriticalCoursesHolder
block|{
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
specifier|public
specifier|static
name|CriticalCoursesProvider
name|getProvider
parameter_list|()
block|{
return|return
name|Customization
operator|.
name|CriticalCoursesProvider
operator|.
name|getProvider
argument_list|()
return|;
block|}
specifier|public
specifier|static
name|void
name|release
parameter_list|()
block|{
name|Customization
operator|.
name|CriticalCoursesProvider
operator|.
name|release
argument_list|()
expr_stmt|;
block|}
specifier|public
specifier|static
name|boolean
name|hasProvider
parameter_list|()
block|{
return|return
name|Customization
operator|.
name|CriticalCoursesProvider
operator|.
name|hasProvider
argument_list|()
return|;
block|}
specifier|public
specifier|static
class|class
name|CheckCriticalCourses
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
specifier|private
name|Collection
argument_list|<
name|Long
argument_list|>
name|iStudentIds
init|=
literal|null
decl_stmt|;
specifier|public
name|CheckCriticalCourses
name|forStudents
parameter_list|(
name|Long
modifier|...
name|studentIds
parameter_list|)
block|{
name|iStudentIds
operator|=
operator|new
name|ArrayList
argument_list|<
name|Long
argument_list|>
argument_list|()
expr_stmt|;
for|for
control|(
name|Long
name|studentId
range|:
name|studentIds
control|)
name|iStudentIds
operator|.
name|add
argument_list|(
name|studentId
argument_list|)
expr_stmt|;
return|return
name|this
return|;
block|}
specifier|public
name|CheckCriticalCourses
name|forStudents
parameter_list|(
name|Collection
argument_list|<
name|Long
argument_list|>
name|studentIds
parameter_list|)
block|{
name|iStudentIds
operator|=
name|studentIds
expr_stmt|;
return|return
name|this
return|;
block|}
specifier|public
name|Collection
argument_list|<
name|Long
argument_list|>
name|getStudentIds
parameter_list|()
block|{
return|return
name|iStudentIds
return|;
block|}
annotation|@
name|Override
specifier|public
name|Boolean
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
operator|!
name|CustomCriticalCoursesHolder
operator|.
name|hasProvider
argument_list|()
condition|)
return|return
literal|false
return|;
specifier|final
name|List
argument_list|<
name|Long
argument_list|>
name|reloadIds
init|=
operator|new
name|ArrayList
argument_list|<
name|Long
argument_list|>
argument_list|()
decl_stmt|;
try|try
block|{
name|int
name|nrThreads
init|=
name|server
operator|.
name|getConfig
argument_list|()
operator|.
name|getPropertyInt
argument_list|(
literal|"CheckCriticalCourses.NrThreads"
argument_list|,
literal|10
argument_list|)
decl_stmt|;
if|if
condition|(
name|nrThreads
operator|<=
literal|1
operator|||
name|getStudentIds
argument_list|()
operator|.
name|size
argument_list|()
operator|<=
literal|1
condition|)
block|{
for|for
control|(
name|Long
name|studentId
range|:
name|getStudentIds
argument_list|()
control|)
block|{
if|if
condition|(
name|recheckStudent
argument_list|(
name|server
argument_list|,
name|helper
argument_list|,
name|studentId
argument_list|)
condition|)
name|reloadIds
operator|.
name|add
argument_list|(
name|studentId
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|List
argument_list|<
name|Worker
argument_list|>
name|workers
init|=
operator|new
name|ArrayList
argument_list|<
name|Worker
argument_list|>
argument_list|()
decl_stmt|;
name|Iterator
argument_list|<
name|Long
argument_list|>
name|studentIds
init|=
name|getStudentIds
argument_list|()
operator|.
name|iterator
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
name|nrThreads
condition|;
name|i
operator|++
control|)
name|workers
operator|.
name|add
argument_list|(
operator|new
name|Worker
argument_list|(
name|i
argument_list|,
name|studentIds
argument_list|)
block|{
annotation|@
name|Override
specifier|protected
name|void
name|process
parameter_list|(
name|Long
name|studentId
parameter_list|)
block|{
if|if
condition|(
name|recheckStudent
argument_list|(
name|server
argument_list|,
operator|new
name|OnlineSectioningHelper
argument_list|(
name|helper
argument_list|)
argument_list|,
name|studentId
argument_list|)
condition|)
block|{
synchronized|synchronized
init|(
name|reloadIds
init|)
block|{
name|reloadIds
operator|.
name|add
argument_list|(
name|studentId
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
argument_list|)
expr_stmt|;
for|for
control|(
name|Worker
name|worker
range|:
name|workers
control|)
name|worker
operator|.
name|start
argument_list|()
expr_stmt|;
for|for
control|(
name|Worker
name|worker
range|:
name|workers
control|)
block|{
try|try
block|{
name|worker
operator|.
name|join
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|InterruptedException
name|e
parameter_list|)
block|{
block|}
block|}
block|}
block|}
finally|finally
block|{
if|if
condition|(
operator|!
name|reloadIds
operator|.
name|isEmpty
argument_list|()
operator|&&
operator|!
operator|(
name|server
operator|instanceof
name|DatabaseServer
operator|)
condition|)
name|server
operator|.
name|execute
argument_list|(
name|server
operator|.
name|createAction
argument_list|(
name|ReloadStudent
operator|.
name|class
argument_list|)
operator|.
name|forStudents
argument_list|(
name|reloadIds
argument_list|)
argument_list|,
name|helper
operator|.
name|getUser
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
operator|!
name|reloadIds
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|protected
name|boolean
name|isCritical
parameter_list|(
name|CourseDemand
name|cd
parameter_list|,
name|CriticalCourses
name|critical
parameter_list|)
block|{
if|if
condition|(
name|critical
operator|==
literal|null
operator|||
name|cd
operator|.
name|isAlternative
argument_list|()
condition|)
return|return
literal|false
return|;
for|for
control|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|CourseRequest
name|cr
range|:
name|cd
operator|.
name|getCourseRequests
argument_list|()
control|)
block|{
if|if
condition|(
name|cr
operator|.
name|getOrder
argument_list|()
operator|==
literal|0
operator|&&
name|critical
operator|.
name|isCritical
argument_list|(
name|cr
operator|.
name|getCourseOffering
argument_list|()
argument_list|)
condition|)
return|return
literal|true
return|;
block|}
return|return
literal|false
return|;
block|}
specifier|protected
name|boolean
name|recheckStudent
parameter_list|(
name|OnlineSectioningServer
name|server
parameter_list|,
name|OnlineSectioningHelper
name|helper
parameter_list|,
name|Long
name|studentId
parameter_list|)
block|{
name|helper
operator|.
name|beginTransaction
argument_list|()
expr_stmt|;
try|try
block|{
name|Student
name|student
init|=
name|StudentDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|studentId
argument_list|,
name|helper
operator|.
name|getHibSession
argument_list|()
argument_list|)
decl_stmt|;
name|boolean
name|changed
init|=
literal|false
decl_stmt|;
if|if
condition|(
name|student
operator|!=
literal|null
condition|)
block|{
name|OnlineSectioningLog
operator|.
name|Action
operator|.
name|Builder
name|action
init|=
name|helper
operator|.
name|addAction
argument_list|(
name|this
argument_list|,
name|server
operator|.
name|getAcademicSession
argument_list|()
argument_list|)
decl_stmt|;
name|action
operator|.
name|setStudent
argument_list|(
name|OnlineSectioningLog
operator|.
name|Entity
operator|.
name|newBuilder
argument_list|()
operator|.
name|setUniqueId
argument_list|(
name|studentId
argument_list|)
operator|.
name|setExternalId
argument_list|(
name|student
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|)
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
operator|.
name|setType
argument_list|(
name|OnlineSectioningLog
operator|.
name|Entity
operator|.
name|EntityType
operator|.
name|STUDENT
argument_list|)
argument_list|)
expr_stmt|;
name|long
name|c0
init|=
name|OnlineSectioningHelper
operator|.
name|getCpuTime
argument_list|()
decl_stmt|;
try|try
block|{
name|CriticalCourses
name|critical
init|=
name|CustomCriticalCoursesHolder
operator|.
name|getProvider
argument_list|()
operator|.
name|getCriticalCourses
argument_list|(
name|server
argument_list|,
name|helper
argument_list|,
operator|new
name|XStudentId
argument_list|(
name|student
argument_list|,
name|helper
argument_list|)
argument_list|,
name|action
argument_list|)
decl_stmt|;
for|for
control|(
name|CourseDemand
name|cd
range|:
name|student
operator|.
name|getCourseDemands
argument_list|()
control|)
block|{
name|boolean
name|crit
init|=
name|isCritical
argument_list|(
name|cd
argument_list|,
name|critical
argument_list|)
decl_stmt|;
if|if
condition|(
name|cd
operator|.
name|isCritical
argument_list|()
operator|==
literal|null
operator|||
name|cd
operator|.
name|isCritical
argument_list|()
operator|.
name|booleanValue
argument_list|()
operator|!=
name|crit
condition|)
block|{
name|cd
operator|.
name|setCritical
argument_list|(
name|crit
argument_list|)
expr_stmt|;
name|helper
operator|.
name|getHibSession
argument_list|()
operator|.
name|update
argument_list|(
name|cd
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
name|changed
condition|)
block|{
name|action
operator|.
name|setResult
argument_list|(
name|OnlineSectioningLog
operator|.
name|Action
operator|.
name|ResultType
operator|.
name|TRUE
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|action
operator|.
name|setResult
argument_list|(
name|OnlineSectioningLog
operator|.
name|Action
operator|.
name|ResultType
operator|.
name|FALSE
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|SectioningException
name|e
parameter_list|)
block|{
name|action
operator|.
name|setResult
argument_list|(
name|OnlineSectioningLog
operator|.
name|Action
operator|.
name|ResultType
operator|.
name|FAILURE
argument_list|)
expr_stmt|;
if|if
condition|(
name|e
operator|.
name|getCause
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|action
operator|.
name|addMessage
argument_list|(
name|OnlineSectioningLog
operator|.
name|Message
operator|.
name|newBuilder
argument_list|()
operator|.
name|setLevel
argument_list|(
name|OnlineSectioningLog
operator|.
name|Message
operator|.
name|Level
operator|.
name|FATAL
argument_list|)
operator|.
name|setText
argument_list|(
name|e
operator|.
name|getCause
argument_list|()
operator|.
name|getClass
argument_list|()
operator|.
name|getName
argument_list|()
operator|+
literal|": "
operator|+
name|e
operator|.
name|getCause
argument_list|()
operator|.
name|getMessage
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|action
operator|.
name|addMessage
argument_list|(
name|OnlineSectioningLog
operator|.
name|Message
operator|.
name|newBuilder
argument_list|()
operator|.
name|setLevel
argument_list|(
name|OnlineSectioningLog
operator|.
name|Message
operator|.
name|Level
operator|.
name|FATAL
argument_list|)
operator|.
name|setText
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
operator|==
literal|null
condition|?
literal|"null"
else|:
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
finally|finally
block|{
name|action
operator|.
name|setCpuTime
argument_list|(
name|OnlineSectioningHelper
operator|.
name|getCpuTime
argument_list|()
operator|-
name|c0
argument_list|)
expr_stmt|;
name|action
operator|.
name|setEndTime
argument_list|(
name|System
operator|.
name|currentTimeMillis
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
name|helper
operator|.
name|commitTransaction
argument_list|()
expr_stmt|;
return|return
name|changed
return|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|helper
operator|.
name|rollbackTransaction
argument_list|()
expr_stmt|;
if|if
condition|(
name|e
operator|instanceof
name|SectioningException
condition|)
throw|throw
operator|(
name|SectioningException
operator|)
name|e
throw|;
throw|throw
operator|new
name|SectioningException
argument_list|(
name|MSG
operator|.
name|exceptionUnknown
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
argument_list|,
name|e
argument_list|)
throw|;
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
literal|"critical-courses"
return|;
block|}
block|}
specifier|protected
specifier|static
specifier|abstract
class|class
name|Worker
extends|extends
name|Thread
block|{
specifier|private
name|Iterator
argument_list|<
name|Long
argument_list|>
name|iStudentsIds
decl_stmt|;
specifier|public
name|Worker
parameter_list|(
name|int
name|index
parameter_list|,
name|Iterator
argument_list|<
name|Long
argument_list|>
name|studentsIds
parameter_list|)
block|{
name|setName
argument_list|(
literal|"CriticalCourses-"
operator|+
operator|(
literal|1
operator|+
name|index
operator|)
argument_list|)
expr_stmt|;
name|iStudentsIds
operator|=
name|studentsIds
expr_stmt|;
block|}
specifier|protected
specifier|abstract
name|void
name|process
parameter_list|(
name|Long
name|studentId
parameter_list|)
function_decl|;
annotation|@
name|Override
specifier|public
name|void
name|run
parameter_list|()
block|{
try|try
block|{
while|while
condition|(
literal|true
condition|)
block|{
name|Long
name|studentId
init|=
literal|null
decl_stmt|;
synchronized|synchronized
init|(
name|iStudentsIds
init|)
block|{
if|if
condition|(
operator|!
name|iStudentsIds
operator|.
name|hasNext
argument_list|()
condition|)
break|break;
name|studentId
operator|=
name|iStudentsIds
operator|.
name|next
argument_list|()
expr_stmt|;
block|}
name|process
argument_list|(
name|studentId
argument_list|)
expr_stmt|;
block|}
block|}
finally|finally
block|{
name|_RootDAO
operator|.
name|closeCurrentThreadSessions
argument_list|()
expr_stmt|;
block|}
block|}
block|}
block|}
end_class

end_unit

