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
name|action
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Date
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
name|javax
operator|.
name|servlet
operator|.
name|http
operator|.
name|HttpServletResponse
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|Action
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionForm
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionForward
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionMapping
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionMessage
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionMessages
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|util
operator|.
name|MessageResources
import|;
end_import

begin_import
import|import
name|org
operator|.
name|springframework
operator|.
name|beans
operator|.
name|factory
operator|.
name|annotation
operator|.
name|Autowired
import|;
end_import

begin_import
import|import
name|org
operator|.
name|springframework
operator|.
name|stereotype
operator|.
name|Service
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
name|Email
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
name|form
operator|.
name|InquiryForm
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
name|EventContact
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
name|security
operator|.
name|Qualifiable
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
name|security
operator|.
name|SessionContext
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
name|security
operator|.
name|rights
operator|.
name|Right
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
name|LookupTables
import|;
end_import

begin_comment
comment|/**   * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|Service
argument_list|(
literal|"/inquiry"
argument_list|)
specifier|public
class|class
name|InquiryAction
extends|extends
name|Action
block|{
annotation|@
name|Autowired
name|SessionContext
name|sessionContext
decl_stmt|;
specifier|public
name|ActionForward
name|execute
parameter_list|(
name|ActionMapping
name|mapping
parameter_list|,
name|ActionForm
name|form
parameter_list|,
name|HttpServletRequest
name|request
parameter_list|,
name|HttpServletResponse
name|response
parameter_list|)
throws|throws
name|Exception
block|{
try|try
block|{
name|InquiryForm
name|myForm
init|=
operator|(
name|InquiryForm
operator|)
name|form
decl_stmt|;
name|MessageResources
name|rsc
init|=
name|getResources
argument_list|(
name|request
argument_list|)
decl_stmt|;
comment|// Check Access
name|sessionContext
operator|.
name|checkPermission
argument_list|(
name|Right
operator|.
name|Inquiry
argument_list|)
expr_stmt|;
comment|// Read operation to be performed
name|String
name|op
init|=
operator|(
name|myForm
operator|.
name|getOp
argument_list|()
operator|!=
literal|null
condition|?
name|myForm
operator|.
name|getOp
argument_list|()
else|:
name|request
operator|.
name|getParameter
argument_list|(
literal|"op"
argument_list|)
operator|)
decl_stmt|;
name|myForm
operator|.
name|setNoRole
argument_list|(
operator|!
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|hasRight
argument_list|(
name|Right
operator|.
name|HasRole
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
literal|"Cancel"
operator|.
name|equals
argument_list|(
name|op
argument_list|)
operator|||
literal|"Back"
operator|.
name|equals
argument_list|(
name|op
argument_list|)
condition|)
block|{
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"submit"
argument_list|)
return|;
block|}
name|ActionMessages
name|errors
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|op
operator|!=
literal|null
operator|&&
name|op
operator|.
name|equals
argument_list|(
name|rsc
operator|.
name|getMessage
argument_list|(
literal|"button.insertAddress"
argument_list|)
argument_list|)
condition|)
block|{
if|if
condition|(
name|myForm
operator|.
name|getPuid
argument_list|()
operator|!=
literal|null
operator|&&
name|myForm
operator|.
name|getPuid
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|myForm
operator|.
name|addToCarbonCopy
argument_list|(
name|myForm
operator|.
name|getPuid
argument_list|()
argument_list|)
expr_stmt|;
name|myForm
operator|.
name|setPuid
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|errors
operator|=
operator|new
name|ActionMessages
argument_list|()
expr_stmt|;
name|errors
operator|.
name|add
argument_list|(
literal|"puid"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
literal|"Recipient has an invalid email address."
argument_list|)
argument_list|)
expr_stmt|;
name|saveErrors
argument_list|(
name|request
argument_list|,
name|errors
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"deleteId"
argument_list|)
operator|!=
literal|null
operator|&&
name|request
operator|.
name|getParameter
argument_list|(
literal|"deleteId"
argument_list|)
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
try|try
block|{
name|int
name|deleteId
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"deleteId"
argument_list|)
argument_list|)
decl_stmt|;
name|myForm
operator|.
name|removeCarbonCopy
argument_list|(
name|deleteId
argument_list|)
expr_stmt|;
name|myForm
operator|.
name|setPuid
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|errors
operator|=
operator|new
name|ActionMessages
argument_list|()
expr_stmt|;
name|errors
operator|.
name|add
argument_list|(
literal|"puid"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
literal|"Invalid email address."
argument_list|)
argument_list|)
expr_stmt|;
name|saveErrors
argument_list|(
name|request
argument_list|,
name|errors
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
literal|"Submit"
operator|.
name|equals
argument_list|(
name|op
argument_list|)
condition|)
block|{
name|errors
operator|=
name|myForm
operator|.
name|validate
argument_list|(
name|mapping
argument_list|,
name|request
argument_list|)
expr_stmt|;
if|if
condition|(
name|errors
operator|.
name|size
argument_list|()
operator|>
literal|0
condition|)
block|{
name|saveErrors
argument_list|(
name|request
argument_list|,
name|errors
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|String
name|mail
init|=
name|myForm
operator|.
name|getMessage
argument_list|()
decl_stmt|;
empty_stmt|;
name|mail
operator|+=
literal|"\r\n"
expr_stmt|;
name|mail
operator|+=
literal|"\r\n"
expr_stmt|;
name|mail
operator|+=
literal|"User info -------------- \r\n"
expr_stmt|;
name|mail
operator|+=
literal|"User: "
operator|+
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getName
argument_list|()
operator|+
literal|"\r\n"
expr_stmt|;
name|mail
operator|+=
literal|"Login: "
operator|+
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getUsername
argument_list|()
operator|+
literal|"\r\n"
expr_stmt|;
name|mail
operator|+=
literal|"Email: "
operator|+
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getEmail
argument_list|()
operator|+
literal|"\r\n"
expr_stmt|;
name|mail
operator|+=
literal|"Role: "
operator|+
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAuthority
argument_list|()
operator|+
literal|"\r\n"
expr_stmt|;
name|List
argument_list|<
name|?
extends|extends
name|Qualifiable
argument_list|>
name|sessions
init|=
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|getQualifiers
argument_list|(
literal|"Session"
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|sessions
operator|.
name|isEmpty
argument_list|()
condition|)
name|mail
operator|+=
literal|"Academic Session: "
operator|+
name|sessions
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|getQualifierLabel
argument_list|()
operator|+
literal|"\r\n"
expr_stmt|;
name|List
argument_list|<
name|?
extends|extends
name|Qualifiable
argument_list|>
name|depts
init|=
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|getQualifiers
argument_list|(
literal|"Department"
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|depts
operator|.
name|isEmpty
argument_list|()
condition|)
name|mail
operator|+=
literal|"Departments: "
operator|+
name|depts
operator|+
literal|"\r\n"
expr_stmt|;
name|List
argument_list|<
name|?
extends|extends
name|Qualifiable
argument_list|>
name|sg
init|=
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|getQualifiers
argument_list|(
literal|"SolverGroup"
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|sg
operator|.
name|isEmpty
argument_list|()
condition|)
name|mail
operator|+=
literal|"Solver Groups: "
operator|+
name|sg
operator|+
literal|"\r\n"
expr_stmt|;
name|mail
operator|+=
literal|"\r\n"
expr_stmt|;
name|mail
operator|+=
literal|"\r\n"
expr_stmt|;
name|mail
operator|+=
literal|"Application info -------------- \r\n"
expr_stmt|;
name|mail
operator|+=
literal|"Version: "
operator|+
name|Constants
operator|.
name|getVersion
argument_list|()
operator|+
literal|" ("
operator|+
name|Constants
operator|.
name|getReleaseDate
argument_list|()
operator|+
literal|")\r\n"
expr_stmt|;
name|mail
operator|+=
literal|"TimeStamp: "
operator|+
operator|(
operator|new
name|Date
argument_list|()
operator|)
expr_stmt|;
name|EventContact
name|c
init|=
name|EventContact
operator|.
name|findByExternalUniqueId
argument_list|(
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getExternalUserId
argument_list|()
argument_list|)
decl_stmt|;
name|Email
name|email
init|=
name|Email
operator|.
name|createEmail
argument_list|()
decl_stmt|;
name|email
operator|.
name|setSubject
argument_list|(
literal|"UniTime ("
operator|+
name|myForm
operator|.
name|getTypeMsg
argument_list|(
name|myForm
operator|.
name|getType
argument_list|()
argument_list|)
operator|+
literal|"): "
operator|+
name|myForm
operator|.
name|getSubject
argument_list|()
argument_list|)
expr_stmt|;
name|email
operator|.
name|setText
argument_list|(
name|mail
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|myForm
operator|.
name|getCarbonCopy
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
for|for
control|(
name|Iterator
name|i
init|=
name|myForm
operator|.
name|getCarbonCopy
argument_list|()
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
name|email
operator|.
name|addRecipientCC
argument_list|(
operator|(
name|String
operator|)
name|i
operator|.
name|next
argument_list|()
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"unitime.email.inquiry"
argument_list|)
operator|!=
literal|null
condition|)
name|email
operator|.
name|addRecipient
argument_list|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"unitime.email.inquiry"
argument_list|)
argument_list|,
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"unitime.email.inquiry.name"
argument_list|)
argument_list|)
expr_stmt|;
else|else
name|email
operator|.
name|addNotify
argument_list|()
expr_stmt|;
name|boolean
name|autoreply
init|=
literal|"true"
operator|.
name|equals
argument_list|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"unitime.email.inquiry.autoreply"
argument_list|,
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.inquiry.autoreply"
argument_list|,
literal|"false"
argument_list|)
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|autoreply
condition|)
block|{
if|if
condition|(
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getEmail
argument_list|()
operator|!=
literal|null
operator|&&
operator|!
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getEmail
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|email
operator|.
name|addRecipientCC
argument_list|(
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getEmail
argument_list|()
argument_list|,
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|c
operator|!=
literal|null
operator|&&
name|c
operator|.
name|getEmailAddress
argument_list|()
operator|!=
literal|null
operator|&&
operator|!
name|c
operator|.
name|getEmailAddress
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|email
operator|.
name|addRecipientCC
argument_list|(
name|c
operator|.
name|getEmailAddress
argument_list|()
argument_list|,
name|c
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|email
operator|.
name|addRecipientCC
argument_list|(
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getUsername
argument_list|()
operator|+
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"unitime.email.inquiry.suffix"
argument_list|,
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.inquiry.email.suffix"
argument_list|,
literal|"@unitime.org"
argument_list|)
argument_list|)
argument_list|,
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
name|email
operator|.
name|send
argument_list|()
expr_stmt|;
if|if
condition|(
name|autoreply
condition|)
block|{
try|try
block|{
name|mail
operator|=
literal|"The following inquiry was submitted on your behalf. "
operator|+
literal|"We will contact you soon. "
operator|+
literal|"This email was automatically generated, please do not reply.\n\n"
expr_stmt|;
if|if
condition|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.inquiry.sender.name"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|mail
operator|+=
literal|"Thank you, \n\n"
operator|+
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.inquiry.sender.name"
argument_list|)
operator|+
literal|"\n\n"
expr_stmt|;
block|}
name|mail
operator|+=
literal|"-- INQUIRY ("
operator|+
name|myForm
operator|.
name|getTypeMsg
argument_list|(
name|myForm
operator|.
name|getType
argument_list|()
argument_list|)
operator|+
literal|"): "
operator|+
name|myForm
operator|.
name|getSubject
argument_list|()
operator|+
literal|" ---------- \n\n"
operator|+
name|myForm
operator|.
name|getMessage
argument_list|()
operator|+
literal|"\n"
operator|+
literal|"-- END INQUIRY -------------------------------------------"
expr_stmt|;
name|email
operator|=
name|Email
operator|.
name|createEmail
argument_list|()
expr_stmt|;
name|email
operator|.
name|setSubject
argument_list|(
literal|"RE: UniTime ("
operator|+
name|myForm
operator|.
name|getTypeMsg
argument_list|(
name|myForm
operator|.
name|getType
argument_list|()
argument_list|)
operator|+
literal|"): "
operator|+
name|myForm
operator|.
name|getSubject
argument_list|()
argument_list|)
expr_stmt|;
name|email
operator|.
name|setText
argument_list|(
name|mail
argument_list|)
expr_stmt|;
if|if
condition|(
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getEmail
argument_list|()
operator|!=
literal|null
operator|&&
operator|!
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getEmail
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|email
operator|.
name|addRecipient
argument_list|(
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getEmail
argument_list|()
argument_list|,
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|c
operator|!=
literal|null
operator|&&
name|c
operator|.
name|getEmailAddress
argument_list|()
operator|!=
literal|null
operator|&&
operator|!
name|c
operator|.
name|getEmailAddress
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|email
operator|.
name|addRecipient
argument_list|(
name|c
operator|.
name|getEmailAddress
argument_list|()
argument_list|,
name|c
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|email
operator|.
name|addRecipient
argument_list|(
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getUsername
argument_list|()
operator|+
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"unitime.email.inquiry.suffix"
argument_list|,
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.inquiry.email.suffix"
argument_list|,
literal|"@unitime.org"
argument_list|)
argument_list|)
argument_list|,
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|email
operator|.
name|send
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
block|}
block|}
name|myForm
operator|.
name|setOp
argument_list|(
literal|"Sent"
argument_list|)
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"display"
argument_list|)
return|;
block|}
block|}
name|LookupTables
operator|.
name|setupTimetableManagers
argument_list|(
name|request
argument_list|)
expr_stmt|;
name|myForm
operator|.
name|updateMessage
argument_list|()
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"display"
argument_list|)
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
throw|throw
name|e
throw|;
block|}
block|}
block|}
end_class

end_unit

