begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.1 (University Timetabling Application)  * Copyright (C) 2008, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program; if not, write to the Free Software Foundation, Inc.,  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|tags
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
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

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|http
operator|.
name|HttpServletRequest
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|jsp
operator|.
name|JspException
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|jsp
operator|.
name|tagext
operator|.
name|TagSupport
import|;
end_import

begin_import
import|import
name|org
operator|.
name|hibernate
operator|.
name|criterion
operator|.
name|Order
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|commons
operator|.
name|Debug
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
name|PreferenceLevel
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
name|dao
operator|.
name|PreferenceLevelDAO
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|PreferenceLegend
extends|extends
name|TagSupport
block|{
specifier|private
name|boolean
name|iNotAvailable
init|=
literal|false
decl_stmt|;
specifier|private
name|boolean
name|iPrefs
init|=
literal|true
decl_stmt|;
specifier|private
name|boolean
name|iDpBackgrounds
init|=
literal|false
decl_stmt|;
specifier|private
name|boolean
name|iDpAssign
init|=
literal|false
decl_stmt|;
specifier|private
name|boolean
name|iDpOffered
init|=
literal|false
decl_stmt|;
specifier|private
name|String
name|iSeparator
init|=
literal|"top"
decl_stmt|;
specifier|public
name|String
name|getSeparator
parameter_list|()
block|{
return|return
name|iSeparator
return|;
block|}
specifier|public
name|void
name|setSeparator
parameter_list|(
name|String
name|separator
parameter_list|)
block|{
name|iSeparator
operator|=
name|separator
expr_stmt|;
block|}
specifier|public
name|void
name|setNotAvailable
parameter_list|(
name|boolean
name|notAvailable
parameter_list|)
block|{
name|iNotAvailable
operator|=
name|notAvailable
expr_stmt|;
block|}
specifier|public
name|boolean
name|isNotAvailable
parameter_list|()
block|{
return|return
name|iNotAvailable
return|;
block|}
specifier|public
name|void
name|setDpBackgrounds
parameter_list|(
name|boolean
name|dpBackgrounds
parameter_list|)
block|{
name|iDpBackgrounds
operator|=
name|dpBackgrounds
expr_stmt|;
block|}
specifier|public
name|boolean
name|isDpBackgrounds
parameter_list|()
block|{
return|return
name|iDpBackgrounds
return|;
block|}
specifier|public
name|void
name|setPrefs
parameter_list|(
name|boolean
name|prefs
parameter_list|)
block|{
name|iPrefs
operator|=
name|prefs
expr_stmt|;
block|}
specifier|public
name|boolean
name|isPrefs
parameter_list|()
block|{
return|return
name|iPrefs
return|;
block|}
specifier|public
name|void
name|setDpOffered
parameter_list|(
name|boolean
name|dpOffered
parameter_list|)
block|{
name|iDpOffered
operator|=
name|dpOffered
expr_stmt|;
block|}
specifier|public
name|boolean
name|isDpOffered
parameter_list|()
block|{
return|return
name|iDpOffered
return|;
block|}
specifier|public
name|void
name|setDpAssign
parameter_list|(
name|boolean
name|dpAssign
parameter_list|)
block|{
name|iDpAssign
operator|=
name|dpAssign
expr_stmt|;
block|}
specifier|public
name|boolean
name|isDpAssign
parameter_list|()
block|{
return|return
name|iDpAssign
return|;
block|}
specifier|public
name|int
name|doEndTag
parameter_list|()
throws|throws
name|JspException
block|{
return|return
name|EVAL_PAGE
return|;
block|}
specifier|public
name|int
name|doStartTag
parameter_list|()
throws|throws
name|JspException
block|{
name|String
name|border
init|=
literal|""
decl_stmt|;
if|if
condition|(
name|iSeparator
operator|!=
literal|null
operator|&&
name|iSeparator
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|border
operator|=
literal|"border-"
operator|+
name|iSeparator
operator|+
literal|":black 1px dashed"
expr_stmt|;
if|if
condition|(
literal|"none"
operator|.
name|equals
argument_list|(
name|iSeparator
argument_list|)
condition|)
name|border
operator|=
literal|null
expr_stmt|;
name|StringBuffer
name|html
init|=
operator|new
name|StringBuffer
argument_list|(
name|border
operator|==
literal|null
condition|?
literal|""
else|:
literal|"<table width='99%' cellspacing='1' cellpadding='1' border='0' style='"
operator|+
name|border
operator|+
literal|"'><tr><td align='center'>"
argument_list|)
decl_stmt|;
name|html
operator|.
name|append
argument_list|(
literal|"<table cellspacing='1' cellpadding='1' border='0'><tr>"
argument_list|)
expr_stmt|;
name|Collection
name|prefLevels
init|=
operator|(
name|Collection
operator|)
name|pageContext
operator|.
name|getRequest
argument_list|()
operator|.
name|getAttribute
argument_list|(
name|PreferenceLevel
operator|.
name|PREF_LEVEL_ATTR_NAME
argument_list|)
decl_stmt|;
if|if
condition|(
name|prefLevels
operator|==
literal|null
condition|)
block|{
name|PreferenceLevelDAO
name|pdao
init|=
operator|new
name|PreferenceLevelDAO
argument_list|()
decl_stmt|;
name|prefLevels
operator|=
name|pdao
operator|.
name|findAll
argument_list|(
name|Order
operator|.
name|asc
argument_list|(
literal|"prefId"
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|String
name|imgFolder
init|=
operator|(
operator|(
name|HttpServletRequest
operator|)
name|pageContext
operator|.
name|getRequest
argument_list|()
operator|)
operator|.
name|getContextPath
argument_list|()
operator|+
literal|"/images/"
decl_stmt|;
if|if
condition|(
name|isPrefs
argument_list|()
condition|)
block|{
for|for
control|(
name|Iterator
name|i
init|=
name|prefLevels
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|PreferenceLevel
name|pl
init|=
operator|(
name|PreferenceLevel
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|String
name|color
init|=
name|pl
operator|.
name|prefcolor
argument_list|()
decl_stmt|;
name|html
operator|.
name|append
argument_list|(
comment|//"<td width='20' height='20' style='border:rgb(0,0,0) 1px solid;background-color:" + color + "'>&nbsp;</td>"
literal|"<td style='font-size: 80%;'>"
operator|+
literal|"<img border='0' align='absmiddle' src='"
operator|+
name|imgFolder
operator|+
literal|"pref"
operator|+
name|pl
operator|.
name|getUniqueId
argument_list|()
operator|+
literal|".png'>"
operator|+
literal|"&nbsp;"
operator|+
name|pl
operator|.
name|getPrefName
argument_list|()
operator|+
literal|"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
operator|+
literal|"</td>"
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|isNotAvailable
argument_list|()
condition|)
block|{
name|html
operator|.
name|append
argument_list|(
literal|"<td style='font-size: 80%;'>"
operator|+
literal|"<img border='0' align='absmiddle'src='"
operator|+
name|imgFolder
operator|+
literal|"prefna.png'>"
operator|+
literal|"&nbsp;Not Available&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
operator|+
literal|"</td>"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|isDpOffered
argument_list|()
condition|)
block|{
name|html
operator|.
name|append
argument_list|(
literal|"<td style='font-size: 80%;'>"
operator|+
literal|"<img border='0' align='absmiddle' src='"
operator|+
name|imgFolder
operator|+
literal|"dp-offered.png'>"
operator|+
literal|"&nbsp;Classes Offered&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
operator|+
literal|"</td>"
argument_list|)
expr_stmt|;
name|html
operator|.
name|append
argument_list|(
literal|"<td style='font-size: 80%;'>"
operator|+
literal|"<img border='0' align='absmiddle' src='"
operator|+
name|imgFolder
operator|+
literal|"dp-notoffered.png'>"
operator|+
literal|"&nbsp;Classes Not Offered&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
operator|+
literal|"</td>"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|isDpBackgrounds
argument_list|()
condition|)
block|{
name|html
operator|.
name|append
argument_list|(
literal|"<td style='font-size: 80%;'>"
operator|+
literal|"<img border='0' align='absmiddle' src='"
operator|+
name|imgFolder
operator|+
literal|"dp-start.png'>"
operator|+
literal|"&nbsp;Start / End&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
operator|+
literal|"</td>"
argument_list|)
expr_stmt|;
name|html
operator|.
name|append
argument_list|(
literal|"<td style='font-size: 80%;'>"
operator|+
literal|"<img border='0' align='absmiddle' src='"
operator|+
name|imgFolder
operator|+
literal|"dp-exam.png'>"
operator|+
literal|"&nbsp;Examination Start&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
operator|+
literal|"</td>"
argument_list|)
expr_stmt|;
name|html
operator|.
name|append
argument_list|(
literal|"<td style='font-size: 80%;'>"
operator|+
literal|"<img border='0' align='absmiddle' src='"
operator|+
name|imgFolder
operator|+
literal|"dp-holiday.png'>"
operator|+
literal|"&nbsp;Holiday&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
operator|+
literal|"</td>"
argument_list|)
expr_stmt|;
name|html
operator|.
name|append
argument_list|(
literal|"<td style='font-size: 80%;'>"
operator|+
literal|"<img border='0' align='absmiddle' src='"
operator|+
name|imgFolder
operator|+
literal|"dp-break.png'>"
operator|+
literal|"&nbsp;Break&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
operator|+
literal|"</td>"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|isDpAssign
argument_list|()
condition|)
block|{
name|html
operator|.
name|append
argument_list|(
literal|"<td style='font-size: 80%;'>"
operator|+
literal|"<img border='0' align='absmiddle' src='"
operator|+
name|imgFolder
operator|+
literal|"dp-assign.png'>"
operator|+
literal|"&nbsp;Assignment&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
operator|+
literal|"</td>"
argument_list|)
expr_stmt|;
block|}
name|html
operator|.
name|append
argument_list|(
literal|"</tr></table>"
argument_list|)
expr_stmt|;
name|html
operator|.
name|append
argument_list|(
name|border
operator|==
literal|null
condition|?
literal|""
else|:
literal|"</td></tr></table>"
argument_list|)
expr_stmt|;
try|try
block|{
name|pageContext
operator|.
name|getOut
argument_list|()
operator|.
name|print
argument_list|(
name|html
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|Debug
operator|.
name|error
argument_list|(
literal|"Could not display preference legend: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|SKIP_BODY
return|;
block|}
block|}
end_class

end_unit

