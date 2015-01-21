begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to The Apereo Foundation under one or more contributor license  * agreements. See the NOTICE file distributed with this work for  * additional information regarding copyright ownership.  *  * The Apereo Foundation licenses this file to you under the Apache License,  * Version 2.0 (the "License"); you may not use this file except in  * compliance with the License. You may obtain a copy of the License at:  *  * http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *  * See the License for the specific language governing permissions and  * limitations under the License.  *  */
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
name|Vector
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
name|javax
operator|.
name|servlet
operator|.
name|http
operator|.
name|HttpSession
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
name|Globals
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
name|MessageResources
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
name|defaults
operator|.
name|ApplicationProperty
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
name|InstructionalOffering
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
name|SimpleItypeConfig
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
name|InstructionalOfferingDAO
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

begin_comment
comment|/**   * MyEclipse Struts  * Creation date: 05-19-2005  *   * XDoclet definition:  * @struts:form name="InstructionalOfferingConfigEditForm"  *  * @author Stephanie Schluttenhofer, Zuzana Mullerova, Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|InstructionalOfferingConfigEditForm
extends|extends
name|ActionForm
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
comment|/** 	 * Comment for<code>serialVersionUID</code> 	 */
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|3257570611432993077L
decl_stmt|;
comment|// --------------------------------------------------------- Instance Variables
specifier|private
name|Long
name|configId
decl_stmt|;
specifier|private
name|String
name|instrOfferingName
decl_stmt|;
specifier|private
name|String
name|courseOfferingId
decl_stmt|;
specifier|private
name|String
name|instrOfferingId
decl_stmt|;
specifier|private
name|String
name|subjectArea
decl_stmt|;
specifier|private
name|String
name|courseNumber
decl_stmt|;
specifier|private
name|int
name|limit
decl_stmt|;
specifier|private
name|Boolean
name|notOffered
decl_stmt|;
specifier|private
name|String
name|itype
decl_stmt|;
specifier|private
name|String
name|op
decl_stmt|;
specifier|private
name|String
name|name
decl_stmt|;
specifier|private
name|Boolean
name|unlimited
decl_stmt|;
specifier|private
name|Integer
name|configCount
decl_stmt|;
specifier|private
name|String
name|catalogLinkLabel
decl_stmt|;
specifier|private
name|String
name|catalogLinkLocation
decl_stmt|;
comment|// Error Codes
specifier|private
specifier|final
name|short
name|NO_ERR
init|=
literal|0
decl_stmt|;
specifier|private
specifier|final
name|short
name|ERR_NC
init|=
operator|-
literal|1
decl_stmt|;
specifier|private
specifier|final
name|short
name|ERR_CL
init|=
operator|-
literal|2
decl_stmt|;
specifier|private
specifier|final
name|short
name|ERR_LS
init|=
operator|-
literal|3
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
operator|new
name|ActionErrors
argument_list|()
decl_stmt|;
comment|// Get Message Resources
name|MessageResources
name|rsc
init|=
operator|(
name|MessageResources
operator|)
name|super
operator|.
name|getServlet
argument_list|()
operator|.
name|getServletContext
argument_list|()
operator|.
name|getAttribute
argument_list|(
name|Globals
operator|.
name|MESSAGES_KEY
argument_list|)
decl_stmt|;
comment|// Check limit in all cases
if|if
condition|(
name|limit
operator|<
literal|0
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"limit"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.integerGtEq"
argument_list|,
literal|"Limit"
argument_list|,
literal|"0"
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|String
name|lblMax
init|=
literal|"Limit"
decl_stmt|;
if|if
condition|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"varLimits"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|lblMax
operator|=
literal|"Max limit"
expr_stmt|;
block|}
comment|// Check Itype is specified
if|if
condition|(
name|op
operator|.
name|equals
argument_list|(
name|rsc
operator|.
name|getMessage
argument_list|(
literal|"button.add"
argument_list|)
argument_list|)
condition|)
block|{
if|if
condition|(
name|itype
operator|==
literal|null
operator|||
name|itype
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
operator|||
name|itype
operator|.
name|equals
argument_list|(
name|Constants
operator|.
name|BLANK_OPTION_VALUE
argument_list|)
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"itype"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.required"
argument_list|,
literal|"Instructional Type"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|op
operator|.
name|equals
argument_list|(
name|MSG
operator|.
name|actionSaveConfiguration
argument_list|()
argument_list|)
operator|||
name|op
operator|.
name|equals
argument_list|(
name|MSG
operator|.
name|actionUpdateConfiguration
argument_list|()
argument_list|)
condition|)
block|{
name|HttpSession
name|webSession
init|=
name|request
operator|.
name|getSession
argument_list|()
decl_stmt|;
name|Vector
name|sp
init|=
operator|(
name|Vector
operator|)
name|webSession
operator|.
name|getAttribute
argument_list|(
name|SimpleItypeConfig
operator|.
name|CONFIGS_ATTR_NAME
argument_list|)
decl_stmt|;
comment|// Check that config name doesn't already exist
name|InstructionalOffering
name|io
init|=
operator|new
name|InstructionalOfferingDAO
argument_list|()
operator|.
name|get
argument_list|(
operator|new
name|Long
argument_list|(
name|this
operator|.
name|getInstrOfferingId
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|io
operator|.
name|existsConfig
argument_list|(
name|this
operator|.
name|getName
argument_list|()
argument_list|,
name|this
operator|.
name|getConfigId
argument_list|()
argument_list|)
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"subparts"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
literal|"A configuration with this name already exists in this offering. Use a unique name"
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|// Read user defined config
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|sp
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|SimpleItypeConfig
name|sic
init|=
operator|(
name|SimpleItypeConfig
operator|)
name|sp
operator|.
name|elementAt
argument_list|(
name|i
argument_list|)
decl_stmt|;
comment|// Check top level subparts
if|if
condition|(
operator|!
name|this
operator|.
name|getUnlimited
argument_list|()
operator|.
name|booleanValue
argument_list|()
operator|&&
name|ApplicationProperty
operator|.
name|ConfigEditCheckLimits
operator|.
name|isTrue
argument_list|()
condition|)
block|{
name|int
name|numClasses
init|=
name|sic
operator|.
name|getNumClasses
argument_list|()
decl_stmt|;
name|int
name|maxLimitPerClass
init|=
name|sic
operator|.
name|getMaxLimitPerClass
argument_list|()
decl_stmt|;
if|if
condition|(
name|numClasses
operator|==
literal|1
operator|&&
name|maxLimitPerClass
operator|!=
name|this
operator|.
name|limit
condition|)
block|{
name|sic
operator|.
name|setHasError
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|errors
operator|.
name|add
argument_list|(
literal|"subparts"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.equal"
argument_list|,
name|lblMax
operator|+
literal|" per class for<u>"
operator|+
name|sic
operator|.
name|getItype
argument_list|()
operator|.
name|getDesc
argument_list|()
operator|+
literal|"</u>"
argument_list|,
literal|"Configuration limit of "
operator|+
name|this
operator|.
name|limit
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|numClasses
operator|>
literal|1
operator|&&
operator|(
name|maxLimitPerClass
operator|*
name|numClasses
operator|)
operator|<
name|this
operator|.
name|limit
condition|)
block|{
name|sic
operator|.
name|setHasError
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|errors
operator|.
name|add
argument_list|(
literal|"subparts"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.integerGtEq"
argument_list|,
literal|"Sum of class limits<u>"
operator|+
name|sic
operator|.
name|getItype
argument_list|()
operator|.
name|getDesc
argument_list|()
operator|+
literal|"</u>"
argument_list|,
literal|"Configuration limit of "
operator|+
name|this
operator|.
name|limit
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
comment|// Check input text fields
name|checkInputfields
argument_list|(
name|request
argument_list|,
name|errors
argument_list|,
name|sic
argument_list|,
name|lblMax
argument_list|,
name|this
operator|.
name|getUnlimited
argument_list|()
operator|.
name|booleanValue
argument_list|()
argument_list|)
expr_stmt|;
comment|// Check child subparts
name|short
name|errCode
init|=
name|checkChildSubpart
argument_list|(
name|request
argument_list|,
name|errors
argument_list|,
name|sic
argument_list|,
name|lblMax
argument_list|,
name|this
operator|.
name|getUnlimited
argument_list|()
operator|.
name|booleanValue
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|errCode
operator|!=
name|NO_ERR
condition|)
block|{
name|String
name|errM
init|=
literal|"Subparts that are grouped under<u>"
operator|+
name|sic
operator|.
name|getItype
argument_list|()
operator|.
name|getDesc
argument_list|()
operator|+
literal|"</u> must<br>"
decl_stmt|;
if|if
condition|(
name|errCode
operator|==
name|ERR_NC
condition|)
name|errM
operator|+=
literal|"&nbsp;&nbsp;&nbsp; have number of classes that is a multiple of "
operator|+
name|sic
operator|.
name|getNumClasses
argument_list|()
operator|+
literal|"."
expr_stmt|;
if|if
condition|(
name|errCode
operator|==
name|ERR_CL
condition|)
name|errM
operator|+=
literal|"&nbsp;&nbsp;&nbsp; have a "
operator|+
name|lblMax
operator|.
name|toLowerCase
argument_list|()
operator|+
literal|" per class<= "
operator|+
name|lblMax
operator|.
name|toLowerCase
argument_list|()
operator|+
literal|" per class of "
operator|+
name|sic
operator|.
name|getMaxLimitPerClass
argument_list|()
operator|+
literal|"."
expr_stmt|;
if|if
condition|(
name|errCode
operator|==
name|ERR_LS
condition|)
name|errM
operator|+=
literal|"&nbsp;&nbsp;&nbsp; not accomodate lesser number of students."
expr_stmt|;
name|errors
operator|.
name|add
argument_list|(
literal|"subparts"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
name|errM
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
return|return
name|errors
return|;
block|}
comment|/**      * Checks input fields       * @param request      * @param errors      * @param sic      * @param lblMax      */
specifier|private
name|void
name|checkInputfields
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|ActionErrors
name|errors
parameter_list|,
name|SimpleItypeConfig
name|sic
parameter_list|,
name|String
name|lblMax
parameter_list|,
name|boolean
name|unlimited
parameter_list|)
block|{
name|int
name|mxlpc
init|=
name|sic
operator|.
name|getMaxLimitPerClass
argument_list|()
decl_stmt|;
name|int
name|mnlpc
init|=
name|sic
operator|.
name|getMinLimitPerClass
argument_list|()
decl_stmt|;
name|int
name|nc
init|=
name|sic
operator|.
name|getNumClasses
argument_list|()
decl_stmt|;
name|int
name|nr
init|=
name|sic
operator|.
name|getNumRooms
argument_list|()
decl_stmt|;
name|int
name|mpw
init|=
name|sic
operator|.
name|getMinPerWeek
argument_list|()
decl_stmt|;
name|float
name|rc
init|=
name|sic
operator|.
name|getRoomRatio
argument_list|()
decl_stmt|;
name|String
name|lblSubpart
init|=
literal|" for<u>"
operator|+
name|sic
operator|.
name|getItype
argument_list|()
operator|.
name|getDesc
argument_list|()
operator|+
literal|"</u>"
decl_stmt|;
name|long
name|indx
init|=
name|sic
operator|.
name|getId
argument_list|()
decl_stmt|;
name|int
name|ct
init|=
name|errors
operator|.
name|size
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|unlimited
condition|)
block|{
if|if
condition|(
name|mxlpc
operator|<
literal|0
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"subparts"
operator|+
name|indx
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.integerGtEq"
argument_list|,
name|lblMax
operator|+
literal|" per class"
operator|+
name|lblSubpart
argument_list|,
literal|"0"
argument_list|)
argument_list|)
expr_stmt|;
else|else
block|{
if|if
condition|(
name|mxlpc
operator|>
name|limit
operator|&&
name|ApplicationProperty
operator|.
name|ConfigEditCheckLimits
operator|.
name|isTrue
argument_list|()
condition|)
block|{
if|if
condition|(
name|nc
operator|>
literal|1
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"subparts"
operator|+
name|indx
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.integerLtEq"
argument_list|,
name|lblMax
operator|+
literal|" per class of "
operator|+
name|mxlpc
operator|+
name|lblSubpart
argument_list|,
literal|" Configuration limit of "
operator|+
name|limit
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"varLimits"
argument_list|)
operator|==
literal|null
condition|)
block|{
name|mnlpc
operator|=
name|mxlpc
expr_stmt|;
block|}
if|if
condition|(
name|mnlpc
operator|<
literal|0
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"subparts"
operator|+
name|indx
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.integerGtEq"
argument_list|,
literal|"Min limit per class"
operator|+
name|lblSubpart
argument_list|,
literal|"0"
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|mnlpc
operator|>
name|mxlpc
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"subparts"
operator|+
name|indx
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.integerLtEq"
argument_list|,
literal|"Min limit per class"
operator|+
name|lblSubpart
argument_list|,
literal|"Max limit per class"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Check no. of classes
if|if
condition|(
name|nc
operator|<=
literal|0
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"subparts"
operator|+
name|indx
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.integerGt"
argument_list|,
literal|"Number of classes"
operator|+
name|lblSubpart
argument_list|,
literal|"0"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Check no. of rooms
if|if
condition|(
name|nr
operator|<
literal|0
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"subparts"
operator|+
name|indx
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.integerGtEq"
argument_list|,
literal|"Number of rooms"
operator|+
name|lblSubpart
argument_list|,
literal|"0"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Check min per week
if|if
condition|(
name|mpw
operator|<
literal|0
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"subparts"
operator|+
name|indx
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.integerGtEq"
argument_list|,
literal|"Minutes per week"
operator|+
name|lblSubpart
argument_list|,
literal|"0"
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|mpw
operator|==
literal|0
operator|&&
name|nr
operator|!=
literal|0
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"subparts"
operator|+
name|indx
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
literal|"Minutes per week "
operator|+
name|lblSubpart
operator|+
literal|" can be 0 only if number of rooms is 0"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Check room ratio
if|if
condition|(
name|rc
operator|<
literal|0
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"subparts"
operator|+
name|indx
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.integerGtEq"
argument_list|,
literal|"Room ratio"
operator|+
name|lblSubpart
argument_list|,
literal|"0.0"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
else|else
block|{
comment|// Check no. of classes
if|if
condition|(
name|nc
operator|<=
literal|0
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"subparts"
operator|+
name|indx
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.integerGt"
argument_list|,
literal|"Number of classes"
operator|+
name|lblSubpart
argument_list|,
literal|"0"
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|mpw
operator|<
literal|0
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"subparts"
operator|+
name|indx
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.integerGtEq"
argument_list|,
literal|"Minutes per week"
operator|+
name|lblSubpart
argument_list|,
literal|"0"
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|errors
operator|.
name|size
argument_list|()
operator|>
name|ct
condition|)
name|sic
operator|.
name|setHasError
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
comment|/**      * Checks child subparts do not have a limit more than the parent      * and that the number of classes in the child is a multiple of the       * parent      * @param request      * @param errors      * @param sic      * @param lblMax      * @return code indicating error or no error      */
specifier|private
name|short
name|checkChildSubpart
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|ActionErrors
name|errors
parameter_list|,
name|SimpleItypeConfig
name|sic
parameter_list|,
name|String
name|lblMax
parameter_list|,
name|boolean
name|unlimited
parameter_list|)
block|{
name|Vector
name|csp
init|=
name|sic
operator|.
name|getSubparts
argument_list|()
decl_stmt|;
if|if
condition|(
name|csp
operator|!=
literal|null
operator|&&
name|csp
operator|.
name|size
argument_list|()
operator|>
literal|0
condition|)
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|csp
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|SimpleItypeConfig
name|csic
init|=
operator|(
name|SimpleItypeConfig
operator|)
name|csp
operator|.
name|elementAt
argument_list|(
name|i
argument_list|)
decl_stmt|;
name|checkInputfields
argument_list|(
name|request
argument_list|,
name|errors
argument_list|,
name|csic
argument_list|,
name|lblMax
argument_list|,
name|unlimited
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|unlimited
condition|)
block|{
if|if
condition|(
name|sic
operator|.
name|getNumClasses
argument_list|()
operator|!=
literal|0
operator|&&
name|csic
operator|.
name|getNumClasses
argument_list|()
operator|%
name|sic
operator|.
name|getNumClasses
argument_list|()
operator|!=
literal|0
condition|)
block|{
name|csic
operator|.
name|setHasError
argument_list|(
literal|true
argument_list|)
expr_stmt|;
return|return
name|ERR_NC
return|;
block|}
if|if
condition|(
name|csic
operator|.
name|getMaxLimitPerClass
argument_list|()
operator|>
name|sic
operator|.
name|getMaxLimitPerClass
argument_list|()
condition|)
block|{
name|csic
operator|.
name|setHasError
argument_list|(
literal|true
argument_list|)
expr_stmt|;
return|return
name|ERR_CL
return|;
block|}
if|if
condition|(
operator|(
name|csic
operator|.
name|getNumClasses
argument_list|()
operator|*
name|csic
operator|.
name|getMaxLimitPerClass
argument_list|()
operator|)
operator|<
operator|(
name|sic
operator|.
name|getNumClasses
argument_list|()
operator|*
name|sic
operator|.
name|getMaxLimitPerClass
argument_list|()
operator|)
condition|)
block|{
name|csic
operator|.
name|setHasError
argument_list|(
literal|true
argument_list|)
expr_stmt|;
return|return
name|ERR_LS
return|;
block|}
block|}
else|else
block|{
if|if
condition|(
name|sic
operator|.
name|getNumClasses
argument_list|()
operator|!=
literal|0
operator|&&
name|csic
operator|.
name|getNumClasses
argument_list|()
operator|%
name|sic
operator|.
name|getNumClasses
argument_list|()
operator|!=
literal|0
condition|)
block|{
name|csic
operator|.
name|setHasError
argument_list|(
literal|true
argument_list|)
expr_stmt|;
return|return
name|ERR_NC
return|;
block|}
block|}
comment|//csic.setHasError(false);
name|short
name|errCode
init|=
name|checkChildSubpart
argument_list|(
name|request
argument_list|,
name|errors
argument_list|,
name|csic
argument_list|,
name|lblMax
argument_list|,
name|unlimited
argument_list|)
decl_stmt|;
if|if
condition|(
name|errCode
operator|!=
name|NO_ERR
condition|)
return|return
name|errCode
return|;
block|}
block|}
return|return
name|NO_ERR
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
name|courseOfferingId
operator|=
literal|""
expr_stmt|;
name|subjectArea
operator|=
literal|""
expr_stmt|;
name|courseNumber
operator|=
literal|""
expr_stmt|;
name|itype
operator|=
literal|""
expr_stmt|;
name|limit
operator|=
literal|0
expr_stmt|;
name|op
operator|=
literal|""
expr_stmt|;
name|unlimited
operator|=
operator|new
name|Boolean
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|configCount
operator|=
operator|new
name|Integer
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|configId
operator|=
operator|new
name|Long
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|name
operator|=
literal|null
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
comment|/**      * Get the no. of configs for the course offering      * @return      */
specifier|public
name|Integer
name|getConfigCount
parameter_list|()
block|{
return|return
name|configCount
return|;
block|}
comment|/**      * Set the no. of configs available to the course offering      * @param configCount      */
specifier|public
name|void
name|setConfigCount
parameter_list|(
name|Integer
name|configCount
parameter_list|)
block|{
name|this
operator|.
name|configCount
operator|=
name|configCount
expr_stmt|;
block|}
comment|/**       * Returns the subjectArea.      * @return String      */
specifier|public
name|String
name|getSubjectArea
parameter_list|()
block|{
return|return
name|subjectArea
return|;
block|}
comment|/**       * Set the subjectArea.      * @param subjectArea The subject to set      */
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
comment|/**       * Returns the courseNumber.      * @return String      */
specifier|public
name|String
name|getCourseNumber
parameter_list|()
block|{
return|return
name|courseNumber
return|;
block|}
comment|/**       * Set the courseNumber.      * @param courseNumber The courseNumber to set      */
specifier|public
name|void
name|setCourseNumber
parameter_list|(
name|String
name|courseNumber
parameter_list|)
block|{
name|this
operator|.
name|courseNumber
operator|=
name|courseNumber
expr_stmt|;
block|}
comment|/**      * @return Returns the courseOfferingId.      */
specifier|public
name|String
name|getCourseOfferingId
parameter_list|()
block|{
return|return
name|courseOfferingId
return|;
block|}
comment|/**      * @param courseOfferingId The uniqueId to set.      */
specifier|public
name|void
name|setCourseOfferingId
parameter_list|(
name|String
name|courseOfferingId
parameter_list|)
block|{
name|this
operator|.
name|courseOfferingId
operator|=
name|courseOfferingId
expr_stmt|;
block|}
comment|/**      * @return Returns the limit.      */
specifier|public
name|int
name|getLimit
parameter_list|()
block|{
return|return
name|limit
return|;
block|}
comment|/**      * @param limit The limit to set.      */
specifier|public
name|void
name|setLimit
parameter_list|(
name|int
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
comment|/** 	 * @return Returns the notOffered. 	 */
specifier|public
name|Boolean
name|getNotOffered
parameter_list|()
block|{
return|return
name|notOffered
return|;
block|}
comment|/** 	 * @param notOffered The notOffered to set. 	 */
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
comment|/**      * @return Returns the itype.      */
specifier|public
name|String
name|getItype
parameter_list|()
block|{
return|return
name|itype
return|;
block|}
comment|/**      * @param itype The itype to set.      */
specifier|public
name|void
name|setItype
parameter_list|(
name|String
name|itype
parameter_list|)
block|{
name|this
operator|.
name|itype
operator|=
name|itype
expr_stmt|;
block|}
comment|/**      * @return Returns the op.      */
specifier|public
name|String
name|getOp
parameter_list|()
block|{
return|return
name|op
return|;
block|}
comment|/**      * @param op The op to set.      */
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
name|Long
name|getConfigId
parameter_list|()
block|{
return|return
name|configId
return|;
block|}
specifier|public
name|void
name|setConfigId
parameter_list|(
name|Long
name|configId
parameter_list|)
block|{
name|this
operator|.
name|configId
operator|=
name|configId
expr_stmt|;
block|}
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|name
return|;
block|}
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
block|}
end_class

end_unit

