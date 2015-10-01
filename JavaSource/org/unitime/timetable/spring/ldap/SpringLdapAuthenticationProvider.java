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
name|spring
operator|.
name|ldap
package|;
end_package

begin_import
import|import
name|org
operator|.
name|springframework
operator|.
name|ldap
operator|.
name|core
operator|.
name|DirContextOperations
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
name|BadCredentialsException
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
name|UsernamePasswordAuthenticationToken
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
name|ldap
operator|.
name|authentication
operator|.
name|LdapAuthenticationProvider
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
name|ldap
operator|.
name|authentication
operator|.
name|LdapAuthenticator
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
name|ldap
operator|.
name|userdetails
operator|.
name|LdapAuthoritiesPopulator
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
name|ApplicationProperty
import|;
end_import

begin_class
specifier|public
class|class
name|SpringLdapAuthenticationProvider
extends|extends
name|LdapAuthenticationProvider
block|{
specifier|public
name|SpringLdapAuthenticationProvider
parameter_list|(
name|LdapAuthenticator
name|authenticator
parameter_list|)
block|{
name|super
argument_list|(
name|authenticator
argument_list|)
expr_stmt|;
block|}
specifier|public
name|SpringLdapAuthenticationProvider
parameter_list|(
name|LdapAuthenticator
name|authenticator
parameter_list|,
name|LdapAuthoritiesPopulator
name|authoritiesPopulator
parameter_list|)
block|{
name|super
argument_list|(
name|authenticator
argument_list|,
name|authoritiesPopulator
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|protected
name|DirContextOperations
name|doAuthentication
parameter_list|(
name|UsernamePasswordAuthenticationToken
name|authentication
parameter_list|)
block|{
if|if
condition|(
name|ApplicationProperty
operator|.
name|AuthenticationLdapUrl
operator|.
name|defaultValue
argument_list|()
operator|.
name|equals
argument_list|(
name|ApplicationProperty
operator|.
name|AuthenticationLdapUrl
operator|.
name|value
argument_list|()
argument_list|)
condition|)
throw|throw
operator|new
name|BadCredentialsException
argument_list|(
literal|"LDAP authentication is not configured."
argument_list|)
throw|;
return|return
name|super
operator|.
name|doAuthentication
argument_list|(
name|authentication
argument_list|)
return|;
block|}
block|}
end_class

end_unit
