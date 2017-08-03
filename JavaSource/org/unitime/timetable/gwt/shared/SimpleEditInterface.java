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
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|gwt
operator|.
name|command
operator|.
name|client
operator|.
name|GwtRpcRequest
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
name|gwt
operator|.
name|command
operator|.
name|client
operator|.
name|GwtRpcResponse
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
implements|,
name|GwtRpcResponse
block|{
specifier|public
specifier|static
enum|enum
name|FieldType
implements|implements
name|IsSerializable
block|{
name|text
block|,
name|textarea
block|,
name|number
block|,
name|toggle
block|,
name|list
block|,
name|multi
block|,
name|students
block|,
name|person
block|,
name|date
block|,
name|parent
block|; 	}
specifier|public
specifier|static
enum|enum
name|Flag
implements|implements
name|IsSerializable
block|{
name|HIDDEN
block|,
name|READ_ONLY
block|,
name|UNIQUE
block|,
name|NOT_EMPTY
block|,
name|PARENT_NOT_EMPTY
block|,
name|FLOAT
block|,
name|NEGATIVE
block|,
name|SHOW_PARENT_IF_EMPTY
block|,
name|DEFAULT_CHECKED
block|, 		;
specifier|public
name|int
name|toInt
parameter_list|()
block|{
return|return
literal|1
operator|<<
name|ordinal
argument_list|()
return|;
block|}
specifier|public
name|boolean
name|has
parameter_list|(
name|int
name|flags
parameter_list|)
block|{
return|return
operator|(
name|flags
operator|&
name|toInt
argument_list|()
operator|)
operator|==
name|toInt
argument_list|()
return|;
block|}
block|}
comment|// private Type iType = null;
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
decl_stmt|,
name|iAddable
init|=
literal|true
decl_stmt|,
name|iSaveOrder
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
specifier|private
name|Long
name|iSessionId
init|=
literal|null
decl_stmt|;
specifier|private
name|String
name|iSessionName
init|=
literal|null
decl_stmt|;
specifier|private
name|PageName
name|iPageName
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
name|Field
modifier|...
name|fields
parameter_list|)
block|{
name|iFields
operator|=
name|fields
expr_stmt|;
block|}
specifier|public
name|boolean
name|isSaveOrder
parameter_list|()
block|{
return|return
name|iSaveOrder
return|;
block|}
specifier|public
name|void
name|setSaveOrder
parameter_list|(
name|boolean
name|saveOrder
parameter_list|)
block|{
name|iSaveOrder
operator|=
name|saveOrder
expr_stmt|;
block|}
specifier|public
name|Long
name|getSessionId
parameter_list|()
block|{
return|return
name|iSessionId
return|;
block|}
specifier|public
name|void
name|setSessionId
parameter_list|(
name|Long
name|sessionId
parameter_list|)
block|{
name|iSessionId
operator|=
name|sessionId
expr_stmt|;
block|}
specifier|public
name|String
name|getSessionName
parameter_list|()
block|{
return|return
name|iSessionName
return|;
block|}
specifier|public
name|void
name|setSessionName
parameter_list|(
name|String
name|sessionName
parameter_list|)
block|{
name|iSessionName
operator|=
name|sessionName
expr_stmt|;
block|}
specifier|public
name|void
name|setPageName
parameter_list|(
name|PageName
name|name
parameter_list|)
block|{
name|iPageName
operator|=
name|name
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasPageName
parameter_list|()
block|{
return|return
name|iPageName
operator|!=
literal|null
return|;
block|}
specifier|public
name|PageName
name|getPageName
parameter_list|()
block|{
return|return
name|iPageName
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
parameter_list|,
name|boolean
name|deletable
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
argument_list|,
name|deletable
argument_list|)
decl_stmt|;
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
operator|!
name|iFields
index|[
name|i
index|]
operator|.
name|isEditable
argument_list|()
condition|)
name|r
operator|.
name|setField
argument_list|(
name|i
argument_list|,
literal|null
argument_list|,
literal|false
argument_list|)
expr_stmt|;
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
name|addRecord
parameter_list|(
name|Long
name|uniqueId
parameter_list|)
block|{
return|return
name|addRecord
argument_list|(
name|uniqueId
argument_list|,
literal|true
argument_list|)
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
name|boolean
name|isAddable
parameter_list|()
block|{
return|return
name|iAddable
return|;
block|}
specifier|public
name|void
name|setAddable
parameter_list|(
name|boolean
name|addable
parameter_list|)
block|{
name|iAddable
operator|=
name|addable
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
name|RecordComparator
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
name|int
name|index
parameter_list|,
name|Record
name|r1
parameter_list|,
name|Record
name|r2
parameter_list|)
block|{
if|if
condition|(
name|getFields
argument_list|()
index|[
literal|0
index|]
operator|.
name|getType
argument_list|()
operator|==
name|FieldType
operator|.
name|parent
condition|)
block|{
name|Record
name|p1
init|=
operator|(
literal|"+"
operator|.
name|equals
argument_list|(
name|r1
operator|.
name|getField
argument_list|(
literal|0
argument_list|)
argument_list|)
operator|||
literal|"-"
operator|.
name|equals
argument_list|(
name|r1
operator|.
name|getField
argument_list|(
literal|0
argument_list|)
argument_list|)
condition|?
literal|null
else|:
name|getRecord
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|r1
operator|.
name|getField
argument_list|(
literal|0
argument_list|)
argument_list|)
argument_list|)
operator|)
decl_stmt|;
name|Record
name|p2
init|=
operator|(
literal|"+"
operator|.
name|equals
argument_list|(
name|r2
operator|.
name|getField
argument_list|(
literal|0
argument_list|)
argument_list|)
operator|||
literal|"-"
operator|.
name|equals
argument_list|(
name|r2
operator|.
name|getField
argument_list|(
literal|0
argument_list|)
argument_list|)
condition|?
literal|null
else|:
name|getRecord
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|r2
operator|.
name|getField
argument_list|(
literal|0
argument_list|)
argument_list|)
argument_list|)
operator|)
decl_stmt|;
if|if
condition|(
operator|(
name|p1
operator|==
literal|null
condition|?
name|r1
else|:
name|p1
operator|)
operator|.
name|equals
argument_list|(
name|p2
operator|==
literal|null
condition|?
name|r2
else|:
name|p2
argument_list|)
condition|)
block|{
comment|// same parents
if|if
condition|(
name|p1
operator|!=
literal|null
operator|&&
name|p2
operator|==
literal|null
condition|)
return|return
literal|1
return|;
comment|// r1 is already a parent
if|if
condition|(
name|p1
operator|==
literal|null
operator|&&
name|p2
operator|!=
literal|null
condition|)
return|return
operator|-
literal|1
return|;
comment|// r2 is already a parent
comment|// same level
block|}
if|else if
condition|(
name|p1
operator|!=
literal|null
operator|||
name|p2
operator|!=
literal|null
condition|)
block|{
comment|// different parents
return|return
name|compare
argument_list|(
name|index
argument_list|,
name|p1
operator|==
literal|null
condition|?
name|r1
else|:
name|p1
argument_list|,
name|p2
operator|==
literal|null
condition|?
name|r2
else|:
name|p2
argument_list|)
return|;
comment|// compare parents
block|}
block|}
if|if
condition|(
name|index
operator|<
literal|0
condition|)
return|return
operator|(
name|r1
operator|.
name|getUniqueId
argument_list|()
operator|==
literal|null
condition|?
name|r2
operator|.
name|getUniqueId
argument_list|()
operator|==
literal|null
condition|?
literal|0
else|:
literal|1
else|:
name|r1
operator|.
name|getUniqueId
argument_list|()
operator|==
literal|null
condition|?
operator|-
literal|1
else|:
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
operator|)
return|;
name|Field
name|field
init|=
name|getFields
argument_list|()
index|[
name|index
index|]
decl_stmt|;
name|String
name|s1
init|=
name|r1
operator|.
name|getText
argument_list|(
name|field
argument_list|,
name|index
argument_list|)
decl_stmt|;
name|String
name|s2
init|=
name|r2
operator|.
name|getText
argument_list|(
name|field
argument_list|,
name|index
argument_list|)
decl_stmt|;
if|if
condition|(
name|s1
operator|==
literal|null
condition|)
return|return
operator|(
name|s2
operator|==
literal|null
condition|?
literal|0
else|:
literal|1
operator|)
return|;
if|if
condition|(
name|s2
operator|==
literal|null
condition|)
return|return
operator|-
literal|1
return|;
switch|switch
condition|(
name|field
operator|.
name|getType
argument_list|()
condition|)
block|{
case|case
name|students
case|:
return|return
operator|new
name|Integer
argument_list|(
name|s1
operator|.
name|isEmpty
argument_list|()
condition|?
literal|0
else|:
name|s1
operator|.
name|split
argument_list|(
literal|"\\n"
argument_list|)
operator|.
name|length
argument_list|)
operator|.
name|compareTo
argument_list|(
name|s2
operator|.
name|isEmpty
argument_list|()
condition|?
literal|0
else|:
name|s2
operator|.
name|split
argument_list|(
literal|"\\n"
argument_list|)
operator|.
name|length
argument_list|)
return|;
default|default:
try|try
block|{
name|Double
name|d1
init|=
name|Double
operator|.
name|parseDouble
argument_list|(
name|s1
operator|.
name|isEmpty
argument_list|()
condition|?
literal|"0"
else|:
name|s1
argument_list|)
decl_stmt|;
name|Double
name|d2
init|=
name|Double
operator|.
name|parseDouble
argument_list|(
name|s2
operator|.
name|isEmpty
argument_list|()
condition|?
literal|"0"
else|:
name|s2
argument_list|)
decl_stmt|;
return|return
name|d1
operator|.
name|compareTo
argument_list|(
name|d2
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|e
parameter_list|)
block|{
return|return
name|s1
operator|.
name|compareTo
argument_list|(
name|s2
argument_list|)
return|;
block|}
block|}
block|}
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
name|int
name|cmp
init|=
name|compare
argument_list|(
name|i
argument_list|,
name|r1
argument_list|,
name|r2
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
name|int
name|cmp
init|=
name|compare
argument_list|(
name|i
argument_list|,
name|r1
argument_list|,
name|r2
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
name|compare
argument_list|(
operator|-
literal|1
argument_list|,
name|r1
argument_list|,
name|r2
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
implements|,
name|GwtRpcResponse
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
specifier|private
name|boolean
index|[]
name|iEditable
init|=
literal|null
decl_stmt|;
specifier|private
name|boolean
name|iDeletable
init|=
literal|true
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
parameter_list|,
name|boolean
name|deletable
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
name|iEditable
operator|=
operator|new
name|boolean
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
block|{
name|iValues
index|[
name|i
index|]
operator|=
literal|null
expr_stmt|;
name|iEditable
index|[
name|i
index|]
operator|=
literal|true
expr_stmt|;
block|}
name|iDeletable
operator|=
name|deletable
expr_stmt|;
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
name|this
argument_list|(
name|uniqueId
argument_list|,
name|nrFields
argument_list|,
literal|true
argument_list|)
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
parameter_list|,
name|boolean
name|editable
parameter_list|)
block|{
name|iValues
index|[
name|index
index|]
operator|=
name|value
expr_stmt|;
name|iEditable
index|[
name|index
index|]
operator|=
name|editable
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
name|boolean
name|isEditable
parameter_list|(
name|int
name|index
parameter_list|)
block|{
return|return
name|iEditable
index|[
name|index
index|]
return|;
block|}
specifier|public
name|boolean
name|isEditable
parameter_list|()
block|{
for|for
control|(
name|boolean
name|editable
range|:
name|iEditable
control|)
if|if
condition|(
name|editable
condition|)
return|return
literal|true
return|;
return|return
literal|false
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
specifier|public
name|boolean
name|isDeletable
parameter_list|()
block|{
return|return
name|iDeletable
return|;
block|}
specifier|public
name|void
name|setDeletable
parameter_list|(
name|boolean
name|deletable
parameter_list|)
block|{
name|iDeletable
operator|=
name|deletable
expr_stmt|;
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
operator|||
operator|!
operator|(
name|o
operator|instanceof
name|Record
operator|)
condition|)
return|return
literal|false
return|;
name|Record
name|r
init|=
operator|(
name|Record
operator|)
name|o
decl_stmt|;
if|if
condition|(
name|getUniqueId
argument_list|()
operator|!=
literal|null
condition|)
return|return
name|getUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
name|r
operator|.
name|getUniqueId
argument_list|()
argument_list|)
return|;
return|return
operator|(
name|r
operator|.
name|getUniqueId
argument_list|()
operator|!=
literal|null
condition|?
literal|false
else|:
name|super
operator|.
name|equals
argument_list|(
name|o
argument_list|)
operator|)
return|;
block|}
specifier|public
name|void
name|copyFrom
parameter_list|(
name|Record
name|record
parameter_list|)
block|{
name|iUniqueId
operator|=
name|record
operator|.
name|iUniqueId
expr_stmt|;
name|iValues
operator|=
name|record
operator|.
name|iValues
expr_stmt|;
name|iEditable
operator|=
name|record
operator|.
name|iEditable
expr_stmt|;
name|iDeletable
operator|=
name|record
operator|.
name|iDeletable
expr_stmt|;
block|}
specifier|public
name|void
name|copyTo
parameter_list|(
name|Record
name|record
parameter_list|)
block|{
name|record
operator|.
name|copyFrom
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Record
name|cloneRecord
parameter_list|()
block|{
name|Record
name|r
init|=
operator|new
name|Record
argument_list|(
name|iUniqueId
argument_list|,
name|iValues
operator|.
name|length
argument_list|,
name|iDeletable
argument_list|)
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|iValues
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|r
operator|.
name|iValues
index|[
name|i
index|]
operator|=
name|iValues
index|[
name|i
index|]
expr_stmt|;
name|r
operator|.
name|iEditable
index|[
name|i
index|]
operator|=
name|iEditable
index|[
name|i
index|]
expr_stmt|;
block|}
return|return
name|r
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
decl_stmt|,
name|iHeight
init|=
literal|1
decl_stmt|,
name|iFlags
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
parameter_list|,
name|int
name|height
parameter_list|,
name|int
name|length
parameter_list|,
name|Flag
modifier|...
name|flags
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
name|iHeight
operator|=
name|height
expr_stmt|;
name|iLength
operator|=
name|length
expr_stmt|;
name|iFlags
operator|=
literal|0
expr_stmt|;
for|for
control|(
name|Flag
name|flag
range|:
name|flags
control|)
if|if
condition|(
name|flag
operator|!=
literal|null
condition|)
name|iFlags
operator|=
name|iFlags
operator||
name|flag
operator|.
name|toInt
argument_list|()
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
name|Flag
modifier|...
name|flags
parameter_list|)
block|{
name|this
argument_list|(
name|name
argument_list|,
name|type
argument_list|,
name|width
argument_list|,
literal|1
argument_list|,
literal|0
argument_list|,
name|flags
argument_list|)
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
parameter_list|,
name|Flag
modifier|...
name|flags
parameter_list|)
block|{
name|this
argument_list|(
name|name
argument_list|,
name|type
argument_list|,
name|width
argument_list|,
literal|1
argument_list|,
name|length
argument_list|,
name|flags
argument_list|)
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
parameter_list|,
name|Flag
modifier|...
name|flags
parameter_list|)
block|{
name|this
argument_list|(
name|name
argument_list|,
name|type
argument_list|,
name|width
argument_list|,
literal|0
argument_list|,
name|flags
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
name|int
name|getHeight
parameter_list|()
block|{
return|return
name|iHeight
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
name|boolean
name|isEditable
parameter_list|()
block|{
return|return
operator|!
name|Flag
operator|.
name|READ_ONLY
operator|.
name|has
argument_list|(
name|iFlags
argument_list|)
return|;
block|}
specifier|public
name|boolean
name|isVisible
parameter_list|()
block|{
return|return
operator|!
name|Flag
operator|.
name|HIDDEN
operator|.
name|has
argument_list|(
name|iFlags
argument_list|)
return|;
block|}
specifier|public
name|boolean
name|isUnique
parameter_list|()
block|{
return|return
name|Flag
operator|.
name|UNIQUE
operator|.
name|has
argument_list|(
name|iFlags
argument_list|)
return|;
block|}
specifier|public
name|boolean
name|isNotEmpty
parameter_list|()
block|{
return|return
name|Flag
operator|.
name|NOT_EMPTY
operator|.
name|has
argument_list|(
name|iFlags
argument_list|)
return|;
block|}
specifier|public
name|boolean
name|isParentNotEmpty
parameter_list|()
block|{
return|return
name|Flag
operator|.
name|PARENT_NOT_EMPTY
operator|.
name|has
argument_list|(
name|iFlags
argument_list|)
return|;
block|}
specifier|public
name|boolean
name|isAllowFloatingPoint
parameter_list|()
block|{
return|return
name|Flag
operator|.
name|FLOAT
operator|.
name|has
argument_list|(
name|iFlags
argument_list|)
return|;
block|}
specifier|public
name|boolean
name|isAllowNegative
parameter_list|()
block|{
return|return
name|Flag
operator|.
name|NEGATIVE
operator|.
name|has
argument_list|(
name|iFlags
argument_list|)
return|;
block|}
specifier|public
name|boolean
name|isShowParentWhenEmpty
parameter_list|()
block|{
return|return
name|Flag
operator|.
name|SHOW_PARENT_IF_EMPTY
operator|.
name|has
argument_list|(
name|iFlags
argument_list|)
return|;
block|}
specifier|public
name|boolean
name|isCheckedByDefault
parameter_list|()
block|{
return|return
name|Flag
operator|.
name|DEFAULT_CHECKED
operator|.
name|has
argument_list|(
name|iFlags
argument_list|)
return|;
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
specifier|public
specifier|static
class|class
name|PageName
implements|implements
name|IsSerializable
implements|,
name|GwtRpcResponse
block|{
specifier|private
name|String
name|iSingular
init|=
literal|null
decl_stmt|,
name|iPlural
init|=
literal|null
decl_stmt|;
specifier|public
name|PageName
parameter_list|()
block|{
block|}
specifier|public
name|PageName
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|iSingular
operator|=
name|name
expr_stmt|;
block|}
specifier|public
name|PageName
parameter_list|(
name|String
name|singularName
parameter_list|,
name|String
name|pluralName
parameter_list|)
block|{
name|iSingular
operator|=
name|singularName
expr_stmt|;
name|iPlural
operator|=
name|pluralName
expr_stmt|;
block|}
specifier|public
name|String
name|singular
parameter_list|()
block|{
return|return
name|iSingular
return|;
block|}
specifier|public
name|String
name|plural
parameter_list|()
block|{
return|return
name|iPlural
operator|==
literal|null
condition|?
name|iSingular
operator|+
literal|"s"
else|:
name|iPlural
return|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|plural
argument_list|()
return|;
block|}
block|}
specifier|public
specifier|static
specifier|abstract
class|class
name|SimpleEditRpcRequest
implements|implements
name|IsSerializable
block|{
specifier|private
name|String
name|iType
decl_stmt|;
specifier|public
name|SimpleEditRpcRequest
parameter_list|()
block|{
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
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|getType
argument_list|()
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|GetPageNameRpcRequest
extends|extends
name|SimpleEditRpcRequest
implements|implements
name|GwtRpcRequest
argument_list|<
name|PageName
argument_list|>
block|{
specifier|public
name|GetPageNameRpcRequest
parameter_list|()
block|{
block|}
specifier|public
specifier|static
name|GetPageNameRpcRequest
name|getPageName
parameter_list|(
name|String
name|type
parameter_list|)
block|{
name|GetPageNameRpcRequest
name|request
init|=
operator|new
name|GetPageNameRpcRequest
argument_list|()
decl_stmt|;
name|request
operator|.
name|setType
argument_list|(
name|type
argument_list|)
expr_stmt|;
return|return
name|request
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|LoadDataRpcRequest
extends|extends
name|SimpleEditRpcRequest
implements|implements
name|GwtRpcRequest
argument_list|<
name|SimpleEditInterface
argument_list|>
block|{
specifier|public
name|LoadDataRpcRequest
parameter_list|()
block|{
block|}
specifier|public
specifier|static
name|LoadDataRpcRequest
name|loadData
parameter_list|(
name|String
name|type
parameter_list|)
block|{
name|LoadDataRpcRequest
name|request
init|=
operator|new
name|LoadDataRpcRequest
argument_list|()
decl_stmt|;
name|request
operator|.
name|setType
argument_list|(
name|type
argument_list|)
expr_stmt|;
return|return
name|request
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|SaveDataRpcRequest
extends|extends
name|SimpleEditRpcRequest
implements|implements
name|GwtRpcRequest
argument_list|<
name|SimpleEditInterface
argument_list|>
block|{
specifier|private
name|SimpleEditInterface
name|iData
decl_stmt|;
specifier|public
name|SaveDataRpcRequest
parameter_list|()
block|{
block|}
specifier|public
name|SimpleEditInterface
name|getData
parameter_list|()
block|{
return|return
name|iData
return|;
block|}
specifier|public
name|void
name|setData
parameter_list|(
name|SimpleEditInterface
name|data
parameter_list|)
block|{
name|iData
operator|=
name|data
expr_stmt|;
block|}
specifier|public
specifier|static
name|SaveDataRpcRequest
name|saveData
parameter_list|(
name|String
name|type
parameter_list|,
name|SimpleEditInterface
name|data
parameter_list|)
block|{
name|SaveDataRpcRequest
name|request
init|=
operator|new
name|SaveDataRpcRequest
argument_list|()
decl_stmt|;
name|request
operator|.
name|setType
argument_list|(
name|type
argument_list|)
expr_stmt|;
name|request
operator|.
name|setData
argument_list|(
name|data
argument_list|)
expr_stmt|;
return|return
name|request
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|SaveRecordRpcRequest
extends|extends
name|SimpleEditRpcRequest
implements|implements
name|GwtRpcRequest
argument_list|<
name|Record
argument_list|>
block|{
specifier|private
name|Record
name|iRecord
decl_stmt|;
specifier|public
name|SaveRecordRpcRequest
parameter_list|()
block|{
block|}
specifier|public
name|Record
name|getRecord
parameter_list|()
block|{
return|return
name|iRecord
return|;
block|}
specifier|public
name|void
name|setRecord
parameter_list|(
name|Record
name|record
parameter_list|)
block|{
name|iRecord
operator|=
name|record
expr_stmt|;
block|}
specifier|public
specifier|static
name|SaveRecordRpcRequest
name|saveRecord
parameter_list|(
name|String
name|type
parameter_list|,
name|Record
name|record
parameter_list|)
block|{
name|SaveRecordRpcRequest
name|request
init|=
operator|new
name|SaveRecordRpcRequest
argument_list|()
decl_stmt|;
name|request
operator|.
name|setType
argument_list|(
name|type
argument_list|)
expr_stmt|;
name|request
operator|.
name|setRecord
argument_list|(
name|record
argument_list|)
expr_stmt|;
return|return
name|request
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|DeleteRecordRpcRequest
extends|extends
name|SaveRecordRpcRequest
block|{
specifier|public
name|DeleteRecordRpcRequest
parameter_list|()
block|{
block|}
specifier|public
specifier|static
name|DeleteRecordRpcRequest
name|deleteRecord
parameter_list|(
name|String
name|type
parameter_list|,
name|Record
name|record
parameter_list|)
block|{
name|DeleteRecordRpcRequest
name|request
init|=
operator|new
name|DeleteRecordRpcRequest
argument_list|()
decl_stmt|;
name|request
operator|.
name|setType
argument_list|(
name|type
argument_list|)
expr_stmt|;
name|request
operator|.
name|setRecord
argument_list|(
name|record
argument_list|)
expr_stmt|;
return|return
name|request
return|;
block|}
block|}
block|}
end_class

end_unit

