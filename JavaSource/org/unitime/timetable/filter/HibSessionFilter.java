begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.0 (University Course Timetabling& Student Sectioning Application)  * Copyright (C) 2007, UniTime.org, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program; if not, write to the Free Software Foundation, Inc.,  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|hibernate
operator|.
name|Session
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
name|dao
operator|.
name|_RootDAO
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
comment|/**  * This filter is used to close Hibernate Session when response   * goes back to user as suggested in Hibernate 3 documentation:   * http://www.hibernate.org/hib_docs/v3/reference/en/pdf/hibernate_reference.pdf  * 19.1.3. Initializing collections and proxies  * @author Dagmar Murray  */
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
name|Session
name|hibSession
init|=
literal|null
decl_stmt|;
try|try
block|{
name|hibSession
operator|=
operator|new
name|_RootDAO
argument_list|()
operator|.
name|getSession
argument_list|()
expr_stmt|;
comment|//if(!hibSession.getTransaction().isActive()) {
comment|//Debug.info("Starting transaction");
comment|//hibSession.beginTransaction();
comment|//}
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
comment|// Close hibernate session, after request is processed
comment|//if(!hibSession.getTransaction().isActive()) {
comment|//Debug.info("Committing transaction");
comment|//hibSession.getTransaction().commit();
comment|//}
if|if
condition|(
name|hibSession
operator|!=
literal|null
operator|&&
name|hibSession
operator|.
name|isOpen
argument_list|()
condition|)
block|{
name|hibSession
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|Throwable
name|ex
parameter_list|)
block|{
comment|// Rollback only
comment|//ex.printStackTrace();
try|try
block|{
if|if
condition|(
name|hibSession
operator|!=
literal|null
operator|&&
name|hibSession
operator|.
name|isOpen
argument_list|()
operator|&&
name|hibSession
operator|.
name|getTransaction
argument_list|()
operator|.
name|isActive
argument_list|()
condition|)
block|{
name|Debug
operator|.
name|debug
argument_list|(
literal|"Trying to rollback database transaction after exception"
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|getTransaction
argument_list|()
operator|.
name|rollback
argument_list|()
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|Throwable
name|rbEx
parameter_list|)
block|{
name|Debug
operator|.
name|error
argument_list|(
name|rbEx
argument_list|)
expr_stmt|;
block|}
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

