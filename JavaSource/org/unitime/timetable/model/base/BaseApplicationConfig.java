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
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|ApplicationConfig
import|;
end_import

begin_comment
comment|/**  * Do not change this class. It has been automatically generated using ant create-model.  * @see org.unitime.commons.ant.CreateBaseModelFromXml  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|BaseApplicationConfig
implements|implements
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
specifier|private
name|String
name|iKey
decl_stmt|;
specifier|private
name|String
name|iValue
decl_stmt|;
specifier|private
name|String
name|iDescription
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_NAME
init|=
literal|"key"
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
name|PROP_DESCRIPTION
init|=
literal|"description"
decl_stmt|;
specifier|public
name|BaseApplicationConfig
parameter_list|()
block|{
name|initialize
argument_list|()
expr_stmt|;
block|}
specifier|public
name|BaseApplicationConfig
parameter_list|(
name|String
name|key
parameter_list|)
block|{
name|setKey
argument_list|(
name|key
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
specifier|public
name|String
name|getKey
parameter_list|()
block|{
return|return
name|iKey
return|;
block|}
specifier|public
name|void
name|setKey
parameter_list|(
name|String
name|key
parameter_list|)
block|{
name|iKey
operator|=
name|key
expr_stmt|;
block|}
specifier|public
name|String
name|getValue
parameter_list|()
block|{
return|return
name|iValue
return|;
block|}
specifier|public
name|void
name|setValue
parameter_list|(
name|String
name|value
parameter_list|)
block|{
name|iValue
operator|=
name|value
expr_stmt|;
block|}
specifier|public
name|String
name|getDescription
parameter_list|()
block|{
return|return
name|iDescription
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
name|iDescription
operator|=
name|description
expr_stmt|;
block|}
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
operator|||
operator|!
operator|(
name|o
operator|instanceof
name|ApplicationConfig
operator|)
condition|)
return|return
literal|false
return|;
if|if
condition|(
name|getKey
argument_list|()
operator|==
literal|null
operator|||
operator|(
operator|(
name|ApplicationConfig
operator|)
name|o
operator|)
operator|.
name|getKey
argument_list|()
operator|==
literal|null
condition|)
return|return
literal|false
return|;
return|return
name|getKey
argument_list|()
operator|.
name|equals
argument_list|(
operator|(
operator|(
name|ApplicationConfig
operator|)
name|o
operator|)
operator|.
name|getKey
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
if|if
condition|(
name|getKey
argument_list|()
operator|==
literal|null
condition|)
return|return
name|super
operator|.
name|hashCode
argument_list|()
return|;
return|return
name|getKey
argument_list|()
operator|.
name|hashCode
argument_list|()
return|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
literal|"ApplicationConfig["
operator|+
name|getKey
argument_list|()
operator|+
literal|"]"
return|;
block|}
specifier|public
name|String
name|toDebugString
parameter_list|()
block|{
return|return
literal|"ApplicationConfig["
operator|+
literal|"\n	Description: "
operator|+
name|getDescription
argument_list|()
operator|+
literal|"\n	Key: "
operator|+
name|getKey
argument_list|()
operator|+
literal|"\n	Value: "
operator|+
name|getValue
argument_list|()
operator|+
literal|"]"
return|;
block|}
block|}
end_class

end_unit

