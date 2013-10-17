begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 - 3.5 (University Timetabling Application)  * Copyright (C) 2008 - 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|ArrayList
import|;
end_import

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
name|Hashtable
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
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|base
operator|.
name|BaseStudentAccomodation
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
name|StudentAccomodationDAO
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|StudentAccomodation
extends|extends
name|BaseStudentAccomodation
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
name|StudentAccomodation
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|StudentAccomodation
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
specifier|public
specifier|static
name|StudentAccomodation
name|findByAbbv
parameter_list|(
name|Long
name|sessionId
parameter_list|,
name|String
name|abbv
parameter_list|)
block|{
return|return
operator|(
name|StudentAccomodation
operator|)
operator|new
name|StudentAccomodationDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select a from StudentAccomodation a where "
operator|+
literal|"a.session.uniqueId=:sessionId and "
operator|+
literal|"a.abbreviation=:abbv"
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
literal|"abbv"
argument_list|,
name|abbv
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
specifier|public
specifier|static
name|List
argument_list|<
name|AccommodationCounter
argument_list|>
name|getAccommodations
parameter_list|(
name|InstructionalOffering
name|offering
parameter_list|)
block|{
name|List
argument_list|<
name|AccommodationCounter
argument_list|>
name|ret
init|=
operator|new
name|ArrayList
argument_list|<
name|AccommodationCounter
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Object
index|[]
name|line
range|:
operator|(
name|List
argument_list|<
name|Object
index|[]
argument_list|>
operator|)
name|StudentAccomodationDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select a, count(distinct e.student) from StudentClassEnrollment e inner join e.student.accomodations a "
operator|+
literal|"where e.courseOffering.instructionalOffering.uniqueId = :offeringId "
operator|+
literal|"group by a.uniqueId, a.session.uniqueId, a.abbreviation, a.name, a.externalUniqueId "
operator|+
literal|"order by count(a) desc, a.name"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"offeringId"
argument_list|,
name|offering
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
control|)
block|{
name|ret
operator|.
name|add
argument_list|(
operator|new
name|AccommodationCounter
argument_list|(
operator|(
name|StudentAccomodation
operator|)
name|line
index|[
literal|0
index|]
argument_list|,
operator|(
operator|(
name|Number
operator|)
name|line
index|[
literal|1
index|]
operator|)
operator|.
name|intValue
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|ret
return|;
block|}
specifier|public
specifier|static
name|List
argument_list|<
name|AccommodationCounter
argument_list|>
name|getAccommodations
parameter_list|(
name|Class_
name|clazz
parameter_list|)
block|{
name|List
argument_list|<
name|AccommodationCounter
argument_list|>
name|ret
init|=
operator|new
name|ArrayList
argument_list|<
name|AccommodationCounter
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Object
index|[]
name|line
range|:
operator|(
name|List
argument_list|<
name|Object
index|[]
argument_list|>
operator|)
name|StudentAccomodationDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select a, count(distinct e.student) from StudentClassEnrollment e inner join e.student.accomodations a "
operator|+
literal|"where e.clazz.uniqueId = :classId "
operator|+
literal|"group by a.uniqueId, a.session.uniqueId, a.abbreviation, a.name, a.externalUniqueId "
operator|+
literal|"order by count(a) desc, a.name"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"classId"
argument_list|,
name|clazz
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
control|)
block|{
name|ret
operator|.
name|add
argument_list|(
operator|new
name|AccommodationCounter
argument_list|(
operator|(
name|StudentAccomodation
operator|)
name|line
index|[
literal|0
index|]
argument_list|,
operator|(
operator|(
name|Number
operator|)
name|line
index|[
literal|1
index|]
operator|)
operator|.
name|intValue
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|ret
return|;
block|}
specifier|public
specifier|static
name|List
argument_list|<
name|AccommodationCounter
argument_list|>
name|getAccommodations
parameter_list|(
name|Exam
name|exam
parameter_list|)
block|{
name|Map
argument_list|<
name|StudentAccomodation
argument_list|,
name|Integer
argument_list|>
name|counter
init|=
operator|new
name|Hashtable
argument_list|<
name|StudentAccomodation
argument_list|,
name|Integer
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|ExamOwner
name|owner
range|:
name|exam
operator|.
name|getOwners
argument_list|()
control|)
block|{
name|String
name|query
init|=
literal|null
decl_stmt|;
switch|switch
condition|(
name|owner
operator|.
name|getOwnerType
argument_list|()
condition|)
block|{
case|case
name|ExamOwner
operator|.
name|sOwnerTypeClass
case|:
name|query
operator|=
literal|"e.clazz.uniqueId = :examOwnerId"
expr_stmt|;
break|break;
case|case
name|ExamOwner
operator|.
name|sOwnerTypeConfig
case|:
name|query
operator|=
literal|"e.clazz.schedulingSubpart.instrOfferingConfig.uniqueId = :examOwnerId"
expr_stmt|;
break|break;
case|case
name|ExamOwner
operator|.
name|sOwnerTypeCourse
case|:
name|query
operator|=
literal|"e.courseOffering.uniqueId = :examOwnerId"
expr_stmt|;
break|break;
case|case
name|ExamOwner
operator|.
name|sOwnerTypeOffering
case|:
name|query
operator|=
literal|"e.courseOffering.instructionalOffering.uniqueId = :examOwnerId"
expr_stmt|;
break|break;
block|}
if|if
condition|(
name|query
operator|==
literal|null
condition|)
continue|continue;
for|for
control|(
name|Object
index|[]
name|line
range|:
operator|(
name|List
argument_list|<
name|Object
index|[]
argument_list|>
operator|)
name|StudentAccomodationDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select a, count(distinct e.student) from StudentClassEnrollment e inner join e.student.accomodations a "
operator|+
literal|"where "
operator|+
name|query
operator|+
literal|" "
operator|+
literal|"group by a.uniqueId, a.session.uniqueId, a.abbreviation, a.name, a.externalUniqueId "
operator|+
literal|"order by count(a) desc, a.name"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"examOwnerId"
argument_list|,
name|owner
operator|.
name|getOwnerId
argument_list|()
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
control|)
block|{
name|StudentAccomodation
name|a
init|=
operator|(
name|StudentAccomodation
operator|)
name|line
index|[
literal|0
index|]
decl_stmt|;
name|int
name|count
init|=
operator|(
operator|(
name|Number
operator|)
name|line
index|[
literal|1
index|]
operator|)
operator|.
name|intValue
argument_list|()
decl_stmt|;
name|Integer
name|prev
init|=
name|counter
operator|.
name|get
argument_list|(
name|a
argument_list|)
decl_stmt|;
name|counter
operator|.
name|put
argument_list|(
name|a
argument_list|,
name|count
operator|+
operator|(
name|prev
operator|==
literal|null
condition|?
literal|0
else|:
name|prev
operator|.
name|intValue
argument_list|()
operator|)
argument_list|)
expr_stmt|;
block|}
block|}
name|List
argument_list|<
name|AccommodationCounter
argument_list|>
name|ret
init|=
operator|new
name|ArrayList
argument_list|<
name|AccommodationCounter
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|StudentAccomodation
argument_list|,
name|Integer
argument_list|>
name|entry
range|:
name|counter
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|ret
operator|.
name|add
argument_list|(
operator|new
name|AccommodationCounter
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|,
name|entry
operator|.
name|getValue
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|Collections
operator|.
name|sort
argument_list|(
name|ret
argument_list|)
expr_stmt|;
return|return
name|ret
return|;
block|}
specifier|public
specifier|static
name|String
name|toHtml
parameter_list|(
name|List
argument_list|<
name|AccommodationCounter
argument_list|>
name|table
parameter_list|)
block|{
if|if
condition|(
name|table
operator|==
literal|null
operator|||
name|table
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
literal|null
return|;
name|StringBuffer
name|ret
init|=
operator|new
name|StringBuffer
argument_list|(
literal|"<table>"
argument_list|)
decl_stmt|;
for|for
control|(
name|AccommodationCounter
name|ac
range|:
name|table
control|)
name|ret
operator|.
name|append
argument_list|(
name|ac
operator|.
name|toHtmlRow
argument_list|()
argument_list|)
expr_stmt|;
name|ret
operator|.
name|append
argument_list|(
literal|"</table>"
argument_list|)
expr_stmt|;
return|return
name|ret
operator|.
name|toString
argument_list|()
return|;
block|}
specifier|public
specifier|static
class|class
name|AccommodationCounter
implements|implements
name|Comparable
argument_list|<
name|AccommodationCounter
argument_list|>
block|{
name|StudentAccomodation
name|iAccommodation
decl_stmt|;
specifier|private
name|int
name|iCount
decl_stmt|;
specifier|public
name|AccommodationCounter
parameter_list|(
name|StudentAccomodation
name|accommodation
parameter_list|,
name|int
name|count
parameter_list|)
block|{
name|iAccommodation
operator|=
name|accommodation
expr_stmt|;
name|iCount
operator|=
name|count
expr_stmt|;
block|}
specifier|public
name|int
name|getCount
parameter_list|()
block|{
return|return
name|iCount
return|;
block|}
specifier|public
name|StudentAccomodation
name|getAccommodation
parameter_list|()
block|{
return|return
name|iAccommodation
return|;
block|}
specifier|public
name|void
name|increment
parameter_list|(
name|int
name|count
parameter_list|)
block|{
name|iCount
operator|+=
name|count
expr_stmt|;
block|}
specifier|public
name|String
name|toHtmlRow
parameter_list|()
block|{
return|return
literal|"<tr><td>"
operator|+
name|getAccommodation
argument_list|()
operator|.
name|getName
argument_list|()
operator|+
literal|":</td><td>"
operator|+
name|getCount
argument_list|()
operator|+
literal|"</td></tr>"
return|;
block|}
specifier|public
name|int
name|compareTo
parameter_list|(
name|AccommodationCounter
name|ac
parameter_list|)
block|{
if|if
condition|(
name|getCount
argument_list|()
operator|>
name|ac
operator|.
name|getCount
argument_list|()
condition|)
return|return
operator|-
literal|1
return|;
if|if
condition|(
name|getCount
argument_list|()
operator|<
name|ac
operator|.
name|getCount
argument_list|()
condition|)
return|return
literal|1
return|;
name|int
name|cmp
init|=
name|getAccommodation
argument_list|()
operator|.
name|getName
argument_list|()
operator|.
name|compareTo
argument_list|(
name|ac
operator|.
name|getAccommodation
argument_list|()
operator|.
name|getName
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
name|getAccommodation
argument_list|()
operator|.
name|getUniqueId
argument_list|()
operator|.
name|compareTo
argument_list|(
name|ac
operator|.
name|getAccommodation
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
return|;
block|}
block|}
block|}
end_class

end_unit

