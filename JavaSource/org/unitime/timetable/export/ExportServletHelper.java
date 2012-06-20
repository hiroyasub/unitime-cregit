begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.4 (University Timetabling Application)  * Copyright (C) 2012, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|export
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
name|PrintWriter
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
name|net
operator|.
name|URLDecoder
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Enumeration
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
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
name|Map
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
name|unitime
operator|.
name|timetable
operator|.
name|events
operator|.
name|QueryEncoderBackend
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
name|SessionDAO
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
name|spring
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
name|spring
operator|.
name|UserContext
import|;
end_import

begin_class
specifier|public
class|class
name|ExportServletHelper
implements|implements
name|ExportHelper
block|{
specifier|private
name|SessionContext
name|iContext
decl_stmt|;
specifier|private
name|Exporter
operator|.
name|Params
name|iParams
decl_stmt|;
specifier|private
name|HttpServletResponse
name|iResponse
decl_stmt|;
specifier|private
name|PrintWriter
name|iWriter
init|=
literal|null
decl_stmt|;
specifier|private
name|OutputStream
name|iOutputStream
init|=
literal|null
decl_stmt|;
specifier|public
name|ExportServletHelper
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|HttpServletResponse
name|response
parameter_list|,
name|SessionContext
name|context
parameter_list|)
throws|throws
name|UnsupportedEncodingException
block|{
name|iResponse
operator|=
name|response
expr_stmt|;
name|iContext
operator|=
name|context
expr_stmt|;
name|String
name|q
init|=
name|request
operator|.
name|getParameter
argument_list|(
literal|"q"
argument_list|)
decl_stmt|;
if|if
condition|(
name|q
operator|!=
literal|null
condition|)
block|{
name|iParams
operator|=
operator|new
name|QParams
argument_list|(
name|q
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|iParams
operator|=
operator|new
name|HttpParams
argument_list|(
name|request
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|setup
parameter_list|(
name|String
name|content
parameter_list|,
name|String
name|fileName
parameter_list|,
name|boolean
name|binary
parameter_list|)
block|{
name|iResponse
operator|.
name|setContentType
argument_list|(
name|content
operator|+
literal|"; charset=UTF-8"
argument_list|)
expr_stmt|;
name|iResponse
operator|.
name|setCharacterEncoding
argument_list|(
literal|"UTF-8"
argument_list|)
expr_stmt|;
name|iResponse
operator|.
name|setHeader
argument_list|(
literal|"Content-Disposition"
argument_list|,
literal|"attachment; filename=\""
operator|+
name|fileName
operator|+
literal|"\""
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getParameter
parameter_list|(
name|String
name|name
parameter_list|)
block|{
return|return
name|iParams
operator|.
name|getParameter
argument_list|(
name|name
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
index|[]
name|getParameterValues
parameter_list|(
name|String
name|name
parameter_list|)
block|{
return|return
name|iParams
operator|.
name|getParameterValues
argument_list|(
name|name
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Enumeration
argument_list|<
name|String
argument_list|>
name|getParameterNames
parameter_list|()
block|{
return|return
name|iParams
operator|.
name|getParameterNames
argument_list|()
return|;
block|}
specifier|public
name|PrintWriter
name|getWriter
parameter_list|()
throws|throws
name|IOException
block|{
if|if
condition|(
name|iWriter
operator|==
literal|null
condition|)
name|iWriter
operator|=
name|iResponse
operator|.
name|getWriter
argument_list|()
expr_stmt|;
return|return
name|iWriter
return|;
block|}
specifier|public
name|boolean
name|hasWriter
parameter_list|()
block|{
return|return
name|iWriter
operator|!=
literal|null
return|;
block|}
specifier|public
name|OutputStream
name|getOutputStream
parameter_list|()
throws|throws
name|IOException
block|{
if|if
condition|(
name|iOutputStream
operator|==
literal|null
condition|)
name|iOutputStream
operator|=
name|iResponse
operator|.
name|getOutputStream
argument_list|()
expr_stmt|;
return|return
name|iOutputStream
return|;
block|}
specifier|public
name|boolean
name|hasOutputStream
parameter_list|()
block|{
return|return
name|iOutputStream
operator|!=
literal|null
return|;
block|}
specifier|public
name|Long
name|getAcademicSessionId
parameter_list|()
block|{
name|Long
name|sessionId
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|iParams
operator|.
name|getParameter
argument_list|(
literal|"sid"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|sessionId
operator|=
name|Long
operator|.
name|valueOf
argument_list|(
name|iParams
operator|.
name|getParameter
argument_list|(
literal|"sid"
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|iContext
operator|.
name|isAuthenticated
argument_list|()
condition|)
name|sessionId
operator|=
operator|(
name|Long
operator|)
name|iContext
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
expr_stmt|;
else|else
name|sessionId
operator|=
operator|(
name|Long
operator|)
name|iContext
operator|.
name|getAttribute
argument_list|(
literal|"sessionId"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|iParams
operator|.
name|getParameter
argument_list|(
literal|"term"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
name|SessionDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
decl_stmt|;
try|try
block|{
name|List
argument_list|<
name|Long
argument_list|>
name|sessions
init|=
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"select s.uniqueId from Session s where "
operator|+
literal|"s.academicTerm || s.academicYear = :term or "
operator|+
literal|"s.academicTerm || s.academicYear || s.academicInitiative = :term"
argument_list|)
operator|.
name|setString
argument_list|(
literal|"term"
argument_list|,
name|iParams
operator|.
name|getParameter
argument_list|(
literal|"term"
argument_list|)
argument_list|)
operator|.
name|list
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|sessions
operator|.
name|isEmpty
argument_list|()
condition|)
name|sessionId
operator|=
name|sessions
operator|.
name|get
argument_list|(
literal|0
argument_list|)
expr_stmt|;
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
return|return
name|sessionId
return|;
block|}
specifier|public
specifier|static
class|class
name|HttpParams
implements|implements
name|Exporter
operator|.
name|Params
block|{
name|HttpServletRequest
name|iRequest
decl_stmt|;
name|HttpParams
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|)
block|{
name|iRequest
operator|=
name|request
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getParameter
parameter_list|(
name|String
name|name
parameter_list|)
block|{
return|return
name|iRequest
operator|.
name|getParameter
argument_list|(
name|name
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
index|[]
name|getParameterValues
parameter_list|(
name|String
name|name
parameter_list|)
block|{
return|return
name|iRequest
operator|.
name|getParameterValues
argument_list|(
name|name
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Enumeration
argument_list|<
name|String
argument_list|>
name|getParameterNames
parameter_list|()
block|{
return|return
name|iRequest
operator|.
name|getParameterNames
argument_list|()
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|QParams
implements|implements
name|Exporter
operator|.
name|Params
block|{
specifier|private
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|String
argument_list|>
argument_list|>
name|iParams
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|String
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
name|QParams
parameter_list|(
name|String
name|q
parameter_list|)
throws|throws
name|UnsupportedEncodingException
block|{
for|for
control|(
name|String
name|p
range|:
name|QueryEncoderBackend
operator|.
name|decode
argument_list|(
name|q
argument_list|)
operator|.
name|split
argument_list|(
literal|"&"
argument_list|)
control|)
block|{
name|String
name|name
init|=
name|p
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|p
operator|.
name|indexOf
argument_list|(
literal|'='
argument_list|)
argument_list|)
decl_stmt|;
name|String
name|value
init|=
name|URLDecoder
operator|.
name|decode
argument_list|(
name|p
operator|.
name|substring
argument_list|(
name|p
operator|.
name|indexOf
argument_list|(
literal|'='
argument_list|)
operator|+
literal|1
argument_list|)
argument_list|,
literal|"UTF-8"
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|values
init|=
name|iParams
operator|.
name|get
argument_list|(
name|name
argument_list|)
decl_stmt|;
if|if
condition|(
name|values
operator|==
literal|null
condition|)
block|{
name|values
operator|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
expr_stmt|;
name|iParams
operator|.
name|put
argument_list|(
name|name
argument_list|,
name|values
argument_list|)
expr_stmt|;
block|}
name|values
operator|.
name|add
argument_list|(
name|value
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|String
name|getParameter
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|values
init|=
name|iParams
operator|.
name|get
argument_list|(
name|name
argument_list|)
decl_stmt|;
return|return
operator|(
name|values
operator|==
literal|null
operator|||
name|values
operator|.
name|isEmpty
argument_list|()
condition|?
literal|null
else|:
name|values
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
index|[]
name|getParameterValues
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|values
init|=
name|iParams
operator|.
name|get
argument_list|(
name|name
argument_list|)
decl_stmt|;
if|if
condition|(
name|values
operator|==
literal|null
condition|)
return|return
literal|null
return|;
name|String
index|[]
name|ret
init|=
operator|new
name|String
index|[
name|values
operator|.
name|size
argument_list|()
index|]
decl_stmt|;
name|values
operator|.
name|toArray
argument_list|(
name|ret
argument_list|)
expr_stmt|;
return|return
name|ret
return|;
block|}
annotation|@
name|Override
specifier|public
name|Enumeration
argument_list|<
name|String
argument_list|>
name|getParameterNames
parameter_list|()
block|{
specifier|final
name|Iterator
argument_list|<
name|String
argument_list|>
name|iterator
init|=
name|iParams
operator|.
name|keySet
argument_list|()
operator|.
name|iterator
argument_list|()
decl_stmt|;
return|return
operator|new
name|Enumeration
argument_list|<
name|String
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|boolean
name|hasMoreElements
parameter_list|()
block|{
return|return
name|iterator
operator|.
name|hasNext
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|nextElement
parameter_list|()
block|{
return|return
name|iterator
operator|.
name|next
argument_list|()
return|;
block|}
block|}
return|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|UserContext
name|getUser
parameter_list|()
block|{
return|return
name|iContext
operator|.
name|getUser
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isRequestEncoded
parameter_list|()
block|{
return|return
name|iParams
operator|instanceof
name|QParams
return|;
block|}
block|}
end_class

end_unit

