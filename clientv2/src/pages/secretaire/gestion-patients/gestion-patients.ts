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
import { Patient } from '../../../interfaces/interfaces';
import { HttpService } from '../../../services/http-service';
import { HttpOperationNotificationService } from '../../../services/http-operation-notification-service';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-gestion-patients',
  imports: [TableModule, ConfirmDialog,ToastModule,SideBar, NavBar, NgClass, FormsModule, Dialog, ButtonModule, ReactiveFormsModule, TagModule, RouterModule],
  providers: [HttpOperationNotificationService, HttpService, MessageService, ConfirmationService],
  templateUrl: './gestion-patients.html'
})
export class GestionPatients {
  isOpen: boolean = true;
  visible_create: boolean = false;
  visible_edit: boolean = false;
  selectedPatientId!: number;
  patients!: Patient[];
  loading: boolean = true;

  // services
  // sideBarService: SideBarDataServiceService = inject(SideBarDataServiceService);
  confirmationService: ConfirmationService = inject(ConfirmationService);
  httpService : HttpService = inject(HttpService);
  router: Router = inject(Router);

  //ngOnInit function
  ngOnInit(){
    this.getPatients()
  }

  fg = new FormGroup({
    nom : new FormControl(""),
    prenom : new FormControl(""),
    sexe : new FormControl(""),
    dateNaissance : new FormControl(""),
    cin : new FormControl(""),
    telephone : new FormControl(""),
    email : new FormControl(""),
    adresse : new FormControl("")
  })

  showCreateDialog(){
    this.fg.reset();
    this.visible_create = true;
  }

  showEditDialog(patient:Patient){
    this.fg.patchValue(patient);
    this.selectedPatientId = patient.id;
    this.visible_edit = true;
  }

  getPatients(): void{
    this.httpService.getAllPatients().subscribe({
      next: r => {
        this.patients = r;
        this.loading = false
      },
      error : () => this.loading = false
    })
  }

  create() {
    this.httpService.createPatient(this.fg.value).subscribe({
      next: () => {
        this.getPatients();
        this.visible_create = false;
      }
    })
  }

  edit(e:Event) {
    e.preventDefault();
    this.httpService.updatePatient(this.fg.value, this.selectedPatientId).subscribe({
      next: () => {
        this.getPatients();
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
          this.httpService.deletePatient(id).subscribe({
            next: () => {
              this.getPatients();
            },
            error: () => {}
          })
        },
    });
  }

  navigateToProfile(id:number){
    this.router.navigate(["/patient-profile", id])
  }
}
