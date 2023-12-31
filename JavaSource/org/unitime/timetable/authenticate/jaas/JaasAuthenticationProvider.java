begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to The Apereo Foundation under one or more contributor license  * agreements. See the NOTICE file distributed with this work for  * additional information regarding copyright ownership.  *  * The Apereo Foundation licenses this file to you under the Apache License,  * Version 2.0 (the "License"); you may not use this file except in  * compliance with the License. You may obtain a copy of the License at:  *  * http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *  * See the License for the specific language governing permissions and  * limitations under the License.  *   */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|authenticate
operator|.
name|jaas
package|;
end_package

begin_import
import|import
name|java
operator|.
name|security
operator|.
name|Principal
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
name|HashSet
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
name|springframework
operator|.
name|security
operator|.
name|authentication
operator|.
name|jaas
operator|.
name|AuthorityGranter
import|;
end_import

begin_import
import|import
name|org
operator|.
name|springframework
operator|.
name|security
operator|.
name|authentication
operator|.
name|jaas
operator|.
name|DefaultJaasAuthenticationProvider
import|;
end_import

begin_import
import|import
name|org
operator|.
name|springframework
operator|.
name|security
operator|.
name|authentication
operator|.
name|jaas
operator|.
name|JaasAuthenticationToken
import|;
end_import

begin_import
import|import
name|org
operator|.
name|springframework
operator|.
name|security
operator|.
name|core
operator|.
name|Authentication
import|;
end_import

begin_import
import|import
name|org
operator|.
name|springframework
operator|.
name|security
operator|.
name|core
operator|.
name|AuthenticationException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|springframework
operator|.
name|security
operator|.
name|core
operator|.
name|GrantedAuthority
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
name|context
operator|.
name|UniTimeUserContext
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|JaasAuthenticationProvider
extends|extends
name|DefaultJaasAuthenticationProvider
block|{
specifier|public
name|JaasAuthenticationProvider
parameter_list|()
block|{
name|setAuthorityGranters
argument_list|(
operator|new
name|AuthorityGranter
index|[]
block|{
operator|new
name|AuthorityGranter
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|Set
argument_list|<
name|String
argument_list|>
name|grant
parameter_list|(
name|Principal
name|principal
parameter_list|)
block|{
name|Set
argument_list|<
name|String
argument_list|>
name|roles
init|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
if|if
condition|(
name|principal
operator|instanceof
name|HasExternalId
condition|)
block|{
name|roles
operator|.
name|add
argument_list|(
operator|(
operator|(
name|HasExternalId
operator|)
name|principal
operator|)
operator|.
name|getExternalId
argument_list|()
argument_list|)
expr_stmt|;
block_content|}
block|else
block|{
name|String
name|user
init|=
name|principal
operator|.
name|getName
argument_list|()
decl_stmt|;
if|if
condition|(
name|user
operator|.
name|indexOf
argument_list|(
literal|'@'
argument_list|)
operator|>=
literal|0
condition|)
name|user
operator|=
name|user
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|user
operator|.
name|indexOf
argument_list|(
literal|'@'
argument_list|)
argument_list|)
expr_stmt|;
name|roles
operator|.
name|add
argument_list|(
name|user
argument_list|)
expr_stmt|;
block_content|}
block|return roles
empty_stmt|;
block_content|}
block|} 		}
block|)
empty_stmt|;
block|}
specifier|public
name|Authentication
name|authenticate
parameter_list|(
name|Authentication
name|auth
parameter_list|)
throws|throws
name|AuthenticationException
block|{
name|JaasAuthenticationToken
name|ret
init|=
operator|(
name|JaasAuthenticationToken
operator|)
name|super
operator|.
name|authenticate
argument_list|(
name|auth
argument_list|)
decl_stmt|;
for|for
control|(
name|GrantedAuthority
name|role
range|:
name|ret
operator|.
name|getAuthorities
argument_list|()
control|)
block|{
name|UniTimeUserContext
name|user
init|=
operator|new
name|UniTimeUserContext
argument_list|(
name|role
operator|.
name|getAuthority
argument_list|()
argument_list|,
name|ret
operator|.
name|getName
argument_list|()
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|)
decl_stmt|;
return|return
operator|new
name|JaasAuthenticationToken
argument_list|(
name|user
argument_list|,
name|ret
operator|.
name|getCredentials
argument_list|()
argument_list|,
operator|new
name|ArrayList
argument_list|<
name|GrantedAuthority
argument_list|>
argument_list|(
name|user
operator|.
name|getAuthorities
argument_list|()
argument_list|)
argument_list|,
name|ret
operator|.
name|getLoginContext
argument_list|()
argument_list|)
return|;
block|}
return|return
literal|null
return|;
block|}
block|}
end_class

end_unit

