begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|filter
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
name|Enumeration
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Hashtable
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
name|javax
operator|.
name|servlet
operator|.
name|Filter
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|FilterChain
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|FilterConfig
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|ServletContext
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
name|ServletRequest
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|ServletResponse
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
name|tiles
operator|.
name|ComponentDefinition
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
name|tiles
operator|.
name|TilesUtil
import|;
end_import

begin_import
import|import
name|org
operator|.
name|dom4j
operator|.
name|Document
import|;
end_import

begin_import
import|import
name|org
operator|.
name|dom4j
operator|.
name|Element
import|;
end_import

begin_import
import|import
name|org
operator|.
name|dom4j
operator|.
name|io
operator|.
name|SAXReader
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
name|User
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
name|web
operator|.
name|Web
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

begin_class
specifier|public
class|class
name|PageAccessFilter
implements|implements
name|Filter
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
name|PageAccessFilter
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|static
name|DecimalFormat
name|sDF
init|=
operator|new
name|DecimalFormat
argument_list|(
literal|"0.00"
argument_list|)
decl_stmt|;
specifier|private
name|ServletContext
name|iContext
decl_stmt|;
specifier|private
name|Hashtable
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|iPath2Tile
init|=
operator|new
name|Hashtable
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|long
name|debugTime
init|=
literal|30000
decl_stmt|;
comment|// Print info about the page if the page load took at least this time.
specifier|private
name|long
name|dumpTime
init|=
literal|300000
decl_stmt|;
comment|// Print debug info about the page if the page load took at least this time.
specifier|private
name|boolean
name|dumpSessionAttribues
init|=
literal|false
decl_stmt|;
comment|// Include session attributes in the dump.
specifier|public
name|void
name|init
parameter_list|(
name|FilterConfig
name|cfg
parameter_list|)
throws|throws
name|ServletException
block|{
name|iContext
operator|=
name|cfg
operator|.
name|getServletContext
argument_list|()
expr_stmt|;
try|try
block|{
name|Document
name|config
init|=
operator|(
operator|new
name|SAXReader
argument_list|()
operator|)
operator|.
name|read
argument_list|(
name|cfg
operator|.
name|getServletContext
argument_list|()
operator|.
name|getResource
argument_list|(
name|cfg
operator|.
name|getInitParameter
argument_list|(
literal|"config"
argument_list|)
argument_list|)
argument_list|)
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|config
operator|.
name|getRootElement
argument_list|()
operator|.
name|element
argument_list|(
literal|"action-mappings"
argument_list|)
operator|.
name|elementIterator
argument_list|(
literal|"action"
argument_list|)
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Element
name|action
init|=
operator|(
name|Element
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|String
name|path
init|=
name|action
operator|.
name|attributeValue
argument_list|(
literal|"path"
argument_list|)
decl_stmt|;
name|String
name|input
init|=
name|action
operator|.
name|attributeValue
argument_list|(
literal|"input"
argument_list|)
decl_stmt|;
if|if
condition|(
name|path
operator|!=
literal|null
operator|&&
name|input
operator|!=
literal|null
condition|)
block|{
name|iPath2Tile
operator|.
name|put
argument_list|(
name|path
operator|+
literal|".do"
argument_list|,
name|input
argument_list|)
expr_stmt|;
block|}
block|}
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
literal|"Unable to read config "
operator|+
name|cfg
operator|.
name|getInitParameter
argument_list|(
literal|"config"
argument_list|)
operator|+
literal|", reason: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|cfg
operator|.
name|getInitParameter
argument_list|(
literal|"debug-time"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|debugTime
operator|=
name|Long
operator|.
name|parseLong
argument_list|(
name|cfg
operator|.
name|getInitParameter
argument_list|(
literal|"debug-time"
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|cfg
operator|.
name|getInitParameter
argument_list|(
literal|"dump-time"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|dumpTime
operator|=
name|Long
operator|.
name|parseLong
argument_list|(
name|cfg
operator|.
name|getInitParameter
argument_list|(
literal|"dump-time"
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|cfg
operator|.
name|getInitParameter
argument_list|(
literal|"session-attributes"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|dumpSessionAttribues
operator|=
name|Boolean
operator|.
name|parseBoolean
argument_list|(
name|cfg
operator|.
name|getInitParameter
argument_list|(
literal|"session-attributes"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|void
name|doFilter
parameter_list|(
name|ServletRequest
name|request
parameter_list|,
name|ServletResponse
name|response
parameter_list|,
name|FilterChain
name|chain
parameter_list|)
throws|throws
name|IOException
throws|,
name|ServletException
block|{
name|long
name|t0
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
if|if
condition|(
name|request
operator|instanceof
name|HttpServletRequest
condition|)
block|{
name|HttpServletRequest
name|r
init|=
operator|(
name|HttpServletRequest
operator|)
name|request
decl_stmt|;
if|if
condition|(
name|r
operator|.
name|getRequestURI
argument_list|()
operator|.
name|endsWith
argument_list|(
literal|".do"
argument_list|)
condition|)
block|{
name|HttpServletResponse
name|x
init|=
operator|(
name|HttpServletResponse
operator|)
name|response
decl_stmt|;
name|String
name|def
init|=
name|r
operator|.
name|getRequestURI
argument_list|()
operator|.
name|substring
argument_list|(
name|r
operator|.
name|getContextPath
argument_list|()
operator|.
name|length
argument_list|()
argument_list|)
decl_stmt|;
try|try
block|{
if|if
condition|(
name|iPath2Tile
operator|.
name|containsKey
argument_list|(
name|def
argument_list|)
condition|)
block|{
name|String
name|tile
init|=
name|iPath2Tile
operator|.
name|get
argument_list|(
name|def
argument_list|)
decl_stmt|;
name|ComponentDefinition
name|c
init|=
name|TilesUtil
operator|.
name|getDefinition
argument_list|(
name|tile
argument_list|,
name|request
argument_list|,
name|iContext
argument_list|)
decl_stmt|;
name|HttpSession
name|s
init|=
name|r
operator|.
name|getSession
argument_list|()
decl_stmt|;
if|if
condition|(
name|c
operator|!=
literal|null
operator|&&
literal|"true"
operator|.
name|equals
argument_list|(
name|c
operator|.
name|getAttribute
argument_list|(
literal|"checkLogin"
argument_list|)
argument_list|)
condition|)
block|{
if|if
condition|(
operator|!
name|Web
operator|.
name|isLoggedIn
argument_list|(
name|s
argument_list|)
condition|)
block|{
name|sLog
operator|.
name|warn
argument_list|(
literal|"Page "
operator|+
name|r
operator|.
name|getRequestURI
argument_list|()
operator|+
literal|" denied: user not logged in"
argument_list|)
expr_stmt|;
if|if
condition|(
name|s
operator|.
name|isNew
argument_list|()
condition|)
name|x
operator|.
name|sendRedirect
argument_list|(
name|x
operator|.
name|encodeURL
argument_list|(
name|r
operator|.
name|getContextPath
argument_list|()
operator|+
literal|"/loginRequired.do?message=Your+timetabling+session+has+expired.+Please+log+in+again."
argument_list|)
argument_list|)
expr_stmt|;
else|else
name|x
operator|.
name|sendRedirect
argument_list|(
name|x
operator|.
name|encodeURL
argument_list|(
name|r
operator|.
name|getContextPath
argument_list|()
operator|+
literal|"/loginRequired.do?message=Login+is+required+to+use+timetabling+application."
argument_list|)
argument_list|)
expr_stmt|;
return|return;
block|}
block|}
if|if
condition|(
name|c
operator|!=
literal|null
operator|&&
literal|"true"
operator|.
name|equals
argument_list|(
name|c
operator|.
name|getAttribute
argument_list|(
literal|"checkRole"
argument_list|)
argument_list|)
condition|)
block|{
name|User
name|user
init|=
name|Web
operator|.
name|getUser
argument_list|(
name|s
argument_list|)
decl_stmt|;
if|if
condition|(
name|user
operator|==
literal|null
operator|||
name|user
operator|.
name|getCurrentRole
argument_list|()
operator|==
literal|null
condition|)
block|{
name|sLog
operator|.
name|warn
argument_list|(
literal|"Page "
operator|+
name|r
operator|.
name|getRequestURI
argument_list|()
operator|+
literal|" denined: no role"
argument_list|)
expr_stmt|;
name|x
operator|.
name|sendRedirect
argument_list|(
name|x
operator|.
name|encodeURL
argument_list|(
name|r
operator|.
name|getContextPath
argument_list|()
operator|+
literal|"/loginRequired.do?message=Insufficient+user+privileges."
argument_list|)
argument_list|)
expr_stmt|;
return|return;
block|}
block|}
if|if
condition|(
name|c
operator|!=
literal|null
operator|&&
literal|"true"
operator|.
name|equals
argument_list|(
name|c
operator|.
name|getAttribute
argument_list|(
literal|"checkAdmin"
argument_list|)
argument_list|)
condition|)
block|{
name|User
name|user
init|=
name|Web
operator|.
name|getUser
argument_list|(
name|s
argument_list|)
decl_stmt|;
if|if
condition|(
name|user
operator|==
literal|null
operator|||
operator|!
name|user
operator|.
name|isAdmin
argument_list|()
condition|)
block|{
name|sLog
operator|.
name|warn
argument_list|(
literal|"Page "
operator|+
name|r
operator|.
name|getRequestURI
argument_list|()
operator|+
literal|" denied: user not admin"
argument_list|)
expr_stmt|;
name|x
operator|.
name|sendRedirect
argument_list|(
name|x
operator|.
name|encodeURL
argument_list|(
name|r
operator|.
name|getContextPath
argument_list|()
operator|+
literal|"/loginRequired.do?message=Insufficient+user+privileges."
argument_list|)
argument_list|)
expr_stmt|;
return|return;
block|}
block|}
if|if
condition|(
name|c
operator|!=
literal|null
operator|&&
literal|"true"
operator|.
name|equals
argument_list|(
name|c
operator|.
name|getAttribute
argument_list|(
literal|"checkAccessLevel"
argument_list|)
argument_list|)
condition|)
block|{
name|String
name|appAccess
init|=
operator|(
name|String
operator|)
name|s
operator|.
name|getAttribute
argument_list|(
name|Constants
operator|.
name|SESSION_APP_ACCESS_LEVEL
argument_list|)
decl_stmt|;
if|if
condition|(
name|appAccess
operator|!=
literal|null
operator|&&
operator|!
literal|"true"
operator|.
name|equalsIgnoreCase
argument_list|(
name|appAccess
argument_list|)
condition|)
block|{
name|sLog
operator|.
name|warn
argument_list|(
literal|"Page "
operator|+
name|r
operator|.
name|getRequestURI
argument_list|()
operator|+
literal|" denied: application access disabled"
argument_list|)
expr_stmt|;
name|x
operator|.
name|sendRedirect
argument_list|(
name|x
operator|.
name|encodeURL
argument_list|(
name|r
operator|.
name|getContextPath
argument_list|()
operator|+
literal|"/loginRequired.do?message=The+application+is+temporarily+unavailable.+Please+try+again+after+some+time."
argument_list|)
argument_list|)
expr_stmt|;
return|return;
block|}
block|}
block|}
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
literal|"Unable to check page access for "
operator|+
name|r
operator|.
name|getRequestURI
argument_list|()
operator|+
literal|", reason: "
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
block|}
comment|// Process request
name|Throwable
name|exception
init|=
literal|null
decl_stmt|;
try|try
block|{
name|chain
operator|.
name|doFilter
argument_list|(
name|request
argument_list|,
name|response
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Throwable
name|t
parameter_list|)
block|{
name|exception
operator|=
name|t
expr_stmt|;
block|}
name|long
name|t1
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
if|if
condition|(
name|request
operator|instanceof
name|HttpServletRequest
operator|&&
operator|(
operator|(
name|t1
operator|-
name|t0
operator|)
operator|>
name|debugTime
operator|||
name|exception
operator|!=
literal|null
operator|)
condition|)
block|{
name|HttpServletRequest
name|r
init|=
operator|(
name|HttpServletRequest
operator|)
name|request
decl_stmt|;
name|String
name|message
init|=
literal|"Page "
operator|+
name|r
operator|.
name|getRequestURI
argument_list|()
operator|+
literal|" took "
operator|+
name|sDF
operator|.
name|format
argument_list|(
operator|(
name|t1
operator|-
name|t0
operator|)
operator|/
literal|1000.0
argument_list|)
operator|+
literal|" s."
decl_stmt|;
if|if
condition|(
name|exception
operator|!=
literal|null
condition|)
block|{
name|message
operator|=
name|exception
operator|+
literal|" seen on page "
operator|+
name|r
operator|.
name|getRequestURI
argument_list|()
operator|+
literal|" (page took "
operator|+
name|sDF
operator|.
name|format
argument_list|(
operator|(
name|t1
operator|-
name|t0
operator|)
operator|/
literal|1000.0
argument_list|)
operator|+
literal|" s)."
expr_stmt|;
block|}
if|if
condition|(
name|exception
operator|!=
literal|null
operator|||
operator|(
name|t1
operator|-
name|t0
operator|)
operator|>
name|dumpTime
condition|)
block|{
name|User
name|u
init|=
name|Web
operator|.
name|getUser
argument_list|(
name|r
operator|.
name|getSession
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|u
operator|==
literal|null
condition|)
block|{
name|message
operator|+=
literal|"\n  User: no user"
expr_stmt|;
block|}
else|else
block|{
name|message
operator|+=
literal|"\n  User: "
operator|+
name|u
operator|.
name|getLogin
argument_list|()
operator|+
operator|(
name|u
operator|.
name|getCurrentRole
argument_list|()
operator|!=
literal|null
condition|?
literal|" ("
operator|+
name|u
operator|.
name|getCurrentRole
argument_list|()
operator|+
literal|")"
else|:
name|u
operator|.
name|isAdmin
argument_list|()
condition|?
literal|"(admin)"
else|:
literal|""
operator|)
expr_stmt|;
block|}
name|message
operator|+=
literal|"\n  Request parameters:"
expr_stmt|;
for|for
control|(
name|Enumeration
name|e
init|=
name|r
operator|.
name|getParameterNames
argument_list|()
init|;
name|e
operator|.
name|hasMoreElements
argument_list|()
condition|;
control|)
block|{
name|String
name|n
init|=
operator|(
name|String
operator|)
name|e
operator|.
name|nextElement
argument_list|()
decl_stmt|;
if|if
condition|(
literal|"password"
operator|.
name|equals
argument_list|(
name|n
argument_list|)
condition|)
continue|continue;
name|message
operator|+=
literal|"\n    "
operator|+
name|n
operator|+
literal|"="
operator|+
name|r
operator|.
name|getParameter
argument_list|(
name|n
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|dumpSessionAttribues
condition|)
block|{
name|message
operator|+=
literal|"\n  Session attributes:"
expr_stmt|;
for|for
control|(
name|Enumeration
name|e
init|=
name|r
operator|.
name|getSession
argument_list|()
operator|.
name|getAttributeNames
argument_list|()
init|;
name|e
operator|.
name|hasMoreElements
argument_list|()
condition|;
control|)
block|{
name|String
name|n
init|=
operator|(
name|String
operator|)
name|e
operator|.
name|nextElement
argument_list|()
decl_stmt|;
if|if
condition|(
name|n
operator|.
name|equals
argument_list|(
literal|"userTrace"
argument_list|)
condition|)
continue|continue;
name|message
operator|+=
literal|"\n    "
operator|+
name|n
operator|+
literal|"="
operator|+
name|r
operator|.
name|getSession
argument_list|()
operator|.
name|getAttribute
argument_list|(
name|n
argument_list|)
expr_stmt|;
block|}
block|}
block|}
else|else
block|{
name|User
name|u
init|=
name|Web
operator|.
name|getUser
argument_list|(
name|r
operator|.
name|getSession
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|u
operator|==
literal|null
condition|)
block|{
name|message
operator|+=
literal|"  (User: no user)"
expr_stmt|;
block|}
else|else
block|{
name|message
operator|+=
literal|"  (User: "
operator|+
name|u
operator|.
name|getLogin
argument_list|()
operator|+
operator|(
name|u
operator|.
name|getCurrentRole
argument_list|()
operator|!=
literal|null
condition|?
literal|", "
operator|+
name|u
operator|.
name|getCurrentRole
argument_list|()
else|:
name|u
operator|.
name|isAdmin
argument_list|()
condition|?
literal|", admin"
else|:
literal|""
operator|)
operator|+
literal|")"
expr_stmt|;
block|}
block|}
if|if
condition|(
name|exception
operator|!=
literal|null
condition|)
name|sLog
operator|.
name|warn
argument_list|(
name|message
argument_list|)
expr_stmt|;
else|else
name|sLog
operator|.
name|info
argument_list|(
name|message
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|exception
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|exception
operator|instanceof
name|ServletException
condition|)
throw|throw
operator|(
name|ServletException
operator|)
name|exception
throw|;
if|if
condition|(
name|exception
operator|instanceof
name|IOException
condition|)
throw|throw
operator|(
name|IOException
operator|)
name|exception
throw|;
if|if
condition|(
name|exception
operator|instanceof
name|RuntimeException
condition|)
throw|throw
operator|(
name|RuntimeException
operator|)
name|exception
throw|;
throw|throw
operator|new
name|ServletException
argument_list|(
name|exception
argument_list|)
throw|;
block|}
block|}
specifier|public
name|void
name|destroy
parameter_list|()
block|{
block|}
block|}
end_class

end_unit

