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
name|GwtRpcResponseBoolean
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
name|GwtRpcResponseList
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
name|GwtRpcResponseLong
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
name|GwtRpcResponseNull
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
comment|/**  * @author Stephanie Schluttenhofer  */
end_comment

begin_class
specifier|public
class|class
name|PointInTimeDataReportsInterface
implements|implements
name|IsSerializable
block|{
specifier|public
specifier|static
class|class
name|Flag
implements|implements
name|IsSerializable
block|{
specifier|private
name|int
name|iValue
decl_stmt|;
specifier|private
name|String
name|iText
decl_stmt|;
specifier|public
name|Flag
parameter_list|()
block|{
block|}
specifier|public
name|int
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
name|int
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
name|getText
parameter_list|()
block|{
return|return
name|iText
return|;
block|}
specifier|public
name|void
name|setText
parameter_list|(
name|String
name|text
parameter_list|)
block|{
name|iText
operator|=
name|text
expr_stmt|;
block|}
block|}
specifier|public
specifier|static
class|class
name|IdValue
implements|implements
name|IsSerializable
implements|,
name|Comparable
argument_list|<
name|IdValue
argument_list|>
block|{
specifier|private
name|String
name|iValue
decl_stmt|,
name|iText
decl_stmt|;
specifier|public
name|IdValue
parameter_list|()
block|{
block|}
specifier|public
name|IdValue
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
name|getText
parameter_list|()
block|{
return|return
name|iText
return|;
block|}
specifier|public
name|void
name|setText
parameter_list|(
name|String
name|text
parameter_list|)
block|{
name|iText
operator|=
name|text
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|compareTo
parameter_list|(
name|IdValue
name|o
parameter_list|)
block|{
return|return
name|getText
argument_list|()
operator|.
name|compareTo
argument_list|(
name|o
operator|.
name|getText
argument_list|()
argument_list|)
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
name|getValue
argument_list|()
operator|+
literal|": "
operator|+
name|getText
argument_list|()
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|Parameter
implements|implements
name|IsSerializable
block|{
specifier|private
name|String
name|iType
decl_stmt|,
name|iName
decl_stmt|;
specifier|private
name|List
argument_list|<
name|IdValue
argument_list|>
name|iValues
init|=
operator|new
name|ArrayList
argument_list|<
name|IdValue
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|boolean
name|iMultiSelect
decl_stmt|;
specifier|private
name|boolean
name|iTextField
decl_stmt|;
specifier|private
name|String
name|iDefaultTextValue
decl_stmt|;
specifier|public
name|Parameter
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
name|List
argument_list|<
name|IdValue
argument_list|>
name|values
parameter_list|()
block|{
return|return
name|iValues
return|;
block|}
specifier|public
name|boolean
name|isMultiSelect
parameter_list|()
block|{
return|return
name|iMultiSelect
return|;
block|}
specifier|public
name|void
name|setMultiSelect
parameter_list|(
name|boolean
name|multiSelect
parameter_list|)
block|{
name|iMultiSelect
operator|=
name|multiSelect
expr_stmt|;
block|}
specifier|public
name|boolean
name|isTextField
parameter_list|()
block|{
return|return
name|iTextField
return|;
block|}
specifier|public
name|void
name|setTextField
parameter_list|(
name|boolean
name|textField
parameter_list|)
block|{
name|this
operator|.
name|iTextField
operator|=
name|textField
expr_stmt|;
block|}
specifier|public
name|String
name|getDefaultTextValue
parameter_list|()
block|{
return|return
name|iDefaultTextValue
return|;
block|}
specifier|public
name|void
name|setDefaultTextValue
parameter_list|(
name|String
name|defaultValue
parameter_list|)
block|{
name|this
operator|.
name|iDefaultTextValue
operator|=
name|defaultValue
expr_stmt|;
block|}
block|}
specifier|public
specifier|static
class|class
name|Report
implements|implements
name|GwtRpcResponse
block|{
specifier|private
name|String
name|iId
init|=
literal|null
decl_stmt|;
specifier|private
name|String
name|iName
decl_stmt|,
name|iDescription
decl_stmt|;
specifier|private
name|List
argument_list|<
name|Parameter
argument_list|>
name|iParameters
init|=
operator|new
name|ArrayList
argument_list|<
name|Parameter
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|int
name|iFlags
init|=
literal|0
decl_stmt|;
specifier|public
name|Report
parameter_list|()
block|{
block|}
specifier|public
name|String
name|getId
parameter_list|()
block|{
return|return
name|iId
return|;
block|}
specifier|public
name|void
name|setId
parameter_list|(
name|String
name|id
parameter_list|)
block|{
name|iId
operator|=
name|id
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
name|getDescription
parameter_list|()
block|{
return|return
name|iDescription
operator|==
literal|null
condition|?
literal|""
else|:
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
name|int
name|getFlags
parameter_list|()
block|{
return|return
name|iFlags
return|;
block|}
specifier|public
name|void
name|setFlags
parameter_list|(
name|int
name|flags
parameter_list|)
block|{
name|iFlags
operator|=
name|flags
expr_stmt|;
block|}
specifier|public
name|void
name|addParameter
parameter_list|(
name|Parameter
name|parameter
parameter_list|)
block|{
name|iParameters
operator|.
name|add
argument_list|(
name|parameter
argument_list|)
expr_stmt|;
block|}
specifier|public
name|List
argument_list|<
name|Parameter
argument_list|>
name|getParameters
parameter_list|()
block|{
return|return
name|iParameters
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
name|getName
argument_list|()
return|;
block|}
specifier|public
name|boolean
name|parametersContain
parameter_list|(
name|String
name|type
parameter_list|)
block|{
if|if
condition|(
name|type
operator|==
literal|null
operator|||
name|type
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
operator|(
literal|false
operator|)
return|;
block|}
name|boolean
name|found
init|=
literal|false
decl_stmt|;
for|for
control|(
name|Parameter
name|param
range|:
name|getParameters
argument_list|()
control|)
block|{
if|if
condition|(
name|type
operator|.
name|equals
argument_list|(
name|param
operator|.
name|getType
argument_list|()
argument_list|)
condition|)
block|{
name|found
operator|=
literal|true
expr_stmt|;
break|break;
block|}
block|}
return|return
operator|(
name|found
operator|)
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|PITDParametersInterface
implements|implements
name|GwtRpcResponse
block|{
specifier|private
name|List
argument_list|<
name|Flag
argument_list|>
name|iFlags
init|=
operator|new
name|ArrayList
argument_list|<
name|Flag
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|List
argument_list|<
name|Parameter
argument_list|>
name|iParameters
init|=
operator|new
name|ArrayList
argument_list|<
name|Parameter
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|boolean
name|iEditable
init|=
literal|false
decl_stmt|;
specifier|public
name|PITDParametersInterface
parameter_list|()
block|{
block|}
specifier|public
name|void
name|addFlag
parameter_list|(
name|Flag
name|flag
parameter_list|)
block|{
name|iFlags
operator|.
name|add
argument_list|(
name|flag
argument_list|)
expr_stmt|;
block|}
specifier|public
name|List
argument_list|<
name|Flag
argument_list|>
name|getFlags
parameter_list|()
block|{
return|return
name|iFlags
return|;
block|}
specifier|public
name|void
name|addParameter
parameter_list|(
name|Parameter
name|parameter
parameter_list|)
block|{
name|iParameters
operator|.
name|add
argument_list|(
name|parameter
argument_list|)
expr_stmt|;
block|}
specifier|public
name|List
argument_list|<
name|Parameter
argument_list|>
name|getParameters
parameter_list|()
block|{
return|return
name|iParameters
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
name|isEditable
parameter_list|()
block|{
return|return
name|iEditable
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|PITDParametersRpcRequest
implements|implements
name|GwtRpcRequest
argument_list|<
name|PITDParametersInterface
argument_list|>
block|{
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
literal|null
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|PITDQueriesRpcRequest
implements|implements
name|GwtRpcRequest
argument_list|<
name|GwtRpcResponseList
argument_list|<
name|Report
argument_list|>
argument_list|>
block|{
specifier|public
name|PITDQueriesRpcRequest
parameter_list|()
block|{
block|}
block|}
specifier|public
specifier|static
class|class
name|Table
implements|implements
name|GwtRpcResponse
block|{
specifier|private
name|List
argument_list|<
name|String
index|[]
argument_list|>
name|iData
init|=
operator|new
name|ArrayList
argument_list|<
name|String
index|[]
argument_list|>
argument_list|()
decl_stmt|;
specifier|public
name|Table
parameter_list|()
block|{
block|}
specifier|public
name|void
name|add
parameter_list|(
name|String
modifier|...
name|line
parameter_list|)
block|{
name|iData
operator|.
name|add
argument_list|(
name|line
argument_list|)
expr_stmt|;
block|}
specifier|public
name|int
name|size
parameter_list|()
block|{
return|return
name|iData
operator|.
name|size
argument_list|()
return|;
block|}
specifier|public
name|String
index|[]
name|get
parameter_list|(
name|int
name|row
parameter_list|)
block|{
return|return
name|iData
operator|.
name|get
argument_list|(
name|row
argument_list|)
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|PITDExecuteRpcRequest
implements|implements
name|GwtRpcRequest
argument_list|<
name|Table
argument_list|>
block|{
specifier|private
name|Report
name|iReport
decl_stmt|;
specifier|private
name|List
argument_list|<
name|IdValue
argument_list|>
name|iParameters
init|=
operator|new
name|ArrayList
argument_list|<
name|IdValue
argument_list|>
argument_list|()
decl_stmt|;
specifier|public
name|PITDExecuteRpcRequest
parameter_list|()
block|{
block|}
specifier|public
name|void
name|setReport
parameter_list|(
name|Report
name|report
parameter_list|)
block|{
name|iReport
operator|=
name|report
expr_stmt|;
block|}
specifier|public
name|Report
name|getReport
parameter_list|()
block|{
return|return
name|iReport
return|;
block|}
specifier|public
name|void
name|addParameter
parameter_list|(
name|String
name|value
parameter_list|,
name|String
name|text
parameter_list|)
block|{
name|iParameters
operator|.
name|add
argument_list|(
operator|new
name|IdValue
argument_list|(
name|value
argument_list|,
name|text
argument_list|)
argument_list|)
expr_stmt|;
block|}
specifier|public
name|List
argument_list|<
name|IdValue
argument_list|>
name|getParameters
parameter_list|()
block|{
return|return
name|iParameters
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
name|iReport
operator|.
name|getName
argument_list|()
operator|+
literal|" {options: "
operator|+
name|getParameters
argument_list|()
operator|+
literal|"}"
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|PITDStoreRpcRequest
extends|extends
name|Report
implements|implements
name|GwtRpcRequest
argument_list|<
name|GwtRpcResponseLong
argument_list|>
block|{
specifier|public
name|PITDStoreRpcRequest
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
specifier|public
name|PITDStoreRpcRequest
parameter_list|(
name|Report
name|query
parameter_list|)
block|{
name|super
argument_list|()
expr_stmt|;
name|setId
argument_list|(
name|query
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
name|setName
argument_list|(
name|query
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|setDescription
argument_list|(
name|query
operator|.
name|getDescription
argument_list|()
argument_list|)
expr_stmt|;
name|setFlags
argument_list|(
name|query
operator|.
name|getFlags
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
specifier|static
class|class
name|PITDDeleteRpcRequest
implements|implements
name|GwtRpcRequest
argument_list|<
name|GwtRpcResponseBoolean
argument_list|>
block|{
specifier|private
name|Long
name|iId
init|=
literal|null
decl_stmt|;
specifier|public
name|PITDDeleteRpcRequest
parameter_list|()
block|{
block|}
specifier|public
name|PITDDeleteRpcRequest
parameter_list|(
name|Long
name|id
parameter_list|)
block|{
name|iId
operator|=
name|id
expr_stmt|;
block|}
specifier|public
name|Long
name|getId
parameter_list|()
block|{
return|return
name|iId
return|;
block|}
specifier|public
name|void
name|setId
parameter_list|(
name|Long
name|id
parameter_list|)
block|{
name|iId
operator|=
name|id
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
name|iId
operator|==
literal|null
condition|?
literal|"null"
else|:
name|iId
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|PITDSetBackRpcRequest
implements|implements
name|GwtRpcRequest
argument_list|<
name|GwtRpcResponseNull
argument_list|>
block|{
specifier|private
name|String
name|iHistory
decl_stmt|;
specifier|private
name|List
argument_list|<
name|Long
argument_list|>
name|iIds
init|=
operator|new
name|ArrayList
argument_list|<
name|Long
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|String
name|iType
decl_stmt|;
specifier|public
name|PITDSetBackRpcRequest
parameter_list|()
block|{
block|}
specifier|public
name|String
name|getHistory
parameter_list|()
block|{
return|return
name|iHistory
return|;
block|}
specifier|public
name|void
name|setHistory
parameter_list|(
name|String
name|history
parameter_list|)
block|{
name|iHistory
operator|=
name|history
expr_stmt|;
block|}
specifier|public
name|void
name|addId
parameter_list|(
name|Long
name|id
parameter_list|)
block|{
if|if
condition|(
operator|!
name|iIds
operator|.
name|contains
argument_list|(
name|id
argument_list|)
condition|)
name|iIds
operator|.
name|add
argument_list|(
name|id
argument_list|)
expr_stmt|;
block|}
specifier|public
name|List
argument_list|<
name|Long
argument_list|>
name|getIds
parameter_list|()
block|{
return|return
name|iIds
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
name|getType
parameter_list|()
block|{
return|return
name|iType
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
literal|"courses"
operator|+
literal|"#"
operator|+
name|getHistory
argument_list|()
return|;
block|}
block|}
block|}
end_class

end_unit

