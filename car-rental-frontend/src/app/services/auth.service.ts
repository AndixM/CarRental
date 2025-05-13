import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';
import {Observable, tap} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/signin';

  constructor(private http: HttpClient, private router: Router) {
  }

  login(credentials: { username: string; password: string }): Observable<string> {

    return this.http.post('http://localhost:8080/api/signin', credentials, {
      responseType: 'text',
      withCredentials: true
    }).pipe(
      tap(response => {
        console.log('Răspuns backend:', response.trim());
      })
    );
  }
}
