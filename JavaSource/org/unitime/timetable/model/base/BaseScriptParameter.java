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
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Script
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
name|ScriptParameter
import|;
end_import

begin_class
specifier|public
specifier|abstract
class|class
name|BaseScriptParameter
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
name|Script
name|iScript
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
name|BaseScriptParameter
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
name|Script
name|getScript
parameter_list|()
block|{
return|return
name|iScript
return|;
block|}
specifier|public
name|void
name|setScript
parameter_list|(
name|Script
name|script
parameter_list|)
block|{
name|iScript
operator|=
name|script
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
name|ScriptParameter
operator|)
condition|)
return|return
literal|false
return|;
name|ScriptParameter
name|scriptParameter
init|=
operator|(
name|ScriptParameter
operator|)
name|o
decl_stmt|;
if|if
condition|(
name|getScript
argument_list|()
operator|==
literal|null
operator|||
name|scriptParameter
operator|.
name|getScript
argument_list|()
operator|==
literal|null
operator|||
operator|!
name|getScript
argument_list|()
operator|.
name|equals
argument_list|(
name|scriptParameter
operator|.
name|getScript
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
name|scriptParameter
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
name|scriptParameter
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
name|getScript
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
name|getScript
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
literal|"ScriptParameter["
operator|+
name|getScript
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
literal|"ScriptParameter["
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
literal|"\n	Script: "
operator|+
name|getScript
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

