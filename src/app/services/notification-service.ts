import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Client, IMessage } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  private stompClient!: Client; // ✅ definite assignment
  private unreadCount$ = new BehaviorSubject<number>(0);
  private notifications$ = new BehaviorSubject<any[]>([]);

  constructor(private http: HttpClient) {}

  connect(userId: number) {
    this.stompClient = new Client({
      webSocketFactory: () => new SockJS('http://localhost:8080/ws'),
      reconnectDelay: 5000 // ✅ auto-reconnexion
    });

    this.stompClient.onConnect = () => {
      this.stompClient.subscribe(`/topic/notifications/${userId}`, (message: IMessage) => {
        const notif = JSON.parse(message.body);
        this.notifications$.next([...this.notifications$.value, notif]);
        this.unreadCount$.next(this.unreadCount$.value + 1);
      });
    };

    this.stompClient.activate(); // ✅ démarre la connexion
  }

  getUnreadCount() {
    return this.unreadCount$.asObservable();
  }

  getNotifications() {
    return this.notifications$.asObservable();
  }

  markAsRead(id: number) {
    return this.http.put(`http://localhost:8080/api/notifications/${id}/read`, {});
  }

  fetchUnread(userId: number) {
    return this.http.get<number>(`http://localhost:8080/api/notifications/unread/count/${userId}`);
  }
}
