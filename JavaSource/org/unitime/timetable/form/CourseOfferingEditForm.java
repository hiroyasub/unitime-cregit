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
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Matcher
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Pattern
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
name|model
operator|.
name|SubjectArea
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
name|SubjectAreaDAO
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
comment|/**  * MyEclipse Struts  * Creation date: 07-25-2006  *  * XDoclet definition:  * @struts:form name="courseOfferingEditForm"  *  * @author Tomas Muller, Stephanie Schluttenhofer, Zuzana Mullerova  */
end_comment

begin_class
specifier|public
class|class
name|CourseOfferingEditForm
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
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|5719027599139781262L
decl_stmt|;
comment|// --------------------------------------------------------- Instance Variables
specifier|private
name|String
name|op
decl_stmt|;
specifier|private
name|boolean
name|add
init|=
literal|false
decl_stmt|;
specifier|private
name|Long
name|subjectAreaId
decl_stmt|;
specifier|private
name|Long
name|courseOfferingId
decl_stmt|;
specifier|private
name|Long
name|instrOfferingId
decl_stmt|;
specifier|private
name|String
name|courseName
decl_stmt|;
specifier|private
name|String
name|courseNbr
decl_stmt|;
specifier|private
name|String
name|title
decl_stmt|;
specifier|private
name|String
name|scheduleBookNote
decl_stmt|;
specifier|private
name|Long
name|demandCourseOfferingId
decl_stmt|;
specifier|private
name|boolean
name|allowDemandCourseOfferings
decl_stmt|;
specifier|private
name|Long
name|consent
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
name|isControl
decl_stmt|;
specifier|private
name|Boolean
name|ioNotOffered
decl_stmt|;
specifier|private
name|String
name|catalogLinkLabel
decl_stmt|;
specifier|private
name|String
name|catalogLinkLocation
decl_stmt|;
specifier|private
name|Boolean
name|byReservationOnly
decl_stmt|;
specifier|private
name|List
name|instructors
decl_stmt|;
specifier|private
name|String
name|wkEnroll
decl_stmt|,
name|wkChange
decl_stmt|,
name|wkDrop
decl_stmt|;
specifier|private
name|Integer
name|wkEnrollDefault
decl_stmt|,
name|wkChangeDefault
decl_stmt|,
name|wkDropDefault
decl_stmt|;
specifier|private
name|String
name|weekStartDayOfWeek
decl_stmt|;
specifier|private
name|String
name|courseTypeId
decl_stmt|;
specifier|private
name|String
name|externalId
decl_stmt|;
comment|// --------------------------------------------------------- Methods
comment|/**      * Method validate      * @param mapping      * @param request      * @return ActionErrors      */
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
name|getCourseNbr
argument_list|()
operator|!=
literal|null
operator|&&
name|ApplicationProperty
operator|.
name|CourseOfferingNumberUpperCase
operator|.
name|isTrue
argument_list|()
condition|)
block|{
name|setCourseNbr
argument_list|(
name|getCourseNbr
argument_list|()
operator|.
name|toUpperCase
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|op
operator|.
name|equals
argument_list|(
name|MSG
operator|.
name|actionUpdateCourseOffering
argument_list|()
argument_list|)
operator|||
name|op
operator|.
name|equals
argument_list|(
name|MSG
operator|.
name|actionSaveCourseOffering
argument_list|()
argument_list|)
condition|)
block|{
if|if
condition|(
name|subjectAreaId
operator|==
literal|null
operator|||
name|subjectAreaId
operator|==
literal|0
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"subjectAreaId"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
name|MSG
operator|.
name|errorSubjectRequired
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|courseNbr
operator|==
literal|null
operator|||
name|courseNbr
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"courseNbr"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
name|MSG
operator|.
name|errorCourseNumberRequired
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|String
name|courseNbrRegex
init|=
name|ApplicationProperty
operator|.
name|CourseOfferingNumberPattern
operator|.
name|value
argument_list|()
decl_stmt|;
name|String
name|courseNbrInfo
init|=
name|ApplicationProperty
operator|.
name|CourseOfferingNumberPatternInfo
operator|.
name|value
argument_list|()
decl_stmt|;
try|try
block|{
name|Pattern
name|pattern
init|=
name|Pattern
operator|.
name|compile
argument_list|(
name|courseNbrRegex
argument_list|)
decl_stmt|;
name|Matcher
name|matcher
init|=
name|pattern
operator|.
name|matcher
argument_list|(
name|courseNbr
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|matcher
operator|.
name|find
argument_list|()
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"courseNbr"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
name|courseNbrInfo
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"courseNbr"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
name|MSG
operator|.
name|errorCourseNumberCannotBeMatched
argument_list|(
name|courseNbrRegex
argument_list|,
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|ApplicationProperty
operator|.
name|CourseOfferingNumberMustBeUnique
operator|.
name|isTrue
argument_list|()
condition|)
block|{
name|SubjectArea
name|sa
init|=
operator|new
name|SubjectAreaDAO
argument_list|()
operator|.
name|get
argument_list|(
name|subjectAreaId
argument_list|)
decl_stmt|;
name|CourseOffering
name|co
init|=
name|CourseOffering
operator|.
name|findBySessionSubjAreaAbbvCourseNbr
argument_list|(
name|sa
operator|.
name|getSessionId
argument_list|()
argument_list|,
name|sa
operator|.
name|getSubjectAreaAbbreviation
argument_list|()
argument_list|,
name|courseNbr
argument_list|)
decl_stmt|;
if|if
condition|(
name|add
operator|&&
name|co
operator|!=
literal|null
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"courseNbr"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
name|MSG
operator|.
name|errorCourseCannotBeCreated
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
operator|!
name|add
operator|&&
name|co
operator|!=
literal|null
operator|&&
operator|!
name|co
operator|.
name|getUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
name|courseOfferingId
argument_list|)
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"courseNbr"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
name|MSG
operator|.
name|errorCourseCannotBeRenamed
argument_list|()
argument_list|)
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
specifier|protected
name|DynamicListObjectFactory
name|factory
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
name|Preference
operator|.
name|BLANK_PREF_VALUE
argument_list|)
return|;
block|}
block|}
decl_stmt|;
comment|/**      * Method reset      * @param mapping      * @param request      */
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
name|subjectAreaId
operator|=
literal|null
expr_stmt|;
name|courseOfferingId
operator|=
literal|null
expr_stmt|;
name|instrOfferingId
operator|=
literal|null
expr_stmt|;
name|courseName
operator|=
literal|""
expr_stmt|;
name|title
operator|=
literal|""
expr_stmt|;
name|scheduleBookNote
operator|=
literal|""
expr_stmt|;
name|demandCourseOfferingId
operator|=
literal|null
expr_stmt|;
name|consent
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
name|courseNbr
operator|=
literal|""
expr_stmt|;
name|ioNotOffered
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
name|instructors
operator|=
name|DynamicList
operator|.
name|getInstance
argument_list|(
operator|new
name|ArrayList
argument_list|()
argument_list|,
name|factory
argument_list|)
expr_stmt|;
name|byReservationOnly
operator|=
literal|false
expr_stmt|;
name|wkEnroll
operator|=
literal|null
expr_stmt|;
name|wkChange
operator|=
literal|null
expr_stmt|;
name|wkDrop
operator|=
literal|null
expr_stmt|;
name|wkEnrollDefault
operator|=
literal|null
expr_stmt|;
name|wkChangeDefault
operator|=
literal|null
expr_stmt|;
name|wkDropDefault
operator|=
literal|null
expr_stmt|;
name|weekStartDayOfWeek
operator|=
literal|null
expr_stmt|;
name|courseTypeId
operator|=
literal|null
expr_stmt|;
name|add
operator|=
literal|false
expr_stmt|;
name|externalId
operator|=
literal|null
expr_stmt|;
block|}
specifier|public
name|boolean
name|isAdd
parameter_list|()
block|{
return|return
name|add
return|;
block|}
specifier|public
name|void
name|setAdd
parameter_list|(
name|boolean
name|add
parameter_list|)
block|{
name|this
operator|.
name|add
operator|=
name|add
expr_stmt|;
block|}
specifier|public
name|Long
name|getCourseOfferingId
parameter_list|()
block|{
return|return
name|courseOfferingId
return|;
block|}
specifier|public
name|void
name|setCourseOfferingId
parameter_list|(
name|Long
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
specifier|public
name|String
name|getScheduleBookNote
parameter_list|()
block|{
return|return
name|scheduleBookNote
return|;
block|}
specifier|public
name|void
name|setScheduleBookNote
parameter_list|(
name|String
name|scheduleBookNote
parameter_list|)
block|{
name|this
operator|.
name|scheduleBookNote
operator|=
name|scheduleBookNote
expr_stmt|;
block|}
specifier|public
name|String
name|getTitle
parameter_list|()
block|{
return|return
name|title
return|;
block|}
specifier|public
name|void
name|setTitle
parameter_list|(
name|String
name|title
parameter_list|)
block|{
name|this
operator|.
name|title
operator|=
name|title
expr_stmt|;
block|}
specifier|public
name|String
name|getCourseName
parameter_list|()
block|{
return|return
name|courseName
return|;
block|}
specifier|public
name|void
name|setCourseName
parameter_list|(
name|String
name|courseName
parameter_list|)
block|{
name|this
operator|.
name|courseName
operator|=
name|courseName
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
name|getDemandCourseOfferingId
parameter_list|()
block|{
return|return
name|demandCourseOfferingId
return|;
block|}
specifier|public
name|void
name|setDemandCourseOfferingId
parameter_list|(
name|Long
name|demandCourseOfferingId
parameter_list|)
block|{
name|this
operator|.
name|demandCourseOfferingId
operator|=
name|demandCourseOfferingId
expr_stmt|;
block|}
specifier|public
name|boolean
name|getAllowDemandCourseOfferings
parameter_list|()
block|{
return|return
name|allowDemandCourseOfferings
return|;
block|}
specifier|public
name|void
name|setAllowDemandCourseOfferings
parameter_list|(
name|boolean
name|allowDemandCourseOfferings
parameter_list|)
block|{
name|this
operator|.
name|allowDemandCourseOfferings
operator|=
name|allowDemandCourseOfferings
expr_stmt|;
block|}
specifier|public
name|Long
name|getConsent
parameter_list|()
block|{
return|return
name|consent
return|;
block|}
specifier|public
name|void
name|setConsent
parameter_list|(
name|Long
name|consent
parameter_list|)
block|{
name|this
operator|.
name|consent
operator|=
name|consent
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
if|if
condition|(
name|ApplicationProperty
operator|.
name|CourseOfferingNumberUpperCase
operator|.
name|isTrue
argument_list|()
condition|)
name|courseNbr
operator|=
name|courseNbr
operator|.
name|toUpperCase
argument_list|()
expr_stmt|;
name|this
operator|.
name|courseNbr
operator|=
name|courseNbr
expr_stmt|;
block|}
specifier|public
name|Boolean
name|getIsControl
parameter_list|()
block|{
return|return
name|isControl
return|;
block|}
specifier|public
name|void
name|setIsControl
parameter_list|(
name|Boolean
name|isControl
parameter_list|)
block|{
name|this
operator|.
name|isControl
operator|=
name|isControl
expr_stmt|;
block|}
specifier|public
name|Boolean
name|getIoNotOffered
parameter_list|()
block|{
return|return
name|ioNotOffered
return|;
block|}
specifier|public
name|void
name|setIoNotOffered
parameter_list|(
name|Boolean
name|ioNotOffered
parameter_list|)
block|{
name|this
operator|.
name|ioNotOffered
operator|=
name|ioNotOffered
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
specifier|public
name|List
name|getInstructors
parameter_list|()
block|{
return|return
name|instructors
return|;
block|}
specifier|public
name|String
name|getInstructors
parameter_list|(
name|int
name|key
parameter_list|)
block|{
return|return
name|instructors
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
name|setInstructors
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
name|instructors
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
name|setInstructors
parameter_list|(
name|List
name|instructors
parameter_list|)
block|{
name|this
operator|.
name|instructors
operator|=
name|instructors
expr_stmt|;
block|}
specifier|public
name|boolean
name|isByReservationOnly
parameter_list|()
block|{
return|return
name|byReservationOnly
return|;
block|}
specifier|public
name|void
name|setByReservationOnly
parameter_list|(
name|boolean
name|byReservationOnly
parameter_list|)
block|{
name|this
operator|.
name|byReservationOnly
operator|=
name|byReservationOnly
expr_stmt|;
block|}
specifier|public
name|String
name|getWkEnroll
parameter_list|()
block|{
return|return
name|wkEnroll
return|;
block|}
specifier|public
name|void
name|setWkEnroll
parameter_list|(
name|String
name|wkEnroll
parameter_list|)
block|{
name|this
operator|.
name|wkEnroll
operator|=
name|wkEnroll
expr_stmt|;
block|}
specifier|public
name|Integer
name|getWkEnrollDefault
parameter_list|()
block|{
return|return
name|wkEnrollDefault
return|;
block|}
specifier|public
name|void
name|setWkEnrollDefault
parameter_list|(
name|Integer
name|wkEnrollDefault
parameter_list|)
block|{
name|this
operator|.
name|wkEnrollDefault
operator|=
name|wkEnrollDefault
expr_stmt|;
block|}
specifier|public
name|String
name|getWkChange
parameter_list|()
block|{
return|return
name|wkChange
return|;
block|}
specifier|public
name|void
name|setWkChange
parameter_list|(
name|String
name|wkChange
parameter_list|)
block|{
name|this
operator|.
name|wkChange
operator|=
name|wkChange
expr_stmt|;
block|}
specifier|public
name|Integer
name|getWkChangeDefault
parameter_list|()
block|{
return|return
name|wkChangeDefault
return|;
block|}
specifier|public
name|void
name|setWkChangeDefault
parameter_list|(
name|Integer
name|wkChangeDefault
parameter_list|)
block|{
name|this
operator|.
name|wkChangeDefault
operator|=
name|wkChangeDefault
expr_stmt|;
block|}
specifier|public
name|String
name|getWkDrop
parameter_list|()
block|{
return|return
name|wkDrop
return|;
block|}
specifier|public
name|void
name|setWkDrop
parameter_list|(
name|String
name|wkDrop
parameter_list|)
block|{
name|this
operator|.
name|wkDrop
operator|=
name|wkDrop
expr_stmt|;
block|}
specifier|public
name|Integer
name|getWkDropDefault
parameter_list|()
block|{
return|return
name|wkDropDefault
return|;
block|}
specifier|public
name|void
name|setWkDropDefault
parameter_list|(
name|Integer
name|wkDropDefault
parameter_list|)
block|{
name|this
operator|.
name|wkDropDefault
operator|=
name|wkDropDefault
expr_stmt|;
block|}
specifier|public
name|String
name|getWeekStartDayOfWeek
parameter_list|()
block|{
return|return
name|weekStartDayOfWeek
return|;
block|}
specifier|public
name|void
name|setWeekStartDayOfWeek
parameter_list|(
name|String
name|weekStartDayOfWeek
parameter_list|)
block|{
name|this
operator|.
name|weekStartDayOfWeek
operator|=
name|weekStartDayOfWeek
expr_stmt|;
block|}
specifier|public
name|String
name|getCourseTypeId
parameter_list|()
block|{
return|return
name|courseTypeId
return|;
block|}
specifier|public
name|void
name|setCourseTypeId
parameter_list|(
name|String
name|courseTypeId
parameter_list|)
block|{
name|this
operator|.
name|courseTypeId
operator|=
name|courseTypeId
expr_stmt|;
block|}
specifier|public
name|String
name|getExternalId
parameter_list|()
block|{
return|return
name|externalId
return|;
block|}
specifier|public
name|void
name|setExternalId
parameter_list|(
name|String
name|externalId
parameter_list|)
block|{
name|this
operator|.
name|externalId
operator|=
name|externalId
expr_stmt|;
block|}
block|}
end_class

end_unit

