const net = require('net');

const HOST = process.env.PUSH_HOST || '127.0.0.1';
const PORT = parseInt(process.env.PUSH_PORT || '9898', 10);
const MAX_MESSAGE_SIZE = 4 * 1024;

const server = net.createServer((socket) => {
  socket.on('data', (data) => {
    if (data.length > MAX_MESSAGE_SIZE) {
      socket.write('SERVER: mensaje demasiado grande.\n');
      socket.end();
      return;
    }
    console.log('Datos recibidos (truncados):', data.toString().slice(0, 200));
  });

  socket.write('SERVER: Hello! This is server speaking.\n');
  socket.end('SERVER: Closing connection now.\n');
}).on('error', (err) => {
  console.error(err.message);
});

server.listen(PORT, HOST, () => {
  console.log('Servidor TCP abierto en ' + HOST + ':' + server.address().port);
});