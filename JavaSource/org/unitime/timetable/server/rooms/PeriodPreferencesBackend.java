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
name|rooms
package|;
end_package

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
name|Set
import|;
end_import

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
name|GwtConstants
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
name|RoomInterface
operator|.
name|ExamTypeInterface
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
name|PeriodInterface
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
name|PeriodPreferenceModel
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
name|PeriodPreferenceRequest
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
name|ExamLocationPref
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
name|ExamPeriod
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
name|ExamType
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
name|Location
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
name|model
operator|.
name|dao
operator|.
name|ExamTypeDAO
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
name|LocationDAO
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

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|GwtRpcImplements
argument_list|(
name|PeriodPreferenceRequest
operator|.
name|class
argument_list|)
specifier|public
class|class
name|PeriodPreferencesBackend
implements|implements
name|GwtRpcImplementation
argument_list|<
name|PeriodPreferenceRequest
argument_list|,
name|PeriodPreferenceModel
argument_list|>
block|{
specifier|protected
specifier|static
specifier|final
name|GwtConstants
name|CONSTANTS
init|=
name|Localization
operator|.
name|create
argument_list|(
name|GwtConstants
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|protected
specifier|static
specifier|final
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
name|PeriodPreferenceModel
name|execute
parameter_list|(
name|PeriodPreferenceRequest
name|request
parameter_list|,
name|SessionContext
name|context
parameter_list|)
block|{
switch|switch
condition|(
name|request
operator|.
name|getOperation
argument_list|()
condition|)
block|{
case|case
name|LOAD
case|:
return|return
name|loadPeriodPreferences
argument_list|(
name|request
argument_list|,
name|context
argument_list|)
return|;
case|case
name|SAVE
case|:
return|return
name|savePeriodPreferences
argument_list|(
name|request
argument_list|,
name|context
argument_list|)
return|;
default|default:
return|return
literal|null
return|;
block|}
block|}
specifier|public
name|PeriodPreferenceModel
name|loadPeriodPreferences
parameter_list|(
name|PeriodPreferenceRequest
name|request
parameter_list|,
name|SessionContext
name|context
parameter_list|)
block|{
name|PeriodPreferenceModel
name|model
init|=
operator|new
name|PeriodPreferenceModel
argument_list|()
decl_stmt|;
name|Location
name|location
init|=
name|LocationDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|request
operator|.
name|getLocationId
argument_list|()
argument_list|)
decl_stmt|;
name|ExamType
name|type
init|=
name|ExamTypeDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|request
operator|.
name|getExamTypeId
argument_list|()
argument_list|)
decl_stmt|;
name|model
operator|.
name|setLocationId
argument_list|(
name|location
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|model
operator|.
name|setExamType
argument_list|(
operator|new
name|ExamTypeInterface
argument_list|(
name|type
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|type
operator|.
name|getReference
argument_list|()
argument_list|,
name|type
operator|.
name|getLabel
argument_list|()
argument_list|,
name|type
operator|.
name|getType
argument_list|()
operator|==
name|ExamType
operator|.
name|sExamTypeFinal
argument_list|)
argument_list|)
expr_stmt|;
name|model
operator|.
name|setFirstDate
argument_list|(
name|location
operator|.
name|getSession
argument_list|()
operator|.
name|getExamBeginDate
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|ExamPeriod
name|period
range|:
operator|(
name|Set
argument_list|<
name|ExamPeriod
argument_list|>
operator|)
name|ExamPeriod
operator|.
name|findAll
argument_list|(
name|location
operator|.
name|getSession
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|type
argument_list|)
control|)
block|{
name|model
operator|.
name|addPeriod
argument_list|(
operator|new
name|PeriodInterface
argument_list|(
name|period
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|period
operator|.
name|getDateOffset
argument_list|()
argument_list|,
name|period
operator|.
name|getStartSlot
argument_list|()
argument_list|,
name|period
operator|.
name|getLength
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|PreferenceLevel
name|pref
range|:
name|PreferenceLevel
operator|.
name|getPreferenceLevelList
argument_list|(
name|model
operator|.
name|getPeriods
argument_list|()
operator|.
name|size
argument_list|()
operator|<
name|model
operator|.
name|getDays
argument_list|()
operator|.
name|size
argument_list|()
operator|*
name|model
operator|.
name|getSlots
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
control|)
block|{
name|PreferenceInterface
name|p
init|=
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
literal|true
argument_list|)
decl_stmt|;
if|if
condition|(
name|PreferenceLevel
operator|.
name|sNeutral
operator|.
name|equals
argument_list|(
name|pref
operator|.
name|getPrefProlog
argument_list|()
argument_list|)
condition|)
name|model
operator|.
name|setDefaultPreference
argument_list|(
name|p
argument_list|)
expr_stmt|;
name|model
operator|.
name|addPreference
argument_list|(
name|p
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|Iterator
name|i
init|=
name|location
operator|.
name|getExamPreferences
argument_list|()
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
name|ExamLocationPref
name|pref
init|=
operator|(
name|ExamLocationPref
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|type
operator|.
name|equals
argument_list|(
name|pref
operator|.
name|getExamPeriod
argument_list|()
operator|.
name|getExamType
argument_list|()
argument_list|)
condition|)
continue|continue;
name|model
operator|.
name|setPreference
argument_list|(
name|pref
operator|.
name|getExamPeriod
argument_list|()
operator|.
name|getDateOffset
argument_list|()
argument_list|,
name|pref
operator|.
name|getExamPeriod
argument_list|()
operator|.
name|getStartSlot
argument_list|()
argument_list|,
name|pref
operator|.
name|getPrefLevel
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|model
return|;
block|}
specifier|public
name|PeriodPreferenceModel
name|savePeriodPreferences
parameter_list|(
name|PeriodPreferenceRequest
name|request
parameter_list|,
name|SessionContext
name|context
parameter_list|)
block|{
return|return
literal|null
return|;
block|}
block|}
end_class

end_unit
