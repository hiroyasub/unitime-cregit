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
name|server
operator|.
name|admin
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collections
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
name|org
operator|.
name|springframework
operator|.
name|beans
operator|.
name|factory
operator|.
name|annotation
operator|.
name|Autowired
import|;
end_import

begin_import
import|import
name|org
operator|.
name|springframework
operator|.
name|context
operator|.
name|ApplicationContext
import|;
end_import

begin_import
import|import
name|org
operator|.
name|springframework
operator|.
name|stereotype
operator|.
name|Service
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|localization
operator|.
name|impl
operator|.
name|Localization
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
name|export
operator|.
name|CSVPrinter
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
name|export
operator|.
name|ExportHelper
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
name|export
operator|.
name|Exporter
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
name|server
operator|.
name|GwtRpcServlet
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
name|resources
operator|.
name|GwtConstants
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
name|resources
operator|.
name|GwtMessages
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
name|shared
operator|.
name|SimpleEditInterface
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
name|shared
operator|.
name|UserDataInterface
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
name|shared
operator|.
name|SimpleEditInterface
operator|.
name|Field
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
name|shared
operator|.
name|SimpleEditInterface
operator|.
name|FieldType
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
name|shared
operator|.
name|SimpleEditInterface
operator|.
name|ListItem
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
name|shared
operator|.
name|SimpleEditInterface
operator|.
name|LoadDataRpcRequest
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
name|shared
operator|.
name|SimpleEditInterface
operator|.
name|Record
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
name|shared
operator|.
name|UserDataInterface
operator|.
name|GetUserDataRpcRequest
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|Service
argument_list|(
literal|"org.unitime.timetable.export.Exporter:admin-report.csv"
argument_list|)
specifier|public
class|class
name|AdminExportToCSV
implements|implements
name|Exporter
block|{
specifier|protected
specifier|static
name|GwtMessages
name|MESSAGES
init|=
name|Localization
operator|.
name|create
argument_list|(
name|GwtMessages
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|protected
specifier|static
name|GwtConstants
name|CONSTANTS
init|=
name|Localization
operator|.
name|create
argument_list|(
name|GwtConstants
operator|.
name|class
argument_list|)
decl_stmt|;
annotation|@
name|Autowired
name|ApplicationContext
name|applicationContext
decl_stmt|;
annotation|@
name|Override
specifier|public
name|String
name|reference
parameter_list|()
block|{
return|return
literal|"admin-report.csv"
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|export
parameter_list|(
name|ExportHelper
name|helper
parameter_list|)
throws|throws
name|IOException
block|{
name|String
name|type
init|=
name|helper
operator|.
name|getParameter
argument_list|(
literal|"type"
argument_list|)
decl_stmt|;
if|if
condition|(
name|type
operator|==
literal|null
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Admin page type not provided, please set the type parameter."
argument_list|)
throw|;
name|String
index|[]
name|filter
init|=
name|helper
operator|.
name|getParameterValues
argument_list|(
literal|"filter"
argument_list|)
decl_stmt|;
name|LoadDataRpcRequest
name|request
init|=
operator|new
name|LoadDataRpcRequest
argument_list|()
decl_stmt|;
name|request
operator|.
name|setFilter
argument_list|(
name|filter
argument_list|)
expr_stmt|;
name|request
operator|.
name|setType
argument_list|(
name|type
argument_list|)
expr_stmt|;
name|SimpleEditInterface
name|data
init|=
name|GwtRpcServlet
operator|.
name|execute
argument_list|(
name|request
argument_list|,
name|applicationContext
argument_list|,
name|helper
operator|.
name|getSessionContext
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|data
operator|==
literal|null
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"No data returned."
argument_list|)
throw|;
specifier|final
name|Comparator
argument_list|<
name|Record
argument_list|>
name|cmp
init|=
name|data
operator|.
name|getComparator
argument_list|()
decl_stmt|;
name|boolean
name|hasDetails
init|=
name|hasDetails
argument_list|(
name|data
argument_list|)
decl_stmt|;
name|String
name|hidden
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|data
operator|.
name|isSaveOrder
argument_list|()
condition|)
block|{
name|GetUserDataRpcRequest
name|ordRequest
init|=
operator|new
name|GetUserDataRpcRequest
argument_list|()
decl_stmt|;
name|ordRequest
operator|.
name|add
argument_list|(
literal|"SimpleEdit.Order["
operator|+
name|type
operator|+
literal|"]"
argument_list|)
expr_stmt|;
if|if
condition|(
name|hasDetails
condition|)
name|ordRequest
operator|.
name|add
argument_list|(
literal|"SimpleEdit.Open["
operator|+
name|type
operator|+
literal|"]"
argument_list|)
expr_stmt|;
name|ordRequest
operator|.
name|add
argument_list|(
literal|"SimpleEdit.Hidden["
operator|+
name|type
operator|+
literal|"]"
argument_list|)
expr_stmt|;
name|UserDataInterface
name|result
init|=
name|GwtRpcServlet
operator|.
name|execute
argument_list|(
name|ordRequest
argument_list|,
name|applicationContext
argument_list|,
name|helper
operator|.
name|getSessionContext
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|result
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|helper
operator|.
name|getParameter
argument_list|(
literal|"order"
argument_list|)
operator|!=
literal|null
condition|)
name|result
operator|.
name|put
argument_list|(
literal|"SimpleEdit.Order["
operator|+
name|type
operator|+
literal|"]"
argument_list|,
name|helper
operator|.
name|getParameter
argument_list|(
literal|"order"
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|helper
operator|.
name|getParameter
argument_list|(
literal|"open"
argument_list|)
operator|!=
literal|null
condition|)
name|result
operator|.
name|put
argument_list|(
literal|"SimpleEdit.Open["
operator|+
name|type
operator|+
literal|"]"
argument_list|,
name|helper
operator|.
name|getParameter
argument_list|(
literal|"open"
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|helper
operator|.
name|getParameter
argument_list|(
literal|"hidden"
argument_list|)
operator|!=
literal|null
condition|)
name|result
operator|.
name|put
argument_list|(
literal|"SimpleEdit.Hidden["
operator|+
name|type
operator|+
literal|"]"
argument_list|,
name|helper
operator|.
name|getParameter
argument_list|(
literal|"hidden"
argument_list|)
argument_list|)
expr_stmt|;
specifier|final
name|String
name|order
init|=
literal|"|"
operator|+
name|result
operator|.
name|get
argument_list|(
literal|"SimpleEdit.Order["
operator|+
name|type
operator|+
literal|"]"
argument_list|)
operator|+
literal|"|"
decl_stmt|;
if|if
condition|(
name|hasDetails
condition|)
block|{
name|String
name|open
init|=
literal|"|"
operator|+
name|result
operator|.
name|get
argument_list|(
literal|"SimpleEdit.Open["
operator|+
name|type
operator|+
literal|"]"
argument_list|)
operator|+
literal|"|"
decl_stmt|;
for|for
control|(
name|Record
name|r
range|:
name|data
operator|.
name|getRecords
argument_list|()
control|)
block|{
if|if
condition|(
name|isParent
argument_list|(
name|data
argument_list|,
name|r
argument_list|)
condition|)
name|r
operator|.
name|setField
argument_list|(
literal|0
argument_list|,
name|open
operator|.
name|indexOf
argument_list|(
literal|"|"
operator|+
name|r
operator|.
name|getUniqueId
argument_list|()
operator|+
literal|"|"
argument_list|)
operator|>=
literal|0
condition|?
literal|"-"
else|:
literal|"+"
argument_list|)
expr_stmt|;
block|}
block|}
name|Collections
operator|.
name|sort
argument_list|(
name|data
operator|.
name|getRecords
argument_list|()
argument_list|,
operator|new
name|Comparator
argument_list|<
name|Record
argument_list|>
argument_list|()
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
name|data
operator|.
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
name|data
operator|.
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
name|data
operator|.
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
name|int
name|i1
init|=
operator|(
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
name|order
operator|.
name|indexOf
argument_list|(
literal|"|"
operator|+
name|r1
operator|.
name|getUniqueId
argument_list|()
operator|+
literal|"|"
argument_list|)
operator|)
decl_stmt|;
if|if
condition|(
name|i1
operator|>=
literal|0
condition|)
block|{
name|int
name|i2
init|=
operator|(
name|r2
operator|.
name|getUniqueId
argument_list|()
operator|==
literal|null
condition|?
operator|-
literal|1
else|:
name|order
operator|.
name|indexOf
argument_list|(
literal|"|"
operator|+
name|r2
operator|.
name|getUniqueId
argument_list|()
operator|+
literal|"|"
argument_list|)
operator|)
decl_stmt|;
if|if
condition|(
name|i2
operator|>=
literal|0
condition|)
block|{
return|return
operator|(
name|i1
operator|<
name|i2
condition|?
operator|-
literal|1
else|:
name|i1
operator|>
name|i2
condition|?
literal|1
else|:
name|cmp
operator|.
name|compare
argument_list|(
name|r1
argument_list|,
name|r2
argument_list|)
operator|)
return|;
block|}
block|}
return|return
name|cmp
operator|.
name|compare
argument_list|(
name|r1
argument_list|,
name|r2
argument_list|)
return|;
block|}
block|}
argument_list|)
expr_stmt|;
name|hidden
operator|=
literal|"|"
operator|+
name|result
operator|.
name|get
argument_list|(
literal|"SimpleEdit.Hidden["
operator|+
name|type
operator|+
literal|"]"
argument_list|)
operator|+
literal|"|"
expr_stmt|;
block|}
else|else
block|{
name|Collections
operator|.
name|sort
argument_list|(
name|data
operator|.
name|getRecords
argument_list|()
argument_list|,
name|cmp
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|Collections
operator|.
name|sort
argument_list|(
name|data
operator|.
name|getRecords
argument_list|()
argument_list|,
name|cmp
argument_list|)
expr_stmt|;
block|}
name|export
argument_list|(
name|data
argument_list|,
name|helper
argument_list|,
name|hidden
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|export
parameter_list|(
name|SimpleEditInterface
name|data
parameter_list|,
name|ExportHelper
name|helper
parameter_list|,
name|String
name|hidden
parameter_list|)
throws|throws
name|IOException
block|{
name|CSVPrinter
name|out
init|=
operator|new
name|CSVPrinter
argument_list|(
name|helper
argument_list|,
literal|false
argument_list|)
decl_stmt|;
try|try
block|{
name|helper
operator|.
name|setup
argument_list|(
name|out
operator|.
name|getContentType
argument_list|()
argument_list|,
name|helper
operator|.
name|getParameter
argument_list|(
literal|"type"
argument_list|)
operator|+
literal|".csv"
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|boolean
name|hasDetails
init|=
name|hasDetails
argument_list|(
name|data
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
name|data
operator|.
name|getFields
argument_list|()
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|boolean
name|visible
init|=
name|data
operator|.
name|getFields
argument_list|()
index|[
name|i
index|]
operator|.
name|isVisible
argument_list|()
operator|&&
operator|(
name|hidden
operator|==
literal|null
operator|||
operator|!
name|hidden
operator|.
name|contains
argument_list|(
literal|"|"
operator|+
name|data
operator|.
name|getFields
argument_list|()
index|[
name|i
index|]
operator|.
name|getName
argument_list|()
operator|+
literal|"|"
argument_list|)
operator|)
decl_stmt|;
if|if
condition|(
operator|!
name|visible
condition|)
name|out
operator|.
name|hideColumn
argument_list|(
name|i
argument_list|)
expr_stmt|;
block|}
name|String
index|[]
name|header
init|=
operator|new
name|String
index|[
name|data
operator|.
name|getFields
argument_list|()
operator|.
name|length
index|]
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
name|data
operator|.
name|getFields
argument_list|()
operator|.
name|length
condition|;
name|i
operator|++
control|)
name|header
index|[
name|i
index|]
operator|=
name|header
argument_list|(
name|data
argument_list|,
name|data
operator|.
name|getFields
argument_list|()
index|[
name|i
index|]
argument_list|)
expr_stmt|;
name|out
operator|.
name|printHeader
argument_list|(
name|header
argument_list|)
expr_stmt|;
name|boolean
name|visible
init|=
literal|true
decl_stmt|;
for|for
control|(
name|Record
name|r
range|:
name|data
operator|.
name|getRecords
argument_list|()
control|)
block|{
if|if
condition|(
name|hasDetails
condition|)
block|{
if|if
condition|(
literal|"-"
operator|.
name|equals
argument_list|(
name|r
operator|.
name|getField
argument_list|(
literal|0
argument_list|)
argument_list|)
condition|)
name|visible
operator|=
literal|true
expr_stmt|;
if|else if
condition|(
literal|"+"
operator|.
name|equals
argument_list|(
name|r
operator|.
name|getField
argument_list|(
literal|0
argument_list|)
argument_list|)
condition|)
name|visible
operator|=
literal|false
expr_stmt|;
if|else if
condition|(
operator|!
name|visible
condition|)
continue|continue;
block|}
name|String
index|[]
name|line
init|=
operator|new
name|String
index|[
name|data
operator|.
name|getFields
argument_list|()
operator|.
name|length
index|]
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
name|data
operator|.
name|getFields
argument_list|()
operator|.
name|length
condition|;
name|i
operator|++
control|)
name|line
index|[
name|i
index|]
operator|=
name|cell
argument_list|(
name|data
operator|.
name|getFields
argument_list|()
index|[
name|i
index|]
argument_list|,
name|r
argument_list|,
name|i
argument_list|)
expr_stmt|;
name|out
operator|.
name|printLine
argument_list|(
name|line
argument_list|)
expr_stmt|;
block|}
block|}
finally|finally
block|{
name|out
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
block|}
specifier|protected
name|String
name|header
parameter_list|(
name|SimpleEditInterface
name|data
parameter_list|,
name|Field
name|field
parameter_list|)
block|{
name|String
name|name
init|=
name|field
operator|.
name|getName
argument_list|()
decl_stmt|;
if|if
condition|(
name|hasDetails
argument_list|(
name|data
argument_list|)
operator|&&
name|name
operator|.
name|contains
argument_list|(
literal|"|"
argument_list|)
condition|)
name|name
operator|=
name|name
operator|.
name|replace
argument_list|(
literal|"|"
argument_list|,
literal|"\n  "
argument_list|)
expr_stmt|;
if|if
condition|(
literal|"&otimes;"
operator|.
name|equals
argument_list|(
name|name
argument_list|)
condition|)
name|name
operator|=
literal|"\u2297"
expr_stmt|;
return|return
name|name
return|;
block|}
specifier|public
specifier|static
name|String
name|slot2time
parameter_list|(
name|int
name|slot
parameter_list|)
block|{
if|if
condition|(
name|CONSTANTS
operator|.
name|useAmPm
argument_list|()
condition|)
block|{
if|if
condition|(
name|slot
operator|==
literal|0
condition|)
return|return
name|CONSTANTS
operator|.
name|timeMidnight
argument_list|()
return|;
if|if
condition|(
name|slot
operator|==
literal|144
condition|)
return|return
name|CONSTANTS
operator|.
name|timeNoon
argument_list|()
return|;
if|if
condition|(
name|slot
operator|==
literal|288
condition|)
return|return
name|CONSTANTS
operator|.
name|timeMidnightEnd
argument_list|()
return|;
block|}
name|int
name|h
init|=
name|slot
operator|/
literal|12
decl_stmt|;
name|int
name|m
init|=
literal|5
operator|*
operator|(
name|slot
operator|%
literal|12
operator|)
decl_stmt|;
if|if
condition|(
name|CONSTANTS
operator|.
name|useAmPm
argument_list|()
condition|)
return|return
operator|(
name|h
operator|>
literal|12
condition|?
name|h
operator|-
literal|12
else|:
name|h
operator|)
operator|+
literal|":"
operator|+
operator|(
name|m
operator|<
literal|10
condition|?
literal|"0"
else|:
literal|""
operator|)
operator|+
name|m
operator|+
literal|" "
operator|+
operator|(
name|h
operator|==
literal|24
condition|?
name|CONSTANTS
operator|.
name|timeAm
argument_list|()
else|:
name|h
operator|>=
literal|12
condition|?
name|CONSTANTS
operator|.
name|timePm
argument_list|()
else|:
name|CONSTANTS
operator|.
name|timeAm
argument_list|()
operator|)
return|;
else|else
return|return
name|h
operator|+
literal|":"
operator|+
operator|(
name|m
operator|<
literal|10
condition|?
literal|"0"
else|:
literal|""
operator|)
operator|+
name|m
return|;
block|}
specifier|protected
name|String
name|getValue
parameter_list|(
name|Field
name|field
parameter_list|,
name|Record
name|record
parameter_list|,
name|int
name|index
parameter_list|)
block|{
name|String
name|value
init|=
name|record
operator|.
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
name|field
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
name|field
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
name|field
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
name|record
operator|.
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
name|field
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
if|else if
condition|(
name|field
operator|.
name|getType
argument_list|()
operator|==
name|FieldType
operator|.
name|time
condition|)
block|{
if|if
condition|(
name|value
operator|==
literal|null
operator|||
name|value
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
literal|""
return|;
return|return
name|slot2time
argument_list|(
name|Integer
operator|.
name|valueOf
argument_list|(
name|value
argument_list|)
argument_list|)
return|;
block|}
return|return
name|value
return|;
block|}
specifier|protected
name|boolean
name|hasDetails
parameter_list|(
name|SimpleEditInterface
name|data
parameter_list|)
block|{
return|return
operator|(
name|data
operator|!=
literal|null
operator|&&
name|data
operator|.
name|getFields
argument_list|()
operator|.
name|length
operator|>
literal|0
operator|&&
name|data
operator|.
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
operator|)
return|;
block|}
specifier|protected
name|boolean
name|isParent
parameter_list|(
name|SimpleEditInterface
name|data
parameter_list|,
name|Record
name|r
parameter_list|)
block|{
return|return
name|hasDetails
argument_list|(
name|data
argument_list|)
operator|&&
operator|(
literal|"+"
operator|.
name|equals
argument_list|(
name|r
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
name|r
operator|.
name|getField
argument_list|(
literal|0
argument_list|)
argument_list|)
operator|)
return|;
block|}
specifier|protected
name|String
name|cell
parameter_list|(
name|Field
name|field
parameter_list|,
name|Record
name|record
parameter_list|,
name|int
name|index
parameter_list|)
block|{
name|String
name|value
init|=
name|getValue
argument_list|(
name|field
argument_list|,
name|record
argument_list|,
name|index
argument_list|)
decl_stmt|;
switch|switch
condition|(
name|field
operator|.
name|getType
argument_list|()
condition|)
block|{
case|case
name|parent
case|:
if|if
condition|(
operator|!
literal|"+"
operator|.
name|equals
argument_list|(
name|value
argument_list|)
operator|&&
operator|!
literal|"-"
operator|.
name|equals
argument_list|(
name|value
argument_list|)
condition|)
return|return
literal|""
return|;
return|return
name|value
return|;
case|case
name|toggle
case|:
if|if
condition|(
operator|!
name|value
operator|.
name|isEmpty
argument_list|()
operator|&&
operator|!
literal|"true"
operator|.
name|equals
argument_list|(
name|value
argument_list|)
operator|&&
operator|!
literal|"false"
operator|.
name|equals
argument_list|(
name|value
argument_list|)
condition|)
return|return
name|record
operator|.
name|getField
argument_list|(
name|index
argument_list|)
return|;
if|if
condition|(
operator|(
name|value
operator|.
name|isEmpty
argument_list|()
operator|&&
name|field
operator|.
name|isCheckedByDefault
argument_list|()
operator|)
operator|||
operator|(
operator|!
name|value
operator|.
name|isEmpty
argument_list|()
operator|&&
literal|"true"
operator|.
name|equalsIgnoreCase
argument_list|(
name|record
operator|.
name|getField
argument_list|(
name|index
argument_list|)
argument_list|)
operator|)
condition|)
return|return
name|MESSAGES
operator|.
name|exportTrue
argument_list|()
return|;
else|else
return|return
name|MESSAGES
operator|.
name|exportFalse
argument_list|()
return|;
case|case
name|person
case|:
name|String
index|[]
name|name
init|=
name|record
operator|.
name|getValues
argument_list|(
name|index
argument_list|)
decl_stmt|;
return|return
operator|(
name|name
operator|.
name|length
operator|<=
literal|2
condition|?
literal|"<i>"
operator|+
name|MESSAGES
operator|.
name|notSet
argument_list|()
operator|+
literal|"</i>"
else|:
name|name
operator|.
name|length
operator|>=
literal|6
operator|&&
operator|!
name|name
index|[
literal|6
index|]
operator|.
name|isEmpty
argument_list|()
condition|?
name|name
index|[
literal|6
index|]
else|:
name|name
index|[
literal|0
index|]
operator|+
literal|", "
operator|+
name|name
index|[
literal|1
index|]
operator|+
operator|(
name|name
index|[
literal|2
index|]
operator|.
name|isEmpty
argument_list|()
condition|?
literal|""
else|:
literal|" "
operator|+
name|name
index|[
literal|2
index|]
operator|)
operator|)
return|;
default|default:
return|return
name|value
return|;
block|}
block|}
block|}
end_class

end_unit

