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
name|gwt
operator|.
name|shared
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
name|Date
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
name|client
operator|.
name|GwtRpcRequest
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
name|client
operator|.
name|GwtRpcResponse
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|gwt
operator|.
name|user
operator|.
name|client
operator|.
name|rpc
operator|.
name|IsSerializable
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|SolverInterface
implements|implements
name|IsSerializable
block|{
specifier|public
specifier|static
enum|enum
name|SolverType
block|{
name|COURSE
block|,
name|EXAM
block|,
name|STUDENT
block|,
name|INSTRUCTOR
block|, 		; 	}
specifier|public
specifier|static
enum|enum
name|SolverOperation
block|{
name|INIT
block|,
name|CHECK
block|,
name|LOAD
block|,
name|START
block|,
name|UNLOAD
block|,
name|RELOAD
block|,
name|STOP
block|,
name|CLEAR
block|,
name|EXPORT_CSV
block|,
name|EXPORT_XML
block|,
name|STUDENT_SECTIONING
block|,
name|SAVE_BEST
block|,
name|RESTORE_BEST
block|,
name|SAVE
block|,
name|SAVE_AS_NEW
block|,
name|SAVE_COMMIT
block|,
name|SAVE_AS_NEW_COMMIT
block|,
name|SAVE_UNCOMMIT
block|, 		;
specifier|public
name|int
name|flag
parameter_list|()
block|{
return|return
literal|1
operator|<<
name|ordinal
argument_list|()
return|;
block|}
specifier|public
name|boolean
name|in
parameter_list|(
name|int
name|flags
parameter_list|)
block|{
return|return
operator|(
name|flags
operator|&
name|flag
argument_list|()
operator|)
operator|!=
literal|0
return|;
block|}
specifier|public
name|int
name|set
parameter_list|(
name|int
name|flags
parameter_list|)
block|{
return|return
operator|(
name|in
argument_list|(
name|flags
argument_list|)
condition|?
name|flags
else|:
name|flags
operator|+
name|flag
argument_list|()
operator|)
return|;
block|}
specifier|public
name|int
name|clear
parameter_list|(
name|int
name|flags
parameter_list|)
block|{
return|return
operator|(
name|in
argument_list|(
name|flags
argument_list|)
condition|?
name|flags
operator|-
name|flag
argument_list|()
else|:
name|flags
operator|)
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|SolverParameter
implements|implements
name|IsSerializable
block|{
specifier|private
name|Long
name|iId
decl_stmt|;
specifier|private
name|String
name|iKey
decl_stmt|,
name|iType
decl_stmt|,
name|iName
decl_stmt|,
name|iValue
decl_stmt|,
name|iDefaut
decl_stmt|;
specifier|public
name|SolverParameter
parameter_list|()
block|{
block|}
specifier|public
name|Long
name|getId
parameter_list|()
block|{
return|return
name|iId
return|;
block|}
specifier|public
name|void
name|setId
parameter_list|(
name|Long
name|id
parameter_list|)
block|{
name|iId
operator|=
name|id
expr_stmt|;
block|}
specifier|public
name|String
name|getKey
parameter_list|()
block|{
return|return
name|iKey
return|;
block|}
specifier|public
name|void
name|setKey
parameter_list|(
name|String
name|key
parameter_list|)
block|{
name|iKey
operator|=
name|key
expr_stmt|;
block|}
specifier|public
name|String
name|getType
parameter_list|()
block|{
return|return
name|iType
return|;
block|}
specifier|public
name|void
name|setType
parameter_list|(
name|String
name|type
parameter_list|)
block|{
name|iType
operator|=
name|type
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
name|void
name|setName
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
name|getValue
parameter_list|()
block|{
return|return
name|iValue
return|;
block|}
specifier|public
name|void
name|setValue
parameter_list|(
name|String
name|value
parameter_list|)
block|{
name|iValue
operator|=
name|value
expr_stmt|;
block|}
specifier|public
name|String
name|getDefaultValue
parameter_list|()
block|{
return|return
name|iDefaut
return|;
block|}
specifier|public
name|void
name|setDefaultValue
parameter_list|(
name|String
name|defaultValue
parameter_list|)
block|{
name|iDefaut
operator|=
name|defaultValue
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|getKey
argument_list|()
operator|+
literal|"="
operator|+
operator|(
name|getValue
argument_list|()
operator|!=
literal|null
condition|?
name|getValue
argument_list|()
else|:
name|getDefaultValue
argument_list|()
operator|!=
literal|null
condition|?
name|getDefaultValue
argument_list|()
else|:
literal|""
operator|)
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|SolverConfiguration
implements|implements
name|IsSerializable
block|{
specifier|private
name|Long
name|iId
decl_stmt|;
specifier|private
name|String
name|iName
decl_stmt|;
specifier|private
name|Map
argument_list|<
name|Long
argument_list|,
name|String
argument_list|>
name|iParameters
decl_stmt|;
specifier|public
name|SolverConfiguration
parameter_list|()
block|{
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
name|void
name|setName
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
name|Long
name|getId
parameter_list|()
block|{
return|return
name|iId
return|;
block|}
specifier|public
name|void
name|setId
parameter_list|(
name|Long
name|id
parameter_list|)
block|{
name|iId
operator|=
name|id
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasParameters
parameter_list|()
block|{
return|return
name|iParameters
operator|!=
literal|null
operator|&&
operator|!
name|iParameters
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|void
name|addParameter
parameter_list|(
name|Long
name|id
parameter_list|,
name|String
name|value
parameter_list|)
block|{
if|if
condition|(
name|iParameters
operator|==
literal|null
condition|)
name|iParameters
operator|=
operator|new
name|HashMap
argument_list|<
name|Long
argument_list|,
name|String
argument_list|>
argument_list|()
expr_stmt|;
name|iParameters
operator|.
name|put
argument_list|(
name|id
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Map
argument_list|<
name|Long
argument_list|,
name|String
argument_list|>
name|getParameters
parameter_list|()
block|{
return|return
name|iParameters
return|;
block|}
specifier|public
name|String
name|getParameter
parameter_list|(
name|Long
name|id
parameter_list|)
block|{
if|if
condition|(
name|iParameters
operator|==
literal|null
condition|)
return|return
literal|null
return|;
return|return
operator|(
name|iParameters
operator|==
literal|null
condition|?
literal|null
else|:
name|iParameters
operator|.
name|get
argument_list|(
name|id
argument_list|)
operator|)
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|SolverOwner
implements|implements
name|IsSerializable
block|{
specifier|private
name|Long
name|iId
decl_stmt|;
specifier|private
name|String
name|iName
decl_stmt|;
specifier|public
name|SolverOwner
parameter_list|()
block|{
block|}
specifier|public
name|SolverOwner
parameter_list|(
name|Long
name|id
parameter_list|,
name|String
name|name
parameter_list|)
block|{
name|iId
operator|=
name|id
expr_stmt|;
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
name|void
name|setName
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
name|Long
name|getId
parameter_list|()
block|{
return|return
name|iId
return|;
block|}
specifier|public
name|void
name|setId
parameter_list|(
name|Long
name|id
parameter_list|)
block|{
name|iId
operator|=
name|id
expr_stmt|;
block|}
block|}
specifier|public
specifier|static
class|class
name|InfoPair
implements|implements
name|IsSerializable
block|{
specifier|private
name|String
name|iName
decl_stmt|,
name|iValue
decl_stmt|;
specifier|public
name|InfoPair
parameter_list|()
block|{
block|}
specifier|public
name|InfoPair
parameter_list|(
name|String
name|name
parameter_list|,
name|String
name|value
parameter_list|)
block|{
name|iName
operator|=
name|name
expr_stmt|;
name|iValue
operator|=
name|value
expr_stmt|;
block|}
specifier|public
name|void
name|setName
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
name|void
name|setValue
parameter_list|(
name|String
name|value
parameter_list|)
block|{
name|iValue
operator|=
name|value
expr_stmt|;
block|}
specifier|public
name|String
name|getValue
parameter_list|()
block|{
return|return
name|iValue
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|iName
operator|+
literal|": "
operator|+
name|iValue
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|SolutionInfo
implements|implements
name|IsSerializable
block|{
specifier|private
name|List
argument_list|<
name|InfoPair
argument_list|>
name|iPairs
init|=
operator|new
name|ArrayList
argument_list|<
name|InfoPair
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|String
name|iName
init|=
literal|null
decl_stmt|;
specifier|private
name|String
name|iLog
init|=
literal|null
decl_stmt|;
specifier|public
name|SolutionInfo
parameter_list|()
block|{
block|}
specifier|public
name|InfoPair
name|addPair
parameter_list|(
name|String
name|name
parameter_list|,
name|String
name|value
parameter_list|)
block|{
name|InfoPair
name|pair
init|=
operator|new
name|InfoPair
argument_list|(
name|name
argument_list|,
name|value
argument_list|)
decl_stmt|;
name|iPairs
operator|.
name|add
argument_list|(
name|pair
argument_list|)
expr_stmt|;
return|return
name|pair
return|;
block|}
specifier|public
name|List
argument_list|<
name|InfoPair
argument_list|>
name|getPairs
parameter_list|()
block|{
return|return
name|iPairs
return|;
block|}
specifier|public
name|boolean
name|isEmpty
parameter_list|()
block|{
return|return
name|iPairs
operator|.
name|isEmpty
argument_list|()
return|;
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
name|void
name|setName
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
name|boolean
name|hasLog
parameter_list|()
block|{
return|return
name|iLog
operator|!=
literal|null
operator|&&
operator|!
name|iLog
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|void
name|setLog
parameter_list|(
name|String
name|html
parameter_list|)
block|{
name|iLog
operator|=
name|html
expr_stmt|;
block|}
specifier|public
name|String
name|getLog
parameter_list|()
block|{
return|return
name|iLog
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|iPairs
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
specifier|public
specifier|static
enum|enum
name|PageMessageType
block|{
name|INFO
block|,
name|WARNING
block|,
name|ERROR
block|, 		; 	}
specifier|public
specifier|static
class|class
name|PageMessage
implements|implements
name|IsSerializable
block|{
specifier|private
name|PageMessageType
name|iType
decl_stmt|;
specifier|private
name|String
name|iMessage
decl_stmt|;
specifier|private
name|String
name|iUrl
decl_stmt|;
specifier|public
name|PageMessage
parameter_list|()
block|{
block|}
specifier|public
name|PageMessage
parameter_list|(
name|PageMessageType
name|type
parameter_list|,
name|String
name|message
parameter_list|,
name|String
name|url
parameter_list|)
block|{
name|iType
operator|=
name|type
expr_stmt|;
name|iMessage
operator|=
name|message
expr_stmt|;
name|iUrl
operator|=
name|url
expr_stmt|;
block|}
specifier|public
name|PageMessage
parameter_list|(
name|PageMessageType
name|type
parameter_list|,
name|String
name|message
parameter_list|)
block|{
name|this
argument_list|(
name|type
argument_list|,
name|message
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
specifier|public
name|PageMessageType
name|getType
parameter_list|()
block|{
return|return
name|iType
return|;
block|}
specifier|public
name|void
name|setType
parameter_list|(
name|PageMessageType
name|type
parameter_list|)
block|{
name|iType
operator|=
name|type
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasMessage
parameter_list|()
block|{
return|return
name|iMessage
operator|!=
literal|null
operator|&&
operator|!
name|iMessage
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|String
name|getMessage
parameter_list|()
block|{
return|return
name|iMessage
return|;
block|}
specifier|public
name|void
name|setMessage
parameter_list|(
name|String
name|message
parameter_list|)
block|{
name|iMessage
operator|=
name|message
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasUrl
parameter_list|()
block|{
return|return
name|iUrl
operator|!=
literal|null
operator|&&
operator|!
name|iUrl
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|String
name|getUrl
parameter_list|()
block|{
return|return
name|iUrl
return|;
block|}
specifier|public
name|void
name|setUrl
parameter_list|(
name|String
name|url
parameter_list|)
block|{
name|iUrl
operator|=
name|url
expr_stmt|;
block|}
block|}
specifier|public
specifier|static
class|class
name|SolverPageResponse
implements|implements
name|GwtRpcResponse
block|{
specifier|private
name|Date
name|iLoadDate
decl_stmt|;
specifier|private
name|SolverType
name|iSolverType
decl_stmt|;
specifier|private
name|SolverOperation
name|iOperation
decl_stmt|;
specifier|private
name|String
name|iSolverStatus
decl_stmt|,
name|iSolverProgress
decl_stmt|;
specifier|private
name|Long
name|iConfigurationId
decl_stmt|;
specifier|private
name|List
argument_list|<
name|SolverConfiguration
argument_list|>
name|iConfigurations
decl_stmt|;
specifier|private
name|List
argument_list|<
name|SolverParameter
argument_list|>
name|iParameters
decl_stmt|;
specifier|private
name|List
argument_list|<
name|Long
argument_list|>
name|iOwnerIds
decl_stmt|;
specifier|private
name|List
argument_list|<
name|SolverOwner
argument_list|>
name|iSolverOwners
decl_stmt|;
specifier|private
name|String
name|iHost
decl_stmt|;
specifier|private
name|List
argument_list|<
name|String
argument_list|>
name|iHosts
decl_stmt|;
specifier|private
name|SolutionInfo
name|iCurrentSolution
decl_stmt|,
name|iBestSolution
decl_stmt|;
specifier|private
name|List
argument_list|<
name|SolutionInfo
argument_list|>
name|iSelectedSolutions
decl_stmt|;
specifier|private
name|String
name|iLog
decl_stmt|;
specifier|private
name|int
name|iOperations
init|=
literal|0
decl_stmt|;
specifier|private
name|boolean
name|iAllowMultipleOwners
init|=
literal|false
decl_stmt|;
specifier|private
name|boolean
name|iWorking
init|=
literal|false
decl_stmt|,
name|iRefresh
init|=
literal|false
decl_stmt|;
specifier|private
name|List
argument_list|<
name|PageMessage
argument_list|>
name|iPageMessages
init|=
literal|null
decl_stmt|;
specifier|public
name|SolverPageResponse
parameter_list|()
block|{
block|}
specifier|public
name|SolverType
name|getSolverType
parameter_list|()
block|{
return|return
name|iSolverType
return|;
block|}
specifier|public
name|void
name|setSolverType
parameter_list|(
name|SolverType
name|type
parameter_list|)
block|{
name|iSolverType
operator|=
name|type
expr_stmt|;
block|}
specifier|public
name|SolverOperation
name|getOperation
parameter_list|()
block|{
return|return
name|iOperation
return|;
block|}
specifier|public
name|void
name|setOperation
parameter_list|(
name|SolverOperation
name|operation
parameter_list|)
block|{
name|iOperation
operator|=
name|operation
expr_stmt|;
block|}
specifier|public
name|String
name|getSolverStatus
parameter_list|()
block|{
return|return
name|iSolverStatus
return|;
block|}
specifier|public
name|void
name|setSolverStatus
parameter_list|(
name|String
name|solverStatus
parameter_list|)
block|{
name|iSolverStatus
operator|=
name|solverStatus
expr_stmt|;
block|}
specifier|public
name|String
name|getSolverProgress
parameter_list|()
block|{
return|return
name|iSolverProgress
return|;
block|}
specifier|public
name|void
name|setSolverProgress
parameter_list|(
name|String
name|solverProgress
parameter_list|)
block|{
name|iSolverProgress
operator|=
name|solverProgress
expr_stmt|;
block|}
specifier|public
name|Long
name|getConfigurationId
parameter_list|()
block|{
return|return
name|iConfigurationId
return|;
block|}
specifier|public
name|void
name|setConfigurationId
parameter_list|(
name|Long
name|configId
parameter_list|)
block|{
name|iConfigurationId
operator|=
name|configId
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasConfigurations
parameter_list|()
block|{
return|return
name|iConfigurations
operator|!=
literal|null
operator|&&
operator|!
name|iConfigurations
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|List
argument_list|<
name|SolverConfiguration
argument_list|>
name|getConfigurations
parameter_list|()
block|{
return|return
name|iConfigurations
return|;
block|}
specifier|public
name|void
name|addConfiguration
parameter_list|(
name|SolverConfiguration
name|config
parameter_list|)
block|{
if|if
condition|(
name|iConfigurations
operator|==
literal|null
condition|)
name|iConfigurations
operator|=
operator|new
name|ArrayList
argument_list|<
name|SolverConfiguration
argument_list|>
argument_list|()
expr_stmt|;
name|iConfigurations
operator|.
name|add
argument_list|(
name|config
argument_list|)
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasParameters
parameter_list|()
block|{
return|return
name|iParameters
operator|!=
literal|null
operator|&&
operator|!
name|iParameters
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|List
argument_list|<
name|SolverParameter
argument_list|>
name|getParameters
parameter_list|()
block|{
return|return
name|iParameters
return|;
block|}
specifier|public
name|void
name|addParameter
parameter_list|(
name|SolverParameter
name|parameter
parameter_list|)
block|{
if|if
condition|(
name|iParameters
operator|==
literal|null
condition|)
name|iParameters
operator|=
operator|new
name|ArrayList
argument_list|<
name|SolverParameter
argument_list|>
argument_list|()
expr_stmt|;
name|iParameters
operator|.
name|add
argument_list|(
name|parameter
argument_list|)
expr_stmt|;
block|}
specifier|public
name|SolverParameter
name|getParameter
parameter_list|(
name|Long
name|id
parameter_list|)
block|{
if|if
condition|(
name|iParameters
operator|==
literal|null
operator|||
name|id
operator|==
literal|null
condition|)
return|return
literal|null
return|;
for|for
control|(
name|SolverParameter
name|paremeter
range|:
name|iParameters
control|)
if|if
condition|(
name|id
operator|.
name|equals
argument_list|(
name|paremeter
operator|.
name|getId
argument_list|()
argument_list|)
condition|)
return|return
name|paremeter
return|;
return|return
literal|null
return|;
block|}
specifier|public
name|boolean
name|hasSolverOwners
parameter_list|()
block|{
return|return
name|iSolverOwners
operator|!=
literal|null
operator|&&
operator|!
name|iSolverOwners
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|List
argument_list|<
name|SolverOwner
argument_list|>
name|getSolverOwners
parameter_list|()
block|{
return|return
name|iSolverOwners
return|;
block|}
specifier|public
name|void
name|addSolverOwner
parameter_list|(
name|SolverOwner
name|solverOwner
parameter_list|)
block|{
if|if
condition|(
name|iSolverOwners
operator|==
literal|null
condition|)
name|iSolverOwners
operator|=
operator|new
name|ArrayList
argument_list|<
name|SolverOwner
argument_list|>
argument_list|()
expr_stmt|;
name|iSolverOwners
operator|.
name|add
argument_list|(
name|solverOwner
argument_list|)
expr_stmt|;
block|}
specifier|public
name|SolverOwner
name|getSolverOwner
parameter_list|(
name|Long
name|id
parameter_list|)
block|{
if|if
condition|(
name|iSolverOwners
operator|==
literal|null
operator|||
name|id
operator|==
literal|null
condition|)
return|return
literal|null
return|;
for|for
control|(
name|SolverOwner
name|g
range|:
name|iSolverOwners
control|)
if|if
condition|(
name|id
operator|.
name|equals
argument_list|(
name|g
operator|.
name|getId
argument_list|()
argument_list|)
condition|)
return|return
name|g
return|;
return|return
literal|null
return|;
block|}
specifier|public
name|boolean
name|isAllowMultipleOwners
parameter_list|()
block|{
return|return
name|iAllowMultipleOwners
return|;
block|}
specifier|public
name|void
name|setAllowMultipleOwners
parameter_list|(
name|boolean
name|allow
parameter_list|)
block|{
name|iAllowMultipleOwners
operator|=
name|allow
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasOwerIds
parameter_list|()
block|{
return|return
name|iOwnerIds
operator|!=
literal|null
operator|&&
operator|!
name|iOwnerIds
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|List
argument_list|<
name|Long
argument_list|>
name|getOwnerIds
parameter_list|()
block|{
return|return
name|iOwnerIds
return|;
block|}
specifier|public
name|void
name|addOwnerId
parameter_list|(
name|Long
name|ownerId
parameter_list|)
block|{
if|if
condition|(
name|iOwnerIds
operator|==
literal|null
condition|)
name|iOwnerIds
operator|=
operator|new
name|ArrayList
argument_list|<
name|Long
argument_list|>
argument_list|()
expr_stmt|;
name|iOwnerIds
operator|.
name|add
argument_list|(
name|ownerId
argument_list|)
expr_stmt|;
block|}
specifier|public
name|String
name|getHost
parameter_list|()
block|{
return|return
name|iHost
return|;
block|}
specifier|public
name|void
name|setHost
parameter_list|(
name|String
name|host
parameter_list|)
block|{
name|iHost
operator|=
name|host
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasHosts
parameter_list|()
block|{
return|return
name|iHosts
operator|!=
literal|null
operator|&&
operator|!
name|iHosts
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getHosts
parameter_list|()
block|{
return|return
name|iHosts
return|;
block|}
specifier|public
name|void
name|addHost
parameter_list|(
name|String
name|host
parameter_list|)
block|{
if|if
condition|(
name|iHosts
operator|==
literal|null
condition|)
name|iHosts
operator|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
expr_stmt|;
name|iHosts
operator|.
name|add
argument_list|(
name|host
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|addHost
parameter_list|(
name|int
name|index
parameter_list|,
name|String
name|host
parameter_list|)
block|{
if|if
condition|(
name|iHosts
operator|==
literal|null
condition|)
name|iHosts
operator|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
expr_stmt|;
name|iHosts
operator|.
name|add
argument_list|(
name|index
argument_list|,
name|host
argument_list|)
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasCurrentSolution
parameter_list|()
block|{
return|return
name|iCurrentSolution
operator|!=
literal|null
return|;
block|}
specifier|public
name|SolutionInfo
name|getCurrentSolution
parameter_list|()
block|{
return|return
name|iCurrentSolution
return|;
block|}
specifier|public
name|void
name|setCurrentSolution
parameter_list|(
name|SolutionInfo
name|current
parameter_list|)
block|{
name|iCurrentSolution
operator|=
name|current
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasBestSolution
parameter_list|()
block|{
return|return
name|iBestSolution
operator|!=
literal|null
return|;
block|}
specifier|public
name|SolutionInfo
name|getBestSolution
parameter_list|()
block|{
return|return
name|iBestSolution
return|;
block|}
specifier|public
name|void
name|setBestSolution
parameter_list|(
name|SolutionInfo
name|best
parameter_list|)
block|{
name|iBestSolution
operator|=
name|best
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasSelectedSolutions
parameter_list|()
block|{
return|return
name|iSelectedSolutions
operator|!=
literal|null
operator|&&
operator|!
name|iSelectedSolutions
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|void
name|addSelectedSolution
parameter_list|(
name|SolutionInfo
name|selected
parameter_list|)
block|{
if|if
condition|(
name|iSelectedSolutions
operator|==
literal|null
condition|)
name|iSelectedSolutions
operator|=
operator|new
name|ArrayList
argument_list|<
name|SolutionInfo
argument_list|>
argument_list|()
expr_stmt|;
name|iSelectedSolutions
operator|.
name|add
argument_list|(
name|selected
argument_list|)
expr_stmt|;
block|}
specifier|public
name|List
argument_list|<
name|SolutionInfo
argument_list|>
name|getSelectedSolutions
parameter_list|()
block|{
return|return
name|iSelectedSolutions
return|;
block|}
specifier|public
name|boolean
name|hasLog
parameter_list|()
block|{
return|return
name|iLog
operator|!=
literal|null
operator|&&
operator|!
name|iLog
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|void
name|setLog
parameter_list|(
name|String
name|html
parameter_list|)
block|{
name|iLog
operator|=
name|html
expr_stmt|;
block|}
specifier|public
name|String
name|getLog
parameter_list|()
block|{
return|return
name|iLog
return|;
block|}
specifier|public
name|boolean
name|canExecute
parameter_list|(
name|SolverOperation
name|operation
parameter_list|)
block|{
return|return
name|operation
operator|.
name|in
argument_list|(
name|iOperations
argument_list|)
return|;
block|}
specifier|public
name|void
name|setCanExecute
parameter_list|(
name|SolverOperation
name|operation
parameter_list|,
name|boolean
name|enabled
parameter_list|)
block|{
if|if
condition|(
name|enabled
condition|)
name|iOperations
operator|=
name|operation
operator|.
name|set
argument_list|(
name|iOperations
argument_list|)
expr_stmt|;
else|else
name|iOperations
operator|=
name|operation
operator|.
name|clear
argument_list|(
name|iOperations
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|setCanExecute
parameter_list|(
name|SolverOperation
modifier|...
name|operations
parameter_list|)
block|{
for|for
control|(
name|SolverOperation
name|operation
range|:
name|operations
control|)
name|iOperations
operator|=
name|operation
operator|.
name|set
argument_list|(
name|iOperations
argument_list|)
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasPageMessages
parameter_list|()
block|{
return|return
name|iPageMessages
operator|!=
literal|null
operator|&&
operator|!
name|iPageMessages
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|List
argument_list|<
name|PageMessage
argument_list|>
name|getPageMessages
parameter_list|()
block|{
return|return
name|iPageMessages
return|;
block|}
specifier|public
name|void
name|addPageMessage
parameter_list|(
name|PageMessage
name|message
parameter_list|)
block|{
if|if
condition|(
name|iPageMessages
operator|==
literal|null
condition|)
name|iPageMessages
operator|=
operator|new
name|ArrayList
argument_list|<
name|PageMessage
argument_list|>
argument_list|()
expr_stmt|;
name|iPageMessages
operator|.
name|add
argument_list|(
name|message
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Date
name|getLoadDate
parameter_list|()
block|{
return|return
name|iLoadDate
return|;
block|}
specifier|public
name|void
name|setLoadDate
parameter_list|(
name|Date
name|date
parameter_list|)
block|{
name|iLoadDate
operator|=
name|date
expr_stmt|;
block|}
specifier|public
name|boolean
name|isWorking
parameter_list|()
block|{
return|return
name|iWorking
return|;
block|}
specifier|public
name|void
name|setWorking
parameter_list|(
name|boolean
name|working
parameter_list|)
block|{
name|iWorking
operator|=
name|working
expr_stmt|;
block|}
specifier|public
name|boolean
name|isRefresh
parameter_list|()
block|{
return|return
name|iRefresh
return|;
block|}
specifier|public
name|void
name|setRefresh
parameter_list|(
name|boolean
name|refresh
parameter_list|)
block|{
name|iRefresh
operator|=
name|refresh
expr_stmt|;
block|}
block|}
specifier|public
specifier|static
class|class
name|SolverPageRequest
implements|implements
name|GwtRpcRequest
argument_list|<
name|SolverPageResponse
argument_list|>
block|{
specifier|private
name|SolverType
name|iType
decl_stmt|;
specifier|private
name|SolverOperation
name|iOperation
decl_stmt|;
specifier|private
name|List
argument_list|<
name|Long
argument_list|>
name|iOwnerIds
decl_stmt|;
specifier|private
name|Long
name|iConfigurationId
decl_stmt|;
specifier|private
name|String
name|iHost
decl_stmt|;
specifier|private
name|Map
argument_list|<
name|Long
argument_list|,
name|String
argument_list|>
name|iParameters
decl_stmt|;
specifier|public
name|SolverPageRequest
parameter_list|()
block|{
block|}
specifier|public
name|SolverPageRequest
parameter_list|(
name|SolverType
name|type
parameter_list|,
name|SolverOperation
name|operation
parameter_list|)
block|{
name|iType
operator|=
name|type
expr_stmt|;
name|iOperation
operator|=
name|operation
expr_stmt|;
block|}
specifier|public
name|SolverType
name|getType
parameter_list|()
block|{
return|return
name|iType
return|;
block|}
specifier|public
name|void
name|setType
parameter_list|(
name|SolverType
name|type
parameter_list|)
block|{
name|iType
operator|=
name|type
expr_stmt|;
block|}
specifier|public
name|SolverOperation
name|getOperation
parameter_list|()
block|{
return|return
name|iOperation
return|;
block|}
specifier|public
name|void
name|setOperation
parameter_list|(
name|SolverOperation
name|operation
parameter_list|)
block|{
name|iOperation
operator|=
name|operation
expr_stmt|;
block|}
specifier|public
name|Long
name|getConfigurationId
parameter_list|()
block|{
return|return
name|iConfigurationId
return|;
block|}
specifier|public
name|void
name|setConfigurationId
parameter_list|(
name|Long
name|configId
parameter_list|)
block|{
name|iConfigurationId
operator|=
name|configId
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasOwerIds
parameter_list|()
block|{
return|return
name|iOwnerIds
operator|!=
literal|null
operator|&&
operator|!
name|iOwnerIds
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|List
argument_list|<
name|Long
argument_list|>
name|getOwnerIds
parameter_list|()
block|{
return|return
name|iOwnerIds
return|;
block|}
specifier|public
name|void
name|addOwnerId
parameter_list|(
name|Long
name|ownerId
parameter_list|)
block|{
if|if
condition|(
name|iOwnerIds
operator|==
literal|null
condition|)
name|iOwnerIds
operator|=
operator|new
name|ArrayList
argument_list|<
name|Long
argument_list|>
argument_list|()
expr_stmt|;
name|iOwnerIds
operator|.
name|add
argument_list|(
name|ownerId
argument_list|)
expr_stmt|;
block|}
specifier|public
name|String
name|getHost
parameter_list|()
block|{
return|return
name|iHost
return|;
block|}
specifier|public
name|void
name|setHost
parameter_list|(
name|String
name|host
parameter_list|)
block|{
name|iHost
operator|=
name|host
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasParameters
parameter_list|()
block|{
return|return
name|iParameters
operator|!=
literal|null
operator|&&
operator|!
name|iParameters
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|void
name|addParameter
parameter_list|(
name|Long
name|id
parameter_list|,
name|String
name|value
parameter_list|)
block|{
if|if
condition|(
name|iParameters
operator|==
literal|null
condition|)
name|iParameters
operator|=
operator|new
name|HashMap
argument_list|<
name|Long
argument_list|,
name|String
argument_list|>
argument_list|()
expr_stmt|;
name|iParameters
operator|.
name|put
argument_list|(
name|id
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Map
argument_list|<
name|Long
argument_list|,
name|String
argument_list|>
name|getParameters
parameter_list|()
block|{
return|return
name|iParameters
return|;
block|}
specifier|public
name|String
name|getParameter
parameter_list|(
name|Long
name|id
parameter_list|)
block|{
if|if
condition|(
name|iParameters
operator|==
literal|null
condition|)
return|return
literal|null
return|;
return|return
operator|(
name|iParameters
operator|==
literal|null
condition|?
literal|null
else|:
name|iParameters
operator|.
name|get
argument_list|(
name|id
argument_list|)
operator|)
return|;
block|}
specifier|public
name|void
name|clear
parameter_list|()
block|{
name|iOwnerIds
operator|=
literal|null
expr_stmt|;
name|iConfigurationId
operator|=
literal|null
expr_stmt|;
name|iHost
operator|=
literal|null
expr_stmt|;
name|iParameters
operator|=
literal|null
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|getType
argument_list|()
operator|+
literal|": "
operator|+
name|getOperation
argument_list|()
return|;
block|}
block|}
block|}
end_class

end_unit

