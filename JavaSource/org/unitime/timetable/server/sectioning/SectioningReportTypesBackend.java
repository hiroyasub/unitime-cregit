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
name|java
operator|.
name|util
operator|.
name|Collection
import|;
end_import

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|studentsct
operator|.
name|report
operator|.
name|DistanceConflictTable
import|;
end_import

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|studentsct
operator|.
name|report
operator|.
name|RequestGroupTable
import|;
end_import

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|studentsct
operator|.
name|report
operator|.
name|RequestPriorityTable
import|;
end_import

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|studentsct
operator|.
name|report
operator|.
name|SectionConflictTable
import|;
end_import

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|studentsct
operator|.
name|report
operator|.
name|TableauReport
import|;
end_import

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|studentsct
operator|.
name|report
operator|.
name|TimeOverlapConflictTable
import|;
end_import

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|studentsct
operator|.
name|report
operator|.
name|UnbalancedSectionsTable
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
name|ReportTypeInterface
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
name|SectioningReportTypesRpcRequest
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
name|StudentSectioningMessages
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
name|reports
operator|.
name|studentsct
operator|.
name|IndividualStudentTimeOverlaps
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
name|reports
operator|.
name|studentsct
operator|.
name|PerturbationsReport
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
name|reports
operator|.
name|studentsct
operator|.
name|StudentAvailabilityConflicts
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
name|reports
operator|.
name|studentsct
operator|.
name|UnasignedCourseRequests
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
name|reports
operator|.
name|studentsct
operator|.
name|UnusedReservations
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
name|SectioningReportTypesRpcRequest
operator|.
name|class
argument_list|)
specifier|public
class|class
name|SectioningReportTypesBackend
implements|implements
name|GwtRpcImplementation
argument_list|<
name|SectioningReportTypesRpcRequest
argument_list|,
name|GwtRpcResponseList
argument_list|<
name|ReportTypeInterface
argument_list|>
argument_list|>
block|{
specifier|protected
specifier|static
specifier|final
name|StudentSectioningMessages
name|SCT_MSG
init|=
name|Localization
operator|.
name|create
argument_list|(
name|StudentSectioningMessages
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
specifier|public
specifier|static
enum|enum
name|ReportType
block|{
name|TIME_CONFLICTS
argument_list|(
literal|"Time Conflicts"
argument_list|,
name|SectionConflictTable
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|,
literal|"type"
argument_list|,
literal|"OVERLAPS"
argument_list|,
literal|"overlapsIncludeAll"
argument_list|,
literal|"true"
argument_list|)
block|,
name|AVAILABLE_CONFLICTS
argument_list|(
literal|"Availability Conflicts"
argument_list|,
name|SectionConflictTable
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|,
literal|"type"
argument_list|,
literal|"UNAVAILABILITIES"
argument_list|,
literal|"overlapsIncludeAll"
argument_list|,
literal|"true"
argument_list|)
block|,
name|SECTION_CONFLICTS
argument_list|(
literal|"Time& Availability Conflicts"
argument_list|,
name|SectionConflictTable
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|,
literal|"type"
argument_list|,
literal|"OVERLAPS_AND_UNAVAILABILITIES"
argument_list|,
literal|"overlapsIncludeAll"
argument_list|,
literal|"true"
argument_list|)
block|,
name|UNBALANCED_SECTIONS
argument_list|(
literal|"Unbalanced Classes"
argument_list|,
name|UnbalancedSectionsTable
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|)
block|,
name|DISTANCE_CONFLICTS
argument_list|(
literal|"Distance Conflicts"
argument_list|,
name|DistanceConflictTable
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|)
block|,
name|TIME_OVERLAPS
argument_list|(
literal|"Time Overlaps"
argument_list|,
name|TimeOverlapConflictTable
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|)
block|,
name|REQUEST_GROUPS
argument_list|(
literal|"Request Groups"
argument_list|,
name|RequestGroupTable
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|)
block|,
name|PERTURBATIONS
argument_list|(
literal|"Perturbations"
argument_list|,
name|PerturbationsReport
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|)
block|,
name|INDIVIDUAL_TIME_OVERLAPS
argument_list|(
literal|"Individual Student Time Overlaps"
argument_list|,
name|IndividualStudentTimeOverlaps
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|)
block|,
name|NOT_ALLOWED_TIME_OVERLAPS
argument_list|(
literal|"Not Allowed Time Overlaps"
argument_list|,
name|IndividualStudentTimeOverlaps
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|,
literal|"includeAllowedOverlaps"
argument_list|,
literal|"false"
argument_list|)
block|,
name|INDIVIDUAL_TIME_OVERLAPS_BT
argument_list|(
literal|"Individual Student Time Overlaps (Exclude Break Times)"
argument_list|,
name|IndividualStudentTimeOverlaps
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|,
literal|"ignoreBreakTimeConflicts"
argument_list|,
literal|"true"
argument_list|)
block|,
name|NOT_ALLOWED_TIME_OVERLAPS_BT
argument_list|(
literal|"Not Allowed Time Overlaps (Exclude Break Times)"
argument_list|,
name|IndividualStudentTimeOverlaps
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|,
literal|"ignoreBreakTimeConflicts"
argument_list|,
literal|"true"
argument_list|,
literal|"includeAllowedOverlaps"
argument_list|,
literal|"false"
argument_list|)
block|,
name|TEACHING_CONFLICTS
argument_list|(
literal|"Teaching Conflicts"
argument_list|,
name|StudentAvailabilityConflicts
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|)
block|,
name|TEACHING_CONFLICTS_NA
argument_list|(
literal|"Teaching Conflicts (Exclude Allowed)"
argument_list|,
name|StudentAvailabilityConflicts
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|,
literal|"includeAllowedOverlaps"
argument_list|,
literal|"false"
argument_list|)
block|,
name|NOT_ASSIGNED_COURSE_REQUESTS
argument_list|(
name|SCT_MSG
operator|.
name|reportUnassignedCourseRequests
argument_list|()
argument_list|,
name|UnasignedCourseRequests
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|)
block|,
name|UNUSED_GROUP_RES
argument_list|(
name|SCT_MSG
operator|.
name|reportUnusedGroupReservations
argument_list|()
argument_list|,
name|UnusedReservations
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|,
literal|"type"
argument_list|,
literal|"group"
argument_list|)
block|,
name|UNUSED_INDIVIDUAL_RES
argument_list|(
name|SCT_MSG
operator|.
name|reportUnusedIndividualReservations
argument_list|()
argument_list|,
name|UnusedReservations
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|,
literal|"type"
argument_list|,
literal|"individual"
argument_list|)
block|,
name|UNUSED_OVERRIDE_RES
argument_list|(
name|SCT_MSG
operator|.
name|reportUnusedOverrideReservations
argument_list|()
argument_list|,
name|UnusedReservations
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|,
literal|"type"
argument_list|,
literal|"override"
argument_list|)
block|,
name|UNUSED_LC_RES
argument_list|(
name|SCT_MSG
operator|.
name|reportUnusedLearningCommunityReservations
argument_list|()
argument_list|,
name|UnusedReservations
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|,
literal|"type"
argument_list|,
literal|"lc"
argument_list|)
block|,
name|COURSE_REQUESTS
argument_list|(
name|SCT_MSG
operator|.
name|reportCourseRequestsWithPriorities
argument_list|()
argument_list|,
name|RequestPriorityTable
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|,
literal|"pritify"
argument_list|,
literal|"false"
argument_list|)
block|,
name|TABLEAU_REPORT
argument_list|(
name|SCT_MSG
operator|.
name|reportTableauReport
argument_list|()
argument_list|,
name|TableauReport
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|,
literal|"pritify"
argument_list|,
literal|"false"
argument_list|,
literal|"showdates"
argument_list|,
literal|"false"
argument_list|)
block|, 		;
name|String
name|iName
decl_stmt|,
name|iImplementation
decl_stmt|;
name|String
index|[]
name|iParameters
decl_stmt|;
name|ReportType
parameter_list|(
name|String
name|name
parameter_list|,
name|String
name|implementation
parameter_list|,
name|String
modifier|...
name|params
parameter_list|)
block|{
name|iName
operator|=
name|name
expr_stmt|;
name|iImplementation
operator|=
name|implementation
expr_stmt|;
name|iParameters
operator|=
name|params
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
name|String
name|getImplementation
parameter_list|()
block|{
return|return
name|iImplementation
return|;
block|}
specifier|public
name|String
index|[]
name|getParameters
parameter_list|()
block|{
return|return
name|iParameters
return|;
block|}
specifier|public
name|ReportTypeInterface
name|toReportTypeInterface
parameter_list|()
block|{
return|return
operator|new
name|ReportTypeInterface
argument_list|(
name|name
argument_list|()
argument_list|,
name|iName
argument_list|,
name|iImplementation
argument_list|,
name|iParameters
argument_list|)
return|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|GwtRpcResponseList
argument_list|<
name|ReportTypeInterface
argument_list|>
name|execute
parameter_list|(
name|SectioningReportTypesRpcRequest
name|request
parameter_list|,
name|SessionContext
name|context
parameter_list|)
block|{
name|GwtRpcResponseList
argument_list|<
name|ReportTypeInterface
argument_list|>
name|ret
init|=
operator|new
name|GwtRpcResponseList
argument_list|<
name|ReportTypeInterface
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|ReportType
name|type
range|:
name|ReportType
operator|.
name|values
argument_list|()
control|)
name|ret
operator|.
name|add
argument_list|(
name|type
operator|.
name|toReportTypeInterface
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|request
operator|.
name|isOnline
argument_list|()
condition|)
block|{
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
operator|!=
literal|null
condition|)
block|{
name|Collection
argument_list|<
name|ReportTypeInterface
argument_list|>
name|types
init|=
name|solver
operator|.
name|getReportTypes
argument_list|()
decl_stmt|;
if|if
condition|(
name|types
operator|!=
literal|null
operator|&&
operator|!
name|types
operator|.
name|isEmpty
argument_list|()
condition|)
name|ret
operator|.
name|addAll
argument_list|(
name|types
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|ret
return|;
block|}
block|}
end_class

end_unit

