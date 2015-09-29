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
name|Calendar
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
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Locale
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
name|BaseReservation
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|Reservation
extends|extends
name|BaseReservation
implements|implements
name|Comparable
argument_list|<
name|Reservation
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
comment|/*[CONSTRUCTOR MARKER BEGIN]*/
specifier|public
name|Reservation
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|Reservation
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
name|boolean
name|isExpired
parameter_list|()
block|{
if|if
condition|(
name|getExpirationDate
argument_list|()
operator|==
literal|null
condition|)
return|return
literal|false
return|;
name|Calendar
name|c
init|=
name|Calendar
operator|.
name|getInstance
argument_list|(
name|Locale
operator|.
name|US
argument_list|)
decl_stmt|;
name|c
operator|.
name|set
argument_list|(
name|Calendar
operator|.
name|HOUR_OF_DAY
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|c
operator|.
name|set
argument_list|(
name|Calendar
operator|.
name|MINUTE
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|c
operator|.
name|set
argument_list|(
name|Calendar
operator|.
name|SECOND
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|c
operator|.
name|set
argument_list|(
name|Calendar
operator|.
name|MILLISECOND
argument_list|,
literal|0
argument_list|)
expr_stmt|;
return|return
name|getExpirationDate
argument_list|()
operator|.
name|before
argument_list|(
name|c
operator|.
name|getTime
argument_list|()
argument_list|)
return|;
block|}
specifier|public
specifier|abstract
name|boolean
name|isApplicable
parameter_list|(
name|Student
name|student
parameter_list|,
name|CourseRequest
name|request
parameter_list|)
function_decl|;
specifier|public
name|int
name|getReservationLimit
parameter_list|()
block|{
return|return
operator|(
name|getLimit
argument_list|()
operator|==
literal|null
condition|?
operator|-
literal|1
else|:
name|getLimit
argument_list|()
operator|.
name|intValue
argument_list|()
operator|)
return|;
block|}
specifier|private
name|boolean
name|hasClass
parameter_list|(
name|Class_
name|clazz
parameter_list|)
block|{
for|for
control|(
name|Class_
name|other
range|:
name|getClasses
argument_list|()
control|)
block|{
if|if
condition|(
name|clazz
operator|.
name|equals
argument_list|(
name|other
argument_list|)
operator|||
name|other
operator|.
name|isParentOf
argument_list|(
name|clazz
argument_list|)
operator|||
name|clazz
operator|.
name|isParentOf
argument_list|(
name|other
argument_list|)
condition|)
return|return
literal|true
return|;
block|}
return|return
literal|false
return|;
block|}
specifier|public
name|boolean
name|isMatching
parameter_list|(
name|List
argument_list|<
name|StudentClassEnrollment
argument_list|>
name|enrollment
parameter_list|)
block|{
if|if
condition|(
name|enrollment
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
literal|false
return|;
if|if
condition|(
operator|!
name|getConfigurations
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
for|for
control|(
name|StudentClassEnrollment
name|e
range|:
name|enrollment
control|)
block|{
if|if
condition|(
operator|!
name|getConfigurations
argument_list|()
operator|.
name|contains
argument_list|(
name|e
operator|.
name|getClazz
argument_list|()
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getInstrOfferingConfig
argument_list|()
argument_list|)
condition|)
return|return
literal|false
return|;
block|}
block|}
if|if
condition|(
operator|!
name|getClasses
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
for|for
control|(
name|StudentClassEnrollment
name|e
range|:
name|enrollment
control|)
block|{
if|if
condition|(
operator|!
name|hasClass
argument_list|(
name|e
operator|.
name|getClazz
argument_list|()
argument_list|)
condition|)
return|return
literal|false
return|;
block|}
block|}
return|return
literal|true
return|;
block|}
specifier|public
specifier|abstract
name|int
name|getPriority
parameter_list|()
function_decl|;
specifier|public
specifier|abstract
name|boolean
name|isCanAssignOverLimit
parameter_list|()
function_decl|;
specifier|public
specifier|abstract
name|boolean
name|isMustBeUsed
parameter_list|()
function_decl|;
specifier|public
specifier|abstract
name|boolean
name|isAllowOverlap
parameter_list|()
function_decl|;
annotation|@
name|Override
specifier|public
name|int
name|compareTo
parameter_list|(
name|Reservation
name|r
parameter_list|)
block|{
if|if
condition|(
name|getPriority
argument_list|()
operator|!=
name|r
operator|.
name|getPriority
argument_list|()
condition|)
block|{
return|return
operator|(
name|getPriority
argument_list|()
operator|<
name|r
operator|.
name|getPriority
argument_list|()
condition|?
operator|-
literal|1
else|:
literal|1
operator|)
return|;
block|}
name|int
name|cmp
init|=
name|Double
operator|.
name|compare
argument_list|(
name|getRestrictivity
argument_list|()
argument_list|,
name|r
operator|.
name|getRestrictivity
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
name|getUniqueId
argument_list|()
operator|.
name|compareTo
argument_list|(
name|r
operator|.
name|getUniqueId
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|double
name|getRestrictivity
parameter_list|()
block|{
if|if
condition|(
name|getConfigurations
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
literal|1.0
return|;
name|double
name|restrictivity
init|=
operator|(
operator|(
name|double
operator|)
name|getConfigurations
argument_list|()
operator|.
name|size
argument_list|()
operator|)
operator|/
name|getInstructionalOffering
argument_list|()
operator|.
name|getInstrOfferingConfigs
argument_list|()
operator|.
name|size
argument_list|()
decl_stmt|;
if|if
condition|(
name|getClasses
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
name|restrictivity
return|;
name|Map
argument_list|<
name|SchedulingSubpart
argument_list|,
name|Integer
argument_list|>
name|counts
init|=
operator|new
name|HashMap
argument_list|<
name|SchedulingSubpart
argument_list|,
name|Integer
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Class_
name|clazz
range|:
name|getClasses
argument_list|()
control|)
block|{
name|Integer
name|old
init|=
name|counts
operator|.
name|get
argument_list|(
name|clazz
operator|.
name|getSchedulingSubpart
argument_list|()
argument_list|)
decl_stmt|;
name|counts
operator|.
name|put
argument_list|(
name|clazz
operator|.
name|getSchedulingSubpart
argument_list|()
argument_list|,
literal|1
operator|+
operator|(
name|old
operator|==
literal|null
condition|?
literal|0
else|:
name|old
operator|.
name|intValue
argument_list|()
operator|)
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|SchedulingSubpart
argument_list|,
name|Integer
argument_list|>
name|entry
range|:
name|counts
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|restrictivity
operator|*=
operator|(
operator|(
name|double
operator|)
name|entry
operator|.
name|getValue
argument_list|()
operator|.
name|intValue
argument_list|()
operator|)
operator|/
name|entry
operator|.
name|getKey
argument_list|()
operator|.
name|getClasses
argument_list|()
operator|.
name|size
argument_list|()
expr_stmt|;
block|}
return|return
name|restrictivity
return|;
block|}
specifier|protected
name|Map
argument_list|<
name|Long
argument_list|,
name|Set
argument_list|<
name|Long
argument_list|>
argument_list|>
name|getSections
parameter_list|()
block|{
name|Map
argument_list|<
name|Long
argument_list|,
name|Set
argument_list|<
name|Long
argument_list|>
argument_list|>
name|ret
init|=
operator|new
name|HashMap
argument_list|<
name|Long
argument_list|,
name|Set
argument_list|<
name|Long
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Class_
name|clazz
range|:
name|getClasses
argument_list|()
control|)
block|{
while|while
condition|(
name|clazz
operator|!=
literal|null
condition|)
block|{
name|Set
argument_list|<
name|Long
argument_list|>
name|sections
init|=
name|ret
operator|.
name|get
argument_list|(
name|clazz
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|sections
operator|==
literal|null
condition|)
block|{
name|sections
operator|=
operator|new
name|HashSet
argument_list|<
name|Long
argument_list|>
argument_list|()
expr_stmt|;
name|ret
operator|.
name|put
argument_list|(
name|clazz
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|sections
argument_list|)
expr_stmt|;
block|}
name|sections
operator|.
name|add
argument_list|(
name|clazz
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|clazz
operator|=
name|clazz
operator|.
name|getParentClass
argument_list|()
expr_stmt|;
block|}
block|}
return|return
name|ret
return|;
block|}
specifier|public
name|int
name|getReservedAvailableSpace
parameter_list|()
block|{
comment|// Unlimited
if|if
condition|(
name|getReservationLimit
argument_list|()
operator|<
literal|0
condition|)
return|return
name|Integer
operator|.
name|MAX_VALUE
return|;
return|return
name|getReservationLimit
argument_list|()
operator|-
name|countEnrollmentsForReservation
argument_list|()
return|;
block|}
specifier|private
name|int
name|countEnrollmentsForReservation
parameter_list|()
block|{
name|Set
argument_list|<
name|Long
argument_list|>
name|checked
init|=
operator|new
name|HashSet
argument_list|<
name|Long
argument_list|>
argument_list|()
decl_stmt|;
name|Set
argument_list|<
name|Long
argument_list|>
name|students
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
name|InstrOfferingConfig
name|config
range|:
name|getInstructionalOffering
argument_list|()
operator|.
name|getInstrOfferingConfigs
argument_list|()
control|)
for|for
control|(
name|SchedulingSubpart
name|subpart
range|:
name|config
operator|.
name|getSchedulingSubparts
argument_list|()
control|)
for|for
control|(
name|Class_
name|clazz
range|:
name|subpart
operator|.
name|getClasses
argument_list|()
control|)
for|for
control|(
name|StudentClassEnrollment
name|e
range|:
name|clazz
operator|.
name|getStudentEnrollments
argument_list|()
control|)
if|if
condition|(
name|e
operator|.
name|getCourseRequest
argument_list|()
operator|!=
literal|null
operator|&&
name|checked
operator|.
name|add
argument_list|(
name|e
operator|.
name|getCourseRequest
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|&&
name|isApplicable
argument_list|(
name|e
operator|.
name|getStudent
argument_list|()
argument_list|,
name|e
operator|.
name|getCourseRequest
argument_list|()
argument_list|)
operator|&&
name|isMatching
argument_list|(
name|e
operator|.
name|getCourseRequest
argument_list|()
operator|.
name|getClassEnrollments
argument_list|()
argument_list|)
condition|)
block|{
name|students
operator|.
name|add
argument_list|(
name|e
operator|.
name|getStudent
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|students
operator|.
name|size
argument_list|()
return|;
block|}
block|}
end_class

end_unit

