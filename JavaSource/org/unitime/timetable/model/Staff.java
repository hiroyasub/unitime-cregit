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
name|model
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collections
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
name|org
operator|.
name|hibernate
operator|.
name|Query
import|;
end_import

begin_import
import|import
name|org
operator|.
name|hibernate
operator|.
name|criterion
operator|.
name|Conjunction
import|;
end_import

begin_import
import|import
name|org
operator|.
name|hibernate
operator|.
name|criterion
operator|.
name|MatchMode
import|;
end_import

begin_import
import|import
name|org
operator|.
name|hibernate
operator|.
name|criterion
operator|.
name|Restrictions
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
name|base
operator|.
name|BaseStaff
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
name|StaffDAO
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
name|NameFormat
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
name|NameInterface
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|Staff
extends|extends
name|BaseStaff
implements|implements
name|Comparable
implements|,
name|NameInterface
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
comment|/*[CONSTRUCTOR MARKER BEGIN]*/
specifier|public
name|Staff
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|Staff
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Long
name|uniqueId
parameter_list|)
block|{
name|super
argument_list|(
name|uniqueId
argument_list|)
expr_stmt|;
block|}
comment|/*[CONSTRUCTOR MARKER END]*/
comment|/** 	 *  	 * @param deptCode 	 * @return 	 */
specifier|public
specifier|static
name|List
name|getStaffByDept
parameter_list|(
name|String
name|deptCode
parameter_list|,
name|Long
name|acadSessionId
parameter_list|)
throws|throws
name|Exception
block|{
if|if
condition|(
name|deptCode
operator|==
literal|null
condition|)
block|{
return|return
operator|(
literal|null
operator|)
return|;
block|}
name|StaffDAO
name|sdao
init|=
operator|new
name|StaffDAO
argument_list|()
decl_stmt|;
name|String
name|sql
init|=
literal|"select distinct s "
operator|+
literal|"from Staff s "
operator|+
literal|"where s.dept='"
operator|+
name|deptCode
operator|+
literal|"'"
operator|+
literal|"  and ( "
operator|+
literal|"select di.externalUniqueId "
operator|+
literal|"from DepartmentalInstructor di "
operator|+
literal|"where di.department.deptCode='"
operator|+
name|deptCode
operator|+
literal|"' "
operator|+
literal|"  and di.department.session.uniqueId="
operator|+
name|acadSessionId
operator|.
name|toString
argument_list|()
operator|+
literal|"  and di.externalUniqueId = s.externalUniqueId ) is null"
decl_stmt|;
name|Query
name|q
init|=
name|sdao
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
name|sql
argument_list|)
decl_stmt|;
name|q
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
expr_stmt|;
return|return
operator|(
name|q
operator|.
name|list
argument_list|()
operator|)
return|;
block|}
comment|/** 	 * Search staff list for instructors with matching names 	 * @param fname First Name  	 * @param lname Last Name 	 * @return 	 */
specifier|public
specifier|static
name|List
name|findMatchingName
parameter_list|(
name|String
name|fname
parameter_list|,
name|String
name|lname
parameter_list|)
block|{
name|List
name|list
init|=
literal|null
decl_stmt|;
if|if
condition|(
operator|(
name|fname
operator|==
literal|null
operator|||
name|fname
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
operator|)
operator|&&
operator|(
name|lname
operator|==
literal|null
operator|||
name|lname
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
operator|)
condition|)
return|return
name|list
return|;
name|Conjunction
name|and
init|=
name|Restrictions
operator|.
name|conjunction
argument_list|()
decl_stmt|;
if|if
condition|(
name|fname
operator|!=
literal|null
operator|&&
name|fname
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|and
operator|.
name|add
argument_list|(
name|Restrictions
operator|.
name|ilike
argument_list|(
literal|"firstName"
argument_list|,
name|fname
argument_list|,
name|MatchMode
operator|.
name|START
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|lname
operator|!=
literal|null
operator|&&
name|lname
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|and
operator|.
name|add
argument_list|(
name|Restrictions
operator|.
name|ilike
argument_list|(
literal|"lastName"
argument_list|,
name|lname
argument_list|,
name|MatchMode
operator|.
name|START
argument_list|)
argument_list|)
expr_stmt|;
name|StaffDAO
name|sdao
init|=
operator|new
name|StaffDAO
argument_list|()
decl_stmt|;
name|list
operator|=
name|sdao
operator|.
name|getSession
argument_list|()
operator|.
name|createCriteria
argument_list|(
name|Staff
operator|.
name|class
argument_list|)
operator|.
name|add
argument_list|(
name|and
argument_list|)
operator|.
name|list
argument_list|()
expr_stmt|;
name|Collections
operator|.
name|sort
argument_list|(
name|list
argument_list|)
expr_stmt|;
return|return
name|list
return|;
block|}
comment|/** 	 *  	 * @param o 	 * @return 	 */
specifier|public
name|int
name|compareTo
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
operator|||
operator|!
operator|(
name|o
operator|instanceof
name|Staff
operator|)
condition|)
return|return
operator|-
literal|1
return|;
name|Staff
name|i
init|=
operator|(
name|Staff
operator|)
name|o
decl_stmt|;
if|if
condition|(
name|getPositionType
argument_list|()
operator|==
literal|null
condition|)
block|{
if|if
condition|(
name|i
operator|.
name|getPositionType
argument_list|()
operator|!=
literal|null
condition|)
return|return
literal|1
return|;
block|}
else|else
block|{
if|if
condition|(
name|i
operator|.
name|getPositionType
argument_list|()
operator|==
literal|null
condition|)
return|return
operator|-
literal|1
return|;
name|int
name|cmp
init|=
name|getPositionType
argument_list|()
operator|.
name|getSortOrder
argument_list|()
operator|.
name|compareTo
argument_list|(
name|i
operator|.
name|getPositionType
argument_list|()
operator|.
name|getSortOrder
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|cmp
operator|!=
literal|0
condition|)
return|return
name|cmp
return|;
block|}
name|int
name|cmp
init|=
name|nameLastNameFirst
argument_list|()
operator|.
name|compareToIgnoreCase
argument_list|(
name|i
operator|.
name|nameLastNameFirst
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|cmp
operator|!=
literal|0
condition|)
return|return
name|cmp
return|;
return|return
operator|(
name|getUniqueId
argument_list|()
operator|==
literal|null
condition|?
operator|new
name|Long
argument_list|(
operator|-
literal|1
argument_list|)
else|:
name|getUniqueId
argument_list|()
operator|)
operator|.
name|compareTo
argument_list|(
name|i
operator|.
name|getUniqueId
argument_list|()
operator|==
literal|null
condition|?
operator|-
literal|1
else|:
name|i
operator|.
name|getUniqueId
argument_list|()
argument_list|)
return|;
block|}
comment|/** 	 *  	 * @return 	 */
specifier|public
name|String
name|nameLastNameFirst
parameter_list|()
block|{
return|return
operator|(
operator|(
name|getLastName
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|getLastName
argument_list|()
operator|.
name|trim
argument_list|()
operator|)
operator|+
literal|", "
operator|+
operator|(
name|getFirstName
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|getFirstName
argument_list|()
operator|.
name|trim
argument_list|()
operator|)
operator|+
literal|" "
operator|+
operator|(
name|getMiddleName
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|getMiddleName
argument_list|()
operator|.
name|trim
argument_list|()
operator|)
operator|)
return|;
block|}
specifier|public
name|String
name|getDeptName
parameter_list|(
name|Long
name|sessionId
parameter_list|)
block|{
if|if
condition|(
name|getDept
argument_list|()
operator|==
literal|null
operator|||
name|getDept
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
return|return
literal|"N/A"
return|;
name|Department
name|d
init|=
name|Department
operator|.
name|findByDeptCode
argument_list|(
name|getDept
argument_list|()
argument_list|,
name|sessionId
argument_list|)
decl_stmt|;
if|if
condition|(
name|d
operator|!=
literal|null
condition|)
return|return
literal|"<span title='"
operator|+
name|d
operator|.
name|getHtmlTitle
argument_list|()
operator|+
literal|"'>"
operator|+
name|d
operator|.
name|getShortLabel
argument_list|()
operator|+
literal|"</span>"
return|;
else|else
return|return
name|getDept
argument_list|()
return|;
block|}
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|Constants
operator|.
name|toInitialCase
argument_list|(
operator|(
name|getLastName
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|getLastName
argument_list|()
operator|.
name|trim
argument_list|()
operator|)
operator|+
literal|", "
operator|+
operator|(
name|getFirstName
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|getFirstName
argument_list|()
operator|.
name|trim
argument_list|()
operator|)
operator|+
operator|(
name|getMiddleName
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
literal|" "
operator|+
name|getMiddleName
argument_list|()
operator|.
name|trim
argument_list|()
operator|)
argument_list|)
return|;
block|}
specifier|public
name|String
name|getName
parameter_list|(
name|String
name|instructorNameFormat
parameter_list|)
block|{
return|return
name|NameFormat
operator|.
name|fromReference
argument_list|(
name|instructorNameFormat
argument_list|)
operator|.
name|format
argument_list|(
name|this
argument_list|)
return|;
block|}
block|}
end_class

end_unit

