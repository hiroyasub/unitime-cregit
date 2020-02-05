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
name|onlinesectioning
operator|.
name|model
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|Externalizable
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|ObjectInput
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|ObjectOutput
import|;
end_import

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
name|BitSet
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
name|org
operator|.
name|infinispan
operator|.
name|commons
operator|.
name|marshall
operator|.
name|Externalizer
import|;
end_import

begin_import
import|import
name|org
operator|.
name|infinispan
operator|.
name|commons
operator|.
name|marshall
operator|.
name|SerializeWith
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
name|AdvisorCourseRequest
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
name|AdvisorSectioningPref
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
name|onlinesectioning
operator|.
name|OnlineSectioningHelper
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
name|onlinesectioning
operator|.
name|model
operator|.
name|XCourseRequest
operator|.
name|XPreference
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|SerializeWith
argument_list|(
name|XAdvisorRequest
operator|.
name|XAdvisorRequestSerializer
operator|.
name|class
argument_list|)
specifier|public
class|class
name|XAdvisorRequest
implements|implements
name|Comparable
argument_list|<
name|XAdvisorRequest
argument_list|>
implements|,
name|Serializable
implements|,
name|Externalizable
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
name|int
name|iPriority
decl_stmt|,
name|iAlternative
decl_stmt|;
specifier|private
name|boolean
name|iSubstitute
decl_stmt|;
specifier|private
name|String
name|iCourseName
decl_stmt|;
specifier|private
name|XCourseId
name|iCourseId
decl_stmt|;
specifier|private
name|String
name|iCredit
decl_stmt|,
name|iNote
decl_stmt|;
specifier|private
name|boolean
name|iCritical
decl_stmt|;
specifier|private
name|XTime
name|iFreeTime
decl_stmt|;
specifier|private
name|List
argument_list|<
name|XPreference
argument_list|>
name|iPreferences
init|=
literal|null
decl_stmt|;
specifier|public
name|XAdvisorRequest
parameter_list|(
name|AdvisorCourseRequest
name|acr
parameter_list|,
name|OnlineSectioningHelper
name|helper
parameter_list|,
name|BitSet
name|freeTimePattern
parameter_list|)
block|{
name|iPriority
operator|=
name|acr
operator|.
name|getPriority
argument_list|()
expr_stmt|;
name|iAlternative
operator|=
name|acr
operator|.
name|getAlternative
argument_list|()
expr_stmt|;
name|iSubstitute
operator|=
name|acr
operator|.
name|isSubstitute
argument_list|()
expr_stmt|;
name|iCritical
operator|=
operator|(
name|acr
operator|.
name|isCritical
argument_list|()
operator|!=
literal|null
operator|&&
name|acr
operator|.
name|isCritical
argument_list|()
operator|.
name|booleanValue
argument_list|()
operator|)
expr_stmt|;
name|iCourseName
operator|=
name|acr
operator|.
name|getCourse
argument_list|()
expr_stmt|;
if|if
condition|(
name|acr
operator|.
name|getCourseOffering
argument_list|()
operator|!=
literal|null
condition|)
name|iCourseId
operator|=
operator|new
name|XCourseId
argument_list|(
name|acr
operator|.
name|getCourseOffering
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|acr
operator|.
name|getFreeTime
argument_list|()
operator|!=
literal|null
condition|)
name|iFreeTime
operator|=
operator|new
name|XTime
argument_list|(
name|acr
operator|.
name|getFreeTime
argument_list|()
argument_list|,
name|freeTimePattern
argument_list|)
expr_stmt|;
name|iCredit
operator|=
name|acr
operator|.
name|getCredit
argument_list|()
expr_stmt|;
name|iNote
operator|=
name|acr
operator|.
name|getNotes
argument_list|()
expr_stmt|;
if|if
condition|(
name|acr
operator|.
name|getPreferences
argument_list|()
operator|!=
literal|null
operator|&&
operator|!
name|acr
operator|.
name|getPreferences
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|iPreferences
operator|=
operator|new
name|ArrayList
argument_list|<
name|XPreference
argument_list|>
argument_list|()
expr_stmt|;
for|for
control|(
name|AdvisorSectioningPref
name|p
range|:
name|acr
operator|.
name|getPreferences
argument_list|()
control|)
name|iPreferences
operator|.
name|add
argument_list|(
operator|new
name|XPreference
argument_list|(
name|acr
argument_list|,
name|p
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|XAdvisorRequest
parameter_list|(
name|ObjectInput
name|in
parameter_list|)
throws|throws
name|IOException
throws|,
name|ClassNotFoundException
block|{
name|readExternal
argument_list|(
name|in
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|readExternal
parameter_list|(
name|ObjectInput
name|in
parameter_list|)
throws|throws
name|IOException
throws|,
name|ClassNotFoundException
block|{
name|iPriority
operator|=
name|in
operator|.
name|readInt
argument_list|()
expr_stmt|;
name|iAlternative
operator|=
name|in
operator|.
name|readInt
argument_list|()
expr_stmt|;
name|iSubstitute
operator|=
name|in
operator|.
name|readBoolean
argument_list|()
expr_stmt|;
name|iCourseName
operator|=
operator|(
name|String
operator|)
name|in
operator|.
name|readObject
argument_list|()
expr_stmt|;
if|if
condition|(
name|in
operator|.
name|readBoolean
argument_list|()
condition|)
name|iCourseId
operator|=
operator|new
name|XCourseId
argument_list|(
name|in
argument_list|)
expr_stmt|;
else|else
name|iCourseId
operator|=
literal|null
expr_stmt|;
name|iCredit
operator|=
operator|(
name|String
operator|)
name|in
operator|.
name|readObject
argument_list|()
expr_stmt|;
empty_stmt|;
name|iNote
operator|=
operator|(
name|String
operator|)
name|in
operator|.
name|readObject
argument_list|()
expr_stmt|;
name|iCritical
operator|=
name|in
operator|.
name|readBoolean
argument_list|()
expr_stmt|;
if|if
condition|(
name|in
operator|.
name|readBoolean
argument_list|()
condition|)
name|iFreeTime
operator|=
operator|new
name|XTime
argument_list|(
name|in
argument_list|)
expr_stmt|;
else|else
name|iFreeTime
operator|=
literal|null
expr_stmt|;
name|int
name|prefs
init|=
name|in
operator|.
name|readInt
argument_list|()
decl_stmt|;
if|if
condition|(
name|prefs
operator|<
literal|0
condition|)
block|{
name|iPreferences
operator|=
literal|null
expr_stmt|;
block|}
else|else
block|{
name|iPreferences
operator|=
operator|new
name|ArrayList
argument_list|<
name|XPreference
argument_list|>
argument_list|(
name|prefs
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|prefs
condition|;
name|i
operator|++
control|)
name|iPreferences
operator|.
name|add
argument_list|(
operator|new
name|XPreference
argument_list|(
name|in
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|writeExternal
parameter_list|(
name|ObjectOutput
name|out
parameter_list|)
throws|throws
name|IOException
block|{
name|out
operator|.
name|writeInt
argument_list|(
name|iPriority
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeInt
argument_list|(
name|iAlternative
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeBoolean
argument_list|(
name|iSubstitute
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeObject
argument_list|(
name|iCourseName
argument_list|)
expr_stmt|;
if|if
condition|(
name|iCourseId
operator|==
literal|null
condition|)
block|{
name|out
operator|.
name|writeBoolean
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|out
operator|.
name|writeBoolean
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|iCourseId
operator|.
name|writeExternal
argument_list|(
name|out
argument_list|)
expr_stmt|;
block|}
name|out
operator|.
name|writeObject
argument_list|(
name|iCredit
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeObject
argument_list|(
name|iNote
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeBoolean
argument_list|(
name|iCritical
argument_list|)
expr_stmt|;
if|if
condition|(
name|iFreeTime
operator|==
literal|null
condition|)
block|{
name|out
operator|.
name|writeBoolean
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|out
operator|.
name|writeBoolean
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|iFreeTime
operator|.
name|writeExternal
argument_list|(
name|out
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|iPreferences
operator|==
literal|null
condition|)
block|{
name|out
operator|.
name|writeInt
argument_list|(
operator|-
literal|1
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|out
operator|.
name|writeInt
argument_list|(
name|iPreferences
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|XPreference
name|p
range|:
name|iPreferences
control|)
name|p
operator|.
name|writeExternal
argument_list|(
name|out
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|int
name|getPriority
parameter_list|()
block|{
return|return
name|iPriority
return|;
block|}
specifier|public
name|int
name|getAlternative
parameter_list|()
block|{
return|return
name|iAlternative
return|;
block|}
specifier|public
name|boolean
name|isSubstitute
parameter_list|()
block|{
return|return
name|iSubstitute
return|;
block|}
specifier|public
name|String
name|getCourseName
parameter_list|()
block|{
return|return
name|iCourseName
return|;
block|}
specifier|public
name|boolean
name|hasCourseName
parameter_list|()
block|{
return|return
name|iCourseName
operator|!=
literal|null
operator|&&
operator|!
name|iCourseName
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|XCourseId
name|getCourseId
parameter_list|()
block|{
return|return
name|iCourseId
return|;
block|}
specifier|public
name|boolean
name|hasCourseId
parameter_list|()
block|{
return|return
name|iCourseId
operator|!=
literal|null
return|;
block|}
specifier|public
name|XTime
name|getFreeTime
parameter_list|()
block|{
return|return
name|iFreeTime
return|;
block|}
specifier|public
name|boolean
name|hasFreeTime
parameter_list|()
block|{
return|return
name|iFreeTime
operator|!=
literal|null
return|;
block|}
specifier|public
name|String
name|getCredit
parameter_list|()
block|{
return|return
name|iCredit
return|;
block|}
specifier|public
name|boolean
name|hasCredit
parameter_list|()
block|{
return|return
name|iCredit
operator|!=
literal|null
operator|&&
operator|!
name|iCredit
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|String
name|getNote
parameter_list|()
block|{
return|return
name|iNote
return|;
block|}
specifier|public
name|boolean
name|hasNote
parameter_list|()
block|{
return|return
name|iNote
operator|!=
literal|null
operator|&&
operator|!
name|iNote
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|boolean
name|isCritical
parameter_list|()
block|{
return|return
name|iCritical
return|;
block|}
specifier|public
name|boolean
name|hasPreferences
parameter_list|()
block|{
return|return
name|iPreferences
operator|!=
literal|null
operator|&&
operator|!
name|iPreferences
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|List
argument_list|<
name|XPreference
argument_list|>
name|getPreferences
parameter_list|()
block|{
return|return
name|iPreferences
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|compareTo
parameter_list|(
name|XAdvisorRequest
name|r
parameter_list|)
block|{
if|if
condition|(
name|getPriority
argument_list|()
operator|!=
name|r
operator|.
name|getPriority
argument_list|()
condition|)
return|return
name|getPriority
argument_list|()
operator|<
name|r
operator|.
name|getPriority
argument_list|()
condition|?
operator|-
literal|1
else|:
literal|1
return|;
if|if
condition|(
name|getAlternative
argument_list|()
operator|!=
name|r
operator|.
name|getAlternative
argument_list|()
condition|)
return|return
name|getAlternative
argument_list|()
operator|<
name|r
operator|.
name|getAlternative
argument_list|()
condition|?
operator|-
literal|1
else|:
literal|1
return|;
return|return
literal|0
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
name|XAdvisorRequest
operator|)
condition|)
return|return
literal|false
return|;
name|XAdvisorRequest
name|ar
init|=
operator|(
name|XAdvisorRequest
operator|)
name|o
decl_stmt|;
return|return
name|getPriority
argument_list|()
operator|==
name|ar
operator|.
name|getPriority
argument_list|()
operator|&&
name|getAlternative
argument_list|()
operator|==
name|ar
operator|.
name|getAlternative
argument_list|()
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
literal|100
operator|*
name|getPriority
argument_list|()
operator|+
name|getAlternative
argument_list|()
return|;
block|}
specifier|public
specifier|static
class|class
name|XAdvisorRequestSerializer
implements|implements
name|Externalizer
argument_list|<
name|XAdvisorRequest
argument_list|>
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
annotation|@
name|Override
specifier|public
name|void
name|writeObject
parameter_list|(
name|ObjectOutput
name|output
parameter_list|,
name|XAdvisorRequest
name|object
parameter_list|)
throws|throws
name|IOException
block|{
name|object
operator|.
name|writeExternal
argument_list|(
name|output
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|XAdvisorRequest
name|readObject
parameter_list|(
name|ObjectInput
name|input
parameter_list|)
throws|throws
name|IOException
throws|,
name|ClassNotFoundException
block|{
return|return
operator|new
name|XAdvisorRequest
argument_list|(
name|input
argument_list|)
return|;
block|}
block|}
block|}
end_class

end_unit

