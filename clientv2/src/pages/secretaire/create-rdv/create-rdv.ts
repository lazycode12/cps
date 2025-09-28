import { Component, inject } from '@angular/core';
import { NavBar } from '../../../components/nav-bar/nav-bar';
import { SideBar } from '../../../components/side-bar/side-bar';
import { NgClass } from '@angular/common';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { HttpService } from '../../../services/http-service';
import { FullCalendarModule } from '@fullcalendar/angular';
import { CalendarOptions, EventInput } from '@fullcalendar/core/index.js';
import dayGridPlugin from '@fullcalendar/daygrid';
import { HttpOperationNotificationService } from '../../../services/http-operation-notification-service';
import { MessageService } from 'primeng/api';
import { Rdv } from '../../../interfaces/interfaces';

@Component({
  selector: 'app-create-rdv',
  imports: [SideBar, NavBar, NgClass, ReactiveFormsModule, FullCalendarModule],
  providers: [HttpOperationNotificationService, HttpService, MessageService],
  templateUrl: './create-rdv.html'
})
export class CreateRdv {
  isOpen: boolean = true;
  selectedConsultationId!: string;
  loading: boolean = true;
  rdvs!: Rdv[];
  events: EventInput[] = [];
  // services
  httpService : HttpService = inject(HttpService);

  rdv_fg = new FormGroup({
    motif : new FormControl(""),
    statut : new FormControl(""),
    date : new FormControl(""),
    consultationId : new FormControl<string>(""),
  })

  calendarOptions: CalendarOptions = {
    initialView: 'dayGridMonth',
    plugins: [dayGridPlugin],
    events: []
  };

  async ngOnInit(){
    await this.getRdvs();
    this.updateCalendarEvents();
  }

  getRdvs(): Promise<void> {
    return new Promise((resolve) => {
      this.httpService.getAllRdvs().subscribe({
        next: (r) => {
          this.rdvs = r;
          this.loading = false;
          resolve();
        },
        error: (err) => {
          this.loading = false;
          resolve();
        }
      });
    });
  }

  private updateCalendarEvents(): void {
    this.events = this.rdvs.map((rdv: Rdv) => ({
      title: rdv.motif,
      date: rdv.date // Ensure this matches your API date format
    }));

    // Update calendar options
    this.calendarOptions = {
      ...this.calendarOptions,
      events: this.events
    };
  }

  createRdv() {
    this.rdv_fg.get("consultationId")?.setValue(this.selectedConsultationId)
    this.httpService.createRdv(this.rdv_fg.value).subscribe({
      next: () => {
      }
    })
  }

}
