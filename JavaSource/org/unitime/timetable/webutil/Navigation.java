begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 (University Timetabling Application)  * Copyright (C) 2008 - 2010, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|util
operator|.
name|Collection
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
name|HttpSession
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
name|Debug
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

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|Navigation
block|{
specifier|public
specifier|static
name|String
name|sLastDisplayedIdsSessionAttribute
init|=
literal|"lastDispIds"
decl_stmt|;
specifier|public
specifier|static
name|int
name|sNrLevels
init|=
literal|3
decl_stmt|;
specifier|public
specifier|static
name|int
name|sInstructionalOfferingLevel
init|=
literal|0
decl_stmt|;
specifier|public
specifier|static
name|int
name|sSchedulingSubpartLevel
init|=
literal|1
decl_stmt|;
specifier|public
specifier|static
name|int
name|sClassLevel
init|=
literal|2
decl_stmt|;
specifier|public
specifier|static
name|Long
name|getNext
parameter_list|(
name|HttpSession
name|session
parameter_list|,
name|int
name|level
parameter_list|,
name|Long
name|id
parameter_list|)
block|{
name|Vector
index|[]
name|ids
init|=
operator|(
name|Vector
index|[]
operator|)
name|session
operator|.
name|getAttribute
argument_list|(
name|sLastDisplayedIdsSessionAttribute
argument_list|)
decl_stmt|;
name|int
name|idx
init|=
operator|(
name|ids
operator|==
literal|null
condition|?
operator|-
literal|1
else|:
name|ids
index|[
name|level
index|]
operator|.
name|indexOf
argument_list|(
name|id
argument_list|)
operator|)
decl_stmt|;
if|if
condition|(
name|idx
operator|>=
literal|0
condition|)
block|{
try|try
block|{
return|return
operator|(
name|Long
operator|)
name|ids
index|[
name|level
index|]
operator|.
name|elementAt
argument_list|(
name|idx
operator|+
literal|1
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|ArrayIndexOutOfBoundsException
name|e
parameter_list|)
block|{
return|return
operator|new
name|Long
argument_list|(
operator|-
literal|1
argument_list|)
return|;
block|}
block|}
return|return
literal|null
return|;
block|}
specifier|public
specifier|static
name|Long
name|getPrevious
parameter_list|(
name|HttpSession
name|session
parameter_list|,
name|int
name|level
parameter_list|,
name|Long
name|id
parameter_list|)
block|{
name|Vector
index|[]
name|ids
init|=
operator|(
name|Vector
index|[]
operator|)
name|session
operator|.
name|getAttribute
argument_list|(
name|sLastDisplayedIdsSessionAttribute
argument_list|)
decl_stmt|;
name|int
name|idx
init|=
operator|(
name|ids
operator|==
literal|null
condition|?
operator|-
literal|1
else|:
name|ids
index|[
name|level
index|]
operator|.
name|indexOf
argument_list|(
name|id
argument_list|)
operator|)
decl_stmt|;
if|if
condition|(
name|idx
operator|>=
literal|0
condition|)
block|{
try|try
block|{
return|return
operator|(
name|Long
operator|)
name|ids
index|[
name|level
index|]
operator|.
name|elementAt
argument_list|(
name|idx
operator|-
literal|1
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|ArrayIndexOutOfBoundsException
name|e
parameter_list|)
block|{
return|return
operator|new
name|Long
argument_list|(
operator|-
literal|1
argument_list|)
return|;
block|}
block|}
return|return
literal|null
return|;
block|}
specifier|public
specifier|static
name|void
name|set
parameter_list|(
name|HttpSession
name|session
parameter_list|,
name|int
name|level
parameter_list|,
name|Collection
name|entities
parameter_list|)
block|{
name|Vector
index|[]
name|ids
init|=
operator|(
name|Vector
index|[]
operator|)
name|session
operator|.
name|getAttribute
argument_list|(
name|sLastDisplayedIdsSessionAttribute
argument_list|)
decl_stmt|;
if|if
condition|(
name|ids
operator|==
literal|null
condition|)
block|{
name|ids
operator|=
operator|new
name|Vector
index|[
name|sNrLevels
index|]
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|sNrLevels
condition|;
name|i
operator|++
control|)
name|ids
index|[
name|i
index|]
operator|=
operator|new
name|Vector
argument_list|()
expr_stmt|;
name|session
operator|.
name|setAttribute
argument_list|(
name|sLastDisplayedIdsSessionAttribute
argument_list|,
name|ids
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|int
name|i
init|=
name|level
init|;
name|i
operator|<
name|sNrLevels
condition|;
name|i
operator|++
control|)
name|ids
index|[
name|i
index|]
operator|.
name|clear
argument_list|()
expr_stmt|;
if|if
condition|(
name|entities
operator|==
literal|null
operator|||
name|entities
operator|.
name|isEmpty
argument_list|()
condition|)
return|return;
for|for
control|(
name|Iterator
name|i
init|=
name|entities
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Object
name|o
init|=
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|o
operator|instanceof
name|Long
condition|)
block|{
name|ids
index|[
name|level
index|]
operator|.
name|add
argument_list|(
name|o
argument_list|)
expr_stmt|;
block|}
else|else
block|{
try|try
block|{
if|if
condition|(
name|o
operator|.
name|getClass
argument_list|()
operator|.
name|isArray
argument_list|()
condition|)
name|ids
index|[
name|level
index|]
operator|.
name|add
argument_list|(
operator|(
operator|(
name|Object
index|[]
operator|)
name|o
operator|)
index|[
literal|0
index|]
operator|.
name|getClass
argument_list|()
operator|.
name|getMethod
argument_list|(
literal|"getUniqueId"
argument_list|,
operator|new
name|Class
index|[]
block|{}
argument_list|)
operator|.
name|invoke
argument_list|(
operator|(
operator|(
name|Object
index|[]
operator|)
name|o
operator|)
index|[
literal|0
index|]
argument_list|,
operator|new
name|Object
index|[]
block|{}
argument_list|)
argument_list|)
expr_stmt|;
else|else
name|ids
index|[
name|level
index|]
operator|.
name|add
argument_list|(
name|o
operator|.
name|getClass
argument_list|()
operator|.
name|getMethod
argument_list|(
literal|"getUniqueId"
argument_list|,
operator|new
name|Class
index|[]
block|{}
argument_list|)
operator|.
name|invoke
argument_list|(
name|o
argument_list|,
operator|new
name|Object
index|[]
block|{}
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|Debug
operator|.
name|error
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|//System.out.println("SET["+level+"]:"+ids[level]);
block|}
specifier|public
specifier|static
name|void
name|set
parameter_list|(
name|SessionContext
name|session
parameter_list|,
name|int
name|level
parameter_list|,
name|Collection
name|entities
parameter_list|)
block|{
name|Vector
index|[]
name|ids
init|=
operator|(
name|Vector
index|[]
operator|)
name|session
operator|.
name|getAttribute
argument_list|(
name|sLastDisplayedIdsSessionAttribute
argument_list|)
decl_stmt|;
if|if
condition|(
name|ids
operator|==
literal|null
condition|)
block|{
name|ids
operator|=
operator|new
name|Vector
index|[
name|sNrLevels
index|]
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|sNrLevels
condition|;
name|i
operator|++
control|)
name|ids
index|[
name|i
index|]
operator|=
operator|new
name|Vector
argument_list|()
expr_stmt|;
name|session
operator|.
name|setAttribute
argument_list|(
name|sLastDisplayedIdsSessionAttribute
argument_list|,
name|ids
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|int
name|i
init|=
name|level
init|;
name|i
operator|<
name|sNrLevels
condition|;
name|i
operator|++
control|)
name|ids
index|[
name|i
index|]
operator|.
name|clear
argument_list|()
expr_stmt|;
if|if
condition|(
name|entities
operator|==
literal|null
operator|||
name|entities
operator|.
name|isEmpty
argument_list|()
condition|)
return|return;
for|for
control|(
name|Iterator
name|i
init|=
name|entities
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Object
name|o
init|=
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|o
operator|instanceof
name|Long
condition|)
block|{
name|ids
index|[
name|level
index|]
operator|.
name|add
argument_list|(
name|o
argument_list|)
expr_stmt|;
block|}
else|else
block|{
try|try
block|{
if|if
condition|(
name|o
operator|.
name|getClass
argument_list|()
operator|.
name|isArray
argument_list|()
condition|)
name|ids
index|[
name|level
index|]
operator|.
name|add
argument_list|(
operator|(
operator|(
name|Object
index|[]
operator|)
name|o
operator|)
index|[
literal|0
index|]
operator|.
name|getClass
argument_list|()
operator|.
name|getMethod
argument_list|(
literal|"getUniqueId"
argument_list|,
operator|new
name|Class
index|[]
block|{}
argument_list|)
operator|.
name|invoke
argument_list|(
operator|(
operator|(
name|Object
index|[]
operator|)
name|o
operator|)
index|[
literal|0
index|]
argument_list|,
operator|new
name|Object
index|[]
block|{}
argument_list|)
argument_list|)
expr_stmt|;
else|else
name|ids
index|[
name|level
index|]
operator|.
name|add
argument_list|(
name|o
operator|.
name|getClass
argument_list|()
operator|.
name|getMethod
argument_list|(
literal|"getUniqueId"
argument_list|,
operator|new
name|Class
index|[]
block|{}
argument_list|)
operator|.
name|invoke
argument_list|(
name|o
argument_list|,
operator|new
name|Object
index|[]
block|{}
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|Debug
operator|.
name|error
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|//System.out.println("SET["+level+"]:"+ids[level]);
block|}
block|}
end_class

end_unit

