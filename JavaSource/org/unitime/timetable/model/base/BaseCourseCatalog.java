begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 - 3.5 (University Timetabling Application)  * Copyright (C) 2010 - 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *  * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|base
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
name|HashSet
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
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|CourseCatalog
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
name|CourseSubpartCredit
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

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|BaseCourseCatalog
implements|implements
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
specifier|private
name|Long
name|iUniqueId
decl_stmt|;
specifier|private
name|String
name|iExternalUniqueId
decl_stmt|;
specifier|private
name|String
name|iSubject
decl_stmt|;
specifier|private
name|String
name|iCourseNumber
decl_stmt|;
specifier|private
name|String
name|iTitle
decl_stmt|;
specifier|private
name|String
name|iPermanentId
decl_stmt|;
specifier|private
name|String
name|iApprovalType
decl_stmt|;
specifier|private
name|String
name|iPreviousSubject
decl_stmt|;
specifier|private
name|String
name|iPreviousCourseNumber
decl_stmt|;
specifier|private
name|String
name|iCreditType
decl_stmt|;
specifier|private
name|String
name|iCreditUnitType
decl_stmt|;
specifier|private
name|String
name|iCreditFormat
decl_stmt|;
specifier|private
name|Float
name|iFixedMinimumCredit
decl_stmt|;
specifier|private
name|Float
name|iMaximumCredit
decl_stmt|;
specifier|private
name|Boolean
name|iFractionalCreditAllowed
decl_stmt|;
specifier|private
name|Session
name|iSession
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|CourseSubpartCredit
argument_list|>
name|iSubparts
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_UNIQUEID
init|=
literal|"uniqueId"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_EXTERNAL_UID
init|=
literal|"externalUniqueId"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_SUBJECT
init|=
literal|"subject"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_COURSE_NBR
init|=
literal|"courseNumber"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_TITLE
init|=
literal|"title"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_PERM_ID
init|=
literal|"permanentId"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_APPROVAL_TYPE
init|=
literal|"approvalType"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_PREV_SUBJECT
init|=
literal|"previousSubject"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_PREV_CRS_NBR
init|=
literal|"previousCourseNumber"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_CREDIT_TYPE
init|=
literal|"creditType"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_CREDIT_UNIT_TYPE
init|=
literal|"creditUnitType"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_CREDIT_FORMAT
init|=
literal|"creditFormat"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_FIXED_MIN_CREDIT
init|=
literal|"fixedMinimumCredit"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_MAX_CREDIT
init|=
literal|"maximumCredit"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_FRAC_CREDIT_ALLOWED
init|=
literal|"fractionalCreditAllowed"
decl_stmt|;
specifier|public
name|BaseCourseCatalog
parameter_list|()
block|{
name|initialize
argument_list|()
expr_stmt|;
block|}
specifier|public
name|BaseCourseCatalog
parameter_list|(
name|Long
name|uniqueId
parameter_list|)
block|{
name|setUniqueId
argument_list|(
name|uniqueId
argument_list|)
expr_stmt|;
name|initialize
argument_list|()
expr_stmt|;
block|}
specifier|protected
name|void
name|initialize
parameter_list|()
block|{
block|}
specifier|public
name|Long
name|getUniqueId
parameter_list|()
block|{
return|return
name|iUniqueId
return|;
block|}
specifier|public
name|void
name|setUniqueId
parameter_list|(
name|Long
name|uniqueId
parameter_list|)
block|{
name|iUniqueId
operator|=
name|uniqueId
expr_stmt|;
block|}
specifier|public
name|String
name|getExternalUniqueId
parameter_list|()
block|{
return|return
name|iExternalUniqueId
return|;
block|}
specifier|public
name|void
name|setExternalUniqueId
parameter_list|(
name|String
name|externalUniqueId
parameter_list|)
block|{
name|iExternalUniqueId
operator|=
name|externalUniqueId
expr_stmt|;
block|}
specifier|public
name|String
name|getSubject
parameter_list|()
block|{
return|return
name|iSubject
return|;
block|}
specifier|public
name|void
name|setSubject
parameter_list|(
name|String
name|subject
parameter_list|)
block|{
name|iSubject
operator|=
name|subject
expr_stmt|;
block|}
specifier|public
name|String
name|getCourseNumber
parameter_list|()
block|{
return|return
name|iCourseNumber
return|;
block|}
specifier|public
name|void
name|setCourseNumber
parameter_list|(
name|String
name|courseNumber
parameter_list|)
block|{
name|iCourseNumber
operator|=
name|courseNumber
expr_stmt|;
block|}
specifier|public
name|String
name|getTitle
parameter_list|()
block|{
return|return
name|iTitle
return|;
block|}
specifier|public
name|void
name|setTitle
parameter_list|(
name|String
name|title
parameter_list|)
block|{
name|iTitle
operator|=
name|title
expr_stmt|;
block|}
specifier|public
name|String
name|getPermanentId
parameter_list|()
block|{
return|return
name|iPermanentId
return|;
block|}
specifier|public
name|void
name|setPermanentId
parameter_list|(
name|String
name|permanentId
parameter_list|)
block|{
name|iPermanentId
operator|=
name|permanentId
expr_stmt|;
block|}
specifier|public
name|String
name|getApprovalType
parameter_list|()
block|{
return|return
name|iApprovalType
return|;
block|}
specifier|public
name|void
name|setApprovalType
parameter_list|(
name|String
name|approvalType
parameter_list|)
block|{
name|iApprovalType
operator|=
name|approvalType
expr_stmt|;
block|}
specifier|public
name|String
name|getPreviousSubject
parameter_list|()
block|{
return|return
name|iPreviousSubject
return|;
block|}
specifier|public
name|void
name|setPreviousSubject
parameter_list|(
name|String
name|previousSubject
parameter_list|)
block|{
name|iPreviousSubject
operator|=
name|previousSubject
expr_stmt|;
block|}
specifier|public
name|String
name|getPreviousCourseNumber
parameter_list|()
block|{
return|return
name|iPreviousCourseNumber
return|;
block|}
specifier|public
name|void
name|setPreviousCourseNumber
parameter_list|(
name|String
name|previousCourseNumber
parameter_list|)
block|{
name|iPreviousCourseNumber
operator|=
name|previousCourseNumber
expr_stmt|;
block|}
specifier|public
name|String
name|getCreditType
parameter_list|()
block|{
return|return
name|iCreditType
return|;
block|}
specifier|public
name|void
name|setCreditType
parameter_list|(
name|String
name|creditType
parameter_list|)
block|{
name|iCreditType
operator|=
name|creditType
expr_stmt|;
block|}
specifier|public
name|String
name|getCreditUnitType
parameter_list|()
block|{
return|return
name|iCreditUnitType
return|;
block|}
specifier|public
name|void
name|setCreditUnitType
parameter_list|(
name|String
name|creditUnitType
parameter_list|)
block|{
name|iCreditUnitType
operator|=
name|creditUnitType
expr_stmt|;
block|}
specifier|public
name|String
name|getCreditFormat
parameter_list|()
block|{
return|return
name|iCreditFormat
return|;
block|}
specifier|public
name|void
name|setCreditFormat
parameter_list|(
name|String
name|creditFormat
parameter_list|)
block|{
name|iCreditFormat
operator|=
name|creditFormat
expr_stmt|;
block|}
specifier|public
name|Float
name|getFixedMinimumCredit
parameter_list|()
block|{
return|return
name|iFixedMinimumCredit
return|;
block|}
specifier|public
name|void
name|setFixedMinimumCredit
parameter_list|(
name|Float
name|fixedMinimumCredit
parameter_list|)
block|{
name|iFixedMinimumCredit
operator|=
name|fixedMinimumCredit
expr_stmt|;
block|}
specifier|public
name|Float
name|getMaximumCredit
parameter_list|()
block|{
return|return
name|iMaximumCredit
return|;
block|}
specifier|public
name|void
name|setMaximumCredit
parameter_list|(
name|Float
name|maximumCredit
parameter_list|)
block|{
name|iMaximumCredit
operator|=
name|maximumCredit
expr_stmt|;
block|}
specifier|public
name|Boolean
name|isFractionalCreditAllowed
parameter_list|()
block|{
return|return
name|iFractionalCreditAllowed
return|;
block|}
specifier|public
name|Boolean
name|getFractionalCreditAllowed
parameter_list|()
block|{
return|return
name|iFractionalCreditAllowed
return|;
block|}
specifier|public
name|void
name|setFractionalCreditAllowed
parameter_list|(
name|Boolean
name|fractionalCreditAllowed
parameter_list|)
block|{
name|iFractionalCreditAllowed
operator|=
name|fractionalCreditAllowed
expr_stmt|;
block|}
specifier|public
name|Session
name|getSession
parameter_list|()
block|{
return|return
name|iSession
return|;
block|}
specifier|public
name|void
name|setSession
parameter_list|(
name|Session
name|session
parameter_list|)
block|{
name|iSession
operator|=
name|session
expr_stmt|;
block|}
specifier|public
name|Set
argument_list|<
name|CourseSubpartCredit
argument_list|>
name|getSubparts
parameter_list|()
block|{
return|return
name|iSubparts
return|;
block|}
specifier|public
name|void
name|setSubparts
parameter_list|(
name|Set
argument_list|<
name|CourseSubpartCredit
argument_list|>
name|subparts
parameter_list|)
block|{
name|iSubparts
operator|=
name|subparts
expr_stmt|;
block|}
specifier|public
name|void
name|addTosubparts
parameter_list|(
name|CourseSubpartCredit
name|courseSubpartCredit
parameter_list|)
block|{
if|if
condition|(
name|iSubparts
operator|==
literal|null
condition|)
name|iSubparts
operator|=
operator|new
name|HashSet
argument_list|<
name|CourseSubpartCredit
argument_list|>
argument_list|()
expr_stmt|;
name|iSubparts
operator|.
name|add
argument_list|(
name|courseSubpartCredit
argument_list|)
expr_stmt|;
block|}
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
if|if
condition|(
name|o
operator|==
literal|null
operator|||
operator|!
operator|(
name|o
operator|instanceof
name|CourseCatalog
operator|)
condition|)
return|return
literal|false
return|;
if|if
condition|(
name|getUniqueId
argument_list|()
operator|==
literal|null
operator|||
operator|(
operator|(
name|CourseCatalog
operator|)
name|o
operator|)
operator|.
name|getUniqueId
argument_list|()
operator|==
literal|null
condition|)
return|return
literal|false
return|;
return|return
name|getUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
operator|(
operator|(
name|CourseCatalog
operator|)
name|o
operator|)
operator|.
name|getUniqueId
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
if|if
condition|(
name|getUniqueId
argument_list|()
operator|==
literal|null
condition|)
return|return
name|super
operator|.
name|hashCode
argument_list|()
return|;
return|return
name|getUniqueId
argument_list|()
operator|.
name|hashCode
argument_list|()
return|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
literal|"CourseCatalog["
operator|+
name|getUniqueId
argument_list|()
operator|+
literal|"]"
return|;
block|}
specifier|public
name|String
name|toDebugString
parameter_list|()
block|{
return|return
literal|"CourseCatalog["
operator|+
literal|"\n	ApprovalType: "
operator|+
name|getApprovalType
argument_list|()
operator|+
literal|"\n	CourseNumber: "
operator|+
name|getCourseNumber
argument_list|()
operator|+
literal|"\n	CreditFormat: "
operator|+
name|getCreditFormat
argument_list|()
operator|+
literal|"\n	CreditType: "
operator|+
name|getCreditType
argument_list|()
operator|+
literal|"\n	CreditUnitType: "
operator|+
name|getCreditUnitType
argument_list|()
operator|+
literal|"\n	ExternalUniqueId: "
operator|+
name|getExternalUniqueId
argument_list|()
operator|+
literal|"\n	FixedMinimumCredit: "
operator|+
name|getFixedMinimumCredit
argument_list|()
operator|+
literal|"\n	FractionalCreditAllowed: "
operator|+
name|getFractionalCreditAllowed
argument_list|()
operator|+
literal|"\n	MaximumCredit: "
operator|+
name|getMaximumCredit
argument_list|()
operator|+
literal|"\n	PermanentId: "
operator|+
name|getPermanentId
argument_list|()
operator|+
literal|"\n	PreviousCourseNumber: "
operator|+
name|getPreviousCourseNumber
argument_list|()
operator|+
literal|"\n	PreviousSubject: "
operator|+
name|getPreviousSubject
argument_list|()
operator|+
literal|"\n	Session: "
operator|+
name|getSession
argument_list|()
operator|+
literal|"\n	Subject: "
operator|+
name|getSubject
argument_list|()
operator|+
literal|"\n	Title: "
operator|+
name|getTitle
argument_list|()
operator|+
literal|"\n	UniqueId: "
operator|+
name|getUniqueId
argument_list|()
operator|+
literal|"]"
return|;
block|}
block|}
end_class

end_unit

