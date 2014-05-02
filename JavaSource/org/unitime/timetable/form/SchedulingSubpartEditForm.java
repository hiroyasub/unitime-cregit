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
name|form
package|;
end_package

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
name|defaults
operator|.
name|ApplicationProperty
import|;
end_import

begin_comment
comment|/**   * MyEclipse Struts  * Creation date: 07-26-2005  *   * XDoclet definition:  * @struts:form name="schedulingSubpartEditForm"  *  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|SchedulingSubpartEditForm
extends|extends
name|PreferencesForm
block|{
comment|// --------------------------------------------------------- Instance Variables
comment|/** 	 * Comment for<code>serialVersionUID</code> 	 */
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|3256445806692087861L
decl_stmt|;
specifier|private
name|String
name|schedulingSubpartId
decl_stmt|;
specifier|private
name|String
name|subjectAreaId
decl_stmt|;
specifier|private
name|String
name|subjectArea
decl_stmt|;
specifier|private
name|String
name|courseNbr
decl_stmt|;
specifier|private
name|String
name|courseTitle
decl_stmt|;
specifier|private
name|String
name|parentSubpart
decl_stmt|;
specifier|private
name|String
name|instructionalType
decl_stmt|;
specifier|private
name|String
name|instructionalTypeLabel
decl_stmt|;
specifier|private
name|Long
name|datePattern
decl_stmt|;
specifier|private
name|String
name|instrOfferingId
decl_stmt|;
specifier|private
name|String
name|parentSubpartId
decl_stmt|;
specifier|private
name|String
name|parentSubpartLabel
decl_stmt|;
specifier|private
name|String
name|managingDeptName
decl_stmt|;
specifier|private
name|String
name|creditFormat
decl_stmt|;
specifier|private
name|Long
name|creditType
decl_stmt|;
specifier|private
name|Long
name|creditUnitType
decl_stmt|;
specifier|private
name|Float
name|units
decl_stmt|;
specifier|private
name|Float
name|maxUnits
decl_stmt|;
specifier|private
name|Boolean
name|fractionalIncrementsAllowed
decl_stmt|;
specifier|private
name|String
name|creditText
decl_stmt|;
specifier|private
name|Boolean
name|sameItypeAsParent
decl_stmt|;
specifier|private
name|Boolean
name|unlimitedEnroll
decl_stmt|;
specifier|private
name|Boolean
name|autoSpreadInTime
decl_stmt|;
specifier|private
name|Boolean
name|subpartCreditEditAllowed
decl_stmt|;
specifier|private
name|boolean
name|itypeBasic
decl_stmt|;
specifier|private
name|Boolean
name|studentAllowOverlap
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
return|return
name|super
operator|.
name|validate
argument_list|(
name|mapping
argument_list|,
name|request
argument_list|)
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
name|schedulingSubpartId
operator|=
literal|""
expr_stmt|;
name|datePattern
operator|=
literal|null
expr_stmt|;
name|unlimitedEnroll
operator|=
literal|null
expr_stmt|;
name|parentSubpartId
operator|=
literal|null
expr_stmt|;
name|parentSubpartLabel
operator|=
literal|null
expr_stmt|;
name|managingDeptName
operator|=
literal|null
expr_stmt|;
name|sameItypeAsParent
operator|=
literal|null
expr_stmt|;
name|creditFormat
operator|=
literal|null
expr_stmt|;
name|creditType
operator|=
literal|null
expr_stmt|;
name|creditUnitType
operator|=
literal|null
expr_stmt|;
name|units
operator|=
literal|null
expr_stmt|;
name|maxUnits
operator|=
literal|null
expr_stmt|;
name|fractionalIncrementsAllowed
operator|=
operator|new
name|Boolean
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|creditText
operator|=
literal|""
expr_stmt|;
name|autoSpreadInTime
operator|=
name|Boolean
operator|.
name|FALSE
expr_stmt|;
name|studentAllowOverlap
operator|=
name|Boolean
operator|.
name|FALSE
expr_stmt|;
name|subpartCreditEditAllowed
operator|=
name|ApplicationProperty
operator|.
name|SubpartCreditEditable
operator|.
name|isTrue
argument_list|()
expr_stmt|;
name|itypeBasic
operator|=
literal|false
expr_stmt|;
name|instructionalType
operator|=
literal|null
expr_stmt|;
name|instructionalTypeLabel
operator|=
literal|null
expr_stmt|;
name|super
operator|.
name|reset
argument_list|(
name|mapping
argument_list|,
name|request
argument_list|)
expr_stmt|;
block|}
comment|/**      * @return Returns the schedulingSubpartId.      */
specifier|public
name|String
name|getSchedulingSubpartId
parameter_list|()
block|{
return|return
name|schedulingSubpartId
return|;
block|}
comment|/**      * @param schedulingSubpartId The schedulingSubpartId to set.      */
specifier|public
name|void
name|setSchedulingSubpartId
parameter_list|(
name|String
name|schedulingSubpartId
parameter_list|)
block|{
name|this
operator|.
name|schedulingSubpartId
operator|=
name|schedulingSubpartId
expr_stmt|;
block|}
comment|/**      * @return Returns the courseNbr.      */
specifier|public
name|String
name|getCourseNbr
parameter_list|()
block|{
return|return
name|courseNbr
return|;
block|}
comment|/**      * @param courseNbr The courseNbr to set.      */
specifier|public
name|void
name|setCourseNbr
parameter_list|(
name|String
name|courseNbr
parameter_list|)
block|{
name|this
operator|.
name|courseNbr
operator|=
name|courseNbr
expr_stmt|;
block|}
specifier|public
name|String
name|getCourseTitle
parameter_list|()
block|{
return|return
name|this
operator|.
name|courseTitle
return|;
block|}
specifier|public
name|void
name|setCourseTitle
parameter_list|(
name|String
name|courseTitle
parameter_list|)
block|{
name|this
operator|.
name|courseTitle
operator|=
name|courseTitle
expr_stmt|;
block|}
comment|/**      * @return Returns the instructionalType.      */
specifier|public
name|String
name|getInstructionalType
parameter_list|()
block|{
return|return
name|instructionalType
return|;
block|}
comment|/**      * @param instructionalType The instructionalType to set.      */
specifier|public
name|void
name|setInstructionalType
parameter_list|(
name|String
name|instructionalType
parameter_list|)
block|{
name|this
operator|.
name|instructionalType
operator|=
name|instructionalType
expr_stmt|;
block|}
comment|/**      * @return Returns the parentSubpart.      */
specifier|public
name|String
name|getParentSubpart
parameter_list|()
block|{
return|return
name|parentSubpart
return|;
block|}
comment|/**      * @param parentSubpart The parentSubpart to set.      */
specifier|public
name|void
name|setParentSubpart
parameter_list|(
name|String
name|parentSubpart
parameter_list|)
block|{
name|this
operator|.
name|parentSubpart
operator|=
name|parentSubpart
expr_stmt|;
block|}
specifier|public
name|String
name|getParentSubpartId
parameter_list|()
block|{
return|return
name|parentSubpartId
return|;
block|}
specifier|public
name|void
name|setParentSubpartId
parameter_list|(
name|String
name|parentSubpartId
parameter_list|)
block|{
name|this
operator|.
name|parentSubpartId
operator|=
name|parentSubpartId
expr_stmt|;
block|}
specifier|public
name|String
name|getParentSubpartLabel
parameter_list|()
block|{
return|return
name|parentSubpartLabel
return|;
block|}
specifier|public
name|void
name|setParentSubpartLabel
parameter_list|(
name|String
name|parentSubpartLabel
parameter_list|)
block|{
name|this
operator|.
name|parentSubpartLabel
operator|=
name|parentSubpartLabel
expr_stmt|;
block|}
comment|/**      * @return Returns the subjectArea.      */
specifier|public
name|String
name|getSubjectArea
parameter_list|()
block|{
return|return
name|subjectArea
return|;
block|}
comment|/**      * @param subjectArea The subjectArea to set.      */
specifier|public
name|void
name|setSubjectArea
parameter_list|(
name|String
name|subjectArea
parameter_list|)
block|{
name|this
operator|.
name|subjectArea
operator|=
name|subjectArea
expr_stmt|;
block|}
comment|/**      * @return Returns the instructionalTypeLabel.      */
specifier|public
name|String
name|getInstructionalTypeLabel
parameter_list|()
block|{
return|return
name|instructionalTypeLabel
return|;
block|}
comment|/**      * @param instructionalTypeLabel The instructionalTypeLabel to set.      */
specifier|public
name|void
name|setInstructionalTypeLabel
parameter_list|(
name|String
name|instructionalTypeLabel
parameter_list|)
block|{
name|this
operator|.
name|instructionalTypeLabel
operator|=
name|instructionalTypeLabel
expr_stmt|;
block|}
specifier|public
name|Long
name|getDatePattern
parameter_list|()
block|{
return|return
name|datePattern
return|;
block|}
specifier|public
name|void
name|setDatePattern
parameter_list|(
name|Long
name|datePattern
parameter_list|)
block|{
name|this
operator|.
name|datePattern
operator|=
name|datePattern
expr_stmt|;
block|}
specifier|public
name|String
name|getSubjectAreaId
parameter_list|()
block|{
return|return
name|subjectAreaId
return|;
block|}
specifier|public
name|void
name|setSubjectAreaId
parameter_list|(
name|String
name|subjectAreaId
parameter_list|)
block|{
name|this
operator|.
name|subjectAreaId
operator|=
name|subjectAreaId
expr_stmt|;
block|}
specifier|public
name|String
name|getInstrOfferingId
parameter_list|()
block|{
return|return
name|instrOfferingId
return|;
block|}
specifier|public
name|void
name|setInstrOfferingId
parameter_list|(
name|String
name|instrOfferingId
parameter_list|)
block|{
name|this
operator|.
name|instrOfferingId
operator|=
name|instrOfferingId
expr_stmt|;
block|}
specifier|public
name|String
name|getManagingDeptName
parameter_list|()
block|{
return|return
name|managingDeptName
return|;
block|}
specifier|public
name|void
name|setManagingDeptName
parameter_list|(
name|String
name|managingDeptName
parameter_list|)
block|{
name|this
operator|.
name|managingDeptName
operator|=
name|managingDeptName
expr_stmt|;
block|}
specifier|public
name|String
name|getCreditFormat
parameter_list|()
block|{
return|return
name|creditFormat
return|;
block|}
specifier|public
name|void
name|setCreditFormat
parameter_list|(
name|String
name|creditFormat
parameter_list|)
block|{
name|this
operator|.
name|creditFormat
operator|=
name|creditFormat
expr_stmt|;
block|}
specifier|public
name|Long
name|getCreditType
parameter_list|()
block|{
return|return
name|creditType
return|;
block|}
specifier|public
name|void
name|setCreditType
parameter_list|(
name|Long
name|creditType
parameter_list|)
block|{
name|this
operator|.
name|creditType
operator|=
name|creditType
expr_stmt|;
block|}
specifier|public
name|Long
name|getCreditUnitType
parameter_list|()
block|{
return|return
name|creditUnitType
return|;
block|}
specifier|public
name|void
name|setCreditUnitType
parameter_list|(
name|Long
name|creditUnitType
parameter_list|)
block|{
name|this
operator|.
name|creditUnitType
operator|=
name|creditUnitType
expr_stmt|;
block|}
specifier|public
name|Boolean
name|getFractionalIncrementsAllowed
parameter_list|()
block|{
return|return
name|fractionalIncrementsAllowed
return|;
block|}
specifier|public
name|void
name|setFractionalIncrementsAllowed
parameter_list|(
name|Boolean
name|fractionalIncrementsAllowed
parameter_list|)
block|{
name|this
operator|.
name|fractionalIncrementsAllowed
operator|=
name|fractionalIncrementsAllowed
expr_stmt|;
block|}
specifier|public
name|Float
name|getMaxUnits
parameter_list|()
block|{
return|return
name|maxUnits
return|;
block|}
specifier|public
name|void
name|setMaxUnits
parameter_list|(
name|Float
name|maxUnits
parameter_list|)
block|{
name|this
operator|.
name|maxUnits
operator|=
name|maxUnits
expr_stmt|;
block|}
specifier|public
name|Float
name|getUnits
parameter_list|()
block|{
return|return
name|units
return|;
block|}
specifier|public
name|void
name|setUnits
parameter_list|(
name|Float
name|units
parameter_list|)
block|{
name|this
operator|.
name|units
operator|=
name|units
expr_stmt|;
block|}
specifier|public
name|String
name|getCreditText
parameter_list|()
block|{
return|return
name|creditText
return|;
block|}
specifier|public
name|void
name|setCreditText
parameter_list|(
name|String
name|creditText
parameter_list|)
block|{
name|this
operator|.
name|creditText
operator|=
name|creditText
expr_stmt|;
block|}
specifier|public
name|Boolean
name|getSameItypeAsParent
parameter_list|()
block|{
return|return
name|sameItypeAsParent
return|;
block|}
specifier|public
name|void
name|setSameItypeAsParent
parameter_list|(
name|Boolean
name|sameItypeAsParent
parameter_list|)
block|{
name|this
operator|.
name|sameItypeAsParent
operator|=
name|sameItypeAsParent
expr_stmt|;
block|}
specifier|public
name|Boolean
name|getUnlimitedEnroll
parameter_list|()
block|{
return|return
name|unlimitedEnroll
return|;
block|}
specifier|public
name|void
name|setUnlimitedEnroll
parameter_list|(
name|Boolean
name|unlimitedEnroll
parameter_list|)
block|{
name|this
operator|.
name|unlimitedEnroll
operator|=
name|unlimitedEnroll
expr_stmt|;
block|}
specifier|public
name|Boolean
name|getAutoSpreadInTime
parameter_list|()
block|{
return|return
name|autoSpreadInTime
return|;
block|}
specifier|public
name|void
name|setAutoSpreadInTime
parameter_list|(
name|Boolean
name|autoSpreadInTime
parameter_list|)
block|{
name|this
operator|.
name|autoSpreadInTime
operator|=
name|autoSpreadInTime
expr_stmt|;
block|}
specifier|public
name|Boolean
name|getSubpartCreditEditAllowed
parameter_list|()
block|{
return|return
name|subpartCreditEditAllowed
return|;
block|}
specifier|public
name|void
name|setSubpartCreditEditAllowed
parameter_list|(
name|Boolean
name|subpartCreditEditAllowed
parameter_list|)
block|{
name|this
operator|.
name|subpartCreditEditAllowed
operator|=
name|subpartCreditEditAllowed
expr_stmt|;
block|}
specifier|public
name|boolean
name|getItypeBasic
parameter_list|()
block|{
return|return
name|itypeBasic
return|;
block|}
specifier|public
name|void
name|setItypeBasic
parameter_list|(
name|boolean
name|itypeBasic
parameter_list|)
block|{
name|this
operator|.
name|itypeBasic
operator|=
name|itypeBasic
expr_stmt|;
block|}
specifier|public
name|boolean
name|getStudentAllowOverlap
parameter_list|()
block|{
return|return
name|studentAllowOverlap
return|;
block|}
specifier|public
name|void
name|setStudentAllowOverlap
parameter_list|(
name|boolean
name|studentAllowOverlap
parameter_list|)
block|{
name|this
operator|.
name|studentAllowOverlap
operator|=
name|studentAllowOverlap
expr_stmt|;
block|}
block|}
end_class

end_unit

