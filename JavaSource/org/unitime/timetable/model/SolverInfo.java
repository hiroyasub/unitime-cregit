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
name|Hashtable
import|;
end_import

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
name|Map
import|;
end_import

begin_import
import|import
name|org
operator|.
name|dom4j
operator|.
name|Document
import|;
end_import

begin_import
import|import
name|org
operator|.
name|dom4j
operator|.
name|DocumentHelper
import|;
end_import

begin_import
import|import
name|org
operator|.
name|dom4j
operator|.
name|Element
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
name|Debug
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
name|BaseSolverInfo
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
name|solver
operator|.
name|ui
operator|.
name|FileInfo
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
name|solver
operator|.
name|ui
operator|.
name|TimetableInfo
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
name|solver
operator|.
name|ui
operator|.
name|TimetableInfoFileProxy
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
name|solver
operator|.
name|ui
operator|.
name|TimetableInfoUtil
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|SolverInfo
extends|extends
name|BaseSolverInfo
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
comment|/*[CONSTRUCTOR MARKER BEGIN]*/
specifier|public
name|SolverInfo
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|SolverInfo
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Long
name|uniqueId
parameter_list|)
block|{
name|super
argument_list|(
name|uniqueId
argument_list|)
expr_stmt|;
block|}
comment|/*[CONSTRUCTOR MARKER END]*/
specifier|public
name|SolverInfo
parameter_list|(
name|Long
name|uniqueId
parameter_list|,
name|Document
name|value
parameter_list|)
block|{
name|super
argument_list|(
name|uniqueId
argument_list|)
expr_stmt|;
name|setValue
argument_list|(
name|value
argument_list|)
expr_stmt|;
block|}
specifier|public
name|TimetableInfo
name|getInfo
parameter_list|()
throws|throws
name|Exception
block|{
return|return
name|getInfo
argument_list|(
name|TimetableInfoUtil
operator|.
name|getInstance
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|TimetableInfo
name|getInfo
parameter_list|(
name|TimetableInfoFileProxy
name|proxy
parameter_list|)
throws|throws
name|Exception
block|{
name|TimetableInfo
name|info
init|=
name|getCached
argument_list|(
name|getUniqueId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|info
operator|!=
literal|null
condition|)
return|return
name|info
return|;
if|if
condition|(
name|getValue
argument_list|()
operator|==
literal|null
condition|)
return|return
literal|null
return|;
name|Element
name|root
init|=
name|getValue
argument_list|()
operator|.
name|getRootElement
argument_list|()
decl_stmt|;
name|Class
name|infoClass
init|=
literal|null
decl_stmt|;
try|try
block|{
name|infoClass
operator|=
name|Class
operator|.
name|forName
argument_list|(
name|root
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ClassNotFoundException
name|ex
parameter_list|)
block|{
name|infoClass
operator|=
name|Class
operator|.
name|forName
argument_list|(
name|getDefinition
argument_list|()
operator|.
name|getImplementation
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|info
operator|=
operator|(
name|TimetableInfo
operator|)
name|infoClass
operator|.
name|getConstructor
argument_list|(
operator|new
name|Class
index|[]
block|{}
argument_list|)
operator|.
name|newInstance
argument_list|(
operator|new
name|Object
index|[]
block|{}
argument_list|)
expr_stmt|;
name|info
operator|.
name|load
argument_list|(
name|root
argument_list|)
expr_stmt|;
if|if
condition|(
name|info
operator|instanceof
name|FileInfo
condition|)
block|{
name|info
operator|=
operator|(
operator|(
name|FileInfo
operator|)
name|info
operator|)
operator|.
name|loadInfo
argument_list|(
name|proxy
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|info
operator|!=
literal|null
condition|)
name|setCached
argument_list|(
name|getUniqueId
argument_list|()
argument_list|,
name|info
argument_list|)
expr_stmt|;
return|return
name|info
return|;
block|}
specifier|public
name|String
name|generateId
parameter_list|()
block|{
throw|throw
operator|new
name|RuntimeException
argument_list|(
literal|"This should never happen."
argument_list|)
throw|;
block|}
specifier|public
name|void
name|setInfo
parameter_list|(
name|TimetableInfo
name|info
parameter_list|)
throws|throws
name|Exception
block|{
name|setInfo
argument_list|(
name|info
argument_list|,
name|TimetableInfoUtil
operator|.
name|getInstance
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|setInfo
parameter_list|(
name|TimetableInfo
name|info
parameter_list|,
name|TimetableInfoFileProxy
name|proxy
parameter_list|)
throws|throws
name|Exception
block|{
if|if
condition|(
name|info
operator|.
name|saveToFile
argument_list|()
condition|)
block|{
name|FileInfo
name|fInfo
init|=
operator|new
name|FileInfo
argument_list|()
decl_stmt|;
name|String
name|defName
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|getDefinition
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|defName
operator|=
name|getDefinition
argument_list|()
operator|.
name|getName
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|defName
operator|=
name|info
operator|.
name|getClass
argument_list|()
operator|.
name|getName
argument_list|()
expr_stmt|;
if|if
condition|(
name|defName
operator|.
name|indexOf
argument_list|(
literal|'.'
argument_list|)
operator|>=
literal|0
condition|)
name|defName
operator|=
name|defName
operator|.
name|substring
argument_list|(
name|defName
operator|.
name|lastIndexOf
argument_list|(
literal|'.'
argument_list|)
operator|+
literal|1
argument_list|)
expr_stmt|;
block|}
name|fInfo
operator|.
name|setName
argument_list|(
name|defName
operator|+
literal|"_"
operator|+
name|generateId
argument_list|()
operator|+
literal|".zxml"
argument_list|)
expr_stmt|;
name|fInfo
operator|.
name|saveInfo
argument_list|(
name|info
argument_list|,
name|proxy
argument_list|)
expr_stmt|;
name|info
operator|=
name|fInfo
expr_stmt|;
block|}
name|Document
name|document
init|=
name|DocumentHelper
operator|.
name|createDocument
argument_list|()
decl_stmt|;
name|Element
name|root
init|=
name|document
operator|.
name|addElement
argument_list|(
name|info
operator|.
name|getClass
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
name|info
operator|.
name|save
argument_list|(
name|root
argument_list|)
expr_stmt|;
name|setValue
argument_list|(
name|document
argument_list|)
expr_stmt|;
name|setCached
argument_list|(
name|getUniqueId
argument_list|()
argument_list|,
name|info
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|delete
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|)
throws|throws
name|Exception
block|{
name|delete
argument_list|(
name|hibSession
argument_list|,
name|TimetableInfoUtil
operator|.
name|getInstance
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|delete
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|TimetableInfoFileProxy
name|proxy
parameter_list|)
throws|throws
name|Exception
block|{
if|if
condition|(
name|getValue
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|Element
name|root
init|=
name|getValue
argument_list|()
operator|.
name|getRootElement
argument_list|()
decl_stmt|;
name|Class
name|infoClass
init|=
literal|null
decl_stmt|;
try|try
block|{
name|infoClass
operator|=
name|Class
operator|.
name|forName
argument_list|(
name|root
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ClassNotFoundException
name|ex
parameter_list|)
block|{
name|infoClass
operator|=
name|Class
operator|.
name|forName
argument_list|(
name|getDefinition
argument_list|()
operator|.
name|getImplementation
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|TimetableInfo
name|info
init|=
operator|(
name|TimetableInfo
operator|)
name|infoClass
operator|.
name|getConstructor
argument_list|(
operator|new
name|Class
index|[]
block|{}
argument_list|)
operator|.
name|newInstance
argument_list|(
operator|new
name|Object
index|[]
block|{}
argument_list|)
decl_stmt|;
if|if
condition|(
name|info
operator|instanceof
name|FileInfo
condition|)
block|{
name|info
operator|.
name|load
argument_list|(
name|root
argument_list|)
expr_stmt|;
operator|(
operator|(
name|FileInfo
operator|)
name|info
operator|)
operator|.
name|deleteFile
argument_list|(
name|proxy
argument_list|)
expr_stmt|;
block|}
block|}
name|removeCached
argument_list|(
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|delete
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|TimetableInfo
name|getCached
parameter_list|(
name|Long
name|uniqueId
parameter_list|)
block|{
if|if
condition|(
name|uniqueId
operator|==
literal|null
condition|)
return|return
literal|null
return|;
synchronized|synchronized
init|(
name|sInfoCache
init|)
block|{
name|CachedTimetableInfo
name|cInfo
init|=
operator|(
name|CachedTimetableInfo
operator|)
name|sInfoCache
operator|.
name|get
argument_list|(
name|uniqueId
argument_list|)
decl_stmt|;
if|if
condition|(
name|cInfo
operator|==
literal|null
condition|)
return|return
literal|null
return|;
name|cInfo
operator|.
name|mark
argument_list|()
expr_stmt|;
return|return
name|cInfo
operator|.
name|getInfo
argument_list|()
return|;
block|}
block|}
specifier|public
specifier|static
name|void
name|setCached
parameter_list|(
name|Long
name|uniqueId
parameter_list|,
name|TimetableInfo
name|info
parameter_list|)
block|{
if|if
condition|(
name|uniqueId
operator|==
literal|null
condition|)
return|return;
synchronized|synchronized
init|(
name|sInfoCache
init|)
block|{
name|sInfoCache
operator|.
name|put
argument_list|(
name|uniqueId
argument_list|,
operator|new
name|CachedTimetableInfo
argument_list|(
name|info
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
specifier|static
name|void
name|removeCached
parameter_list|(
name|Long
name|uniqueId
parameter_list|)
block|{
if|if
condition|(
name|uniqueId
operator|==
literal|null
condition|)
return|return;
synchronized|synchronized
init|(
name|sInfoCache
init|)
block|{
name|sInfoCache
operator|.
name|remove
argument_list|(
name|uniqueId
argument_list|)
expr_stmt|;
block|}
block|}
specifier|protected
specifier|static
name|Hashtable
name|sInfoCache
init|=
literal|null
decl_stmt|;
specifier|protected
specifier|static
name|InfoCacheCleanup
name|sCleanupThread
init|=
literal|null
decl_stmt|;
specifier|protected
specifier|static
name|long
name|sInfoCacheTimeToLive
init|=
literal|600000
decl_stmt|;
comment|//10 minutes
specifier|protected
specifier|static
name|long
name|sInfoCacheCleanupInterval
init|=
literal|30000
decl_stmt|;
comment|//30 secs
static|static
block|{
name|sInfoCache
operator|=
operator|new
name|Hashtable
argument_list|()
expr_stmt|;
name|sCleanupThread
operator|=
operator|new
name|InfoCacheCleanup
argument_list|()
expr_stmt|;
name|sCleanupThread
operator|.
name|start
argument_list|()
expr_stmt|;
block|}
specifier|public
specifier|static
name|void
name|stopInfoCacheCleanup
parameter_list|()
block|{
name|sInfoCache
operator|=
literal|null
expr_stmt|;
if|if
condition|(
name|sCleanupThread
operator|!=
literal|null
operator|&&
name|sCleanupThread
operator|.
name|isAlive
argument_list|()
condition|)
name|sCleanupThread
operator|.
name|interrupt
argument_list|()
expr_stmt|;
block|}
specifier|protected
specifier|static
class|class
name|InfoCacheCleanup
extends|extends
name|Thread
block|{
specifier|public
name|InfoCacheCleanup
parameter_list|()
block|{
name|setDaemon
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|setName
argument_list|(
literal|"InfoCacheCleanup"
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|run
parameter_list|()
block|{
name|Debug
operator|.
name|info
argument_list|(
literal|"InfoCache cleanup thread started."
argument_list|)
expr_stmt|;
try|try
block|{
while|while
condition|(
literal|true
condition|)
block|{
name|sleep
argument_list|(
name|sInfoCacheCleanupInterval
argument_list|)
expr_stmt|;
if|if
condition|(
name|sInfoCache
operator|==
literal|null
condition|)
return|return;
synchronized|synchronized
init|(
name|sInfoCache
init|)
block|{
if|if
condition|(
name|sInfoCache
operator|.
name|isEmpty
argument_list|()
condition|)
continue|continue;
for|for
control|(
name|Iterator
name|i
init|=
name|sInfoCache
operator|.
name|entrySet
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
name|Map
operator|.
name|Entry
name|entry
init|=
operator|(
name|Map
operator|.
name|Entry
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|CachedTimetableInfo
name|cInfo
init|=
operator|(
name|CachedTimetableInfo
operator|)
name|entry
operator|.
name|getValue
argument_list|()
decl_stmt|;
if|if
condition|(
name|cInfo
operator|.
name|getAge
argument_list|()
operator|>
name|sInfoCacheTimeToLive
condition|)
block|{
name|i
operator|.
name|remove
argument_list|()
expr_stmt|;
block|}
block|}
block|}
block|}
block|}
catch|catch
parameter_list|(
name|InterruptedException
name|ex
parameter_list|)
block|{
name|Debug
operator|.
name|info
argument_list|(
literal|"InfoCache cleanup thread interrupted."
argument_list|)
expr_stmt|;
block|}
name|Debug
operator|.
name|info
argument_list|(
literal|"InfoCache cleanup thread finished."
argument_list|)
expr_stmt|;
block|}
block|}
specifier|protected
specifier|static
class|class
name|CachedTimetableInfo
block|{
specifier|private
name|TimetableInfo
name|iInfo
init|=
literal|null
decl_stmt|;
specifier|private
name|long
name|iTimeStamp
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
specifier|public
name|CachedTimetableInfo
parameter_list|(
name|TimetableInfo
name|info
parameter_list|)
block|{
name|iInfo
operator|=
name|info
expr_stmt|;
block|}
specifier|public
name|TimetableInfo
name|getInfo
parameter_list|()
block|{
return|return
name|iInfo
return|;
block|}
specifier|public
name|long
name|getAge
parameter_list|()
block|{
return|return
name|System
operator|.
name|currentTimeMillis
argument_list|()
operator|-
name|iTimeStamp
return|;
block|}
specifier|public
name|void
name|mark
parameter_list|()
block|{
name|iTimeStamp
operator|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

