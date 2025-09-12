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
import { Rdv } from '../../../interfaces/interfaces';

@Component({
  selector: 'app-gestion-rdvs',
  imports: [TableModule, ConfirmDialog,ToastModule,SideBar, NavBar, NgClass, FormsModule, Dialog, ButtonModule, ReactiveFormsModule, TagModule],
  providers: [HttpOperationNotificationService, HttpService, MessageService, ConfirmationService],
  templateUrl: './gestion-rdvs.html'
})
export class GestionRdvs {
  sidebarItems: any = [];
  isOpen: boolean = true;
  visible_create: boolean = false;
  visible_edit: boolean = false;
  selectedRdvId!: number;
  loading: boolean = true;

  rdvs!: Rdv[]

  // services
  // sideBarService: SideBarDataServiceService = inject(SideBarDataServiceService);
  confirmationService: ConfirmationService = inject(ConfirmationService);
  httpService : HttpService = inject(HttpService);

  //ngOnInit function
  ngOnInit(){
    this.getRdvs()
  }

  fg = new FormGroup({
    motif : new FormControl(""),
    statut : new FormControl(""),
    date : new FormControl(""),
  })

  showCreateDialog(){
    this.fg.reset();
    this.visible_create = true;
  }

  showEditDialog(rdv:Rdv){
    this.fg.patchValue(rdv);
    this.selectedRdvId = rdv.id;
    this.visible_edit = true;
  }

  getRdvs(): void{
    this.httpService.getAllRdvs().subscribe({
      next: r => {
        this.rdvs = r;
        this.loading = false
      },
      error : () => this.loading = false
    })
  }

  create() {
    this.httpService.createRdv(this.fg.value).subscribe({
      next: () => {
        this.getRdvs();
        this.visible_create = false;
      }
    })
  }

  edit(e:Event) {
    e.preventDefault();
    this.httpService.updateRdv(this.fg.value, this.selectedRdvId).subscribe({
      next: () => this.getRdvs()
    })
    this.visible_edit = false;
  }

  deleteRdv(id:number){
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
          this.httpService.deleteRdv(id).subscribe({
            next: () => {
              this.getRdvs();
            },
            error: () => {}
          })
        },
    });
  }
}
