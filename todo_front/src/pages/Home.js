import React, { useState } from 'react';
import '../assets/styles/HomeStyle.css';

function Home() {
  // const [todos, setTodos] = useState([]);
  const [todos, setTodos] = useState([
    { text: 'check list 1', isChecked: false, isEditing: false },
    { text: 'check list 2', isChecked: true, isEditing: false },
    { text: 'check list 2', isChecked: true, isEditing: false },
    { text: 'check list 2', isChecked: true, isEditing: false },
    { text: 'check list 2', isChecked: true, isEditing: false },
    { text: 'check list 2', isChecked: true, isEditing: false },
    { text: 'check list 2', isChecked: true, isEditing: false },
    { text: 'check list 2', isChecked: true, isEditing: false },
    { text: 'check list 2', isChecked: true, isEditing: false },
    { text: 'check list 2', isChecked: true, isEditing: false },
    { text: 'check list 2', isChecked: true, isEditing: false },
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
  );

  // return (
  //   <div className="home-container">
  //     <div className="home-body">
  //       <div className="home-add">
  //         <input className="home-input" />
  //         <button className="home-button-add">+</button>
  //       </div>
  //       <div className='home-context'>
  //         <div className="home-button">
  //           <button className="home-button-save">저장하기</button>
  //           <button className="home-button-delete">전체 삭제</button>
  //         </div>
  //         <div className="home-list">
  //           <div className='home-list-test1'>
  //             <input type='checkbox' />
  //             <input type='text' />
  //             <img className='home-image-modify' src='/images/modify.png'></img>
  //             <img className='home-image-delete' src='/images/delete.png'></img>
  //           </div>
  //         </div>
  //       </div>
  //     </div>
  //   </div>
  // );
}

export default Home;
