begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 - 3.5 (University Timetabling Application)  * Copyright (C) 2008 - 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|IOException
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
name|javax
operator|.
name|servlet
operator|.
name|http
operator|.
name|HttpSession
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
name|Globals
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
specifier|public
class|class
name|BackTracker
block|{
specifier|public
specifier|static
name|int
name|MAX_BACK_STEPS
init|=
literal|10
decl_stmt|;
specifier|public
specifier|static
name|String
name|BACK_LIST
init|=
literal|"BackTracker.back"
decl_stmt|;
specifier|protected
specifier|static
name|MessageResources
name|getResources
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|)
block|{
return|return
operator|(
operator|(
name|MessageResources
operator|)
name|request
operator|.
name|getAttribute
argument_list|(
name|Globals
operator|.
name|MESSAGES_KEY
argument_list|)
operator|)
return|;
block|}
specifier|public
specifier|static
name|Vector
name|getBackList
parameter_list|(
name|HttpSession
name|session
parameter_list|)
block|{
synchronized|synchronized
init|(
name|session
init|)
block|{
name|Vector
name|back
init|=
operator|(
name|Vector
operator|)
name|session
operator|.
name|getAttribute
argument_list|(
name|BACK_LIST
argument_list|)
decl_stmt|;
if|if
condition|(
name|back
operator|==
literal|null
condition|)
block|{
name|back
operator|=
operator|new
name|Vector
argument_list|()
expr_stmt|;
name|session
operator|.
name|setAttribute
argument_list|(
literal|"BackTracker.back"
argument_list|,
name|back
argument_list|)
expr_stmt|;
block|}
return|return
name|back
return|;
block|}
block|}
specifier|public
specifier|static
name|void
name|markForBack
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|String
name|uri
parameter_list|,
name|String
name|title
parameter_list|,
name|boolean
name|back
parameter_list|,
name|boolean
name|clear
parameter_list|)
block|{
synchronized|synchronized
init|(
name|request
operator|.
name|getSession
argument_list|()
init|)
block|{
name|Vector
name|backList
init|=
name|getBackList
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|clear
condition|)
name|backList
operator|.
name|clear
argument_list|()
expr_stmt|;
if|if
condition|(
name|back
condition|)
block|{
if|if
condition|(
name|uri
operator|==
literal|null
operator|&&
name|request
operator|.
name|getAttribute
argument_list|(
literal|"javax.servlet.forward.request_uri"
argument_list|)
operator|==
literal|null
condition|)
return|return;
name|Object
name|titleObj
init|=
operator|(
name|title
operator|==
literal|null
condition|?
name|request
operator|.
name|getAttribute
argument_list|(
literal|"title"
argument_list|)
else|:
name|title
operator|)
decl_stmt|;
name|String
name|requestURI
init|=
operator|(
name|String
operator|)
name|request
operator|.
name|getAttribute
argument_list|(
literal|"javax.servlet.forward.request_uri"
argument_list|)
decl_stmt|;
name|String
name|queryString
init|=
operator|(
name|String
operator|)
name|request
operator|.
name|getAttribute
argument_list|(
literal|"javax.servlet.forward.query_string"
argument_list|)
decl_stmt|;
if|if
condition|(
name|queryString
operator|!=
literal|null
operator|&&
name|queryString
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|requestURI
operator|+=
literal|"?"
operator|+
name|queryString
expr_stmt|;
if|if
condition|(
name|uri
operator|!=
literal|null
condition|)
name|requestURI
operator|=
name|uri
expr_stmt|;
if|if
condition|(
operator|!
name|backList
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|int
name|found
init|=
operator|-
literal|1
decl_stmt|;
for|for
control|(
name|int
name|idx
init|=
literal|0
init|;
name|idx
operator|<
name|backList
operator|.
name|size
argument_list|()
condition|;
name|idx
operator|++
control|)
block|{
name|String
index|[]
name|lastBack
init|=
operator|(
name|String
index|[]
operator|)
name|backList
operator|.
name|elementAt
argument_list|(
name|idx
argument_list|)
decl_stmt|;
if|if
condition|(
name|lastBack
index|[
literal|0
index|]
operator|.
name|equals
argument_list|(
name|requestURI
argument_list|)
condition|)
block|{
name|found
operator|=
name|idx
expr_stmt|;
break|break;
block|}
block|}
while|while
condition|(
name|found
operator|>=
literal|0
operator|&&
name|backList
operator|.
name|size
argument_list|()
operator|>
name|found
condition|)
name|backList
operator|.
name|removeElementAt
argument_list|(
name|backList
operator|.
name|size
argument_list|()
operator|-
literal|1
argument_list|)
expr_stmt|;
block|}
name|backList
operator|.
name|addElement
argument_list|(
operator|new
name|String
index|[]
block|{
name|requestURI
block|,
operator|(
name|titleObj
operator|==
literal|null
condition|?
literal|null
else|:
name|titleObj
operator|.
name|toString
argument_list|()
operator|)
block|}
argument_list|)
expr_stmt|;
comment|//System.out.println("ADD BACK:"+requestURI+" ("+titleObj+")");
block|}
block|}
block|}
specifier|public
specifier|static
name|void
name|markForBack
parameter_list|(
name|SessionContext
name|context
parameter_list|,
name|String
name|uri
parameter_list|,
name|String
name|title
parameter_list|,
name|boolean
name|back
parameter_list|,
name|boolean
name|clear
parameter_list|)
block|{
name|Vector
name|backList
init|=
operator|(
name|Vector
operator|)
name|context
operator|.
name|getAttribute
argument_list|(
name|BACK_LIST
argument_list|)
decl_stmt|;
if|if
condition|(
name|backList
operator|==
literal|null
condition|)
block|{
name|backList
operator|=
operator|new
name|Vector
argument_list|()
expr_stmt|;
name|context
operator|.
name|setAttribute
argument_list|(
literal|"BackTracker.back"
argument_list|,
name|backList
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|clear
condition|)
name|backList
operator|.
name|clear
argument_list|()
expr_stmt|;
if|if
condition|(
name|back
condition|)
block|{
if|if
condition|(
operator|!
name|backList
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|int
name|found
init|=
operator|-
literal|1
decl_stmt|;
for|for
control|(
name|int
name|idx
init|=
literal|0
init|;
name|idx
operator|<
name|backList
operator|.
name|size
argument_list|()
condition|;
name|idx
operator|++
control|)
block|{
name|String
index|[]
name|lastBack
init|=
operator|(
name|String
index|[]
operator|)
name|backList
operator|.
name|elementAt
argument_list|(
name|idx
argument_list|)
decl_stmt|;
if|if
condition|(
name|lastBack
index|[
literal|0
index|]
operator|.
name|equals
argument_list|(
name|uri
argument_list|)
condition|)
block|{
name|found
operator|=
name|idx
expr_stmt|;
break|break;
block|}
block|}
while|while
condition|(
name|found
operator|>=
literal|0
operator|&&
name|backList
operator|.
name|size
argument_list|()
operator|>
name|found
condition|)
name|backList
operator|.
name|removeElementAt
argument_list|(
name|backList
operator|.
name|size
argument_list|()
operator|-
literal|1
argument_list|)
expr_stmt|;
block|}
name|backList
operator|.
name|addElement
argument_list|(
operator|new
name|String
index|[]
block|{
name|uri
block|,
name|title
block|}
argument_list|)
expr_stmt|;
block|}
block|}
specifier|private
specifier|static
name|String
name|mark
init|=
literal|"-_.!~*'()\""
decl_stmt|;
specifier|public
specifier|static
name|String
name|encodeURL
parameter_list|(
name|String
name|url
parameter_list|)
block|{
name|StringBuffer
name|encodedUrl
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
comment|// Encoded URL
name|int
name|len
init|=
name|url
operator|.
name|length
argument_list|()
decl_stmt|;
comment|// Encode each URL character
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|len
condition|;
name|i
operator|++
control|)
block|{
name|char
name|c
init|=
name|url
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
decl_stmt|;
comment|// Get next character
if|if
condition|(
operator|(
name|c
operator|>=
literal|'0'
operator|&&
name|c
operator|<=
literal|'9'
operator|)
operator|||
operator|(
name|c
operator|>=
literal|'a'
operator|&&
name|c
operator|<=
literal|'z'
operator|)
operator|||
operator|(
name|c
operator|>=
literal|'A'
operator|&&
name|c
operator|<=
literal|'Z'
operator|)
condition|)
comment|// Alphanumeric characters require no encoding, append as is
name|encodedUrl
operator|.
name|append
argument_list|(
name|c
argument_list|)
expr_stmt|;
else|else
block|{
name|int
name|imark
init|=
name|mark
operator|.
name|indexOf
argument_list|(
name|c
argument_list|)
decl_stmt|;
if|if
condition|(
name|imark
operator|>=
literal|0
condition|)
block|{
comment|// Unreserved punctuation marks and symbols require
comment|//  no encoding, append as is
name|encodedUrl
operator|.
name|append
argument_list|(
name|c
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// Encode all other characters to Hex, using the format "%XX",
comment|//  where XX are the hex digits
name|encodedUrl
operator|.
name|append
argument_list|(
literal|'%'
argument_list|)
expr_stmt|;
comment|// Add % character
comment|// Encode the character's high-order nibble to Hex
name|encodedUrl
operator|.
name|append
argument_list|(
name|Integer
operator|.
name|toHexString
argument_list|(
name|c
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
return|return
name|encodedUrl
operator|.
name|toString
argument_list|()
return|;
comment|// Return encoded URL
block|}
specifier|public
specifier|static
name|String
name|getBackButton
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|int
name|nrBackSteps
parameter_list|,
name|String
name|name
parameter_list|,
name|String
name|title
parameter_list|,
name|String
name|accessKey
parameter_list|,
name|String
name|style
parameter_list|,
name|String
name|clazz
parameter_list|,
name|String
name|backType
parameter_list|,
name|String
name|backId
parameter_list|)
block|{
name|MessageResources
name|rsc
init|=
name|getResources
argument_list|(
name|request
argument_list|)
decl_stmt|;
if|if
condition|(
name|rsc
operator|!=
literal|null
operator|&&
name|rsc
operator|.
name|getMessage
argument_list|(
name|name
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|name
operator|=
name|rsc
operator|.
name|getMessage
argument_list|(
name|name
argument_list|)
expr_stmt|;
block|}
synchronized|synchronized
init|(
name|request
operator|.
name|getSession
argument_list|()
init|)
block|{
name|Vector
name|backList
init|=
name|getBackList
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|backList
operator|.
name|size
argument_list|()
operator|<
name|nrBackSteps
condition|)
return|return
literal|""
return|;
name|String
index|[]
name|backItem
init|=
operator|(
name|String
index|[]
operator|)
name|backList
operator|.
name|elementAt
argument_list|(
name|backList
operator|.
name|size
argument_list|()
operator|-
name|nrBackSteps
argument_list|)
decl_stmt|;
if|if
condition|(
name|backItem
index|[
literal|1
index|]
operator|!=
literal|null
condition|)
name|title
operator|=
name|title
operator|.
name|replaceAll
argument_list|(
literal|"%%"
argument_list|,
name|backItem
index|[
literal|1
index|]
argument_list|)
expr_stmt|;
name|String
name|backUrl
init|=
name|backItem
index|[
literal|0
index|]
decl_stmt|;
if|if
condition|(
name|backId
operator|!=
literal|null
operator|&&
name|backType
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|backUrl
operator|.
name|indexOf
argument_list|(
literal|'?'
argument_list|)
operator|>
literal|0
condition|)
name|backUrl
operator|+=
literal|"&backType="
operator|+
name|backType
operator|+
literal|"&backId="
operator|+
name|backId
operator|+
literal|"#back"
expr_stmt|;
else|else
name|backUrl
operator|+=
literal|"?backType="
operator|+
name|backType
operator|+
literal|"&backId="
operator|+
name|backId
operator|+
literal|"#back"
expr_stmt|;
block|}
return|return
literal|"<input type='button'"
operator|+
literal|" value='"
operator|+
name|name
operator|+
literal|"' "
operator|+
operator|(
name|accessKey
operator|==
literal|null
condition|?
literal|""
else|:
literal|" accesskey=\""
operator|+
name|accessKey
operator|+
literal|"\""
operator|)
operator|+
operator|(
name|style
operator|==
literal|null
condition|?
literal|""
else|:
literal|" style=\""
operator|+
name|style
operator|+
literal|"\""
operator|)
operator|+
operator|(
name|clazz
operator|==
literal|null
condition|?
literal|""
else|:
literal|" class=\""
operator|+
name|clazz
operator|+
literal|"\""
operator|)
operator|+
literal|" title=\""
operator|+
name|title
operator|+
literal|"\""
operator|+
literal|" onClick=\"document.location='back.do?uri="
operator|+
name|encodeURL
argument_list|(
name|backUrl
argument_list|)
operator|+
literal|"'"
operator|+
literal|";\""
operator|+
literal|"/>"
return|;
block|}
block|}
specifier|public
specifier|static
name|String
name|getGwtBack
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|int
name|nrBackSteps
parameter_list|)
block|{
synchronized|synchronized
init|(
name|request
operator|.
name|getSession
argument_list|()
init|)
block|{
name|Vector
name|back
init|=
name|getBackList
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|back
operator|.
name|size
argument_list|()
operator|<=
literal|1
condition|)
return|return
literal|""
return|;
name|StringBuffer
name|ret
init|=
operator|new
name|StringBuffer
argument_list|(
literal|""
argument_list|)
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
name|Math
operator|.
name|max
argument_list|(
literal|0
argument_list|,
name|back
operator|.
name|size
argument_list|()
operator|-
name|MAX_BACK_STEPS
argument_list|)
init|;
name|i
operator|<
name|back
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|String
index|[]
name|backItem
init|=
operator|(
name|String
index|[]
operator|)
name|back
operator|.
name|elementAt
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
name|ret
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|ret
operator|.
name|append
argument_list|(
literal|"&"
argument_list|)
expr_stmt|;
name|ret
operator|.
name|append
argument_list|(
name|encodeURL
argument_list|(
name|backItem
index|[
literal|0
index|]
argument_list|)
operator|+
literal|"|"
operator|+
name|backItem
index|[
literal|1
index|]
argument_list|)
expr_stmt|;
block|}
return|return
literal|"<span id='UniTimeGWT:Back' style='display:none;'>"
operator|+
name|ret
operator|.
name|toString
argument_list|()
operator|+
literal|"</span>"
return|;
block|}
block|}
specifier|public
specifier|static
name|boolean
name|hasBack
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|int
name|nrBackSteps
parameter_list|)
block|{
synchronized|synchronized
init|(
name|request
operator|.
name|getSession
argument_list|()
init|)
block|{
name|Vector
name|backList
init|=
name|getBackList
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|backList
operator|.
name|size
argument_list|()
operator|<
name|nrBackSteps
condition|)
return|return
literal|false
return|;
return|return
literal|true
return|;
block|}
block|}
specifier|public
specifier|static
name|boolean
name|doBack
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|HttpServletResponse
name|response
parameter_list|)
throws|throws
name|IOException
block|{
synchronized|synchronized
init|(
name|request
operator|.
name|getSession
argument_list|()
init|)
block|{
name|String
name|uri
init|=
name|request
operator|.
name|getParameter
argument_list|(
literal|"uri"
argument_list|)
decl_stmt|;
name|Vector
name|back
init|=
name|getBackList
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|back
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
literal|false
return|;
if|if
condition|(
name|uri
operator|==
literal|null
condition|)
block|{
name|uri
operator|=
operator|(
operator|(
name|String
index|[]
operator|)
name|back
operator|.
name|lastElement
argument_list|()
operator|)
index|[
literal|0
index|]
expr_stmt|;
name|back
operator|.
name|remove
argument_list|(
name|back
operator|.
name|size
argument_list|()
operator|-
literal|1
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|String
name|uriNoBack
init|=
name|uri
decl_stmt|;
if|if
condition|(
name|uriNoBack
operator|.
name|indexOf
argument_list|(
literal|"backType="
argument_list|)
operator|>=
literal|0
condition|)
name|uriNoBack
operator|=
name|uriNoBack
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|uriNoBack
operator|.
name|indexOf
argument_list|(
literal|"backType="
argument_list|)
operator|-
literal|1
argument_list|)
expr_stmt|;
while|while
condition|(
operator|!
name|back
operator|.
name|isEmpty
argument_list|()
operator|&&
operator|!
name|uriNoBack
operator|.
name|equals
argument_list|(
operator|(
operator|(
name|String
index|[]
operator|)
name|back
operator|.
name|lastElement
argument_list|()
operator|)
index|[
literal|0
index|]
argument_list|)
condition|)
name|back
operator|.
name|remove
argument_list|(
name|back
operator|.
name|size
argument_list|()
operator|-
literal|1
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|back
operator|.
name|isEmpty
argument_list|()
condition|)
name|back
operator|.
name|remove
argument_list|(
name|back
operator|.
name|size
argument_list|()
operator|-
literal|1
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|uri
operator|.
name|indexOf
argument_list|(
literal|"backType="
argument_list|)
operator|<
literal|0
operator|&&
name|request
operator|.
name|getAttribute
argument_list|(
literal|"backType"
argument_list|)
operator|!=
literal|null
operator|&&
name|request
operator|.
name|getAttribute
argument_list|(
literal|"backId"
argument_list|)
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|uri
operator|.
name|indexOf
argument_list|(
literal|'?'
argument_list|)
operator|>
literal|0
condition|)
name|uri
operator|+=
literal|"&backType="
operator|+
name|request
operator|.
name|getAttribute
argument_list|(
literal|"backType"
argument_list|)
operator|+
literal|"&backId="
operator|+
name|request
operator|.
name|getAttribute
argument_list|(
literal|"backId"
argument_list|)
operator|+
literal|"#back"
expr_stmt|;
else|else
name|uri
operator|+=
literal|"?backType="
operator|+
name|request
operator|.
name|getAttribute
argument_list|(
literal|"backType"
argument_list|)
operator|+
literal|"&backId="
operator|+
name|request
operator|.
name|getAttribute
argument_list|(
literal|"backId"
argument_list|)
operator|+
literal|"#back"
expr_stmt|;
block|}
name|response
operator|.
name|sendRedirect
argument_list|(
name|response
operator|.
name|encodeURL
argument_list|(
name|uri
argument_list|)
argument_list|)
expr_stmt|;
return|return
literal|true
return|;
block|}
block|}
specifier|public
specifier|static
name|String
name|getBackTree
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|)
block|{
synchronized|synchronized
init|(
name|request
operator|.
name|getSession
argument_list|()
init|)
block|{
name|Vector
name|back
init|=
name|getBackList
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|back
operator|.
name|size
argument_list|()
operator|<=
literal|1
condition|)
return|return
literal|""
return|;
name|StringBuffer
name|ret
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
name|Math
operator|.
name|max
argument_list|(
literal|0
argument_list|,
name|back
operator|.
name|size
argument_list|()
operator|-
name|MAX_BACK_STEPS
argument_list|)
init|;
name|i
operator|<
name|back
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|String
index|[]
name|backItem
init|=
operator|(
name|String
index|[]
operator|)
name|back
operator|.
name|elementAt
argument_list|(
name|i
argument_list|)
decl_stmt|;
comment|//if (!e.hasMoreElements()) continue;
if|if
condition|(
name|ret
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|ret
operator|.
name|append
argument_list|(
literal|"&rarr; "
argument_list|)
expr_stmt|;
name|ret
operator|.
name|append
argument_list|(
literal|"<span class='item'><A href='back.do?uri="
operator|+
name|encodeURL
argument_list|(
name|backItem
index|[
literal|0
index|]
argument_list|)
operator|+
literal|"'>"
operator|+
name|backItem
index|[
literal|1
index|]
operator|+
literal|"</A></span>"
argument_list|)
expr_stmt|;
block|}
return|return
literal|"&nbsp;"
operator|+
name|ret
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
block|}
end_class

end_unit

