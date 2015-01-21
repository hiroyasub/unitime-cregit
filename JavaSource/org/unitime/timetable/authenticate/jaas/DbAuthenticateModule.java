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
name|NoSuchAlgorithmException
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
name|Map
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
name|apache
operator|.
name|log4j
operator|.
name|Logger
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
name|encoding
operator|.
name|MessageDigestPasswordEncoder
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
name|User
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
name|UserDAO
import|;
end_import

begin_comment
comment|/**  * Authenticates a user by looking up username/password in the database Options:  * debug=true/false  *  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|Deprecated
specifier|public
class|class
name|DbAuthenticateModule
extends|extends
name|AuthenticateModule
block|{
specifier|private
specifier|static
name|Logger
name|sLog
init|=
name|Logger
operator|.
name|getLogger
argument_list|(
name|DbAuthenticateModule
operator|.
name|class
argument_list|)
decl_stmt|;
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
block|{
return|return
literal|false
return|;
block|}
else|else
block|{
if|if
condition|(
name|isAuthSucceeded
argument_list|()
operator|&&
operator|!
name|isCommitSucceeded
argument_list|()
condition|)
block|{
name|reset
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|logout
argument_list|()
expr_stmt|;
block|}
block|}
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
name|getSubject
argument_list|()
operator|.
name|getPrincipals
argument_list|()
operator|.
name|add
argument_list|(
operator|new
name|AuthenticatedUser
argument_list|(
name|getUser
argument_list|()
argument_list|,
name|iExternalUid
argument_list|)
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
name|sLog
operator|.
name|debug
argument_list|(
literal|"Performing db authentication ... "
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
name|sLog
operator|.
name|debug
argument_list|(
literal|"Db authentication failed ... "
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
name|sLog
operator|.
name|debug
argument_list|(
literal|"Db authentication failed ... "
operator|+
name|ex
operator|.
name|getMessage
argument_list|()
argument_list|,
name|ex
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
comment|// Check username/password with DB
name|User
name|u
init|=
operator|new
name|UserDAO
argument_list|()
operator|.
name|get
argument_list|(
name|n
argument_list|)
decl_stmt|;
if|if
condition|(
name|u
operator|!=
literal|null
condition|)
block|{
name|String
name|pwd
init|=
name|u
operator|.
name|getPassword
argument_list|()
decl_stmt|;
comment|// Authentication succeeded
if|if
condition|(
name|checkPassword
argument_list|(
name|p
argument_list|,
name|pwd
argument_list|)
condition|)
block|{
name|sLog
operator|.
name|debug
argument_list|(
literal|"Db authentication passed ... "
argument_list|)
expr_stmt|;
name|setAuthSucceeded
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|iExternalUid
operator|=
name|u
operator|.
name|getExternalUniqueId
argument_list|()
expr_stmt|;
name|setUser
argument_list|(
name|n
argument_list|)
expr_stmt|;
return|return
literal|true
return|;
block|}
block|}
return|return
literal|false
return|;
block|}
comment|/** 	 * Gets the MD5 hash and encodes it in Base 64 notation 	 *  	 * @param clearTextPassword 	 * @return 	 * @throws NoSuchAlgorithmException 	 */
specifier|public
specifier|static
name|String
name|getEncodedPassword
parameter_list|(
name|String
name|clearTextPassword
parameter_list|)
block|{
return|return
operator|new
name|MessageDigestPasswordEncoder
argument_list|(
literal|"MD5"
argument_list|,
literal|true
argument_list|)
operator|.
name|encodePassword
argument_list|(
name|clearTextPassword
argument_list|,
literal|null
argument_list|)
return|;
block|}
comment|/** 	 * Checks a password with the MD5 hash 	 *  	 * @param clearTextTestPassword 	 * @param encodedActualPassword 	 * @return 	 * @throws NoSuchAlgorithmException 	 */
specifier|public
specifier|static
name|boolean
name|checkPassword
parameter_list|(
name|String
name|clearTextTestPassword
parameter_list|,
name|String
name|encodedActualPassword
parameter_list|)
throws|throws
name|NoSuchAlgorithmException
block|{
name|String
name|encodedTestPassword
init|=
name|getEncodedPassword
argument_list|(
name|clearTextTestPassword
argument_list|)
decl_stmt|;
return|return
operator|(
name|encodedTestPassword
operator|.
name|equals
argument_list|(
name|encodedActualPassword
argument_list|)
operator|)
return|;
block|}
comment|/** 	 * Generate passwords 	 *  	 * @param args 	 * @throws Exception 	 */
specifier|public
specifier|static
name|void
name|main
parameter_list|(
name|String
index|[]
name|args
parameter_list|)
throws|throws
name|Exception
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|DbAuthenticateModule
operator|.
name|getEncodedPassword
argument_list|(
name|args
index|[
literal|0
index|]
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

