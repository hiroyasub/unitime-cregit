begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 (University Timetabling Application)  * Copyright (C) 2008 - 2010, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|webutil
package|;
end_package

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|commons
operator|.
name|web
operator|.
name|htmlgen
operator|.
name|TableCell
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
name|model
operator|.
name|Class_
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
name|model
operator|.
name|PreferenceGroup
import|;
end_import

begin_comment
comment|/**  * @author Stephanie Schluttenhofer  *  */
end_comment

begin_class
specifier|public
class|class
name|WebSubpartClassListTableBuilder
extends|extends
name|WebClassListTableBuilder
block|{
comment|/** 	 *  	 */
specifier|public
name|WebSubpartClassListTableBuilder
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
specifier|private
name|String
name|addDeleteAnchor
parameter_list|(
name|Class_
name|aClass
parameter_list|)
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
literal|"<A onclick=\" if(confirm('Are you sure you want to delete this class section? Continue?')) {"
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"document.location.href='schedulingSubpartEdit.do?op=DeleteClass&ssuid="
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|aClass
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"&classId="
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|aClass
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"'; return true; } else { return false; }"
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"\">"
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"<IMG src=\"images/Delete16.gif\" border=\"0\" alt=\"Delete Class\" title=\"Delete Class\" align=\"top\">"
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"</A> "
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
name|TableCell
name|buildPrefGroupLabel
parameter_list|(
name|PreferenceGroup
name|prefGroup
parameter_list|,
name|String
name|indentSpaces
parameter_list|,
name|boolean
name|isEditable
parameter_list|)
block|{
if|if
condition|(
name|prefGroup
operator|instanceof
name|Class_
condition|)
block|{
name|TableCell
name|cell
init|=
name|initNormalCell
argument_list|(
name|indentSpaces
argument_list|,
name|isEditable
argument_list|)
decl_stmt|;
if|if
condition|(
literal|"PreferenceGroup"
operator|.
name|equals
argument_list|(
name|getBackType
argument_list|()
argument_list|)
operator|&&
name|prefGroup
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
operator|.
name|equals
argument_list|(
name|getBackId
argument_list|()
argument_list|)
condition|)
name|cell
operator|.
name|addContent
argument_list|(
literal|"<A name=\"back\"></A>"
argument_list|)
expr_stmt|;
name|Class_
name|aClass
init|=
operator|(
name|Class_
operator|)
name|prefGroup
decl_stmt|;
if|if
condition|(
operator|!
name|isEditable
condition|)
block|{
name|cell
operator|.
name|addContent
argument_list|(
literal|"<font color=gray>"
argument_list|)
expr_stmt|;
block|}
name|cell
operator|.
name|addContent
argument_list|(
literal|"<A name=\"A"
operator|+
name|prefGroup
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
operator|+
literal|"\"></A>"
argument_list|)
expr_stmt|;
if|if
condition|(
name|isEditable
operator|&&
name|aClass
operator|.
name|canBeDeleted
argument_list|()
condition|)
block|{
name|cell
operator|.
name|addContent
argument_list|(
name|this
operator|.
name|addDeleteAnchor
argument_list|(
name|aClass
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|isEditable
condition|)
block|{
name|cell
operator|.
name|addContent
argument_list|(
literal|"<A onclick=\"document.location='classEdit.do?cid="
operator|+
name|aClass
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
operator|+
literal|"&sec="
operator|+
name|aClass
operator|.
name|getSectionNumberString
argument_list|()
operator|+
literal|"'\">"
argument_list|)
expr_stmt|;
block|}
name|cell
operator|.
name|addContent
argument_list|(
literal|"<b>"
argument_list|)
expr_stmt|;
name|cell
operator|.
name|addContent
argument_list|(
name|aClass
operator|.
name|getClassLabel
argument_list|()
argument_list|)
expr_stmt|;
name|cell
operator|.
name|addContent
argument_list|(
literal|"</b>"
argument_list|)
expr_stmt|;
if|if
condition|(
name|isEditable
condition|)
block|{
name|cell
operator|.
name|addContent
argument_list|(
literal|"</A>"
argument_list|)
expr_stmt|;
block|}
name|cell
operator|.
name|setNoWrap
argument_list|(
literal|true
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|isEditable
condition|)
block|{
name|cell
operator|.
name|addContent
argument_list|(
literal|"</font>"
argument_list|)
expr_stmt|;
block|}
return|return
operator|(
name|cell
operator|)
return|;
block|}
else|else
block|{
return|return
operator|(
name|super
operator|.
name|buildPrefGroupLabel
argument_list|(
name|prefGroup
argument_list|,
name|indentSpaces
argument_list|,
name|isEditable
argument_list|,
literal|null
argument_list|)
operator|)
return|;
block|}
block|}
block|}
end_class

end_unit

