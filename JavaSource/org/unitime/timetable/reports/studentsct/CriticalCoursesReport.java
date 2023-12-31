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

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|CriticalCoursesReport
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
name|CriticalCoursesReport
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
name|reportPriority
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
name|report1stAlt
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
name|report2ndAlt
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
name|reportEnrolledCourse
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
name|reportEnrolledChoice
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
condition|)
continue|continue;
name|int
name|priority
init|=
literal|0
decl_stmt|;
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
if|if
condition|(
name|r
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
name|r
decl_stmt|;
name|priority
operator|++
expr_stmt|;
if|if
condition|(
operator|!
name|cr
operator|.
name|isCritical
argument_list|()
operator|||
name|cr
operator|.
name|isAlternative
argument_list|()
condition|)
continue|continue;
name|Enrollment
name|e
init|=
name|cr
operator|.
name|getAssignment
argument_list|(
name|assignment
argument_list|)
decl_stmt|;
name|Course
name|course
init|=
name|cr
operator|.
name|getCourses
argument_list|()
operator|.
name|get
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|Course
name|alt1
init|=
operator|(
name|cr
operator|.
name|getCourses
argument_list|()
operator|.
name|size
argument_list|()
operator|<
literal|2
condition|?
literal|null
else|:
name|cr
operator|.
name|getCourses
argument_list|()
operator|.
name|get
argument_list|(
literal|1
argument_list|)
operator|)
decl_stmt|;
name|Course
name|alt2
init|=
operator|(
name|cr
operator|.
name|getCourses
argument_list|()
operator|.
name|size
argument_list|()
operator|<
literal|3
condition|?
literal|null
else|:
name|cr
operator|.
name|getCourses
argument_list|()
operator|.
name|get
argument_list|(
literal|2
argument_list|)
operator|)
decl_stmt|;
name|Course
name|enrolled
init|=
operator|(
name|e
operator|==
literal|null
condition|?
literal|null
else|:
name|e
operator|.
name|getCourse
argument_list|()
operator|)
decl_stmt|;
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
name|csv
operator|.
name|addLine
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
name|student
operator|.
name|getId
argument_list|()
argument_list|)
block|,
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
block|,
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
block|,
operator|new
name|CSVFile
operator|.
name|CSVField
argument_list|(
name|dbStudent
operator|==
literal|null
condition|?
literal|null
else|:
name|dbStudent
operator|.
name|getEmail
argument_list|()
argument_list|)
block|,
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
block|,
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
block|,
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
block|,
operator|new
name|CSVFile
operator|.
name|CSVField
argument_list|(
name|priority
argument_list|)
block|,
operator|new
name|CSVFile
operator|.
name|CSVField
argument_list|(
name|course
operator|.
name|getName
argument_list|()
argument_list|)
block|,
operator|new
name|CSVFile
operator|.
name|CSVField
argument_list|(
name|alt1
operator|==
literal|null
condition|?
literal|""
else|:
name|alt1
operator|.
name|getName
argument_list|()
argument_list|)
block|,
operator|new
name|CSVFile
operator|.
name|CSVField
argument_list|(
name|alt2
operator|==
literal|null
condition|?
literal|""
else|:
name|alt2
operator|.
name|getName
argument_list|()
argument_list|)
block|,
operator|new
name|CSVFile
operator|.
name|CSVField
argument_list|(
name|enrolled
operator|==
literal|null
condition|?
literal|""
else|:
name|enrolled
operator|.
name|getName
argument_list|()
argument_list|)
block|,
operator|new
name|CSVFile
operator|.
name|CSVField
argument_list|(
name|enrolled
operator|==
literal|null
condition|?
literal|""
else|:
name|String
operator|.
name|valueOf
argument_list|(
name|cr
operator|.
name|getCourses
argument_list|()
operator|.
name|indexOf
argument_list|(
name|enrolled
argument_list|)
operator|+
literal|1
argument_list|)
argument_list|)
block|}
argument_list|)
expr_stmt|;
block|}
block|}
block|}
return|return
name|csv
return|;
block|}
block|}
end_class

end_unit

