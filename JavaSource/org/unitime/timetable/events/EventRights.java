begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.4 (University Timetabling Application)  * Copyright (C) 2012, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|events
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|Serializable
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Date
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
name|gwt
operator|.
name|shared
operator|.
name|EventInterface
operator|.
name|EventType
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
name|gwt
operator|.
name|shared
operator|.
name|PageAccessException
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
name|Meeting
import|;
end_import

begin_interface
specifier|public
interface|interface
name|EventRights
extends|extends
name|Serializable
block|{
comment|/** 	 * Creates a default page access exception 	 * @return Authentication required if not authenticated, insufficient rights if authenticated 	 */
specifier|public
name|PageAccessException
name|getException
parameter_list|()
function_decl|;
comment|/** 	 * Throws an exception if access to Event Management features is disabled 	 * @throws PageAccessException when not authenticated but the authentication is required to access events 	 */
specifier|public
name|void
name|checkAccess
parameter_list|()
throws|throws
name|PageAccessException
function_decl|;
comment|/** 	 * Check whether an event can be created by the current user. 	 * @param type event type, Special Event if null 	 * @param userId event main contact (current user if null) 	 * @return true if the user can create an event  	 */
specifier|public
name|boolean
name|canAddEvent
parameter_list|(
name|EventType
name|type
parameter_list|,
name|String
name|userId
parameter_list|)
function_decl|;
comment|/** 	 * Check if people lookup can be enabled while creating a new event 	 * @return true if the user can lookup people for event contacts 	 */
specifier|public
name|boolean
name|canLookupContacts
parameter_list|()
function_decl|;
comment|/** 	 * Check if the user can see a schedule of the given user 	 * @param userId user external id, any user if null  	 * @return true if a schedule of given user can be displayed 	 */
specifier|public
name|boolean
name|canSeeSchedule
parameter_list|(
name|String
name|userId
parameter_list|)
function_decl|;
comment|/** 	 * Check if the details of an event can be seen by the user. 	 * @param event an event 	 * @return true if the user can open Event Detail page for an event 	 */
specifier|public
name|boolean
name|canSee
parameter_list|(
name|Event
name|event
parameter_list|)
function_decl|;
comment|/** 	 * Check if the given event can be edited (e.g., a new meeting can be added to it). 	 * @param event an event 	 * @return true if the user can open Edit Event page for an event 	 */
specifier|public
name|boolean
name|canEdit
parameter_list|(
name|Event
name|event
parameter_list|)
function_decl|;
comment|/** 	 * Check if the given time is in the past or outside of the term (to be used with {@link Meeting#getStartTime()}). 	 * @param date start time of a meeting 	 * @return true if the meeting is in the past (e.g., it should not be tinkered with) 	 */
specifier|public
name|boolean
name|isPastOrOutside
parameter_list|(
name|Date
name|date
parameter_list|)
function_decl|;
comment|/** 	 * Check if the given meeting can be edited (e.g., removed). 	 * Both {@link EventRights#canEdit(Event)} and {@link EventRights#canEdit(Meeting)} should be true. 	 * @param meeting a meeting 	 * @return true if the user can edit the given meeting 	 */
specifier|public
name|boolean
name|canEdit
parameter_list|(
name|Meeting
name|meeting
parameter_list|)
function_decl|;
comment|/** 	 * Check if the given meeting can be approved / rejected / inquired 	 * @param meeting a meeting 	 * @return true if the user can approve / reject / inquire the given meeting 	 */
specifier|public
name|boolean
name|canApprove
parameter_list|(
name|Meeting
name|meeting
parameter_list|)
function_decl|;
comment|/** 	 * Check if the given location is an event location 	 * @param location a location (null to check if there is at least one such location) 	 * @return if the given location is event location (UniTime is maintaining events for this location)  	 */
specifier|public
name|boolean
name|isEventLocation
parameter_list|(
name|Long
name|locationId
parameter_list|)
function_decl|;
comment|/** 	 * Check if the user can create a meeting using the given location. 	 * @param location a location 	 * @return true, if a meeting can be created using the given location 	 */
specifier|public
name|boolean
name|canCreate
parameter_list|(
name|Long
name|locationId
parameter_list|)
function_decl|;
comment|/** 	 * Check if the user can approve meetings in the given location. 	 * @param location a location 	 * @return true if a newly created meeting by the user should get automatically approved 	 */
specifier|public
name|boolean
name|canApprove
parameter_list|(
name|Long
name|locationId
parameter_list|)
function_decl|;
comment|/** 	 * Check if the user can overbook meetings in the given location. 	 * @param location a location 	 * @return true if the user can create a meeting in the room that is conflicting with some other meeting 	 */
specifier|public
name|boolean
name|canOverbook
parameter_list|(
name|Long
name|locationId
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

