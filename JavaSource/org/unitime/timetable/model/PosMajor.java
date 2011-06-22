begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 (University Timetabling Application)  * Copyright (C) 2008 - 2010, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|Session
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
name|BasePosMajor
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
name|PosMajorDAO
import|;
end_import

begin_class
specifier|public
class|class
name|PosMajor
extends|extends
name|BasePosMajor
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
name|PosMajor
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|PosMajor
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
comment|/** Request attribute name for available pos majors **/
specifier|public
specifier|static
name|String
name|POSMAJOR_ATTR_NAME
init|=
literal|"posMajorList"
decl_stmt|;
comment|/** 	 * Retrieves all pos majors in the database for the academic session 	 * ordered by column name 	 * @param sessionId academic session 	 * @return Vector of PosMajor objects 	 */
specifier|public
specifier|static
name|List
name|getPosMajorList
parameter_list|(
name|Long
name|sessionId
parameter_list|)
block|{
name|Session
name|hibSession
init|=
operator|new
name|PosMajorDAO
argument_list|()
operator|.
name|getSession
argument_list|()
decl_stmt|;
name|String
name|query
init|=
literal|"from PosMajor where academicArea.sessionId=:acadSessionId order by name"
decl_stmt|;
name|Query
name|q
init|=
name|hibSession
operator|.
name|createQuery
argument_list|(
name|query
argument_list|)
decl_stmt|;
name|q
operator|.
name|setLong
argument_list|(
literal|"acadSessionId"
argument_list|,
name|sessionId
operator|.
name|longValue
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|q
operator|.
name|list
argument_list|()
return|;
block|}
comment|/**      * Creates label of the format Name - Code      * @return      */
specifier|public
name|String
name|getLabelNameCode
parameter_list|()
block|{
return|return
name|this
operator|.
name|getName
argument_list|()
operator|+
literal|" - "
operator|+
name|this
operator|.
name|getCode
argument_list|()
return|;
block|}
comment|/**      * Creates label of the format Code - Name      * @return      */
specifier|public
name|String
name|getLabelCodeName
parameter_list|()
block|{
return|return
name|this
operator|.
name|getCode
argument_list|()
operator|+
literal|" - "
operator|+
name|this
operator|.
name|getName
argument_list|()
return|;
block|}
specifier|public
specifier|static
name|PosMajor
name|findByCode
parameter_list|(
name|Long
name|sessionId
parameter_list|,
name|String
name|code
parameter_list|)
block|{
return|return
operator|(
name|PosMajor
operator|)
operator|new
name|PosMajorDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select a from PosMajor a where "
operator|+
literal|"a.session.uniqueId=:sessionId and "
operator|+
literal|"a.code=:code"
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
literal|"code"
argument_list|,
name|code
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
name|PosMajor
name|findByExternalIdAcadAreaExternalId
parameter_list|(
name|Long
name|sessionId
parameter_list|,
name|String
name|externalId
parameter_list|,
name|String
name|academicArea
parameter_list|)
block|{
return|return
operator|(
name|PosMajor
operator|)
operator|new
name|PosMajorDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select a from PosMajor a inner join a.academicAreas as areas where "
operator|+
literal|"a.session.uniqueId=:sessionId and "
operator|+
literal|"a.externalUniqueId=:externalUniqueId"
operator|+
literal|"areas.externalUniqueId = :academicArea"
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
literal|"externalUniqueId"
argument_list|,
name|externalId
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
name|PosMajor
name|findByCodeAcadAreaId
parameter_list|(
name|Long
name|sessionId
parameter_list|,
name|String
name|code
parameter_list|,
name|Long
name|areaId
parameter_list|)
block|{
if|if
condition|(
name|areaId
operator|==
literal|null
condition|)
return|return
name|findByCode
argument_list|(
name|sessionId
argument_list|,
name|code
argument_list|)
return|;
return|return
operator|(
name|PosMajor
operator|)
operator|new
name|PosMajorDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select p from PosMajor p inner join p.academicAreas a where "
operator|+
literal|"p.session.uniqueId=:sessionId and "
operator|+
literal|"a.uniqueId=:areaId and p.code=:code"
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
name|setLong
argument_list|(
literal|"areaId"
argument_list|,
name|areaId
operator|.
name|longValue
argument_list|()
argument_list|)
operator|.
name|setString
argument_list|(
literal|"code"
argument_list|,
name|code
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
name|PosMajor
name|findByCodeAcadAreaAbbv
parameter_list|(
name|Long
name|sessionId
parameter_list|,
name|String
name|code
parameter_list|,
name|String
name|areaAbbv
parameter_list|)
block|{
if|if
condition|(
name|areaAbbv
operator|==
literal|null
operator|||
name|areaAbbv
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
return|return
name|findByCode
argument_list|(
name|sessionId
argument_list|,
name|code
argument_list|)
return|;
return|return
operator|(
name|PosMajor
operator|)
operator|new
name|PosMajorDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select p from PosMajor p inner join p.academicAreas a where "
operator|+
literal|"p.session.uniqueId=:sessionId and "
operator|+
literal|"a.academicAreaAbbreviation=:areaAbbv and p.code=:code"
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
literal|"areaAbbv"
argument_list|,
name|areaAbbv
argument_list|)
operator|.
name|setString
argument_list|(
literal|"code"
argument_list|,
name|code
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
name|Object
name|clone
parameter_list|()
block|{
name|PosMajor
name|m
init|=
operator|new
name|PosMajor
argument_list|()
decl_stmt|;
name|m
operator|.
name|setExternalUniqueId
argument_list|(
name|getExternalUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|m
operator|.
name|setCode
argument_list|(
name|getCode
argument_list|()
argument_list|)
expr_stmt|;
name|m
operator|.
name|setName
argument_list|(
name|getName
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|m
return|;
block|}
block|}
end_class

end_unit

