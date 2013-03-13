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
name|unitime
operator|.
name|localization
operator|.
name|impl
operator|.
name|Localization
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|localization
operator|.
name|messages
operator|.
name|ExaminationMessages
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
name|BaseExamType
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
name|ExamTypeDAO
import|;
end_import

begin_class
specifier|public
class|class
name|ExamType
extends|extends
name|BaseExamType
implements|implements
name|Comparable
argument_list|<
name|ExamType
argument_list|>
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
specifier|protected
specifier|static
name|ExaminationMessages
name|MSG
init|=
name|Localization
operator|.
name|create
argument_list|(
name|ExaminationMessages
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|sExamTypeFinal
init|=
literal|0
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|sExamTypeMidterm
init|=
literal|1
decl_stmt|;
specifier|public
name|ExamType
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|compareTo
parameter_list|(
name|ExamType
name|o
parameter_list|)
block|{
return|return
operator|(
name|getType
argument_list|()
operator|.
name|equals
argument_list|(
name|o
operator|.
name|getType
argument_list|()
argument_list|)
condition|?
name|getLabel
argument_list|()
operator|.
name|compareToIgnoreCase
argument_list|(
name|o
operator|.
name|getLabel
argument_list|()
argument_list|)
else|:
name|getType
argument_list|()
operator|.
name|compareTo
argument_list|(
name|o
operator|.
name|getType
argument_list|()
argument_list|)
operator|)
return|;
block|}
specifier|public
specifier|static
name|ExamType
name|findByReference
parameter_list|(
name|String
name|ref
parameter_list|)
block|{
return|return
operator|(
name|ExamType
operator|)
name|ExamTypeDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"from ExamType where reference = :ref"
argument_list|)
operator|.
name|setString
argument_list|(
literal|"ref"
argument_list|,
name|ref
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|setMaxResults
argument_list|(
literal|1
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
name|ExamType
argument_list|>
name|findAllOfType
parameter_list|(
name|int
name|type
parameter_list|)
block|{
return|return
operator|(
name|List
argument_list|<
name|ExamType
argument_list|>
operator|)
name|ExamTypeDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"from ExamType where type = :type order by type, label"
argument_list|)
operator|.
name|setInteger
argument_list|(
literal|"type"
argument_list|,
name|type
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
name|TreeSet
argument_list|<
name|ExamType
argument_list|>
name|findAllUsed
parameter_list|(
name|Long
name|sessionId
parameter_list|)
block|{
return|return
operator|new
name|TreeSet
argument_list|<
name|ExamType
argument_list|>
argument_list|(
name|ExamTypeDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select distinct p.examType from ExamPeriod p where p.session.uniqueId = :sessionId"
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
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|List
argument_list|<
name|ExamType
argument_list|>
name|findAll
parameter_list|()
block|{
return|return
operator|(
name|List
argument_list|<
name|ExamType
argument_list|>
operator|)
name|ExamTypeDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"from ExamType order by type, label"
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
name|boolean
name|isUsed
parameter_list|(
name|Long
name|sessionId
parameter_list|)
block|{
if|if
condition|(
name|sessionId
operator|==
literal|null
condition|)
block|{
return|return
operator|(
operator|(
name|Number
operator|)
name|ExamTypeDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select count(p) from ExamPeriod p where p.examType.uniqueId = :typeId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"typeId"
argument_list|,
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|uniqueResult
argument_list|()
operator|)
operator|.
name|longValue
argument_list|()
operator|>
literal|0
return|;
block|}
else|else
block|{
return|return
operator|(
operator|(
name|Number
operator|)
name|ExamTypeDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select count(p) from ExamPeriod p where p.examType.uniqueId = :typeId and p.session.uniqueId = :sessionId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"typeId"
argument_list|,
name|getUniqueId
argument_list|()
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
name|uniqueResult
argument_list|()
operator|)
operator|.
name|longValue
argument_list|()
operator|>
literal|0
return|;
block|}
block|}
block|}
end_class

end_unit

