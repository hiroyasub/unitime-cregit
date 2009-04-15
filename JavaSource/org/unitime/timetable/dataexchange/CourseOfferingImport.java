begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*   * UniTime 3.1 (University Course Timetabling& Student Sectioning Application)  * Copyright (C) 2008-2009, UniTime LLC  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program; if not, write to the Free Software Foundation, Inc.,  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
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
comment|/**  * @author says  *  */
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
block|}
end_class

end_unit

