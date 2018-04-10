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
name|SavedHQL
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
name|SavedHQLParameter
import|;
end_import

begin_comment
comment|/**  * Do not change this class. It has been automatically generated using ant create-model.  * @see org.unitime.commons.ant.CreateBaseModelFromXml  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|BaseSavedHQLParameter
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
name|SavedHQL
name|iSavedHQL
decl_stmt|;
specifier|private
name|String
name|iName
decl_stmt|;
specifier|private
name|String
name|iLabel
decl_stmt|;
specifier|private
name|String
name|iType
decl_stmt|;
specifier|private
name|String
name|iDefaultValue
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_LABEL
init|=
literal|"label"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_TYPE
init|=
literal|"type"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_DEFAULT_VALUE
init|=
literal|"defaultValue"
decl_stmt|;
specifier|public
name|BaseSavedHQLParameter
parameter_list|()
block|{
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
name|SavedHQL
name|getSavedHQL
parameter_list|()
block|{
return|return
name|iSavedHQL
return|;
block|}
specifier|public
name|void
name|setSavedHQL
parameter_list|(
name|SavedHQL
name|savedHQL
parameter_list|)
block|{
name|iSavedHQL
operator|=
name|savedHQL
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
name|getLabel
parameter_list|()
block|{
return|return
name|iLabel
return|;
block|}
specifier|public
name|void
name|setLabel
parameter_list|(
name|String
name|label
parameter_list|)
block|{
name|iLabel
operator|=
name|label
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
name|String
name|getDefaultValue
parameter_list|()
block|{
return|return
name|iDefaultValue
return|;
block|}
specifier|public
name|void
name|setDefaultValue
parameter_list|(
name|String
name|defaultValue
parameter_list|)
block|{
name|iDefaultValue
operator|=
name|defaultValue
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
name|SavedHQLParameter
operator|)
condition|)
return|return
literal|false
return|;
name|SavedHQLParameter
name|savedHQLParameter
init|=
operator|(
name|SavedHQLParameter
operator|)
name|o
decl_stmt|;
if|if
condition|(
name|getSavedHQL
argument_list|()
operator|==
literal|null
operator|||
name|savedHQLParameter
operator|.
name|getSavedHQL
argument_list|()
operator|==
literal|null
operator|||
operator|!
name|getSavedHQL
argument_list|()
operator|.
name|equals
argument_list|(
name|savedHQLParameter
operator|.
name|getSavedHQL
argument_list|()
argument_list|)
condition|)
return|return
literal|false
return|;
if|if
condition|(
name|getName
argument_list|()
operator|==
literal|null
operator|||
name|savedHQLParameter
operator|.
name|getName
argument_list|()
operator|==
literal|null
operator|||
operator|!
name|getName
argument_list|()
operator|.
name|equals
argument_list|(
name|savedHQLParameter
operator|.
name|getName
argument_list|()
argument_list|)
condition|)
return|return
literal|false
return|;
return|return
literal|true
return|;
block|}
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
if|if
condition|(
name|getSavedHQL
argument_list|()
operator|==
literal|null
operator|||
name|getName
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
name|getSavedHQL
argument_list|()
operator|.
name|hashCode
argument_list|()
operator|^
name|getName
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
literal|"SavedHQLParameter["
operator|+
name|getSavedHQL
argument_list|()
operator|+
literal|", "
operator|+
name|getName
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
literal|"SavedHQLParameter["
operator|+
literal|"\n	DefaultValue: "
operator|+
name|getDefaultValue
argument_list|()
operator|+
literal|"\n	Label: "
operator|+
name|getLabel
argument_list|()
operator|+
literal|"\n	Name: "
operator|+
name|getName
argument_list|()
operator|+
literal|"\n	SavedHQL: "
operator|+
name|getSavedHQL
argument_list|()
operator|+
literal|"\n	Type: "
operator|+
name|getType
argument_list|()
operator|+
literal|"]"
return|;
block|}
block|}
end_class

end_unit

