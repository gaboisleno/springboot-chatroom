import { Component, OnInit } from '@angular/core';
import * as SockJs from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';
import { FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent implements OnInit {
  serverUrl = 'http://localhost:8080/socket';
  title = 'WebSocket Chat';
  stompClient?: any;
  chats: string[] = [];

  myForm = this.fb.group({
    message: ["", [Validators.required, Validators.min(1)]]
  });

  constructor(private fb: FormBuilder){}

  ngOnInit(): void {
    this.initializeWebSocket();
  }
  
  initializeWebSocket() {
    let ws = new SockJs(this.serverUrl);
    this.stompClient = Stomp.over(ws);
    let that = this;
    
    this.stompClient.connect({}, () => {
      that.stompClient.subscribe("/chat-response", (message: any) => {
        this.chats.push(message.body);
      })
    })
  }

  sendMessage() {
    if (this.myForm.valid){
      this.stompClient.send('/app/chat-room', {}, this.myForm.get('message')?.value);
      this.myForm.patchValue({ message: "" });
    }
  }

}
