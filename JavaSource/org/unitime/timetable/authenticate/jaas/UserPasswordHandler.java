begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 (University Timetabling Application)  * Copyright (C) 2008 - 2010, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|io
operator|.
name|IOException
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
name|callback
operator|.
name|UnsupportedCallbackException
import|;
end_import

begin_comment
comment|/**  * Handler for username / password  * @author Heston Fernandes  */
end_comment

begin_class
specifier|public
class|class
name|UserPasswordHandler
implements|implements
name|CallbackHandler
block|{
specifier|private
name|String
name|username
decl_stmt|;
specifier|private
name|String
name|password
decl_stmt|;
comment|/** 	 * Initialize handler 	 * @param username 	 * @param password 	 */
specifier|public
name|UserPasswordHandler
parameter_list|(
name|String
name|username
parameter_list|,
name|String
name|password
parameter_list|)
block|{
name|this
operator|.
name|username
operator|=
name|username
expr_stmt|;
name|this
operator|.
name|password
operator|=
name|password
expr_stmt|;
block|}
comment|/* (non-Javadoc) 	 * @see javax.security.auth.callback.CallbackHandler#handle(javax.security.auth.callback.Callback[]) 	 */
specifier|public
name|void
name|handle
parameter_list|(
name|Callback
index|[]
name|callbacks
parameter_list|)
throws|throws
name|IOException
throws|,
name|UnsupportedCallbackException
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|callbacks
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|Callback
name|c
init|=
name|callbacks
index|[
name|i
index|]
decl_stmt|;
if|if
condition|(
name|c
operator|instanceof
name|NameCallback
condition|)
block|{
name|NameCallback
name|nc
init|=
operator|(
name|NameCallback
operator|)
name|c
decl_stmt|;
name|nc
operator|.
name|setName
argument_list|(
name|username
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|c
operator|instanceof
name|PasswordCallback
condition|)
block|{
name|PasswordCallback
name|pc
init|=
operator|(
name|PasswordCallback
operator|)
name|c
decl_stmt|;
name|pc
operator|.
name|setPassword
argument_list|(
name|password
operator|.
name|toCharArray
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
throw|throw
operator|new
name|UnsupportedCallbackException
argument_list|(
name|c
argument_list|,
name|c
operator|.
name|getClass
argument_list|()
operator|.
name|getName
argument_list|()
operator|+
literal|" callback not supported"
argument_list|)
throw|;
block|}
block|}
block|}
end_class

end_unit

