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
name|ReadWriteLock
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
name|java
operator|.
name|util
operator|.
name|concurrent
operator|.
name|locks
operator|.
name|ReentrantReadWriteLock
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
name|MultiReadWriteLock
block|{
specifier|protected
name|Lock
name|iLock
init|=
operator|new
name|ReentrantLock
argument_list|()
decl_stmt|;
specifier|protected
name|Condition
name|iLockNotAvailable
init|=
name|iLock
operator|.
name|newCondition
argument_list|()
decl_stmt|,
name|iGlobalLockNotAvailable
init|=
name|iLock
operator|.
name|newCondition
argument_list|()
decl_stmt|;
specifier|protected
name|Map
argument_list|<
name|Long
argument_list|,
name|ReadWriteLock
argument_list|>
name|iIndividualLocks
init|=
operator|new
name|HashMap
argument_list|<
name|Long
argument_list|,
name|ReadWriteLock
argument_list|>
argument_list|()
decl_stmt|;
specifier|protected
name|ReadWriteLock
name|iGlobalLock
init|=
operator|new
name|ReentrantReadWriteLock
argument_list|(
literal|true
argument_list|)
decl_stmt|;
specifier|protected
name|int
name|iGlobalLockRequests
init|=
literal|0
decl_stmt|;
specifier|public
name|Unlock
name|lock
parameter_list|(
name|boolean
name|write
parameter_list|,
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
name|write
argument_list|,
name|list
argument_list|)
return|;
block|}
specifier|public
name|Unlock
name|tryLock
parameter_list|(
name|boolean
name|write
parameter_list|,
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
name|tryLock
argument_list|(
name|write
argument_list|,
name|list
argument_list|)
return|;
block|}
specifier|public
name|Unlock
name|lock
parameter_list|(
name|boolean
name|write
parameter_list|,
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
argument_list|()
return|;
while|while
condition|(
literal|true
condition|)
block|{
name|Unlock
name|unlock
init|=
name|tryLock
argument_list|(
name|write
argument_list|,
name|ids
argument_list|)
decl_stmt|;
if|if
condition|(
name|unlock
operator|!=
literal|null
condition|)
return|return
name|unlock
return|;
name|iLockNotAvailable
operator|.
name|awaitUninterruptibly
argument_list|()
expr_stmt|;
block|}
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
name|tryLock
parameter_list|(
name|boolean
name|write
parameter_list|,
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
literal|null
return|;
name|List
argument_list|<
name|Lock
argument_list|>
name|acquiredLocks
init|=
operator|new
name|ArrayList
argument_list|<
name|Lock
argument_list|>
argument_list|()
decl_stmt|;
if|if
condition|(
name|iGlobalLock
operator|.
name|readLock
argument_list|()
operator|.
name|tryLock
argument_list|()
condition|)
block|{
name|acquiredLocks
operator|.
name|add
argument_list|(
name|iGlobalLock
operator|.
name|readLock
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
return|return
literal|null
return|;
block|}
for|for
control|(
name|Long
name|courseId
range|:
name|ids
control|)
block|{
name|ReadWriteLock
name|courseLock
init|=
name|iIndividualLocks
operator|.
name|get
argument_list|(
name|courseId
argument_list|)
decl_stmt|;
if|if
condition|(
name|courseLock
operator|==
literal|null
condition|)
block|{
name|courseLock
operator|=
operator|new
name|ReentrantReadWriteLock
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|iIndividualLocks
operator|.
name|put
argument_list|(
name|courseId
argument_list|,
name|courseLock
argument_list|)
expr_stmt|;
block|}
name|Lock
name|lock
init|=
operator|(
name|write
condition|?
name|courseLock
operator|.
name|writeLock
argument_list|()
else|:
name|courseLock
operator|.
name|readLock
argument_list|()
operator|)
decl_stmt|;
if|if
condition|(
name|lock
operator|.
name|tryLock
argument_list|()
condition|)
block|{
name|acquiredLocks
operator|.
name|add
argument_list|(
name|lock
argument_list|)
expr_stmt|;
block|}
else|else
block|{
for|for
control|(
name|Lock
name|undo
range|:
name|acquiredLocks
control|)
name|undo
operator|.
name|unlock
argument_list|()
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
return|return
operator|new
name|Unlock
argument_list|(
name|acquiredLocks
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
name|Unlock
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
while|while
condition|(
literal|true
condition|)
block|{
if|if
condition|(
name|iGlobalLock
operator|.
name|writeLock
argument_list|()
operator|.
name|tryLock
argument_list|()
condition|)
return|return
operator|new
name|Unlock
argument_list|(
name|iGlobalLock
operator|.
name|writeLock
argument_list|()
argument_list|)
return|;
else|else
block|{
name|iGlobalLockRequests
operator|++
expr_stmt|;
name|iGlobalLockNotAvailable
operator|.
name|awaitUninterruptibly
argument_list|()
expr_stmt|;
name|iGlobalLockRequests
operator|--
expr_stmt|;
block|}
block|}
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
name|empty
parameter_list|()
block|{
return|return
operator|new
name|Unlock
argument_list|()
return|;
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
name|List
argument_list|<
name|Lock
argument_list|>
name|iAcquiredLocks
decl_stmt|;
specifier|private
name|Unlock
parameter_list|(
name|List
argument_list|<
name|Lock
argument_list|>
name|acquiredLocks
parameter_list|)
block|{
name|iAcquiredLocks
operator|=
name|acquiredLocks
expr_stmt|;
block|}
specifier|private
name|Unlock
parameter_list|(
name|Lock
modifier|...
name|acquiredLocks
parameter_list|)
block|{
name|iAcquiredLocks
operator|=
operator|new
name|ArrayList
argument_list|<
name|Lock
argument_list|>
argument_list|()
expr_stmt|;
for|for
control|(
name|Lock
name|lock
range|:
name|acquiredLocks
control|)
name|iAcquiredLocks
operator|.
name|add
argument_list|(
name|lock
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|release
parameter_list|()
block|{
name|iLock
operator|.
name|lock
argument_list|()
expr_stmt|;
try|try
block|{
for|for
control|(
name|Lock
name|lock
range|:
name|iAcquiredLocks
control|)
name|lock
operator|.
name|unlock
argument_list|()
expr_stmt|;
if|if
condition|(
name|iGlobalLockRequests
operator|>
literal|0
condition|)
name|iGlobalLockNotAvailable
operator|.
name|signal
argument_list|()
expr_stmt|;
else|else
name|iLockNotAvailable
operator|.
name|signalAll
argument_list|()
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
block|}
specifier|public
name|void
name|remove
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
name|iIndividualLocks
operator|.
name|remove
argument_list|(
name|id
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
name|void
name|removeAll
parameter_list|()
block|{
name|iLock
operator|.
name|lock
argument_list|()
expr_stmt|;
try|try
block|{
name|iIndividualLocks
operator|.
name|clear
argument_list|()
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
name|MultiReadWriteLock
name|lock
init|=
operator|new
name|MultiReadWriteLock
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
for|for
control|(
name|int
name|x
init|=
literal|1
init|;
name|x
operator|<=
literal|10
condition|;
name|x
operator|++
control|)
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
name|boolean
name|write
init|=
operator|(
name|ToolBox
operator|.
name|random
argument_list|(
literal|10
argument_list|)
operator|==
literal|0
operator|)
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
literal|"("
operator|+
name|x
operator|+
literal|") "
operator|+
operator|(
name|write
condition|?
literal|"Write "
else|:
literal|"Read "
operator|)
operator|+
literal|"locking: ["
operator|+
name|s
operator|+
literal|"]"
argument_list|)
expr_stmt|;
name|Unlock
name|unlock
init|=
name|lock
operator|.
name|lock
argument_list|(
name|write
argument_list|,
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
literal|"("
operator|+
name|x
operator|+
literal|") "
operator|+
operator|(
name|write
condition|?
literal|"Write "
else|:
literal|"Read "
operator|)
operator|+
literal|"locked: ["
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
literal|"("
operator|+
name|x
operator|+
literal|") "
operator|+
operator|(
name|write
condition|?
literal|"Write "
else|:
literal|"Read "
operator|)
operator|+
literal|"unlocking: ["
operator|+
name|s
operator|+
literal|"]"
argument_list|)
expr_stmt|;
name|unlock
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
literal|"("
operator|+
name|x
operator|+
literal|") "
operator|+
operator|(
name|write
condition|?
literal|"Write "
else|:
literal|"Read "
operator|)
operator|+
literal|"unlocked: ["
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
for|for
control|(
name|int
name|x
init|=
literal|1
init|;
name|x
operator|<=
literal|10
condition|;
name|x
operator|++
control|)
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
literal|"("
operator|+
name|x
operator|+
literal|") "
operator|+
literal|"Locking all..."
argument_list|)
expr_stmt|;
name|Unlock
name|unlock
init|=
name|lock
operator|.
name|lockAll
argument_list|()
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
literal|"("
operator|+
name|x
operator|+
literal|") "
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
literal|"("
operator|+
name|x
operator|+
literal|") "
operator|+
literal|"Unlocking all."
argument_list|)
expr_stmt|;
name|unlock
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
literal|"("
operator|+
name|x
operator|+
literal|") "
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

