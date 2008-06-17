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
comment|/**  * This is an object that contains data related to the MEETING table.  * Do not modify this class because it will be overwritten if the configuration file  * related to this class is modified.  *  * @hibernate.class  *  table="MEETING"  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|BaseMeeting
implements|implements
name|Serializable
block|{
specifier|public
specifier|static
name|String
name|REF
init|=
literal|"Meeting"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_MEETING_DATE
init|=
literal|"meetingDate"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_START_PERIOD
init|=
literal|"startPeriod"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_START_OFFSET
init|=
literal|"startOffset"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_STOP_PERIOD
init|=
literal|"stopPeriod"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_STOP_OFFSET
init|=
literal|"stopOffset"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_LOCATION_PERMANENT_ID
init|=
literal|"locationPermanentId"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_CLASS_CAN_OVERRIDE
init|=
literal|"classCanOverride"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_APPROVED_DATE
init|=
literal|"approvedDate"
decl_stmt|;
comment|// constructors
specifier|public
name|BaseMeeting
parameter_list|()
block|{
name|initialize
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|BaseMeeting
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
name|BaseMeeting
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
name|Event
name|event
parameter_list|,
name|java
operator|.
name|util
operator|.
name|Date
name|meetingDate
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|Integer
name|startPeriod
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|Integer
name|stopPeriod
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|Boolean
name|classCanOverride
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
name|setEvent
argument_list|(
name|event
argument_list|)
expr_stmt|;
name|this
operator|.
name|setMeetingDate
argument_list|(
name|meetingDate
argument_list|)
expr_stmt|;
name|this
operator|.
name|setStartPeriod
argument_list|(
name|startPeriod
argument_list|)
expr_stmt|;
name|this
operator|.
name|setStopPeriod
argument_list|(
name|stopPeriod
argument_list|)
expr_stmt|;
name|this
operator|.
name|setClassCanOverride
argument_list|(
name|classCanOverride
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
name|util
operator|.
name|Date
name|meetingDate
decl_stmt|;
specifier|private
name|java
operator|.
name|lang
operator|.
name|Integer
name|startPeriod
decl_stmt|;
specifier|private
name|java
operator|.
name|lang
operator|.
name|Integer
name|startOffset
decl_stmt|;
specifier|private
name|java
operator|.
name|lang
operator|.
name|Integer
name|stopPeriod
decl_stmt|;
specifier|private
name|java
operator|.
name|lang
operator|.
name|Integer
name|stopOffset
decl_stmt|;
specifier|private
name|java
operator|.
name|lang
operator|.
name|Long
name|locationPermanentId
decl_stmt|;
specifier|private
name|java
operator|.
name|lang
operator|.
name|Boolean
name|classCanOverride
decl_stmt|;
specifier|private
name|java
operator|.
name|util
operator|.
name|Date
name|approvedDate
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
name|Event
name|event
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
comment|/** 	 * Return the value associated with the column: MEETING_DATE 	 */
specifier|public
name|java
operator|.
name|util
operator|.
name|Date
name|getMeetingDate
parameter_list|()
block|{
return|return
name|meetingDate
return|;
block|}
comment|/** 	 * Set the value related to the column: MEETING_DATE 	 * @param meetingDate the MEETING_DATE value 	 */
specifier|public
name|void
name|setMeetingDate
parameter_list|(
name|java
operator|.
name|util
operator|.
name|Date
name|meetingDate
parameter_list|)
block|{
name|this
operator|.
name|meetingDate
operator|=
name|meetingDate
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: START_PERIOD 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|Integer
name|getStartPeriod
parameter_list|()
block|{
return|return
name|startPeriod
return|;
block|}
comment|/** 	 * Set the value related to the column: START_PERIOD 	 * @param startPeriod the START_PERIOD value 	 */
specifier|public
name|void
name|setStartPeriod
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Integer
name|startPeriod
parameter_list|)
block|{
name|this
operator|.
name|startPeriod
operator|=
name|startPeriod
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: START_OFFSET 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|Integer
name|getStartOffset
parameter_list|()
block|{
return|return
name|startOffset
return|;
block|}
comment|/** 	 * Set the value related to the column: START_OFFSET 	 * @param startOffset the START_OFFSET value 	 */
specifier|public
name|void
name|setStartOffset
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Integer
name|startOffset
parameter_list|)
block|{
name|this
operator|.
name|startOffset
operator|=
name|startOffset
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: STOP_PERIOD 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|Integer
name|getStopPeriod
parameter_list|()
block|{
return|return
name|stopPeriod
return|;
block|}
comment|/** 	 * Set the value related to the column: STOP_PERIOD 	 * @param stopPeriod the STOP_PERIOD value 	 */
specifier|public
name|void
name|setStopPeriod
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Integer
name|stopPeriod
parameter_list|)
block|{
name|this
operator|.
name|stopPeriod
operator|=
name|stopPeriod
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: STOP_OFFSET 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|Integer
name|getStopOffset
parameter_list|()
block|{
return|return
name|stopOffset
return|;
block|}
comment|/** 	 * Set the value related to the column: STOP_OFFSET 	 * @param stopOffset the STOP_OFFSET value 	 */
specifier|public
name|void
name|setStopOffset
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Integer
name|stopOffset
parameter_list|)
block|{
name|this
operator|.
name|stopOffset
operator|=
name|stopOffset
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: location_perm_id 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|Long
name|getLocationPermanentId
parameter_list|()
block|{
return|return
name|locationPermanentId
return|;
block|}
comment|/** 	 * Set the value related to the column: location_perm_id 	 * @param locationPermanentId the location_perm_id value 	 */
specifier|public
name|void
name|setLocationPermanentId
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Long
name|locationPermanentId
parameter_list|)
block|{
name|this
operator|.
name|locationPermanentId
operator|=
name|locationPermanentId
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: CLASS_CAN_OVERRIDE 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|Boolean
name|isClassCanOverride
parameter_list|()
block|{
return|return
name|classCanOverride
return|;
block|}
comment|/** 	 * Set the value related to the column: CLASS_CAN_OVERRIDE 	 * @param classCanOverride the CLASS_CAN_OVERRIDE value 	 */
specifier|public
name|void
name|setClassCanOverride
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Boolean
name|classCanOverride
parameter_list|)
block|{
name|this
operator|.
name|classCanOverride
operator|=
name|classCanOverride
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: APPROVED_DATE 	 */
specifier|public
name|java
operator|.
name|util
operator|.
name|Date
name|getApprovedDate
parameter_list|()
block|{
return|return
name|approvedDate
return|;
block|}
comment|/** 	 * Set the value related to the column: APPROVED_DATE 	 * @param approvedDate the APPROVED_DATE value 	 */
specifier|public
name|void
name|setApprovedDate
parameter_list|(
name|java
operator|.
name|util
operator|.
name|Date
name|approvedDate
parameter_list|)
block|{
name|this
operator|.
name|approvedDate
operator|=
name|approvedDate
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: EVENT_ID 	 */
specifier|public
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Event
name|getEvent
parameter_list|()
block|{
return|return
name|event
return|;
block|}
comment|/** 	 * Set the value related to the column: EVENT_ID 	 * @param event the EVENT_ID value 	 */
specifier|public
name|void
name|setEvent
parameter_list|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Event
name|event
parameter_list|)
block|{
name|this
operator|.
name|event
operator|=
name|event
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
name|Meeting
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
name|Meeting
name|meeting
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
name|Meeting
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
name|meeting
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
name|meeting
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

