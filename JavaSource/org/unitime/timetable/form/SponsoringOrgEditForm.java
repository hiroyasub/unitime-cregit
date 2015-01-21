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
name|form
package|;
end_package

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
name|javax
operator|.
name|servlet
operator|.
name|http
operator|.
name|HttpServletRequest
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionErrors
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionForm
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionMapping
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionMessage
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
name|SponsoringOrganization
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
name|SponsoringOrganizationDAO
import|;
end_import

begin_comment
comment|/**  * @author Zuzana Mullerova, Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|SponsoringOrgEditForm
extends|extends
name|ActionForm
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|5587107210035732698L
decl_stmt|;
specifier|private
name|String
name|iScreen
init|=
literal|"edit"
decl_stmt|;
specifier|private
name|String
name|iOrgName
decl_stmt|;
specifier|private
name|String
name|iOrgEmail
decl_stmt|;
specifier|private
name|String
name|iOp
decl_stmt|;
specifier|private
name|SponsoringOrganization
name|iOrg
decl_stmt|;
specifier|private
name|Long
name|iId
decl_stmt|;
specifier|public
name|ActionErrors
name|validate
parameter_list|(
name|ActionMapping
name|mapping
parameter_list|,
name|HttpServletRequest
name|request
parameter_list|)
block|{
name|ActionErrors
name|errors
init|=
operator|new
name|ActionErrors
argument_list|()
decl_stmt|;
if|if
condition|(
name|iOrgName
operator|==
literal|null
operator|||
name|iOrgName
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"orgName"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
literal|"Please enter the name of the organization."
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
for|for
control|(
name|Iterator
name|i
init|=
name|SponsoringOrganization
operator|.
name|findAll
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
name|SponsoringOrganization
name|so2
init|=
operator|(
name|SponsoringOrganization
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|iOrgName
operator|.
name|compareToIgnoreCase
argument_list|(
name|so2
operator|.
name|getName
argument_list|()
argument_list|)
operator|==
literal|0
condition|)
block|{
if|if
condition|(
name|iId
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|iId
operator|.
name|compareTo
argument_list|(
name|so2
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|!=
literal|0
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"orgNameExists"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
literal|"Another organization with this name already exists."
argument_list|)
argument_list|)
expr_stmt|;
break|break;
block|}
block|}
else|else
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"orgNameExists"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
literal|"Another organization with this name already exists."
argument_list|)
argument_list|)
expr_stmt|;
break|break;
block|}
block|}
block|}
block|}
return|return
name|errors
return|;
block|}
specifier|public
name|void
name|reset
parameter_list|(
name|ActionMapping
name|mapping
parameter_list|,
name|HttpServletRequest
name|request
parameter_list|)
block|{
if|if
condition|(
literal|"add"
operator|.
name|equals
argument_list|(
name|request
operator|.
name|getAttribute
argument_list|(
literal|"op"
argument_list|)
argument_list|)
condition|)
block|{
name|iScreen
operator|=
literal|"add"
expr_stmt|;
name|iOrg
operator|=
literal|null
expr_stmt|;
block|}
if|else if
condition|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"id"
argument_list|)
operator|!=
literal|null
operator|&&
name|request
operator|.
name|getParameter
argument_list|(
literal|"id"
argument_list|)
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|iId
operator|=
name|Long
operator|.
name|valueOf
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"id"
argument_list|)
argument_list|)
expr_stmt|;
name|iOrg
operator|=
name|SponsoringOrganizationDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|iId
argument_list|)
expr_stmt|;
block|}
name|iOrgName
operator|=
operator|(
name|iOrg
operator|==
literal|null
condition|?
literal|""
else|:
name|iOrg
operator|.
name|getName
argument_list|()
operator|)
expr_stmt|;
name|iOrgEmail
operator|=
operator|(
name|iOrg
operator|==
literal|null
condition|?
literal|""
else|:
name|iOrg
operator|.
name|getEmail
argument_list|()
operator|)
expr_stmt|;
block|}
specifier|public
name|String
name|getScreen
parameter_list|()
block|{
return|return
name|iScreen
return|;
block|}
specifier|public
name|void
name|setScreen
parameter_list|(
name|String
name|screen
parameter_list|)
block|{
name|iScreen
operator|=
name|screen
expr_stmt|;
block|}
specifier|public
name|String
name|getOrgName
parameter_list|()
block|{
return|return
name|iOrgName
return|;
block|}
specifier|public
name|void
name|setOrgName
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|iOrgName
operator|=
name|name
expr_stmt|;
block|}
specifier|public
name|String
name|getOrgEmail
parameter_list|()
block|{
return|return
name|iOrgEmail
return|;
block|}
specifier|public
name|void
name|setOrgEmail
parameter_list|(
name|String
name|email
parameter_list|)
block|{
name|iOrgEmail
operator|=
name|email
expr_stmt|;
block|}
specifier|public
name|String
name|getOp
parameter_list|()
block|{
return|return
name|iOp
return|;
block|}
specifier|public
name|void
name|setOp
parameter_list|(
name|String
name|op
parameter_list|)
block|{
name|iOp
operator|=
name|op
expr_stmt|;
block|}
specifier|public
name|Long
name|getId
parameter_list|()
block|{
return|return
name|iId
return|;
block|}
specifier|public
name|void
name|setId
parameter_list|(
name|Long
name|id
parameter_list|)
block|{
name|iId
operator|=
name|id
expr_stmt|;
block|}
specifier|public
name|SponsoringOrganization
name|getOrg
parameter_list|()
block|{
return|return
name|iOrg
return|;
block|}
specifier|public
name|void
name|setOrg
parameter_list|(
name|SponsoringOrganization
name|org
parameter_list|)
block|{
name|iOrg
operator|=
name|org
expr_stmt|;
block|}
block|}
end_class

end_unit

