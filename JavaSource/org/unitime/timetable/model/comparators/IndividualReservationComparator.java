begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.1 (University Timetabling Application)  * Copyright (C) 2008, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program; if not, write to the Free Software Foundation, Inc.,  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
operator|.
name|comparators
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Comparator
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
name|IndividualReservation
import|;
end_import

begin_comment
comment|/**  * Compares academic area reservations based on puid  *   * @author Heston Fernandes  */
end_comment

begin_class
specifier|public
class|class
name|IndividualReservationComparator
implements|implements
name|Comparator
block|{
specifier|public
name|int
name|compare
parameter_list|(
name|Object
name|o1
parameter_list|,
name|Object
name|o2
parameter_list|)
block|{
if|if
condition|(
operator|!
operator|(
name|o1
operator|instanceof
name|IndividualReservation
operator|)
condition|)
throw|throw
operator|new
name|ClassCastException
argument_list|(
literal|"o1 must be of type IndividualReservation"
argument_list|)
throw|;
if|if
condition|(
operator|!
operator|(
name|o2
operator|instanceof
name|IndividualReservation
operator|)
condition|)
throw|throw
operator|new
name|ClassCastException
argument_list|(
literal|"o2 must be of type IndividualReservation"
argument_list|)
throw|;
name|IndividualReservation
name|a1
init|=
operator|(
name|IndividualReservation
operator|)
name|o1
decl_stmt|;
name|IndividualReservation
name|a2
init|=
operator|(
name|IndividualReservation
operator|)
name|o2
decl_stmt|;
return|return
name|a1
operator|.
name|getExternalUniqueId
argument_list|()
operator|.
name|compareTo
argument_list|(
name|a2
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|)
return|;
block|}
block|}
end_class

end_unit

