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
name|timetable
operator|.
name|solver
operator|.
name|ui
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|Serializable
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|*
import|;
end_import

begin_import
import|import
name|org
operator|.
name|dom4j
operator|.
name|Element
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|cpsolver
operator|.
name|ifs
operator|.
name|util
operator|.
name|Progress
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|LogInfo
implements|implements
name|TimetableInfo
implements|,
name|Serializable
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
specifier|public
specifier|static
name|int
name|sVersion
init|=
literal|1
decl_stmt|;
comment|// to be able to do some changes in the future
specifier|public
specifier|static
name|int
name|sNoSaveThreshold
init|=
name|Progress
operator|.
name|MSGLEVEL_DEBUG
decl_stmt|;
specifier|private
name|Vector
name|iLog
init|=
operator|new
name|Vector
argument_list|()
decl_stmt|;
specifier|public
name|void
name|setLog
parameter_list|(
name|Vector
name|log
parameter_list|)
block|{
name|iLog
operator|=
name|log
expr_stmt|;
block|}
specifier|public
name|Vector
name|getLog
parameter_list|()
block|{
return|return
name|iLog
return|;
block|}
specifier|public
name|String
name|getLog
parameter_list|(
name|int
name|level
parameter_list|)
block|{
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
for|for
control|(
name|Enumeration
name|e
init|=
name|iLog
operator|.
name|elements
argument_list|()
init|;
name|e
operator|.
name|hasMoreElements
argument_list|()
condition|;
control|)
block|{
name|Progress
operator|.
name|Message
name|m
init|=
operator|(
name|Progress
operator|.
name|Message
operator|)
name|e
operator|.
name|nextElement
argument_list|()
decl_stmt|;
name|String
name|s
init|=
name|m
operator|.
name|toString
argument_list|(
name|level
argument_list|)
decl_stmt|;
if|if
condition|(
name|s
operator|!=
literal|null
condition|)
name|sb
operator|.
name|append
argument_list|(
name|s
operator|+
literal|"\n"
argument_list|)
expr_stmt|;
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
specifier|public
name|String
name|getHtmlLog
parameter_list|(
name|int
name|level
parameter_list|,
name|boolean
name|includeDate
parameter_list|)
block|{
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
for|for
control|(
name|Enumeration
name|e
init|=
name|iLog
operator|.
name|elements
argument_list|()
init|;
name|e
operator|.
name|hasMoreElements
argument_list|()
condition|;
control|)
block|{
name|Progress
operator|.
name|Message
name|m
init|=
operator|(
name|Progress
operator|.
name|Message
operator|)
name|e
operator|.
name|nextElement
argument_list|()
decl_stmt|;
name|String
name|s
init|=
name|m
operator|.
name|toHtmlString
argument_list|(
name|level
argument_list|,
name|includeDate
argument_list|)
decl_stmt|;
if|if
condition|(
name|s
operator|!=
literal|null
condition|)
name|sb
operator|.
name|append
argument_list|(
name|s
operator|+
literal|"<br>"
argument_list|)
expr_stmt|;
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
specifier|public
name|String
name|getHtmlLog
parameter_list|(
name|int
name|level
parameter_list|,
name|boolean
name|includeDate
parameter_list|,
name|String
name|fromStage
parameter_list|)
block|{
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
for|for
control|(
name|Enumeration
name|e
init|=
name|iLog
operator|.
name|elements
argument_list|()
init|;
name|e
operator|.
name|hasMoreElements
argument_list|()
condition|;
control|)
block|{
name|Progress
operator|.
name|Message
name|m
init|=
operator|(
name|Progress
operator|.
name|Message
operator|)
name|e
operator|.
name|nextElement
argument_list|()
decl_stmt|;
if|if
condition|(
name|m
operator|.
name|getLevel
argument_list|()
operator|==
name|Progress
operator|.
name|MSGLEVEL_STAGE
operator|&&
name|m
operator|.
name|getMessage
argument_list|()
operator|.
name|equals
argument_list|(
name|fromStage
argument_list|)
condition|)
name|sb
operator|=
operator|new
name|StringBuffer
argument_list|()
expr_stmt|;
name|String
name|s
init|=
name|m
operator|.
name|toHtmlString
argument_list|(
name|level
argument_list|,
name|includeDate
argument_list|)
decl_stmt|;
if|if
condition|(
name|s
operator|!=
literal|null
condition|)
name|sb
operator|.
name|append
argument_list|(
name|s
operator|+
literal|"<br>"
argument_list|)
expr_stmt|;
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
specifier|public
name|void
name|load
parameter_list|(
name|Element
name|root
parameter_list|)
throws|throws
name|Exception
block|{
comment|/* 		XMLWriter writer = new XMLWriter(System.out, OutputFormat.createPrettyPrint()); 		writer.write(root.getDocument()); 		writer.flush(); 		*/
name|iLog
operator|.
name|clear
argument_list|()
expr_stmt|;
name|int
name|version
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|root
operator|.
name|attributeValue
argument_list|(
literal|"version"
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|version
operator|==
literal|1
condition|)
block|{
for|for
control|(
name|Iterator
name|i
init|=
name|root
operator|.
name|elementIterator
argument_list|(
literal|"msg"
argument_list|)
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
name|iLog
operator|.
name|add
argument_list|(
operator|new
name|Progress
operator|.
name|Message
argument_list|(
operator|(
name|Element
operator|)
name|i
operator|.
name|next
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|void
name|save
parameter_list|(
name|Element
name|root
parameter_list|)
throws|throws
name|Exception
block|{
name|root
operator|.
name|addAttribute
argument_list|(
literal|"version"
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|sVersion
argument_list|)
argument_list|)
expr_stmt|;
for|for
control|(
name|Enumeration
name|e
init|=
name|iLog
operator|.
name|elements
argument_list|()
init|;
name|e
operator|.
name|hasMoreElements
argument_list|()
condition|;
control|)
block|{
name|Progress
operator|.
name|Message
name|msg
init|=
operator|(
name|Progress
operator|.
name|Message
operator|)
name|e
operator|.
name|nextElement
argument_list|()
decl_stmt|;
if|if
condition|(
name|msg
operator|.
name|getLevel
argument_list|()
operator|<=
name|sNoSaveThreshold
condition|)
continue|continue;
name|msg
operator|.
name|save
argument_list|(
name|root
operator|.
name|addElement
argument_list|(
literal|"msg"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|boolean
name|saveToFile
parameter_list|()
block|{
return|return
literal|false
return|;
block|}
block|}
end_class

end_unit

