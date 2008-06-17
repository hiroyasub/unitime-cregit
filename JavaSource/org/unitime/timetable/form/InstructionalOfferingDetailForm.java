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
name|Iterator
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
name|ActionForm
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
name|util
operator|.
name|DynamicList
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
name|DynamicListObjectFactory
import|;
end_import

begin_comment
comment|/**   * MyEclipse Struts  * Creation date: 03-20-2006  *   * XDoclet definition:  * @struts:form name="instructionalOfferingConfigDetailForm"  */
end_comment

begin_class
specifier|public
class|class
name|InstructionalOfferingDetailForm
extends|extends
name|ActionForm
block|{
comment|// --------------------------------------------------------- Instance Variables
specifier|private
name|String
name|op
decl_stmt|;
specifier|private
name|Long
name|subjectAreaId
decl_stmt|;
specifier|private
name|Long
name|crsOfferingId
decl_stmt|;
specifier|private
name|Long
name|instrOfferingId
decl_stmt|;
specifier|private
name|Long
name|ctrlCrsOfferingId
decl_stmt|;
specifier|private
name|Integer
name|projectedDemand
decl_stmt|;
specifier|private
name|Integer
name|demand
decl_stmt|;
specifier|private
name|Integer
name|limit
decl_stmt|;
specifier|private
name|Boolean
name|unlimited
decl_stmt|;
specifier|private
name|Boolean
name|notOffered
decl_stmt|;
specifier|private
name|Boolean
name|isEditable
decl_stmt|;
specifier|private
name|Boolean
name|isFullyEditable
decl_stmt|;
specifier|private
name|Boolean
name|isManager
decl_stmt|;
specifier|private
name|String
name|instrOfferingName
decl_stmt|;
specifier|private
name|String
name|instrOfferingNameNoTitle
decl_stmt|;
specifier|private
name|List
name|courseOfferings
decl_stmt|;
specifier|private
name|String
name|subjectAreaAbbr
decl_stmt|;
specifier|private
name|String
name|courseNbr
decl_stmt|;
specifier|private
name|String
name|consentType
decl_stmt|;
specifier|private
name|Boolean
name|designatorRequired
decl_stmt|;
specifier|private
name|String
name|creditText
decl_stmt|;
specifier|private
name|String
name|nextId
decl_stmt|;
specifier|private
name|String
name|previousId
decl_stmt|;
specifier|private
name|String
name|catalogLinkLabel
decl_stmt|;
specifier|private
name|String
name|catalogLinkLocation
decl_stmt|;
comment|// --------------------------------------------------------- Classes
comment|/** Factory to create dynamic list element for Course Offerings */
specifier|protected
name|DynamicListObjectFactory
name|factoryCourseOfferings
init|=
operator|new
name|DynamicListObjectFactory
argument_list|()
block|{
specifier|public
name|Object
name|create
parameter_list|()
block|{
return|return
operator|new
name|String
argument_list|(
literal|""
argument_list|)
return|;
block|}
block|}
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
throw|throw
operator|new
name|UnsupportedOperationException
argument_list|(
literal|"Generated method 'validate(...)' not implemented."
argument_list|)
throw|;
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
name|op
operator|=
literal|"view"
expr_stmt|;
name|subjectAreaId
operator|=
literal|null
expr_stmt|;
name|subjectAreaAbbr
operator|=
literal|null
expr_stmt|;
name|courseNbr
operator|=
literal|null
expr_stmt|;
name|crsOfferingId
operator|=
literal|null
expr_stmt|;
name|instrOfferingId
operator|=
literal|null
expr_stmt|;
name|ctrlCrsOfferingId
operator|=
literal|null
expr_stmt|;
name|demand
operator|=
literal|null
expr_stmt|;
name|projectedDemand
operator|=
literal|null
expr_stmt|;
name|limit
operator|=
literal|null
expr_stmt|;
name|unlimited
operator|=
operator|new
name|Boolean
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|notOffered
operator|=
literal|null
expr_stmt|;
name|instrOfferingName
operator|=
literal|""
expr_stmt|;
name|instrOfferingNameNoTitle
operator|=
literal|""
expr_stmt|;
name|isEditable
operator|=
literal|null
expr_stmt|;
name|isFullyEditable
operator|=
literal|null
expr_stmt|;
name|isManager
operator|=
literal|null
expr_stmt|;
name|courseOfferings
operator|=
name|DynamicList
operator|.
name|getInstance
argument_list|(
operator|new
name|ArrayList
argument_list|()
argument_list|,
name|factoryCourseOfferings
argument_list|)
expr_stmt|;
name|nextId
operator|=
name|previousId
operator|=
literal|null
expr_stmt|;
name|creditText
operator|=
literal|""
expr_stmt|;
name|catalogLinkLabel
operator|=
literal|null
expr_stmt|;
name|catalogLinkLocation
operator|=
literal|null
expr_stmt|;
block|}
specifier|public
name|List
name|getCourseOfferings
parameter_list|()
block|{
return|return
name|courseOfferings
return|;
block|}
specifier|public
name|String
name|getCourseOfferings
parameter_list|(
name|int
name|key
parameter_list|)
block|{
return|return
name|courseOfferings
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
name|setCourseOfferings
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
name|courseOfferings
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
name|setCourseOfferings
parameter_list|(
name|List
name|courseOfferings
parameter_list|)
block|{
name|this
operator|.
name|courseOfferings
operator|=
name|courseOfferings
expr_stmt|;
block|}
specifier|public
name|Long
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
name|Long
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
name|Long
name|getCrsOfferingId
parameter_list|()
block|{
return|return
name|crsOfferingId
return|;
block|}
specifier|public
name|void
name|setCrsOfferingId
parameter_list|(
name|Long
name|crsOfferingId
parameter_list|)
block|{
name|this
operator|.
name|crsOfferingId
operator|=
name|crsOfferingId
expr_stmt|;
block|}
specifier|public
name|Long
name|getCtrlCrsOfferingId
parameter_list|()
block|{
return|return
name|ctrlCrsOfferingId
return|;
block|}
specifier|public
name|void
name|setCtrlCrsOfferingId
parameter_list|(
name|Long
name|ctrlCrsOfferingId
parameter_list|)
block|{
name|this
operator|.
name|ctrlCrsOfferingId
operator|=
name|ctrlCrsOfferingId
expr_stmt|;
block|}
specifier|public
name|Integer
name|getDemand
parameter_list|()
block|{
return|return
name|demand
return|;
block|}
specifier|public
name|void
name|setDemand
parameter_list|(
name|Integer
name|demand
parameter_list|)
block|{
name|this
operator|.
name|demand
operator|=
name|demand
expr_stmt|;
block|}
specifier|public
name|Integer
name|getProjectedDemand
parameter_list|()
block|{
return|return
name|projectedDemand
return|;
block|}
specifier|public
name|void
name|setProjectedDemand
parameter_list|(
name|Integer
name|projectedDemand
parameter_list|)
block|{
name|this
operator|.
name|projectedDemand
operator|=
name|projectedDemand
expr_stmt|;
block|}
specifier|public
name|Long
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
name|Long
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
name|Integer
name|getLimit
parameter_list|()
block|{
return|return
name|limit
return|;
block|}
specifier|public
name|void
name|setLimit
parameter_list|(
name|Integer
name|limit
parameter_list|)
block|{
name|this
operator|.
name|limit
operator|=
name|limit
expr_stmt|;
block|}
specifier|public
name|Boolean
name|getUnlimited
parameter_list|()
block|{
return|return
name|unlimited
return|;
block|}
specifier|public
name|void
name|setUnlimited
parameter_list|(
name|Boolean
name|unlimited
parameter_list|)
block|{
name|this
operator|.
name|unlimited
operator|=
name|unlimited
expr_stmt|;
block|}
specifier|public
name|Boolean
name|getNotOffered
parameter_list|()
block|{
return|return
name|notOffered
return|;
block|}
specifier|public
name|void
name|setNotOffered
parameter_list|(
name|Boolean
name|notOffered
parameter_list|)
block|{
name|this
operator|.
name|notOffered
operator|=
name|notOffered
expr_stmt|;
block|}
specifier|public
name|Boolean
name|getIsEditable
parameter_list|()
block|{
return|return
name|isEditable
return|;
block|}
specifier|public
name|void
name|setIsEditable
parameter_list|(
name|Boolean
name|isEditable
parameter_list|)
block|{
name|this
operator|.
name|isEditable
operator|=
name|isEditable
expr_stmt|;
block|}
specifier|public
name|Boolean
name|getIsFullyEditable
parameter_list|()
block|{
return|return
name|isFullyEditable
return|;
block|}
specifier|public
name|void
name|setIsFullyEditable
parameter_list|(
name|Boolean
name|isFullyEditable
parameter_list|)
block|{
name|this
operator|.
name|isFullyEditable
operator|=
name|isFullyEditable
expr_stmt|;
block|}
specifier|public
name|String
name|getOp
parameter_list|()
block|{
return|return
name|op
return|;
block|}
specifier|public
name|void
name|setOp
parameter_list|(
name|String
name|op
parameter_list|)
block|{
name|this
operator|.
name|op
operator|=
name|op
expr_stmt|;
block|}
specifier|public
name|String
name|getSubjectAreaAbbr
parameter_list|()
block|{
return|return
name|subjectAreaAbbr
return|;
block|}
specifier|public
name|void
name|setSubjectAreaAbbr
parameter_list|(
name|String
name|subjectAreaAbbr
parameter_list|)
block|{
name|this
operator|.
name|subjectAreaAbbr
operator|=
name|subjectAreaAbbr
expr_stmt|;
block|}
specifier|public
name|String
name|getCourseNbr
parameter_list|()
block|{
return|return
name|courseNbr
return|;
block|}
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
name|getInstrOfferingName
parameter_list|()
block|{
return|return
name|instrOfferingName
return|;
block|}
specifier|public
name|void
name|setInstrOfferingName
parameter_list|(
name|String
name|instrOfferingName
parameter_list|)
block|{
name|this
operator|.
name|instrOfferingName
operator|=
name|instrOfferingName
expr_stmt|;
block|}
specifier|public
name|String
name|getInstrOfferingNameNoTitle
parameter_list|()
block|{
return|return
name|instrOfferingNameNoTitle
return|;
block|}
specifier|public
name|void
name|setInstrOfferingNameNoTitle
parameter_list|(
name|String
name|instrOfferingNameNoTitle
parameter_list|)
block|{
name|this
operator|.
name|instrOfferingNameNoTitle
operator|=
name|instrOfferingNameNoTitle
expr_stmt|;
block|}
specifier|public
name|Boolean
name|getIsManager
parameter_list|()
block|{
return|return
name|isManager
return|;
block|}
specifier|public
name|void
name|setIsManager
parameter_list|(
name|Boolean
name|isManager
parameter_list|)
block|{
name|this
operator|.
name|isManager
operator|=
name|isManager
expr_stmt|;
block|}
specifier|public
name|String
name|getConsentType
parameter_list|()
block|{
return|return
name|consentType
return|;
block|}
specifier|public
name|void
name|setConsentType
parameter_list|(
name|String
name|consentType
parameter_list|)
block|{
name|this
operator|.
name|consentType
operator|=
name|consentType
expr_stmt|;
block|}
specifier|public
name|Boolean
name|getDesignatorRequired
parameter_list|()
block|{
return|return
name|designatorRequired
return|;
block|}
specifier|public
name|void
name|setDesignatorRequired
parameter_list|(
name|Boolean
name|designatorRequired
parameter_list|)
block|{
name|this
operator|.
name|designatorRequired
operator|=
name|designatorRequired
expr_stmt|;
block|}
specifier|public
name|String
name|getCatalogLinkLabel
parameter_list|()
block|{
return|return
name|catalogLinkLabel
return|;
block|}
specifier|public
name|void
name|setCatalogLinkLabel
parameter_list|(
name|String
name|catalogLinkLabel
parameter_list|)
block|{
name|this
operator|.
name|catalogLinkLabel
operator|=
name|catalogLinkLabel
expr_stmt|;
block|}
specifier|public
name|String
name|getCatalogLinkLocation
parameter_list|()
block|{
return|return
name|catalogLinkLocation
return|;
block|}
specifier|public
name|void
name|setCatalogLinkLocation
parameter_list|(
name|String
name|catalogLinkLocation
parameter_list|)
block|{
name|this
operator|.
name|catalogLinkLocation
operator|=
name|catalogLinkLocation
expr_stmt|;
block|}
comment|/**      * Add a course offering to the existing list      * @param co Course Offering      */
specifier|public
name|void
name|addToCourseOfferings
parameter_list|(
name|CourseOffering
name|co
parameter_list|)
block|{
name|this
operator|.
name|courseOfferings
operator|.
name|add
argument_list|(
name|co
argument_list|)
expr_stmt|;
block|}
comment|/**      * @return No. of course offerings in the instr offering      */
specifier|public
name|Integer
name|getCourseOfferingCount
parameter_list|()
block|{
return|return
operator|new
name|Integer
argument_list|(
name|this
operator|.
name|courseOfferings
operator|.
name|size
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|String
name|getNextId
parameter_list|()
block|{
return|return
name|nextId
return|;
block|}
specifier|public
name|void
name|setNextId
parameter_list|(
name|String
name|nextId
parameter_list|)
block|{
name|this
operator|.
name|nextId
operator|=
name|nextId
expr_stmt|;
block|}
specifier|public
name|String
name|getPreviousId
parameter_list|()
block|{
return|return
name|previousId
return|;
block|}
specifier|public
name|void
name|setPreviousId
parameter_list|(
name|String
name|previousId
parameter_list|)
block|{
name|this
operator|.
name|previousId
operator|=
name|previousId
expr_stmt|;
block|}
specifier|public
name|boolean
name|getHasDemandOfferings
parameter_list|()
block|{
if|if
condition|(
name|courseOfferings
operator|==
literal|null
operator|||
name|courseOfferings
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
literal|false
return|;
for|for
control|(
name|Iterator
name|i
init|=
name|courseOfferings
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
if|if
condition|(
operator|(
operator|(
name|CourseOffering
operator|)
name|i
operator|.
name|next
argument_list|()
operator|)
operator|.
name|getDemandOffering
argument_list|()
operator|!=
literal|null
condition|)
return|return
literal|true
return|;
return|return
literal|false
return|;
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
block|}
end_class

end_unit

