begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.5 (University Timetabling Application)  * Copyright (C) 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|model
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|Externalizable
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|ObjectInput
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|ObjectOutput
import|;
end_import

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
name|BitSet
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
name|Date
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
name|java
operator|.
name|util
operator|.
name|TreeSet
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
name|AcademicAreaCode
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
name|CourseRequest
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
name|FreeTimeRequest
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
name|Request
import|;
end_import

begin_import
import|import
name|org
operator|.
name|infinispan
operator|.
name|commons
operator|.
name|marshall
operator|.
name|Externalizer
import|;
end_import

begin_import
import|import
name|org
operator|.
name|infinispan
operator|.
name|commons
operator|.
name|marshall
operator|.
name|SerializeWith
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
name|AcademicAreaClassification
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
name|PosMajor
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
name|StudentAccomodation
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
name|StudentClassEnrollment
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
name|StudentGroup
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

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|SerializeWith
argument_list|(
name|XStudent
operator|.
name|XStudentSerializer
operator|.
name|class
argument_list|)
specifier|public
class|class
name|XStudent
extends|extends
name|XStudentId
implements|implements
name|Externalizable
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
name|List
argument_list|<
name|XAcademicAreaCode
argument_list|>
name|iAcadAreaClassifs
init|=
operator|new
name|ArrayList
argument_list|<
name|XAcademicAreaCode
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|List
argument_list|<
name|XAcademicAreaCode
argument_list|>
name|iMajors
init|=
operator|new
name|ArrayList
argument_list|<
name|XAcademicAreaCode
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|List
argument_list|<
name|String
argument_list|>
name|iGroups
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|List
argument_list|<
name|String
argument_list|>
name|iAccomodations
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|List
argument_list|<
name|XRequest
argument_list|>
name|iRequests
init|=
operator|new
name|ArrayList
argument_list|<
name|XRequest
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|String
name|iStatus
init|=
literal|null
decl_stmt|;
specifier|private
name|String
name|iEmail
init|=
literal|null
decl_stmt|;
specifier|private
name|Date
name|iEmailTimeStamp
init|=
literal|null
decl_stmt|;
specifier|public
name|XStudent
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
specifier|public
name|XStudent
parameter_list|(
name|ObjectInput
name|in
parameter_list|)
throws|throws
name|IOException
throws|,
name|ClassNotFoundException
block|{
name|super
argument_list|()
expr_stmt|;
name|readExternal
argument_list|(
name|in
argument_list|)
expr_stmt|;
block|}
specifier|public
name|XStudent
parameter_list|(
name|Student
name|student
parameter_list|,
name|OnlineSectioningHelper
name|helper
parameter_list|,
name|BitSet
name|freeTimePattern
parameter_list|)
block|{
name|super
argument_list|(
name|student
argument_list|,
name|helper
argument_list|)
expr_stmt|;
name|iStatus
operator|=
name|student
operator|.
name|getSectioningStatus
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
name|student
operator|.
name|getSectioningStatus
argument_list|()
operator|.
name|getReference
argument_list|()
expr_stmt|;
name|iEmail
operator|=
name|student
operator|.
name|getEmail
argument_list|()
expr_stmt|;
name|iEmailTimeStamp
operator|=
name|student
operator|.
name|getScheduleEmailedDate
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
name|student
operator|.
name|getScheduleEmailedDate
argument_list|()
expr_stmt|;
for|for
control|(
name|AcademicAreaClassification
name|aac
range|:
name|student
operator|.
name|getAcademicAreaClassifications
argument_list|()
control|)
block|{
name|iAcadAreaClassifs
operator|.
name|add
argument_list|(
operator|new
name|XAcademicAreaCode
argument_list|(
name|aac
operator|.
name|getAcademicArea
argument_list|()
operator|.
name|getAcademicAreaAbbreviation
argument_list|()
argument_list|,
name|aac
operator|.
name|getAcademicClassification
argument_list|()
operator|.
name|getCode
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
for|for
control|(
name|PosMajor
name|major
range|:
name|aac
operator|.
name|getAcademicArea
argument_list|()
operator|.
name|getPosMajors
argument_list|()
control|)
if|if
condition|(
name|student
operator|.
name|getPosMajors
argument_list|()
operator|.
name|contains
argument_list|(
name|major
argument_list|)
condition|)
name|iMajors
operator|.
name|add
argument_list|(
operator|new
name|XAcademicAreaCode
argument_list|(
name|aac
operator|.
name|getAcademicArea
argument_list|()
operator|.
name|getAcademicAreaAbbreviation
argument_list|()
argument_list|,
name|major
operator|.
name|getCode
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|StudentGroup
name|group
range|:
name|student
operator|.
name|getGroups
argument_list|()
control|)
name|iGroups
operator|.
name|add
argument_list|(
name|group
operator|.
name|getGroupAbbreviation
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|StudentAccomodation
name|accomodation
range|:
name|student
operator|.
name|getAccomodations
argument_list|()
control|)
name|iAccomodations
operator|.
name|add
argument_list|(
name|accomodation
operator|.
name|getAbbreviation
argument_list|()
argument_list|)
expr_stmt|;
name|TreeSet
argument_list|<
name|CourseDemand
argument_list|>
name|demands
init|=
operator|new
name|TreeSet
argument_list|<
name|CourseDemand
argument_list|>
argument_list|(
operator|new
name|Comparator
argument_list|<
name|CourseDemand
argument_list|>
argument_list|()
block|{
specifier|public
name|int
name|compare
parameter_list|(
name|CourseDemand
name|d1
parameter_list|,
name|CourseDemand
name|d2
parameter_list|)
block|{
if|if
condition|(
name|d1
operator|.
name|isAlternative
argument_list|()
operator|&&
operator|!
name|d2
operator|.
name|isAlternative
argument_list|()
condition|)
return|return
literal|1
return|;
if|if
condition|(
operator|!
name|d1
operator|.
name|isAlternative
argument_list|()
operator|&&
name|d2
operator|.
name|isAlternative
argument_list|()
condition|)
return|return
operator|-
literal|1
return|;
name|int
name|cmp
init|=
name|d1
operator|.
name|getPriority
argument_list|()
operator|.
name|compareTo
argument_list|(
name|d2
operator|.
name|getPriority
argument_list|()
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
name|d1
operator|.
name|getUniqueId
argument_list|()
operator|.
name|compareTo
argument_list|(
name|d2
operator|.
name|getUniqueId
argument_list|()
argument_list|)
return|;
block|}
block|}
argument_list|)
decl_stmt|;
name|demands
operator|.
name|addAll
argument_list|(
name|student
operator|.
name|getCourseDemands
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|CourseDemand
name|cd
range|:
name|demands
control|)
block|{
if|if
condition|(
name|cd
operator|.
name|getFreeTime
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|iRequests
operator|.
name|add
argument_list|(
operator|new
name|XFreeTimeRequest
argument_list|(
name|cd
argument_list|,
name|freeTimePattern
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
operator|!
name|cd
operator|.
name|getCourseRequests
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|iRequests
operator|.
name|add
argument_list|(
operator|new
name|XCourseRequest
argument_list|(
name|cd
argument_list|,
name|helper
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
name|Map
argument_list|<
name|CourseOffering
argument_list|,
name|List
argument_list|<
name|StudentClassEnrollment
argument_list|>
argument_list|>
name|unmatchedCourses
init|=
operator|new
name|HashMap
argument_list|<
name|CourseOffering
argument_list|,
name|List
argument_list|<
name|StudentClassEnrollment
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|StudentClassEnrollment
name|enrollment
range|:
name|student
operator|.
name|getClassEnrollments
argument_list|()
control|)
block|{
if|if
condition|(
name|getRequestForCourse
argument_list|(
name|enrollment
operator|.
name|getCourseOffering
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|!=
literal|null
condition|)
continue|continue;
name|List
argument_list|<
name|StudentClassEnrollment
argument_list|>
name|classes
init|=
name|unmatchedCourses
operator|.
name|get
argument_list|(
name|enrollment
operator|.
name|getCourseOffering
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|classes
operator|==
literal|null
condition|)
block|{
name|classes
operator|=
operator|new
name|ArrayList
argument_list|<
name|StudentClassEnrollment
argument_list|>
argument_list|()
expr_stmt|;
name|unmatchedCourses
operator|.
name|put
argument_list|(
name|enrollment
operator|.
name|getCourseOffering
argument_list|()
argument_list|,
name|classes
argument_list|)
expr_stmt|;
block|}
name|classes
operator|.
name|add
argument_list|(
name|enrollment
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|unmatchedCourses
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|int
name|priority
init|=
literal|0
decl_stmt|;
for|for
control|(
name|XRequest
name|request
range|:
name|iRequests
control|)
if|if
condition|(
operator|!
name|request
operator|.
name|isAlternative
argument_list|()
operator|&&
name|request
operator|.
name|getPriority
argument_list|()
operator|>
name|priority
condition|)
name|priority
operator|=
name|request
operator|.
name|getPriority
argument_list|()
expr_stmt|;
for|for
control|(
name|CourseOffering
name|course
range|:
operator|new
name|TreeSet
argument_list|<
name|CourseOffering
argument_list|>
argument_list|(
name|unmatchedCourses
operator|.
name|keySet
argument_list|()
argument_list|)
control|)
block|{
name|List
argument_list|<
name|StudentClassEnrollment
argument_list|>
name|classes
init|=
name|unmatchedCourses
operator|.
name|get
argument_list|(
name|course
argument_list|)
decl_stmt|;
name|iRequests
operator|.
name|add
argument_list|(
operator|new
name|XCourseRequest
argument_list|(
name|student
argument_list|,
name|course
argument_list|,
operator|++
name|priority
argument_list|,
name|helper
argument_list|,
name|classes
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
name|Collections
operator|.
name|sort
argument_list|(
name|iRequests
argument_list|)
expr_stmt|;
block|}
specifier|public
name|XStudent
parameter_list|(
name|XStudent
name|student
parameter_list|)
block|{
name|super
argument_list|(
name|student
argument_list|)
expr_stmt|;
name|iStatus
operator|=
name|student
operator|.
name|getStatus
argument_list|()
expr_stmt|;
name|iEmail
operator|=
name|student
operator|.
name|getEmail
argument_list|()
expr_stmt|;
name|iEmailTimeStamp
operator|=
name|student
operator|.
name|getEmailTimeStamp
argument_list|()
expr_stmt|;
name|iAcadAreaClassifs
operator|.
name|addAll
argument_list|(
name|student
operator|.
name|getAcademicAreaClasiffications
argument_list|()
argument_list|)
expr_stmt|;
name|iMajors
operator|.
name|addAll
argument_list|(
name|student
operator|.
name|getMajors
argument_list|()
argument_list|)
expr_stmt|;
name|iGroups
operator|.
name|addAll
argument_list|(
name|student
operator|.
name|getGroups
argument_list|()
argument_list|)
expr_stmt|;
name|iAccomodations
operator|.
name|addAll
argument_list|(
name|student
operator|.
name|getAccomodations
argument_list|()
argument_list|)
expr_stmt|;
name|iRequests
operator|.
name|addAll
argument_list|(
name|student
operator|.
name|getRequests
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
name|XStudent
parameter_list|(
name|XStudent
name|student
parameter_list|,
name|Collection
argument_list|<
name|CourseDemand
argument_list|>
name|demands
parameter_list|,
name|OnlineSectioningHelper
name|helper
parameter_list|,
name|BitSet
name|freeTimePattern
parameter_list|)
block|{
name|super
argument_list|(
name|student
argument_list|)
expr_stmt|;
name|iStatus
operator|=
name|student
operator|.
name|getStatus
argument_list|()
expr_stmt|;
name|iEmail
operator|=
name|student
operator|.
name|getEmail
argument_list|()
expr_stmt|;
name|iEmailTimeStamp
operator|=
name|student
operator|.
name|getEmailTimeStamp
argument_list|()
expr_stmt|;
name|iAcadAreaClassifs
operator|.
name|addAll
argument_list|(
name|student
operator|.
name|getAcademicAreaClasiffications
argument_list|()
argument_list|)
expr_stmt|;
name|iMajors
operator|.
name|addAll
argument_list|(
name|student
operator|.
name|getMajors
argument_list|()
argument_list|)
expr_stmt|;
name|iGroups
operator|.
name|addAll
argument_list|(
name|student
operator|.
name|getGroups
argument_list|()
argument_list|)
expr_stmt|;
name|iAccomodations
operator|.
name|addAll
argument_list|(
name|student
operator|.
name|getAccomodations
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|demands
operator|!=
literal|null
condition|)
for|for
control|(
name|CourseDemand
name|cd
range|:
name|demands
control|)
block|{
if|if
condition|(
name|cd
operator|.
name|getFreeTime
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|iRequests
operator|.
name|add
argument_list|(
operator|new
name|XFreeTimeRequest
argument_list|(
name|cd
argument_list|,
name|freeTimePattern
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
operator|!
name|cd
operator|.
name|getCourseRequests
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|iRequests
operator|.
name|add
argument_list|(
operator|new
name|XCourseRequest
argument_list|(
name|cd
argument_list|,
name|helper
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
name|Collections
operator|.
name|sort
argument_list|(
name|iRequests
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|List
argument_list|<
name|XRequest
argument_list|>
name|loadRequests
parameter_list|(
name|Student
name|student
parameter_list|,
name|OnlineSectioningHelper
name|helper
parameter_list|,
name|BitSet
name|freeTimePattern
parameter_list|)
block|{
name|List
argument_list|<
name|XRequest
argument_list|>
name|requests
init|=
operator|new
name|ArrayList
argument_list|<
name|XRequest
argument_list|>
argument_list|()
decl_stmt|;
name|TreeSet
argument_list|<
name|CourseDemand
argument_list|>
name|demands
init|=
operator|new
name|TreeSet
argument_list|<
name|CourseDemand
argument_list|>
argument_list|(
operator|new
name|Comparator
argument_list|<
name|CourseDemand
argument_list|>
argument_list|()
block|{
specifier|public
name|int
name|compare
parameter_list|(
name|CourseDemand
name|d1
parameter_list|,
name|CourseDemand
name|d2
parameter_list|)
block|{
if|if
condition|(
name|d1
operator|.
name|isAlternative
argument_list|()
operator|&&
operator|!
name|d2
operator|.
name|isAlternative
argument_list|()
condition|)
return|return
literal|1
return|;
if|if
condition|(
operator|!
name|d1
operator|.
name|isAlternative
argument_list|()
operator|&&
name|d2
operator|.
name|isAlternative
argument_list|()
condition|)
return|return
operator|-
literal|1
return|;
name|int
name|cmp
init|=
name|d1
operator|.
name|getPriority
argument_list|()
operator|.
name|compareTo
argument_list|(
name|d2
operator|.
name|getPriority
argument_list|()
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
name|d1
operator|.
name|getUniqueId
argument_list|()
operator|.
name|compareTo
argument_list|(
name|d2
operator|.
name|getUniqueId
argument_list|()
argument_list|)
return|;
block|}
block|}
argument_list|)
decl_stmt|;
name|demands
operator|.
name|addAll
argument_list|(
name|student
operator|.
name|getCourseDemands
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|CourseDemand
name|cd
range|:
name|demands
control|)
block|{
if|if
condition|(
name|cd
operator|.
name|getFreeTime
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|requests
operator|.
name|add
argument_list|(
operator|new
name|XFreeTimeRequest
argument_list|(
name|cd
argument_list|,
name|freeTimePattern
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
operator|!
name|cd
operator|.
name|getCourseRequests
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|requests
operator|.
name|add
argument_list|(
operator|new
name|XCourseRequest
argument_list|(
name|cd
argument_list|,
name|helper
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
name|Collections
operator|.
name|sort
argument_list|(
name|requests
argument_list|)
expr_stmt|;
return|return
name|requests
return|;
block|}
specifier|public
name|XStudent
parameter_list|(
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
name|Student
name|student
parameter_list|)
block|{
name|super
argument_list|(
name|student
argument_list|)
expr_stmt|;
name|iStatus
operator|=
name|student
operator|.
name|getStatus
argument_list|()
expr_stmt|;
name|iEmailTimeStamp
operator|=
operator|(
name|student
operator|.
name|getEmailTimeStamp
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
operator|new
name|Date
argument_list|(
name|student
operator|.
name|getEmailTimeStamp
argument_list|()
argument_list|)
operator|)
expr_stmt|;
for|for
control|(
name|AcademicAreaCode
name|aac
range|:
name|student
operator|.
name|getAcademicAreaClasiffications
argument_list|()
control|)
name|iAcadAreaClassifs
operator|.
name|add
argument_list|(
operator|new
name|XAcademicAreaCode
argument_list|(
name|aac
operator|.
name|getArea
argument_list|()
argument_list|,
name|aac
operator|.
name|getCode
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
for|for
control|(
name|AcademicAreaCode
name|aac
range|:
name|student
operator|.
name|getMajors
argument_list|()
control|)
name|iMajors
operator|.
name|add
argument_list|(
operator|new
name|XAcademicAreaCode
argument_list|(
name|aac
operator|.
name|getArea
argument_list|()
argument_list|,
name|aac
operator|.
name|getCode
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
for|for
control|(
name|AcademicAreaCode
name|aac
range|:
name|student
operator|.
name|getMinors
argument_list|()
control|)
block|{
if|if
condition|(
literal|"A"
operator|.
name|equals
argument_list|(
name|aac
operator|.
name|getArea
argument_list|()
argument_list|)
condition|)
name|iAccomodations
operator|.
name|add
argument_list|(
name|aac
operator|.
name|getCode
argument_list|()
argument_list|)
expr_stmt|;
else|else
name|iGroups
operator|.
name|add
argument_list|(
name|aac
operator|.
name|getCode
argument_list|()
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|Request
name|request
range|:
name|student
operator|.
name|getRequests
argument_list|()
control|)
block|{
if|if
condition|(
name|request
operator|instanceof
name|FreeTimeRequest
condition|)
block|{
name|iRequests
operator|.
name|add
argument_list|(
operator|new
name|XFreeTimeRequest
argument_list|(
operator|(
name|FreeTimeRequest
operator|)
name|request
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|request
operator|instanceof
name|CourseRequest
condition|)
block|{
name|iRequests
operator|.
name|add
argument_list|(
operator|new
name|XCourseRequest
argument_list|(
operator|(
name|CourseRequest
operator|)
name|request
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
specifier|public
name|XCourseRequest
name|getRequestForCourse
parameter_list|(
name|Long
name|courseId
parameter_list|)
block|{
for|for
control|(
name|XRequest
name|request
range|:
name|iRequests
control|)
if|if
condition|(
name|request
operator|instanceof
name|XCourseRequest
operator|&&
operator|(
operator|(
name|XCourseRequest
operator|)
name|request
operator|)
operator|.
name|hasCourse
argument_list|(
name|courseId
argument_list|)
condition|)
return|return
operator|(
name|XCourseRequest
operator|)
name|request
return|;
return|return
literal|null
return|;
block|}
comment|/**      * List of academic area - classification codes ({@link AcademicAreaCode})      * for the given student      */
specifier|public
name|List
argument_list|<
name|XAcademicAreaCode
argument_list|>
name|getAcademicAreaClasiffications
parameter_list|()
block|{
return|return
name|iAcadAreaClassifs
return|;
block|}
comment|/**      * List of major codes ({@link AcademicAreaCode}) for the given student      */
specifier|public
name|List
argument_list|<
name|XAcademicAreaCode
argument_list|>
name|getMajors
parameter_list|()
block|{
return|return
name|iMajors
return|;
block|}
comment|/**      * List of group codes for the given student      */
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getGroups
parameter_list|()
block|{
return|return
name|iGroups
return|;
block|}
comment|/**      * List of group codes for the given student      */
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getAccomodations
parameter_list|()
block|{
return|return
name|iAccomodations
return|;
block|}
comment|/**      * Get student status (online sectioning only)      */
specifier|public
name|String
name|getStatus
parameter_list|()
block|{
return|return
name|iStatus
return|;
block|}
comment|/**      * Set student status      */
specifier|public
name|void
name|setStatus
parameter_list|(
name|String
name|status
parameter_list|)
block|{
name|iStatus
operator|=
name|status
expr_stmt|;
block|}
comment|/**      * Get last email time stamp (online sectioning only)      */
specifier|public
name|Date
name|getEmailTimeStamp
parameter_list|()
block|{
return|return
name|iEmailTimeStamp
return|;
block|}
comment|/**      * Set last email time stamp      */
specifier|public
name|void
name|setEmailTimeStamp
parameter_list|(
name|Date
name|emailTimeStamp
parameter_list|)
block|{
name|iEmailTimeStamp
operator|=
name|emailTimeStamp
expr_stmt|;
block|}
specifier|public
name|List
argument_list|<
name|XRequest
argument_list|>
name|getRequests
parameter_list|()
block|{
return|return
name|iRequests
return|;
block|}
specifier|public
name|String
name|getEmail
parameter_list|()
block|{
return|return
name|iEmail
return|;
block|}
comment|/**      * True if the given request can be assigned to the student. A request      * cannot be assigned to a student when the student already has the desired      * number of requests assigned (i.e., number of non-alternative course      * requests).      **/
specifier|public
name|boolean
name|canAssign
parameter_list|(
name|XCourseRequest
name|request
parameter_list|)
block|{
if|if
condition|(
name|request
operator|.
name|getEnrollment
argument_list|()
operator|!=
literal|null
condition|)
return|return
literal|true
return|;
name|int
name|alt
init|=
literal|0
decl_stmt|;
name|boolean
name|found
init|=
literal|false
decl_stmt|;
for|for
control|(
name|XRequest
name|r
range|:
name|iRequests
control|)
block|{
if|if
condition|(
name|r
operator|.
name|equals
argument_list|(
name|request
argument_list|)
condition|)
name|found
operator|=
literal|true
expr_stmt|;
name|boolean
name|course
init|=
operator|(
name|r
operator|instanceof
name|XCourseRequest
operator|)
decl_stmt|;
name|boolean
name|assigned
init|=
operator|(
operator|!
name|course
operator|||
operator|(
operator|(
name|XCourseRequest
operator|)
name|r
operator|)
operator|.
name|getEnrollment
argument_list|()
operator|!=
literal|null
operator|||
name|r
operator|.
name|equals
argument_list|(
name|request
argument_list|)
operator|)
decl_stmt|;
name|boolean
name|waitlist
init|=
operator|(
name|course
operator|&&
operator|(
operator|(
name|XCourseRequest
operator|)
name|r
operator|)
operator|.
name|isWaitlist
argument_list|()
operator|)
decl_stmt|;
if|if
condition|(
name|r
operator|.
name|isAlternative
argument_list|()
condition|)
block|{
if|if
condition|(
name|assigned
operator|||
operator|(
operator|!
name|found
operator|&&
name|waitlist
operator|)
condition|)
name|alt
operator|--
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|course
operator|&&
operator|!
name|waitlist
operator|&&
operator|!
name|assigned
condition|)
name|alt
operator|++
expr_stmt|;
block|}
block|}
return|return
operator|(
name|alt
operator|>=
literal|0
operator|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|getName
argument_list|()
operator|+
literal|" ("
operator|+
name|getExternalId
argument_list|()
operator|+
literal|")"
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|readExternal
parameter_list|(
name|ObjectInput
name|in
parameter_list|)
throws|throws
name|IOException
throws|,
name|ClassNotFoundException
block|{
name|super
operator|.
name|readExternal
argument_list|(
name|in
argument_list|)
expr_stmt|;
name|int
name|nrAcadAreClassifs
init|=
name|in
operator|.
name|readInt
argument_list|()
decl_stmt|;
name|iAcadAreaClassifs
operator|.
name|clear
argument_list|()
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|nrAcadAreClassifs
condition|;
name|i
operator|++
control|)
name|iAcadAreaClassifs
operator|.
name|add
argument_list|(
operator|new
name|XAcademicAreaCode
argument_list|(
name|in
argument_list|)
argument_list|)
expr_stmt|;
name|int
name|nrMajors
init|=
name|in
operator|.
name|readInt
argument_list|()
decl_stmt|;
name|iMajors
operator|.
name|clear
argument_list|()
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|nrMajors
condition|;
name|i
operator|++
control|)
name|iMajors
operator|.
name|add
argument_list|(
operator|new
name|XAcademicAreaCode
argument_list|(
name|in
argument_list|)
argument_list|)
expr_stmt|;
name|int
name|nrGroups
init|=
name|in
operator|.
name|readInt
argument_list|()
decl_stmt|;
name|iGroups
operator|.
name|clear
argument_list|()
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|nrGroups
condition|;
name|i
operator|++
control|)
name|iGroups
operator|.
name|add
argument_list|(
operator|(
name|String
operator|)
name|in
operator|.
name|readObject
argument_list|()
argument_list|)
expr_stmt|;
name|int
name|nrAccomodations
init|=
name|in
operator|.
name|readInt
argument_list|()
decl_stmt|;
name|iAccomodations
operator|.
name|clear
argument_list|()
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|nrAccomodations
condition|;
name|i
operator|++
control|)
name|iAccomodations
operator|.
name|add
argument_list|(
operator|(
name|String
operator|)
name|in
operator|.
name|readObject
argument_list|()
argument_list|)
expr_stmt|;
name|int
name|nrRequests
init|=
name|in
operator|.
name|readInt
argument_list|()
decl_stmt|;
name|iRequests
operator|.
name|clear
argument_list|()
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|nrRequests
condition|;
name|i
operator|++
control|)
name|iRequests
operator|.
name|add
argument_list|(
name|in
operator|.
name|readBoolean
argument_list|()
condition|?
operator|new
name|XCourseRequest
argument_list|(
name|in
argument_list|)
else|:
operator|new
name|XFreeTimeRequest
argument_list|(
name|in
argument_list|)
argument_list|)
expr_stmt|;
name|iStatus
operator|=
operator|(
name|String
operator|)
name|in
operator|.
name|readObject
argument_list|()
expr_stmt|;
name|iEmail
operator|=
operator|(
name|String
operator|)
name|in
operator|.
name|readObject
argument_list|()
expr_stmt|;
name|iEmailTimeStamp
operator|=
operator|(
name|in
operator|.
name|readBoolean
argument_list|()
condition|?
operator|new
name|Date
argument_list|(
name|in
operator|.
name|readLong
argument_list|()
argument_list|)
else|:
literal|null
operator|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|writeExternal
parameter_list|(
name|ObjectOutput
name|out
parameter_list|)
throws|throws
name|IOException
block|{
name|super
operator|.
name|writeExternal
argument_list|(
name|out
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeInt
argument_list|(
name|iAcadAreaClassifs
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|XAcademicAreaCode
name|aac
range|:
name|iAcadAreaClassifs
control|)
name|aac
operator|.
name|writeExternal
argument_list|(
name|out
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeInt
argument_list|(
name|iMajors
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|XAcademicAreaCode
name|major
range|:
name|iMajors
control|)
name|major
operator|.
name|writeExternal
argument_list|(
name|out
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeInt
argument_list|(
name|iGroups
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|String
name|group
range|:
name|iGroups
control|)
name|out
operator|.
name|writeObject
argument_list|(
name|group
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeInt
argument_list|(
name|iAccomodations
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|String
name|accomodation
range|:
name|iAccomodations
control|)
name|out
operator|.
name|writeObject
argument_list|(
name|accomodation
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeInt
argument_list|(
name|iRequests
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|XRequest
name|request
range|:
name|iRequests
control|)
if|if
condition|(
name|request
operator|instanceof
name|XCourseRequest
condition|)
block|{
name|out
operator|.
name|writeBoolean
argument_list|(
literal|true
argument_list|)
expr_stmt|;
operator|(
operator|(
name|XCourseRequest
operator|)
name|request
operator|)
operator|.
name|writeExternal
argument_list|(
name|out
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|out
operator|.
name|writeBoolean
argument_list|(
literal|false
argument_list|)
expr_stmt|;
operator|(
operator|(
name|XFreeTimeRequest
operator|)
name|request
operator|)
operator|.
name|writeExternal
argument_list|(
name|out
argument_list|)
expr_stmt|;
block|}
name|out
operator|.
name|writeObject
argument_list|(
name|iStatus
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeObject
argument_list|(
name|iEmail
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeBoolean
argument_list|(
name|iEmailTimeStamp
operator|!=
literal|null
argument_list|)
expr_stmt|;
if|if
condition|(
name|iEmailTimeStamp
operator|!=
literal|null
condition|)
name|out
operator|.
name|writeLong
argument_list|(
name|iEmailTimeStamp
operator|.
name|getTime
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
class|class
name|XStudentSerializer
implements|implements
name|Externalizer
argument_list|<
name|XStudent
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
annotation|@
name|Override
specifier|public
name|void
name|writeObject
parameter_list|(
name|ObjectOutput
name|output
parameter_list|,
name|XStudent
name|object
parameter_list|)
throws|throws
name|IOException
block|{
name|object
operator|.
name|writeExternal
argument_list|(
name|output
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|XStudent
name|readObject
parameter_list|(
name|ObjectInput
name|input
parameter_list|)
throws|throws
name|IOException
throws|,
name|ClassNotFoundException
block|{
return|return
operator|new
name|XStudent
argument_list|(
name|input
argument_list|)
return|;
block|}
block|}
block|}
end_class

end_unit

