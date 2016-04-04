begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to The Apereo Foundation under one or more contributor license  * agreements. See the NOTICE file distributed with this work for  * additional information regarding copyright ownership.  *  * The Apereo Foundation licenses this file to you under the Apache License,  * Version 2.0 (the "License"); you may not use this file except in  * compliance with the License. You may obtain a copy of the License at:  *  * http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *  * See the License for the specific language governing permissions and  * limitations under the License.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|gwt
operator|.
name|client
operator|.
name|instructor
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Comparator
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
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|gwt
operator|.
name|shared
operator|.
name|InstructorInterface
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
name|gwt
operator|.
name|shared
operator|.
name|InstructorInterface
operator|.
name|AttributeInterface
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
name|gwt
operator|.
name|shared
operator|.
name|InstructorInterface
operator|.
name|AttributesColumn
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|InstructorAttributesComparator
implements|implements
name|Comparator
argument_list|<
name|AttributeInterface
argument_list|>
block|{
specifier|private
name|AttributesColumn
name|iColumn
decl_stmt|;
specifier|private
name|boolean
name|iAsc
decl_stmt|;
specifier|public
name|InstructorAttributesComparator
parameter_list|(
name|AttributesColumn
name|column
parameter_list|,
name|boolean
name|asc
parameter_list|)
block|{
name|iColumn
operator|=
name|column
expr_stmt|;
name|iAsc
operator|=
name|asc
expr_stmt|;
block|}
specifier|public
name|int
name|compareById
parameter_list|(
name|AttributeInterface
name|r1
parameter_list|,
name|AttributeInterface
name|r2
parameter_list|)
block|{
return|return
name|compare
argument_list|(
name|r1
operator|.
name|getId
argument_list|()
argument_list|,
name|r2
operator|.
name|getId
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|int
name|compareByName
parameter_list|(
name|AttributeInterface
name|r1
parameter_list|,
name|AttributeInterface
name|r2
parameter_list|)
block|{
return|return
name|compare
argument_list|(
name|r1
operator|.
name|getName
argument_list|()
argument_list|,
name|r2
operator|.
name|getName
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|int
name|compareByCode
parameter_list|(
name|AttributeInterface
name|r1
parameter_list|,
name|AttributeInterface
name|r2
parameter_list|)
block|{
return|return
name|compare
argument_list|(
name|r1
operator|.
name|getCode
argument_list|()
argument_list|,
name|r2
operator|.
name|getCode
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|int
name|compareByParent
parameter_list|(
name|AttributeInterface
name|r1
parameter_list|,
name|AttributeInterface
name|r2
parameter_list|)
block|{
return|return
name|compare
argument_list|(
name|r1
operator|.
name|getParentName
argument_list|()
argument_list|,
name|r2
operator|.
name|getParentName
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|int
name|compareByType
parameter_list|(
name|AttributeInterface
name|r1
parameter_list|,
name|AttributeInterface
name|r2
parameter_list|)
block|{
return|return
name|compare
argument_list|(
name|r1
operator|.
name|getType
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
name|r1
operator|.
name|getType
argument_list|()
operator|.
name|getAbbreviation
argument_list|()
argument_list|,
name|r2
operator|.
name|getType
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
name|r2
operator|.
name|getType
argument_list|()
operator|.
name|getAbbreviation
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|int
name|compareByInstructors
parameter_list|(
name|AttributeInterface
name|r1
parameter_list|,
name|AttributeInterface
name|r2
parameter_list|)
block|{
if|if
condition|(
name|r1
operator|.
name|hasInstructors
argument_list|()
condition|)
block|{
if|if
condition|(
name|r2
operator|.
name|hasInstructors
argument_list|()
condition|)
block|{
name|Iterator
argument_list|<
name|InstructorInterface
argument_list|>
name|i1
init|=
name|r1
operator|.
name|getInstructors
argument_list|()
operator|.
name|iterator
argument_list|()
decl_stmt|;
name|Iterator
argument_list|<
name|InstructorInterface
argument_list|>
name|i2
init|=
name|r2
operator|.
name|getInstructors
argument_list|()
operator|.
name|iterator
argument_list|()
decl_stmt|;
while|while
condition|(
name|i1
operator|.
name|hasNext
argument_list|()
operator|&&
name|i2
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|int
name|cmp
init|=
name|compare
argument_list|(
name|i1
operator|.
name|next
argument_list|()
operator|.
name|getOrderName
argument_list|()
argument_list|,
name|i2
operator|.
name|next
argument_list|()
operator|.
name|getOrderName
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|cmp
operator|!=
literal|0
condition|)
return|return
name|cmp
return|;
block|}
if|if
condition|(
name|i1
operator|.
name|hasNext
argument_list|()
condition|)
block|{
return|return
name|i2
operator|.
name|hasNext
argument_list|()
condition|?
literal|0
else|:
literal|1
return|;
block|}
else|else
block|{
return|return
name|i2
operator|.
name|hasNext
argument_list|()
condition|?
operator|-
literal|1
else|:
literal|0
return|;
block|}
block|}
else|else
block|{
return|return
literal|1
return|;
block|}
block|}
else|else
block|{
return|return
name|r2
operator|.
name|hasInstructors
argument_list|()
condition|?
operator|-
literal|1
else|:
literal|0
return|;
block|}
block|}
specifier|protected
name|int
name|compareByColumn
parameter_list|(
name|AttributeInterface
name|r1
parameter_list|,
name|AttributeInterface
name|r2
parameter_list|)
block|{
switch|switch
condition|(
name|iColumn
condition|)
block|{
case|case
name|NAME
case|:
return|return
name|compareByName
argument_list|(
name|r1
argument_list|,
name|r2
argument_list|)
return|;
case|case
name|CODE
case|:
return|return
name|compareByCode
argument_list|(
name|r1
argument_list|,
name|r2
argument_list|)
return|;
case|case
name|TYPE
case|:
return|return
name|compareByType
argument_list|(
name|r1
argument_list|,
name|r2
argument_list|)
return|;
case|case
name|PARENT
case|:
return|return
name|compareByParent
argument_list|(
name|r1
argument_list|,
name|r2
argument_list|)
return|;
case|case
name|INSTRUCTORS
case|:
return|return
name|compareByInstructors
argument_list|(
name|r1
argument_list|,
name|r2
argument_list|)
return|;
default|default:
return|return
name|compareByName
argument_list|(
name|r1
argument_list|,
name|r2
argument_list|)
return|;
block|}
block|}
specifier|public
specifier|static
name|boolean
name|isApplicable
parameter_list|(
name|AttributesColumn
name|column
parameter_list|)
block|{
switch|switch
condition|(
name|column
condition|)
block|{
case|case
name|NAME
case|:
case|case
name|CODE
case|:
case|case
name|PARENT
case|:
case|case
name|TYPE
case|:
case|case
name|INSTRUCTORS
case|:
return|return
literal|true
return|;
default|default:
return|return
literal|false
return|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|int
name|compare
parameter_list|(
name|AttributeInterface
name|r1
parameter_list|,
name|AttributeInterface
name|r2
parameter_list|)
block|{
name|int
name|cmp
init|=
name|compareByColumn
argument_list|(
name|r1
argument_list|,
name|r2
argument_list|)
decl_stmt|;
if|if
condition|(
name|cmp
operator|==
literal|0
condition|)
name|cmp
operator|=
name|compareByName
argument_list|(
name|r1
argument_list|,
name|r2
argument_list|)
expr_stmt|;
if|if
condition|(
name|cmp
operator|==
literal|0
condition|)
name|cmp
operator|=
name|compareById
argument_list|(
name|r1
argument_list|,
name|r2
argument_list|)
expr_stmt|;
return|return
operator|(
name|iAsc
condition|?
name|cmp
else|:
operator|-
name|cmp
operator|)
return|;
block|}
specifier|protected
name|int
name|compare
parameter_list|(
name|String
name|s1
parameter_list|,
name|String
name|s2
parameter_list|)
block|{
if|if
condition|(
name|s1
operator|==
literal|null
operator|||
name|s1
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
operator|(
name|s2
operator|==
literal|null
operator|||
name|s2
operator|.
name|isEmpty
argument_list|()
condition|?
literal|0
else|:
literal|1
operator|)
return|;
block|}
else|else
block|{
return|return
operator|(
name|s2
operator|==
literal|null
operator|||
name|s2
operator|.
name|isEmpty
argument_list|()
condition|?
operator|-
literal|1
else|:
name|s1
operator|.
name|compareToIgnoreCase
argument_list|(
name|s2
argument_list|)
operator|)
return|;
block|}
block|}
specifier|protected
name|int
name|compare
parameter_list|(
name|Number
name|n1
parameter_list|,
name|Number
name|n2
parameter_list|)
block|{
return|return
operator|(
name|n1
operator|==
literal|null
condition|?
name|n2
operator|==
literal|null
condition|?
literal|0
else|:
operator|-
literal|1
else|:
name|n2
operator|==
literal|null
condition|?
literal|1
else|:
name|Double
operator|.
name|compare
argument_list|(
name|n1
operator|.
name|doubleValue
argument_list|()
argument_list|,
name|n2
operator|.
name|doubleValue
argument_list|()
argument_list|)
operator|)
return|;
block|}
specifier|protected
name|int
name|compare
parameter_list|(
name|Boolean
name|b1
parameter_list|,
name|Boolean
name|b2
parameter_list|)
block|{
return|return
operator|(
name|b1
operator|==
literal|null
condition|?
name|b2
operator|==
literal|null
condition|?
literal|0
else|:
operator|-
literal|1
else|:
name|b2
operator|==
literal|null
condition|?
literal|1
else|:
operator|(
name|b1
operator|.
name|booleanValue
argument_list|()
operator|==
name|b2
operator|.
name|booleanValue
argument_list|()
operator|)
condition|?
literal|0
else|:
operator|(
name|b1
operator|.
name|booleanValue
argument_list|()
condition|?
literal|1
else|:
operator|-
literal|1
operator|)
operator|)
return|;
block|}
block|}
end_class

end_unit

