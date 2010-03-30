begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.1 (University Timetabling Application)  * Copyright (C) 2008, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program; if not, write to the Free Software Foundation, Inc.,  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|text
operator|.
name|DecimalFormat
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
name|Hashtable
import|;
end_import

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
name|Vector
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
name|InitialDirContext
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|security
operator|.
name|auth
operator|.
name|Subject
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|security
operator|.
name|auth
operator|.
name|callback
operator|.
name|Callback
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|security
operator|.
name|auth
operator|.
name|callback
operator|.
name|CallbackHandler
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|security
operator|.
name|auth
operator|.
name|callback
operator|.
name|NameCallback
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|security
operator|.
name|auth
operator|.
name|callback
operator|.
name|PasswordCallback
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|security
operator|.
name|auth
operator|.
name|login
operator|.
name|LoginException
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
name|model
operator|.
name|Department
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
name|ManagerRole
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
name|TimetableManager
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|LdapAuthenticateModule
extends|extends
name|AuthenticateModule
block|{
specifier|private
name|String
name|iExternalUid
decl_stmt|;
comment|/** 	 * Abort authentication (when overall authentication fails) 	 */
specifier|public
name|boolean
name|abort
parameter_list|()
throws|throws
name|LoginException
block|{
if|if
condition|(
operator|!
name|isAuthSucceeded
argument_list|()
condition|)
return|return
literal|false
return|;
if|if
condition|(
name|isAuthSucceeded
argument_list|()
operator|&&
operator|!
name|isCommitSucceeded
argument_list|()
condition|)
name|reset
argument_list|()
expr_stmt|;
else|else
name|logout
argument_list|()
expr_stmt|;
return|return
literal|true
return|;
block|}
comment|/** 	 * Commit phase of login 	 */
specifier|public
name|boolean
name|commit
parameter_list|()
throws|throws
name|LoginException
block|{
if|if
condition|(
name|isAuthSucceeded
argument_list|()
condition|)
block|{
comment|// Check if authentication succeeded
comment|// External UID must exist in order to get manager info
if|if
condition|(
name|iExternalUid
operator|==
literal|null
operator|||
name|iExternalUid
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
throw|throw
operator|new
name|LoginException
argument_list|(
literal|"External UID not found"
argument_list|)
throw|;
name|org
operator|.
name|unitime
operator|.
name|commons
operator|.
name|User
name|p
init|=
operator|new
name|org
operator|.
name|unitime
operator|.
name|commons
operator|.
name|User
argument_list|()
decl_stmt|;
name|p
operator|.
name|setLogin
argument_list|(
name|getUser
argument_list|()
argument_list|)
expr_stmt|;
name|p
operator|.
name|setId
argument_list|(
name|iExternalUid
argument_list|)
expr_stmt|;
name|p
operator|.
name|setName
argument_list|(
name|getUser
argument_list|()
argument_list|)
expr_stmt|;
name|p
operator|.
name|setAdmin
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|p
operator|.
name|setRoles
argument_list|(
operator|new
name|Vector
argument_list|()
argument_list|)
expr_stmt|;
name|p
operator|.
name|setDepartments
argument_list|(
operator|new
name|Vector
argument_list|()
argument_list|)
expr_stmt|;
name|TimetableManager
name|manager
init|=
name|TimetableManager
operator|.
name|findByExternalId
argument_list|(
name|iExternalUid
argument_list|)
decl_stmt|;
if|if
condition|(
name|manager
operator|!=
literal|null
condition|)
block|{
name|p
operator|.
name|setName
argument_list|(
name|manager
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
comment|// Get roles
for|for
control|(
name|Iterator
name|i
init|=
name|manager
operator|.
name|getManagerRoles
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
name|ManagerRole
name|role
init|=
operator|(
name|ManagerRole
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|p
operator|.
name|getRoles
argument_list|()
operator|.
name|add
argument_list|(
name|role
operator|.
name|getRole
argument_list|()
operator|.
name|getReference
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|// Get departments
for|for
control|(
name|Iterator
name|i
init|=
name|manager
operator|.
name|getDepartments
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
name|Department
name|dept
init|=
operator|(
name|Department
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|p
operator|.
name|getDepartments
argument_list|()
operator|.
name|add
argument_list|(
name|dept
operator|.
name|getDeptCode
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
comment|// Check at least one role is found
if|if
condition|(
name|p
operator|.
name|getRoles
argument_list|()
operator|.
name|isEmpty
argument_list|()
operator|&&
operator|!
literal|"true"
operator|.
name|equals
argument_list|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.authentication.norole"
argument_list|,
literal|"false"
argument_list|)
argument_list|)
condition|)
block|{
if|if
condition|(
name|isDebug
argument_list|()
condition|)
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Role not found. Access Denied to User: "
operator|+
name|getUser
argument_list|()
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|LoginException
argument_list|(
literal|"Role not found. Access Denied to User: "
operator|+
name|getUser
argument_list|()
argument_list|)
throw|;
block|}
comment|// Create user principal
if|if
condition|(
name|isDebug
argument_list|()
condition|)
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"User Roles: "
operator|+
name|p
operator|.
name|getRoles
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|isDebug
argument_list|()
condition|)
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"User Depts: "
operator|+
name|p
operator|.
name|getDepartments
argument_list|()
argument_list|)
expr_stmt|;
comment|// Add user object to subjects public credentials
name|getSubject
argument_list|()
operator|.
name|getPublicCredentials
argument_list|()
operator|.
name|add
argument_list|(
name|p
argument_list|)
expr_stmt|;
name|setCommitSucceeded
argument_list|(
literal|true
argument_list|)
expr_stmt|;
return|return
literal|true
return|;
block|}
else|else
block|{
comment|// Authentication failed - do not commit
name|reset
argument_list|()
expr_stmt|;
return|return
literal|false
return|;
block|}
block|}
comment|/** 	 * Initialize 	 */
specifier|public
name|void
name|initialize
parameter_list|(
name|Subject
name|subject
parameter_list|,
name|CallbackHandler
name|callbackHandler
parameter_list|,
name|Map
name|sharedState
parameter_list|,
name|Map
name|options
parameter_list|)
block|{
name|super
operator|.
name|initialize
argument_list|(
name|subject
argument_list|,
name|callbackHandler
argument_list|,
name|sharedState
argument_list|,
name|options
argument_list|)
expr_stmt|;
name|iExternalUid
operator|=
literal|null
expr_stmt|;
block|}
comment|/** 	 * Authenticate the user 	 */
specifier|public
name|boolean
name|login
parameter_list|()
throws|throws
name|LoginException
block|{
comment|// Skip this module when LDAP provider is not set
if|if
condition|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.authenticate.ldap.provider"
argument_list|)
operator|==
literal|null
condition|)
return|return
literal|false
return|;
if|if
condition|(
name|isDebug
argument_list|()
condition|)
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Performing ldap authentication ... "
argument_list|)
expr_stmt|;
comment|// Get callback parameters
if|if
condition|(
name|getCallbackHandler
argument_list|()
operator|==
literal|null
condition|)
throw|throw
operator|new
name|LoginException
argument_list|(
literal|"Error: no CallbackHandler available "
argument_list|)
throw|;
name|Callback
index|[]
name|callbacks
init|=
operator|new
name|Callback
index|[
literal|2
index|]
decl_stmt|;
name|callbacks
index|[
literal|0
index|]
operator|=
operator|new
name|NameCallback
argument_list|(
literal|"User Name: "
argument_list|)
expr_stmt|;
name|callbacks
index|[
literal|1
index|]
operator|=
operator|new
name|PasswordCallback
argument_list|(
literal|"Password: "
argument_list|,
literal|true
argument_list|)
expr_stmt|;
try|try
block|{
name|getCallbackHandler
argument_list|()
operator|.
name|handle
argument_list|(
name|callbacks
argument_list|)
expr_stmt|;
name|String
name|n
init|=
operator|(
operator|(
name|NameCallback
operator|)
name|callbacks
index|[
literal|0
index|]
operator|)
operator|.
name|getName
argument_list|()
decl_stmt|;
name|String
name|p
init|=
name|String
operator|.
name|valueOf
argument_list|(
operator|(
operator|(
name|PasswordCallback
operator|)
name|callbacks
index|[
literal|1
index|]
operator|)
operator|.
name|getPassword
argument_list|()
argument_list|)
decl_stmt|;
name|HashMap
name|userProps
init|=
operator|new
name|HashMap
argument_list|()
decl_stmt|;
name|userProps
operator|.
name|put
argument_list|(
literal|"username"
argument_list|,
name|n
argument_list|)
expr_stmt|;
name|userProps
operator|.
name|put
argument_list|(
literal|"password"
argument_list|,
name|p
argument_list|)
expr_stmt|;
if|if
condition|(
name|doAuthenticate
argument_list|(
name|userProps
argument_list|)
condition|)
return|return
literal|true
return|;
comment|// Authentication failed
if|if
condition|(
name|isDebug
argument_list|()
condition|)
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Ldap authentication failed ... "
argument_list|)
expr_stmt|;
name|setAuthSucceeded
argument_list|(
literal|false
argument_list|)
expr_stmt|;
return|return
literal|false
return|;
block|}
catch|catch
parameter_list|(
name|Exception
name|ex
parameter_list|)
block|{
if|if
condition|(
name|isDebug
argument_list|()
condition|)
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Ldap authentication failed ... "
operator|+
name|ex
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
name|setAuthSucceeded
argument_list|(
literal|false
argument_list|)
expr_stmt|;
return|return
literal|false
return|;
block|}
block|}
comment|/** 	 * Logs the user out 	 */
specifier|public
name|boolean
name|logout
parameter_list|()
throws|throws
name|LoginException
block|{
name|reset
argument_list|()
expr_stmt|;
return|return
literal|true
return|;
block|}
comment|/** 	 * Resets user attributes and status flags 	 */
specifier|public
name|void
name|reset
parameter_list|()
block|{
name|iExternalUid
operator|=
literal|null
expr_stmt|;
name|super
operator|.
name|reset
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Perform actual authentication the user 	 */
specifier|public
name|boolean
name|doAuthenticate
parameter_list|(
name|HashMap
name|userProps
parameter_list|)
throws|throws
name|Exception
block|{
if|if
condition|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.authenticate.ldap.provider"
argument_list|)
operator|==
literal|null
condition|)
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Ldap provider is not set."
argument_list|)
throw|;
name|String
name|principal
init|=
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.authenticate.ldap.principal"
argument_list|)
decl_stmt|;
if|if
condition|(
name|principal
operator|==
literal|null
condition|)
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Ldap principal is not set."
argument_list|)
throw|;
name|String
name|query
init|=
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.authenticate.ldap.query"
argument_list|)
decl_stmt|;
if|if
condition|(
name|query
operator|==
literal|null
condition|)
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Ldap query is not set."
argument_list|)
throw|;
name|String
name|n
init|=
operator|(
name|String
operator|)
name|userProps
operator|.
name|get
argument_list|(
literal|"username"
argument_list|)
decl_stmt|;
name|String
name|p
init|=
operator|(
name|String
operator|)
name|userProps
operator|.
name|get
argument_list|(
literal|"password"
argument_list|)
decl_stmt|;
name|Hashtable
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
name|env
operator|.
name|put
argument_list|(
name|Context
operator|.
name|SECURITY_PRINCIPAL
argument_list|,
name|principal
operator|.
name|replaceAll
argument_list|(
literal|"%"
argument_list|,
name|n
argument_list|)
argument_list|)
expr_stmt|;
comment|//if (isDebug()) System.out.println("env:"+ToolBox.dict2string(env, 2));
name|env
operator|.
name|put
argument_list|(
name|Context
operator|.
name|SECURITY_CREDENTIALS
argument_list|,
name|p
argument_list|)
expr_stmt|;
name|InitialDirContext
name|cx
init|=
operator|new
name|InitialDirContext
argument_list|(
name|env
argument_list|)
decl_stmt|;
comment|//if (isDebug()) System.out.println("cx:"+cx);
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
name|Attributes
name|attributes
init|=
name|cx
operator|.
name|getAttributes
argument_list|(
name|query
operator|.
name|replaceAll
argument_list|(
literal|"%"
argument_list|,
name|n
argument_list|)
argument_list|,
operator|new
name|String
index|[]
block|{
name|idAttributeName
block|}
argument_list|)
decl_stmt|;
comment|//if (isDebug()) System.out.println("attr:"+attributes);
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
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|isDebug
argument_list|()
condition|)
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Ldap authentication passed ... "
argument_list|)
expr_stmt|;
name|setAuthSucceeded
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|iExternalUid
operator|=
operator|(
name|String
operator|)
name|idAttribute
operator|.
name|get
argument_list|()
expr_stmt|;
try|try
block|{
if|if
condition|(
name|iExternalUid
operator|!=
literal|null
operator|&&
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.authenticate.ldap.externalId.format"
argument_list|)
operator|!=
literal|null
condition|)
name|iExternalUid
operator|=
operator|new
name|DecimalFormat
argument_list|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.authenticate.ldap.externalId.format"
argument_list|)
argument_list|)
operator|.
name|format
argument_list|(
name|Long
operator|.
name|parseLong
argument_list|(
name|iExternalUid
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|e
parameter_list|)
block|{
block|}
name|setUser
argument_list|(
name|n
argument_list|)
expr_stmt|;
return|return
literal|true
return|;
block|}
return|return
literal|false
return|;
block|}
block|}
end_class

end_unit

