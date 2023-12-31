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
name|model
operator|.
name|comparators
package|;
end_package

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
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|ItypeDesc
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
name|SimpleItypeConfig
import|;
end_import

begin_comment
comment|/**  * Compares SimpleItypeConfig objects based on Itype  *   * @author Heston Fernandes  */
end_comment

begin_class
specifier|public
class|class
name|SicComparator
implements|implements
name|Comparator
block|{
comment|/**      * Compares SimpleItypeConfig objects based on Itype      * @param o1 SimpleItypeConfig      * @param o2 SimpleItypeConfig      * @return 0 if equal, -1 if o1<o2, +1 if o1>o2      */
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
if|if
condition|(
operator|!
operator|(
name|o1
operator|instanceof
name|SimpleItypeConfig
operator|)
operator|||
name|o1
operator|==
literal|null
condition|)
throw|throw
operator|new
name|RuntimeException
argument_list|(
literal|"Object o1 must be of type SimpleItypeConfig and cannot be null"
argument_list|)
throw|;
if|if
condition|(
operator|!
operator|(
name|o2
operator|instanceof
name|SimpleItypeConfig
operator|)
operator|||
name|o2
operator|==
literal|null
condition|)
throw|throw
operator|new
name|RuntimeException
argument_list|(
literal|"Object o2 must be of type SimpleItypeConfig and cannot be null"
argument_list|)
throw|;
name|SimpleItypeConfig
name|sic1
init|=
operator|(
name|SimpleItypeConfig
operator|)
name|o1
decl_stmt|;
name|SimpleItypeConfig
name|sic2
init|=
operator|(
name|SimpleItypeConfig
operator|)
name|o2
decl_stmt|;
name|ItypeDesc
name|id1
init|=
name|sic1
operator|.
name|getItype
argument_list|()
decl_stmt|;
if|if
condition|(
name|id1
operator|==
literal|null
condition|)
throw|throw
operator|new
name|RuntimeException
argument_list|(
literal|"Object o1 does not have an assigned Itype"
argument_list|)
throw|;
name|ItypeDesc
name|id2
init|=
name|sic2
operator|.
name|getItype
argument_list|()
decl_stmt|;
if|if
condition|(
name|id2
operator|==
literal|null
condition|)
throw|throw
operator|new
name|RuntimeException
argument_list|(
literal|"Object o2 does not have an assigned Itype"
argument_list|)
throw|;
name|int
name|itype1
init|=
name|id1
operator|.
name|getItype
argument_list|()
operator|.
name|intValue
argument_list|()
decl_stmt|;
name|int
name|itype2
init|=
name|id2
operator|.
name|getItype
argument_list|()
operator|.
name|intValue
argument_list|()
decl_stmt|;
name|int
name|retValue
init|=
literal|0
decl_stmt|;
if|if
condition|(
name|itype1
operator|>
name|itype2
condition|)
name|retValue
operator|=
literal|1
expr_stmt|;
if|if
condition|(
name|itype1
operator|<
name|itype2
condition|)
name|retValue
operator|=
operator|-
literal|1
expr_stmt|;
return|return
name|retValue
return|;
block|}
block|}
end_class

end_unit

