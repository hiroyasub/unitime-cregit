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
name|java
operator|.
name|util
operator|.
name|Iterator
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
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionMessage
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
name|action
operator|.
name|ActionMessages
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
name|taglib
operator|.
name|TagUtils
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
name|taglib
operator|.
name|html
operator|.
name|TextTag
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|CourseNumberSuggestBox
extends|extends
name|TextTag
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
name|iOuterStyle
init|=
literal|null
decl_stmt|;
specifier|private
name|String
name|iConfiguration
init|=
literal|null
decl_stmt|;
specifier|public
name|String
name|getOuterStyle
parameter_list|()
block|{
return|return
name|iOuterStyle
return|;
block|}
specifier|public
name|void
name|setOuterStyle
parameter_list|(
name|String
name|outerStyle
parameter_list|)
block|{
name|iOuterStyle
operator|=
name|outerStyle
expr_stmt|;
block|}
specifier|public
name|String
name|getConfiguration
parameter_list|()
block|{
return|return
name|iConfiguration
return|;
block|}
specifier|public
name|void
name|setConfiguration
parameter_list|(
name|String
name|configuration
parameter_list|)
block|{
name|iConfiguration
operator|=
name|configuration
expr_stmt|;
block|}
specifier|public
name|int
name|doStartTag
parameter_list|()
throws|throws
name|JspException
block|{
name|ActionMessages
name|errors
init|=
literal|null
decl_stmt|;
try|try
block|{
name|errors
operator|=
name|TagUtils
operator|.
name|getInstance
argument_list|()
operator|.
name|getActionMessages
argument_list|(
name|pageContext
argument_list|,
name|Globals
operator|.
name|ERROR_KEY
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|JspException
name|e
parameter_list|)
block|{
name|TagUtils
operator|.
name|getInstance
argument_list|()
operator|.
name|saveException
argument_list|(
name|pageContext
argument_list|,
name|e
argument_list|)
expr_stmt|;
throw|throw
name|e
throw|;
block|}
name|String
name|hint
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|errors
operator|!=
literal|null
operator|&&
operator|!
name|errors
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|String
name|message
init|=
literal|null
decl_stmt|;
name|Iterator
name|reports
init|=
operator|(
name|getProperty
argument_list|()
operator|==
literal|null
condition|?
name|errors
operator|.
name|get
argument_list|()
else|:
name|errors
operator|.
name|get
argument_list|(
name|getProperty
argument_list|()
argument_list|)
operator|)
decl_stmt|;
while|while
condition|(
name|reports
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|ActionMessage
name|report
init|=
operator|(
name|ActionMessage
operator|)
name|reports
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|report
operator|.
name|isResource
argument_list|()
condition|)
block|{
name|message
operator|=
name|TagUtils
operator|.
name|getInstance
argument_list|()
operator|.
name|message
argument_list|(
name|pageContext
argument_list|,
literal|null
argument_list|,
name|Globals
operator|.
name|LOCALE_KEY
argument_list|,
name|report
operator|.
name|getKey
argument_list|()
argument_list|,
name|report
operator|.
name|getValues
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|message
operator|=
name|report
operator|.
name|getKey
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|message
operator|!=
literal|null
operator|&&
operator|!
name|message
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|hint
operator|=
operator|(
name|hint
operator|==
literal|null
condition|?
literal|""
else|:
name|hint
operator|+
literal|"<br>"
operator|)
operator|+
name|message
expr_stmt|;
block|}
block|}
block|}
comment|// setStyleClass("unitime-DateSelectionBox");
name|String
name|onchange
init|=
name|getOnchange
argument_list|()
decl_stmt|;
name|setOnchange
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|TagUtils
operator|.
name|getInstance
argument_list|()
operator|.
name|write
argument_list|(
name|pageContext
argument_list|,
literal|"<span name='UniTimeGWT:CourseNumberSuggestBox' configuration=\""
operator|+
name|getConfiguration
argument_list|()
operator|+
literal|"\""
operator|+
operator|(
name|hint
operator|==
literal|null
condition|?
literal|""
else|:
literal|" error=\""
operator|+
name|hint
operator|+
literal|"\""
operator|)
operator|+
operator|(
name|onchange
operator|==
literal|null
condition|?
literal|""
else|:
literal|" onchange=\""
operator|+
name|onchange
operator|+
literal|"\""
operator|)
operator|+
operator|(
name|getOuterStyle
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
literal|" style=\""
operator|+
name|getOuterStyle
argument_list|()
operator|+
literal|"\""
operator|)
operator|+
literal|">"
argument_list|)
expr_stmt|;
name|super
operator|.
name|doStartTag
argument_list|()
expr_stmt|;
name|TagUtils
operator|.
name|getInstance
argument_list|()
operator|.
name|write
argument_list|(
name|pageContext
argument_list|,
literal|"</span>"
argument_list|)
expr_stmt|;
return|return
name|EVAL_BODY_BUFFERED
return|;
block|}
block|}
end_class

end_unit

