import { Component, inject } from '@angular/core';
import { NavBar } from '../../../components/nav-bar/nav-bar';
import { SideBar } from '../../../components/side-bar/side-bar';
import { NgClass } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RolesTable } from '../../../components/tables/roles-table/roles-table';
import { PermissionsTable } from '../../../components/tables/permissions-table/permissions-table';
import { JsonPipe } from '@angular/common';
import { Permission, Role } from '../../../interfaces/interfaces';
import { HttpOperationNotificationService } from '../../../services/http-operation-notification-service';
import { HttpService } from '../../../services/http-service';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-gestion-roles-permissions',
  imports: [SideBar, NavBar, NgClass, FormsModule, RolesTable, PermissionsTable, JsonPipe],
  providers: [HttpOperationNotificationService, HttpService, MessageService],
  templateUrl: './gestion-roles-permissions.html'
})
export class GestionRolesPermissions {
  sidebarItems: any = [];
  isOpen: boolean = true;
  table: string = "role";
  isSaving: boolean = false;
  saveMessage: string = '';
  rolesLoading: boolean = true;
  PermissionsLoading: boolean = true;

  modifiedRoleIds = new Set()

  roles!: Role[]
  permissions! : Permission[]

  httpService : HttpService = inject(HttpService);

  ngOnInit() {
    this.getPermissions();
    this.getRoles();
  }

  getRoles(){
    return this.httpService.getAllRoles().subscribe({
      next: r =>  {this.roles = r;this.rolesLoading=false}
    })
  }
  getPermissions() {
    // This should ideally fetch roles from a service
    return this.httpService.getAllPermissions().subscribe({
      next: r =>  {this.permissions = r;this.PermissionsLoading=false}
    })
  }
 hasPermission(permissions: Permission[], permissionId: number): boolean {
    return permissions.some((p) => p.id == permissionId);
  }

  togglePermission(role: Role, permission: Permission): void {
    const index = role.permissions.findIndex(p => p.id === permission.id);
    if (index === -1) {
      role.permissions.push(permission);
    } else {
      role.permissions.splice(index, 1);
    }
    role.permissions.sort();
    this.modifiedRoleIds.add(role.id);
    this.saveMessage = ''; // Clear any previous messages when making new changes
  }

  getModifiedRoles() :Role[]{
    return this.roles.filter(role => this.modifiedRoleIds.has(role.id))
  }

  saveRoles(): void {
    if(this.modifiedRoleIds.size == 0) return

    const modifiedRoles = this.getModifiedRoles();

    this.isSaving = true;

    this.httpService.updateRolesPermissions(modifiedRoles).subscribe({
      next: () => {
        this.saveMessage = 'saved';
        this.isSaving = false;
      }
    })
  }
}
