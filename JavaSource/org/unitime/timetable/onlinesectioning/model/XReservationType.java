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
name|Serializable
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_enum
specifier|public
enum|enum
name|XReservationType
implements|implements
name|Serializable
block|{
name|Individual
block|,
name|Group
block|,
name|IndividualOverride
block|,
name|GroupOverride
block|,
name|Course
block|,
name|Curriculum
block|,
name|Dummy
block|,
name|None
block|,
name|LearningCommunity
block|,
name|CurriculumOverride
block|,
name|IndividualGroup
block|, 	; }
end_enum

end_unit

