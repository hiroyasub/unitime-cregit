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
name|defaults
package|;
end_package

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_enum
specifier|public
enum|enum
name|SessionAttribute
block|{
name|SelectedSolution
argument_list|(
literal|"Solver.selectedSolutionId"
argument_list|,
literal|"Selected course timetabling solution or solutions (String containing a comma separated list of ids)."
argument_list|)
block|,
name|ClassAssignment
argument_list|(
literal|"LastSolutionClassAssignmentProxy"
argument_list|,
literal|"Last used class assignment proxy."
argument_list|)
block|,
name|CourseTimetablingSolver
argument_list|(
literal|"SolverProxy"
argument_list|,
literal|"Last used course timetabling solver."
argument_list|)
block|,
name|CourseTimetablingUser
argument_list|(
literal|"ManageSolver.puid"
argument_list|,
literal|"User id of the solver I am looking at (if different from user id, admin only)"
argument_list|)
block|,
name|ExaminationSolver
argument_list|(
literal|"ExamSolverProxy"
argument_list|,
literal|"Last used examination solver."
argument_list|)
block|,
name|ExaminationUser
argument_list|(
literal|"ManageSolver.examPuid"
argument_list|,
literal|"User id of the solver I am looking at (if different from user id, admin only)"
argument_list|)
block|,
name|StudentSectioningSolver
argument_list|(
literal|"StudentSolverProxy"
argument_list|,
literal|"Last used student sectioning solver."
argument_list|)
block|,
name|StudentSectioningUser
argument_list|(
literal|"ManageSolver.sectionPuid"
argument_list|,
literal|"User id of the solver I am looking at (if different from user id, admin only)"
argument_list|)
block|,
name|InstructorSchedulingSolver
argument_list|(
literal|"InstructorSchedulingProxy"
argument_list|,
literal|"Last used instructor scheduling solver."
argument_list|)
block|,
name|InstructorSchedulingUser
argument_list|(
literal|"ManageSolver.instrPuid"
argument_list|,
literal|"User id of the solver I am looking at (if different from user id, admin only)"
argument_list|)
block|,
name|OfferingsSubjectArea
argument_list|(
literal|"subjectAreaId"
argument_list|,
literal|"Last used subject area or areas (String containing a comma separated list of ids)"
argument_list|)
block|,
name|OfferingsCourseNumber
argument_list|(
literal|"courseNbr"
argument_list|,
literal|"Last used course number (String containing course number)"
argument_list|)
block|,
name|ClassesSubjectAreas
argument_list|(
literal|"crsLstSubjectAreaIds"
argument_list|,
literal|"Last used subject areas (String containing a comma separated list of ids)"
argument_list|)
block|,
name|ClassesCourseNumber
argument_list|(
literal|"crsLstCrsNbr"
argument_list|,
literal|"Last used course number (String containing course number)"
argument_list|)
block|,
name|ClassAssignmentsSubjectAreas
argument_list|(
literal|"crsAsgnLstSubjectAreaIds"
argument_list|,
literal|"Last used subject areas (String containing a comma separated list of ids)"
argument_list|)
block|,
name|DepartmentId
argument_list|(
literal|"deptUniqueId"
argument_list|,
literal|"Last department (String containing department unique id)"
argument_list|)
block|,
name|DepartmentCodeRoom
argument_list|(
literal|"deptCodeRoom"
argument_list|,
literal|"Last department code (used by Rooms pages)"
argument_list|)
block|,
name|TableOrder
argument_list|(
literal|"OrderInfo"
argument_list|,
literal|"WebTable order info"
argument_list|)
block|,
name|ExamType
argument_list|(
literal|"Exam.Type"
argument_list|,
literal|"Examination type"
argument_list|)
block|,
name|FormFactor
argument_list|(
literal|"mgwt.formfactor"
argument_list|,
literal|"Device form factor"
argument_list|)
block|, 	;
name|String
name|iKey
decl_stmt|,
name|iDefault
decl_stmt|,
name|iDescription
decl_stmt|;
name|SessionAttribute
parameter_list|(
name|String
name|key
parameter_list|,
name|String
name|defaultValue
parameter_list|,
name|String
name|description
parameter_list|)
block|{
name|iKey
operator|=
name|key
expr_stmt|;
name|iDefault
operator|=
name|defaultValue
expr_stmt|;
name|iDescription
operator|=
name|defaultValue
expr_stmt|;
block|}
name|SessionAttribute
parameter_list|(
name|String
name|key
parameter_list|,
name|String
name|description
parameter_list|)
block|{
name|this
argument_list|(
name|key
argument_list|,
literal|null
argument_list|,
name|description
argument_list|)
expr_stmt|;
block|}
specifier|public
name|String
name|key
parameter_list|()
block|{
return|return
name|iKey
return|;
block|}
specifier|public
name|String
name|defaultValue
parameter_list|()
block|{
return|return
name|iDefault
return|;
block|}
specifier|public
name|String
name|description
parameter_list|()
block|{
return|return
name|iDescription
return|;
block|}
block|}
end_enum

end_unit

