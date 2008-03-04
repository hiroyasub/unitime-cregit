begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.0 (University Course Timetabling& Student Sectioning Application)  * Copyright (C) 2007, UniTime.org, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program; if not, write to the Free Software Foundation, Inc.,  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|form
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
name|Iterator
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
name|TreeSet
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Vector
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|http
operator|.
name|HttpServletRequest
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|Globals
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionErrors
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionForm
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionMapping
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionMessage
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|util
operator|.
name|MessageResources
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|commons
operator|.
name|web
operator|.
name|Web
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
name|Exam
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
name|Preference
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
name|dao
operator|.
name|CourseOfferingDAO
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
name|ComboBoxLookup
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
name|util
operator|.
name|DynamicList
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
name|DynamicListObjectFactory
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
name|IdValue
import|;
end_import

begin_class
specifier|public
class|class
name|ExamDistributionPrefsForm
extends|extends
name|ActionForm
block|{
specifier|private
name|String
name|op
decl_stmt|;
specifier|private
name|String
name|distPrefId
decl_stmt|;
specifier|private
name|String
name|distType
decl_stmt|;
specifier|private
name|String
name|prefLevel
decl_stmt|;
specifier|private
name|String
name|description
decl_stmt|;
specifier|private
name|List
name|subjectArea
decl_stmt|;
specifier|private
name|List
name|courseNbr
decl_stmt|;
specifier|private
name|List
name|exam
decl_stmt|;
specifier|private
name|int
name|iExamType
decl_stmt|;
specifier|private
name|boolean
name|iHasEveningExams
init|=
literal|false
decl_stmt|;
specifier|private
name|String
name|filterSubjectAreaId
decl_stmt|;
specifier|private
name|Collection
name|filterSubjectAreas
decl_stmt|;
specifier|private
name|String
name|filterCourseNbr
decl_stmt|;
specifier|private
name|boolean
name|canAdd
decl_stmt|;
specifier|protected
name|DynamicListObjectFactory
name|factory
init|=
operator|new
name|DynamicListObjectFactory
argument_list|()
block|{
specifier|public
name|Object
name|create
parameter_list|()
block|{
return|return
operator|new
name|Long
argument_list|(
operator|-
literal|1
argument_list|)
return|;
block|}
block|}
decl_stmt|;
specifier|public
name|ActionErrors
name|validate
parameter_list|(
name|ActionMapping
name|mapping
parameter_list|,
name|HttpServletRequest
name|request
parameter_list|)
block|{
name|ActionErrors
name|errors
init|=
operator|new
name|ActionErrors
argument_list|()
decl_stmt|;
comment|// Get Message Resources
name|MessageResources
name|rsc
init|=
operator|(
name|MessageResources
operator|)
name|super
operator|.
name|getServlet
argument_list|()
operator|.
name|getServletContext
argument_list|()
operator|.
name|getAttribute
argument_list|(
name|Globals
operator|.
name|MESSAGES_KEY
argument_list|)
decl_stmt|;
comment|// Distribution Type must be selected
if|if
condition|(
name|distType
operator|==
literal|null
operator|||
name|distType
operator|.
name|equals
argument_list|(
name|Preference
operator|.
name|BLANK_PREF_VALUE
argument_list|)
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"distType"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
literal|"Select a distribution type. "
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|// Distribution Pref Level must be selected
if|if
condition|(
name|prefLevel
operator|==
literal|null
operator|||
name|prefLevel
operator|.
name|equals
argument_list|(
name|Preference
operator|.
name|BLANK_PREF_VALUE
argument_list|)
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"prefLevel"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
literal|"Select a preference level. "
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|// Save/Update clicked
if|if
condition|(
name|op
operator|.
name|equals
argument_list|(
name|rsc
operator|.
name|getMessage
argument_list|(
literal|"button.addNew"
argument_list|)
argument_list|)
operator|||
name|op
operator|.
name|equals
argument_list|(
name|rsc
operator|.
name|getMessage
argument_list|(
literal|"button.update"
argument_list|)
argument_list|)
condition|)
block|{
block|}
return|return
name|errors
return|;
block|}
comment|/**       * Method reset      * @param mapping      * @param request      */
specifier|public
name|void
name|reset
parameter_list|(
name|ActionMapping
name|mapping
parameter_list|,
name|HttpServletRequest
name|request
parameter_list|)
block|{
name|op
operator|=
literal|""
expr_stmt|;
name|distPrefId
operator|=
literal|""
expr_stmt|;
name|distType
operator|=
name|Preference
operator|.
name|BLANK_PREF_VALUE
expr_stmt|;
name|prefLevel
operator|=
name|Preference
operator|.
name|BLANK_PREF_VALUE
expr_stmt|;
name|subjectArea
operator|=
name|DynamicList
operator|.
name|getInstance
argument_list|(
operator|new
name|ArrayList
argument_list|()
argument_list|,
name|factory
argument_list|)
expr_stmt|;
name|courseNbr
operator|=
name|DynamicList
operator|.
name|getInstance
argument_list|(
operator|new
name|ArrayList
argument_list|()
argument_list|,
name|factory
argument_list|)
expr_stmt|;
name|exam
operator|=
name|DynamicList
operator|.
name|getInstance
argument_list|(
operator|new
name|ArrayList
argument_list|()
argument_list|,
name|factory
argument_list|)
expr_stmt|;
name|filterSubjectAreaId
operator|=
operator|(
name|String
operator|)
name|request
operator|.
name|getSession
argument_list|()
operator|.
name|getAttribute
argument_list|(
name|Constants
operator|.
name|SUBJ_AREA_ID_ATTR_NAME
argument_list|)
expr_stmt|;
name|filterCourseNbr
operator|=
operator|(
name|String
operator|)
name|request
operator|.
name|getSession
argument_list|()
operator|.
name|getAttribute
argument_list|(
name|Constants
operator|.
name|CRS_NBR_ATTR_NAME
argument_list|)
expr_stmt|;
name|filterSubjectAreas
operator|=
operator|new
name|ArrayList
argument_list|()
expr_stmt|;
name|iExamType
operator|=
name|Exam
operator|.
name|sExamTypeFinal
expr_stmt|;
if|if
condition|(
name|request
operator|.
name|getSession
argument_list|()
operator|.
name|getAttribute
argument_list|(
literal|"Exam.Type"
argument_list|)
operator|!=
literal|null
condition|)
name|iExamType
operator|=
operator|(
name|Integer
operator|)
name|request
operator|.
name|getSession
argument_list|()
operator|.
name|getAttribute
argument_list|(
literal|"Exam.Type"
argument_list|)
expr_stmt|;
name|canAdd
operator|=
literal|false
expr_stmt|;
try|try
block|{
name|iHasEveningExams
operator|=
name|Exam
operator|.
name|hasEveningExams
argument_list|(
name|Session
operator|.
name|getCurrentAcadSession
argument_list|(
name|Web
operator|.
name|getUser
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|)
argument_list|)
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
block|}
block|}
specifier|public
name|String
name|getDistPrefId
parameter_list|()
block|{
return|return
name|distPrefId
return|;
block|}
specifier|public
name|void
name|setDistPrefId
parameter_list|(
name|String
name|distPrefId
parameter_list|)
block|{
name|this
operator|.
name|distPrefId
operator|=
name|distPrefId
expr_stmt|;
block|}
specifier|public
name|String
name|getOp
parameter_list|()
block|{
return|return
name|op
return|;
block|}
specifier|public
name|void
name|setOp
parameter_list|(
name|String
name|op
parameter_list|)
block|{
name|this
operator|.
name|op
operator|=
name|op
expr_stmt|;
block|}
specifier|public
name|String
name|getPrefLevel
parameter_list|()
block|{
return|return
name|prefLevel
return|;
block|}
specifier|public
name|void
name|setPrefLevel
parameter_list|(
name|String
name|prefLevel
parameter_list|)
block|{
name|this
operator|.
name|prefLevel
operator|=
name|prefLevel
expr_stmt|;
block|}
specifier|public
name|String
name|getDistType
parameter_list|()
block|{
return|return
name|distType
return|;
block|}
specifier|public
name|void
name|setDistType
parameter_list|(
name|String
name|distType
parameter_list|)
block|{
name|this
operator|.
name|distType
operator|=
name|distType
expr_stmt|;
block|}
specifier|public
name|String
name|getDescription
parameter_list|()
block|{
return|return
name|description
return|;
block|}
specifier|public
name|void
name|setDescription
parameter_list|(
name|String
name|description
parameter_list|)
block|{
name|this
operator|.
name|description
operator|=
name|description
expr_stmt|;
block|}
specifier|public
name|List
name|getSubjectAreaList
parameter_list|()
block|{
return|return
name|subjectArea
return|;
block|}
specifier|public
name|List
name|getSubjectArea
parameter_list|()
block|{
return|return
name|subjectArea
return|;
block|}
specifier|public
name|Long
name|getSubjectArea
parameter_list|(
name|int
name|key
parameter_list|)
block|{
return|return
operator|(
name|Long
operator|)
name|subjectArea
operator|.
name|get
argument_list|(
name|key
argument_list|)
return|;
block|}
specifier|public
name|void
name|setSubjectArea
parameter_list|(
name|int
name|key
parameter_list|,
name|Long
name|value
parameter_list|)
block|{
name|this
operator|.
name|subjectArea
operator|.
name|set
argument_list|(
name|key
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|setSubjectArea
parameter_list|(
name|List
name|subjectArea
parameter_list|)
block|{
name|this
operator|.
name|subjectArea
operator|=
name|subjectArea
expr_stmt|;
block|}
specifier|public
name|List
name|getCourseNbr
parameter_list|()
block|{
return|return
name|courseNbr
return|;
block|}
specifier|public
name|Long
name|getCourseNbr
parameter_list|(
name|int
name|key
parameter_list|)
block|{
return|return
operator|(
name|Long
operator|)
name|courseNbr
operator|.
name|get
argument_list|(
name|key
argument_list|)
return|;
block|}
specifier|public
name|void
name|setCourseNbr
parameter_list|(
name|int
name|key
parameter_list|,
name|Long
name|value
parameter_list|)
block|{
name|this
operator|.
name|courseNbr
operator|.
name|set
argument_list|(
name|key
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|setCourseNbr
parameter_list|(
name|List
name|courseNbr
parameter_list|)
block|{
name|this
operator|.
name|courseNbr
operator|=
name|courseNbr
expr_stmt|;
block|}
specifier|public
name|List
name|getExam
parameter_list|()
block|{
return|return
name|exam
return|;
block|}
specifier|public
name|Long
name|getExam
parameter_list|(
name|int
name|key
parameter_list|)
block|{
return|return
operator|(
name|Long
operator|)
name|exam
operator|.
name|get
argument_list|(
name|key
argument_list|)
return|;
block|}
specifier|public
name|void
name|setExam
parameter_list|(
name|int
name|key
parameter_list|,
name|Long
name|value
parameter_list|)
block|{
name|this
operator|.
name|exam
operator|.
name|set
argument_list|(
name|key
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|setExam
parameter_list|(
name|List
name|itype
parameter_list|)
block|{
name|this
operator|.
name|exam
operator|=
name|itype
expr_stmt|;
block|}
specifier|public
name|boolean
name|getCanAdd
parameter_list|()
block|{
return|return
name|canAdd
return|;
block|}
specifier|public
name|void
name|setCanAdd
parameter_list|(
name|boolean
name|canAdd
parameter_list|)
block|{
name|this
operator|.
name|canAdd
operator|=
name|canAdd
expr_stmt|;
block|}
specifier|public
name|void
name|deleteExam
parameter_list|(
name|int
name|key
parameter_list|)
block|{
name|subjectArea
operator|.
name|remove
argument_list|(
name|key
argument_list|)
expr_stmt|;
name|courseNbr
operator|.
name|remove
argument_list|(
name|key
argument_list|)
expr_stmt|;
name|exam
operator|.
name|remove
argument_list|(
name|key
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|swapExams
parameter_list|(
name|int
name|i1
parameter_list|,
name|int
name|i2
parameter_list|)
block|{
name|Object
name|objSa1
init|=
name|subjectArea
operator|.
name|get
argument_list|(
name|i1
argument_list|)
decl_stmt|;
name|Object
name|objCo1
init|=
name|courseNbr
operator|.
name|get
argument_list|(
name|i1
argument_list|)
decl_stmt|;
name|Object
name|objEx1
init|=
name|exam
operator|.
name|get
argument_list|(
name|i1
argument_list|)
decl_stmt|;
name|Object
name|objSa2
init|=
name|subjectArea
operator|.
name|get
argument_list|(
name|i2
argument_list|)
decl_stmt|;
name|Object
name|objCo2
init|=
name|courseNbr
operator|.
name|get
argument_list|(
name|i2
argument_list|)
decl_stmt|;
name|Object
name|objEx2
init|=
name|exam
operator|.
name|get
argument_list|(
name|i2
argument_list|)
decl_stmt|;
name|subjectArea
operator|.
name|set
argument_list|(
name|i1
argument_list|,
name|objSa2
argument_list|)
expr_stmt|;
name|subjectArea
operator|.
name|set
argument_list|(
name|i2
argument_list|,
name|objSa1
argument_list|)
expr_stmt|;
name|courseNbr
operator|.
name|set
argument_list|(
name|i1
argument_list|,
name|objCo2
argument_list|)
expr_stmt|;
name|courseNbr
operator|.
name|set
argument_list|(
name|i2
argument_list|,
name|objCo1
argument_list|)
expr_stmt|;
name|exam
operator|.
name|set
argument_list|(
name|i1
argument_list|,
name|objEx2
argument_list|)
expr_stmt|;
name|exam
operator|.
name|set
argument_list|(
name|i2
argument_list|,
name|objEx1
argument_list|)
expr_stmt|;
block|}
specifier|public
name|String
name|getFilterSubjectAreaId
parameter_list|()
block|{
return|return
name|filterSubjectAreaId
return|;
block|}
specifier|public
name|void
name|setFilterSubjectAreaId
parameter_list|(
name|String
name|filterSubjectAreaId
parameter_list|)
block|{
name|this
operator|.
name|filterSubjectAreaId
operator|=
name|filterSubjectAreaId
expr_stmt|;
block|}
specifier|public
name|String
name|getFilterCourseNbr
parameter_list|()
block|{
return|return
name|filterCourseNbr
return|;
block|}
specifier|public
name|void
name|setFilterCourseNbr
parameter_list|(
name|String
name|filterCourseNbr
parameter_list|)
block|{
name|this
operator|.
name|filterCourseNbr
operator|=
name|filterCourseNbr
expr_stmt|;
block|}
specifier|public
name|Collection
name|getFilterSubjectAreas
parameter_list|()
block|{
return|return
name|filterSubjectAreas
return|;
block|}
specifier|public
name|void
name|setFilterSubjectAreas
parameter_list|(
name|Collection
name|filterSubjectAreas
parameter_list|)
block|{
name|this
operator|.
name|filterSubjectAreas
operator|=
name|filterSubjectAreas
expr_stmt|;
block|}
specifier|public
name|Collection
name|getCourseNbrs
parameter_list|(
name|int
name|idx
parameter_list|)
block|{
name|Vector
name|ret
init|=
operator|new
name|Vector
argument_list|()
decl_stmt|;
name|boolean
name|contains
init|=
literal|false
decl_stmt|;
if|if
condition|(
name|getSubjectArea
argument_list|(
name|idx
argument_list|)
operator|>=
literal|0
condition|)
block|{
for|for
control|(
name|Iterator
name|i
init|=
operator|new
name|CourseOfferingDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select co.uniqueId, co.courseNbr from CourseOffering co "
operator|+
literal|"where co.uniqueCourseNbr.subjectArea.uniqueId = :subjectAreaId "
operator|+
literal|"and co.instructionalOffering.notOffered = false "
operator|+
literal|"order by co.courseNbr "
argument_list|)
operator|.
name|setFetchSize
argument_list|(
literal|200
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"subjectAreaId"
argument_list|,
name|getSubjectArea
argument_list|(
name|idx
argument_list|)
argument_list|)
operator|.
name|iterate
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Object
index|[]
name|o
init|=
operator|(
name|Object
index|[]
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|ret
operator|.
name|add
argument_list|(
operator|new
name|IdValue
argument_list|(
operator|(
name|Long
operator|)
name|o
index|[
literal|0
index|]
argument_list|,
operator|(
name|String
operator|)
name|o
index|[
literal|1
index|]
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|o
index|[
literal|0
index|]
operator|.
name|equals
argument_list|(
name|getCourseNbr
argument_list|(
name|idx
argument_list|)
argument_list|)
condition|)
name|contains
operator|=
literal|true
expr_stmt|;
block|}
block|}
if|if
condition|(
operator|!
name|contains
condition|)
name|setCourseNbr
argument_list|(
name|idx
argument_list|,
operator|-
literal|1L
argument_list|)
expr_stmt|;
if|if
condition|(
name|ret
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
name|setCourseNbr
argument_list|(
name|idx
argument_list|,
operator|(
operator|(
name|IdValue
operator|)
name|ret
operator|.
name|firstElement
argument_list|()
operator|)
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
else|else
name|ret
operator|.
name|insertElementAt
argument_list|(
operator|new
name|IdValue
argument_list|(
operator|-
literal|1L
argument_list|,
literal|"-"
argument_list|)
argument_list|,
literal|0
argument_list|)
expr_stmt|;
return|return
name|ret
return|;
block|}
specifier|public
name|Collection
name|getExams
parameter_list|(
name|int
name|idx
parameter_list|)
block|{
name|Vector
name|ret
init|=
operator|new
name|Vector
argument_list|()
decl_stmt|;
name|boolean
name|contains
init|=
literal|false
decl_stmt|;
if|if
condition|(
name|getCourseNbr
argument_list|(
name|idx
argument_list|)
operator|>=
literal|0
condition|)
block|{
name|TreeSet
name|exams
init|=
operator|new
name|TreeSet
argument_list|(
name|Exam
operator|.
name|findExamsOfCourseOffering
argument_list|(
name|getCourseNbr
argument_list|(
name|idx
argument_list|)
argument_list|,
name|getExamType
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|exams
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Exam
name|exam
init|=
operator|(
name|Exam
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|ret
operator|.
name|add
argument_list|(
operator|new
name|IdValue
argument_list|(
name|exam
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|exam
operator|.
name|getLabel
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|exam
operator|.
name|getUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
name|getExam
argument_list|(
name|idx
argument_list|)
argument_list|)
condition|)
name|contains
operator|=
literal|true
expr_stmt|;
block|}
block|}
if|if
condition|(
operator|!
name|contains
condition|)
name|setExam
argument_list|(
name|idx
argument_list|,
operator|-
literal|1L
argument_list|)
expr_stmt|;
if|if
condition|(
name|ret
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
name|setCourseNbr
argument_list|(
name|idx
argument_list|,
operator|(
operator|(
name|IdValue
operator|)
name|ret
operator|.
name|firstElement
argument_list|()
operator|)
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
else|else
name|ret
operator|.
name|insertElementAt
argument_list|(
operator|new
name|IdValue
argument_list|(
operator|-
literal|1L
argument_list|,
literal|"-"
argument_list|)
argument_list|,
literal|0
argument_list|)
expr_stmt|;
return|return
name|ret
return|;
block|}
specifier|public
name|int
name|getExamType
parameter_list|()
block|{
return|return
name|iExamType
return|;
block|}
specifier|public
name|void
name|setExamType
parameter_list|(
name|int
name|type
parameter_list|)
block|{
name|iExamType
operator|=
name|type
expr_stmt|;
block|}
specifier|public
name|Collection
name|getExamTypes
parameter_list|()
block|{
name|Vector
name|ret
init|=
operator|new
name|Vector
argument_list|(
name|Exam
operator|.
name|sExamTypes
operator|.
name|length
argument_list|)
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
name|Exam
operator|.
name|sExamTypes
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|i
operator|==
name|Exam
operator|.
name|sExamTypeEvening
operator|&&
operator|!
name|iHasEveningExams
condition|)
continue|continue;
name|ret
operator|.
name|add
argument_list|(
operator|new
name|ComboBoxLookup
argument_list|(
name|Exam
operator|.
name|sExamTypes
index|[
name|i
index|]
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|i
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|ret
return|;
block|}
block|}
end_class

end_unit

