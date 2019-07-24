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
name|Date
import|;
end_import

begin_import
import|import
name|org
operator|.
name|hibernate
operator|.
name|Transaction
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
name|BaseMapTileCache
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
name|MapTileCacheDAO
import|;
end_import

begin_class
specifier|public
class|class
name|MapTileCache
extends|extends
name|BaseMapTileCache
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
specifier|public
name|MapTileCache
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
specifier|public
name|MapTileCache
parameter_list|(
name|int
name|zoom
parameter_list|,
name|int
name|x
parameter_list|,
name|int
name|y
parameter_list|)
block|{
name|super
argument_list|()
expr_stmt|;
name|setX
argument_list|(
name|x
argument_list|)
expr_stmt|;
name|setY
argument_list|(
name|y
argument_list|)
expr_stmt|;
name|setZ
argument_list|(
name|zoom
argument_list|)
expr_stmt|;
block|}
specifier|public
name|boolean
name|isTooOld
parameter_list|()
block|{
return|return
name|System
operator|.
name|currentTimeMillis
argument_list|()
operator|-
name|getTimeStamp
argument_list|()
operator|.
name|getTime
argument_list|()
operator|>
literal|604800000l
return|;
block|}
specifier|public
specifier|static
name|byte
index|[]
name|get
parameter_list|(
name|int
name|zoom
parameter_list|,
name|int
name|x
parameter_list|,
name|int
name|y
parameter_list|)
block|{
name|MapTileCache
name|tile
init|=
name|MapTileCacheDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
operator|new
name|MapTileCache
argument_list|(
name|zoom
argument_list|,
name|x
argument_list|,
name|y
argument_list|)
argument_list|)
decl_stmt|;
return|return
name|tile
operator|==
literal|null
operator|||
name|tile
operator|.
name|isTooOld
argument_list|()
condition|?
literal|null
else|:
name|tile
operator|.
name|getData
argument_list|()
return|;
block|}
specifier|public
specifier|static
name|void
name|put
parameter_list|(
name|int
name|zoom
parameter_list|,
name|int
name|x
parameter_list|,
name|int
name|y
parameter_list|,
name|byte
index|[]
name|data
parameter_list|)
block|{
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
name|MapTileCacheDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
decl_stmt|;
name|Transaction
name|tx
init|=
name|hibSession
operator|.
name|beginTransaction
argument_list|()
decl_stmt|;
try|try
block|{
name|MapTileCache
name|tile
init|=
name|MapTileCacheDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
operator|new
name|MapTileCache
argument_list|(
name|zoom
argument_list|,
name|x
argument_list|,
name|y
argument_list|)
argument_list|,
name|hibSession
argument_list|)
decl_stmt|;
if|if
condition|(
name|tile
operator|==
literal|null
condition|)
block|{
name|tile
operator|=
operator|new
name|MapTileCache
argument_list|()
expr_stmt|;
name|tile
operator|.
name|setX
argument_list|(
name|x
argument_list|)
expr_stmt|;
name|tile
operator|.
name|setY
argument_list|(
name|y
argument_list|)
expr_stmt|;
name|tile
operator|.
name|setZ
argument_list|(
name|zoom
argument_list|)
expr_stmt|;
block|}
name|tile
operator|.
name|setData
argument_list|(
name|data
argument_list|)
expr_stmt|;
name|tile
operator|.
name|setTimeStamp
argument_list|(
operator|new
name|Date
argument_list|()
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|tile
argument_list|)
expr_stmt|;
name|tx
operator|.
name|commit
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|tx
operator|.
name|rollback
argument_list|()
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit
