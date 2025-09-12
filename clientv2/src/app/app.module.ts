import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';
import { HttpOperationNotificationService } from '../services/http-operation-notification-service';

@NgModule({
  imports: [
    BrowserModule,
    ToastModule, // Import ToastModule
  ],
  providers: [
    MessageService,
    HttpOperationNotificationService // Add MessageService to providers array
    // ... other services
  ],
  // ... other module properties
})
export class AppModule { }