begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 (University Timetabling Application)  * Copyright (C) 2008 - 2010, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|java
operator|.
name|util
operator|.
name|Vector
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
name|util
operator|.
name|Constants
import|;
end_import

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
name|void
name|addToPosMajors
parameter_list|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|PosMajor
name|major
parameter_list|)
block|{
if|if
condition|(
literal|null
operator|==
name|getPosMajors
argument_list|()
condition|)
name|setPosMajors
argument_list|(
operator|new
name|java
operator|.
name|util
operator|.
name|HashSet
argument_list|()
argument_list|)
expr_stmt|;
name|getPosMajors
argument_list|()
operator|.
name|add
argument_list|(
name|major
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|addToPosMinors
parameter_list|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|PosMinor
name|minor
parameter_list|)
block|{
if|if
condition|(
literal|null
operator|==
name|getPosMinors
argument_list|()
condition|)
name|setPosMinors
argument_list|(
operator|new
name|java
operator|.
name|util
operator|.
name|HashSet
argument_list|()
argument_list|)
expr_stmt|;
name|getPosMinors
argument_list|()
operator|.
name|add
argument_list|(
name|minor
argument_list|)
expr_stmt|;
block|}
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
literal|"left join fetch cr.classEnrollments as cre "
operator|+
literal|"left join fetch s.academicAreaClassifications "
operator|+
literal|"left join fetch s.posMajors "
operator|+
literal|"left join fetch s.posMinors "
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
if|if
condition|(
name|enrollment
operator|.
name|getCourseRequest
argument_list|()
operator|!=
literal|null
condition|)
name|enrollment
operator|.
name|getCourseRequest
argument_list|()
operator|.
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
literal|"where e.student.uniqueId=:studentId and o.ownerType=:ownerType and o.ownerId=e.clazz.uniqueId and o.exam.examType=:examType"
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
literal|"where e.student.uniqueId=:studentId and o.ownerType=:ownerType and o.ownerId=e.clazz.schedulingSubpart.instrOfferingConfig.uniqueId and o.exam.examType=:examType"
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
literal|"where e.student.uniqueId=:studentId and o.ownerType=:ownerType and o.ownerId=e.courseOffering.uniqueId and o.exam.examType=:examType"
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
literal|"where e.student.uniqueId=:studentId and o.ownerType=:ownerType and o.ownerId=e.courseOffering.instructionalOffering.uniqueId and o.exam.examType=:examType"
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
name|String
name|getName
parameter_list|(
name|String
name|instructorNameFormat
parameter_list|)
block|{
if|if
condition|(
name|DepartmentalInstructor
operator|.
name|sNameFormatLastFist
operator|.
name|equals
argument_list|(
name|instructorNameFormat
argument_list|)
condition|)
return|return
name|Constants
operator|.
name|toInitialCase
argument_list|(
operator|(
name|getLastName
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|getLastName
argument_list|()
operator|.
name|trim
argument_list|()
operator|)
operator|+
literal|", "
operator|+
operator|(
name|getFirstName
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|getFirstName
argument_list|()
operator|.
name|trim
argument_list|()
operator|)
argument_list|)
return|;
if|else if
condition|(
name|DepartmentalInstructor
operator|.
name|sNameFormatFirstLast
operator|.
name|equals
argument_list|(
name|instructorNameFormat
argument_list|)
condition|)
return|return
name|Constants
operator|.
name|toInitialCase
argument_list|(
operator|(
name|getFirstName
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|getFirstName
argument_list|()
operator|.
name|trim
argument_list|()
operator|)
operator|+
literal|" "
operator|+
operator|(
name|getLastName
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|getLastName
argument_list|()
operator|.
name|trim
argument_list|()
operator|)
argument_list|)
return|;
if|else if
condition|(
name|DepartmentalInstructor
operator|.
name|sNameFormatInitialLast
operator|.
name|equals
argument_list|(
name|instructorNameFormat
argument_list|)
condition|)
return|return
operator|(
name|getFirstName
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
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
operator|.
name|toUpperCase
argument_list|()
operator|)
operator|+
operator|(
name|getMiddleName
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
literal|" "
operator|+
name|getMiddleName
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
operator|.
name|toUpperCase
argument_list|()
operator|)
operator|+
literal|" "
operator|+
operator|(
name|Constants
operator|.
name|toInitialCase
argument_list|(
name|getLastName
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|getLastName
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
operator|)
return|;
if|else if
condition|(
name|DepartmentalInstructor
operator|.
name|sNameFormatLastInitial
operator|.
name|equals
argument_list|(
name|instructorNameFormat
argument_list|)
condition|)
return|return
name|Constants
operator|.
name|toInitialCase
argument_list|(
name|getLastName
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|getLastName
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
operator|+
literal|", "
operator|+
operator|(
name|getFirstName
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
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
operator|.
name|toUpperCase
argument_list|()
operator|)
operator|+
operator|(
name|getMiddleName
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
literal|" "
operator|+
name|getMiddleName
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
operator|.
name|toUpperCase
argument_list|()
operator|)
return|;
if|else if
condition|(
name|DepartmentalInstructor
operator|.
name|sNameFormatLastFirstMiddle
operator|.
name|equals
argument_list|(
name|instructorNameFormat
argument_list|)
condition|)
return|return
name|Constants
operator|.
name|toInitialCase
argument_list|(
operator|(
name|getLastName
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|getLastName
argument_list|()
operator|.
name|trim
argument_list|()
operator|)
operator|+
literal|", "
operator|+
operator|(
name|getFirstName
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|getFirstName
argument_list|()
operator|.
name|trim
argument_list|()
operator|)
operator|+
operator|(
name|getMiddleName
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
literal|" "
operator|+
name|getMiddleName
argument_list|()
operator|.
name|trim
argument_list|()
operator|)
argument_list|)
return|;
if|else if
condition|(
name|DepartmentalInstructor
operator|.
name|sNameFormatShort
operator|.
name|equals
argument_list|(
name|instructorNameFormat
argument_list|)
condition|)
block|{
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
if|if
condition|(
name|getFirstName
argument_list|()
operator|!=
literal|null
operator|&&
name|getFirstName
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|getFirstName
argument_list|()
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|1
argument_list|)
operator|.
name|toUpperCase
argument_list|()
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|". "
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|getLastName
argument_list|()
operator|!=
literal|null
operator|&&
name|getLastName
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|getLastName
argument_list|()
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|1
argument_list|)
operator|.
name|toUpperCase
argument_list|()
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|getLastName
argument_list|()
operator|.
name|substring
argument_list|(
literal|1
argument_list|,
name|Math
operator|.
name|min
argument_list|(
literal|10
argument_list|,
name|getLastName
argument_list|()
operator|.
name|length
argument_list|()
argument_list|)
argument_list|)
operator|.
name|toLowerCase
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
else|else
return|return
name|Constants
operator|.
name|toInitialCase
argument_list|(
operator|(
name|getFirstName
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|getFirstName
argument_list|()
operator|.
name|trim
argument_list|()
operator|)
operator|+
literal|" "
operator|+
operator|(
name|getMiddleName
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|getMiddleName
argument_list|()
operator|.
name|trim
argument_list|()
operator|)
operator|+
literal|" "
operator|+
operator|(
name|getLastName
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|getLastName
argument_list|()
operator|.
name|trim
argument_list|()
operator|)
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
name|getName
argument_list|(
name|DepartmentalInstructor
operator|.
name|sNameFormatLastFist
argument_list|)
operator|.
name|compareTo
argument_list|(
name|student
operator|.
name|getName
argument_list|(
name|DepartmentalInstructor
operator|.
name|sNameFormatLastFist
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
name|getUniqueId
argument_list|()
operator|.
name|compareTo
argument_list|(
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
name|Vector
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
literal|")"
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
name|elementAt
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
block|}
end_class

end_unit

