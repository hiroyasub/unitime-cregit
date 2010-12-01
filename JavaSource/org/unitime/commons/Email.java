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
name|commons
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
name|io
operator|.
name|InputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|OutputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|UnsupportedEncodingException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|text
operator|.
name|DecimalFormat
import|;
end_import

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
name|Properties
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|activation
operator|.
name|DataHandler
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|activation
operator|.
name|DataSource
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|activation
operator|.
name|FileDataSource
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|mail
operator|.
name|Authenticator
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|mail
operator|.
name|BodyPart
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|mail
operator|.
name|MessagingException
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|mail
operator|.
name|Multipart
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|mail
operator|.
name|PasswordAuthentication
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|mail
operator|.
name|Transport
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|mail
operator|.
name|Message
operator|.
name|RecipientType
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|mail
operator|.
name|internet
operator|.
name|InternetAddress
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|mail
operator|.
name|internet
operator|.
name|MimeBodyPart
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|mail
operator|.
name|internet
operator|.
name|MimeMessage
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|mail
operator|.
name|internet
operator|.
name|MimeMultipart
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|Log
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|LogFactory
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
name|upload
operator|.
name|FormFile
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

begin_class
specifier|public
class|class
name|Email
block|{
specifier|private
specifier|static
name|Log
name|sLog
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|Email
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|javax
operator|.
name|mail
operator|.
name|Session
name|iMailSession
init|=
literal|null
decl_stmt|;
specifier|private
name|MimeMessage
name|iMail
init|=
literal|null
decl_stmt|;
specifier|private
name|Multipart
name|iBody
init|=
literal|null
decl_stmt|;
specifier|public
name|Email
parameter_list|()
block|{
name|Properties
name|p
init|=
name|ApplicationProperties
operator|.
name|getProperties
argument_list|()
decl_stmt|;
if|if
condition|(
name|p
operator|.
name|getProperty
argument_list|(
literal|"mail.smtp.host"
argument_list|)
operator|==
literal|null
operator|&&
name|p
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.smtp.host"
argument_list|)
operator|!=
literal|null
condition|)
name|p
operator|.
name|setProperty
argument_list|(
literal|"mail.smtp.host"
argument_list|,
name|p
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.smtp.host"
argument_list|)
argument_list|)
expr_stmt|;
specifier|final
name|String
name|user
init|=
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"mail.smtp.user"
argument_list|,
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"unitime.email.user"
argument_list|,
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.mail.user"
argument_list|)
argument_list|)
argument_list|)
decl_stmt|;
specifier|final
name|String
name|password
init|=
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"mail.smtp.password"
argument_list|,
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"unitime.email.password"
argument_list|,
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.mail.pwd"
argument_list|)
argument_list|)
argument_list|)
decl_stmt|;
name|Authenticator
name|a
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|user
operator|!=
literal|null
operator|&&
name|password
operator|!=
literal|null
condition|)
block|{
name|p
operator|.
name|setProperty
argument_list|(
literal|"mail.smtp.user"
argument_list|,
name|user
argument_list|)
expr_stmt|;
name|p
operator|.
name|setProperty
argument_list|(
literal|"mail.smtp.auth"
argument_list|,
literal|"true"
argument_list|)
expr_stmt|;
name|a
operator|=
operator|new
name|Authenticator
argument_list|()
block|{
specifier|public
name|PasswordAuthentication
name|getPasswordAuthentication
parameter_list|()
block|{
return|return
operator|new
name|PasswordAuthentication
argument_list|(
name|user
argument_list|,
name|password
argument_list|)
return|;
block|}
block|}
expr_stmt|;
block|}
name|iMailSession
operator|=
name|javax
operator|.
name|mail
operator|.
name|Session
operator|.
name|getDefaultInstance
argument_list|(
name|p
argument_list|,
name|a
argument_list|)
expr_stmt|;
name|iMail
operator|=
operator|new
name|MimeMessage
argument_list|(
name|iMailSession
argument_list|)
expr_stmt|;
name|iBody
operator|=
operator|new
name|MimeMultipart
argument_list|()
expr_stmt|;
block|}
specifier|public
specifier|static
name|boolean
name|isEnabled
parameter_list|()
block|{
return|return
operator|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.smtp.host"
argument_list|)
operator|!=
literal|null
operator|&&
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.smtp.host"
argument_list|)
operator|.
name|length
argument_list|()
operator|>
literal|0
operator|)
operator|||
operator|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"mail.smtp.host"
argument_list|)
operator|!=
literal|null
operator|&&
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"mail.smtp.host"
argument_list|)
operator|.
name|length
argument_list|()
operator|>
literal|0
operator|)
return|;
block|}
specifier|public
name|void
name|setSubject
parameter_list|(
name|String
name|subject
parameter_list|)
throws|throws
name|MessagingException
block|{
name|iMail
operator|.
name|setSubject
argument_list|(
name|subject
argument_list|)
expr_stmt|;
block|}
specifier|private
name|void
name|setFrom
parameter_list|(
name|String
name|email
parameter_list|,
name|String
name|name
parameter_list|)
throws|throws
name|MessagingException
throws|,
name|UnsupportedEncodingException
block|{
if|if
condition|(
name|email
operator|!=
literal|null
condition|)
name|iMail
operator|.
name|setFrom
argument_list|(
operator|new
name|InternetAddress
argument_list|(
name|email
argument_list|,
name|name
argument_list|)
argument_list|)
expr_stmt|;
block|}
specifier|private
name|void
name|setReplyTo
parameter_list|(
name|String
name|email
parameter_list|,
name|String
name|name
parameter_list|)
throws|throws
name|UnsupportedEncodingException
throws|,
name|MessagingException
block|{
if|if
condition|(
name|email
operator|!=
literal|null
condition|)
name|iMail
operator|.
name|setReplyTo
argument_list|(
operator|new
name|InternetAddress
index|[]
block|{
operator|new
name|InternetAddress
argument_list|(
name|email
argument_list|,
name|name
argument_list|)
block|}
argument_list|)
expr_stmt|;
block|}
specifier|private
name|void
name|addRecipient
parameter_list|(
name|RecipientType
name|type
parameter_list|,
name|String
name|email
parameter_list|,
name|String
name|name
parameter_list|)
throws|throws
name|UnsupportedEncodingException
throws|,
name|MessagingException
block|{
name|iMail
operator|.
name|addRecipient
argument_list|(
name|type
argument_list|,
operator|new
name|InternetAddress
argument_list|(
name|email
argument_list|,
name|name
argument_list|)
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|addRecipient
parameter_list|(
name|String
name|email
parameter_list|,
name|String
name|name
parameter_list|)
throws|throws
name|UnsupportedEncodingException
throws|,
name|MessagingException
block|{
name|addRecipient
argument_list|(
name|RecipientType
operator|.
name|TO
argument_list|,
name|email
argument_list|,
name|name
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|addRecipientCC
parameter_list|(
name|String
name|email
parameter_list|,
name|String
name|name
parameter_list|)
throws|throws
name|UnsupportedEncodingException
throws|,
name|MessagingException
block|{
name|addRecipient
argument_list|(
name|RecipientType
operator|.
name|CC
argument_list|,
name|email
argument_list|,
name|name
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|addRecipientBCC
parameter_list|(
name|String
name|email
parameter_list|,
name|String
name|name
parameter_list|)
throws|throws
name|UnsupportedEncodingException
throws|,
name|MessagingException
block|{
name|addRecipient
argument_list|(
name|RecipientType
operator|.
name|BCC
argument_list|,
name|email
argument_list|,
name|name
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|setText
parameter_list|(
name|String
name|message
parameter_list|)
throws|throws
name|MessagingException
block|{
name|MimeBodyPart
name|text
init|=
operator|new
name|MimeBodyPart
argument_list|()
decl_stmt|;
name|text
operator|.
name|setContent
argument_list|(
name|message
argument_list|,
literal|"text/plain"
argument_list|)
expr_stmt|;
name|iBody
operator|.
name|addBodyPart
argument_list|(
name|text
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|setHTML
parameter_list|(
name|String
name|message
parameter_list|)
throws|throws
name|MessagingException
block|{
name|MimeBodyPart
name|text
init|=
operator|new
name|MimeBodyPart
argument_list|()
decl_stmt|;
name|text
operator|.
name|setContent
argument_list|(
name|message
argument_list|,
literal|"text/html"
argument_list|)
expr_stmt|;
name|iBody
operator|.
name|addBodyPart
argument_list|(
name|text
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|addNotify
parameter_list|(
name|RecipientType
name|type
parameter_list|)
throws|throws
name|MessagingException
throws|,
name|UnsupportedEncodingException
block|{
name|iMail
operator|.
name|addRecipient
argument_list|(
name|RecipientType
operator|.
name|TO
argument_list|,
operator|new
name|InternetAddress
argument_list|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"unitime.email.notif"
argument_list|,
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.notif.email"
argument_list|,
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.notif.commit.email"
argument_list|)
argument_list|)
argument_list|)
argument_list|,
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"unitime.email.notif.name"
argument_list|,
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.notif.email.name"
argument_list|,
literal|"UniTime Operator"
argument_list|)
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|addNotify
parameter_list|()
throws|throws
name|MessagingException
throws|,
name|UnsupportedEncodingException
block|{
name|addNotify
argument_list|(
name|RecipientType
operator|.
name|TO
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|addNotifyCC
parameter_list|()
throws|throws
name|MessagingException
throws|,
name|UnsupportedEncodingException
block|{
name|addNotify
argument_list|(
name|RecipientType
operator|.
name|CC
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|addAttachement
parameter_list|(
name|File
name|file
parameter_list|,
name|String
name|name
parameter_list|)
throws|throws
name|MessagingException
block|{
name|BodyPart
name|attachement
init|=
operator|new
name|MimeBodyPart
argument_list|()
decl_stmt|;
name|attachement
operator|.
name|setDataHandler
argument_list|(
operator|new
name|DataHandler
argument_list|(
operator|new
name|FileDataSource
argument_list|(
name|file
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|attachement
operator|.
name|setFileName
argument_list|(
name|name
operator|==
literal|null
condition|?
name|file
operator|.
name|getName
argument_list|()
else|:
name|name
argument_list|)
expr_stmt|;
name|iBody
operator|.
name|addBodyPart
argument_list|(
name|attachement
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|addAttachement
parameter_list|(
specifier|final
name|FormFile
name|file
parameter_list|)
throws|throws
name|MessagingException
block|{
name|BodyPart
name|attachement
init|=
operator|new
name|MimeBodyPart
argument_list|()
decl_stmt|;
name|attachement
operator|.
name|setDataHandler
argument_list|(
operator|new
name|DataHandler
argument_list|(
operator|new
name|DataSource
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|OutputStream
name|getOutputStream
parameter_list|()
throws|throws
name|IOException
block|{
throw|throw
operator|new
name|IOException
argument_list|(
literal|"No output stream."
argument_list|)
throw|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|file
operator|.
name|getFileName
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|InputStream
name|getInputStream
parameter_list|()
throws|throws
name|IOException
block|{
return|return
name|file
operator|.
name|getInputStream
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getContentType
parameter_list|()
block|{
return|return
name|file
operator|.
name|getContentType
argument_list|()
return|;
block|}
block|}
argument_list|)
argument_list|)
expr_stmt|;
name|attachement
operator|.
name|setFileName
argument_list|(
name|file
operator|.
name|getFileName
argument_list|()
argument_list|)
expr_stmt|;
name|iBody
operator|.
name|addBodyPart
argument_list|(
name|attachement
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|send
parameter_list|()
throws|throws
name|MessagingException
throws|,
name|UnsupportedEncodingException
block|{
name|long
name|t0
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
try|try
block|{
name|setFrom
argument_list|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"unitime.email.sender"
argument_list|,
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.inquiry.sender"
argument_list|,
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.contact.email"
argument_list|)
argument_list|)
argument_list|)
argument_list|,
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"unitime.email.sender.name"
argument_list|,
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.inquiry.sender.name"
argument_list|,
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.contact.email.name"
argument_list|,
literal|"UniTime Email"
argument_list|)
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|setReplyTo
argument_list|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"unitime.email.replyto"
argument_list|)
argument_list|,
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"unitime.email.replyto.name"
argument_list|)
argument_list|)
expr_stmt|;
name|iMail
operator|.
name|setSentDate
argument_list|(
operator|new
name|Date
argument_list|()
argument_list|)
expr_stmt|;
name|iMail
operator|.
name|setContent
argument_list|(
name|iBody
argument_list|)
expr_stmt|;
name|iMail
operator|.
name|saveChanges
argument_list|()
expr_stmt|;
name|Transport
operator|.
name|send
argument_list|(
name|iMail
argument_list|)
expr_stmt|;
block|}
finally|finally
block|{
name|long
name|t
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
operator|-
name|t0
decl_stmt|;
if|if
condition|(
name|t
operator|>
literal|30000
condition|)
name|sLog
operator|.
name|warn
argument_list|(
literal|"It took "
operator|+
operator|new
name|DecimalFormat
argument_list|(
literal|"0.00"
argument_list|)
operator|.
name|format
argument_list|(
name|t
operator|/
literal|1000.0
argument_list|)
operator|+
literal|" seconds to send an email."
argument_list|)
expr_stmt|;
if|else if
condition|(
name|t
operator|>
literal|5000
condition|)
name|sLog
operator|.
name|info
argument_list|(
literal|"It took "
operator|+
operator|new
name|DecimalFormat
argument_list|(
literal|"0.00"
argument_list|)
operator|.
name|format
argument_list|(
name|t
operator|/
literal|1000.0
argument_list|)
operator|+
literal|" seconds to send an email."
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

