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
name|List
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
name|BaseAttachementType
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
name|AttachementTypeDAO
import|;
end_import

begin_class
specifier|public
class|class
name|AttachementType
extends|extends
name|BaseAttachementType
implements|implements
name|Comparable
argument_list|<
name|AttachementType
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
specifier|public
specifier|static
enum|enum
name|VisibilityFlag
block|{
name|IS_IMAGE
block|,
name|ROOM_PICTURE_TYPE
block|,
name|SHOW_ROOMS_TABLE
block|,
name|SHOW_ROOM_TOOLTIP
block|, 		;
specifier|public
name|int
name|flag
parameter_list|()
block|{
return|return
literal|1
operator|<<
name|ordinal
argument_list|()
return|;
block|}
specifier|public
name|boolean
name|in
parameter_list|(
name|int
name|flags
parameter_list|)
block|{
return|return
operator|(
name|flags
operator|&
name|flag
argument_list|()
operator|)
operator|!=
literal|0
return|;
block|}
specifier|public
name|int
name|set
parameter_list|(
name|int
name|flags
parameter_list|)
block|{
return|return
operator|(
name|in
argument_list|(
name|flags
argument_list|)
condition|?
name|flags
else|:
name|flags
operator|+
name|flag
argument_list|()
operator|)
return|;
block|}
specifier|public
name|int
name|clear
parameter_list|(
name|int
name|flags
parameter_list|)
block|{
return|return
operator|(
name|in
argument_list|(
name|flags
argument_list|)
condition|?
name|flags
operator|-
name|flag
argument_list|()
else|:
name|flags
operator|)
return|;
block|}
block|}
specifier|public
name|AttachementType
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|compareTo
parameter_list|(
name|AttachementType
name|a
parameter_list|)
block|{
name|int
name|cmp
init|=
name|getLabel
argument_list|()
operator|.
name|compareTo
argument_list|(
name|a
operator|.
name|getLabel
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
name|a
operator|.
name|getUniqueId
argument_list|()
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|List
argument_list|<
name|AttachementType
argument_list|>
name|listTypes
parameter_list|(
name|int
name|flag
parameter_list|)
block|{
if|if
condition|(
name|flag
operator|==
literal|0
condition|)
return|return
name|AttachementTypeDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"from AttachementType order by label"
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
return|;
else|else
return|return
name|AttachementTypeDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"from AttachementType where bit_and(visibility, :flag) = :flag order by label"
argument_list|)
operator|.
name|setInteger
argument_list|(
literal|"flag"
argument_list|,
name|flag
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
return|;
block|}
specifier|public
specifier|static
name|List
argument_list|<
name|AttachementType
argument_list|>
name|listTypes
parameter_list|(
name|VisibilityFlag
modifier|...
name|flags
parameter_list|)
block|{
name|int
name|flag
init|=
literal|0
decl_stmt|;
for|for
control|(
name|VisibilityFlag
name|f
range|:
name|flags
control|)
block|{
name|flag
operator|=
name|f
operator|.
name|set
argument_list|(
name|flag
argument_list|)
expr_stmt|;
block|}
return|return
name|listTypes
argument_list|(
name|flag
argument_list|)
return|;
block|}
block|}
end_class

end_unit

