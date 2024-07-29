import React from 'react';
import '../assets/styles/HomeStyle.css';

function Home() {
  return (
    <div className="home-container">
      <div className="home-body">
        <div className="home-add">
          <input className="home-input" />
          <button className="home-button-add">+</button>
        </div>
        <div className='home-context'>
          <div className="home-button">
            <button className="home-button-save">저장하기</button>
            <button className="home-button-delete">전체 삭제</button>
          </div>
          <div className="home-list">
            <div className='home-list-test1'>
              <input type='checkbox' />
              <input type='text' />
              <img className='home-image-modify' src='/images/modify.png'></img>
              <img className='home-image-delete' src='/images/delete.png'></img>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Home;
