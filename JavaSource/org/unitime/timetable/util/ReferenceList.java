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

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
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
name|model
operator|.
name|RefTableEntry
import|;
end_import

begin_comment
comment|/**  * @author Dagmar Murray, Tomas Muller  *  */
end_comment

begin_class
specifier|public
class|class
name|ReferenceList
extends|extends
name|ArrayList
argument_list|<
name|RefTableEntry
argument_list|>
block|{
comment|/** 	 * Comment for<code>serialVersionUID</code> 	 */
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|3760561970914801209L
decl_stmt|;
specifier|public
name|String
name|labelForReference
parameter_list|(
name|String
name|ref
parameter_list|)
block|{
for|for
control|(
name|RefTableEntry
name|r
range|:
name|this
control|)
block|{
if|if
condition|(
name|r
operator|.
name|getReference
argument_list|()
operator|.
name|equals
argument_list|(
name|ref
argument_list|)
condition|)
return|return
name|r
operator|.
name|getLabel
argument_list|()
return|;
block|}
return|return
literal|null
return|;
block|}
block|}
end_class

end_unit

