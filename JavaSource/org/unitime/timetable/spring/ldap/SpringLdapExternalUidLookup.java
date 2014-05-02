begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.4 - 3.5 (University Timetabling Application)  * Copyright (C) 2012 - 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|Log
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|LogFactory
import|;
end_import

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
name|ContextSource
import|;
end_import

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
name|ldap
operator|.
name|SpringSecurityLdapTemplate
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

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|interfaces
operator|.
name|ExternalUidLookup
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
name|spring
operator|.
name|SpringApplicationContextHolder
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|SpringLdapExternalUidLookup
implements|implements
name|ExternalUidLookup
block|{
specifier|private
specifier|static
name|Log
name|sLog
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|SpringLdapExternalUidLookup
operator|.
name|class
argument_list|)
decl_stmt|;
annotation|@
name|Override
specifier|public
name|UserInfo
name|doLookup
parameter_list|(
name|String
name|uid
parameter_list|)
throws|throws
name|Exception
block|{
try|try
block|{
name|ContextSource
name|source
init|=
operator|(
name|ContextSource
operator|)
name|SpringApplicationContextHolder
operator|.
name|getBean
argument_list|(
literal|"unitimeLdapContextSource"
argument_list|)
decl_stmt|;
name|String
name|query
init|=
name|ApplicationProperty
operator|.
name|AuthenticationLdapIdentify
operator|.
name|value
argument_list|()
decl_stmt|;
name|String
name|idAttributeName
init|=
name|ApplicationProperty
operator|.
name|AuthenticationLdapIdAttribute
operator|.
name|value
argument_list|()
decl_stmt|;
name|SpringSecurityLdapTemplate
name|template
init|=
operator|new
name|SpringSecurityLdapTemplate
argument_list|(
name|source
argument_list|)
decl_stmt|;
name|DirContextOperations
name|user
init|=
name|template
operator|.
name|retrieveEntry
argument_list|(
name|query
operator|.
name|replaceAll
argument_list|(
literal|"\\{0\\}"
argument_list|,
name|uid
argument_list|)
argument_list|,
operator|new
name|String
index|[]
block|{
literal|"uid"
block|,
name|idAttributeName
block|,
literal|"cn"
block|,
literal|"givenName"
block|,
literal|"sn"
block|,
literal|"mail"
block|}
argument_list|)
decl_stmt|;
if|if
condition|(
name|user
operator|==
literal|null
operator|||
name|user
operator|.
name|getStringAttribute
argument_list|(
name|idAttributeName
argument_list|)
operator|==
literal|null
condition|)
return|return
literal|null
return|;
name|UserInfo
name|info
init|=
operator|new
name|UserInfo
argument_list|()
decl_stmt|;
name|info
operator|.
name|setExternalId
argument_list|(
name|user
operator|.
name|getStringAttribute
argument_list|(
name|idAttributeName
argument_list|)
argument_list|)
expr_stmt|;
name|info
operator|.
name|setUserName
argument_list|(
name|user
operator|.
name|getStringAttribute
argument_list|(
literal|"uid"
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|info
operator|.
name|getUserName
argument_list|()
operator|==
literal|null
condition|)
name|info
operator|.
name|setUserName
argument_list|(
name|uid
argument_list|)
expr_stmt|;
name|info
operator|.
name|setName
argument_list|(
name|user
operator|.
name|getStringAttribute
argument_list|(
literal|"cn"
argument_list|)
argument_list|)
expr_stmt|;
name|info
operator|.
name|setFirstName
argument_list|(
name|user
operator|.
name|getStringAttribute
argument_list|(
literal|"givenName"
argument_list|)
argument_list|)
expr_stmt|;
name|info
operator|.
name|setLastName
argument_list|(
name|user
operator|.
name|getStringAttribute
argument_list|(
literal|"sn"
argument_list|)
argument_list|)
expr_stmt|;
name|info
operator|.
name|setEmail
argument_list|(
name|user
operator|.
name|getStringAttribute
argument_list|(
literal|"mail"
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|info
operator|.
name|getEmail
argument_list|()
operator|==
literal|null
condition|)
block|{
name|String
name|email
init|=
name|info
operator|.
name|getUserName
argument_list|()
operator|+
literal|"@"
decl_stmt|;
for|for
control|(
name|String
name|x
range|:
name|user
operator|.
name|getNameInNamespace
argument_list|()
operator|.
name|split
argument_list|(
literal|","
argument_list|)
control|)
if|if
condition|(
name|x
operator|.
name|startsWith
argument_list|(
literal|"dc="
argument_list|)
condition|)
name|email
operator|+=
operator|(
name|email
operator|.
name|endsWith
argument_list|(
literal|"@"
argument_list|)
condition|?
literal|""
else|:
literal|"."
operator|)
operator|+
name|x
operator|.
name|substring
argument_list|(
literal|3
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|email
operator|.
name|endsWith
argument_list|(
literal|"@"
argument_list|)
condition|)
name|info
operator|.
name|setEmail
argument_list|(
name|email
argument_list|)
expr_stmt|;
block|}
return|return
name|info
return|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|sLog
operator|.
name|warn
argument_list|(
literal|"Lookup for "
operator|+
name|uid
operator|+
literal|" failed: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
literal|null
return|;
block|}
block|}
end_class

end_unit

