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
name|gwt
operator|.
name|shared
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Date
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
name|timetable
operator|.
name|gwt
operator|.
name|command
operator|.
name|client
operator|.
name|GwtRpcRequest
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
name|command
operator|.
name|client
operator|.
name|GwtRpcResponseList
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|gwt
operator|.
name|user
operator|.
name|client
operator|.
name|rpc
operator|.
name|IsSerializable
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|PublishedSectioningSolutionInterface
implements|implements
name|IsSerializable
block|{
specifier|private
name|Long
name|iUniqueId
decl_stmt|;
specifier|private
name|Date
name|iTimeStamp
decl_stmt|;
specifier|private
name|String
name|iOwner
decl_stmt|;
specifier|private
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|iInfo
decl_stmt|;
specifier|private
name|boolean
name|iLoaded
decl_stmt|,
name|iClonned
decl_stmt|,
name|iSelected
decl_stmt|;
specifier|private
name|boolean
name|iCanSelect
decl_stmt|,
name|iCanClone
decl_stmt|,
name|iCanLoad
decl_stmt|,
name|iCanChangeNote
decl_stmt|;
specifier|private
name|String
name|iConfig
decl_stmt|,
name|iNote
decl_stmt|;
specifier|public
name|PublishedSectioningSolutionInterface
parameter_list|()
block|{
block|}
specifier|public
name|Long
name|getUniqueId
parameter_list|()
block|{
return|return
name|iUniqueId
return|;
block|}
specifier|public
name|void
name|setUniqueId
parameter_list|(
name|Long
name|uniqueId
parameter_list|)
block|{
name|iUniqueId
operator|=
name|uniqueId
expr_stmt|;
block|}
specifier|public
name|Date
name|getTimeStamp
parameter_list|()
block|{
return|return
name|iTimeStamp
return|;
block|}
specifier|public
name|void
name|setTimeStamp
parameter_list|(
name|Date
name|ts
parameter_list|)
block|{
name|iTimeStamp
operator|=
name|ts
expr_stmt|;
block|}
specifier|public
name|String
name|getOwner
parameter_list|()
block|{
return|return
name|iOwner
return|;
block|}
specifier|public
name|void
name|setOwner
parameter_list|(
name|String
name|owner
parameter_list|)
block|{
name|iOwner
operator|=
name|owner
expr_stmt|;
block|}
specifier|public
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|getInfo
parameter_list|()
block|{
return|return
name|iInfo
return|;
block|}
specifier|public
name|void
name|setInfo
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|info
parameter_list|)
block|{
name|iInfo
operator|=
name|info
expr_stmt|;
block|}
specifier|public
name|boolean
name|isLoaded
parameter_list|()
block|{
return|return
name|iLoaded
return|;
block|}
specifier|public
name|void
name|setLoaded
parameter_list|(
name|boolean
name|loaded
parameter_list|)
block|{
name|iLoaded
operator|=
name|loaded
expr_stmt|;
block|}
specifier|public
name|boolean
name|isClonned
parameter_list|()
block|{
return|return
name|iClonned
return|;
block|}
specifier|public
name|void
name|setClonned
parameter_list|(
name|boolean
name|clonned
parameter_list|)
block|{
name|iClonned
operator|=
name|clonned
expr_stmt|;
block|}
specifier|public
name|boolean
name|isSelected
parameter_list|()
block|{
return|return
name|iSelected
return|;
block|}
specifier|public
name|void
name|setSelected
parameter_list|(
name|boolean
name|selected
parameter_list|)
block|{
name|iSelected
operator|=
name|selected
expr_stmt|;
block|}
specifier|public
name|boolean
name|isCanLoad
parameter_list|()
block|{
return|return
name|iCanLoad
return|;
block|}
specifier|public
name|void
name|setCanLoad
parameter_list|(
name|boolean
name|canLoad
parameter_list|)
block|{
name|iCanLoad
operator|=
name|canLoad
expr_stmt|;
block|}
specifier|public
name|boolean
name|isCanSelect
parameter_list|()
block|{
return|return
name|iCanSelect
return|;
block|}
specifier|public
name|void
name|setCanSelect
parameter_list|(
name|boolean
name|canSelect
parameter_list|)
block|{
name|iCanSelect
operator|=
name|canSelect
expr_stmt|;
block|}
specifier|public
name|boolean
name|isCanClone
parameter_list|()
block|{
return|return
name|iCanClone
return|;
block|}
specifier|public
name|void
name|setCanClone
parameter_list|(
name|boolean
name|canClone
parameter_list|)
block|{
name|iCanClone
operator|=
name|canClone
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasConfig
parameter_list|()
block|{
return|return
name|iConfig
operator|!=
literal|null
operator|&&
operator|!
name|iConfig
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|String
name|getConfig
parameter_list|()
block|{
return|return
name|iConfig
return|;
block|}
specifier|public
name|void
name|setConfig
parameter_list|(
name|String
name|config
parameter_list|)
block|{
name|iConfig
operator|=
name|config
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasNote
parameter_list|()
block|{
return|return
name|iNote
operator|!=
literal|null
operator|&&
operator|!
name|iNote
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|String
name|getNote
parameter_list|()
block|{
return|return
name|iNote
return|;
block|}
specifier|public
name|void
name|setNote
parameter_list|(
name|String
name|note
parameter_list|)
block|{
name|iNote
operator|=
name|note
expr_stmt|;
block|}
specifier|public
name|boolean
name|isCanChangeNote
parameter_list|()
block|{
return|return
name|iCanChangeNote
return|;
block|}
specifier|public
name|void
name|setCanChangeNote
parameter_list|(
name|boolean
name|canChangeNote
parameter_list|)
block|{
name|iCanChangeNote
operator|=
name|canChangeNote
expr_stmt|;
block|}
specifier|public
name|String
name|getValue
parameter_list|(
name|String
name|attribute
parameter_list|)
block|{
if|if
condition|(
name|iInfo
operator|==
literal|null
condition|)
return|return
literal|""
return|;
name|String
name|value
init|=
name|iInfo
operator|.
name|get
argument_list|(
name|attribute
argument_list|)
decl_stmt|;
return|return
operator|(
name|value
operator|==
literal|null
condition|?
literal|""
else|:
name|value
operator|.
name|replace
argument_list|(
literal|" ("
argument_list|,
literal|"\n("
argument_list|)
operator|)
return|;
block|}
specifier|public
specifier|static
enum|enum
name|Operation
implements|implements
name|IsSerializable
block|{
name|LIST
block|,
name|REMOVE
block|,
name|LOAD
block|,
name|UNLOAD
block|,
name|PUBLISH
block|,
name|UNPUBLISH
block|,
name|SELECT
block|,
name|DESELECT
block|,
name|NOTE
block|, 	}
specifier|public
specifier|static
class|class
name|PublishedSectioningSolutionsRequest
implements|implements
name|GwtRpcRequest
argument_list|<
name|GwtRpcResponseList
argument_list|<
name|PublishedSectioningSolutionInterface
argument_list|>
argument_list|>
block|{
specifier|private
name|Operation
name|iOperation
decl_stmt|;
specifier|private
name|Long
name|iUniqueId
decl_stmt|;
specifier|private
name|String
name|iNote
decl_stmt|;
specifier|public
name|PublishedSectioningSolutionsRequest
parameter_list|()
block|{
block|}
specifier|public
name|PublishedSectioningSolutionsRequest
parameter_list|(
name|Operation
name|operation
parameter_list|)
block|{
name|iOperation
operator|=
name|operation
expr_stmt|;
block|}
specifier|public
name|PublishedSectioningSolutionsRequest
parameter_list|(
name|Operation
name|operation
parameter_list|,
name|Long
name|uniqueId
parameter_list|)
block|{
name|iOperation
operator|=
name|operation
expr_stmt|;
name|iUniqueId
operator|=
name|uniqueId
expr_stmt|;
block|}
specifier|public
name|PublishedSectioningSolutionsRequest
parameter_list|(
name|Long
name|uniqueId
parameter_list|,
name|String
name|note
parameter_list|)
block|{
name|iOperation
operator|=
name|Operation
operator|.
name|NOTE
expr_stmt|;
name|iUniqueId
operator|=
name|uniqueId
expr_stmt|;
name|iNote
operator|=
name|note
expr_stmt|;
block|}
specifier|public
name|Long
name|getUniqueId
parameter_list|()
block|{
return|return
name|iUniqueId
return|;
block|}
specifier|public
name|void
name|setUniqueId
parameter_list|(
name|Long
name|uniqueId
parameter_list|)
block|{
name|iUniqueId
operator|=
name|uniqueId
expr_stmt|;
block|}
specifier|public
name|Operation
name|getOperation
parameter_list|()
block|{
return|return
name|iOperation
return|;
block|}
specifier|public
name|void
name|setOperation
parameter_list|(
name|Operation
name|operation
parameter_list|)
block|{
name|iOperation
operator|=
name|operation
expr_stmt|;
block|}
specifier|public
name|void
name|setNote
parameter_list|(
name|String
name|note
parameter_list|)
block|{
name|iNote
operator|=
name|note
expr_stmt|;
block|}
specifier|public
name|String
name|getNote
parameter_list|()
block|{
return|return
name|iNote
return|;
block|}
block|}
specifier|public
specifier|static
enum|enum
name|TableColumn
block|{
name|DATE_TIME
block|,
name|OWNER
block|,
name|CONFIG
block|,
name|COURSE_REQUESTS
argument_list|(
literal|"Assigned course requests"
argument_list|)
block|,
name|PRIORITY_REQUESTS
argument_list|(
literal|"Assigned priority course requests"
argument_list|)
block|,
name|CRITICAL
argument_list|(
literal|"Assigned critical course requests"
argument_list|)
block|,
name|IMPORTANT
argument_list|(
literal|"Assigned important course requests"
argument_list|)
block|,
name|COMPLETE
argument_list|(
literal|"Students with complete schedule"
argument_list|)
block|,
name|SELECTION
argument_list|(
literal|"Selection"
argument_list|)
block|,
name|DISTANCE
argument_list|(
literal|"Student distance conflicts"
argument_list|)
block|,
name|TIME
argument_list|(
literal|"Time overlapping conflicts"
argument_list|)
block|,
name|DISBALANCED
argument_list|(
literal|"Sections disbalanced by 10% or more"
argument_list|)
block|,
name|NO_TIME
argument_list|(
literal|"Using classes w/o time"
argument_list|)
block|,
name|NOTE
block|,
name|OPERATIONS
block|, 		;
name|String
name|iProperty
decl_stmt|;
name|TableColumn
parameter_list|()
block|{
block|}
name|TableColumn
parameter_list|(
name|String
name|property
parameter_list|)
block|{
name|iProperty
operator|=
name|property
expr_stmt|;
block|}
specifier|public
name|String
name|getAttribute
parameter_list|()
block|{
return|return
name|iProperty
return|;
block|}
block|}
block|}
end_class

end_unit

