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
name|webutil
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|File
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|FileOutputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashSet
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
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Set
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|TreeSet
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
name|commons
operator|.
name|User
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
name|web
operator|.
name|Web
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
name|web
operator|.
name|WebTable
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
name|ApplicationProperties
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
name|DepartmentalInstructor
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
name|Designator
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
name|Settings
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
name|SubjectArea
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
name|DepartmentalInstructorDAO
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
name|SubjectAreaDAO
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
name|util
operator|.
name|Constants
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
name|util
operator|.
name|PdfEventHandler
import|;
end_import

begin_import
import|import
name|com
operator|.
name|lowagie
operator|.
name|text
operator|.
name|Document
import|;
end_import

begin_import
import|import
name|com
operator|.
name|lowagie
operator|.
name|text
operator|.
name|FontFactory
import|;
end_import

begin_import
import|import
name|com
operator|.
name|lowagie
operator|.
name|text
operator|.
name|PageSize
import|;
end_import

begin_import
import|import
name|com
operator|.
name|lowagie
operator|.
name|text
operator|.
name|Paragraph
import|;
end_import

begin_comment
comment|/**  * Build Designator List for a specific subject area or instructor  *   * @author Heston Fernandes  */
end_comment

begin_class
specifier|public
class|class
name|DesignatorListBuilder
block|{
comment|/**      * Build Designator List for a specific subject area      * @param request      * @param subjectAreaId      * @return      */
specifier|public
name|String
name|htmlTableForSubjectArea
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|String
name|subjectAreaId
parameter_list|,
name|int
name|order
parameter_list|)
block|{
comment|// Create new table
name|WebTable
name|webTable
init|=
operator|new
name|WebTable
argument_list|(
literal|2
argument_list|,
literal|""
argument_list|,
literal|"designatorList.do?order=%%&subjectAreaId="
operator|+
name|subjectAreaId
argument_list|,
operator|new
name|String
index|[]
block|{
literal|"Instructor"
block|,
literal|"Code"
block|}
argument_list|,
operator|new
name|String
index|[]
block|{
literal|"left"
block|,
literal|"left"
block|}
argument_list|,
operator|new
name|boolean
index|[]
block|{
literal|true
block|,
literal|true
block|}
argument_list|)
decl_stmt|;
name|webTable
operator|.
name|enableHR
argument_list|(
literal|"#EFEFEF"
argument_list|)
expr_stmt|;
name|SubjectAreaDAO
name|sDao
init|=
operator|new
name|SubjectAreaDAO
argument_list|()
decl_stmt|;
name|SubjectArea
name|sa
init|=
name|sDao
operator|.
name|get
argument_list|(
operator|new
name|Long
argument_list|(
name|subjectAreaId
argument_list|)
argument_list|)
decl_stmt|;
name|Set
name|designators
init|=
name|sa
operator|.
name|getDesignatorInstructors
argument_list|()
decl_stmt|;
if|if
condition|(
name|designators
operator|==
literal|null
operator|||
name|designators
operator|.
name|size
argument_list|()
operator|==
literal|0
condition|)
block|{
name|webTable
operator|.
name|addLine
argument_list|(
literal|""
argument_list|,
operator|new
name|String
index|[]
block|{
literal|"<font class='errorCell'>No designators found for this subject area</font>"
block|,
literal|"&nbsp;"
block|}
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|User
name|user
init|=
name|Web
operator|.
name|getUser
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|)
decl_stmt|;
name|String
name|nameFormat
init|=
name|Settings
operator|.
name|getSettingValue
argument_list|(
name|user
argument_list|,
name|Constants
operator|.
name|SETTINGS_INSTRUCTOR_NAME_FORMAT
argument_list|)
decl_stmt|;
name|String
name|instructorSortOrder
init|=
name|Settings
operator|.
name|getSettingValue
argument_list|(
name|user
argument_list|,
name|Constants
operator|.
name|SETTINGS_INSTRUCTOR_SORT
argument_list|)
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|designators
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
name|Designator
name|d
init|=
operator|(
name|Designator
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|String
name|name
init|=
name|d
operator|.
name|getInstructor
argument_list|()
operator|.
name|getName
argument_list|(
name|nameFormat
argument_list|)
decl_stmt|;
name|String
name|nameOrd
init|=
name|d
operator|.
name|getInstructor
argument_list|()
operator|.
name|nameLastNameFirst
argument_list|()
operator|.
name|toLowerCase
argument_list|()
decl_stmt|;
if|if
condition|(
name|instructorSortOrder
operator|!=
literal|null
operator|&&
name|instructorSortOrder
operator|.
name|equals
argument_list|(
name|Constants
operator|.
name|SETTINGS_INSTRUCTOR_SORT_NATURAL
argument_list|)
condition|)
name|nameOrd
operator|=
name|name
operator|.
name|toLowerCase
argument_list|()
expr_stmt|;
name|webTable
operator|.
name|addLine
argument_list|(
name|d
operator|.
name|canUserEdit
argument_list|(
name|user
argument_list|)
condition|?
literal|"onClick=\"document.location.href='designatorEdit.do?op=Edit&id="
operator|+
name|d
operator|.
name|getUniqueId
argument_list|()
operator|+
literal|"';\""
else|:
literal|null
argument_list|,
operator|new
name|String
index|[]
block|{
name|name
operator|+
literal|"&nbsp;"
block|,
name|d
operator|.
name|getCode
argument_list|()
block|}
argument_list|,
operator|new
name|Comparable
index|[]
block|{
name|nameOrd
block|,
name|d
operator|.
name|getCode
argument_list|()
block|}
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|webTable
operator|.
name|printTable
argument_list|(
name|order
argument_list|)
return|;
block|}
comment|/**      * Build Designator List for a specific instructor      * @param request      * @param instructorId      * @return      */
specifier|public
name|String
name|htmlTableForInstructor
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|String
name|instructorId
parameter_list|,
name|int
name|order
parameter_list|)
block|{
name|User
name|user
init|=
name|Web
operator|.
name|getUser
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|)
decl_stmt|;
name|DepartmentalInstructorDAO
name|iDao
init|=
operator|new
name|DepartmentalInstructorDAO
argument_list|()
decl_stmt|;
name|DepartmentalInstructor
name|di
init|=
name|iDao
operator|.
name|get
argument_list|(
operator|new
name|Long
argument_list|(
name|instructorId
argument_list|)
argument_list|)
decl_stmt|;
name|String
name|puid
init|=
name|di
operator|.
name|getExternalUniqueId
argument_list|()
decl_stmt|;
name|Set
name|designators
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|puid
operator|==
literal|null
operator|||
name|puid
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
block|{
name|designators
operator|=
name|di
operator|.
name|getDesignatorSubjectAreas
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|designators
operator|=
operator|new
name|HashSet
argument_list|()
expr_stmt|;
name|String
name|sessionId
init|=
name|user
operator|.
name|getAttribute
argument_list|(
name|Constants
operator|.
name|SESSION_ID_ATTR_NAME
argument_list|)
operator|.
name|toString
argument_list|()
decl_stmt|;
name|List
name|all
init|=
name|DepartmentalInstructor
operator|.
name|getAllForInstructor
argument_list|(
name|di
argument_list|,
operator|new
name|Long
argument_list|(
name|sessionId
argument_list|)
argument_list|)
decl_stmt|;
name|TreeSet
name|sortedAll
init|=
operator|new
name|TreeSet
argument_list|(
name|all
argument_list|)
decl_stmt|;
for|for
control|(
name|Iterator
name|iterInstDept
init|=
name|sortedAll
operator|.
name|iterator
argument_list|()
init|;
name|iterInstDept
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|DepartmentalInstructor
name|anotherDi
init|=
operator|(
name|DepartmentalInstructor
operator|)
name|iterInstDept
operator|.
name|next
argument_list|()
decl_stmt|;
name|designators
operator|.
name|addAll
argument_list|(
name|anotherDi
operator|.
name|getDesignatorSubjectAreas
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|designators
operator|!=
literal|null
operator|&&
name|designators
operator|.
name|size
argument_list|()
operator|>
literal|0
condition|)
block|{
name|WebTable
name|webTable
init|=
operator|new
name|WebTable
argument_list|(
literal|2
argument_list|,
literal|""
argument_list|,
literal|"instructorDetail.do?order=%%&instructorId="
operator|+
name|instructorId
argument_list|,
operator|new
name|String
index|[]
block|{
literal|"Subject Area"
block|,
literal|"Code"
block|}
argument_list|,
operator|new
name|String
index|[]
block|{
literal|"left"
block|,
literal|"left"
block|}
argument_list|,
operator|new
name|boolean
index|[]
block|{
literal|true
block|,
literal|true
block|}
argument_list|)
decl_stmt|;
name|webTable
operator|.
name|enableHR
argument_list|(
literal|"#EFEFEF"
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|designators
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
name|Designator
name|d
init|=
operator|(
name|Designator
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|String
name|sa
init|=
name|d
operator|.
name|getSubjectArea
argument_list|()
operator|.
name|getSubjectAreaAbbreviation
argument_list|()
decl_stmt|;
name|webTable
operator|.
name|addLine
argument_list|(
name|d
operator|.
name|canUserEdit
argument_list|(
name|user
argument_list|)
condition|?
literal|"onClick=\"document.location.href='designatorEdit.do?op=Edit&id="
operator|+
name|d
operator|.
name|getUniqueId
argument_list|()
operator|+
literal|"';\""
else|:
literal|null
argument_list|,
operator|new
name|String
index|[]
block|{
name|d
operator|.
name|getSubjectArea
argument_list|()
operator|.
name|getSubjectAreaAbbreviation
argument_list|()
operator|+
literal|"&nbsp;"
block|,
name|d
operator|.
name|getCode
argument_list|()
block|}
argument_list|,
operator|new
name|Comparable
index|[]
block|{
name|sa
block|,
name|d
operator|.
name|getCode
argument_list|()
block|}
argument_list|)
expr_stmt|;
block|}
return|return
name|webTable
operator|.
name|printTable
argument_list|(
name|order
argument_list|)
return|;
block|}
return|return
literal|"<TR><TD>&nbsp;</TD></TR>"
return|;
block|}
specifier|public
name|void
name|pdfTableForSubjectArea
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|String
name|subjectAreaId
parameter_list|,
name|int
name|order
parameter_list|)
block|{
name|SubjectAreaDAO
name|sDao
init|=
operator|new
name|SubjectAreaDAO
argument_list|()
decl_stmt|;
name|SubjectArea
name|sa
init|=
name|sDao
operator|.
name|get
argument_list|(
operator|new
name|Long
argument_list|(
name|subjectAreaId
argument_list|)
argument_list|)
decl_stmt|;
name|Set
name|designators
init|=
name|sa
operator|.
name|getDesignatorInstructors
argument_list|()
decl_stmt|;
comment|// Create new table
name|PdfWebTable
name|webTable
init|=
operator|new
name|PdfWebTable
argument_list|(
literal|2
argument_list|,
name|sa
operator|.
name|getSubjectAreaAbbreviation
argument_list|()
operator|+
literal|" Designator List"
argument_list|,
literal|null
argument_list|,
operator|new
name|String
index|[]
block|{
literal|"Instructor"
block|,
literal|"Code"
block|}
argument_list|,
operator|new
name|String
index|[]
block|{
literal|"left"
block|,
literal|"left"
block|}
argument_list|,
operator|new
name|boolean
index|[]
block|{
literal|true
block|,
literal|true
block|}
argument_list|)
decl_stmt|;
name|webTable
operator|.
name|enableHR
argument_list|(
literal|"#EFEFEF"
argument_list|)
expr_stmt|;
if|if
condition|(
name|designators
operator|==
literal|null
operator|||
name|designators
operator|.
name|size
argument_list|()
operator|==
literal|0
condition|)
return|return;
name|User
name|user
init|=
name|Web
operator|.
name|getUser
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|)
decl_stmt|;
name|String
name|nameFormat
init|=
name|Settings
operator|.
name|getSettingValue
argument_list|(
name|user
argument_list|,
name|Constants
operator|.
name|SETTINGS_INSTRUCTOR_NAME_FORMAT
argument_list|)
decl_stmt|;
name|String
name|instructorSortOrder
init|=
name|Settings
operator|.
name|getSettingValue
argument_list|(
name|user
argument_list|,
name|Constants
operator|.
name|SETTINGS_INSTRUCTOR_SORT
argument_list|)
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|designators
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
name|Designator
name|d
init|=
operator|(
name|Designator
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|String
name|name
init|=
name|d
operator|.
name|getInstructor
argument_list|()
operator|.
name|getName
argument_list|(
name|nameFormat
argument_list|)
decl_stmt|;
name|String
name|nameOrd
init|=
name|d
operator|.
name|getInstructor
argument_list|()
operator|.
name|nameLastNameFirst
argument_list|()
operator|.
name|toLowerCase
argument_list|()
decl_stmt|;
if|if
condition|(
name|instructorSortOrder
operator|!=
literal|null
operator|&&
name|instructorSortOrder
operator|.
name|equals
argument_list|(
name|Constants
operator|.
name|SETTINGS_INSTRUCTOR_SORT_NATURAL
argument_list|)
condition|)
name|nameOrd
operator|=
name|name
operator|.
name|toLowerCase
argument_list|()
expr_stmt|;
name|webTable
operator|.
name|addLine
argument_list|(
literal|null
argument_list|,
operator|new
name|String
index|[]
block|{
name|name
block|,
name|d
operator|.
name|getCode
argument_list|()
block|}
argument_list|,
operator|new
name|Comparable
index|[]
block|{
name|nameOrd
block|,
name|d
operator|.
name|getCode
argument_list|()
block|}
argument_list|)
expr_stmt|;
block|}
name|FileOutputStream
name|out
init|=
literal|null
decl_stmt|;
try|try
block|{
name|File
name|file
init|=
name|ApplicationProperties
operator|.
name|getTempFile
argument_list|(
literal|"designators"
argument_list|,
literal|"pdf"
argument_list|)
decl_stmt|;
name|out
operator|=
operator|new
name|FileOutputStream
argument_list|(
name|file
argument_list|)
expr_stmt|;
name|Document
name|doc
init|=
operator|new
name|Document
argument_list|(
name|PageSize
operator|.
name|LETTER
argument_list|,
literal|30
argument_list|,
literal|30
argument_list|,
literal|30
argument_list|,
literal|30
argument_list|)
decl_stmt|;
name|PdfEventHandler
operator|.
name|initFooter
argument_list|(
name|doc
argument_list|,
name|out
argument_list|)
expr_stmt|;
name|doc
operator|.
name|open
argument_list|()
expr_stmt|;
name|doc
operator|.
name|add
argument_list|(
operator|new
name|Paragraph
argument_list|(
name|webTable
operator|.
name|getName
argument_list|()
argument_list|,
name|FontFactory
operator|.
name|getFont
argument_list|(
name|FontFactory
operator|.
name|HELVETICA_BOLD
argument_list|,
literal|16
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|doc
operator|.
name|add
argument_list|(
name|webTable
operator|.
name|printPdfTable
argument_list|(
name|order
argument_list|)
argument_list|)
expr_stmt|;
name|doc
operator|.
name|close
argument_list|()
expr_stmt|;
name|request
operator|.
name|setAttribute
argument_list|(
name|Constants
operator|.
name|REQUEST_OPEN_URL
argument_list|,
literal|"temp/"
operator|+
name|file
operator|.
name|getName
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
name|e
argument_list|)
expr_stmt|;
block|}
finally|finally
block|{
try|try
block|{
if|if
condition|(
name|out
operator|!=
literal|null
condition|)
name|out
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
block|}
block|}
block|}
block|}
end_class

end_unit

