/*
 * UniTime 3.4 (University Timetabling Application)
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

alter table standard_event_note add discriminator varchar(10) default 'global';
alter table standard_event_note add session_id decimal(20,0);
alter table standard_event_note add department_id decimal(20,0);

alter table standard_event_note add constraint fk_stdevt_note_session foreign key (session_id)
	references sessions (uniqueid) on delete cascade;
alter table standard_event_note add constraint fk_stdevt_note_dept foreign key (department_id)
	references department (uniqueid) on delete cascade;

insert into rights (role_id, value)
	select distinct r.role_id, 'StandardEventNotesGlobalEdit'
	from roles r, rights g where g.role_id = r.role_id and g.value = 'StandardEventNotes';
insert into rights (role_id, value)
	select distinct r.role_id, 'StandardEventNotesSessionEdit'
	from roles r, rights g where g.role_id = r.role_id and g.value = 'IsAdmin';
insert into rights (role_id, value)
	select distinct r.role_id, 'StandardEventNotesDepartmentEdit'
	from roles r, rights g where g.role_id = r.role_id and g.value = 'EventMeetingApprove';
delete from rights where value = 'StandardEventNotes';
insert into rights (role_id, value)
	select distinct r.role_id, 'StandardEventNotes'
	from roles r, rights g where g.role_id = r.role_id and g.value in ('StandardEventNotesGlobalEdit', 'StandardEventNotesSessionEdit', 'StandardEventNotesDepartmentEdit');

/*
 * Update database version
 */

update application_config set value='109' where name='tmtbl.db.version';

commit;
