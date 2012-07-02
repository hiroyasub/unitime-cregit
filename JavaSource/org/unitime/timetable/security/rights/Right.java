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
name|security
operator|.
name|rights
package|;
end_package

begin_enum
specifier|public
enum|enum
name|Right
block|{
comment|/** Session default: current session */
name|SessionDefaultCurrent
block|,
comment|// -- DEFAULT SESSION SELECTION
comment|/** Session default: first future session */
name|SessionDefaultFirstFuture
block|,
comment|/** Session default: first examination session */
name|SessionDefaultFirstExamination
block|,
comment|/** Session dependency -- if independent the role applies to all academic session */
name|SessionIndependent
block|,
name|SessionIndependentIfNoSessionGiven
block|,
comment|/** Session dependency -- test sessions are allowed */
name|AllowTestSessions
block|,
comment|/** Department dependency -- department must match */
name|DepartmentIndependent
block|,
comment|/** Status dependency -- session / department status must match */
name|StatusIndependent
block|,
name|AddNonUnivLocation
block|,
name|AddSpecialUseRoom
block|,
name|ApplicationConfig
block|,
name|AssignedClasses
block|,
name|AssignedExams
block|,
name|AssignmentHistory
block|,
comment|/** Class level rights */
name|ClassDetail
block|,
name|ClassEdit
block|,
comment|/** Curriculum rights */
name|CurriculumView
block|,
name|CurriculumDetail
block|,
name|CurriculumAdd
block|,
name|CurriculumEdit
block|,
name|CurriculumDelete
block|,
name|CurriculumMerge
block|,
name|CurriculumAdmin
block|; }
end_enum

end_unit

