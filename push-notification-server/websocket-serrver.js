const http = require('http');
const { WebSocketServer } = require('ws');

const HOST = process.env.PUSH_HOST || '127.0.0.1';
const PORT = parseInt(process.env.PUSH_PORT || '9898', 10);
const MAX_MESSAGE_SIZE = 64 * 1024;
const ALLOWED_ORIGINS = (process.env.PUSH_ALLOWED_ORIGINS || 'http://localhost:4200,http://localhost')
    .split(',')
    .map((origin) => origin.trim())
    .filter(Boolean);

const server = http.createServer();

const wsServer = new WebSocketServer({
    server,
    maxPayload: MAX_MESSAGE_SIZE,
    verifyClient: (info) => {
        const origin = info.origin || '';
        const allowed = ALLOWED_ORIGINS.length === 0 || ALLOWED_ORIGINS.includes(origin);
        if (!allowed) {
            console.warn('Conexión rechazada: origin no permitido:', origin);
        }
        return allowed;
    }
});

wsServer.on('connection', (socket, request) => {
    const peer = request.socket.remoteAddress;
    console.log('Cliente conectado desde:', peer);

    socket.on('message', (data) => {
        const text = data.toString().slice(0, 200);
        console.log('Mensaje recibido (truncado a 200 chars):', text);
        socket.send('Hi this is WebSocket server!');
    });

    socket.on('close', () => {
        console.log('Cliente desconectado.');
    });

    socket.on('error', (err) => {
        console.error('Error en conexión:', err.message);
    });
});

server.listen(PORT, HOST, () => {
    console.log('Servidor WebSocket en ws://' + HOST + ':' + PORT);
});