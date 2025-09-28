import { Activite, Facture, Medicament, Rdv } from './../../../interfaces/interfaces';
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
import { RouterModule } from '@angular/router';
import { HttpOperationNotificationService } from '../../../services/http-operation-notification-service';
import { HttpService } from '../../../services/http-service';
import { Consultation, Patient } from '../../../interfaces/interfaces';

@Component({
  selector: 'app-type-consultation',
  imports: [RouterModule, TableModule, ConfirmDialog,ToastModule,SideBar, NavBar, NgClass, FormsModule, Dialog, ButtonModule, ReactiveFormsModule, TagModule],
  providers: [HttpOperationNotificationService, HttpService, MessageService, ConfirmationService],
  templateUrl: './type-consultation.html'
})
export class TypeConsultation {
  loading: boolean = true;
  types:any;
  isOpen: boolean = true;
  visible_create: boolean = false;
  visible_edit: boolean = false;
  selectedType!: number;

  confirmationService: ConfirmationService = inject(ConfirmationService);
  httpService : HttpService = inject(HttpService);

  //ngOnInit function
  ngOnInit(){
    this.getTypes()
  }

  fg = new FormGroup({
    libelle : new FormControl(""),
    prix : new FormControl("")
  })

  showCreateDialog(){
    this.fg.reset();
    this.visible_create = true;
  }

  showEditDialog(type:any){
    this.fg.patchValue(type);
    this.selectedType = type.id;
    this.visible_edit = true;
  }

  getTypes(): void{
    this.httpService.getAllTypeConsultations().subscribe({
      next: r => {
        this.types = r;
        this.loading = false
      },
      error : () => this.loading = false
    })
  }

  create() {
    this.httpService.createTypeConsultation(this.fg.value).subscribe({
      next: () => {
        this.getTypes();
        this.visible_create = false;
      }
    })
  }

  edit(e:Event) {
    e.preventDefault();
    this.httpService.updateTypeConsultation(this.fg.value, this.selectedType).subscribe({
      next: () => {
        this.getTypes();
        this.visible_edit = false;
      }
    })
    this.visible_edit = false;
  }

  deletePatient(id:number){
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
          this.httpService.deleteTypeConsultation(id).subscribe({
            next: () => {
              this.getTypes();
            },
            error: () => {}
          })
        },
    });
  }
}
