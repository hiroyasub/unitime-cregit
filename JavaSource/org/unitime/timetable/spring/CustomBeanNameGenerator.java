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
name|spring
package|;
end_package

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
name|springframework
operator|.
name|beans
operator|.
name|factory
operator|.
name|annotation
operator|.
name|AnnotatedBeanDefinition
import|;
end_import

begin_import
import|import
name|org
operator|.
name|springframework
operator|.
name|context
operator|.
name|annotation
operator|.
name|AnnotationBeanNameGenerator
import|;
end_import

begin_import
import|import
name|org
operator|.
name|springframework
operator|.
name|core
operator|.
name|type
operator|.
name|AnnotationMetadata
import|;
end_import

begin_import
import|import
name|org
operator|.
name|springframework
operator|.
name|util
operator|.
name|StringUtils
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
name|gwt
operator|.
name|command
operator|.
name|server
operator|.
name|GwtRpcImplements
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
name|gwt
operator|.
name|command
operator|.
name|server
operator|.
name|GwtRpcLogging
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
name|permissions
operator|.
name|PermissionForRight
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
name|rights
operator|.
name|Right
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|CustomBeanNameGenerator
extends|extends
name|AnnotationBeanNameGenerator
block|{
annotation|@
name|Override
specifier|protected
name|String
name|determineBeanNameFromAnnotation
parameter_list|(
name|AnnotatedBeanDefinition
name|annotatedDef
parameter_list|)
block|{
name|AnnotationMetadata
name|amd
init|=
name|annotatedDef
operator|.
name|getMetadata
argument_list|()
decl_stmt|;
name|Set
argument_list|<
name|String
argument_list|>
name|types
init|=
name|amd
operator|.
name|getAnnotationTypes
argument_list|()
decl_stmt|;
name|String
name|beanName
init|=
literal|null
decl_stmt|;
for|for
control|(
name|String
name|type
range|:
name|types
control|)
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|attributes
init|=
name|amd
operator|.
name|getAnnotationAttributes
argument_list|(
name|type
argument_list|)
decl_stmt|;
if|if
condition|(
name|isStereotypeWithNameValue
argument_list|(
name|type
argument_list|,
name|amd
operator|.
name|getMetaAnnotationTypes
argument_list|(
name|type
argument_list|)
argument_list|,
name|attributes
argument_list|)
condition|)
block|{
name|String
name|value
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|PermissionForRight
operator|.
name|class
operator|.
name|getName
argument_list|()
operator|.
name|equals
argument_list|(
name|type
argument_list|)
condition|)
block|{
name|Right
name|right
init|=
operator|(
name|Right
operator|)
name|attributes
operator|.
name|get
argument_list|(
literal|"value"
argument_list|)
decl_stmt|;
name|value
operator|=
literal|"permission"
operator|+
name|right
operator|.
name|name
argument_list|()
expr_stmt|;
block|}
if|else if
condition|(
name|GwtRpcImplements
operator|.
name|class
operator|.
name|getName
argument_list|()
operator|.
name|equals
argument_list|(
name|type
argument_list|)
condition|)
block|{
name|Class
argument_list|<
name|?
argument_list|>
name|requestClass
init|=
operator|(
name|Class
argument_list|<
name|?
argument_list|>
operator|)
name|attributes
operator|.
name|get
argument_list|(
literal|"value"
argument_list|)
decl_stmt|;
name|value
operator|=
name|requestClass
operator|.
name|getName
argument_list|()
expr_stmt|;
block|}
if|else if
condition|(
name|GwtRpcLogging
operator|.
name|class
operator|.
name|getName
argument_list|()
operator|.
name|equals
argument_list|(
name|type
argument_list|)
condition|)
block|{
continue|continue;
block|}
else|else
block|{
name|value
operator|=
operator|(
name|String
operator|)
name|attributes
operator|.
name|get
argument_list|(
literal|"value"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|StringUtils
operator|.
name|hasLength
argument_list|(
name|value
argument_list|)
condition|)
block|{
if|if
condition|(
name|beanName
operator|!=
literal|null
operator|&&
operator|!
name|value
operator|.
name|equals
argument_list|(
name|beanName
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Stereotype annotations suggest inconsistent "
operator|+
literal|"component names: '"
operator|+
name|beanName
operator|+
literal|"' versus '"
operator|+
name|value
operator|+
literal|"'"
argument_list|)
throw|;
block|}
name|beanName
operator|=
name|value
expr_stmt|;
block|}
block|}
block|}
return|return
name|beanName
return|;
block|}
block|}
end_class

end_unit

