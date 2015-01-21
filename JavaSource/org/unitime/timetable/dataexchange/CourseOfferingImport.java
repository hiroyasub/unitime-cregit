begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*   * Licensed to The Apereo Foundation under one or more contributor license  * agreements. See the NOTICE file distributed with this work for  * additional information regarding copyright ownership.  *  * The Apereo Foundation licenses this file to you under the Apache License,  * Version 2.0 (the "License"); you may not use this file except in  * compliance with the License. You may obtain a copy of the License at:  *  * http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *  * See the License for the specific language governing permissions and  * limitations under the License.  *   */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|dataexchange
package|;
end_package

begin_import
import|import
name|org
operator|.
name|dom4j
operator|.
name|Element
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
name|InstrOfferingConfig
import|;
end_import

begin_comment
comment|/**  * @author Stephanie Schluttenhofer  *  */
end_comment

begin_class
specifier|public
class|class
name|CourseOfferingImport
extends|extends
name|BaseCourseOfferingImport
block|{
comment|/** 	 *  	 */
specifier|public
name|CourseOfferingImport
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
name|rootElementName
operator|=
literal|"offerings"
expr_stmt|;
block|}
annotation|@
name|Override
specifier|protected
name|boolean
name|handleCustomCourseChildElements
parameter_list|(
name|CourseOffering
name|courseOffering
parameter_list|,
name|Element
name|courseOfferingElement
parameter_list|)
block|{
comment|// Core UniTime does not have any child elements for the course offering element
return|return
operator|(
literal|false
operator|)
return|;
block|}
annotation|@
name|Override
specifier|protected
name|boolean
name|handleCustomClassChildElements
parameter_list|(
name|Element
name|classElement
parameter_list|,
name|InstrOfferingConfig
name|ioc
parameter_list|,
name|Class_
name|clazz
parameter_list|)
block|{
comment|// Core UniTime does not have any child elements for the class element
return|return
literal|false
return|;
block|}
annotation|@
name|Override
specifier|protected
name|void
name|postLoadAction
parameter_list|()
block|{
comment|// Core UniTime does not implement the post load action
block|}
annotation|@
name|Override
specifier|protected
name|void
name|preLoadAction
parameter_list|()
block|{
comment|// Core UniTime does not implement the pre load action
block|}
annotation|@
name|Override
specifier|protected
name|boolean
name|handleCustomInstrOffrConfigChildElements
parameter_list|(
name|InstrOfferingConfig
name|instrOfferingConfig
parameter_list|,
name|Element
name|instrOfferingConfigElement
parameter_list|)
throws|throws
name|Exception
block|{
comment|// Core UniTime does not have any child elements for the instructional offering config element
return|return
literal|false
return|;
block|}
block|}
end_class

end_unit

