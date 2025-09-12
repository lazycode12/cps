import { HttpService } from './../../services/http-service';
import { NgClass } from '@angular/common';
import { Component, inject, Input } from '@angular/core';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import { AuthService } from '../../services/auth-service';

@Component({
  selector: 'app-side-bar',
  imports: [NgClass, RouterLink, RouterLinkActive],
  templateUrl: './side-bar.html',
  styleUrl: "./side-bar.css"
})
export class SideBar {

  @Input() isOpen: boolean = false;
  items: String[] | null = null;

  paths : String[] = [
  'gestion-utilisateurs',
  'gestion-roles-permissions',
  'gestion-activites',
  'gestion-medicaments',
  'gestion-patients',
  'gestion-consultations',
  'gestion-rdvs',
  'gestion-factures',
  'consulter-statistiques'
];

  httpService : HttpService = inject(HttpService);
  router: Router = inject(Router)



  ngOnInit(){
    let authoroties = localStorage.getItem("authoroties")
    if(authoroties){
      this.items = JSON.parse(authoroties) ;
      this.items = this.items!.filter(e => this.paths.includes(e))
    }
  }


  logout() {
    this.httpService.logout().subscribe({
      next: () => {
        localStorage.clear()
        this.router.navigate(['/login'])
      }
    })
  }
}
