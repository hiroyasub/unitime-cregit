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
name|widgets
package|;
end_package

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
name|shared
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
name|Window
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|UniTimeFrameDialog
block|{
specifier|private
specifier|static
name|UniTimeFrameDialogDisplay
name|sDialog
init|=
literal|null
decl_stmt|;
specifier|public
specifier|static
specifier|native
name|void
name|createTriggers
parameter_list|()
comment|/*-{ 		$wnd.showGwtDialog = function(title, source, width, height) { 			@org.unitime.timetable.gwt.client.widgets.UniTimeFrameDialog::openDialog(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)(title, source, width, height); 		}; 		$wnd.hideGwtDialog = function() { 			@org.unitime.timetable.gwt.client.widgets.UniTimeFrameDialog::hideDialog()(); 		}; 		$wnd.hasGwtDialog = function() { 			return @org.unitime.timetable.gwt.client.widgets.UniTimeFrameDialog::hasDialog()(); 		}; 	}-*/
function_decl|;
specifier|public
specifier|static
name|void
name|openDialog
parameter_list|(
name|String
name|title
parameter_list|,
name|String
name|source
parameter_list|)
block|{
name|openDialog
argument_list|(
name|title
argument_list|,
name|source
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|void
name|openDialog
parameter_list|(
name|String
name|title
parameter_list|,
name|String
name|source
parameter_list|,
name|String
name|width
parameter_list|,
name|String
name|height
parameter_list|)
block|{
if|if
condition|(
name|sDialog
operator|==
literal|null
condition|)
block|{
if|if
condition|(
name|Window
operator|.
name|getClientWidth
argument_list|()
operator|<=
literal|800
condition|)
name|sDialog
operator|=
name|GWT
operator|.
name|create
argument_list|(
name|UniTimeFrameDialogDisplay
operator|.
name|Mobile
operator|.
name|class
argument_list|)
expr_stmt|;
else|else
name|sDialog
operator|=
name|GWT
operator|.
name|create
argument_list|(
name|UniTimeFrameDialogDisplay
operator|.
name|class
argument_list|)
expr_stmt|;
block|}
name|sDialog
operator|.
name|openDialog
argument_list|(
name|title
argument_list|,
name|source
argument_list|,
name|width
argument_list|,
name|height
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|void
name|hideDialog
parameter_list|()
block|{
if|if
condition|(
name|sDialog
operator|!=
literal|null
operator|&&
name|sDialog
operator|.
name|isShowing
argument_list|()
condition|)
name|sDialog
operator|.
name|hideDialog
argument_list|()
expr_stmt|;
block|}
specifier|public
specifier|static
name|boolean
name|hasDialog
parameter_list|()
block|{
return|return
name|sDialog
operator|!=
literal|null
operator|&&
name|sDialog
operator|.
name|isShowing
argument_list|()
return|;
block|}
block|}
end_class

end_unit

