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
name|dataexchange
package|;
end_package

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
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|ChangeLog
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
name|Department
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
name|Session
import|;
end_import

begin_comment
comment|/**  *   * @author Timothy Almon  *  */
end_comment

begin_class
specifier|public
class|class
name|DepartmentImport
extends|extends
name|BaseImport
block|{
specifier|public
name|void
name|loadXml
parameter_list|(
name|Element
name|root
parameter_list|)
throws|throws
name|Exception
block|{
if|if
condition|(
operator|!
name|root
operator|.
name|getName
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"departments"
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Given XML file is not an Department load file."
argument_list|)
throw|;
block|}
try|try
block|{
name|beginTransaction
argument_list|()
expr_stmt|;
name|String
name|campus
init|=
name|root
operator|.
name|attributeValue
argument_list|(
literal|"campus"
argument_list|)
decl_stmt|;
name|String
name|year
init|=
name|root
operator|.
name|attributeValue
argument_list|(
literal|"year"
argument_list|)
decl_stmt|;
name|String
name|term
init|=
name|root
operator|.
name|attributeValue
argument_list|(
literal|"term"
argument_list|)
decl_stmt|;
name|String
name|created
init|=
name|root
operator|.
name|attributeValue
argument_list|(
literal|"created"
argument_list|)
decl_stmt|;
name|Session
name|session
init|=
name|Session
operator|.
name|getSessionUsingInitiativeYearTerm
argument_list|(
name|campus
argument_list|,
name|year
argument_list|,
name|term
argument_list|)
decl_stmt|;
if|if
condition|(
name|session
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|Exception
argument_list|(
literal|"No session found for the given campus, year, and term."
argument_list|)
throw|;
block|}
if|if
condition|(
name|created
operator|!=
literal|null
condition|)
block|{
name|ChangeLog
operator|.
name|addChange
argument_list|(
name|getHibSession
argument_list|()
argument_list|,
name|getManager
argument_list|()
argument_list|,
name|session
argument_list|,
name|session
argument_list|,
name|created
argument_list|,
name|ChangeLog
operator|.
name|Source
operator|.
name|DATA_IMPORT_DEPARTMENTS
argument_list|,
name|ChangeLog
operator|.
name|Operation
operator|.
name|UPDATE
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|Iterator
name|it
init|=
name|root
operator|.
name|elementIterator
argument_list|()
init|;
name|it
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Element
name|element
init|=
operator|(
name|Element
operator|)
name|it
operator|.
name|next
argument_list|()
decl_stmt|;
name|String
name|externalId
init|=
name|element
operator|.
name|attributeValue
argument_list|(
literal|"externalId"
argument_list|)
decl_stmt|;
name|Department
name|department
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|externalId
operator|!=
literal|null
operator|&&
name|externalId
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|department
operator|=
name|findByExternalId
argument_list|(
name|externalId
argument_list|,
name|session
operator|.
name|getSessionId
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|department
operator|=
name|Department
operator|.
name|findByDeptCode
argument_list|(
name|element
operator|.
name|attributeValue
argument_list|(
literal|"deptCode"
argument_list|)
argument_list|,
name|session
operator|.
name|getSessionId
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|department
operator|==
literal|null
condition|)
block|{
name|department
operator|=
operator|new
name|Department
argument_list|()
expr_stmt|;
name|department
operator|.
name|setSession
argument_list|(
name|session
argument_list|)
expr_stmt|;
name|department
operator|.
name|setAllowReqTime
argument_list|(
operator|new
name|Boolean
argument_list|(
literal|false
argument_list|)
argument_list|)
expr_stmt|;
name|department
operator|.
name|setAllowReqRoom
argument_list|(
operator|new
name|Boolean
argument_list|(
literal|false
argument_list|)
argument_list|)
expr_stmt|;
name|department
operator|.
name|setExternalManager
argument_list|(
operator|new
name|Boolean
argument_list|(
literal|false
argument_list|)
argument_list|)
expr_stmt|;
name|department
operator|.
name|setDistributionPrefPriority
argument_list|(
operator|new
name|Integer
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
literal|"T"
operator|.
name|equalsIgnoreCase
argument_list|(
name|element
operator|.
name|attributeValue
argument_list|(
literal|"delete"
argument_list|)
argument_list|)
condition|)
block|{
name|getHibSession
argument_list|()
operator|.
name|delete
argument_list|(
name|department
argument_list|)
expr_stmt|;
continue|continue;
block|}
block|}
name|department
operator|.
name|setAbbreviation
argument_list|(
name|element
operator|.
name|attributeValue
argument_list|(
literal|"abbreviation"
argument_list|)
argument_list|)
expr_stmt|;
name|department
operator|.
name|setName
argument_list|(
name|element
operator|.
name|attributeValue
argument_list|(
literal|"name"
argument_list|)
argument_list|)
expr_stmt|;
name|department
operator|.
name|setDeptCode
argument_list|(
name|element
operator|.
name|attributeValue
argument_list|(
literal|"deptCode"
argument_list|)
argument_list|)
expr_stmt|;
name|department
operator|.
name|setExternalUniqueId
argument_list|(
name|externalId
argument_list|)
expr_stmt|;
name|getHibSession
argument_list|()
operator|.
name|saveOrUpdate
argument_list|(
name|department
argument_list|)
expr_stmt|;
name|flushIfNeeded
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
name|commitTransaction
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|fatal
argument_list|(
literal|"Exception: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
name|rollbackTransaction
argument_list|()
expr_stmt|;
throw|throw
name|e
throw|;
block|}
block|}
specifier|private
name|Department
name|findByExternalId
parameter_list|(
name|String
name|externalId
parameter_list|,
name|Long
name|sessionId
parameter_list|)
block|{
return|return
operator|(
name|Department
operator|)
name|this
operator|.
name|getHibSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select distinct a from Department as a where a.externalUniqueId=:externalId and a.session.uniqueId=:sessionId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
operator|.
name|longValue
argument_list|()
argument_list|)
operator|.
name|setString
argument_list|(
literal|"externalId"
argument_list|,
name|externalId
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|uniqueResult
argument_list|()
return|;
block|}
block|}
end_class

end_unit

