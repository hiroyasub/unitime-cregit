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
name|model
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
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
name|List
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|commons
operator|.
name|Debug
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
name|base
operator|.
name|BaseUserData
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
name|dao
operator|.
name|UserDataDAO
import|;
end_import

begin_class
specifier|public
class|class
name|UserData
extends|extends
name|BaseUserData
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
comment|/*[CONSTRUCTOR MARKER BEGIN]*/
specifier|public
name|UserData
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
comment|/*[CONSTRUCTOR MARKER END]*/
specifier|public
name|UserData
parameter_list|(
name|String
name|externalUniqueId
parameter_list|,
name|String
name|name
parameter_list|)
block|{
name|setExternalUniqueId
argument_list|(
name|externalUniqueId
argument_list|)
expr_stmt|;
name|setName
argument_list|(
name|name
argument_list|)
expr_stmt|;
name|initialize
argument_list|()
expr_stmt|;
block|}
specifier|public
specifier|static
name|void
name|setProperty
parameter_list|(
name|String
name|externalUniqueId
parameter_list|,
name|String
name|name
parameter_list|,
name|String
name|value
parameter_list|)
block|{
try|try
block|{
name|UserDataDAO
name|dao
init|=
operator|new
name|UserDataDAO
argument_list|()
decl_stmt|;
name|UserData
name|userData
init|=
name|dao
operator|.
name|get
argument_list|(
operator|new
name|UserData
argument_list|(
name|externalUniqueId
argument_list|,
name|name
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|value
operator|!=
literal|null
operator|&&
name|value
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
name|value
operator|=
literal|null
expr_stmt|;
if|if
condition|(
name|userData
operator|==
literal|null
operator|&&
name|value
operator|==
literal|null
condition|)
return|return;
if|if
condition|(
name|userData
operator|!=
literal|null
operator|&&
name|value
operator|!=
literal|null
operator|&&
name|value
operator|.
name|equals
argument_list|(
name|userData
operator|.
name|getValue
argument_list|()
argument_list|)
condition|)
return|return;
if|if
condition|(
name|userData
operator|==
literal|null
condition|)
block|{
name|userData
operator|=
operator|new
name|UserData
argument_list|(
name|externalUniqueId
argument_list|,
name|name
argument_list|)
expr_stmt|;
block|}
name|userData
operator|.
name|setValue
argument_list|(
name|value
argument_list|)
expr_stmt|;
if|if
condition|(
name|value
operator|==
literal|null
condition|)
name|dao
operator|.
name|delete
argument_list|(
name|userData
argument_list|)
expr_stmt|;
else|else
name|dao
operator|.
name|saveOrUpdate
argument_list|(
name|userData
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|Debug
operator|.
name|warning
argument_list|(
literal|"Failed to set user property "
operator|+
name|name
operator|+
literal|":="
operator|+
name|value
operator|+
literal|" ("
operator|+
name|e
operator|.
name|getMessage
argument_list|()
operator|+
literal|")"
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
specifier|static
name|String
name|getProperty
parameter_list|(
name|String
name|externalUniqueId
parameter_list|,
name|String
name|name
parameter_list|)
block|{
name|UserDataDAO
name|dao
init|=
operator|new
name|UserDataDAO
argument_list|()
decl_stmt|;
name|UserData
name|userData
init|=
name|dao
operator|.
name|get
argument_list|(
operator|new
name|UserData
argument_list|(
name|externalUniqueId
argument_list|,
name|name
argument_list|)
argument_list|)
decl_stmt|;
return|return
operator|(
name|userData
operator|==
literal|null
condition|?
literal|null
else|:
name|userData
operator|.
name|getValue
argument_list|()
operator|)
return|;
block|}
specifier|public
specifier|static
name|String
name|getProperty
parameter_list|(
name|String
name|externalUniqueId
parameter_list|,
name|String
name|name
parameter_list|,
name|String
name|defaultValue
parameter_list|)
block|{
name|String
name|value
init|=
name|getProperty
argument_list|(
name|externalUniqueId
argument_list|,
name|name
argument_list|)
decl_stmt|;
return|return
operator|(
name|value
operator|!=
literal|null
condition|?
name|value
else|:
name|defaultValue
operator|)
return|;
block|}
specifier|public
specifier|static
name|void
name|removeProperty
parameter_list|(
name|String
name|externalUniqueId
parameter_list|,
name|String
name|name
parameter_list|)
block|{
name|setProperty
argument_list|(
name|externalUniqueId
argument_list|,
name|name
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|getProperties
parameter_list|(
name|String
name|externalUniqueId
parameter_list|,
name|Collection
argument_list|<
name|String
argument_list|>
name|names
parameter_list|)
block|{
name|String
name|q
init|=
literal|"select u from UserData u where u.externalUniqueId = :externalUniqueId and u.name in ("
decl_stmt|;
for|for
control|(
name|Iterator
argument_list|<
name|String
argument_list|>
name|i
init|=
name|names
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
name|q
operator|+=
literal|"'"
operator|+
name|i
operator|.
name|next
argument_list|()
operator|+
literal|"'"
expr_stmt|;
if|if
condition|(
name|i
operator|.
name|hasNext
argument_list|()
condition|)
name|q
operator|+=
literal|","
expr_stmt|;
block|}
name|q
operator|+=
literal|")"
expr_stmt|;
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|ret
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|UserData
name|u
range|:
operator|(
name|List
argument_list|<
name|UserData
argument_list|>
operator|)
name|UserDataDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
name|q
argument_list|)
operator|.
name|setString
argument_list|(
literal|"externalUniqueId"
argument_list|,
name|externalUniqueId
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
control|)
block|{
name|ret
operator|.
name|put
argument_list|(
name|u
operator|.
name|getName
argument_list|()
argument_list|,
name|u
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|ret
return|;
block|}
specifier|public
specifier|static
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|getProperties
parameter_list|(
name|String
name|externalUniqueId
parameter_list|)
block|{
name|String
name|q
init|=
literal|"select u from UserData u where u.externalUniqueId = :externalUniqueId"
decl_stmt|;
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|ret
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|UserData
name|u
range|:
operator|(
name|List
argument_list|<
name|UserData
argument_list|>
operator|)
name|UserDataDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
name|q
argument_list|)
operator|.
name|setString
argument_list|(
literal|"externalUniqueId"
argument_list|,
name|externalUniqueId
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
control|)
block|{
name|ret
operator|.
name|put
argument_list|(
name|u
operator|.
name|getName
argument_list|()
argument_list|,
name|u
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|ret
return|;
block|}
specifier|public
specifier|static
name|boolean
name|getPropertyBoolean
parameter_list|(
name|String
name|externalUniqueId
parameter_list|,
name|String
name|name
parameter_list|,
name|boolean
name|defaultValue
parameter_list|)
block|{
name|String
name|value
init|=
name|getProperty
argument_list|(
name|externalUniqueId
argument_list|,
name|name
argument_list|)
decl_stmt|;
return|return
operator|(
name|value
operator|!=
literal|null
condition|?
literal|"1"
operator|.
name|equals
argument_list|(
name|value
argument_list|)
else|:
name|defaultValue
operator|)
return|;
block|}
block|}
end_class

end_unit

