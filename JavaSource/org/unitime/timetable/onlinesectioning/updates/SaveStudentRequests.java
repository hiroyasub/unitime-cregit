begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 - 3.5 (University Timetabling Application)  * Copyright (C) 2011 - 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|Date
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
name|HashSet
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
name|java
operator|.
name|util
operator|.
name|Map
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
name|ClassWaitList
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
name|CourseOffering
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
name|CourseRequest
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
name|CourseRequestOption
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
name|FreeTime
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
name|StudentClassEnrollment
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
name|CourseOfferingDAO
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
name|SaveStudentRequests
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
specifier|private
name|Long
name|iStudentId
decl_stmt|;
specifier|private
name|CourseRequestInterface
name|iRequest
decl_stmt|;
specifier|private
name|boolean
name|iKeepEnrollments
decl_stmt|;
specifier|public
name|SaveStudentRequests
parameter_list|(
name|Long
name|studentId
parameter_list|,
name|CourseRequestInterface
name|request
parameter_list|,
name|boolean
name|keepEnrollments
parameter_list|)
block|{
name|iStudentId
operator|=
name|studentId
expr_stmt|;
name|iRequest
operator|=
name|request
expr_stmt|;
name|iKeepEnrollments
operator|=
name|keepEnrollments
expr_stmt|;
block|}
specifier|public
name|Long
name|getStudentId
parameter_list|()
block|{
return|return
name|iStudentId
return|;
block|}
specifier|public
name|CourseRequestInterface
name|getRequest
parameter_list|()
block|{
return|return
name|iRequest
return|;
block|}
specifier|public
name|boolean
name|getKeepEnrollments
parameter_list|()
block|{
return|return
name|iKeepEnrollments
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
name|Lock
name|lock
init|=
name|server
operator|.
name|lockStudent
argument_list|(
name|getStudentId
argument_list|()
argument_list|,
literal|null
argument_list|,
literal|true
argument_list|)
decl_stmt|;
try|try
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
name|getStudentId
argument_list|()
argument_list|,
name|helper
operator|.
name|getHibSession
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|student
operator|==
literal|null
condition|)
throw|throw
operator|new
name|SectioningException
argument_list|(
name|MSG
operator|.
name|exceptionBadStudentId
argument_list|()
argument_list|)
throw|;
name|OnlineSectioningLog
operator|.
name|Action
operator|.
name|Builder
name|action
init|=
name|helper
operator|.
name|getAction
argument_list|()
decl_stmt|;
if|if
condition|(
name|getRequest
argument_list|()
operator|.
name|getStudentId
argument_list|()
operator|!=
literal|null
condition|)
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
name|getStudentId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
comment|// Save requests
name|saveRequest
argument_list|(
name|server
argument_list|,
name|helper
argument_list|,
name|student
argument_list|,
name|getRequest
argument_list|()
argument_list|,
name|getKeepEnrollments
argument_list|()
argument_list|)
expr_stmt|;
comment|// Reload student
name|XStudent
name|oldStudent
init|=
name|server
operator|.
name|getStudent
argument_list|(
name|getStudentId
argument_list|()
argument_list|)
decl_stmt|;
name|XStudent
name|newStudent
init|=
literal|null
decl_stmt|;
try|try
block|{
name|newStudent
operator|=
name|ReloadAllData
operator|.
name|loadStudent
argument_list|(
name|student
argument_list|,
literal|null
argument_list|,
name|server
argument_list|,
name|helper
argument_list|)
expr_stmt|;
name|server
operator|.
name|update
argument_list|(
name|newStudent
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|action
operator|.
name|getStudentBuilder
argument_list|()
operator|.
name|setUniqueId
argument_list|(
name|newStudent
operator|.
name|getStudentId
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
operator|.
name|setName
argument_list|(
name|newStudent
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|XRequest
name|r
range|:
name|newStudent
operator|.
name|getRequests
argument_list|()
control|)
name|action
operator|.
name|addRequest
argument_list|(
name|OnlineSectioningHelper
operator|.
name|toProto
argument_list|(
name|r
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
if|if
condition|(
name|e
operator|instanceof
name|RuntimeException
condition|)
throw|throw
operator|(
name|RuntimeException
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
name|server
operator|.
name|execute
argument_list|(
operator|new
name|NotifyStudentAction
argument_list|(
name|getStudentId
argument_list|()
argument_list|,
name|oldStudent
argument_list|)
argument_list|,
name|helper
operator|.
name|getUser
argument_list|()
argument_list|)
expr_stmt|;
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
literal|"save-request"
return|;
block|}
specifier|public
specifier|static
name|CourseOffering
name|getCourse
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|Long
name|sessionId
parameter_list|,
name|String
name|courseName
parameter_list|)
block|{
for|for
control|(
name|CourseOffering
name|co
range|:
operator|(
name|List
argument_list|<
name|CourseOffering
argument_list|>
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"select c from CourseOffering c where "
operator|+
literal|"c.subjectArea.session.uniqueId = :sessionId and "
operator|+
literal|"lower(c.subjectArea.subjectAreaAbbreviation || ' ' || c.courseNbr) = :course"
argument_list|)
operator|.
name|setString
argument_list|(
literal|"course"
argument_list|,
name|courseName
operator|.
name|toLowerCase
argument_list|()
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|setMaxResults
argument_list|(
literal|1
argument_list|)
operator|.
name|list
argument_list|()
control|)
block|{
return|return
name|co
return|;
block|}
return|return
literal|null
return|;
block|}
specifier|public
specifier|static
name|Map
argument_list|<
name|Long
argument_list|,
name|CourseRequest
argument_list|>
name|saveRequest
parameter_list|(
name|OnlineSectioningServer
name|server
parameter_list|,
name|OnlineSectioningHelper
name|helper
parameter_list|,
name|Student
name|student
parameter_list|,
name|CourseRequestInterface
name|request
parameter_list|,
name|boolean
name|keepEnrollments
parameter_list|)
throws|throws
name|SectioningException
block|{
name|Set
argument_list|<
name|CourseDemand
argument_list|>
name|remaining
init|=
operator|new
name|TreeSet
argument_list|<
name|CourseDemand
argument_list|>
argument_list|(
name|student
operator|.
name|getCourseDemands
argument_list|()
argument_list|)
decl_stmt|;
name|int
name|priority
init|=
literal|0
decl_stmt|;
name|Date
name|ts
init|=
operator|new
name|Date
argument_list|()
decl_stmt|;
name|Map
argument_list|<
name|Long
argument_list|,
name|CourseRequest
argument_list|>
name|course2request
init|=
operator|new
name|HashMap
argument_list|<
name|Long
argument_list|,
name|CourseRequest
argument_list|>
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|CourseRequest
argument_list|>
name|unusedRequests
init|=
operator|new
name|ArrayList
argument_list|<
name|CourseRequest
argument_list|>
argument_list|()
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
if|if
condition|(
name|r
operator|.
name|hasRequestedFreeTime
argument_list|()
operator|&&
name|r
operator|.
name|hasRequestedCourse
argument_list|()
operator|&&
operator|(
operator|(
name|server
operator|==
literal|null
operator|&&
name|getCourse
argument_list|(
name|helper
operator|.
name|getHibSession
argument_list|()
argument_list|,
name|request
operator|.
name|getAcademicSessionId
argument_list|()
argument_list|,
name|r
operator|.
name|getRequestedCourse
argument_list|()
argument_list|)
operator|!=
literal|null
operator|)
operator|||
operator|(
name|server
operator|!=
literal|null
operator|&&
name|server
operator|.
name|getCourse
argument_list|(
name|r
operator|.
name|getRequestedCourse
argument_list|()
argument_list|)
operator|!=
literal|null
operator|)
operator|)
condition|)
name|r
operator|.
name|getRequestedFreeTime
argument_list|()
operator|.
name|clear
argument_list|()
expr_stmt|;
if|if
condition|(
name|r
operator|.
name|hasRequestedFreeTime
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
name|r
operator|.
name|getRequestedFreeTime
argument_list|()
control|)
block|{
name|CourseDemand
name|cd
init|=
literal|null
decl_stmt|;
for|for
control|(
name|Iterator
argument_list|<
name|CourseDemand
argument_list|>
name|i
init|=
name|remaining
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|CourseDemand
name|adept
init|=
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|adept
operator|.
name|getFreeTime
argument_list|()
operator|==
literal|null
condition|)
continue|continue;
name|cd
operator|=
name|adept
expr_stmt|;
name|i
operator|.
name|remove
argument_list|()
expr_stmt|;
break|break;
block|}
if|if
condition|(
name|cd
operator|==
literal|null
condition|)
block|{
name|cd
operator|=
operator|new
name|CourseDemand
argument_list|()
expr_stmt|;
name|cd
operator|.
name|setTimestamp
argument_list|(
name|ts
argument_list|)
expr_stmt|;
name|cd
operator|.
name|setChangedBy
argument_list|(
name|helper
operator|.
name|getUser
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
name|helper
operator|.
name|getUser
argument_list|()
operator|.
name|getExternalId
argument_list|()
argument_list|)
expr_stmt|;
name|student
operator|.
name|getCourseDemands
argument_list|()
operator|.
name|add
argument_list|(
name|cd
argument_list|)
expr_stmt|;
name|cd
operator|.
name|setStudent
argument_list|(
name|student
argument_list|)
expr_stmt|;
block|}
name|cd
operator|.
name|setAlternative
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|cd
operator|.
name|setPriority
argument_list|(
name|priority
argument_list|)
expr_stmt|;
name|cd
operator|.
name|setWaitlist
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|FreeTime
name|free
init|=
name|cd
operator|.
name|getFreeTime
argument_list|()
decl_stmt|;
if|if
condition|(
name|free
operator|==
literal|null
condition|)
block|{
name|free
operator|=
operator|new
name|FreeTime
argument_list|()
expr_stmt|;
name|cd
operator|.
name|setFreeTime
argument_list|(
name|free
argument_list|)
expr_stmt|;
block|}
name|free
operator|.
name|setCategory
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|free
operator|.
name|setDayCode
argument_list|(
name|DayCode
operator|.
name|toInt
argument_list|(
name|DayCode
operator|.
name|toDayCodes
argument_list|(
name|ft
operator|.
name|getDays
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|free
operator|.
name|setStartSlot
argument_list|(
name|ft
operator|.
name|getStart
argument_list|()
argument_list|)
expr_stmt|;
name|free
operator|.
name|setLength
argument_list|(
name|ft
operator|.
name|getLength
argument_list|()
argument_list|)
expr_stmt|;
name|free
operator|.
name|setSession
argument_list|(
name|student
operator|.
name|getSession
argument_list|()
argument_list|)
expr_stmt|;
name|free
operator|.
name|setName
argument_list|(
name|ft
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|helper
operator|.
name|getHibSession
argument_list|()
operator|.
name|saveOrUpdate
argument_list|(
name|free
argument_list|)
expr_stmt|;
name|helper
operator|.
name|getHibSession
argument_list|()
operator|.
name|saveOrUpdate
argument_list|(
name|cd
argument_list|)
expr_stmt|;
block|}
block|}
if|else if
condition|(
name|r
operator|.
name|hasRequestedCourse
argument_list|()
operator|||
name|r
operator|.
name|hasFirstAlternative
argument_list|()
operator|||
name|r
operator|.
name|hasSecondAlternative
argument_list|()
condition|)
block|{
name|List
argument_list|<
name|CourseOffering
argument_list|>
name|courses
init|=
operator|new
name|ArrayList
argument_list|<
name|CourseOffering
argument_list|>
argument_list|()
decl_stmt|;
if|if
condition|(
name|r
operator|.
name|hasRequestedCourse
argument_list|()
condition|)
block|{
name|XCourseId
name|c
init|=
operator|(
name|server
operator|==
literal|null
condition|?
literal|null
else|:
name|server
operator|.
name|getCourse
argument_list|(
name|r
operator|.
name|getRequestedCourse
argument_list|()
argument_list|)
operator|)
decl_stmt|;
name|CourseOffering
name|co
init|=
operator|(
name|c
operator|==
literal|null
condition|?
name|getCourse
argument_list|(
name|helper
operator|.
name|getHibSession
argument_list|()
argument_list|,
name|request
operator|.
name|getAcademicSessionId
argument_list|()
argument_list|,
name|r
operator|.
name|getRequestedCourse
argument_list|()
argument_list|)
else|:
name|CourseOfferingDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|c
operator|.
name|getCourseId
argument_list|()
argument_list|,
name|helper
operator|.
name|getHibSession
argument_list|()
argument_list|)
operator|)
decl_stmt|;
if|if
condition|(
name|co
operator|!=
literal|null
condition|)
name|courses
operator|.
name|add
argument_list|(
name|co
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|r
operator|.
name|hasFirstAlternative
argument_list|()
condition|)
block|{
name|XCourseId
name|c
init|=
operator|(
name|server
operator|==
literal|null
condition|?
literal|null
else|:
name|server
operator|.
name|getCourse
argument_list|(
name|r
operator|.
name|getFirstAlternative
argument_list|()
argument_list|)
operator|)
decl_stmt|;
name|CourseOffering
name|co
init|=
operator|(
name|c
operator|==
literal|null
condition|?
name|getCourse
argument_list|(
name|helper
operator|.
name|getHibSession
argument_list|()
argument_list|,
name|request
operator|.
name|getAcademicSessionId
argument_list|()
argument_list|,
name|r
operator|.
name|getFirstAlternative
argument_list|()
argument_list|)
else|:
name|CourseOfferingDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|c
operator|.
name|getCourseId
argument_list|()
argument_list|,
name|helper
operator|.
name|getHibSession
argument_list|()
argument_list|)
operator|)
decl_stmt|;
if|if
condition|(
name|co
operator|!=
literal|null
condition|)
name|courses
operator|.
name|add
argument_list|(
name|co
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|r
operator|.
name|hasSecondAlternative
argument_list|()
condition|)
block|{
name|XCourseId
name|c
init|=
operator|(
name|server
operator|==
literal|null
condition|?
literal|null
else|:
name|server
operator|.
name|getCourse
argument_list|(
name|r
operator|.
name|getSecondAlternative
argument_list|()
argument_list|)
operator|)
decl_stmt|;
name|CourseOffering
name|co
init|=
operator|(
name|c
operator|==
literal|null
condition|?
name|getCourse
argument_list|(
name|helper
operator|.
name|getHibSession
argument_list|()
argument_list|,
name|request
operator|.
name|getAcademicSessionId
argument_list|()
argument_list|,
name|r
operator|.
name|getSecondAlternative
argument_list|()
argument_list|)
else|:
name|CourseOfferingDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|c
operator|.
name|getCourseId
argument_list|()
argument_list|,
name|helper
operator|.
name|getHibSession
argument_list|()
argument_list|)
operator|)
decl_stmt|;
if|if
condition|(
name|co
operator|!=
literal|null
condition|)
name|courses
operator|.
name|add
argument_list|(
name|co
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|courses
operator|.
name|isEmpty
argument_list|()
condition|)
continue|continue;
name|CourseDemand
name|cd
init|=
literal|null
decl_stmt|;
name|adepts
label|:
for|for
control|(
name|Iterator
argument_list|<
name|CourseDemand
argument_list|>
name|i
init|=
name|remaining
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|CourseDemand
name|adept
init|=
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|adept
operator|.
name|getFreeTime
argument_list|()
operator|!=
literal|null
condition|)
continue|continue;
for|for
control|(
name|CourseRequest
name|cr
range|:
name|adept
operator|.
name|getCourseRequests
argument_list|()
control|)
if|if
condition|(
name|cr
operator|.
name|getCourseOffering
argument_list|()
operator|.
name|getUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
name|courses
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|getUniqueId
argument_list|()
argument_list|)
condition|)
block|{
name|cd
operator|=
name|adept
expr_stmt|;
name|i
operator|.
name|remove
argument_list|()
expr_stmt|;
break|break
name|adepts
break|;
block|}
block|}
if|if
condition|(
name|cd
operator|==
literal|null
condition|)
block|{
name|cd
operator|=
operator|new
name|CourseDemand
argument_list|()
expr_stmt|;
name|cd
operator|.
name|setTimestamp
argument_list|(
name|ts
argument_list|)
expr_stmt|;
name|cd
operator|.
name|setChangedBy
argument_list|(
name|helper
operator|.
name|getUser
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
name|helper
operator|.
name|getUser
argument_list|()
operator|.
name|getExternalId
argument_list|()
argument_list|)
expr_stmt|;
name|cd
operator|.
name|setCourseRequests
argument_list|(
operator|new
name|HashSet
argument_list|<
name|CourseRequest
argument_list|>
argument_list|()
argument_list|)
expr_stmt|;
name|cd
operator|.
name|setStudent
argument_list|(
name|student
argument_list|)
expr_stmt|;
name|student
operator|.
name|getCourseDemands
argument_list|()
operator|.
name|add
argument_list|(
name|cd
argument_list|)
expr_stmt|;
block|}
name|cd
operator|.
name|setAlternative
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|cd
operator|.
name|setPriority
argument_list|(
name|priority
argument_list|)
expr_stmt|;
name|cd
operator|.
name|setWaitlist
argument_list|(
name|r
operator|.
name|isWaitList
argument_list|()
argument_list|)
expr_stmt|;
name|Iterator
argument_list|<
name|CourseRequest
argument_list|>
name|requests
init|=
operator|new
name|TreeSet
argument_list|<
name|CourseRequest
argument_list|>
argument_list|(
name|cd
operator|.
name|getCourseRequests
argument_list|()
argument_list|)
operator|.
name|iterator
argument_list|()
decl_stmt|;
name|int
name|order
init|=
literal|0
decl_stmt|;
for|for
control|(
name|CourseOffering
name|co
range|:
name|courses
control|)
block|{
name|CourseRequest
name|cr
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|requests
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|cr
operator|=
name|requests
operator|.
name|next
argument_list|()
expr_stmt|;
comment|/* 						if (cr.getClassEnrollments() != null) 							cr.getClassEnrollments().clear(); 							*/
if|if
condition|(
name|cr
operator|.
name|getCourseRequestOptions
argument_list|()
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|Iterator
argument_list|<
name|CourseRequestOption
argument_list|>
name|i
init|=
name|cr
operator|.
name|getCourseRequestOptions
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
name|helper
operator|.
name|getHibSession
argument_list|()
operator|.
name|delete
argument_list|(
name|i
operator|.
name|next
argument_list|()
argument_list|)
expr_stmt|;
name|cr
operator|.
name|getCourseRequestOptions
argument_list|()
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
block|}
else|else
block|{
name|cr
operator|=
operator|new
name|CourseRequest
argument_list|()
expr_stmt|;
name|cd
operator|.
name|getCourseRequests
argument_list|()
operator|.
name|add
argument_list|(
name|cr
argument_list|)
expr_stmt|;
name|cr
operator|.
name|setCourseDemand
argument_list|(
name|cd
argument_list|)
expr_stmt|;
name|cr
operator|.
name|setCourseRequestOptions
argument_list|(
operator|new
name|HashSet
argument_list|<
name|CourseRequestOption
argument_list|>
argument_list|()
argument_list|)
expr_stmt|;
comment|// cr.setClassEnrollments(new HashSet<StudentClassEnrollment>());
block|}
name|cr
operator|.
name|setAllowOverlap
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|cr
operator|.
name|setCredit
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|cr
operator|.
name|setOrder
argument_list|(
name|order
operator|++
argument_list|)
expr_stmt|;
name|cr
operator|.
name|setCourseOffering
argument_list|(
name|co
argument_list|)
expr_stmt|;
name|course2request
operator|.
name|put
argument_list|(
name|co
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|cr
argument_list|)
expr_stmt|;
block|}
while|while
condition|(
name|requests
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|unusedRequests
operator|.
name|add
argument_list|(
name|requests
operator|.
name|next
argument_list|()
argument_list|)
expr_stmt|;
name|requests
operator|.
name|remove
argument_list|()
expr_stmt|;
block|}
name|helper
operator|.
name|getHibSession
argument_list|()
operator|.
name|saveOrUpdate
argument_list|(
name|cd
argument_list|)
expr_stmt|;
block|}
name|priority
operator|++
expr_stmt|;
block|}
for|for
control|(
name|Iterator
argument_list|<
name|StudentClassEnrollment
argument_list|>
name|i
init|=
name|student
operator|.
name|getClassEnrollments
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|StudentClassEnrollment
name|enrl
init|=
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|keepEnrollments
condition|)
block|{
name|CourseRequest
name|cr
init|=
name|course2request
operator|.
name|get
argument_list|(
name|enrl
operator|.
name|getCourseOffering
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|cr
operator|==
literal|null
condition|)
block|{
name|enrl
operator|.
name|setCourseRequest
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|helper
operator|.
name|getHibSession
argument_list|()
operator|.
name|saveOrUpdate
argument_list|(
name|enrl
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|enrl
operator|.
name|setCourseRequest
argument_list|(
name|cr
argument_list|)
expr_stmt|;
comment|/* 					if (cr.getClassEnrollments() == null) 						cr.setClassEnrollments(new HashSet<StudentClassEnrollment>()); 					cr.getClassEnrollments().add(enrl); 					*/
name|helper
operator|.
name|getHibSession
argument_list|()
operator|.
name|saveOrUpdate
argument_list|(
name|enrl
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|enrl
operator|.
name|getClazz
argument_list|()
operator|.
name|getStudentEnrollments
argument_list|()
operator|.
name|remove
argument_list|(
name|enrl
argument_list|)
expr_stmt|;
comment|/* 				if (enrl.getCourseRequest() != null) 					enrl.getCourseRequest().getClassEnrollments().remove(enrl); 					*/
name|helper
operator|.
name|getHibSession
argument_list|()
operator|.
name|delete
argument_list|(
name|enrl
argument_list|)
expr_stmt|;
name|i
operator|.
name|remove
argument_list|()
expr_stmt|;
block|}
block|}
if|if
condition|(
operator|!
name|keepEnrollments
condition|)
block|{
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
if|if
condition|(
name|cd
operator|.
name|getCourseRequests
argument_list|()
operator|!=
literal|null
condition|)
for|for
control|(
name|CourseRequest
name|cr
range|:
name|cd
operator|.
name|getCourseRequests
argument_list|()
control|)
if|if
condition|(
name|cr
operator|.
name|getClassWaitLists
argument_list|()
operator|!=
literal|null
condition|)
for|for
control|(
name|Iterator
argument_list|<
name|ClassWaitList
argument_list|>
name|i
init|=
name|cr
operator|.
name|getClassWaitLists
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|helper
operator|.
name|getHibSession
argument_list|()
operator|.
name|delete
argument_list|(
name|i
operator|.
name|next
argument_list|()
argument_list|)
expr_stmt|;
name|i
operator|.
name|remove
argument_list|()
expr_stmt|;
block|}
block|}
for|for
control|(
name|CourseRequest
name|cr
range|:
name|unusedRequests
control|)
name|helper
operator|.
name|getHibSession
argument_list|()
operator|.
name|delete
argument_list|(
name|cr
argument_list|)
expr_stmt|;
for|for
control|(
name|CourseDemand
name|cd
range|:
name|remaining
control|)
block|{
if|if
condition|(
name|cd
operator|.
name|getFreeTime
argument_list|()
operator|!=
literal|null
condition|)
name|helper
operator|.
name|getHibSession
argument_list|()
operator|.
name|delete
argument_list|(
name|cd
operator|.
name|getFreeTime
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|CourseRequest
name|cr
range|:
name|cd
operator|.
name|getCourseRequests
argument_list|()
control|)
name|helper
operator|.
name|getHibSession
argument_list|()
operator|.
name|delete
argument_list|(
name|cr
argument_list|)
expr_stmt|;
name|student
operator|.
name|getCourseDemands
argument_list|()
operator|.
name|remove
argument_list|(
name|cd
argument_list|)
expr_stmt|;
name|helper
operator|.
name|getHibSession
argument_list|()
operator|.
name|delete
argument_list|(
name|cd
argument_list|)
expr_stmt|;
block|}
name|helper
operator|.
name|getHibSession
argument_list|()
operator|.
name|saveOrUpdate
argument_list|(
name|student
argument_list|)
expr_stmt|;
name|helper
operator|.
name|getHibSession
argument_list|()
operator|.
name|flush
argument_list|()
expr_stmt|;
return|return
name|course2request
return|;
block|}
block|}
end_class

end_unit

