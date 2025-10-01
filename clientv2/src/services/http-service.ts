import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { catchError, Observable, tap, throwError } from 'rxjs';
import { HttpOperationNotificationService } from './http-operation-notification-service';
import { Consultation, Role } from '../interfaces/interfaces';

@Injectable({
  providedIn: 'root'
})
export class HttpService {

  httpOperationNotificationService: HttpOperationNotificationService = inject(HttpOperationNotificationService)

  baseUrl: string = "http://localhost:8080/";
  token: string | null = localStorage.getItem('token');

  private defaultHeaders = {
    'Content-Type': 'application/json',
    "Authorization": `Bearer ${this.token}`
  };

  private handleError(error: HttpErrorResponse) {
    let errorMessage = 'An unknown error occurred!';

    if (error.error instanceof ErrorEvent) {
      // Client-side error
      errorMessage = `Error: ${error.error.message}`;
    } else {
      // Server-side error - check if your API returns structured error responses
      if (error.error && typeof error.error === 'object') {
        // Try to extract message from your API's error response structure
        const apiError = error.error;

        if (apiError.message) {
          errorMessage = apiError.message;
        } else if (apiError.error) {
          errorMessage = apiError.error;
        } else {
          errorMessage = `Error ${error.status}: ${error.message}`;
        }
      } else if (typeof error.error === 'string') {
        // If the error is a simple string
        errorMessage = error.error;
      } else {
        // Fallback to HTTP status message
        errorMessage = `Error ${error.status}: ${error.message}`;
      }

      // You can still keep your status-based custom messages as fallback
      switch (error.status) {
        case 404:
          errorMessage = errorMessage || 'Resource not found';
          break;
        case 401:
          errorMessage = errorMessage || 'Unauthorized access';
          break;
        case 500:
          errorMessage = errorMessage || 'Server error occurred';
          break;
      }
    }

    this.httpOperationNotificationService.showError(errorMessage);
    return throwError(() => new Error(errorMessage));
  }

  constructor(private http: HttpClient){

  }

  // ****************************** Generic CRUD Methods ******************************

  private post<T>(endpoint: string, data:any): Observable<T> {
    return this.http.post<T>(`${this.baseUrl}${endpoint}`,data , { headers: this.defaultHeaders })
      .pipe(
        catchError(this.handleError.bind(this)),
        tap( (response: any) => {
          if(response && response.message){
            this.httpOperationNotificationService.showSuccess(response.message)
          }
        })
      );
  }


  private get<T>(endpoint: string): Observable<T> {
    return this.http.get<T>(`${this.baseUrl}${endpoint}`, {headers: {"Authorization": `Bearer ${this.token}`}})
      .pipe(catchError(this.handleError.bind(this)));
  }

  private put<T>(endpoint: string, data: any): Observable<T> {
    return this.http.put<T>(`${this.baseUrl}${endpoint}`, data, { headers: this.defaultHeaders })
      .pipe(
        catchError(this.handleError.bind(this)),
        tap( (response: any) => {
          if(response && response.message){
            this.httpOperationNotificationService.showSuccess(response.message)
          }
        })
      );
  }

  private delete<T>(endpoint: string): Observable<T> {
    return this.http.delete<T>(`${this.baseUrl}${endpoint}`, { headers: this.defaultHeaders })
      .pipe(
        catchError(this.handleError.bind(this)),
        tap( (response: any) => {
          if(response && response.message){
            this.httpOperationNotificationService.showSuccess(response.message)
          }
        })
      );
  }

  // ****************************** generate ******************************
  generateOrdonance(data: any){
    return this.http.post(`${this.baseUrl}generate/ordonance`, data, {headers:{"Authorization": `Bearer ${this.token}`}, responseType: 'blob'})
  }

  generateFacture(consultatino_id: any){
    return this.http.post(`${this.baseUrl}generate/facture/${consultatino_id}`,{}, {headers:{"Authorization": `Bearer ${this.token}`}, responseType: 'blob'})
  }


  // ****************************** uers ******************************

  createUser(data:any){
    return this.post<any>("users", data)
  }
  getAllUsers(): Observable<any>{
    return this.get<any>("users")
  }

  getUser(id:number): Observable<any>{
    return this.get<any>(`users/${id}`)
  }

  updateUser(data:any, id:number): Observable<any>{
    return this.put(`users/${id}`, data)
  }

  deleteUser(id:number): Observable<any>{
    return this.delete(`users/${id}`)
  }



  // ****************************** roles ******************************

  createRole(data:any){
    return this.post<any>("roles", data)
  }
  getAllRoles(): Observable<any>{
    return this.get<any>("roles")
  }

  getRole(id:number): Observable<any>{
    return this.get<any>(`roles/${id}`)
  }

  updateRole(data:any, id:number): Observable<any>{
    return this.put(`roles/${id}`, data)
  }

  deleteRole(id:number): Observable<any>{
    return this.delete(`roles/${id}`)
  }

  updateRolesPermissions(roles: Role[]){
    return this.put("roles/updatepermissions", roles)
  }



  // ****************************** activities ******************************
  createActivitie(data:any){
    return this.post<any>("activites", data)
  }
  getAllActivities(): Observable<any>{
    return this.get<any>(`activites`)
  }

  getActivity(id:number): Observable<any>{
    return this.get<any>(`activites/${id}`)
  }

  updateActivity(data:any, id:number): Observable<any>{
    return this.put(`activites/${id}`, data)
  }

  deleteActivity(id:number): Observable<any>{
    return this.delete(`activites/${id}`)
  }


