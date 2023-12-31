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
name|commons
operator|.
name|web
operator|.
name|htmlgen
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
name|jsp
operator|.
name|JspWriter
import|;
end_import

begin_comment
comment|/**  *   * @author Stephanie Schluttenhofer  *  */
end_comment

begin_class
specifier|public
class|class
name|TableStream
extends|extends
name|ScrollTable
block|{
name|JspWriter
name|outStream
decl_stmt|;
specifier|public
name|TableStream
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
specifier|public
name|TableStream
parameter_list|(
name|JspWriter
name|out
parameter_list|)
block|{
name|super
argument_list|()
expr_stmt|;
name|outStream
operator|=
name|out
expr_stmt|;
block|}
specifier|public
name|JspWriter
name|getOutStream
parameter_list|()
block|{
return|return
name|outStream
return|;
block|}
specifier|public
name|void
name|setOutStream
parameter_list|(
name|JspWriter
name|outStream
parameter_list|)
block|{
name|this
operator|.
name|outStream
operator|=
name|outStream
expr_stmt|;
block|}
specifier|public
name|void
name|addContent
parameter_list|(
name|Object
name|obj
parameter_list|)
block|{
try|try
block|{
name|getOutStream
argument_list|()
operator|.
name|print
argument_list|(
name|htmlForObject
argument_list|(
name|obj
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
specifier|public
name|void
name|tableDefComplete
parameter_list|()
block|{
try|try
block|{
name|getOutStream
argument_list|()
operator|.
name|print
argument_list|(
name|startTagHtml
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
specifier|public
name|void
name|tableComplete
parameter_list|()
block|{
try|try
block|{
name|getOutStream
argument_list|()
operator|.
name|print
argument_list|(
name|endTagHtml
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

