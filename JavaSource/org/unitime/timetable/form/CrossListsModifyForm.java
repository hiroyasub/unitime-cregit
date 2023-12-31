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
comment|/**   * MyEclipse Struts  * Creation date: 04-18-2006  *   * XDoclet definition:  * @struts:form name="crossListsModifyForm"  *  * @author Zuzana Mullerova, Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|CrossListsModifyForm
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
comment|// --------------------------------------------------------- Instance Variables
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|3638385556572422628L
decl_stmt|;
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
name|instrOfferingId
decl_stmt|;
specifier|private
name|Long
name|addCourseOfferingId
decl_stmt|;
specifier|private
name|Long
name|ctrlCrsOfferingId
decl_stmt|;
specifier|private
name|String
name|instrOfferingName
decl_stmt|;
specifier|private
name|Boolean
name|ownedInstrOffr
decl_stmt|;
specifier|private
name|List
name|courseOfferingIds
decl_stmt|;
specifier|private
name|List
name|courseOfferingNames
decl_stmt|;
specifier|private
name|List
name|ownedCourse
decl_stmt|;
specifier|private
name|List
name|resvId
decl_stmt|;
specifier|private
name|List
name|limits
decl_stmt|;
specifier|private
name|List
name|requested
decl_stmt|;
specifier|private
name|List
name|projected
decl_stmt|;
specifier|private
name|List
name|lastTerm
decl_stmt|;
specifier|private
name|List
name|canDelete
decl_stmt|;
specifier|private
name|Integer
name|ioLimit
decl_stmt|;
specifier|private
name|Boolean
name|unlimited
decl_stmt|;
specifier|private
name|Long
name|readOnlyCrsOfferingId
decl_stmt|;
specifier|private
name|String
name|originalOfferings
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
name|actionAddCourseToCrossList
argument_list|()
argument_list|)
condition|)
block|{
comment|// Check Added Course
if|if
condition|(
name|this
operator|.
name|addCourseOfferingId
operator|==
literal|null
operator|||
name|this
operator|.
name|addCourseOfferingId
operator|.
name|intValue
argument_list|()
operator|<=
literal|0
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"addCourseOfferingId"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
name|MSG
operator|.
name|errorRequiredCourseOffering
argument_list|()
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
name|actionUpdateCrossLists
argument_list|()
argument_list|)
condition|)
block|{
comment|// Check controlling course
if|if
condition|(
name|this
operator|.
name|ctrlCrsOfferingId
operator|==
literal|null
operator|||
name|this
operator|.
name|ctrlCrsOfferingId
operator|.
name|intValue
argument_list|()
operator|<=
literal|0
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"ctrlCrsOfferingId"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
name|MSG
operator|.
name|errorRequiredControllingCourse
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
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
name|subjectAreaId
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
name|readOnlyCrsOfferingId
operator|=
literal|null
expr_stmt|;
name|instrOfferingName
operator|=
literal|null
expr_stmt|;
name|courseOfferingIds
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
name|courseOfferingNames
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
name|ownedCourse
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
name|resvId
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
name|limits
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
name|requested
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
name|projected
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
name|lastTerm
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
name|canDelete
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
name|originalOfferings
operator|=
literal|""
expr_stmt|;
name|ioLimit
operator|=
literal|null
expr_stmt|;
name|unlimited
operator|=
literal|null
expr_stmt|;
block|}
specifier|public
name|List
name|getCourseOfferingIds
parameter_list|()
block|{
return|return
name|courseOfferingIds
return|;
block|}
specifier|public
name|String
name|getCourseOfferingIds
parameter_list|(
name|int
name|key
parameter_list|)
block|{
return|return
name|courseOfferingIds
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
name|setCourseOfferingIds
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
name|courseOfferingIds
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
name|setCourseOfferingIds
parameter_list|(
name|List
name|courseOfferingIds
parameter_list|)
block|{
name|this
operator|.
name|courseOfferingIds
operator|=
name|courseOfferingIds
expr_stmt|;
block|}
specifier|public
name|List
name|getCourseOfferingNames
parameter_list|()
block|{
return|return
name|courseOfferingNames
return|;
block|}
specifier|public
name|String
name|getCourseOfferingNames
parameter_list|(
name|int
name|key
parameter_list|)
block|{
return|return
name|courseOfferingNames
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
name|setCourseOfferingNames
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
name|courseOfferingNames
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
name|setCourseOfferingNames
parameter_list|(
name|List
name|courseOfferingNames
parameter_list|)
block|{
name|this
operator|.
name|courseOfferingNames
operator|=
name|courseOfferingNames
expr_stmt|;
block|}
specifier|public
name|List
name|getOwnedCourse
parameter_list|()
block|{
return|return
name|ownedCourse
return|;
block|}
specifier|public
name|String
name|getOwnedCourse
parameter_list|(
name|int
name|key
parameter_list|)
block|{
return|return
name|ownedCourse
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
name|setOwnedCourse
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
name|ownedCourse
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
name|setOwnedCourse
parameter_list|(
name|List
name|ownedCourse
parameter_list|)
block|{
name|this
operator|.
name|ownedCourse
operator|=
name|ownedCourse
expr_stmt|;
block|}
specifier|public
name|List
name|getLimits
parameter_list|()
block|{
return|return
name|limits
return|;
block|}
specifier|public
name|String
name|getLimits
parameter_list|(
name|int
name|key
parameter_list|)
block|{
return|return
name|limits
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
name|setLimits
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
name|limits
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
name|setLimits
parameter_list|(
name|List
name|limits
parameter_list|)
block|{
name|this
operator|.
name|limits
operator|=
name|limits
expr_stmt|;
block|}
specifier|public
name|List
name|getResvId
parameter_list|()
block|{
return|return
name|resvId
return|;
block|}
specifier|public
name|String
name|getResvId
parameter_list|(
name|int
name|key
parameter_list|)
block|{
return|return
name|resvId
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
name|setResvId
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
name|resvId
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
name|setResvId
parameter_list|(
name|List
name|resvId
parameter_list|)
block|{
name|this
operator|.
name|resvId
operator|=
name|resvId
expr_stmt|;
block|}
specifier|public
name|List
name|getRequested
parameter_list|()
block|{
return|return
name|requested
return|;
block|}
specifier|public
name|String
name|getRequested
parameter_list|(
name|int
name|key
parameter_list|)
block|{
return|return
name|requested
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
name|setRequested
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
name|requested
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
name|setRequested
parameter_list|(
name|List
name|requested
parameter_list|)
block|{
name|this
operator|.
name|requested
operator|=
name|requested
expr_stmt|;
block|}
specifier|public
name|List
name|getProjected
parameter_list|()
block|{
return|return
name|projected
return|;
block|}
specifier|public
name|String
name|getProjected
parameter_list|(
name|int
name|key
parameter_list|)
block|{
return|return
name|projected
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
name|setProjected
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
name|projected
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
name|setProjected
parameter_list|(
name|List
name|projected
parameter_list|)
block|{
name|this
operator|.
name|projected
operator|=
name|projected
expr_stmt|;
block|}
specifier|public
name|List
name|getLastTerm
parameter_list|()
block|{
return|return
name|lastTerm
return|;
block|}
specifier|public
name|String
name|getLastTerm
parameter_list|(
name|int
name|key
parameter_list|)
block|{
return|return
name|lastTerm
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
name|setLastTerm
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
name|lastTerm
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
name|setLastTerm
parameter_list|(
name|List
name|lastTerm
parameter_list|)
block|{
name|this
operator|.
name|lastTerm
operator|=
name|lastTerm
expr_stmt|;
block|}
specifier|public
name|List
name|getCanDelete
parameter_list|()
block|{
return|return
name|canDelete
return|;
block|}
specifier|public
name|Boolean
name|getCanDelete
parameter_list|(
name|int
name|key
parameter_list|)
block|{
return|return
operator|(
name|Boolean
operator|)
name|canDelete
operator|.
name|get
argument_list|(
name|key
argument_list|)
return|;
block|}
specifier|public
name|void
name|setCanDelete
parameter_list|(
name|int
name|key
parameter_list|,
name|Boolean
name|value
parameter_list|)
block|{
name|this
operator|.
name|canDelete
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
name|setCanDelete
parameter_list|(
name|List
name|canDelete
parameter_list|)
block|{
name|this
operator|.
name|canDelete
operator|=
name|canDelete
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
name|getAddCourseOfferingId
parameter_list|()
block|{
return|return
name|addCourseOfferingId
return|;
block|}
specifier|public
name|void
name|setAddCourseOfferingId
parameter_list|(
name|Long
name|addCourseOfferingId
parameter_list|)
block|{
name|this
operator|.
name|addCourseOfferingId
operator|=
name|addCourseOfferingId
expr_stmt|;
block|}
specifier|public
name|Long
name|getReadOnlyCrsOfferingId
parameter_list|()
block|{
return|return
name|readOnlyCrsOfferingId
return|;
block|}
specifier|public
name|void
name|setReadOnlyCrsOfferingId
parameter_list|(
name|Long
name|readOnlyCrsOfferingId
parameter_list|)
block|{
name|this
operator|.
name|readOnlyCrsOfferingId
operator|=
name|readOnlyCrsOfferingId
expr_stmt|;
block|}
specifier|public
name|String
name|getOriginalOfferings
parameter_list|()
block|{
return|return
name|originalOfferings
return|;
block|}
specifier|public
name|void
name|setOriginalOfferings
parameter_list|(
name|String
name|originalOfferings
parameter_list|)
block|{
name|this
operator|.
name|originalOfferings
operator|=
name|originalOfferings
expr_stmt|;
block|}
specifier|public
name|Boolean
name|getOwnedInstrOffr
parameter_list|()
block|{
return|return
name|ownedInstrOffr
return|;
block|}
specifier|public
name|void
name|setOwnedInstrOffr
parameter_list|(
name|Boolean
name|ownedInstrOffr
parameter_list|)
block|{
name|this
operator|.
name|ownedInstrOffr
operator|=
name|ownedInstrOffr
expr_stmt|;
block|}
specifier|public
name|Integer
name|getIoLimit
parameter_list|()
block|{
return|return
name|ioLimit
return|;
block|}
specifier|public
name|void
name|setIoLimit
parameter_list|(
name|Integer
name|ioLimit
parameter_list|)
block|{
name|this
operator|.
name|ioLimit
operator|=
name|ioLimit
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
comment|/**      * Add course offering to original course offerings list      * @param co Course Offering object      */
specifier|public
name|void
name|addToOriginalCourseOfferings
parameter_list|(
name|CourseOffering
name|co
parameter_list|)
block|{
name|this
operator|.
name|originalOfferings
operator|+=
literal|" "
operator|+
name|co
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
comment|/**      * Add course offering to the list      * @param co Course Offering object      * @param resv      * @param isOwner      */
specifier|public
name|void
name|addToCourseOfferings
parameter_list|(
name|CourseOffering
name|co
parameter_list|,
name|Boolean
name|isOwner
parameter_list|,
name|Boolean
name|canDelete
parameter_list|)
block|{
name|this
operator|.
name|courseOfferingIds
operator|.
name|add
argument_list|(
name|co
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|this
operator|.
name|courseOfferingNames
operator|.
name|add
argument_list|(
operator|(
name|co
operator|.
name|getCourseNameWithTitle
argument_list|()
operator|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|ownedCourse
operator|.
name|add
argument_list|(
name|isOwner
argument_list|)
expr_stmt|;
name|this
operator|.
name|resvId
operator|.
name|add
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|this
operator|.
name|limits
operator|.
name|add
argument_list|(
name|co
operator|.
name|getReservation
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|co
operator|.
name|getReservation
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|this
operator|.
name|requested
operator|.
name|add
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|this
operator|.
name|projected
operator|.
name|add
argument_list|(
name|co
operator|.
name|getProjectedDemand
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|co
operator|.
name|getProjectedDemand
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|this
operator|.
name|lastTerm
operator|.
name|add
argument_list|(
name|co
operator|.
name|getDemand
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|co
operator|.
name|getDemand
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|this
operator|.
name|canDelete
operator|.
name|add
argument_list|(
name|canDelete
argument_list|)
expr_stmt|;
block|}
comment|/**      * Remove course offering from the list      * @param courseOfferingId Course Offering Id       */
specifier|public
name|void
name|removeFromCourseOfferings
parameter_list|(
name|String
name|courseOfferingId
parameter_list|)
block|{
name|int
name|ct
init|=
literal|0
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|this
operator|.
name|courseOfferingIds
operator|.
name|listIterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|String
name|co1
init|=
name|i
operator|.
name|next
argument_list|()
operator|.
name|toString
argument_list|()
decl_stmt|;
if|if
condition|(
name|co1
operator|.
name|equals
argument_list|(
name|courseOfferingId
argument_list|)
condition|)
block|{
name|i
operator|.
name|remove
argument_list|()
expr_stmt|;
name|this
operator|.
name|courseOfferingNames
operator|.
name|remove
argument_list|(
name|ct
argument_list|)
expr_stmt|;
name|this
operator|.
name|ownedCourse
operator|.
name|remove
argument_list|(
name|ct
argument_list|)
expr_stmt|;
name|this
operator|.
name|resvId
operator|.
name|remove
argument_list|(
name|ct
argument_list|)
expr_stmt|;
name|this
operator|.
name|limits
operator|.
name|remove
argument_list|(
name|ct
argument_list|)
expr_stmt|;
name|this
operator|.
name|requested
operator|.
name|remove
argument_list|(
name|ct
argument_list|)
expr_stmt|;
name|this
operator|.
name|projected
operator|.
name|remove
argument_list|(
name|ct
argument_list|)
expr_stmt|;
name|this
operator|.
name|lastTerm
operator|.
name|remove
argument_list|(
name|ct
argument_list|)
expr_stmt|;
name|this
operator|.
name|canDelete
operator|.
name|remove
argument_list|(
name|ct
argument_list|)
expr_stmt|;
break|break;
block|}
operator|++
name|ct
expr_stmt|;
block|}
block|}
comment|/**      * @param course      * @return -1 if not found      */
specifier|public
name|int
name|getIndex
parameter_list|(
name|String
name|courseOfferingId
parameter_list|)
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
name|courseOfferingIds
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|String
name|co1
init|=
operator|(
name|String
operator|)
name|courseOfferingIds
operator|.
name|get
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
name|co1
operator|.
name|equals
argument_list|(
name|courseOfferingId
argument_list|)
condition|)
block|{
return|return
name|i
return|;
block|}
block|}
return|return
operator|-
literal|1
return|;
block|}
block|}
end_class

end_unit

