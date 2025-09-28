import { Component } from '@angular/core';
import { NavBar } from '../../../components/nav-bar/nav-bar';
import { SideBar } from '../../../components/side-bar/side-bar';
import { NgClass } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RolesTable } from '../../../components/tables/roles-table/roles-table';
import { PermissionsTable } from '../../../components/tables/permissions-table/permissions-table';
import { HttpOperationNotificationService } from '../../../services/http-operation-notification-service';
import { HttpService } from '../../../services/http-service';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-gestion-roles-permissions',
  imports: [SideBar, NavBar, NgClass, FormsModule, RolesTable, PermissionsTable],
  providers: [HttpOperationNotificationService, HttpService, MessageService],
  templateUrl: './gestion-roles-permissions.html'
})
export class GestionRolesPermissions {
  isOpen: boolean = true;
  table: string = "role";

}
