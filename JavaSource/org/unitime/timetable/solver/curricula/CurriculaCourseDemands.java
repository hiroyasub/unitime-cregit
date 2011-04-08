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
name|ArrayList
import|;
end_import

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
name|HashSet
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Hashtable
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
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|Log
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|LogFactory
import|;
end_import

begin_import
import|import
name|org
operator|.
name|dom4j
operator|.
name|Document
import|;
end_import

begin_import
import|import
name|org
operator|.
name|dom4j
operator|.
name|DocumentHelper
import|;
end_import

begin_import
import|import
name|org
operator|.
name|dom4j
operator|.
name|Element
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
name|CurriculumCourse
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
name|CurriculumCourseGroup
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
name|solver
operator|.
name|curricula
operator|.
name|students
operator|.
name|CurModel
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
name|curricula
operator|.
name|students
operator|.
name|CurStudent
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
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
name|net
operator|.
name|sf
operator|.
name|cpsolver
operator|.
name|ifs
operator|.
name|util
operator|.
name|IdGenerator
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
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

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|CurriculaCourseDemands
implements|implements
name|StudentCourseDemands
block|{
specifier|private
specifier|static
name|Log
name|sLog
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|CurriculaCourseDemands
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|Set
argument_list|<
name|WeightedStudentId
argument_list|>
argument_list|>
name|iDemands
init|=
operator|new
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|Set
argument_list|<
name|WeightedStudentId
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|Set
argument_list|<
name|WeightedCourseOffering
argument_list|>
argument_list|>
name|iStudentRequests
init|=
operator|new
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|Set
argument_list|<
name|WeightedCourseOffering
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|IdGenerator
name|lastStudentId
init|=
operator|new
name|IdGenerator
argument_list|()
decl_stmt|;
specifier|protected
name|ProjectedStudentCourseDemands
name|iFallback
decl_stmt|;
specifier|private
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|Hashtable
argument_list|<
name|String
argument_list|,
name|Set
argument_list|<
name|String
argument_list|>
argument_list|>
argument_list|>
name|iLoadedCurricula
init|=
operator|new
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|Hashtable
argument_list|<
name|String
argument_list|,
name|Set
argument_list|<
name|String
argument_list|>
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|HashSet
argument_list|<
name|Long
argument_list|>
name|iCheckedCourses
init|=
operator|new
name|HashSet
argument_list|<
name|Long
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|boolean
name|iIncludeOtherStudents
init|=
literal|true
decl_stmt|;
specifier|public
name|CurriculaCourseDemands
parameter_list|(
name|DataProperties
name|properties
parameter_list|)
block|{
if|if
condition|(
name|properties
operator|!=
literal|null
condition|)
name|iFallback
operator|=
operator|new
name|ProjectedStudentCourseDemands
argument_list|(
name|properties
argument_list|)
expr_stmt|;
name|iIncludeOtherStudents
operator|=
name|properties
operator|.
name|getPropertyBoolean
argument_list|(
literal|"CurriculaCourseDemands.IncludeOtherStudents"
argument_list|,
name|iIncludeOtherStudents
argument_list|)
expr_stmt|;
block|}
specifier|public
name|CurriculaCourseDemands
parameter_list|()
block|{
name|this
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
specifier|public
name|boolean
name|isMakingUpStudents
parameter_list|()
block|{
return|return
literal|true
return|;
block|}
specifier|public
name|boolean
name|canUseStudentClassEnrollmentsAsSolution
parameter_list|()
block|{
return|return
literal|false
return|;
block|}
specifier|public
name|boolean
name|isWeightStudentsToFillUpOffering
parameter_list|()
block|{
return|return
literal|false
return|;
block|}
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
block|{
name|iFallback
operator|.
name|init
argument_list|(
name|hibSession
argument_list|,
name|progress
argument_list|,
name|session
argument_list|,
name|offerings
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|Curriculum
argument_list|>
name|curricula
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|offerings
operator|!=
literal|null
operator|&&
name|offerings
operator|.
name|size
argument_list|()
operator|<=
literal|1000
condition|)
block|{
name|String
name|courses
init|=
literal|""
decl_stmt|;
for|for
control|(
name|InstructionalOffering
name|offering
range|:
name|offerings
control|)
for|for
control|(
name|CourseOffering
name|course
range|:
name|offering
operator|.
name|getCourseOfferings
argument_list|()
control|)
block|{
if|if
condition|(
operator|!
name|courses
operator|.
name|isEmpty
argument_list|()
condition|)
name|courses
operator|+=
literal|","
expr_stmt|;
name|courses
operator|+=
name|course
operator|.
name|getUniqueId
argument_list|()
expr_stmt|;
block|}
name|curricula
operator|=
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"select distinct c from CurriculumCourse cc inner join cc.classification.curriculum c where "
operator|+
literal|"c.academicArea.session.uniqueId = :sessionId and cc.course.uniqueId in ("
operator|+
name|courses
operator|+
literal|")"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|list
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|curricula
operator|=
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"select c from Curriculum c where c.academicArea.session.uniqueId = :sessionId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|list
argument_list|()
expr_stmt|;
block|}
name|progress
operator|.
name|setPhase
argument_list|(
literal|"Loading curricula"
argument_list|,
name|curricula
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|Curriculum
name|curriculum
range|:
name|curricula
control|)
block|{
for|for
control|(
name|CurriculumClassification
name|clasf
range|:
name|curriculum
operator|.
name|getClassifications
argument_list|()
control|)
block|{
name|init
argument_list|(
name|hibSession
argument_list|,
name|clasf
argument_list|)
expr_stmt|;
block|}
name|progress
operator|.
name|incProgress
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|iDemands
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|progress
operator|.
name|warn
argument_list|(
literal|"There are no curricula, using projected course demands instead."
argument_list|)
expr_stmt|;
block|}
block|}
specifier|protected
name|String
name|getCacheName
parameter_list|()
block|{
return|return
literal|"curriculum-demands"
return|;
block|}
specifier|private
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
name|CurriculumClassification
name|clasf
parameter_list|)
block|{
if|if
condition|(
name|clasf
operator|.
name|getNrStudents
argument_list|()
operator|<=
literal|0
condition|)
return|return;
name|sLog
operator|.
name|debug
argument_list|(
literal|"Processing "
operator|+
name|clasf
operator|.
name|getCurriculum
argument_list|()
operator|.
name|getAbbv
argument_list|()
operator|+
literal|" "
operator|+
name|clasf
operator|.
name|getName
argument_list|()
operator|+
literal|" ... ("
operator|+
name|clasf
operator|.
name|getNrStudents
argument_list|()
operator|+
literal|" students, "
operator|+
name|clasf
operator|.
name|getCourses
argument_list|()
operator|.
name|size
argument_list|()
operator|+
literal|" courses)"
argument_list|)
expr_stmt|;
comment|// Create model
name|List
argument_list|<
name|CurStudent
argument_list|>
name|students
init|=
operator|new
name|ArrayList
argument_list|<
name|CurStudent
argument_list|>
argument_list|()
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
name|clasf
operator|.
name|getNrStudents
argument_list|()
condition|;
name|i
operator|++
control|)
name|students
operator|.
name|add
argument_list|(
operator|new
name|CurStudent
argument_list|(
operator|-
name|lastStudentId
operator|.
name|newId
argument_list|()
argument_list|,
literal|1f
argument_list|)
argument_list|)
expr_stmt|;
name|CurModel
name|m
init|=
operator|new
name|CurModel
argument_list|(
name|students
argument_list|)
decl_stmt|;
for|for
control|(
name|CurriculumCourse
name|course
range|:
name|clasf
operator|.
name|getCourses
argument_list|()
control|)
block|{
name|int
name|nrStudents
init|=
name|Math
operator|.
name|round
argument_list|(
name|clasf
operator|.
name|getNrStudents
argument_list|()
operator|*
name|course
operator|.
name|getPercShare
argument_list|()
argument_list|)
decl_stmt|;
name|m
operator|.
name|addCourse
argument_list|(
name|course
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|course
operator|.
name|getCourse
argument_list|()
operator|.
name|getCourseName
argument_list|()
argument_list|,
name|nrStudents
argument_list|)
expr_stmt|;
name|Hashtable
argument_list|<
name|String
argument_list|,
name|Set
argument_list|<
name|String
argument_list|>
argument_list|>
name|curricula
init|=
name|iLoadedCurricula
operator|.
name|get
argument_list|(
name|course
operator|.
name|getCourse
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|curricula
operator|==
literal|null
condition|)
block|{
name|curricula
operator|=
operator|new
name|Hashtable
argument_list|<
name|String
argument_list|,
name|Set
argument_list|<
name|String
argument_list|>
argument_list|>
argument_list|()
expr_stmt|;
name|iLoadedCurricula
operator|.
name|put
argument_list|(
name|course
operator|.
name|getCourse
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|curricula
argument_list|)
expr_stmt|;
block|}
name|Set
argument_list|<
name|String
argument_list|>
name|majors
init|=
name|curricula
operator|.
name|get
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
decl_stmt|;
if|if
condition|(
name|majors
operator|==
literal|null
condition|)
block|{
name|majors
operator|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|()
expr_stmt|;
name|curricula
operator|.
name|put
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
argument_list|,
name|majors
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|clasf
operator|.
name|getCurriculum
argument_list|()
operator|.
name|getMajors
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|majors
operator|.
name|add
argument_list|(
literal|""
argument_list|)
expr_stmt|;
block|}
else|else
block|{
for|for
control|(
name|PosMajor
name|mj
range|:
name|clasf
operator|.
name|getCurriculum
argument_list|()
operator|.
name|getMajors
argument_list|()
control|)
name|majors
operator|.
name|add
argument_list|(
name|mj
operator|.
name|getCode
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
name|computeTargetShare
argument_list|(
name|clasf
argument_list|,
name|m
argument_list|)
expr_stmt|;
name|m
operator|.
name|setStudentLimits
argument_list|()
expr_stmt|;
comment|// Load model from cache (if exists)
name|CurModel
name|cachedModel
init|=
literal|null
decl_stmt|;
name|Element
name|cache
init|=
operator|(
name|clasf
operator|.
name|getStudents
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
name|clasf
operator|.
name|getStudents
argument_list|()
operator|.
name|getRootElement
argument_list|()
operator|)
decl_stmt|;
if|if
condition|(
name|cache
operator|!=
literal|null
operator|&&
name|cache
operator|.
name|getName
argument_list|()
operator|.
name|equals
argument_list|(
name|getCacheName
argument_list|()
argument_list|)
condition|)
block|{
name|cachedModel
operator|=
name|CurModel
operator|.
name|loadFromXml
argument_list|(
name|cache
argument_list|)
expr_stmt|;
name|cachedModel
operator|.
name|setStudentLimits
argument_list|()
expr_stmt|;
block|}
comment|// Check the cached model
if|if
condition|(
name|cachedModel
operator|!=
literal|null
operator|&&
name|cachedModel
operator|.
name|isSameModel
argument_list|(
name|m
argument_list|)
condition|)
block|{
comment|// Reuse
name|sLog
operator|.
name|debug
argument_list|(
literal|"  using cached model..."
argument_list|)
expr_stmt|;
name|m
operator|=
name|cachedModel
expr_stmt|;
block|}
else|else
block|{
comment|// Solve model
name|m
operator|.
name|solve
argument_list|()
expr_stmt|;
comment|// Save into the cache
name|Document
name|doc
init|=
name|DocumentHelper
operator|.
name|createDocument
argument_list|()
decl_stmt|;
name|m
operator|.
name|saveAsXml
argument_list|(
name|doc
operator|.
name|addElement
argument_list|(
name|getCacheName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
comment|// sLog.debug("Model:\n" + doc.asXML());
name|clasf
operator|.
name|setStudents
argument_list|(
name|doc
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|update
argument_list|(
name|clasf
argument_list|)
expr_stmt|;
block|}
comment|// Save results
for|for
control|(
name|CurriculumCourse
name|course
range|:
name|clasf
operator|.
name|getCourses
argument_list|()
control|)
block|{
name|Set
argument_list|<
name|WeightedStudentId
argument_list|>
name|courseStudents
init|=
name|iDemands
operator|.
name|get
argument_list|(
name|course
operator|.
name|getCourse
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|courseStudents
operator|==
literal|null
condition|)
block|{
name|courseStudents
operator|=
operator|new
name|HashSet
argument_list|<
name|WeightedStudentId
argument_list|>
argument_list|()
expr_stmt|;
name|iDemands
operator|.
name|put
argument_list|(
name|course
operator|.
name|getCourse
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|courseStudents
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|CurStudent
name|s
range|:
name|m
operator|.
name|getCourse
argument_list|(
name|course
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|getStudents
argument_list|()
control|)
block|{
name|WeightedStudentId
name|student
init|=
operator|new
name|WeightedStudentId
argument_list|(
name|s
operator|.
name|getStudentId
argument_list|()
argument_list|)
decl_stmt|;
name|student
operator|.
name|setStats
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
argument_list|,
name|clasf
operator|.
name|getAcademicClassification
argument_list|()
operator|.
name|getCode
argument_list|()
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|student
operator|.
name|setCurriculum
argument_list|(
name|clasf
operator|.
name|getCurriculum
argument_list|()
operator|.
name|getAbbv
argument_list|()
argument_list|)
expr_stmt|;
name|courseStudents
operator|.
name|add
argument_list|(
name|student
argument_list|)
expr_stmt|;
name|Set
argument_list|<
name|WeightedCourseOffering
argument_list|>
name|courses
init|=
name|iStudentRequests
operator|.
name|get
argument_list|(
name|student
operator|.
name|getStudentId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|courses
operator|==
literal|null
condition|)
block|{
name|courses
operator|=
operator|new
name|HashSet
argument_list|<
name|WeightedCourseOffering
argument_list|>
argument_list|()
expr_stmt|;
name|iStudentRequests
operator|.
name|put
argument_list|(
name|student
operator|.
name|getStudentId
argument_list|()
argument_list|,
name|courses
argument_list|)
expr_stmt|;
block|}
name|courses
operator|.
name|add
argument_list|(
operator|new
name|WeightedCourseOffering
argument_list|(
name|course
operator|.
name|getCourse
argument_list|()
argument_list|,
name|student
operator|.
name|getWeight
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
specifier|protected
name|void
name|computeTargetShare
parameter_list|(
name|CurriculumClassification
name|clasf
parameter_list|,
name|CurModel
name|model
parameter_list|)
block|{
for|for
control|(
name|CurriculumCourse
name|c1
range|:
name|clasf
operator|.
name|getCourses
argument_list|()
control|)
block|{
name|int
name|x1
init|=
name|Math
operator|.
name|round
argument_list|(
name|clasf
operator|.
name|getNrStudents
argument_list|()
operator|*
name|c1
operator|.
name|getPercShare
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|CurriculumCourse
name|c2
range|:
name|clasf
operator|.
name|getCourses
argument_list|()
control|)
block|{
name|int
name|x2
init|=
name|Math
operator|.
name|round
argument_list|(
name|clasf
operator|.
name|getNrStudents
argument_list|()
operator|*
name|c2
operator|.
name|getPercShare
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|c1
operator|.
name|getUniqueId
argument_list|()
operator|>=
name|c2
operator|.
name|getUniqueId
argument_list|()
condition|)
continue|continue;
name|int
name|share
init|=
name|Math
operator|.
name|round
argument_list|(
name|c1
operator|.
name|getPercShare
argument_list|()
operator|*
name|c2
operator|.
name|getPercShare
argument_list|()
operator|*
name|clasf
operator|.
name|getNrStudents
argument_list|()
argument_list|)
decl_stmt|;
name|CurriculumCourseGroup
name|group
init|=
literal|null
decl_stmt|;
name|groups
label|:
for|for
control|(
name|CurriculumCourseGroup
name|g1
range|:
name|c1
operator|.
name|getGroups
argument_list|()
control|)
block|{
for|for
control|(
name|CurriculumCourseGroup
name|g2
range|:
name|c2
operator|.
name|getGroups
argument_list|()
control|)
block|{
if|if
condition|(
name|g1
operator|.
name|equals
argument_list|(
name|g2
argument_list|)
condition|)
block|{
name|group
operator|=
name|g1
expr_stmt|;
break|break
name|groups
break|;
block|}
block|}
block|}
if|if
condition|(
name|group
operator|!=
literal|null
condition|)
block|{
name|share
operator|=
operator|(
name|group
operator|.
name|getType
argument_list|()
operator|==
literal|0
condition|?
literal|0
else|:
name|Math
operator|.
name|min
argument_list|(
name|x1
argument_list|,
name|x2
argument_list|)
operator|)
expr_stmt|;
block|}
name|model
operator|.
name|setTargetShare
argument_list|(
name|c1
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|c2
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|share
argument_list|)
expr_stmt|;
block|}
block|}
block|}
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
block|{
if|if
condition|(
name|iDemands
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
name|iFallback
operator|.
name|getDemands
argument_list|(
name|course
argument_list|)
return|;
name|Set
argument_list|<
name|WeightedStudentId
argument_list|>
name|demands
init|=
name|iDemands
operator|.
name|get
argument_list|(
name|course
operator|.
name|getUniqueId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|iIncludeOtherStudents
condition|)
return|return
name|demands
return|;
if|if
condition|(
name|demands
operator|==
literal|null
condition|)
block|{
name|demands
operator|=
operator|new
name|HashSet
argument_list|<
name|WeightedStudentId
argument_list|>
argument_list|()
expr_stmt|;
name|iDemands
operator|.
name|put
argument_list|(
name|course
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|demands
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|iCheckedCourses
operator|.
name|add
argument_list|(
name|course
operator|.
name|getUniqueId
argument_list|()
argument_list|)
condition|)
block|{
name|int
name|was
init|=
name|demands
operator|.
name|size
argument_list|()
decl_stmt|;
name|Hashtable
argument_list|<
name|String
argument_list|,
name|Set
argument_list|<
name|String
argument_list|>
argument_list|>
name|curricula
init|=
name|iLoadedCurricula
operator|.
name|get
argument_list|(
name|course
operator|.
name|getUniqueId
argument_list|()
argument_list|)
decl_stmt|;
name|Set
argument_list|<
name|WeightedStudentId
argument_list|>
name|other
init|=
name|iFallback
operator|.
name|getDemands
argument_list|(
name|course
argument_list|)
decl_stmt|;
if|if
condition|(
name|curricula
operator|==
literal|null
operator|||
name|curricula
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|demands
operator|.
name|addAll
argument_list|(
name|other
argument_list|)
expr_stmt|;
block|}
else|else
block|{
for|for
control|(
name|WeightedStudentId
name|student
range|:
name|other
control|)
block|{
if|if
condition|(
name|student
operator|.
name|getArea
argument_list|()
operator|==
literal|null
condition|)
continue|continue;
comment|// ignore students w/o academic area
name|Set
argument_list|<
name|String
argument_list|>
name|majors
init|=
name|curricula
operator|.
name|get
argument_list|(
name|student
operator|.
name|getArea
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|majors
operator|!=
literal|null
operator|&&
name|majors
operator|.
name|contains
argument_list|(
literal|""
argument_list|)
condition|)
continue|continue;
comment|// all majors
if|if
condition|(
name|majors
operator|==
literal|null
operator|||
operator|(
name|student
operator|.
name|getMajor
argument_list|()
operator|!=
literal|null
operator|&&
operator|!
name|majors
operator|.
name|contains
argument_list|(
name|student
operator|.
name|getMajor
argument_list|()
argument_list|)
operator|)
condition|)
name|demands
operator|.
name|add
argument_list|(
name|student
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|demands
operator|.
name|size
argument_list|()
operator|>
name|was
condition|)
name|sLog
operator|.
name|info
argument_list|(
name|course
operator|.
name|getCourseName
argument_list|()
operator|+
literal|" has "
operator|+
operator|(
name|demands
operator|.
name|size
argument_list|()
operator|-
name|was
operator|)
operator|+
literal|" other students (besides of the "
operator|+
name|was
operator|+
literal|" curriculum students)."
argument_list|)
expr_stmt|;
block|}
return|return
name|demands
return|;
block|}
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
block|{
if|if
condition|(
name|studentId
operator|>=
literal|0
operator|||
name|iStudentRequests
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
name|iFallback
operator|.
name|getCourses
argument_list|(
name|studentId
argument_list|)
return|;
return|return
name|iStudentRequests
operator|.
name|get
argument_list|(
name|studentId
argument_list|)
return|;
block|}
block|}
end_class

end_unit

