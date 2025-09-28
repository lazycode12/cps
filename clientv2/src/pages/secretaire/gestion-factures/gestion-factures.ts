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
import { HttpOperationNotificationService } from '../../../services/http-operation-notification-service';
import { HttpService } from '../../../services/http-service';
import { Facture } from '../../../interfaces/interfaces';

@Component({
  selector: 'app-gestion-factures',
  imports: [TableModule, ConfirmDialog,ToastModule,SideBar, NavBar, NgClass, FormsModule, Dialog, ButtonModule, ReactiveFormsModule, TagModule],
  providers: [HttpOperationNotificationService, HttpService, MessageService, ConfirmationService],
  templateUrl: './gestion-factures.html'
})
export class GestionFactures {
  isOpen: boolean = true;
  visible_create: boolean = false;
  visible_edit: boolean = false;
  loading:boolean = true;
  factures!:Facture[];
  selectedFactureId!: number;

  // services
  // sideBarService: SideBarDataServiceService = inject(SideBarDataServiceService);
  confirmationService: ConfirmationService = inject(ConfirmationService);
  httpService : HttpService = inject(HttpService);

  //ngOnInit function
  ngOnInit(){
    // this.sidebarItems = this.sideBarService.getSideBarData("admin-user");
    // this.getUsers();
    this.getFactures()
  }

  getFactures(){
    this.httpService.getAllFactures().subscribe({
      next: r => {
        this.factures = r;
        this.loading = false
      },
      error : () => this.loading = false
    })
  }

  fg = new FormGroup({
    montant : new FormControl(0.0),
    statut : new FormControl(""),
    datePaiment : new FormControl(""),
  })

  showEditDialog(facture:Facture){
    let data = {
      montant : facture.montant,
      datePaiment : facture.datePaiment
    }
    this.fg.patchValue(data);
    this.selectedFactureId = facture.id;
    this.visible_edit = true;
  }
  showCreateDialog() {
    this.visible_create = true;
  }

  create() {

  }

  generate(consultationId:number){
    this.httpService.generateFacture(consultationId).subscribe({
      next: (pdfBlob) => {
      const fileURL = URL.createObjectURL(pdfBlob);
      window.open(fileURL);
    }
    })
  }

  edit(e:Event) {
    e.preventDefault();
    this.httpService.updateFacture(this.fg.value, this.selectedFactureId).subscribe({
      next: () => {
        this.getFactures();
        this.visible_edit = false;
      }
    })
    this.visible_edit = false;
  }

  deleteFacture(id:number){
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
          this.httpService.deleteFacture(id).subscribe({
            next: () => {
              this.getFactures();
            },
            error: () => {}
          })
        },
    });
  }

}
