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
name|basic
package|;
end_package

begin_import
import|import
name|java
operator|.
name|text
operator|.
name|DecimalFormat
import|;
end_import

begin_import
import|import
name|java
operator|.
name|text
operator|.
name|DecimalFormatSymbols
import|;
end_import

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
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Locale
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
name|net
operator|.
name|sf
operator|.
name|cpsolver
operator|.
name|coursett
operator|.
name|model
operator|.
name|TimeLocation
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
name|extension
operator|.
name|DistanceConflict
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
name|extension
operator|.
name|TimeOverlapsCounter
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
name|XConfig
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
name|XEnrollments
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
name|XFreeTimeRequest
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
name|XSection
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
name|model
operator|.
name|XSubpart
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
name|StudentSchedulingAssistantWeights
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|GetInfo
implements|implements
name|OnlineSectioningAction
argument_list|<
name|Map
argument_list|<
name|String
argument_list|,
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
annotation|@
name|Override
specifier|public
name|Map
argument_list|<
name|String
argument_list|,
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
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|info
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|()
decl_stmt|;
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
name|DecimalFormat
name|df
init|=
operator|new
name|DecimalFormat
argument_list|(
literal|"0.00"
argument_list|,
operator|new
name|DecimalFormatSymbols
argument_list|(
name|Locale
operator|.
name|US
argument_list|)
argument_list|)
decl_stmt|;
name|StudentSchedulingAssistantWeights
name|w
init|=
operator|new
name|StudentSchedulingAssistantWeights
argument_list|(
name|server
operator|.
name|getConfig
argument_list|()
argument_list|)
decl_stmt|;
name|DistanceConflict
name|dc
init|=
operator|new
name|DistanceConflict
argument_list|(
name|server
operator|.
name|getDistanceMetric
argument_list|()
argument_list|,
name|server
operator|.
name|getConfig
argument_list|()
argument_list|)
decl_stmt|;
name|TimeOverlapsCounter
name|toc
init|=
operator|new
name|TimeOverlapsCounter
argument_list|(
literal|null
argument_list|,
name|server
operator|.
name|getConfig
argument_list|()
argument_list|)
decl_stmt|;
name|int
name|nrVars
init|=
literal|0
decl_stmt|,
name|assgnVars
init|=
literal|0
decl_stmt|,
name|nrStud
init|=
literal|0
decl_stmt|,
name|compStud
init|=
literal|0
decl_stmt|,
name|dist
init|=
literal|0
decl_stmt|,
name|overlap
init|=
literal|0
decl_stmt|,
name|free
init|=
literal|0
decl_stmt|;
name|double
name|value
init|=
literal|0.0
decl_stmt|;
for|for
control|(
name|XStudentId
name|id
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
name|Student
name|student
init|=
name|convert
argument_list|(
name|id
argument_list|,
name|server
argument_list|)
decl_stmt|;
if|if
condition|(
name|student
operator|==
literal|null
condition|)
continue|continue;
name|boolean
name|complete
init|=
literal|true
decl_stmt|;
for|for
control|(
name|Request
name|request
range|:
name|student
operator|.
name|getRequests
argument_list|()
control|)
block|{
if|if
condition|(
name|request
operator|instanceof
name|FreeTimeRequest
condition|)
continue|continue;
name|Enrollment
name|enrollment
init|=
name|request
operator|.
name|getAssignment
argument_list|()
decl_stmt|;
if|if
condition|(
name|enrollment
operator|!=
literal|null
condition|)
block|{
name|assgnVars
operator|++
expr_stmt|;
name|nrVars
operator|++
expr_stmt|;
name|value
operator|+=
name|w
operator|.
name|getWeight
argument_list|(
name|enrollment
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|student
operator|.
name|canAssign
argument_list|(
name|request
argument_list|)
condition|)
block|{
name|nrVars
operator|++
expr_stmt|;
name|complete
operator|=
literal|false
expr_stmt|;
block|}
block|}
name|nrStud
operator|++
expr_stmt|;
if|if
condition|(
name|complete
condition|)
name|compStud
operator|++
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|student
operator|.
name|getRequests
argument_list|()
operator|.
name|size
argument_list|()
operator|-
literal|1
condition|;
name|i
operator|++
control|)
block|{
name|Request
name|r1
init|=
name|student
operator|.
name|getRequests
argument_list|()
operator|.
name|get
argument_list|(
name|i
argument_list|)
decl_stmt|;
name|Enrollment
name|e1
init|=
operator|(
name|r1
operator|instanceof
name|CourseRequest
condition|?
operator|(
operator|(
name|CourseRequest
operator|)
name|r1
operator|)
operator|.
name|getAssignment
argument_list|()
else|:
literal|null
operator|)
decl_stmt|;
if|if
condition|(
name|e1
operator|==
literal|null
condition|)
continue|continue;
name|dist
operator|+=
name|dc
operator|.
name|nrConflicts
argument_list|(
name|e1
argument_list|)
expr_stmt|;
name|free
operator|+=
name|toc
operator|.
name|nrFreeTimeConflicts
argument_list|(
name|e1
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|j
init|=
name|i
operator|+
literal|1
init|;
name|j
operator|<
name|student
operator|.
name|getRequests
argument_list|()
operator|.
name|size
argument_list|()
condition|;
name|j
operator|++
control|)
block|{
name|Request
name|r2
init|=
name|student
operator|.
name|getRequests
argument_list|()
operator|.
name|get
argument_list|(
name|j
argument_list|)
decl_stmt|;
name|Enrollment
name|e2
init|=
operator|(
name|r2
operator|instanceof
name|CourseRequest
condition|?
operator|(
operator|(
name|CourseRequest
operator|)
name|r2
operator|)
operator|.
name|getAssignment
argument_list|()
else|:
literal|null
operator|)
decl_stmt|;
if|if
condition|(
name|e2
operator|==
literal|null
condition|)
continue|continue;
name|dist
operator|+=
name|dc
operator|.
name|nrConflicts
argument_list|(
name|e1
argument_list|,
name|e2
argument_list|)
expr_stmt|;
name|overlap
operator|+=
name|toc
operator|.
name|nrConflicts
argument_list|(
name|e1
argument_list|,
name|e2
argument_list|)
expr_stmt|;
block|}
block|}
block|}
name|info
operator|.
name|put
argument_list|(
literal|"Assigned variables"
argument_list|,
name|df
operator|.
name|format
argument_list|(
literal|100.0
operator|*
name|assgnVars
operator|/
name|nrVars
argument_list|)
operator|+
literal|"% ("
operator|+
name|assgnVars
operator|+
literal|"/"
operator|+
name|nrVars
operator|+
literal|")"
argument_list|)
expr_stmt|;
name|info
operator|.
name|put
argument_list|(
literal|"Overall solution value"
argument_list|,
name|df
operator|.
name|format
argument_list|(
name|value
argument_list|)
argument_list|)
expr_stmt|;
name|info
operator|.
name|put
argument_list|(
literal|"Students with complete schedule"
argument_list|,
name|df
operator|.
name|format
argument_list|(
literal|100.0
operator|*
name|compStud
operator|/
name|nrStud
argument_list|)
operator|+
literal|"% ("
operator|+
name|compStud
operator|+
literal|"/"
operator|+
name|nrStud
operator|+
literal|")"
argument_list|)
expr_stmt|;
name|info
operator|.
name|put
argument_list|(
literal|"Student distance conflicts"
argument_list|,
name|df
operator|.
name|format
argument_list|(
literal|1.0
operator|*
name|dist
operator|/
name|nrStud
argument_list|)
operator|+
literal|" ("
operator|+
name|dist
operator|+
literal|")"
argument_list|)
expr_stmt|;
name|info
operator|.
name|put
argument_list|(
literal|"Time overlapping conflicts"
argument_list|,
name|df
operator|.
name|format
argument_list|(
name|overlap
operator|/
literal|12.0
operator|/
name|nrStud
argument_list|)
operator|+
literal|"h ("
operator|+
name|overlap
operator|+
literal|")"
argument_list|)
expr_stmt|;
name|info
operator|.
name|put
argument_list|(
literal|"Free time overlapping conflicts"
argument_list|,
name|df
operator|.
name|format
argument_list|(
name|free
operator|/
literal|12.0
operator|/
name|nrStud
argument_list|)
operator|+
literal|"h ("
operator|+
name|free
operator|+
literal|")"
argument_list|)
expr_stmt|;
name|double
name|disbWeight
init|=
literal|0
decl_stmt|;
name|int
name|disbSections
init|=
literal|0
decl_stmt|;
name|int
name|disb10Sections
init|=
literal|0
decl_stmt|;
name|Set
argument_list|<
name|Long
argument_list|>
name|offerings
init|=
operator|new
name|HashSet
argument_list|<
name|Long
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|XCourseId
name|ci
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
name|XOffering
name|offering
init|=
name|server
operator|.
name|getOffering
argument_list|(
name|ci
operator|.
name|getOfferingId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|offering
operator|==
literal|null
condition|)
continue|continue;
if|if
condition|(
name|offerings
operator|.
name|add
argument_list|(
name|offering
operator|.
name|getOfferingId
argument_list|()
argument_list|)
condition|)
block|{
name|XEnrollments
name|enrollments
init|=
name|server
operator|.
name|getEnrollments
argument_list|(
name|offering
operator|.
name|getOfferingId
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|XConfig
name|config
range|:
name|offering
operator|.
name|getConfigs
argument_list|()
control|)
block|{
name|double
name|enrlConf
init|=
name|enrollments
operator|.
name|countEnrollmentsForConfig
argument_list|(
name|config
operator|.
name|getConfigId
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|XSubpart
name|subpart
range|:
name|config
operator|.
name|getSubparts
argument_list|()
control|)
block|{
if|if
condition|(
name|subpart
operator|.
name|getSections
argument_list|()
operator|.
name|size
argument_list|()
operator|<=
literal|1
condition|)
continue|continue;
if|if
condition|(
name|subpart
operator|.
name|getLimit
argument_list|()
operator|>
literal|0
condition|)
block|{
comment|// sections have limits -> desired size is section limit x (total enrollment / total limit)
name|double
name|ratio
init|=
name|enrlConf
operator|/
name|subpart
operator|.
name|getLimit
argument_list|()
decl_stmt|;
for|for
control|(
name|XSection
name|section
range|:
name|subpart
operator|.
name|getSections
argument_list|()
control|)
block|{
name|double
name|enrl
init|=
name|enrollments
operator|.
name|countEnrollmentsForSection
argument_list|(
name|section
operator|.
name|getSectionId
argument_list|()
argument_list|)
decl_stmt|;
name|double
name|desired
init|=
name|ratio
operator|*
name|section
operator|.
name|getLimit
argument_list|()
decl_stmt|;
name|disbWeight
operator|+=
name|Math
operator|.
name|abs
argument_list|(
name|enrl
operator|-
name|desired
argument_list|)
expr_stmt|;
name|disbSections
operator|++
expr_stmt|;
if|if
condition|(
name|Math
operator|.
name|abs
argument_list|(
name|desired
operator|-
name|enrl
argument_list|)
operator|>=
name|Math
operator|.
name|max
argument_list|(
literal|1.0
argument_list|,
literal|0.1
operator|*
name|section
operator|.
name|getLimit
argument_list|()
argument_list|)
condition|)
name|disb10Sections
operator|++
expr_stmt|;
block|}
block|}
else|else
block|{
comment|// unlimited sections -> desired size is total enrollment / number of sections
for|for
control|(
name|XSection
name|section
range|:
name|subpart
operator|.
name|getSections
argument_list|()
control|)
block|{
name|double
name|enrl
init|=
name|enrollments
operator|.
name|countEnrollmentsForSection
argument_list|(
name|section
operator|.
name|getSectionId
argument_list|()
argument_list|)
decl_stmt|;
name|double
name|desired
init|=
name|enrlConf
operator|/
name|subpart
operator|.
name|getSections
argument_list|()
operator|.
name|size
argument_list|()
decl_stmt|;
name|disbWeight
operator|+=
name|Math
operator|.
name|abs
argument_list|(
name|enrl
operator|-
name|desired
argument_list|)
expr_stmt|;
name|disbSections
operator|++
expr_stmt|;
if|if
condition|(
name|Math
operator|.
name|abs
argument_list|(
name|desired
operator|-
name|enrl
argument_list|)
operator|>=
name|Math
operator|.
name|max
argument_list|(
literal|1.0
argument_list|,
literal|0.1
operator|*
name|desired
argument_list|)
condition|)
name|disb10Sections
operator|++
expr_stmt|;
block|}
block|}
block|}
block|}
block|}
if|if
condition|(
name|disbSections
operator|!=
literal|0
condition|)
block|{
name|info
operator|.
name|put
argument_list|(
literal|"Average disbalance"
argument_list|,
name|df
operator|.
name|format
argument_list|(
name|disbWeight
operator|/
name|disbSections
argument_list|)
operator|+
literal|" ("
operator|+
name|df
operator|.
name|format
argument_list|(
name|assgnVars
operator|==
literal|0
condition|?
literal|0.0
else|:
literal|100.0
operator|*
name|disbWeight
operator|/
name|assgnVars
argument_list|)
operator|+
literal|"%)"
argument_list|)
expr_stmt|;
name|info
operator|.
name|put
argument_list|(
literal|"Sections disbalanced by 10% or more"
argument_list|,
name|disb10Sections
operator|+
literal|" ("
operator|+
name|df
operator|.
name|format
argument_list|(
name|disbSections
operator|==
literal|0
condition|?
literal|0.0
else|:
literal|100.0
operator|*
name|disb10Sections
operator|/
name|disbSections
argument_list|)
operator|+
literal|"%)"
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
return|return
name|info
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
literal|"info"
return|;
block|}
specifier|public
specifier|static
name|Student
name|convert
parameter_list|(
name|XStudentId
name|id
parameter_list|,
name|OnlineSectioningServer
name|server
parameter_list|)
block|{
name|XStudent
name|student
init|=
operator|(
name|id
operator|instanceof
name|XStudent
condition|?
operator|(
name|XStudent
operator|)
name|id
else|:
name|server
operator|.
name|getStudent
argument_list|(
name|id
operator|.
name|getStudentId
argument_list|()
argument_list|)
operator|)
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
name|Student
name|clonnedStudent
init|=
operator|new
name|Student
argument_list|(
name|student
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
operator|instanceof
name|XFreeTimeRequest
condition|)
block|{
name|XFreeTimeRequest
name|ft
init|=
operator|(
name|XFreeTimeRequest
operator|)
name|r
decl_stmt|;
name|FreeTimeRequest
name|ftr
init|=
operator|new
name|FreeTimeRequest
argument_list|(
name|r
operator|.
name|getRequestId
argument_list|()
argument_list|,
name|r
operator|.
name|getPriority
argument_list|()
argument_list|,
name|r
operator|.
name|isAlternative
argument_list|()
argument_list|,
name|clonnedStudent
argument_list|,
operator|new
name|TimeLocation
argument_list|(
name|ft
operator|.
name|getTime
argument_list|()
operator|.
name|getDays
argument_list|()
argument_list|,
name|ft
operator|.
name|getTime
argument_list|()
operator|.
name|getSlot
argument_list|()
argument_list|,
name|ft
operator|.
name|getTime
argument_list|()
operator|.
name|getLength
argument_list|()
argument_list|,
literal|0
argument_list|,
literal|0.0
argument_list|,
operator|-
literal|1l
argument_list|,
literal|"Free Time"
argument_list|,
name|server
operator|.
name|getAcademicSession
argument_list|()
operator|.
name|getFreeTimePattern
argument_list|()
argument_list|,
literal|0
argument_list|)
argument_list|)
decl_stmt|;
name|ftr
operator|.
name|assign
argument_list|(
literal|0
argument_list|,
name|ftr
operator|.
name|createEnrollment
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|XCourseRequest
name|cr
init|=
operator|(
name|XCourseRequest
operator|)
name|r
decl_stmt|;
name|List
argument_list|<
name|Course
argument_list|>
name|courses
init|=
operator|new
name|ArrayList
argument_list|<
name|Course
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|XCourseId
name|c
range|:
name|cr
operator|.
name|getCourseIds
argument_list|()
control|)
block|{
name|XOffering
name|offering
init|=
name|server
operator|.
name|getOffering
argument_list|(
name|c
operator|.
name|getOfferingId
argument_list|()
argument_list|)
decl_stmt|;
name|courses
operator|.
name|add
argument_list|(
name|offering
operator|.
name|toCourse
argument_list|(
name|c
operator|.
name|getCourseId
argument_list|()
argument_list|,
name|student
argument_list|,
name|server
operator|.
name|getExpectations
argument_list|(
name|c
operator|.
name|getOfferingId
argument_list|()
argument_list|)
argument_list|,
name|offering
operator|.
name|getDistributions
argument_list|()
argument_list|,
name|server
operator|.
name|getEnrollments
argument_list|(
name|c
operator|.
name|getOfferingId
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|CourseRequest
name|clonnedRequest
init|=
operator|new
name|CourseRequest
argument_list|(
name|r
operator|.
name|getRequestId
argument_list|()
argument_list|,
name|r
operator|.
name|getPriority
argument_list|()
argument_list|,
name|r
operator|.
name|isAlternative
argument_list|()
argument_list|,
name|clonnedStudent
argument_list|,
name|courses
argument_list|,
name|cr
operator|.
name|isWaitlist
argument_list|()
argument_list|,
name|cr
operator|.
name|getTimeStamp
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
name|cr
operator|.
name|getTimeStamp
argument_list|()
operator|.
name|getTime
argument_list|()
argument_list|)
decl_stmt|;
name|XEnrollment
name|enrollment
init|=
name|cr
operator|.
name|getEnrollment
argument_list|()
decl_stmt|;
if|if
condition|(
name|enrollment
operator|!=
literal|null
condition|)
block|{
name|Config
name|config
init|=
literal|null
decl_stmt|;
name|Set
argument_list|<
name|Section
argument_list|>
name|assignments
init|=
operator|new
name|HashSet
argument_list|<
name|Section
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Course
name|c
range|:
name|clonnedRequest
operator|.
name|getCourses
argument_list|()
control|)
block|{
if|if
condition|(
name|enrollment
operator|.
name|getCourseId
argument_list|()
operator|.
name|equals
argument_list|(
name|c
operator|.
name|getId
argument_list|()
argument_list|)
condition|)
for|for
control|(
name|Config
name|g
range|:
name|c
operator|.
name|getOffering
argument_list|()
operator|.
name|getConfigs
argument_list|()
control|)
block|{
if|if
condition|(
name|enrollment
operator|.
name|getConfigId
argument_list|()
operator|.
name|equals
argument_list|(
name|g
operator|.
name|getId
argument_list|()
argument_list|)
condition|)
block|{
name|config
operator|=
name|g
expr_stmt|;
for|for
control|(
name|Subpart
name|s
range|:
name|g
operator|.
name|getSubparts
argument_list|()
control|)
for|for
control|(
name|Section
name|x
range|:
name|s
operator|.
name|getSections
argument_list|()
control|)
if|if
condition|(
name|enrollment
operator|.
name|getSectionIds
argument_list|()
operator|.
name|contains
argument_list|(
name|x
operator|.
name|getId
argument_list|()
argument_list|)
condition|)
name|assignments
operator|.
name|add
argument_list|(
name|x
argument_list|)
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
name|config
operator|!=
literal|null
condition|)
name|clonnedRequest
operator|.
name|assign
argument_list|(
literal|0
argument_list|,
operator|new
name|Enrollment
argument_list|(
name|clonnedRequest
argument_list|,
literal|0
argument_list|,
name|config
argument_list|,
name|assignments
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
return|return
name|clonnedStudent
return|;
block|}
block|}
end_class

end_unit

