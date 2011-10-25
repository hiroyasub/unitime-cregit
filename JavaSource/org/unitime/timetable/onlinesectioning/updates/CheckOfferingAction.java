begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.3 (University Timetabling Application)  * Copyright (C) 2011, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|Date
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
name|ifs
operator|.
name|util
operator|.
name|DataProperties
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
name|extension
operator|.
name|DistanceConflict
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
name|extension
operator|.
name|TimeOverlapsCounter
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
name|Assignment
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
name|Enrollment
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
name|Offering
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
name|OnlineSectioningServer
operator|.
name|Lock
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
name|solver
operator|.
name|ResectioningWeights
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
name|solver
operator|.
name|SectioningRequest
import|;
end_import

begin_class
specifier|public
class|class
name|CheckOfferingAction
implements|implements
name|OnlineSectioningAction
argument_list|<
name|Boolean
argument_list|>
block|{
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
name|iOfferingIds
decl_stmt|;
specifier|public
name|CheckOfferingAction
parameter_list|(
name|Long
modifier|...
name|offeringIds
parameter_list|)
block|{
name|iOfferingIds
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
name|offeringId
range|:
name|offeringIds
control|)
name|iOfferingIds
operator|.
name|add
argument_list|(
name|offeringId
argument_list|)
expr_stmt|;
block|}
specifier|public
name|CheckOfferingAction
parameter_list|(
name|Collection
argument_list|<
name|Long
argument_list|>
name|offeringIds
parameter_list|)
block|{
name|iOfferingIds
operator|=
name|offeringIds
expr_stmt|;
block|}
specifier|public
name|Collection
argument_list|<
name|Long
argument_list|>
name|getOfferingIds
parameter_list|()
block|{
return|return
name|iOfferingIds
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
if|if
condition|(
operator|!
name|server
operator|.
name|getAcademicSession
argument_list|()
operator|.
name|isSectioningEnabled
argument_list|()
condition|)
throw|throw
operator|new
name|SectioningException
argument_list|(
name|MSG
operator|.
name|exceptionNotSupportedFeature
argument_list|()
argument_list|)
throw|;
for|for
control|(
name|Long
name|offeringId
range|:
name|getOfferingIds
argument_list|()
control|)
block|{
comment|// offering is locked -> assuming that the offering will get checked when it is unlocked
if|if
condition|(
name|server
operator|.
name|isOfferingLocked
argument_list|(
name|offeringId
argument_list|)
condition|)
continue|continue;
comment|// lock and check the offering
name|Lock
name|lock
init|=
name|server
operator|.
name|lockOffering
argument_list|(
name|offeringId
argument_list|,
literal|null
argument_list|,
literal|false
argument_list|)
decl_stmt|;
try|try
block|{
name|Offering
name|offering
init|=
name|server
operator|.
name|getOffering
argument_list|(
name|offeringId
argument_list|)
decl_stmt|;
name|helper
operator|.
name|getAction
argument_list|()
operator|.
name|addOther
argument_list|(
name|OnlineSectioningLog
operator|.
name|Entity
operator|.
name|newBuilder
argument_list|()
operator|.
name|setUniqueId
argument_list|(
name|offeringId
argument_list|)
operator|.
name|setName
argument_list|(
name|offering
operator|.
name|getName
argument_list|()
argument_list|)
operator|.
name|setType
argument_list|(
name|OnlineSectioningLog
operator|.
name|Entity
operator|.
name|EntityType
operator|.
name|OFFERING
argument_list|)
argument_list|)
expr_stmt|;
name|checkOffering
argument_list|(
name|server
argument_list|,
name|helper
argument_list|,
name|offering
argument_list|)
expr_stmt|;
name|updateEnrollmentCounters
argument_list|(
name|server
argument_list|,
name|helper
argument_list|,
name|offering
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
return|return
literal|true
return|;
block|}
specifier|public
name|void
name|checkOffering
parameter_list|(
name|OnlineSectioningServer
name|server
parameter_list|,
name|OnlineSectioningHelper
name|helper
parameter_list|,
name|Offering
name|offering
parameter_list|)
block|{
if|if
condition|(
operator|!
name|server
operator|.
name|getAcademicSession
argument_list|()
operator|.
name|isSectioningEnabled
argument_list|()
operator|||
name|offering
operator|==
literal|null
condition|)
return|return;
name|Set
argument_list|<
name|SectioningRequest
argument_list|>
name|queue
init|=
operator|new
name|TreeSet
argument_list|<
name|SectioningRequest
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Course
name|course
range|:
name|offering
operator|.
name|getCourses
argument_list|()
control|)
block|{
for|for
control|(
name|CourseRequest
name|request
range|:
name|course
operator|.
name|getRequests
argument_list|()
control|)
block|{
if|if
condition|(
name|request
operator|.
name|getAssignment
argument_list|()
operator|==
literal|null
condition|)
block|{
name|OnlineSectioningLog
operator|.
name|Action
operator|.
name|Builder
name|action
init|=
name|helper
operator|.
name|addAction
argument_list|(
name|this
argument_list|,
name|server
operator|.
name|getAcademicSession
argument_list|()
argument_list|)
decl_stmt|;
name|action
operator|.
name|setStudent
argument_list|(
name|OnlineSectioningLog
operator|.
name|Entity
operator|.
name|newBuilder
argument_list|()
operator|.
name|setUniqueId
argument_list|(
name|request
operator|.
name|getStudent
argument_list|()
operator|.
name|getId
argument_list|()
argument_list|)
operator|.
name|setExternalId
argument_list|(
name|request
operator|.
name|getStudent
argument_list|()
operator|.
name|getExternalId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|action
operator|.
name|addOther
argument_list|(
name|OnlineSectioningLog
operator|.
name|Entity
operator|.
name|newBuilder
argument_list|()
operator|.
name|setUniqueId
argument_list|(
name|offering
operator|.
name|getId
argument_list|()
argument_list|)
operator|.
name|setName
argument_list|(
name|offering
operator|.
name|getName
argument_list|()
argument_list|)
operator|.
name|setType
argument_list|(
name|OnlineSectioningLog
operator|.
name|Entity
operator|.
name|EntityType
operator|.
name|OFFERING
argument_list|)
argument_list|)
expr_stmt|;
name|action
operator|.
name|addRequest
argument_list|(
name|OnlineSectioningHelper
operator|.
name|toProto
argument_list|(
name|request
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|request
operator|.
name|getStudent
argument_list|()
operator|.
name|canAssign
argument_list|(
name|request
argument_list|)
condition|)
name|queue
operator|.
name|add
argument_list|(
operator|new
name|SectioningRequest
argument_list|(
name|offering
argument_list|,
name|request
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|,
name|action
argument_list|,
literal|null
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
operator|!
name|check
argument_list|(
name|request
operator|.
name|getAssignment
argument_list|()
argument_list|)
condition|)
block|{
name|OnlineSectioningLog
operator|.
name|Action
operator|.
name|Builder
name|action
init|=
name|helper
operator|.
name|addAction
argument_list|(
name|this
argument_list|,
name|server
operator|.
name|getAcademicSession
argument_list|()
argument_list|)
decl_stmt|;
name|action
operator|.
name|setStudent
argument_list|(
name|OnlineSectioningLog
operator|.
name|Entity
operator|.
name|newBuilder
argument_list|()
operator|.
name|setUniqueId
argument_list|(
name|request
operator|.
name|getStudent
argument_list|()
operator|.
name|getId
argument_list|()
argument_list|)
operator|.
name|setExternalId
argument_list|(
name|request
operator|.
name|getStudent
argument_list|()
operator|.
name|getExternalId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|action
operator|.
name|addRequest
argument_list|(
name|OnlineSectioningHelper
operator|.
name|toProto
argument_list|(
name|request
argument_list|)
argument_list|)
expr_stmt|;
name|OnlineSectioningLog
operator|.
name|Enrollment
operator|.
name|Builder
name|enrollment
init|=
name|OnlineSectioningLog
operator|.
name|Enrollment
operator|.
name|newBuilder
argument_list|()
decl_stmt|;
name|enrollment
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
expr_stmt|;
for|for
control|(
name|Assignment
name|assignment
range|:
name|request
operator|.
name|getAssignment
argument_list|()
operator|.
name|getAssignments
argument_list|()
control|)
name|enrollment
operator|.
name|addSection
argument_list|(
name|OnlineSectioningHelper
operator|.
name|toProto
argument_list|(
name|assignment
argument_list|,
name|request
operator|.
name|getAssignment
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|action
operator|.
name|addEnrollment
argument_list|(
name|enrollment
argument_list|)
expr_stmt|;
name|request
operator|.
name|getSelectedChoices
argument_list|()
operator|.
name|clear
argument_list|()
expr_stmt|;
for|for
control|(
name|Section
name|s
range|:
name|request
operator|.
name|getAssignment
argument_list|()
operator|.
name|getSections
argument_list|()
control|)
name|request
operator|.
name|getSelectedChoices
argument_list|()
operator|.
name|add
argument_list|(
name|s
operator|.
name|getChoice
argument_list|()
argument_list|)
expr_stmt|;
name|queue
operator|.
name|add
argument_list|(
operator|new
name|SectioningRequest
argument_list|(
name|offering
argument_list|,
name|request
argument_list|,
literal|null
argument_list|,
name|request
operator|.
name|getAssignment
argument_list|()
argument_list|,
name|action
argument_list|,
literal|null
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
operator|!
name|queue
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
comment|// Load course request options
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|OnlineSectioningLog
operator|.
name|CourseRequestOption
argument_list|>
name|options
init|=
operator|new
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|OnlineSectioningLog
operator|.
name|CourseRequestOption
argument_list|>
argument_list|()
decl_stmt|;
name|helper
operator|.
name|beginTransaction
argument_list|()
expr_stmt|;
try|try
block|{
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
name|helper
operator|.
name|getHibSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select o.courseRequest.courseDemand.student.uniqueId, o.value from CourseRequestOption o "
operator|+
literal|"where o.courseRequest.courseOffering.instructionalOffering.uniqueId = :offeringId and "
operator|+
literal|"o.optionType = :type"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"offeringId"
argument_list|,
name|offering
operator|.
name|getId
argument_list|()
argument_list|)
operator|.
name|setInteger
argument_list|(
literal|"type"
argument_list|,
name|OnlineSectioningLog
operator|.
name|CourseRequestOption
operator|.
name|OptionType
operator|.
name|ORIGINAL_ENROLLMENT
operator|.
name|getNumber
argument_list|()
argument_list|)
operator|.
name|list
argument_list|()
control|)
block|{
name|Long
name|studentId
init|=
operator|(
name|Long
operator|)
name|o
index|[
literal|0
index|]
decl_stmt|;
try|try
block|{
name|options
operator|.
name|put
argument_list|(
name|studentId
argument_list|,
name|OnlineSectioningLog
operator|.
name|CourseRequestOption
operator|.
name|parseFrom
argument_list|(
operator|(
name|byte
index|[]
operator|)
name|o
index|[
literal|1
index|]
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|helper
operator|.
name|warn
argument_list|(
literal|"Unable to parse course request options for student "
operator|+
name|studentId
operator|+
literal|": "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
name|helper
operator|.
name|commitTransaction
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|helper
operator|.
name|warn
argument_list|(
literal|"Unable to parse course request options: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|DataProperties
name|properties
init|=
operator|new
name|DataProperties
argument_list|()
decl_stmt|;
name|ResectioningWeights
name|w
init|=
operator|new
name|ResectioningWeights
argument_list|(
name|properties
argument_list|)
decl_stmt|;
name|DistanceConflict
name|dc
init|=
operator|new
name|DistanceConflict
argument_list|(
literal|null
argument_list|,
name|properties
argument_list|)
decl_stmt|;
name|TimeOverlapsCounter
name|toc
init|=
operator|new
name|TimeOverlapsCounter
argument_list|(
literal|null
argument_list|,
name|properties
argument_list|)
decl_stmt|;
name|Date
name|ts
init|=
operator|new
name|Date
argument_list|()
decl_stmt|;
for|for
control|(
name|SectioningRequest
name|r
range|:
name|queue
control|)
block|{
comment|// helper.info("Resectioning " + r.getRequest() + " (was " + (r.getLastEnrollment() == null ? "not assigned" : r.getLastEnrollment().getAssignments()) + ")");
name|r
operator|.
name|setOriginalEnrollment
argument_list|(
name|options
operator|.
name|get
argument_list|(
name|r
operator|.
name|getRequest
argument_list|()
operator|.
name|getStudent
argument_list|()
operator|.
name|getId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|long
name|c0
init|=
name|OnlineSectioningHelper
operator|.
name|getCpuTime
argument_list|()
decl_stmt|;
name|Enrollment
name|enrollment
init|=
name|r
operator|.
name|resection
argument_list|(
name|w
argument_list|,
name|dc
argument_list|,
name|toc
argument_list|)
decl_stmt|;
name|Lock
name|wl
init|=
name|server
operator|.
name|writeLock
argument_list|()
decl_stmt|;
try|try
block|{
if|if
condition|(
name|enrollment
operator|!=
literal|null
condition|)
block|{
name|r
operator|.
name|getRequest
argument_list|()
operator|.
name|setInitialAssignment
argument_list|(
name|enrollment
argument_list|)
expr_stmt|;
name|r
operator|.
name|getRequest
argument_list|()
operator|.
name|assign
argument_list|(
literal|0
argument_list|,
name|enrollment
argument_list|)
expr_stmt|;
name|enrollment
operator|.
name|setTimeStamp
argument_list|(
name|ts
operator|.
name|getTime
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|r
operator|.
name|getRequest
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|r
operator|.
name|getRequest
argument_list|()
operator|.
name|setInitialAssignment
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|r
operator|.
name|getRequest
argument_list|()
operator|.
name|unassign
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
block|}
finally|finally
block|{
name|wl
operator|.
name|release
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|enrollment
operator|!=
literal|null
condition|)
block|{
name|OnlineSectioningLog
operator|.
name|Enrollment
operator|.
name|Builder
name|e
init|=
name|OnlineSectioningLog
operator|.
name|Enrollment
operator|.
name|newBuilder
argument_list|()
decl_stmt|;
name|e
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
expr_stmt|;
for|for
control|(
name|Assignment
name|assignment
range|:
name|enrollment
operator|.
name|getAssignments
argument_list|()
control|)
name|e
operator|.
name|addSection
argument_list|(
name|OnlineSectioningHelper
operator|.
name|toProto
argument_list|(
name|assignment
argument_list|,
name|enrollment
argument_list|)
argument_list|)
expr_stmt|;
name|r
operator|.
name|getAction
argument_list|()
operator|.
name|addEnrollment
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
comment|// helper.info("New: " + (r.getRequest().getAssignment() == null ? "not assigned" : r.getRequest().getAssignment().getAssignments()));
if|if
condition|(
name|r
operator|.
name|getLastEnrollment
argument_list|()
operator|==
literal|null
operator|&&
name|r
operator|.
name|getRequest
argument_list|()
operator|.
name|getAssignment
argument_list|()
operator|==
literal|null
condition|)
continue|continue;
if|if
condition|(
name|r
operator|.
name|getLastEnrollment
argument_list|()
operator|!=
literal|null
operator|&&
name|r
operator|.
name|getLastEnrollment
argument_list|()
operator|.
name|equals
argument_list|(
name|r
operator|.
name|getRequest
argument_list|()
operator|.
name|getAssignment
argument_list|()
argument_list|)
condition|)
continue|continue;
name|helper
operator|.
name|beginTransaction
argument_list|()
expr_stmt|;
try|try
block|{
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Student
name|student
init|=
name|StudentDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|r
operator|.
name|getRequest
argument_list|()
operator|.
name|getStudent
argument_list|()
operator|.
name|getId
argument_list|()
argument_list|,
name|helper
operator|.
name|getHibSession
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|Iterator
argument_list|<
name|StudentClassEnrollment
argument_list|>
name|i
init|=
name|student
operator|.
name|getClassEnrollments
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
name|StudentClassEnrollment
name|enrl
init|=
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
operator|(
name|enrl
operator|.
name|getCourseRequest
argument_list|()
operator|!=
literal|null
operator|&&
name|enrl
operator|.
name|getCourseRequest
argument_list|()
operator|.
name|getCourseDemand
argument_list|()
operator|.
name|getUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
name|r
operator|.
name|getRequest
argument_list|()
operator|.
name|getId
argument_list|()
argument_list|)
operator|)
operator|||
operator|(
name|r
operator|.
name|getLastEnrollment
argument_list|()
operator|!=
literal|null
operator|&&
name|enrl
operator|.
name|getCourseOffering
argument_list|()
operator|!=
literal|null
operator|&&
name|enrl
operator|.
name|getCourseOffering
argument_list|()
operator|.
name|getUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
name|r
operator|.
name|getLastEnrollment
argument_list|()
operator|.
name|getCourse
argument_list|()
operator|.
name|getId
argument_list|()
argument_list|)
operator|)
condition|)
block|{
name|helper
operator|.
name|info
argument_list|(
literal|"Deleting "
operator|+
name|enrl
operator|.
name|getClazz
argument_list|()
operator|.
name|getClassLabel
argument_list|()
argument_list|)
expr_stmt|;
name|enrl
operator|.
name|getClazz
argument_list|()
operator|.
name|getStudentEnrollments
argument_list|()
operator|.
name|remove
argument_list|(
name|enrl
argument_list|)
expr_stmt|;
if|if
condition|(
name|enrl
operator|.
name|getCourseRequest
argument_list|()
operator|!=
literal|null
condition|)
name|enrl
operator|.
name|getCourseRequest
argument_list|()
operator|.
name|getClassEnrollments
argument_list|()
operator|.
name|remove
argument_list|(
name|enrl
argument_list|)
expr_stmt|;
name|helper
operator|.
name|getHibSession
argument_list|()
operator|.
name|delete
argument_list|(
name|enrl
argument_list|)
expr_stmt|;
name|i
operator|.
name|remove
argument_list|()
expr_stmt|;
block|}
block|}
name|CourseDemand
name|cd
init|=
literal|null
decl_stmt|;
for|for
control|(
name|CourseDemand
name|x
range|:
name|student
operator|.
name|getCourseDemands
argument_list|()
control|)
if|if
condition|(
name|x
operator|.
name|getUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
name|r
operator|.
name|getRequest
argument_list|()
operator|.
name|getId
argument_list|()
argument_list|)
condition|)
block|{
name|cd
operator|=
name|x
expr_stmt|;
break|break;
block|}
if|if
condition|(
name|r
operator|.
name|getRequest
argument_list|()
operator|.
name|getAssignment
argument_list|()
operator|!=
literal|null
condition|)
block|{
comment|// save enrollment
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|CourseRequest
name|cr
init|=
literal|null
decl_stmt|;
name|CourseOffering
name|co
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|co
operator|==
literal|null
condition|)
name|co
operator|=
name|CourseOfferingDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|r
operator|.
name|getRequest
argument_list|()
operator|.
name|getAssignment
argument_list|()
operator|.
name|getCourse
argument_list|()
operator|.
name|getId
argument_list|()
argument_list|,
name|helper
operator|.
name|getHibSession
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|Section
name|section
range|:
name|r
operator|.
name|getRequest
argument_list|()
operator|.
name|getAssignment
argument_list|()
operator|.
name|getSections
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
name|section
operator|.
name|getId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|cd
operator|!=
literal|null
operator|&&
name|cr
operator|==
literal|null
condition|)
block|{
for|for
control|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|CourseRequest
name|x
range|:
name|cd
operator|.
name|getCourseRequests
argument_list|()
control|)
if|if
condition|(
name|x
operator|.
name|getCourseOffering
argument_list|()
operator|.
name|getUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
name|co
operator|.
name|getUniqueId
argument_list|()
argument_list|)
condition|)
block|{
name|cr
operator|=
name|x
expr_stmt|;
break|break;
block|}
block|}
if|if
condition|(
name|co
operator|==
literal|null
condition|)
name|co
operator|=
name|clazz
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getInstrOfferingConfig
argument_list|()
operator|.
name|getInstructionalOffering
argument_list|()
operator|.
name|getControllingCourseOffering
argument_list|()
expr_stmt|;
name|StudentClassEnrollment
name|enrl
init|=
operator|new
name|StudentClassEnrollment
argument_list|()
decl_stmt|;
name|enrl
operator|.
name|setClazz
argument_list|(
name|clazz
argument_list|)
expr_stmt|;
name|clazz
operator|.
name|getStudentEnrollments
argument_list|()
operator|.
name|add
argument_list|(
name|enrl
argument_list|)
expr_stmt|;
name|enrl
operator|.
name|setCourseOffering
argument_list|(
name|co
argument_list|)
expr_stmt|;
name|enrl
operator|.
name|setCourseRequest
argument_list|(
name|cr
argument_list|)
expr_stmt|;
name|enrl
operator|.
name|setTimestamp
argument_list|(
name|ts
argument_list|)
expr_stmt|;
name|enrl
operator|.
name|setStudent
argument_list|(
name|student
argument_list|)
expr_stmt|;
name|student
operator|.
name|getClassEnrollments
argument_list|()
operator|.
name|add
argument_list|(
name|enrl
argument_list|)
expr_stmt|;
name|helper
operator|.
name|info
argument_list|(
literal|"Adding "
operator|+
name|enrl
operator|.
name|getClazz
argument_list|()
operator|.
name|getClassLabel
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
if|else if
condition|(
operator|!
name|r
operator|.
name|getRequest
argument_list|()
operator|.
name|isAlternative
argument_list|()
condition|)
block|{
comment|// wait-list
if|if
condition|(
name|cd
operator|!=
literal|null
operator|&&
operator|!
name|cd
operator|.
name|isWaitlist
argument_list|()
condition|)
block|{
name|cd
operator|.
name|setWaitlist
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|helper
operator|.
name|getHibSession
argument_list|()
operator|.
name|saveOrUpdate
argument_list|(
name|cd
argument_list|)
expr_stmt|;
block|}
name|r
operator|.
name|getRequest
argument_list|()
operator|.
name|setWaitlist
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
name|helper
operator|.
name|getHibSession
argument_list|()
operator|.
name|save
argument_list|(
name|student
argument_list|)
expr_stmt|;
name|EnrollStudent
operator|.
name|updateSpace
argument_list|(
name|helper
argument_list|,
name|r
operator|.
name|getRequest
argument_list|()
operator|.
name|getAssignment
argument_list|()
argument_list|,
name|r
operator|.
name|getLastEnrollment
argument_list|()
argument_list|)
expr_stmt|;
name|server
operator|.
name|persistExpectedSpaces
argument_list|(
name|offering
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
name|server
operator|.
name|notifyStudentChanged
argument_list|(
name|r
operator|.
name|getRequest
argument_list|()
operator|.
name|getStudent
argument_list|()
operator|.
name|getId
argument_list|()
argument_list|,
name|r
operator|.
name|getRequest
argument_list|()
argument_list|,
name|r
operator|.
name|getLastEnrollment
argument_list|()
argument_list|)
expr_stmt|;
name|helper
operator|.
name|commitTransaction
argument_list|()
expr_stmt|;
name|r
operator|.
name|getAction
argument_list|()
operator|.
name|setResult
argument_list|(
name|enrollment
operator|==
literal|null
condition|?
name|OnlineSectioningLog
operator|.
name|Action
operator|.
name|ResultType
operator|.
name|NULL
else|:
name|OnlineSectioningLog
operator|.
name|Action
operator|.
name|ResultType
operator|.
name|SUCCESS
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|r
operator|.
name|getRequest
argument_list|()
operator|.
name|setInitialAssignment
argument_list|(
name|r
operator|.
name|getLastEnrollment
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|r
operator|.
name|getLastEnrollment
argument_list|()
operator|==
literal|null
condition|)
block|{
if|if
condition|(
name|r
operator|.
name|getRequest
argument_list|()
operator|.
name|getAssignment
argument_list|()
operator|!=
literal|null
condition|)
name|r
operator|.
name|getRequest
argument_list|()
operator|.
name|unassign
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|r
operator|.
name|getRequest
argument_list|()
operator|.
name|assign
argument_list|(
literal|0
argument_list|,
name|r
operator|.
name|getLastEnrollment
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|r
operator|.
name|getAction
argument_list|()
operator|.
name|setResult
argument_list|(
name|OnlineSectioningLog
operator|.
name|Action
operator|.
name|ResultType
operator|.
name|FAILURE
argument_list|)
expr_stmt|;
name|r
operator|.
name|getAction
argument_list|()
operator|.
name|addMessage
argument_list|(
name|OnlineSectioningLog
operator|.
name|Message
operator|.
name|newBuilder
argument_list|()
operator|.
name|setLevel
argument_list|(
name|OnlineSectioningLog
operator|.
name|Message
operator|.
name|Level
operator|.
name|FATAL
argument_list|)
operator|.
name|setText
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|helper
operator|.
name|rollbackTransaction
argument_list|()
expr_stmt|;
name|helper
operator|.
name|error
argument_list|(
literal|"Unable to resection student: "
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
name|r
operator|.
name|getAction
argument_list|()
operator|.
name|setCpuTime
argument_list|(
name|OnlineSectioningHelper
operator|.
name|getCpuTime
argument_list|()
operator|-
name|c0
argument_list|)
expr_stmt|;
name|r
operator|.
name|getAction
argument_list|()
operator|.
name|setEndTime
argument_list|(
name|System
operator|.
name|currentTimeMillis
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
annotation|@
name|Deprecated
specifier|public
specifier|static
name|void
name|updateEnrollmentCounters
parameter_list|(
name|OnlineSectioningServer
name|server
parameter_list|,
name|OnlineSectioningHelper
name|helper
parameter_list|,
name|Offering
name|offering
parameter_list|)
block|{
comment|/* 		if (offering == null) return; 		helper.beginTransaction(); 		try { 	     	helper.getHibSession().createQuery( 	     			"update CourseOffering c set c.enrollment = " + 	     			"(select count(distinct e.student) from StudentClassEnrollment e where e.courseOffering.uniqueId = c.uniqueId) " +  	                 "where c.instructionalOffering.uniqueId = :offeringId"). 	                 setLong("offeringId", offering.getId()).executeUpdate(); 	     	 	     	helper.getHibSession().createQuery( 	     			"update Class_ c set c.enrollment = " + 	     			"(select count(distinct e.student) from StudentClassEnrollment e where e.clazz.uniqueId = c.uniqueId) " +  	                 "where c.schedulingSubpart.uniqueId in " + 	                 "(select s.uniqueId from SchedulingSubpart s where s.instrOfferingConfig.instructionalOffering.uniqueId = :offeringId)"). 	                 setLong("offeringId", offering.getId()).executeUpdate(); 			helper.commitTransaction(); 		} catch (Exception e) { 			helper.rollbackTransaction(); 			if (e instanceof SectioningException) 				throw (SectioningException)e; 			throw new SectioningException(MSG.exceptionUnknown(e.getMessage()), e); 		} 		*/
block|}
specifier|public
name|boolean
name|check
parameter_list|(
name|Enrollment
name|e
parameter_list|)
block|{
if|if
condition|(
name|e
operator|.
name|getSections
argument_list|()
operator|.
name|size
argument_list|()
operator|!=
name|e
operator|.
name|getConfig
argument_list|()
operator|.
name|getSubparts
argument_list|()
operator|.
name|size
argument_list|()
condition|)
return|return
literal|false
return|;
for|for
control|(
name|Section
name|s1
range|:
name|e
operator|.
name|getSections
argument_list|()
control|)
for|for
control|(
name|Section
name|s2
range|:
name|e
operator|.
name|getSections
argument_list|()
control|)
block|{
if|if
condition|(
name|s1
operator|.
name|getId
argument_list|()
operator|<
name|s2
operator|.
name|getId
argument_list|()
operator|&&
name|s1
operator|.
name|isOverlapping
argument_list|(
name|s2
argument_list|)
condition|)
return|return
literal|false
return|;
if|if
condition|(
name|s1
operator|.
name|getId
argument_list|()
operator|!=
name|s2
operator|.
name|getId
argument_list|()
operator|&&
name|s1
operator|.
name|getSubpart
argument_list|()
operator|.
name|getId
argument_list|()
operator|==
name|s2
operator|.
name|getSubpart
argument_list|()
operator|.
name|getId
argument_list|()
condition|)
return|return
literal|false
return|;
block|}
for|for
control|(
name|Request
name|r
range|:
name|e
operator|.
name|getStudent
argument_list|()
operator|.
name|getRequests
argument_list|()
control|)
block|{
if|if
condition|(
name|r
operator|.
name|getId
argument_list|()
operator|!=
name|e
operator|.
name|getRequest
argument_list|()
operator|.
name|getId
argument_list|()
operator|&&
name|r
operator|.
name|getInitialAssignment
argument_list|()
operator|!=
literal|null
operator|&&
name|r
operator|.
name|getInitialAssignment
argument_list|()
operator|.
name|isOverlapping
argument_list|(
name|e
argument_list|)
condition|)
return|return
literal|false
return|;
block|}
return|return
literal|true
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|name
parameter_list|()
block|{
return|return
literal|"check-offering"
return|;
block|}
block|}
end_class

end_unit

