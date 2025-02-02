const express = require('express');
const http = require('http');
const socketIo = require('socket.io');

const app = express();
const server = http.createServer(app);
const io = socketIo(server);

const rooms = {}; // Stores active rooms and their players

const path = require('path');

app.use(express.static(path.join(__dirname, 'public'))); // Serve static files from 'public' folder

app.get('/', (req, res) => {
    res.sendFile(path.join(__dirname, 'public', 'index.html'));
});


// Generate a unique 6-digit room code
function generateRoomCode() {
    return Math.floor(100000 + Math.random() * 900000).toString();
}

// Handle socket connection
io.on('connection', (socket) => {
    console.log(`User connected: ${socket.id}`);

    // Create a new room
    socket.on('create-room', (callback) => {
        const roomCode = generateRoomCode();
        rooms[roomCode] = { players: [], currentTurn: 0, timer: null };
        socket.join(roomCode);
        rooms[roomCode].players.push(socket.id);
        callback({ success: true, roomCode });
        console.log(`Room ${roomCode} created`);
    });

    // Join an existing room
    socket.on('join-room', (roomCode, callback) => {
        if (rooms[roomCode] && rooms[roomCode].players.length < 10) {
            socket.join(roomCode);
            rooms[roomCode].players.push(socket.id);
            callback({ success: true, roomCode });

            // Notify others in the room
            io.to(roomCode).emit('player-joined', { id: socket.id, players: rooms[roomCode].players });

            // If at least 2 players, start game
            if (rooms[roomCode].players.length >= 2) {
                startTurn(roomCode);
            }
        } else {
            callback({ success: false, message: "Room full or not found" });
        }
    });

    // Handle turn completion
    socket.on('end-turn', (roomCode) => {
        if (rooms[roomCode]) {
            nextTurn(roomCode);
        }
    });

    // Handle disconnection
    socket.on('disconnect', () => {
        for (const roomCode in rooms) {
            let room = rooms[roomCode];
            room.players = room.players.filter(id => id !== socket.id);

            if (room.players.length === 0) {
                delete rooms[roomCode]; // Remove empty room
            } else {
                io.to(roomCode).emit('player-left', { id: socket.id, players: room.players });

                // If current player left, move to the next turn
                if (room.players[room.currentTurn] === socket.id) {
                    nextTurn(roomCode);
                }
            }
        }
        console.log(`User disconnected: ${socket.id}`);
    });
});

// Start a player's turn
function startTurn(roomCode) {
    if (!rooms[roomCode]) return;

    let room = rooms[roomCode];
    let currentPlayer = room.players[room.currentTurn];

    io.to(roomCode).emit('turn-start', { playerId: currentPlayer, time: 30 });

    // Start a 30-second timer
    room.timer = setTimeout(() => {
        nextTurn(roomCode);
    }, 30000);
}

// Move to the next turn
function nextTurn(roomCode) {
    if (!rooms[roomCode]) return;

    let room = rooms[roomCode];

    // Clear previous timer
    if (room.timer) clearTimeout(room.timer);

    // Move to the next player
    room.currentTurn = (room.currentTurn + 1) % room.players.length;

    startTurn(roomCode);
}
setInterval(() => {
    for (const roomCode in rooms) {
        nextTurn(roomCode);
    }
}, 5000); // Change turn every 5 seconds for testing


server.listen(3000, () => console.log('Server running on http://localhost:3000'));
