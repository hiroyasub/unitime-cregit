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
name|util
package|;
end_package

begin_comment
comment|/**  * Customized Message Resources   * @author Heston Fernandes, Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|MessageResourcesFactory
extends|extends
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|util
operator|.
name|MessageResourcesFactory
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
operator|-
literal|3170113345618008226L
decl_stmt|;
annotation|@
name|Override
specifier|public
name|MessageResources
name|createResources
parameter_list|(
name|String
name|config
parameter_list|)
block|{
return|return
operator|new
name|MessageResources
argument_list|(
name|this
argument_list|,
name|config
argument_list|,
name|this
operator|.
name|getReturnNull
argument_list|()
argument_list|)
return|;
block|}
block|}
end_class

end_unit

