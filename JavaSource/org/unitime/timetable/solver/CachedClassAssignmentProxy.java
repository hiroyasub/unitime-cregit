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
name|solver
package|;
end_package

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
name|Iterator
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
name|Set
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
name|java
operator|.
name|util
operator|.
name|Enumeration
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
name|interfaces
operator|.
name|RoomAvailabilityInterface
operator|.
name|TimeBlock
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
name|Assignment
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
name|Class_
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
name|solver
operator|.
name|ui
operator|.
name|AssignmentPreferenceInfo
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|CachedClassAssignmentProxy
implements|implements
name|ClassAssignmentProxy
block|{
specifier|private
specifier|static
name|Object
name|sNULL
init|=
name|Boolean
operator|.
name|FALSE
decl_stmt|;
specifier|private
name|ClassAssignmentProxy
name|iProxy
decl_stmt|;
specifier|private
name|Hashtable
name|iAssignmentTable
init|=
operator|new
name|Hashtable
argument_list|()
decl_stmt|;
specifier|private
name|Hashtable
name|iAssignmentInfoTable
init|=
operator|new
name|Hashtable
argument_list|()
decl_stmt|;
specifier|public
name|CachedClassAssignmentProxy
parameter_list|(
name|ClassAssignmentProxy
name|proxy
parameter_list|)
block|{
name|iProxy
operator|=
name|proxy
expr_stmt|;
block|}
specifier|public
name|Assignment
name|getAssignment
parameter_list|(
name|Long
name|classId
parameter_list|)
block|{
name|Object
name|cached
init|=
name|iAssignmentTable
operator|.
name|get
argument_list|(
name|classId
argument_list|)
decl_stmt|;
if|if
condition|(
name|cached
operator|!=
literal|null
condition|)
block|{
return|return
operator|(
name|sNULL
operator|.
name|equals
argument_list|(
name|cached
argument_list|)
condition|?
literal|null
else|:
operator|(
name|Assignment
operator|)
name|cached
operator|)
return|;
block|}
name|Assignment
name|assignment
init|=
name|iProxy
operator|.
name|getAssignment
argument_list|(
name|classId
argument_list|)
decl_stmt|;
name|iAssignmentTable
operator|.
name|put
argument_list|(
name|classId
argument_list|,
operator|(
name|assignment
operator|==
literal|null
condition|?
name|sNULL
else|:
name|assignment
operator|)
argument_list|)
expr_stmt|;
return|return
name|assignment
return|;
block|}
specifier|public
name|Assignment
name|getAssignment
parameter_list|(
name|Class_
name|clazz
parameter_list|)
block|{
name|Object
name|cached
init|=
name|iAssignmentTable
operator|.
name|get
argument_list|(
name|clazz
operator|.
name|getUniqueId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|cached
operator|!=
literal|null
condition|)
block|{
return|return
operator|(
name|sNULL
operator|.
name|equals
argument_list|(
name|cached
argument_list|)
condition|?
literal|null
else|:
operator|(
name|Assignment
operator|)
name|cached
operator|)
return|;
block|}
name|Assignment
name|assignment
init|=
name|iProxy
operator|.
name|getAssignment
argument_list|(
name|clazz
argument_list|)
decl_stmt|;
name|iAssignmentTable
operator|.
name|put
argument_list|(
name|clazz
operator|.
name|getUniqueId
argument_list|()
argument_list|,
operator|(
name|assignment
operator|==
literal|null
condition|?
name|sNULL
else|:
name|assignment
operator|)
argument_list|)
expr_stmt|;
return|return
name|assignment
return|;
block|}
specifier|public
name|AssignmentPreferenceInfo
name|getAssignmentInfo
parameter_list|(
name|Long
name|classId
parameter_list|)
block|{
name|Object
name|cached
init|=
name|iAssignmentInfoTable
operator|.
name|get
argument_list|(
name|classId
argument_list|)
decl_stmt|;
if|if
condition|(
name|cached
operator|!=
literal|null
condition|)
block|{
return|return
operator|(
name|sNULL
operator|.
name|equals
argument_list|(
name|cached
argument_list|)
condition|?
literal|null
else|:
operator|(
name|AssignmentPreferenceInfo
operator|)
name|cached
operator|)
return|;
block|}
name|AssignmentPreferenceInfo
name|info
init|=
name|iProxy
operator|.
name|getAssignmentInfo
argument_list|(
name|classId
argument_list|)
decl_stmt|;
name|iAssignmentInfoTable
operator|.
name|put
argument_list|(
name|classId
argument_list|,
operator|(
name|info
operator|==
literal|null
condition|?
name|sNULL
else|:
name|info
operator|)
argument_list|)
expr_stmt|;
return|return
name|info
return|;
block|}
specifier|public
name|AssignmentPreferenceInfo
name|getAssignmentInfo
parameter_list|(
name|Class_
name|clazz
parameter_list|)
block|{
name|Object
name|cached
init|=
name|iAssignmentInfoTable
operator|.
name|get
argument_list|(
name|clazz
operator|.
name|getUniqueId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|cached
operator|!=
literal|null
condition|)
block|{
return|return
operator|(
name|sNULL
operator|.
name|equals
argument_list|(
name|cached
argument_list|)
condition|?
literal|null
else|:
operator|(
name|AssignmentPreferenceInfo
operator|)
name|cached
operator|)
return|;
block|}
name|AssignmentPreferenceInfo
name|info
init|=
name|iProxy
operator|.
name|getAssignmentInfo
argument_list|(
name|clazz
argument_list|)
decl_stmt|;
name|iAssignmentInfoTable
operator|.
name|put
argument_list|(
name|clazz
operator|.
name|getUniqueId
argument_list|()
argument_list|,
operator|(
name|info
operator|==
literal|null
condition|?
name|sNULL
else|:
name|info
operator|)
argument_list|)
expr_stmt|;
return|return
name|info
return|;
block|}
specifier|public
name|Hashtable
name|getAssignmentTable
parameter_list|(
name|Collection
name|classesOrClassIds
parameter_list|)
block|{
name|Hashtable
name|assignments
init|=
operator|new
name|Hashtable
argument_list|()
decl_stmt|;
name|Vector
name|unknown
init|=
operator|new
name|Vector
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|classesOrClassIds
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
name|classOrClassId
init|=
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|classOrClassId
operator|instanceof
name|Object
index|[]
condition|)
name|classOrClassId
operator|=
operator|(
operator|(
name|Object
index|[]
operator|)
name|classOrClassId
operator|)
index|[
literal|0
index|]
expr_stmt|;
name|Long
name|classId
init|=
operator|(
name|classOrClassId
operator|instanceof
name|Class_
condition|?
operator|(
operator|(
name|Class_
operator|)
name|classOrClassId
operator|)
operator|.
name|getUniqueId
argument_list|()
else|:
operator|(
name|Long
operator|)
name|classOrClassId
operator|)
decl_stmt|;
name|Object
name|cached
init|=
name|iAssignmentTable
operator|.
name|get
argument_list|(
name|classId
argument_list|)
decl_stmt|;
if|if
condition|(
name|cached
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
operator|!
name|sNULL
operator|.
name|equals
argument_list|(
name|cached
argument_list|)
condition|)
name|assignments
operator|.
name|put
argument_list|(
name|classId
argument_list|,
name|cached
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|unknown
operator|.
name|add
argument_list|(
name|classOrClassId
argument_list|)
expr_stmt|;
block|}
block|}
name|Hashtable
name|newAssignments
init|=
name|iProxy
operator|.
name|getAssignmentTable
argument_list|(
name|unknown
argument_list|)
decl_stmt|;
for|for
control|(
name|Enumeration
name|e
init|=
name|unknown
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
name|Object
name|classOrClassId
init|=
name|e
operator|.
name|nextElement
argument_list|()
decl_stmt|;
if|if
condition|(
name|classOrClassId
operator|instanceof
name|Object
index|[]
condition|)
name|classOrClassId
operator|=
operator|(
operator|(
name|Object
index|[]
operator|)
name|classOrClassId
operator|)
index|[
literal|0
index|]
expr_stmt|;
name|Long
name|classId
init|=
operator|(
name|classOrClassId
operator|instanceof
name|Class_
condition|?
operator|(
operator|(
name|Class_
operator|)
name|classOrClassId
operator|)
operator|.
name|getUniqueId
argument_list|()
else|:
operator|(
name|Long
operator|)
name|classOrClassId
operator|)
decl_stmt|;
name|Assignment
name|assignment
init|=
operator|(
name|Assignment
operator|)
name|newAssignments
operator|.
name|get
argument_list|(
name|classId
argument_list|)
decl_stmt|;
name|iAssignmentTable
operator|.
name|put
argument_list|(
name|classId
argument_list|,
operator|(
name|assignment
operator|==
literal|null
condition|?
name|sNULL
else|:
name|assignment
operator|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|assignment
operator|!=
literal|null
condition|)
name|assignments
operator|.
name|put
argument_list|(
name|classId
argument_list|,
name|assignment
argument_list|)
expr_stmt|;
block|}
return|return
name|assignments
return|;
block|}
specifier|public
name|Hashtable
name|getAssignmentInfoTable
parameter_list|(
name|Collection
name|classesOrClassIds
parameter_list|)
block|{
name|Hashtable
name|infos
init|=
operator|new
name|Hashtable
argument_list|()
decl_stmt|;
name|Vector
name|unknown
init|=
operator|new
name|Vector
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|classesOrClassIds
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
name|classOrClassId
init|=
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|classOrClassId
operator|instanceof
name|Object
index|[]
condition|)
name|classOrClassId
operator|=
operator|(
operator|(
name|Object
index|[]
operator|)
name|classOrClassId
operator|)
index|[
literal|0
index|]
expr_stmt|;
name|Long
name|classId
init|=
operator|(
name|classOrClassId
operator|instanceof
name|Class_
condition|?
operator|(
operator|(
name|Class_
operator|)
name|classOrClassId
operator|)
operator|.
name|getUniqueId
argument_list|()
else|:
operator|(
name|Long
operator|)
name|classOrClassId
operator|)
decl_stmt|;
name|Object
name|cached
init|=
name|iAssignmentInfoTable
operator|.
name|get
argument_list|(
name|classId
argument_list|)
decl_stmt|;
if|if
condition|(
name|cached
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
operator|!
name|sNULL
operator|.
name|equals
argument_list|(
name|cached
argument_list|)
condition|)
name|infos
operator|.
name|put
argument_list|(
name|classId
argument_list|,
name|cached
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|unknown
operator|.
name|add
argument_list|(
name|classOrClassId
argument_list|)
expr_stmt|;
block|}
block|}
name|Hashtable
name|newInfos
init|=
name|iProxy
operator|.
name|getAssignmentInfoTable
argument_list|(
name|unknown
argument_list|)
decl_stmt|;
for|for
control|(
name|Enumeration
name|e
init|=
name|unknown
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
name|Object
name|classOrClassId
init|=
name|e
operator|.
name|nextElement
argument_list|()
decl_stmt|;
if|if
condition|(
name|classOrClassId
operator|instanceof
name|Object
index|[]
condition|)
name|classOrClassId
operator|=
operator|(
operator|(
name|Object
index|[]
operator|)
name|classOrClassId
operator|)
index|[
literal|0
index|]
expr_stmt|;
name|Long
name|classId
init|=
operator|(
name|classOrClassId
operator|instanceof
name|Class_
condition|?
operator|(
operator|(
name|Class_
operator|)
name|classOrClassId
operator|)
operator|.
name|getUniqueId
argument_list|()
else|:
operator|(
name|Long
operator|)
name|classOrClassId
operator|)
decl_stmt|;
name|AssignmentPreferenceInfo
name|info
init|=
operator|(
name|AssignmentPreferenceInfo
operator|)
name|newInfos
operator|.
name|get
argument_list|(
name|classId
argument_list|)
decl_stmt|;
name|iAssignmentInfoTable
operator|.
name|put
argument_list|(
name|classId
argument_list|,
operator|(
name|info
operator|==
literal|null
condition|?
name|sNULL
else|:
name|info
operator|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|info
operator|!=
literal|null
condition|)
name|infos
operator|.
name|put
argument_list|(
name|classId
argument_list|,
name|info
argument_list|)
expr_stmt|;
block|}
return|return
name|infos
return|;
block|}
specifier|public
name|void
name|setCache
parameter_list|(
name|Collection
name|classesOrClassIds
parameter_list|)
block|{
name|Vector
name|classesOrClassIdsVect
init|=
operator|(
name|classesOrClassIds
operator|instanceof
name|Vector
condition|?
operator|(
name|Vector
operator|)
name|classesOrClassIds
else|:
operator|new
name|Vector
argument_list|(
name|classesOrClassIds
argument_list|)
operator|)
decl_stmt|;
name|Hashtable
name|newAssignments
init|=
name|iProxy
operator|.
name|getAssignmentTable
argument_list|(
name|classesOrClassIdsVect
argument_list|)
decl_stmt|;
name|Hashtable
name|newInfos
init|=
name|iProxy
operator|.
name|getAssignmentInfoTable
argument_list|(
name|classesOrClassIdsVect
argument_list|)
decl_stmt|;
for|for
control|(
name|Enumeration
name|e
init|=
name|classesOrClassIdsVect
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
name|Object
name|classOrClassId
init|=
name|e
operator|.
name|nextElement
argument_list|()
decl_stmt|;
if|if
condition|(
name|classOrClassId
operator|instanceof
name|Object
index|[]
condition|)
name|classOrClassId
operator|=
operator|(
operator|(
name|Object
index|[]
operator|)
name|classOrClassId
operator|)
index|[
literal|0
index|]
expr_stmt|;
name|Long
name|classId
init|=
operator|(
name|classOrClassId
operator|instanceof
name|Class_
condition|?
operator|(
operator|(
name|Class_
operator|)
name|classOrClassId
operator|)
operator|.
name|getUniqueId
argument_list|()
else|:
operator|(
name|Long
operator|)
name|classOrClassId
operator|)
decl_stmt|;
name|Assignment
name|assignment
init|=
operator|(
name|Assignment
operator|)
name|newAssignments
operator|.
name|get
argument_list|(
name|classId
argument_list|)
decl_stmt|;
name|iAssignmentTable
operator|.
name|put
argument_list|(
name|classId
argument_list|,
operator|(
name|assignment
operator|==
literal|null
condition|?
name|sNULL
else|:
name|assignment
operator|)
argument_list|)
expr_stmt|;
name|AssignmentPreferenceInfo
name|info
init|=
operator|(
name|AssignmentPreferenceInfo
operator|)
name|newInfos
operator|.
name|get
argument_list|(
name|classId
argument_list|)
decl_stmt|;
name|iAssignmentInfoTable
operator|.
name|put
argument_list|(
name|classId
argument_list|,
operator|(
name|info
operator|==
literal|null
condition|?
name|sNULL
else|:
name|info
operator|)
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|hasConflicts
parameter_list|(
name|Long
name|offeringId
parameter_list|)
block|{
return|return
name|iProxy
operator|.
name|hasConflicts
argument_list|(
name|offeringId
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Set
argument_list|<
name|Assignment
argument_list|>
name|getConflicts
parameter_list|(
name|Long
name|classId
parameter_list|)
block|{
return|return
name|iProxy
operator|.
name|getConflicts
argument_list|(
name|classId
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Set
argument_list|<
name|TimeBlock
argument_list|>
name|getConflictingTimeBlocks
parameter_list|(
name|Long
name|classId
parameter_list|)
block|{
return|return
name|iProxy
operator|.
name|getConflictingTimeBlocks
argument_list|(
name|classId
argument_list|)
return|;
block|}
block|}
end_class

end_unit

