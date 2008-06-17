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

begin_import
import|import
name|org
operator|.
name|dom4j
operator|.
name|Document
import|;
end_import

begin_comment
comment|/**  * This is an object that contains data related to the SOLVER_INFO table.  * Do not modify this class because it will be overwritten if the configuration file  * related to this class is modified.  *  * @hibernate.class  *  table="SOLVER_INFO"  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|BaseSolverInfo
implements|implements
name|Serializable
block|{
specifier|public
specifier|static
name|String
name|REF
init|=
literal|"SolverInfo"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_VALUE
init|=
literal|"value"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_OPT
init|=
literal|"opt"
decl_stmt|;
comment|// constructors
specifier|public
name|BaseSolverInfo
parameter_list|()
block|{
name|initialize
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|BaseSolverInfo
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
name|BaseSolverInfo
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Long
name|uniqueId
parameter_list|,
name|Document
name|value
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
name|setValue
argument_list|(
name|value
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
name|Document
name|value
decl_stmt|;
specifier|private
name|java
operator|.
name|lang
operator|.
name|String
name|opt
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
name|SolverInfoDef
name|definition
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
comment|/** 	 * Return the value associated with the column: VALUE 	 */
specifier|public
name|Document
name|getValue
parameter_list|()
block|{
return|return
name|value
return|;
block|}
comment|/** 	 * Set the value related to the column: VALUE 	 * @param value the VALUE value 	 */
specifier|public
name|void
name|setValue
parameter_list|(
name|Document
name|value
parameter_list|)
block|{
name|this
operator|.
name|value
operator|=
name|value
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: OPT 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|String
name|getOpt
parameter_list|()
block|{
return|return
name|opt
return|;
block|}
comment|/** 	 * Set the value related to the column: OPT 	 * @param opt the OPT value 	 */
specifier|public
name|void
name|setOpt
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|String
name|opt
parameter_list|)
block|{
name|this
operator|.
name|opt
operator|=
name|opt
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: SOLVER_INFO_DEF_ID 	 */
specifier|public
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|SolverInfoDef
name|getDefinition
parameter_list|()
block|{
return|return
name|definition
return|;
block|}
comment|/** 	 * Set the value related to the column: SOLVER_INFO_DEF_ID 	 * @param definition the SOLVER_INFO_DEF_ID value 	 */
specifier|public
name|void
name|setDefinition
parameter_list|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|SolverInfoDef
name|definition
parameter_list|)
block|{
name|this
operator|.
name|definition
operator|=
name|definition
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
name|SolverInfo
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
name|SolverInfo
name|solverInfo
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
name|SolverInfo
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
name|solverInfo
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
name|solverInfo
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

