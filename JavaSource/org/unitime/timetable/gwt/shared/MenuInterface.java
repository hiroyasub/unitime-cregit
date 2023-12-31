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
name|GwtRpcResponseBoolean
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
name|GwtRpcResponseList
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
name|MenuInterface
implements|implements
name|IsSerializable
block|{
specifier|private
name|String
name|iName
init|=
literal|null
decl_stmt|;
specifier|private
name|String
name|iTitle
init|=
literal|null
decl_stmt|;
specifier|private
name|String
name|iPage
init|=
literal|null
decl_stmt|;
specifier|private
name|String
name|iHash
init|=
literal|null
decl_stmt|;
specifier|private
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|String
argument_list|>
argument_list|>
name|iParameters
init|=
literal|null
decl_stmt|;
specifier|private
name|String
name|iTarget
init|=
literal|null
decl_stmt|;
specifier|private
name|boolean
name|iGWT
init|=
literal|false
decl_stmt|;
specifier|private
name|List
argument_list|<
name|MenuInterface
argument_list|>
name|iSubMenus
init|=
literal|null
decl_stmt|;
specifier|public
name|MenuInterface
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
name|String
name|getTitle
parameter_list|()
block|{
return|return
name|iTitle
return|;
block|}
specifier|public
name|void
name|setTitle
parameter_list|(
name|String
name|title
parameter_list|)
block|{
name|iTitle
operator|=
name|title
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasPage
parameter_list|()
block|{
return|return
name|iPage
operator|!=
literal|null
operator|&&
operator|!
name|iPage
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|String
name|getPage
parameter_list|()
block|{
return|return
name|iPage
return|;
block|}
specifier|public
name|void
name|setPage
parameter_list|(
name|String
name|page
parameter_list|)
block|{
name|iPage
operator|=
name|page
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasTarget
parameter_list|()
block|{
return|return
name|iTarget
operator|!=
literal|null
operator|&&
operator|!
name|iTarget
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|String
name|getTarget
parameter_list|()
block|{
return|return
name|iTarget
return|;
block|}
specifier|public
name|void
name|setTarget
parameter_list|(
name|String
name|target
parameter_list|)
block|{
name|iTarget
operator|=
name|target
expr_stmt|;
block|}
specifier|public
name|void
name|setGWT
parameter_list|(
name|boolean
name|gwt
parameter_list|)
block|{
name|iGWT
operator|=
name|gwt
expr_stmt|;
block|}
specifier|public
name|boolean
name|isGWT
parameter_list|()
block|{
return|return
name|iGWT
return|;
block|}
specifier|public
name|boolean
name|isSeparator
parameter_list|()
block|{
return|return
name|getName
argument_list|()
operator|==
literal|null
return|;
block|}
specifier|public
name|String
name|getHash
parameter_list|()
block|{
return|return
name|iHash
return|;
block|}
specifier|public
name|void
name|setHash
parameter_list|(
name|String
name|hash
parameter_list|)
block|{
name|iHash
operator|=
name|hash
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasHash
parameter_list|()
block|{
return|return
name|iHash
operator|!=
literal|null
operator|&&
operator|!
name|iHash
operator|.
name|isEmpty
argument_list|()
return|;
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
name|String
name|name
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
name|String
argument_list|,
name|List
argument_list|<
name|String
argument_list|>
argument_list|>
argument_list|()
expr_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|values
init|=
name|iParameters
operator|.
name|get
argument_list|(
name|name
argument_list|)
decl_stmt|;
if|if
condition|(
name|values
operator|==
literal|null
condition|)
block|{
name|values
operator|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
expr_stmt|;
name|iParameters
operator|.
name|put
argument_list|(
name|name
argument_list|,
name|values
argument_list|)
expr_stmt|;
block|}
name|values
operator|.
name|add
argument_list|(
name|value
argument_list|)
expr_stmt|;
block|}
specifier|public
name|String
name|getParameters
parameter_list|(
name|ValueEncoder
name|encoder
parameter_list|)
block|{
name|String
name|ret
init|=
literal|""
decl_stmt|;
if|if
condition|(
name|iParameters
operator|!=
literal|null
condition|)
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|String
argument_list|>
argument_list|>
name|values
range|:
name|iParameters
operator|.
name|entrySet
argument_list|()
control|)
block|{
for|for
control|(
name|String
name|value
range|:
name|values
operator|.
name|getValue
argument_list|()
control|)
block|{
if|if
condition|(
operator|!
name|ret
operator|.
name|isEmpty
argument_list|()
condition|)
name|ret
operator|+=
literal|"&"
expr_stmt|;
name|ret
operator|+=
name|values
operator|.
name|getKey
argument_list|()
operator|+
literal|"="
operator|+
operator|(
name|encoder
operator|==
literal|null
condition|?
name|value
else|:
name|encoder
operator|.
name|encode
argument_list|(
name|value
argument_list|)
operator|)
expr_stmt|;
block|}
block|}
return|return
name|ret
return|;
block|}
specifier|public
name|boolean
name|hasSubMenus
parameter_list|()
block|{
return|return
name|iSubMenus
operator|!=
literal|null
operator|&&
operator|!
name|iSubMenus
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|List
argument_list|<
name|MenuInterface
argument_list|>
name|getSubMenus
parameter_list|()
block|{
return|return
name|iSubMenus
return|;
block|}
specifier|public
name|void
name|addSubMenu
parameter_list|(
name|MenuInterface
name|menu
parameter_list|)
block|{
if|if
condition|(
name|iSubMenus
operator|==
literal|null
condition|)
name|iSubMenus
operator|=
operator|new
name|ArrayList
argument_list|<
name|MenuInterface
argument_list|>
argument_list|()
expr_stmt|;
name|iSubMenus
operator|.
name|add
argument_list|(
name|menu
argument_list|)
expr_stmt|;
block|}
specifier|public
name|String
name|getURL
parameter_list|(
name|ValueEncoder
name|encoder
parameter_list|)
block|{
if|if
condition|(
name|isGWT
argument_list|()
condition|)
return|return
literal|"gwt.jsp?page="
operator|+
name|getPage
argument_list|()
operator|+
operator|(
name|hasParameters
argument_list|()
condition|?
literal|"&"
operator|+
name|getParameters
argument_list|(
name|encoder
argument_list|)
else|:
literal|""
operator|)
operator|+
operator|(
name|hasHash
argument_list|()
condition|?
literal|"#"
operator|+
name|getHash
argument_list|()
else|:
literal|""
operator|)
return|;
else|else
return|return
name|getPage
argument_list|()
operator|+
operator|(
name|hasParameters
argument_list|()
condition|?
literal|"?"
operator|+
name|getParameters
argument_list|(
name|encoder
argument_list|)
else|:
literal|""
operator|)
operator|+
operator|(
name|hasHash
argument_list|()
condition|?
literal|"#"
operator|+
name|getHash
argument_list|()
else|:
literal|""
operator|)
return|;
block|}
specifier|public
specifier|static
interface|interface
name|ValueEncoder
block|{
specifier|public
name|String
name|encode
parameter_list|(
name|String
name|value
parameter_list|)
function_decl|;
block|}
specifier|public
specifier|static
class|class
name|MenuRpcRequest
implements|implements
name|GwtRpcRequest
argument_list|<
name|GwtRpcResponseList
argument_list|<
name|MenuInterface
argument_list|>
argument_list|>
block|{
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
literal|null
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|InfoPairInterface
implements|implements
name|IsSerializable
block|{
specifier|private
name|String
name|iName
decl_stmt|,
name|iValue
decl_stmt|;
specifier|private
name|boolean
name|iSeparator
init|=
literal|false
decl_stmt|;
specifier|public
name|InfoPairInterface
parameter_list|()
block|{
block|}
specifier|public
name|InfoPairInterface
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
specifier|public
name|boolean
name|hasSeparator
parameter_list|()
block|{
return|return
name|iSeparator
return|;
block|}
specifier|public
name|void
name|setSeparator
parameter_list|(
name|boolean
name|separator
parameter_list|)
block|{
name|iSeparator
operator|=
name|separator
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
name|InfoInterface
implements|implements
name|GwtRpcResponse
block|{
specifier|private
name|List
argument_list|<
name|InfoPairInterface
argument_list|>
name|iPairs
init|=
operator|new
name|ArrayList
argument_list|<
name|MenuInterface
operator|.
name|InfoPairInterface
argument_list|>
argument_list|()
decl_stmt|;
specifier|public
name|InfoInterface
parameter_list|()
block|{
block|}
specifier|public
name|InfoPairInterface
name|addPair
parameter_list|(
name|String
name|name
parameter_list|,
name|String
name|value
parameter_list|)
block|{
name|InfoPairInterface
name|pair
init|=
operator|new
name|InfoPairInterface
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
name|InfoPairInterface
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
class|class
name|UserInfoInterface
extends|extends
name|InfoInterface
block|{
specifier|private
name|boolean
name|iChameleon
init|=
literal|false
decl_stmt|;
specifier|private
name|String
name|iName
decl_stmt|,
name|iRole
decl_stmt|;
specifier|public
name|void
name|setChameleon
parameter_list|(
name|boolean
name|chameleon
parameter_list|)
block|{
name|iChameleon
operator|=
name|chameleon
expr_stmt|;
block|}
specifier|public
name|boolean
name|isChameleon
parameter_list|()
block|{
return|return
name|iChameleon
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
name|getName
parameter_list|()
block|{
return|return
name|iName
return|;
block|}
specifier|public
name|void
name|setRole
parameter_list|(
name|String
name|role
parameter_list|)
block|{
name|iRole
operator|=
name|role
expr_stmt|;
block|}
specifier|public
name|String
name|getRole
parameter_list|()
block|{
return|return
name|iRole
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|UserInfoRpcRequest
implements|implements
name|GwtRpcRequest
argument_list|<
name|UserInfoInterface
argument_list|>
block|{
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
literal|null
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|VersionInfoInterface
implements|implements
name|GwtRpcResponse
block|{
specifier|private
name|String
name|iVersion
decl_stmt|;
specifier|private
name|String
name|iBuildNumber
decl_stmt|;
specifier|private
name|String
name|iReleaseDate
decl_stmt|;
specifier|public
name|VersionInfoInterface
parameter_list|()
block|{
block|}
specifier|public
name|String
name|getVersion
parameter_list|()
block|{
return|return
name|iVersion
return|;
block|}
specifier|public
name|void
name|setVersion
parameter_list|(
name|String
name|version
parameter_list|)
block|{
name|iVersion
operator|=
name|version
expr_stmt|;
block|}
specifier|public
name|String
name|getBuildNumber
parameter_list|()
block|{
return|return
name|iBuildNumber
return|;
block|}
specifier|public
name|void
name|setBuildNumber
parameter_list|(
name|String
name|buildNumber
parameter_list|)
block|{
name|iBuildNumber
operator|=
name|buildNumber
expr_stmt|;
block|}
specifier|public
name|String
name|getReleaseDate
parameter_list|()
block|{
return|return
name|iReleaseDate
return|;
block|}
specifier|public
name|void
name|setReleaseDate
parameter_list|(
name|String
name|releaseDate
parameter_list|)
block|{
name|iReleaseDate
operator|=
name|releaseDate
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
name|iVersion
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|VersionInfoRpcRequest
implements|implements
name|GwtRpcRequest
argument_list|<
name|VersionInfoInterface
argument_list|>
block|{
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
literal|null
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|SessionInfoInterface
extends|extends
name|InfoInterface
block|{
specifier|private
name|String
name|iSession
init|=
literal|null
decl_stmt|;
specifier|public
name|String
name|getSession
parameter_list|()
block|{
return|return
name|iSession
return|;
block|}
specifier|public
name|void
name|setSession
parameter_list|(
name|String
name|session
parameter_list|)
block|{
name|iSession
operator|=
name|session
expr_stmt|;
block|}
block|}
specifier|public
specifier|static
class|class
name|SessionInfoRpcRequest
implements|implements
name|GwtRpcRequest
argument_list|<
name|SessionInfoInterface
argument_list|>
block|{
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
literal|null
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|SolverInfoInterface
extends|extends
name|InfoInterface
block|{
specifier|private
name|String
name|iSolver
decl_stmt|,
name|iType
decl_stmt|,
name|iUrl
decl_stmt|;
specifier|public
name|String
name|getSolver
parameter_list|()
block|{
return|return
name|iSolver
return|;
block|}
specifier|public
name|void
name|setSolver
parameter_list|(
name|String
name|solver
parameter_list|)
block|{
name|iSolver
operator|=
name|solver
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
name|SolverInfoRpcRequest
implements|implements
name|GwtRpcRequest
argument_list|<
name|SolverInfoInterface
argument_list|>
block|{
specifier|private
name|boolean
name|iIncludeSolutionInfo
init|=
literal|false
decl_stmt|;
specifier|public
name|SolverInfoRpcRequest
parameter_list|()
block|{
block|}
specifier|public
name|SolverInfoRpcRequest
parameter_list|(
name|boolean
name|includeSolutionInfo
parameter_list|)
block|{
name|iIncludeSolutionInfo
operator|=
name|includeSolutionInfo
expr_stmt|;
block|}
specifier|public
name|boolean
name|isIncludeSolutionInfo
parameter_list|()
block|{
return|return
name|iIncludeSolutionInfo
return|;
block|}
specifier|public
name|void
name|setIncludeSolutionInfo
parameter_list|(
name|boolean
name|incldueSolutionInfo
parameter_list|)
block|{
name|iIncludeSolutionInfo
operator|=
name|incldueSolutionInfo
expr_stmt|;
block|}
block|}
specifier|public
specifier|static
class|class
name|PageNameInterface
implements|implements
name|GwtRpcResponse
block|{
specifier|private
name|String
name|iHelpUrl
decl_stmt|,
name|iName
decl_stmt|;
specifier|public
name|PageNameInterface
parameter_list|()
block|{
block|}
specifier|public
name|PageNameInterface
parameter_list|(
name|String
name|name
parameter_list|,
name|String
name|helpUrl
parameter_list|)
block|{
name|iName
operator|=
name|name
expr_stmt|;
name|iHelpUrl
operator|=
name|helpUrl
expr_stmt|;
block|}
specifier|public
name|PageNameInterface
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|iName
operator|=
name|name
expr_stmt|;
name|iHelpUrl
operator|=
literal|null
expr_stmt|;
block|}
specifier|public
name|String
name|getHelpUrl
parameter_list|()
block|{
return|return
name|iHelpUrl
return|;
block|}
specifier|public
name|void
name|setHelpUrl
parameter_list|(
name|String
name|url
parameter_list|)
block|{
name|iHelpUrl
operator|=
name|url
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasHelpUrl
parameter_list|()
block|{
return|return
name|iHelpUrl
operator|!=
literal|null
operator|&&
operator|!
name|iHelpUrl
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
name|hasName
parameter_list|()
block|{
return|return
name|iName
operator|!=
literal|null
operator|&&
operator|!
name|iName
operator|.
name|isEmpty
argument_list|()
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
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|PageNameRpcRequest
implements|implements
name|GwtRpcRequest
argument_list|<
name|PageNameInterface
argument_list|>
block|{
specifier|private
name|String
name|iName
decl_stmt|;
specifier|public
name|PageNameRpcRequest
parameter_list|()
block|{
block|}
specifier|public
name|PageNameRpcRequest
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
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|iName
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|IsSessionBusyRpcRequest
implements|implements
name|GwtRpcRequest
argument_list|<
name|GwtRpcResponseBoolean
argument_list|>
block|{ 	}
block|}
end_class

end_unit

