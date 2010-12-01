begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 (University Timetabling Application)  * Copyright (C) 2010, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *  * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|Set
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
name|ManagerSettings
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
name|Settings
import|;
end_import

begin_class
specifier|public
specifier|abstract
class|class
name|BaseSettings
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
name|Long
name|iUniqueId
decl_stmt|;
specifier|private
name|String
name|iKey
decl_stmt|;
specifier|private
name|String
name|iDefaultValue
decl_stmt|;
specifier|private
name|String
name|iAllowedValues
decl_stmt|;
specifier|private
name|String
name|iDescription
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|ManagerSettings
argument_list|>
name|iManagerSettings
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_UNIQUEID
init|=
literal|"uniqueId"
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
name|PROP_DEFAULT_VALUE
init|=
literal|"defaultValue"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_ALLOWED_VALUES
init|=
literal|"allowedValues"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_DESCRIPTION
init|=
literal|"description"
decl_stmt|;
specifier|public
name|BaseSettings
parameter_list|()
block|{
name|initialize
argument_list|()
expr_stmt|;
block|}
specifier|public
name|BaseSettings
parameter_list|(
name|Long
name|uniqueId
parameter_list|)
block|{
name|setUniqueId
argument_list|(
name|uniqueId
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
name|Long
name|getUniqueId
parameter_list|()
block|{
return|return
name|iUniqueId
return|;
block|}
specifier|public
name|void
name|setUniqueId
parameter_list|(
name|Long
name|uniqueId
parameter_list|)
block|{
name|iUniqueId
operator|=
name|uniqueId
expr_stmt|;
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
name|String
name|getAllowedValues
parameter_list|()
block|{
return|return
name|iAllowedValues
return|;
block|}
specifier|public
name|void
name|setAllowedValues
parameter_list|(
name|String
name|allowedValues
parameter_list|)
block|{
name|iAllowedValues
operator|=
name|allowedValues
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
name|Set
argument_list|<
name|ManagerSettings
argument_list|>
name|getManagerSettings
parameter_list|()
block|{
return|return
name|iManagerSettings
return|;
block|}
specifier|public
name|void
name|setManagerSettings
parameter_list|(
name|Set
argument_list|<
name|ManagerSettings
argument_list|>
name|managerSettings
parameter_list|)
block|{
name|iManagerSettings
operator|=
name|managerSettings
expr_stmt|;
block|}
specifier|public
name|void
name|addTomanagerSettings
parameter_list|(
name|ManagerSettings
name|managerSettings
parameter_list|)
block|{
if|if
condition|(
name|iManagerSettings
operator|==
literal|null
condition|)
name|iManagerSettings
operator|=
operator|new
name|HashSet
argument_list|<
name|ManagerSettings
argument_list|>
argument_list|()
expr_stmt|;
name|iManagerSettings
operator|.
name|add
argument_list|(
name|managerSettings
argument_list|)
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
name|Settings
operator|)
condition|)
return|return
literal|false
return|;
if|if
condition|(
name|getUniqueId
argument_list|()
operator|==
literal|null
operator|||
operator|(
operator|(
name|Settings
operator|)
name|o
operator|)
operator|.
name|getUniqueId
argument_list|()
operator|==
literal|null
condition|)
return|return
literal|false
return|;
return|return
name|getUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
operator|(
operator|(
name|Settings
operator|)
name|o
operator|)
operator|.
name|getUniqueId
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
name|getUniqueId
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
name|getUniqueId
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
literal|"Settings["
operator|+
name|getUniqueId
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
literal|"Settings["
operator|+
literal|"\n	AllowedValues: "
operator|+
name|getAllowedValues
argument_list|()
operator|+
literal|"\n	DefaultValue: "
operator|+
name|getDefaultValue
argument_list|()
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
literal|"\n	UniqueId: "
operator|+
name|getUniqueId
argument_list|()
operator|+
literal|"]"
return|;
block|}
block|}
end_class

end_unit

