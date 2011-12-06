begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 (University Timetabling Application)  * Copyright (C) 2011, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|updates
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
name|Placement
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
name|studentsct
operator|.
name|model
operator|.
name|Course
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
name|unitime
operator|.
name|localization
operator|.
name|impl
operator|.
name|Localization
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
name|resources
operator|.
name|StudentSectioningMessages
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
name|ClassInstructor
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
name|DepartmentalInstructor
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
name|OnlineSectioningLog
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
operator|.
name|Lock
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|ClassAssignmentChanged
implements|implements
name|OnlineSectioningAction
argument_list|<
name|Boolean
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
specifier|static
name|StudentSectioningMessages
name|MSG
init|=
name|Localization
operator|.
name|create
argument_list|(
name|StudentSectioningMessages
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|Collection
argument_list|<
name|Long
argument_list|>
name|iClassIds
init|=
literal|null
decl_stmt|;
specifier|public
name|ClassAssignmentChanged
parameter_list|(
name|Long
modifier|...
name|classIds
parameter_list|)
block|{
name|iClassIds
operator|=
operator|new
name|ArrayList
argument_list|<
name|Long
argument_list|>
argument_list|()
expr_stmt|;
for|for
control|(
name|Long
name|classId
range|:
name|classIds
control|)
name|iClassIds
operator|.
name|add
argument_list|(
name|classId
argument_list|)
expr_stmt|;
block|}
specifier|public
name|ClassAssignmentChanged
parameter_list|(
name|Collection
argument_list|<
name|Long
argument_list|>
name|classIds
parameter_list|)
block|{
name|iClassIds
operator|=
name|classIds
expr_stmt|;
block|}
specifier|public
name|Collection
argument_list|<
name|Long
argument_list|>
name|getClassIds
parameter_list|()
block|{
return|return
name|iClassIds
return|;
block|}
specifier|public
name|Collection
argument_list|<
name|Long
argument_list|>
name|getCourseIds
parameter_list|(
name|OnlineSectioningServer
name|server
parameter_list|)
block|{
name|Set
argument_list|<
name|Long
argument_list|>
name|courseIds
init|=
operator|new
name|HashSet
argument_list|<
name|Long
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Long
name|classId
range|:
name|getClassIds
argument_list|()
control|)
block|{
name|Section
name|section
init|=
name|server
operator|.
name|getSection
argument_list|(
name|classId
argument_list|)
decl_stmt|;
if|if
condition|(
name|section
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|Course
name|course
range|:
name|section
operator|.
name|getSubpart
argument_list|()
operator|.
name|getConfig
argument_list|()
operator|.
name|getOffering
argument_list|()
operator|.
name|getCourses
argument_list|()
control|)
block|{
name|courseIds
operator|.
name|add
argument_list|(
name|course
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
return|return
name|courseIds
return|;
block|}
annotation|@
name|Override
specifier|public
name|Boolean
name|execute
parameter_list|(
name|OnlineSectioningServer
name|server
parameter_list|,
name|OnlineSectioningHelper
name|helper
parameter_list|)
block|{
name|helper
operator|.
name|info
argument_list|(
name|getClassIds
argument_list|()
operator|.
name|size
argument_list|()
operator|+
literal|" class assignments changed."
argument_list|)
expr_stmt|;
name|Lock
name|readLock
init|=
name|server
operator|.
name|readLock
argument_list|()
decl_stmt|;
try|try
block|{
name|helper
operator|.
name|beginTransaction
argument_list|()
expr_stmt|;
try|try
block|{
name|OnlineSectioningLog
operator|.
name|Enrollment
operator|.
name|Builder
name|previous
init|=
name|OnlineSectioningLog
operator|.
name|Enrollment
operator|.
name|newBuilder
argument_list|()
operator|.
name|setType
argument_list|(
name|OnlineSectioningLog
operator|.
name|Enrollment
operator|.
name|EnrollmentType
operator|.
name|PREVIOUS
argument_list|)
decl_stmt|;
name|OnlineSectioningLog
operator|.
name|Enrollment
operator|.
name|Builder
name|stored
init|=
name|OnlineSectioningLog
operator|.
name|Enrollment
operator|.
name|newBuilder
argument_list|()
operator|.
name|setType
argument_list|(
name|OnlineSectioningLog
operator|.
name|Enrollment
operator|.
name|EnrollmentType
operator|.
name|STORED
argument_list|)
decl_stmt|;
for|for
control|(
name|Long
name|classId
range|:
name|getClassIds
argument_list|()
control|)
block|{
name|Class_
name|clazz
init|=
name|Class_DAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|classId
argument_list|,
name|helper
operator|.
name|getHibSession
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|clazz
operator|==
literal|null
condition|)
block|{
name|helper
operator|.
name|warn
argument_list|(
literal|"Class "
operator|+
name|classId
operator|+
literal|" wos deleted -- unsupported operation (use reload offering instead)."
argument_list|)
expr_stmt|;
continue|continue;
block|}
name|Lock
name|lock
init|=
name|server
operator|.
name|lockClass
argument_list|(
name|classId
argument_list|,
operator|(
name|List
argument_list|<
name|Long
argument_list|>
operator|)
name|helper
operator|.
name|getHibSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select e.student.uniqueId from StudentClassEnrollment e where "
operator|+
literal|"e.clazz.uniqueId = :classId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"classId"
argument_list|,
name|classId
argument_list|)
operator|.
name|list
argument_list|()
argument_list|)
decl_stmt|;
try|try
block|{
name|Section
name|section
init|=
name|server
operator|.
name|getSection
argument_list|(
name|clazz
operator|.
name|getUniqueId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|section
operator|==
literal|null
condition|)
block|{
name|helper
operator|.
name|warn
argument_list|(
literal|"Class "
operator|+
name|clazz
operator|.
name|getClassLabel
argument_list|()
operator|+
literal|" was added -- unsupported operation (use reload offering instead)."
argument_list|)
expr_stmt|;
continue|continue;
block|}
name|previous
operator|.
name|addSection
argument_list|(
name|OnlineSectioningHelper
operator|.
name|toProto
argument_list|(
name|section
argument_list|)
argument_list|)
expr_stmt|;
name|helper
operator|.
name|info
argument_list|(
literal|"Reloading "
operator|+
name|clazz
operator|.
name|getClassLabel
argument_list|()
argument_list|)
expr_stmt|;
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Assignment
name|a
init|=
name|clazz
operator|.
name|getCommittedAssignment
argument_list|()
decl_stmt|;
name|Placement
name|p
init|=
operator|(
name|a
operator|==
literal|null
condition|?
literal|null
else|:
name|a
operator|.
name|getPlacement
argument_list|()
operator|)
decl_stmt|;
if|if
condition|(
name|p
operator|!=
literal|null
operator|&&
name|p
operator|.
name|getTimeLocation
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|p
operator|.
name|getTimeLocation
argument_list|()
operator|.
name|setDatePattern
argument_list|(
name|p
operator|.
name|getTimeLocation
argument_list|()
operator|.
name|getDatePatternId
argument_list|()
argument_list|,
name|ReloadAllData
operator|.
name|datePatternName
argument_list|(
name|p
operator|.
name|getTimeLocation
argument_list|()
argument_list|,
name|server
operator|.
name|getAcademicSession
argument_list|()
argument_list|)
argument_list|,
name|p
operator|.
name|getTimeLocation
argument_list|()
operator|.
name|getWeekCode
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|section
operator|.
name|setPlacement
argument_list|(
name|p
argument_list|)
expr_stmt|;
name|helper
operator|.
name|info
argument_list|(
literal|"  -- placement: "
operator|+
name|p
argument_list|)
expr_stmt|;
name|int
name|minLimit
init|=
name|clazz
operator|.
name|getExpectedCapacity
argument_list|()
decl_stmt|;
name|int
name|maxLimit
init|=
name|clazz
operator|.
name|getMaxExpectedCapacity
argument_list|()
decl_stmt|;
name|int
name|limit
init|=
name|maxLimit
decl_stmt|;
if|if
condition|(
name|minLimit
operator|<
name|maxLimit
operator|&&
name|p
operator|!=
literal|null
condition|)
block|{
name|int
name|roomLimit
init|=
name|Math
operator|.
name|round
argument_list|(
operator|(
name|clazz
operator|.
name|getRoomRatio
argument_list|()
operator|==
literal|null
condition|?
literal|1.0f
else|:
name|clazz
operator|.
name|getRoomRatio
argument_list|()
operator|)
operator|*
name|p
operator|.
name|getRoomSize
argument_list|()
argument_list|)
decl_stmt|;
name|limit
operator|=
name|Math
operator|.
name|min
argument_list|(
name|Math
operator|.
name|max
argument_list|(
name|minLimit
argument_list|,
name|roomLimit
argument_list|)
argument_list|,
name|maxLimit
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|clazz
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getInstrOfferingConfig
argument_list|()
operator|.
name|isUnlimitedEnrollment
argument_list|()
operator|||
name|limit
operator|>=
literal|9999
condition|)
name|limit
operator|=
operator|-
literal|1
expr_stmt|;
name|section
operator|.
name|setLimit
argument_list|(
name|limit
argument_list|)
expr_stmt|;
name|helper
operator|.
name|info
argument_list|(
literal|"  -- limit: "
operator|+
name|limit
argument_list|)
expr_stmt|;
name|String
name|instructorIds
init|=
literal|""
decl_stmt|;
name|String
name|instructorNames
init|=
literal|""
decl_stmt|;
for|for
control|(
name|Iterator
argument_list|<
name|ClassInstructor
argument_list|>
name|k
init|=
name|clazz
operator|.
name|getClassInstructors
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|k
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|ClassInstructor
name|ci
init|=
name|k
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|ci
operator|.
name|isLead
argument_list|()
condition|)
continue|continue;
if|if
condition|(
operator|!
name|instructorIds
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|instructorIds
operator|+=
literal|":"
expr_stmt|;
name|instructorNames
operator|+=
literal|":"
expr_stmt|;
block|}
name|instructorIds
operator|+=
name|ci
operator|.
name|getInstructor
argument_list|()
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
expr_stmt|;
name|instructorNames
operator|+=
name|ci
operator|.
name|getInstructor
argument_list|()
operator|.
name|getName
argument_list|(
name|DepartmentalInstructor
operator|.
name|sNameFormatShort
argument_list|)
operator|+
literal|"|"
operator|+
operator|(
name|ci
operator|.
name|getInstructor
argument_list|()
operator|.
name|getEmail
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|ci
operator|.
name|getInstructor
argument_list|()
operator|.
name|getEmail
argument_list|()
operator|)
expr_stmt|;
block|}
name|section
operator|.
name|getChoice
argument_list|()
operator|.
name|setInstructor
argument_list|(
name|instructorIds
argument_list|,
name|instructorNames
argument_list|)
expr_stmt|;
name|helper
operator|.
name|info
argument_list|(
literal|"  -- instructor: "
operator|+
name|instructorNames
argument_list|)
expr_stmt|;
name|section
operator|.
name|setName
argument_list|(
name|clazz
operator|.
name|getExternalUniqueId
argument_list|()
operator|==
literal|null
condition|?
name|clazz
operator|.
name|getClassSuffix
argument_list|()
operator|==
literal|null
condition|?
name|clazz
operator|.
name|getSectionNumberString
argument_list|(
name|helper
operator|.
name|getHibSession
argument_list|()
argument_list|)
else|:
name|clazz
operator|.
name|getClassSuffix
argument_list|()
else|:
name|clazz
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|stored
operator|.
name|addSection
argument_list|(
name|OnlineSectioningHelper
operator|.
name|toProto
argument_list|(
name|section
argument_list|)
argument_list|)
expr_stmt|;
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
name|helper
operator|.
name|getAction
argument_list|()
operator|.
name|addEnrollment
argument_list|(
name|previous
argument_list|)
expr_stmt|;
name|helper
operator|.
name|getAction
argument_list|()
operator|.
name|addEnrollment
argument_list|(
name|stored
argument_list|)
expr_stmt|;
name|helper
operator|.
name|commitTransaction
argument_list|()
expr_stmt|;
return|return
literal|true
return|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|helper
operator|.
name|rollbackTransaction
argument_list|()
expr_stmt|;
if|if
condition|(
name|e
operator|instanceof
name|SectioningException
condition|)
throw|throw
operator|(
name|SectioningException
operator|)
name|e
throw|;
throw|throw
operator|new
name|SectioningException
argument_list|(
name|MSG
operator|.
name|exceptionUnknown
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
finally|finally
block|{
name|readLock
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
literal|"class-reassigned"
return|;
block|}
block|}
end_class

end_unit

