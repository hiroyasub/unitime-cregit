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
name|base
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

begin_comment
comment|/**  * This is an object that contains data related to the POS_RESERVATION table.  * Do not modify this class because it will be overwritten if the configuration file  * related to this class is modified.  *  * @hibernate.class  *  table="POS_RESERVATION"  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|BasePosReservation
extends|extends
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|AcadAreaPosReservation
implements|implements
name|Serializable
block|{
specifier|public
specifier|static
name|String
name|REF
init|=
literal|"PosReservation"
decl_stmt|;
comment|// constructors
specifier|public
name|BasePosReservation
parameter_list|()
block|{
name|initialize
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|BasePosReservation
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
comment|/** 	 * Constructor for required fields 	 */
specifier|public
name|BasePosReservation
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Long
name|uniqueId
parameter_list|,
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|ReservationType
name|reservationType
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|String
name|ownerClassId
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|Long
name|owner
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|Integer
name|priority
parameter_list|)
block|{
name|super
argument_list|(
name|uniqueId
argument_list|,
name|reservationType
argument_list|,
name|ownerClassId
argument_list|,
name|owner
argument_list|,
name|priority
argument_list|)
expr_stmt|;
block|}
specifier|private
name|int
name|hashCode
init|=
name|Integer
operator|.
name|MIN_VALUE
decl_stmt|;
comment|// many to one
specifier|private
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|PosMajor
name|posMajor
decl_stmt|;
comment|/** 	 * Return the value associated with the column: POS_MAJOR 	 */
specifier|public
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|PosMajor
name|getPosMajor
parameter_list|()
block|{
return|return
name|posMajor
return|;
block|}
comment|/** 	 * Set the value related to the column: POS_MAJOR 	 * @param posMajor the POS_MAJOR value 	 */
specifier|public
name|void
name|setPosMajor
parameter_list|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|PosMajor
name|posMajor
parameter_list|)
block|{
name|this
operator|.
name|posMajor
operator|=
name|posMajor
expr_stmt|;
block|}
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|obj
parameter_list|)
block|{
if|if
condition|(
literal|null
operator|==
name|obj
condition|)
return|return
literal|false
return|;
if|if
condition|(
operator|!
operator|(
name|obj
operator|instanceof
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|PosReservation
operator|)
condition|)
return|return
literal|false
return|;
else|else
block|{
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|PosReservation
name|posReservation
init|=
operator|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|PosReservation
operator|)
name|obj
decl_stmt|;
if|if
condition|(
literal|null
operator|==
name|this
operator|.
name|getUniqueId
argument_list|()
operator|||
literal|null
operator|==
name|posReservation
operator|.
name|getUniqueId
argument_list|()
condition|)
return|return
literal|false
return|;
else|else
return|return
operator|(
name|this
operator|.
name|getUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
name|posReservation
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|)
return|;
block|}
block|}
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
if|if
condition|(
name|Integer
operator|.
name|MIN_VALUE
operator|==
name|this
operator|.
name|hashCode
condition|)
block|{
if|if
condition|(
literal|null
operator|==
name|this
operator|.
name|getUniqueId
argument_list|()
condition|)
return|return
name|super
operator|.
name|hashCode
argument_list|()
return|;
else|else
block|{
name|String
name|hashStr
init|=
name|this
operator|.
name|getClass
argument_list|()
operator|.
name|getName
argument_list|()
operator|+
literal|":"
operator|+
name|this
operator|.
name|getUniqueId
argument_list|()
operator|.
name|hashCode
argument_list|()
decl_stmt|;
name|this
operator|.
name|hashCode
operator|=
name|hashStr
operator|.
name|hashCode
argument_list|()
expr_stmt|;
block|}
block|}
return|return
name|this
operator|.
name|hashCode
return|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|super
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
end_class

end_unit

