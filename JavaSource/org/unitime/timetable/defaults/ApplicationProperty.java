begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.4 - 3.5 (University Timetabling Application)  * Copyright (C) 2012 - 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|defaults
package|;
end_package

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_enum
specifier|public
enum|enum
name|ApplicationProperty
block|{
name|KeepLastUsedAcademicSession
argument_list|(
literal|"tmtbl.keeplastused.session"
argument_list|,
literal|"false"
argument_list|,
literal|"On login, automatically select the last used academic session."
argument_list|)
block|,
name|DistanceEllipsoid
argument_list|(
literal|"unitime.distance.ellipsoid"
argument_list|,
literal|"LEGACY"
argument_list|,
literal|"Distance matrix ellipsid"
argument_list|)
block|,
name|SolverMemoryLimit
argument_list|(
literal|"tmtbl.solver.mem_limit"
argument_list|,
literal|"200"
argument_list|,
literal|"Minimal amount of free memory (in MB) for the solver to load."
argument_list|)
block|, 	 	;
name|String
name|iKey
decl_stmt|,
name|iDefault
decl_stmt|,
name|iDescription
decl_stmt|;
name|ApplicationProperty
parameter_list|(
name|String
name|key
parameter_list|,
name|String
name|defaultValue
parameter_list|,
name|String
name|description
parameter_list|)
block|{
name|iKey
operator|=
name|key
expr_stmt|;
name|iDefault
operator|=
name|defaultValue
expr_stmt|;
name|iDescription
operator|=
name|defaultValue
expr_stmt|;
block|}
specifier|public
name|String
name|key
parameter_list|()
block|{
return|return
name|iKey
return|;
block|}
specifier|public
name|String
name|defaultValue
parameter_list|()
block|{
return|return
name|iDefault
return|;
block|}
specifier|public
name|String
name|description
parameter_list|()
block|{
return|return
name|iDescription
return|;
block|}
block|}
end_enum

end_unit

