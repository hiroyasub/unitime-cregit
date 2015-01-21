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
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|interfaces
operator|.
name|ExternalClassNameHelperInterface
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
name|Class_
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

begin_comment
comment|/**  * @author Stephanie Schluttenhofer, Tomas Muller  *  */
end_comment

begin_class
specifier|public
class|class
name|DefaultExternalClassNameHelper
implements|implements
name|ExternalClassNameHelperInterface
block|{
comment|/** 	 *  	 */
specifier|public
name|DefaultExternalClassNameHelper
parameter_list|()
block|{
comment|// do nothing
block|}
comment|/* (non-Javadoc) 	 * @see org.unitime.timetable.interfaces.ExternalClassNameHelperInterface#getClassLabel(org.unitime.timetable.model.Class_, org.unitime.timetable.model.CourseOffering) 	 */
specifier|public
name|String
name|getClassLabel
parameter_list|(
name|Class_
name|clazz
parameter_list|,
name|CourseOffering
name|courseOffering
parameter_list|)
block|{
return|return
name|courseOffering
operator|.
name|getCourseName
argument_list|()
operator|+
literal|" "
operator|+
name|clazz
operator|.
name|getItypeDesc
argument_list|()
operator|.
name|trim
argument_list|()
operator|+
literal|" "
operator|+
name|clazz
operator|.
name|getSectionNumberString
argument_list|()
return|;
block|}
comment|/* (non-Javadoc) 	 * @see org.unitime.timetable.interfaces.ExternalClassNameHelperInterface#getClassSuffix(org.unitime.timetable.model.Class_, org.unitime.timetable.model.CourseOffering) 	 */
specifier|public
name|String
name|getClassSuffix
parameter_list|(
name|Class_
name|clazz
parameter_list|,
name|CourseOffering
name|courseOffering
parameter_list|)
block|{
return|return
operator|(
name|clazz
operator|.
name|getClassSuffix
argument_list|()
operator|)
return|;
block|}
comment|/* (non-Javadoc) 	 * @see org.unitime.timetable.interfaces.ExternalClassNameHelperInterface#getClassLabelWithTitle(org.unitime.timetable.model.Class_, org.unitime.timetable.model.CourseOffering) 	 */
specifier|public
name|String
name|getClassLabelWithTitle
parameter_list|(
name|Class_
name|clazz
parameter_list|,
name|CourseOffering
name|courseOffering
parameter_list|)
block|{
return|return
name|courseOffering
operator|.
name|getCourseNameWithTitle
argument_list|()
operator|+
literal|" "
operator|+
name|clazz
operator|.
name|getItypeDesc
argument_list|()
operator|.
name|trim
argument_list|()
operator|+
literal|" "
operator|+
name|clazz
operator|.
name|getSectionNumberString
argument_list|()
return|;
block|}
comment|/* (non-Javadoc) 	 * @see org.unitime.timetable.interfaces.ExternalClassNameHelperInterface#getClassLabelWithTitle(org.unitime.timetable.model.Class_, org.unitime.timetable.model.CourseOffering) 	 */
specifier|public
name|String
name|getExternalId
parameter_list|(
name|Class_
name|clazz
parameter_list|,
name|CourseOffering
name|courseOffering
parameter_list|)
block|{
return|return
operator|(
name|clazz
operator|.
name|getExternalUniqueId
argument_list|()
operator|)
return|;
block|}
block|}
end_class

end_unit

