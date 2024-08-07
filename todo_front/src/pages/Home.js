import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { checkToken } from '../components/CheckToken';
import Nav from '../components/Nav';
import '../assets/styles/HomeStyle.css';

function Home() {
  const navigate = useNavigate();

  const [isPageVisible, setIsPageVisible] = useState(false);

  useEffect(() => {
    const verifyToken = async () => {
      const result = await checkToken(navigate);

      if (result === 'success') {
        console.log('home console success')
        setIsPageVisible(true);
      } else {
        console.log('home console failed')
        setIsPageVisible(false);
    }
    };

    verifyToken();
  }, [navigate]);


  // const [todos, setTodos] = useState([]);
  const [todos, setTodos] = useState([
    { text: 'check list 1', isChecked: false, isEditing: false },
    { text: 'check list 50', isChecked: true, isEditing: false }
  ]);
  const [newTodo, setNewTodo] = useState('');

  const handleAddTodo = () => {
    if (newTodo.trim()) {
      setTodos([...todos, { text: newTodo, isChecked: false }]);
      setNewTodo('');
    }
  };

  const handleCheckboxChange = (index) => {
    const updatedTodos = todos.map((todo, i) =>
      i === index ? { ...todo, isChecked: !todo.isChecked } : todo
    );
    setTodos(updatedTodos);
  };

  return (
    <>
    <Nav />
      {isPageVisible ? (
    <div className="home-container">
      <div className="home-input-container">
        <input
          type="text"
          value={newTodo}
          onChange={e => setNewTodo(e.target.value)}
          placeholder="할 일을 입력하세요"
        />
        <button onClick={handleAddTodo}>+</button>
      </div>
      <div className="home-button-container">
        <button className="home-button-save">저장하기</button>
        <button className="home-button-delete">전체 삭제</button>
      </div>
      <ul className="todo-list">
        {todos.map((todo, index) => (
          <li key={index} className="todo-item">
            {/* <input type="checkbox" />
            {todo.isEditing ? (
              <input type="text" value={todo.text} className="edit-input" />
            ) : (
              <span>{todo.text}</span>
            )} */}
            <input
              type="checkbox"
              checked={todo.isChecked}
              onChange={() => handleCheckboxChange(index)}
            />
            <span className={todo.isChecked ? 'strikethrough' : ''}>
              {todo.text}
            </span>
            <div className="icon-group">
              <img src="/images/edit.png" alt="수정" className="edit-icon" />
              <img
                src="/images/delete.png"
                alt="삭제"
                className="delete-icon"
              />
            </div>
            {/* <div className="todo-actions">
              <button className="edit-btn">✎</button>
              <button className="delete-btn">🗑</button>
            </div> */}
          </li>
        ))}
      </ul>
    </div>
      ) : null}
    </>
  );
}

export default Home;
