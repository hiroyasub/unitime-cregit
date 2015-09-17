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
package|;
end_package

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
name|HashMap
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
name|BaseInstrOfferingConfig
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
name|comparators
operator|.
name|InstrOfferingConfigComparator
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
name|comparators
operator|.
name|NavigationComparator
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
name|InstrOfferingConfigDAO
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
name|security
operator|.
name|SessionContext
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
name|util
operator|.
name|duration
operator|.
name|DurationModel
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
name|util
operator|.
name|duration
operator|.
name|MinutesPerWeek
import|;
end_import

begin_comment
comment|/**  * @author Stephanie Schluttenhofer, Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|InstrOfferingConfig
extends|extends
name|BaseInstrOfferingConfig
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
name|InstrOfferingConfig
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|InstrOfferingConfig
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
comment|/*[CONSTRUCTOR MARKER END]*/
specifier|public
name|Department
name|getDepartment
parameter_list|()
block|{
return|return
operator|(
name|this
operator|.
name|getInstructionalOffering
argument_list|()
operator|.
name|getDepartment
argument_list|()
operator|)
return|;
block|}
specifier|public
name|Session
name|getSession
parameter_list|()
block|{
return|return
operator|(
name|this
operator|.
name|getInstructionalOffering
argument_list|()
operator|.
name|getSession
argument_list|()
operator|)
return|;
block|}
specifier|public
name|Long
name|getSessionId
parameter_list|()
block|{
return|return
operator|(
name|this
operator|.
name|getInstructionalOffering
argument_list|()
operator|.
name|getSessionId
argument_list|()
operator|)
return|;
block|}
specifier|public
name|String
name|getCourseName
parameter_list|()
block|{
return|return
operator|(
name|this
operator|.
name|getControllingCourseOffering
argument_list|()
operator|.
name|getCourseName
argument_list|()
operator|)
return|;
block|}
specifier|public
name|String
name|getCourseNameWithTitle
parameter_list|()
block|{
return|return
operator|(
name|this
operator|.
name|getControllingCourseOffering
argument_list|()
operator|.
name|getCourseNameWithTitle
argument_list|()
operator|)
return|;
block|}
specifier|public
name|CourseOffering
name|getControllingCourseOffering
parameter_list|()
block|{
return|return
operator|(
name|this
operator|.
name|getInstructionalOffering
argument_list|()
operator|.
name|getControllingCourseOffering
argument_list|()
operator|)
return|;
block|}
comment|/**      * Check if a config has at least one class      * @return true if at least one class exists, false otherwise       */
specifier|public
name|boolean
name|hasClasses
parameter_list|()
block|{
name|Set
name|subparts
init|=
name|this
operator|.
name|getSchedulingSubparts
argument_list|()
decl_stmt|;
if|if
condition|(
name|subparts
operator|!=
literal|null
operator|&&
operator|!
name|subparts
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
for|for
control|(
name|Iterator
name|i
init|=
name|subparts
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
name|SchedulingSubpart
name|ss
init|=
operator|(
name|SchedulingSubpart
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|ss
operator|.
name|getClasses
argument_list|()
operator|!=
literal|null
operator|&&
operator|!
name|ss
operator|.
name|getClasses
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
literal|true
return|;
block|}
block|}
block|}
return|return
literal|false
return|;
block|}
specifier|public
name|int
name|getFirstSectionNumber
parameter_list|(
name|ItypeDesc
name|itype
parameter_list|)
block|{
if|if
condition|(
name|getInstructionalOffering
argument_list|()
operator|.
name|getInstrOfferingConfigs
argument_list|()
operator|.
name|size
argument_list|()
operator|<=
literal|1
condition|)
return|return
literal|1
return|;
name|Comparator
name|cmp
init|=
operator|new
name|InstrOfferingConfigComparator
argument_list|(
literal|null
argument_list|)
decl_stmt|;
name|int
name|ret
init|=
literal|1
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|getInstructionalOffering
argument_list|()
operator|.
name|getInstrOfferingConfigs
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
name|InstrOfferingConfig
name|cfg
init|=
operator|(
name|InstrOfferingConfig
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|int
name|size
init|=
literal|0
decl_stmt|;
if|if
condition|(
name|cmp
operator|.
name|compare
argument_list|(
name|cfg
argument_list|,
name|this
argument_list|)
operator|>=
literal|0
condition|)
continue|continue;
for|for
control|(
name|Iterator
name|j
init|=
name|cfg
operator|.
name|getSchedulingSubparts
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|j
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|SchedulingSubpart
name|subpart
init|=
operator|(
name|SchedulingSubpart
operator|)
name|j
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|subpart
operator|.
name|getItype
argument_list|()
operator|.
name|equals
argument_list|(
name|itype
argument_list|)
condition|)
continue|continue;
name|size
operator|=
name|Math
operator|.
name|max
argument_list|(
name|size
argument_list|,
name|subpart
operator|.
name|getClasses
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|ret
operator|+=
name|size
expr_stmt|;
block|}
return|return
name|ret
return|;
block|}
comment|/**      * Add subpart to config      * @param schedulingSubpart      */
specifier|public
name|void
name|addToschedulingSubparts
parameter_list|(
name|SchedulingSubpart
name|schedulingSubpart
parameter_list|)
block|{
if|if
condition|(
literal|null
operator|==
name|getSchedulingSubparts
argument_list|()
condition|)
name|setSchedulingSubparts
argument_list|(
operator|new
name|HashSet
argument_list|()
argument_list|)
expr_stmt|;
name|getSchedulingSubparts
argument_list|()
operator|.
name|add
argument_list|(
name|schedulingSubpart
argument_list|)
expr_stmt|;
block|}
specifier|public
name|String
name|getName
parameter_list|()
block|{
name|String
name|name
init|=
name|super
operator|.
name|getName
argument_list|()
decl_stmt|;
if|if
condition|(
name|name
operator|!=
literal|null
operator|&&
name|name
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
return|return
name|name
return|;
if|if
condition|(
name|getInstructionalOffering
argument_list|()
operator|==
literal|null
condition|)
return|return
literal|null
return|;
if|if
condition|(
name|getUniqueId
argument_list|()
operator|==
literal|null
condition|)
return|return
name|String
operator|.
name|valueOf
argument_list|(
name|getInstructionalOffering
argument_list|()
operator|.
name|getInstrOfferingConfigs
argument_list|()
operator|.
name|size
argument_list|()
operator|+
literal|1
argument_list|)
return|;
name|int
name|idx
init|=
literal|0
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|getInstructionalOffering
argument_list|()
operator|.
name|getInstrOfferingConfigs
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
name|InstrOfferingConfig
name|c
init|=
operator|(
name|InstrOfferingConfig
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|c
operator|.
name|getUniqueId
argument_list|()
operator|.
name|compareTo
argument_list|(
name|getUniqueId
argument_list|()
argument_list|)
operator|<
literal|0
condition|)
name|idx
operator|++
expr_stmt|;
block|}
return|return
name|String
operator|.
name|valueOf
argument_list|(
name|idx
operator|+
literal|1
argument_list|)
return|;
block|}
comment|/**      * Gets first unused number as the config name for the specified instr offering      * @param io Instructional Offering      * @return null if io is null. Name starting with number 1 as the first       */
specifier|public
specifier|static
name|String
name|getGeneratedName
parameter_list|(
name|InstructionalOffering
name|io
parameter_list|)
block|{
if|if
condition|(
name|io
operator|==
literal|null
condition|)
return|return
literal|null
return|;
name|int
name|idx
init|=
literal|1
decl_stmt|;
name|HashMap
name|idxes
init|=
operator|new
name|HashMap
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|io
operator|.
name|getInstrOfferingConfigs
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
name|InstrOfferingConfig
name|c
init|=
operator|(
name|InstrOfferingConfig
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|idxes
operator|.
name|put
argument_list|(
name|c
operator|.
name|getName
argument_list|()
argument_list|,
name|c
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
for|for
control|(
init|;
condition|;
control|)
block|{
if|if
condition|(
name|idxes
operator|.
name|get
argument_list|(
literal|""
operator|+
name|idx
argument_list|)
operator|==
literal|null
condition|)
break|break;
operator|++
name|idx
expr_stmt|;
block|}
return|return
literal|""
operator|+
name|idx
return|;
block|}
specifier|public
name|InstrOfferingConfig
name|getNextInstrOfferingConfig
parameter_list|(
name|SessionContext
name|context
parameter_list|)
block|{
return|return
name|getNextInstrOfferingConfig
argument_list|(
name|context
argument_list|,
operator|new
name|NavigationComparator
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|InstrOfferingConfig
name|getPreviousInstrOfferingConfig
parameter_list|(
name|SessionContext
name|context
parameter_list|)
block|{
return|return
name|getPreviousInstrOfferingConfig
argument_list|(
name|context
argument_list|,
operator|new
name|NavigationComparator
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|InstrOfferingConfig
name|getNextInstrOfferingConfig
parameter_list|(
name|SessionContext
name|context
parameter_list|,
name|Comparator
name|cmp
parameter_list|)
block|{
name|InstrOfferingConfig
name|next
init|=
literal|null
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|getInstructionalOffering
argument_list|()
operator|.
name|getInstrOfferingConfigs
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
name|InstrOfferingConfig
name|c
init|=
operator|(
name|InstrOfferingConfig
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|cmp
operator|.
name|compare
argument_list|(
name|this
argument_list|,
name|c
argument_list|)
operator|>=
literal|0
condition|)
continue|continue;
if|if
condition|(
name|next
operator|==
literal|null
operator|||
name|cmp
operator|.
name|compare
argument_list|(
name|next
argument_list|,
name|c
argument_list|)
operator|>
literal|0
condition|)
name|next
operator|=
name|c
expr_stmt|;
block|}
if|if
condition|(
name|next
operator|!=
literal|null
condition|)
return|return
name|next
return|;
name|InstructionalOffering
name|nextIO
init|=
name|getInstructionalOffering
argument_list|()
operator|.
name|getNextInstructionalOffering
argument_list|(
name|context
argument_list|,
name|cmp
argument_list|)
decl_stmt|;
if|if
condition|(
name|nextIO
operator|==
literal|null
condition|)
return|return
literal|null
return|;
for|for
control|(
name|Iterator
name|i
init|=
name|nextIO
operator|.
name|getInstrOfferingConfigs
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
name|InstrOfferingConfig
name|c
init|=
operator|(
name|InstrOfferingConfig
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|next
operator|==
literal|null
operator|||
name|cmp
operator|.
name|compare
argument_list|(
name|next
argument_list|,
name|c
argument_list|)
operator|>
literal|0
condition|)
name|next
operator|=
name|c
expr_stmt|;
block|}
return|return
name|next
return|;
block|}
specifier|public
name|InstrOfferingConfig
name|getPreviousInstrOfferingConfig
parameter_list|(
name|SessionContext
name|context
parameter_list|,
name|Comparator
name|cmp
parameter_list|)
block|{
name|InstrOfferingConfig
name|previous
init|=
literal|null
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|getInstructionalOffering
argument_list|()
operator|.
name|getInstrOfferingConfigs
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
name|InstrOfferingConfig
name|c
init|=
operator|(
name|InstrOfferingConfig
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|cmp
operator|.
name|compare
argument_list|(
name|this
argument_list|,
name|c
argument_list|)
operator|<=
literal|0
condition|)
continue|continue;
if|if
condition|(
name|previous
operator|==
literal|null
operator|||
name|cmp
operator|.
name|compare
argument_list|(
name|previous
argument_list|,
name|c
argument_list|)
operator|<
literal|0
condition|)
name|previous
operator|=
name|c
expr_stmt|;
block|}
if|if
condition|(
name|previous
operator|!=
literal|null
condition|)
return|return
name|previous
return|;
name|InstructionalOffering
name|previousIO
init|=
name|getInstructionalOffering
argument_list|()
operator|.
name|getPreviousInstructionalOffering
argument_list|(
name|context
argument_list|,
name|cmp
argument_list|)
decl_stmt|;
if|if
condition|(
name|previousIO
operator|==
literal|null
condition|)
return|return
literal|null
return|;
for|for
control|(
name|Iterator
name|i
init|=
name|previousIO
operator|.
name|getInstrOfferingConfigs
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
name|InstrOfferingConfig
name|c
init|=
operator|(
name|InstrOfferingConfig
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|previous
operator|==
literal|null
operator|||
name|cmp
operator|.
name|compare
argument_list|(
name|previous
argument_list|,
name|c
argument_list|)
operator|<
literal|0
condition|)
name|previous
operator|=
name|c
expr_stmt|;
block|}
return|return
name|previous
return|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|getCourseName
argument_list|()
operator|+
literal|" ["
operator|+
name|getName
argument_list|()
operator|+
literal|"]"
return|;
block|}
specifier|public
name|boolean
name|hasGroupedClasses
parameter_list|()
block|{
if|if
condition|(
name|this
operator|.
name|getSchedulingSubparts
argument_list|()
operator|!=
literal|null
operator|&&
name|this
operator|.
name|getSchedulingSubparts
argument_list|()
operator|.
name|size
argument_list|()
operator|>
literal|0
condition|)
block|{
name|SchedulingSubpart
name|ss
init|=
literal|null
decl_stmt|;
for|for
control|(
name|Iterator
name|it
init|=
name|this
operator|.
name|getSchedulingSubparts
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|it
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|ss
operator|=
operator|(
name|SchedulingSubpart
operator|)
name|it
operator|.
name|next
argument_list|()
expr_stmt|;
if|if
condition|(
name|ss
operator|.
name|getParentSubpart
argument_list|()
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
operator|!
name|ss
operator|.
name|getParentSubpart
argument_list|()
operator|.
name|getItype
argument_list|()
operator|.
name|getItype
argument_list|()
operator|.
name|equals
argument_list|(
name|ss
operator|.
name|getItype
argument_list|()
operator|.
name|getItype
argument_list|()
argument_list|)
condition|)
block|{
return|return
operator|(
literal|true
operator|)
return|;
block|}
else|else
block|{
name|Class_
name|c
init|=
literal|null
decl_stmt|;
for|for
control|(
name|Iterator
name|cIt
init|=
name|ss
operator|.
name|getParentSubpart
argument_list|()
operator|.
name|getClasses
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|cIt
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|c
operator|=
operator|(
name|Class_
operator|)
name|cIt
operator|.
name|next
argument_list|()
expr_stmt|;
if|if
condition|(
name|c
operator|.
name|isOddOrEvenWeeksOnly
argument_list|()
condition|)
block|{
return|return
operator|(
literal|true
operator|)
return|;
block|}
block|}
for|for
control|(
name|Iterator
name|cIt
init|=
name|ss
operator|.
name|getClasses
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|cIt
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|c
operator|=
operator|(
name|Class_
operator|)
name|cIt
operator|.
name|next
argument_list|()
expr_stmt|;
if|if
condition|(
name|c
operator|.
name|isOddOrEvenWeeksOnly
argument_list|()
condition|)
block|{
return|return
operator|(
literal|true
operator|)
return|;
block|}
block|}
block|}
block|}
block|}
block|}
return|return
operator|(
literal|false
operator|)
return|;
block|}
specifier|public
name|Object
name|clone
parameter_list|()
block|{
name|InstrOfferingConfig
name|newInstrOffrConfig
init|=
operator|new
name|InstrOfferingConfig
argument_list|()
decl_stmt|;
name|newInstrOffrConfig
operator|.
name|setLimit
argument_list|(
name|getLimit
argument_list|()
argument_list|)
expr_stmt|;
name|newInstrOffrConfig
operator|.
name|setName
argument_list|(
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|newInstrOffrConfig
operator|.
name|setUnlimitedEnrollment
argument_list|(
name|isUnlimitedEnrollment
argument_list|()
argument_list|)
expr_stmt|;
name|newInstrOffrConfig
operator|.
name|setClassDurationType
argument_list|(
name|getClassDurationType
argument_list|()
argument_list|)
expr_stmt|;
name|newInstrOffrConfig
operator|.
name|setInstructionalMethod
argument_list|(
name|getInstructionalMethod
argument_list|()
argument_list|)
expr_stmt|;
return|return
operator|(
name|newInstrOffrConfig
operator|)
return|;
block|}
specifier|private
name|void
name|setSubpartConfig
parameter_list|(
name|SchedulingSubpart
name|schedulingSubpart
parameter_list|,
name|InstrOfferingConfig
name|instrOffrConfig
parameter_list|)
block|{
name|schedulingSubpart
operator|.
name|setInstrOfferingConfig
argument_list|(
name|instrOffrConfig
argument_list|)
expr_stmt|;
name|instrOffrConfig
operator|.
name|addToschedulingSubparts
argument_list|(
name|schedulingSubpart
argument_list|)
expr_stmt|;
if|if
condition|(
name|schedulingSubpart
operator|.
name|getChildSubparts
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|SchedulingSubpart
name|childSubpart
init|=
literal|null
decl_stmt|;
for|for
control|(
name|Iterator
name|cssIt
init|=
name|schedulingSubpart
operator|.
name|getChildSubparts
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|cssIt
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|childSubpart
operator|=
operator|(
name|SchedulingSubpart
operator|)
name|cssIt
operator|.
name|next
argument_list|()
expr_stmt|;
name|setSubpartConfig
argument_list|(
name|childSubpart
argument_list|,
name|instrOffrConfig
argument_list|)
expr_stmt|;
block|}
block|}
block|}
specifier|public
name|Object
name|cloneWithSubparts
parameter_list|()
block|{
name|InstrOfferingConfig
name|newInstrOffrConfig
init|=
operator|(
name|InstrOfferingConfig
operator|)
name|clone
argument_list|()
decl_stmt|;
if|if
condition|(
name|getSchedulingSubparts
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|SchedulingSubpart
name|origSubpart
init|=
literal|null
decl_stmt|;
name|SchedulingSubpart
name|newSubpart
init|=
literal|null
decl_stmt|;
for|for
control|(
name|Iterator
name|ssIt
init|=
name|getSchedulingSubparts
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|ssIt
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|origSubpart
operator|=
operator|(
name|SchedulingSubpart
operator|)
name|ssIt
operator|.
name|next
argument_list|()
expr_stmt|;
if|if
condition|(
name|origSubpart
operator|.
name|getParentSubpart
argument_list|()
operator|==
literal|null
condition|)
block|{
name|newSubpart
operator|=
operator|(
name|SchedulingSubpart
operator|)
name|origSubpart
operator|.
name|cloneDeep
argument_list|()
expr_stmt|;
name|setSubpartConfig
argument_list|(
name|newSubpart
argument_list|,
name|newInstrOffrConfig
argument_list|)
expr_stmt|;
block|}
block|}
block|}
return|return
operator|(
name|newInstrOffrConfig
operator|)
return|;
block|}
specifier|public
specifier|static
name|InstrOfferingConfig
name|findByIdRolledForwardFrom
parameter_list|(
name|Long
name|sessionId
parameter_list|,
name|Long
name|uniqueIdRolledForwardFrom
parameter_list|)
block|{
return|return
operator|(
name|InstrOfferingConfig
operator|)
operator|new
name|InstrOfferingConfigDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select ioc from InstrOfferingConfig ioc where ioc.instructionalOffering.session.uniqueId=:sessionId and ioc.uniqueIdRolledForwardFrom=:uniqueIdRolledForwardFrom"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
operator|.
name|longValue
argument_list|()
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"uniqueIdRolledForwardFrom"
argument_list|,
name|uniqueIdRolledForwardFrom
operator|.
name|longValue
argument_list|()
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|uniqueResult
argument_list|()
return|;
block|}
specifier|public
name|ClassDurationType
name|getEffectiveDurationType
parameter_list|()
block|{
if|if
condition|(
name|getClassDurationType
argument_list|()
operator|!=
literal|null
condition|)
return|return
name|getClassDurationType
argument_list|()
return|;
return|return
name|getSession
argument_list|()
operator|.
name|getDefaultClassDurationType
argument_list|()
return|;
block|}
specifier|public
name|DurationModel
name|getDurationModel
parameter_list|()
block|{
name|ClassDurationType
name|type
init|=
name|getEffectiveDurationType
argument_list|()
decl_stmt|;
if|if
condition|(
name|type
operator|==
literal|null
condition|)
return|return
operator|new
name|MinutesPerWeek
argument_list|(
literal|null
argument_list|)
return|;
else|else
return|return
name|type
operator|.
name|getModel
argument_list|()
return|;
block|}
block|}
end_class

end_unit

