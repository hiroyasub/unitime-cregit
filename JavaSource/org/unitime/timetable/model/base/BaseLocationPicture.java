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
name|java
operator|.
name|util
operator|.
name|Date
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
name|AttachmentType
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
name|LocationPicture
import|;
end_import

begin_comment
comment|/**  * Do not change this class. It has been automatically generated using ant create-model.  * @see org.unitime.commons.ant.CreateBaseModelFromXml  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|BaseLocationPicture
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
name|byte
index|[]
name|iDataFile
decl_stmt|;
specifier|private
name|String
name|iFileName
decl_stmt|;
specifier|private
name|String
name|iContentType
decl_stmt|;
specifier|private
name|Date
name|iTimeStamp
decl_stmt|;
specifier|private
name|AttachmentType
name|iType
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
name|PROP_DATA_FILE
init|=
literal|"dataFile"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_FILE_NAME
init|=
literal|"fileName"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_CONTENT_TYPE
init|=
literal|"contentType"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_TIME_STAMP
init|=
literal|"timeStamp"
decl_stmt|;
specifier|public
name|BaseLocationPicture
parameter_list|()
block|{
name|initialize
argument_list|()
expr_stmt|;
block|}
specifier|public
name|BaseLocationPicture
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
name|byte
index|[]
name|getDataFile
parameter_list|()
block|{
return|return
name|iDataFile
return|;
block|}
specifier|public
name|void
name|setDataFile
parameter_list|(
name|byte
index|[]
name|dataFile
parameter_list|)
block|{
name|iDataFile
operator|=
name|dataFile
expr_stmt|;
block|}
specifier|public
name|String
name|getFileName
parameter_list|()
block|{
return|return
name|iFileName
return|;
block|}
specifier|public
name|void
name|setFileName
parameter_list|(
name|String
name|fileName
parameter_list|)
block|{
name|iFileName
operator|=
name|fileName
expr_stmt|;
block|}
specifier|public
name|String
name|getContentType
parameter_list|()
block|{
return|return
name|iContentType
return|;
block|}
specifier|public
name|void
name|setContentType
parameter_list|(
name|String
name|contentType
parameter_list|)
block|{
name|iContentType
operator|=
name|contentType
expr_stmt|;
block|}
specifier|public
name|Date
name|getTimeStamp
parameter_list|()
block|{
return|return
name|iTimeStamp
return|;
block|}
specifier|public
name|void
name|setTimeStamp
parameter_list|(
name|Date
name|timeStamp
parameter_list|)
block|{
name|iTimeStamp
operator|=
name|timeStamp
expr_stmt|;
block|}
specifier|public
name|AttachmentType
name|getType
parameter_list|()
block|{
return|return
name|iType
return|;
block|}
specifier|public
name|void
name|setType
parameter_list|(
name|AttachmentType
name|type
parameter_list|)
block|{
name|iType
operator|=
name|type
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
name|LocationPicture
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
name|LocationPicture
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
name|LocationPicture
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
literal|"LocationPicture["
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
literal|"LocationPicture["
operator|+
literal|"\n	ContentType: "
operator|+
name|getContentType
argument_list|()
operator|+
literal|"\n	DataFile: "
operator|+
name|getDataFile
argument_list|()
operator|+
literal|"\n	FileName: "
operator|+
name|getFileName
argument_list|()
operator|+
literal|"\n	TimeStamp: "
operator|+
name|getTimeStamp
argument_list|()
operator|+
literal|"\n	Type: "
operator|+
name|getType
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

