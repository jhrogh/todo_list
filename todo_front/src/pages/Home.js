import React, { useState, useEffect } from 'react';
import Nav from '../components/Nav';
import '../assets/styles/HomeStyle.css';

function Home() {
  const [isPageVisible, setIsPageVisible] = useState(false);
  const [userName, setUserName] = useState('');

  const [todos, setTodos] = useState([]);
  const [newTodo, setNewTodo] = useState('');

  const handleKeyDown = e => {
    if (e.key === 'Enter') {
      handleAddTodo();
    }
  };

  useEffect(() => {
    const fetchTodos = async () => {
      try {
        const response = await fetch('http://localhost:8080/api/home/show', {
          method: 'GET',
          credentials: 'include',
        });

        if (response.ok) {
          const data = await response.json();
          if (data.lists) {
            console.log(data.lists);
            const fetchedTodos = data.lists.map(item => ({
              id: item.id,
              text: item.content,
              isChecked: item.isChecked,
            }));
            setTodos(fetchedTodos);
          }
        } else {
          console.error('Failed to fetch todos');
        }
      } catch (error) {
        console.error('Error fetching todos:', error);
      }
    };

    fetchTodos();
  }, []);

  const handleAddTodo = async () => {
    if (newTodo.trim()) {
      try {
        const response = await fetch('http://localhost:8080/api/home/add', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          credentials: 'include',
          body: newTodo.trim(),
          // body: JSON.stringify({ content: newTodo }),
        });

        if (response.ok) {
          const data = await response.json();
          if (data.status === 'success') {
            const newTodoItem = {
              text: newTodo,
              isChecked: false,
            };
            setTodos([...todos, newTodoItem]);
            setNewTodo('');
          } else {
            console.error('Failed to add todo');
          }
        } else {
          console.error('Failed to add todo');
        }
      } catch (error) {
        console.error('Error adding todo:', error);
      }
    }
  };

  const handleCheckboxChange = async index => {
    const updatedCheckedState = !todos[index].isChecked;
    console.log('1번 updatedCheckedState: ' + updatedCheckedState);

    const updatedTodos = todos.map((todo, i) =>
      i === index ? { ...todo, isChecked: updatedCheckedState } : todo,
    );
    setTodos(updatedTodos);
    console.log('2번 updatedTodos:', updatedTodos);

    const requestBody = JSON.stringify({
      id: todos[index].id,
      isChecked: updatedCheckedState,
    });

    console.log('3번 requestBody:', requestBody);

    try {
      const response = await fetch('http://localhost:8080/api/home/checkbox', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        credentials: 'include',
        body: requestBody,
      });
      console.log('4번 fetch 요청 결과:', response);

      if (response.ok) {
        console.log(
          '응답 200 - 체크박스 상태가 성공적으로 업데이트 되었습니다.',
        );
      } else {
        console.error('Failed to update todo');
      }
    } catch (error) {
      console.error('Error updating todo:', error);
    }
  };

  

  return (
    <>
      <Nav setIsPageVisible={setIsPageVisible} setUserName={setUserName} />
      {isPageVisible && (
        <div className="home-container">
          <div className="home-input-container">
            <input
              type="text"
              value={newTodo}
              onChange={e => setNewTodo(e.target.value)}
              onKeyDown={handleKeyDown}
              placeholder="할 일을 입력하세요"
            />
            <button onClick={handleAddTodo}>+</button>{' '}
          </div>
          <div className="home-button-container">
            <button className="home-button-save">저장하기</button>
            <button className="home-button-delete">전체 삭제</button>
          </div>
          <ul className="todo-list">
            {todos.map((todo, index) => (
              <li key={index} className="todo-item">
                <input
                  type="checkbox"
                  checked={todo.isChecked}
                  onChange={() => handleCheckboxChange(index)}
                />
                <span className={todo.isChecked ? 'strikethrough' : ''}>
                  {' '}
                  {todo.text}
                </span>
                <div className="icon-group">
                  <img
                    src="/images/edit.png"
                    alt="수정"
                    className="edit-icon"
                    onChange={() => handleUpdate(index)}
                  />
                  <img
                    src="/images/delete.png"
                    alt="삭제"
                    className="delete-icon"
                    onChange={() => handleDelete(index)}
                  />
                </div>
              </li>
            ))}
          </ul>
        </div>
      )}
    </>
  );
}

export default Home;
