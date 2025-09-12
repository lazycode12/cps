import { Component, inject } from '@angular/core';
import { TableModule } from 'primeng/table';
import { ConfirmDialog } from 'primeng/confirmdialog';
import { ToastModule } from 'primeng/toast';
import { ConfirmationService, MessageService } from 'primeng/api';
import { Dialog } from 'primeng/dialog';
import { TagModule } from 'primeng/tag';
import { ButtonModule } from 'primeng/button';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { HttpService } from '../../../services/http-service';
import { HttpOperationNotificationService } from '../../../services/http-operation-notification-service';

@Component({
  selector: 'app-roles-table',
  imports: [TableModule, ConfirmDialog,ToastModule, Dialog, ButtonModule, TagModule, ReactiveFormsModule],
  templateUrl: './roles-table.html',
  providers: [ConfirmationService, HttpOperationNotificationService, HttpService, MessageService],
})
export class RolesTable {

  visible_edit: boolean = false;
  visible_create: boolean = false;
  roles: any[] = [];
  selectedRoleId!: number;

  fg = new FormGroup({
    nom: new FormControl(""),
    description: new FormControl("")
  });

  // services
  confirmationService: ConfirmationService = inject(ConfirmationService);
  httpService : HttpService = inject(HttpService);

  ngOnInit() {
    this.getRoles();
  }

  getRoles(){
    return this.httpService.getAllRoles().subscribe({
      next: r =>  this.roles = r
    })
  }

  showEditDialog(role: any) {
    this.fg.patchValue(role)
    this.selectedRoleId = role.id;
    this.visible_edit = true;
  }

  showCreateDialog() {
    this.visible_create = true;
    this.fg.reset();
  }

  create(){
    // Logic to create a new role
    this.httpService.createRole(this.fg.value).subscribe({
      next: () => {
        this.getRoles();
        this.visible_create = false;
      }
    })
  }

  deleteRole(roleId: number) {
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
          this.httpService.deleteRole(roleId).subscribe({
            next: () => {
              this.getRoles();
            },
            error: () => {}
          })
        },
    });
  }

  edit(event: any) {
    event.preventDefault();
    this.httpService.updateRole(this.fg.value, this.selectedRoleId).subscribe({
      next: () => this.getRoles()
    })
    this.visible_edit = false;
  }
}