  // ****************************** activities ******************************
  createProduit(data:any){
    return this.post<any>("produits", data)
  }
  getAllProduits(): Observable<any>{
    return this.get<any>(`produits`)
  }

  getProduit(id:number): Observable<any>{
    return this.get<any>(`produits/${id}`)
  }

  updateProduit(data:any, id:number): Observable<any>{
    return this.put(`produits/${id}`, data)
  }

  deleteProduit(id:number): Observable<any>{
    return this.delete(`produits/${id}`)
  }


  // ****************************** Consultations ******************************
  createConsultation(data:any){
    return this.post<any>("consultations", data)
  }

  addActivities(data:any){
    return this.http.post<any>(`${this.baseUrl}consultations/addactivities`, data)
  }

  getAllConsultations(): Observable<Consultation[]>{
    return this.get<Consultation[]>("consultations")
  }

  getAllConsultationsForActivity(): Observable<any>{
    return this.get<any>("consultations/foractivity")
  }

  getConsultation(id:number): Observable<any>{
    return this.get<any>(`consultations/${id}`)
  }

  updateConsultation(data:any, id:number): Observable<any>{
    return this.put(`consultations/${id}`, data)
  }

  deleteConsultation(id:number): Observable<any>{
    return this.delete(`consultations/${id}`)
  }


  // ****************************** factures ******************************

  createFacture(data:any){
    return this.post<any>("factures", data)
  }

  getAllFactures(): Observable<any>{
    return this.get<any>("factures")
  }

  getFacture(id:number): Observable<any>{
    return this.get<any>(`factures/${id}`)
  }

  updateFacture(data:any, id:number): Observable<any>{
    return this.put(`factures/${id}`, data)
  }

  deleteFacture(id:number): Observable<any>{
    return this.delete(`factures/${id}`)
  }



  // ****************************** Patients ******************************

  createPatient(data:any){
    return this.post<any>("patients", data)
  }

  getAllPatients(): Observable<any>{
    return this.get<any>("patients")
  }

  getPatient(id:string): Observable<any>{
    return this.get<any>(`patients/${id}`)
  }

  updatePatient(data:any, id:number): Observable<any>{
    return this.put(`patients/${id}`, data)
  }

  deletePatient(id:number): Observable<any>{
    return this.delete(`patients/${id}`)
  }



  // ****************************** Permissions ******************************

  createPermission(data:any){
    return this.post<any>("permissions", data)
  }

  getAllPermissions(): Observable<any>{
    return this.get<any>("permissions")
  }

  getPermissione(id:number): Observable<any>{
    return this.get<any>(`permissions/${id}`)
  }

  updatePermission(data:any, id:number): Observable<any>{
    return this.put(`permissions/${id}`, data)
  }

  deletePermission(id:number): Observable<any>{
    return this.delete(`permissions/${id}`)
  }



  // ****************************** Rdvs ******************************

  createRdv(data:any){
    return this.post<any>("rdvs", data)
  }

  getAllRdvs(): Observable<any>{
    return this.get<any>("rdvs")
  }

  getRdv(id:number): Observable<any>{
    return this.get<any>(`rdvs/${id}`)
  }

  updateRdv(data:any, id:number): Observable<any>{
    return this.put(`rdvs/${id}`, data)
  }

  deleteRdv(id:number): Observable<any>{
    return this.delete(`rdvs/${id}`)
  }


  // ****************************** TypeConsultation ******************************

  createTypeConsultation(data:any){
    return this.post<any>("type-consultations", data)
  }
  getAllTypeConsultations(): Observable<any>{
    return this.get<any>("type-consultations")
  }

  getTypeConsultation(id:number): Observable<any>{
    return this.get<any>(`type-consultations/${id}`)
  }

  updateTypeConsultation(data:any, id:number): Observable<any>{
    return this.put(`type-consultations/${id}`, data)
  }

  deleteTypeConsultation(id:number): Observable<any>{
    return this.delete(`type-consultations/${id}`)
  }



  // ****************************** medicaments ******************************

  createMedicament(data:any){
    return this.post<any>("medicaments", data)
  }

  getAllMedicaments(): Observable<any>{
    return this.get<any>("medicaments")
  }

  getMedicament(id:number): Observable<any>{
    return this.get<any>(`medicaments/${id}`)
  }

  updateMedicament(data:any, id:number): Observable<any>{
    return this.put(`medicaments/${id}`, data)
  }

  deleteMedicament(id:number): Observable<any>{
    return this.delete(`medicaments/${id}`)
  }


  // ****************************** documents medical ******************************

  uploadMedicalDocument(data:FormData){
    return this.http.post(`${this.baseUrl}documents-medicales`, data, {headers: {"Authorization": `Bearer ${this.token}`}})
            .pipe(
              catchError(this.handleError.bind(this)),
              tap( (response: any) => {
                if(response && response.message){
                  this.httpOperationNotificationService.showSuccess(response.message)
                }
              }));
  }

  getDocumentsByPatientId(id: string): Observable<any>{
    return this.get<any>(`documents-medicales/bypatient/${id}`)
  }

  getDocumentMedical(id:number): Observable<any>{
    return this.get<any>(`documents-medicales/${id}`)
  }

  deleteDocumentMedical(id:number): Observable<any>{
    return this.delete(`medicaments/${id}`)
  }

  logout(){
    return this.http.post(`${this.baseUrl}logout`, {})
  }

  // ****************************** log ******************************
  getAllLogs(){
    return this.get<any>("logs")
  }
}
