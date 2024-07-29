import React from 'react';
import '../assets/styles/FindIdStyle.css';

function FindId() {
  return (
    <div className="findid-container">
      <div className='findid-body'>
        <div className="findid-title">아이디 찾기</div>
        <div className="findid-input">
          <label>*이름</label>
          <input className="findid-input-name" type="text" />
        </div>
        <div className="findid-input">
          <label>*이메일</label>
          <input className="findid-input-email" type="email" />
        </div>
        <button className="findid-button">찾기</button>
      </div>
    </div>
  );
}

export default FindId;
