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
name|model
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Calendar
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
name|HashSet
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
name|resources
operator|.
name|StudentSectioningMessages
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
name|base
operator|.
name|BaseStudentSectioningStatus
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
name|model
operator|.
name|dao
operator|.
name|StudentSectioningStatusDAO
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
name|Formats
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|StudentSectioningStatus
extends|extends
name|BaseStudentSectioningStatus
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
operator|-
literal|33276457852954947L
decl_stmt|;
specifier|private
specifier|static
name|StudentSectioningMessages
name|MSG
init|=
name|Localization
operator|.
name|create
argument_list|(
name|StudentSectioningMessages
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|public
specifier|static
enum|enum
name|Option
block|{
comment|/*    1 */
name|enabled
argument_list|(
literal|"Scheduling Assistant Access Enabled"
argument_list|)
block|,
comment|/*    2 */
name|advisor
argument_list|(
literal|"Advisor Can Enroll"
argument_list|)
block|,
comment|/*    4 */
name|email
argument_list|(
literal|"Email Notifications"
argument_list|)
block|,
comment|/*    8 */
name|notype
argument_list|(
literal|"Must Have Course Type"
argument_list|)
block|,
comment|/*   16 */
name|waitlist
argument_list|(
literal|"Wait-Listing Enabled"
argument_list|)
block|,
comment|/*   32 */
name|nobatch
argument_list|(
literal|"Do Not Schedule in Batch Solver"
argument_list|)
block|,
comment|/*   64 */
name|enrollment
argument_list|(
literal|"Student Can Enrol"
argument_list|)
block|,
comment|/*  128 */
name|admin
argument_list|(
literal|"Admin Can Enroll"
argument_list|)
block|,
comment|/*  256 */
name|registration
argument_list|(
literal|"Student Can Register"
argument_list|)
block|,
comment|/*  512 */
name|regenabled
argument_list|(
literal|"Course Requests Access Enabled"
argument_list|)
block|,
comment|/* 1024 */
name|regadvisor
argument_list|(
literal|"Advisor Can Register"
argument_list|)
block|,
comment|/* 2048 */
name|regadmin
argument_list|(
literal|"Admin Can Register"
argument_list|)
block|,
comment|/* 4196 */
name|advcanset
argument_list|(
literal|"Advisor Can Set Status"
argument_list|)
block|,
comment|/* 8192 */
name|reqval
argument_list|(
literal|"Course Request Validation"
argument_list|)
block|,
comment|/*16384 */
name|specreg
argument_list|(
literal|"Special Registration"
argument_list|)
block|,
comment|/*32768 */
name|canreq
argument_list|(
literal|"Can Require Sections / IMs"
argument_list|)
block|,
comment|/*65536 */
name|noschedule
argument_list|(
literal|"Do Not Show Personal Schedule"
argument_list|)
block|,
comment|/*131072*/
name|nosubs
argument_list|(
literal|"No-Substitutions Enabled"
argument_list|)
block|, 		;
specifier|private
name|String
name|iName
decl_stmt|;
name|Option
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
name|getName
parameter_list|()
block|{
return|return
name|iName
return|;
block|}
specifier|public
name|int
name|toggle
parameter_list|()
block|{
return|return
literal|1
operator|<<
name|ordinal
argument_list|()
return|;
block|}
block|}
specifier|public
name|boolean
name|hasOption
parameter_list|(
name|Option
modifier|...
name|options
parameter_list|)
block|{
if|if
condition|(
name|getStatus
argument_list|()
operator|==
literal|null
condition|)
return|return
literal|false
return|;
for|for
control|(
name|Option
name|option
range|:
name|options
control|)
block|{
if|if
condition|(
operator|(
name|getStatus
argument_list|()
operator|&
name|option
operator|.
name|toggle
argument_list|()
operator|)
operator|==
literal|0
condition|)
return|return
literal|false
return|;
block|}
return|return
literal|true
return|;
block|}
specifier|public
name|void
name|addOption
parameter_list|(
name|Option
name|option
parameter_list|)
block|{
if|if
condition|(
operator|!
name|hasOption
argument_list|(
name|option
argument_list|)
condition|)
name|setStatus
argument_list|(
operator|(
name|getStatus
argument_list|()
operator|==
literal|null
condition|?
literal|0
else|:
name|getStatus
argument_list|()
operator|)
operator|+
name|option
operator|.
name|toggle
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|removeOption
parameter_list|(
name|Option
name|option
parameter_list|)
block|{
if|if
condition|(
name|hasOption
argument_list|(
name|option
argument_list|)
condition|)
name|setStatus
argument_list|(
name|getStatus
argument_list|()
operator|-
name|option
operator|.
name|toggle
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
name|StudentSectioningStatus
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
specifier|public
specifier|static
name|StudentSectioningStatus
name|getStatus
parameter_list|(
name|String
name|reference
parameter_list|,
name|Long
name|sessionId
parameter_list|,
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|)
block|{
if|if
condition|(
name|reference
operator|!=
literal|null
condition|)
block|{
name|StudentSectioningStatus
name|status
init|=
operator|(
name|StudentSectioningStatus
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"from StudentSectioningStatus s where s.reference = :reference and s.session is null"
argument_list|)
operator|.
name|setString
argument_list|(
literal|"reference"
argument_list|,
name|reference
argument_list|)
operator|.
name|setMaxResults
argument_list|(
literal|1
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|uniqueResult
argument_list|()
decl_stmt|;
if|if
condition|(
name|status
operator|!=
literal|null
condition|)
return|return
name|status
return|;
if|if
condition|(
name|sessionId
operator|!=
literal|null
condition|)
block|{
name|status
operator|=
operator|(
name|StudentSectioningStatus
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"from StudentSectioningStatus s where s.reference = :reference and s.session = :sessionId"
argument_list|)
operator|.
name|setString
argument_list|(
literal|"reference"
argument_list|,
name|reference
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
argument_list|)
operator|.
name|setMaxResults
argument_list|(
literal|1
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|uniqueResult
argument_list|()
expr_stmt|;
if|if
condition|(
name|status
operator|!=
literal|null
condition|)
return|return
name|status
return|;
block|}
block|}
if|if
condition|(
name|sessionId
operator|!=
literal|null
condition|)
block|{
name|StudentSectioningStatus
name|status
init|=
operator|(
name|StudentSectioningStatus
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"select s.defaultSectioningStatus from Session s where s.uniqueId = :sessionId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
argument_list|)
operator|.
name|setMaxResults
argument_list|(
literal|1
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|uniqueResult
argument_list|()
decl_stmt|;
if|if
condition|(
name|status
operator|!=
literal|null
condition|)
return|return
name|status
return|;
block|}
return|return
literal|null
return|;
block|}
specifier|public
specifier|static
name|boolean
name|hasOption
parameter_list|(
name|Option
name|option
parameter_list|,
name|String
name|reference
parameter_list|,
name|Long
name|sessionId
parameter_list|,
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|)
block|{
name|StudentSectioningStatus
name|status
init|=
name|getStatus
argument_list|(
name|reference
argument_list|,
name|sessionId
argument_list|,
name|hibSession
argument_list|)
decl_stmt|;
return|return
name|status
operator|==
literal|null
operator|||
name|status
operator|.
name|hasOption
argument_list|(
name|option
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|Set
argument_list|<
name|String
argument_list|>
name|getMatchingStatuses
parameter_list|(
name|Long
name|sessionId
parameter_list|,
name|Option
modifier|...
name|options
parameter_list|)
block|{
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
name|StudentSectioningStatusDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|createNewSession
argument_list|()
decl_stmt|;
try|try
block|{
name|Session
name|session
init|=
name|SessionDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|sessionId
argument_list|,
name|hibSession
argument_list|)
decl_stmt|;
name|Set
argument_list|<
name|String
argument_list|>
name|statuses
init|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|StudentSectioningStatus
name|status
range|:
name|StudentSectioningStatus
operator|.
name|findAll
argument_list|(
name|hibSession
argument_list|,
name|sessionId
argument_list|)
control|)
block|{
if|if
condition|(
name|StudentSectioningStatus
operator|.
name|hasEffectiveOption
argument_list|(
name|status
argument_list|,
name|session
argument_list|,
name|options
argument_list|)
condition|)
name|statuses
operator|.
name|add
argument_list|(
name|status
operator|.
name|getReference
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|statuses
return|;
block|}
finally|finally
block|{
name|hibSession
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
block|}
specifier|public
name|boolean
name|isEffectiveNow
parameter_list|()
block|{
name|Calendar
name|cal
init|=
name|Calendar
operator|.
name|getInstance
argument_list|(
name|Localization
operator|.
name|getJavaLocale
argument_list|()
argument_list|)
decl_stmt|;
name|int
name|slot
init|=
literal|12
operator|*
name|cal
operator|.
name|get
argument_list|(
name|Calendar
operator|.
name|HOUR_OF_DAY
argument_list|)
operator|+
name|cal
operator|.
name|get
argument_list|(
name|Calendar
operator|.
name|MINUTE
argument_list|)
operator|/
literal|5
decl_stmt|;
name|cal
operator|.
name|set
argument_list|(
name|Calendar
operator|.
name|HOUR_OF_DAY
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|cal
operator|.
name|set
argument_list|(
name|Calendar
operator|.
name|MINUTE
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|cal
operator|.
name|set
argument_list|(
name|Calendar
operator|.
name|SECOND
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|cal
operator|.
name|set
argument_list|(
name|Calendar
operator|.
name|MILLISECOND
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|Date
name|today
init|=
name|cal
operator|.
name|getTime
argument_list|()
decl_stmt|;
if|if
condition|(
name|getEffectiveStartDate
argument_list|()
operator|!=
literal|null
operator|&&
name|today
operator|.
name|before
argument_list|(
name|getEffectiveStartDate
argument_list|()
argument_list|)
condition|)
return|return
literal|false
return|;
if|if
condition|(
name|getEffectiveStartPeriod
argument_list|()
operator|!=
literal|null
operator|&&
operator|(
name|getEffectiveStartDate
argument_list|()
operator|==
literal|null
operator|||
name|today
operator|.
name|equals
argument_list|(
name|getEffectiveStartDate
argument_list|()
argument_list|)
operator|)
operator|&&
name|slot
operator|<
name|getEffectiveStartPeriod
argument_list|()
condition|)
return|return
literal|false
return|;
if|if
condition|(
name|getEffectiveStopDate
argument_list|()
operator|!=
literal|null
operator|&&
name|today
operator|.
name|after
argument_list|(
name|getEffectiveStopDate
argument_list|()
argument_list|)
condition|)
return|return
literal|false
return|;
if|if
condition|(
name|getEffectiveStopPeriod
argument_list|()
operator|!=
literal|null
operator|&&
operator|(
name|getEffectiveStopPeriod
argument_list|()
operator|==
literal|null
operator|||
name|today
operator|.
name|equals
argument_list|(
name|getEffectiveStopDate
argument_list|()
argument_list|)
operator|)
operator|&&
name|slot
operator|>=
name|getEffectiveStopPeriod
argument_list|()
condition|)
return|return
literal|false
return|;
return|return
literal|true
return|;
block|}
specifier|public
name|boolean
name|isPast
parameter_list|()
block|{
name|Calendar
name|cal
init|=
name|Calendar
operator|.
name|getInstance
argument_list|(
name|Localization
operator|.
name|getJavaLocale
argument_list|()
argument_list|)
decl_stmt|;
name|int
name|slot
init|=
literal|12
operator|*
name|cal
operator|.
name|get
argument_list|(
name|Calendar
operator|.
name|HOUR_OF_DAY
argument_list|)
operator|+
name|cal
operator|.
name|get
argument_list|(
name|Calendar
operator|.
name|MINUTE
argument_list|)
operator|/
literal|5
decl_stmt|;
name|cal
operator|.
name|set
argument_list|(
name|Calendar
operator|.
name|HOUR_OF_DAY
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|cal
operator|.
name|set
argument_list|(
name|Calendar
operator|.
name|MINUTE
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|cal
operator|.
name|set
argument_list|(
name|Calendar
operator|.
name|SECOND
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|cal
operator|.
name|set
argument_list|(
name|Calendar
operator|.
name|MILLISECOND
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|Date
name|today
init|=
name|cal
operator|.
name|getTime
argument_list|()
decl_stmt|;
if|if
condition|(
name|getEffectiveStopDate
argument_list|()
operator|!=
literal|null
operator|&&
name|today
operator|.
name|after
argument_list|(
name|getEffectiveStopDate
argument_list|()
argument_list|)
condition|)
return|return
literal|true
return|;
if|if
condition|(
name|getEffectiveStopPeriod
argument_list|()
operator|!=
literal|null
operator|&&
operator|(
name|getEffectiveStopPeriod
argument_list|()
operator|==
literal|null
operator|||
name|today
operator|.
name|equals
argument_list|(
name|getEffectiveStopDate
argument_list|()
argument_list|)
operator|)
operator|&&
name|slot
operator|>=
name|getEffectiveStopPeriod
argument_list|()
condition|)
return|return
literal|true
return|;
return|return
literal|false
return|;
block|}
specifier|public
specifier|static
name|boolean
name|hasEffectiveOption
parameter_list|(
name|StudentSectioningStatus
name|status
parameter_list|,
name|Session
name|session
parameter_list|,
name|Option
modifier|...
name|options
parameter_list|)
block|{
if|if
condition|(
name|status
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|status
operator|.
name|isEffectiveNow
argument_list|()
condition|)
return|return
name|status
operator|.
name|hasOption
argument_list|(
name|options
argument_list|)
return|;
name|StudentSectioningStatus
name|fallback
init|=
name|status
operator|.
name|getFallBackStatus
argument_list|()
decl_stmt|;
name|int
name|depth
init|=
literal|10
decl_stmt|;
while|while
condition|(
name|fallback
operator|!=
literal|null
operator|&&
name|depth
operator|--
operator|>
literal|0
condition|)
block|{
if|if
condition|(
name|fallback
operator|.
name|isEffectiveNow
argument_list|()
condition|)
return|return
name|fallback
operator|.
name|hasOption
argument_list|(
name|options
argument_list|)
return|;
else|else
name|fallback
operator|=
name|fallback
operator|.
name|getFallBackStatus
argument_list|()
expr_stmt|;
block|}
block|}
name|StudentSectioningStatus
name|defaultStatus
init|=
operator|(
name|session
operator|==
literal|null
condition|?
literal|null
else|:
name|session
operator|.
name|getDefaultSectioningStatus
argument_list|()
operator|)
decl_stmt|;
return|return
operator|(
name|defaultStatus
operator|==
literal|null
condition|?
literal|true
else|:
name|defaultStatus
operator|.
name|hasOption
argument_list|(
name|options
argument_list|)
operator|)
return|;
block|}
specifier|public
name|String
name|getEffectivePeriod
parameter_list|()
block|{
name|String
name|start
init|=
literal|null
decl_stmt|,
name|stop
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|getEffectiveStartDate
argument_list|()
operator|!=
literal|null
operator|||
name|getEffectiveStartPeriod
argument_list|()
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|getEffectiveStartDate
argument_list|()
operator|==
literal|null
condition|)
name|start
operator|=
name|Constants
operator|.
name|slot2str
argument_list|(
name|getEffectiveStartPeriod
argument_list|()
argument_list|)
expr_stmt|;
if|else if
condition|(
name|getEffectiveStartPeriod
argument_list|()
operator|==
literal|null
condition|)
name|start
operator|=
name|Formats
operator|.
name|getDateFormat
argument_list|(
name|Formats
operator|.
name|Pattern
operator|.
name|DATE_EVENT
argument_list|)
operator|.
name|format
argument_list|(
name|getEffectiveStartDate
argument_list|()
argument_list|)
expr_stmt|;
else|else
name|start
operator|=
name|Formats
operator|.
name|getDateFormat
argument_list|(
name|Formats
operator|.
name|Pattern
operator|.
name|DATE_EVENT
argument_list|)
operator|.
name|format
argument_list|(
name|getEffectiveStartDate
argument_list|()
argument_list|)
operator|+
literal|" "
operator|+
name|Constants
operator|.
name|slot2str
argument_list|(
name|getEffectiveStartPeriod
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|getEffectiveStopDate
argument_list|()
operator|!=
literal|null
operator|||
name|getEffectiveStopPeriod
argument_list|()
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|getEffectiveStopDate
argument_list|()
operator|==
literal|null
condition|)
name|stop
operator|=
name|Constants
operator|.
name|slot2str
argument_list|(
name|getEffectiveStopPeriod
argument_list|()
argument_list|)
expr_stmt|;
if|else if
condition|(
name|getEffectiveStopPeriod
argument_list|()
operator|==
literal|null
condition|)
name|stop
operator|=
name|Formats
operator|.
name|getDateFormat
argument_list|(
name|Formats
operator|.
name|Pattern
operator|.
name|DATE_EVENT
argument_list|)
operator|.
name|format
argument_list|(
name|getEffectiveStopDate
argument_list|()
argument_list|)
expr_stmt|;
else|else
name|stop
operator|=
name|Formats
operator|.
name|getDateFormat
argument_list|(
name|Formats
operator|.
name|Pattern
operator|.
name|DATE_EVENT
argument_list|)
operator|.
name|format
argument_list|(
name|getEffectiveStopDate
argument_list|()
argument_list|)
operator|+
literal|" "
operator|+
name|Constants
operator|.
name|slot2str
argument_list|(
name|getEffectiveStopPeriod
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|start
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|stop
operator|!=
literal|null
condition|)
block|{
return|return
name|MSG
operator|.
name|messageEffectivePeriodBetween
argument_list|(
name|start
argument_list|,
name|stop
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|MSG
operator|.
name|messageEffectivePeriodAfter
argument_list|(
name|start
argument_list|)
return|;
block|}
block|}
if|else if
condition|(
name|stop
operator|!=
literal|null
condition|)
block|{
return|return
name|MSG
operator|.
name|messageEffectivePeriodBefore
argument_list|(
name|stop
argument_list|)
return|;
block|}
else|else
block|{
return|return
literal|null
return|;
block|}
block|}
specifier|public
specifier|static
name|List
argument_list|<
name|StudentSectioningStatus
argument_list|>
name|findAll
parameter_list|(
name|Long
name|sessionId
parameter_list|)
block|{
return|return
name|findAll
argument_list|(
literal|null
argument_list|,
name|sessionId
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|List
argument_list|<
name|StudentSectioningStatus
argument_list|>
name|findAll
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|Long
name|sessionId
parameter_list|)
block|{
if|if
condition|(
name|sessionId
operator|==
literal|null
condition|)
return|return
operator|(
name|List
argument_list|<
name|StudentSectioningStatus
argument_list|>
operator|)
operator|(
name|hibSession
operator|==
literal|null
condition|?
name|StudentSectioningStatusDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
else|:
name|hibSession
operator|)
operator|.
name|createQuery
argument_list|(
literal|"from StudentSectioningStatus where session is null order by label"
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
return|;
else|else
return|return
operator|(
name|List
argument_list|<
name|StudentSectioningStatus
argument_list|>
operator|)
operator|(
name|hibSession
operator|==
literal|null
condition|?
name|StudentSectioningStatusDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
else|:
name|hibSession
operator|)
operator|.
name|createQuery
argument_list|(
literal|"from StudentSectioningStatus where session is null or session = :sessionId order by label"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
return|;
block|}
specifier|public
specifier|static
name|StudentSectioningStatus
name|getPresentStatus
parameter_list|(
name|StudentSectioningStatus
name|status
parameter_list|)
block|{
name|int
name|depth
init|=
literal|10
decl_stmt|;
while|while
condition|(
name|status
operator|!=
literal|null
operator|&&
name|status
operator|.
name|isPast
argument_list|()
operator|&&
name|status
operator|.
name|getFallBackStatus
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|status
operator|=
name|status
operator|.
name|getFallBackStatus
argument_list|()
expr_stmt|;
if|if
condition|(
name|depth
operator|--
operator|==
literal|0
condition|)
return|return
literal|null
return|;
block|}
return|return
name|status
return|;
block|}
specifier|public
specifier|static
name|StudentSectioningStatus
name|getPresentStatus
parameter_list|(
name|String
name|reference
parameter_list|,
name|Long
name|sessionId
parameter_list|,
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|)
block|{
return|return
name|getPresentStatus
argument_list|(
name|getStatus
argument_list|(
name|reference
argument_list|,
name|sessionId
argument_list|,
name|hibSession
argument_list|)
argument_list|)
return|;
block|}
block|}
end_class

end_unit

