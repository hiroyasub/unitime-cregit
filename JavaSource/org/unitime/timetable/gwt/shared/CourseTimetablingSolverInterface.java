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
name|List
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
name|GwtRpcRequest
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
name|RoomInterface
operator|.
name|PreferenceInterface
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
name|SolverInterface
operator|.
name|HasPageMessages
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
name|SolverInterface
operator|.
name|PageMessage
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
name|SuggestionsInterface
operator|.
name|SuggestionProperties
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|CourseTimetablingSolverInterface
block|{
specifier|public
specifier|static
class|class
name|AssignedClassesFilterRequest
implements|implements
name|GwtRpcRequest
argument_list|<
name|AssignedClassesFilterResponse
argument_list|>
implements|,
name|Serializable
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|0l
decl_stmt|;
block|}
specifier|public
specifier|static
class|class
name|AssignedClassesFilterResponse
extends|extends
name|FilterInterface
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|0l
decl_stmt|;
specifier|private
name|List
argument_list|<
name|PreferenceInterface
argument_list|>
name|iPreferences
init|=
operator|new
name|ArrayList
argument_list|<
name|PreferenceInterface
argument_list|>
argument_list|()
decl_stmt|;
specifier|public
name|void
name|addPreference
parameter_list|(
name|PreferenceInterface
name|preference
parameter_list|)
block|{
name|iPreferences
operator|.
name|add
argument_list|(
name|preference
argument_list|)
expr_stmt|;
block|}
specifier|public
name|List
argument_list|<
name|PreferenceInterface
argument_list|>
name|getPreferences
parameter_list|()
block|{
return|return
name|iPreferences
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|NotAssignedClassesFilterRequest
implements|implements
name|GwtRpcRequest
argument_list|<
name|NotAssignedClassesFilterResponse
argument_list|>
implements|,
name|Serializable
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|0l
decl_stmt|;
block|}
specifier|public
specifier|static
class|class
name|NotAssignedClassesFilterResponse
extends|extends
name|AssignedClassesFilterResponse
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|0l
decl_stmt|;
block|}
specifier|public
specifier|static
class|class
name|AssignedClassesRequest
implements|implements
name|GwtRpcRequest
argument_list|<
name|AssignedClassesResponse
argument_list|>
implements|,
name|Serializable
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|0l
decl_stmt|;
specifier|private
name|FilterInterface
name|iFilter
decl_stmt|;
specifier|public
name|FilterInterface
name|getFilter
parameter_list|()
block|{
return|return
name|iFilter
return|;
block|}
specifier|public
name|void
name|setFilter
parameter_list|(
name|FilterInterface
name|filter
parameter_list|)
block|{
name|iFilter
operator|=
name|filter
expr_stmt|;
block|}
block|}
specifier|public
specifier|static
class|class
name|AssignedClassesResponse
extends|extends
name|TableInterface
implements|implements
name|HasPageMessages
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|0l
decl_stmt|;
specifier|private
name|List
argument_list|<
name|PageMessage
argument_list|>
name|iPageMessages
init|=
literal|null
decl_stmt|;
specifier|public
name|boolean
name|hasPageMessages
parameter_list|()
block|{
return|return
name|iPageMessages
operator|!=
literal|null
operator|&&
operator|!
name|iPageMessages
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|List
argument_list|<
name|PageMessage
argument_list|>
name|getPageMessages
parameter_list|()
block|{
return|return
name|iPageMessages
return|;
block|}
specifier|public
name|void
name|addPageMessage
parameter_list|(
name|PageMessage
name|message
parameter_list|)
block|{
if|if
condition|(
name|iPageMessages
operator|==
literal|null
condition|)
name|iPageMessages
operator|=
operator|new
name|ArrayList
argument_list|<
name|PageMessage
argument_list|>
argument_list|()
expr_stmt|;
name|iPageMessages
operator|.
name|add
argument_list|(
name|message
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
specifier|static
class|class
name|NotAssignedClassesRequest
implements|implements
name|GwtRpcRequest
argument_list|<
name|NotAssignedClassesResponse
argument_list|>
implements|,
name|Serializable
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|0l
decl_stmt|;
specifier|private
name|FilterInterface
name|iFilter
decl_stmt|;
specifier|public
name|FilterInterface
name|getFilter
parameter_list|()
block|{
return|return
name|iFilter
return|;
block|}
specifier|public
name|void
name|setFilter
parameter_list|(
name|FilterInterface
name|filter
parameter_list|)
block|{
name|iFilter
operator|=
name|filter
expr_stmt|;
block|}
block|}
specifier|public
specifier|static
class|class
name|NotAssignedClassesResponse
extends|extends
name|AssignedClassesResponse
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|0l
decl_stmt|;
specifier|private
name|boolean
name|iShowNote
init|=
literal|false
decl_stmt|;
specifier|public
name|boolean
name|isShowNote
parameter_list|()
block|{
return|return
name|iShowNote
return|;
block|}
specifier|public
name|void
name|setShowNote
parameter_list|(
name|boolean
name|showNote
parameter_list|)
block|{
name|iShowNote
operator|=
name|showNote
expr_stmt|;
block|}
block|}
specifier|public
specifier|static
class|class
name|ConflictStatisticsFilterRequest
implements|implements
name|GwtRpcRequest
argument_list|<
name|ConflictStatisticsFilterResponse
argument_list|>
implements|,
name|Serializable
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|0l
decl_stmt|;
block|}
specifier|public
specifier|static
class|class
name|ConflictStatisticsFilterResponse
extends|extends
name|FilterInterface
implements|implements
name|HasPageMessages
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|0l
decl_stmt|;
specifier|private
name|SuggestionProperties
name|iProperties
decl_stmt|;
specifier|private
name|List
argument_list|<
name|PageMessage
argument_list|>
name|iPageMessages
init|=
literal|null
decl_stmt|;
specifier|public
name|SuggestionProperties
name|getSuggestionProperties
parameter_list|()
block|{
return|return
name|iProperties
return|;
block|}
specifier|public
name|void
name|setSuggestionProperties
parameter_list|(
name|SuggestionProperties
name|properties
parameter_list|)
block|{
name|iProperties
operator|=
name|properties
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasPageMessages
parameter_list|()
block|{
return|return
name|iPageMessages
operator|!=
literal|null
operator|&&
operator|!
name|iPageMessages
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|List
argument_list|<
name|PageMessage
argument_list|>
name|getPageMessages
parameter_list|()
block|{
return|return
name|iPageMessages
return|;
block|}
specifier|public
name|void
name|addPageMessage
parameter_list|(
name|PageMessage
name|message
parameter_list|)
block|{
if|if
condition|(
name|iPageMessages
operator|==
literal|null
condition|)
name|iPageMessages
operator|=
operator|new
name|ArrayList
argument_list|<
name|PageMessage
argument_list|>
argument_list|()
expr_stmt|;
name|iPageMessages
operator|.
name|add
argument_list|(
name|message
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

