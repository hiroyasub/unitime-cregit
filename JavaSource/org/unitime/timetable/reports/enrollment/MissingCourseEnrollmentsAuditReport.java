begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 (University Timetabling Application)  * Copyright (C) 2010, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|reports
operator|.
name|enrollment
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
name|IOException
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
name|TreeSet
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Vector
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
name|SubjectArea
import|;
end_import

begin_import
import|import
name|com
operator|.
name|itextpdf
operator|.
name|text
operator|.
name|DocumentException
import|;
end_import

begin_comment
comment|/**  * @author says  *  */
end_comment

begin_class
specifier|public
class|class
name|MissingCourseEnrollmentsAuditReport
extends|extends
name|PdfEnrollmentAuditReport
block|{
specifier|public
name|MissingCourseEnrollmentsAuditReport
parameter_list|(
name|int
name|mode
parameter_list|,
name|File
name|file
parameter_list|,
name|Session
name|session
parameter_list|,
name|TreeSet
argument_list|<
name|SubjectArea
argument_list|>
name|subjectAreas
parameter_list|,
name|String
name|subTitle
parameter_list|)
throws|throws
name|DocumentException
throws|,
name|IOException
block|{
name|super
argument_list|(
name|mode
argument_list|,
name|getTitle
argument_list|()
argument_list|,
name|file
argument_list|,
name|session
argument_list|,
name|subjectAreas
argument_list|,
name|subTitle
argument_list|)
expr_stmt|;
block|}
specifier|public
name|MissingCourseEnrollmentsAuditReport
parameter_list|(
name|int
name|mode
parameter_list|,
name|File
name|file
parameter_list|,
name|Session
name|session
parameter_list|)
throws|throws
name|DocumentException
throws|,
name|IOException
block|{
name|super
argument_list|(
name|mode
argument_list|,
name|getTitle
argument_list|()
argument_list|,
name|file
argument_list|,
name|session
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|printReport
parameter_list|()
throws|throws
name|DocumentException
block|{
name|setHeader
argument_list|(
name|buildHeaderString
argument_list|()
argument_list|)
expr_stmt|;
name|List
name|results
init|=
name|getAuditResults
argument_list|(
name|getSubjectAreas
argument_list|()
argument_list|)
decl_stmt|;
name|Vector
argument_list|<
name|String
argument_list|>
name|lines
init|=
operator|new
name|Vector
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|Iterator
name|it
init|=
name|results
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
name|MissingCourseEnrollmentsAuditResult
name|result
init|=
operator|new
name|MissingCourseEnrollmentsAuditResult
argument_list|(
operator|(
name|Object
index|[]
operator|)
name|it
operator|.
name|next
argument_list|()
argument_list|)
decl_stmt|;
name|lines
operator|.
name|add
argument_list|(
name|buildLineString
argument_list|(
name|result
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|printHeader
argument_list|()
expr_stmt|;
for|for
control|(
name|String
name|str
range|:
name|lines
control|)
block|{
name|println
argument_list|(
name|str
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|lines
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|lastPage
argument_list|()
expr_stmt|;
block|}
block|}
specifier|public
specifier|static
name|String
name|getTitle
parameter_list|()
block|{
return|return
operator|(
literal|"Missing Course Enrollments"
operator|)
return|;
block|}
specifier|private
name|String
name|buildLineString
parameter_list|(
name|MissingCourseEnrollmentsAuditResult
name|result
parameter_list|)
block|{
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|buildBaseAuditLine
argument_list|(
name|result
argument_list|)
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|" | "
argument_list|)
operator|.
name|append
argument_list|(
literal|" "
argument_list|)
operator|.
name|append
argument_list|(
name|rpad
argument_list|(
name|result
operator|.
name|itypeString
argument_list|()
argument_list|,
literal|' '
argument_list|,
name|itypeLength
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
specifier|private
name|String
index|[]
name|buildHeaderString
parameter_list|()
block|{
name|String
index|[]
name|hdr
init|=
operator|new
name|String
index|[
literal|3
index|]
decl_stmt|;
name|StringBuilder
name|sb0
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|StringBuilder
name|sb1
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|StringBuilder
name|sb2
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|String
index|[]
name|baseHdr
init|=
name|getBaseHeader
argument_list|()
decl_stmt|;
name|sb0
operator|.
name|append
argument_list|(
name|baseHdr
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
name|sb1
operator|.
name|append
argument_list|(
name|baseHdr
index|[
literal|1
index|]
argument_list|)
expr_stmt|;
name|sb2
operator|.
name|append
argument_list|(
name|baseHdr
index|[
literal|2
index|]
argument_list|)
expr_stmt|;
name|sb0
operator|.
name|append
argument_list|(
literal|" | "
argument_list|)
expr_stmt|;
name|sb1
operator|.
name|append
argument_list|(
literal|" | "
argument_list|)
expr_stmt|;
name|sb2
operator|.
name|append
argument_list|(
literal|" | "
argument_list|)
expr_stmt|;
name|sb0
operator|.
name|append
argument_list|(
literal|" "
argument_list|)
operator|.
name|append
argument_list|(
name|rpad
argument_list|(
literal|"Missing"
argument_list|,
literal|' '
argument_list|,
name|itypeLength
argument_list|)
argument_list|)
expr_stmt|;
name|sb1
operator|.
name|append
argument_list|(
literal|" "
argument_list|)
operator|.
name|append
argument_list|(
name|rpad
argument_list|(
literal|"Subpart"
argument_list|,
literal|' '
argument_list|,
name|itypeLength
argument_list|)
argument_list|)
expr_stmt|;
name|sb2
operator|.
name|append
argument_list|(
literal|" "
argument_list|)
operator|.
name|append
argument_list|(
name|rpad
argument_list|(
literal|""
argument_list|,
literal|'-'
argument_list|,
name|itypeLength
argument_list|)
argument_list|)
expr_stmt|;
name|hdr
index|[
literal|0
index|]
operator|=
name|sb0
operator|.
name|toString
argument_list|()
expr_stmt|;
name|hdr
index|[
literal|1
index|]
operator|=
name|sb1
operator|.
name|toString
argument_list|()
expr_stmt|;
name|hdr
index|[
literal|2
index|]
operator|=
name|sb2
operator|.
name|toString
argument_list|()
expr_stmt|;
return|return
operator|(
name|hdr
operator|)
return|;
block|}
specifier|protected
name|String
name|createQueryString
parameter_list|(
name|TreeSet
argument_list|<
name|SubjectArea
argument_list|>
name|subjectAreas
parameter_list|)
block|{
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"select distinct s.externalUniqueId, s.lastName, s.firstName, s.middleName,"
argument_list|)
operator|.
name|append
argument_list|(
literal|" sce.courseOffering.subjectArea.subjectAreaAbbreviation, sce.courseOffering.courseNbr, sce.courseOffering.title,"
argument_list|)
operator|.
name|append
argument_list|(
literal|" ss.itype.abbv,"
argument_list|)
operator|.
name|append
argument_list|(
literal|" ( select count(sce1) from StudentClassEnrollment sce1"
argument_list|)
operator|.
name|append
argument_list|(
literal|" where sce1.clazz.schedulingSubpart.uniqueId = ss.uniqueId and sce1.student.uniqueId = s.uniqueId ) "
argument_list|)
operator|.
name|append
argument_list|(
literal|" from Student s inner join s.classEnrollments as sce, SchedulingSubpart ss"
argument_list|)
operator|.
name|append
argument_list|(
literal|" where  ss.instrOfferingConfig.uniqueId = sce.clazz.schedulingSubpart.instrOfferingConfig.uniqueId"
argument_list|)
operator|.
name|append
argument_list|(
literal|" and s.session.uniqueId = :sessId"
argument_list|)
expr_stmt|;
if|if
condition|(
name|subjectAreas
operator|!=
literal|null
operator|&&
operator|!
name|subjectAreas
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|" and sce.courseOffering.subjectArea.uniqueId in ("
argument_list|)
expr_stmt|;
name|boolean
name|first
init|=
literal|true
decl_stmt|;
for|for
control|(
name|SubjectArea
name|sa
range|:
name|subjectAreas
control|)
block|{
if|if
condition|(
name|first
condition|)
block|{
name|first
operator|=
literal|false
expr_stmt|;
block|}
else|else
block|{
name|sb
operator|.
name|append
argument_list|(
literal|","
argument_list|)
expr_stmt|;
block|}
name|sb
operator|.
name|append
argument_list|(
literal|" "
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|sa
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|sb
operator|.
name|append
argument_list|(
literal|" ) "
argument_list|)
expr_stmt|;
block|}
name|sb
operator|.
name|append
argument_list|(
literal|" and 0 = ( select count(sce1) from StudentClassEnrollment sce1"
argument_list|)
operator|.
name|append
argument_list|(
literal|" where sce1.clazz.schedulingSubpart.uniqueId = ss.uniqueId and sce1.student.uniqueId = s.uniqueId )"
argument_list|)
operator|.
name|append
argument_list|(
literal|" order by sce.courseOffering.subjectArea.subjectAreaAbbreviation, sce.courseOffering.courseNbr,"
argument_list|)
operator|.
name|append
argument_list|(
literal|" sce.courseOffering.title, ss.itype.abbv"
argument_list|)
expr_stmt|;
if|if
condition|(
name|isShowId
argument_list|()
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|", s.externalUniqueId"
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|isShowName
argument_list|()
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|", s.lastName, s.firstName, s.middleName"
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
specifier|private
class|class
name|MissingCourseEnrollmentsAuditResult
extends|extends
name|EnrollmentAuditResult
block|{
specifier|private
name|String
name|itype
decl_stmt|;
specifier|public
name|MissingCourseEnrollmentsAuditResult
parameter_list|(
name|Object
index|[]
name|result
parameter_list|)
block|{
name|super
argument_list|(
name|result
argument_list|)
expr_stmt|;
if|if
condition|(
name|result
index|[
literal|7
index|]
operator|!=
literal|null
condition|)
name|this
operator|.
name|itype
operator|=
name|result
index|[
literal|7
index|]
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
specifier|public
name|String
name|itypeString
parameter_list|()
block|{
return|return
operator|(
name|itype
operator|)
return|;
block|}
block|}
block|}
end_class

end_unit

