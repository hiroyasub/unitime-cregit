/*
 * Licensed to The Apereo Foundation under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for
 * additional information regarding copyright ownership.
 *
 * The Apereo Foundation licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
*/

create table course_type (
	uniqueid decimal(20,0) primary key not null,
	reference varchar(20) not null,
	label varchar(60) not null
) engine = INNODB;

alter table course_offering add course_type_id decimal(20,0);

alter table course_offering add constraint fk_course_offering_type foreign key (course_type_id)
	references course_type (uniqueid) on delete set null;

create table sectioning_course_types (
	sectioning_status_id decimal(20,0) not null,
	course_type_id decimal(20,0) not null,
	primary key(sectioning_status_id, course_type_id)
) engine = INNODB;

alter table sectioning_course_types add constraint fk_sect_course_status foreign key (sectioning_status_id)
	references sectioning_status (uniqueid) on delete cascade;

alter table sectioning_course_types add constraint fk_sect_course_type foreign key (course_type_id)
	references course_type (uniqueid) on delete cascade;

insert into rights (role_id, value)
	select distinct r.role_id, 'CourseTypes'
	from roles r, rights g where g.role_id = r.role_id and g.value = 'PositionTypes';

insert into rights (role_id, value)
	select distinct r.role_id, 'CourseTypeEdit'
	from roles r, rights g where g.role_id = r.role_id and g.value = 'PositionTypeEdit';

/*
 * Update database version
 */

update application_config set value='110' where name='tmtbl.db.version';

commit;
