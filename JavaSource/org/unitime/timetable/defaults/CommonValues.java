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
name|defaults
package|;
end_package

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_enum
specifier|public
enum|enum
name|CommonValues
block|{
name|NoteAsIcon
argument_list|(
literal|"icon"
argument_list|)
block|,
name|NoteAsFullText
argument_list|(
literal|"full text"
argument_list|)
block|,
name|NoteAsShortText
argument_list|(
literal|"shortened text"
argument_list|)
block|,
name|HorizontalGrid
argument_list|(
literal|"horizontal"
argument_list|)
block|,
name|VerticalGrid
argument_list|(
literal|"vertical"
argument_list|)
block|,
name|TextGrid
argument_list|(
literal|"text"
argument_list|)
block|,
name|NameLastFirst
argument_list|(
literal|"last-first"
argument_list|)
block|,
name|NameFirstLast
argument_list|(
literal|"first-last"
argument_list|)
block|,
name|NameInitialLast
argument_list|(
literal|"initial-last"
argument_list|)
block|,
name|NameLastInitial
argument_list|(
literal|"last-initial"
argument_list|)
block|,
name|NameFirstMiddleLast
argument_list|(
literal|"first-middle-last"
argument_list|)
block|,
name|NameShort
argument_list|(
literal|"short"
argument_list|)
block|,
name|Yes
argument_list|(
literal|"yes"
argument_list|)
block|,
name|No
argument_list|(
literal|"no"
argument_list|)
block|,
name|Ask
argument_list|(
literal|"ask"
argument_list|)
block|,
name|Always
argument_list|(
literal|"always"
argument_list|)
block|,
name|Never
argument_list|(
literal|"never"
argument_list|)
block|,
name|SortByLastName
argument_list|(
literal|"Always by Last Name"
argument_list|)
block|,
name|SortAsDisplayed
argument_list|(
literal|"Natural Order (as displayed)"
argument_list|)
block|, 	 	;
name|String
name|iValue
decl_stmt|;
name|CommonValues
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
name|value
parameter_list|()
block|{
return|return
name|iValue
return|;
block|}
specifier|public
name|boolean
name|eq
parameter_list|(
name|String
name|value
parameter_list|)
block|{
return|return
name|iValue
operator|.
name|equals
argument_list|(
name|value
argument_list|)
return|;
block|}
specifier|public
name|boolean
name|ne
parameter_list|(
name|String
name|value
parameter_list|)
block|{
return|return
operator|!
name|eq
argument_list|(
name|value
argument_list|)
return|;
block|}
block|}
end_enum

end_unit

