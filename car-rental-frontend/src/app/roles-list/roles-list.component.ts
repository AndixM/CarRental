import {Component} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {CommonModule} from '@angular/common';
import {Router} from '@angular/router';
import {MenuComponent} from '../menu/menu.component';

interface UserResponse {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
  address: string;
  age: number;
  phoneNumber: string;
  roles: string[];
}

@Component({
  selector: 'app-users-list',
  standalone: true,
  templateUrl: './users-list.component.html',
  styleUrls: ['./users-list.component.css'],
  imports: [CommonModule, MenuComponent]
})
export class RolesListComponent {
  users: UserResponse[] = [];

  constructor(private http: HttpClient, private router: Router) {
    this.fetchUsers();
  }

  fetchUsers() {
    this.http.get<UserResponse[]>('http://localhost:8080/api/users')
      .subscribe(data => {
        this.users = data;
        console.log(this.users);
      }, error => {
        console.error('Error fetching users:', error);
      });
  }

  deleteUser(index: number) {
    if (confirm("Are you sure you want to delete this user?")) {
      this.users.splice(index, 1);
    }
  }
}
