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
name|solver
package|;
end_package

begin_import
import|import
name|java
operator|.
name|text
operator|.
name|DecimalFormat
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
name|Collections
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
name|Locale
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
name|defaults
operator|.
name|ApplicationProperty
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
name|defaults
operator|.
name|SessionAttribute
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
name|defaults
operator|.
name|UserProperty
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
name|gwt
operator|.
name|shared
operator|.
name|TableInterface
operator|.
name|TableHeaderIterface
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
operator|.
name|TableRowInterface
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
name|Solution
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
name|SolverGroup
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
name|SolutionDAO
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
name|ui
operator|.
name|SolutionUnassignedClassesModel
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
name|ui
operator|.
name|UnassignedClassRow
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
name|ui
operator|.
name|UnassignedClassesModel
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
name|util
operator|.
name|Constants
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
name|webutil
operator|.
name|BackTracker
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|GwtRpcImplements
argument_list|(
name|NotAssignedClassesRequest
operator|.
name|class
argument_list|)
specifier|public
class|class
name|NotAssignedClassesBackend
implements|implements
name|GwtRpcImplementation
argument_list|<
name|NotAssignedClassesRequest
argument_list|,
name|NotAssignedClassesResponse
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
specifier|protected
specifier|static
name|DecimalFormat
name|sDF
init|=
operator|new
name|DecimalFormat
argument_list|(
literal|"0.###"
argument_list|,
operator|new
name|java
operator|.
name|text
operator|.
name|DecimalFormatSymbols
argument_list|(
name|Locale
operator|.
name|US
argument_list|)
argument_list|)
decl_stmt|;
annotation|@
name|Autowired
name|SolverService
argument_list|<
name|SolverProxy
argument_list|>
name|courseTimetablingSolverService
decl_stmt|;
annotation|@
name|Override
specifier|public
name|NotAssignedClassesResponse
name|execute
parameter_list|(
name|NotAssignedClassesRequest
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
name|NotAssignedClasses
argument_list|)
expr_stmt|;
name|NotAssignedClassesResponse
name|response
init|=
operator|new
name|NotAssignedClassesResponse
argument_list|()
decl_stmt|;
name|SolverProxy
name|solver
init|=
name|courseTimetablingSolverService
operator|.
name|getSolver
argument_list|()
decl_stmt|;
name|String
name|subjects
init|=
name|request
operator|.
name|getFilter
argument_list|()
operator|.
name|getParameterValue
argument_list|(
literal|"subjectArea"
argument_list|)
decl_stmt|;
name|context
operator|.
name|setAttribute
argument_list|(
name|SessionAttribute
operator|.
name|OfferingsSubjectArea
argument_list|,
name|AssignedClassesBackend
operator|.
name|isAllSubjects
argument_list|(
name|subjects
argument_list|)
condition|?
name|Constants
operator|.
name|ALL_OPTION_VALUE
else|:
name|request
operator|.
name|getFilter
argument_list|()
operator|.
name|getParameterValue
argument_list|(
literal|"subjectArea"
argument_list|)
argument_list|)
expr_stmt|;
name|String
name|instructorNameFormat
init|=
name|UserProperty
operator|.
name|NameFormat
operator|.
name|get
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
argument_list|)
decl_stmt|;
name|String
name|solutionIdsStr
init|=
operator|(
name|String
operator|)
name|context
operator|.
name|getAttribute
argument_list|(
name|SessionAttribute
operator|.
name|SelectedSolution
argument_list|)
decl_stmt|;
if|if
condition|(
name|solver
operator|==
literal|null
condition|)
block|{
if|if
condition|(
name|solutionIdsStr
operator|==
literal|null
operator|||
name|solutionIdsStr
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
for|for
control|(
name|SolverGroup
name|g
range|:
name|SolverGroup
operator|.
name|getUserSolverGroups
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
argument_list|)
control|)
block|{
for|for
control|(
name|Long
name|id
range|:
operator|(
name|List
argument_list|<
name|Long
argument_list|>
operator|)
name|SolutionDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select s.uniqueId from Solution s where s.commited = true and s.owner = :groupId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"groupId"
argument_list|,
name|g
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
control|)
block|{
if|if
condition|(
name|solutionIdsStr
operator|==
literal|null
condition|)
name|solutionIdsStr
operator|=
name|id
operator|.
name|toString
argument_list|()
expr_stmt|;
else|else
name|solutionIdsStr
operator|+=
operator|(
name|solutionIdsStr
operator|.
name|isEmpty
argument_list|()
condition|?
literal|""
else|:
literal|","
operator|)
operator|+
name|id
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
name|solutionIdsStr
operator|==
literal|null
operator|||
name|solutionIdsStr
operator|.
name|isEmpty
argument_list|()
condition|)
throw|throw
operator|new
name|GwtRpcException
argument_list|(
name|MESSAGES
operator|.
name|errorNotAssignedClassesNoSolution
argument_list|()
argument_list|)
throw|;
block|}
name|UnassignedClassesModel
name|model
init|=
literal|null
decl_stmt|;
name|String
index|[]
name|prefixes
init|=
literal|null
decl_stmt|;
if|if
condition|(
operator|!
name|AssignedClassesBackend
operator|.
name|isAllSubjects
argument_list|(
name|subjects
argument_list|)
condition|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|list
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|id
range|:
name|subjects
operator|.
name|split
argument_list|(
literal|","
argument_list|)
control|)
block|{
name|list
operator|.
name|add
argument_list|(
name|request
operator|.
name|getFilter
argument_list|()
operator|.
name|getParameter
argument_list|(
literal|"subjectArea"
argument_list|)
operator|.
name|getOptionText
argument_list|(
name|id
argument_list|)
operator|+
literal|" "
argument_list|)
expr_stmt|;
block|}
name|prefixes
operator|=
name|list
operator|.
name|toArray
argument_list|(
operator|new
name|String
index|[
name|list
operator|.
name|size
argument_list|()
index|]
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|solver
operator|!=
literal|null
condition|)
block|{
name|model
operator|=
name|solver
operator|.
name|getUnassignedClassesModel
argument_list|(
name|prefixes
argument_list|)
expr_stmt|;
name|response
operator|.
name|setShowNote
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|List
argument_list|<
name|Solution
argument_list|>
name|solutions
init|=
operator|new
name|ArrayList
argument_list|<
name|Solution
argument_list|>
argument_list|()
decl_stmt|;
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
name|SolutionDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|solutionId
range|:
name|solutionIdsStr
operator|.
name|split
argument_list|(
literal|","
argument_list|)
control|)
block|{
name|Solution
name|solution
init|=
name|SolutionDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|solutionId
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|solution
operator|!=
literal|null
condition|)
name|solutions
operator|.
name|add
argument_list|(
name|solution
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|solutions
operator|.
name|isEmpty
argument_list|()
condition|)
name|model
operator|=
operator|new
name|SolutionUnassignedClassesModel
argument_list|(
name|solutions
argument_list|,
name|hibSession
argument_list|,
name|instructorNameFormat
argument_list|,
name|prefixes
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|model
operator|!=
literal|null
condition|)
block|{
name|Collections
operator|.
name|sort
argument_list|(
name|model
operator|.
name|rows
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|UnassignedClassRow
name|ucr
range|:
name|model
operator|.
name|rows
argument_list|()
control|)
block|{
name|boolean
name|showClassDetail
init|=
operator|(
name|solver
operator|==
literal|null
operator|&&
name|context
operator|.
name|hasPermission
argument_list|(
name|ucr
operator|.
name|getId
argument_list|()
argument_list|,
literal|"Class_"
argument_list|,
name|Right
operator|.
name|ClassDetail
argument_list|)
operator|)
decl_stmt|;
name|response
operator|.
name|addRow
argument_list|(
operator|new
name|TableRowInterface
argument_list|(
name|ucr
operator|.
name|getId
argument_list|()
argument_list|,
operator|(
name|showClassDetail
condition|?
literal|"classDetail.do?cid="
operator|+
name|ucr
operator|.
name|getId
argument_list|()
else|:
literal|"gwt.jsp?page=suggestions&menu=hide&id="
operator|+
name|ucr
operator|.
name|getId
argument_list|()
operator|)
argument_list|,
operator|(
name|showClassDetail
condition|?
literal|null
else|:
name|MESSAGES
operator|.
name|dialogSuggestions
argument_list|()
operator|)
argument_list|,
operator|new
name|TableInterface
operator|.
name|TableCellClassName
argument_list|(
name|ucr
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|,
operator|new
name|TableInterface
operator|.
name|TableCellItems
argument_list|(
name|ucr
operator|.
name|getInstructors
argument_list|()
argument_list|)
argument_list|,
operator|new
name|TableInterface
operator|.
name|TableCellInterface
argument_list|<
name|Integer
argument_list|>
argument_list|(
name|ucr
operator|.
name|getNrStudents
argument_list|()
argument_list|)
argument_list|,
operator|new
name|TableInterface
operator|.
name|TableCellText
argument_list|(
name|ucr
operator|.
name|getInitial
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
name|response
operator|.
name|setHeader
argument_list|(
operator|new
name|TableHeaderIterface
argument_list|(
name|MESSAGES
operator|.
name|colClass
argument_list|()
argument_list|)
argument_list|,
operator|new
name|TableHeaderIterface
argument_list|(
name|MESSAGES
operator|.
name|colInstructor
argument_list|()
argument_list|)
argument_list|,
operator|new
name|TableHeaderIterface
argument_list|(
name|MESSAGES
operator|.
name|colNrAssignedStudents
argument_list|()
argument_list|)
argument_list|,
operator|new
name|TableHeaderIterface
argument_list|(
name|MESSAGES
operator|.
name|colInitialAssignment
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|SolverPageBackend
operator|.
name|fillSolverWarnings
argument_list|(
name|context
argument_list|,
name|solver
argument_list|,
name|SolverType
operator|.
name|COURSE
argument_list|,
name|response
argument_list|)
expr_stmt|;
name|BackTracker
operator|.
name|markForBack
argument_list|(
name|context
argument_list|,
literal|"gwt.jsp?page=notAssignedClasses"
argument_list|,
name|MESSAGES
operator|.
name|pageNotAssignedClasses
argument_list|()
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|)
expr_stmt|;
if|if
condition|(
name|ApplicationProperty
operator|.
name|TimeGridShowCrosslists
operator|.
name|isTrue
argument_list|()
condition|)
name|AssignedClassesBackend
operator|.
name|addCrosslistedNames
argument_list|(
name|response
argument_list|,
name|ApplicationProperty
operator|.
name|SolverShowClassSufix
operator|.
name|isTrue
argument_list|()
argument_list|,
name|ApplicationProperty
operator|.
name|SolverShowConfiguratioName
operator|.
name|isTrue
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|response
return|;
block|}
block|}
end_class

end_unit

