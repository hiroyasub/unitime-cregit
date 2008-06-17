/*
 * UniTime 3.1 (University Timetabling Application)
 * Copyright (C) 2008, UniTime LLC
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
*/

/**
 * For all instructional types (table ITYPE_DESC), set abbreviation
 * (column ABBV) to SMAS abbreviation if it is not set.
 */ 
update itype_desc set abbv=smas_abbv where trim(abbv) is null;

/**
 * Drop SMAS abbreviation (column SMAS_ABBV) of table ITYPE_DESC.
 */ 
alter table itype_desc drop column smas_abbv;

commit;
