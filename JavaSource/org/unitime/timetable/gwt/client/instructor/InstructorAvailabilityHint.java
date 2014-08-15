begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.4 - 3.5 (University Timetabling Application)  * Copyright (C) 2012 - 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|gwt
operator|.
name|client
operator|.
name|instructor
package|;
end_package

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
name|client
operator|.
name|GwtHint
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
name|client
operator|.
name|instructor
operator|.
name|InstructorAvailabilityWidget
operator|.
name|InstructorAvailabilityModel
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
name|client
operator|.
name|instructor
operator|.
name|InstructorAvailabilityWidget
operator|.
name|InstructorAvailabilityRequest
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
name|client
operator|.
name|rooms
operator|.
name|RoomSharingWidget
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
name|command
operator|.
name|client
operator|.
name|GwtRpcService
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
name|command
operator|.
name|client
operator|.
name|GwtRpcServiceAsync
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
name|RoomInterface
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|gwt
operator|.
name|core
operator|.
name|client
operator|.
name|GWT
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|gwt
operator|.
name|core
operator|.
name|client
operator|.
name|JavaScriptObject
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|gwt
operator|.
name|dom
operator|.
name|client
operator|.
name|Element
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|gwt
operator|.
name|user
operator|.
name|client
operator|.
name|rpc
operator|.
name|AsyncCallback
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|InstructorAvailabilityHint
block|{
specifier|private
specifier|static
name|RoomSharingWidget
name|sSharing
decl_stmt|;
specifier|private
specifier|static
name|String
name|sLastInstructorId
init|=
literal|null
decl_stmt|;
specifier|private
specifier|static
name|GwtRpcServiceAsync
name|RPC
init|=
name|GWT
operator|.
name|create
argument_list|(
name|GwtRpcService
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|public
specifier|static
name|RoomSharingWidget
name|content
parameter_list|(
name|RoomInterface
operator|.
name|RoomSharingModel
name|model
parameter_list|)
block|{
if|if
condition|(
name|sSharing
operator|==
literal|null
condition|)
name|sSharing
operator|=
operator|new
name|InstructorAvailabilityWidget
argument_list|()
expr_stmt|;
name|sSharing
operator|.
name|setModel
argument_list|(
name|model
argument_list|)
expr_stmt|;
return|return
name|sSharing
return|;
block|}
comment|/** Never use from GWT code */
specifier|public
specifier|static
name|void
name|_showInstructorSharingHint
parameter_list|(
name|JavaScriptObject
name|source
parameter_list|,
name|String
name|instructorId
parameter_list|)
block|{
name|showHint
argument_list|(
operator|(
name|Element
operator|)
name|source
operator|.
name|cast
argument_list|()
argument_list|,
name|instructorId
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|void
name|showHint
parameter_list|(
specifier|final
name|Element
name|relativeObject
parameter_list|,
specifier|final
name|String
name|instructorId
parameter_list|)
block|{
name|sLastInstructorId
operator|=
name|instructorId
expr_stmt|;
name|RPC
operator|.
name|execute
argument_list|(
name|InstructorAvailabilityRequest
operator|.
name|load
argument_list|(
name|instructorId
argument_list|)
argument_list|,
operator|new
name|AsyncCallback
argument_list|<
name|InstructorAvailabilityModel
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onFailure
parameter_list|(
name|Throwable
name|caught
parameter_list|)
block|{
block|}
annotation|@
name|Override
specifier|public
name|void
name|onSuccess
parameter_list|(
name|InstructorAvailabilityModel
name|result
parameter_list|)
block|{
if|if
condition|(
name|instructorId
operator|.
name|equals
argument_list|(
name|sLastInstructorId
argument_list|)
operator|&&
name|result
operator|!=
literal|null
condition|)
name|GwtHint
operator|.
name|showHint
argument_list|(
name|relativeObject
argument_list|,
name|content
argument_list|(
name|result
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|void
name|hideHint
parameter_list|()
block|{
name|GwtHint
operator|.
name|hideHint
argument_list|()
expr_stmt|;
block|}
specifier|public
specifier|static
specifier|native
name|void
name|createTriggers
parameter_list|()
comment|/*-{ 	$wnd.showGwtInstructorAvailabilityHint = function(source, content) { 		@org.unitime.timetable.gwt.client.instructor.InstructorAvailabilityHint::_showInstructorSharingHint(Lcom/google/gwt/core/client/JavaScriptObject;Ljava/lang/String;)(source, content); 	}; 	$wnd.hideGwtInstructorAvailabilityHint = function() { 		@org.unitime.timetable.gwt.client.instructor.InstructorAvailabilityHint::hideHint()(); 	}; 	}-*/
function_decl|;
block|}
end_class

end_unit

