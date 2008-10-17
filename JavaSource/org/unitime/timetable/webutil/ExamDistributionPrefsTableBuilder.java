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
name|java
operator|.
name|util
operator|.
name|List
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
name|hibernate
operator|.
name|Query
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
name|Department
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
name|DistributionObject
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
name|DistributionPref
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
name|Exam
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
name|ExamOwner
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
name|Roles
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
name|Session
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
name|TimetableManager
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
name|DistributionPrefDAO
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
name|TimetableManagerDAO
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
name|Paragraph
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
name|Rectangle
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
name|pdf
operator|.
name|PdfPTable
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
name|pdf
operator|.
name|PdfWriter
import|;
end_import

begin_comment
comment|/**  * Builds HTML tables for distribution preferences  *   * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|ExamDistributionPrefsTableBuilder
block|{
specifier|public
name|String
name|getDistPrefsTable
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|Long
name|subjectAreaId
parameter_list|,
name|String
name|courseNbr
parameter_list|,
name|Integer
name|examType
parameter_list|)
throws|throws
name|Exception
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
name|Session
name|session
init|=
name|Session
operator|.
name|getCurrentAcadSession
argument_list|(
name|user
argument_list|)
decl_stmt|;
name|Query
name|q
init|=
operator|new
name|DistributionPrefDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select distinct dp from DistributionPref dp "
operator|+
literal|"inner join dp.distributionObjects do, Exam x inner join x.owners o "
operator|+
literal|"where "
operator|+
operator|(
name|courseNbr
operator|==
literal|null
operator|||
name|courseNbr
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|?
literal|""
else|:
name|courseNbr
operator|.
name|indexOf
argument_list|(
literal|'*'
argument_list|)
operator|>=
literal|0
condition|?
literal|"o.course.courseNbr like :courseNbr and "
else|:
literal|"o.course.courseNbr=:courseNbr and"
operator|)
operator|+
operator|(
name|subjectAreaId
operator|==
literal|null
condition|?
literal|""
else|:
literal|" o.course.subjectArea.uniqueId=:subjectAreaId and "
operator|)
operator|+
literal|"dp.distributionType.examPref = true and "
operator|+
literal|"do.prefGroup = x and x.session.uniqueId=:sessionId and x.examType=:examType"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|setInteger
argument_list|(
literal|"examType"
argument_list|,
name|examType
argument_list|)
decl_stmt|;
if|if
condition|(
name|subjectAreaId
operator|!=
literal|null
condition|)
name|q
operator|.
name|setLong
argument_list|(
literal|"subjectAreaId"
argument_list|,
name|subjectAreaId
argument_list|)
expr_stmt|;
if|if
condition|(
name|courseNbr
operator|!=
literal|null
operator|&&
name|courseNbr
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|!=
literal|0
condition|)
name|q
operator|.
name|setString
argument_list|(
literal|"courseNbr"
argument_list|,
name|courseNbr
operator|.
name|trim
argument_list|()
operator|.
name|replaceAll
argument_list|(
literal|"\\*"
argument_list|,
literal|"%"
argument_list|)
argument_list|)
expr_stmt|;
name|List
name|distPrefs
init|=
name|q
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
decl_stmt|;
return|return
name|toHtmlTable
argument_list|(
name|request
argument_list|,
name|distPrefs
argument_list|,
literal|null
argument_list|)
return|;
block|}
specifier|public
name|File
name|getDistPrefsTableAsPdf
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|Long
name|subjectAreaId
parameter_list|,
name|String
name|courseNbr
parameter_list|,
name|Integer
name|examType
parameter_list|)
throws|throws
name|Exception
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
name|Session
name|session
init|=
name|Session
operator|.
name|getCurrentAcadSession
argument_list|(
name|user
argument_list|)
decl_stmt|;
name|Query
name|q
init|=
operator|new
name|DistributionPrefDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select distinct dp from DistributionPref dp "
operator|+
literal|"inner join dp.distributionObjects do, Exam x inner join x.owners o "
operator|+
literal|"where "
operator|+
operator|(
name|courseNbr
operator|==
literal|null
operator|||
name|courseNbr
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|?
literal|""
else|:
name|courseNbr
operator|.
name|indexOf
argument_list|(
literal|'*'
argument_list|)
operator|>=
literal|0
condition|?
literal|"o.course.courseNbr like :courseNbr and "
else|:
literal|"o.course.courseNbr=:courseNbr and"
operator|)
operator|+
operator|(
name|subjectAreaId
operator|==
literal|null
condition|?
literal|""
else|:
literal|" o.course.subjectArea.uniqueId=:subjectAreaId and "
operator|)
operator|+
literal|"dp.distributionType.examPref = true and "
operator|+
literal|"do.prefGroup = x and x.session.uniqueId=:sessionId and x.examType=:examType"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|setInteger
argument_list|(
literal|"examType"
argument_list|,
name|examType
argument_list|)
decl_stmt|;
if|if
condition|(
name|subjectAreaId
operator|!=
literal|null
condition|)
name|q
operator|.
name|setLong
argument_list|(
literal|"subjectAreaId"
argument_list|,
name|subjectAreaId
argument_list|)
expr_stmt|;
if|if
condition|(
name|courseNbr
operator|!=
literal|null
operator|&&
name|courseNbr
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|!=
literal|0
condition|)
name|q
operator|.
name|setString
argument_list|(
literal|"courseNbr"
argument_list|,
name|courseNbr
operator|.
name|trim
argument_list|()
operator|.
name|replaceAll
argument_list|(
literal|"\\*"
argument_list|,
literal|"%"
argument_list|)
argument_list|)
expr_stmt|;
name|List
name|distPrefs
init|=
name|q
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
decl_stmt|;
return|return
name|toPdfTable
argument_list|(
name|request
argument_list|,
name|distPrefs
argument_list|,
name|examType
argument_list|)
return|;
block|}
specifier|public
name|String
name|getDistPrefsTable
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|Exam
name|exam
parameter_list|)
throws|throws
name|Exception
block|{
return|return
name|toHtmlTable
argument_list|(
name|request
argument_list|,
name|exam
operator|.
name|effectivePreferences
argument_list|(
name|DistributionPref
operator|.
name|class
argument_list|)
argument_list|,
literal|"Distribution Preferences"
argument_list|)
return|;
block|}
comment|/**      * Build a html table with the list representing distribution prefs       * @param distPrefs      * @param ordCol      * @param editable      * @return      */
specifier|public
name|String
name|toHtmlTable
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|Collection
name|distPrefs
parameter_list|,
name|String
name|title
parameter_list|)
throws|throws
name|Exception
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
name|Session
name|session
init|=
name|Session
operator|.
name|getCurrentAcadSession
argument_list|(
name|user
argument_list|)
decl_stmt|;
name|String
name|ownerId
init|=
operator|(
name|String
operator|)
name|user
operator|.
name|getAttribute
argument_list|(
name|Constants
operator|.
name|TMTBL_MGR_ID_ATTR_NAME
argument_list|)
decl_stmt|;
name|TimetableManager
name|manager
init|=
literal|null
decl_stmt|;
name|boolean
name|editable
init|=
literal|false
decl_stmt|;
name|boolean
name|isAdmin
init|=
literal|false
decl_stmt|;
name|boolean
name|isExamMgr
init|=
literal|false
decl_stmt|;
if|if
condition|(
name|ownerId
operator|!=
literal|null
condition|)
block|{
name|manager
operator|=
operator|new
name|TimetableManagerDAO
argument_list|()
operator|.
name|get
argument_list|(
operator|new
name|Long
argument_list|(
name|ownerId
argument_list|)
argument_list|)
expr_stmt|;
name|editable
operator|=
name|manager
operator|.
name|canEditExams
argument_list|(
name|session
argument_list|,
name|user
argument_list|)
expr_stmt|;
name|isAdmin
operator|=
name|user
operator|.
name|getCurrentRole
argument_list|()
operator|.
name|equals
argument_list|(
name|Roles
operator|.
name|ADMIN_ROLE
argument_list|)
expr_stmt|;
name|isExamMgr
operator|=
name|user
operator|.
name|getCurrentRole
argument_list|()
operator|.
name|equals
argument_list|(
name|Roles
operator|.
name|EXAM_MGR_ROLE
argument_list|)
expr_stmt|;
block|}
name|String
name|backId
init|=
operator|(
literal|"PreferenceGroup"
operator|.
name|equals
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"backType"
argument_list|)
argument_list|)
condition|?
name|request
operator|.
name|getParameter
argument_list|(
literal|"backId"
argument_list|)
else|:
literal|null
operator|)
decl_stmt|;
name|WebTable
operator|.
name|setOrder
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|,
literal|"examDistPrefsTable.ord"
argument_list|,
name|request
operator|.
name|getParameter
argument_list|(
literal|"order"
argument_list|)
argument_list|,
literal|4
argument_list|)
expr_stmt|;
name|WebTable
name|tbl
init|=
operator|new
name|WebTable
argument_list|(
literal|3
argument_list|,
name|title
argument_list|,
literal|"examDistributionPrefs.do?order=%%"
argument_list|,
operator|new
name|String
index|[]
block|{
literal|" Type "
block|,
literal|" Exam "
block|,
literal|" Class/Course "
block|}
argument_list|,
operator|new
name|String
index|[]
block|{
literal|"left"
block|,
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
block|,
literal|true
block|}
argument_list|)
decl_stmt|;
name|int
name|nrPrefs
init|=
literal|0
decl_stmt|;
for|for
control|(
name|Iterator
name|i1
init|=
name|distPrefs
operator|.
name|iterator
argument_list|()
init|;
name|i1
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|DistributionPref
name|dp
init|=
operator|(
name|DistributionPref
operator|)
name|i1
operator|.
name|next
argument_list|()
decl_stmt|;
name|boolean
name|prefEditable
init|=
name|editable
decl_stmt|;
name|nrPrefs
operator|++
expr_stmt|;
name|String
name|examStr
init|=
literal|""
decl_stmt|;
name|String
name|objStr
init|=
literal|""
decl_stmt|;
for|for
control|(
name|Iterator
name|i2
init|=
name|dp
operator|.
name|getOrderedSetOfDistributionObjects
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|i2
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|DistributionObject
name|dO
init|=
operator|(
name|DistributionObject
operator|)
name|i2
operator|.
name|next
argument_list|()
decl_stmt|;
name|Exam
name|exam
init|=
operator|(
name|Exam
operator|)
name|dO
operator|.
name|getPrefGroup
argument_list|()
decl_stmt|;
name|examStr
operator|+=
name|dO
operator|.
name|preferenceText
argument_list|()
expr_stmt|;
for|for
control|(
name|Iterator
name|i3
init|=
name|exam
operator|.
name|getOwners
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|i3
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|ExamOwner
name|owner
init|=
operator|(
name|ExamOwner
operator|)
name|i3
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|prefEditable
operator|&&
operator|!
name|isAdmin
operator|&&
operator|!
name|isExamMgr
condition|)
block|{
name|Department
name|dept
init|=
name|owner
operator|.
name|getCourse
argument_list|()
operator|.
name|getDepartment
argument_list|()
decl_stmt|;
if|if
condition|(
name|manager
operator|.
name|getDepartments
argument_list|()
operator|.
name|contains
argument_list|(
name|dept
argument_list|)
condition|)
block|{
if|if
condition|(
name|dept
operator|.
name|isExternalManager
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|)
block|{
if|if
condition|(
operator|!
name|dept
operator|.
name|effectiveStatusType
argument_list|()
operator|.
name|canManagerEdit
argument_list|()
condition|)
name|prefEditable
operator|=
literal|false
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
operator|!
name|dept
operator|.
name|effectiveStatusType
argument_list|()
operator|.
name|canOwnerEdit
argument_list|()
condition|)
name|prefEditable
operator|=
literal|false
expr_stmt|;
block|}
block|}
else|else
name|prefEditable
operator|=
literal|false
expr_stmt|;
block|}
name|objStr
operator|+=
name|owner
operator|.
name|getLabel
argument_list|()
expr_stmt|;
if|if
condition|(
name|i3
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|examStr
operator|+=
literal|"<BR>"
expr_stmt|;
name|objStr
operator|+=
literal|"<BR>"
expr_stmt|;
block|}
block|}
if|if
condition|(
name|i2
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|examStr
operator|+=
literal|"<BR>"
expr_stmt|;
name|objStr
operator|+=
literal|"<BR>"
expr_stmt|;
block|}
block|}
name|String
name|distType
init|=
name|dp
operator|.
name|getDistributionType
argument_list|()
operator|.
name|getLabel
argument_list|()
decl_stmt|;
name|String
name|prefLevel
init|=
name|dp
operator|.
name|getPrefLevel
argument_list|()
operator|.
name|getPrefName
argument_list|()
decl_stmt|;
name|String
name|prefColor
init|=
name|dp
operator|.
name|getPrefLevel
argument_list|()
operator|.
name|prefcolor
argument_list|()
decl_stmt|;
if|if
condition|(
name|PreferenceLevel
operator|.
name|sNeutral
operator|.
name|equals
argument_list|(
name|dp
operator|.
name|getPrefLevel
argument_list|()
operator|.
name|getPrefProlog
argument_list|()
argument_list|)
condition|)
name|prefColor
operator|=
literal|"gray"
expr_stmt|;
name|String
name|onClick
init|=
literal|null
decl_stmt|;
name|boolean
name|gray
init|=
literal|false
decl_stmt|;
if|if
condition|(
name|prefEditable
condition|)
block|{
name|onClick
operator|=
literal|"onClick=\"document.location='examDistributionPrefs.do"
operator|+
literal|"?dp="
operator|+
name|dp
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
operator|+
literal|"&op=view'\""
expr_stmt|;
block|}
comment|//else gray = true;
name|boolean
name|back
init|=
name|dp
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
operator|.
name|equals
argument_list|(
name|backId
argument_list|)
decl_stmt|;
name|tbl
operator|.
name|addLine
argument_list|(
name|onClick
argument_list|,
operator|new
name|String
index|[]
block|{
operator|(
name|back
condition|?
literal|"<A name=\"back\"</A>"
else|:
literal|""
operator|)
operator|+
operator|(
name|gray
condition|?
literal|"<span style='color:gray;'>"
else|:
literal|"<span style='color:"
operator|+
name|prefColor
operator|+
literal|";font-weight:bold;' title='"
operator|+
name|prefLevel
operator|+
literal|" "
operator|+
name|distType
operator|+
literal|"'>"
operator|)
operator|+
name|distType
operator|+
literal|"</span>"
block|,
operator|(
name|gray
condition|?
literal|"<span style='color:gray;'>"
else|:
literal|""
operator|)
operator|+
name|examStr
operator|+
operator|(
name|gray
condition|?
literal|"</span>"
else|:
literal|""
operator|)
block|,
operator|(
name|gray
condition|?
literal|"<span style='color:gray;'>"
else|:
literal|""
operator|)
operator|+
name|objStr
operator|+
operator|(
name|gray
condition|?
literal|"</span>"
else|:
literal|""
operator|)
block|}
argument_list|,
operator|new
name|Comparable
index|[]
block|{
name|distType
block|,
name|examStr
block|,
name|objStr
block|}
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|nrPrefs
operator|==
literal|0
condition|)
name|tbl
operator|.
name|addLine
argument_list|(
literal|null
argument_list|,
operator|new
name|String
index|[]
block|{
literal|"No preferences found"
block|,
literal|""
block|,
literal|""
block|}
argument_list|,
literal|null
argument_list|)
expr_stmt|;
return|return
name|tbl
operator|.
name|printTable
argument_list|(
name|WebTable
operator|.
name|getOrder
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|,
literal|"examDistPrefsTable.ord"
argument_list|)
argument_list|)
return|;
block|}
specifier|public
name|File
name|toPdfTable
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|Collection
name|distPrefs
parameter_list|,
name|int
name|examType
parameter_list|)
block|{
name|String
name|backId
init|=
operator|(
literal|"PreferenceGroup"
operator|.
name|equals
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"backType"
argument_list|)
argument_list|)
condition|?
name|request
operator|.
name|getParameter
argument_list|(
literal|"backId"
argument_list|)
else|:
literal|null
operator|)
decl_stmt|;
name|WebTable
operator|.
name|setOrder
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|,
literal|"examDistPrefsTable.ord"
argument_list|,
name|request
operator|.
name|getParameter
argument_list|(
literal|"order"
argument_list|)
argument_list|,
literal|4
argument_list|)
expr_stmt|;
name|PdfWebTable
name|tbl
init|=
operator|new
name|PdfWebTable
argument_list|(
literal|4
argument_list|,
name|Exam
operator|.
name|sExamTypes
index|[
name|examType
index|]
operator|+
literal|" Examination Distribution Preferences"
argument_list|,
literal|null
argument_list|,
operator|new
name|String
index|[]
block|{
literal|" Preference "
block|,
literal|" Type "
block|,
literal|" Exam "
block|,
literal|" Class/Course "
block|}
argument_list|,
operator|new
name|String
index|[]
block|{
literal|"left"
block|,
literal|"left"
block|,
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
block|,
literal|true
block|,
literal|true
block|}
argument_list|)
decl_stmt|;
name|int
name|nrPrefs
init|=
literal|0
decl_stmt|;
for|for
control|(
name|Iterator
name|i1
init|=
name|distPrefs
operator|.
name|iterator
argument_list|()
init|;
name|i1
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|DistributionPref
name|dp
init|=
operator|(
name|DistributionPref
operator|)
name|i1
operator|.
name|next
argument_list|()
decl_stmt|;
name|nrPrefs
operator|++
expr_stmt|;
name|String
name|examStr
init|=
literal|""
decl_stmt|;
name|String
name|objStr
init|=
literal|""
decl_stmt|;
for|for
control|(
name|Iterator
name|i2
init|=
name|dp
operator|.
name|getOrderedSetOfDistributionObjects
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|i2
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|DistributionObject
name|dO
init|=
operator|(
name|DistributionObject
operator|)
name|i2
operator|.
name|next
argument_list|()
decl_stmt|;
name|Exam
name|exam
init|=
operator|(
name|Exam
operator|)
name|dO
operator|.
name|getPrefGroup
argument_list|()
decl_stmt|;
name|examStr
operator|+=
name|dO
operator|.
name|preferenceText
argument_list|()
expr_stmt|;
for|for
control|(
name|Iterator
name|i3
init|=
name|exam
operator|.
name|getOwners
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|i3
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|ExamOwner
name|owner
init|=
operator|(
name|ExamOwner
operator|)
name|i3
operator|.
name|next
argument_list|()
decl_stmt|;
name|objStr
operator|+=
name|owner
operator|.
name|getLabel
argument_list|()
expr_stmt|;
if|if
condition|(
name|i3
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|examStr
operator|+=
literal|"\n"
expr_stmt|;
name|objStr
operator|+=
literal|"\n"
expr_stmt|;
block|}
block|}
if|if
condition|(
name|i2
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|examStr
operator|+=
literal|"\n"
expr_stmt|;
name|objStr
operator|+=
literal|"\n"
expr_stmt|;
block|}
block|}
name|String
name|distType
init|=
name|dp
operator|.
name|getDistributionType
argument_list|()
operator|.
name|getLabel
argument_list|()
decl_stmt|;
name|String
name|prefLevel
init|=
name|dp
operator|.
name|getPrefLevel
argument_list|()
operator|.
name|getPrefName
argument_list|()
decl_stmt|;
name|boolean
name|back
init|=
name|dp
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
operator|.
name|equals
argument_list|(
name|backId
argument_list|)
decl_stmt|;
name|tbl
operator|.
name|addLine
argument_list|(
literal|null
argument_list|,
operator|new
name|String
index|[]
block|{
name|prefLevel
block|,
name|distType
block|,
name|examStr
block|,
name|objStr
block|}
argument_list|,
operator|new
name|Comparable
index|[]
block|{
literal|null
block|,
name|distType
block|,
name|examStr
block|,
name|objStr
block|}
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|nrPrefs
operator|==
literal|0
condition|)
name|tbl
operator|.
name|addLine
argument_list|(
literal|null
argument_list|,
operator|new
name|String
index|[]
block|{
literal|"No preferences found"
block|,
literal|""
block|,
literal|""
block|,
literal|""
block|}
argument_list|,
literal|null
argument_list|)
expr_stmt|;
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
literal|"exdistpref"
argument_list|,
literal|"pdf"
argument_list|)
decl_stmt|;
name|int
name|ord
init|=
name|WebTable
operator|.
name|getOrder
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|,
literal|"examDistPrefsTable.ord"
argument_list|)
decl_stmt|;
name|ord
operator|=
operator|(
name|ord
operator|>
literal|0
condition|?
literal|1
else|:
operator|-
literal|1
operator|)
operator|*
operator|(
literal|1
operator|+
name|Math
operator|.
name|abs
argument_list|(
name|ord
argument_list|)
operator|)
expr_stmt|;
name|PdfPTable
name|table
init|=
name|tbl
operator|.
name|printPdfTable
argument_list|(
name|ord
argument_list|)
decl_stmt|;
name|float
name|width
init|=
name|tbl
operator|.
name|getWidth
argument_list|()
decl_stmt|;
name|Document
name|doc
init|=
operator|new
name|Document
argument_list|(
operator|new
name|Rectangle
argument_list|(
literal|60f
operator|+
name|width
argument_list|,
literal|60f
operator|+
literal|1.30f
operator|*
name|width
argument_list|)
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
name|out
operator|=
operator|new
name|FileOutputStream
argument_list|(
name|file
argument_list|)
expr_stmt|;
name|PdfWriter
name|iWriter
init|=
name|PdfWriter
operator|.
name|getInstance
argument_list|(
name|doc
argument_list|,
name|out
argument_list|)
decl_stmt|;
name|iWriter
operator|.
name|setPageEvent
argument_list|(
operator|new
name|PdfEventHandler
argument_list|()
argument_list|)
expr_stmt|;
name|doc
operator|.
name|open
argument_list|()
expr_stmt|;
if|if
condition|(
name|tbl
operator|.
name|getName
argument_list|()
operator|!=
literal|null
condition|)
name|doc
operator|.
name|add
argument_list|(
operator|new
name|Paragraph
argument_list|(
name|tbl
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
name|table
argument_list|)
expr_stmt|;
name|doc
operator|.
name|close
argument_list|()
expr_stmt|;
return|return
name|file
return|;
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
return|return
literal|null
return|;
block|}
block|}
end_class

end_unit

