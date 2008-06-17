begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.1 (University Timetabling Application)  * Copyright (C) 2008, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program; if not, write to the Free Software Foundation, Inc.,  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|Enumeration
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
name|Locale
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
name|Department
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
name|SchedulingSubpart
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
name|Class_DAO
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
name|InstructionalOfferingDAO
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
name|SchedulingSubpartDAO
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|cpsolver
operator|.
name|coursett
operator|.
name|constraint
operator|.
name|JenrlConstraint
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|cpsolver
operator|.
name|coursett
operator|.
name|model
operator|.
name|Configuration
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|cpsolver
operator|.
name|coursett
operator|.
name|model
operator|.
name|FinalSectioning
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|cpsolver
operator|.
name|coursett
operator|.
name|model
operator|.
name|Lecture
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|cpsolver
operator|.
name|coursett
operator|.
name|model
operator|.
name|Student
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|cpsolver
operator|.
name|coursett
operator|.
name|model
operator|.
name|TimetableModel
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|cpsolver
operator|.
name|ifs
operator|.
name|util
operator|.
name|FastVector
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
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

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|EnrollmentCheck
block|{
specifier|private
specifier|static
name|java
operator|.
name|text
operator|.
name|DecimalFormat
name|sDoubleFormat
init|=
operator|new
name|java
operator|.
name|text
operator|.
name|DecimalFormat
argument_list|(
literal|"0.00"
argument_list|,
operator|new
name|java
operator|.
name|text
operator|.
name|DecimalFormatSymbols
argument_list|(
name|Locale
operator|.
name|US
argument_list|)
argument_list|)
decl_stmt|;
specifier|private
name|TimetableModel
name|iModel
init|=
literal|null
decl_stmt|;
specifier|public
name|EnrollmentCheck
parameter_list|(
name|TimetableModel
name|model
parameter_list|)
block|{
name|iModel
operator|=
name|model
expr_stmt|;
block|}
comment|/** Check validity of JENRL constraints from student enrollments */
specifier|public
name|void
name|checkJenrl
parameter_list|(
name|Progress
name|p
parameter_list|)
block|{
try|try
block|{
name|p
operator|.
name|setPhase
argument_list|(
literal|"Checking jenrl ..."
argument_list|,
name|iModel
operator|.
name|variables
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|Enumeration
name|i1
init|=
name|iModel
operator|.
name|variables
argument_list|()
operator|.
name|elements
argument_list|()
init|;
name|i1
operator|.
name|hasMoreElements
argument_list|()
condition|;
control|)
block|{
name|Lecture
name|l1
init|=
operator|(
name|Lecture
operator|)
name|i1
operator|.
name|nextElement
argument_list|()
decl_stmt|;
name|p
operator|.
name|incProgress
argument_list|()
expr_stmt|;
name|p
operator|.
name|debug
argument_list|(
literal|"Checking "
operator|+
name|l1
operator|.
name|getName
argument_list|()
operator|+
literal|" ..."
argument_list|)
expr_stmt|;
for|for
control|(
name|Enumeration
name|i2
init|=
name|iModel
operator|.
name|variables
argument_list|()
operator|.
name|elements
argument_list|()
init|;
name|i2
operator|.
name|hasMoreElements
argument_list|()
condition|;
control|)
block|{
name|Lecture
name|l2
init|=
operator|(
name|Lecture
operator|)
name|i2
operator|.
name|nextElement
argument_list|()
decl_stmt|;
if|if
condition|(
name|l1
operator|.
name|getId
argument_list|()
operator|<
name|l2
operator|.
name|getId
argument_list|()
condition|)
block|{
name|double
name|jenrl
init|=
literal|0
decl_stmt|;
name|Vector
name|jenrlStudents
init|=
operator|new
name|FastVector
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|i3
init|=
name|l1
operator|.
name|students
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|i3
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Student
name|student
init|=
operator|(
name|Student
operator|)
name|i3
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|l2
operator|.
name|students
argument_list|()
operator|.
name|contains
argument_list|(
name|student
argument_list|)
condition|)
block|{
name|jenrl
operator|+=
name|student
operator|.
name|getJenrlWeight
argument_list|(
name|l1
argument_list|,
name|l2
argument_list|)
expr_stmt|;
name|jenrlStudents
operator|.
name|addElement
argument_list|(
name|student
argument_list|)
expr_stmt|;
block|}
block|}
name|boolean
name|found
init|=
literal|false
decl_stmt|;
for|for
control|(
name|Enumeration
name|i3
init|=
name|iModel
operator|.
name|getJenrlConstraints
argument_list|()
operator|.
name|elements
argument_list|()
init|;
name|i3
operator|.
name|hasMoreElements
argument_list|()
condition|;
control|)
block|{
name|JenrlConstraint
name|j
init|=
operator|(
name|JenrlConstraint
operator|)
name|i3
operator|.
name|nextElement
argument_list|()
decl_stmt|;
name|Lecture
name|a
init|=
operator|(
name|Lecture
operator|)
name|j
operator|.
name|first
argument_list|()
decl_stmt|;
name|Lecture
name|b
init|=
operator|(
name|Lecture
operator|)
name|j
operator|.
name|second
argument_list|()
decl_stmt|;
if|if
condition|(
operator|(
name|a
operator|.
name|equals
argument_list|(
name|l1
argument_list|)
operator|&&
name|b
operator|.
name|equals
argument_list|(
name|l2
argument_list|)
operator|)
operator|||
operator|(
name|a
operator|.
name|equals
argument_list|(
name|l2
argument_list|)
operator|&&
name|b
operator|.
name|equals
argument_list|(
name|l1
argument_list|)
operator|)
condition|)
block|{
name|found
operator|=
literal|true
expr_stmt|;
if|if
condition|(
name|j
operator|.
name|getJenrl
argument_list|()
operator|!=
operator|(
name|int
operator|)
name|Math
operator|.
name|ceil
argument_list|(
name|jenrl
argument_list|)
condition|)
block|{
name|p
operator|.
name|error
argument_list|(
literal|"Wrong jenrl between "
operator|+
name|getClassLabel
argument_list|(
name|l1
argument_list|)
operator|+
literal|" and "
operator|+
name|getClassLabel
argument_list|(
name|l2
argument_list|)
operator|+
literal|" (constraint="
operator|+
name|j
operator|.
name|getJenrl
argument_list|()
operator|+
literal|" != computed="
operator|+
name|jenrl
operator|+
literal|").<br>"
operator|+
literal|"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
operator|+
name|getClassLabel
argument_list|(
name|l1
argument_list|)
operator|+
literal|" has students: "
operator|+
name|l1
operator|.
name|students
argument_list|()
operator|+
literal|"<br>"
operator|+
literal|"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
operator|+
name|getClassLabel
argument_list|(
name|l2
argument_list|)
operator|+
literal|" has students: "
operator|+
name|l2
operator|.
name|students
argument_list|()
operator|+
literal|"<br>"
operator|+
literal|"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;intersection: "
operator|+
name|jenrlStudents
argument_list|)
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
operator|!
name|found
operator|&&
name|jenrl
operator|>
literal|0
condition|)
block|{
name|p
operator|.
name|error
argument_list|(
literal|"Missing jenrl between "
operator|+
name|getClassLabel
argument_list|(
name|l1
argument_list|)
operator|+
literal|" and "
operator|+
name|getClassLabel
argument_list|(
name|l2
argument_list|)
operator|+
literal|" (computed="
operator|+
name|jenrl
operator|+
literal|").<br>"
operator|+
literal|"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
operator|+
name|getClassLabel
argument_list|(
name|l1
argument_list|)
operator|+
literal|" has students: "
operator|+
name|l1
operator|.
name|students
argument_list|()
operator|+
literal|"<br>"
operator|+
literal|"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
operator|+
name|getClassLabel
argument_list|(
name|l2
argument_list|)
operator|+
literal|" has students: "
operator|+
name|l2
operator|.
name|students
argument_list|()
operator|+
literal|"<br>"
operator|+
literal|"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;intersection: "
operator|+
name|jenrlStudents
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|p
operator|.
name|error
argument_list|(
literal|"Unexpected exception: "
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
block|}
specifier|public
name|void
name|checkEnrollment
parameter_list|(
name|Progress
name|p
parameter_list|,
name|Student
name|s
parameter_list|,
name|Long
name|subpartId
parameter_list|,
name|Collection
name|lectures
parameter_list|)
block|{
name|Lecture
name|enrolled
init|=
literal|null
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|lectures
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
name|Lecture
name|lecture
init|=
operator|(
name|Lecture
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|s
operator|.
name|getLectures
argument_list|()
operator|.
name|contains
argument_list|(
name|lecture
argument_list|)
condition|)
block|{
if|if
condition|(
name|enrolled
operator|!=
literal|null
condition|)
name|p
operator|.
name|warn
argument_list|(
literal|"Student "
operator|+
name|s
operator|.
name|getId
argument_list|()
operator|+
literal|" enrolled in multiple classes of the same subpart "
operator|+
name|getClassLabel
argument_list|(
name|enrolled
argument_list|)
operator|+
literal|", "
operator|+
name|getClassLabel
argument_list|(
name|lecture
argument_list|)
operator|+
literal|"."
argument_list|)
expr_stmt|;
name|enrolled
operator|=
name|lecture
expr_stmt|;
block|}
block|}
if|if
condition|(
name|enrolled
operator|==
literal|null
condition|)
block|{
name|p
operator|.
name|warn
argument_list|(
literal|"Student "
operator|+
name|s
operator|.
name|getId
argument_list|()
operator|+
literal|" not enrolled in any class of subpart "
operator|+
name|getSubpartLabel
argument_list|(
name|subpartId
argument_list|)
operator|+
literal|"."
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|enrolled
operator|.
name|hasAnyChildren
argument_list|()
condition|)
block|{
for|for
control|(
name|Enumeration
name|e
init|=
name|enrolled
operator|.
name|getChildrenSubpartIds
argument_list|()
init|;
name|e
operator|.
name|hasMoreElements
argument_list|()
condition|;
control|)
block|{
name|Long
name|sid
init|=
operator|(
name|Long
operator|)
name|e
operator|.
name|nextElement
argument_list|()
decl_stmt|;
name|checkEnrollment
argument_list|(
name|p
argument_list|,
name|s
argument_list|,
name|sid
argument_list|,
name|enrolled
operator|.
name|getChildren
argument_list|(
name|sid
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
specifier|private
name|String
name|getClassLabel
parameter_list|(
name|Lecture
name|lecture
parameter_list|)
block|{
return|return
literal|"<A href='classDetail.do?cid="
operator|+
name|lecture
operator|.
name|getClassId
argument_list|()
operator|+
literal|"'>"
operator|+
name|lecture
operator|.
name|getName
argument_list|()
operator|+
literal|"</A>"
return|;
block|}
specifier|private
name|String
name|getSubpartLabel
parameter_list|(
name|Long
name|subpartId
parameter_list|)
block|{
name|SchedulingSubpart
name|subpart
init|=
operator|(
operator|new
name|SchedulingSubpartDAO
argument_list|()
operator|)
operator|.
name|get
argument_list|(
name|subpartId
argument_list|)
decl_stmt|;
if|if
condition|(
name|subpart
operator|!=
literal|null
condition|)
block|{
name|String
name|suffix
init|=
name|subpart
operator|.
name|getSchedulingSubpartSuffix
argument_list|()
decl_stmt|;
return|return
literal|"<A href='schedulingSubpartDetail.do?ssuid="
operator|+
name|subpart
operator|.
name|getUniqueId
argument_list|()
operator|+
literal|"'>"
operator|+
name|subpart
operator|.
name|getCourseName
argument_list|()
operator|+
literal|" "
operator|+
name|subpart
operator|.
name|getItypeDesc
argument_list|()
operator|.
name|trim
argument_list|()
operator|+
operator|(
name|suffix
operator|==
literal|null
operator|||
name|suffix
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|?
literal|""
else|:
literal|" ("
operator|+
name|suffix
operator|+
literal|")"
operator|)
operator|+
literal|"</A>"
return|;
block|}
else|else
return|return
name|subpartId
operator|.
name|toString
argument_list|()
return|;
block|}
specifier|private
name|String
name|getOfferingLabel
parameter_list|(
name|Long
name|offeringId
parameter_list|)
block|{
name|InstructionalOffering
name|offering
init|=
operator|(
operator|new
name|InstructionalOfferingDAO
argument_list|()
operator|)
operator|.
name|get
argument_list|(
name|offeringId
argument_list|)
decl_stmt|;
if|if
condition|(
name|offering
operator|!=
literal|null
condition|)
return|return
literal|"<A href='instructionalOfferingDetail.do?io="
operator|+
name|offering
operator|.
name|getUniqueId
argument_list|()
operator|+
literal|"'>"
operator|+
name|offering
operator|.
name|getCourseName
argument_list|()
operator|+
literal|"</A>"
return|;
else|else
return|return
name|offeringId
operator|.
name|toString
argument_list|()
return|;
block|}
specifier|private
name|String
name|getOfferingsLabel
parameter_list|(
name|Collection
name|offeringIds
parameter_list|)
block|{
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|(
literal|"["
argument_list|)
decl_stmt|;
if|if
condition|(
name|offeringIds
operator|!=
literal|null
condition|)
for|for
control|(
name|Iterator
name|i
init|=
name|offeringIds
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
name|Long
name|offeringId
init|=
operator|(
name|Long
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|getOfferingLabel
argument_list|(
name|offeringId
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|i
operator|.
name|hasNext
argument_list|()
condition|)
name|sb
operator|.
name|append
argument_list|(
literal|", "
argument_list|)
expr_stmt|;
block|}
name|sb
operator|.
name|append
argument_list|(
literal|"]"
argument_list|)
expr_stmt|;
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
specifier|public
name|boolean
name|hasSubpartMixedOwnership
parameter_list|(
name|SchedulingSubpart
name|subpart
parameter_list|)
block|{
name|Department
name|owner
init|=
literal|null
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|subpart
operator|.
name|getClasses
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
name|Class_
name|clazz
init|=
operator|(
name|Class_
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|owner
operator|==
literal|null
condition|)
name|owner
operator|=
name|clazz
operator|.
name|getManagingDept
argument_list|()
expr_stmt|;
if|else if
condition|(
operator|!
name|owner
operator|.
name|equals
argument_list|(
name|clazz
operator|.
name|getManagingDept
argument_list|()
argument_list|)
condition|)
return|return
literal|true
return|;
block|}
if|if
condition|(
name|subpart
operator|.
name|getParentSubpart
argument_list|()
operator|!=
literal|null
condition|)
return|return
name|hasSubpartMixedOwnership
argument_list|(
name|subpart
operator|.
name|getParentSubpart
argument_list|()
argument_list|)
return|;
return|return
literal|false
return|;
block|}
specifier|public
name|boolean
name|hasSubpartMixedOwnership
parameter_list|(
name|Lecture
name|lecture
parameter_list|)
block|{
name|Class_
name|clazz
init|=
operator|(
operator|new
name|Class_DAO
argument_list|()
operator|)
operator|.
name|get
argument_list|(
name|lecture
operator|.
name|getClassId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|clazz
operator|==
literal|null
condition|)
return|return
literal|false
return|;
name|SchedulingSubpart
name|subpart
init|=
name|clazz
operator|.
name|getSchedulingSubpart
argument_list|()
decl_stmt|;
if|if
condition|(
name|subpart
operator|.
name|getClasses
argument_list|()
operator|.
name|size
argument_list|()
operator|>
name|lecture
operator|.
name|sameSubpartLectures
argument_list|()
operator|.
name|size
argument_list|()
condition|)
return|return
literal|true
return|;
if|if
condition|(
name|lecture
operator|.
name|getParent
argument_list|()
operator|!=
literal|null
condition|)
block|{
return|return
name|hasSubpartMixedOwnership
argument_list|(
name|lecture
operator|.
name|getParent
argument_list|()
argument_list|)
return|;
block|}
else|else
block|{
if|if
condition|(
name|subpart
operator|.
name|getParentSubpart
argument_list|()
operator|!=
literal|null
condition|)
return|return
name|hasSubpartMixedOwnership
argument_list|(
name|subpart
operator|.
name|getParentSubpart
argument_list|()
argument_list|)
return|;
block|}
return|return
literal|false
return|;
block|}
specifier|public
name|void
name|checkStudentEnrollments
parameter_list|(
name|Progress
name|p
parameter_list|)
block|{
name|p
operator|.
name|setStatus
argument_list|(
literal|"Student Enrollments Check"
argument_list|)
expr_stmt|;
if|if
condition|(
name|iModel
operator|.
name|getViolatedStudentConflicts
argument_list|()
operator|!=
name|iModel
operator|.
name|countViolatedStudentConflicts
argument_list|()
condition|)
block|{
name|p
operator|.
name|warn
argument_list|(
literal|"Inconsistent number of student conflits (counter="
operator|+
name|iModel
operator|.
name|getViolatedStudentConflicts
argument_list|()
operator|+
literal|", actual="
operator|+
name|iModel
operator|.
name|countViolatedStudentConflicts
argument_list|()
operator|+
literal|")."
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|iModel
operator|.
name|getHardStudentConflicts
argument_list|()
operator|!=
name|iModel
operator|.
name|countHardStudentConflicts
argument_list|()
condition|)
block|{
name|p
operator|.
name|warn
argument_list|(
literal|"Inconsistent number of hard student conflits (counter="
operator|+
name|iModel
operator|.
name|getHardStudentConflicts
argument_list|()
operator|+
literal|", actual="
operator|+
name|iModel
operator|.
name|countHardStudentConflicts
argument_list|()
operator|+
literal|")."
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|iModel
operator|.
name|getStudentDistanceConflicts
argument_list|()
operator|!=
name|iModel
operator|.
name|countStudentDistanceConflicts
argument_list|()
condition|)
block|{
name|p
operator|.
name|warn
argument_list|(
literal|"Inconsistent number of distance student conflits (counter="
operator|+
name|iModel
operator|.
name|getStudentDistanceConflicts
argument_list|()
operator|+
literal|", actual="
operator|+
name|iModel
operator|.
name|countStudentDistanceConflicts
argument_list|()
operator|+
literal|")."
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|iModel
operator|.
name|getCommitedStudentConflicts
argument_list|()
operator|!=
name|iModel
operator|.
name|countCommitedStudentConflicts
argument_list|()
condition|)
block|{
name|p
operator|.
name|warn
argument_list|(
literal|"Inconsistent number of committed student conflits (counter="
operator|+
name|iModel
operator|.
name|getCommitedStudentConflicts
argument_list|()
operator|+
literal|", actual="
operator|+
name|iModel
operator|.
name|countCommitedStudentConflicts
argument_list|()
operator|+
literal|")."
argument_list|)
expr_stmt|;
block|}
name|p
operator|.
name|setPhase
argument_list|(
literal|"Checking class limits..."
argument_list|,
name|iModel
operator|.
name|variables
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|Enumeration
name|e
init|=
name|iModel
operator|.
name|variables
argument_list|()
operator|.
name|elements
argument_list|()
init|;
name|e
operator|.
name|hasMoreElements
argument_list|()
condition|;
control|)
block|{
name|Lecture
name|lecture
init|=
operator|(
name|Lecture
operator|)
name|e
operator|.
name|nextElement
argument_list|()
decl_stmt|;
name|p
operator|.
name|incProgress
argument_list|()
expr_stmt|;
name|p
operator|.
name|debug
argument_list|(
literal|"Checking "
operator|+
name|getClassLabel
argument_list|(
name|lecture
argument_list|)
operator|+
literal|" ... students="
operator|+
name|lecture
operator|.
name|students
argument_list|()
operator|.
name|size
argument_list|()
operator|+
literal|", weighted="
operator|+
name|lecture
operator|.
name|nrWeightedStudents
argument_list|()
operator|+
literal|", limit="
operator|+
name|lecture
operator|.
name|classLimit
argument_list|()
operator|+
literal|" ("
operator|+
name|lecture
operator|.
name|minClassLimit
argument_list|()
operator|+
literal|".."
operator|+
name|lecture
operator|.
name|maxClassLimit
argument_list|()
operator|+
literal|")"
argument_list|)
expr_stmt|;
if|if
condition|(
name|lecture
operator|.
name|students
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
continue|continue;
name|double
name|w
init|=
literal|0
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|lecture
operator|.
name|students
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
name|w
operator|=
name|Math
operator|.
name|max
argument_list|(
name|w
argument_list|,
operator|(
operator|(
name|Student
operator|)
name|i
operator|.
name|next
argument_list|()
operator|)
operator|.
name|getOfferingWeight
argument_list|(
name|lecture
operator|.
name|getConfiguration
argument_list|()
operator|.
name|getOfferingId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|lecture
operator|.
name|nrWeightedStudents
argument_list|()
operator|-
name|w
operator|>
name|FinalSectioning
operator|.
name|sEps
operator|+
name|lecture
operator|.
name|classLimit
argument_list|()
condition|)
block|{
if|if
condition|(
name|hasSubpartMixedOwnership
argument_list|(
name|lecture
argument_list|)
condition|)
name|p
operator|.
name|info
argument_list|(
literal|"Class limit exceeded for class "
operator|+
name|getClassLabel
argument_list|(
name|lecture
argument_list|)
operator|+
literal|" ("
operator|+
name|sDoubleFormat
operator|.
name|format
argument_list|(
name|lecture
operator|.
name|nrWeightedStudents
argument_list|()
argument_list|)
operator|+
literal|">"
operator|+
name|lecture
operator|.
name|classLimit
argument_list|()
operator|+
literal|")."
argument_list|)
expr_stmt|;
else|else
name|p
operator|.
name|warn
argument_list|(
literal|"Class limit exceeded for class "
operator|+
name|getClassLabel
argument_list|(
name|lecture
argument_list|)
operator|+
literal|" ("
operator|+
name|sDoubleFormat
operator|.
name|format
argument_list|(
name|lecture
operator|.
name|nrWeightedStudents
argument_list|()
argument_list|)
operator|+
literal|">"
operator|+
name|lecture
operator|.
name|classLimit
argument_list|()
operator|+
literal|")."
argument_list|)
expr_stmt|;
block|}
block|}
comment|//iModel.checkJenrl(p);
name|p
operator|.
name|setPhase
argument_list|(
literal|"Checking enrollments..."
argument_list|,
name|iModel
operator|.
name|getAllStudents
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|iModel
operator|.
name|getAllStudents
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
name|p
operator|.
name|incProgress
argument_list|()
expr_stmt|;
name|Student
name|student
init|=
operator|(
name|Student
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|j
init|=
name|student
operator|.
name|getLectures
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|j
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Lecture
name|lecture
init|=
operator|(
name|Lecture
operator|)
name|j
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|student
operator|.
name|canEnroll
argument_list|(
name|lecture
argument_list|)
condition|)
name|p
operator|.
name|info
argument_list|(
literal|"Student "
operator|+
name|student
operator|.
name|getId
argument_list|()
operator|+
literal|" enrolled to invalid class "
operator|+
name|getClassLabel
argument_list|(
name|lecture
argument_list|)
operator|+
literal|"."
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|student
operator|.
name|getConfigurations
argument_list|()
operator|.
name|size
argument_list|()
operator|!=
name|student
operator|.
name|getOfferings
argument_list|()
operator|.
name|size
argument_list|()
condition|)
block|{
name|Vector
name|got
init|=
operator|new
name|Vector
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|j
init|=
name|student
operator|.
name|getConfigurations
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|j
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Configuration
name|cfg
init|=
operator|(
name|Configuration
operator|)
name|j
operator|.
name|next
argument_list|()
decl_stmt|;
name|got
operator|.
name|add
argument_list|(
name|cfg
operator|.
name|getOfferingId
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|p
operator|.
name|warn
argument_list|(
literal|"Student "
operator|+
name|student
operator|.
name|getId
argument_list|()
operator|+
literal|" demands offerings "
operator|+
name|getOfferingsLabel
argument_list|(
name|student
operator|.
name|getOfferings
argument_list|()
argument_list|)
operator|+
literal|", but got "
operator|+
name|getOfferingsLabel
argument_list|(
name|got
argument_list|)
operator|+
literal|"."
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|Iterator
name|j
init|=
name|student
operator|.
name|getConfigurations
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|j
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Configuration
name|cfg
init|=
operator|(
name|Configuration
operator|)
name|j
operator|.
name|next
argument_list|()
decl_stmt|;
for|for
control|(
name|Enumeration
name|e
init|=
name|cfg
operator|.
name|getTopSubpartIds
argument_list|()
init|;
name|e
operator|.
name|hasMoreElements
argument_list|()
condition|;
control|)
block|{
name|Long
name|subpartId
init|=
operator|(
name|Long
operator|)
name|e
operator|.
name|nextElement
argument_list|()
decl_stmt|;
name|checkEnrollment
argument_list|(
name|p
argument_list|,
name|student
argument_list|,
name|subpartId
argument_list|,
name|cfg
operator|.
name|getTopLectures
argument_list|(
name|subpartId
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
block|}
end_class

end_unit

