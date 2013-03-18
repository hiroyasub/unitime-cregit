begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 (University Timetabling Application)  * Copyright (C) 2011, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|services
package|;
end_package

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
name|FilterRpcRequest
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
name|gwt
operator|.
name|shared
operator|.
name|ReservationException
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
name|ReservationInterface
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

begin_interface
specifier|public
interface|interface
name|ReservationServiceAsync
block|{
specifier|public
name|void
name|getOffering
parameter_list|(
name|Long
name|offeringId
parameter_list|,
name|AsyncCallback
argument_list|<
name|ReservationInterface
operator|.
name|Offering
argument_list|>
name|callback
parameter_list|)
throws|throws
name|ReservationException
throws|,
name|PageAccessException
function_decl|;
specifier|public
name|void
name|getOfferingByCourseName
parameter_list|(
name|String
name|course
parameter_list|,
name|AsyncCallback
argument_list|<
name|ReservationInterface
operator|.
name|Offering
argument_list|>
name|callback
parameter_list|)
throws|throws
name|ReservationException
throws|,
name|PageAccessException
function_decl|;
specifier|public
name|void
name|getAreas
parameter_list|(
name|AsyncCallback
argument_list|<
name|List
argument_list|<
name|ReservationInterface
operator|.
name|Area
argument_list|>
argument_list|>
name|callback
parameter_list|)
throws|throws
name|ReservationException
throws|,
name|PageAccessException
function_decl|;
specifier|public
name|void
name|getStudentGroups
parameter_list|(
name|AsyncCallback
argument_list|<
name|List
argument_list|<
name|ReservationInterface
operator|.
name|IdName
argument_list|>
argument_list|>
name|callback
parameter_list|)
throws|throws
name|ReservationException
throws|,
name|PageAccessException
function_decl|;
specifier|public
name|void
name|getCurricula
parameter_list|(
name|Long
name|offeringId
parameter_list|,
name|AsyncCallback
argument_list|<
name|List
argument_list|<
name|ReservationInterface
operator|.
name|Curriculum
argument_list|>
argument_list|>
name|callback
parameter_list|)
throws|throws
name|ReservationException
throws|,
name|PageAccessException
function_decl|;
specifier|public
name|void
name|getReservation
parameter_list|(
name|Long
name|reservationId
parameter_list|,
name|AsyncCallback
argument_list|<
name|ReservationInterface
argument_list|>
name|callback
parameter_list|)
throws|throws
name|ReservationException
throws|,
name|PageAccessException
function_decl|;
specifier|public
name|void
name|getReservations
parameter_list|(
name|Long
name|offeringId
parameter_list|,
name|AsyncCallback
argument_list|<
name|List
argument_list|<
name|ReservationInterface
argument_list|>
argument_list|>
name|callback
parameter_list|)
throws|throws
name|ReservationException
throws|,
name|PageAccessException
function_decl|;
specifier|public
name|void
name|save
parameter_list|(
name|ReservationInterface
name|reservation
parameter_list|,
name|AsyncCallback
argument_list|<
name|Long
argument_list|>
name|callback
parameter_list|)
throws|throws
name|ReservationException
throws|,
name|PageAccessException
function_decl|;
specifier|public
name|void
name|delete
parameter_list|(
name|Long
name|reservationId
parameter_list|,
name|AsyncCallback
argument_list|<
name|Boolean
argument_list|>
name|callback
parameter_list|)
throws|throws
name|ReservationException
throws|,
name|PageAccessException
function_decl|;
specifier|public
name|void
name|canAddReservation
parameter_list|(
name|AsyncCallback
argument_list|<
name|Boolean
argument_list|>
name|callback
parameter_list|)
throws|throws
name|ReservationException
throws|,
name|PageAccessException
function_decl|;
specifier|public
name|void
name|lastReservationFilter
parameter_list|(
name|AsyncCallback
argument_list|<
name|String
argument_list|>
name|callback
parameter_list|)
throws|throws
name|ReservationException
throws|,
name|PageAccessException
function_decl|;
specifier|public
name|void
name|findReservations
parameter_list|(
name|FilterRpcRequest
name|filter
parameter_list|,
name|AsyncCallback
argument_list|<
name|List
argument_list|<
name|ReservationInterface
argument_list|>
argument_list|>
name|callback
parameter_list|)
throws|throws
name|ReservationException
throws|,
name|PageAccessException
function_decl|;
block|}
end_interface

end_unit

