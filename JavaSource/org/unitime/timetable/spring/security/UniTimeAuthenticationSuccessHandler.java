begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.4 - 3.5 (University Timetabling Application)  * Copyright (C) 2012 - 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *   */
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
name|security
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
name|net
operator|.
name|URLEncoder
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
name|http
operator|.
name|HttpServletRequest
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
name|HttpServletResponse
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
name|web
operator|.
name|authentication
operator|.
name|SimpleUrlAuthenticationSuccessHandler
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
name|web
operator|.
name|savedrequest
operator|.
name|HttpSessionRequestCache
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
name|web
operator|.
name|savedrequest
operator|.
name|RequestCache
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
name|web
operator|.
name|savedrequest
operator|.
name|SavedRequest
import|;
end_import

begin_import
import|import
name|org
operator|.
name|springframework
operator|.
name|stereotype
operator|.
name|Service
import|;
end_import

begin_import
import|import
name|org
operator|.
name|springframework
operator|.
name|util
operator|.
name|StringUtils
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
name|LoginManager
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|Service
argument_list|(
literal|"unitimeAuthenticationSuccessHandler"
argument_list|)
specifier|public
class|class
name|UniTimeAuthenticationSuccessHandler
extends|extends
name|SimpleUrlAuthenticationSuccessHandler
block|{
specifier|protected
specifier|final
name|Log
name|logger
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|this
operator|.
name|getClass
argument_list|()
argument_list|)
decl_stmt|;
specifier|private
name|RequestCache
name|requestCache
init|=
operator|new
name|HttpSessionRequestCache
argument_list|()
decl_stmt|;
specifier|private
name|boolean
name|useReferer
init|=
literal|false
decl_stmt|;
specifier|public
name|UniTimeAuthenticationSuccessHandler
parameter_list|()
block|{
name|setAlwaysUseDefaultTargetUrl
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|setDefaultTargetUrl
argument_list|(
literal|"/selectPrimaryRole.do"
argument_list|)
expr_stmt|;
name|setTargetUrlParameter
argument_list|(
literal|"target"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|onAuthenticationSuccess
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|HttpServletResponse
name|response
parameter_list|,
name|Authentication
name|authentication
parameter_list|)
throws|throws
name|IOException
throws|,
name|ServletException
block|{
name|LoginManager
operator|.
name|loginSuceeded
argument_list|(
name|authentication
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|request
operator|.
name|getSession
argument_list|()
operator|.
name|removeAttribute
argument_list|(
literal|"SUGGEST_PASSWORD_RESET"
argument_list|)
expr_stmt|;
name|super
operator|.
name|onAuthenticationSuccess
argument_list|(
name|request
argument_list|,
name|response
argument_list|,
name|authentication
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|protected
name|String
name|determineTargetUrl
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|HttpServletResponse
name|response
parameter_list|)
block|{
if|if
condition|(
name|isAlwaysUseDefaultTargetUrl
argument_list|()
condition|)
return|return
name|getDefaultTargetUrl
argument_list|()
return|;
name|String
name|targetUrl
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|getTargetUrlParameter
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|targetUrl
operator|=
name|request
operator|.
name|getParameter
argument_list|(
name|getTargetUrlParameter
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|SavedRequest
name|savedRequest
init|=
name|requestCache
operator|.
name|getRequest
argument_list|(
name|request
argument_list|,
name|response
argument_list|)
decl_stmt|;
if|if
condition|(
name|savedRequest
operator|!=
literal|null
operator|&&
operator|!
name|StringUtils
operator|.
name|hasText
argument_list|(
name|targetUrl
argument_list|)
condition|)
block|{
name|targetUrl
operator|=
name|savedRequest
operator|.
name|getRedirectUrl
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|useReferer
operator|&&
operator|!
name|StringUtils
operator|.
name|hasText
argument_list|(
name|targetUrl
argument_list|)
condition|)
block|{
name|targetUrl
operator|=
name|request
operator|.
name|getHeader
argument_list|(
literal|"Referer"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|StringUtils
operator|.
name|hasText
argument_list|(
name|targetUrl
argument_list|)
condition|)
block|{
try|try
block|{
name|request
operator|.
name|setAttribute
argument_list|(
literal|"target"
argument_list|,
name|targetUrl
argument_list|)
expr_stmt|;
return|return
name|getDefaultTargetUrl
argument_list|()
operator|+
literal|"?"
operator|+
name|getTargetUrlParameter
argument_list|()
operator|+
literal|"="
operator|+
name|URLEncoder
operator|.
name|encode
argument_list|(
name|targetUrl
argument_list|,
literal|"UTF-8"
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
block|}
block|}
return|return
name|getDefaultTargetUrl
argument_list|()
return|;
block|}
specifier|public
name|void
name|setUseReferer
parameter_list|(
name|boolean
name|useReferer
parameter_list|)
block|{
name|this
operator|.
name|useReferer
operator|=
name|useReferer
expr_stmt|;
block|}
block|}
end_class

end_unit

