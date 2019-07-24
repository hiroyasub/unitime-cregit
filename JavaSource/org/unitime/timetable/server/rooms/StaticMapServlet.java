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
name|server
operator|.
name|rooms
package|;
end_package

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Graphics
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|image
operator|.
name|BufferedImage
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
name|net
operator|.
name|MalformedURLException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URL
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URLConnection
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|imageio
operator|.
name|ImageIO
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|ServletException
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
name|HttpServlet
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
name|cpsolver
operator|.
name|ifs
operator|.
name|util
operator|.
name|ToolBox
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
name|MapTileCache
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
name|context
operator|.
name|HttpSessionContext
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|StaticMapServlet
extends|extends
name|HttpServlet
block|{
specifier|protected
specifier|static
name|Log
name|sLog
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|StaticMapServlet
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
specifier|private
specifier|static
name|int
name|sTileSize
init|=
literal|256
decl_stmt|;
specifier|protected
name|SessionContext
name|getSessionContext
parameter_list|()
block|{
return|return
name|HttpSessionContext
operator|.
name|getSessionContext
argument_list|(
name|getServletContext
argument_list|()
argument_list|)
return|;
block|}
specifier|protected
specifier|synchronized
name|BufferedImage
name|fetchTile
parameter_list|(
name|int
name|zoom
parameter_list|,
name|int
name|x
parameter_list|,
name|int
name|y
parameter_list|,
name|HttpServletRequest
name|request
parameter_list|)
throws|throws
name|MalformedURLException
throws|,
name|IOException
block|{
name|byte
index|[]
name|cached
init|=
name|MapTileCache
operator|.
name|get
argument_list|(
name|zoom
argument_list|,
name|x
argument_list|,
name|y
argument_list|)
decl_stmt|;
if|if
condition|(
name|cached
operator|==
literal|null
condition|)
block|{
name|String
name|referer
init|=
name|ApplicationProperty
operator|.
name|UniTimeUrl
operator|.
name|value
argument_list|()
decl_stmt|;
if|if
condition|(
name|referer
operator|==
literal|null
condition|)
name|referer
operator|=
name|request
operator|.
name|getScheme
argument_list|()
operator|+
literal|"://"
operator|+
name|request
operator|.
name|getServerName
argument_list|()
operator|+
literal|":"
operator|+
name|request
operator|.
name|getServerPort
argument_list|()
operator|+
name|request
operator|.
name|getRequestURI
argument_list|()
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|request
operator|.
name|getRequestURI
argument_list|()
operator|.
name|lastIndexOf
argument_list|(
literal|'/'
argument_list|)
argument_list|)
expr_stmt|;
name|String
name|agent
init|=
name|request
operator|.
name|getHeader
argument_list|(
literal|"User-Agent"
argument_list|)
decl_stmt|;
name|String
name|tileURL
init|=
name|ApplicationProperty
operator|.
name|RoomUseLeafletMapTiles
operator|.
name|value
argument_list|()
operator|.
name|replace
argument_list|(
literal|"{s}"
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
operator|(
name|char
operator|)
operator|(
literal|'a'
operator|+
name|ToolBox
operator|.
name|random
argument_list|(
literal|3
argument_list|)
operator|)
argument_list|)
argument_list|)
operator|.
name|replace
argument_list|(
literal|"{z}"
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|zoom
argument_list|)
argument_list|)
operator|.
name|replace
argument_list|(
literal|"{x}"
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|x
argument_list|)
argument_list|)
operator|.
name|replace
argument_list|(
literal|"{y}"
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|y
argument_list|)
argument_list|)
decl_stmt|;
name|ByteArrayOutputStream
name|out
init|=
operator|new
name|ByteArrayOutputStream
argument_list|()
decl_stmt|;
name|InputStream
name|in
init|=
literal|null
decl_stmt|;
try|try
block|{
name|URLConnection
name|con
init|=
operator|new
name|URL
argument_list|(
name|tileURL
argument_list|)
operator|.
name|openConnection
argument_list|()
decl_stmt|;
if|if
condition|(
name|referer
operator|!=
literal|null
condition|)
name|con
operator|.
name|setRequestProperty
argument_list|(
literal|"REFERER"
argument_list|,
name|referer
argument_list|)
expr_stmt|;
if|if
condition|(
name|agent
operator|!=
literal|null
condition|)
name|con
operator|.
name|setRequestProperty
argument_list|(
literal|"User-Agent"
argument_list|,
name|agent
argument_list|)
expr_stmt|;
name|in
operator|=
name|con
operator|.
name|getInputStream
argument_list|()
expr_stmt|;
name|byte
index|[]
name|byteChunk
init|=
operator|new
name|byte
index|[
literal|4096
index|]
decl_stmt|;
name|int
name|n
decl_stmt|;
while|while
condition|(
operator|(
name|n
operator|=
name|in
operator|.
name|read
argument_list|(
name|byteChunk
argument_list|)
operator|)
operator|>
literal|0
condition|)
name|out
operator|.
name|write
argument_list|(
name|byteChunk
argument_list|,
literal|0
argument_list|,
name|n
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
literal|"Failed to fetch tile "
operator|+
name|tileURL
operator|+
literal|": "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
finally|finally
block|{
if|if
condition|(
name|in
operator|!=
literal|null
condition|)
name|in
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
name|cached
operator|=
name|out
operator|.
name|toByteArray
argument_list|()
expr_stmt|;
name|MapTileCache
operator|.
name|put
argument_list|(
name|zoom
argument_list|,
name|x
argument_list|,
name|y
argument_list|,
name|cached
argument_list|)
expr_stmt|;
block|}
return|return
name|ImageIO
operator|.
name|read
argument_list|(
operator|new
name|ByteArrayInputStream
argument_list|(
name|cached
argument_list|)
argument_list|)
return|;
block|}
specifier|protected
name|double
name|lonToTile
parameter_list|(
name|double
name|lon
parameter_list|,
name|int
name|zoom
parameter_list|)
block|{
return|return
operator|(
operator|(
name|lon
operator|+
literal|180.0
operator|)
operator|/
literal|360.0
operator|)
operator|*
name|Math
operator|.
name|pow
argument_list|(
literal|2.0
argument_list|,
name|zoom
argument_list|)
return|;
block|}
specifier|protected
name|double
name|latToTile
parameter_list|(
name|double
name|lat
parameter_list|,
name|int
name|zoom
parameter_list|)
block|{
return|return
operator|(
literal|1.0
operator|-
name|Math
operator|.
name|log
argument_list|(
name|Math
operator|.
name|tan
argument_list|(
name|lat
operator|*
name|Math
operator|.
name|PI
operator|/
literal|180.0
argument_list|)
operator|+
literal|1.0
operator|/
name|Math
operator|.
name|cos
argument_list|(
name|lat
operator|*
name|Math
operator|.
name|PI
operator|/
literal|180
argument_list|)
argument_list|)
operator|/
name|Math
operator|.
name|PI
operator|)
operator|/
literal|2.0
operator|*
name|Math
operator|.
name|pow
argument_list|(
literal|2.0
argument_list|,
name|zoom
argument_list|)
return|;
block|}
specifier|protected
name|BufferedImage
name|createBaseMap
parameter_list|(
name|int
name|width
parameter_list|,
name|int
name|height
parameter_list|,
name|double
name|lat
parameter_list|,
name|double
name|lon
parameter_list|,
name|int
name|zoom
parameter_list|,
name|HttpServletRequest
name|request
parameter_list|)
throws|throws
name|MalformedURLException
throws|,
name|IOException
block|{
name|double
name|centerX
init|=
name|lonToTile
argument_list|(
name|lon
argument_list|,
name|zoom
argument_list|)
decl_stmt|;
name|double
name|centerY
init|=
name|latToTile
argument_list|(
name|lat
argument_list|,
name|zoom
argument_list|)
decl_stmt|;
name|int
name|startX
init|=
operator|(
name|int
operator|)
name|Math
operator|.
name|floor
argument_list|(
name|centerX
operator|-
operator|(
name|width
operator|/
literal|2.0
operator|)
operator|/
name|sTileSize
argument_list|)
decl_stmt|;
name|int
name|startY
init|=
operator|(
name|int
operator|)
name|Math
operator|.
name|floor
argument_list|(
name|centerY
operator|-
operator|(
name|height
operator|/
literal|2.0
operator|)
operator|/
name|sTileSize
argument_list|)
decl_stmt|;
name|int
name|endX
init|=
operator|(
name|int
operator|)
name|Math
operator|.
name|ceil
argument_list|(
name|centerX
operator|+
operator|(
name|width
operator|/
literal|2.0
operator|)
operator|/
name|sTileSize
argument_list|)
decl_stmt|;
name|int
name|endY
init|=
operator|(
name|int
operator|)
name|Math
operator|.
name|ceil
argument_list|(
name|centerY
operator|+
operator|(
name|height
operator|/
literal|2.0
operator|)
operator|/
name|sTileSize
argument_list|)
decl_stmt|;
name|double
name|offsetX
init|=
operator|-
name|Math
operator|.
name|floor
argument_list|(
operator|(
name|centerX
operator|-
name|Math
operator|.
name|floor
argument_list|(
name|centerX
argument_list|)
operator|)
operator|*
name|sTileSize
argument_list|)
decl_stmt|;
name|double
name|offsetY
init|=
operator|-
name|Math
operator|.
name|floor
argument_list|(
operator|(
name|centerY
operator|-
name|Math
operator|.
name|floor
argument_list|(
name|centerY
argument_list|)
operator|)
operator|*
name|sTileSize
argument_list|)
decl_stmt|;
name|offsetX
operator|+=
name|Math
operator|.
name|floor
argument_list|(
name|width
operator|/
literal|2.0
argument_list|)
expr_stmt|;
name|offsetY
operator|+=
name|Math
operator|.
name|floor
argument_list|(
name|height
operator|/
literal|2.0
argument_list|)
expr_stmt|;
name|offsetX
operator|+=
name|Math
operator|.
name|floor
argument_list|(
name|startX
operator|-
name|Math
operator|.
name|floor
argument_list|(
name|centerX
argument_list|)
argument_list|)
operator|*
name|sTileSize
expr_stmt|;
name|offsetY
operator|+=
name|Math
operator|.
name|floor
argument_list|(
name|startY
operator|-
name|Math
operator|.
name|floor
argument_list|(
name|centerY
argument_list|)
argument_list|)
operator|*
name|sTileSize
expr_stmt|;
name|BufferedImage
name|result
init|=
operator|new
name|BufferedImage
argument_list|(
name|width
argument_list|,
name|height
argument_list|,
name|BufferedImage
operator|.
name|TYPE_INT_RGB
argument_list|)
decl_stmt|;
name|Graphics
name|g
init|=
name|result
operator|.
name|getGraphics
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|x
init|=
name|startX
init|;
name|x
operator|<=
name|endX
condition|;
name|x
operator|++
control|)
for|for
control|(
name|int
name|y
init|=
name|startY
init|;
name|y
operator|<=
name|endY
condition|;
name|y
operator|++
control|)
block|{
name|BufferedImage
name|tile
init|=
name|fetchTile
argument_list|(
name|zoom
argument_list|,
name|x
argument_list|,
name|y
argument_list|,
name|request
argument_list|)
decl_stmt|;
if|if
condition|(
name|tile
operator|==
literal|null
condition|)
continue|continue;
name|int
name|destX
init|=
operator|(
name|x
operator|-
name|startX
operator|)
operator|*
name|sTileSize
operator|+
operator|(
name|int
operator|)
name|offsetX
decl_stmt|;
name|int
name|destY
init|=
operator|(
name|y
operator|-
name|startY
operator|)
operator|*
name|sTileSize
operator|+
operator|(
name|int
operator|)
name|offsetY
decl_stmt|;
name|g
operator|.
name|drawImage
argument_list|(
name|tile
argument_list|,
name|destX
argument_list|,
name|destY
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
name|BufferedImage
name|shadow
init|=
name|ImageIO
operator|.
name|read
argument_list|(
name|StaticMapServlet
operator|.
name|class
operator|.
name|getClassLoader
argument_list|()
operator|.
name|getResourceAsStream
argument_list|(
literal|"org/unitime/timetable/server/resources/marker-shadow.png"
argument_list|)
argument_list|)
decl_stmt|;
name|g
operator|.
name|drawImage
argument_list|(
name|shadow
argument_list|,
name|width
operator|/
literal|2
operator|-
literal|12
argument_list|,
name|height
operator|/
literal|2
operator|-
literal|41
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|BufferedImage
name|marker
init|=
name|ImageIO
operator|.
name|read
argument_list|(
name|StaticMapServlet
operator|.
name|class
operator|.
name|getClassLoader
argument_list|()
operator|.
name|getResourceAsStream
argument_list|(
literal|"org/unitime/timetable/server/resources/marker.png"
argument_list|)
argument_list|)
decl_stmt|;
name|g
operator|.
name|drawImage
argument_list|(
name|marker
argument_list|,
name|width
operator|/
literal|2
operator|-
literal|12
argument_list|,
name|height
operator|/
literal|2
operator|-
literal|41
argument_list|,
literal|null
argument_list|)
expr_stmt|;
return|return
name|result
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|doGet
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|HttpServletResponse
name|response
parameter_list|)
throws|throws
name|ServletException
throws|,
name|IOException
block|{
name|String
name|tile
init|=
name|request
operator|.
name|getParameter
argument_list|(
literal|"tile"
argument_list|)
decl_stmt|;
if|if
condition|(
name|tile
operator|!=
literal|null
condition|)
block|{
name|String
index|[]
name|params
init|=
name|tile
operator|.
name|split
argument_list|(
literal|","
argument_list|)
decl_stmt|;
name|BufferedImage
name|image
init|=
name|fetchTile
argument_list|(
name|Integer
operator|.
name|valueOf
argument_list|(
name|params
index|[
literal|0
index|]
argument_list|)
argument_list|,
name|Integer
operator|.
name|valueOf
argument_list|(
name|params
index|[
literal|1
index|]
argument_list|)
argument_list|,
name|Integer
operator|.
name|valueOf
argument_list|(
name|params
index|[
literal|2
index|]
argument_list|)
argument_list|,
name|request
argument_list|)
decl_stmt|;
if|if
condition|(
name|image
operator|==
literal|null
condition|)
block|{
name|response
operator|.
name|sendError
argument_list|(
literal|500
argument_list|,
literal|"Failed to fetch a tile, please check the logs for more details."
argument_list|)
expr_stmt|;
return|return;
block|}
name|response
operator|.
name|setContentType
argument_list|(
literal|"image/png"
argument_list|)
expr_stmt|;
name|response
operator|.
name|setDateHeader
argument_list|(
literal|"Date"
argument_list|,
name|System
operator|.
name|currentTimeMillis
argument_list|()
argument_list|)
expr_stmt|;
name|response
operator|.
name|setDateHeader
argument_list|(
literal|"Expires"
argument_list|,
name|System
operator|.
name|currentTimeMillis
argument_list|()
operator|+
literal|604800000l
argument_list|)
expr_stmt|;
name|response
operator|.
name|setHeader
argument_list|(
literal|"Cache-control"
argument_list|,
literal|"public, max-age=604800"
argument_list|)
expr_stmt|;
name|ImageIO
operator|.
name|write
argument_list|(
name|image
argument_list|,
literal|"PNG"
argument_list|,
name|response
operator|.
name|getOutputStream
argument_list|()
argument_list|)
expr_stmt|;
return|return;
block|}
name|String
name|center
init|=
name|request
operator|.
name|getParameter
argument_list|(
literal|"center"
argument_list|)
decl_stmt|;
name|int
name|zoom
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"zoom"
argument_list|)
argument_list|)
decl_stmt|;
name|String
name|size
init|=
name|request
operator|.
name|getParameter
argument_list|(
literal|"size"
argument_list|)
decl_stmt|;
name|double
name|lat
init|=
name|Double
operator|.
name|parseDouble
argument_list|(
name|center
operator|.
name|split
argument_list|(
literal|","
argument_list|)
index|[
literal|0
index|]
argument_list|)
decl_stmt|;
name|double
name|lon
init|=
name|Double
operator|.
name|parseDouble
argument_list|(
name|center
operator|.
name|split
argument_list|(
literal|","
argument_list|)
index|[
literal|1
index|]
argument_list|)
decl_stmt|;
name|int
name|width
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|size
operator|.
name|split
argument_list|(
literal|"[,x]"
argument_list|)
index|[
literal|0
index|]
argument_list|)
decl_stmt|;
name|int
name|height
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|size
operator|.
name|split
argument_list|(
literal|"[,x]"
argument_list|)
index|[
literal|1
index|]
argument_list|)
decl_stmt|;
name|BufferedImage
name|image
init|=
name|createBaseMap
argument_list|(
name|width
argument_list|,
name|height
argument_list|,
name|lat
argument_list|,
name|lon
argument_list|,
name|zoom
argument_list|,
name|request
argument_list|)
decl_stmt|;
name|response
operator|.
name|setContentType
argument_list|(
literal|"image/png"
argument_list|)
expr_stmt|;
name|response
operator|.
name|setDateHeader
argument_list|(
literal|"Date"
argument_list|,
name|System
operator|.
name|currentTimeMillis
argument_list|()
argument_list|)
expr_stmt|;
name|response
operator|.
name|setDateHeader
argument_list|(
literal|"Expires"
argument_list|,
name|System
operator|.
name|currentTimeMillis
argument_list|()
operator|+
literal|604800000l
argument_list|)
expr_stmt|;
name|response
operator|.
name|setHeader
argument_list|(
literal|"Cache-control"
argument_list|,
literal|"public, max-age=604800"
argument_list|)
expr_stmt|;
name|ImageIO
operator|.
name|write
argument_list|(
name|image
argument_list|,
literal|"PNG"
argument_list|,
name|response
operator|.
name|getOutputStream
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit
