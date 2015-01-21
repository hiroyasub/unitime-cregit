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
name|filter
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
name|java
operator|.
name|util
operator|.
name|Enumeration
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|Filter
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|FilterChain
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|FilterConfig
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|ServletException
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|ServletRequest
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|ServletResponse
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
name|LogManager
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
name|apache
operator|.
name|log4j
operator|.
name|NDC
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
name|context
operator|.
name|SecurityContextHolder
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
name|UserContext
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|MessageLogFilter
implements|implements
name|Filter
block|{
annotation|@
name|Override
specifier|public
name|void
name|init
parameter_list|(
name|FilterConfig
name|config
parameter_list|)
throws|throws
name|ServletException
block|{
block|}
annotation|@
name|Override
specifier|public
name|void
name|destroy
parameter_list|()
block|{
for|for
control|(
name|Enumeration
name|e
init|=
name|LogManager
operator|.
name|getCurrentLoggers
argument_list|()
init|;
name|e
operator|.
name|hasMoreElements
argument_list|()
condition|;
control|)
block|{
name|Logger
name|logger
init|=
operator|(
name|Logger
operator|)
name|e
operator|.
name|nextElement
argument_list|()
decl_stmt|;
name|logger
operator|.
name|removeAppender
argument_list|(
literal|"mlog"
argument_list|)
expr_stmt|;
block|}
name|LogManager
operator|.
name|getRootLogger
argument_list|()
operator|.
name|removeAppender
argument_list|(
literal|"mlog"
argument_list|)
expr_stmt|;
block|}
specifier|private
name|UserContext
name|getUser
parameter_list|()
block|{
name|Authentication
name|authentication
init|=
name|SecurityContextHolder
operator|.
name|getContext
argument_list|()
operator|.
name|getAuthentication
argument_list|()
decl_stmt|;
if|if
condition|(
name|authentication
operator|!=
literal|null
operator|&&
name|authentication
operator|.
name|isAuthenticated
argument_list|()
operator|&&
name|authentication
operator|.
name|getPrincipal
argument_list|()
operator|instanceof
name|UserContext
condition|)
return|return
operator|(
name|UserContext
operator|)
name|authentication
operator|.
name|getPrincipal
argument_list|()
return|;
return|return
literal|null
return|;
block|}
specifier|private
name|int
name|ndcPush
parameter_list|()
block|{
name|int
name|count
init|=
literal|0
decl_stmt|;
try|try
block|{
name|UserContext
name|user
init|=
name|getUser
argument_list|()
decl_stmt|;
if|if
condition|(
name|user
operator|!=
literal|null
condition|)
block|{
name|NDC
operator|.
name|push
argument_list|(
literal|"uid:"
operator|+
name|user
operator|.
name|getExternalUserId
argument_list|()
argument_list|)
expr_stmt|;
name|count
operator|++
expr_stmt|;
if|if
condition|(
name|user
operator|.
name|getCurrentAuthority
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|NDC
operator|.
name|push
argument_list|(
literal|"role:"
operator|+
name|user
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|getRole
argument_list|()
argument_list|)
expr_stmt|;
name|count
operator|++
expr_stmt|;
name|Long
name|sessionId
init|=
name|user
operator|.
name|getCurrentAcademicSessionId
argument_list|()
decl_stmt|;
if|if
condition|(
name|sessionId
operator|!=
literal|null
condition|)
block|{
name|NDC
operator|.
name|push
argument_list|(
literal|"sid:"
operator|+
name|sessionId
argument_list|)
expr_stmt|;
name|count
operator|++
expr_stmt|;
block|}
block|}
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
block|}
return|return
name|count
return|;
block|}
specifier|private
name|void
name|ndcPop
parameter_list|(
name|int
name|count
parameter_list|)
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
name|count
condition|;
name|i
operator|++
control|)
name|NDC
operator|.
name|pop
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|doFilter
parameter_list|(
name|ServletRequest
name|request
parameter_list|,
name|ServletResponse
name|response
parameter_list|,
name|FilterChain
name|chain
parameter_list|)
throws|throws
name|ServletException
throws|,
name|IOException
block|{
name|int
name|count
init|=
name|ndcPush
argument_list|()
decl_stmt|;
try|try
block|{
name|chain
operator|.
name|doFilter
argument_list|(
name|request
argument_list|,
name|response
argument_list|)
expr_stmt|;
block|}
finally|finally
block|{
name|ndcPop
argument_list|(
name|count
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

