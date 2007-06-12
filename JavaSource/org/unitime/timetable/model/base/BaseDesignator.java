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
comment|/**  * This is an object that contains data related to the DESIGNATOR table.  * Do not modify this class because it will be overwritten if the configuration file  * related to this class is modified.  *  * @hibernate.class  *  table="DESIGNATOR"  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|BaseDesignator
implements|implements
name|Serializable
block|{
specifier|public
specifier|static
name|String
name|REF
init|=
literal|"Designator"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_CODE
init|=
literal|"code"
decl_stmt|;
comment|// constructors
specifier|public
name|BaseDesignator
parameter_list|()
block|{
name|initialize
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|BaseDesignator
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
name|BaseDesignator
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
name|SubjectArea
name|subjectArea
parameter_list|,
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|DepartmentalInstructor
name|instructor
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|String
name|code
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
name|setSubjectArea
argument_list|(
name|subjectArea
argument_list|)
expr_stmt|;
name|this
operator|.
name|setInstructor
argument_list|(
name|instructor
argument_list|)
expr_stmt|;
name|this
operator|.
name|setCode
argument_list|(
name|code
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
name|String
name|code
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
name|SubjectArea
name|subjectArea
decl_stmt|;
specifier|private
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|DepartmentalInstructor
name|instructor
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
comment|/** 	 * Return the value associated with the column: CODE 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|String
name|getCode
parameter_list|()
block|{
return|return
name|code
return|;
block|}
comment|/** 	 * Set the value related to the column: CODE 	 * @param code the CODE value 	 */
specifier|public
name|void
name|setCode
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|String
name|code
parameter_list|)
block|{
name|this
operator|.
name|code
operator|=
name|code
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: SUBJECT_AREA_ID 	 */
specifier|public
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|SubjectArea
name|getSubjectArea
parameter_list|()
block|{
return|return
name|subjectArea
return|;
block|}
comment|/** 	 * Set the value related to the column: SUBJECT_AREA_ID 	 * @param subjectArea the SUBJECT_AREA_ID value 	 */
specifier|public
name|void
name|setSubjectArea
parameter_list|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|SubjectArea
name|subjectArea
parameter_list|)
block|{
name|this
operator|.
name|subjectArea
operator|=
name|subjectArea
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: INSTRUCTOR_ID 	 */
specifier|public
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|DepartmentalInstructor
name|getInstructor
parameter_list|()
block|{
return|return
name|instructor
return|;
block|}
comment|/** 	 * Set the value related to the column: INSTRUCTOR_ID 	 * @param instructor the INSTRUCTOR_ID value 	 */
specifier|public
name|void
name|setInstructor
parameter_list|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|DepartmentalInstructor
name|instructor
parameter_list|)
block|{
name|this
operator|.
name|instructor
operator|=
name|instructor
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
name|Designator
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
name|Designator
name|designator
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
name|Designator
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
name|designator
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
name|designator
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

