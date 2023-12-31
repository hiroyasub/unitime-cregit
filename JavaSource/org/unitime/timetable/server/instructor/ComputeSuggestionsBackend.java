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
name|instructor
package|;
end_package

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
name|InstructorInterface
operator|.
name|AssignmentInfo
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
name|InstructorInterface
operator|.
name|ComputeSuggestionsRequest
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
name|InstructorInterface
operator|.
name|InstructorInfo
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
name|InstructorInterface
operator|.
name|SuggestionsResponse
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
name|model
operator|.
name|DepartmentalInstructor
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
name|model
operator|.
name|TeachingRequest
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
name|model
operator|.
name|dao
operator|.
name|DepartmentalInstructorDAO
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
name|model
operator|.
name|dao
operator|.
name|TeachingRequestDAO
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
name|instructor
operator|.
name|InstructorSchedulingProxy
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

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|GwtRpcImplements
argument_list|(
name|ComputeSuggestionsRequest
operator|.
name|class
argument_list|)
specifier|public
class|class
name|ComputeSuggestionsBackend
extends|extends
name|InstructorSchedulingBackendHelper
implements|implements
name|GwtRpcImplementation
argument_list|<
name|ComputeSuggestionsRequest
argument_list|,
name|SuggestionsResponse
argument_list|>
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
annotation|@
name|Autowired
name|SolverService
argument_list|<
name|InstructorSchedulingProxy
argument_list|>
name|instructorSchedulingSolverService
decl_stmt|;
annotation|@
name|Override
specifier|public
name|SuggestionsResponse
name|execute
parameter_list|(
name|ComputeSuggestionsRequest
name|request
parameter_list|,
name|SessionContext
name|context
parameter_list|)
block|{
name|context
operator|.
name|checkPermission
argument_list|(
name|Right
operator|.
name|InstructorSchedulingSolver
argument_list|)
expr_stmt|;
name|InstructorSchedulingProxy
name|solver
init|=
name|instructorSchedulingSolverService
operator|.
name|getSolver
argument_list|()
decl_stmt|;
if|if
condition|(
name|solver
operator|!=
literal|null
condition|)
return|return
name|solver
operator|.
name|computeSuggestions
argument_list|(
name|request
argument_list|)
return|;
name|SuggestionsResponse
name|response
init|=
operator|new
name|SuggestionsResponse
argument_list|()
decl_stmt|;
name|Context
name|cx
init|=
operator|new
name|Context
argument_list|(
name|context
argument_list|,
name|solver
argument_list|)
decl_stmt|;
name|Suggestion
name|s
init|=
operator|new
name|Suggestion
argument_list|()
decl_stmt|;
for|for
control|(
name|AssignmentInfo
name|ai
range|:
name|request
operator|.
name|getAssignments
argument_list|()
control|)
block|{
name|TeachingRequest
name|tr
init|=
name|TeachingRequestDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|ai
operator|.
name|getRequest
argument_list|()
operator|.
name|getRequestId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|tr
operator|==
literal|null
condition|)
continue|continue;
name|DepartmentalInstructor
name|instructor
init|=
operator|(
name|ai
operator|.
name|getInstructor
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
name|DepartmentalInstructorDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|ai
operator|.
name|getInstructor
argument_list|()
operator|.
name|getInstructorId
argument_list|()
argument_list|)
operator|)
decl_stmt|;
if|if
condition|(
name|instructor
operator|!=
literal|null
condition|)
block|{
name|InstructorInfo
name|prev
init|=
name|ai
operator|.
name|getRequest
argument_list|()
operator|.
name|getInstructor
argument_list|(
name|ai
operator|.
name|getIndex
argument_list|()
argument_list|)
decl_stmt|;
name|s
operator|.
name|set
argument_list|(
name|tr
argument_list|,
name|ai
operator|.
name|getIndex
argument_list|()
argument_list|,
name|instructor
argument_list|,
name|prev
operator|==
literal|null
condition|?
literal|null
else|:
name|DepartmentalInstructorDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|prev
operator|.
name|getInstructorId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
name|response
operator|.
name|setCurrentAssignment
argument_list|(
name|s
operator|.
name|toInfo
argument_list|(
name|cx
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|request
operator|.
name|getSelectedInstructorId
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|DepartmentalInstructor
name|instructor
init|=
name|DepartmentalInstructorDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|request
operator|.
name|getSelectedInstructorId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|instructor
operator|==
literal|null
condition|)
return|return
literal|null
return|;
name|TeachingRequest
name|tr
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|request
operator|.
name|getSelectedRequestId
argument_list|()
operator|!=
literal|null
condition|)
name|tr
operator|=
name|TeachingRequestDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|request
operator|.
name|getSelectedRequestId
argument_list|()
argument_list|)
expr_stmt|;
name|computeDomainForInstructor
argument_list|(
name|response
argument_list|,
name|instructor
argument_list|,
name|tr
argument_list|,
name|cx
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|request
operator|.
name|getSelectedRequestId
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|TeachingRequest
name|tr
init|=
name|TeachingRequestDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|request
operator|.
name|getSelectedRequestId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|tr
operator|==
literal|null
condition|)
return|return
literal|null
return|;
name|cx
operator|.
name|setBase
argument_list|(
name|s
argument_list|)
expr_stmt|;
name|computeDomainForClass
argument_list|(
name|response
argument_list|,
name|tr
argument_list|,
name|request
operator|.
name|getSelectedIndex
argument_list|()
argument_list|,
name|cx
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

