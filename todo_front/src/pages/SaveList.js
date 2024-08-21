import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import Nav from '../components/Nav';
import '../assets/styles/SaveListStyle.css';

function SaveList() {
  const navigate = useNavigate();
  const [isPageVisible, setIsPageVisible] = useState(false);
  const [userName, setUserName] = useState('');

  const [todos, setTodos] = useState([]);
  const [editIndex, setEditIndex] = useState(null);
  const [editText, setEditText] = useState('');

  useEffect(() => {
    const fetchTodos = async () => {
      try {
        const response = await fetch(
          'http://localhost:8080/api/savelist/show',
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
              title: item.title,
            }));
            setTodos(fetchedTodos);
            console.log('데이터 불러오기 성공:', fetchedTodos);
          } else {
            console.error('lists 항목이 없습니다.');
          }
        } else {
          console.error('Failed to fetch todos:', response.status);
        }
      } catch (error) {
        console.error('Error fetching todos:', error);
      }
    };

    fetchTodos();
  }, []);

  const handleUpdate = async index => {
    if (todos[index].isChecked) return;

    const updatedTodo = { ...todos[index], title: editText };
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
        'http://localhost:8080/api/savelist/update',
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
        `http://localhost:8080/api/savelist/delete?id=${todos[index].id}`,
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

  const handleGoTo = (id, title) => {
    console.log(id);
    navigate('/savelist/selectlist', { state: { id, title } });
  };

  return (
    <>
      <Nav setIsPageVisible={setIsPageVisible} setUserName={setUserName} />
      {isPageVisible && (
        <div className="todo-list-container">
          <ul className="todo-list">
            {todos.map((todo, index) => (
              <li key={index} className="todo-item">
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
                    className={todo.isChecked ? 'strikethrough' : ''}
                    onClick={() => handleGoTo(todo.id, todo.title)}
                  >
                    {todo.title}
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
                          setEditText(todo.title);
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

export default SaveList;
