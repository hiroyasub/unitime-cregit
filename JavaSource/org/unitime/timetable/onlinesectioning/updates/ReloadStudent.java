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
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|ApplicationProperties
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
operator|.
name|Lock
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
name|Assignment
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

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|ReloadStudent
extends|extends
name|ReloadAllData
block|{
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
name|ReloadStudent
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
block|}
specifier|public
name|ReloadStudent
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
name|OnlineSectioningServer
name|server
parameter_list|,
name|OnlineSectioningHelper
name|helper
parameter_list|)
block|{
if|if
condition|(
operator|!
literal|"true"
operator|.
name|equals
argument_list|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"unitime.enrollment.load"
argument_list|,
literal|"true"
argument_list|)
argument_list|)
condition|)
return|return
literal|false
return|;
name|helper
operator|.
name|info
argument_list|(
name|getStudentIds
argument_list|()
operator|.
name|size
argument_list|()
operator|+
literal|" students changed."
argument_list|)
expr_stmt|;
name|helper
operator|.
name|beginTransaction
argument_list|()
expr_stmt|;
try|try
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
name|helper
operator|.
name|getAction
argument_list|()
operator|.
name|addOther
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
name|Lock
name|lock
init|=
name|server
operator|.
name|lockStudent
argument_list|(
name|studentId
argument_list|,
operator|(
name|List
argument_list|<
name|Long
argument_list|>
operator|)
name|helper
operator|.
name|getHibSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select distinct e.courseOffering.instructionalOffering.uniqueId from StudentClassEnrollment e where "
operator|+
literal|"e.student.uniqueId = :studentId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"studentId"
argument_list|,
name|studentId
argument_list|)
operator|.
name|list
argument_list|()
argument_list|,
literal|false
argument_list|)
decl_stmt|;
try|try
block|{
comment|// Unload student
name|Student
name|oldStudent
init|=
name|server
operator|.
name|getStudent
argument_list|(
name|studentId
argument_list|)
decl_stmt|;
if|if
condition|(
name|oldStudent
operator|!=
literal|null
condition|)
block|{
name|OnlineSectioningLog
operator|.
name|Enrollment
operator|.
name|Builder
name|enrollment
init|=
name|OnlineSectioningLog
operator|.
name|Enrollment
operator|.
name|newBuilder
argument_list|()
decl_stmt|;
name|enrollment
operator|.
name|setType
argument_list|(
name|OnlineSectioningLog
operator|.
name|Enrollment
operator|.
name|EnrollmentType
operator|.
name|PREVIOUS
argument_list|)
expr_stmt|;
for|for
control|(
name|Request
name|oldRequest
range|:
name|oldStudent
operator|.
name|getRequests
argument_list|()
control|)
block|{
if|if
condition|(
name|oldRequest
operator|.
name|getInitialAssignment
argument_list|()
operator|!=
literal|null
condition|)
for|for
control|(
name|Assignment
name|assignment
range|:
name|oldRequest
operator|.
name|getInitialAssignment
argument_list|()
operator|.
name|getAssignments
argument_list|()
control|)
name|enrollment
operator|.
name|addSection
argument_list|(
name|OnlineSectioningHelper
operator|.
name|toProto
argument_list|(
name|assignment
argument_list|,
name|oldRequest
operator|.
name|getInitialAssignment
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|action
operator|.
name|addEnrollment
argument_list|(
name|enrollment
argument_list|)
expr_stmt|;
name|server
operator|.
name|remove
argument_list|(
name|oldStudent
argument_list|)
expr_stmt|;
name|action
operator|.
name|getStudentBuilder
argument_list|()
operator|.
name|setUniqueId
argument_list|(
name|oldStudent
operator|.
name|getId
argument_list|()
argument_list|)
operator|.
name|setExternalId
argument_list|(
name|oldStudent
operator|.
name|getExternalId
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|// Load student
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
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
name|Student
name|newStudent
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|student
operator|!=
literal|null
condition|)
block|{
name|newStudent
operator|=
name|loadStudent
argument_list|(
name|student
argument_list|,
name|server
argument_list|,
name|helper
argument_list|)
expr_stmt|;
if|if
condition|(
name|newStudent
operator|!=
literal|null
condition|)
block|{
name|server
operator|.
name|update
argument_list|(
name|newStudent
argument_list|)
expr_stmt|;
name|OnlineSectioningLog
operator|.
name|Enrollment
operator|.
name|Builder
name|enrollment
init|=
name|OnlineSectioningLog
operator|.
name|Enrollment
operator|.
name|newBuilder
argument_list|()
decl_stmt|;
name|enrollment
operator|.
name|setType
argument_list|(
name|OnlineSectioningLog
operator|.
name|Enrollment
operator|.
name|EnrollmentType
operator|.
name|STORED
argument_list|)
expr_stmt|;
for|for
control|(
name|Request
name|newRequest
range|:
name|newStudent
operator|.
name|getRequests
argument_list|()
control|)
block|{
name|action
operator|.
name|addRequest
argument_list|(
name|OnlineSectioningHelper
operator|.
name|toProto
argument_list|(
name|newRequest
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|newRequest
operator|.
name|getInitialAssignment
argument_list|()
operator|!=
literal|null
operator|&&
name|newRequest
operator|.
name|getInitialAssignment
argument_list|()
operator|.
name|isCourseRequest
argument_list|()
condition|)
for|for
control|(
name|Assignment
name|assignment
range|:
name|newRequest
operator|.
name|getInitialAssignment
argument_list|()
operator|.
name|getAssignments
argument_list|()
control|)
name|enrollment
operator|.
name|addSection
argument_list|(
name|OnlineSectioningHelper
operator|.
name|toProto
argument_list|(
name|assignment
argument_list|,
name|newRequest
operator|.
name|getInitialAssignment
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|action
operator|.
name|addEnrollment
argument_list|(
name|enrollment
argument_list|)
expr_stmt|;
block|}
name|action
operator|.
name|getStudentBuilder
argument_list|()
operator|.
name|setUniqueId
argument_list|(
name|newStudent
operator|.
name|getId
argument_list|()
argument_list|)
operator|.
name|setExternalId
argument_list|(
name|newStudent
operator|.
name|getExternalId
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|server
operator|.
name|notifyStudentChanged
argument_list|(
name|studentId
argument_list|,
operator|(
name|oldStudent
operator|==
literal|null
condition|?
literal|null
else|:
name|oldStudent
operator|.
name|getRequests
argument_list|()
operator|)
argument_list|,
operator|(
name|newStudent
operator|==
literal|null
condition|?
literal|null
else|:
name|newStudent
operator|.
name|getRequests
argument_list|()
operator|)
argument_list|)
expr_stmt|;
block|}
finally|finally
block|{
name|lock
operator|.
name|release
argument_list|()
expr_stmt|;
block|}
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
name|helper
operator|.
name|commitTransaction
argument_list|()
expr_stmt|;
return|return
literal|true
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
name|SectioningExceptionType
operator|.
name|UNKNOWN
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
literal|"reload-student"
return|;
block|}
block|}
end_class

end_unit

