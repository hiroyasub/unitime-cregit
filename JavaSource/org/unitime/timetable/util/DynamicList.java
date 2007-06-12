begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.0 (University Course Timetabling& Student Sectioning Application)  * Copyright (C) 2007, UniTime.org, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program; if not, write to the Free Software Foundation, Inc.,  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|util
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
name|java
operator|.
name|util
operator|.
name|ListIterator
import|;
end_import

begin_comment
comment|/**  * This class is a workaround to a bug in Struts   * (does not re-initialize the ArrayList with new values)  * It overrides the get method to create a new element at   * the given index if one does not exist   *   * @author Heston Fernandes  */
end_comment

begin_class
specifier|public
class|class
name|DynamicList
implements|implements
name|List
block|{
comment|/**      * The factory creating new instances of the class       * which are included in the list      */
specifier|private
name|DynamicListObjectFactory
name|objectFactory
decl_stmt|;
comment|/** list holding the values */
specifier|private
name|List
name|list
decl_stmt|;
comment|/**      * function to be used to get a new instance from the decorator      * @param list List      * @param objectFactory object factory to create elements in the list      */
specifier|public
specifier|static
name|List
name|getInstance
parameter_list|(
name|List
name|list
parameter_list|,
name|DynamicListObjectFactory
name|objectFactory
parameter_list|)
block|{
return|return
operator|new
name|DynamicList
argument_list|(
name|list
argument_list|,
name|objectFactory
argument_list|)
return|;
block|}
comment|/**      * Constructor      * @param list List      * @param objectFactory object factory to create elements in the list      */
specifier|private
name|DynamicList
parameter_list|(
name|List
name|list
parameter_list|,
name|DynamicListObjectFactory
name|objectFactory
parameter_list|)
block|{
if|if
condition|(
name|objectFactory
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Factory must not be null"
argument_list|)
throw|;
block|}
if|if
condition|(
name|list
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"List must not be null"
argument_list|)
throw|;
block|}
name|this
operator|.
name|list
operator|=
name|list
expr_stmt|;
name|this
operator|.
name|objectFactory
operator|=
name|objectFactory
expr_stmt|;
block|}
comment|/**      * If the list size is less than the index      * expand the list to the given index by creating new entries      * and initializing them       * @param index Index      */
specifier|public
name|Object
name|get
parameter_list|(
name|int
name|index
parameter_list|)
block|{
name|int
name|size
init|=
name|list
operator|.
name|size
argument_list|()
decl_stmt|;
comment|// Within bounds, get the object
if|if
condition|(
name|index
operator|<
name|size
condition|)
block|{
name|Object
name|object
init|=
name|list
operator|.
name|get
argument_list|(
name|index
argument_list|)
decl_stmt|;
comment|// item is a place holder, create new one, set and return
if|if
condition|(
name|object
operator|==
literal|null
condition|)
block|{
name|object
operator|=
name|objectFactory
operator|.
name|create
argument_list|()
expr_stmt|;
name|list
operator|.
name|set
argument_list|(
name|index
argument_list|,
name|object
argument_list|)
expr_stmt|;
return|return
name|object
return|;
block|}
else|else
block|{
return|return
name|object
return|;
block|}
block|}
comment|// Out of bounds
else|else
block|{
comment|// Grow the list
for|for
control|(
name|int
name|i
init|=
name|size
init|;
name|i
operator|<
name|index
condition|;
name|i
operator|++
control|)
block|{
name|list
operator|.
name|add
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
comment|// Create the last object, set and return
name|Object
name|object
init|=
name|objectFactory
operator|.
name|create
argument_list|()
decl_stmt|;
name|list
operator|.
name|add
argument_list|(
name|object
argument_list|)
expr_stmt|;
return|return
name|object
return|;
block|}
block|}
comment|/**      * Sets the list at index with the object      * If the index is out of bounds the list is expanded to the index      * @param index Index      * @object element Object to set at index      */
specifier|public
name|Object
name|set
parameter_list|(
name|int
name|index
parameter_list|,
name|Object
name|element
parameter_list|)
block|{
name|int
name|size
init|=
name|list
operator|.
name|size
argument_list|()
decl_stmt|;
comment|// Grow the list
if|if
condition|(
name|index
operator|>=
name|size
condition|)
block|{
for|for
control|(
name|int
name|i
init|=
name|size
init|;
name|i
operator|<=
name|index
condition|;
name|i
operator|++
control|)
block|{
name|list
operator|.
name|add
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|list
operator|.
name|set
argument_list|(
name|index
argument_list|,
name|element
argument_list|)
return|;
block|}
comment|/*      * From here on - just delegate methods to the list      */
specifier|public
name|void
name|add
parameter_list|(
name|int
name|index
parameter_list|,
name|Object
name|element
parameter_list|)
block|{
name|list
operator|.
name|add
argument_list|(
name|index
argument_list|,
name|element
argument_list|)
expr_stmt|;
block|}
specifier|public
name|boolean
name|add
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
return|return
name|list
operator|.
name|add
argument_list|(
name|o
argument_list|)
return|;
block|}
specifier|public
name|boolean
name|addAll
parameter_list|(
name|int
name|index
parameter_list|,
name|Collection
name|c
parameter_list|)
block|{
return|return
name|list
operator|.
name|addAll
argument_list|(
name|index
argument_list|,
name|c
argument_list|)
return|;
block|}
specifier|public
name|boolean
name|addAll
parameter_list|(
name|Collection
name|c
parameter_list|)
block|{
return|return
name|list
operator|.
name|addAll
argument_list|(
name|c
argument_list|)
return|;
block|}
specifier|public
name|void
name|clear
parameter_list|()
block|{
name|list
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
specifier|public
name|boolean
name|contains
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
return|return
name|list
operator|.
name|contains
argument_list|(
name|o
argument_list|)
return|;
block|}
specifier|public
name|boolean
name|containsAll
parameter_list|(
name|Collection
name|c
parameter_list|)
block|{
return|return
name|list
operator|.
name|containsAll
argument_list|(
name|c
argument_list|)
return|;
block|}
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|obj
parameter_list|)
block|{
return|return
name|list
operator|.
name|equals
argument_list|(
name|obj
argument_list|)
return|;
block|}
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|list
operator|.
name|hashCode
argument_list|()
return|;
block|}
specifier|public
name|int
name|indexOf
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
return|return
name|list
operator|.
name|indexOf
argument_list|(
name|o
argument_list|)
return|;
block|}
specifier|public
name|boolean
name|isEmpty
parameter_list|()
block|{
return|return
name|list
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|Iterator
name|iterator
parameter_list|()
block|{
return|return
name|list
operator|.
name|iterator
argument_list|()
return|;
block|}
specifier|public
name|int
name|lastIndexOf
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
return|return
name|list
operator|.
name|lastIndexOf
argument_list|(
name|o
argument_list|)
return|;
block|}
specifier|public
name|ListIterator
name|listIterator
parameter_list|()
block|{
return|return
name|list
operator|.
name|listIterator
argument_list|()
return|;
block|}
specifier|public
name|ListIterator
name|listIterator
parameter_list|(
name|int
name|index
parameter_list|)
block|{
return|return
name|list
operator|.
name|listIterator
argument_list|(
name|index
argument_list|)
return|;
block|}
specifier|public
name|Object
name|remove
parameter_list|(
name|int
name|index
parameter_list|)
block|{
return|return
name|list
operator|.
name|remove
argument_list|(
name|index
argument_list|)
return|;
block|}
specifier|public
name|boolean
name|remove
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
return|return
name|list
operator|.
name|remove
argument_list|(
name|o
argument_list|)
return|;
block|}
specifier|public
name|boolean
name|removeAll
parameter_list|(
name|Collection
name|c
parameter_list|)
block|{
return|return
name|list
operator|.
name|removeAll
argument_list|(
name|c
argument_list|)
return|;
block|}
specifier|public
name|boolean
name|retainAll
parameter_list|(
name|Collection
name|c
parameter_list|)
block|{
return|return
name|list
operator|.
name|retainAll
argument_list|(
name|c
argument_list|)
return|;
block|}
specifier|public
name|int
name|size
parameter_list|()
block|{
return|return
name|list
operator|.
name|size
argument_list|()
return|;
block|}
specifier|public
name|List
name|subList
parameter_list|(
name|int
name|fromIndex
parameter_list|,
name|int
name|toIndex
parameter_list|)
block|{
return|return
name|list
operator|.
name|subList
argument_list|(
name|fromIndex
argument_list|,
name|toIndex
argument_list|)
return|;
block|}
specifier|public
name|Object
index|[]
name|toArray
parameter_list|()
block|{
return|return
name|list
operator|.
name|toArray
argument_list|()
return|;
block|}
specifier|public
name|Object
index|[]
name|toArray
parameter_list|(
name|Object
index|[]
name|a
parameter_list|)
block|{
return|return
name|list
operator|.
name|toArray
argument_list|(
name|a
argument_list|)
return|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|list
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
end_class

end_unit

