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
name|IOException
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
name|List
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
name|DegreePlanInterface
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
operator|.
name|XGroup
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|CriticalCoursesExplorers
extends|extends
name|CriticalCoursesQuery
block|{
specifier|protected
name|DegreeWorksCourseRequests
name|iDGW
decl_stmt|;
specifier|public
name|CriticalCoursesExplorers
parameter_list|()
throws|throws
name|ServletException
throws|,
name|IOException
block|{
name|iDGW
operator|=
operator|new
name|DegreeWorksCourseRequests
argument_list|()
expr_stmt|;
block|}
specifier|protected
name|boolean
name|isFallBackToDegreeWorks
parameter_list|()
block|{
return|return
literal|"true"
operator|.
name|equalsIgnoreCase
argument_list|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"banner.unex.useDgwFallback"
argument_list|,
literal|"true"
argument_list|)
argument_list|)
return|;
block|}
specifier|protected
name|String
name|getGroupType
parameter_list|()
block|{
return|return
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"banner.unex.groupType"
argument_list|,
literal|"1st Choice"
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|protected
name|List
argument_list|<
name|XAreaClassificationMajor
argument_list|>
name|getAreaClasfMajors
parameter_list|(
name|OnlineSectioningServer
name|server
parameter_list|,
name|OnlineSectioningHelper
name|helper
parameter_list|,
name|XStudent
name|student
parameter_list|)
block|{
name|List
argument_list|<
name|XAreaClassificationMajor
argument_list|>
name|ret
init|=
operator|(
name|isFallBackToDegreeWorks
argument_list|()
condition|?
operator|new
name|ArrayList
argument_list|<
name|XAreaClassificationMajor
argument_list|>
argument_list|()
else|:
literal|null
operator|)
decl_stmt|;
name|String
name|gType
init|=
name|getGroupType
argument_list|()
decl_stmt|;
name|String
name|clasf
init|=
literal|"01"
decl_stmt|;
if|if
condition|(
literal|"true"
operator|.
name|equalsIgnoreCase
argument_list|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"banner.unex.checkClassification"
argument_list|,
literal|"true"
argument_list|)
argument_list|)
condition|)
if|if
condition|(
name|student
operator|.
name|getMajors
argument_list|()
operator|!=
literal|null
condition|)
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
name|clasf
operator|=
name|acm
operator|.
name|getClassification
argument_list|()
expr_stmt|;
for|for
control|(
name|XGroup
name|g
range|:
name|student
operator|.
name|getGroups
argument_list|()
control|)
block|{
if|if
condition|(
name|gType
operator|.
name|equals
argument_list|(
name|g
operator|.
name|getType
argument_list|()
argument_list|)
operator|&&
name|g
operator|.
name|getAbbreviation
argument_list|()
operator|.
name|contains
argument_list|(
literal|"-"
argument_list|)
condition|)
block|{
name|String
name|area
init|=
name|g
operator|.
name|getAbbreviation
argument_list|()
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|g
operator|.
name|getAbbreviation
argument_list|()
operator|.
name|indexOf
argument_list|(
literal|'-'
argument_list|)
argument_list|)
decl_stmt|;
name|String
name|major
init|=
name|g
operator|.
name|getAbbreviation
argument_list|()
operator|.
name|substring
argument_list|(
name|g
operator|.
name|getAbbreviation
argument_list|()
operator|.
name|indexOf
argument_list|(
literal|'-'
argument_list|)
operator|+
literal|1
argument_list|)
decl_stmt|;
if|if
condition|(
name|ret
operator|==
literal|null
condition|)
name|ret
operator|=
operator|new
name|ArrayList
argument_list|<
name|XAreaClassificationMajor
argument_list|>
argument_list|(
name|student
operator|.
name|getMajors
argument_list|()
argument_list|)
expr_stmt|;
name|ret
operator|.
name|add
argument_list|(
operator|new
name|XAreaClassificationMajor
argument_list|(
name|area
argument_list|,
name|clasf
argument_list|,
name|major
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|ret
operator|!=
literal|null
condition|?
name|ret
else|:
name|student
operator|.
name|getMajors
argument_list|()
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
name|isFallBackToDegreeWorks
argument_list|()
condition|)
block|{
name|CriticalCourses
name|critQuery
init|=
name|super
operator|.
name|getCriticalCourses
argument_list|(
name|server
argument_list|,
name|helper
argument_list|,
name|studentId
argument_list|,
name|action
argument_list|)
decl_stmt|;
if|if
condition|(
name|critQuery
operator|!=
literal|null
operator|&&
operator|!
name|critQuery
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|CriticalCourses
name|critDgw
init|=
name|iDGW
operator|.
name|getCriticalCourses
argument_list|(
name|server
argument_list|,
name|helper
argument_list|,
name|studentId
argument_list|,
name|action
argument_list|)
decl_stmt|;
if|if
condition|(
name|critDgw
operator|==
literal|null
operator|||
name|critDgw
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
name|critQuery
return|;
return|return
operator|new
name|CombinedCriticals
argument_list|(
name|critDgw
argument_list|,
name|critQuery
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|iDGW
operator|.
name|getCriticalCourses
argument_list|(
name|server
argument_list|,
name|helper
argument_list|,
name|studentId
argument_list|,
name|action
argument_list|)
return|;
block|}
block|}
else|else
block|{
return|return
name|super
operator|.
name|getCriticalCourses
argument_list|(
name|server
argument_list|,
name|helper
argument_list|,
name|studentId
argument_list|,
name|action
argument_list|)
return|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|List
argument_list|<
name|DegreePlanInterface
argument_list|>
name|getDegreePlans
parameter_list|(
name|OnlineSectioningServer
name|server
parameter_list|,
name|OnlineSectioningHelper
name|helper
parameter_list|,
name|XStudent
name|student
parameter_list|,
name|CourseMatcher
name|matcher
parameter_list|)
throws|throws
name|SectioningException
block|{
if|if
condition|(
name|isFallBackToDegreeWorks
argument_list|()
condition|)
block|{
name|List
argument_list|<
name|DegreePlanInterface
argument_list|>
name|plans
init|=
name|super
operator|.
name|getDegreePlans
argument_list|(
name|server
argument_list|,
name|helper
argument_list|,
name|student
argument_list|,
name|matcher
argument_list|)
decl_stmt|;
if|if
condition|(
name|plans
operator|==
literal|null
operator|||
name|plans
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
name|iDGW
operator|.
name|getDegreePlans
argument_list|(
name|server
argument_list|,
name|helper
argument_list|,
name|student
argument_list|,
name|matcher
argument_list|)
return|;
else|else
block|{
name|List
argument_list|<
name|DegreePlanInterface
argument_list|>
name|dgw
init|=
name|iDGW
operator|.
name|getDegreePlans
argument_list|(
name|server
argument_list|,
name|helper
argument_list|,
name|student
argument_list|,
name|matcher
argument_list|)
decl_stmt|;
if|if
condition|(
name|dgw
operator|!=
literal|null
condition|)
name|plans
operator|.
name|addAll
argument_list|(
name|dgw
argument_list|)
expr_stmt|;
return|return
name|plans
return|;
block|}
block|}
else|else
block|{
return|return
name|super
operator|.
name|getDegreePlans
argument_list|(
name|server
argument_list|,
name|helper
argument_list|,
name|student
argument_list|,
name|matcher
argument_list|)
return|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|dispose
parameter_list|()
block|{
name|super
operator|.
name|dispose
argument_list|()
expr_stmt|;
name|iDGW
operator|.
name|dispose
argument_list|()
expr_stmt|;
block|}
specifier|public
specifier|static
class|class
name|CombinedCriticals
implements|implements
name|CriticalCourses
block|{
specifier|private
name|CriticalCourses
name|iPrimary
decl_stmt|,
name|iSecondary
decl_stmt|;
specifier|public
name|CombinedCriticals
parameter_list|(
name|CriticalCourses
name|primary
parameter_list|,
name|CriticalCourses
name|secondary
parameter_list|)
block|{
name|iPrimary
operator|=
name|primary
expr_stmt|;
name|iSecondary
operator|=
name|secondary
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isEmpty
parameter_list|()
block|{
return|return
name|iPrimary
operator|.
name|isEmpty
argument_list|()
operator|&&
name|iSecondary
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
name|int
name|crit
init|=
name|iPrimary
operator|.
name|isCritical
argument_list|(
name|course
argument_list|)
decl_stmt|;
if|if
condition|(
name|crit
operator|>
literal|0
condition|)
return|return
name|crit
return|;
return|return
name|iSecondary
operator|.
name|isCritical
argument_list|(
name|course
argument_list|)
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
name|int
name|crit
init|=
name|iPrimary
operator|.
name|isCritical
argument_list|(
name|course
argument_list|)
decl_stmt|;
if|if
condition|(
name|crit
operator|>
literal|0
condition|)
return|return
name|crit
return|;
return|return
name|iSecondary
operator|.
name|isCritical
argument_list|(
name|course
argument_list|)
return|;
block|}
block|}
block|}
end_class

end_unit

