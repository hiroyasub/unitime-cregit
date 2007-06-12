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

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|AbstractCollection
import|;
end_import

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
name|Iterator
import|;
end_import

begin_comment
comment|/**  * @author Stephanie Schluttenhofer  *  * TODO To change the template for this generated type comment go to  * Window - Preferences - Java - Code Style - Code Templates  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|GeneralHtml
block|{
comment|/** 	 *  	 */
specifier|public
name|GeneralHtml
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
name|this
operator|.
name|setContents
argument_list|(
operator|new
name|ArrayList
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|private
name|String
name|styleClass
decl_stmt|;
specifier|private
name|String
name|style
decl_stmt|;
specifier|private
name|String
name|title
decl_stmt|;
specifier|private
name|String
name|id
decl_stmt|;
specifier|private
name|String
name|dir
decl_stmt|;
specifier|private
name|String
name|lang
decl_stmt|;
comment|// subclasses should override and set tag equal to the tag used in their html
specifier|private
name|String
name|tag
decl_stmt|;
specifier|private
name|ArrayList
name|contents
decl_stmt|;
comment|/** 	 * @return Returns the contents. 	 */
specifier|public
name|ArrayList
name|getContents
parameter_list|()
block|{
return|return
name|contents
return|;
block|}
comment|/** 	 * @param contents The contents to set. 	 */
specifier|public
name|void
name|setContents
parameter_list|(
name|ArrayList
name|contents
parameter_list|)
block|{
name|this
operator|.
name|contents
operator|=
name|contents
expr_stmt|;
block|}
specifier|public
name|void
name|addContent
parameter_list|(
name|Object
name|obj
parameter_list|)
block|{
name|this
operator|.
name|getContents
argument_list|()
operator|.
name|add
argument_list|(
name|obj
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * @return Returns the id. 	 */
specifier|public
name|String
name|getId
parameter_list|()
block|{
return|return
name|id
return|;
block|}
comment|/** 	 * @param id The id to set. 	 */
specifier|public
name|void
name|setId
parameter_list|(
name|String
name|id
parameter_list|)
block|{
name|this
operator|.
name|id
operator|=
name|id
expr_stmt|;
block|}
comment|/** 	 * @return Returns the style. 	 */
specifier|public
name|String
name|getStyle
parameter_list|()
block|{
return|return
name|style
return|;
block|}
comment|/** 	 * @param style The style to set. 	 */
specifier|public
name|void
name|setStyle
parameter_list|(
name|String
name|style
parameter_list|)
block|{
name|this
operator|.
name|style
operator|=
name|style
expr_stmt|;
block|}
comment|/** 	 * @return Returns the styleClass. 	 */
specifier|public
name|String
name|getStyleClass
parameter_list|()
block|{
return|return
name|styleClass
return|;
block|}
comment|/** 	 * @param styleClass The styleClass to set. 	 */
specifier|public
name|void
name|setStyleClass
parameter_list|(
name|String
name|styleClass
parameter_list|)
block|{
name|this
operator|.
name|styleClass
operator|=
name|styleClass
expr_stmt|;
block|}
comment|/** 	 * @return Returns the title. 	 */
specifier|public
name|String
name|getTitle
parameter_list|()
block|{
return|return
name|title
return|;
block|}
comment|/** 	 * @param title The title to set. 	 */
specifier|public
name|void
name|setTitle
parameter_list|(
name|String
name|title
parameter_list|)
block|{
name|this
operator|.
name|title
operator|=
name|title
expr_stmt|;
block|}
comment|/** 	 * @return Returns the tag. 	 */
specifier|public
name|String
name|getTag
parameter_list|()
block|{
return|return
name|tag
return|;
block|}
comment|/** 	 * @param tag The tag to set. 	 */
specifier|protected
name|void
name|setTag
parameter_list|(
name|String
name|tag
parameter_list|)
block|{
name|this
operator|.
name|tag
operator|=
name|tag
expr_stmt|;
block|}
specifier|public
name|String
name|startTagHtml
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
literal|"<"
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|this
operator|.
name|getTag
argument_list|()
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|this
operator|.
name|htmlForAttributes
argument_list|()
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|">\n"
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
specifier|public
name|String
name|endTagHtml
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
literal|"</"
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|this
operator|.
name|getTag
argument_list|()
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|">\n"
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
specifier|public
name|String
name|toHtml
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
name|startTagHtml
argument_list|()
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|this
operator|.
name|contentHtml
argument_list|()
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|this
operator|.
name|endTagHtml
argument_list|()
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
comment|/** 	 * @return html for any non null attributes and not empty string attributes 	 */
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
name|this
operator|.
name|htmlForAttribute
argument_list|(
literal|"class"
argument_list|,
name|this
operator|.
name|getStyleClass
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
literal|"style"
argument_list|,
name|this
operator|.
name|getStyle
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
literal|"id"
argument_list|,
name|this
operator|.
name|getId
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
literal|"title"
argument_list|,
name|this
operator|.
name|getTitle
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
literal|"dir"
argument_list|,
name|this
operator|.
name|getDir
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
literal|"lang"
argument_list|,
name|this
operator|.
name|getLang
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
specifier|protected
name|String
name|htmlForObject
parameter_list|(
name|Object
name|obj
parameter_list|)
block|{
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
if|if
condition|(
name|obj
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|GeneralHtml
operator|.
name|class
operator|.
name|isInstance
argument_list|(
name|obj
argument_list|)
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
operator|(
operator|(
name|GeneralHtml
operator|)
name|obj
operator|)
operator|.
name|toHtml
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|AbstractCollection
operator|.
name|class
operator|.
name|isInstance
argument_list|(
name|obj
argument_list|)
condition|)
block|{
name|Iterator
name|colIt
init|=
operator|(
operator|(
name|AbstractCollection
operator|)
name|obj
operator|)
operator|.
name|iterator
argument_list|()
decl_stmt|;
while|while
condition|(
name|colIt
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|this
operator|.
name|htmlForObject
argument_list|(
name|colIt
operator|.
name|next
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|sb
operator|.
name|append
argument_list|(
name|obj
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
return|return
operator|(
name|sb
operator|.
name|toString
argument_list|()
operator|)
return|;
block|}
comment|/** 	 * @return html for any contents of this html container 	 */
specifier|protected
name|String
name|contentHtml
parameter_list|()
block|{
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|Iterator
name|it
init|=
name|this
operator|.
name|getContents
argument_list|()
operator|.
name|iterator
argument_list|()
decl_stmt|;
while|while
condition|(
name|it
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|htmlForObject
argument_list|(
name|it
operator|.
name|next
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
operator|(
name|sb
operator|.
name|toString
argument_list|()
operator|)
return|;
block|}
specifier|protected
name|String
name|htmlForAttribute
parameter_list|(
name|String
name|attributeName
parameter_list|,
name|String
name|attributeValue
parameter_list|)
block|{
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
if|if
condition|(
name|attributeName
operator|!=
literal|null
operator|&&
name|attributeName
operator|.
name|length
argument_list|()
operator|!=
literal|0
operator|&&
name|attributeValue
operator|!=
literal|null
operator|&&
name|attributeValue
operator|.
name|length
argument_list|()
operator|!=
literal|0
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|" "
operator|+
name|attributeName
operator|+
literal|"="
operator|+
literal|"\""
operator|+
name|attributeValue
operator|+
literal|"\""
argument_list|)
expr_stmt|;
block|}
return|return
operator|(
name|sb
operator|.
name|toString
argument_list|()
operator|)
return|;
block|}
specifier|protected
name|String
name|htmlForAttribute
parameter_list|(
name|String
name|attributeName
parameter_list|,
name|boolean
name|attributeValue
parameter_list|)
block|{
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
if|if
condition|(
name|attributeName
operator|!=
literal|null
operator|&&
name|attributeName
operator|.
name|length
argument_list|()
operator|!=
literal|0
operator|&&
name|attributeValue
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|" "
operator|+
name|attributeName
argument_list|)
expr_stmt|;
block|}
return|return
operator|(
name|sb
operator|.
name|toString
argument_list|()
operator|)
return|;
block|}
specifier|protected
name|String
name|htmlForAttribute
parameter_list|(
name|String
name|attributeName
parameter_list|,
name|int
name|attributeValue
parameter_list|)
block|{
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
if|if
condition|(
name|attributeName
operator|!=
literal|null
operator|&&
name|attributeName
operator|.
name|length
argument_list|()
operator|!=
literal|0
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|" "
operator|+
name|attributeName
operator|+
literal|"="
operator|+
literal|"\""
operator|+
name|Integer
operator|.
name|toString
argument_list|(
name|attributeValue
argument_list|)
operator|+
literal|"\""
argument_list|)
expr_stmt|;
block|}
return|return
operator|(
name|sb
operator|.
name|toString
argument_list|()
operator|)
return|;
block|}
comment|/** 	 * @return Returns the dir. 	 */
specifier|public
name|String
name|getDir
parameter_list|()
block|{
return|return
name|dir
return|;
block|}
comment|/** 	 * @param dir The dir to set. 	 */
specifier|public
name|void
name|setDir
parameter_list|(
name|String
name|dir
parameter_list|)
block|{
name|this
operator|.
name|dir
operator|=
name|dir
expr_stmt|;
block|}
comment|/** 	 * @return Returns the lang. 	 */
specifier|public
name|String
name|getLang
parameter_list|()
block|{
return|return
name|lang
return|;
block|}
comment|/** 	 * @param lang The lang to set. 	 */
specifier|public
name|void
name|setLang
parameter_list|(
name|String
name|lang
parameter_list|)
block|{
name|this
operator|.
name|lang
operator|=
name|lang
expr_stmt|;
block|}
block|}
end_class

end_unit

