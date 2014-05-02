begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.4 - 3.5 (University Timetabling Application)  * Copyright (C) 2012 - 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|UnsupportedEncodingException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|math
operator|.
name|BigInteger
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URLDecoder
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URLEncoder
import|;
end_import

begin_import
import|import
name|java
operator|.
name|security
operator|.
name|NoSuchAlgorithmException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|security
operator|.
name|spec
operator|.
name|InvalidKeySpecException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|security
operator|.
name|spec
operator|.
name|KeySpec
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|crypto
operator|.
name|Cipher
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|crypto
operator|.
name|SecretKey
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|crypto
operator|.
name|SecretKeyFactory
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|crypto
operator|.
name|spec
operator|.
name|PBEKeySpec
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|crypto
operator|.
name|spec
operator|.
name|SecretKeySpec
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
name|gwt
operator|.
name|command
operator|.
name|server
operator|.
name|GwtRpcImplementation
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
name|gwt
operator|.
name|command
operator|.
name|server
operator|.
name|GwtRpcImplements
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
name|gwt
operator|.
name|shared
operator|.
name|EventInterface
operator|.
name|EncodeQueryRpcRequest
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
name|gwt
operator|.
name|shared
operator|.
name|EventInterface
operator|.
name|EncodeQueryRpcResponse
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

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|GwtRpcImplements
argument_list|(
name|EncodeQueryRpcRequest
operator|.
name|class
argument_list|)
specifier|public
class|class
name|QueryEncoderBackend
implements|implements
name|GwtRpcImplementation
argument_list|<
name|EncodeQueryRpcRequest
argument_list|,
name|EncodeQueryRpcResponse
argument_list|>
block|{
specifier|private
specifier|static
name|Logger
name|sLog
init|=
name|Logger
operator|.
name|getLogger
argument_list|(
name|QueryEncoderBackend
operator|.
name|class
argument_list|)
decl_stmt|;
annotation|@
name|Override
specifier|public
name|EncodeQueryRpcResponse
name|execute
parameter_list|(
name|EncodeQueryRpcRequest
name|request
parameter_list|,
name|SessionContext
name|context
parameter_list|)
block|{
return|return
operator|new
name|EncodeQueryRpcResponse
argument_list|(
name|encode
argument_list|(
name|request
operator|.
name|getQuery
argument_list|()
operator|+
operator|(
name|context
operator|.
name|getUser
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
literal|"&user="
operator|+
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getExternalUserId
argument_list|()
operator|+
operator|(
name|context
operator|.
name|getUser
argument_list|()
operator|==
literal|null
operator|||
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAuthority
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
literal|"&role="
operator|+
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|getRole
argument_list|()
operator|)
operator|)
argument_list|)
argument_list|)
return|;
block|}
specifier|private
specifier|static
name|SecretKey
name|secret
parameter_list|()
throws|throws
name|NoSuchAlgorithmException
throws|,
name|InvalidKeySpecException
block|{
name|byte
name|salt
index|[]
init|=
operator|new
name|byte
index|[]
block|{
operator|(
name|byte
operator|)
literal|0x33
block|,
operator|(
name|byte
operator|)
literal|0x7b
block|,
operator|(
name|byte
operator|)
literal|0x09
block|,
operator|(
name|byte
operator|)
literal|0x0e
block|,
operator|(
name|byte
operator|)
literal|0xcf
block|,
operator|(
name|byte
operator|)
literal|0x5a
block|,
operator|(
name|byte
operator|)
literal|0x58
block|,
operator|(
name|byte
operator|)
literal|0xd9
block|}
decl_stmt|;
name|SecretKeyFactory
name|factory
init|=
name|SecretKeyFactory
operator|.
name|getInstance
argument_list|(
literal|"PBKDF2WithHmacSHA1"
argument_list|)
decl_stmt|;
name|KeySpec
name|spec
init|=
operator|new
name|PBEKeySpec
argument_list|(
name|ApplicationProperty
operator|.
name|UrlEncoderSecret
operator|.
name|value
argument_list|()
operator|.
name|toCharArray
argument_list|()
argument_list|,
name|salt
argument_list|,
literal|1024
argument_list|,
literal|128
argument_list|)
decl_stmt|;
name|SecretKey
name|key
init|=
name|factory
operator|.
name|generateSecret
argument_list|(
name|spec
argument_list|)
decl_stmt|;
return|return
operator|new
name|SecretKeySpec
argument_list|(
name|key
operator|.
name|getEncoded
argument_list|()
argument_list|,
literal|"AES"
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|String
name|encode
parameter_list|(
name|String
name|text
parameter_list|)
block|{
try|try
block|{
name|Cipher
name|cipher
init|=
name|Cipher
operator|.
name|getInstance
argument_list|(
literal|"AES/ECB/PKCS5Padding"
argument_list|)
decl_stmt|;
name|cipher
operator|.
name|init
argument_list|(
name|Cipher
operator|.
name|ENCRYPT_MODE
argument_list|,
name|secret
argument_list|()
argument_list|)
expr_stmt|;
return|return
operator|new
name|BigInteger
argument_list|(
name|cipher
operator|.
name|doFinal
argument_list|(
name|text
operator|.
name|getBytes
argument_list|()
argument_list|)
argument_list|)
operator|.
name|toString
argument_list|(
literal|36
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|sLog
operator|.
name|warn
argument_list|(
literal|"Encoding failed: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
try|try
block|{
return|return
name|URLEncoder
operator|.
name|encode
argument_list|(
name|text
argument_list|,
literal|"ISO-8859-1"
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|UnsupportedEncodingException
name|x
parameter_list|)
block|{
return|return
literal|null
return|;
block|}
block|}
block|}
specifier|public
specifier|static
name|String
name|decode
parameter_list|(
name|String
name|text
parameter_list|)
block|{
try|try
block|{
if|if
condition|(
name|text
operator|==
literal|null
operator|||
name|text
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
literal|null
return|;
name|Cipher
name|cipher
init|=
name|Cipher
operator|.
name|getInstance
argument_list|(
literal|"AES/ECB/PKCS5Padding"
argument_list|)
decl_stmt|;
name|cipher
operator|.
name|init
argument_list|(
name|Cipher
operator|.
name|DECRYPT_MODE
argument_list|,
name|secret
argument_list|()
argument_list|)
expr_stmt|;
return|return
operator|new
name|String
argument_list|(
name|cipher
operator|.
name|doFinal
argument_list|(
operator|new
name|BigInteger
argument_list|(
name|text
argument_list|,
literal|36
argument_list|)
operator|.
name|toByteArray
argument_list|()
argument_list|)
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|sLog
operator|.
name|warn
argument_list|(
literal|"Decoding failed: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
try|try
block|{
return|return
name|URLDecoder
operator|.
name|decode
argument_list|(
name|text
argument_list|,
literal|"ISO-8859-1"
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|UnsupportedEncodingException
name|x
parameter_list|)
block|{
return|return
literal|null
return|;
block|}
block|}
block|}
block|}
end_class

end_unit

