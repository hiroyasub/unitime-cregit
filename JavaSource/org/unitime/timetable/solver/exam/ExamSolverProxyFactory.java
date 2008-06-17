begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.1 (University Timetabling Application)  * Copyright (C) 2008, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program; if not, write to the Free Software Foundation, Inc.,  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|solver
operator|.
name|exam
package|;
end_package

begin_import
import|import
name|java
operator|.
name|lang
operator|.
name|reflect
operator|.
name|InvocationHandler
import|;
end_import

begin_import
import|import
name|java
operator|.
name|lang
operator|.
name|reflect
operator|.
name|Method
import|;
end_import

begin_import
import|import
name|java
operator|.
name|lang
operator|.
name|reflect
operator|.
name|Proxy
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
name|solver
operator|.
name|remote
operator|.
name|RemoteSolverServerProxy
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|ExamSolverProxyFactory
implements|implements
name|InvocationHandler
block|{
specifier|private
name|RemoteSolverServerProxy
name|iProxy
decl_stmt|;
specifier|private
name|RemoteExamSolverProxy
name|iExamSolverProxy
decl_stmt|;
specifier|private
name|String
name|iPuid
init|=
literal|null
decl_stmt|;
specifier|private
name|ExamSolverProxyFactory
parameter_list|(
name|RemoteSolverServerProxy
name|proxy
parameter_list|,
name|String
name|puid
parameter_list|)
block|{
name|iProxy
operator|=
name|proxy
expr_stmt|;
name|iPuid
operator|=
name|puid
expr_stmt|;
block|}
specifier|public
name|void
name|setExamSolverProxy
parameter_list|(
name|RemoteExamSolverProxy
name|proxy
parameter_list|)
block|{
name|iExamSolverProxy
operator|=
name|proxy
expr_stmt|;
block|}
specifier|public
specifier|static
name|ExamSolverProxy
name|create
parameter_list|(
name|RemoteSolverServerProxy
name|proxy
parameter_list|,
name|String
name|puid
parameter_list|)
block|{
name|ExamSolverProxyFactory
name|handler
init|=
operator|new
name|ExamSolverProxyFactory
argument_list|(
name|proxy
argument_list|,
name|puid
argument_list|)
decl_stmt|;
name|RemoteExamSolverProxy
name|px
init|=
operator|(
name|RemoteExamSolverProxy
operator|)
name|Proxy
operator|.
name|newProxyInstance
argument_list|(
name|ExamSolverProxyFactory
operator|.
name|class
operator|.
name|getClassLoader
argument_list|()
argument_list|,
operator|new
name|Class
index|[]
block|{
name|RemoteExamSolverProxy
operator|.
name|class
block|}
argument_list|,
name|handler
argument_list|)
decl_stmt|;
name|handler
operator|.
name|setExamSolverProxy
argument_list|(
name|px
argument_list|)
expr_stmt|;
return|return
name|px
return|;
block|}
specifier|public
name|RemoteSolverServerProxy
name|getServerProxy
parameter_list|()
block|{
return|return
name|iProxy
return|;
block|}
specifier|public
name|String
name|getHost
parameter_list|()
block|{
return|return
name|iProxy
operator|.
name|getAddress
argument_list|()
operator|.
name|getHostName
argument_list|()
operator|+
literal|":"
operator|+
name|iProxy
operator|.
name|getPort
argument_list|()
return|;
block|}
specifier|public
name|String
name|getPuid
parameter_list|()
block|{
return|return
name|iPuid
return|;
block|}
specifier|public
name|String
name|getHostLabel
parameter_list|()
block|{
name|String
name|hostName
init|=
name|iProxy
operator|.
name|getAddress
argument_list|()
operator|.
name|getHostName
argument_list|()
decl_stmt|;
if|if
condition|(
name|hostName
operator|.
name|indexOf
argument_list|(
literal|'.'
argument_list|)
operator|>=
literal|0
condition|)
name|hostName
operator|=
name|hostName
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|hostName
operator|.
name|indexOf
argument_list|(
literal|'.'
argument_list|)
argument_list|)
expr_stmt|;
try|try
block|{
name|Integer
operator|.
name|parseInt
argument_list|(
name|hostName
argument_list|)
expr_stmt|;
comment|//hostName is an IP address -> return that IP address
name|hostName
operator|=
name|iProxy
operator|.
name|getAddress
argument_list|()
operator|.
name|getHostName
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|x
parameter_list|)
block|{
block|}
return|return
name|hostName
operator|+
literal|":"
operator|+
name|iProxy
operator|.
name|getPort
argument_list|()
return|;
block|}
specifier|public
name|Object
name|invoke
parameter_list|(
name|Object
name|proxy
parameter_list|,
name|Method
name|method
parameter_list|,
name|Object
index|[]
name|args
parameter_list|)
throws|throws
name|Throwable
block|{
try|try
block|{
return|return
name|getClass
argument_list|()
operator|.
name|getMethod
argument_list|(
name|method
operator|.
name|getName
argument_list|()
argument_list|,
name|method
operator|.
name|getParameterTypes
argument_list|()
argument_list|)
operator|.
name|invoke
argument_list|(
name|this
argument_list|,
name|args
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|NoSuchMethodException
name|e
parameter_list|)
block|{
block|}
name|Object
index|[]
name|params
init|=
operator|new
name|Object
index|[
literal|2
operator|*
operator|(
name|args
operator|==
literal|null
condition|?
literal|0
else|:
name|args
operator|.
name|length
operator|)
operator|+
literal|3
index|]
decl_stmt|;
name|params
index|[
literal|0
index|]
operator|=
name|method
operator|.
name|getName
argument_list|()
expr_stmt|;
name|params
index|[
literal|1
index|]
operator|=
literal|"EXAM"
expr_stmt|;
name|params
index|[
literal|2
index|]
operator|=
name|iPuid
expr_stmt|;
if|if
condition|(
name|args
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|args
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|params
index|[
literal|2
operator|*
name|i
operator|+
literal|3
index|]
operator|=
name|method
operator|.
name|getParameterTypes
argument_list|()
index|[
name|i
index|]
expr_stmt|;
name|params
index|[
literal|2
operator|*
name|i
operator|+
literal|4
index|]
operator|=
name|args
index|[
name|i
index|]
expr_stmt|;
block|}
block|}
return|return
name|iProxy
operator|.
name|query
argument_list|(
name|params
argument_list|)
return|;
block|}
block|}
end_class

end_unit

