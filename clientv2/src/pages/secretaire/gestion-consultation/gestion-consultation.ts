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
  selector: 'app-gestion-consultation',
  imports: [RouterModule, TableModule, ConfirmDialog,ToastModule,SideBar, NavBar, NgClass, FormsModule, Dialog, ButtonModule, ReactiveFormsModule, TagModule],
  providers: [HttpOperationNotificationService, HttpService, MessageService, ConfirmationService],
  templateUrl: './gestion-consultation.html'
})
export class GestionConsultation {
  sidebarItems: any = [];
  isOpen: boolean = true;
  visible_create: boolean = false;
  visible_rdv_create: boolean = false;
  visible_facture_create: boolean = false;
  visible_rdv_view: boolean = false;
  visible_facture_view: boolean = false;
  visible_edit: boolean = false;
  visible_generate: boolean = false;
  visible_activity: boolean = false;
  selectedConsultationId!: number;
  selectedConsultationIdForRdv!: number;
  selectedConsultationIdForFacture!: number;
  selectedPatient!: Patient;
  selectedActivites!: number[];
  selectedRdv!: Rdv;
  selectedFacture!: Facture;
  consultations!: Consultation[];
  medicaments!: Medicament[];
  selectedMedicamentIds: any;
  patients!: Patient[];
  activities!: Activite[]
  consultationsLoading: boolean = true;
  patientsLoading: boolean = true;


  // services
  httpService : HttpService = inject(HttpService);
  confirmationService: ConfirmationService = inject(ConfirmationService);

  //ngOnInit function
  ngOnInit(){
    this.getAllConsultations()
    this.getMedicaments()
  }

  fg = new FormGroup({
    dateDebut : new FormControl(""),
    dateFin : new FormControl(""),
    patientId : new FormControl(0),
  })

  rdv_fg = new FormGroup({
    motif : new FormControl(""),
    statut : new FormControl(""),
    date : new FormControl(""),
    consultationId : new FormControl(0),
  })

  facture_fg = new FormGroup({
    montant : new FormControl(0.0),
    statut : new FormControl(""),
    datePaiment : new FormControl(""),
    consultationId : new FormControl(0),
  })

  ordonance_fg = new FormGroup({
    consultationId : new FormControl(0),
    medicamentsIds : new FormControl<number[] | null>(null),
    description : new FormControl(""),
  })

  add_activites_fg = new FormGroup({
    consultationId : new FormControl(0),
    activiteIds : new FormControl<number[] | null>(null)
  })

  showCreateDialog(){
    this.getPatients()
    this.fg.reset();
    this.visible_create = true;
  }

  showEditDialog(consultation: Consultation){
    let data = {
      dateDebut : consultation.dateDebut,
      dateFin : consultation.dateFin,
    }
    this.fg.patchValue(data);
    this.selectedConsultationId = consultation.id;
    this.visible_edit = true;
  }
  showRdvCreateDialog(idConsultation: number){
    this.selectedConsultationIdForRdv = idConsultation;
    this.visible_rdv_create = true;
  }

  showFactureCreateDialog(idConsultation: number){
    this.selectedConsultationIdForFacture = idConsultation;
    this.visible_facture_create = true;
  }

  showRdvViewDialog(rdv: Rdv){
    this.selectedRdv = rdv;
    this.visible_rdv_view = true;
  }

  showFactureViewDialog(facture: Facture){
    this.selectedFacture = facture;
    this.visible_facture_view = true;
  }

  showOrdonanceDialog(id_consultation: number){
    this.selectedConsultationId = id_consultation;
    this.visible_generate = true
  }

  showAddActivity(id_consultation: number){
    this.getActivities()
    this.selectedConsultationId = id_consultation;
    this.visible_activity = true
  }

  getAllConsultations(){
    this.httpService.getAllConsultations().subscribe({
      next: r => {
        this.consultations = r;
        this.consultationsLoading = false
      },
      error : () => this.consultationsLoading = false
    })
  }

  getPatients(): void{
    this.httpService.getAllPatients().subscribe({
      next: r => {
        this.patients = r;
        this.patientsLoading = false
      },
      error : () => this.patientsLoading = false
    })
  }

  getMedicaments(){
    this.httpService.getAllMedicaments().subscribe({
      next: r => {
        this.medicaments = r;
      },
      error : () => {}
    })
  }

  getActivities(): void{
    this.httpService.getAllActivities().subscribe({
      next: r => {
        this.activities = r;
      },
      error : () => {}
    })
  }

  createConsultation() {
    this.fg.get("patientId")?.setValue(this.selectedPatient.id)
    this.httpService.createConsultation(this.fg.value).subscribe({
      next: () => {
        this.getAllConsultations();
        this.visible_create = false;
      }
    })
  }

  createRdv() {
    this.rdv_fg.get("consultationId")?.setValue(this.selectedConsultationIdForRdv)
    this.httpService.createRdv(this.rdv_fg.value).subscribe({
      next: () => {
        this.getAllConsultations();
        this.visible_rdv_create = false;
      }
    })
  }

  createFacture() {
    this.facture_fg.get("consultationId")?.setValue(this.selectedConsultationIdForFacture)
    let data = {}
    if(this.facture_fg.get("statut")?.value == "PAYEE"){
      data = this.facture_fg.value
    }else{
      data = {
        montant : this.facture_fg.get("montant")?.value,
        statut : this.facture_fg.get("statut")?.value,
        consultationId: this.facture_fg.get("consultationId")?.value,
      }
    }
    this.httpService.createFacture(data).subscribe({
      next: () => {
        this.getAllConsultations();
        this.visible_facture_create = false;
      }
    })
  }

  edit(e:Event) {
    e.preventDefault();
    this.httpService.updateConsultation(this.fg.value, this.selectedConsultationId).subscribe({
      next: () => {
        this.getAllConsultations();
        this.visible_edit = false;
      }
    })
    this.visible_edit = false;
  }

  deleteConsultation(id:number){
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
          this.httpService.deleteConsultation(id).subscribe({
            next: () => {
              this.getAllConsultations();
            },
            error: () => {}
          })
        },
    });
  }

  addActivites(){
    this.add_activites_fg.get("consultationId")?.setValue(this.selectedConsultationId)
    this.add_activites_fg.get("activiteIds")?.setValue(this.selectedActivites)

    this.httpService.addActivities(this.add_activites_fg).subscribe({
      next: () => {this.visible_activity = false},
      error: e => console.log(e)
    })
  }

  generateOrdonance(){
    this.ordonance_fg.get("consultationId")?.setValue(this.selectedConsultationId);
    this.ordonance_fg.get("medicamentsIds")?.setValue(this.selectedMedicamentIds);

    this.httpService.generateOrdonance(this.ordonance_fg.value).subscribe({
      next: (pdfBlob) => {
      const fileURL = URL.createObjectURL(pdfBlob);
      window.open(fileURL); // 👈 open in browser
    }
    })
  }
}
