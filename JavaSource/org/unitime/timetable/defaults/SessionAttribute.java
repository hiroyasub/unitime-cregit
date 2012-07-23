begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.4 (University Timetabling Application)  * Copyright (C) 2012, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|OfferingsSubjectArea
argument_list|(
literal|"subjectAreaId"
argument_list|,
literal|"Last used subject area (String containing subject area id)"
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
block|; 	;
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

