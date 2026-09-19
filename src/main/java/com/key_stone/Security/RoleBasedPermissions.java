package com.key_stone.Security;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.key_stone.Enum.Permissions;
import com.key_stone.Enum.Role;

public class RoleBasedPermissions {
	private static final Map<Role,Set<Permissions>>rolePermissions(){
		
		Map<Role,Set<Permissions>>permissions= new HashMap<>();
	
		permissions.put(Role.MANAGER, new HashSet<>(Arrays.asList(
												Permissions.CREATE_USER,
												Permissions.VIEW_USER,
												Permissions.UPDATE_USER,
												Permissions.DELETE_USER,
													
												Permissions.CREATE_CUSTOMER,
												Permissions.VIEW_CUSTOMER,
												Permissions.UPDATE_CUSTOMER,
												Permissions.DELETE_CUSTOMER,
													
												Permissions.CREATE_SITE,
												Permissions.VIEW_SITE,
												Permissions.UPDATE_SITE,
												Permissions.DELETE_SITE,
												
												Permissions.CREATE_WO,
												Permissions.VIEW_WO,
												Permissions.UPDATE_WO,
												Permissions.ASSIGN_WO,
												Permissions.CANCEL_WO,
												Permissions.CLOSE_WO,
												Permissions.DELETE_WO,
												
												Permissions.ADD_PARTS,
												Permissions.VIEW_PARTS,
												Permissions.UPDATE_PARTS,
												Permissions.USE_PARTS,
												Permissions.DELETE_PARTS,
												
												Permissions.ADD_TIME_LOGS,
												Permissions.VIEW_TIME_LOGS,
												
												Permissions.VIEW_DASHBOARD,
												Permissions.VIEW_RECORDS,
												
												Permissions.SEND_NOTIFICATION)));
		
		permissions.put(Role.DISPATCHER, new HashSet<>(Arrays.asList(
												Permissions.CREATE_CUSTOMER,
												Permissions.UPDATE_CUSTOMER,
												Permissions.VIEW_CUSTOMER,
												
												Permissions.CREATE_SITE,
												Permissions.VIEW_SITE,
												Permissions.UPDATE_SITE,
												
												Permissions.CREATE_WO,
												Permissions.VIEW_WO,
												Permissions.UPDATE_WO,
												Permissions.ASSIGN_WO,
												Permissions.CANCEL_WO,
												
												Permissions.VIEW_DASHBOARD)));
		
		permissions.put(Role.TECHNICIAN, new HashSet<>(Arrays.asList(
												Permissions.START_WORK,
												Permissions.HOLD_WORK,
												Permissions.RESUME_WORK,
												Permissions.COMPLETE_WORK)));
		
		permissions.put(Role.CUSTOMER, new HashSet<>(Arrays.asList(
												Permissions.RAISE_REQUEST,
												Permissions.VIEW_OWN_REQUEST)));
		
		return permissions;

	}
}
