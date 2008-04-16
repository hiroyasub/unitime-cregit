begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*   * UniTime 3.1 (University Course Timetabling& Student Sectioning Application)  * Copyright (C) 2008, UniTime.org  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program; if not, write to the Free Software Foundation, Inc.,  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
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
name|HashSet
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
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|base
operator|.
name|BaseEvent
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
name|EventDAO
import|;
end_import

begin_class
specifier|public
class|class
name|Event
extends|extends
name|BaseEvent
implements|implements
name|Comparable
argument_list|<
name|Event
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
name|Event
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|Event
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
name|Event
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
name|EventType
name|eventType
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|Integer
name|minCapacity
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|Integer
name|maxCapacity
parameter_list|)
block|{
name|super
argument_list|(
name|uniqueId
argument_list|,
name|eventType
argument_list|,
name|minCapacity
argument_list|,
name|maxCapacity
argument_list|)
expr_stmt|;
block|}
comment|/*[CONSTRUCTOR MARKER END]*/
specifier|public
specifier|static
name|List
name|findEventsOfSubjectArea
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|Long
name|subjectAreaId
parameter_list|,
name|Integer
name|eventType
parameter_list|)
block|{
return|return
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"select distinct e from Event e inner join e.relatedCourses r where "
operator|+
literal|"r.course.subjectArea.uniqueId=:subjectAreaId and e.eventType.uniqueId=:eventType"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"subjectAreaId"
argument_list|,
name|subjectAreaId
argument_list|)
operator|.
name|setInteger
argument_list|(
literal|"examType"
argument_list|,
name|eventType
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
specifier|public
specifier|static
name|List
name|findEventsOfSubjectArea
parameter_list|(
name|Long
name|subjectAreaId
parameter_list|,
name|Integer
name|eventType
parameter_list|)
block|{
return|return
operator|(
name|findEventsOfSubjectArea
argument_list|(
operator|(
operator|new
name|EventDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|)
argument_list|,
name|subjectAreaId
argument_list|,
name|eventType
argument_list|)
operator|)
return|;
block|}
specifier|public
specifier|static
name|List
name|findExamsOfCourseOffering
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|Long
name|courseOfferingId
parameter_list|,
name|Integer
name|eventType
parameter_list|)
block|{
return|return
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"select distinct e from Event e inner join e.relatedCourses r where "
operator|+
literal|"r.course.uniqueId=:courseOfferingId and e.eventType.uniqueId=:eventType"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"courseOfferingId"
argument_list|,
name|courseOfferingId
argument_list|)
operator|.
name|setInteger
argument_list|(
literal|"eventType"
argument_list|,
name|eventType
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
specifier|public
specifier|static
name|List
name|findExamsOfCourseOffering
parameter_list|(
name|Long
name|courseOfferingId
parameter_list|,
name|Integer
name|eventType
parameter_list|)
block|{
return|return
operator|(
name|findExamsOfCourseOffering
argument_list|(
operator|(
operator|new
name|EventDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|)
argument_list|,
name|courseOfferingId
argument_list|,
name|eventType
argument_list|)
operator|)
return|;
block|}
specifier|public
specifier|static
name|List
name|findEventsOfCourse
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|Long
name|subjectAreaId
parameter_list|,
name|String
name|courseNbr
parameter_list|,
name|Integer
name|eventType
parameter_list|)
block|{
if|if
condition|(
name|courseNbr
operator|==
literal|null
operator|||
name|courseNbr
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
return|return
name|findEventsOfSubjectArea
argument_list|(
name|subjectAreaId
argument_list|,
name|eventType
argument_list|)
return|;
return|return
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"select distinct e from Event e inner join e.relatedCourses r where "
operator|+
literal|"r.course.subjectArea.uniqueId=:subjectAreaId and e.eventType.uniqueId=:eventType and "
operator|+
operator|(
name|courseNbr
operator|.
name|indexOf
argument_list|(
literal|'*'
argument_list|)
operator|>=
literal|0
condition|?
literal|"r.course.courseNbr like :courseNbr"
else|:
literal|"r.course.courseNbr=:courseNbr"
operator|)
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"subjectAreaId"
argument_list|,
name|subjectAreaId
argument_list|)
operator|.
name|setString
argument_list|(
literal|"courseNbr"
argument_list|,
name|courseNbr
operator|.
name|trim
argument_list|()
operator|.
name|replaceAll
argument_list|(
literal|"\\*"
argument_list|,
literal|"%"
argument_list|)
argument_list|)
operator|.
name|setInteger
argument_list|(
literal|"eventType"
argument_list|,
name|eventType
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
specifier|public
specifier|static
name|List
name|findCourseRelatedEventsOfTypeOwnedBy
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|Long
name|eventType
parameter_list|,
name|Long
name|ownerId
parameter_list|,
name|Integer
name|ownerType
parameter_list|)
block|{
return|return
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"select distinct e from Event e inner join e.relatedCourses r where "
operator|+
literal|"r.ownerId=:ownerId and r.ownerType=:ownerType and e.eventType.uniqueId=:eventType"
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
name|setLong
argument_list|(
literal|"eventType"
argument_list|,
name|eventType
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
specifier|public
specifier|static
name|List
name|findCourseRelatedEventsOfTypeOwnedBy
parameter_list|(
name|Long
name|eventType
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
name|findCourseRelatedEventsOfTypeOwnedBy
argument_list|(
operator|(
operator|new
name|EventDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|)
argument_list|,
name|eventType
argument_list|,
name|ownerId
argument_list|,
name|ownerType
argument_list|)
operator|)
return|;
block|}
specifier|public
specifier|static
name|List
name|findCourseRelatedEventsOfTypeOwnedBy
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|Long
name|eventType
parameter_list|,
name|CourseOffering
name|courseOffering
parameter_list|)
block|{
return|return
operator|(
name|findCourseRelatedEventsOfTypeOwnedBy
argument_list|(
name|hibSession
argument_list|,
name|eventType
argument_list|,
name|courseOffering
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|ExamOwner
operator|.
name|sOwnerTypeCourse
argument_list|)
operator|)
return|;
block|}
specifier|public
specifier|static
name|List
name|findCourseRelatedEventsOfTypeOwnedBy
parameter_list|(
name|Long
name|eventType
parameter_list|,
name|CourseOffering
name|courseOffering
parameter_list|)
block|{
return|return
operator|(
name|findCourseRelatedEventsOfTypeOwnedBy
argument_list|(
operator|(
operator|new
name|EventDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|)
argument_list|,
name|eventType
argument_list|,
name|courseOffering
argument_list|)
operator|)
return|;
block|}
specifier|public
specifier|static
name|List
name|findCourseRelatedEventsOfTypeOwnedBy
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|Long
name|eventType
parameter_list|,
name|InstructionalOffering
name|instructionalOffering
parameter_list|)
block|{
return|return
operator|(
name|findCourseRelatedEventsOfTypeOwnedBy
argument_list|(
name|hibSession
argument_list|,
name|eventType
argument_list|,
name|instructionalOffering
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|ExamOwner
operator|.
name|sOwnerTypeOffering
argument_list|)
operator|)
return|;
block|}
specifier|public
specifier|static
name|List
name|findCourseRelatedEventsOfTypeOwnedBy
parameter_list|(
name|Long
name|eventType
parameter_list|,
name|InstructionalOffering
name|instructionalOffering
parameter_list|)
block|{
return|return
operator|(
name|findCourseRelatedEventsOfTypeOwnedBy
argument_list|(
operator|(
operator|new
name|EventDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|)
argument_list|,
name|eventType
argument_list|,
name|instructionalOffering
argument_list|)
operator|)
return|;
block|}
specifier|public
specifier|static
name|List
name|findCourseRelatedEventsOfTypeOwnedBy
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|Long
name|eventType
parameter_list|,
name|InstrOfferingConfig
name|instrOffrConfig
parameter_list|)
block|{
return|return
operator|(
name|findCourseRelatedEventsOfTypeOwnedBy
argument_list|(
name|hibSession
argument_list|,
name|eventType
argument_list|,
name|instrOffrConfig
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|ExamOwner
operator|.
name|sOwnerTypeConfig
argument_list|)
operator|)
return|;
block|}
specifier|public
specifier|static
name|List
name|findCourseRelatedEventsOfTypeOwnedBy
parameter_list|(
name|Long
name|eventType
parameter_list|,
name|InstrOfferingConfig
name|instrOffrConfig
parameter_list|)
block|{
return|return
operator|(
name|findCourseRelatedEventsOfTypeOwnedBy
argument_list|(
operator|(
operator|new
name|EventDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|)
argument_list|,
name|eventType
argument_list|,
name|instrOffrConfig
argument_list|)
operator|)
return|;
block|}
specifier|public
specifier|static
name|List
name|findCourseRelatedEventsOfTypeOwnedBy
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|Long
name|eventType
parameter_list|,
name|Class_
name|clazz
parameter_list|)
block|{
return|return
operator|(
name|findCourseRelatedEventsOfTypeOwnedBy
argument_list|(
name|hibSession
argument_list|,
name|eventType
argument_list|,
name|clazz
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|ExamOwner
operator|.
name|sOwnerTypeClass
argument_list|)
operator|)
return|;
block|}
specifier|public
specifier|static
name|List
name|findCourseRelatedEventsOfTypeOwnedBy
parameter_list|(
name|Long
name|eventType
parameter_list|,
name|Class_
name|clazz
parameter_list|)
block|{
return|return
operator|(
name|findCourseRelatedEventsOfTypeOwnedBy
argument_list|(
operator|(
operator|new
name|EventDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|)
argument_list|,
name|eventType
argument_list|,
name|clazz
argument_list|)
operator|)
return|;
block|}
specifier|public
specifier|static
name|List
name|findEventsOfCourse
parameter_list|(
name|Long
name|subjectAreaId
parameter_list|,
name|String
name|courseNbr
parameter_list|,
name|Integer
name|eventType
parameter_list|)
block|{
if|if
condition|(
name|courseNbr
operator|==
literal|null
operator|||
name|courseNbr
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
return|return
name|findEventsOfSubjectArea
argument_list|(
name|subjectAreaId
argument_list|,
name|eventType
argument_list|)
return|;
return|return
operator|(
name|findEventsOfCourse
argument_list|(
operator|(
operator|new
name|EventDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|)
argument_list|,
name|subjectAreaId
argument_list|,
name|courseNbr
argument_list|,
name|eventType
argument_list|)
operator|)
return|;
block|}
specifier|public
name|Set
argument_list|<
name|Student
argument_list|>
name|getStudents
parameter_list|()
block|{
name|HashSet
argument_list|<
name|Student
argument_list|>
name|students
init|=
operator|new
name|HashSet
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|getRelatedCourses
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
name|students
operator|.
name|addAll
argument_list|(
operator|(
operator|(
name|RelatedCourseInfo
operator|)
name|i
operator|.
name|next
argument_list|()
operator|)
operator|.
name|getStudents
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|students
return|;
block|}
specifier|public
name|Set
argument_list|<
name|Long
argument_list|>
name|getStudentIds
parameter_list|()
block|{
name|HashSet
argument_list|<
name|Long
argument_list|>
name|students
init|=
operator|new
name|HashSet
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
argument_list|<
name|?
argument_list|>
name|i
init|=
name|getRelatedCourses
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
name|students
operator|.
name|addAll
argument_list|(
operator|(
operator|(
name|RelatedCourseInfo
operator|)
name|i
operator|.
name|next
argument_list|()
operator|)
operator|.
name|getStudentIds
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|students
return|;
block|}
specifier|public
name|Set
argument_list|<
name|DepartmentalInstructor
argument_list|>
name|getInstructors
parameter_list|()
block|{
name|HashSet
argument_list|<
name|DepartmentalInstructor
argument_list|>
name|instructors
init|=
operator|new
name|HashSet
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|getRelatedCourses
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
name|instructors
operator|.
name|addAll
argument_list|(
operator|(
operator|(
name|RelatedCourseInfo
operator|)
name|i
operator|.
name|next
argument_list|()
operator|)
operator|.
name|getInstructors
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|instructors
return|;
block|}
specifier|public
name|int
name|countStudents
parameter_list|()
block|{
name|int
name|nrStudents
init|=
literal|0
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|getRelatedCourses
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
name|nrStudents
operator|+=
operator|(
operator|(
name|RelatedCourseInfo
operator|)
name|i
operator|.
name|next
argument_list|()
operator|)
operator|.
name|countStudents
argument_list|()
expr_stmt|;
return|return
name|nrStudents
return|;
block|}
specifier|public
specifier|static
name|void
name|deleteFromEvents
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|Integer
name|ownerType
parameter_list|,
name|Long
name|ownerId
parameter_list|)
block|{
for|for
control|(
name|Iterator
name|i
init|=
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"select r from Event e inner join e.relatedCourses r where "
operator|+
literal|"r.ownerType=:ownerType and r.ownerId=:ownerId"
argument_list|)
operator|.
name|setInteger
argument_list|(
literal|"ownerType"
argument_list|,
name|ownerType
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"ownerId"
argument_list|,
name|ownerId
argument_list|)
operator|.
name|iterate
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|RelatedCourseInfo
name|relatedCourse
init|=
operator|(
name|RelatedCourseInfo
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|Event
name|event
init|=
name|relatedCourse
operator|.
name|getEvent
argument_list|()
decl_stmt|;
name|event
operator|.
name|getRelatedCourses
argument_list|()
operator|.
name|remove
argument_list|(
name|relatedCourse
argument_list|)
expr_stmt|;
name|relatedCourse
operator|.
name|setOwnerId
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|relatedCourse
operator|.
name|setCourse
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|delete
argument_list|(
name|relatedCourse
argument_list|)
expr_stmt|;
if|if
condition|(
name|event
operator|.
name|getRelatedCourses
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|hibSession
operator|.
name|delete
argument_list|(
name|event
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|event
argument_list|)
expr_stmt|;
block|}
block|}
block|}
specifier|public
specifier|static
name|void
name|deleteFromEvents
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|Class_
name|clazz
parameter_list|)
block|{
name|deleteFromEvents
argument_list|(
name|hibSession
argument_list|,
name|ExamOwner
operator|.
name|sOwnerTypeClass
argument_list|,
name|clazz
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|void
name|deleteFromEvents
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|InstrOfferingConfig
name|config
parameter_list|)
block|{
name|deleteFromEvents
argument_list|(
name|hibSession
argument_list|,
name|ExamOwner
operator|.
name|sOwnerTypeConfig
argument_list|,
name|config
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|void
name|deleteFromEvents
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|InstructionalOffering
name|offering
parameter_list|)
block|{
name|deleteFromEvents
argument_list|(
name|hibSession
argument_list|,
name|ExamOwner
operator|.
name|sOwnerTypeOffering
argument_list|,
name|offering
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|void
name|deleteFromEvents
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|CourseOffering
name|course
parameter_list|)
block|{
name|deleteFromEvents
argument_list|(
name|hibSession
argument_list|,
name|ExamOwner
operator|.
name|sOwnerTypeCourse
argument_list|,
name|course
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
operator|(
name|this
operator|.
name|getEventName
argument_list|()
operator|)
return|;
block|}
specifier|public
name|int
name|compareTo
parameter_list|(
name|Event
name|e
parameter_list|)
block|{
if|if
condition|(
name|getEventName
argument_list|()
operator|!=
name|e
operator|.
name|getEventName
argument_list|()
condition|)
block|{
return|return
name|getEventName
argument_list|()
operator|.
name|compareTo
argument_list|(
name|e
operator|.
name|getEventName
argument_list|()
argument_list|)
return|;
block|}
else|else
return|return
name|getUniqueId
argument_list|()
operator|.
name|compareTo
argument_list|(
name|e
operator|.
name|getUniqueId
argument_list|()
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|List
name|findAll
parameter_list|()
block|{
return|return
operator|new
name|EventDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select e from Event e"
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
block|}
end_class

end_unit

