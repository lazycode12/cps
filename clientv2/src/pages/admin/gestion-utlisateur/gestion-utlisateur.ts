import { Component, inject } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SideBar } from '../../../components/side-bar/side-bar';
import { NavBar } from '../../../components/nav-bar/nav-bar';
import { NgClass } from '@angular/common';
import { Dialog } from 'primeng/dialog';
import { ButtonModule } from 'primeng/button';
import { TagModule } from 'primeng/tag';
import { ConfirmDialog } from 'primeng/confirmdialog';
import { ToastModule } from 'primeng/toast';
import { ConfirmationService, MessageService } from 'primeng/api';
import { TableModule } from 'primeng/table';
import { HttpService } from '../../../services/http-service';
import { HttpOperationNotificationService } from '../../../services/http-operation-notification-service';

@Component({
  selector: 'app-gestion-utlisateur',
  imports: [TableModule, ConfirmDialog,ToastModule,SideBar, NavBar, NgClass, FormsModule, Dialog, ButtonModule, ReactiveFormsModule, TagModule],
  providers: [MessageService, ConfirmationService, HttpOperationNotificationService, HttpService],
  templateUrl: './gestion-utlisateur.html'
})
export class GestionUtlisateur {
  isOpen: boolean = true;
  visible_create: boolean = false;
  visible_edit: boolean = false;
  users: any[] = [];
  roles: any[] = [];
  selectedUserId!: number;
  loading:boolean = true;

  // services
  // sideBarService: SideBarDataServiceService = inject(SideBarDataServiceService);
  confirmationService: ConfirmationService = inject(ConfirmationService);
  httpService : HttpService = inject(HttpService);


  // getUsers(){
  //   this.httpService.getComptes().subscribe({
  //     next: res => this.users = res
  //   })
  // }
  //ngOnInit function
  ngOnInit(){
    // this.sidebarItems = this.sideBarService.getSideBarData("admin-user");
    this.getUsers()
    this.getRoles()
  }

  getUsers(){
    return this.httpService.getAllUsers().subscribe({
      next: r => {
        this.users = r;
        this.loading = false
      },
      error: r => this.loading = false
    })
  }

  getRoles(){
    return this.httpService.getAllRoles().subscribe({
      next: r =>  this.roles = r
    })
  }

  fg = new FormGroup({
    nom : new FormControl(""),
    prenom : new FormControl(""),
    email : new FormControl(""),
    password : new FormControl(""),
    roleId : new FormControl(0)
  })

  showCreateDialog(){
    this.visible_create = true;
  }

  showEditDialog(user:any){
    let data = {
      nom : user.nom,
      prenom : user.prenom,
      email : user.email,
      roleId : user.role.id,
    }
    this.fg.patchValue(data);
    this.selectedUserId = user.id;
    this.visible_edit = true;
  }

  create() {
    this.httpService.createUser(this.fg.value).subscribe({
    next: (response) => {
      // this.loading = false;
      // Handle success - show toast, navigate, reset form, etc.
      console.log('User created successfully', response);
      this.fg.reset(); // Reset form
      // this.showSuccessMessage('User created successfully!');
      },
      error: (error) => {
        // this.loading = false;
        // Error handling is already done in the service/interceptor
        console.error('Error creating user', error);
      },
      complete: () => {
        // this.loading = false; // Ensure loading is false even on complete
      }
    });
  }

  edit(e:Event) {
    this.httpService.updateUser(this.fg.value, this.selectedUserId).subscribe({
      next: () => {
        this.getUsers();
        this.visible_edit = false;
      }
    })
  }



  deleteUser(id:number){
    this.confirmationService.confirm({
      // target: event.target as EventTarget,
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
        this.httpService.deleteUser(id).subscribe({
          next: () => {
            this.getUsers();
          },
          error: () => {}
        })
      },
  });
  }
}
