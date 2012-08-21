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
name|Collection
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
name|apache
operator|.
name|struts
operator|.
name|util
operator|.
name|LabelValueBean
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|localization
operator|.
name|impl
operator|.
name|Localization
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|localization
operator|.
name|messages
operator|.
name|CourseMessages
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
name|interfaces
operator|.
name|ExternalUidLookup
operator|.
name|UserInfo
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
name|PositionType
import|;
end_import

begin_comment
comment|/**   * MyEclipse Struts  * Creation date: 10-20-2005  *   * XDoclet definition:  * @struts:form name="instructorEditForm"  */
end_comment

begin_class
specifier|public
class|class
name|InstructorEditForm
extends|extends
name|PreferencesForm
block|{
specifier|protected
specifier|final
specifier|static
name|CourseMessages
name|MSG
init|=
name|Localization
operator|.
name|create
argument_list|(
name|CourseMessages
operator|.
name|class
argument_list|)
decl_stmt|;
comment|// --------------------------------------------------------- Instance Variables
comment|/** 	 *  	 */
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|7234507709430023477L
decl_stmt|;
comment|/** deptCode property */
specifier|private
name|String
name|deptCode
decl_stmt|;
comment|/** instructorId property */
specifier|private
name|String
name|instructorId
decl_stmt|;
comment|/** puId property */
specifier|private
name|String
name|puId
decl_stmt|;
comment|/** name property */
specifier|private
name|String
name|name
decl_stmt|;
specifier|private
name|String
name|careerAcct
decl_stmt|;
specifier|private
name|String
name|posType
decl_stmt|;
specifier|private
name|String
name|note
decl_stmt|;
specifier|private
name|boolean
name|displayPrefs
decl_stmt|;
specifier|private
name|String
name|lname
decl_stmt|;
specifier|private
name|String
name|mname
decl_stmt|;
specifier|private
name|String
name|fname
decl_stmt|;
specifier|private
name|String
name|deptName
decl_stmt|;
specifier|private
name|String
name|email
decl_stmt|;
specifier|private
name|String
name|searchSelect
decl_stmt|;
specifier|private
name|UserInfo
name|i2a2Match
decl_stmt|;
specifier|private
name|Collection
name|staffMatch
decl_stmt|;
specifier|private
name|Boolean
name|matchFound
decl_stmt|;
specifier|private
name|String
name|screenName
decl_stmt|;
specifier|private
name|String
name|prevId
decl_stmt|;
specifier|private
name|String
name|nextId
decl_stmt|;
specifier|private
name|boolean
name|ignoreDist
decl_stmt|;
specifier|private
name|Boolean
name|lookupEnabled
decl_stmt|;
comment|// --------------------------------------------------------- Methods
specifier|public
name|boolean
name|getIgnoreDist
parameter_list|()
block|{
return|return
name|ignoreDist
return|;
block|}
specifier|public
name|void
name|setIgnoreDist
parameter_list|(
name|boolean
name|ignoreDist
parameter_list|)
block|{
name|this
operator|.
name|ignoreDist
operator|=
name|ignoreDist
expr_stmt|;
block|}
specifier|public
name|String
name|getDeptName
parameter_list|()
block|{
return|return
name|deptName
return|;
block|}
specifier|public
name|void
name|setDeptName
parameter_list|(
name|String
name|deptName
parameter_list|)
block|{
name|this
operator|.
name|deptName
operator|=
name|deptName
expr_stmt|;
block|}
specifier|public
name|String
name|getEmail
parameter_list|()
block|{
return|return
name|email
return|;
block|}
specifier|public
name|void
name|setEmail
parameter_list|(
name|String
name|email
parameter_list|)
block|{
name|this
operator|.
name|email
operator|=
name|email
expr_stmt|;
block|}
specifier|public
name|String
name|getFname
parameter_list|()
block|{
return|return
name|fname
return|;
block|}
specifier|public
name|void
name|setFname
parameter_list|(
name|String
name|fname
parameter_list|)
block|{
name|this
operator|.
name|fname
operator|=
name|fname
expr_stmt|;
block|}
specifier|public
name|String
name|getLname
parameter_list|()
block|{
return|return
name|lname
return|;
block|}
specifier|public
name|void
name|setLname
parameter_list|(
name|String
name|lname
parameter_list|)
block|{
name|this
operator|.
name|lname
operator|=
name|lname
expr_stmt|;
block|}
specifier|public
name|String
name|getMname
parameter_list|()
block|{
return|return
name|mname
return|;
block|}
specifier|public
name|void
name|setMname
parameter_list|(
name|String
name|mname
parameter_list|)
block|{
name|this
operator|.
name|mname
operator|=
name|mname
expr_stmt|;
block|}
specifier|public
name|String
name|getCareerAcct
parameter_list|()
block|{
return|return
name|careerAcct
return|;
block|}
specifier|public
name|void
name|setCareerAcct
parameter_list|(
name|String
name|careerAcct
parameter_list|)
block|{
name|this
operator|.
name|careerAcct
operator|=
name|careerAcct
expr_stmt|;
block|}
specifier|public
name|String
name|getNote
parameter_list|()
block|{
return|return
name|note
return|;
block|}
specifier|public
name|void
name|setNote
parameter_list|(
name|String
name|note
parameter_list|)
block|{
name|this
operator|.
name|note
operator|=
name|note
expr_stmt|;
block|}
specifier|public
name|String
name|getPosType
parameter_list|()
block|{
return|return
name|posType
return|;
block|}
specifier|public
name|void
name|setPosType
parameter_list|(
name|String
name|posCode
parameter_list|)
block|{
name|this
operator|.
name|posType
operator|=
name|posCode
expr_stmt|;
block|}
specifier|public
name|String
name|getScreenName
parameter_list|()
block|{
return|return
name|screenName
return|;
block|}
specifier|public
name|void
name|setScreenName
parameter_list|(
name|String
name|screenName
parameter_list|)
block|{
name|this
operator|.
name|screenName
operator|=
name|screenName
expr_stmt|;
block|}
comment|/**  	 * Method reset 	 * @param mapping 	 * @param request 	 */
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
name|instructorId
operator|=
literal|""
expr_stmt|;
name|screenName
operator|=
literal|"instructor"
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
comment|//Set request attributes
name|setPosType
argument_list|(
name|request
argument_list|)
expr_stmt|;
name|prevId
operator|=
name|nextId
operator|=
literal|null
expr_stmt|;
name|ignoreDist
operator|=
literal|false
expr_stmt|;
name|email
operator|=
literal|null
expr_stmt|;
block|}
comment|/** 	 *  	 * @param request 	 */
specifier|private
name|void
name|setPosType
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|)
block|{
name|ArrayList
name|list
init|=
operator|new
name|ArrayList
argument_list|()
decl_stmt|;
for|for
control|(
name|PositionType
name|pt
range|:
name|PositionType
operator|.
name|getPositionTypeList
argument_list|()
control|)
block|{
name|list
operator|.
name|add
argument_list|(
operator|new
name|LabelValueBean
argument_list|(
name|pt
operator|.
name|getLabel
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|,
name|pt
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|request
operator|.
name|setAttribute
argument_list|(
name|PositionType
operator|.
name|POSTYPE_ATTR_NAME
argument_list|,
name|list
argument_list|)
expr_stmt|;
block|}
comment|/**  	 * Method validate 	 * @param mapping 	 * @param request 	 * @return ActionErrors 	 */
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
operator|new
name|ActionErrors
argument_list|()
decl_stmt|;
if|if
condition|(
name|op
operator|.
name|equals
argument_list|(
name|MSG
operator|.
name|actionLookupInstructor
argument_list|()
argument_list|)
condition|)
block|{
if|if
condition|(
operator|(
name|fname
operator|==
literal|null
operator|||
name|fname
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
operator|)
operator|&&
operator|(
name|lname
operator|==
literal|null
operator|||
name|lname
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
operator|)
operator|&&
operator|(
name|careerAcct
operator|==
literal|null
operator|||
name|careerAcct
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
operator|)
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"fname"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
name|MSG
operator|.
name|errorSupplyInfoForInstructorLookup
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|errors
return|;
block|}
if|if
condition|(
operator|!
name|screenName
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"instructorPref"
argument_list|)
condition|)
block|{
if|if
condition|(
name|lname
operator|==
literal|null
operator|||
name|lname
operator|.
name|trim
argument_list|()
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"Last Name"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
name|MSG
operator|.
name|errorRequiredLastName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|errors
operator|.
name|size
argument_list|()
operator|==
literal|0
condition|)
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
else|else
block|{
return|return
name|errors
return|;
block|}
block|}
comment|/**  	 * Returns the instructorId. 	 * @return String 	 */
specifier|public
name|String
name|getInstructorId
parameter_list|()
block|{
return|return
name|instructorId
return|;
block|}
comment|/**  	 * Set the instructorId. 	 * @param instructorId The instructorId to set 	 */
specifier|public
name|void
name|setInstructorId
parameter_list|(
name|String
name|instructorId
parameter_list|)
block|{
name|this
operator|.
name|instructorId
operator|=
name|instructorId
expr_stmt|;
block|}
comment|/**  	 * Returns the puId. 	 * @return String 	 */
specifier|public
name|String
name|getPuId
parameter_list|()
block|{
return|return
name|puId
return|;
block|}
comment|/**  	 * Set the puId. 	 * @param puId The puId to set 	 */
specifier|public
name|void
name|setPuId
parameter_list|(
name|String
name|puId
parameter_list|)
block|{
name|this
operator|.
name|puId
operator|=
name|puId
expr_stmt|;
block|}
comment|/**  	 * Returns the name. 	 * @return String 	 */
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|name
return|;
block|}
comment|/**  	 * Set the name. 	 * @param name The name to set 	 */
specifier|public
name|void
name|setName
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|this
operator|.
name|name
operator|=
name|name
expr_stmt|;
block|}
comment|/** 	 *  	 * @return 	 */
specifier|public
name|String
name|getDeptCode
parameter_list|()
block|{
return|return
name|deptCode
return|;
block|}
comment|/** 	 *  	 * @param deptCode 	 */
specifier|public
name|void
name|setDeptCode
parameter_list|(
name|String
name|deptCode
parameter_list|)
block|{
name|this
operator|.
name|deptCode
operator|=
name|deptCode
expr_stmt|;
block|}
specifier|public
name|boolean
name|isDisplayPrefs
parameter_list|()
block|{
return|return
name|displayPrefs
return|;
block|}
specifier|public
name|void
name|setDisplayPrefs
parameter_list|(
name|boolean
name|displayPrefs
parameter_list|)
block|{
name|this
operator|.
name|displayPrefs
operator|=
name|displayPrefs
expr_stmt|;
block|}
specifier|public
name|UserInfo
name|getI2a2Match
parameter_list|()
block|{
return|return
name|i2a2Match
return|;
block|}
specifier|public
name|void
name|setI2a2Match
parameter_list|(
name|UserInfo
name|match
parameter_list|)
block|{
name|i2a2Match
operator|=
name|match
expr_stmt|;
block|}
specifier|public
name|Collection
name|getStaffMatch
parameter_list|()
block|{
return|return
name|staffMatch
return|;
block|}
specifier|public
name|void
name|setStaffMatch
parameter_list|(
name|Collection
name|staffMatch
parameter_list|)
block|{
name|this
operator|.
name|staffMatch
operator|=
name|staffMatch
expr_stmt|;
block|}
specifier|public
name|Boolean
name|getMatchFound
parameter_list|()
block|{
return|return
name|matchFound
return|;
block|}
specifier|public
name|void
name|setMatchFound
parameter_list|(
name|Boolean
name|matchFound
parameter_list|)
block|{
name|this
operator|.
name|matchFound
operator|=
name|matchFound
expr_stmt|;
block|}
specifier|public
name|String
name|getSearchSelect
parameter_list|()
block|{
return|return
name|searchSelect
return|;
block|}
specifier|public
name|void
name|setSearchSelect
parameter_list|(
name|String
name|searchSelect
parameter_list|)
block|{
name|this
operator|.
name|searchSelect
operator|=
name|searchSelect
expr_stmt|;
block|}
specifier|public
name|void
name|setPreviousId
parameter_list|(
name|String
name|prevId
parameter_list|)
block|{
name|this
operator|.
name|prevId
operator|=
name|prevId
expr_stmt|;
block|}
specifier|public
name|String
name|getPreviousId
parameter_list|()
block|{
return|return
name|prevId
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
name|getNextId
parameter_list|()
block|{
return|return
name|nextId
return|;
block|}
specifier|public
name|Boolean
name|getLookupEnabled
parameter_list|()
block|{
return|return
name|lookupEnabled
return|;
block|}
specifier|public
name|void
name|setLookupEnabled
parameter_list|(
name|Boolean
name|lookupEnabled
parameter_list|)
block|{
name|this
operator|.
name|lookupEnabled
operator|=
name|lookupEnabled
expr_stmt|;
block|}
block|}
end_class

end_unit

