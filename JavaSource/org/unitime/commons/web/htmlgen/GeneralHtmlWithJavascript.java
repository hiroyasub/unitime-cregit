begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.0 (University Course Timetabling& Student Sectioning Application)  * Copyright (C) 2007, UniTime.org, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program; if not, write to the Free Software Foundation, Inc.,  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
comment|/**  * @author Stephanie Schluttenhofer  *  * TODO To change the template for this generated type comment go to  * Window - Preferences - Java - Code Style - Code Templates  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|GeneralHtmlWithJavascript
extends|extends
name|GeneralHtml
block|{
comment|/** 	 *  	 */
specifier|public
name|GeneralHtmlWithJavascript
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
specifier|private
name|String
name|onClick
decl_stmt|;
specifier|private
name|String
name|onDblClick
decl_stmt|;
specifier|private
name|String
name|onHelp
decl_stmt|;
specifier|private
name|String
name|onKeyDown
decl_stmt|;
specifier|private
name|String
name|onKeyPress
decl_stmt|;
specifier|private
name|String
name|onKeyUp
decl_stmt|;
specifier|private
name|String
name|onMouseDown
decl_stmt|;
specifier|private
name|String
name|onMouseMove
decl_stmt|;
specifier|private
name|String
name|onMouseOut
decl_stmt|;
specifier|private
name|String
name|onMouseOver
decl_stmt|;
specifier|private
name|String
name|onMouseUp
decl_stmt|;
comment|/** 	 * @return Returns the onClick. 	 */
specifier|public
name|String
name|getOnClick
parameter_list|()
block|{
return|return
name|onClick
return|;
block|}
comment|/** 	 * @param onClick The onClick to set. 	 */
specifier|public
name|void
name|setOnClick
parameter_list|(
name|String
name|onClick
parameter_list|)
block|{
name|this
operator|.
name|onClick
operator|=
name|onClick
expr_stmt|;
block|}
comment|/** 	 * @return Returns the onDblClick. 	 */
specifier|public
name|String
name|getOnDblClick
parameter_list|()
block|{
return|return
name|onDblClick
return|;
block|}
comment|/** 	 * @param onDblClick The onDblClick to set. 	 */
specifier|public
name|void
name|setOnDblClick
parameter_list|(
name|String
name|onDblClick
parameter_list|)
block|{
name|this
operator|.
name|onDblClick
operator|=
name|onDblClick
expr_stmt|;
block|}
comment|/** 	 * @return Returns the onHelp. 	 */
specifier|public
name|String
name|getOnHelp
parameter_list|()
block|{
return|return
name|onHelp
return|;
block|}
comment|/** 	 * @param onHelp The onHelp to set. 	 */
specifier|public
name|void
name|setOnHelp
parameter_list|(
name|String
name|onHelp
parameter_list|)
block|{
name|this
operator|.
name|onHelp
operator|=
name|onHelp
expr_stmt|;
block|}
comment|/** 	 * @return Returns the onKeyDown. 	 */
specifier|public
name|String
name|getOnKeyDown
parameter_list|()
block|{
return|return
name|onKeyDown
return|;
block|}
comment|/** 	 * @param onKeyDown The onKeyDown to set. 	 */
specifier|public
name|void
name|setOnKeyDown
parameter_list|(
name|String
name|onKeyDown
parameter_list|)
block|{
name|this
operator|.
name|onKeyDown
operator|=
name|onKeyDown
expr_stmt|;
block|}
comment|/** 	 * @return Returns the onKeyPress. 	 */
specifier|public
name|String
name|getOnKeyPress
parameter_list|()
block|{
return|return
name|onKeyPress
return|;
block|}
comment|/** 	 * @param onKeyPress The onKeyPress to set. 	 */
specifier|public
name|void
name|setOnKeyPress
parameter_list|(
name|String
name|onKeyPress
parameter_list|)
block|{
name|this
operator|.
name|onKeyPress
operator|=
name|onKeyPress
expr_stmt|;
block|}
comment|/** 	 * @return Returns the onKeyUp. 	 */
specifier|public
name|String
name|getOnKeyUp
parameter_list|()
block|{
return|return
name|onKeyUp
return|;
block|}
comment|/** 	 * @param onKeyUp The onKeyUp to set. 	 */
specifier|public
name|void
name|setOnKeyUp
parameter_list|(
name|String
name|onKeyUp
parameter_list|)
block|{
name|this
operator|.
name|onKeyUp
operator|=
name|onKeyUp
expr_stmt|;
block|}
comment|/** 	 * @return Returns the onMouseDown. 	 */
specifier|public
name|String
name|getOnMouseDown
parameter_list|()
block|{
return|return
name|onMouseDown
return|;
block|}
comment|/** 	 * @param onMouseDown The onMouseDown to set. 	 */
specifier|public
name|void
name|setOnMouseDown
parameter_list|(
name|String
name|onMouseDown
parameter_list|)
block|{
name|this
operator|.
name|onMouseDown
operator|=
name|onMouseDown
expr_stmt|;
block|}
comment|/** 	 * @return Returns the onMouseMove. 	 */
specifier|public
name|String
name|getOnMouseMove
parameter_list|()
block|{
return|return
name|onMouseMove
return|;
block|}
comment|/** 	 * @param onMouseMove The onMouseMove to set. 	 */
specifier|public
name|void
name|setOnMouseMove
parameter_list|(
name|String
name|onMouseMove
parameter_list|)
block|{
name|this
operator|.
name|onMouseMove
operator|=
name|onMouseMove
expr_stmt|;
block|}
comment|/** 	 * @return Returns the onMouseOut. 	 */
specifier|public
name|String
name|getOnMouseOut
parameter_list|()
block|{
return|return
name|onMouseOut
return|;
block|}
comment|/** 	 * @param onMouseOut The onMouseOut to set. 	 */
specifier|public
name|void
name|setOnMouseOut
parameter_list|(
name|String
name|onMouseOut
parameter_list|)
block|{
name|this
operator|.
name|onMouseOut
operator|=
name|onMouseOut
expr_stmt|;
block|}
comment|/** 	 * @return Returns the onMouseOver. 	 */
specifier|public
name|String
name|getOnMouseOver
parameter_list|()
block|{
return|return
name|onMouseOver
return|;
block|}
comment|/** 	 * @param onMouseOver The onMouseOver to set. 	 */
specifier|public
name|void
name|setOnMouseOver
parameter_list|(
name|String
name|onMouseOver
parameter_list|)
block|{
name|this
operator|.
name|onMouseOver
operator|=
name|onMouseOver
expr_stmt|;
block|}
comment|/** 	 * @return Returns the onMouseUp. 	 */
specifier|public
name|String
name|getOnMouseUp
parameter_list|()
block|{
return|return
name|onMouseUp
return|;
block|}
comment|/** 	 * @param onMouseUp The onMouseUp to set. 	 */
specifier|public
name|void
name|setOnMouseUp
parameter_list|(
name|String
name|onMouseUp
parameter_list|)
block|{
name|this
operator|.
name|onMouseUp
operator|=
name|onMouseUp
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
literal|"onclick"
argument_list|,
name|this
operator|.
name|getOnClick
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
literal|"ondblclick"
argument_list|,
name|this
operator|.
name|getOnDblClick
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
literal|"onhelp"
argument_list|,
name|this
operator|.
name|getOnHelp
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
literal|"onkeydown"
argument_list|,
name|this
operator|.
name|getOnKeyDown
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
literal|"onkeypress"
argument_list|,
name|this
operator|.
name|getOnKeyPress
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
literal|"onkeyup"
argument_list|,
name|this
operator|.
name|getOnKeyUp
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
literal|"onmousedown"
argument_list|,
name|this
operator|.
name|getOnMouseDown
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
literal|"onmousemove"
argument_list|,
name|this
operator|.
name|getOnMouseMove
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
literal|"onmouseout"
argument_list|,
name|this
operator|.
name|getOnMouseOut
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
literal|"onmouseover"
argument_list|,
name|this
operator|.
name|getOnMouseOver
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
literal|"onmouseup"
argument_list|,
name|this
operator|.
name|getOnMouseUp
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

