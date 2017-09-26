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
name|events
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|BufferedReader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|ByteArrayInputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|ByteArrayOutputStream
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
name|StringReader
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
name|regex
operator|.
name|Matcher
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Pattern
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
name|Part
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
name|log4j
operator|.
name|Logger
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
name|timetable
operator|.
name|defaults
operator|.
name|ApplicationProperty
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
name|Event
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
name|EventNote
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
name|EventDAO
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
name|_RootDAO
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|Service
argument_list|(
literal|"eventInboundEmailService"
argument_list|)
specifier|public
class|class
name|EventInboundEmailService
block|{
specifier|private
name|Pattern
name|iSubjectPattern
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"(?i)\\[EVENT-([0-9a-f]+)\\]"
argument_list|)
decl_stmt|;
specifier|private
specifier|static
name|Logger
name|sLog
init|=
name|Logger
operator|.
name|getLogger
argument_list|(
name|EventInboundEmailService
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|public
name|void
name|process
parameter_list|(
name|MimeMessage
name|message
parameter_list|)
throws|throws
name|MessagingException
throws|,
name|IOException
block|{
name|sLog
operator|.
name|info
argument_list|(
literal|"Recieved message: "
operator|+
name|message
operator|.
name|getSubject
argument_list|()
argument_list|)
expr_stmt|;
name|Matcher
name|subjectMatcher
init|=
name|iSubjectPattern
operator|.
name|matcher
argument_list|(
name|message
operator|.
name|getSubject
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|subjectMatcher
operator|.
name|find
argument_list|()
condition|)
return|return;
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
operator|new
name|_RootDAO
argument_list|()
operator|.
name|createNewSession
argument_list|()
decl_stmt|;
try|try
block|{
name|Event
name|event
init|=
name|EventDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|Long
operator|.
name|parseLong
argument_list|(
name|subjectMatcher
operator|.
name|group
argument_list|(
literal|1
argument_list|)
argument_list|,
literal|16
argument_list|)
argument_list|,
name|hibSession
argument_list|)
decl_stmt|;
if|if
condition|(
name|event
operator|==
literal|null
condition|)
return|return;
name|String
name|body
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|message
operator|.
name|getContent
argument_list|()
operator|instanceof
name|String
condition|)
block|{
name|body
operator|=
operator|(
name|String
operator|)
name|message
operator|.
name|getContent
argument_list|()
expr_stmt|;
block|}
if|else if
condition|(
name|message
operator|.
name|getContent
argument_list|()
operator|instanceof
name|MimeMultipart
condition|)
block|{
name|MimeMultipart
name|multi
init|=
operator|(
name|MimeMultipart
operator|)
name|message
operator|.
name|getContent
argument_list|()
decl_stmt|;
if|if
condition|(
name|multi
operator|.
name|getBodyPart
argument_list|(
literal|0
argument_list|)
operator|.
name|getContent
argument_list|()
operator|instanceof
name|MimeMultipart
operator|&&
operator|(
operator|(
name|MimeMultipart
operator|)
name|multi
operator|.
name|getBodyPart
argument_list|(
literal|0
argument_list|)
operator|.
name|getContent
argument_list|()
operator|)
operator|.
name|getBodyPart
argument_list|(
literal|0
argument_list|)
operator|.
name|getContent
argument_list|()
operator|instanceof
name|String
condition|)
block|{
name|body
operator|=
operator|(
name|String
operator|)
operator|(
operator|(
name|MimeMultipart
operator|)
name|multi
operator|.
name|getBodyPart
argument_list|(
literal|0
argument_list|)
operator|.
name|getContent
argument_list|()
operator|)
operator|.
name|getBodyPart
argument_list|(
literal|0
argument_list|)
operator|.
name|getContent
argument_list|()
expr_stmt|;
block|}
if|else if
condition|(
name|multi
operator|.
name|getBodyPart
argument_list|(
literal|0
argument_list|)
operator|.
name|getContent
argument_list|()
operator|instanceof
name|String
condition|)
block|{
name|body
operator|=
operator|(
name|String
operator|)
name|multi
operator|.
name|getBodyPart
argument_list|(
literal|0
argument_list|)
operator|.
name|getContent
argument_list|()
expr_stmt|;
block|}
block|}
name|BodyPart
name|attachment
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|message
operator|.
name|getContent
argument_list|()
operator|instanceof
name|MimeMultipart
condition|)
block|{
name|MimeMultipart
name|multi
init|=
operator|(
name|MimeMultipart
operator|)
name|message
operator|.
name|getContent
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|1
init|;
name|i
operator|<
name|multi
operator|.
name|getCount
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|Part
operator|.
name|ATTACHMENT
operator|.
name|equalsIgnoreCase
argument_list|(
name|multi
operator|.
name|getBodyPart
argument_list|(
name|i
argument_list|)
operator|.
name|getDisposition
argument_list|()
argument_list|)
operator|&&
name|multi
operator|.
name|getBodyPart
argument_list|(
name|i
argument_list|)
operator|.
name|getFileName
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|attachment
operator|=
name|multi
operator|.
name|getBodyPart
argument_list|(
name|i
argument_list|)
expr_stmt|;
break|break;
block|}
block|}
block|}
if|if
condition|(
name|body
operator|==
literal|null
condition|)
return|return;
name|BufferedReader
name|reader
init|=
operator|new
name|BufferedReader
argument_list|(
operator|new
name|StringReader
argument_list|(
name|body
argument_list|)
argument_list|)
decl_stmt|;
name|String
name|text
init|=
literal|null
decl_stmt|,
name|line
init|=
literal|null
decl_stmt|,
name|skip
init|=
literal|null
decl_stmt|;
while|while
condition|(
operator|(
name|line
operator|=
name|reader
operator|.
name|readLine
argument_list|()
operator|)
operator|!=
literal|null
condition|)
block|{
name|line
operator|=
name|line
operator|.
name|trim
argument_list|()
expr_stmt|;
if|if
condition|(
name|line
operator|.
name|isEmpty
argument_list|()
operator|&&
name|text
operator|==
literal|null
operator|&&
name|skip
operator|==
literal|null
condition|)
continue|continue;
if|if
condition|(
name|line
operator|.
name|matches
argument_list|(
literal|"(?i)-+ ?Original message ?-+"
argument_list|)
operator|||
name|line
operator|.
name|matches
argument_list|(
literal|"(?i)-+ ?Forwarded message ?-+"
argument_list|)
operator|||
name|line
operator|.
name|matches
argument_list|(
literal|"On .* wrote:"
argument_list|)
condition|)
break|break;
if|if
condition|(
name|line
operator|.
name|matches
argument_list|(
literal|"(?i)[0-9]+/[0-9]+/[0-9]+ "
operator|+
name|ApplicationProperty
operator|.
name|EmailSenderName
operator|.
name|value
argument_list|()
operator|+
literal|"<"
operator|+
name|ApplicationProperty
operator|.
name|EmailSenderAddress
operator|.
name|value
argument_list|()
operator|+
literal|">"
argument_list|)
condition|)
break|break;
if|if
condition|(
name|line
operator|.
name|startsWith
argument_list|(
literal|">"
argument_list|)
operator|||
name|line
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|skip
operator|=
operator|(
name|skip
operator|==
literal|null
condition|?
literal|""
else|:
name|skip
operator|+
literal|"\n"
operator|)
operator|+
name|line
expr_stmt|;
block|}
else|else
block|{
name|text
operator|=
operator|(
name|text
operator|==
literal|null
condition|?
literal|""
else|:
name|text
operator|+
literal|"\n"
operator|)
operator|+
operator|(
name|skip
operator|==
literal|null
condition|?
literal|""
else|:
name|skip
operator|+
literal|"\n"
operator|)
operator|+
name|line
expr_stmt|;
name|skip
operator|=
literal|null
expr_stmt|;
block|}
block|}
name|reader
operator|.
name|close
argument_list|()
expr_stmt|;
if|if
condition|(
name|text
operator|==
literal|null
operator|&&
name|attachment
operator|==
literal|null
condition|)
return|return;
specifier|final
name|EventNote
name|note
init|=
operator|new
name|EventNote
argument_list|()
decl_stmt|;
name|note
operator|.
name|setEvent
argument_list|(
name|event
argument_list|)
expr_stmt|;
name|note
operator|.
name|setNoteType
argument_list|(
name|EventNote
operator|.
name|sEventNoteTypeEmail
argument_list|)
expr_stmt|;
name|Date
name|ts
init|=
operator|new
name|Date
argument_list|()
decl_stmt|;
if|if
condition|(
name|message
operator|.
name|getReceivedDate
argument_list|()
operator|!=
literal|null
condition|)
name|ts
operator|=
name|message
operator|.
name|getReceivedDate
argument_list|()
expr_stmt|;
if|else if
condition|(
name|message
operator|.
name|getSentDate
argument_list|()
operator|!=
literal|null
condition|)
name|ts
operator|=
name|message
operator|.
name|getSentDate
argument_list|()
expr_stmt|;
name|note
operator|.
name|setTimeStamp
argument_list|(
name|ts
argument_list|)
expr_stmt|;
name|String
name|user
init|=
literal|"EMAIL"
decl_stmt|,
name|userId
init|=
literal|null
decl_stmt|;
name|InternetAddress
name|from
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|message
operator|.
name|getFrom
argument_list|()
operator|!=
literal|null
operator|&&
name|message
operator|.
name|getFrom
argument_list|()
operator|.
name|length
operator|>
literal|0
operator|&&
name|message
operator|.
name|getFrom
argument_list|()
index|[
literal|0
index|]
operator|instanceof
name|InternetAddress
condition|)
block|{
name|from
operator|=
operator|(
name|InternetAddress
operator|)
name|message
operator|.
name|getFrom
argument_list|()
index|[
literal|0
index|]
expr_stmt|;
if|if
condition|(
name|from
operator|.
name|getPersonal
argument_list|()
operator|!=
literal|null
condition|)
name|user
operator|=
name|from
operator|.
name|getPersonal
argument_list|()
expr_stmt|;
else|else
name|user
operator|=
name|from
operator|.
name|getAddress
argument_list|()
expr_stmt|;
block|}
name|note
operator|.
name|setUser
argument_list|(
name|user
argument_list|)
expr_stmt|;
name|note
operator|.
name|setUserId
argument_list|(
name|userId
argument_list|)
expr_stmt|;
name|note
operator|.
name|setTextNote
argument_list|(
name|text
argument_list|)
expr_stmt|;
if|if
condition|(
name|note
operator|.
name|getTextNote
argument_list|()
operator|!=
literal|null
operator|&&
name|note
operator|.
name|getTextNote
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|2000
condition|)
name|note
operator|.
name|setTextNote
argument_list|(
name|note
operator|.
name|getTextNote
argument_list|()
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|2000
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|attachment
operator|!=
literal|null
condition|)
block|{
name|note
operator|.
name|setAttachedName
argument_list|(
name|attachment
operator|.
name|getFileName
argument_list|()
argument_list|)
expr_stmt|;
name|note
operator|.
name|setAttachedContentType
argument_list|(
name|attachment
operator|.
name|getContentType
argument_list|()
argument_list|)
expr_stmt|;
name|InputStream
name|input
init|=
name|attachment
operator|.
name|getInputStream
argument_list|()
decl_stmt|;
name|ByteArrayOutputStream
name|bytes
init|=
operator|new
name|ByteArrayOutputStream
argument_list|()
decl_stmt|;
name|byte
index|[]
name|buffer
init|=
operator|new
name|byte
index|[
literal|1024
index|]
decl_stmt|;
name|int
name|len
decl_stmt|;
while|while
condition|(
operator|(
name|len
operator|=
name|input
operator|.
name|read
argument_list|(
name|buffer
argument_list|)
operator|)
operator|!=
operator|-
literal|1
condition|)
name|bytes
operator|.
name|write
argument_list|(
name|buffer
argument_list|,
literal|0
argument_list|,
name|len
argument_list|)
expr_stmt|;
name|input
operator|.
name|close
argument_list|()
expr_stmt|;
name|bytes
operator|.
name|flush
argument_list|()
expr_stmt|;
name|bytes
operator|.
name|close
argument_list|()
expr_stmt|;
name|note
operator|.
name|setAttachedFile
argument_list|(
name|bytes
operator|.
name|toByteArray
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|event
operator|.
name|getNotes
argument_list|()
operator|.
name|add
argument_list|(
name|note
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|note
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|update
argument_list|(
name|event
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|flush
argument_list|()
expr_stmt|;
name|DataSource
name|data
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|note
operator|.
name|getAttachedFile
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|data
operator|=
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
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|note
operator|.
name|getAttachedName
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
operator|new
name|ByteArrayInputStream
argument_list|(
name|note
operator|.
name|getAttachedFile
argument_list|()
argument_list|)
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
name|note
operator|.
name|getAttachedContentType
argument_list|()
return|;
block|}
block|}
expr_stmt|;
block|}
try|try
block|{
name|EventEmail
operator|.
name|eventUpdated
argument_list|(
name|event
argument_list|,
name|text
argument_list|,
name|from
argument_list|,
name|data
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|sLog
operator|.
name|error
argument_list|(
literal|"Failed to sent confirmation email: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
finally|finally
block|{
name|hibSession
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

