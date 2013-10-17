begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 - 3.5 (University Timetabling Application)  * Copyright (C) 2008 - 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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

