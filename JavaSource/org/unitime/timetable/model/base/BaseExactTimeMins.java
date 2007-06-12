begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.0 (University Course Timetabling& Student Sectioning Application)  * Copyright (C) 2007, UniTime.org, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program; if not, write to the Free Software Foundation, Inc.,  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
comment|/**  * This is an object that contains data related to the EXACT_TIME_MINS table.  * Do not modify this class because it will be overwritten if the configuration file  * related to this class is modified.  *  * @hibernate.class  *  table="EXACT_TIME_MINS"  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|BaseExactTimeMins
implements|implements
name|Serializable
block|{
specifier|public
specifier|static
name|String
name|REF
init|=
literal|"ExactTimeMins"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_MINS_PER_MTG_MIN
init|=
literal|"minsPerMtgMin"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_MINS_PER_MTG_MAX
init|=
literal|"minsPerMtgMax"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_NR_SLOTS
init|=
literal|"nrSlots"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_BREAK_TIME
init|=
literal|"breakTime"
decl_stmt|;
comment|// constructors
specifier|public
name|BaseExactTimeMins
parameter_list|()
block|{
name|initialize
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|BaseExactTimeMins
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Long
name|uniqueId
parameter_list|)
block|{
name|this
operator|.
name|setUniqueId
argument_list|(
name|uniqueId
argument_list|)
expr_stmt|;
name|initialize
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for required fields 	 */
specifier|public
name|BaseExactTimeMins
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Long
name|uniqueId
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|Integer
name|minsPerMtgMin
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|Integer
name|minsPerMtgMax
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|Integer
name|nrSlots
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|Integer
name|breakTime
parameter_list|)
block|{
name|this
operator|.
name|setUniqueId
argument_list|(
name|uniqueId
argument_list|)
expr_stmt|;
name|this
operator|.
name|setMinsPerMtgMin
argument_list|(
name|minsPerMtgMin
argument_list|)
expr_stmt|;
name|this
operator|.
name|setMinsPerMtgMax
argument_list|(
name|minsPerMtgMax
argument_list|)
expr_stmt|;
name|this
operator|.
name|setNrSlots
argument_list|(
name|nrSlots
argument_list|)
expr_stmt|;
name|this
operator|.
name|setBreakTime
argument_list|(
name|breakTime
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
name|Long
name|uniqueId
decl_stmt|;
comment|// fields
specifier|private
name|java
operator|.
name|lang
operator|.
name|Integer
name|minsPerMtgMin
decl_stmt|;
specifier|private
name|java
operator|.
name|lang
operator|.
name|Integer
name|minsPerMtgMax
decl_stmt|;
specifier|private
name|java
operator|.
name|lang
operator|.
name|Integer
name|nrSlots
decl_stmt|;
specifier|private
name|java
operator|.
name|lang
operator|.
name|Integer
name|breakTime
decl_stmt|;
comment|/** 	 * Return the unique identifier of this class      * @hibernate.id      *  generator-class="sequence"      *  column="UNIQUEID"      */
specifier|public
name|java
operator|.
name|lang
operator|.
name|Long
name|getUniqueId
parameter_list|()
block|{
return|return
name|uniqueId
return|;
block|}
comment|/** 	 * Set the unique identifier of this class 	 * @param uniqueId the new ID 	 */
specifier|public
name|void
name|setUniqueId
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Long
name|uniqueId
parameter_list|)
block|{
name|this
operator|.
name|uniqueId
operator|=
name|uniqueId
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
comment|/** 	 * Return the value associated with the column: MINS_MIN 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|Integer
name|getMinsPerMtgMin
parameter_list|()
block|{
return|return
name|minsPerMtgMin
return|;
block|}
comment|/** 	 * Set the value related to the column: MINS_MIN 	 * @param minsPerMtgMin the MINS_MIN value 	 */
specifier|public
name|void
name|setMinsPerMtgMin
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Integer
name|minsPerMtgMin
parameter_list|)
block|{
name|this
operator|.
name|minsPerMtgMin
operator|=
name|minsPerMtgMin
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: MINS_MAX 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|Integer
name|getMinsPerMtgMax
parameter_list|()
block|{
return|return
name|minsPerMtgMax
return|;
block|}
comment|/** 	 * Set the value related to the column: MINS_MAX 	 * @param minsPerMtgMax the MINS_MAX value 	 */
specifier|public
name|void
name|setMinsPerMtgMax
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Integer
name|minsPerMtgMax
parameter_list|)
block|{
name|this
operator|.
name|minsPerMtgMax
operator|=
name|minsPerMtgMax
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: NR_SLOTS 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|Integer
name|getNrSlots
parameter_list|()
block|{
return|return
name|nrSlots
return|;
block|}
comment|/** 	 * Set the value related to the column: NR_SLOTS 	 * @param nrSlots the NR_SLOTS value 	 */
specifier|public
name|void
name|setNrSlots
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Integer
name|nrSlots
parameter_list|)
block|{
name|this
operator|.
name|nrSlots
operator|=
name|nrSlots
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: BREAK_TIME 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|Integer
name|getBreakTime
parameter_list|()
block|{
return|return
name|breakTime
return|;
block|}
comment|/** 	 * Set the value related to the column: BREAK_TIME 	 * @param breakTime the BREAK_TIME value 	 */
specifier|public
name|void
name|setBreakTime
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Integer
name|breakTime
parameter_list|)
block|{
name|this
operator|.
name|breakTime
operator|=
name|breakTime
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
name|ExactTimeMins
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
name|ExactTimeMins
name|exactTimeMins
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
name|ExactTimeMins
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
name|exactTimeMins
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
name|exactTimeMins
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

