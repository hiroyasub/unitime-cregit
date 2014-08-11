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
name|server
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
name|Hashtable
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
name|OnlineSectioningServerContext
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
name|match
operator|.
name|StudentMatcher
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
name|XExpectations
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
name|XOffering
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

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|InMemoryServer
extends|extends
name|AbstractLockingServer
block|{
specifier|private
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|XCourseId
argument_list|>
name|iCourseForId
init|=
operator|new
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|XCourseId
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|Hashtable
argument_list|<
name|String
argument_list|,
name|TreeSet
argument_list|<
name|XCourseId
argument_list|>
argument_list|>
name|iCourseForName
init|=
operator|new
name|Hashtable
argument_list|<
name|String
argument_list|,
name|TreeSet
argument_list|<
name|XCourseId
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|XStudent
argument_list|>
name|iStudentTable
init|=
operator|new
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|XStudent
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|XOffering
argument_list|>
name|iOfferingTable
init|=
operator|new
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|XOffering
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|List
argument_list|<
name|XCourseRequest
argument_list|>
argument_list|>
name|iOfferingRequests
init|=
operator|new
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|List
argument_list|<
name|XCourseRequest
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|XExpectations
argument_list|>
name|iExpectations
init|=
operator|new
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|XExpectations
argument_list|>
argument_list|()
decl_stmt|;
specifier|public
name|InMemoryServer
parameter_list|(
name|OnlineSectioningServerContext
name|context
parameter_list|)
throws|throws
name|SectioningException
block|{
name|super
argument_list|(
name|context
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|Collection
argument_list|<
name|XCourseId
argument_list|>
name|findCourses
parameter_list|(
name|String
name|query
parameter_list|,
name|Integer
name|limit
parameter_list|,
name|CourseMatcher
name|matcher
parameter_list|)
block|{
if|if
condition|(
name|matcher
operator|!=
literal|null
condition|)
name|matcher
operator|.
name|setServer
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|Lock
name|lock
init|=
name|readLock
argument_list|()
decl_stmt|;
try|try
block|{
name|SubSet
argument_list|<
name|XCourseId
argument_list|>
name|ret
init|=
operator|new
name|SubSet
argument_list|<
name|XCourseId
argument_list|>
argument_list|(
name|limit
argument_list|)
decl_stmt|;
name|String
name|queryInLowerCase
init|=
name|query
operator|.
name|toLowerCase
argument_list|()
decl_stmt|;
for|for
control|(
name|XCourseId
name|c
range|:
name|iCourseForId
operator|.
name|values
argument_list|()
control|)
block|{
if|if
condition|(
name|c
operator|.
name|matchCourseName
argument_list|(
name|queryInLowerCase
argument_list|)
operator|&&
operator|(
name|matcher
operator|==
literal|null
operator|||
name|matcher
operator|.
name|match
argument_list|(
name|c
argument_list|)
operator|)
condition|)
name|ret
operator|.
name|add
argument_list|(
name|c
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|ret
operator|.
name|isLimitReached
argument_list|()
operator|&&
name|queryInLowerCase
operator|.
name|length
argument_list|()
operator|>
literal|2
condition|)
block|{
for|for
control|(
name|XCourseId
name|c
range|:
name|iCourseForId
operator|.
name|values
argument_list|()
control|)
block|{
if|if
condition|(
name|c
operator|.
name|matchTitle
argument_list|(
name|queryInLowerCase
argument_list|)
operator|&&
operator|(
name|matcher
operator|==
literal|null
operator|||
name|matcher
operator|.
name|match
argument_list|(
name|c
argument_list|)
operator|)
condition|)
name|ret
operator|.
name|add
argument_list|(
name|c
argument_list|)
expr_stmt|;
block|}
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
name|Collection
argument_list|<
name|XCourseId
argument_list|>
name|findCourses
parameter_list|(
name|CourseMatcher
name|matcher
parameter_list|)
block|{
if|if
condition|(
name|matcher
operator|!=
literal|null
condition|)
name|matcher
operator|.
name|setServer
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|Lock
name|lock
init|=
name|readLock
argument_list|()
decl_stmt|;
try|try
block|{
name|Set
argument_list|<
name|XCourseId
argument_list|>
name|ret
init|=
operator|new
name|TreeSet
argument_list|<
name|XCourseId
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|XCourseId
name|c
range|:
name|iCourseForId
operator|.
name|values
argument_list|()
control|)
block|{
if|if
condition|(
name|matcher
operator|.
name|match
argument_list|(
name|c
argument_list|)
condition|)
name|ret
operator|.
name|add
argument_list|(
name|c
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
name|Collection
argument_list|<
name|XStudent
argument_list|>
name|findStudents
parameter_list|(
name|StudentMatcher
name|matcher
parameter_list|)
block|{
if|if
condition|(
name|matcher
operator|!=
literal|null
condition|)
name|matcher
operator|.
name|setServer
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|Lock
name|lock
init|=
name|readLock
argument_list|()
decl_stmt|;
try|try
block|{
name|List
argument_list|<
name|XStudent
argument_list|>
name|ret
init|=
operator|new
name|ArrayList
argument_list|<
name|XStudent
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|XStudent
name|s
range|:
name|iStudentTable
operator|.
name|values
argument_list|()
control|)
if|if
condition|(
name|matcher
operator|.
name|match
argument_list|(
name|s
argument_list|)
condition|)
name|ret
operator|.
name|add
argument_list|(
name|s
argument_list|)
expr_stmt|;
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
name|XCourseId
name|getCourse
parameter_list|(
name|String
name|course
parameter_list|)
block|{
name|Lock
name|lock
init|=
name|readLock
argument_list|()
decl_stmt|;
try|try
block|{
if|if
condition|(
name|course
operator|.
name|indexOf
argument_list|(
literal|'-'
argument_list|)
operator|>=
literal|0
condition|)
block|{
name|String
name|courseName
init|=
name|course
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|course
operator|.
name|indexOf
argument_list|(
literal|'-'
argument_list|)
argument_list|)
operator|.
name|trim
argument_list|()
decl_stmt|;
name|String
name|title
init|=
name|course
operator|.
name|substring
argument_list|(
name|course
operator|.
name|indexOf
argument_list|(
literal|'-'
argument_list|)
operator|+
literal|1
argument_list|)
operator|.
name|trim
argument_list|()
decl_stmt|;
name|TreeSet
argument_list|<
name|XCourseId
argument_list|>
name|infos
init|=
name|iCourseForName
operator|.
name|get
argument_list|(
name|courseName
operator|.
name|toLowerCase
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|infos
operator|!=
literal|null
operator|&&
operator|!
name|infos
operator|.
name|isEmpty
argument_list|()
condition|)
for|for
control|(
name|XCourseId
name|info
range|:
name|infos
control|)
if|if
condition|(
name|title
operator|.
name|equalsIgnoreCase
argument_list|(
name|info
operator|.
name|getTitle
argument_list|()
argument_list|)
condition|)
return|return
name|info
return|;
return|return
literal|null
return|;
block|}
else|else
block|{
name|TreeSet
argument_list|<
name|XCourseId
argument_list|>
name|infos
init|=
name|iCourseForName
operator|.
name|get
argument_list|(
name|course
operator|.
name|toLowerCase
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|infos
operator|!=
literal|null
operator|&&
operator|!
name|infos
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
name|infos
operator|.
name|first
argument_list|()
return|;
return|return
literal|null
return|;
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
block|}
specifier|private
name|XCourse
name|toCourse
parameter_list|(
name|XCourseId
name|course
parameter_list|)
block|{
if|if
condition|(
name|course
operator|==
literal|null
condition|)
return|return
literal|null
return|;
if|if
condition|(
name|course
operator|instanceof
name|XCourse
condition|)
return|return
operator|(
name|XCourse
operator|)
name|course
return|;
name|XOffering
name|offering
init|=
name|getOffering
argument_list|(
name|course
operator|.
name|getOfferingId
argument_list|()
argument_list|)
decl_stmt|;
return|return
name|offering
operator|==
literal|null
condition|?
literal|null
else|:
name|offering
operator|.
name|getCourse
argument_list|(
name|course
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|XCourse
name|getCourse
parameter_list|(
name|Long
name|courseId
parameter_list|)
block|{
name|Lock
name|lock
init|=
name|readLock
argument_list|()
decl_stmt|;
try|try
block|{
return|return
name|toCourse
argument_list|(
name|iCourseForId
operator|.
name|get
argument_list|(
name|courseId
argument_list|)
argument_list|)
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
name|XStudent
name|getStudent
parameter_list|(
name|Long
name|studentId
parameter_list|)
block|{
name|Lock
name|lock
init|=
name|readLock
argument_list|()
decl_stmt|;
try|try
block|{
return|return
name|iStudentTable
operator|.
name|get
argument_list|(
name|studentId
argument_list|)
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
name|XOffering
name|getOffering
parameter_list|(
name|Long
name|offeringId
parameter_list|)
block|{
name|Lock
name|lock
init|=
name|readLock
argument_list|()
decl_stmt|;
try|try
block|{
return|return
name|iOfferingTable
operator|.
name|get
argument_list|(
name|offeringId
argument_list|)
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
name|Collection
argument_list|<
name|XCourseRequest
argument_list|>
name|getRequests
parameter_list|(
name|Long
name|offeringId
parameter_list|)
block|{
name|Lock
name|lock
init|=
name|readLock
argument_list|()
decl_stmt|;
try|try
block|{
return|return
name|iOfferingRequests
operator|.
name|get
argument_list|(
name|offeringId
argument_list|)
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
name|XExpectations
name|getExpectations
parameter_list|(
name|Long
name|offeringId
parameter_list|)
block|{
name|Lock
name|lock
init|=
name|readLock
argument_list|()
decl_stmt|;
try|try
block|{
name|XExpectations
name|expectations
init|=
name|iExpectations
operator|.
name|get
argument_list|(
name|offeringId
argument_list|)
decl_stmt|;
return|return
name|expectations
operator|==
literal|null
condition|?
operator|new
name|XExpectations
argument_list|(
name|offeringId
argument_list|)
else|:
name|expectations
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
name|void
name|update
parameter_list|(
name|XExpectations
name|expectations
parameter_list|)
block|{
name|Lock
name|lock
init|=
name|writeLock
argument_list|()
decl_stmt|;
try|try
block|{
name|iExpectations
operator|.
name|put
argument_list|(
name|expectations
operator|.
name|getOfferingId
argument_list|()
argument_list|,
name|expectations
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
block|}
annotation|@
name|Override
specifier|public
name|void
name|remove
parameter_list|(
name|XStudent
name|student
parameter_list|)
block|{
name|Lock
name|lock
init|=
name|writeLock
argument_list|()
decl_stmt|;
try|try
block|{
name|XStudent
name|oldStudent
init|=
name|iStudentTable
operator|.
name|remove
argument_list|(
name|student
operator|.
name|getStudentId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|oldStudent
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|XRequest
name|request
range|:
name|oldStudent
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
for|for
control|(
name|XCourseId
name|course
range|:
operator|(
operator|(
name|XCourseRequest
operator|)
name|request
operator|)
operator|.
name|getCourseIds
argument_list|()
control|)
block|{
name|List
argument_list|<
name|XCourseRequest
argument_list|>
name|requests
init|=
name|iOfferingRequests
operator|.
name|get
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
operator|!=
literal|null
condition|)
name|requests
operator|.
name|remove
argument_list|(
name|request
argument_list|)
expr_stmt|;
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
block|}
annotation|@
name|Override
specifier|public
name|void
name|update
parameter_list|(
name|XStudent
name|student
parameter_list|,
name|boolean
name|updateRequests
parameter_list|)
block|{
name|Lock
name|lock
init|=
name|writeLock
argument_list|()
decl_stmt|;
try|try
block|{
name|XStudent
name|oldStudent
init|=
name|iStudentTable
operator|.
name|put
argument_list|(
name|student
operator|.
name|getStudentId
argument_list|()
argument_list|,
name|student
argument_list|)
decl_stmt|;
if|if
condition|(
name|updateRequests
condition|)
block|{
if|if
condition|(
name|oldStudent
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|XRequest
name|request
range|:
name|oldStudent
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
for|for
control|(
name|XCourseId
name|course
range|:
operator|(
operator|(
name|XCourseRequest
operator|)
name|request
operator|)
operator|.
name|getCourseIds
argument_list|()
control|)
block|{
name|List
argument_list|<
name|XCourseRequest
argument_list|>
name|requests
init|=
name|iOfferingRequests
operator|.
name|get
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
operator|!=
literal|null
condition|)
name|requests
operator|.
name|remove
argument_list|(
name|request
argument_list|)
expr_stmt|;
block|}
block|}
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
for|for
control|(
name|XCourseId
name|course
range|:
operator|(
operator|(
name|XCourseRequest
operator|)
name|request
operator|)
operator|.
name|getCourseIds
argument_list|()
control|)
block|{
name|List
argument_list|<
name|XCourseRequest
argument_list|>
name|requests
init|=
name|iOfferingRequests
operator|.
name|get
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
condition|)
block|{
name|requests
operator|=
operator|new
name|ArrayList
argument_list|<
name|XCourseRequest
argument_list|>
argument_list|()
expr_stmt|;
name|iOfferingRequests
operator|.
name|put
argument_list|(
name|course
operator|.
name|getOfferingId
argument_list|()
argument_list|,
name|requests
argument_list|)
expr_stmt|;
block|}
name|requests
operator|.
name|add
argument_list|(
operator|(
name|XCourseRequest
operator|)
name|request
argument_list|)
expr_stmt|;
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
block|}
annotation|@
name|Override
specifier|public
name|void
name|remove
parameter_list|(
name|XOffering
name|offering
parameter_list|)
block|{
name|remove
argument_list|(
name|offering
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|remove
parameter_list|(
name|XOffering
name|offering
parameter_list|,
name|boolean
name|removeExpectations
parameter_list|)
block|{
name|Lock
name|lock
init|=
name|writeLock
argument_list|()
decl_stmt|;
try|try
block|{
for|for
control|(
name|XCourse
name|course
range|:
name|offering
operator|.
name|getCourses
argument_list|()
control|)
block|{
name|iCourseForId
operator|.
name|remove
argument_list|(
name|course
operator|.
name|getCourseId
argument_list|()
argument_list|)
expr_stmt|;
name|TreeSet
argument_list|<
name|XCourseId
argument_list|>
name|courses
init|=
name|iCourseForName
operator|.
name|get
argument_list|(
name|course
operator|.
name|getCourseNameInLowerCase
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|courses
operator|!=
literal|null
condition|)
block|{
name|courses
operator|.
name|remove
argument_list|(
name|course
argument_list|)
expr_stmt|;
if|if
condition|(
name|courses
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
for|for
control|(
name|XCourseId
name|x
range|:
name|courses
control|)
name|x
operator|.
name|setHasUniqueName
argument_list|(
literal|true
argument_list|)
expr_stmt|;
if|if
condition|(
name|courses
operator|.
name|isEmpty
argument_list|()
condition|)
name|iCourseForName
operator|.
name|remove
argument_list|(
name|course
operator|.
name|getCourseNameInLowerCase
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
name|iOfferingTable
operator|.
name|remove
argument_list|(
name|offering
operator|.
name|getOfferingId
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|removeExpectations
condition|)
name|iExpectations
operator|.
name|remove
argument_list|(
name|offering
operator|.
name|getOfferingId
argument_list|()
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
block|}
annotation|@
name|Override
specifier|public
name|void
name|update
parameter_list|(
name|XOffering
name|offering
parameter_list|)
block|{
name|Lock
name|lock
init|=
name|writeLock
argument_list|()
decl_stmt|;
try|try
block|{
name|XOffering
name|oldOffering
init|=
name|iOfferingTable
operator|.
name|get
argument_list|(
name|offering
operator|.
name|getOfferingId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|oldOffering
operator|!=
literal|null
condition|)
name|remove
argument_list|(
name|oldOffering
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|iOfferingTable
operator|.
name|put
argument_list|(
name|offering
operator|.
name|getOfferingId
argument_list|()
argument_list|,
name|offering
argument_list|)
expr_stmt|;
for|for
control|(
name|XCourse
name|course
range|:
name|offering
operator|.
name|getCourses
argument_list|()
control|)
block|{
name|iCourseForId
operator|.
name|put
argument_list|(
name|course
operator|.
name|getCourseId
argument_list|()
argument_list|,
name|course
argument_list|)
expr_stmt|;
name|TreeSet
argument_list|<
name|XCourseId
argument_list|>
name|courses
init|=
name|iCourseForName
operator|.
name|get
argument_list|(
name|course
operator|.
name|getCourseNameInLowerCase
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|courses
operator|==
literal|null
condition|)
block|{
name|courses
operator|=
operator|new
name|TreeSet
argument_list|<
name|XCourseId
argument_list|>
argument_list|()
expr_stmt|;
name|iCourseForName
operator|.
name|put
argument_list|(
name|course
operator|.
name|getCourseNameInLowerCase
argument_list|()
argument_list|,
name|courses
argument_list|)
expr_stmt|;
block|}
name|courses
operator|.
name|add
argument_list|(
name|course
argument_list|)
expr_stmt|;
if|if
condition|(
name|courses
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
for|for
control|(
name|XCourseId
name|x
range|:
name|courses
control|)
name|x
operator|.
name|setHasUniqueName
argument_list|(
literal|true
argument_list|)
expr_stmt|;
if|else if
condition|(
name|courses
operator|.
name|size
argument_list|()
operator|>
literal|1
condition|)
for|for
control|(
name|XCourseId
name|x
range|:
name|courses
control|)
name|x
operator|.
name|setHasUniqueName
argument_list|(
literal|false
argument_list|)
expr_stmt|;
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
block|}
annotation|@
name|Override
specifier|public
name|void
name|clearAll
parameter_list|()
block|{
name|Lock
name|lock
init|=
name|writeLock
argument_list|()
decl_stmt|;
try|try
block|{
name|iStudentTable
operator|.
name|clear
argument_list|()
expr_stmt|;
name|iOfferingTable
operator|.
name|clear
argument_list|()
expr_stmt|;
name|iCourseForId
operator|.
name|clear
argument_list|()
expr_stmt|;
name|iCourseForName
operator|.
name|clear
argument_list|()
expr_stmt|;
name|iOfferingRequests
operator|.
name|clear
argument_list|()
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
block|}
annotation|@
name|Override
specifier|public
name|void
name|clearAllStudents
parameter_list|()
block|{
name|Lock
name|lock
init|=
name|writeLock
argument_list|()
decl_stmt|;
try|try
block|{
name|iStudentTable
operator|.
name|clear
argument_list|()
expr_stmt|;
name|iOfferingRequests
operator|.
name|clear
argument_list|()
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
block|}
annotation|@
name|Override
specifier|public
name|XCourseRequest
name|assign
parameter_list|(
name|XCourseRequest
name|request
parameter_list|,
name|XEnrollment
name|enrollment
parameter_list|)
block|{
name|Lock
name|lock
init|=
name|writeLock
argument_list|()
decl_stmt|;
try|try
block|{
name|XStudent
name|student
init|=
name|iStudentTable
operator|.
name|get
argument_list|(
name|request
operator|.
name|getStudentId
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|XRequest
name|r
range|:
name|student
operator|.
name|getRequests
argument_list|()
control|)
block|{
if|if
condition|(
name|r
operator|.
name|equals
argument_list|(
name|request
argument_list|)
condition|)
block|{
name|XCourseRequest
name|cr
init|=
operator|(
name|XCourseRequest
operator|)
name|r
decl_stmt|;
comment|// remove old requests
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
name|List
argument_list|<
name|XCourseRequest
argument_list|>
name|requests
init|=
name|iOfferingRequests
operator|.
name|get
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
operator|!=
literal|null
condition|)
name|requests
operator|.
name|remove
argument_list|(
name|cr
argument_list|)
expr_stmt|;
block|}
comment|// assign
name|cr
operator|.
name|setEnrollment
argument_list|(
name|enrollment
argument_list|)
expr_stmt|;
comment|// put new requests
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
name|List
argument_list|<
name|XCourseRequest
argument_list|>
name|requests
init|=
name|iOfferingRequests
operator|.
name|get
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
condition|)
block|{
name|requests
operator|=
operator|new
name|ArrayList
argument_list|<
name|XCourseRequest
argument_list|>
argument_list|()
expr_stmt|;
name|iOfferingRequests
operator|.
name|put
argument_list|(
name|course
operator|.
name|getOfferingId
argument_list|()
argument_list|,
name|requests
argument_list|)
expr_stmt|;
block|}
name|requests
operator|.
name|add
argument_list|(
name|cr
argument_list|)
expr_stmt|;
block|}
return|return
name|cr
return|;
block|}
block|}
return|return
literal|null
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
name|XCourseRequest
name|waitlist
parameter_list|(
name|XCourseRequest
name|request
parameter_list|,
name|boolean
name|waitlist
parameter_list|)
block|{
name|Lock
name|lock
init|=
name|writeLock
argument_list|()
decl_stmt|;
try|try
block|{
name|XStudent
name|student
init|=
name|iStudentTable
operator|.
name|get
argument_list|(
name|request
operator|.
name|getStudentId
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|XRequest
name|r
range|:
name|student
operator|.
name|getRequests
argument_list|()
control|)
block|{
if|if
condition|(
name|r
operator|.
name|equals
argument_list|(
name|request
argument_list|)
condition|)
block|{
name|XCourseRequest
name|cr
init|=
operator|(
name|XCourseRequest
operator|)
name|r
decl_stmt|;
comment|// remove old requests
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
name|List
argument_list|<
name|XCourseRequest
argument_list|>
name|requests
init|=
name|iOfferingRequests
operator|.
name|get
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
operator|!=
literal|null
condition|)
name|requests
operator|.
name|remove
argument_list|(
name|cr
argument_list|)
expr_stmt|;
block|}
comment|// assign
name|cr
operator|.
name|setWaitlist
argument_list|(
name|waitlist
argument_list|)
expr_stmt|;
comment|// put new requests
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
name|List
argument_list|<
name|XCourseRequest
argument_list|>
name|requests
init|=
name|iOfferingRequests
operator|.
name|get
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
condition|)
block|{
name|requests
operator|=
operator|new
name|ArrayList
argument_list|<
name|XCourseRequest
argument_list|>
argument_list|()
expr_stmt|;
name|iOfferingRequests
operator|.
name|put
argument_list|(
name|course
operator|.
name|getOfferingId
argument_list|()
argument_list|,
name|requests
argument_list|)
expr_stmt|;
block|}
name|requests
operator|.
name|add
argument_list|(
name|cr
argument_list|)
expr_stmt|;
block|}
return|return
name|cr
return|;
block|}
block|}
return|return
literal|null
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
block|}
end_class

end_unit

