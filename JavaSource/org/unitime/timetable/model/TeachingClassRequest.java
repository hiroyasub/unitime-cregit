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
name|model
package|;
end_package

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
name|base
operator|.
name|BaseTeachingClassRequest
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
name|comparators
operator|.
name|ClassComparator
import|;
end_import

begin_class
specifier|public
class|class
name|TeachingClassRequest
extends|extends
name|BaseTeachingClassRequest
implements|implements
name|Comparable
argument_list|<
name|TeachingClassRequest
argument_list|>
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
specifier|public
name|TeachingClassRequest
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
specifier|protected
name|boolean
name|isParentOf
parameter_list|(
name|TeachingClassRequest
name|r
parameter_list|)
block|{
return|return
name|getTeachingClass
argument_list|()
operator|.
name|isParentOf
argument_list|(
name|r
operator|.
name|getTeachingClass
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|compareTo
parameter_list|(
name|TeachingClassRequest
name|o
parameter_list|)
block|{
name|ClassComparator
name|cmp
init|=
operator|new
name|ClassComparator
argument_list|(
name|ClassComparator
operator|.
name|COMPARE_BY_HIERARCHY
argument_list|)
decl_stmt|;
return|return
name|cmp
operator|.
name|compare
argument_list|(
name|getTeachingClass
argument_list|()
argument_list|,
name|o
operator|.
name|getTeachingClass
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|getTeachingClass
argument_list|()
operator|.
name|getItypeDesc
argument_list|()
operator|.
name|trim
argument_list|()
operator|+
literal|" "
operator|+
name|getTeachingClass
argument_list|()
operator|.
name|getSectionNumberString
argument_list|()
return|;
block|}
block|}
end_class

end_unit

