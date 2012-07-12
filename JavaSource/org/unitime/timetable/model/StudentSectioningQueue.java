begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 (University Timetabling Application)  * Copyright (C) 2010, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *  * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|Collection
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Date
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
name|List
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
name|dom4j
operator|.
name|DocumentHelper
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
name|commons
operator|.
name|User
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
name|BaseStudentSectioningQueue
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
name|onlinesectioning
operator|.
name|OnlineSectioningLog
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

begin_class
specifier|public
class|class
name|StudentSectioningQueue
extends|extends
name|BaseStudentSectioningQueue
implements|implements
name|Comparable
argument_list|<
name|StudentSectioningQueue
argument_list|>
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|8492171207847794888L
decl_stmt|;
specifier|public
name|StudentSectioningQueue
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
specifier|public
specifier|static
enum|enum
name|Type
block|{
name|STUDENT_ENROLLMENT_CHANGE
block|,
name|CLASS_ASSIGNMENT_CHANGE
block|,
name|SESSION_STATUS_CHANGE
block|,
name|SESSION_RELOAD
block|,
name|OFFERING_CHANGE
block|}
specifier|public
specifier|static
name|TreeSet
argument_list|<
name|StudentSectioningQueue
argument_list|>
name|getItems
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|Long
name|sessionId
parameter_list|,
name|Date
name|lastTimeStamp
parameter_list|)
block|{
if|if
condition|(
name|sessionId
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|lastTimeStamp
operator|==
literal|null
condition|)
block|{
return|return
operator|new
name|TreeSet
argument_list|<
name|StudentSectioningQueue
argument_list|>
argument_list|(
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"select q from StudentSectioningQueue q where q.sessionId = :sessionId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
argument_list|)
operator|.
name|list
argument_list|()
argument_list|)
return|;
block|}
else|else
block|{
return|return
operator|new
name|TreeSet
argument_list|<
name|StudentSectioningQueue
argument_list|>
argument_list|(
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"select q from StudentSectioningQueue q where q.sessionId = :sessionId and q.timeStamp> :timeStamp"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
argument_list|)
operator|.
name|setTimestamp
argument_list|(
literal|"timeStamp"
argument_list|,
name|lastTimeStamp
argument_list|)
operator|.
name|list
argument_list|()
argument_list|)
return|;
block|}
block|}
else|else
block|{
if|if
condition|(
name|lastTimeStamp
operator|==
literal|null
condition|)
block|{
return|return
operator|new
name|TreeSet
argument_list|<
name|StudentSectioningQueue
argument_list|>
argument_list|(
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"select q from StudentSectioningQueue q"
argument_list|)
operator|.
name|list
argument_list|()
argument_list|)
return|;
block|}
else|else
block|{
return|return
operator|new
name|TreeSet
argument_list|<
name|StudentSectioningQueue
argument_list|>
argument_list|(
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"select q from StudentSectioningQueue q where q.timeStamp> :timeStamp"
argument_list|)
operator|.
name|setTimestamp
argument_list|(
literal|"timeStamp"
argument_list|,
name|lastTimeStamp
argument_list|)
operator|.
name|list
argument_list|()
argument_list|)
return|;
block|}
block|}
block|}
specifier|public
specifier|static
name|Date
name|getLastTimeStamp
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|Long
name|sessionId
parameter_list|)
block|{
if|if
condition|(
name|sessionId
operator|!=
literal|null
condition|)
return|return
operator|(
name|Date
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"select max(q.timeStamp) from StudentSectioningQueue q where q.sessionId = :sessionId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
argument_list|)
operator|.
name|uniqueResult
argument_list|()
return|;
else|else
return|return
operator|(
name|Date
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"select max(q.timeStamp) from StudentSectioningQueue q"
argument_list|)
operator|.
name|uniqueResult
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|compareTo
parameter_list|(
name|StudentSectioningQueue
name|q
parameter_list|)
block|{
name|int
name|cmp
init|=
name|getTimeStamp
argument_list|()
operator|.
name|compareTo
argument_list|(
name|q
operator|.
name|getTimeStamp
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
name|getUniqueId
argument_list|()
operator|.
name|compareTo
argument_list|(
name|q
operator|.
name|getUniqueId
argument_list|()
argument_list|)
return|;
block|}
specifier|protected
specifier|static
name|void
name|addItem
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|org
operator|.
name|unitime
operator|.
name|commons
operator|.
name|User
name|user
parameter_list|,
name|Long
name|sessionId
parameter_list|,
name|Type
name|type
parameter_list|,
name|Collection
argument_list|<
name|Long
argument_list|>
name|ids
parameter_list|)
block|{
name|StudentSectioningQueue
name|q
init|=
operator|new
name|StudentSectioningQueue
argument_list|()
decl_stmt|;
name|q
operator|.
name|setTimeStamp
argument_list|(
operator|new
name|Date
argument_list|()
argument_list|)
expr_stmt|;
name|q
operator|.
name|setType
argument_list|(
name|type
operator|.
name|ordinal
argument_list|()
argument_list|)
expr_stmt|;
name|q
operator|.
name|setSessionId
argument_list|(
name|sessionId
argument_list|)
expr_stmt|;
name|Document
name|d
init|=
name|DocumentHelper
operator|.
name|createDocument
argument_list|()
decl_stmt|;
name|Element
name|root
init|=
name|d
operator|.
name|addElement
argument_list|(
literal|"generic"
argument_list|)
decl_stmt|;
if|if
condition|(
name|user
operator|!=
literal|null
condition|)
block|{
name|Element
name|e
init|=
name|root
operator|.
name|addElement
argument_list|(
literal|"user"
argument_list|)
decl_stmt|;
name|e
operator|.
name|addAttribute
argument_list|(
literal|"id"
argument_list|,
name|user
operator|.
name|getId
argument_list|()
argument_list|)
operator|.
name|setText
argument_list|(
name|user
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|ids
operator|!=
literal|null
operator|&&
operator|!
name|ids
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
for|for
control|(
name|Long
name|id
range|:
name|ids
control|)
name|root
operator|.
name|addElement
argument_list|(
literal|"id"
argument_list|)
operator|.
name|setText
argument_list|(
name|id
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|q
operator|.
name|setMessage
argument_list|(
name|d
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|save
argument_list|(
name|q
argument_list|)
expr_stmt|;
block|}
specifier|protected
specifier|static
name|void
name|addItem
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|UserContext
name|user
parameter_list|,
name|Long
name|sessionId
parameter_list|,
name|Type
name|type
parameter_list|,
name|Collection
argument_list|<
name|Long
argument_list|>
name|ids
parameter_list|)
block|{
name|StudentSectioningQueue
name|q
init|=
operator|new
name|StudentSectioningQueue
argument_list|()
decl_stmt|;
name|q
operator|.
name|setTimeStamp
argument_list|(
operator|new
name|Date
argument_list|()
argument_list|)
expr_stmt|;
name|q
operator|.
name|setType
argument_list|(
name|type
operator|.
name|ordinal
argument_list|()
argument_list|)
expr_stmt|;
name|q
operator|.
name|setSessionId
argument_list|(
name|sessionId
argument_list|)
expr_stmt|;
name|Document
name|d
init|=
name|DocumentHelper
operator|.
name|createDocument
argument_list|()
decl_stmt|;
name|Element
name|root
init|=
name|d
operator|.
name|addElement
argument_list|(
literal|"generic"
argument_list|)
decl_stmt|;
if|if
condition|(
name|user
operator|!=
literal|null
condition|)
block|{
name|Element
name|e
init|=
name|root
operator|.
name|addElement
argument_list|(
literal|"user"
argument_list|)
decl_stmt|;
name|e
operator|.
name|addAttribute
argument_list|(
literal|"id"
argument_list|,
name|user
operator|.
name|getExternalUserId
argument_list|()
argument_list|)
operator|.
name|setText
argument_list|(
name|user
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|ids
operator|!=
literal|null
operator|&&
operator|!
name|ids
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
for|for
control|(
name|Long
name|id
range|:
name|ids
control|)
name|root
operator|.
name|addElement
argument_list|(
literal|"id"
argument_list|)
operator|.
name|setText
argument_list|(
name|id
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|q
operator|.
name|setMessage
argument_list|(
name|d
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|save
argument_list|(
name|q
argument_list|)
expr_stmt|;
block|}
specifier|protected
specifier|static
name|void
name|addItem
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|org
operator|.
name|unitime
operator|.
name|commons
operator|.
name|User
name|user
parameter_list|,
name|Long
name|sessionId
parameter_list|,
name|Type
name|type
parameter_list|,
name|Long
modifier|...
name|ids
parameter_list|)
block|{
name|StudentSectioningQueue
name|q
init|=
operator|new
name|StudentSectioningQueue
argument_list|()
decl_stmt|;
name|q
operator|.
name|setTimeStamp
argument_list|(
operator|new
name|Date
argument_list|()
argument_list|)
expr_stmt|;
name|q
operator|.
name|setType
argument_list|(
name|type
operator|.
name|ordinal
argument_list|()
argument_list|)
expr_stmt|;
name|q
operator|.
name|setSessionId
argument_list|(
name|sessionId
argument_list|)
expr_stmt|;
name|Document
name|d
init|=
name|DocumentHelper
operator|.
name|createDocument
argument_list|()
decl_stmt|;
name|Element
name|root
init|=
name|d
operator|.
name|addElement
argument_list|(
literal|"generic"
argument_list|)
decl_stmt|;
if|if
condition|(
name|user
operator|!=
literal|null
condition|)
block|{
name|Element
name|e
init|=
name|root
operator|.
name|addElement
argument_list|(
literal|"user"
argument_list|)
decl_stmt|;
name|e
operator|.
name|addAttribute
argument_list|(
literal|"id"
argument_list|,
name|user
operator|.
name|getId
argument_list|()
argument_list|)
operator|.
name|setText
argument_list|(
name|user
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|ids
operator|!=
literal|null
operator|&&
name|ids
operator|.
name|length
operator|>
literal|0
condition|)
block|{
for|for
control|(
name|Long
name|id
range|:
name|ids
control|)
name|root
operator|.
name|addElement
argument_list|(
literal|"id"
argument_list|)
operator|.
name|setText
argument_list|(
name|id
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|q
operator|.
name|setMessage
argument_list|(
name|d
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|save
argument_list|(
name|q
argument_list|)
expr_stmt|;
block|}
specifier|protected
specifier|static
name|void
name|addItem
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|UserContext
name|user
parameter_list|,
name|Long
name|sessionId
parameter_list|,
name|Type
name|type
parameter_list|,
name|Long
modifier|...
name|ids
parameter_list|)
block|{
name|StudentSectioningQueue
name|q
init|=
operator|new
name|StudentSectioningQueue
argument_list|()
decl_stmt|;
name|q
operator|.
name|setTimeStamp
argument_list|(
operator|new
name|Date
argument_list|()
argument_list|)
expr_stmt|;
name|q
operator|.
name|setType
argument_list|(
name|type
operator|.
name|ordinal
argument_list|()
argument_list|)
expr_stmt|;
name|q
operator|.
name|setSessionId
argument_list|(
name|sessionId
argument_list|)
expr_stmt|;
name|Document
name|d
init|=
name|DocumentHelper
operator|.
name|createDocument
argument_list|()
decl_stmt|;
name|Element
name|root
init|=
name|d
operator|.
name|addElement
argument_list|(
literal|"generic"
argument_list|)
decl_stmt|;
if|if
condition|(
name|user
operator|!=
literal|null
condition|)
block|{
name|Element
name|e
init|=
name|root
operator|.
name|addElement
argument_list|(
literal|"user"
argument_list|)
decl_stmt|;
name|e
operator|.
name|addAttribute
argument_list|(
literal|"id"
argument_list|,
name|user
operator|.
name|getExternalUserId
argument_list|()
argument_list|)
operator|.
name|setText
argument_list|(
name|user
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|ids
operator|!=
literal|null
operator|&&
name|ids
operator|.
name|length
operator|>
literal|0
condition|)
block|{
for|for
control|(
name|Long
name|id
range|:
name|ids
control|)
name|root
operator|.
name|addElement
argument_list|(
literal|"id"
argument_list|)
operator|.
name|setText
argument_list|(
name|id
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|q
operator|.
name|setMessage
argument_list|(
name|d
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|save
argument_list|(
name|q
argument_list|)
expr_stmt|;
block|}
specifier|public
name|List
argument_list|<
name|Long
argument_list|>
name|getIds
parameter_list|()
block|{
if|if
condition|(
name|getMessage
argument_list|()
operator|==
literal|null
condition|)
return|return
literal|null
return|;
name|Element
name|root
init|=
name|getMessage
argument_list|()
operator|.
name|getRootElement
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
literal|"generic"
operator|.
name|equals
argument_list|(
name|root
operator|.
name|getName
argument_list|()
argument_list|)
condition|)
return|return
literal|null
return|;
name|List
argument_list|<
name|Long
argument_list|>
name|ids
init|=
operator|new
name|ArrayList
argument_list|<
name|Long
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
argument_list|<
name|Element
argument_list|>
name|i
init|=
name|root
operator|.
name|elementIterator
argument_list|(
literal|"id"
argument_list|)
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
name|ids
operator|.
name|add
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|i
operator|.
name|next
argument_list|()
operator|.
name|getText
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|ids
return|;
block|}
specifier|public
name|OnlineSectioningLog
operator|.
name|Entity
name|getUser
parameter_list|()
block|{
if|if
condition|(
name|getMessage
argument_list|()
operator|==
literal|null
condition|)
return|return
literal|null
return|;
name|Element
name|root
init|=
name|getMessage
argument_list|()
operator|.
name|getRootElement
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
literal|"generic"
operator|.
name|equals
argument_list|(
name|root
operator|.
name|getName
argument_list|()
argument_list|)
condition|)
return|return
literal|null
return|;
name|Element
name|user
init|=
name|root
operator|.
name|element
argument_list|(
literal|"user"
argument_list|)
decl_stmt|;
if|if
condition|(
name|user
operator|==
literal|null
condition|)
return|return
name|OnlineSectioningLog
operator|.
name|Entity
operator|.
name|newBuilder
argument_list|()
operator|.
name|setExternalId
argument_list|(
name|StudentClassEnrollment
operator|.
name|SystemChange
operator|.
name|SYSTEM
operator|.
name|name
argument_list|()
argument_list|)
operator|.
name|setName
argument_list|(
name|StudentClassEnrollment
operator|.
name|SystemChange
operator|.
name|SYSTEM
operator|.
name|getName
argument_list|()
argument_list|)
operator|.
name|setType
argument_list|(
name|OnlineSectioningLog
operator|.
name|Entity
operator|.
name|EntityType
operator|.
name|OTHER
argument_list|)
operator|.
name|build
argument_list|()
return|;
else|else
return|return
name|OnlineSectioningLog
operator|.
name|Entity
operator|.
name|newBuilder
argument_list|()
operator|.
name|setExternalId
argument_list|(
name|user
operator|.
name|attributeValue
argument_list|(
literal|"id"
argument_list|)
argument_list|)
operator|.
name|setName
argument_list|(
name|user
operator|.
name|getText
argument_list|()
argument_list|)
operator|.
name|setType
argument_list|(
name|OnlineSectioningLog
operator|.
name|Entity
operator|.
name|EntityType
operator|.
name|MANAGER
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
specifier|public
specifier|static
name|void
name|sessionStatusChanged
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|org
operator|.
name|unitime
operator|.
name|commons
operator|.
name|User
name|user
parameter_list|,
name|Long
name|sessionId
parameter_list|,
name|boolean
name|reload
parameter_list|)
block|{
name|addItem
argument_list|(
name|hibSession
argument_list|,
name|user
argument_list|,
name|sessionId
argument_list|,
operator|(
name|reload
condition|?
name|Type
operator|.
name|SESSION_RELOAD
else|:
name|Type
operator|.
name|SESSION_STATUS_CHANGE
operator|)
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|void
name|allStudentsChanged
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|org
operator|.
name|unitime
operator|.
name|commons
operator|.
name|User
name|user
parameter_list|,
name|Long
name|sessionId
parameter_list|)
block|{
name|addItem
argument_list|(
name|hibSession
argument_list|,
name|user
argument_list|,
name|sessionId
argument_list|,
name|Type
operator|.
name|STUDENT_ENROLLMENT_CHANGE
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|void
name|studentChanged
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|org
operator|.
name|unitime
operator|.
name|commons
operator|.
name|User
name|user
parameter_list|,
name|Long
name|sessionId
parameter_list|,
name|Collection
argument_list|<
name|Long
argument_list|>
name|studentIds
parameter_list|)
block|{
name|addItem
argument_list|(
name|hibSession
argument_list|,
name|user
argument_list|,
name|sessionId
argument_list|,
name|Type
operator|.
name|STUDENT_ENROLLMENT_CHANGE
argument_list|,
name|studentIds
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Deprecated
comment|/** Use {@link StudentSectioningQueue#studentChanged(org.hibernate.Session, org.unitime.commons.User, Long, Collection<Long>)} */
specifier|public
specifier|static
name|void
name|studentChanged
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|Long
name|sessionId
parameter_list|,
name|Collection
argument_list|<
name|Long
argument_list|>
name|studentIds
parameter_list|)
block|{
name|addItem
argument_list|(
name|hibSession
argument_list|,
operator|(
name|User
operator|)
literal|null
argument_list|,
name|sessionId
argument_list|,
name|Type
operator|.
name|STUDENT_ENROLLMENT_CHANGE
argument_list|,
name|studentIds
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|void
name|studentChanged
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|org
operator|.
name|unitime
operator|.
name|commons
operator|.
name|User
name|user
parameter_list|,
name|Long
name|sessionId
parameter_list|,
name|Long
modifier|...
name|studentIds
parameter_list|)
block|{
name|addItem
argument_list|(
name|hibSession
argument_list|,
name|user
argument_list|,
name|sessionId
argument_list|,
name|Type
operator|.
name|STUDENT_ENROLLMENT_CHANGE
argument_list|,
name|studentIds
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|void
name|classAssignmentChanged
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|org
operator|.
name|unitime
operator|.
name|commons
operator|.
name|User
name|user
parameter_list|,
name|Long
name|sessionId
parameter_list|,
name|Collection
argument_list|<
name|Long
argument_list|>
name|classIds
parameter_list|)
block|{
name|addItem
argument_list|(
name|hibSession
argument_list|,
name|user
argument_list|,
name|sessionId
argument_list|,
name|Type
operator|.
name|CLASS_ASSIGNMENT_CHANGE
argument_list|,
name|classIds
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|void
name|classAssignmentChanged
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|UserContext
name|user
parameter_list|,
name|Long
name|sessionId
parameter_list|,
name|Collection
argument_list|<
name|Long
argument_list|>
name|classIds
parameter_list|)
block|{
name|addItem
argument_list|(
name|hibSession
argument_list|,
name|user
argument_list|,
name|sessionId
argument_list|,
name|Type
operator|.
name|CLASS_ASSIGNMENT_CHANGE
argument_list|,
name|classIds
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|void
name|classAssignmentChanged
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|org
operator|.
name|unitime
operator|.
name|commons
operator|.
name|User
name|user
parameter_list|,
name|Long
name|sessionId
parameter_list|,
name|Long
modifier|...
name|classIds
parameter_list|)
block|{
name|addItem
argument_list|(
name|hibSession
argument_list|,
name|user
argument_list|,
name|sessionId
argument_list|,
name|Type
operator|.
name|CLASS_ASSIGNMENT_CHANGE
argument_list|,
name|classIds
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|void
name|offeringChanged
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|org
operator|.
name|unitime
operator|.
name|commons
operator|.
name|User
name|user
parameter_list|,
name|Long
name|sessionId
parameter_list|,
name|Collection
argument_list|<
name|Long
argument_list|>
name|offeringId
parameter_list|)
block|{
name|addItem
argument_list|(
name|hibSession
argument_list|,
name|user
argument_list|,
name|sessionId
argument_list|,
name|Type
operator|.
name|OFFERING_CHANGE
argument_list|,
name|offeringId
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|void
name|offeringChanged
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|UserContext
name|user
parameter_list|,
name|Long
name|sessionId
parameter_list|,
name|Collection
argument_list|<
name|Long
argument_list|>
name|offeringId
parameter_list|)
block|{
name|addItem
argument_list|(
name|hibSession
argument_list|,
name|user
argument_list|,
name|sessionId
argument_list|,
name|Type
operator|.
name|OFFERING_CHANGE
argument_list|,
name|offeringId
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|void
name|offeringChanged
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|org
operator|.
name|unitime
operator|.
name|commons
operator|.
name|User
name|user
parameter_list|,
name|Long
name|sessionId
parameter_list|,
name|Long
modifier|...
name|offeringId
parameter_list|)
block|{
name|addItem
argument_list|(
name|hibSession
argument_list|,
name|user
argument_list|,
name|sessionId
argument_list|,
name|Type
operator|.
name|OFFERING_CHANGE
argument_list|,
name|offeringId
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|void
name|offeringChanged
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|UserContext
name|user
parameter_list|,
name|Long
name|sessionId
parameter_list|,
name|Long
modifier|...
name|offeringId
parameter_list|)
block|{
name|addItem
argument_list|(
name|hibSession
argument_list|,
name|user
argument_list|,
name|sessionId
argument_list|,
name|Type
operator|.
name|OFFERING_CHANGE
argument_list|,
name|offeringId
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

