begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 (University Timetabling Application)  * Copyright (C) 2010, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|util
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Hashtable
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|naming
operator|.
name|Context
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|naming
operator|.
name|NamingException
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|naming
operator|.
name|directory
operator|.
name|Attribute
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|naming
operator|.
name|directory
operator|.
name|Attributes
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|naming
operator|.
name|directory
operator|.
name|DirContext
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|naming
operator|.
name|directory
operator|.
name|InitialDirContext
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
name|ApplicationProperties
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

begin_class
annotation|@
name|Deprecated
comment|/**  * @deprecated Use {@link org.unitime.timetable.spring.ldap.SpringLdapExternalUidLookup} instead.  */
specifier|public
class|class
name|LdapExternalUidLookup
implements|implements
name|ExternalUidLookup
block|{
specifier|public
name|DirContext
name|getDirContext
parameter_list|()
throws|throws
name|NamingException
block|{
name|Hashtable
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|env
init|=
operator|new
name|Hashtable
argument_list|()
decl_stmt|;
name|env
operator|.
name|put
argument_list|(
name|Context
operator|.
name|INITIAL_CONTEXT_FACTORY
argument_list|,
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.authenticate.ldap.ctxFactory"
argument_list|,
literal|"com.sun.jndi.ldap.LdapCtxFactory"
argument_list|)
argument_list|)
expr_stmt|;
name|env
operator|.
name|put
argument_list|(
name|Context
operator|.
name|PROVIDER_URL
argument_list|,
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.authenticate.ldap.provider"
argument_list|)
argument_list|)
expr_stmt|;
name|env
operator|.
name|put
argument_list|(
name|Context
operator|.
name|REFERRAL
argument_list|,
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.authenticate.ldap.referral"
argument_list|,
literal|"ignore"
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.authenticate.ldap.version"
argument_list|)
operator|!=
literal|null
condition|)
name|env
operator|.
name|put
argument_list|(
literal|"java.naming.ldap.version"
argument_list|,
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.authenticate.ldap.version"
argument_list|)
argument_list|)
expr_stmt|;
name|env
operator|.
name|put
argument_list|(
name|Context
operator|.
name|SECURITY_AUTHENTICATION
argument_list|,
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.authenticate.ldap.security"
argument_list|,
literal|"simple"
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.authenticate.ldap.socketFactory"
argument_list|)
operator|!=
literal|null
condition|)
name|env
operator|.
name|put
argument_list|(
literal|"java.naming.ldap.factory.socket"
argument_list|,
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.authenticate.ldap.socketFactory"
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.authenticate.ldap.ssl.keyStore"
argument_list|)
operator|!=
literal|null
condition|)
name|System
operator|.
name|setProperty
argument_list|(
literal|"javax.net.ssl.keyStore"
argument_list|,
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.authenticate.ldap.ssl.keyStore"
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"%WEB-INF%"
argument_list|,
name|ApplicationProperties
operator|.
name|getBasePath
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.authenticate.ldap.ssl.trustStore"
argument_list|)
operator|!=
literal|null
condition|)
name|System
operator|.
name|setProperty
argument_list|(
literal|"javax.net.ssl.trustStore"
argument_list|,
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.authenticate.ldap.ssl.trustStore"
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"%WEB-INF%"
argument_list|,
name|ApplicationProperties
operator|.
name|getBasePath
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.authenticate.ldap.ssl.trustStorePassword"
argument_list|)
operator|!=
literal|null
condition|)
name|System
operator|.
name|setProperty
argument_list|(
literal|"javax.net.ssl.keyStorePassword"
argument_list|,
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.authenticate.ldap.ssl.keyStorePassword"
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.authenticate.ldap.ssl.trustStorePassword"
argument_list|)
operator|!=
literal|null
condition|)
name|System
operator|.
name|setProperty
argument_list|(
literal|"javax.net.ssl.trustStorePassword"
argument_list|,
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.authenticate.ldap.ssl.trustStorePassword"
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.authenticate.ldap.ssl.trustStoreType"
argument_list|)
operator|!=
literal|null
condition|)
name|System
operator|.
name|setProperty
argument_list|(
literal|"javax.net.ssl.trustStoreType"
argument_list|,
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.authenticate.ldap.ssl.trustStoreType"
argument_list|)
argument_list|)
expr_stmt|;
return|return
operator|new
name|InitialDirContext
argument_list|(
name|env
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|UserInfo
name|doLookup
parameter_list|(
name|String
name|searchId
parameter_list|)
throws|throws
name|Exception
block|{
name|String
name|query
init|=
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.authenticate.ldap.identify"
argument_list|)
decl_stmt|;
if|if
condition|(
name|query
operator|==
literal|null
condition|)
return|return
literal|null
return|;
name|DirContext
name|ctx
init|=
literal|null
decl_stmt|;
try|try
block|{
name|ctx
operator|=
name|getDirContext
argument_list|()
expr_stmt|;
name|String
name|idAttributeName
init|=
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.authenticate.ldap.externalId"
argument_list|,
literal|"uid"
argument_list|)
decl_stmt|;
name|String
name|loginAttributeName
init|=
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.authenticate.ldap.login"
argument_list|,
literal|"uid"
argument_list|)
decl_stmt|;
name|Attributes
name|attributes
init|=
name|ctx
operator|.
name|getAttributes
argument_list|(
name|query
operator|.
name|replaceAll
argument_list|(
literal|"%"
argument_list|,
name|searchId
argument_list|)
argument_list|,
operator|new
name|String
index|[]
block|{
name|idAttributeName
block|,
name|loginAttributeName
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
name|Attribute
name|idAttribute
init|=
name|attributes
operator|.
name|get
argument_list|(
name|idAttributeName
argument_list|)
decl_stmt|;
if|if
condition|(
name|idAttribute
operator|==
literal|null
condition|)
return|return
literal|null
return|;
name|UserInfo
name|user
init|=
operator|new
name|UserInfo
argument_list|()
decl_stmt|;
name|user
operator|.
name|setExternalId
argument_list|(
operator|(
name|String
operator|)
name|idAttribute
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
name|user
operator|.
name|setUserName
argument_list|(
operator|(
name|String
operator|)
name|attributes
operator|.
name|get
argument_list|(
name|loginAttributeName
argument_list|)
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|attributes
operator|.
name|get
argument_list|(
literal|"cn"
argument_list|)
operator|!=
literal|null
condition|)
name|user
operator|.
name|setName
argument_list|(
operator|(
name|String
operator|)
name|attributes
operator|.
name|get
argument_list|(
literal|"cn"
argument_list|)
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|attributes
operator|.
name|get
argument_list|(
literal|"givenName"
argument_list|)
operator|!=
literal|null
condition|)
name|user
operator|.
name|setFirstName
argument_list|(
operator|(
name|String
operator|)
name|attributes
operator|.
name|get
argument_list|(
literal|"givenName"
argument_list|)
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|attributes
operator|.
name|get
argument_list|(
literal|"cn"
argument_list|)
operator|!=
literal|null
condition|)
name|user
operator|.
name|setName
argument_list|(
operator|(
name|String
operator|)
name|attributes
operator|.
name|get
argument_list|(
literal|"cn"
argument_list|)
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|attributes
operator|.
name|get
argument_list|(
literal|"sn"
argument_list|)
operator|!=
literal|null
condition|)
name|user
operator|.
name|setLastName
argument_list|(
operator|(
name|String
operator|)
name|attributes
operator|.
name|get
argument_list|(
literal|"sn"
argument_list|)
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|attributes
operator|.
name|get
argument_list|(
literal|"mail"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|user
operator|.
name|setEmail
argument_list|(
operator|(
name|String
operator|)
name|attributes
operator|.
name|get
argument_list|(
literal|"mail"
argument_list|)
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|String
name|email
init|=
name|user
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
name|query
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
name|user
operator|.
name|setEmail
argument_list|(
name|email
argument_list|)
expr_stmt|;
block|}
return|return
name|user
return|;
block|}
finally|finally
block|{
if|if
condition|(
name|ctx
operator|!=
literal|null
condition|)
name|ctx
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

