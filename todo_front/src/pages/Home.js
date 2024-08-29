import React, { useState, useEffect } from 'react';
import Nav from '../components/Nav';
import '../assets/styles/HomeStyle.css';

function Home() {
  const [isPageVisible, setIsPageVisible] = useState(false);
  const [userName, setUserName] = useState('');

  const [todos, setTodos] = useState([]);
  const [newTodo, setNewTodo] = useState('');
  const [editIndex, setEditIndex] = useState(null);
  const [editText, setEditText] = useState('');

  const handleKeyDown = e => {
    if (e.key === 'Enter') {
      handleAddTodo();
    }
  };

  useEffect(() => {
    console.log('Todos state has been updated:', todos);
  }, [todos]);

  useEffect(() => {
    const fetchTodos = async () => {
      try {
        const response = await fetch(
          // 'http://43.202.173.195:8080/api/home/show',
          'http://localhost:8080/api/home/show',
          {
            method: 'GET',
            credentials: 'include',
          },
        );

        if (response.ok) {
          const data = await response.json();
          if (data.lists) {
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
        const response = await fetch(
          // 'http://43.202.173.195:8080/api/home/add',
          'http://localhost:8080/api/home/add',
          {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
            },
            credentials: 'include',
            body: newTodo.trim(),
          },
        );

        if (response.ok) {
          const data = await response.json();
          console.log(data);
          if (data.status === 'success') {
            const newTodoItem = {
              id: data.id,
              text: data.content,
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
    console.log(updatedCheckedState);

    const updatedTodos = todos.map((todo, i) =>
      i === index ? { ...todo, isChecked: updatedCheckedState } : todo,
    );
    setTodos(updatedTodos);
    console.log(todos);

    const requestBody = JSON.stringify({
      id: todos[index].id,
      isChecked: updatedCheckedState,
    });
    console.log(requestBody);

    try {
      const response = await fetch(
        // 'http://43.202.173.195:8080/api/home/checkbox',
        'http://localhost:8080/api/home/checkbox',
        {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          credentials: 'include',
          body: requestBody,
        },
      );

      console.log(todos);
      if (response.ok) {
        console.log('Check 상태 업데이트 성공');
        setTodos(prevTodos =>
          prevTodos.map((todo, i) =>
            i === index ? { ...todo, isChecked: updatedCheckedState } : todo,
          ),
        );
        console.log(todos);
      } else {
        console.error('Failed to update todo');
      }
    } catch (error) {
      console.error('Error updating todo:', error);
    }
  };

  const handleUpdate = async index => {
    if (todos[index].isChecked) return;

    const updatedTodo = { ...todos[index], text: editText };
    const updatedTodos = todos.map((todo, i) =>
      i === index ? updatedTodo : todo,
    );
    setTodos(updatedTodos);

    const requestBody = JSON.stringify({
      id: todos[index].id,
      content: editText,
    });

    try {
      const response = await fetch(
        // 'http://43.202.173.195:8080/api/home/update',
        'http://localhost:8080/api/home/update',
        {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          credentials: 'include',
          body: requestBody,
        },
      );
      const data = await response.json();
      if (response.ok) {
        console.log(data.message);
      } else {
        console.error('Failed to update todo');
      }
    } catch (error) {
      console.error('Error updating todo:', error);
    }

    setEditIndex(null);
    setEditText('');
  };

  const handleDelete = async index => {
    if (todos[index].isChecked) return;

    try {
      const response = await fetch(
        // `http://43.202.173.195:8080/api/home/delete-list?id=${todos[index].id}`,
        `http://localhost:8080/api/home/delete-list?id=${todos[index].id}`,
        {
          method: 'DELETE',
          headers: {
            'Content-Type': 'application/json',
          },
          credentials: 'include',
        },
      );

      if (response.ok) {
        const updatedTodos = todos.filter((_, i) => i !== index);
        setTodos(updatedTodos);
        console.log('Todo 삭제 성공');
      } else {
        console.error('Failed to delete todo');
      }
    } catch (error) {
      console.error('Error deleting todo:', error);
    }
  };

  const handleSaveAll = async () => {
    const ids = todos.map(todo => todo.id);
    console.log(ids);
    if (ids.length == 0) {
      alert('작성된 내용이 없습니다.');
      return;
    }

    try {
      // const response = await fetch('http://43.202.173.195:8080/api/home/save', {
        const response = await fetch('http://localhost:8080/api/home/save', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        credentials: 'include',
        body: JSON.stringify({ ids }),
      });

      if (response.ok) {
        console.log('리스트 상태 저장 성공');
        setTodos([]);
        alert('저장되었습니다. 내역에서 확인해주세요 :)');
      } else {
        console.error('Failed to save list states');
      }
    } catch (error) {
      console.error('Error saving list states:', error);
    }
  };

  const handleDeleteAll = async index => {
    const ids = todos.map(todo => todo.id);
    if (ids.length == 0) {
      alert('작성된 내용이 없습니다.');
      return;
    }

    alert('삭제하시겠습니까?');

    try {
      const response = await fetch(
        // 'http://43.202.173.195:8080/api/home/delete-all',
        'http://localhost:8080/api/home/delete-all',
        {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          credentials: 'include',
          body: JSON.stringify({ ids }),
        },
      );

      if (response.ok) {
        console.log('리스트 전체 삭제 성공');
        setTodos([]);
      } else {
        console.error('Failed to save list states');
      }
    } catch (error) {
      console.error('Error saving list states:', error);
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
            <button onClick={handleAddTodo}>+</button>
          </div>
          <div className="home-button-container">
            <button className="home-button-save" onClick={handleSaveAll}>
              저장하기
            </button>
            <button className="home-button-delete" onClick={handleDeleteAll}>
              전체 삭제
            </button>
          </div>
          <ul className="todo-list">
            {todos.map((todo, index) => (
              <li key={index} className="todo-item">
                <input
                  type="checkbox"
                  checked={todo.isChecked}
                  onChange={() => handleCheckboxChange(index)}
                />
                {editIndex === index ? (
                  <div className="todo-edit-container">
                    <input
                      type="text"
                      value={editText}
                      onChange={e => setEditText(e.target.value)}
                      onBlur={() => handleUpdate(index)}
                      onKeyDown={e => {
                        if (e.key === 'Enter') {
                          handleUpdate(index);
                        }
                      }}
                      className="todo-edit-input"
                    />
                  </div>
                ) : (
                  <span
                    className={
                      todo.isChecked ? 'strikethrough' : 'todo-item-span'
                    }
                  >
                    {todo.text}
                  </span>
                )}
                <div className="icon-group">
                  {editIndex === index ? (
                    <img
                      src="/images/ok.png"
                      alt="완료"
                      className="edit-icon"
                      onClick={() => handleUpdate(index)}
                    />
                  ) : (
                    <img
                      src="/images/edit.png"
                      alt="수정"
                      className="edit-icon"
                      onClick={() => {
                        if (!todo.isChecked) {
                          setEditIndex(index);
                          setEditText(todo.text);
                        }
                      }}
                    />
                  )}
                  <img
                    src="/images/delete.png"
                    alt="삭제"
                    className="delete-icon"
                    onClick={() => {
                      if (!todo.isChecked) {
                        handleDelete(index);
                      }
                    }}
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
