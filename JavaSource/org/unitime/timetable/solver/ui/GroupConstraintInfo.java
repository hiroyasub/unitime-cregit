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
name|solver
operator|.
name|ui
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
name|cpsolver
operator|.
name|coursett
operator|.
name|constraint
operator|.
name|FlexibleConstraint
import|;
end_import

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|coursett
operator|.
name|constraint
operator|.
name|GroupConstraint
import|;
end_import

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|coursett
operator|.
name|model
operator|.
name|Lecture
import|;
end_import

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|coursett
operator|.
name|model
operator|.
name|Placement
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
name|dom4j
operator|.
name|Element
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
name|util
operator|.
name|Constants
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
name|util
operator|.
name|Formats
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|GroupConstraintInfo
implements|implements
name|TimetableInfo
implements|,
name|Serializable
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
specifier|public
specifier|static
name|int
name|sVersion
init|=
literal|1
decl_stmt|;
comment|// to be able to do some changes in the future
specifier|public
name|String
name|iPreference
init|=
literal|"0"
decl_stmt|;
specifier|public
name|boolean
name|iIsSatisfied
init|=
literal|false
decl_stmt|;
specifier|public
name|String
name|iName
init|=
literal|null
decl_stmt|;
specifier|public
name|String
name|iType
init|=
literal|null
decl_stmt|;
specifier|public
name|GroupConstraintInfo
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
specifier|public
name|GroupConstraintInfo
parameter_list|(
name|Assignment
argument_list|<
name|Lecture
argument_list|,
name|Placement
argument_list|>
name|assignment
parameter_list|,
name|GroupConstraint
name|gc
parameter_list|)
block|{
name|super
argument_list|()
expr_stmt|;
name|setPreference
argument_list|(
name|gc
operator|.
name|getPrologPreference
argument_list|()
argument_list|)
expr_stmt|;
name|setIsSatisfied
argument_list|(
name|gc
operator|.
name|isSatisfied
argument_list|(
name|assignment
argument_list|)
argument_list|)
expr_stmt|;
name|setName
argument_list|(
name|gc
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
name|GroupConstraintInfo
parameter_list|(
name|Assignment
argument_list|<
name|Lecture
argument_list|,
name|Placement
argument_list|>
name|assignment
parameter_list|,
name|FlexibleConstraint
name|gc
parameter_list|)
block|{
name|super
argument_list|()
expr_stmt|;
name|setPreference
argument_list|(
name|gc
operator|.
name|getPrologPreference
argument_list|()
argument_list|)
expr_stmt|;
name|double
name|preference
init|=
name|gc
operator|.
name|getContext
argument_list|(
name|assignment
argument_list|)
operator|.
name|getPreference
argument_list|()
decl_stmt|;
name|setIsSatisfied
argument_list|(
name|gc
operator|.
name|isHard
argument_list|()
operator|||
name|preference
operator|<=
literal|0.0
argument_list|)
expr_stmt|;
name|setName
argument_list|(
name|gc
operator|.
name|getName
argument_list|()
operator|+
operator|(
name|preference
operator|<=
literal|0.0
condition|?
literal|""
else|:
literal|" (penalty: "
operator|+
name|Formats
operator|.
name|getNumberFormat
argument_list|(
literal|"0.###"
argument_list|)
operator|.
name|format
argument_list|(
name|preference
operator|/
name|Math
operator|.
name|abs
argument_list|(
name|Constants
operator|.
name|preference2preferenceLevel
argument_list|(
name|gc
operator|.
name|getPrologPreference
argument_list|()
argument_list|)
argument_list|)
argument_list|)
operator|+
literal|")"
operator|)
argument_list|)
expr_stmt|;
block|}
specifier|public
name|String
name|getPreference
parameter_list|()
block|{
return|return
name|iPreference
return|;
block|}
specifier|public
name|void
name|setPreference
parameter_list|(
name|String
name|preference
parameter_list|)
block|{
name|iPreference
operator|=
name|preference
expr_stmt|;
block|}
specifier|public
name|boolean
name|isSatisfied
parameter_list|()
block|{
return|return
name|iIsSatisfied
return|;
block|}
specifier|public
name|void
name|setIsSatisfied
parameter_list|(
name|boolean
name|isSatisfied
parameter_list|)
block|{
name|iIsSatisfied
operator|=
name|isSatisfied
expr_stmt|;
block|}
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|iName
return|;
block|}
specifier|public
name|void
name|setName
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|iName
operator|=
name|name
expr_stmt|;
block|}
specifier|public
name|String
name|getType
parameter_list|()
block|{
return|return
name|iType
return|;
block|}
specifier|public
name|void
name|setType
parameter_list|(
name|String
name|type
parameter_list|)
block|{
name|iType
operator|=
name|type
expr_stmt|;
block|}
specifier|public
name|void
name|load
parameter_list|(
name|Element
name|root
parameter_list|)
block|{
name|int
name|version
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|root
operator|.
name|attributeValue
argument_list|(
literal|"version"
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|version
operator|==
literal|1
condition|)
block|{
if|if
condition|(
name|root
operator|.
name|element
argument_list|(
literal|"name"
argument_list|)
operator|!=
literal|null
condition|)
name|iName
operator|=
name|root
operator|.
name|element
argument_list|(
literal|"name"
argument_list|)
operator|.
name|getText
argument_list|()
expr_stmt|;
name|iPreference
operator|=
name|root
operator|.
name|element
argument_list|(
literal|"pref"
argument_list|)
operator|.
name|getText
argument_list|()
expr_stmt|;
name|iIsSatisfied
operator|=
name|Boolean
operator|.
name|valueOf
argument_list|(
name|root
operator|.
name|element
argument_list|(
literal|"isSatisfied"
argument_list|)
operator|.
name|getText
argument_list|()
argument_list|)
operator|.
name|booleanValue
argument_list|()
expr_stmt|;
name|iType
operator|=
name|root
operator|.
name|elementText
argument_list|(
literal|"type"
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|void
name|save
parameter_list|(
name|Element
name|root
parameter_list|)
block|{
name|root
operator|.
name|addAttribute
argument_list|(
literal|"version"
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|sVersion
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|iName
operator|!=
literal|null
condition|)
name|root
operator|.
name|addElement
argument_list|(
literal|"name"
argument_list|)
operator|.
name|setText
argument_list|(
name|iName
argument_list|)
expr_stmt|;
name|root
operator|.
name|addElement
argument_list|(
literal|"pref"
argument_list|)
operator|.
name|setText
argument_list|(
name|iPreference
argument_list|)
expr_stmt|;
if|if
condition|(
name|iType
operator|!=
literal|null
condition|)
name|root
operator|.
name|addElement
argument_list|(
literal|"type"
argument_list|)
operator|.
name|setText
argument_list|(
name|iType
argument_list|)
expr_stmt|;
name|root
operator|.
name|addElement
argument_list|(
literal|"isSatisfied"
argument_list|)
operator|.
name|setText
argument_list|(
name|String
operator|.
name|valueOf
argument_list|(
name|iIsSatisfied
argument_list|)
argument_list|)
expr_stmt|;
block|}
specifier|public
name|boolean
name|saveToFile
parameter_list|()
block|{
return|return
literal|false
return|;
block|}
block|}
end_class

end_unit

