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
name|Hashtable
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
name|StudentClassEnrollmentDAO
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
name|reports
operator|.
name|PdfLegacyReport
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
name|DocumentException
import|;
end_import

begin_comment
comment|/**  * @author Stephanie Schluttenhofer  *  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|PdfEnrollmentAuditReport
extends|extends
name|PdfLegacyReport
block|{
specifier|public
specifier|static
name|Hashtable
argument_list|<
name|String
argument_list|,
name|Class
argument_list|>
name|sRegisteredReports
init|=
operator|new
name|Hashtable
argument_list|<
name|String
argument_list|,
name|Class
argument_list|>
argument_list|()
decl_stmt|;
specifier|public
specifier|static
name|String
name|sAllRegisteredReports
init|=
literal|""
decl_stmt|;
specifier|protected
specifier|static
name|int
name|studentIdLength
init|=
literal|10
decl_stmt|;
specifier|protected
specifier|static
name|int
name|studentNameLength
init|=
literal|23
decl_stmt|;
specifier|protected
specifier|static
name|int
name|offeringNameLength
init|=
literal|45
decl_stmt|;
specifier|protected
specifier|static
name|int
name|classNameLength
init|=
literal|14
decl_stmt|;
specifier|protected
specifier|static
name|int
name|itypeLength
init|=
literal|7
decl_stmt|;
specifier|protected
specifier|static
name|int
name|multipleClassesLength
init|=
literal|30
decl_stmt|;
static|static
block|{
name|sRegisteredReports
operator|.
name|put
argument_list|(
literal|"struct"
argument_list|,
name|EnrollmentsViolatingCourseStructureAuditReport
operator|.
name|class
argument_list|)
expr_stmt|;
name|sRegisteredReports
operator|.
name|put
argument_list|(
literal|"missing"
argument_list|,
name|MissingCourseEnrollmentsAuditReport
operator|.
name|class
argument_list|)
expr_stmt|;
name|sRegisteredReports
operator|.
name|put
argument_list|(
literal|"many-subp"
argument_list|,
name|MultipleCourseEnrollmentsAuditReport
operator|.
name|class
argument_list|)
expr_stmt|;
name|sRegisteredReports
operator|.
name|put
argument_list|(
literal|"many-conf"
argument_list|,
name|MultipleConfigEnrollmentsAuditReport
operator|.
name|class
argument_list|)
expr_stmt|;
for|for
control|(
name|String
name|report
range|:
name|sRegisteredReports
operator|.
name|keySet
argument_list|()
control|)
name|sAllRegisteredReports
operator|+=
operator|(
name|sAllRegisteredReports
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|?
literal|","
else|:
literal|""
operator|)
operator|+
name|report
expr_stmt|;
block|}
specifier|private
name|Session
name|iSession
init|=
literal|null
decl_stmt|;
specifier|private
name|boolean
name|iShowId
decl_stmt|;
specifier|private
name|boolean
name|iShowName
decl_stmt|;
specifier|private
name|TreeSet
argument_list|<
name|SubjectArea
argument_list|>
name|iSubjectAreas
decl_stmt|;
comment|/** 	 * @param mode 	 * @param file 	 * @param title 	 * @param title2 	 * @param subject 	 * @param session 	 * @throws IOException 	 * @throws DocumentException 	 */
specifier|public
name|PdfEnrollmentAuditReport
parameter_list|(
name|int
name|mode
parameter_list|,
name|File
name|file
parameter_list|,
name|String
name|title
parameter_list|,
name|String
name|title2
parameter_list|,
name|String
name|subject
parameter_list|,
name|String
name|session
parameter_list|)
throws|throws
name|IOException
throws|,
name|DocumentException
block|{
name|super
argument_list|(
name|mode
argument_list|,
name|file
argument_list|,
name|title
argument_list|,
name|title2
argument_list|,
name|subject
argument_list|,
name|session
argument_list|)
expr_stmt|;
block|}
specifier|public
name|PdfEnrollmentAuditReport
parameter_list|(
name|int
name|mode
parameter_list|,
name|String
name|title
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
name|file
argument_list|,
name|title
argument_list|,
name|subTitle
argument_list|,
name|title
operator|+
literal|" -- "
operator|+
name|session
operator|.
name|getLabel
argument_list|()
argument_list|,
name|session
operator|.
name|getLabel
argument_list|()
argument_list|)
expr_stmt|;
name|this
operator|.
name|iSession
operator|=
name|session
expr_stmt|;
name|this
operator|.
name|iSubjectAreas
operator|=
name|subjectAreas
expr_stmt|;
block|}
specifier|public
name|PdfEnrollmentAuditReport
parameter_list|(
name|int
name|mode
parameter_list|,
name|String
name|title
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
name|file
argument_list|,
name|title
argument_list|,
literal|""
argument_list|,
name|title
operator|+
literal|" -- "
operator|+
name|session
operator|.
name|getLabel
argument_list|()
argument_list|,
name|session
operator|.
name|getLabel
argument_list|()
argument_list|)
expr_stmt|;
name|iSession
operator|=
name|session
expr_stmt|;
name|this
operator|.
name|iSubjectAreas
operator|=
literal|null
expr_stmt|;
block|}
specifier|public
specifier|abstract
name|void
name|printReport
parameter_list|()
throws|throws
name|DocumentException
function_decl|;
comment|/**  * @return the iSession  */
specifier|public
name|Session
name|getSession
parameter_list|()
block|{
return|return
name|iSession
return|;
block|}
comment|/**  * @param iSession the iSession to set  */
specifier|public
name|void
name|setSession
parameter_list|(
name|Session
name|session
parameter_list|)
block|{
name|this
operator|.
name|iSession
operator|=
name|session
expr_stmt|;
block|}
comment|/**  * @return the showId  */
specifier|public
name|boolean
name|isShowId
parameter_list|()
block|{
return|return
name|iShowId
return|;
block|}
comment|/**  * @param showId the showId to set  */
specifier|public
name|void
name|setShowId
parameter_list|(
name|boolean
name|showId
parameter_list|)
block|{
name|this
operator|.
name|iShowId
operator|=
name|showId
expr_stmt|;
block|}
comment|/**  * @return the showName  */
specifier|public
name|boolean
name|isShowName
parameter_list|()
block|{
return|return
name|iShowName
return|;
block|}
comment|/**  * @param showName the showName to set  */
specifier|public
name|void
name|setShowName
parameter_list|(
name|boolean
name|showName
parameter_list|)
block|{
name|this
operator|.
name|iShowName
operator|=
name|showName
expr_stmt|;
block|}
comment|/**  * @return the subjectAreas  */
specifier|public
name|TreeSet
argument_list|<
name|SubjectArea
argument_list|>
name|getSubjectAreas
parameter_list|()
block|{
return|return
name|iSubjectAreas
return|;
block|}
comment|/**  * @param subjectAreas the subjectAreas to set  */
specifier|public
name|void
name|setSubjectAreas
parameter_list|(
name|TreeSet
argument_list|<
name|SubjectArea
argument_list|>
name|subjectAreas
parameter_list|)
block|{
name|this
operator|.
name|iSubjectAreas
operator|=
name|subjectAreas
expr_stmt|;
block|}
specifier|protected
specifier|abstract
name|String
name|createQueryString
parameter_list|(
name|TreeSet
argument_list|<
name|SubjectArea
argument_list|>
name|subjectAreas
parameter_list|)
function_decl|;
specifier|protected
name|List
name|getAuditResults
parameter_list|(
name|TreeSet
argument_list|<
name|SubjectArea
argument_list|>
name|subjectAreas
parameter_list|)
block|{
name|String
name|query
init|=
name|createQueryString
argument_list|(
name|subjectAreas
argument_list|)
decl_stmt|;
return|return
operator|(
name|StudentClassEnrollmentDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getQuery
argument_list|(
name|query
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessId"
argument_list|,
name|getSession
argument_list|()
operator|.
name|getUniqueId
argument_list|()
operator|.
name|longValue
argument_list|()
argument_list|)
operator|.
name|list
argument_list|()
operator|)
return|;
block|}
specifier|protected
name|String
name|buildBaseAuditLine
parameter_list|(
name|EnrollmentAuditResult
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
literal|" "
argument_list|)
operator|.
name|append
argument_list|(
name|lpad
argument_list|(
name|result
operator|.
name|getStudentId
argument_list|()
argument_list|,
literal|' '
argument_list|,
name|studentIdLength
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|isShowName
argument_list|()
condition|)
block|{
name|sb
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
name|getStudentName
argument_list|()
argument_list|,
literal|' '
argument_list|,
name|studentNameLength
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|sb
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
name|getOffering
argument_list|()
argument_list|,
literal|' '
argument_list|,
name|offeringNameLength
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
index|[]
name|getBaseHeader
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
if|if
condition|(
name|isShowId
argument_list|()
condition|)
block|{
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
literal|""
argument_list|,
literal|' '
argument_list|,
name|studentIdLength
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
literal|"Student ID"
argument_list|,
literal|' '
argument_list|,
name|studentIdLength
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
name|studentIdLength
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|isShowName
argument_list|()
condition|)
block|{
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
literal|""
argument_list|,
literal|' '
argument_list|,
name|studentNameLength
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
literal|"Name"
argument_list|,
literal|' '
argument_list|,
name|studentNameLength
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
name|studentNameLength
argument_list|)
argument_list|)
expr_stmt|;
block|}
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
literal|""
argument_list|,
literal|' '
argument_list|,
name|offeringNameLength
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
literal|"Offering"
argument_list|,
literal|' '
argument_list|,
name|offeringNameLength
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
name|offeringNameLength
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
specifier|abstract
class|class
name|EnrollmentAuditResult
block|{
specifier|private
name|String
name|studentId
decl_stmt|;
specifier|private
name|String
name|studentLastName
decl_stmt|;
specifier|private
name|String
name|studentFirstName
decl_stmt|;
specifier|private
name|String
name|studentMiddleName
decl_stmt|;
specifier|private
name|String
name|subjectArea
decl_stmt|;
specifier|private
name|String
name|courseNbr
decl_stmt|;
specifier|private
name|String
name|title
decl_stmt|;
specifier|public
name|EnrollmentAuditResult
parameter_list|(
name|Object
index|[]
name|result
parameter_list|)
block|{
name|super
argument_list|()
expr_stmt|;
if|if
condition|(
name|result
index|[
literal|0
index|]
operator|!=
literal|null
condition|)
name|this
operator|.
name|studentId
operator|=
name|result
index|[
literal|0
index|]
operator|.
name|toString
argument_list|()
operator|.
name|trim
argument_list|()
expr_stmt|;
if|if
condition|(
name|result
index|[
literal|1
index|]
operator|!=
literal|null
condition|)
name|this
operator|.
name|studentLastName
operator|=
name|result
index|[
literal|1
index|]
operator|.
name|toString
argument_list|()
expr_stmt|;
if|if
condition|(
name|result
index|[
literal|2
index|]
operator|!=
literal|null
condition|)
name|this
operator|.
name|studentFirstName
operator|=
name|result
index|[
literal|2
index|]
operator|.
name|toString
argument_list|()
expr_stmt|;
if|if
condition|(
name|result
index|[
literal|3
index|]
operator|!=
literal|null
condition|)
name|this
operator|.
name|studentMiddleName
operator|=
name|result
index|[
literal|3
index|]
operator|.
name|toString
argument_list|()
expr_stmt|;
if|if
condition|(
name|result
index|[
literal|4
index|]
operator|!=
literal|null
condition|)
name|this
operator|.
name|subjectArea
operator|=
name|result
index|[
literal|4
index|]
operator|.
name|toString
argument_list|()
expr_stmt|;
if|if
condition|(
name|result
index|[
literal|5
index|]
operator|!=
literal|null
condition|)
name|this
operator|.
name|courseNbr
operator|=
name|result
index|[
literal|5
index|]
operator|.
name|toString
argument_list|()
expr_stmt|;
if|if
condition|(
name|result
index|[
literal|6
index|]
operator|!=
literal|null
condition|)
name|this
operator|.
name|title
operator|=
name|result
index|[
literal|6
index|]
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
specifier|public
name|String
name|getStudentName
parameter_list|()
block|{
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
if|if
condition|(
name|studentLastName
operator|!=
literal|null
operator|&&
name|studentLastName
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|studentLastName
argument_list|)
expr_stmt|;
if|if
condition|(
name|studentFirstName
operator|!=
literal|null
operator|&&
name|studentFirstName
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|", "
argument_list|)
operator|.
name|append
argument_list|(
name|studentFirstName
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|studentMiddleName
operator|!=
literal|null
operator|&&
name|studentMiddleName
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|" "
argument_list|)
operator|.
name|append
argument_list|(
name|studentMiddleName
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
name|String
name|name
init|=
name|sb
operator|.
name|toString
argument_list|()
decl_stmt|;
if|if
condition|(
name|name
operator|.
name|length
argument_list|()
operator|>
name|studentNameLength
condition|)
block|{
name|name
operator|=
name|name
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|studentNameLength
argument_list|)
expr_stmt|;
block|}
return|return
operator|(
name|name
operator|)
return|;
block|}
comment|/** 	 * @return the studentId 	 */
specifier|public
name|String
name|getStudentId
parameter_list|()
block|{
return|return
name|studentId
return|;
block|}
specifier|public
name|String
name|getOffering
parameter_list|()
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
name|subjectArea
argument_list|)
operator|.
name|append
argument_list|(
literal|" "
argument_list|)
operator|.
name|append
argument_list|(
name|courseNbr
argument_list|)
operator|.
name|append
argument_list|(
literal|" - "
argument_list|)
operator|.
name|append
argument_list|(
name|title
argument_list|)
expr_stmt|;
name|String
name|title
init|=
name|sb
operator|.
name|toString
argument_list|()
decl_stmt|;
if|if
condition|(
name|title
operator|.
name|length
argument_list|()
operator|>
name|offeringNameLength
condition|)
block|{
name|title
operator|=
name|title
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|offeringNameLength
argument_list|)
expr_stmt|;
block|}
return|return
operator|(
name|title
operator|)
return|;
block|}
specifier|protected
name|String
name|createClassString
parameter_list|(
name|String
name|itypeStr
parameter_list|,
name|String
name|nbrStr
parameter_list|,
name|String
name|suffixStr
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
name|itypeStr
argument_list|)
expr_stmt|;
if|if
condition|(
name|nbrStr
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
argument_list|)
operator|.
name|append
argument_list|(
name|nbrStr
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|suffixStr
operator|.
name|trim
argument_list|()
operator|.
name|equals
argument_list|(
literal|"-"
argument_list|)
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|"("
argument_list|)
operator|.
name|append
argument_list|(
name|suffixStr
argument_list|)
operator|.
name|append
argument_list|(
literal|")"
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
block|}
block|}
end_class

end_unit

