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
name|SavedHQL
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|HQLImport
extends|extends
name|BaseImport
block|{
annotation|@
name|Override
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
try|try
block|{
name|beginTransaction
argument_list|()
expr_stmt|;
if|if
condition|(
name|root
operator|.
name|getName
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"report"
argument_list|)
condition|)
block|{
name|importReport
argument_list|(
name|root
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|root
operator|.
name|getName
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"reports"
argument_list|)
condition|)
block|{
for|for
control|(
name|Iterator
name|i
init|=
name|root
operator|.
name|elementIterator
argument_list|(
literal|"report"
argument_list|)
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
name|importReport
argument_list|(
operator|(
name|Element
operator|)
name|i
operator|.
name|next
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Given XML file is not a HQL report file."
argument_list|)
throw|;
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
specifier|protected
name|void
name|importReport
parameter_list|(
name|Element
name|reportEl
parameter_list|)
block|{
name|String
name|name
init|=
name|reportEl
operator|.
name|attributeValue
argument_list|(
literal|"name"
argument_list|)
decl_stmt|;
if|if
condition|(
name|name
operator|==
literal|null
condition|)
block|{
name|error
argument_list|(
literal|"Attribute name is not provided."
argument_list|)
expr_stmt|;
return|return;
block|}
name|SavedHQL
name|report
init|=
operator|(
name|SavedHQL
operator|)
name|getHibSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"from SavedHQL where name = :name"
argument_list|)
operator|.
name|setString
argument_list|(
literal|"name"
argument_list|,
name|name
argument_list|)
operator|.
name|setMaxResults
argument_list|(
literal|1
argument_list|)
operator|.
name|uniqueResult
argument_list|()
decl_stmt|;
if|if
condition|(
name|report
operator|==
literal|null
condition|)
block|{
name|report
operator|=
operator|new
name|SavedHQL
argument_list|()
expr_stmt|;
name|report
operator|.
name|setName
argument_list|(
name|name
argument_list|)
expr_stmt|;
block|}
name|report
operator|.
name|setType
argument_list|(
literal|0
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|reportEl
operator|.
name|elementIterator
argument_list|(
literal|"flag"
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
name|e
init|=
operator|(
name|Element
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|SavedHQL
operator|.
name|Flag
name|flag
init|=
name|SavedHQL
operator|.
name|Flag
operator|.
name|valueOf
argument_list|(
name|e
operator|.
name|getTextTrim
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|flag
operator|!=
literal|null
condition|)
name|report
operator|.
name|set
argument_list|(
name|flag
argument_list|)
expr_stmt|;
block|}
name|Element
name|queryEl
init|=
name|reportEl
operator|.
name|element
argument_list|(
literal|"query"
argument_list|)
decl_stmt|;
if|if
condition|(
name|queryEl
operator|!=
literal|null
condition|)
name|report
operator|.
name|setQuery
argument_list|(
name|queryEl
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
else|else
name|report
operator|.
name|setQuery
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|Element
name|descriptionEl
init|=
name|reportEl
operator|.
name|element
argument_list|(
literal|"description"
argument_list|)
decl_stmt|;
if|if
condition|(
name|descriptionEl
operator|!=
literal|null
condition|)
name|report
operator|.
name|setDescription
argument_list|(
name|descriptionEl
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
else|else
name|report
operator|.
name|setDescription
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|getHibSession
argument_list|()
operator|.
name|saveOrUpdate
argument_list|(
name|report
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

