import { Activite, Facture, Medicament, Rdv } from './../../../interfaces/interfaces';
import { Component, inject } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
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
import { Router, RouterModule } from '@angular/router';
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
  types:any;
  consultationsLoading: boolean = true;
  patientsLoading: boolean = true;
  ordonnanceForm!: FormGroup;

// services
httpService: HttpService = inject(HttpService);
confirmationService: ConfirmationService = inject(ConfirmationService);
router: Router = inject(Router);

constructor(private fb: FormBuilder) {
  this.ordonnanceForm = this.fb.group({
    consultationId: [0],
    req: this.fb.array([]) // ← Nom correct
  });
}

get medicamentsArray(): FormArray {
  return this.ordonnanceForm.get('req') as FormArray;
}

nouveauMedicament(): FormGroup {
  return this.fb.group({
    nom: ['', Validators.required],
    dosage: ['', Validators.required],
    forme: ['', Validators.required],
    posologie: ['', Validators.required],
    duree: ['', [Validators.required]] // ← Correction : "duree" au lieu de "duree"
  });
}

ajouterMedicament(): void {
  this.medicamentsArray.push(this.nouveauMedicament());
}

supprimerMedicament(index: number): void {
  this.medicamentsArray.removeAt(index);
}

generateOrdonance() {
  if (this.ordonnanceForm.valid) {
    this.ordonnanceForm.get('consultationId')?.setValue(this.selectedConsultationId)
    const ordonnance = this.ordonnanceForm.value; // ← Correction ici aussi
    console.log('Ordonnance enregistrée:', ordonnance);

    this.httpService.generateOrdonance(ordonnance).subscribe({
      next: (pdfBlob) => {
      const fileURL = URL.createObjectURL(pdfBlob);
      window.open(fileURL); // 👈 open in browser
    }
    })
  } else {
    console.log('Formulaire invalide');
  }
}


  //ngOnInit function
  ngOnInit(){
    this.getAllConsultations()
    this.getMedicaments()
    this.getTypes()
  }

  fg = new FormGroup({
    dateDebut : new FormControl(""),
    dateFin : new FormControl(""),
    patientId : new FormControl(0),
    typeId: new FormControl<number>(0)
  })

  rdv_fg = new FormGroup({
    motif : new FormControl(""),
    statut : new FormControl(""),
    date : new FormControl(""),
    consultationId : new FormControl(0),
  })

  facture_fg = new FormGroup({
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
  navigate(id: number){
    // this.selectedConsultationIdForRdv = id;
    // this.visible_rdv_create = true;
    this.router.navigate(["/create-rdvs", id])
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

  getTypes(): void{
    this.httpService.getAllTypeConsultations().subscribe({
      next: r => {
        this.types = r;
      },
      error : () => {}
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

    this.httpService.createFacture(this.facture_fg.value).subscribe({
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


}
