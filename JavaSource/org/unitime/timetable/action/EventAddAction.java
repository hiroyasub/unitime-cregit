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
name|action
package|;
end_package

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
name|Iterator
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
name|HttpServletResponse
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
name|Action
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
name|ActionForward
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
name|ActionMessages
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
name|User
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
name|web
operator|.
name|Web
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
name|web
operator|.
name|WebTable
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
name|form
operator|.
name|EventAddForm
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
name|Class_
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
name|CourseEvent
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
name|Event
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
name|ExamOwner
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
name|InstrOfferingConfig
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
name|RelatedCourseInfo
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
name|RoomType
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
name|TimetableManager
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
name|CourseEventDAO
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
name|EventDAO
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
comment|/**  * @author Zuzana Mullerova  */
end_comment

begin_class
specifier|public
class|class
name|EventAddAction
extends|extends
name|Action
block|{
comment|/**  	 * Method execute 	 * @param mapping 	 * @param form 	 * @param request 	 * @param response 	 * @return ActionForward 	 */
specifier|public
name|ActionForward
name|execute
parameter_list|(
name|ActionMapping
name|mapping
parameter_list|,
name|ActionForm
name|form
parameter_list|,
name|HttpServletRequest
name|request
parameter_list|,
name|HttpServletResponse
name|response
parameter_list|)
throws|throws
name|Exception
block|{
comment|//Collect initial info
name|EventAddForm
name|myForm
init|=
operator|(
name|EventAddForm
operator|)
name|form
decl_stmt|;
name|User
name|user
init|=
name|Web
operator|.
name|getUser
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|)
decl_stmt|;
comment|//Verification of user being logged in
if|if
condition|(
operator|!
name|Web
operator|.
name|isLoggedIn
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Access Denied."
argument_list|)
throw|;
block|}
comment|//Operations
name|String
name|iOp
init|=
name|myForm
operator|.
name|getOp
argument_list|()
decl_stmt|;
if|if
condition|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"op2"
argument_list|)
operator|!=
literal|null
operator|&&
name|request
operator|.
name|getParameter
argument_list|(
literal|"op2"
argument_list|)
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|iOp
operator|=
name|request
operator|.
name|getParameter
argument_list|(
literal|"op2"
argument_list|)
expr_stmt|;
comment|// if user is returning from the Event Room Availability screen,
comment|// load the parameters he/she entered before
if|if
condition|(
literal|"eventRoomAvailability"
operator|.
name|equals
argument_list|(
name|request
operator|.
name|getAttribute
argument_list|(
literal|"back"
argument_list|)
argument_list|)
condition|)
block|{
name|myForm
operator|.
name|load
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|)
expr_stmt|;
name|iOp
operator|=
literal|null
expr_stmt|;
block|}
if|if
condition|(
name|iOp
operator|!=
literal|null
operator|&&
operator|!
operator|(
literal|"SessionChanged"
operator|.
name|equals
argument_list|(
name|iOp
argument_list|)
operator|||
literal|"Add Object"
operator|.
name|equals
argument_list|(
name|iOp
argument_list|)
operator|||
literal|"Delete"
operator|.
name|equals
argument_list|(
name|iOp
argument_list|)
operator|||
literal|"Show Scheduled Events"
operator|.
name|equals
argument_list|(
name|iOp
argument_list|)
operator|||
literal|"Show Availability"
operator|.
name|equals
argument_list|(
name|iOp
argument_list|)
operator|||
literal|"Back"
operator|.
name|equals
argument_list|(
name|iOp
argument_list|)
operator|)
condition|)
block|{
name|iOp
operator|=
literal|null
expr_stmt|;
block|}
if|if
condition|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"id"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|myForm
operator|.
name|setEventId
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"id"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|myForm
operator|.
name|getEventId
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|Event
name|event
init|=
name|EventDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|myForm
operator|.
name|getEventId
argument_list|()
argument_list|)
decl_stmt|;
name|myForm
operator|.
name|setEvent
argument_list|(
name|event
argument_list|)
expr_stmt|;
if|if
condition|(
name|event
operator|.
name|getMinCapacity
argument_list|()
operator|!=
literal|null
condition|)
name|myForm
operator|.
name|setCapacity
argument_list|(
name|String
operator|.
name|valueOf
argument_list|(
name|event
operator|.
name|getMinCapacity
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|myForm
operator|.
name|setEventType
argument_list|(
name|myForm
operator|.
name|getEvent
argument_list|()
operator|.
name|getEventTypeLabel
argument_list|()
argument_list|)
expr_stmt|;
name|myForm
operator|.
name|setIsAddMeetings
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|myForm
operator|.
name|setEventName
argument_list|(
name|myForm
operator|.
name|getEvent
argument_list|()
operator|.
name|getEventName
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
comment|//Table of courses for a course event when adding meetings to that event
if|if
condition|(
name|myForm
operator|.
name|getEventId
argument_list|()
operator|!=
literal|null
operator|&&
name|myForm
operator|.
name|getEventId
argument_list|()
operator|!=
literal|0
condition|)
block|{
if|if
condition|(
literal|"Course Related Event"
operator|.
name|equals
argument_list|(
name|myForm
operator|.
name|getEventType
argument_list|()
argument_list|)
condition|)
block|{
name|CourseEvent
name|courseEvent
init|=
operator|new
name|CourseEventDAO
argument_list|()
operator|.
name|get
argument_list|(
name|myForm
operator|.
name|getEventId
argument_list|()
argument_list|)
decl_stmt|;
empty_stmt|;
if|if
condition|(
operator|!
name|courseEvent
operator|.
name|getRelatedCourses
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|WebTable
name|table
init|=
operator|new
name|WebTable
argument_list|(
literal|3
argument_list|,
literal|null
argument_list|,
operator|new
name|String
index|[]
block|{
literal|"Object"
block|,
literal|"Type"
block|,
literal|"Title"
block|}
argument_list|,
operator|new
name|String
index|[]
block|{
literal|"left"
block|,
literal|"left"
block|,
literal|"left"
block|}
argument_list|,
operator|new
name|boolean
index|[]
block|{
literal|true
block|,
literal|true
block|,
literal|true
block|}
argument_list|)
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
operator|new
name|TreeSet
argument_list|(
name|courseEvent
operator|.
name|getRelatedCourses
argument_list|()
argument_list|)
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
block|{
name|RelatedCourseInfo
name|rci
init|=
operator|(
name|RelatedCourseInfo
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|String
name|onclick
init|=
literal|null
decl_stmt|,
name|name
init|=
literal|null
decl_stmt|,
name|type
init|=
literal|null
decl_stmt|,
name|title
init|=
literal|null
decl_stmt|;
switch|switch
condition|(
name|rci
operator|.
name|getOwnerType
argument_list|()
condition|)
block|{
case|case
name|ExamOwner
operator|.
name|sOwnerTypeClass
case|:
name|Class_
name|clazz
init|=
operator|(
name|Class_
operator|)
name|rci
operator|.
name|getOwnerObject
argument_list|()
decl_stmt|;
comment|//    if (clazz.isViewableBy(user))
comment|//        onclick = "onClick=\"document.location='classDetail.do?cid="+clazz.getUniqueId()+"';\"";
name|name
operator|=
name|rci
operator|.
name|getLabel
argument_list|()
expr_stmt|;
comment|//clazz.getClassLabel();
name|type
operator|=
literal|"Class"
expr_stmt|;
name|title
operator|=
name|clazz
operator|.
name|getSchedulePrintNote
argument_list|()
expr_stmt|;
if|if
condition|(
name|title
operator|==
literal|null
operator|||
name|title
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
name|title
operator|=
name|clazz
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getControllingCourseOffering
argument_list|()
operator|.
name|getTitle
argument_list|()
expr_stmt|;
break|break;
case|case
name|ExamOwner
operator|.
name|sOwnerTypeConfig
case|:
name|InstrOfferingConfig
name|config
init|=
operator|(
name|InstrOfferingConfig
operator|)
name|rci
operator|.
name|getOwnerObject
argument_list|()
decl_stmt|;
comment|//   if (config.isViewableBy(user))
comment|//       onclick = "onClick=\"document.location='instructionalOfferingDetail.do?io="+config.getInstructionalOffering().getUniqueId()+"';\"";;
name|name
operator|=
name|rci
operator|.
name|getLabel
argument_list|()
expr_stmt|;
comment|//config.getCourseName()+" ["+config.getName()+"]";
name|type
operator|=
literal|"Configuration"
expr_stmt|;
name|title
operator|=
name|config
operator|.
name|getControllingCourseOffering
argument_list|()
operator|.
name|getTitle
argument_list|()
expr_stmt|;
break|break;
case|case
name|ExamOwner
operator|.
name|sOwnerTypeOffering
case|:
name|InstructionalOffering
name|offering
init|=
operator|(
name|InstructionalOffering
operator|)
name|rci
operator|.
name|getOwnerObject
argument_list|()
decl_stmt|;
comment|//  if (offering.isViewableBy(user))
comment|//      onclick = "onClick=\"document.location='instructionalOfferingDetail.do?io="+offering.getUniqueId()+"';\"";;
name|name
operator|=
name|rci
operator|.
name|getLabel
argument_list|()
expr_stmt|;
comment|//offering.getCourseName();
name|type
operator|=
literal|"Offering"
expr_stmt|;
name|title
operator|=
name|offering
operator|.
name|getControllingCourseOffering
argument_list|()
operator|.
name|getTitle
argument_list|()
expr_stmt|;
break|break;
case|case
name|ExamOwner
operator|.
name|sOwnerTypeCourse
case|:
name|CourseOffering
name|course
init|=
operator|(
name|CourseOffering
operator|)
name|rci
operator|.
name|getOwnerObject
argument_list|()
decl_stmt|;
comment|//    if (course.isViewableBy(user))
comment|//        onclick = "onClick=\"document.location='instructionalOfferingDetail.do?io="+course.getInstructionalOffering().getUniqueId()+"';\"";;
name|name
operator|=
name|rci
operator|.
name|getLabel
argument_list|()
expr_stmt|;
comment|//course.getCourseName();
name|type
operator|=
literal|"Course"
expr_stmt|;
name|title
operator|=
name|course
operator|.
name|getTitle
argument_list|()
expr_stmt|;
break|break;
block|}
name|table
operator|.
name|addLine
argument_list|(
name|onclick
argument_list|,
operator|new
name|String
index|[]
block|{
name|name
block|,
name|type
block|,
name|title
block|}
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
name|request
operator|.
name|setAttribute
argument_list|(
literal|"EventAddMeetings.table"
argument_list|,
name|table
operator|.
name|printTable
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
name|iOp
operator|!=
literal|null
operator|&&
operator|!
literal|"SessionChanged"
operator|.
name|equals
argument_list|(
name|iOp
argument_list|)
condition|)
block|{
name|myForm
operator|.
name|loadDates
argument_list|(
name|request
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
literal|"Add Object"
operator|.
name|equals
argument_list|(
name|iOp
argument_list|)
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
name|Constants
operator|.
name|PREF_ROWS_ADDED
condition|;
name|i
operator|++
control|)
block|{
name|myForm
operator|.
name|addRelatedCourseInfo
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
name|request
operator|.
name|setAttribute
argument_list|(
literal|"hash"
argument_list|,
literal|"objects"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
literal|"Delete"
operator|.
name|equals
argument_list|(
name|iOp
argument_list|)
condition|)
block|{
if|if
condition|(
name|myForm
operator|.
name|getSelected
argument_list|()
operator|>=
literal|0
condition|)
block|{
name|myForm
operator|.
name|deleteRelatedCourseInfo
argument_list|(
name|myForm
operator|.
name|getSelected
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
literal|"Show Scheduled Events"
operator|.
name|equals
argument_list|(
name|iOp
argument_list|)
condition|)
block|{
name|ActionMessages
name|errors
init|=
name|myForm
operator|.
name|validate
argument_list|(
name|mapping
argument_list|,
name|request
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|errors
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|saveErrors
argument_list|(
name|request
argument_list|,
name|errors
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|myForm
operator|.
name|save
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
literal|"Show Availability"
operator|.
name|equals
argument_list|(
name|iOp
argument_list|)
condition|)
block|{
name|ActionMessages
name|errors
init|=
name|myForm
operator|.
name|validate
argument_list|(
name|mapping
argument_list|,
name|request
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|errors
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|saveErrors
argument_list|(
name|request
argument_list|,
name|errors
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|myForm
operator|.
name|save
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"showEventRoomAvailability"
argument_list|)
return|;
block|}
block|}
if|if
condition|(
literal|"Back"
operator|.
name|equals
argument_list|(
name|iOp
argument_list|)
condition|)
block|{
name|myForm
operator|.
name|cleanSessionAttributes
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|myForm
operator|.
name|getIsAddMeetings
argument_list|()
condition|)
block|{
name|response
operator|.
name|sendRedirect
argument_list|(
name|response
operator|.
name|encodeURL
argument_list|(
literal|"eventDetail.do?id="
operator|+
name|myForm
operator|.
name|getEventId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"back"
argument_list|)
return|;
block|}
if|if
condition|(
name|myForm
operator|.
name|getSessionId
argument_list|()
operator|!=
literal|null
condition|)
name|myForm
operator|.
name|setSubjectAreas
argument_list|(
operator|new
name|TreeSet
argument_list|(
name|SubjectArea
operator|.
name|getSubjectAreaList
argument_list|(
name|myForm
operator|.
name|getSessionId
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
comment|//Display the page
if|if
condition|(
name|myForm
operator|.
name|getEventId
argument_list|()
operator|==
literal|null
operator|||
name|myForm
operator|.
name|getEventId
argument_list|()
operator|==
literal|0
condition|)
block|{
name|TimetableManager
name|mgr
init|=
operator|(
name|user
operator|==
literal|null
condition|?
literal|null
else|:
name|TimetableManager
operator|.
name|getManager
argument_list|(
name|user
argument_list|)
operator|)
decl_stmt|;
if|if
condition|(
name|mgr
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|myForm
operator|.
name|getRoomTypes
argument_list|()
operator|==
literal|null
operator|||
name|myForm
operator|.
name|getRoomTypes
argument_list|()
operator|.
name|length
operator|==
literal|0
condition|)
block|{
name|Collection
argument_list|<
name|RoomType
argument_list|>
name|allRoomTypes
init|=
name|myForm
operator|.
name|getAllRoomTypes
argument_list|()
decl_stmt|;
name|Vector
argument_list|<
name|RoomType
argument_list|>
name|defaultRoomTypes
init|=
name|mgr
operator|.
name|findDefaultEventManagerRoomTimesFor
argument_list|(
name|user
operator|.
name|getRole
argument_list|()
argument_list|,
name|myForm
operator|.
name|getSessionId
argument_list|()
argument_list|)
decl_stmt|;
name|Vector
argument_list|<
name|Long
argument_list|>
name|orderedTypeList
init|=
operator|new
name|Vector
argument_list|(
name|allRoomTypes
operator|.
name|size
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|RoomType
name|displayedRoomType
range|:
name|allRoomTypes
control|)
block|{
for|for
control|(
name|RoomType
name|rt
range|:
name|defaultRoomTypes
control|)
block|{
if|if
condition|(
name|displayedRoomType
operator|.
name|getUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
name|rt
operator|.
name|getUniqueId
argument_list|()
argument_list|)
condition|)
block|{
name|orderedTypeList
operator|.
name|add
argument_list|(
name|rt
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
break|break;
block|}
block|}
block|}
name|myForm
operator|.
name|setRoomTypes
argument_list|(
operator|new
name|Long
index|[
name|orderedTypeList
operator|.
name|size
argument_list|()
index|]
argument_list|)
expr_stmt|;
name|int
name|i
init|=
literal|0
decl_stmt|;
for|for
control|(
name|Long
name|l
range|:
name|orderedTypeList
control|)
block|{
name|myForm
operator|.
name|getRoomTypes
argument_list|()
index|[
name|i
index|]
operator|=
name|l
expr_stmt|;
name|i
operator|++
expr_stmt|;
block|}
block|}
block|}
block|}
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"update"
argument_list|)
return|;
block|}
block|}
end_class

end_unit

