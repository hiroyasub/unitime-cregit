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
name|server
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|PrintWriter
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|StringWriter
import|;
end_import

begin_import
import|import
name|java
operator|.
name|security
operator|.
name|MessageDigest
import|;
end_import

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
name|Date
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
name|HashSet
import|;
end_import

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
name|Random
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
name|springframework
operator|.
name|beans
operator|.
name|factory
operator|.
name|annotation
operator|.
name|Autowired
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
name|Base64
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
name|Email
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
name|gwt
operator|.
name|client
operator|.
name|admin
operator|.
name|PasswordPage
operator|.
name|PasswordChangeRequest
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
name|client
operator|.
name|admin
operator|.
name|PasswordPage
operator|.
name|PasswordChangeResponse
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
name|GwtRpcException
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
name|server
operator|.
name|GwtRpcImplementation
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
name|server
operator|.
name|GwtRpcImplements
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
name|resources
operator|.
name|GwtConstants
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
name|resources
operator|.
name|GwtMessages
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
name|UserData
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
name|SessionContext
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
name|Constants
import|;
end_import

begin_import
import|import
name|freemarker
operator|.
name|template
operator|.
name|Configuration
import|;
end_import

begin_import
import|import
name|freemarker
operator|.
name|template
operator|.
name|Template
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|GwtRpcImplements
argument_list|(
name|PasswordChangeRequest
operator|.
name|class
argument_list|)
specifier|public
class|class
name|PasswordChangeBackend
implements|implements
name|GwtRpcImplementation
argument_list|<
name|PasswordChangeRequest
argument_list|,
name|PasswordChangeResponse
argument_list|>
block|{
specifier|protected
specifier|static
name|GwtMessages
name|MESSAGES
init|=
name|Localization
operator|.
name|create
argument_list|(
name|GwtMessages
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|protected
specifier|static
name|GwtConstants
name|CONSTANTS
init|=
name|Localization
operator|.
name|create
argument_list|(
name|GwtConstants
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|sGenCharset
init|=
literal|"0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
decl_stmt|;
annotation|@
name|Autowired
name|HttpServletRequest
name|iRequest
decl_stmt|;
annotation|@
name|Override
specifier|public
name|PasswordChangeResponse
name|execute
parameter_list|(
name|PasswordChangeRequest
name|request
parameter_list|,
name|SessionContext
name|context
parameter_list|)
block|{
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
name|UserDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
decl_stmt|;
if|if
condition|(
name|request
operator|.
name|isReset
argument_list|()
condition|)
block|{
try|try
block|{
name|Set
argument_list|<
name|String
argument_list|>
name|userIds
init|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|userIds
operator|.
name|addAll
argument_list|(
operator|(
name|List
argument_list|<
name|String
argument_list|>
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"select distinct externalUniqueId from TimetableManager where lower(emailAddress) = :email and externalUniqueId is not null"
argument_list|)
operator|.
name|setString
argument_list|(
literal|"email"
argument_list|,
name|request
operator|.
name|getEmail
argument_list|()
operator|.
name|toLowerCase
argument_list|()
argument_list|)
operator|.
name|list
argument_list|()
argument_list|)
expr_stmt|;
name|userIds
operator|.
name|addAll
argument_list|(
operator|(
name|List
argument_list|<
name|String
argument_list|>
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"select distinct externalUniqueId from Staff where lower(email) = :email and externalUniqueId is not null"
argument_list|)
operator|.
name|setString
argument_list|(
literal|"email"
argument_list|,
name|request
operator|.
name|getEmail
argument_list|()
operator|.
name|toLowerCase
argument_list|()
argument_list|)
operator|.
name|list
argument_list|()
argument_list|)
expr_stmt|;
name|userIds
operator|.
name|addAll
argument_list|(
operator|(
name|List
argument_list|<
name|String
argument_list|>
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"select distinct externalUniqueId from DepartmentalInstructor where lower(email) = :email and externalUniqueId is not null"
argument_list|)
operator|.
name|setString
argument_list|(
literal|"email"
argument_list|,
name|request
operator|.
name|getEmail
argument_list|()
operator|.
name|toLowerCase
argument_list|()
argument_list|)
operator|.
name|list
argument_list|()
argument_list|)
expr_stmt|;
name|userIds
operator|.
name|addAll
argument_list|(
operator|(
name|List
argument_list|<
name|String
argument_list|>
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"select distinct externalUniqueId from Student where lower(email) = :email and externalUniqueId is not null"
argument_list|)
operator|.
name|setString
argument_list|(
literal|"email"
argument_list|,
name|request
operator|.
name|getEmail
argument_list|()
operator|.
name|toLowerCase
argument_list|()
argument_list|)
operator|.
name|list
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|userIds
operator|.
name|isEmpty
argument_list|()
condition|)
throw|throw
operator|new
name|GwtRpcException
argument_list|(
name|MESSAGES
operator|.
name|errorEmailNotValid
argument_list|()
argument_list|)
throw|;
name|boolean
name|matched
init|=
literal|false
decl_stmt|;
for|for
control|(
name|String
name|userId
range|:
name|userIds
control|)
block|{
name|User
name|user
init|=
name|User
operator|.
name|findByExternalId
argument_list|(
name|userId
argument_list|)
decl_stmt|;
if|if
condition|(
name|user
operator|==
literal|null
condition|)
continue|continue;
name|matched
operator|=
literal|true
expr_stmt|;
name|String
name|key
init|=
literal|""
decl_stmt|;
name|Random
name|rnd
init|=
operator|new
name|Random
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
literal|32
condition|;
name|i
operator|++
control|)
name|key
operator|+=
name|sGenCharset
operator|.
name|charAt
argument_list|(
name|rnd
operator|.
name|nextInt
argument_list|(
name|sGenCharset
operator|.
name|length
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|UserData
operator|.
name|setProperty
argument_list|(
name|userId
argument_list|,
literal|"Password.TempKey"
argument_list|,
name|encode
argument_list|(
name|key
argument_list|)
argument_list|)
expr_stmt|;
name|UserData
operator|.
name|setProperty
argument_list|(
name|userId
argument_list|,
literal|"Password.TempKeyStamp"
argument_list|,
name|Long
operator|.
name|toString
argument_list|(
name|System
operator|.
name|currentTimeMillis
argument_list|()
operator|+
literal|48
operator|*
literal|60
operator|*
literal|60
operator|*
literal|1000
argument_list|,
literal|16
argument_list|)
argument_list|)
expr_stmt|;
name|Email
name|email
init|=
name|Email
operator|.
name|createEmail
argument_list|()
decl_stmt|;
name|email
operator|.
name|setSubject
argument_list|(
name|MESSAGES
operator|.
name|emailPasswordChange
argument_list|()
argument_list|)
expr_stmt|;
name|Configuration
name|cfg
init|=
operator|new
name|Configuration
argument_list|()
decl_stmt|;
name|cfg
operator|.
name|setClassForTemplateLoading
argument_list|(
name|PasswordChangeBackend
operator|.
name|class
argument_list|,
literal|""
argument_list|)
expr_stmt|;
name|cfg
operator|.
name|setLocale
argument_list|(
name|Localization
operator|.
name|getJavaLocale
argument_list|()
argument_list|)
expr_stmt|;
name|cfg
operator|.
name|setOutputEncoding
argument_list|(
literal|"utf-8"
argument_list|)
expr_stmt|;
name|cfg
operator|.
name|setEncoding
argument_list|(
name|Localization
operator|.
name|getJavaLocale
argument_list|()
argument_list|,
literal|"utf-8"
argument_list|)
expr_stmt|;
name|Template
name|template
init|=
name|cfg
operator|.
name|getTemplate
argument_list|(
literal|"PasswordResetEmail.ftl"
argument_list|)
decl_stmt|;
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|input
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
argument_list|()
decl_stmt|;
name|input
operator|.
name|put
argument_list|(
literal|"msg"
argument_list|,
name|MESSAGES
argument_list|)
expr_stmt|;
name|input
operator|.
name|put
argument_list|(
literal|"const"
argument_list|,
name|CONSTANTS
argument_list|)
expr_stmt|;
name|String
name|url
init|=
name|iRequest
operator|.
name|getScheme
argument_list|()
operator|+
literal|"://"
operator|+
name|iRequest
operator|.
name|getServerName
argument_list|()
operator|+
literal|":"
operator|+
name|iRequest
operator|.
name|getServerPort
argument_list|()
operator|+
name|iRequest
operator|.
name|getContextPath
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|url
operator|.
name|endsWith
argument_list|(
literal|"/"
argument_list|)
condition|)
name|url
operator|+=
literal|"/"
expr_stmt|;
name|url
operator|+=
literal|"gwt.jsp?page=password&user="
operator|+
name|user
operator|.
name|getUsername
argument_list|()
operator|+
literal|"&key="
operator|+
name|key
expr_stmt|;
name|input
operator|.
name|put
argument_list|(
literal|"username"
argument_list|,
name|user
operator|.
name|getUsername
argument_list|()
argument_list|)
expr_stmt|;
name|input
operator|.
name|put
argument_list|(
literal|"url"
argument_list|,
name|url
argument_list|)
expr_stmt|;
name|input
operator|.
name|put
argument_list|(
literal|"version"
argument_list|,
name|MESSAGES
operator|.
name|pageVersion
argument_list|(
name|Constants
operator|.
name|getVersion
argument_list|()
argument_list|,
name|Constants
operator|.
name|getReleaseDate
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|input
operator|.
name|put
argument_list|(
literal|"ts"
argument_list|,
operator|new
name|Date
argument_list|()
argument_list|)
expr_stmt|;
name|input
operator|.
name|put
argument_list|(
literal|"sender"
argument_list|,
name|ApplicationProperty
operator|.
name|EmailSenderName
operator|.
name|value
argument_list|()
argument_list|)
expr_stmt|;
name|StringWriter
name|s
init|=
operator|new
name|StringWriter
argument_list|()
decl_stmt|;
name|template
operator|.
name|process
argument_list|(
name|input
argument_list|,
operator|new
name|PrintWriter
argument_list|(
name|s
argument_list|)
argument_list|)
expr_stmt|;
name|s
operator|.
name|flush
argument_list|()
expr_stmt|;
name|s
operator|.
name|close
argument_list|()
expr_stmt|;
name|email
operator|.
name|setHTML
argument_list|(
name|s
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|email
operator|.
name|addRecipient
argument_list|(
name|request
operator|.
name|getEmail
argument_list|()
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|email
operator|.
name|send
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|matched
condition|)
throw|throw
operator|new
name|GwtRpcException
argument_list|(
name|MESSAGES
operator|.
name|errorNoMatchingUser
argument_list|()
argument_list|)
throw|;
block|}
catch|catch
parameter_list|(
name|GwtRpcException
name|e
parameter_list|)
block|{
throw|throw
name|e
throw|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|GwtRpcException
argument_list|(
name|MESSAGES
operator|.
name|failedToResetPassword
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
else|else
block|{
try|try
block|{
if|if
condition|(
name|request
operator|.
name|hasUsername
argument_list|()
condition|)
block|{
name|User
name|user
init|=
name|User
operator|.
name|findByUserName
argument_list|(
name|request
operator|.
name|getUsername
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|user
operator|==
literal|null
condition|)
throw|throw
operator|new
name|GwtRpcException
argument_list|(
name|MESSAGES
operator|.
name|errorBadCredentials
argument_list|()
argument_list|)
throw|;
if|if
condition|(
name|encode
argument_list|(
name|request
operator|.
name|getOldPassword
argument_list|()
argument_list|)
operator|.
name|equals
argument_list|(
name|UserData
operator|.
name|getProperty
argument_list|(
name|user
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|,
literal|"Password.TempKey"
argument_list|)
argument_list|)
condition|)
block|{
name|long
name|ts
init|=
name|Long
operator|.
name|valueOf
argument_list|(
name|UserData
operator|.
name|getProperty
argument_list|(
name|user
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|,
literal|"Password.TempKeyStamp"
argument_list|,
literal|"0"
argument_list|)
argument_list|,
literal|16
argument_list|)
decl_stmt|;
if|if
condition|(
name|ts
operator|<
name|System
operator|.
name|currentTimeMillis
argument_list|()
condition|)
throw|throw
operator|new
name|GwtRpcException
argument_list|(
name|MESSAGES
operator|.
name|errorPasswordResetExpired
argument_list|()
argument_list|)
throw|;
name|user
operator|.
name|setPassword
argument_list|(
name|encode
argument_list|(
name|request
operator|.
name|getNewPassword
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|update
argument_list|(
name|user
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|flush
argument_list|()
expr_stmt|;
name|UserData
operator|.
name|removeProperty
argument_list|(
name|user
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|,
literal|"Password.TempKey"
argument_list|)
expr_stmt|;
name|UserData
operator|.
name|removeProperty
argument_list|(
name|user
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|,
literal|"Password.TempKeyStamp"
argument_list|)
expr_stmt|;
return|return
operator|new
name|PasswordChangeResponse
argument_list|()
return|;
block|}
block|}
else|else
block|{
name|context
operator|.
name|checkPermissionAnyAuthority
argument_list|(
name|Right
operator|.
name|ChangePassword
argument_list|)
expr_stmt|;
block|}
name|String
name|username
init|=
operator|(
name|request
operator|.
name|hasUsername
argument_list|()
condition|?
name|request
operator|.
name|getUsername
argument_list|()
else|:
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getUsername
argument_list|()
operator|)
decl_stmt|;
name|User
name|user
init|=
operator|(
name|User
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"from User where username = :username and password = :password"
argument_list|)
operator|.
name|setString
argument_list|(
literal|"username"
argument_list|,
name|username
argument_list|)
operator|.
name|setString
argument_list|(
literal|"password"
argument_list|,
name|encode
argument_list|(
name|request
operator|.
name|getOldPassword
argument_list|()
argument_list|)
argument_list|)
operator|.
name|setMaxResults
argument_list|(
literal|1
argument_list|)
operator|.
name|uniqueResult
argument_list|()
decl_stmt|;
if|if
condition|(
name|user
operator|==
literal|null
condition|)
block|{
if|if
condition|(
name|request
operator|.
name|hasUsername
argument_list|()
condition|)
throw|throw
operator|new
name|GwtRpcException
argument_list|(
name|MESSAGES
operator|.
name|errorBadCredentials
argument_list|()
argument_list|)
throw|;
else|else
throw|throw
operator|new
name|GwtRpcException
argument_list|(
name|MESSAGES
operator|.
name|errorOldPasswordNotValid
argument_list|()
argument_list|)
throw|;
block|}
if|if
condition|(
name|request
operator|.
name|getNewPassword
argument_list|()
operator|==
literal|null
operator|||
name|request
operator|.
name|getNewPassword
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
throw|throw
operator|new
name|GwtRpcException
argument_list|(
name|MESSAGES
operator|.
name|errorEnterNewPassword
argument_list|()
argument_list|)
throw|;
name|user
operator|.
name|setPassword
argument_list|(
name|encode
argument_list|(
name|request
operator|.
name|getNewPassword
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|update
argument_list|(
name|user
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|flush
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NoSuchAlgorithmException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|GwtRpcException
argument_list|(
name|MESSAGES
operator|.
name|failedToChangePassword
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
return|return
operator|new
name|PasswordChangeResponse
argument_list|()
return|;
block|}
specifier|private
specifier|static
name|String
name|encode
parameter_list|(
name|String
name|password
parameter_list|)
throws|throws
name|NoSuchAlgorithmException
block|{
name|MessageDigest
name|md
init|=
name|MessageDigest
operator|.
name|getInstance
argument_list|(
literal|"MD5"
argument_list|)
decl_stmt|;
name|md
operator|.
name|update
argument_list|(
name|password
operator|.
name|getBytes
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|Base64
operator|.
name|encodeBytes
argument_list|(
name|md
operator|.
name|digest
argument_list|()
argument_list|)
return|;
block|}
block|}
end_class

end_unit

