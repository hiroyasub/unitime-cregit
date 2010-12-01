begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 (University Timetabling Application)  * Copyright (C) 2009 - 2010, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|timetable
operator|.
name|ApplicationProperties
import|;
end_import

begin_comment
comment|/**  * @author says  *  */
end_comment

begin_class
specifier|public
class|class
name|LoginManager
block|{
specifier|private
specifier|static
name|HashMap
argument_list|<
name|String
argument_list|,
name|FailedLoginAttempt
argument_list|>
name|failedLoginAttempts
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|FailedLoginAttempt
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
specifier|final
specifier|static
name|int
name|DEFAULT_MAX_FAILED_ATTEMPTS
init|=
literal|7
decl_stmt|;
specifier|private
specifier|final
specifier|static
name|int
name|DEFAULT_DELAY_MILLISECONDS
init|=
literal|15000
decl_stmt|;
comment|/** 	 *  	 */
specifier|public
name|LoginManager
parameter_list|()
block|{
comment|// TODO Auto-generated constructor stub
block|}
specifier|public
specifier|static
name|int
name|getMaxFailedAttempts
parameter_list|()
block|{
name|String
name|maxFailedAttemptsStr
init|=
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.login.max.failed.attempts"
argument_list|,
name|Integer
operator|.
name|toString
argument_list|(
name|DEFAULT_MAX_FAILED_ATTEMPTS
argument_list|)
argument_list|)
decl_stmt|;
name|int
name|maxFailedAttempts
decl_stmt|;
try|try
block|{
name|maxFailedAttempts
operator|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|maxFailedAttemptsStr
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|e
parameter_list|)
block|{
name|maxFailedAttempts
operator|=
name|DEFAULT_MAX_FAILED_ATTEMPTS
expr_stmt|;
block|}
if|if
condition|(
name|maxFailedAttempts
operator|<
literal|0
condition|)
block|{
name|maxFailedAttempts
operator|=
name|DEFAULT_MAX_FAILED_ATTEMPTS
expr_stmt|;
block|}
return|return
operator|(
name|maxFailedAttempts
operator|)
return|;
block|}
specifier|public
specifier|static
name|int
name|getDelayMilliseconds
parameter_list|()
block|{
name|String
name|delayMillisecondsStr
init|=
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.login.failed.delay.milliseconds"
argument_list|,
name|Integer
operator|.
name|toString
argument_list|(
name|DEFAULT_DELAY_MILLISECONDS
argument_list|)
argument_list|)
decl_stmt|;
name|int
name|delayMilliseconds
decl_stmt|;
try|try
block|{
name|delayMilliseconds
operator|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|delayMillisecondsStr
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|e
parameter_list|)
block|{
name|delayMilliseconds
operator|=
name|DEFAULT_DELAY_MILLISECONDS
expr_stmt|;
block|}
if|if
condition|(
name|delayMilliseconds
operator|<
literal|0
condition|)
block|{
name|delayMilliseconds
operator|=
name|DEFAULT_DELAY_MILLISECONDS
expr_stmt|;
block|}
return|return
operator|(
name|delayMilliseconds
operator|)
return|;
block|}
specifier|public
specifier|static
name|boolean
name|isUserLockedOut
parameter_list|(
name|String
name|user
parameter_list|,
name|Date
name|attemptDateTime
parameter_list|)
block|{
name|FailedLoginAttempt
name|fla
init|=
name|failedLoginAttempts
operator|.
name|get
argument_list|(
name|user
argument_list|)
decl_stmt|;
if|if
condition|(
name|fla
operator|!=
literal|null
condition|)
block|{
name|boolean
name|lockedOut
init|=
name|fla
operator|.
name|isUserLockedOut
argument_list|(
name|user
argument_list|,
name|attemptDateTime
argument_list|)
decl_stmt|;
if|if
condition|(
name|lockedOut
condition|)
block|{
name|Debug
operator|.
name|info
argument_list|(
literal|"Too many failed login attempts - User: "
operator|+
name|user
operator|+
literal|" temporarily locked out."
argument_list|)
expr_stmt|;
if|if
condition|(
name|fla
operator|.
name|getCount
argument_list|()
operator|>
operator|(
name|getMaxFailedAttempts
argument_list|()
operator|+
literal|3
operator|)
condition|)
block|{
comment|// If user has exceed his max failed attempts by 3 do not respond as quickly
comment|// This helps to prevent users from flooding the system with failed login attempts.
try|try
block|{
name|Thread
operator|.
name|sleep
argument_list|(
name|getDelayMilliseconds
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|InterruptedException
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
block|}
return|return
operator|(
name|lockedOut
operator|)
return|;
block|}
else|else
block|{
return|return
operator|(
literal|false
operator|)
return|;
block|}
block|}
specifier|public
specifier|static
name|void
name|addFailedLoginAttempt
parameter_list|(
name|String
name|user
parameter_list|,
name|Date
name|attemptDateTime
parameter_list|)
block|{
name|FailedLoginAttempt
name|fla
init|=
name|failedLoginAttempts
operator|.
name|get
argument_list|(
name|user
argument_list|)
decl_stmt|;
if|if
condition|(
name|fla
operator|==
literal|null
condition|)
block|{
name|failedLoginAttempts
operator|.
name|put
argument_list|(
name|user
argument_list|,
operator|new
name|FailedLoginAttempt
argument_list|(
name|user
argument_list|,
literal|1
argument_list|,
name|attemptDateTime
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|fla
operator|.
name|getCount
argument_list|()
operator|<
name|getMaxFailedAttempts
argument_list|()
condition|)
block|{
name|fla
operator|.
name|setLastFailedAttempt
argument_list|(
name|attemptDateTime
argument_list|)
expr_stmt|;
block|}
name|fla
operator|.
name|setCount
argument_list|(
name|fla
operator|.
name|getCount
argument_list|()
operator|+
literal|1
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
specifier|static
name|void
name|loginSuceeded
parameter_list|(
name|String
name|user
parameter_list|)
block|{
name|failedLoginAttempts
operator|.
name|remove
argument_list|(
name|user
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

