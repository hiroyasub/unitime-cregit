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
name|sectioning
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
name|GwtHint
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
name|client
operator|.
name|widgets
operator|.
name|UniTimeTextBox
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
name|client
operator|.
name|widgets
operator|.
name|UniTimeWidget
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
name|resources
operator|.
name|GwtResources
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
name|dom
operator|.
name|client
operator|.
name|Element
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
name|event
operator|.
name|dom
operator|.
name|client
operator|.
name|ChangeEvent
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
name|event
operator|.
name|dom
operator|.
name|client
operator|.
name|ChangeHandler
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
name|event
operator|.
name|dom
operator|.
name|client
operator|.
name|MouseOutEvent
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
name|event
operator|.
name|dom
operator|.
name|client
operator|.
name|MouseOutHandler
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
name|event
operator|.
name|dom
operator|.
name|client
operator|.
name|MouseOverEvent
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
name|event
operator|.
name|dom
operator|.
name|client
operator|.
name|MouseOverHandler
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
name|event
operator|.
name|logical
operator|.
name|shared
operator|.
name|ValueChangeEvent
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
name|event
operator|.
name|logical
operator|.
name|shared
operator|.
name|ValueChangeHandler
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
name|DOM
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
name|Anchor
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
name|Composite
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
name|FlowPanel
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
name|HTML
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
name|Hidden
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
name|Image
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
name|ListBox
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
name|TextBox
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|CourseDetailsWidget
extends|extends
name|Composite
block|{
specifier|protected
specifier|static
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
specifier|protected
specifier|static
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
name|GwtResources
name|RESOURCES
init|=
name|GWT
operator|.
name|create
argument_list|(
name|GwtResources
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|FlowPanel
name|iPanel
decl_stmt|;
specifier|private
name|Image
name|iLoadingImage
decl_stmt|;
specifier|private
name|Anchor
name|iAnchor
init|=
literal|null
decl_stmt|;
specifier|private
name|HTML
name|iDetails
init|=
literal|null
decl_stmt|;
specifier|public
name|CourseDetailsWidget
parameter_list|(
name|boolean
name|anchor
parameter_list|)
block|{
name|iPanel
operator|=
operator|new
name|FlowPanel
argument_list|()
expr_stmt|;
name|iLoadingImage
operator|=
operator|new
name|Image
argument_list|(
name|RESOURCES
operator|.
name|loading_small
argument_list|()
argument_list|)
expr_stmt|;
name|iLoadingImage
operator|.
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|iPanel
operator|.
name|add
argument_list|(
name|iLoadingImage
argument_list|)
expr_stmt|;
name|iDetails
operator|=
operator|new
name|HTML
argument_list|(
literal|""
argument_list|)
expr_stmt|;
if|if
condition|(
name|anchor
condition|)
block|{
name|iAnchor
operator|=
operator|new
name|Anchor
argument_list|(
name|MESSAGES
operator|.
name|courseCatalogLink
argument_list|()
argument_list|)
expr_stmt|;
name|iAnchor
operator|.
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|iAnchor
operator|.
name|setTarget
argument_list|(
literal|"_blank"
argument_list|)
expr_stmt|;
name|iPanel
operator|.
name|add
argument_list|(
name|iAnchor
argument_list|)
expr_stmt|;
name|iAnchor
operator|.
name|addMouseOverHandler
argument_list|(
operator|new
name|MouseOverHandler
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onMouseOver
parameter_list|(
name|MouseOverEvent
name|event
parameter_list|)
block|{
if|if
condition|(
operator|!
name|iDetails
operator|.
name|getText
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
name|GwtHint
operator|.
name|showHint
argument_list|(
name|iAnchor
operator|.
name|getElement
argument_list|()
argument_list|,
name|iDetails
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|iAnchor
operator|.
name|addMouseOutHandler
argument_list|(
operator|new
name|MouseOutHandler
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onMouseOut
parameter_list|(
name|MouseOutEvent
name|event
parameter_list|)
block|{
name|GwtHint
operator|.
name|hideHint
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|iDetails
operator|.
name|setStyleName
argument_list|(
literal|"unitime-CourseDetailsPopup"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|iPanel
operator|.
name|add
argument_list|(
name|iDetails
argument_list|)
expr_stmt|;
block|}
name|initWidget
argument_list|(
name|iPanel
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
name|String
name|command
init|=
name|panel
operator|.
name|getElement
argument_list|()
operator|.
name|getInnerText
argument_list|()
operator|.
name|trim
argument_list|()
decl_stmt|;
name|panel
operator|.
name|getElement
argument_list|()
operator|.
name|setInnerText
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|panel
operator|.
name|add
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|panel
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
try|try
block|{
name|reload
argument_list|(
operator|new
name|CourseDetailsRpcRequest
argument_list|(
name|Long
operator|.
name|parseLong
argument_list|(
name|command
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|e
parameter_list|)
block|{
name|Element
name|subjectElement
init|=
name|DOM
operator|.
name|getElementById
argument_list|(
name|command
operator|.
name|split
argument_list|(
literal|","
argument_list|)
index|[
literal|0
index|]
argument_list|)
decl_stmt|;
name|Element
name|courseElement
init|=
name|DOM
operator|.
name|getElementById
argument_list|(
name|command
operator|.
name|split
argument_list|(
literal|","
argument_list|)
index|[
literal|1
index|]
argument_list|)
decl_stmt|;
if|if
condition|(
literal|"select"
operator|.
name|equalsIgnoreCase
argument_list|(
name|subjectElement
operator|.
name|getTagName
argument_list|()
argument_list|)
condition|)
block|{
specifier|final
name|ListBox
name|subjectId
init|=
name|ListBox
operator|.
name|wrap
argument_list|(
name|subjectElement
argument_list|)
decl_stmt|;
specifier|final
name|TextBox
name|courseNumber
init|=
name|TextBox
operator|.
name|wrap
argument_list|(
name|courseElement
argument_list|)
decl_stmt|;
name|courseNumber
operator|.
name|addValueChangeHandler
argument_list|(
operator|new
name|ValueChangeHandler
argument_list|<
name|String
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onValueChange
parameter_list|(
name|ValueChangeEvent
argument_list|<
name|String
argument_list|>
name|event
parameter_list|)
block|{
if|if
condition|(
name|subjectId
operator|.
name|getSelectedIndex
argument_list|()
operator|>=
literal|0
operator|&&
operator|!
name|courseNumber
operator|.
name|getValue
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
try|try
block|{
name|reload
argument_list|(
operator|new
name|CourseDetailsRpcRequest
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|subjectId
operator|.
name|getValue
argument_list|(
name|subjectId
operator|.
name|getSelectedIndex
argument_list|()
argument_list|)
argument_list|)
argument_list|,
name|courseNumber
operator|.
name|getValue
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|e
parameter_list|)
block|{
block|}
block|}
block|}
block|}
argument_list|)
expr_stmt|;
if|if
condition|(
name|subjectId
operator|.
name|getSelectedIndex
argument_list|()
operator|>=
literal|0
operator|&&
operator|!
name|courseNumber
operator|.
name|getValue
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
try|try
block|{
name|reload
argument_list|(
operator|new
name|CourseDetailsRpcRequest
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|subjectId
operator|.
name|getValue
argument_list|(
name|subjectId
operator|.
name|getSelectedIndex
argument_list|()
argument_list|)
argument_list|)
argument_list|,
name|courseNumber
operator|.
name|getValue
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|f
parameter_list|)
block|{
block|}
block|}
block|}
if|else if
condition|(
literal|"input"
operator|.
name|equalsIgnoreCase
argument_list|(
name|courseElement
operator|.
name|getTagName
argument_list|()
argument_list|)
condition|)
block|{
specifier|final
name|Hidden
name|subjectId
init|=
name|Hidden
operator|.
name|wrap
argument_list|(
name|subjectElement
argument_list|)
decl_stmt|;
specifier|final
name|TextBox
name|courseNumber
init|=
name|TextBox
operator|.
name|wrap
argument_list|(
name|courseElement
argument_list|)
decl_stmt|;
name|courseNumber
operator|.
name|addValueChangeHandler
argument_list|(
operator|new
name|ValueChangeHandler
argument_list|<
name|String
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onValueChange
parameter_list|(
name|ValueChangeEvent
argument_list|<
name|String
argument_list|>
name|event
parameter_list|)
block|{
if|if
condition|(
operator|!
name|courseNumber
operator|.
name|getValue
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
try|try
block|{
name|reload
argument_list|(
operator|new
name|CourseDetailsRpcRequest
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|subjectId
operator|.
name|getValue
argument_list|()
argument_list|)
argument_list|,
name|courseNumber
operator|.
name|getValue
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|e
parameter_list|)
block|{
block|}
block|}
block|}
block|}
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|courseNumber
operator|.
name|getValue
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
try|try
block|{
name|reload
argument_list|(
operator|new
name|CourseDetailsRpcRequest
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|subjectId
operator|.
name|getValue
argument_list|()
argument_list|)
argument_list|,
name|courseNumber
operator|.
name|getValue
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|f
parameter_list|)
block|{
block|}
block|}
block|}
else|else
block|{
specifier|final
name|Hidden
name|subjectId
init|=
name|Hidden
operator|.
name|wrap
argument_list|(
name|subjectElement
argument_list|)
decl_stmt|;
specifier|final
name|Hidden
name|courseNumber
init|=
name|Hidden
operator|.
name|wrap
argument_list|(
name|courseElement
argument_list|)
decl_stmt|;
name|reload
argument_list|(
operator|new
name|CourseDetailsRpcRequest
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|subjectId
operator|.
name|getValue
argument_list|()
argument_list|)
argument_list|,
name|courseNumber
operator|.
name|getValue
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
specifier|public
name|void
name|populate
parameter_list|(
name|UniTimeWidget
argument_list|<
name|ListBox
argument_list|>
name|iSubjectArea
parameter_list|,
name|UniTimeWidget
argument_list|<
name|UniTimeTextBox
argument_list|>
name|iCourseNumber
parameter_list|,
name|Long
name|subjectAreaId
parameter_list|)
block|{
if|if
condition|(
name|iSubjectArea
operator|!=
literal|null
condition|)
block|{
specifier|final
name|ListBox
name|subjectId
init|=
name|iSubjectArea
operator|.
name|getWidget
argument_list|()
decl_stmt|;
specifier|final
name|TextBox
name|courseNumber
init|=
name|iCourseNumber
operator|.
name|getWidget
argument_list|()
decl_stmt|;
name|courseNumber
operator|.
name|addValueChangeHandler
argument_list|(
operator|new
name|ValueChangeHandler
argument_list|<
name|String
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onValueChange
parameter_list|(
name|ValueChangeEvent
argument_list|<
name|String
argument_list|>
name|event
parameter_list|)
block|{
if|if
condition|(
name|subjectId
operator|.
name|getSelectedIndex
argument_list|()
operator|>=
literal|0
operator|&&
operator|!
name|courseNumber
operator|.
name|getValue
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
try|try
block|{
name|reload
argument_list|(
operator|new
name|CourseDetailsRpcRequest
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|subjectId
operator|.
name|getValue
argument_list|(
name|subjectId
operator|.
name|getSelectedIndex
argument_list|()
argument_list|)
argument_list|)
argument_list|,
name|courseNumber
operator|.
name|getValue
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|e
parameter_list|)
block|{
block|}
block|}
block|}
block|}
argument_list|)
expr_stmt|;
name|iSubjectArea
operator|.
name|getWidget
argument_list|()
operator|.
name|addChangeHandler
argument_list|(
operator|new
name|ChangeHandler
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onChange
parameter_list|(
name|ChangeEvent
name|event
parameter_list|)
block|{
if|if
condition|(
name|subjectId
operator|.
name|getSelectedIndex
argument_list|()
operator|>=
literal|0
operator|&&
operator|!
name|courseNumber
operator|.
name|getValue
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
try|try
block|{
name|reload
argument_list|(
operator|new
name|CourseDetailsRpcRequest
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|subjectId
operator|.
name|getValue
argument_list|(
name|subjectId
operator|.
name|getSelectedIndex
argument_list|()
argument_list|)
argument_list|)
argument_list|,
name|courseNumber
operator|.
name|getValue
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|e
parameter_list|)
block|{
block|}
block|}
block|}
block|}
argument_list|)
expr_stmt|;
if|if
condition|(
name|subjectId
operator|.
name|getSelectedIndex
argument_list|()
operator|>=
literal|0
operator|&&
operator|!
name|courseNumber
operator|.
name|getValue
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
try|try
block|{
name|reload
argument_list|(
operator|new
name|CourseDetailsRpcRequest
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|subjectId
operator|.
name|getValue
argument_list|(
name|subjectId
operator|.
name|getSelectedIndex
argument_list|()
argument_list|)
argument_list|)
argument_list|,
name|courseNumber
operator|.
name|getValue
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|f
parameter_list|)
block|{
block|}
block|}
block|}
if|else if
condition|(
name|iCourseNumber
operator|!=
literal|null
condition|)
block|{
specifier|final
name|TextBox
name|courseNumber
init|=
name|iCourseNumber
operator|.
name|getWidget
argument_list|()
decl_stmt|;
name|courseNumber
operator|.
name|addValueChangeHandler
argument_list|(
operator|new
name|ValueChangeHandler
argument_list|<
name|String
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onValueChange
parameter_list|(
name|ValueChangeEvent
argument_list|<
name|String
argument_list|>
name|event
parameter_list|)
block|{
if|if
condition|(
operator|!
name|courseNumber
operator|.
name|getValue
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
try|try
block|{
name|reload
argument_list|(
operator|new
name|CourseDetailsRpcRequest
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|subjectAreaId
argument_list|)
argument_list|,
name|courseNumber
operator|.
name|getValue
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|e
parameter_list|)
block|{
block|}
block|}
block|}
block|}
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|courseNumber
operator|.
name|getValue
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
try|try
block|{
name|reload
argument_list|(
operator|new
name|CourseDetailsRpcRequest
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|subjectAreaId
argument_list|)
argument_list|,
name|courseNumber
operator|.
name|getValue
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|f
parameter_list|)
block|{
block|}
block|}
block|}
block|}
specifier|private
name|void
name|reload
parameter_list|(
name|CourseDetailsRpcRequest
name|request
parameter_list|)
block|{
name|iLoadingImage
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
if|if
condition|(
name|iAnchor
operator|!=
literal|null
condition|)
block|{
name|iAnchor
operator|.
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|iDetails
operator|.
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
name|RPC
operator|.
name|execute
argument_list|(
name|request
argument_list|,
operator|new
name|AsyncCallback
argument_list|<
name|CourseDetailsRpcResponse
argument_list|>
argument_list|()
block|{
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
name|iLoadingImage
operator|.
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|onSuccess
parameter_list|(
name|CourseDetailsRpcResponse
name|result
parameter_list|)
block|{
name|iLoadingImage
operator|.
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
if|if
condition|(
name|result
operator|!=
literal|null
condition|)
block|{
name|iDetails
operator|.
name|setHTML
argument_list|(
name|result
operator|.
name|hasDetails
argument_list|()
condition|?
name|result
operator|.
name|getDetails
argument_list|()
else|:
literal|""
argument_list|)
expr_stmt|;
if|if
condition|(
name|iAnchor
operator|!=
literal|null
condition|)
block|{
name|iAnchor
operator|.
name|setHref
argument_list|(
name|result
operator|.
name|getLink
argument_list|()
argument_list|)
expr_stmt|;
name|iAnchor
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|iDetails
operator|.
name|setVisible
argument_list|(
name|result
operator|.
name|hasDetails
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
class|class
name|CourseDetailsRpcRequest
implements|implements
name|GwtRpcRequest
argument_list|<
name|CourseDetailsRpcResponse
argument_list|>
block|{
specifier|private
name|Long
name|iCourseId
init|=
literal|null
decl_stmt|;
specifier|private
name|Long
name|iSubjectId
init|=
literal|null
decl_stmt|;
specifier|private
name|String
name|iCourseNumber
init|=
literal|null
decl_stmt|;
specifier|public
name|CourseDetailsRpcRequest
parameter_list|()
block|{
block|}
specifier|public
name|CourseDetailsRpcRequest
parameter_list|(
name|Long
name|courseId
parameter_list|)
block|{
name|setCourseId
argument_list|(
name|courseId
argument_list|)
expr_stmt|;
block|}
specifier|public
name|CourseDetailsRpcRequest
parameter_list|(
name|Long
name|subjectId
parameter_list|,
name|String
name|courseNbr
parameter_list|)
block|{
name|setSubjectId
argument_list|(
name|subjectId
argument_list|)
expr_stmt|;
name|setCourseNumber
argument_list|(
name|courseNbr
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Long
name|getCourseId
parameter_list|()
block|{
return|return
name|iCourseId
return|;
block|}
specifier|public
name|void
name|setCourseId
parameter_list|(
name|Long
name|courseId
parameter_list|)
block|{
name|iCourseId
operator|=
name|courseId
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasCourseId
parameter_list|()
block|{
return|return
name|iCourseId
operator|!=
literal|null
return|;
block|}
specifier|public
name|Long
name|getSubjectId
parameter_list|()
block|{
return|return
name|iSubjectId
return|;
block|}
specifier|public
name|void
name|setSubjectId
parameter_list|(
name|Long
name|subjectId
parameter_list|)
block|{
name|iSubjectId
operator|=
name|subjectId
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasSubjectId
parameter_list|()
block|{
return|return
name|iSubjectId
operator|!=
literal|null
return|;
block|}
specifier|public
name|String
name|getCourseNumber
parameter_list|()
block|{
return|return
name|iCourseNumber
return|;
block|}
specifier|public
name|void
name|setCourseNumber
parameter_list|(
name|String
name|courseNbr
parameter_list|)
block|{
name|iCourseNumber
operator|=
name|courseNbr
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasCourseNumber
parameter_list|()
block|{
return|return
name|iCourseNumber
operator|!=
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
if|if
condition|(
name|hasCourseId
argument_list|()
condition|)
return|return
name|getCourseId
argument_list|()
operator|.
name|toString
argument_list|()
return|;
else|else
return|return
name|getSubjectId
argument_list|()
operator|+
literal|" "
operator|+
name|getCourseNumber
argument_list|()
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|CourseDetailsRpcResponse
implements|implements
name|GwtRpcResponse
block|{
specifier|private
name|String
name|iLink
init|=
literal|null
decl_stmt|;
specifier|private
name|String
name|iDetails
init|=
literal|null
decl_stmt|;
specifier|public
name|CourseDetailsRpcResponse
parameter_list|()
block|{
block|}
specifier|public
name|String
name|getLink
parameter_list|()
block|{
return|return
name|iLink
return|;
block|}
specifier|public
name|void
name|setLink
parameter_list|(
name|String
name|link
parameter_list|)
block|{
name|iLink
operator|=
name|link
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasLink
parameter_list|()
block|{
return|return
name|iLink
operator|!=
literal|null
operator|&&
operator|!
name|iLink
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|String
name|getDetails
parameter_list|()
block|{
return|return
name|iDetails
return|;
block|}
specifier|public
name|void
name|setDetails
parameter_list|(
name|String
name|details
parameter_list|)
block|{
name|iDetails
operator|=
name|details
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasDetails
parameter_list|()
block|{
return|return
name|iDetails
operator|!=
literal|null
operator|&&
operator|!
name|iDetails
operator|.
name|isEmpty
argument_list|()
return|;
block|}
block|}
block|}
end_class

end_unit

