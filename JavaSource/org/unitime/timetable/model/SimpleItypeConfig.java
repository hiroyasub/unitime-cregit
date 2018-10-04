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
name|Vector
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
name|defaults
operator|.
name|SessionAttribute
import|;
end_import

begin_comment
comment|/**  * Config for user manipulations  * Not stored in database till user hits commit   */
end_comment

begin_class
specifier|public
class|class
name|SimpleItypeConfig
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
comment|// Generates unique ids for itypes
specifier|private
specifier|static
name|long
name|currId
init|=
literal|0
decl_stmt|;
specifier|private
name|long
name|id
decl_stmt|;
specifier|private
name|long
name|subpartId
decl_stmt|;
specifier|private
name|ItypeDesc
name|itype
decl_stmt|;
specifier|private
name|SimpleItypeConfig
name|parent
decl_stmt|;
specifier|private
name|Vector
name|subparts
decl_stmt|;
specifier|private
name|int
name|numClasses
decl_stmt|;
specifier|private
name|int
name|numRooms
decl_stmt|;
specifier|private
name|int
name|minLimitPerClass
decl_stmt|;
specifier|private
name|int
name|maxLimitPerClass
decl_stmt|;
specifier|private
name|int
name|minPerWeek
decl_stmt|;
specifier|private
name|float
name|roomRatio
decl_stmt|;
specifier|private
name|boolean
name|disabled
decl_stmt|;
specifier|private
name|boolean
name|notOwned
decl_stmt|;
specifier|private
name|boolean
name|hasError
decl_stmt|;
specifier|private
name|long
name|managingDeptId
decl_stmt|;
comment|/** Request attribute name for user defined config **/
specifier|public
specifier|static
name|String
name|CONFIGS_ATTR_NAME
init|=
name|SessionAttribute
operator|.
name|InstructionalOfferingConfigList
operator|.
name|key
argument_list|()
decl_stmt|;
comment|/** Default Constructor **/
specifier|public
name|SimpleItypeConfig
parameter_list|(
name|ItypeDesc
name|itype
parameter_list|)
block|{
name|this
argument_list|(
name|itype
argument_list|,
operator|-
literal|1L
argument_list|)
expr_stmt|;
block|}
specifier|public
name|SimpleItypeConfig
parameter_list|(
name|ItypeDesc
name|itype
parameter_list|,
name|long
name|subpartId
parameter_list|)
block|{
if|if
condition|(
name|currId
operator|>
name|Long
operator|.
name|MAX_VALUE
condition|)
name|currId
operator|=
literal|0
expr_stmt|;
name|this
operator|.
name|id
operator|=
operator|++
name|currId
expr_stmt|;
name|this
operator|.
name|subpartId
operator|=
name|subpartId
expr_stmt|;
name|this
operator|.
name|itype
operator|=
name|itype
expr_stmt|;
name|this
operator|.
name|numClasses
operator|=
operator|-
literal|1
expr_stmt|;
name|this
operator|.
name|numRooms
operator|=
literal|1
expr_stmt|;
name|this
operator|.
name|minLimitPerClass
operator|=
operator|-
literal|1
expr_stmt|;
name|this
operator|.
name|maxLimitPerClass
operator|=
operator|-
literal|1
expr_stmt|;
name|this
operator|.
name|roomRatio
operator|=
literal|1.0f
expr_stmt|;
name|this
operator|.
name|minPerWeek
operator|=
operator|-
literal|1
expr_stmt|;
name|this
operator|.
name|managingDeptId
operator|=
operator|-
literal|1
expr_stmt|;
name|subparts
operator|=
operator|new
name|Vector
argument_list|()
expr_stmt|;
name|disabled
operator|=
literal|false
expr_stmt|;
name|notOwned
operator|=
literal|false
expr_stmt|;
name|hasError
operator|=
literal|false
expr_stmt|;
block|}
comment|/**      * Add subpart to itype      * @param config subpart config      */
specifier|public
name|void
name|addSubpart
parameter_list|(
name|SimpleItypeConfig
name|config
parameter_list|)
block|{
name|config
operator|.
name|setParent
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|subparts
operator|.
name|addElement
argument_list|(
name|config
argument_list|)
expr_stmt|;
block|}
comment|/**      * @return Returns the subparts.      */
specifier|public
name|Vector
name|getSubparts
parameter_list|()
block|{
return|return
name|subparts
return|;
block|}
comment|/**      * @param subparts The children to set.      */
specifier|public
name|void
name|setSubparts
parameter_list|(
name|Vector
name|subparts
parameter_list|)
block|{
name|this
operator|.
name|subparts
operator|=
name|subparts
expr_stmt|;
block|}
comment|/**      * @return Returns the itype.      */
specifier|public
name|ItypeDesc
name|getItype
parameter_list|()
block|{
return|return
name|itype
return|;
block|}
comment|/**      * @param itype The itype to set.      */
specifier|public
name|void
name|setItype
parameter_list|(
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
comment|/**      * @return Returns the minLimitPerClass.      */
specifier|public
name|int
name|getMinLimitPerClass
parameter_list|()
block|{
return|return
name|minLimitPerClass
return|;
block|}
comment|/**      * @param minLimitPerClass The minLimitPerClass to set.      */
specifier|public
name|void
name|setMinLimitPerClass
parameter_list|(
name|int
name|minLimitPerClass
parameter_list|)
block|{
name|this
operator|.
name|minLimitPerClass
operator|=
name|minLimitPerClass
expr_stmt|;
block|}
comment|/**      * @return Returns the maxLimitPerClass.      */
specifier|public
name|int
name|getMaxLimitPerClass
parameter_list|()
block|{
return|return
name|maxLimitPerClass
return|;
block|}
comment|/**      * @param maxLimitPerClass The maxLimitPerClass to set.      */
specifier|public
name|void
name|setMaxLimitPerClass
parameter_list|(
name|int
name|maxLimitPerClass
parameter_list|)
block|{
name|this
operator|.
name|maxLimitPerClass
operator|=
name|maxLimitPerClass
expr_stmt|;
block|}
comment|/**      * @return Returns the minPerWeek.      */
specifier|public
name|int
name|getMinPerWeek
parameter_list|()
block|{
return|return
name|minPerWeek
return|;
block|}
comment|/**      * @param minPerWeek The minPerWeek to set.      */
specifier|public
name|void
name|setMinPerWeek
parameter_list|(
name|int
name|minPerWeek
parameter_list|)
block|{
name|this
operator|.
name|minPerWeek
operator|=
name|minPerWeek
expr_stmt|;
block|}
comment|/**      * @return Returns the numClasses.      */
specifier|public
name|int
name|getNumClasses
parameter_list|()
block|{
return|return
name|numClasses
return|;
block|}
comment|/**      * @param numClasses The numClasses to set.      */
specifier|public
name|void
name|setNumClasses
parameter_list|(
name|int
name|numClasses
parameter_list|)
block|{
name|this
operator|.
name|numClasses
operator|=
name|numClasses
expr_stmt|;
block|}
comment|/**      * @return Returns the id.      */
specifier|public
name|long
name|getId
parameter_list|()
block|{
return|return
name|id
return|;
block|}
comment|/**      * @return Returns the parent.      */
specifier|public
name|SimpleItypeConfig
name|getParent
parameter_list|()
block|{
return|return
name|parent
return|;
block|}
comment|/**      * @param parent The parent to set.      */
specifier|public
name|void
name|setParent
parameter_list|(
name|SimpleItypeConfig
name|parent
parameter_list|)
block|{
name|this
operator|.
name|parent
operator|=
name|parent
expr_stmt|;
block|}
comment|/**      * @return Returns the roomRatio.      */
specifier|public
name|float
name|getRoomRatio
parameter_list|()
block|{
return|return
name|roomRatio
return|;
block|}
comment|/**      * @param roomRatio The roomRatio to set.      */
specifier|public
name|void
name|setRoomRatio
parameter_list|(
name|float
name|roomRatio
parameter_list|)
block|{
name|this
operator|.
name|roomRatio
operator|=
name|roomRatio
expr_stmt|;
block|}
comment|/**      * @return Returns the disabled.      */
specifier|public
name|boolean
name|isDisabled
parameter_list|()
block|{
return|return
name|disabled
return|;
block|}
comment|/**      * @param disabled The disabled to set.      */
specifier|public
name|void
name|setDisabled
parameter_list|(
name|boolean
name|disabled
parameter_list|)
block|{
name|this
operator|.
name|disabled
operator|=
name|disabled
expr_stmt|;
block|}
comment|/**      * @return Returns the notOwned.      */
specifier|public
name|boolean
name|isNotOwned
parameter_list|()
block|{
return|return
name|notOwned
return|;
block|}
comment|/**      * @param notOwned The notOwned to set.      */
specifier|public
name|void
name|setNotOwned
parameter_list|(
name|boolean
name|notOwned
parameter_list|)
block|{
name|this
operator|.
name|notOwned
operator|=
name|notOwned
expr_stmt|;
block|}
specifier|public
name|long
name|getSubpartId
parameter_list|()
block|{
return|return
name|subpartId
return|;
block|}
specifier|public
name|void
name|setSubpartId
parameter_list|(
name|long
name|subpartId
parameter_list|)
block|{
name|this
operator|.
name|subpartId
operator|=
name|subpartId
expr_stmt|;
block|}
specifier|public
name|boolean
name|getHasError
parameter_list|()
block|{
return|return
name|hasError
return|;
block|}
specifier|public
name|void
name|setHasError
parameter_list|(
name|boolean
name|hasError
parameter_list|)
block|{
name|this
operator|.
name|hasError
operator|=
name|hasError
expr_stmt|;
block|}
specifier|public
name|int
name|getNumRooms
parameter_list|()
block|{
return|return
name|numRooms
return|;
block|}
specifier|public
name|void
name|setNumRooms
parameter_list|(
name|int
name|numRooms
parameter_list|)
block|{
name|this
operator|.
name|numRooms
operator|=
name|numRooms
expr_stmt|;
block|}
specifier|public
name|long
name|getManagingDeptId
parameter_list|()
block|{
return|return
name|managingDeptId
return|;
block|}
specifier|public
name|void
name|setManagingDeptId
parameter_list|(
name|long
name|managingDeptId
parameter_list|)
block|{
name|this
operator|.
name|managingDeptId
operator|=
name|managingDeptId
expr_stmt|;
block|}
block|}
end_class

end_unit

