begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.5 (University Timetabling Application)  * Copyright (C) 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|commons
operator|.
name|jgroups
package|;
end_package

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
name|net
operator|.
name|sf
operator|.
name|ehcache
operator|.
name|CacheManager
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|ehcache
operator|.
name|distribution
operator|.
name|CacheManagerPeerProvider
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|ehcache
operator|.
name|distribution
operator|.
name|CacheManagerPeerProviderFactory
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|ehcache
operator|.
name|distribution
operator|.
name|jgroups
operator|.
name|JGroupsCacheManagerPeerProvider
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|commons
operator|.
name|Debug
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
name|ApplicationProperties
import|;
end_import

begin_class
specifier|public
class|class
name|JGroupsCacheManagerPeerProviderFactory
extends|extends
name|CacheManagerPeerProviderFactory
block|{
annotation|@
name|Override
specifier|public
name|CacheManagerPeerProvider
name|createCachePeerProvider
parameter_list|(
name|CacheManager
name|cacheManager
parameter_list|,
name|Properties
name|properties
parameter_list|)
block|{
try|try
block|{
name|String
name|configuration
init|=
name|JGroupsUtils
operator|.
name|getConfigurationString
argument_list|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"unitime.hibernate.jgroups.config"
argument_list|,
literal|"hibernate-jgroups-tcp.xml"
argument_list|)
argument_list|)
decl_stmt|;
name|JGroupsCacheManagerPeerProvider
name|peerProvider
init|=
operator|new
name|JGroupsCacheManagerPeerProvider
argument_list|(
name|cacheManager
argument_list|,
name|configuration
argument_list|)
decl_stmt|;
name|peerProvider
operator|.
name|setChannelName
argument_list|(
literal|"UniTime:hibernate"
argument_list|)
expr_stmt|;
return|return
name|peerProvider
return|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|Debug
operator|.
name|error
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
block|}
end_class

end_unit

