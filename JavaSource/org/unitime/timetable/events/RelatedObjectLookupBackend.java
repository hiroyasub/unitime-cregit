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
name|events
package|;
end_package

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
name|shared
operator|.
name|EventInterface
operator|.
name|RelatedObjectInterface
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
name|EventInterface
operator|.
name|RelatedObjectLookupRpcRequest
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
name|EventInterface
operator|.
name|RelatedObjectLookupRpcResponse
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
name|Class_
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
name|InstrOfferingConfig
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
name|SchedulingSubpart
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
name|comparators
operator|.
name|ClassComparator
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
name|comparators
operator|.
name|InstrOfferingConfigComparator
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
name|comparators
operator|.
name|SchedulingSubpartComparator
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
name|model
operator|.
name|dao
operator|.
name|SchedulingSubpartDAO
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
name|SessionDAO
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

begin_class
annotation|@
name|GwtRpcImplements
argument_list|(
name|RelatedObjectLookupRpcRequest
operator|.
name|class
argument_list|)
specifier|public
class|class
name|RelatedObjectLookupBackend
extends|extends
name|EventAction
argument_list|<
name|RelatedObjectLookupRpcRequest
argument_list|,
name|GwtRpcResponseList
argument_list|<
name|RelatedObjectLookupRpcResponse
argument_list|>
argument_list|>
block|{
annotation|@
name|Override
specifier|public
name|GwtRpcResponseList
argument_list|<
name|RelatedObjectLookupRpcResponse
argument_list|>
name|execute
parameter_list|(
name|RelatedObjectLookupRpcRequest
name|request
parameter_list|,
name|EventContext
name|context
parameter_list|)
block|{
name|context
operator|.
name|checkPermission
argument_list|(
name|Right
operator|.
name|EventAddCourseRelated
argument_list|)
expr_stmt|;
name|GwtRpcResponseList
argument_list|<
name|RelatedObjectLookupRpcResponse
argument_list|>
name|response
init|=
operator|new
name|GwtRpcResponseList
argument_list|<
name|RelatedObjectLookupRpcResponse
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
name|SessionDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
decl_stmt|;
switch|switch
condition|(
name|request
operator|.
name|getLevel
argument_list|()
condition|)
block|{
case|case
name|SESSION
case|:
for|for
control|(
name|Object
index|[]
name|object
range|:
operator|(
name|List
argument_list|<
name|Object
index|[]
argument_list|>
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"select s.uniqueId, s.subjectAreaAbbreviation from SubjectArea s "
operator|+
literal|"where s.session.uniqueId = :sessionId "
operator|+
literal|"order by s.subjectAreaAbbreviation"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|request
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
name|response
operator|.
name|add
argument_list|(
operator|new
name|RelatedObjectLookupRpcResponse
argument_list|(
name|RelatedObjectLookupRpcRequest
operator|.
name|Level
operator|.
name|SUBJECT
argument_list|,
operator|(
name|Long
operator|)
name|object
index|[
literal|0
index|]
argument_list|,
operator|(
name|String
operator|)
name|object
index|[
literal|1
index|]
argument_list|)
argument_list|)
expr_stmt|;
block|}
break|break;
case|case
name|SUBJECT
case|:
for|for
control|(
name|CourseOffering
name|course
range|:
operator|(
name|List
argument_list|<
name|CourseOffering
argument_list|>
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"select co from CourseOffering co "
operator|+
literal|"where co.subjectArea.uniqueId = :subjectAreaId "
operator|+
literal|"and co.instructionalOffering.notOffered = false "
operator|+
literal|"order by co.courseNbr"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"subjectAreaId"
argument_list|,
name|request
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
name|RelatedObjectInterface
name|related
init|=
operator|new
name|RelatedObjectInterface
argument_list|()
decl_stmt|;
if|if
condition|(
name|course
operator|.
name|getIsControl
argument_list|()
condition|)
block|{
name|related
operator|.
name|setType
argument_list|(
name|RelatedObjectInterface
operator|.
name|RelatedObjectType
operator|.
name|Offering
argument_list|)
expr_stmt|;
name|related
operator|.
name|setUniqueId
argument_list|(
name|course
operator|.
name|getInstructionalOffering
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|related
operator|.
name|setName
argument_list|(
name|course
operator|.
name|getCourseName
argument_list|()
argument_list|)
expr_stmt|;
name|related
operator|.
name|addCourseName
argument_list|(
name|course
operator|.
name|getCourseName
argument_list|()
argument_list|)
expr_stmt|;
name|related
operator|.
name|addCourseTitle
argument_list|(
name|course
operator|.
name|getTitle
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|course
operator|.
name|getTitle
argument_list|()
argument_list|)
expr_stmt|;
name|related
operator|.
name|setSelection
argument_list|(
operator|new
name|long
index|[]
block|{
name|course
operator|.
name|getSubjectArea
argument_list|()
operator|.
name|getUniqueId
argument_list|()
block|,
name|course
operator|.
name|getUniqueId
argument_list|()
block|}
argument_list|)
expr_stmt|;
name|related
operator|.
name|setNote
argument_list|(
name|course
operator|.
name|getScheduleBookNote
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|related
operator|.
name|setType
argument_list|(
name|RelatedObjectInterface
operator|.
name|RelatedObjectType
operator|.
name|Course
argument_list|)
expr_stmt|;
name|related
operator|.
name|setUniqueId
argument_list|(
name|course
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|related
operator|.
name|setName
argument_list|(
name|course
operator|.
name|getCourseName
argument_list|()
argument_list|)
expr_stmt|;
name|related
operator|.
name|addCourseName
argument_list|(
name|course
operator|.
name|getCourseName
argument_list|()
argument_list|)
expr_stmt|;
name|related
operator|.
name|addCourseTitle
argument_list|(
name|course
operator|.
name|getTitle
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|course
operator|.
name|getTitle
argument_list|()
argument_list|)
expr_stmt|;
name|related
operator|.
name|setSelection
argument_list|(
operator|new
name|long
index|[]
block|{
name|course
operator|.
name|getSubjectArea
argument_list|()
operator|.
name|getUniqueId
argument_list|()
block|,
name|course
operator|.
name|getUniqueId
argument_list|()
block|}
argument_list|)
expr_stmt|;
name|related
operator|.
name|setNote
argument_list|(
name|course
operator|.
name|getScheduleBookNote
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|response
operator|.
name|add
argument_list|(
operator|new
name|RelatedObjectLookupRpcResponse
argument_list|(
name|RelatedObjectLookupRpcRequest
operator|.
name|Level
operator|.
name|COURSE
argument_list|,
name|course
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|course
operator|.
name|getCourseNbr
argument_list|()
argument_list|,
name|course
operator|.
name|getTitle
argument_list|()
argument_list|,
name|related
argument_list|)
argument_list|)
expr_stmt|;
block|}
break|break;
case|case
name|COURSE
case|:
name|CourseOffering
name|course
init|=
name|CourseOfferingDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|request
operator|.
name|getUniqueId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|course
operator|==
literal|null
condition|)
break|break;
if|if
condition|(
name|course
operator|.
name|isIsControl
argument_list|()
condition|)
block|{
name|RelatedObjectInterface
name|relatedOffering
init|=
operator|new
name|RelatedObjectInterface
argument_list|()
decl_stmt|;
name|relatedOffering
operator|.
name|setType
argument_list|(
name|RelatedObjectInterface
operator|.
name|RelatedObjectType
operator|.
name|Offering
argument_list|)
expr_stmt|;
name|relatedOffering
operator|.
name|setUniqueId
argument_list|(
name|course
operator|.
name|getInstructionalOffering
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|relatedOffering
operator|.
name|setName
argument_list|(
name|course
operator|.
name|getCourseName
argument_list|()
argument_list|)
expr_stmt|;
name|relatedOffering
operator|.
name|setNote
argument_list|(
name|course
operator|.
name|getScheduleBookNote
argument_list|()
argument_list|)
expr_stmt|;
name|relatedOffering
operator|.
name|addCourseName
argument_list|(
name|course
operator|.
name|getCourseName
argument_list|()
argument_list|)
expr_stmt|;
name|relatedOffering
operator|.
name|addCourseTitle
argument_list|(
name|course
operator|.
name|getTitle
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|course
operator|.
name|getTitle
argument_list|()
argument_list|)
expr_stmt|;
name|relatedOffering
operator|.
name|setSelection
argument_list|(
operator|new
name|long
index|[]
block|{
name|course
operator|.
name|getSubjectArea
argument_list|()
operator|.
name|getUniqueId
argument_list|()
block|,
name|course
operator|.
name|getUniqueId
argument_list|()
block|}
argument_list|)
expr_stmt|;
name|response
operator|.
name|add
argument_list|(
operator|new
name|RelatedObjectLookupRpcResponse
argument_list|(
name|RelatedObjectLookupRpcRequest
operator|.
name|Level
operator|.
name|OFFERING
argument_list|,
name|course
operator|.
name|getInstructionalOffering
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|,
literal|"Offering"
argument_list|,
name|relatedOffering
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|RelatedObjectInterface
name|relatedCourse
init|=
operator|new
name|RelatedObjectInterface
argument_list|()
decl_stmt|;
name|relatedCourse
operator|.
name|setType
argument_list|(
name|RelatedObjectInterface
operator|.
name|RelatedObjectType
operator|.
name|Course
argument_list|)
expr_stmt|;
name|relatedCourse
operator|.
name|setUniqueId
argument_list|(
name|course
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|relatedCourse
operator|.
name|setName
argument_list|(
name|course
operator|.
name|getCourseName
argument_list|()
argument_list|)
expr_stmt|;
name|relatedCourse
operator|.
name|setNote
argument_list|(
name|course
operator|.
name|getScheduleBookNote
argument_list|()
argument_list|)
expr_stmt|;
name|relatedCourse
operator|.
name|addCourseName
argument_list|(
name|course
operator|.
name|getCourseName
argument_list|()
argument_list|)
expr_stmt|;
name|relatedCourse
operator|.
name|addCourseTitle
argument_list|(
name|course
operator|.
name|getTitle
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|course
operator|.
name|getTitle
argument_list|()
argument_list|)
expr_stmt|;
name|relatedCourse
operator|.
name|setSelection
argument_list|(
operator|new
name|long
index|[]
block|{
name|course
operator|.
name|getSubjectArea
argument_list|()
operator|.
name|getUniqueId
argument_list|()
block|,
name|course
operator|.
name|getUniqueId
argument_list|()
block|}
argument_list|)
expr_stmt|;
name|response
operator|.
name|add
argument_list|(
operator|new
name|RelatedObjectLookupRpcResponse
argument_list|(
name|RelatedObjectLookupRpcRequest
operator|.
name|Level
operator|.
name|COURSE
argument_list|,
name|course
operator|.
name|getUniqueId
argument_list|()
argument_list|,
literal|"Course"
argument_list|,
name|relatedCourse
argument_list|)
argument_list|)
expr_stmt|;
name|Set
argument_list|<
name|InstrOfferingConfig
argument_list|>
name|configs
init|=
operator|new
name|TreeSet
argument_list|<
name|InstrOfferingConfig
argument_list|>
argument_list|(
operator|new
name|InstrOfferingConfigComparator
argument_list|(
literal|null
argument_list|)
argument_list|)
decl_stmt|;
name|configs
operator|.
name|addAll
argument_list|(
name|course
operator|.
name|getInstructionalOffering
argument_list|()
operator|.
name|getInstrOfferingConfigs
argument_list|()
argument_list|)
expr_stmt|;
name|Set
argument_list|<
name|SchedulingSubpart
argument_list|>
name|subparts
init|=
operator|new
name|TreeSet
argument_list|<
name|SchedulingSubpart
argument_list|>
argument_list|(
operator|new
name|SchedulingSubpartComparator
argument_list|(
literal|null
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|configs
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|response
operator|.
name|add
argument_list|(
operator|new
name|RelatedObjectLookupRpcResponse
argument_list|(
name|RelatedObjectLookupRpcRequest
operator|.
name|Level
operator|.
name|NONE
argument_list|,
literal|null
argument_list|,
literal|"-- Configurations --"
argument_list|)
argument_list|)
expr_stmt|;
for|for
control|(
name|InstrOfferingConfig
name|config
range|:
name|configs
control|)
block|{
name|RelatedObjectInterface
name|relatedConfig
init|=
operator|new
name|RelatedObjectInterface
argument_list|()
decl_stmt|;
name|relatedConfig
operator|.
name|setType
argument_list|(
name|RelatedObjectInterface
operator|.
name|RelatedObjectType
operator|.
name|Config
argument_list|)
expr_stmt|;
name|relatedConfig
operator|.
name|setUniqueId
argument_list|(
name|config
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|relatedConfig
operator|.
name|setName
argument_list|(
name|config
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|relatedConfig
operator|.
name|addCourseName
argument_list|(
name|course
operator|.
name|getCourseName
argument_list|()
argument_list|)
expr_stmt|;
name|relatedConfig
operator|.
name|addCourseTitle
argument_list|(
name|course
operator|.
name|getTitle
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|course
operator|.
name|getTitle
argument_list|()
argument_list|)
expr_stmt|;
name|relatedConfig
operator|.
name|setSelection
argument_list|(
operator|new
name|long
index|[]
block|{
name|course
operator|.
name|getSubjectArea
argument_list|()
operator|.
name|getUniqueId
argument_list|()
block|,
name|course
operator|.
name|getUniqueId
argument_list|()
block|,
name|config
operator|.
name|getUniqueId
argument_list|()
block|}
argument_list|)
expr_stmt|;
name|response
operator|.
name|add
argument_list|(
operator|new
name|RelatedObjectLookupRpcResponse
argument_list|(
name|RelatedObjectLookupRpcRequest
operator|.
name|Level
operator|.
name|CONFIG
argument_list|,
name|config
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|config
operator|.
name|getName
argument_list|()
argument_list|,
name|relatedConfig
argument_list|)
argument_list|)
expr_stmt|;
name|subparts
operator|.
name|addAll
argument_list|(
name|config
operator|.
name|getSchedulingSubparts
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
operator|!
name|subparts
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|response
operator|.
name|add
argument_list|(
operator|new
name|RelatedObjectLookupRpcResponse
argument_list|(
name|RelatedObjectLookupRpcRequest
operator|.
name|Level
operator|.
name|NONE
argument_list|,
literal|null
argument_list|,
literal|"-- Subparts --"
argument_list|)
argument_list|)
expr_stmt|;
for|for
control|(
name|SchedulingSubpart
name|subpart
range|:
name|subparts
control|)
block|{
name|String
name|name
init|=
name|subpart
operator|.
name|getItype
argument_list|()
operator|.
name|getAbbv
argument_list|()
decl_stmt|;
name|String
name|sufix
init|=
name|subpart
operator|.
name|getSchedulingSubpartSuffix
argument_list|()
decl_stmt|;
while|while
condition|(
name|subpart
operator|.
name|getParentSubpart
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|name
operator|=
literal|"\u00A0\u00A0\u00A0\u00A0"
operator|+
name|name
expr_stmt|;
name|subpart
operator|=
name|subpart
operator|.
name|getParentSubpart
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|subpart
operator|.
name|getInstrOfferingConfig
argument_list|()
operator|.
name|getInstructionalOffering
argument_list|()
operator|.
name|getInstrOfferingConfigs
argument_list|()
operator|.
name|size
argument_list|()
operator|>
literal|1
condition|)
name|name
operator|+=
literal|" ["
operator|+
name|subpart
operator|.
name|getInstrOfferingConfig
argument_list|()
operator|.
name|getName
argument_list|()
operator|+
literal|"]"
expr_stmt|;
name|response
operator|.
name|add
argument_list|(
operator|new
name|RelatedObjectLookupRpcResponse
argument_list|(
name|RelatedObjectLookupRpcRequest
operator|.
name|Level
operator|.
name|SUBPART
argument_list|,
name|subpart
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|name
operator|+
operator|(
name|sufix
operator|==
literal|null
operator|||
name|sufix
operator|.
name|isEmpty
argument_list|()
condition|?
literal|""
else|:
literal|" ("
operator|+
name|sufix
operator|+
literal|")"
operator|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
break|break;
case|case
name|SUBPART
case|:
name|course
operator|=
name|CourseOfferingDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|request
operator|.
name|getCourseId
argument_list|()
argument_list|)
expr_stmt|;
name|SchedulingSubpart
name|subpart
init|=
name|SchedulingSubpartDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|request
operator|.
name|getUniqueId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|subpart
operator|==
literal|null
condition|)
break|break;
name|Set
argument_list|<
name|Class_
argument_list|>
name|classes
init|=
operator|new
name|TreeSet
argument_list|<
name|Class_
argument_list|>
argument_list|(
operator|new
name|ClassComparator
argument_list|(
name|ClassComparator
operator|.
name|COMPARE_BY_HIERARCHY
argument_list|)
argument_list|)
decl_stmt|;
name|classes
operator|.
name|addAll
argument_list|(
name|subpart
operator|.
name|getClasses
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|Class_
name|clazz
range|:
name|classes
control|)
block|{
name|String
name|extId
init|=
name|clazz
operator|.
name|getClassSuffix
argument_list|(
name|course
argument_list|)
decl_stmt|;
name|RelatedObjectInterface
name|relatedClass
init|=
operator|new
name|RelatedObjectInterface
argument_list|()
decl_stmt|;
name|relatedClass
operator|.
name|setType
argument_list|(
name|RelatedObjectInterface
operator|.
name|RelatedObjectType
operator|.
name|Class
argument_list|)
expr_stmt|;
name|relatedClass
operator|.
name|setUniqueId
argument_list|(
name|clazz
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|relatedClass
operator|.
name|setName
argument_list|(
name|clazz
operator|.
name|getClassLabel
argument_list|(
name|course
argument_list|)
argument_list|)
expr_stmt|;
name|String
name|note
init|=
name|course
operator|.
name|getScheduleBookNote
argument_list|()
decl_stmt|;
if|if
condition|(
name|clazz
operator|.
name|getSchedulePrintNote
argument_list|()
operator|!=
literal|null
operator|&&
operator|!
name|clazz
operator|.
name|getSchedulePrintNote
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
name|note
operator|=
operator|(
name|note
operator|==
literal|null
operator|||
name|note
operator|.
name|isEmpty
argument_list|()
condition|?
literal|""
else|:
name|note
operator|+
literal|"\n"
operator|)
operator|+
name|clazz
operator|.
name|getSchedulePrintNote
argument_list|()
expr_stmt|;
name|relatedClass
operator|.
name|setNote
argument_list|(
name|note
argument_list|)
expr_stmt|;
name|relatedClass
operator|.
name|addCourseName
argument_list|(
name|course
operator|.
name|getCourseName
argument_list|()
argument_list|)
expr_stmt|;
name|relatedClass
operator|.
name|addCourseTitle
argument_list|(
name|course
operator|.
name|getTitle
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|course
operator|.
name|getTitle
argument_list|()
argument_list|)
expr_stmt|;
name|relatedClass
operator|.
name|setSelection
argument_list|(
operator|new
name|long
index|[]
block|{
name|course
operator|.
name|getSubjectArea
argument_list|()
operator|.
name|getUniqueId
argument_list|()
block|,
name|course
operator|.
name|getUniqueId
argument_list|()
block|,
name|subpart
operator|.
name|getUniqueId
argument_list|()
block|,
name|clazz
operator|.
name|getUniqueId
argument_list|()
block|}
argument_list|)
expr_stmt|;
name|response
operator|.
name|add
argument_list|(
operator|new
name|RelatedObjectLookupRpcResponse
argument_list|(
name|RelatedObjectLookupRpcRequest
operator|.
name|Level
operator|.
name|CLASS
argument_list|,
name|clazz
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|clazz
operator|.
name|getSectionNumberString
argument_list|(
name|hibSession
argument_list|)
argument_list|,
operator|(
name|extId
operator|==
literal|null
operator|||
name|extId
operator|.
name|isEmpty
argument_list|()
operator|||
name|extId
operator|.
name|equalsIgnoreCase
argument_list|(
name|clazz
operator|.
name|getSectionNumberString
argument_list|(
name|hibSession
argument_list|)
argument_list|)
condition|?
literal|null
else|:
name|extId
operator|)
argument_list|,
name|relatedClass
argument_list|)
argument_list|)
expr_stmt|;
block|}
break|break;
default|default:
name|response
operator|.
name|add
argument_list|(
operator|new
name|RelatedObjectLookupRpcResponse
argument_list|(
name|RelatedObjectLookupRpcRequest
operator|.
name|Level
operator|.
name|NONE
argument_list|,
literal|null
argument_list|,
literal|"N/A"
argument_list|)
argument_list|)
expr_stmt|;
break|break;
block|}
return|return
name|response
return|;
block|}
block|}
end_class

end_unit

