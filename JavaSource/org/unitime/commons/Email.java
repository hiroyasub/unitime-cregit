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
specifier|abstract
class|class
name|Email
block|{
specifier|public
specifier|static
name|Email
name|createEmail
parameter_list|()
throws|throws
name|Exception
block|{
return|return
operator|(
name|Email
operator|)
name|Class
operator|.
name|forName
argument_list|(
name|ApplicationProperty
operator|.
name|EmailProvider
operator|.
name|value
argument_list|()
argument_list|)
operator|.
name|newInstance
argument_list|()
return|;
block|}
specifier|public
specifier|abstract
name|void
name|setSubject
parameter_list|(
name|String
name|subject
parameter_list|)
throws|throws
name|Exception
function_decl|;
specifier|public
specifier|abstract
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
name|Exception
function_decl|;
specifier|public
specifier|abstract
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
name|Exception
function_decl|;
specifier|public
specifier|abstract
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
name|Exception
function_decl|;
specifier|public
specifier|abstract
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
name|Exception
function_decl|;
specifier|public
specifier|abstract
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
name|Exception
function_decl|;
specifier|public
specifier|abstract
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
name|Exception
function_decl|;
specifier|protected
specifier|abstract
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
name|Exception
function_decl|;
specifier|public
name|void
name|setText
parameter_list|(
name|String
name|message
parameter_list|)
throws|throws
name|Exception
block|{
name|setBody
argument_list|(
name|message
argument_list|,
literal|"text/plain; charset=UTF-8"
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
name|Exception
block|{
name|setBody
argument_list|(
name|message
argument_list|,
literal|"text/html; charset=UTF-8"
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|addNotify
parameter_list|()
throws|throws
name|Exception
block|{
name|addRecipient
argument_list|(
name|ApplicationProperty
operator|.
name|EmailNotificationAddress
operator|.
name|value
argument_list|()
argument_list|,
name|ApplicationProperty
operator|.
name|EmailNotificationAddressName
operator|.
name|value
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|addNotifyCC
parameter_list|()
throws|throws
name|Exception
block|{
name|addRecipientCC
argument_list|(
name|ApplicationProperty
operator|.
name|EmailNotificationAddress
operator|.
name|value
argument_list|()
argument_list|,
name|ApplicationProperty
operator|.
name|EmailNotificationAddressName
operator|.
name|value
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|protected
specifier|abstract
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
name|Exception
function_decl|;
specifier|public
name|void
name|addAttachment
parameter_list|(
name|File
name|file
parameter_list|,
name|String
name|name
parameter_list|)
throws|throws
name|Exception
block|{
name|addAttachment
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
argument_list|,
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
block|}
specifier|public
name|void
name|addAttachment
parameter_list|(
specifier|final
name|FormFile
name|file
parameter_list|)
throws|throws
name|Exception
block|{
name|addAttachment
argument_list|(
name|file
operator|.
name|getFileName
argument_list|()
argument_list|,
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
block|}
specifier|public
name|void
name|addAttachment
parameter_list|(
name|DataSource
name|source
parameter_list|)
throws|throws
name|Exception
block|{
name|addAttachment
argument_list|(
name|source
operator|.
name|getName
argument_list|()
argument_list|,
operator|new
name|DataHandler
argument_list|(
name|source
argument_list|)
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|abstract
name|void
name|send
parameter_list|()
throws|throws
name|Exception
function_decl|;
specifier|public
specifier|abstract
name|void
name|setInReplyTo
parameter_list|(
name|String
name|messageId
parameter_list|)
throws|throws
name|Exception
function_decl|;
specifier|public
specifier|abstract
name|String
name|getMessageId
parameter_list|()
throws|throws
name|Exception
function_decl|;
block|}
end_class

end_unit

