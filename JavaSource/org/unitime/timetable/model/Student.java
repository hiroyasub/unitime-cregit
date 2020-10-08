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
name|model
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|Serializable
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Date
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
name|Set
import|;
end_import

begin_import
import|import
name|org
operator|.
name|hibernate
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
name|model
operator|.
name|CourseRequest
operator|.
name|CourseRequestOverrideStatus
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
name|base
operator|.
name|BaseStudent
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
name|LocationDAO
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
name|security
operator|.
name|Qualifiable
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
name|NameFormat
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
name|NameInterface
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller, Stephanie Schluttenhofer  */
end_comment

begin_class
specifier|public
class|class
name|Student
extends|extends
name|BaseStudent
implements|implements
name|Comparable
argument_list|<
name|Student
argument_list|>
implements|,
name|NameInterface
implements|,
name|Qualifiable
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
comment|/*[CONSTRUCTOR MARKER BEGIN]*/
specifier|public
name|Student
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|Student
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Long
name|uniqueId
parameter_list|)
block|{
name|super
argument_list|(
name|uniqueId
argument_list|)
expr_stmt|;
block|}
comment|/*[CONSTRUCTOR MARKER END]*/
specifier|public
specifier|static
name|List
name|findAll
parameter_list|(
name|Long
name|sessionId
parameter_list|)
block|{
return|return
operator|new
name|StudentDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select s from Student s where s.session.uniqueId=:sessionId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
operator|.
name|longValue
argument_list|()
argument_list|)
operator|.
name|list
argument_list|()
return|;
block|}
specifier|public
specifier|static
name|Student
name|findByExternalId
parameter_list|(
name|Long
name|sessionId
parameter_list|,
name|String
name|externalId
parameter_list|)
block|{
return|return
operator|(
name|Student
operator|)
operator|new
name|StudentDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select s from Student s where "
operator|+
literal|"s.session.uniqueId=:sessionId and "
operator|+
literal|"s.externalUniqueId=:externalId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
operator|.
name|longValue
argument_list|()
argument_list|)
operator|.
name|setString
argument_list|(
literal|"externalId"
argument_list|,
name|externalId
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|uniqueResult
argument_list|()
return|;
block|}
specifier|public
specifier|static
name|Student
name|findByExternalIdBringBackEnrollments
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|Long
name|sessionId
parameter_list|,
name|String
name|externalId
parameter_list|)
block|{
return|return
operator|(
name|Student
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"select s from Student s "
operator|+
literal|"left join fetch s.courseDemands as cd "
operator|+
literal|"left join fetch cd.courseRequests as cr "
operator|+
literal|"left join fetch s.classEnrollments as e "
operator|+
literal|"left join fetch s.areaClasfMajors "
operator|+
literal|"left join fetch s.areaClasfMinors "
operator|+
literal|"where "
operator|+
literal|"s.session.uniqueId=:sessionId and "
operator|+
literal|"s.externalUniqueId=:externalId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
operator|.
name|longValue
argument_list|()
argument_list|)
operator|.
name|setString
argument_list|(
literal|"externalId"
argument_list|,
name|externalId
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|uniqueResult
argument_list|()
return|;
block|}
specifier|public
name|void
name|removeAllEnrollments
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|)
block|{
name|HashSet
argument_list|<
name|StudentClassEnrollment
argument_list|>
name|enrollments
init|=
operator|new
name|HashSet
argument_list|<
name|StudentClassEnrollment
argument_list|>
argument_list|()
decl_stmt|;
if|if
condition|(
name|getClassEnrollments
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|enrollments
operator|.
name|addAll
argument_list|(
name|getClassEnrollments
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|enrollments
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
for|for
control|(
name|StudentClassEnrollment
name|enrollment
range|:
name|enrollments
control|)
block|{
name|getClassEnrollments
argument_list|()
operator|.
name|remove
argument_list|(
name|enrollment
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|delete
argument_list|(
name|enrollment
argument_list|)
expr_stmt|;
block|}
block|}
block|}
annotation|@
name|Deprecated
specifier|public
name|Set
argument_list|<
name|Exam
argument_list|>
name|getExams
parameter_list|(
name|Integer
name|examType
parameter_list|)
block|{
name|HashSet
name|exams
init|=
operator|new
name|HashSet
argument_list|()
decl_stmt|;
name|exams
operator|.
name|addAll
argument_list|(
operator|new
name|StudentDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select distinct o.exam from ExamOwner o, StudentClassEnrollment e "
operator|+
literal|"where e.student.uniqueId=:studentId and o.ownerType=:ownerType and o.ownerId=e.clazz.uniqueId and o.exam.examType.type=:examType"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"studentId"
argument_list|,
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|setInteger
argument_list|(
literal|"ownerType"
argument_list|,
name|ExamOwner
operator|.
name|sOwnerTypeClass
argument_list|)
operator|.
name|setInteger
argument_list|(
literal|"examType"
argument_list|,
name|examType
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
expr_stmt|;
name|exams
operator|.
name|addAll
argument_list|(
operator|new
name|StudentDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select distinct o.exam from ExamOwner o, StudentClassEnrollment e "
operator|+
literal|"where e.student.uniqueId=:studentId and o.ownerType=:ownerType and o.ownerId=e.clazz.schedulingSubpart.instrOfferingConfig.uniqueId and o.exam.examType.type=:examType"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"studentId"
argument_list|,
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|setInteger
argument_list|(
literal|"ownerType"
argument_list|,
name|ExamOwner
operator|.
name|sOwnerTypeConfig
argument_list|)
operator|.
name|setInteger
argument_list|(
literal|"examType"
argument_list|,
name|examType
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
expr_stmt|;
name|exams
operator|.
name|addAll
argument_list|(
operator|new
name|StudentDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select distinct o.exam from ExamOwner o, StudentClassEnrollment e "
operator|+
literal|"where e.student.uniqueId=:studentId and o.ownerType=:ownerType and o.ownerId=e.courseOffering.uniqueId and o.exam.examType.type=:examType"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"studentId"
argument_list|,
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|setInteger
argument_list|(
literal|"ownerType"
argument_list|,
name|ExamOwner
operator|.
name|sOwnerTypeCourse
argument_list|)
operator|.
name|setInteger
argument_list|(
literal|"examType"
argument_list|,
name|examType
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
expr_stmt|;
name|exams
operator|.
name|addAll
argument_list|(
operator|new
name|StudentDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select distinct o.exam from ExamOwner o, StudentClassEnrollment e "
operator|+
literal|"where e.student.uniqueId=:studentId and o.ownerType=:ownerType and o.ownerId=e.courseOffering.instructionalOffering.uniqueId and o.exam.examType.type=:examType"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"studentId"
argument_list|,
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|setInteger
argument_list|(
literal|"ownerType"
argument_list|,
name|ExamOwner
operator|.
name|sOwnerTypeOffering
argument_list|)
operator|.
name|setInteger
argument_list|(
literal|"examType"
argument_list|,
name|examType
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
expr_stmt|;
return|return
name|exams
return|;
block|}
specifier|public
name|Set
argument_list|<
name|Exam
argument_list|>
name|getExams
parameter_list|(
name|ExamType
name|examType
parameter_list|)
block|{
name|HashSet
name|exams
init|=
operator|new
name|HashSet
argument_list|()
decl_stmt|;
name|exams
operator|.
name|addAll
argument_list|(
operator|new
name|StudentDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select distinct o.exam from ExamOwner o, StudentClassEnrollment e "
operator|+
literal|"where e.student.uniqueId=:studentId and o.ownerType=:ownerType and o.ownerId=e.clazz.uniqueId and o.exam.examType.uniqueId=:examType"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"studentId"
argument_list|,
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|setInteger
argument_list|(
literal|"ownerType"
argument_list|,
name|ExamOwner
operator|.
name|sOwnerTypeClass
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"examType"
argument_list|,
name|examType
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
argument_list|)
expr_stmt|;
name|exams
operator|.
name|addAll
argument_list|(
operator|new
name|StudentDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select distinct o.exam from ExamOwner o, StudentClassEnrollment e "
operator|+
literal|"where e.student.uniqueId=:studentId and o.ownerType=:ownerType and o.ownerId=e.clazz.schedulingSubpart.instrOfferingConfig.uniqueId and o.exam.examType.uniqueId=:examType"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"studentId"
argument_list|,
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|setInteger
argument_list|(
literal|"ownerType"
argument_list|,
name|ExamOwner
operator|.
name|sOwnerTypeConfig
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"examType"
argument_list|,
name|examType
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
argument_list|)
expr_stmt|;
name|exams
operator|.
name|addAll
argument_list|(
operator|new
name|StudentDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select distinct o.exam from ExamOwner o, StudentClassEnrollment e "
operator|+
literal|"where e.student.uniqueId=:studentId and o.ownerType=:ownerType and o.ownerId=e.courseOffering.uniqueId and o.exam.examType.uniqueId=:examType"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"studentId"
argument_list|,
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|setInteger
argument_list|(
literal|"ownerType"
argument_list|,
name|ExamOwner
operator|.
name|sOwnerTypeCourse
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"examType"
argument_list|,
name|examType
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
argument_list|)
expr_stmt|;
name|exams
operator|.
name|addAll
argument_list|(
operator|new
name|StudentDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select distinct o.exam from ExamOwner o, StudentClassEnrollment e "
operator|+
literal|"where e.student.uniqueId=:studentId and o.ownerType=:ownerType and o.ownerId=e.courseOffering.instructionalOffering.uniqueId and o.exam.examType.uniqueId=:examType"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"studentId"
argument_list|,
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|setInteger
argument_list|(
literal|"ownerType"
argument_list|,
name|ExamOwner
operator|.
name|sOwnerTypeOffering
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"examType"
argument_list|,
name|examType
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
argument_list|)
expr_stmt|;
return|return
name|exams
return|;
block|}
specifier|public
name|String
name|getName
parameter_list|(
name|String
name|instructorNameFormat
parameter_list|)
block|{
return|return
name|NameFormat
operator|.
name|fromReference
argument_list|(
name|instructorNameFormat
argument_list|)
operator|.
name|format
argument_list|(
name|this
argument_list|)
return|;
block|}
specifier|public
name|int
name|compareTo
parameter_list|(
name|Student
name|student
parameter_list|)
block|{
name|int
name|cmp
init|=
name|NameFormat
operator|.
name|LAST_FIRST
operator|.
name|format
argument_list|(
name|this
argument_list|)
operator|.
name|compareTo
argument_list|(
name|NameFormat
operator|.
name|LAST_FIRST
operator|.
name|format
argument_list|(
name|student
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|cmp
operator|!=
literal|0
condition|)
return|return
name|cmp
return|;
return|return
operator|(
name|getUniqueId
argument_list|()
operator|==
literal|null
condition|?
operator|new
name|Long
argument_list|(
operator|-
literal|1
argument_list|)
else|:
name|getUniqueId
argument_list|()
operator|)
operator|.
name|compareTo
argument_list|(
name|student
operator|.
name|getUniqueId
argument_list|()
operator|==
literal|null
condition|?
operator|-
literal|1
else|:
name|student
operator|.
name|getUniqueId
argument_list|()
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|Set
argument_list|<
name|Long
argument_list|>
argument_list|>
name|findConflictingStudents
parameter_list|(
name|Long
name|classId
parameter_list|,
name|int
name|startSlot
parameter_list|,
name|int
name|length
parameter_list|,
name|List
argument_list|<
name|Date
argument_list|>
name|dates
parameter_list|)
block|{
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|Set
argument_list|<
name|Long
argument_list|>
argument_list|>
name|table
init|=
operator|new
name|Hashtable
argument_list|()
decl_stmt|;
if|if
condition|(
name|dates
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
name|table
return|;
name|String
name|datesStr
init|=
literal|""
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|dates
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|i
operator|>
literal|0
condition|)
name|datesStr
operator|+=
literal|", "
expr_stmt|;
name|datesStr
operator|+=
literal|":date"
operator|+
name|i
expr_stmt|;
block|}
name|Query
name|q
init|=
name|LocationDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select distinct e.clazz.uniqueId, e.student.uniqueId "
operator|+
literal|"from StudentClassEnrollment e, ClassEvent c inner join c.meetings m, StudentClassEnrollment x "
operator|+
literal|"where x.clazz.uniqueId=:classId and x.student=e.student and "
operator|+
comment|// only look among students of the given class
literal|"e.clazz=c.clazz and "
operator|+
comment|// link ClassEvent c with StudentClassEnrollment e
literal|"m.stopPeriod>:startSlot and :endSlot>m.startPeriod and "
operator|+
comment|// meeting time within given time period
literal|"m.meetingDate in ("
operator|+
name|datesStr
operator|+
literal|") and m.approvalStatus = 1"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"classId"
argument_list|,
name|classId
argument_list|)
operator|.
name|setInteger
argument_list|(
literal|"startSlot"
argument_list|,
name|startSlot
argument_list|)
operator|.
name|setInteger
argument_list|(
literal|"endSlot"
argument_list|,
name|startSlot
operator|+
name|length
argument_list|)
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|dates
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|q
operator|.
name|setDate
argument_list|(
literal|"date"
operator|+
name|i
argument_list|,
name|dates
operator|.
name|get
argument_list|(
name|i
argument_list|)
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|Iterator
name|i
init|=
name|q
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
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
name|Set
argument_list|<
name|Long
argument_list|>
name|set
init|=
name|table
operator|.
name|get
argument_list|(
operator|(
name|Long
operator|)
name|o
index|[
literal|0
index|]
argument_list|)
decl_stmt|;
if|if
condition|(
name|set
operator|==
literal|null
condition|)
block|{
name|set
operator|=
operator|new
name|HashSet
argument_list|<
name|Long
argument_list|>
argument_list|()
expr_stmt|;
name|table
operator|.
name|put
argument_list|(
operator|(
name|Long
operator|)
name|o
index|[
literal|0
index|]
argument_list|,
name|set
argument_list|)
expr_stmt|;
block|}
name|set
operator|.
name|add
argument_list|(
operator|(
name|Long
operator|)
name|o
index|[
literal|1
index|]
argument_list|)
expr_stmt|;
block|}
return|return
name|table
return|;
block|}
specifier|public
name|boolean
name|hasSectioningStatusOption
parameter_list|(
name|StudentSectioningStatus
operator|.
name|Option
name|option
parameter_list|)
block|{
name|StudentSectioningStatus
name|status
init|=
name|getEffectiveStatus
argument_list|()
decl_stmt|;
return|return
name|status
operator|!=
literal|null
operator|&&
name|status
operator|.
name|hasOption
argument_list|(
name|option
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getAcademicTitle
parameter_list|()
block|{
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|Serializable
name|getQualifierId
parameter_list|()
block|{
return|return
name|getUniqueId
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getQualifierType
parameter_list|()
block|{
return|return
name|getClass
argument_list|()
operator|.
name|getSimpleName
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getQualifierReference
parameter_list|()
block|{
return|return
name|getExternalUniqueId
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getQualifierLabel
parameter_list|()
block|{
return|return
name|NameFormat
operator|.
name|LAST_FIRST_MIDDLE
operator|.
name|format
argument_list|(
name|this
argument_list|)
return|;
block|}
specifier|public
name|CourseRequestOverrideStatus
name|getMaxCreditOverrideStatus
parameter_list|()
block|{
if|if
condition|(
name|getOverrideStatus
argument_list|()
operator|==
literal|null
condition|)
return|return
name|CourseRequestOverrideStatus
operator|.
name|APPROVED
return|;
return|return
name|CourseRequestOverrideStatus
operator|.
name|values
argument_list|()
index|[
name|getOverrideStatus
argument_list|()
index|]
return|;
block|}
specifier|public
name|void
name|setMaxCreditOverrideStatus
parameter_list|(
name|CourseRequestOverrideStatus
name|status
parameter_list|)
block|{
name|setOverrideStatus
argument_list|(
name|status
operator|==
literal|null
condition|?
literal|null
else|:
operator|new
name|Integer
argument_list|(
name|status
operator|.
name|ordinal
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
specifier|public
name|boolean
name|isRequestApproved
parameter_list|()
block|{
return|return
name|getOverrideStatus
argument_list|()
operator|==
literal|null
operator|||
name|getOverrideStatus
argument_list|()
operator|.
name|intValue
argument_list|()
operator|==
name|CourseRequestOverrideStatus
operator|.
name|APPROVED
operator|.
name|ordinal
argument_list|()
return|;
block|}
specifier|public
name|boolean
name|isRequestPending
parameter_list|()
block|{
return|return
name|getOverrideStatus
argument_list|()
operator|!=
literal|null
operator|&&
name|getOverrideStatus
argument_list|()
operator|.
name|intValue
argument_list|()
operator|==
name|CourseRequestOverrideStatus
operator|.
name|PENDING
operator|.
name|ordinal
argument_list|()
return|;
block|}
specifier|public
name|boolean
name|isRequestCancelled
parameter_list|()
block|{
return|return
name|getOverrideStatus
argument_list|()
operator|!=
literal|null
operator|&&
name|getOverrideStatus
argument_list|()
operator|.
name|intValue
argument_list|()
operator|==
name|CourseRequestOverrideStatus
operator|.
name|CANCELLED
operator|.
name|ordinal
argument_list|()
return|;
block|}
specifier|public
name|boolean
name|isRequestRejected
parameter_list|()
block|{
return|return
name|getOverrideStatus
argument_list|()
operator|!=
literal|null
operator|&&
name|getOverrideStatus
argument_list|()
operator|.
name|intValue
argument_list|()
operator|==
name|CourseRequestOverrideStatus
operator|.
name|REJECTED
operator|.
name|ordinal
argument_list|()
return|;
block|}
specifier|public
name|StudentSectioningStatus
name|getEffectiveStatus
parameter_list|()
block|{
if|if
condition|(
name|getSectioningStatus
argument_list|()
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|getSectioningStatus
argument_list|()
operator|.
name|isEffectiveNow
argument_list|()
condition|)
return|return
name|getSectioningStatus
argument_list|()
return|;
name|StudentSectioningStatus
name|fallback
init|=
name|getSectioningStatus
argument_list|()
operator|.
name|getFallBackStatus
argument_list|()
decl_stmt|;
name|int
name|depth
init|=
literal|10
decl_stmt|;
while|while
condition|(
name|fallback
operator|!=
literal|null
operator|&&
name|depth
operator|--
operator|>
literal|0
condition|)
block|{
if|if
condition|(
name|fallback
operator|.
name|isEffectiveNow
argument_list|()
condition|)
return|return
name|fallback
return|;
else|else
name|fallback
operator|=
name|fallback
operator|.
name|getFallBackStatus
argument_list|()
expr_stmt|;
block|}
block|}
return|return
name|getSession
argument_list|()
operator|.
name|getDefaultSectioningStatus
argument_list|()
return|;
block|}
specifier|public
name|Date
name|getLastChangedByStudent
parameter_list|()
block|{
name|Date
name|ret
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|getCourseDemands
argument_list|()
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|CourseDemand
name|cd
range|:
name|getCourseDemands
argument_list|()
control|)
block|{
if|if
condition|(
name|getExternalUniqueId
argument_list|()
operator|!=
literal|null
operator|&&
name|getExternalUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
name|cd
operator|.
name|getChangedBy
argument_list|()
argument_list|)
condition|)
block|{
if|if
condition|(
name|ret
operator|==
literal|null
operator|||
name|ret
operator|.
name|before
argument_list|(
name|cd
operator|.
name|getTimestamp
argument_list|()
argument_list|)
condition|)
name|ret
operator|=
name|cd
operator|.
name|getTimestamp
argument_list|()
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
name|getClassEnrollments
argument_list|()
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|StudentClassEnrollment
name|e
range|:
name|getClassEnrollments
argument_list|()
control|)
block|{
if|if
condition|(
name|getExternalUniqueId
argument_list|()
operator|!=
literal|null
operator|&&
name|getExternalUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
name|e
operator|.
name|getChangedBy
argument_list|()
argument_list|)
condition|)
block|{
if|if
condition|(
name|ret
operator|==
literal|null
operator|||
name|ret
operator|.
name|before
argument_list|(
name|e
operator|.
name|getTimestamp
argument_list|()
argument_list|)
condition|)
name|ret
operator|=
name|e
operator|.
name|getTimestamp
argument_list|()
expr_stmt|;
block|}
block|}
block|}
return|return
name|ret
return|;
block|}
specifier|public
name|boolean
name|hasReleasedPin
parameter_list|()
block|{
return|return
name|getPin
argument_list|()
operator|!=
literal|null
operator|&&
operator|!
name|getPin
argument_list|()
operator|.
name|isEmpty
argument_list|()
operator|&&
name|isPinReleased
argument_list|()
operator|!=
literal|null
operator|&&
name|isPinReleased
argument_list|()
operator|.
name|booleanValue
argument_list|()
return|;
block|}
specifier|public
name|String
name|getReleasedPin
parameter_list|()
block|{
return|return
operator|(
name|hasReleasedPin
argument_list|()
condition|?
name|getPin
argument_list|()
else|:
literal|null
operator|)
return|;
block|}
block|}
end_class

end_unit

