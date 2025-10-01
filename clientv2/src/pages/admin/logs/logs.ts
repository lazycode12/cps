import { Component, inject } from '@angular/core';

import { TableModule } from 'primeng/table';
import { HttpService } from '../../../services/http-service';
import { ButtonModule } from 'primeng/button';
import { TagModule } from 'primeng/tag';
import { SideBar } from '../../../components/side-bar/side-bar';
import { NavBar } from '../../../components/nav-bar/nav-bar';
import { NgClass } from '@angular/common';
import { LogEntry } from '../../../interfaces/interfaces';
import { MessageService } from 'primeng/api';
import { HttpOperationNotificationService } from '../../../services/http-operation-notification-service';

@Component({
  selector: 'app-logs',
  imports: [TableModule,SideBar, NavBar, NgClass, ButtonModule , TagModule],
  providers: [MessageService, HttpOperationNotificationService, HttpService],
  templateUrl: './logs.html',
  styles: ``
})
export class Logs {
  isOpen: boolean = true;
  loading: boolean = false
  logs: any

  httpService : HttpService = inject(HttpService);

  ngOnInit(){
    this.getLogs()
  }

  getLogs(){
    return this.httpService.getAllLogs().subscribe({
      next: r => {
        this.logs = r;
        this.loading = false
      },
      error: r => this.loading = false
    })
  }
}
