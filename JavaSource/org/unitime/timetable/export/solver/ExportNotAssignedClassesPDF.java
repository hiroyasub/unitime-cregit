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
name|CourseTimetablingSolverInterface
operator|.
name|NotAssignedClassesFilterRequest
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
name|NotAssignedClassesFilterResponse
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
name|NotAssignedClassesRequest
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
name|NotAssignedClassesResponse
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|Service
argument_list|(
literal|"org.unitime.timetable.export.Exporter:unassigned-classes.pdf"
argument_list|)
specifier|public
class|class
name|ExportNotAssignedClassesPDF
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
literal|"unassigned-classes.pdf"
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
name|GwtRpcImplementation
argument_list|<
name|NotAssignedClassesFilterRequest
argument_list|,
name|NotAssignedClassesFilterResponse
argument_list|>
name|filterService
init|=
operator|(
name|GwtRpcImplementation
argument_list|<
name|NotAssignedClassesFilterRequest
argument_list|,
name|NotAssignedClassesFilterResponse
argument_list|>
operator|)
name|applicationContext
operator|.
name|getBean
argument_list|(
name|NotAssignedClassesFilterRequest
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
name|NotAssignedClassesFilterResponse
name|filter
init|=
name|filterService
operator|.
name|execute
argument_list|(
operator|new
name|NotAssignedClassesFilterRequest
argument_list|()
argument_list|,
name|helper
operator|.
name|getSessionContext
argument_list|()
argument_list|)
decl_stmt|;
name|fillInFilter
argument_list|(
name|filter
argument_list|,
name|helper
argument_list|)
expr_stmt|;
name|GwtRpcImplementation
argument_list|<
name|NotAssignedClassesRequest
argument_list|,
name|NotAssignedClassesResponse
argument_list|>
name|service
init|=
operator|(
name|GwtRpcImplementation
argument_list|<
name|NotAssignedClassesRequest
argument_list|,
name|NotAssignedClassesResponse
argument_list|>
operator|)
name|applicationContext
operator|.
name|getBean
argument_list|(
name|NotAssignedClassesRequest
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
name|NotAssignedClassesRequest
name|request
init|=
operator|new
name|NotAssignedClassesRequest
argument_list|()
decl_stmt|;
name|request
operator|.
name|setFilter
argument_list|(
name|filter
argument_list|)
expr_stmt|;
name|NotAssignedClassesResponse
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
name|printTablePDF
argument_list|(
name|response
argument_list|,
name|helper
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

