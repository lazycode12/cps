import { Component, inject } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SideBar } from '../../components/side-bar/side-bar';
import { NavBar } from '../../components/nav-bar/nav-bar';
import { NgClass } from '@angular/common';
import { Dialog } from 'primeng/dialog';
import { ButtonModule } from 'primeng/button';
import { TagModule } from 'primeng/tag';
import { ConfirmDialog } from 'primeng/confirmdialog';
import { ToastModule } from 'primeng/toast';
import { ConfirmationService, MessageService } from 'primeng/api';
import { TableModule } from 'primeng/table';
import { MultiSelectModule } from 'primeng/multiselect';
import { SelectModule } from 'primeng/select';
import { HttpService } from '../../services/http-service';
import { HttpOperationNotificationService } from '../../services/http-operation-notification-service';
import { Patient } from '../../interfaces/interfaces';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-patient-profile',
  imports: [SelectModule, MultiSelectModule,TableModule, ConfirmDialog,ToastModule,SideBar, NavBar, NgClass, FormsModule, Dialog, ButtonModule, ReactiveFormsModule, TagModule],
providers: [HttpOperationNotificationService, HttpService, MessageService, ConfirmationService],
  templateUrl: './patient-profile.html'
})
export class PatientProfile {
  sidebarItems: any = [];
  isOpen: boolean = true;
  patient!: Patient;
  documents:any;
  categories = ['analyse', 'radio', 'compte rendu'];
  upload_visibility: boolean = false;
  loading: boolean = true;
  readonly patientId: string;
  private route = inject(ActivatedRoute);

  confirmationService: ConfirmationService = inject(ConfirmationService);
  httpService : HttpService = inject(HttpService);

  fg!: FormGroup;

  constructor(){
    const id = this.route.snapshot.paramMap.get('id');
    if (id === null) {
      throw new Error("Patient ID not found in route parameters.");
    }
    this.patientId = id;

    this.fg = new FormGroup({
      typeDocument: new FormControl(""),
      file: new FormControl<File | null>(null),
      patientId : new FormControl(this.patientId)
    });
  }

  ngOnInit(){
    this.getPatient()
    this.getDocumentsByPatientId()
  }

  onFileSelected(event: Event) {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      const file = input.files[0];
      this.fg.get('file')?.setValue(file);
    }
  }


  getPatient(): void{
    this.httpService.getPatient(this.patientId).subscribe({
      next: r => {
        this.patient = r;
        this.loading = false
      },
      error : () => this.loading = false
    })
  }

  getDocumentsByPatientId(){
    this.loading = true
    this.httpService.getDocumentsByPatientId(this.patientId).subscribe({
      next: r => {
        this.documents = r;
        this.loading = false
      },
      error : () => this.loading = false
    })
  }

  showUploadDialogue(){
    this.upload_visibility = true
  }

upload() {
  const formData = new FormData();
  const file: File = this.fg.get('file')?.value;

  let metadata = {
    fileType: this.fg.get('typeDocument')?.value,
    patientId: this.patientId
  }

  const jsonBlob = new Blob([JSON.stringify(metadata)], {
    type: 'application/json'
  });

  // Append both parts with correct field names
  formData.append('file', file);
  formData.append('req', jsonBlob);

  // Use the formData, not this.fg.value
  this.httpService.uploadMedicalDocument(formData).subscribe({
    next: r => {
      this.getDocumentsByPatientId()
      this.upload_visibility = false
    },
    error: e => console.log(e)
  });
}
}
