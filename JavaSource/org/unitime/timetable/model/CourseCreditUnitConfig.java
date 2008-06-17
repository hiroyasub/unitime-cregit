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
name|model
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Locale
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
name|base
operator|.
name|BaseCourseCreditUnitConfig
import|;
end_import

begin_class
specifier|public
specifier|abstract
class|class
name|CourseCreditUnitConfig
extends|extends
name|BaseCourseCreditUnitConfig
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
specifier|protected
specifier|static
name|java
operator|.
name|text
operator|.
name|DecimalFormat
name|sCreditFormat
init|=
operator|new
name|java
operator|.
name|text
operator|.
name|DecimalFormat
argument_list|(
literal|"0.###"
argument_list|,
operator|new
name|java
operator|.
name|text
operator|.
name|DecimalFormatSymbols
argument_list|(
name|Locale
operator|.
name|US
argument_list|)
argument_list|)
decl_stmt|;
comment|/*[CONSTRUCTOR MARKER BEGIN]*/
specifier|public
name|CourseCreditUnitConfig
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|CourseCreditUnitConfig
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
name|CourseCreditUnitConfig
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
specifier|static
name|CourseCreditUnitConfig
name|createCreditUnitConfigOfFormat
parameter_list|(
name|String
name|creditFormat
parameter_list|,
name|String
name|creditType
parameter_list|,
name|String
name|creditUnitType
parameter_list|,
name|Float
name|units
parameter_list|,
name|Float
name|maxUnits
parameter_list|,
name|Boolean
name|fractionalIncrementsAllowed
parameter_list|,
name|Boolean
name|creditAtCourseLevel
parameter_list|)
block|{
if|if
condition|(
name|creditFormat
operator|==
literal|null
operator|||
name|creditFormat
operator|.
name|length
argument_list|()
operator|==
literal|0
operator||
name|creditType
operator|==
literal|null
operator||
name|creditUnitType
operator|==
literal|null
operator|||
name|creditType
operator|.
name|length
argument_list|()
operator|==
literal|0
operator||
name|creditUnitType
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
block|{
return|return
operator|(
literal|null
operator|)
return|;
block|}
name|CourseCreditType
name|cct
init|=
name|CourseCreditType
operator|.
name|getCourseCreditTypeForReference
argument_list|(
name|creditType
argument_list|)
decl_stmt|;
name|CourseCreditUnitType
name|ccut
init|=
name|CourseCreditUnitType
operator|.
name|getCourseCreditUnitTypeForReference
argument_list|(
name|creditUnitType
argument_list|)
decl_stmt|;
return|return
operator|(
name|createCreditUnitConfigOfFormat
argument_list|(
name|creditFormat
argument_list|,
name|cct
argument_list|,
name|ccut
argument_list|,
name|units
argument_list|,
name|maxUnits
argument_list|,
name|fractionalIncrementsAllowed
argument_list|,
name|creditAtCourseLevel
argument_list|)
operator|)
return|;
block|}
specifier|public
specifier|static
name|CourseCreditUnitConfig
name|createCreditUnitConfigOfFormat
parameter_list|(
name|String
name|creditFormat
parameter_list|,
name|Long
name|creditType
parameter_list|,
name|Long
name|creditUnitType
parameter_list|,
name|Float
name|units
parameter_list|,
name|Float
name|maxUnits
parameter_list|,
name|Boolean
name|fractionalIncrementsAllowed
parameter_list|,
name|Boolean
name|creditAtCourseLevel
parameter_list|)
block|{
if|if
condition|(
name|creditFormat
operator|==
literal|null
operator|||
name|creditFormat
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
block|{
return|return
operator|(
literal|null
operator|)
return|;
block|}
name|CourseCreditType
name|cct
init|=
name|CourseCreditType
operator|.
name|getCourseCreditTypeForUniqueId
argument_list|(
name|creditType
argument_list|)
decl_stmt|;
name|CourseCreditUnitType
name|ccut
init|=
name|CourseCreditUnitType
operator|.
name|getCourseCreditUnitTypeForUniqueId
argument_list|(
name|creditUnitType
argument_list|)
decl_stmt|;
return|return
operator|(
name|createCreditUnitConfigOfFormat
argument_list|(
name|creditFormat
argument_list|,
name|cct
argument_list|,
name|ccut
argument_list|,
name|units
argument_list|,
name|maxUnits
argument_list|,
name|fractionalIncrementsAllowed
argument_list|,
name|creditAtCourseLevel
argument_list|)
operator|)
return|;
block|}
specifier|public
specifier|static
name|CourseCreditUnitConfig
name|createCreditUnitConfigOfFormat
parameter_list|(
name|String
name|creditFormat
parameter_list|,
name|CourseCreditType
name|creditType
parameter_list|,
name|CourseCreditUnitType
name|creditUnitType
parameter_list|,
name|Float
name|units
parameter_list|,
name|Float
name|maxUnits
parameter_list|,
name|Boolean
name|fractionalIncrementsAllowed
parameter_list|,
name|Boolean
name|creditAtCourseLevel
parameter_list|)
block|{
if|if
condition|(
name|creditFormat
operator|==
literal|null
operator|||
name|creditFormat
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
block|{
return|return
operator|(
literal|null
operator|)
return|;
block|}
name|CourseCreditUnitConfig
name|ccuc
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|creditFormat
operator|.
name|equals
argument_list|(
name|FixedCreditUnitConfig
operator|.
name|CREDIT_FORMAT
argument_list|)
condition|)
block|{
name|FixedCreditUnitConfig
name|fcuc
init|=
operator|new
name|FixedCreditUnitConfig
argument_list|()
decl_stmt|;
name|fcuc
operator|.
name|setFixedUnits
argument_list|(
name|units
argument_list|)
expr_stmt|;
name|ccuc
operator|=
name|fcuc
expr_stmt|;
block|}
if|else if
condition|(
name|creditFormat
operator|.
name|equals
argument_list|(
name|VariableFixedCreditUnitConfig
operator|.
name|CREDIT_FORMAT
argument_list|)
condition|)
block|{
name|VariableFixedCreditUnitConfig
name|vfcuc
init|=
operator|new
name|VariableFixedCreditUnitConfig
argument_list|()
decl_stmt|;
name|vfcuc
operator|.
name|setMinUnits
argument_list|(
name|units
argument_list|)
expr_stmt|;
name|vfcuc
operator|.
name|setMaxUnits
argument_list|(
name|maxUnits
argument_list|)
expr_stmt|;
name|ccuc
operator|=
name|vfcuc
expr_stmt|;
block|}
if|else if
condition|(
name|creditFormat
operator|.
name|equals
argument_list|(
name|VariableRangeCreditUnitConfig
operator|.
name|CREDIT_FORMAT
argument_list|)
condition|)
block|{
name|VariableRangeCreditUnitConfig
name|vrcuc
init|=
operator|new
name|VariableRangeCreditUnitConfig
argument_list|()
decl_stmt|;
name|vrcuc
operator|.
name|setMinUnits
argument_list|(
name|units
argument_list|)
expr_stmt|;
name|vrcuc
operator|.
name|setMaxUnits
argument_list|(
name|maxUnits
argument_list|)
expr_stmt|;
name|vrcuc
operator|.
name|setFractionalIncrementsAllowed
argument_list|(
name|fractionalIncrementsAllowed
argument_list|)
expr_stmt|;
name|ccuc
operator|=
name|vrcuc
expr_stmt|;
block|}
if|else if
condition|(
name|creditFormat
operator|.
name|endsWith
argument_list|(
name|ArrangeCreditUnitConfig
operator|.
name|CREDIT_FORMAT
argument_list|)
condition|)
block|{
name|ccuc
operator|=
operator|new
name|ArrangeCreditUnitConfig
argument_list|()
expr_stmt|;
block|}
else|else
block|{
return|return
operator|(
literal|null
operator|)
return|;
block|}
name|ccuc
operator|.
name|setDefinesCreditAtCourseLevel
argument_list|(
name|creditAtCourseLevel
argument_list|)
expr_stmt|;
name|ccuc
operator|.
name|setCreditType
argument_list|(
name|creditType
argument_list|)
expr_stmt|;
name|ccuc
operator|.
name|setCreditUnitType
argument_list|(
name|creditUnitType
argument_list|)
expr_stmt|;
return|return
operator|(
name|ccuc
operator|)
return|;
block|}
specifier|public
specifier|abstract
name|String
name|creditText
parameter_list|()
function_decl|;
specifier|public
specifier|abstract
name|String
name|creditAbbv
parameter_list|()
function_decl|;
specifier|public
name|void
name|setOwner
parameter_list|(
name|SchedulingSubpart
name|schedulingSubpart
parameter_list|)
block|{
if|if
condition|(
operator|!
name|isDefinesCreditAtCourseLevel
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|)
block|{
name|setSubpartOwner
argument_list|(
name|schedulingSubpart
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|void
name|setOwner
parameter_list|(
name|InstructionalOffering
name|instructionalOffering
parameter_list|)
block|{
if|if
condition|(
name|isDefinesCreditAtCourseLevel
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|)
block|{
name|setInstructionalOfferingOwner
argument_list|(
name|instructionalOffering
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|CourseCreditFormat
name|getCourseCreditFormat
parameter_list|()
block|{
return|return
name|CourseCreditFormat
operator|.
name|getCourseCreditForReference
argument_list|(
name|getCreditFormat
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|String
name|getCreditFormatAbbv
parameter_list|()
block|{
name|CourseCreditFormat
name|ccf
init|=
name|getCourseCreditFormat
argument_list|()
decl_stmt|;
if|if
condition|(
name|ccf
operator|==
literal|null
condition|)
return|return
literal|""
return|;
return|return
name|ccf
operator|.
name|getAbbv
argument_list|()
return|;
block|}
specifier|protected
name|void
name|baseClone
parameter_list|(
name|CourseCreditUnitConfig
name|newCreditConfig
parameter_list|)
block|{
name|newCreditConfig
operator|.
name|setCreditFormat
argument_list|(
name|getCreditFormat
argument_list|()
argument_list|)
expr_stmt|;
name|newCreditConfig
operator|.
name|setCreditType
argument_list|(
name|getCreditType
argument_list|()
argument_list|)
expr_stmt|;
name|newCreditConfig
operator|.
name|setCreditUnitType
argument_list|(
name|getCreditUnitType
argument_list|()
argument_list|)
expr_stmt|;
name|newCreditConfig
operator|.
name|setDefinesCreditAtCourseLevel
argument_list|(
name|isDefinesCreditAtCourseLevel
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|abstract
name|Object
name|clone
parameter_list|()
function_decl|;
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
operator|(
name|creditText
argument_list|()
operator|)
return|;
block|}
block|}
end_class

end_unit

