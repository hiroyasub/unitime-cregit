begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.5 (University Timetabling Application)  * Copyright (C) 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|onlinesectioning
operator|.
name|model
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|ObjectInput
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|ObjectOutput
import|;
end_import

begin_import
import|import
name|org
operator|.
name|infinispan
operator|.
name|commons
operator|.
name|marshall
operator|.
name|Externalizer
import|;
end_import

begin_import
import|import
name|org
operator|.
name|infinispan
operator|.
name|commons
operator|.
name|marshall
operator|.
name|SerializeWith
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
name|StudentGroupReservation
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|SerializeWith
argument_list|(
name|XGroupReservation
operator|.
name|XCourseReservationSerializer
operator|.
name|class
argument_list|)
specifier|public
class|class
name|XGroupReservation
extends|extends
name|XReservation
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
specifier|private
name|int
name|iLimit
decl_stmt|;
specifier|private
name|String
name|iGroup
decl_stmt|;
specifier|public
name|XGroupReservation
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
specifier|public
name|XGroupReservation
parameter_list|(
name|ObjectInput
name|in
parameter_list|)
throws|throws
name|IOException
throws|,
name|ClassNotFoundException
block|{
name|super
argument_list|()
expr_stmt|;
name|readExternal
argument_list|(
name|in
argument_list|)
expr_stmt|;
block|}
specifier|public
name|XGroupReservation
parameter_list|(
name|XOffering
name|offering
parameter_list|,
name|StudentGroupReservation
name|reservation
parameter_list|)
block|{
name|super
argument_list|(
name|XReservationType
operator|.
name|Group
argument_list|,
name|offering
argument_list|,
name|reservation
argument_list|)
expr_stmt|;
name|iLimit
operator|=
operator|(
name|reservation
operator|.
name|getLimit
argument_list|()
operator|==
literal|null
condition|?
operator|-
literal|1
else|:
name|reservation
operator|.
name|getLimit
argument_list|()
operator|)
expr_stmt|;
name|iGroup
operator|=
name|reservation
operator|.
name|getGroup
argument_list|()
operator|.
name|getGroupAbbreviation
argument_list|()
expr_stmt|;
block|}
specifier|public
name|String
name|getGroup
parameter_list|()
block|{
return|return
name|iGroup
return|;
block|}
comment|/**      * Group reservations can not be assigned over the limit.      */
annotation|@
name|Override
specifier|public
name|boolean
name|canAssignOverLimit
parameter_list|()
block|{
return|return
literal|false
return|;
block|}
comment|/**      * Reservation limit      */
annotation|@
name|Override
specifier|public
name|int
name|getReservationLimit
parameter_list|()
block|{
return|return
name|iLimit
return|;
block|}
comment|/**      * Overlaps are allowed for individual reservations.       */
annotation|@
name|Override
specifier|public
name|boolean
name|isAllowOverlap
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
name|isApplicable
parameter_list|(
name|XStudent
name|student
parameter_list|)
block|{
return|return
name|student
operator|.
name|getGroups
argument_list|()
operator|.
name|contains
argument_list|(
name|iGroup
argument_list|)
return|;
block|}
comment|/**      * Individual or group reservation must be used (unless it is expired)      * @return true if not expired, false if expired      */
annotation|@
name|Override
specifier|public
name|boolean
name|mustBeUsed
parameter_list|()
block|{
return|return
literal|true
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|readExternal
parameter_list|(
name|ObjectInput
name|in
parameter_list|)
throws|throws
name|IOException
throws|,
name|ClassNotFoundException
block|{
name|super
operator|.
name|readExternal
argument_list|(
name|in
argument_list|)
expr_stmt|;
name|iGroup
operator|=
operator|(
name|String
operator|)
name|in
operator|.
name|readObject
argument_list|()
expr_stmt|;
name|iLimit
operator|=
name|in
operator|.
name|readInt
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|writeExternal
parameter_list|(
name|ObjectOutput
name|out
parameter_list|)
throws|throws
name|IOException
block|{
name|super
operator|.
name|writeExternal
argument_list|(
name|out
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeObject
argument_list|(
name|iGroup
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeInt
argument_list|(
name|iLimit
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
class|class
name|XCourseReservationSerializer
implements|implements
name|Externalizer
argument_list|<
name|XGroupReservation
argument_list|>
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
annotation|@
name|Override
specifier|public
name|void
name|writeObject
parameter_list|(
name|ObjectOutput
name|output
parameter_list|,
name|XGroupReservation
name|object
parameter_list|)
throws|throws
name|IOException
block|{
name|object
operator|.
name|writeExternal
argument_list|(
name|output
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|XGroupReservation
name|readObject
parameter_list|(
name|ObjectInput
name|input
parameter_list|)
throws|throws
name|IOException
throws|,
name|ClassNotFoundException
block|{
return|return
operator|new
name|XGroupReservation
argument_list|(
name|input
argument_list|)
return|;
block|}
block|}
block|}
end_class

end_unit

