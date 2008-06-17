<%--
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
--%>
<%@ taglib uri="/WEB-INF/tld/timetable.tld" prefix="tt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "DTD/xhtml1-transitional.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link rel="stylesheet" type="text/css" href="styles/timetabling.css" />
	<tt:hasProperty name="tmtbl.custom.css">
		<LINK rel="stylesheet" type="text/css" href="%tmtbl.custom.css%" />
	</tt:hasProperty>
	<script language="javascript" type="text/javascript">
	
		var strMenuFramesetRows =  window.parent.document.getElementById('__idMenuFramesetLeft').rows;
		
		function minimize() {

			var parentFrame = window.parent.document.getElementById('__idMenuFrameset');
			
			// save the current frameset width
			window.parent.strFrameCols = parentFrame.cols;
			
			// collapse the frameset	
			parentFrame.cols = "28px,0px,*";
		}
		
		function mouseover() {			
			document.getElementById('idCloseMenu').src = "images/minimize_RO.gif";
		}
		
		function mouseout() {
			document.getElementById('idCloseMenu').src = "images/minimize.gif";
		}
	</script>
</head>

<body class="HelpBody" tabindex="1">
<table width="100%" cellpadding="0" cellspacing="0" border="0" valign="middle">
	<tr>
		<tt:hasProperty name="tmtbl.title">
			<td align='left'>
				&nbsp;&nbsp;&nbsp;<tt:property name="tmtbl.title"/>
			</td>
		</tt:hasProperty>
		<td align="right">
		<table cellpadding="0" cellspacing="0" border="0">
			<tr>
				<td height="20">
				<%--
					<a href="menu.jsp?e=all" target="__idMenuFrame"><img border="0" src="images/openMenu.gif" alt="Expand All Menus" title="Expand All Menus"></a>
				--%>
					<a onclick="minimize();" onmouseover="mouseover();"	onmouseout="mouseout();"><img id="idCloseMenu" src="images/minimize.gif" title="Minimize Menu" border="0" tabindex="4" alt="" /></a>
				</td>
				<td height="20" width="7">&nbsp;</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</body>
</html>
