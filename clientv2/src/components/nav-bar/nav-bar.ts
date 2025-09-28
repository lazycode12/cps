import { Component, EventEmitter, Output, ViewChild } from '@angular/core';
import { OverlayBadgeModule } from 'primeng/overlaybadge';
import { Notification } from '../notification/notification';
import { NotificationService } from '../../services/notification-service';

@Component({
  selector: 'app-nav-bar',
  imports: [OverlayBadgeModule, Notification],
  templateUrl: './nav-bar.html'
})
export class NavBar {
  @Output() toggle = new EventEmitter<void>();
  @ViewChild(Notification) notification!: Notification;

  toggleSideBar(){
      this.toggle.emit();
  }
  showNotificationsPanel = false;
  unreadCount = 0;

  constructor(private notificationService: NotificationService) {}

  ngOnInit() {
    this.notificationService.getNotifications().subscribe(() => {
      this.unreadCount = this.notificationService.getUnreadCount();
    });
  }

  toggleNotifications() {
    // this.showNotificationsPanel = !this.showNotificationsPanel;
    this.notification.toggleNotifications()

  }

  showNotifications() {
    this.showNotificationsPanel = true;
  }
}
