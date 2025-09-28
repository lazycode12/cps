import { Component, inject } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SideBar } from '../../../components/side-bar/side-bar';
import { NavBar } from '../../../components/nav-bar/nav-bar';
import { NgClass } from '@angular/common';
import { Dialog } from 'primeng/dialog';
import { ButtonModule } from 'primeng/button';
import { TagModule } from 'primeng/tag';
import { ConfirmDialog } from 'primeng/confirmdialog';
import { ToastModule } from 'primeng/toast';
import { ConfirmationService, MessageService } from 'primeng/api';
import { TableModule } from 'primeng/table';
import { HttpOperationNotificationService } from '../../../services/http-operation-notification-service';
import { HttpService } from '../../../services/http-service';
import { NotificationService } from '../../../services/notification-service';

@Component({
  selector: 'app-gestion-produits',
  imports: [TableModule, ConfirmDialog,ToastModule,SideBar, NavBar, NgClass, FormsModule, Dialog, ButtonModule, ReactiveFormsModule, TagModule],
  providers: [HttpOperationNotificationService, HttpService, MessageService, ConfirmationService],
  templateUrl: './gestion-produits.html',
  styles: ``
})
export class GestionProduits {

  isOpen: boolean = true;
  visible_create: boolean = false;
  visible_edit: boolean = false;
  loading:boolean = true;
  produits!:any[];
  selectedProduitId!: number;

  // Store original product data for comparison
  private originalProduit: any;

  // services
  // sideBarService: SideBarDataServiceService = inject(SideBarDataServiceService);
  confirmationService: ConfirmationService = inject(ConfirmationService);
  httpService : HttpService = inject(HttpService);
  notificationService: NotificationService = inject(NotificationService)

  //ngOnInit function
  ngOnInit(){
    this.getProduits()
  }

  getProduits(){
    this.httpService.getAllProduits().subscribe({
      next: r => {
        this.produits = r;
        this.loading = false
      },
      error : () => this.loading = false
    })
  }

  fg = new FormGroup({
    nom : new FormControl(""),
    categorie : new FormControl(""),
    quantite : new FormControl(0),
    unite : new FormControl(""),
    seuilReapprovisionnement: new FormControl(1)
  })

  showEditDialog(produit:any){
    this.originalProduit = { ...produit };
    this.fg.patchValue(produit);
    this.selectedProduitId = produit.id;
    this.visible_edit = true;
  }
  showCreateDialog() {
    this.visible_create = true;
  }

  create() {
    this.httpService.createProduit(this.fg.value).subscribe({
      next: () => {
        this.getProduits()
        this.visible_create = false
      }
    })
  }

  private checkLowStockNotification(updatedProduit: any, oldQuantity: number, newQuantity: number) {
    const seuil = updatedProduit.seuilReapprovisionnement;

    // Check if quantity decreased and is now at or below threshold
    if (seuil !== null && seuil !== undefined && newQuantity <= seuil) {
      // Optional: Only notify if it wasn't already below threshold
      const wasAlreadyBelowThreshold = oldQuantity <= seuil;

      if (!wasAlreadyBelowThreshold) {
        this.createLowStockNotification(updatedProduit);
      } else if (newQuantity < oldQuantity) {
        // Notify even if already below threshold, but quantity decreased further
        this.createLowStockNotification(updatedProduit);
      }
    }
  }

  private createLowStockNotification(produit: any) {
    const message = `Stock faible: ${produit.nom} (${produit.quantite} ${produit.unite}) - Seuil: ${produit.seuilReapprovisionnement} ${produit.unite}`;
    this.notificationService.addNotification(message, 'warning');
  }

  edit(e:Event) {
    e.preventDefault();
    const formValue = this.fg.value;
    const newQuantity = formValue.quantite ?? 0;
    const oldQuantity = this.originalProduit?.quantite ?? 0;

    this.httpService.updateProduit(this.fg.value, this.selectedProduitId).subscribe({
      next: () => {
        this.getProduits();
        this.visible_edit = false;
        this.checkLowStockNotification(formValue, oldQuantity, newQuantity);
      }
    })
    this.visible_edit = false;
  }

  deleteProduit(id:number){
    this.confirmationService.confirm({
        message: 'Veuillez confirmer pour continuer',
        header: 'Confirmation',
        closable: true,
        closeOnEscape: true,
        icon: 'pi pi-exclamation-triangle',
        rejectButtonProps: {
            label: 'Annuler',
            severity: 'secondary',
            outlined: true,
        },
        acceptButtonProps: {
            label: 'supprimer',
            severity: 'danger',
        },
        accept: () => {
          this.httpService.deleteProduit(id).subscribe({
            next: () => {
              this.getProduits();
            },
            error: () => {}
          })
        },
    });
  }
}
