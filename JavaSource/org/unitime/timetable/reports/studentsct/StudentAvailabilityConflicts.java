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
name|reports
operator|.
name|studentsct
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
name|Comparator
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
name|org
operator|.
name|cpsolver
operator|.
name|ifs
operator|.
name|assignment
operator|.
name|Assignment
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
name|util
operator|.
name|CSVFile
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
name|util
operator|.
name|DataProperties
import|;
end_import

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|studentsct
operator|.
name|StudentSectioningModel
import|;
end_import

begin_import
import|import
name|org
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
name|org
operator|.
name|cpsolver
operator|.
name|studentsct
operator|.
name|model
operator|.
name|AreaClassificationMajor
import|;
end_import

begin_import
import|import
name|org
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
name|org
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
name|org
operator|.
name|cpsolver
operator|.
name|studentsct
operator|.
name|model
operator|.
name|Instructor
import|;
end_import

begin_import
import|import
name|org
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
name|org
operator|.
name|cpsolver
operator|.
name|studentsct
operator|.
name|model
operator|.
name|SctAssignment
import|;
end_import

begin_import
import|import
name|org
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
name|org
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
name|cpsolver
operator|.
name|studentsct
operator|.
name|model
operator|.
name|StudentGroup
import|;
end_import

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|studentsct
operator|.
name|model
operator|.
name|Unavailability
import|;
end_import

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|studentsct
operator|.
name|report
operator|.
name|StudentSectioningReport
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
name|util
operator|.
name|Constants
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|StudentAvailabilityConflicts
implements|implements
name|StudentSectioningReport
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
specifier|private
specifier|static
name|DecimalFormat
name|sDF1
init|=
operator|new
name|DecimalFormat
argument_list|(
literal|"0.####"
argument_list|)
decl_stmt|;
specifier|private
name|StudentSectioningModel
name|iModel
init|=
literal|null
decl_stmt|;
specifier|private
name|TimeOverlapsCounter
name|iTOC
init|=
literal|null
decl_stmt|;
specifier|public
name|StudentAvailabilityConflicts
parameter_list|(
name|StudentSectioningModel
name|model
parameter_list|)
block|{
name|iModel
operator|=
name|model
expr_stmt|;
name|iTOC
operator|=
name|model
operator|.
name|getTimeOverlaps
argument_list|()
expr_stmt|;
if|if
condition|(
name|iTOC
operator|==
literal|null
condition|)
block|{
name|iTOC
operator|=
operator|new
name|TimeOverlapsCounter
argument_list|(
literal|null
argument_list|,
name|model
operator|.
name|getProperties
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|StudentSectioningModel
name|getModel
parameter_list|()
block|{
return|return
name|iModel
return|;
block|}
specifier|public
name|boolean
name|shareHoursIgnoreBreakTime
parameter_list|(
name|TimeLocation
name|t1
parameter_list|,
name|TimeLocation
name|t2
parameter_list|)
block|{
name|int
name|s1
init|=
name|t1
operator|.
name|getStartSlot
argument_list|()
operator|*
name|Constants
operator|.
name|SLOT_LENGTH_MIN
operator|+
name|Constants
operator|.
name|FIRST_SLOT_TIME_MIN
decl_stmt|;
name|int
name|e1
init|=
operator|(
name|t1
operator|.
name|getStartSlot
argument_list|()
operator|+
name|t1
operator|.
name|getLength
argument_list|()
operator|)
operator|*
name|Constants
operator|.
name|SLOT_LENGTH_MIN
operator|+
name|Constants
operator|.
name|FIRST_SLOT_TIME_MIN
operator|-
name|t1
operator|.
name|getBreakTime
argument_list|()
decl_stmt|;
name|int
name|s2
init|=
name|t2
operator|.
name|getStartSlot
argument_list|()
operator|*
name|Constants
operator|.
name|SLOT_LENGTH_MIN
operator|+
name|Constants
operator|.
name|FIRST_SLOT_TIME_MIN
decl_stmt|;
name|int
name|e2
init|=
operator|(
name|t2
operator|.
name|getStartSlot
argument_list|()
operator|+
name|t2
operator|.
name|getLength
argument_list|()
operator|)
operator|*
name|Constants
operator|.
name|SLOT_LENGTH_MIN
operator|+
name|Constants
operator|.
name|FIRST_SLOT_TIME_MIN
operator|-
name|t2
operator|.
name|getBreakTime
argument_list|()
decl_stmt|;
return|return
name|e1
operator|>
name|s2
operator|&&
name|e2
operator|>
name|s1
return|;
block|}
specifier|public
name|boolean
name|inConflict
parameter_list|(
name|SctAssignment
name|a1
parameter_list|,
name|SctAssignment
name|a2
parameter_list|,
name|boolean
name|ignoreBreakTimeConflicts
parameter_list|)
block|{
if|if
condition|(
name|a1
operator|.
name|getTime
argument_list|()
operator|==
literal|null
operator|||
name|a2
operator|.
name|getTime
argument_list|()
operator|==
literal|null
condition|)
return|return
literal|false
return|;
if|if
condition|(
name|ignoreBreakTimeConflicts
condition|)
block|{
name|TimeLocation
name|t1
init|=
name|a1
operator|.
name|getTime
argument_list|()
decl_stmt|;
name|TimeLocation
name|t2
init|=
name|a2
operator|.
name|getTime
argument_list|()
decl_stmt|;
return|return
name|t1
operator|.
name|shareDays
argument_list|(
name|t2
argument_list|)
operator|&&
name|shareHoursIgnoreBreakTime
argument_list|(
name|t1
argument_list|,
name|t2
argument_list|)
operator|&&
name|t1
operator|.
name|shareWeeks
argument_list|(
name|t2
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|a1
operator|.
name|getTime
argument_list|()
operator|.
name|hasIntersection
argument_list|(
name|a2
operator|.
name|getTime
argument_list|()
argument_list|)
return|;
block|}
block|}
specifier|public
name|int
name|nrSharedHoursIgnoreBreakTime
parameter_list|(
name|TimeLocation
name|t1
parameter_list|,
name|TimeLocation
name|t2
parameter_list|)
block|{
name|int
name|s1
init|=
name|t1
operator|.
name|getStartSlot
argument_list|()
operator|*
name|Constants
operator|.
name|SLOT_LENGTH_MIN
operator|+
name|Constants
operator|.
name|FIRST_SLOT_TIME_MIN
decl_stmt|;
name|int
name|e1
init|=
operator|(
name|t1
operator|.
name|getStartSlot
argument_list|()
operator|+
name|t1
operator|.
name|getLength
argument_list|()
operator|)
operator|*
name|Constants
operator|.
name|SLOT_LENGTH_MIN
operator|+
name|Constants
operator|.
name|FIRST_SLOT_TIME_MIN
operator|-
name|t1
operator|.
name|getBreakTime
argument_list|()
decl_stmt|;
name|int
name|s2
init|=
name|t2
operator|.
name|getStartSlot
argument_list|()
operator|*
name|Constants
operator|.
name|SLOT_LENGTH_MIN
operator|+
name|Constants
operator|.
name|FIRST_SLOT_TIME_MIN
decl_stmt|;
name|int
name|e2
init|=
operator|(
name|t2
operator|.
name|getStartSlot
argument_list|()
operator|+
name|t2
operator|.
name|getLength
argument_list|()
operator|)
operator|*
name|Constants
operator|.
name|SLOT_LENGTH_MIN
operator|+
name|Constants
operator|.
name|FIRST_SLOT_TIME_MIN
operator|-
name|t2
operator|.
name|getBreakTime
argument_list|()
decl_stmt|;
name|int
name|end
init|=
name|Math
operator|.
name|min
argument_list|(
name|e1
argument_list|,
name|e2
argument_list|)
decl_stmt|;
name|int
name|start
init|=
name|Math
operator|.
name|max
argument_list|(
name|s1
argument_list|,
name|s2
argument_list|)
decl_stmt|;
return|return
operator|(
name|end
operator|<
name|start
condition|?
literal|0
else|:
name|end
operator|-
name|start
operator|)
return|;
block|}
specifier|public
name|int
name|share
parameter_list|(
name|SctAssignment
name|a1
parameter_list|,
name|SctAssignment
name|a2
parameter_list|,
name|boolean
name|ignoreBreakTimeConflicts
parameter_list|)
block|{
if|if
condition|(
operator|!
name|inConflict
argument_list|(
name|a1
argument_list|,
name|a2
argument_list|,
name|ignoreBreakTimeConflicts
argument_list|)
condition|)
return|return
literal|0
return|;
if|if
condition|(
name|ignoreBreakTimeConflicts
condition|)
block|{
return|return
name|a1
operator|.
name|getTime
argument_list|()
operator|.
name|nrSharedDays
argument_list|(
name|a2
operator|.
name|getTime
argument_list|()
argument_list|)
operator|*
name|nrSharedHoursIgnoreBreakTime
argument_list|(
name|a1
operator|.
name|getTime
argument_list|()
argument_list|,
name|a2
operator|.
name|getTime
argument_list|()
argument_list|)
return|;
block|}
else|else
block|{
return|return
literal|5
operator|*
name|a1
operator|.
name|getTime
argument_list|()
operator|.
name|nrSharedDays
argument_list|(
name|a2
operator|.
name|getTime
argument_list|()
argument_list|)
operator|*
name|a1
operator|.
name|getTime
argument_list|()
operator|.
name|nrSharedHours
argument_list|(
name|a2
operator|.
name|getTime
argument_list|()
argument_list|)
return|;
block|}
block|}
specifier|protected
name|String
name|curriculum
parameter_list|(
name|Student
name|student
parameter_list|)
block|{
name|String
name|curriculum
init|=
literal|""
decl_stmt|;
for|for
control|(
name|AreaClassificationMajor
name|acm
range|:
name|student
operator|.
name|getAreaClassificationMajors
argument_list|()
control|)
name|curriculum
operator|+=
operator|(
name|curriculum
operator|.
name|isEmpty
argument_list|()
condition|?
literal|""
else|:
literal|", "
operator|)
operator|+
name|acm
operator|.
name|toString
argument_list|()
expr_stmt|;
return|return
name|curriculum
return|;
block|}
specifier|protected
name|String
name|group
parameter_list|(
name|Student
name|student
parameter_list|)
block|{
name|String
name|group
init|=
literal|""
decl_stmt|;
name|Set
argument_list|<
name|String
argument_list|>
name|groups
init|=
operator|new
name|TreeSet
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|StudentGroup
name|g
range|:
name|student
operator|.
name|getGroups
argument_list|()
control|)
name|groups
operator|.
name|add
argument_list|(
name|g
operator|.
name|getReference
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|String
name|g
range|:
name|groups
control|)
name|group
operator|+=
operator|(
name|group
operator|.
name|isEmpty
argument_list|()
condition|?
literal|""
else|:
literal|", "
operator|)
operator|+
name|g
expr_stmt|;
return|return
name|group
return|;
block|}
specifier|protected
name|String
name|advisor
parameter_list|(
name|Student
name|student
parameter_list|)
block|{
name|String
name|advisors
init|=
literal|""
decl_stmt|;
for|for
control|(
name|Instructor
name|instructor
range|:
name|student
operator|.
name|getAdvisors
argument_list|()
control|)
name|advisors
operator|+=
operator|(
name|advisors
operator|.
name|isEmpty
argument_list|()
condition|?
literal|""
else|:
literal|", "
operator|)
operator|+
name|instructor
operator|.
name|getName
argument_list|()
expr_stmt|;
return|return
name|advisors
return|;
block|}
specifier|public
name|CSVFile
name|createTable
parameter_list|(
specifier|final
name|Assignment
argument_list|<
name|Request
argument_list|,
name|Enrollment
argument_list|>
name|assignment
parameter_list|,
name|boolean
name|includeLastLikeStudents
parameter_list|,
name|boolean
name|includeRealStudents
parameter_list|,
specifier|final
name|boolean
name|useAmPm
parameter_list|,
name|boolean
name|includeAllowedOverlaps
parameter_list|,
name|boolean
name|ignoreBreakTimeConflicts
parameter_list|)
block|{
name|CSVFile
name|csv
init|=
operator|new
name|CSVFile
argument_list|()
decl_stmt|;
if|if
condition|(
name|includeAllowedOverlaps
condition|)
block|{
name|csv
operator|.
name|setHeader
argument_list|(
operator|new
name|CSVFile
operator|.
name|CSVField
index|[]
block|{
operator|new
name|CSVFile
operator|.
name|CSVField
argument_list|(
name|MSG
operator|.
name|reportStudentId
argument_list|()
argument_list|)
block|,
operator|new
name|CSVFile
operator|.
name|CSVField
argument_list|(
name|MSG
operator|.
name|reportStudentName
argument_list|()
argument_list|)
block|,
operator|new
name|CSVFile
operator|.
name|CSVField
argument_list|(
name|MSG
operator|.
name|reportStudentEmail
argument_list|()
argument_list|)
block|,
operator|new
name|CSVFile
operator|.
name|CSVField
argument_list|(
name|MSG
operator|.
name|reportStudentCurriculum
argument_list|()
argument_list|)
block|,
operator|new
name|CSVFile
operator|.
name|CSVField
argument_list|(
name|MSG
operator|.
name|reportStudentGroup
argument_list|()
argument_list|)
block|,
operator|new
name|CSVFile
operator|.
name|CSVField
argument_list|(
name|MSG
operator|.
name|reportStudentAdvisor
argument_list|()
argument_list|)
block|,
operator|new
name|CSVFile
operator|.
name|CSVField
argument_list|(
name|MSG
operator|.
name|reportAllowedOverlap
argument_list|()
argument_list|)
block|,
operator|new
name|CSVFile
operator|.
name|CSVField
argument_list|(
name|MSG
operator|.
name|reportCourse
argument_list|()
argument_list|)
block|,
operator|new
name|CSVFile
operator|.
name|CSVField
argument_list|(
name|MSG
operator|.
name|reportClass
argument_list|()
argument_list|)
block|,
operator|new
name|CSVFile
operator|.
name|CSVField
argument_list|(
name|MSG
operator|.
name|reportMeetingTime
argument_list|()
argument_list|)
block|,
operator|new
name|CSVFile
operator|.
name|CSVField
argument_list|(
name|MSG
operator|.
name|reportSubpartOverlap
argument_list|()
argument_list|)
block|,
operator|new
name|CSVFile
operator|.
name|CSVField
argument_list|(
name|MSG
operator|.
name|reportTimeOverride
argument_list|()
argument_list|)
block|,
operator|new
name|CSVFile
operator|.
name|CSVField
argument_list|(
name|MSG
operator|.
name|reportConflictingAssignment
argument_list|()
argument_list|)
block|,
operator|new
name|CSVFile
operator|.
name|CSVField
argument_list|(
name|MSG
operator|.
name|reportConflictingMeetingTime
argument_list|()
argument_list|)
block|,
operator|new
name|CSVFile
operator|.
name|CSVField
argument_list|(
name|MSG
operator|.
name|reportTeachingOverlap
argument_list|()
argument_list|)
block|,
operator|new
name|CSVFile
operator|.
name|CSVField
argument_list|(
name|MSG
operator|.
name|reportOverlapMinutes
argument_list|()
argument_list|)
block|}
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|csv
operator|.
name|setHeader
argument_list|(
operator|new
name|CSVFile
operator|.
name|CSVField
index|[]
block|{
operator|new
name|CSVFile
operator|.
name|CSVField
argument_list|(
name|MSG
operator|.
name|reportStudentId
argument_list|()
argument_list|)
block|,
operator|new
name|CSVFile
operator|.
name|CSVField
argument_list|(
name|MSG
operator|.
name|reportStudentName
argument_list|()
argument_list|)
block|,
operator|new
name|CSVFile
operator|.
name|CSVField
argument_list|(
name|MSG
operator|.
name|reportStudentEmail
argument_list|()
argument_list|)
block|,
operator|new
name|CSVFile
operator|.
name|CSVField
argument_list|(
name|MSG
operator|.
name|reportStudentCurriculum
argument_list|()
argument_list|)
block|,
operator|new
name|CSVFile
operator|.
name|CSVField
argument_list|(
name|MSG
operator|.
name|reportStudentGroup
argument_list|()
argument_list|)
block|,
operator|new
name|CSVFile
operator|.
name|CSVField
argument_list|(
name|MSG
operator|.
name|reportStudentAdvisor
argument_list|()
argument_list|)
block|,
operator|new
name|CSVFile
operator|.
name|CSVField
argument_list|(
name|MSG
operator|.
name|reportCourse
argument_list|()
argument_list|)
block|,
operator|new
name|CSVFile
operator|.
name|CSVField
argument_list|(
name|MSG
operator|.
name|reportClass
argument_list|()
argument_list|)
block|,
operator|new
name|CSVFile
operator|.
name|CSVField
argument_list|(
name|MSG
operator|.
name|reportMeetingTime
argument_list|()
argument_list|)
block|,
operator|new
name|CSVFile
operator|.
name|CSVField
argument_list|(
name|MSG
operator|.
name|reportConflictingAssignment
argument_list|()
argument_list|)
block|,
operator|new
name|CSVFile
operator|.
name|CSVField
argument_list|(
name|MSG
operator|.
name|reportConflictingMeetingTime
argument_list|()
argument_list|)
block|,
operator|new
name|CSVFile
operator|.
name|CSVField
argument_list|(
name|MSG
operator|.
name|reportOverlapMinutes
argument_list|()
argument_list|)
block|}
argument_list|)
expr_stmt|;
block|}
name|Set
argument_list|<
name|Student
argument_list|>
name|students
init|=
operator|new
name|TreeSet
argument_list|<
name|Student
argument_list|>
argument_list|(
operator|new
name|Comparator
argument_list|<
name|Student
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|int
name|compare
parameter_list|(
name|Student
name|s1
parameter_list|,
name|Student
name|s2
parameter_list|)
block|{
return|return
name|s1
operator|.
name|getExternalId
argument_list|()
operator|.
name|compareTo
argument_list|(
name|s2
operator|.
name|getExternalId
argument_list|()
argument_list|)
return|;
block|}
block|}
argument_list|)
decl_stmt|;
name|students
operator|.
name|addAll
argument_list|(
name|getModel
argument_list|()
operator|.
name|getStudents
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|Student
name|student
range|:
name|students
control|)
block|{
if|if
condition|(
name|student
operator|.
name|isDummy
argument_list|()
operator|&&
operator|!
name|includeLastLikeStudents
condition|)
continue|continue;
if|if
condition|(
operator|!
name|student
operator|.
name|isDummy
argument_list|()
operator|&&
operator|!
name|includeRealStudents
condition|)
continue|continue;
if|if
condition|(
name|student
operator|.
name|getUnavailabilities
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
continue|continue;
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
block|{
name|Enrollment
name|e
init|=
name|assignment
operator|.
name|getValue
argument_list|(
name|r
argument_list|)
decl_stmt|;
if|if
condition|(
name|e
operator|==
literal|null
operator|||
name|r
operator|instanceof
name|FreeTimeRequest
condition|)
continue|continue;
for|for
control|(
name|Section
name|s
range|:
name|e
operator|.
name|getSections
argument_list|()
control|)
block|{
for|for
control|(
name|Unavailability
name|u
range|:
name|student
operator|.
name|getUnavailabilities
argument_list|()
control|)
block|{
if|if
condition|(
name|inConflict
argument_list|(
name|s
argument_list|,
name|u
argument_list|,
name|ignoreBreakTimeConflicts
argument_list|)
condition|)
block|{
if|if
condition|(
operator|!
name|includeAllowedOverlaps
operator|&&
operator|(
name|e
operator|.
name|isAllowOverlap
argument_list|()
operator|||
name|u
operator|.
name|isAllowOverlap
argument_list|()
operator|||
operator|!
name|s
operator|.
name|isOverlapping
argument_list|(
name|u
argument_list|)
operator|)
condition|)
continue|continue;
name|List
argument_list|<
name|CSVFile
operator|.
name|CSVField
argument_list|>
name|line
init|=
operator|new
name|ArrayList
argument_list|<
name|CSVFile
operator|.
name|CSVField
argument_list|>
argument_list|()
decl_stmt|;
name|line
operator|.
name|add
argument_list|(
operator|new
name|CSVFile
operator|.
name|CSVField
argument_list|(
name|student
operator|.
name|getExternalId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|line
operator|.
name|add
argument_list|(
operator|new
name|CSVFile
operator|.
name|CSVField
argument_list|(
name|student
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Student
name|dbStudent
init|=
name|StudentDAO
operator|.
name|getInstance
argument_list|()
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
name|dbStudent
operator|!=
literal|null
condition|)
name|line
operator|.
name|add
argument_list|(
operator|new
name|CSVFile
operator|.
name|CSVField
argument_list|(
name|dbStudent
operator|.
name|getEmail
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|line
operator|.
name|add
argument_list|(
operator|new
name|CSVFile
operator|.
name|CSVField
argument_list|(
name|curriculum
argument_list|(
name|student
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|line
operator|.
name|add
argument_list|(
operator|new
name|CSVFile
operator|.
name|CSVField
argument_list|(
name|group
argument_list|(
name|student
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|line
operator|.
name|add
argument_list|(
operator|new
name|CSVFile
operator|.
name|CSVField
argument_list|(
name|advisor
argument_list|(
name|student
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|includeAllowedOverlaps
condition|)
name|line
operator|.
name|add
argument_list|(
operator|new
name|CSVFile
operator|.
name|CSVField
argument_list|(
name|e
operator|.
name|isAllowOverlap
argument_list|()
operator|||
name|u
operator|.
name|isAllowOverlap
argument_list|()
operator|||
operator|!
name|s
operator|.
name|isOverlapping
argument_list|(
name|u
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|line
operator|.
name|add
argument_list|(
operator|new
name|CSVFile
operator|.
name|CSVField
argument_list|(
name|e
operator|.
name|getCourse
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|line
operator|.
name|add
argument_list|(
operator|new
name|CSVFile
operator|.
name|CSVField
argument_list|(
name|s
operator|.
name|getSubpart
argument_list|()
operator|.
name|getName
argument_list|()
operator|+
literal|" "
operator|+
name|s
operator|.
name|getName
argument_list|(
name|e
operator|.
name|getCourse
argument_list|()
operator|.
name|getId
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|line
operator|.
name|add
argument_list|(
operator|new
name|CSVFile
operator|.
name|CSVField
argument_list|(
name|s
operator|.
name|getTime
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|s
operator|.
name|getTime
argument_list|()
operator|.
name|getDayHeader
argument_list|()
operator|+
literal|" "
operator|+
name|s
operator|.
name|getTime
argument_list|()
operator|.
name|getStartTimeHeader
argument_list|(
name|useAmPm
argument_list|)
operator|+
literal|" - "
operator|+
name|s
operator|.
name|getTime
argument_list|()
operator|.
name|getEndTimeHeader
argument_list|(
name|useAmPm
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|includeAllowedOverlaps
condition|)
block|{
name|line
operator|.
name|add
argument_list|(
operator|new
name|CSVFile
operator|.
name|CSVField
argument_list|(
name|s
operator|.
name|getSubpart
argument_list|()
operator|.
name|isAllowOverlap
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|line
operator|.
name|add
argument_list|(
operator|new
name|CSVFile
operator|.
name|CSVField
argument_list|(
name|e
operator|.
name|getReservation
argument_list|()
operator|!=
literal|null
operator|&&
name|e
operator|.
name|getReservation
argument_list|()
operator|.
name|isAllowOverlap
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|u
operator|.
name|getSection
argument_list|()
operator|.
name|getSubpart
argument_list|()
operator|==
literal|null
condition|)
name|line
operator|.
name|add
argument_list|(
operator|new
name|CSVFile
operator|.
name|CSVField
argument_list|(
name|u
operator|.
name|getSection
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
else|else
name|line
operator|.
name|add
argument_list|(
operator|new
name|CSVFile
operator|.
name|CSVField
argument_list|(
name|u
operator|.
name|getSection
argument_list|()
operator|.
name|getSubpart
argument_list|()
operator|.
name|getConfig
argument_list|()
operator|.
name|getOffering
argument_list|()
operator|.
name|getName
argument_list|()
operator|+
literal|" "
operator|+
name|u
operator|.
name|getSection
argument_list|()
operator|.
name|getSubpart
argument_list|()
operator|.
name|getName
argument_list|()
operator|+
literal|" "
operator|+
name|u
operator|.
name|getSection
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|line
operator|.
name|add
argument_list|(
operator|new
name|CSVFile
operator|.
name|CSVField
argument_list|(
name|u
operator|.
name|getTime
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|u
operator|.
name|getTime
argument_list|()
operator|.
name|getDayHeader
argument_list|()
operator|+
literal|" "
operator|+
name|u
operator|.
name|getTime
argument_list|()
operator|.
name|getStartTimeHeader
argument_list|(
name|useAmPm
argument_list|)
operator|+
literal|" - "
operator|+
name|u
operator|.
name|getTime
argument_list|()
operator|.
name|getEndTimeHeader
argument_list|(
name|useAmPm
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|includeAllowedOverlaps
condition|)
block|{
name|line
operator|.
name|add
argument_list|(
operator|new
name|CSVFile
operator|.
name|CSVField
argument_list|(
name|u
operator|.
name|isAllowOverlap
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|line
operator|.
name|add
argument_list|(
operator|new
name|CSVFile
operator|.
name|CSVField
argument_list|(
name|sDF1
operator|.
name|format
argument_list|(
name|share
argument_list|(
name|s
argument_list|,
name|u
argument_list|,
name|ignoreBreakTimeConflicts
argument_list|)
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|csv
operator|.
name|addLine
argument_list|(
name|line
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
block|}
return|return
name|csv
return|;
block|}
annotation|@
name|Override
specifier|public
name|CSVFile
name|create
parameter_list|(
name|Assignment
argument_list|<
name|Request
argument_list|,
name|Enrollment
argument_list|>
name|assignment
parameter_list|,
name|DataProperties
name|properties
parameter_list|)
block|{
return|return
name|createTable
argument_list|(
name|assignment
argument_list|,
name|properties
operator|.
name|getPropertyBoolean
argument_list|(
literal|"lastlike"
argument_list|,
literal|false
argument_list|)
argument_list|,
name|properties
operator|.
name|getPropertyBoolean
argument_list|(
literal|"real"
argument_list|,
literal|true
argument_list|)
argument_list|,
name|properties
operator|.
name|getPropertyBoolean
argument_list|(
literal|"useAmPm"
argument_list|,
literal|true
argument_list|)
argument_list|,
name|properties
operator|.
name|getPropertyBoolean
argument_list|(
literal|"includeAllowedOverlaps"
argument_list|,
literal|true
argument_list|)
argument_list|,
name|properties
operator|.
name|getPropertyBoolean
argument_list|(
literal|"ignoreBreakTimeConflicts"
argument_list|,
literal|false
argument_list|)
argument_list|)
return|;
block|}
block|}
end_class

end_unit

