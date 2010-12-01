begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 (University Timetabling Application)  * Copyright (C) 2008 - 2010, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|AcadAreaPosReservation
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
comment|/**  * Subclasses: AcademicAreaReservation, and PosReservation forms  *   * @author Heston Fernandes  */
end_comment

begin_class
specifier|public
class|class
name|AcademicAreaPosReservationForm
extends|extends
name|CharacteristicReservationForm
block|{
comment|// --------------------------------------------------------- Instance Variables
comment|/** 	 *  	 */
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|2482481549947035012L
decl_stmt|;
specifier|private
name|List
name|academicClassificationId
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
name|academicClassificationId
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
name|getAcademicClassificationId
parameter_list|()
block|{
return|return
name|academicClassificationId
return|;
block|}
specifier|public
name|void
name|setAcademicClassificationId
parameter_list|(
name|List
name|academicClassificationId
parameter_list|)
block|{
name|this
operator|.
name|academicClassificationId
operator|=
name|academicClassificationId
expr_stmt|;
block|}
specifier|public
name|String
name|getAcademicClassificationId
parameter_list|(
name|int
name|key
parameter_list|)
block|{
return|return
name|academicClassificationId
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
name|setAcademicClassificationId
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
name|academicClassificationId
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
name|addToAcademicClassificationId
parameter_list|(
name|String
name|academicClassificationId
parameter_list|)
block|{
name|this
operator|.
name|academicClassificationId
operator|.
name|add
argument_list|(
name|academicClassificationId
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
name|addToAcademicClassificationId
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
name|academicClassificationId
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
specifier|public
name|void
name|addReservation
parameter_list|(
name|AcadAreaPosReservation
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
if|if
condition|(
name|resv
operator|.
name|getAcademicClassification
argument_list|()
operator|!=
literal|null
condition|)
name|addToAcademicClassificationId
argument_list|(
name|resv
operator|.
name|getAcademicClassification
argument_list|()
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
else|else
name|addToAcademicClassificationId
argument_list|(
literal|""
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
name|academicClassificationId
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

