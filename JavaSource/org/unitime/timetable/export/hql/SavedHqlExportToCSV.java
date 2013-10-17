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
name|export
operator|.
name|hql
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
name|ArrayList
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
name|dom4j
operator|.
name|Document
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
name|SessionFactory
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
name|spi
operator|.
name|SessionFactoryImplementor
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
name|spi
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
name|metadata
operator|.
name|ClassMetadata
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
name|CollectionType
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
name|springframework
operator|.
name|stereotype
operator|.
name|Service
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|localization
operator|.
name|impl
operator|.
name|Localization
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
name|export
operator|.
name|CSVPrinter
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
name|export
operator|.
name|ExportHelper
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
name|export
operator|.
name|Exporter
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
name|resources
operator|.
name|GwtMessages
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
name|PageAccessException
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
name|SavedHQLException
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
name|SavedHQLInterface
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
name|SavedHQL
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
name|SavedHQLDAO
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
name|UserContext
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|Service
argument_list|(
literal|"org.unitime.timetable.export.Exporter:hql-report.csv"
argument_list|)
specifier|public
class|class
name|SavedHqlExportToCSV
implements|implements
name|Exporter
block|{
specifier|protected
specifier|static
name|GwtMessages
name|MESSAGES
init|=
name|Localization
operator|.
name|create
argument_list|(
name|GwtMessages
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|static
name|Logger
name|sLog
init|=
name|Logger
operator|.
name|getLogger
argument_list|(
name|SavedHqlExportToCSV
operator|.
name|class
argument_list|)
decl_stmt|;
annotation|@
name|Override
specifier|public
name|String
name|reference
parameter_list|()
block|{
return|return
literal|"hql-report.csv"
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|export
parameter_list|(
name|ExportHelper
name|helper
parameter_list|)
throws|throws
name|IOException
block|{
comment|// Check rights
comment|// FIXME: helper.getSessionContext().checkPermission(Right.???);
comment|// Retrive report
name|SavedHQL
name|hql
init|=
name|SavedHQLDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|helper
operator|.
name|getParameter
argument_list|(
literal|"report"
argument_list|)
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|hql
operator|==
literal|null
condition|)
throw|throw
operator|new
name|SavedHQLException
argument_list|(
literal|"No report provided."
argument_list|)
throw|;
name|SavedHQLInterface
operator|.
name|Query
name|q
init|=
operator|new
name|SavedHQLInterface
operator|.
name|Query
argument_list|()
decl_stmt|;
name|q
operator|.
name|setName
argument_list|(
name|hql
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|q
operator|.
name|setId
argument_list|(
name|hql
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|q
operator|.
name|setQuery
argument_list|(
name|hql
operator|.
name|getQuery
argument_list|()
argument_list|)
expr_stmt|;
name|q
operator|.
name|setFlags
argument_list|(
name|hql
operator|.
name|getType
argument_list|()
argument_list|)
expr_stmt|;
name|q
operator|.
name|setDescription
argument_list|(
name|hql
operator|.
name|getDescription
argument_list|()
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|SavedHQLInterface
operator|.
name|IdValue
argument_list|>
name|params
init|=
operator|new
name|ArrayList
argument_list|<
name|SavedHQLInterface
operator|.
name|IdValue
argument_list|>
argument_list|()
decl_stmt|;
if|if
condition|(
name|helper
operator|.
name|getParameter
argument_list|(
literal|"params"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|String
index|[]
name|p
init|=
name|helper
operator|.
name|getParameter
argument_list|(
literal|"params"
argument_list|)
operator|.
name|split
argument_list|(
literal|":"
argument_list|)
decl_stmt|;
name|int
name|i
init|=
literal|0
decl_stmt|;
for|for
control|(
name|SavedHQL
operator|.
name|Option
name|o
range|:
name|SavedHQL
operator|.
name|Option
operator|.
name|values
argument_list|()
control|)
block|{
if|if
condition|(
operator|!
name|o
operator|.
name|allowSingleSelection
argument_list|()
operator|&&
operator|!
name|o
operator|.
name|allowMultiSelection
argument_list|()
condition|)
continue|continue;
if|if
condition|(
name|q
operator|.
name|getQuery
argument_list|()
operator|.
name|contains
argument_list|(
literal|"%"
operator|+
name|o
operator|.
name|name
argument_list|()
operator|+
literal|"%"
argument_list|)
condition|)
block|{
name|SavedHQLInterface
operator|.
name|IdValue
name|v
init|=
operator|new
name|SavedHQLInterface
operator|.
name|IdValue
argument_list|()
decl_stmt|;
name|v
operator|.
name|setValue
argument_list|(
name|o
operator|.
name|name
argument_list|()
argument_list|)
expr_stmt|;
name|v
operator|.
name|setText
argument_list|(
name|i
operator|<
name|p
operator|.
name|length
condition|?
name|p
index|[
name|i
index|]
else|:
literal|""
argument_list|)
expr_stmt|;
name|params
operator|.
name|add
argument_list|(
name|v
argument_list|)
expr_stmt|;
name|i
operator|++
expr_stmt|;
block|}
block|}
block|}
name|Printer
name|out
init|=
operator|new
name|CSVPrinter
argument_list|(
name|helper
operator|.
name|getWriter
argument_list|()
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|helper
operator|.
name|setup
argument_list|(
name|out
operator|.
name|getContentType
argument_list|()
argument_list|,
name|q
operator|.
name|getName
argument_list|()
operator|.
name|replace
argument_list|(
literal|'/'
argument_list|,
literal|'-'
argument_list|)
operator|.
name|replace
argument_list|(
literal|'\\'
argument_list|,
literal|'-'
argument_list|)
operator|.
name|replace
argument_list|(
literal|':'
argument_list|,
literal|'-'
argument_list|)
operator|+
literal|".csv"
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|execute
argument_list|(
name|helper
operator|.
name|getSessionContext
argument_list|()
operator|.
name|getUser
argument_list|()
argument_list|,
name|out
argument_list|,
name|q
argument_list|,
name|params
argument_list|,
literal|0
argument_list|,
operator|-
literal|1
argument_list|)
expr_stmt|;
name|out
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
specifier|public
specifier|static
name|void
name|execute
parameter_list|(
name|UserContext
name|user
parameter_list|,
name|Printer
name|out
parameter_list|,
name|SavedHQLInterface
operator|.
name|Query
name|query
parameter_list|,
name|List
argument_list|<
name|SavedHQLInterface
operator|.
name|IdValue
argument_list|>
name|options
parameter_list|,
name|int
name|fromRow
parameter_list|,
name|int
name|maxRows
parameter_list|)
throws|throws
name|SavedHQLException
throws|,
name|PageAccessException
block|{
try|try
block|{
name|String
name|hql
init|=
name|query
operator|.
name|getQuery
argument_list|()
decl_stmt|;
for|for
control|(
name|SavedHQL
operator|.
name|Option
name|o
range|:
name|SavedHQL
operator|.
name|Option
operator|.
name|values
argument_list|()
control|)
block|{
if|if
condition|(
name|hql
operator|.
name|indexOf
argument_list|(
literal|"%"
operator|+
name|o
operator|.
name|name
argument_list|()
operator|+
literal|"%"
argument_list|)
operator|>=
literal|0
condition|)
block|{
name|String
name|value
init|=
literal|null
decl_stmt|;
for|for
control|(
name|SavedHQLInterface
operator|.
name|IdValue
name|v
range|:
name|options
control|)
if|if
condition|(
name|o
operator|.
name|name
argument_list|()
operator|.
name|equals
argument_list|(
name|v
operator|.
name|getValue
argument_list|()
argument_list|)
condition|)
block|{
name|value
operator|=
name|v
operator|.
name|getText
argument_list|()
expr_stmt|;
break|break;
block|}
if|if
condition|(
name|value
operator|==
literal|null
operator|||
name|value
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|Map
argument_list|<
name|Long
argument_list|,
name|String
argument_list|>
name|vals
init|=
name|o
operator|.
name|values
argument_list|(
name|user
argument_list|)
decl_stmt|;
if|if
condition|(
name|vals
operator|==
literal|null
operator|||
name|vals
operator|.
name|isEmpty
argument_list|()
condition|)
throw|throw
operator|new
name|SavedHQLException
argument_list|(
name|MESSAGES
operator|.
name|errorUnableToSetParameterNoValues
argument_list|(
name|o
operator|.
name|name
argument_list|()
argument_list|)
argument_list|)
throw|;
name|value
operator|=
literal|""
expr_stmt|;
for|for
control|(
name|Long
name|id
range|:
name|vals
operator|.
name|keySet
argument_list|()
control|)
block|{
if|if
condition|(
operator|!
name|value
operator|.
name|isEmpty
argument_list|()
condition|)
name|value
operator|+=
literal|","
expr_stmt|;
name|value
operator|+=
name|id
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
block|}
name|hql
operator|=
name|hql
operator|.
name|replace
argument_list|(
literal|"%"
operator|+
name|o
operator|.
name|name
argument_list|()
operator|+
literal|"%"
argument_list|,
literal|"("
operator|+
name|value
operator|+
literal|")"
argument_list|)
expr_stmt|;
block|}
block|}
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
name|SavedHQLDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
decl_stmt|;
name|org
operator|.
name|hibernate
operator|.
name|Query
name|q
init|=
name|hibSession
operator|.
name|createQuery
argument_list|(
name|hql
argument_list|)
decl_stmt|;
if|if
condition|(
name|maxRows
operator|>
literal|0
condition|)
name|q
operator|.
name|setMaxResults
argument_list|(
name|maxRows
argument_list|)
expr_stmt|;
if|if
condition|(
name|fromRow
operator|>
literal|0
condition|)
name|q
operator|.
name|setFirstResult
argument_list|(
name|fromRow
argument_list|)
expr_stmt|;
name|q
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|int
name|len
init|=
operator|-
literal|1
decl_stmt|;
for|for
control|(
name|Object
name|o
range|:
name|q
operator|.
name|list
argument_list|()
control|)
block|{
if|if
condition|(
name|len
operator|<
literal|0
condition|)
block|{
name|len
operator|=
name|length
argument_list|(
name|o
argument_list|)
expr_stmt|;
name|String
index|[]
name|line
init|=
operator|new
name|String
index|[
name|len
index|]
decl_stmt|;
name|header
argument_list|(
name|line
argument_list|,
name|o
argument_list|,
name|q
operator|.
name|getReturnAliases
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|line
operator|.
name|length
operator|>
literal|0
operator|&&
name|line
index|[
literal|0
index|]
operator|.
name|startsWith
argument_list|(
literal|"__"
argument_list|)
condition|)
name|out
operator|.
name|hideColumn
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|out
operator|.
name|printHeader
argument_list|(
name|line
argument_list|)
expr_stmt|;
block|}
name|String
index|[]
name|line
init|=
operator|new
name|String
index|[
name|len
index|]
decl_stmt|;
name|line
argument_list|(
name|line
argument_list|,
name|o
argument_list|,
operator|(
name|SessionImplementor
operator|)
name|hibSession
argument_list|)
expr_stmt|;
name|out
operator|.
name|printLine
argument_list|(
name|line
argument_list|)
expr_stmt|;
name|out
operator|.
name|flush
argument_list|()
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|PageAccessException
name|e
parameter_list|)
block|{
throw|throw
name|e
throw|;
block|}
catch|catch
parameter_list|(
name|SavedHQLException
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
name|sLog
operator|.
name|error
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|SavedHQLException
argument_list|(
name|MESSAGES
operator|.
name|failedExecution
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
operator|+
operator|(
name|e
operator|.
name|getCause
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
literal|" ("
operator|+
name|e
operator|.
name|getCause
argument_list|()
operator|.
name|getMessage
argument_list|()
operator|+
literal|")"
operator|)
argument_list|)
argument_list|)
throw|;
block|}
block|}
specifier|private
specifier|static
name|boolean
name|skip
parameter_list|(
name|Type
name|t
parameter_list|,
name|boolean
name|lazy
parameter_list|)
block|{
try|try
block|{
if|if
condition|(
name|t
operator|.
name|isCollectionType
argument_list|()
condition|)
block|{
if|if
condition|(
operator|!
name|lazy
condition|)
return|return
literal|true
return|;
name|SessionFactory
name|hibSessionFactory
init|=
operator|new
name|_RootDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|getSessionFactory
argument_list|()
decl_stmt|;
name|Type
name|w
init|=
operator|(
operator|(
name|CollectionType
operator|)
name|t
operator|)
operator|.
name|getElementType
argument_list|(
operator|(
name|SessionFactoryImplementor
operator|)
name|hibSessionFactory
argument_list|)
decl_stmt|;
name|Class
name|ts
init|=
name|w
operator|.
name|getReturnedClass
argument_list|()
operator|.
name|getMethod
argument_list|(
literal|"toString"
argument_list|,
operator|new
name|Class
index|[]
block|{}
argument_list|)
operator|.
name|getDeclaringClass
argument_list|()
decl_stmt|;
return|return
operator|(
name|ts
operator|.
name|equals
argument_list|(
name|Object
operator|.
name|class
argument_list|)
operator|||
name|ts
operator|.
name|getName
argument_list|()
operator|.
name|startsWith
argument_list|(
literal|"org.unitime.timetable.model.base.Base"
argument_list|)
operator|)
return|;
block|}
block|}
catch|catch
parameter_list|(
name|MappingException
name|e
parameter_list|)
block|{
return|return
literal|true
return|;
block|}
catch|catch
parameter_list|(
name|NoSuchMethodException
name|e
parameter_list|)
block|{
return|return
literal|true
return|;
block|}
try|try
block|{
name|Class
name|ts
init|=
name|t
operator|.
name|getReturnedClass
argument_list|()
operator|.
name|getMethod
argument_list|(
literal|"toString"
argument_list|,
operator|new
name|Class
index|[]
block|{}
argument_list|)
operator|.
name|getDeclaringClass
argument_list|()
decl_stmt|;
return|return
operator|(
name|ts
operator|.
name|equals
argument_list|(
name|Object
operator|.
name|class
argument_list|)
operator|||
name|ts
operator|.
name|getName
argument_list|()
operator|.
name|startsWith
argument_list|(
literal|"org.unitime.timetable.model.base.Base"
argument_list|)
operator|)
return|;
block|}
catch|catch
parameter_list|(
name|NoSuchMethodException
name|e
parameter_list|)
block|{
return|return
literal|true
return|;
block|}
block|}
specifier|private
specifier|static
name|int
name|length
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
if|if
condition|(
name|o
operator|==
literal|null
condition|)
return|return
literal|1
return|;
name|int
name|len
init|=
literal|0
decl_stmt|;
if|if
condition|(
name|o
operator|instanceof
name|Object
index|[]
condition|)
block|{
for|for
control|(
name|Object
name|x
range|:
operator|(
name|Object
index|[]
operator|)
name|o
control|)
block|{
if|if
condition|(
name|x
operator|==
literal|null
condition|)
block|{
name|len
operator|++
expr_stmt|;
block|}
else|else
block|{
name|ClassMetadata
name|meta
init|=
name|SavedHQLDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|getSessionFactory
argument_list|()
operator|.
name|getClassMetadata
argument_list|(
name|x
operator|.
name|getClass
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|meta
operator|==
literal|null
condition|)
block|{
name|len
operator|++
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|meta
operator|.
name|getIdentifierPropertyName
argument_list|()
operator|!=
literal|null
condition|)
name|len
operator|++
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
name|meta
operator|.
name|getPropertyNames
argument_list|()
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
operator|!
name|skip
argument_list|(
name|meta
operator|.
name|getPropertyTypes
argument_list|()
index|[
name|i
index|]
argument_list|,
name|meta
operator|.
name|getPropertyLaziness
argument_list|()
index|[
name|i
index|]
argument_list|)
condition|)
name|len
operator|++
expr_stmt|;
block|}
block|}
block|}
block|}
block|}
else|else
block|{
name|ClassMetadata
name|meta
init|=
name|SavedHQLDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|getSessionFactory
argument_list|()
operator|.
name|getClassMetadata
argument_list|(
name|o
operator|.
name|getClass
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|meta
operator|==
literal|null
condition|)
block|{
name|len
operator|++
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|meta
operator|.
name|getIdentifierPropertyName
argument_list|()
operator|!=
literal|null
condition|)
name|len
operator|++
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
name|meta
operator|.
name|getPropertyNames
argument_list|()
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
operator|!
name|skip
argument_list|(
name|meta
operator|.
name|getPropertyTypes
argument_list|()
index|[
name|i
index|]
argument_list|,
name|meta
operator|.
name|getPropertyLaziness
argument_list|()
index|[
name|i
index|]
argument_list|)
condition|)
name|len
operator|++
expr_stmt|;
block|}
block|}
block|}
return|return
name|len
return|;
block|}
specifier|private
specifier|static
name|String
name|format
parameter_list|(
name|String
name|column
parameter_list|)
block|{
if|if
condition|(
name|column
operator|==
literal|null
operator|||
name|column
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
literal|"?"
return|;
return|return
name|column
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|1
argument_list|)
operator|.
name|toUpperCase
argument_list|()
operator|+
name|column
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
return|;
block|}
specifier|private
specifier|static
name|void
name|header
parameter_list|(
name|String
index|[]
name|ret
parameter_list|,
name|Object
name|o
parameter_list|,
name|String
index|[]
name|alias
parameter_list|)
block|{
if|if
condition|(
name|o
operator|==
literal|null
condition|)
block|{
if|if
condition|(
name|alias
operator|!=
literal|null
operator|&&
name|alias
operator|.
name|length
operator|>=
literal|1
operator|&&
name|alias
index|[
literal|0
index|]
operator|!=
literal|null
operator|&&
operator|!
name|alias
index|[
literal|0
index|]
operator|.
name|isEmpty
argument_list|()
condition|)
name|ret
index|[
literal|0
index|]
operator|=
name|alias
index|[
literal|0
index|]
expr_stmt|;
else|else
name|ret
index|[
literal|0
index|]
operator|=
literal|"Result"
expr_stmt|;
block|}
if|else if
condition|(
name|o
operator|instanceof
name|Object
index|[]
condition|)
block|{
name|int
name|a
init|=
literal|0
decl_stmt|,
name|idx
init|=
literal|0
decl_stmt|;
for|for
control|(
name|Object
name|x
range|:
operator|(
name|Object
index|[]
operator|)
name|o
control|)
block|{
if|if
condition|(
name|x
operator|==
literal|null
condition|)
block|{
if|if
condition|(
name|alias
operator|!=
literal|null
operator|&&
name|alias
operator|.
name|length
operator|>
name|a
operator|&&
name|alias
index|[
name|a
index|]
operator|!=
literal|null
operator|&&
operator|!
name|alias
index|[
name|a
index|]
operator|.
name|isEmpty
argument_list|()
condition|)
name|ret
index|[
name|idx
operator|++
index|]
operator|=
name|alias
index|[
name|a
index|]
expr_stmt|;
else|else
name|ret
index|[
name|idx
operator|++
index|]
operator|=
literal|"Column"
operator|+
operator|(
name|a
operator|+
literal|1
operator|)
expr_stmt|;
block|}
else|else
block|{
name|ClassMetadata
name|meta
init|=
name|SavedHQLDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|getSessionFactory
argument_list|()
operator|.
name|getClassMetadata
argument_list|(
name|x
operator|.
name|getClass
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|meta
operator|==
literal|null
condition|)
block|{
if|if
condition|(
name|alias
operator|!=
literal|null
operator|&&
name|alias
operator|.
name|length
operator|>
name|a
operator|&&
name|alias
index|[
name|a
index|]
operator|!=
literal|null
operator|&&
operator|!
name|alias
index|[
name|a
index|]
operator|.
name|isEmpty
argument_list|()
condition|)
name|ret
index|[
name|idx
operator|++
index|]
operator|=
name|alias
index|[
name|a
index|]
expr_stmt|;
else|else
name|ret
index|[
name|idx
operator|++
index|]
operator|=
literal|"Column"
operator|+
operator|(
name|a
operator|+
literal|1
operator|)
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|meta
operator|.
name|getIdentifierPropertyName
argument_list|()
operator|!=
literal|null
condition|)
name|ret
index|[
name|idx
operator|++
index|]
operator|=
name|meta
operator|.
name|getIdentifierPropertyName
argument_list|()
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
name|meta
operator|.
name|getPropertyNames
argument_list|()
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
operator|!
name|skip
argument_list|(
name|meta
operator|.
name|getPropertyTypes
argument_list|()
index|[
name|i
index|]
argument_list|,
name|meta
operator|.
name|getPropertyLaziness
argument_list|()
index|[
name|i
index|]
argument_list|)
condition|)
name|ret
index|[
name|idx
operator|++
index|]
operator|=
name|format
argument_list|(
name|meta
operator|.
name|getPropertyNames
argument_list|()
index|[
name|i
index|]
argument_list|)
expr_stmt|;
block|}
block|}
block|}
name|a
operator|++
expr_stmt|;
block|}
block|}
else|else
block|{
name|ClassMetadata
name|meta
init|=
name|SavedHQLDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|getSessionFactory
argument_list|()
operator|.
name|getClassMetadata
argument_list|(
name|o
operator|.
name|getClass
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|meta
operator|==
literal|null
condition|)
block|{
if|if
condition|(
name|alias
operator|!=
literal|null
operator|&&
name|alias
operator|.
name|length
operator|>=
literal|1
operator|&&
name|alias
index|[
literal|0
index|]
operator|!=
literal|null
operator|&&
operator|!
name|alias
index|[
literal|0
index|]
operator|.
name|isEmpty
argument_list|()
condition|)
name|ret
index|[
literal|0
index|]
operator|=
name|alias
index|[
literal|0
index|]
expr_stmt|;
else|else
name|ret
index|[
literal|0
index|]
operator|=
literal|"Result"
expr_stmt|;
block|}
else|else
block|{
name|int
name|idx
init|=
literal|0
decl_stmt|;
if|if
condition|(
name|meta
operator|.
name|getIdentifierPropertyName
argument_list|()
operator|!=
literal|null
condition|)
name|ret
index|[
name|idx
operator|++
index|]
operator|=
name|meta
operator|.
name|getIdentifierPropertyName
argument_list|()
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
name|meta
operator|.
name|getPropertyNames
argument_list|()
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
operator|!
name|skip
argument_list|(
name|meta
operator|.
name|getPropertyTypes
argument_list|()
index|[
name|i
index|]
argument_list|,
name|meta
operator|.
name|getPropertyLaziness
argument_list|()
index|[
name|i
index|]
argument_list|)
condition|)
name|ret
index|[
name|idx
operator|++
index|]
operator|=
name|format
argument_list|(
name|meta
operator|.
name|getPropertyNames
argument_list|()
index|[
name|i
index|]
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
specifier|private
specifier|static
name|String
name|toString
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
if|if
condition|(
name|o
operator|!=
literal|null
operator|&&
name|o
operator|instanceof
name|Document
condition|)
return|return
operator|(
operator|(
name|Document
operator|)
name|o
operator|)
operator|.
name|asXML
argument_list|()
return|;
return|return
operator|(
name|o
operator|==
literal|null
condition|?
literal|""
else|:
name|o
operator|.
name|toString
argument_list|()
operator|)
return|;
block|}
specifier|private
specifier|static
name|void
name|line
parameter_list|(
name|String
index|[]
name|ret
parameter_list|,
name|Object
name|o
parameter_list|,
name|SessionImplementor
name|session
parameter_list|)
block|{
if|if
condition|(
name|o
operator|==
literal|null
condition|)
block|{
name|ret
index|[
literal|0
index|]
operator|=
literal|""
expr_stmt|;
block|}
if|else if
condition|(
name|o
operator|instanceof
name|Object
index|[]
condition|)
block|{
name|int
name|idx
init|=
literal|0
decl_stmt|;
for|for
control|(
name|Object
name|x
range|:
operator|(
name|Object
index|[]
operator|)
name|o
control|)
block|{
if|if
condition|(
name|x
operator|==
literal|null
condition|)
block|{
name|ret
index|[
name|idx
operator|++
index|]
operator|=
literal|""
expr_stmt|;
block|}
else|else
block|{
name|ClassMetadata
name|meta
init|=
name|SavedHQLDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|getSessionFactory
argument_list|()
operator|.
name|getClassMetadata
argument_list|(
name|x
operator|.
name|getClass
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|meta
operator|==
literal|null
condition|)
block|{
name|ret
index|[
name|idx
operator|++
index|]
operator|=
name|toString
argument_list|(
name|x
argument_list|)
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|meta
operator|.
name|getIdentifierPropertyName
argument_list|()
operator|!=
literal|null
condition|)
name|ret
index|[
name|idx
operator|++
index|]
operator|=
name|toString
argument_list|(
name|meta
operator|.
name|getIdentifier
argument_list|(
name|x
argument_list|,
name|session
argument_list|)
argument_list|)
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
name|meta
operator|.
name|getPropertyNames
argument_list|()
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
operator|!
name|skip
argument_list|(
name|meta
operator|.
name|getPropertyTypes
argument_list|()
index|[
name|i
index|]
argument_list|,
name|meta
operator|.
name|getPropertyLaziness
argument_list|()
index|[
name|i
index|]
argument_list|)
condition|)
name|ret
index|[
name|idx
operator|++
index|]
operator|=
name|toString
argument_list|(
name|meta
operator|.
name|getPropertyValue
argument_list|(
name|x
argument_list|,
name|meta
operator|.
name|getPropertyNames
argument_list|()
index|[
name|i
index|]
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
block|}
else|else
block|{
name|ClassMetadata
name|meta
init|=
name|SavedHQLDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|getSessionFactory
argument_list|()
operator|.
name|getClassMetadata
argument_list|(
name|o
operator|.
name|getClass
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|meta
operator|==
literal|null
condition|)
block|{
name|ret
index|[
literal|0
index|]
operator|=
name|toString
argument_list|(
name|o
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|int
name|idx
init|=
literal|0
decl_stmt|;
if|if
condition|(
name|meta
operator|.
name|getIdentifierPropertyName
argument_list|()
operator|!=
literal|null
condition|)
name|ret
index|[
name|idx
operator|++
index|]
operator|=
name|toString
argument_list|(
name|meta
operator|.
name|getIdentifier
argument_list|(
name|o
argument_list|,
name|session
argument_list|)
argument_list|)
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
name|meta
operator|.
name|getPropertyNames
argument_list|()
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
operator|!
name|skip
argument_list|(
name|meta
operator|.
name|getPropertyTypes
argument_list|()
index|[
name|i
index|]
argument_list|,
name|meta
operator|.
name|getPropertyLaziness
argument_list|()
index|[
name|i
index|]
argument_list|)
condition|)
name|ret
index|[
name|idx
operator|++
index|]
operator|=
name|toString
argument_list|(
name|meta
operator|.
name|getPropertyValue
argument_list|(
name|o
argument_list|,
name|meta
operator|.
name|getPropertyNames
argument_list|()
index|[
name|i
index|]
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
block|}
end_class

end_unit

