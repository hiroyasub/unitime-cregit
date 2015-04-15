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
name|ServletRequest
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
name|TagSupport
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|Globals
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
name|SessionContext
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
name|UserAuthority
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
name|context
operator|.
name|HttpSessionContext
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
name|rights
operator|.
name|Right
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|PageWarning
extends|extends
name|TagSupport
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
specifier|private
name|String
name|iPrefix
decl_stmt|;
specifier|private
name|String
name|iStyle
decl_stmt|;
specifier|private
name|String
name|iPage
decl_stmt|;
specifier|public
name|String
name|getPrefix
parameter_list|()
block|{
return|return
name|iPrefix
return|;
block|}
specifier|public
name|void
name|setPrefix
parameter_list|(
name|String
name|prefix
parameter_list|)
block|{
name|iPrefix
operator|=
name|prefix
expr_stmt|;
block|}
specifier|public
name|String
name|getStyle
parameter_list|()
block|{
return|return
name|iStyle
return|;
block|}
specifier|public
name|void
name|setStyle
parameter_list|(
name|String
name|styleName
parameter_list|)
block|{
name|iStyle
operator|=
name|styleName
expr_stmt|;
block|}
specifier|public
name|String
name|getPage
parameter_list|()
block|{
return|return
name|iPage
return|;
block|}
specifier|public
name|void
name|setPage
parameter_list|(
name|String
name|page
parameter_list|)
block|{
name|iPage
operator|=
name|page
expr_stmt|;
block|}
specifier|protected
name|SessionContext
name|getSessionContext
parameter_list|()
block|{
return|return
name|HttpSessionContext
operator|.
name|getSessionContext
argument_list|(
name|pageContext
operator|.
name|getServletContext
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|String
name|getPageWarning
parameter_list|(
name|ServletRequest
name|request
parameter_list|)
block|{
if|if
condition|(
name|iPage
operator|!=
literal|null
operator|&&
operator|!
name|iPage
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|String
name|warning
init|=
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
name|getPrefix
argument_list|()
operator|+
name|iPage
argument_list|)
decl_stmt|;
if|if
condition|(
name|warning
operator|!=
literal|null
operator|&&
operator|!
name|warning
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
name|warning
return|;
block|}
else|else
block|{
name|String
name|page
init|=
name|request
operator|.
name|getParameter
argument_list|(
literal|"page"
argument_list|)
decl_stmt|;
if|if
condition|(
name|page
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
literal|"admin"
operator|.
name|equals
argument_list|(
name|page
argument_list|)
condition|)
block|{
name|String
name|type
init|=
name|request
operator|.
name|getParameter
argument_list|(
literal|"type"
argument_list|)
decl_stmt|;
if|if
condition|(
name|type
operator|!=
literal|null
condition|)
block|{
name|String
name|warning
init|=
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
name|getPrefix
argument_list|()
operator|+
name|page
operator|+
literal|"."
operator|+
name|type
argument_list|)
decl_stmt|;
if|if
condition|(
name|warning
operator|!=
literal|null
operator|&&
operator|!
name|warning
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
name|warning
return|;
block|}
block|}
name|String
name|warning
init|=
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
name|getPrefix
argument_list|()
operator|+
name|page
argument_list|)
decl_stmt|;
if|if
condition|(
name|warning
operator|!=
literal|null
operator|&&
operator|!
name|warning
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
name|warning
return|;
block|}
name|String
name|action
init|=
operator|(
name|String
operator|)
name|request
operator|.
name|getAttribute
argument_list|(
name|Globals
operator|.
name|ORIGINAL_URI_KEY
argument_list|)
decl_stmt|;
if|if
condition|(
name|action
operator|!=
literal|null
operator|&&
name|action
operator|.
name|endsWith
argument_list|(
literal|".do"
argument_list|)
condition|)
block|{
name|String
name|warning
init|=
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
name|getPrefix
argument_list|()
operator|+
name|action
operator|.
name|substring
argument_list|(
name|action
operator|.
name|lastIndexOf
argument_list|(
literal|'/'
argument_list|)
operator|+
literal|1
argument_list|,
name|action
operator|.
name|length
argument_list|()
operator|-
literal|3
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|warning
operator|!=
literal|null
operator|&&
operator|!
name|warning
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
name|warning
return|;
block|}
block|}
name|SessionContext
name|context
init|=
name|getSessionContext
argument_list|()
decl_stmt|;
name|UserContext
name|user
init|=
operator|(
name|context
operator|!=
literal|null
operator|&&
name|context
operator|.
name|isAuthenticated
argument_list|()
condition|?
name|context
operator|.
name|getUser
argument_list|()
else|:
literal|null
operator|)
decl_stmt|;
name|UserAuthority
name|authority
init|=
operator|(
name|user
operator|!=
literal|null
condition|?
name|user
operator|.
name|getCurrentAuthority
argument_list|()
else|:
literal|null
operator|)
decl_stmt|;
name|String
name|role
init|=
operator|(
name|authority
operator|!=
literal|null
condition|?
name|authority
operator|.
name|getRole
argument_list|()
else|:
literal|null
operator|)
decl_stmt|;
if|if
condition|(
name|role
operator|!=
literal|null
condition|)
block|{
name|String
name|warning
init|=
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
name|getPrefix
argument_list|()
operator|+
literal|"role."
operator|+
name|role
argument_list|)
decl_stmt|;
if|if
condition|(
name|warning
operator|!=
literal|null
operator|&&
operator|!
name|warning
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
name|warning
return|;
block|}
if|if
condition|(
name|authority
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|Right
name|right
range|:
name|Right
operator|.
name|values
argument_list|()
control|)
if|if
condition|(
name|authority
operator|.
name|hasRight
argument_list|(
name|right
argument_list|)
condition|)
block|{
name|String
name|warning
init|=
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
name|getPrefix
argument_list|()
operator|+
literal|"permission."
operator|+
name|right
operator|.
name|name
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|warning
operator|!=
literal|null
operator|&&
operator|!
name|warning
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
name|warning
return|;
block|}
block|}
return|return
literal|null
return|;
block|}
specifier|public
name|int
name|doStartTag
parameter_list|()
block|{
try|try
block|{
name|String
name|warning
init|=
name|getPageWarning
argument_list|(
name|pageContext
operator|.
name|getRequest
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|warning
operator|!=
literal|null
condition|)
block|{
name|pageContext
operator|.
name|getOut
argument_list|()
operator|.
name|println
argument_list|(
literal|"<div class=\""
operator|+
name|getStyle
argument_list|()
operator|+
literal|"\">"
argument_list|)
expr_stmt|;
name|pageContext
operator|.
name|getOut
argument_list|()
operator|.
name|println
argument_list|(
name|warning
argument_list|)
expr_stmt|;
name|pageContext
operator|.
name|getOut
argument_list|()
operator|.
name|println
argument_list|(
literal|"</div>"
argument_list|)
expr_stmt|;
block|}
return|return
name|SKIP_BODY
return|;
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
name|e
argument_list|)
expr_stmt|;
return|return
name|SKIP_BODY
return|;
block|}
block|}
specifier|public
name|int
name|doEndTag
parameter_list|()
block|{
return|return
name|EVAL_PAGE
return|;
block|}
block|}
end_class

end_unit

