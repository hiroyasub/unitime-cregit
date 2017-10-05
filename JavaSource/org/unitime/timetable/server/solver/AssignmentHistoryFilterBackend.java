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
name|server
operator|.
name|solver
package|;
end_package

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|localization
operator|.
name|impl
operator|.
name|Localization
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
name|GwtRpcImplementation
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
name|resources
operator|.
name|GwtMessages
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
name|CourseTimetablingSolverInterface
operator|.
name|AssignmentHistoryFilterRequest
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
name|CourseTimetablingSolverInterface
operator|.
name|AssignmentHistoryFilterResponse
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
name|FilterInterface
operator|.
name|FilterParameterInterface
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
name|model
operator|.
name|PreferenceLevel
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
name|SessionContext
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

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|GwtRpcImplements
argument_list|(
name|AssignmentHistoryFilterRequest
operator|.
name|class
argument_list|)
specifier|public
class|class
name|AssignmentHistoryFilterBackend
implements|implements
name|GwtRpcImplementation
argument_list|<
name|AssignmentHistoryFilterRequest
argument_list|,
name|AssignmentHistoryFilterResponse
argument_list|>
block|{
specifier|protected
specifier|static
name|GwtMessages
name|MESSAGES
init|=
name|Localization
operator|.
name|create
argument_list|(
name|GwtMessages
operator|.
name|class
argument_list|)
decl_stmt|;
annotation|@
name|Override
specifier|public
name|AssignmentHistoryFilterResponse
name|execute
parameter_list|(
name|AssignmentHistoryFilterRequest
name|request
parameter_list|,
name|SessionContext
name|context
parameter_list|)
block|{
name|context
operator|.
name|checkPermission
argument_list|(
name|Right
operator|.
name|AssignmentHistory
argument_list|)
expr_stmt|;
name|AssignmentHistoryFilterResponse
name|response
init|=
operator|new
name|AssignmentHistoryFilterResponse
argument_list|()
decl_stmt|;
name|FilterParameterInterface
name|simplifiedMode
init|=
operator|new
name|FilterParameterInterface
argument_list|()
decl_stmt|;
name|simplifiedMode
operator|.
name|setName
argument_list|(
literal|"simpleMode"
argument_list|)
expr_stmt|;
name|simplifiedMode
operator|.
name|setType
argument_list|(
literal|"boolean"
argument_list|)
expr_stmt|;
name|simplifiedMode
operator|.
name|setLabel
argument_list|(
name|MESSAGES
operator|.
name|propCourseTimetablingSolverSimplifiedMode
argument_list|()
argument_list|)
expr_stmt|;
name|simplifiedMode
operator|.
name|setDefaultValue
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getProperty
argument_list|(
literal|"SuggestionsModel.simpleMode"
argument_list|,
literal|"0"
argument_list|)
argument_list|)
expr_stmt|;
name|response
operator|.
name|addParameter
argument_list|(
name|simplifiedMode
argument_list|)
expr_stmt|;
for|for
control|(
name|PreferenceLevel
name|pref
range|:
name|PreferenceLevel
operator|.
name|getPreferenceLevelList
argument_list|(
literal|true
argument_list|)
control|)
name|response
operator|.
name|addPreference
argument_list|(
operator|new
name|PreferenceInterface
argument_list|(
name|pref
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|PreferenceLevel
operator|.
name|prolog2bgColor
argument_list|(
name|pref
operator|.
name|getPrefProlog
argument_list|()
argument_list|)
argument_list|,
name|pref
operator|.
name|getPrefProlog
argument_list|()
argument_list|,
name|pref
operator|.
name|getPrefName
argument_list|()
argument_list|,
name|pref
operator|.
name|getAbbreviation
argument_list|()
argument_list|,
literal|false
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|response
return|;
block|}
block|}
end_class

end_unit

