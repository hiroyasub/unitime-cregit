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
name|form
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
name|Collection
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
name|Comparator
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
name|Enumeration
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
name|java
operator|.
name|util
operator|.
name|StringTokenizer
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
name|net
operator|.
name|sf
operator|.
name|cpsolver
operator|.
name|ifs
operator|.
name|util
operator|.
name|Progress
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
name|SolverPredefinedSetting
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
name|solver
operator|.
name|SolverProxy
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
name|WebSolver
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
name|LogInfo
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
name|PropertiesInfo
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
name|Formats
import|;
end_import

begin_comment
comment|/**   * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|ListSolutionsForm
extends|extends
name|ActionForm
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|632293328433911455L
decl_stmt|;
specifier|private
specifier|static
name|Formats
operator|.
name|Format
argument_list|<
name|Date
argument_list|>
name|sDF
init|=
name|Formats
operator|.
name|getDateFormat
argument_list|(
name|Formats
operator|.
name|Pattern
operator|.
name|DATE_TIME_STAMP
argument_list|)
decl_stmt|;
specifier|private
name|Vector
name|iMessages
init|=
operator|new
name|Vector
argument_list|()
decl_stmt|;
specifier|private
name|String
name|iOp
init|=
literal|null
decl_stmt|;
specifier|private
name|Long
name|iEmptySetting
init|=
literal|null
decl_stmt|;
specifier|private
name|Vector
name|iSettings
init|=
literal|null
decl_stmt|;
specifier|private
name|SolverProxy
name|iSolver
init|=
literal|null
decl_stmt|;
specifier|private
name|String
name|iHost
init|=
literal|null
decl_stmt|;
specifier|private
name|String
name|iHostEmpty
init|=
literal|null
decl_stmt|;
specifier|private
name|Long
name|iOwnerId
init|=
literal|null
decl_stmt|;
specifier|private
name|Vector
name|iSolutionBeans
init|=
operator|new
name|Vector
argument_list|()
decl_stmt|;
specifier|private
name|int
name|iSelectedSolutionBean
init|=
operator|-
literal|1
decl_stmt|;
specifier|private
name|Long
name|iSetting
init|=
literal|null
decl_stmt|;
specifier|private
name|String
name|iNote
init|=
literal|null
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
name|iSelectedSolutionBean
operator|<
literal|0
operator|||
name|iSelectedSolutionBean
operator|>=
name|iSolutionBeans
operator|.
name|size
argument_list|()
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"selectedSolutionBean"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.general"
argument_list|,
literal|"No solution selected"
argument_list|)
argument_list|)
expr_stmt|;
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
name|iNote
operator|=
literal|null
expr_stmt|;
name|iSolutionBeans
operator|.
name|clear
argument_list|()
expr_stmt|;
name|iSelectedSolutionBean
operator|=
operator|-
literal|1
expr_stmt|;
name|iSettings
operator|=
name|SolverPredefinedSetting
operator|.
name|getIdValueList
argument_list|(
name|SolverPredefinedSetting
operator|.
name|APPEARANCE_TIMETABLES
argument_list|)
expr_stmt|;
name|iEmptySetting
operator|=
literal|null
expr_stmt|;
name|iSetting
operator|=
literal|null
expr_stmt|;
if|if
condition|(
name|iSettings
operator|!=
literal|null
operator|&&
operator|!
name|iSettings
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|iEmptySetting
operator|=
operator|(
operator|(
name|SolverPredefinedSetting
operator|.
name|IdValue
operator|)
name|iSettings
operator|.
name|firstElement
argument_list|()
operator|)
operator|.
name|getId
argument_list|()
expr_stmt|;
name|iSetting
operator|=
name|iEmptySetting
expr_stmt|;
block|}
name|iSolver
operator|=
name|WebSolver
operator|.
name|getSolver
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|)
expr_stmt|;
name|iMessages
operator|.
name|clear
argument_list|()
expr_stmt|;
name|SolverProxy
name|solver
init|=
name|WebSolver
operator|.
name|getSolver
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|)
decl_stmt|;
name|iHost
operator|=
operator|(
name|solver
operator|==
literal|null
condition|?
literal|"auto"
else|:
name|solver
operator|.
name|getHost
argument_list|()
operator|)
expr_stmt|;
name|iHostEmpty
operator|=
name|iHost
expr_stmt|;
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
name|setNote
parameter_list|(
name|String
name|note
parameter_list|)
block|{
name|iNote
operator|=
name|note
expr_stmt|;
block|}
specifier|public
name|String
name|getNote
parameter_list|()
block|{
return|return
name|iNote
return|;
block|}
specifier|public
name|Vector
name|getSettings
parameter_list|()
block|{
return|return
name|iSettings
return|;
block|}
specifier|public
name|void
name|setSettings
parameter_list|(
name|Vector
name|settings
parameter_list|)
block|{
name|iSettings
operator|=
name|settings
expr_stmt|;
block|}
specifier|public
name|Long
name|getEmptySetting
parameter_list|()
block|{
return|return
name|iEmptySetting
return|;
block|}
specifier|public
name|void
name|setEmptySetting
parameter_list|(
name|Long
name|setting
parameter_list|)
block|{
name|iEmptySetting
operator|=
name|setting
expr_stmt|;
block|}
specifier|public
name|boolean
name|getHasSettings
parameter_list|()
block|{
return|return
operator|(
name|iSettings
operator|!=
literal|null
operator|&&
operator|!
name|iSettings
operator|.
name|isEmpty
argument_list|()
operator|)
return|;
block|}
specifier|public
name|void
name|setSolver
parameter_list|(
name|SolverProxy
name|solver
parameter_list|)
block|{
name|iSolver
operator|=
name|solver
expr_stmt|;
block|}
specifier|public
name|SolverProxy
name|getSolver
parameter_list|()
block|{
return|return
name|iSolver
return|;
block|}
specifier|public
name|String
name|getSolverNote
parameter_list|()
throws|throws
name|Exception
block|{
if|if
condition|(
name|iSolver
operator|==
literal|null
condition|)
return|return
literal|null
return|;
return|return
name|iSolver
operator|.
name|getNote
argument_list|()
return|;
block|}
specifier|public
name|void
name|setSolverNote
parameter_list|(
name|String
name|note
parameter_list|)
throws|throws
name|Exception
block|{
if|if
condition|(
name|iSolver
operator|!=
literal|null
condition|)
name|iSolver
operator|.
name|setNote
argument_list|(
name|note
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
class|class
name|InfoComparator
implements|implements
name|Comparator
block|{
specifier|private
specifier|static
name|Vector
name|sInfoKeys
init|=
literal|null
decl_stmt|;
static|static
block|{
name|sInfoKeys
operator|=
operator|new
name|Vector
argument_list|()
expr_stmt|;
name|sInfoKeys
operator|.
name|add
argument_list|(
literal|"Assigned variables"
argument_list|)
expr_stmt|;
name|sInfoKeys
operator|.
name|add
argument_list|(
literal|"Overall solution value"
argument_list|)
expr_stmt|;
name|sInfoKeys
operator|.
name|add
argument_list|(
literal|"Time preferences"
argument_list|)
expr_stmt|;
name|sInfoKeys
operator|.
name|add
argument_list|(
literal|"Student conflicts"
argument_list|)
expr_stmt|;
name|sInfoKeys
operator|.
name|add
argument_list|(
literal|"Room preferences"
argument_list|)
expr_stmt|;
name|sInfoKeys
operator|.
name|add
argument_list|(
literal|"Distribution preferences"
argument_list|)
expr_stmt|;
name|sInfoKeys
operator|.
name|add
argument_list|(
literal|"Back-to-back instructor preferences"
argument_list|)
expr_stmt|;
name|sInfoKeys
operator|.
name|add
argument_list|(
literal|"Too big rooms"
argument_list|)
expr_stmt|;
name|sInfoKeys
operator|.
name|add
argument_list|(
literal|"Useless half-hours"
argument_list|)
expr_stmt|;
name|sInfoKeys
operator|.
name|add
argument_list|(
literal|"Same subpart balancing penalty"
argument_list|)
expr_stmt|;
name|sInfoKeys
operator|.
name|add
argument_list|(
literal|"Department balancing penalty"
argument_list|)
expr_stmt|;
name|sInfoKeys
operator|.
name|add
argument_list|(
literal|"Direct Conflicts"
argument_list|)
expr_stmt|;
name|sInfoKeys
operator|.
name|add
argument_list|(
literal|"More Than 2 A Day Conflicts"
argument_list|)
expr_stmt|;
name|sInfoKeys
operator|.
name|add
argument_list|(
literal|"Back-To-Back Conflicts"
argument_list|)
expr_stmt|;
name|sInfoKeys
operator|.
name|add
argument_list|(
literal|"Distance Back-To-Back Conflicts"
argument_list|)
expr_stmt|;
name|sInfoKeys
operator|.
name|add
argument_list|(
literal|"Instructor Direct Conflicts"
argument_list|)
expr_stmt|;
name|sInfoKeys
operator|.
name|add
argument_list|(
literal|"Instructor More Than 2 A Day Conflicts"
argument_list|)
expr_stmt|;
name|sInfoKeys
operator|.
name|add
argument_list|(
literal|"Instructor Back-To-Back Conflicts"
argument_list|)
expr_stmt|;
name|sInfoKeys
operator|.
name|add
argument_list|(
literal|"Instructor Distance Back-To-Back Conflicts"
argument_list|)
expr_stmt|;
name|sInfoKeys
operator|.
name|add
argument_list|(
literal|"Period Penalty"
argument_list|)
expr_stmt|;
name|sInfoKeys
operator|.
name|add
argument_list|(
literal|"Period&times;Size Penalty"
argument_list|)
expr_stmt|;
name|sInfoKeys
operator|.
name|add
argument_list|(
literal|"Exam Rotation Penalty"
argument_list|)
expr_stmt|;
name|sInfoKeys
operator|.
name|add
argument_list|(
literal|"Average Period"
argument_list|)
expr_stmt|;
name|sInfoKeys
operator|.
name|add
argument_list|(
literal|"Room Penalty"
argument_list|)
expr_stmt|;
name|sInfoKeys
operator|.
name|add
argument_list|(
literal|"Room Split Penalty"
argument_list|)
expr_stmt|;
name|sInfoKeys
operator|.
name|add
argument_list|(
literal|"Room Split Distance Penalty"
argument_list|)
expr_stmt|;
name|sInfoKeys
operator|.
name|add
argument_list|(
literal|"Room Size Penalty"
argument_list|)
expr_stmt|;
name|sInfoKeys
operator|.
name|add
argument_list|(
literal|"Not-Original Room Penalty"
argument_list|)
expr_stmt|;
name|sInfoKeys
operator|.
name|add
argument_list|(
literal|"Distribution Penalty"
argument_list|)
expr_stmt|;
name|sInfoKeys
operator|.
name|add
argument_list|(
literal|"Large Exams Penalty"
argument_list|)
expr_stmt|;
name|sInfoKeys
operator|.
name|add
argument_list|(
literal|"Perturbation Penalty"
argument_list|)
expr_stmt|;
name|sInfoKeys
operator|.
name|add
argument_list|(
literal|"Perturbation penalty"
argument_list|)
expr_stmt|;
name|sInfoKeys
operator|.
name|add
argument_list|(
literal|"Room Perturbation Penalty"
argument_list|)
expr_stmt|;
name|sInfoKeys
operator|.
name|add
argument_list|(
literal|"Perturbation variables"
argument_list|)
expr_stmt|;
name|sInfoKeys
operator|.
name|add
argument_list|(
literal|"Perturbations: Total penalty"
argument_list|)
expr_stmt|;
name|sInfoKeys
operator|.
name|add
argument_list|(
literal|"Time"
argument_list|)
expr_stmt|;
name|sInfoKeys
operator|.
name|add
argument_list|(
literal|"Iteration"
argument_list|)
expr_stmt|;
name|sInfoKeys
operator|.
name|add
argument_list|(
literal|"Memory usage"
argument_list|)
expr_stmt|;
name|sInfoKeys
operator|.
name|add
argument_list|(
literal|"Speed"
argument_list|)
expr_stmt|;
block|}
specifier|public
name|int
name|compare
parameter_list|(
name|Object
name|o1
parameter_list|,
name|Object
name|o2
parameter_list|)
block|{
name|String
name|key1
init|=
operator|(
name|String
operator|)
name|o1
decl_stmt|;
name|String
name|key2
init|=
operator|(
name|String
operator|)
name|o2
decl_stmt|;
name|int
name|i1
init|=
name|sInfoKeys
operator|.
name|indexOf
argument_list|(
name|key1
argument_list|)
decl_stmt|;
name|int
name|i2
init|=
name|sInfoKeys
operator|.
name|indexOf
argument_list|(
name|key2
argument_list|)
decl_stmt|;
if|if
condition|(
name|i1
operator|<
literal|0
condition|)
block|{
if|if
condition|(
name|i2
operator|<
literal|0
condition|)
return|return
name|key1
operator|.
name|compareTo
argument_list|(
name|key2
argument_list|)
return|;
else|else
return|return
literal|1
return|;
block|}
if|else if
condition|(
name|i2
operator|<
literal|0
condition|)
return|return
operator|-
literal|1
return|;
return|return
operator|(
name|i1
operator|<
name|i2
condition|?
operator|-
literal|1
else|:
literal|1
operator|)
return|;
block|}
block|}
specifier|public
name|Vector
name|getMessages
parameter_list|()
block|{
return|return
name|iMessages
return|;
block|}
specifier|public
name|String
name|getHost
parameter_list|()
block|{
return|return
name|iHost
return|;
block|}
specifier|public
name|void
name|setHost
parameter_list|(
name|String
name|host
parameter_list|)
block|{
name|iHost
operator|=
name|host
expr_stmt|;
block|}
specifier|public
name|String
name|getHostEmpty
parameter_list|()
block|{
return|return
name|iHostEmpty
return|;
block|}
specifier|public
name|void
name|setHostEmpty
parameter_list|(
name|String
name|hostEmpty
parameter_list|)
block|{
name|iHostEmpty
operator|=
name|hostEmpty
expr_stmt|;
block|}
specifier|public
name|Long
name|getOwnerId
parameter_list|()
block|{
return|return
name|iOwnerId
return|;
block|}
specifier|public
name|void
name|setOwnerId
parameter_list|(
name|Long
name|ownerId
parameter_list|)
block|{
name|iOwnerId
operator|=
name|ownerId
expr_stmt|;
block|}
specifier|public
name|Long
name|getSetting
parameter_list|()
block|{
return|return
name|iSetting
return|;
block|}
specifier|public
name|void
name|setSetting
parameter_list|(
name|Long
name|setting
parameter_list|)
block|{
name|iSetting
operator|=
name|setting
expr_stmt|;
block|}
specifier|public
name|Vector
name|getSolutionBeans
parameter_list|()
block|{
return|return
name|iSolutionBeans
return|;
block|}
specifier|public
name|void
name|setSolutionBeans
parameter_list|(
name|Vector
name|solutionBeans
parameter_list|)
block|{
name|iSolutionBeans
operator|=
name|solutionBeans
expr_stmt|;
block|}
specifier|public
name|int
name|getSelectedSolutionBean
parameter_list|()
block|{
return|return
name|iSelectedSolutionBean
return|;
block|}
specifier|public
name|void
name|setSelectedSolutionBean
parameter_list|(
name|int
name|selectedSolutionBean
parameter_list|)
block|{
name|iSelectedSolutionBean
operator|=
name|selectedSolutionBean
expr_stmt|;
block|}
specifier|public
name|void
name|addSolution
parameter_list|(
name|Solution
name|solution
parameter_list|)
throws|throws
name|Exception
block|{
name|SolutionBean
name|solutionBean
init|=
literal|null
decl_stmt|;
for|for
control|(
name|Enumeration
name|e
init|=
name|iSolutionBeans
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
name|SolutionBean
name|sb
init|=
operator|(
name|SolutionBean
operator|)
name|e
operator|.
name|nextElement
argument_list|()
decl_stmt|;
if|if
condition|(
name|sb
operator|.
name|getOwnerId
argument_list|()
operator|.
name|equals
argument_list|(
name|solution
operator|.
name|getOwner
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
condition|)
block|{
name|solutionBean
operator|=
name|sb
expr_stmt|;
break|break;
block|}
block|}
if|if
condition|(
name|solutionBean
operator|!=
literal|null
condition|)
name|iSolutionBeans
operator|.
name|remove
argument_list|(
name|solutionBean
argument_list|)
expr_stmt|;
name|iSolutionBeans
operator|.
name|addElement
argument_list|(
operator|new
name|SolutionBean
argument_list|(
name|solution
argument_list|)
argument_list|)
expr_stmt|;
block|}
specifier|public
name|String
name|getSolutionId
parameter_list|()
block|{
name|String
name|solutionId
init|=
literal|""
decl_stmt|;
for|for
control|(
name|Enumeration
name|e
init|=
name|iSolutionBeans
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
name|SolutionBean
name|sb
init|=
operator|(
name|SolutionBean
operator|)
name|e
operator|.
name|nextElement
argument_list|()
decl_stmt|;
name|solutionId
operator|+=
name|sb
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
expr_stmt|;
if|if
condition|(
name|e
operator|.
name|hasMoreElements
argument_list|()
condition|)
name|solutionId
operator|+=
literal|","
expr_stmt|;
block|}
return|return
name|solutionId
return|;
block|}
specifier|public
name|void
name|setSolutionId
parameter_list|(
name|String
name|solutionId
parameter_list|)
throws|throws
name|Exception
block|{
name|iSolutionBeans
operator|.
name|clear
argument_list|()
expr_stmt|;
if|if
condition|(
name|solutionId
operator|==
literal|null
operator|||
name|solutionId
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
return|return;
for|for
control|(
name|StringTokenizer
name|s
init|=
operator|new
name|StringTokenizer
argument_list|(
name|solutionId
argument_list|,
literal|","
argument_list|)
init|;
name|s
operator|.
name|hasMoreTokens
argument_list|()
condition|;
control|)
block|{
name|Long
name|id
init|=
name|Long
operator|.
name|valueOf
argument_list|(
name|s
operator|.
name|nextToken
argument_list|()
argument_list|)
decl_stmt|;
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
name|id
argument_list|)
decl_stmt|;
if|if
condition|(
name|solution
operator|!=
literal|null
condition|)
name|iSolutionBeans
operator|.
name|addElement
argument_list|(
operator|new
name|SolutionBean
argument_list|(
name|solution
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|void
name|removeSolution
parameter_list|(
name|Long
name|solutionId
parameter_list|)
block|{
name|SolutionBean
name|solutionBean
init|=
literal|null
decl_stmt|;
for|for
control|(
name|Enumeration
name|e
init|=
name|iSolutionBeans
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
name|SolutionBean
name|sb
init|=
operator|(
name|SolutionBean
operator|)
name|e
operator|.
name|nextElement
argument_list|()
decl_stmt|;
if|if
condition|(
name|sb
operator|.
name|getUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
name|solutionId
argument_list|)
condition|)
block|{
name|solutionBean
operator|=
name|sb
expr_stmt|;
break|break;
block|}
block|}
if|if
condition|(
name|solutionBean
operator|!=
literal|null
condition|)
name|iSolutionBeans
operator|.
name|remove
argument_list|(
name|solutionBean
argument_list|)
expr_stmt|;
block|}
specifier|public
name|SolutionBean
name|getSolutionBean
parameter_list|()
block|{
if|if
condition|(
name|iSelectedSolutionBean
operator|<
literal|0
condition|)
return|return
literal|null
return|;
try|try
block|{
return|return
operator|(
name|SolutionBean
operator|)
name|iSolutionBeans
operator|.
name|elementAt
argument_list|(
name|iSelectedSolutionBean
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|ArrayIndexOutOfBoundsException
name|e
parameter_list|)
block|{
return|return
literal|null
return|;
block|}
block|}
specifier|public
name|SolutionBean
name|getSolutionBean
parameter_list|(
name|Long
name|solutionId
parameter_list|)
block|{
for|for
control|(
name|Enumeration
name|e
init|=
name|iSolutionBeans
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
name|SolutionBean
name|sb
init|=
operator|(
name|SolutionBean
operator|)
name|e
operator|.
name|nextElement
argument_list|()
decl_stmt|;
if|if
condition|(
name|sb
operator|.
name|getUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
name|solutionId
argument_list|)
condition|)
return|return
name|sb
return|;
block|}
return|return
literal|null
return|;
block|}
specifier|public
name|Long
index|[]
name|getOwnerIds
parameter_list|()
block|{
name|Long
index|[]
name|ret
init|=
operator|new
name|Long
index|[
name|iSolutionBeans
operator|.
name|size
argument_list|()
index|]
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|iSolutionBeans
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
name|ret
index|[
name|i
index|]
operator|=
operator|(
operator|(
name|SolutionBean
operator|)
name|iSolutionBeans
operator|.
name|elementAt
argument_list|(
name|i
argument_list|)
operator|)
operator|.
name|getOwnerId
argument_list|()
expr_stmt|;
return|return
name|ret
return|;
block|}
specifier|public
specifier|static
class|class
name|SolutionBean
implements|implements
name|Serializable
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
specifier|private
name|Long
name|iUniqueId
init|=
literal|null
decl_stmt|;
specifier|private
name|String
name|iCreated
init|=
literal|null
decl_stmt|;
specifier|private
name|String
name|iCommited
init|=
literal|null
decl_stmt|;
specifier|private
name|String
name|iOwner
init|=
literal|null
decl_stmt|;
specifier|private
name|String
name|iNote
init|=
literal|null
decl_stmt|;
specifier|private
name|boolean
name|iValid
init|=
literal|false
decl_stmt|;
specifier|private
name|String
name|iLog
init|=
literal|null
decl_stmt|;
specifier|private
name|Properties
name|iGlobalInfo
init|=
literal|null
decl_stmt|;
specifier|private
name|Long
name|iOwnerId
init|=
literal|null
decl_stmt|;
specifier|public
name|SolutionBean
parameter_list|()
block|{
block|}
specifier|public
name|SolutionBean
parameter_list|(
name|Solution
name|solution
parameter_list|)
throws|throws
name|Exception
block|{
name|this
argument_list|()
expr_stmt|;
name|setSolution
argument_list|(
name|solution
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|setSolution
parameter_list|(
name|Solution
name|solution
parameter_list|)
throws|throws
name|Exception
block|{
name|iUniqueId
operator|=
name|solution
operator|.
name|getUniqueId
argument_list|()
expr_stmt|;
name|iGlobalInfo
operator|=
operator|(
name|PropertiesInfo
operator|)
name|solution
operator|.
name|getInfo
argument_list|(
literal|"GlobalInfo"
argument_list|)
expr_stmt|;
name|LogInfo
name|logInfo
init|=
operator|(
name|LogInfo
operator|)
name|solution
operator|.
name|getInfo
argument_list|(
literal|"LogInfo"
argument_list|)
decl_stmt|;
name|iLog
operator|=
operator|(
name|logInfo
operator|==
literal|null
condition|?
literal|null
else|:
name|logInfo
operator|.
name|getHtmlLog
argument_list|(
name|Progress
operator|.
name|MSGLEVEL_WARN
argument_list|,
literal|false
argument_list|,
literal|"Loading input data ..."
argument_list|)
operator|)
expr_stmt|;
name|iCreated
operator|=
name|sDF
operator|.
name|format
argument_list|(
name|solution
operator|.
name|getCreated
argument_list|()
argument_list|)
expr_stmt|;
name|iCommited
operator|=
operator|(
name|solution
operator|.
name|isCommited
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|?
name|sDF
operator|.
name|format
argument_list|(
name|solution
operator|.
name|getCommitDate
argument_list|()
argument_list|)
else|:
literal|""
operator|)
expr_stmt|;
name|iNote
operator|=
name|solution
operator|.
name|getNote
argument_list|()
expr_stmt|;
name|iOwner
operator|=
name|solution
operator|.
name|getOwner
argument_list|()
operator|.
name|getName
argument_list|()
expr_stmt|;
name|iOwnerId
operator|=
name|solution
operator|.
name|getOwner
argument_list|()
operator|.
name|getUniqueId
argument_list|()
expr_stmt|;
name|iValid
operator|=
name|solution
operator|.
name|isValid
argument_list|()
operator|.
name|booleanValue
argument_list|()
expr_stmt|;
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
name|setCreated
parameter_list|(
name|String
name|created
parameter_list|)
block|{
name|iCreated
operator|=
name|created
expr_stmt|;
block|}
specifier|public
name|String
name|getCreated
parameter_list|()
block|{
return|return
name|iCreated
return|;
block|}
specifier|public
name|void
name|setCommited
parameter_list|(
name|String
name|commited
parameter_list|)
block|{
name|iCommited
operator|=
name|commited
expr_stmt|;
block|}
specifier|public
name|String
name|getCommited
parameter_list|()
block|{
return|return
name|iCommited
return|;
block|}
specifier|public
name|void
name|setOwner
parameter_list|(
name|String
name|owner
parameter_list|)
block|{
name|iOwner
operator|=
name|owner
expr_stmt|;
block|}
specifier|public
name|String
name|getOwner
parameter_list|()
block|{
return|return
name|iOwner
return|;
block|}
specifier|public
name|void
name|setOwnerId
parameter_list|(
name|Long
name|ownerId
parameter_list|)
block|{
name|iOwnerId
operator|=
name|ownerId
expr_stmt|;
block|}
specifier|public
name|Long
name|getOwnerId
parameter_list|()
block|{
return|return
name|iOwnerId
return|;
block|}
specifier|public
name|void
name|setNote
parameter_list|(
name|String
name|note
parameter_list|)
block|{
name|iNote
operator|=
name|note
expr_stmt|;
block|}
specifier|public
name|String
name|getNote
parameter_list|()
block|{
return|return
name|iNote
return|;
block|}
specifier|public
name|void
name|setValid
parameter_list|(
name|boolean
name|valid
parameter_list|)
block|{
name|iValid
operator|=
name|valid
expr_stmt|;
block|}
specifier|public
name|boolean
name|getValid
parameter_list|()
block|{
return|return
name|iValid
return|;
block|}
specifier|public
name|String
name|getLog
parameter_list|()
block|{
return|return
name|iLog
return|;
block|}
specifier|public
name|void
name|setLog
parameter_list|(
name|String
name|log
parameter_list|)
block|{
name|iLog
operator|=
name|log
expr_stmt|;
block|}
specifier|public
name|void
name|setGlobalInfo
parameter_list|(
name|Properties
name|globalInfo
parameter_list|)
block|{
name|iGlobalInfo
operator|=
name|globalInfo
expr_stmt|;
block|}
specifier|public
name|Properties
name|getGlobalInfo
parameter_list|()
block|{
return|return
name|iGlobalInfo
return|;
block|}
specifier|public
name|String
name|getInfo
parameter_list|(
name|String
name|key
parameter_list|)
block|{
try|try
block|{
return|return
name|iGlobalInfo
operator|.
name|getProperty
argument_list|(
name|key
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
return|return
literal|null
return|;
block|}
specifier|public
name|void
name|setInfo
parameter_list|(
name|String
name|key
parameter_list|,
name|String
name|value
parameter_list|)
block|{
name|iGlobalInfo
operator|.
name|setProperty
argument_list|(
name|key
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Collection
name|getInfos
parameter_list|()
block|{
if|if
condition|(
name|iGlobalInfo
operator|==
literal|null
condition|)
return|return
operator|new
name|Vector
argument_list|()
return|;
name|Vector
name|infos
init|=
operator|new
name|Vector
argument_list|(
name|iGlobalInfo
operator|.
name|keySet
argument_list|()
argument_list|)
decl_stmt|;
name|Collections
operator|.
name|sort
argument_list|(
name|infos
argument_list|,
operator|new
name|InfoComparator
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|infos
return|;
block|}
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|iUniqueId
operator|.
name|hashCode
argument_list|()
return|;
block|}
specifier|public
name|boolean
name|equals
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
name|SolutionBean
operator|)
condition|)
return|return
literal|false
return|;
return|return
name|getUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
operator|(
operator|(
name|SolutionBean
operator|)
name|o
operator|)
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

