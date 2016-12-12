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
name|Hashtable
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
name|TreeSet
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
name|assignment
operator|.
name|AssignmentComparator
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
name|model
operator|.
name|Course
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
name|CourseRequest
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
name|onlinesectioning
operator|.
name|OnlineSectioningHelper
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|UnasignedCourseRequests
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
name|StudentSectioningModel
name|iModel
init|=
literal|null
decl_stmt|;
specifier|public
name|UnasignedCourseRequests
parameter_list|(
name|StudentSectioningModel
name|model
parameter_list|)
block|{
name|iModel
operator|=
name|model
expr_stmt|;
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
argument_list|)
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
parameter_list|)
block|{
name|CSVFile
name|csv
init|=
operator|new
name|CSVFile
argument_list|()
decl_stmt|;
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
literal|"__Student"
argument_list|)
block|,
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
name|reportUnassignedCourse
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
name|reportAssignmentConflict
argument_list|()
argument_list|)
block|}
argument_list|)
expr_stmt|;
for|for
control|(
name|Student
name|student
range|:
name|getModel
argument_list|()
operator|.
name|getStudents
argument_list|()
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
name|assignment
operator|.
name|getValue
argument_list|(
name|request
argument_list|)
decl_stmt|;
if|if
condition|(
name|enrollment
operator|!=
literal|null
operator|||
operator|!
name|student
operator|.
name|canAssign
argument_list|(
name|assignment
argument_list|,
name|request
argument_list|)
condition|)
continue|continue;
name|CourseRequest
name|courseRequest
init|=
operator|(
name|CourseRequest
operator|)
name|request
decl_stmt|;
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
name|getId
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
name|courseRequest
operator|.
name|getCourses
argument_list|()
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|TreeSet
argument_list|<
name|Enrollment
argument_list|>
name|overlaps
init|=
operator|new
name|TreeSet
argument_list|<
name|Enrollment
argument_list|>
argument_list|(
operator|new
name|Comparator
argument_list|<
name|Enrollment
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|int
name|compare
parameter_list|(
name|Enrollment
name|o1
parameter_list|,
name|Enrollment
name|o2
parameter_list|)
block|{
return|return
name|o1
operator|.
name|getRequest
argument_list|()
operator|.
name|compareTo
argument_list|(
name|o2
operator|.
name|getRequest
argument_list|()
argument_list|)
return|;
block|}
block|}
argument_list|)
decl_stmt|;
name|Hashtable
argument_list|<
name|CourseRequest
argument_list|,
name|TreeSet
argument_list|<
name|Section
argument_list|>
argument_list|>
name|overlapingSections
init|=
operator|new
name|Hashtable
argument_list|<
name|CourseRequest
argument_list|,
name|TreeSet
argument_list|<
name|Section
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|Enrollment
argument_list|>
name|av
init|=
name|courseRequest
operator|.
name|getAvaiableEnrollmentsSkipSameTime
argument_list|(
name|assignment
argument_list|)
decl_stmt|;
if|if
condition|(
name|av
operator|.
name|isEmpty
argument_list|()
operator|||
operator|(
name|av
operator|.
name|size
argument_list|()
operator|==
literal|1
operator|&&
name|av
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|equals
argument_list|(
name|courseRequest
operator|.
name|getInitialAssignment
argument_list|()
argument_list|)
operator|&&
name|getModel
argument_list|()
operator|.
name|inConflict
argument_list|(
name|assignment
argument_list|,
name|av
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
operator|)
condition|)
block|{
if|if
condition|(
name|courseRequest
operator|.
name|getCourses
argument_list|()
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|getLimit
argument_list|()
operator|>=
literal|0
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
name|MSG
operator|.
name|courseIsFull
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
name|MSG
operator|.
name|classNotAvailable
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
for|for
control|(
name|Iterator
argument_list|<
name|Enrollment
argument_list|>
name|e
init|=
name|av
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
name|Enrollment
name|enrl
init|=
name|e
operator|.
name|next
argument_list|()
decl_stmt|;
for|for
control|(
name|Request
name|q
range|:
name|enrl
operator|.
name|getStudent
argument_list|()
operator|.
name|getRequests
argument_list|()
control|)
block|{
if|if
condition|(
name|q
operator|.
name|equals
argument_list|(
name|request
argument_list|)
condition|)
continue|continue;
name|Enrollment
name|x
init|=
name|assignment
operator|.
name|getValue
argument_list|(
name|q
argument_list|)
decl_stmt|;
if|if
condition|(
name|x
operator|==
literal|null
operator|||
name|x
operator|.
name|getAssignments
argument_list|()
operator|==
literal|null
operator|||
name|x
operator|.
name|getAssignments
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
continue|continue;
for|for
control|(
name|Iterator
argument_list|<
name|SctAssignment
argument_list|>
name|i
init|=
name|x
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
name|SctAssignment
name|a
init|=
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|a
operator|.
name|isOverlapping
argument_list|(
name|enrl
operator|.
name|getAssignments
argument_list|()
argument_list|)
condition|)
block|{
name|overlaps
operator|.
name|add
argument_list|(
name|x
argument_list|)
expr_stmt|;
if|if
condition|(
name|x
operator|.
name|getRequest
argument_list|()
operator|instanceof
name|CourseRequest
condition|)
block|{
name|CourseRequest
name|cr
init|=
operator|(
name|CourseRequest
operator|)
name|x
operator|.
name|getRequest
argument_list|()
decl_stmt|;
name|TreeSet
argument_list|<
name|Section
argument_list|>
name|ss
init|=
name|overlapingSections
operator|.
name|get
argument_list|(
name|cr
argument_list|)
decl_stmt|;
if|if
condition|(
name|ss
operator|==
literal|null
condition|)
block|{
name|ss
operator|=
operator|new
name|TreeSet
argument_list|<
name|Section
argument_list|>
argument_list|(
operator|new
name|AssignmentComparator
argument_list|<
name|Section
argument_list|,
name|Request
argument_list|,
name|Enrollment
argument_list|>
argument_list|(
name|assignment
argument_list|)
argument_list|)
expr_stmt|;
name|overlapingSections
operator|.
name|put
argument_list|(
name|cr
argument_list|,
name|ss
argument_list|)
expr_stmt|;
block|}
name|ss
operator|.
name|add
argument_list|(
operator|(
name|Section
operator|)
name|a
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
block|}
if|if
condition|(
operator|!
name|overlaps
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|String
name|overlap
init|=
literal|""
decl_stmt|;
for|for
control|(
name|Enrollment
name|q
range|:
name|overlaps
control|)
block|{
if|if
condition|(
operator|!
name|overlap
operator|.
name|isEmpty
argument_list|()
condition|)
name|overlap
operator|+=
literal|"\n"
expr_stmt|;
if|if
condition|(
name|q
operator|.
name|getRequest
argument_list|()
operator|instanceof
name|FreeTimeRequest
condition|)
block|{
name|overlap
operator|+=
name|OnlineSectioningHelper
operator|.
name|toString
argument_list|(
operator|(
name|FreeTimeRequest
operator|)
name|q
operator|.
name|getRequest
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|CourseRequest
name|cr
init|=
operator|(
name|CourseRequest
operator|)
name|q
operator|.
name|getRequest
argument_list|()
decl_stmt|;
name|Course
name|o
init|=
name|q
operator|.
name|getCourse
argument_list|()
decl_stmt|;
name|String
name|ov
init|=
name|MSG
operator|.
name|course
argument_list|(
name|o
operator|.
name|getSubjectArea
argument_list|()
argument_list|,
name|o
operator|.
name|getCourseNumber
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|overlapingSections
operator|.
name|get
argument_list|(
name|cr
argument_list|)
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
for|for
control|(
name|Iterator
argument_list|<
name|Section
argument_list|>
name|i
init|=
name|overlapingSections
operator|.
name|get
argument_list|(
name|cr
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
name|Section
name|s
init|=
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|ov
operator|+=
literal|" "
operator|+
name|s
operator|.
name|getSubpart
argument_list|()
operator|.
name|getName
argument_list|()
expr_stmt|;
if|if
condition|(
name|i
operator|.
name|hasNext
argument_list|()
condition|)
name|ov
operator|+=
literal|","
expr_stmt|;
block|}
name|overlap
operator|+=
name|ov
expr_stmt|;
block|}
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
name|MSG
operator|.
name|conflictWithFirst
argument_list|(
name|overlap
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
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
name|MSG
operator|.
name|courseNotAssigned
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
name|csv
operator|.
name|addLine
argument_list|(
name|line
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|csv
return|;
block|}
block|}
end_class

end_unit

