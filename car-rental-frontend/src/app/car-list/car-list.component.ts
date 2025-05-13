import {Component} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {CommonModule} from '@angular/common';
import {Router} from '@angular/router';
import {MenuComponent} from '../menu/menu.component';

@Component({
  selector: 'app-car-list',
  standalone: true,
  templateUrl: './car-list.component.html',
  styleUrls: ['./car-list.component.css'],
  imports: [CommonModule, MenuComponent]
})
export class CarListComponent {
  cars: any[] = [];

  constructor(private http: HttpClient, private router: Router) {
    this.fetchCars();
  }

  fetchCars() {
    this.http.get<any[]>('http://localhost:8080/api/car')
      .subscribe(data => {
        this.cars = data;
      }, error => {
        console.error('Error fetching cars:', error);
      });
  }

  deleteCar(id: number, index: number) {
    if(confirm("Are you sure you want to delete this car?")){
      this.http.delete<any[]>('http://localhost:8080/api/deleteCar/' + id)
        .subscribe(data => {
          this.cars.splice(index, 1);
        }, error => {
          confirm("Error at deleting, reservation existing for this car")
        });
    }
  }

  editCar(carId: number) {
    this.router.navigate(['/add-cars'], { queryParams: { carId: carId } });
  }
}
