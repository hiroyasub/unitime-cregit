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
name|export
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|PrintWriter
import|;
end_import

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
name|HashMap
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
name|List
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
name|export
operator|.
name|Exporter
operator|.
name|Printer
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|gson
operator|.
name|Gson
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|gson
operator|.
name|GsonBuilder
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|JSONPrinter
implements|implements
name|Printer
block|{
specifier|private
name|PrintWriter
name|iOut
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|Integer
argument_list|>
name|iHiddenColumns
init|=
operator|new
name|HashSet
argument_list|<
name|Integer
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|String
index|[]
name|iHeader
init|=
literal|null
decl_stmt|;
specifier|private
name|List
argument_list|<
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
argument_list|>
name|iList
init|=
operator|new
name|ArrayList
argument_list|<
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
specifier|public
name|JSONPrinter
parameter_list|(
name|PrintWriter
name|writer
parameter_list|)
block|{
name|iOut
operator|=
name|writer
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getContentType
parameter_list|()
block|{
return|return
literal|"application/json"
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|hideColumn
parameter_list|(
name|int
name|col
parameter_list|)
block|{
name|iHiddenColumns
operator|.
name|add
argument_list|(
name|col
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|printHeader
parameter_list|(
name|String
modifier|...
name|fields
parameter_list|)
block|{
name|iHeader
operator|=
name|fields
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|printLine
parameter_list|(
name|String
modifier|...
name|fields
parameter_list|)
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|entry
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|idx
init|=
literal|0
init|;
name|idx
operator|<
name|fields
operator|.
name|length
condition|;
name|idx
operator|++
control|)
block|{
if|if
condition|(
name|iHiddenColumns
operator|.
name|contains
argument_list|(
name|idx
argument_list|)
condition|)
continue|continue;
name|String
name|f
init|=
name|fields
index|[
name|idx
index|]
decl_stmt|;
name|String
name|h
init|=
name|iHeader
index|[
name|idx
index|]
decl_stmt|;
if|if
condition|(
name|f
operator|==
literal|null
condition|)
continue|continue;
try|try
block|{
name|entry
operator|.
name|put
argument_list|(
name|h
argument_list|,
name|Integer
operator|.
name|parseInt
argument_list|(
name|f
argument_list|)
argument_list|)
expr_stmt|;
continue|continue;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|e
parameter_list|)
block|{
block|}
try|try
block|{
name|entry
operator|.
name|put
argument_list|(
name|h
argument_list|,
name|Double
operator|.
name|parseDouble
argument_list|(
name|f
argument_list|)
argument_list|)
expr_stmt|;
continue|continue;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|e
parameter_list|)
block|{
block|}
if|if
condition|(
literal|"true"
operator|.
name|equals
argument_list|(
name|f
argument_list|)
condition|)
name|entry
operator|.
name|put
argument_list|(
name|h
argument_list|,
name|Boolean
operator|.
name|TRUE
argument_list|)
expr_stmt|;
if|else if
condition|(
literal|"false"
operator|.
name|equals
argument_list|(
name|f
argument_list|)
condition|)
name|entry
operator|.
name|put
argument_list|(
name|h
argument_list|,
name|Boolean
operator|.
name|FALSE
argument_list|)
expr_stmt|;
else|else
name|entry
operator|.
name|put
argument_list|(
name|h
argument_list|,
name|f
argument_list|)
expr_stmt|;
block|}
name|iList
operator|.
name|add
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|Gson
name|createGson
parameter_list|()
block|{
return|return
operator|new
name|GsonBuilder
argument_list|()
operator|.
name|setPrettyPrinting
argument_list|()
operator|.
name|create
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|flush
parameter_list|()
block|{
name|iOut
operator|.
name|print
argument_list|(
name|createGson
argument_list|()
operator|.
name|toJson
argument_list|(
name|iList
argument_list|)
argument_list|)
expr_stmt|;
name|iOut
operator|.
name|flush
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|close
parameter_list|()
block|{
block|}
block|}
end_class

end_unit

