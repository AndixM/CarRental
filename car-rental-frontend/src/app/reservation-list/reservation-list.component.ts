import {Component} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {CommonModule} from '@angular/common';
import {Router} from '@angular/router';
import {MenuComponent} from '../menu/menu.component';

@Component({
  selector: 'app-reservation-list',
  standalone: true,
  templateUrl: './reservation-list.component.html',
  styleUrls: ['./reservation-list.component.css'],
  imports: [CommonModule, MenuComponent]
})
export class ReservationListComponent {
  reservations: any[] = [];

  constructor(private http: HttpClient, private router: Router) {
    this.fetchReservations();
  }

  fetchReservations() {
    this.http.get<any[]>('http://localhost:8080/api/reservations')
      .subscribe(data => {
        this.reservations = data;
      }, error => {
        console.error('Error fetching reservations:', error);
      });
  }

  deleteReservation(index: number) {
    if (confirm("Are you sure you want to delete this reservation?")) {
      this.reservations.splice(index, 1);
    }
  }

  editReservation(reservationId: number) {
    this.router.navigate(['/add-reservations'], { queryParams: {reservationId: reservationId}})
  }
}
