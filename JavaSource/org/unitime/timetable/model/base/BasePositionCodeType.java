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
comment|/**  * This is an object that contains data related to the POSITION_CODE_TO_TYPE table.  * Do not modify this class because it will be overwritten if the configuration file  * related to this class is modified.  *  * @hibernate.class  *  table="POSITION_CODE_TO_TYPE"  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|BasePositionCodeType
implements|implements
name|Serializable
block|{
specifier|public
specifier|static
name|String
name|REF
init|=
literal|"PositionCodeType"
decl_stmt|;
comment|// constructors
specifier|public
name|BasePositionCodeType
parameter_list|()
block|{
name|initialize
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|BasePositionCodeType
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|String
name|positionCode
parameter_list|)
block|{
name|this
operator|.
name|setPositionCode
argument_list|(
name|positionCode
argument_list|)
expr_stmt|;
name|initialize
argument_list|()
expr_stmt|;
block|}
specifier|protected
name|void
name|initialize
parameter_list|()
block|{
block|}
specifier|private
name|int
name|hashCode
init|=
name|Integer
operator|.
name|MIN_VALUE
decl_stmt|;
comment|// primary key
specifier|private
name|java
operator|.
name|lang
operator|.
name|String
name|positionCode
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
name|PositionType
name|positionType
decl_stmt|;
comment|// collections
specifier|private
name|java
operator|.
name|util
operator|.
name|Set
name|staff
decl_stmt|;
comment|/** 	 * Return the unique identifier of this class      * @hibernate.id      *  column="POSITION_CODE"      */
specifier|public
name|java
operator|.
name|lang
operator|.
name|String
name|getPositionCode
parameter_list|()
block|{
return|return
name|positionCode
return|;
block|}
comment|/** 	 * Set the unique identifier of this class 	 * @param positionCode the new ID 	 */
specifier|public
name|void
name|setPositionCode
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|String
name|positionCode
parameter_list|)
block|{
name|this
operator|.
name|positionCode
operator|=
name|positionCode
expr_stmt|;
name|this
operator|.
name|hashCode
operator|=
name|Integer
operator|.
name|MIN_VALUE
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: POS_CODE_TYPE 	 */
specifier|public
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|PositionType
name|getPositionType
parameter_list|()
block|{
return|return
name|positionType
return|;
block|}
comment|/** 	 * Set the value related to the column: POS_CODE_TYPE 	 * @param positionType the POS_CODE_TYPE value 	 */
specifier|public
name|void
name|setPositionType
parameter_list|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|PositionType
name|positionType
parameter_list|)
block|{
name|this
operator|.
name|positionType
operator|=
name|positionType
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: staff 	 */
specifier|public
name|java
operator|.
name|util
operator|.
name|Set
name|getStaff
parameter_list|()
block|{
return|return
name|staff
return|;
block|}
comment|/** 	 * Set the value related to the column: staff 	 * @param staff the staff value 	 */
specifier|public
name|void
name|setStaff
parameter_list|(
name|java
operator|.
name|util
operator|.
name|Set
name|staff
parameter_list|)
block|{
name|this
operator|.
name|staff
operator|=
name|staff
expr_stmt|;
block|}
specifier|public
name|void
name|addTostaff
parameter_list|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Staff
name|staff
parameter_list|)
block|{
if|if
condition|(
literal|null
operator|==
name|getStaff
argument_list|()
condition|)
name|setStaff
argument_list|(
operator|new
name|java
operator|.
name|util
operator|.
name|HashSet
argument_list|()
argument_list|)
expr_stmt|;
name|getStaff
argument_list|()
operator|.
name|add
argument_list|(
name|staff
argument_list|)
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
name|PositionCodeType
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
name|PositionCodeType
name|positionCodeType
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
name|PositionCodeType
operator|)
name|obj
decl_stmt|;
if|if
condition|(
literal|null
operator|==
name|this
operator|.
name|getPositionCode
argument_list|()
operator|||
literal|null
operator|==
name|positionCodeType
operator|.
name|getPositionCode
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
name|getPositionCode
argument_list|()
operator|.
name|equals
argument_list|(
name|positionCodeType
operator|.
name|getPositionCode
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
name|getPositionCode
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
name|getPositionCode
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

