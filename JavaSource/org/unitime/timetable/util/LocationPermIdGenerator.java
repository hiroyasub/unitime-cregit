begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.0 (University Course Timetabling& Student Sectioning Application)  * Copyright (C) 2007, UniTime.org, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program; if not, write to the Free Software Foundation, Inc.,  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|util
package|;
end_package

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
name|org
operator|.
name|hibernate
operator|.
name|HibernateException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|hibernate
operator|.
name|dialect
operator|.
name|Dialect
import|;
end_import

begin_import
import|import
name|org
operator|.
name|hibernate
operator|.
name|engine
operator|.
name|SessionImplementor
import|;
end_import

begin_import
import|import
name|org
operator|.
name|hibernate
operator|.
name|id
operator|.
name|IdentifierGenerator
import|;
end_import

begin_import
import|import
name|org
operator|.
name|hibernate
operator|.
name|id
operator|.
name|PersistentIdentifierGenerator
import|;
end_import

begin_import
import|import
name|org
operator|.
name|hibernate
operator|.
name|id
operator|.
name|SequenceGenerator
import|;
end_import

begin_import
import|import
name|org
operator|.
name|hibernate
operator|.
name|type
operator|.
name|LongType
import|;
end_import

begin_import
import|import
name|org
operator|.
name|hibernate
operator|.
name|type
operator|.
name|Type
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
name|hibernate
operator|.
name|id
operator|.
name|UniqueIdGenerator
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
name|Location
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
name|LocationDAO
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
comment|/**  *   * @author Stephanie Schluttenhofer  *  */
end_comment

begin_class
specifier|public
class|class
name|LocationPermIdGenerator
block|{
specifier|private
specifier|static
name|IdentifierGenerator
name|sGenerator
init|=
literal|null
decl_stmt|;
specifier|protected
specifier|static
name|String
name|sSequence
init|=
literal|"loc_perm_id_seq"
decl_stmt|;
specifier|public
specifier|static
name|IdentifierGenerator
name|getGenerator
parameter_list|()
throws|throws
name|HibernateException
block|{
try|try
block|{
if|if
condition|(
name|sGenerator
operator|!=
literal|null
condition|)
return|return
name|sGenerator
return|;
name|UniqueIdGenerator
name|idGen
init|=
operator|new
name|UniqueIdGenerator
argument_list|()
decl_stmt|;
name|Dialect
name|dialect
init|=
operator|(
name|Dialect
operator|)
name|Class
operator|.
name|forName
argument_list|(
name|LocationDAO
operator|.
name|getConfiguration
argument_list|()
operator|.
name|getProperty
argument_list|(
literal|"hibernate.dialect"
argument_list|)
argument_list|)
operator|.
name|getConstructor
argument_list|(
operator|new
name|Class
index|[]
block|{}
argument_list|)
operator|.
name|newInstance
argument_list|(
operator|new
name|Object
index|[]
block|{}
argument_list|)
decl_stmt|;
name|Type
name|type
init|=
operator|new
name|LongType
argument_list|()
decl_stmt|;
name|Properties
name|params
init|=
operator|new
name|Properties
argument_list|()
decl_stmt|;
name|params
operator|.
name|put
argument_list|(
name|SequenceGenerator
operator|.
name|SEQUENCE
argument_list|,
name|sSequence
argument_list|)
expr_stmt|;
name|params
operator|.
name|put
argument_list|(
name|PersistentIdentifierGenerator
operator|.
name|SCHEMA
argument_list|,
name|_RootDAO
operator|.
name|getConfiguration
argument_list|()
operator|.
name|getProperty
argument_list|(
literal|"default_schema"
argument_list|)
argument_list|)
expr_stmt|;
name|idGen
operator|.
name|configure
argument_list|(
name|type
argument_list|,
name|params
argument_list|,
name|dialect
argument_list|)
expr_stmt|;
name|sGenerator
operator|=
name|idGen
expr_stmt|;
return|return
name|sGenerator
return|;
block|}
catch|catch
parameter_list|(
name|HibernateException
name|e
parameter_list|)
block|{
throw|throw
name|e
throw|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|HibernateException
argument_list|(
name|e
argument_list|)
throw|;
block|}
block|}
specifier|public
specifier|static
name|void
name|setPermanentId
parameter_list|(
name|Location
name|location
parameter_list|)
block|{
name|location
operator|.
name|setPermanentId
argument_list|(
operator|(
operator|(
name|Number
operator|)
name|getGenerator
argument_list|()
operator|.
name|generate
argument_list|(
operator|(
name|SessionImplementor
operator|)
operator|new
name|_RootDAO
argument_list|()
operator|.
name|getSession
argument_list|()
argument_list|,
name|location
argument_list|)
operator|)
operator|.
name|longValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

