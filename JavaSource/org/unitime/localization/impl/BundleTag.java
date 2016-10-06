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
name|localization
operator|.
name|impl
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

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|localization
operator|.
name|messages
operator|.
name|Messages
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|localization
operator|.
name|impl
operator|.
name|Localization
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|BundleTag
extends|extends
name|BodyTagSupport
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|8594907730878329848L
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|DEFAULT_ID
init|=
literal|"MSG"
decl_stmt|;
specifier|private
name|String
name|iName
init|=
literal|null
decl_stmt|;
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|iName
return|;
block|}
specifier|public
name|void
name|setName
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|iName
operator|=
name|name
expr_stmt|;
block|}
specifier|private
name|String
name|iId
init|=
name|DEFAULT_ID
decl_stmt|;
specifier|public
name|String
name|getId
parameter_list|()
block|{
return|return
name|iId
return|;
block|}
specifier|public
name|void
name|setId
parameter_list|(
name|String
name|id
parameter_list|)
block|{
name|iId
operator|=
name|id
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|doStartTag
parameter_list|()
throws|throws
name|JspTagException
block|{
try|try
block|{
try|try
block|{
name|pageContext
operator|.
name|setAttribute
argument_list|(
name|getId
argument_list|()
argument_list|,
name|Localization
operator|.
name|create
argument_list|(
operator|(
name|Class
argument_list|<
name|?
extends|extends
name|Messages
argument_list|>
operator|)
name|Class
operator|.
name|forName
argument_list|(
name|Localization
operator|.
name|ROOT
operator|+
name|getName
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ClassNotFoundException
name|f
parameter_list|)
block|{
name|pageContext
operator|.
name|setAttribute
argument_list|(
name|getId
argument_list|()
argument_list|,
name|Localization
operator|.
name|create
argument_list|(
operator|(
name|Class
argument_list|<
name|?
extends|extends
name|Messages
argument_list|>
operator|)
name|Class
operator|.
name|forName
argument_list|(
name|getName
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
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
literal|"Unable to find messages '"
operator|+
name|getName
argument_list|()
operator|+
literal|"': "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
block|}
return|return
name|EVAL_BODY_INCLUDE
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|doEndTag
parameter_list|()
block|{
return|return
name|EVAL_PAGE
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|doAfterBody
parameter_list|()
block|{
return|return
name|SKIP_BODY
return|;
block|}
block|}
end_class

end_unit

