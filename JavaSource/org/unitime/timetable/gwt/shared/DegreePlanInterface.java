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
name|gwt
operator|.
name|shared
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|Serializable
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
name|Date
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
name|IsSerializable
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|DegreePlanInterface
implements|implements
name|IsSerializable
implements|,
name|Serializable
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
specifier|private
name|Long
name|iStudentId
decl_stmt|,
name|iSessionId
decl_stmt|;
specifier|private
name|String
name|iId
decl_stmt|,
name|iName
decl_stmt|,
name|iDegree
decl_stmt|,
name|iSchool
decl_stmt|,
name|iTrack
decl_stmt|;
specifier|private
name|Date
name|iModified
decl_stmt|;
specifier|private
name|DegreeGroupInterface
name|iGroup
decl_stmt|;
specifier|public
name|DegreePlanInterface
parameter_list|()
block|{
block|}
specifier|public
name|Long
name|getStudentId
parameter_list|()
block|{
return|return
name|iStudentId
return|;
block|}
specifier|public
name|void
name|setStudentId
parameter_list|(
name|Long
name|studentId
parameter_list|)
block|{
name|iStudentId
operator|=
name|studentId
expr_stmt|;
block|}
specifier|public
name|Long
name|getSessionId
parameter_list|()
block|{
return|return
name|iSessionId
return|;
block|}
specifier|public
name|void
name|setSessionId
parameter_list|(
name|Long
name|sessionId
parameter_list|)
block|{
name|iSessionId
operator|=
name|sessionId
expr_stmt|;
block|}
specifier|public
name|String
name|getId
parameter_list|()
block|{
return|return
name|iId
return|;
block|}
specifier|public
name|void
name|setId
parameter_list|(
name|String
name|id
parameter_list|)
block|{
name|iId
operator|=
name|id
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
name|void
name|setName
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|iName
operator|=
name|name
expr_stmt|;
block|}
specifier|public
name|String
name|getDegree
parameter_list|()
block|{
return|return
name|iDegree
return|;
block|}
specifier|public
name|void
name|setDegree
parameter_list|(
name|String
name|degree
parameter_list|)
block|{
name|iDegree
operator|=
name|degree
expr_stmt|;
block|}
specifier|public
name|String
name|getSchool
parameter_list|()
block|{
return|return
name|iSchool
return|;
block|}
specifier|public
name|void
name|setSchool
parameter_list|(
name|String
name|school
parameter_list|)
block|{
name|iSchool
operator|=
name|school
expr_stmt|;
block|}
specifier|public
name|String
name|getTrackingStatus
parameter_list|()
block|{
return|return
name|iTrack
return|;
block|}
specifier|public
name|void
name|setTrackingStatus
parameter_list|(
name|String
name|track
parameter_list|)
block|{
name|iTrack
operator|=
name|track
expr_stmt|;
block|}
specifier|public
name|Date
name|getLastModified
parameter_list|()
block|{
return|return
name|iModified
return|;
block|}
specifier|public
name|void
name|setLastModified
parameter_list|(
name|Date
name|modified
parameter_list|)
block|{
name|iModified
operator|=
name|modified
expr_stmt|;
block|}
specifier|public
name|DegreeGroupInterface
name|getGroup
parameter_list|()
block|{
return|return
name|iGroup
return|;
block|}
specifier|public
name|void
name|setGroup
parameter_list|(
name|DegreeGroupInterface
name|group
parameter_list|)
block|{
name|iGroup
operator|=
name|group
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|iName
operator|+
literal|": "
operator|+
name|iGroup
return|;
block|}
specifier|public
specifier|static
specifier|abstract
class|class
name|DegreeItemInterface
implements|implements
name|IsSerializable
implements|,
name|Serializable
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
block|}
specifier|public
specifier|static
class|class
name|DegreeGroupInterface
extends|extends
name|DegreeItemInterface
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
specifier|private
name|boolean
name|iChoice
init|=
literal|false
decl_stmt|;
name|List
argument_list|<
name|DegreeCourseInterface
argument_list|>
name|iCourses
init|=
literal|null
decl_stmt|;
name|List
argument_list|<
name|DegreeGroupInterface
argument_list|>
name|iGroups
init|=
literal|null
decl_stmt|;
name|List
argument_list|<
name|DegreePlaceHolderInterface
argument_list|>
name|iPlaceHolders
init|=
literal|null
decl_stmt|;
specifier|private
name|Boolean
name|iSelected
init|=
literal|null
decl_stmt|;
specifier|private
name|String
name|iDescription
init|=
literal|null
decl_stmt|;
specifier|public
name|DegreeGroupInterface
parameter_list|()
block|{
block|}
specifier|public
name|boolean
name|isChoice
parameter_list|()
block|{
return|return
name|iChoice
return|;
block|}
specifier|public
name|void
name|setChoice
parameter_list|(
name|boolean
name|choice
parameter_list|)
block|{
name|iChoice
operator|=
name|choice
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasCourses
parameter_list|()
block|{
return|return
name|iCourses
operator|!=
literal|null
operator|&&
operator|!
name|iCourses
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|List
argument_list|<
name|DegreeCourseInterface
argument_list|>
name|getCourses
parameter_list|()
block|{
return|return
name|iCourses
return|;
block|}
specifier|public
name|void
name|addCourse
parameter_list|(
name|DegreeCourseInterface
name|course
parameter_list|)
block|{
if|if
condition|(
name|iCourses
operator|==
literal|null
condition|)
name|iCourses
operator|=
operator|new
name|ArrayList
argument_list|<
name|DegreeCourseInterface
argument_list|>
argument_list|()
expr_stmt|;
name|iCourses
operator|.
name|add
argument_list|(
name|course
argument_list|)
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasGroups
parameter_list|()
block|{
return|return
name|iGroups
operator|!=
literal|null
operator|&&
operator|!
name|iGroups
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|List
argument_list|<
name|DegreeGroupInterface
argument_list|>
name|getGroups
parameter_list|()
block|{
return|return
name|iGroups
return|;
block|}
specifier|public
name|void
name|addGroup
parameter_list|(
name|DegreeGroupInterface
name|group
parameter_list|)
block|{
if|if
condition|(
name|iGroups
operator|==
literal|null
condition|)
name|iGroups
operator|=
operator|new
name|ArrayList
argument_list|<
name|DegreeGroupInterface
argument_list|>
argument_list|()
expr_stmt|;
name|iGroups
operator|.
name|add
argument_list|(
name|group
argument_list|)
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasPlaceHolders
parameter_list|()
block|{
return|return
name|iPlaceHolders
operator|!=
literal|null
operator|&&
operator|!
name|iPlaceHolders
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|List
argument_list|<
name|DegreePlaceHolderInterface
argument_list|>
name|getPlaceHolders
parameter_list|()
block|{
return|return
name|iPlaceHolders
return|;
block|}
specifier|public
name|void
name|addPlaceHolder
parameter_list|(
name|DegreePlaceHolderInterface
name|placeHolder
parameter_list|)
block|{
if|if
condition|(
name|iPlaceHolders
operator|==
literal|null
condition|)
name|iPlaceHolders
operator|=
operator|new
name|ArrayList
argument_list|<
name|DegreePlaceHolderInterface
argument_list|>
argument_list|()
expr_stmt|;
name|iPlaceHolders
operator|.
name|add
argument_list|(
name|placeHolder
argument_list|)
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasSelected
parameter_list|()
block|{
return|return
name|iSelected
operator|!=
literal|null
return|;
block|}
specifier|public
name|boolean
name|isSelected
parameter_list|()
block|{
return|return
name|iSelected
operator|==
literal|null
operator|||
name|iSelected
operator|.
name|booleanValue
argument_list|()
return|;
block|}
specifier|public
name|void
name|setSelected
parameter_list|(
name|boolean
name|selected
parameter_list|)
block|{
name|iSelected
operator|=
name|selected
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasDescription
parameter_list|()
block|{
return|return
name|iDescription
operator|!=
literal|null
operator|&&
operator|!
name|iDescription
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|String
name|getDescription
parameter_list|()
block|{
return|return
name|hasDescription
argument_list|()
condition|?
name|iDescription
else|:
name|toString
argument_list|()
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
name|iDescription
operator|=
name|description
expr_stmt|;
block|}
specifier|public
name|int
name|getMaxDepth
parameter_list|()
block|{
if|if
condition|(
name|iGroups
operator|==
literal|null
operator|||
name|iGroups
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
literal|1
return|;
name|int
name|ret
init|=
literal|0
decl_stmt|;
for|for
control|(
name|DegreeGroupInterface
name|g
range|:
name|iGroups
control|)
if|if
condition|(
name|ret
operator|<
name|g
operator|.
name|getMaxDepth
argument_list|()
condition|)
name|ret
operator|=
name|g
operator|.
name|getMaxDepth
argument_list|()
expr_stmt|;
return|return
literal|1
operator|+
name|ret
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
name|String
name|ret
init|=
literal|""
decl_stmt|;
if|if
condition|(
name|iCourses
operator|!=
literal|null
condition|)
for|for
control|(
name|DegreeCourseInterface
name|course
range|:
name|iCourses
control|)
name|ret
operator|+=
operator|(
name|ret
operator|.
name|isEmpty
argument_list|()
condition|?
literal|""
else|:
name|iChoice
condition|?
literal|" and "
else|:
literal|" or "
operator|)
operator|+
name|course
expr_stmt|;
if|if
condition|(
name|iGroups
operator|!=
literal|null
condition|)
for|for
control|(
name|DegreeGroupInterface
name|group
range|:
name|iGroups
control|)
name|ret
operator|+=
operator|(
name|ret
operator|.
name|isEmpty
argument_list|()
condition|?
literal|""
else|:
name|iChoice
condition|?
literal|" and "
else|:
literal|" or "
operator|)
operator|+
literal|"("
operator|+
name|group
operator|+
literal|")"
expr_stmt|;
if|if
condition|(
name|iPlaceHolders
operator|!=
literal|null
condition|)
for|for
control|(
name|DegreePlaceHolderInterface
name|ph
range|:
name|iPlaceHolders
control|)
name|ret
operator|+=
operator|(
name|ret
operator|.
name|isEmpty
argument_list|()
condition|?
literal|""
else|:
name|iChoice
condition|?
literal|" and "
else|:
literal|" or "
operator|)
operator|+
name|ph
expr_stmt|;
return|return
name|ret
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|DegreeCourseInterface
extends|extends
name|DegreeItemInterface
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
specifier|private
name|Long
name|iCourseId
init|=
literal|null
decl_stmt|;
specifier|private
name|String
name|iSubject
decl_stmt|,
name|iCourse
decl_stmt|,
name|iTitle
decl_stmt|,
name|iName
decl_stmt|;
specifier|private
name|Boolean
name|iSelected
init|=
literal|null
decl_stmt|;
specifier|public
name|DegreeCourseInterface
parameter_list|()
block|{
block|}
specifier|public
name|String
name|getSubject
parameter_list|()
block|{
return|return
name|iSubject
return|;
block|}
specifier|public
name|void
name|setSubject
parameter_list|(
name|String
name|subject
parameter_list|)
block|{
name|iSubject
operator|=
name|subject
expr_stmt|;
block|}
specifier|public
name|String
name|getCourse
parameter_list|()
block|{
return|return
name|iCourse
return|;
block|}
specifier|public
name|void
name|setCourse
parameter_list|(
name|String
name|course
parameter_list|)
block|{
name|iCourse
operator|=
name|course
expr_stmt|;
block|}
specifier|public
name|String
name|getTitle
parameter_list|()
block|{
return|return
name|iTitle
return|;
block|}
specifier|public
name|void
name|setTitle
parameter_list|(
name|String
name|title
parameter_list|)
block|{
name|iTitle
operator|=
name|title
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasSelected
parameter_list|()
block|{
return|return
name|iSelected
operator|!=
literal|null
return|;
block|}
specifier|public
name|boolean
name|isSelected
parameter_list|()
block|{
return|return
name|iSelected
operator|==
literal|null
operator|||
name|iSelected
operator|.
name|booleanValue
argument_list|()
return|;
block|}
specifier|public
name|void
name|setSelected
parameter_list|(
name|boolean
name|selected
parameter_list|)
block|{
name|iSelected
operator|=
name|selected
expr_stmt|;
block|}
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|iName
operator|==
literal|null
condition|?
name|iSubject
operator|+
literal|" "
operator|+
name|iCourse
else|:
name|iName
return|;
block|}
specifier|public
name|void
name|setName
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|iName
operator|=
name|name
expr_stmt|;
block|}
specifier|public
name|Long
name|getCourseId
parameter_list|()
block|{
return|return
name|iCourseId
return|;
block|}
specifier|public
name|void
name|setCourseId
parameter_list|(
name|long
name|courseId
parameter_list|)
block|{
name|iCourseId
operator|=
name|courseId
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|iSubject
operator|+
literal|" "
operator|+
name|iCourse
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|DegreePlaceHolderInterface
extends|extends
name|DegreeItemInterface
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
specifier|private
name|String
name|iType
decl_stmt|;
specifier|private
name|String
name|iName
decl_stmt|;
specifier|public
name|DegreePlaceHolderInterface
parameter_list|()
block|{
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
name|void
name|setName
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|iName
operator|=
name|name
expr_stmt|;
block|}
specifier|public
name|String
name|getType
parameter_list|()
block|{
return|return
name|iType
return|;
block|}
specifier|public
name|void
name|setType
parameter_list|(
name|String
name|type
parameter_list|)
block|{
name|iType
operator|=
name|type
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|iType
return|;
block|}
block|}
block|}
end_class

end_unit

