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
operator|.
name|solver
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
name|gwt
operator|.
name|command
operator|.
name|server
operator|.
name|GwtRpcImplementation
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
name|TableInterface
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
name|CourseTimetablingSolverInterface
operator|.
name|SolverReportsRequest
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
name|CourseTimetablingSolverInterface
operator|.
name|SolverReportsResponse
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|Service
argument_list|(
literal|"org.unitime.timetable.export.Exporter:solution-reports.csv"
argument_list|)
specifier|public
class|class
name|ExportSolutionReportsCSV
extends|extends
name|TableExporter
block|{
annotation|@
name|Autowired
specifier|private
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
literal|"solution-reports.csv"
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
name|tableId
init|=
name|helper
operator|.
name|getParameter
argument_list|(
literal|"table"
argument_list|)
decl_stmt|;
name|GwtRpcImplementation
argument_list|<
name|SolverReportsRequest
argument_list|,
name|SolverReportsResponse
argument_list|>
name|service
init|=
operator|(
name|GwtRpcImplementation
argument_list|<
name|SolverReportsRequest
argument_list|,
name|SolverReportsResponse
argument_list|>
operator|)
name|applicationContext
operator|.
name|getBean
argument_list|(
name|SolverReportsRequest
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
name|SolverReportsRequest
name|request
init|=
operator|new
name|SolverReportsRequest
argument_list|()
decl_stmt|;
name|SolverReportsResponse
name|response
init|=
name|service
operator|.
name|execute
argument_list|(
name|request
argument_list|,
name|helper
operator|.
name|getSessionContext
argument_list|()
argument_list|)
decl_stmt|;
name|Printer
name|out
init|=
operator|new
name|CSVPrinter
argument_list|(
name|helper
operator|.
name|getWriter
argument_list|()
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|helper
operator|.
name|setup
argument_list|(
name|out
operator|.
name|getContentType
argument_list|()
argument_list|,
name|reference
argument_list|()
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|boolean
name|first
init|=
literal|true
decl_stmt|;
if|if
condition|(
name|response
operator|.
name|hasTables
argument_list|()
condition|)
block|{
for|for
control|(
name|TableInterface
name|table
range|:
name|response
operator|.
name|getTables
argument_list|()
control|)
block|{
if|if
condition|(
name|tableId
operator|!=
literal|null
operator|&&
operator|!
name|tableId
operator|.
name|equals
argument_list|(
name|table
operator|.
name|getTableId
argument_list|()
argument_list|)
condition|)
continue|continue;
if|if
condition|(
operator|!
name|first
condition|)
block|{
name|out
operator|.
name|printLine
argument_list|()
expr_stmt|;
name|out
operator|.
name|printLine
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|first
operator|=
literal|false
expr_stmt|;
block|}
if|if
condition|(
name|table
operator|.
name|hasName
argument_list|()
condition|)
name|out
operator|.
name|printLine
argument_list|(
name|table
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|sort
argument_list|(
name|table
argument_list|,
name|helper
argument_list|)
expr_stmt|;
name|String
index|[]
name|header
init|=
operator|new
name|String
index|[
name|table
operator|.
name|getHeader
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
name|table
operator|.
name|getHeader
argument_list|()
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|header
index|[
name|i
index|]
operator|=
name|table
operator|.
name|getHeader
argument_list|(
name|i
argument_list|)
operator|.
name|getName
argument_list|()
operator|.
name|replace
argument_list|(
literal|"<br>"
argument_list|,
literal|"\n"
argument_list|)
expr_stmt|;
block|}
name|out
operator|.
name|printHeader
argument_list|(
name|header
argument_list|)
expr_stmt|;
name|out
operator|.
name|flush
argument_list|()
expr_stmt|;
for|for
control|(
name|TableInterface
operator|.
name|TableRowInterface
name|row
range|:
name|table
operator|.
name|getRows
argument_list|()
control|)
block|{
name|String
index|[]
name|line
init|=
operator|new
name|String
index|[
name|row
operator|.
name|getNrCells
argument_list|()
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
name|row
operator|.
name|getNrCells
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|line
index|[
name|i
index|]
operator|=
name|convertCSV
argument_list|(
name|row
operator|.
name|getCell
argument_list|(
name|i
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|out
operator|.
name|printLine
argument_list|(
name|line
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|table
operator|.
name|hasColumnDescriptions
argument_list|()
condition|)
block|{
name|out
operator|.
name|printLine
argument_list|()
expr_stmt|;
for|for
control|(
name|TableInterface
operator|.
name|TableHeaderIterface
name|h
range|:
name|table
operator|.
name|getHeader
argument_list|()
control|)
if|if
condition|(
name|h
operator|.
name|hasDescription
argument_list|()
condition|)
name|out
operator|.
name|printLine
argument_list|(
name|h
operator|.
name|getName
argument_list|()
argument_list|,
name|h
operator|.
name|getDescription
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|out
operator|.
name|flush
argument_list|()
expr_stmt|;
block|}
block|}
name|out
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit
