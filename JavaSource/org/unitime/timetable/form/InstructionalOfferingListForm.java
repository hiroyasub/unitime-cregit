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
name|Map
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|TreeSet
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
name|commons
operator|.
name|Debug
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
name|comparators
operator|.
name|ClassCourseComparator
import|;
end_import

begin_comment
comment|/**  * @author Stephanie Schluttenhofer, Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|InstructionalOfferingListForm
extends|extends
name|ActionForm
implements|implements
name|InstructionalOfferingListFormInterface
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
operator|-
literal|6985831814265952068L
decl_stmt|;
specifier|private
name|Map
argument_list|<
name|Long
argument_list|,
name|TreeSet
argument_list|<
name|InstructionalOffering
argument_list|>
argument_list|>
name|instructionalOfferings
decl_stmt|;
specifier|private
name|Collection
name|subjectAreas
decl_stmt|;
specifier|private
name|String
index|[]
name|subjectAreaIds
decl_stmt|;
specifier|private
name|String
name|courseNbr
decl_stmt|;
specifier|private
name|Boolean
name|showNotOffered
decl_stmt|;
specifier|private
name|String
name|buttonAction
decl_stmt|;
specifier|private
name|String
name|subjectAreaAbbv
decl_stmt|;
specifier|private
name|Boolean
name|isControl
decl_stmt|;
specifier|private
name|String
name|ctrlInstrOfferingId
decl_stmt|;
specifier|private
name|Boolean
name|divSec
decl_stmt|;
specifier|private
name|Boolean
name|demand
decl_stmt|;
specifier|private
name|Boolean
name|projectedDemand
decl_stmt|;
specifier|private
name|Boolean
name|minPerWk
decl_stmt|;
specifier|private
name|Boolean
name|limit
decl_stmt|;
specifier|private
name|Boolean
name|snapshotLimit
decl_stmt|;
specifier|private
name|Boolean
name|roomLimit
decl_stmt|;
specifier|private
name|Boolean
name|manager
decl_stmt|;
specifier|private
name|Boolean
name|datePattern
decl_stmt|;
specifier|private
name|Boolean
name|timePattern
decl_stmt|;
specifier|private
name|Boolean
name|preferences
decl_stmt|;
specifier|private
name|Boolean
name|instructor
decl_stmt|;
specifier|private
name|Boolean
name|timetable
decl_stmt|;
specifier|private
name|Boolean
name|credit
decl_stmt|;
specifier|private
name|Boolean
name|subpartCredit
decl_stmt|;
specifier|private
name|Boolean
name|schedulePrintNote
decl_stmt|;
specifier|private
name|Boolean
name|note
decl_stmt|;
specifier|private
name|Boolean
name|consent
decl_stmt|;
specifier|private
name|Boolean
name|title
decl_stmt|;
specifier|private
name|Boolean
name|exams
decl_stmt|;
specifier|private
name|String
name|sortBy
decl_stmt|;
specifier|private
name|Boolean
name|instructorAssignment
decl_stmt|;
comment|/** 	 * @return Returns the ctrlInstrOfferingId. 	 */
specifier|public
name|String
name|getCtrlInstrOfferingId
parameter_list|()
block|{
return|return
name|ctrlInstrOfferingId
return|;
block|}
comment|/** 	 * @param ctrlInstrOfferingId 	 *            The ctrlInstrOfferingId to set. 	 */
specifier|public
name|void
name|setCtrlInstrOfferingId
parameter_list|(
name|String
name|ctrlInstrOfferingId
parameter_list|)
block|{
name|this
operator|.
name|ctrlInstrOfferingId
operator|=
name|ctrlInstrOfferingId
expr_stmt|;
block|}
comment|/** 	 * @return Returns the isControl. 	 */
specifier|public
name|Boolean
name|getIsControl
parameter_list|()
block|{
return|return
name|isControl
return|;
block|}
comment|/** 	 * @param isControl 	 *            The isControl to set. 	 */
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
comment|/** 	 * @return Returns the subjectAreaAbbv. 	 */
specifier|public
name|String
name|getSubjectAreaAbbv
parameter_list|()
block|{
return|return
name|subjectAreaAbbv
return|;
block|}
comment|/** 	 * @param subjectAreaAbbv 	 *            The subjectAreaAbbv to set. 	 */
specifier|public
name|void
name|setSubjectAreaAbbv
parameter_list|(
name|String
name|subjectAreaAbbv
parameter_list|)
block|{
name|this
operator|.
name|subjectAreaAbbv
operator|=
name|subjectAreaAbbv
expr_stmt|;
block|}
comment|/** 	 * @return Returns the buttonAction. 	 */
specifier|public
name|String
name|getButtonAction
parameter_list|()
block|{
return|return
name|buttonAction
return|;
block|}
comment|/** 	 * @param buttonAction 	 *            The buttonAction to set. 	 */
specifier|public
name|void
name|setButtonAction
parameter_list|(
name|String
name|buttonAction
parameter_list|)
block|{
name|this
operator|.
name|buttonAction
operator|=
name|buttonAction
expr_stmt|;
block|}
comment|/** 	 * @return Returns the courseNbr. 	 */
specifier|public
name|String
name|getCourseNbr
parameter_list|()
block|{
return|return
name|courseNbr
return|;
block|}
comment|/** 	 * @param courseNbr 	 *            The courseNbr to set. 	 */
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
comment|/** 	 * @return Returns the subjectAreaId. 	 */
specifier|public
name|String
index|[]
name|getSubjectAreaIds
parameter_list|()
block|{
return|return
name|subjectAreaIds
return|;
block|}
comment|/** 	 * @param subjectAreaId 	 *            The subjectAreaId to set. 	 */
specifier|public
name|void
name|setSubjectAreaIds
parameter_list|(
name|String
index|[]
name|subjectAreaIds
parameter_list|)
block|{
name|this
operator|.
name|subjectAreaIds
operator|=
name|subjectAreaIds
expr_stmt|;
block|}
comment|// --------------------------------------------------------- Methods
comment|/** 	 * Method reset 	 *  	 * @param mapping 	 * @param request 	 */
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
name|courseNbr
operator|=
literal|""
expr_stmt|;
name|instructionalOfferings
operator|=
literal|null
expr_stmt|;
name|subjectAreas
operator|=
operator|new
name|ArrayList
argument_list|()
expr_stmt|;
name|divSec
operator|=
operator|new
name|Boolean
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|demand
operator|=
operator|new
name|Boolean
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|projectedDemand
operator|=
operator|new
name|Boolean
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|minPerWk
operator|=
operator|new
name|Boolean
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|limit
operator|=
operator|new
name|Boolean
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|snapshotLimit
operator|=
operator|new
name|Boolean
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|roomLimit
operator|=
operator|new
name|Boolean
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|manager
operator|=
operator|new
name|Boolean
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|datePattern
operator|=
operator|new
name|Boolean
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|timePattern
operator|=
operator|new
name|Boolean
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|preferences
operator|=
operator|new
name|Boolean
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|instructor
operator|=
operator|new
name|Boolean
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|timetable
operator|=
operator|new
name|Boolean
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|credit
operator|=
operator|new
name|Boolean
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|subpartCredit
operator|=
operator|new
name|Boolean
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|schedulePrintNote
operator|=
operator|new
name|Boolean
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|note
operator|=
operator|new
name|Boolean
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|title
operator|=
operator|new
name|Boolean
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|consent
operator|=
operator|new
name|Boolean
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|exams
operator|=
operator|new
name|Boolean
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|instructorAssignment
operator|=
operator|new
name|Boolean
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|sortBy
operator|=
name|ClassCourseComparator
operator|.
name|getName
argument_list|(
name|ClassCourseComparator
operator|.
name|SortBy
operator|.
name|NAME
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * @return Returns the instructionalOfferings. 	 */
specifier|public
name|Map
argument_list|<
name|Long
argument_list|,
name|TreeSet
argument_list|<
name|InstructionalOffering
argument_list|>
argument_list|>
name|getInstructionalOfferings
parameter_list|()
block|{
return|return
name|instructionalOfferings
return|;
block|}
specifier|public
name|TreeSet
argument_list|<
name|InstructionalOffering
argument_list|>
name|getInstructionalOfferings
parameter_list|(
name|Long
name|subjectAreaId
parameter_list|)
block|{
return|return
name|instructionalOfferings
operator|==
literal|null
condition|?
literal|null
else|:
name|instructionalOfferings
operator|.
name|get
argument_list|(
name|subjectAreaId
argument_list|)
return|;
block|}
comment|/** 	 * @param instructionalOfferings 	 *            The instructionalOfferings to set. 	 */
specifier|public
name|void
name|setInstructionalOfferings
parameter_list|(
name|Map
argument_list|<
name|Long
argument_list|,
name|TreeSet
argument_list|<
name|InstructionalOffering
argument_list|>
argument_list|>
name|instructionalOfferings
parameter_list|)
block|{
name|this
operator|.
name|instructionalOfferings
operator|=
name|instructionalOfferings
expr_stmt|;
block|}
comment|/** 	 * @return Returns the subjectAreas. 	 */
specifier|public
name|Collection
name|getSubjectAreas
parameter_list|()
block|{
return|return
name|subjectAreas
return|;
block|}
comment|/** 	 * @param subjectAreas 	 *            The subjectAreas to set. 	 */
specifier|public
name|void
name|setSubjectAreas
parameter_list|(
name|Collection
name|subjectAreas
parameter_list|)
block|{
name|this
operator|.
name|subjectAreas
operator|=
name|subjectAreas
expr_stmt|;
block|}
comment|/** 	 * @return Returns the showNotOffered. 	 */
specifier|public
name|Boolean
name|getShowNotOffered
parameter_list|()
block|{
return|return
name|showNotOffered
return|;
block|}
comment|/** 	 * @param showNotOffered 	 *            The showNotOffered to set. 	 */
specifier|public
name|void
name|setShowNotOffered
parameter_list|(
name|Boolean
name|showNotOffered
parameter_list|)
block|{
name|this
operator|.
name|showNotOffered
operator|=
name|showNotOffered
expr_stmt|;
block|}
comment|/* 	 * (non-Javadoc) 	 *  	 * @see org.apache.struts.action.ActionForm#validate(org.apache.struts.action.ActionMapping, 	 *      javax.servlet.http.HttpServletRequest) 	 */
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
name|subjectAreaIds
operator|==
literal|null
operator|||
name|subjectAreaIds
operator|.
name|length
operator|==
literal|0
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"subjectAreaIds"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.required"
argument_list|,
name|MSG
operator|.
name|labelSubjectArea
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|errors
return|;
block|}
specifier|public
name|Boolean
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
name|Boolean
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
name|Boolean
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
name|Boolean
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
name|Boolean
name|getDivSec
parameter_list|()
block|{
return|return
name|divSec
return|;
block|}
specifier|public
name|void
name|setDivSec
parameter_list|(
name|Boolean
name|divSec
parameter_list|)
block|{
name|this
operator|.
name|divSec
operator|=
name|divSec
expr_stmt|;
block|}
specifier|public
name|Boolean
name|getInstructor
parameter_list|()
block|{
return|return
name|instructor
return|;
block|}
specifier|public
name|void
name|setInstructor
parameter_list|(
name|Boolean
name|instructor
parameter_list|)
block|{
name|this
operator|.
name|instructor
operator|=
name|instructor
expr_stmt|;
block|}
specifier|public
name|Boolean
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
name|Boolean
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
name|getSnapshotLimit
parameter_list|()
block|{
return|return
name|snapshotLimit
return|;
block|}
specifier|public
name|void
name|setSnapshotLimit
parameter_list|(
name|Boolean
name|snapshotLimit
parameter_list|)
block|{
name|this
operator|.
name|snapshotLimit
operator|=
name|snapshotLimit
expr_stmt|;
block|}
specifier|public
name|Boolean
name|getManager
parameter_list|()
block|{
return|return
name|manager
return|;
block|}
specifier|public
name|void
name|setManager
parameter_list|(
name|Boolean
name|manager
parameter_list|)
block|{
name|this
operator|.
name|manager
operator|=
name|manager
expr_stmt|;
block|}
specifier|public
name|Boolean
name|getMinPerWk
parameter_list|()
block|{
return|return
name|minPerWk
return|;
block|}
specifier|public
name|void
name|setMinPerWk
parameter_list|(
name|Boolean
name|minPerWk
parameter_list|)
block|{
name|this
operator|.
name|minPerWk
operator|=
name|minPerWk
expr_stmt|;
block|}
specifier|public
name|Boolean
name|getPreferences
parameter_list|()
block|{
return|return
name|preferences
return|;
block|}
specifier|public
name|void
name|setPreferences
parameter_list|(
name|Boolean
name|preferences
parameter_list|)
block|{
name|this
operator|.
name|preferences
operator|=
name|preferences
expr_stmt|;
block|}
specifier|public
name|Boolean
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
name|Boolean
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
name|Boolean
name|getRoomLimit
parameter_list|()
block|{
return|return
name|roomLimit
return|;
block|}
specifier|public
name|void
name|setRoomLimit
parameter_list|(
name|Boolean
name|roomLimit
parameter_list|)
block|{
name|this
operator|.
name|roomLimit
operator|=
name|roomLimit
expr_stmt|;
block|}
specifier|public
name|Boolean
name|getTimePattern
parameter_list|()
block|{
return|return
name|timePattern
return|;
block|}
specifier|public
name|void
name|setTimePattern
parameter_list|(
name|Boolean
name|timePattern
parameter_list|)
block|{
name|this
operator|.
name|timePattern
operator|=
name|timePattern
expr_stmt|;
block|}
specifier|public
name|Boolean
name|getTimetable
parameter_list|()
block|{
return|return
name|timetable
return|;
block|}
specifier|public
name|void
name|setTimetable
parameter_list|(
name|Boolean
name|timetable
parameter_list|)
block|{
name|this
operator|.
name|timetable
operator|=
name|timetable
expr_stmt|;
block|}
specifier|public
name|Boolean
name|getCredit
parameter_list|()
block|{
return|return
name|credit
return|;
block|}
specifier|public
name|void
name|setCredit
parameter_list|(
name|Boolean
name|credit
parameter_list|)
block|{
name|this
operator|.
name|credit
operator|=
name|credit
expr_stmt|;
block|}
specifier|public
name|Boolean
name|getSubpartCredit
parameter_list|()
block|{
return|return
name|subpartCredit
return|;
block|}
specifier|public
name|void
name|setSubpartCredit
parameter_list|(
name|Boolean
name|subpartCredit
parameter_list|)
block|{
name|this
operator|.
name|subpartCredit
operator|=
name|subpartCredit
expr_stmt|;
block|}
specifier|public
name|Boolean
name|getSchedulePrintNote
parameter_list|()
block|{
return|return
name|schedulePrintNote
return|;
block|}
specifier|public
name|void
name|setSchedulePrintNote
parameter_list|(
name|Boolean
name|schedulePrintNote
parameter_list|)
block|{
name|this
operator|.
name|schedulePrintNote
operator|=
name|schedulePrintNote
expr_stmt|;
block|}
specifier|public
name|Boolean
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
name|Boolean
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
name|Boolean
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
name|Boolean
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
name|Boolean
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
name|Boolean
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
name|Boolean
name|getExams
parameter_list|()
block|{
return|return
name|exams
return|;
block|}
specifier|public
name|void
name|setExams
parameter_list|(
name|Boolean
name|exams
parameter_list|)
block|{
name|this
operator|.
name|exams
operator|=
name|exams
expr_stmt|;
block|}
specifier|public
name|Boolean
name|getInstructorAssignment
parameter_list|()
block|{
return|return
name|instructorAssignment
return|;
block|}
specifier|public
name|void
name|setInstructorAssignment
parameter_list|(
name|Boolean
name|instructorAssignment
parameter_list|)
block|{
name|this
operator|.
name|instructorAssignment
operator|=
name|instructorAssignment
expr_stmt|;
block|}
specifier|protected
name|void
name|finalize
parameter_list|()
throws|throws
name|Throwable
block|{
name|Debug
operator|.
name|debug
argument_list|(
literal|"!!! Finalizing InstructionalOfferingListForm ... "
argument_list|)
expr_stmt|;
name|instructionalOfferings
operator|=
literal|null
expr_stmt|;
name|subjectAreas
operator|=
literal|null
expr_stmt|;
name|subjectAreaIds
operator|=
operator|new
name|String
index|[
literal|0
index|]
expr_stmt|;
name|courseNbr
operator|=
literal|null
expr_stmt|;
name|showNotOffered
operator|=
literal|null
expr_stmt|;
name|buttonAction
operator|=
literal|null
expr_stmt|;
name|subjectAreaAbbv
operator|=
literal|null
expr_stmt|;
name|isControl
operator|=
literal|null
expr_stmt|;
name|ctrlInstrOfferingId
operator|=
literal|null
expr_stmt|;
name|divSec
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
name|minPerWk
operator|=
literal|null
expr_stmt|;
name|limit
operator|=
literal|null
expr_stmt|;
name|snapshotLimit
operator|=
literal|null
expr_stmt|;
name|roomLimit
operator|=
literal|null
expr_stmt|;
name|manager
operator|=
literal|null
expr_stmt|;
name|datePattern
operator|=
literal|null
expr_stmt|;
name|timePattern
operator|=
literal|null
expr_stmt|;
name|preferences
operator|=
literal|null
expr_stmt|;
name|instructor
operator|=
literal|null
expr_stmt|;
name|timetable
operator|=
literal|null
expr_stmt|;
name|credit
operator|=
literal|null
expr_stmt|;
name|subpartCredit
operator|=
literal|null
expr_stmt|;
name|schedulePrintNote
operator|=
literal|null
expr_stmt|;
name|note
operator|=
literal|null
expr_stmt|;
name|title
operator|=
literal|null
expr_stmt|;
name|consent
operator|=
literal|null
expr_stmt|;
name|instructorAssignment
operator|=
literal|null
expr_stmt|;
name|super
operator|.
name|finalize
argument_list|()
expr_stmt|;
block|}
specifier|public
name|String
name|getSortBy
parameter_list|()
block|{
return|return
name|sortBy
return|;
block|}
specifier|public
name|void
name|setSortBy
parameter_list|(
name|String
name|sortBy
parameter_list|)
block|{
name|this
operator|.
name|sortBy
operator|=
name|sortBy
expr_stmt|;
block|}
specifier|public
name|String
index|[]
name|getSortByOptions
parameter_list|()
block|{
return|return
name|ClassCourseComparator
operator|.
name|getNames
argument_list|()
return|;
block|}
specifier|public
name|Boolean
name|getEnrollmentInformation
parameter_list|()
block|{
return|return
operator|(
operator|new
name|Boolean
argument_list|(
name|getDemand
argument_list|()
operator|.
name|booleanValue
argument_list|()
operator|&&
name|getProjectedDemand
argument_list|()
operator|.
name|booleanValue
argument_list|()
operator|&&
name|getLimit
argument_list|()
operator|.
name|booleanValue
argument_list|()
operator|&&
name|getRoomLimit
argument_list|()
operator|.
name|booleanValue
argument_list|()
argument_list|)
operator|)
return|;
block|}
specifier|public
name|void
name|setEnrollmentInformation
parameter_list|()
block|{
empty_stmt|;
comment|//do nothing
block|}
specifier|public
name|Boolean
name|getDateTimeInformation
parameter_list|()
block|{
return|return
operator|(
operator|new
name|Boolean
argument_list|(
name|getDatePattern
argument_list|()
operator|.
name|booleanValue
argument_list|()
operator|&&
name|getMinPerWk
argument_list|()
operator|.
name|booleanValue
argument_list|()
operator|&&
name|getTimePattern
argument_list|()
operator|.
name|booleanValue
argument_list|()
argument_list|)
operator|)
return|;
block|}
specifier|public
name|void
name|setDateTimeInformation
parameter_list|()
block|{
empty_stmt|;
comment|//do nothing
block|}
specifier|public
name|Boolean
name|getCatalogInformation
parameter_list|()
block|{
return|return
operator|(
operator|new
name|Boolean
argument_list|(
name|getTitle
argument_list|()
operator|.
name|booleanValue
argument_list|()
operator|&&
name|getCredit
argument_list|()
operator|.
name|booleanValue
argument_list|()
operator|&&
name|getSubpartCredit
argument_list|()
operator|.
name|booleanValue
argument_list|()
operator|&&
name|getConsent
argument_list|()
operator|.
name|booleanValue
argument_list|()
operator|&&
name|getSchedulePrintNote
argument_list|()
operator|.
name|booleanValue
argument_list|()
argument_list|)
operator|)
return|;
block|}
specifier|public
name|void
name|setCatalogInformation
parameter_list|()
block|{
empty_stmt|;
comment|//do nothing
block|}
block|}
end_class

end_unit

