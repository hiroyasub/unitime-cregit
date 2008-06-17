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
comment|/**  * This is an object that contains data related to the  table.  * Do not modify this class because it will be overwritten if the configuration file  * related to this class is modified.  *  * @hibernate.class  *  table=""  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|BaseRefTableEntry
implements|implements
name|Serializable
block|{
specifier|public
specifier|static
name|String
name|REF
init|=
literal|"RefTableEntry"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_REFERENCE
init|=
literal|"reference"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_LABEL
init|=
literal|"label"
decl_stmt|;
comment|// constructors
specifier|public
name|BaseRefTableEntry
parameter_list|()
block|{
name|initialize
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|BaseRefTableEntry
parameter_list|(
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
name|BaseRefTableEntry
parameter_list|(
name|Long
name|uniqueId
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|String
name|reference
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
name|setReference
argument_list|(
name|reference
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
name|Long
name|uniqueId
decl_stmt|;
comment|// fields
specifier|private
name|java
operator|.
name|lang
operator|.
name|String
name|reference
decl_stmt|;
specifier|private
name|java
operator|.
name|lang
operator|.
name|String
name|label
decl_stmt|;
comment|/** 	 * Return the unique identifier of this class      * @hibernate.id      *  generator-class="sequence"      *  column="UNIQUEID"      */
specifier|public
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
comment|/** 	 * Return the value associated with the column: REFERENCE 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|String
name|getReference
parameter_list|()
block|{
return|return
name|reference
return|;
block|}
comment|/** 	 * Set the value related to the column: REFERENCE 	 * @param reference the REFERENCE value 	 */
specifier|public
name|void
name|setReference
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|String
name|reference
parameter_list|)
block|{
name|this
operator|.
name|reference
operator|=
name|reference
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: LABEL 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|String
name|getLabel
parameter_list|()
block|{
return|return
name|label
return|;
block|}
comment|/** 	 * Set the value related to the column: LABEL 	 * @param label the LABEL value 	 */
specifier|public
name|void
name|setLabel
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|String
name|label
parameter_list|)
block|{
name|this
operator|.
name|label
operator|=
name|label
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
name|RefTableEntry
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
name|RefTableEntry
name|refTableEntry
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
name|RefTableEntry
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
name|refTableEntry
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
name|refTableEntry
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

