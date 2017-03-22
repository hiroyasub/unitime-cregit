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
name|solver
operator|.
name|curricula
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
name|Set
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
name|ifs
operator|.
name|util
operator|.
name|Progress
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
name|Session
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
name|SubjectArea
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|StudentCourseRequests
implements|implements
name|StudentCourseDemands
block|{
specifier|protected
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|Set
argument_list|<
name|WeightedStudentId
argument_list|>
argument_list|>
argument_list|>
name|iDemands
init|=
operator|new
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|Set
argument_list|<
name|WeightedStudentId
argument_list|>
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
specifier|protected
name|org
operator|.
name|hibernate
operator|.
name|Session
name|iHibSession
init|=
literal|null
decl_stmt|;
specifier|protected
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|Set
argument_list|<
name|WeightedCourseOffering
argument_list|>
argument_list|>
name|iStudentRequests
init|=
literal|null
decl_stmt|;
specifier|protected
name|Long
name|iSessionId
init|=
literal|null
decl_stmt|;
specifier|protected
name|double
name|iBasePriorityWeight
init|=
literal|0.9
decl_stmt|;
specifier|private
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|Double
argument_list|>
argument_list|>
name|iEnrollmentPriorities
init|=
operator|new
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|Double
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
specifier|public
name|StudentCourseRequests
parameter_list|(
name|DataProperties
name|conf
parameter_list|)
block|{
name|iBasePriorityWeight
operator|=
name|conf
operator|.
name|getPropertyDouble
argument_list|(
literal|"StudentCourseRequests.BasePriorityWeight"
argument_list|,
name|iBasePriorityWeight
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|init
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|Progress
name|progress
parameter_list|,
name|Session
name|session
parameter_list|,
name|Collection
argument_list|<
name|InstructionalOffering
argument_list|>
name|offerings
parameter_list|)
block|{
name|iHibSession
operator|=
name|hibSession
expr_stmt|;
name|iSessionId
operator|=
name|session
operator|.
name|getUniqueId
argument_list|()
expr_stmt|;
block|}
specifier|protected
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|Set
argument_list|<
name|WeightedStudentId
argument_list|>
argument_list|>
name|loadDemandsForSubjectArea
parameter_list|(
name|SubjectArea
name|subjectArea
parameter_list|)
block|{
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|Set
argument_list|<
name|WeightedStudentId
argument_list|>
argument_list|>
name|demands
init|=
operator|new
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|Set
argument_list|<
name|WeightedStudentId
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Object
index|[]
name|o
range|:
operator|(
name|List
argument_list|<
name|Object
index|[]
argument_list|>
operator|)
name|iHibSession
operator|.
name|createQuery
argument_list|(
literal|"select distinct s, r.courseOffering.uniqueId, r.courseDemand.priority, r.courseDemand.alternative, r.order from "
operator|+
literal|"CourseRequest r inner join r.courseDemand.student s left join fetch s.areaClasfMajors where "
operator|+
literal|"r.courseOffering.subjectArea.uniqueId = :subjectId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"subjectId"
argument_list|,
name|subjectArea
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
name|Student
name|s
init|=
operator|(
name|Student
operator|)
name|o
index|[
literal|0
index|]
decl_stmt|;
name|Long
name|courseId
init|=
operator|(
name|Long
operator|)
name|o
index|[
literal|1
index|]
decl_stmt|;
name|Integer
name|priority
init|=
operator|(
name|Integer
operator|)
name|o
index|[
literal|2
index|]
decl_stmt|;
name|Boolean
name|alternative
init|=
operator|(
name|Boolean
operator|)
name|o
index|[
literal|3
index|]
decl_stmt|;
name|Integer
name|order
init|=
operator|(
name|Integer
operator|)
name|o
index|[
literal|4
index|]
decl_stmt|;
name|Set
argument_list|<
name|WeightedStudentId
argument_list|>
name|students
init|=
name|demands
operator|.
name|get
argument_list|(
name|courseId
argument_list|)
decl_stmt|;
if|if
condition|(
name|students
operator|==
literal|null
condition|)
block|{
name|students
operator|=
operator|new
name|HashSet
argument_list|<
name|WeightedStudentId
argument_list|>
argument_list|()
expr_stmt|;
name|demands
operator|.
name|put
argument_list|(
name|courseId
argument_list|,
name|students
argument_list|)
expr_stmt|;
block|}
name|WeightedStudentId
name|student
init|=
operator|new
name|WeightedStudentId
argument_list|(
name|s
argument_list|)
decl_stmt|;
name|students
operator|.
name|add
argument_list|(
name|student
argument_list|)
expr_stmt|;
if|if
condition|(
name|priority
operator|!=
literal|null
operator|&&
name|Boolean
operator|.
name|FALSE
operator|.
name|equals
argument_list|(
name|alternative
argument_list|)
condition|)
block|{
if|if
condition|(
name|order
operator|!=
literal|null
condition|)
name|priority
operator|+=
name|order
expr_stmt|;
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|Double
argument_list|>
name|priorities
init|=
name|iEnrollmentPriorities
operator|.
name|get
argument_list|(
name|s
operator|.
name|getUniqueId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|priorities
operator|==
literal|null
condition|)
block|{
name|priorities
operator|=
operator|new
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|Double
argument_list|>
argument_list|()
expr_stmt|;
name|iEnrollmentPriorities
operator|.
name|put
argument_list|(
name|s
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|priorities
argument_list|)
expr_stmt|;
block|}
name|priorities
operator|.
name|put
argument_list|(
name|courseId
argument_list|,
name|Math
operator|.
name|pow
argument_list|(
name|iBasePriorityWeight
argument_list|,
name|priority
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|demands
return|;
block|}
annotation|@
name|Override
specifier|public
name|Set
argument_list|<
name|WeightedCourseOffering
argument_list|>
name|getCourses
parameter_list|(
name|Long
name|studentId
parameter_list|)
block|{
if|if
condition|(
name|iStudentRequests
operator|==
literal|null
condition|)
block|{
name|iStudentRequests
operator|=
operator|new
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|Set
argument_list|<
name|WeightedCourseOffering
argument_list|>
argument_list|>
argument_list|()
expr_stmt|;
for|for
control|(
name|Object
index|[]
name|o
range|:
operator|(
name|List
argument_list|<
name|Object
index|[]
argument_list|>
operator|)
name|iHibSession
operator|.
name|createQuery
argument_list|(
literal|"select distinct d.student.uniqueId, c, d.priority, d.alternative, r.order "
operator|+
literal|"from CourseRequest r inner join r.courseOffering c inner join r.courseDemand d where d.student.session.uniqueId = :sessionId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|iSessionId
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
name|Long
name|sid
init|=
operator|(
name|Long
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
name|Integer
name|priority
init|=
operator|(
name|Integer
operator|)
name|o
index|[
literal|2
index|]
decl_stmt|;
name|Boolean
name|alternative
init|=
operator|(
name|Boolean
operator|)
name|o
index|[
literal|3
index|]
decl_stmt|;
name|Integer
name|order
init|=
operator|(
name|Integer
operator|)
name|o
index|[
literal|4
index|]
decl_stmt|;
name|Set
argument_list|<
name|WeightedCourseOffering
argument_list|>
name|courses
init|=
name|iStudentRequests
operator|.
name|get
argument_list|(
name|sid
argument_list|)
decl_stmt|;
if|if
condition|(
name|courses
operator|==
literal|null
condition|)
block|{
name|courses
operator|=
operator|new
name|HashSet
argument_list|<
name|WeightedCourseOffering
argument_list|>
argument_list|()
expr_stmt|;
name|iStudentRequests
operator|.
name|put
argument_list|(
name|sid
argument_list|,
name|courses
argument_list|)
expr_stmt|;
block|}
name|courses
operator|.
name|add
argument_list|(
operator|new
name|WeightedCourseOffering
argument_list|(
name|co
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|priority
operator|!=
literal|null
operator|&&
name|Boolean
operator|.
name|FALSE
operator|.
name|equals
argument_list|(
name|alternative
argument_list|)
condition|)
block|{
if|if
condition|(
name|order
operator|!=
literal|null
condition|)
name|priority
operator|+=
name|order
expr_stmt|;
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|Double
argument_list|>
name|priorities
init|=
name|iEnrollmentPriorities
operator|.
name|get
argument_list|(
name|studentId
argument_list|)
decl_stmt|;
if|if
condition|(
name|priorities
operator|==
literal|null
condition|)
block|{
name|priorities
operator|=
operator|new
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|Double
argument_list|>
argument_list|()
expr_stmt|;
name|iEnrollmentPriorities
operator|.
name|put
argument_list|(
name|studentId
argument_list|,
name|priorities
argument_list|)
expr_stmt|;
block|}
name|priorities
operator|.
name|put
argument_list|(
name|co
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|Math
operator|.
name|pow
argument_list|(
name|iBasePriorityWeight
argument_list|,
name|priority
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
return|return
name|iStudentRequests
operator|.
name|get
argument_list|(
name|studentId
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Set
argument_list|<
name|WeightedStudentId
argument_list|>
name|getDemands
parameter_list|(
name|CourseOffering
name|course
parameter_list|)
block|{
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|Set
argument_list|<
name|WeightedStudentId
argument_list|>
argument_list|>
name|demands
init|=
name|iDemands
operator|.
name|get
argument_list|(
name|course
operator|.
name|getSubjectArea
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|demands
operator|==
literal|null
condition|)
block|{
name|demands
operator|=
name|loadDemandsForSubjectArea
argument_list|(
name|course
operator|.
name|getSubjectArea
argument_list|()
argument_list|)
expr_stmt|;
name|iDemands
operator|.
name|put
argument_list|(
name|course
operator|.
name|getSubjectArea
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|demands
argument_list|)
expr_stmt|;
block|}
return|return
name|demands
operator|.
name|get
argument_list|(
name|course
operator|.
name|getUniqueId
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isMakingUpStudents
parameter_list|()
block|{
return|return
literal|false
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isWeightStudentsToFillUpOffering
parameter_list|()
block|{
return|return
literal|false
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|canUseStudentClassEnrollmentsAsSolution
parameter_list|()
block|{
return|return
literal|true
return|;
block|}
annotation|@
name|Override
specifier|public
name|Double
name|getEnrollmentPriority
parameter_list|(
name|Long
name|studentId
parameter_list|,
name|Long
name|courseId
parameter_list|)
block|{
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|Double
argument_list|>
name|priorities
init|=
name|iEnrollmentPriorities
operator|.
name|get
argument_list|(
name|studentId
argument_list|)
decl_stmt|;
return|return
operator|(
name|priorities
operator|==
literal|null
condition|?
literal|null
else|:
name|priorities
operator|.
name|get
argument_list|(
name|courseId
argument_list|)
operator|)
return|;
block|}
block|}
end_class

end_unit

