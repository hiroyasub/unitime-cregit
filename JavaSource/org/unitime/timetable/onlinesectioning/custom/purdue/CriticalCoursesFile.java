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
operator|.
name|purdue
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|File
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
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
name|javax
operator|.
name|servlet
operator|.
name|ServletException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|log4j
operator|.
name|Logger
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
name|cpsolver
operator|.
name|ifs
operator|.
name|util
operator|.
name|CSVFile
operator|.
name|CSVLine
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
operator|.
name|Action
operator|.
name|Builder
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
name|XAreaClassificationMajor
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

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|CriticalCoursesFile
implements|implements
name|CriticalCoursesProvider
block|{
specifier|private
specifier|static
name|Logger
name|sLog
init|=
name|Logger
operator|.
name|getLogger
argument_list|(
name|CriticalCoursesFile
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|static
name|Map
argument_list|<
name|String
argument_list|,
name|CriticalCoursesImpl
argument_list|>
name|iCriticalCourses
init|=
literal|null
decl_stmt|;
specifier|public
name|CriticalCoursesFile
parameter_list|()
throws|throws
name|ServletException
throws|,
name|IOException
block|{
name|String
name|courses
init|=
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"purdue.critical.file"
argument_list|,
literal|"critical-courses.csv"
argument_list|)
decl_stmt|;
name|CSVFile
name|file
init|=
literal|null
decl_stmt|;
if|if
condition|(
operator|new
name|File
argument_list|(
name|courses
argument_list|)
operator|.
name|exists
argument_list|()
condition|)
block|{
name|sLog
operator|.
name|info
argument_list|(
literal|"Reading menu from "
operator|+
name|courses
operator|+
literal|" ..."
argument_list|)
expr_stmt|;
name|file
operator|=
operator|new
name|CSVFile
argument_list|(
operator|new
name|File
argument_list|(
name|courses
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
throw|throw
operator|new
name|ServletException
argument_list|(
literal|"Unable to create critical course table, reason: resource "
operator|+
name|courses
operator|+
literal|" not found."
argument_list|)
throw|;
block|}
name|iCriticalCourses
operator|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|CriticalCoursesImpl
argument_list|>
argument_list|()
expr_stmt|;
for|for
control|(
name|CSVLine
name|line
range|:
name|file
operator|.
name|getLines
argument_list|()
control|)
block|{
name|CSVField
name|area
init|=
name|line
operator|.
name|getField
argument_list|(
literal|"Area"
argument_list|)
decl_stmt|;
name|CSVField
name|major
init|=
name|line
operator|.
name|getField
argument_list|(
literal|"Major"
argument_list|)
decl_stmt|;
name|CSVField
name|subject
init|=
name|line
operator|.
name|getField
argument_list|(
literal|"Subject"
argument_list|)
decl_stmt|;
name|CSVField
name|course
init|=
name|line
operator|.
name|getField
argument_list|(
literal|"Course"
argument_list|)
decl_stmt|;
if|if
condition|(
name|area
operator|!=
literal|null
operator|&&
name|major
operator|!=
literal|null
operator|&&
name|subject
operator|!=
literal|null
operator|&&
name|course
operator|!=
literal|null
condition|)
block|{
name|CriticalCoursesImpl
name|critical
init|=
name|iCriticalCourses
operator|.
name|get
argument_list|(
name|area
operator|+
literal|"/"
operator|+
name|major
argument_list|)
decl_stmt|;
if|if
condition|(
name|critical
operator|==
literal|null
condition|)
block|{
name|critical
operator|=
operator|new
name|CriticalCoursesImpl
argument_list|()
expr_stmt|;
name|iCriticalCourses
operator|.
name|put
argument_list|(
name|area
operator|+
literal|"/"
operator|+
name|major
argument_list|,
name|critical
argument_list|)
expr_stmt|;
block|}
name|critical
operator|.
name|add
argument_list|(
name|subject
operator|.
name|toString
argument_list|()
argument_list|,
name|course
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
annotation|@
name|Override
specifier|public
name|CriticalCourses
name|getCriticalCourses
parameter_list|(
name|OnlineSectioningServer
name|server
parameter_list|,
name|OnlineSectioningHelper
name|helper
parameter_list|,
name|XStudentId
name|student
parameter_list|)
block|{
return|return
name|getCriticalCourses
argument_list|(
name|server
argument_list|,
name|helper
argument_list|,
name|student
argument_list|,
name|helper
operator|.
name|getAction
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|CriticalCourses
name|getCriticalCourses
parameter_list|(
name|OnlineSectioningServer
name|server
parameter_list|,
name|OnlineSectioningHelper
name|helper
parameter_list|,
name|XStudentId
name|studentId
parameter_list|,
name|Builder
name|action
parameter_list|)
block|{
if|if
condition|(
name|iCriticalCourses
operator|==
literal|null
operator|||
name|iCriticalCourses
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
literal|null
return|;
name|XStudent
name|student
init|=
operator|(
name|studentId
operator|instanceof
name|XStudent
condition|?
operator|(
name|XStudent
operator|)
name|studentId
else|:
name|server
operator|.
name|getStudent
argument_list|(
name|studentId
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
for|for
control|(
name|XAreaClassificationMajor
name|acm
range|:
name|student
operator|.
name|getMajors
argument_list|()
control|)
block|{
name|CriticalCourses
name|cc
init|=
name|iCriticalCourses
operator|.
name|get
argument_list|(
name|acm
operator|.
name|getArea
argument_list|()
operator|+
literal|"/"
operator|+
name|acm
operator|.
name|getMajor
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|cc
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|action
operator|!=
literal|null
condition|)
name|action
operator|.
name|addOptionBuilder
argument_list|()
operator|.
name|setKey
argument_list|(
literal|"critical"
argument_list|)
operator|.
name|setValue
argument_list|(
name|cc
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|cc
return|;
block|}
block|}
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|dispose
parameter_list|()
block|{
block|}
specifier|protected
specifier|static
class|class
name|CriticalCoursesImpl
implements|implements
name|CriticalCourses
block|{
specifier|private
name|Set
argument_list|<
name|String
argument_list|>
name|iCriticalCourses
init|=
operator|new
name|TreeSet
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
specifier|public
name|boolean
name|add
parameter_list|(
name|String
name|subject
parameter_list|,
name|String
name|course
parameter_list|)
block|{
return|return
name|iCriticalCourses
operator|.
name|add
argument_list|(
name|subject
operator|+
literal|" "
operator|+
name|course
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isEmpty
parameter_list|()
block|{
return|return
name|iCriticalCourses
operator|.
name|isEmpty
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|isCritical
parameter_list|(
name|CourseOffering
name|course
parameter_list|)
block|{
for|for
control|(
name|String
name|c
range|:
name|iCriticalCourses
control|)
if|if
condition|(
name|course
operator|.
name|getCourseName
argument_list|()
operator|.
name|startsWith
argument_list|(
name|c
argument_list|)
condition|)
return|return
name|CourseDemand
operator|.
name|Critical
operator|.
name|IMPORTANT
operator|.
name|ordinal
argument_list|()
return|;
return|return
name|CourseDemand
operator|.
name|Critical
operator|.
name|NORMAL
operator|.
name|ordinal
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|isCritical
parameter_list|(
name|XCourseId
name|course
parameter_list|)
block|{
for|for
control|(
name|String
name|c
range|:
name|iCriticalCourses
control|)
if|if
condition|(
name|course
operator|.
name|getCourseName
argument_list|()
operator|.
name|startsWith
argument_list|(
name|c
argument_list|)
condition|)
return|return
name|CourseDemand
operator|.
name|Critical
operator|.
name|IMPORTANT
operator|.
name|ordinal
argument_list|()
return|;
return|return
name|CourseDemand
operator|.
name|Critical
operator|.
name|NORMAL
operator|.
name|ordinal
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
name|Set
argument_list|<
name|String
argument_list|>
name|courses
init|=
operator|new
name|TreeSet
argument_list|<
name|String
argument_list|>
argument_list|(
name|iCriticalCourses
argument_list|)
decl_stmt|;
return|return
name|courses
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
block|}
end_class

end_unit

