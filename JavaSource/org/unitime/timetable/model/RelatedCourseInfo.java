begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*   * UniTime 3.1 (University Course Timetabling& Student Sectioning Application)  * Copyright (C) 2008, UniTime LLC  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program; if not, write to the Free Software Foundation, Inc.,  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
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
name|List
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
name|BaseRelatedCourseInfo
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
name|comparators
operator|.
name|ClassComparator
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
name|comparators
operator|.
name|InstrOfferingConfigComparator
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
name|model
operator|.
name|dao
operator|.
name|RelatedCourseInfoDAO
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
name|InstrOfferingConfigDAO
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

begin_class
specifier|public
class|class
name|RelatedCourseInfo
extends|extends
name|BaseRelatedCourseInfo
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
name|RelatedCourseInfo
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|RelatedCourseInfo
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
comment|/** 	 * Constructor for required fields 	 */
specifier|public
name|RelatedCourseInfo
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Long
name|uniqueId
parameter_list|,
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|CourseEvent
name|event
parameter_list|,
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|CourseOffering
name|course
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|Long
name|ownerId
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|Integer
name|ownerType
parameter_list|)
block|{
name|super
argument_list|(
name|uniqueId
argument_list|,
name|event
argument_list|,
name|course
argument_list|,
name|ownerId
argument_list|,
name|ownerType
argument_list|)
expr_stmt|;
block|}
comment|/*[CONSTRUCTOR MARKER END]*/
specifier|public
specifier|static
name|List
name|findByOwnerIdType
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|Long
name|ownerId
parameter_list|,
name|Integer
name|ownerType
parameter_list|)
block|{
return|return
operator|(
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"select o from RelatedCourseInfo o where o.ownerId=:ownerId and o.ownerType=:ownerType"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"ownerId"
argument_list|,
name|ownerId
argument_list|)
operator|.
name|setInteger
argument_list|(
literal|"ownerType"
argument_list|,
name|ownerType
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
operator|)
return|;
block|}
specifier|public
specifier|static
name|List
name|findByOwnerIdType
parameter_list|(
name|Long
name|ownerId
parameter_list|,
name|Integer
name|ownerType
parameter_list|)
block|{
return|return
operator|(
name|findByOwnerIdType
argument_list|(
operator|new
name|RelatedCourseInfoDAO
argument_list|()
operator|.
name|getSession
argument_list|()
argument_list|,
name|ownerId
argument_list|,
name|ownerType
argument_list|)
operator|)
return|;
block|}
specifier|private
name|Object
name|iOwnerObject
init|=
literal|null
decl_stmt|;
specifier|public
name|Object
name|getOwnerObject
parameter_list|()
block|{
if|if
condition|(
name|iOwnerObject
operator|!=
literal|null
condition|)
return|return
name|iOwnerObject
return|;
switch|switch
condition|(
name|getOwnerType
argument_list|()
condition|)
block|{
case|case
name|ExamOwner
operator|.
name|sOwnerTypeClass
case|:
name|iOwnerObject
operator|=
operator|new
name|Class_DAO
argument_list|()
operator|.
name|get
argument_list|(
name|getOwnerId
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|iOwnerObject
return|;
case|case
name|ExamOwner
operator|.
name|sOwnerTypeConfig
case|:
name|iOwnerObject
operator|=
operator|new
name|InstrOfferingConfigDAO
argument_list|()
operator|.
name|get
argument_list|(
name|getOwnerId
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|iOwnerObject
return|;
case|case
name|ExamOwner
operator|.
name|sOwnerTypeCourse
case|:
name|iOwnerObject
operator|=
operator|new
name|CourseOfferingDAO
argument_list|()
operator|.
name|get
argument_list|(
name|getOwnerId
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|iOwnerObject
return|;
case|case
name|ExamOwner
operator|.
name|sOwnerTypeOffering
case|:
name|iOwnerObject
operator|=
operator|new
name|InstructionalOfferingDAO
argument_list|()
operator|.
name|get
argument_list|(
name|getOwnerId
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|iOwnerObject
return|;
default|default :
throw|throw
operator|new
name|RuntimeException
argument_list|(
literal|"Unknown owner type "
operator|+
name|getOwnerType
argument_list|()
argument_list|)
throw|;
block|}
block|}
specifier|public
name|void
name|setOwner
parameter_list|(
name|Class_
name|clazz
parameter_list|)
block|{
name|setOwnerId
argument_list|(
name|clazz
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|setOwnerType
argument_list|(
name|ExamOwner
operator|.
name|sOwnerTypeClass
argument_list|)
expr_stmt|;
name|setCourse
argument_list|(
name|clazz
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getInstrOfferingConfig
argument_list|()
operator|.
name|getControllingCourseOffering
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|setOwner
parameter_list|(
name|InstrOfferingConfig
name|config
parameter_list|)
block|{
name|setOwnerId
argument_list|(
name|config
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|setOwnerType
argument_list|(
name|ExamOwner
operator|.
name|sOwnerTypeConfig
argument_list|)
expr_stmt|;
name|setCourse
argument_list|(
name|config
operator|.
name|getControllingCourseOffering
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|setOwner
parameter_list|(
name|CourseOffering
name|course
parameter_list|)
block|{
name|setOwnerId
argument_list|(
name|course
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|setOwnerType
argument_list|(
name|ExamOwner
operator|.
name|sOwnerTypeCourse
argument_list|)
expr_stmt|;
name|setCourse
argument_list|(
name|course
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|setOwner
parameter_list|(
name|InstructionalOffering
name|offering
parameter_list|)
block|{
name|setOwnerId
argument_list|(
name|offering
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|setOwnerType
argument_list|(
name|ExamOwner
operator|.
name|sOwnerTypeOffering
argument_list|)
expr_stmt|;
name|setCourse
argument_list|(
name|offering
operator|.
name|getControllingCourseOffering
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
name|CourseOffering
name|computeCourse
parameter_list|()
block|{
name|Object
name|owner
init|=
name|getOwnerObject
argument_list|()
decl_stmt|;
switch|switch
condition|(
name|getOwnerType
argument_list|()
condition|)
block|{
case|case
name|ExamOwner
operator|.
name|sOwnerTypeClass
case|:
return|return
operator|(
operator|(
name|Class_
operator|)
name|owner
operator|)
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getControllingCourseOffering
argument_list|()
return|;
case|case
name|ExamOwner
operator|.
name|sOwnerTypeConfig
case|:
return|return
operator|(
operator|(
name|InstrOfferingConfig
operator|)
name|owner
operator|)
operator|.
name|getControllingCourseOffering
argument_list|()
return|;
case|case
name|ExamOwner
operator|.
name|sOwnerTypeCourse
case|:
return|return
operator|(
name|CourseOffering
operator|)
name|owner
return|;
case|case
name|ExamOwner
operator|.
name|sOwnerTypeOffering
case|:
return|return
operator|(
operator|(
name|InstructionalOffering
operator|)
name|owner
operator|)
operator|.
name|getControllingCourseOffering
argument_list|()
return|;
default|default :
throw|throw
operator|new
name|RuntimeException
argument_list|(
literal|"Unknown owner type "
operator|+
name|getOwnerType
argument_list|()
argument_list|)
throw|;
block|}
block|}
specifier|public
name|int
name|compareTo
parameter_list|(
name|RelatedCourseInfo
name|owner
parameter_list|)
block|{
name|CourseOffering
name|c1
init|=
name|getCourse
argument_list|()
decl_stmt|;
name|CourseOffering
name|c2
init|=
name|owner
operator|.
name|getCourse
argument_list|()
decl_stmt|;
name|int
name|cmp
init|=
literal|0
decl_stmt|;
name|cmp
operator|=
name|c1
operator|.
name|getSubjectAreaAbbv
argument_list|()
operator|.
name|compareTo
argument_list|(
name|c2
operator|.
name|getSubjectAreaAbbv
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|cmp
operator|!=
literal|0
condition|)
return|return
name|cmp
return|;
name|cmp
operator|=
name|c1
operator|.
name|getCourseNbr
argument_list|()
operator|.
name|compareTo
argument_list|(
name|c2
operator|.
name|getCourseNbr
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|cmp
operator|!=
literal|0
condition|)
return|return
name|cmp
return|;
name|cmp
operator|=
name|getOwnerType
argument_list|()
operator|.
name|compareTo
argument_list|(
name|owner
operator|.
name|getOwnerType
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|cmp
operator|!=
literal|0
condition|)
return|return
name|cmp
return|;
switch|switch
condition|(
name|getOwnerType
argument_list|()
condition|)
block|{
case|case
name|ExamOwner
operator|.
name|sOwnerTypeClass
case|:
return|return
operator|new
name|ClassComparator
argument_list|(
name|ClassComparator
operator|.
name|COMPARE_BY_HIERARCHY
argument_list|)
operator|.
name|compare
argument_list|(
name|getOwnerObject
argument_list|()
argument_list|,
name|owner
operator|.
name|getOwnerObject
argument_list|()
argument_list|)
return|;
case|case
name|ExamOwner
operator|.
name|sOwnerTypeConfig
case|:
return|return
operator|new
name|InstrOfferingConfigComparator
argument_list|(
literal|null
argument_list|)
operator|.
name|compare
argument_list|(
name|getOwnerObject
argument_list|()
argument_list|,
name|owner
operator|.
name|getOwnerObject
argument_list|()
argument_list|)
return|;
block|}
return|return
name|getOwnerId
argument_list|()
operator|.
name|compareTo
argument_list|(
name|owner
operator|.
name|getOwnerId
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|List
name|getStudents
parameter_list|()
block|{
switch|switch
condition|(
name|getOwnerType
argument_list|()
condition|)
block|{
case|case
name|ExamOwner
operator|.
name|sOwnerTypeClass
case|:
return|return
operator|new
name|RelatedCourseInfoDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select distinct e.student from "
operator|+
literal|"StudentClassEnrollment e inner join e.clazz c  "
operator|+
literal|"where c.uniqueId = :eventOwnerId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"eventOwnerId"
argument_list|,
name|getOwnerId
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
return|;
case|case
name|ExamOwner
operator|.
name|sOwnerTypeConfig
case|:
return|return
operator|new
name|RelatedCourseInfoDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select distinct e.student from "
operator|+
literal|"StudentClassEnrollment e inner join e.clazz c  "
operator|+
literal|"where c.schedulingSubpart.instrOfferingConfig.uniqueId = :eventOwnerId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"eventOwnerId"
argument_list|,
name|getOwnerId
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
return|;
case|case
name|ExamOwner
operator|.
name|sOwnerTypeCourse
case|:
return|return
operator|new
name|RelatedCourseInfoDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select distinct e.student from "
operator|+
literal|"StudentClassEnrollment e inner join e.courseOffering co  "
operator|+
literal|"where co.uniqueId = :eventOwnerId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"eventOwnerId"
argument_list|,
name|getOwnerId
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
return|;
case|case
name|ExamOwner
operator|.
name|sOwnerTypeOffering
case|:
return|return
operator|new
name|RelatedCourseInfoDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select distinct e.student from "
operator|+
literal|"StudentClassEnrollment e inner join e.courseOffering co  "
operator|+
literal|"where co.instructionalOffering.uniqueId = :eventOwnerId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"eventOwnerId"
argument_list|,
name|getOwnerId
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
return|;
default|default :
throw|throw
operator|new
name|RuntimeException
argument_list|(
literal|"Unknown owner type "
operator|+
name|getOwnerType
argument_list|()
argument_list|)
throw|;
block|}
block|}
specifier|public
name|List
name|getStudentIds
parameter_list|()
block|{
switch|switch
condition|(
name|getOwnerType
argument_list|()
condition|)
block|{
case|case
name|ExamOwner
operator|.
name|sOwnerTypeClass
case|:
return|return
operator|new
name|RelatedCourseInfoDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select distinct e.student.uniqueId from "
operator|+
literal|"StudentClassEnrollment e inner join e.clazz c  "
operator|+
literal|"where c.uniqueId = :eventOwnerId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"eventOwnerId"
argument_list|,
name|getOwnerId
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
return|;
case|case
name|ExamOwner
operator|.
name|sOwnerTypeConfig
case|:
return|return
operator|new
name|RelatedCourseInfoDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select distinct e.student.uniqueId from "
operator|+
literal|"StudentClassEnrollment e inner join e.clazz c  "
operator|+
literal|"where c.schedulingSubpart.instrOfferingConfig.uniqueId = :eventOwnerId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"eventOwnerId"
argument_list|,
name|getOwnerId
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
return|;
case|case
name|ExamOwner
operator|.
name|sOwnerTypeCourse
case|:
return|return
operator|new
name|RelatedCourseInfoDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select distinct e.student.uniqueId from "
operator|+
literal|"StudentClassEnrollment e inner join e.courseOffering co  "
operator|+
literal|"where co.uniqueId = :eventOwnerId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"eventOwnerId"
argument_list|,
name|getOwnerId
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
return|;
case|case
name|ExamOwner
operator|.
name|sOwnerTypeOffering
case|:
return|return
operator|new
name|RelatedCourseInfoDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select distinct e.student.uniqueId from "
operator|+
literal|"StudentClassEnrollment e inner join e.courseOffering co  "
operator|+
literal|"where co.instructionalOffering.uniqueId = :eventOwnerId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"eventOwnerId"
argument_list|,
name|getOwnerId
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
return|;
default|default :
throw|throw
operator|new
name|RuntimeException
argument_list|(
literal|"Unknown owner type "
operator|+
name|getOwnerType
argument_list|()
argument_list|)
throw|;
block|}
block|}
specifier|public
name|List
name|getInstructors
parameter_list|()
block|{
switch|switch
condition|(
name|getOwnerType
argument_list|()
condition|)
block|{
case|case
name|ExamOwner
operator|.
name|sOwnerTypeClass
case|:
return|return
operator|new
name|RelatedCourseInfoDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select i from "
operator|+
literal|"Class_ c inner join c.classInstructors ci inner join ci.instructor i "
operator|+
literal|"where c.uniqueId = :eventOwnerId and ci.lead=true"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"eventOwnerId"
argument_list|,
name|getOwnerId
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
return|;
case|case
name|ExamOwner
operator|.
name|sOwnerTypeConfig
case|:
return|return
operator|new
name|RelatedCourseInfoDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select distinct i from "
operator|+
literal|"Class_ c inner join c.classInstructors ci inner join ci.instructor i "
operator|+
literal|"where c.schedulingSubpart.instrOfferingConfig.uniqueId = :eventOwnerId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"eventOwnerId"
argument_list|,
name|getOwnerId
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
return|;
case|case
name|ExamOwner
operator|.
name|sOwnerTypeCourse
case|:
return|return
operator|new
name|RelatedCourseInfoDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select distinct i from "
operator|+
literal|"Class_ c inner join c.classInstructors ci inner join ci.instructor i inner join c.schedulingSubpart.instrOfferingConfig.instructionalOffering.courseOfferings co "
operator|+
literal|"where co.uniqueId = :eventOwnerId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"eventOwnerId"
argument_list|,
name|getOwnerId
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
return|;
case|case
name|ExamOwner
operator|.
name|sOwnerTypeOffering
case|:
return|return
operator|new
name|RelatedCourseInfoDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select distinct i from "
operator|+
literal|"Class_ c inner join c.classInstructors ci inner join ci.instructor i "
operator|+
literal|"where c.schedulingSubpart.instrOfferingConfig.instructionalOffering.uniqueId = :eventOwnerId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"eventOwnerId"
argument_list|,
name|getOwnerId
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
return|;
default|default :
throw|throw
operator|new
name|RuntimeException
argument_list|(
literal|"Unknown owner type "
operator|+
name|getOwnerType
argument_list|()
argument_list|)
throw|;
block|}
block|}
specifier|public
name|int
name|countStudents
parameter_list|()
block|{
switch|switch
condition|(
name|getOwnerType
argument_list|()
condition|)
block|{
case|case
name|ExamOwner
operator|.
name|sOwnerTypeClass
case|:
return|return
operator|(
operator|(
name|Number
operator|)
operator|new
name|RelatedCourseInfoDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select count(distinct e.student) from "
operator|+
literal|"StudentClassEnrollment e inner join e.clazz c  "
operator|+
literal|"where c.uniqueId = :eventOwnerId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"eventOwnerId"
argument_list|,
name|getOwnerId
argument_list|()
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|uniqueResult
argument_list|()
operator|)
operator|.
name|intValue
argument_list|()
return|;
case|case
name|ExamOwner
operator|.
name|sOwnerTypeConfig
case|:
return|return
operator|(
operator|(
name|Number
operator|)
operator|new
name|RelatedCourseInfoDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select count(distinct e.student) from "
operator|+
literal|"StudentClassEnrollment e inner join e.clazz c  "
operator|+
literal|"where c.schedulingSubpart.instrOfferingConfig.uniqueId = :eventOwnerId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"eventOwnerId"
argument_list|,
name|getOwnerId
argument_list|()
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|uniqueResult
argument_list|()
operator|)
operator|.
name|intValue
argument_list|()
return|;
case|case
name|ExamOwner
operator|.
name|sOwnerTypeCourse
case|:
return|return
operator|(
operator|(
name|Number
operator|)
operator|new
name|RelatedCourseInfoDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select count(distinct e.student) from "
operator|+
literal|"StudentClassEnrollment e inner join e.courseOffering co  "
operator|+
literal|"where co.uniqueId = :eventOwnerId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"eventOwnerId"
argument_list|,
name|getOwnerId
argument_list|()
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|uniqueResult
argument_list|()
operator|)
operator|.
name|intValue
argument_list|()
return|;
case|case
name|ExamOwner
operator|.
name|sOwnerTypeOffering
case|:
return|return
operator|(
operator|(
name|Number
operator|)
operator|new
name|RelatedCourseInfoDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select count(distinct e.student) from "
operator|+
literal|"StudentClassEnrollment e inner join e.courseOffering co  "
operator|+
literal|"where co.instructionalOffering.uniqueId = :eventOwnerId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"eventOwnerId"
argument_list|,
name|getOwnerId
argument_list|()
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|uniqueResult
argument_list|()
operator|)
operator|.
name|intValue
argument_list|()
return|;
default|default :
throw|throw
operator|new
name|RuntimeException
argument_list|(
literal|"Unknown owner type "
operator|+
name|getOwnerType
argument_list|()
argument_list|)
throw|;
block|}
block|}
specifier|public
name|String
name|getLabel
parameter_list|()
block|{
name|Object
name|owner
init|=
name|getOwnerObject
argument_list|()
decl_stmt|;
switch|switch
condition|(
name|getOwnerType
argument_list|()
condition|)
block|{
case|case
name|ExamOwner
operator|.
name|sOwnerTypeClass
case|:
return|return
operator|(
operator|(
name|Class_
operator|)
name|owner
operator|)
operator|.
name|getClassLabel
argument_list|()
return|;
case|case
name|ExamOwner
operator|.
name|sOwnerTypeConfig
case|:
return|return
operator|(
operator|(
name|InstrOfferingConfig
operator|)
name|owner
operator|)
operator|.
name|toString
argument_list|()
return|;
case|case
name|ExamOwner
operator|.
name|sOwnerTypeCourse
case|:
return|return
operator|(
operator|(
name|CourseOffering
operator|)
name|owner
operator|)
operator|.
name|getCourseName
argument_list|()
return|;
case|case
name|ExamOwner
operator|.
name|sOwnerTypeOffering
case|:
return|return
operator|(
operator|(
name|InstructionalOffering
operator|)
name|owner
operator|)
operator|.
name|getCourseName
argument_list|()
return|;
default|default :
throw|throw
operator|new
name|RuntimeException
argument_list|(
literal|"Unknown owner type "
operator|+
name|getOwnerType
argument_list|()
argument_list|)
throw|;
block|}
block|}
block|}
end_class

end_unit

