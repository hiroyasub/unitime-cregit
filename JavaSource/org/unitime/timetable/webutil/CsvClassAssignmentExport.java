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
name|webutil
package|;
end_package

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
name|cpsolver
operator|.
name|coursett
operator|.
name|model
operator|.
name|Placement
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
name|RoomLocation
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
name|CSVFile
operator|.
name|CSVField
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|commons
operator|.
name|Debug
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
name|defaults
operator|.
name|UserProperty
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
name|GwtConstants
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
name|Assignment
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
name|ClassInstructor
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
name|DepartmentalInstructor
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
name|security
operator|.
name|UserContext
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
name|solver
operator|.
name|ClassAssignmentProxy
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
name|duration
operator|.
name|DurationModel
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|CsvClassAssignmentExport
block|{
specifier|protected
specifier|static
name|GwtConstants
name|CONSTANTS
init|=
name|Localization
operator|.
name|create
argument_list|(
name|GwtConstants
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|public
specifier|static
name|CSVFile
name|exportCsv
parameter_list|(
name|UserContext
name|user
parameter_list|,
name|Collection
name|classes
parameter_list|,
name|ClassAssignmentProxy
name|proxy
parameter_list|)
block|{
name|CSVFile
name|file
init|=
operator|new
name|CSVFile
argument_list|()
decl_stmt|;
name|String
name|instructorFormat
init|=
name|user
operator|.
name|getProperty
argument_list|(
name|UserProperty
operator|.
name|NameFormat
argument_list|)
decl_stmt|;
name|file
operator|.
name|setSeparator
argument_list|(
literal|","
argument_list|)
expr_stmt|;
name|file
operator|.
name|setQuotationMark
argument_list|(
literal|"\""
argument_list|)
expr_stmt|;
name|file
operator|.
name|setHeader
argument_list|(
operator|new
name|CSVField
index|[]
block|{
operator|new
name|CSVField
argument_list|(
literal|"COURSE"
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
literal|"ITYPE"
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
literal|"SECTION"
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
literal|"SUFFIX"
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
literal|"EXTERNAL_ID"
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
literal|"ENROLLMENT"
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
literal|"LIMIT"
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
literal|"DATE_PATTERN"
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
literal|"DAY"
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
literal|"START_TIME"
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
literal|"END_TIME"
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
literal|"ROOM"
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
literal|"INSTRUCTOR"
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
literal|"SCHEDULE_NOTE"
argument_list|)
block|}
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|classes
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
name|Object
index|[]
name|o
init|=
operator|(
name|Object
index|[]
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|Class_
name|clazz
init|=
operator|(
name|Class_
operator|)
name|o
index|[
literal|0
index|]
decl_stmt|;
name|CourseOffering
name|co
init|=
operator|(
name|CourseOffering
operator|)
name|o
index|[
literal|1
index|]
decl_stmt|;
name|StringBuffer
name|leadsSb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
if|if
condition|(
name|clazz
operator|.
name|isDisplayInstructor
argument_list|()
condition|)
for|for
control|(
name|ClassInstructor
name|ci
range|:
name|clazz
operator|.
name|getClassInstructors
argument_list|()
control|)
block|{
if|if
condition|(
operator|!
name|leadsSb
operator|.
name|toString
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
name|leadsSb
operator|.
name|append
argument_list|(
literal|"\n"
argument_list|)
expr_stmt|;
name|DepartmentalInstructor
name|instructor
init|=
name|ci
operator|.
name|getInstructor
argument_list|()
decl_stmt|;
name|leadsSb
operator|.
name|append
argument_list|(
name|instructor
operator|.
name|getName
argument_list|(
name|instructorFormat
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|String
name|divSec
init|=
name|clazz
operator|.
name|getClassSuffix
argument_list|(
name|co
argument_list|)
decl_stmt|;
name|Assignment
name|assignment
init|=
literal|null
decl_stmt|;
try|try
block|{
name|assignment
operator|=
name|proxy
operator|.
name|getAssignment
argument_list|(
name|clazz
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|Debug
operator|.
name|error
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|assignment
operator|!=
literal|null
condition|)
block|{
name|Placement
name|placement
init|=
name|assignment
operator|.
name|getPlacement
argument_list|()
decl_stmt|;
name|file
operator|.
name|addLine
argument_list|(
operator|new
name|CSVField
index|[]
block|{
operator|new
name|CSVField
argument_list|(
name|co
operator|.
name|getCourseName
argument_list|()
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
name|clazz
operator|.
name|getItypeDesc
argument_list|()
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
name|clazz
operator|.
name|getSectionNumber
argument_list|()
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
name|clazz
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getSchedulingSubpartSuffix
argument_list|()
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
name|divSec
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
name|clazz
operator|.
name|getEnrollment
argument_list|()
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
name|clazz
operator|.
name|getClassLimit
argument_list|(
name|proxy
argument_list|)
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
name|assignment
operator|.
name|getDatePattern
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
name|placement
operator|.
name|getTimeLocation
argument_list|()
operator|.
name|getDayHeader
argument_list|()
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
name|placement
operator|.
name|getTimeLocation
argument_list|()
operator|.
name|getStartTimeHeader
argument_list|(
name|CONSTANTS
operator|.
name|useAmPm
argument_list|()
argument_list|)
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
name|placement
operator|.
name|getTimeLocation
argument_list|()
operator|.
name|getEndTimeHeader
argument_list|(
name|CONSTANTS
operator|.
name|useAmPm
argument_list|()
argument_list|)
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
name|placement
operator|.
name|getRoomName
argument_list|(
literal|","
argument_list|)
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
name|leadsSb
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
name|clazz
operator|.
name|getSchedulePrintNote
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|clazz
operator|.
name|getSchedulePrintNote
argument_list|()
argument_list|)
block|}
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|DurationModel
name|dm
init|=
name|clazz
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getInstrOfferingConfig
argument_list|()
operator|.
name|getDurationModel
argument_list|()
decl_stmt|;
name|Integer
name|arrHrs
init|=
name|dm
operator|.
name|getArrangedHours
argument_list|(
name|clazz
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getMinutesPerWk
argument_list|()
argument_list|,
name|clazz
operator|.
name|effectiveDatePattern
argument_list|()
argument_list|)
decl_stmt|;
name|file
operator|.
name|addLine
argument_list|(
operator|new
name|CSVField
index|[]
block|{
operator|new
name|CSVField
argument_list|(
name|co
operator|.
name|getCourseName
argument_list|()
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
name|clazz
operator|.
name|getItypeDesc
argument_list|()
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
name|clazz
operator|.
name|getSectionNumber
argument_list|()
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
name|clazz
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getSchedulingSubpartSuffix
argument_list|()
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
name|divSec
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
name|clazz
operator|.
name|getEnrollment
argument_list|()
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
name|clazz
operator|.
name|getClassLimit
argument_list|(
name|proxy
argument_list|)
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
name|clazz
operator|.
name|effectiveDatePattern
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
literal|"Arr "
operator|+
operator|(
name|arrHrs
operator|==
literal|null
condition|?
literal|""
else|:
name|arrHrs
operator|+
literal|" "
operator|)
operator|+
literal|"Hrs"
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
literal|""
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
literal|""
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
literal|""
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
name|leadsSb
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
name|clazz
operator|.
name|getSchedulePrintNote
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|clazz
operator|.
name|getSchedulePrintNote
argument_list|()
argument_list|)
block|}
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|file
return|;
block|}
specifier|public
specifier|static
name|CSVFile
name|exportCsv2
parameter_list|(
name|UserContext
name|user
parameter_list|,
name|Collection
name|classes
parameter_list|,
name|ClassAssignmentProxy
name|proxy
parameter_list|)
block|{
name|CSVFile
name|file
init|=
operator|new
name|CSVFile
argument_list|()
decl_stmt|;
name|file
operator|.
name|setSeparator
argument_list|(
literal|","
argument_list|)
expr_stmt|;
name|file
operator|.
name|setQuotationMark
argument_list|(
literal|"\""
argument_list|)
expr_stmt|;
name|file
operator|.
name|setHeader
argument_list|(
operator|new
name|CSVField
index|[]
block|{
operator|new
name|CSVField
argument_list|(
literal|"ID"
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
literal|"COURSE"
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
literal|"DIVSEC"
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
literal|"TITLEOFFHR"
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
literal|"DATE_PATTERN"
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
literal|"DAYS"
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
literal|"TIME"
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
literal|"BUILDING"
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
literal|"ROOM"
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
literal|"LASTNAME"
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
literal|"FIRSTNAME"
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
literal|"MIDINITIAL"
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
literal|"INITIALS"
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
literal|"RANK"
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
literal|"NOTES"
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
literal|"SUBJECT"
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
literal|"INSTR_TYPE"
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
literal|"CROSS_LIST"
argument_list|)
block|}
argument_list|)
expr_stmt|;
name|int
name|idx
init|=
literal|1
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|classes
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
name|Object
index|[]
name|o
init|=
operator|(
name|Object
index|[]
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|Class_
name|clazz
init|=
operator|(
name|Class_
operator|)
name|o
index|[
literal|0
index|]
decl_stmt|;
name|CourseOffering
name|course
init|=
operator|(
name|CourseOffering
operator|)
name|o
index|[
literal|1
index|]
decl_stmt|;
if|if
condition|(
operator|!
name|course
operator|.
name|isIsControl
argument_list|()
condition|)
continue|continue;
name|List
argument_list|<
name|DepartmentalInstructor
argument_list|>
name|leads
init|=
name|clazz
operator|.
name|getLeadInstructors
argument_list|()
decl_stmt|;
name|StringBuffer
name|lastNameSb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|StringBuffer
name|firstNameSb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|StringBuffer
name|midNameSb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|StringBuffer
name|iniSb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|StringBuffer
name|rankSb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
argument_list|<
name|DepartmentalInstructor
argument_list|>
name|e
init|=
name|leads
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
name|DepartmentalInstructor
name|instructor
init|=
operator|(
name|DepartmentalInstructor
operator|)
name|e
operator|.
name|next
argument_list|()
decl_stmt|;
name|lastNameSb
operator|.
name|append
argument_list|(
name|instructor
operator|.
name|getLastName
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|instructor
operator|.
name|getLastName
argument_list|()
operator|.
name|trim
argument_list|()
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|1
argument_list|)
operator|+
name|instructor
operator|.
name|getLastName
argument_list|()
operator|.
name|trim
argument_list|()
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
operator|.
name|toLowerCase
argument_list|()
argument_list|)
expr_stmt|;
name|firstNameSb
operator|.
name|append
argument_list|(
name|instructor
operator|.
name|getFirstName
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|instructor
operator|.
name|getFirstName
argument_list|()
operator|.
name|trim
argument_list|()
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|1
argument_list|)
operator|+
name|instructor
operator|.
name|getFirstName
argument_list|()
operator|.
name|trim
argument_list|()
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
operator|.
name|toLowerCase
argument_list|()
argument_list|)
expr_stmt|;
name|midNameSb
operator|.
name|append
argument_list|(
name|instructor
operator|.
name|getMiddleName
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|instructor
operator|.
name|getMiddleName
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
name|iniSb
operator|.
name|append
argument_list|(
name|instructor
operator|.
name|getFirstName
argument_list|()
operator|==
literal|null
operator|||
name|instructor
operator|.
name|getFirstName
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|?
literal|""
else|:
name|instructor
operator|.
name|getFirstName
argument_list|()
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|iniSb
operator|.
name|append
argument_list|(
name|instructor
operator|.
name|getMiddleName
argument_list|()
operator|==
literal|null
operator|||
name|instructor
operator|.
name|getMiddleName
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|?
literal|""
else|:
literal|" "
operator|+
name|instructor
operator|.
name|getMiddleName
argument_list|()
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|rankSb
operator|.
name|append
argument_list|(
name|instructor
operator|.
name|getPositionType
argument_list|()
operator|.
name|getLabel
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|e
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|lastNameSb
operator|.
name|append
argument_list|(
literal|"\n"
argument_list|)
expr_stmt|;
name|firstNameSb
operator|.
name|append
argument_list|(
literal|"\n"
argument_list|)
expr_stmt|;
name|midNameSb
operator|.
name|append
argument_list|(
literal|"\n"
argument_list|)
expr_stmt|;
name|iniSb
operator|.
name|append
argument_list|(
literal|"\n"
argument_list|)
expr_stmt|;
name|rankSb
operator|.
name|append
argument_list|(
literal|"\n"
argument_list|)
expr_stmt|;
block|}
block|}
name|StringBuffer
name|noteSb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
if|if
condition|(
name|clazz
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getInstrOfferingConfig
argument_list|()
operator|.
name|getInstructionalOffering
argument_list|()
operator|.
name|getControllingCourseOffering
argument_list|()
operator|.
name|getScheduleBookNote
argument_list|()
operator|!=
literal|null
condition|)
name|noteSb
operator|.
name|append
argument_list|(
name|clazz
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getInstrOfferingConfig
argument_list|()
operator|.
name|getInstructionalOffering
argument_list|()
operator|.
name|getControllingCourseOffering
argument_list|()
operator|.
name|getScheduleBookNote
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|clazz
operator|.
name|getSchedulePrintNote
argument_list|()
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|noteSb
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|noteSb
operator|.
name|append
argument_list|(
literal|"\n"
argument_list|)
expr_stmt|;
name|noteSb
operator|.
name|append
argument_list|(
name|clazz
operator|.
name|getSchedulePrintNote
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|clazz
operator|.
name|getNotes
argument_list|()
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|noteSb
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|noteSb
operator|.
name|append
argument_list|(
literal|"\n"
argument_list|)
expr_stmt|;
name|noteSb
operator|.
name|append
argument_list|(
name|clazz
operator|.
name|getNotes
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|StringBuffer
name|titleSb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
if|if
condition|(
name|clazz
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getInstrOfferingConfig
argument_list|()
operator|.
name|getInstructionalOffering
argument_list|()
operator|.
name|getControllingCourseOffering
argument_list|()
operator|.
name|getTitle
argument_list|()
operator|!=
literal|null
condition|)
name|titleSb
operator|.
name|append
argument_list|(
name|clazz
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getInstrOfferingConfig
argument_list|()
operator|.
name|getInstructionalOffering
argument_list|()
operator|.
name|getControllingCourseOffering
argument_list|()
operator|.
name|getTitle
argument_list|()
argument_list|)
expr_stmt|;
name|StringBuffer
name|crossListSb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
if|if
condition|(
name|clazz
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getInstrOfferingConfig
argument_list|()
operator|.
name|getInstructionalOffering
argument_list|()
operator|.
name|getCourseOfferings
argument_list|()
operator|.
name|size
argument_list|()
operator|>
literal|1
condition|)
block|{
for|for
control|(
name|Iterator
name|j
init|=
name|clazz
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getInstrOfferingConfig
argument_list|()
operator|.
name|getInstructionalOffering
argument_list|()
operator|.
name|getCourseOfferings
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
name|CourseOffering
name|co
init|=
operator|(
name|CourseOffering
operator|)
name|j
operator|.
name|next
argument_list|()
decl_stmt|;
comment|/*                     if (clazz.getClassLimit(proxy,co)>0) {                         if (crossListSb.length()>0) crossListSb.append("\n");                         crossListSb.append(co.getCourseName());                     }                     */
name|crossListSb
operator|.
name|append
argument_list|(
name|co
operator|.
name|getCourseName
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
name|String
name|divSec
init|=
name|clazz
operator|.
name|getClassSuffix
argument_list|(
name|course
argument_list|)
decl_stmt|;
name|Assignment
name|assignment
init|=
literal|null
decl_stmt|;
try|try
block|{
name|assignment
operator|=
name|proxy
operator|.
name|getAssignment
argument_list|(
name|clazz
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|Debug
operator|.
name|error
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|assignment
operator|!=
literal|null
condition|)
block|{
name|Placement
name|placement
init|=
name|assignment
operator|.
name|getPlacement
argument_list|()
decl_stmt|;
name|StringBuffer
name|roomSb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|StringBuffer
name|bldgSb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
if|if
condition|(
name|placement
operator|.
name|isMultiRoom
argument_list|()
condition|)
block|{
for|for
control|(
name|Iterator
argument_list|<
name|RoomLocation
argument_list|>
name|e
init|=
name|placement
operator|.
name|getRoomLocations
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
name|RoomLocation
name|r
init|=
name|e
operator|.
name|next
argument_list|()
decl_stmt|;
name|String
name|room
init|=
name|r
operator|.
name|getName
argument_list|()
decl_stmt|;
name|bldgSb
operator|.
name|append
argument_list|(
name|room
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|room
operator|.
name|lastIndexOf
argument_list|(
literal|' '
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|roomSb
operator|.
name|append
argument_list|(
name|room
operator|.
name|substring
argument_list|(
name|room
operator|.
name|lastIndexOf
argument_list|(
literal|' '
argument_list|)
operator|+
literal|1
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|e
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|roomSb
operator|.
name|append
argument_list|(
literal|'\n'
argument_list|)
expr_stmt|;
name|bldgSb
operator|.
name|append
argument_list|(
literal|'\n'
argument_list|)
expr_stmt|;
block|}
block|}
block|}
else|else
block|{
name|String
name|room
init|=
operator|(
name|placement
operator|.
name|getRoomLocation
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|placement
operator|.
name|getRoomLocation
argument_list|()
operator|.
name|getName
argument_list|()
operator|)
decl_stmt|;
name|bldgSb
operator|.
name|append
argument_list|(
name|room
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|room
operator|.
name|lastIndexOf
argument_list|(
literal|' '
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|roomSb
operator|.
name|append
argument_list|(
name|room
operator|.
name|substring
argument_list|(
name|room
operator|.
name|lastIndexOf
argument_list|(
literal|' '
argument_list|)
operator|+
literal|1
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|file
operator|.
name|addLine
argument_list|(
operator|new
name|CSVField
index|[]
block|{
operator|new
name|CSVField
argument_list|(
name|idx
operator|++
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
name|clazz
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getInstrOfferingConfig
argument_list|()
operator|.
name|getInstructionalOffering
argument_list|()
operator|.
name|getControllingCourseOffering
argument_list|()
operator|.
name|getCourseNbr
argument_list|()
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
name|divSec
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
name|titleSb
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
name|assignment
operator|.
name|getDatePattern
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
name|placement
operator|.
name|getTimeLocation
argument_list|()
operator|.
name|getDayHeader
argument_list|()
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
name|placement
operator|.
name|getTimeLocation
argument_list|()
operator|.
name|getStartTimeHeader
argument_list|(
name|CONSTANTS
operator|.
name|useAmPm
argument_list|()
argument_list|)
operator|+
literal|" - "
operator|+
name|placement
operator|.
name|getTimeLocation
argument_list|()
operator|.
name|getEndTimeHeader
argument_list|(
name|CONSTANTS
operator|.
name|useAmPm
argument_list|()
argument_list|)
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
name|bldgSb
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
name|roomSb
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
name|lastNameSb
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
name|firstNameSb
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
name|midNameSb
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
name|iniSb
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
name|rankSb
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
name|noteSb
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
name|clazz
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getInstrOfferingConfig
argument_list|()
operator|.
name|getInstructionalOffering
argument_list|()
operator|.
name|getControllingCourseOffering
argument_list|()
operator|.
name|getSubjectAreaAbbv
argument_list|()
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
name|clazz
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getItypeDesc
argument_list|()
operator|.
name|trim
argument_list|()
operator|.
name|toUpperCase
argument_list|()
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
name|crossListSb
argument_list|)
block|}
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|DurationModel
name|dm
init|=
name|clazz
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getInstrOfferingConfig
argument_list|()
operator|.
name|getDurationModel
argument_list|()
decl_stmt|;
name|Integer
name|arrHrs
init|=
name|dm
operator|.
name|getArrangedHours
argument_list|(
name|clazz
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getMinutesPerWk
argument_list|()
argument_list|,
name|clazz
operator|.
name|effectiveDatePattern
argument_list|()
argument_list|)
decl_stmt|;
name|file
operator|.
name|addLine
argument_list|(
operator|new
name|CSVField
index|[]
block|{
operator|new
name|CSVField
argument_list|(
name|idx
operator|++
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
name|clazz
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getInstrOfferingConfig
argument_list|()
operator|.
name|getInstructionalOffering
argument_list|()
operator|.
name|getControllingCourseOffering
argument_list|()
operator|.
name|getCourseNbr
argument_list|()
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
name|divSec
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
name|titleSb
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
name|clazz
operator|.
name|effectiveDatePattern
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
literal|""
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
literal|"Arr "
operator|+
operator|(
name|arrHrs
operator|==
literal|null
condition|?
literal|""
else|:
name|arrHrs
operator|+
literal|" "
operator|)
operator|+
literal|"Hrs"
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
literal|""
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
literal|""
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
name|lastNameSb
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
name|firstNameSb
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
name|midNameSb
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
name|iniSb
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
name|rankSb
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
name|noteSb
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
name|clazz
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getInstrOfferingConfig
argument_list|()
operator|.
name|getInstructionalOffering
argument_list|()
operator|.
name|getControllingCourseOffering
argument_list|()
operator|.
name|getSubjectAreaAbbv
argument_list|()
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
name|clazz
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getItypeDesc
argument_list|()
operator|.
name|trim
argument_list|()
operator|.
name|toUpperCase
argument_list|()
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
name|crossListSb
argument_list|)
block|}
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|file
return|;
block|}
block|}
end_class

end_unit

