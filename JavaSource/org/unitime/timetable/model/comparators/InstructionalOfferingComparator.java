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
operator|.
name|comparators
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Comparator
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
name|CourseOffering
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
name|InstructionalOffering
import|;
end_import

begin_comment
comment|/**  *  @author Heston Fernandes  */
end_comment

begin_class
specifier|public
class|class
name|InstructionalOfferingComparator
implements|implements
name|Comparator
block|{
specifier|private
name|Long
name|subjectUID
decl_stmt|;
specifier|public
name|InstructionalOfferingComparator
parameter_list|(
name|Long
name|subjectUID
parameter_list|)
block|{
name|this
operator|.
name|subjectUID
operator|=
name|subjectUID
expr_stmt|;
block|}
specifier|public
name|int
name|compare
parameter_list|(
name|Object
name|o1
parameter_list|,
name|Object
name|o2
parameter_list|)
block|{
name|InstructionalOffering
name|i1
init|=
operator|(
name|InstructionalOffering
operator|)
name|o1
decl_stmt|;
name|InstructionalOffering
name|i2
init|=
operator|(
name|InstructionalOffering
operator|)
name|o2
decl_stmt|;
if|if
condition|(
name|i1
operator|.
name|getCourseOfferings
argument_list|()
operator|==
literal|null
operator|||
name|i1
operator|.
name|getCourseOfferings
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
throw|throw
operator|new
name|IndexOutOfBoundsException
argument_list|(
literal|"i1 - Instructional Offering must have at least on Course Offering"
argument_list|)
throw|;
if|if
condition|(
name|i2
operator|.
name|getCourseOfferings
argument_list|()
operator|==
literal|null
operator|||
name|i2
operator|.
name|getCourseOfferings
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
throw|throw
operator|new
name|IndexOutOfBoundsException
argument_list|(
literal|"i2 - Instructional Offering must have at least on Course Offering"
argument_list|)
throw|;
if|if
condition|(
name|i1
operator|.
name|getUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
name|i2
operator|.
name|getUniqueId
argument_list|()
argument_list|)
condition|)
return|return
literal|0
return|;
name|CourseOffering
name|co1
init|=
name|i1
operator|.
name|findSortCourseOfferingForSubjectArea
argument_list|(
name|getSubjectUID
argument_list|()
argument_list|)
decl_stmt|;
name|CourseOffering
name|co2
init|=
name|i2
operator|.
name|findSortCourseOfferingForSubjectArea
argument_list|(
name|getSubjectUID
argument_list|()
argument_list|)
decl_stmt|;
name|int
name|cmp
init|=
name|co1
operator|.
name|getSubjectAreaAbbv
argument_list|()
operator|.
name|compareTo
argument_list|(
name|co2
operator|.
name|getSubjectAreaAbbv
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|cmp
operator|!=
literal|0
condition|)
return|return
name|cmp
return|;
name|cmp
operator|=
operator|(
name|co1
operator|.
name|getCourseNbr
argument_list|()
operator|.
name|compareTo
argument_list|(
name|co2
operator|.
name|getCourseNbr
argument_list|()
argument_list|)
operator|)
expr_stmt|;
if|if
condition|(
name|cmp
operator|!=
literal|0
condition|)
return|return
name|cmp
return|;
return|return
name|co1
operator|.
name|getUniqueId
argument_list|()
operator|.
name|compareTo
argument_list|(
name|co2
operator|.
name|getUniqueId
argument_list|()
argument_list|)
return|;
block|}
comment|/**      * @return Returns the subjectUID.      */
specifier|public
name|Long
name|getSubjectUID
parameter_list|()
block|{
return|return
name|subjectUID
return|;
block|}
comment|/**      * @param subjectUID      *            The subjectUID to set.      */
specifier|public
name|void
name|setSubjectUID
parameter_list|(
name|Long
name|subjectUID
parameter_list|)
block|{
name|this
operator|.
name|subjectUID
operator|=
name|subjectUID
expr_stmt|;
block|}
block|}
end_class

end_unit

