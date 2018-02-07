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
name|Externalizable
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|ObjectInput
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|ObjectOutput
import|;
end_import

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
name|Date
import|;
end_import

begin_import
import|import
name|org
operator|.
name|infinispan
operator|.
name|commons
operator|.
name|marshall
operator|.
name|Externalizer
import|;
end_import

begin_import
import|import
name|org
operator|.
name|infinispan
operator|.
name|commons
operator|.
name|marshall
operator|.
name|SerializeWith
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|SerializeWith
argument_list|(
name|XOverride
operator|.
name|XOverrideSerializer
operator|.
name|class
argument_list|)
specifier|public
class|class
name|XOverride
implements|implements
name|Serializable
implements|,
name|Externalizable
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
specifier|private
name|String
name|iExternalId
init|=
literal|null
decl_stmt|;
specifier|private
name|Date
name|iTimeStamp
init|=
literal|null
decl_stmt|;
specifier|private
name|Integer
name|iStatus
init|=
literal|null
decl_stmt|;
specifier|public
name|XOverride
parameter_list|(
name|String
name|externalId
parameter_list|,
name|Date
name|timeStamp
parameter_list|,
name|Integer
name|status
parameter_list|)
block|{
name|iExternalId
operator|=
name|externalId
expr_stmt|;
name|iTimeStamp
operator|=
name|timeStamp
expr_stmt|;
name|iStatus
operator|=
name|status
expr_stmt|;
block|}
specifier|public
name|XOverride
parameter_list|(
name|ObjectInput
name|in
parameter_list|)
throws|throws
name|IOException
throws|,
name|ClassNotFoundException
block|{
name|readExternal
argument_list|(
name|in
argument_list|)
expr_stmt|;
block|}
specifier|public
name|String
name|getExternalId
parameter_list|()
block|{
return|return
name|iExternalId
return|;
block|}
specifier|public
name|Date
name|getTimeStamp
parameter_list|()
block|{
return|return
name|iTimeStamp
return|;
block|}
specifier|public
name|Integer
name|getStatus
parameter_list|()
block|{
return|return
name|iStatus
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|readExternal
parameter_list|(
name|ObjectInput
name|in
parameter_list|)
throws|throws
name|IOException
throws|,
name|ClassNotFoundException
block|{
name|iExternalId
operator|=
operator|(
name|String
operator|)
name|in
operator|.
name|readObject
argument_list|()
expr_stmt|;
name|iTimeStamp
operator|=
operator|(
name|in
operator|.
name|readBoolean
argument_list|()
condition|?
operator|new
name|Date
argument_list|(
name|in
operator|.
name|readLong
argument_list|()
argument_list|)
else|:
literal|null
operator|)
expr_stmt|;
name|int
name|status
init|=
name|in
operator|.
name|readInt
argument_list|()
decl_stmt|;
name|iStatus
operator|=
operator|(
name|status
operator|<
literal|0
condition|?
literal|null
else|:
operator|new
name|Integer
argument_list|(
name|status
argument_list|)
operator|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|writeExternal
parameter_list|(
name|ObjectOutput
name|out
parameter_list|)
throws|throws
name|IOException
block|{
name|out
operator|.
name|writeObject
argument_list|(
name|iExternalId
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeBoolean
argument_list|(
name|iTimeStamp
operator|!=
literal|null
argument_list|)
expr_stmt|;
if|if
condition|(
name|iTimeStamp
operator|!=
literal|null
condition|)
name|out
operator|.
name|writeLong
argument_list|(
name|iTimeStamp
operator|.
name|getTime
argument_list|()
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeInt
argument_list|(
name|iStatus
operator|==
literal|null
condition|?
operator|-
literal|1
else|:
name|iStatus
operator|.
name|intValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
class|class
name|XOverrideSerializer
implements|implements
name|Externalizer
argument_list|<
name|XOverride
argument_list|>
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
annotation|@
name|Override
specifier|public
name|void
name|writeObject
parameter_list|(
name|ObjectOutput
name|output
parameter_list|,
name|XOverride
name|object
parameter_list|)
throws|throws
name|IOException
block|{
name|object
operator|.
name|writeExternal
argument_list|(
name|output
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|XOverride
name|readObject
parameter_list|(
name|ObjectInput
name|input
parameter_list|)
throws|throws
name|IOException
throws|,
name|ClassNotFoundException
block|{
return|return
operator|new
name|XOverride
argument_list|(
name|input
argument_list|)
return|;
block|}
block|}
block|}
end_class

end_unit
