import { Component, inject } from '@angular/core';
import { AuthService } from '../../services/auth-service';
import { Router, RouterModule } from '@angular/router';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule, RouterModule],
  // providers: [AuthService],
  templateUrl: './login.html',
})
export class Login {
  user:any;
  errMessage:string = "";

  //headers
  // headers = new HttpHeaders().set('Content-Type', 'application/json');

  //services
  private authService: AuthService = inject(AuthService);
  // private utilisateurService: UtilisateurService = inject(UtilisateurService)
  private router: Router = inject(Router)
  private formBuilder = inject(FormBuilder);

  fg = this.formBuilder.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', Validators.required]
  })

  isInvalid(field: string): boolean {
    return this.fg.get(field)!.invalid && this.fg.get(field)!.touched;
  }

  login(e: Event){
    e.preventDefault();
    this.authService.login(this.fg.value).subscribe({
      next: (r) => {
        this.router.navigate([`/${r.authorities[1]}`])
      }
    })
  }


  navigate(role:string){
    switch (role) {
      case 'etudiant':
        this.router.navigate(["/etudiant-home"]);
        break;
      case 'enseignant':
        this.router.navigate(["/create-quiz"]);
        break;
      default:
        this.router.navigate(["/gestion-classes"]);
        break;
    }
  }
}
