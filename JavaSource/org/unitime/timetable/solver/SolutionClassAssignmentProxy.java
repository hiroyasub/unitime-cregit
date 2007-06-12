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
name|solver
package|;
end_package

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
name|HashSet
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
name|Iterator
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
name|org
operator|.
name|hibernate
operator|.
name|ObjectNotFoundException
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
name|Solution
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
name|SolutionDAO
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
name|SolutionClassAssignmentProxy
extends|extends
name|CommitedClassAssignmentProxy
block|{
specifier|private
name|Set
name|iSolutionIds
init|=
operator|new
name|HashSet
argument_list|()
decl_stmt|;
specifier|private
name|Hashtable
name|iDepartmentIds
init|=
operator|new
name|Hashtable
argument_list|()
decl_stmt|;
comment|/* 	private Hashtable iCachedAssignments = new Hashtable(); 	private HashSet iCachedSubjects =  new HashSet(); 	*/
specifier|public
name|SolutionClassAssignmentProxy
parameter_list|(
name|Collection
name|solutionIds
parameter_list|)
block|{
name|super
argument_list|()
expr_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|solutionIds
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
name|Solution
name|solution
init|=
operator|(
operator|new
name|SolutionDAO
argument_list|()
operator|)
operator|.
name|get
argument_list|(
operator|(
name|Long
operator|)
name|i
operator|.
name|next
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|solution
operator|==
literal|null
condition|)
continue|continue;
name|iSolutionIds
operator|.
name|add
argument_list|(
name|solution
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
name|j
init|=
name|solution
operator|.
name|getOwner
argument_list|()
operator|.
name|getDepartments
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|j
operator|.
name|hasNext
argument_list|()
condition|;
control|)
name|iDepartmentIds
operator|.
name|put
argument_list|(
operator|(
operator|(
name|Department
operator|)
name|j
operator|.
name|next
argument_list|()
operator|)
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|solution
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|SolutionClassAssignmentProxy
parameter_list|(
name|Solution
name|solution
parameter_list|)
block|{
name|super
argument_list|()
expr_stmt|;
name|iSolutionIds
operator|.
name|add
argument_list|(
name|solution
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
name|j
init|=
name|solution
operator|.
name|getOwner
argument_list|()
operator|.
name|getDepartments
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|j
operator|.
name|hasNext
argument_list|()
condition|;
control|)
name|iDepartmentIds
operator|.
name|put
argument_list|(
operator|(
operator|(
name|Department
operator|)
name|j
operator|.
name|next
argument_list|()
operator|)
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|solution
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Long
name|getSolutionId
parameter_list|(
name|Class_
name|clazz
parameter_list|)
block|{
name|Department
name|department
init|=
name|clazz
operator|.
name|getManagingDept
argument_list|()
decl_stmt|;
if|if
condition|(
name|department
operator|==
literal|null
condition|)
return|return
literal|null
return|;
return|return
operator|(
name|Long
operator|)
name|iDepartmentIds
operator|.
name|get
argument_list|(
name|department
operator|.
name|getUniqueId
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|Assignment
name|getAssignment
parameter_list|(
name|Class_
name|clazz
parameter_list|)
throws|throws
name|Exception
block|{
name|Long
name|solutionId
init|=
name|getSolutionId
argument_list|(
name|clazz
argument_list|)
decl_stmt|;
if|if
condition|(
name|solutionId
operator|==
literal|null
condition|)
return|return
name|super
operator|.
name|getAssignment
argument_list|(
name|clazz
argument_list|)
return|;
name|Iterator
name|i
init|=
literal|null
decl_stmt|;
try|try
block|{
name|i
operator|=
name|clazz
operator|.
name|getAssignments
argument_list|()
operator|.
name|iterator
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ObjectNotFoundException
name|e
parameter_list|)
block|{
operator|new
name|_RootDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|refresh
argument_list|(
name|clazz
argument_list|)
expr_stmt|;
name|i
operator|=
name|clazz
operator|.
name|getAssignments
argument_list|()
operator|.
name|iterator
argument_list|()
expr_stmt|;
block|}
while|while
condition|(
name|i
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|Assignment
name|a
init|=
operator|(
name|Assignment
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|solutionId
operator|.
name|equals
argument_list|(
name|a
operator|.
name|getSolution
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
condition|)
return|return
name|a
return|;
block|}
return|return
literal|null
return|;
comment|/*     	String subjectName = clazz.getSchedulingSubpart().getInstrOfferingConfig().getControllingCourseOffering().getSubjectAreaAbbv();     	if (iCachedSubjects.add(subjectName+"."+solutionId)) {         	Query q = (new AssignmentDAO()).getSession().createQuery(     				"select distinct a from Assignment as a inner join a.clazz.schedulingSubpart.instrOfferingConfig.instructionalOffering.courseOfferings as o where " +     				"a.solution.uniqueId=:solutionId and " +     				"o.isControl=true and o.subjectAreaAbbv=:subjectName");     		q.setInteger("solutionId",solutionId.intValue());     		q.setString("subjectName",subjectName);     		for (Iterator i=q.list().iterator();i.hasNext();) {     			Assignment a = (Assignment)i.next();     			a.getAssignmentInfo("AssignmentInfo"); //force loading assignment info     			iCachedAssignments.put(a.getClassId(),a);     		}     	} 		return (Assignment)iCachedAssignments.get(clazz.getUniqueId()); 		*/
block|}
specifier|public
name|AssignmentPreferenceInfo
name|getAssignmentInfo
parameter_list|(
name|Class_
name|clazz
parameter_list|)
throws|throws
name|Exception
block|{
name|Long
name|solutionId
init|=
name|getSolutionId
argument_list|(
name|clazz
argument_list|)
decl_stmt|;
if|if
condition|(
name|solutionId
operator|==
literal|null
condition|)
return|return
name|super
operator|.
name|getAssignmentInfo
argument_list|(
name|clazz
argument_list|)
return|;
name|Assignment
name|a
init|=
name|getAssignment
argument_list|(
name|clazz
argument_list|)
decl_stmt|;
return|return
operator|(
name|a
operator|==
literal|null
condition|?
literal|null
else|:
operator|(
name|AssignmentPreferenceInfo
operator|)
name|a
operator|.
name|getAssignmentInfo
argument_list|(
literal|"AssignmentInfo"
argument_list|)
operator|)
return|;
block|}
specifier|public
name|Set
name|getSolutionIds
parameter_list|()
block|{
return|return
name|iSolutionIds
return|;
block|}
specifier|public
name|boolean
name|equals
parameter_list|(
name|Collection
name|solutionIds
parameter_list|)
block|{
if|if
condition|(
name|solutionIds
operator|.
name|size
argument_list|()
operator|!=
name|iSolutionIds
operator|.
name|size
argument_list|()
condition|)
return|return
literal|false
return|;
for|for
control|(
name|Iterator
name|i
init|=
name|solutionIds
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
name|Long
name|solutionId
init|=
operator|(
name|Long
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|iSolutionIds
operator|.
name|contains
argument_list|(
name|solutionId
argument_list|)
condition|)
return|return
literal|false
return|;
block|}
return|return
literal|true
return|;
block|}
block|}
end_class

end_unit

