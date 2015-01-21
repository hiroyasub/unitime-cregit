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
name|tags
package|;
end_package

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
name|jsp
operator|.
name|JspException
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|jsp
operator|.
name|tagext
operator|.
name|BodyTagSupport
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
name|defaults
operator|.
name|ApplicationProperty
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|Wiki
extends|extends
name|BodyTagSupport
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1018397983833478965L
decl_stmt|;
specifier|private
name|String
name|iWikiUrl
init|=
literal|null
decl_stmt|;
specifier|private
name|boolean
name|iEnabled
init|=
literal|false
decl_stmt|;
specifier|public
name|Wiki
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
name|iEnabled
operator|=
name|ApplicationProperty
operator|.
name|PageHelpEnabled
operator|.
name|isTrue
argument_list|()
expr_stmt|;
name|iWikiUrl
operator|=
name|ApplicationProperty
operator|.
name|PageHelpUrl
operator|.
name|value
argument_list|()
expr_stmt|;
block|}
specifier|public
name|void
name|setWikiUrl
parameter_list|(
name|String
name|wikiUrl
parameter_list|)
block|{
name|iWikiUrl
operator|=
name|wikiUrl
expr_stmt|;
block|}
specifier|public
name|String
name|getWikiUrl
parameter_list|()
block|{
return|return
name|iWikiUrl
return|;
block|}
specifier|public
name|boolean
name|isEnabled
parameter_list|()
block|{
return|return
name|iEnabled
return|;
block|}
specifier|public
name|void
name|setEnabled
parameter_list|(
name|boolean
name|enabled
parameter_list|)
block|{
name|iEnabled
operator|=
name|enabled
expr_stmt|;
block|}
specifier|public
name|int
name|doStartTag
parameter_list|()
throws|throws
name|JspException
block|{
return|return
name|EVAL_BODY_BUFFERED
return|;
block|}
specifier|public
name|int
name|doEndTag
parameter_list|()
throws|throws
name|JspException
block|{
name|String
name|pageName
init|=
operator|(
name|getBodyContent
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
name|getBodyContent
argument_list|()
operator|.
name|getString
argument_list|()
operator|)
decl_stmt|;
if|if
condition|(
name|pageName
operator|==
literal|null
operator|||
name|pageName
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
return|return
name|EVAL_PAGE
return|;
name|StringBuffer
name|html
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
if|if
condition|(
name|isEnabled
argument_list|()
operator|&&
name|getWikiUrl
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|String
name|img
init|=
operator|(
operator|(
name|HttpServletRequest
operator|)
name|pageContext
operator|.
name|getRequest
argument_list|()
operator|)
operator|.
name|getContextPath
argument_list|()
operator|+
literal|"/images/help.png"
decl_stmt|;
name|String
name|url
init|=
name|getWikiUrl
argument_list|()
operator|+
name|pageName
operator|.
name|trim
argument_list|()
operator|.
name|replace
argument_list|(
literal|' '
argument_list|,
literal|'_'
argument_list|)
decl_stmt|;
name|html
operator|.
name|append
argument_list|(
literal|"<img border='0' align='top' src='"
operator|+
name|img
operator|+
literal|"' alt='"
operator|+
name|pageName
operator|.
name|trim
argument_list|()
operator|+
literal|" Help' "
operator|+
literal|"onMouseOver=\"this.style.cursor='hand';this.style.cursor='pointer';\" "
operator|+
literal|"onClick=\"showGwtDialog('"
operator|+
name|pageName
operator|.
name|trim
argument_list|()
operator|+
literal|" Help', '"
operator|+
name|url
operator|+
literal|"', '75%', '75%');\" "
operator|+
literal|"title='"
operator|+
name|pageName
operator|.
name|trim
argument_list|()
operator|+
literal|" Help'>"
argument_list|)
expr_stmt|;
block|}
try|try
block|{
name|pageContext
operator|.
name|getOut
argument_list|()
operator|.
name|print
argument_list|(
name|html
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|Debug
operator|.
name|error
argument_list|(
literal|"Could not display wiki help: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|EVAL_PAGE
return|;
block|}
block|}
end_class

end_unit

