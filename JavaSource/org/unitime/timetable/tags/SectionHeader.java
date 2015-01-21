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
name|JspTagException
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

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|SectionHeader
extends|extends
name|BodyTagSupport
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|7960410331965348148L
decl_stmt|;
specifier|private
name|String
name|iTitle
init|=
literal|null
decl_stmt|;
specifier|public
name|String
name|getTitle
parameter_list|()
block|{
return|return
name|iTitle
return|;
block|}
specifier|public
name|void
name|setTitle
parameter_list|(
name|String
name|title
parameter_list|)
block|{
name|iTitle
operator|=
name|title
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
try|try
block|{
name|pageContext
operator|.
name|getOut
argument_list|()
operator|.
name|println
argument_list|(
literal|"<table class='BottomBorder' width='100%'><tr><td width='100%' nowrap>"
argument_list|)
expr_stmt|;
name|pageContext
operator|.
name|getOut
argument_list|()
operator|.
name|println
argument_list|(
literal|"<DIV class='WelcomeRowHeadNoLine'>"
argument_list|)
expr_stmt|;
if|if
condition|(
name|iTitle
operator|!=
literal|null
condition|)
name|pageContext
operator|.
name|getOut
argument_list|()
operator|.
name|println
argument_list|(
name|iTitle
argument_list|)
expr_stmt|;
name|pageContext
operator|.
name|getOut
argument_list|()
operator|.
name|println
argument_list|(
literal|"</DIV>"
argument_list|)
expr_stmt|;
name|pageContext
operator|.
name|getOut
argument_list|()
operator|.
name|println
argument_list|(
literal|"</td><td style='padding-bottom: 3px' nowrap>"
argument_list|)
expr_stmt|;
name|String
name|body
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
name|body
operator|!=
literal|null
condition|)
name|pageContext
operator|.
name|getOut
argument_list|()
operator|.
name|println
argument_list|(
name|body
argument_list|)
expr_stmt|;
name|pageContext
operator|.
name|getOut
argument_list|()
operator|.
name|println
argument_list|(
literal|"</td></tr></table>"
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|JspTagException
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
throw|;
block|}
return|return
name|EVAL_PAGE
return|;
block|}
block|}
end_class

end_unit

