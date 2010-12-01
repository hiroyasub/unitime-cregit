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
operator|.
name|base
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
name|JointEnrollment
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

begin_class
specifier|public
specifier|abstract
class|class
name|BaseJointEnrollment
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
decl_stmt|;
specifier|private
name|Double
name|iJenrl
decl_stmt|;
specifier|private
name|Solution
name|iSolution
decl_stmt|;
specifier|private
name|Class_
name|iClass1
decl_stmt|;
specifier|private
name|Class_
name|iClass2
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_UNIQUEID
init|=
literal|"uniqueId"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_JENRL
init|=
literal|"jenrl"
decl_stmt|;
specifier|public
name|BaseJointEnrollment
parameter_list|()
block|{
name|initialize
argument_list|()
expr_stmt|;
block|}
specifier|public
name|BaseJointEnrollment
parameter_list|(
name|Long
name|uniqueId
parameter_list|)
block|{
name|setUniqueId
argument_list|(
name|uniqueId
argument_list|)
expr_stmt|;
name|initialize
argument_list|()
expr_stmt|;
block|}
specifier|protected
name|void
name|initialize
parameter_list|()
block|{
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
name|Double
name|getJenrl
parameter_list|()
block|{
return|return
name|iJenrl
return|;
block|}
specifier|public
name|void
name|setJenrl
parameter_list|(
name|Double
name|jenrl
parameter_list|)
block|{
name|iJenrl
operator|=
name|jenrl
expr_stmt|;
block|}
specifier|public
name|Solution
name|getSolution
parameter_list|()
block|{
return|return
name|iSolution
return|;
block|}
specifier|public
name|void
name|setSolution
parameter_list|(
name|Solution
name|solution
parameter_list|)
block|{
name|iSolution
operator|=
name|solution
expr_stmt|;
block|}
specifier|public
name|Class_
name|getClass1
parameter_list|()
block|{
return|return
name|iClass1
return|;
block|}
specifier|public
name|void
name|setClass1
parameter_list|(
name|Class_
name|class1
parameter_list|)
block|{
name|iClass1
operator|=
name|class1
expr_stmt|;
block|}
specifier|public
name|Class_
name|getClass2
parameter_list|()
block|{
return|return
name|iClass2
return|;
block|}
specifier|public
name|void
name|setClass2
parameter_list|(
name|Class_
name|class2
parameter_list|)
block|{
name|iClass2
operator|=
name|class2
expr_stmt|;
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
name|JointEnrollment
operator|)
condition|)
return|return
literal|false
return|;
if|if
condition|(
name|getUniqueId
argument_list|()
operator|==
literal|null
operator|||
operator|(
operator|(
name|JointEnrollment
operator|)
name|o
operator|)
operator|.
name|getUniqueId
argument_list|()
operator|==
literal|null
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
name|JointEnrollment
operator|)
name|o
operator|)
operator|.
name|getUniqueId
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
if|if
condition|(
name|getUniqueId
argument_list|()
operator|==
literal|null
condition|)
return|return
name|super
operator|.
name|hashCode
argument_list|()
return|;
return|return
name|getUniqueId
argument_list|()
operator|.
name|hashCode
argument_list|()
return|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
literal|"JointEnrollment["
operator|+
name|getUniqueId
argument_list|()
operator|+
literal|"]"
return|;
block|}
specifier|public
name|String
name|toDebugString
parameter_list|()
block|{
return|return
literal|"JointEnrollment["
operator|+
literal|"\n	Class1: "
operator|+
name|getClass1
argument_list|()
operator|+
literal|"\n	Class2: "
operator|+
name|getClass2
argument_list|()
operator|+
literal|"\n	Jenrl: "
operator|+
name|getJenrl
argument_list|()
operator|+
literal|"\n	Solution: "
operator|+
name|getSolution
argument_list|()
operator|+
literal|"\n	UniqueId: "
operator|+
name|getUniqueId
argument_list|()
operator|+
literal|"]"
return|;
block|}
block|}
end_class

end_unit

