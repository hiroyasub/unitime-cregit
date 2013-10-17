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
name|HttpServletRequestWrapper
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

begin_comment
comment|/**  *  Servlet Filter to allow only certain characters in the request  *  object to prevent Cross-Site Scripting and SQL Injection attacks.    *   *  The filter accepts the following optional paramters:  *    *   replace_string   : This is character / string that replaces all characters   * 				  		not in the allowed list. replaceStr defaults to underscore (_)  *   additional_chars : These are additional characters that should be allowed in addition  * 				  		to the default characters. RegEx patterns may also be supplied.  *   * @author Heston Fernandes  */
end_comment

begin_class
specifier|public
class|class
name|XSSFilter
implements|implements
name|Filter
block|{
comment|// ------------------------------------------------------ Properties
comment|/** Filter Configuration object **/
specifier|private
name|FilterConfig
name|filterConfig
decl_stmt|;
comment|/** The character that replaces the filtered character **/
specifier|private
name|String
name|replaceStr
init|=
literal|"_"
decl_stmt|;
comment|/** Additional characters that should be allowed **/
specifier|private
name|String
name|addlChars
init|=
literal|""
decl_stmt|;
comment|/** Pattern of allowed characters **/
comment|//private String charsAllowed = "A-Za-z0-9@.' _+&%=/\\-";
specifier|private
name|String
name|charsAllowed
init|=
literal|"A-Za-z0-9@. _+&%/\\-"
decl_stmt|;
comment|// ------------------------------------------------------ Methods
comment|/**      * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)      */
specifier|public
name|void
name|init
parameter_list|(
name|FilterConfig
name|filterConfig
parameter_list|)
throws|throws
name|ServletException
block|{
name|this
operator|.
name|filterConfig
operator|=
name|filterConfig
expr_stmt|;
block|}
comment|/**      * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,      *      javax.servlet.ServletResponse, javax.servlet.FilterChain)      */
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
comment|// Get Filter Init Parameters
name|String
name|replaceStrParam
init|=
name|filterConfig
operator|.
name|getInitParameter
argument_list|(
literal|"replace_string"
argument_list|)
decl_stmt|;
name|String
name|addlCharsParam
init|=
name|filterConfig
operator|.
name|getInitParameter
argument_list|(
literal|"additional_chars"
argument_list|)
decl_stmt|;
comment|// Set variables
if|if
condition|(
name|replaceStrParam
operator|!=
literal|null
condition|)
name|replaceStr
operator|=
name|replaceStrParam
expr_stmt|;
if|if
condition|(
name|addlCharsParam
operator|!=
literal|null
condition|)
name|addlChars
operator|=
name|addlCharsParam
expr_stmt|;
comment|// Construct allowed characters pattern
name|String
name|charPattern
init|=
literal|"([^"
operator|+
name|charsAllowed
operator|+
name|addlChars
operator|+
literal|"]+)(%0A)"
decl_stmt|;
comment|// Instantiate actual filter
name|RequestXSSFilter
name|rxs
init|=
operator|new
name|RequestXSSFilter
argument_list|(
operator|(
name|HttpServletRequest
operator|)
name|request
argument_list|,
name|replaceStr
argument_list|,
name|charPattern
argument_list|)
decl_stmt|;
comment|// Process request
name|chain
operator|.
name|doFilter
argument_list|(
name|rxs
argument_list|,
name|response
argument_list|)
expr_stmt|;
block|}
comment|/**      * @see javax.servlet.Filter#destroy()      */
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
comment|/**      * Testing function      * @param args      */
specifier|public
specifier|static
name|void
name|main
parameter_list|(
name|String
index|[]
name|args
parameter_list|)
block|{
name|String
name|arg
init|=
literal|"jhfsdhfds/=+;-%0Adsa"
decl_stmt|;
if|if
condition|(
name|args
index|[
literal|0
index|]
operator|!=
literal|null
operator|&&
name|args
index|[
literal|0
index|]
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|arg
operator|=
name|args
index|[
literal|0
index|]
expr_stmt|;
name|String
name|result
init|=
name|arg
operator|.
name|trim
argument_list|()
operator|.
name|replaceAll
argument_list|(
literal|"([^A-Za-z0-9@.' _+&%/=\\-]+)(%0A)"
argument_list|,
literal|"_"
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|result
operator|.
name|equals
argument_list|(
name|arg
argument_list|)
condition|)
block|{
name|Debug
operator|.
name|debug
argument_list|(
literal|"Before Filtering: "
operator|+
name|result
argument_list|)
expr_stmt|;
name|Debug
operator|.
name|debug
argument_list|(
literal|"After Filtering: "
operator|+
name|result
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

begin_comment
comment|/**  * Class that wraps HttpServletRequest and filters the request parameters  * This logic is contrary to conventional logic where only certain tags and  * words are replaced. As there are new hacks everyday, the safest way is to   * allow only certain characters: A-Z a-z 0-9 @ . ' space _ - (by default)  * @note This logic is yet to be tested so do not use it yet. Heston   */
end_comment

begin_class
specifier|final
class|class
name|RequestXSSFilter
extends|extends
name|HttpServletRequestWrapper
block|{
comment|/** The character that replaces the filtered character **/
specifier|private
name|String
name|replaceStr
init|=
literal|"_"
decl_stmt|;
comment|/** Pattern of allowed characters **/
specifier|private
name|String
name|charsAllowed
init|=
literal|""
decl_stmt|;
comment|/**      * Constructor with filter parameters      * @param servletRequest HttpServletRequest object      * @param replaceStr Character/String that replaces all characters not allowed      * @param charsAllowed Pattern of allowed characters      */
specifier|public
name|RequestXSSFilter
parameter_list|(
name|HttpServletRequest
name|servletRequest
parameter_list|,
name|String
name|replaceStr
parameter_list|,
name|String
name|charsAllowed
parameter_list|)
block|{
name|super
argument_list|(
name|servletRequest
argument_list|)
expr_stmt|;
name|this
operator|.
name|charsAllowed
operator|=
name|charsAllowed
expr_stmt|;
name|this
operator|.
name|replaceStr
operator|=
name|replaceStr
expr_stmt|;
block|}
comment|/**      * @param parameter Parameter name      * @return array of filtered request parameters      */
specifier|public
name|String
index|[]
name|getParameterValues
parameter_list|(
name|String
name|parameter
parameter_list|)
block|{
name|String
index|[]
name|results
init|=
name|super
operator|.
name|getParameterValues
argument_list|(
name|parameter
argument_list|)
decl_stmt|;
if|if
condition|(
name|results
operator|==
literal|null
condition|)
return|return
name|results
return|;
name|int
name|count
init|=
name|results
operator|.
name|length
decl_stmt|;
name|String
index|[]
name|params
init|=
operator|new
name|String
index|[
name|count
index|]
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
name|count
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|results
index|[
name|i
index|]
operator|!=
literal|null
condition|)
block|{
comment|// Filter
name|params
index|[
name|i
index|]
operator|=
name|results
index|[
name|i
index|]
operator|.
name|trim
argument_list|()
operator|.
name|replaceAll
argument_list|(
name|charsAllowed
argument_list|,
name|replaceStr
argument_list|)
expr_stmt|;
comment|// Characters were filtered out
if|if
condition|(
operator|!
name|results
index|[
name|i
index|]
operator|.
name|equals
argument_list|(
name|params
index|[
name|i
index|]
argument_list|)
condition|)
block|{
name|Debug
operator|.
name|debug
argument_list|(
literal|"parameterValues: "
operator|+
name|parameter
argument_list|)
expr_stmt|;
name|Debug
operator|.
name|debug
argument_list|(
literal|"   - before Filtering: "
operator|+
name|results
index|[
name|i
index|]
argument_list|)
expr_stmt|;
name|Debug
operator|.
name|debug
argument_list|(
literal|"   - after Filtering: "
operator|+
name|params
index|[
name|i
index|]
argument_list|)
expr_stmt|;
block|}
block|}
else|else
name|params
index|[
name|i
index|]
operator|=
literal|null
expr_stmt|;
block|}
return|return
name|params
return|;
block|}
comment|/**      * @see javax.servlet.ServletRequest#getParameter(java.lang.String)      */
specifier|public
name|String
name|getParameter
parameter_list|(
name|String
name|parameter
parameter_list|)
block|{
name|String
name|result
init|=
name|super
operator|.
name|getParameter
argument_list|(
name|parameter
argument_list|)
decl_stmt|;
if|if
condition|(
name|result
operator|==
literal|null
condition|)
return|return
name|result
return|;
name|String
name|bf
init|=
name|result
decl_stmt|;
comment|// Filter
name|result
operator|=
name|result
operator|.
name|trim
argument_list|()
operator|.
name|replaceAll
argument_list|(
name|charsAllowed
argument_list|,
name|replaceStr
argument_list|)
expr_stmt|;
comment|// Characters were filtered out
if|if
condition|(
operator|!
name|bf
operator|.
name|equals
argument_list|(
name|result
argument_list|)
condition|)
block|{
name|Debug
operator|.
name|debug
argument_list|(
literal|"parameter: "
operator|+
name|parameter
argument_list|)
expr_stmt|;
name|Debug
operator|.
name|debug
argument_list|(
literal|"   - before Filtering: "
operator|+
name|bf
argument_list|)
expr_stmt|;
name|Debug
operator|.
name|debug
argument_list|(
literal|"   - after Filtering: "
operator|+
name|result
argument_list|)
expr_stmt|;
block|}
return|return
name|result
return|;
block|}
block|}
end_class

end_unit

