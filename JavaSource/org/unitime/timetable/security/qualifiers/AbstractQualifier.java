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
name|security
operator|.
name|qualifiers
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
name|unitime
operator|.
name|timetable
operator|.
name|security
operator|.
name|Qualifiable
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
name|security
operator|.
name|UserQualifier
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|AbstractQualifier
implements|implements
name|UserQualifier
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
specifier|private
name|Serializable
name|iId
decl_stmt|;
specifier|private
name|String
name|iType
decl_stmt|,
name|iReference
decl_stmt|,
name|iLabel
decl_stmt|;
specifier|public
name|AbstractQualifier
parameter_list|(
name|String
name|type
parameter_list|,
name|Serializable
name|id
parameter_list|,
name|String
name|reference
parameter_list|,
name|String
name|label
parameter_list|)
block|{
name|iType
operator|=
name|type
expr_stmt|;
name|iId
operator|=
name|id
expr_stmt|;
name|iReference
operator|=
name|reference
expr_stmt|;
name|iLabel
operator|=
name|label
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getQualifierType
parameter_list|()
block|{
return|return
name|iType
return|;
block|}
annotation|@
name|Override
specifier|public
name|Serializable
name|getQualifierId
parameter_list|()
block|{
return|return
name|iId
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getQualifierReference
parameter_list|()
block|{
return|return
name|iReference
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getQualifierLabel
parameter_list|()
block|{
return|return
name|iLabel
operator|==
literal|null
condition|?
name|iReference
else|:
name|iLabel
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|getQualifierType
argument_list|()
operator|+
literal|":"
operator|+
operator|(
name|getQualifierReference
argument_list|()
operator|==
literal|null
condition|?
name|getQualifierId
argument_list|()
operator|.
name|toString
argument_list|()
else|:
name|getQualifierReference
argument_list|()
operator|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|getQualifierId
argument_list|()
operator|==
literal|null
condition|?
name|getQualifierReference
argument_list|()
operator|.
name|hashCode
argument_list|()
else|:
name|getQualifierId
argument_list|()
operator|.
name|hashCode
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
if|if
condition|(
name|o
operator|==
literal|null
condition|)
return|return
literal|false
return|;
if|if
condition|(
name|o
operator|instanceof
name|Qualifiable
condition|)
block|{
name|Qualifiable
name|q
init|=
operator|(
name|Qualifiable
operator|)
name|o
decl_stmt|;
return|return
name|notNullAndEqual
argument_list|(
name|getQualifierType
argument_list|()
argument_list|,
name|q
operator|.
name|getQualifierType
argument_list|()
argument_list|)
operator|&&
operator|(
name|notNullAndEqual
argument_list|(
name|getQualifierId
argument_list|()
argument_list|,
name|q
operator|.
name|getQualifierId
argument_list|()
argument_list|)
operator|||
name|notNullAndEqual
argument_list|(
name|getQualifierReference
argument_list|()
argument_list|,
name|q
operator|.
name|getQualifierReference
argument_list|()
argument_list|)
operator|)
return|;
block|}
return|return
literal|false
return|;
block|}
specifier|private
name|boolean
name|notNullAndEqual
parameter_list|(
name|Object
name|o1
parameter_list|,
name|Object
name|o2
parameter_list|)
block|{
return|return
name|o1
operator|==
literal|null
condition|?
literal|false
else|:
name|o2
operator|==
literal|null
condition|?
literal|false
else|:
name|o1
operator|.
name|equals
argument_list|(
name|o2
argument_list|)
return|;
block|}
block|}
end_class

end_unit

