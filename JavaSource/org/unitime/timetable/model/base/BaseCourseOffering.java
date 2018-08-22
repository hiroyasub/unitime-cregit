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
operator|.
name|base
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
name|CourseCreditUnitConfig
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
name|CourseType
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
name|DemandOfferingType
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
name|OfferingConsentType
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
name|OverrideType
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
comment|/**  * Do not change this class. It has been automatically generated using ant create-model.  * @see org.unitime.commons.ant.CreateBaseModelFromXml  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|BaseCourseOffering
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
name|iUniqueId
decl_stmt|;
specifier|private
name|Boolean
name|iIsControl
decl_stmt|;
specifier|private
name|String
name|iPermId
decl_stmt|;
specifier|private
name|Integer
name|iProjectedDemand
decl_stmt|;
specifier|private
name|Integer
name|iNbrExpectedStudents
decl_stmt|;
specifier|private
name|Integer
name|iDemand
decl_stmt|;
specifier|private
name|Integer
name|iEnrollment
decl_stmt|;
specifier|private
name|Integer
name|iReservation
decl_stmt|;
specifier|private
name|String
name|iSubjectAreaAbbv
decl_stmt|;
specifier|private
name|String
name|iCourseNbr
decl_stmt|;
specifier|private
name|String
name|iTitle
decl_stmt|;
specifier|private
name|String
name|iScheduleBookNote
decl_stmt|;
specifier|private
name|String
name|iExternalUniqueId
decl_stmt|;
specifier|private
name|Long
name|iUniqueIdRolledForwardFrom
decl_stmt|;
specifier|private
name|Integer
name|iSnapshotProjectedDemand
decl_stmt|;
specifier|private
name|Date
name|iSnapshotProjectedDemandDate
decl_stmt|;
specifier|private
name|SubjectArea
name|iSubjectArea
decl_stmt|;
specifier|private
name|InstructionalOffering
name|iInstructionalOffering
decl_stmt|;
specifier|private
name|CourseOffering
name|iDemandOffering
decl_stmt|;
specifier|private
name|DemandOfferingType
name|iDemandOfferingType
decl_stmt|;
specifier|private
name|CourseType
name|iCourseType
decl_stmt|;
specifier|private
name|OfferingConsentType
name|iConsentType
decl_stmt|;
specifier|private
name|CourseOffering
name|iAlternativeOffering
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|CourseCreditUnitConfig
argument_list|>
name|iCreditConfigs
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|OverrideType
argument_list|>
name|iDisabledOverrides
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_UNIQUEID
init|=
literal|"uniqueId"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_IS_CONTROL
init|=
literal|"isControl"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_PERM_ID
init|=
literal|"permId"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_PROJ_DEMAND
init|=
literal|"projectedDemand"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_NBR_EXPECTED_STDENTS
init|=
literal|"nbrExpectedStudents"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_LASTLIKE_DEMAND
init|=
literal|"demand"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_RESERVATION
init|=
literal|"reservation"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_COURSE_NBR
init|=
literal|"courseNbr"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_TITLE
init|=
literal|"title"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_SCHEDULE_BOOK_NOTE
init|=
literal|"scheduleBookNote"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_EXTERNAL_UID
init|=
literal|"externalUniqueId"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_UID_ROLLED_FWD_FROM
init|=
literal|"uniqueIdRolledForwardFrom"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_SNAPSHOT_PROJ_DEMAND
init|=
literal|"snapshotProjectedDemand"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_SNAPSHOT_PRJ_DMD_DATE
init|=
literal|"snapshotProjectedDemandDate"
decl_stmt|;
specifier|public
name|BaseCourseOffering
parameter_list|()
block|{
name|initialize
argument_list|()
expr_stmt|;
block|}
specifier|public
name|BaseCourseOffering
parameter_list|(
name|Long
name|uniqueId
parameter_list|)
block|{
name|setUniqueId
argument_list|(
name|uniqueId
argument_list|)
expr_stmt|;
name|initialize
argument_list|()
expr_stmt|;
block|}
specifier|protected
name|void
name|initialize
parameter_list|()
block|{
block|}
specifier|public
name|Long
name|getUniqueId
parameter_list|()
block|{
return|return
name|iUniqueId
return|;
block|}
specifier|public
name|void
name|setUniqueId
parameter_list|(
name|Long
name|uniqueId
parameter_list|)
block|{
name|iUniqueId
operator|=
name|uniqueId
expr_stmt|;
block|}
specifier|public
name|Boolean
name|isIsControl
parameter_list|()
block|{
return|return
name|iIsControl
return|;
block|}
specifier|public
name|Boolean
name|getIsControl
parameter_list|()
block|{
return|return
name|iIsControl
return|;
block|}
specifier|public
name|void
name|setIsControl
parameter_list|(
name|Boolean
name|isControl
parameter_list|)
block|{
name|iIsControl
operator|=
name|isControl
expr_stmt|;
block|}
specifier|public
name|String
name|getPermId
parameter_list|()
block|{
return|return
name|iPermId
return|;
block|}
specifier|public
name|void
name|setPermId
parameter_list|(
name|String
name|permId
parameter_list|)
block|{
name|iPermId
operator|=
name|permId
expr_stmt|;
block|}
specifier|public
name|Integer
name|getProjectedDemand
parameter_list|()
block|{
return|return
name|iProjectedDemand
return|;
block|}
specifier|public
name|void
name|setProjectedDemand
parameter_list|(
name|Integer
name|projectedDemand
parameter_list|)
block|{
name|iProjectedDemand
operator|=
name|projectedDemand
expr_stmt|;
block|}
specifier|public
name|Integer
name|getNbrExpectedStudents
parameter_list|()
block|{
return|return
name|iNbrExpectedStudents
return|;
block|}
specifier|public
name|void
name|setNbrExpectedStudents
parameter_list|(
name|Integer
name|nbrExpectedStudents
parameter_list|)
block|{
name|iNbrExpectedStudents
operator|=
name|nbrExpectedStudents
expr_stmt|;
block|}
specifier|public
name|Integer
name|getDemand
parameter_list|()
block|{
return|return
name|iDemand
return|;
block|}
specifier|public
name|void
name|setDemand
parameter_list|(
name|Integer
name|demand
parameter_list|)
block|{
name|iDemand
operator|=
name|demand
expr_stmt|;
block|}
specifier|public
name|Integer
name|getEnrollment
parameter_list|()
block|{
return|return
name|iEnrollment
return|;
block|}
specifier|public
name|void
name|setEnrollment
parameter_list|(
name|Integer
name|enrollment
parameter_list|)
block|{
name|iEnrollment
operator|=
name|enrollment
expr_stmt|;
block|}
specifier|public
name|Integer
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
name|Integer
name|reservation
parameter_list|)
block|{
name|iReservation
operator|=
name|reservation
expr_stmt|;
block|}
specifier|public
name|String
name|getSubjectAreaAbbv
parameter_list|()
block|{
return|return
name|iSubjectAreaAbbv
return|;
block|}
specifier|public
name|void
name|setSubjectAreaAbbv
parameter_list|(
name|String
name|subjectAreaAbbv
parameter_list|)
block|{
name|iSubjectAreaAbbv
operator|=
name|subjectAreaAbbv
expr_stmt|;
block|}
specifier|public
name|String
name|getCourseNbr
parameter_list|()
block|{
return|return
name|iCourseNbr
return|;
block|}
specifier|public
name|void
name|setCourseNbr
parameter_list|(
name|String
name|courseNbr
parameter_list|)
block|{
name|iCourseNbr
operator|=
name|courseNbr
expr_stmt|;
block|}
specifier|public
name|String
name|getTitle
parameter_list|()
block|{
return|return
name|iTitle
return|;
block|}
specifier|public
name|void
name|setTitle
parameter_list|(
name|String
name|title
parameter_list|)
block|{
name|iTitle
operator|=
name|title
expr_stmt|;
block|}
specifier|public
name|String
name|getScheduleBookNote
parameter_list|()
block|{
return|return
name|iScheduleBookNote
return|;
block|}
specifier|public
name|void
name|setScheduleBookNote
parameter_list|(
name|String
name|scheduleBookNote
parameter_list|)
block|{
name|iScheduleBookNote
operator|=
name|scheduleBookNote
expr_stmt|;
block|}
specifier|public
name|String
name|getExternalUniqueId
parameter_list|()
block|{
return|return
name|iExternalUniqueId
return|;
block|}
specifier|public
name|void
name|setExternalUniqueId
parameter_list|(
name|String
name|externalUniqueId
parameter_list|)
block|{
name|iExternalUniqueId
operator|=
name|externalUniqueId
expr_stmt|;
block|}
specifier|public
name|Long
name|getUniqueIdRolledForwardFrom
parameter_list|()
block|{
return|return
name|iUniqueIdRolledForwardFrom
return|;
block|}
specifier|public
name|void
name|setUniqueIdRolledForwardFrom
parameter_list|(
name|Long
name|uniqueIdRolledForwardFrom
parameter_list|)
block|{
name|iUniqueIdRolledForwardFrom
operator|=
name|uniqueIdRolledForwardFrom
expr_stmt|;
block|}
specifier|public
name|Integer
name|getSnapshotProjectedDemand
parameter_list|()
block|{
return|return
name|iSnapshotProjectedDemand
return|;
block|}
specifier|public
name|void
name|setSnapshotProjectedDemand
parameter_list|(
name|Integer
name|snapshotProjectedDemand
parameter_list|)
block|{
name|iSnapshotProjectedDemand
operator|=
name|snapshotProjectedDemand
expr_stmt|;
block|}
specifier|public
name|Date
name|getSnapshotProjectedDemandDate
parameter_list|()
block|{
return|return
name|iSnapshotProjectedDemandDate
return|;
block|}
specifier|public
name|void
name|setSnapshotProjectedDemandDate
parameter_list|(
name|Date
name|snapshotProjectedDemandDate
parameter_list|)
block|{
name|iSnapshotProjectedDemandDate
operator|=
name|snapshotProjectedDemandDate
expr_stmt|;
block|}
specifier|public
name|SubjectArea
name|getSubjectArea
parameter_list|()
block|{
return|return
name|iSubjectArea
return|;
block|}
specifier|public
name|void
name|setSubjectArea
parameter_list|(
name|SubjectArea
name|subjectArea
parameter_list|)
block|{
name|iSubjectArea
operator|=
name|subjectArea
expr_stmt|;
block|}
specifier|public
name|InstructionalOffering
name|getInstructionalOffering
parameter_list|()
block|{
return|return
name|iInstructionalOffering
return|;
block|}
specifier|public
name|void
name|setInstructionalOffering
parameter_list|(
name|InstructionalOffering
name|instructionalOffering
parameter_list|)
block|{
name|iInstructionalOffering
operator|=
name|instructionalOffering
expr_stmt|;
block|}
specifier|public
name|CourseOffering
name|getDemandOffering
parameter_list|()
block|{
return|return
name|iDemandOffering
return|;
block|}
specifier|public
name|void
name|setDemandOffering
parameter_list|(
name|CourseOffering
name|demandOffering
parameter_list|)
block|{
name|iDemandOffering
operator|=
name|demandOffering
expr_stmt|;
block|}
specifier|public
name|DemandOfferingType
name|getDemandOfferingType
parameter_list|()
block|{
return|return
name|iDemandOfferingType
return|;
block|}
specifier|public
name|void
name|setDemandOfferingType
parameter_list|(
name|DemandOfferingType
name|demandOfferingType
parameter_list|)
block|{
name|iDemandOfferingType
operator|=
name|demandOfferingType
expr_stmt|;
block|}
specifier|public
name|CourseType
name|getCourseType
parameter_list|()
block|{
return|return
name|iCourseType
return|;
block|}
specifier|public
name|void
name|setCourseType
parameter_list|(
name|CourseType
name|courseType
parameter_list|)
block|{
name|iCourseType
operator|=
name|courseType
expr_stmt|;
block|}
specifier|public
name|OfferingConsentType
name|getConsentType
parameter_list|()
block|{
return|return
name|iConsentType
return|;
block|}
specifier|public
name|void
name|setConsentType
parameter_list|(
name|OfferingConsentType
name|consentType
parameter_list|)
block|{
name|iConsentType
operator|=
name|consentType
expr_stmt|;
block|}
specifier|public
name|CourseOffering
name|getAlternativeOffering
parameter_list|()
block|{
return|return
name|iAlternativeOffering
return|;
block|}
specifier|public
name|void
name|setAlternativeOffering
parameter_list|(
name|CourseOffering
name|alternativeOffering
parameter_list|)
block|{
name|iAlternativeOffering
operator|=
name|alternativeOffering
expr_stmt|;
block|}
specifier|public
name|Set
argument_list|<
name|CourseCreditUnitConfig
argument_list|>
name|getCreditConfigs
parameter_list|()
block|{
return|return
name|iCreditConfigs
return|;
block|}
specifier|public
name|void
name|setCreditConfigs
parameter_list|(
name|Set
argument_list|<
name|CourseCreditUnitConfig
argument_list|>
name|creditConfigs
parameter_list|)
block|{
name|iCreditConfigs
operator|=
name|creditConfigs
expr_stmt|;
block|}
specifier|public
name|void
name|addTocreditConfigs
parameter_list|(
name|CourseCreditUnitConfig
name|courseCreditUnitConfig
parameter_list|)
block|{
if|if
condition|(
name|iCreditConfigs
operator|==
literal|null
condition|)
name|iCreditConfigs
operator|=
operator|new
name|HashSet
argument_list|<
name|CourseCreditUnitConfig
argument_list|>
argument_list|()
expr_stmt|;
name|iCreditConfigs
operator|.
name|add
argument_list|(
name|courseCreditUnitConfig
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Set
argument_list|<
name|OverrideType
argument_list|>
name|getDisabledOverrides
parameter_list|()
block|{
return|return
name|iDisabledOverrides
return|;
block|}
specifier|public
name|void
name|setDisabledOverrides
parameter_list|(
name|Set
argument_list|<
name|OverrideType
argument_list|>
name|disabledOverrides
parameter_list|)
block|{
name|iDisabledOverrides
operator|=
name|disabledOverrides
expr_stmt|;
block|}
specifier|public
name|void
name|addTodisabledOverrides
parameter_list|(
name|OverrideType
name|overrideType
parameter_list|)
block|{
if|if
condition|(
name|iDisabledOverrides
operator|==
literal|null
condition|)
name|iDisabledOverrides
operator|=
operator|new
name|HashSet
argument_list|<
name|OverrideType
argument_list|>
argument_list|()
expr_stmt|;
name|iDisabledOverrides
operator|.
name|add
argument_list|(
name|overrideType
argument_list|)
expr_stmt|;
block|}
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
operator|||
operator|!
operator|(
name|o
operator|instanceof
name|CourseOffering
operator|)
condition|)
return|return
literal|false
return|;
if|if
condition|(
name|getUniqueId
argument_list|()
operator|==
literal|null
operator|||
operator|(
operator|(
name|CourseOffering
operator|)
name|o
operator|)
operator|.
name|getUniqueId
argument_list|()
operator|==
literal|null
condition|)
return|return
literal|false
return|;
return|return
name|getUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
operator|(
operator|(
name|CourseOffering
operator|)
name|o
operator|)
operator|.
name|getUniqueId
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
if|if
condition|(
name|getUniqueId
argument_list|()
operator|==
literal|null
condition|)
return|return
name|super
operator|.
name|hashCode
argument_list|()
return|;
return|return
name|getUniqueId
argument_list|()
operator|.
name|hashCode
argument_list|()
return|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
literal|"CourseOffering["
operator|+
name|getUniqueId
argument_list|()
operator|+
literal|"]"
return|;
block|}
specifier|public
name|String
name|toDebugString
parameter_list|()
block|{
return|return
literal|"CourseOffering["
operator|+
literal|"\n	AlternativeOffering: "
operator|+
name|getAlternativeOffering
argument_list|()
operator|+
literal|"\n	ConsentType: "
operator|+
name|getConsentType
argument_list|()
operator|+
literal|"\n	CourseNbr: "
operator|+
name|getCourseNbr
argument_list|()
operator|+
literal|"\n	CourseType: "
operator|+
name|getCourseType
argument_list|()
operator|+
literal|"\n	Demand: "
operator|+
name|getDemand
argument_list|()
operator|+
literal|"\n	DemandOffering: "
operator|+
name|getDemandOffering
argument_list|()
operator|+
literal|"\n	DemandOfferingType: "
operator|+
name|getDemandOfferingType
argument_list|()
operator|+
literal|"\n	ExternalUniqueId: "
operator|+
name|getExternalUniqueId
argument_list|()
operator|+
literal|"\n	InstructionalOffering: "
operator|+
name|getInstructionalOffering
argument_list|()
operator|+
literal|"\n	IsControl: "
operator|+
name|getIsControl
argument_list|()
operator|+
literal|"\n	NbrExpectedStudents: "
operator|+
name|getNbrExpectedStudents
argument_list|()
operator|+
literal|"\n	PermId: "
operator|+
name|getPermId
argument_list|()
operator|+
literal|"\n	ProjectedDemand: "
operator|+
name|getProjectedDemand
argument_list|()
operator|+
literal|"\n	Reservation: "
operator|+
name|getReservation
argument_list|()
operator|+
literal|"\n	ScheduleBookNote: "
operator|+
name|getScheduleBookNote
argument_list|()
operator|+
literal|"\n	SnapshotProjectedDemand: "
operator|+
name|getSnapshotProjectedDemand
argument_list|()
operator|+
literal|"\n	SnapshotProjectedDemandDate: "
operator|+
name|getSnapshotProjectedDemandDate
argument_list|()
operator|+
literal|"\n	SubjectArea: "
operator|+
name|getSubjectArea
argument_list|()
operator|+
literal|"\n	Title: "
operator|+
name|getTitle
argument_list|()
operator|+
literal|"\n	UniqueId: "
operator|+
name|getUniqueId
argument_list|()
operator|+
literal|"\n	UniqueIdRolledForwardFrom: "
operator|+
name|getUniqueIdRolledForwardFrom
argument_list|()
operator|+
literal|"]"
return|;
block|}
block|}
end_class

end_unit

