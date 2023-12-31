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
name|action
package|;
end_package

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts2
operator|.
name|convention
operator|.
name|annotation
operator|.
name|Action
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts2
operator|.
name|convention
operator|.
name|annotation
operator|.
name|Result
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts2
operator|.
name|tiles
operator|.
name|annotation
operator|.
name|TilesDefinition
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts2
operator|.
name|tiles
operator|.
name|annotation
operator|.
name|TilesDefinitions
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts2
operator|.
name|tiles
operator|.
name|annotation
operator|.
name|TilesPutAttribute
import|;
end_import

begin_import
import|import
name|org
operator|.
name|hibernate
operator|.
name|Transaction
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|commons
operator|.
name|Debug
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
name|localization
operator|.
name|messages
operator|.
name|CourseMessages
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
name|form
operator|.
name|ItypeDescEditForm
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
name|ItypeDesc
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
name|ItypeDescDAO
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
name|rights
operator|.
name|Right
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
name|LookupTables
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|Action
argument_list|(
name|value
operator|=
literal|"itypeDescEdit"
argument_list|,
name|results
operator|=
block|{
annotation|@
name|Result
argument_list|(
name|name
operator|=
literal|"edit"
argument_list|,
name|type
operator|=
literal|"tiles"
argument_list|,
name|location
operator|=
literal|"itypeDescEdit.tiles"
argument_list|)
block|,
annotation|@
name|Result
argument_list|(
name|name
operator|=
literal|"add"
argument_list|,
name|type
operator|=
literal|"tiles"
argument_list|,
name|location
operator|=
literal|"itypeDescAdd.tiles"
argument_list|)
block|,
annotation|@
name|Result
argument_list|(
name|name
operator|=
literal|"back"
argument_list|,
name|type
operator|=
literal|"redirectAction"
argument_list|,
name|location
operator|=
literal|"itypeDescList"
argument_list|,
name|params
operator|=
block|{
literal|"id"
block|,
literal|"${form.id}"
block|}
argument_list|)
block|}
argument_list|)
annotation|@
name|TilesDefinitions
argument_list|(
name|value
operator|=
block|{
annotation|@
name|TilesDefinition
argument_list|(
name|name
operator|=
literal|"itypeDescEdit.tiles"
argument_list|,
name|extend
operator|=
literal|"baseLayout"
argument_list|,
name|putAttributes
operator|=
block|{
annotation|@
name|TilesPutAttribute
argument_list|(
name|name
operator|=
literal|"title"
argument_list|,
name|value
operator|=
literal|"Edit Instructional Type"
argument_list|)
block|,
annotation|@
name|TilesPutAttribute
argument_list|(
name|name
operator|=
literal|"body"
argument_list|,
name|value
operator|=
literal|"/admin/itypeDescEdit.jsp"
argument_list|)
block|}
argument_list|)
block|,
annotation|@
name|TilesDefinition
argument_list|(
name|name
operator|=
literal|"itypeDescAdd.tiles"
argument_list|,
name|extend
operator|=
literal|"baseLayout"
argument_list|,
name|putAttributes
operator|=
block|{
annotation|@
name|TilesPutAttribute
argument_list|(
name|name
operator|=
literal|"title"
argument_list|,
name|value
operator|=
literal|"Add Instructional Type"
argument_list|)
block|,
annotation|@
name|TilesPutAttribute
argument_list|(
name|name
operator|=
literal|"body"
argument_list|,
name|value
operator|=
literal|"/admin/itypeDescEdit.jsp"
argument_list|)
block|}
argument_list|)
block|}
argument_list|)
specifier|public
class|class
name|ItypeDescEditAction
extends|extends
name|UniTimeAction
argument_list|<
name|ItypeDescEditForm
argument_list|>
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
operator|-
literal|3956260021665092931L
decl_stmt|;
specifier|protected
specifier|static
name|CourseMessages
name|MSG
init|=
name|Localization
operator|.
name|create
argument_list|(
name|CourseMessages
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|protected
name|Integer
name|id
decl_stmt|;
specifier|public
name|Integer
name|getId
parameter_list|()
block|{
return|return
name|id
return|;
block|}
specifier|public
name|void
name|setId
parameter_list|(
name|Integer
name|id
parameter_list|)
block|{
name|this
operator|.
name|id
operator|=
name|id
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|execute
parameter_list|()
throws|throws
name|Exception
block|{
try|try
block|{
if|if
condition|(
name|form
operator|==
literal|null
condition|)
block|{
name|form
operator|=
operator|new
name|ItypeDescEditForm
argument_list|()
expr_stmt|;
block|}
comment|// Check Access
name|sessionContext
operator|.
name|checkPermission
argument_list|(
name|Right
operator|.
name|InstructionalTypes
argument_list|)
expr_stmt|;
comment|// Return
if|if
condition|(
name|MSG
operator|.
name|actionBackITypes
argument_list|()
operator|.
name|equals
argument_list|(
name|op
argument_list|)
condition|)
block|{
return|return
literal|"back"
return|;
block|}
if|if
condition|(
name|MSG
operator|.
name|actionAddIType
argument_list|()
operator|.
name|equals
argument_list|(
name|op
argument_list|)
condition|)
block|{
name|sessionContext
operator|.
name|checkPermission
argument_list|(
name|Right
operator|.
name|InstructionalTypeAdd
argument_list|)
expr_stmt|;
block|}
name|LookupTables
operator|.
name|setupItypes
argument_list|(
name|request
argument_list|,
literal|true
argument_list|)
expr_stmt|;
comment|// Add / Update
if|if
condition|(
name|MSG
operator|.
name|actionUpdateIType
argument_list|()
operator|.
name|equals
argument_list|(
name|op
argument_list|)
operator|||
name|MSG
operator|.
name|actionSaveIType
argument_list|()
operator|.
name|equals
argument_list|(
name|op
argument_list|)
condition|)
block|{
comment|// Validate input
name|form
operator|.
name|validate
argument_list|(
name|this
argument_list|)
expr_stmt|;
if|if
condition|(
name|hasFieldErrors
argument_list|()
condition|)
block|{
return|return
name|MSG
operator|.
name|actionSaveIType
argument_list|()
operator|.
name|equals
argument_list|(
name|op
argument_list|)
condition|?
literal|"add"
else|:
literal|"edit"
return|;
block|}
else|else
block|{
name|Transaction
name|tx
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|form
operator|.
name|getUniqueId
argument_list|()
operator|==
literal|null
operator|||
name|form
operator|.
name|getUniqueId
argument_list|()
operator|<
literal|0
condition|)
name|sessionContext
operator|.
name|checkPermission
argument_list|(
name|Right
operator|.
name|InstructionalTypeAdd
argument_list|)
expr_stmt|;
else|else
name|sessionContext
operator|.
name|checkPermission
argument_list|(
name|form
operator|.
name|getUniqueId
argument_list|()
argument_list|,
literal|"ItypeDesc"
argument_list|,
name|Right
operator|.
name|InstructionalTypeEdit
argument_list|)
expr_stmt|;
try|try
block|{
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
operator|(
operator|new
name|ItypeDescDAO
argument_list|()
operator|)
operator|.
name|getSession
argument_list|()
decl_stmt|;
if|if
condition|(
name|hibSession
operator|.
name|getTransaction
argument_list|()
operator|==
literal|null
operator|||
operator|!
name|hibSession
operator|.
name|getTransaction
argument_list|()
operator|.
name|isActive
argument_list|()
condition|)
name|tx
operator|=
name|hibSession
operator|.
name|beginTransaction
argument_list|()
expr_stmt|;
name|form
operator|.
name|saveOrUpdate
argument_list|(
name|hibSession
argument_list|)
expr_stmt|;
if|if
condition|(
name|tx
operator|!=
literal|null
condition|)
name|tx
operator|.
name|commit
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
if|if
condition|(
name|tx
operator|!=
literal|null
condition|)
name|tx
operator|.
name|rollback
argument_list|()
expr_stmt|;
throw|throw
name|e
throw|;
block|}
return|return
literal|"back"
return|;
block|}
block|}
comment|// Edit
if|if
condition|(
name|op
operator|==
literal|null
condition|)
block|{
name|sessionContext
operator|.
name|checkPermission
argument_list|(
name|Integer
operator|.
name|valueOf
argument_list|(
name|id
argument_list|)
argument_list|,
literal|"ItypeDesc"
argument_list|,
name|Right
operator|.
name|InstructionalTypeEdit
argument_list|)
expr_stmt|;
name|setOp
argument_list|(
name|MSG
operator|.
name|actionUpdateIType
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|id
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|Exception
argument_list|(
name|MSG
operator|.
name|errorRequiredField
argument_list|(
name|MSG
operator|.
name|fieldIType
argument_list|()
argument_list|)
argument_list|)
throw|;
block|}
else|else
block|{
name|ItypeDesc
name|itype
init|=
operator|new
name|ItypeDescDAO
argument_list|()
operator|.
name|get
argument_list|(
name|Integer
operator|.
name|valueOf
argument_list|(
name|id
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|itype
operator|==
literal|null
condition|)
block|{
return|return
literal|"back"
return|;
block|}
else|else
block|{
name|form
operator|.
name|load
argument_list|(
name|itype
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|// Delete
if|if
condition|(
name|MSG
operator|.
name|actionDeleteIType
argument_list|()
operator|.
name|equals
argument_list|(
name|op
argument_list|)
condition|)
block|{
name|Transaction
name|tx
init|=
literal|null
decl_stmt|;
name|sessionContext
operator|.
name|checkPermission
argument_list|(
name|form
operator|.
name|getUniqueId
argument_list|()
argument_list|,
literal|"ItypeDesc"
argument_list|,
name|Right
operator|.
name|InstructionalTypeDelete
argument_list|)
expr_stmt|;
try|try
block|{
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
operator|(
operator|new
name|ItypeDescDAO
argument_list|()
operator|)
operator|.
name|getSession
argument_list|()
decl_stmt|;
if|if
condition|(
name|hibSession
operator|.
name|getTransaction
argument_list|()
operator|==
literal|null
operator|||
operator|!
name|hibSession
operator|.
name|getTransaction
argument_list|()
operator|.
name|isActive
argument_list|()
condition|)
name|tx
operator|=
name|hibSession
operator|.
name|beginTransaction
argument_list|()
expr_stmt|;
name|form
operator|.
name|delete
argument_list|(
name|hibSession
argument_list|)
expr_stmt|;
name|tx
operator|.
name|commit
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
if|if
condition|(
name|tx
operator|!=
literal|null
condition|)
name|tx
operator|.
name|rollback
argument_list|()
expr_stmt|;
throw|throw
name|e
throw|;
block|}
return|return
literal|"back"
return|;
block|}
if|if
condition|(
name|MSG
operator|.
name|actionAddIType
argument_list|()
operator|.
name|equals
argument_list|(
name|op
argument_list|)
condition|)
block|{
name|setOp
argument_list|(
name|MSG
operator|.
name|actionSaveIType
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
operator|(
name|MSG
operator|.
name|actionSaveIType
argument_list|()
operator|.
name|equals
argument_list|(
name|getOp
argument_list|()
argument_list|)
condition|?
literal|"add"
else|:
literal|"edit"
operator|)
return|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|Debug
operator|.
name|error
argument_list|(
name|e
argument_list|)
expr_stmt|;
throw|throw
name|e
throw|;
block|}
block|}
block|}
end_class

end_unit

