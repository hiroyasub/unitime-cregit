begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 - 3.5 (University Timetabling Application)  * Copyright (C) 2010 - 2014, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *  * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|base
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
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
name|CourseCreditUnitConfig
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
name|dao
operator|.
name|_RootDAO
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
name|dao
operator|.
name|CourseCreditUnitConfigDAO
import|;
end_import

begin_comment
comment|/**  * Do not change this class. It has been automatically generated using ant create-model.  * @see org.unitime.commons.ant.CreateBaseModelFromXml  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|BaseCourseCreditUnitConfigDAO
extends|extends
name|_RootDAO
argument_list|<
name|CourseCreditUnitConfig
argument_list|,
name|Long
argument_list|>
block|{
specifier|private
specifier|static
name|CourseCreditUnitConfigDAO
name|sInstance
decl_stmt|;
specifier|public
specifier|static
name|CourseCreditUnitConfigDAO
name|getInstance
parameter_list|()
block|{
if|if
condition|(
name|sInstance
operator|==
literal|null
condition|)
name|sInstance
operator|=
operator|new
name|CourseCreditUnitConfigDAO
argument_list|()
expr_stmt|;
return|return
name|sInstance
return|;
block|}
specifier|public
name|Class
argument_list|<
name|CourseCreditUnitConfig
argument_list|>
name|getReferenceClass
parameter_list|()
block|{
return|return
name|CourseCreditUnitConfig
operator|.
name|class
return|;
block|}
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
specifier|public
name|List
argument_list|<
name|CourseCreditUnitConfig
argument_list|>
name|findByCourseCreditFormat
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|Long
name|courseCreditFormatId
parameter_list|)
block|{
return|return
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"from CourseCreditUnitConfig x where x.courseCreditFormat.uniqueId = :courseCreditFormatId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"courseCreditFormatId"
argument_list|,
name|courseCreditFormatId
argument_list|)
operator|.
name|list
argument_list|()
return|;
block|}
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
specifier|public
name|List
argument_list|<
name|CourseCreditUnitConfig
argument_list|>
name|findByCreditType
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|Long
name|creditTypeId
parameter_list|)
block|{
return|return
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"from CourseCreditUnitConfig x where x.creditType.uniqueId = :creditTypeId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"creditTypeId"
argument_list|,
name|creditTypeId
argument_list|)
operator|.
name|list
argument_list|()
return|;
block|}
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
specifier|public
name|List
argument_list|<
name|CourseCreditUnitConfig
argument_list|>
name|findByCreditUnitType
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|Long
name|creditUnitTypeId
parameter_list|)
block|{
return|return
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"from CourseCreditUnitConfig x where x.creditUnitType.uniqueId = :creditUnitTypeId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"creditUnitTypeId"
argument_list|,
name|creditUnitTypeId
argument_list|)
operator|.
name|list
argument_list|()
return|;
block|}
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
specifier|public
name|List
argument_list|<
name|CourseCreditUnitConfig
argument_list|>
name|findBySubpartOwner
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|Long
name|subpartOwnerId
parameter_list|)
block|{
return|return
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"from CourseCreditUnitConfig x where x.subpartOwner.uniqueId = :subpartOwnerId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"subpartOwnerId"
argument_list|,
name|subpartOwnerId
argument_list|)
operator|.
name|list
argument_list|()
return|;
block|}
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
specifier|public
name|List
argument_list|<
name|CourseCreditUnitConfig
argument_list|>
name|findByInstructionalOfferingOwner
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|Long
name|instructionalOfferingOwnerId
parameter_list|)
block|{
return|return
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"from CourseCreditUnitConfig x where x.instructionalOfferingOwner.uniqueId = :instructionalOfferingOwnerId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"instructionalOfferingOwnerId"
argument_list|,
name|instructionalOfferingOwnerId
argument_list|)
operator|.
name|list
argument_list|()
return|;
block|}
block|}
end_class

end_unit

