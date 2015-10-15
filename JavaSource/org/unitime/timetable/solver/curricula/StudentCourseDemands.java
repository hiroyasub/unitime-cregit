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
name|solver
operator|.
name|curricula
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
name|cpsolver
operator|.
name|ifs
operator|.
name|util
operator|.
name|Progress
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
name|AcademicAreaClassification
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
name|CourseOffering
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
name|Curriculum
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
name|CurriculumClassification
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
name|InstructionalOffering
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
name|PosMajor
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
name|Session
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
name|Student
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
name|CourseOfferingDAO
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_interface
specifier|public
interface|interface
name|StudentCourseDemands
block|{
comment|/** 	 * Called only once 	 * @param hibSession opened hibernate session 	 * @param progress progress to print messages 	 * @param session current academic session 	 * @param offerings instructional offerings of the problem that is being loaded 	 */
specifier|public
name|void
name|init
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|Progress
name|progress
parameter_list|,
name|Session
name|session
parameter_list|,
name|Collection
argument_list|<
name|InstructionalOffering
argument_list|>
name|offerings
parameter_list|)
function_decl|;
comment|/** 	 * Called once for each course 	 * @param course course for which demands are requested 	 * @return set of students (their unique ids, and weights) that request the course 	 */
specifier|public
name|Set
argument_list|<
name|WeightedStudentId
argument_list|>
name|getDemands
parameter_list|(
name|CourseOffering
name|course
parameter_list|)
function_decl|;
comment|/** 	 * Returns enrollment priority, i.e., an importance of a course request to a student  	 * @param studentId identification of a student, e.g., as returned by {@link StudentCourseDemands#getDemands(CourseOffering)} 	 * @param course one of the course offerings requested by the student 	 * @return<code>null</code> if not implemented, 0.0 no priority, 1.0 highest priority 	 */
specifier|public
name|Double
name|getEnrollmentPriority
parameter_list|(
name|Long
name|studentId
parameter_list|,
name|Long
name|courseId
parameter_list|)
function_decl|;
comment|/** 	 * Return true if students are made up (i.e, it does not make any sense to save them with the solution). 	 */
specifier|public
name|boolean
name|isMakingUpStudents
parameter_list|()
function_decl|;
comment|/** 	 * Return true if students are real students (not last-like) for which the student class enrollments 	 * apply (real student class enrollments can be loaded instead of solution's student class assignments). 	 */
specifier|public
name|boolean
name|canUseStudentClassEnrollmentsAsSolution
parameter_list|()
function_decl|;
comment|/** 	 * Return true if students should be weghted so that the offering is filled in completely. 	 */
specifier|public
name|boolean
name|isWeightStudentsToFillUpOffering
parameter_list|()
function_decl|;
comment|/** 	 * List of courses for a student 	 */
specifier|public
name|Set
argument_list|<
name|WeightedCourseOffering
argument_list|>
name|getCourses
parameter_list|(
name|Long
name|studentId
parameter_list|)
function_decl|;
specifier|public
specifier|static
class|class
name|AreaCode
implements|implements
name|Comparable
argument_list|<
name|AreaCode
argument_list|>
block|{
name|String
name|iArea
decl_stmt|,
name|iCode
decl_stmt|;
specifier|public
name|AreaCode
parameter_list|(
name|String
name|area
parameter_list|,
name|String
name|code
parameter_list|)
block|{
name|iArea
operator|=
name|area
expr_stmt|;
name|iCode
operator|=
name|code
expr_stmt|;
block|}
specifier|public
name|String
name|getArea
parameter_list|()
block|{
return|return
name|iArea
return|;
block|}
specifier|public
name|String
name|getCode
parameter_list|()
block|{
return|return
name|iCode
return|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|getArea
argument_list|()
operator|+
operator|(
name|getCode
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|?
literal|""
else|:
literal|" "
operator|+
name|getCode
argument_list|()
operator|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|toString
argument_list|()
operator|.
name|hashCode
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
if|if
condition|(
name|o
operator|==
literal|null
operator|||
operator|!
operator|(
name|o
operator|instanceof
name|AreaCode
operator|)
condition|)
return|return
literal|false
return|;
return|return
name|toString
argument_list|()
operator|.
name|equals
argument_list|(
name|o
operator|.
name|toString
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|compareTo
parameter_list|(
name|AreaCode
name|ac
parameter_list|)
block|{
return|return
name|toString
argument_list|()
operator|.
name|compareTo
argument_list|(
name|ac
operator|.
name|toString
argument_list|()
argument_list|)
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|WeightedStudentId
block|{
specifier|private
name|long
name|iStudentId
decl_stmt|;
specifier|private
name|float
name|iWeight
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|AreaCode
argument_list|>
name|iAreas
init|=
operator|new
name|TreeSet
argument_list|<
name|AreaCode
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|AreaCode
argument_list|>
name|iMajors
init|=
operator|new
name|TreeSet
argument_list|<
name|AreaCode
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|String
argument_list|>
name|iCurricula
init|=
operator|new
name|TreeSet
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
specifier|public
name|WeightedStudentId
parameter_list|(
name|Student
name|student
parameter_list|,
name|ProjectionsProvider
name|projections
parameter_list|)
block|{
name|iStudentId
operator|=
name|student
operator|.
name|getUniqueId
argument_list|()
expr_stmt|;
name|iWeight
operator|=
literal|1.0f
expr_stmt|;
name|float
name|rule
init|=
literal|1.0f
decl_stmt|;
name|int
name|cnt
init|=
literal|0
decl_stmt|;
for|for
control|(
name|AcademicAreaClassification
name|aac
range|:
name|student
operator|.
name|getAcademicAreaClassifications
argument_list|()
control|)
block|{
name|iAreas
operator|.
name|add
argument_list|(
operator|new
name|AreaCode
argument_list|(
name|aac
operator|.
name|getAcademicArea
argument_list|()
operator|.
name|getAcademicAreaAbbreviation
argument_list|()
argument_list|,
name|aac
operator|.
name|getAcademicClassification
argument_list|()
operator|.
name|getCode
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|boolean
name|hasMajor
init|=
literal|false
decl_stmt|;
for|for
control|(
name|PosMajor
name|major
range|:
name|student
operator|.
name|getPosMajors
argument_list|()
control|)
block|{
if|if
condition|(
name|major
operator|.
name|getAcademicAreas
argument_list|()
operator|.
name|contains
argument_list|(
name|aac
operator|.
name|getAcademicArea
argument_list|()
argument_list|)
condition|)
block|{
if|if
condition|(
name|projections
operator|!=
literal|null
condition|)
block|{
name|rule
operator|*=
name|projections
operator|.
name|getProjection
argument_list|(
name|aac
operator|.
name|getAcademicArea
argument_list|()
operator|.
name|getAcademicAreaAbbreviation
argument_list|()
argument_list|,
name|aac
operator|.
name|getAcademicClassification
argument_list|()
operator|.
name|getCode
argument_list|()
argument_list|,
name|major
operator|.
name|getCode
argument_list|()
argument_list|)
expr_stmt|;
name|cnt
operator|++
expr_stmt|;
name|hasMajor
operator|=
literal|true
expr_stmt|;
block|}
name|iMajors
operator|.
name|add
argument_list|(
operator|new
name|AreaCode
argument_list|(
name|aac
operator|.
name|getAcademicArea
argument_list|()
operator|.
name|getAcademicAreaAbbreviation
argument_list|()
argument_list|,
name|major
operator|.
name|getCode
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
operator|!
name|hasMajor
operator|&&
name|projections
operator|!=
literal|null
condition|)
block|{
name|rule
operator|*=
name|projections
operator|.
name|getProjection
argument_list|(
name|aac
operator|.
name|getAcademicArea
argument_list|()
operator|.
name|getAcademicAreaAbbreviation
argument_list|()
argument_list|,
name|aac
operator|.
name|getAcademicClassification
argument_list|()
operator|.
name|getCode
argument_list|()
argument_list|,
literal|""
argument_list|)
expr_stmt|;
name|cnt
operator|++
expr_stmt|;
block|}
block|}
if|if
condition|(
name|cnt
operator|==
literal|1
condition|)
name|iWeight
operator|=
name|rule
expr_stmt|;
if|else if
condition|(
name|cnt
operator|>
literal|1
condition|)
name|iWeight
operator|=
operator|(
name|float
operator|)
name|Math
operator|.
name|pow
argument_list|(
name|rule
argument_list|,
literal|1.0
operator|/
name|cnt
argument_list|)
expr_stmt|;
block|}
specifier|public
name|WeightedStudentId
parameter_list|(
name|Long
name|studentId
parameter_list|,
name|CurriculumClassification
name|cc
parameter_list|,
name|ProjectionsProvider
name|projections
parameter_list|)
block|{
name|Curriculum
name|curriculum
init|=
name|cc
operator|.
name|getCurriculum
argument_list|()
decl_stmt|;
name|iWeight
operator|=
literal|1.0f
expr_stmt|;
if|if
condition|(
name|projections
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|curriculum
operator|.
name|getMajors
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|iWeight
operator|=
name|projections
operator|.
name|getProjection
argument_list|(
name|curriculum
operator|.
name|getAcademicArea
argument_list|()
operator|.
name|getAcademicAreaAbbreviation
argument_list|()
argument_list|,
name|cc
operator|.
name|getAcademicClassification
argument_list|()
operator|.
name|getCode
argument_list|()
argument_list|,
literal|""
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|curriculum
operator|.
name|getMajors
argument_list|()
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
block|{
for|for
control|(
name|PosMajor
name|m
range|:
name|curriculum
operator|.
name|getMajors
argument_list|()
control|)
name|iWeight
operator|=
name|projections
operator|.
name|getProjection
argument_list|(
name|curriculum
operator|.
name|getAcademicArea
argument_list|()
operator|.
name|getAcademicAreaAbbreviation
argument_list|()
argument_list|,
name|cc
operator|.
name|getAcademicClassification
argument_list|()
operator|.
name|getCode
argument_list|()
argument_list|,
name|m
operator|.
name|getCode
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|double
name|rule
init|=
literal|1.0
decl_stmt|;
for|for
control|(
name|PosMajor
name|m
range|:
name|curriculum
operator|.
name|getMajors
argument_list|()
control|)
name|rule
operator|*=
name|projections
operator|.
name|getProjection
argument_list|(
name|curriculum
operator|.
name|getAcademicArea
argument_list|()
operator|.
name|getAcademicAreaAbbreviation
argument_list|()
argument_list|,
name|cc
operator|.
name|getAcademicClassification
argument_list|()
operator|.
name|getCode
argument_list|()
argument_list|,
name|m
operator|.
name|getCode
argument_list|()
argument_list|)
expr_stmt|;
name|iWeight
operator|=
operator|(
name|float
operator|)
name|Math
operator|.
name|pow
argument_list|(
name|rule
argument_list|,
literal|1.0
operator|/
name|curriculum
operator|.
name|getMajors
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
name|iAreas
operator|.
name|add
argument_list|(
operator|new
name|AreaCode
argument_list|(
name|curriculum
operator|.
name|getAcademicArea
argument_list|()
operator|.
name|getAcademicAreaAbbreviation
argument_list|()
argument_list|,
name|cc
operator|.
name|getAcademicClassification
argument_list|()
operator|.
name|getCode
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
for|for
control|(
name|PosMajor
name|major
range|:
name|curriculum
operator|.
name|getMajors
argument_list|()
control|)
name|iMajors
operator|.
name|add
argument_list|(
operator|new
name|AreaCode
argument_list|(
name|curriculum
operator|.
name|getAcademicArea
argument_list|()
operator|.
name|getAcademicAreaAbbreviation
argument_list|()
argument_list|,
name|major
operator|.
name|getCode
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|iCurricula
operator|.
name|add
argument_list|(
name|curriculum
operator|.
name|getAbbv
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
name|WeightedStudentId
parameter_list|(
name|Student
name|student
parameter_list|)
block|{
name|this
argument_list|(
name|student
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
specifier|public
name|WeightedStudentId
parameter_list|(
name|Long
name|studentId
parameter_list|,
name|CurriculumClassification
name|cc
parameter_list|)
block|{
name|this
argument_list|(
name|studentId
argument_list|,
name|cc
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
specifier|public
name|long
name|getStudentId
parameter_list|()
block|{
return|return
name|iStudentId
return|;
block|}
specifier|public
name|float
name|getWeight
parameter_list|()
block|{
return|return
name|iWeight
return|;
block|}
specifier|public
name|void
name|setWeight
parameter_list|(
name|float
name|weight
parameter_list|)
block|{
name|iWeight
operator|=
name|weight
expr_stmt|;
block|}
specifier|public
name|void
name|setCurriculum
parameter_list|(
name|String
name|curriculum
parameter_list|)
block|{
name|iCurricula
operator|.
name|clear
argument_list|()
expr_stmt|;
name|iCurricula
operator|.
name|add
argument_list|(
name|curriculum
argument_list|)
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasArea
parameter_list|(
name|String
name|areaAbbv
parameter_list|)
block|{
for|for
control|(
name|AreaCode
name|a
range|:
name|iAreas
control|)
if|if
condition|(
name|a
operator|.
name|getArea
argument_list|()
operator|.
name|equals
argument_list|(
name|areaAbbv
argument_list|)
condition|)
return|return
literal|true
return|;
return|return
literal|false
return|;
block|}
specifier|public
name|boolean
name|hasClassification
parameter_list|(
name|String
name|areaAbbv
parameter_list|,
name|String
name|clasfCode
parameter_list|)
block|{
for|for
control|(
name|AreaCode
name|a
range|:
name|iAreas
control|)
if|if
condition|(
name|a
operator|.
name|getArea
argument_list|()
operator|.
name|equals
argument_list|(
name|areaAbbv
argument_list|)
operator|&&
name|a
operator|.
name|getCode
argument_list|()
operator|.
name|equals
argument_list|(
name|clasfCode
argument_list|)
condition|)
return|return
literal|true
return|;
return|return
literal|false
return|;
block|}
specifier|public
name|boolean
name|hasMajor
parameter_list|(
name|String
name|areaAbbv
parameter_list|,
name|String
name|majorCode
parameter_list|)
block|{
for|for
control|(
name|AreaCode
name|a
range|:
name|iMajors
control|)
if|if
condition|(
name|a
operator|.
name|getArea
argument_list|()
operator|.
name|equals
argument_list|(
name|areaAbbv
argument_list|)
operator|&&
name|a
operator|.
name|getCode
argument_list|()
operator|.
name|equals
argument_list|(
name|majorCode
argument_list|)
condition|)
return|return
literal|true
return|;
return|return
literal|false
return|;
block|}
specifier|public
name|Set
argument_list|<
name|AreaCode
argument_list|>
name|getAreas
parameter_list|()
block|{
return|return
name|iAreas
return|;
block|}
specifier|public
name|Set
argument_list|<
name|String
argument_list|>
name|getMajors
parameter_list|(
name|String
name|area
parameter_list|)
block|{
name|Set
argument_list|<
name|String
argument_list|>
name|ret
init|=
operator|new
name|TreeSet
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|AreaCode
name|m
range|:
name|iMajors
control|)
if|if
condition|(
name|m
operator|.
name|getArea
argument_list|()
operator|.
name|equals
argument_list|(
name|area
argument_list|)
condition|)
name|ret
operator|.
name|add
argument_list|(
name|m
operator|.
name|getCode
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|ret
return|;
block|}
specifier|public
name|String
name|getArea
parameter_list|()
block|{
return|return
name|toString
argument_list|(
name|iAreas
argument_list|,
literal|true
argument_list|,
literal|","
argument_list|)
return|;
block|}
specifier|public
name|String
name|getClasf
parameter_list|()
block|{
return|return
name|toString
argument_list|(
name|iAreas
argument_list|,
literal|false
argument_list|,
literal|","
argument_list|)
return|;
block|}
specifier|public
name|String
name|getMajor
parameter_list|()
block|{
return|return
name|toString
argument_list|(
name|iMajors
argument_list|,
literal|false
argument_list|,
literal|","
argument_list|)
return|;
block|}
specifier|public
name|String
name|getCurriculum
parameter_list|()
block|{
name|StringBuffer
name|ret
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
if|if
condition|(
name|iCurricula
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
for|for
control|(
name|AreaCode
name|a
range|:
name|iAreas
control|)
block|{
name|StringBuffer
name|majors
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
for|for
control|(
name|AreaCode
name|m
range|:
name|iMajors
control|)
block|{
if|if
condition|(
name|a
operator|.
name|getArea
argument_list|()
operator|.
name|equals
argument_list|(
name|m
operator|.
name|getArea
argument_list|()
argument_list|)
condition|)
block|{
if|if
condition|(
name|majors
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|majors
operator|.
name|append
argument_list|(
literal|","
argument_list|)
expr_stmt|;
name|majors
operator|.
name|append
argument_list|(
name|m
operator|.
name|getCode
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|ret
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|ret
operator|.
name|append
argument_list|(
literal|", "
argument_list|)
expr_stmt|;
name|ret
operator|.
name|append
argument_list|(
name|a
operator|.
name|getArea
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|majors
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|ret
operator|.
name|append
argument_list|(
literal|"/"
argument_list|)
expr_stmt|;
name|ret
operator|.
name|append
argument_list|(
name|majors
argument_list|)
expr_stmt|;
block|}
block|}
block|}
else|else
block|{
for|for
control|(
name|String
name|curriculum
range|:
name|iCurricula
control|)
block|{
if|if
condition|(
name|ret
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|ret
operator|.
name|append
argument_list|(
literal|", "
argument_list|)
expr_stmt|;
name|ret
operator|.
name|append
argument_list|(
name|curriculum
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|ret
operator|.
name|toString
argument_list|()
return|;
block|}
specifier|private
specifier|static
name|String
name|toString
parameter_list|(
name|Set
argument_list|<
name|AreaCode
argument_list|>
name|set
parameter_list|,
name|boolean
name|area
parameter_list|,
name|String
name|delim
parameter_list|)
block|{
if|if
condition|(
name|set
operator|==
literal|null
operator|||
name|set
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
literal|null
return|;
name|StringBuffer
name|ret
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
for|for
control|(
name|AreaCode
name|s
range|:
name|set
control|)
block|{
if|if
condition|(
name|ret
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|ret
operator|.
name|append
argument_list|(
name|delim
argument_list|)
expr_stmt|;
name|ret
operator|.
name|append
argument_list|(
name|area
condition|?
name|s
operator|.
name|getArea
argument_list|()
else|:
name|s
operator|.
name|getCode
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|ret
operator|.
name|toString
argument_list|()
return|;
block|}
specifier|public
name|boolean
name|match
parameter_list|(
name|String
name|areaAbbv
parameter_list|,
name|Set
argument_list|<
name|String
argument_list|>
name|majors
parameter_list|)
block|{
for|for
control|(
name|AreaCode
name|a
range|:
name|iAreas
control|)
block|{
if|if
condition|(
name|a
operator|.
name|getArea
argument_list|()
operator|.
name|equals
argument_list|(
name|areaAbbv
argument_list|)
condition|)
block|{
for|for
control|(
name|AreaCode
name|m
range|:
name|iMajors
control|)
block|{
if|if
condition|(
name|m
operator|.
name|getArea
argument_list|()
operator|.
name|equals
argument_list|(
name|areaAbbv
argument_list|)
operator|&&
name|majors
operator|.
name|contains
argument_list|(
name|m
operator|.
name|getCode
argument_list|()
argument_list|)
condition|)
return|return
literal|true
return|;
block|}
block|}
block|}
return|return
literal|false
return|;
block|}
specifier|public
name|boolean
name|match
parameter_list|(
name|CurriculumClassification
name|clasf
parameter_list|)
block|{
for|for
control|(
name|AreaCode
name|a
range|:
name|iAreas
control|)
block|{
if|if
condition|(
name|a
operator|.
name|getArea
argument_list|()
operator|.
name|equals
argument_list|(
name|clasf
operator|.
name|getCurriculum
argument_list|()
operator|.
name|getAcademicArea
argument_list|()
operator|.
name|getAcademicAreaAbbreviation
argument_list|()
argument_list|)
operator|&&
name|a
operator|.
name|getCode
argument_list|()
operator|.
name|equals
argument_list|(
name|clasf
operator|.
name|getAcademicClassification
argument_list|()
operator|.
name|getCode
argument_list|()
argument_list|)
condition|)
block|{
if|if
condition|(
name|clasf
operator|.
name|getCurriculum
argument_list|()
operator|.
name|isMultipleMajors
argument_list|()
condition|)
block|{
for|for
control|(
name|PosMajor
name|major
range|:
name|clasf
operator|.
name|getCurriculum
argument_list|()
operator|.
name|getMajors
argument_list|()
control|)
block|{
name|boolean
name|found
init|=
literal|false
decl_stmt|;
for|for
control|(
name|AreaCode
name|m
range|:
name|iMajors
control|)
block|{
if|if
condition|(
name|m
operator|.
name|getArea
argument_list|()
operator|.
name|equals
argument_list|(
name|a
operator|.
name|getArea
argument_list|()
argument_list|)
operator|&&
name|m
operator|.
name|getCode
argument_list|()
operator|.
name|equals
argument_list|(
name|major
operator|.
name|getCode
argument_list|()
argument_list|)
condition|)
block|{
name|found
operator|=
literal|true
expr_stmt|;
break|break;
block|}
block|}
if|if
condition|(
name|found
condition|)
return|return
literal|true
return|;
block|}
block|}
else|else
block|{
for|for
control|(
name|PosMajor
name|major
range|:
name|clasf
operator|.
name|getCurriculum
argument_list|()
operator|.
name|getMajors
argument_list|()
control|)
block|{
for|for
control|(
name|AreaCode
name|m
range|:
name|iMajors
control|)
block|{
if|if
condition|(
name|m
operator|.
name|getArea
argument_list|()
operator|.
name|equals
argument_list|(
name|a
operator|.
name|getArea
argument_list|()
argument_list|)
operator|&&
name|m
operator|.
name|getCode
argument_list|()
operator|.
name|equals
argument_list|(
name|major
operator|.
name|getCode
argument_list|()
argument_list|)
condition|)
return|return
literal|true
return|;
block|}
block|}
block|}
block|}
block|}
return|return
literal|false
return|;
block|}
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
operator|new
name|Long
argument_list|(
name|getStudentId
argument_list|()
argument_list|)
operator|.
name|hashCode
argument_list|()
return|;
block|}
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
if|if
condition|(
name|o
operator|==
literal|null
operator|||
operator|!
operator|(
name|o
operator|instanceof
name|WeightedStudentId
operator|)
condition|)
return|return
literal|false
return|;
return|return
name|getStudentId
argument_list|()
operator|==
operator|(
operator|(
name|WeightedStudentId
operator|)
name|o
operator|)
operator|.
name|getStudentId
argument_list|()
return|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|String
operator|.
name|valueOf
argument_list|(
name|getStudentId
argument_list|()
argument_list|)
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|WeightedCourseOffering
block|{
specifier|private
specifier|transient
name|CourseOffering
name|iCourseOffering
init|=
literal|null
decl_stmt|;
specifier|private
name|long
name|iCourseOfferingId
decl_stmt|;
specifier|private
name|float
name|iWeight
init|=
literal|1.0f
decl_stmt|;
specifier|public
name|WeightedCourseOffering
parameter_list|(
name|CourseOffering
name|courseOffering
parameter_list|)
block|{
name|iCourseOffering
operator|=
name|courseOffering
expr_stmt|;
name|iCourseOfferingId
operator|=
name|courseOffering
operator|.
name|getUniqueId
argument_list|()
expr_stmt|;
block|}
specifier|public
name|WeightedCourseOffering
parameter_list|(
name|Long
name|courseOfferingId
parameter_list|)
block|{
name|iCourseOfferingId
operator|=
name|courseOfferingId
expr_stmt|;
block|}
specifier|public
name|WeightedCourseOffering
parameter_list|(
name|CourseOffering
name|courseOffering
parameter_list|,
name|float
name|weight
parameter_list|)
block|{
name|this
argument_list|(
name|courseOffering
argument_list|)
expr_stmt|;
name|iWeight
operator|=
name|weight
expr_stmt|;
block|}
specifier|public
name|WeightedCourseOffering
parameter_list|(
name|Long
name|courseOfferingId
parameter_list|,
name|float
name|weight
parameter_list|)
block|{
name|this
argument_list|(
name|courseOfferingId
argument_list|)
expr_stmt|;
name|iWeight
operator|=
name|weight
expr_stmt|;
block|}
specifier|public
name|Long
name|getCourseOfferingId
parameter_list|()
block|{
return|return
name|iCourseOfferingId
return|;
block|}
specifier|public
name|CourseOffering
name|getCourseOffering
parameter_list|()
block|{
if|if
condition|(
name|iCourseOffering
operator|==
literal|null
condition|)
name|iCourseOffering
operator|=
name|CourseOfferingDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|iCourseOfferingId
argument_list|)
expr_stmt|;
return|return
name|iCourseOffering
return|;
block|}
specifier|public
name|float
name|getWeight
parameter_list|()
block|{
return|return
name|iWeight
return|;
block|}
block|}
specifier|public
specifier|static
interface|interface
name|ProjectionsProvider
block|{
specifier|public
name|float
name|getProjection
parameter_list|(
name|String
name|areaAbbv
parameter_list|,
name|String
name|clasfCode
parameter_list|,
name|String
name|majorCode
parameter_list|)
function_decl|;
block|}
block|}
end_interface

end_unit

