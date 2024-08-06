import React from 'react';
import Nav from '../components/Nav';
import '../assets/styles/SaveListStyle.css';

function SaveList() {
  const posts = [
    { id: 1, title: '2024.07.31.16:30' },
    { id: 2, title: '여행 준비물' },
    { id: 3, title: '2024.06.15.15:33' },
    { id: 4, title: '글제목 수정 중' }
  ];

  return (
    <>
    <Nav />
    <div className="todo-list-container">
      {posts.map(post => (
        <div key={post.id} className="todo-item">
          <span>{post.title}</span>
          <div className="icon-group">
            <img
              src="/images/edit.png"
              alt="수정"
              className="edit-icon"
            />
            <img
              src="/images/delete.png"
              alt="삭제"
              className="delete-icon"
            />
          </div>
        </div>
      ))}
    </div>
    </>
  );
}

export default SaveList;
