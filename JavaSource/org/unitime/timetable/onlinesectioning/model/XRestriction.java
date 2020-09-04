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
name|cpsolver
operator|.
name|studentsct
operator|.
name|model
operator|.
name|Config
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
name|cpsolver
operator|.
name|studentsct
operator|.
name|model
operator|.
name|Subpart
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|XRestriction
extends|extends
name|XRestrictionId
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
name|Set
argument_list|<
name|Long
argument_list|>
name|iConfigs
init|=
operator|new
name|HashSet
argument_list|<
name|Long
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|Map
argument_list|<
name|Long
argument_list|,
name|Set
argument_list|<
name|Long
argument_list|>
argument_list|>
name|iSections
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
specifier|private
name|Set
argument_list|<
name|Long
argument_list|>
name|iIds
init|=
operator|new
name|HashSet
argument_list|<
name|Long
argument_list|>
argument_list|()
decl_stmt|;
specifier|public
name|XRestriction
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
specifier|public
name|XRestriction
parameter_list|(
name|XRestrictionType
name|type
parameter_list|,
name|org
operator|.
name|cpsolver
operator|.
name|studentsct
operator|.
name|reservation
operator|.
name|Restriction
name|restriction
parameter_list|)
block|{
name|super
argument_list|(
name|type
argument_list|,
name|restriction
operator|.
name|getOffering
argument_list|()
operator|.
name|getId
argument_list|()
argument_list|,
name|restriction
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|Config
name|config
range|:
name|restriction
operator|.
name|getConfigs
argument_list|()
control|)
block|{
name|iConfigs
operator|.
name|add
argument_list|(
name|config
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
name|iIds
operator|.
name|add
argument_list|(
operator|-
name|config
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|Subpart
argument_list|,
name|Set
argument_list|<
name|Section
argument_list|>
argument_list|>
name|entry
range|:
name|restriction
operator|.
name|getSections
argument_list|()
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|Set
argument_list|<
name|Long
argument_list|>
name|sections
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
name|Section
name|section
range|:
name|entry
operator|.
name|getValue
argument_list|()
control|)
block|{
name|sections
operator|.
name|add
argument_list|(
name|section
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
name|iIds
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
name|iSections
operator|.
name|put
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
operator|.
name|getId
argument_list|()
argument_list|,
name|sections
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Returns true if the student is applicable for the reservation      * @param student a student       * @return true if student can use the reservation to get into the course / configuration / section      */
specifier|public
specifier|abstract
name|boolean
name|isApplicable
parameter_list|(
name|XStudent
name|student
parameter_list|,
name|XCourseId
name|course
parameter_list|)
function_decl|;
comment|/**      * One or more configurations on which the reservation is set (optional).      */
specifier|public
name|Set
argument_list|<
name|Long
argument_list|>
name|getConfigsIds
parameter_list|()
block|{
return|return
name|iConfigs
return|;
block|}
specifier|public
name|boolean
name|hasConfigRestriction
parameter_list|(
name|Long
name|configId
parameter_list|)
block|{
return|return
name|iIds
operator|.
name|contains
argument_list|(
operator|-
name|configId
argument_list|)
return|;
block|}
specifier|public
name|boolean
name|hasSectionRestriction
parameter_list|(
name|Long
name|sectionId
parameter_list|)
block|{
return|return
name|iIds
operator|.
name|contains
argument_list|(
name|sectionId
argument_list|)
return|;
block|}
comment|/**      * One or more sections on which the reservation is set (optional).      */
specifier|public
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
return|return
name|iSections
return|;
block|}
comment|/**      * One or more sections on which the reservation is set (optional).      */
specifier|public
name|Set
argument_list|<
name|Long
argument_list|>
name|getSectionIds
parameter_list|(
name|Long
name|subpartId
parameter_list|)
block|{
return|return
name|iSections
operator|.
name|get
argument_list|(
name|subpartId
argument_list|)
return|;
block|}
comment|/**      * Return true if the given enrollment meets the reservation.      */
specifier|public
name|boolean
name|isIncluded
parameter_list|(
name|Long
name|configId
parameter_list|,
name|List
argument_list|<
name|XSection
argument_list|>
name|sections
parameter_list|)
block|{
comment|// no restrictions -> not included
if|if
condition|(
name|iConfigs
operator|.
name|isEmpty
argument_list|()
operator|&&
name|iSections
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
literal|false
return|;
comment|// If there are configurations, check the configuration
if|if
condition|(
operator|!
name|iConfigs
operator|.
name|isEmpty
argument_list|()
operator|&&
operator|!
name|iConfigs
operator|.
name|contains
argument_list|(
name|configId
argument_list|)
condition|)
return|return
literal|false
return|;
comment|// Check all the sections of the enrollment
for|for
control|(
name|XSection
name|section
range|:
name|sections
control|)
block|{
name|Set
argument_list|<
name|Long
argument_list|>
name|reserved
init|=
name|iSections
operator|.
name|get
argument_list|(
name|section
operator|.
name|getSubpartId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|reserved
operator|!=
literal|null
operator|&&
operator|!
name|reserved
operator|.
name|contains
argument_list|(
name|section
operator|.
name|getSectionId
argument_list|()
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
name|int
name|nrConfigs
init|=
name|in
operator|.
name|readInt
argument_list|()
decl_stmt|;
name|iConfigs
operator|.
name|clear
argument_list|()
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|nrConfigs
condition|;
name|i
operator|++
control|)
name|iConfigs
operator|.
name|add
argument_list|(
name|in
operator|.
name|readLong
argument_list|()
argument_list|)
expr_stmt|;
name|int
name|nrSubparts
init|=
name|in
operator|.
name|readInt
argument_list|()
decl_stmt|;
name|iSections
operator|.
name|clear
argument_list|()
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|nrSubparts
condition|;
name|i
operator|++
control|)
block|{
name|Set
argument_list|<
name|Long
argument_list|>
name|sections
init|=
operator|new
name|HashSet
argument_list|<
name|Long
argument_list|>
argument_list|()
decl_stmt|;
name|iSections
operator|.
name|put
argument_list|(
name|in
operator|.
name|readLong
argument_list|()
argument_list|,
name|sections
argument_list|)
expr_stmt|;
name|int
name|nrSection
init|=
name|in
operator|.
name|readInt
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|j
init|=
literal|0
init|;
name|j
operator|<
name|nrSection
condition|;
name|j
operator|++
control|)
block|{
name|sections
operator|.
name|add
argument_list|(
name|in
operator|.
name|readLong
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
name|iIds
operator|.
name|clear
argument_list|()
expr_stmt|;
name|int
name|nrIds
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
name|nrIds
condition|;
name|i
operator|++
control|)
name|iIds
operator|.
name|add
argument_list|(
name|in
operator|.
name|readLong
argument_list|()
argument_list|)
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
name|writeInt
argument_list|(
name|iConfigs
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|Long
name|config
range|:
name|iConfigs
control|)
name|out
operator|.
name|writeLong
argument_list|(
name|config
argument_list|)
expr_stmt|;
name|Set
argument_list|<
name|Map
operator|.
name|Entry
argument_list|<
name|Long
argument_list|,
name|Set
argument_list|<
name|Long
argument_list|>
argument_list|>
argument_list|>
name|entries
init|=
name|iSections
operator|.
name|entrySet
argument_list|()
decl_stmt|;
name|out
operator|.
name|writeInt
argument_list|(
name|entries
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|Long
argument_list|,
name|Set
argument_list|<
name|Long
argument_list|>
argument_list|>
name|entry
range|:
name|entries
control|)
block|{
name|out
operator|.
name|writeLong
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeInt
argument_list|(
name|entry
operator|.
name|getValue
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|Long
name|id
range|:
name|entry
operator|.
name|getValue
argument_list|()
control|)
name|out
operator|.
name|writeLong
argument_list|(
name|id
argument_list|)
expr_stmt|;
block|}
name|out
operator|.
name|writeInt
argument_list|(
name|iIds
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|Long
name|id
range|:
name|iIds
control|)
name|out
operator|.
name|writeLong
argument_list|(
name|id
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

