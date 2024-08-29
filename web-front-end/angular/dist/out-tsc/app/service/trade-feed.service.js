import { __decorate } from "tslib";
import { Injectable } from '@angular/core';
import { environment } from 'main/environments/environment';
import { io } from "socket.io-client";
let TradeFeedService = class TradeFeedService {
    constructor() {
        this.onConnect = () => {
            console.log('Trade feed is connected, connection id' + this.socket.id);
        };
        this.onDisconnect = () => {
            console.log('Trade feed is disconnected, connection id was ' + this.socket.id);
        };
        this.connect();
    }
    connect() {
        // create socketio client with long polling only
        this.socket = io(environment.tradeFeedUrl);
        this.socket.on("connect", this.onConnect);
        this.socket.on("disconnect", this.onDisconnect);
    }
    subscribe(topic, callback) {
        const callbackFn = (args) => {
            console.log("received message -> " + JSON.stringify(args));
            if (args.from !== 'System' && args.topic === topic) {
                callback(args.payload);
            }
        };
        this.socket.on('publish', callbackFn);
        this.socket.emit('subscribe', topic);
        console.log('subscribing', topic);
        return () => {
            this.unSubscribe(topic, callbackFn);
        };
    }
    unSubscribe(topic, callback) {
        console.log('unsubscribing' + topic);
        this.socket.emit('unsubscribe', topic);
        this.socket.off('publish', callback);
    }
};
TradeFeedService = __decorate([
    Injectable({
        providedIn: 'root'
    })
], TradeFeedService);
export { TradeFeedService };
//# sourceMappingURL=trade-feed.service.js.map