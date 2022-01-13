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
name|model
package|;
end_package

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
name|Collection
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
name|Set
import|;
end_import

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|studentsct
operator|.
name|model
operator|.
name|Enrollment
import|;
end_import

begin_import
import|import
name|org
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

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|SerializeWith
argument_list|(
name|XEnrollment
operator|.
name|XEnrollmentSerializer
operator|.
name|class
argument_list|)
specifier|public
class|class
name|XEnrollment
extends|extends
name|XCourseId
implements|implements
name|Serializable
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
name|Long
name|iStudentId
init|=
literal|null
decl_stmt|;
specifier|private
name|Long
name|iConfigId
init|=
literal|null
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|Long
argument_list|>
name|iSectionIds
init|=
operator|new
name|HashSet
argument_list|<
name|Long
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|Date
name|iTimeStamp
init|=
literal|null
decl_stmt|;
specifier|private
name|XApproval
name|iApproval
init|=
literal|null
decl_stmt|;
specifier|private
name|XReservationId
name|iReservation
init|=
literal|null
decl_stmt|;
specifier|public
name|XEnrollment
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
specifier|public
name|XEnrollment
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
name|XEnrollment
parameter_list|(
name|Student
name|student
parameter_list|,
name|CourseOffering
name|course
parameter_list|,
name|OnlineSectioningHelper
name|helper
parameter_list|,
name|Collection
argument_list|<
name|StudentClassEnrollment
argument_list|>
name|enrollments
parameter_list|)
block|{
name|super
argument_list|(
name|course
argument_list|)
expr_stmt|;
name|iStudentId
operator|=
name|student
operator|.
name|getUniqueId
argument_list|()
expr_stmt|;
for|for
control|(
name|StudentClassEnrollment
name|enrl
range|:
name|enrollments
control|)
block|{
if|if
condition|(
name|iConfigId
operator|==
literal|null
condition|)
name|iConfigId
operator|=
name|enrl
operator|.
name|getClazz
argument_list|()
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getInstrOfferingConfig
argument_list|()
operator|.
name|getUniqueId
argument_list|()
expr_stmt|;
name|iSectionIds
operator|.
name|add
argument_list|(
name|enrl
operator|.
name|getClazz
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|iTimeStamp
operator|==
literal|null
operator|||
operator|(
name|enrl
operator|.
name|getTimestamp
argument_list|()
operator|!=
literal|null
operator|&&
name|enrl
operator|.
name|getTimestamp
argument_list|()
operator|.
name|after
argument_list|(
name|iTimeStamp
argument_list|)
operator|)
condition|)
name|iTimeStamp
operator|=
name|enrl
operator|.
name|getTimestamp
argument_list|()
expr_stmt|;
if|if
condition|(
name|iApproval
operator|==
literal|null
operator|&&
name|enrl
operator|.
name|getApprovedBy
argument_list|()
operator|!=
literal|null
operator|&&
name|enrl
operator|.
name|getApprovedDate
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|iApproval
operator|=
operator|new
name|XApproval
argument_list|(
name|enrl
operator|.
name|getApprovedBy
argument_list|()
argument_list|,
name|enrl
operator|.
name|getApprovedDate
argument_list|()
argument_list|,
name|helper
operator|.
name|getApproverName
argument_list|(
name|enrl
operator|.
name|getApprovedBy
argument_list|()
argument_list|,
name|student
operator|.
name|getSession
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
specifier|public
name|XEnrollment
parameter_list|(
name|XEnrollment
name|enrollment
parameter_list|)
block|{
name|super
argument_list|(
name|enrollment
argument_list|)
expr_stmt|;
name|iStudentId
operator|=
name|enrollment
operator|.
name|getStudentId
argument_list|()
expr_stmt|;
name|iConfigId
operator|=
name|enrollment
operator|.
name|getConfigId
argument_list|()
expr_stmt|;
name|iSectionIds
operator|.
name|addAll
argument_list|(
name|enrollment
operator|.
name|getSectionIds
argument_list|()
argument_list|)
expr_stmt|;
name|iTimeStamp
operator|=
name|enrollment
operator|.
name|getTimeStamp
argument_list|()
expr_stmt|;
name|iApproval
operator|=
name|enrollment
operator|.
name|getApproval
argument_list|()
expr_stmt|;
name|iReservation
operator|=
name|enrollment
operator|.
name|getReservation
argument_list|()
expr_stmt|;
block|}
specifier|public
name|XEnrollment
parameter_list|(
name|Enrollment
name|enrollment
parameter_list|)
block|{
name|super
argument_list|(
name|enrollment
operator|.
name|getOffering
argument_list|()
operator|.
name|getId
argument_list|()
argument_list|,
name|enrollment
operator|.
name|getCourse
argument_list|()
operator|.
name|getId
argument_list|()
argument_list|,
name|enrollment
operator|.
name|getCourse
argument_list|()
operator|.
name|getSubjectArea
argument_list|()
argument_list|,
name|enrollment
operator|.
name|getCourse
argument_list|()
operator|.
name|getCourseNumber
argument_list|()
argument_list|)
expr_stmt|;
name|iStudentId
operator|=
name|enrollment
operator|.
name|getStudent
argument_list|()
operator|.
name|getId
argument_list|()
expr_stmt|;
name|iConfigId
operator|=
name|enrollment
operator|.
name|getConfig
argument_list|()
operator|.
name|getId
argument_list|()
expr_stmt|;
for|for
control|(
name|Section
name|section
range|:
name|enrollment
operator|.
name|getSections
argument_list|()
control|)
block|{
name|iSectionIds
operator|.
name|add
argument_list|(
name|section
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|iTimeStamp
operator|=
operator|(
name|enrollment
operator|.
name|getTimeStamp
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
operator|new
name|Date
argument_list|(
name|enrollment
operator|.
name|getTimeStamp
argument_list|()
argument_list|)
operator|)
expr_stmt|;
name|iApproval
operator|=
name|enrollment
operator|.
name|getApproval
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
operator|new
name|XApproval
argument_list|(
name|enrollment
operator|.
name|getApproval
argument_list|()
operator|.
name|split
argument_list|(
literal|":"
argument_list|)
argument_list|)
expr_stmt|;
name|iReservation
operator|=
name|enrollment
operator|.
name|getReservation
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
operator|new
name|XReservationId
argument_list|(
name|enrollment
operator|.
name|getReservation
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
name|XEnrollment
parameter_list|(
name|Student
name|student
parameter_list|,
name|XCourseId
name|courseId
parameter_list|,
name|Long
name|configId
parameter_list|,
name|Collection
argument_list|<
name|Long
argument_list|>
name|sectionIds
parameter_list|)
block|{
name|super
argument_list|(
name|courseId
argument_list|)
expr_stmt|;
name|iStudentId
operator|=
name|student
operator|.
name|getUniqueId
argument_list|()
expr_stmt|;
name|iConfigId
operator|=
name|configId
expr_stmt|;
if|if
condition|(
name|sectionIds
operator|!=
literal|null
condition|)
name|iSectionIds
operator|.
name|addAll
argument_list|(
name|sectionIds
argument_list|)
expr_stmt|;
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
specifier|public
name|Long
name|getConfigId
parameter_list|()
block|{
return|return
name|iConfigId
return|;
block|}
specifier|public
name|Set
argument_list|<
name|Long
argument_list|>
name|getSectionIds
parameter_list|()
block|{
return|return
name|iSectionIds
return|;
block|}
specifier|public
name|Date
name|getTimeStamp
parameter_list|()
block|{
return|return
name|iTimeStamp
return|;
block|}
specifier|public
name|XApproval
name|getApproval
parameter_list|()
block|{
return|return
name|iApproval
return|;
block|}
specifier|public
name|void
name|setApproval
parameter_list|(
name|XApproval
name|approval
parameter_list|)
block|{
name|iApproval
operator|=
name|approval
expr_stmt|;
block|}
specifier|public
name|XReservationId
name|getReservation
parameter_list|()
block|{
return|return
name|iReservation
return|;
block|}
specifier|public
name|void
name|setReservation
parameter_list|(
name|XReservationId
name|reservation
parameter_list|)
block|{
if|if
condition|(
name|reservation
operator|==
literal|null
condition|)
name|iReservation
operator|=
literal|null
expr_stmt|;
if|else if
condition|(
name|reservation
operator|instanceof
name|XReservation
condition|)
name|iReservation
operator|=
operator|new
name|XReservationId
argument_list|(
name|reservation
argument_list|)
expr_stmt|;
else|else
name|iReservation
operator|=
name|reservation
expr_stmt|;
block|}
specifier|public
name|void
name|setTimeStamp
parameter_list|(
name|Date
name|ts
parameter_list|)
block|{
name|iTimeStamp
operator|=
name|ts
expr_stmt|;
block|}
specifier|public
name|float
name|getCredit
parameter_list|(
name|OnlineSectioningServer
name|server
parameter_list|)
block|{
name|XOffering
name|offering
init|=
name|server
operator|.
name|getOffering
argument_list|(
name|getOfferingId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|offering
operator|==
literal|null
condition|)
return|return
literal|0f
return|;
name|Float
name|sectionCredit
init|=
literal|null
decl_stmt|;
for|for
control|(
name|Long
name|sectionId
range|:
name|getSectionIds
argument_list|()
control|)
block|{
name|XSection
name|section
init|=
name|offering
operator|.
name|getSection
argument_list|(
name|sectionId
argument_list|)
decl_stmt|;
name|Float
name|credit
init|=
operator|(
name|section
operator|==
literal|null
condition|?
literal|null
else|:
name|section
operator|.
name|getCreditOverride
argument_list|(
name|getCourseId
argument_list|()
argument_list|)
operator|)
decl_stmt|;
if|if
condition|(
name|credit
operator|!=
literal|null
condition|)
block|{
name|sectionCredit
operator|=
operator|(
name|sectionCredit
operator|==
literal|null
condition|?
literal|0f
else|:
name|sectionCredit
operator|.
name|floatValue
argument_list|()
operator|)
operator|+
name|credit
expr_stmt|;
block|}
block|}
if|if
condition|(
name|sectionCredit
operator|!=
literal|null
condition|)
return|return
name|sectionCredit
return|;
name|XCourse
name|course
init|=
name|offering
operator|.
name|getCourse
argument_list|(
name|getCourseId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|course
operator|!=
literal|null
operator|&&
name|course
operator|.
name|hasCredit
argument_list|()
condition|)
return|return
name|course
operator|.
name|getMinCredit
argument_list|()
return|;
return|return
literal|0f
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|compareTo
parameter_list|(
name|XCourseId
name|courseId
parameter_list|)
block|{
if|if
condition|(
name|courseId
operator|instanceof
name|XEnrollment
condition|)
block|{
name|XEnrollment
name|enrollment
init|=
operator|(
name|XEnrollment
operator|)
name|courseId
decl_stmt|;
name|int
name|cmp
init|=
name|getTimeStamp
argument_list|()
operator|.
name|compareTo
argument_list|(
name|enrollment
operator|.
name|getTimeStamp
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
name|cmp
operator|=
name|getStudentId
argument_list|()
operator|.
name|compareTo
argument_list|(
name|enrollment
operator|.
name|getStudentId
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
block|}
return|return
name|super
operator|.
name|compareTo
argument_list|(
name|courseId
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
if|if
condition|(
name|o
operator|==
literal|null
condition|)
return|return
literal|false
return|;
if|if
condition|(
operator|!
operator|(
name|o
operator|instanceof
name|XEnrollment
operator|)
condition|)
block|{
if|if
condition|(
name|o
operator|instanceof
name|XCourseId
condition|)
return|return
operator|(
operator|(
name|XCourseId
operator|)
name|o
operator|)
operator|.
name|equals
argument_list|(
name|this
argument_list|)
return|;
return|return
literal|false
return|;
block|}
name|XEnrollment
name|e
init|=
operator|(
name|XEnrollment
operator|)
name|o
decl_stmt|;
return|return
name|getCourseId
argument_list|()
operator|.
name|equals
argument_list|(
name|e
operator|.
name|getCourseId
argument_list|()
argument_list|)
operator|&&
name|getConfigId
argument_list|()
operator|.
name|equals
argument_list|(
name|e
operator|.
name|getConfigId
argument_list|()
argument_list|)
operator|&&
name|getSectionIds
argument_list|()
operator|.
name|equals
argument_list|(
name|e
operator|.
name|getSectionIds
argument_list|()
argument_list|)
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
name|getCourseName
argument_list|()
operator|+
literal|"/"
operator|+
name|getSectionIds
argument_list|()
operator|+
operator|(
name|getApproval
argument_list|()
operator|!=
literal|null
condition|?
name|getReservation
argument_list|()
operator|!=
literal|null
condition|?
literal|" (ar)"
else|:
literal|" (a)"
else|:
name|getReservation
argument_list|()
operator|!=
literal|null
condition|?
literal|" (r)"
else|:
literal|""
operator|)
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
name|iStudentId
operator|=
name|in
operator|.
name|readLong
argument_list|()
expr_stmt|;
name|iConfigId
operator|=
name|in
operator|.
name|readLong
argument_list|()
expr_stmt|;
name|iSectionIds
operator|.
name|clear
argument_list|()
expr_stmt|;
name|int
name|nrSections
init|=
name|in
operator|.
name|readInt
argument_list|()
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
name|nrSections
condition|;
name|i
operator|++
control|)
name|iSectionIds
operator|.
name|add
argument_list|(
name|in
operator|.
name|readLong
argument_list|()
argument_list|)
expr_stmt|;
name|iTimeStamp
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
name|iApproval
operator|=
operator|(
name|in
operator|.
name|readBoolean
argument_list|()
condition|?
operator|new
name|XApproval
argument_list|(
name|in
argument_list|)
else|:
literal|null
operator|)
expr_stmt|;
name|iReservation
operator|=
operator|(
name|in
operator|.
name|readBoolean
argument_list|()
condition|?
operator|new
name|XReservationId
argument_list|(
name|in
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
name|writeLong
argument_list|(
name|iStudentId
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeLong
argument_list|(
name|iConfigId
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeInt
argument_list|(
name|iSectionIds
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|Long
name|sectionId
range|:
name|iSectionIds
control|)
name|out
operator|.
name|writeLong
argument_list|(
name|sectionId
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeBoolean
argument_list|(
name|iTimeStamp
operator|!=
literal|null
argument_list|)
expr_stmt|;
if|if
condition|(
name|iTimeStamp
operator|!=
literal|null
condition|)
name|out
operator|.
name|writeLong
argument_list|(
name|iTimeStamp
operator|.
name|getTime
argument_list|()
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeBoolean
argument_list|(
name|iApproval
operator|!=
literal|null
argument_list|)
expr_stmt|;
if|if
condition|(
name|iApproval
operator|!=
literal|null
condition|)
name|iApproval
operator|.
name|writeExternal
argument_list|(
name|out
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeBoolean
argument_list|(
name|iReservation
operator|!=
literal|null
argument_list|)
expr_stmt|;
if|if
condition|(
name|iReservation
operator|!=
literal|null
condition|)
name|iReservation
operator|.
name|writeExternal
argument_list|(
name|out
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
class|class
name|XEnrollmentSerializer
implements|implements
name|Externalizer
argument_list|<
name|XEnrollment
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
name|XEnrollment
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
name|XEnrollment
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
name|XEnrollment
argument_list|(
name|input
argument_list|)
return|;
block|}
block|}
block|}
end_class

end_unit

