begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 - 3.5 (University Timetabling Application)  * Copyright (C) 2008 - 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|model
operator|.
name|base
operator|.
name|_BaseRootDAO
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|cpsolver
operator|.
name|ifs
operator|.
name|util
operator|.
name|JProf
import|;
end_import

begin_comment
comment|/**  * This filter is used to close Hibernate Session when response   * goes back to user as suggested in Hibernate 3 documentation:   * http://www.hibernate.org/hib_docs/v3/reference/en/pdf/hibernate_reference.pdf  * 19.1.3. Initializing collections and proxies  * @author Dagmar Murray, Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|HibSessionFilter
implements|implements
name|Filter
block|{
specifier|private
name|FilterConfig
name|filterConfig
init|=
literal|null
decl_stmt|;
comment|/** 	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig) 	 */
specifier|public
name|void
name|init
parameter_list|(
name|FilterConfig
name|arg0
parameter_list|)
throws|throws
name|ServletException
block|{
name|filterConfig
operator|=
name|arg0
expr_stmt|;
name|Debug
operator|.
name|debug
argument_list|(
literal|"Initializing filter, obtaining Hibernate SessionFactory from HibernateUtil"
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain) 	 */
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
name|IOException
throws|,
name|ServletException
block|{
if|if
condition|(
name|filterConfig
operator|==
literal|null
condition|)
return|return;
if|if
condition|(
name|request
operator|.
name|getAttribute
argument_list|(
literal|"TimeStamp"
argument_list|)
operator|==
literal|null
condition|)
name|request
operator|.
name|setAttribute
argument_list|(
literal|"TimeStamp"
argument_list|,
operator|new
name|Double
argument_list|(
name|JProf
operator|.
name|currentTimeSec
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
try|try
block|{
comment|// Process request
name|chain
operator|.
name|doFilter
argument_list|(
name|request
argument_list|,
name|response
argument_list|)
expr_stmt|;
name|_BaseRootDAO
operator|.
name|closeCurrentThreadSessions
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Throwable
name|ex
parameter_list|)
block|{
name|_BaseRootDAO
operator|.
name|rollbackCurrentThreadSessions
argument_list|()
expr_stmt|;
if|if
condition|(
name|ex
operator|instanceof
name|ServletException
condition|)
throw|throw
operator|(
name|ServletException
operator|)
name|ex
throw|;
if|if
condition|(
name|ex
operator|instanceof
name|IOException
condition|)
throw|throw
operator|(
name|IOException
operator|)
name|ex
throw|;
if|if
condition|(
name|ex
operator|instanceof
name|RuntimeException
condition|)
throw|throw
operator|(
name|RuntimeException
operator|)
name|ex
throw|;
comment|// Let others handle it... maybe another interceptor for exceptions?
throw|throw
operator|new
name|ServletException
argument_list|(
name|ex
argument_list|)
throw|;
block|}
block|}
comment|/** 	 * @see javax.servlet.Filter#destroy() 	 */
specifier|public
name|void
name|destroy
parameter_list|()
block|{
name|this
operator|.
name|filterConfig
operator|=
literal|null
expr_stmt|;
block|}
block|}
end_class

end_unit

