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
name|List
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
name|History
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

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|UniTimeBack
block|{
specifier|private
name|String
name|iBackUrl
init|=
literal|null
decl_stmt|;
specifier|private
name|List
argument_list|<
name|String
index|[]
argument_list|>
name|iBacks
init|=
operator|new
name|ArrayList
argument_list|<
name|String
index|[]
argument_list|>
argument_list|()
decl_stmt|;
specifier|public
name|UniTimeBack
parameter_list|()
block|{
name|History
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
name|event
operator|.
name|getValue
argument_list|()
operator|==
literal|null
operator|||
name|event
operator|.
name|getValue
argument_list|()
operator|.
name|isEmpty
argument_list|()
operator|||
literal|"back"
operator|.
name|equals
argument_list|(
name|event
operator|.
name|getValue
argument_list|()
argument_list|)
condition|)
block|{
name|open
argument_list|(
name|GWT
operator|.
name|getHostPageBaseURL
argument_list|()
operator|+
literal|"back.do?uri="
operator|+
name|iBackUrl
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|String
name|uri
init|=
name|token2uri
argument_list|(
name|event
operator|.
name|getValue
argument_list|()
operator|.
name|replace
argument_list|(
literal|"%20"
argument_list|,
literal|" "
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|uri
operator|!=
literal|null
condition|)
name|open
argument_list|(
name|GWT
operator|.
name|getHostPageBaseURL
argument_list|()
operator|+
literal|"back.do?uri="
operator|+
name|uri
argument_list|)
expr_stmt|;
block|}
block|}
block|}
argument_list|)
expr_stmt|;
block|}
specifier|private
specifier|native
name|void
name|open
parameter_list|(
name|String
name|url
parameter_list|)
comment|/*-{ 		$wnd.location = url; 	}-*/
function_decl|;
specifier|private
name|String
name|token2uri
parameter_list|(
name|String
name|token
parameter_list|)
block|{
for|for
control|(
name|String
index|[]
name|back
range|:
name|iBacks
control|)
block|{
if|if
condition|(
name|back
index|[
literal|1
index|]
operator|.
name|equals
argument_list|(
name|token
argument_list|)
condition|)
return|return
name|back
index|[
literal|0
index|]
return|;
block|}
return|return
literal|null
return|;
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
name|backs
init|=
name|panel
operator|.
name|getElement
argument_list|()
operator|.
name|getInnerText
argument_list|()
decl_stmt|;
name|iBacks
operator|.
name|clear
argument_list|()
expr_stmt|;
for|for
control|(
name|String
name|back
range|:
name|backs
operator|.
name|split
argument_list|(
literal|"\\&"
argument_list|)
control|)
block|{
name|String
index|[]
name|b
init|=
name|back
operator|.
name|split
argument_list|(
literal|"\\|"
argument_list|)
decl_stmt|;
name|iBacks
operator|.
name|add
argument_list|(
name|b
argument_list|)
expr_stmt|;
if|if
condition|(
name|History
operator|.
name|getToken
argument_list|()
operator|==
literal|null
operator|||
name|History
operator|.
name|getToken
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
name|History
operator|.
name|newItem
argument_list|(
name|b
index|[
literal|1
index|]
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|iBacks
operator|.
name|isEmpty
argument_list|()
condition|)
return|return;
name|int
name|back
init|=
literal|2
decl_stmt|;
name|String
name|lastUrl
init|=
name|iBacks
operator|.
name|get
argument_list|(
name|iBacks
operator|.
name|size
argument_list|()
operator|-
literal|1
argument_list|)
index|[
literal|0
index|]
decl_stmt|;
if|if
condition|(
name|lastUrl
operator|.
name|indexOf
argument_list|(
literal|'%'
argument_list|)
operator|>=
literal|0
condition|)
name|lastUrl
operator|=
name|lastUrl
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|lastUrl
operator|.
name|indexOf
argument_list|(
literal|'%'
argument_list|)
argument_list|)
expr_stmt|;
name|String
name|currentUrl
init|=
name|Window
operator|.
name|Location
operator|.
name|getHref
argument_list|()
decl_stmt|;
if|if
condition|(
name|currentUrl
operator|.
name|indexOf
argument_list|(
literal|'/'
argument_list|)
operator|>=
literal|0
condition|)
name|currentUrl
operator|=
name|currentUrl
operator|.
name|substring
argument_list|(
name|currentUrl
operator|.
name|lastIndexOf
argument_list|(
literal|'/'
argument_list|)
operator|+
literal|1
argument_list|)
expr_stmt|;
if|if
condition|(
name|currentUrl
operator|.
name|indexOf
argument_list|(
literal|'?'
argument_list|)
operator|>=
literal|0
condition|)
name|currentUrl
operator|=
name|currentUrl
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|currentUrl
operator|.
name|lastIndexOf
argument_list|(
literal|'?'
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|currentUrl
operator|.
name|indexOf
argument_list|(
literal|'#'
argument_list|)
operator|>=
literal|0
condition|)
name|currentUrl
operator|=
name|currentUrl
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|currentUrl
operator|.
name|lastIndexOf
argument_list|(
literal|'#'
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|lastUrl
operator|.
name|equals
argument_list|(
name|currentUrl
argument_list|)
operator|||
name|iBacks
operator|.
name|size
argument_list|()
operator|<
literal|2
condition|)
block|{
name|back
operator|=
literal|1
expr_stmt|;
if|if
condition|(
name|History
operator|.
name|getToken
argument_list|()
operator|==
literal|null
operator|||
name|History
operator|.
name|getToken
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
name|History
operator|.
name|newItem
argument_list|(
literal|"back"
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
name|iBackUrl
operator|=
name|iBacks
operator|.
name|get
argument_list|(
name|iBacks
operator|.
name|size
argument_list|()
operator|-
name|back
argument_list|)
index|[
literal|0
index|]
expr_stmt|;
block|}
block|}
end_class

end_unit

