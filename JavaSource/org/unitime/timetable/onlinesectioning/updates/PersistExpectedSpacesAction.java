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
name|updates
package|;
end_package

begin_import
import|import
name|java
operator|.
name|text
operator|.
name|DecimalFormat
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
name|Collection
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
name|Location
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
name|SectioningInfo
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
name|server
operator|.
name|CheckMaster
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
name|server
operator|.
name|CheckMaster
operator|.
name|Master
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|CheckMaster
argument_list|(
name|Master
operator|.
name|REQUIRED
argument_list|)
specifier|public
class|class
name|PersistExpectedSpacesAction
implements|implements
name|OnlineSectioningAction
argument_list|<
name|Boolean
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
specifier|private
specifier|static
name|DecimalFormat
name|sDF
init|=
operator|new
name|DecimalFormat
argument_list|(
literal|"+0.000;-0.000"
argument_list|)
decl_stmt|;
specifier|public
name|PersistExpectedSpacesAction
name|forOfferings
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
return|return
name|this
return|;
block|}
specifier|public
name|PersistExpectedSpacesAction
name|forOfferings
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
return|return
name|this
return|;
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
for|for
control|(
name|Long
name|offeringId
range|:
name|getOfferingIds
argument_list|()
control|)
block|{
try|try
block|{
name|helper
operator|.
name|beginTransaction
argument_list|()
expr_stmt|;
name|persistExpectedSpaces
argument_list|(
name|offeringId
argument_list|,
literal|true
argument_list|,
name|server
argument_list|,
name|helper
argument_list|)
expr_stmt|;
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
name|rollbackTransaction
argument_list|()
expr_stmt|;
name|helper
operator|.
name|error
argument_list|(
name|MSG
operator|.
name|exceptionUnknown
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
return|return
literal|true
return|;
block|}
specifier|private
specifier|static
name|int
name|getLimit
parameter_list|(
name|Class_
name|clazz
parameter_list|)
block|{
name|int
name|limit
init|=
operator|-
literal|1
decl_stmt|;
if|if
condition|(
operator|!
name|clazz
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getInstrOfferingConfig
argument_list|()
operator|.
name|isUnlimitedEnrollment
argument_list|()
condition|)
block|{
name|limit
operator|=
name|clazz
operator|.
name|getMaxExpectedCapacity
argument_list|()
expr_stmt|;
if|if
condition|(
name|clazz
operator|.
name|getExpectedCapacity
argument_list|()
operator|<
name|clazz
operator|.
name|getMaxExpectedCapacity
argument_list|()
operator|&&
name|clazz
operator|.
name|getCommittedAssignment
argument_list|()
operator|!=
literal|null
operator|&&
name|clazz
operator|.
name|getCommittedAssignment
argument_list|()
operator|.
name|getRooms
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|int
name|roomSize
init|=
name|Integer
operator|.
name|MAX_VALUE
decl_stmt|;
for|for
control|(
name|Location
name|room
range|:
name|clazz
operator|.
name|getCommittedAssignment
argument_list|()
operator|.
name|getRooms
argument_list|()
control|)
name|roomSize
operator|=
name|Math
operator|.
name|min
argument_list|(
name|roomSize
argument_list|,
name|room
operator|.
name|getCapacity
argument_list|()
operator|==
literal|null
condition|?
literal|0
else|:
name|room
operator|.
name|getCapacity
argument_list|()
argument_list|)
expr_stmt|;
name|int
name|roomLimit
init|=
operator|(
name|int
operator|)
name|Math
operator|.
name|floor
argument_list|(
name|roomSize
operator|/
operator|(
name|clazz
operator|.
name|getRoomRatio
argument_list|()
operator|==
literal|null
condition|?
literal|1.0f
else|:
name|clazz
operator|.
name|getRoomRatio
argument_list|()
operator|)
argument_list|)
decl_stmt|;
name|limit
operator|=
name|Math
operator|.
name|min
argument_list|(
name|Math
operator|.
name|max
argument_list|(
name|clazz
operator|.
name|getExpectedCapacity
argument_list|()
argument_list|,
name|roomLimit
argument_list|)
argument_list|,
name|clazz
operator|.
name|getMaxExpectedCapacity
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|limit
operator|>=
literal|9999
condition|)
name|limit
operator|=
operator|-
literal|1
expr_stmt|;
block|}
return|return
name|limit
return|;
block|}
specifier|public
specifier|static
name|void
name|persistExpectedSpaces
parameter_list|(
name|Long
name|offeringId
parameter_list|,
name|boolean
name|needLock
parameter_list|,
name|OnlineSectioningServer
name|server
parameter_list|,
name|OnlineSectioningHelper
name|helper
parameter_list|)
block|{
name|Map
argument_list|<
name|Long
argument_list|,
name|Double
argument_list|>
name|expectations
init|=
name|server
operator|.
name|getExpectations
argument_list|(
name|offeringId
argument_list|)
operator|.
name|toMap
argument_list|()
decl_stmt|;
if|if
condition|(
name|expectations
operator|==
literal|null
operator|||
name|expectations
operator|.
name|isEmpty
argument_list|()
condition|)
return|return;
for|for
control|(
name|SectioningInfo
name|info
range|:
operator|(
name|List
argument_list|<
name|SectioningInfo
argument_list|>
operator|)
name|helper
operator|.
name|getHibSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select i from SectioningInfo i "
operator|+
literal|"left join fetch i.clazz as c "
operator|+
literal|"where i.clazz.schedulingSubpart.instrOfferingConfig.instructionalOffering = :offeringId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"offeringId"
argument_list|,
name|offeringId
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
control|)
block|{
name|Double
name|expectation
init|=
name|expectations
operator|.
name|remove
argument_list|(
name|info
operator|.
name|getClazz
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|expectation
operator|==
literal|null
condition|)
block|{
name|helper
operator|.
name|getHibSession
argument_list|()
operator|.
name|delete
argument_list|(
name|info
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
operator|!
name|expectation
operator|.
name|equals
argument_list|(
name|info
operator|.
name|getNbrExpectedStudents
argument_list|()
argument_list|)
condition|)
block|{
name|helper
operator|.
name|debug
argument_list|(
name|info
operator|.
name|getClazz
argument_list|()
operator|.
name|getClassLabel
argument_list|(
name|helper
operator|.
name|getHibSession
argument_list|()
argument_list|)
operator|+
literal|": expected "
operator|+
name|sDF
operator|.
name|format
argument_list|(
name|expectation
operator|-
name|info
operator|.
name|getNbrExpectedStudents
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|int
name|limit
init|=
name|getLimit
argument_list|(
name|info
operator|.
name|getClazz
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|limit
operator|>=
literal|0
operator|&&
name|limit
operator|>=
name|info
operator|.
name|getNbrExpectedStudents
argument_list|()
operator|&&
name|limit
operator|<
name|expectation
condition|)
name|helper
operator|.
name|debug
argument_list|(
name|info
operator|.
name|getClazz
argument_list|()
operator|.
name|getClassLabel
argument_list|(
name|helper
operator|.
name|getHibSession
argument_list|()
argument_list|)
operator|+
literal|": become over-expected"
argument_list|)
expr_stmt|;
if|if
condition|(
name|limit
operator|>=
literal|0
operator|&&
name|limit
operator|<
name|info
operator|.
name|getNbrExpectedStudents
argument_list|()
operator|&&
name|limit
operator|>=
name|expectation
condition|)
name|helper
operator|.
name|debug
argument_list|(
name|info
operator|.
name|getClazz
argument_list|()
operator|.
name|getClassLabel
argument_list|(
name|helper
operator|.
name|getHibSession
argument_list|()
argument_list|)
operator|+
literal|": no longer over-expected"
argument_list|)
expr_stmt|;
name|info
operator|.
name|setNbrExpectedStudents
argument_list|(
name|expectation
argument_list|)
expr_stmt|;
name|helper
operator|.
name|getHibSession
argument_list|()
operator|.
name|saveOrUpdate
argument_list|(
name|info
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
operator|!
name|expectations
operator|.
name|isEmpty
argument_list|()
condition|)
for|for
control|(
name|Class_
name|clazz
range|:
operator|(
name|List
argument_list|<
name|Class_
argument_list|>
operator|)
name|helper
operator|.
name|getHibSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select c from Class_ c where c.schedulingSubpart.instrOfferingConfig.instructionalOffering = :offeringId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"offeringId"
argument_list|,
name|offeringId
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
control|)
block|{
name|Double
name|expectation
init|=
name|expectations
operator|.
name|remove
argument_list|(
name|clazz
operator|.
name|getUniqueId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|expectation
operator|==
literal|null
condition|)
continue|continue;
name|SectioningInfo
name|info
init|=
operator|new
name|SectioningInfo
argument_list|()
decl_stmt|;
name|helper
operator|.
name|debug
argument_list|(
name|clazz
operator|.
name|getClassLabel
argument_list|(
name|helper
operator|.
name|getHibSession
argument_list|()
argument_list|)
operator|+
literal|": expected "
operator|+
name|sDF
operator|.
name|format
argument_list|(
name|expectation
argument_list|)
operator|+
literal|" (new)"
argument_list|)
expr_stmt|;
name|int
name|limit
init|=
name|getLimit
argument_list|(
name|clazz
argument_list|)
decl_stmt|;
if|if
condition|(
name|limit
operator|>=
literal|0
operator|&&
name|limit
operator|<
name|expectation
condition|)
name|helper
operator|.
name|debug
argument_list|(
name|clazz
operator|.
name|getClassLabel
argument_list|(
name|helper
operator|.
name|getHibSession
argument_list|()
argument_list|)
operator|+
literal|": become over-expected"
argument_list|)
expr_stmt|;
name|info
operator|.
name|setClazz
argument_list|(
name|clazz
argument_list|)
expr_stmt|;
name|info
operator|.
name|setNbrExpectedStudents
argument_list|(
name|expectation
argument_list|)
expr_stmt|;
name|info
operator|.
name|setNbrHoldingStudents
argument_list|(
literal|0.0
argument_list|)
expr_stmt|;
name|helper
operator|.
name|getHibSession
argument_list|()
operator|.
name|saveOrUpdate
argument_list|(
name|info
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|String
name|name
parameter_list|()
block|{
return|return
literal|"persist-expectations"
return|;
block|}
block|}
end_class

end_unit

