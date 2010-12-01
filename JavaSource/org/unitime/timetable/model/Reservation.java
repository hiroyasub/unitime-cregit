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
name|model
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
name|model
operator|.
name|base
operator|.
name|BaseReservation
import|;
end_import

begin_comment
comment|/*  * OwnerID Discriminator  * 		InstructionalOffering: "I"  * 		InstrOfferingConfig:   "R"  * 		Class_:                "C"  *		CourseOffering:        "U"  */
end_comment

begin_class
specifier|public
class|class
name|Reservation
extends|extends
name|BaseReservation
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
comment|/** Request Attribute name for Reservations **/
specifier|public
specifier|static
specifier|final
name|String
name|RESV_REQUEST_ATTR
init|=
literal|"reservationsList"
decl_stmt|;
comment|/** Request Attribute name for Reservation Class **/
specifier|public
specifier|static
specifier|final
name|String
name|RESV_CLASS_REQUEST_ATTR
init|=
literal|"reservationsClassList"
decl_stmt|;
comment|/** Request Attribute name for Reservation Priorities **/
specifier|public
specifier|static
specifier|final
name|String
name|RESV_PRIORITY_REQUEST_ATTR
init|=
literal|"reservationsPriorityList"
decl_stmt|;
comment|/*[CONSTRUCTOR MARKER BEGIN]*/
specifier|public
name|Reservation
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|Reservation
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Long
name|uniqueId
parameter_list|)
block|{
name|super
argument_list|(
name|uniqueId
argument_list|)
expr_stmt|;
block|}
comment|/*[CONSTRUCTOR MARKER END]*/
block|}
end_class

end_unit

