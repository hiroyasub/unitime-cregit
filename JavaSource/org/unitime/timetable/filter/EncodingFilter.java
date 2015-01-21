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

