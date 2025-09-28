import { Component } from '@angular/core';
import { NotificationService } from '../../services/notification-service';
import { DatePipe } from '@angular/common';
import { ButtonModule } from 'primeng/button';

export interface notification {
  id: number;
  message: string;
  type: 'warning' | 'info' | 'success' | 'error';
  timestamp: Date;
  read: boolean;
}

@Component({
  selector: 'app-notification',
  imports: [DatePipe, ButtonModule],
  templateUrl: './notification.html',
  styles: ``
})
export class Notification {
  showNotificationPanel = false;
  notifications: notification[] = [];
  unreadCount = 0;

  constructor(private notificationService: NotificationService) {}

  ngOnInit() {
    this.notificationService.getNotifications().subscribe(notifications => {
      this.notifications = notifications;
      this.unreadCount = this.notificationService.getUnreadCount();
    });
  }

  toggleNotifications() {
    this.showNotificationPanel = !this.showNotificationPanel;
  }

  markAsRead(id: number) {
    this.notificationService.markAsRead(id);
  }

  markAllAsRead() {
    this.notificationService.markAllAsRead();
  }

  clearAll() {
    this.notificationService.clearAll();
  }

  getNotificationIcon(type: string): string {
    const icons = {
      warning: 'pi pi-exclamation-triangle text-warning',
      info: 'pi pi-info-circle text-info',
      success: 'pi pi-check-circle text-success',
      error: 'pi pi-times-circle text-danger'
    };
    return icons[type as keyof typeof icons] || 'pi pi-bell';
  }
}
