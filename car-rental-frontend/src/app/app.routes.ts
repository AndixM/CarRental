import {Routes} from '@angular/router';
import {LoginComponent} from './login/login.component';
import {RegisterComponent} from './register/register.component';
import {CarListComponent} from './car-list/car-list.component';
import {AddNewCarComponent} from './add-new-car/add-new-car.component';
import {ReservationListComponent} from './reservation-list/reservation-list.component';
import {AddNewReservationComponent} from './add-new-reservation/add-new-reservation.component';
import {BranchesListComponent} from './branches-list/branches-list.component';
import {AddNewBranchComponent} from './add-new-branch/add-new-branch.component';
import {UsersListComponent} from './users-list/users-list.component';

export const routes: Routes = [
  {path: '', redirectTo: 'login', pathMatch: 'full'},
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'cars', component: CarListComponent},
  {path: 'add-cars', component: AddNewCarComponent},
  {path: 'reservations', component: ReservationListComponent},
  {path: 'add-reservations', component: AddNewReservationComponent},
  {path: 'branches', component: BranchesListComponent},
  {path: 'add-branches', component: AddNewBranchComponent},
  {path: 'users', component: UsersListComponent},
];

