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
name|tagext
operator|.
name|TagSupport
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|IsLtrTag
extends|extends
name|TagSupport
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|0l
decl_stmt|;
specifier|public
name|int
name|doStartTag
parameter_list|()
block|{
if|if
condition|(
operator|!
name|Localization
operator|.
name|isRTL
argument_list|()
condition|)
return|return
name|EVAL_BODY_INCLUDE
return|;
return|return
name|SKIP_BODY
return|;
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

