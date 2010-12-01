begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 (University Timetabling Application)  * Copyright (C) 2010, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|shared
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

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
name|List
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|gwt
operator|.
name|user
operator|.
name|client
operator|.
name|rpc
operator|.
name|IsSerializable
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|SimpleEditInterface
implements|implements
name|IsSerializable
block|{
specifier|public
specifier|static
enum|enum
name|Type
block|{
name|area
argument_list|(
literal|"Academic Areas"
argument_list|)
block|,
name|classification
argument_list|(
literal|"Academic Classifications"
argument_list|)
block|,
name|major
argument_list|(
literal|"Majors"
argument_list|)
block|,
name|minor
argument_list|(
literal|"Minors"
argument_list|)
block|;
specifier|private
name|String
name|iPageName
decl_stmt|;
name|Type
parameter_list|(
name|String
name|pageName
parameter_list|)
block|{
name|iPageName
operator|=
name|pageName
expr_stmt|;
block|}
specifier|public
name|String
name|getTitle
parameter_list|()
block|{
return|return
name|iPageName
return|;
block|}
block|}
specifier|public
specifier|static
enum|enum
name|FieldType
block|{
name|text
block|,
name|toggle
block|,
name|list
block|,
name|multi
block|; 	}
specifier|private
name|Type
name|iType
init|=
literal|null
decl_stmt|;
specifier|private
name|List
argument_list|<
name|Record
argument_list|>
name|iRecords
init|=
operator|new
name|ArrayList
argument_list|<
name|Record
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|Field
index|[]
name|iFields
init|=
literal|null
decl_stmt|;
specifier|private
name|boolean
name|iEditable
init|=
literal|true
decl_stmt|;
specifier|private
name|int
index|[]
name|iSort
init|=
literal|null
decl_stmt|;
specifier|public
name|SimpleEditInterface
parameter_list|()
block|{
block|}
specifier|public
name|SimpleEditInterface
parameter_list|(
name|Type
name|type
parameter_list|,
name|Field
modifier|...
name|fields
parameter_list|)
block|{
name|iType
operator|=
name|type
expr_stmt|;
name|iFields
operator|=
name|fields
expr_stmt|;
block|}
specifier|public
name|Type
name|getType
parameter_list|()
block|{
return|return
name|iType
return|;
block|}
specifier|public
name|List
argument_list|<
name|Record
argument_list|>
name|getRecords
parameter_list|()
block|{
return|return
name|iRecords
return|;
block|}
specifier|public
name|Record
name|addRecord
parameter_list|(
name|Long
name|uniqueId
parameter_list|)
block|{
name|Record
name|r
init|=
operator|new
name|Record
argument_list|(
name|uniqueId
argument_list|,
name|iFields
operator|.
name|length
argument_list|)
decl_stmt|;
name|iRecords
operator|.
name|add
argument_list|(
name|r
argument_list|)
expr_stmt|;
return|return
name|r
return|;
block|}
specifier|public
name|Record
name|insertEmptyRecord
parameter_list|(
name|int
name|pos
parameter_list|)
block|{
name|Record
name|r
init|=
operator|new
name|Record
argument_list|(
literal|null
argument_list|,
name|iFields
operator|.
name|length
argument_list|)
decl_stmt|;
name|iRecords
operator|.
name|add
argument_list|(
name|pos
argument_list|,
name|r
argument_list|)
expr_stmt|;
return|return
name|r
return|;
block|}
specifier|public
name|void
name|moveRecord
parameter_list|(
name|int
name|row
parameter_list|,
name|int
name|before
parameter_list|)
block|{
name|Record
name|r
init|=
name|iRecords
operator|.
name|get
argument_list|(
name|row
argument_list|)
decl_stmt|;
name|iRecords
operator|.
name|remove
argument_list|(
name|row
argument_list|)
expr_stmt|;
name|iRecords
operator|.
name|add
argument_list|(
name|before
operator|+
operator|(
name|row
operator|<
name|before
condition|?
operator|-
literal|1
else|:
literal|0
operator|)
argument_list|,
name|r
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Record
name|getRecord
parameter_list|(
name|Long
name|uniqueId
parameter_list|)
block|{
for|for
control|(
name|Record
name|r
range|:
name|iRecords
control|)
if|if
condition|(
name|r
operator|.
name|getUniqueId
argument_list|()
operator|!=
literal|null
operator|&&
name|r
operator|.
name|getUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
name|uniqueId
argument_list|)
condition|)
return|return
name|r
return|;
return|return
literal|null
return|;
block|}
specifier|public
name|List
argument_list|<
name|Record
argument_list|>
name|getNewRecords
parameter_list|()
block|{
name|List
argument_list|<
name|Record
argument_list|>
name|ret
init|=
operator|new
name|ArrayList
argument_list|<
name|Record
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Record
name|r
range|:
name|iRecords
control|)
block|{
if|if
condition|(
name|r
operator|.
name|getUniqueId
argument_list|()
operator|!=
literal|null
operator|||
name|r
operator|.
name|isEmpty
argument_list|()
condition|)
continue|continue;
name|ret
operator|.
name|add
argument_list|(
name|r
argument_list|)
expr_stmt|;
block|}
return|return
name|ret
return|;
block|}
specifier|public
name|Field
index|[]
name|getFields
parameter_list|()
block|{
return|return
name|iFields
return|;
block|}
specifier|public
name|int
name|indexOf
parameter_list|(
name|String
name|name
parameter_list|)
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|iFields
operator|.
name|length
condition|;
name|i
operator|++
control|)
if|if
condition|(
name|iFields
index|[
name|i
index|]
operator|.
name|getName
argument_list|()
operator|.
name|equals
argument_list|(
name|name
argument_list|)
condition|)
return|return
name|i
return|;
return|return
operator|-
literal|1
return|;
block|}
specifier|public
name|boolean
name|isEditable
parameter_list|()
block|{
return|return
name|iEditable
return|;
block|}
specifier|public
name|void
name|setEditable
parameter_list|(
name|boolean
name|editable
parameter_list|)
block|{
name|iEditable
operator|=
name|editable
expr_stmt|;
block|}
specifier|public
name|int
index|[]
name|getSortBy
parameter_list|()
block|{
return|return
name|iSort
return|;
block|}
specifier|public
name|void
name|setSortBy
parameter_list|(
name|int
modifier|...
name|columns
parameter_list|)
block|{
name|iSort
operator|=
name|columns
expr_stmt|;
block|}
specifier|public
name|Comparator
argument_list|<
name|Record
argument_list|>
name|getComparator
parameter_list|()
block|{
return|return
operator|new
name|RecordComparator
argument_list|()
return|;
block|}
specifier|public
class|class
name|RecordComparator
implements|implements
name|Comparator
argument_list|<
name|Record
argument_list|>
block|{
specifier|public
name|int
name|compare
parameter_list|(
name|Record
name|r1
parameter_list|,
name|Record
name|r2
parameter_list|)
block|{
if|if
condition|(
name|getSortBy
argument_list|()
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|int
name|i
range|:
name|getSortBy
argument_list|()
control|)
block|{
name|String
name|s1
init|=
name|r1
operator|.
name|getText
argument_list|(
name|getFields
argument_list|()
index|[
name|i
index|]
argument_list|,
name|i
argument_list|)
decl_stmt|;
name|String
name|s2
init|=
name|r2
operator|.
name|getText
argument_list|(
name|getFields
argument_list|()
index|[
name|i
index|]
argument_list|,
name|i
argument_list|)
decl_stmt|;
name|int
name|cmp
init|=
name|s1
operator|.
name|compareTo
argument_list|(
name|s2
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
block|}
else|else
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|r1
operator|.
name|getValues
argument_list|()
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|String
name|s1
init|=
name|r1
operator|.
name|getText
argument_list|(
name|getFields
argument_list|()
index|[
name|i
index|]
argument_list|,
name|i
argument_list|)
decl_stmt|;
name|String
name|s2
init|=
name|r2
operator|.
name|getText
argument_list|(
name|getFields
argument_list|()
index|[
name|i
index|]
argument_list|,
name|i
argument_list|)
decl_stmt|;
name|int
name|cmp
init|=
name|s1
operator|.
name|compareTo
argument_list|(
name|s2
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
block|}
return|return
name|r1
operator|.
name|getUniqueId
argument_list|()
operator|.
name|compareTo
argument_list|(
name|r2
operator|.
name|getUniqueId
argument_list|()
argument_list|)
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|Record
implements|implements
name|IsSerializable
block|{
specifier|private
name|Long
name|iUniqueId
init|=
literal|null
decl_stmt|;
specifier|private
name|String
index|[]
name|iValues
init|=
literal|null
decl_stmt|;
specifier|public
name|Record
parameter_list|()
block|{
block|}
specifier|public
name|Record
parameter_list|(
name|Long
name|uniqueId
parameter_list|,
name|int
name|nrFields
parameter_list|)
block|{
name|iUniqueId
operator|=
name|uniqueId
expr_stmt|;
name|iValues
operator|=
operator|new
name|String
index|[
name|nrFields
index|]
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|nrFields
condition|;
name|i
operator|++
control|)
name|iValues
index|[
name|i
index|]
operator|=
literal|null
expr_stmt|;
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
name|void
name|setField
parameter_list|(
name|int
name|index
parameter_list|,
name|String
name|value
parameter_list|)
block|{
name|iValues
index|[
name|index
index|]
operator|=
name|value
expr_stmt|;
block|}
specifier|public
name|String
name|getField
parameter_list|(
name|int
name|index
parameter_list|)
block|{
return|return
name|iValues
index|[
name|index
index|]
return|;
block|}
specifier|public
name|void
name|addToField
parameter_list|(
name|int
name|index
parameter_list|,
name|String
name|value
parameter_list|)
block|{
if|if
condition|(
name|iValues
index|[
name|index
index|]
operator|==
literal|null
condition|)
name|iValues
index|[
name|index
index|]
operator|=
name|value
expr_stmt|;
else|else
name|iValues
index|[
name|index
index|]
operator|+=
literal|"|"
operator|+
name|value
expr_stmt|;
block|}
specifier|public
name|String
index|[]
name|getValues
parameter_list|(
name|int
name|index
parameter_list|)
block|{
return|return
operator|(
name|iValues
index|[
name|index
index|]
operator|==
literal|null
condition|?
operator|new
name|String
index|[]
block|{}
else|:
name|iValues
index|[
name|index
index|]
operator|.
name|split
argument_list|(
literal|"\\|"
argument_list|)
operator|)
return|;
block|}
specifier|public
name|String
index|[]
name|getValues
parameter_list|()
block|{
return|return
name|iValues
return|;
block|}
specifier|public
name|void
name|setValues
parameter_list|(
name|String
index|[]
name|values
parameter_list|)
block|{
name|iValues
operator|=
name|values
expr_stmt|;
block|}
specifier|public
name|String
name|getText
parameter_list|(
name|Field
name|f
parameter_list|,
name|int
name|index
parameter_list|)
block|{
name|String
name|value
init|=
name|getField
argument_list|(
name|index
argument_list|)
decl_stmt|;
if|if
condition|(
name|value
operator|==
literal|null
condition|)
return|return
literal|""
return|;
if|if
condition|(
name|f
operator|.
name|getType
argument_list|()
operator|==
name|FieldType
operator|.
name|list
condition|)
block|{
for|for
control|(
name|ListItem
name|item
range|:
name|f
operator|.
name|getValues
argument_list|()
control|)
block|{
if|if
condition|(
name|item
operator|.
name|getValue
argument_list|()
operator|.
name|equals
argument_list|(
name|value
argument_list|)
condition|)
return|return
name|item
operator|.
name|getText
argument_list|()
return|;
block|}
block|}
if|else if
condition|(
name|f
operator|.
name|getType
argument_list|()
operator|==
name|FieldType
operator|.
name|multi
condition|)
block|{
name|String
name|text
init|=
literal|""
decl_stmt|;
for|for
control|(
name|String
name|val
range|:
name|getValues
argument_list|(
name|index
argument_list|)
control|)
block|{
for|for
control|(
name|ListItem
name|item
range|:
name|f
operator|.
name|getValues
argument_list|()
control|)
block|{
if|if
condition|(
name|item
operator|.
name|getValue
argument_list|()
operator|.
name|equals
argument_list|(
name|val
argument_list|)
condition|)
block|{
if|if
condition|(
operator|!
name|text
operator|.
name|isEmpty
argument_list|()
condition|)
name|text
operator|+=
literal|", "
expr_stmt|;
name|text
operator|+=
name|item
operator|.
name|getText
argument_list|()
expr_stmt|;
block|}
block|}
block|}
return|return
name|text
return|;
block|}
return|return
name|value
return|;
block|}
specifier|public
name|boolean
name|isEmpty
parameter_list|()
block|{
if|if
condition|(
name|getUniqueId
argument_list|()
operator|!=
literal|null
condition|)
return|return
literal|false
return|;
for|for
control|(
name|String
name|v
range|:
name|iValues
control|)
block|{
if|if
condition|(
name|v
operator|!=
literal|null
operator|&&
operator|!
name|v
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
literal|false
return|;
block|}
return|return
literal|true
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|ListItem
implements|implements
name|IsSerializable
block|{
specifier|private
name|String
name|iValue
decl_stmt|,
name|iText
decl_stmt|;
specifier|public
name|ListItem
parameter_list|()
block|{
block|}
specifier|public
name|ListItem
parameter_list|(
name|String
name|value
parameter_list|,
name|String
name|text
parameter_list|)
block|{
name|iValue
operator|=
name|value
expr_stmt|;
name|iText
operator|=
name|text
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
name|String
name|getText
parameter_list|()
block|{
return|return
name|iText
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|Field
implements|implements
name|IsSerializable
block|{
specifier|private
name|String
name|iName
init|=
literal|null
decl_stmt|;
specifier|private
name|FieldType
name|iType
init|=
literal|null
decl_stmt|;
specifier|private
name|int
name|iLength
init|=
literal|0
decl_stmt|,
name|iWidth
init|=
literal|0
decl_stmt|;
specifier|private
name|List
argument_list|<
name|ListItem
argument_list|>
name|iValues
init|=
literal|null
decl_stmt|;
specifier|public
name|Field
parameter_list|()
block|{
block|}
specifier|public
name|Field
parameter_list|(
name|String
name|name
parameter_list|,
name|FieldType
name|type
parameter_list|,
name|int
name|width
parameter_list|)
block|{
name|iName
operator|=
name|name
expr_stmt|;
name|iType
operator|=
name|type
expr_stmt|;
name|iWidth
operator|=
name|width
expr_stmt|;
block|}
specifier|public
name|Field
parameter_list|(
name|String
name|name
parameter_list|,
name|FieldType
name|type
parameter_list|,
name|int
name|width
parameter_list|,
name|int
name|length
parameter_list|)
block|{
name|this
argument_list|(
name|name
argument_list|,
name|type
argument_list|,
name|width
argument_list|)
expr_stmt|;
name|iLength
operator|=
name|length
expr_stmt|;
block|}
specifier|public
name|Field
parameter_list|(
name|String
name|name
parameter_list|,
name|FieldType
name|type
parameter_list|,
name|int
name|width
parameter_list|,
name|List
argument_list|<
name|ListItem
argument_list|>
name|values
parameter_list|)
block|{
name|this
argument_list|(
name|name
argument_list|,
name|type
argument_list|,
name|width
argument_list|)
expr_stmt|;
name|iValues
operator|=
name|values
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
name|FieldType
name|getType
parameter_list|()
block|{
return|return
name|iType
return|;
block|}
specifier|public
name|int
name|getLength
parameter_list|()
block|{
return|return
name|iLength
return|;
block|}
specifier|public
name|int
name|getWidth
parameter_list|()
block|{
return|return
name|iWidth
return|;
block|}
specifier|public
name|List
argument_list|<
name|ListItem
argument_list|>
name|getValues
parameter_list|()
block|{
return|return
name|iValues
return|;
block|}
specifier|public
name|void
name|addValue
parameter_list|(
name|ListItem
name|item
parameter_list|)
block|{
if|if
condition|(
name|iValues
operator|==
literal|null
condition|)
name|iValues
operator|=
operator|new
name|ArrayList
argument_list|<
name|ListItem
argument_list|>
argument_list|()
expr_stmt|;
name|iValues
operator|.
name|add
argument_list|(
name|item
argument_list|)
expr_stmt|;
block|}
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|getName
argument_list|()
operator|.
name|hashCode
argument_list|()
return|;
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
name|Field
operator|)
condition|)
return|return
literal|false
return|;
return|return
name|getName
argument_list|()
operator|.
name|equals
argument_list|(
operator|(
operator|(
name|Field
operator|)
name|o
operator|)
operator|.
name|getName
argument_list|()
argument_list|)
return|;
block|}
block|}
block|}
end_class

end_unit

