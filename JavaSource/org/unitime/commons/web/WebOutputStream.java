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
package|;
end_package

begin_comment
comment|/** Simple extension of java.io.OutputStream. \n character is replaced by&lt;br&gt;  *  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|WebOutputStream
extends|extends
name|java
operator|.
name|io
operator|.
name|OutputStream
block|{
comment|/** buffer */
name|StringBuffer
name|iBuffer
init|=
literal|null
decl_stmt|;
comment|/** constructor */
specifier|public
name|WebOutputStream
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
name|iBuffer
operator|=
operator|new
name|StringBuffer
argument_list|()
expr_stmt|;
block|}
comment|/** writes a byte to stream */
specifier|public
name|void
name|write
parameter_list|(
name|int
name|b
parameter_list|)
throws|throws
name|java
operator|.
name|io
operator|.
name|IOException
block|{
if|if
condition|(
name|b
operator|==
literal|'\n'
condition|)
block|{
name|iBuffer
operator|.
name|append
argument_list|(
literal|"<br>"
argument_list|)
expr_stmt|;
block|}
name|iBuffer
operator|.
name|append
argument_list|(
operator|(
name|char
operator|)
name|b
argument_list|)
expr_stmt|;
block|}
comment|/** returns content -- characters \n are replaced by tag&lt;br&gt; */
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|iBuffer
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
end_class

end_unit

