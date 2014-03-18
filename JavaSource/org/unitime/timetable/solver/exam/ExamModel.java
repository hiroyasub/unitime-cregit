begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 - 3.5 (University Timetabling Application)  * Copyright (C) 2008 - 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|solver
operator|.
name|exam
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashSet
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Hashtable
import|;
end_import

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
name|Set
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
name|org
operator|.
name|cpsolver
operator|.
name|exam
operator|.
name|model
operator|.
name|Exam
import|;
end_import

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|exam
operator|.
name|model
operator|.
name|ExamPeriod
import|;
end_import

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|exam
operator|.
name|model
operator|.
name|ExamPlacement
import|;
end_import

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|ifs
operator|.
name|assignment
operator|.
name|Assignment
import|;
end_import

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|ifs
operator|.
name|util
operator|.
name|Callback
import|;
end_import

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|ifs
operator|.
name|util
operator|.
name|DataProperties
import|;
end_import

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|ifs
operator|.
name|util
operator|.
name|Progress
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

begin_import
import|import
name|org
operator|.
name|dom4j
operator|.
name|Element
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|ExamModel
extends|extends
name|org
operator|.
name|cpsolver
operator|.
name|exam
operator|.
name|model
operator|.
name|ExamModel
block|{
specifier|private
name|Hashtable
argument_list|<
name|ExamPeriod
argument_list|,
name|Vector
argument_list|<
name|ExamResourceUnavailability
argument_list|>
argument_list|>
name|iUnavailabilitites
init|=
literal|null
decl_stmt|;
specifier|public
name|ExamModel
parameter_list|(
name|DataProperties
name|properties
parameter_list|)
block|{
name|super
argument_list|(
name|properties
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Vector
argument_list|<
name|ExamResourceUnavailability
argument_list|>
name|getUnavailabilities
parameter_list|(
name|ExamPeriod
name|period
parameter_list|)
block|{
return|return
operator|(
name|iUnavailabilitites
operator|==
literal|null
condition|?
literal|null
else|:
name|iUnavailabilitites
operator|.
name|get
argument_list|(
name|period
argument_list|)
operator|)
return|;
block|}
specifier|public
name|void
name|addUnavailability
parameter_list|(
name|ExamResourceUnavailability
name|unavailability
parameter_list|)
block|{
if|if
condition|(
name|unavailability
operator|.
name|getStudentIds
argument_list|()
operator|.
name|isEmpty
argument_list|()
operator|&&
name|unavailability
operator|.
name|getInstructorIds
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
return|return;
if|if
condition|(
name|iUnavailabilitites
operator|==
literal|null
condition|)
name|iUnavailabilitites
operator|=
operator|new
name|Hashtable
argument_list|()
expr_stmt|;
name|Vector
argument_list|<
name|ExamResourceUnavailability
argument_list|>
name|unavailabilities
init|=
name|iUnavailabilitites
operator|.
name|get
argument_list|(
name|unavailability
operator|.
name|getPeriod
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|unavailabilities
operator|==
literal|null
condition|)
block|{
name|unavailabilities
operator|=
operator|new
name|Vector
argument_list|<
name|ExamResourceUnavailability
argument_list|>
argument_list|()
expr_stmt|;
name|iUnavailabilitites
operator|.
name|put
argument_list|(
name|unavailability
operator|.
name|getPeriod
argument_list|()
argument_list|,
name|unavailabilities
argument_list|)
expr_stmt|;
block|}
name|unavailabilities
operator|.
name|add
argument_list|(
name|unavailability
argument_list|)
expr_stmt|;
block|}
specifier|public
name|boolean
name|load
parameter_list|(
name|Document
name|document
parameter_list|,
name|Assignment
argument_list|<
name|Exam
argument_list|,
name|ExamPlacement
argument_list|>
name|assignment
parameter_list|)
block|{
return|return
name|load
argument_list|(
name|document
argument_list|,
name|assignment
argument_list|,
literal|null
argument_list|)
return|;
block|}
specifier|public
name|boolean
name|load
parameter_list|(
name|Document
name|document
parameter_list|,
name|Assignment
argument_list|<
name|Exam
argument_list|,
name|ExamPlacement
argument_list|>
name|assignment
parameter_list|,
name|Callback
name|saveBest
parameter_list|)
block|{
if|if
condition|(
operator|!
name|super
operator|.
name|load
argument_list|(
name|document
argument_list|,
name|assignment
argument_list|,
name|saveBest
argument_list|)
condition|)
return|return
literal|false
return|;
if|if
condition|(
name|iUnavailabilitites
operator|!=
literal|null
condition|)
name|iUnavailabilitites
operator|.
name|clear
argument_list|()
expr_stmt|;
name|Element
name|elements
init|=
name|document
operator|.
name|getRootElement
argument_list|()
operator|.
name|element
argument_list|(
literal|"notavailable"
argument_list|)
decl_stmt|;
if|if
condition|(
name|elements
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|Iterator
name|i
init|=
name|elements
operator|.
name|elementIterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Element
name|element
init|=
operator|(
name|Element
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|Set
argument_list|<
name|Long
argument_list|>
name|studentIds
init|=
operator|new
name|HashSet
argument_list|()
decl_stmt|;
name|Set
argument_list|<
name|Long
argument_list|>
name|instructorIds
init|=
operator|new
name|HashSet
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|j
init|=
name|element
operator|.
name|elementIterator
argument_list|(
literal|"student"
argument_list|)
init|;
name|j
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Element
name|e
init|=
operator|(
name|Element
operator|)
name|j
operator|.
name|next
argument_list|()
decl_stmt|;
name|studentIds
operator|.
name|add
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|e
operator|.
name|attributeValue
argument_list|(
literal|"id"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|Iterator
name|j
init|=
name|element
operator|.
name|elementIterator
argument_list|(
literal|"instructor"
argument_list|)
init|;
name|j
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Element
name|e
init|=
operator|(
name|Element
operator|)
name|j
operator|.
name|next
argument_list|()
decl_stmt|;
name|instructorIds
operator|.
name|add
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|e
operator|.
name|attributeValue
argument_list|(
literal|"id"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|addUnavailability
argument_list|(
operator|new
name|ExamResourceUnavailability
argument_list|(
name|getPeriod
argument_list|(
name|Long
operator|.
name|parseLong
argument_list|(
name|element
operator|.
name|attributeValue
argument_list|(
literal|"period"
argument_list|)
argument_list|)
argument_list|)
argument_list|,
name|Long
operator|.
name|valueOf
argument_list|(
name|element
operator|.
name|attributeValue
argument_list|(
literal|"id"
argument_list|)
argument_list|)
argument_list|,
name|element
operator|.
name|getName
argument_list|()
argument_list|,
name|element
operator|.
name|attributeValue
argument_list|(
literal|"name"
argument_list|,
literal|""
argument_list|)
argument_list|,
name|element
operator|.
name|attributeValue
argument_list|(
literal|"date"
argument_list|,
literal|""
argument_list|)
argument_list|,
name|element
operator|.
name|attributeValue
argument_list|(
literal|"time"
argument_list|,
literal|""
argument_list|)
argument_list|,
name|element
operator|.
name|attributeValue
argument_list|(
literal|"room"
argument_list|,
literal|""
argument_list|)
argument_list|,
name|Integer
operator|.
name|parseInt
argument_list|(
name|element
operator|.
name|attributeValue
argument_list|(
literal|"size"
argument_list|,
literal|"0"
argument_list|)
argument_list|)
argument_list|,
name|studentIds
argument_list|,
name|instructorIds
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
name|Progress
name|p
init|=
name|Progress
operator|.
name|getInstance
argument_list|(
name|this
argument_list|)
decl_stmt|;
if|if
condition|(
name|p
operator|!=
literal|null
condition|)
block|{
name|p
operator|.
name|load
argument_list|(
name|document
operator|.
name|getRootElement
argument_list|()
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|p
operator|.
name|message
argument_list|(
name|Progress
operator|.
name|MSGLEVEL_STAGE
argument_list|,
literal|"Restoring from backup ..."
argument_list|)
expr_stmt|;
block|}
return|return
literal|true
return|;
block|}
specifier|public
name|Document
name|save
parameter_list|(
name|Assignment
argument_list|<
name|Exam
argument_list|,
name|ExamPlacement
argument_list|>
name|assignment
parameter_list|)
block|{
name|Document
name|document
init|=
name|super
operator|.
name|save
argument_list|(
name|assignment
argument_list|)
decl_stmt|;
if|if
condition|(
name|document
operator|==
literal|null
condition|)
return|return
literal|null
return|;
if|if
condition|(
name|iUnavailabilitites
operator|!=
literal|null
condition|)
block|{
name|Element
name|elements
init|=
name|document
operator|.
name|getRootElement
argument_list|()
operator|.
name|addElement
argument_list|(
literal|"notavailable"
argument_list|)
decl_stmt|;
for|for
control|(
name|Vector
argument_list|<
name|ExamResourceUnavailability
argument_list|>
name|unavailabilties
range|:
name|iUnavailabilitites
operator|.
name|values
argument_list|()
control|)
block|{
for|for
control|(
name|ExamResourceUnavailability
name|unavailability
range|:
name|unavailabilties
control|)
block|{
name|Element
name|element
init|=
name|elements
operator|.
name|addElement
argument_list|(
name|unavailability
operator|.
name|getType
argument_list|()
argument_list|)
operator|.
name|addAttribute
argument_list|(
literal|"period"
argument_list|,
name|unavailability
operator|.
name|getPeriod
argument_list|()
operator|.
name|getId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
operator|.
name|addAttribute
argument_list|(
literal|"id"
argument_list|,
name|unavailability
operator|.
name|getId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
operator|.
name|addAttribute
argument_list|(
literal|"name"
argument_list|,
name|unavailability
operator|.
name|getName
argument_list|()
argument_list|)
operator|.
name|addAttribute
argument_list|(
literal|"date"
argument_list|,
name|unavailability
operator|.
name|getDate
argument_list|()
argument_list|)
operator|.
name|addAttribute
argument_list|(
literal|"time"
argument_list|,
name|unavailability
operator|.
name|getTime
argument_list|()
argument_list|)
operator|.
name|addAttribute
argument_list|(
literal|"room"
argument_list|,
name|unavailability
operator|.
name|getRoom
argument_list|()
argument_list|)
operator|.
name|addAttribute
argument_list|(
literal|"size"
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|unavailability
operator|.
name|getSize
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
for|for
control|(
name|Long
name|studentId
range|:
name|unavailability
operator|.
name|getStudentIds
argument_list|()
control|)
name|element
operator|.
name|addElement
argument_list|(
literal|"student"
argument_list|)
operator|.
name|addAttribute
argument_list|(
literal|"id"
argument_list|,
name|studentId
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|Long
name|studentId
range|:
name|unavailability
operator|.
name|getInstructorIds
argument_list|()
control|)
name|element
operator|.
name|addElement
argument_list|(
literal|"instructor"
argument_list|)
operator|.
name|addAttribute
argument_list|(
literal|"id"
argument_list|,
name|studentId
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
name|Progress
name|p
init|=
name|Progress
operator|.
name|getInstance
argument_list|(
name|this
argument_list|)
decl_stmt|;
if|if
condition|(
name|p
operator|!=
literal|null
condition|)
block|{
name|Progress
operator|.
name|getInstance
argument_list|(
name|this
argument_list|)
operator|.
name|save
argument_list|(
name|document
operator|.
name|getRootElement
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|document
return|;
block|}
block|}
end_class

end_unit

