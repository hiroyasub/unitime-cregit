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
name|form
package|;
end_package

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
name|HashSet
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
name|TreeSet
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
name|HttpServletRequest
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
name|action
operator|.
name|ActionErrors
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
name|action
operator|.
name|ActionForm
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
name|action
operator|.
name|ActionMapping
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
name|action
operator|.
name|ActionMessage
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
name|DatePattern
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
name|dao
operator|.
name|DatePatternDAO
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
name|DepartmentDAO
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

begin_comment
comment|/**   * @author Tomas Muller, Stephanie Schluttenhofer  */
end_comment

begin_class
specifier|public
class|class
name|DatePatternEditForm
extends|extends
name|ActionForm
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
operator|-
literal|929558620061783652L
decl_stmt|;
specifier|private
name|String
name|iOp
decl_stmt|;
specifier|private
name|String
name|iName
decl_stmt|;
specifier|private
name|Long
name|iUniqueId
decl_stmt|;
specifier|private
name|String
name|iType
decl_stmt|;
specifier|private
name|boolean
name|iIsUsed
decl_stmt|;
specifier|private
name|boolean
name|iIsDefault
decl_stmt|;
specifier|private
name|boolean
name|iVisible
decl_stmt|;
specifier|private
name|DatePattern
name|iDp
init|=
literal|null
decl_stmt|;
specifier|private
name|Vector
name|iDepartmentIds
init|=
operator|new
name|Vector
argument_list|()
decl_stmt|;
specifier|private
name|Long
name|iDepartmentId
decl_stmt|;
specifier|private
name|Vector
name|iParentIds
init|=
operator|new
name|Vector
argument_list|()
decl_stmt|;
specifier|private
name|Long
name|iParentId
decl_stmt|;
specifier|private
name|Long
name|iSessionId
decl_stmt|;
specifier|private
name|Integer
name|iNumberOfWeeks
decl_stmt|;
specifier|public
name|ActionErrors
name|validate
parameter_list|(
name|ActionMapping
name|mapping
parameter_list|,
name|HttpServletRequest
name|request
parameter_list|)
block|{
name|ActionErrors
name|errors
init|=
operator|new
name|ActionErrors
argument_list|()
decl_stmt|;
if|if
condition|(
name|iName
operator|==
literal|null
operator|||
name|iName
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"name"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.required"
argument_list|,
literal|""
argument_list|)
argument_list|)
expr_stmt|;
else|else
block|{
try|try
block|{
name|DatePattern
name|pat
init|=
name|DatePattern
operator|.
name|findByName
argument_list|(
name|iSessionId
argument_list|,
name|iName
argument_list|)
decl_stmt|;
if|if
condition|(
name|pat
operator|!=
literal|null
operator|&&
operator|!
name|pat
operator|.
name|getUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
name|iUniqueId
argument_list|)
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"name"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.exists"
argument_list|,
name|iName
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
name|errors
operator|.
name|add
argument_list|(
literal|"name"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|getTypeInt
argument_list|()
operator|<
literal|0
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"type"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.required"
argument_list|,
literal|""
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|getTypeInt
argument_list|()
operator|!=
name|DatePattern
operator|.
name|sTypeExtended
operator|&&
operator|!
name|iDepartmentIds
operator|.
name|isEmpty
argument_list|()
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"type"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
literal|"Only extended pattern can contain relations with departments."
argument_list|)
argument_list|)
expr_stmt|;
try|try
block|{
name|DatePattern
name|dp
init|=
name|getDatePattern
argument_list|(
name|request
argument_list|)
decl_stmt|;
if|if
condition|(
name|getTypeInt
argument_list|()
operator|==
name|DatePattern
operator|.
name|sTypePatternSet
condition|)
block|{
if|if
condition|(
name|dp
operator|.
name|size
argument_list|()
operator|!=
literal|0
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"type"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
literal|"Alternative pattern set date pattern can not have any dates selected."
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|getParentIds
argument_list|()
operator|!=
literal|null
operator|&&
operator|!
name|getParentIds
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"type"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
literal|"Alternative pattern set date pattern can not have a pattern set."
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
if|if
condition|(
name|dp
operator|.
name|size
argument_list|()
operator|==
literal|0
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"pattern"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.required"
argument_list|,
literal|""
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|dp
operator|.
name|getPattern
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|366
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"pattern"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
literal|"Date Patterns cannot contain more than 1 year."
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
block|}
return|return
name|errors
return|;
block|}
specifier|public
name|void
name|reset
parameter_list|(
name|ActionMapping
name|mapping
parameter_list|,
name|HttpServletRequest
name|request
parameter_list|)
block|{
name|iOp
operator|=
literal|null
expr_stmt|;
name|iUniqueId
operator|=
operator|new
name|Long
argument_list|(
operator|-
literal|1
argument_list|)
expr_stmt|;
name|iType
operator|=
name|DatePattern
operator|.
name|sTypes
index|[
literal|0
index|]
expr_stmt|;
name|iIsUsed
operator|=
literal|false
expr_stmt|;
name|iVisible
operator|=
literal|false
expr_stmt|;
name|iName
operator|=
literal|""
expr_stmt|;
name|iIsDefault
operator|=
literal|false
expr_stmt|;
name|iNumberOfWeeks
operator|=
literal|null
expr_stmt|;
name|iDp
operator|=
literal|null
expr_stmt|;
name|iDepartmentId
operator|=
literal|null
expr_stmt|;
name|iDepartmentIds
operator|.
name|clear
argument_list|()
expr_stmt|;
name|iParentId
operator|=
literal|null
expr_stmt|;
name|iParentIds
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
specifier|public
name|void
name|load
parameter_list|(
name|DatePattern
name|dp
parameter_list|)
block|{
if|if
condition|(
name|dp
operator|==
literal|null
condition|)
block|{
name|reset
argument_list|(
literal|null
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|iOp
operator|=
literal|"Save"
expr_stmt|;
name|iVisible
operator|=
literal|true
expr_stmt|;
name|iIsUsed
operator|=
literal|false
expr_stmt|;
name|iIsDefault
operator|=
literal|false
expr_stmt|;
block|}
else|else
block|{
name|setName
argument_list|(
name|dp
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|setVisible
argument_list|(
name|dp
operator|.
name|isVisible
argument_list|()
operator|.
name|booleanValue
argument_list|()
argument_list|)
expr_stmt|;
name|setIsUsed
argument_list|(
name|dp
operator|.
name|isUsed
argument_list|()
argument_list|)
expr_stmt|;
name|setTypeInt
argument_list|(
name|dp
operator|.
name|getType
argument_list|()
operator|.
name|intValue
argument_list|()
argument_list|)
expr_stmt|;
name|setUniqueId
argument_list|(
name|dp
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|setIsDefault
argument_list|(
name|dp
operator|.
name|isDefault
argument_list|()
argument_list|)
expr_stmt|;
name|setSessionId
argument_list|(
name|dp
operator|.
name|getSession
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|setNumberOfWeeks
argument_list|(
name|dp
operator|.
name|getNumberOfWeeks
argument_list|()
argument_list|)
expr_stmt|;
name|iParentIds
operator|.
name|clear
argument_list|()
expr_stmt|;
name|TreeSet
name|parents
init|=
operator|new
name|TreeSet
argument_list|(
name|dp
operator|.
name|getParents
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|parents
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
name|DatePattern
name|d
init|=
operator|(
name|DatePattern
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|iParentIds
operator|.
name|add
argument_list|(
name|d
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|iDepartmentIds
operator|.
name|clear
argument_list|()
expr_stmt|;
name|TreeSet
name|depts
init|=
operator|new
name|TreeSet
argument_list|(
name|dp
operator|.
name|getDepartments
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|depts
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
name|Department
name|d
init|=
operator|(
name|Department
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|iDepartmentIds
operator|.
name|add
argument_list|(
name|d
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|iOp
operator|=
literal|"Update"
expr_stmt|;
block|}
block|}
specifier|public
name|void
name|update
parameter_list|(
name|DatePattern
name|dp
parameter_list|,
name|HttpServletRequest
name|request
parameter_list|,
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|)
throws|throws
name|Exception
block|{
name|dp
operator|.
name|setName
argument_list|(
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|dp
operator|.
name|setVisible
argument_list|(
operator|new
name|Boolean
argument_list|(
name|getVisible
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|dp
operator|.
name|setType
argument_list|(
operator|new
name|Integer
argument_list|(
name|getTypeInt
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|dp
operator|.
name|setPatternAndOffset
argument_list|(
name|request
argument_list|)
expr_stmt|;
name|dp
operator|.
name|setNumberOfWeeks
argument_list|(
name|getNumberOfWeeks
argument_list|()
operator|==
literal|null
operator|||
name|getNumberOfWeeks
argument_list|()
operator|==
literal|0
condition|?
literal|null
else|:
name|getNumberOfWeeks
argument_list|()
argument_list|)
expr_stmt|;
name|HashSet
name|oldParents
init|=
operator|new
name|HashSet
argument_list|(
name|dp
operator|.
name|getParents
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|Enumeration
name|e
init|=
name|iParentIds
operator|.
name|elements
argument_list|()
init|;
name|e
operator|.
name|hasMoreElements
argument_list|()
condition|;
control|)
block|{
name|Long
name|parentId
init|=
operator|(
name|Long
operator|)
name|e
operator|.
name|nextElement
argument_list|()
decl_stmt|;
name|DatePattern
name|d
init|=
operator|(
operator|new
name|DatePatternDAO
argument_list|()
operator|)
operator|.
name|get
argument_list|(
name|parentId
argument_list|,
name|hibSession
argument_list|)
decl_stmt|;
if|if
condition|(
name|d
operator|==
literal|null
condition|)
continue|continue;
if|if
condition|(
name|oldParents
operator|.
name|remove
argument_list|(
name|d
argument_list|)
condition|)
block|{
comment|//not changed -> do nothing
block|}
else|else
block|{
name|dp
operator|.
name|getParents
argument_list|()
operator|.
name|add
argument_list|(
name|d
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|dp
argument_list|)
expr_stmt|;
block|}
block|}
for|for
control|(
name|Iterator
name|i
init|=
name|oldParents
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
name|DatePattern
name|d
init|=
operator|(
name|DatePattern
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|dp
operator|.
name|getParents
argument_list|()
operator|.
name|remove
argument_list|(
name|d
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|d
argument_list|)
expr_stmt|;
block|}
name|HashSet
name|oldDepts
init|=
operator|new
name|HashSet
argument_list|(
name|dp
operator|.
name|getDepartments
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|Enumeration
name|e
init|=
name|iDepartmentIds
operator|.
name|elements
argument_list|()
init|;
name|e
operator|.
name|hasMoreElements
argument_list|()
condition|;
control|)
block|{
name|Long
name|departmentId
init|=
operator|(
name|Long
operator|)
name|e
operator|.
name|nextElement
argument_list|()
decl_stmt|;
name|Department
name|d
init|=
operator|(
operator|new
name|DepartmentDAO
argument_list|()
operator|)
operator|.
name|get
argument_list|(
name|departmentId
argument_list|,
name|hibSession
argument_list|)
decl_stmt|;
if|if
condition|(
name|d
operator|==
literal|null
condition|)
continue|continue;
if|if
condition|(
name|oldDepts
operator|.
name|remove
argument_list|(
name|d
argument_list|)
condition|)
block|{
comment|//not changed -> do nothing
block|}
else|else
block|{
name|dp
operator|.
name|getDepartments
argument_list|()
operator|.
name|add
argument_list|(
name|d
argument_list|)
expr_stmt|;
name|d
operator|.
name|getDatePatterns
argument_list|()
operator|.
name|add
argument_list|(
name|dp
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|d
argument_list|)
expr_stmt|;
block|}
block|}
for|for
control|(
name|Iterator
name|i
init|=
name|oldDepts
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
name|Department
name|d
init|=
operator|(
name|Department
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|dp
operator|.
name|getDepartments
argument_list|()
operator|.
name|remove
argument_list|(
name|d
argument_list|)
expr_stmt|;
name|d
operator|.
name|getDatePatterns
argument_list|()
operator|.
name|remove
argument_list|(
name|dp
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|d
argument_list|)
expr_stmt|;
block|}
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|dp
argument_list|)
expr_stmt|;
block|}
specifier|public
name|DatePattern
name|create
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|)
throws|throws
name|Exception
block|{
name|DatePattern
name|dp
init|=
operator|new
name|DatePattern
argument_list|()
decl_stmt|;
name|dp
operator|.
name|setName
argument_list|(
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|dp
operator|.
name|setSession
argument_list|(
name|SessionDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|getSessionId
argument_list|()
argument_list|,
name|hibSession
argument_list|)
argument_list|)
expr_stmt|;
name|dp
operator|.
name|setVisible
argument_list|(
operator|new
name|Boolean
argument_list|(
name|getVisible
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|dp
operator|.
name|setType
argument_list|(
operator|new
name|Integer
argument_list|(
name|getTypeInt
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|dp
operator|.
name|setPatternAndOffset
argument_list|(
name|request
argument_list|)
expr_stmt|;
name|dp
operator|.
name|setNumberOfWeeks
argument_list|(
name|getNumberOfWeeks
argument_list|()
operator|==
literal|null
operator|||
name|getNumberOfWeeks
argument_list|()
operator|==
literal|0
condition|?
literal|null
else|:
name|getNumberOfWeeks
argument_list|()
argument_list|)
expr_stmt|;
name|HashSet
name|newParents
init|=
operator|new
name|HashSet
argument_list|()
decl_stmt|;
for|for
control|(
name|Enumeration
name|e
init|=
name|iParentIds
operator|.
name|elements
argument_list|()
init|;
name|e
operator|.
name|hasMoreElements
argument_list|()
condition|;
control|)
block|{
name|Long
name|parentId
init|=
operator|(
name|Long
operator|)
name|e
operator|.
name|nextElement
argument_list|()
decl_stmt|;
name|DatePattern
name|d
init|=
operator|(
operator|new
name|DatePatternDAO
argument_list|()
operator|)
operator|.
name|get
argument_list|(
name|parentId
argument_list|,
name|hibSession
argument_list|)
decl_stmt|;
if|if
condition|(
name|d
operator|==
literal|null
condition|)
continue|continue;
name|newParents
operator|.
name|add
argument_list|(
name|d
argument_list|)
expr_stmt|;
block|}
name|dp
operator|.
name|setParents
argument_list|(
name|newParents
argument_list|)
expr_stmt|;
name|HashSet
name|newDepts
init|=
operator|new
name|HashSet
argument_list|()
decl_stmt|;
for|for
control|(
name|Enumeration
name|e
init|=
name|iDepartmentIds
operator|.
name|elements
argument_list|()
init|;
name|e
operator|.
name|hasMoreElements
argument_list|()
condition|;
control|)
block|{
name|Long
name|departmentId
init|=
operator|(
name|Long
operator|)
name|e
operator|.
name|nextElement
argument_list|()
decl_stmt|;
name|Department
name|d
init|=
operator|(
operator|new
name|DepartmentDAO
argument_list|()
operator|)
operator|.
name|get
argument_list|(
name|departmentId
argument_list|,
name|hibSession
argument_list|)
decl_stmt|;
if|if
condition|(
name|d
operator|==
literal|null
condition|)
continue|continue;
name|newDepts
operator|.
name|add
argument_list|(
name|d
argument_list|)
expr_stmt|;
block|}
name|dp
operator|.
name|setDepartments
argument_list|(
name|newDepts
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|save
argument_list|(
name|dp
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|newDepts
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
name|Department
name|d
init|=
operator|(
name|Department
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|d
operator|.
name|getDatePatterns
argument_list|()
operator|.
name|add
argument_list|(
name|dp
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|d
argument_list|)
expr_stmt|;
block|}
name|setUniqueId
argument_list|(
name|dp
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|dp
return|;
block|}
specifier|public
name|DatePattern
name|saveOrUpdate
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|)
throws|throws
name|Exception
block|{
name|DatePattern
name|dp
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|getUniqueId
argument_list|()
operator|.
name|longValue
argument_list|()
operator|>=
literal|0
condition|)
name|dp
operator|=
operator|(
operator|new
name|DatePatternDAO
argument_list|()
operator|)
operator|.
name|get
argument_list|(
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|dp
operator|==
literal|null
condition|)
name|dp
operator|=
name|create
argument_list|(
name|request
argument_list|,
name|hibSession
argument_list|)
expr_stmt|;
else|else
name|update
argument_list|(
name|dp
argument_list|,
name|request
argument_list|,
name|hibSession
argument_list|)
expr_stmt|;
return|return
name|dp
return|;
block|}
specifier|public
name|void
name|delete
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|)
throws|throws
name|Exception
block|{
if|if
condition|(
name|getUniqueId
argument_list|()
operator|.
name|longValue
argument_list|()
operator|<
literal|0
condition|)
return|return;
if|if
condition|(
name|getIsUsed
argument_list|()
condition|)
return|return;
name|DatePattern
name|dp
init|=
operator|(
operator|new
name|DatePatternDAO
argument_list|()
operator|)
operator|.
name|get
argument_list|(
name|getUniqueId
argument_list|()
argument_list|,
name|hibSession
argument_list|)
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|dp
operator|.
name|getDepartments
argument_list|()
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
name|Department
name|d
init|=
operator|(
name|Department
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|d
operator|.
name|getDatePatterns
argument_list|()
operator|.
name|remove
argument_list|(
name|dp
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|d
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|Iterator
name|i
init|=
name|dp
operator|.
name|findChildren
argument_list|()
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
name|DatePattern
name|d
init|=
operator|(
name|DatePattern
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|d
operator|.
name|getParents
argument_list|()
operator|.
name|remove
argument_list|(
name|dp
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|d
argument_list|)
expr_stmt|;
block|}
name|dp
operator|.
name|getParents
argument_list|()
operator|.
name|clear
argument_list|()
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|dp
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|delete
argument_list|(
name|dp
argument_list|)
expr_stmt|;
block|}
specifier|public
name|String
name|getOp
parameter_list|()
block|{
return|return
name|iOp
return|;
block|}
specifier|public
name|void
name|setOp
parameter_list|(
name|String
name|op
parameter_list|)
block|{
name|iOp
operator|=
name|op
expr_stmt|;
block|}
specifier|public
name|Long
name|getUniqueId
parameter_list|()
block|{
return|return
name|iUniqueId
return|;
block|}
specifier|public
name|void
name|setUniqueId
parameter_list|(
name|Long
name|uniqueId
parameter_list|)
block|{
name|iUniqueId
operator|=
name|uniqueId
expr_stmt|;
block|}
specifier|public
name|String
name|getType
parameter_list|()
block|{
return|return
name|iType
return|;
block|}
specifier|public
name|void
name|setType
parameter_list|(
name|String
name|type
parameter_list|)
block|{
name|iType
operator|=
name|type
expr_stmt|;
block|}
specifier|public
name|String
index|[]
name|getTypes
parameter_list|()
block|{
return|return
name|DatePattern
operator|.
name|sTypes
return|;
block|}
specifier|public
name|int
name|getTypeInt
parameter_list|()
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
name|DatePattern
operator|.
name|sTypes
operator|.
name|length
condition|;
name|i
operator|++
control|)
if|if
condition|(
name|DatePattern
operator|.
name|sTypes
index|[
name|i
index|]
operator|.
name|equals
argument_list|(
name|iType
argument_list|)
condition|)
return|return
name|i
return|;
return|return
operator|-
literal|1
return|;
block|}
specifier|public
name|void
name|setTypeInt
parameter_list|(
name|int
name|type
parameter_list|)
block|{
name|iType
operator|=
operator|(
name|type
operator|<
literal|0
condition|?
literal|""
else|:
name|DatePattern
operator|.
name|sTypes
index|[
name|type
index|]
operator|)
expr_stmt|;
block|}
specifier|public
name|boolean
name|getIsUsed
parameter_list|()
block|{
return|return
name|iIsUsed
return|;
block|}
specifier|public
name|void
name|setIsUsed
parameter_list|(
name|boolean
name|isUsed
parameter_list|)
block|{
name|iIsUsed
operator|=
name|isUsed
expr_stmt|;
block|}
specifier|public
name|boolean
name|getVisible
parameter_list|()
block|{
return|return
name|iVisible
return|;
block|}
specifier|public
name|void
name|setVisible
parameter_list|(
name|boolean
name|visible
parameter_list|)
block|{
name|iVisible
operator|=
name|visible
expr_stmt|;
block|}
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|iName
return|;
block|}
specifier|public
name|void
name|setName
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|iName
operator|=
name|name
expr_stmt|;
block|}
specifier|public
name|boolean
name|getIsDefault
parameter_list|()
block|{
return|return
name|iIsDefault
return|;
block|}
specifier|public
name|void
name|setIsDefault
parameter_list|(
name|boolean
name|isDefault
parameter_list|)
block|{
name|iIsDefault
operator|=
name|isDefault
expr_stmt|;
block|}
specifier|public
name|Vector
name|getDepartmentIds
parameter_list|()
block|{
return|return
name|iDepartmentIds
return|;
block|}
specifier|public
name|void
name|setDepartmentIds
parameter_list|(
name|Vector
name|departmentIds
parameter_list|)
block|{
name|iDepartmentIds
operator|=
name|departmentIds
expr_stmt|;
block|}
specifier|public
name|Long
name|getDepartmentId
parameter_list|()
block|{
return|return
name|iDepartmentId
return|;
block|}
specifier|public
name|void
name|setDepartmentId
parameter_list|(
name|Long
name|deptId
parameter_list|)
block|{
name|iDepartmentId
operator|=
name|deptId
expr_stmt|;
block|}
specifier|public
name|Long
name|getParentId
parameter_list|()
block|{
return|return
name|iParentId
return|;
block|}
specifier|public
name|void
name|setParentId
parameter_list|(
name|Long
name|parentId
parameter_list|)
block|{
name|iParentId
operator|=
name|parentId
expr_stmt|;
block|}
specifier|public
name|Vector
name|getParentIds
parameter_list|()
block|{
return|return
name|iParentIds
return|;
block|}
specifier|public
name|void
name|setParentIds
parameter_list|(
name|Vector
name|parentIds
parameter_list|)
block|{
name|iParentIds
operator|=
name|parentIds
expr_stmt|;
block|}
specifier|public
name|Integer
name|getNumberOfWeeks
parameter_list|()
block|{
return|return
name|iNumberOfWeeks
return|;
block|}
specifier|public
name|void
name|setNumberOfWeeks
parameter_list|(
name|Integer
name|numberOfWeeks
parameter_list|)
block|{
name|iNumberOfWeeks
operator|=
name|numberOfWeeks
expr_stmt|;
block|}
specifier|public
name|Long
name|getSessionId
parameter_list|()
block|{
return|return
name|iSessionId
return|;
block|}
specifier|public
name|void
name|setSessionId
parameter_list|(
name|Long
name|sessionId
parameter_list|)
block|{
name|iSessionId
operator|=
name|sessionId
expr_stmt|;
block|}
specifier|public
name|DatePattern
name|getDatePattern
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|)
throws|throws
name|Exception
block|{
if|if
condition|(
name|getUniqueId
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|iDp
operator|=
operator|(
operator|new
name|DatePatternDAO
argument_list|()
operator|)
operator|.
name|get
argument_list|(
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|iDp
operator|!=
literal|null
condition|)
name|iDp
operator|=
operator|(
name|DatePattern
operator|)
name|iDp
operator|.
name|clone
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|iDp
operator|==
literal|null
condition|)
block|{
name|iDp
operator|=
operator|new
name|DatePattern
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|iDp
operator|.
name|getSession
argument_list|()
operator|==
literal|null
condition|)
block|{
name|iDp
operator|.
name|setSession
argument_list|(
name|SessionDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|getSessionId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"cal_select"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|iDp
operator|.
name|setName
argument_list|(
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|iDp
operator|.
name|setVisible
argument_list|(
operator|new
name|Boolean
argument_list|(
name|getVisible
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|iDp
operator|.
name|setType
argument_list|(
operator|new
name|Integer
argument_list|(
name|getTypeInt
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|iDp
operator|.
name|setPatternAndOffset
argument_list|(
name|request
argument_list|)
expr_stmt|;
name|iDp
operator|.
name|setNumberOfWeeks
argument_list|(
name|getNumberOfWeeks
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|iDp
return|;
block|}
block|}
end_class

end_unit

