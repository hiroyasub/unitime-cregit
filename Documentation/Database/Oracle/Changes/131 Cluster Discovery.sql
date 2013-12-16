/*
 * UniTime 3.5 (University Timetabling Application)
 * Copyright (C) 2013, UniTime LLC
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
*/

create table cluster_discovery (
	own_address varchar2(200 char) constraint nn_cluster_address not null,
	cluster_name varchar2(200 char) constraint nn_cluster_name not null,
	ping_data blob
);

alter table cluster_discovery add constraint pk_cluster_discovery primary key (own_address, cluster_name);

/*
 * Update database version
 */

update application_config set value='131' where name='tmtbl.db.version';

commit;