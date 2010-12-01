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
name|Map
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Properties
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
name|PropertiesInfo
extends|extends
name|Properties
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
name|PropertiesInfo
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
specifier|public
name|PropertiesInfo
parameter_list|(
name|Map
name|info
parameter_list|)
block|{
name|super
argument_list|()
expr_stmt|;
for|for
control|(
name|Iterator
name|i1
init|=
name|info
operator|.
name|entrySet
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|i1
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Map
operator|.
name|Entry
name|entry
init|=
operator|(
name|Map
operator|.
name|Entry
operator|)
name|i1
operator|.
name|next
argument_list|()
decl_stmt|;
name|String
name|key
init|=
operator|(
name|String
operator|)
name|entry
operator|.
name|getKey
argument_list|()
decl_stmt|;
name|String
name|value
init|=
name|entry
operator|.
name|getValue
argument_list|()
operator|.
name|toString
argument_list|()
decl_stmt|;
name|setProperty
argument_list|(
name|key
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|void
name|load
parameter_list|(
name|Element
name|root
parameter_list|)
throws|throws
name|Exception
block|{
name|clear
argument_list|()
expr_stmt|;
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
for|for
control|(
name|Iterator
name|i
init|=
name|root
operator|.
name|elementIterator
argument_list|(
literal|"entry"
argument_list|)
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Element
name|el
init|=
operator|(
name|Element
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|setProperty
argument_list|(
name|el
operator|.
name|attributeValue
argument_list|(
literal|"key"
argument_list|)
argument_list|,
name|el
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
specifier|public
name|void
name|save
parameter_list|(
name|Element
name|root
parameter_list|)
throws|throws
name|Exception
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
for|for
control|(
name|Iterator
name|i
init|=
name|entrySet
argument_list|()
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
name|Map
operator|.
name|Entry
name|entry
init|=
operator|(
name|Map
operator|.
name|Entry
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|String
name|key
init|=
operator|(
name|String
operator|)
name|entry
operator|.
name|getKey
argument_list|()
decl_stmt|;
name|String
name|value
init|=
operator|(
name|String
operator|)
name|entry
operator|.
name|getValue
argument_list|()
decl_stmt|;
name|root
operator|.
name|addElement
argument_list|(
literal|"entry"
argument_list|)
operator|.
name|addAttribute
argument_list|(
literal|"key"
argument_list|,
name|key
argument_list|)
operator|.
name|setText
argument_list|(
name|value
argument_list|)
expr_stmt|;
block|}
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

