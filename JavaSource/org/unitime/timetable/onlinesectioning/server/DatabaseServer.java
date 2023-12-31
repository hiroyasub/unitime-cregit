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
name|server
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
name|org
operator|.
name|cpsolver
operator|.
name|coursett
operator|.
name|constraint
operator|.
name|GroupConstraint
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
name|constraint
operator|.
name|IgnoreStudentConflictsConstraint
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
name|SectioningServlet
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
name|interfaces
operator|.
name|ExternalClassNameHelperInterface
operator|.
name|HasGradableSubpart
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
name|interfaces
operator|.
name|ExternalClassNameHelperInterface
operator|.
name|HasGradableSubpartCache
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
name|DistributionPref
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
name|InstructionalOffering
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
name|PreferenceLevel
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
name|Student
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
name|CourseOfferingDAO
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
name|AcademicSessionInfo
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
name|OnlineSectioningServerContext
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
name|match
operator|.
name|StudentMatcher
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
name|XExpectations
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
name|updates
operator|.
name|ReloadAllData
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|DatabaseServer
extends|extends
name|AbstractLockingServer
block|{
specifier|public
name|DatabaseServer
parameter_list|(
name|OnlineSectioningServerContext
name|context
parameter_list|)
throws|throws
name|SectioningException
block|{
name|super
argument_list|(
name|context
argument_list|)
expr_stmt|;
block|}
specifier|public
name|DatabaseServer
parameter_list|(
name|AcademicSessionInfo
name|session
parameter_list|,
name|boolean
name|allowAsyncCalls
parameter_list|)
block|{
name|super
argument_list|(
name|session
argument_list|,
name|allowAsyncCalls
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|protected
name|void
name|loadOnMaster
parameter_list|(
name|OnlineSectioningServerContext
name|context
parameter_list|)
throws|throws
name|SectioningException
block|{
name|setReady
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|Collection
argument_list|<
name|XCourseId
argument_list|>
name|findCourses
parameter_list|(
name|String
name|query
parameter_list|,
name|Integer
name|limit
parameter_list|,
name|CourseMatcher
name|matcher
parameter_list|)
block|{
if|if
condition|(
name|matcher
operator|!=
literal|null
condition|)
name|matcher
operator|.
name|setServer
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|Collection
argument_list|<
name|XCourseId
argument_list|>
name|ret
init|=
operator|new
name|ArrayList
argument_list|<
name|XCourseId
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|CourseOffering
name|c
range|:
operator|(
name|List
argument_list|<
name|CourseOffering
argument_list|>
operator|)
name|getCurrentHelper
argument_list|()
operator|.
name|getHibSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select c from CourseOffering c where "
operator|+
literal|"c.subjectArea.session.uniqueId = :sessionId and c.instructionalOffering.notOffered = false and ("
operator|+
literal|"(lower(c.subjectArea.subjectAreaAbbreviation || ' ' || c.courseNbr) like :q || '%' or lower(c.subjectArea.subjectAreaAbbreviation || ' ' || c.courseNbr || ' - ' || c.title) like :q || '%') "
operator|+
operator|(
name|query
operator|.
name|length
argument_list|()
operator|>
literal|2
condition|?
literal|"or lower(c.title) like '%' || :q || '%'"
else|:
literal|""
operator|)
operator|+
literal|") "
operator|+
literal|"order by case "
operator|+
literal|"when lower(c.subjectArea.subjectAreaAbbreviation || ' ' || c.courseNbr) like :q || '%' then 0 else 1 end,"
operator|+
comment|// matches on course name first
literal|"c.subjectArea.subjectAreaAbbreviation, c.courseNbr"
argument_list|)
operator|.
name|setString
argument_list|(
literal|"q"
argument_list|,
name|query
operator|.
name|toLowerCase
argument_list|()
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|getAcademicSession
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
control|)
block|{
name|XCourse
name|course
init|=
operator|new
name|XCourse
argument_list|(
name|c
argument_list|)
decl_stmt|;
if|if
condition|(
name|matcher
operator|==
literal|null
operator|||
name|matcher
operator|.
name|match
argument_list|(
name|course
argument_list|)
condition|)
name|ret
operator|.
name|add
argument_list|(
name|course
argument_list|)
expr_stmt|;
if|if
condition|(
name|limit
operator|!=
literal|null
operator|&&
name|limit
operator|>
literal|0
operator|&&
name|ret
operator|.
name|size
argument_list|()
operator|>=
name|limit
condition|)
break|break;
block|}
return|return
name|ret
return|;
block|}
annotation|@
name|Override
specifier|public
name|Collection
argument_list|<
name|XCourseId
argument_list|>
name|findCourses
parameter_list|(
name|CourseMatcher
name|matcher
parameter_list|)
block|{
if|if
condition|(
name|matcher
operator|!=
literal|null
condition|)
name|matcher
operator|.
name|setServer
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|Collection
argument_list|<
name|XCourseId
argument_list|>
name|ret
init|=
operator|new
name|ArrayList
argument_list|<
name|XCourseId
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|CourseOffering
name|c
range|:
operator|(
name|List
argument_list|<
name|CourseOffering
argument_list|>
operator|)
name|getCurrentHelper
argument_list|()
operator|.
name|getHibSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select c from CourseOffering c where "
operator|+
literal|"c.subjectArea.session.uniqueId = :sessionId and c.instructionalOffering.notOffered = false "
operator|+
literal|"order by c.subjectArea.subjectAreaAbbreviation, c.courseNbr"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|getAcademicSession
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
control|)
block|{
name|XCourse
name|course
init|=
operator|new
name|XCourse
argument_list|(
name|c
argument_list|)
decl_stmt|;
if|if
condition|(
name|matcher
operator|==
literal|null
operator|||
name|matcher
operator|.
name|match
argument_list|(
name|course
argument_list|)
condition|)
name|ret
operator|.
name|add
argument_list|(
name|course
argument_list|)
expr_stmt|;
block|}
return|return
name|ret
return|;
block|}
annotation|@
name|Override
specifier|public
name|Collection
argument_list|<
name|XStudent
argument_list|>
name|findStudents
parameter_list|(
name|StudentMatcher
name|matcher
parameter_list|)
block|{
if|if
condition|(
name|matcher
operator|!=
literal|null
condition|)
name|matcher
operator|.
name|setServer
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|Collection
argument_list|<
name|XStudent
argument_list|>
name|ret
init|=
operator|new
name|ArrayList
argument_list|<
name|XStudent
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Student
name|s
range|:
operator|(
name|List
argument_list|<
name|Student
argument_list|>
operator|)
name|getCurrentHelper
argument_list|()
operator|.
name|getHibSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select distinct s from Student s "
operator|+
literal|"left join fetch s.courseDemands as cd "
operator|+
literal|"left join fetch cd.courseRequests as cr "
operator|+
literal|"left join fetch cr.classWaitLists as cwl "
operator|+
literal|"left join fetch s.classEnrollments as e "
operator|+
literal|"left join fetch s.areaClasfMajors as acm "
operator|+
literal|"left join fetch s.waitlists as w "
operator|+
literal|"left join fetch s.groups as g "
operator|+
literal|"left join fetch s.notes as n "
operator|+
literal|"where s.session.uniqueId = :sessionId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|getAcademicSession
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
control|)
block|{
name|XStudent
name|student
init|=
operator|new
name|XStudent
argument_list|(
name|s
argument_list|,
name|getCurrentHelper
argument_list|()
argument_list|,
name|getAcademicSession
argument_list|()
operator|.
name|getFreeTimePattern
argument_list|()
argument_list|,
name|getAcademicSession
argument_list|()
operator|.
name|getDatePatternFirstDate
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|matcher
operator|==
literal|null
operator|||
name|matcher
operator|.
name|match
argument_list|(
name|student
argument_list|)
condition|)
name|ret
operator|.
name|add
argument_list|(
name|student
argument_list|)
expr_stmt|;
block|}
return|return
name|ret
return|;
block|}
annotation|@
name|Override
specifier|public
name|XCourse
name|getCourse
parameter_list|(
name|Long
name|courseId
parameter_list|)
block|{
name|CourseOffering
name|c
init|=
name|CourseOfferingDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|courseId
argument_list|,
name|getCurrentHelper
argument_list|()
operator|.
name|getHibSession
argument_list|()
argument_list|)
decl_stmt|;
return|return
name|c
operator|==
literal|null
operator|||
name|c
operator|.
name|getInstructionalOffering
argument_list|()
operator|.
name|isNotOffered
argument_list|()
condition|?
literal|null
else|:
operator|new
name|XCourse
argument_list|(
name|c
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|XCourseId
name|getCourse
parameter_list|(
name|String
name|course
parameter_list|)
block|{
name|CourseOffering
name|c
init|=
name|SectioningServlet
operator|.
name|lookupCourse
argument_list|(
name|getCurrentHelper
argument_list|()
operator|.
name|getHibSession
argument_list|()
argument_list|,
name|getAcademicSession
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|,
literal|null
argument_list|,
name|course
argument_list|,
literal|null
argument_list|)
decl_stmt|;
return|return
name|c
operator|==
literal|null
condition|?
literal|null
else|:
operator|new
name|XCourseId
argument_list|(
name|c
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|XStudent
name|getStudent
parameter_list|(
name|Long
name|studentId
parameter_list|)
block|{
name|Student
name|s
init|=
operator|(
name|Student
operator|)
name|getCurrentHelper
argument_list|()
operator|.
name|getHibSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select s from Student s "
operator|+
literal|"left join fetch s.courseDemands as cd "
operator|+
literal|"left join fetch cd.courseRequests as cr "
operator|+
literal|"left join fetch cd.freeTime as ft "
operator|+
literal|"left join fetch cr.courseOffering as co "
operator|+
literal|"left join fetch cr.courseRequestOptions as cro "
operator|+
literal|"left join fetch cr.classWaitLists as cwl "
operator|+
literal|"left join fetch s.classEnrollments as e "
operator|+
literal|"where s.uniqueId = :studentId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"studentId"
argument_list|,
name|studentId
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|uniqueResult
argument_list|()
decl_stmt|;
return|return
name|s
operator|==
literal|null
condition|?
literal|null
else|:
operator|new
name|XStudent
argument_list|(
name|s
argument_list|,
name|getCurrentHelper
argument_list|()
argument_list|,
name|getAcademicSession
argument_list|()
operator|.
name|getFreeTimePattern
argument_list|()
argument_list|,
name|getAcademicSession
argument_list|()
operator|.
name|getDatePatternFirstDate
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|XOffering
name|getOffering
parameter_list|(
name|Long
name|offeringId
parameter_list|)
block|{
name|Collection
argument_list|<
name|XDistribution
argument_list|>
name|distributions
init|=
operator|new
name|ArrayList
argument_list|<
name|XDistribution
argument_list|>
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|DistributionPref
argument_list|>
name|distPrefs
init|=
name|getCurrentHelper
argument_list|()
operator|.
name|getHibSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select distinct p from DistributionPref p inner join p.distributionObjects o, Department d, "
operator|+
literal|"Class_ c inner join c.schedulingSubpart.instrOfferingConfig.instructionalOffering io "
operator|+
literal|"where p.distributionType.reference in (:ref1, :ref2) and d.session.uniqueId = :sessionId "
operator|+
literal|"and io.uniqueId = :offeringId and (o.prefGroup = c or o.prefGroup = c.schedulingSubpart) "
operator|+
literal|"and p.owner = d and p.prefLevel.prefProlog = :pref"
argument_list|)
operator|.
name|setString
argument_list|(
literal|"ref1"
argument_list|,
name|GroupConstraint
operator|.
name|ConstraintType
operator|.
name|LINKED_SECTIONS
operator|.
name|reference
argument_list|()
argument_list|)
operator|.
name|setString
argument_list|(
literal|"ref2"
argument_list|,
name|IgnoreStudentConflictsConstraint
operator|.
name|REFERENCE
argument_list|)
operator|.
name|setString
argument_list|(
literal|"pref"
argument_list|,
name|PreferenceLevel
operator|.
name|sRequired
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|getAcademicSession
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"offeringId"
argument_list|,
name|offeringId
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|distPrefs
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
for|for
control|(
name|DistributionPref
name|pref
range|:
name|distPrefs
control|)
block|{
name|int
name|variant
init|=
literal|0
decl_stmt|;
for|for
control|(
name|Collection
argument_list|<
name|Class_
argument_list|>
name|sections
range|:
name|ReloadAllData
operator|.
name|getSections
argument_list|(
name|pref
argument_list|)
control|)
block|{
name|XDistributionType
name|type
init|=
name|XDistributionType
operator|.
name|IngoreConflicts
decl_stmt|;
if|if
condition|(
name|GroupConstraint
operator|.
name|ConstraintType
operator|.
name|LINKED_SECTIONS
operator|.
name|reference
argument_list|()
operator|.
name|equals
argument_list|(
name|pref
operator|.
name|getDistributionType
argument_list|()
operator|.
name|getReference
argument_list|()
argument_list|)
condition|)
name|type
operator|=
name|XDistributionType
operator|.
name|LinkedSections
expr_stmt|;
name|distributions
operator|.
name|add
argument_list|(
operator|new
name|XDistribution
argument_list|(
name|type
argument_list|,
name|pref
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|variant
operator|++
argument_list|,
name|sections
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
name|InstructionalOffering
name|o
init|=
operator|(
name|InstructionalOffering
operator|)
name|getCurrentHelper
argument_list|()
operator|.
name|getHibSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select io from InstructionalOffering io "
operator|+
literal|"left join fetch io.courseOfferings co "
operator|+
literal|"left join fetch io.instrOfferingConfigs cf "
operator|+
literal|"left join fetch cf.schedulingSubparts ss "
operator|+
literal|"left join fetch ss.classes c "
operator|+
literal|"left join fetch c.assignments a "
operator|+
literal|"left join fetch a.rooms r "
operator|+
literal|"left join fetch c.classInstructors i "
operator|+
literal|"left join fetch io.reservations x "
operator|+
literal|"left join fetch co.creditConfigs cc "
operator|+
literal|"left join fetch ss.creditConfigs sc "
operator|+
literal|"where io.uniqueId = :offeringId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"offeringId"
argument_list|,
name|offeringId
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|uniqueResult
argument_list|()
decl_stmt|;
if|if
condition|(
name|o
operator|==
literal|null
operator|||
operator|!
name|o
operator|.
name|isAllowStudentScheduling
argument_list|()
condition|)
return|return
literal|null
return|;
if|if
condition|(
name|ApplicationProperty
operator|.
name|OnlineSchedulingGradableIType
operator|.
name|isTrue
argument_list|()
operator|&&
name|Class_
operator|.
name|getExternalClassNameHelper
argument_list|()
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|Class_
operator|.
name|getExternalClassNameHelper
argument_list|()
operator|instanceof
name|HasGradableSubpartCache
condition|)
block|{
name|List
argument_list|<
name|Long
argument_list|>
name|offeringIds
init|=
operator|new
name|ArrayList
argument_list|<
name|Long
argument_list|>
argument_list|(
literal|1
argument_list|)
decl_stmt|;
name|offeringIds
operator|.
name|add
argument_list|(
name|offeringId
argument_list|)
expr_stmt|;
name|getCurrentHelper
argument_list|()
operator|.
name|setGradableSubpartsProvider
argument_list|(
operator|(
operator|(
name|HasGradableSubpartCache
operator|)
name|Class_
operator|.
name|getExternalClassNameHelper
argument_list|()
operator|)
operator|.
name|getGradableSubparts
argument_list|(
name|offeringIds
argument_list|,
name|getCurrentHelper
argument_list|()
operator|.
name|getHibSession
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|Class_
operator|.
name|getExternalClassNameHelper
argument_list|()
operator|instanceof
name|HasGradableSubpart
condition|)
block|{
name|getCurrentHelper
argument_list|()
operator|.
name|setGradableSubpartsProvider
argument_list|(
operator|(
name|HasGradableSubpart
operator|)
name|Class_
operator|.
name|getExternalClassNameHelper
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
return|return
operator|new
name|XOffering
argument_list|(
name|o
argument_list|,
name|distributions
argument_list|,
name|getCurrentHelper
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Collection
argument_list|<
name|XCourseRequest
argument_list|>
name|getRequests
parameter_list|(
name|Long
name|offeringId
parameter_list|)
block|{
name|Collection
argument_list|<
name|XCourseRequest
argument_list|>
name|ret
init|=
operator|new
name|ArrayList
argument_list|<
name|XCourseRequest
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|CourseDemand
name|d
range|:
operator|(
name|List
argument_list|<
name|CourseDemand
argument_list|>
operator|)
name|getCurrentHelper
argument_list|()
operator|.
name|getHibSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select distinct cd from CourseRequest r inner join r.courseDemand cd "
operator|+
literal|"left join fetch cd.courseRequests as cr "
operator|+
literal|"left join fetch cr.classWaitLists as cwl "
operator|+
literal|"left join fetch cd.student as s "
operator|+
literal|"left join fetch s.classEnrollments as e "
operator|+
literal|"where r.courseOffering.instructionalOffering = :offeringId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"offeringId"
argument_list|,
name|offeringId
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
control|)
block|{
name|ret
operator|.
name|add
argument_list|(
operator|new
name|XCourseRequest
argument_list|(
name|d
argument_list|,
name|getCurrentHelper
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|ret
return|;
block|}
annotation|@
name|Override
specifier|public
name|XExpectations
name|getExpectations
parameter_list|(
name|Long
name|offeringId
parameter_list|)
block|{
name|Map
argument_list|<
name|Long
argument_list|,
name|Double
argument_list|>
name|expectations
init|=
operator|new
name|HashMap
argument_list|<
name|Long
argument_list|,
name|Double
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Object
index|[]
name|info
range|:
operator|(
name|List
argument_list|<
name|Object
index|[]
argument_list|>
operator|)
name|getCurrentHelper
argument_list|()
operator|.
name|getHibSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select i.clazz.uniqueId, i.nbrExpectedStudents from SectioningInfo i where i.clazz.schedulingSubpart.instrOfferingConfig.instructionalOffering = :offeringId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"offeringId"
argument_list|,
name|offeringId
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
control|)
block|{
name|expectations
operator|.
name|put
argument_list|(
operator|(
name|Long
operator|)
name|info
index|[
literal|0
index|]
argument_list|,
operator|(
name|Double
operator|)
name|info
index|[
literal|1
index|]
argument_list|)
expr_stmt|;
block|}
return|return
operator|new
name|XExpectations
argument_list|(
name|offeringId
argument_list|,
name|expectations
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|update
parameter_list|(
name|XExpectations
name|expectations
parameter_list|)
block|{
block|}
annotation|@
name|Override
specifier|public
name|void
name|remove
parameter_list|(
name|XStudent
name|student
parameter_list|)
block|{
block|}
annotation|@
name|Override
specifier|public
name|void
name|update
parameter_list|(
name|XStudent
name|student
parameter_list|,
name|boolean
name|updateRequests
parameter_list|)
block|{
block|}
annotation|@
name|Override
specifier|public
name|void
name|remove
parameter_list|(
name|XOffering
name|offering
parameter_list|)
block|{
block|}
annotation|@
name|Override
specifier|public
name|void
name|update
parameter_list|(
name|XOffering
name|offering
parameter_list|)
block|{
block|}
annotation|@
name|Override
specifier|public
name|void
name|clearAll
parameter_list|()
block|{
block|}
annotation|@
name|Override
specifier|public
name|void
name|clearAllStudents
parameter_list|()
block|{
block|}
annotation|@
name|Override
specifier|public
name|XCourseRequest
name|assign
parameter_list|(
name|XCourseRequest
name|request
parameter_list|,
name|XEnrollment
name|enrollment
parameter_list|)
block|{
name|request
operator|.
name|setEnrollment
argument_list|(
name|enrollment
argument_list|)
expr_stmt|;
return|return
name|request
return|;
block|}
annotation|@
name|Override
specifier|public
name|XCourseRequest
name|waitlist
parameter_list|(
name|XCourseRequest
name|request
parameter_list|,
name|boolean
name|waitlist
parameter_list|)
block|{
name|request
operator|.
name|setWaitlist
argument_list|(
name|waitlist
argument_list|)
expr_stmt|;
return|return
name|request
return|;
block|}
annotation|@
name|Override
specifier|public
name|Collection
argument_list|<
name|Long
argument_list|>
name|getInstructedOfferings
parameter_list|(
name|String
name|instructorExternalId
parameter_list|)
block|{
return|return
operator|(
name|List
argument_list|<
name|Long
argument_list|>
operator|)
name|getCurrentHelper
argument_list|()
operator|.
name|getHibSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select distinct ci.classInstructing.schedulingSubpart.instrOfferingConfig.instructionalOffering.uniqueId "
operator|+
literal|"from ClassInstructor ci "
operator|+
literal|"where ci.instructor.externalUniqueId = :instructorExternalId and ci.instructor.department.session.uniqueId = :sessionId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|getAcademicSession
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|setString
argument_list|(
literal|"instructorExternalId"
argument_list|,
name|instructorExternalId
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|Set
argument_list|<
name|Long
argument_list|>
name|getRequestedCourseIds
parameter_list|(
name|Long
name|studentId
parameter_list|)
block|{
return|return
operator|new
name|HashSet
argument_list|<
name|Long
argument_list|>
argument_list|(
name|getCurrentHelper
argument_list|()
operator|.
name|getHibSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select cr.courseOffering.uniqueId from CourseRequest cr where cr.courseDemand.student.uniqueId = :studentId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"studentId"
argument_list|,
name|studentId
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
argument_list|)
return|;
block|}
block|}
end_class

end_unit

