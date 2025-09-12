import { Component, inject } from '@angular/core';
import { TableModule } from 'primeng/table';
import { ConfirmDialog } from 'primeng/confirmdialog';
import { ToastModule } from 'primeng/toast';
import { ConfirmationService, MessageService } from 'primeng/api';
import { Dialog } from 'primeng/dialog';
import { TagModule } from 'primeng/tag';
import { ButtonModule } from 'primeng/button';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MultiSelectModule } from 'primeng/multiselect';
import { HttpOperationNotificationService } from '../../../services/http-operation-notification-service';
import { HttpService } from '../../../services/http-service';

@Component({
  selector: 'app-permissions-table',
  imports: [TableModule,FormsModule, ConfirmDialog,ToastModule, Dialog, MultiSelectModule, ButtonModule, TagModule, ReactiveFormsModule],
  providers: [ConfirmationService, HttpOperationNotificationService, HttpService, MessageService],
  templateUrl: './permissions-table.html'
})
export class PermissionsTable {
  visible_edit: boolean = false;
  visible_create: boolean = false;
  permissions: any[] = [];
  selectedPermissionId!: number;
  selectedRoleId: number | null = null;
  roles: any[] = [];
  selectedRoles: any[] = [];

  fg = new FormGroup({
    nom: new FormControl(""),
    description: new FormControl(""),
    selectedRolesIds: new FormControl([])
  });

  // services
  confirmationService: ConfirmationService = inject(ConfirmationService);
  httpService : HttpService = inject(HttpService);

  ngOnInit() {
    this.getPermissions();
    this.getRoles();
  }

  getRoles(){
    return this.httpService.getAllRoles().subscribe({
      next: r =>  this.roles = r
    })
  }
  getPermissions() {
    // This should ideally fetch roles from a service
    return this.httpService.getAllPermissions().subscribe({
      next: r =>  this.permissions = r
    })
  }

  showEditDialog(permission: any) {
    this.fg.patchValue(permission)
    this.selectedPermissionId = permission.id;
    this.visible_edit = true;
  }

  showCreateDialog() {
    this.visible_create = true;
    this.fg.reset();
  }

  create() {
    this.httpService.createPermission(this.fg.value).subscribe({
      next: () => {
        this.getPermissions();
        this.visible_create = false;
      }
    })
  }

  deletePermession(id: number) {
    this.confirmationService.confirm({
        message: 'Veuillez confirmer pour continuer',
        header: 'Confirmation',
        closable: true,
        closeOnEscape: true,
        icon: 'pi pi-exclamation-triangle',
        rejectButtonProps: {
            label: 'Annuler',
            severity: 'secondary',
            outlined: true,
        },
        acceptButtonProps: {
            label: 'supprimer',
            severity: 'danger',
        },
        accept: () => {
          this.httpService.deletePermission(id).subscribe({
            next: () => {
              this.getPermissions();
            },
            error: () => {}
          })
        },
    });
  }

  edit(event: any) {
    event.preventDefault();
    this.httpService.updatePermission(this.fg.value, this.selectedPermissionId).subscribe({
      next: () => this.getPermissions()
    })
    this.visible_edit = false;
  }
}
