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
name|comparators
operator|.
name|ClassCourseComparator
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
name|LookupTables
import|;
end_import

begin_comment
comment|/**  * @author Stephanie Schluttenhofer, Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|ClassAssignmentsReportForm
extends|extends
name|ActionForm
implements|implements
name|ClassListFormInterface
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
literal|3257854294022959921L
decl_stmt|;
specifier|private
name|Collection
name|classes
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
name|buttonAction
decl_stmt|;
specifier|private
name|String
name|subjectAreaAbbv
decl_stmt|;
specifier|private
name|String
name|ctrlInstrOfferingId
decl_stmt|;
specifier|private
name|String
name|sortBy
decl_stmt|;
specifier|private
name|String
name|filterAssignedRoom
decl_stmt|;
specifier|private
name|String
name|filterManager
decl_stmt|;
specifier|private
name|String
name|filterIType
decl_stmt|;
specifier|private
name|boolean
name|filterAssignedTimeMon
decl_stmt|;
specifier|private
name|boolean
name|filterAssignedTimeTue
decl_stmt|;
specifier|private
name|boolean
name|filterAssignedTimeWed
decl_stmt|;
specifier|private
name|boolean
name|filterAssignedTimeThu
decl_stmt|;
specifier|private
name|boolean
name|filterAssignedTimeFri
decl_stmt|;
specifier|private
name|boolean
name|filterAssignedTimeSat
decl_stmt|;
specifier|private
name|boolean
name|filterAssignedTimeSun
decl_stmt|;
specifier|private
name|String
name|filterAssignedTimeHour
decl_stmt|;
specifier|private
name|String
name|filterAssignedTimeMin
decl_stmt|;
specifier|private
name|String
name|filterAssignedTimeAmPm
decl_stmt|;
specifier|private
name|String
name|filterAssignedTimeLength
decl_stmt|;
specifier|private
name|boolean
name|sortByKeepSubparts
decl_stmt|;
specifier|private
name|boolean
name|showCrossListedClasses
decl_stmt|;
specifier|private
name|boolean
name|returnAllControlClassesForSubjects
decl_stmt|;
specifier|private
name|String
index|[]
name|userDeptIds
decl_stmt|;
comment|/**      * @return Returns the ctrlInstrOfferingId.      */
specifier|public
name|String
name|getCtrlInstrOfferingId
parameter_list|()
block|{
return|return
name|ctrlInstrOfferingId
return|;
block|}
comment|/**      * @param ctrlInstrOfferingId The ctrlInstrOfferingId to set.      */
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
comment|/**      * @return Returns the subjectAreaAbbv.      */
specifier|public
name|String
name|getSubjectAreaAbbv
parameter_list|()
block|{
return|return
name|subjectAreaAbbv
return|;
block|}
comment|/**      * @param subjectAreaAbbv The subjectAreaAbbv to set.      */
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
comment|/**      * @return Returns the buttonAction.      */
specifier|public
name|String
name|getButtonAction
parameter_list|()
block|{
return|return
name|buttonAction
return|;
block|}
comment|/**      * @param buttonAction The buttonAction to set.      */
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
comment|/** 	 * @return Returns the subjectAreaIds. 	 */
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
comment|/** 	 * @param subjectAreaIds The subjectAreaIds to set. 	 */
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
name|init
argument_list|()
expr_stmt|;
name|LookupTables
operator|.
name|setupItypes
argument_list|(
name|request
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|init
parameter_list|()
block|{
name|classes
operator|=
operator|new
name|ArrayList
argument_list|()
expr_stmt|;
name|subjectAreas
operator|=
operator|new
name|ArrayList
argument_list|()
expr_stmt|;
name|subjectAreaIds
operator|=
operator|new
name|String
index|[
literal|0
index|]
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
name|filterManager
operator|=
literal|""
expr_stmt|;
name|filterAssignedRoom
operator|=
literal|""
expr_stmt|;
name|filterIType
operator|=
literal|""
expr_stmt|;
name|sortByKeepSubparts
operator|=
literal|false
expr_stmt|;
name|filterAssignedTimeMon
operator|=
literal|false
expr_stmt|;
name|filterAssignedTimeTue
operator|=
literal|false
expr_stmt|;
name|filterAssignedTimeWed
operator|=
literal|false
expr_stmt|;
name|filterAssignedTimeThu
operator|=
literal|false
expr_stmt|;
name|filterAssignedTimeFri
operator|=
literal|false
expr_stmt|;
name|filterAssignedTimeSat
operator|=
literal|false
expr_stmt|;
name|filterAssignedTimeSun
operator|=
literal|false
expr_stmt|;
name|filterAssignedTimeHour
operator|=
literal|""
expr_stmt|;
name|filterAssignedTimeMin
operator|=
literal|""
expr_stmt|;
name|filterAssignedTimeAmPm
operator|=
literal|""
expr_stmt|;
name|filterAssignedTimeLength
operator|=
literal|""
expr_stmt|;
name|returnAllControlClassesForSubjects
operator|=
literal|true
expr_stmt|;
name|userDeptIds
operator|=
operator|new
name|String
index|[
literal|0
index|]
expr_stmt|;
name|showCrossListedClasses
operator|=
literal|false
expr_stmt|;
block|}
comment|/**      * @return Returns the classes.      */
specifier|public
name|Collection
name|getClasses
parameter_list|()
block|{
return|return
name|classes
return|;
block|}
comment|/**      * @param classes The classes to set.      */
specifier|public
name|void
name|setClasses
parameter_list|(
name|Collection
name|classes
parameter_list|)
block|{
name|this
operator|.
name|classes
operator|=
name|classes
expr_stmt|;
block|}
comment|/**      * @return Returns the subjectAreas.      */
specifier|public
name|Collection
name|getSubjectAreas
parameter_list|()
block|{
return|return
name|subjectAreas
return|;
block|}
comment|/**      * @param subjectAreas The subjectAreas to set.      */
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
comment|/* (non-Javadoc)      * @see org.apache.struts.action.ActionForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)      */
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
return|return
name|errors
return|;
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
name|String
name|getFilterManager
parameter_list|()
block|{
return|return
name|filterManager
return|;
block|}
specifier|public
name|void
name|setFilterManager
parameter_list|(
name|String
name|filterManager
parameter_list|)
block|{
name|this
operator|.
name|filterManager
operator|=
name|filterManager
expr_stmt|;
block|}
specifier|public
name|String
name|getFilterAssignedRoom
parameter_list|()
block|{
return|return
name|filterAssignedRoom
return|;
block|}
specifier|public
name|void
name|setFilterAssignedRoom
parameter_list|(
name|String
name|filterAssignedRoom
parameter_list|)
block|{
name|this
operator|.
name|filterAssignedRoom
operator|=
name|filterAssignedRoom
expr_stmt|;
block|}
comment|/* 	public String getFilterInstructor() { return filterInstructor; } 	public void setFilterInstructor(String filterInstructor) { this.filterInstructor = filterInstructor; } 	*/
comment|//NO instructor is shown on class assignment page -> display no filter
specifier|public
name|String
name|getFilterInstructor
parameter_list|()
block|{
return|return
literal|""
return|;
block|}
specifier|public
name|String
name|getFilterIType
parameter_list|()
block|{
return|return
name|filterIType
return|;
block|}
specifier|public
name|void
name|setFilterIType
parameter_list|(
name|String
name|filterIType
parameter_list|)
block|{
name|this
operator|.
name|filterIType
operator|=
name|filterIType
expr_stmt|;
block|}
specifier|public
name|boolean
name|getFilterAssignedTimeMon
parameter_list|()
block|{
return|return
name|filterAssignedTimeMon
return|;
block|}
specifier|public
name|void
name|setFilterAssignedTimeMon
parameter_list|(
name|boolean
name|filterAssignedTimeMon
parameter_list|)
block|{
name|this
operator|.
name|filterAssignedTimeMon
operator|=
name|filterAssignedTimeMon
expr_stmt|;
block|}
specifier|public
name|boolean
name|getFilterAssignedTimeTue
parameter_list|()
block|{
return|return
name|filterAssignedTimeTue
return|;
block|}
specifier|public
name|void
name|setFilterAssignedTimeTue
parameter_list|(
name|boolean
name|filterAssignedTimeTue
parameter_list|)
block|{
name|this
operator|.
name|filterAssignedTimeTue
operator|=
name|filterAssignedTimeTue
expr_stmt|;
block|}
specifier|public
name|boolean
name|getFilterAssignedTimeWed
parameter_list|()
block|{
return|return
name|filterAssignedTimeWed
return|;
block|}
specifier|public
name|void
name|setFilterAssignedTimeWed
parameter_list|(
name|boolean
name|filterAssignedTimeWed
parameter_list|)
block|{
name|this
operator|.
name|filterAssignedTimeWed
operator|=
name|filterAssignedTimeWed
expr_stmt|;
block|}
specifier|public
name|boolean
name|getFilterAssignedTimeThu
parameter_list|()
block|{
return|return
name|filterAssignedTimeThu
return|;
block|}
specifier|public
name|void
name|setFilterAssignedTimeThu
parameter_list|(
name|boolean
name|filterAssignedTimeThu
parameter_list|)
block|{
name|this
operator|.
name|filterAssignedTimeThu
operator|=
name|filterAssignedTimeThu
expr_stmt|;
block|}
specifier|public
name|boolean
name|getFilterAssignedTimeFri
parameter_list|()
block|{
return|return
name|filterAssignedTimeFri
return|;
block|}
specifier|public
name|void
name|setFilterAssignedTimeFri
parameter_list|(
name|boolean
name|filterAssignedTimeFri
parameter_list|)
block|{
name|this
operator|.
name|filterAssignedTimeFri
operator|=
name|filterAssignedTimeFri
expr_stmt|;
block|}
specifier|public
name|boolean
name|getFilterAssignedTimeSat
parameter_list|()
block|{
return|return
name|filterAssignedTimeSat
return|;
block|}
specifier|public
name|void
name|setFilterAssignedTimeSat
parameter_list|(
name|boolean
name|filterAssignedTimeSat
parameter_list|)
block|{
name|this
operator|.
name|filterAssignedTimeSat
operator|=
name|filterAssignedTimeSat
expr_stmt|;
block|}
specifier|public
name|boolean
name|getFilterAssignedTimeSun
parameter_list|()
block|{
return|return
name|filterAssignedTimeSun
return|;
block|}
specifier|public
name|void
name|setFilterAssignedTimeSun
parameter_list|(
name|boolean
name|filterAssignedTimeSun
parameter_list|)
block|{
name|this
operator|.
name|filterAssignedTimeSun
operator|=
name|filterAssignedTimeSun
expr_stmt|;
block|}
specifier|public
name|String
name|getFilterAssignedTimeHour
parameter_list|()
block|{
return|return
name|filterAssignedTimeHour
return|;
block|}
specifier|public
name|void
name|setFilterAssignedTimeHour
parameter_list|(
name|String
name|filterAssignedTimeHour
parameter_list|)
block|{
name|this
operator|.
name|filterAssignedTimeHour
operator|=
name|filterAssignedTimeHour
expr_stmt|;
block|}
specifier|public
name|String
name|getFilterAssignedTimeMin
parameter_list|()
block|{
return|return
name|filterAssignedTimeMin
return|;
block|}
specifier|public
name|void
name|setFilterAssignedTimeMin
parameter_list|(
name|String
name|filterAssignedTimeMin
parameter_list|)
block|{
name|this
operator|.
name|filterAssignedTimeMin
operator|=
name|filterAssignedTimeMin
expr_stmt|;
block|}
specifier|public
name|String
name|getFilterAssignedTimeAmPm
parameter_list|()
block|{
return|return
name|filterAssignedTimeAmPm
return|;
block|}
specifier|public
name|void
name|setFilterAssignedTimeAmPm
parameter_list|(
name|String
name|filterAssignedTimeAmPm
parameter_list|)
block|{
name|this
operator|.
name|filterAssignedTimeAmPm
operator|=
name|filterAssignedTimeAmPm
expr_stmt|;
block|}
specifier|public
name|String
name|getFilterAssignedTimeLength
parameter_list|()
block|{
return|return
operator|(
literal|"0"
operator|.
name|equals
argument_list|(
name|filterAssignedTimeLength
argument_list|)
condition|?
literal|""
else|:
name|filterAssignedTimeLength
operator|)
return|;
block|}
specifier|public
name|void
name|setFilterAssignedTimeLength
parameter_list|(
name|String
name|filterAssignedTimeLength
parameter_list|)
block|{
name|this
operator|.
name|filterAssignedTimeLength
operator|=
name|filterAssignedTimeLength
expr_stmt|;
block|}
specifier|public
name|String
index|[]
name|getFilterAssignedTimeHours
parameter_list|()
block|{
return|return
operator|new
name|String
index|[]
block|{
literal|""
block|,
literal|"1"
block|,
literal|"2"
block|,
literal|"3"
block|,
literal|"4"
block|,
literal|"5"
block|,
literal|"6"
block|,
literal|"7"
block|,
literal|"8"
block|,
literal|"9"
block|,
literal|"10"
block|,
literal|"11"
block|,
literal|"12"
block|}
return|;
block|}
specifier|public
name|String
index|[]
name|getFilterAssignedTimeMins
parameter_list|()
block|{
return|return
operator|new
name|String
index|[]
block|{
literal|""
block|,
literal|"00"
block|,
literal|"05"
block|,
literal|"10"
block|,
literal|"15"
block|,
literal|"20"
block|,
literal|"25"
block|,
literal|"30"
block|,
literal|"35"
block|,
literal|"40"
block|,
literal|"45"
block|,
literal|"50"
block|,
literal|"55"
block|}
return|;
block|}
specifier|public
name|String
index|[]
name|getFilterAssignedTimeAmPms
parameter_list|()
block|{
return|return
operator|new
name|String
index|[]
block|{
literal|""
block|,
name|MSG
operator|.
name|timeAm
argument_list|()
block|,
name|MSG
operator|.
name|timePm
argument_list|()
block|}
return|;
block|}
specifier|public
name|String
index|[]
name|getFilterAssignedTimeLengths
parameter_list|()
block|{
name|String
index|[]
name|ret
init|=
operator|new
name|String
index|[
literal|41
index|]
decl_stmt|;
name|ret
index|[
literal|0
index|]
operator|=
literal|""
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|1
init|;
name|i
operator|<
name|ret
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
name|String
operator|.
name|valueOf
argument_list|(
literal|5
operator|*
name|i
argument_list|)
expr_stmt|;
return|return
name|ret
return|;
block|}
specifier|public
name|int
name|getFilterDayCode
parameter_list|()
block|{
name|int
name|dayCode
init|=
literal|0
decl_stmt|;
if|if
condition|(
name|filterAssignedTimeMon
condition|)
name|dayCode
operator|+=
name|Constants
operator|.
name|DAY_CODES
index|[
name|Constants
operator|.
name|DAY_MON
index|]
expr_stmt|;
if|if
condition|(
name|filterAssignedTimeTue
condition|)
name|dayCode
operator|+=
name|Constants
operator|.
name|DAY_CODES
index|[
name|Constants
operator|.
name|DAY_TUE
index|]
expr_stmt|;
if|if
condition|(
name|filterAssignedTimeWed
condition|)
name|dayCode
operator|+=
name|Constants
operator|.
name|DAY_CODES
index|[
name|Constants
operator|.
name|DAY_WED
index|]
expr_stmt|;
if|if
condition|(
name|filterAssignedTimeThu
condition|)
name|dayCode
operator|+=
name|Constants
operator|.
name|DAY_CODES
index|[
name|Constants
operator|.
name|DAY_THU
index|]
expr_stmt|;
if|if
condition|(
name|filterAssignedTimeFri
condition|)
name|dayCode
operator|+=
name|Constants
operator|.
name|DAY_CODES
index|[
name|Constants
operator|.
name|DAY_FRI
index|]
expr_stmt|;
if|if
condition|(
name|filterAssignedTimeSat
condition|)
name|dayCode
operator|+=
name|Constants
operator|.
name|DAY_CODES
index|[
name|Constants
operator|.
name|DAY_SAT
index|]
expr_stmt|;
if|if
condition|(
name|filterAssignedTimeSun
condition|)
name|dayCode
operator|+=
name|Constants
operator|.
name|DAY_CODES
index|[
name|Constants
operator|.
name|DAY_SUN
index|]
expr_stmt|;
return|return
name|dayCode
return|;
block|}
specifier|public
name|void
name|setFilterDayCode
parameter_list|(
name|int
name|dayCode
parameter_list|)
block|{
if|if
condition|(
name|dayCode
operator|>=
literal|0
condition|)
block|{
name|filterAssignedTimeMon
operator|=
operator|(
name|dayCode
operator|&
name|Constants
operator|.
name|DAY_CODES
index|[
name|Constants
operator|.
name|DAY_MON
index|]
operator|)
operator|!=
literal|0
expr_stmt|;
name|filterAssignedTimeTue
operator|=
operator|(
name|dayCode
operator|&
name|Constants
operator|.
name|DAY_CODES
index|[
name|Constants
operator|.
name|DAY_TUE
index|]
operator|)
operator|!=
literal|0
expr_stmt|;
name|filterAssignedTimeWed
operator|=
operator|(
name|dayCode
operator|&
name|Constants
operator|.
name|DAY_CODES
index|[
name|Constants
operator|.
name|DAY_WED
index|]
operator|)
operator|!=
literal|0
expr_stmt|;
name|filterAssignedTimeThu
operator|=
operator|(
name|dayCode
operator|&
name|Constants
operator|.
name|DAY_CODES
index|[
name|Constants
operator|.
name|DAY_THU
index|]
operator|)
operator|!=
literal|0
expr_stmt|;
name|filterAssignedTimeFri
operator|=
operator|(
name|dayCode
operator|&
name|Constants
operator|.
name|DAY_CODES
index|[
name|Constants
operator|.
name|DAY_FRI
index|]
operator|)
operator|!=
literal|0
expr_stmt|;
name|filterAssignedTimeSat
operator|=
operator|(
name|dayCode
operator|&
name|Constants
operator|.
name|DAY_CODES
index|[
name|Constants
operator|.
name|DAY_SAT
index|]
operator|)
operator|!=
literal|0
expr_stmt|;
name|filterAssignedTimeSun
operator|=
operator|(
name|dayCode
operator|&
name|Constants
operator|.
name|DAY_CODES
index|[
name|Constants
operator|.
name|DAY_SUN
index|]
operator|)
operator|!=
literal|0
expr_stmt|;
block|}
else|else
block|{
name|filterAssignedTimeMon
operator|=
literal|false
expr_stmt|;
name|filterAssignedTimeTue
operator|=
literal|false
expr_stmt|;
name|filterAssignedTimeWed
operator|=
literal|false
expr_stmt|;
name|filterAssignedTimeThu
operator|=
literal|false
expr_stmt|;
name|filterAssignedTimeFri
operator|=
literal|false
expr_stmt|;
name|filterAssignedTimeSat
operator|=
literal|false
expr_stmt|;
name|filterAssignedTimeSun
operator|=
literal|false
expr_stmt|;
block|}
block|}
specifier|public
name|int
name|getFilterStartSlot
parameter_list|()
block|{
try|try
block|{
name|int
name|hour
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|filterAssignedTimeHour
argument_list|)
decl_stmt|;
name|int
name|min
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|filterAssignedTimeMin
argument_list|)
decl_stmt|;
name|boolean
name|morn
init|=
operator|!
operator|(
name|MSG
operator|.
name|timePm
argument_list|()
operator|.
name|equals
argument_list|(
name|filterAssignedTimeAmPm
argument_list|)
operator|)
decl_stmt|;
if|if
condition|(
name|hour
operator|==
literal|12
condition|)
name|hour
operator|=
literal|0
expr_stmt|;
name|int
name|startTime
init|=
operator|(
operator|(
name|hour
operator|+
operator|(
name|morn
condition|?
literal|0
else|:
literal|12
operator|)
operator|)
operator|%
literal|24
operator|)
operator|*
literal|60
operator|+
name|min
decl_stmt|;
return|return
operator|(
name|startTime
operator|-
name|Constants
operator|.
name|FIRST_SLOT_TIME_MIN
operator|)
operator|/
name|Constants
operator|.
name|SLOT_LENGTH_MIN
return|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|e
parameter_list|)
block|{
block|}
catch|catch
parameter_list|(
name|NullPointerException
name|e
parameter_list|)
block|{
block|}
return|return
operator|-
literal|1
return|;
block|}
specifier|public
name|void
name|setFilterStartSlot
parameter_list|(
name|int
name|startSlot
parameter_list|)
block|{
if|if
condition|(
name|startSlot
operator|>=
literal|0
condition|)
block|{
name|int
name|startMin
init|=
name|startSlot
operator|*
name|Constants
operator|.
name|SLOT_LENGTH_MIN
operator|+
name|Constants
operator|.
name|FIRST_SLOT_TIME_MIN
decl_stmt|;
name|int
name|min
init|=
name|startMin
operator|%
literal|60
decl_stmt|;
name|int
name|startHour
init|=
name|startMin
operator|/
literal|60
decl_stmt|;
name|boolean
name|morn
init|=
operator|(
name|startHour
operator|<
literal|12
operator|)
decl_stmt|;
name|int
name|hour
init|=
name|startHour
operator|%
literal|12
decl_stmt|;
if|if
condition|(
name|hour
operator|==
literal|0
condition|)
name|hour
operator|=
literal|12
expr_stmt|;
name|filterAssignedTimeHour
operator|=
name|String
operator|.
name|valueOf
argument_list|(
name|hour
argument_list|)
expr_stmt|;
name|filterAssignedTimeMin
operator|=
operator|(
name|min
operator|<
literal|10
condition|?
literal|"0"
else|:
literal|""
operator|)
operator|+
name|min
expr_stmt|;
name|filterAssignedTimeAmPm
operator|=
operator|(
name|morn
condition|?
name|MSG
operator|.
name|timeAm
argument_list|()
else|:
name|MSG
operator|.
name|timePm
argument_list|()
operator|)
expr_stmt|;
block|}
else|else
block|{
name|filterAssignedTimeHour
operator|=
literal|""
expr_stmt|;
name|filterAssignedTimeMin
operator|=
literal|""
expr_stmt|;
name|filterAssignedTimeAmPm
operator|=
literal|""
expr_stmt|;
block|}
block|}
specifier|public
name|int
name|getFilterLength
parameter_list|()
block|{
try|try
block|{
return|return
name|Integer
operator|.
name|parseInt
argument_list|(
name|filterAssignedTimeLength
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|e
parameter_list|)
block|{
block|}
catch|catch
parameter_list|(
name|NullPointerException
name|e
parameter_list|)
block|{
block|}
return|return
literal|0
return|;
block|}
specifier|public
name|void
name|setFilterLength
parameter_list|(
name|int
name|length
parameter_list|)
block|{
if|if
condition|(
name|length
operator|>=
literal|0
condition|)
block|{
name|filterAssignedTimeLength
operator|=
name|String
operator|.
name|valueOf
argument_list|(
name|length
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|filterAssignedTimeLength
operator|=
literal|""
expr_stmt|;
block|}
block|}
specifier|public
name|String
name|getCourseNbr
parameter_list|()
block|{
return|return
literal|null
return|;
block|}
specifier|public
name|boolean
name|getSortByKeepSubparts
parameter_list|()
block|{
return|return
name|sortByKeepSubparts
return|;
block|}
specifier|public
name|void
name|setSortByKeepSubparts
parameter_list|(
name|boolean
name|sortByKeepSubparts
parameter_list|)
block|{
name|this
operator|.
name|sortByKeepSubparts
operator|=
name|sortByKeepSubparts
expr_stmt|;
block|}
specifier|public
name|boolean
name|isReturnAllControlClassesForSubjects
parameter_list|()
block|{
return|return
name|returnAllControlClassesForSubjects
return|;
block|}
specifier|public
name|void
name|setReturnAllControlClassesForSubjects
parameter_list|(
name|boolean
name|returnAllControlClassesForSubjects
parameter_list|)
block|{
name|this
operator|.
name|returnAllControlClassesForSubjects
operator|=
name|returnAllControlClassesForSubjects
expr_stmt|;
block|}
specifier|public
name|String
index|[]
name|getUserDeptIds
parameter_list|()
block|{
return|return
name|userDeptIds
return|;
block|}
specifier|public
name|void
name|setUserDeptIds
parameter_list|(
name|String
index|[]
name|userDeptIds
parameter_list|)
block|{
name|this
operator|.
name|userDeptIds
operator|=
name|userDeptIds
expr_stmt|;
block|}
specifier|public
name|Boolean
name|getDivSec
parameter_list|()
block|{
return|return
operator|(
operator|new
name|Boolean
argument_list|(
literal|true
argument_list|)
operator|)
return|;
block|}
specifier|public
name|Boolean
name|getDemand
parameter_list|()
block|{
return|return
operator|(
operator|new
name|Boolean
argument_list|(
literal|false
argument_list|)
operator|)
return|;
block|}
specifier|public
name|Boolean
name|getProjectedDemand
parameter_list|()
block|{
return|return
operator|(
operator|new
name|Boolean
argument_list|(
literal|false
argument_list|)
operator|)
return|;
block|}
specifier|public
name|Boolean
name|getMinPerWk
parameter_list|()
block|{
return|return
operator|(
operator|new
name|Boolean
argument_list|(
literal|false
argument_list|)
operator|)
return|;
block|}
specifier|public
name|Boolean
name|getLimit
parameter_list|()
block|{
return|return
operator|(
operator|new
name|Boolean
argument_list|(
literal|true
argument_list|)
operator|)
return|;
block|}
specifier|public
name|Boolean
name|getSnapshotLimit
parameter_list|()
block|{
return|return
operator|(
operator|new
name|Boolean
argument_list|(
literal|false
argument_list|)
operator|)
return|;
block|}
specifier|public
name|Boolean
name|getRoomLimit
parameter_list|()
block|{
return|return
operator|(
operator|new
name|Boolean
argument_list|(
literal|false
argument_list|)
operator|)
return|;
block|}
specifier|public
name|Boolean
name|getManager
parameter_list|()
block|{
return|return
operator|(
operator|new
name|Boolean
argument_list|(
literal|false
argument_list|)
operator|)
return|;
block|}
specifier|public
name|Boolean
name|getDatePattern
parameter_list|()
block|{
return|return
operator|(
operator|new
name|Boolean
argument_list|(
literal|true
argument_list|)
operator|)
return|;
block|}
specifier|public
name|Boolean
name|getTimePattern
parameter_list|()
block|{
return|return
operator|(
operator|new
name|Boolean
argument_list|(
literal|false
argument_list|)
operator|)
return|;
block|}
specifier|public
name|Boolean
name|getPreferences
parameter_list|()
block|{
return|return
operator|(
operator|new
name|Boolean
argument_list|(
literal|false
argument_list|)
operator|)
return|;
block|}
specifier|public
name|Boolean
name|getInstructor
parameter_list|()
block|{
return|return
operator|(
operator|new
name|Boolean
argument_list|(
literal|false
argument_list|)
operator|)
return|;
block|}
specifier|public
name|Boolean
name|getTimetable
parameter_list|()
block|{
return|return
operator|(
operator|new
name|Boolean
argument_list|(
literal|true
argument_list|)
operator|)
return|;
block|}
specifier|public
name|Boolean
name|getCredit
parameter_list|()
block|{
return|return
operator|(
operator|new
name|Boolean
argument_list|(
literal|false
argument_list|)
operator|)
return|;
block|}
specifier|public
name|Boolean
name|getSubpartCredit
parameter_list|()
block|{
return|return
operator|(
operator|new
name|Boolean
argument_list|(
literal|false
argument_list|)
operator|)
return|;
block|}
specifier|public
name|Boolean
name|getSchedulePrintNote
parameter_list|()
block|{
return|return
operator|(
operator|new
name|Boolean
argument_list|(
literal|true
argument_list|)
operator|)
return|;
block|}
specifier|public
name|Boolean
name|getNote
parameter_list|()
block|{
return|return
operator|(
operator|new
name|Boolean
argument_list|(
literal|false
argument_list|)
operator|)
return|;
block|}
specifier|public
name|Boolean
name|getConsent
parameter_list|()
block|{
return|return
operator|(
operator|new
name|Boolean
argument_list|(
literal|false
argument_list|)
operator|)
return|;
block|}
specifier|public
name|Boolean
name|getTitle
parameter_list|()
block|{
return|return
operator|(
operator|new
name|Boolean
argument_list|(
literal|false
argument_list|)
operator|)
return|;
block|}
specifier|public
name|Boolean
name|getExams
parameter_list|()
block|{
return|return
name|Boolean
operator|.
name|FALSE
return|;
block|}
specifier|public
name|Boolean
name|getCanSeeExams
parameter_list|()
block|{
return|return
name|Boolean
operator|.
name|FALSE
return|;
block|}
specifier|public
name|boolean
name|getShowCrossListedClasses
parameter_list|()
block|{
return|return
name|showCrossListedClasses
return|;
block|}
specifier|public
name|void
name|setShowCrossListedClasses
parameter_list|(
name|boolean
name|showCrossListedClasses
parameter_list|)
block|{
name|this
operator|.
name|showCrossListedClasses
operator|=
name|showCrossListedClasses
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|getIncludeCancelledClasses
parameter_list|()
block|{
return|return
literal|false
return|;
block|}
annotation|@
name|Override
specifier|public
name|Boolean
name|getInstructorAssignment
parameter_list|()
block|{
return|return
literal|false
return|;
block|}
annotation|@
name|Override
specifier|public
name|Boolean
name|getLms
parameter_list|()
block|{
return|return
literal|false
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|getFilterNeedInstructor
parameter_list|()
block|{
return|return
literal|false
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getWaitlist
parameter_list|()
block|{
return|return
literal|null
return|;
block|}
block|}
end_class

end_unit

