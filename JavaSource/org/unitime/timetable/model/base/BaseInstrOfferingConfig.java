begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.0 (University Course Timetabling& Student Sectioning Application)  * Copyright (C) 2007, UniTime.org, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program; if not, write to the Free Software Foundation, Inc.,  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
comment|/**  * This is an object that contains data related to the INSTR_OFFERING_CONFIG table.  * Do not modify this class because it will be overwritten if the configuration file  * related to this class is modified.  *  * @hibernate.class  *  table="INSTR_OFFERING_CONFIG"  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|BaseInstrOfferingConfig
implements|implements
name|Serializable
block|{
specifier|public
specifier|static
name|String
name|REF
init|=
literal|"InstrOfferingConfig"
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
name|PROP_UNLIMITED_ENROLLMENT
init|=
literal|"unlimitedEnrollment"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_NAME
init|=
literal|"name"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_UNIQUE_ID_ROLLED_FORWARD_FROM
init|=
literal|"UniqueIdRolledForwardFrom"
decl_stmt|;
comment|// constructors
specifier|public
name|BaseInstrOfferingConfig
parameter_list|()
block|{
name|initialize
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|BaseInstrOfferingConfig
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
name|BaseInstrOfferingConfig
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
name|InstructionalOffering
name|instructionalOffering
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|Integer
name|limit
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|Boolean
name|unlimitedEnrollment
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
name|setInstructionalOffering
argument_list|(
name|instructionalOffering
argument_list|)
expr_stmt|;
name|this
operator|.
name|setLimit
argument_list|(
name|limit
argument_list|)
expr_stmt|;
name|this
operator|.
name|setUnlimitedEnrollment
argument_list|(
name|unlimitedEnrollment
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
name|limit
decl_stmt|;
specifier|private
name|java
operator|.
name|lang
operator|.
name|Boolean
name|unlimitedEnrollment
decl_stmt|;
specifier|private
name|java
operator|.
name|lang
operator|.
name|String
name|name
decl_stmt|;
specifier|private
name|java
operator|.
name|lang
operator|.
name|Long
name|uniqueIdRolledForwardFrom
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
name|InstructionalOffering
name|instructionalOffering
decl_stmt|;
comment|// collections
specifier|private
name|java
operator|.
name|util
operator|.
name|Set
name|schedulingSubparts
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
comment|/** 	 * Return the value associated with the column: CONFIG_LIMIT 	 */
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
comment|/** 	 * Set the value related to the column: CONFIG_LIMIT 	 * @param limit the CONFIG_LIMIT value 	 */
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
comment|/** 	 * Return the value associated with the column: UNLIMITED_ENROLLMENT 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|Boolean
name|isUnlimitedEnrollment
parameter_list|()
block|{
return|return
name|unlimitedEnrollment
return|;
block|}
comment|/** 	 * Set the value related to the column: UNLIMITED_ENROLLMENT 	 * @param unlimitedEnrollment the UNLIMITED_ENROLLMENT value 	 */
specifier|public
name|void
name|setUnlimitedEnrollment
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Boolean
name|unlimitedEnrollment
parameter_list|)
block|{
name|this
operator|.
name|unlimitedEnrollment
operator|=
name|unlimitedEnrollment
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: NAME 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|String
name|getName
parameter_list|()
block|{
return|return
name|name
return|;
block|}
comment|/** 	 * Set the value related to the column: NAME 	 * @param name the NAME value 	 */
specifier|public
name|void
name|setName
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|String
name|name
parameter_list|)
block|{
name|this
operator|.
name|name
operator|=
name|name
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
comment|/** 	 * Return the value associated with the column: INSTR_OFFR_ID 	 */
specifier|public
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|InstructionalOffering
name|getInstructionalOffering
parameter_list|()
block|{
return|return
name|instructionalOffering
return|;
block|}
comment|/** 	 * Set the value related to the column: INSTR_OFFR_ID 	 * @param instructionalOffering the INSTR_OFFR_ID value 	 */
specifier|public
name|void
name|setInstructionalOffering
parameter_list|(
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
parameter_list|)
block|{
name|this
operator|.
name|instructionalOffering
operator|=
name|instructionalOffering
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: schedulingSubparts 	 */
specifier|public
name|java
operator|.
name|util
operator|.
name|Set
name|getSchedulingSubparts
parameter_list|()
block|{
return|return
name|schedulingSubparts
return|;
block|}
comment|/** 	 * Set the value related to the column: schedulingSubparts 	 * @param schedulingSubparts the schedulingSubparts value 	 */
specifier|public
name|void
name|setSchedulingSubparts
parameter_list|(
name|java
operator|.
name|util
operator|.
name|Set
name|schedulingSubparts
parameter_list|)
block|{
name|this
operator|.
name|schedulingSubparts
operator|=
name|schedulingSubparts
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
name|InstrOfferingConfig
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
name|InstrOfferingConfig
name|instrOfferingConfig
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
name|InstrOfferingConfig
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
name|instrOfferingConfig
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
name|instrOfferingConfig
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

