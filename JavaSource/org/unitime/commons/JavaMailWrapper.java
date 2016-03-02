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
name|commons
package|;
end_package

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
name|mail
operator|.
name|Address
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
name|defaults
operator|.
name|ApplicationProperty
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|JavaMailWrapper
extends|extends
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
name|JavaMailWrapper
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
name|ApplicationProperty
operator|.
name|EmailSmtpUser
operator|.
name|value
argument_list|()
decl_stmt|;
specifier|final
name|String
name|password
init|=
name|ApplicationProperty
operator|.
name|EmailSmtpPassword
operator|.
name|value
argument_list|()
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
annotation|@
name|Override
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
argument_list|,
literal|"UTF-8"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
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
argument_list|,
literal|"UTF-8"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
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
argument_list|,
literal|"UTF-8"
argument_list|)
block|}
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|addReplyTo
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
operator|==
literal|null
operator|||
name|email
operator|.
name|isEmpty
argument_list|()
condition|)
return|return;
name|Address
index|[]
name|replyTo
init|=
name|iMail
operator|.
name|getReplyTo
argument_list|()
decl_stmt|;
if|if
condition|(
name|replyTo
operator|==
literal|null
operator|||
name|replyTo
operator|.
name|length
operator|==
literal|0
condition|)
block|{
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
argument_list|,
literal|"UTF-8"
argument_list|)
block|}
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|Address
index|[]
name|newReplyTo
init|=
operator|new
name|Address
index|[
name|replyTo
operator|.
name|length
operator|+
literal|1
index|]
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|replyTo
operator|.
name|length
condition|;
name|i
operator|++
control|)
name|newReplyTo
index|[
name|i
index|]
operator|=
name|replyTo
index|[
name|i
index|]
expr_stmt|;
name|newReplyTo
index|[
name|replyTo
operator|.
name|length
index|]
operator|=
operator|new
name|InternetAddress
argument_list|(
name|email
argument_list|,
name|name
argument_list|,
literal|"UTF-8"
argument_list|)
expr_stmt|;
name|iMail
operator|.
name|setReplyTo
argument_list|(
name|newReplyTo
argument_list|)
expr_stmt|;
block|}
block|}
specifier|protected
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
argument_list|,
literal|"UTF-8"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
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
annotation|@
name|Override
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
annotation|@
name|Override
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
annotation|@
name|Override
specifier|public
name|void
name|setBody
parameter_list|(
name|String
name|message
parameter_list|,
name|String
name|type
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
name|type
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
annotation|@
name|Override
specifier|protected
name|void
name|addAttachment
parameter_list|(
name|String
name|name
parameter_list|,
name|DataHandler
name|data
parameter_list|)
throws|throws
name|MessagingException
block|{
name|BodyPart
name|attachment
init|=
operator|new
name|MimeBodyPart
argument_list|()
decl_stmt|;
name|attachment
operator|.
name|setDataHandler
argument_list|(
name|data
argument_list|)
expr_stmt|;
name|attachment
operator|.
name|setFileName
argument_list|(
name|name
argument_list|)
expr_stmt|;
name|attachment
operator|.
name|setHeader
argument_list|(
literal|"Content-ID"
argument_list|,
literal|"<"
operator|+
name|name
operator|+
literal|">"
argument_list|)
expr_stmt|;
name|iBody
operator|.
name|addBodyPart
argument_list|(
name|attachment
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
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
if|if
condition|(
name|iMail
operator|.
name|getFrom
argument_list|()
operator|==
literal|null
operator|||
name|iMail
operator|.
name|getFrom
argument_list|()
operator|.
name|length
operator|==
literal|0
condition|)
name|setFrom
argument_list|(
name|ApplicationProperty
operator|.
name|EmailSenderAddress
operator|.
name|value
argument_list|()
argument_list|,
name|ApplicationProperty
operator|.
name|EmailSenderName
operator|.
name|value
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|iMail
operator|.
name|getReplyTo
argument_list|()
operator|==
literal|null
operator|||
name|iMail
operator|.
name|getReplyTo
argument_list|()
operator|.
name|length
operator|==
literal|0
condition|)
name|setReplyTo
argument_list|(
name|ApplicationProperty
operator|.
name|EmailReplyToAddress
operator|.
name|value
argument_list|()
argument_list|,
name|ApplicationProperty
operator|.
name|EmailReplyToName
operator|.
name|value
argument_list|()
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
annotation|@
name|Override
specifier|public
name|void
name|setInReplyTo
parameter_list|(
name|String
name|messageId
parameter_list|)
throws|throws
name|MessagingException
block|{
if|if
condition|(
name|messageId
operator|!=
literal|null
condition|)
name|iMail
operator|.
name|setHeader
argument_list|(
literal|"In-Reply-To"
argument_list|,
name|messageId
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getMessageId
parameter_list|()
throws|throws
name|MessagingException
block|{
return|return
name|iMail
operator|.
name|getHeader
argument_list|(
literal|"Message-Id"
argument_list|,
literal|null
argument_list|)
return|;
block|}
block|}
end_class

end_unit

