import { Routes } from '@angular/router';
import { GestionUtlisateur } from '../pages/admin/gestion-utlisateur/gestion-utlisateur';
import { GestionRolesPermissions } from '../pages/admin/gestion-roles-permissions/gestion-roles-permissions';
import { GestionActivites } from '../pages/admin/gestion-activites/gestion-activites';
import { GestionPatients } from '../pages/secretaire/gestion-patients/gestion-patients';
import { GestionConsultation } from '../pages/secretaire/gestion-consultation/gestion-consultation';
import { GestionRdvs } from '../pages/secretaire/gestion-rdvs/gestion-rdvs';
import { GestionFactures } from '../pages/secretaire/gestion-factures/gestion-factures';
import { PatientProfile } from '../pages/patient-profile/patient-profile';
import { GestionMedicaments } from '../pages/admin/gestion-medicaments/gestion-medicaments';
import { Login } from '../pages/login/login';
import { Statistics } from '../pages/statistics/statistics';

export const routes: Routes = [
    {path: 'gestion-utilisateurs', component: GestionUtlisateur},
    {path: 'gestion-roles-permissions', component: GestionRolesPermissions},
    {path: 'gestion-activites', component: GestionActivites},
    {path: 'gestion-medicaments', component: GestionMedicaments},

    {path: 'gestion-patients', component: GestionPatients},
    {path: 'gestion-consultations', component: GestionConsultation},
    {path: 'gestion-rdvs', component: GestionRdvs},
    {path: 'gestion-factures', component: GestionFactures},

    {path: 'patient-profile/:id', component: PatientProfile},

    {path: 'consulter-statistiques', component: Statistics},

    {path: 'login', component: Login},
    {path: '', component: Login},


];
