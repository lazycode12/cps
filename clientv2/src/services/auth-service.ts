import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  baseUrl: string = "http://localhost:8080/auth/";
  authorities!: String[];

  constructor(private http: HttpClient, private router: Router){}

  login(credentials: any) : Observable<any> {
    return this.http.post<any>(`${this.baseUrl}login`, credentials).pipe(
      tap(response => {
        // Store the JWT token
        localStorage.setItem('token', response.token);
        this.authorities = response.authorities;
        localStorage.setItem('authoroties', JSON.stringify(response.authorities))
      })
    )
  }

  getToken(key:string): string | null {
    return localStorage.getItem(key);
  }

  private getAuthHeaders(): HttpHeaders {
    const token = this.getToken('accestoken');
    let headers = new HttpHeaders().set('Content-Type', 'application/json');
    if (token) {
      headers = headers.set('Authorization', `Bearer ${token}`);
    }
    return headers;
  }

  logout(data:any){
    return this.http.get<any>(`${this.baseUrl}logout`)
  }


  // logout(): Observable<void>{

  // }

  deleteAccount() {
  }
}
