import React, { useState, useEffect } from 'react';
import './App.css';

function App() {
  const [board, setBoard] = useState([]);

  useEffect(() => {
    fetch("/api/game/start")
      .then(res => res.json())
      .then(data => setBoard(data.board));
  }, []);

  const move = (direction) => {
    fetch(`/api/game/move/${direction}`, { method: 'POST' })
      .then(res => res.json())
      .then(data => setBoard(data.board));
  };

  return (
    <div className="App">
      <h1>2048 Game</h1>
      <div className="board">
        {board.map((row, i) => (
          <div key={i} className="row">
            {row.map((cell, j) => (
              <div key={j} className="cell">{cell}</div>
            ))}
          </div>
        ))}
      </div>
      <div className="controls">
        <button onClick={() => move('up')}>⬆ Up</button>
        <button onClick={() => move('left')}>⬅ Left</button>
        <button onClick={() => move('down')}>⬇ Down</button>
        <button onClick={() => move('right')}>➡ Right</button>
      </div>
    </div>
  );
}

export default App;
