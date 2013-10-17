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

begin_comment
comment|/**  * @author Tomas Muller, Zuzana Mullerova  */
end_comment

begin_class
specifier|public
class|class
name|EncodingFilter
implements|implements
name|Filter
block|{
specifier|private
name|String
name|iEncoding
decl_stmt|;
comment|/** 	* @see javax.servlet.Filter#init(javax.servlet.FilterConfig) 	*/
specifier|public
name|void
name|init
parameter_list|(
name|FilterConfig
name|fc
parameter_list|)
throws|throws
name|ServletException
block|{
name|iEncoding
operator|=
name|fc
operator|.
name|getInitParameter
argument_list|(
literal|"encoding"
argument_list|)
expr_stmt|;
block|}
comment|/** 	* @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain) 	*/
specifier|public
name|void
name|doFilter
parameter_list|(
name|ServletRequest
name|req
parameter_list|,
name|ServletResponse
name|resp
parameter_list|,
name|FilterChain
name|chain
parameter_list|)
throws|throws
name|IOException
throws|,
name|ServletException
block|{
name|req
operator|.
name|setCharacterEncoding
argument_list|(
name|iEncoding
argument_list|)
expr_stmt|;
name|resp
operator|.
name|setCharacterEncoding
argument_list|(
name|iEncoding
argument_list|)
expr_stmt|;
name|chain
operator|.
name|doFilter
argument_list|(
name|req
argument_list|,
name|resp
argument_list|)
expr_stmt|;
block|}
comment|/** 	* @see javax.servlet.Filter#destroy() 	*/
specifier|public
name|void
name|destroy
parameter_list|()
block|{
block|}
block|}
end_class

end_unit

