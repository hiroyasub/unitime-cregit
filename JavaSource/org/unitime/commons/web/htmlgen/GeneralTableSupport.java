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
name|commons
operator|.
name|web
operator|.
name|htmlgen
package|;
end_package

begin_comment
comment|/**  * @author Stephanie Schluttenhofer  *  * Window - Preferences - Java - Code Style - Code Templates  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|GeneralTableSupport
extends|extends
name|GeneralHtmlWithJavascript
block|{
comment|/** 	 *  	 */
specifier|public
name|GeneralTableSupport
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
specifier|private
name|String
name|align
decl_stmt|;
specifier|private
name|String
name|background
decl_stmt|;
specifier|private
name|String
name|bgColor
decl_stmt|;
specifier|private
name|String
name|borderColor
decl_stmt|;
specifier|private
name|String
name|height
decl_stmt|;
comment|/** 	 * @return Returns the align. 	 */
specifier|public
name|String
name|getAlign
parameter_list|()
block|{
return|return
name|align
return|;
block|}
comment|/** 	 * @param align The align to set. 	 */
specifier|public
name|void
name|setAlign
parameter_list|(
name|String
name|align
parameter_list|)
block|{
name|this
operator|.
name|align
operator|=
name|align
expr_stmt|;
block|}
comment|/** 	 * @return Returns the background. 	 */
specifier|public
name|String
name|getBackground
parameter_list|()
block|{
return|return
name|background
return|;
block|}
comment|/** 	 * @param background The background to set. 	 */
specifier|public
name|void
name|setBackground
parameter_list|(
name|String
name|background
parameter_list|)
block|{
name|this
operator|.
name|background
operator|=
name|background
expr_stmt|;
block|}
comment|/** 	 * @return Returns the bgColor. 	 */
specifier|public
name|String
name|getBgColor
parameter_list|()
block|{
return|return
name|bgColor
return|;
block|}
comment|/** 	 * @param bgColor The bgColor to set. 	 */
specifier|public
name|void
name|setBgColor
parameter_list|(
name|String
name|bgColor
parameter_list|)
block|{
name|this
operator|.
name|bgColor
operator|=
name|bgColor
expr_stmt|;
block|}
comment|/** 	 * @return Returns the borderColor. 	 */
specifier|public
name|String
name|getBorderColor
parameter_list|()
block|{
return|return
name|borderColor
return|;
block|}
comment|/** 	 * @param borderColor The borderColor to set. 	 */
specifier|public
name|void
name|setBorderColor
parameter_list|(
name|String
name|borderColor
parameter_list|)
block|{
name|this
operator|.
name|borderColor
operator|=
name|borderColor
expr_stmt|;
block|}
comment|/** 	 * @return Returns the height. 	 */
specifier|public
name|String
name|getHeight
parameter_list|()
block|{
return|return
name|height
return|;
block|}
comment|/** 	 * @param height The height to set. 	 */
specifier|public
name|void
name|setHeight
parameter_list|(
name|String
name|height
parameter_list|)
block|{
name|this
operator|.
name|height
operator|=
name|height
expr_stmt|;
block|}
specifier|protected
name|String
name|htmlForAttributes
parameter_list|()
block|{
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|super
operator|.
name|htmlForAttributes
argument_list|()
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|this
operator|.
name|htmlForAttribute
argument_list|(
literal|"align"
argument_list|,
name|this
operator|.
name|getAlign
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|this
operator|.
name|htmlForAttribute
argument_list|(
literal|"background"
argument_list|,
name|this
operator|.
name|getBackground
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|this
operator|.
name|htmlForAttribute
argument_list|(
literal|"bgcolor"
argument_list|,
name|this
operator|.
name|getBgColor
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|this
operator|.
name|htmlForAttribute
argument_list|(
literal|"bordercolor"
argument_list|,
name|this
operator|.
name|getBorderColor
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|this
operator|.
name|htmlForAttribute
argument_list|(
literal|"height"
argument_list|,
name|this
operator|.
name|getHeight
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
return|return
operator|(
name|sb
operator|.
name|toString
argument_list|()
operator|)
return|;
block|}
block|}
end_class

end_unit

