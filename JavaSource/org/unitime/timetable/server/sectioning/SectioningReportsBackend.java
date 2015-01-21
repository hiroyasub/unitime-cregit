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
name|sectioning
package|;
end_package

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
name|cpsolver
operator|.
name|ifs
operator|.
name|util
operator|.
name|DataProperties
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
operator|.
name|CSVLine
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
name|gwt
operator|.
name|client
operator|.
name|sectioning
operator|.
name|SectioningReports
operator|.
name|SectioningReportRpcRequest
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
name|client
operator|.
name|sectioning
operator|.
name|SectioningReports
operator|.
name|SectioningReportRpcResponse
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
name|GwtRpcException
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
name|command
operator|.
name|server
operator|.
name|GwtRpcImplements
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
name|onlinesectioning
operator|.
name|OnlineSectioningLog
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
name|onlinesectioning
operator|.
name|OnlineSectioningServer
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
name|onlinesectioning
operator|.
name|basic
operator|.
name|GenerateSectioningReport
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
name|SessionContext
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
name|service
operator|.
name|SolverServerService
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
name|GwtRpcImplements
argument_list|(
name|SectioningReportRpcRequest
operator|.
name|class
argument_list|)
specifier|public
class|class
name|SectioningReportsBackend
implements|implements
name|GwtRpcImplementation
argument_list|<
name|SectioningReportRpcRequest
argument_list|,
name|SectioningReportRpcResponse
argument_list|>
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
name|Autowired
name|SolverService
argument_list|<
name|StudentSolverProxy
argument_list|>
name|studentSectioningSolverService
decl_stmt|;
annotation|@
name|Autowired
name|SolverServerService
name|solverServerService
decl_stmt|;
annotation|@
name|Override
specifier|public
name|SectioningReportRpcResponse
name|execute
parameter_list|(
name|SectioningReportRpcRequest
name|request
parameter_list|,
name|SessionContext
name|context
parameter_list|)
block|{
name|DataProperties
name|parameters
init|=
operator|new
name|DataProperties
argument_list|(
name|request
operator|.
name|getParameters
argument_list|()
argument_list|)
decl_stmt|;
name|CSVFile
name|csv
init|=
literal|null
decl_stmt|;
name|boolean
name|online
init|=
name|parameters
operator|.
name|getPropertyBoolean
argument_list|(
literal|"online"
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|parameters
operator|.
name|setProperty
argument_list|(
literal|"useAmPm"
argument_list|,
name|CONSTANTS
operator|.
name|useAmPm
argument_list|()
condition|?
literal|"true"
else|:
literal|"false"
argument_list|)
expr_stmt|;
if|if
condition|(
name|online
condition|)
block|{
name|context
operator|.
name|checkPermission
argument_list|(
name|Right
operator|.
name|SchedulingDashboard
argument_list|)
expr_stmt|;
name|OnlineSectioningServer
name|server
init|=
name|solverServerService
operator|.
name|getOnlineStudentSchedulingContainer
argument_list|()
operator|.
name|getSolver
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|server
operator|==
literal|null
condition|)
throw|throw
operator|new
name|GwtRpcException
argument_list|(
literal|"Online student scheduling is not enabled for "
operator|+
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|getQualifiers
argument_list|(
literal|"Session"
argument_list|)
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|getQualifierLabel
argument_list|()
operator|+
literal|"."
argument_list|)
throw|;
name|OnlineSectioningLog
operator|.
name|Entity
name|user
init|=
name|OnlineSectioningLog
operator|.
name|Entity
operator|.
name|newBuilder
argument_list|()
operator|.
name|setExternalId
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getExternalUserId
argument_list|()
argument_list|)
operator|.
name|setName
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getName
argument_list|()
operator|==
literal|null
condition|?
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getUsername
argument_list|()
else|:
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
operator|.
name|setType
argument_list|(
name|context
operator|.
name|hasPermission
argument_list|(
name|Right
operator|.
name|StudentSchedulingAdvisor
argument_list|)
condition|?
name|OnlineSectioningLog
operator|.
name|Entity
operator|.
name|EntityType
operator|.
name|MANAGER
else|:
name|OnlineSectioningLog
operator|.
name|Entity
operator|.
name|EntityType
operator|.
name|STUDENT
argument_list|)
operator|.
name|build
argument_list|()
decl_stmt|;
name|csv
operator|=
name|server
operator|.
name|execute
argument_list|(
name|server
operator|.
name|createAction
argument_list|(
name|GenerateSectioningReport
operator|.
name|class
argument_list|)
operator|.
name|withParameters
argument_list|(
name|parameters
argument_list|)
argument_list|,
name|user
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|context
operator|.
name|checkPermission
argument_list|(
name|Right
operator|.
name|StudentSectioningSolver
argument_list|)
expr_stmt|;
name|StudentSolverProxy
name|solver
init|=
name|studentSectioningSolverService
operator|.
name|getSolver
argument_list|()
decl_stmt|;
if|if
condition|(
name|solver
operator|==
literal|null
condition|)
throw|throw
operator|new
name|GwtRpcException
argument_list|(
literal|"No student solver is running."
argument_list|)
throw|;
name|csv
operator|=
name|solver
operator|.
name|getReport
argument_list|(
name|parameters
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|csv
operator|==
literal|null
condition|)
throw|throw
operator|new
name|GwtRpcException
argument_list|(
literal|"No report was created."
argument_list|)
throw|;
name|SectioningReportRpcResponse
name|response
init|=
operator|new
name|SectioningReportRpcResponse
argument_list|()
decl_stmt|;
name|String
index|[]
name|header
init|=
operator|new
name|String
index|[
name|csv
operator|.
name|getHeader
argument_list|()
operator|.
name|getFields
argument_list|()
operator|.
name|size
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
name|csv
operator|.
name|getHeader
argument_list|()
operator|.
name|getFields
argument_list|()
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
name|header
index|[
name|i
index|]
operator|=
name|csv
operator|.
name|getHeader
argument_list|()
operator|.
name|getField
argument_list|(
name|i
argument_list|)
operator|.
name|toString
argument_list|()
expr_stmt|;
name|response
operator|.
name|addLine
argument_list|(
name|header
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
name|CSVLine
name|line
range|:
name|csv
operator|.
name|getLines
argument_list|()
control|)
block|{
name|String
index|[]
name|row
init|=
operator|new
name|String
index|[
name|line
operator|.
name|getFields
argument_list|()
operator|.
name|size
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
name|line
operator|.
name|getFields
argument_list|()
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
name|row
index|[
name|i
index|]
operator|=
name|line
operator|.
name|getField
argument_list|(
name|i
argument_list|)
operator|.
name|toString
argument_list|()
expr_stmt|;
name|response
operator|.
name|addLine
argument_list|(
name|row
argument_list|)
expr_stmt|;
block|}
return|return
name|response
return|;
block|}
block|}
end_class

end_unit

