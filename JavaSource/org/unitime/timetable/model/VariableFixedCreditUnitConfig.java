begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.0 (University Course Timetabling& Student Sectioning Application)  * Copyright (C) 2007, UniTime.org, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program; if not, write to the Free Software Foundation, Inc.,  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|BaseVariableFixedCreditUnitConfig
import|;
end_import

begin_class
specifier|public
class|class
name|VariableFixedCreditUnitConfig
extends|extends
name|BaseVariableFixedCreditUnitConfig
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
specifier|static
name|String
name|CREDIT_FORMAT
init|=
literal|"variableMinMax"
decl_stmt|;
comment|/*[CONSTRUCTOR MARKER BEGIN]*/
specifier|public
name|VariableFixedCreditUnitConfig
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|VariableFixedCreditUnitConfig
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Long
name|uniqueId
parameter_list|)
block|{
name|super
argument_list|(
name|uniqueId
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * Constructor for required fields 	 */
specifier|public
name|VariableFixedCreditUnitConfig
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Long
name|uniqueId
parameter_list|,
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|CourseCreditType
name|creditType
parameter_list|,
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|CourseCreditUnitType
name|creditUnitType
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|String
name|creditFormat
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|Boolean
name|definesCreditAtCourseLevel
parameter_list|)
block|{
name|super
argument_list|(
name|uniqueId
argument_list|,
name|creditType
argument_list|,
name|creditUnitType
argument_list|,
name|creditFormat
argument_list|,
name|definesCreditAtCourseLevel
argument_list|)
expr_stmt|;
block|}
comment|/*[CONSTRUCTOR MARKER END]*/
specifier|public
name|String
name|creditText
parameter_list|()
block|{
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|sCreditFormat
operator|.
name|format
argument_list|(
name|this
operator|.
name|getMinUnits
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|" or "
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|sCreditFormat
operator|.
name|format
argument_list|(
name|this
operator|.
name|getMaxUnits
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|" "
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|this
operator|.
name|getCreditUnitType
argument_list|()
operator|.
name|getLabel
argument_list|()
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|" of "
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|this
operator|.
name|getCreditType
argument_list|()
operator|.
name|getLabel
argument_list|()
argument_list|)
expr_stmt|;
return|return
operator|(
name|sb
operator|.
name|toString
argument_list|()
operator|)
return|;
block|}
specifier|public
name|String
name|creditAbbv
parameter_list|()
block|{
return|return
operator|(
name|getCreditFormatAbbv
argument_list|()
operator|+
literal|" "
operator|+
name|sCreditFormat
operator|.
name|format
argument_list|(
name|getMinUnits
argument_list|()
argument_list|)
operator|+
literal|","
operator|+
name|sCreditFormat
operator|.
name|format
argument_list|(
name|getMaxUnits
argument_list|()
argument_list|)
operator|+
literal|" "
operator|+
name|getCreditUnitType
argument_list|()
operator|.
name|getAbbv
argument_list|()
operator|+
literal|" "
operator|+
name|getCreditType
argument_list|()
operator|.
name|getAbbv
argument_list|()
operator|)
operator|.
name|trim
argument_list|()
return|;
block|}
specifier|public
name|Object
name|clone
parameter_list|()
block|{
name|VariableFixedCreditUnitConfig
name|newCreditConfig
init|=
operator|new
name|VariableFixedCreditUnitConfig
argument_list|()
decl_stmt|;
name|baseClone
argument_list|(
name|newCreditConfig
argument_list|)
expr_stmt|;
name|newCreditConfig
operator|.
name|setMaxUnits
argument_list|(
name|getMaxUnits
argument_list|()
argument_list|)
expr_stmt|;
name|newCreditConfig
operator|.
name|setMinUnits
argument_list|(
name|getMinUnits
argument_list|()
argument_list|)
expr_stmt|;
return|return
operator|(
name|newCreditConfig
operator|)
return|;
block|}
block|}
end_class

end_unit

