begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 (University Timetabling Application)  * Copyright (C) 2011, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|test
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
name|BitSet
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
name|Properties
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|log4j
operator|.
name|Logger
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|log4j
operator|.
name|PropertyConfigurator
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
name|hibernate
operator|.
name|util
operator|.
name|HibernateUtil
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
name|ApplicationProperties
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
name|_RootDAO
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
name|net
operator|.
name|sf
operator|.
name|cpsolver
operator|.
name|coursett
operator|.
name|TimetableXMLLoader
operator|.
name|DatePattern
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
name|coursett
operator|.
name|model
operator|.
name|Lecture
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
name|coursett
operator|.
name|model
operator|.
name|Placement
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
name|coursett
operator|.
name|model
operator|.
name|TimeLocation
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
name|extension
operator|.
name|Extension
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
name|solver
operator|.
name|Solver
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

begin_class
specifier|public
class|class
name|MuniPdFKSCZVDatePatterns
extends|extends
name|Extension
argument_list|<
name|Lecture
argument_list|,
name|Placement
argument_list|>
block|{
specifier|protected
specifier|static
name|Logger
name|sLog
init|=
name|Logger
operator|.
name|getLogger
argument_list|(
name|MuniPdFKSCZVDatePatterns
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|Hashtable
argument_list|<
name|Integer
argument_list|,
name|List
argument_list|<
name|DatePattern
argument_list|>
argument_list|>
name|iDatePatternsExt
init|=
operator|new
name|Hashtable
argument_list|<
name|Integer
argument_list|,
name|List
argument_list|<
name|DatePattern
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|Hashtable
argument_list|<
name|Integer
argument_list|,
name|List
argument_list|<
name|DatePattern
argument_list|>
argument_list|>
name|iDatePatternsAll
init|=
operator|new
name|Hashtable
argument_list|<
name|Integer
argument_list|,
name|List
argument_list|<
name|DatePattern
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
specifier|public
name|MuniPdFKSCZVDatePatterns
parameter_list|(
name|Solver
argument_list|<
name|Lecture
argument_list|,
name|Placement
argument_list|>
name|solver
parameter_list|,
name|DataProperties
name|properties
parameter_list|)
block|{
name|super
argument_list|(
name|solver
argument_list|,
name|properties
argument_list|)
expr_stmt|;
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
name|createNewSession
argument_list|()
decl_stmt|;
try|try
block|{
for|for
control|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|DatePattern
name|dp
range|:
operator|(
name|List
argument_list|<
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|DatePattern
argument_list|>
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"from DatePattern dp where dp.session.uniqueId = :sessionId and dp.type = :type and dp.name like :name"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|properties
operator|.
name|getPropertyLong
argument_list|(
literal|"General.SessionId"
argument_list|,
operator|-
literal|1
argument_list|)
argument_list|)
operator|.
name|setInteger
argument_list|(
literal|"type"
argument_list|,
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|DatePattern
operator|.
name|sTypeExtended
argument_list|)
operator|.
name|setString
argument_list|(
literal|"name"
argument_list|,
literal|"T%den %"
argument_list|)
operator|.
name|list
argument_list|()
control|)
block|{
name|BitSet
name|weekCode
init|=
name|dp
operator|.
name|getPatternBitSet
argument_list|()
decl_stmt|;
name|int
name|nrWeeks
init|=
name|weekCode
operator|.
name|cardinality
argument_list|()
operator|/
literal|7
decl_stmt|;
name|List
argument_list|<
name|DatePattern
argument_list|>
name|patterns
init|=
name|iDatePatternsExt
operator|.
name|get
argument_list|(
name|nrWeeks
argument_list|)
decl_stmt|;
if|if
condition|(
name|patterns
operator|==
literal|null
condition|)
block|{
name|patterns
operator|=
operator|new
name|ArrayList
argument_list|<
name|DatePattern
argument_list|>
argument_list|()
expr_stmt|;
name|iDatePatternsExt
operator|.
name|put
argument_list|(
name|nrWeeks
argument_list|,
name|patterns
argument_list|)
expr_stmt|;
block|}
name|patterns
operator|.
name|add
argument_list|(
operator|new
name|DatePattern
argument_list|(
name|dp
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|dp
operator|.
name|getName
argument_list|()
argument_list|,
name|weekCode
argument_list|)
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|DatePattern
name|dp
range|:
operator|(
name|List
argument_list|<
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|DatePattern
argument_list|>
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"from DatePattern dp where dp.session.uniqueId = :sessionId and dp.name like :name"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|properties
operator|.
name|getPropertyLong
argument_list|(
literal|"General.SessionId"
argument_list|,
operator|-
literal|1
argument_list|)
argument_list|)
operator|.
name|setString
argument_list|(
literal|"name"
argument_list|,
literal|"T%den %"
argument_list|)
operator|.
name|list
argument_list|()
control|)
block|{
name|BitSet
name|weekCode
init|=
name|dp
operator|.
name|getPatternBitSet
argument_list|()
decl_stmt|;
name|int
name|nrWeeks
init|=
name|weekCode
operator|.
name|cardinality
argument_list|()
operator|/
literal|7
decl_stmt|;
name|List
argument_list|<
name|DatePattern
argument_list|>
name|patterns
init|=
name|iDatePatternsAll
operator|.
name|get
argument_list|(
name|nrWeeks
argument_list|)
decl_stmt|;
if|if
condition|(
name|patterns
operator|==
literal|null
condition|)
block|{
name|patterns
operator|=
operator|new
name|ArrayList
argument_list|<
name|DatePattern
argument_list|>
argument_list|()
expr_stmt|;
name|iDatePatternsAll
operator|.
name|put
argument_list|(
name|nrWeeks
argument_list|,
name|patterns
argument_list|)
expr_stmt|;
block|}
name|patterns
operator|.
name|add
argument_list|(
operator|new
name|DatePattern
argument_list|(
name|dp
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|dp
operator|.
name|getName
argument_list|()
argument_list|,
name|weekCode
argument_list|)
argument_list|)
expr_stmt|;
block|}
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
annotation|@
name|Override
specifier|public
name|void
name|variableAdded
parameter_list|(
name|Lecture
name|lecture
parameter_list|)
block|{
if|if
condition|(
name|lecture
operator|.
name|timeLocations
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
return|return;
name|List
argument_list|<
name|TimeLocation
argument_list|>
name|times
init|=
operator|new
name|ArrayList
argument_list|<
name|TimeLocation
argument_list|>
argument_list|(
name|lecture
operator|.
name|timeLocations
argument_list|()
argument_list|)
decl_stmt|;
comment|// SP courses take all TÃ½den % date patterns (other take just the extended ones)
comment|// boolean sp = lecture.getName().startsWith("SP ");
comment|// SP courses have Thursday
name|boolean
name|sp
init|=
literal|false
decl_stmt|;
for|for
control|(
name|TimeLocation
name|t
range|:
name|times
control|)
if|if
condition|(
operator|(
name|t
operator|.
name|getDayCode
argument_list|()
operator|&
name|Constants
operator|.
name|DAY_CODES
index|[
name|Constants
operator|.
name|DAY_THU
index|]
operator|)
operator|!=
literal|0
condition|)
block|{
name|sp
operator|=
literal|true
expr_stmt|;
break|break;
block|}
if|if
condition|(
name|times
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|getDatePatternName
argument_list|()
operator|.
name|matches
argument_list|(
literal|"[1-6]x"
argument_list|)
condition|)
block|{
name|int
name|n
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|times
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|getDatePatternName
argument_list|()
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|1
argument_list|)
argument_list|)
decl_stmt|;
name|lecture
operator|.
name|timeLocations
argument_list|()
operator|.
name|clear
argument_list|()
expr_stmt|;
for|for
control|(
name|TimeLocation
name|t
range|:
name|times
control|)
block|{
name|List
argument_list|<
name|DatePattern
argument_list|>
name|datePatterns
init|=
operator|(
name|sp
condition|?
name|iDatePatternsAll
else|:
name|iDatePatternsExt
operator|)
operator|.
name|get
argument_list|(
name|n
argument_list|)
decl_stmt|;
for|for
control|(
name|DatePattern
name|dp
range|:
name|datePatterns
control|)
block|{
comment|// Only TÃ½den 0 allows for Thursday
if|if
condition|(
operator|!
name|dp
operator|.
name|getName
argument_list|()
operator|.
name|endsWith
argument_list|(
literal|"den 0"
argument_list|)
operator|&&
operator|(
name|t
operator|.
name|getDayCode
argument_list|()
operator|&
name|Constants
operator|.
name|DAY_CODES
index|[
name|Constants
operator|.
name|DAY_THU
index|]
operator|)
operator|!=
literal|0
condition|)
continue|continue;
comment|// Clone time location with the new date pattern
name|TimeLocation
name|time
init|=
operator|new
name|TimeLocation
argument_list|(
name|t
operator|.
name|getDayCode
argument_list|()
argument_list|,
name|t
operator|.
name|getStartSlot
argument_list|()
argument_list|,
name|t
operator|.
name|getLength
argument_list|()
argument_list|,
name|t
operator|.
name|getPreference
argument_list|()
argument_list|,
name|t
operator|.
name|getNormalizedPreference
argument_list|()
argument_list|,
name|dp
operator|.
name|getId
argument_list|()
argument_list|,
name|dp
operator|.
name|getName
argument_list|()
argument_list|,
name|dp
operator|.
name|getPattern
argument_list|()
argument_list|,
name|t
operator|.
name|getBreakTime
argument_list|()
argument_list|)
decl_stmt|;
name|time
operator|.
name|setTimePatternId
argument_list|(
name|t
operator|.
name|getTimePatternId
argument_list|()
argument_list|)
expr_stmt|;
name|lecture
operator|.
name|timeLocations
argument_list|()
operator|.
name|add
argument_list|(
name|time
argument_list|)
expr_stmt|;
block|}
block|}
block|}
name|lecture
operator|.
name|clearValueCache
argument_list|()
expr_stmt|;
block|}
specifier|static
name|String
index|[]
name|sCombinations
init|=
operator|new
name|String
index|[]
block|{
comment|// 1x
literal|"1,0,0,0,0,0,0,0,0,0,0,0,0"
block|,
literal|"0,1,0,0,0,0,0,0,0,0,0,0,0"
block|,
literal|"0,0,1,0,0,0,0,0,0,0,0,0,0"
block|,
literal|"0,0,0,1,0,0,0,0,0,0,0,0,0"
block|,
literal|"0,0,0,0,1,0,0,0,0,0,0,0,0"
block|,
literal|"0,0,0,0,0,1,0,0,0,0,0,0,0"
block|,
literal|"0,0,0,0,0,0,1,0,0,0,0,0,0"
block|,
literal|"0,0,0,0,0,0,0,1,0,0,0,0,0"
block|,
literal|"0,0,0,0,0,0,0,0,1,0,0,0,0"
block|,
literal|"0,0,0,0,0,0,0,0,0,1,0,0,0"
block|,
literal|"0,0,0,0,0,0,0,0,0,0,1,0,0"
block|,
literal|"0,0,0,0,0,0,0,0,0,0,0,1,0"
block|,
literal|"0,0,0,0,0,0,0,0,0,0,0,0,1"
block|,
comment|// 2x
literal|"1,1,0,0,0,0,0,0,0,0,0,0,0"
block|,
literal|"0,1,1,0,0,0,0,0,0,0,0,0,0"
block|,
literal|"0,0,1,1,0,0,0,0,0,0,0,0,0"
block|,
literal|"0,0,0,1,1,0,0,0,0,0,0,0,0"
block|,
literal|"0,0,0,0,1,1,0,0,0,0,0,0,0"
block|,
literal|"0,0,0,0,0,1,1,0,0,0,0,0,0"
block|,
literal|"0,0,0,0,0,0,1,1,0,0,0,0,0"
block|,
literal|"0,0,0,0,0,0,0,1,1,0,0,0,0"
block|,
literal|"0,0,0,0,0,0,0,0,1,1,0,0,0"
block|,
literal|"0,0,0,0,0,0,0,0,0,1,1,0,0"
block|,
literal|"0,0,0,0,0,0,0,0,0,0,1,1,0"
block|,
literal|"0,0,0,0,0,0,0,0,0,0,0,1,1"
block|,
literal|"1,0,0,0,0,0,1,0,0,0,0,0,0"
block|,
literal|"0,1,0,0,0,0,0,1,0,0,0,0,0"
block|,
literal|"0,0,1,0,0,0,0,0,1,0,0,0,0"
block|,
literal|"0,0,0,1,0,0,0,0,0,1,0,0,0"
block|,
literal|"0,0,0,0,1,0,0,0,0,0,1,0,0"
block|,
literal|"0,0,0,0,0,1,0,0,0,0,0,1,0"
block|,
literal|"0,0,0,0,0,0,1,0,0,0,0,0,1"
block|,
comment|// 3x
literal|"1,1,1,0,0,0,0,0,0,0,0,0,0"
block|,
literal|"0,1,1,1,0,0,0,0,0,0,0,0,0"
block|,
literal|"0,0,1,1,1,0,0,0,0,0,0,0,0"
block|,
literal|"0,0,0,1,1,1,0,0,0,0,0,0,0"
block|,
literal|"0,0,0,0,1,1,1,0,0,0,0,0,0"
block|,
literal|"0,0,0,0,0,1,1,1,0,0,0,0,0"
block|,
literal|"0,0,0,0,0,0,1,1,1,0,0,0,0"
block|,
literal|"0,0,0,0,0,0,0,1,1,1,0,0,0"
block|,
literal|"0,0,0,0,0,0,0,0,1,1,1,0,0"
block|,
literal|"0,0,0,0,0,0,0,0,0,1,1,1,0"
block|,
literal|"0,0,0,0,0,0,0,0,0,0,1,1,1"
block|,
literal|"1,0,0,0,1,0,0,0,1,0,0,0,0"
block|,
literal|"0,1,0,0,0,1,0,0,0,1,0,0,0"
block|,
literal|"0,0,1,0,0,0,1,0,0,0,1,0,0"
block|,
literal|"0,0,0,1,0,0,0,1,0,0,0,1,0"
block|,
literal|"0,0,0,0,1,0,0,0,1,0,0,0,1"
block|,
comment|// 4x
literal|"1,1,1,1,0,0,0,0,0,0,0,0,0"
block|,
literal|"0,1,1,1,1,0,0,0,0,0,0,0,0"
block|,
literal|"0,0,1,1,1,1,0,0,0,0,0,0,0"
block|,
literal|"0,0,0,1,1,1,1,0,0,0,0,0,0"
block|,
literal|"0,0,0,0,1,1,1,1,0,0,0,0,0"
block|,
literal|"0,0,0,0,0,1,1,1,1,0,0,0,0"
block|,
literal|"0,0,0,0,0,0,1,1,1,1,0,0,0"
block|,
literal|"0,0,0,0,0,0,0,1,1,1,1,0,0"
block|,
literal|"0,0,0,0,0,0,0,0,1,1,1,1,0"
block|,
literal|"0,0,0,0,0,0,0,0,0,1,1,1,1"
block|,
literal|"1,0,0,1,0,0,1,0,0,1,0,0,0"
block|,
literal|"0,1,0,0,1,0,0,1,0,0,1,0,0"
block|,
literal|"0,0,1,0,0,1,0,0,1,0,0,1,0"
block|,
literal|"0,0,0,1,0,0,1,0,0,1,0,0,1"
block|,
comment|// 5x
literal|"1,1,1,1,1,0,0,0,0,0,0,0,0"
block|,
literal|"0,1,1,1,1,1,0,0,0,0,0,0,0"
block|,
literal|"0,0,1,1,1,1,1,0,0,0,0,0,0"
block|,
literal|"0,0,0,1,1,1,1,1,0,0,0,0,0"
block|,
literal|"0,0,0,0,1,1,1,1,1,0,0,0,0"
block|,
literal|"0,0,0,0,0,1,1,1,1,1,0,0,0"
block|,
literal|"0,0,0,0,0,0,1,1,1,1,1,0,0"
block|,
literal|"0,0,0,0,0,0,0,1,1,1,1,1,0"
block|,
literal|"0,0,0,0,0,0,0,0,1,1,1,1,1"
block|,
literal|"1,0,1,0,1,0,1,0,1,0,0,0,0"
block|,
literal|"0,1,0,1,0,1,0,1,0,1,0,0,0"
block|,
literal|"0,0,1,0,1,0,1,0,1,0,1,0,0"
block|,
literal|"0,0,0,1,0,1,0,1,0,1,0,1,0"
block|,
literal|"0,0,0,0,1,0,1,0,1,0,1,0,1"
block|,
comment|// 6x
literal|"1,1,1,1,1,1,0,0,0,0,0,0,0"
block|,
literal|"0,1,1,1,1,1,1,0,0,0,0,0,0"
block|,
literal|"0,0,1,1,1,1,1,1,0,0,0,0,0"
block|,
literal|"0,0,0,1,1,1,1,1,1,0,0,0,0"
block|,
literal|"0,0,0,0,1,1,1,1,1,1,0,0,0"
block|,
literal|"0,0,0,0,0,1,1,1,1,1,1,0,0"
block|,
literal|"0,0,0,0,0,0,1,1,1,1,1,1,0"
block|,
literal|"0,0,0,0,0,0,0,1,1,1,1,1,1"
block|,
literal|"1,0,1,0,1,0,1,0,1,0,1,0,0"
block|,
literal|"0,1,0,1,0,1,0,1,0,1,0,1,0"
block|,
literal|"0,0,1,0,1,0,1,0,1,0,1,0,1"
block|,     }
decl_stmt|;
specifier|public
specifier|static
name|void
name|main
parameter_list|(
name|String
index|[]
name|args
parameter_list|)
block|{
try|try
block|{
name|Properties
name|props
init|=
operator|new
name|Properties
argument_list|()
decl_stmt|;
name|props
operator|.
name|setProperty
argument_list|(
literal|"log4j.rootLogger"
argument_list|,
literal|"DEBUG, A1"
argument_list|)
expr_stmt|;
name|props
operator|.
name|setProperty
argument_list|(
literal|"log4j.appender.A1"
argument_list|,
literal|"org.apache.log4j.ConsoleAppender"
argument_list|)
expr_stmt|;
name|props
operator|.
name|setProperty
argument_list|(
literal|"log4j.appender.A1.layout"
argument_list|,
literal|"org.apache.log4j.PatternLayout"
argument_list|)
expr_stmt|;
name|props
operator|.
name|setProperty
argument_list|(
literal|"log4j.appender.A1.layout.ConversionPattern"
argument_list|,
literal|"%-5p %c{2}: %m%n"
argument_list|)
expr_stmt|;
name|props
operator|.
name|setProperty
argument_list|(
literal|"log4j.logger.org.hibernate"
argument_list|,
literal|"INFO"
argument_list|)
expr_stmt|;
name|props
operator|.
name|setProperty
argument_list|(
literal|"log4j.logger.org.hibernate.cfg"
argument_list|,
literal|"WARN"
argument_list|)
expr_stmt|;
name|props
operator|.
name|setProperty
argument_list|(
literal|"log4j.logger.org.hibernate.cache.EhCacheProvider"
argument_list|,
literal|"ERROR"
argument_list|)
expr_stmt|;
name|props
operator|.
name|setProperty
argument_list|(
literal|"log4j.logger.org.unitime.commons.hibernate"
argument_list|,
literal|"INFO"
argument_list|)
expr_stmt|;
name|props
operator|.
name|setProperty
argument_list|(
literal|"log4j.logger.net"
argument_list|,
literal|"INFO"
argument_list|)
expr_stmt|;
name|PropertyConfigurator
operator|.
name|configure
argument_list|(
name|props
argument_list|)
expr_stmt|;
name|HibernateUtil
operator|.
name|configureHibernate
argument_list|(
name|ApplicationProperties
operator|.
name|getProperties
argument_list|()
argument_list|)
expr_stmt|;
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
operator|new
name|_RootDAO
argument_list|()
operator|.
name|getSession
argument_list|()
decl_stmt|;
name|Session
name|session
init|=
name|Session
operator|.
name|getSessionUsingInitiativeYearTerm
argument_list|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"initiative"
argument_list|,
literal|"PdF"
argument_list|)
argument_list|,
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"year"
argument_list|,
literal|"2011"
argument_list|)
argument_list|,
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"term"
argument_list|,
literal|"Podzim"
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|session
operator|==
literal|null
condition|)
block|{
name|sLog
operator|.
name|error
argument_list|(
literal|"Academic session not found, use properties initiative, year, and term to set academic session."
argument_list|)
expr_stmt|;
name|System
operator|.
name|exit
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|sLog
operator|.
name|info
argument_list|(
literal|"Session: "
operator|+
name|session
argument_list|)
expr_stmt|;
block|}
name|List
argument_list|<
name|BitSet
argument_list|>
name|weeks
init|=
operator|new
name|ArrayList
argument_list|<
name|BitSet
argument_list|>
argument_list|()
decl_stmt|;
name|BitSet
name|fullTerm
init|=
name|session
operator|.
name|getDefaultDatePattern
argument_list|()
operator|.
name|getPatternBitSet
argument_list|()
decl_stmt|;
name|int
name|cnt
init|=
literal|0
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
name|fullTerm
operator|.
name|length
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|fullTerm
operator|.
name|get
argument_list|(
name|i
argument_list|)
condition|)
block|{
name|int
name|w
init|=
operator|(
name|cnt
operator|++
operator|)
operator|/
literal|7
decl_stmt|;
if|if
condition|(
name|weeks
operator|.
name|size
argument_list|()
operator|==
name|w
condition|)
block|{
name|weeks
operator|.
name|add
argument_list|(
operator|new
name|BitSet
argument_list|(
name|fullTerm
operator|.
name|length
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|weeks
operator|.
name|get
argument_list|(
name|w
argument_list|)
operator|.
name|set
argument_list|(
name|i
argument_list|)
expr_stmt|;
block|}
block|}
for|for
control|(
name|String
name|c
range|:
name|sCombinations
control|)
block|{
name|BitSet
name|weekCode
init|=
operator|new
name|BitSet
argument_list|(
name|weeks
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|length
argument_list|()
argument_list|)
decl_stmt|;
name|String
name|dp
init|=
literal|""
decl_stmt|;
name|long
name|id
init|=
literal|0
decl_stmt|;
name|int
name|f
init|=
operator|-
literal|1
decl_stmt|,
name|i
init|=
literal|0
decl_stmt|;
empty_stmt|;
for|for
control|(
name|String
name|x
range|:
name|c
operator|.
name|split
argument_list|(
literal|","
argument_list|)
control|)
block|{
if|if
condition|(
name|x
operator|.
name|equals
argument_list|(
literal|"1"
argument_list|)
condition|)
block|{
if|if
condition|(
name|f
operator|<
literal|0
condition|)
name|f
operator|=
literal|1
operator|+
name|i
expr_stmt|;
name|weekCode
operator|.
name|or
argument_list|(
name|weeks
operator|.
name|get
argument_list|(
name|i
argument_list|)
argument_list|)
expr_stmt|;
name|id
operator|+=
operator|(
literal|1
operator|<<
name|i
operator|)
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|f
operator|>
literal|0
condition|)
block|{
if|if
condition|(
operator|!
name|dp
operator|.
name|isEmpty
argument_list|()
condition|)
name|dp
operator|+=
literal|","
expr_stmt|;
if|if
condition|(
name|f
operator|==
name|i
condition|)
name|dp
operator|+=
literal|""
operator|+
name|f
expr_stmt|;
else|else
name|dp
operator|+=
name|f
operator|+
literal|"-"
operator|+
name|i
expr_stmt|;
name|f
operator|=
operator|-
literal|1
expr_stmt|;
block|}
block|}
name|i
operator|++
expr_stmt|;
block|}
if|if
condition|(
name|f
operator|>
literal|0
condition|)
block|{
if|if
condition|(
operator|!
name|dp
operator|.
name|isEmpty
argument_list|()
condition|)
name|dp
operator|+=
literal|","
expr_stmt|;
if|if
condition|(
name|f
operator|==
name|weeks
operator|.
name|size
argument_list|()
condition|)
name|dp
operator|+=
literal|""
operator|+
name|f
expr_stmt|;
else|else
name|dp
operator|+=
name|f
operator|+
literal|"-"
operator|+
name|weeks
operator|.
name|size
argument_list|()
expr_stmt|;
block|}
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|DatePattern
name|p
init|=
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|DatePattern
operator|.
name|findByName
argument_list|(
name|session
argument_list|,
literal|"TÃ½den "
operator|+
name|dp
argument_list|)
decl_stmt|;
if|if
condition|(
name|p
operator|==
literal|null
condition|)
block|{
name|p
operator|=
operator|new
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|DatePattern
argument_list|()
expr_stmt|;
name|p
operator|.
name|setSession
argument_list|(
name|session
argument_list|)
expr_stmt|;
name|p
operator|.
name|setName
argument_list|(
literal|"TÃ½den "
operator|+
name|dp
argument_list|)
expr_stmt|;
name|p
operator|.
name|setOffset
argument_list|(
name|fullTerm
operator|.
name|nextSetBit
argument_list|(
literal|0
argument_list|)
operator|-
name|weekCode
operator|.
name|nextSetBit
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|p
operator|.
name|setType
argument_list|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|DatePattern
operator|.
name|sTypeExtended
argument_list|)
expr_stmt|;
name|p
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|String
name|pattern
init|=
literal|""
decl_stmt|;
for|for
control|(
name|int
name|j
init|=
name|weekCode
operator|.
name|nextSetBit
argument_list|(
literal|0
argument_list|)
init|;
name|j
operator|<
name|weekCode
operator|.
name|length
argument_list|()
condition|;
name|j
operator|++
control|)
name|pattern
operator|+=
operator|(
name|weekCode
operator|.
name|get
argument_list|(
name|j
argument_list|)
condition|?
literal|"1"
else|:
literal|"0"
operator|)
expr_stmt|;
name|p
operator|.
name|setPattern
argument_list|(
name|pattern
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|p
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|String
name|pattern
init|=
literal|""
decl_stmt|;
for|for
control|(
name|int
name|j
init|=
name|weekCode
operator|.
name|nextSetBit
argument_list|(
literal|0
argument_list|)
init|;
name|j
operator|<
name|weekCode
operator|.
name|length
argument_list|()
condition|;
name|j
operator|++
control|)
name|pattern
operator|+=
operator|(
name|weekCode
operator|.
name|get
argument_list|(
name|j
argument_list|)
condition|?
literal|"1"
else|:
literal|"0"
operator|)
expr_stmt|;
name|p
operator|.
name|setOffset
argument_list|(
name|fullTerm
operator|.
name|nextSetBit
argument_list|(
literal|0
argument_list|)
operator|-
name|weekCode
operator|.
name|nextSetBit
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|p
operator|.
name|setPattern
argument_list|(
name|pattern
argument_list|)
expr_stmt|;
name|p
operator|.
name|setType
argument_list|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|DatePattern
operator|.
name|sTypeExtended
argument_list|)
expr_stmt|;
name|p
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|p
argument_list|)
expr_stmt|;
block|}
block|}
name|hibSession
operator|.
name|flush
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

