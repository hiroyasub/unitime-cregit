begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 (University Timetabling Application)  * Copyright (C) 2008 - 2010, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
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
name|Date
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
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|Log
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|LogFactory
import|;
end_import

begin_import
import|import
name|org
operator|.
name|hibernate
operator|.
name|Transaction
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
name|Class_
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
name|SectioningInfo
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
name|Session
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
name|StudentSectioningQueue
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
name|WaitList
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
name|SessionDAO
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
name|solver
operator|.
name|Solver
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
name|StudentSectioningSaver
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
name|Config
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
name|Enrollment
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
name|Offering
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
name|Section
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
name|Subpart
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|BatchStudentSectioningSaver
extends|extends
name|StudentSectioningSaver
block|{
specifier|private
specifier|static
name|Log
name|sLog
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|BatchStudentSectioningSaver
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|boolean
name|iIncludeCourseDemands
init|=
literal|true
decl_stmt|;
specifier|private
name|boolean
name|iIncludeLastLikeStudents
init|=
literal|true
decl_stmt|;
specifier|private
name|String
name|iInitiative
init|=
literal|null
decl_stmt|;
specifier|private
name|String
name|iTerm
init|=
literal|null
decl_stmt|;
specifier|private
name|String
name|iYear
init|=
literal|null
decl_stmt|;
specifier|private
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Student
argument_list|>
name|iStudents
init|=
literal|null
decl_stmt|;
specifier|private
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|CourseOffering
argument_list|>
name|iCourses
init|=
literal|null
decl_stmt|;
specifier|private
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|Class_
argument_list|>
name|iClasses
init|=
literal|null
decl_stmt|;
specifier|private
name|Hashtable
argument_list|<
name|String
argument_list|,
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|CourseRequest
argument_list|>
name|iRequests
init|=
literal|null
decl_stmt|;
specifier|private
name|int
name|iInsert
init|=
literal|0
decl_stmt|;
specifier|public
name|BatchStudentSectioningSaver
parameter_list|(
name|Solver
name|solver
parameter_list|)
block|{
name|super
argument_list|(
name|solver
argument_list|)
expr_stmt|;
name|iIncludeCourseDemands
operator|=
name|solver
operator|.
name|getProperties
argument_list|()
operator|.
name|getPropertyBoolean
argument_list|(
literal|"Load.IncludeCourseDemands"
argument_list|,
name|iIncludeCourseDemands
argument_list|)
expr_stmt|;
name|iIncludeLastLikeStudents
operator|=
name|solver
operator|.
name|getProperties
argument_list|()
operator|.
name|getPropertyBoolean
argument_list|(
literal|"Load.IncludeLastLikeStudents"
argument_list|,
name|iIncludeLastLikeStudents
argument_list|)
expr_stmt|;
name|iInitiative
operator|=
name|solver
operator|.
name|getProperties
argument_list|()
operator|.
name|getProperty
argument_list|(
literal|"Data.Initiative"
argument_list|)
expr_stmt|;
name|iYear
operator|=
name|solver
operator|.
name|getProperties
argument_list|()
operator|.
name|getProperty
argument_list|(
literal|"Data.Year"
argument_list|)
expr_stmt|;
name|iTerm
operator|=
name|solver
operator|.
name|getProperties
argument_list|()
operator|.
name|getProperty
argument_list|(
literal|"Data.Term"
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|save
parameter_list|()
throws|throws
name|Exception
block|{
name|Session
name|session
init|=
name|Session
operator|.
name|getSessionUsingInitiativeYearTerm
argument_list|(
name|iInitiative
argument_list|,
name|iYear
argument_list|,
name|iTerm
argument_list|)
decl_stmt|;
if|if
condition|(
name|session
operator|==
literal|null
condition|)
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Session "
operator|+
name|iInitiative
operator|+
literal|" "
operator|+
name|iTerm
operator|+
name|iYear
operator|+
literal|" not found!"
argument_list|)
throw|;
name|save
argument_list|(
name|session
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|flushIfNeeded
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|)
block|{
name|iInsert
operator|++
expr_stmt|;
if|if
condition|(
operator|(
name|iInsert
operator|%
literal|1000
operator|)
operator|==
literal|0
condition|)
block|{
name|hibSession
operator|.
name|flush
argument_list|()
expr_stmt|;
name|hibSession
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
block|}
specifier|public
name|void
name|flush
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|)
block|{
name|hibSession
operator|.
name|flush
argument_list|()
expr_stmt|;
name|hibSession
operator|.
name|clear
argument_list|()
expr_stmt|;
name|iInsert
operator|=
literal|0
expr_stmt|;
block|}
specifier|public
name|void
name|saveStudent
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|Student
name|student
parameter_list|)
block|{
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Student
name|s
init|=
name|iStudents
operator|.
name|get
argument_list|(
name|student
operator|.
name|getId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|s
operator|==
literal|null
condition|)
block|{
name|sLog
operator|.
name|warn
argument_list|(
literal|"Student "
operator|+
name|student
operator|.
name|getId
argument_list|()
operator|+
literal|" not found."
argument_list|)
expr_stmt|;
return|return;
block|}
for|for
control|(
name|Iterator
name|i
init|=
name|s
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
name|sce
init|=
operator|(
name|StudentClassEnrollment
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|hibSession
operator|.
name|delete
argument_list|(
name|sce
argument_list|)
expr_stmt|;
name|i
operator|.
name|remove
argument_list|()
expr_stmt|;
block|}
for|for
control|(
name|Iterator
name|i
init|=
name|s
operator|.
name|getWaitlists
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
name|WaitList
name|wl
init|=
operator|(
name|WaitList
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|hibSession
operator|.
name|delete
argument_list|(
name|wl
argument_list|)
expr_stmt|;
name|i
operator|.
name|remove
argument_list|()
expr_stmt|;
block|}
for|for
control|(
name|Iterator
name|e
init|=
name|student
operator|.
name|getRequests
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|e
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Request
name|request
init|=
operator|(
name|Request
operator|)
name|e
operator|.
name|next
argument_list|()
decl_stmt|;
name|Enrollment
name|enrollment
init|=
operator|(
name|Enrollment
operator|)
name|request
operator|.
name|getAssignment
argument_list|()
decl_stmt|;
if|if
condition|(
name|request
operator|instanceof
name|CourseRequest
condition|)
block|{
name|CourseRequest
name|courseRequest
init|=
operator|(
name|CourseRequest
operator|)
name|request
decl_stmt|;
if|if
condition|(
name|enrollment
operator|==
literal|null
condition|)
block|{
if|if
condition|(
name|courseRequest
operator|.
name|isWaitlist
argument_list|()
operator|&&
name|student
operator|.
name|canAssign
argument_list|(
name|courseRequest
argument_list|)
condition|)
block|{
name|WaitList
name|wl
init|=
operator|new
name|WaitList
argument_list|()
decl_stmt|;
name|wl
operator|.
name|setStudent
argument_list|(
name|s
argument_list|)
expr_stmt|;
name|wl
operator|.
name|setCourseOffering
argument_list|(
name|iCourses
operator|.
name|get
argument_list|(
operator|(
operator|(
name|Course
operator|)
name|courseRequest
operator|.
name|getCourses
argument_list|()
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|)
operator|.
name|getId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|wl
operator|.
name|setTimestamp
argument_list|(
operator|new
name|Date
argument_list|()
argument_list|)
expr_stmt|;
name|wl
operator|.
name|setType
argument_list|(
operator|new
name|Integer
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|save
argument_list|(
name|wl
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
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
init|=
name|iRequests
operator|.
name|get
argument_list|(
name|request
operator|.
name|getId
argument_list|()
operator|+
literal|":"
operator|+
name|enrollment
operator|.
name|getOffering
argument_list|()
operator|.
name|getId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|cr
operator|==
literal|null
condition|)
continue|continue;
for|for
control|(
name|Iterator
name|i
init|=
name|enrollment
operator|.
name|getAssignments
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
name|Section
name|section
init|=
operator|(
name|Section
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|StudentClassEnrollment
name|sce
init|=
operator|new
name|StudentClassEnrollment
argument_list|()
decl_stmt|;
name|sce
operator|.
name|setStudent
argument_list|(
name|s
argument_list|)
expr_stmt|;
name|sce
operator|.
name|setClazz
argument_list|(
name|iClasses
operator|.
name|get
argument_list|(
name|section
operator|.
name|getId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|sce
operator|.
name|setCourseRequest
argument_list|(
name|cr
argument_list|)
expr_stmt|;
name|sce
operator|.
name|setCourseOffering
argument_list|(
name|cr
operator|.
name|getCourseOffering
argument_list|()
argument_list|)
expr_stmt|;
name|sce
operator|.
name|setTimestamp
argument_list|(
operator|new
name|Date
argument_list|()
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|save
argument_list|(
name|sce
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
block|}
specifier|public
name|void
name|save
parameter_list|(
name|Session
name|session
parameter_list|)
block|{
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
operator|new
name|SessionDAO
argument_list|()
operator|.
name|getSession
argument_list|()
decl_stmt|;
name|Transaction
name|tx
init|=
name|hibSession
operator|.
name|beginTransaction
argument_list|()
decl_stmt|;
try|try
block|{
name|iClasses
operator|=
operator|new
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|Class_
argument_list|>
argument_list|()
expr_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|Class_
operator|.
name|findAll
argument_list|(
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|)
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
name|Class_
name|clazz
init|=
operator|(
name|Class_
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|iClasses
operator|.
name|put
argument_list|(
name|clazz
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|clazz
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|iIncludeCourseDemands
condition|)
block|{
name|iStudents
operator|=
operator|new
name|Hashtable
argument_list|()
expr_stmt|;
name|iCourses
operator|=
operator|new
name|Hashtable
argument_list|()
expr_stmt|;
name|iRequests
operator|=
operator|new
name|Hashtable
argument_list|()
expr_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|CourseDemand
operator|.
name|findAll
argument_list|(
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|)
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
name|demand
init|=
operator|(
name|CourseDemand
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|iStudents
operator|.
name|put
argument_list|(
name|demand
operator|.
name|getStudent
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|demand
operator|.
name|getStudent
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
name|j
init|=
name|demand
operator|.
name|getCourseRequests
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|j
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|CourseRequest
name|request
init|=
operator|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|CourseRequest
operator|)
name|j
operator|.
name|next
argument_list|()
decl_stmt|;
name|iRequests
operator|.
name|put
argument_list|(
name|demand
operator|.
name|getUniqueId
argument_list|()
operator|+
literal|":"
operator|+
name|request
operator|.
name|getCourseOffering
argument_list|()
operator|.
name|getInstructionalOffering
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|request
argument_list|)
expr_stmt|;
name|iCourses
operator|.
name|put
argument_list|(
name|request
operator|.
name|getCourseOffering
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|request
operator|.
name|getCourseOffering
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
for|for
control|(
name|Iterator
name|e
init|=
name|getModel
argument_list|()
operator|.
name|getStudents
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|e
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Student
name|student
init|=
operator|(
name|Student
operator|)
name|e
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|student
operator|.
name|isDummy
argument_list|()
condition|)
continue|continue;
name|saveStudent
argument_list|(
name|hibSession
argument_list|,
name|student
argument_list|)
expr_stmt|;
name|flushIfNeeded
argument_list|(
name|hibSession
argument_list|)
expr_stmt|;
block|}
name|flush
argument_list|(
name|hibSession
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|iIncludeLastLikeStudents
condition|)
block|{
name|getModel
argument_list|()
operator|.
name|computeOnlineSectioningInfos
argument_list|()
expr_stmt|;
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|SectioningInfo
argument_list|>
name|infoTable
init|=
operator|new
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|SectioningInfo
argument_list|>
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|SectioningInfo
argument_list|>
name|infos
init|=
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"select i from SectioningInfo i where i.clazz.schedulingSubpart.instrOfferingConfig.instructionalOffering.session.uniqueId = :sessionId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|list
argument_list|()
decl_stmt|;
for|for
control|(
name|SectioningInfo
name|info
range|:
name|infos
control|)
name|infoTable
operator|.
name|put
argument_list|(
name|info
operator|.
name|getClazz
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|info
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
name|e
init|=
name|getModel
argument_list|()
operator|.
name|getOfferings
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|e
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Offering
name|offering
init|=
operator|(
name|Offering
operator|)
name|e
operator|.
name|next
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|f
init|=
name|offering
operator|.
name|getConfigs
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|f
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Config
name|config
init|=
operator|(
name|Config
operator|)
name|f
operator|.
name|next
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|g
init|=
name|config
operator|.
name|getSubparts
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|g
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Subpart
name|subpart
init|=
operator|(
name|Subpart
operator|)
name|g
operator|.
name|next
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|h
init|=
name|subpart
operator|.
name|getSections
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|h
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Section
name|section
init|=
operator|(
name|Section
operator|)
name|h
operator|.
name|next
argument_list|()
decl_stmt|;
name|Class_
name|clazz
init|=
name|iClasses
operator|.
name|get
argument_list|(
name|section
operator|.
name|getId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|clazz
operator|==
literal|null
condition|)
continue|continue;
name|SectioningInfo
name|info
init|=
name|infoTable
operator|.
name|get
argument_list|(
name|section
operator|.
name|getId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|info
operator|==
literal|null
condition|)
block|{
name|info
operator|=
operator|new
name|SectioningInfo
argument_list|()
expr_stmt|;
name|info
operator|.
name|setClazz
argument_list|(
name|clazz
argument_list|)
expr_stmt|;
block|}
name|sLog
operator|.
name|debug
argument_list|(
literal|"  -- "
operator|+
name|info
operator|.
name|getClazz
argument_list|()
operator|.
name|getClassLabel
argument_list|()
operator|+
literal|" expects "
operator|+
name|info
operator|.
name|getNbrExpectedStudents
argument_list|()
operator|+
literal|", holds "
operator|+
name|info
operator|.
name|getNbrHoldingStudents
argument_list|()
operator|+
literal|" of "
operator|+
name|section
operator|.
name|getLimit
argument_list|()
argument_list|)
expr_stmt|;
name|info
operator|.
name|setNbrExpectedStudents
argument_list|(
name|section
operator|.
name|getSpaceExpected
argument_list|()
argument_list|)
expr_stmt|;
name|info
operator|.
name|setNbrHoldingStudents
argument_list|(
name|section
operator|.
name|getSpaceHeld
argument_list|()
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|info
argument_list|)
expr_stmt|;
name|flushIfNeeded
argument_list|(
name|hibSession
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
block|}
comment|// Update class enrollments
for|for
control|(
name|Iterator
name|e
init|=
name|getModel
argument_list|()
operator|.
name|getOfferings
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|e
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Offering
name|offering
init|=
operator|(
name|Offering
operator|)
name|e
operator|.
name|next
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|f
init|=
name|offering
operator|.
name|getConfigs
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|f
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Config
name|config
init|=
operator|(
name|Config
operator|)
name|f
operator|.
name|next
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|g
init|=
name|config
operator|.
name|getSubparts
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|g
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Subpart
name|subpart
init|=
operator|(
name|Subpart
operator|)
name|g
operator|.
name|next
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|h
init|=
name|subpart
operator|.
name|getSections
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|h
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Section
name|section
init|=
operator|(
name|Section
operator|)
name|h
operator|.
name|next
argument_list|()
decl_stmt|;
name|Class_
name|clazz
init|=
name|iClasses
operator|.
name|get
argument_list|(
name|section
operator|.
name|getId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|clazz
operator|==
literal|null
condition|)
continue|continue;
name|int
name|enrl
init|=
literal|0
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|section
operator|.
name|getEnrollments
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
name|Enrollment
name|en
init|=
operator|(
name|Enrollment
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|en
operator|.
name|getStudent
argument_list|()
operator|.
name|isDummy
argument_list|()
condition|)
name|enrl
operator|++
expr_stmt|;
block|}
name|clazz
operator|.
name|setEnrollment
argument_list|(
name|enrl
argument_list|)
expr_stmt|;
name|sLog
operator|.
name|debug
argument_list|(
literal|"  -- "
operator|+
name|clazz
operator|.
name|getClassLabel
argument_list|()
operator|+
literal|" has an enrollment of "
operator|+
name|enrl
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|clazz
argument_list|)
expr_stmt|;
name|flushIfNeeded
argument_list|(
name|hibSession
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
name|StudentSectioningQueue
operator|.
name|allStudentsChanged
argument_list|(
name|hibSession
argument_list|,
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|tx
operator|.
name|commit
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|tx
operator|.
name|rollback
argument_list|()
expr_stmt|;
throw|throw
operator|new
name|RuntimeException
argument_list|(
name|e
argument_list|)
throw|;
block|}
finally|finally
block|{
name|hibSession
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

