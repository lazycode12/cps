import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-nav-bar',
  imports: [],
  templateUrl: './nav-bar.html'
})
export class NavBar {
  @Output() toggle = new EventEmitter<void>();

  toggleSideBar(){
      this.toggle.emit();
    }
}
