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

begin_comment
comment|/**  * This is an object that contains data related to the INSTRUCTIONAL_OFFERING table.  * Do not modify this class because it will be overwritten if the configuration file  * related to this class is modified.  *  * @hibernate.class  *  table="INSTRUCTIONAL_OFFERING"  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|BaseInstructionalOffering
implements|implements
name|Serializable
block|{
specifier|public
specifier|static
name|String
name|REF
init|=
literal|"InstructionalOffering"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_INSTR_OFFERING_PERM_ID
init|=
literal|"instrOfferingPermId"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_NOT_OFFERED
init|=
literal|"notOffered"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_DEMAND
init|=
literal|"demand"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_PROJECTED_DEMAND
init|=
literal|"projectedDemand"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_CTRL_COURSE_ID
init|=
literal|"ctrlCourseId"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_LIMIT
init|=
literal|"limit"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_DESIGNATOR_REQUIRED
init|=
literal|"designatorRequired"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_UNIQUE_ID_ROLLED_FORWARD_FROM
init|=
literal|"uniqueIdRolledForwardFrom"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_EXTERNAL_UNIQUE_ID
init|=
literal|"externalUniqueId"
decl_stmt|;
comment|// constructors
specifier|public
name|BaseInstructionalOffering
parameter_list|()
block|{
name|initialize
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|BaseInstructionalOffering
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Long
name|uniqueId
parameter_list|)
block|{
name|this
operator|.
name|setUniqueId
argument_list|(
name|uniqueId
argument_list|)
expr_stmt|;
name|initialize
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for required fields 	 */
specifier|public
name|BaseInstructionalOffering
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
name|Session
name|session
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|Integer
name|instrOfferingPermId
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|Boolean
name|notOffered
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|Boolean
name|designatorRequired
parameter_list|)
block|{
name|this
operator|.
name|setUniqueId
argument_list|(
name|uniqueId
argument_list|)
expr_stmt|;
name|this
operator|.
name|setSession
argument_list|(
name|session
argument_list|)
expr_stmt|;
name|this
operator|.
name|setInstrOfferingPermId
argument_list|(
name|instrOfferingPermId
argument_list|)
expr_stmt|;
name|this
operator|.
name|setNotOffered
argument_list|(
name|notOffered
argument_list|)
expr_stmt|;
name|this
operator|.
name|setDesignatorRequired
argument_list|(
name|designatorRequired
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
specifier|private
name|int
name|hashCode
init|=
name|Integer
operator|.
name|MIN_VALUE
decl_stmt|;
comment|// primary key
specifier|private
name|java
operator|.
name|lang
operator|.
name|Long
name|uniqueId
decl_stmt|;
comment|// fields
specifier|private
name|java
operator|.
name|lang
operator|.
name|Integer
name|instrOfferingPermId
decl_stmt|;
specifier|private
name|java
operator|.
name|lang
operator|.
name|Boolean
name|notOffered
decl_stmt|;
specifier|private
name|java
operator|.
name|lang
operator|.
name|Integer
name|demand
decl_stmt|;
specifier|private
name|java
operator|.
name|lang
operator|.
name|Integer
name|projectedDemand
decl_stmt|;
specifier|private
name|java
operator|.
name|lang
operator|.
name|Integer
name|ctrlCourseId
decl_stmt|;
specifier|private
name|java
operator|.
name|lang
operator|.
name|Integer
name|limit
decl_stmt|;
specifier|private
name|java
operator|.
name|lang
operator|.
name|Boolean
name|designatorRequired
decl_stmt|;
specifier|private
name|java
operator|.
name|lang
operator|.
name|Long
name|uniqueIdRolledForwardFrom
decl_stmt|;
specifier|private
name|java
operator|.
name|lang
operator|.
name|String
name|externalUniqueId
decl_stmt|;
comment|// many to one
specifier|private
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Session
name|session
decl_stmt|;
specifier|private
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|OfferingConsentType
name|consentType
decl_stmt|;
comment|// collections
specifier|private
name|java
operator|.
name|util
operator|.
name|Set
name|courseOfferings
decl_stmt|;
specifier|private
name|java
operator|.
name|util
operator|.
name|Set
name|instrOfferingConfigs
decl_stmt|;
specifier|private
name|java
operator|.
name|util
operator|.
name|Set
name|courseReservations
decl_stmt|;
specifier|private
name|java
operator|.
name|util
operator|.
name|Set
name|individualReservations
decl_stmt|;
specifier|private
name|java
operator|.
name|util
operator|.
name|Set
name|studentGroupReservations
decl_stmt|;
specifier|private
name|java
operator|.
name|util
operator|.
name|Set
name|acadAreaReservations
decl_stmt|;
specifier|private
name|java
operator|.
name|util
operator|.
name|Set
name|posReservations
decl_stmt|;
specifier|private
name|java
operator|.
name|util
operator|.
name|Set
name|creditConfigs
decl_stmt|;
comment|/** 	 * Return the unique identifier of this class      * @hibernate.id      *  generator-class="org.unitime.commons.hibernate.id.UniqueIdGenerator"      *  column="UNIQUEID"      */
specifier|public
name|java
operator|.
name|lang
operator|.
name|Long
name|getUniqueId
parameter_list|()
block|{
return|return
name|uniqueId
return|;
block|}
comment|/** 	 * Set the unique identifier of this class 	 * @param uniqueId the new ID 	 */
specifier|public
name|void
name|setUniqueId
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Long
name|uniqueId
parameter_list|)
block|{
name|this
operator|.
name|uniqueId
operator|=
name|uniqueId
expr_stmt|;
name|this
operator|.
name|hashCode
operator|=
name|Integer
operator|.
name|MIN_VALUE
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: INSTR_OFFERING_PERM_ID 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|Integer
name|getInstrOfferingPermId
parameter_list|()
block|{
return|return
name|instrOfferingPermId
return|;
block|}
comment|/** 	 * Set the value related to the column: INSTR_OFFERING_PERM_ID 	 * @param instrOfferingPermId the INSTR_OFFERING_PERM_ID value 	 */
specifier|public
name|void
name|setInstrOfferingPermId
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Integer
name|instrOfferingPermId
parameter_list|)
block|{
name|this
operator|.
name|instrOfferingPermId
operator|=
name|instrOfferingPermId
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: NOT_OFFERED 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|Boolean
name|isNotOffered
parameter_list|()
block|{
return|return
name|notOffered
return|;
block|}
comment|/** 	 * Set the value related to the column: NOT_OFFERED 	 * @param notOffered the NOT_OFFERED value 	 */
specifier|public
name|void
name|setNotOffered
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Boolean
name|notOffered
parameter_list|)
block|{
name|this
operator|.
name|notOffered
operator|=
name|notOffered
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: demand 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|Integer
name|getDemand
parameter_list|()
block|{
return|return
name|demand
return|;
block|}
comment|/** 	 * Set the value related to the column: demand 	 * @param demand the demand value 	 */
specifier|public
name|void
name|setDemand
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Integer
name|demand
parameter_list|)
block|{
name|this
operator|.
name|demand
operator|=
name|demand
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: projectedDemand 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|Integer
name|getProjectedDemand
parameter_list|()
block|{
return|return
name|projectedDemand
return|;
block|}
comment|/** 	 * Set the value related to the column: projectedDemand 	 * @param projectedDemand the projectedDemand value 	 */
specifier|public
name|void
name|setProjectedDemand
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Integer
name|projectedDemand
parameter_list|)
block|{
name|this
operator|.
name|projectedDemand
operator|=
name|projectedDemand
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: ctrlCourseId 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|Integer
name|getCtrlCourseId
parameter_list|()
block|{
return|return
name|ctrlCourseId
return|;
block|}
comment|/** 	 * Set the value related to the column: ctrlCourseId 	 * @param ctrlCourseId the ctrlCourseId value 	 */
specifier|public
name|void
name|setCtrlCourseId
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Integer
name|ctrlCourseId
parameter_list|)
block|{
name|this
operator|.
name|ctrlCourseId
operator|=
name|ctrlCourseId
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: limit 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|Integer
name|getLimit
parameter_list|()
block|{
return|return
name|limit
return|;
block|}
comment|/** 	 * Set the value related to the column: limit 	 * @param limit the limit value 	 */
specifier|public
name|void
name|setLimit
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Integer
name|limit
parameter_list|)
block|{
name|this
operator|.
name|limit
operator|=
name|limit
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: DESIGNATOR_REQUIRED 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|Boolean
name|isDesignatorRequired
parameter_list|()
block|{
return|return
name|designatorRequired
return|;
block|}
comment|/** 	 * Set the value related to the column: DESIGNATOR_REQUIRED 	 * @param designatorRequired the DESIGNATOR_REQUIRED value 	 */
specifier|public
name|void
name|setDesignatorRequired
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Boolean
name|designatorRequired
parameter_list|)
block|{
name|this
operator|.
name|designatorRequired
operator|=
name|designatorRequired
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: UID_ROLLED_FWD_FROM 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|Long
name|getUniqueIdRolledForwardFrom
parameter_list|()
block|{
return|return
name|uniqueIdRolledForwardFrom
return|;
block|}
comment|/** 	 * Set the value related to the column: UID_ROLLED_FWD_FROM 	 * @param uniqueIdRolledForwardFrom the UID_ROLLED_FWD_FROM value 	 */
specifier|public
name|void
name|setUniqueIdRolledForwardFrom
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Long
name|uniqueIdRolledForwardFrom
parameter_list|)
block|{
name|this
operator|.
name|uniqueIdRolledForwardFrom
operator|=
name|uniqueIdRolledForwardFrom
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: EXTERNAL_UID 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|String
name|getExternalUniqueId
parameter_list|()
block|{
return|return
name|externalUniqueId
return|;
block|}
comment|/** 	 * Set the value related to the column: EXTERNAL_UID 	 * @param externalUniqueId the EXTERNAL_UID value 	 */
specifier|public
name|void
name|setExternalUniqueId
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|String
name|externalUniqueId
parameter_list|)
block|{
name|this
operator|.
name|externalUniqueId
operator|=
name|externalUniqueId
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: SESSION_ID 	 */
specifier|public
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Session
name|getSession
parameter_list|()
block|{
return|return
name|session
return|;
block|}
comment|/** 	 * Set the value related to the column: SESSION_ID 	 * @param session the SESSION_ID value 	 */
specifier|public
name|void
name|setSession
parameter_list|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Session
name|session
parameter_list|)
block|{
name|this
operator|.
name|session
operator|=
name|session
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: CONSENT_TYPE 	 */
specifier|public
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|OfferingConsentType
name|getConsentType
parameter_list|()
block|{
return|return
name|consentType
return|;
block|}
comment|/** 	 * Set the value related to the column: CONSENT_TYPE 	 * @param consentType the CONSENT_TYPE value 	 */
specifier|public
name|void
name|setConsentType
parameter_list|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|OfferingConsentType
name|consentType
parameter_list|)
block|{
name|this
operator|.
name|consentType
operator|=
name|consentType
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: courseOfferings 	 */
specifier|public
name|java
operator|.
name|util
operator|.
name|Set
name|getCourseOfferings
parameter_list|()
block|{
return|return
name|courseOfferings
return|;
block|}
comment|/** 	 * Set the value related to the column: courseOfferings 	 * @param courseOfferings the courseOfferings value 	 */
specifier|public
name|void
name|setCourseOfferings
parameter_list|(
name|java
operator|.
name|util
operator|.
name|Set
name|courseOfferings
parameter_list|)
block|{
name|this
operator|.
name|courseOfferings
operator|=
name|courseOfferings
expr_stmt|;
block|}
specifier|public
name|void
name|addTocourseOfferings
parameter_list|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|CourseOffering
name|courseOffering
parameter_list|)
block|{
if|if
condition|(
literal|null
operator|==
name|getCourseOfferings
argument_list|()
condition|)
name|setCourseOfferings
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
name|getCourseOfferings
argument_list|()
operator|.
name|add
argument_list|(
name|courseOffering
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: instrOfferingConfigs 	 */
specifier|public
name|java
operator|.
name|util
operator|.
name|Set
name|getInstrOfferingConfigs
parameter_list|()
block|{
return|return
name|instrOfferingConfigs
return|;
block|}
comment|/** 	 * Set the value related to the column: instrOfferingConfigs 	 * @param instrOfferingConfigs the instrOfferingConfigs value 	 */
specifier|public
name|void
name|setInstrOfferingConfigs
parameter_list|(
name|java
operator|.
name|util
operator|.
name|Set
name|instrOfferingConfigs
parameter_list|)
block|{
name|this
operator|.
name|instrOfferingConfigs
operator|=
name|instrOfferingConfigs
expr_stmt|;
block|}
specifier|public
name|void
name|addToinstrOfferingConfigs
parameter_list|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|InstrOfferingConfig
name|instrOfferingConfig
parameter_list|)
block|{
if|if
condition|(
literal|null
operator|==
name|getInstrOfferingConfigs
argument_list|()
condition|)
name|setInstrOfferingConfigs
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
name|getInstrOfferingConfigs
argument_list|()
operator|.
name|add
argument_list|(
name|instrOfferingConfig
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: courseReservations 	 */
specifier|public
name|java
operator|.
name|util
operator|.
name|Set
name|getCourseReservations
parameter_list|()
block|{
return|return
name|courseReservations
return|;
block|}
comment|/** 	 * Set the value related to the column: courseReservations 	 * @param courseReservations the courseReservations value 	 */
specifier|public
name|void
name|setCourseReservations
parameter_list|(
name|java
operator|.
name|util
operator|.
name|Set
name|courseReservations
parameter_list|)
block|{
name|this
operator|.
name|courseReservations
operator|=
name|courseReservations
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: individualReservations 	 */
specifier|public
name|java
operator|.
name|util
operator|.
name|Set
name|getIndividualReservations
parameter_list|()
block|{
return|return
name|individualReservations
return|;
block|}
comment|/** 	 * Set the value related to the column: individualReservations 	 * @param individualReservations the individualReservations value 	 */
specifier|public
name|void
name|setIndividualReservations
parameter_list|(
name|java
operator|.
name|util
operator|.
name|Set
name|individualReservations
parameter_list|)
block|{
name|this
operator|.
name|individualReservations
operator|=
name|individualReservations
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: studentGroupReservations 	 */
specifier|public
name|java
operator|.
name|util
operator|.
name|Set
name|getStudentGroupReservations
parameter_list|()
block|{
return|return
name|studentGroupReservations
return|;
block|}
comment|/** 	 * Set the value related to the column: studentGroupReservations 	 * @param studentGroupReservations the studentGroupReservations value 	 */
specifier|public
name|void
name|setStudentGroupReservations
parameter_list|(
name|java
operator|.
name|util
operator|.
name|Set
name|studentGroupReservations
parameter_list|)
block|{
name|this
operator|.
name|studentGroupReservations
operator|=
name|studentGroupReservations
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: acadAreaReservations 	 */
specifier|public
name|java
operator|.
name|util
operator|.
name|Set
name|getAcadAreaReservations
parameter_list|()
block|{
return|return
name|acadAreaReservations
return|;
block|}
comment|/** 	 * Set the value related to the column: acadAreaReservations 	 * @param acadAreaReservations the acadAreaReservations value 	 */
specifier|public
name|void
name|setAcadAreaReservations
parameter_list|(
name|java
operator|.
name|util
operator|.
name|Set
name|acadAreaReservations
parameter_list|)
block|{
name|this
operator|.
name|acadAreaReservations
operator|=
name|acadAreaReservations
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: posReservations 	 */
specifier|public
name|java
operator|.
name|util
operator|.
name|Set
name|getPosReservations
parameter_list|()
block|{
return|return
name|posReservations
return|;
block|}
comment|/** 	 * Set the value related to the column: posReservations 	 * @param posReservations the posReservations value 	 */
specifier|public
name|void
name|setPosReservations
parameter_list|(
name|java
operator|.
name|util
operator|.
name|Set
name|posReservations
parameter_list|)
block|{
name|this
operator|.
name|posReservations
operator|=
name|posReservations
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: creditConfigs 	 */
specifier|public
name|java
operator|.
name|util
operator|.
name|Set
name|getCreditConfigs
parameter_list|()
block|{
return|return
name|creditConfigs
return|;
block|}
comment|/** 	 * Set the value related to the column: creditConfigs 	 * @param creditConfigs the creditConfigs value 	 */
specifier|public
name|void
name|setCreditConfigs
parameter_list|(
name|java
operator|.
name|util
operator|.
name|Set
name|creditConfigs
parameter_list|)
block|{
name|this
operator|.
name|creditConfigs
operator|=
name|creditConfigs
expr_stmt|;
block|}
specifier|public
name|void
name|addTocreditConfigs
parameter_list|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|CourseCreditUnitConfig
name|courseCreditUnitConfig
parameter_list|)
block|{
if|if
condition|(
literal|null
operator|==
name|getCreditConfigs
argument_list|()
condition|)
name|setCreditConfigs
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
name|getCreditConfigs
argument_list|()
operator|.
name|add
argument_list|(
name|courseCreditUnitConfig
argument_list|)
expr_stmt|;
block|}
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|obj
parameter_list|)
block|{
if|if
condition|(
literal|null
operator|==
name|obj
condition|)
return|return
literal|false
return|;
if|if
condition|(
operator|!
operator|(
name|obj
operator|instanceof
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|InstructionalOffering
operator|)
condition|)
return|return
literal|false
return|;
else|else
block|{
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|InstructionalOffering
name|instructionalOffering
init|=
operator|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|InstructionalOffering
operator|)
name|obj
decl_stmt|;
if|if
condition|(
literal|null
operator|==
name|this
operator|.
name|getUniqueId
argument_list|()
operator|||
literal|null
operator|==
name|instructionalOffering
operator|.
name|getUniqueId
argument_list|()
condition|)
return|return
literal|false
return|;
else|else
return|return
operator|(
name|this
operator|.
name|getUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
name|instructionalOffering
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|)
return|;
block|}
block|}
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
if|if
condition|(
name|Integer
operator|.
name|MIN_VALUE
operator|==
name|this
operator|.
name|hashCode
condition|)
block|{
if|if
condition|(
literal|null
operator|==
name|this
operator|.
name|getUniqueId
argument_list|()
condition|)
return|return
name|super
operator|.
name|hashCode
argument_list|()
return|;
else|else
block|{
name|String
name|hashStr
init|=
name|this
operator|.
name|getClass
argument_list|()
operator|.
name|getName
argument_list|()
operator|+
literal|":"
operator|+
name|this
operator|.
name|getUniqueId
argument_list|()
operator|.
name|hashCode
argument_list|()
decl_stmt|;
name|this
operator|.
name|hashCode
operator|=
name|hashStr
operator|.
name|hashCode
argument_list|()
expr_stmt|;
block|}
block|}
return|return
name|this
operator|.
name|hashCode
return|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|super
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
end_class

end_unit

