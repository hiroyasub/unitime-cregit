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
name|java
operator|.
name|io
operator|.
name|PrintWriter
import|;
end_import

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|ifs
operator|.
name|util
operator|.
name|CSVFile
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
name|shared
operator|.
name|SolverInterface
operator|.
name|SolverType
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
name|security
operator|.
name|rights
operator|.
name|Right
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
name|solver
operator|.
name|CommonSolverInterface
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
name|solver
operator|.
name|SolverProxy
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|Service
argument_list|(
literal|"org.unitime.timetable.export.Exporter:solution.csv"
argument_list|)
specifier|public
class|class
name|ExportSolutionCSV
extends|extends
name|ExportSolutionXML
block|{
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
name|Override
specifier|public
name|String
name|reference
parameter_list|()
block|{
return|return
literal|"solution.csv"
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
name|t
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
name|t
operator|==
literal|null
operator|||
name|t
operator|.
name|isEmpty
argument_list|()
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Type parameter was not provided."
argument_list|)
throw|;
name|SolverType
name|type
init|=
literal|null
decl_stmt|;
try|try
block|{
name|type
operator|=
name|SolverType
operator|.
name|valueOf
argument_list|(
name|t
operator|.
name|toUpperCase
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Wrong solver type."
argument_list|)
throw|;
block|}
name|CommonSolverInterface
name|solver
init|=
name|getSolver
argument_list|(
name|type
argument_list|)
decl_stmt|;
if|if
condition|(
name|solver
operator|==
literal|null
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Solver is not started."
argument_list|)
throw|;
if|if
condition|(
name|solver
operator|.
name|isWorking
argument_list|()
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Solver is working, stop it first."
argument_list|)
throw|;
name|CSVFile
name|csv
init|=
literal|null
decl_stmt|;
switch|switch
condition|(
name|type
condition|)
block|{
case|case
name|COURSE
case|:
name|helper
operator|.
name|getSessionContext
argument_list|()
operator|.
name|checkPermission
argument_list|(
name|solver
operator|.
name|getProperties
argument_list|()
operator|.
name|getPropertyLongArry
argument_list|(
literal|"General.SolverGroupId"
argument_list|,
literal|null
argument_list|)
argument_list|,
literal|"SolverGroup"
argument_list|,
name|Right
operator|.
name|SolverSolutionExportCsv
argument_list|)
expr_stmt|;
name|csv
operator|=
operator|(
operator|(
name|SolverProxy
operator|)
name|solver
operator|)
operator|.
name|export
argument_list|(
name|CONSTANTS
operator|.
name|useAmPm
argument_list|()
argument_list|)
expr_stmt|;
break|break;
default|default:
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Feature not implemented."
argument_list|)
throw|;
block|}
name|helper
operator|.
name|setup
argument_list|(
literal|"text/csv"
argument_list|,
name|type
operator|.
name|name
argument_list|()
operator|.
name|toLowerCase
argument_list|()
operator|+
literal|"-solution.csv"
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|PrintWriter
name|writer
init|=
name|helper
operator|.
name|getWriter
argument_list|()
decl_stmt|;
if|if
condition|(
name|csv
operator|.
name|getHeader
argument_list|()
operator|!=
literal|null
condition|)
name|writer
operator|.
name|println
argument_list|(
name|csv
operator|.
name|getHeader
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|csv
operator|.
name|getLines
argument_list|()
operator|!=
literal|null
condition|)
for|for
control|(
name|CSVFile
operator|.
name|CSVLine
name|line
range|:
name|csv
operator|.
name|getLines
argument_list|()
control|)
name|writer
operator|.
name|println
argument_list|(
name|line
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|writer
operator|.
name|flush
argument_list|()
expr_stmt|;
name|writer
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

