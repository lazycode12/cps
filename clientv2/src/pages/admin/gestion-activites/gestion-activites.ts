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
import { Activite, ConsultationForActivity } from '../../../interfaces/interfaces';

@Component({
  selector: 'app-gestion-activites',
  imports: [TableModule, ConfirmDialog,ToastModule,SideBar, NavBar, NgClass, FormsModule, Dialog, ButtonModule, ReactiveFormsModule, TagModule],
  providers: [HttpOperationNotificationService, HttpService, MessageService, ConfirmationService],
  templateUrl: './gestion-activites.html'
})
export class GestionActivites {
  sidebarItems: any = [];
  isOpen: boolean = true;
  visible_create: boolean = false;
  visible_edit: boolean = false;
  activities!: Activite[]
  consultations!: ConsultationForActivity[];
  selectedActivityId!: number;
  selectedConsultation!: ConsultationForActivity;
  loading: boolean = true;
  loadingForActivity: boolean = true;

  // services
  // sideBarService: SideBarDataServiceService = inject(SideBarDataServiceService);
  confirmationService: ConfirmationService = inject(ConfirmationService);
  httpService : HttpService = inject(HttpService);


  //ngOnInit function
  ngOnInit(){
    // this.sidebarItems = this.sideBarService.getSideBarData("admin-user");
    this.getActivities()
    this.getConsultationsForActivity()
  }

  fg = new FormGroup({
    type : new FormControl(""),
    statut : new FormControl(""),
    date : new FormControl(""),
    consultationId : new FormControl(0)
  })

  getActivities(): void{
    this.httpService.getAllActivities().subscribe({
      next: r => {
        this.activities = r;
        this.loading = false
      },
      error : () => this.loading = false
    })
  }

  getConsultationsForActivity(){
    this.httpService.getAllConsultationsForActivity().subscribe({
      next: r => {
        this.consultations = r;
        this.loadingForActivity = false
      }
    })
  }

  showEditDialog(activity:Activite){
    this.fg.patchValue(activity);
    this.selectedActivityId = activity.id;
    this.visible_edit = true;
  }
  showCreateDialog() {
    this.visible_create = true;
  }

  create() {
    this.fg.get("consultationId")?.setValue(this.selectedConsultation.id)
    this.httpService.createActivitie(this.fg.value).subscribe({
      next: () => {
        this.getActivities();
        this.visible_create = false;
      }
    })
  }

  edit(e:Event) {
    e.preventDefault();
    this.httpService.updateActivity(this.fg.value, this.selectedActivityId).subscribe({
      next: () => {
        this.getActivities();
        this.visible_edit = false;
      }
    })
    this.visible_edit = false;
  }



  deleteActivity(id:number){
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
          this.httpService.deleteActivity(id).subscribe({
            next: () => {
              this.getActivities();
            },
            error: () => {}
          })
        },
    });
  }
}
