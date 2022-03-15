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
name|Collection
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collections
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
name|HashMap
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
name|org
operator|.
name|cpsolver
operator|.
name|ifs
operator|.
name|heuristics
operator|.
name|RouletteWheelSelection
import|;
end_import

begin_import
import|import
name|org
operator|.
name|hibernate
operator|.
name|criterion
operator|.
name|Order
import|;
end_import

begin_import
import|import
name|org
operator|.
name|hibernate
operator|.
name|type
operator|.
name|LongType
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
name|ApplicationProperty
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
name|Query
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
name|ClassAssignmentInterface
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
name|ClassAssignmentInterface
operator|.
name|CourseAssignment
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
name|OverrideType
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
name|OverrideTypeDAO
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
name|custom
operator|.
name|CustomCourseLookupHolder
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
name|status
operator|.
name|StatusPageSuggestionsAction
operator|.
name|StudentMatcher
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|ListCourseOfferings
implements|implements
name|OnlineSectioningAction
argument_list|<
name|Collection
argument_list|<
name|ClassAssignmentInterface
operator|.
name|CourseAssignment
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
specifier|protected
name|String
name|iQuery
init|=
literal|null
decl_stmt|;
specifier|protected
name|Integer
name|iLimit
init|=
literal|null
decl_stmt|;
specifier|protected
name|CourseMatcher
name|iMatcher
init|=
literal|null
decl_stmt|;
specifier|protected
name|Long
name|iStudentId
decl_stmt|;
specifier|protected
name|String
name|iFilterIM
init|=
literal|null
decl_stmt|;
specifier|private
specifier|transient
name|XStudent
name|iStudent
init|=
literal|null
decl_stmt|;
specifier|public
name|ListCourseOfferings
name|forQuery
parameter_list|(
name|String
name|query
parameter_list|)
block|{
name|iQuery
operator|=
name|query
expr_stmt|;
return|return
name|this
return|;
block|}
specifier|public
name|ListCourseOfferings
name|withLimit
parameter_list|(
name|Integer
name|limit
parameter_list|)
block|{
name|iLimit
operator|=
name|limit
expr_stmt|;
return|return
name|this
return|;
block|}
specifier|public
name|ListCourseOfferings
name|withMatcher
parameter_list|(
name|CourseMatcher
name|matcher
parameter_list|)
block|{
name|iMatcher
operator|=
name|matcher
expr_stmt|;
return|return
name|this
return|;
block|}
specifier|public
name|ListCourseOfferings
name|forStudent
parameter_list|(
name|Long
name|studentId
parameter_list|)
block|{
name|iStudentId
operator|=
name|studentId
expr_stmt|;
return|return
name|this
return|;
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
annotation|@
name|Override
specifier|public
name|Collection
argument_list|<
name|CourseAssignment
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
name|iStudent
operator|=
operator|(
name|getStudentId
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
name|server
operator|.
name|getStudent
argument_list|(
name|getStudentId
argument_list|()
argument_list|)
operator|)
expr_stmt|;
if|if
condition|(
name|iStudent
operator|!=
literal|null
condition|)
block|{
name|String
name|filter
init|=
name|server
operator|.
name|getConfig
argument_list|()
operator|.
name|getProperty
argument_list|(
literal|"Filter.OnlineOnlyStudentFilter"
argument_list|,
literal|null
argument_list|)
decl_stmt|;
if|if
condition|(
name|filter
operator|!=
literal|null
operator|&&
operator|!
name|filter
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
if|if
condition|(
operator|new
name|Query
argument_list|(
name|filter
argument_list|)
operator|.
name|match
argument_list|(
operator|new
name|StudentMatcher
argument_list|(
name|iStudent
argument_list|,
name|server
operator|.
name|getAcademicSession
argument_list|()
operator|.
name|getDefaultSectioningStatus
argument_list|()
argument_list|,
name|server
argument_list|,
literal|false
argument_list|)
argument_list|)
condition|)
block|{
name|iFilterIM
operator|=
name|server
operator|.
name|getConfig
argument_list|()
operator|.
name|getProperty
argument_list|(
literal|"Filter.OnlineOnlyInstructionalModeRegExp"
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|server
operator|.
name|getConfig
argument_list|()
operator|.
name|getPropertyBoolean
argument_list|(
literal|"Filter.OnlineOnlyExclusiveCourses"
argument_list|,
literal|false
argument_list|)
condition|)
block|{
name|iFilterIM
operator|=
name|server
operator|.
name|getConfig
argument_list|()
operator|.
name|getProperty
argument_list|(
literal|"Filter.ResidentialInstructionalModeRegExp"
argument_list|)
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
name|iFilterIM
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|helper
operator|.
name|hasAdminPermission
argument_list|()
operator|&&
name|server
operator|.
name|getConfig
argument_list|()
operator|.
name|getPropertyBoolean
argument_list|(
literal|"Filter.OnlineOnlyAdminOverride"
argument_list|,
literal|false
argument_list|)
condition|)
name|iFilterIM
operator|=
literal|null
expr_stmt|;
if|else if
condition|(
name|helper
operator|.
name|hasAvisorPermission
argument_list|()
operator|&&
name|server
operator|.
name|getConfig
argument_list|()
operator|.
name|getPropertyBoolean
argument_list|(
literal|"Filter.OnlineOnlyAdvisorOverride"
argument_list|,
literal|false
argument_list|)
condition|)
name|iFilterIM
operator|=
literal|null
expr_stmt|;
block|}
name|List
argument_list|<
name|CourseAssignment
argument_list|>
name|courses
init|=
name|listCourses
argument_list|(
name|server
argument_list|,
name|helper
argument_list|)
decl_stmt|;
if|if
condition|(
name|courses
operator|!=
literal|null
operator|&&
operator|!
name|courses
operator|.
name|isEmpty
argument_list|()
operator|&&
name|courses
operator|.
name|size
argument_list|()
operator|<=
literal|1000
condition|)
block|{
name|List
argument_list|<
name|OverrideType
argument_list|>
name|overrides
init|=
name|OverrideTypeDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|findAll
argument_list|(
name|helper
operator|.
name|getHibSession
argument_list|()
argument_list|,
name|Order
operator|.
name|asc
argument_list|(
literal|"label"
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|overrides
operator|!=
literal|null
operator|&&
operator|!
name|overrides
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|Map
argument_list|<
name|Long
argument_list|,
name|CourseAssignment
argument_list|>
name|table
init|=
operator|new
name|HashMap
argument_list|<
name|Long
argument_list|,
name|CourseAssignment
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|CourseAssignment
name|ca
range|:
name|courses
control|)
name|table
operator|.
name|put
argument_list|(
name|ca
operator|.
name|getCourseId
argument_list|()
argument_list|,
name|ca
argument_list|)
expr_stmt|;
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
name|helper
operator|.
name|getHibSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"from CourseOffering co left join fetch co.disabledOverrides do where co.uniqueId in :courseIds"
argument_list|)
operator|.
name|setParameterList
argument_list|(
literal|"courseIds"
argument_list|,
name|table
operator|.
name|keySet
argument_list|()
argument_list|,
name|LongType
operator|.
name|INSTANCE
argument_list|)
operator|.
name|list
argument_list|()
control|)
block|{
for|for
control|(
name|OverrideType
name|override
range|:
name|overrides
control|)
if|if
condition|(
operator|!
name|co
operator|.
name|getDisabledOverrides
argument_list|()
operator|.
name|contains
argument_list|(
name|override
argument_list|)
condition|)
name|table
operator|.
name|get
argument_list|(
name|co
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|addOverride
argument_list|(
name|override
operator|.
name|getReference
argument_list|()
argument_list|,
name|override
operator|.
name|getLabel
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
name|ApplicationProperty
operator|.
name|ListCourseOfferingsMatchingCampusFirst
operator|.
name|isTrue
argument_list|()
operator|&&
name|iStudent
operator|!=
literal|null
operator|&&
name|courses
operator|!=
literal|null
operator|&&
operator|!
name|courses
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|XAreaClassificationMajor
name|primary
init|=
name|iStudent
operator|.
name|getPrimaryMajor
argument_list|()
decl_stmt|;
specifier|final
name|String
name|campus
init|=
operator|(
name|primary
operator|==
literal|null
condition|?
literal|null
else|:
name|primary
operator|.
name|getCampus
argument_list|()
operator|)
decl_stmt|;
if|if
condition|(
name|campus
operator|!=
literal|null
operator|&&
operator|!
name|campus
operator|.
name|equals
argument_list|(
name|server
operator|.
name|getAcademicSession
argument_list|()
operator|.
name|getCampus
argument_list|()
argument_list|)
condition|)
block|{
name|List
argument_list|<
name|CourseAssignment
argument_list|>
name|ret
init|=
operator|new
name|ArrayList
argument_list|<
name|CourseAssignment
argument_list|>
argument_list|(
name|courses
operator|.
name|size
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|CourseAssignment
name|ca
range|:
name|courses
control|)
block|{
if|if
condition|(
name|ca
operator|.
name|getSubject
argument_list|()
operator|.
name|startsWith
argument_list|(
name|campus
operator|+
literal|" - "
argument_list|)
condition|)
name|ret
operator|.
name|add
argument_list|(
name|ca
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|ret
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
name|courses
return|;
for|for
control|(
name|CourseAssignment
name|ca
range|:
name|courses
control|)
block|{
if|if
condition|(
operator|!
name|ca
operator|.
name|getSubject
argument_list|()
operator|.
name|startsWith
argument_list|(
name|campus
operator|+
literal|" - "
argument_list|)
condition|)
name|ret
operator|.
name|add
argument_list|(
name|ca
argument_list|)
expr_stmt|;
block|}
return|return
name|ret
return|;
block|}
block|}
return|return
name|courses
return|;
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
specifier|protected
name|List
argument_list|<
name|CourseAssignment
argument_list|>
name|listCourses
parameter_list|(
name|OnlineSectioningServer
name|server
parameter_list|,
name|OnlineSectioningHelper
name|helper
parameter_list|)
block|{
name|List
argument_list|<
name|CourseAssignment
argument_list|>
name|ret
init|=
name|customCourseLookup
argument_list|(
name|server
argument_list|,
name|helper
argument_list|)
decl_stmt|;
if|if
condition|(
name|ret
operator|!=
literal|null
operator|&&
operator|!
name|ret
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
name|ret
return|;
name|ret
operator|=
operator|new
name|ArrayList
argument_list|<
name|CourseAssignment
argument_list|>
argument_list|()
expr_stmt|;
for|for
control|(
name|XCourseId
name|id
range|:
name|server
operator|.
name|findCourses
argument_list|(
name|iQuery
argument_list|,
name|iLimit
argument_list|,
name|iMatcher
argument_list|)
control|)
block|{
name|XCourse
name|course
init|=
name|server
operator|.
name|getCourse
argument_list|(
name|id
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
name|ret
operator|.
name|add
argument_list|(
name|convert
argument_list|(
name|course
argument_list|,
name|server
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|ret
return|;
block|}
specifier|protected
name|List
argument_list|<
name|CourseAssignment
argument_list|>
name|customCourseLookup
parameter_list|(
name|OnlineSectioningServer
name|server
parameter_list|,
name|OnlineSectioningHelper
name|helper
parameter_list|)
block|{
try|try
block|{
if|if
condition|(
name|iMatcher
operator|!=
literal|null
condition|)
name|iMatcher
operator|.
name|setServer
argument_list|(
name|server
argument_list|)
expr_stmt|;
if|if
condition|(
name|iQuery
operator|!=
literal|null
operator|&&
operator|!
name|iQuery
operator|.
name|isEmpty
argument_list|()
operator|&&
name|CustomCourseLookupHolder
operator|.
name|hasProvider
argument_list|()
condition|)
block|{
name|List
argument_list|<
name|XCourse
argument_list|>
name|courses
init|=
name|CustomCourseLookupHolder
operator|.
name|getProvider
argument_list|()
operator|.
name|getCourses
argument_list|(
name|server
argument_list|,
name|helper
argument_list|,
name|iQuery
argument_list|,
literal|true
argument_list|)
decl_stmt|;
if|if
condition|(
name|courses
operator|!=
literal|null
operator|&&
operator|!
name|courses
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|List
argument_list|<
name|CourseAssignment
argument_list|>
name|ret
init|=
operator|new
name|ArrayList
argument_list|<
name|CourseAssignment
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|XCourse
name|course
range|:
name|courses
control|)
block|{
if|if
condition|(
name|course
operator|!=
literal|null
operator|&&
operator|(
name|iMatcher
operator|==
literal|null
operator|||
name|iMatcher
operator|.
name|match
argument_list|(
name|course
argument_list|)
operator|)
condition|)
name|ret
operator|.
name|add
argument_list|(
name|convert
argument_list|(
name|course
argument_list|,
name|server
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|setSelection
argument_list|(
name|ret
argument_list|)
expr_stmt|;
return|return
name|ret
return|;
block|}
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|helper
operator|.
name|error
argument_list|(
literal|"Failed to use the custom course lookup: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
return|return
literal|null
return|;
block|}
specifier|protected
name|CourseAssignment
name|convert
parameter_list|(
name|XCourse
name|c
parameter_list|,
name|OnlineSectioningServer
name|server
parameter_list|)
block|{
name|CourseAssignment
name|course
init|=
operator|new
name|CourseAssignment
argument_list|()
decl_stmt|;
name|course
operator|.
name|setCourseId
argument_list|(
name|c
operator|.
name|getCourseId
argument_list|()
argument_list|)
expr_stmt|;
name|course
operator|.
name|setSubject
argument_list|(
name|c
operator|.
name|getSubjectArea
argument_list|()
argument_list|)
expr_stmt|;
name|course
operator|.
name|setCourseNbr
argument_list|(
name|c
operator|.
name|getCourseNumber
argument_list|()
argument_list|)
expr_stmt|;
name|course
operator|.
name|setTitle
argument_list|(
name|c
operator|.
name|getTitle
argument_list|()
argument_list|)
expr_stmt|;
name|course
operator|.
name|setNote
argument_list|(
name|c
operator|.
name|getNote
argument_list|()
argument_list|)
expr_stmt|;
name|course
operator|.
name|setCreditAbbv
argument_list|(
name|c
operator|.
name|getCreditAbbv
argument_list|()
argument_list|)
expr_stmt|;
name|course
operator|.
name|setCreditText
argument_list|(
name|c
operator|.
name|getCreditText
argument_list|()
argument_list|)
expr_stmt|;
name|course
operator|.
name|setTitle
argument_list|(
name|c
operator|.
name|getTitle
argument_list|()
argument_list|)
expr_stmt|;
name|course
operator|.
name|setHasUniqueName
argument_list|(
name|c
operator|.
name|hasUniqueName
argument_list|()
argument_list|)
expr_stmt|;
name|course
operator|.
name|setLimit
argument_list|(
name|c
operator|.
name|getLimit
argument_list|()
argument_list|)
expr_stmt|;
name|course
operator|.
name|setSnapShotLimit
argument_list|(
name|c
operator|.
name|getSnapshotLimit
argument_list|()
argument_list|)
expr_stmt|;
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
name|XEnrollment
name|enrollment
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|iFilterIM
operator|!=
literal|null
operator|&&
name|iStudent
operator|!=
literal|null
condition|)
block|{
name|XCourseRequest
name|r
init|=
name|iStudent
operator|.
name|getRequestForCourse
argument_list|(
name|c
operator|.
name|getCourseId
argument_list|()
argument_list|)
decl_stmt|;
name|enrollment
operator|=
operator|(
name|r
operator|==
literal|null
condition|?
literal|null
else|:
name|r
operator|.
name|getEnrollment
argument_list|()
operator|)
expr_stmt|;
block|}
if|if
condition|(
name|offering
operator|!=
literal|null
condition|)
block|{
name|course
operator|.
name|setAvailability
argument_list|(
name|offering
operator|.
name|getCourseAvailability
argument_list|(
name|server
operator|.
name|getRequests
argument_list|(
name|c
operator|.
name|getOfferingId
argument_list|()
argument_list|)
argument_list|,
name|c
argument_list|)
argument_list|)
expr_stmt|;
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
if|if
condition|(
name|iFilterIM
operator|!=
literal|null
operator|&&
operator|(
name|enrollment
operator|==
literal|null
operator|||
operator|!
name|config
operator|.
name|getConfigId
argument_list|()
operator|.
name|equals
argument_list|(
name|enrollment
operator|.
name|getConfigId
argument_list|()
argument_list|)
operator|)
condition|)
block|{
name|String
name|imRef
init|=
operator|(
name|config
operator|.
name|getInstructionalMethod
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
name|config
operator|.
name|getInstructionalMethod
argument_list|()
operator|.
name|getReference
argument_list|()
operator|)
decl_stmt|;
if|if
condition|(
name|iFilterIM
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
if|if
condition|(
name|imRef
operator|!=
literal|null
operator|&&
operator|!
name|imRef
operator|.
name|isEmpty
argument_list|()
condition|)
continue|continue;
block|}
else|else
block|{
if|if
condition|(
name|imRef
operator|==
literal|null
operator|||
operator|!
name|imRef
operator|.
name|matches
argument_list|(
name|iFilterIM
argument_list|)
condition|)
continue|continue;
block|}
block|}
if|if
condition|(
name|config
operator|.
name|getInstructionalMethod
argument_list|()
operator|!=
literal|null
condition|)
name|course
operator|.
name|addInstructionalMethod
argument_list|(
name|config
operator|.
name|getInstructionalMethod
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|config
operator|.
name|getInstructionalMethod
argument_list|()
operator|.
name|getLabel
argument_list|()
argument_list|)
expr_stmt|;
else|else
name|course
operator|.
name|setHasNoInstructionalMethod
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
name|course
operator|.
name|setHasCrossList
argument_list|(
name|offering
operator|.
name|hasCrossList
argument_list|()
argument_list|)
expr_stmt|;
name|course
operator|.
name|setCanWaitList
argument_list|(
name|offering
operator|.
name|isWaitList
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|course
return|;
block|}
specifier|static
interface|interface
name|SelectionModeInterface
block|{
specifier|public
name|int
name|getPoints
parameter_list|(
name|CourseAssignment
name|ca
parameter_list|)
function_decl|;
block|}
specifier|public
specifier|static
enum|enum
name|SelectionMode
implements|implements
name|Comparator
argument_list|<
name|CourseAssignment
argument_list|>
block|{
name|availability
argument_list|(
operator|new
name|SelectionModeInterface
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|int
name|getPoints
parameter_list|(
name|CourseAssignment
name|ca
parameter_list|)
block|{
name|int
name|p
init|=
literal|0
decl_stmt|;
if|if
condition|(
name|ca
operator|.
name|getLimit
argument_list|()
operator|!=
literal|null
condition|)
name|p
operator|+=
literal|4
operator|*
operator|(
name|ca
operator|.
name|getLimit
argument_list|()
operator|<
literal|0
condition|?
literal|9999
else|:
name|ca
operator|.
name|getLimit
argument_list|()
operator|)
expr_stmt|;
if|if
condition|(
name|ca
operator|.
name|getEnrollment
argument_list|()
operator|!=
literal|null
condition|)
name|p
operator|-=
literal|3
operator|*
operator|(
name|ca
operator|.
name|getEnrollment
argument_list|()
operator|)
expr_stmt|;
if|if
condition|(
name|ca
operator|.
name|getRequested
argument_list|()
operator|!=
literal|null
condition|)
name|p
operator|-=
name|ca
operator|.
name|getRequested
argument_list|()
expr_stmt|;
return|return
name|p
return|;
block|}
block|}
argument_list|)
block|,
name|limit
argument_list|(
operator|new
name|SelectionModeInterface
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|int
name|getPoints
parameter_list|(
name|CourseAssignment
name|ca
parameter_list|)
block|{
return|return
operator|(
name|ca
operator|.
name|getLimit
argument_list|()
operator|<
literal|0
condition|?
literal|999
else|:
name|ca
operator|.
name|getLimit
argument_list|()
operator|)
return|;
block|}
block|}
argument_list|)
block|,
name|snapshot
argument_list|(
operator|new
name|SelectionModeInterface
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|int
name|getPoints
parameter_list|(
name|CourseAssignment
name|ca
parameter_list|)
block|{
name|int
name|snapshot
init|=
operator|(
name|ca
operator|.
name|getSnapShotLimit
argument_list|()
operator|==
literal|null
condition|?
literal|0
else|:
name|ca
operator|.
name|getSnapShotLimit
argument_list|()
operator|<
literal|0
condition|?
literal|999
else|:
name|ca
operator|.
name|getSnapShotLimit
argument_list|()
operator|)
decl_stmt|;
name|int
name|limit
init|=
operator|(
name|ca
operator|.
name|getLimit
argument_list|()
operator|<
literal|0
condition|?
literal|999
else|:
name|ca
operator|.
name|getLimit
argument_list|()
operator|)
decl_stmt|;
return|return
name|Math
operator|.
name|max
argument_list|(
name|snapshot
argument_list|,
name|limit
argument_list|)
return|;
block|}
block|}
argument_list|)
block|, 		;
name|SelectionModeInterface
name|iMode
decl_stmt|;
name|SelectionMode
parameter_list|(
name|SelectionModeInterface
name|mode
parameter_list|)
block|{
name|iMode
operator|=
name|mode
expr_stmt|;
block|}
specifier|public
name|int
name|getPoints
parameter_list|(
name|CourseAssignment
name|ca
parameter_list|)
block|{
return|return
name|iMode
operator|.
name|getPoints
argument_list|(
name|ca
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|compare
parameter_list|(
name|CourseAssignment
name|ca1
parameter_list|,
name|CourseAssignment
name|ca2
parameter_list|)
block|{
name|int
name|p1
init|=
name|getPoints
argument_list|(
name|ca1
argument_list|)
decl_stmt|;
name|int
name|p2
init|=
name|getPoints
argument_list|(
name|ca2
argument_list|)
decl_stmt|;
if|if
condition|(
name|p1
operator|!=
name|p2
condition|)
return|return
operator|(
name|p1
operator|>
name|p2
condition|?
operator|-
literal|1
else|:
literal|1
operator|)
return|;
return|return
name|ca1
operator|.
name|getCourseNameWithTitle
argument_list|()
operator|.
name|compareTo
argument_list|(
name|ca2
operator|.
name|getCourseNameWithTitle
argument_list|()
argument_list|)
return|;
block|}
block|}
specifier|public
specifier|static
name|void
name|setSelection
parameter_list|(
name|List
argument_list|<
name|CourseAssignment
argument_list|>
name|courses
parameter_list|)
block|{
if|if
condition|(
name|courses
operator|==
literal|null
operator|||
name|courses
operator|.
name|isEmpty
argument_list|()
condition|)
return|return;
name|SelectionMode
name|mode
init|=
name|SelectionMode
operator|.
name|valueOf
argument_list|(
name|ApplicationProperty
operator|.
name|ListCourseOfferingsSelectionMode
operator|.
name|value
argument_list|()
argument_list|)
decl_stmt|;
name|int
name|limit
init|=
name|ApplicationProperty
operator|.
name|ListCourseOfferingsSelectionLimit
operator|.
name|intValue
argument_list|()
decl_stmt|;
if|if
condition|(
name|ApplicationProperty
operator|.
name|ListCourseOfferingsSelectionRandomize
operator|.
name|isTrue
argument_list|()
condition|)
block|{
name|RouletteWheelSelection
argument_list|<
name|CourseAssignment
argument_list|>
name|roulette
init|=
operator|new
name|RouletteWheelSelection
argument_list|<
name|CourseAssignment
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|CourseAssignment
name|ca
range|:
name|courses
control|)
block|{
name|int
name|p
init|=
name|mode
operator|.
name|getPoints
argument_list|(
name|ca
argument_list|)
decl_stmt|;
if|if
condition|(
name|p
operator|>
literal|0
condition|)
name|roulette
operator|.
name|add
argument_list|(
name|ca
argument_list|,
name|p
argument_list|)
expr_stmt|;
block|}
name|int
name|idx
init|=
literal|0
decl_stmt|;
while|while
condition|(
name|roulette
operator|.
name|hasMoreElements
argument_list|()
operator|&&
name|idx
operator|<
name|limit
condition|)
block|{
name|CourseAssignment
name|ca
init|=
name|roulette
operator|.
name|nextElement
argument_list|()
decl_stmt|;
name|ca
operator|.
name|setSelection
argument_list|(
name|idx
operator|++
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|List
argument_list|<
name|CourseAssignment
argument_list|>
name|sorted
init|=
operator|new
name|ArrayList
argument_list|<
name|CourseAssignment
argument_list|>
argument_list|(
name|courses
argument_list|)
decl_stmt|;
name|Collections
operator|.
name|sort
argument_list|(
name|sorted
argument_list|,
name|mode
argument_list|)
expr_stmt|;
name|int
name|idx
init|=
literal|0
decl_stmt|;
for|for
control|(
name|CourseAssignment
name|ca
range|:
name|sorted
control|)
block|{
name|int
name|p
init|=
name|mode
operator|.
name|getPoints
argument_list|(
name|ca
argument_list|)
decl_stmt|;
if|if
condition|(
name|p
operator|<=
literal|0
operator|||
name|idx
operator|>=
name|limit
condition|)
break|break;
name|ca
operator|.
name|setSelection
argument_list|(
name|idx
operator|++
argument_list|)
expr_stmt|;
block|}
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
literal|"list-courses"
return|;
block|}
block|}
end_class

end_unit

