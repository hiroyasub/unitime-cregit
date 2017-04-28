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
name|java
operator|.
name|util
operator|.
name|HashMap
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
name|model
operator|.
name|DistributionPref
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
name|Preference
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
comment|/**   * MyEclipse Struts  * Creation date: 12-14-2005  *   * XDoclet definition:  * @struts:form name="distributionPrefsForm"  *  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|DistributionPrefsForm
extends|extends
name|ActionForm
block|{
comment|// Messages
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
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|6316876654471770646L
decl_stmt|;
comment|// --------------------------------------------------------- Class Variables
specifier|public
specifier|static
specifier|final
name|String
name|SUBJ_AREA_ATTR_LIST
init|=
literal|"subjectAreaList"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|CRS_NUM_ATTR_LIST
init|=
literal|"courseNbrList"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|ITYPE_ATTR_LIST
init|=
literal|"itypeList"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|CLASS_NUM_ATTR_LIST
init|=
literal|"classNumList"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|ALL_CLASSES_SELECT
init|=
literal|"-1"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|LIST_SIZE_ATTR
init|=
literal|"listSize"
decl_stmt|;
comment|// --------------------------------------------------------- Instance Variables
comment|/** op property */
specifier|private
name|String
name|op
decl_stmt|;
comment|/** distributionDesc property */
specifier|private
name|String
name|distType
decl_stmt|;
comment|/** prefLevel property */
specifier|private
name|String
name|prefLevel
decl_stmt|;
comment|/** owner property */
specifier|private
name|String
name|owner
decl_stmt|;
comment|/** distribution pref uniqueid **/
specifier|private
name|String
name|distPrefId
decl_stmt|;
comment|/** distribution objects **/
specifier|private
name|List
name|subjectArea
decl_stmt|;
specifier|private
name|List
name|courseNbr
decl_stmt|;
specifier|private
name|List
name|itype
decl_stmt|;
specifier|private
name|List
name|classNumber
decl_stmt|;
specifier|private
name|String
name|description
decl_stmt|;
specifier|private
name|String
name|groupingDescription
decl_stmt|;
specifier|private
name|String
name|grouping
decl_stmt|;
specifier|private
name|String
name|filterSubjectAreaId
decl_stmt|;
specifier|private
name|Collection
name|filterSubjectAreas
decl_stmt|;
specifier|private
name|String
name|filterCourseNbr
decl_stmt|;
comment|// --------------------------------------------------------- Classes
comment|/** Factory to create dynamic list element for Distribution Objects */
specifier|protected
name|DynamicListObjectFactory
name|factoryDistObj
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
name|Preference
operator|.
name|BLANK_PREF_VALUE
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
name|ActionErrors
name|errors
init|=
operator|new
name|ActionErrors
argument_list|()
decl_stmt|;
comment|// Distribution Type must be selected
if|if
condition|(
name|distType
operator|==
literal|null
operator|||
name|distType
operator|.
name|equals
argument_list|(
name|Preference
operator|.
name|BLANK_PREF_VALUE
argument_list|)
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"distType"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
name|MSG
operator|.
name|errorSelectDistributionType
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/*         // Distribution Type must be selected         if(grouping==null || grouping.equals(Preference.BLANK_PREF_VALUE)) { 	        errors.add("grouping",  	                new ActionMessage( 	                        "errors.generic", "Select a structure. ") );         }         */
comment|// Distribution Pref Level must be selected
if|if
condition|(
name|prefLevel
operator|==
literal|null
operator|||
name|prefLevel
operator|.
name|equals
argument_list|(
name|Preference
operator|.
name|BLANK_PREF_VALUE
argument_list|)
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"prefLevel"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
name|MSG
operator|.
name|errorSelectDistributionPreferenceLevel
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|// Check duplicate / blank selections
if|if
condition|(
operator|!
name|checkClasses
argument_list|()
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"classes"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
name|MSG
operator|.
name|errorInvalidClassSelectionDP
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|// Save/Update clicked
if|if
condition|(
name|op
operator|.
name|equals
argument_list|(
name|MSG
operator|.
name|accessSaveNewDistributionPreference
argument_list|()
argument_list|)
operator|||
name|op
operator|.
name|equals
argument_list|(
name|MSG
operator|.
name|accessUpdateDistributionPreference
argument_list|()
argument_list|)
condition|)
block|{
comment|// At least one row of subpart should exist
if|if
condition|(
name|subjectArea
operator|.
name|size
argument_list|()
operator|==
literal|0
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"classes"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
name|MSG
operator|.
name|errorInvalidClassSelectionDPSubpart
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
comment|// At least 2 rows should exist if one is a class
if|if
condition|(
name|subjectArea
operator|.
name|size
argument_list|()
operator|==
literal|1
operator|&&
operator|!
name|classNumber
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|toString
argument_list|()
operator|.
name|equals
argument_list|(
name|ALL_CLASSES_SELECT
argument_list|)
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"classes"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
name|MSG
operator|.
name|errorInvalidClassSelectionDPMinTwoClasses
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
comment|// Class cannot be specified if its subpart is already specified
if|if
condition|(
name|subjectArea
operator|.
name|size
argument_list|()
operator|>
literal|1
condition|)
block|{
name|HashMap
name|mapSubparts
init|=
operator|new
name|HashMap
argument_list|()
decl_stmt|;
name|HashMap
name|mapClasses
init|=
operator|new
name|HashMap
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
name|subjectArea
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|String
name|subpart
init|=
name|itype
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|.
name|toString
argument_list|()
decl_stmt|;
name|String
name|classNum
init|=
name|classNumber
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|.
name|toString
argument_list|()
decl_stmt|;
if|if
condition|(
name|classNum
operator|.
name|equals
argument_list|(
name|ALL_CLASSES_SELECT
argument_list|)
condition|)
block|{
if|if
condition|(
name|mapClasses
operator|.
name|get
argument_list|(
name|subpart
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"classes"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
name|MSG
operator|.
name|errorInvalidClassSelectionDPIndividualClass
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
break|break;
block|}
else|else
name|mapSubparts
operator|.
name|put
argument_list|(
name|subpart
argument_list|,
name|classNum
argument_list|)
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|mapSubparts
operator|.
name|get
argument_list|(
name|subpart
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"classes"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
name|MSG
operator|.
name|errorInvalidClassSelectionDPIndividualClass
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
break|break;
block|}
else|else
name|mapClasses
operator|.
name|put
argument_list|(
name|subpart
argument_list|,
name|classNum
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
return|return
name|errors
return|;
block|}
comment|/**      * Check that classes are not blank and are valid      * @return      */
specifier|public
name|boolean
name|checkClasses
parameter_list|()
block|{
name|HashMap
name|map
init|=
operator|new
name|HashMap
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
name|subjectArea
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
comment|// Check Blanks
if|if
condition|(
name|subjectArea
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|==
literal|null
operator|||
name|subjectArea
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|.
name|toString
argument_list|()
operator|.
name|equals
argument_list|(
name|Preference
operator|.
name|BLANK_PREF_VALUE
argument_list|)
condition|)
return|return
literal|false
return|;
if|if
condition|(
name|courseNbr
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|==
literal|null
operator|||
name|courseNbr
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|.
name|toString
argument_list|()
operator|.
name|equals
argument_list|(
name|Preference
operator|.
name|BLANK_PREF_VALUE
argument_list|)
condition|)
return|return
literal|false
return|;
if|if
condition|(
name|itype
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|==
literal|null
operator|||
name|itype
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|.
name|toString
argument_list|()
operator|.
name|equals
argument_list|(
name|Preference
operator|.
name|BLANK_PREF_VALUE
argument_list|)
condition|)
return|return
literal|false
return|;
if|if
condition|(
name|classNumber
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|==
literal|null
operator|||
name|classNumber
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|.
name|toString
argument_list|()
operator|.
name|equals
argument_list|(
name|Preference
operator|.
name|BLANK_PREF_VALUE
argument_list|)
condition|)
return|return
literal|false
return|;
comment|// Check Duplicates
name|String
name|str
init|=
name|subjectArea
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|.
name|toString
argument_list|()
operator|+
name|courseNbr
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|.
name|toString
argument_list|()
operator|+
name|itype
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|.
name|toString
argument_list|()
operator|+
name|classNumber
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|.
name|toString
argument_list|()
decl_stmt|;
if|if
condition|(
name|map
operator|.
name|get
argument_list|(
name|str
argument_list|)
operator|!=
literal|null
condition|)
return|return
literal|false
return|;
name|map
operator|.
name|put
argument_list|(
name|str
argument_list|,
literal|"1"
argument_list|)
expr_stmt|;
block|}
return|return
literal|true
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
name|op
operator|=
literal|""
expr_stmt|;
name|distPrefId
operator|=
literal|""
expr_stmt|;
name|distType
operator|=
name|Preference
operator|.
name|BLANK_PREF_VALUE
expr_stmt|;
name|prefLevel
operator|=
name|Preference
operator|.
name|BLANK_PREF_VALUE
expr_stmt|;
name|owner
operator|=
literal|""
expr_stmt|;
name|description
operator|=
literal|""
expr_stmt|;
name|groupingDescription
operator|=
literal|""
expr_stmt|;
name|subjectArea
operator|=
name|DynamicList
operator|.
name|getInstance
argument_list|(
operator|new
name|ArrayList
argument_list|()
argument_list|,
name|factoryDistObj
argument_list|)
expr_stmt|;
name|courseNbr
operator|=
name|DynamicList
operator|.
name|getInstance
argument_list|(
operator|new
name|ArrayList
argument_list|()
argument_list|,
name|factoryDistObj
argument_list|)
expr_stmt|;
name|itype
operator|=
name|DynamicList
operator|.
name|getInstance
argument_list|(
operator|new
name|ArrayList
argument_list|()
argument_list|,
name|factoryDistObj
argument_list|)
expr_stmt|;
name|classNumber
operator|=
name|DynamicList
operator|.
name|getInstance
argument_list|(
operator|new
name|ArrayList
argument_list|()
argument_list|,
name|factoryDistObj
argument_list|)
expr_stmt|;
name|grouping
operator|=
name|Preference
operator|.
name|BLANK_PREF_VALUE
expr_stmt|;
name|filterSubjectAreaId
operator|=
literal|null
expr_stmt|;
name|filterCourseNbr
operator|=
literal|null
expr_stmt|;
name|filterSubjectAreas
operator|=
operator|new
name|ArrayList
argument_list|()
expr_stmt|;
block|}
comment|/**      * @return Returns the distPrefId.      */
specifier|public
name|String
name|getDistPrefId
parameter_list|()
block|{
return|return
name|distPrefId
return|;
block|}
comment|/**      * @param distPrefId The distPrefId to set.      */
specifier|public
name|void
name|setDistPrefId
parameter_list|(
name|String
name|distPrefId
parameter_list|)
block|{
name|this
operator|.
name|distPrefId
operator|=
name|distPrefId
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
comment|/**       * Returns the prefLevel.      * @return String      */
specifier|public
name|String
name|getPrefLevel
parameter_list|()
block|{
return|return
name|prefLevel
return|;
block|}
comment|/**       * Set the prefLevel.      * @param prefLevel The prefLevel to set      */
specifier|public
name|void
name|setPrefLevel
parameter_list|(
name|String
name|prefLevel
parameter_list|)
block|{
name|this
operator|.
name|prefLevel
operator|=
name|prefLevel
expr_stmt|;
block|}
comment|/**       * Returns the distType.      * @return String      */
specifier|public
name|String
name|getDistType
parameter_list|()
block|{
return|return
name|distType
return|;
block|}
comment|/**       * Set the distType.      * @param distType The distType to set      */
specifier|public
name|void
name|setDistType
parameter_list|(
name|String
name|distType
parameter_list|)
block|{
name|this
operator|.
name|distType
operator|=
name|distType
expr_stmt|;
block|}
comment|/**      * @return Returns the owner.      */
specifier|public
name|String
name|getOwner
parameter_list|()
block|{
return|return
name|owner
return|;
block|}
comment|/**      * @param owner The owner to set.      */
specifier|public
name|void
name|setOwner
parameter_list|(
name|String
name|owner
parameter_list|)
block|{
name|this
operator|.
name|owner
operator|=
name|owner
expr_stmt|;
block|}
comment|/**      * @return Returns the subjectArea.      */
specifier|public
name|List
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
name|List
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
comment|/**      * @return Returns the subjectArea.      */
specifier|public
name|String
name|getSubjectArea
parameter_list|(
name|int
name|key
parameter_list|)
block|{
return|return
name|subjectArea
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
comment|/**      * @param key The key to set.      * @param value The value to set.      */
specifier|public
name|void
name|setSubjectArea
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
name|subjectArea
operator|.
name|set
argument_list|(
name|key
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
comment|/**      * @param value      */
specifier|public
name|void
name|addToSubjectArea
parameter_list|(
name|String
name|value
parameter_list|)
block|{
name|this
operator|.
name|subjectArea
operator|.
name|add
argument_list|(
name|value
argument_list|)
expr_stmt|;
block|}
comment|/**      * @return Returns the courseNbr.      */
specifier|public
name|List
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
name|List
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
comment|/**      * @return Returns the courseNbr.      */
specifier|public
name|String
name|getCourseNbr
parameter_list|(
name|int
name|key
parameter_list|)
block|{
return|return
name|courseNbr
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
comment|/**      * @param key The key to set.      * @param value The value to set.      */
specifier|public
name|void
name|setCourseNbr
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
name|courseNbr
operator|.
name|set
argument_list|(
name|key
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
comment|/**      * @param value      */
specifier|public
name|void
name|addToCourseNbr
parameter_list|(
name|String
name|value
parameter_list|)
block|{
name|this
operator|.
name|courseNbr
operator|.
name|add
argument_list|(
name|value
argument_list|)
expr_stmt|;
block|}
comment|/**      * @return Returns the itype.      */
specifier|public
name|List
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
name|List
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
comment|/**      * @return Returns the itype.      */
specifier|public
name|String
name|getItype
parameter_list|(
name|int
name|key
parameter_list|)
block|{
return|return
name|itype
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
comment|/**      * @param key The key to set.      * @param value The value to set.      */
specifier|public
name|void
name|setItype
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
name|itype
operator|.
name|set
argument_list|(
name|key
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
comment|/**      * @param value      */
specifier|public
name|void
name|addToItype
parameter_list|(
name|String
name|value
parameter_list|)
block|{
name|this
operator|.
name|itype
operator|.
name|add
argument_list|(
name|value
argument_list|)
expr_stmt|;
block|}
comment|/**      * @return Returns the classNumber.      */
specifier|public
name|List
name|getClassNumber
parameter_list|()
block|{
return|return
name|classNumber
return|;
block|}
comment|/**      * @param classNumber The classNumber to set.      */
specifier|public
name|void
name|setClassNumber
parameter_list|(
name|List
name|classNumber
parameter_list|)
block|{
name|this
operator|.
name|classNumber
operator|=
name|classNumber
expr_stmt|;
block|}
comment|/**      * @return Returns the classNumber.      */
specifier|public
name|String
name|getClassNumber
parameter_list|(
name|int
name|key
parameter_list|)
block|{
return|return
name|classNumber
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
comment|/**      * @param key The key to set.      * @param value The value to set.      */
specifier|public
name|void
name|setClassNumber
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
name|classNumber
operator|.
name|set
argument_list|(
name|key
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
comment|/**      * @param value      */
specifier|public
name|void
name|addToClassNumber
parameter_list|(
name|String
name|value
parameter_list|)
block|{
name|this
operator|.
name|classNumber
operator|.
name|add
argument_list|(
name|value
argument_list|)
expr_stmt|;
block|}
specifier|public
name|String
name|getDescription
parameter_list|()
block|{
return|return
name|description
return|;
block|}
specifier|public
name|void
name|setDescription
parameter_list|(
name|String
name|description
parameter_list|)
block|{
name|this
operator|.
name|description
operator|=
name|description
expr_stmt|;
block|}
specifier|public
name|String
name|getGroupingDescription
parameter_list|()
block|{
return|return
name|groupingDescription
return|;
block|}
specifier|public
name|void
name|setGroupingDescription
parameter_list|(
name|String
name|groupingDescription
parameter_list|)
block|{
name|this
operator|.
name|groupingDescription
operator|=
name|groupingDescription
expr_stmt|;
block|}
specifier|public
name|String
name|getGrouping
parameter_list|()
block|{
return|return
name|grouping
return|;
block|}
specifier|public
name|DistributionPref
operator|.
name|Structure
name|getStructure
parameter_list|()
block|{
for|for
control|(
name|DistributionPref
operator|.
name|Structure
name|structure
range|:
name|DistributionPref
operator|.
name|Structure
operator|.
name|values
argument_list|()
control|)
block|{
if|if
condition|(
name|structure
operator|.
name|getName
argument_list|()
operator|.
name|equals
argument_list|(
name|grouping
argument_list|)
condition|)
return|return
name|structure
return|;
block|}
return|return
name|DistributionPref
operator|.
name|Structure
operator|.
name|AllClasses
return|;
block|}
specifier|public
name|void
name|setGrouping
parameter_list|(
name|String
name|grouping
parameter_list|)
block|{
name|this
operator|.
name|grouping
operator|=
name|grouping
expr_stmt|;
block|}
specifier|public
name|void
name|setStructure
parameter_list|(
name|DistributionPref
operator|.
name|Structure
name|structure
parameter_list|)
block|{
name|this
operator|.
name|grouping
operator|=
operator|(
name|structure
operator|==
literal|null
condition|?
name|DistributionPref
operator|.
name|Structure
operator|.
name|AllClasses
operator|.
name|getName
argument_list|()
else|:
name|structure
operator|.
name|getName
argument_list|()
operator|)
expr_stmt|;
block|}
specifier|public
name|String
index|[]
name|getGroupings
parameter_list|()
block|{
name|String
index|[]
name|ret
init|=
operator|new
name|String
index|[
name|DistributionPref
operator|.
name|Structure
operator|.
name|values
argument_list|()
operator|.
name|length
index|]
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
name|DistributionPref
operator|.
name|Structure
operator|.
name|values
argument_list|()
operator|.
name|length
condition|;
name|i
operator|++
control|)
name|ret
index|[
name|i
index|]
operator|=
name|DistributionPref
operator|.
name|Structure
operator|.
name|values
argument_list|()
index|[
name|i
index|]
operator|.
name|getName
argument_list|()
expr_stmt|;
return|return
name|ret
return|;
block|}
comment|/**      * @param subjectAreaId Subject area of the new class (optional, null otherwise)      * Add a blank row      */
specifier|public
name|void
name|addNewClass
parameter_list|(
name|String
name|subjectAreaId
parameter_list|)
block|{
if|if
condition|(
name|subjectAreaId
operator|==
literal|null
operator|||
name|subjectAreaId
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
name|addToSubjectArea
argument_list|(
name|Preference
operator|.
name|BLANK_PREF_VALUE
argument_list|)
expr_stmt|;
else|else
name|addToSubjectArea
argument_list|(
name|subjectAreaId
argument_list|)
expr_stmt|;
name|addToCourseNbr
argument_list|(
name|Preference
operator|.
name|BLANK_PREF_VALUE
argument_list|)
expr_stmt|;
name|addToItype
argument_list|(
name|Preference
operator|.
name|BLANK_PREF_VALUE
argument_list|)
expr_stmt|;
name|addToClassNumber
argument_list|(
name|Preference
operator|.
name|BLANK_PREF_VALUE
argument_list|)
expr_stmt|;
block|}
comment|/**      * Remove object specified by the index from the lists      * @param key      */
specifier|public
name|void
name|removeFromLists
parameter_list|(
name|int
name|key
parameter_list|)
block|{
name|this
operator|.
name|subjectArea
operator|.
name|remove
argument_list|(
name|key
argument_list|)
expr_stmt|;
name|this
operator|.
name|courseNbr
operator|.
name|remove
argument_list|(
name|key
argument_list|)
expr_stmt|;
name|this
operator|.
name|itype
operator|.
name|remove
argument_list|(
name|key
argument_list|)
expr_stmt|;
name|this
operator|.
name|classNumber
operator|.
name|remove
argument_list|(
name|key
argument_list|)
expr_stmt|;
block|}
comment|/**      * Swaps two list elements for the specified indexes      * @param index      * @param index2      */
specifier|public
name|void
name|swap
parameter_list|(
name|int
name|index
parameter_list|,
name|int
name|index2
parameter_list|)
block|{
name|Object
name|objSa
init|=
name|subjectArea
operator|.
name|get
argument_list|(
name|index
argument_list|)
decl_stmt|;
name|Object
name|objCo
init|=
name|courseNbr
operator|.
name|get
argument_list|(
name|index
argument_list|)
decl_stmt|;
name|Object
name|objIt
init|=
name|itype
operator|.
name|get
argument_list|(
name|index
argument_list|)
decl_stmt|;
name|Object
name|objCl
init|=
name|classNumber
operator|.
name|get
argument_list|(
name|index
argument_list|)
decl_stmt|;
name|Object
name|objSa2
init|=
name|subjectArea
operator|.
name|get
argument_list|(
name|index2
argument_list|)
decl_stmt|;
name|Object
name|objCo2
init|=
name|courseNbr
operator|.
name|get
argument_list|(
name|index2
argument_list|)
decl_stmt|;
name|Object
name|objIt2
init|=
name|itype
operator|.
name|get
argument_list|(
name|index2
argument_list|)
decl_stmt|;
name|Object
name|objCl2
init|=
name|classNumber
operator|.
name|get
argument_list|(
name|index2
argument_list|)
decl_stmt|;
name|subjectArea
operator|.
name|set
argument_list|(
name|index
argument_list|,
name|objSa2
argument_list|)
expr_stmt|;
name|subjectArea
operator|.
name|set
argument_list|(
name|index2
argument_list|,
name|objSa
argument_list|)
expr_stmt|;
name|courseNbr
operator|.
name|set
argument_list|(
name|index
argument_list|,
name|objCo2
argument_list|)
expr_stmt|;
name|courseNbr
operator|.
name|set
argument_list|(
name|index2
argument_list|,
name|objCo
argument_list|)
expr_stmt|;
name|itype
operator|.
name|set
argument_list|(
name|index
argument_list|,
name|objIt2
argument_list|)
expr_stmt|;
name|itype
operator|.
name|set
argument_list|(
name|index2
argument_list|,
name|objIt
argument_list|)
expr_stmt|;
name|classNumber
operator|.
name|set
argument_list|(
name|index
argument_list|,
name|objCl2
argument_list|)
expr_stmt|;
name|classNumber
operator|.
name|set
argument_list|(
name|index2
argument_list|,
name|objCl
argument_list|)
expr_stmt|;
block|}
specifier|public
name|String
name|getFilterSubjectAreaId
parameter_list|()
block|{
return|return
name|filterSubjectAreaId
return|;
block|}
specifier|public
name|void
name|setFilterSubjectAreaId
parameter_list|(
name|String
name|filterSubjectAreaId
parameter_list|)
block|{
name|this
operator|.
name|filterSubjectAreaId
operator|=
name|filterSubjectAreaId
expr_stmt|;
block|}
specifier|public
name|String
name|getFilterCourseNbr
parameter_list|()
block|{
return|return
name|filterCourseNbr
return|;
block|}
specifier|public
name|void
name|setFilterCourseNbr
parameter_list|(
name|String
name|filterCourseNbr
parameter_list|)
block|{
name|this
operator|.
name|filterCourseNbr
operator|=
name|filterCourseNbr
expr_stmt|;
block|}
specifier|public
name|Collection
name|getFilterSubjectAreas
parameter_list|()
block|{
return|return
name|filterSubjectAreas
return|;
block|}
specifier|public
name|void
name|setFilterSubjectAreas
parameter_list|(
name|Collection
name|filterSubjectAreas
parameter_list|)
block|{
name|this
operator|.
name|filterSubjectAreas
operator|=
name|filterSubjectAreas
expr_stmt|;
block|}
block|}
end_class

end_unit

