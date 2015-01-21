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
name|client
operator|.
name|page
package|;
end_package

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
name|client
operator|.
name|page
operator|.
name|InfoPanelDisplay
operator|.
name|Callback
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
name|GwtRpcService
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
name|GwtRpcServiceAsync
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
name|GwtMessages
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
name|shared
operator|.
name|MenuInterface
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
name|shared
operator|.
name|MenuInterface
operator|.
name|SessionInfoInterface
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
name|shared
operator|.
name|MenuInterface
operator|.
name|SolverInfoInterface
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
name|shared
operator|.
name|MenuInterface
operator|.
name|UserInfoInterface
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
name|core
operator|.
name|client
operator|.
name|GWT
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
name|Timer
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
name|AsyncCallback
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
name|ui
operator|.
name|RootPanel
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
name|ui
operator|.
name|Widget
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|UniTimePageHeader
implements|implements
name|PageHeaderDisplay
block|{
specifier|protected
specifier|static
specifier|final
name|GwtRpcServiceAsync
name|RPC
init|=
name|GWT
operator|.
name|create
argument_list|(
name|GwtRpcService
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|protected
specifier|static
specifier|final
name|GwtMessages
name|MESSAGES
init|=
name|GWT
operator|.
name|create
argument_list|(
name|GwtMessages
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|PageHeader
name|iHeader
decl_stmt|;
specifier|private
specifier|static
name|UniTimePageHeader
name|sInstance
init|=
literal|null
decl_stmt|;
specifier|private
name|UniTimePageHeader
parameter_list|()
block|{
name|iHeader
operator|=
operator|new
name|PageHeader
argument_list|()
expr_stmt|;
name|getLeft
argument_list|()
operator|.
name|setCallback
argument_list|(
operator|new
name|Callback
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|execute
parameter_list|(
name|Callback
name|callback
parameter_list|)
block|{
name|reloadSolverInfo
argument_list|(
literal|true
argument_list|,
name|callback
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|reloadSessionInfo
argument_list|()
expr_stmt|;
name|reloadUserInfo
argument_list|()
expr_stmt|;
name|reloadSolverInfo
argument_list|(
literal|false
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|insert
parameter_list|(
specifier|final
name|RootPanel
name|panel
parameter_list|)
block|{
if|if
condition|(
name|panel
operator|.
name|getWidgetCount
argument_list|()
operator|>
literal|0
condition|)
return|return;
name|panel
operator|.
name|add
argument_list|(
name|iHeader
argument_list|)
expr_stmt|;
name|panel
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
specifier|synchronized
name|UniTimePageHeader
name|getInstance
parameter_list|()
block|{
if|if
condition|(
name|sInstance
operator|==
literal|null
condition|)
name|sInstance
operator|=
operator|new
name|UniTimePageHeader
argument_list|()
expr_stmt|;
return|return
name|sInstance
return|;
block|}
specifier|public
name|void
name|reloadSessionInfo
parameter_list|()
block|{
if|if
condition|(
name|getRight
argument_list|()
operator|.
name|isPreventDefault
argument_list|()
condition|)
return|return;
name|RPC
operator|.
name|execute
argument_list|(
operator|new
name|MenuInterface
operator|.
name|SessionInfoRpcRequest
argument_list|()
argument_list|,
operator|new
name|AsyncCallback
argument_list|<
name|SessionInfoInterface
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onSuccess
parameter_list|(
name|SessionInfoInterface
name|result
parameter_list|)
block|{
if|if
condition|(
name|getRight
argument_list|()
operator|.
name|isPreventDefault
argument_list|()
condition|)
return|return;
if|if
condition|(
name|result
operator|==
literal|null
operator|||
name|result
operator|.
name|getSession
argument_list|()
operator|==
literal|null
condition|)
block|{
name|getRight
argument_list|()
operator|.
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|getRight
argument_list|()
operator|.
name|setText
argument_list|(
name|result
operator|.
name|getSession
argument_list|()
argument_list|)
expr_stmt|;
name|getRight
argument_list|()
operator|.
name|setInfo
argument_list|(
name|result
argument_list|)
expr_stmt|;
name|getRight
argument_list|()
operator|.
name|setHint
argument_list|(
name|MESSAGES
operator|.
name|hintClickToChangeSession
argument_list|()
argument_list|)
expr_stmt|;
name|getRight
argument_list|()
operator|.
name|setUrl
argument_list|(
literal|"selectPrimaryRole.do?list=Y"
argument_list|)
expr_stmt|;
name|getRight
argument_list|()
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|onFailure
parameter_list|(
name|Throwable
name|caught
parameter_list|)
block|{
if|if
condition|(
name|getRight
argument_list|()
operator|.
name|isPreventDefault
argument_list|()
condition|)
return|return;
name|getRight
argument_list|()
operator|.
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|reloadUserInfo
parameter_list|()
block|{
if|if
condition|(
name|getMiddle
argument_list|()
operator|.
name|isPreventDefault
argument_list|()
condition|)
return|return;
name|RPC
operator|.
name|execute
argument_list|(
operator|new
name|MenuInterface
operator|.
name|UserInfoRpcRequest
argument_list|()
argument_list|,
operator|new
name|AsyncCallback
argument_list|<
name|UserInfoInterface
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onSuccess
parameter_list|(
name|UserInfoInterface
name|result
parameter_list|)
block|{
if|if
condition|(
name|getMiddle
argument_list|()
operator|.
name|isPreventDefault
argument_list|()
condition|)
return|return;
if|if
condition|(
name|result
operator|==
literal|null
operator|||
name|result
operator|.
name|getName
argument_list|()
operator|==
literal|null
condition|)
block|{
name|getMiddle
argument_list|()
operator|.
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|getMiddle
argument_list|()
operator|.
name|setText
argument_list|(
name|result
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|getMiddle
argument_list|()
operator|.
name|setHint
argument_list|(
name|result
operator|.
name|getRole
argument_list|()
argument_list|)
expr_stmt|;
name|getMiddle
argument_list|()
operator|.
name|setInfo
argument_list|(
name|result
argument_list|)
expr_stmt|;
if|if
condition|(
name|result
operator|.
name|isChameleon
argument_list|()
condition|)
block|{
name|getMiddle
argument_list|()
operator|.
name|setUrl
argument_list|(
literal|"chameleon.do"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|getMiddle
argument_list|()
operator|.
name|setUrl
argument_list|(
literal|"selectPrimaryRole.do?list=Y"
argument_list|)
expr_stmt|;
block|}
name|getMiddle
argument_list|()
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|onFailure
parameter_list|(
name|Throwable
name|caught
parameter_list|)
block|{
if|if
condition|(
name|getMiddle
argument_list|()
operator|.
name|isPreventDefault
argument_list|()
condition|)
return|return;
name|getMiddle
argument_list|()
operator|.
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|reloadSolverInfo
parameter_list|(
name|boolean
name|includeSolutionInfo
parameter_list|,
specifier|final
name|Callback
name|callback
parameter_list|)
block|{
if|if
condition|(
name|getLeft
argument_list|()
operator|.
name|isPreventDefault
argument_list|()
condition|)
return|return;
name|RPC
operator|.
name|execute
argument_list|(
operator|new
name|MenuInterface
operator|.
name|SolverInfoRpcRequest
argument_list|(
name|includeSolutionInfo
argument_list|)
argument_list|,
operator|new
name|AsyncCallback
argument_list|<
name|SolverInfoInterface
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onSuccess
parameter_list|(
name|SolverInfoInterface
name|result
parameter_list|)
block|{
if|if
condition|(
name|getLeft
argument_list|()
operator|.
name|isPreventDefault
argument_list|()
condition|)
return|return;
try|try
block|{
if|if
condition|(
name|result
operator|!=
literal|null
operator|&&
name|result
operator|.
name|getSolver
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|getLeft
argument_list|()
operator|.
name|setText
argument_list|(
name|result
operator|.
name|getSolver
argument_list|()
argument_list|)
expr_stmt|;
name|getLeft
argument_list|()
operator|.
name|setHint
argument_list|(
name|result
operator|.
name|getType
argument_list|()
argument_list|)
expr_stmt|;
name|getLeft
argument_list|()
operator|.
name|setInfo
argument_list|(
name|result
argument_list|)
expr_stmt|;
name|getLeft
argument_list|()
operator|.
name|setUrl
argument_list|(
name|result
operator|.
name|getUrl
argument_list|()
argument_list|)
expr_stmt|;
name|getLeft
argument_list|()
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|getLeft
argument_list|()
operator|.
name|setVisible
argument_list|(
literal|false
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
block|}
name|Timer
name|t
init|=
operator|new
name|Timer
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|run
parameter_list|()
block|{
name|reloadSolverInfo
argument_list|(
name|getLeft
argument_list|()
operator|.
name|isPopupShowing
argument_list|()
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
block|}
decl_stmt|;
name|t
operator|.
name|schedule
argument_list|(
name|result
operator|!=
literal|null
condition|?
literal|1000
else|:
literal|60000
argument_list|)
expr_stmt|;
if|if
condition|(
name|callback
operator|!=
literal|null
condition|)
name|callback
operator|.
name|execute
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|onFailure
parameter_list|(
name|Throwable
name|caught
parameter_list|)
block|{
if|if
condition|(
name|getLeft
argument_list|()
operator|.
name|isPreventDefault
argument_list|()
condition|)
return|return;
name|Timer
name|t
init|=
operator|new
name|Timer
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|run
parameter_list|()
block|{
name|reloadSolverInfo
argument_list|(
name|getLeft
argument_list|()
operator|.
name|isPopupShowing
argument_list|()
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
block|}
decl_stmt|;
name|t
operator|.
name|schedule
argument_list|(
literal|5000
argument_list|)
expr_stmt|;
if|if
condition|(
name|callback
operator|!=
literal|null
condition|)
name|callback
operator|.
name|execute
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|InfoPanel
name|getLeft
parameter_list|()
block|{
return|return
name|iHeader
operator|.
name|getLeft
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|InfoPanel
name|getMiddle
parameter_list|()
block|{
return|return
name|iHeader
operator|.
name|getMiddle
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|InfoPanel
name|getRight
parameter_list|()
block|{
return|return
name|iHeader
operator|.
name|getRight
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|Widget
name|asWidget
parameter_list|()
block|{
return|return
name|iHeader
return|;
block|}
block|}
end_class

end_unit

