begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.1 (University Timetabling Application)  * Copyright (C) 2008, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program; if not, write to the Free Software Foundation, Inc.,  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|form
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
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|http
operator|.
name|HttpServletRequest
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionErrors
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionMapping
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionMessage
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
name|AcadAreaReservation
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
name|util
operator|.
name|Constants
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
name|util
operator|.
name|DynamicList
import|;
end_import

begin_comment
comment|/**   * MyEclipse Struts  * Creation date: 09-01-2006  *   * XDoclet definition:  * @struts:form name="academicAreaReservationEditForm"  */
end_comment

begin_class
specifier|public
class|class
name|AcademicAreaReservationEditForm
extends|extends
name|AcademicAreaPosReservationForm
block|{
comment|// --------------------------------------------------------- Instance Variables
specifier|private
name|List
name|academicAreaId
decl_stmt|;
comment|// --------------------------------------------------------- Methods
comment|/**       * Method validate      * @param mapping      * @param request      * @return ActionErrors      */
specifier|public
name|ActionErrors
name|validate
parameter_list|(
name|ActionMapping
name|mapping
parameter_list|,
name|HttpServletRequest
name|request
parameter_list|)
block|{
name|ActionErrors
name|errors
init|=
name|super
operator|.
name|validate
argument_list|(
name|mapping
argument_list|,
name|request
argument_list|)
decl_stmt|;
name|ArrayList
name|l
init|=
operator|new
name|ArrayList
argument_list|()
decl_stmt|;
name|List
name|academicClassificationId
init|=
name|super
operator|.
name|getAcademicClassificationId
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|academicAreaId
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|l
operator|.
name|add
argument_list|(
name|academicAreaId
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|.
name|toString
argument_list|()
operator|+
name|academicClassificationId
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|checkList
argument_list|(
name|l
argument_list|,
literal|false
argument_list|)
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"academicAreaId"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
literal|"Invalid Academic Area / Classification: Check for duplicate / blank values. "
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|errors
return|;
block|}
comment|/**       * Method reset      * @param mapping      * @param request      */
specifier|public
name|void
name|reset
parameter_list|(
name|ActionMapping
name|mapping
parameter_list|,
name|HttpServletRequest
name|request
parameter_list|)
block|{
name|super
operator|.
name|reset
argument_list|(
name|mapping
argument_list|,
name|request
argument_list|)
expr_stmt|;
name|academicAreaId
operator|=
name|DynamicList
operator|.
name|getInstance
argument_list|(
operator|new
name|ArrayList
argument_list|()
argument_list|,
name|factoryResv
argument_list|)
expr_stmt|;
block|}
specifier|public
name|List
name|getAcademicAreaId
parameter_list|()
block|{
return|return
name|academicAreaId
return|;
block|}
specifier|public
name|void
name|setAcademicAreaId
parameter_list|(
name|List
name|academicAreaId
parameter_list|)
block|{
name|this
operator|.
name|academicAreaId
operator|=
name|academicAreaId
expr_stmt|;
block|}
specifier|public
name|String
name|getAcademicAreaId
parameter_list|(
name|int
name|key
parameter_list|)
block|{
return|return
name|academicAreaId
operator|.
name|get
argument_list|(
name|key
argument_list|)
operator|.
name|toString
argument_list|()
return|;
block|}
specifier|public
name|void
name|setAcademicAreaId
parameter_list|(
name|int
name|key
parameter_list|,
name|Object
name|value
parameter_list|)
block|{
name|this
operator|.
name|academicAreaId
operator|.
name|set
argument_list|(
name|key
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|addToAcademicAreaId
parameter_list|(
name|String
name|academicAreaId
parameter_list|)
block|{
name|this
operator|.
name|academicAreaId
operator|.
name|add
argument_list|(
name|academicAreaId
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|addBlankRows
parameter_list|()
block|{
name|super
operator|.
name|addBlankRows
argument_list|()
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|RESV_ROWS_ADDED
condition|;
name|i
operator|++
control|)
block|{
name|addToAcademicAreaId
argument_list|(
name|Constants
operator|.
name|BLANK_OPTION_VALUE
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|void
name|clear
parameter_list|()
block|{
name|super
operator|.
name|clear
argument_list|()
expr_stmt|;
name|this
operator|.
name|academicAreaId
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
specifier|public
name|void
name|addReservation
parameter_list|(
name|AcadAreaReservation
name|resv
parameter_list|)
block|{
name|super
operator|.
name|addReservation
argument_list|(
name|resv
argument_list|)
expr_stmt|;
name|addToAcademicAreaId
argument_list|(
name|resv
operator|.
name|getAcademicArea
argument_list|()
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|removeRow
parameter_list|(
name|int
name|rowNum
parameter_list|)
block|{
if|if
condition|(
name|rowNum
operator|>=
literal|0
condition|)
block|{
name|super
operator|.
name|removeRow
argument_list|(
name|rowNum
argument_list|)
expr_stmt|;
name|academicAreaId
operator|.
name|remove
argument_list|(
name|rowNum
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

