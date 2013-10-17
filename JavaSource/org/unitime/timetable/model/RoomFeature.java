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
name|List
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
name|org
operator|.
name|hibernate
operator|.
name|HibernateException
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
name|BaseRoomFeature
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
name|RoomFeatureDAO
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|RoomFeature
extends|extends
name|BaseRoomFeature
implements|implements
name|Comparable
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
name|RoomFeature
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|RoomFeature
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
name|String
name|featureTypeDisplayString
parameter_list|()
block|{
return|return
literal|""
return|;
block|}
specifier|public
specifier|static
name|List
argument_list|<
name|GlobalRoomFeature
argument_list|>
name|getAllGlobalRoomFeatures
parameter_list|(
name|Long
name|sessionId
parameter_list|)
throws|throws
name|HibernateException
block|{
return|return
operator|(
name|List
argument_list|<
name|GlobalRoomFeature
argument_list|>
operator|)
name|RoomFeatureDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"from GlobalRoomFeature rf where rf.session.uniqueId = :sessionId order by label"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
return|;
block|}
specifier|public
specifier|static
name|List
argument_list|<
name|GlobalRoomFeature
argument_list|>
name|getAllGlobalRoomFeatures
parameter_list|(
name|Long
name|sessionId
parameter_list|,
name|Long
name|featureTypeId
parameter_list|)
throws|throws
name|HibernateException
block|{
if|if
condition|(
name|featureTypeId
operator|==
literal|null
operator|||
name|featureTypeId
operator|<
literal|0
condition|)
block|{
return|return
operator|(
name|List
argument_list|<
name|GlobalRoomFeature
argument_list|>
operator|)
name|RoomFeatureDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"from GlobalRoomFeature rf where rf.session.uniqueId = :sessionId and rf.featureType is null order by label"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
return|;
block|}
else|else
block|{
return|return
operator|(
name|List
argument_list|<
name|GlobalRoomFeature
argument_list|>
operator|)
name|RoomFeatureDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"from GlobalRoomFeature rf where rf.session.uniqueId = :sessionId and rf.featureType = :featureTypeId order by label"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"featureTypeId"
argument_list|,
name|featureTypeId
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
return|;
block|}
block|}
specifier|public
specifier|static
name|List
argument_list|<
name|GlobalRoomFeature
argument_list|>
name|getAllGlobalRoomFeatures
parameter_list|(
name|Session
name|session
parameter_list|)
throws|throws
name|HibernateException
block|{
return|return
name|getAllGlobalRoomFeatures
argument_list|(
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|List
argument_list|<
name|DepartmentRoomFeature
argument_list|>
name|getAllDepartmentRoomFeatures
parameter_list|(
name|Department
name|dept
parameter_list|)
throws|throws
name|HibernateException
block|{
if|if
condition|(
name|dept
operator|==
literal|null
condition|)
return|return
literal|null
return|;
return|return
operator|(
name|List
argument_list|<
name|DepartmentRoomFeature
argument_list|>
operator|)
name|RoomFeatureDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"from DepartmentRoomFeature rf where rf.department.uniqueId = :deptId order by label"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"deptId"
argument_list|,
name|dept
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
return|;
block|}
comment|/** 	 * @param id 	 * @return 	 * @throws HibernateException 	 */
specifier|public
specifier|static
name|RoomFeature
name|getRoomFeatureById
parameter_list|(
name|Long
name|id
parameter_list|)
throws|throws
name|HibernateException
block|{
return|return
operator|(
name|RoomFeature
operator|)
operator|(
operator|new
name|RoomFeatureDAO
argument_list|()
operator|)
operator|.
name|get
argument_list|(
name|id
argument_list|)
return|;
block|}
comment|/** 	 * @param id 	 * @throws HibernateException 	 */
specifier|public
specifier|static
name|void
name|deleteRoomFeatureById
parameter_list|(
name|Long
name|id
parameter_list|)
throws|throws
name|HibernateException
block|{
name|RoomFeature
name|rf
init|=
name|RoomFeature
operator|.
name|getRoomFeatureById
argument_list|(
name|id
argument_list|)
decl_stmt|;
if|if
condition|(
name|rf
operator|!=
literal|null
condition|)
block|{
operator|(
operator|new
name|RoomFeatureDAO
argument_list|()
operator|)
operator|.
name|delete
argument_list|(
name|rf
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|void
name|saveOrUpdate
parameter_list|()
throws|throws
name|HibernateException
block|{
operator|(
operator|new
name|RoomFeatureDAO
argument_list|()
operator|)
operator|.
name|saveOrUpdate
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
comment|/** Request attribute name for available room features **/
specifier|public
specifier|static
name|String
name|FEATURE_LIST_ATTR_NAME
init|=
literal|"roomFeaturesList"
decl_stmt|;
comment|/**      *       */
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
name|RoomFeature
operator|)
condition|)
return|return
operator|-
literal|1
return|;
name|RoomFeature
name|rf
init|=
operator|(
name|RoomFeature
operator|)
name|o
decl_stmt|;
name|int
name|cmp
init|=
name|getLabel
argument_list|()
operator|.
name|compareTo
argument_list|(
name|rf
operator|.
name|getLabel
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
name|rf
operator|.
name|getUniqueId
argument_list|()
operator|==
literal|null
condition|?
operator|-
literal|1
else|:
name|rf
operator|.
name|getUniqueId
argument_list|()
argument_list|)
return|;
block|}
comment|/**      *       * @param location      * @return      */
specifier|public
name|boolean
name|hasLocation
parameter_list|(
name|Location
name|location
parameter_list|)
block|{
return|return
name|getRooms
argument_list|()
operator|.
name|contains
argument_list|(
name|location
argument_list|)
return|;
block|}
comment|/** 	 * @return Room feature label 	 */
specifier|public
name|String
name|getLabelWithType
parameter_list|()
block|{
return|return
name|getLabel
argument_list|()
operator|+
operator|(
name|getFeatureType
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
literal|" ("
operator|+
name|getFeatureType
argument_list|()
operator|.
name|getReference
argument_list|()
operator|+
literal|")"
operator|)
return|;
block|}
specifier|public
name|String
name|getAbbv
parameter_list|()
block|{
if|if
condition|(
name|super
operator|.
name|getAbbv
argument_list|()
operator|!=
literal|null
operator|&&
name|super
operator|.
name|getAbbv
argument_list|()
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
return|return
name|super
operator|.
name|getAbbv
argument_list|()
return|;
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
for|for
control|(
name|StringTokenizer
name|stk
init|=
operator|new
name|StringTokenizer
argument_list|(
name|getLabel
argument_list|()
argument_list|,
literal|" "
argument_list|)
init|;
name|stk
operator|.
name|hasMoreTokens
argument_list|()
condition|;
control|)
block|{
name|String
name|word
init|=
name|stk
operator|.
name|nextToken
argument_list|()
decl_stmt|;
if|if
condition|(
literal|"and"
operator|.
name|equalsIgnoreCase
argument_list|(
name|word
argument_list|)
condition|)
name|sb
operator|.
name|append
argument_list|(
literal|"&amp;"
argument_list|)
expr_stmt|;
if|else if
condition|(
name|word
operator|.
name|replaceAll
argument_list|(
literal|"[a-zA-Z\\.]*"
argument_list|,
literal|""
argument_list|)
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
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
name|word
operator|.
name|length
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|i
operator|==
literal|0
condition|)
name|sb
operator|.
name|append
argument_list|(
name|word
operator|.
name|substring
argument_list|(
name|i
argument_list|,
name|i
operator|+
literal|1
argument_list|)
operator|.
name|toUpperCase
argument_list|()
argument_list|)
expr_stmt|;
if|else if
condition|(
operator|(
name|i
operator|==
literal|1
operator|&&
name|word
operator|.
name|length
argument_list|()
operator|>
literal|3
operator|)
operator|||
operator|(
name|word
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
operator|>=
literal|'A'
operator|&&
name|word
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
operator|<=
literal|'Z'
operator|)
condition|)
name|sb
operator|.
name|append
argument_list|(
name|word
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
else|else
name|sb
operator|.
name|append
argument_list|(
name|word
argument_list|)
expr_stmt|;
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
specifier|public
name|RoomFeature
name|findSameFeatureInSession
parameter_list|(
name|Session
name|session
parameter_list|)
block|{
if|if
condition|(
name|session
operator|==
literal|null
condition|)
return|return
literal|null
return|;
name|List
name|matchingFeatures
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|this
operator|instanceof
name|DepartmentRoomFeature
condition|)
block|{
name|matchingFeatures
operator|=
name|RoomFeatureDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select distinct d from DepartmentRoomFeature d where d.department.session.uniqueId=:sessionId and d.label=:label and d.department.deptCode=:deptCode"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|session
operator|.
name|getUniqueId
argument_list|()
operator|.
name|longValue
argument_list|()
argument_list|)
operator|.
name|setString
argument_list|(
literal|"deptCode"
argument_list|,
operator|(
operator|(
name|DepartmentRoomFeature
operator|)
name|this
operator|)
operator|.
name|getDeptCode
argument_list|()
argument_list|)
operator|.
name|setString
argument_list|(
literal|"label"
argument_list|,
name|getLabel
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
expr_stmt|;
block|}
else|else
block|{
name|matchingFeatures
operator|=
name|RoomFeatureDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select g from GlobalRoomFeature g where g.session.uniqueId=:sessionId and g.label=:label"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|session
operator|.
name|getUniqueId
argument_list|()
operator|.
name|longValue
argument_list|()
argument_list|)
operator|.
name|setString
argument_list|(
literal|"label"
argument_list|,
name|getLabel
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
expr_stmt|;
block|}
return|return
operator|(
name|matchingFeatures
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|?
operator|(
name|RoomFeature
operator|)
name|matchingFeatures
operator|.
name|get
argument_list|(
literal|0
argument_list|)
else|:
literal|null
operator|)
return|;
block|}
block|}
end_class

end_unit

