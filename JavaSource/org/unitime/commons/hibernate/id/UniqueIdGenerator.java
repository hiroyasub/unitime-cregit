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
name|commons
operator|.
name|hibernate
operator|.
name|id
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|Serializable
import|;
end_import

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
name|MappingException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|hibernate
operator|.
name|cfg
operator|.
name|Configuration
import|;
end_import

begin_import
import|import
name|org
operator|.
name|hibernate
operator|.
name|cfg
operator|.
name|ObjectNameNormalizer
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
name|Configurable
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
name|type
operator|.
name|Type
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|UniqueIdGenerator
implements|implements
name|IdentifierGenerator
implements|,
name|Configurable
block|{
name|IdentifierGenerator
name|iGenerator
init|=
literal|null
decl_stmt|;
specifier|private
specifier|static
name|String
name|sGenClass
init|=
literal|null
decl_stmt|;
specifier|private
specifier|static
name|String
name|sDefaultSchema
init|=
literal|null
decl_stmt|;
specifier|private
specifier|static
name|ObjectNameNormalizer
name|sNormalizer
init|=
literal|null
decl_stmt|;
specifier|public
specifier|static
name|void
name|configure
parameter_list|(
name|Configuration
name|config
parameter_list|)
block|{
name|sGenClass
operator|=
name|config
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.uniqueid.generator"
argument_list|)
expr_stmt|;
if|if
condition|(
name|sGenClass
operator|==
literal|null
condition|)
name|sGenClass
operator|=
literal|"org.hibernate.id.SequenceGenerator"
expr_stmt|;
name|sDefaultSchema
operator|=
name|config
operator|.
name|getProperty
argument_list|(
literal|"default_schema"
argument_list|)
expr_stmt|;
name|sNormalizer
operator|=
name|config
operator|.
name|createMappings
argument_list|()
operator|.
name|getObjectNameNormalizer
argument_list|()
expr_stmt|;
block|}
specifier|public
name|IdentifierGenerator
name|getGenerator
parameter_list|()
throws|throws
name|HibernateException
block|{
if|if
condition|(
name|iGenerator
operator|==
literal|null
condition|)
block|{
if|if
condition|(
name|sGenClass
operator|==
literal|null
condition|)
throw|throw
operator|new
name|HibernateException
argument_list|(
literal|"UniqueIdGenerator is not configured, please call configure(Config) first."
argument_list|)
throw|;
try|try
block|{
name|iGenerator
operator|=
operator|(
name|IdentifierGenerator
operator|)
name|Class
operator|.
name|forName
argument_list|(
name|sGenClass
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
expr_stmt|;
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
literal|"Unable to initialize uniqueId generator, reason: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
return|return
name|iGenerator
return|;
block|}
specifier|public
name|Serializable
name|generate
parameter_list|(
name|SessionImplementor
name|session
parameter_list|,
name|Object
name|object
parameter_list|)
throws|throws
name|HibernateException
block|{
return|return
name|getGenerator
argument_list|()
operator|.
name|generate
argument_list|(
name|session
argument_list|,
name|object
argument_list|)
return|;
block|}
specifier|public
name|void
name|configure
parameter_list|(
name|Type
name|type
parameter_list|,
name|Properties
name|params
parameter_list|,
name|Dialect
name|d
parameter_list|)
throws|throws
name|MappingException
block|{
if|if
condition|(
name|getGenerator
argument_list|()
operator|instanceof
name|Configurable
condition|)
block|{
if|if
condition|(
name|params
operator|.
name|getProperty
argument_list|(
literal|"schema"
argument_list|)
operator|==
literal|null
operator|&&
name|sDefaultSchema
operator|!=
literal|null
condition|)
name|params
operator|.
name|setProperty
argument_list|(
literal|"schema"
argument_list|,
name|sDefaultSchema
argument_list|)
expr_stmt|;
if|if
condition|(
name|params
operator|.
name|get
argument_list|(
literal|"identifier_normalizer"
argument_list|)
operator|==
literal|null
operator|&&
name|sNormalizer
operator|!=
literal|null
condition|)
name|params
operator|.
name|put
argument_list|(
literal|"identifier_normalizer"
argument_list|,
name|sNormalizer
argument_list|)
expr_stmt|;
operator|(
operator|(
name|Configurable
operator|)
name|getGenerator
argument_list|()
operator|)
operator|.
name|configure
argument_list|(
name|type
argument_list|,
name|params
argument_list|,
name|d
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

