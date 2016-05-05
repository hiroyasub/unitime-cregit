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
name|OutputStream
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
name|exam
operator|.
name|ExamSolverProxy
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
name|service
operator|.
name|SolverService
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
name|studentsct
operator|.
name|StudentSolverProxy
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|Service
argument_list|(
literal|"org.unitime.timetable.export.Exporter:solution.xml"
argument_list|)
specifier|public
class|class
name|ExportSolutionXML
implements|implements
name|Exporter
block|{
annotation|@
name|Override
specifier|public
name|String
name|reference
parameter_list|()
block|{
return|return
literal|"solution.xml"
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
name|SolverSolutionExportXml
argument_list|)
expr_stmt|;
break|break;
case|case
name|EXAM
case|:
name|helper
operator|.
name|getSessionContext
argument_list|()
operator|.
name|checkPermission
argument_list|(
name|Right
operator|.
name|ExaminationSolutionExportXml
argument_list|)
expr_stmt|;
break|break;
case|case
name|STUDENT
case|:
name|helper
operator|.
name|getSessionContext
argument_list|()
operator|.
name|checkPermission
argument_list|(
name|Right
operator|.
name|StudentSectioningSolutionExportXml
argument_list|)
expr_stmt|;
break|break;
block|}
name|byte
index|[]
name|buf
init|=
name|solver
operator|.
name|exportXml
argument_list|()
decl_stmt|;
name|helper
operator|.
name|setup
argument_list|(
literal|"application/xml"
argument_list|,
name|type
operator|.
name|name
argument_list|()
operator|.
name|toLowerCase
argument_list|()
operator|+
literal|"-solution.xml"
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|OutputStream
name|out
init|=
name|helper
operator|.
name|getOutputStream
argument_list|()
decl_stmt|;
name|out
operator|.
name|write
argument_list|(
name|buf
argument_list|)
expr_stmt|;
name|out
operator|.
name|flush
argument_list|()
expr_stmt|;
name|out
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Autowired
name|SolverService
argument_list|<
name|SolverProxy
argument_list|>
name|courseTimetablingSolverService
decl_stmt|;
annotation|@
name|Autowired
name|SolverService
argument_list|<
name|ExamSolverProxy
argument_list|>
name|examinationSolverService
decl_stmt|;
annotation|@
name|Autowired
name|SolverService
argument_list|<
name|StudentSolverProxy
argument_list|>
name|studentSectioningSolverService
decl_stmt|;
specifier|protected
name|CommonSolverInterface
name|getSolver
parameter_list|(
name|SolverType
name|type
parameter_list|)
block|{
switch|switch
condition|(
name|type
condition|)
block|{
case|case
name|COURSE
case|:
return|return
name|courseTimetablingSolverService
operator|.
name|getSolver
argument_list|()
return|;
case|case
name|EXAM
case|:
return|return
name|examinationSolverService
operator|.
name|getSolver
argument_list|()
return|;
case|case
name|STUDENT
case|:
return|return
name|studentSectioningSolverService
operator|.
name|getSolver
argument_list|()
return|;
default|default:
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Invalid solver type "
operator|+
name|type
argument_list|)
throw|;
block|}
block|}
block|}
end_class

end_unit

