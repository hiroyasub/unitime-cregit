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
name|form
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Iterator
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Vector
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|http
operator|.
name|HttpServletRequest
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionMapping
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
name|Department
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
name|DistributionType
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
name|RefTableEntry
import|;
end_import

begin_comment
comment|/**   * MyEclipse Struts  * Creation date: 02-18-2005  *   * XDoclet definition:  * @struts:form name="distributionTypeEditForm"  */
end_comment

begin_class
specifier|public
class|class
name|DistributionTypeEditForm
extends|extends
name|RefTableEntryEditForm
block|{
name|String
name|iOp
init|=
literal|null
decl_stmt|;
specifier|private
name|Vector
name|iDepartmentIds
init|=
operator|new
name|Vector
argument_list|()
decl_stmt|;
specifier|private
name|Long
name|iDepartmentId
decl_stmt|;
comment|/**      * Comment for<code>serialVersionUID</code>      */
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|3252210646873060656L
decl_stmt|;
comment|// --------------------------------------------------------- Instance Variables
specifier|public
name|DistributionTypeEditForm
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
name|refTableEntry
operator|=
operator|new
name|DistributionType
argument_list|()
expr_stmt|;
block|}
comment|// --------------------------------------------------------- Methods
specifier|public
name|void
name|reset
parameter_list|(
name|ActionMapping
name|mapping
parameter_list|,
name|HttpServletRequest
name|request
parameter_list|)
block|{
name|iOp
operator|=
literal|null
expr_stmt|;
name|refTableEntry
operator|=
operator|new
name|DistributionType
argument_list|()
expr_stmt|;
name|iDepartmentId
operator|=
literal|null
expr_stmt|;
name|iDepartmentIds
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
specifier|public
name|void
name|setRefTableEntry
parameter_list|(
name|RefTableEntry
name|refTableEntry
parameter_list|,
name|Long
name|sessionId
parameter_list|)
block|{
name|super
operator|.
name|setRefTableEntry
argument_list|(
name|refTableEntry
argument_list|)
expr_stmt|;
name|DistributionType
name|distType
init|=
operator|(
name|DistributionType
operator|)
name|refTableEntry
decl_stmt|;
name|iDepartmentIds
operator|.
name|clear
argument_list|()
expr_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|distType
operator|.
name|getDepartments
argument_list|(
name|sessionId
argument_list|)
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Department
name|d
init|=
operator|(
name|Department
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|iDepartmentIds
operator|.
name|add
argument_list|(
name|d
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|void
name|setSequencingRequired
parameter_list|(
name|boolean
name|sequencingRequired
parameter_list|)
block|{
operator|(
operator|(
name|DistributionType
operator|)
name|refTableEntry
operator|)
operator|.
name|setSequencingRequired
argument_list|(
name|sequencingRequired
argument_list|)
expr_stmt|;
block|}
specifier|public
name|boolean
name|isSequencingRequired
parameter_list|()
block|{
return|return
operator|(
operator|(
operator|(
name|DistributionType
operator|)
name|refTableEntry
operator|)
operator|.
name|isSequencingRequired
argument_list|()
operator|)
return|;
block|}
specifier|public
name|void
name|setRequirementId
parameter_list|(
name|String
name|requirementId
parameter_list|)
block|{
name|Integer
name|reqId
init|=
name|Integer
operator|.
name|valueOf
argument_list|(
name|requirementId
argument_list|)
decl_stmt|;
operator|(
operator|(
name|DistributionType
operator|)
name|refTableEntry
operator|)
operator|.
name|setRequirementId
argument_list|(
name|reqId
argument_list|)
expr_stmt|;
block|}
specifier|public
name|String
name|getRequirementId
parameter_list|()
block|{
if|if
condition|(
operator|(
operator|(
name|DistributionType
operator|)
name|refTableEntry
operator|)
operator|.
name|getRequirementId
argument_list|()
operator|==
literal|null
condition|)
block|{
return|return
operator|(
literal|""
operator|)
return|;
block|}
else|else
block|{
return|return
operator|(
operator|(
operator|(
name|DistributionType
operator|)
name|refTableEntry
operator|)
operator|.
name|getRequirementId
argument_list|()
operator|.
name|toString
argument_list|()
operator|)
return|;
block|}
block|}
specifier|public
name|String
name|getOp
parameter_list|()
block|{
return|return
name|iOp
return|;
block|}
specifier|public
name|void
name|setOp
parameter_list|(
name|String
name|op
parameter_list|)
block|{
name|iOp
operator|=
name|op
expr_stmt|;
block|}
specifier|public
name|String
name|getAbbreviation
parameter_list|()
block|{
return|return
operator|(
operator|(
name|DistributionType
operator|)
name|refTableEntry
operator|)
operator|.
name|getAbbreviation
argument_list|()
return|;
block|}
specifier|public
name|void
name|setAbbreviation
parameter_list|(
name|String
name|abbreviation
parameter_list|)
block|{
operator|(
operator|(
name|DistributionType
operator|)
name|refTableEntry
operator|)
operator|.
name|setAbbreviation
argument_list|(
name|abbreviation
argument_list|)
expr_stmt|;
block|}
specifier|public
name|boolean
name|isInstructorPref
parameter_list|()
block|{
return|return
operator|(
operator|(
operator|(
name|DistributionType
operator|)
name|refTableEntry
operator|)
operator|.
name|isInstructorPref
argument_list|()
operator|==
literal|null
condition|?
literal|false
else|:
operator|(
operator|(
name|DistributionType
operator|)
name|refTableEntry
operator|)
operator|.
name|isInstructorPref
argument_list|()
operator|.
name|booleanValue
argument_list|()
operator|)
return|;
block|}
specifier|public
name|void
name|setInstructorPref
parameter_list|(
name|boolean
name|instructorPref
parameter_list|)
block|{
operator|(
operator|(
name|DistributionType
operator|)
name|refTableEntry
operator|)
operator|.
name|setInstructorPref
argument_list|(
operator|new
name|Boolean
argument_list|(
name|instructorPref
argument_list|)
argument_list|)
expr_stmt|;
block|}
specifier|public
name|boolean
name|isExamPref
parameter_list|()
block|{
return|return
operator|(
operator|(
operator|(
name|DistributionType
operator|)
name|refTableEntry
operator|)
operator|.
name|isExamPref
argument_list|()
operator|==
literal|null
condition|?
literal|false
else|:
operator|(
operator|(
name|DistributionType
operator|)
name|refTableEntry
operator|)
operator|.
name|isExamPref
argument_list|()
operator|.
name|booleanValue
argument_list|()
operator|)
return|;
block|}
specifier|public
name|void
name|setExamPref
parameter_list|(
name|boolean
name|examPref
parameter_list|)
block|{
operator|(
operator|(
name|DistributionType
operator|)
name|refTableEntry
operator|)
operator|.
name|setExamPref
argument_list|(
operator|new
name|Boolean
argument_list|(
name|examPref
argument_list|)
argument_list|)
expr_stmt|;
block|}
specifier|public
name|String
name|getAllowedPref
parameter_list|()
block|{
return|return
operator|(
operator|(
name|DistributionType
operator|)
name|refTableEntry
operator|)
operator|.
name|getAllowedPref
argument_list|()
return|;
block|}
specifier|public
name|void
name|setAllowedPref
parameter_list|(
name|String
name|allowedPref
parameter_list|)
block|{
operator|(
operator|(
name|DistributionType
operator|)
name|refTableEntry
operator|)
operator|.
name|setAllowedPref
argument_list|(
name|allowedPref
argument_list|)
expr_stmt|;
block|}
specifier|public
name|String
name|getDescription
parameter_list|()
block|{
return|return
operator|(
operator|(
name|DistributionType
operator|)
name|refTableEntry
operator|)
operator|.
name|getDescr
argument_list|()
return|;
block|}
specifier|public
name|void
name|setDescription
parameter_list|(
name|String
name|description
parameter_list|)
block|{
operator|(
operator|(
name|DistributionType
operator|)
name|refTableEntry
operator|)
operator|.
name|setDescr
argument_list|(
name|description
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Vector
name|getDepartmentIds
parameter_list|()
block|{
return|return
name|iDepartmentIds
return|;
block|}
specifier|public
name|void
name|setDepartmentIds
parameter_list|(
name|Vector
name|departmentIds
parameter_list|)
block|{
name|iDepartmentIds
operator|=
name|departmentIds
expr_stmt|;
block|}
specifier|public
name|Long
name|getDepartmentId
parameter_list|()
block|{
return|return
name|iDepartmentId
return|;
block|}
specifier|public
name|void
name|setDepartmentId
parameter_list|(
name|Long
name|deptId
parameter_list|)
block|{
name|iDepartmentId
operator|=
name|deptId
expr_stmt|;
block|}
block|}
end_class

end_unit

