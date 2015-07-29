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
name|basic
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
name|Map
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Properties
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
name|assignment
operator|.
name|AssignmentMap
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
name|DistanceConflict
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
name|Config
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
name|Offering
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
name|Subpart
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
name|online
operator|.
name|OnlineSectioningModel
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
name|SectionConflictTable
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
name|cpsolver
operator|.
name|studentsct
operator|.
name|reservation
operator|.
name|CourseReservation
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
name|reservation
operator|.
name|CurriculumReservation
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
name|reservation
operator|.
name|DummyReservation
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
name|reservation
operator|.
name|GroupReservation
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
name|reservation
operator|.
name|IndividualReservation
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
name|reservation
operator|.
name|Reservation
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
name|reservation
operator|.
name|ReservationOverride
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
name|XCourseReservation
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
name|XCurriculumReservation
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
name|XDistribution
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
name|XDistributionType
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
name|XGroupReservation
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
name|XIndividualReservation
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
name|XReservation
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

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|GenerateSectioningReport
implements|implements
name|OnlineSectioningAction
argument_list|<
name|CSVFile
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
name|DataProperties
name|iParameters
init|=
literal|null
decl_stmt|;
specifier|public
name|GenerateSectioningReport
name|withParameters
parameter_list|(
name|Properties
name|parameters
parameter_list|)
block|{
if|if
condition|(
name|parameters
operator|instanceof
name|DataProperties
condition|)
name|iParameters
operator|=
operator|(
name|DataProperties
operator|)
name|parameters
expr_stmt|;
else|else
name|iParameters
operator|=
operator|new
name|DataProperties
argument_list|(
name|parameters
argument_list|)
expr_stmt|;
return|return
name|this
return|;
block|}
annotation|@
name|Override
specifier|public
name|CSVFile
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
name|readLock
argument_list|()
decl_stmt|;
try|try
block|{
name|OnlineSectioningModel
name|model
init|=
operator|new
name|OnlineSectioningModel
argument_list|(
name|server
operator|.
name|getConfig
argument_list|()
argument_list|,
name|server
operator|.
name|getOverExpectedCriterion
argument_list|()
argument_list|)
decl_stmt|;
name|model
operator|.
name|setDistanceConflict
argument_list|(
operator|new
name|DistanceConflict
argument_list|(
name|server
operator|.
name|getDistanceMetric
argument_list|()
argument_list|,
name|model
operator|.
name|getProperties
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|model
operator|.
name|setTimeOverlaps
argument_list|(
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
argument_list|)
expr_stmt|;
name|Map
argument_list|<
name|Long
argument_list|,
name|Offering
argument_list|>
name|offerings
init|=
operator|new
name|HashMap
argument_list|<
name|Long
argument_list|,
name|Offering
argument_list|>
argument_list|()
decl_stmt|;
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|Course
argument_list|>
name|courses
init|=
operator|new
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|Course
argument_list|>
argument_list|()
decl_stmt|;
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|GroupReservation
argument_list|>
argument_list|>
name|groups
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|GroupReservation
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|Config
argument_list|>
name|configs
init|=
operator|new
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|Config
argument_list|>
argument_list|()
decl_stmt|;
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|Subpart
argument_list|>
name|subparts
init|=
operator|new
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|Subpart
argument_list|>
argument_list|()
decl_stmt|;
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|Section
argument_list|>
name|sections
init|=
operator|new
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|Section
argument_list|>
argument_list|()
decl_stmt|;
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|Reservation
argument_list|>
name|reservations
init|=
operator|new
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|Reservation
argument_list|>
argument_list|()
decl_stmt|;
name|Set
argument_list|<
name|XDistribution
argument_list|>
name|linkedSections
init|=
operator|new
name|HashSet
argument_list|<
name|XDistribution
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
operator|||
name|offerings
operator|.
name|containsKey
argument_list|(
name|offering
operator|.
name|getOfferingId
argument_list|()
argument_list|)
condition|)
continue|continue;
name|Offering
name|clonedOffering
init|=
operator|new
name|Offering
argument_list|(
name|offering
operator|.
name|getOfferingId
argument_list|()
argument_list|,
name|offering
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
name|clonedOffering
operator|.
name|setModel
argument_list|(
name|model
argument_list|)
expr_stmt|;
name|Long
name|courseId
init|=
literal|null
decl_stmt|;
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
name|Course
name|clonedCourse
init|=
operator|new
name|Course
argument_list|(
name|course
operator|.
name|getCourseId
argument_list|()
argument_list|,
name|course
operator|.
name|getSubjectArea
argument_list|()
argument_list|,
name|course
operator|.
name|getCourseNumber
argument_list|()
argument_list|,
name|clonedOffering
argument_list|,
name|course
operator|.
name|getLimit
argument_list|()
argument_list|,
name|course
operator|.
name|getProjected
argument_list|()
argument_list|)
decl_stmt|;
name|clonedCourse
operator|.
name|setNote
argument_list|(
name|course
operator|.
name|getNote
argument_list|()
argument_list|)
expr_stmt|;
name|courses
operator|.
name|put
argument_list|(
name|course
operator|.
name|getCourseId
argument_list|()
argument_list|,
name|clonedCourse
argument_list|)
expr_stmt|;
if|if
condition|(
name|offering
operator|.
name|getName
argument_list|()
operator|.
name|equals
argument_list|(
name|course
operator|.
name|getCourseName
argument_list|()
argument_list|)
condition|)
name|courseId
operator|=
name|course
operator|.
name|getCourseId
argument_list|()
expr_stmt|;
block|}
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
name|Config
name|clonedConfig
init|=
operator|new
name|Config
argument_list|(
name|config
operator|.
name|getConfigId
argument_list|()
argument_list|,
name|config
operator|.
name|getLimit
argument_list|()
argument_list|,
name|config
operator|.
name|getName
argument_list|()
argument_list|,
name|clonedOffering
argument_list|)
decl_stmt|;
name|configs
operator|.
name|put
argument_list|(
name|config
operator|.
name|getConfigId
argument_list|()
argument_list|,
name|clonedConfig
argument_list|)
expr_stmt|;
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
name|Subpart
name|clonedSubpart
init|=
operator|new
name|Subpart
argument_list|(
name|subpart
operator|.
name|getSubpartId
argument_list|()
argument_list|,
name|subpart
operator|.
name|getInstructionalType
argument_list|()
argument_list|,
name|subpart
operator|.
name|getName
argument_list|()
argument_list|,
name|clonedConfig
argument_list|,
operator|(
name|subpart
operator|.
name|getParentId
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
name|subparts
operator|.
name|get
argument_list|(
name|subpart
operator|.
name|getParentId
argument_list|()
argument_list|)
operator|)
argument_list|)
decl_stmt|;
name|clonedSubpart
operator|.
name|setAllowOverlap
argument_list|(
name|subpart
operator|.
name|isAllowOverlap
argument_list|()
argument_list|)
expr_stmt|;
name|clonedSubpart
operator|.
name|setCredit
argument_list|(
name|subpart
operator|.
name|getCredit
argument_list|(
name|courseId
argument_list|)
argument_list|)
expr_stmt|;
name|subparts
operator|.
name|put
argument_list|(
name|subpart
operator|.
name|getSubpartId
argument_list|()
argument_list|,
name|clonedSubpart
argument_list|)
expr_stmt|;
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
name|Section
name|clonedSection
init|=
operator|new
name|Section
argument_list|(
name|section
operator|.
name|getSectionId
argument_list|()
argument_list|,
name|section
operator|.
name|getLimit
argument_list|()
argument_list|,
name|section
operator|.
name|getName
argument_list|()
argument_list|,
name|clonedSubpart
argument_list|,
name|section
operator|.
name|toPlacement
argument_list|()
argument_list|,
name|section
operator|.
name|getInstructorIds
argument_list|()
argument_list|,
name|section
operator|.
name|getInstructorNames
argument_list|()
argument_list|,
operator|(
name|section
operator|.
name|getParentId
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
name|sections
operator|.
name|get
argument_list|(
name|section
operator|.
name|getParentId
argument_list|()
argument_list|)
operator|)
argument_list|)
decl_stmt|;
name|clonedSection
operator|.
name|setName
argument_list|(
operator|-
literal|1l
argument_list|,
name|section
operator|.
name|getName
argument_list|(
operator|-
literal|1l
argument_list|)
argument_list|)
expr_stmt|;
name|clonedSection
operator|.
name|setNote
argument_list|(
name|section
operator|.
name|getNote
argument_list|()
argument_list|)
expr_stmt|;
name|clonedSection
operator|.
name|setCancelled
argument_list|(
name|section
operator|.
name|isCancelled
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|XDistribution
name|distribution
range|:
name|offering
operator|.
name|getDistributions
argument_list|()
control|)
block|{
if|if
condition|(
name|distribution
operator|.
name|getDistributionType
argument_list|()
operator|==
name|XDistributionType
operator|.
name|IngoreConflicts
operator|&&
name|distribution
operator|.
name|hasSection
argument_list|(
name|section
operator|.
name|getSectionId
argument_list|()
argument_list|)
condition|)
block|{
for|for
control|(
name|Long
name|id
range|:
name|distribution
operator|.
name|getSectionIds
argument_list|()
control|)
if|if
condition|(
operator|!
name|id
operator|.
name|equals
argument_list|(
name|section
operator|.
name|getSectionId
argument_list|()
argument_list|)
condition|)
name|clonedSection
operator|.
name|addIgnoreConflictWith
argument_list|(
name|id
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|distribution
operator|.
name|getDistributionType
argument_list|()
operator|==
name|XDistributionType
operator|.
name|LinkedSections
condition|)
block|{
name|linkedSections
operator|.
name|add
argument_list|(
name|distribution
argument_list|)
expr_stmt|;
block|}
block|}
name|sections
operator|.
name|put
argument_list|(
name|section
operator|.
name|getSectionId
argument_list|()
argument_list|,
name|clonedSection
argument_list|)
expr_stmt|;
block|}
block|}
block|}
for|for
control|(
name|XReservation
name|reservation
range|:
name|offering
operator|.
name|getReservations
argument_list|()
control|)
block|{
name|Reservation
name|clonedReservation
init|=
literal|null
decl_stmt|;
switch|switch
condition|(
name|reservation
operator|.
name|getType
argument_list|()
condition|)
block|{
case|case
name|Course
case|:
name|XCourseReservation
name|courseR
init|=
operator|(
name|XCourseReservation
operator|)
name|reservation
decl_stmt|;
name|clonedReservation
operator|=
operator|new
name|CourseReservation
argument_list|(
name|reservation
operator|.
name|getReservationId
argument_list|()
argument_list|,
name|courses
operator|.
name|get
argument_list|(
name|courseR
operator|.
name|getCourseId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
break|break;
case|case
name|Curriculum
case|:
name|XCurriculumReservation
name|curriculumR
init|=
operator|(
name|XCurriculumReservation
operator|)
name|reservation
decl_stmt|;
name|clonedReservation
operator|=
operator|new
name|CurriculumReservation
argument_list|(
name|reservation
operator|.
name|getReservationId
argument_list|()
argument_list|,
name|reservation
operator|.
name|getLimit
argument_list|()
argument_list|,
name|clonedOffering
argument_list|,
name|curriculumR
operator|.
name|getAcademicArea
argument_list|()
argument_list|,
name|curriculumR
operator|.
name|getClassifications
argument_list|()
argument_list|,
name|curriculumR
operator|.
name|getMajors
argument_list|()
argument_list|)
expr_stmt|;
break|break;
case|case
name|Group
case|:
if|if
condition|(
name|reservation
operator|instanceof
name|XIndividualReservation
condition|)
block|{
name|XIndividualReservation
name|indR
init|=
operator|(
name|XIndividualReservation
operator|)
name|reservation
decl_stmt|;
name|clonedReservation
operator|=
operator|new
name|GroupReservation
argument_list|(
name|reservation
operator|.
name|getReservationId
argument_list|()
argument_list|,
name|reservation
operator|.
name|getLimit
argument_list|()
argument_list|,
name|clonedOffering
argument_list|,
name|indR
operator|.
name|getStudentIds
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|XGroupReservation
name|groupR
init|=
operator|(
name|XGroupReservation
operator|)
name|reservation
decl_stmt|;
name|clonedReservation
operator|=
operator|new
name|GroupReservation
argument_list|(
name|reservation
operator|.
name|getReservationId
argument_list|()
argument_list|,
name|reservation
operator|.
name|getLimit
argument_list|()
argument_list|,
name|clonedOffering
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|GroupReservation
argument_list|>
name|list
init|=
name|groups
operator|.
name|get
argument_list|(
name|groupR
operator|.
name|getGroup
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|list
operator|==
literal|null
condition|)
block|{
name|list
operator|=
operator|new
name|ArrayList
argument_list|<
name|GroupReservation
argument_list|>
argument_list|()
expr_stmt|;
name|groups
operator|.
name|put
argument_list|(
name|groupR
operator|.
name|getGroup
argument_list|()
argument_list|,
name|list
argument_list|)
expr_stmt|;
block|}
name|list
operator|.
name|add
argument_list|(
operator|(
name|GroupReservation
operator|)
name|clonedReservation
argument_list|)
expr_stmt|;
block|}
break|break;
case|case
name|Individual
case|:
name|XIndividualReservation
name|indR
init|=
operator|(
name|XIndividualReservation
operator|)
name|reservation
decl_stmt|;
name|clonedReservation
operator|=
operator|new
name|IndividualReservation
argument_list|(
name|reservation
operator|.
name|getReservationId
argument_list|()
argument_list|,
name|clonedOffering
argument_list|,
name|indR
operator|.
name|getStudentIds
argument_list|()
argument_list|)
expr_stmt|;
break|break;
case|case
name|Override
case|:
name|XIndividualReservation
name|ovrR
init|=
operator|(
name|XIndividualReservation
operator|)
name|reservation
decl_stmt|;
name|clonedReservation
operator|=
operator|new
name|ReservationOverride
argument_list|(
name|reservation
operator|.
name|getReservationId
argument_list|()
argument_list|,
name|clonedOffering
argument_list|,
name|ovrR
operator|.
name|getStudentIds
argument_list|()
argument_list|)
expr_stmt|;
operator|(
operator|(
name|ReservationOverride
operator|)
name|clonedReservation
operator|)
operator|.
name|setMustBeUsed
argument_list|(
name|ovrR
operator|.
name|mustBeUsed
argument_list|()
argument_list|)
expr_stmt|;
operator|(
operator|(
name|ReservationOverride
operator|)
name|clonedReservation
operator|)
operator|.
name|setAllowOverlap
argument_list|(
name|ovrR
operator|.
name|isAllowOverlap
argument_list|()
argument_list|)
expr_stmt|;
operator|(
operator|(
name|ReservationOverride
operator|)
name|clonedReservation
operator|)
operator|.
name|setCanAssignOverLimit
argument_list|(
name|ovrR
operator|.
name|canAssignOverLimit
argument_list|()
argument_list|)
expr_stmt|;
break|break;
default|default:
name|clonedReservation
operator|=
operator|new
name|DummyReservation
argument_list|(
name|clonedOffering
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|Long
name|configId
range|:
name|reservation
operator|.
name|getConfigsIds
argument_list|()
control|)
name|clonedReservation
operator|.
name|addConfig
argument_list|(
name|configs
operator|.
name|get
argument_list|(
name|configId
argument_list|)
argument_list|)
expr_stmt|;
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|Long
argument_list|,
name|Set
argument_list|<
name|Long
argument_list|>
argument_list|>
name|entry
range|:
name|reservation
operator|.
name|getSections
argument_list|()
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|Set
argument_list|<
name|Section
argument_list|>
name|clonedSections
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
name|Long
name|sectionId
range|:
name|entry
operator|.
name|getValue
argument_list|()
control|)
name|clonedSections
operator|.
name|add
argument_list|(
name|sections
operator|.
name|get
argument_list|(
name|sectionId
argument_list|)
argument_list|)
expr_stmt|;
name|clonedReservation
operator|.
name|getSections
argument_list|()
operator|.
name|put
argument_list|(
name|subparts
operator|.
name|get
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|)
argument_list|,
name|clonedSections
argument_list|)
expr_stmt|;
block|}
name|reservations
operator|.
name|put
argument_list|(
name|reservation
operator|.
name|getReservationId
argument_list|()
argument_list|,
name|clonedReservation
argument_list|)
expr_stmt|;
block|}
name|offerings
operator|.
name|put
argument_list|(
name|offering
operator|.
name|getOfferingId
argument_list|()
argument_list|,
name|clonedOffering
argument_list|)
expr_stmt|;
name|model
operator|.
name|addOffering
argument_list|(
name|clonedOffering
argument_list|)
expr_stmt|;
block|}
name|Map
argument_list|<
name|Long
argument_list|,
name|Student
argument_list|>
name|students
init|=
operator|new
name|HashMap
argument_list|<
name|Long
argument_list|,
name|Student
argument_list|>
argument_list|()
decl_stmt|;
name|Assignment
argument_list|<
name|Request
argument_list|,
name|Enrollment
argument_list|>
name|assignment
init|=
operator|new
name|AssignmentMap
argument_list|<
name|Request
argument_list|,
name|Enrollment
argument_list|>
argument_list|()
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
name|clonnedStudent
operator|.
name|setExternalId
argument_list|(
name|student
operator|.
name|getExternalId
argument_list|()
argument_list|)
expr_stmt|;
name|clonnedStudent
operator|.
name|setName
argument_list|(
name|student
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|String
name|g
range|:
name|student
operator|.
name|getGroups
argument_list|()
control|)
block|{
name|List
argument_list|<
name|GroupReservation
argument_list|>
name|list
init|=
name|groups
operator|.
name|get
argument_list|(
name|g
argument_list|)
decl_stmt|;
if|if
condition|(
name|list
operator|!=
literal|null
condition|)
for|for
control|(
name|GroupReservation
name|gr
range|:
name|list
control|)
name|gr
operator|.
name|getStudentIds
argument_list|()
operator|.
name|add
argument_list|(
name|student
operator|.
name|getStudentId
argument_list|()
argument_list|)
expr_stmt|;
block|}
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
name|req
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
name|Course
name|course
init|=
name|courses
operator|.
name|get
argument_list|(
name|c
operator|.
name|getCourseId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|course
operator|!=
literal|null
condition|)
name|req
operator|.
name|add
argument_list|(
name|course
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|req
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
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
name|req
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
name|configs
operator|.
name|get
argument_list|(
name|enrollment
operator|.
name|getConfigId
argument_list|()
argument_list|)
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
name|Long
name|sectionId
range|:
name|enrollment
operator|.
name|getSectionIds
argument_list|()
control|)
block|{
name|Section
name|section
init|=
name|sections
operator|.
name|get
argument_list|(
name|sectionId
argument_list|)
decl_stmt|;
if|if
condition|(
name|section
operator|!=
literal|null
condition|)
name|assignments
operator|.
name|add
argument_list|(
name|section
argument_list|)
expr_stmt|;
block|}
name|Reservation
name|reservation
init|=
operator|(
name|enrollment
operator|.
name|getReservation
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
name|reservations
operator|.
name|get
argument_list|(
name|enrollment
operator|.
name|getReservation
argument_list|()
operator|.
name|getReservationId
argument_list|()
argument_list|)
operator|)
decl_stmt|;
if|if
condition|(
name|config
operator|!=
literal|null
operator|&&
operator|!
name|sections
operator|.
name|isEmpty
argument_list|()
condition|)
name|assignment
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
name|courses
operator|.
name|get
argument_list|(
name|enrollment
operator|.
name|getCourseId
argument_list|()
argument_list|)
argument_list|,
name|config
argument_list|,
name|assignments
argument_list|,
name|reservation
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
name|students
operator|.
name|put
argument_list|(
name|student
operator|.
name|getStudentId
argument_list|()
argument_list|,
name|clonnedStudent
argument_list|)
expr_stmt|;
name|model
operator|.
name|addStudent
argument_list|(
name|clonnedStudent
argument_list|)
expr_stmt|;
block|}
block|}
for|for
control|(
name|XDistribution
name|distribution
range|:
name|linkedSections
control|)
block|{
name|List
argument_list|<
name|Section
argument_list|>
name|linked
init|=
operator|new
name|ArrayList
argument_list|<
name|Section
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Long
name|id
range|:
name|distribution
operator|.
name|getSectionIds
argument_list|()
control|)
block|{
name|Section
name|section
init|=
name|sections
operator|.
name|get
argument_list|(
name|id
argument_list|)
decl_stmt|;
if|if
condition|(
name|section
operator|!=
literal|null
condition|)
name|linked
operator|.
name|add
argument_list|(
name|section
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|linked
operator|.
name|size
argument_list|()
operator|>
literal|1
condition|)
name|model
operator|.
name|addLinkedSections
argument_list|(
name|linked
argument_list|)
expr_stmt|;
block|}
name|String
name|name
init|=
name|iParameters
operator|.
name|getProperty
argument_list|(
literal|"report"
argument_list|,
name|SectionConflictTable
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
name|Class
argument_list|<
name|StudentSectioningReport
argument_list|>
name|clazz
init|=
operator|(
name|Class
argument_list|<
name|StudentSectioningReport
argument_list|>
operator|)
name|Class
operator|.
name|forName
argument_list|(
name|name
argument_list|)
decl_stmt|;
name|StudentSectioningReport
name|report
init|=
name|clazz
operator|.
name|getConstructor
argument_list|(
name|StudentSectioningModel
operator|.
name|class
argument_list|)
operator|.
name|newInstance
argument_list|(
name|model
argument_list|)
decl_stmt|;
return|return
name|report
operator|.
name|create
argument_list|(
name|assignment
argument_list|,
name|iParameters
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|SectioningException
name|e
parameter_list|)
block|{
throw|throw
name|e
throw|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|SectioningException
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
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
literal|"report"
return|;
block|}
block|}
end_class

end_unit

