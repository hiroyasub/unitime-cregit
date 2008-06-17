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
comment|/**  * This is an object that contains data related to the DISTRIBUTION_TYPE table.  * Do not modify this class because it will be overwritten if the configuration file  * related to this class is modified.  *  * @hibernate.class  *  table="DISTRIBUTION_TYPE"  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|BaseDistributionType
extends|extends
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|RefTableEntry
implements|implements
name|Serializable
block|{
specifier|public
specifier|static
name|String
name|REF
init|=
literal|"DistributionType"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_SEQUENCING_REQUIRED
init|=
literal|"sequencingRequired"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_REQUIREMENT_ID
init|=
literal|"requirementId"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_ALLOWED_PREF
init|=
literal|"allowedPref"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_DESCR
init|=
literal|"descr"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_ABBREVIATION
init|=
literal|"abbreviation"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_INSTRUCTOR_PREF
init|=
literal|"instructorPref"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_EXAM_PREF
init|=
literal|"examPref"
decl_stmt|;
comment|// constructors
specifier|public
name|BaseDistributionType
parameter_list|()
block|{
name|initialize
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|BaseDistributionType
parameter_list|(
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
name|BaseDistributionType
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
name|super
argument_list|(
name|uniqueId
argument_list|,
name|reference
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
comment|// fields
specifier|private
name|boolean
name|sequencingRequired
decl_stmt|;
specifier|private
name|java
operator|.
name|lang
operator|.
name|Integer
name|requirementId
decl_stmt|;
specifier|private
name|java
operator|.
name|lang
operator|.
name|String
name|allowedPref
decl_stmt|;
specifier|private
name|java
operator|.
name|lang
operator|.
name|String
name|descr
decl_stmt|;
specifier|private
name|java
operator|.
name|lang
operator|.
name|String
name|abbreviation
decl_stmt|;
specifier|private
name|java
operator|.
name|lang
operator|.
name|Boolean
name|instructorPref
decl_stmt|;
specifier|private
name|java
operator|.
name|lang
operator|.
name|Boolean
name|examPref
decl_stmt|;
comment|// collections
specifier|private
name|java
operator|.
name|util
operator|.
name|Set
name|departments
decl_stmt|;
comment|/** 	 * Return the value associated with the column: SEQUENCING_REQUIRED 	 */
specifier|public
name|boolean
name|isSequencingRequired
parameter_list|()
block|{
return|return
name|sequencingRequired
return|;
block|}
comment|/** 	 * Set the value related to the column: SEQUENCING_REQUIRED 	 * @param sequencingRequired the SEQUENCING_REQUIRED value 	 */
specifier|public
name|void
name|setSequencingRequired
parameter_list|(
name|boolean
name|sequencingRequired
parameter_list|)
block|{
name|this
operator|.
name|sequencingRequired
operator|=
name|sequencingRequired
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: REQ_ID 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|Integer
name|getRequirementId
parameter_list|()
block|{
return|return
name|requirementId
return|;
block|}
comment|/** 	 * Set the value related to the column: REQ_ID 	 * @param requirementId the REQ_ID value 	 */
specifier|public
name|void
name|setRequirementId
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Integer
name|requirementId
parameter_list|)
block|{
name|this
operator|.
name|requirementId
operator|=
name|requirementId
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: ALLOWED_PREF 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|String
name|getAllowedPref
parameter_list|()
block|{
return|return
name|allowedPref
return|;
block|}
comment|/** 	 * Set the value related to the column: ALLOWED_PREF 	 * @param allowedPref the ALLOWED_PREF value 	 */
specifier|public
name|void
name|setAllowedPref
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|String
name|allowedPref
parameter_list|)
block|{
name|this
operator|.
name|allowedPref
operator|=
name|allowedPref
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: DESCRIPTION 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|String
name|getDescr
parameter_list|()
block|{
return|return
name|descr
return|;
block|}
comment|/** 	 * Set the value related to the column: DESCRIPTION 	 * @param descr the DESCRIPTION value 	 */
specifier|public
name|void
name|setDescr
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|String
name|descr
parameter_list|)
block|{
name|this
operator|.
name|descr
operator|=
name|descr
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: ABBREVIATION 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|String
name|getAbbreviation
parameter_list|()
block|{
return|return
name|abbreviation
return|;
block|}
comment|/** 	 * Set the value related to the column: ABBREVIATION 	 * @param abbreviation the ABBREVIATION value 	 */
specifier|public
name|void
name|setAbbreviation
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|String
name|abbreviation
parameter_list|)
block|{
name|this
operator|.
name|abbreviation
operator|=
name|abbreviation
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: INSTRUCTOR_PREF 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|Boolean
name|isInstructorPref
parameter_list|()
block|{
return|return
name|instructorPref
return|;
block|}
comment|/** 	 * Set the value related to the column: INSTRUCTOR_PREF 	 * @param instructorPref the INSTRUCTOR_PREF value 	 */
specifier|public
name|void
name|setInstructorPref
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Boolean
name|instructorPref
parameter_list|)
block|{
name|this
operator|.
name|instructorPref
operator|=
name|instructorPref
expr_stmt|;
block|}
specifier|public
name|java
operator|.
name|lang
operator|.
name|Boolean
name|isExamPref
parameter_list|()
block|{
return|return
name|examPref
return|;
block|}
specifier|public
name|void
name|setExamPref
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Boolean
name|examPref
parameter_list|)
block|{
name|this
operator|.
name|examPref
operator|=
name|examPref
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: departments 	 */
specifier|public
name|java
operator|.
name|util
operator|.
name|Set
name|getDepartments
parameter_list|()
block|{
return|return
name|departments
return|;
block|}
comment|/** 	 * Set the value related to the column: departments 	 * @param departments the departments value 	 */
specifier|public
name|void
name|setDepartments
parameter_list|(
name|java
operator|.
name|util
operator|.
name|Set
name|departments
parameter_list|)
block|{
name|this
operator|.
name|departments
operator|=
name|departments
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
name|DistributionType
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
name|DistributionType
name|distributionType
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
name|DistributionType
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
name|distributionType
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
name|distributionType
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

