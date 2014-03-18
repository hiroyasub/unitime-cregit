begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 - 3.5 (University Timetabling Application)  * Copyright (C) 2011 - 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|HashMap
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
name|Map
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
name|java
operator|.
name|util
operator|.
name|concurrent
operator|.
name|locks
operator|.
name|Condition
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|concurrent
operator|.
name|locks
operator|.
name|Lock
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|concurrent
operator|.
name|locks
operator|.
name|ReentrantLock
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|Log
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|LogFactory
import|;
end_import

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|ifs
operator|.
name|util
operator|.
name|ToolBox
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|MultiLock
block|{
specifier|private
name|Log
name|iLog
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|MultiLock
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|Lock
name|iLock
init|=
operator|new
name|ReentrantLock
argument_list|()
decl_stmt|;
specifier|private
name|Condition
name|iAllLocked
init|=
literal|null
decl_stmt|;
specifier|private
name|Map
argument_list|<
name|Long
argument_list|,
name|Condition
argument_list|>
name|iIndividualLocks
init|=
operator|new
name|HashMap
argument_list|<
name|Long
argument_list|,
name|Condition
argument_list|>
argument_list|()
decl_stmt|;
specifier|public
name|MultiLock
parameter_list|()
block|{
name|iLog
operator|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|MultiLock
operator|.
name|class
operator|.
name|getName
argument_list|()
operator|+
literal|".lock"
argument_list|)
expr_stmt|;
block|}
specifier|public
name|MultiLock
parameter_list|(
name|AcademicSessionInfo
name|session
parameter_list|)
block|{
name|iLog
operator|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|MultiLock
operator|.
name|class
operator|.
name|getName
argument_list|()
operator|+
literal|".lock["
operator|+
name|session
operator|.
name|toCompactString
argument_list|()
operator|+
literal|"]"
argument_list|)
expr_stmt|;
block|}
specifier|private
name|Condition
name|hasLock
parameter_list|(
name|Collection
argument_list|<
name|Long
argument_list|>
name|ids
parameter_list|)
block|{
if|if
condition|(
name|iAllLocked
operator|!=
literal|null
condition|)
return|return
name|iAllLocked
return|;
for|for
control|(
name|Long
name|id
range|:
name|ids
control|)
block|{
name|Condition
name|c
init|=
name|iIndividualLocks
operator|.
name|get
argument_list|(
name|id
argument_list|)
decl_stmt|;
if|if
condition|(
name|c
operator|!=
literal|null
condition|)
return|return
name|c
return|;
block|}
return|return
literal|null
return|;
block|}
specifier|public
name|Unlock
name|lock
parameter_list|(
name|Long
modifier|...
name|ids
parameter_list|)
block|{
name|List
argument_list|<
name|Long
argument_list|>
name|list
init|=
operator|new
name|ArrayList
argument_list|<
name|Long
argument_list|>
argument_list|(
name|ids
operator|.
name|length
argument_list|)
decl_stmt|;
for|for
control|(
name|Long
name|id
range|:
name|ids
control|)
name|list
operator|.
name|add
argument_list|(
name|id
argument_list|)
expr_stmt|;
return|return
name|lock
argument_list|(
name|list
argument_list|)
return|;
block|}
specifier|public
name|UnlockAll
name|lockAll
parameter_list|()
block|{
name|iLock
operator|.
name|lock
argument_list|()
expr_stmt|;
try|try
block|{
name|iLog
operator|.
name|debug
argument_list|(
literal|"Locking all ..."
argument_list|)
expr_stmt|;
while|while
condition|(
name|iAllLocked
operator|!=
literal|null
condition|)
name|iAllLocked
operator|.
name|awaitUninterruptibly
argument_list|()
expr_stmt|;
name|iAllLocked
operator|=
name|iLock
operator|.
name|newCondition
argument_list|()
expr_stmt|;
while|while
condition|(
operator|!
name|iIndividualLocks
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|Condition
name|otherCondition
init|=
name|iIndividualLocks
operator|.
name|values
argument_list|()
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
decl_stmt|;
name|otherCondition
operator|.
name|awaitUninterruptibly
argument_list|()
expr_stmt|;
block|}
name|iLog
operator|.
name|debug
argument_list|(
literal|"Locked: all"
argument_list|)
expr_stmt|;
return|return
operator|new
name|UnlockAll
argument_list|()
return|;
block|}
finally|finally
block|{
name|iLock
operator|.
name|unlock
argument_list|()
expr_stmt|;
block|}
block|}
specifier|public
name|void
name|unlockAll
parameter_list|()
block|{
name|iLock
operator|.
name|lock
argument_list|()
expr_stmt|;
try|try
block|{
name|iLog
operator|.
name|debug
argument_list|(
literal|"Unlocking all ..."
argument_list|)
expr_stmt|;
name|Condition
name|allLocked
init|=
name|iAllLocked
decl_stmt|;
name|iAllLocked
operator|=
literal|null
expr_stmt|;
name|allLocked
operator|.
name|signalAll
argument_list|()
expr_stmt|;
name|iLog
operator|.
name|debug
argument_list|(
literal|"Unlocked: all"
argument_list|)
expr_stmt|;
block|}
finally|finally
block|{
name|iLock
operator|.
name|unlock
argument_list|()
expr_stmt|;
block|}
block|}
specifier|public
name|Unlock
name|lock
parameter_list|(
name|Collection
argument_list|<
name|Long
argument_list|>
name|ids
parameter_list|)
block|{
name|iLock
operator|.
name|lock
argument_list|()
expr_stmt|;
try|try
block|{
if|if
condition|(
name|ids
operator|==
literal|null
operator|||
name|ids
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
operator|new
name|Unlock
argument_list|(
name|ids
argument_list|)
return|;
name|iLog
operator|.
name|debug
argument_list|(
literal|"Locking "
operator|+
name|ids
operator|+
literal|" ..."
argument_list|)
expr_stmt|;
name|Condition
name|otherCondition
init|=
literal|null
decl_stmt|;
while|while
condition|(
operator|(
name|otherCondition
operator|=
name|hasLock
argument_list|(
name|ids
argument_list|)
operator|)
operator|!=
literal|null
condition|)
name|otherCondition
operator|.
name|awaitUninterruptibly
argument_list|()
expr_stmt|;
name|Condition
name|myCondition
init|=
name|iLock
operator|.
name|newCondition
argument_list|()
decl_stmt|;
for|for
control|(
name|Long
name|id
range|:
name|ids
control|)
name|iIndividualLocks
operator|.
name|put
argument_list|(
name|id
argument_list|,
name|myCondition
argument_list|)
expr_stmt|;
name|iLog
operator|.
name|debug
argument_list|(
literal|"Locked: "
operator|+
name|ids
argument_list|)
expr_stmt|;
return|return
operator|new
name|Unlock
argument_list|(
name|ids
argument_list|)
return|;
block|}
finally|finally
block|{
name|iLock
operator|.
name|unlock
argument_list|()
expr_stmt|;
block|}
block|}
specifier|private
name|void
name|unlock
parameter_list|(
name|Collection
argument_list|<
name|Long
argument_list|>
name|ids
parameter_list|)
block|{
name|iLock
operator|.
name|lock
argument_list|()
expr_stmt|;
try|try
block|{
if|if
condition|(
name|ids
operator|==
literal|null
operator|||
name|ids
operator|.
name|isEmpty
argument_list|()
condition|)
return|return;
name|iLog
operator|.
name|debug
argument_list|(
literal|"Unlocking "
operator|+
name|ids
operator|+
literal|" ..."
argument_list|)
expr_stmt|;
name|Condition
name|myCondition
init|=
literal|null
decl_stmt|;
for|for
control|(
name|Long
name|id
range|:
name|ids
control|)
name|myCondition
operator|=
name|iIndividualLocks
operator|.
name|remove
argument_list|(
name|id
argument_list|)
expr_stmt|;
if|if
condition|(
name|myCondition
operator|!=
literal|null
condition|)
name|myCondition
operator|.
name|signalAll
argument_list|()
expr_stmt|;
name|iLog
operator|.
name|debug
argument_list|(
literal|"Unlocked: "
operator|+
name|ids
argument_list|)
expr_stmt|;
block|}
finally|finally
block|{
name|iLock
operator|.
name|unlock
argument_list|()
expr_stmt|;
block|}
block|}
specifier|public
name|Set
argument_list|<
name|Long
argument_list|>
name|locked
parameter_list|()
block|{
name|iLock
operator|.
name|lock
argument_list|()
expr_stmt|;
try|try
block|{
return|return
operator|new
name|TreeSet
argument_list|<
name|Long
argument_list|>
argument_list|(
name|iIndividualLocks
operator|.
name|keySet
argument_list|()
argument_list|)
return|;
block|}
finally|finally
block|{
name|iLock
operator|.
name|unlock
argument_list|()
expr_stmt|;
block|}
block|}
specifier|public
name|boolean
name|isLocked
parameter_list|(
name|Long
name|id
parameter_list|)
block|{
name|iLock
operator|.
name|lock
argument_list|()
expr_stmt|;
try|try
block|{
return|return
name|iIndividualLocks
operator|.
name|containsKey
argument_list|(
name|id
argument_list|)
return|;
block|}
finally|finally
block|{
name|iLock
operator|.
name|unlock
argument_list|()
expr_stmt|;
block|}
block|}
specifier|public
class|class
name|Unlock
implements|implements
name|OnlineSectioningServer
operator|.
name|Lock
block|{
specifier|private
name|Collection
argument_list|<
name|Long
argument_list|>
name|iIds
decl_stmt|;
specifier|private
name|Unlock
parameter_list|(
name|Collection
argument_list|<
name|Long
argument_list|>
name|ids
parameter_list|)
block|{
name|iIds
operator|=
name|ids
expr_stmt|;
block|}
specifier|public
name|void
name|release
parameter_list|()
block|{
name|unlock
argument_list|(
name|iIds
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
class|class
name|UnlockAll
implements|implements
name|OnlineSectioningServer
operator|.
name|Lock
block|{
specifier|private
name|UnlockAll
parameter_list|()
block|{
block|}
specifier|public
name|void
name|release
parameter_list|()
block|{
name|unlockAll
argument_list|()
expr_stmt|;
block|}
block|}
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
specifier|final
name|MultiLock
name|lock
init|=
operator|new
name|MultiLock
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|1
init|;
name|i
operator|<=
literal|1000
condition|;
name|i
operator|++
control|)
block|{
name|Thread
name|t
init|=
operator|new
name|Thread
argument_list|(
operator|new
name|Runnable
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|run
parameter_list|()
block|{
try|try
block|{
while|while
condition|(
literal|true
condition|)
block|{
name|int
name|nrCourses
init|=
literal|2
operator|+
name|ToolBox
operator|.
name|random
argument_list|(
literal|9
argument_list|)
decl_stmt|;
name|Set
argument_list|<
name|Long
argument_list|>
name|courses
init|=
operator|new
name|HashSet
argument_list|<
name|Long
argument_list|>
argument_list|()
decl_stmt|;
name|String
name|s
init|=
literal|""
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
name|nrCourses
condition|;
name|i
operator|++
control|)
block|{
name|long
name|courseId
decl_stmt|;
do|do
block|{
name|courseId
operator|=
name|ToolBox
operator|.
name|random
argument_list|(
literal|10000
argument_list|)
expr_stmt|;
block|}
do|while
condition|(
operator|!
name|courses
operator|.
name|add
argument_list|(
name|courseId
argument_list|)
condition|)
do|;
name|s
operator|+=
operator|(
name|i
operator|>
literal|0
condition|?
literal|", "
else|:
literal|""
operator|)
operator|+
name|courseId
expr_stmt|;
block|}
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|Thread
operator|.
name|currentThread
argument_list|()
operator|.
name|getName
argument_list|()
operator|+
literal|"Locking: ["
operator|+
name|s
operator|+
literal|"]"
argument_list|)
expr_stmt|;
name|Unlock
name|l
init|=
name|lock
operator|.
name|lock
argument_list|(
name|courses
argument_list|)
decl_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|Thread
operator|.
name|currentThread
argument_list|()
operator|.
name|getName
argument_list|()
operator|+
literal|"Locked: ["
operator|+
name|s
operator|+
literal|"]"
argument_list|)
expr_stmt|;
try|try
block|{
name|Thread
operator|.
name|sleep
argument_list|(
name|ToolBox
operator|.
name|random
argument_list|(
literal|1000
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|InterruptedException
name|e
parameter_list|)
block|{
block|}
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|Thread
operator|.
name|currentThread
argument_list|()
operator|.
name|getName
argument_list|()
operator|+
literal|"Unlocking: ["
operator|+
name|s
operator|+
literal|"]"
argument_list|)
expr_stmt|;
name|l
operator|.
name|release
argument_list|()
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|Thread
operator|.
name|currentThread
argument_list|()
operator|.
name|getName
argument_list|()
operator|+
literal|"Unlocked: ["
operator|+
name|s
operator|+
literal|"]"
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
name|Thread
operator|.
name|currentThread
argument_list|()
operator|.
name|getName
argument_list|()
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
block|}
argument_list|)
decl_stmt|;
name|t
operator|.
name|setName
argument_list|(
literal|"[T"
operator|+
name|i
operator|+
literal|"]: "
argument_list|)
expr_stmt|;
name|t
operator|.
name|start
argument_list|()
expr_stmt|;
block|}
for|for
control|(
name|int
name|i
init|=
literal|1
init|;
name|i
operator|<=
literal|3
condition|;
name|i
operator|++
control|)
block|{
name|Thread
name|t
init|=
operator|new
name|Thread
argument_list|(
operator|new
name|Runnable
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|run
parameter_list|()
block|{
try|try
block|{
while|while
condition|(
literal|true
condition|)
block|{
try|try
block|{
name|Thread
operator|.
name|sleep
argument_list|(
name|ToolBox
operator|.
name|random
argument_list|(
literal|5000
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|InterruptedException
name|e
parameter_list|)
block|{
block|}
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|Thread
operator|.
name|currentThread
argument_list|()
operator|.
name|getName
argument_list|()
operator|+
literal|"Locking all..."
argument_list|)
expr_stmt|;
name|lock
operator|.
name|lockAll
argument_list|()
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|Thread
operator|.
name|currentThread
argument_list|()
operator|.
name|getName
argument_list|()
operator|+
literal|"All locked."
argument_list|)
expr_stmt|;
try|try
block|{
name|Thread
operator|.
name|sleep
argument_list|(
name|ToolBox
operator|.
name|random
argument_list|(
literal|1000
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|InterruptedException
name|e
parameter_list|)
block|{
block|}
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|Thread
operator|.
name|currentThread
argument_list|()
operator|.
name|getName
argument_list|()
operator|+
literal|"Unlocking all."
argument_list|)
expr_stmt|;
name|lock
operator|.
name|unlockAll
argument_list|()
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|Thread
operator|.
name|currentThread
argument_list|()
operator|.
name|getName
argument_list|()
operator|+
literal|"All unlocked."
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
name|Thread
operator|.
name|currentThread
argument_list|()
operator|.
name|getName
argument_list|()
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
block|}
argument_list|)
decl_stmt|;
name|t
operator|.
name|setName
argument_list|(
literal|"[A"
operator|+
name|i
operator|+
literal|"]: "
argument_list|)
expr_stmt|;
name|t
operator|.
name|start
argument_list|()
expr_stmt|;
block|}
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

