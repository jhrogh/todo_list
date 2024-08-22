import React, { useState, useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import Nav from '../components/Nav';
import '../assets/styles/SelectListStyle.css';

function SelectList() {
  const location = useLocation();
  const { id, title } = location.state || {};

  const [isPageVisible, setIsPageVisible] = useState(false);
  const [userName, setUserName] = useState('');

  const [todos, setTodos] = useState([]);
  const [newTodo, setNewTodo] = useState('');
  const [editIndex, setEditIndex] = useState(null);
  const [editText, setEditText] = useState('');

  const handleKeyDown = e => {
    if (e.key === 'Enter') {
      e.preventDefault();
      handleAddTodo();
    }
  };

  useEffect(() => {
    const showData = async () => {
      try {
        const response = await fetch(
          `http://43.202.173.195:8080/api/savelist/selectlist/id?id=${encodeURIComponent(
            id,
          )}`,
          {
            method: 'GET',
            headers: { 'Content-Type': 'application/json' },
            credentials: 'include',
          },
        );

        const data = await response.json();

        if (response.ok) {
          if (data.lists) {
            const fetchedTodos = data.lists.map(item => ({
              id: item.id,
              text: item.content,
              isChecked: item.isChecked,
            }));
            setTodos(fetchedTodos);
          } else {
            console.error('Failed to fetch todos');
          }
        }
      } catch (error) {
        console.error('Error fetching todos:', error);
      }
    };
    showData();
  }, []);

  const handleAddTodo = async () => {
    if (newTodo.trim()) {
      try {
        const response = await fetch(
          'http://43.202.173.195:8080/api/savelist/selectlist/add',
          {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
            },
            credentials: 'include',
            body: JSON.stringify({ id: id, content: newTodo.trim() }),
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
        'http://43.202.173.195:8080/api/savelist/selectlist/checkbox',
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
        'http://43.202.173.195:8080/api/savelist/selectlist/update',
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
        `http://43.202.173.195:8080/api/savelist/selectlist/delete?id=${todos[index].id}`,
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

  return (
    <>
      <Nav setIsPageVisible={setIsPageVisible} setUserName={setUserName} />
      {isPageVisible && (
        <div className="selectlist-container">
          <div className="selectlist-input-container">
            <input
              type="text"
              value={newTodo}
              onChange={e => setNewTodo(e.target.value)}
              onKeyDown={handleKeyDown}
            />
            <button onClick={handleAddTodo}>+</button>
          </div>
          <div className="selectlist-savelist-title">{title}</div>
          <ul className="selectlist-todo-list">
            {todos.map((todo, index) => (
              <li key={index} className="selectlist-todo-item">
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
                          e.preventDefault();
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

export default SelectList;
