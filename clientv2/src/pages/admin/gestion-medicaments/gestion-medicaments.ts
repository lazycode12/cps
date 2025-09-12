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
import { Medicament } from '../../../interfaces/interfaces';

@Component({
  selector: 'app-gestion-medicaments',
  imports: [TableModule, ConfirmDialog,ToastModule,SideBar, NavBar, NgClass, FormsModule, Dialog, ButtonModule, ReactiveFormsModule, TagModule],
  providers: [HttpOperationNotificationService, HttpService, MessageService, ConfirmationService],
  templateUrl: './gestion-medicaments.html'
})
export class GestionMedicaments {
  sidebarItems: any = [];
  isOpen: boolean = true;
  visible_create: boolean = false;
  visible_edit: boolean = false;
  medicaments!: Medicament[];
  selectedMedicamentId!: number;
  loading: boolean = true;

  // services
  // sideBarService: SideBarDataServiceService = inject(SideBarDataServiceService);
  confirmationService: ConfirmationService = inject(ConfirmationService);
  httpService : HttpService = inject(HttpService);

  ngOnInit(){
    // this.sidebarItems = this.sideBarService.getSideBarData("admin-user");
    this.getMedicaments()
  }

  fg = new FormGroup({
    nom : new FormControl(""),
    qte : new FormControl(0),
    description : new FormControl("")
  })

  getMedicaments(){
    this.loading = true
    this.httpService.getAllMedicaments().subscribe({
      next: r => {
        this.medicaments = r;
        this.loading = false
      },
      error : () => this.loading = false
    })
  }

  showCreateDialog(){
    this.visible_create = true;
  }

  showEditDialog(medicament: Medicament){
    this.fg.patchValue(medicament);
    this.selectedMedicamentId = medicament.id;
    this.visible_edit = true;
  }


  create() {
    this.httpService.createMedicament(this.fg.value).subscribe({
      next: () => {
        this.getMedicaments();
        this.visible_create = false;
      }
    })
  }

  edit(e:Event) {
    e.preventDefault();
    this.httpService.updateMedicament(this.fg.value, this.selectedMedicamentId).subscribe({
      next: () => {
        this.getMedicaments();
        this.visible_edit = false;
      }
    })
    this.visible_edit = false;
  }



  deleteMedicament(id:number){
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
          this.httpService.deleteMedicament(id).subscribe({
            next: () => {
              this.getMedicaments();
            },
            error: () => {}
          })
        },
    });
  }
}
