begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 (University Timetabling Application)  * Copyright (C) 2010, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|gwt
operator|.
name|services
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
name|java
operator|.
name|util
operator|.
name|HashMap
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
name|Set
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|TreeSet
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
name|ClassAssignmentInterface
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
name|CurriculaException
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
name|CurriculumInterface
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
name|CurriculumInterface
operator|.
name|AcademicAreaInterface
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
name|CurriculumInterface
operator|.
name|AcademicClassificationInterface
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
name|CurriculumInterface
operator|.
name|MajorInterface
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
name|PageAccessException
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|gwt
operator|.
name|user
operator|.
name|client
operator|.
name|rpc
operator|.
name|AsyncCallback
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_interface
specifier|public
interface|interface
name|CurriculaServiceAsync
block|{
specifier|public
name|void
name|findCurricula
parameter_list|(
name|String
name|filter
parameter_list|,
name|AsyncCallback
argument_list|<
name|TreeSet
argument_list|<
name|CurriculumInterface
argument_list|>
argument_list|>
name|callback
parameter_list|)
throws|throws
name|CurriculaException
throws|,
name|PageAccessException
function_decl|;
specifier|public
name|void
name|loadClassifications
parameter_list|(
name|List
argument_list|<
name|Long
argument_list|>
name|curriculumIds
parameter_list|,
name|AsyncCallback
argument_list|<
name|List
argument_list|<
name|CurriculumInterface
operator|.
name|CurriculumClassificationInterface
argument_list|>
argument_list|>
name|callback
parameter_list|)
throws|throws
name|CurriculaException
throws|,
name|PageAccessException
function_decl|;
specifier|public
name|void
name|computeEnrollmentsAndLastLikes
parameter_list|(
name|Long
name|acadAreaId
parameter_list|,
name|List
argument_list|<
name|Long
argument_list|>
name|majors
parameter_list|,
name|AsyncCallback
argument_list|<
name|HashMap
argument_list|<
name|String
argument_list|,
name|CurriculumInterface
operator|.
name|CurriculumStudentsInterface
index|[]
argument_list|>
argument_list|>
name|callback
parameter_list|)
throws|throws
name|CurriculaException
throws|,
name|PageAccessException
function_decl|;
specifier|public
name|void
name|loadAcademicAreas
parameter_list|(
name|AsyncCallback
argument_list|<
name|TreeSet
argument_list|<
name|CurriculumInterface
operator|.
name|AcademicAreaInterface
argument_list|>
argument_list|>
name|callback
parameter_list|)
throws|throws
name|CurriculaException
throws|,
name|PageAccessException
function_decl|;
specifier|public
name|void
name|loadAcademicClassifications
parameter_list|(
name|AsyncCallback
argument_list|<
name|TreeSet
argument_list|<
name|CurriculumInterface
operator|.
name|AcademicClassificationInterface
argument_list|>
argument_list|>
name|callback
parameter_list|)
throws|throws
name|CurriculaException
throws|,
name|PageAccessException
function_decl|;
specifier|public
name|void
name|loadDepartments
parameter_list|(
name|AsyncCallback
argument_list|<
name|TreeSet
argument_list|<
name|CurriculumInterface
operator|.
name|DepartmentInterface
argument_list|>
argument_list|>
name|callback
parameter_list|)
throws|throws
name|CurriculaException
throws|,
name|PageAccessException
function_decl|;
specifier|public
name|void
name|loadMajors
parameter_list|(
name|Long
name|curriculumId
parameter_list|,
name|Long
name|academicAreaId
parameter_list|,
name|AsyncCallback
argument_list|<
name|TreeSet
argument_list|<
name|CurriculumInterface
operator|.
name|MajorInterface
argument_list|>
argument_list|>
name|callback
parameter_list|)
throws|throws
name|CurriculaException
throws|,
name|PageAccessException
function_decl|;
specifier|public
name|void
name|lastCurriculaFilter
parameter_list|(
name|AsyncCallback
argument_list|<
name|String
argument_list|>
name|callback
parameter_list|)
throws|throws
name|CurriculaException
throws|,
name|PageAccessException
function_decl|;
specifier|public
name|void
name|loadCurriculum
parameter_list|(
name|Long
name|curriculumId
parameter_list|,
name|AsyncCallback
argument_list|<
name|CurriculumInterface
argument_list|>
name|callback
parameter_list|)
throws|throws
name|CurriculaException
throws|,
name|PageAccessException
function_decl|;
specifier|public
name|void
name|saveCurriculum
parameter_list|(
name|CurriculumInterface
name|curriculum
parameter_list|,
name|AsyncCallback
argument_list|<
name|Long
argument_list|>
name|callback
parameter_list|)
throws|throws
name|CurriculaException
throws|,
name|PageAccessException
function_decl|;
specifier|public
name|void
name|deleteCurriculum
parameter_list|(
name|Long
name|curriculumId
parameter_list|,
name|AsyncCallback
argument_list|<
name|Boolean
argument_list|>
name|callback
parameter_list|)
throws|throws
name|CurriculaException
throws|,
name|PageAccessException
function_decl|;
specifier|public
name|void
name|deleteCurricula
parameter_list|(
name|Set
argument_list|<
name|Long
argument_list|>
name|curriculumIds
parameter_list|,
name|AsyncCallback
argument_list|<
name|Boolean
argument_list|>
name|callback
parameter_list|)
throws|throws
name|CurriculaException
throws|,
name|PageAccessException
function_decl|;
specifier|public
name|void
name|mergeCurricula
parameter_list|(
name|Set
argument_list|<
name|Long
argument_list|>
name|curriculumIds
parameter_list|,
name|AsyncCallback
argument_list|<
name|Boolean
argument_list|>
name|callback
parameter_list|)
throws|throws
name|CurriculaException
throws|,
name|PageAccessException
function_decl|;
specifier|public
name|void
name|findCurriculaForACourse
parameter_list|(
name|String
name|courseName
parameter_list|,
name|AsyncCallback
argument_list|<
name|TreeSet
argument_list|<
name|CurriculumInterface
argument_list|>
argument_list|>
name|callback
parameter_list|)
throws|throws
name|CurriculaException
throws|,
name|PageAccessException
function_decl|;
specifier|public
name|void
name|findCurriculaForAnInstructionalOffering
parameter_list|(
name|Long
name|offeringId
parameter_list|,
name|AsyncCallback
argument_list|<
name|TreeSet
argument_list|<
name|CurriculumInterface
argument_list|>
argument_list|>
name|callback
parameter_list|)
throws|throws
name|CurriculaException
throws|,
name|PageAccessException
function_decl|;
specifier|public
name|void
name|saveClassifications
parameter_list|(
name|List
argument_list|<
name|CurriculumInterface
argument_list|>
name|curricula
parameter_list|,
name|AsyncCallback
argument_list|<
name|Boolean
argument_list|>
name|callback
parameter_list|)
throws|throws
name|CurriculaException
throws|,
name|PageAccessException
function_decl|;
specifier|public
name|void
name|listCourseOfferings
parameter_list|(
name|String
name|query
parameter_list|,
name|Integer
name|limit
parameter_list|,
name|AsyncCallback
argument_list|<
name|Collection
argument_list|<
name|ClassAssignmentInterface
operator|.
name|CourseAssignment
argument_list|>
argument_list|>
name|callback
parameter_list|)
throws|throws
name|CurriculaException
throws|,
name|PageAccessException
function_decl|;
specifier|public
name|void
name|retrieveCourseDetails
parameter_list|(
name|String
name|course
parameter_list|,
name|AsyncCallback
argument_list|<
name|String
argument_list|>
name|callback
parameter_list|)
throws|throws
name|CurriculaException
throws|,
name|PageAccessException
function_decl|;
specifier|public
name|void
name|listClasses
parameter_list|(
name|String
name|course
parameter_list|,
name|AsyncCallback
argument_list|<
name|Collection
argument_list|<
name|ClassAssignmentInterface
operator|.
name|ClassAssignment
argument_list|>
argument_list|>
name|callback
parameter_list|)
throws|throws
name|CurriculaException
throws|,
name|PageAccessException
function_decl|;
specifier|public
name|void
name|getApplicationProperty
parameter_list|(
name|String
index|[]
name|name
parameter_list|,
name|AsyncCallback
argument_list|<
name|String
index|[]
argument_list|>
name|callback
parameter_list|)
throws|throws
name|CurriculaException
throws|,
name|PageAccessException
function_decl|;
specifier|public
name|void
name|canAddCurriculum
parameter_list|(
name|AsyncCallback
argument_list|<
name|Boolean
argument_list|>
name|callback
parameter_list|)
throws|throws
name|CurriculaException
throws|,
name|PageAccessException
function_decl|;
specifier|public
name|void
name|isAdmin
parameter_list|(
name|AsyncCallback
argument_list|<
name|Boolean
argument_list|>
name|callback
parameter_list|)
throws|throws
name|CurriculaException
throws|,
name|PageAccessException
function_decl|;
specifier|public
name|void
name|makeupCurriculaFromLastLikeDemands
parameter_list|(
name|boolean
name|lastLike
parameter_list|,
name|AsyncCallback
argument_list|<
name|Boolean
argument_list|>
name|callback
parameter_list|)
throws|throws
name|CurriculaException
throws|,
name|PageAccessException
function_decl|;
specifier|public
name|void
name|updateCurriculaByProjections
parameter_list|(
name|Set
argument_list|<
name|Long
argument_list|>
name|curriculumIds
parameter_list|,
name|boolean
name|updateCurriculumCourses
parameter_list|,
name|AsyncCallback
argument_list|<
name|Boolean
argument_list|>
name|callback
parameter_list|)
throws|throws
name|CurriculaException
throws|,
name|PageAccessException
function_decl|;
specifier|public
name|void
name|populateCourseProjectedDemands
parameter_list|(
name|boolean
name|includeOtherStudents
parameter_list|,
name|AsyncCallback
argument_list|<
name|Boolean
argument_list|>
name|callback
parameter_list|)
throws|throws
name|CurriculaException
throws|,
name|PageAccessException
function_decl|;
specifier|public
name|void
name|populateCourseProjectedDemands
parameter_list|(
name|boolean
name|includeOtherStudents
parameter_list|,
name|Long
name|offeringId
parameter_list|,
name|AsyncCallback
argument_list|<
name|Boolean
argument_list|>
name|callback
parameter_list|)
throws|throws
name|CurriculaException
throws|,
name|PageAccessException
function_decl|;
specifier|public
name|void
name|loadProjectionRules
parameter_list|(
name|AsyncCallback
argument_list|<
name|HashMap
argument_list|<
name|AcademicAreaInterface
argument_list|,
name|HashMap
argument_list|<
name|MajorInterface
argument_list|,
name|HashMap
argument_list|<
name|AcademicClassificationInterface
argument_list|,
name|Number
index|[]
argument_list|>
argument_list|>
argument_list|>
argument_list|>
name|callback
parameter_list|)
throws|throws
name|CurriculaException
throws|,
name|PageAccessException
function_decl|;
specifier|public
name|void
name|saveProjectionRules
parameter_list|(
name|HashMap
argument_list|<
name|AcademicAreaInterface
argument_list|,
name|HashMap
argument_list|<
name|MajorInterface
argument_list|,
name|HashMap
argument_list|<
name|AcademicClassificationInterface
argument_list|,
name|Number
index|[]
argument_list|>
argument_list|>
argument_list|>
name|rules
parameter_list|,
name|AsyncCallback
argument_list|<
name|Boolean
argument_list|>
name|callback
parameter_list|)
throws|throws
name|CurriculaException
throws|,
name|PageAccessException
function_decl|;
specifier|public
name|void
name|canEditProjectionRules
parameter_list|(
name|AsyncCallback
argument_list|<
name|Boolean
argument_list|>
name|callback
parameter_list|)
throws|throws
name|CurriculaException
throws|,
name|PageAccessException
function_decl|;
block|}
end_interface

end_unit

