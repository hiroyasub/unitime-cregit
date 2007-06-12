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
comment|/**  * This is an object that contains data related to the SCHEDULING_SUBPART table.  * Do not modify this class because it will be overwritten if the configuration file  * related to this class is modified.  *  * @hibernate.class  *  table="SCHEDULING_SUBPART"  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|BaseSchedulingSubpart
extends|extends
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|PreferenceGroup
implements|implements
name|Serializable
block|{
specifier|public
specifier|static
name|String
name|REF
init|=
literal|"SchedulingSubpart"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_MINUTES_PER_WK
init|=
literal|"minutesPerWk"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_AUTO_SPREAD_IN_TIME
init|=
literal|"autoSpreadInTime"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_STUDENT_ALLOW_OVERLAP
init|=
literal|"studentAllowOverlap"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_SCHEDULING_SUBPART_SUFFIX_CACHE
init|=
literal|"schedulingSubpartSuffixCache"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_COURSE_NAME
init|=
literal|"courseName"
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
name|PROP_UNIQUE_ID_ROLLED_FORWARD_FROM
init|=
literal|"UniqueIdRolledForwardFrom"
decl_stmt|;
comment|// constructors
specifier|public
name|BaseSchedulingSubpart
parameter_list|()
block|{
name|initialize
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|BaseSchedulingSubpart
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
specifier|private
name|int
name|hashCode
init|=
name|Integer
operator|.
name|MIN_VALUE
decl_stmt|;
comment|// fields
specifier|private
name|java
operator|.
name|lang
operator|.
name|Integer
name|minutesPerWk
decl_stmt|;
specifier|private
name|java
operator|.
name|lang
operator|.
name|Boolean
name|autoSpreadInTime
decl_stmt|;
specifier|private
name|java
operator|.
name|lang
operator|.
name|Boolean
name|studentAllowOverlap
decl_stmt|;
specifier|private
name|java
operator|.
name|lang
operator|.
name|String
name|schedulingSubpartSuffixCache
decl_stmt|;
specifier|private
name|java
operator|.
name|lang
operator|.
name|String
name|courseName
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
name|ItypeDesc
name|itype
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
name|SchedulingSubpart
name|parentSubpart
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
name|InstrOfferingConfig
name|instrOfferingConfig
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
name|DatePattern
name|datePattern
decl_stmt|;
comment|// collections
specifier|private
name|java
operator|.
name|util
operator|.
name|Set
name|childSubparts
decl_stmt|;
specifier|private
name|java
operator|.
name|util
operator|.
name|Set
name|classes
decl_stmt|;
specifier|private
name|java
operator|.
name|util
operator|.
name|Set
name|creditConfigs
decl_stmt|;
comment|/** 	 * Return the value associated with the column: MIN_PER_WK 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|Integer
name|getMinutesPerWk
parameter_list|()
block|{
return|return
name|minutesPerWk
return|;
block|}
comment|/** 	 * Set the value related to the column: MIN_PER_WK 	 * @param minutesPerWk the MIN_PER_WK value 	 */
specifier|public
name|void
name|setMinutesPerWk
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Integer
name|minutesPerWk
parameter_list|)
block|{
name|this
operator|.
name|minutesPerWk
operator|=
name|minutesPerWk
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: AUTO_TIME_SPREAD 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|Boolean
name|isAutoSpreadInTime
parameter_list|()
block|{
return|return
name|autoSpreadInTime
return|;
block|}
comment|/** 	 * Set the value related to the column: AUTO_TIME_SPREAD 	 * @param autoSpreadInTime the AUTO_TIME_SPREAD value 	 */
specifier|public
name|void
name|setAutoSpreadInTime
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Boolean
name|autoSpreadInTime
parameter_list|)
block|{
name|this
operator|.
name|autoSpreadInTime
operator|=
name|autoSpreadInTime
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: STUDENT_ALLOW_OVERLAP 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|Boolean
name|isStudentAllowOverlap
parameter_list|()
block|{
return|return
name|studentAllowOverlap
return|;
block|}
comment|/** 	 * Set the value related to the column: STUDENT_ALLOW_OVERLAP 	 * @param studentAllowOverlap the STUDENT_ALLOW_OVERLAP value 	 */
specifier|public
name|void
name|setStudentAllowOverlap
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Boolean
name|studentAllowOverlap
parameter_list|)
block|{
name|this
operator|.
name|studentAllowOverlap
operator|=
name|studentAllowOverlap
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: SUBPART_SUFFIX 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|String
name|getSchedulingSubpartSuffixCache
parameter_list|()
block|{
return|return
name|schedulingSubpartSuffixCache
return|;
block|}
comment|/** 	 * Set the value related to the column: SUBPART_SUFFIX 	 * @param schedulingSubpartSuffixCache the SUBPART_SUFFIX value 	 */
specifier|public
name|void
name|setSchedulingSubpartSuffixCache
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|String
name|schedulingSubpartSuffixCache
parameter_list|)
block|{
name|this
operator|.
name|schedulingSubpartSuffixCache
operator|=
name|schedulingSubpartSuffixCache
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: courseName 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|String
name|getCourseName
parameter_list|()
block|{
return|return
name|courseName
return|;
block|}
comment|/** 	 * Set the value related to the column: courseName 	 * @param courseName the courseName value 	 */
specifier|public
name|void
name|setCourseName
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|String
name|courseName
parameter_list|)
block|{
name|this
operator|.
name|courseName
operator|=
name|courseName
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
comment|/** 	 * Return the value associated with the column: session 	 */
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
comment|/** 	 * Set the value related to the column: session 	 * @param session the session value 	 */
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
comment|/** 	 * Return the value associated with the column: itype 	 */
specifier|public
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|ItypeDesc
name|getItype
parameter_list|()
block|{
return|return
name|itype
return|;
block|}
comment|/** 	 * Set the value related to the column: itype 	 * @param itype the itype value 	 */
specifier|public
name|void
name|setItype
parameter_list|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|ItypeDesc
name|itype
parameter_list|)
block|{
name|this
operator|.
name|itype
operator|=
name|itype
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: PARENT 	 */
specifier|public
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|SchedulingSubpart
name|getParentSubpart
parameter_list|()
block|{
return|return
name|parentSubpart
return|;
block|}
comment|/** 	 * Set the value related to the column: PARENT 	 * @param parentSubpart the PARENT value 	 */
specifier|public
name|void
name|setParentSubpart
parameter_list|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|SchedulingSubpart
name|parentSubpart
parameter_list|)
block|{
name|this
operator|.
name|parentSubpart
operator|=
name|parentSubpart
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: CONFIG_ID 	 */
specifier|public
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|InstrOfferingConfig
name|getInstrOfferingConfig
parameter_list|()
block|{
return|return
name|instrOfferingConfig
return|;
block|}
comment|/** 	 * Set the value related to the column: CONFIG_ID 	 * @param instrOfferingConfig the CONFIG_ID value 	 */
specifier|public
name|void
name|setInstrOfferingConfig
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
name|this
operator|.
name|instrOfferingConfig
operator|=
name|instrOfferingConfig
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: DATE_PATTERN_ID 	 */
specifier|public
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|DatePattern
name|getDatePattern
parameter_list|()
block|{
return|return
name|datePattern
return|;
block|}
comment|/** 	 * Set the value related to the column: DATE_PATTERN_ID 	 * @param datePattern the DATE_PATTERN_ID value 	 */
specifier|public
name|void
name|setDatePattern
parameter_list|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|DatePattern
name|datePattern
parameter_list|)
block|{
name|this
operator|.
name|datePattern
operator|=
name|datePattern
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: childSubparts 	 */
specifier|public
name|java
operator|.
name|util
operator|.
name|Set
name|getChildSubparts
parameter_list|()
block|{
return|return
name|childSubparts
return|;
block|}
comment|/** 	 * Set the value related to the column: childSubparts 	 * @param childSubparts the childSubparts value 	 */
specifier|public
name|void
name|setChildSubparts
parameter_list|(
name|java
operator|.
name|util
operator|.
name|Set
name|childSubparts
parameter_list|)
block|{
name|this
operator|.
name|childSubparts
operator|=
name|childSubparts
expr_stmt|;
block|}
specifier|public
name|void
name|addTochildSubparts
parameter_list|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|SchedulingSubpart
name|schedulingSubpart
parameter_list|)
block|{
if|if
condition|(
literal|null
operator|==
name|getChildSubparts
argument_list|()
condition|)
name|setChildSubparts
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
name|getChildSubparts
argument_list|()
operator|.
name|add
argument_list|(
name|schedulingSubpart
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: classes 	 */
specifier|public
name|java
operator|.
name|util
operator|.
name|Set
name|getClasses
parameter_list|()
block|{
return|return
name|classes
return|;
block|}
comment|/** 	 * Set the value related to the column: classes 	 * @param classes the classes value 	 */
specifier|public
name|void
name|setClasses
parameter_list|(
name|java
operator|.
name|util
operator|.
name|Set
name|classes
parameter_list|)
block|{
name|this
operator|.
name|classes
operator|=
name|classes
expr_stmt|;
block|}
specifier|public
name|void
name|addToclasses
parameter_list|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Class_
name|class_
parameter_list|)
block|{
if|if
condition|(
literal|null
operator|==
name|getClasses
argument_list|()
condition|)
name|setClasses
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
name|getClasses
argument_list|()
operator|.
name|add
argument_list|(
name|class_
argument_list|)
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
name|SchedulingSubpart
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
name|SchedulingSubpart
name|schedulingSubpart
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
name|SchedulingSubpart
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
name|schedulingSubpart
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
name|schedulingSubpart
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

