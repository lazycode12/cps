import { inject, Injectable } from '@angular/core';
import { MessageService } from 'primeng/api';
import { AppModule } from '../app/app.module';

export type ToastSeverity = 'success' | 'info' | 'warn' | 'error';

@Injectable({
  providedIn: AppModule,
})
export class HttpOperationNotificationService {


  messageService : MessageService  = inject(MessageService)
  constructor() {}
  show(severity: ToastSeverity, summary: string, detail: string, life: number = 3000): void {
  this.messageService.add({
    severity: severity,
    summary: summary,
    detail: detail,
    life: life
  });
  }

  showSuccess(message: string, title: string = 'Success'): void {
    this.show('success', title, message);
  }

  showError(message: string, title: string = 'Error'): void {
    this.show('error', title, message);
  }

  showInfo(message: string, title: string = 'Info'): void {
    this.show('info', title, message);
  }

  showWarning(message: string, title: string = 'Warning'): void {
    this.show('warn', title, message);
  }

  clear(): void {
    this.messageService.clear();
  }

}
